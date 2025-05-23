package aca.web.salida;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import aca.mail.MailService;

import aca.salida.spring.SalAuto;
import aca.salida.spring.SalBitacora;
import aca.salida.spring.SalClub;
import aca.alumno.spring.AlumAcademico;
import aca.alumno.spring.AlumAcademicoDao;
import aca.alumno.spring.AlumEstado;
import aca.alumno.spring.AlumEstadoDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlanDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatEstado;
import aca.catalogo.spring.CatEstadoDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatPais;
import aca.catalogo.spring.CatPaisDao;
import aca.emp.spring.EmpDatosDao;
import aca.salida.spring.SalAlumno;
import aca.salida.spring.SalConsejero;
import aca.salida.spring.SalSolicitud;
import aca.salida.spring.SalGrupo;
import aca.salida.spring.SalInforme;
import aca.salida.spring.SalInformeDao;
import aca.salida.spring.SalInvitado;
import aca.salida.spring.SalProposito;
import aca.salida.spring.SalPropositoDao;
import aca.vista.spring.Maestros;
import aca.vista.spring.UsuariosDao;

@Controller
public class ContSalidaSolicitud {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	aca.salida.spring.SalAutoDao salAutoDao;
	
	@Autowired
	aca.salida.spring.SalAlumnoDao salAlumnoDao;
	
	@Autowired
	aca.salida.spring.SalBitacoraDao salBitacoraDao;
	
	@Autowired
	aca.salida.spring.SalConsejeroDao salConsejeroDao;
	
	@Autowired
	aca.salida.spring.SalSolicitudDao salSolicitudDao;
	
	@Autowired
	aca.salida.spring.SalGrupoDao salGrupoDao;
	
	@Autowired
	aca.vista.spring.MaestrosDao maestrosDao;
	
	@Autowired
	aca.salida.spring.SalClubDao salClubDao;

	@Autowired
	aca.salida.spring.SalInvitadoDao salInvitadoDao;
	
	@Autowired
	CatPaisDao catPaisDao;
	
	@Autowired
	CatEstadoDao catEstadoDao;
	
	@Autowired
	AlumAcademicoDao alumAcademicoDao;
	
	@Autowired
	UsuariosDao usuariosDao;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;
	
	@Autowired
	AlumPlanDao alumPlanDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;

	@Autowired
	CatFacultadDao catFacultadDao;
	
	@Autowired
	SalInformeDao salInformeDao;
	
	@Autowired
	AlumEstadoDao alumEstadoDao;
	
	@Autowired
	private MailService mailService;
		
	@Autowired
	EmpDatosDao empDatosDao;
	
	@Autowired
	SalPropositoDao salPropositoDao;
	
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/salida/solicitud/pdf")	
	public String salidaSolicitudPdf(HttpServletRequest request, Model modelo){
		
		String salidaId 			= request.getParameter("salida")==null?"0":request.getParameter("salida");
		SalSolicitud salSolicitud	= new SalSolicitud();
		SalGrupo salGrupo			= new SalGrupo();
		String usuarioResponsable	= "-";
		String usuarioLlenado		= "-";
		String usuarioPreautorizado	= "-";
		String usuarioAutorizado	= "-";
		String paisNombre			= "-";
		String estadoNombre			= "-";
		String proposito			= "-";
		
		List<SalConsejero> lisConsejeros			= salConsejeroDao.getListAll(salidaId, "ORDER BY FOLIO");					
		List<SalAlumno> lisAlumnos					= salAlumnoDao.lisPorSalida(salidaId , "ORDER BY ALUM_DORMITORIO(CODIGO_PERSONAL), ALUM_APELLIDO(CODIGO_PERSONAL)");
		List<SalInvitado> lisInvitados 				= salInvitadoDao.lisInvitados(salidaId, " ORDER BY NOMBRE");
		List<SalAuto> lisAutos						= salAutoDao.lisSalida(salidaId, " ORDER BY TIPO");
		HashMap<String,String> mapaResidencias 		= alumAcademicoDao.mapaResidenciaSalidas(salidaId);
		HashMap<String,String> mapaBitacora 		= salBitacoraDao.mapaBitacora(salidaId);
		HashMap<String,String> mapaAlumnos 			= alumPersonalDao.mapaAlumnosEnSalida(salidaId);
		HashMap<String,AlumAcademico> mapaAcademicos= alumAcademicoDao.mapAlumnosEnSalida(salidaId);
		HashMap<String,String> mapaCarrerasAlumnos	= alumPlanDao.mapaAlumnosSalida(salidaId);
		HashMap<String,CatCarrera> mapaCarreras		= catCarreraDao.getMapAll("");		
		HashMap<String,String> mapaComAutorizacion	= salSolicitudDao.mapaComAutorizacion(salidaId);		
		
		if (salSolicitudDao.existeReg(salidaId)) {
			salSolicitud 			= salSolicitudDao.mapeaRegId(salidaId);
			proposito				 = salPropositoDao.mapeaRegId(salSolicitud.getPropositoId()).getPropositoNombre();
			salGrupo 				= salGrupoDao.mapeaRegSalida(salidaId);
			usuarioResponsable 		= usuariosDao.getNombreUsuario(salSolicitud.getResponsable(), "NOMBRE");
			usuarioLlenado 			= usuariosDao.getNombreUsuario(salSolicitud.getUsuario(), "NOMBRE");
			usuarioPreautorizado 	= usuariosDao.getNombreUsuario(salSolicitud.getPreautorizo(), "NOMBRE");
			usuarioAutorizado 		= usuariosDao.getNombreUsuario(salSolicitud.getAutorizo(), "NOMBRE");
			paisNombre 				= catPaisDao.getNombrePais(salSolicitud.getPaisId());
			estadoNombre 			= catEstadoDao.getNombreEstado(salSolicitud.getPaisId(), salSolicitud.getEstadoId());
		}
		
		modelo.addAttribute("usuarioResponsable", usuarioResponsable);
		modelo.addAttribute("usuarioLlenado", usuarioLlenado);
		modelo.addAttribute("usuarioPreautorizado", usuarioPreautorizado);
		modelo.addAttribute("usuarioAutorizado", usuarioAutorizado);
		modelo.addAttribute("salSolicitud", salSolicitud);
		modelo.addAttribute("salGrupo", salGrupo);
		modelo.addAttribute("lisConsejeros", lisConsejeros);
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("lisInvitados", lisInvitados);
		modelo.addAttribute("lisAutos", lisAutos);
		modelo.addAttribute("mapaResidencias", mapaResidencias);
		modelo.addAttribute("mapaBitacora", mapaBitacora);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("mapaAcademicos", mapaAcademicos);
		modelo.addAttribute("mapaCarrerasAlumnos", mapaCarrerasAlumnos);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaComAutorizacion", mapaComAutorizacion);
		modelo.addAttribute("paisNombre", paisNombre);
		modelo.addAttribute("estadoNombre", estadoNombre);
		modelo.addAttribute("proposito", proposito);
		
		return "salida/solicitud/pdf";
	}

	@RequestMapping("/salida/solicitud/autos")
	public String salidaSolicitudAutos(HttpServletRequest request, Model modelo){
		
		String salidaId = request.getParameter("SalidaId")==null?"0":request.getParameter("SalidaId");		
		modelo.addAttribute("lista", salAutoDao.lisSalida(salidaId," ORDER BY TIPO"));
		
		return "salida/solicitud/autos";
	}

	@RequestMapping("/salida/solicitud/grabarAuto")
	public String salidaSolicitudGrabarAuto(HttpServletRequest request, Model modelo){
		
		String salidaId  = request.getParameter("SalidaId")==null?"0":request.getParameter("SalidaId");
		String poliza	 = request.getParameter("Poliza")==null?"-":request.getParameter("Poliza");
		String telefono	 = request.getParameter("Telefono")==null?"-":request.getParameter("Telefono");
		String tipo   	 = request.getParameter("Tipo")==null?"-":request.getParameter("Tipo");
		
		SalAuto salAuto = new SalAuto();
		
		salAuto.setSalidaId(salidaId);
		salAuto.setPoliza(poliza);
		salAuto.setTelefono(telefono);
		salAuto.setTipo(tipo);
		salAuto.setFolio(salAutoDao.maximoReg(salidaId));
		salAutoDao.insertReg(salAuto);
		
		return "redirect:/salida/solicitud/autos?SalidaId="+salidaId;
	}
	
	@RequestMapping("/salida/solicitud/borrarAuto")
	public String salidaSolicitudBorrarAuto(HttpServletRequest request, Model modelo){
		
		String salidaId  = request.getParameter("SalidaId")==null?"0":request.getParameter("SalidaId");
		String folio	 = request.getParameter("Folio")==null?"-":request.getParameter("Folio");
				
		if (salAutoDao.existeReg(salidaId, folio)) {
			salAutoDao.deleteReg(salidaId, folio);
		}	
		
		return "redirect:/salida/solicitud/autos?SalidaId="+salidaId;
	}	
	
	@RequestMapping("/salida/solicitud/solicitud")
	public String salidaSolicitudSolicitud(HttpServletRequest request, Model modelo){
		//long time = System.currentTimeMillis(); 
		HttpSession sesion	= ((HttpServletRequest)request).getSession();
		String codigoPersonal 	= "0";
		String periodo 			= "-";
		
		if (sesion!=null){
			codigoPersonal 	= (String)sesion.getAttribute("codigoPersonal");
			periodo 		= (String)sesion.getAttribute("periodo");
		}
		
		boolean registroLlegada = true;
		//System.out.println("Tiempo 1:"+(System.currentTimeMillis()-time));				
		List<SalSolicitud> lisSolicitud 			= salSolicitudDao.lisUsuario(codigoPersonal, " ORDER BY ENOC.SAL_SOLICITUD.FECHA_SALIDA DESC");				
		List<SalSolicitud> lisAutorizadas 			= salSolicitudDao.lisAutorizadasPorUsuario(codigoPersonal, aca.util.Fecha.getHoy(), " ORDER BY ENOC.SAL_SOLICITUD.FECHA_SALIDA DESC");		
		List<SalGrupo> lisGrupo 					= salGrupoDao.getListUsuario(codigoPersonal, " ORDER BY GRUPO_ID");		
		List<Maestros> lisMaestros 					= maestrosDao.lisMaestros(" ORDER BY CODIGO_PERSONAL");		
		HashMap<String,String> mapaGrupoNombre 		= salSolicitudDao.mapNombreGrupo();		
		HashMap<String,String> mapaMaestros 		= maestrosDao.mapMaestroNombre("NOMBRE");
		HashMap<String,SalInforme> mapaInformes 	= salInformeDao.mapaInformes();		
		HashMap<String,String> mapAutorizadas 		= salSolicitudDao.mapAutorizadas();		
		HashMap<String,String> mapaAlumnos 			= salAlumnoDao.mapaPorSolicitud();		
		HashMap<String,String> mapaEmpleados		= salConsejeroDao.mapaPorSolicitud();		
		HashMap<String,String> mapaAutos			= salAutoDao.mapaPorSolicitud();
		
		for(SalSolicitud sol : lisAutorizadas) {
			if(mapAutorizadas.containsKey(sol.getSalidaId())){
				if (!mapaInformes.containsKey(sol.getSalidaId())){
					registroLlegada = false;
			    }
			}
		}
		
		modelo.addAttribute("CodigoPersonal", codigoPersonal);		
		modelo.addAttribute("Periodo", periodo);
		modelo.addAttribute("registroLlegada", registroLlegada);				
		modelo.addAttribute("lisSolicitud", lisSolicitud);
		modelo.addAttribute("lisGrupo", lisGrupo);
		modelo.addAttribute("lisMaestros", lisMaestros);
		
		modelo.addAttribute("mapaGrupoNombre", mapaGrupoNombre);
		modelo.addAttribute("mapaMaestros", mapaMaestros);
		modelo.addAttribute("mapaInformes", mapaInformes);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("mapaEmpleados", mapaEmpleados);
		modelo.addAttribute("mapaAutos", mapaAutos);
		
		return "salida/solicitud/solicitud";
	}
	
	@RequestMapping("/salida/solicitud/accion")
	public String salidaSolicitudAccion(HttpServletRequest request, Model modelo){		
				
		String salidaId				= request.getParameter("salida")==null?"0":request.getParameter("salida");		
		SalSolicitud salSolicitud  	= new SalSolicitud();
		if (salSolicitudDao.existeReg(salidaId)) {
			salSolicitud = salSolicitudDao.mapeaRegId(salidaId);
		}
		
		List<SalProposito> lisPropositos 	= salPropositoDao.lisTodos(" ORDER BY PROPOSITO_NOMBRE");
		List<SalGrupo> lisGrupos 			= salGrupoDao.getListAll(" ORDER BY 1");		
		List<Maestros> lisMaestros 			= maestrosDao.getListAll(" ORDER BY APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE");
		List<CatPais> lisPaises 			= catPaisDao.getListAll(" ORDER BY NOMBRE_PAIS");
		List<CatEstado> lisEstados 			= catEstadoDao.getLista(salSolicitud.getPaisId(), " ORDER BY NOMBRE_ESTADO");		
		
		modelo.addAttribute("salSolicitud", salSolicitud);
		modelo.addAttribute("lisPropositos", lisPropositos);
		modelo.addAttribute("lisGrupos", lisGrupos);
		modelo.addAttribute("lisMaestros", lisMaestros);
		modelo.addAttribute("lisPaises", lisPaises);
		modelo.addAttribute("lisEstados", lisEstados);
		
		return "salida/solicitud/accion";
	}
	
	@RequestMapping("/salida/solicitud/getEstados")
	@ResponseBody
	public String salidaSolicitudGetEstados(HttpServletRequest request){
		String paisId 					= request.getParameter("paisId")==null?"":request.getParameter("paisId");	
		String mensaje					= "";		
		List<CatEstado> lisEstados 		= catEstadoDao.getLista(paisId,"ORDER BY 1,3");	
		for(CatEstado edo: lisEstados){
			mensaje+= "<option value='"+edo.getEstadoId()+"'>"+ edo.getNombreEstado()+"</option>";
		}		
		return mensaje;
	}
	
	@RequestMapping("/salida/solicitud/grabar")
	public String salidaSolicitudGrabar(HttpServletRequest request, Model modelo){
		
		HttpSession sesion			= ((HttpServletRequest)request).getSession();
		String codigoPersonal 		= "0";		
		if (sesion!=null){
			codigoPersonal 			= (String)sesion.getAttribute("codigoPersonal");
		}
		String salidaId				= request.getParameter("salida")==null?"0":request.getParameter("salida");				
		String fechaHoy 			= aca.util.Fecha.getHoy();
		String mensaje 				= "-";
		
		SalSolicitud salSolicitud  	= new SalSolicitud();
		SalBitacora	salBitacora 	= new SalBitacora();
		if (salidaId.equals("0")){
			salidaId = salSolicitudDao.maximoReg();
			salSolicitud.setSalidaId(salidaId);
		}else{
			salSolicitud.setSalidaId(salidaId);	
		}		
	
		salSolicitud.setGrupoId(request.getParameter("Grupo")==null?"0":request.getParameter("Grupo"));
		salSolicitud.setFecha(fechaHoy);
		salSolicitud.setFechaSalida(request.getParameter("FechaSalida")+" "+request.getParameter("HoraSalida"));
		salSolicitud.setFechaLlegada(request.getParameter("FechaLlegada")+" "+request.getParameter("HoraLlegada"));
		salSolicitud.setAlimento(request.getParameter("Alimento"));
		salSolicitud.setPropositoId(request.getParameter("Proposito"));
		if (request.getParameter("OtroP") == null) {
			salSolicitud.setOtroProposito("-");
		} else {
			salSolicitud.setOtroProposito(request.getParameter("OtroP"));
		}
		salSolicitud.setComentario(request.getParameter("Comentario"));
		salSolicitud.setLugar(request.getParameter("Lugar"));
		salSolicitud.setLugarSalida(request.getParameter("LugarSalida"));
		salSolicitud.setDesayuno(request.getParameter("Desayuno"));
		salSolicitud.setComida(request.getParameter("Comida"));
		salSolicitud.setCena(request.getParameter("Cena"));
		salSolicitud.setTransporte(request.getParameter("Transporte"));
		salSolicitud.setHospedaje(request.getParameter("Hospedaje"));
		salSolicitud.setTotal(request.getParameter("Total"));
		salSolicitud.setTotalPersona(request.getParameter("TotalPersona"));
		salSolicitud.setUsuario(codigoPersonal);
		salSolicitud.setResponsable(request.getParameter("Responsable"));
		salSolicitud.setTelefono(request.getParameter("Telefono"));
		salSolicitud.setPaisId(request.getParameter("PaisId"));
		salSolicitud.setEstadoId(request.getParameter("EstadoId"));
		salSolicitud.setPermiso("N");

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		salBitacora.setSalidaId(salidaId);
		salBitacora.setEstado("1");
		salBitacora.setFecha(sdf.format(timestamp));
		
		if (salSolicitudDao.existeReg(salidaId) == false) {
			
			if (salSolicitudDao.insertReg(salSolicitud)) {
				if(salBitacoraDao.existeReg(salidaId,"1")) {
					salBitacora = salBitacoraDao.mapeaRegId(salidaId, "1");
					salBitacora.setFecha(sdf.format(timestamp));
					salBitacoraDao.updateReg(salBitacora);
				}else{
					salBitacoraDao.insertReg(salBitacora);
				}
				mensaje = "Grabado: ";
			} else {
				mensaje = "No Grabó: ";
			}
		} else {			
			if (salSolicitudDao.updateReg(salSolicitud)) {
				mensaje = "La solicitud  ha sido modificada";
			} else {
				mensaje = "No Cambió: ";
			}
		}	
		
		return "redirect:/salida/solicitud/accion?salida="+salidaId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/salida/solicitud/borrar")
	public String salidaSolicitudBorrar(HttpServletRequest request, Model modelo){	
		
		String salidaId		= request.getParameter("salida")==null?"0":request.getParameter("salida");
		String mensaje 		= "-"; 
		if (salSolicitudDao.existeReg(salidaId)) {
			if (salSolicitudDao.deleteReg(salidaId)) {
				mensaje = "¡Borrado!";
			}else {
				mensaje = "¡Error al borrar!";
			}
		}
		return "redirect:/salida/solicitud/solicitud?Mensaje="+mensaje;
	}
	
	@RequestMapping("/salida/solicitud/alumnos")
	public String salidaSolicitudAlumnos(HttpServletRequest request, Model modelo){
		String codigoPersonal 	= "0";		
		String codigoAlumno		= "0";
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");
			codigoAlumno		= (String) sesion.getAttribute("codigoAlumno");
		}
		
		String accion 		= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		String salidaId 	= request.getParameter("salida")==null?"0":request.getParameter("salida");
		String modalidad 	= request.getParameter("modalidad")==null?"0":request.getParameter("modalidad");
		String facultad 	= request.getParameter("facultad")==null?"000":request.getParameter("facultad");
		String carreraId 	= request.getParameter("carreraId")==null?"00000":request.getParameter("carreraId");
		String grado 		= request.getParameter("grado")==null?"0":request.getParameter("grado");
		String residencia 	= request.getParameter("residencia")==null?"0":request.getParameter("residencia");
		String dormitorio 	= request.getParameter("dormitorio")==null?"0":request.getParameter("dormitorio");
		
		List<SalAlumno> lisAlumno 	= salAlumnoDao.lisPorSalida(salidaId, "ORDER BY 2");	
		List<AlumEstado> lisFiltro	= new ArrayList<AlumEstado>();
		List<CatFacultad> listFacu 	= catFacultadDao.getListAll("ORDER BY FACULTAD_ID");
		List<CatCarrera> listCar 	= catCarreraDao.getLista(facultad, "ORDER BY FACULTAD_ID");
		List<SalClub> lisClubes 	= salClubDao.listResponsable(codigoPersonal, " ORDER BY GRUPO_ID");	

		HashMap<String, AlumPersonal> mapaInscritos = alumPersonalDao.getMapInscritos();
		HashMap<String, CatFacultad> mapaFacultad 	= catFacultadDao.getMapFacultad("");
		HashMap<String, CatCarrera> mapaCarrera 	= catCarreraDao.getMapAll("");
		
		String nombreAlumno = alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
		
		SalAlumno salAlumno = new SalAlumno();
		
		if (accion.equals("1")){
			lisFiltro = alumEstadoDao.filtroAlumnos(modalidad, facultad, carreraId, residencia, dormitorio, grado);
		}else if (accion.equals("2")){
			
			lisFiltro = alumEstadoDao.filtroAlumnos(modalidad, facultad, carreraId, residencia, dormitorio, grado);
			String checked = "";
			for (AlumEstado alumno : lisFiltro){
				checked = request.getParameter(alumno.getCodigoPersonal())==null?"N":"S";
				salAlumno.setCodigoPersonal(alumno.getCodigoPersonal());
				salAlumno.setSalidaId(salidaId);
				
				if (checked.equals("S")){				
					salAlumno.setFecha(aca.util.Fecha.getHoy());
					salAlumno.setUsuario(codigoPersonal);
					if (alumPersonalDao.existeReg(alumno.getCodigoPersonal()) && salAlumnoDao.existeReg(salidaId,alumno.getCodigoPersonal()) == false){
						salAlumnoDao.insertReg(salAlumno);
					}
				}else{
					if (salAlumnoDao.existeReg(salidaId,codigoPersonal) == true){
						salAlumnoDao.deleteReg(salAlumno);
					}
				}
			}
			
			// Actualizar la lista de alumnos		
			lisAlumno 	= salAlumnoDao.lisPorSalida(salidaId, "ORDER BY 2");		
		}
		
		modelo.addAttribute("codigoPersonal", codigoPersonal);
		modelo.addAttribute("codigoAlumno", codigoAlumno);
		modelo.addAttribute("salidaId", salidaId);
		modelo.addAttribute("modalidad", modalidad);
		modelo.addAttribute("facultad", facultad);
		modelo.addAttribute("carreraId", carreraId);
		modelo.addAttribute("grado", grado);
		modelo.addAttribute("residencia", residencia);
		modelo.addAttribute("residencia", residencia);
		modelo.addAttribute("dormitorio", dormitorio);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("lisAlumno", lisAlumno);
		modelo.addAttribute("listFacu", listFacu);
		modelo.addAttribute("listCar", listCar);
		modelo.addAttribute("lisFiltro", lisFiltro);
		modelo.addAttribute("lisClubes", lisClubes);
		modelo.addAttribute("mapaInscritos", mapaInscritos);
		modelo.addAttribute("mapaFacultad", mapaFacultad);
		modelo.addAttribute("mapaCarrera", mapaCarrera);
		
		return "salida/solicitud/alumnos";
	}
	
	@RequestMapping("/salida/solicitud/accionAlumno")
	public String salidaSolicitudAccionAlumno(HttpServletRequest request){
		
		String salidaId 		= request.getParameter("salida") == null ? "0" : request.getParameter("salida");
		String accion 			= request.getParameter("Accion") == null ? "0" : request.getParameter("Accion");
		String codigoPersonal 	= "0";
		String codigoAlumno		= request.getParameter("Alumno") == null ? "0" : request.getParameter("Alumno");
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");
		}
		
		SalAlumno salAlumno = new SalAlumno();
		
		if (accion.equals("2")){
		 // Grabar
			salAlumno.setCodigoPersonal(codigoAlumno);
			salAlumno.setSalidaId(salidaId);
			salAlumno.setFecha(aca.util.Fecha.getHoy());
			salAlumno.setUsuario(codigoPersonal);
			if (alumPersonalDao.existeReg(codigoAlumno) && salAlumnoDao.existeReg(salidaId,codigoAlumno) == false){
				salAlumnoDao.insertReg(salAlumno);
			}
		}else if (accion.equals("4")){
			// Borrar
			salAlumno.setCodigoPersonal(codigoAlumno);
			salAlumno.setSalidaId(salidaId);
			if (salAlumnoDao.existeReg(salidaId,codigoAlumno) == true){
				salAlumnoDao.deleteReg(salAlumno);
			}
		}
		
		return "redirect:/salida/solicitud/alumnos?salida="+salidaId;
	}
	
	/* @Async */
	@RequestMapping("/salida/solicitud/enviar")
	public String salidaSolicitudEnviar(HttpServletRequest request){
		
		String salidaId  	= request.getParameter("SalidaId")==null?"0":request.getParameter("SalidaId");
		boolean grabo		= false;			
		
		SalSolicitud salSolicitud = new SalSolicitud();
		salSolicitud = salSolicitudDao.mapeaRegId(salidaId);
		salSolicitud.setEstado("E");
		
		salSolicitudDao.updateReg(salSolicitud);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		SalBitacora salBitacora = new SalBitacora();
		if(salBitacoraDao.existeReg(salidaId, "2")) {
			salBitacora = salBitacoraDao.mapeaRegId(salidaId, "2");
			salBitacora.setFecha(sdf.format(timestamp));
			if (salBitacoraDao.updateReg(salBitacora)) {
				grabo = true;
			}
		}
		
		// Enviar los correos a las personas que deben preautorizar...
		if (grabo == true) {
			SalGrupo salGrupo 	= salGrupoDao.mapeaRegId(salSolicitud.getGrupoId());					
			String[]correos 	= salGrupo.getCorreo().split(",");				
			String asunto		= "VRE(solicitud de salida (folio "+salSolicitud.getSalidaId()+")";
			String datosSalida 	= "Has recibido este correo para notificarte que hay una solicitud de salida de grupo estudiantil que puedes pre autorizar si estás de acuerdo con dicha salida y si cumple con los requisitos institucionales."
								+ "\nLos datos registrados son los siguientes."
								+ "\n1. Fue generada por:"+maestrosDao.getNombreCorto(salSolicitud.getUsuario(), "NOMBRE")+"."
								+ "\n2. Lugar de la salida:"+salSolicitud.getLugar()+"."
								+ "\n3. Fecha y hora:"+salSolicitud.getFechaSalida()+"."
								+ "\n4. Responsable del grupo: "+maestrosDao.getNombreCorto(salSolicitud.getResponsable(), "NOMBRE")+"."
								+ "\nDeberás ingresar al sistema académico para realizar dicha preautorización. Recuerda que debe realizarse con la anticipación debida.\n"
								+ "\nPara dudas y apoyo al respecto comunícate a la Dirección de Actividades Complementarias a la Ext. 5003 o al correo ac@um.edu.mx";		
			try {
				for(String email : correos) {					
					email = email.trim();
					mailService.sendMesageSimple(email, asunto, datosSalida);
				}	
			} catch (Exception e) {
				System.out.println("Error en salidaSolicitudEnviar("+salGrupo.getCorreo()+"):"+e.getMessage());
			}			
		}		
		
		return "redirect:/salida/solicitud/solicitud";
	}

	@RequestMapping("/salida/solicitud/crearGrupo")
	public String salidaSolicitudCrearGrupo(HttpServletRequest request, Model modelo){
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		String codigoPersonal 	= "0";
		SalClub grupo 			= new SalClub();
		
		if (sesion!=null){
			codigoPersonal 		= (String)sesion.getAttribute("codigoPersonal");
		}
		
		String grupoId  		= request.getParameter("GrupoId")==null?"0":request.getParameter("GrupoId");
		
		if (salClubDao.existeReg(grupoId)) {
			grupo  = salClubDao.mapeaRegId(grupoId);
		}else {
			grupo.setResponsable(codigoPersonal);
		}			
		
		List<aca.salida.spring.SalClub> lisClubes 	= salClubDao.listResponsable(codigoPersonal, " ORDER BY GRUPO_ID");
		
		modelo.addAttribute("lisClubes", lisClubes);
		
		modelo.addAttribute("grupo", grupo);
		
		return "salida/solicitud/crearGrupo";
	}
	
	@RequestMapping("/salida/solicitud/grabarGrupo")
	public String salidaSolicitudGrabarGrupo(HttpServletRequest request, Model modelo){
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		String codigoPersonal 	= "0";		
		if (sesion!=null){
			codigoPersonal 	= (String)sesion.getAttribute("codigoPersonal");
		}
		
		String grupoId  	= request.getParameter("GrupoId")==null?"0":request.getParameter("GrupoId");		
		String grupoNombre	= request.getParameter("GrupoNombre")==null?"-":request.getParameter("GrupoNombre");
		String alumnos   	= request.getParameter("Alumnos")==null?"-":request.getParameter("Alumnos");
		if (grupoId.equals("0")) {
			grupoId = salClubDao.maximoReg();
		} 
		
		SalClub salClub = new SalClub();		
		salClub.setGrupoId(grupoId);
		salClub.setGrupoNombre(grupoNombre);
		salClub.setResponsable(codigoPersonal);
		salClub.setAlumnos(alumnos);
		if (salClubDao.existeReg(grupoId)) {
			salClubDao.updateReg(salClub);
		}else {
			salClubDao.insertReg(salClub);
		}
		
		return "redirect:/salida/solicitud/crearGrupo";
	}
	
	@RequestMapping("/salida/solicitud/borrarGrupo")
	public String salidaSolicitudBorrarGrupo(HttpServletRequest request, Model modelo){
		
					
		String grupoId  	= request.getParameter("GrupoId")==null?"0":request.getParameter("GrupoId");	
		if (salClubDao.existeReg(grupoId)) {
			salClubDao.deleteReg(grupoId);
		}
		
		return "redirect:/salida/solicitud/crearGrupo";
	}
	
	@RequestMapping("/salida/solicitud/grabarAlumnos")
	public String salidaSolicitudGrabarAlumnos(HttpServletRequest request, Model modelo){
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		String codigoPersonal 	= "0";		
		if (sesion!=null){
			codigoPersonal 	= (String)sesion.getAttribute("codigoPersonal");
		}		
		
		String salidaId  	= request.getParameter("salida")==null?"0":request.getParameter("salida");
		String grupoId  	= request.getParameter("GrupoId")==null?"0":request.getParameter("GrupoId");
		String alumnos 		= "-";
		if (salClubDao.existeReg(grupoId) ) {
			if (salClubDao.getAlumnos(grupoId).split(",").length > 0) {
				alumnos = salClubDao.getAlumnos(grupoId);
				String[] arrAlumnos = alumnos.split(",");
				for(String  alumno : arrAlumnos) {
					SalAlumno salAlumno = new SalAlumno();
					salAlumno.setSalidaId(salidaId);
					salAlumno.setUsuario(codigoPersonal);
					salAlumno.setFecha(aca.util.Fecha.getHoy());			
					salAlumno.setCodigoPersonal(alumno);
					
					if(alumPersonalDao.existeAlumno(alumno)){
						if (alumPersonalDao.existeReg(alumno) && !salAlumnoDao.existeReg(salidaId, alumno)) {
							salAlumnoDao.insertReg(salAlumno);
						}					
					}
				}
			}			
		}
		
		return "redirect:/salida/solicitud/alumnos?salida="+salidaId;
	}
	
	@RequestMapping("/salida/solicitud/borrarAlumnos")
	public String salidaSolicitudBorrarAlumnos(HttpServletRequest request, Model modelo){		
					
		String salidaId  	= request.getParameter("salida")==null?"0":request.getParameter("salida");
		
		if (salAlumnoDao.tieneAlumnos(salidaId)) {
			salAlumnoDao.deleteAlumnos(salidaId);
		}
		
		return "redirect:/salida/solicitud/alumnos?salida="+salidaId;
	}
	
	@RequestMapping("/salida/solicitud/consejero")
	public String salidaSolicitudConsejero(HttpServletRequest request, Model modelo){		
		
		String salidaId  			= request.getParameter("salida")==null?"0":request.getParameter("salida");
		String codigoEmpleado 		= "0";
		String nombreUsuario		= "-";
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();				
		if (sesion!=null){
			codigoEmpleado 			= (String)sesion.getAttribute("codigoEmpleado");
			nombreUsuario			= usuariosDao.getNombreUsuario(codigoEmpleado, "NOMBRE");
		}
		
		List<SalConsejero> lisConsejeros	= salConsejeroDao.getListAll(salidaId," ORDER BY FOLIO");
		
		modelo.addAttribute("lisConsejeros", lisConsejeros);
		modelo.addAttribute("nombreUsuario", nombreUsuario);
		
		return "salida/solicitud/consejero";
	}
	
	@RequestMapping("/salida/solicitud/grabarConsejero")
	public String salidaSolicitudGrabarConsejero(HttpServletRequest request, Model modelo){		
		
		String salidaId  			= request.getParameter("salida")==null?"0":request.getParameter("salida");
		String folio  				= salConsejeroDao.maximoReg(salidaId);
		String codigoEmpleado 		= "0";
		String nombreEmpleado		= "-";
		String trabajoEmpleado 		= "";
		
		HttpSession sesion			= ((HttpServletRequest)request).getSession();				
		if (sesion!=null){
			codigoEmpleado 			= (String)sesion.getAttribute("codigoEmpleado");
			nombreEmpleado			= usuariosDao.getNombreUsuario(codigoEmpleado, "NOMBRE");
			trabajoEmpleado			= empDatosDao.getNombreDepartamento(codigoEmpleado);
		}
		
		SalConsejero salConsejero = new SalConsejero();
		salConsejero.setSalidaId(salidaId);
		salConsejero.setFolio(folio);
		salConsejero.setConsejero(nombreEmpleado); 
		salConsejero.setTrabajo(trabajoEmpleado);
		salConsejero.setClave(codigoEmpleado); 

		if (!salConsejeroDao.existeReg(salidaId,folio) ) {
			salConsejeroDao.insertReg(salConsejero);		
		}

		return "redirect:/salida/solicitud/consejero?salida="+salidaId;
	}
	
	@RequestMapping("/salida/solicitud/borrarConsejero")
	public String salidaSolicitudBorrarConsejero(HttpServletRequest request, Model modelo){		
		
		String salidaId  		= request.getParameter("Salida")==null?"0":request.getParameter("Salida");
		String folio  			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");	 

		if (salConsejeroDao.existeReg(salidaId,folio) ) {
			salConsejeroDao.deleteReg(salidaId, folio);		
		}

		return "redirect:/salida/solicitud/consejero?salida="+salidaId;
	}
	
	@RequestMapping("/salida/solicitud/invitados")
	public String salidaSolicitudInvitados(HttpServletRequest request, Model modelo){		
		
		String salidaId  				= request.getParameter("salida")==null?"0":request.getParameter("salida");
		
		List<SalInvitado> lisInvitados 	= salInvitadoDao.getListAll(salidaId, "");
		
		modelo.addAttribute("lisInvitados", lisInvitados);
		modelo.addAttribute("salidaId", salidaId);
		
		return "salida/solicitud/invitados";
	}
	
	@RequestMapping("/salida/solicitud/grabarInvitado")
	public String salidaSolicitudGrabarInvitado(HttpServletRequest request, Model modelo){
		
		String salidaId  	= request.getParameter("Salida")==null?"0":request.getParameter("Salida");
		String nombre  		= request.getParameter("Nombre")==null?"-":request.getParameter("Nombre");
		String tipo  		= request.getParameter("Tipo")==null?"I":request.getParameter("Tipo");
		String folio  		= salInvitadoDao.getFolio(salidaId);
		
		SalInvitado invitado = new SalInvitado();
		invitado.setSalidaId(salidaId);
		invitado.setNombre(nombre);
		invitado.setFolio(folio);
		invitado.setTipo(tipo);

		if (!salInvitadoDao.existeReg(salidaId,folio) ) {
			salInvitadoDao.insertReg(invitado);		
		}
		
		return "redirect:/salida/solicitud/invitados?salida="+salidaId;
	}
	
	@RequestMapping("/salida/solicitud/borrarInvitado")
	public String salidaSolicitudBorrarInvitado(HttpServletRequest request, Model modelo){
		
		String salidaId  = request.getParameter("Salida")==null?"0":request.getParameter("Salida");
		String folio  	 = request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		
		SalInvitado invitado = salInvitadoDao.mapeaRegId(salidaId, folio);
		
		if (salInvitadoDao.existeReg(salidaId,folio) ) {
			salInvitadoDao.deleteReg(invitado);
		}
		
		return "redirect:/salida/solicitud/invitados?salida="+salidaId;
	}
	
	@RequestMapping("/salida/solicitud/editarInforme")
	public String salidaSolicitudEditarInforme(HttpServletRequest request, Model modelo){		
		
		String salidaId  				= request.getParameter("SalidaId")==null?"0":request.getParameter("SalidaId");
		SalInforme salInforme 			= new SalInforme();
		boolean existe					= false;
		
		if (salInformeDao.existeReg(salidaId)) {
			salInforme 	= salInformeDao.mapeaRegId(salidaId);
			existe 		= true;
		}
		
		modelo.addAttribute("salidaId", salidaId);
		modelo.addAttribute("existe", existe);
		modelo.addAttribute("salInforme", salInforme);
		
		return "salida/solicitud/editarInforme";
	}
	
	@RequestMapping("/salida/solicitud/grabarInforme")
	public String salidaSolicitudGrabarInforme(HttpServletRequest request, Model modelo){
		
		String salidaId  		= request.getParameter("SalidaId")==null?"0":request.getParameter("SalidaId");
		String incidentes		= request.getParameter("Incidentes")==null?"-":request.getParameter("Incidentes");
		String observaciones	= request.getParameter("Observaciones")==null?"-":request.getParameter("Observaciones");
		String usuario			= "0";
		String mensaje 			= "-";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();				
		if (sesion!=null){
			usuario 	= (String)sesion.getAttribute("codigoPersonal");
		}
		
		SalInforme informe = new SalInforme();
		informe.setSalidaId(salidaId);
		informe.setIncidentes(incidentes);
		informe.setObservaciones(observaciones);
		informe.setUsuario(usuario);
		if (salInformeDao.existeReg(salidaId)==false) {
			if (salInformeDao.insertReg(informe)) {
				mensaje = "¡ Se grabó el registro !";
			}else {
				mensaje = "¡ Error al intentar grabar !";
			}		
		}else {
			if (salInformeDao.updateReg(informe)){
				mensaje = "¡ Se grabó el registro !";
			}else {
				mensaje = "¡ Error al intentar grabar !";
			}
		}
		
		return "redirect:/salida/solicitud/editarInforme?SalidaId="+salidaId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/salida/solicitud/accionConsejero")
	public String salidaSolicitudAccionConsejero(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContSalidaSolicitud|salidaSolicitudAccionConsejero:");
		return "salida/solicitud/accionConsejero";
	}	
	
}