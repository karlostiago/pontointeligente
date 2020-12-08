package com.ctsousa.pontointeligente.exception.handler

import com.ctsousa.pontointeligente.exception.GlobalErrorException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.NoHandlerFoundException
import java.time.Instant
import java.time.format.DateTimeFormatter

@RestControllerAdvice
class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException::class)
    fun handleNotFoundException(ex: NoHandlerFoundException): ResponseEntity<GlobalErrorException> {
        val status: HttpStatus = HttpStatus.NOT_FOUND
        val headers: HttpHeaders = HttpHeaders.EMPTY
        val error = GlobalErrorException(404, "Não conseguimos encontrar a página que procura.", DateTimeFormatter.ISO_INSTANT.format(Instant.now()))
        return ResponseEntity(error, headers, status)
    }
}