package com.resona.domain.post.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {
    BALLAD("발라드/감성"),
    DANCE("댄스/팝"),
    HIPHOP("힙합/알앤비"),
    INDIE("인디/포크"),
    JAZZ("재즈/클래식"),
    ROCK("락/밴드"),
    OTHER("기타");

    private final String description;
}