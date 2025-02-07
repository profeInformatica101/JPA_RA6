package com.hlc.usuario_uno_a_uno.servicio;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hlc.usuario_uno_a_uno.entidad.InformacionUsuario;
import com.hlc.usuario_uno_a_uno.entidad.Usuario;
import com.hlc.usuario_uno_a_uno.repositorio.UsuarioRepositorio;

@ExtendWith(MockitoExtension.class)
class UsuarioServicioImplTest {

    @Mock
    private UsuarioRepositorio usuarioRepositorio;

    @InjectMocks
    private UsuarioServicioImpl usuarioServicio;

    private Usuario usuario;
    private InformacionUsuario informacionUsuario;

    @BeforeEach
    void setUp() {
        informacionUsuario = new InformacionUsuario("test@example.com", "123456789");
        usuario = new Usuario("testuser", "testpass", informacionUsuario);
        usuario.setId(1L);
    }

    @Test
    void testGuardarOActualizarUsuario_NuevoUsuario() {
        // Configurar el comportamiento del repositorio
        when(usuarioRepositorio.save(usuario)).thenReturn(usuario);

        // Llamar al método bajo prueba
        Usuario resultado = usuarioServicio.guardarOActualizarUsuario(usuario);

        // Verificar el resultado
        assertNotNull(resultado);
        assertEquals(usuario.getUsername(), resultado.getUsername());
        assertEquals(usuario.getInformacionUsuario().getEmail(), resultado.getInformacionUsuario().getEmail());

        // Verificar que se llamó al método save del repositorio
        verify(usuarioRepositorio, times(1)).save(usuario);
    }

    @Test
    void testGuardarOActualizarUsuario_ActualizarUsuario() {
        // Configurar el comportamiento del repositorio
        when(usuarioRepositorio.existsById(usuario.getId())).thenReturn(true);
        when(usuarioRepositorio.findById(usuario.getId())).thenReturn(Optional.of(usuario));
        when(usuarioRepositorio.save(usuario)).thenReturn(usuario);

        // Llamar al método bajo prueba
        Usuario resultado = usuarioServicio.guardarOActualizarUsuario(usuario);

        // Verificar el resultado
        assertNotNull(resultado);
        assertEquals(usuario.getUsername(), resultado.getUsername());

        // Verificar que se llamó al método save del repositorio
        verify(usuarioRepositorio, times(1)).save(usuario);
    }

    @Test
    void testObtenerUsuarioPorId() {
        // Configurar el comportamiento del repositorio
        when(usuarioRepositorio.findById(usuario.getId())).thenReturn(Optional.of(usuario));

        // Llamar al método bajo prueba
        Usuario resultado = usuarioServicio.obtenerUsuarioPorId(usuario.getId());

        // Verificar el resultado
        assertNotNull(resultado);
        assertEquals(usuario.getId(), resultado.getId());
        assertEquals(usuario.getUsername(), resultado.getUsername());

        // Verificar que se llamó al método findById del repositorio
        verify(usuarioRepositorio, times(1)).findById(usuario.getId());
    }

    @Test
    void testObtenerUsuarioPorId_NoEncontrado() {
        // Configurar el comportamiento del repositorio
        when(usuarioRepositorio.findById(anyLong())).thenReturn(Optional.empty());

        // Verificar que se lanza una excepción
        Exception exception = assertThrows(RuntimeException.class, () -> {
            usuarioServicio.obtenerUsuarioPorId(1L);
        });

        // Verificar el mensaje de la excepción
        assertEquals("Usuario no encontrado con ID: 1", exception.getMessage());

        // Verificar que se llamó al método findById del repositorio
        verify(usuarioRepositorio, times(1)).findById(1L);
    }

    @Test
    void testEliminarUsuario() {
        // Configurar el comportamiento del repositorio
        doNothing().when(usuarioRepositorio).deleteById(usuario.getId());

        // Llamar al método bajo prueba
        usuarioServicio.eliminarUsuario(usuario.getId());

        // Verificar que se llamó al método deleteById del repositorio
        verify(usuarioRepositorio, times(1)).deleteById(usuario.getId());
    }
}
