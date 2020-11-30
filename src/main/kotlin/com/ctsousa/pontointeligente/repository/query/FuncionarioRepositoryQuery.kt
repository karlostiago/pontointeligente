package com.ctsousa.pontointeligente.repository.query

import com.ctsousa.pontointeligente.entity.Funcionario

interface FuncionarioRepositoryQuery {

    fun buscaPorID(id: Long) : Funcionario?
}