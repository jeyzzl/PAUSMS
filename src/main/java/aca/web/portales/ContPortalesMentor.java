package aca.web.portales;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import aca.alerta.spring.AlertaDocAlum;
import aca.alerta.spring.AlertaDocAlumDao;
import aca.alerta.spring.AlertaPeriodoDao;
import aca.alumno.spring.AlumAcademico;
import aca.alumno.spring.AlumAcademicoDao;
import aca.alumno.spring.AlumActualiza;
import aca.alumno.spring.AlumActualizaDao;
import aca.alumno.spring.AlumCovid;
import aca.alumno.spring.AlumCovidDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanDao;
import aca.alumno.spring.AlumUbicacion;
import aca.alumno.spring.AlumUbicacionDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatCiudad;
import aca.catalogo.spring.CatCiudadDao;
import aca.catalogo.spring.CatEstado;
import aca.catalogo.spring.CatEstadoDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatPais;
import aca.catalogo.spring.CatPaisDao;
import aca.catalogo.spring.CatPeriodo;
import aca.catalogo.spring.CatPeriodoDao;
import aca.catalogo.spring.CatReligionDao;
import aca.catalogo.spring.CatTipoAlumnoDao;
import aca.catalogo.spring.CatTipoCurso;
import aca.catalogo.spring.CatTipoCursoDao;
import aca.disciplina.spring.CondAlumno;
import aca.disciplina.spring.CondAlumnoDao;
import aca.disciplina.spring.CondLugarDao;
import aca.disciplina.spring.CondReporte;
import aca.disciplina.spring.CondReporteDao;
import aca.financiero.spring.FinSaldo;
import aca.internado.spring.IntDormitorioDao;
import aca.kardex.spring.KrdxCursoActDao;
import aca.leg.spring.LegExtdoctosDao;
import aca.mentores.spring.MentAlumno;
import aca.mentores.spring.MentAlumnoDao;
import aca.mentores.spring.MentAlumnoDatos;
import aca.mentores.spring.MentAlumnoDatosDao;
import aca.mentores.spring.MentContacto;
import aca.mentores.spring.MentContactoDao;
import aca.mentores.spring.MentMotivo;
import aca.mentores.spring.MentMotivoDao;
import aca.plan.spring.MapaCreditoDao;
import aca.plan.spring.MapaCurso;
import aca.plan.spring.MapaCursoDao;
import aca.plan.spring.MapaPlanDao;
import aca.residencia.spring.ResDatos;
import aca.residencia.spring.ResDatosDao;
import aca.residencia.spring.ResRazonDao;
import aca.vista.spring.AlumnoCursoDao;
import aca.vista.spring.InscritosDao;
import aca.vista.spring.MaestrosDao;
import aca.vista.spring.PlanCiclo;
import aca.vista.spring.PlanCicloDao;

@Controller
public class ContPortalesMentor {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;		
	
	@Autowired	
	private MentContactoDao mentContactoDao;
	
	@Autowired	
	private MaestrosDao maestrosDao;
	
	@Autowired	
	private MentMotivoDao mentMotivoDao;
	
	@Autowired	
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired	
	private MentAlumnoDao mentAlumnoDao;
	
	@Autowired	
	private CatPeriodoDao catPeriodoDao;
	
	@Autowired	
	private CondAlumnoDao condAlumnoDao;
	
	@Autowired	
	private CondLugarDao condLugarDao;
	
	@Autowired	
	private CondReporteDao condReporteDao;
	
	@Autowired	
	private InscritosDao inscritosDao;
	
	@Autowired	
	private AlumActualizaDao alumActualizaDao;
	
	@Autowired	
	private AlumnoCursoDao alumnoCursoDao;
	
	@Autowired	
	private KrdxCursoActDao krdxCursoActDao;
	
	@Autowired	
	private CargaDao cargaDao;
	
	@Autowired
	AlumCovidDao alumCovidDao;
	
	@Autowired
	AlertaPeriodoDao alertaPeriodoDao;
	
	@Autowired
	AlertaDocAlumDao alertaDocAlumDao;

	@Autowired
	CatFacultadDao catFacultadDao;

	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	CatPaisDao catPaisDao;
	
	@Autowired
	MapaPlanDao mapaPlanDao;
	
	@Autowired
	AlumPlanDao alumPlanDao;
	
	@Autowired
	MapaCursoDao mapaCursoDao;
	
	@Autowired
	PlanCicloDao planCicloDao;
	
	@Autowired
	CatTipoCursoDao catTipoCursoDao;
	
	@Autowired
	LegExtdoctosDao legExtdoctosDao;
	
	@Autowired
	CatTipoAlumnoDao catTipoAlumnoDao;
	
	@Autowired
	AlumAcademicoDao alumAcademicoDao;
	
	@Autowired
	MentAlumnoDatosDao mentAlumnoDatosDao;
	
	@Autowired
	CatReligionDao catReligionDao;
	
	@Autowired
	AlumUbicacionDao alumUbicacionDao;
	
	@Autowired
	CatEstadoDao catEstadoDao;
	
	@Autowired
	CatCiudadDao catCiudadDao;
	
	@Autowired
	IntDormitorioDao intDormitorioDao;
	
	@Autowired
	ResDatosDao resDatosDao;
	
	@Autowired
	ResRazonDao resRazonDao;
	
	@Autowired
	MapaCreditoDao mapaCreditoDao;
	
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
			//System.out.println("Abrir conEnoc"+url);
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/portales/mentor/solicitudAccion")
	public String portalesMentorSolicitudAccion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPortalesMentor|portalesMentorSolicitudAccion");
		return "portales/mentor/solicitudAccion";
	}
	
	@RequestMapping("/portales/mentor/contacto_del")
	public String portalesMentorContactoDel(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPortalesMentor|portalesMentorContactoDel");
		return "portales/mentor/contacto_del";
	}
	
	//***
	@RequestMapping("/portales/mentor/entra_carrera")
	public String portalesMentorEntraCarrera(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPortalesMentor|portalesMentorEntraCarrera");
		return "portales/mentor/entra_carrera";
	}
	
	@RequestMapping("/portales/mentor/entrevistas")
	public String portalesMentorEntrevistas(HttpServletRequest request, Model modelo){
		
		String periodoId		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String mentorId			= "0";
		String mentorNombre		= "-";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();		
        if (sesion!=null){
        	mentorId 			= (String) sesion.getAttribute("codigoPersonal");
        	mentorNombre		= maestrosDao.getNombreMaestro(mentorId, "NOMBRE");
        }       
 
    	List<MentContacto> lisEntrevistas		= mentContactoDao.getListAlumnosEnt(mentorId, periodoId, " ORDER BY TO_DATE(FECHA_CONTACTO,'DD/MM/YYYY')");
    	HashMap<String,MentMotivo> mapaMotivos	= mentMotivoDao.mapMotivo();
    	HashMap<String,String> mapaAlumnos	= alumPersonalDao.mapaAlumnosAconsejados(periodoId, mentorId);
		
		modelo.addAttribute("mentorNombre", mentorNombre);
		modelo.addAttribute("lisEntrevistas", lisEntrevistas);
		modelo.addAttribute("mapaMotivos", mapaMotivos);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		
		return "portales/mentor/entrevistas";
	}
	
	@RequestMapping("/portales/mentor/frm_mentor_contacto")
	public String portalesMentorFrmMentorContacto(HttpServletRequest request, Model modelo){
		
		String periodo 	= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		
		modelo.addAttribute("periodo", periodo);
		
		return "portales/mentor/frm_mentor_contacto";
	}
	
	@RequestMapping("/portales/mentor/grabar")
	public String portalesMentorGrabar(HttpServletRequest request){
		String usuario		= "-";
		HttpSession sesion	= ((HttpServletRequest) request).getSession();
		if (sesion != null) {
			usuario = (String) sesion.getAttribute("codigoPersonal");
		}
		
		String periodoId		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String codigoAlumno		= request.getParameter("CodigoAlumno")==null?"0":request.getParameter("CodigoAlumno");
		String tipo				= request.getParameter("Tipo")==null?"0":request.getParameter("Tipo");
		String llegada			= request.getParameter("Fechallegada")==null?"0":request.getParameter("Fechallegada");
		String vacuna	 		= request.getParameter("Vacuna")==null?"N":request.getParameter("Vacuna");
		String fechaVacuna	 	= request.getParameter("FechaVacuna")==null?"0":request.getParameter("FechaVacuna");
		String positivo			= request.getParameter("Positivo")==null?"N":request.getParameter("Positivo");
		String fechaPositivo	= request.getParameter("FechaPositivo")==null?null:request.getParameter("FechaPositivo");
		String sospechoso		= request.getParameter("Sospechoso")==null?"N":request.getParameter("Sospechoso");
		String fechaSospechoso	= request.getParameter("FechaSospechoso")==null?"0":request.getParameter("FechaSospechoso");
		String aislamiento 		= request.getParameter("Aislamiento")==null?"0":request.getParameter("Aislamiento");
		String finAislamiento	= request.getParameter("FinAislamiento")==null?null:request.getParameter("FinAislamiento");
		String validado			= request.getParameter("Validado")==null?"0":request.getParameter("Validado");	
		String comentario		= request.getParameter("Comentario")==null?"0":request.getParameter("Comentario");
		String fechaAlta		= aca.util.Fecha.getHoy();
		String mensaje 			= "0";
		
		AlumCovid alumCovid = new AlumCovid();
		
		alumCovid.setPeriodoId(periodoId);
		alumCovid.setCodigoPersonal(codigoAlumno);
		alumCovid.setTipo(tipo);
		alumCovid.setFechaLlegada(llegada);
		alumCovid.setVacuna(vacuna);
		alumCovid.setFechaVacuna(fechaVacuna);
		alumCovid.setPositivoCovid(positivo);
		alumCovid.setFechaPositivo(fechaPositivo);
		alumCovid.setSospechoso(sospechoso);
		alumCovid.setFechaSospechoso(fechaSospechoso);
		alumCovid.setAislamiento(aislamiento);
		alumCovid.setFinAislamiento(finAislamiento);
		alumCovid.setUsuarioAlta(usuario);
		alumCovid.setFechaAlta(fechaAlta);
		alumCovid.setComentario(comentario);
		alumCovid.setValidado(validado);

		if (alumCovidDao.existeReg(codigoAlumno,periodoId)){
			if (alumCovidDao.updateReg(alumCovid)){				
				mensaje = "1";
			}else {
				mensaje = "2";
			}
		}else {
			if (alumCovidDao.insertReg(alumCovid)){
				mensaje = "1";
			}else {
				mensaje = "2";
			}
		}		
		
		return "redirect:/portales/mentor/retorno?CodigoAlumno="+codigoAlumno+"&PeriodoId="+periodoId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/portales/mentor/mat_pendientes")
	public String portalesMentorMatPendientes(HttpServletRequest request, Model modelo){
		//enviarConEnoc(request,"Error-aca.ContPortalesMentor|portalesMentorMatPendientes");
		String codigoAlumno		= request.getParameter("matricula")==null?"0":request.getParameter("matricula");
		String nombreAlumno 		= alumPersonalDao.getNombreAlumno(codigoAlumno, "APELLIDO");		
		String planAlumno			= alumPlanDao.getPlanActual(codigoAlumno);
		
		List<MapaCurso> lisCursos				= mapaCursoDao.getListCursosPendientes(codigoAlumno, planAlumno, "ORDER BY CURSO_NOMBRE(CURSO_ID)");
		List<PlanCiclo> lisCiclos 				= planCicloDao.getListCiclosPlan(planAlumno, " ORDER BY CICLO");
		HashMap<String,String> mapaPendientes	= alumnoCursoDao.mapaMateriasPendientes(codigoAlumno, planAlumno);
		HashMap<String,CatTipoCurso> mapaTipos 	= catTipoCursoDao.getMapAll("");
		
		modelo.addAttribute("planAlumno", planAlumno);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("lisCursos", lisCursos);
		modelo.addAttribute("lisCiclos", lisCiclos);
		modelo.addAttribute("mapaPendientes", mapaPendientes);
		modelo.addAttribute("mapaTipos", mapaTipos);
		
		return "portales/mentor/mat_pendientes";
	}
	
	@RequestMapping("/portales/mentor/mentor_opciones")
	public String portalesMentorMentorOpciones(HttpServletRequest request, Model modelo){	
		
		String matricula = request.getParameter("matricula")==null?"0":request.getParameter("matricula");
		String mensaje = request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		
		AlumPersonal alumPersonal 		= new AlumPersonal();
		AlumPlan alumPlan 				= new AlumPlan();
		AlumAcademico academico			= new AlumAcademico();
		MentAlumnoDatos mentAlumnoDatos	= new MentAlumnoDatos();
		
		if(alumPersonalDao.existeAlumno(matricula)) {
			alumPersonal = alumPersonalDao.mapeaRegId(matricula);
		}
		
		if(alumPlanDao.existeCodigoPersonal(matricula)) {
			alumPlan = alumPlanDao.mapeaRegId(matricula);
		}
		
		if(alumAcademicoDao.existeReg(matricula)) {
			academico = alumAcademicoDao.mapeaRegId(matricula);
		}
		
		if(mentAlumnoDatosDao.existeReg(matricula)) {
			mentAlumnoDatos = mentAlumnoDatosDao.mapeaRegId(matricula);
		}
		
		String nombreAlumno 	= alumPersonalDao.getNombre(matricula, "NOMBRE");
		String carreraId 		= alumPlanDao.getCarreraId(matricula);
		String nombreCarrera 	= catCarreraDao.getNombreCarrera(carreraId);
		String nombrePais 		= catPaisDao.getNombrePais(alumPersonal.getNacionalidad());
		String fechaVenceFM3	= legExtdoctosDao.getFechaVenceFM3(matricula);
		String nombreTipo		= catTipoAlumnoDao.getNombreTipo(academico.getTipoAlumno());
		String nombreReligion	= catReligionDao.getNombreReligion(alumPersonal.getReligionId());
		
		modelo.addAttribute("matricula", matricula);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("nombreCarrera", nombreCarrera);
		modelo.addAttribute("nombrePais", nombrePais);
		modelo.addAttribute("fechaVenceFM3", fechaVenceFM3);
		modelo.addAttribute("nombreTipo", nombreTipo);
		modelo.addAttribute("nombreReligion", nombreReligion);
		
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("alumPlan", alumPlan);
		modelo.addAttribute("mentAlumnoDatos", mentAlumnoDatos);
		modelo.addAttribute("mensaje", mensaje);
		
		return "portales/mentor/datosPersonales";
	}
	
	@RequestMapping("/portales/mentor/grabarDatosPersonales")
	public String portalesMentorGrabarDatosPersonales(HttpServletRequest request, Model modelo){
		
		String matricula 	= request.getParameter("matricula")==null?"0":request.getParameter("matricula");
		String iglesia 		= request.getParameter("iglesia")==null?"-":request.getParameter("iglesia");
		String claseEs 		= request.getParameter("claseEs")==null?"-":request.getParameter("claseEs");
		String telefono 	= request.getParameter("telefono")==null?"-":request.getParameter("telefono");
		String email 		= request.getParameter("email")==null?"-":request.getParameter("email");
		String mensaje 		= "0";
		
		MentAlumnoDatos mentAlumnoDatos	= new MentAlumnoDatos();
		AlumPersonal alumPersonal 		= new AlumPersonal();
		
		if(alumPersonalDao.existeAlumno(matricula)) {
			alumPersonal = alumPersonalDao.mapeaRegId(matricula);
		}
		
		if(mentAlumnoDatosDao.existeReg(matricula)) {
			mentAlumnoDatos = mentAlumnoDatosDao.mapeaRegId(matricula);
		}
		
		mentAlumnoDatos.setCodigoPersonal(matricula);
		mentAlumnoDatos.setIglesia(iglesia);
		mentAlumnoDatos.setClaseEs(claseEs);
		
		alumPersonal.setTelefono(telefono);
		alumPersonal.setEmail(email);

		if(mentAlumnoDatosDao.existeReg(matricula)){
			if(mentAlumnoDatosDao.updateReg(mentAlumnoDatos)){
				if(alumPersonalDao.existeReg(matricula)){
					if(alumPersonalDao.updateReg(alumPersonal)) {
						mensaje = "1";
					}else {
						mensaje = "2";
					}
				}
			}
		}else{
			if(mentAlumnoDatosDao.insertReg(mentAlumnoDatos)){
				mensaje = "1";
			}else {
				mensaje = "2";
			}
		}
//		return "redirect:/portales/mentor/datosPersonales?matricula="+matricula+"&Mensaje="+mensaje;
		return "redirect:/portales/mentor/mentor_opciones?matricula="+matricula+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/portales/mentor/datosDomicilio")
	public String portalesMentorDatosDomicilio(HttpServletRequest request, Model modelo){	
		
		String matricula 	= request.getParameter("matricula")==null?"0":request.getParameter("matricula");
		String mensaje 		= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		String paisId 		= request.getParameter("nacPais")==null?"0":request.getParameter("nacPais");
		String estadoId		= request.getParameter("nacEdo")==null?"0":request.getParameter("nacEdo");
		String ciudadId		= request.getParameter("nacCiudad")==null?"0":request.getParameter("nacCiudad");
		String edoCd 		= "";	
		String edo 			= "";
		String cd 			= "";
		
		AlumUbicacion alumUbicacion 	= new AlumUbicacion();
		
		if(alumUbicacionDao.existeReg(matricula)) {
			alumUbicacion = alumUbicacionDao.mapeaRegId(matricula);
		}
		
		List<CatPais> paises 	= catPaisDao.getListAll(" ORDER BY NOMBRE_PAIS");
		
		if(paisId.equals("0")) {
			if (alumUbicacion.gettPais()!= null){
				paisId = alumUbicacion.gettPais();
			}
		}
		
		List<CatEstado> estados = catEstadoDao.getLista(paisId, " ORDER BY NOMBRE_ESTADO");
		
		if(estadoId.equals("0")) {
			if (alumUbicacion.gettEstado() != null) {
				estadoId = alumUbicacion.gettEstado();
			}
		}
		
		if(ciudadId.equals("0")) {
			if (alumUbicacion.gettCiudad() != null) {
				ciudadId = alumUbicacion.gettCiudad();
			}
		}
		
		List<CatCiudad> ciudades = catCiudadDao.getLista(paisId,estadoId, " ORDER BY NOMBRE_CIUDAD");
		
		if(!alumUbicacion.gettEdoCd().equals("-")) {
			edoCd = alumUbicacion.gettEdoCd();	
		}
		
		if (!paisId.equals("153")) {
			if (edoCd.contains("-")) {
				String[] arr = edoCd.split("-");
				edo = arr[0];
				cd = arr[1];
			}
		}
		
		modelo.addAttribute("matricula", matricula);
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("paisId", paisId);
		modelo.addAttribute("estadoId", estadoId);
		modelo.addAttribute("ciudadId", ciudadId);
		modelo.addAttribute("edoCd", edoCd);
		modelo.addAttribute("edo", edo);
		modelo.addAttribute("cd", cd);
		
		modelo.addAttribute("alumUbicacion", alumUbicacion);
		modelo.addAttribute("paises", paises);
		modelo.addAttribute("estados", estados);
		modelo.addAttribute("ciudades", ciudades);
		
		return "portales/mentor/datosDomicilio";
	}
	
	@RequestMapping("/portales/mentor/grabarDatosDomicilio")
	public String portalesMentorGrabarDatosDomicilio(HttpServletRequest request, Model modelo){
		
		String matricula 	= request.getParameter("matricula")==null?"0":request.getParameter("matricula");
		String mensaje 		= "0";
		String usuario 		= "0";
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();		
        if (sesion!=null){
        	usuario	= (String) sesion.getAttribute("codigoPersonal");
        }

		String pais = request.getParameter("nacPais");
		String estado = request.getParameter("nacEdo");
		String ciudad = request.getParameter("nacCiudad");
		
		if(!pais.equals("91")){
			estado = "0";
			ciudad = "0";

			String otroEstado = request.getParameter("otroEstado");
			String otraCiudad = request.getParameter("otraCiudad");
			
			alumUbicacionDao.updateOtroEdoCd(matricula, otroEstado+"-"+otraCiudad);
		}else{
			alumUbicacionDao.updateOtroEdoCd(matricula, "-");
		}
		
		AlumUbicacion alumUbicacion 	= new AlumUbicacion();
		AlumActualiza alumActualiza 	= new AlumActualiza();
		
		if(alumUbicacionDao.existeReg(matricula)) {
			alumUbicacion = alumUbicacionDao.mapeaRegId(matricula);
		}
		
		alumUbicacion.settPais(pais);
		alumUbicacion.settEstado(estado);
		alumUbicacion.settCiudad(ciudad);
		alumUbicacion.setCodigoPersonal(matricula);
		alumUbicacion.settDireccion(request.getParameter("TDireccion"));
		alumUbicacion.settColonia(request.getParameter("TColonia"));
		alumUbicacion.settApartado(request.getParameter("TApartado"));
		alumUbicacion.settCodigo(request.getParameter("TCodigo"));
		alumUbicacion.settEmail(request.getParameter("TEmail"));
		alumUbicacion.settTelefono(request.getParameter("TTelefono"));
		alumUbicacion.settCelular(request.getParameter("TCelular"));
		alumUbicacion.settComunica(request.getParameter("TComunica"));
		
		if(alumUbicacionDao.existeReg(matricula)){
			if(alumUbicacionDao.updateUbicacion(alumUbicacion)){
				if(alumActualizaDao.existeReg(matricula)){
					alumActualiza =  alumActualizaDao.mapeaRegId(matricula);
					alumActualiza.setCodigoPersonal(matricula);
					alumActualiza.setCodigoEmpleado(usuario);
					alumActualiza.setEstado("A");
					alumActualiza.setFecha(aca.util.Fecha.getHoy());
					alumActualizaDao.updateReg(alumActualiza);
				}else{
					alumActualizaDao.insertReg(alumActualiza);
				}
				mensaje = "1";
			}
		}else{
				mensaje = "2";
		}

		return "redirect:/portales/mentor/datosDomicilio?matricula="+matricula+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/portales/mentor/datosResidencia")
	public String portalesMentorDatosResidencia(HttpServletRequest request, Model modelo){	
		
		String matricula 	= request.getParameter("matricula")==null?"0":request.getParameter("matricula");
		
		AlumAcademico alumAcademico 	= new AlumAcademico();
		
		if(alumAcademicoDao.existeReg(matricula)) {
			alumAcademico = alumAcademicoDao.mapeaRegId(matricula);
		}
		
		ResDatos datos = new ResDatos();
		
		if(resDatosDao.existeReg(matricula)) {
			datos = resDatosDao.mapeaRegId(matricula);
		}
		
		String cargaId = cargaDao.getMejorCarga(matricula);
		
		String cuarto = "";
		
		if(alumAcademico.getResidenciaId().equals("I")) {
			cuarto = intDormitorioDao.getCuartoAlumno(matricula);
		}
		
		int comidas 		= alumPersonalDao.numComidas(matricula,cargaId);
		String nombreRazon 	= resRazonDao.nombreRazon(datos.getRazon());
		
		modelo.addAttribute("matricula", matricula);
		modelo.addAttribute("cuarto", cuarto);
		modelo.addAttribute("comidas", comidas);
		modelo.addAttribute("nombreRazon", nombreRazon);
		
		modelo.addAttribute("alumAcademico", alumAcademico);
		modelo.addAttribute("datos", datos);
		
		return "portales/mentor/datosResidencia";
	}
	
	@RequestMapping("/portales/mentor/datosAcademicos")
	public String portalesMentorDatosAcademicos(HttpServletRequest request, Model modelo){	
		
		String matricula 	= request.getParameter("matricula")==null?"0":request.getParameter("matricula");
		
		String planAlumno	= alumPlanDao.getPlanActual(matricula);		
		int mE = alumnoCursoDao.materiasAlumno(matricula,planAlumno,"2");
		int mO = alumnoCursoDao.materiasAlumno(matricula,planAlumno,"1");
		int mC = alumnoCursoDao.materiasAlumno(matricula,planAlumno,"3");
		int totalM = mE+mO+mC;
		
		//numero de materias reprobadas
		int mER = alumnoCursoDao.matAlumReprobadas(matricula,planAlumno,"2");
		int mOR = alumnoCursoDao.matAlumReprobadas(matricula,planAlumno,"1");
		int mCR = alumnoCursoDao.matAlumReprobadas(matricula,planAlumno,"3");
		int totalMR = mER+mOR+mCR;
		
		String ponderado = alumPersonalDao.getPromedioFinal(matricula,planAlumno);
		
		float crElec = mapaCreditoDao.creditosElecPlan(planAlumno);		
		float crObli = mapaCreditoDao.creditosObligatorios(planAlumno);		
		float total = crElec + crObli;
		// Creditos acreditados por el alumno hasta la fecha (Obligatorios y Electivos)
		float crObliAC = alumnoCursoDao.getCrObliAC(matricula, planAlumno);		
		float crElecAC = alumnoCursoDao.getCrElecAC(matricula, planAlumno);		
		float totalAC = crObliAC + crElecAC;
		float avance = (totalAC * 100) / total;
		
		List<PlanCiclo> lisCiclos = planCicloDao.getListCiclosPlan(planAlumno, "ORDER BY CICLO");
		
		String infoCiclos = "";
		
		HashMap<String, String> mapaPendientesPorCiclo = mapaCursoDao.mapaPendientesPorCiclo(matricula, planAlumno);
		
		for (PlanCiclo ciclo : lisCiclos) {
			infoCiclos += " "+ciclo.getCiclo() + ": [ ";
			if(mapaPendientesPorCiclo.containsKey(ciclo.getCiclo())) {
				infoCiclos += mapaPendientesPorCiclo.get(ciclo.getCiclo());
			}
			infoCiclos += " ],";
		}
		
		infoCiclos = "Cycle "+infoCiclos;
		
		modelo.addAttribute("matricula", matricula);
		modelo.addAttribute("totalM", totalM);
		modelo.addAttribute("totalMR", totalMR);
		modelo.addAttribute("ponderado", ponderado);
		modelo.addAttribute("avance", avance);
		
		modelo.addAttribute("infoCiclos", infoCiclos);
		
		return "portales/mentor/datosAcademicos";
	}
	
	@RequestMapping("/portales/mentor/portal")
	public String portalesMentorPortal(HttpServletRequest request, Model modelo){
		
		String mentorId 				= "0";
		String mentorNombre				= "-";
		String periodoId				= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String fecha	 				= request.getParameter("Fecha")==null?"0":request.getParameter("Fecha");
		DecimalFormat formato	 		= new java.text.DecimalFormat("###,##0.00; -###,##0.00");
		String alertaPeriodoId 			= alertaPeriodoDao.getPeriodoActivo();
		
		List<CatPeriodo> lisPeriodos	= catPeriodoDao.getListAll(" ORDER BY PERIODO_ID DESC");
		
		HttpSession sesion			= ((HttpServletRequest)request).getSession();		
        if (sesion!=null){
        	mentorId	 			= (String) sesion.getAttribute("codigoPersonal");
        	mentorNombre			= maestrosDao.getNombreMaestro(mentorId, "NOMBRE");
        	
        	if (periodoId.equals("0")) {
        		periodoId 	= (String) sesion.getAttribute("periodo");
        	}else {
        		sesion.setAttribute("periodo", periodoId);
        	}
        	if (fecha.equals("0")) {
        		fecha = (String)sesion.getAttribute("fecha");
        	}else {
        		sesion.setAttribute("fecha", fecha);
        	}
        }
        
		List<MentAlumno> lisAlumnos 				= mentAlumnoDao.getListActivosEnFecha(mentorId, periodoId, fecha, "ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");		
		HashMap<String,String> mapaConducta			= condAlumnoDao.mapaCantidades(periodoId);
		HashMap<String,String> mapaInscritos		= inscritosDao.getMapaInscritos();
		HashMap<String,AlumActualiza> mapaActualiza	= alumActualizaDao.mapaActualiza();
		HashMap<String,String> mapaAlumnos			= alumPersonalDao.mapaAconsejadosPorPeriodo(periodoId);
		HashMap<String,String> mapaCargas			= alumnoCursoDao.mapaMaximaCarga(mentorId, periodoId);
		HashMap<String,String> mapaEntrevistas		= mentContactoDao.mapEntrevistasMentor(mentorId, periodoId);
		HashMap<String,String> mapaPromedios		= krdxCursoActDao.mapaPromediosAconsejados(mentorId, periodoId,"'I','1'");
		HashMap<String,String> mapaAvances			= krdxCursoActDao.mapaAvanceEvaluaciones(mentorId, periodoId,"'I','1'");
		HashMap<String,FinSaldo> mapaSaldos			= new HashMap<String,FinSaldo>();
		HashMap<String,String> mapaAlumCovid 		= alumCovidDao.mapaPorPeriodo(alertaPeriodoId);
		HashMap<String,String> mapDocAlumPorPeriodo = alertaDocAlumDao.mapaPorPeriodo(alertaPeriodoId);
		
		for (MentAlumno alumno : lisAlumnos) {
			String cargaId = "0";
			if (mapaCargas.containsKey(alumno.getCodigoPersonal())) {
				cargaId = mapaCargas.get(alumno.getCodigoPersonal());
				//double promedio = alumnoCursoDao.promedioAlumnoCarga(alumno.getCodigoPersonal(), cargaId);
				//mapaPromedios.put(alumno.getCodigoPersonal(), formato.format(promedio).replaceAll(" ", ""));
				mapaPromedios.put(alumno.getCodigoPersonal(), "0");
			}
			// Consulta el saldo del estudiante
			FinSaldo finSaldo 	= new FinSaldo();
			try {		
				RestTemplate restTemplate = new RestTemplate();				
				finSaldo = restTemplate.getForObject("http://172.16.251.110:8080/infor/reportes/api/fe/saldo/"+alumno.getCodigoPersonal(), FinSaldo.class);
				mapaSaldos.put(alumno.getCodigoPersonal(), finSaldo);
			}catch(Exception ex) {
				System.out.println("Error en saldo:"+alumno.getCodigoPersonal());
			}
		}
		
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("alertaPeriodoId", alertaPeriodoId);
		modelo.addAttribute("mentorNombre", mentorNombre);
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("mapaConducta", mapaConducta);
		modelo.addAttribute("mapaInscritos", mapaInscritos);
		modelo.addAttribute("mapaActualiza", mapaActualiza);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("mapaCargas", mapaCargas);
		modelo.addAttribute("mapaEntrevistas", mapaEntrevistas);
		modelo.addAttribute("mapaPromedios", mapaPromedios);
		modelo.addAttribute("mapaAvances", mapaAvances);
		modelo.addAttribute("mapaSaldos", mapaSaldos);
		modelo.addAttribute("mapaAlumCovid", mapaAlumCovid);
		modelo.addAttribute("mapDocAlumPorPeriodo", mapDocAlumPorPeriodo);
		
		return "portales/mentor/portal";
	}
	
	@RequestMapping("/portales/mentor/rep_mentor_alumno_foto")
	public String portalesMentorRepMentorAlumnoFoto(HttpServletRequest request, Model modelo){
		
		String periodoId		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String mentorId			= "0";
		String mentorNombre		= "-";
		String fechaHoy 		= aca.util.Fecha.getHoy();
		HttpSession sesion		= ((HttpServletRequest)request).getSession();		
        if (sesion!=null){
        	mentorId 			= (String) sesion.getAttribute("codigoPersonal");
        	mentorNombre		= maestrosDao.getNombreMaestro(mentorId, "NOMBRE");
        }
        
        List<MentAlumno> lisAlumnos					= mentAlumnoDao.getListMentorPeriodo(mentorId, periodoId, fechaHoy, " ORDER BY 4, 1");
        HashMap<String,String> mapaAlumnos			= alumPersonalDao.mapaAlumnosAconsejados(periodoId, mentorId);
        HashMap<String, CatFacultad> mapaFacultades = catFacultadDao.getMapFacultad("");
		HashMap<String, CatCarrera> mapaCarreras 	= catCarreraDao.getMapAll("");
        
		modelo.addAttribute("mentorNombre", mentorNombre);
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		
		return "portales/mentor/rep_mentor_alumno_foto";
	}
	
	@RequestMapping("/portales/mentor/rep_mentor_alumno")
	public String portalesMentorRepMentorAlumno(HttpServletRequest request, Model modelo){		
		
		String periodoId		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String mentorId			= "0";
		String mentorNombre		= "-";
		String fechaHoy 		= aca.util.Fecha.getHoy();
		HttpSession sesion		= ((HttpServletRequest)request).getSession();		
        if (sesion!=null){
        	mentorId 			= (String) sesion.getAttribute("codigoPersonal");
        	mentorNombre		= maestrosDao.getNombreMaestro(mentorId, "NOMBRE");
        }
		
		List<MentAlumno> lisMenAlumnos				= mentAlumnoDao.getListMentorPeriodo(mentorId, periodoId, fechaHoy, "ORDER BY 4, 1");
		HashMap<String,String> mapaAlumnos			= alumPersonalDao.mapaAlumnosAconsejados(periodoId, mentorId);
		
		
		modelo.addAttribute("mentorNombre", mentorNombre);
		modelo.addAttribute("lisMenAlumnos", lisMenAlumnos);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);		
		
		return "portales/mentor/rep_mentor_alumno";
	}
	
	@RequestMapping("/portales/mentor/rep_mentor_contacto")
	public String portalesMentorRepMentorContacto(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPortalesMentor|portalesMentorRepMentorContacto");
		return "portales/mentor/rep_mentor_contacto";
	}
	
	@RequestMapping("/portales/mentor/subir")
	public String portalesMentorSubir(HttpServletRequest request, Model modelo){
		String codigoAlumno	= request.getParameter("f_codigo_personal")==null?"0":request.getParameter("f_codigo_personal");
		boolean existe 		= false;
		
		HttpSession sesion	= ((HttpServletRequest)request).getSession();		
        if (sesion!=null && alumPersonalDao.existeAlumno(codigoAlumno)){
        	sesion.setAttribute("codigoAlumno", codigoAlumno);
        	existe 			= true;
        }
        modelo.addAttribute("existe", existe);
        
		return "portales/mentor/subir";
	}
	
	@RequestMapping("/portales/mentor/unidad")
	public String portalesMentorUnidad(HttpServletRequest request, Model modelo){
		String periodoId				= request.getParameter("periodo")==null?"0":request.getParameter("periodo");
		String codigoAlumno				= request.getParameter("codigoAlumno")==null?"0":request.getParameter("codigoAlumno");
		String alumnoNombre				= "-";
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();		
        if (sesion!=null){
        	if(periodoId.equals("0")) {
    			periodoId 	= (String) sesion.getAttribute("periodo");
    		}
        }		
		if (alumPersonalDao.existeReg(codigoAlumno)){
			alumnoNombre = alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
		}
		
		List<CondAlumno> lisRegistros				= condAlumnoDao.getLista(periodoId, codigoAlumno, "ORDER BY TO_DATE(FECHA, 'DD/MM/YYYY')");
		List<CatPeriodo> lisPeriodos			 	= catPeriodoDao.getListAll("ORDER BY PERIODO_ID DESC");
		HashMap<String,String> mapaLugares			= condLugarDao.mapaLugar();
		HashMap<String,CondReporte> mapaReportes	= condReporteDao.mapaReportes();
		HashMap<String,String> mapaMaestros			= maestrosDao.mapMaestroNombre("NOMBRE");
		
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("alumnoNombre", alumnoNombre);
		modelo.addAttribute("lisRegistros", lisRegistros);
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("mapaLugares", mapaLugares);
		modelo.addAttribute("mapaReportes", mapaReportes);
		modelo.addAttribute("mapaMaestros", mapaMaestros);
		
		return "portales/mentor/unidad";
	}
	
	@RequestMapping("/portales/mentor/elegirCarga")
	public String portalesMentorElegirCarga(HttpServletRequest request, Model modelo){
		String periodoId				= request.getParameter("periodo")==null?"0":request.getParameter("periodo");		
		String codigoAlumno				= request.getParameter("codigoAlumno")==null?"0":request.getParameter("codigoAlumno");		
		String alumnoNombre				= "-";
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();		
        if (sesion!=null){
        	if(periodoId.equals("0")) {
    			periodoId 	= (String) sesion.getAttribute("periodo");
    		}       	
        }		
		if (alumPersonalDao.existeReg(codigoAlumno)){
			alumnoNombre = alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
		}		
		
		
		List<Carga> lisCargaAlumnos				 		= cargaDao.getListCargasAlumno(codigoAlumno, " ORDER BY 1 DESC"); 
		List<CatPeriodo> lisPeriodos				 	= catPeriodoDao.getListAll("ORDER BY PERIODO_ID DESC");
		HashMap<String,String> mapaMaestros				= maestrosDao.mapMaestroNombre("NOMBRE");
		HashMap<String,String> mapaMateriasPorCarga		= alumnoCursoDao.mapaMateriasPorCarga(codigoAlumno); 
		
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("alumnoNombre", alumnoNombre);
		modelo.addAttribute("lisCargaAlumnos", lisCargaAlumnos);
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("mapaMaestros", mapaMaestros);
		modelo.addAttribute("mapaMateriasPorCarga", mapaMateriasPorCarga);
		
		return "portales/mentor/elegirCarga";
	}
	
	@RequestMapping("/portales/mentor/modificarCarga")
	public String portalesMentorModificarCarga(HttpServletRequest request, Model modelo){
		String periodoId				= "0";
		String codigoAlumno				= request.getParameter("codigoAlumno")==null?"0":request.getParameter("codigoAlumno");
		String cargaId					= request.getParameter("cargaId")==null?"0":request.getParameter("cargaId");
		String codigoPersonal			="0";
		String folio					= request.getParameter("folio")==null?"0":request.getParameter("folio");
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();		
        if (sesion!=null){
        	if(periodoId.equals("0")) {
    			periodoId 	= (String) sesion.getAttribute("periodo");
    		}
        	if(codigoPersonal.equals("0")) {
        		codigoPersonal 	= (String) sesion.getAttribute("codigoPersonal");
    		}
        }
        
        MentAlumno mentAlumno = new MentAlumno();
        if(mentAlumnoDao.existeReg(periodoId, codigoPersonal, codigoAlumno, folio)) {
        	mentAlumno = mentAlumnoDao.mapeaRegId(periodoId, codigoPersonal, codigoAlumno, folio);
        	mentAlumno.setCargaId(cargaId);
        	mentAlumno.setPeriodoId(periodoId); 
        	mentAlumnoDao.updateReg(mentAlumno);
        }
				
		return "redirect:/portales/mentor/portal";
	}
	
	@RequestMapping("/portales/mentor/entrevistas_alumno")
	public String portalesMentorEntrevistasAlumno(HttpServletRequest request, Model modelo){
		String mentorId			= "0";
		String mentorNombre		= "-";
		String codigoAlumno		= request.getParameter("CodigoAlumno")==null?"":request.getParameter("CodigoAlumno");
		String periodoId		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String alumnoNombre		= "-";	
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();		
        if (sesion!=null){
        	mentorId = (String) sesion.getAttribute("codigoPersonal");
        	mentorNombre = maestrosDao.getNombreMaestro(mentorId, "NOMBRE");
        	if (periodoId.equals("0")) {
        		periodoId 	= (String) sesion.getAttribute("periodo");
        	}
        }		
		if (alumPersonalDao.existeReg(codigoAlumno)){
			alumnoNombre = alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
		}
		
		List<MentContacto> lisEntrevistas			= mentContactoDao.lisEntrevistasAlumno( mentorId, codigoAlumno, periodoId, " ORDER BY TO_DATE(FECHA_CONTACTO,'DD/MM/YYYY')");				
		HashMap<String,MentMotivo> mapaMotivos		= mentMotivoDao.mapMotivo();	
		
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("alumnoNombre", alumnoNombre);
		modelo.addAttribute("mentorNombre", mentorNombre);
		modelo.addAttribute("lisEntrevistas", lisEntrevistas);		
		modelo.addAttribute("mapaMotivos", mapaMotivos);
		
		return "portales/mentor/entrevistas_alumno";
	}
	
	@RequestMapping("/portales/mentor/editarEntrevista")
	public String portalesMentorEditarEntrevista(HttpServletRequest request, Model modelo){
		
		String mentorId			= "0";
		String mentorNombre		= "-";
		String codigoAlumno		= request.getParameter("CodigoAlumno")==null?"":request.getParameter("CodigoAlumno");
		String periodoId		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String contactoId		= request.getParameter("ContactoId")==null?"0":request.getParameter("ContactoId");
		String alumnoNombre		= "-";		
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();		
        if (sesion!=null){
        	mentorId = (String) sesion.getAttribute("codigoPersonal");
        	mentorNombre = maestrosDao.getNombreMaestro(mentorId, "NOMBRE");
        	if (periodoId.equals("0")) {
        		periodoId 	= (String) sesion.getAttribute("periodo");
        	}
        }		 
		MentContacto mentContacto = new MentContacto();
		if (mentContactoDao.existeReg(periodoId, mentorId, contactoId)) {
			mentContacto = mentContactoDao.mapeaRegId(periodoId, mentorId, contactoId);
		}
		
		if (alumPersonalDao.existeReg(codigoAlumno)) {
			alumnoNombre = alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE"); 
		}
		
		List<MentMotivo> lisMotivos 				= mentMotivoDao.getListAll(" ORDER BY 2");
		
		modelo.addAttribute("mentContacto", mentContacto);
		modelo.addAttribute("mentorNombre", mentorNombre);
		modelo.addAttribute("alumnoNombre", alumnoNombre);		
		modelo.addAttribute("lisMotivos", lisMotivos);
		
		return "portales/mentor/editarEntrevista";
	}
	
	@RequestMapping("/portales/mentor/grabarEntrevista")
	public String portalesMentorGrabarEntrevista(HttpServletRequest request, Model modelo){
		MentContacto mentContacto 	= new MentContacto();
		String periodoId 			= "0";
		String mentorId 			= "0";
		String contactoId 			= request.getParameter("ContactoId")==null?"0":request.getParameter("ContactoId");
		String codigoAlumno 		= request.getParameter("CodigoAlumno")==null?"0":request.getParameter("CodigoAlumno");
		String fecha		 		= request.getParameter("Fecha")==null?aca.util.Fecha.getHoy():request.getParameter("Fecha");
		String comentario	 		= request.getParameter("Comentario")==null?aca.util.Fecha.getHoy():request.getParameter("Comentario");
		String tipo 				= request.getParameter("Tipo")==null?"0":request.getParameter("Tipo");
		String motivos 				= request.getParameter("Motivos")==null?"0":request.getParameter("Motivos");
		String mensaje 				= "-";
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();		
        if (sesion!=null){
        	periodoId 			= (String) sesion.getAttribute("periodo");
        	mentorId 			= (String) sesion.getAttribute("codigoPersonal");        	
        }
        
        if(contactoId.equals("0")) contactoId = mentContactoDao.maximoReg(periodoId, mentorId);
        mentContacto.setPeriodoId(periodoId);
        mentContacto.setMentorId(mentorId);
        mentContacto.setContactoId(contactoId);
        mentContacto.setFecha(aca.util.Fecha.getHoy());
        mentContacto.setCodigoPersonal(codigoAlumno);					
        mentContacto.setMotivoId("0");
        mentContacto.setMiAconsejado("S");
        mentContacto.setFechaContacto(fecha);
        mentContacto.setComentario(comentario);    	
        mentContacto.setTipo(tipo);
        mentContacto.setCarreraId(mentContactoDao.getAlumCarr(codigoAlumno));
        mentContacto.setMotivos(motivos);		
        
        if(alumPersonalDao.existeReg(codigoAlumno)) {        
	    	if (mentContactoDao.existeReg(periodoId, mentorId, contactoId)){
		    	if(mentContactoDao.updateReg(mentContacto)==true){
		    		mensaje = "Updated";
		    	}else {
		    		mensaje = "Error updating";
		    	}
			}else {
				if(mentContactoDao.insertReg(mentContacto)==true){
		    		mensaje = "Saved";
		    	}else {
		    		mensaje = "Error saving";
		    	}
			}
        }else {
        	mensaje = "No existe el alumno";
        }
		return "redirect:/portales/mentor/editarEntrevista?CodigoAlumno="+codigoAlumno+"&ContactoId="+contactoId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/portales/mentor/borrarEntrevista")
	public String portalesMentorBorrarEntrevista(HttpServletRequest request, Model modelo){
		
		String periodoId 			= "0";
		String mentorId 			= "0";
		String contactoId 			= request.getParameter("ContactoId")==null?"0":request.getParameter("ContactoId");
		String codigoAlumno 		= request.getParameter("CodigoAlumno")==null?"0":request.getParameter("CodigoAlumno");
		String mensaje 				= "-";
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();		
        if (sesion!=null){
        	periodoId 			= (String) sesion.getAttribute("periodo");
        	mentorId 			= (String) sesion.getAttribute("codigoPersonal");        	
        }   
        		
    	if (mentContactoDao.existeReg(periodoId, mentorId, contactoId)){							
	    	if(mentContactoDao.deleteReg(periodoId, mentorId, contactoId)==true){
	    		mensaje = "Deleted";
	    	}else {
	    		mensaje = "Error deleting";
	    	}
		}
		
		return "redirect:/portales/mentor/entrevistas_alumno?CodigoAlumno="+codigoAlumno+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/portales/mentor/retorno")
	public String portalesMentorRetorno(HttpServletRequest request, Model modelo){
		
		String codigoAlumno		= request.getParameter("CodigoAlumno")==null?"0":request.getParameter("CodigoAlumno");
		String periodoId 		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String mensaje			= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		String nombreAlumno		= "-";
		boolean existe 			= false;
		HttpSession sesion		= ((HttpServletRequest) request).getSession();
		AlumCovid alumCovid 	= new AlumCovid();
		
		if (codigoAlumno.equals("0") && sesion != null){
			codigoAlumno 	= (String) sesion.getAttribute("codigoAlumno");
		}
		if(alumCovidDao.existeReg(codigoAlumno, periodoId)){
			existe 			= true;
			alumCovid 		= alumCovidDao.mapeaRegId(codigoAlumno, periodoId);
		}
		nombreAlumno 	= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");	
		
		modelo.addAttribute("codigoAlumno", codigoAlumno);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("existe", existe);		
		modelo.addAttribute("periodoId", periodoId);		
		modelo.addAttribute("alumCovid", alumCovid);
		modelo.addAttribute("mensaje", mensaje);	
		
		return "portales/mentor/retorno";		
	}
	
	@RequestMapping("/portales/mentor/grabarRetorno")
	public String alertaRegistroGrabarCovid(HttpServletRequest request, Model model){				
		
		String usuario		= "-";
		HttpSession sesion	= ((HttpServletRequest) request).getSession();
		if (sesion != null) {
			usuario = (String) sesion.getAttribute("codigoPersonal");
		}
		
		String periodoId		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String codigoAlumno		= request.getParameter("CodigoAlumno")==null?"0":request.getParameter("CodigoAlumno");
		String tipo				= request.getParameter("Tipo")==null?"0":request.getParameter("Tipo");
		String llegada			= request.getParameter("Fechallegada")==null?"0":request.getParameter("Fechallegada");
		String vacuna	 		= request.getParameter("Vacuna")==null?"N":request.getParameter("Vacuna");
		String fechaVacuna	 	= request.getParameter("FechaVacuna")==null?"0":request.getParameter("FechaVacuna");
		String positivo			= request.getParameter("Positivo")==null?"N":request.getParameter("Positivo");
		String fechaPositivo	= request.getParameter("FechaPositivo")==null?"0":request.getParameter("FechaPositivo");
		String sospechoso		= request.getParameter("Sospechoso")==null?"N":request.getParameter("Sospechoso");
		String fechaSospechoso	= request.getParameter("FechaSospechoso")==null?"0":request.getParameter("FechaSospechoso");
		String aislamiento 		= request.getParameter("Aislamiento")==null?"0":request.getParameter("Aislamiento");
		String finAislamiento	= request.getParameter("FinAislamiento")==null?"0":request.getParameter("FinAislamiento");
		String validado			= request.getParameter("Validado")==null?"0":request.getParameter("Validado");	
		String comentario		= request.getParameter("Comentario")==null?"0":request.getParameter("Comentario");
		String fechaAlta		= aca.util.Fecha.getHoy();
		String mensaje 			= "0";
		
		AlumCovid alumCovid = new AlumCovid();
		
		alumCovid.setPeriodoId(periodoId);
		alumCovid.setCodigoPersonal(codigoAlumno);
		alumCovid.setTipo(tipo);
		alumCovid.setFechaLlegada(llegada);
		alumCovid.setVacuna(vacuna);
		alumCovid.setFechaVacuna(fechaVacuna);
		alumCovid.setPositivoCovid(positivo);
		alumCovid.setFechaPositivo(fechaPositivo);
		alumCovid.setSospechoso(sospechoso);
		alumCovid.setFechaSospechoso(fechaSospechoso);
		alumCovid.setAislamiento(aislamiento);
		alumCovid.setFinAislamiento(finAislamiento);
		alumCovid.setUsuarioAlta(usuario);
		alumCovid.setFechaAlta(fechaAlta);
		alumCovid.setComentario(comentario);
		alumCovid.setValidado(validado);

		if (alumCovidDao.existeReg(codigoAlumno,periodoId)){
			if (alumCovidDao.updateReg(alumCovid)){				
				mensaje = "1";
			}else {
				mensaje = "2";
			}
		}else {
			if (alumCovidDao.insertReg(alumCovid)){
				mensaje = "1";
			}else {
				mensaje = "2";
			}
		}		
		return "redirect:/portales/mentor/retorno?CodigoAlumno="+codigoAlumno+"&PeriodoId="+periodoId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/portales/mentor/descargaCartaResponsiva")
	public void portalesMentorDescargaCartaResponsiva(HttpServletResponse response, HttpServletRequest request){
		
		String codigoAlumno 	= request.getParameter("CodigoAlumno") == null ? "0" : request.getParameter("CodigoAlumno");
		String periodoId 		= request.getParameter("PeriodoId") == null ? "0" : request.getParameter("PeriodoId");
		String documentoId	 	= "1";
				
		AlertaDocAlum documento = new AlertaDocAlum();		
		if(alertaDocAlumDao.existeReg(codigoAlumno, periodoId, documentoId)) {
			documento = alertaDocAlumDao.mapeaRegId(codigoAlumno, periodoId, documentoId);
		}		
		try {
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition","attachment; filename=\""+documento.getNombre()+ "\"");
			response.getOutputStream().write(documento.getArchivo());
			response.flushBuffer();
		} catch (IOException e) {
			System.out.println("ERROR");
			e.printStackTrace();
		}
	}
	
}