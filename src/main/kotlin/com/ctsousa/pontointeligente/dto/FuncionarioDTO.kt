package com.ctsousa.pontointeligente.dto

data class FuncionarioDTO (
    val id: String? = null,
    val nome: String,
    val email: String,
    val senha: String,
    val valorHora: String,
    val qtdHorasTralhadoDia: String
)