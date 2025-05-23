package aca.web.bitacora;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultadDao;
import aca.vista.spring.Alumno;
import aca.vista.spring.AlumnoDao;

@Controller
public class ContBitacoraConsulta {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	CatFacultadDao catFacultadDao;
	
	@Autowired
	AlumnoDao alumnoDao;
	
		
	@RequestMapping("/bitacora/consulta/consulta")
	public String bitacoraConsultaConsulta(HttpServletRequest request, Model modelo){
		
		String codigoAlumno		= "0";
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoAlumno = (String)sesion.getAttribute("codigoAlumno");
        }
        
		Alumno alumno = alumnoDao.mapeaRegId(codigoAlumno);
		
		String facultadId = catCarreraDao.getFacultadId(alumno.getCarreraId());
		String nombreFacultada = catFacultadDao.getNombreCorto(facultadId);
		
		modelo.addAttribute("codigoAlumno", codigoAlumno);
		modelo.addAttribute("alumno", alumno);
		modelo.addAttribute("facultadId", facultadId);
		modelo.addAttribute("nombreFacultada", nombreFacultada);
		
		return "bitacora/consulta/consulta";
	}
	
}
