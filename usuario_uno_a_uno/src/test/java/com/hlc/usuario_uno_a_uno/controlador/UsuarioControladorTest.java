package com.hlc.usuario_uno_a_uno.controlador;

import com.hlc.usuario_uno_a_uno.entidad.InformacionUsuario;
import com.hlc.usuario_uno_a_uno.entidad.Usuario;
import com.hlc.usuario_uno_a_uno.entidad.enumerado.Rol;
import com.hlc.usuario_uno_a_uno.servicio.UsuarioServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.Collections;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioControladorTest {

    @Mock
    private UsuarioServicio usuarioServicio;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private UsuarioControlador usuarioControlador;

    private Usuario usuario;
    private InformacionUsuario infoUsuario;
    private Rol rol;

    @BeforeEach
    void setUp() {
        infoUsuario = new InformacionUsuario("user@email.com", "123456789");
        usuario = new Usuario("testuser", "password123", infoUsuario, rol);
        infoUsuario.setUsuario(usuario);
        usuario.setId(1L);
    }

    @Test
    void testListarUsuarios() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Usuario> usuarios = new PageImpl<>(Collections.singletonList(usuario));
        when(usuarioServicio.listarUsuariosPaginados(pageable)).thenReturn(usuarios);

        String view = usuarioControlador.listarUsuarios(0, 10, model);

        assertEquals("usuarios/listar", view);
        verify(model).addAttribute("usuarios", usuarios);
        verify(model).addAttribute("currentPage", 0);
        verify(model).addAttribute("totalPages", usuarios.getTotalPages());
    }


    @Test
    void testMostrarFormularioNuevoUsuario() {
        String view = usuarioControlador.mostrarFormularioNuevoUsuario(model);

        assertEquals("usuarios/formulario", view);
        verify(model).addAttribute(eq("usuario"), any(Usuario.class));
    }

    @Test
    void testGuardarUsuarioConErrores() {
        when(bindingResult.hasErrors()).thenReturn(true);

        String view = usuarioControlador.guardarUsuario(usuario, bindingResult, model);

        assertEquals("usuarios/formulario", view);
        verify(model).addAttribute("usuario", usuario);
    }

    @Test
    void testGuardarUsuarioSinErrores() {
        when(bindingResult.hasErrors()).thenReturn(false);

        String view = usuarioControlador.guardarUsuario(usuario, bindingResult, model);

        assertEquals("redirect:/usuarios", view);
        verify(usuarioServicio).guardarOActualizarUsuario(usuario);
    }

    @Test
    void testGuardarUsuarioSinInformacionUsuario() {
        Usuario usuarioSinInfo = new Usuario("user2", "password456", null, rol);
        when(bindingResult.hasErrors()).thenReturn(false);

        String view = usuarioControlador.guardarUsuario(usuarioSinInfo, bindingResult, model);

        assertEquals("redirect:/usuarios", view);
        verify(usuarioServicio).guardarOActualizarUsuario(usuarioSinInfo);
    }

    @Test
    void testMostrarFormularioEditarUsuario() {
        when(usuarioServicio.obtenerUsuarioPorId(1L)).thenReturn(usuario);

        String view = usuarioControlador.mostrarFormularioEditarUsuario(1L, model);

        assertEquals("usuarios/formulario", view);
        verify(model).addAttribute("usuario", usuario);
    }

    @Test
    void testMostrarFormularioEditarUsuarioNoExistente() {
        when(usuarioServicio.obtenerUsuarioPorId(99L)).thenReturn(null);

        Exception exception = assertThrows(NullPointerException.class, () -> {
            usuarioControlador.mostrarFormularioEditarUsuario(99L, model);
        });

        assertNotNull(exception);
    }

    @Test
    void testEliminarUsuario() {
        String view = usuarioControlador.eliminarUsuario(1L);

        assertEquals("redirect:/usuarios", view);
        verify(usuarioServicio).eliminarUsuario(1L);
    }

    @Test
    void testEliminarUsuarioNoExistente() {
        doThrow(new RuntimeException("Usuario no encontrado")).when(usuarioServicio).eliminarUsuario(99L);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            usuarioControlador.eliminarUsuario(99L);
        });

        assertEquals("Usuario no encontrado", exception.getMessage());
    }

    @Test
    void testBuscarUsuarios() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Usuario> usuarios = new PageImpl<>(Arrays.asList(usuario));

        when(usuarioServicio.buscarPorRol(Rol.ADMIN, pageable)).thenReturn(usuarios);

        String view = usuarioControlador.buscarUsuarios("test", "ADMIN", 0, 10, model);

        assertEquals("usuarios/listar", view);

        verify(model).addAttribute("usuarios", usuarios);
        verify(model).addAttribute("currentPage", 0);
        verify(model).addAttribute("totalPages", usuarios.getTotalPages());
        verify(model).addAttribute("nombre", "test");
    }


}

