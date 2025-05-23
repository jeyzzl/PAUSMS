package aca.web.finanzas;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.financiero.spring.FinBloqueo;
import aca.financiero.spring.FinBloqueoDao;
import aca.financiero.spring.FinPeriodo;
import aca.financiero.spring.FinPeriodoDao;

@Controller
public class ContFinanzasAlumno {
	
	@Autowired	
	private FinPeriodoDao finPeriodoDao;
	
	@Autowired	
	private FinBloqueoDao finBloqueoDao;
	
	@Autowired	
	private AlumPersonalDao alumPersonalDao;
	
	
	@RequestMapping("/finanzas/alumno/listado")
	public String finanzasAlumnoListado(HttpServletRequest request, Model modelo){
		
		List<FinPeriodo> lisPeriodo	= finPeriodoDao.getListAll(" ORDER BY ENOC.FIN_PERIODO.FECHA_INI DESC");

		String periodoId = request.getParameter("PeriodoId") == null ? String.valueOf(lisPeriodo.size()) : request.getParameter("PeriodoId");
		String codigos	 = request.getParameter("Codigos") == null ? "0" : request.getParameter("Codigos");
		
		String codigoEmpleado = "";

		HttpSession sesion = ((HttpServletRequest) request).getSession();
		if (sesion != null) {
			codigoEmpleado = (String) sesion.getAttribute("codigoPersonal");
		}
		
		FinBloqueo finBloqueo = new FinBloqueo();
		
		if(!codigos.equals("0")) {
			codigos = codigos.replaceAll("\n", "-");
			codigos = codigos.replaceAll("\r", "");
	
			String array[] = codigos.split("-");
			for(String matricula : array) {
				finBloqueo.setCodigoPersonal(matricula);
				finBloqueo.setEstado("A");
				finBloqueo.setPeriodoId(periodoId);
				finBloqueo.setUsuario(codigoEmpleado);
				if(alumPersonalDao.existeAlumno(matricula)) {
					finBloqueoDao.insertReg(finBloqueo);
				}
			}
		}
		
		List<FinBloqueo> lisAlumnos = finBloqueoDao.lisTodos(" WHERE PERIODO_ID = '"+periodoId+"'");
		
		HashMap<String, AlumPersonal> mapaAlumnosBloqueados = alumPersonalDao.mapaAlumnosBloqueados(periodoId);
		
		modelo.addAttribute("codigoEmpleado", codigoEmpleado);
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("lisPeriodo", lisPeriodo);
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("mapaAlumnosBloqueados", mapaAlumnosBloqueados);
		
		return "finanzas/alumno/listado";
	}
	
	@RequestMapping("/finanzas/alumno/borrar")
	public String finanzasBorrar(HttpServletRequest request, Model modelo){
		
		String periodoId 		= request.getParameter("PeriodoId") == null ? "1" : request.getParameter("PeriodoId");
		String codigoPersonal	= request.getParameter("CodigoPersonal") == null ? "0" : request.getParameter("CodigoPersonal");
		
		if(finBloqueoDao.existeReg(codigoPersonal, periodoId)) {
			finBloqueoDao.deleteReg(codigoPersonal, periodoId);
		}
		
		return "redirect:/finanzas/alumno/listado?PeriodoId="+periodoId;
	}
	
	@RequestMapping("/finanzas/alumno/editar")
	public String finanzasEditar(HttpServletRequest request, Model modelo){
		
		String periodoId 		= request.getParameter("PeriodoId") == null ? "1" : request.getParameter("PeriodoId");
		String codigoPersonal	= request.getParameter("CodigoPersonal") == null ? "0" : request.getParameter("CodigoPersonal");
		
		String codigoEmpleado = "";

		HttpSession sesion = ((HttpServletRequest) request).getSession();
		if (sesion != null) {
			codigoEmpleado = (String) sesion.getAttribute("codigoEmpleado");
		}
		
		FinBloqueo alumno = new FinBloqueo();
		
		if(finBloqueoDao.existeReg(codigoPersonal, periodoId)) {
			alumno = finBloqueoDao.mapeaRegId(codigoPersonal, periodoId);
			
			if(alumno.getEstado().equals("I")) {
				alumno.setEstado("A");
			}else {
				alumno.setEstado("I");
			}
			
			alumno.setUsuario(codigoEmpleado);
			
			finBloqueoDao.updateReg(alumno);
		}
		
		return "redirect:/finanzas/alumno/listado?PeriodoId="+periodoId;
	}
	
}