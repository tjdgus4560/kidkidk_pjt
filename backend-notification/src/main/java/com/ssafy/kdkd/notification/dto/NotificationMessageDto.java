package com.ssafy.kdkd.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationMessageDto {
    private String subId;
    private String pubName;
    private String title;
    private String content;
    private String require;
    private Long childId;
    private int amount;

}
