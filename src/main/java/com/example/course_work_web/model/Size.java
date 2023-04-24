package com.example.course_work_web.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Size {
    XS(33), S(36), M(39), L(42), XL(45);

    private final int maxSize;

    Size(int maxSize) {
        this.maxSize = maxSize;
    }
}
