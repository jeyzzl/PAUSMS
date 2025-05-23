package aca.web.exalumnos;

import java.sql.SQLException;
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
import org.springframework.web.bind.annotation.ResponseBody;

import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatPaisDao;
import aca.exa.spring.ExaCorreo;
import aca.exa.spring.ExaCorreoDao;
import aca.exa.spring.ExaDireccion;
import aca.exa.spring.ExaDireccionDao;
import aca.exa.spring.ExaEgreso;
import aca.exa.spring.ExaEgresoDao;
import aca.exa.spring.ExaEmpleo;
import aca.exa.spring.ExaEmpleoDao;
import aca.exa.spring.ExaEstado;
import aca.exa.spring.ExaEstadoDao;
import aca.exa.spring.ExaEstudio;
import aca.exa.spring.ExaEstudioDao;
import aca.exa.spring.ExaEvento;
import aca.exa.spring.ExaEventoDao;
import aca.exa.spring.ExaFamilia;
import aca.exa.spring.ExaFamiliaDao;
import aca.exa.spring.ExaIglesia;
import aca.exa.spring.ExaIglesiaDao;
import aca.exa.spring.ExaPais;
import aca.exa.spring.ExaPaisDao;
import aca.exa.spring.ExaRed;
import aca.exa.spring.ExaRedDao;
import aca.exa.spring.ExaTelefono;
import aca.exa.spring.ExaTelefonoDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;

@Controller
public class ContExalumnosExalumno {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	ExaEventoDao exaEventoDao;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;
	
	@Autowired
	CatPaisDao catPaisDao;
	
	@Autowired
	ExaCorreoDao exaCorreoDao;
	
	@Autowired
	ExaDireccionDao exaDireccionDao;	
	
	@Autowired
	ExaFamiliaDao exaFamiliaDao;
	
	@Autowired
	ExaEgresoDao exaEgresoDao;
	
	@Autowired
	ExaEmpleoDao exaEmpleoDao;
	
	@Autowired
	ExaIglesiaDao exaIglesiaDao;
	
	@Autowired
	ExaTelefonoDao exaTelefonoDao;
	
	@Autowired
	ExaRedDao exaRedDao;
	
	@Autowired
	ExaPaisDao exaPaisDao;
	
	@Autowired
	ExaEstadoDao exaEstadoDao;
	
	@Autowired
	CatFacultadDao catFacultadDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	MapaPlanDao mapaPlanDao;	
	
	@Autowired
	ExaEstudioDao exaEstudioDao; 
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}	
	
	@ResponseBody
	@RequestMapping("/exalumnos/exalumno/getEstados")
	public String exalumnosExalumnoGetEstados(HttpServletRequest request){
		//enviarConEnoc(request,"Error-aca.ContExalumnosExalumno|exalumnosExalumnoGetEstados");
		String paisId = request.getParameter("pais")==null?"0":request.getParameter("pais");	
		List<ExaEstado> lisEstados = exaEstadoDao.getLista(paisId, "ORDER BY 1");
		String estados = ""; 
		for(ExaEstado estado: lisEstados){
			estados += "<option value='"+estado.getEstadoId()+"'>"+estado.getNombreEstado()+"</option>";
		}
		return estados;
	}
	
	@RequestMapping("/exalumnos/exalumno/correo")
	public String exalumnosExalumnoCorreo(HttpServletRequest request, Model modelo){
		//enviarConEnoc(request,"Error-aca.ContExalumnosExalumno|exalumnosExalumnoCorreo");
		
		String matricula 		= "0";	
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			matricula 			= (String)sesion.getAttribute("codigoAlumno");
			sesion.setAttribute("exaOpcion", "correo");
		}
		String correoId = request.getParameter("correoId")==null?"0":request.getParameter("correoId");
		String accion = request.getParameter("Accion")==null?"":request.getParameter("Accion");
		
		AlumPersonal alumPersonal = new AlumPersonal();		
		alumPersonal 	= alumPersonalDao.mapeaRegId( matricula);		
		String nacionalidad 	= catPaisDao.getNacionalidad(alumPersonal.getNacionalidad());
		String alumnoNombre 	= alumPersonal.getNombre()+" "+alumPersonal.getApellidoPaterno()+" "+alumPersonal.getApellidoMaterno();
		
		ExaCorreo exaCorreo 	= new ExaCorreo();
		if(accion.equals("1")){//Grabar nuevo 
			String tiempo = aca.util.Fecha.getHoy()+" "+ aca.util.Fecha.getHoraDelDia()+":"+aca.util.Fecha.getMinutos()+":"+aca.util.Fecha.getSegundos();
			
			exaCorreo.setCorreoId(exaCorreoDao.maximoReg());
			exaCorreo.setMatricula(matricula);
			exaCorreo.setCorreo(request.getParameter("correo"));
			exaCorreo.setFechaAct(tiempo);
			exaCorreo.setEliminado("0");
			exaCorreo.setIdCorreo("-");			
			exaCorreoDao.insertReg(exaCorreo);
		}else if(accion.equals("2")){//Borrar		
			exaCorreoDao.eliminar( correoId); 
		}
		
		exaCorreo = exaCorreoDao.mapeaRegIdCorreo(exaCorreoDao.maximoReg(matricula));
		
		boolean existeCorreo	= exaCorreoDao.existeAlumno(matricula);
		boolean existeDireccion	= exaDireccionDao.existeAlumno(matricula);
		boolean existeFamilia	= exaFamiliaDao.existeAlumno(matricula);
		boolean existeEgreso	= exaEgresoDao.existeAlumno(matricula);
		boolean existeEmpleo	= exaEmpleoDao.existeAlumno(matricula);
		boolean existeTelefono	= exaTelefonoDao.existeAlumno(matricula);
		boolean existeIglesia	= exaIglesiaDao.existeAlumno(matricula);
		boolean existeRed		= exaRedDao.existeAlumno(matricula);
		
		
		String totCorreo 	 	= exaCorreoDao.getCantidadGenerico("ENOC.EXA_CORREO");
		String totDireccion	 	= exaCorreoDao.getCantidadGenerico("ENOC.EXA_DIRECCION");
		String totFamilia		= exaCorreoDao.getCantidadGenerico("ENOC.EXA_FAMILIA");
		String totEstudio		= exaCorreoDao.getCantidadGenerico("ENOC.EXA_EGRESO");
		String totEmpleo		= exaCorreoDao.getCantidadGenerico("ENOC.EXA_EMPLEO");
		String totIglesia		= exaCorreoDao.getCantidadGenerico("ENOC.EXA_IGLESIA");
		String totTelefono		= exaCorreoDao.getCantidadGenerico("ENOC.EXA_TELEFONO");
		String totRed			= exaCorreoDao.getCantidadGenerico("ENOC.EXA_REDSOCIAL");
		
		
		modelo.addAttribute("exaCorreo", exaCorreo);
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("alumnoNombre", alumnoNombre);
		modelo.addAttribute("nacionalidad", nacionalidad);
		modelo.addAttribute("existeCorreo", existeCorreo);
		modelo.addAttribute("existeDireccion", existeDireccion);
		modelo.addAttribute("existeFamilia", existeFamilia);
		modelo.addAttribute("existeEgreso", existeEgreso);
		modelo.addAttribute("existeEmpleo", existeEmpleo);
		modelo.addAttribute("existeTelefono", existeTelefono);
		modelo.addAttribute("existeIglesia", existeIglesia);		
		modelo.addAttribute("existeRed", existeRed);
		
		modelo.addAttribute("totCorreo", totCorreo);
		modelo.addAttribute("totDireccion", totDireccion);
		modelo.addAttribute("totFamilia", totFamilia);
		modelo.addAttribute("totEstudio", totEstudio);
		modelo.addAttribute("totEmpleo", totEmpleo);
		modelo.addAttribute("totIglesia", totIglesia);
		modelo.addAttribute("totTelefono", totTelefono);
		modelo.addAttribute("totRed", totRed);	
		
		return "exalumnos/exalumno/correo";
	}
	
	@RequestMapping("/exalumnos/exalumno/datos")
	public String exalumnosExalumnoDatos(HttpServletRequest request, Model modelo){
		
		String matricula 		= "0";
		String nacionalidad		= "-";		
		String alumnoNombre 	= "-";
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			matricula 			= (String)sesion.getAttribute("codigoAlumno");
			sesion.setAttribute("exaOpcion", "datos");
		}	
		
		AlumPersonal alumPersonal = new AlumPersonal();		
		alumPersonal 	= alumPersonalDao.mapeaRegId( matricula);		
		nacionalidad 	= catPaisDao.getNacionalidad(alumPersonal.getNacionalidad());
		alumnoNombre 	= alumPersonal.getNombre()+" "+alumPersonal.getApellidoPaterno()+" "+alumPersonal.getApellidoMaterno();
		
		boolean existeCorreo	= exaCorreoDao.existeAlumno(matricula);
		boolean existeDireccion	= exaDireccionDao.existeAlumno(matricula);
		boolean existeFamilia	= exaFamiliaDao.existeAlumno(matricula);
		boolean existeEgreso	= exaEgresoDao.existeAlumno(matricula);
		boolean existeEmpleo	= exaEmpleoDao.existeAlumno(matricula);
		boolean existeTelefono	= exaTelefonoDao.existeAlumno(matricula);
		boolean existeIglesia	= exaIglesiaDao.existeAlumno(matricula);
		boolean existeRed		= exaRedDao.existeAlumno(matricula);
		
		
		String totCorreo 	 	= exaCorreoDao.getCantidadGenerico("ENOC.EXA_CORREO");
		String totDireccion	 	= exaCorreoDao.getCantidadGenerico("ENOC.EXA_DIRECCION");
		String totFamilia		= exaCorreoDao.getCantidadGenerico("ENOC.EXA_FAMILIA");
		String totEstudio		= exaCorreoDao.getCantidadGenerico("ENOC.EXA_EGRESO");
		String totEmpleo		= exaCorreoDao.getCantidadGenerico("ENOC.EXA_EMPLEO");
		String totIglesia		= exaCorreoDao.getCantidadGenerico("ENOC.EXA_IGLESIA");
		String totTelefono		= exaCorreoDao.getCantidadGenerico("ENOC.EXA_TELEFONO");
		String totRed			= exaCorreoDao.getCantidadGenerico("ENOC.EXA_REDSOCIAL");
		
		
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("alumnoNombre", alumnoNombre);
		modelo.addAttribute("nacionalidad", nacionalidad);
		modelo.addAttribute("existeCorreo", existeCorreo);
		modelo.addAttribute("existeDireccion", existeDireccion);
		modelo.addAttribute("existeFamilia", existeFamilia);
		modelo.addAttribute("existeEgreso", existeEgreso);
		modelo.addAttribute("existeEmpleo", existeEmpleo);
		modelo.addAttribute("existeTelefono", existeTelefono);
		modelo.addAttribute("existeIglesia", existeIglesia);		
		modelo.addAttribute("existeRed", existeRed);
		
		modelo.addAttribute("totCorreo", totCorreo);
		modelo.addAttribute("totDireccion", totDireccion);
		modelo.addAttribute("totFamilia", totFamilia);
		modelo.addAttribute("totEstudio", totEstudio);
		modelo.addAttribute("totEmpleo", totEmpleo);
		modelo.addAttribute("totIglesia", totIglesia);
		modelo.addAttribute("totTelefono", totTelefono);
		modelo.addAttribute("totRed", totRed);
		
		
		return "exalumnos/exalumno/datos";
	}
	
	@RequestMapping("/exalumnos/exalumno/direccion")
	public String exalumnosExalumnoDireccion(HttpServletRequest request, Model modelo){
		//enviarConEnoc(request,"Error-aca.ContExalumnosExalumno|exalumnosExalumnoDireccion");
		
		String accion = request.getParameter("Accion")==null?"":request.getParameter("Accion");
		
		String matricula 		= "0";
		String alumnoNombre 	= "-";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			matricula 			= (String)sesion.getAttribute("codigoAlumno");
			sesion.setAttribute("exaOpcion", "direccion");
		}	
		
		AlumPersonal alumPersonal = new AlumPersonal();		
		alumPersonal 	= alumPersonalDao.mapeaRegId( matricula);		
		alumnoNombre 	= alumPersonal.getNombre()+" "+alumPersonal.getApellidoPaterno()+" "+alumPersonal.getApellidoMaterno();
		
		ExaDireccion direccion = new ExaDireccion();
		if(accion.equals("1")){//Grabar nuevo 
			String tiempo = aca.util.Fecha.getHoy()+" "+ aca.util.Fecha.getHoraDelDia()+":"+aca.util.Fecha.getMinutos()+":"+aca.util.Fecha.getSegundos();
			
			direccion.setDireccionId(exaDireccionDao.maximoReg());
			direccion.setMatricula(matricula);
			direccion.setCiudad(request.getParameter("ciudad")==null?"0":request.getParameter("ciudad"));
			direccion.setDireccion(request.getParameter("direccion"));
			direccion.setEstadoId(request.getParameter("estado")==null?"-":request.getParameter("estado"));
			direccion.setPaisId(request.getParameter("pais")==null?"-":request.getParameter("pais"));
			direccion.setCp(request.getParameter("codigoPostal")==null?"-":request.getParameter("codigoPostal"));
			direccion.setFechaActualizacion(tiempo);
			direccion.setEliminado("0");
			direccion.setIdDireccion("-");
			
			exaDireccionDao.insertReg(direccion);
		}else if(accion.equals("2")){//Borrar 
			exaDireccionDao.eliminar(request.getParameter("dirId")==null?"0":request.getParameter("dirId"));
		}
		
		List<ExaPais> lisPaises = exaPaisDao.getListAll( "ORDER BY 1");
		
		direccion = exaDireccionDao.mapeaRegIdEstudio(exaDireccionDao.maximoReg(matricula));
		
		String paisId = lisPaises.get(0).getPaisId();
		if(!direccion.getPaisId().equals("")){
			paisId = direccion.getPaisId();
		}
		List<ExaEstado> lisEstados = exaEstadoDao.getLista( paisId, "ORDER BY 1");
		
		boolean existeCorreo	= exaCorreoDao.existeAlumno(matricula);
		boolean existeDireccion	= exaDireccionDao.existeAlumno(matricula);
		boolean existeFamilia	= exaFamiliaDao.existeAlumno(matricula);
		boolean existeEgreso	= exaEgresoDao.existeAlumno(matricula);
		boolean existeEmpleo	= exaEmpleoDao.existeAlumno(matricula);
		boolean existeTelefono	= exaTelefonoDao.existeAlumno(matricula);
		boolean existeIglesia	= exaIglesiaDao.existeAlumno(matricula);
		boolean existeRed		= exaRedDao.existeAlumno(matricula);		
		
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("alumnoNombre", alumnoNombre);
		modelo.addAttribute("existeCorreo", existeCorreo);
		modelo.addAttribute("existeDireccion", existeDireccion);
		modelo.addAttribute("existeFamilia", existeFamilia);
		modelo.addAttribute("existeEgreso", existeEgreso);
		modelo.addAttribute("existeEmpleo", existeEmpleo);
		modelo.addAttribute("existeTelefono", existeTelefono);
		modelo.addAttribute("existeIglesia", existeIglesia);		
		modelo.addAttribute("existeRed", existeRed);	
		
		modelo.addAttribute("direccion", direccion);
		modelo.addAttribute("lisPaises", lisPaises);
		modelo.addAttribute("lisEstados", lisEstados);
		
		
		return "exalumnos/exalumno/direccion";
	}	
	
	@RequestMapping("/exalumnos/exalumno/egreso")
	public String exalumnosExalumnoEgreso(HttpServletRequest request, Model modelo){		
		
		String facultadId		= request.getParameter("FacultadId")==null?"0":request.getParameter("FacultadId");
		String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		
		String matricula 		= "0";
		String alumnoNombre 	= "-";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			matricula 			= (String)sesion.getAttribute("codigoAlumno");
			sesion.setAttribute("exaOpcion", "egreso");
		}	
		
		AlumPersonal alumPersonal = new AlumPersonal();		
		alumPersonal 	= alumPersonalDao.mapeaRegId( matricula);		
		alumnoNombre 	= alumPersonal.getNombre()+" "+alumPersonal.getApellidoPaterno()+" "+alumPersonal.getApellidoMaterno();		
		
		List<CatFacultad> lisFacultades			= catFacultadDao.getListAll("ORDER BY 3");		
		List<aca.Mapa> lisCarreras				=  catCarreraDao.lisPlanesPorFacultad(facultadId, "ORDER BY 2");		  
		List<aca.Mapa> lisCarrerasAlumno		= catCarreraDao.lisPlanesAlumno(matricula);		
		List<ExaEgreso> lisEgreso 				= exaEgresoDao.getEgresos( matricula, "ORDER BY 1");
		List<ExaEstudio> lisEstudio             = exaEstudioDao.lisEstudios(matricula,"ORDER BY 1");

		HashMap<String,MapaPlan> mapaPlanes 	= mapaPlanDao.mapPlanes("'A','V','I'");
		HashMap<String,CatCarrera> mapaCarreras	= catCarreraDao.mapaCarreras();
		
		ExaEgreso exaEgreso = new ExaEgreso();
		if(accion.equals("1")){
			//Grabar nuevo 
			String tiempo = aca.util.Fecha.getHoy()+" "+ aca.util.Fecha.getHoraDelDia()+":"+aca.util.Fecha.getMinutos()+":"+aca.util.Fecha.getSegundos();
			int year =0;
			year = Integer.parseInt(request.getParameter("Year"));
			exaEgreso.setEgresoId(exaEgresoDao.maximoReg());
			exaEgreso.setMatricula(matricula);
			exaEgreso.setPlanId(request.getParameter("PlanId"));
			MapaPlan mapaPlan = mapaPlanDao.mapeaRegId(request.getParameter("PlanId"));
			exaEgreso.setCarreraId(mapaPlan.getCarreraId());
			exaEgreso.setYear(request.getParameter("Year"));		
			exaEgreso.setFechaAct(tiempo);
			exaEgreso.setEliminado("0");
			exaEgreso.setIdEgresadoAno("-");		
			if (exaEgresoDao.insertReg( exaEgreso)){			
			}
			lisEgreso 				= exaEgresoDao.getEgresos(matricula, "ORDER BY 1");
		}else if(accion.equals("2")){
			String tiempo = aca.util.Fecha.getHoy()+" "+ aca.util.Fecha.getHoraDelDia()+":"+aca.util.Fecha.getMinutos()+":"+aca.util.Fecha.getSegundos();
			int year = 0;
			year = Integer.parseInt(request.getParameter("YearOtra"));
			exaEgreso.setEgresoId(exaEgresoDao.maximoReg());
			exaEgreso.setMatricula(matricula);
			exaEgreso.setPlanId(request.getParameter("PlanId"));
			MapaPlan mapaPlan = mapaPlanDao.mapeaRegId( request.getParameter("PlanId"));
			exaEgreso.setCarreraId(mapaPlan.getCarreraId());
			exaEgreso.setYear(request.getParameter("YearOtra"));		
			exaEgreso.setFechaAct(tiempo);
			exaEgreso.setEliminado("0");
			exaEgreso.setIdEgresadoAno("-");	
			if (exaEgresoDao.insertReg(exaEgreso)){			
			}
			lisEgreso 				= exaEgresoDao.getEgresos(matricula, "ORDER BY 1");
			
		}else if(accion.equals("3")){
			//Borrar			
			if (exaEgresoDao.eliminar(request.getParameter("EgresoId"))){			
			}		
			// Actualizando la lista de carreras
			lisEgreso 				= exaEgresoDao.getEgresos(matricula, "ORDER BY 1");
		}
		
		boolean existeCorreo	= exaCorreoDao.existeAlumno(matricula);
		boolean existeDireccion	= exaDireccionDao.existeAlumno(matricula);
		boolean existeFamilia	= exaFamiliaDao.existeAlumno(matricula);
		boolean existeEgreso	= exaEgresoDao.existeAlumno(matricula);
		boolean existeEmpleo	= exaEmpleoDao.existeAlumno(matricula);
		boolean existeTelefono	= exaTelefonoDao.existeAlumno(matricula);
		boolean existeIglesia	= exaIglesiaDao.existeAlumno(matricula);
		boolean existeRed		= exaRedDao.existeAlumno(matricula);		
		
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("alumnoNombre", alumnoNombre);
		modelo.addAttribute("existeCorreo", existeCorreo);
		modelo.addAttribute("existeDireccion", existeDireccion);
		modelo.addAttribute("existeFamilia", existeFamilia);
		modelo.addAttribute("existeEgreso", existeEgreso);
		modelo.addAttribute("existeEmpleo", existeEmpleo);
		modelo.addAttribute("existeTelefono", existeTelefono);
		modelo.addAttribute("existeIglesia", existeIglesia);		
		modelo.addAttribute("existeRed", existeRed);
		
		modelo.addAttribute("lisFacultades", lisFacultades);
		modelo.addAttribute("lisCarreras", lisCarreras);
		modelo.addAttribute("lisCarrerasAlumno", lisCarrerasAlumno);
		modelo.addAttribute("lisEgreso", lisEgreso);	
		modelo.addAttribute("mapaPlanes", mapaPlanes);		
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("lisEstudio", lisEstudio);
		
		return "exalumnos/exalumno/egreso";
	}
	
	@RequestMapping("/exalumnos/exalumno/empleo")
	public String exalumnosExalumnoEmpleo(HttpServletRequest request, Model modelo){
		String accion = request.getParameter("Accion")==null?"":request.getParameter("Accion");
		
		String matricula 		= "0";
		String alumnoNombre 	= "-";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			matricula 			= (String)sesion.getAttribute("codigoAlumno");
			sesion.setAttribute("exaOpcion", "empleo");
		}	
		
		AlumPersonal alumPersonal = new AlumPersonal();		
		alumPersonal 	= alumPersonalDao.mapeaRegId( matricula);		
		alumnoNombre 	= alumPersonal.getNombre()+" "+alumPersonal.getApellidoPaterno()+" "+alumPersonal.getApellidoMaterno();
		
		ExaEmpleo empleo = new ExaEmpleo();
		
		if(accion.equals("1")){//Grabar nuevo 
			String tiempo = aca.util.Fecha.getHoy()+" "+ aca.util.Fecha.getHoraDelDia()+":"+aca.util.Fecha.getMinutos()+":"+aca.util.Fecha.getSegundos();
			
			empleo.setEmpleoId(exaEmpleoDao.maximoReg());
			empleo.setMatricula(matricula);
			empleo.setEmpresa(request.getParameter("empresa"));
			empleo.setPeriodo(request.getParameter("periodo"));
			empleo.setFechaAct(tiempo);
			empleo.setEliminado("0");
			empleo.setIdEmpleo("-");
		
			exaEmpleoDao.insertReg(empleo);
		}else if(accion.equals("2")){//Borrar 
			exaEmpleoDao.eliminar(request.getParameter("empleoId"));
		}

		List<ExaEmpleo> empleos = exaEmpleoDao.getEmpleos(matricula, "ORDER BY 1");
		
		boolean existeCorreo	= exaCorreoDao.existeAlumno(matricula);
		boolean existeDireccion	= exaDireccionDao.existeAlumno(matricula);
		boolean existeFamilia	= exaFamiliaDao.existeAlumno(matricula);
		boolean existeEgreso	= exaEgresoDao.existeAlumno(matricula);
		boolean existeEmpleo	= exaEmpleoDao.existeAlumno(matricula);
		boolean existeTelefono	= exaTelefonoDao.existeAlumno(matricula);
		boolean existeIglesia	= exaIglesiaDao.existeAlumno(matricula);
		boolean existeRed		= exaRedDao.existeAlumno(matricula);		
		
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("alumnoNombre", alumnoNombre);
		modelo.addAttribute("existeCorreo", existeCorreo);
		modelo.addAttribute("existeDireccion", existeDireccion);
		modelo.addAttribute("existeFamilia", existeFamilia);
		modelo.addAttribute("existeEgreso", existeEgreso);
		modelo.addAttribute("existeEmpleo", existeEmpleo);
		modelo.addAttribute("existeTelefono", existeTelefono);
		modelo.addAttribute("existeIglesia", existeIglesia);		
		modelo.addAttribute("existeRed", existeRed);	
		
		modelo.addAttribute("empleos", empleos);
		
		return "exalumnos/exalumno/empleo";
	}
	
	@RequestMapping("/exalumnos/exalumno/estudio")
	public String exalumnosExalumnoEstudio(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContExalumnosExalumno|exalumnosExalumnoEstudio");
		return "exalumnos/exalumno/estudio";
	}
	
	@RequestMapping("/exalumnos/exalumno/eventos")
	public String exalumnosExalumnoEventos(HttpServletRequest request, Model modelo){		
		
		String matricula 		= "0";
		String alumnoNombre 	= "-";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			matricula 			= (String)sesion.getAttribute("codigoAlumno");
		}	
		
		List<ExaEvento> lisEventos = exaEventoDao.lisPorAlumno(matricula," ORDER BY FECHAEVENTO");
		
		modelo.addAttribute("lisEventos", lisEventos);
		
		return "exalumnos/exalumno/eventos";
	}
	
	@RequestMapping("/exalumnos/exalumno/familia")
	public String exalumnosExalumnoFamilia(HttpServletRequest request, Model modelo){
		String accion = request.getParameter("Accion")==null?"":request.getParameter("Accion");
		
		String matricula 		= "0";
		String alumnoNombre 	= "-";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			matricula 			= (String)sesion.getAttribute("codigoAlumno");
			sesion.setAttribute("exaOpcion", "familia");
		}	
		
		AlumPersonal alumPersonal = new AlumPersonal();		
		alumPersonal 	= alumPersonalDao.mapeaRegId( matricula);		
		alumnoNombre 	= alumPersonal.getNombre()+" "+alumPersonal.getApellidoPaterno()+" "+alumPersonal.getApellidoMaterno();
		
		ExaFamilia familia = new ExaFamilia();
		if(accion.equals("1")){//Grabar nuevo familiar
			String tiempo = aca.util.Fecha.getHoy()+" "+ aca.util.Fecha.getHoraDelDia()+":"+aca.util.Fecha.getMinutos()+":"+aca.util.Fecha.getSegundos();
			
			familia.setFamiliaId(exaFamiliaDao.maximoReg());
			familia.setMatricula(matricula);
			familia.setNombre(request.getParameter("nombre"));
			familia.setRelacion(request.getParameter("relacion"));
			familia.setFechaNac(request.getParameter("FechaNac"));
			familia.setFechaAct(tiempo);
			familia.setEliminado("0");
			familia.setCorreo(request.getParameter("correo"));
			familia.setFechaAniv(request.getParameter("FechaAni"));
			familia.setIdFamilia("-");
			
			exaFamiliaDao.insertReg( familia);
		}else if(accion.equals("2")){//Borrar familiar			
			exaFamiliaDao.deleteReg(request.getParameter("familiaId"));
		}
		
		boolean existeCorreo	= exaCorreoDao.existeAlumno(matricula);
		boolean existeDireccion	= exaDireccionDao.existeAlumno(matricula);
		boolean existeFamilia	= exaFamiliaDao.existeAlumno(matricula);
		boolean existeEgreso	= exaEgresoDao.existeAlumno(matricula);
		boolean existeEmpleo	= exaEmpleoDao.existeAlumno(matricula);
		boolean existeTelefono	= exaTelefonoDao.existeAlumno(matricula);
		boolean existeIglesia	= exaIglesiaDao.existeAlumno(matricula);
		boolean existeRed		= exaRedDao.existeAlumno(matricula);
		
		List<ExaFamilia> lisFamilia = exaFamiliaDao.getFamilia( matricula, " ORDER BY RELACION,FECHANACIMIENTO");
		
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("alumnoNombre", alumnoNombre);
		modelo.addAttribute("existeCorreo", existeCorreo);
		modelo.addAttribute("existeDireccion", existeDireccion);
		modelo.addAttribute("existeFamilia", existeFamilia);
		modelo.addAttribute("existeEgreso", existeEgreso);
		modelo.addAttribute("existeEmpleo", existeEmpleo);
		modelo.addAttribute("existeTelefono", existeTelefono);
		modelo.addAttribute("existeIglesia", existeIglesia);		
		modelo.addAttribute("existeRed", existeRed);
		
		modelo.addAttribute("lisFamilia", lisFamilia);
		
		return "exalumnos/exalumno/familia";
	}
	
	@RequestMapping("/exalumnos/exalumno/iglesia")
	public String exalumnosExalumnoIglesia(HttpServletRequest request, Model modelo){
		String accion = request.getParameter("Accion")==null?"":request.getParameter("Accion");
		
		String matricula 		= "0";
		String alumnoNombre 	= "-";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			matricula 			= (String)sesion.getAttribute("codigoAlumno");
			sesion.setAttribute("exaOpcion", "iglesia");
		}	
		
		AlumPersonal alumPersonal = new AlumPersonal();		
		alumPersonal 	= alumPersonalDao.mapeaRegId( matricula);		
		alumnoNombre 	= alumPersonal.getNombre()+" "+alumPersonal.getApellidoPaterno()+" "+alumPersonal.getApellidoMaterno();
		
		ExaIglesia iglesia = new ExaIglesia();		
		String iglesiaId = "1";
		iglesiaId = exaIglesiaDao.maximoReg();		
		
		if(accion.equals("1")){//Grabar nuevo 
			String tiempo = aca.util.Fecha.getHoy()+" "+ aca.util.Fecha.getHoraDelDia()+":"+aca.util.Fecha.getMinutos()+":"+aca.util.Fecha.getSegundos();
			
			iglesia.setIglesiaId(iglesiaId);
			iglesia.setMatricula(matricula);
			iglesia.setIglesia(request.getParameter("iglesia"));
			iglesia.setFuncion(request.getParameter("funcion"));
			iglesia.setFechaAct(tiempo);
			iglesia.setEliminado("0");
			iglesia.setIdEgresadoIglesia("-");
			
			exaIglesiaDao.insertReg(iglesia);
		}else if(accion.equals("2")){//Borrar			
			exaIglesiaDao.eliminar(Integer.parseInt(request.getParameter("iglesiaId")));			
		}
		
		iglesia = exaIglesiaDao.mapeaRegIdIglesia(exaIglesiaDao.maximoReg(matricula));
		
		boolean existeCorreo	= exaCorreoDao.existeAlumno(matricula);
		boolean existeDireccion	= exaDireccionDao.existeAlumno(matricula);
		boolean existeFamilia	= exaFamiliaDao.existeAlumno(matricula);
		boolean existeEgreso	= exaEgresoDao.existeAlumno(matricula);
		boolean existeEmpleo	= exaEmpleoDao.existeAlumno(matricula);
		boolean existeTelefono	= exaTelefonoDao.existeAlumno(matricula);
		boolean existeIglesia	= exaIglesiaDao.existeAlumno(matricula);
		boolean existeRed		= exaRedDao.existeAlumno(matricula);		
		
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("alumnoNombre", alumnoNombre);
		modelo.addAttribute("existeCorreo", existeCorreo);
		modelo.addAttribute("existeDireccion", existeDireccion);
		modelo.addAttribute("existeFamilia", existeFamilia);
		modelo.addAttribute("existeEgreso", existeEgreso);
		modelo.addAttribute("existeEmpleo", existeEmpleo);
		modelo.addAttribute("existeTelefono", existeTelefono);
		modelo.addAttribute("existeIglesia", existeIglesia);		
		modelo.addAttribute("existeRed", existeRed);	
		
		modelo.addAttribute("iglesia", iglesia);
	
		return "exalumnos/exalumno/iglesia";
	}
	
	@RequestMapping("/exalumnos/exalumno/redSocial")
	public String exalumnosExalumnoRedSocial(HttpServletRequest request, Model modelo){
		String accion = request.getParameter("Accion")==null?"":request.getParameter("Accion");
		
		String matricula 		= "0";
		String alumnoNombre 	= "-";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			matricula 			= (String)sesion.getAttribute("codigoAlumno");
			sesion.setAttribute("exaOpcion", "redSocial");
		}	
		
		AlumPersonal alumPersonal = new AlumPersonal();		
		alumPersonal 	= alumPersonalDao.mapeaRegId( matricula);		
		alumnoNombre 	= alumPersonal.getNombre()+" "+alumPersonal.getApellidoPaterno()+" "+alumPersonal.getApellidoMaterno();
		
		ExaRed red = new ExaRed();
		
		if(accion.equals("1")){//Grabar nuevo 
			String tiempo = aca.util.Fecha.getHoy()+" "+ aca.util.Fecha.getHoraDelDia()+":"+aca.util.Fecha.getMinutos()+":"+aca.util.Fecha.getSegundos();
			
			red.setRedSocialId(exaRedDao.maximoReg());
			red.setMatricula(matricula);
			red.setRed(request.getParameter("red"));
			red.setFechaAct(tiempo);
			red.setEliminado("0");
			red.setIdRedSocial("-");
			
			exaRedDao.insertReg(red);
		}else if(accion.equals("2")){//Borrar 
			red.setRedSocialId(request.getParameter("redId"));
			exaRedDao.eliminar(request.getParameter("redId")); 
		}
		
		List<ExaRed> redes = exaRedDao.getRed(matricula, "ORDER BY 1");
		
		boolean existeCorreo	= exaCorreoDao.existeAlumno(matricula);
		boolean existeDireccion	= exaDireccionDao.existeAlumno(matricula);
		boolean existeFamilia	= exaFamiliaDao.existeAlumno(matricula);
		boolean existeEgreso	= exaEgresoDao.existeAlumno(matricula);
		boolean existeEmpleo	= exaEmpleoDao.existeAlumno(matricula);
		boolean existeTelefono	= exaTelefonoDao.existeAlumno(matricula);
		boolean existeIglesia	= exaIglesiaDao.existeAlumno(matricula);
		boolean existeRed		= exaRedDao.existeAlumno(matricula);		
		
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("alumnoNombre", alumnoNombre);
		modelo.addAttribute("existeCorreo", existeCorreo);
		modelo.addAttribute("existeDireccion", existeDireccion);
		modelo.addAttribute("existeFamilia", existeFamilia);
		modelo.addAttribute("existeEgreso", existeEgreso);
		modelo.addAttribute("existeEmpleo", existeEmpleo);
		modelo.addAttribute("existeTelefono", existeTelefono);
		modelo.addAttribute("existeIglesia", existeIglesia);		
		modelo.addAttribute("existeRed", existeRed);	
		
		modelo.addAttribute("redes", redes);
		
		return "exalumnos/exalumno/redSocial";
	}
	
	@RequestMapping("/exalumnos/exalumno/reporte")
	public String exalumnosExalumnoReporte(HttpServletRequest request, Model modelo){	
		
		String matricula 		= "0";
		String alumnoNombre 	= "-";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			matricula 			= (String)sesion.getAttribute("codigoAlumno");
			alumnoNombre 		= alumPersonalDao.getNombreAlumno(matricula, "NOMBRE");
		}
		AlumPersonal alumPersonal 	= new AlumPersonal();
		alumPersonal 				= alumPersonalDao.mapeaRegId( matricula);
		ExaDireccion exaDireccion 	= exaDireccionDao.mapeaRegIdEstudio(exaDireccionDao.maximoReg(matricula));
		String paisNombre			= catPaisDao.getNacionalidad( alumPersonal.getNacionalidad());
		
		List<ExaPais> lisPaises 	= exaPaisDao.getListAll("ORDER BY 1");
		String paisId 				= lisPaises.get(0).getPaisId();
		if(!exaDireccion.getPaisId().equals("")){
			paisId = exaDireccion.getPaisId();
		}else {
			exaDireccion.setPaisId(paisId);
		}	
		ExaCorreo exaCorreo 		= exaCorreoDao.mapeaRegIdCorreo(exaCorreoDao.maximoReg(matricula));
		ExaIglesia exaIglesia 		= exaIglesiaDao.mapeaRegIdIglesia(exaIglesiaDao.maximoReg(matricula));
		
		List<ExaFamilia> lisFamilias 	= exaFamiliaDao.getFamilia( matricula, "ORDER BY RELACION, FECHANACIMIENTO");
		List<ExaEstudio> lisEstudios 	= exaEstudioDao.lisEstudios( matricula, "ORDER BY 1");
		List<ExaEmpleo> lisEmpleos 		= exaEmpleoDao.getEmpleos(matricula, "ORDER BY 1");		
		List<ExaRed> lisRedes 			= exaRedDao.getRed(matricula, " ORDER BY 1");
		List<ExaTelefono> lisTelefonos 	= exaTelefonoDao.getTelefono(matricula, "ORDER BY 1");
		
		
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("exaDireccion", exaDireccion);
		modelo.addAttribute("exaCorreo", exaCorreo);
		modelo.addAttribute("exaIglesia", exaIglesia);	
		modelo.addAttribute("alumnoNombre", alumnoNombre);		
		modelo.addAttribute("paisNombre", paisNombre);
		
		modelo.addAttribute("lisFamilias", lisFamilias);
		modelo.addAttribute("lisEstudios", lisEstudios);
		modelo.addAttribute("lisEmpleos", lisEmpleos);
		modelo.addAttribute("lisRedes", lisRedes);
		modelo.addAttribute("lisTelefonos", lisTelefonos);
		
		return "exalumnos/exalumno/reporte";
	}
	
	@RequestMapping("/exalumnos/exalumno/telefono")
	public String exalumnosExalumnoTelefono(HttpServletRequest request, Model modelo){
		String accion = request.getParameter("Accion")==null?"":request.getParameter("Accion");
		
		String matricula 		= "0";
		String alumnoNombre 	= "-";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			matricula 			= (String)sesion.getAttribute("codigoAlumno");
			sesion.setAttribute("exaOpcion", "telefono");
		}	
		
		AlumPersonal alumPersonal = new AlumPersonal();
		alumPersonal 	= alumPersonalDao.mapeaRegId( matricula);
		alumnoNombre 	= alumPersonal.getNombre()+" "+alumPersonal.getApellidoPaterno()+" "+alumPersonal.getApellidoMaterno();
		
		ExaTelefono telefono = new ExaTelefono();
		
		if(accion.equals("1")){//Grabar nuevo 
			String tiempo = aca.util.Fecha.getHoy()+" "+ aca.util.Fecha.getHoraDelDia()+":"+aca.util.Fecha.getMinutos()+":"+aca.util.Fecha.getSegundos();
			
			telefono.setTelefonoId(exaTelefonoDao.maximoReg());
			telefono.setMatricula(matricula);
			telefono.setTelefono(request.getParameter("telefono"));
			telefono.setTipo(request.getParameter("tipo"));
			telefono.setFechaAct(tiempo);
			telefono.setEliminado("0");
			telefono.setIdTelefono("-");
			
			exaTelefonoDao.insertReg(telefono);
		}else if(accion.equals("2")){//Borrar 
			telefono.setTelefonoId(request.getParameter("telefonoId"));
			exaTelefonoDao.eliminar(request.getParameter("telefonoId")); 
		}

		List<ExaTelefono> telefonos = exaTelefonoDao.getTelefono(matricula, "ORDER BY 1");
		
		boolean existeCorreo	= exaCorreoDao.existeAlumno(matricula);
		boolean existeDireccion	= exaDireccionDao.existeAlumno(matricula);
		boolean existeFamilia	= exaFamiliaDao.existeAlumno(matricula);
		boolean existeEgreso	= exaEgresoDao.existeAlumno(matricula);
		boolean existeEmpleo	= exaEmpleoDao.existeAlumno(matricula);
		boolean existeTelefono	= exaTelefonoDao.existeAlumno(matricula);
		boolean existeIglesia	= exaIglesiaDao.existeAlumno(matricula);
		boolean existeRed		= exaRedDao.existeAlumno(matricula);		
		
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("alumnoNombre", alumnoNombre);
		modelo.addAttribute("existeCorreo", existeCorreo);
		modelo.addAttribute("existeDireccion", existeDireccion);
		modelo.addAttribute("existeFamilia", existeFamilia);
		modelo.addAttribute("existeEgreso", existeEgreso);
		modelo.addAttribute("existeEmpleo", existeEmpleo);
		modelo.addAttribute("existeTelefono", existeTelefono);
		modelo.addAttribute("existeIglesia", existeIglesia);		
		modelo.addAttribute("existeRed", existeRed);	
		
		modelo.addAttribute("telefonos", telefonos);
		
		return "exalumnos/exalumno/telefono";
	}
}