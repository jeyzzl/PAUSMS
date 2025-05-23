package aca.web.admlinea;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.admision.spring.AdmPrueba;
import aca.admision.spring.AdmPruebaAlumno;
import aca.admision.spring.AdmPruebaAlumnoDao;
import aca.admision.spring.AdmPruebaDao;
import aca.admision.spring.AdmSolicitud;
import aca.admision.spring.AdmSolicitudDao;
import aca.admision.spring.AdmUsuario;
import aca.admision.spring.AdmUsuarioDao;

@Controller
public class ContAmdlineaPrueba {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	private AdmPruebaDao admPruebaDao;
	
	@Autowired
	private AdmPruebaAlumnoDao admPruebaAlumnoDao;	
	
	@Autowired
	private AdmSolicitudDao admSolicitudDao;
	
	@Autowired
	private AdmUsuarioDao admUsuarioDao;
	
	
	@RequestMapping("/admlinea/prueba/lista")
	public String admPruebaLista(HttpServletRequest request, Model modelo){
		
		String pruebaId 	= request.getParameter("PruebaId") == null ?"0":request.getParameter("PruebaId");
		String folios	 	= request.getParameter("Folios") == null ?"0":request.getParameter("Folios");
		
		List<AdmPrueba> listaTipos 			= admPruebaDao.lisTodos(" ORDER BY NOMBRE");
		if (pruebaId.equals("0") && listaTipos.size() >= 1) {
			pruebaId = listaTipos.get(0).getPruebaId();
		}
		
		List<AdmPruebaAlumno> listaAlumnos 	= admPruebaAlumnoDao.lisPorPrueba(pruebaId, " ORDER BY FECHA");
		
		String codigoPersonal 	= "";
		HttpSession sesion 		= ((HttpServletRequest) request).getSession();
		if (sesion != null) {
			codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");
		}
		
		AdmPruebaAlumno alumno = new AdmPruebaAlumno();
		
		if(!folios.equals("0")) {
			folios = folios.replaceAll("\n", "-");
			folios = folios.replaceAll("\r", "");
	
			String array[] = folios.split("-");
			for(String matricula : array) {
				alumno.setFolio(matricula);
				alumno.setPruebaId(pruebaId);
				alumno.setUsuario(codigoPersonal);
				if(!admPruebaAlumnoDao.existeReg(matricula) && admSolicitudDao.existeReg(matricula)) {
					admPruebaAlumnoDao.insertReg(alumno);
				}
			}
			listaAlumnos 	= admPruebaAlumnoDao.lisPorPrueba(pruebaId," ORDER BY FECHA");
		}
		
		HashMap<String,AdmSolicitud> mapaSolicitudes 	= admSolicitudDao.mapaAlumnosEnTpt();
		HashMap<String,AdmUsuario> mapaUsuarios			= admUsuarioDao.mapaAlumnosEnTpt();
		
		modelo.addAttribute("pruebaId", pruebaId);
		
		modelo.addAttribute("listaTipos", listaTipos);
		modelo.addAttribute("listaAlumnos", listaAlumnos);
		modelo.addAttribute("mapaSolicitudes", mapaSolicitudes);
		modelo.addAttribute("mapaUsuarios", mapaUsuarios);
		
		return "admlinea/prueba/lista";
	}
	
	@RequestMapping("/admlinea/prueba/borrar")
	public String admPruebaBorrar(HttpServletRequest request, Model modelo){
		
		String folio 	= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		
		admPruebaAlumnoDao.deleteReg(folio);
		
		return "redirect:/admlinea/prueba/lista";
	}
	
}
