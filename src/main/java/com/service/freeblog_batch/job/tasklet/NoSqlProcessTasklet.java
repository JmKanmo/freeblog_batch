package com.service.freeblog_batch.job.tasklet;

import com.service.freeblog_batch.web.service.BatchRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class NoSqlProcessTasklet implements Tasklet {
    private final BatchRedisService batchRedisService;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        /**
         * TODO 서비스 별로 메소드 작성해서 일괄처리
         * 전 블로거 블로그 방문횟수 처리 (일일 방문자수, 전체 방문자수, 어제 방문자수)
         * 탈퇴한 회원, 블로그, 삭제 된 게시글 관련 redis 데이터 정말 삭제되었는지 확인 & 미삭제 시에 처리
         */
        System.out.println("no sql clean step done");
        return RepeatStatus.FINISHED;
    }
}
