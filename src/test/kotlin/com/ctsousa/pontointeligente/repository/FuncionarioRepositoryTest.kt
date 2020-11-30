package com.ctsousa.pontointeligente.repository

import com.ctsousa.pontointeligente.entity.Empresa
import com.ctsousa.pontointeligente.entity.Funcionario
import com.ctsousa.pontointeligente.enums.PerfilEnum
import com.ctsousa.pontointeligente.util.SenhaUtils
import org.assertj.core.api.Assertions
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FuncionarioRepositoryTest {

    @Autowired
    val testEntityManager : TestEntityManager? = null

    @Autowired
    val funcionarioRepository : FuncionarioRepository? = null

    @Test
    fun deveBuscarFuncionarioPorID() {
        val empresa = testEntityManager?.persist(empresa())
        val funcionario = testEntityManager?.persist(funcionario(empresa!!))
        val resultado = funcionarioRepository?.buscaPorID(funcionario!!.id!!)

        Assertions
            .assertThat(resultado)
            .isNotNull
    }

    private fun empresa(): Empresa {
        return Empresa(null, "razao social", "cnpj")
    }

    private fun funcionario(empresa: Empresa): Funcionario {
        return Funcionario(null, "funcionario", "email", SenhaUtils().gerarBcrypt("123456"),
                "123.456.789-10", PerfilEnum.ROLE_USUARIO, empresa)
    }
}