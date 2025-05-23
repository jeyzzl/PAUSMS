package aca.web.mentores;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumAcademico;
import aca.alumno.spring.AlumAcademicoDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanDao;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatPaisDao;
import aca.catalogo.spring.CatTipoAlumnoDao;
import aca.leg.spring.LegExtdoctosDao;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.AlumnoDao;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContMentoresDatos {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;
	
	@Autowired
	AlumAcademicoDao alumAcademicoDao;
	
	@Autowired
	AlumPlanDao alumPlanDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	CatPaisDao catPaisDao;
	
	@Autowired
	CatTipoAlumnoDao catTipoAlumnoDao;
	
	@Autowired
	MapaPlanDao mapaPlanDao;
	
	@Autowired
	AlumnoDao alumnoDao;

	@Autowired
	LegExtdoctosDao legExtdoctosDao;
	
	@Autowired
	AccesoDao accesoDao;
	
	@Autowired
	MaestrosDao maestrosDao;
	
	@RequestMapping("/mentores/datos/aconsejado")
	public String mentoresDatosAconsejado(HttpServletRequest request, Model modelo){
		
		String codigoAlumno			= "0";
		String codigoPersonal		= "0";
		HttpSession sesion 			= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoAlumno			= (String) sesion.getAttribute("codigoAlumno");		
			codigoPersonal 			= (String) sesion.getAttribute("codigoPersonal");
		}		
		String planAlumno			= alumPlanDao.getPlanActual(codigoAlumno);
		String carreraId			= mapaPlanDao.getCarreraId(planAlumno);
		String carreraNombre		= catCarreraDao.getNombreCarrera(carreraId);
		AlumPersonal alumPersonal 	= new AlumPersonal();
		AlumAcademico alumAcademico	= new AlumAcademico();
		AlumPlan alumPlan 			= new AlumPlan();
		Acceso acceso 				= new Acceso();
		boolean existe				= false;
		
		if (alumPersonalDao.existeReg(codigoAlumno) && maestrosDao.existeReg(codigoAlumno)==false){
			alumPlan 			= alumPlanDao.mapeaRegId(codigoAlumno);
			alumPersonal 		= alumPersonalDao.mapeaRegId(codigoAlumno);
			alumAcademico 		= alumAcademicoDao.mapeaRegId(codigoAlumno);	
			acceso 				= accesoDao.mapeaRegId(codigoPersonal);
			existe				= true;
		}
		
		String nombrePais		= catPaisDao.getNombrePais(alumPersonal.getPaisId());
		String nombreTipo		= catTipoAlumnoDao.getNombreTipo(alumAcademico.getTipoAlumno());
		String fechaVenceFm3	= legExtdoctosDao.getFechaVenceFM3(codigoAlumno);
		
		modelo.addAttribute("planAlumno", planAlumno);
		modelo.addAttribute("existe", existe);
		modelo.addAttribute("carreraNombre", carreraNombre);
		modelo.addAttribute("nombrePais", nombrePais);
		modelo.addAttribute("nombreTipo", nombreTipo);
		modelo.addAttribute("carreraId", carreraId);		
		modelo.addAttribute("fechaVenceFm3", fechaVenceFm3);		
		modelo.addAttribute("alumPlan", alumPlan);
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("alumAcademico", alumAcademico);
		modelo.addAttribute("acceso", acceso);
				
		return "mentores/datos/aconsejado";
	}
	
}