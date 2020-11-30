package com.ctsousa.pontointeligente.dto

data class LancamentoDTO (
    val id: String? = null,
    val data: String,
    val tipo: String? = null,
    val funcionario: String? = null,
    val descricao: String? = null,
    val localizacao: String? = null,
)