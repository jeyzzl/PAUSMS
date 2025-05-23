package aca.web.bitacora;

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
import org.springframework.web.client.RestTemplate;

import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.archivo.spring.ArchDocAlum;
import aca.archivo.spring.ArchDocAlumDao;
import aca.archivo.spring.ArchDocumentosDao;
import aca.bitacora.spring.BitAreaDao;
import aca.bitacora.spring.BitEstadoDao;
import aca.bitacora.spring.BitEtiqueta;
import aca.bitacora.spring.BitEtiquetaDao;
import aca.bitacora.spring.BitRequisito;
import aca.bitacora.spring.BitRequisitoAlumno;
import aca.bitacora.spring.BitSolicitud;
import aca.bitacora.spring.BitSolicitudDao;
import aca.bitacora.spring.BitTramite;
import aca.bitacora.spring.BitTramiteAlumno;
import aca.bitacora.spring.BitTramiteAlumnoDao;
import aca.bitacora.spring.BitTramiteDao;
import aca.catalogo.spring.CatCarreraDao;
import aca.financiero.spring.FinSaldo;
import aca.vista.spring.Alumno;
import aca.vista.spring.AlumnoDao;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContBitacoraMesa {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired	
	private BitTramiteAlumnoDao bitTramiteAlumnoDao;
	
	@Autowired	
	private BitTramiteDao bitTramiteDao;
	
	@Autowired	
	private BitEstadoDao bitEstadoDao;
	
	@Autowired	
	private BitEtiquetaDao bitEtiquetaDao;
	
	@Autowired	
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired	
	private BitAreaDao bitAreaDao;
	
	@Autowired	
	private MaestrosDao maestrosDao;
	
	@Autowired	
	private AlumnoDao alumnoDao;
	
	@Autowired	
	private CatCarreraDao catCarreraDao;
	
	@Autowired	
	private ArchDocAlumDao archDocAlumDao;
	
	@Autowired	
	private ArchDocumentosDao archDocumentosDao;
	
	@Autowired	
	private BitSolicitudDao bitSolicitudDao;
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/bitacora/mesa/entrada")
	public String bitacoraMesaEntrada(HttpServletRequest request, Model modelo){
		
		String codigoAlumno = "0";
		String alumnoNombre	= "-";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();		
		if (sesion!=null){
			codigoAlumno		= (String) sesion.getAttribute("codigoAlumno");
			alumnoNombre 		= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
		}
		
		// Lista de tramites del alumno
		List<BitTramiteAlumno> lisTramites 			= bitTramiteAlumnoDao.lisTramitePrincipal(codigoAlumno, " ORDER BY ENOC.BIT_TRAMITE_ALUMNO.FECHA_INICIO");		
		// Mapa del catalogo de tramites
		HashMap<String,BitTramite> mapaTramites		= bitTramiteDao.mapTramites();			
		// Mapa del catalogo de estados
		HashMap<String,String> mapaEstados			= bitEstadoDao.mapEstados();			
		// Mapa que cuenta el numero de folios dependientes de un folio principal
		HashMap<String,String> mapaHijos 			= bitTramiteAlumnoDao.mapCuentaHijos("1,2,3");		
		// Mapa que cuenta el numero de etiquetas por folio
		HashMap<String,String> mapaEtiquetas		= bitEtiquetaDao.mapaCuentaEtiquetas(codigoAlumno);
		HashMap<String,String> mapaAreas			= bitAreaDao.mapaAreas();
		
		modelo.addAttribute("alumnoNombre", alumnoNombre);
		modelo.addAttribute("lisTramites", lisTramites);
		modelo.addAttribute("mapaTramites", mapaTramites);
		modelo.addAttribute("mapaEstados", mapaEstados);
		modelo.addAttribute("mapaHijos", mapaHijos);
		modelo.addAttribute("mapaEtiquetas", mapaEtiquetas);
		modelo.addAttribute("mapaAreas", mapaAreas);
		
		return "bitacora/mesa/entrada";
	}
	
	@RequestMapping("/bitacora/mesa/borrarFolio")
	public String bitacoraMesaBorrar(HttpServletRequest request, Model modelo){
		
		String folio	= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String mensaje 	= "-";
		
		if (bitEtiquetaDao.numEtiquetas(folio) > 0){
			bitEtiquetaDao.deleteAllReg(folio);
		}
		if(bitTramiteAlumnoDao.deleteReg(folio)){
			mensaje = "Se eliminó el folio:"+folio;
			bitSolicitudDao.updateFolioTramite(folio);
		}else{
			mensaje = "Error al intentar borrar el folio:"+folio;
		}
		
		return "redirect:/bitacora/mesa/entrada?Mensaje="+mensaje;	
	}
	
	@RequestMapping("/bitacora/mesa/etiquetas")
	public String bitacoraMesaEtiquetas(HttpServletRequest request, Model modelo){
		
		String folio				= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		
		String codigoAlumno = "0";
		String alumnoNombre	= "-";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();		
		if (sesion!=null){
			codigoAlumno		= (String) sesion.getAttribute("codigoAlumno");
			alumnoNombre 		= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
		}
		
		BitTramiteAlumno bitTramiteAlumno 					= bitTramiteAlumnoDao.mapeaRegId(folio);
		
		List<BitEtiqueta> lisEtiquetas 						= bitEtiquetaDao.listEtiquetas(folio, " ORDER BY ENOC.BIT_ETIQUETA.FECHA");
		List<BitEtiqueta> lisTurnadas						= bitEtiquetaDao.listEtiquetasAlumno(codigoAlumno, "ORDER BY FOLIO, ENOC.BIT_ETIQUETA.FECHA");
		HashMap<String,String> mapaEstados					= bitEstadoDao.mapEstados();
		HashMap<String,BitTramite> mapaTramites				= bitTramiteDao.mapTramites();
		HashMap<String,String> mapaMaestros					= maestrosDao.mapaMaestroCorto("NOMBRE");
		HashMap<String,String> mapaAreas					= bitAreaDao.mapaAreas();		
		HashMap<String,BitTramiteAlumno> mapaTramitesAlumno	= bitTramiteAlumnoDao.mapTramitesAlumno(codigoAlumno);
				
		modelo.addAttribute("alumnoNombre", alumnoNombre);
		modelo.addAttribute("bitTramiteAlumno", bitTramiteAlumno);
		modelo.addAttribute("lisEtiquetas", lisEtiquetas);
		modelo.addAttribute("lisTurnadas", lisTurnadas);
		modelo.addAttribute("mapaEstados", mapaEstados);
		modelo.addAttribute("mapaTramites", mapaTramites);
		modelo.addAttribute("mapaMaestros", mapaMaestros);
		modelo.addAttribute("mapaAreas", mapaAreas);
		modelo.addAttribute("mapaTramitesAlumno", mapaTramitesAlumno);
		
		return "bitacora/mesa/etiquetas";
	}
	
	@RequestMapping("/bitacora/mesa/nuevaSolicitud")
	public String bitacoraMesaNuevaSolicitud(HttpServletRequest request, Model modelo){
		
		String folio 			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String tramiteId		= request.getParameter("TramiteId")==null?"0":request.getParameter("TramiteId");
		String codigoAlumno		= "";
		String alumnoNombre		= "-";	
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();		
		if (sesion!=null){
			codigoAlumno		= (String) sesion.getAttribute("codigoAlumno");
			alumnoNombre 		= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
		}
		
		BitTramiteAlumno bitTramiteAlumno = new BitTramiteAlumno();
		if (bitTramiteAlumnoDao.existeReg(folio)) {
			bitTramiteAlumno = bitTramiteAlumnoDao.mapeaRegId(folio);
			tramiteId = bitTramiteAlumno.getTramiteId();
		}
		
		// Consulta el saldo del estudiante
		FinSaldo finSaldo 	= new FinSaldo();
		try {		
			RestTemplate restTemplate = new RestTemplate();
			finSaldo = restTemplate.getForObject("http://172.16.251.110:8080/infor/reportes/api/fe/saldo/"+codigoAlumno, FinSaldo.class);
		}catch(Exception ex) {
			System.out.println("Error en saldo:"+codigoAlumno);
		}
		
		List<BitTramite> lisTramites 				= bitTramiteDao.lisTramitesPorTipos("'G','E'"," ORDER BY NOMBRE");
		if (tramiteId.equals("0") && lisTramites.size() > 0){
			tramiteId = lisTramites.get(0).getTramiteId();
		}
		
		BitTramite bitTramite = new BitTramite();
		if (bitTramiteDao.existeReg(tramiteId)) {
			bitTramite = bitTramiteDao.mapeaRegId(tramiteId);
		}
		
		List<ArchDocAlum> lisDocumentos 			= archDocAlumDao.getListAll(codigoAlumno, " ORDER BY IDDOCUMENTO");		
		HashMap<String, String> mapAreas 			= bitAreaDao.mapaAreas();
		HashMap<String, String> mapDoc	 			= archDocumentosDao.mapaTodos();				
		Alumno alumno 								= alumnoDao.mapeaRegId(codigoAlumno);
		AlumPersonal alumnoPersonal 				= alumPersonalDao.mapeaRegId(codigoAlumno);
		String nombreCarrera 						= catCarreraDao.getNivelId(alumno.getCarreraId());		
		boolean inscrito 							= alumPersonalDao.esInscrito(alumno.getCodigoPersonal());
		
		modelo.addAttribute("codigoAlumno", codigoAlumno);
		modelo.addAttribute("alumnoNombre", alumnoNombre);
		modelo.addAttribute("nombreCarrera", nombreCarrera);
		modelo.addAttribute("tramiteId", tramiteId);
		modelo.addAttribute("bitTramiteAlumno", bitTramiteAlumno);
		modelo.addAttribute("bitTramite", bitTramite);
		modelo.addAttribute("finSaldo", finSaldo);
		modelo.addAttribute("inscrito", inscrito);
		modelo.addAttribute("alumno", alumno);
		modelo.addAttribute("alumnoPersonal", alumnoPersonal);		
		modelo.addAttribute("lisTramites", lisTramites);
		modelo.addAttribute("lisDocumentos", lisDocumentos);
		modelo.addAttribute("mapAreas", mapAreas);
		modelo.addAttribute("mapDoc", mapDoc);
		
		return "bitacora/mesa/nuevaSolicitud";
	}

	@RequestMapping("/bitacora/mesa/grabarSolicitud")
	public String bitacoraMesaGrabarSolicitud(HttpServletRequest request, Model modelo){
		String codigoAlumno		= "";
		String codigoUsuario	= "";
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();		
		if (sesion!=null){
			codigoAlumno	= (String) sesion.getAttribute("codigoAlumno");
			codigoUsuario	= (String) sesion.getAttribute("codigoPersonal");
		}
		
		BitTramiteAlumno bitTramiteAlumno 	= new BitTramiteAlumno();
		BitEtiqueta bitEtiqueta 			= new BitEtiqueta();
		
		String folio 		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String tramiteId 	= request.getParameter("TramiteId")==null?"0":request.getParameter("TramiteId");
		String comentario 	= request.getParameter("Comentario")==null?"-":request.getParameter("Comentario");
		String mensaje 		= "-";
		String year    		= aca.util.Fecha.getHoy().substring(6, 10);
		
		String maxFolio 	= folio;		
		boolean existe 		= bitTramiteAlumnoDao.existeReg(folio);	
		if (!existe ){
			maxFolio 	= bitTramiteAlumnoDao.maximoReg(year);
			folio 		= maxFolio;
		}
		
		bitTramiteAlumno.setFolio(maxFolio);
		bitTramiteAlumno.setCodigoPersonal(codigoAlumno);
		bitTramiteAlumno.setTramiteId(tramiteId);
		bitTramiteAlumno.setAreaId(bitTramiteDao.getAreaId(tramiteId));
		bitTramiteAlumno.setFechaInicio(aca.util.Fecha.getHoy());
		bitTramiteAlumno.setFechaFinal(null);
		bitTramiteAlumno.setFolioOrigen("-");
		bitTramiteAlumno.setAreaOrigen("0");
		bitTramiteAlumno.setEstado("2");
		bitTramiteAlumno.setUsuario(codigoUsuario);
		bitTramiteAlumno.setComentario(comentario);
		if (existe){
			if (bitTramiteAlumnoDao.updateReg(bitTramiteAlumno)){				
				mensaje = "¡ Grabado !";					
			}else{
				mensaje = "¡ No grabó el tramite !";
			}
		}else{
			if (bitTramiteAlumnoDao.insertReg( bitTramiteAlumno)){
				bitEtiqueta.setFolio(maxFolio);				
				bitEtiqueta.setAreaId("7"); //Mesa de entrada
				bitEtiqueta.setComentario("Tramite registrado en mesa de entrada.");
				bitEtiqueta.setUsuario(codigoUsuario);
				bitEtiqueta.setTurnado("-");
				bitEtiqueta.setEtiquetaId(bitEtiquetaDao.maximoReg(maxFolio));
				if (bitEtiquetaDao.insertReg(bitEtiqueta)){
					mensaje = "¡ Grabado !";
				}else{
					mensaje = "¡ No grabó la etiqueta !";
				}			
			}else{
				mensaje = "¡ No grabó el tramite !";
			}
		}
		
		return "redirect:/bitacora/mesa/nuevaSolicitud?Mensaje="+mensaje+"&Folio="+maxFolio;
	}
	
	@RequestMapping("/bitacora/mesa/traking")
	public String bitacoraMesaTraking(HttpServletRequest request){		
		return "bitacora/mesa/traking";
	}
	
}
