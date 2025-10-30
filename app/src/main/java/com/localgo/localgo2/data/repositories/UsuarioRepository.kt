package com.localgo.localgo2.data.repositories

import com.localgo.localgo2.data.models.Usuario

class UsuarioRepository {
    private val usuarios = mutableListOf(
        Usuario(1, "Luciano", "luciano@mail.com"),
        Usuario(2, "Ana", "ana@mail.com")
    )

    fun getUsuarios(): List<Usuario> = usuarios

    fun agregarUsuario(usuario: Usuario) {
        usuarios.add(usuario)
    }
}
