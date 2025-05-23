package aca.web.cartas;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumPersonalDao;

@Controller
public class ContCartasImprimir {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired	
	private AlumPersonalDao alumPersonalDao;
	
	
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/cartas/imprimir/getInfo")
	public String cartasImprimirGetinfo(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContCartasImprimir|cartasImprimirGetinfo:");
		return "cartas/imprimir/getInfo";
	}
	
	@RequestMapping("/cartas/imprimir/getConstanciasAlumno")
	public String cartasImprimirGetConstanciasAlumno(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContCartasImprimir|getConstanciasAlumno:");
		return "cartas/imprimir/getConstanciasAlumno";
	}
	
	@RequestMapping("/cartas/imprimir/vistaPrevia")
	public String cartasImprimirVistaPrevia(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContCartasImprimir|cartasImprimirVistaPrevia:");
		return "cartas/imprimir/vistaPrevia";
	}
	
	@RequestMapping("/cartas/imprimir/constancias")
	public String cartasImprimirConstancia(HttpServletRequest request, Model modelo){
		
		String codigoAlumno = "";
		HttpSession sesion	= ((HttpServletRequest)request).getSession();		
		if (sesion!=null){
			codigoAlumno	= (String) sesion.getAttribute("codigoAlumno");
		}
		boolean existeAlumno = false;
		if( alumPersonalDao.existeAlumno(codigoAlumno) == false ){
			existeAlumno = true;
		}
		
		modelo.addAttribute("existeAlumno", existeAlumno);
		
		return "cartas/imprimir/constancias";
	}
}