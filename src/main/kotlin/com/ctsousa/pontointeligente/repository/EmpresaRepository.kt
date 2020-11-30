package com.ctsousa.pontointeligente.repository

import com.ctsousa.pontointeligente.entity.Empresa
import org.springframework.data.jpa.repository.JpaRepository

interface EmpresaRepository : JpaRepository<Empresa, Long> {

    fun findByCnpj(cnpj: String) : Empresa?;
}