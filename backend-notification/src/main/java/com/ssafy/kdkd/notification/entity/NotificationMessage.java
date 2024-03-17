package com.ssafy.kdkd.notification.entity;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationMessage {
    private String subId;
    private String pubName;
    private String title;
    private String content;
    private String require;
    private Long childId;
    private int amount;

    private String key;
    private boolean isRead;
}
