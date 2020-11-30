package com.ctsousa.pontointeligente.entity

import com.ctsousa.pontointeligente.enums.PerfilEnum
import org.hibernate.validator.constraints.Length
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

@Entity
data class Funcionario (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long?,

        @NotEmpty(message = "Nome não pode ser vázio")
        @Column(nullable = false)
        val nome: String,

        @NotEmpty(message = "Email não pode ser vázio")
        @Length(min = 5, max = 50, message = "Email deve conter entre 5 e 50 caracteres")
        @Email(message = "Email inválido")
        @Column(nullable = false)
        val email: String,

        @Column(nullable = false)
        val senha: String,

        @Column(nullable = false)
        val cpf: String,

        @Column(nullable = false)
        val perfil: PerfilEnum,

        @ManyToOne
        @JoinColumn(name = "empresa_id", nullable =  false)
        val empresa: Empresa,

        @Column(name = "valor_hora")
        val valorHora: Double? = 0.0,

        @Column(name = "quantidade_horas_trabalhada_dia")
        val qtdHorasTralhadoDia: Float? = 0F,

        @Column(name = "quantidade_horas_almoco")
        val qtdHorasAlmoco: Float? = 0F
)