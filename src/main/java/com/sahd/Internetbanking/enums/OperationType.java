package com.sahd.Internetbanking.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum OperationType {
    WITHDRAWAL("W"),
    DEPOSIT("D");

    private final String code;
    private static final Map<String, OperationType> ENUM_MAP;

    static {
        ENUM_MAP = Arrays.stream(OperationType.values())
                .collect(Collectors.toMap(OperationType::getCode, op -> op));
    }

    OperationType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static OperationType get(String code) {
        return ENUM_MAP.get(code);
    }

}
