package aca.web.archivo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import aca.admision.spring.AdmAcademico;
import aca.admision.spring.AdmAcademicoDao;
import aca.alumno.spring.AlumAcademico;
import aca.alumno.spring.AlumEstado;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPlan;
import aca.archivo.spring.ArchDocAlum;
import aca.archivo.spring.ArchDocStatus;
import aca.archivo.spring.ArchDocStatusDao;
import aca.archivo.spring.ArchDocumentos;
import aca.archivo.spring.ArchGrupo;
import aca.archivo.spring.ArchGrupoDao;
import aca.archivo.spring.ArchGrupoDocumento;
import aca.archivo.spring.ArchGrupoDocumentoDao;
import aca.archivo.spring.ArchGrupoPlanDao;
import aca.archivo.spring.ArchGrupos;
import aca.archivo.spring.ArchGruposCarreraDao;
import aca.archivo.spring.ArchGruposDao;
import aca.archivo.spring.ArchPermisos;
import aca.archivo.spring.ArchPermisosDao;
import aca.archivo.spring.ArchRevalida;
import aca.archivo.spring.ArchRevalidaDao;
import aca.archivo.spring.ArchStatus;
import aca.archivo.spring.ArchUbicacion;
import aca.pg.archivo.ArchGeneral;
import aca.pg.archivo.spring.PosArchDocAlum;
import aca.pg.archivo.spring.PosArchDocAlumDao;
import aca.pg.archivo.spring.PosArchGeneral;
import aca.pg.archivo.spring.PosArchGeneralDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;

@Controller
public class ContArchivoDocumentosAlumno {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired	
	private aca.alumno.spring.AlumPersonalDao alumPersonalDao;
	
	@Autowired	
	private aca.alumno.spring.AlumPlanDao alumPlanDao;
	
	@Autowired	
	private aca.alumno.spring.AlumAcademicoDao alumAcademicoDao;
	
	@Autowired	
	private aca.alumno.spring.AlumEstadoDao alumEstadoDao;
	
	@Autowired	
	private aca.carga.spring.CargaBloqueDao cargaBloqueDao;
	
	@Autowired	
	private aca.archivo.spring.ArchDocAlumDao archDocAlumDao;
	
	@Autowired	
	private aca.archivo.spring.ArchDocumentosDao archDocumentosDao;
	
	@Autowired	
	private aca.archivo.spring.ArchStatusDao archStatusDao;
	
	@Autowired	
	private aca.archivo.spring.ArchUbicacionDao archUbicacionDao;
	
	@Autowired	
	private aca.archivo.spring.ArchivoDao archivoDao;	
	
	@Autowired	
	private PosArchDocAlumDao posArchDocAlumDao;
	
	@Autowired	
	private PosArchGeneralDao posArchGeneralDao;
	
	@Autowired	
	private AdmAcademicoDao admAcademicoDao;
	
	@Autowired	
	private MapaPlanDao mapaPlanDao;
	
	@Autowired
	private ArchGruposDao archGruposDao;
	
	@Autowired
	private ArchPermisosDao archPermisosDao;
	
	@Autowired
	private ArchGruposCarreraDao archGruposCarreraDao;
	
	@Autowired
	private ArchGrupoPlanDao archGrupoPlanDao;
	
	@Autowired
	private ArchDocStatusDao archDocStatusDao;
	
	@Autowired
	private ArchGrupoDocumentoDao archGrupoDocumentoDao;
	
	@Autowired
	private ArchGrupoDao archGrupoDao;
	
	@Autowired
	private ArchRevalidaDao archRevalidaDao;	
		
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/archivo/documentos_alumno/accion")
	public String archivoDocumentosAlumnoAccion(HttpServletRequest request, Model modelo){
		
		String matricula 			= "0";	
		String idDocumento			= request.getParameter("IdDocumento");	
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			matricula			= (String) sesion.getAttribute("codigoAlumno");
		}		
		String mensaje 			= "-";
		
		ArchDocAlum documento 		= archDocAlumDao.mapeaRegId(matricula, idDocumento);
		
		if (archDocAlumDao.existeReg( matricula, idDocumento) == true){
			if (archDocAlumDao.deleteReg(matricula, idDocumento)){
				// Borrado en postgres	
				if (posArchDocAlumDao.existeRegDoc(matricula, idDocumento)){
					if (posArchDocAlumDao.deleteRegDoc(matricula, idDocumento)){
						mensaje = "<font size='3' color='blue'><b>File deleted (Record and Image)</b></font>";
					}else{						
						mensaje = "<font size='3' color='blue'><b>Error deleting file</b></font>";							
					}
				}else{
					mensaje = "<font size='3' color='blue'><b>File deleted (Record)</b></font>";			
				}
			}else{					
				mensaje = "<font size='3' color='red'><b>Error deleting: "+documento.getIdDocumento()+"</b></font>";
			}			
		}else{
			mensaje = "<font size='3' color='red'><b>Not found: "+documento.getIdDocumento()+"</b></font>";
		}
		
		return "redirect:/archivo/documentos_alumno/documentos?Mensaje="+mensaje;
	}
	
	@RequestMapping("/archivo/documentos_alumno/add")
	public String archivoDocumentosAlumnoAdd(HttpServletRequest request, Model modelo){
		
		String idDocumento		= request.getParameter("IdDocumento");
		String idStatus 		= request.getParameter("IdStatus");
		String fecha 			= request.getParameter("Fecha");
		String usuario 			= request.getParameter("Usuario");
		String cantidad 		= request.getParameter("Cantidad");
		String ubicacion 		= request.getParameter("Ubicacion");
		String opcion 			= request.getParameter("Opcion");		
		String matricula		= "0";
		String mensaje		 	= "";
		String color			= "red";
		
		ArchDocAlum documento 	= new ArchDocAlum();
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			matricula			= (String) sesion.getAttribute("codigoAlumno");
		}
		
		if (opcion.equals("I")){
			documento.setMatricula(matricula);
			documento.setIdDocumento(idDocumento);
			documento.setIdStatus(idStatus);
			documento.setFecha(fecha);
			documento.setUsuario(usuario);
			documento.setCantidad(cantidad);
			documento.setUbicacion(ubicacion);
			documento.setIncorrecto("N");
			if(!archDocAlumDao.existeReg(matricula, idDocumento)){
				if (archDocAlumDao.insertReg(documento)){
					mensaje = "Added File";
					color	= "blue";
				}else{
					mensaje = "Error saving. Try again";
				}
			}
		}else{
			documento.setMatricula(matricula);
			documento.setIdDocumento(idDocumento);
			documento.setIdStatus(idStatus);
			documento.setFecha(fecha); 
			documento.setUsuario(usuario);
			documento.setCantidad(cantidad);
			documento.setUbicacion(ubicacion);
			documento.setIncorrecto("N");
			if (archDocAlumDao.existeReg(matricula, idDocumento)){
				if (archDocAlumDao.updateReg(documento)){
					mensaje = "Updated File";
					color	= "blue";
				}else{
					mensaje = "Error updating. Try agian";
				}
			}else{
				mensaje = "File not found";
			}
		}
		
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("color", color);
		
		return "archivo/documentos_alumno/add";
	}
	
	@RequestMapping("/archivo/documentos_alumno/addimg")
	public String archivoDocumentosAlumnoAddImg(HttpServletRequest request, Model modelo){
		
		String codigoAlumno 		= "0";
		String codigoUsuario		= "0";
		String documentoId			= request.getParameter("DocumentoId")==null?"1":request.getParameter("DocumentoId");
		String hoja					= request.getParameter("Hoja")==null?"0":request.getParameter("Hoja");		
		String origen				= request.getParameter("Origen")==null?"1":request.getParameter("Origen");
		String documentoNombre		= "-";
		String carreraAlumno		= "-";
		boolean existeImagen 		= false; 
		AlumPersonal alumPersonal	= new AlumPersonal();
		AlumPlan alumPlan 			= new AlumPlan();
		
		HttpSession sesion			= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoAlumno 			= (String) sesion.getAttribute("codigoAlumno");
			codigoUsuario			= (String) sesion.getAttribute("codigoPersonal");
			if (archDocumentosDao.existeReg(documentoId)){
				documentoNombre 	= archDocumentosDao.getDescripcion(documentoId); 
			}
			if (alumPersonalDao.existeReg(codigoAlumno)){
				alumPersonal 		= alumPersonalDao.mapeaRegId(codigoAlumno);
			}
			alumPlan 				= alumPlanDao.mapeaRegId(codigoAlumno);
			carreraAlumno 			= mapaPlanDao.getCarreraSe(alumPlan.getPlanId());
			existeImagen 			= posArchDocAlumDao.existeReg(codigoAlumno, documentoId, hoja);	
		}	

		modelo.addAttribute("existeImagen", existeImagen);
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("documentoNombre", documentoNombre);
		modelo.addAttribute("alumPlan", alumPlan);
		modelo.addAttribute("carreraAlumno", carreraAlumno);
		
		return "archivo/documentos_alumno/addimg";	
	}
	
	@PostMapping("/archivo/documentos_alumno/subirImagen")
	public String archivoDocumentosAlumnoSubirImagen(@RequestParam("imagen") MultipartFile imagen, HttpServletRequest request){
		
		String codigoAlumno 		= "0";
		String codigoUsuario		= "0";
		String documentoId			= request.getParameter("DocumentoId")==null?"1":request.getParameter("DocumentoId");	
		String hoja					= request.getParameter("Hoja")==null?"0":request.getParameter("Hoja");		
		String origen				= request.getParameter("Origen")==null?"1":request.getParameter("Origen");
		String mensaje 				= "";
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoAlumno 		= (String) sesion.getAttribute("codigoAlumno");
			codigoUsuario		= (String) sesion.getAttribute("codigoPersonal");
		}	
		
		if(imagen.getOriginalFilename().contains(".jpg") || imagen.getOriginalFilename().contains(".jpeg")){
			try{			
				PosArchDocAlum documento	= new PosArchDocAlum();		
				if (posArchDocAlumDao.existeReg(codigoAlumno,documentoId, hoja)){
					documento = posArchDocAlumDao.mapeaRegId(codigoAlumno, documentoId, hoja);				
					documento.setOrigen(origen);
					documento.setUsuario(codigoUsuario);
					documento.setFuente("A");
					documento.setImagenByte(imagen.getBytes());
					documento.setImagen(1);
					documento.setEstado("B");				
					documento.setTipo("U");
					documento.setFupdate(aca.util.Fecha.getHoy());				
					posArchDocAlumDao.updateReg(documento);
				}else {
					documento.setMatricula(codigoAlumno);
					documento.setIddocumento(documentoId);
					documento.setHoja(hoja);
					documento.setOrigen(origen);
					documento.setUsuario(codigoUsuario);
					documento.setFuente("A");
					documento.setImagenByte(imagen.getBytes());
					documento.setImagen(1);
					documento.setEstado("B");				
					documento.setTipo("I");
					documento.setFupdate(aca.util.Fecha.getHoy());
					documento.setFinsert(aca.util.Fecha.getHoy());				
					posArchDocAlumDao.insertReg(documento);
				}
			}catch(Exception ex){
				System.out.println("Error saving image: "+ex);
			}

		}else{
			mensaje = "Must upload a .jpg or .jpeg file";
			return "redirect:/archivo/documentos_alumno/addimg?DocumentoId="+documentoId+"&Hoja="+hoja+"&Origen="+origen+"&Mensaje="+mensaje;
		}
		
		return "redirect:/archivo/documentos_alumno/addimg?DocumentoId="+documentoId+"&Hoja="+hoja+"&Origen="+origen;
	}

	@RequestMapping("/archivo/documentos_alumno/deleteimg")
	public String archivoDocumentosAlumnoDeleteImg(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ControllerArchivo|archivoDocumentosAlumnoDeleteImg");
		return "archivo/documentos_alumno/deleteimg";
	}
	
	@RequestMapping("/archivo/documentos_alumno/digital")
	public String archivoDocumentosAlumnoDigital(HttpServletRequest request, Model modelo){		
		
		String codigoAlumno 			= "0";		
		String documentoId				= request.getParameter("DocumentoId")==null?"0":request.getParameter("DocumentoId");
		String hoja						= request.getParameter("Hoja")==null?"0":request.getParameter("Hoja");
		String usuario					= request.getParameter("usuario");
		String alumnoNombre 			= "-";
		String documentoNombre 			= "-";
		
		AlumPersonal alumPersonal		= new AlumPersonal();		
		AlumPlan alumPlan				= new AlumPlan();		
		PosArchDocAlum imagenDoc		= new PosArchDocAlum();
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoAlumno 		= (String) sesion.getAttribute("codigoAlumno");			
			if (alumPersonalDao.existeAlumno(codigoAlumno)) {
				alumPersonal 		= alumPersonalDao.mapeaRegId(codigoAlumno);
				alumnoNombre 		= alumPersonal.getNombre()+" "+(alumPersonal.getApellidoMaterno().equals("-")?"":alumPersonal.getApellidoMaterno())+" "+alumPersonal.getApellidoPaterno();
				alumPlan			= alumPlanDao.mapeaRegId(codigoAlumno);
			}			
		}
		
		if (!documentoId.equals("0")) {
			documentoNombre = archDocumentosDao.getDescripcion(documentoId);
			if (!hoja.equals("0")) {				
				imagenDoc = posArchDocAlumDao.mapeaRegId(codigoAlumno, documentoId, hoja);
			}
		}
		
		List<ArchDocAlum> lisDocumentos				= archDocAlumDao.getListOne(codigoAlumno, "ORDER BY IDDOCUMENTO");
		List<PosArchDocAlum> lisImagenes 			= posArchDocAlumDao.lisImagenesAlumno(codigoAlumno," ORDER BY IDDOCUMENTO, HOJA");
		HashMap<String,String> mapaDocumentos 		= archDocumentosDao.mapaTodos();
		
		modelo.addAttribute("alumnoNombre", alumnoNombre);
		modelo.addAttribute("documentoNombre", documentoNombre);
		modelo.addAttribute("imagenDoc", imagenDoc);
		modelo.addAttribute("alumPlan", alumPlan);
		modelo.addAttribute("lisDocumentos", lisDocumentos);
		modelo.addAttribute("lisImagenes", lisImagenes);
		modelo.addAttribute("mapaDocumentos", mapaDocumentos);
		
		return "archivo/documentos_alumno/digital";		
	}
	
	@RequestMapping("/archivo/documentos_alumno/digitalIncorrecto")
	public String archivoDocumentosAlumnoDigitalIncorrecto(HttpServletRequest request) {
		
		String documentoId				= request.getParameter("DocumentoId")==null?"0":request.getParameter("DocumentoId");
		String codigoAlumno 			= "0";	
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoAlumno 		= (String) sesion.getAttribute("codigoAlumno");			
		}
		
		if (archDocAlumDao.existeReg(codigoAlumno,documentoId)) {
			ArchDocAlum documento = archDocAlumDao.mapeaRegId(codigoAlumno,documentoId);
			
			if(documento.getIncorrecto().equals("S")) {
				archDocAlumDao.marcarIncorrecto(codigoAlumno,documentoId,"N");
			}else {
				archDocAlumDao.marcarIncorrecto(codigoAlumno,documentoId,"S");
			}
		}
		
		return "redirect:/archivo/documentos_alumno/digital";
	}
	
	@RequestMapping("/archivo/documentos_alumno/documentos")
	public String archivoDocumentosAlumnoDocumentos(HttpServletRequest request, Model modelo){	
		
		//String IdStatus		= request.getParameter("IdStatus")==null?"0":request.getParameter("IdStatus");
		//String IdDocumento	= request.getParameter("IdDocumento")==null?"0":request.getParameter("IdDocumento");
		String planId 				= request.getParameter("PlanId") == null ? "0" : request.getParameter("PlanId").trim();
		String grupoId 				= request.getParameter("GrupoId") == null ? "0" : request.getParameter("GrupoId");
		String revalidaCodigoPer	= request.getParameter("CodigoPersonal") == null ? "0" : request.getParameter("CodigoPersonal");
		String borrar				= request.getParameter("BorrarRevalida") == null ? "0" : request.getParameter("BorrarRevalida");
		String codigoAlumno			= "0";		
		String folio				= "0";
		String alumCarrera			= "";
		String autorizaAlumno		= "";		
		String gruposCarrera 		= "";	
		
		AlumPersonal alumno 	= new AlumPersonal();
		AlumPlan plan			= new AlumPlan();
		AlumAcademico academico	= new AlumAcademico();
		AlumEstado estado		= new AlumEstado();
		
		AdmAcademico admAcademico = new AdmAcademico();
		ArchRevalida revalida 	  = new ArchRevalida();
		
		if(archRevalidaDao.existeReg(borrar)) {
			archRevalidaDao.deleteReg(borrar);
		}	
				
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoAlumno		= (String) sesion.getAttribute("codigoAlumno");	
			if (planId.equals("0")) {
				planId 				= alumPlanDao.getPlanActual(codigoAlumno);
			}
			
			alumCarrera			= alumPersonalDao.getCarrera(codigoAlumno);
			autorizaAlumno 		= archivoDao.autorizaAlumno(codigoAlumno, planId);			
			alumno 				= alumPersonalDao.mapeaRegId(codigoAlumno);
			plan 				= alumPlanDao.mapeaRegId(codigoAlumno);
			academico 			= alumAcademicoDao.mapeaRegId(codigoAlumno);						
			MapaPlan mapaPlan 	= mapaPlanDao.mapeaRegId(planId);			
			if(admAcademicoDao.existeRegPorMatriculaCarrera(codigoAlumno,mapaPlan.getCarreraId())) {
				admAcademico = admAcademicoDao.mapeaRegIdPorMatriculaCarrera(codigoAlumno,mapaPlan.getCarreraId());
				folio		 = admAcademico.getFolio();
			}else if (admAcademicoDao.existeRegPorMatricula(codigoAlumno)) {
				admAcademico = admAcademicoDao.mapeaRegIdPorMatricula(codigoAlumno);
				folio		 = admAcademico.getFolio();
			}			
			gruposCarrera 	= archGruposCarreraDao.getGruposCarrera(mapaPlan.getCarreraId());			
		}
		
		if(archRevalidaDao.existeReg(codigoAlumno)) {
			revalida = archRevalidaDao.mapeaRegId(codigoAlumno);
		}else {
			if(!revalidaCodigoPer.equals("0")) {
				revalida.setCodigoPersonal(revalidaCodigoPer);
				revalida.setRevalida("S");
				revalida.setFechaRevalida(aca.util.Fecha.getHoy());
				archRevalidaDao.insertReg(revalida);
			}
		}		
		
		List<ArchDocAlum> lisDocumentos 		= archDocAlumDao.getListAll(codigoAlumno, "ORDER BY ENOC.DOC_ORDEN(da.iddocumento)");
		List<ArchGrupos> lisGrupos				= archGruposDao.getListGrupo(grupoId, "");
		List<ArchPermisos> listaPermisos 		= archPermisosDao.getListThis(codigoAlumno, " ORDER BY ENOC.ARCH_PERMISOS.FECHA_LIM DESC");
		List<String> lisPlanes 					= alumPlanDao.getPlanesAlumno(codigoAlumno);
		List<String> gruposDelPlan 				= archGrupoPlanDao.gruposDelPlan(planId);
		List<ArchGrupoDocumento> lisPorGrupo	= archGrupoDocumentoDao.lisPorGrupo(grupoId, "");
		List<ArchDocStatus> lisValidos			= archDocStatusDao.lisStatusValidos(planId, " ORDER BY IDDOCUMENTO, IDSTATUS");
		
		HashMap<String,String> mapaDocumentos 			= archDocumentosDao.mapaTodos();
		HashMap<String,String> mapaStatus 				= archStatusDao.mapaStatus();
		HashMap<String,String> mapaUbicacion			= archUbicacionDao.mapaUbicacion();
		HashMap<String, String> mapaTotGrupo 			= archGruposDao.mapaTotPorGrupo();
		HashMap<String, String> mapaTotAlumno 			= archGruposDao.mapaGruposAlumno(codigoAlumno);
		HashMap<String, MapaPlan> mapaPlanes 			= mapaPlanDao.mapPlanes("'V','I','A'");
		HashMap<String, ArchDocStatus> mapaValidos 		= archDocStatusDao.mapaEstadosValidos();
		HashMap<String,Boolean> mapaDocCompletos		= new HashMap<String,Boolean>();
		HashMap<String, ArchGrupo> mapaGrupos 			= archGrupoDao.mapaArchGrupo();
		HashMap<String, ArchDocAlum> mapaDocAlumno 		= archDocAlumDao.mapArchDocAlumno(codigoAlumno);
		HashMap<String, ArchDocAlum> mapaBuscaDocumento = archDocAlumDao.mapaBuscaPorDocumento(codigoAlumno);

		int numImgGeneral = posArchGeneralDao.numImagenes(codigoAlumno);
		int numImgDocAlum = posArchDocAlumDao.numImagenes(codigoAlumno);
		
		for(String grupo : gruposDelPlan) {
			
			List<ArchGrupoDocumento> lisDoc		= archGrupoDocumentoDao.lisPorGrupo(grupo, "");
			int cont = 0;
			for (ArchGrupoDocumento doc : lisDoc) {
				 if (archDocAlumDao.existeReg(codigoAlumno, doc.getDocumentoId())){
					 ArchDocAlum docAlumno = archDocAlumDao.mapeaRegId(codigoAlumno, doc.getDocumentoId());
					 if (mapaValidos.containsKey(docAlumno.getIdDocumento()+"-"+docAlumno.getIdStatus())){
						 cont++;
					 }
				 }
			}
			if (lisDoc.size()==cont) mapaDocCompletos.put(grupo, true); else mapaDocCompletos.put(grupo, false);

		}
		
		modelo.addAttribute("folio", folio);
		modelo.addAttribute("alumCarrera", alumCarrera);
		modelo.addAttribute("autorizaAlumno", autorizaAlumno);
		modelo.addAttribute("alumno", alumno);
		modelo.addAttribute("revalida", revalida);
		modelo.addAttribute("plan", plan);
		modelo.addAttribute("academico", academico);
		modelo.addAttribute("estado", estado);		
		modelo.addAttribute("gruposCarrera", gruposCarrera);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("numImgGeneral", numImgGeneral);
		modelo.addAttribute("numImgDocAlum", numImgDocAlum);
		//modelo.addAttribute("docu", docu);
		//modelo.addAttribute("status", status);
		modelo.addAttribute("lisDocumentos", lisDocumentos);
		modelo.addAttribute("listaPermisos", listaPermisos);
		modelo.addAttribute("lisGrupos", lisGrupos);
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("lisPorGrupo", lisPorGrupo);
		modelo.addAttribute("lisValidos", lisValidos);
		modelo.addAttribute("gruposDelPlan", gruposDelPlan);
		modelo.addAttribute("mapaDocumentos", mapaDocumentos);
		modelo.addAttribute("mapaStatus", mapaStatus);
		modelo.addAttribute("mapaUbicacion", mapaUbicacion);
		modelo.addAttribute("mapaTotGrupo", mapaTotGrupo);
		modelo.addAttribute("mapaTotAlumno", mapaTotAlumno);
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		modelo.addAttribute("mapaDocCompletos", mapaDocCompletos);
		modelo.addAttribute("mapaGrupos", mapaGrupos);
		modelo.addAttribute("mapaDocAlumno", mapaDocAlumno);
		modelo.addAttribute("mapaBuscaDocumento", mapaBuscaDocumento);
		
		return "archivo/documentos_alumno/documentos";
	}
	
	@RequestMapping("/archivo/documentos_alumno/listaPermisosAlumno")
	public String portalesAlumnoListaPermisosAlumno(HttpServletRequest request, Model modelo) {		 
		String matricula 	= "0";
		String nombreAlumno = "-";
		
		HttpSession sesion = ((HttpServletRequest) request).getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
			nombreAlumno = alumPersonalDao.getNombreAlumno(matricula, "NOMBRE");
		}
		
		List<ArchPermisos> listaPermisos = archPermisosDao.getListThis(matricula, " ORDER BY ENOC.ARCH_PERMISOS.FECHA_LIM DESC");
		
		modelo.addAttribute("listaPermisos", listaPermisos);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		
		return "archivo/documentos_alumno/listaPermisosAlumno";
	}
	
	@RequestMapping("/archivo/documentos_alumno/editar")
	public String archivoDocumentosAlumnoEditar(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContArchivoDocumentosAlumno|archivoDocumentosAlumnoEditar");
		return "archivo/documentos_alumno/editar";
	}
	
	@RequestMapping("/archivo/documentos_alumno/borrarDocumento")
	public String archivoDocumentosAlumnoBorrarDocumento(HttpServletRequest request) throws SQLException{
		
		String matricula 		= request.getParameter("matricula")==null?"0":request.getParameter("matricula");
		String documentoId	 	= request.getParameter("documento")==null?"0":request.getParameter("documento");
		String hoja 			= request.getParameter("hoja")==null?"0":request.getParameter("hoja");		
		String comando 			= "-";
		
		Connection conn 		= null; 
		PreparedStatement ps	= null;
		PreparedStatement ps2	= null;
		ResultSet rset			= null;
    	try{
    		//coneccion a ATLAS
    		DriverManager.registerDriver (new org.postgresql.Driver());
    		conn		= DriverManager.getConnection(aca.conecta.Conectar.coneccion(),aca.conecta.Conectar.usuario(),aca.conecta.Conectar.password());    		
    		conn.setAutoCommit(false);   		
    		
    		if (posArchDocAlumDao.existeImagenHoja(matricula, documentoId, hoja)) {
    			boolean existeOid = false;
    			boolean desligado = false;
    			
    			if (posArchDocAlumDao.existeOID(matricula, documentoId, hoja)){
    				existeOid = true;
    				comando = "SELECT LO_UNLINK(IMAGEN) FROM ARCH_DOCALUM WHERE MATRICULA = ? AND IDDOCUMENTO = TO_NUMBER(?,'999') AND HOJA = TO_NUMBER(?,'99')";
    				ps = conn.prepareStatement(comando);
    				ps.setString(1, matricula);
    				ps.setString(2, documentoId);
    				ps.setString(3, hoja);
    				rset = ps.executeQuery();
    				if (rset.next()){
    					desligado = true;    				
    				}
    			}
				
				if (existeOid == false || desligado == true){
					
					comando = 	"DELETE FROM ARCH_DOCALUM WHERE MATRICULA = ? AND IDDOCUMENTO = TO_NUMBER(?,'999') AND HOJA = TO_NUMBER(?,'99')";
					ps2 = conn.prepareStatement(comando);
					ps2.setString(1, matricula);
					ps2.setString(2, documentoId);
					ps2.setString(3, hoja);
					if (ps2.executeUpdate()==1){
						conn.commit();					
					}else{
						conn.rollback();					
					}
				}
    		}else{    			
    			comando = 	"DELETE FROM ARCH_DOCALUM WHERE MATRICULA = ? AND IDDOCUMENTO = TO_NUMBER(?,'999') AND HOJA = TO_NUMBER(?,'99')";
    			ps = conn.prepareStatement(comando);
    			ps.setString(1, matricula);
    			ps.setString(2, documentoId);
    			ps.setString(3, hoja);
    			if (ps.executeUpdate()==1){
    				conn.commit();
    			}else{
    				conn.rollback();
    			}
    		}		
    		
    	}catch(Exception ex) {
    		System.out.println("Error: archivoDocumentosAlumnoBorrarDocumento:"+ex);
    	}finally {
    		if (conn!=null) conn.close();
    		try { ps.close(); } catch (Exception ignore) { }
    		if (ps2!=null) ps2.close();
    		if (rset!=null) rset.close();
    	}
		
		return "redirect:/archivo/documentos_alumno/digital";
	}
	
	/* Es una copia del método anterior utilizado para la pantalla de addImg */
	@RequestMapping("/archivo/documentos_alumno/borrarHoja")
	public String archivoDocumentosAlumnoBorrarHoja(HttpServletRequest request) throws SQLException{
		
		String matricula 		= request.getParameter("matricula")==null?"0":request.getParameter("matricula");
		String documentoId	 	= request.getParameter("documento")==null?"0":request.getParameter("documento");
		String hoja 			= request.getParameter("hoja")==null?"0":request.getParameter("hoja");		
		String mensaje			= "";

		if(posArchDocAlumDao.existeImagenHoja(matricula, documentoId, hoja)){
			if(posArchDocAlumDao.existeOID(matricula, documentoId, hoja)){
				if(posArchDocAlumDao.deleteReg(matricula, documentoId, hoja)){
					mensaje = "Deleted";
				}else{
					mensaje = "Error deleting image";
				}
			}else{
				if(posArchDocAlumDao.deleteRegDoc(matricula, documentoId, hoja)){
					mensaje = "Deleted";
				}else{
					mensaje = "Error deleting image";
				}
			}
		}else{
			mensaje = "No image found"; 
		}
		
		return "redirect:/archivo/documentos_alumno/addimg?DocumentoId="+documentoId+"&Hoja="+hoja+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/archivo/documentos_alumno/elegir_documento")
	public String archivoDocumentosAlumnoElegirDocumento(HttpServletRequest request, Model modelo){
		
		String IdDocumento	= request.getParameter("IdDocumento")==null?"0":request.getParameter("IdDocumento"); 
		
		String codigoAlumno	= "0";
		
		AlumPlan plan 		= new AlumPlan();
		
		HttpSession sesion	= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoAlumno 	= (String) sesion.getAttribute("codigoAlumno");
			plan 			= alumPlanDao.mapeaRegId(codigoAlumno);
		}
		
		List<ArchDocumentos> lisDocumentos 	= archDocumentosDao.getListOne(codigoAlumno, "ORDER BY DESCRIPCION");
		List<ArchStatus> lisStatus 			= archStatusDao.lisPorDocumentoyEstado(IdDocumento, "A"," ORDER BY DESCRIPCION");
		
		modelo.addAttribute("plan", plan);
		modelo.addAttribute("lisDocumentos", lisDocumentos);
		modelo.addAttribute("lisStatus", lisStatus);
		
		return "archivo/documentos_alumno/elegir_documento";
	}
	
	@RequestMapping("/archivo/documentos_alumno/foto")
	public String archivoDocumentosAlumnoFoto(HttpServletRequest request){
		return "archivo/documentos_alumno/foto";
	}
	
	@RequestMapping("/archivo/documentos_alumno/fotoGeneral")
	public String archivoDocumentosAlumnoFotoGeneral(HttpServletRequest request){
		return "archivo/documentos_alumno/fotoGeneral";
	}
	
	@RequestMapping("/archivo/documentos_alumno/fotoNew")
	public String archivoDocumentosAlumnoFotoNew(HttpServletRequest request){
		return "archivo/documentos_alumno/fotoNew";
	}
	
	@RequestMapping("/archivo/documentos_alumno/general")
	public String archivoDocumentosAlumnoGeneral(HttpServletRequest request, Model modelo){
		
		String codigoAlumno		= request.getParameter("codigoAlumno")==null?"0":request.getParameter("codigoAlumno");
		
		HttpSession sesion	= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			if(codigoAlumno.equals("0")) {
				codigoAlumno = (String) sesion.getAttribute("codigoAlumno");
			}else{
				sesion.setAttribute("codigoAlumno", codigoAlumno);
			}			
		}		
		
		String alumnoNombre 	= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
		List<PosArchGeneral> lisDocumentos	= posArchGeneralDao.lisAlumnos(codigoAlumno,"ORDER BY FOLIO");
		
		modelo.addAttribute("alumnoNombre", alumnoNombre);
		modelo.addAttribute("lisDocumentos", lisDocumentos);
		
		return "archivo/documentos_alumno/general";
	}	
	
	@RequestMapping("/archivo/documentos_alumno/generalAccion")
	public String archivoDocumentosAlumnoGeneralAccion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContArchivoDocumentosAlumno|archivoDocumentos_alumnoGeneralAccion");
		return "archivo/documentos_alumno/generalAccion";
	}
	
	@ResponseBody
	@RequestMapping("/archivo/documentos_alumno/verDocumentos")
	public String archivoDocumentosAlumnoVerDocumentos(HttpServletRequest request){
		
		String codigoAlumno		= "0";
		String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		HttpSession sesion	= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoAlumno 	= (String) sesion.getAttribute("codigoAlumno");
		}	
		
		List<ArchDocAlum> lisAsignados			= archDocAlumDao.getListOne(codigoAlumno, "ORDER BY NOMBRE_DOCUMENTO(IDDOCUMENTO)");
		List<ArchDocumentos> lisOtros			= archDocumentosDao.getListOne(codigoAlumno, "ORDER BY DESCRIPCION");
		HashMap<String, String> mapaDocumentos 	= archDocumentosDao.mapaTodos();
		HashMap<String, String> mapaStatus 		= archStatusDao.mapaStatus();
		
		String datos = ""
			+ "	<h4>Select File</h4>"
			+ "	<div style=\"position:relative; height:300px; overflow: auto; border: solid 1px black;\">"
			+ "		<table style=\"width:100%\" class=\"tabla\">"
			+ "		<tr>"
			+ "			<th colspan=\"3\"><b>Student Files</b></th>"
			+ "		</tr>";
		for(ArchDocAlum archDocAlumno : lisAsignados){
			String nombreDocumento = "-";
			if (mapaDocumentos.containsKey(archDocAlumno.getIdDocumento())) {
				nombreDocumento = mapaDocumentos.get(archDocAlumno.getIdDocumento());
			}
			
			String nombreStatus = "-";
			if (mapaStatus.containsKey(archDocAlumno.getIdStatus())) {
				nombreStatus = mapaStatus.get(archDocAlumno.getIdStatus());
			}
			int numImagenes = posArchDocAlumDao.numImagenes(codigoAlumno, archDocAlumno.getIdDocumento());
			
			datos += ""
			+ "		<tr class='button' onclick=\"$('statusBox').innerHTML=''; muestraNumHojas('"+archDocAlumno.getIdDocumento()+"', '"+archDocAlumno.getIdStatus()+"', '"+folio+"');\">"+
			"			<td>"+nombreDocumento+"</td>"+
			"			<td>"+nombreStatus+"</td>"+
			"			<td>"+numImagenes+"</td>"+
			"		</tr>";			
		}
		
		datos += "	</table>"
				+ "	<br>"
				+ "	<table style=\"width:100%\" class=\"tabla\">"
				+ "	<tr>"
				+ "		<th><b>Files not assigned to student</b></th>"
				+ "	</tr>";
		for(ArchDocumentos archDocumentos : lisOtros){
			datos += ""
			+ "		<tr class=\"button\" onclick=\"muestraStatus('"+archDocumentos.getIdDocumento()+"', '"+folio+"');\">"
			+ "			<td>"+archDocumentos.getDescripcion()+"</td>"
			+ "		</tr>";
		}
		datos += "</table>"
				+ "			</div>"
				+ "			<br>"
				+ "			<div style=\"position:relative; overflow: auto;\">"
				+ "				<table style=\"width:100%\">"
				+ "					<tr>"
				+ "						<td id=\"statusBox\"></td>"
				+ "					</tr>"
				+ "				</table>"
				+ "			</div>"
				+ "			<br>"
				+ "			<div style=\"position:relative; overflow: auto;\">"
				+ "				<table style=\"width:100%\">"
				+ "					<tr>"
				+ "						<td id=\"numHojaBox\"></td>"
				+ "					</tr>"
				+ "				</table>"
				+ "			</div>";			
		
		return datos;
	}
	
	@ResponseBody
	@RequestMapping("/archivo/documentos_alumno/verStatus")
	public String archivoDocumentosAlumnoVerStatus(HttpServletRequest request){		
		
		String documentoId		= request.getParameter("DocumentoId")==null?"0":request.getParameter("DocumentoId");
		String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String nombreDocumento	= archDocumentosDao.getDescripcion(documentoId);
		
		List<ArchDocStatus> lisStatus	= archDocStatusDao.lisStatus(documentoId, "ORDER BY IDSTATUS");
		
		String datos = "<h4>Select File Status</h4>"
			+ "	<table style=\"width:100%\" class=\"tabla\">"
			+ "	<tr>"
			+ "		<td align=\"center\"><b>"+nombreDocumento+"</b></td>"
			+ "	</tr>";		
		for(ArchDocStatus status : lisStatus) {			
			String nombreStatus		= archStatusDao.getDescripcion(status.getIdStatus());
			datos += ""
			+ "	<tr class=\"button\" onclick=\"muestraNumHojas('"+documentoId+"', '"+status.getIdStatus()+"', '"+folio+"');\">"
			+ "		<td>"+nombreStatus+"</td>"
			+ "	</tr>";
		}
		datos += "</table>";
		
		return datos;
	}	
	
	@RequestMapping("/archivo/documentos_alumno/verHojas")
	public String archivoDocumentosAlumnoVerHojas(HttpServletRequest request, Model modelo){		
		
		String documentoId		= request.getParameter("DocumentoId")==null?"0":request.getParameter("DocumentoId");
		String statusId			= request.getParameter("StatusId")==null?"0":request.getParameter("StatusId");		
		
		String nombreDocumento	= archDocumentosDao.getDescripcion(documentoId);
		String nombreStatus		= archStatusDao.getDescripcion(statusId);	
		
		modelo.addAttribute("nombreDocumento", nombreDocumento);
		modelo.addAttribute("nombreStatus", nombreStatus);		
		
		return "archivo/documentos_alumno/verHojas";
	}
	
	@Transactional
	@ResponseBody
	@RequestMapping("/archivo/documentos_alumno/asignar")
	public String archivoDocumentosAlumnoAsignar(HttpServletRequest request, Model modelo){
		
		String codigoAlumno					= "0";
		String iddocumento					= request.getParameter("iddocumento");
		String idstatus						= request.getParameter("idstatus");
		String folio						= request.getParameter("folio");
		String hoja							= request.getParameter("hoja");
		String origen						= request.getParameter("origen");
		String usuario						= "0";
		String resultado 					= "";
		HttpSession sesion	= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoAlumno	= (String) sesion.getAttribute("codigoAlumno");
			usuario 		= (String) sesion.getAttribute("codigoPersonal");
		}
		
		PosArchGeneral archGeneral 		= posArchGeneralDao.mapeaRegId(codigoAlumno, folio);
		ArchDocAlum archDocAlum 		= new ArchDocAlum();
		boolean guardoEnOracle 			= false;
		if(archDocAlumDao.existeReg(codigoAlumno, iddocumento)){
			archDocAlum = archDocAlumDao.mapeaRegId(codigoAlumno, iddocumento);
			archDocAlum.setCantidad(String.valueOf((posArchDocAlumDao.numImagenes(codigoAlumno, iddocumento)+1)));				
			if(archDocAlumDao.updateReg(archDocAlum)){
				guardoEnOracle = true;
			}
		}else{//Si no existe el documento en oracle
			archDocAlum.setMatricula(codigoAlumno);
			archDocAlum.setIdDocumento(iddocumento);
			archDocAlum.setIdStatus(idstatus);
			archDocAlum.setFecha(aca.util.Fecha.getHoy());
			archDocAlum.setCantidad("1");
			archDocAlum.setUsuario(usuario);
			archDocAlum.setIncorrecto("N");
			if(archDocAlumDao.insertReg(archDocAlum)){
				guardoEnOracle = true;
			}
		}
		
		PosArchDocAlum posDoc 		= new PosArchDocAlum();
		if(guardoEnOracle){			
			//Si existe el documento en postgres
			if(posArchDocAlumDao.existeReg(codigoAlumno, iddocumento, hoja)){
				String estado = posArchDocAlumDao.getEstado(codigoAlumno, iddocumento, hoja);
				resultado = "deshabilitaPantalla();"
						+ "	muestraDocumentoExistente('"+iddocumento+"','"+idstatus+"','"+folio+"','"+hoja+"','"+origen+"');";
			}else{				
				//Si no existe el documento en postgres	
				posDoc.setMatricula(codigoAlumno);
				posDoc.setIddocumento(iddocumento);
				posDoc.setHoja(hoja);
				posDoc.setImagen(0);
				posDoc.setFuente("G");//General
				posDoc.setTipo("I");//Insert
				posDoc.setOrigen(origen);
				posDoc.setFinsert(archGeneral.getFecha());
				posDoc.setFupdate(archGeneral.getFecha());
				posDoc.setUsuario(usuario);
				posDoc.setImagenByte(archGeneral.getImagenByte());
				posDoc.setEstado("B");
				if(posArchDocAlumDao.insertReg(posDoc)){
					
					//Si inserta archDocAlum de posgres					
					if(posArchGeneralDao.deleteReg(codigoAlumno, folio)){//Si borra archGeneral de postgres (la referencia a la imagen en general)
						resultado = "	$('visor').innerHTML = '<font size=\"3\" color=\"blue\"><b>Image Assigned Successfully</b></font>';"
							+ "		$('panel').innerHTML = '&nbsp;';"
							+ "		$('Imagen"+folio+"').remove();";
					}else{
						resultado = "$('visor').innerHTML = '&nbsp;';"
							+ "		$('panel').innerHTML = '&nbsp;';"
							+ "		alert('An error occurred while assigning the image.\\nTry again. Contact your system administrator if the error persists.');";
					}
				}else{
					resultado = "$('visor').innerHTML = '&nbsp;';"
							+ "		$('panel').innerHTML = '&nbsp;';"
							+ "		alert('An error occurred while assigning the image.\\\\nTry again. Contact your system administrator if the error persists.');";
				}
			}
		}else{
			resultado = "$('visor').innerHTML = '&nbsp;';"
					+ "		$('panel').innerHTML = '&nbsp;';"
					+ "		alert('An error occurred while assigning the image.\\\\nTry again. Contact your system administrator if the error persists.');";
		}
		
		return resultado;
	}
	
	@Transactional
	@ResponseBody
	@RequestMapping("/archivo/documentos_alumno/reemplazar")
	public String archivoDocumentosAlumnoReemplazar(HttpServletRequest request, Model modelo){
		
		String codigoAlumno					= "0";
		String iddocumento					= request.getParameter("iddocumento");
		String idstatus						= request.getParameter("idstatus");
		String folio						= request.getParameter("folio");
		String hoja							= request.getParameter("hoja");
		String origen						= request.getParameter("origen");
		String usuario						= "0";
		String resultado 					= "";
		boolean grabaOracle 				= false;
		
		HttpSession sesion	= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoAlumno	= (String) sesion.getAttribute("codigoAlumno");
			usuario 		= (String) sesion.getAttribute("codigoPersonal");
		}
		
		PosArchGeneral general = posArchGeneralDao.mapeaRegId(codigoAlumno, folio);		
					
		PosArchDocAlum posDoc = posArchDocAlumDao.mapeaRegId(codigoAlumno, iddocumento, hoja );
		if(posArchDocAlumDao.unlinkImagen(codigoAlumno, iddocumento, hoja)){
			
			posDoc.setMatricula(codigoAlumno);
			posDoc.setIddocumento(iddocumento);
			posDoc.setHoja(hoja);
			posDoc.setImagen(general.getImagen());
			posDoc.setFuente("G");//General
			posDoc.setTipo("U");//Update
			posDoc.setOrigen(origen);
			posDoc.setFupdate(general.getFecha());
			posDoc.setUsuario(usuario);
			if(posArchDocAlumDao.updateReg(posDoc)){//Si actualiza archDocAlum de postgres
				
				if(posArchGeneralDao.deleteReg(codigoAlumno, folio)){
					
					//Si borra archGeneral de posgres (la referencia a la imagen en general)
					resultado = "$('visor').innerHTML = '<font size=\"3\" color=\"blue\"><b>Assigned Image (Updated) Correctly</b></font>';"
							+ "				$('panel').innerHTML = '&nbsp;';"
							+ "				$('Imagen"+folio+"').remove();"
							+ "				cancelaReemplazo();";
				}else{
					resultado = "alert('An error occurred while deleting the general image.\\nTry again. If the error persists, please contact your system support.');";
				}
			}else{
				resultado = "alert('An error occurred while updating the student's files.\\nTry again. If the error persists, please contact your system support.');";
			}
		}else{
			resultado = "alert('An error occurred while unlinking the image.\\nTry again. If the error persists, please contact your system support.');";
		}		
		
		return resultado;
	}
	
	@ResponseBody
	@RequestMapping("/archivo/documentos_alumno/borrar")
	public String archivoDocumentosAlumnoBorrar(HttpServletRequest request){		
		
		String codigoAlumno 	= "0";
		String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		
		HttpSession sesion	= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoAlumno	= (String) sesion.getAttribute("codigoAlumno");			
		}
		String resultado = "";	
		if(posArchGeneralDao.deletePorMatriculaAndFolio(codigoAlumno, folio)){
			resultado = "$('visor').innerHTML = '&nbsp;'; $('panel').innerHTML = '&nbsp;'; $('Imagen"+folio+"').remove();";
		}else{
			resultado = "alert('An error occurred while deleting the image.\\nTry again.');";
		}
		
		return resultado;
	}
	
	@RequestMapping("/archivo/documentos_alumno/grabar")
	public String archivoDocumentosAlumnoGrabar(HttpServletRequest request, Model modelo){
		
		String IdDocumento	= request.getParameter("IdDocumento")==null?"0":request.getParameter("IdDocumento");
		
		String codigoAlumno	= "0";
		String nombreAlumno = "";
		String alumCarrera	= "";
		
		AlumPlan plan 		= new AlumPlan();
		
		HttpSession sesion	= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoAlumno 	= (String) sesion.getAttribute("codigoAlumno");
			
			nombreAlumno	= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
			alumCarrera		= alumPersonalDao.getCarrera(codigoAlumno);
			plan 			= alumPlanDao.mapeaRegId(codigoAlumno);
		}
		
		List<ArchUbicacion> lisUbicacion 	= archUbicacionDao.getListAll("ORDER BY UBICACION_ID");
		List<ArchStatus> lisStatus 			= archStatusDao.lisPorDocumentoyEstado(IdDocumento, "A", " ORDER BY IDSTATUS");
		
		HashMap<String,String> mapaDocumentos 	= archDocumentosDao.mapaTodos();
		HashMap<String,String> mapaStatus 		= archStatusDao.mapaStatus();
		
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("alumCarrera", alumCarrera);
		modelo.addAttribute("plan", plan);
		modelo.addAttribute("lisUbicacion", lisUbicacion);
		modelo.addAttribute("lisStatus", lisStatus);
		modelo.addAttribute("mapaDocumentos", mapaDocumentos);
		modelo.addAttribute("mapaStatus", mapaStatus);
		
		return "archivo/documentos_alumno/grabar";
	}
	
	@RequestMapping("/archivo/documentos_alumno/grabarNuevo")
	public String archivoDocumentosAlumnoGrabarNuevo(HttpServletRequest request, Model modelo){
		
		String idDocumento		= request.getParameter("IdDocumento")==null?"0":request.getParameter("IdDocumento");
		String idStatus			= request.getParameter("IdStatus")==null?"0":request.getParameter("IdStatus");		
		String codigoAlumno		= "0";
		String codigoPersonal	= "0";
		
		ArchDocAlum documento 	= new ArchDocAlum();
		
		
		HttpSession sesion	= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoAlumno 	= (String) sesion.getAttribute("codigoAlumno");
			codigoPersonal 	= (String) sesion.getAttribute("codigoPersonal");
			documento.setMatricula(codigoAlumno);			
			documento.setIdDocumento(idDocumento);
			documento.setIdStatus(idStatus);
			documento.setFecha(aca.util.Fecha.getHoy());
			documento.setCantidad("0");
			documento.setUbicacion("1");
			documento.setUsuario(codigoPersonal);
			documento.setIncorrecto("N");
			
			if(!archDocAlumDao.existeReg(codigoAlumno, idDocumento)){
				if (archDocAlumDao.insertReg(documento)){				
				}
			}
		}		
		
		return "redirect:/archivo/documentos_alumno/documentos";
	}
	
	@RequestMapping("/archivo/documentos_alumno/verimgcmax")
	public String archivoDocumentosAlumnoVerImgcMax(HttpServletRequest request){	
		return "archivo/documentos_alumno/verimgcmax";
	}
	
	@RequestMapping("/archivo/documentos_alumno/documentosToPdf")
	public String archivoDocumentosAlumnoDocumentosToPdf(HttpServletRequest request, Model modelo) {
		
		String documentoId			= request.getParameter("DocumentoId")==null?"0":request.getParameter("DocumentoId");
		String matricula = "0";
		String documentoNombre = "0";
		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
		}
		if (!documentoId.equals("0")) {
			documentoNombre = archDocumentosDao.getDescripcion(documentoId);
		}
		
		AlumPersonal alumPersonal 		 	= alumPersonalDao.mapeaRegId(matricula);
		List<PosArchDocAlum> lisImagenes	= posArchDocAlumDao.lisHojasporDoc(matricula, documentoId);		
		
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("lisImagenes", lisImagenes);
		modelo.addAttribute("documentoNombre", documentoNombre);	
		
		return "archivo/documentos_alumno/documentosToPdf";
	}
}