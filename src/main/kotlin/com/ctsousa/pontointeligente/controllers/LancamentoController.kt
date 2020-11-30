package com.ctsousa.pontointeligente.controllers

import com.ctsousa.pontointeligente.dto.LancamentoDTO
import com.ctsousa.pontointeligente.entity.Funcionario
import com.ctsousa.pontointeligente.service.FuncionarioService
import com.ctsousa.pontointeligente.service.LancamentoService
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigInteger
import javax.validation.Valid
import javax.xml.ws.Response

@RestController
@RequestMapping("/api/lancamentos")
class LancamentoController(val lancamentoService: LancamentoService, val funcionarioService: FuncionarioService) {

    @Value("\${paginacao.quantidade_por_pagina}")
    val quantidadePorPagina: Int = 15

    @PostMapping
    fun adicionar(@Valid @RequestBody lancamentoDTO: LancamentoDTO, result: BindingResult) : ResponseEntity<Response<LancamentoDTO>> {
//        val response: Response<LancamentoDTO> = Response<LancamentoDTO>()
//        validarFuncionario(lancamentoDTO, result)
        TODO()
    }

    private fun validarFuncionario(lancamentoDTO: LancamentoDTO, result: BindingResult) {
        if(lancamentoDTO.funcionario == null) {
            result.addError(ObjectError("funcionario", "Funcionário não informado"))
            return
        }

        val funcionario: Funcionario? = funcionarioService.buscarPorID(BigInteger(lancamentoDTO.funcionario).toLong())
        if(funcionario == null) {
            result.addError(ObjectError("funcionario", "Funcionário não encontrado. ID inexistente"))
        }
    }
}