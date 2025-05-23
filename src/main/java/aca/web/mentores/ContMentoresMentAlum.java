package aca.web.mentores;

import java.util.ArrayList;
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

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlanDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatPeriodo;
import aca.catalogo.spring.CatPeriodoDao;
import aca.mentores.spring.MentAcceso;
import aca.mentores.spring.MentAccesoDao;
import aca.mentores.spring.MentAlumno;
import aca.mentores.spring.MentAlumnoDao;
import aca.mentores.spring.MentCarrera;
import aca.mentores.spring.MentCarreraDao;
import aca.mentores.spring.MentContacto;
import aca.mentores.spring.MentContactoDao;
import aca.mentores.spring.MentMotivo;
import aca.mentores.spring.MentMotivoDao;
import aca.portal.spring.AlumnoPortalDao;
import aca.util.Fecha;
import aca.vista.spring.InscritosDao;
import aca.vista.spring.Maestros;
import aca.vista.spring.MaestrosDao;
import aca.vista.spring.Usuarios;
import aca.vista.spring.UsuariosDao;

@Controller
public class ContMentoresMentAlum {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	MentAccesoDao mentAccesoDao;

	@Autowired
	CatPeriodoDao catPeriodoDao;

	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	AccesoDao accesoDao;
	
	@Autowired
	CatFacultadDao catFacultadDao;
	
	@Autowired
	MentCarreraDao mentCarreraDao;
	
	@Autowired
	MentMotivoDao mentMotivoDao;
	
	@Autowired
	MentContactoDao mentContactoDao;

	@Autowired
	MentAlumnoDao mentAlumnoDao;
	
	@Autowired
	MaestrosDao maestrosDao;
	
	@Autowired
	UsuariosDao usuariosDao;
	
	@Autowired
	AlumnoPortalDao alumnoPortalDao;
	
	@Autowired
	AlumPlanDao alumPlanDao;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;
	
	@Autowired
	InscritosDao inscritosDao;
	
	@RequestMapping("/mentores/ment_alum/carrera")
	public String mentoresMentAlumCarrera(HttpServletRequest request, Model modelo){
		
		String codigoPersonal	= "0";
		String periodoId		= "0";
		String fecha 			= "-";
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");
			periodoId			= request.getParameter("periodo") == null ? (String) sesion.getAttribute("ciclo") : request.getParameter("periodo");
			fecha 				= (String) sesion.getAttribute("fechaMentores");
		}	
		
		if( fecha == null ){
			fecha = aca.util.Fecha.getHoy();
			sesion.setAttribute("fechaMentores", fecha);
		}
		if( request.getParameter("fechaMentores") != null ){
			fecha = request.getParameter("fechaMentores") ;
			sesion.setAttribute("fechaMentores", fecha);
		}
		
		String sModulo		= request.getParameter("moduloId");
		String sCarpeta     = request.getParameter("carpeta");
		
		MentAcceso mentAcceso 			= mentAccesoDao.mapeaRegId(codigoPersonal);
		Acceso acceso 					= accesoDao.mapeaRegId(codigoPersonal);
		
		List<CatPeriodo> lisPeriodo 	= catPeriodoDao.getListAll("ORDER BY PERIODO_ID DESC");
		List<CatCarrera> lisCarrera		= catCarreraDao.ListCarrerasConMentores(periodoId, " ORDER BY 1,4");		
		
		HashMap<String,CatFacultad> mapFacultad 		= catFacultadDao.getMapFacultad("");
		HashMap<String,String> mapNumMentoresCarrera 	= mentCarreraDao.mapNumMentoresCarrera();
		HashMap<String,String> mapEntrevistasCarrera	= mentContactoDao.mapEntrevistasPorCarrera(periodoId);
		
		modelo.addAttribute("codigoPersonal", codigoPersonal);
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("fecha", fecha);
		modelo.addAttribute("sModulo", sModulo);
		modelo.addAttribute("sCarpeta", sCarpeta);		
		modelo.addAttribute("mentAcceso", mentAcceso);		
		modelo.addAttribute("lisPeriodo", lisPeriodo);		
		modelo.addAttribute("lisCarrera", lisCarrera);
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("mapFacultad", mapFacultad);
		modelo.addAttribute("mapNumMentoresCarrera", mapNumMentoresCarrera);
		modelo.addAttribute("mapEntrevistasCarrera", mapEntrevistasCarrera);
				
		return "mentores/ment_alum/carrera";
	}

	@RequestMapping("/mentores/ment_alum/mentor")
	public String mentoresMentAlumMentor(HttpServletRequest request, Model modelo){
		String codigoPersonal	= "0";
		String periodoId		= "0";
		String carreraId		= request.getParameter("carrera") == null ? "0" : request.getParameter("carrera");
		String accion			= request.getParameter("Accion") == null ? "0" : request.getParameter("Accion");
		String fecha 			= "-";
		String facultadId		= catCarreraDao.getFacultadId(carreraId);		
		boolean tieneAcceso 	= false;
		String nombreCarrera	= ""; 
		String nombreFacultad	= "";
		String nombrePeriodo	= "";		
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");
			periodoId			= request.getParameter("periodo") == null ? (String) sesion.getAttribute("ciclo") : request.getParameter("periodo");
			fecha 				= (String) sesion.getAttribute("fechaMentores");
			
			if( fecha == null ){
				fecha = aca.util.Fecha.getHoy();
				sesion.setAttribute("fechaMentores", fecha);
			}			
			if (Boolean.parseBoolean(sesion.getAttribute("admin")+"") || accesoDao.esSupervisor(codigoPersonal) || 
				mentAccesoDao.tieneAccesoCarrera(codigoPersonal, carreraId)){
				tieneAcceso = true;
			}
		}
		
		nombreCarrera 		= catCarreraDao.getNombreCarrera(carreraId);
		nombreFacultad 		= catFacultadDao.getNombreFacultad(facultadId);
		nombrePeriodo 		= catPeriodoDao.getNombre(periodoId);		
		Acceso acceso 		= accesoDao.mapeaRegId(codigoPersonal);
		
		List<MentCarrera> lisMentCarrera	= mentCarreraDao.getListCarrera(carreraId, periodoId, "ORDER BY EMP_NOMBRE(MENTOR_ID)");
		
		HashMap<String,String> mapaAlumPorMent			= mentAlumnoDao.getAlumPorMent(periodoId);
		HashMap<String,String> mapaAlumActivos			= mentAlumnoDao.mapaAlumPorMentEnCarrera(periodoId);
		HashMap<String,String> mapMaestroNombre      	= maestrosDao.mapMaestroNombre("NOMBRE");
		HashMap<String,String> mapEntrevistas			= mentContactoDao.mapEntrevistasPeriodo(periodoId);
		
		modelo.addAttribute("codigoPersonal", codigoPersonal);
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("fecha", fecha);
		modelo.addAttribute("carreraId", carreraId);
		modelo.addAttribute("accion", accion);
		modelo.addAttribute("facultadId", facultadId);
		modelo.addAttribute("acceso", acceso);		
		modelo.addAttribute("tieneAcceso", tieneAcceso);
		modelo.addAttribute("nombreCarrera", nombreCarrera);
		modelo.addAttribute("nombreFacultad", nombreFacultad);
		modelo.addAttribute("nombrePeriodo", nombrePeriodo);
		modelo.addAttribute("lisMentCarrera", lisMentCarrera);
		modelo.addAttribute("mapaAlumPorMent", mapaAlumPorMent);
		modelo.addAttribute("mapaAlumActivos", mapaAlumActivos);
		modelo.addAttribute("mapMaestroNombre", mapMaestroNombre);
		modelo.addAttribute("mapEntrevistas", mapEntrevistas);
		
		return "mentores/ment_alum/mentor";
	}
	
	@RequestMapping("/mentores/ment_alum/borrarMentor")
	public String mentoresMentAlumBorrarMentor(HttpServletRequest request, Model modelo){		
		String mentorId 		= request.getParameter("MentorId")==null?"0":request.getParameter("MentorId");
		String carreraId		= request.getParameter("CarreraId") == null ?"0":request.getParameter("CarreraId");
		String periodoId 		= "0";
		String mensaje 			= "-";		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){						
			periodoId			= request.getParameter("periodo")==null?(String)sesion.getAttribute("ciclo"):request.getParameter("periodo");
		}
				
		if(!mentAlumnoDao.tieneAlumnosPeriodoCarrera(mentorId, periodoId, carreraId)){			
			if(mentCarreraDao.deleteReg(carreraId,mentorId,periodoId)){
				mensaje = "<div class='alert alert-success'>Mentor Deleted</div>";
			}else{
				mensaje = "<div class='alert alert-danger'>Error deleting the mentor</div>";
			}
		}else{
			mensaje = "<div class='alert alert-danger'>This mentor has assigned students. Remove assigned students in order to delete</div>";	
		}
		
		return "redirect:/mentores/ment_alum/mentor?carrera="+carreraId+"&periodo="+periodoId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/mentores/ment_alum/buscar")
	public String mentoresMentAlumBuscar(HttpServletRequest request, Model modelo){
		
		String origen			= request.getParameter("Origen") == null ? "X" : request.getParameter("Origen");		
		String carrera			= request.getParameter("carreraId") == null ? "X" : request.getParameter("carreraId");
//		String sCarpeta 		= sCarpetab;
		String sAccion			= request.getParameter("Accion") == null ? "0" : request.getParameter("Accion");

		List<Usuarios> lisUsuarios = new ArrayList<Usuarios>();	
		
		String opcion			= "Empleado";
		String color			= "";
		
		int nAccion 			= Integer.parseInt(sAccion);
		String sResultado		= "<font size='2'>Select query option</font>";
		
		String nombreCompleto 	= "";
		
		String sModulo			= request.getParameter("moduloId");
		String periodoId		= "0";
		String codigoEmpleado	= "0";
		String codigoPersonal	= "0";
		boolean existeUsuario   = false;
		Usuarios usuarios 		= new Usuarios();;
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoEmpleado	= request.getParameter("empleado") == null ? (String) sesion.getAttribute("codigoEmpleado") : request.getParameter("empleado");
			codigoPersonal 	= request.getParameter("CodigoPersonal") == null ? (String) sesion.getAttribute("codigoPersonal") : request.getParameter("CodigoPersonal");
			periodoId		= request.getParameter("periodo") == null ? (String) sesion.getAttribute("ciclo") : request.getParameter("periodo");
		}
		
		String accion			= request.getParameter("accion")==null?"0":request.getParameter("accion");
		String msj				= "";		
		
		boolean completo = (request.getParameter("Completo") != null && !request.getParameter("Completo").equals("")) ? true : false;
		
		switch (nAccion){
			// Busqueda por nombre
			case 1:{
				nombreCompleto	= request.getParameter("NombreCompleto")==null ? "" : request.getParameter("NombreCompleto");
				if(!nombreCompleto.equals("")){
					nombreCompleto = nombreCompleto.replaceAll(" ","%");
					lisUsuarios = usuariosDao.getListAllFltro(nombreCompleto, opcion, " ORDER BY NOMBRE||APELLIDO_PATERNO||APELLIDO_MATERNO");
		
					if (lisUsuarios.size() > 0){
						sResultado	= "<font size='2'>Click the green button to save the mentor</font>";
					}
					else{
						sResultado = "<font size='2' color='#AE2113'>No results found</font>";
					}
				}
				break;
			} 
			
			// Busqueda por codigo
			case 2:{
				if(usuariosDao.existeReg(codigoPersonal)){
					usuariosDao.mapeaRegId(codigoPersonal);
					sResultado = "<font size='2'>Click the green button to save the mentor</font>";
				}
				else{
					sResultado = "<font size='2' color='#AE2113'>Not found: "+codigoPersonal+"</font>";
				}
				if(usuariosDao.existeReg(codigoPersonal)) {
					existeUsuario = true;
					usuarios = usuariosDao.mapeaRegId(codigoPersonal);
				}
			break;
			}
		}
		
		color = alumnoPortalDao.obtenColor(codigoPersonal);

		modelo.addAttribute("origen", origen);
		modelo.addAttribute("carrera", carrera);
		modelo.addAttribute("sAccion", sAccion);
		modelo.addAttribute("opcion", opcion);
		modelo.addAttribute("nAccion", nAccion);
		modelo.addAttribute("sResultado", sResultado);
		modelo.addAttribute("nombreCompleto", nombreCompleto);
		modelo.addAttribute("sModulo", sModulo);
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("codigoEmpleado", codigoEmpleado);
		modelo.addAttribute("codigoPersonal", codigoPersonal);
		modelo.addAttribute("accion", accion);
		modelo.addAttribute("msj", msj);
		modelo.addAttribute("completo", completo);
		modelo.addAttribute("lisUsuarios", lisUsuarios);
		modelo.addAttribute("existeUsuario", existeUsuario);
		modelo.addAttribute("usuarios", usuarios);
		modelo.addAttribute("color", color);
				
		return "mentores/ment_alum/buscar";
	}
	
	@RequestMapping("/mentores/ment_alum/grabarMentor")
	public String mentoresMentAlumGrabarMentor(HttpServletRequest request, Model modelo){		
		String mentorId			= request.getParameter("empleado") == null ? "0" : request.getParameter("empleado");
		String carrera			= request.getParameter("carreraId") == null ? "0" : request.getParameter("carreraId");
		String periodoId 		= "0";
		String mensaje 			= "-";
		MentCarrera mentCarrera	= new MentCarrera();
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){		
			periodoId		= request.getParameter("periodo") == null ? (String) sesion.getAttribute("ciclo") : request.getParameter("periodo");			
		}		
		mentCarrera.setPeriodoId(periodoId);
		mentCarrera.setCarreraId(carrera);
		mentCarrera.setMentorId(mentorId);
		if(!mentCarreraDao.existeReg(carrera,mentorId,periodoId)){
			if(mentCarreraDao.insertReg(mentCarrera)){				
				mensaje = "<div class='alert alert-succes'>Assigned mentor</div>";
			}
		}else{
			mensaje = "<div class='alert alert-danger'>This professor is already assigned</div>";
		}		
		return "redirect:/mentores/ment_alum/mentor?carrera="+carrera+"&periodo="+periodoId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/mentores/ment_alum/carreraMentor")
	public String mentoresMentAlumCarreraMentor(HttpServletRequest request, Model modelo){
		
		String periodoId		= "0";
		String carreraId		= request.getParameter("carreraId") == null ? "X" : request.getParameter("carreraId");
		String empleado			= "0";
		String empleadoNombre 	= "-"; 
		
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			periodoId			= request.getParameter("periodo") == null ? (String) sesion.getAttribute("ciclo") : request.getParameter("periodo");
			empleado 			= (String) sesion.getAttribute("codigoEmpleado");
			if (maestrosDao.existeReg(empleado)) {
				empleadoNombre 	= maestrosDao.getNombreMaestro(empleado, "NOMBRE"); 
			}			
		}
		
		List<MentCarrera> lisMentCarrera				= mentCarreraDao.getCarrerasMentor(periodoId, empleado, "");
		HashMap<String,CatFacultad> mapaFacultades 		= catFacultadDao.getMapFacultad("");
		HashMap<String,CatCarrera> mapaCarreras 		= catCarreraDao.getMapAll("");
		HashMap<String,String> mapaMaestros 			= maestrosDao.mapMaestroNombre("NOMBRE");		
		
		modelo.addAttribute("empleadoNombre", empleadoNombre);
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("carreraId", carreraId);
		modelo.addAttribute("lisMentCarrera", lisMentCarrera);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaMaestros", mapaMaestros);
				
		return "mentores/ment_alum/carreraMentor";
	}

	@RequestMapping("/mentores/ment_alum/mentor_alumno")
	public String mentoresMentAlumMentorAlumno(HttpServletRequest request, Model modelo){
		
		String codigoPersonal	= "0";
		String periodoId		= "0";
		String fecha 			= "-";
		String carreraId		= request.getParameter("carrera") == null ? "X" : request.getParameter("carrera"); 
		String folio			= request.getParameter("folio") == null ? "X" : request.getParameter("folio");		
		String matricula 		= request.getParameter("matricula");
		String mentorId			= request.getParameter("mentor");
		String accion			= request.getParameter("Accion") == null ? "" : request.getParameter("Accion");
		String hoy 				= aca.util.Fecha.getHoy();
		String sFacultad		= catCarreraDao.getFacultadId(carreraId);
		String alumFac			= catCarreraDao.getFacultadId(alumPlanDao.getCarreraId(matricula));
		
	    String msj				= "";
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");
			periodoId			= request.getParameter("periodo") == null ? (String) sesion.getAttribute("ciclo") : request.getParameter("periodo");
			fecha 				= (String) sesion.getAttribute("fechaMentores");
			
			if( fecha == null ){
				fecha = aca.util.Fecha.getHoy();
				sesion.setAttribute("fechaMentores", fecha);
			}
		}	
		
		Acceso acceso 			= accesoDao.mapeaRegId(codigoPersonal);
		
		MentAlumno alumno = new MentAlumno();
		
		if(accion.equals("1")){	//Guardar el alumno con el mentor
	    	if( sFacultad.equals(alumFac) || Boolean.parseBoolean(sesion.getAttribute("admin")+"") ){
	    		alumno.setFolio(mentAlumnoDao.maximoRegFolio(periodoId, matricula));
	    		alumno.setPeriodoId(periodoId);
		    	alumno.setMentorId(mentorId);
		    	alumno.setCodigoPersonal(matricula);
		    	alumno.setFecha(Fecha.getHoy());
		    	alumno.setStatus("A");
		    	alumno.setCarreraId(alumPlanDao.getCarreraId(matricula));
		    	alumno.setFechaInicio(request.getParameter("fInicio"));
		    	alumno.setFechaFinal(request.getParameter("fFinal"));
	    		
	    		if(catPeriodoDao.existeFechaBetween(periodoId, alumno.getFechaInicio()) == false ||
	    		   catPeriodoDao.existeFechaBetween(periodoId, alumno.getFechaFinal()) == false){
	    			
	    			msj = "<div class='alert alert-danger'>Dates must be within the period range.</div>";
	    		}else if(
	    				mentAlumnoDao.existeFechaBetween(matricula, periodoId, alumno.getFechaInicio()) 
					|| 	mentAlumnoDao.existeFechaBetween(matricula, periodoId, alumno.getFechaFinal()) 
					|| 	mentAlumnoDao.existeFechaINIBetween(matricula, periodoId, alumno.getFechaInicio(), alumno.getFechaFinal())
					|| 	mentAlumnoDao.existeFechaFINBetween(matricula, periodoId, alumno.getFechaInicio(), alumno.getFechaFinal())
				
				){	    			
	    			MentAlumno mentAlumno = mentAlumnoDao.mapeaRegId(periodoId, matricula, hoy); 
	    			msj = "<div class='alert alert-danger'>The student ["+matricula+"] already has a mentor assigned for the date. The current mentor is: <strong>"+ usuariosDao.getNombreUsuario(mentAlumno.getMentorId(), "NOMBRE")+"  Degree:"+catCarreraDao.getNombreCarrera(mentAlumno.getCarreraId())+"</strong></div>";
	    		}else{
		    		if(mentAlumnoDao.insertReg(alumno)){		    			
		    			msj = "<div class='alert alert-success'>Student assigned to mentor</div>";
	    			}else{
	    				msj = "<div class='alert alert-danger'>Error updating. Try Again</div>";
		    		}
	    		}
	    	}else{
	    		msj = "<div class='alert alert-danger'>The student ["+matricula+"] is not enrolled or is not under this degree</div>";
	    	}
	    }else if(accion.equals("2")){ // Eliminar
	    	if(mentAlumnoDao.deleteReg(periodoId,mentorId,matricula,folio)){
	    		msj = "<div class='alert alert-success'>Student deleted</div>";
	    	}else{
	    		msj = "<div class='alert alert-danger'>Error deleting. Try again</div>";
	    	}
	    }else if(accion.equals("3")){
	    	alumno.setFechaInicio(request.getParameter("fechaInicial"));
	    	alumno.setFechaFinal(request.getParameter("fechaFinal"));
	       	alumno.setPeriodoId(request.getParameter("periodoId"));
	    	alumno.setMentorId(request.getParameter("mentorId"));
	    	alumno.setCodigoPersonal(request.getParameter("codigoAlum"));
	    	alumno.setFolio(request.getParameter("folioId"));
	    	if(mentAlumnoDao.updateFechas(alumno)){
	    		return "mentores/ment_alum/mentor_alumno?carrera="+carreraId+"&mentor="+request.getParameter("mentorId");
	    	}    	
	    }
		
		 if(request.getParameter("activos") != null && !request.getParameter("activos").equals("")){
			sesion.setAttribute("activos", request.getParameter("activos"));
		}
		 
		String activos = (String)sesion.getAttribute("activos") == null ? "1" : (String)sesion.getAttribute("activos");
		
		List<MentAlumno> lisMenAlumno = new ArrayList<MentAlumno>();
		
	    if(activos.equals("1")){
	    	lisMenAlumno	= mentAlumnoDao.getListActivos(mentorId, periodoId, "ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");
	    }else if(activos.equals("2")){
	    	lisMenAlumno 	= mentAlumnoDao.getListMentorPeriodo(mentorId, periodoId, fecha, "ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");
	    }
	    
	    String nombreMaestro = maestrosDao.getNombreMaestro(mentorId, "NOMBRE");
	    String nombrePeriodo = catPeriodoDao.getNombre(periodoId);
	    String nombreCarrera = catCarreraDao.getNombreCarrera(carreraId);
	    String nombreFacultad = catFacultadDao.getNombreFacultad(sFacultad);
	    
	    HashMap<String, AlumPersonal> mapMentorContacto = alumPersonalDao.mapMentorAconsejado(periodoId);
	    HashMap<String,CatCarrera> mapCarrera 			= catCarreraDao.getMapAll("");
	    HashMap<String, String> getMapaInscritos 		= inscritosDao.getMapaTodosInscritos();
		
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("carreraId", carreraId);
		modelo.addAttribute("accion", accion);
		modelo.addAttribute("fecha", fecha);
		modelo.addAttribute("mentorId", mentorId);
		modelo.addAttribute("msj", msj);
		modelo.addAttribute("sFacultad", sFacultad);
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("alumFac", alumFac);
		modelo.addAttribute("activos", activos);
		modelo.addAttribute("lisMenAlumno", lisMenAlumno);
		modelo.addAttribute("nombreMaestro", nombreMaestro);
		modelo.addAttribute("nombrePeriodo", nombrePeriodo);
		modelo.addAttribute("nombreCarrera", nombreCarrera);
		modelo.addAttribute("nombreFacultad", nombreFacultad);
		modelo.addAttribute("mapMentorContacto", mapMentorContacto);
		modelo.addAttribute("mapCarrera", mapCarrera);
		modelo.addAttribute("getMapaInscritos", getMapaInscritos);
		
		return "mentores/ment_alum/mentor_alumno";
	}
	
	@RequestMapping("/mentores/ment_alum/busca_clave_alumno")
	public String mentoresMentAlumBuscaClaveAlumno(HttpServletRequest request, Model modelo){
		
		String nombre = request.getParameter("nombre");
		
		List<AlumPersonal> lisPersonal	= alumPersonalDao.getListAlumTodo(nombre.trim().replaceAll(" ", "%"), "ORDER BY 3,4,2");
		
		modelo.addAttribute("nombre", nombre);
		modelo.addAttribute("lisPersonal", lisPersonal);
				
		return "mentores/ment_alum/busca_clave_alumno";
	}
	
	@RequestMapping("/mentores/ment_alum/totEnt")
	public String mentoresMentAlumTotEnt(HttpServletRequest request, Model modelo){
		
		String periodoId		= "0";
		String fecha 			= "0";
		
		String carreraId		= request.getParameter("carrera");
		String mentorId			= request.getParameter("mentor");
		
		String matricula		= request.getParameter("matricula");
		String sFacultad		= catCarreraDao.getFacultadId(carreraId);
		String alumFac			= catCarreraDao.getFacultadId(alumPlanDao.getCarreraId(matricula));
			
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			periodoId			= request.getParameter("periodo") == null ? (String) sesion.getAttribute("ciclo") : request.getParameter("periodo");
			fecha 				= (String) sesion.getAttribute("fechaMentores");
			
			if( fecha == null ){
				fecha = aca.util.Fecha.getHoy();
				sesion.setAttribute("fechaMentores", fecha);
			}
		}	
		
		String nombreCarrera 	= catCarreraDao.getNombreCarrera(carreraId);
		String nombreFacultad 	= catFacultadDao.getNombreFacultad(sFacultad);
		String nombreMaestro 	= maestrosDao.getNombreMaestro(mentorId, "NOMBRE");
		 
		List <MentContacto> lisMenAlumno  = mentContactoDao.getListAlumnosEnt(mentorId, periodoId, fecha, "ORDER BY CODIGO_PERSONAL");
		
		HashMap<String, MentMotivo> mapMotivo 		= mentMotivoDao.mapMotivo();
		HashMap<String,CatCarrera> mapCarrera 		= catCarreraDao.getMapAll("");
		HashMap<String, String> getMapaInscritos 	= inscritosDao.getMapaTodosInscritos();
		HashMap<String, AlumPersonal> mapMentorContacto = alumPersonalDao.mapMentorContacto(periodoId);
		
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("fecha", fecha);
		modelo.addAttribute("carreraId", carreraId);
		modelo.addAttribute("mentorId", mentorId);
		modelo.addAttribute("sFacultad", sFacultad);
		modelo.addAttribute("alumFac", alumFac);
		modelo.addAttribute("nombreCarrera", nombreCarrera);
		modelo.addAttribute("nombreFacultad", nombreFacultad);
		modelo.addAttribute("nombreMaestro", nombreMaestro);
		modelo.addAttribute("lisMenAlumno", lisMenAlumno);
		modelo.addAttribute("mapMotivo", mapMotivo);
		modelo.addAttribute("mapCarrera", mapCarrera);
		modelo.addAttribute("getMapaInscritos", getMapaInscritos);
		modelo.addAttribute("mapMentorContacto", mapMentorContacto);
		
		return "mentores/ment_alum/totEnt";
	}
	
	@RequestMapping("/mentores/ment_alum/altaMentor")
	public String mentoresMentAlumAltaMentor(HttpServletRequest request, Model modelo){
		
		String periodoId	 	= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String periodoNombre 	= catPeriodoDao.getNombre(periodoId);
		String codigoPersonal	= "0";
		Acceso acceso 			= new Acceso();
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal		= (String)sesion.getAttribute("codigoPersonal");			
			acceso 				= accesoDao.mapeaRegId(codigoPersonal);		
		}		
		List<Maestros> lisMaestros		= maestrosDao.getListAll(" ORDER BY APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE");
		List<CatCarrera> lisCarreras	= catCarreraDao.getListAll(" ORDER BY FACULTAD_ID, NIVEL_ID, NOMBRE_CARRERA");		 
	
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("periodoNombre", periodoNombre);
		modelo.addAttribute("lisMaestros", lisMaestros);
		modelo.addAttribute("lisCarreras", lisCarreras);
		
				
		return "mentores/ment_alum/altaMentor";
	}
	
	@RequestMapping("/mentores/ment_alum/addMentor")
	public String mentoresMentAlumAddMentor(HttpServletRequest request, Model modelo){
		
		String periodoId	 	= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String mentorId		 	= request.getParameter("MentorId")==null?"0":request.getParameter("MentorId");
		String carreraId	 	= request.getParameter("CarreraId")==null?"0":request.getParameter("CarreraId");
		String mensaje 			= "-"; 
		
		MentCarrera mentCarrera = new MentCarrera();
		mentCarrera.setPeriodoId(periodoId);
		mentCarrera.setCarreraId(carreraId);
		mentCarrera.setMentorId(mentorId);		
		if (mentCarreraDao.existeReg(carreraId, mentorId, periodoId)==false){
			if (mentCarreraDao.insertReg(mentCarrera)) {
				mensaje = "Saved";
			}else {
				mensaje = "Error saving";
			}
		}
		return "redirect:/mentores/ment_alum/carrera?PeriodoId="+periodoId+"&Mensaje="+mensaje;
	}	
}