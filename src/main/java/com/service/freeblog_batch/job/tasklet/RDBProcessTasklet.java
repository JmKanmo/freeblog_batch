package com.service.freeblog_batch.job.tasklet;

import com.service.freeblog_batch.web.service.BatchJdbcService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RDBProcessTasklet implements Tasklet {
    private final BatchJdbcService batchJdbcService;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        System.out.println("rdb step done");
        /**
         * TODO 서비스 별로 메소드 작성해서 일괄처리
         * RDB 데이터 처리
         * user : 정지, 탈퇴 회원
         * blog: 삭제 된 블로그 처리
         * category: 삭제 된 블로그의 카테고리 처리
         * post: 삭제 된 포스트 처리
         * tag: (삭제 된 포스트의 태그 처리)
         * comment: (삭제 된 게시글의 댓글 처리)
         */
        return RepeatStatus.FINISHED;
    }
}
