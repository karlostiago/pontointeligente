package com.ctsousa.pontointeligente.repository.query.impl

import com.ctsousa.pontointeligente.entity.Funcionario
import com.ctsousa.pontointeligente.entity.Lancamento
import com.ctsousa.pontointeligente.repository.query.LancamentoRepositoryQuery
import java.lang.Exception
import javax.persistence.EntityManager
import javax.persistence.TypedQuery

class LancamentoRepositoryQueryImpl(private val entityManager: EntityManager) : LancamentoRepositoryQuery {

    override fun buscarPorId(id: Long): Lancamento? {
        val sql = StringBuilder("SELECT lancamento FROM Lancamento lancamento WHERE lancamento.id = :id ")

        val query: TypedQuery<Lancamento> = entityManager.createQuery(sql.toString(), Lancamento::class.java)
        query.setParameter("id", id)

        return try {
            query.singleResult
        }
        catch(ex : Exception) {
            null
        }
    }

    override fun buscarPorFuncionario(funcionario: Funcionario): Lancamento? {
        val sql = StringBuilder("SELECT lancamento FROM Lancamento lancamento WHERE lancamento.funcionario = :funcionario ")

        val query: TypedQuery<Lancamento> = entityManager.createQuery(sql.toString(), Lancamento::class.java)
        query.setParameter("funcionario", funcionario)

        return try {
            query.singleResult
        }
        catch(ex : Exception) {
            null
        }
    }
}