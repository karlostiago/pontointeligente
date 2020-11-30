package com.ctsousa.pontointeligente.repository.query.impl

import com.ctsousa.pontointeligente.entity.Funcionario
import com.ctsousa.pontointeligente.repository.query.FuncionarioRepositoryQuery
import javax.persistence.EntityManager
import javax.persistence.TypedQuery

class FuncionarioRepositoryQueryImpl(private val entityManager: EntityManager) : FuncionarioRepositoryQuery {

    override fun buscaPorID(id: Long): Funcionario? {
        val sql = StringBuilder("SELECT f FROM Funcionario f WHERE f.id = :id ")

        val query: TypedQuery<Funcionario> = entityManager.createQuery(sql.toString(), Funcionario::class.java)
        query.setParameter("id", id)

        return try {
            query.singleResult
        }
        catch (ex: Exception) {
            null
        }
    }
}