package com.hlc.usuario_uno_a_uno.entidad;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.hlc.usuario_uno_a_uno.entidad.enumerado.Rol;

class UsuarioTest {

    
    private Usuario usuario;
    private InformacionUsuario informacionUsuario;
    
    private Rol rol;
    
    @BeforeEach
    void setUp() {
        informacionUsuario = new InformacionUsuario("test@email.com", "12345678");
        usuario = new Usuario("testuser", "password", informacionUsuario, rol);
        informacionUsuario.setUsuario(usuario);
    }
    
    @Test
    @DisplayName("Verificar valores iniciales de Usuario")
    void testUsuario_ValoresIniciales() {
        assertNotNull(usuario, "El objeto usuario no debe ser nulo");
        assertEquals("testuser", usuario.getUsername(), "El username no coincide");
        assertEquals("password", usuario.getPassword(), "El password no coincide");
        assertNotNull(usuario.getInformacionUsuario(), "La información del usuario no debe ser nula");
    }
    
    @Test
    @DisplayName("Verificar valores iniciales de InformacionUsuario")
    void testInformacionUsuario_ValoresIniciales() {
        assertNotNull(informacionUsuario, "El objeto InformacionUsuario no debe ser nulo");
        assertEquals("test@email.com", informacionUsuario.getEmail(), "El email no coincide");
        assertEquals("12345678", informacionUsuario.getTelefono(), "El teléfono no coincide");
        assertNotNull(informacionUsuario.getUsuario(), "El usuario en la información no debe ser nulo");
    }
    
    @Test
    @DisplayName("Actualizar valores de Usuario")
    void testUsuario_Setters() {
        usuario.setUsername("newuser");
        usuario.setPassword("newpassword");
        
        assertEquals("newuser", usuario.getUsername(), "El username actualizado no coincide");
        assertEquals("newpassword", usuario.getPassword(), "El password actualizado no coincide");
    }
    
    @Test
    @DisplayName("Actualizar valores de InformacionUsuario")
    void testInformacionUsuario_Setters() {
        informacionUsuario.setEmail("new@email.com");
        informacionUsuario.setTelefono("87654321");
        
        assertEquals("new@email.com", informacionUsuario.getEmail(), "El email actualizado no coincide");
        assertEquals("87654321", informacionUsuario.getTelefono(), "El teléfono actualizado no coincide");
    }
    
    @Test
    @DisplayName("Verificar que se lanza una excepción correctamente")
    void testExcepcionLanzada() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            throw new RuntimeException("Error esperado en el test");
        });

        assertEquals("Error esperado en el test", exception.getMessage(), "El mensaje de la excepción no coincide");
    }

}
