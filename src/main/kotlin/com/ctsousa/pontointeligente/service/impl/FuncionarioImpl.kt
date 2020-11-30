package com.ctsousa.pontointeligente.service.impl

import com.ctsousa.pontointeligente.entity.Funcionario
import com.ctsousa.pontointeligente.repository.FuncionarioRepository
import com.ctsousa.pontointeligente.service.FuncionarioService
import org.springframework.stereotype.Service

@Service
class FuncionarioImpl(val funcionarioRepository: FuncionarioRepository) : FuncionarioService {
    override fun salvar(funcionario: Funcionario): Funcionario
        = funcionarioRepository.save(funcionario)

    override fun buscarPorCpf(cpf: String): Funcionario?
        = funcionarioRepository.findByCpf(cpf)

    override fun buscarPorEmail(email: String): Funcionario?
        = funcionarioRepository.findByEmail(email)

    override fun buscarPorID(id: Long): Funcionario?
        = funcionarioRepository.buscaPorID(id)
}