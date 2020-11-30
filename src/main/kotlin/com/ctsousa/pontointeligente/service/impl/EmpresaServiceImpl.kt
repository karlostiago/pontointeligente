package com.ctsousa.pontointeligente.service.impl

import com.ctsousa.pontointeligente.entity.Empresa
import com.ctsousa.pontointeligente.repository.EmpresaRepository
import com.ctsousa.pontointeligente.service.EmpresaService
import org.springframework.stereotype.Service

@Service
class EmpresaServiceImpl(val empresaRepository: EmpresaRepository) : EmpresaService {

    override fun buscarPorCnpj(cnpj: String): Empresa? = empresaRepository.findByCnpj(cnpj);

    override fun salvar(empresa: Empresa): Empresa = empresaRepository.save(empresa);
}