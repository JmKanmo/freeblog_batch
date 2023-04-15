package com.service.freeblog_batch.web.domain.post;

import com.service.freeblog_batch.web.util.ConstUtil;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class PostView implements Serializable {
    private static final long serialVersionUID = ConstUtil.SERIAL_VERSION_ID;
    private Long postId;
    private Long blogId;
    private Long view;
}
