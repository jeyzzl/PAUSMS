package aca.web.titulacion;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import aca.plan.spring.MapaPlan;
import aca.tit.spring.FirmaResponsables;
import aca.tit.spring.TitAlumno;
import aca.tit.spring.TitAlumnoDao;
import aca.tit.spring.TitAntecedente;
import aca.tit.spring.TitAntecedenteDao;
import aca.tit.spring.TitCadena;
import aca.tit.spring.TitCadenaDao;
import aca.tit.spring.TitCarrera;
import aca.tit.spring.TitCarreraDao;
import aca.tit.spring.TitExpedicion;
import aca.tit.spring.TitExpedicionDao;
import aca.tit.spring.TitFirma;
import aca.tit.spring.TitInstitucion;
import aca.tit.spring.TitInstitucionDao;
import aca.tit.spring.TitInstitucionUsuario;
import aca.tit.spring.TitInstitucionUsuarioDao;
import aca.tit.spring.TitProfesional;
import aca.tit.spring.TitProfesionalDao;
import aca.tit.spring.TitTramite;
import aca.tit.spring.TitTramiteDoc;
import aca.tit.spring.TituloElectronico;
import aca.wsdl.client.TituloWebService;
import aca.wsdl.sep.AccesoCadenaResponse;
import aca.wsdl.sep.DescargaResponse;


@Controller
public class ContTitulacionTramites {
	
	@Autowired	
	private aca.tit.spring.TitTramiteDao titTramiteDao;	
	
	@Autowired	
	private aca.tit.spring.TitTramiteDocDao titTramiteDocDao;
	
	@Autowired	
	private TitAlumnoDao titAlumnoDao;
	
	@Autowired	
	private aca.tit.spring.TitFirmaDao titFirmaDao;
	
	@Autowired	
	private aca.alumno.spring.AlumPersonalDao alumPersonalDao;
	
	@Autowired	
	private aca.plan.spring.MapaPlanDao mapaPlanDao;
	
	@Autowired	
	private TitCadenaDao titCadenaDao;
	
	@Autowired	
	private TituloWebService tituloClient;
	
	@Autowired	
	private TitInstitucionUsuarioDao titInstitucionUsuarioDao;
	
	@Autowired
	TitInstitucionDao titInstitucionDao;
	
	@Autowired
	TitCarreraDao titCarreraDao;
	
	@Autowired
	TitProfesionalDao titProfesionalDao;
	
	@Autowired
	TitExpedicionDao titExpedicionDao;
	
	@Autowired
	TitAntecedenteDao titAntecedenteDao;
	
	@Autowired
	ServletContext context;	
	
	
	@RequestMapping("/titulacion/tramites/tramite")
	public String titulacionTramitesTramite(HttpServletRequest request, Model modelo){
		
		String institucion 			= request.getParameter("Institucion")==null?"UM":request.getParameter("Institucion");
		String estado 				= request.getParameter("Estado")==null?"A":request.getParameter("Estado");		 
		String codigoPersonal		= "0";
		
		HttpSession sesion = ((HttpServletRequest) request).getSession();
		if (sesion != null) {
			codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");
			sesion.setAttribute("valInstitucion", institucion);
			sesion.setAttribute("valEstado", estado);
		}	
		
		List<aca.tit.spring.TitTramite> lisTramites	 	= titTramiteDao.lisInstitucion(institucion, estado, "ORDER BY FECHA, TRAMITE_ID");
		List<TitInstitucionUsuario> lisInstituciones	= titInstitucionUsuarioDao.lisInstitucionesPorUsuario(codigoPersonal, " ORDER BY INSTITUCION DESC");
		HashMap<String,String> mapaTotTitulos			= titTramiteDocDao.mapaTotTitulos();		
		HashMap<String,String> mapaSenl					= titTramiteDocDao.mapaTotalPorRespuesta("XML recibido en SENL");
		HashMap<String,String> mapaAutorizados			= titTramiteDocDao.mapaTotalPorRespuesta("ZIP-SEP");
		HashMap<String,String> mapaErrorFolio			= titTramiteDocDao.mapaSinFolioControl();
		
		modelo.addAttribute("lisInstituciones", lisInstituciones);
		modelo.addAttribute("lisTramites", lisTramites);
		modelo.addAttribute("mapaTotTitulos", mapaTotTitulos);
		modelo.addAttribute("mapaAutorizados", mapaAutorizados);
		modelo.addAttribute("mapaSenl", mapaSenl);
		modelo.addAttribute("mapaErrorFolio", mapaErrorFolio);
		
		return "titulacion/tramites/tramite";
	}
	
	@RequestMapping("/titulacion/tramites/editar")
	public String titulacionTramitesEditar(HttpServletRequest request, Model modelo){
		
		String tramite 			= request.getParameter("Tramite")==null?"0":request.getParameter("Tramite");
		String grabo 			= request.getParameter("Grabo")==null?"0":request.getParameter("Grabo");
		String numDoc			= titTramiteDao.getNumDoc(tramite);		
		boolean existe			= false;
		
		TitTramite	titTramite	= new TitTramite(); 
		if (titTramiteDao.existeReg(tramite)) {
			titTramite = titTramiteDao.mapeaRegId(tramite);
			existe = true;
		}else {
			titTramite.setTramiteId("0");
		}
		
		modelo.addAttribute("titTramite", titTramite);
		modelo.addAttribute("existe", existe);
		modelo.addAttribute("numDoc", numDoc);
		modelo.addAttribute("grabo", grabo);
		
		return "titulacion/tramites/editar";
	}
	
	@RequestMapping("/titulacion/tramites/grabar")
	public String titulacionTramitesGrabar(HttpServletRequest request, Model modelo){
		
		String tramiteId		= request.getParameter("Tramite")==null?"0":request.getParameter("Tramite");		
		String fecha			= request.getParameter("Fecha")==null?aca.util.Fecha.getHoyReversa():request.getParameter("Fecha");
		String descripcion		= request.getParameter("Descripcion")==null?"-":request.getParameter("Descripcion");
		String institucion		= request.getParameter("Institucion")==null?"UM":request.getParameter("Institucion");
		String estado			= request.getParameter("Estado")==null?"A":request.getParameter("Estado");		
		String recibo			= request.getParameter("Recibo")==null?"0":request.getParameter("Recibo");		
		String grabo			= "0";
		
		TitTramite	titTramite	= new TitTramite(); 
		titTramite.setTramiteId(tramiteId);
		titTramite.setFecha(fecha);
		titTramite.setDescripcion(descripcion);
		titTramite.setInstitucion(institucion);
		titTramite.setEstado(estado);
		titTramite.setRecibo(recibo);
		
		if (titTramiteDao.existeReg(tramiteId)) {
			if(titTramiteDao.updateReg(titTramite)) {
				grabo = "1";
			}
		}else {
			tramiteId = titTramiteDao.maximoFolio();
			titTramite.setTramiteId(tramiteId);
			if(titTramiteDao.insertReg(titTramite)) {
				grabo = "1";
			}
		}	
		
		return "redirect:/titulacion/tramites/editar?Tramite="+tramiteId+"&Estado="+estado+"&Grabo="+grabo;
	}
	
	@RequestMapping("/titulacion/tramites/borrar")
	public String titulacionTramitesBorrar(HttpServletRequest request, Model modelo){	
		
		String tramite 			= request.getParameter("Tramite")==null?"0":request.getParameter("Tramite");
		String institucion		= request.getParameter("Institucion")==null?"UM":request.getParameter("Institucion");
		String estado			= request.getParameter("Estado")==null?"A":request.getParameter("Estado");
		 
		if (titTramiteDao.existeReg(tramite)) {
			titTramiteDao.deleteReg(tramite);
		}
		
		return "redirect:/titulacion/tramites/tramite?Institucion="+institucion+"&Estado="+estado;
	}
	
	@RequestMapping("/titulacion/tramites/quitarXml")
	public String titulacionTramitesQuitarXml(HttpServletRequest request, Model modelo){	
		
		String tramite 			= request.getParameter("Tramite")==null?"0":request.getParameter("Tramite");		
		titAlumnoDao.quitarXml(tramite);
		
		return "redirect:/titulacion/tramites/tramite";
	}
	
	@RequestMapping("/titulacion/tramites/firmar")
	public String titulacionTramitesFirmar(HttpServletRequest request, Model modelo){	
		
		String tramite 			= request.getParameter("Tramite")==null?"0":request.getParameter("Tramite");
		String institucion 		= titTramiteDao.getInstitucion(tramite);

		List<aca.tit.spring.TitFirma> lisFirmas	 		= titFirmaDao.lisInstitucion(institucion, " ORDER BY PRIMERAPELLIDO");
		for (aca.tit.spring.TitFirma firma : lisFirmas) {
			try {
				InputStream cerUsuario = new ByteArrayInputStream(firma.getCer());
				
				aca.tit.spring.Certificado certificado = new aca.tit.spring.Certificado();
				certificado.getCertificadoYNumero(cerUsuario);
				
			}catch(Exception ex) {
				
			}	
		}
		
		modelo.addAttribute("lisFirmas", lisFirmas);		
		
		return "titulacion/tramites/firmar";
	}
	
	@RequestMapping("/titulacion/tramites/subirFirma")
	public String titulacionTramitesSubirFirma(HttpServletRequest request, Model modelo){		
		
		return "titulacion/tramites/subirFirma";
	}
	
	@PostMapping("/titulacion/tramites/grabarSello")
	public String titulacionTitulacionGrabarSello(@RequestParam("llave") MultipartFile llave, HttpServletRequest request){
		
		String institucion  	= request.getParameter("Institucion")==null?"UM":request.getParameter("Institucion");
		String codigoPersonal	= request.getParameter("CodigoPersonal")==null?"0":request.getParameter("CodigoPersonal");
		String tramite			= request.getParameter("Tramite")==null?"0":request.getParameter("Tramite");
		String clave			= request.getParameter("Clave")==null?"0":request.getParameter("Clave");
		String xmlTitulo		= "";
		String cadenaOriginal	= "";
		String mensaje			= "0";
		
		List<aca.tit.spring.TitAlumno> lisTitulos	 	= titAlumnoDao.lisTramite(tramite, " ORDER BY PLAN_ID, FECHA");		
		try{
			
			for (aca.tit.spring.TitAlumno titulo : lisTitulos) {
				
				// Obtener el archivo key
				InputStream archivoKey 	= new ByteArrayInputStream(llave.getBytes());
				
				xmlTitulo 				= titulo.getXml();
				// Obtener cadena de tit_cadena
				TitCadena cadena	 	= titCadenaDao.mapeaRegId(titulo.getFolio(), codigoPersonal);
				cadenaOriginal 			= cadena.getCadena();
								
				aca.tit.spring.Sello sello = new aca.tit.spring.Sello();
				
				if (sello.Sellado(archivoKey, clave, cadenaOriginal)) {
					//System.out.println(titulo.getCodigoPersonal()+":"+sello.getSignature());
					if (codigoPersonal.equals("9800146")) { xmlTitulo = xmlTitulo.replace("SELLORAQUEL", sello.getSignature()); }
					if (codigoPersonal.equals("9800052")) {	xmlTitulo = xmlTitulo.replace("SELLOISMAEL", sello.getSignature()); }					
					if (codigoPersonal.equals("9801097")) {	xmlTitulo = xmlTitulo.replace("SELLOJOSE", sello.getSignature()); }
					if (codigoPersonal.equals("9800812")) {	xmlTitulo = xmlTitulo.replace("SELLOANA", sello.getSignature()); }
					if (codigoPersonal.equals("9801085")) {	xmlTitulo = xmlTitulo.replace("SELLOJAIME", sello.getSignature()); }
					if (codigoPersonal.equals("9800308")) {	xmlTitulo = xmlTitulo.replace("SELLOERY", sello.getSignature()); }
					
					//System.out.println("Datos:"+codigoPersonal+":"+tramite+":"+clave+":"+titulo.getCodigoPersonal()+":"+sello.getSignature());
					
					titulo.setXml(xmlTitulo);
					if (titAlumnoDao.updateReg(titulo)) {
						cadena.setSello(sello.getSignature());
						titCadenaDao.updateReg(cadena);
						mensaje = "1";
					}
				}else {
					mensaje = "3";
				}							
			}			
		}catch(Exception ex){
			System.out.println("Error al grabar el sello: "+ex);
			mensaje = "2";
		}	
		
		return "redirect:/titulacion/tramites/firmar?Institucion="+institucion+"&Tramite="+tramite+"&CodigoPersonal="+codigoPersonal+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/titulacion/tramites/titulos")
	public String titulacionTramitesTitulos(HttpServletRequest request, Model modelo){	
		
		String tramite 			= request.getParameter("Tramite")==null?"0":request.getParameter("Tramite");
		String institucion 		= titTramiteDao.getInstitucion(tramite); 
		int totFirmas			= titFirmaDao.numFirmas(institucion);
		
		TitTramite	titTramite	= new TitTramite(); 
		if (titTramiteDao.existeReg(tramite)) {
			titTramite = titTramiteDao.mapeaRegId(tramite);
		}else {
			titTramite.setTramiteId("0");
		}
		
		List<aca.tit.spring.TitAlumno> lisAsignados	 	= titAlumnoDao.lisTramite(tramite, " ORDER BY ENOC.PLAN_NOMBRE(PLAN_ID), ALUM_APELLIDO(CODIGO_PERSONAL), FECHA");
		List<aca.tit.spring.TitAlumno> lisDisponibles	= titAlumnoDao.lisInstitucion(institucion, "A", " ORDER BY ENOC.PLAN_NOMBRE(PLAN_ID), ALUM_APELLIDO(CODIGO_PERSONAL)");
		HashMap<String, String> mapaAlumnos				= alumPersonalDao.mapaAlumnosConTitulo("APELLIDOS");
		HashMap<String, MapaPlan> mapaPlanes			= mapaPlanDao.mapPlanes("'A','V','I'");
		
		modelo.addAttribute("totFirmas", totFirmas);
		modelo.addAttribute("titTramite", titTramite);
		modelo.addAttribute("lisAsignados", lisAsignados);
		modelo.addAttribute("lisDisponibles", lisDisponibles);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("mapaPlanes", mapaPlanes);

		return "titulacion/tramites/titulos";
	}
	
	@RequestMapping("/titulacion/tramites/agregarTitulo")
	public String titulacionTramitesAgregarTitulo(HttpServletRequest request, Model modelo){	
		
		String tramite 			= request.getParameter("Tramite")==null?"0":request.getParameter("Tramite");
		String folio 			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		
		TitTramiteDoc titDoc	= new TitTramiteDoc();
		titDoc.setTramiteId(tramite); 
		titDoc.setFolio(folio);
		titDoc.setFecha(aca.util.Fecha.getHoyReversa());
		if (titTramiteDocDao.insertReg(titDoc) ) {
			titAlumnoDao.updateEstado(folio, "T");
		}
		
		return "redirect:/titulacion/tramites/titulos?Tramite="+tramite;
	}
	
	@RequestMapping("/titulacion/tramites/quitarTitulo")
	public String titulacionTramitesQuitarTitulo(HttpServletRequest request, Model modelo){
		String tramite 			= request.getParameter("Tramite")==null?"0":request.getParameter("Tramite");		
		String folio 			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");		
		if (titTramiteDocDao.deleteReg(tramite, folio) ) {
			titAlumnoDao.updateEstado(folio, "A");
		}		
		return "redirect:/titulacion/tramites/titulos?Tramite="+tramite;
	}

	@RequestMapping("/titulacion/tramites/alumnos")
	public String titulacionTramitesAlumnos(HttpServletRequest request, Model modelo){	
		
		String tramite		= request.getParameter("Tramite")==null?"1":request.getParameter("Tramite");	
		
		String institucion 	= "0";
		
		HttpSession sesion = ((HttpServletRequest) request).getSession();
		if (sesion != null) {
			institucion 		= (String) sesion.getAttribute("valInstitucion");			
		}	
		
		List<aca.tit.spring.TitAlumno> lisAlumnos	 	= titAlumnoDao.lisTramite(tramite, " ORDER BY ENOC.PLAN_NOMBRE(PLAN_ID), ALUM_APELLIDO(CODIGO_PERSONAL), FECHA");
		List<aca.tit.spring.TitFirma> lisFirmas	 		= titFirmaDao.lisInstitucion(institucion, " ORDER BY PRIMERAPELLIDO");
		HashMap<String, String> mapaAlumnos				= alumPersonalDao.mapaAlumnosConTitulo("APELLIDOS");
		HashMap<String, MapaPlan> mapaPlanes			= mapaPlanDao.mapPlanes("'A','V','I'");
		
		TitTramite	titTramite	= new TitTramite(); 
		if (titTramiteDao.existeReg(tramite)) {
			titTramite = titTramiteDao.mapeaRegId(tramite);			
		}else {
			titTramite.setTramiteId("0");
		}		
		modelo.addAttribute("titTramite", titTramite);
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("lisFirmas", lisFirmas);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		
		return "titulacion/tramites/alumnos";
	}
	
	@RequestMapping("/titulacion/tramites/grabarRecibos")
	public String titulacionTramitesGrabarRecibos(HttpServletRequest request, Model modelo){	
		
		String institucion 	= request.getParameter("Institucion")==null?"UM":request.getParameter("Institucion");
		String tramite		= request.getParameter("Tramite")==null?"1":request.getParameter("Tramite");	
		String estado 		= request.getParameter("Estado")==null?"A":request.getParameter("Estado");		
		
		List<TitAlumno> lisAlumnos	 	= titAlumnoDao.lisTramite(tramite, " ORDER BY ENOC.PLAN_NOMBRE(PLAN_ID), ALUM_APELLIDO(CODIGO_PERSONAL), FECHA");
		for (TitAlumno titAlumno : lisAlumnos) {
			String recibo = request.getParameter("FolioControl"+titAlumno.getCodigoPersonal())==null?"0":request.getParameter("FolioControl"+titAlumno.getCodigoPersonal());
			titAlumnoDao.updateFolioControl(titAlumno.getFolio(), recibo);
			// Modificar XML y Cadena
		}
		return "redirect:/titulacion/tramites/alumnos?Institucion="+institucion+"&Tramite="+tramite+"&Estado="+estado;
	}
	
	@RequestMapping("/titulacion/tramites/crearXml")
	public String titulacionTramitesCrearXml(HttpServletRequest request, Model modelo){
		
		String origen 			= request.getParameter("Origen")==null?"alumnos":request.getParameter("Origen");
		String tramite 			= request.getParameter("Tramite")==null?"0":request.getParameter("Tramite");
		String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		System.out.println("Datos:"+origen+":"+tramite+" Folio:"+folio);
		String cadenaOriginal 	= "";
		TitAlumno titAlumno 	= titAlumnoDao.mapeaRegId(folio);
		String institucion 		= titAlumno.getInstitucion();
		
		List<TitFirma> lisFirmas			= (List<TitFirma>)titFirmaDao.lisInstitucion(institucion, "ORDER BY PRIMERAPELLIDO, SEGUNDOAPELLIDO, NOMBRE");
		FirmaResponsables firmaResponsables = new FirmaResponsables();
		firmaResponsables.setFirmaResponsable(lisFirmas);
		
		TitInstitucion titInstitucion = new TitInstitucion();
		titInstitucion = titInstitucionDao.mapeaRegId(institucion);
		
		TitCarrera titCarrera = new TitCarrera();
		if (titCarreraDao.existeReg(folio)){
			titCarrera = titCarreraDao.mapeaRegId(folio);			
		}	
		
		TitProfesional titProfesional = new TitProfesional();
		if (titProfesionalDao.existeReg(folio)){
			titProfesional = titProfesionalDao.mapeaRegId(folio);
		}
		
		TitExpedicion titExpedicion = new TitExpedicion();
		if (titExpedicionDao.existeReg(folio)){
			titExpedicion = titExpedicionDao.mapeaRegId(folio);
		}
			
		TitAntecedente titAntecedente = new TitAntecedente();
		if (titAntecedenteDao.existeReg(folio)){
			titAntecedente = titAntecedenteDao.mapeaRegId(folio);
		}
		
		TituloElectronico titulo = new TituloElectronico();		
		titulo.setVersion(titulo.getVersion());
		titulo.setFolioControl(titAlumno.getFolioControl());
		
		// FORMAR LAS CADENAS ORIGINALES PARA GRABAR EN BD
		// Nodo de firmas responsables
		for (TitFirma firma : lisFirmas) {	
			
			// Nodo de título electronico		
			cadenaOriginal = "||"+titulo.getVersion()+"|"+titAlumno.getFolioControl()+"|";
			
			cadenaOriginal += firma.getCurp()+"|"+firma.getIdCargo()+"|"+firma.getCargo()+"|"+firma.getAbrTitulo()+"|";
			
			// Nodo de institución
			cadenaOriginal += titInstitucion.getCveInstitucion()+"|"+titInstitucion.getNombreInstitucion()+"|";
			
			// Nodo de Carrera
			cadenaOriginal += titCarrera.getCveCarrera()+"|"+titCarrera.getNombreCarrera()+"|"+titCarrera.getFechaInicio()+"|"+titCarrera.getFechaTerminacion()+"|"+titCarrera.getIdAutorizacion()+"|"+titCarrera.getAutorizacion()+"|"+titCarrera.getNumeroRvoe()+"|";
			
			// Nodo Profesionista
			cadenaOriginal += titProfesional.getCurp()+"|"+titProfesional.getNombre()+"|"+titProfesional.getPrimerApellido()+"|"+titProfesional.getSegundoApellido()+"|"+titProfesional.getCorreoElectronico()+"|";
			
			// Nodo Expedicion 
			cadenaOriginal += titExpedicion.getFechaExpedicion()+"|"+titExpedicion.getModalidadId()+"|"+titExpedicion.getModalidad()+"|"+titExpedicion.getFechaExamen()+"|"+titExpedicion.getFechaExencion()+"|"+
							titExpedicion.getServicio()+"|"+titExpedicion.getFundamentoId()+"|"+titExpedicion.getFundamento()+"|"+titExpedicion.getEntidadId()+"|"+titExpedicion.getEntidad()+"|";

			// Nodo de Antecedentes
			cadenaOriginal += titAntecedente.getInstitucion()+"|"+titAntecedente.getEstudioId()+"|"+titAntecedente.getEstudio()+"|"+titAntecedente.getEntidadId()+"|"+titAntecedente.getEntidad()+"|"+
							titAntecedente.getFechaInicio()+"|"+titAntecedente.getFechaTerminacion()+"|"+titAntecedente.getCedula()+"||";
			
			cadenaOriginal = cadenaOriginal.replace("|null|", "||");
			
			//System.out.println("Datos:"+firma.getCodigoPersonal()+":"+firma.getCargo()+":"+folio+":"+cadenaOriginal);
			
			// Grabar las cadenas originales para los firmantes
			TitCadena cadena = new TitCadena();
			cadena.setFolio(folio);
			cadena.setCodigoPersonal(firma.getCodigoPersonal());
			cadena.setCadena(cadenaOriginal);
			cadena.setSello("SELLO");
			if (titCadenaDao.existeReg(folio, firma.getCodigoPersonal())){ 
				titCadenaDao.updateReg(cadena);
			}else {
				titCadenaDao.insertReg(cadena);
			}						
		}
		
		// Corregir los caracteres especiales para el xml
		titInstitucion.setInstitucion(titInstitucion.getInstitucion().replace("&", "&amp;").replace("\"", "&quot;").replace("<", "&lt;").replace(">", "&gt;").replace("'", "&apos;"));
		titProfesional.setNombre(titProfesional.getNombre().replace("&", "&amp;").replace("\"", "&quot;").replace("<", "&lt;").replace(">", "&gt;").replace("'", "&apos;"));
		titProfesional.setPrimerApellido(titProfesional.getPrimerApellido().replace("&", "&amp;").replace("\"", "&quot;").replace("<", "&lt;").replace(">", "&gt;").replace("'", "&apos;"));
		if (titProfesional.getSegundoApellido()!=null) {
			titProfesional.setSegundoApellido(titProfesional.getSegundoApellido().replace("&", "&amp;").replace("\"", "&quot;").replace("<", "&lt;").replace(">", "&gt;").replace("'", "&apos;"));
		}
		titProfesional.setCorreoElectronico(titProfesional.getCorreoElectronico().replace("&", "&amp;").replace("\"", "&quot;").replace("<", "&lt;").replace(">", "&gt;").replace("'", "&apos;"));
		
		// Nodos del título electrónico
		titulo.setFirmaResponsables(firmaResponsables);
		titulo.setInstitucion(titInstitucion);
		titulo.setCarrera(titCarrera);
		titulo.setProfesionista(titProfesional);
		titulo.setExpedicion(titExpedicion);
		titulo.setAntecedente(titAntecedente);
		
		try {
			//JAXBContext jaxbContext = JAXBContext.newInstance(my.generatedschema.dir.ObjectFactory.class);
			//DocumentType documentType = ((JAXBElement<DocumentType>) jaxbContext.createUnmarshaller().unmarshal(inputStream)).getValue();
			
			JAXBContext jaxbContext 	= JAXBContext.newInstance(TituloElectronico.class);
			Marshaller jaxbMarshaller 	= jaxbContext.createMarshaller();
			String xml = "";
			
			//jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);			
			jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");			
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);			
			jaxbMarshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION,"https://www.siged.sep.gob.mx/titulos/schema.xsd");
			StringWriter tituloWriter = new StringWriter();
			jaxbMarshaller.marshal(titulo, tituloWriter);
			xml = tituloWriter.toString().replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");						
			// Grabar xml en base de datos
			titAlumnoDao.updateXml(folio, xml);		
			
		}catch (JAXBException e) {
			e.printStackTrace();
		}
		
		if (origen.equals("alumnos")) {
			return "redirect:/titulacion/tramites/alumnos?Tramite="+tramite;
		}else {		
			return "redirect:/titulacion/tramites/titulos?Tramite="+tramite;
		}
	}	
	
	@RequestMapping(value={"/titulacion/tramites/enviarXML"})
	public String titulacionTitulacionEnviarXml(HttpServletRequest request ){
		String folio 		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String institucion  		= request.getParameter("Institucion")==null?"UM":request.getParameter("Institucion");
		String tramite 				= request.getParameter("Tramite")==null?"0":request.getParameter("Tramite");
		String estado 				= request.getParameter("Estado")==null?"A":request.getParameter("Estado");
		String recibo 				= "0";
		
		if(titTramiteDocDao.existeReg(folio)) {
			String tramiteId = titTramiteDocDao.getTramiteId(folio);
			if (!tramiteId.equals("0")) {
				recibo = titTramiteDao.mapeaRegId(tramiteId).getRecibo(); 
			}			
		}		
		AccesoCadenaResponse respuesta = tituloClient.getAccesoCadenaResult("http://app-msys.uienl.edu.mx:8044/WebProduccionTitulo/WsTitulo.asmx?wsdl",190101,"U78M79U8511MOB12","O77N85O7918NMO10", folio, recibo);
		String mensaje		= respuesta.getAccesoCadenaResult();	
		
		titAlumnoDao.updateRespuesta(folio, mensaje);
		
		return "redirect:/titulacion/tramites/alumnos?Institucion="+institucion+"&Tramite="+tramite+"&Estado="+estado;
	}
	
	@RequestMapping(value={"/titulacion/tramites/enviarCovopromXML"})
	public String titulacionTitulacionEnviarCovopromXml(HttpServletRequest request ){
		String folio 		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String institucion  		= request.getParameter("Institucion")==null?"UM":request.getParameter("Institucion");
		String tramite 				= request.getParameter("Tramite")==null?"0":request.getParameter("Tramite");
		String estado 				= request.getParameter("Estado")==null?"A":request.getParameter("Estado");
		String recibo 				= "0";
		
		if(titTramiteDocDao.existeReg(folio)) {
			String tramiteId = titTramiteDocDao.getTramiteId(folio);
			if (!tramiteId.equals("0")) {			
				recibo = titTramiteDao.mapeaRegId(tramiteId).getRecibo();					 
			}			
		}		
		//System.out.println("Recibo:"+recibo);
		//AccesoCadenaResponse respuesta = tituloClient.getAccesoCadenaResult("http://app-msys.uienl.edu.mx:8044/WebPrueba/WsTitulo.asmx?wsdl",190017,"E83N79E6924NOM25","O78S69O7939SNO21", folio, "190017");		
		AccesoCadenaResponse respuesta = tituloClient.getAccesoCadenaResult("http://app-msys.uienl.edu.mx:8044/WebProduccionTitulo/WsTitulo.asmx?wsdl",190017,"E83N80E6924NOM26","O78S6917939SNO22", folio, recibo);
		String mensaje		= respuesta.getAccesoCadenaResult();
		
		titAlumnoDao.updateRespuesta(folio, mensaje);
		
		return "redirect:/titulacion/tramites/alumnos?Institucion="+institucion+"&Tramite="+tramite+"&Estado="+estado;
	}
	
	@RequestMapping(value={"/titulacion/tramites/descargarXML"})
	public String titulacionTitulacionDescargarXml(HttpServletRequest request ) throws UnsupportedEncodingException{
		String folio 				= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String institucion  		= request.getParameter("Institucion")==null?"UM":request.getParameter("Institucion");
		String tramite 				= request.getParameter("Tramite")==null?"0":request.getParameter("Tramite");
		String estado 				= request.getParameter("Estado")==null?"A":request.getParameter("Estado");
		String dir					= context.getRealPath("/WEB-INF/xml/"+"/");
		String recibo 				= "0";
		
		if(titTramiteDocDao.existeReg(folio)) {
			String tramiteId = titTramiteDocDao.getTramiteId(folio);
			if (!tramiteId.equals("0")) {
				recibo = titTramiteDao.mapeaRegId(tramiteId).getRecibo(); 
			}			
		}
		
		DescargaResponse respuesta 	= tituloClient.getDescargaResult("http://app-msys.uienl.edu.mx:8044/WebProduccionTitulo/WsTitulo.asmx?wsdl", 190101, folio, recibo);
		String xmlSep 				= respuesta.getDescargaResult();
		if (xmlSep.length() > 1000){
			try {
				//Desencriptar zip
				byte[] tituloDesencriptado = java.util.Base64.getDecoder().decode(xmlSep);
				/*
				 //Abrir zip y leer archivo 				  
				InputStream is = new ByteArrayInputStream(tituloDesencriptado);
				ZipInputStream zis = new ZipInputStream(is);
				ZipEntry zipEntry = zis.getNextEntry();
				System.out.println("Size:"+tituloDesencriptado.length);
				//Descomprimir archivo zip y escribirlo en el directorio actual
				FileOutputStream fos = new FileOutputStream(dir+zipEntry.getName());
			    int leido;
			    byte [] bytesSalida = new byte[(int)zipEntry.getSize()];
			    while (0<(leido=zis.read(bytesSalida))){
			       fos.write(bytesSalida,0,leido);
			    }
			    fos.flush();		    
			    fos.close();
			    zis.closeEntry();
			    
			    //Leer el archivo xml para obetener los bytes a grabar en el campo xmlSep
			    InputStream instream = new FileInputStream(dir+zipEntry.getName());
				byte[] bytesEntrada = new byte[(int)zipEntry.getSize()];
				instream.read(bytesEntrada);
				//String xml = new String(bytesEntrada);
				//System.out.println(xml);
				instream.close();
				
				// Borrar el archivo .xml
			    File fileXml = new File(dir+zipEntry.getName());
			    if (fileXml.exists()) {
			    	fileXml.delete();
			    }
			    */
				String respuestaAnterior = titAlumnoDao.getRespuesta(folio);
				if (titAlumnoDao.updateXmlSep(folio, tituloDesencriptado)){
					if(titAlumnoDao.updateRespuesta(folio, "ZIP-SEP")) {
						if (!respuestaAnterior.equals("ZIP-SEP")) {
							titAlumnoDao.updateFechaRes(folio, aca.util.Fecha.getHoyReversa());
						}
					}				
				}				
			}catch(Exception ex) {
				System.out.println("Error en titulacion/tramites/descargarXML:"+ex);
			}			
		}else {
			titAlumnoDao.updateRespuesta(folio, xmlSep);
		}	
		
		
		return "redirect:/titulacion/tramites/alumnos?Institucion="+institucion+"&Tramite="+tramite+"&Estado="+estado;
	}
	
	@RequestMapping(value={"/titulacion/tramites/descargarCovopromXML"})
	public String titulacionTitulacionDescargarCovopromXml(HttpServletRequest request ) throws UnsupportedEncodingException{
		String folio 				= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String institucion  		= request.getParameter("Institucion")==null?"UM":request.getParameter("Institucion");
		String tramite 				= request.getParameter("Tramite")==null?"0":request.getParameter("Tramite");
		String estado 				= request.getParameter("Estado")==null?"A":request.getParameter("Estado");		
		String recibo 				= "0";
		
		if(titTramiteDocDao.existeReg(folio)) {
			String tramiteId = titTramiteDocDao.getTramiteId(folio);
			if (!tramiteId.equals("0")) {
				recibo = titTramiteDao.mapeaRegId(tramiteId).getRecibo(); 
			}			
		}
		
		//DescargaResponse respuesta 	= tituloClient.getDescargaResult("http://app-msys.uienl.edu.mx:8044/WebPrueba/WsTitulo.asmx?wsdl", 190017, folio, "190017");
		DescargaResponse respuesta 	= tituloClient.getDescargaResult("http://app-msys.uienl.edu.mx:8044/WebProduccionTitulo/WsTitulo.asmx?wsdl", 190017, folio, recibo);	
		String xmlSep 				= respuesta.getDescargaResult();
		if (xmlSep.length() > 1000){
			try {
				//Desencriptar zip
				byte[] tituloDesencriptado = java.util.Base64.getDecoder().decode(xmlSep);
				
				String respuestaAnterior = titAlumnoDao.getRespuesta(folio);
				if (titAlumnoDao.updateXmlSep(folio, tituloDesencriptado)){
					if(titAlumnoDao.updateRespuesta(folio, "ZIP-SEP")) {
						if (!respuestaAnterior.equals("ZIP-SEP")) {
							titAlumnoDao.updateFechaRes(folio, aca.util.Fecha.getHoyReversa());
						}
					}				
				}				
			}catch(Exception ex) {
				System.out.println("Error en titulacion/tramites/descargarXML:"+ex);
			}			
		}else {
			titAlumnoDao.updateRespuesta(folio, xmlSep);
		}		
		return "redirect:/titulacion/tramites/alumnos?Institucion="+institucion+"&Tramite="+tramite+"&Estado="+estado;
	}
	
	@RequestMapping(value={"/titulacion/tramites/actualizarXML"})
	public String titulacionTitulacionActualizarXml(HttpServletRequest request ) throws UnsupportedEncodingException{
		String tramite		= request.getParameter("Tramite")==null?"1":request.getParameter("Tramite");	
		String estado 		= request.getParameter("Estado")==null?"A":request.getParameter("Estado");
		String institucion 	= request.getParameter("Institucion")==null?"UM":request.getParameter("Institucion");
		
		List<TitAlumno> lisAlumnos = titAlumnoDao.lisTramite(tramite, " ORDER BY PLAN_ID, FECHA");
		
		for(TitAlumno alumno : lisAlumnos) {
			String folio 	= alumno.getFolio();
			//String dir	 	= context.getRealPath("/WEB-INF/xml/"+"/");
			String recibo 	= "0";			
			if(titTramiteDocDao.existeReg(folio)) {
				String tramiteId = titTramiteDocDao.getTramiteId(folio);
				if (!tramiteId.equals("0")) {					
					recibo = titTramiteDao.mapeaRegId(tramiteId).getRecibo();										 
				}			
			}
			
			String claveDgp = institucion.equals("UM")?"190101":"190017";
			// Recibo de pruebas para covoprom
			//if (institucion.equals("COVOPROM")) recibo = "190017";
			DescargaResponse respuesta 	= tituloClient.getDescargaResult("http://app-msys.uienl.edu.mx:8044/WebProduccionTitulo/WsTitulo.asmx?wsdl", Integer.parseInt(claveDgp), folio, recibo);
			String xmlSep 				= respuesta.getDescargaResult();
			if (xmlSep.length() > 1000){				
				try {
					//Desencriptar zip
					byte[] tituloDesencriptado = java.util.Base64.getDecoder().decode(xmlSep);					
					
					String respuestaAnterior = titAlumnoDao.getRespuesta(folio);
					if (titAlumnoDao.updateXmlSep(folio, tituloDesencriptado)){						
						if(titAlumnoDao.updateRespuesta(folio, "ZIP-SEP")){					
							if (!respuestaAnterior.equals("ZIP-SEP")) {
								titAlumnoDao.updateFechaRes(folio, aca.util.Fecha.getHoyReversa());
							}
						}
					}				
				}catch(Exception ex) {
					System.out.println("Error en titulacion/tramites/descargarXML:"+ex);
				}			
			}else {
				titAlumnoDao.updateRespuesta(folio, xmlSep);
			}	
		}		
		return "redirect:/titulacion/tramites/tramite?Institucion="+institucion+"&Estado="+estado;
	}
	
}