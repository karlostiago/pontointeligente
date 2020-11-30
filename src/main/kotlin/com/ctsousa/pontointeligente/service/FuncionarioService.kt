package com.ctsousa.pontointeligente.service

import com.ctsousa.pontointeligente.entity.Funcionario

interface FuncionarioService {

    fun salvar(funcionario: Funcionario) : Funcionario

    fun buscarPorCpf(cpf: String) : Funcionario?

    fun buscarPorEmail(email: String) : Funcionario?

    fun buscarPorID(id: Long) : Funcionario?
}