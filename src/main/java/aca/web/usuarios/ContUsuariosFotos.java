package aca.web.usuarios;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContUsuariosFotos {
	
	@RequestMapping("/usuarios/fotos/bajar")
	public String usuariosFotosBajar(HttpServletRequest request){
		
		return "usuarios/fotos/bajar";
	}	
}