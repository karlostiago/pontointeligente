package com.ctsousa.pontointeligente.repository.query

import com.ctsousa.pontointeligente.entity.Funcionario
import com.ctsousa.pontointeligente.entity.Lancamento

interface LancamentoRepositoryQuery {

    fun buscarPorId(id: Long): Lancamento?

    fun buscarPorFuncionario(funcionario: Funcionario) : Lancamento?
}