package com.miniautorizador.miniautorizador.controller;

import com.miniautorizador.miniautorizador.request.CriarCartaoRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import com.miniautorizador.miniautorizador.service.CriarCartaoService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/cartoes")
@Slf4j
public class CartaoController {

    @Autowired
    private CriarCartaoService criarCartaoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?>  criarCartao(
            @Valid @RequestBody final CriarCartaoRequest criarCartaoRequest) {
        try {
            return criarCartaoService.verificarStatusCartao(criarCartaoRequest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    @GetMapping("/{numeroCartao}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?>  obterSaldo(
            @Valid @PathVariable final String numeroCartao) {
        try {
            return criarCartaoService.varificarCartao(numeroCartao);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
