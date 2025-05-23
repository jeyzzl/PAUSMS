package aca.web.archivo;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumAcademico;
import aca.alumno.spring.AlumAcademicoDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlanDao;
import aca.archivo.spring.ArchDocAlum;
import aca.archivo.spring.ArchDocAlumDao;
import aca.archivo.spring.ArchDocumentosDao;
import aca.archivo.spring.ArchStatusDao;
import aca.archivo.spring.ArchivoDao;

@Controller
public class ContArchivoConsulta {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired	
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired	
	private AlumAcademicoDao alumAcademicoDao;
	
	@Autowired	
	private ArchDocAlumDao archDocAlumDao;
	
	@Autowired	
	private ArchDocumentosDao archDocumentosDao;
	
	@Autowired	
	private ArchStatusDao archStatusDao;
	
	@Autowired
	ArchivoDao archivoDao;
	
	@Autowired
	AlumPlanDao alumPlanDao;
	
	
	@RequestMapping("/archivo/consulta/consulta")
	public String archivoConsultaConsulta(HttpServletRequest request, Model modelo){
		
		String matricula 	= "";
		String planActivo	= "";
		
		AlumPersonal alumPersonal = new AlumPersonal();
		AlumAcademico alumAcademico = new AlumAcademico();
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
		if (sesion!=null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
			alumPersonal 	= alumPersonalDao.mapeaRegId(matricula);
			alumAcademico	= alumAcademicoDao.mapeaRegId(matricula);
			planActivo 		=  alumPlanDao.getPlanActual(matricula);
		}
		
		String autorizado 	= archivoDao.autorizaAlumno(matricula, planActivo);
		
		List<ArchDocAlum> lisDocumentos			= archDocAlumDao.lisAlumno(matricula, " ORDER BY IDDOCUMENTO");
		HashMap<String, String> mapaDocumentos	= archDocumentosDao.mapaTodos();
		HashMap<String, String> mapaStatus		= archStatusDao.mapaStatus();
		
		modelo.addAttribute("planActivo", planActivo);
		modelo.addAttribute("autorizado", autorizado);
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("alumAcademico", alumAcademico);
		modelo.addAttribute("lisDocumentos", lisDocumentos);		
		modelo.addAttribute("mapaDocumentos", mapaDocumentos);
		modelo.addAttribute("mapaStatus", mapaStatus);
		
		return "archivo/consulta/consulta";
	}

}
