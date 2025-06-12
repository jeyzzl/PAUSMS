package aca.web.admlinea;

import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.commons.math3.geometry.spherical.oned.S1Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import aca.admision.spring.AdmArchivoDao;
import aca.admision.spring.AdmAsesor;
import aca.admision.spring.AdmAsesorDao;
import aca.admision.spring.AdmBanco;
import aca.admision.spring.AdmBancoDao;
import aca.admision.spring.AdmCarta;
import aca.admision.spring.AdmCartaDao;
import aca.admision.spring.AdmCartaFulton;
import aca.admision.spring.AdmCartaFultonDao;
import aca.admision.spring.AdmCartaPau;
import aca.admision.spring.AdmCartaPauDao;
import aca.admision.spring.AdmCartaSonoma;
import aca.admision.spring.AdmCartaSonomaDao;
import aca.admision.spring.AdmCartaSubir;
import aca.admision.spring.AdmCartaSubirDao;
import aca.admision.spring.AdmComentario;
import aca.admision.spring.AdmComentarioDao;
import aca.admision.spring.AdmDirecto;
import aca.admision.spring.AdmDirectoDao;
import aca.admision.spring.AdmDocAlum;
import aca.admision.spring.AdmDocAlumDao;
import aca.admision.spring.AdmDocumento;
import aca.admision.spring.AdmImagenDao;
import aca.admision.spring.AdmIngresoDao;
import aca.admision.spring.AdmPadres;
import aca.admision.spring.AdmPadresDao;
import aca.admision.spring.AdmPasos;
import aca.admision.spring.AdmPasosDao;
import aca.admision.spring.AdmProceso;
import aca.admision.spring.AdmProcesoDao;
import aca.admision.spring.AdmPrueba;
import aca.admision.spring.AdmPruebaAlumno;
import aca.admision.spring.AdmPruebaAlumnoDao;
import aca.admision.spring.AdmPruebaDao;
import aca.admision.spring.AdmRecomienda;
import aca.admision.spring.AdmRecomiendaDao;
import aca.admision.spring.AdmRequisito;
import aca.admision.spring.AdmRequisitoDao;
import aca.admision.spring.AdmReservada;
import aca.admision.spring.AdmReservadaDao;
import aca.admision.spring.AdmSalud;
import aca.admision.spring.AdmSaludDao;
import aca.admision.spring.AdmSolicitud;
import aca.admision.spring.AdmEventoDao;
import aca.admision.spring.AdmFormato;
import aca.admision.spring.AdmFormatoDao;
import aca.admision.spring.AdmEvento;
import aca.admision.spring.AdmDocumentoDao;
import aca.admision.spring.AdmEncuesta;
import aca.admision.spring.AdmEncuestaDao;
import aca.admision.spring.AdmEstudio;
import aca.admision.spring.AdmEstudioDao;
import aca.admision.spring.AdmEvaluacion;
import aca.admision.spring.AdmEvaluacionDao;
import aca.admision.spring.AdmEvaluacionNota;
import aca.admision.spring.AdmEvaluacionNotaDao;
import aca.admision.spring.AdmImagen;
import aca.admision.spring.AdmSolicitudDao;
import aca.admision.spring.AdmTutor;
import aca.admision.spring.AdmTutorDao;
import aca.admision.spring.AdmUbicacion;
import aca.admision.spring.AdmUbicacionDao;
import aca.admision.spring.AdmUsuario;
import aca.admision.spring.AdmUsuarioDao;
import aca.alumno.spring.AlumAcademico;
import aca.alumno.spring.AlumAcademicoDao;
import aca.alumno.spring.AlumBanco;
import aca.alumno.spring.AlumBancoDao;
import aca.alumno.spring.AlumEstado;
import aca.alumno.spring.AlumEstadoDao;
import aca.alumno.spring.AlumEstudio;
import aca.alumno.spring.AlumEstudioDao;
import aca.admision.spring.AdmPago;
import aca.admision.spring.AdmPagoDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanDao;
import aca.alumno.spring.AlumReferencia;
import aca.alumno.spring.AlumReferenciaDao;
import aca.alumno.spring.AlumUbicacion;
import aca.alumno.spring.AlumUbicacionDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatCiudad;
import aca.catalogo.spring.CatCiudadDao;
import aca.catalogo.spring.CatCulturalDao;
import aca.catalogo.spring.CatEstado;
import aca.catalogo.spring.CatEstadoDao;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatModalidad;
import aca.catalogo.spring.CatModalidadDao;
import aca.catalogo.spring.CatNivelDao;
import aca.catalogo.spring.CatPais;
import aca.catalogo.spring.CatPaisDao;
import aca.catalogo.spring.CatPeriodoDao;
import aca.catalogo.spring.CatRegionDao;
import aca.catalogo.spring.CatReligion;
import aca.catalogo.spring.CatReligionDao;
import aca.emp.spring.EmpleadoDao;
import aca.internado.spring.IntAlumno;
import aca.log.spring.LogOperacion;
import aca.log.spring.LogOperacionDao;
import aca.mail.MailService;
import aca.mensaje.mensaje;
import aca.parametros.spring.Parametros;
import aca.parametros.spring.ParametrosDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.MaestrosDao;
import aca.vista.spring.UsuariosDao;
import aca.podium.spring.Aspirante;
import aca.podium.spring.AspiranteDao;
import aca.podium.spring.ExaArea;
import aca.podium.spring.ExaAreaDao;
import aca.podium.spring.Examen;
import aca.podium.spring.ExamenDao;
import aca.util.Encriptar;
import aca.podium.spring.ExamenAreaDao;
import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.admision.spring.AdmAcademico;
import aca.admision.spring.AdmAcademicoDao;
import aca.admision.spring.AdmAccesoVoboDao;
import aca.admision.spring.AdmAcomodo;
import aca.admision.spring.AdmAcomodoDao;
import aca.admision.spring.AdmArchivo;

@Controller
public class ContAmdlineaAdmision {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	private AdmImagenDao admImagenDao;

	@Autowired
	private AdmBancoDao admBancoDao;
	
	@Autowired
	private AdmArchivoDao admArchivoDao;	
	
	@Autowired
	private AdmDocumentoDao admDocumentoDao;
	
	@Autowired
	private AdmEventoDao admEventoDao;	

	@Autowired
	private AdmReservadaDao admReservadaDao;	
	
	@Autowired
	private AdmSolicitudDao admSolicitudDao;

	@Autowired
	private AlumBancoDao alumBancoDao;
	
	@Autowired
	private AdmAcademicoDao admAcademicoDao;
	
	@Autowired
	private AdmDocAlumDao admDocAlumDao;
	
	@Autowired
	private AdmProcesoDao admProcesoDao;
	
	@Autowired
	private CatCarreraDao catCarreraDao;
	
	@Autowired
	private CatNivelDao catNivelDao;
	
	@Autowired
	private CatModalidadDao catModalidadDao;
	
	@Autowired
	private AdmRequisitoDao admRequisitoDao;
	
	@Autowired
	private AdmFormatoDao admFormatoDao;
	
	@Autowired
	private MaestrosDao maestrosDao;
	
	@Autowired
	private CatPaisDao catPaisDao;
	
	@Autowired
	private EmpleadoDao empleadoDao;
	
	@Autowired
	private AccesoDao accesoDao;
	
	@Autowired
	private AdmAsesorDao admAsesorDao;
	
	@Autowired
	private AdmAccesoVoboDao admAccesoVoboDao;
	
	@Autowired
	private UsuariosDao usuariosDao;
	
	@Autowired
	private AdmEncuestaDao admEncuestaDao;

	@Autowired
	private AdmPasosDao admPasosDao;
	
	@Autowired
	private AdmEvaluacionDao admEvaluacionDao;
	
	@Autowired
	private AdmEvaluacionNotaDao admEvaluacionNotaDao;
	
	@Autowired
	private AdmComentarioDao admComentarioDao;
	
	@Autowired
	private AdmCartaDao admCartaDao;
	
	@Autowired
	private CatFacultadDao catFacultadDao;
	
	@Autowired
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired
	private LogOperacionDao logOperacionDao;
	
	@Autowired
	private AdmEstudioDao admEstudioDao;
	
	@Autowired
	private AdmPadresDao admPadresDao;
	
	@Autowired
	private AdmSaludDao admSaludDao;
	
	@Autowired
	private AdmTutorDao admTutorDao;
	
	@Autowired
	private AdmUbicacionDao admUbicacionDao;
	
	@Autowired
	private AdmRecomiendaDao admRecomiendaDao;
	
	@Autowired
	private AlumUbicacionDao alumUbicacionDao;
	
	@Autowired
	private AdmDirectoDao admDirectoDao;
	
	@Autowired
	private MapaPlanDao mapaPlanDao;
	
	@Autowired
	private ExamenDao examenDao;
	
	@Autowired
	private ExamenAreaDao examenAreaDao;
	
	@Autowired
	private ExaAreaDao exaAreaDao;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private AspiranteDao aspiranteDao;

	@Autowired
	private AlumReferenciaDao alumReferenciaDao;
	
	@Autowired
	private ParametrosDao parametrosDao;
	
	@Autowired
	private AdmDocAlumDao docAlumDao;
	
	@Autowired
	CatEstadoDao catEstadoDao;
	
	@Autowired
	CatCiudadDao catCiudadDao;
	
	@Autowired
	CatReligionDao catReligionDao;
	
	@Autowired
	AdmCartaSubirDao admCartaSubirDao;
	
	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;	
	
	@Autowired
	AlumAcademicoDao alumAcademicoDao;
	
	@Autowired
	AdmPruebaDao admPruebaDao;
	
	@Autowired
	AdmPruebaAlumnoDao admPruebaAlumnoDao;
	
	@Autowired
	AdmUsuarioDao admUsuarioDao;
	
	@Autowired
	AdmPagoDao admPagoDao;
	
	@Autowired
	AlumEstudioDao alumEstudioDao;
	
	@Autowired
	AlumPlanDao alumPlanDao;
	
	@Autowired
	AlumEstadoDao alumEstadoDao;

	@Autowired
	AdmAcomodoDao admAcomodoDao;

	@Autowired
	CatCulturalDao catCulturalDao;

	@Autowired
	CatRegionDao catRegionDao;

	@Autowired
	ParametrosDao acaParametrosDao;

	@Autowired
	AdmCartaPauDao admCartaPauDao;

	@Autowired
	AdmCartaSonomaDao admCartaSonomaDao;

	@Autowired
	AdmCartaFultonDao admCartaFultonDao;

	@Autowired
	AdmIngresoDao admIngresoDao;

	@Autowired
	CatPeriodoDao catPeriodoDao;
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/admlinea/admision/admitido")
	public String admlineaAdmisionAdmitido(HttpServletRequest request, Model modelo){
		
		String folio 				= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String accion				= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		String fecha				= request.getParameter("Fecha")==null?"0":request.getParameter("Fecha");
		AdmSolicitud admSolicitud 	= admSolicitudDao.mapeaRegId(folio);
		AdmAcademico admAcademico 	= admAcademicoDao.mapeaRegId(folio);		
		AdmProceso admProceso 		= admProcesoDao.mapeaRegId( folio);
		boolean bajarPdf 			= false;
		boolean vistoBueno			=false;
		String facultadId	 		= catCarreraDao.getFacultadId(admAcademico.getCarreraId());
		String facultadNombre	 	= catFacultadDao.getNombreFacultad(facultadId);
		String carreraNombre 		= catCarreraDao.getNombreCarrera(admAcademico.getCarreraId());
		String numCondiciones	 	= admCartaDao.getNumCondiciones(folio);
		String codigoPersonal		= "0";
		boolean esAsesor			= false;
		AdmPasos admPasos 			= new AdmPasos();
		Acceso acceso				= new Acceso();
		HttpSession sesion		= ((HttpServletRequest)request).getSession();		
		if (sesion != null){
			codigoPersonal 		= sesion.getAttribute("codigoPersonal").toString();
			acceso				= accesoDao.mapeaRegId(codigoPersonal);
		}	
		
		boolean nivelPosgrado = false;
		if(admAcademico.getNivelId().equals("4") || admAcademico.getNivelId().equals("3") || admAcademico.getNivelId().equals("6")){
			nivelPosgrado = true;
		}
		
		if(accion.equals("1")){
			admSolicitud = admSolicitudDao.mapeaRegId(folio);
			admProcesoDao.updateFecha(5, folio);
			admSolicitud.setFechaIngreso(fecha);
			admSolicitud.setEstado("5");
			admSolicitud.setFecha(aca.util.Fecha.getHoy());
			admSolicitudDao.updateReg(admSolicitud);
			
			
			
		}	
			
		if (!admSolicitud.getFechaIngreso().equals("-") &&  admSolicitud.getEstado().equals("5")){
			bajarPdf = true;
		}
		
		if (admAsesorDao.existeReg(codigoPersonal)){
			esAsesor 		= true;
		}
		
		if (admPasosDao.existeReg(folio,"2")){
			vistoBueno 		= true;
		}
		
		boolean voboPosgrado = false;
		if(admPasosDao.existeReg(folio, "5")){
			voboPosgrado = true;
		}
		
		List<AdmDocAlum> lisDocumentos				= admDocAlumDao.lisPorFolio( folio, " AND CARTA= 'S' ORDER BY DOCUMENTO_ID");
		List<String> lisFechas	 					= admSolicitudDao.lisFechas("ORDER BY FECHA");
		HashMap<String,AdmDocumento> mapaDocumentos	= admDocumentoDao.mapaDocumentos();
		HashMap<String,AdmRequisito> mapaRequisitosPorCarrera = admRequisitoDao.mapaRequisitosPorCarrera(admAcademico.getCarreraId());
		
		modelo.addAttribute("vistoBueno", vistoBueno);
		modelo.addAttribute("voboPosgrado", voboPosgrado);
		modelo.addAttribute("nivelPosgrado", nivelPosgrado);
		modelo.addAttribute("admPasos", admPasos);
		modelo.addAttribute("esAsesor", esAsesor);
		modelo.addAttribute("esAdmin", acceso.getAdministrador().equals("S")?true:false);
		modelo.addAttribute("facultadNombre", facultadNombre);
		modelo.addAttribute("carreraNombre", carreraNombre);
		modelo.addAttribute("numCondiciones", numCondiciones);
		modelo.addAttribute("admSolicitud", admSolicitud);
		modelo.addAttribute("admAcademico", admAcademico);
		modelo.addAttribute("admProceso", admProceso);		
		modelo.addAttribute("bajarPdf", bajarPdf);		
		modelo.addAttribute("lisDocumentos", lisDocumentos);
		modelo.addAttribute("lisFechas", lisFechas);
		modelo.addAttribute("mapaDocumentos", mapaDocumentos);
		modelo.addAttribute("mapaRequisitosPorCarrera", mapaRequisitosPorCarrera);
		
		return "admlinea/admision/admitido";
	}
	
	@RequestMapping("/admlinea/admision/admitidoPDF")
	public String admlineaAdmisionAdmitidoPDF(HttpServletRequest request, Model modelo){	
		
		String folio 				= request.getParameter("Folio") == null ? "0" : request.getParameter("Folio");	
		
		AdmSolicitud admSolicitud 	= admSolicitudDao.mapeaRegId(folio);
		AdmAcademico admAcademico 	= admAcademicoDao.mapeaRegId(folio);		
		AdmProceso admProceso 		= admProcesoDao.mapeaRegId( folio);
		String facultadId	 		= catCarreraDao.getFacultadId(admAcademico.getCarreraId());
		String facultadNombre	 	= catFacultadDao.getNombreFacultad(facultadId);
		String carreraNombre 		= catCarreraDao.getNombreCarrera(admAcademico.getCarreraId());
		
		List<AdmCarta> lisCondiciones 		= admCartaDao.lisPorFolio(folio, " ORDER BY FOLIO");				
		List<AdmDocAlum> lisDocumentos		= admDocAlumDao.lisPorFolio( folio, " AND CARTA= 'S' ORDER BY DOCUMENTO_ID");
		List<AdmEvaluacion> lisEvaluaciones	= admEvaluacionDao.getListAll(" ORDER BY EVALUACION_ID");
		
		HashMap<String,String> mapaNotas						= admEvaluacionNotaDao.mapaNotaResultadosExamenes();
		HashMap<String,AdmDocumento> mapaDocumentos				= admDocumentoDao.mapaDocumentos();
		HashMap<String,AdmRequisito> mapaRequisitosPorCarrera 	= admRequisitoDao.mapaRequisitosPorCarrera(admAcademico.getCarreraId());
		
		modelo.addAttribute("facultadNombre", facultadNombre);
		modelo.addAttribute("carreraNombre", carreraNombre);
		modelo.addAttribute("admSolicitud", admSolicitud);
		modelo.addAttribute("admAcademico", admAcademico);
		modelo.addAttribute("admProceso", admProceso);
		
		modelo.addAttribute("lisEvaluaciones", lisEvaluaciones);
		modelo.addAttribute("lisCondiciones", lisCondiciones);
		modelo.addAttribute("lisDocumentos", lisDocumentos);
		modelo.addAttribute("mapaNotas", mapaNotas);
		modelo.addAttribute("mapaDocumentos", mapaDocumentos);
		modelo.addAttribute("mapaRequisitosPorCarrera", mapaRequisitosPorCarrera);
		
		return "admlinea/admision/admitidoPDF";
	}
	
	@RequestMapping("/admlinea/admision/enviarCarta")
	public String admlineaAdmisionCarta(HttpServletRequest request, Model modelo){	
		
		String folio 				= request.getParameter("Folio") == null ? "0" : request.getParameter("Folio");	
		AdmSolicitud admSolicitud 	= admSolicitudDao.mapeaRegId(folio);
		
		String para 		= admSolicitud.getEmail();
		String asunto 		= "Carta Aceptación UM";
		String mensaje 		= "Tu carta de aceptación ya está disponible, puedes descargarla en la ultima sección de tu portal de admisiones con el título, Carta de admisión";	
		try{
			//mailService.sendMesageSimple(para, asunto, mensaje);
			admSolicitudDao.updateCarta(folio, "S");
		}catch(Exception ex){			
	    	System.out.println("Error: admLinea.admision.admitido.enviarCorreo|"+ex);
	    }
		
		return "redirect:/admlinea/admision/admitido?Folio="+folio;
	}
	
	@RequestMapping("/admlinea/admision/admitir")
	public String admlineaAdmisionAdmitir(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContAdmlineaAdmision|admlineaAdmisionAdmitir");
		return "admlinea/admision/admitir";
	}
	
	@RequestMapping("/admlinea/admision/avance")
	public String admlineaAdmisionAvance(HttpServletRequest request, Model modelo){
		
		String folio 	= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		
		AdmSolicitud admSolicitud 	= new AdmSolicitud();
		Acceso acceso 				= new Acceso();
		String usuario 				= "";
		boolean existePersonal 		= false;
		boolean existeUbicacion		= false;
		boolean existeAcademico 	= false;
		boolean existeBanco		 	= false;
		String alumnoNombre			= "-";
		String matriculaAsignada 	= "-";	
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if(sesion != null){
			usuario 			= (String)sesion.getAttribute("codigoPersonal");	
			acceso 				= accesoDao.mapeaRegId(usuario);
		}

		if(admSolicitudDao.existeReg(folio)) {
			admSolicitud = admSolicitudDao.mapeaRegId(folio);
			matriculaAsignada 	= admSolicitud.getMatricula();
			if (alumPersonalDao.existeReg(matriculaAsignada)) {
				existePersonal 	= true;
				alumnoNombre 	= alumPersonalDao.getNombreAlumno(matriculaAsignada, "NOMBRE"); 
			}
			if (alumUbicacionDao.existeReg(matriculaAsignada)) {
				existeUbicacion = true;
			}
			if (alumAcademicoDao.existeReg(matriculaAsignada)) {
				existeAcademico = true;
			}
			if(alumBancoDao.existeReg(matriculaAsignada)){
				existeBanco = true;
			}
		}

		Parametros parametros = parametrosDao.mapeaRegId("1");

		String tipo = "PAU";
		if(parametros.getInstitucion().equals("Sonoma")){
			tipo = "SONOMA";
		}
		if(parametros.getInstitucion().equals("Fulton")){
			tipo = "FAC";
		}

		String siguienteMatricula = alumPersonalDao.maximoReg(tipo);
		
		String apellidoMaterno =  admSolicitud.getApellidoMaterno()==null||admSolicitud.getApellidoMaterno().trim().length()==0 ? "" : admSolicitud.getApellidoMaterno();
		List<AlumPersonal> lisAlumnoDuplicados = alumPersonalDao.BuscaDuplicados(admSolicitud.getNombre(), admSolicitud.getApellidoPaterno(), apellidoMaterno, 70);
		
		modelo.addAttribute("siguienteMatricula",siguienteMatricula);
		modelo.addAttribute("alumnoNombre",alumnoNombre);
		modelo.addAttribute("existePersonal",existePersonal);
		modelo.addAttribute("existeUbicacion",existeUbicacion);
		modelo.addAttribute("existeAcademico",existeAcademico);
		modelo.addAttribute("existeBanco",existeBanco);
		modelo.addAttribute("admSolicitud",admSolicitud);
		modelo.addAttribute("lisAlumnoDuplicados",lisAlumnoDuplicados);
		modelo.addAttribute("acceso", acceso);
		
		return "admlinea/admision/avance";
	}	
	
	@RequestMapping("/admlinea/admision/avanceAsignar")
	public String admlineaAdmisionAvanceAsignar(HttpServletRequest request, Model modelo){
		String folio 		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String matricula 	= request.getParameter("Matricula")==null?"-":request.getParameter("Matricula");
		String usuarioId 	= admSolicitudDao.getUsuarioId(folio); 
		String mensaje		= "-";
		
		AdmSolicitud admSolicitud = new AdmSolicitud();
		AdmProceso admProceso = new AdmProceso();		
		if(admSolicitudDao.existeReg(folio)) {
			admSolicitud = admSolicitudDao.mapeaRegId(folio);
		}		
		admSolicitud.setEstado("4");
		admSolicitud.setMatricula(matricula);
		admSolicitud.setFecha(aca.util.Fecha.getHoy());			
 		if(admSolicitudDao.updateReg(admSolicitud) && admUsuarioDao.updateMatricula(usuarioId, matricula)){
 			admProceso.setFolio(folio);			
 			admProceso.setFechaAdmision(aca.util.Fecha.getHoy());			
 			if(admProcesoDao.updateFecha(4, folio)){
 				mensaje = "Student ID:"+ matricula + " assigned to Applicant with Process ID: "+folio;
 			}
 		}		
		return "redirect:/admlinea/admision/avance?Folio="+folio+"&Mensaje="+mensaje;
	}	
	
	@RequestMapping("/admlinea/admision/bajar")
	public String admlineaAdmisionBajar(HttpServletRequest request, HttpServletResponse response){
		
		aca.admision.spring.AdmArchivo archivo = new aca.admision.spring.AdmArchivo();
		String folio 			= request.getParameter("folio")==null?"0":request.getParameter("folio"); 
		String documentoId		= request.getParameter("documentoId")==null?"0":request.getParameter("documentoId");	
		try {						
			if(admArchivoDao.existeReg(folio, documentoId)){
				archivo = admArchivoDao.mapeaRegId(folio, documentoId);				
				OutputStream out = response.getOutputStream();
				response.setHeader("Content-Disposition","attachment; filename=\""+ archivo.getNombre() + "\"");
				out.write(archivo.getArchivo());			
			}
		}catch(Exception ex){
			System.out.println("Error:documentosArchivo:"+ex);
		}		
		return "admlinea/admision/bajar";
	}
	
	@RequestMapping("/admlinea/admision/borrararchivo")
	public String admlineaAdmisionBorrarArchivo(HttpServletRequest request){		
		return "admlinea/admision/borrararchivo";
	}
	
	@RequestMapping("/admlinea/admision/sinSolicitud")
	public String admlineaAdmisionSinSolicitud(HttpServletRequest request, Model modelo){
		
		List<AdmUsuario> lisUsuarios = admUsuarioDao.lisSinSolicitud("ORDER BY APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE");
		
		modelo.addAttribute("lisUsuarios",lisUsuarios);
		
		return "admlinea/admision/sinSolicitud";
	}
	
	@RequestMapping("/admlinea/admision/cambia")
	public String admlineaAdmisionCambia(HttpServletRequest request, Model modelo){
		
		String folio		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String usuarioId 	= admSolicitudDao.getUsuarioId(folio);
		
		String nombre 		= admUsuarioDao.getNombreCorto(usuarioId);
		String usuario 		= admUsuarioDao.getCuenta(usuarioId);		
		
		modelo.addAttribute("folio",folio);
		modelo.addAttribute("usuarioId",usuarioId);
		modelo.addAttribute("nombre",nombre);
		modelo.addAttribute("usuario",usuario);
		
		return "admlinea/admision/cambia";
	}
	
	@RequestMapping("/admlinea/admision/cambiaPass")
	public String admlineaAdmisionCambiaPass(HttpServletRequest request, Model modelo){
		
		String usuarioId		= request.getParameter("UsuarioId")==null?"0":request.getParameter("UsuarioId");					
		AdmUsuario admUsuario	= admUsuarioDao.mapeaRegId(usuarioId);
		
		modelo.addAttribute("usuarioId",usuarioId);
		modelo.addAttribute("admUsuario",admUsuario);
		
		return "admlinea/admision/cambiaPass";
	}
	
	@RequestMapping("/admlinea/admision/cambiaClave")
	public String admlineaAdmisionCambiaClave(HttpServletRequest request, Model modelo){
		
		String folio		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String accion 		= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		String usuarioId 	= admSolicitudDao.getUsuarioId(folio);
		
		String mensaje 	= "0";
		try {
			if(accion.equals("1")){
				String clave 		= request.getParameter("clave1");
				MessageDigest md5 	= MessageDigest.getInstance("MD5");
				
				md5.update(clave.getBytes("UTF-8"));
				byte raw[] 			= md5.digest();    
				String claveDigest	=  java.util.Base64.getEncoder().encodeToString(raw);							    
				if(admUsuarioDao.updateClaveUsuario(usuarioId, claveDigest)){
					mensaje = "La clave se guardó correctamente";
				}else{
					mensaje = "Ocurrió un error al guardar, intentelo de nuevo";
				}
			}
		}catch(Exception ex) {
			System.out.println("Error: "+ex);
		}

		return "redirect:/admlinea/admision/cambia?Mensaje="+mensaje+"&Folio="+folio;
	}
	
	@RequestMapping("/admlinea/admision/grabaClave")
	public String admlineaAdmisionGrabaClave(HttpServletRequest request, Model modelo){
		
		String usuarioId	= request.getParameter("UsuarioId")==null?"0":request.getParameter("UsuarioId");
		String clave 		= request.getParameter("Clave")==null?"0":request.getParameter("Clave");		
		
		String mensaje 	= "0";
		try {		
			
			MessageDigest md5 	= MessageDigest.getInstance("MD5");			
			md5.update(clave.getBytes("UTF-8"));
			byte raw[] 			= md5.digest();    
			String claveDigest	=  java.util.Base64.getEncoder().encodeToString(raw);							    
			if(admUsuarioDao.updateClaveUsuario(usuarioId, claveDigest)){
				mensaje = "La clave se guardó correctamente";
			}else{
				mensaje = "Ocurrió un error al guardar, intentelo de nuevo";
			}			
		}catch(Exception ex) {
			System.out.println("Error: "+ex);
		}

		return "redirect:/admlinea/admision/cambiaPass?Mensaje="+mensaje+"&UsuarioId="+usuarioId;
	}

	@RequestMapping("/admlinea/admision/solicitud")
	public String admlineaAdmisionSolicitud(HttpServletRequest request, Model modelo){
		String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String codigoPersonal 	= "";
		String nombreCarrera 	= "";
		String nombrePlan 		= "";
		String edad				= "0";
		Acceso acceso 			= new Acceso();

		HttpSession sesion		= ((HttpServletRequest)request).getSession();	
		if (sesion != null){
			codigoPersonal 		= sesion.getAttribute("codigoPersonal").toString();
			acceso 				= accesoDao.mapeaRegId(codigoPersonal);			
		}
		
		AdmSolicitud solicitud 	= admSolicitudDao.mapeaRegId(folio);
		AdmUsuario usuario 		= admUsuarioDao.mapeaRegId(solicitud.getUsuarioId());
		AdmAcademico academico 	= admAcademicoDao.mapeaRegId(folio);
		AdmEstudio estudio 		= admEstudioDao.mapeaRegMaxEstudio(folio);
		AdmSalud salud 			= admSaludDao.mapeaRegId(folio);
		AdmPadres padres 		= admPadresDao.mapeaRegId(folio);
		AdmTutor tutor 			= admTutorDao.mapeaRegId(folio);
		AdmAcomodo acomodo		= admAcomodoDao.mapeaRegId(solicitud.getAcomodoId());
		AdmBanco banco			= admBancoDao.mapeaRegId(folio);
		
		boolean tieneRecomendaciones = false;

		HashMap<String,AdmRecomienda> recomienda = admRecomiendaDao.mapaRecomendaciones(folio);

		if(admRecomiendaDao.existeReg(folio, "1") && admRecomiendaDao.existeReg(folio, "2")){
			tieneRecomendaciones = true;
		}

		String nombreNacionalidad 		= catPaisDao.getNacionalidad(solicitud.getNacionalidad());
		String nombrePais 				= catPaisDao.getNombrePais(solicitud.getPaisId());
		String nombreEstado 			= catEstadoDao.getNombreEstado(solicitud.getPaisId(), solicitud.getEstadoId());
		String nombreCiudad 			= catCiudadDao.getNombreCiudad(solicitud.getPaisId(), solicitud.getEstadoId(), solicitud.getCiudadId());
		String nombreReligion 			= catReligionDao.getNombreReligion(solicitud.getReligionId());
		String nombreModalidad 			= catModalidadDao.getNombreModalidad(academico.getModalidad());
		String educacionPais 			= catPaisDao.getNombrePais(estudio.getPaisId());
		String nombrePadreReligion 		= catReligionDao.getNombreReligion(padres.getPadreReligion());
		String nombrePadreNacionalidad 	= catPaisDao.getNacionalidad(padres.getPadreNacionalidad());
		String nombreMadreReligion 		= catReligionDao.getNombreReligion(padres.getMadreReligion());
		String nombreMadreNacionalidad 	= catPaisDao.getNacionalidad(padres.getMadreNacionalidad());
		String nombreResPais 			= catPaisDao.getNacionalidad(solicitud.getResPaisId());
		String nombreResEstado 			= catEstadoDao.getNombreEstado(solicitud.getResPaisId(), solicitud.getResEstadoId());
		String nombreResCiudad 			= catCiudadDao.getNombreCiudad(solicitud.getResPaisId(), solicitud.getResEstadoId(), solicitud.getResCiudadId());
		
		String tipoSolicitud 			= "";
		if(academico.getTipo().equals("S")) tipoSolicitud = "School Leaver";
		if(academico.getTipo().equals("N")) tipoSolicitud = "Non-School Leaver";
		if(academico.getTipo().equals("R")) tipoSolicitud = "Re-admission";
		if(academico.getTipo().equals("P")) tipoSolicitud = "Post Graduate";
		
		nombreCarrera = catCarreraDao.getNombreCarrera(academico.getCarreraId());
		nombrePlan = mapaPlanDao.getNombrePlan(academico.getPlanId());
		edad = admSolicitudDao.getEdad(folio);

		modelo.addAttribute("recomienda", recomienda);
		modelo.addAttribute("folio", folio);
		modelo.addAttribute("solicitud", solicitud);
		modelo.addAttribute("usuario", usuario);
		modelo.addAttribute("academico", academico);
		modelo.addAttribute("estudio", estudio);
		modelo.addAttribute("salud", salud);
		modelo.addAttribute("padres", padres);
		modelo.addAttribute("tutor", tutor);
		modelo.addAttribute("acomodo", acomodo);
		modelo.addAttribute("banco", banco);
		modelo.addAttribute("nombreCarrera", nombreCarrera);
		modelo.addAttribute("nombrePlan", nombrePlan);
		modelo.addAttribute("tipoSolicitud", tipoSolicitud);
		modelo.addAttribute("edad", edad);
		modelo.addAttribute("tieneRecomendaciones", tieneRecomendaciones);

		modelo.addAttribute("nombreNacionalidad", nombreNacionalidad);
		modelo.addAttribute("nombrePais", nombrePais);
		modelo.addAttribute("nombreEstado", nombreEstado);
		modelo.addAttribute("nombreCiudad", nombreCiudad);
		modelo.addAttribute("nombreReligion", nombreReligion);
		modelo.addAttribute("nombreModalidad", nombreModalidad);
		modelo.addAttribute("educacionPais", educacionPais);
		modelo.addAttribute("nombrePadreReligion", nombrePadreReligion);
		modelo.addAttribute("nombrePadreNacionalidad", nombrePadreNacionalidad);
		modelo.addAttribute("nombreMadreReligion", nombreMadreReligion);
		modelo.addAttribute("nombreMadreNacionalidad", nombreMadreNacionalidad);
		modelo.addAttribute("nombreResPais", nombreResPais);
		modelo.addAttribute("nombreResEstado", nombreResEstado);
		modelo.addAttribute("nombreResCiudad", nombreResCiudad);

		return "admlinea/admision/solicitud";
	}
	
	
	@RequestMapping("/admlinea/admision/confirmar")
	public String admlineaAdmisionConfirmar(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContAdmlineaAdmision|admlineaAdmisionConfirmar");
		return "admlinea/admision/confirmar";
	}	
	
	@RequestMapping("/admlinea/admision/documentosEnviados")
	public String admlineaAdmisionDocumentosEnviados(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContAdmlineaAdmision|admlineaAdmisionDocumentosEnviados");		
		return "admlinea/admision/documentosEnviados";
	}	
	
	@RequestMapping("/admlinea/admision/encuestas")
	public String admlineaAdmisionEncuestas(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContAdmlineaAdmision|admlineaAdmisionEncuestas");
		return "admlinea/admision/encuestas";
	}
	
	@RequestMapping("/admlinea/admision/guardararchivo")
	public String admlineaAdmisionGuardArarchivo(HttpServletRequest request){		
		return "admlinea/admision/guardararchivo";
	}	
	
	@RequestMapping("/admlinea/admision/imagenDoc")
	public String admlineaAdmisionImagenDoc(HttpServletRequest request){		
		return "admlinea/admision/imagenDoc";
	}
	
	@RequestMapping("/admlinea/admision/proceso")
	public String admlineaAdmisionProceso(HttpServletRequest request, Model modelo){
		String fechaHoy			= aca.util.Fecha.getHoy();
		String arrayHoy[]		= fechaHoy.split("/");
		int yearNow				= Integer.parseInt(fechaHoy.substring(6,10));
		//int yearOld				= Integer.parseInt(fechaHoy.substring(6,10))-1;
		String fechaIni 		= request.getParameter("fechaIni")==null?String.valueOf(yearNow)+"-01-01":request.getParameter("fechaIni");
		String fechaFin 		= request.getParameter("fechaFin")==null?arrayHoy[2]+"-"+arrayHoy[1]+"-"+arrayHoy[0]:request.getParameter("fechaFin");
		String estados 			= request.getParameter("Estados") == null ? "0" : request.getParameter("Estados");
		String admitidos 		= request.getParameter("Admitidos") == null ? "0" : request.getParameter("Admitidos");
		String modalidades		= (request.getParameter("Modalidades") == null ? "0" : request.getParameter("Modalidades"));
		String asesor 			= request.getParameter("Asesor") == null ? "0000000" : request.getParameter("Asesor");
		String planId 			= request.getParameter("PlanId") == null ? "0" : request.getParameter("PlanId");		
		String nombreAlumno		= (request.getParameter("NombreAlumno") == null ? "0" : request.getParameter("NombreAlumno"));
		String codigoPersonal	= "0";		
		Acceso acceso 			= new Acceso(); 
		HttpSession sesion		= ((HttpServletRequest)request).getSession();		
		String arrayIni[] 		= fechaIni.split("-");
		String arrayFin[] 		= fechaFin.split("-");

		String institucionPaisId = parametrosDao.getPaisId("1");

		if (sesion != null){
			codigoPersonal 		= sesion.getAttribute("codigoPersonal").toString();
			acceso 				= accesoDao.mapeaRegId(codigoPersonal);			
			sesion.setAttribute("fechaInicio",arrayIni[2]+"/"+arrayIni[1]+"/"+arrayIni[0]);
			sesion.setAttribute("fechaFinal",arrayFin[2]+"/"+arrayFin[1]+"/"+arrayFin[0]);
			sesion.setAttribute("estados",estados);			
			sesion.setAttribute("admitidos",admitidos);
			sesion.setAttribute("modalidades",modalidades);
			sesion.setAttribute("asesor",asesor);
		}
		
		if (estados.equals("0")) estados = "'0','1','2','3','4','5','6'"; else estados = "'"+estados+"'";	
		
		String condicion 		= " AND TO_DATE(TO_CHAR(SOL.FECHA,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+arrayIni[2]+"/"+arrayIni[1]+"/"+arrayIni[0]+"','DD/MM/YYYY') AND TO_DATE('"+arrayFin[2]+"/"+arrayFin[1]+"/"+arrayFin[0]+"','DD/MM/YYYY') AND SOL.ESTADO IN ("+estados+")";			
		
		if(admitidos.equals("1")) {
			condicion += " AND SOL.FECHA_INGRESO = '-'";
		}else if(admitidos.equals("2")) {
			condicion += " AND SOL.FECHA_INGRESO != '-'";
		}

		if(modalidades.equals("0")) {
			condicion += "";			
		}else {
			condicion += " AND SOL.FOLIO IN (SELECT FOLIO FROM SALOMON.ADM_ACADEMICO WHERE MODALIDAD_ID IN("+modalidades+"))";			
		}

		if(!planId.equals("0")) {
			condicion += " AND SOL.FOLIO IN (SELECT FOLIO FROM SALOMON.ADM_ACADEMICO WHERE PLAN_ID = '"+planId+"')";
		}

		List<String> listAsesor							= admSolicitudDao.listAsesor();
		listAsesor.add(0, "0000000");
		
		if(!asesor.equals("0000000")) {
			condicion += " AND SOL.ASESOR_ID = '"+asesor+"' ";
		}
		
		List<String> lisAsesores = admAsesorDao.lisAsesorId("A");
		nombreAlumno = nombreAlumno.toUpperCase();
		
		if(!nombreAlumno.equals("0")) {
			condicion += " AND (US.NOMBRE LIKE '%"+nombreAlumno+"%' OR US.APELLIDO_PATERNO LIKE '%"+nombreAlumno+"%' OR US.APELLIDO_MATERNO LIKE '%"+nombreAlumno+"%')";
		}

		List<AdmSolicitud> lisAlumnos 					= admSolicitudDao.listIngreso( condicion," ORDER BY SOL.FECHA DESC");
		
		String folio = "-";
		for(AdmSolicitud alumno : lisAlumnos){
			folio = alumno.getFolio();
		}
		
		List<AdmRequisito> listaRequisitos = admRequisitoDao.getListAll(" ORDER BY CARRERA_ID,DOCUMENTO_ID");	
		List<CatModalidad> listaModalidades = catModalidadDao.getListAll(" ORDER BY MODALIDAD_ID");
		List<MapaPlan> listaPlanes = mapaPlanDao.getPlanesAdmisiones(" ORDER BY PLAN_ID");
		
		HashMap<String, AdmAcademico> mapaAcademico 	= admAcademicoDao.mapaTodos();
		HashMap<String, AdmBanco> mapaBanco 			= admBancoDao.mapaTodos();	
		HashMap<String, String> mapaPago 				= admPagoDao.mapaExiste();												
		HashMap<String,CatModalidad> mapaModalidades 	= catModalidadDao.getMapAll("");									
		HashMap<String,CatCarrera> mapaCarreras 		= catCarreraDao.getMapAll("");									
		HashMap<String,CatPais> mapaPaises 				= catPaisDao.getMapAll();												
		HashMap<String,String> mapaMaestros	 			= maestrosDao.mapaMaestroCorto("NOMBRE");						
		HashMap<String,String> mapaReservados 			= admReservadaDao.mapaReservados("'A','R'");					
		HashMap<String,AdmPasos> mapaPasos	 			= admPasosDao.mapaPasos();												
		HashMap<String,String> mapaResultados 			= admEvaluacionNotaDao.mapaResultados();								
		HashMap<String,String> mapaResultadosExamenes	= admEvaluacionNotaDao.mapaResultadosExamenes();						
		HashMap<String,String> mapaExamenes				= examenDao.mapaPorFolio();												
		HashMap<String,String> mapaNuevos				= admDocAlumDao.mapaDocumentosEstado();									
		HashMap<String,String> mapaDocumentos			= admDocAlumDao.mapaDocumentosRevisado();								
		HashMap<String,String> mapaDocPorFolio 			= admDocAlumDao.mapaDocPorFolio();	
		HashMap<String,String> mapaSolicitudesVacias 	= admSolicitudDao.mapaSolicitudesVacias();	
		HashMap<String,String> mapaNombrePlanes 		= mapaPlanDao.mapNombrePlan();								
		
		modelo.addAttribute("acceso",acceso);
		modelo.addAttribute("asesor",asesor);		
		modelo.addAttribute("admitidos",admitidos);
		modelo.addAttribute("modalidades",modalidades);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("institucionPaisId", institucionPaisId);
		modelo.addAttribute("listAsesor",listAsesor);
		modelo.addAttribute("lisAlumnos",lisAlumnos);
		modelo.addAttribute("mapaAcademico",mapaAcademico);
		modelo.addAttribute("mapaBanco", mapaBanco);
		modelo.addAttribute("mapaPago", mapaPago);
		modelo.addAttribute("mapaModalidades",mapaModalidades);
		modelo.addAttribute("mapaCarreras",mapaCarreras);
		modelo.addAttribute("mapaPaises",mapaPaises);
		modelo.addAttribute("mapaMaestros",mapaMaestros);
		modelo.addAttribute("mapaReservados",mapaReservados);
		modelo.addAttribute("mapaPasos",mapaPasos);
		modelo.addAttribute("mapaResultados",mapaResultados);
		modelo.addAttribute("mapaResultadosExamenes",mapaResultadosExamenes);
		modelo.addAttribute("mapaComentarios",admComentarioDao.mapaComentarios("'A'"));
		modelo.addAttribute("mapaExamenes",mapaExamenes);
		modelo.addAttribute("mapaNuevos",mapaNuevos);
		modelo.addAttribute("mapaDocumentos",mapaDocumentos);
		modelo.addAttribute("nombreAlumno",nombreAlumno);
		modelo.addAttribute("mapaDocPorFolio",mapaDocPorFolio);
		modelo.addAttribute("mapaSolicitudesVacias", mapaSolicitudesVacias);
		modelo.addAttribute("mapaNombrePlanes", mapaNombrePlanes);
		modelo.addAttribute("listaRequisitos",listaRequisitos);
		modelo.addAttribute("listaModalidades",listaModalidades);
		modelo.addAttribute("lisAsesores", lisAsesores);
		modelo.addAttribute("listaPlanes", listaPlanes);
		
		return "admlinea/admision/proceso";
	}

	@RequestMapping("/admlinea/admision/eliminarSolicitudVacia")
	public String eliminarSolicitudVacia(HttpServletRequest request, Model modelo){
		String folio = request.getParameter("Folio");
		String mensaje = "";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();		

		HashMap<String,String> mapaSolicitudesVacias = admSolicitudDao.mapaSolicitudesVacias();
		if(admSolicitudDao.existeReg(folio)){
			try{
				if(mapaSolicitudesVacias.containsKey(folio)){
					if(admSolicitudDao.deleteReg(folio)){
						mensaje = "1";
					}else{
						mensaje = "0";
					}		
				}
			}catch (Exception e){
				System.out.println("Error - aca.web.amdlinea.ContAmdlineaAdmision|deleteReg|:"+e);
			}
		}

		return "redirect:/admlinea/admision/proceso";
	}

	@RequestMapping("/admlinea/admision/eliminarSolicitud")
	public String eliminarSolicitud(HttpServletRequest request, Model modelo){
		String folio = request.getParameter("Folio");	
		String mensaje = "";

		boolean admArchivo 		= admArchivoDao.existeRegFolio(folio);
		boolean admDocAlum 		= admDocAlumDao.existeRegFolio(folio);
		boolean admRecomienda 	= admRecomiendaDao.existeRegFolio(folio);
		boolean admSalud 		= admSaludDao.existeReg(folio);
		boolean admEstudio 		= admEstudioDao.existeRegFolio(folio);
		boolean admAcademico 	= admAcademicoDao.existeReg(folio);
		boolean admSolicitud 	= admSolicitudDao.existeReg(folio);
		boolean admProceso 		= admProcesoDao.existeReg(folio);
		boolean admPasos 		= admPasosDao.existeRegFolio(folio);

		if(admArchivo) admArchivoDao.deleteReg(folio);
		if(admDocAlum) admDocAlumDao.deleteReg(folio);
		if(admRecomienda) admRecomiendaDao.deleteReg(folio);
		if(admSalud) admSaludDao.deleteReg(folio);
		if(admEstudio) admEstudioDao.deleteReg(folio);
		if(admAcademico) admAcademicoDao.deleteReg(folio);
		if(admProceso) admProcesoDao.deleteReg(folio);
		if(admPasos) admPasosDao.deleteReg(folio);
		if(admSolicitud) admSolicitudDao.deleteReg(folio);
		
		mensaje = "Deleted application";
		
		return "redirect:/admlinea/admision/proceso?Mensaje="+mensaje;
	}
	
	@ResponseBody
	@RequestMapping(value="/admlinea/admision/copiaDatosFaltantes",method = RequestMethod.GET)
	public String acopiaDatosFaltantes(HttpServletRequest request) {
		String respuesta = "";
		
		String matLike = request.getParameter("matLike");
		
		List<AlumUbicacion> listAlumU = alumUbicacionDao.getListAll("WHERE CODIGO_PERSONAL LIKE '"+matLike+"%'" +
				" AND P_NOMBRE = ' '" + 
				" AND P_RELIGION IS NULL");
		
		for(AlumUbicacion au: listAlumU){
			AdmSolicitud admSolicitud = admSolicitudDao.mapeaRegMatricula(au.getCodigoPersonal());
			String folio = admSolicitud.getFolio();			
			
			AdmUbicacion Ubicacion = admUbicacionDao.mapeaRegId(folio);
			AdmPadres Padres = admPadresDao.mapeaRegId(folio);
			AdmTutor Tutor = admTutorDao.mapeaRegId(folio);
			
			au.setpNombre(Padres.getPadreApellido().trim().toUpperCase()+" "+Padres.getPadreNombre().trim().toUpperCase());
			au.setpReligion(Padres.getPadreReligion());
			au.setpNacionalidad(Padres.getPadreNacionalidad());
			au.setmNombre(Padres.getMadreApellido().trim().toUpperCase()+" "+Padres.getMadreNombre().trim().toUpperCase());
			au.setmReligion(Padres.getMadreReligion());
			au.setmNacionalidad(Padres.getMadreNacionalidad());
			if(Tutor.getTutor().equals("0")){
				au.settNombre(Padres.getPadreApellido().trim().toUpperCase()+" "+Padres.getPadreNombre().trim().toUpperCase());
			}
			else if(Tutor.getTutor().equals("1")){
				au.settNombre(Padres.getMadreApellido().trim().toUpperCase()+" "+Padres.getMadreNombre().trim().toUpperCase());
			}
			else if(Tutor.getTutor().equals("3")){
				au.settNombre(Tutor.getNombre().trim().toUpperCase());
			}
			if(Tutor.getTutor().equals("2")){
				au.settNombre(admSolicitud.getApellidoPaterno().toUpperCase()+" "+admSolicitud.getApellidoMaterno().toUpperCase()+" "+admSolicitud.getNombre().trim().toUpperCase());
			}
			au.settDireccion(Tutor.getCalle().trim().toUpperCase()+" #"+Tutor.getNumero().trim());
			au.settColonia(Tutor.getColonia().trim().toUpperCase());
			au.settCodigo(Tutor.getCodigoPostal().trim());
			au.settTelefono(Tutor.getTelefono().trim());
			au.settPais(Tutor.getPaisId());
			au.settEstado(Tutor.getEstadoId());
			au.settCiudad(Tutor.getCiudadId());			
			au.settApartado("");
			au.settCelular("-");
			au.settComunica("E");
			
			if(alumUbicacionDao.updateReg(au)){
				respuesta +=au.getCodigoPersonal()+" Listo<br />";
			}else{
				respuesta +=au.getCodigoPersonal()+" ERROR AL GUARDAR. INTENTE DE NUEVO<br />";
			}
		}
		respuesta += "Fin de lista";
		return respuesta;
	}
	
	@RequestMapping("/admlinea/admision/registro")
	public String admlineaAdmisionRegistro(HttpServletRequest request, Model modelo){
		
		String folio 			= request.getParameter("Folio")==null?"0":request.getParameter("Folio"); 
		String accion 			= request.getParameter("Accion")==null? "0" : request.getParameter("Accion");
		String codigoPersonal 	= "0";
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();	
		if (sesion != null){
			codigoPersonal 		= sesion.getAttribute("codigoPersonal").toString();  
		}
		AdmSolicitud admSolicitud 	= new AdmSolicitud();		
		AlumPersonal alumPersonal	= new AlumPersonal();
		
		boolean acceso = false;		
		if(admAsesorDao.existeReg(codigoPersonal)){
			acceso = true;
		}
		
		admSolicitud 			= admSolicitudDao.mapeaRegId( folio);
		boolean tieneMatricula 	= false;
		if(admSolicitud.getMatricula()!=null && admSolicitud.getMatricula().length()==7){
			try{
				int mat = Integer.parseInt(admSolicitud.getMatricula());
				tieneMatricula = true;
			}catch(Exception e){
				tieneMatricula = false;		
			}
		}
		
		if(accion.equals("1")){			
			admSolicitud = admSolicitudDao.mapeaRegId(folio);
			
			admSolicitud.setNombre(request.getParameter("Nombre").trim().toUpperCase());
			admSolicitud.setApellidoPaterno(request.getParameter("Paterno").trim().toUpperCase());
			admSolicitud.setApellidoMaterno(request.getParameter("Materno").trim().toUpperCase());
			admSolicitud.setGenero(request.getParameter("Genero"));
			admSolicitud.setEmail(request.getParameter("Email"));
			admSolicitud.setEstado(request.getParameter("estado")==null ? admSolicitud.getEstado() : request.getParameter("estado"));
			admSolicitud.setUsuario(request.getParameter("usuario"));
			admSolicitud.setTelefono(request.getParameter("Telefono"));
			if(admSolicitudDao.updateReg(admSolicitud)){
				if(tieneMatricula){
					if(alumPersonalDao.existeReg( admSolicitud.getMatricula())){
						alumPersonal = alumPersonalDao.mapeaRegId(admSolicitud.getMatricula());					
						alumPersonal.setNombre(admSolicitud.getNombre().trim().toUpperCase());
						alumPersonal.setApellidoPaterno(admSolicitud.getApellidoPaterno().equals("") ? " " : admSolicitud.getApellidoPaterno().trim().toUpperCase());
						alumPersonal.setApellidoMaterno(admSolicitud.getApellidoMaterno().equals("") ? " " : admSolicitud.getApellidoMaterno().trim().toUpperCase());
						alumPersonal.setNombreLegal(alumPersonal.getNombre()+" "+alumPersonal.getApellidoPaterno()+" "+alumPersonal.getApellidoMaterno());
						alumPersonal.setSexo(admSolicitud.getGenero());
						alumPersonal.setEmail(admSolicitud.getEmail());
						alumPersonal.setTelefono(admSolicitud.getTelefono());
						alumPersonalDao.updateReg(alumPersonal);
					}
				}
			}
		}

		String nombreNacionalidad 	= catPaisDao.getNacionalidad(admSolicitud.getPaisId());
		String nombrePais 			= catPaisDao.getNombrePais(admSolicitud.getPaisId());
		String nombreEstado 		= catEstadoDao.getNombreEstado(admSolicitud.getPaisId(), admSolicitud.getEstadoId());
		String nombreCiudad 		= catCiudadDao.getNombreCiudad(admSolicitud.getPaisId(), admSolicitud.getEstadoId(), admSolicitud.getCiudadId());
		String nombreCultural 		= catCulturalDao.getNombreCultural(admSolicitud.getCulturalId());
		String nombreRegion 		= catRegionDao.getNombreRegion(admSolicitud.getCulturalId(), admSolicitud.getRegionId());
		String nombreResPais 		= catPaisDao.getNombrePais(admSolicitud.getResPaisId());
		String nombreResEstado 		= catEstadoDao.getNombreEstado(admSolicitud.getResPaisId(), admSolicitud.getResEstadoId());
		String nombreResCiudad 		= catCiudadDao.getNombreCiudad(admSolicitud.getResPaisId(), admSolicitud.getResEstadoId(), admSolicitud.getResCiudadId());
		String nombreReligion 		= catReligionDao.getNombreReligion(admSolicitud.getReligionId());
		String nombreAcomodo		= admAcomodoDao.getNombreAcomodo(admSolicitud.getAcomodoId());
		
		
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("tieneMatricula", tieneMatricula);
		modelo.addAttribute("admSolicitud", admSolicitud);	
		modelo.addAttribute("nombreNacionalidad", nombreNacionalidad);	
		modelo.addAttribute("nombrePais", nombrePais);
		modelo.addAttribute("nombreEstado", nombreEstado);
		modelo.addAttribute("nombreCiudad", nombreCiudad);
		modelo.addAttribute("nombreCultural", nombreCultural);
		modelo.addAttribute("nombreRegion", nombreRegion);
		modelo.addAttribute("nombreResPais", nombreResPais);
		modelo.addAttribute("nombreResEstado", nombreResEstado);
		modelo.addAttribute("nombreResCiudad", nombreResCiudad);	
		modelo.addAttribute("nombreReligion", nombreReligion);
		modelo.addAttribute("nombreAcomodo", nombreAcomodo);
		
		return "admlinea/admision/registro";
	}

	@RequestMapping("/admlinea/admision/banco")
	public String admlineaAdmisionBanco(HttpServletRequest request, Model modelo){
		
		String folio 			= request.getParameter("Folio")==null?"0":request.getParameter("Folio"); 
		String codigoPersonal 	= "0";
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();	
		if (sesion != null){
			codigoPersonal 		= sesion.getAttribute("codigoPersonal").toString();  
		}
		AdmBanco admBanco 	= new AdmBanco();		
		AdmSolicitud admSolicitud = new AdmSolicitud();
		
		if(admBancoDao.existeReg(folio)){
			admSolicitud		= admSolicitudDao.mapeaRegId(folio);
			admBanco 			= admBancoDao.mapeaRegId(folio);
		}
		
		modelo.addAttribute("admSolicitud", admSolicitud);
		modelo.addAttribute("admBanco", admBanco);			
		
		return "admlinea/admision/banco";
	}
	
	@RequestMapping("/admlinea/admision/requisitos")
	public String admlineaAdmisionRequisitos(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContAdmlineaAdmision|admlineaAdmisionRequisitos");
		return "admlinea/admision/requisitos";
	}	
	
	@RequestMapping("/admlinea/admision/sincronizar")
	public String admlineaAdmisionSincronizar(HttpServletRequest request, Model modelo){
		
		String folio 			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String usuarioId 		= admSolicitudDao.getUsuarioId(folio);
		String matriculaUsuario	= admUsuarioDao.getMatricula(usuarioId);
		boolean existeAlumno	= alumPersonalDao.existeReg(matriculaUsuario);
		String matricula		= request.getParameter("Matricula")==null?"X":request.getParameter("Matricula");		
		String accion			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		String claveHexa		= bCryptPasswordEncoder.encode(matricula);
		String respuesta 		= "-";
		
		String codigoPersonal 	= "0";		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();	
		if (sesion != null){
			codigoPersonal 		= sesion.getAttribute("codigoPersonal").toString();  
		}
		
		if(accion.equals("1")){				
			if (admProcesoDao.existeReg(folio)){
				admProcesoDao.updateFecha(4, folio);
			}
			admSolicitudDao.updateEstadoAndFecha(folio,"5");
			AdmAcademico admAcademico 	= admAcademicoDao.mapeaRegId(folio);		
			AdmSolicitud admSolicitud 	= admSolicitudDao.mapeaRegId(folio);
			AdmBanco admBanco			= admBancoDao.mapeaRegId(folio);
			
			AdmEstudio admEstudio		= admEstudioDao.mapeaRegMaxEstudio(folio);
			AdmUsuario admUsuario 		= admUsuarioDao.mapeaRegId(usuarioId); 
			//AdmUbicacion admUbicacion = admUbicacionDao.mapeaRegId(folio);
			AdmPadres admPadres 		= admPadresDao.mapeaRegId(folio) == null ? new AdmPadres() : admPadresDao.mapeaRegId(folio);		
			AdmTutor admTutor	 		= admTutorDao.mapeaRegId(folio) == null ? new AdmTutor() : admTutorDao.mapeaRegId(folio);	
			String clave 				= "";		
			
			String apellidoPaterno = admSolicitud.getApellidoPaterno()==null ? " " : (admSolicitud.getApellidoPaterno().trim().equals("") ? " " : admSolicitud.getApellidoPaterno());
			String apellidoMaterno = admSolicitud.getApellidoMaterno()==null ? " " : (admSolicitud.getApellidoMaterno().trim().equals("") ? " " : admSolicitud.getApellidoMaterno());
			
			AlumPersonal alumPersonal = new AlumPersonal();
			alumPersonal.setCodigoPersonal(matricula);			
			alumPersonal.setNombre(admSolicitud.getNombre().trim().toUpperCase());
			alumPersonal.setApellidoPaterno(apellidoPaterno.toUpperCase());
			alumPersonal.setApellidoMaterno(apellidoMaterno.toUpperCase());
			alumPersonal.setNombreLegal(admSolicitud.getNombre().trim().toUpperCase()+" "+apellidoPaterno.toUpperCase()+" "+apellidoMaterno.toUpperCase());
			alumPersonal.setFNacimiento(admSolicitud.getFechaNac());
			alumPersonal.setSexo(admSolicitud.getGenero());
			alumPersonal.setEstadoCivil(admSolicitud.getEstadoCivil());
			alumPersonal.setReligionId(admSolicitud.getReligionId());
			if(alumPersonal.getReligionId().equals("1")){
				alumPersonal.setBautizado(admSolicitud.getBautizado());
				if(admSolicitud.getBautizado().equals("S")){
					alumPersonal.setfBautizado(admSolicitud.getFechaBautizo());
				}
			}
			alumPersonal.setPaisId(admSolicitud.getPaisId());
			alumPersonal.setEstadoId(admSolicitud.getEstadoId());
			alumPersonal.setCiudadId(admSolicitud.getCiudadId());
			alumPersonal.setNacionalidad(admSolicitud.getNacionalidad());
			alumPersonal.setEmail(admSolicitud.getEmail().trim());
			
			// Verifica que exista Grupo Cultural y Grupo Regional
			if(catCulturalDao.existeReg(admSolicitud.getCulturalId())){
				alumPersonal.setCulturalId(admSolicitud.getCulturalId());
				// System.out.println(admSolicitud.getCulturalId());
				if(catRegionDao.existeReg(admSolicitud.getRegionId(), admSolicitud.getCulturalId())){
					// System.out.println(admSolicitud.getRegionId());
					alumPersonal.setRegionId(admSolicitud.getRegionId());
				}
			}

			alumPersonal.setResPaisId(admSolicitud.getResPaisId());
			alumPersonal.setResEstadoId(admSolicitud.getResEstadoId());
			alumPersonal.setResCiudadId(admSolicitud.getResCiudadId());
			
			// Asigna la CURP
			// alumPersonal.setCurp(aca.tools.Curp.getCurp(admSolicitud.getNombre().trim(), apellidoPaterno, apellidoMaterno, admSolicitud.getGenero(),
			// 					admSolicitud.getFechaNac(), Integer.parseInt(admSolicitud.getPaisId()), Integer.parseInt(admSolicitud.getEstadoId())));
			alumPersonal.setCotejado("N");
			
			//Establecemos la clave		 
			String claveDigest	= aca.util.Encriptar.sha512ConBase64(request.getParameter("Matricula"));
			
			Acceso acceso = new Acceso(); 
			acceso.setCodigoPersonal(matricula);
			acceso.setAdministrador("N");
			acceso.setAccesos("X");
			acceso.setCotejador("N");
			acceso.setSupervisor("N");
			acceso.setPortalAlumno("N");
			acceso.setPortalMaestro("N");
			acceso.setModalidad("0");
			acceso.setUsuario(matricula);
			acceso.setClave(claveDigest);
			acceso.setIdioma("en");
			acceso.setMenu("1");
			acceso.setClaveInicial(matricula);
			acceso.setClaveHexa(claveHexa);
			acceso.setEnLinea("N");
			alumPersonal.setEstado("A");
			alumPersonal.setFCreado(aca.util.Fecha.getHoy());
			alumPersonal.setUsAlta(codigoPersonal);
			
			if(alumPersonalDao.existeReg(matricula)==false && matricula.length()==7){
				if(alumPersonalDao.insertReg(alumPersonal) && admSolicitudDao.updateMatricula(folio, matricula)){			
					if(accesoDao.insertReg(acceso)){				
						AlumUbicacion alumUbicacion = new AlumUbicacion();
						alumUbicacion.setCodigoPersonal(matricula);
						alumUbicacion.setpNombre(admPadres.getPadreApellido().trim().toUpperCase()+" "+admPadres.getPadreNombre().trim().toUpperCase());
						alumUbicacion.setpReligion(admPadres.getPadreReligion());
						alumUbicacion.setpNacionalidad(admPadres.getPadreNacionalidad());
						alumUbicacion.setmNombre(admPadres.getMadreApellido().trim().toUpperCase()+" "+admPadres.getMadreNombre().trim().toUpperCase());
						alumUbicacion.setmReligion(admPadres.getMadreReligion());
						alumUbicacion.setmNacionalidad(admPadres.getMadreNacionalidad());						
						if(admTutor.getTutor().equals("0")){
							alumUbicacion.settNombre(admPadres.getPadreApellido().trim().toUpperCase()+" "+admPadres.getPadreNombre().trim().toUpperCase());
						}
						else if(admTutor.getTutor().equals("1")){
							alumUbicacion.settNombre(admPadres.getMadreApellido().trim().toUpperCase()+" "+admPadres.getMadreNombre().trim().toUpperCase());
						}
						else if(admTutor.getTutor().equals("2")){
							alumUbicacion.settNombre(admTutor.getNombre().trim().toUpperCase());
						}else{
							alumUbicacion.settNombre("");
						}
						
						alumUbicacion.settDireccion(admTutor.getCalle().trim().toUpperCase()+" #"+admTutor.getNumero().trim());
						alumUbicacion.settColonia(admTutor.getColonia().trim().toUpperCase());
						alumUbicacion.settCodigo(admTutor.getCodigoPostal().trim());
						alumUbicacion.settTelefono(admTutor.getTelefono().trim());
						alumUbicacion.settPais(admTutor.getPaisId());
						alumUbicacion.settEstado(admTutor.getEstadoId());
						alumUbicacion.settCiudad(admTutor.getCiudadId());						
						alumUbicacion.settApartado("");
						alumUbicacion.settCelular("-");
						alumUbicacion.settComunica("E");
						
						if(alumUbicacionDao.insertReg(alumUbicacion)){					
							AlumAcademico alumAcademico = new AlumAcademico();
							AlumEstudio alumEstudio = new AlumEstudio();
							AlumPlan alumPlan = new AlumPlan();
							alumAcademico.setCodigoPersonal(matricula);
							alumAcademico.setTipoAlumno("1");
							alumAcademico.setClasFin("1");
							alumAcademico.setObrero("N");
							alumAcademico.setObreroInstitucion("0");
							// Cambiado por covid a externo
							alumAcademico.setResidenciaId("I");
							alumAcademico.setRequerido("S");
							alumAcademico.setDormitorio("0");
							alumAcademico.setFSolicitud(admSolicitud.getFecha());
							alumAcademico.setFAdmision(aca.util.Fecha.getHoy());
							alumAcademico.setFAlta("01/01/1900");
							alumAcademico.setModalidadId(admAcademico.getModalidad());
							alumAcademico.setExtensionId("99");
							alumAcademico.setPrepaLugar("0");
							alumAcademico.setAcomodoId(admSolicitud.getTipoAcomodo());
							
							alumEstudio.setCodigoPersonal(matricula);
							alumEstudio.setCompleto(admEstudio.getCompleto());
							alumEstudio.setConvalida("N");
							alumEstudio.setDependencia("");
							alumEstudio.setFin("");
							alumEstudio.setId("1");
							alumEstudio.setInicio("");
							alumEstudio.setInstitucion(admEstudio.getInstitucion());
							alumEstudio.setTitulo(admEstudio.getTitulo());
							
							alumPlan.setCodigoPersonal(matricula);
							alumPlan.setPlanId(admAcademico.getPlanId());
							alumPlan.setFInicio(admAcademico.getFecha());
							alumPlan.setEstado("1");
							alumPlan.setEscuelaId("1");
							alumPlan.setAvanceId("1");
							alumPlan.setPermiso("N");
							alumPlan.setEvento("N");
							alumPlan.setGrado("1");
							alumPlan.setCiclo("1");
							alumPlan.setPrincipal("1");
							alumPlan.setEscala("100");
							alumPlan.setPrimerMatricula("01/01/2000");
							alumPlan.setActualizado("N");
							alumPlan.setCicloSep("0");
							alumPlan.setPromedio("0");
							alumPlan.setFinalizado("N");
							
							
							if(alumAcademicoDao.insertReg(alumAcademico) && alumEstudioDao.insertReg(alumEstudio) && alumPlanDao.insertReg(alumPlan)){
								admSolicitud.setMatricula(matricula);
								admSolicitud.setEstado("5");
								
								if (admUsuarioDao.updateMatricula(usuarioId, matricula) && admSolicitudDao.updateReg(admSolicitud)){
									AlumBanco alumBanco = new AlumBanco();
									alumBanco.setBanco(admBanco.getBanco());
									alumBanco.setBancoId(alumBancoDao.maximoReg());
									alumBanco.setBancoRama(admBanco.getBancoRama());
									alumBanco.setCuentaNombre(admBanco.getCuentaNombre());
									alumBanco.setCuentaNumero(admBanco.getCuentaNumero());
									alumBanco.setCuentaTipo(admBanco.getCuentaTipo());
									alumBanco.setCodigoPersonal(matricula);
									alumBanco.setNumeroBBS(admBanco.getNumeroBbs());
									alumBanco.setCodigoSwift(admBanco.getCodigoSwift());
									if(alumBancoDao.insertReg(alumBanco)){
										respuesta = "Successfully Transfered!";
									}else{
										respuesta = "There was an error transfering the bank details of the applicant";
									}		
								}else{
									respuesta = "There was an error updating the application status of the applicant";
								}
							}else{
								respuesta = "There was an error transfering academic details of the applicant";
							}
						}else{
							respuesta = "There was an error transfering provenance details of the applicant";									
						}
					}else{
						respuesta = "There was an error creating access details of the applicant";
					}
				}else{
					respuesta = "There was an error transfering personal details of the applicant";					
				}
			}else{
				respuesta = "This Student ID is already registered";
			}			
		}
		return "redirect:/admlinea/admision/avance?Folio="+folio+"&Mensaje="+respuesta;
	}
	
	@RequestMapping("/admlinea/admision/solicitudPDF")
	public String admlineaAdmisionSolicitudPDF(HttpServletRequest request, Model modelo){		
		
		String folio 	= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		
		AdmAcademico admAcademico 		= admAcademicoDao.mapeaRegId(folio);
		AdmSolicitud admSolicitud 		= admSolicitudDao.mapeaRegId(folio);
		AdmSalud admSalud 				= admSaludDao.mapeaRegId(folio);
		AdmPadres admPadres				= admPadresDao.mapeaRegId(folio);
		AdmTutor admTutor 				= admTutorDao.mapeaRegId(folio);
		String paisNombre				= catPaisDao.getNombrePais(admSolicitud.getPaisId());
		String nacionalidad				= catPaisDao.getNacionalidad(admSolicitud.getNacionalidad());
		String edad						= admSolicitudDao.getEdad(folio);
		String religionNombre			= catReligionDao.getNombreReligion(admSolicitud.getReligionId());
		String carreraNombre 			= catCarreraDao.getNombreCarrera(admAcademico.getCarreraId());
		String estadoNombre 			= "-";
		String ciudadNombre 			= "-";
		String paisDomicilio 			= admTutor.getPaisId()==null?"0":admTutor.getPaisId();
		String estadoDomicilio 			= admTutor.getEstadoId()==null?"0":admTutor.getEstadoId();
		String ciudadDomicilio			= admTutor.getCiudadId()==null?"0":admTutor.getCiudadId();		
		String paisDomicilioNombre		= catPaisDao.getNombrePais(paisDomicilio);	
		String estadoDomicilioNombre	= catEstadoDao.getNombreEstado(paisDomicilio, estadoDomicilio);
		String ciudadDomicilioNombre	= catCiudadDao.getNombreCiudad(paisDomicilio, estadoDomicilio, ciudadDomicilio);
		String religionPadre			= catReligionDao.getNombreReligion(admPadres.getPadreReligion());
		String religionMadre			= catReligionDao.getNombreReligion(admPadres.getMadreReligion());
		
		if(admSolicitud.getMatricula().equals("IIIIIII")){
			admSolicitud.setMatricula("-");
			admSolicitudDao.updateReg(admSolicitud);
		}
		
		if (admSolicitud.getPaisId().equals("147")){
			estadoNombre 	= catEstadoDao.getNombreEstado(admSolicitud.getPaisId(),admSolicitud.getEstadoId());
			ciudadNombre 	= catCiudadDao.getNombreCiudad(admSolicitud.getPaisId(),admSolicitud.getEstadoId(),admSolicitud.getCiudadId());
		}else{
			estadoNombre 	= "-";
			ciudadNombre 	= "-";
		}
		
		List<AdmEstudio> lisEstudios = admEstudioDao.getListAFolio(folio, "ORDER BY FOLIO, ID");
		
		HashMap<String,CatPais> mapPaises 			= catPaisDao.getMapAll();
		HashMap<String,CatEstado> mapEstados 		= catEstadoDao.getMapAll();
		HashMap<String,CatCiudad> mapCiudades 		= catCiudadDao.getMapAll("");
		HashMap<String,CatReligion> mapReligiones	= catReligionDao.getMapAll("");
		
		modelo.addAttribute("admAcademico", admAcademico);
		modelo.addAttribute("admSolicitud", admSolicitud);
		modelo.addAttribute("admSalud", admSalud);
		modelo.addAttribute("admPadres", admPadres);
		modelo.addAttribute("admTutor", admTutor);		
		modelo.addAttribute("paisNombre", paisNombre);
		modelo.addAttribute("estadoNombre", estadoNombre);
		modelo.addAttribute("ciudadNombre", ciudadNombre);
		modelo.addAttribute("nacionalidad", nacionalidad);
		modelo.addAttribute("edad", edad);
		modelo.addAttribute("religionNombre", religionNombre);
		modelo.addAttribute("carreraNombre", carreraNombre);
		modelo.addAttribute("ciudadDomicilio", ciudadDomicilioNombre);
		modelo.addAttribute("estadoDomicilio", estadoDomicilioNombre);
		modelo.addAttribute("paisDomicilio", paisDomicilioNombre);
		modelo.addAttribute("religionPadre", religionPadre);
		modelo.addAttribute("religionMadre", religionMadre);
		
		modelo.addAttribute("lisEstudios", lisEstudios);
		modelo.addAttribute("mapPaises", mapPaises);
		modelo.addAttribute("mapEstados", mapEstados);
		modelo.addAttribute("mapCiudades", mapCiudades);
		modelo.addAttribute("mapReligiones", mapReligiones);
		
		return "admlinea/admision/solicitudPDF";
	}

	@RequestMapping("/admlinea/admision/marcarPago")
	public String admlineaAdmisionMarcarPago(HttpServletRequest request){
		String folio 	= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String usuario	= "0";		
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			usuario = (String)sesion.getAttribute("codigoPersonal");			
		}
		
		AdmPasos pasos = new AdmPasos();
		
		pasos.setFolio(folio);
		pasos.setUsuario(usuario);
		pasos.setPasos("1");
		
		if (admPasosDao.existeReg(folio, "1")){
			admSolicitudDao.updateEstadoAndFecha(folio, "4");			
		}else {
			if (admPasosDao.insertReg(pasos)){
				admSolicitudDao.updateEstadoAndFecha(folio, "4");
			}
		}
		
		return "redirect:/admlinea/admision/mostrarProceso?Folio="+folio;
	}
	
	@RequestMapping("/admlinea/admision/editarvobo")
	public String admlineaAdmisionEditarvobo(HttpServletRequest request, Model modelo){
		String folio	 	= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String opcion	 	= request.getParameter("Opcion")==null?"0":request.getParameter("Opcion");
		String paso 		= request.getParameter("Paso")==null?"0":request.getParameter("Paso");
		
		AdmPasos admPasos 	= new AdmPasos();		
		if(admPasosDao.existeReg(folio, paso)) {
			admPasos = admPasosDao.mapeaRegId(folio, paso);
		}
		
		AdmSolicitud admSolicitud	= new AdmSolicitud();
		AdmAcademico admAcademico	= new AdmAcademico();
		
		String carreraNombre		= "-";		
		if (admSolicitudDao.existeReg(folio)) {
			admSolicitud 			= admSolicitudDao.mapeaRegId(folio);
			admAcademico			= admAcademicoDao.mapeaRegId(folio);
			carreraNombre 			= catCarreraDao.getNombreCarrera(admAcademico.getCarreraId());
		}
		
		modelo.addAttribute("folio",folio);		
		modelo.addAttribute("opcion",opcion);
		modelo.addAttribute("paso",paso);
		modelo.addAttribute("carreraNombre",carreraNombre);
		modelo.addAttribute("admPasos",admPasos);
		modelo.addAttribute("admSolicitud",admSolicitud);
		modelo.addAttribute("admAcademico",admAcademico);
		
		return "admlinea/admision/editarvobo";
	}

	@RequestMapping("/admlinea/admision/grabarvobo")
	public String admlineaAdmisionVistoBueno(HttpServletRequest request){
		String folio 		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String comentario 	= request.getParameter("Comentario")==null?"0":request.getParameter("Comentario");
		String opcion 		= request.getParameter("Opcion")==null?"0":request.getParameter("Opcion");
		String paso 		= request.getParameter("Paso")==null?"0":request.getParameter("Paso");
		String usuario		= "0";		
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			usuario = (String)sesion.getAttribute("codigoPersonal");			
		}
		
		AdmPasos pasos = new AdmPasos();
		
		pasos.setFolio(folio);
		pasos.setUsuario(usuario);
		pasos.setPasos(paso);
		pasos.setComentario(comentario);
		
		if (admPasosDao.existeReg(folio, paso)){
			admSolicitudDao.updateFecha(folio);			
		}else {
			if (admPasosDao.insertReg(pasos)){
				admSolicitudDao.updateFecha(folio);
			}
		}
		
		return "redirect:/admlinea/admision/editarvobo?Folio="+folio+"&Opcion="+opcion+"&Paso="+paso;
	}
	
	@RequestMapping("/admlinea/admision/examen")
	public String admlineaAdmisionExamen(HttpServletRequest request){
		String folio 	= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String usuario	= "0";		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			usuario = (String)sesion.getAttribute("codigoPersonal");	
		}
		AdmPasos pasos = new AdmPasos();
		pasos.setFolio(folio);
		pasos.setUsuario(usuario);
		pasos.setPasos("3");
		
		if (admPasosDao.existeReg(folio, "3")){
			admSolicitudDao.updateFecha(folio);			
		}else {
			if (admPasosDao.insertReg(pasos)){
				admSolicitudDao.updateFecha(folio);
			}
		}
		
		return "redirect:/admlinea/admision/mostrarProceso?Folio="+folio;
	}
	
	@RequestMapping("/admlinea/admision/borrarPaso")
	public String admlineaAdmisionBorrarExamen(HttpServletRequest request){
		String folio 	= request.getParameter("Folio")==null?"0":request.getParameter("Folio");		
		String paso 	= request.getParameter("Paso")==null?"0":request.getParameter("Paso");
		if (admPasosDao.existeReg(folio, paso)){
			admPasosDao.deleteReg(folio, paso);
		}	
		return "redirect:/admlinea/admision/mostrarProceso?Folio="+folio;
	}
	
	@RequestMapping("/admlinea/admision/resultados")
	public String admlineaAdmisionResultados(HttpServletRequest request){
		String folio 	= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String usuario	= "0";		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			usuario = (String)sesion.getAttribute("codigoPersonal");			
		}		
		AdmPasos pasos = new AdmPasos();		
		pasos.setFolio(folio);
		pasos.setUsuario(usuario);
		pasos.setPasos("4");	
		if (admPasosDao.existeReg(folio, "4")){
			admSolicitudDao.updateFecha(folio);			
		}else {
			if (admPasosDao.insertReg(pasos)){
				admSolicitudDao.updateFecha(folio);
			}
		}
		
		return "redirect:/admlinea/admision/mostrarProceso?Folio="+folio;
	}
	
	@RequestMapping("/admlinea/admision/mostrarProceso")
	public String admlineaAdmisionMostrarProceso(HttpServletRequest request, Model modelo){
		
		String folio 				= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		
		if (aca.util.NumberToLetter.isNumeric(folio)==false) folio = "0";
		boolean tieneSede 			= admReservadaDao.existeReservacion(folio, "'A','C'");		
		boolean esAsesor			= false;
		boolean tienePago			= false;		
		boolean tieneResultados		= false;
		boolean tieneOtros			= false;
		boolean vistoBueno			= false;
		boolean listoVoBo			= false;
		boolean voboPosgrado		= false;
		boolean tienePrueba			= false;
		boolean tieneRecomendaciones = false;
		String usuario				= "0";	
		String asesorNombre			= "-";
		String carreraNombre		= "-";
		String fechaReservacion		= "";
		int encuestasFolio			= 0;
		
		Acceso acceso 					= new Acceso();
		AdmSolicitud admSolicitud		= new AdmSolicitud();
		AdmUsuario admUsuario			= new AdmUsuario();
		AdmAsesor admAsesor				= new AdmAsesor();		
		AdmAcademico admAcademico		= new AdmAcademico();
		AdmProceso admProceso			= new AdmProceso();
		AdmPasos admPasos 				= new AdmPasos();
		AdmDirecto admDirecto			= new AdmDirecto();
		AdmCartaSubir admCartaSubir		= new AdmCartaSubir();
		AdmPruebaAlumno admPruebaAlumno	= new AdmPruebaAlumno();
		AdmPrueba admPrueba				= new AdmPrueba();
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			if (!folio.equals("0")) sesion.setAttribute("folio", folio);
			usuario 			= (String)sesion.getAttribute("codigoPersonal");		
			acceso 				= accesoDao.mapeaRegId(usuario);	
		}
		if (admSolicitudDao.existeReg(folio)) {
			admSolicitud 		= admSolicitudDao.mapeaRegId(folio);
			
			admUsuario 			= admUsuarioDao.mapeaRegId(admSolicitud.getUsuarioId());
			admAsesor			= admAsesorDao.mapeaRegId(admSolicitud.getAsesorId());			
			admAcademico		= admAcademicoDao.mapeaRegId(folio);
			admProceso			= admProcesoDao.mapeaRegConHoras(folio);			
			admDirecto			= admDirectoDao.mapeaRegId(folio);
			asesorNombre 		= usuariosDao.getNombreUsuario(admSolicitud.getAsesorId(), "NOMBRE");
			encuestasFolio		= admEncuestaDao.numEncuestasFolio(folio);
			carreraNombre 		= catCarreraDao.getNombreCarrera(admAcademico.getCarreraId());
			tienePrueba 		= admPruebaAlumnoDao.existeReg(folio);
			
			if (admAsesorDao.existeReg(usuario)){
				esAsesor 		= true;
			}		
			if (admPasosDao.existeReg(folio,"1")){
				tienePago 		= true;
			}
			if (admPasosDao.existeReg(folio,"2")){
				vistoBueno 		= true;
			}				
			if (admPasosDao.existeReg(folio,"3")){
				tieneOtros 		= true;
			}
			if (admReservadaDao.existeReservacion(folio, "'A','C'")){
				fechaReservacion	= admReservadaDao.mapeaRegId(folio).getFecha();				
			}			
			if (admPasosDao.existeReg(folio,"4")){
				tieneResultados	= true;
			}
			if(admPasosDao.existeReg(folio, "5")){
				voboPosgrado = true;
			}
			if(admPasosDao.existeReg(folio, "7")){
				listoVoBo = true;
			}
		}
		
		if (tienePrueba) {
			admPruebaAlumno = admPruebaAlumnoDao.mapeaRegId(folio);
			admPrueba = admPruebaDao.mapeaRegId(admPruebaAlumno.getPruebaId());
		}
	
		boolean nivelPosgrado = false;
		if(admAcademico.getNivelId().equals("9")){
			nivelPosgrado = true;
		}
				
		if(admPasosDao.existeReg(folio, "2")) {
			admPasos = admPasosDao.mapeaRegId(folio, "2");
		}

		if(admCartaSubirDao.existeReg(folio)) {
			admCartaSubir = admCartaSubirDao.mapeaRegId(folio);
		}

		if(admDirectoDao.existeReg(folio)) {
			admDirecto = admDirectoDao.mapeaRegId(folio);
		}
		
		List<AdmEvaluacion> listaEvaluaciones 		= admEvaluacionDao.getListAll(" ORDER BY EVALUACION_ID");
		List<String> listaAcceso					= admAccesoVoboDao.listAccesoCarrea(admAcademico.getCarreraId());
		List<AdmAsesor> listaAsesores				= admAsesorDao.lisAsesores("'A'");
		HashMap<String,String> mapaMaestros			= maestrosDao.mapaMaestroCorto("NOMBRE");		
		HashMap<String,AdmEvaluacionNota> mapaNotas	= admEvaluacionNotaDao.mapaNotaResultados(folio);
		HashMap<String,AdmPasos> mapaPasos			= admPasosDao.mapaPasos(folio);	
		List<AdmPago> lisPagos						= admPagoDao.listPagos(folio);	
		
		int cantidadDocs 	= 0;
		
		if(admDocAlumDao.tieneDocs(folio)) {
			cantidadDocs 	= admDocAlumDao.cantidadDocs(folio);
		}
		
		int cantidadEncuesta = admEncuestaDao.numEncuestasFolio(folio);

		if(admRecomiendaDao.existeReg(folio, "1")){
			tieneRecomendaciones = true;
		}
		
		modelo.addAttribute("usuario",usuario);
		modelo.addAttribute("acceso",acceso);
		modelo.addAttribute("tieneSede",tieneSede);
		modelo.addAttribute("encuestasFolio",encuestasFolio);
		modelo.addAttribute("esAsesor",esAsesor);
		modelo.addAttribute("tienePago",tienePago);
		modelo.addAttribute("vistoBueno",vistoBueno);
		modelo.addAttribute("voboPosgrado",voboPosgrado);
		modelo.addAttribute("tieneOtros",tieneOtros);
		modelo.addAttribute("tieneResultados",tieneResultados);
		modelo.addAttribute("tieneRecomendaciones",tieneRecomendaciones);
		modelo.addAttribute("asesorNombre", asesorNombre);
		modelo.addAttribute("carreraNombre", carreraNombre);
		modelo.addAttribute("listaEvaluaciones", listaEvaluaciones);
		modelo.addAttribute("listaAcceso", listaAcceso);
		modelo.addAttribute("listaAsesores", listaAsesores);
		modelo.addAttribute("mapaMaestros", mapaMaestros);
		modelo.addAttribute("nivelPosgrado", nivelPosgrado);
		modelo.addAttribute("mapaNotas", mapaNotas);
		modelo.addAttribute("mapaPasos", mapaPasos);
		modelo.addAttribute("listoVoBo", listoVoBo);
		modelo.addAttribute("lisPagos", lisPagos);
		
		modelo.addAttribute("admDirecto",admDirecto);
		modelo.addAttribute("admCartaSubir",admCartaSubir);
		modelo.addAttribute("admSolicitud",admSolicitud);
		modelo.addAttribute("admUsuario",admUsuario);
		modelo.addAttribute("admAsesor",admAsesor);		
		modelo.addAttribute("admAcademico",admAcademico);
		modelo.addAttribute("admProceso",admProceso);
		modelo.addAttribute("admPasos",admPasos);		
		
		modelo.addAttribute("tienePrueba",tienePrueba);
		modelo.addAttribute("admPrueba",admPrueba);
		modelo.addAttribute("admPruebaAlumno",admPruebaAlumno);
		
		modelo.addAttribute("numComentarios",admComentarioDao.cantidadComentarios(folio,"'A'"));
		modelo.addAttribute("cantidadDocs",cantidadDocs);
		modelo.addAttribute("cantidadEncuesta", cantidadEncuesta);
		modelo.addAttribute("fechaReservacion", fechaReservacion);		
		
		return "admlinea/admision/mostrarProceso";
	}
	
	@RequestMapping("/admlinea/admision/referencias")
	public String portalesAlumnoReferencias(HttpServletRequest request, Model modelo) {
		
		String folio 			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
        String cuenta			= "5";
        
		String digito 			= alumReferenciaDao.generarReferenciaSantander(cuenta+folio);
		String nombre			= "";
		String numCuenta		= "25300000361";
		boolean tieneScotiabank	= false;
		boolean tieneSantander 	= false;
		boolean tieneBanorte	= false;
		boolean esCovoprom 		= false;
		
		AdmSolicitud admSolicitud	= new AdmSolicitud();
		
		if (admSolicitudDao.existeReg(folio)) {
			admSolicitud 		= admSolicitudDao.mapeaRegId(folio);
			nombre = admSolicitud.getNombre()+" "+(admSolicitud.getApellidoMaterno()==null?"":admSolicitud.getApellidoMaterno())+" "+admSolicitud.getApellidoPaterno();
		}
		
		String institucion 		= parametrosDao.mapeaRegId("1").getInstitucion();
		
		AlumReferencia alumno = new AlumReferencia();
		alumno.setCodigoPersonal(cuenta+folio);
		if (alumReferenciaDao.existeReg(cuenta+folio) ){
			
			alumno = alumReferenciaDao.mapeaRegId(cuenta+folio);
			tieneScotiabank 	= alumno.getScotiabank()!=null&&!alumno.getScotiabank().equals("-");			
			tieneSantander 		= alumno.getSantander()!=null&&!alumno.getSantander().equals("-");
			// Es la misma referencia de Scotiabank
			tieneBanorte	 	= alumno.getScotiabank()!=null&&!alumno.getScotiabank().equals("-");
			
			if (tieneScotiabank==false || tieneSantander ==false){		
				// Se actualiza el que no exista
				if (tieneScotiabank==false) alumno.setScotiabank(digito);
				if (tieneSantander==false) alumno.setSantander(digito);
				if (alumReferenciaDao.updateReg(alumno)){
					tieneScotiabank = true;	
					tieneSantander 	= true;
				}
			}			
		}else{			
			alumno.setBanamex("-");
			alumno.setScotiabank(digito);
			alumno.setSantander(digito);
			if (alumReferenciaDao.insertReg(alumno)){
				tieneScotiabank = true;
				tieneSantander 	= true;
				tieneBanorte 	= true;
			}
		}
		
		String tmp = cuenta+folio+alumno.getScotiabank();
		String agrega = "0";

		for(int i = 0; i < 9; i++) {
			if(tmp.length() < 9) {
				tmp = folio;
				tmp = cuenta+agrega+tmp+alumno.getScotiabank();
				agrega = agrega+agrega;
			}else {
				break;
			}
		}
		
		String referencia = tmp;
		
		modelo.addAttribute("SubMenu", "5");
		modelo.addAttribute("cuenta", cuenta);
		modelo.addAttribute("nombre", nombre);
		modelo.addAttribute("numCuenta", numCuenta);
		modelo.addAttribute("tieneScotiabank", tieneScotiabank);
		modelo.addAttribute("tieneSantander", tieneSantander);
		modelo.addAttribute("tieneBanorte", tieneBanorte);
		modelo.addAttribute("esCovoprom", esCovoprom);
		modelo.addAttribute("institucion", institucion);
		modelo.addAttribute("alumno", alumno);
		modelo.addAttribute("referencia", referencia);
		
		return "admlinea/admision/referencias";
	}
	
	@RequestMapping("/admlinea/admision/resultadosEvaluacion")
	public String admlineaAdmisionResultados(HttpServletRequest request, Model modelo){
		
		String examenId			= request.getParameter("ExamenId")==null?"0":request.getParameter("ExamenId");
		String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String nombre			= admSolicitudDao.getNombre(folio);
		AdmAcademico adm 		= admAcademicoDao.mapeaRegId(folio);	
		AdmPrueba admPrueba				= new AdmPrueba();
		String carreraNivel		= catCarreraDao.getNivelId(adm.getCarreraId());
		String carreraNombre	= "-";
		boolean tienePrueba			= false;
		AdmPruebaAlumno admPruebaAlumno	= new AdmPruebaAlumno();


		float resLog 	= 0;		
		float resMat 	= 0;		
		float resEsp 	= 0;		
		float resIng 	= 0;
		float resBio 	= 0;
		float resFis 	= 0;
		float resQui 	= 0;
		float resEnsayo = 0;
		
		boolean pregrado = true;
		
		Aspirante aspirante = new Aspirante();
		if(aspiranteDao.existeAspirantePorFolio(Integer.parseInt(folio))) {
			aspirante = aspiranteDao.buscaAspirantePorFolio(Integer.parseInt(folio));
		}
		
		if(aspirante.getGrado().equals("P")) {
			pregrado = false;
		}
		if (admSolicitudDao.existeReg(folio)) {
			
			tienePrueba 		= admPruebaAlumnoDao.existeReg(folio);
		}
	
		if (tienePrueba) {
			admPruebaAlumno = admPruebaAlumnoDao.mapeaRegId(folio);
			admPrueba = admPruebaDao.mapeaRegId(admPruebaAlumno.getPruebaId());
		}
		
		List<Examen> lisExamenes						= examenDao.lisExamenesPorFolio(Integer.parseInt(folio), "ORDER BY FECHA DESC");

		
		if (examenId.equals("0") && lisExamenes.size() >= 1) {
			examenId = String.valueOf(lisExamenes.get(0).getId());
		}
		if (pregrado == false){
			resLog 		= examenAreaDao.getPuntosPorArea(Integer.parseInt(examenId), "1,15,16");		
			resMat 		= examenAreaDao.getPuntosPorArea(Integer.parseInt(examenId), "2,17,18");		
			resEsp 		= examenAreaDao.getPuntosPorArea(Integer.parseInt(examenId), "3,4,14,19");		
			resIng 		= examenAreaDao.getPuntosPorArea(Integer.parseInt(examenId), "5,6");
			resBio 		= examenAreaDao.getPuntosPorArea(Integer.parseInt(examenId), "7");
			resFis 		= examenAreaDao.getPuntosPorArea(Integer.parseInt(examenId), "8");
			resQui 		= examenAreaDao.getPuntosPorArea(Integer.parseInt(examenId), "9");
			resEnsayo 	= examenAreaDao.getPuntosPorArea(Integer.parseInt(examenId), "12,20,21");
		}
		
		if (pregrado) {
			if (lisExamenes.size() >= 1) {
				for(Examen examen : lisExamenes) {
					if(resLog <= examenAreaDao.getPuntosPorArea(examen.getId(), "1,15,16")) {
						resLog = examenAreaDao.getPuntosPorArea(examen.getId(), "1,15,16");		
					}
					if(resMat <= examenAreaDao.getPuntosPorArea(examen.getId(), "2,17,18")) {
						resMat 	= examenAreaDao.getPuntosPorArea(examen.getId(), "2,17,18");		
					}
					if(resEsp <= examenAreaDao.getPuntosPorArea(examen.getId(), "3,4,14,19")) {
						resEsp 	= examenAreaDao.getPuntosPorArea(examen.getId(), "3,4,14,19");		
					}
					if(resIng <= examenAreaDao.getPuntosPorArea(examen.getId(), "5,6")) {
						resIng 	= examenAreaDao.getPuntosPorArea(examen.getId(), "5,6");
					}
					if(resBio <= examenAreaDao.getPuntosPorArea(examen.getId(), "7")) {
						resBio 	= examenAreaDao.getPuntosPorArea(examen.getId(), "7");
					}
					if(resFis <= examenAreaDao.getPuntosPorArea(examen.getId(), "8")) {
						resFis 	= examenAreaDao.getPuntosPorArea(examen.getId(), "8");
					}
					if(resQui <= examenAreaDao.getPuntosPorArea(examen.getId(), "9")) {
						resQui 	= examenAreaDao.getPuntosPorArea(examen.getId(), "9");
					}
					if(resEnsayo <= examenAreaDao.getPuntosPorArea(examen.getId(), "12,20,21")) {
						resEnsayo = examenAreaDao.getPuntosPorArea(examen.getId(), "12,20,21");
					}
				}
			}else if (lisExamenes.size() == 1) {
				examenId = String.valueOf(lisExamenes.get(0).getId());
				
				resLog 	= examenAreaDao.getPuntosPorArea(Integer.parseInt(examenId), "1,15,16");		
				resMat 	= examenAreaDao.getPuntosPorArea(Integer.parseInt(examenId), "2,17,18");		
				resEsp 	= examenAreaDao.getPuntosPorArea(Integer.parseInt(examenId), "3,4,14,19");		
				resIng 	= examenAreaDao.getPuntosPorArea(Integer.parseInt(examenId), "5,6");
				resBio 	= examenAreaDao.getPuntosPorArea(Integer.parseInt(examenId), "7");
				resFis 	= examenAreaDao.getPuntosPorArea(Integer.parseInt(examenId), "8");
				resQui 	= examenAreaDao.getPuntosPorArea(Integer.parseInt(examenId), "9");
				resEnsayo = examenAreaDao.getPuntosPorArea(Integer.parseInt(examenId), "12,20,21");
			}
		}
		
		HashMap<String,AdmEvaluacionNota> mapaNotas		= admEvaluacionNotaDao.mapaNotaResultados();
		HashMap<Integer,ExaArea> mapaAreas				= exaAreaDao.mapaArea();
		
		float minLogPre	= 0;
		float minLogPos	= 0;
		if (mapaAreas.containsKey(15)){	
			minLogPre  = mapaAreas.get(15).getMinimoPre() * mapaAreas.get(15).getPuntosPre();	
			minLogPos  = mapaAreas.get(15).getMinimoPos() * mapaAreas.get(15).getPuntosPos();	
		}else if (mapaAreas.containsKey(16)){	
			minLogPre  = mapaAreas.get(16).getMinimoPre() * mapaAreas.get(16).getPuntosPre();	
			minLogPos  = mapaAreas.get(16).getMinimoPos() * mapaAreas.get(16).getPuntosPos();	
		}else if (mapaAreas.containsKey(1)){	
			minLogPre  = mapaAreas.get(1).getMinimoPre() * mapaAreas.get(1).getPuntosPre();	
			minLogPos  = mapaAreas.get(1).getMinimoPos() * mapaAreas.get(1).getPuntosPos();	
		}
		float minMatPre	= 0;
		float minMatPos	= 0;
		if (mapaAreas.containsKey(2)){	
			minMatPre  = mapaAreas.get(2).getMinimoPre() * mapaAreas.get(2).getPuntosPre();
			minMatPos  = mapaAreas.get(2).getMinimoPos() * mapaAreas.get(2).getPuntosPos();
		} else if (mapaAreas.containsKey(17)){	
			minMatPre  = mapaAreas.get(17).getMinimoPre() * mapaAreas.get(17).getPuntosPre();
			minMatPos  = mapaAreas.get(17).getMinimoPos() * mapaAreas.get(17).getPuntosPos();
		} else if (mapaAreas.containsKey(18)){	
			minMatPre  = mapaAreas.get(18).getMinimoPre() * mapaAreas.get(18).getPuntosPre();
			minMatPos  = mapaAreas.get(18).getMinimoPos() * mapaAreas.get(18).getPuntosPos();
		}			
		float minEspPre	= 0;
		float minEspPos	= 0;
		if (mapaAreas.containsKey(3)){	
			minEspPre  = mapaAreas.get(3).getMinimoPre() * mapaAreas.get(3).getPuntosPre();	
			minEspPos  = mapaAreas.get(3).getMinimoPos() * mapaAreas.get(3).getPuntosPos();	
		}else if (mapaAreas.containsKey(14)){	
			minEspPre  = mapaAreas.get(14).getMinimoPre() * mapaAreas.get(14).getPuntosPre();	
			minEspPos  = mapaAreas.get(14).getMinimoPos() * mapaAreas.get(14).getPuntosPos();	
		}
		if (mapaAreas.containsKey(4)){	
			minEspPre += mapaAreas.get(4).getMinimoPre() * mapaAreas.get(4).getPuntosPre();	
			minEspPos += mapaAreas.get(4).getMinimoPos() * mapaAreas.get(4).getPuntosPos();	
		} else if (mapaAreas.containsKey(19)){	
			minEspPre += mapaAreas.get(19).getMinimoPre() * mapaAreas.get(19).getPuntosPre();	
			minEspPos += mapaAreas.get(19).getMinimoPos() * mapaAreas.get(19).getPuntosPos();	
		}			
		float minIngPre	= 0;
		float minIngPos	= 0;
		if (mapaAreas.containsKey(3)){
			minIngPre  = mapaAreas.get(5).getMinimoPre() * mapaAreas.get(5).getPuntosPre();
			minIngPos  = mapaAreas.get(5).getMinimoPos() * mapaAreas.get(5).getPuntosPos();
		}
		if (mapaAreas.containsKey(4)){	
			minIngPre += mapaAreas.get(6).getMinimoPre() * mapaAreas.get(6).getPuntosPre();
			minIngPos += mapaAreas.get(6).getMinimoPos() * mapaAreas.get(6).getPuntosPos();
		}		
		float minBioPre	= 0;
		float minBioPos	= 0;
		if (mapaAreas.containsKey(7)){
			minBioPre  = mapaAreas.get(7).getMinimoPre() * mapaAreas.get(7).getPuntosPre();
			minBioPos  = mapaAreas.get(7).getMinimoPos() * mapaAreas.get(7).getPuntosPos();
		}		
		float minFisPre	= 0;
		float minFisPos	= 0;
		if (mapaAreas.containsKey(8)){
			minFisPre  = mapaAreas.get(8).getMinimoPre() * mapaAreas.get(8).getPuntosPre();
			minFisPos  = mapaAreas.get(8).getMinimoPos() * mapaAreas.get(8).getPuntosPos();
		}		
		float minQuiPre	= 0;
		float minQuiPos	= 0;
		if (mapaAreas.containsKey(8)){
			minQuiPre  = mapaAreas.get(9).getMinimoPre() * mapaAreas.get(9).getPuntosPre();
			minQuiPos  = mapaAreas.get(9).getMinimoPos() * mapaAreas.get(9).getPuntosPos();
		}
		
		boolean paseLog	= false;		boolean paseMat	= false;		boolean paseEsp	= false;
		boolean paseIng	= false;		boolean paseBio	= false;		boolean paseFis	= false;		
		boolean paseQui	= false;
		
		boolean tieneLog= false;		boolean tieneMat= false;		boolean tieneEsp= false;
		boolean tieneIng= false;		boolean tieneBio= false;		boolean tieneFis= false;
		boolean tieneQui= false;

		float nota = 0;
		for (Examen exa : lisExamenes) {
			
			nota = examenAreaDao.getPuntosPorArea(exa.getId(), "1,15,16");
			if (nota >= 1) tieneLog 	= true;
			if ( (pregrado && nota >= minLogPre) || (pregrado==false && nota >= minLogPos) ) paseLog = true;
			
			nota = examenAreaDao.getPuntosPorArea(exa.getId(), "2,17,18");
			if (nota >= 1) tieneMat 	= true;
			if ( (pregrado && nota >= minMatPre) || (pregrado==false && nota >= minMatPos) ) paseMat = true;
			
			nota = examenAreaDao.getPuntosPorArea(exa.getId(), "3,4,14,19");
			if (nota >= 1) tieneEsp 	= true;
			if ( (pregrado && nota >= minEspPre) || (pregrado==false && nota >= minEspPos) ) paseEsp = true;
			
			nota = examenAreaDao.getPuntosPorArea(exa.getId(), "5,6");
			if (nota >= 1) tieneIng 	= true;
			if ( (pregrado && nota >= minIngPre) || (pregrado==false && nota >= minIngPos) ) paseIng = true;
			
			nota = examenAreaDao.getPuntosPorArea(exa.getId(), "7");
			if (nota >= 1) tieneBio 	= true;
			if ( (pregrado && nota >= minBioPre) || (pregrado==false && nota >= minBioPos) ) paseBio = true;
			
			nota = examenAreaDao.getPuntosPorArea(exa.getId(), "8");
			if (nota >= 1) tieneFis 	= true;
			if ( (pregrado && nota >= minFisPre) || (pregrado==false && nota >= minFisPos) ) paseFis = true;
			
			nota = examenAreaDao.getPuntosPorArea(exa.getId(), "9");
			if (nota >= 1) tieneQui 	= true;
			if ( (pregrado && nota >= minQuiPre) || (pregrado==false && nota >= minQuiPos) ) paseQui = true;			
		}
		
		if(resEnsayo > 0) {
			resEsp = resEsp + resEnsayo;
		}
		
		carreraNombre       = catCarreraDao.getNombreCarrera(adm.getCarreraId());
		
		modelo.addAttribute("examenId", examenId);
		modelo.addAttribute("folio", folio);
		modelo.addAttribute("nombre", nombre);
		modelo.addAttribute("carreraNivel", carreraNivel);
		modelo.addAttribute("adm", adm);
		modelo.addAttribute("carreraNombre", carreraNombre);
		modelo.addAttribute("resLog", resLog);		
		modelo.addAttribute("resMat", resMat);
		modelo.addAttribute("resEsp", resEsp);
		modelo.addAttribute("resIng", resIng);
		modelo.addAttribute("resBio", resBio);
		modelo.addAttribute("resFis", resFis);
		modelo.addAttribute("resQui", resQui);
		modelo.addAttribute("resEnsayo", resEnsayo);
		
		modelo.addAttribute("paseLog", paseLog);		
		modelo.addAttribute("paseMat", paseMat);
		modelo.addAttribute("paseEsp", paseEsp);
		modelo.addAttribute("paseIng", paseIng);
		modelo.addAttribute("paseBio", paseBio);
		modelo.addAttribute("paseFis", paseFis);
		modelo.addAttribute("paseQui", paseQui);
		
		modelo.addAttribute("tieneLog", tieneLog);		
		modelo.addAttribute("tieneMat", tieneMat);
		modelo.addAttribute("tieneEsp", tieneEsp);
		modelo.addAttribute("tieneIng", tieneIng);
		modelo.addAttribute("tieneBio", tieneBio);
		modelo.addAttribute("tieneFis", tieneFis);
		modelo.addAttribute("tieneQui", tieneQui);		

		modelo.addAttribute("pregrado", pregrado);		
		modelo.addAttribute("admPrueba", admPrueba);		
		
		modelo.addAttribute("tienePrueba", tienePrueba);		
		modelo.addAttribute("lisExamenes", lisExamenes);
		modelo.addAttribute("mapaNotas", mapaNotas);
		modelo.addAttribute("mapaAreas", mapaAreas);
		
		return "admlinea/admision/resultadosEvaluacion";		
	}	
	
	@RequestMapping("/admlinea/admision/resultadosPDF")
	public String resultadoPDF(HttpServletRequest request, Model modelo){
		
		String examenId		= request.getParameter("ExamenId")==null?"0":request.getParameter("ExamenId");
		String folio 		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");				
		String fecha 		= examenDao.fechaExamen(examenId);		
		String nombre		= admSolicitudDao.getNombre(folio);
		
		AdmAcademico admAcademico 			= admAcademicoDao.mapeaRegId(folio);
		String nombreCarrera				= catCarreraDao.getNombreCarrera(admAcademico.getCarreraId());
		String nivelCarrera					= catCarreraDao.getNivelId(admAcademico.getCarreraId());
		HashMap<Integer,ExaArea> mapaAreas	= exaAreaDao.mapaArea();
		
		float resLog 		= 0;		
		float resMat 		= 0;		
		float resEsp 		= 0;		
		float resIng 		= 0;
		float resBio 		= 0;
		float resFis 		= 0;
		float resQui 		= 0;
		
		List<Examen> lisExamenes = examenDao.lisExamenesPorFolio(Integer.parseInt(folio), "ORDER BY FECHA DESC");
		
		if (lisExamenes.size() >= 1) {
			for(Examen examen : lisExamenes) {
				if(resLog <= examenAreaDao.getPuntosPorArea(examen.getId(), "1,15,16")) {
					resLog = examenAreaDao.getPuntosPorArea(examen.getId(), "1,15,16");		
				}
				if(resMat <= examenAreaDao.getPuntosPorArea(examen.getId(), "2,17,18")) {
					resMat 	= examenAreaDao.getPuntosPorArea(examen.getId(), "2,17,18");		
				}
				if(resEsp <= examenAreaDao.getPuntosPorArea(examen.getId(), "3,4,14,19")) {
					resEsp 	= examenAreaDao.getPuntosPorArea(examen.getId(), "3,4,14,19");		
				}
				if(resIng <= examenAreaDao.getPuntosPorArea(examen.getId(), "5,6")) {
					resIng 	= examenAreaDao.getPuntosPorArea(examen.getId(), "5,6");
				}
				if(resBio <= examenAreaDao.getPuntosPorArea(examen.getId(), "7")) {
					resBio 	= examenAreaDao.getPuntosPorArea(examen.getId(), "7");
				}
				if(resFis <= examenAreaDao.getPuntosPorArea(examen.getId(), "8")) {
					resFis 	= examenAreaDao.getPuntosPorArea(examen.getId(), "8");
				}
				if(resQui <= examenAreaDao.getPuntosPorArea(examen.getId(), "9")) {
					resQui 	= examenAreaDao.getPuntosPorArea(examen.getId(), "9");
				}
			}
		}else if (lisExamenes.size() == 1) {
			examenId = String.valueOf(lisExamenes.get(0).getId());
			
			resLog 	= examenAreaDao.getPuntosPorArea(Integer.parseInt(examenId), "1,15,16");		
			resMat 	= examenAreaDao.getPuntosPorArea(Integer.parseInt(examenId), "2,17,18");		
			resEsp 	= examenAreaDao.getPuntosPorArea(Integer.parseInt(examenId), "3,4,14,19");		
			resIng 	= examenAreaDao.getPuntosPorArea(Integer.parseInt(examenId), "5,6");
			resBio 	= examenAreaDao.getPuntosPorArea(Integer.parseInt(examenId), "7");
			resFis 	= examenAreaDao.getPuntosPorArea(Integer.parseInt(examenId), "8");
			resQui 	= examenAreaDao.getPuntosPorArea(Integer.parseInt(examenId), "9");
		}
		
		modelo.addAttribute("examenId", examenId);
		modelo.addAttribute("folio", folio);
		modelo.addAttribute("admAcademico", admAcademico);
		modelo.addAttribute("nombreCarrera", nombreCarrera);
		modelo.addAttribute("nivelCarrera", nivelCarrera);
		modelo.addAttribute("nombre", nombre);
		modelo.addAttribute("resLog", resLog);		
		modelo.addAttribute("resMat", resMat);
		modelo.addAttribute("resEsp", resEsp);
		modelo.addAttribute("resIng", resIng);
		modelo.addAttribute("resBio", resBio);
		modelo.addAttribute("resFis", resFis);
		modelo.addAttribute("resQui", resQui);
		modelo.addAttribute("mapaAreas", mapaAreas);
		modelo.addAttribute("fecha", fecha);
		
		return "admlinea/admision/resultadosPDF";		
	}	
	
	@RequestMapping("/admlinea/admision/resultadosPDF3")
	public String resultadoPDF3(HttpServletRequest request, Model modelo){
		
		String examenId		= request.getParameter("ExamenId")==null?"0":request.getParameter("ExamenId");
		String folio 		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");				
		String fecha 		= examenDao.fechaExamen(examenId);		
		String nombre		= admSolicitudDao.getNombre(folio);
		
		AdmAcademico admAcademico 			= admAcademicoDao.mapeaRegId(folio);
		String nombreCarrera				= catCarreraDao.getNombreCarrera(admAcademico.getCarreraId());
		HashMap<Integer,ExaArea> mapaAreas	= exaAreaDao.mapaArea();
		
		float resLog 	= 0;		
		float resMat 	= 0;		
		float resEsp 	= 0;		
		float resIng 	= 0;
		float resEnsayo = 0;
			
		resLog 	= examenAreaDao.getPuntosPorArea(Integer.parseInt(examenId), "1,15,16");		
		resMat 	= examenAreaDao.getPuntosPorArea(Integer.parseInt(examenId), "2,17,18");		
		resEsp 	= examenAreaDao.getPuntosPorArea(Integer.parseInt(examenId), "3,4,14,19");		
		resIng 	= examenAreaDao.getPuntosPorArea(Integer.parseInt(examenId), "5,6");
		resEnsayo = examenAreaDao.getPuntosPorArea(Integer.parseInt(examenId), "12");
		
		if(resEnsayo > 0) {
			resEsp = resEsp + resEnsayo;
		}
		
		modelo.addAttribute("examenId", examenId);
		modelo.addAttribute("folio", folio);
		modelo.addAttribute("admAcademico", admAcademico);
		modelo.addAttribute("nombreCarrera", nombreCarrera);
		modelo.addAttribute("nombre", nombre);
		modelo.addAttribute("resLog", resLog);		
		modelo.addAttribute("resMat", resMat);
		modelo.addAttribute("resEsp", resEsp);
		modelo.addAttribute("resIng", resIng);
		modelo.addAttribute("mapaAreas", mapaAreas);
		modelo.addAttribute("fecha", fecha);
		
		return "admlinea/admision/resultadosPDF3";		
	}	
	
	@RequestMapping("/admlinea/admision/ingresoDirecto")
	public String admlineaAdmisionIngresoDirecto(HttpServletRequest request, Model model){
		String folio 		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String promedio 	= "";			
		String nombreAlumno	= "";	
		String nombrePlan	= "";	
		
		AdmDirecto admDirecto 		= new AdmDirecto();
		
		if(admDirectoDao.existeReg(folio)) {
			admDirecto 		= admDirectoDao.mapeaRegId(folio);
			promedio 		= admDirectoDao.promedio(admDirecto.getMatricula(), admDirecto.getPlanId());
			nombreAlumno 	= admDirectoDao.nombreMatricula(admDirecto.getMatricula());
			nombrePlan 		= mapaPlanDao.getNombrePlan(admDirecto.getPlanId());
		}

		model.addAttribute("admDirecto",admDirecto);
		model.addAttribute("promedio",promedio);
		model.addAttribute("nombreAlumno",nombreAlumno);
		model.addAttribute("nombrePlan",nombrePlan);
		
		return "admlinea/admision/ingresoDirecto";
	}
	
	@RequestMapping("/admlinea/admision/reciente")
	public String admlineaAdmisionReciente(HttpServletRequest request){
		String folio = request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String aprobar = request.getParameter("Aprobar")==null?"0":request.getParameter("Aprobar");
		
		AdmDirecto admDirecto = new AdmDirecto();
		
		if(admDirectoDao.existeReg(folio)) {
			admDirecto 	= admDirectoDao.mapeaRegId(folio);
			
			admDirecto.setReciente(aprobar);
			
			admDirectoDao.updateReg(admDirecto);
		}
		
		return "redirect:/admlinea/admision/ingresoDirecto?Folio="+folio;
	}
	
	@RequestMapping("/admlinea/admision/tetra")
	public String admlineaAdmisionTetra(HttpServletRequest request){
		String folio = request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String aprobar = request.getParameter("Aprobar")==null?"0":request.getParameter("Aprobar");
		
		AdmDirecto admDirecto = new AdmDirecto();
		
		if(admDirectoDao.existeReg(folio)) {
			admDirecto 	= admDirectoDao.mapeaRegId(folio);
			
			admDirecto.setTetra(aprobar);
			
			admDirectoDao.updateReg(admDirecto);
		}
		
		return "redirect:/admlinea/admision/ingresoDirecto?Folio="+folio;
	}
	
	@RequestMapping("/admlinea/admision/prepaValido")
	public String admlineaAdmisionPrepaValido(HttpServletRequest request){
		String folio = request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String aprobar = request.getParameter("Aprobar")==null?"0":request.getParameter("Aprobar");
		
		AdmDirecto admDirecto = new AdmDirecto();
		
		if(admDirectoDao.existeReg(folio)) {
			admDirecto 	= admDirectoDao.mapeaRegId(folio);
			
			admDirecto.setPrepaValido(aprobar);
			
			admDirectoDao.updateReg(admDirecto);
		}
		
		return "redirect:/admlinea/admision/ingresoDirecto?Folio="+folio;
	}
	
	@RequestMapping("/admlinea/admision/vreValido")
	public String admlineaAdmisionVreValido(HttpServletRequest request){
		String folio = request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String aprobar = request.getParameter("Aprobar")==null?"0":request.getParameter("Aprobar");
		
		AdmDirecto admDirecto = new AdmDirecto();
		
		if(admDirectoDao.existeReg(folio)) {
			admDirecto 	= admDirectoDao.mapeaRegId(folio);
			
			admDirecto.setVreValido(aprobar);
			
			admDirectoDao.updateReg(admDirecto);
		}
		
		return "redirect:/admlinea/admision/ingresoDirecto?Folio="+folio;
	}
	
	@RequestMapping(value={"/admlinea/admision/bajarRecPrepa"})
	public void admlineaAdmisionBajarRecPrepa(HttpServletRequest request, HttpServletResponse response){
		String folio 	= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		byte[] archivo	= null;
		
		AdmDirecto admDirecto = new AdmDirecto();
		
		if(admDirectoDao.existeReg(folio)) {
			admDirecto 	= admDirectoDao.mapeaRegId(folio);
			archivo = admDirecto.getRecPrepa();
		}

		try{			
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition","attachment; filename=\""+folio+"-"+admDirecto.getNombrePrepa()+"\"");	
			response.getOutputStream().write(archivo);
			response.flushBuffer();			
		}catch(Exception ex){
			System.out.println("Error /admlineaAdmisionBajarRecPrepa:"+ex);
		}
	}
	@RequestMapping(value={"/admlinea/admision/bajarRecVre"})
	public void admlineaAdmisionBajarRecVre(HttpServletRequest request, HttpServletResponse response){
		String folio 	= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		byte[] archivo	= null;
		
		AdmDirecto admDirecto = new AdmDirecto();
		
		if(admDirectoDao.existeReg(folio)) {
			admDirecto 	= admDirectoDao.mapeaRegId(folio);
			archivo = admDirecto.getRecVre();
		}
		
		try{			
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition","attachment; filename=\""+folio+"-"+admDirecto.getNombreVre()+"\"");	
			response.getOutputStream().write(archivo);
			response.flushBuffer();			
		}catch(Exception ex){
			System.out.println("Error /admlineaAdmisionBajarRecVre:"+ex);
		}
	}

	@RequestMapping("/admlinea/admision/grabarFecha")
	public String admlineaAdmisionGrabarFecha(HttpServletRequest request){
		String folio 		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		
		if (admSolicitudDao.existeReg(folio)) {
			admSolicitudDao.updateFecha(folio);
		}
		
		return "redirect:/admlinea/admision/mostrarProceso?Folio="+folio;
	}
	
	@RequestMapping("/admlinea/admision/subirCartaAdmision")
	public String admlineaAdmisionSubirCartaAdmision(HttpServletRequest request, Model modelo){
		String folio 		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		
		AdmCartaSubir carta = new AdmCartaSubir();
		Parametros parametros = parametrosDao.mapeaRegId("1");
		
		carta.setFolio(folio);
		
		if (admCartaSubirDao.existeReg(folio)) {
			carta = admCartaSubirDao.mapeaRegId(folio);
			
		}
		
		modelo.addAttribute("carta",carta);
		modelo.addAttribute("parametros", parametros);
		
		return "admlinea/admision/subirCartaAdmision";
	}
	
	@RequestMapping("/admlinea/admision/grabarCartaAdmision")
	public String admlineaAdmisionGrabarCartaAdmision(@RequestParam("archivo") MultipartFile file, HttpServletRequest request){
		String folio = request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		
		AdmCartaSubir carta = new AdmCartaSubir();
		
		try {
			carta.setFolio(folio);
			carta.setNombre( file.getOriginalFilename() );
			carta.setCarta(file.getBytes());
			carta.setFecha(aca.util.Fecha.getHoy());
			
			if(admCartaSubirDao.existeReg(folio)){
				admCartaSubirDao.updateReg(carta);
			}else{
				admCartaSubirDao.insertReg(carta);
			}
			
		}catch(Exception ex){
			
		}

		return "redirect:/admlinea/admision/mostrarProceso?Folio="+folio;
	}
	
	@RequestMapping("/admlinea/admision/borrarCartaAdmision")
	public String admlineaAdmisionBorrarCartaAdmision(HttpServletRequest request){
		String folio = request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		
		if(admCartaSubirDao.existeReg(folio)){
			admCartaSubirDao.deleteReg(folio);
		}
			
		return "redirect:/admlinea/admision/mostrarProceso?Folio="+folio;
	}
	
	@RequestMapping("/admlinea/admision/grabarEstado")
	public String admlineaAdmisionGrabarEstado(HttpServletRequest request){
		String folio 		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String estado 		= request.getParameter("Estado")==null?"0":request.getParameter("Estado");
		String asesorId 	= request.getParameter("AsesorId")==null?"0":request.getParameter("AsesorId");
		
		if (admSolicitudDao.existeReg(folio)) {
			admSolicitudDao.updateEstado(folio, estado);
			admSolicitudDao.updateAsesor(folio, asesorId);
		}
		
		return "redirect:/admlinea/admision/mostrarProceso?Folio="+folio;
	}
	
	@RequestMapping("/admlinea/admision/evaluacionNota")
	public String admlineaAdmisionEvaluacionNota(HttpServletRequest request, Model modelo){
		
		String folio 		= request.getParameter("Folio") == null ? "0" : request.getParameter("Folio");
		String evaluacionId	= request.getParameter("Id") == null ? "0" : request.getParameter("Id");

		AdmSolicitud admSolicitud			= new AdmSolicitud();
		AdmAcademico admAcademico			= new AdmAcademico();
		AdmEvaluacion admEvaluacion			= admEvaluacionDao.mapeaRegId(evaluacionId);
		AdmEvaluacionNota evaluacionNota 	= new AdmEvaluacionNota();

		
		if(admEvaluacionNotaDao.existeReg(evaluacionId, folio)) {
			evaluacionNota = admEvaluacionNotaDao.mapeaRegId(evaluacionId, folio);
		}
		
		String carreraNombre		= "-";
		
		if (admSolicitudDao.existeReg(folio)) {
			admSolicitud 		= admSolicitudDao.mapeaRegId(folio);
			admAcademico		= admAcademicoDao.mapeaRegId(folio);
			carreraNombre 		= catCarreraDao.getNombreCarrera(admAcademico.getCarreraId());
		}
		
		
		modelo.addAttribute("folio",folio);
		modelo.addAttribute("evaluacionId",evaluacionId);
		modelo.addAttribute("carreraNombre",carreraNombre);
		modelo.addAttribute("admSolicitud",admSolicitud);
		modelo.addAttribute("admAcademico",admAcademico);
		modelo.addAttribute("admEvaluacion",admEvaluacion);
		modelo.addAttribute("evaluacionNota",evaluacionNota);
		
		return "admlinea/admision/evaluacionNota";
	}
	
	@RequestMapping("/admlinea/admision/borrarEvaluacionNota")
	public String admlineaAdmisionBorrarEvaluacionNota(HttpServletRequest request, Model modelo){
		
		String folio 		= request.getParameter("Folio") == null ? "0" : request.getParameter("Folio");
		String evaluacionId	= request.getParameter("EvaluacionId") == null ? "0" : request.getParameter("EvaluacionId");
		
		if(admEvaluacionNotaDao.existeReg(evaluacionId, folio)) {
			admEvaluacionNotaDao.deleteReg(evaluacionId, folio);
		}
		
		return "redirect:/admlinea/admision/evaluacionNota?Folio="+folio+"&Id="+evaluacionId;
	}
	
	@RequestMapping("/admlinea/admision/grabaEvaluacionNota")
	public String admlineaAdmisionGrabaEvaluacionNota(HttpServletRequest request){
		
		String folio 			= request.getParameter("Folio") == null ? "0" : request.getParameter("Folio");
		String evaluacionId		= request.getParameter("Id") == null ? "0" : request.getParameter("Id");
		String nota				= request.getParameter("Nota") == null ? "0" : request.getParameter("Nota");
		String comentario		= request.getParameter("Comentario") == null ? "0" : request.getParameter("Comentario");
		String usuario			= "";
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			usuario = (String)sesion.getAttribute("codigoPersonal");
		}
		
		AdmEvaluacionNota evaluacionNota = new AdmEvaluacionNota();
		
		if(admEvaluacionNotaDao.existeReg(evaluacionId, folio)) {
			evaluacionNota = admEvaluacionNotaDao.mapeaRegId(evaluacionId, folio);
			evaluacionNota.setNota(nota);
			evaluacionNota.setFecha(aca.util.Fecha.getHoy());
			evaluacionNota.setUsuario(usuario);
			evaluacionNota.setComentario(comentario);
			
			admEvaluacionNotaDao.updateReg(evaluacionNota);
		}else {
			evaluacionNota.setFolio(folio);
			evaluacionNota.setEvaluacionId(evaluacionId);
			evaluacionNota.setNota(nota);
			evaluacionNota.setFecha(aca.util.Fecha.getHoy());
			evaluacionNota.setUsuario(usuario);
			evaluacionNota.setComentario(comentario);
			
			admEvaluacionNotaDao.insertReg(evaluacionNota);
		}
		
		return "redirect:/admlinea/admision/mostrarProceso?Folio="+folio;
	}

	@ResponseBody
	@RequestMapping("/admlinea/admision/enviarCorreo")
	public String admlineaAdmisionEnviarCorreo(HttpServletRequest request){		
		
		String folio 		= request.getParameter("Folio");
		//String de 			= request.getParameter("De");
		String para 		= request.getParameter("Para")==null?"bethydom@um.edu.mx":request.getParameter("Para").trim();
		String asunto 		= request.getParameter("Asunto")==null?"Admisiones-UM("+folio+")":request.getParameter("Asunto");
		String mensaje 		= request.getParameter("Mensaje").replaceAll("/N", "\n")+"\n\nMuchas gracias por su colaboración.\nOficina de Admisión \nUniversidad de Montemorelos";		
		
		String respuesta	= "-";
		try{
			mailService.sendMesageSimple(para, asunto, mensaje);
			respuesta 	= "cambiar('1');";			
		}catch(Exception ex){			
			respuesta 		= "cambiar('2');";
	    	System.out.println("Error: admLinea.admision.enviarCorreo("+para+")|"+ex);
	    }
		
		return respuesta;
	}
	
	@RequestMapping("/admlinea/admision/eliminarAspirante")
	public String admlineaAdmisionEliminarAspirante(HttpServletRequest request){
		String folio 		= request.getParameter("Folio") == null ? "0" : request.getParameter("Folio");
		String usuario		= "";
		LogOperacion log	= new LogOperacion();
		
		HttpSession sesion	= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			usuario = (String)sesion.getAttribute("codigoPersonal");
		}
		
		AdmSolicitud solicitud = new AdmSolicitud();
		
		if(admSolicitudDao.existeReg(folio)) {
			solicitud = admSolicitudDao.mapeaRegId(folio);
		}
		
		String datos = "Folio:"+folio+", Nombre:"+solicitud.getNombre()+" "+(solicitud.getApellidoMaterno()==null?"":solicitud.getApellidoMaterno())+" "+solicitud.getApellidoPaterno();
		
		try {
			admAcademicoDao.deleteReg(folio);
			
			List<AdmDocAlum> lisPorFolio = admDocAlumDao.lisPorFolio(folio, "");
			for(AdmDocAlum doc : lisPorFolio) {
				admDocAlumDao.deleteReg(folio, doc.getDocumentoId());
			}
			
			for(int i=1; i<=5; i++){
				admEncuestaDao.deleteReg(folio, i+"");
			}
			
			List<AdmEstudio> getListAFolio = admEstudioDao.getListAFolio(folio, "");
			for(AdmEstudio estudio : getListAFolio) {
				admEstudioDao.deleteReg(folio, estudio.getId());
			}
			
			admPadresDao.deleteReg(folio);
	
			for(int i=1; i<=5; i++){
				admRecomiendaDao.deleteReg(folio,i+"");
			}
			
			admSaludDao.deleteReg(folio);
			admTutorDao.deleteReg(folio);
			admUbicacionDao.deleteReg(folio);
			admProcesoDao.deleteReg(folio);
			
			if(admSolicitudDao.deleteReg(folio)) {
				log.setDatos(datos);
				log.setIp(request.getRemoteAddr());
				log.setUsuario(usuario);
				log.setTabla("ADM_SOLICITUD");
				log.setOperacion("delete");
				logOperacionDao.insertReg(log);
			}
		} catch (Exception e) {
			System.out.println("Error - aca.web.amdlinea.ContAmdlineaAdmision|updateReg|:"+e);	
		}
		
		return "redirect:/admlinea/admision/proceso";
	}
	
	@RequestMapping("/admlinea/admision/subirArchivo")
	public String admlineaAdmisionSubirArchivo(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContAdmlineaAdmision|admlineaAdmisionSubirArchivo");
		return "admlinea/admision/subirArchivo";
	}
	
	@RequestMapping("/admlinea/admision/visualizar")
	public String admlineaAdmisionVisualizar(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContAdmlineaAdmision|admlineaAdmisionVisualizar");
		return "admlinea/admision/visualizar";
	}
	
	@RequestMapping("/admlinea/admision/visualizarDocs")
	public String admlineaAdmisionVisualizarDocs(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContAdmlineaAdmision|admlineaAdmisionVisualizarDocs");
		return "admlinea/admision/visualizarDocs";
	}
	
	@RequestMapping("/admlinea/admision/documentos")
	public String admLineaAdmisionDocumentos(HttpServletRequest request, Model modelo){
		
		String codigoPersonal 		= "0";
		String folio				= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String documentoId			= request.getParameter("DocumentoId")==null?"0":request.getParameter("DocumentoId");		
		String accion 				= request.getParameter("Accion")==null ? "0" : request.getParameter("Accion");
		
		AdmDocAlum docAlum 			= new AdmDocAlum();
		AdmSolicitud admSolicitud	= new AdmSolicitud();
		AdmProceso proceso			= new AdmProceso();
		AdmAcademico admAcademico	= new AdmAcademico();
		AdmPasos admPasos			= new AdmPasos();
		CatCarrera catCarrera		= new CatCarrera();
		Acceso acceso				= new Acceso();
		
		String documentoNombre		= "-";
		String nivelNombre 			= "-";
		String modalidadNombre 		= "-";
		String asesorNombre			= "-";
		boolean existeAsesor		= false;		
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			codigoPersonal 		= sesion.getAttribute("codigoPersonal").toString();
			acceso				= accesoDao.mapeaRegId(codigoPersonal);
			if (!folio.equals("0")){ 
				sesion.setAttribute("folio", folio);
				admSolicitud 		= admSolicitudDao.mapeaRegId(folio);
				admAcademico 		= admAcademicoDao.mapeaRegId(folio);
				catCarrera 			= catCarreraDao.mapeaRegId(admAcademico.getCarreraId());
				nivelNombre 		= catNivelDao.getNivelNombre(admAcademico.getNivelId());
				modalidadNombre 	= catModalidadDao.getNombreModalidad(admAcademico.getModalidad());
				asesorNombre 		= maestrosDao.getNombreMaestro(admSolicitud.getAsesorId(), "NOMBRE");
				existeAsesor		= admSolicitudDao.existeAsesor(codigoPersonal);
				documentoNombre 	= admDocumentoDao.getNombreDocumento(documentoId);
			}
		}
		
		HashMap<String, AdmArchivo> mapaArchivo = admArchivoDao.mapaArchivo(folio);
		HashMap<String,AdmImagen> mapaImagen 	= admImagenDao.mapaImagen(folio);
		
		// Consulta el numero de documentos que tiene el alumno y no son requeridos en su carrera
		int documentosExtras		= admDocAlumDao.numDoctosExtras(folio, admAcademico.getCarreraId());
				
		if (admDocAlumDao.existeReg(folio, documentoId)) {
			docAlum = admDocAlumDao.mapeaRegId(folio, documentoId);			
		}
		
		if(accion.equals("1")){			
			docAlum.setEstado("A");
			docAlum.setUsuario(codigoPersonal);
			if (admDocAlumDao.updateReg(docAlum)) {
				//System.out.println("Update:"+folio);
			}else {
				System.out.println("Error en Update:"+folio);
			}
		}else if(accion.equals("2")){			
			docAlum.setEstado("N");
			docAlum.setUsuario(codigoPersonal);
			admDocAlumDao.updateReg(docAlum);
		}else if(accion.equals("3")){			
			if(admDocAlumDao.existeReg(folio, documentoId) || admImagenDao.existeReg(folio, documentoId) ){
				docAlum.setEstado("E");
				docAlum.setUsuario(codigoPersonal);
				admDocAlumDao.updateReg(docAlum);							
			}
		}else if(accion.equals("4")){	
			docAlum.setUsuario(codigoPersonal);		
			docAlum.setComentario(request.getParameter("Comentario"));
			admDocAlumDao.updateReg(docAlum);			
		}else if(accion.equals("5")){ // AUTORIZAR DOCUMENTOS	
			admSolicitudDao.updateEstadoAndFecha(folio,"4");
			admSolicitud = admSolicitudDao.mapeaRegId(folio);
			
			proceso.setFolio(folio);
			admProcesoDao.updateFecha(3,folio);
			
			String para 		= admSolicitud.getEmail();
			String asunto 		= "Admission System ("+folio+")";
			String mensaje 		= "Your documents have been authorized, contact your advisor if your need assistance to continue with the process.";		
			
			docAlum.setUsuario(codigoPersonal);
			admDocAlumDao.updateReg(docAlum);

			try{
				mailService.sendMesageSimple(para, asunto, mensaje);
			}catch(Exception ex){			
		    	System.out.println("Error: admLineaAdmisionDocumentos("+para+")|"+ex);
		    }
			
		}else if(accion.equals("6")){			
			docAlum.setCarta("N");
			docAlum.setUsuario(codigoPersonal);
			admDocAlumDao.updateReg(docAlum);		
		}else if(accion.equals("7")){
			docAlum.setCarta("S");
			docAlum.setUsuario(codigoPersonal);			
			admDocAlumDao.updateReg(docAlum);
		}else if(accion.equals("8")){			
			docAlum.setFolio(folio);
			docAlum.setUsuario(codigoPersonal);
			docAlum.setDocumentoId(documentoId);
			docAlum.setEstado("A");
			admDocAlumDao.insertReg(docAlum);
		}else if(accion.equals("9")){
			docAlum.setFolio(folio);
			docAlum.setUsuario(codigoPersonal);
			docAlum.setDocumentoId(documentoId);
			docAlum.setEstado("N");
			if (admDocAlumDao.existeReg(folio, documentoId)) {				
				admDocAlumDao.updateReg(docAlum);			
			}else {
				admDocAlumDao.insertReg(docAlum);			
			}		
		}else if(accion.equals("10")){
			admPasos.setFolio(folio);
			admPasos.setUsuario(codigoPersonal);
			admPasos.setPasos("6");
			admPasos.setComentario("Pre-autorizado");
			
			if (admPasosDao.existeReg(folio, "6")){
				admSolicitudDao.updateFecha(folio);			
			}else {
				if (admPasosDao.insertReg(admPasos)){
					admSolicitudDao.updateFecha(folio);
				}
			}			
		}
		
		if (admDocAlumDao.existeReg(folio, documentoId)) {
			docAlum = admDocAlumDao.mapeaRegId(folio, documentoId);
		}
		
		List<AdmRequisito> lisRequisitos 			= admRequisitoDao.getLista(admAcademico.getCarreraId(), " AND MODALIDADES IS NOT NULL ORDER BY SALOMON.DOCUMENTO_NOMBRE(DOCUMENTO_ID),SALOMON.ADM_DOCTIPO(DOCUMENTO_ID)");

		HashMap<String,String> mapImagen 			= admImagenDao.mapImagen(folio);
		HashMap<String, String> mapArchivo			= admArchivoDao.mapArchivo(folio);
		HashMap<String, AdmDocumento> mapDocumentos	= admDocumentoDao.mapaDocumentos();
		HashMap<String, AdmFormato> mapFormatos		= admFormatoDao.mapaFormatos();
		HashMap<String, AdmDocAlum> mapDocAlum		= admDocAlumDao.mapaDocumentosAlumno(folio);
		System.out.println(mapDocAlum.size());
		System.out.println(lisRequisitos.size());

		boolean docAutorizados = true;

		for(AdmRequisito admRequisito : lisRequisitos){
			System.out.println(admRequisito.getDocumentoId());
			if(mapDocAlum.containsKey(admRequisito.getDocumentoId())){
				System.out.println(admRequisito.getDocumentoId());
				String estadoDoc = mapDocAlum.get(admRequisito.getDocumentoId()).getEstado();
				if(!estadoDoc.equals("A")){ // E = Enviados, A = Autorizados.
					docAutorizados = false; // If any document is not authorized, set docAutorizados to false
					break; // Exit the loop early, as we already know the result
				}
			} else {
				docAutorizados = false; // If a required document is missing, set docAutorizados to false
				break; // Exit the loop early, as we already know the result
			}
		}
		
		modelo.addAttribute("docAlum",docAlum);		
		modelo.addAttribute("admSolicitud",admSolicitud);
		modelo.addAttribute("admAcademico",admAcademico);
		modelo.addAttribute("catCarrera",catCarrera);
		modelo.addAttribute("lisRequisitos",lisRequisitos);
		modelo.addAttribute("mapDocAlum",mapDocAlum);
		modelo.addAttribute("mapArchivo",mapArchivo);
		modelo.addAttribute("mapImagen",mapImagen);
		modelo.addAttribute("mapDocumentos",mapDocumentos);		
		modelo.addAttribute("mapFormatos",mapFormatos);
		modelo.addAttribute("nivelNombre",nivelNombre);
		modelo.addAttribute("modalidadNombre",modalidadNombre);
		modelo.addAttribute("asesorNombre",asesorNombre);
		modelo.addAttribute("existeAsesor",existeAsesor);
		modelo.addAttribute("existeAdmin",acceso.getAdministrador().equals("S")?true:false);
		modelo.addAttribute("documentoNombre",documentoNombre);		
		modelo.addAttribute("documentosExtras",documentosExtras);
		modelo.addAttribute("mapaArchivo",mapaArchivo);
		modelo.addAttribute("mapaImagen",mapaImagen);
		modelo.addAttribute("docAutorizado", docAutorizados);
		
		return "admlinea/admision/documentos";
	}
	
	@RequestMapping("/admlinea/admision/addMensaje")
	public String admLineaAdmisionAddMensaje(HttpServletRequest request, Model modelo){
		
		String folio				= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String documentoId			= request.getParameter("DocumentoId")==null?"0":request.getParameter("DocumentoId");		
		String comentario			= request.getParameter("Comentario")==null?"0":request.getParameter("Comentario");
		
		if(admDocAlumDao.updateComentario(folio, documentoId, comentario)){
			
		}
		
		return "redirect:/admlinea/admision/documentos?Folio="+folio;
	}
	
	@Transactional
	@RequestMapping("/admlinea/admision/borrar")
	public String admlineaAdmisionBorrar(HttpServletRequest request){
		String folio 	= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String mensaje 	= "-"; 
		
		boolean pasoImagen 		= false;
		boolean pasoArchivo 	= false;		
		if(admImagenDao.existeRegFolio(folio)){
			if (admImagenDao.deleteRegFolio(folio)) {
				pasoImagen = true;
			}
		}else {
			pasoImagen = true;
		}
		
		if(pasoImagen){
			if (admArchivoDao.existeRegFolio(folio)) {
				if (admArchivoDao.deleteRegFolio(folio)){
					pasoArchivo = true;			
				}
			}else {
				pasoArchivo = true;
			}	
		}
		
		if(pasoArchivo){
			if (admDocAlumDao.existeRegFolio(folio)) {
				if (admDocAlumDao.deleteRegFolio(folio)) {
					mensaje = "¡Se eliminaron todas las imágenes y archivos!";
				}
			}else {
				mensaje = "¡No existe información en este folio!";
			}	
		}
		
		return "redirect:/admlinea/admision/documentos?Folio="+folio+"&Mensaje="+mensaje+"&Accion=0";
	}
	
	@RequestMapping("/admlinea/admision/borrarExtras")
	public String admlineaAdmisionBorrarExtras(HttpServletRequest request){
		String folio 		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String carreraId 	= request.getParameter("CarreraId")==null?"0":request.getParameter("CarreraId");
		String mensaje 	= "-";
		
		List<AdmDocAlum> lisDocumentos = admDocAlumDao.lisDocumentosExtras(folio, carreraId, " ORDER BY DOCUMENTO_ID");
		for (AdmDocAlum doc : lisDocumentos) {
			boolean pasoImagen 		= false;
			boolean pasoArchivo 	= false;		
			if(admImagenDao.existeRegDocumento(folio, doc.getDocumentoId())){
				if (admImagenDao.deleteRegDocumento(folio, doc.getDocumentoId())) {
					pasoImagen = true;
				}
			}else{
				pasoImagen = true;
			}
			
			if(pasoImagen){
				if (admArchivoDao.existeRegDocumento(folio, doc.getDocumentoId())){
					if (admArchivoDao.deleteRegDocumento(folio, doc.getDocumentoId())){
						pasoArchivo = true;			
					}
				}else{
					pasoArchivo = true;
				}	
			}
			
			if(pasoArchivo){
				if (admDocAlumDao.existeReg(folio, doc.getDocumentoId())) {
					if (admDocAlumDao.deleteReg(folio, doc.getDocumentoId())) {
						mensaje = "¡Se eliminaron todas las imágenes y archivos!";
					}
				}else {
					mensaje = "¡No existe información en este folio!";
				}	
			}			
		}		
		return "redirect:/admlinea/admision/documentos?Folio="+folio+"&Mensaje="+mensaje+"&Accion=0";
	}
	
	@RequestMapping("/admlinea/admision/imagen")
	public String admLineaAdmisionImagen(HttpServletRequest request, Model modelo) {		
		String folio 			= "0"; 
		String documentoId		= request.getParameter("documentoId")==null?"0":request.getParameter("documentoId");
		String hoja				= request.getParameter("hoja")==null?"0":request.getParameter("hoja");
		String nombreDocumento	= admDocumentoDao.getNombreDocumento(documentoId);
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			folio				= (String) sesion.getAttribute("folio");
		}
		
		AdmImagen imagen 		= new AdmImagen();
		byte imagenByte[] 		= null;		

		try {						
			if(admImagenDao.existeReg(folio, documentoId, hoja)){
				imagen = admImagenDao.mapeaRegId(folio, documentoId, hoja);
				imagenByte = imagen.getImagen();				
			}else{
				imagen = admImagenDao.mapeaRegId("0", "0", "0");
				imagenByte = imagen.getImagen();				
			}	
		}catch(Exception ex){
			System.out.println("Error:documentosImagen:"+ex);
		}
		modelo.addAttribute("imagenByte",imagenByte);		
		modelo.addAttribute("nombreDocumento",nombreDocumento);		
		
		return "admlinea/admision/imagen";
	}
	
	@RequestMapping("/admlinea/admision/archivo")
	public void admLineaAdmisionArchivo(HttpServletRequest request, HttpServletResponse response ) {
		
		aca.admision.spring.AdmArchivo archivo = new aca.admision.spring.AdmArchivo();
		String folio 			= ""; 
		String documentoId		= request.getParameter("documentoId")==null?"0":request.getParameter("documentoId");	
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			folio				= (String) sesion.getAttribute("folio");
		}
		String extencion = "";
		
		try {						
			if(admArchivoDao.existeReg(folio, documentoId)){
				archivo = admArchivoDao.mapeaRegId(folio, documentoId);			

				OutputStream out = response.getOutputStream();
				response.setHeader("Content-Disposition","attachment; filename=\""+ archivo.getNombre() + "\"");
				out.write(archivo.getArchivo());
				
			}
		}catch(Exception ex){
			System.out.println("Error:documentosArchivo:"+ex);
		}			
		
	}
	
	@RequestMapping("/admlinea/admision/cita")
	public String admLineaAdmisionCita(HttpServletRequest request, Model modelo) {
		
		AdmReservada admReservada 	= new AdmReservada();
		AdmEvento admEvento 		= new AdmEvento();
		
		String folio 			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String nombreAlumno		= admSolicitudDao.getNombre(folio);
		
		if (admReservadaDao.existeReservacion(folio)){
			admReservada 		= admReservadaDao.mapeaRegId(folio);			
			admEvento			= admEventoDao.mapeaRegId(admReservada.getEventoId());	
		}
		
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("admReservada", admReservada);
		modelo.addAttribute("admEvento", admEvento);
		
		return "admlinea/admision/cita";
	}
	
	@RequestMapping("/admlinea/admision/cambiarEstado")
	public String admLineaAdmisionCambiarEstado(HttpServletRequest request, Model modelo) {
		
		AdmReservada admReservada 	= new AdmReservada();		
		
		String folio 		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String estado 		= request.getParameter("Estado")==null?"A":request.getParameter("Estado");	
		
		if (admReservadaDao.existeReservacion(folio)){
			admReservada 	= admReservadaDao.mapeaRegId(folio);
			admReservada.setEstado(estado);
			admReservadaDao.updateReg(admReservada);
		}	
		
		return "redirect:/admlinea/admision/cita?Folio="+folio;
	}
	
	@RequestMapping("/admlinea/admision/comentarios")
	public String admLineaAdmisionComentarios(HttpServletRequest request, Model modelo) {
		String folio = request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		
		List<AdmComentario> lista = new ArrayList<AdmComentario>();
		
		AdmSolicitud admSolicitud	= new AdmSolicitud();
		
		if(admComentarioDao.tieneComentarios(folio)) {
			lista = admComentarioDao.getAll(folio, "ORDER BY COMENTARIO_ID");
		}
		
		if(admSolicitudDao.existeReg(folio)) {
			admSolicitud = admSolicitudDao.mapeaRegId(folio);
		}
		
		modelo.addAttribute("admSolicitud", admSolicitud);
		modelo.addAttribute("comentarios", lista);
		modelo.addAttribute("folio", folio);
		
		return "admlinea/admision/comentarios";
	}
	
	@RequestMapping("/admlinea/admision/editarComentario")
	public String admLineaAdmisionEditarComentario(HttpServletRequest request, Model modelo) {
		String folio 		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String comentarioId = request.getParameter("ComentarioId")==null?"0":request.getParameter("ComentarioId");
		String mensaje 		= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		
		AdmComentario comentario 	= new AdmComentario();
		AdmSolicitud admSolicitud	= new AdmSolicitud();
		
		if(admComentarioDao.existeReg(folio, comentarioId)) {
			comentario = admComentarioDao.mapeaRegId(folio, comentarioId);
		}
		
		if(admSolicitudDao.existeReg(folio)) {
			admSolicitud = admSolicitudDao.mapeaRegId(folio);
		}
		
		modelo.addAttribute("admSolicitud", admSolicitud);
		modelo.addAttribute("comentario", comentario);
		modelo.addAttribute("folio", folio);
		modelo.addAttribute("mensaje", mensaje);
		
		return "admlinea/admision/editarComentario";
	}
	
	@RequestMapping("/admlinea/admision/grabarComentario")
	public String admLineaAdmisionGrabarComentario(HttpServletRequest request, Model modelo) {
		String folio 		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String tipo			= request.getParameter("Tipo")==null?"0":request.getParameter("Tipo");
		String comentarioId = request.getParameter("ComentarioId")==null?"0":request.getParameter("ComentarioId");
		String come 		= request.getParameter("Comentario")==null?"-":request.getParameter("Comentario");
		String estado 		= request.getParameter("Estado")==null?"A":request.getParameter("Estado");
		String usuario		= "0";		
		String mensaje 		= "0";
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			usuario = (String)sesion.getAttribute("codigoPersonal");			
		}
		
		AdmComentario comentario = new AdmComentario();
		
		if(comentarioId.equals("0")) {
			comentarioId = admComentarioDao.maximoReg();
		}
		
		comentario.setFolio(folio);
		comentario.setTipo(tipo);
		comentario.setComentarioId(comentarioId);
		comentario.setUsuario(usuario);
		comentario.setFecha(aca.util.Fecha.getHoy());
		comentario.setComentario(come);
		comentario.setEstado(estado);
		
		if(!admComentarioDao.existeReg(folio, comentarioId)) {
			if(admComentarioDao.insertReg(comentario)) {
				mensaje = "1";
			}else {
				mensaje = "2";
			}
		}else {
			if(admComentarioDao.updateReg(comentario)) {
				mensaje = "1";
			}else {
				mensaje = "2";
			}
		}
		
		modelo.addAttribute("comentario", comentario);
		modelo.addAttribute("folio", folio);
		
		return "redirect:/admlinea/admision/editarComentario?Folio="+folio+"&ComentarioId="+comentarioId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/admlinea/admision/borrarComentario")
	public String admLineaAdmisionBorrarComentario(HttpServletRequest request, Model modelo) {
		String folio 		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String comentarioId = request.getParameter("ComentarioId")==null?"0":request.getParameter("ComentarioId");
		
		if(admComentarioDao.existeReg(folio, comentarioId)) {
			admComentarioDao.deleteReg(folio, comentarioId);
		}
		
		return "redirect:/admlinea/admision/comentarios?Folio="+folio;
	}

	@RequestMapping("/admlinea/admision/condiciones")
	public String admLineaAdmisionCondiciones(HttpServletRequest request, Model modelo) {
		String folio = request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		
		AdmSolicitud admSolicitud		= new AdmSolicitud();
		
		if(admSolicitudDao.existeReg(folio)) {
			admSolicitud = admSolicitudDao.mapeaRegId(folio);
		}
			
		List<AdmCarta> lisCondiciones 	= admCartaDao.lisPorFolio(folio, "ORDER BY CONDICION_ID");	
		
		modelo.addAttribute("folio", folio);
		modelo.addAttribute("admSolicitud", admSolicitud);
		modelo.addAttribute("lisCondiciones", lisCondiciones);
		
		return "admlinea/admision/condiciones";
	}
	
	@RequestMapping("/admlinea/admision/editarCondicion")
	public String admLineaAdmisionEditarCondicion(HttpServletRequest request, Model modelo) {
		String folio 			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String condicionId  	= request.getParameter("CondicionId")==null?"0":request.getParameter("CondicionId");
		
		AdmCarta admCarta			= new AdmCarta();
		AdmSolicitud admSolicitud	= new AdmSolicitud();
		
		if(admCartaDao.existeReg(folio, condicionId)) {
			admCarta = admCartaDao.mapeaRegId(folio, condicionId);
		}
		
		if(admSolicitudDao.existeReg(folio)) {
			admSolicitud = admSolicitudDao.mapeaRegId(folio);
		}
		
		modelo.addAttribute("admSolicitud", admSolicitud);
		modelo.addAttribute("admCarta", admCarta);		
		
		return "admlinea/admision/editarCondicion";
	}
	
	@RequestMapping("/admlinea/admision/grabarCondicion")
	public String admLineaAdmisionGrabarCondicion(HttpServletRequest request, Model modelo) {
		String folio 			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String condicionId  	= request.getParameter("CondicionId")==null?"0":request.getParameter("CondicionId");
		String condicionNombre	= request.getParameter("CondicionNombre")==null?"":request.getParameter("CondicionNombre");
		String mensaje 			= "0";
		
		AdmCarta condicion = new AdmCarta();
		
		if(condicionId.equals("0")) {
			condicionId = admCartaDao.maximoReg(folio);
		}
		
		condicion.setFolio(folio);
		condicion.setCondicionId(condicionId);
		condicion.setCondicionNombre(condicionNombre);
		
		if(!admCartaDao.existeReg(folio, condicionId)) {
			if(admCartaDao.insertReg(condicion)) {
				mensaje = "1";
			}else {
				mensaje = "2";
			}
		}else {
			if(admCartaDao.updateReg(condicion)) {
				mensaje = "1";
			}else {
				mensaje = "2";
			}
		}
		
		modelo.addAttribute("condicion", condicion);
		modelo.addAttribute("folio", folio);
		
		return "redirect:/admlinea/admision/editarCondicion?Folio="+folio+"&CondicionId="+condicionId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/admlinea/admision/borrarCondicion")
	public String admLineaAdmisionBorrarCondicion(HttpServletRequest request, Model modelo) {
		String folio 		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String condicionId = request.getParameter("CondicionId")==null?"0":request.getParameter("CondicionId");
		
		if(admCartaDao.existeReg(folio, condicionId)) {
			admCartaDao.deleteReg(folio, condicionId);
		}
		
		return "redirect:/admlinea/admision/condiciones?Folio="+folio;
	}
	
	@RequestMapping("/admlinea/admision/listaResultados")
	public String admlineaAdmisionListaResultados(HttpServletRequest request, Model modelo){
		String folio 		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String nombre		= admSolicitudDao.getNombre(folio);
		
		List<AdmEvaluacionNota> lisEvaluaciones 		= admEvaluacionNotaDao.listEvaluaciones(folio);
		HashMap<String,AdmEvaluacion> mapaEvaluaciones	= admEvaluacionDao.mapaEvaluaciones();
		
		modelo.addAttribute("nombre", nombre);
		modelo.addAttribute("lisEvaluaciones", lisEvaluaciones);
		modelo.addAttribute("mapaEvaluaciones", mapaEvaluaciones);
				
		return "admlinea/admision/listaResultados";
	}
	
	@RequestMapping("/admlinea/admision/listaPago")
	public String admlineaAdmisionListaPagos(HttpServletRequest request, Model modelo){
		String folio 		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String nombre		= admSolicitudDao.getNombre(folio);
		
		List<AdmPago> lisPagos		= admPagoDao.listPagos(folio);
		
		
		modelo.addAttribute("nombre", nombre);
		modelo.addAttribute("lisPagos", lisPagos);
		
				
		return "admlinea/admision/listaPago";
	}
	
	@RequestMapping("/admlinea/admision/descargarPago")
	public void descargarPago(HttpServletResponse response, HttpServletRequest request){
		String folio 		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");  
		
		AdmPago admPago		= new AdmPago();
		
		try {						
			if(admPagoDao.existeReg(folio)){
				admPago = admPagoDao.mapeaRegId(folio);			
				
				OutputStream out = response.getOutputStream();
				response.setHeader("Content-Disposition","attachment; filename=\""+ folio+admPago.getNombre()+ "\"");
				out.write(admPago.getArchivo());
			}
		}catch(Exception ex){
			System.out.println("Error:/admlinea/admision/descargarPago:"+ex);
		}
	}
	
	@RequestMapping("/admlinea/admision/listadoEncuestas")
	public String admlineaAdmisionListadoEncuestas(HttpServletRequest request, Model modelo){
		
		String folio 		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String mensaje 		= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		String nombre		= admSolicitudDao.getNombre(folio);
		
		List<AdmRecomienda> listaRecomendaciones	= admRecomiendaDao.lisPorFolio(folio," ORDER BY RECOMENDACION_ID");		
		HashMap<String,AdmEncuesta> mapaEncuestas 	= admEncuestaDao.mapaEncuestas(folio);
		
		modelo.addAttribute("nombre", nombre);
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("listaRecomendaciones", listaRecomendaciones);
		modelo.addAttribute("mapaEncuestas", mapaEncuestas);
		
		return "admlinea/admision/listadoEncuestas";
	}
	
	@RequestMapping("/admlinea/admision/editarContacto")
	public String admlineaAdmisionEditarContacto(HttpServletRequest request, Model modelo){
		
		String folio 		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String id	 		= request.getParameter("Id")==null?"0":request.getParameter("Id");
		String mensaje 		= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		String nombre		= admSolicitudDao.getNombre(folio);
		
		AdmRecomienda recomendacion	= new AdmRecomienda();
		
		if(admRecomiendaDao.existeReg(folio, id)) {
			recomendacion = admRecomiendaDao.mapeaRegId(folio,id);		
		}
		
		modelo.addAttribute("nombre", nombre);
		modelo.addAttribute("recomendacion", recomendacion);
		modelo.addAttribute("mensaje", mensaje);
		
		return "admlinea/admision/editarContacto";
	}
	
	@RequestMapping("/admlinea/admision/grabarContacto")
	public String admLineaAdmisionGrabarContacto(HttpServletRequest request, Model modelo) {
		String folio 		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String id  			= request.getParameter("Id")==null?"0":request.getParameter("Id");
		String nombre		= request.getParameter("Nombre")==null?"":request.getParameter("Nombre");
		String puesto		= request.getParameter("Puesto")==null?"":request.getParameter("Puesto");
		String email		= request.getParameter("Email")==null?"":request.getParameter("Email");
		String telefono		= request.getParameter("Telefono")==null?"":request.getParameter("Telefono");
		String mensaje 		= "0";
		
		AdmRecomienda recomendacion	= new AdmRecomienda();
		
		if(admRecomiendaDao.existeReg(folio, id)) {
			recomendacion = admRecomiendaDao.mapeaRegId(folio,id);		
		}
		
		recomendacion.setFolio(folio);
		recomendacion.setRecomendacionId(id);
		recomendacion.setNombre(nombre);
		recomendacion.setPuesto(puesto);
		recomendacion.setEmail(email);
		recomendacion.setTelefono(telefono);
		recomendacion.setEstado("A");
		
		if(!admRecomiendaDao.existeReg(folio, id)) {
			if(admRecomiendaDao.insertReg(recomendacion)) {
				mensaje = "1";
			}else {
				mensaje = "2";
			}
		}else {
			if(admRecomiendaDao.updateReg(recomendacion)) {
				mensaje = "1";
			}else {
				mensaje = "2";
			}
		}
		
		return "redirect:/admlinea/admision/editarContacto?Folio="+folio+"&Id="+id+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/admlinea/admision/enviarCorreoContacto")
	public String admLineaAdmisionEnviarCorreo(HttpServletRequest request){
		String folio 		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String id  			= request.getParameter("Id")==null?"0":request.getParameter("Id");
		String email		= request.getParameter("Email")==null?"0":request.getParameter("Email");
		String mensaje 		= "";
		String ruta 		= "";
		String institucion  = "";
		String remitente 	= "";
		Parametros parametros = parametrosDao.mapeaRegId("1");
		if(parametros.getInstitucion().equals("Pacific Adventist University")){ 
			ruta 		= "https://admissions.pau.ac.pg/admission"; 
			institucion = "Pacific Adventist University";
			remitente 	= "PAU";
		}
		if(parametros.getInstitucion().equals("Sonoma")){ 
			ruta 		= "https://admissions.sonoma.ac.pg/admission"; 
			institucion = "Sonoma Adventist College";
			remitente 	= "SAC";
		}
		if(parametros.getInstitucion().equals("Fulton")){ 
			ruta 		= "https://fulton.um.edu.mx/admission";
			institucion = "Fulton Adventist University College";
			remitente 	= "FAUC";	 
		}
		
		AdmRecomienda recomienda 	= admRecomiendaDao.mapeaRegId(folio, id+"");
		String nombre				= admSolicitudDao.getNombre(folio);
		String clave 				= Encriptar.sha256ConBase64(folio+String.valueOf(id));
		clave = clave.replace("=", "");

		try{
			String texto = "Thank you for accepting to help us with "+nombre+" Admission Process."+
					"\n\nPlease evaluate the applicant by selecting, for each quality, the alternative that best describes your perception of him or her."+
					"\n\nIf you consider that you do not have sufficient knowledge of the candidate in any of the above mentioned characteristics, please choose the option: I do not have enough information."+
					"\n\nWe appreciate your cooperation in answering and sending this information as soon as possible. If you are unable to do so at this time, please return to this page by clicking on the link in the email message."+
					"\n\n"+ruta+"/encuesta?Folio="+folio+"&Clave="+clave+"&Id="+id+
					"\n\nThis information will be kept confidential, anonymous, and for the exclusive use in the admission process."+
					"\n\nThank you very much for your cooperation."+
					"\nAdmissions Office \n"+institucion;
			mailService.sendMesageSimple(email, remitente+" - Admissions Office/"+recomienda.getNombre() , texto);
			mensaje = "3";
		}catch(Exception ex){
			System.out.println("Error: admLineaAdmisionEnviarCorreo("+email+")"+ex);
		}
		
		return "redirect:/admlinea/admision/listadoEncuestas?Folio="+folio+"&Mensaje="+mensaje;
	}

	@RequestMapping("/admlinea/admision/mostrarEncuesta")
	public String admlineaAdmisionMostrarEncuesta(HttpServletRequest request, Model modelo){
		
		String folio 			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String recoId 			= request.getParameter("Id")==null?"0":request.getParameter("Id");
		
		AdmEncuesta admEncuesta = new AdmEncuesta();
		HashMap<String,AdmRecomienda> mapaEncuestas = admRecomiendaDao.mapaRecomendaciones(folio);
		
		if(admEncuestaDao.existeReg(folio, recoId)) {
			admEncuesta = admEncuestaDao.mapeaRegId(folio, recoId);
		}
		
		modelo.addAttribute("folio", folio);
		modelo.addAttribute("admEncuesta", admEncuesta);
		modelo.addAttribute("mapaEncuestas", mapaEncuestas);
		
		return "admlinea/admision/mostrarEncuesta";
	}

	@RequestMapping("/admlinea/admision/listoParaVoBo")
	public String admlineaAdmisionListoParaVoBo(HttpServletRequest request){
		
		String folio 		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String accion 		= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		AdmPasos admPasos 	= new AdmPasos();
		String usuario 		= "0";
		String fechaHoy		= aca.util.Fecha.getHoy();
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			usuario 			= (String)sesion.getAttribute("codigoPersonal");
		}
		
		admPasos.setComentario("Ready for registration");
		admPasos.setEstado("0");
		admPasos.setFecha(fechaHoy);
		admPasos.setFolio(folio);
		admPasos.setPasos("7");
		admPasos.setUsuario(usuario);
		
		if (!admPasosDao.existeReg(folio,"7") && accion.equals("1")){
			admPasosDao.insertReg(admPasos);
		}else {
			admPasosDao.deleteReg(folio, "7");
		}
			
		return "redirect:/admlinea/admision/mostrarProceso?Folio="+folio;
	}
	
	@RequestMapping("/admlinea/admision/tpt")
	public String admlineaAdmisionTpt(HttpServletRequest request, Model modelo){
		
		String folio 			= request.getParameter("Folio") == null ? "0" : request.getParameter("Folio");
		String evaluacionId		= request.getParameter("Id") == null ? "0" : request.getParameter("Id");
		boolean tienePrueba		= false;

		AdmSolicitud admSolicitud			= new AdmSolicitud();
		AdmAcademico admAcademico			= new AdmAcademico();
		AdmEvaluacion admEvaluacion			= admEvaluacionDao.mapeaRegId(evaluacionId);
		AdmPrueba admPrueba					= new AdmPrueba();
		AdmPruebaAlumno admPruebaAlumno		= new AdmPruebaAlumno();
		
		String carreraNombre		= "-";
		
		if (admSolicitudDao.existeReg(folio)) {
			admSolicitud 		= admSolicitudDao.mapeaRegId(folio);
			admAcademico		= admAcademicoDao.mapeaRegId(folio);
			carreraNombre 		= catCarreraDao.getNombreCarrera(admAcademico.getCarreraId());
			tienePrueba 		= admPruebaAlumnoDao.existeReg(folio);
		}
		
		if (tienePrueba) {
			admPruebaAlumno = admPruebaAlumnoDao.mapeaRegId(folio);
			admPrueba = admPruebaDao.mapeaRegId(admPruebaAlumno.getPruebaId());
		}
		
		modelo.addAttribute("folio",folio);
		modelo.addAttribute("evaluacionId",evaluacionId);
		modelo.addAttribute("tienePrueba",tienePrueba);
		modelo.addAttribute("admPrueba",admPrueba);
		modelo.addAttribute("carreraNombre",carreraNombre);
		modelo.addAttribute("admSolicitud",admSolicitud);
		modelo.addAttribute("admAcademico",admAcademico);
		modelo.addAttribute("admEvaluacion",admEvaluacion);
		
		return "admlinea/admision/tpt";
	}
	
	@ResponseBody
	@RequestMapping("/admlinea/admision/verificaMatricula")
	public String admlineaAdmisionVerificaMatricula(HttpServletRequest request){
		
		String matricula 		= request.getParameter("Matricula")==null?"0":request.getParameter("Matricula");
		String respuesta		= "";
		if (alumPersonalDao.existeReg(matricula)) {			
			respuesta = alumPersonalDao.getNombreAlumno(matricula, "NOMBRE");
		}else {
			respuesta = "<br>Click on <b><i>Transfer</i></b> to save the student in the System";
		}		
		return respuesta;
	}

	@RequestMapping("/admlinea/admision/generarCarta")
	public String admlineaAdmisionGenerarCarta(HttpServletRequest request, Model modelo){
		String folio 			= request.getParameter("Folio") == null ? "0" : request.getParameter("Folio");
		String matricula 		= "0";
		String url 				= "";
		String nombreAplicante  = "n/a";
		String carreraNombre	= "n/a";
		String ciclo 			= "0";
		String mensaje 			= "";
		Parametros parametros 	= parametrosDao.mapeaRegId("1");

		AdmSolicitud admSolicitud 		= new AdmSolicitud();
		AdmAcademico admAcademico		= new AdmAcademico();
		AdmCartaPau admCartaPau 		= admCartaPauDao.existeReg("1")?admCartaPauDao.mapeaRegId("1"): new AdmCartaPau();
		AdmCartaSonoma admCartaSonoma 	= admCartaSonomaDao.existeReg("1")?admCartaSonomaDao.mapeaRegId("1"): new AdmCartaSonoma();
		AdmCartaFulton admCartaFulton 	= admCartaFultonDao.existeReg("1")?admCartaFultonDao.mapeaRegId("1"): new AdmCartaFulton();

		if(admSolicitudDao.existeReg(folio)){
			admSolicitud 	= admSolicitudDao.mapeaRegId(folio);
			admAcademico	= admAcademicoDao.mapeaRegId(folio);
			carreraNombre 	= catCarreraDao.getNombreCarrera(admAcademico.getCarreraId());
			matricula 		= admSolicitud.getMatricula();
			nombreAplicante 	= admSolicitud.getNombre()+" "
								+ admSolicitud.getApellidoPaterno()+" "
								+ (admSolicitud.getApellidoMaterno()==null || admSolicitud.getApellidoMaterno().equals("-")?"":admSolicitud.getApellidoMaterno());

		}else{
			mensaje = "Error en solicitud";
		}

		if(admAcademicoDao.existeReg(folio)){		
			ciclo = admIngresoDao.getNombre(admAcademico.getPeriodoId());
		}

		AlumPersonal alumPersonal = new AlumPersonal();
		if(alumPersonalDao.existeReg(admSolicitud.getMatricula())){
			alumPersonal = alumPersonalDao.mapeaRegId(matricula);
		}

		MapaPlan plan			= mapaPlanDao.mapeaRegId(admAcademico.getPlanId());
		AdmAcomodo admAcomodo  	= admAcomodoDao.mapeaRegId(admSolicitud.getAcomodoId());
		String acomodoNombre 	= admAcomodo.getAcomodoNombre();

		// Datos generales de la carta
		String fechaHoy		= aca.util.Fecha.getHoy();

		// Decide que carta generar
		String carta = "";
		if(parametros.getInstitucion().equals("Pacific Adventist University")) carta = "cartaPauPDF";
		if(parametros.getInstitucion().equals("Sonoma")) carta = "cartaSonomaPDF";
		if(parametros.getInstitucion().equals("Fulton")) carta = "cartaFultonPDF";

		url = carta+"?Folio="+folio
			+ "&Nombre="+nombreAplicante
			+ "&Matricula="+matricula
			+ "&Curso="+carreraNombre
			+ "&Ciclo="+ciclo
			+ "&Fecha="+fechaHoy
			+ "&Acomodo="+acomodoNombre; 

		if(parametros.getInstitucion().equals("Pacific Adventist University")){ 		// PARAMETROS DE PAU
			String fechaRegistro 	= admCartaPau.getFechaRegistro();
			String fechaOrientacion = admCartaPau.getFechaOrientacion();
			String fechaApertura 	= admCartaPau.getFechaApertura();
			String fechaInicio 		= admCartaPau.getFechaInicio();
			String fechaCierre 		= admCartaPau.getFechaCierre();
			String fechaCierreEscrita = aca.util.Fecha.getFechaOficialIngles(fechaCierre);
			String estadoResidencia = admAcomodo.getAcomodoNombre();
			String fechaEscrita 	= aca.util.Fecha.getFechaOficialIngles(fechaHoy);
			String direccionPostal 	= catCiudadDao.getNombreCiudad(admSolicitud.getResPaisId(), admSolicitud.getResEstadoId(), admSolicitud.getResCiudadId())+", "+catEstadoDao.getNombreEstado(admSolicitud.getResPaisId(), admSolicitud.getResEstadoId())+", "+catPaisDao.getNombrePais(admSolicitud.getResPaisId());
			String numeroCiclos 	= String.valueOf((Integer.parseInt(plan.getCiclos()) / 2));

			url += "&fRegistro="+fechaRegistro
				 + "&fOrientacion="+fechaOrientacion
				 + "&fApertura="+fechaApertura
				 + "&fInicio="+fechaInicio
				 + "&fCierre="+fechaCierre
				 + "&fCierreEsc="+fechaCierreEscrita
				 + "&Direccion="+direccionPostal
				 + "&Residencia="+estadoResidencia
				 + "&fEscrita="+fechaEscrita
				 + "&nCiclos="+numeroCiclos;
		}
		if(parametros.getInstitucion().equals("Sonoma")){								// PARAMETROS DE SONOMA
			String numeroOferta 		= "";
			String numeroCiclos 		= String.valueOf(Integer.parseInt(plan.getCiclos())/2);
			String fechaFinal 			= admCartaSonoma.getFechaFinal();
			String fechaFinalEscrita 	= aca.util.Fecha.getFechaOficial(fechaFinal);


			url +="&fFinalEscrita="+fechaFinalEscrita
				+"&fFinal="+fechaFinal
				+"&nOferta="+numeroOferta
				+"&nCiclos="+numeroCiclos;
		}
		if(parametros.getInstitucion().equals("Fulton")){								// PARAMETROS DE FULTON
			String fechaRegistro		= admCartaFulton.getFechaRegistro();
			String fechaOrientacion 	= admCartaFulton.getFechaOrientacion();
			String fechaInicio 			= admCartaFulton.getFechaInicio();
			String fechaCierre 			= admCartaFulton.getFechaCierre();
			String fechaArribo 			= admCartaFulton.getFechaArribo();
			String numeroCiclos 		= String.valueOf(Integer.parseInt(plan.getCiclos())/2);
			String direccionInterior 	= "";

			url += "&fRegistro="+fechaRegistro
				+"&fOrientacion="+fechaOrientacion
				+"&fInicio="+fechaInicio
				+"&fCierre="+fechaCierre
				+"&fArribo="+fechaArribo
				+"&Direccion="+direccionInterior
				+"&nCiclos="+numeroCiclos;
		}

		modelo.addAttribute("admAcomodo", admAcomodo);
		
		return "redirect:/admlinea/admision/"+url;
	}

	@RequestMapping("/admlinea/admision/parametrosCarta")
	public String admlineaAdmisionParametrosCarta(HttpServletRequest request, Model modelo){
		Parametros parametros 	= parametrosDao.mapeaRegId("1");
		boolean existeCarta 	= false;

		AdmCartaFulton admCartaFulton 	= new AdmCartaFulton();
		AdmCartaSonoma admCartaSonoma 	= new AdmCartaSonoma();
		AdmCartaPau admCartaPau 		= new AdmCartaPau();

		if(parametros.getInstitucion().equals("Pacific Adventist University")){
			if(admCartaPauDao.existeReg("1")){
				admCartaPau = admCartaPauDao.mapeaRegId("1");
				existeCarta = true;
			}
		}
		if(parametros.getInstitucion().equals("Sonoma")){
			if(admCartaSonomaDao.existeReg("1")){
				admCartaSonoma = admCartaSonomaDao.mapeaRegId("1");
				existeCarta = true;
			}
		}
		if(parametros.getInstitucion().equals("Fulton")){
			if(admCartaFultonDao.existeReg("1")){
				admCartaFulton = admCartaFultonDao.mapeaRegId("1");
				existeCarta = true;
			}
		}

		modelo.addAttribute("parametros", parametros);
		modelo.addAttribute("existeCarta", existeCarta);
		modelo.addAttribute("admCartaFulton", admCartaFulton);
		modelo.addAttribute("admCartaSonoma", admCartaSonoma);
		modelo.addAttribute("admCartaPau", admCartaPau);

		return "admlinea/admision/parametrosCarta";
	}

	@RequestMapping("/admlinea/admision/grabarPau")
	public String admlineaAdmisionGrabarParametrosPau(HttpServletRequest request, Model modelo){
		String cartaId 			= request.getParameter("cartaId") == null ? "0" : request.getParameter("cartaId");
		String fechaRegistro 	= request.getParameter("fechaRegistro") == null ? "0" : request.getParameter("fechaRegistro");
		String fechaOrientacion = request.getParameter("fechaOrientacion") == null ? "0" : request.getParameter("fechaOrientacion");
		String fechaApertura 	= request.getParameter("fechaApertura") == null ? "0" : request.getParameter("fechaApertura");
		String fechaInicio 		= request.getParameter("fechaInicio") == null ? "0" : request.getParameter("fechaInicio");
		String fechaCierre 		= request.getParameter("fechaCierre") == null ? "0" : request.getParameter("fechaCierre");
		String mensaje 			= "";

		AdmCartaPau admCartaPau = new AdmCartaPau();

		if(admCartaPauDao.existeReg(cartaId)){
			admCartaPau = admCartaPauDao.mapeaRegId(cartaId);
		}else{
			admCartaPau.setCartaId("1");
		}

		admCartaPau.setFechaRegistro(fechaRegistro);
		admCartaPau.setFechaOrientacion(fechaOrientacion);
		admCartaPau.setFechaApertura(fechaApertura); 
		admCartaPau.setFechaInicio(fechaInicio);
		admCartaPau.setFechaCierre(fechaCierre);

		if(admCartaPauDao.existeReg(cartaId)){
			if(admCartaPauDao.updateReg(admCartaPau)){
				mensaje = "1";
			}else{
				mensaje = "0";
			}
		}else{
			if(admCartaPauDao.insertReg(admCartaPau)){
				mensaje = "1";				
			}else{
				mensaje = "0";
			}
		}

		return "redirect:/admlinea/admision/parametrosCarta?Mensaje="+mensaje;
	}

	@RequestMapping("/admlinea/admision/borrarPau")
	public String admlineaAdmisionEliminarParametrosPau(HttpServletRequest request, Model modelo){
		String cartaId 			= request.getParameter("cartaId") == null ? "0" : request.getParameter("cartaId");
		String mensaje 			= "";

		if(admCartaPauDao.deleteReg(cartaId)){
			mensaje = "1";
		}else{
			mensaje = "0";
		}

		return "redirect:/admlinea/admision/parametrosCarta?Mensaje="+mensaje;
	}

	@RequestMapping("/admlinea/admision/grabarSonoma")
	public String admlineaAdmisionGrabarParametrosSonoma(HttpServletRequest request, Model modelo){
		String cartaId 			= request.getParameter("cartaId") == null ? "0" : request.getParameter("cartaId");
		String fechaFinal 		= request.getParameter("fechaFinal") == null ? "0" : request.getParameter("fechaFinal");
		String mensaje 			= "";

		AdmCartaSonoma admCartaSonoma = new AdmCartaSonoma();

		if(admCartaSonomaDao.existeReg(cartaId)){
			admCartaSonoma = admCartaSonomaDao.mapeaRegId(cartaId);
		}else{
			admCartaSonoma.setCartaId("1");
		}

		admCartaSonoma.setFechaFinal(fechaFinal);

		if(admCartaSonomaDao.existeReg(cartaId)){
			if(admCartaSonomaDao.updateReg(admCartaSonoma)){
				mensaje = "1";
			}else{
				mensaje = "0";
			}
		}else{
			if(admCartaSonomaDao.insertReg(admCartaSonoma)){
				mensaje = "1";				
			}else{
				mensaje = "0";
			}
		}

		return "redirect:/admlinea/admision/parametrosCarta?Mensaje="+mensaje;
	}

	@RequestMapping("/admlinea/admision/borrarSonoma")
	public String admlineaAdmisionEliminarParametrosSonoma(HttpServletRequest request, Model modelo){
		String cartaId 			= request.getParameter("cartaId") == null ? "0" : request.getParameter("cartaId");
		String mensaje 			= "";

		if(admCartaSonomaDao.deleteReg(cartaId)){
			mensaje = "1";
		}else{
			mensaje = "0";
		}

		return "redirect:/admlinea/admision/parametrosCarta?Mensaje="+mensaje;
	}

	@RequestMapping("/admlinea/admision/grabarFulton")
	public String admlineaAdmisionGrabarParametrosFulton(HttpServletRequest request, Model modelo){
		String cartaId 			= request.getParameter("cartaId") == null ? "0" : request.getParameter("cartaId");
		String fechaRegistro 	= request.getParameter("fechaRegistroFulton") == null ? "0" : request.getParameter("fechaRegistroFulton");
		String fechaOrientacion = request.getParameter("fechaOrientacionFulton") == null ? "0" : request.getParameter("fechaOrientacionFulton");
		String fechaInicio 		= request.getParameter("fechaInicioFulton") == null ? "0" : request.getParameter("fechaInicioFulton");
		String fechaCierre 		= request.getParameter("fechaCierreFulton") == null ? "0" : request.getParameter("fechaCierreFulton");
		String fechaArribo 		= request.getParameter("fechaArriboFulton") == null ? "0" : request.getParameter("fechaArriboFulton");
		String mensaje 			= "";

		AdmCartaFulton admCartaFulton = new AdmCartaFulton();

		if(admCartaFultonDao.existeReg(cartaId)){
			admCartaFulton = admCartaFultonDao.mapeaRegId(cartaId);
		}else{
			admCartaFulton.setCartaId("1");
		}

		admCartaFulton.setFechaRegistro(fechaRegistro);
		admCartaFulton.setFechaOrientacion(fechaOrientacion);
		admCartaFulton.setFechaInicio(fechaInicio);
		admCartaFulton.setFechaCierre(fechaCierre);
		admCartaFulton.setFechaArribo(fechaArribo);

		if(admCartaFultonDao.existeReg(cartaId)){
			if(admCartaFultonDao.updateReg(admCartaFulton)){
				mensaje = "1";
			}else{
				mensaje = "0";
			}
		}else{
			if(admCartaFultonDao.insertReg(admCartaFulton)){
				mensaje = "1";				
			}else{
				mensaje = "0";
			}
		}

		return "redirect:/admlinea/admision/parametrosCarta?Mensaje="+mensaje;
	}

	@RequestMapping("/admlinea/admision/borrarFulton")
	public String admlineaAdmisionEliminarParametrosFulton(HttpServletRequest request, Model modelo){
		String cartaId 			= request.getParameter("cartaId") == null ? "0" : request.getParameter("cartaId");
		String mensaje 			= "";

		if(admCartaFultonDao.deleteReg(cartaId)){
			mensaje = "1";
		}else{
			mensaje = "0";
		}

		return "redirect:/admlinea/admision/parametrosCarta?Mensaje="+mensaje;
	}
	
	@RequestMapping("/admlinea/admision/cartaPauPDF")
	public String admlineaAdmisionCartaPauPDF(HttpServletRequest request, Model modelo){
		String folio 			= request.getParameter("Folio") == null ? "0" : request.getParameter("Folio");
		String nombre 			= request.getParameter("Nombre") == null ? "0" : request.getParameter("Nombre");
		String matricula 		= request.getParameter("Matricula") == null ? "0" : request.getParameter("Matricula");
		String curso 			= request.getParameter("Curso") == null ? "0" : request.getParameter("Curso");
		String ciclo 			= request.getParameter("Ciclo") == null ? "0" : request.getParameter("Ciclo");
		String acomodo 			= request.getParameter("Acomodo") == null ? "0" : request.getParameter("Acomodo");
		String fecha 			= request.getParameter("Fecha") == null ? "0" : request.getParameter("Fecha");
		String fechaRegistro 	= request.getParameter("fRegistro") == null ? "0" : request.getParameter("fRegistro");
		String fechaOrientacion = request.getParameter("fOrientacion") == null ? "0" : request.getParameter("fOrientacion");
		String fechaApertura 	= request.getParameter("fApertura") == null ? "0" : request.getParameter("fApertura");
		String fechaInicio 		= request.getParameter("fInicio") == null ? "0" : request.getParameter("fInicio");
		String fechaCierre 		= request.getParameter("fCierre") == null ? "0" : request.getParameter("fCierre");
		String fechaCierreEsc 	= request.getParameter("fCierreEsc") == null ? "0" : request.getParameter("fCierreEsc");
		String estadoResidencia = request.getParameter("Residencia") == null ? "0" : request.getParameter("Residencia");
		String direccionPostal 	= request.getParameter("Direccion") == null ? "0" : request.getParameter("Direccion");
		String fechaEscrita 	= request.getParameter("fEscrita") == null ? "0" : request.getParameter("fEscrita");
		String ciclos 			= request.getParameter("nCiclos") == null ? "0" : request.getParameter("nCiclos");

		modelo.addAttribute("folio",folio);
		modelo.addAttribute("nombre",nombre);
		modelo.addAttribute("matricula",matricula);
		modelo.addAttribute("curso",curso);
		modelo.addAttribute("ciclo",ciclo);
		modelo.addAttribute("acomodo", acomodo);
		modelo.addAttribute("fecha",fecha);
		modelo.addAttribute("fechaRegistro",fechaRegistro);
		modelo.addAttribute("fechaOrientacion",fechaOrientacion);
		modelo.addAttribute("fechaApertura",fechaApertura);
		modelo.addAttribute("fechaInicio",fechaInicio);
		modelo.addAttribute("fechaCierre",fechaCierre);
		modelo.addAttribute("fechaCierreEsc",fechaCierreEsc);
		modelo.addAttribute("estadoResidencia",estadoResidencia);
		modelo.addAttribute("direccionPostal",direccionPostal);
		modelo.addAttribute("fechaEscrita", fechaEscrita);
		modelo.addAttribute("ciclos", ciclos);
		
		return "admlinea/admision/cartaPauPDF";
	}

	@RequestMapping("/admlinea/admision/cartaSonomaPDF")
	public String admlineaAdmisionCartaSonomaPDF(HttpServletRequest request, Model modelo){
		String folio 			= request.getParameter("Folio") == null ? "0" : request.getParameter("Folio");
		String nombre 			= request.getParameter("Nombre") == null ? "0" : request.getParameter("Nombre");
		String matricula 		= request.getParameter("Matricula") == null ? "0" : request.getParameter("Matricula");
		String curso 			= request.getParameter("Curso") == null ? "0" : request.getParameter("Curso");
		String ciclo 			= request.getParameter("Ciclo") == null ? "0" : request.getParameter("Ciclo");
		String fecha 			= request.getParameter("Fecha") == null ? "0" : request.getParameter("Fecha");
		String numeroOferta 	= request.getParameter("nOferta") == null ? "0" : request.getParameter("nOferta");
		String numeroCiclos 	= request.getParameter("nCiclos") == null ? "0" : request.getParameter("nCiclos");
		String fechaFinal 		= request.getParameter("fFinal") == null ? "0" : request.getParameter("fFinal");
		String fechaFinalEscrita = request.getParameter("fFinalEscrita") == null ? "0" : request.getParameter("fFinalEscrita");
		
		modelo.addAttribute("folio",folio);
		modelo.addAttribute("nombre",nombre);
		modelo.addAttribute("matricula",matricula);
		modelo.addAttribute("curso", curso);
		modelo.addAttribute("ciclo",ciclo);
		modelo.addAttribute("fecha",fecha);
		modelo.addAttribute("numeroOferta",numeroOferta);
		modelo.addAttribute("numeroCiclos",numeroCiclos);
		modelo.addAttribute("fechaFinal",fechaFinal);
		modelo.addAttribute("fechaFinalEscrita", fechaFinalEscrita);
		
		return "admlinea/admision/cartaSonomaPDF";
	}

	@RequestMapping("/admlinea/admision/cartaFultonPDF")
	public String admlineaAdmisionCartaFultonPDF(HttpServletRequest request, Model modelo){
		String folio 			= request.getParameter("Folio") == null ? "0" : request.getParameter("Folio");
		String nombre 			= request.getParameter("Nombre") == null ? "0" : request.getParameter("Nombre");
		String matricula 		= request.getParameter("Matricula") == null ? "0" : request.getParameter("Matricula");
		String curso 			= request.getParameter("Curso") == null ? "0" : request.getParameter("Curso");
		String ciclo 			= request.getParameter("Ciclo") == null ? "0" : request.getParameter("Ciclo");
		String fecha 			= request.getParameter("Fecha") == null ? "0" : request.getParameter("Fecha");
		String fechaRegistro 	= request.getParameter("fRegistro") == null ? "0" : request.getParameter("fRegistro");
		String fechaOrientacion = request.getParameter("fOrientacion") == null ? "0" : request.getParameter("fOrientacion");
		String fechaInicio 		= request.getParameter("fInicio") == null ? "0" : request.getParameter("fInicio");
		String fechaCierre 		= request.getParameter("fCierre") == null ? "0" : request.getParameter("fCierre");
		String fechaArribo 		= request.getParameter("fArribo") == null ? "0" : request.getParameter("fArribo");
		String direccion 		= request.getParameter("Direccion") == null ? "0" : request.getParameter("Direccion");
		String numeroCiclos 	= request.getParameter("nCiclos") == null ? "0" : request.getParameter("nCiclos");

		modelo.addAttribute("folio",folio);
		modelo.addAttribute("nombre",nombre);
		modelo.addAttribute("matricula",matricula);
		modelo.addAttribute("curso", curso);
		modelo.addAttribute("ciclo",ciclo);
		modelo.addAttribute("fecha",fecha);
		modelo.addAttribute("fechaRegistro",fechaRegistro);
		modelo.addAttribute("fechaOrientacion",fechaOrientacion);
		modelo.addAttribute("fechaInicio",fechaInicio);
		modelo.addAttribute("fechaCierre",fechaCierre);
		modelo.addAttribute("fechaArribo",fechaArribo);
		modelo.addAttribute("direccion",direccion);
		modelo.addAttribute("numeroCiclos", numeroCiclos);
		
		return "admlinea/admision/cartaFultonPDF";
	}
}
