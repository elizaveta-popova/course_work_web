package com.example.course_work_web.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Size {
    XS(33), S(36), M(39), L(42), XL(45);

    private final int maxSize;

    Size(int maxSize) {
        this.maxSize = maxSize;
    }
    @JsonCreator
    public static Size convertSize (int value) {
        for (Size size : Size.values()) {
            if (value == size.maxSize) {
                return size;
            }
        }
        throw new RuntimeException("Такого размера нет.");
    }
}
