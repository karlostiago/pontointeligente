package com.ctsousa.pontointeligente.dto

data class EmpresaDTO (
     val razaoSocial: String,
     val cnpj: String,
     val id: Long? = null
)