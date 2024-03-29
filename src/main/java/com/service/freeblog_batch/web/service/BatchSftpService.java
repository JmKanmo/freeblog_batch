package com.service.freeblog_batch.web.service;

import com.service.freeblog_batch.config.batch.BatchConfig;
import com.service.freeblog_batch.config.sftp.SFtpConfig;
import com.service.freeblog_batch.web.util.ConstUtil;
import com.service.freeblog_batch.web.util.sftp.SftpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BatchSftpService {
    private final SftpUtil sftpUtil;
    private final SFtpConfig sFtpConfig;
    private final BatchConfig batchConfig;

    /**
     * @param hash (USER_PROFILE_THUMBNAIL_HASH | POST_HASH | COMMENT_HASH ...)
     * @param id   (USER_ID | POST_META_KEY | COMMENT_IMAGE_META_KEY ...)
     */
    public void deleteImageFile(int hash, String id) {
        try {
            sftpUtil.deleteImageFile(ConstUtil.SFTP_IMAGE_TYPE, hash, id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * @param dir (image full directory)
     */
    public void deleteFile(String dir) {
        try {
            String[] parsed = parsedSftpImgSrc(dir);
            deleteImageFile(Integer.parseInt(parsed[2]), parsed[3]);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private String[] parsedSftpImgSrc(String imgSrc) {
        String[] parsed = imgSrc.split("://");

        if (parsed.length <= 1) {
            throw new RuntimeException("invalid image path");
        }

        parsed = parsed[1].split("/");

        if (parsed.length < 6) {
            throw new RuntimeException("invalid image path");
        }

        return parsed;
    }

    /**
     * image, video 파일 삭제
     * 특정 기간(N년) 동안 미참조 이미지,비디오 파일 삭제
     * (get lastModifiedTime method): https://www.tabnine.com/code/java/methods/com.jcraft.jsch.SftpATTRS/getMTime
     */
    public void cleanOldestImageFile() throws Exception {
        sftpUtil.checkAngDeleteFile(sFtpConfig.getDirectory() + "/" + ConstUtil.SFTP_IMAGE_TYPE, batchConfig.getOldFileCleanPeriod());
    }
}
