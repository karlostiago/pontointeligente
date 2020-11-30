package com.ctsousa.pontointeligente.util

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class SenhaUtilsTest {

    private val SENHA = "123456";
    private val bCryptEncoder = BCryptPasswordEncoder();

    @Test
    fun deveGerarHashSenha() {
        val hash = SenhaUtils().gerarBcrypt(SENHA)

        Assertions
            .assertThat(bCryptEncoder.matches(SENHA, hash))
            .isTrue;
    }
}