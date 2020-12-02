package com.ctsousa.pontointeligente.controllers

import com.ctsousa.pontointeligente.dto.LancamentoDTO
import com.ctsousa.pontointeligente.entity.Funcionario
import com.ctsousa.pontointeligente.entity.Lancamento
import com.ctsousa.pontointeligente.enums.TipoEnum
import com.ctsousa.pontointeligente.response.Response
import com.ctsousa.pontointeligente.service.FuncionarioService
import com.ctsousa.pontointeligente.service.LancamentoService
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.*
import java.text.SimpleDateFormat
import javax.validation.Valid

@RestController
@RequestMapping("/api/lancamentos")
class LancamentoController(val lancamentoService: LancamentoService, val funcionarioService: FuncionarioService) {

    @Value("\${paginacao.quantidade_por_pagina}")
    private val quantidadePorPagina: Int = 15

    private var funcionario: Funcionario? = null

    private val dateFormat = SimpleDateFormat("yyy-MM-dd HH:mm:ss")

    @PostMapping
    fun adicionar(@Valid @RequestBody lancamentoDTO: LancamentoDTO, result: BindingResult) : ResponseEntity<Response<LancamentoDTO>> {
        val response: Response<LancamentoDTO> = Response<LancamentoDTO>()
        validarFuncionario(lancamentoDTO, result)

        if(result.hasErrors()) {
            for(error in result.allErrors) {
                response.errors.add(error.defaultMessage.toString())
            }
            return ResponseEntity.badRequest().body(response)
        }

        val lancamento: Lancamento = converterDTOParaLancamento(lancamentoDTO, result)
        lancamentoService.salvar(lancamento)
        response.data = converterLancamentoParaLancamentoDTO(lancamento)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{id}")
    fun listarPorId(@PathVariable("id") id: Long): ResponseEntity<Response<LancamentoDTO>> {
        val response: Response<LancamentoDTO> = Response<LancamentoDTO>()
        val lancamento: Lancamento? = lancamentoService.buscarPorId(id)

        if(lancamento == null) {
            response.errors.add("Lançamento não encontrado para o id $id")
            return ResponseEntity.badRequest().body(response)
        }

        response.data = converterLancamentoParaLancamentoDTO(lancamento)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/funcionario/{id}")
    fun listarPorFuncionario(
            @PathVariable("id") id: Long,
            @RequestParam(value = "pag", defaultValue = "0") pag: Int,
            @RequestParam(value = "ord", defaultValue = "id") ord: String,
            @RequestParam(value = "dir", defaultValue = "DESC") dir: String): ResponseEntity<Response<Page<LancamentoDTO>>>
    {
        val response: Response<Page<LancamentoDTO>> = Response<Page<LancamentoDTO>>()

        val pageRequest: PageRequest = PageRequest.of(pag, quantidadePorPagina, Sort.Direction.valueOf(dir), ord)

        val funcionario = funcionarioService.buscarPorID(id)
        val lancamentos: Page<Lancamento> = lancamentoService.buscarPorFuncionario(funcionario!!, pageRequest)

        val lancamentosDto: Page<LancamentoDTO>
            = lancamentos.map { lancamento -> converterLancamentoParaLancamentoDTO(lancamento) }

        response.data = lancamentosDto
        return ResponseEntity.ok(response)
    }

    @PutMapping("/{id}")
    fun atualizar(@PathVariable("id") id: Long, @Valid @RequestBody lancamentoDTO: LancamentoDTO, bindingResult: BindingResult): ResponseEntity<Response<LancamentoDTO>> {
        val response: Response<LancamentoDTO> = Response<LancamentoDTO>()
        validarFuncionario(lancamentoDTO, bindingResult)

        lancamentoDTO.id = id.toString()
        val lancamento: Lancamento = converterDTOParaLancamento(lancamentoDTO, bindingResult)

        if(bindingResult.hasErrors()) {
            for(error in bindingResult.allErrors) {
                response.errors.add(error.defaultMessage.toString())
                return ResponseEntity.badRequest().body(response)
            }
        }

        lancamentoService.salvar(lancamento)
        response.data = converterLancamentoParaLancamentoDTO(lancamento)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{id}")
    fun remover(@PathVariable("id") id: Long): ResponseEntity<Response<String>> {
        val response: Response<String> = Response<String>()
        val lancamento: Lancamento? = lancamentoService.buscarPorId(id)

        if(lancamento == null) {
            response.errors.add("Erro ao remover lançamento. Registro não encontrado para o id $id")
            return ResponseEntity.badRequest().body(response)
        }

        lancamentoService.remover(id)
        return ResponseEntity.ok(Response<String>())
    }

    private fun converterLancamentoParaLancamentoDTO(lancamento: Lancamento): LancamentoDTO {
        return LancamentoDTO(lancamento.id.toString(), dateFormat.format(lancamento.data), lancamento.tipo.toString(), lancamento.funcionario.id.toString(), lancamento.descricao, lancamento.localizacao)
    }

    private fun converterDTOParaLancamento(lancamentoDTO: LancamentoDTO, result: BindingResult): Lancamento {
        if(lancamentoDTO.id != null) {
            val lancamento: Lancamento? = lancamentoService.buscarPorId(lancamentoDTO.id!!.toLong())
            if(lancamento == null) {
                result.addError(ObjectError("lancamento","Lançamento não encontrado."))
            }
        }

        return Lancamento(lancamentoDTO.id!!.toLong(), dateFormat.parse(lancamentoDTO.data), TipoEnum.valueOf(lancamentoDTO.tipo!!),
            funcionario!!, lancamentoDTO.descricao, lancamentoDTO.localizacao)
    }

    private fun validarFuncionario(lancamentoDTO: LancamentoDTO, result: BindingResult) {
        if(lancamentoDTO.funcionario == null) {
            result.addError(ObjectError("funcionario", "Funcionário não informado"))
            return
        }

        funcionario = funcionarioService.buscarPorID(lancamentoDTO.funcionario.toLong())
        if(funcionario == null) {
            result.addError(ObjectError("funcionario", "Funcionário não encontrado. ID ${lancamentoDTO.funcionario.toLong()} inexistente"))
        }
    }
}