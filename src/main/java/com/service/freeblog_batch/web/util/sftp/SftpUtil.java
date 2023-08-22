package com.service.freeblog_batch.web.util.sftp;

import com.jcraft.jsch.*;
import com.service.freeblog_batch.config.sftp.SFtpConfig;
import com.service.freeblog_batch.web.util.BatchUtil;
import com.service.freeblog_batch.web.util.ConstUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.Vector;

@Component
@Slf4j
@RequiredArgsConstructor
public class SftpUtil {
    private Session jschSession;
    private Channel channel;
    private ChannelSftp channelSftp;
    private final SFtpConfig sFtpConfig;


    private boolean checkFileLastViewTime(String directory, long maxDiffSec) {
        try {
            String command = BatchUtil.calcFileSizeCommand(directory);
            String viewTimeResult = doCommand(command);
            String[] splited = viewTimeResult.split(" ");
            String viewTime = splited[0] + " " + splited[1].substring(0, splited[1].lastIndexOf("."));
            LocalDateTime convertedLocalDateTime = BatchUtil.formatStrToLocalDateTime(viewTime, "yyyy-MM-dd HH:mm:ss");
            long diffSec = Duration.between(convertedLocalDateTime, LocalDateTime.now()).toSeconds();
            return diffSec > maxDiffSec;
        } catch (Exception e) {
            log.info("[SftpUtil:checkFileTime] Exception: " + e);
            return false;
        }
    }

    private void traversalDirectory(String directory, long maxDiffSec) throws Exception {
        try {
            Vector<ChannelSftp.LsEntry> directoryEntries = channelSftp.ls(directory);
            boolean isEmptyDir = directoryEntries.size() <= 2;

            if (!isEmptyDir) {
                for (ChannelSftp.LsEntry entry : directoryEntries) {
                    String fileName = entry.getFilename();

                    if (fileName.equals(".") || fileName.equals("..")) {
                        continue;
                    }

                    String fullFileDir = directory + "/" + fileName;

                    if (!entry.getAttrs().isDir()) {
                        if (checkFileLastViewTime(fullFileDir, maxDiffSec)) {
                            deleteImageFile(fullFileDir);
                        }
                    } else {
                        traversalDirectory(fullFileDir, maxDiffSec);
                    }
                }
            }
        } catch (SftpException sftpException) {
            throw sftpException;
        }
    }

    public void checkAngDeleteFile(String directory, long maxDiffSec) throws Exception {
        try {
            connectSFTP();
            traversalDirectory(directory, maxDiffSec);
            disconnectSFTP();
        } catch (SftpException sftpException) {
            throw sftpException;
        }
    }

    private String doCommand(String command) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            ChannelExec channel = (ChannelExec) jschSession.openChannel("exec");
            channel.setCommand(command);
            channel.setInputStream(null);
            channel.setErrStream(System.err);

            try (BufferedInputStream bufferedInputStream = new BufferedInputStream(channel.getInputStream())) {
                channel.connect();

                byte[] tmp = new byte[1024];

                while (true) {
                    while (bufferedInputStream.available() > 0) {
                        int i = bufferedInputStream.read(tmp, 0, 1024);

                        if (i < 0) {
                            break;
                        }
                        stringBuilder.append(new String(tmp, 0, i));
                    }

                    if (channel.isClosed()) {
                        if (bufferedInputStream.available() > 0) continue;
                        log.info("[SftpUtil:doCommand] Exit status: " + channel.getExitStatus());
                        break;
                    }
                }
            }
        } catch (Exception exception) {
            log.error("[SftpUtil:doCommand] Exception: " + exception);
        }
        return stringBuilder.toString();
    }

    /**
     * type/hash/id 디렉토리 삭제
     *
     * @param hash (hash)
     * @param id   (unique key)
     * @throws Exception
     */
    public void deleteImageFile(String type, int hash, String id) throws Exception {
        try {
            connectSFTP();
            String dir = sFtpConfig.getDirectory() + "/" + type + "/" + hash + "/" + id;

            if (checkDir(dir)) {
                channelSftp.rmdir(dir);
            }

            dir = sFtpConfig.getDirectory() + "/" + type + "/" + hash;
            if (channelSftp.ls(dir).size() <= 2) {
                channelSftp.rmdir(dir);
            }

            dir = sFtpConfig.getDirectory() + "/" + type;
            if (channelSftp.ls(dir).size() <= 2) {
                channelSftp.rmdir(dir);
            }
            disconnectSFTP();
        } catch (SftpException sftpException) {
            throw sftpException;
        }
    }

    /**
     * directory 파일 삭제
     *
     * @param directory (directory)
     * @throws Exception
     */
    public void deleteImageFile(String directory) throws Exception {
        try {
            channelSftp.rm(directory);
        } catch (SftpException sftpException) {
            throw sftpException;
        }
    }

    /**
     * SFTP 접속하기
     *
     * @return
     * @throws JSchException
     * @throws Exception
     */
    public void connectSFTP() throws Exception {
        // JSch 객체를 생성
        JSch jsch = new JSch();

        // JSch 세션 객체를 생성 (사용자 이름, 접속할 호스트, 포트 전달)
        jschSession = jsch.getSession(sFtpConfig.getId(), sFtpConfig.getIp(), sFtpConfig.getPort());

        // 패스워드 설정
        jschSession.setPassword(sFtpConfig.getPassword());

        // 기타설정 적용
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        jschSession.setConfig(config);

        // 접속
        jschSession.connect();

        // sftp 채널 열기
        channel = jschSession.openChannel("sftp");

        // sftp 채널 연결
        channelSftp = (ChannelSftp) channel;
        channelSftp.connect();
    }

    /**
     * SFTP 접속해제
     */
    public void disconnectSFTP() {
        try {
            if (channelSftp != null && channelSftp.isConnected()) {
                channelSftp.disconnect();
            }
        } catch (Exception e) {
            throw e;
        } finally {
            channelSftp = null;
        }

        try {
            if (channel != null && channel.isConnected()) {
                channel.disconnect();
            }
        } catch (Exception e) {
            throw e;
        } finally {
            channel = null;
        }

        try {
            if (jschSession != null && jschSession.isConnected()) {
                jschSession.disconnect();
            }
        } catch (Exception e) {
            throw e;
        } finally {
            jschSession = null;
        }
    }

    private boolean checkDir(String dir) throws Exception {
        try {
            return channelSftp.stat(dir).isDir();
        } catch (Exception e) {
            return false;
        }
    }
}
