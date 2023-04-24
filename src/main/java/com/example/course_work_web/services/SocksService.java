package com.example.course_work_web.services;

import com.example.course_work_web.model.Color;
import com.example.course_work_web.model.Size;
import com.example.course_work_web.model.SockDto;

public interface SocksService {

    boolean addSocks(SockDto sockDto);
    boolean issueSock(SockDto sockDto);

    int getAllSocks(Color color, Size size, Integer cottonMin, Integer cottonMax);

    boolean deleteSocks(SockDto sockDto);

}
