package com.ctsousa.pontointeligente.service

import com.ctsousa.pontointeligente.entity.Empresa

interface EmpresaService {

    fun buscarPorCnpj(cnpj: String) : Empresa?;

    fun salvar(empresa: Empresa) : Empresa;
}