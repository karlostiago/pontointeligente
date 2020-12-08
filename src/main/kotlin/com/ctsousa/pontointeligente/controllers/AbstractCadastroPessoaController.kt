package com.ctsousa.pontointeligente.controllers

import com.ctsousa.pontointeligente.entity.Empresa
import com.ctsousa.pontointeligente.entity.Funcionario
import com.ctsousa.pontointeligente.service.EmpresaService
import com.ctsousa.pontointeligente.service.FuncionarioService
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError

abstract class AbstractCadastroPessoaController(open val empresaService: EmpresaService, open val funcionarioService: FuncionarioService) {

    var empresa: Empresa? = null

    protected fun validarDadosExistentes(cnpj: String?, cpf: String?, email: String?, pessoaFisica: Boolean, result: BindingResult) {
        if(cnpj != null) {
            empresa = empresaService.buscarPorCnpj(cnpj)
            if (!pessoaFisica && empresa != null) {
                result.addError(ObjectError("empresa", "Empresa já existente"))
                return
            }
            if (pessoaFisica && empresa == null) {
                result.addError(ObjectError("empresa", "Empresa não encontrada"))
            }
        }

        if(cpf != null) {
            val funcionario: Funcionario? = funcionarioService.buscarPorCpf(cpf)
            if (funcionario != null) {
                result.addError(ObjectError("funcionario", "CPF já existente"))
            }
        }

        if(email != null) {
            val funcionario: Funcionario? = funcionarioService.buscarPorEmail(email)
            if (funcionario != null) {
                result.addError(ObjectError("funcionario", "E-mail já existente"))
            }
        }
    }
}