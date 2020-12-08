package com.ctsousa.pontointeligente.controllers

import com.ctsousa.pontointeligente.dto.FuncionarioDTO
import com.ctsousa.pontointeligente.entity.Funcionario
import com.ctsousa.pontointeligente.response.Response
import com.ctsousa.pontointeligente.service.FuncionarioService
import com.ctsousa.pontointeligente.util.SenhaUtils
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/funcionarios")
class FuncionarioController(val funcionarioService: FuncionarioService) {

    @PutMapping("/{id}")
    fun atualizar(@PathVariable("id") id: String, @Valid @RequestBody funcionarioDTO: FuncionarioDTO, result: BindingResult):
        ResponseEntity<Response<FuncionarioDTO>>{

        val response: Response<FuncionarioDTO> = Response<FuncionarioDTO>()
        val funcionario: Funcionario? = funcionarioService.buscarPorID(id.toLong())

        if(funcionario == null) {
            result.addError(ObjectError("funcionario", "Funcionário não encontrado."))
        }

        if(result.hasErrors()) {
            for(error in result.allErrors) {
                response.errors.add(error.defaultMessage.toString())
            }
            return ResponseEntity.badRequest().body(response)
        }

        var funcionarioAtualizado: Funcionario = atualizarDadosFuncionario(funcionario!!, funcionarioDTO)
        funcionarioAtualizado = funcionarioService.salvar(funcionarioAtualizado)

        response.data = converterFuncionarioDTO(funcionarioAtualizado)

        return ResponseEntity.ok(response)
    }

    private fun converterFuncionarioDTO(funcionario: Funcionario): FuncionarioDTO {
        return FuncionarioDTO(funcionario.id.toString(), funcionario.nome, funcionario.email,
            funcionario.senha, funcionario.valorHora?.toString(), funcionario.qtdHorasTralhadoDia?.toString(),
            funcionario.qtdHorasAlmoco?.toString())
    }

    private fun atualizarDadosFuncionario(funcionario: Funcionario, funcionarioDTO: FuncionarioDTO): Funcionario {

        var senha: String? = null
        senha = if(funcionarioDTO.senha == null) {
            funcionario.senha
        }
        else {
            SenhaUtils().gerarBcrypt(funcionarioDTO.senha)
        }

        return Funcionario(funcionario.id, funcionario.nome, funcionario.email, senha, funcionario.cpf,
            funcionario.perfil, funcionario.empresa, funcionarioDTO.valorHora?.toDouble(),
            funcionarioDTO.qtdHorasTralhadoDia?.toFloat(), funcionarioDTO.qtdHoraAlmoco?.toFloat())
    }


}