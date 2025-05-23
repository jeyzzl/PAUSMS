package aca.web.credencialemp;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.cred.spring.CredEmpleado;
import aca.cred.spring.CredEmpleadoDao;
import aca.vista.spring.Maestros;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContCredencialEmpCumple {
	
	@Autowired	
	MaestrosDao maestrosDao;
	
	@Autowired
	CredEmpleadoDao credEmpleadoDao;
	
	
	@RequestMapping("/credencial_emp/cumple/mes")
	public String credencialEmpCumpleMes(HttpServletRequest request, Model modelo){	
		
		String mesActual 	= aca.util.Fecha.getMesActual(aca.util.Fecha.getHoy());
		String dia			= request.getParameter("Dia")==null?"00":request.getParameter("Dia");
		String mes			= request.getParameter("Mes")==null?mesActual:request.getParameter("Mes");
		if (dia.length()==1) dia = "0"+dia;
		List<Maestros> lisEmpleados 		= maestrosDao.lisEnCumpleaños(mes, dia, " ORDER BY TO_CHAR(ENOC.MAESTROS.F_NACIMIENTO,'MM-DD')");
		HashMap<String,CredEmpleado> mapaPuestos 	= credEmpleadoDao.mapaCredenciales(); 
		
		modelo.addAttribute("lisEmpleados", lisEmpleados);
		modelo.addAttribute("mapaPuestos", mapaPuestos);
		
		return "credencial_emp/cumple/mes";
	}
	
}
		