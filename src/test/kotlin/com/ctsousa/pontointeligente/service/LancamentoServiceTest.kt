package com.ctsousa.pontointeligente.service

import com.ctsousa.pontointeligente.entity.Empresa
import com.ctsousa.pontointeligente.entity.Funcionario
import com.ctsousa.pontointeligente.entity.Lancamento
import com.ctsousa.pontointeligente.enums.PerfilEnum
import com.ctsousa.pontointeligente.enums.TipoEnum
import com.ctsousa.pontointeligente.repository.LancamentoRepository
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.junit4.SpringRunner
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

@RunWith(SpringRunner::class)
@SpringBootTest
class LancamentoServiceTest {

    @MockBean
    private val lancamentoRepository: LancamentoRepository? = null

    @Autowired
    private val lancamentoService: LancamentoService? = null

    private val id: Long = 1L

    @Before
    @Throws(Exception::class)
    fun setup() {
        BDDMockito
            .given<Page<Lancamento>>(lancamentoRepository?.findByFuncionario(getFuncionario(), PageRequest.of(0, 10)))
            .willReturn(PageImpl(ArrayList<Lancamento>()))

        BDDMockito
                .given(lancamentoRepository?.buscarPorId(id))
                .willReturn(getLancamento())

        BDDMockito
                .given(lancamentoRepository?.save(Mockito.any(Lancamento::class.java)))
                .willReturn(getLancamento())
    }

    @Test
    fun deveBuscarLancamentoPorFuncionario() {
        val lancamento: Page<Lancamento>?
            = lancamentoService?.buscarPorFuncionario(getFuncionario(), PageRequest.of(0,10))

        Assertions
            .assertThat(lancamento)
            .isNotNull
    }

    @Test
    fun deveBuscarLancamentoPorID() {
        val lancamento: Lancamento? = lancamentoService?.buscarPorId(id)

        Assertions
            .assertThat(lancamento)
            .isNotNull
    }

    @Test
    fun deveSalvarLancamento() {
        val lancamento: Lancamento? = lancamentoService?.salvar(getLancamento())

        Assertions
            .assertThat(lancamento)
            .isNotNull
    }

    private fun getLancamento(): Lancamento
        = Lancamento(id, Date(), TipoEnum.INICIO_JORNADA_TRABALHO, getFuncionario())

    private fun getEmpresa(): Empresa
        = Empresa(id, "Empresa", "00.123.456/0007-89")

    private fun getFuncionario(): Funcionario
        = Funcionario(id, "Funcionario","funcionario@email.com", "123456", "12345678910", PerfilEnum.ROLE_USUARIO, getEmpresa(), 0.0, 0F, 0F)
}