package com.ctsousa.pontointeligente.util

class SenhaUtils {

    fun gerarBcrypt(senha: String): String = senha /*BCryptPasswordEncoder().encode(senha)*/;
}