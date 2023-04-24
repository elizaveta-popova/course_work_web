package com.example.course_work_web.services;

import com.example.course_work_web.exceptions.InsufficientSockQuantityException;
import com.example.course_work_web.exceptions.InvalidSockRequestException;
import com.example.course_work_web.model.Color;
import com.example.course_work_web.model.Size;
import com.example.course_work_web.model.Sock;
import com.example.course_work_web.model.SockDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class SocksServiceImpl implements SocksService {
    private final ObjectMapper objectMapper;
    private final Map<Sock, Integer> socksStock = new HashMap<>();
    private final static String STORE_FILES = "recipes";

    public SocksServiceImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
      public boolean addSocks(SockDto sockDto) {
        validateRequest (sockDto);
        Sock sock = mapToSock(sockDto);
        if (socksStock.containsKey(sock)) {
            socksStock.put(sock, socksStock.get(sock) + sockDto.getQuantity());
        } else {
            socksStock.put(sock, sockDto.getQuantity());
        }
        return true;
    }

    private void validateRequest (SockDto sockDto) {
        if (sockDto.getColor() == null || sockDto.getSize() == null) {
            throw new InvalidSockRequestException("Запоните все поля.");
        }
        if (sockDto.getCottonPart() < 0 || sockDto.getCottonPart() >  100) {
            throw new InvalidSockRequestException("Процентное содержание хлопка должно находиться в диапазоне от 0 до 100.");
        }
        if (sockDto.getQuantity() <= 0) {
            throw new InvalidSockRequestException("Количество должно быть больше 0.");
        }
    }

    private Sock mapToSock (SockDto sockDto ) {
        return new Sock(sockDto.getColor(), sockDto.getSize(), sockDto.getCottonPart());
    }

    private SockDto mapToDto (Sock sock, int quantity) {
        SockDto sockDto = new SockDto();
        sockDto.setColor(sock.getColor());
        sockDto.setSize(sock.getSize());
        sockDto.setCottonPart(sock.getCottonPart());
        sockDto.setQuantity(quantity);
        return sockDto;
    }
    @Override
    public boolean issueSock (SockDto sockDto) {
        decreaseSockQuantity(sockDto, true);
        return true;
    }

    public boolean deleteSocks (SockDto sockDto) {
        decreaseSockQuantity (sockDto, false);
        return false;
    }

    private void decreaseSockQuantity (SockDto sockDto, boolean isIssue) {
        validateRequest(sockDto);
        Sock sock = mapToSock(sockDto);
        int sockQuantity = socksStock.getOrDefault(sock,0);
        if (sockQuantity >= sockDto.getQuantity()) {
            socksStock.put(sock, sockQuantity-sockDto .getQuantity());
        } else {
            throw new InsufficientSockQuantityException("Нет носков.");
        }
    }
    @Override
    public int getAllSocks (Color color, Size size, Integer cottonMin, Integer cottonMax) {
        int total = 0;
        for (Map.Entry <Sock, Integer> entry:socksStock.entrySet()) {
            if (color != null && !entry.getKey().getColor().equals(color)) {
                continue;
            }
            if (size != null && !entry.getKey().getSize().equals(size)) {
                continue;
            }
            if (cottonMin != null && entry.getKey().getCottonPart() > cottonMin) {
                continue;
            }
            if (cottonMax != null && entry.getKey().getCottonPart() < cottonMax) {
                continue;
            }
            total += entry.getValue();
            }
        return total;
    }

}
