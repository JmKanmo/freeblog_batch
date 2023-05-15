package com.service.freeblog_batch.web.util.sftp;

import com.jcraft.jsch.*;
import com.service.freeblog_batch.config.sftp.SFtpConfig;
import com.service.freeblog_batch.web.util.ConstUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Properties;

@Component
@RequiredArgsConstructor
public class SftpUtil {
    private Session jschSession;
    private Channel channel;
    private ChannelSftp channelSftp;
    private final SFtpConfig sFtpConfig;


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
