package com.example.course_work_web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SockDto {
    private Color color;
    private Size size;
    private int cottonPart;
    private int quantity;
}
