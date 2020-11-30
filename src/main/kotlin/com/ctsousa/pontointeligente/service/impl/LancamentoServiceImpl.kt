package com.ctsousa.pontointeligente.service.impl

import com.ctsousa.pontointeligente.entity.Funcionario
import com.ctsousa.pontointeligente.entity.Lancamento
import com.ctsousa.pontointeligente.repository.LancamentoRepository
import com.ctsousa.pontointeligente.service.LancamentoService
import org.springframework.data.domain.AbstractPageRequest
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service

@Service
class LancamentoServiceImpl(private val lancamentoRepository: LancamentoRepository) : LancamentoService {

    override fun buscarPorFuncionario(funcionario: Funcionario, pageRequest: AbstractPageRequest): Page<Lancamento> {
        return lancamentoRepository.findByFuncionario(funcionario, pageRequest)
    }

    override fun buscarPorId(id: Long): Lancamento? = lancamentoRepository.buscarPorId(id)

    override fun salvar(lancamento: Lancamento): Lancamento = lancamentoRepository.save(lancamento)

    override fun remover(id: Long) = lancamentoRepository.deleteById(id)
}