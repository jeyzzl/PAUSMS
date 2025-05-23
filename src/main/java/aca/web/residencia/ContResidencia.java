package aca.web.residencia;

import java.io.OutputStream;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.pg.archivo.ArchResidencia;
import aca.pg.archivo.ArchResidenciaUtil;

@Controller
public class ContResidencia {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/residencia/imagen")
	public void imagenActivo(HttpServletRequest request, HttpServletResponse response){
		String matricula 	= request.getParameter("matricula")==null?"0":request.getParameter("matricula");
		String folio		= request.getParameter("folio")==null?"0":request.getParameter("folio");
		ArchResidencia imagen = new ArchResidencia();
		ArchResidenciaUtil imagenU = new ArchResidenciaUtil();
		// Busca la imagen 
		try{			
			imagen = imagenU.mapeaRegId(matricula, folio);
			OutputStream out = response.getOutputStream();
			out.write(imagen.getImagen());
			out.close();
		}catch(Exception ex){
			System.out.println("Error /residencia/imagen:"+ex);
		}
	}
	
}