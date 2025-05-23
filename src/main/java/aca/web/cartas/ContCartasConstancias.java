package aca.web.cartas;

import java.util.Date;
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

import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlanDao;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatPaisDao;
import aca.leg.spring.LegExtdoctosDao;
import aca.leg.spring.LegExtranjero;
import aca.leg.spring.LegExtranjeroDao;
import aca.parametros.spring.Parametros;
import aca.parametros.spring.ParametrosDao;
import aca.plan.spring.MapaPlanDao;
import aca.util.Fecha;

@Controller
public class ContCartasConstancias {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	AlumPlanDao alumPlanDao;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;
	
	@Autowired
	MapaPlanDao mapaPlanDao; 
	
	@Autowired
	LegExtdoctosDao legExtDoctosDao;
	
	@Autowired
	ParametrosDao parametrosDao;  
	
	@Autowired
	LegExtranjeroDao legExtranjeroDao; 
	
	@Autowired
	CatCarreraDao catCarreraDao; 
	
	@Autowired
	CatFacultadDao catFacultadDao;  
	
	@Autowired
	CatPaisDao catPaisDao;
	
	
	@RequestMapping("/cartas/constancia/form")
	public String cartasConstanciaForm(HttpServletRequest request, Model modelo){

		String codigoPersonal = "";
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal 		= (String) sesion.getAttribute("codigoAlumno");
		}
		
		String planCombo	 	= request.getParameter("Plan")==null?"":request.getParameter("Plan");
		String planId = "";
		if(planCombo.equals("")){
		 	planId			= alumPlanDao.getPlanActual(codigoPersonal);
		 	planCombo 		= planId;
		}else{
			planId 			= planCombo;	
		}
		
		String nombreAlumno 	= alumPersonalDao.getNombreAlumno(codigoPersonal, "NOMBRE");
		//boolean esNuevo			= aca.alumno.AlumUtil.esNuevoIngreso(codigoPersonal, planId);
		int nacionalidad		= (Integer.parseInt(alumPersonalDao.getNacionalidad(codigoPersonal)));
		if(String.valueOf(nacionalidad) == null) nacionalidad = 0;
		String carreraId		= mapaPlanDao.getCarreraId(planId);
		String nivelId			= alumPlanDao.getCarreraNivel(carreraId);
		int numSem				= Integer.parseInt(alumPlanDao.getSem(codigoPersonal, planId));
		
		String periodo			= Fecha.getPeriodoMes(Fecha.getHoy());
		String curso			= Fecha.getPeriodoActual();	
		
		boolean	tieneFM3 		= false;
		
		try {
			java.text.SimpleDateFormat sdf 	= new java.text.SimpleDateFormat("dd/MM/yyyy");	
			Date fechaHoy 		= sdf.parse(aca.util.Fecha.getHoy());
			
			if(alumPersonalDao.esExtranjero(codigoPersonal)) {
				Date fechaFM3 		= sdf.parse(legExtDoctosDao.getFechaVenceFM3(codigoPersonal));
				Date fechaDocumento	= sdf.parse(legExtDoctosDao.getFechaVenceDocumento(codigoPersonal,"4"));
			
				if (fechaHoy.before(fechaFM3) || fechaHoy.before(fechaDocumento)) { 
					tieneFM3 = true; 
				}
			}
		}catch (Exception ex){
			System.out.println("Error - aca.web.cartas.ContCartasConstancias:"+ex);
		}
		
		Parametros parametros = parametrosDao.mapeaRegId("1");
		LegExtranjero extranjero = new LegExtranjero();
		
	    boolean cambioCarrera	= false;
	    String carreraFM3		= "";
	    String carreraAlum 		= alumPersonalDao.getCarreraIdCodigo(codigoPersonal);
	    extranjero.setCodigo(codigoPersonal);
	    if(legExtranjeroDao.existeReg(codigoPersonal)){
	    	extranjero = legExtranjeroDao.mapeaRegId(codigoPersonal);
	    	carreraFM3 = extranjero.getCarrera();
	    	if(!carreraAlum.equals(carreraFM3)){
	        	cambioCarrera=true;
	        }   	
	    }
	    
	    String facultad		= catCarreraDao.getFacultadId(carreraId);
	   
	 // Nombres de Facultad y Carrera
		String nombreFacultad 		= catFacultadDao.getNombreFacultad(facultad);
		String nombreCarrera		= mapaPlanDao.getCarreraSe(planId);
		
		List<String> listaPlanes	= alumPlanDao.getPlanesAlumno(codigoPersonal);
		String nacion				= catPaisDao.getNacionalidad(String.valueOf(nacionalidad));

		HashMap<String,String> mapCarreraPlan = new HashMap<String,String>(); 
		for(String plan : listaPlanes) {
			mapCarreraPlan.put(plan, mapaPlanDao.getCarreraId(planId));
		}
		
		HashMap<String, String> mapNombrePlan = mapaPlanDao.mapNombrePlan();
		
		modelo.addAttribute("codigoPersonal", codigoPersonal);
		modelo.addAttribute("planCombo", planCombo);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("carreraId", carreraId);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("nacionalidad", nacionalidad);
		modelo.addAttribute("nivelId", nivelId);
		modelo.addAttribute("numSem", numSem);
		modelo.addAttribute("periodo", periodo);
		
		modelo.addAttribute("curso", curso);
		modelo.addAttribute("tieneFM3", tieneFM3);
		modelo.addAttribute("parametros", parametros);
		modelo.addAttribute("cambioCarrera", cambioCarrera);
		modelo.addAttribute("carreraAlum", carreraAlum);
		modelo.addAttribute("carreraFM3", carreraFM3);
		modelo.addAttribute("extranjero", extranjero);
		modelo.addAttribute("facultad", facultad);
		modelo.addAttribute("nombreFacultad", nombreFacultad);
		modelo.addAttribute("nombreCarrera", nombreCarrera);
		modelo.addAttribute("listaPlanes", listaPlanes);
		modelo.addAttribute("nacion", nacion);
		modelo.addAttribute("mapCarreraPlan", mapCarreraPlan);
		modelo.addAttribute("mapNombrePlan", mapNombrePlan);
		
		return "cartas/constancia/form";
	}
	
	@RequestMapping("/cartas/constancia/show")
	public String cartasConstanciaShow(HttpServletRequest request){
		return "cartas/constancia/show";
	}
	
	@RequestMapping("/cartas/constancia/view")
	public String cartasConstanciaView(HttpServletRequest request, Model modelo){
		
		String codigoPersonal = "";
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal 		= (String) sesion.getAttribute("codigoAlumno");
		}
		
		String nombreAlumno 		= alumPersonalDao.getNombreAlumno(codigoPersonal,"NOMBRE");
		String planId				= alumPlanDao.getPlanActual(codigoPersonal);		
		boolean esNuevo				= alumPersonalDao.esNuevoIngreso(codigoPersonal, planId);	
		int nacionalidad			= Integer.parseInt(alumPersonalDao.getNacionalidad(codigoPersonal));
		boolean	tieneFM3 			= false;
		
		
		try {
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");	
			java.util.Date fechaHoy = sdf.parse(aca.util.Fecha.getHoy());
			java.util.Date fechaFM3 = sdf.parse(legExtDoctosDao.getFechaVenceFM3(codigoPersonal));   
			if (fechaHoy.before(fechaFM3)) tieneFM3 = true;	    
		}catch (Exception ex){
			System.out.println("Error - aca.web.cartas.ContCartasConstancias:"+ex);			
		}		
		
	    Parametros parametros = parametrosDao.mapeaRegId("1");
		
		modelo.addAttribute("codigoPersonal", codigoPersonal);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("esNuevo", esNuevo);
		modelo.addAttribute("nacionalidad", nacionalidad);
		modelo.addAttribute("tieneFM3", tieneFM3);
		modelo.addAttribute("parametros", parametros);
		
		return "cartas/constancia/view";
	}
	
}