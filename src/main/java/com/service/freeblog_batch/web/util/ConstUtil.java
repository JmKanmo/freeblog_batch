package com.service.freeblog_batch.web.util;

public class ConstUtil {
    public static final long SERIAL_VERSION_ID = Long.MIN_VALUE;
    public static final String SFTP_IMAGE_TYPE = "images";
    public static final String SFTP_VIDEO_TYPE = "videos";
    public static final String SFTP_IMAGE_URL = "http://%s/%s"; // 추후에 https 설정 및 변경
    public static final int SFTP_PROFILE_THUMBNAIL_HASH = "profile-thumbnail".hashCode(); // (유저 썸네일, 댓글 썸네일, 게시글 썸네일)
    public static final int SFTP_POST_IMAGE_HASH = "post-image".hashCode();
    public static final int SFTP_COMMENT_IMAGE_HASH = "comment-image".hashCode();
}
