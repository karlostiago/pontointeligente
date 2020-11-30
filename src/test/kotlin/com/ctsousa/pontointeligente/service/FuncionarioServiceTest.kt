package com.ctsousa.pontointeligente.service

import com.ctsousa.pontointeligente.entity.Empresa
import com.ctsousa.pontointeligente.entity.Funcionario
import com.ctsousa.pontointeligente.enums.PerfilEnum
import com.ctsousa.pontointeligente.repository.FuncionarioRepository
import com.ctsousa.pontointeligente.util.SenhaUtils
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class FuncionarioServiceTest  {

    @MockBean
    private val funcionarioRepository: FuncionarioRepository? = null

    @Autowired
    private val funcionarioService: FuncionarioService? = null

    private val email: String = "email@email.com"
    private val cpf: String = "34234855948"
    private val id: Long = 1L

    @Before
    @Throws(Exception::class)
    fun setup() {
        BDDMockito
                .given(funcionarioRepository?.save(Mockito.any(Funcionario::class.java)))
                .willReturn((funcionario()))

        BDDMockito
                .given(funcionarioRepository?.findByEmail(email))
                .willReturn(funcionario())

        BDDMockito
                .given(funcionarioRepository?.buscaPorID(id))
                .willReturn(funcionario())

        BDDMockito
                .given(funcionarioRepository?.findByCpf(cpf))
                .willReturn(funcionario())
    }

    @Test
    fun deveSalvarFuncionario() {
        val funcionario: Funcionario? = funcionarioService?.salvar(funcionario())

        Assertions
            .assertThat(funcionario)
            .isNotNull
    }

    @Test
    fun deveBuscarFuncionarioPorCpf() {
        val funcionario: Funcionario? = funcionarioService?.buscarPorCpf(cpf)

        Assertions
            .assertThat(funcionario)
            .isNotNull
    }

    @Test
    fun deveBuscarFuncionarioPorEmail() {
        val funcionario: Funcionario? = funcionarioService?.buscarPorEmail(email)

        Assertions
            .assertThat(funcionario)
            .isNotNull
    }

    @Test
    fun deveBuscarFuncionarioPorID() {
        val funcionario: Funcionario? = funcionarioService?.buscarPorID(id)

        Assertions
            .assertThat(funcionario)
            .isNotNull
    }

    private fun funcionario(): Funcionario {
        val empresa : Empresa = Empresa(1L, "razao social", "cnpj")
        return Funcionario(1L, "funcionario", email, SenhaUtils().gerarBcrypt("123456"),
                    cpf, PerfilEnum.ROLE_USUARIO, empresa)
    }
}