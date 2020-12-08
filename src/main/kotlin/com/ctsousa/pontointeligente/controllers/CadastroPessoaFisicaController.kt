package com.ctsousa.pontointeligente.controllers

import com.ctsousa.pontointeligente.dto.CadastroPessoaFisicaDTO
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
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/cadastro-pessoa-fisica")
class CadastroPessoaFisicaController(override val empresaService: EmpresaService, override val funcionarioService: FuncionarioService) : AbstractCadastroPessoaController(empresaService, funcionarioService) {

    @PostMapping
    fun salvar(@Valid @RequestBody cadastroPessoaFisicaDTO: CadastroPessoaFisicaDTO, result: BindingResult):
        ResponseEntity<Response<CadastroPessoaFisicaDTO>>{

        val response: Response<CadastroPessoaFisicaDTO> = Response<CadastroPessoaFisicaDTO>()

        val cnpj: String = cadastroPessoaFisicaDTO.cnpj
        val cpf: String = cadastroPessoaFisicaDTO.cpf
        val email: String = cadastroPessoaFisicaDTO.email

        validarDadosExistentes(cnpj, cpf, email, true, result)

        if(result.hasErrors()) {
            for(error in result.allErrors) {
                response.errors.add(error.defaultMessage.toString())
            }
            return ResponseEntity.badRequest().body(response)
        }

        var funcionario: Funcionario = converterParaFuncionarioDTO(cadastroPessoaFisicaDTO, empresa!!)
        funcionario = funcionarioService.salvar(funcionario)

        response.data = converterParaCadastroPessoaFisicaDTO(funcionario, empresa!!)

        return ResponseEntity.ok(response)
    }

    private fun converterParaFuncionarioDTO(cadastroPessoaFisicaDTO: CadastroPessoaFisicaDTO, empresa: Empresa): Funcionario {
        return Funcionario(cadastroPessoaFisicaDTO.id?.toLong(), cadastroPessoaFisicaDTO.nome, cadastroPessoaFisicaDTO.email,
                SenhaUtils().gerarBcrypt(cadastroPessoaFisicaDTO.senha), cadastroPessoaFisicaDTO.cpf,
                PerfilEnum.ROLE_ADMIN, empresa, cadastroPessoaFisicaDTO.valorHora?.toDouble(),
                cadastroPessoaFisicaDTO.qtdHorasTralhadoDia?.toFloat(),
                cadastroPessoaFisicaDTO.qtdHorasAlmoco?.toFloat())
    }

    private fun converterParaCadastroPessoaFisicaDTO(funcionario: Funcionario, empresa: Empresa): CadastroPessoaFisicaDTO {
        return CadastroPessoaFisicaDTO(funcionario.id.toString(), funcionario.nome, funcionario.email, "", funcionario.cpf,
            empresa.cnpj.toString(), empresa.id.toString(), funcionario.valorHora.toString(), funcionario.qtdHorasTralhadoDia.toString(),
            funcionario.qtdHorasTralhadoDia.toString())
    }
}