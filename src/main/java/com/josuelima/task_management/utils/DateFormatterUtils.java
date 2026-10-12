package com.josuelima.task_management.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormatterUtils {

    public static LocalDateTime dataFormatada(LocalDateTime data){
        return LocalDateTime.parse(data.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
    }
}
