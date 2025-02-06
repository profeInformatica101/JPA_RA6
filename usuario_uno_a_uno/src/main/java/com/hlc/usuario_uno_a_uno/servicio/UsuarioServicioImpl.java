package com.hlc.usuario_uno_a_uno.servicio;



import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hlc.usuario_uno_a_uno.entidad.Usuario;
import com.hlc.usuario_uno_a_uno.entidad.enumerado.Rol;
import com.hlc.usuario_uno_a_uno.errores.excepcion.ResourceNotFoundException;
import com.hlc.usuario_uno_a_uno.repositorio.UsuarioRepositorio;

@Service
public class UsuarioServicioImpl implements UsuarioServicio {

	
	private final UsuarioRepositorio usuarioRepositorio;
    
    public UsuarioServicioImpl(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    public Usuario guardarOActualizarUsuario(Usuario usuario) {
    	if (usuario.getId() != null && usuarioRepositorio.existsById(usuario.getId())) {
            Optional<Usuario> usuarioExistente = usuarioRepositorio.findById(usuario.getId());
            if (usuarioExistente.isPresent()) {
                Usuario actualizado = usuarioExistente.get();
                actualizado.setId(usuario.getId());
                actualizado.setUsername(usuario.getUsername());
                actualizado.setPassword(usuario.getPassword());
                actualizado.setRol(usuario.getRol());
                actualizado.setInformacionUsuario(usuario.getInformacionUsuario());
                return usuarioRepositorio.save(actualizado);
            }
        }
        return usuarioRepositorio.save(usuario);

    }

    @Override
    public Usuario obtenerUsuarioPorId(Long id) {
        Optional<Usuario> usuario = usuarioRepositorio.findById(id);
        return  usuario.orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: "+id));
    }

    @Override
    public void eliminarUsuario(Long id) {
        usuarioRepositorio.deleteById(id);
    }

    @Override
    public Page<Usuario> listarUsuariosPaginados(Pageable pageable) {
        return usuarioRepositorio.findAll(pageable);
    }

    @Override
    public Page<Usuario> buscarPorNombre(String nombre, Pageable pageable) {
        return usuarioRepositorio.findByUsernameContainingIgnoreCase(nombre, pageable);
    }

	@Override
	public Page<Usuario> buscarPorRoles(Rol rol, Pageable pageable) {
		return usuarioRepositorio.findByRol(rol, pageable);
	}


}
