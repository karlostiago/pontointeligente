package com.ctsousa.pontointeligente.repository

import com.ctsousa.pontointeligente.entity.Funcionario
import com.ctsousa.pontointeligente.entity.Lancamento
import com.ctsousa.pontointeligente.repository.query.LancamentoRepositoryQuery
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface LancamentoRepository : JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery {

    fun findByFuncionario(funcionario: Funcionario, pageable: Pageable): Page<Lancamento>
}