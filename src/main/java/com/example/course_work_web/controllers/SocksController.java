package com.example.course_work_web.controllers;

import com.example.course_work_web.model.Color;
import com.example.course_work_web.model.Size;
import com.example.course_work_web.model.Sock;
import com.example.course_work_web.model.SockDto;
import com.example.course_work_web.services.SocksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
    @RequestMapping("/socks")
    @Tag(name = "Склад носков", description = "CRUD-операции и энпойнты для работы со складом.")
    public class SocksController {
    private final SocksService socksService;

    public SocksController(SocksService socksService) {
        this.socksService = socksService;
    }

    @PostMapping("")
    @Operation(summary = "Добавление партии носков", description = "Укажите цвет, процентное содержание хлопка, размер, количество пар носков")
    @ApiResponse(responseCode = "200", description = "Партия носков успешно добавлена", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Sock.class)))
    })
    public ResponseEntity<Void> addSocks(@RequestBody SockDto sockDto) {
        if (socksService.addSocks(new SockDto())) {
        return ResponseEntity.ok().build();

        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping
    @Operation(summary = "Отпустить партию носков", description = "Укажите цвет, процентное содержание хлопка, размер, количество пар носков")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Партия отпущена"),
            @ApiResponse(responseCode = "400", description = "Параметры запроса имеют некорректный формат"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка")
    })
    public ResponseEntity<Void> issueSock(@RequestBody SockDto sockDto) {
       if (socksService.issueSock(new SockDto())){
        return ResponseEntity.ok().build();
    }
        return ResponseEntity.notFound().build();
}

    @GetMapping("/getAll")
    @Operation(summary = "Получить все имеющиеся на складе носки")
    @ApiResponse(responseCode = "200", description = "Вывод всех носков", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Sock.class)))
    })
    public int getAllSocks(@RequestParam(required = false, name = "цвет") Color color,
                           @RequestParam(required = false, name = "размер") Size size,
                           @RequestParam(required = false, name = "минимальное содержание хлопка") Integer cottonMin,
                           @RequestParam(required = false, name = "максимальное содержание хлопка") Integer cottonMax) {

        return socksService.getAllSocks(color, size, cottonMin, cottonMax);
    }

    @DeleteMapping
    @Operation(summary = "Списать бракованные носки", description = "Необходимо указать цвет, процентное содержание хлопка, размер, а также количество пар носков для списания")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Партия списана", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Sock.class)))}),
            @ApiResponse(responseCode = "400", description = "Параметры запроса отсутствуют или имеют некорректный формат"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка, не зависящая от вызывающей стороны")

    })
    public ResponseEntity<Void> deleteSocks(@RequestBody SockDto sockDto) {
        if (socksService.deleteSocks(new SockDto())) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
