package com.ctsousa.pontointeligente.service

import com.ctsousa.pontointeligente.entity.Empresa
import com.ctsousa.pontointeligente.repository.EmpresaRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class EmpresaServiceTest {

    @Autowired
    val empresaService: EmpresaService? = null;

    @MockBean
    val empresaRepository: EmpresaRepository? = null;

    private val CNPJ = "51463645000100";

    @Test
    @DisplayName("deve buscar uma empresa por cnpj")
    fun deveBuscarEmpresaPorCnpj() {
        BDDMockito
                .given(empresaRepository?.findByCnpj(CNPJ))
                .willReturn(empresa());

        BDDMockito
                .given(empresaRepository?.save(empresa()))
                .willReturn(empresa());

        val empresa: Empresa? = empresaService?.buscarPorCnpj(CNPJ);
        Assertions.assertThat(empresa).isNotNull;
    }

    @Test
    @DisplayName("deve salvar uma empresa")
    fun deveSalvarEmpresa() {
        BDDMockito
                .given(empresaRepository?.findByCnpj(CNPJ))
                .willReturn(empresa());

        BDDMockito
                .given(empresaRepository?.save(empresa()))
                .willReturn(empresa());

        val empresa: Empresa? = empresaService?.salvar(empresa());
        Assertions.assertThat(empresa).isNotNull;
    }

    private fun empresa(): Empresa = Empresa(1L, "Razão Social", CNPJ);
}