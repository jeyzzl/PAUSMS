package aca.web.bitacora;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.bitacora.spring.BitEtiqueta;
import aca.bitacora.spring.BitEtiquetaDao;
import aca.bitacora.spring.BitRequisito;
import aca.bitacora.spring.BitRequisitoAlumno;
import aca.bitacora.spring.BitRequisitoAlumnoDao;
import aca.bitacora.spring.BitSolicitud;
import aca.bitacora.spring.BitSolicitudDao;
import aca.bitacora.spring.BitTramite;
import aca.bitacora.spring.BitTramiteAlumno;
import aca.bitacora.spring.BitTramiteAlumnoDao;
import aca.bitacora.spring.BitTramiteRequisito;
import aca.bitacora.spring.BitTramiteRequisitoDao;

@Controller
public class ContBitacoraSolicitud {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	aca.bitacora.spring.BitRequisitoDao bitRequisitoDao;
	
	@Autowired
	aca.bitacora.spring.BitTramiteDao bitTramiteDao;
	
	@Autowired
	BitRequisitoAlumnoDao bitRequisitoAlumnoDao;
	
	@Autowired
	BitSolicitudDao bitSolicitudDao;
	
	@Autowired
	BitTramiteRequisitoDao bitTramiteRequisitoDao;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;

	@Autowired
	BitTramiteAlumnoDao bitTramiteAlumnoDao;
	
	@Autowired
	BitEtiquetaDao bitEtiquetaDao;
	
	
	@RequestMapping("/bitacora/solicitud/listado")
	public String bitacoraRequisitoListado(HttpServletRequest request, Model modelo){
		
		String estado 	= request.getParameter("Estado")==null?"0":request.getParameter("Estado");		
		
		List<BitSolicitud> lisSolicitudes		= new ArrayList<BitSolicitud>();
		if (estado.equals("0")) {
			lisSolicitudes = bitSolicitudDao.lisTodasSolicitudes(" ORDER BY STATUS, ENOC.BIT_SOLICITUD.FECHA DESC");
		}else{
			lisSolicitudes = bitSolicitudDao.lisPorEstado(estado," ORDER BY STATUS, ENOC.BIT_SOLICITUD.FECHA DESC");
		}		
		
		HashMap<String, BitTramite> mapaTramite 				= bitTramiteDao.mapTramites();
		HashMap<String, String> mapaEtiquetas	 				= bitEtiquetaDao.mapaCuentaEtiquetas();
		HashMap<String, AlumPersonal> mapaAlumnosConSolicitu 	= alumPersonalDao.mapaAlumnosConSolicitu();		
		HashMap<String,String> mapaRequisitosEnTramite 			= bitTramiteRequisitoDao.mapaRequisitosEnTramite();
		HashMap<String, String> mapaBitRequisitosCumple 		= bitRequisitoAlumnoDao.mapaBitRequisitosCumple("A");
		
		modelo.addAttribute("lisSolicitudes", lisSolicitudes);
		modelo.addAttribute("mapaTramite", mapaTramite);
		modelo.addAttribute("mapaEtiquetas", mapaEtiquetas);
		modelo.addAttribute("mapaAlumnosConSolicitu", mapaAlumnosConSolicitu);
		modelo.addAttribute("mapaRequisitosEnTramite", mapaRequisitosEnTramite);
		modelo.addAttribute("mapaBitRequisitosCumple", mapaBitRequisitosCumple);
		
		return "bitacora/solicitud/listado";
	}	
	
	@RequestMapping("/bitacora/solicitud/requisitos")
	public String bitacoraRequisitoEditar(HttpServletRequest request, Model modelo){
		String codigoPersonal	 		= request.getParameter("CodigoPersonal")==null?"0":request.getParameter("CodigoPersonal");
		String folio	 				= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String mensaje	 				= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		String nombreAlumno				= "";	
		BitSolicitud solicitud 	= new BitSolicitud();
		BitTramite tramite	 	= new BitTramite();
		HashMap<String, BitRequisito> mapRequisitos = bitRequisitoDao.mapRequisitos();
		
		if (bitSolicitudDao.existeReg(codigoPersonal,folio)) {
			solicitud = bitSolicitudDao.mapeaRegId(codigoPersonal,folio);
		}

		if (bitTramiteDao.existeReg(solicitud.getTramiteId())) {
			tramite = bitTramiteDao.mapeaRegId(solicitud.getTramiteId());
		}
		
		List<BitTramiteRequisito> lisRequisitos = bitTramiteRequisitoDao.lisTramites(tramite.getTramiteId(),"ORDER BY REQUISITO_ID");
		
		HashMap<String, BitRequisitoAlumno> mapaBitRequisitoAlumno = new HashMap<String, BitRequisitoAlumno>();
		
		if(bitRequisitoAlumnoDao.existeReg(codigoPersonal, solicitud.getTramiteId(),folio)) {
			mapaBitRequisitoAlumno = bitRequisitoAlumnoDao.mapaBitRequisitoAlumno(codigoPersonal,solicitud.getTramiteId());
		}
		
		if(alumPersonalDao.existeAlumno(codigoPersonal)) {
			nombreAlumno = alumPersonalDao.getNombreAlumno(codigoPersonal, "NOMBRE");
		}
		
		modelo.addAttribute("solicitud", solicitud);
		modelo.addAttribute("tramite", tramite);
		modelo.addAttribute("lisRequisitos", lisRequisitos);
		modelo.addAttribute("mapRequisitos", mapRequisitos);
		modelo.addAttribute("mapaBitRequisitoAlumno", mapaBitRequisitoAlumno);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("mensaje", mensaje);
		
		return "bitacora/solicitud/requisitos";
	}
	
	@RequestMapping("/bitacora/solicitud/grabar")
	public String bitacoraRequisitoGrabar(HttpServletRequest request, Model modelo){
		String tramiteId 		= request.getParameter("TramiteId")==null?"0":request.getParameter("TramiteId");
		String codigoPersonal 	= request.getParameter("CodigoPersonal")==null?"":request.getParameter("CodigoPersonal");
		String folio		 	= request.getParameter("Folio")==null?"":request.getParameter("Folio");
		String comentario	 	= request.getParameter("Comentario")==null?"":request.getParameter("Comentario");
		String mensaje			= "0";
		String codigoEmpleado	= "0";
		String fechaHoy 		= aca.util.Fecha.getHoy();
		
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoEmpleado 	   	= (String) sesion.getAttribute("codigoPersonal");
        }
		
		List<BitTramiteRequisito> lisRequisitos = bitTramiteRequisitoDao.lisTramites(tramiteId,"ORDER BY REQUISITO_ID");	
	
		BitRequisitoAlumno bitRequisitoAlumno = new BitRequisitoAlumno();	

		for(BitTramiteRequisito requisito : lisRequisitos){
			if(request.getParameter(requisito.getRequisitoId()) != null) {
				if(bitRequisitoAlumnoDao.existeReg(codigoPersonal, tramiteId, requisito.getRequisitoId(),folio)){
					bitRequisitoAlumno = bitRequisitoAlumnoDao.mapeaRegId(codigoPersonal, tramiteId, requisito.getRequisitoId(),folio);
					bitRequisitoAlumno.setCodigoEmpleado(codigoEmpleado);
					bitRequisitoAlumno.setFecha(fechaHoy);
					bitRequisitoAlumno.setEstado("A");
					
					if(bitRequisitoAlumnoDao.updateReg(bitRequisitoAlumno)) {
						mensaje = "1";
					}else {
						mensaje = "2";
					}	
				}else {
					bitRequisitoAlumno.setCodigoEmpleado(codigoEmpleado);
					bitRequisitoAlumno.setCodigoPersonal(codigoPersonal);
					bitRequisitoAlumno.setFecha(fechaHoy);
					bitRequisitoAlumno.setRequisitoId(requisito.getRequisitoId());
					bitRequisitoAlumno.setTramiteId(tramiteId);
					bitRequisitoAlumno.setFolio(folio);
					bitRequisitoAlumno.setEstado("A");

					if(bitRequisitoAlumnoDao.insertReg(bitRequisitoAlumno)) {
						mensaje = "1";
					}else {
						mensaje = "2";
					}	
				}
			}else {
				if(bitRequisitoAlumnoDao.existeReg(codigoPersonal, tramiteId, requisito.getRequisitoId(),folio)){
					bitRequisitoAlumno = bitRequisitoAlumnoDao.mapeaRegId(codigoPersonal, tramiteId, requisito.getRequisitoId(),folio);
					bitRequisitoAlumno.setCodigoEmpleado(codigoEmpleado);
					bitRequisitoAlumno.setFecha(fechaHoy);
					bitRequisitoAlumno.setEstado("I");
					
					if(bitRequisitoAlumnoDao.updateReg(bitRequisitoAlumno)) {
						mensaje = "1";
					}else {
						mensaje = "2";
					}	
				}
			}
		}
		
		int numRequisitos 	= lisRequisitos.size();
		int numCumplidos	= bitRequisitoAlumnoDao.numAutorizados(codigoPersonal, tramiteId, folio);
		BitSolicitud bitSolicitud = new BitSolicitud();
		if(bitSolicitudDao.existeReg(codigoPersonal, folio)){
			bitSolicitud = bitSolicitudDao.mapeaRegId(codigoPersonal, folio);
			bitSolicitud.setTextoAdmin(comentario);
			if(numRequisitos == numCumplidos){				
				if (!bitSolicitud.getStatus().equals("4")){
					bitSolicitud.setStatus("3");
				}							
			}else if (numCumplidos == 0) {
				bitSolicitud.setStatus("1");
			}else{
				bitSolicitud.setStatus("2");
			}
			if(bitSolicitudDao.updateReg(bitSolicitud)) {
				mensaje = "1";
			}else {
				mensaje = "2";
			}			
		}		
		return "redirect:/bitacora/solicitud/requisitos?CodigoPersonal="+codigoPersonal+"&Folio="+folio+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/bitacora/solicitud/borrar")
	public String bitacoraRequisitoBorrar(HttpServletRequest request, Model modelo){
		String solicitudId 	= request.getParameter("RequisitoId")==null?"0":request.getParameter("RequisitoId");
		
		if (bitRequisitoDao.existeReg(solicitudId)) {
			bitRequisitoDao.deleteReg(solicitudId);
		}
		
		return "redirect:/bitacora/solicitud/listado";
	}
	
	@RequestMapping("/bitacora/solicitud/borrarFolio")
	public String bitacoraSolicitudBorrar(HttpServletRequest request, Model modelo){
		String codigo		= request.getParameter("Codigo")==null?"0":request.getParameter("Codigo");
		String folio 		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String tramiteId 	= request.getParameter("TramiteId")==null?"0":request.getParameter("TramiteId");
		
		if(bitTramiteAlumnoDao.existeReg(tramiteId)){
			if (bitTramiteAlumnoDao.deleteReg(tramiteId)){
				if(bitSolicitudDao.existeReg(codigo, folio)) {
					bitSolicitudDao.deleteReg(codigo, folio);
				}
			}	
		}else {
			if(bitSolicitudDao.existeReg(codigo, folio)) {
				bitSolicitudDao.deleteReg(codigo, folio);
			}
		}
		
		return "redirect:/bitacora/solicitud/listado";
	}
	
	@RequestMapping("/bitacora/solicitud/autorizarTramite")
	public String bitacoraSolicitudAutorizarTramite(HttpServletRequest request, Model modelo){
		String autorizar 		= request.getParameter("Autorizar")==null?"0":request.getParameter("Autorizar");
		String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String codigoPersonal	= request.getParameter("CodigoPersonal")==null?"0":request.getParameter("CodigoPersonal");
		String fechaHoy 		= aca.util.Fecha.getHoy();
		String codigoEmpleado	= "0";
		
		BitTramiteAlumno tramite = new BitTramiteAlumno();
		
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoEmpleado 	   	= (String) sesion.getAttribute("codigoPersonal");
        }
		
		if(bitSolicitudDao.existeReg(codigoPersonal,folio)) {
			BitSolicitud solicitud = bitSolicitudDao.mapeaRegId(codigoPersonal, folio);
			solicitud.setStatus(autorizar);
			if(solicitud.getFolioTramite().equals("0")) {
				solicitud.setFolioTramite(bitTramiteAlumnoDao.maximoReg(aca.util.Fecha.getHoy().substring(6, 10)));
			}
			
			BitTramite bitTramite 	= new BitTramite();
			BitEtiqueta bitEtiqueta = new BitEtiqueta();
			
			bitEtiqueta.setFolio(solicitud.getFolioTramite());
			bitEtiqueta.setEtiquetaId("1");
			bitEtiqueta.setAreaId("7");
			bitEtiqueta.setComentario("Registrado por el sistema de Trámites en Línea");
			bitEtiqueta.setFecha(fechaHoy);
			bitEtiqueta.setUsuario(codigoEmpleado);
			bitEtiqueta.setTurnado("-");
			
			if(bitTramiteDao.existeReg(solicitud.getTramiteId())){
				bitTramite = bitTramiteDao.mapeaRegId(solicitud.getTramiteId());
			}
			
			if (bitSolicitudDao.updateReg(solicitud)){	
				tramite.setFolio(solicitud.getFolioTramite());
				tramite.setCodigoPersonal(codigoPersonal);
				tramite.setTramiteId(solicitud.getTramiteId());
				tramite.setFechaInicio(fechaHoy);
				tramite.setFechaFinal(null);
				tramite.setEstado("2");
				tramite.setAreaId(bitTramite.getAreaId());
				tramite.setAreaOrigen("0");
				tramite.setFolioOrigen("-");
				tramite.setUsuario(codigoEmpleado);
				tramite.setComentario(solicitud.getTextoAdmin());
				if(!bitTramiteAlumnoDao.existeReg(solicitud.getFolioTramite()) && autorizar.equals("4")){
					if(bitTramiteAlumnoDao.insertReg(tramite)) {
						bitEtiquetaDao.insertReg(bitEtiqueta);
					}
				}else if(bitTramiteAlumnoDao.existeReg(solicitud.getFolioTramite()) && autorizar.equals("3")) {
					bitEtiquetaDao.deleteAllReg(solicitud.getFolioTramite());
					bitTramiteAlumnoDao.deleteReg(solicitud.getFolioTramite());
				}
			}	
		}
		
		return "redirect:/bitacora/solicitud/listado";
	}
}