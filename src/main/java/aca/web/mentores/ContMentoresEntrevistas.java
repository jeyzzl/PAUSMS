package aca.web.mentores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatPeriodo;
import aca.catalogo.spring.CatPeriodoDao;
import aca.mentores.spring.MentAcceso;
import aca.mentores.spring.MentAccesoDao;
import aca.mentores.spring.MentAlumnoDao;
import aca.mentores.spring.MentCarrera;
import aca.mentores.spring.MentCarreraDao;
import aca.mentores.spring.MentContacto;
import aca.mentores.spring.MentContactoDao;
import aca.mentores.spring.MentMotivo;
import aca.mentores.spring.MentMotivoDao;
import aca.vista.spring.Inscritos;
import aca.vista.spring.InscritosDao;
import aca.vista.spring.InscritosMapper;
import aca.vista.spring.MaestrosDao;
import aca.vista.spring.UsuariosDao;

@Controller
public class ContMentoresEntrevistas {	
	
	@Autowired
	MentAccesoDao mentAccesoDao;
	
	@Autowired
	AccesoDao accesoDao;
	
	@Autowired
	MaestrosDao maestrosDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	CatFacultadDao catFacultadDao;
	
	@Autowired
	UsuariosDao usuariosDao;
	
	@Autowired
	CatPeriodoDao catPeriodoDao;
	
	@Autowired
	MentCarreraDao mentCarreraDao;
	
	@Autowired
	MentContactoDao mentContactoDao;	
	
	@Autowired
	MentAlumnoDao mentAlumnoDao;
	
	@Autowired
	MentMotivoDao mentMotivoDao;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;	
	
	
	@RequestMapping("/mentores/entrevistas/menu")
	public String mentoresEntrevistasMenu(HttpServletRequest request, Model modelo){		
		
		return "mentores/entrevistas/menu";
	}
	
	@RequestMapping("/mentores/entrevistas/mentores")
	public String mentoresEntrevistasMentores(HttpServletRequest request, Model modelo){
		
		String periodoId		= request.getParameter("periodo")==null?"0":request.getParameter("periodo"); 	
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	if (periodoId.equals("0")) {
    			periodoId = (String) sesion.getAttribute("ciclo");
    		}
        }	
	   	
		List<CatPeriodo> lisPeriodos 				= catPeriodoDao.getListAll(" ORDER BY PERIODO_ID");		
		List<String> lisMentores 					= mentCarreraDao.listMentoresPeriodo(periodoId, " ORDER BY USUARIO_APELLIDO(MENTOR_ID)");			
		HashMap<String,String> mapaEntrevistas 		= mentContactoDao.mapEntrevistasPeriodo(periodoId);		
		HashMap<String,String> mapaAlumnos			= mentAlumnoDao.getAlumPorMentL(periodoId);			
		HashMap<String,String> mapaMentores			= maestrosDao.mapMaestroNombre("APELLIDOS");
		
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("lisMentores", lisMentores);
		modelo.addAttribute("mapaEntrevistas", mapaEntrevistas);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("mapaMentores", mapaMentores);
		
		return "mentores/entrevistas/mentores";
	}
	
	@RequestMapping("/mentores/entrevistas/entrevistas")
	public String mentoresEntrevistasEntrevistas(HttpServletRequest request, Model modelo){
		
		String periodoId	 				= request.getParameter("PeriodoId");
		String mentorId						= request.getParameter("MentorId");
		String mentorNombre 				= "-";	
		if (maestrosDao.existeReg(mentorId)) {
			mentorNombre = maestrosDao.getNombreMaestro(mentorId, "NOMBRE"); 
		}
		
		List<MentContacto> lisEntrevistas 			= mentContactoDao.getListAlumnosEnt(mentorId, periodoId, " ORDER BY ENOC.MENT_CONTACTO.FECHA_CONTACTO");
		HashMap<String,MentMotivo> mapaMotivos	 	= mentMotivoDao.mapMotivo();
		HashMap<String,String> mapaAlumnos	 		= alumPersonalDao.mapaAlumnosEntrevistados(periodoId);
		
		modelo.addAttribute("mentorNombre", mentorNombre);
		modelo.addAttribute("lisEntrevistas", lisEntrevistas);
		modelo.addAttribute("mapaMotivos", mapaMotivos);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		
		return "mentores/entrevistas/entrevistas";
	}
	
	@RequestMapping("/mentores/entrevistas/entra_facultad")
	public String mentoresEntrevistasEntraFacultad(HttpServletRequest request, Model modelo){
		String codigoPersonal	= "0";
		String periodoId		= request.getParameter("periodo")==null?"0":request.getParameter("periodo"); 	
		MentAcceso mentAcceso = new MentAcceso();
		Acceso acceso = new Acceso();
		HttpSession sesion		= ((HttpServletRequest)request).getSession();		
        if (sesion!=null){
        	codigoPersonal = (String) sesion.getAttribute("codigoPersonal");
        	if (periodoId.equals("0")) {
    			periodoId = (String) sesion.getAttribute("ciclo");
    		}
        	mentAcceso = mentAccesoDao.mapeaRegId(codigoPersonal);
        	acceso = accesoDao.mapeaRegId(codigoPersonal);
        }	
    	
        List<CatPeriodo> lisPeriodo 						= catPeriodoDao.getListAll("ORDER BY PERIODO_ID");
       	List<CatCarrera> lisCarrera							= catCarreraDao.getListAll("ORDER BY 1,4"); 	
       	HashMap<String,String> mapaMentores					= mentCarreraDao.mapNumMentoresCarrera(periodoId);
       	HashMap<String,CatFacultad> mapaFacultades 			= catFacultadDao.getMapFacultad("");
       	
    	modelo.addAttribute("mentAcceso", mentAcceso);
    	modelo.addAttribute("acceso", acceso);
       	modelo.addAttribute("lisPeriodo", lisPeriodo);
		modelo.addAttribute("lisCarrera", lisCarrera);
		modelo.addAttribute("mapaMentores", mapaMentores);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		
		return "mentores/entrevistas/entra_facultad";
	}
	
	@RequestMapping("/mentores/entrevistas/entra_mentor")
	public String mentoresEntrevistasEntraMentor(HttpServletRequest request, Model modelo){
		String codigoPersonal	= "0";
		String periodoId		= request.getParameter("periodo")==null?"0":request.getParameter("periodo"); 	
		String carreraId		= request.getParameter("carreraId")==null?"0":request.getParameter("carreraId");
		String carreraNombre	= catCarreraDao.getNombreCarrera(carreraId);
		String facultadId 		= catCarreraDao.getFacultadId(carreraId);
		String facultadNombre	= catFacultadDao.getNombreCorto(facultadId);
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();		
        if (sesion!=null){
        	codigoPersonal = (String) sesion.getAttribute("codigoPersonal");
        	if (periodoId.equals("0")) {
    			periodoId = (String) sesion.getAttribute("ciclo");
    		}
        }	
        
        List<MentCarrera> lisMenCarrera	= mentCarreraDao.getListCarrera(carreraId, periodoId, "ORDER BY 3");
        
		HashMap<String,String> mapaEntrevistas 			= mentContactoDao.mapEntrevistasCarrera(periodoId);	
		HashMap<String,String> mapaMaestros 			= maestrosDao.mapMaestroNombre("NOMBRE");
		
		modelo.addAttribute("facultadNombre", facultadNombre);
		modelo.addAttribute("carreraNombre", carreraNombre);
		modelo.addAttribute("lisMenCarrera", lisMenCarrera);	
        modelo.addAttribute("mapaEntrevistas", mapaEntrevistas);               
        modelo.addAttribute("mapaMaestros", mapaMaestros);
        
		return "mentores/entrevistas/entra_mentor";
	}
	
	@RequestMapping("/mentores/entrevistas/frm_mentor_contacto")
	public String mentoresEntrevistasEntraMentorFrmMentor_contacto(HttpServletRequest request, Model modelo){
		
		String periodoId		= request.getParameter("periodo")==null?"0":request.getParameter("periodo"); 	
		String carreraId		= request.getParameter("carreraId")==null?"0":request.getParameter("carreraId");
		String mentorId			= request.getParameter("idMentor")==null?"0":request.getParameter("idMentor");
		String accion 			= request.getParameter("Accion")==null?"1":request.getParameter("Accion");
		String carreraNombre	= catCarreraDao.getNombreCarrera(carreraId);
		String facultadId 		= catCarreraDao.getFacultadId(carreraId);
		String facultadNombre	= catFacultadDao.getNombreCorto(facultadId);
		String mentorNombre		= maestrosDao.getNombreMaestro(mentorId,"NOMBRE");
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();		
        if (sesion!=null){        	
        	if (periodoId.equals("0")) {
    			periodoId = (String) sesion.getAttribute("ciclo");
    		}
        }
        
        List<MentContacto> lisMenContacto 	= new ArrayList<MentContacto>();
        if (accion.equals("1")){		
    		lisMenContacto	= mentContactoDao.getListEntCarrera(mentorId, carreraId, periodoId, "ORDER BY 3");
    	}else{
    		lisMenContacto	= mentContactoDao.getHistorial(mentorId, "ORDER BY 1");	
    	}        
        
        HashMap<String,MentMotivo> mapaMotivo 		= mentMotivoDao.mapMotivo();
        
		HashMap<String,String> mapaEntrevistas 			= mentContactoDao.mapEntrevistasCarrera(periodoId);			  
		HashMap<String,String> mapaAlumnos				= alumPersonalDao.mapaAlumnosEnEntrevistas(periodoId);
		
		modelo.addAttribute("mentorNombre", mentorNombre);
		modelo.addAttribute("lisMenContacto", lisMenContacto);
		modelo.addAttribute("mapaMotivo", mapaMotivo);
		modelo.addAttribute("facultadNombre", facultadNombre);
		modelo.addAttribute("carreraNombre", carreraNombre);	
        modelo.addAttribute("mapaEntrevistas", mapaEntrevistas);
        modelo.addAttribute("mapaAlumnos", mapaAlumnos);
        
		return "mentores/entrevistas/frm_mentor_contacto";
	}
	
}