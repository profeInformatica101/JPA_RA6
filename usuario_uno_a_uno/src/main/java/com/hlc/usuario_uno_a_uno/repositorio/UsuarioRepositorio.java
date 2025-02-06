package com.hlc.usuario_uno_a_uno.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hlc.usuario_uno_a_uno.entidad.Usuario;
import com.hlc.usuario_uno_a_uno.entidad.enumerado.Rol;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

	Page<Usuario> findByUsernameContainingIgnoreCase(String nombre, Pageable pageable);
	Page<Usuario> findByRol(Rol rol, Pageable pageable);	
}
