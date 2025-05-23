package aca.web.titulacion;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import aca.tit.spring.TitCadena;
import aca.tit.spring.TitCadenaDao;
import aca.tit.spring.TitInstitucionUsuario;
import aca.tit.spring.TitInstitucionUsuarioDao;
import aca.tit.spring.TitTramite;

@Controller
public class ContTitulacionCatalogos {
	
	@Controller
	public class ContInscritosInscritosSe {
		
		@Autowired
		@Qualifier("dsEnoc")
		private DataSource enoc;
		
		@Autowired
		@Qualifier("dsArchivo")
		private DataSource archivo;
		
		@Autowired
		ServletContext context;
		public void enviarConEnoc(HttpServletRequest request, String url){
			// Enviar de la conexión
			try{ 
				request.setAttribute("conEnoc", enoc.getConnection());
			}catch(Exception ex){ 
				System.out.println(url+" "+ex);
			}
		}
		
		public void enviarConArchivo(HttpServletRequest request, String url){
			// Enviar de la conexión
			try{ 
				request.setAttribute("conArchivo", archivo.getConnection());
			}catch(Exception ex){ 
				System.out.println(url+" "+ex);
			}
		}
		
		@RequestMapping("/titulacion/catalogos/catalogos")
		public String titulacionCatalogoCatalogo(HttpServletRequest request){
			enviarConEnoc(request,"Error-aca.ContTitulacionCatalogoCatalogo|titulacionCatalogoCatalogo:");					
			return "/titulacion/catalogos/catalogos";
		}
	}
}