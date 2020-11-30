package com.ctsousa.pontointeligente.repository

import com.ctsousa.pontointeligente.entity.Funcionario
import com.ctsousa.pontointeligente.repository.query.FuncionarioRepositoryQuery
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*
import javax.persistence.LockModeType
import javax.persistence.NamedQuery

interface FuncionarioRepository : JpaRepository<Funcionario, Long>, FuncionarioRepositoryQuery {

    fun findByEmail(email: String): Funcionario?

    fun findByCpf(cpf: String): Funcionario?
}