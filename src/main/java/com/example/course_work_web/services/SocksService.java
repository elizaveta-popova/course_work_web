package com.example.course_work_web.services;

import com.example.course_work_web.model.Color;
import com.example.course_work_web.model.Size;
import com.example.course_work_web.model.Sock;
import com.example.course_work_web.model.SockDto;

public interface SocksService {

    Sock addSocks(SockDto sockDto);
    Sock issueSock(SockDto sockDto);

    int getAllSocks(Color color, Size size, Integer cottonMin, Integer cottonMax);

    void deleteSocks(SockDto sockDto);

}
