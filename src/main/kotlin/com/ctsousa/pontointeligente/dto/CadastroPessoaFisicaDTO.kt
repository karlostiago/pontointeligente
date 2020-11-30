package com.ctsousa.pontointeligente.dto

import org.hibernate.validator.constraints.Length
import org.hibernate.validator.constraints.br.CNPJ
import org.hibernate.validator.constraints.br.CPF
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class CadastroPessoaFisicaDTO (
        val id: String,

        @NotEmpty(message = "Nome não pode ser vázio")
        @Length(min = 3, max = 100, message = "Nome deve conter entre 3 e 100 caracteres")
        val nome: String,

        @NotEmpty(message = "Email não pode ser vázio")
        @Length(min = 3, max = 100, message = "Email deve conter entre 3 e 100 caracteres")
        @Email(message = "Email inválid")
        val email: String,

        @NotEmpty(message = "Senha não pode ser vázio")
        val senha: String,


        @NotEmpty(message = "CPF não pode ser vázio")
        @CPF(message = "CPF inválido")
        val cpf: String = "",

        @NotEmpty(message = "CNPJ não pode ser vázio")
        @CNPJ(message = "CNPJ inválido")
        val cnpj: String = "",

        val empresa: String,
        val valorHora: String,
        val qtdHorasTralhadoDia: String,
        val qtdHorasAlmoco: String
)