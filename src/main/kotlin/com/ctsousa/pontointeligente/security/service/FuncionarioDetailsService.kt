package com.ctsousa.pontointeligente.security.service

import com.ctsousa.pontointeligente.entity.Funcionario
import com.ctsousa.pontointeligente.security.FuncionarioPrincipal
import com.ctsousa.pontointeligente.service.FuncionarioService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class FuncionarioDetailsService(val funcionarioService: FuncionarioService) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        if(username != null) {
            val funcionario: Funcionario? = funcionarioService.buscarPorEmail(username)
            if(funcionario != null) {
                return FuncionarioPrincipal(funcionario)
            }
        }
        throw UsernameNotFoundException(username)
    }
}