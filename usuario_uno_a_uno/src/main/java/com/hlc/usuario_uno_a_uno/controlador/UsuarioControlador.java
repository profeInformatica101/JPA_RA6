package com.hlc.usuario_uno_a_uno.controlador;

import com.hlc.usuario_uno_a_uno.entidad.InformacionUsuario;
import com.hlc.usuario_uno_a_uno.entidad.Usuario;
import com.hlc.usuario_uno_a_uno.entidad.enumerado.Rol;
import com.hlc.usuario_uno_a_uno.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/usuarios")
public class UsuarioControlador {

    private static final String VISTA_FORMULARIO = "usuarios/formulario";
    private static final String REDIRECT_LISTADO = "redirect:/usuarios";

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping
    public String listarUsuarios(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Usuario> usuarios = usuarioServicio.listarUsuariosPaginados(pageable);
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", usuarios.getTotalPages());
        model.addAttribute("roles", Rol.values());
        return "usuarios/listar";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoUsuario(Model model) {
        Usuario usuario = new Usuario();
        InformacionUsuario informacionUsuario = new InformacionUsuario();
    	usuario.setInformacionUsuario(informacionUsuario); // Inicializa la relación 1:1      

    	model.addAttribute("usuario", usuario);
    	model.addAttribute("roles", Rol.values());
        return VISTA_FORMULARIO;
    }
    
    @GetMapping("/filtrar")
    public String filtrarRoles(@RequestParam String rol, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size, Model model) {
    	
    	Pageable pageable = PageRequest.of(page, size);
    	Page<Usuario> usuarios = usuarioServicio.buscarPorRoles(Rol.valueOf(rol), pageable);
    	model.addAttribute("usuarios", usuarios);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", usuarios.getTotalPages());
        model.addAttribute("roles", Rol.values());
        
    	return "usuarios/listar";
    }

    @PostMapping("/guardar")
    public String guardarUsuario(@Valid @ModelAttribute Usuario usuario, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("roles", Rol.values());
            return VISTA_FORMULARIO;
        }

        // Establece la relación bidireccional
        if (usuario.getInformacionUsuario() != null) {
            usuario.getInformacionUsuario().setUsuario(usuario);
        }

        usuarioServicio.guardarOActualizarUsuario(usuario);
        return REDIRECT_LISTADO;
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarUsuario(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioServicio.obtenerUsuarioPorId(id);

        // Inicializa InformacionUsuario si es null
        if (usuario.getInformacionUsuario() == null) {
            usuario.setInformacionUsuario(new InformacionUsuario());
        }
        
        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", Rol.values());
        return VISTA_FORMULARIO;
    }
    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id, Model model) {
        usuarioServicio.eliminarUsuario(id);
        model.addAttribute("roles", Rol.values());
        return REDIRECT_LISTADO;
    }

    @GetMapping("/buscar")
    public String buscarUsuarios(@RequestParam String nombre,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Usuario> usuarios = usuarioServicio.buscarPorNombre(nombre, pageable);
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", usuarios.getTotalPages());
        model.addAttribute("nombre", nombre);
        model.addAttribute("roles", Rol.values());
        return "usuarios/listar";
    }
}
