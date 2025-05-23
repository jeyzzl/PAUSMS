package aca.web.portales;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import aca.alumno.spring.AlumAcademico;
import aca.alumno.spring.AlumAcademicoDao;
import aca.alumno.spring.AlumBloqueo;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlanDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatCiudadDao;
import aca.catalogo.spring.CatEstadoDao;
import aca.catalogo.spring.CatInstitucionDao;
import aca.catalogo.spring.CatPaisDao;
import aca.catalogo.spring.CatPeriodoDao;
import aca.catalogo.spring.CatReligion;
import aca.catalogo.spring.CatReligionDao;
import aca.catalogo.spring.CatTipoAlumnoDao;
import aca.disciplina.spring.CondAlumnoDao;
import aca.emp.spring.EmpleadoDao;
import aca.financiero.spring.FesContratoFinancieroDao;
import aca.financiero.spring.FinSaldo;
import aca.internado.spring.IntAcceso;
import aca.internado.spring.IntAccesoDao;
import aca.internado.spring.IntAlumReporte;
import aca.internado.spring.IntAlumReporteDao;
import aca.internado.spring.IntCuarto;
import aca.internado.spring.IntCuartoDao;
import aca.internado.spring.IntDatosAlumno;
import aca.internado.spring.IntDatosAlumnoDao;
import aca.internado.spring.IntDormitorio;
import aca.internado.spring.IntDormitorioDao;
import aca.internado.spring.IntReporte;
import aca.internado.spring.IntReporteDao;
import aca.mentores.spring.MentAlumno;
import aca.mentores.spring.MentAlumnoDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.Inscritos;
import aca.vista.spring.InscritosDao;
import aca.vista.spring.Maestros;
import aca.vista.spring.MaestrosDao;
import aca.vista.spring.UsuariosDao;
import aca.internado.spring.IntAlumno;
import aca.internado.spring.IntAlumnoDao;

@Controller
public class ContPortalesPreceptor {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	
	
	@Autowired
	AlumPersonalDao alumPersonalDao;
	
	@Autowired
	IntAccesoDao intAccesoDao;
	
	@Autowired
	CatPeriodoDao catPeriodoDao;
	
	@Autowired
	MaestrosDao maestrosDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	CatReligionDao catReligionDao;
	
	@Autowired
	IntCuartoDao intCuartoDao;
	
	@Autowired
	AlumPlanDao alumPlanDao;
	
	@Autowired
	MentAlumnoDao mentAlumnoDao;
	
	@Autowired
	AlumAcademicoDao alumAcademicoDao;
	
	@Autowired
	UsuariosDao usuariosDao;
	
	@Autowired
	IntAlumnoDao intAlumnoDao;
	
	@Autowired
	IntDormitorioDao intDormitorioDao;
	
	@Autowired
	InscritosDao inscritosDao;
	
	@Autowired
	CondAlumnoDao condAlumnoDao;
	
	@Autowired
	FesContratoFinancieroDao fesContratoFinancieroDao;
	
	@Autowired
	IntAlumReporteDao intAlumReporteDao;
	
	@Autowired
	IntReporteDao intReporteDao;
	
	@Autowired
	EmpleadoDao empleadoDao;
	
	@Autowired
	CatPaisDao catPaisDao;
	
	@Autowired
	CatEstadoDao catEstadoDao;
	
	@Autowired
	CatCiudadDao catCiudadDao;
	
	@Autowired
	CatInstitucionDao catInstitucionDao;
	
	@Autowired
	MapaPlanDao mapaPlanDao;
	
	@Autowired
	CatTipoAlumnoDao catTipoAlumnoDao;
	
	@Autowired
	IntDatosAlumnoDao intDatosAlumnoDao;
	
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
			//System.out.println("Abrir conEnoc"+url);
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/portales/preceptor/asignarCuartos")
	public String portalesPreceptorAsignarCuartos(HttpServletRequest request, Model modelo){
		
		String codigoAlumno 	= "0";
		String nombreAlumno 	= "-";
		boolean existeAlumno 	= false;
		HttpSession sesion 		= ((HttpServletRequest) request).getSession();
		if (sesion!=null) {
			codigoAlumno 		= (String)sesion.getAttribute("codigoAlumno");
			if (alumPersonalDao.existeReg(codigoAlumno)){
				existeAlumno = true;
				nombreAlumno 		= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
			}			
		}
		
		String dormitorioId 	= "0";
		boolean esAdmin			= (boolean) sesion.getAttribute("admin");
		boolean esPreceptor		= false;
		boolean esAsistente 	= false;
		
		String accion 			= request.getParameter("accion")==null?"0":request.getParameter("accion");
		String codigoPersonal	= request.getParameter("codigo")==null?"0":request.getParameter("codigo");
		String cP				= request.getParameter("cP");
		String cuartoId			= request.getParameter("cuarto");
		String orden			= request.getParameter("orden");
		String pasillo			= request.getParameter("Pasillo")==null?"A":request.getParameter("Pasillo");
		
		String fechaIni			= (String) sesion.getAttribute("fechaIni");
		String fechaFin			= (String) sesion.getAttribute("fechaFin");
		
		HashMap<String,String> mapAlumnoFecha = inscritosDao.mapInscritoUltimaFecha();

		
		boolean mn				= false;
		String mensaje			= "-";
		
		if (sesion!=null) {
			sesion.setAttribute("menuPreceptor", "3");
		}
		
		if (codigoPersonal.equals("0"))	codigoPersonal = (String) sesion.getAttribute("codigoPersonal");
		
		esPreceptor = intDormitorioDao.existePreceptor(codigoPersonal);
		esAsistente = intAccesoDao.esAsistente(codigoPersonal);

		if(esAdmin || esPreceptor){
			dormitorioId = (String) sesion.getAttribute("dormitorioId");
		}else{
			dormitorioId = intAccesoDao.getDormitorioId(codigoPersonal);
		}			
		
		IntDormitorio dormi = new IntDormitorio();
		
		if(intDormitorioDao.existeReg(dormitorioId)) {
			dormi = intDormitorioDao.mapeaRegId(dormitorioId);
		}

		IntAcceso acceso = new IntAcceso();
		if (esAdmin) {
			sesion.setAttribute("rolInternado", "A");
		}
		else if(esAdmin == false) {
			if(intDormitorioDao.existePreceptor(codigoPersonal) && sesion !=null) {
				
				sesion.setAttribute("rolInternado", "P");
				
			}
		}else {
			acceso		= intAccesoDao.mapeaRegId(codigoPersonal);
		}		
		
		String pasilloMonitor	= acceso.getPasillo();
		
		IntAlumno dormiAlumno = new IntAlumno();
		AlumPersonal personal = new AlumPersonal();
		AlumAcademico academico = new AlumAcademico();

		if (accion.equals("guardar")){
			dormiAlumno.setCodigoPersonal(codigoAlumno);
			dormiAlumno.setCuartoId(cuartoId);
			dormiAlumno.setDormitorioId(dormitorioId);
			dormiAlumno.setOrden(orden);
			dormiAlumno.setEstado("A");
			dormiAlumno.setFechaInicio(aca.util.Fecha.getFechaHoy());
			if (existeAlumno){
				personal = alumPersonalDao.mapeaRegId(codigoAlumno);
				academico = alumAcademicoDao.mapeaRegId(codigoAlumno);
				if (personal.getSexo().equals(dormi.getSexo())){
					if (intAlumnoDao.existeReg(codigoAlumno)==false){					
						if (intAlumnoDao.insertReg(dormiAlumno)){						
							dormiAlumno = intAlumnoDao.mapeaRegId(dormitorioId,cuartoId,codigoAlumno);		
							academico.setDormitorio(dormitorioId);
							alumAcademicoDao.updateReg(academico);
						}else{
							if (!dormiAlumno.getCuartoId().equals(cuartoId) || !dormiAlumno.getDormitorioId().equals(dormitorioId)){							
							}
							mensaje = "The student couldn't be found or there's an error with the ID";
							mn=true;
						}
					}else{
						dormiAlumno = intAlumnoDao.mapeaRegId(codigoAlumno);
						mn=true;
						mensaje = "The student "+alumPersonalDao.getNombreAlumno(codigoAlumno,"NOMBRE")+" is already registered in the Dormitory "+
								intDormitorioDao.getDormitorioAlumno(codigoAlumno)+
								" in room "+intDormitorioDao.getCuartoAlumno(codigoAlumno);
					}
				}else{
					String sexo="";
					String sexoA = "";
					if (dormi.getSexo().equals("M")) sexo = "Male";
					else sexo = "Female";
					if (personal.getSexo().equals("M")) sexoA = "Male";
					else sexoA = "Female";
					mensaje = "Only "+sexo+" students can be added to this dormitory. "+
								"The student "+codigoAlumno+" ("+alumPersonalDao.getNombreAlumno(codigoAlumno,"NOMBRE")+")is "+sexoA+".";
					mn=true;
					}
			} else {
				mensaje = "Student not found";
			}
		}else if (accion.equals("eliminar")){
			dormiAlumno.setDormitorioId(dormitorioId);
			dormiAlumno.setCodigoPersonal(cP);
			intAlumnoDao.deleteReg(dormitorioId,cuartoId,cP);
		}else if (accion.equals("update")){
		}
		
		List<IntCuarto> lisCuartos = intDormitorioDao.getCuartos(dormitorioId, pasillo, " ORDER BY TO_CHAR(CUARTO_ID,'000')");
		// Lista de alumnos en el dormitorio
		List<IntAlumno> lisAlumnos = intDormitorioDao.getAlumnos(dormitorioId, "ORDER BY ORDEN");


		HashMap<String,IntAlumno> mapCamas 				= intAlumnoDao.mapPorCuartoYOrden(dormitorioId);
		// Map de alumnos
		HashMap<String, AlumPersonal> mapAlumnos 		= alumPersonalDao.mapAlumnosInternos(dormitorioId);
		// Map de Datos Academicos
		HashMap<String, AlumAcademico> mapAcademico 	= alumAcademicoDao.mapInternos(dormitorioId);
		// Map de inscritos
		HashMap<String, String> mapInscritos 	= inscritosDao.getMapaInscritos();
		// Mapa de lugares ocupado en cada cuarto
		HashMap<String, String> mapOcupados 	= intAlumnoDao.mapOcupados(dormitorioId);
		// Mapa de ocupados por  pasillo
		HashMap<String,String> mapPasillo  = intAlumnoDao.mapOcupadosPorPasillo(dormitorioId);

		
		modelo.addAttribute("esAdmin", esAdmin);
		modelo.addAttribute("esPreceptor", esPreceptor);
		modelo.addAttribute("existeAlumno", existeAlumno);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("accion", accion);
		modelo.addAttribute("cuartoId", cuartoId);
		modelo.addAttribute("pasillo", pasillo);
		modelo.addAttribute("pasilloMonitor", pasilloMonitor);
		modelo.addAttribute("fechaIni", fechaIni);
		modelo.addAttribute("fechaFin", fechaFin);
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("mn", mn);
		modelo.addAttribute("dormi", dormi);
		modelo.addAttribute("dormitorioId", dormitorioId);
		modelo.addAttribute("lisCuartos", lisCuartos);
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("mapAlumnoFecha", mapAlumnoFecha);
		modelo.addAttribute("mapAlumnos", mapAlumnos);
		modelo.addAttribute("mapAcademico", mapAcademico);
		modelo.addAttribute("mapInscritos", mapInscritos);
		modelo.addAttribute("mapInscritos", mapInscritos);
		modelo.addAttribute("mapPasillo", mapPasillo);
		modelo.addAttribute("mapOcupados", mapOcupados);
		modelo.addAttribute("mapCamas", mapCamas);
		
		return "portales/preceptor/asignarCuartos";
	}

	@RequestMapping("/portales/preceptor/borrarAlumnosDormitorio")
	public String portalesPreceptorBorrarAlumnosDormitorio(HttpServletRequest request, Model modelo){
		HttpSession sesion 		= ((HttpServletRequest) request).getSession();

		boolean esAdmin			= false;
		String dormitorioId 	= "0";
		String codigoPersonal 	= "0";
		String mensaje 			= "";

		if(sesion!=null){
			esAdmin 			= (boolean) sesion.getAttribute("admin");
			codigoPersonal 		= (String)sesion.getAttribute("codigoPersonal");
		}
		
		if (esAdmin) {
			dormitorioId = (String) sesion.getAttribute("dormitorioId");
			if (dormitorioId.equals("0")) {
				dormitorioId = String.valueOf(intAccesoDao.tieneAcceso(codigoPersonal));
			}
		}else {
			dormitorioId = String.valueOf(intAccesoDao.tieneAcceso(codigoPersonal));
		}

		if(intAlumnoDao.borrarAlumnosDormitorio(dormitorioId)){
			mensaje = "<b>All students have been removed from this dormitory</b>";
		}else{
			mensaje = "<b>Error removing students</b>";
		}

		return "redirect:/portales/preceptor/asignarCuartos";
	}

	@RequestMapping("/portales/preceptor/buscar")
	public String portalesPreceptorBuscar(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPortalesPreceptor|portalesPreceptorBuscar");
		return "portales/preceptor/buscar";
	}

	@RequestMapping("/portales/preceptor/cuartos")
	public String portalesPreceptorCuartos(HttpServletRequest request, Model modelo){
		HttpSession sesion 		= ((HttpServletRequest) request).getSession();
		boolean esAdmin			= (boolean) sesion.getAttribute("admin");
		boolean esPreceptor		= false;
		boolean esAsistente 	= false;
		String dormitorioId 	= "0";		
		String codigoPersonal	= request.getParameter("codigo")==null?"0":request.getParameter("codigo");
		String accion 			= request.getParameter("accion")==null?"0":request.getParameter("accion");
		
		String cuartoId			= request.getParameter("cuartoId");
		String pasillo 			= request.getParameter("pasillo");
		String cupo	 			= request.getParameter("cupo");
		String estado	 		= request.getParameter("estado");
		String cE				= request.getParameter("cE");	
		int cupoTotal 			= 0;

		if (sesion!=null) {
			sesion.setAttribute("menuPreceptor", "2");
		}

		if (codigoPersonal.equals("0"))	codigoPersonal = (String) sesion.getAttribute("codigoPreceptor");

		esPreceptor = intDormitorioDao.existePreceptor(codigoPersonal);
		esAsistente = intAccesoDao.esAsistente(codigoPersonal);

		if(esPreceptor || esAdmin){
			dormitorioId = (String) sesion.getAttribute("dormitorioId");

		}else if(esAsistente){
			dormitorioId = intAccesoDao.getDormitorioId(codigoPersonal);
		}
		
		if (accion==null) accion = "";
		
		IntDormitorio dormi = new IntDormitorio();
		
		if(intDormitorioDao.existeReg(dormitorioId)) {
			dormi = intDormitorioDao.mapeaRegId(dormitorioId);
		}
		
		IntCuarto cuarto = new IntCuarto();

		if (accion.equals("grabar")){
			cuarto.setDormitorioId(dormitorioId);
			cuarto.setCuartoId(cuartoId);
			cuarto.setPasillo(pasillo);
			cuarto.setCupo(cupo);		
			cuarto.setEstado(estado);			
			if (intCuartoDao.insertReg(cuarto)){				
			}
		}else if (accion.equals("eliminar")){
			cuarto.setCuartoId(cE);
			cuarto.setDormitorioId(dormitorioId);
			if (intCuartoDao.deleteReg(dormitorioId, cE)){
			}
		}else if (accion.equals("update")){			
			cuarto.setDormitorioId(dormitorioId);
			cuarto.setCuartoId(cE);		
			cuarto.setPasillo(pasillo);
			cuarto.setCupo(cupo);
			cuarto.setEstado(estado);
			
			if (intCuartoDao.updateReg(cuarto)){
			}
		}
		
		String nextCuarto = intDormitorioDao.nextCuarto(dormitorioId);
		List<IntCuarto> vCuartos = intDormitorioDao.getCuartos(dormitorioId,"ORDER BY PASILLO, TO_CHAR(CUARTO_ID,'000')");
		
		HashMap<String,String> mapaTieneAlumnos 	= intAlumnoDao.mapaTieneAlumnos();
		HashMap<String,String> mapaAsignados 		= intAlumnoDao.mapOcupados(dormitorioId);
		HashMap<String,String> mapaInscritos 		= intAlumnoDao.mapInscritosPorCuarto(dormitorioId);
		
		modelo.addAttribute("esAdmin", esAdmin);
		modelo.addAttribute("esPreceptor", esPreceptor);
		modelo.addAttribute("accion", accion);
		modelo.addAttribute("cE", cE);
		modelo.addAttribute("cupoTotal", cupoTotal);
		modelo.addAttribute("cupoTotal", cupoTotal);
		modelo.addAttribute("nextCuarto", nextCuarto);
		modelo.addAttribute("vCuartos", vCuartos);
		modelo.addAttribute("dormi", dormi);
		modelo.addAttribute("mapaTieneAlumnos", mapaTieneAlumnos);
		modelo.addAttribute("mapaAsignados", mapaAsignados);
		modelo.addAttribute("mapaInscritos", mapaInscritos);
		
		return "portales/preceptor/cuartos";
	}

	@RequestMapping("/portales/preceptor/datosPadres")
	public String portalesPreceptorDatosPadres(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPortalesPreceptor|portalesPreceptorDatosPadres");
		
		String codigoAlumno = request.getParameter("CodigoAlumno") == null ? "0" : request.getParameter("CodigoAlumno");
		
		HttpSession sesion 			= ((HttpServletRequest) request).getSession();
		if (sesion != null) {
			sesion.setAttribute("codigoAlumno", codigoAlumno);
		}
		
		return "portales/preceptor/datosPadres";
	}

	@RequestMapping("/portales/preceptor/datosPersonales")
	public String portalesPreceptorDatosPersonales(HttpServletRequest request, Model modelo){		
		
		String codigoAlumno 			= request.getParameter("CodigoAlumno") == null ? "0" : request.getParameter("CodigoAlumno");
		String accion 					= request.getParameter("Accion") == null ? "0" : request.getParameter("Accion");
		String planId					= "";
		AlumPersonal alumPersonal 		= new AlumPersonal();
		AlumAcademico alumAcademico		= new AlumAcademico();
		IntDatosAlumno datos 			= new IntDatosAlumno();
		String paisNombre				= "-";
		String estadoNombre				= "-";		
		String nacionalidad				= "-";
		String institucionNombre		= "-";
		int edad						= 0;
		boolean esInscrito				= false;
		String grado 					= "1";
		String ciclo					= "1";
		String carreraNombre			= "-";
		String tipoAlumno				= "-";
		String religionNombre 			= "-";
		String resultado 				= "-";
		
		HttpSession sesion 			= ((HttpServletRequest) request).getSession();
		if (sesion != null) {
			sesion.setAttribute("codigoAlumno", codigoAlumno);
			planId 				= alumPlanDao.getPlanActual( codigoAlumno);
			alumPersonal		= alumPersonalDao.mapeaRegId(codigoAlumno);
			alumAcademico		= alumAcademicoDao.mapeaRegId(codigoAlumno);
			datos				= intDatosAlumnoDao.mapeaRegId(codigoAlumno);
			paisNombre 			= catPaisDao.getNombrePais(alumPersonal.getPaisId());
			estadoNombre 		= catEstadoDao.getNombreEstado(alumPersonal.getPaisId(), alumPersonal.getEstadoId());
			nacionalidad 		= catPaisDao.getNombrePais(alumPersonal.getNacionalidad());
			institucionNombre	= catInstitucionDao.getNombreInstitucion(alumAcademico.getObreroInstitucion());
			edad 				= alumPersonalDao.getEdad(codigoAlumno);
			esInscrito 			= alumPersonalDao.esInscrito(codigoAlumno);
			carreraNombre 		= mapaPlanDao.getCarreraSe(planId);
			grado 				= alumPlanDao.mapeaRegId(codigoAlumno, planId).getGrado();
			ciclo 				= alumPlanDao.mapeaRegId(codigoAlumno, planId).getCiclo();
			tipoAlumno 			= catTipoAlumnoDao.getNombreTipo(alumAcademico.getTipoAlumno());
			religionNombre 		= catReligionDao.getNombreReligion(alumPersonal.getReligionId());			
		}
		
		// Operaciones a realizar en la pantalla	
		if (accion.equals("3")){		
			datos.setCodigoPersonal(codigoAlumno);			
			datos.setComputadora(request.getParameter("Computadora"));
			datos.setInstrumentos(request.getParameter("Instrumentos"));			
			datos.setTipoSangre(request.getParameter("TipoSangre"));
			datos.setTratamiento(request.getParameter("Tratamiento"));
			datos.setMotivo(request.getParameter("Motivo"));
			datos.setCelular(request.getParameter("Celular"));
			datos.setCorreo(request.getParameter("Correo"));
			datos.setTelefono(request.getParameter("Telefono"));
			if (alumPersonalDao.existeReg(codigoAlumno)){				
					resultado = "Updated: "+alumPersonal.getCodigoPersonal();
					if (intDatosAlumnoDao.existeReg(codigoAlumno)) 
						intDatosAlumnoDao.updateReg(datos);
					else 
						intDatosAlumnoDao.insertReg(datos);			
			}else{
				resultado = "Not found: "+alumPersonal.getCodigoPersonal();
			}		
		}
		
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("alumAcademico", alumAcademico);
		modelo.addAttribute("datos", datos);
		modelo.addAttribute("paisNombre", paisNombre);
		modelo.addAttribute("estadoNombre", estadoNombre);
		modelo.addAttribute("nacionalidad", nacionalidad);
		modelo.addAttribute("institucionNombre", institucionNombre);
		modelo.addAttribute("edad", edad);
		modelo.addAttribute("esInscrito", esInscrito);
		modelo.addAttribute("carreraNombre", carreraNombre);
		modelo.addAttribute("grado", grado);
		modelo.addAttribute("ciclo", ciclo);
		modelo.addAttribute("tipoAlumno", tipoAlumno);
		modelo.addAttribute("religionNombre",  religionNombre);
		
		return "portales/preceptor/datosPersonales";
	}
	
	@RequestMapping("/portales/preceptor/resumen")
	public String portalesPreceptorResumen(HttpServletRequest request){
		
		String codigoAlumno = request.getParameter("CodigoAlumno") == null ? "0" : request.getParameter("CodigoAlumno");
		
		HttpSession sesion 			= ((HttpServletRequest) request).getSession();
		if (sesion != null) {
			sesion.setAttribute("codigoAlumno", codigoAlumno);
		}		
		return "redirect:/portales/alumno/resumen";
	}
	
	@RequestMapping("/portales/preceptor/disciplina_alta")
	public String portalesPreceptorDisciplinaAlta(HttpServletRequest request, Model modelo){
		String codigoAlumno 	= "0";
		String codigoUsuario 	= "0";
		String nombreAlumno 	= "-";
		boolean existeAlumno 	= false;

		HttpSession sesion 		= ((HttpServletRequest) request).getSession();
		if (sesion!=null) {
			codigoAlumno 		= (String)sesion.getAttribute("codigoAlumno");
			codigoUsuario 		= (String)sesion.getAttribute("codigoPersonal");
			if (alumPersonalDao.existeReg(codigoAlumno)){
				existeAlumno = true;
				nombreAlumno 		= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
			}			
		}
		
		String dormitorioId 	= (String) sesion.getAttribute("dormitorioId");
		boolean esAdmin			= (boolean) sesion.getAttribute("admin");
		boolean esPreceptor 	= false;
		
		String folio 				= request.getParameter("Folio")==null?"0":request.getParameter("Folio");	
		String accion				= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		String fecha				= request.getParameter("Fecha")==null?aca.util.Fecha.getHoy():request.getParameter("Fecha");	
		String mensaje 				= "";
		
		List<IntReporte> lisReportes = intReporteDao.lisAll(" ORDER BY REPORTE_NOMBRE");

		IntAlumReporte reporte = new IntAlumReporte();

		esPreceptor = intDormitorioDao.existePreceptor(codigoUsuario);

		if(intAlumReporteDao.existeReg(codigoAlumno, folio)){
			reporte = intAlumReporteDao.mapeaRegId(codigoAlumno, folio);
		}

		if(accion.equals("1")){
			if(intAlumReporteDao.existeReg(codigoAlumno, folio)){
				reporte.setFecha(fecha);
				reporte.setReporteId(request.getParameter("ReporteId"));
				reporte.setUsuario(codigoUsuario);
				reporte.setComentario(request.getParameter("Comentario"));
				reporte.setCantidad(request.getParameter("Cantidad"));
				reporte.setDormitorio(dormitorioId);
				if(intAlumReporteDao.updateReg(reporte)){
					mensaje = "1";
				}
			}else{
				reporte.setCodigoPersonal(codigoAlumno);
				reporte.setFolio(intAlumReporteDao.maximoReg(codigoAlumno));
				reporte.setFecha(fecha);
				reporte.setReporteId(request.getParameter("ReporteId"));
				reporte.setUsuario(codigoUsuario);
				reporte.setComentario(request.getParameter("Comentario"));
				reporte.setCantidad(request.getParameter("Cantidad"));
				reporte.setDormitorio(dormitorioId);
				if(intAlumReporteDao.insertReg(reporte)){
					mensaje = "1";
				}
			}
		}

		modelo.addAttribute("codigoAlumno", codigoAlumno);
		modelo.addAttribute("codigoUsuario", codigoUsuario);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("existeAlumno", existeAlumno);
		modelo.addAttribute("dormitorioId", dormitorioId);
		modelo.addAttribute("esAdmin", esAdmin);
		modelo.addAttribute("esPreceptor", esPreceptor);

		modelo.addAttribute("folio", folio);
		modelo.addAttribute("accion", accion);
		modelo.addAttribute("fecha", fecha);
		modelo.addAttribute("mensaje", mensaje);

		modelo.addAttribute("lisReportes", lisReportes);
		modelo.addAttribute("reporte", reporte);

		// enviarConEnoc(request,"Error-aca.ContPortalesPreceptor|portalesPreceptorDisciplinaAlta");
		return "portales/preceptor/disciplina_alta";
	}

	@RequestMapping("/portales/preceptor/disciplina")
	public String portalesPreceptorDisciplina(HttpServletRequest request, Model modelo){
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		
		boolean esAdmin			= (boolean) sesion.getAttribute("admin");
		boolean esPreceptor 	= false;
		String dormitorioId 	= "0";
		
		String codigoPersonal	= request.getParameter("codigo")==null?"0":request.getParameter("codigo");
		String folio 			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");	
		String accion			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		String orden			= request.getParameter("orden");
		String fechaIni 		= request.getParameter("FechaIni")==null?aca.util.Fecha.getInicioMes():request.getParameter("FechaIni");
		String fechaFin 		= request.getParameter("FechaFni")==null?aca.util.Fecha.getHoy():request.getParameter("FechaFni");
		
		if (sesion!=null) {
			sesion.setAttribute("menuPreceptor", "7");
		}
		if (codigoPersonal.equals("0"))	codigoPersonal = (String) sesion.getAttribute("codigoPreceptor");
		esPreceptor = intDormitorioDao.existePreceptor(codigoPersonal);
		if (esAdmin || esPreceptor) {
			dormitorioId = (String) sesion.getAttribute("dormitorioId");
			if (dormitorioId.equals("0")) {
				dormitorioId = String.valueOf(intAccesoDao.tieneAcceso(codigoPersonal));
			}
		}else {
			dormitorioId = String.valueOf(intAccesoDao.tieneAcceso(codigoPersonal));
		}
		
		if(orden == null) orden = "ORDER BY APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE";
		
		if (accion.equals("1")){
			if (intAlumReporteDao.deleteReg(request.getParameter("CodigoAlumno"), folio)){
			}else{
			}
		 }
		
		// Map de reportes
		HashMap<String, IntReporte> mapReporte		= intReporteDao.mapAll("");
		HashMap<String, AlumPersonal> mapAlumnos 	= alumPersonalDao.mapAlumnosInternos(dormitorioId);
		HashMap<String, String> mapEmpleadoCorto    = empleadoDao.mapEmpleadoCorto();
		
		// Lista de reportes en el rango de fechas
		List <IntAlumReporte> listReportes = intAlumReporteDao.listReportesPorDormi(fechaIni, fechaFin, dormitorioId, "ORDER BY TO_CHAR(ENOC.INT_ALUM_REPORTE.FECHA,'YYYY-MM-DD'), ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");
		
		String nombreDormitorio = intDormitorioDao.getNombre(dormitorioId);
		
		modelo.addAttribute("esAdmin", esAdmin);
		modelo.addAttribute("esPreceptor", esPreceptor);
		modelo.addAttribute("fechaIni", fechaIni);
		modelo.addAttribute("fechaFin", fechaFin);
		modelo.addAttribute("nombreDormitorio", nombreDormitorio);
		modelo.addAttribute("mapReporte", mapReporte);
		modelo.addAttribute("mapAlumnos", mapAlumnos);
		modelo.addAttribute("mapEmpleadoCorto", mapEmpleadoCorto);
		modelo.addAttribute("listReportes", listReportes);
		
		return "portales/preceptor/disciplina";
	}

	@RequestMapping("/portales/preceptor/ficha")
	public String portalesPreceptorFicha(HttpServletRequest request){		
		return "portales/preceptor/ficha";
	}

	@RequestMapping("/portales/preceptor/internos_inscritos")
	public String portalesPreceptorInternosInscritos(HttpServletRequest request, Model modelo){
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		
		String fechaIni			= request.getParameter("FechaIni")==null?(String) sesion.getAttribute("fechaIni"):request.getParameter("FechaIni");
		String fechaFin			= request.getParameter("FechaFin")==null?(String) sesion.getAttribute("fechaFin"):request.getParameter("FechaFin");
		
		String codigoPersonal	= request.getParameter("codigo")==null?"0":request.getParameter("codigo");
		String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		
		if (sesion!=null) {
			sesion.setAttribute("menuPreceptor", "6");
		}
		if (accion.equals("1")){		
			sesion.setAttribute("fechaIni", fechaIni);
			sesion.setAttribute("fechaFin", fechaFin);
		}
		
		boolean esAdmin			= (boolean) sesion.getAttribute("admin");
		boolean esPreceptor		= false;
		String dormitorioId 	= "0";
		String orden			= request.getParameter("orden");
		
		if(orden == null) orden = "ORDER BY APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE";
		
		if (codigoPersonal.equals("0"))	codigoPersonal = (String) sesion.getAttribute("codigoPreceptor");
		esPreceptor = intDormitorioDao.existePreceptor(codigoPersonal);
		if (esAdmin || esPreceptor) {
			dormitorioId = (String) sesion.getAttribute("dormitorioId");
			if (dormitorioId.equals("0")) {
				dormitorioId = String.valueOf(intAccesoDao.tieneAcceso(codigoPersonal));
			}
		}else {
			dormitorioId = String.valueOf(intAccesoDao.tieneAcceso(codigoPersonal));
		}
		
		List<Inscritos> lisInscritos = inscritosDao.getListAllUM("WHERE RESIDENCIA_ID = 'I' AND DORMITORIO = '"+dormitorioId+"' AND MODALIDAD_ID IN (1,4) AND F_INSCRIPCION "
				+ " BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY') "+orden);
		
		String periodoId = catPeriodoDao.getPeriodo();
		
		HashMap<String,String> mapMentorAlumno 	= mentAlumnoDao.mapMentorAlumno(periodoId);
		HashMap<String,String> mapExiste 		= intAlumnoDao.mapExiste(dormitorioId);
		HashMap<String,CatCarrera> mapaCarrera 	= catCarreraDao.getMapAll("");
		HashMap<String, Maestros> mapaMaestros 	= maestrosDao.mapaMaestros();
		
		String nombreDormitorio = intDormitorioDao.getNombre(dormitorioId);
		
		modelo.addAttribute("esAdmin", esAdmin);
		modelo.addAttribute("esPreceptor", esPreceptor);
		modelo.addAttribute("dormitorioId", dormitorioId);
		modelo.addAttribute("fechaIni", fechaIni);
		modelo.addAttribute("fechaFin", fechaFin);
		modelo.addAttribute("lisInscritos", lisInscritos);
		modelo.addAttribute("mapMentorAlumno", mapMentorAlumno);
		modelo.addAttribute("mapExiste", mapExiste);
		modelo.addAttribute("mapaCarrera", mapaCarrera);
		modelo.addAttribute("mapaMaestros", mapaMaestros);
		modelo.addAttribute("nombreDormitorio", nombreDormitorio);
		
		return "portales/preceptor/internos_inscritos";
	}

	@RequestMapping("/portales/preceptor/internos")
	public String portalesPreceptorInternos(HttpServletRequest request, Model modelo){
		
		//long inicio = System.currentTimeMillis();
		boolean esAdmin 			= false;
		boolean esPreceptor			= false;
		String dormitorioId 	= "0";
		String codigoPersonal	= request.getParameter("codigo")==null?"0":request.getParameter("codigo");
		String orden 			= request.getParameter("orden")==null?"cuarto":request.getParameter("orden");
		String saldo 			= request.getParameter("Saldo")==null?"N":request.getParameter("Saldo");
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			sesion.setAttribute("menuPreceptor", "5");
			esAdmin 				= (boolean) sesion.getAttribute("admin");
			if (codigoPersonal.equals("0")) {
				codigoPersonal = (String) sesion.getAttribute("codigoPreceptor");				
			}
			esPreceptor = intDormitorioDao.existePreceptor(codigoPersonal);
			if (esAdmin || esPreceptor) {
				dormitorioId = (String) sesion.getAttribute("dormitorioId");
				if (dormitorioId.equals("0")) {
					dormitorioId = String.valueOf(intAccesoDao.tieneAcceso(codigoPersonal));
				}
			}else {
				dormitorioId = intAccesoDao.getDormitorioId(codigoPersonal);
			}	
		}		
		String periodoId	 	= catPeriodoDao.getPeriodo();
		IntDormitorio dormi 	= intDormitorioDao.mapeaRegId(dormitorioId);
		
	 	if (orden.equals("matricula")) orden = "CODIGO_PERSONAL";
	 	else if (orden.equals("nombre")) orden = "ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)";
	 	else if (orden.equals("carrera")) orden = "ENOC.ALUM_CARRERA(CODIGO_PERSONAL), ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)";
	 	else if (orden.equals("cuarto")) orden = "TO_CHAR(CUARTO_ID,'000'), ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)";
		
		List<IntAlumno> lisAlumnos 					= intDormitorioDao.getAlumnos(dormitorioId,"");
		
		HashMap<String, String> mapElogio 			= condAlumnoDao.getElogios();		
		HashMap<String, String> mapUnidades 		= condAlumnoDao.getUnidades();		
		HashMap<String, String> mapMaestros 		= maestrosDao.mapMaestroNombre("APELLIDOS") ;		
		HashMap<String, AlumPersonal> mapAlumnos 	= alumPersonalDao.mapAlumnosInternos(dormitorioId);
		HashMap<String, CatCarrera> mapCarrera 		= catCarreraDao.getMapAll("");
		HashMap<String, CatReligion> mapReligion 	= catReligionDao.getMapAll("");		
		HashMap<String, IntCuarto> mapCuarto 		= intCuartoDao.mapCuartos(dormitorioId);
		HashMap<String, MentAlumno> mapMentor 		= mentAlumnoDao.mapMentorAlumnoVigente(periodoId);		
		HashMap<String, String> mapAlumPlan 		= alumPlanDao.mapaPlanDelInterno(dormitorioId);
		HashMap<String, AlumAcademico> mapAcademico = alumAcademicoDao.mapInternos(dormitorioId);		
		HashMap<String, String> mapaInscritos 		= inscritosDao.getMapaInscritos();		
		HashMap<String, MapaPlan> mapaPlanes 		= mapaPlanDao.mapPlanes("'A','V','I'");
		HashMap<String,FinSaldo> mapSaldos			= new HashMap<String,FinSaldo>();
		if (saldo.equals("S")) {
			for (IntAlumno alumno : lisAlumnos){		
				// Consulta el saldo del estudiante
				FinSaldo finSaldo 	= new FinSaldo();
				try {		
					RestTemplate restTemplate = new RestTemplate();				
					finSaldo = restTemplate.getForObject("http://172.16.251.110:8080/infor/reportes/api/fe/saldo/"+alumno.getCodigoPersonal(), FinSaldo.class);
					mapSaldos.put(alumno.getCodigoPersonal(), finSaldo);
				}catch(Exception ex) {
					System.out.println("Error en saldo:"+alumno.getCodigoPersonal());
				}
			}
		}	
		
		modelo.addAttribute("esAdmin", esAdmin);
		modelo.addAttribute("esPreceptor", esPreceptor);
		modelo.addAttribute("orden", orden);
		modelo.addAttribute("dormitorioId", dormitorioId);
		modelo.addAttribute("dormi", dormi);
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("mapMaestros", mapMaestros);		
		modelo.addAttribute("mapAlumnos", mapAlumnos);
		modelo.addAttribute("mapCarrera", mapCarrera);
		modelo.addAttribute("mapReligion", mapReligion);
		modelo.addAttribute("mapCuarto", mapCuarto);
		modelo.addAttribute("mapMentor", mapMentor);
		modelo.addAttribute("mapAlumPlan", mapAlumPlan);
		modelo.addAttribute("mapAcademico", mapAcademico);
		modelo.addAttribute("mapElogio", mapElogio);
		modelo.addAttribute("mapUnidades", mapUnidades);
		modelo.addAttribute("mapSaldos", mapSaldos);
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		modelo.addAttribute("mapaInscritos", mapaInscritos);
		
		return "portales/preceptor/internos";
	}
	
	@RequestMapping("/portales/preceptor/cumple")
	public String portalesPreceptorCumple(HttpServletRequest request, Model modelo){		
		boolean esAdmin 		= false;
		boolean esPreceptor		= false;
		String dormitorioId 	= "0";
		String codigoPersonal	= request.getParameter("codigo")==null?"0":request.getParameter("codigo");	
		String mes				= request.getParameter("Mes")== null?aca.util.Fecha.getHoy().substring(3,5):request.getParameter("Mes");		
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			sesion.setAttribute("menuPreceptor", "4");
			esAdmin 				= (boolean) sesion.getAttribute("admin");
			if (codigoPersonal.equals("0")) {
				codigoPersonal = (String) sesion.getAttribute("codigoPreceptor");				
			}
			esPreceptor = intDormitorioDao.existePreceptor(codigoPersonal);
			if (esAdmin || esPreceptor) {
				dormitorioId = (String) sesion.getAttribute("dormitorioId");
				if (dormitorioId.equals("0")) {
					dormitorioId = String.valueOf(intAccesoDao.tieneAcceso(codigoPersonal));
				}
			} else {
				dormitorioId = intAccesoDao.getDormitorioId(codigoPersonal);
			}
		}
		IntDormitorio intDormitorio 	= intDormitorioDao.mapeaRegId(dormitorioId);		
		List<AlumPersonal> lisAlumnos 				= alumPersonalDao.lisPorDormitorio(dormitorioId, mes," ORDER BY SUBSTR(F_NACIMIENTO,9,2)");
		HashMap<String, IntAlumno> mapaInternos 	= intAlumnoDao.mapaInternos(dormitorioId);
		HashMap<String, IntCuarto> mapaCuartos 		= intCuartoDao.mapCuartos(dormitorioId); 
		
		modelo.addAttribute("esAdmin", esAdmin);	
		modelo.addAttribute("esPreceptor", esPreceptor);		
		modelo.addAttribute("dormitorioId", dormitorioId);		
    	modelo.addAttribute("intDormitorio", intDormitorio);    
        modelo.addAttribute("mes", mes); 
        modelo.addAttribute("lisAlumnos", lisAlumnos);
        modelo.addAttribute("mapaInternos", mapaInternos);
        modelo.addAttribute("mapaCuartos", mapaCuartos);

		return "portales/preceptor/cumple";
	}
	
	@ResponseBody
	@RequestMapping(value="/portales/preceptor/getFoto")
	public String portalesPreceptorGetFoto (HttpServletRequest request) {
		String codigo = request.getParameter("Codigo")==null?"0":request.getParameter("Codigo");
		String imagen = "<img src=\"../../fotoMenu?Codigo="+codigo+"\" height=\"60px\"/>";
		return imagen;
	}

	@RequestMapping("/portales/preceptor/personal")
	public String portalesPreceptorPersonal(HttpServletRequest request, Model modelo){
		
		HttpSession sesion 		= ((HttpServletRequest) request).getSession();
		String accion 			= request.getParameter("accion")==null?"0":request.getParameter("accion");	
		String codigoPersonal	= request.getParameter("codigo")==null?"0":request.getParameter("codigo");
		String dormitorioId 	= request.getParameter("dormitorioId")==null?"0":request.getParameter("dormitorioId");	
		
		boolean esAdmin			= (boolean) sesion.getAttribute("admin");
		boolean esPreceptor 	= false;
		boolean esAsistente 	= false;
		String rol	 			= request.getParameter("rol");
		String pasillo 			= request.getParameter("pasillo");
		String r	 			= request.getParameter("r");
		String cP	 			= request.getParameter("cP");
		
		String mensaje			= "-";
		
		if (sesion!=null) {
			sesion.setAttribute("menuPreceptor", "1");
		}
		
		if (codigoPersonal.equals("0"))	codigoPersonal = (String) sesion.getAttribute("codigoPreceptor");

		esPreceptor 			= intDormitorioDao.existePreceptor(codigoPersonal);
		esAsistente				= intAccesoDao.esAsistente(codigoPersonal);
		
		if(esPreceptor || esAdmin){
			dormitorioId = (String) sesion.getAttribute("dormitorioId");
		}else if(esAsistente){
			dormitorioId = intAccesoDao.getDormitorioId(codigoPersonal);
		}	

		IntDormitorio intDormitorio = new IntDormitorio();
		if(intDormitorioDao.existeReg(dormitorioId)) {
			intDormitorio = intDormitorioDao.mapeaRegId(dormitorioId);
		}

		String nombreMaestro 	= maestrosDao.getNombreMaestro(intDormitorio.getPreceptor(),"NOMBRE");
		String codigosAcceso	= intAccesoDao.getCodigos();
		
		if(intAccesoDao.esMonitor(codigoPersonal)) {
			return "redirect:/portales/preceptor/asignarCuartos";
		}
		
		IntAcceso dormiAcceso = new IntAcceso();
		if (accion.equals("guardar")){			
			dormiAcceso.setCodigoPersonal(codigoPersonal);
			dormiAcceso.setDormitorioId(dormitorioId);
			dormiAcceso.setRol(rol);
			dormiAcceso.setPasillo(pasillo);
			if (intAccesoDao.existeRegId(codigoPersonal, rol, dormitorioId)==false){
				if (intAccesoDao.insertReg(dormiAcceso)) {
					sesion.setAttribute("preceptores", intDormitorioDao.getPreceptores());
					mensaje = "Saved";
				}else {
					mensaje = "Error saving";
				}
			}			
		}else if (accion.equals("eliminar")){
			dormiAcceso.setCodigoPersonal(cP);
			dormiAcceso.setDormitorioId(dormitorioId);
			dormiAcceso.setRol(r);
			if (intAccesoDao.deleteReg(dormitorioId, cP, r)) {
				sesion.setAttribute("preceptores", intDormitorioDao.getPreceptores());
				mensaje = "Deleted";
			}else {
				mensaje = "Error deleting";
			}
		}else if (accion.equals("update")){
			dormiAcceso = intAccesoDao.mapeaRegId(dormitorioId,cP,r);
			dormiAcceso.setPasillo(pasillo);
			if (intAccesoDao.updateReg(dormiAcceso)) {
				sesion.setAttribute("preceptores", intDormitorioDao.getPreceptores());
				mensaje = "Updated";
			}else {
				mensaje = "Error updating";
			}
		}
		
		List<IntAcceso> vPersonal 				= intAccesoDao.getPersonal(dormitorioId,"ORDER BY ROL DESC, PASILLO");		
		HashMap<String,String> mapaUsuarios 	= usuariosDao.mapaUsuarios(codigosAcceso);		
		
		modelo.addAttribute("esAdmin", esAdmin);
		modelo.addAttribute("esPreceptor", esPreceptor);
		modelo.addAttribute("intDormitorio", intDormitorio);
		modelo.addAttribute("dormitorioId", dormitorioId);
		modelo.addAttribute("accion", accion);
		modelo.addAttribute("cP", cP);
		modelo.addAttribute("vPersonal", vPersonal);
		modelo.addAttribute("nombreMaestro", nombreMaestro);
		modelo.addAttribute("mapaUsuarios", mapaUsuarios);
		modelo.addAttribute("mensaje", mensaje);
		
		return "portales/preceptor/personal";
	}
	
	@RequestMapping("/portales/preceptor/portal")
	public String portalesPreceptorPortal(HttpServletRequest request, Model modelo){
		
		return "portales/preceptor/portal";
	}
}