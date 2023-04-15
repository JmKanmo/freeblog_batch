package com.service.freeblog_batch.web.util;

public class BatchUtil {
    public static int hashCode(Object... args) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Object arg : args) {
            stringBuilder.append(arg);
        }
        return stringBuilder.toString().hashCode();
    }
}
