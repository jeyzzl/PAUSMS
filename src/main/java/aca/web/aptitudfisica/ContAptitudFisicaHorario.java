package aca.web.aptitudfisica;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumPersonalDao;
import aca.apFisica.spring.ApFisicaAlumno;
import aca.apFisica.spring.ApFisicaAlumnoDao;
import aca.apFisica.spring.ApFisicaGrupo;
import aca.apFisica.spring.ApFisicaGrupoDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.vista.spring.AlumnoCurso;
import aca.vista.spring.AlumnoCursoDao;
import aca.vista.spring.AlumnoDao;

@Controller
public class ContAptitudFisicaHorario {

	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	ApFisicaGrupoDao apFisicaGrupoDao;
	
	@Autowired
	ApFisicaAlumnoDao apFisicaAlumnoDao;
	
	@Autowired
	AlumnoDao alumnoDao;
	
	@Autowired
	AlumnoCursoDao alumnoCursoDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	CargaDao cargadao;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;	
	
	
	@RequestMapping("/aptitud_fisica/horario/agregar") //EDITAR
	public String aptitudFisicaHorarioAgregar(HttpServletRequest request, Model modelo){
		
		String periodoId = "-";
		String grupoId    	= request.getParameter("GrupoId");
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
		if (sesion!=null) {
			
		 periodoId 	= (String)sesion.getAttribute("periodo");
		}
		
		List<Carga> lisCarga =  cargadao.getListAll("WHERE PERIODO IN ('"+periodoId+"') AND ESTADO = '1' ORDER BY CARGA_ID");
		
		ApFisicaGrupo apFiGru = new ApFisicaGrupo();
		if(apFisicaGrupoDao.existeReg(grupoId)) {
			apFiGru = apFisicaGrupoDao.mapeaRegId(grupoId);
		}else {
			if (lisCarga.size()>=1) {
				apFiGru.setCargas(lisCarga.get(0).getCargaId());
			}
		}
		
		modelo.addAttribute("lisCarga", lisCarga);
		modelo.addAttribute("apFiGru", apFiGru);
		
		return "aptitud_fisica/horario/agregar";
	}
	
	@RequestMapping("/aptitud_fisica/horario/guardar")
	public String aptitudFisicaHorarioGuardar(HttpServletRequest request, Model modelo){
		
		String grupoId    		= request.getParameter("GrupoId")==null?"0":request.getParameter("GrupoId");
		String nombreGrupo 		= request.getParameter("NombreGrupo")==null?"0":request.getParameter("NombreGrupo");
		String lugar 			= request.getParameter("Lugar")==null?"0":request.getParameter("Lugar");
		String instructor 		= request.getParameter("Instructor")==null?"0":request.getParameter("Instructor");
		String cupo 			= request.getParameter("Cupo")==null?"0":request.getParameter("Cupo");
		String dia 				= request.getParameter("Dia")==null?"0":request.getParameter("Dia");
		String cargas 			= request.getParameter("Cargas")==null?"0":request.getParameter("Cargas");
		String fInicio 			= request.getParameter("FInicio")==null?"0":request.getParameter("FInicio");
		String fCierre 			= request.getParameter("FCierre")==null?"0":request.getParameter("FCierre");
		String fFinal 			= request.getParameter("FFinal")==null?"0":request.getParameter("FFinal");
		String descripcion 		= request.getParameter("Descripcion")==null?"0":request.getParameter("Descripcion");
		String clave 			= request.getParameter("Clave")==null?"0":request.getParameter("Clave");
		String hora 			= request.getParameter("Hora")==null?"0":request.getParameter("Hora");
		String acceso 			= request.getParameter("Acceso")==null?"0":request.getParameter("Acceso");
		String liga 			= request.getParameter("Liga")==null?"0":request.getParameter("Liga");
		String sexo 			= request.getParameter("Sexo")==null?"T":request.getParameter("Sexo");
		String mensaje 			= "-";
		
		ApFisicaGrupo apFiGru = new ApFisicaGrupo();

		if(apFisicaGrupoDao.existeReg(grupoId)) {
			//modificar
			apFiGru = apFisicaGrupoDao.mapeaRegId(grupoId);
			apFiGru.setNombreGrupo(nombreGrupo);
			apFiGru.setLugar(lugar);
			apFiGru.setInstructor(instructor);
			apFiGru.setCupo(cupo);
			apFiGru.setDia1(dia);
			apFiGru.setCargas(cargas);
			apFiGru.setfInicio(fInicio);
			apFiGru.setfCierre(fCierre);
			apFiGru.setfFinal(fFinal);
			apFiGru.setDescripcion(descripcion);
			apFiGru.setClave(clave);
			apFiGru.setHora(hora);
			apFiGru.setAcceso(acceso);
			apFiGru.setLiga(liga);
			apFiGru.setSexo(sexo);

			if (apFisicaGrupoDao.updateReg(apFiGru)){
				mensaje = "Actualizado correctamente";
 			}else{
 				mensaje = "Ocurrió un error al intentar actualizar";	
 			}	
		}else{
			grupoId = apFisicaGrupoDao.maximoReg();
			apFiGru.setGrupoId(grupoId);
			apFiGru.setNombreGrupo(nombreGrupo);
			apFiGru.setLugar(lugar);
			apFiGru.setInstructor(instructor);
			apFiGru.setCupo(cupo);
			apFiGru.setDia1(dia);
			apFiGru.setCargas(cargas);
			apFiGru.setfInicio(fInicio);
			apFiGru.setfCierre(fCierre);
			apFiGru.setfFinal(fFinal);
			apFiGru.setDescripcion(descripcion);
			apFiGru.setClave(clave);
			apFiGru.setHora(hora);
			apFiGru.setAcceso(acceso);
			apFiGru.setLiga(liga);
			apFiGru.setSexo(sexo);
			
			if (apFisicaGrupoDao.insertReg(apFiGru)){
 				mensaje = "Guardado correctamente";				
 			}else{
 				mensaje = "Ocurrió un error al intentar guardar";
 			}
		}
		
		return "redirect:/aptitud_fisica/horario/agregar?GrupoId="+grupoId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/aptitud_fisica/horario/borrar")
	public String aptitudFisicaHorarioBorrar(HttpServletRequest request, Model modelo){
		
		String grupoId    		= request.getParameter("GrupoId")==null?"0":request.getParameter("GrupoId");
		String mensaje 			= "-";
		
		//aptitudGrupo.setGrupoID(grupoId);
		
		if(apFisicaGrupoDao.existeReg(grupoId)){
			if(apFisicaGrupoDao.deleteReg(grupoId)){
				mensaje = "Se eliminó";
				
			}else{
				mensaje = "No se pudo eliminar";
			}
		}else{
			mensaje = "No existe";
		}
		
		return "redirect:/aptitud_fisica/horario/grupo?Mensaje="+mensaje;
	}
	
	@RequestMapping("/aptitud_fisica/horario/grupo")
	public String aptitudFisicaHorarioGrupo(HttpServletRequest request, Model modelo){
		
		String fecha    	= request.getParameter("Fecha")==null?aca.util.Fecha.getHoy():request.getParameter("Fecha");
		   
		List<ApFisicaGrupo> lisGrupos 				= apFisicaGrupoDao.lisTieneGrupo( fecha , " ORDER BY CLAVE, NOMBRE_GRUPO");
		List<String> lisCargas 						= apFisicaGrupoDao.lisCargasEnFecha(fecha);
		List<String> lisMaterias 					= apFisicaGrupoDao.lisMateriasEnFecha(fecha);
		HashMap<String, String> mapaCupoHombres 	= apFisicaGrupoDao.mapaCupoHombres(fecha);
		HashMap<String, String> mapaCupoMujeres 	= apFisicaGrupoDao.mapaCupoMujeres(fecha);
		HashMap<String, String> mapaRegistrados 	= apFisicaAlumnoDao.mapaRegistrados();
		
		String cargas 	= "'0'";
		for (String objeto : lisCargas) {
			if (cargas.equals("'0'")) cargas = "'"+objeto+"'"; else cargas = cargas+",'"+objeto+"'";
		}
		String materias = "'0'";
		for (String objeto : lisMaterias) {
			if (materias.equals("'0'")) materias = "'"+objeto+"'"; else materias = materias+",'"+objeto+"'";
		}
		
		HashMap<String, String> mapaHombresEnCursos	= alumnoCursoDao.mapaAlumnosPorMateria(cargas, materias, "M");
		HashMap<String, String> mapaMujeresEnCursos	= alumnoCursoDao.mapaAlumnosPorMateria(cargas, materias, "F");
		
		modelo.addAttribute("cargas", cargas);
		modelo.addAttribute("materias", materias);
		modelo.addAttribute("lisGrupos", lisGrupos);		
		modelo.addAttribute("lisMaterias", lisMaterias);
		modelo.addAttribute("mapaCupoHombres", mapaCupoHombres);
		modelo.addAttribute("mapaCupoMujeres", mapaCupoMujeres);		
		modelo.addAttribute("mapaHombresEnCursos", mapaHombresEnCursos);
		modelo.addAttribute("mapaMujeresEnCursos", mapaMujeresEnCursos);
		modelo.addAttribute("mapaRegistrados", mapaRegistrados);
		
		return "aptitud_fisica/horario/grupo";
	}
	
	@RequestMapping("/aptitud_fisica/horario/lista")
	public String aptitudFisicaHorarioLista(HttpServletRequest request, Model modelo){	
		
		String grupoId      = request.getParameter("GrupoId")==null?"0":request.getParameter("GrupoId");
		String cargaId      = request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String clave	    = request.getParameter("Clave")==null?"0":request.getParameter("Clave");
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
		if (sesion!=null) {		
			if(grupoId.equals("0")){
				if((String)sesion.getAttribute("grupoId")!=null){
					grupoId = (String)sesion.getAttribute("grupoId");
				}else{
					grupoId = "1";
				}		
			}else {
				sesion.setAttribute("grupoId", grupoId);
			}		
		} 
		ApFisicaGrupo grupo 		= new ApFisicaGrupo();
		if (apFisicaGrupoDao.existeReg(grupoId)) {
			grupo = apFisicaGrupoDao.mapeaRegId(grupoId);
		}
		
	   	List<ApFisicaAlumno> lisAlumnos    		= apFisicaAlumnoDao.getListAlum("A", grupoId, " ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");	   	
	   	HashMap<String, String> mapaAlumnos 	= alumPersonalDao.mapaAlumnosEnAptitud();	   	
	   	HashMap<String,CatCarrera> mapaCarreras	= catCarreraDao.getMapAll("");
	   	
	   	modelo.addAttribute("lisAlumnos", lisAlumnos);
	   	modelo.addAttribute("grupo", grupo);
	   	modelo.addAttribute("mapaAlumnos", mapaAlumnos);
	   	modelo.addAttribute("mapaCarreras", mapaCarreras);
	   	modelo.addAttribute("cargaId", cargaId);
	   	modelo.addAttribute("clave", clave);
		   	
		return "aptitud_fisica/horario/lista";
	}
	
	@RequestMapping("/aptitud_fisica/horario/candidatos")
	public String aptitudFisicaHorarioCandidatos(HttpServletRequest request, Model modelo){
		
		String grupoId      = "0";
		String cargaId      = request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String clave	    = request.getParameter("Clave")==null?"0":request.getParameter("Clave");
		int registrados 	= 0;		
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
		if (sesion!=null) {		
			grupoId = (String)sesion.getAttribute("grupoId");
		}	
					
		ApFisicaGrupo grupo = new ApFisicaGrupo();
		if (apFisicaGrupoDao.existeReg(grupoId)) {
			grupo 			= apFisicaGrupoDao.mapeaRegId(grupoId);
			registrados 	= apFisicaAlumnoDao.registrados(grupoId);
		}
		
		List<AlumnoCurso> lisAlum 				 = alumnoCursoDao.lisAptitudSinGrupo(cargaId, clave, "");
		HashMap<String, String> mapaAlumnos  	 = alumPersonalDao.mapaAlumnosEnMateria(clave);
		HashMap<String,String> nombreCarrera 	= catCarreraDao.mapaCarreraApt();
		
		modelo.addAttribute("grupo", grupo);
		modelo.addAttribute("registrados", registrados);
		modelo.addAttribute("lisAlum", lisAlum);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("nombreCarrera", nombreCarrera);
		
		return "aptitud_fisica/horario/candidatos";
	}
	
	@RequestMapping("/aptitud_fisica/horario/grabarAlumno")
	public String aptitudFisicaHorarioGrabarAlumno(HttpServletRequest request, Model modelo){
		
		String grupoId			= "0";
		String cargaId     	 	= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String clave	  	  	= request.getParameter("Clave")==null?"0":request.getParameter("Clave");
		String codigoAlumno 	= request.getParameter("CodigoAlumno")==null?"0":request.getParameter("CodigoAlumno");
		String cursoCargaId 	= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		String mensaje 			= "";
		int registrados 		= 0;
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
		if (sesion!=null) {		
			grupoId = (String)sesion.getAttribute("grupoId");
		}	
		ApFisicaGrupo grupo = new ApFisicaGrupo();
		boolean disponibles = false;
		if (apFisicaGrupoDao.existeReg(grupoId)) {
			grupo 			= apFisicaGrupoDao.mapeaRegId(grupoId);
			registrados 	= apFisicaAlumnoDao.registrados(grupoId);
			if (Integer.parseInt(grupo.getCupo()) > registrados) disponibles = true;
		}
		ApFisicaAlumno alumno = new ApFisicaAlumno();	   	
	    alumno.setCodigoPersonal(codigoAlumno);
	    alumno.setGrupoId(grupoId);
	    alumno.setCursoCargaId(cursoCargaId);
	    alumno.setFecha(aca.util.Fecha.getHoy());
	    alumno.setEstado("A");
	    if (!apFisicaAlumnoDao.existeReg(codigoAlumno, grupoId) && disponibles) {
	    	if (apFisicaAlumnoDao.insertReg(alumno)){
	    		mensaje = "¡Grabado!";
	    	}else {
	    		mensaje = "¡Error al grabar!";
	    	}
	    }else {
	    	mensaje = "¡El alumno ya está registrado o no hay lugares disponibles en este grupo!";
	    }	
		
		return "redirect:/aptitud_fisica/horario/candidatos?CargaId="+cargaId+"&Clave="+clave+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/aptitud_fisica/horario/borrarAlumno")
	public String aptitudFisicaHorarioBorrarAlumno(HttpServletRequest request, Model modelo){
		
		String grupoId    		= request.getParameter("GrupoId")==null?"0":request.getParameter("GrupoId");
		String codigoAlumno 	= request.getParameter("CodigoAlumno")==null?"0":request.getParameter("CodigoAlumno");
		String mensaje 			= "";
		
		if(apFisicaAlumnoDao.existeReg(codigoAlumno, grupoId)){
			if(apFisicaAlumnoDao.deleteAlumno(grupoId, codigoAlumno)){
				mensaje = "Se eliminó";
				
			}else{
				mensaje = "No se pudo eliminar";
			}
		}else{
			mensaje = "No existe";
		}
		
		return "redirect:/aptitud_fisica/horario/lista?GrupoId="+grupoId+"&Mensaje="+mensaje;
	}
	
}
