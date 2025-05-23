package aca.web.titulacion;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPlan;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.plan.spring.MapaPlan;
import aca.tit.spring.TitProfesional;
import aca.tit.spring.TitTramiteDao;
import aca.tit.spring.TitTramiteDocDao;
import aca.tit.spring.FirmaResponsables;
import aca.tit.spring.TitAlumno;
import aca.tit.spring.TitAntecedente;
import aca.tit.spring.TitCadena;
import aca.tit.spring.TitCadenaDao;
import aca.tit.spring.TitCarrera;
import aca.tit.spring.TituloElectronico;
import aca.wsdl.client.TituloWebService;
import aca.wsdl.sep.AccesoCadenaResponse;
import aca.tit.spring.TitExpedicion;
import aca.tit.spring.TitFirma;
import aca.tit.spring.TitInstitucion;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

@Controller
public class ContTitulacionTitulacion {
	
	@Autowired	
	private aca.alumno.spring.AlumPersonalDao alumPersonalDao;
	
	@Autowired	
	private aca.alumno.spring.AlumPlanDao alumPlanDao;
	
	@Autowired	
	private aca.plan.spring.MapaPlanDao mapaPlanDao;
	
	@Autowired	
	private aca.tit.spring.TitFirmaDao titFirmaDao;
	
	@Autowired	
	private aca.tit.spring.TitInstitucionDao titInstitucionDao;
	
	@Autowired	
	private aca.tit.spring.TitCarreraDao titCarreraDao;
	
	@Autowired	
	private aca.tit.spring.TitProfesionalDao titProfesionalDao;
	
	@Autowired	
	private aca.tit.spring.TitAntecedenteDao titAntecedenteDao;
	
	@Autowired	
	private aca.tit.spring.TitAlumnoDao titAlumnoDao;
	
	@Autowired	
	private aca.tit.spring.TitExpedicionDao titExpedicionDao;
	
	@Autowired	
	private aca.tit.spring.TitServicioDao titServicioDao;
	
	@Autowired	
	private aca.tit.spring.TitModalidadDao titModalidadDao;
	
	@Autowired	
	private aca.catalogo.spring.CatEstadoDao catEstadoDao;
	
	@Autowired	
	private aca.tit.spring.TitAutorizacionDao titAutorizacionDao;
	
	@Autowired	
	private aca.tit.spring.TitEstudioDao titEstudioDao;
	
	@Autowired	
	private aca.vista.spring.UsuariosDao usuariosDao;
	
	@Autowired	
	private TitTramiteDocDao titTramiteDocDao;
	
	@Autowired	
	private TitCadenaDao titCadenaDao;
	
	@Autowired	
	private TituloWebService tituloClient;
	
	@Autowired	
	private TitTramiteDao titTramiteDao;
	
	@Autowired	
	private CatCarreraDao catCarreraDao;
	
	@Autowired
	ServletContext context;
	
	
	@RequestMapping("/titulacion/titulacion/titulacion")
	public String titulacionTitulacionTitulacion(HttpServletRequest request, Model modelo){
		String codigoAlumno		= "0";
		String nombreAlumno		= "-";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoAlumno		= (String) sesion.getAttribute("codigoAlumno");
			// Si es alumno
			if (codigoAlumno.substring(0,1).equals("0") || codigoAlumno.substring(0,1).equals("1")){
				nombreAlumno = alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
			}
		}
		
		List<AlumPlan> lisPlanes 			= alumPlanDao.lisSinTitulo(codigoAlumno, " ORDER BY PLAN_ID");		
		List<TitAlumno> lisTitulos	 		= titAlumnoDao.lisAlumno(codigoAlumno, " ORDER BY FECHA, PLAN_ID");
		List<TitAlumno> lisConXml			= titAlumnoDao.lisConXml(" ORDER BY CODIGO_PERSONAL");
		
		HashMap<String,aca.plan.spring.MapaPlan> mapaPlanes 				= mapaPlanDao.mapPlanes("'A','V','I'");
		HashMap<String,aca.tit.spring.TitCarrera> mapaTitCarrera 			= titCarreraDao.mapaAll();
		HashMap<String,aca.tit.spring.TitProfesional> mapaTitProfesional 	= titProfesionalDao.mapaAll();
		HashMap<String,aca.tit.spring.TitExpedicion> mapaTitExpedicion 		= titExpedicionDao.mapaAll();
		HashMap<String,aca.tit.spring.TitAntecedente> mapaTitAntecedente 	= titAntecedenteDao.mapaAll();
		HashMap<String,String> mapaCarreraPlan 								= mapaPlanDao.mapCarreraPlan();
		HashMap<String,String> mapaTramites									= titTramiteDocDao.mapaTramitesAlumno(codigoAlumno);
				
		String btnGrabar = "";
		if(lisPlanes.isEmpty()) {
			btnGrabar = "disabled";
		}
		
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("btnGrabar", btnGrabar);
		
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("lisTitulos", lisTitulos);
		modelo.addAttribute("lisConXml", lisConXml);
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		modelo.addAttribute("mapaTitCarrera", mapaTitCarrera);
		modelo.addAttribute("mapaTitProfesional", mapaTitProfesional);
		modelo.addAttribute("mapaTitExpedicion", mapaTitExpedicion);
		modelo.addAttribute("mapaTitAntecedente", mapaTitAntecedente);
		modelo.addAttribute("mapaCarreraPlan", mapaCarreraPlan);
		modelo.addAttribute("mapaTramites", mapaTramites);		
		
		return "titulacion/titulacion/titulacion";
	}
	
	@RequestMapping("/titulacion/titulacion/grabarFolioSep")
	public String titulacionTitulacionGrabarFolioSep(HttpServletRequest request, Model modelo){
		String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String folioSep			= request.getParameter("FolioSep")==null?"0":request.getParameter("FolioSep");
		
		if (titAlumnoDao.existeReg(folio)){
			
			titAlumnoDao.updateFolioControl(folio, folioSep);
		}	
		
		return "redirect:/titulacion/titulacion/titulacion";
	}
	
	@RequestMapping("/titulacion/titulacion/subirAlumno")
	public String titulacionTitulacionSubirAlumno(HttpServletRequest request, Model modelo){
		String codigoAlumno		= request.getParameter("AlumnoXml")==null?"0":request.getParameter("AlumnoXml");		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			sesion.setAttribute("codigoAlumno",codigoAlumno);			
		}		
		return "redirect:/titulacion/titulacion/titulacion";
	}
	
	@RequestMapping("/titulacion/titulacion/grabar")
	public String titulacionTitulacionGrabar(HttpServletRequest request, Model modelo){
		
		String codigoAlumno		= "0";
		String institucion 		= "UM"; 
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			codigoAlumno		= (String)sesion.getAttribute("codigoAlumno");
		}
		
		String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String planId			= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String fecha			= aca.util.Fecha.getHoyReversa();		
		String estado			= request.getParameter("Estado")==null?"0":request.getParameter("Estado");		
		String year				= request.getParameter("Year")==null?"0":request.getParameter("Year");
		String carreraId 		= mapaPlanDao.getCarreraId(planId);
		if (carreraId.equals("10209") || carreraId.equals("10210") || carreraId.equals("10268")) {
			institucion = "COVOPROM";
		}
		
		TitAlumno alumno = new TitAlumno();
		alumno.setFolio(folio);
		alumno.setCodigoPersonal(codigoAlumno);
		alumno.setPlanId(planId);
		alumno.setFecha(fecha);
		alumno.setInstitucion(institucion);
		alumno.setXml("XML");
		alumno.setRespuesta("X");
		alumno.setEstado(estado);
		alumno.setFolioControl(codigoAlumno+planId);
		
		if (titAlumnoDao.existeReg(folio)){
			titAlumnoDao.updateReg(alumno);
		}else{
			alumno.setFolio(titAlumnoDao.maximoReg(institucion,year));
			titAlumnoDao.insertReg(alumno);
		}	
		
		return "redirect:/titulacion/titulacion/titulacion";
	}
	
	@RequestMapping("/titulacion/titulacion/borrar")
	public String titulacionTitulacionBorrar(HttpServletRequest request, Model modelo){		
		
		String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		if (titAlumnoDao.existeReg(folio)){
			titAlumnoDao.deleteReg(folio);
		}		
		
		return "redirect:/titulacion/titulacion/titulacion";
	}
	
	@RequestMapping("/titulacion/titulacion/xml")
	public String titulacionTitulacionXml(HttpServletRequest request, Model modelo){
		
		String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
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
			//jaxbMarshaller.marshal(titulo, System.out);			
			// Grabar xml en base de datos
			titAlumnoDao.updateXml(folio, xml);
			//titAlumnoDao.updateCadena(folio, cadenaOriginal);
			
			/* Metodo unmarshall ---- Ejemplo de como convertir el xml en un objeto TitElectronico */			
			TituloElectronico tituloUn = new TituloElectronico();
			tituloUn = (TituloElectronico)JAXBContext.newInstance(TituloElectronico.class).createUnmarshaller().unmarshal(new StringReader(xml));
			//System.out.println(tituloUn.getFolioControl()+":"+tituloUn.getCarrera().getNombreCarrera());
			
			}catch (JAXBException e) {
				e.printStackTrace();
		    }
		
		return "redirect:/titulacion/titulacion/titulacion";
	}
	
	@RequestMapping("/titulacion/titulacion/quitarXml")
	public String titulacionTitulacionQuitarXml(HttpServletRequest request, Model modelo){		
		String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		if (titAlumnoDao.existeReg(folio)){
			titAlumnoDao.updateEstado(folio, "A");
			titAlumnoDao.quitarXmlFolio(folio);
			titCadenaDao.quitarCadena(folio);
		}
					
		return "redirect:/titulacion/titulacion/titulacion";
	}
	
	@RequestMapping("/titulacion/titulacion/firma")
	public String titulacionTitulacionFirma(HttpServletRequest request, Model modelo){
		
		String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String institucion 		= titAlumnoDao.getInstitucion(folio);
		String codigoAlumno		= "0";
		String nombreAlumno		= "-";
		String planId			= "-";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoAlumno		= (String) sesion.getAttribute("codigoAlumno");
			// Si es alumno
			if (codigoAlumno.substring(0,1).equals("0") || codigoAlumno.substring(0,1).equals("1")){
				nombreAlumno = alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
			}
			planId	= titAlumnoDao.mapeaRegId(folio).getPlanId();
		}
		
		List<TitFirma> lisFirmas					= titFirmaDao.lisInstitucion(institucion, "ORDER BY PRIMERAPELLIDO, SEGUNDOAPELLIDO,NOMBRE");
		HashMap<String,String> mapaCertificados		= titFirmaDao.mapaCertificados();
		
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("lisFirmas", lisFirmas);
		modelo.addAttribute("mapaCertificados", mapaCertificados);
		
		return "titulacion/titulacion/firma";
	}
	
	@RequestMapping("/titulacion/titulacion/subircer")
	public String titulacionTitulacionSubircer(HttpServletRequest request, Model modelo){		
		String codigoPersonal		= request.getParameter("CodigoPersonal")==null?"0":request.getParameter("CodigoPersonal");
		String nombreUsuario 		= "-";
		
		nombreUsuario = usuariosDao.getNombreUsuario(codigoPersonal, "NOMBRE");
		modelo.addAttribute("nombreUsuario", nombreUsuario);
		
		return "titulacion/titulacion/subircer";
	}
	
	@PostMapping("/titulacion/titulacion/grabarCer")
	public String titulacionTitulacionGrabarCer(@RequestParam("certificado") MultipartFile certificado, HttpServletRequest request){
		String codigoPersonal	= request.getParameter("CodigoPersonal")==null?"0":request.getParameter("CodigoPersonal");
		String institucion		= request.getParameter("Institucion")==null?"0":request.getParameter("Institucion");
		String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
        
		TitFirma firma = new TitFirma();
		aca.tit.spring.Certificado cer = new aca.tit.spring.Certificado();	
		
		try{			
			InputStream archivoCer = new ByteArrayInputStream(certificado.getBytes());
			cer.getCertificadoYNumero(archivoCer);			
			if (titFirmaDao.existeReg(codigoPersonal, institucion)) {
				firma = titFirmaDao.mapeaRegId(codigoPersonal, institucion);
				firma.setCer(certificado.getBytes());
				firma.setCertificado(cer.getCertificado());
				firma.setNumeroCertificado(cer.getNoCertificado());
				titFirmaDao.updateReg(firma);
			}
		}catch(Exception ex){
			System.out.println("Error al grabar el certificado: "+ex);
		}
		
		return "redirect:/titulacion/titulacion/firma?Institucion="+institucion+"&Folio="+folio;
	}
	
	@RequestMapping("/titulacion/titulacion/institucion")
	public String titulacionTitulacionInstitucion(HttpServletRequest request, Model modelo){	
		
		String folio				= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String institucion			= titAlumnoDao.getInstitucion(folio);
		String codigoAlumno			= "0";
		String nombreAlumno			= "-";
		String planId				= "-";
		HttpSession sesion			= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoAlumno			= (String) sesion.getAttribute("codigoAlumno");
			// Si es alumno
			if (codigoAlumno.substring(0,1).equals("0") || codigoAlumno.substring(0,1).equals("1")){
				nombreAlumno = alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
			}
			planId	= titAlumnoDao.mapeaRegId(folio).getPlanId();
		}
		
		TitInstitucion titInstitucion	= new TitInstitucion();
		titInstitucion 					= titInstitucionDao.mapeaRegId(institucion);
		
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("institucion", institucion);
		modelo.addAttribute("titInstitucion", titInstitucion);
		
		return "titulacion/titulacion/institucion";
	}
	
	@RequestMapping("/titulacion/titulacion/cambiarInstitucion")
	public String titulacionTitulacionCambiarInstitucion(HttpServletRequest request, Model modelo){		
		
		String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String institucion		= request.getParameter("Institucion")==null?"0":request.getParameter("Institucion");		
		if (titAlumnoDao.existeReg(folio)){
			titAlumnoDao.updateInstitucion(folio, institucion);
		}		
		return "redirect:/titulacion/titulacion/institucion?Folio="+folio;
	}
	
	@RequestMapping("/titulacion/titulacion/carrera")
	public String titulacionTitulacionCarrera(HttpServletRequest request, Model modelo){
		
		String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String codigoAlumno		= "0";
		String nombreAlumno		= "-";
		String planId			= "-";
		boolean existe			= false;
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoAlumno		= (String) sesion.getAttribute("codigoAlumno");
			// Si es alumno
			if (codigoAlumno.substring(0,1).equals("0") || codigoAlumno.substring(0,1).equals("1")){
				nombreAlumno = alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
			}
			planId	= titAlumnoDao.mapeaRegId(folio).getPlanId();
		}
		
		TitCarrera titCarrera 	= new TitCarrera();
		MapaPlan plan 			= new MapaPlan();		
		TitAlumno titAlumno 	= new TitAlumno();
		String revoe		 	= "";
		String clave 			= "";
		String carreraNombre 	= "";
		
		if (titAlumnoDao.existeReg(folio)) {
			titAlumno = titAlumnoDao.mapeaRegId(folio);
			if (mapaPlanDao.existeReg(titAlumno.getPlanId())) {
				plan 			= mapaPlanDao.mapeaRegId(titAlumno.getPlanId());
				revoe 			= plan.getRvoe();
				clave 			= plan.getClaveProfesiones();
				carreraNombre 	= plan.getCarreraSe(); 
			}
		}
		
		List<aca.tit.spring.TitAutorizacion> lisAutorizacion 	= titAutorizacionDao.listAll("ORDER BY AUTORIZACION_ID");
		
		if (!folio.equals("0")){
			if (titCarreraDao.existeReg(folio)){
				titCarrera = titCarreraDao.mapeaRegId(folio);
				existe = true;
			}else{
				titCarrera.setFolio(folio);
			}		
		}
		
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("titCarrera", titCarrera);
		modelo.addAttribute("titAlumno", titAlumno);
		modelo.addAttribute("lisAutorizacion", lisAutorizacion);
		modelo.addAttribute("revoe", revoe);
		modelo.addAttribute("clave", clave);
		modelo.addAttribute("carreraNombre", carreraNombre);
		modelo.addAttribute("existe", existe);
		modelo.addAttribute("planId", planId);
		
		return "titulacion/titulacion/carrera";
	}
	
	@RequestMapping("/titulacion/titulacion/grabarCarrera")
	public String titulacionTitulacionGrabarCarrera(HttpServletRequest request, Model modelo){		
		String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String cveCarrera		= request.getParameter("CveCarrera")==null?"-":request.getParameter("CveCarrera");
		String nombreCarrera 	= request.getParameter("NombreCarrera")==null?"-":request.getParameter("NombreCarrera");
		String fechaInicio		= request.getParameter("FechaInicio")==null?"-":request.getParameter("FechaInicio");
		String fechaTerminacion	= request.getParameter("FechaTerminacion")==null?"-":request.getParameter("FechaTerminacion");
		String idAutorizacion	= request.getParameter("IdAutorizacion")==null?"-":request.getParameter("IdAutorizacion");
		String numeroRvoe		= request.getParameter("NumeroRvoe")==null?"-":request.getParameter("NumeroRvoe");		
		
		//cveCarrera = cveCarrera.replace("&", "&amp;").replace("\"", "&quot;").replace("<", "&lt;").replace(">", "&gt;").replace("'", "&apos;");
		//nombreCarrera = nombreCarrera.replace("&", "&amp;").replace("\"", "&quot;").replace("<", "&lt;").replace(">", "&gt;").replace("'", "&apos;");		
		//numeroRvoe = numeroRvoe.replace("&", "&amp;").replace("\"", "&quot;").replace("<", "&lt;").replace(">", "&gt;").replace("'", "&apos;");
		
		TitCarrera carrera 		= new TitCarrera(); 
		carrera.setFolio(folio);
		carrera.setCveCarrera(cveCarrera);
		carrera.setNombreCarrera(nombreCarrera);
		carrera.setFechaInicio(fechaInicio);
		carrera.setFechaTerminacion(fechaTerminacion);
		carrera.setIdAutorizacion(idAutorizacion);
		carrera.setAutorizacion(titAutorizacionDao.getNombreAutorizacion(idAutorizacion));
		carrera.setNumeroRvoe(numeroRvoe);
		
		if (!folio.equals("0")) {
			if (titCarreraDao.existeReg(folio)){
				titCarreraDao.updateReg(carrera);
			}else{			
				titCarreraDao.insertReg(carrera);
			}
		}
		
		return "redirect:/titulacion/titulacion/titulacion";
	}
	
	@RequestMapping("/titulacion/titulacion/borrarCarrera")
	public String titulacionTitulacionBorrarCarrera(HttpServletRequest request, Model modelo){		
		String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");	
		
		if (titCarreraDao.existeReg(folio)){
			titCarreraDao.deleteReg(folio);
		}		
		
		return "redirect:/titulacion/titulacion/carrera?Folio="+folio;
	}
	
	@RequestMapping("/titulacion/titulacion/profesional")
	public String titulacionTitulacionProfesional(HttpServletRequest request, Model modelo){
		String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String codigoAlumno		= "0";
		String nombreAlumno		= "-";
		String planId			= "-";
		boolean existe			= false;
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoAlumno		= (String) sesion.getAttribute("codigoAlumno");
			// Si es alumno
			if (codigoAlumno.substring(0,1).equals("0") || codigoAlumno.substring(0,1).equals("1")){
				nombreAlumno = alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
			}
			planId	= titAlumnoDao.mapeaRegId(folio).getPlanId();
		}
		
		TitAlumno titAlumno = new TitAlumno();
		if (titAlumnoDao.existeReg(folio)) {
			titAlumno = titAlumnoDao.mapeaRegId(folio);
		}
		
		TitProfesional profesional = new TitProfesional();
		AlumPersonal alumno	= new AlumPersonal();
		
		if (!folio.equals("0")){
			if (titProfesionalDao.existeReg(folio)){
				profesional = titProfesionalDao.mapeaRegId(folio);
				existe = true;
			}else{				
				profesional.setFolio(folio);
				alumno = alumPersonalDao.mapeaRegId(codigoAlumno);
				profesional.setCurp(alumno.getCurp());
				profesional.setNombre(alumno.getNombre());
				profesional.setPrimerApellido(alumno.getApellidoPaterno());
				profesional.setSegundoApellido(alumno.getApellidoMaterno());
				profesional.setCorreoElectronico(alumno.getEmail());
			}		
		}
		
		modelo.addAttribute("titAlumno", titAlumno);
		modelo.addAttribute("profesional", profesional);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("existe", existe);
		
		return "titulacion/titulacion/profesional";
	}
	
	@RequestMapping("/titulacion/titulacion/grabarProfesional")
	public String titulacionTitulacionGrabarProfesional(HttpServletRequest request, Model modelo){
		String folio		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String curp		= request.getParameter("Curp")==null?"-":request.getParameter("Curp");
		String nombre		= request.getParameter("Nombre")==null?"-":request.getParameter("Nombre");
		String primerApellido		= request.getParameter("PrimerApellido")==null?"-":request.getParameter("PrimerApellido");
		String segundoApellido		= request.getParameter("SegundoApellido")==null?"-":request.getParameter("SegundoApellido");
		String correo		= request.getParameter("Correo")==null?"-":request.getParameter("Correo");
		
		//curp = curp.replace("&", "&amp;").replace("\"", "&quot;").replace("<", "&lt;").replace(">", "&gt;").replace("'", "&apos;");
		//nombre = nombre.replace("&", "&amp;").replace("\"", "&quot;").replace("<", "&lt;").replace(">", "&gt;").replace("'", "&apos;");
		//primerApellido = primerApellido.replace("&", "&amp;").replace("\"", "&quot;").replace("<", "&lt;").replace(">", "&gt;").replace("'", "&apos;");		
		//segundoApellido = segundoApellido.replace("&", "&amp;").replace("\"", "&quot;").replace("<", "&lt;").replace(">", "&gt;").replace("'", "&apos;");
		//correo = correo.replace("&", "&amp;").replace("\"", "&quot;").replace("<", "&lt;").replace(">", "&gt;").replace("'", "&apos;");

		TitProfesional profesional = new TitProfesional(); 
		profesional.setFolio(folio);
		profesional.setCurp(curp);
		profesional.setNombre(nombre);
		profesional.setPrimerApellido(primerApellido);
		profesional.setSegundoApellido(segundoApellido);
		profesional.setCorreoElectronico(correo);
		if (!folio.equals("0")) {
			if (titProfesionalDao.existeReg(folio)){
				titProfesionalDao.updateReg(profesional);
			}else{			
				titProfesionalDao.insertReg(profesional);
			}
		}
		
		return "redirect:/titulacion/titulacion/titulacion";
	}
	
	@RequestMapping("/titulacion/titulacion/borrarProfesional")
	public String titulacionTitulacionBorrarProfesional(HttpServletRequest request, Model modelo){		
		String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");	
		
		if (titProfesionalDao.existeReg(folio)){
			titProfesionalDao.deleteReg(folio);
		}
		
		return "redirect:/titulacion/titulacion/profesional?Folio="+folio;
	}
	
	@RequestMapping("/titulacion/titulacion/folio")
	public String titulacionTitulacionFolio(HttpServletRequest request, Model modelo){
		
		String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String codigoAlumno		= "0";
		String nombreAlumno		= "-";		
		boolean existe			= false;
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoAlumno		= (String) sesion.getAttribute("codigoAlumno");
			// Si es alumno
			if (codigoAlumno.substring(0,1).equals("0") || codigoAlumno.substring(0,1).equals("1")){
				nombreAlumno = alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
			}			
		}
		
		TitAlumno titAlumno = new TitAlumno();
		if (titAlumnoDao.existeReg(folio)) {
			titAlumno = titAlumnoDao.mapeaRegId(folio);
			existe = true;
		}	
		
		modelo.addAttribute("titAlumno", titAlumno);		
		modelo.addAttribute("nombreAlumno", nombreAlumno);		
		modelo.addAttribute("existe", existe);
		
		return "titulacion/titulacion/folio";
	}
	
	@RequestMapping("/titulacion/titulacion/grabarFolio")
	public String titulacionTitulacionGrabarFolio(HttpServletRequest request, Model modelo){
		
		String folio				= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String folioFisico			= request.getParameter("FolioFisico")==null?"-":request.getParameter("FolioFisico");
		
		TitAlumno alumno = new TitAlumno(); 
		alumno.setFolio(folio);
		alumno.setFolioFisico(folioFisico);
		if (!folio.equals("0")) {
			if (titAlumnoDao.existeReg(folio)){				 
				titAlumnoDao.updateFolioFisico(folio, folioFisico);
			}
		}
		
		return "redirect:/titulacion/titulacion/titulacion";
	}
	
	@RequestMapping("/titulacion/titulacion/expedicion")
	public String titulacionTitulacionExpedicion(HttpServletRequest request, Model modelo){	
		
		String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String codigoAlumno		= "0";
		String nombreAlumno		= "-";
		String planId			= "-";
		boolean existe 			= false;
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoAlumno		= (String) sesion.getAttribute("codigoAlumno");
			// Si es alumno
			if (codigoAlumno.substring(0,1).equals("0") || codigoAlumno.substring(0,1).equals("1")){
				nombreAlumno = alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
			}
			planId	= titAlumnoDao.mapeaRegId(folio).getPlanId();
		}
		
		MapaPlan plan 		= mapaPlanDao.mapeaRegId(planId);
		CatCarrera carrera 	= catCarreraDao.mapeaRegId(plan.getCarreraId());
		String nivel 		= carrera.getNivelId();
		
		List<aca.tit.spring.TitModalidad> lisModalidad 	= titModalidadDao.listAll("ORDER BY MODALIDAD_ID");
		List<aca.tit.spring.TitServicio> lisFundamento 	= titServicioDao.listAll("ORDER BY SERVICIO_ID");
		List<aca.catalogo.spring.CatEstado> lisEstados 	= catEstadoDao.getLista("91", "ORDER BY NOMBRE_ESTADO");
		
		TitAlumno titAlumno = new TitAlumno();
		if (titAlumnoDao.existeReg(folio)) {
			titAlumno = titAlumnoDao.mapeaRegId(folio);
		}
		
		TitExpedicion titExpedicion = new TitExpedicion();
		
		if (!folio.equals("0")){
			if (titExpedicionDao.existeReg(folio)){
				titExpedicion = titExpedicionDao.mapeaRegId(folio);
				existe = true;
			}else{
				titExpedicion.setFolio(folio);
			}		
		}
		
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("titAlumno", titAlumno);
		modelo.addAttribute("titExpedicion", titExpedicion);
		modelo.addAttribute("lisModalidad", lisModalidad);
		modelo.addAttribute("lisFundamento", lisFundamento);
		modelo.addAttribute("lisEstados", lisEstados);
		modelo.addAttribute("existe", existe);
		modelo.addAttribute("nivel", nivel);
		
		return "titulacion/titulacion/expedicion";
	}

	@RequestMapping("/titulacion/titulacion/grabarExpedicion")
	public String titulacionTitulacionGrabarExpedicion(HttpServletRequest request, Model modelo){
		String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String fechaExpedicion	= request.getParameter("FechaExpedicion")==null?"1950-01-01":request.getParameter("FechaExpedicion");
		String modalidadId		= request.getParameter("ModalidadId")==null?"-":request.getParameter("ModalidadId");		
		String fechaExamen		= request.getParameter("FechaExamen")==null?"1950-01-01":request.getParameter("FechaExamen");
		String fechaExencion	= request.getParameter("FechaExencion")==null?"1950-01-01":request.getParameter("FechaExencion");
		String servicio			= request.getParameter("Servicio")==null?"-":request.getParameter("Servicio");
		String fundamentoId		= request.getParameter("FundamentoId")==null?"0":request.getParameter("FundamentoId");
		String entidadId		= request.getParameter("EntidadId")==null?"0":request.getParameter("EntidadId");
		
		TitExpedicion expedicion = new TitExpedicion();
		expedicion.setFolio(folio);
		expedicion.setFechaExpedicion(fechaExpedicion);
		expedicion.setModalidadId(modalidadId);
		expedicion.setModalidad(titModalidadDao.getNombreModalidad(modalidadId));
		expedicion.setFechaExamen(fechaExamen);
		expedicion.setFechaExencion(fechaExencion);
		expedicion.setServicio(servicio);
		expedicion.setFundamentoId(fundamentoId);
		expedicion.setFundamento(titServicioDao.getNombreFundamento(fundamentoId));
		expedicion.setEntidadId(entidadId);
		expedicion.setEntidad(catEstadoDao.getNombreEstado("91", entidadId));		
		
		if (!folio.equals("0")) {			
			if (titExpedicionDao.existeReg(folio)){				
				titExpedicionDao.updateReg(expedicion);
			}else{				
				titExpedicionDao.insertReg(expedicion);
			}			
		}
		
		return "redirect:/titulacion/titulacion/titulacion";
	}
	
	@RequestMapping("/titulacion/titulacion/borrarExpedicion")
	public String titulacionTitulacionBorrarExpedicion(HttpServletRequest request, Model modelo){		
		String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");	
		
		if (titExpedicionDao.existeReg(folio)){
			titExpedicionDao.deleteReg(folio);
		}
		
		return "redirect:/titulacion/titulacion/expedicion?Folio="+folio;
	}
	
	@RequestMapping("/titulacion/titulacion/antecedentes")
	public String titulacionTitulacionAntecedentes(HttpServletRequest request, Model modelo){	
		
		String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String codigoAlumno		= "0";
		String nombreAlumno		= "-";
		String planId			= "-";
		boolean existe			= false;
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoAlumno		= (String) sesion.getAttribute("codigoAlumno");
			// Si es alumno
			if (codigoAlumno.substring(0,1).equals("0") || codigoAlumno.substring(0,1).equals("1")){
				nombreAlumno = alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
			}
			planId	= titAlumnoDao.mapeaRegId(folio).getPlanId();
		}
		
		List<aca.tit.spring.TitEstudio> lisEstudio	 		= titEstudioDao.getLista("ORDER BY ESTUDIO_NOMBRE");
		List<aca.catalogo.spring.CatEstado> lisEstados 		= catEstadoDao.getLista("91", "ORDER BY NOMBRE_ESTADO");
		
		TitAlumno titAlumno = new TitAlumno();
		if (titAlumnoDao.existeReg(folio)) {
			titAlumno = titAlumnoDao.mapeaRegId(folio);
		}
		
		TitAntecedente antecedente = new TitAntecedente();
		if (!folio.equals("0")){
			if (titAntecedenteDao.existeReg(folio)){
				antecedente = titAntecedenteDao.mapeaRegId(folio);
				existe = true;
			}else{
				antecedente.setFolio(folio);
			}		
		}
		
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("titAlumno", titAlumno);
		modelo.addAttribute("antecedente", antecedente);
		modelo.addAttribute("lisEstudio", lisEstudio);
		modelo.addAttribute("lisEstados", lisEstados);
		modelo.addAttribute("existe", existe);
		
		return "titulacion/titulacion/antecedentes";
	}
	
	@RequestMapping("/titulacion/titulacion/grabarAntecedentes")
	public String titulacionTitulacionGrabarAntecedentes(HttpServletRequest request, Model modelo){	
		
		String folio				= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String institucion			= request.getParameter("Institucion")==null?"-":request.getParameter("Institucion");
		String estudioId			= request.getParameter("EstudioId")==null?"-":request.getParameter("EstudioId");		
		String entidadId			= request.getParameter("EntidadId")==null?"-":request.getParameter("EntidadId");
		String fechaInicio			= request.getParameter("FechaInicio")==null?"-":request.getParameter("FechaInicio");
		String fechaTerminacion		= request.getParameter("FechaTerminacion")==null?"-":request.getParameter("FechaTerminacion");
		String cedula				= request.getParameter("Cedula")==null?"":request.getParameter("Cedula");
		institucion = institucion.toUpperCase();	
		
		//institucion = institucion.replace("&", "&amp;").replace("\"", "&quot;").replace("<", "&lt;").replace(">", "&gt;").replace("'", "&apos;");

		//cedula = cedula.replace("&", "&amp;").replace("\"", "&quot;").replace("<", "&lt;").replace(">", "&gt;").replace("'", "&apos;");
		
		TitAntecedente antecedente = new TitAntecedente(); 
		antecedente.setFolio(folio);
		antecedente.setInstitucion(institucion);
		antecedente.setEstudioId(estudioId);
		antecedente.setEstudio(titEstudioDao.getEstudioNombre(estudioId));
		antecedente.setEntidadId(entidadId);
		antecedente.setEntidad(catEstadoDao.getNombreEstado("91", entidadId));
		antecedente.setFechaInicio(fechaInicio);
		antecedente.setFechaTerminacion(fechaTerminacion);
		antecedente.setCedula(cedula);
		if (!folio.equals("0")){
			if (titAntecedenteDao.existeReg(folio)){
				titAntecedenteDao.updateReg(antecedente);
			}else{			
				titAntecedenteDao.insertReg(antecedente);
			}
		}
		
		return "redirect:/titulacion/titulacion/titulacion";
	}
	
	@RequestMapping("/titulacion/titulacion/borrarAntecedente")
	public String titulacionTitulacionBorrarAntecedentes(HttpServletRequest request, Model modelo){		
		String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		
		if (titAntecedenteDao.existeReg(folio)){
			titAntecedenteDao.deleteReg(folio);
		}
		
		return "redirect:/titulacion/titulacion/antecedentes?Folio="+folio;
	}
	
	@RequestMapping(value={"/titulacion/titulacion/bajarXml"})
	public void titulacionTitulacionBajarXml(HttpServletRequest request, HttpServletResponse response){
		String folio 		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String xml			= "XML";
		TitAlumno titAlumno = new TitAlumno();
		
		// Si existe el titulo del alumno
		if (titAlumnoDao.existeReg(folio)){
			titAlumno = titAlumnoDao.mapeaRegId(folio);
			xml = titAlumno.getXml();			
		}		
		// Busca la imagen 
		try{			
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/xml");
			response.setHeader("Content-Disposition","attachment; filename=\""+folio+"-"+titAlumno.getCodigoPersonal()+".xml\"");	
			response.getOutputStream().write(xml.getBytes("UTF-8"));
			response.flushBuffer();			
		}catch(Exception ex){
			System.out.println("Error /titulacionTitulacionBajarXml:"+ex);
		}
	}
	
	@RequestMapping(value={"/titulacion/titulacion/bajarXmlSep"})
	public void titulacionTitulacionBajarXmlSep(HttpServletRequest request, HttpServletResponse response){
		String folio 			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		byte[] xml				= null;
		TitAlumno titAlumno 	= new TitAlumno();
		String tipoArchivo		= ".xml";		
		
		if (titAlumnoDao.existeReg(folio)){
			titAlumno 		= titAlumnoDao.mapeaRegId(folio);
			xml 			= titAlumno.getXmlSep();
			if (titAlumno.getRespuesta().contains("ZIP")) {
				tipoArchivo	= ".zip";
			}
		}		
		// Busca la imagen 
		try{			
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/octet-stream");
			//response.setContentType("text/xml");
			response.setHeader("Content-Disposition","attachment; filename=\""+folio+"-"+titAlumno.getCodigoPersonal()+titAlumno.getPlanId()+tipoArchivo+"\"");	
			response.getOutputStream().write(xml);
			response.flushBuffer();			
		}catch(Exception ex){
			System.out.println("Error /titulacionTitulacionBajarXmlSep:"+ex);
		}
	}
	
	@RequestMapping(value={"/titulacion/titulacion/enviarXML"})
	public String titulacionTitulacionEnviarXml(HttpServletRequest request ){
		String folio 		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");		
		String recibo 				= "0";
		
		if(titTramiteDocDao.existeReg(folio)) {
			String tramiteId = titTramiteDocDao.getTramiteId(folio);
			if (!tramiteId.equals("0")) {
				recibo = titTramiteDao.mapeaRegId(tramiteId).getRecibo();
			}			
		}
		
		AccesoCadenaResponse respuesta = tituloClient.getAccesoCadenaResult("http://app-msys.uienl.edu.mx:8044/WebPrueba/WsTitulo.asmx?wsdl",190101,"U78M79U8511MOB12","O77N85O7918NMO10", folio, recibo);		
		String mensaje		= respuesta.getAccesoCadenaResult();
		titAlumnoDao.updateRespuesta(folio, mensaje);
		
		return "redirect:/titulacion/titulacion/titulacion?Mensaje="+mensaje;
	}
	
	@RequestMapping(value={"/titulacion/titulacion/enviarCovopromXML"})
	public String titulacionTitulacionEnviarCovopromXML(HttpServletRequest request ){
		String folio 		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");		
		String recibo 				= "0";
		
		if(titTramiteDocDao.existeReg(folio)) {
			String tramiteId = titTramiteDocDao.getTramiteId(folio);
			if (!tramiteId.equals("0")) {
				recibo = titTramiteDao.mapeaRegId(tramiteId).getRecibo();
			}			
		}
		
		AccesoCadenaResponse respuesta = tituloClient.getAccesoCadenaResult("http://app-msys.uienl.edu.mx:8044/WebPrueba/WsTitulo.asmx?wsdl",190017,"U78M79U8511MOB12","O77N85O7918NMO10", folio, recibo);		
		String mensaje		= respuesta.getAccesoCadenaResult();
		titAlumnoDao.updateRespuesta(folio, mensaje);
		
		return "redirect:/titulacion/titulacion/titulacion?Mensaje="+mensaje;
	}	
}