package com.ctsousa.pontointeligente.service

import com.ctsousa.pontointeligente.entity.Funcionario
import com.ctsousa.pontointeligente.entity.Lancamento
import org.springframework.data.domain.AbstractPageRequest
import org.springframework.data.domain.Page

interface LancamentoService {

    fun buscarPorFuncionario(funcionario: Funcionario, pageRequest: AbstractPageRequest): Page<Lancamento>

    fun buscarPorId(id: Long): Lancamento?

    fun salvar(lancamento: Lancamento): Lancamento

    fun remover(id: Long)
}