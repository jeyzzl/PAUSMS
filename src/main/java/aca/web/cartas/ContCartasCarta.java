package aca.web.cartas;

import javax.management.modelmbean.ModelMBeanOperationInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumAcademicoDao;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlanDao;
import aca.alumno.spring.AlumVacacion;
import aca.alumno.spring.AlumVacacionDao;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatNivelDao;
import aca.parametros.spring.Parametros;
import aca.parametros.spring.ParametrosDao;
import aca.plan.spring.MapaPlanDao;

@Controller
public class ContCartasCarta {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	AlumPlanDao alumPlanDao; 
	
	@Autowired
	MapaPlanDao mapaPlanDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	CatNivelDao catNivelDao;
	
	@Autowired
	AlumAcademicoDao alumAcademicoDao;
	
	@Autowired
	AlumVacacionDao alumVacacionDao;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;
	
	@Autowired
	ParametrosDao parametrosDao;
	
	
	@RequestMapping("/cartas/carta/carta")
	public String cartasCartaCarta(HttpServletRequest request, Model modelo){		
		String codigoPersonal 		= "0";
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
		if (sesion!=null) {
			codigoPersonal = (String)sesion.getAttribute("codigoAlumno");
		}		
		
		String planId			= alumPlanDao.getPlanActual(codigoPersonal);
		String carreraId		= mapaPlanDao.getCarreraId(planId);		
		String nivelId			= catCarreraDao.getNivelId(carreraId);
		String nivelNombre		= catNivelDao.getNivelNombre(nivelId);
		String modalidadId		= alumAcademicoDao.getModalidadId(codigoPersonal);		
		boolean datosCapturados = false;
		
		if (alumVacacionDao.existeReg(nivelId, modalidadId )){
			datosCapturados = true;
		}
		
		modelo.addAttribute("datosCapturados", datosCapturados);
		modelo.addAttribute("nivelId", nivelId);
		modelo.addAttribute("nivelNombre", nivelNombre);
		
		return "cartas/carta/carta";
	}
	
	@RequestMapping("/cartas/carta/view")
	public String cartasCartaView(HttpServletRequest request, Model modelo){		
		
		String codigoPersonal 		= "0";		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
		if (sesion!=null) {
			codigoPersonal = (String)sesion.getAttribute("codigoAlumno");
		}		
		String nombreAlumno			= alumPersonalDao.getNombreAlumno(codigoPersonal, "NOMBRE");
		String modalidadId			= alumAcademicoDao.getModalidadId(codigoPersonal);		
		String planId				= alumPlanDao.getPlanActual(codigoPersonal);
		String carreraId			= mapaPlanDao.getCarreraId(planId);
		String carrera				= mapaPlanDao.getCarreraSe(planId);
		String facultad				= catCarreraDao.getFacultadId(carreraId);		
		String nivelId				= catCarreraDao.getNivelId(carreraId);				
		String sexo					= alumPersonalDao.getGenero(codigoPersonal);		
		String semestre				= alumAcademicoDao.getSemestre(codigoPersonal);
		AlumVacacion alumVacacion 	= new AlumVacacion();
		if (alumVacacionDao.existeReg(nivelId, modalidadId )){
			alumVacacion			= alumVacacionDao.mapeaRegId(nivelId, modalidadId);
		}
		
		String fExamen				= alumVacacion.getfExamen();
		String fInicio				= alumVacacion.getfInicio();
		String fFinal				= alumVacacion.getfFinal();
		Parametros parametros 		= parametrosDao.mapeaRegId("1");	
		
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("modalidadId", modalidadId);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("carreraId", carreraId);
		modelo.addAttribute("carrera", carrera);
		modelo.addAttribute("facultad", facultad);
		modelo.addAttribute("nivelId", nivelId);
		modelo.addAttribute("sexo", sexo);
		modelo.addAttribute("semestre", semestre);		
		modelo.addAttribute("fExamen", fExamen);	
		modelo.addAttribute("fInicio", fInicio);
		modelo.addAttribute("fFinal", fFinal);
		modelo.addAttribute("parametros", parametros);	
		
		return "cartas/carta/view";
	}
	
	@RequestMapping("/cartas/carta/view2")
	public String cartasCartaView2(HttpServletRequest request, Model modelo){		
		
		String codigoPersonal		= "0";
		HttpSession session	= ((HttpServletRequest)request).getSession();
		if (session != null) {
			codigoPersonal 		=(String)session.getAttribute("codigoAlumno");
		}
		
		String nombreAlumno			= alumPersonalDao.getNombreAlumno(codigoPersonal, "NOMBRE");
		String modalidadId			= alumAcademicoDao.getModalidadId(codigoPersonal);		
		String planId				= alumPlanDao.getPlanActual(codigoPersonal);
		String carreraId			= mapaPlanDao.getCarreraId(planId);
		String carrera				= mapaPlanDao.getCarreraSe(planId);
		String facultad				= catCarreraDao.getFacultadId(carreraId);		
		String nivelId				= catCarreraDao.getNivelId(carreraId);				
		String sexo					= alumPersonalDao.getGenero(codigoPersonal);		
		String semestre				= alumAcademicoDao.getSemestre(codigoPersonal);
		AlumVacacion alumVacacion	= new AlumVacacion();
		if (alumVacacionDao.existeReg(nivelId, modalidadId)) {
			alumVacacion			=alumVacacionDao.mapeaRegId(nivelId, modalidadId);
		}
		String fExamen				= alumVacacion.getfExamen();
		String fInicio				= alumVacacion.getfInicio();
		String fFinal				= alumVacacion.getfFinal();
		Parametros parametros 		= parametrosDao.mapeaRegId("1");	
		
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("modalidadId", modalidadId);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("carreraId", carreraId);
		modelo.addAttribute("carrera", carrera);
		modelo.addAttribute("facultad", facultad);
		modelo.addAttribute("nivelId", nivelId);
		modelo.addAttribute("sexo", sexo);
		modelo.addAttribute("semestre", semestre);		
		modelo.addAttribute("fExamen", fExamen);	
		modelo.addAttribute("fInicio", fInicio);
		modelo.addAttribute("fFinal", fFinal);
		modelo.addAttribute("parametros", parametros);	
		
		return "cartas/carta/view2";
	}	
}