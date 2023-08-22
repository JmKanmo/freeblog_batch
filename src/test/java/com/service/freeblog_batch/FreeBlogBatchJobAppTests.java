package com.service.freeblog_batch;

import com.service.freeblog_batch.config.batch.BatchConfig;
import com.service.freeblog_batch.config.sftp.SFtpConfig;
import com.service.freeblog_batch.web.util.BatchUtil;
import com.service.freeblog_batch.web.util.ConstUtil;
import com.service.freeblog_batch.web.util.sftp.SftpUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest
class FreeBlogBatchJobAppTests {
    @Autowired
    private SftpUtil sftpUtil;

    @Autowired
    private BatchConfig batchConfig;

    @Autowired
    private SFtpConfig sFtpConfig;

//    @Test
//    void contextLoads() throws Exception {
//        String dir = "/home/junmokang/jmservice/jmblog/images/520904174/1689522661606/2023-07-17/2c65a79c-c917-42d9-a501-086687c28f62.png";
//        String command = BatchUtil.calcFileSizeCommand(dir);
//        String result = sftpUtil.doCommand(command);
//        String[] splited = result.split(" ");
//        String time = splited[0] + " " + splited[1].substring(0, splited[1].lastIndexOf("."));
//        LocalDateTime convertedLocalDateTime = BatchUtil.formatStrToLocalDateTime(time, "yyyy-MM-dd HH:mm:ss");
//        Assertions.assertNotNull(time);
//        Assertions.assertNotNull(convertedLocalDateTime);
//    }

    @Test
    void directoryTraversal() throws Exception {
        // root 기준으로 모든 경로 순회
        // 순회 시에, 각 경로 대상으로 위 명령어를 통한 날짜 -> LocalDateTime 변환
        // 변환 된 LocalDateTime <-> 현재 날짜 비교
        // 기준 시간 GAP  <-> 설정 값을 넘는지 비교
        // 기준 시간 GAP <-> 설정 값보다 클 경우, 해당 경로를 대상으로 삭제 진행
        // TODO
        sftpUtil.checkAngDeleteFile(sFtpConfig.getDirectory() + "/" + ConstUtil.SFTP_IMAGE_TYPE, batchConfig.getOldFileCleanPeriod());
    }
}
