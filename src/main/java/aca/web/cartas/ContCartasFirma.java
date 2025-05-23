package aca.web.cartas;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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
public class ContCartasFirma {	
	
	
	@Autowired
	AlumPlanDao alumPlanDao; 
	
	@Autowired
	AlumAcademicoDao alumAcademicoDao; 
	
	@Autowired
	AlumVacacionDao alumVacacionDao; 
	
	@Autowired
	CatNivelDao catNivelDao; 
	
	@Autowired
	MapaPlanDao mapaPlanDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;
	
	@Autowired
	ParametrosDao parametrosDao;
	
	
	@RequestMapping("/cartas/firma/carta")
	public String cartasFirmaCarta(HttpServletRequest request, Model modelo){
				
		String codigoPersonal 		= "0";		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
		if (sesion!=null) {
			codigoPersonal = (String)sesion.getAttribute("codigoAlumno");
		}
		
		String planId			= alumPlanDao.getPlanActual(codigoPersonal);		
		String carreraId		= mapaPlanDao.getCarreraId(planId);		
		String nivelId			= catCarreraDao.getNivelId(carreraId);		
		String modalidadId		= alumAcademicoDao.getModalidadId(codigoPersonal);
		boolean esInscrito		= alumAcademicoDao.esInscrito(codigoPersonal);		
		String nombreNivel      = catNivelDao.getNivelNombre(nivelId);
		
		boolean datosCapturados = false;
		if (alumVacacionDao.existeReg(nivelId, modalidadId )){
			datosCapturados = true;
		}
		
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("carreraId", carreraId);
		modelo.addAttribute("nivelId", nivelId);
		modelo.addAttribute("modalidadId", modalidadId);
		modelo.addAttribute("esInscrito", esInscrito);
		modelo.addAttribute("nombreNivel", nombreNivel);
		modelo.addAttribute("datosCapturados", datosCapturados);
		
		return "cartas/firma/carta";
	}
	
	@RequestMapping("/cartas/firma/view")
	public String cartasFirmaView(HttpServletRequest request, Model modelo){
		
		String codigoPersonal 		= "0";		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
		if (sesion!=null) {
			codigoPersonal = (String)sesion.getAttribute("codigoAlumno");
		}
		
		String nombreAlumno			= alumPersonalDao.getNombreAlumno(codigoPersonal,"NOMBRE");
		String modalidadId			= alumAcademicoDao.getModalidadId(codigoPersonal);
		String planId				= alumPlanDao.getPlanActual(codigoPersonal);
		String carreraId			= mapaPlanDao.getCarreraId(planId);
		String carrera				= mapaPlanDao.getCarreraSe(planId);
		String facultad				= catCarreraDao.getFacultadId(carreraId);
		String sexo					= alumPersonalDao.getGenero(codigoPersonal);
		String semestre				= alumAcademicoDao.getSemestre(codigoPersonal); 
		String periodo				="-";
		String nivelId				= catCarreraDao.getNivelId(carreraId);
		
		AlumVacacion alumVacacion 	= new AlumVacacion();
		if (alumVacacionDao.existeReg(nivelId, modalidadId )){
			alumVacacion			= alumVacacionDao.mapeaRegId(nivelId, modalidadId);
		}
		String fExamen				= alumVacacion.getfExamen();
		String fInicio				= alumVacacion.getfInicio();
		String fFinal				= alumVacacion.getfFinal();
		
		Parametros parametros 		= parametrosDao.mapeaRegId("1");
		
		modelo.addAttribute("parametros", parametros);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("modalidadId", modalidadId);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("carreraId", carreraId);
		modelo.addAttribute("carrera", carrera);
		modelo.addAttribute("facultad", facultad);
		modelo.addAttribute("sexo", sexo);
		modelo.addAttribute("semestre", semestre);
		modelo.addAttribute("periodo", periodo);
		modelo.addAttribute("nivelId", nivelId);		
		modelo.addAttribute("fExamen", fExamen);	
		modelo.addAttribute("fInicio", fInicio);
		modelo.addAttribute("fFinal", fFinal);
		
		return "cartas/firma/view";
	}
	
	@RequestMapping("/cartas/firma/view2")
	public String cartasFirmaView2(HttpServletRequest request, Model modelo){
		
		String codigoPersonal		= "0";
		HttpSession session = ((HttpServletRequest)request).getSession();
		if (session != null) {
			codigoPersonal = (String)session.getAttribute("codigoAlumno");		
		}
		
		String institucion 			= (String) session.getAttribute("institucion");
		String nombreAlumno			= alumPersonalDao.getNombreAlumno(codigoPersonal,"NOMBRE");
		String modalidadId			= alumAcademicoDao.getModalidadId(codigoPersonal);
		String planId				= alumPlanDao.getPlanActual(codigoPersonal);
		String carreraId			= mapaPlanDao.getCarreraId(planId);
		String carrera				= mapaPlanDao.getCarreraSe(planId);
		String sexo					= alumPersonalDao.getGenero(codigoPersonal);
		String semestre				= alumAcademicoDao.getSemestre(codigoPersonal); 
		String periodo				="-";
		String nivelId				= catCarreraDao.getNivelId(carreraId);
		
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
		modelo.addAttribute("institucion", institucion);
		modelo.addAttribute("nivelId", nivelId);
		modelo.addAttribute("fExamen", fExamen);	
		modelo.addAttribute("fInicio", fInicio);
		modelo.addAttribute("fFinal", fFinal);
		modelo.addAttribute("sexo", sexo);
		modelo.addAttribute("semestre", semestre);
		modelo.addAttribute("periodo", periodo);
		modelo.addAttribute("parametros", parametros);		
		
		return "cartas/firma/view2";
	}
	
}