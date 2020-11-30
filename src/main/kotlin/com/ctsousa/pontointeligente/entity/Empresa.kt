package com.ctsousa.pontointeligente.entity

import javax.persistence.*

@Entity
data class Empresa (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long?,

        @Column(nullable = false)
        val razaoSocial: String,

        @Column(nullable = false)
        val cnpj: String? = null
)