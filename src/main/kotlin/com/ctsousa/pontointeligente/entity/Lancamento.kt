package com.ctsousa.pontointeligente.entity

import com.ctsousa.pontointeligente.enums.TipoEnum
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotEmpty

@Entity
data class Lancamento (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,

        @NotEmpty(message = "Data não pode ser vázia")
        @Temporal(TemporalType.DATE)
        @Column(nullable = false)
        val data: Date,

        @NotEmpty(message = "Tipo não pode ser vázio")
        @Column(nullable = false)
        val tipo: TipoEnum,

        @ManyToOne
        @JoinColumn(name = "funcionario_id", nullable = false)
        val funcionario: Funcionario,

        val descricao: String? = "",

        val localizacao: String? = "",
)