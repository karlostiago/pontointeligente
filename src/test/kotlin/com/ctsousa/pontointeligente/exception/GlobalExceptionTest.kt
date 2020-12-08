package com.ctsousa.pontointeligente.exception

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.RequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class GlobalExceptionTest {

    @Autowired
    private val mockMvc: MockMvc? = null

    @Test
    @WithMockUser(username = "admin", password = "123456")
    fun deveRetornar404PaginaNaoEncontrada() {
        val builder: RequestBuilder = getRequestBuilder("/not-found")

        mockMvc?.perform(builder)
               ?.andExpect(MockMvcResultMatchers.status().isNotFound)
               ?.andExpect(MockMvcResultMatchers.jsonPath("$.code").value("404"))
               ?.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Não conseguimos encontrar a página que procura."))
    }

    private fun getRequestBuilder(url: String): RequestBuilder {
        return MockMvcRequestBuilders
                .get(url)
                .accept(MediaType.APPLICATION_JSON)
    }
}