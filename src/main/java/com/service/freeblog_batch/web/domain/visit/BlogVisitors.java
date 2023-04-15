package com.service.freeblog_batch.web.domain.visit;

import com.service.freeblog_batch.web.util.ConstUtil;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * "blog-visitors-count:{}": {
 * "visitors" : {} // (로그인/비로그인) 사용자 접속 정보(id)를 담은 set
 * todayVisitors: {} // 1일 배치 기준으로 초기화
 * yesterdayVisitors: {} // 1일 배치 기준으로 업데이트
 * totalVisitors: {} // 1일 배치 기준으로 업데이트
 * <p>
 * "today-view": {} // 1일 배치 기준으로 초기화
 * ,"yesterday-view": {} // 1일 배치 기준으로 업데이트
 * ,"total-view": {} // 1일 배치 기준으로 업데이트
 * }
 */
@Data
@Builder
public class BlogVisitors implements Serializable {
    private static final long serialVersionUID = ConstUtil.SERIAL_VERSION_ID;

    private final Set<Integer> visitorSet; // 중복 방문을 막기 위한 set
    private long todayVisitors; // 오늘 방문자 수
    private long yesterdayVisitors; // 어제 방문자 수
    private long totalVisitors; // 전체 방문자 수

    private long todayViews; // 오늘 방문 횟수

    private long yesterdayViews; // 어제 방문 횟수

    private long totalViews; // 전체 방문 횟수

    public void update() {
        /**
         * 어제 방문자 = 오늘 방문자
         * 전체 방문자 += 오늘 방문자
         * 오늘 방문자 = 0
         */
        this.yesterdayVisitors = this.todayVisitors;
        this.totalVisitors += this.todayVisitors;
        this.todayVisitors = 0;
    }
}
