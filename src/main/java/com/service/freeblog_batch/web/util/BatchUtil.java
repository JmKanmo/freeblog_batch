package com.service.freeblog_batch.web.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BatchUtil {
    public static int hashCode(Object... args) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Object arg : args) {
            stringBuilder.append(arg);
        }
        return stringBuilder.toString().hashCode();
    }

    public static String calcFileSizeCommand(String dir) {
        return "stat -c \"%x\" " + dir + "| cut -d ' ' -f 1-2";
    }

    public static LocalDateTime formatStrToLocalDateTime(String timeStr, String pattern) {
        return LocalDateTime.parse(timeStr, DateTimeFormatter.ofPattern(pattern));
    }
}
