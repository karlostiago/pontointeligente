package com.ctsousa.pontointeligente.controllers

import com.ctsousa.pontointeligente.dto.LancamentoDTO
import com.ctsousa.pontointeligente.entity.Empresa
import com.ctsousa.pontointeligente.entity.Funcionario
import com.ctsousa.pontointeligente.entity.Lancamento
import com.ctsousa.pontointeligente.enums.PerfilEnum
import com.ctsousa.pontointeligente.enums.TipoEnum
import com.ctsousa.pontointeligente.service.FuncionarioService
import com.ctsousa.pontointeligente.service.LancamentoService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.text.SimpleDateFormat
import java.util.*
import kotlin.jvm.Throws

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class LancamentoControllerTest {

    @Autowired
    private val mvc: MockMvc? = null

    @MockBean
    private val lancamentoService: LancamentoService? = null

    @MockBean
    private val funcionarioService: FuncionarioService? = null

    private val url: String = "/api/lancamentos"
    private val funcionarioID: Long = 1L
    private val lancamentoID: Long = 1L
    private val empresaID: Long = 1L
    private val tipo: String = TipoEnum.INICIO_JORNADA_TRABALHO.name
    private val data: Date = Date()

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    @Test
    @Throws(Exception::class)
    fun deveCasdatrarLancamento() {
        val lancamento: Lancamento = getLancamento()

        BDDMockito
            .given<Funcionario>(funcionarioService?.buscarPorID(funcionarioID))
            .willReturn(getFuncionario())

        BDDMockito
            .given(lancamentoService?.salvar(getLancamento()))
            .willReturn(lancamento)

        mvc!!.perform(MockMvcRequestBuilders.post(url)
                .content(jsonRequest())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.tipo").value(tipo))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.data").value(dateFormat.format(data)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.funcionario").value(funcionarioID.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").isEmpty)
    }

    @Test
    fun naoDeveCadastrarLancamentoComFuncionarioInvalido() {
        BDDMockito
            .given<Funcionario>(funcionarioService?.buscarPorID(funcionarioID))
            .willReturn(null)

        mvc!!.perform(MockMvcRequestBuilders.post(url)
                .content(jsonRequest())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").value("Funcionário não encontrado. ID $funcionarioID inexistente"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty)
    }

    @Test
    fun deveRemoverLancamento() {
        BDDMockito
            .given<Lancamento>(lancamentoService?.buscarPorId(lancamentoID))
            .willReturn(getLancamento())

        mvc!!.perform(MockMvcRequestBuilders.delete("$url/$lancamentoID")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)
    }

    private fun getEmpresa(): Empresa
        = Empresa(empresaID, "Razão social", "01.123.456/0001-92")

    private fun getFuncionario(): Funcionario
        = Funcionario(funcionarioID, "Funcionario", "email@email.com", "123456", "012.346.45-78", PerfilEnum.ROLE_USUARIO, getEmpresa(), 0.0, 0F, 0F)

    private fun getLancamento(): Lancamento
        = Lancamento(lancamentoID, data, TipoEnum.valueOf(tipo), getFuncionario(), "Descrição", "1.243,4.345")

    private fun jsonRequest(): String {
        val lancamentoDTO: LancamentoDTO
            = LancamentoDTO(lancamentoID.toString(), dateFormat.format(data), tipo, "1", "Descrição", "Localização")

        val mapper = ObjectMapper()
        return mapper.writeValueAsString(lancamentoDTO)
    }
}