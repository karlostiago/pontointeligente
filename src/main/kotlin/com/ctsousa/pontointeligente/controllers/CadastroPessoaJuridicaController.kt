package com.ctsousa.pontointeligente.controllers

import com.ctsousa.pontointeligente.dto.CadastroPessoaJuridicaDTO
import com.ctsousa.pontointeligente.entity.Empresa
import com.ctsousa.pontointeligente.entity.Funcionario
import com.ctsousa.pontointeligente.enums.PerfilEnum
import com.ctsousa.pontointeligente.response.Response
import com.ctsousa.pontointeligente.service.EmpresaService
import com.ctsousa.pontointeligente.service.FuncionarioService
import com.ctsousa.pontointeligente.util.SenhaUtils
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/cadastro-pessoa-juridica")
class CadastroPessoaJuridicaController(override val empresaService: EmpresaService, override val funcionarioService: FuncionarioService) : AbstractCadastroPessoaController(empresaService, funcionarioService) {

    @PostMapping
    fun salvar(@Valid @RequestBody cadastroPessoaJuridicaDTO: CadastroPessoaJuridicaDTO, result: BindingResult):
            ResponseEntity<Response<CadastroPessoaJuridicaDTO>> {

        val response: Response<CadastroPessoaJuridicaDTO> = Response<CadastroPessoaJuridicaDTO>()

        val cnpj: String = cadastroPessoaJuridicaDTO.cnpj
        val cpf: String = cadastroPessoaJuridicaDTO.cpf
        val email: String = cadastroPessoaJuridicaDTO.email

        validarDadosExistentes(cnpj, cpf, email, false, result)

        if(result.hasErrors()) {
            for(error in result.allErrors) {
                response.errors.add(error.defaultMessage.toString())
            }
            return ResponseEntity.badRequest().body(response)
        }

        empresaService.salvar(empresa!!)

        val funcionario: Funcionario = converterParaFuncionarioDTO(cadastroPessoaJuridicaDTO, empresa!!)
        funcionarioService.salvar(funcionario)

        response.data = converterParaCadastroPessoaJuridicaDTO(funcionario, empresa!!)

        return ResponseEntity.ok(response)
    }

    private fun converterParaCadastroPessoaJuridicaDTO(funcionario: Funcionario, empresa: Empresa): CadastroPessoaJuridicaDTO {
        return CadastroPessoaJuridicaDTO(funcionario.id.toString(), funcionario.nome, funcionario.email, "",
            funcionario.cpf, empresa.cnpj.toString(), empresa.razaoSocial)
    }

    private fun converterParaFuncionarioDTO(cadastroPessoaJuridicaDTO: CadastroPessoaJuridicaDTO, empresa: Empresa): Funcionario {
        return Funcionario(null, cadastroPessoaJuridicaDTO.nome, cadastroPessoaJuridicaDTO.email,
                SenhaUtils().gerarBcrypt(cadastroPessoaJuridicaDTO.senha), cadastroPessoaJuridicaDTO.cpf,
                PerfilEnum.ROLE_ADMIN, empresa)
    }

    private fun converterParaEmpresaDTO(cadastroPessoaJuridicaDTO: CadastroPessoaJuridicaDTO): Empresa
            = Empresa(null, cadastroPessoaJuridicaDTO.razaoSocial, cadastroPessoaJuridicaDTO.cnpj)

}