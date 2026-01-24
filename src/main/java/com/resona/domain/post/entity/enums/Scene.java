package com.resona.domain.post.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Scene {
    COMMUTE("출퇴근길"),
    RAIN("비오는 날"),
    DAWN("새벽 감성"),
    TRAVEL("산책/여행"),
    FOCUS("집중/작업"),
    EXERCISE("운동/활동"),
    OTHER("기타");

    private final String description;
}