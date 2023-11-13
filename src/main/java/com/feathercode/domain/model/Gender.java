package com.feathercode.domain.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gender {

    MAN("남자"),
    WOMAN("여자");

    @JsonValue
    private final String name;
}
