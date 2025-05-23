package aca.web.bitacora;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.archivo.spring.ArchGruposCarrera;
import aca.bitacora.spring.BitArea;
import aca.bitacora.spring.BitEtiqueta;
import aca.bitacora.spring.BitRequisito;
import aca.bitacora.spring.BitRequisitoDao;
import aca.bitacora.spring.BitRequisitoTramiteDao;
import aca.bitacora.spring.BitTramite;
import aca.bitacora.spring.BitTramiteAlumno;

@Controller
public class ContBitacoraTramite {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	aca.bitacora.spring.BitTramiteAlumnoDao bitTramiteAlumnoDao;
	
	@Autowired
	aca.bitacora.spring.BitAreaDao bitAreaDao;
	
	@Autowired
	aca.bitacora.spring.BitTramiteDao bitTramiteDao;
	
	@Autowired
	aca.bitacora.spring.BitEtiquetaDao bitEtiquetaDao;
	
	@Autowired
	aca.bitacora.spring.BitEstadoDao bitEstadoDao;
	
	@Autowired
	aca.alumno.spring.AlumPersonalDao alumPersonalDao;

	@Autowired
	aca.archivo.spring.ArchGruposCarreraDao archGruposCarreraDao;
	
	@Autowired
	BitRequisitoDao bitRequisitoDao;
	
	@Autowired
	BitRequisitoTramiteDao bitRequisitoTramiteDao;
	
	@RequestMapping("/bitacora/tramite/tramite")
	public String bitacoraTramiteTramite(HttpServletRequest request, Model modelo){
		List<BitTramite> lisTramites 			= bitTramiteDao.lisTramites("ORDER BY AREA_NOMBRE(AREA_ID), NOMBRE");
		
		HashMap<String, String> mapaAreas 		= bitAreaDao.mapaAreas();
		HashMap<String, String> mapaTramite 	= bitTramiteAlumnoDao.mapTramites();
		HashMap<String, String> mapaTotales 	= bitTramiteAlumnoDao.mapTotalPorTramite();
		HashMap<String, String> mapaRequisitos 	= bitRequisitoTramiteDao.mapaTotalRequisitos();
		
		modelo.addAttribute("lisTramites", lisTramites);
		modelo.addAttribute("mapaAreas", mapaAreas);
		modelo.addAttribute("mapaTramite", mapaTramite);
		modelo.addAttribute("mapaTotales", mapaTotales);
		modelo.addAttribute("mapaRequisitos", mapaRequisitos);
		
		return "bitacora/tramite/tramite";
	}	
	
	@RequestMapping("/bitacora/tramite/editar")
	public String bitacoraTramiteEditar(HttpServletRequest request, Model modelo){
		String tramiteId 	= request.getParameter("TramiteId")==null?"0":request.getParameter("TramiteId");
		String mensaje	 	= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		BitTramite tramite 	= new BitTramite();
		List<BitArea> lisArea = bitAreaDao.lisAreas("ORDER BY AREA_ID");
		
		if (bitTramiteDao.existeReg(tramiteId)) {
			tramite = bitTramiteDao.mapeaRegId(tramiteId);
		}
		modelo.addAttribute("tramite", tramite);
		modelo.addAttribute("lisArea", lisArea);
		modelo.addAttribute("mensaje", mensaje);
		
		return "bitacora/tramite/editar";
	}
	
	@RequestMapping("/bitacora/tramite/grabar")
	public String bitacoraTramiteGrabarTramite(HttpServletRequest request, Model modelo){
		String tramiteId 	= request.getParameter("TramiteId")==null?"0":request.getParameter("TramiteId");
		String nombre 		= request.getParameter("Nombre")==null?"-":request.getParameter("Nombre");
		String areaId 		= request.getParameter("AreaId")==null?"0":request.getParameter("AreaId");
		String minimo 		= request.getParameter("Minimo")==null?"0":request.getParameter("Minimo");
		String maximo 		= request.getParameter("Maximo")==null?"0":request.getParameter("Maximo");
		String promedio 	= request.getParameter("Promedio")==null?"0":request.getParameter("Promedio");
		String requisitos 	= request.getParameter("Requisitos")==null?"0":request.getParameter("Requisitos");
		String tipo		 	= request.getParameter("Tipo")==null?"0":request.getParameter("Tipo");
		String solAlumno 	= request.getParameter("SolAlumno")==null?"N":request.getParameter("SolAlumno");
		String costo 	    = request.getParameter("Costo")==null?"-":request.getParameter("Costo");
 
		String mensaje		= "0";
		
		BitTramite tramite	= new BitTramite();
		tramite.setAreaId(areaId);
		tramite.setMaximo(maximo);
		tramite.setMinimo(minimo);
		tramite.setNombre(nombre);
		tramite.setPromedio(promedio);
		tramite.setTipo(tipo);
		tramite.setRequisitos(requisitos);
		tramite.setSolAlumno(solAlumno);
		tramite.setImporte(costo);
		
		if (bitTramiteDao.existeReg(tramiteId)) {
			tramite.setTramiteId(tramiteId);
			if(bitTramiteDao.updateReg(tramite)) {
				mensaje = "1";
			}else {
				mensaje = "2";
			}
		} else {
			tramite.setTramiteId(bitTramiteDao.maximoReg());
			if(bitTramiteDao.insertReg(tramite)) {
				mensaje = "1";
			}else {
				mensaje = "2";
			}
		}
		
		return "redirect:/bitacora/tramite/editar?TramiteId="+tramite.getTramiteId()+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/bitacora/tramite/borrarTramite")
	public String bitacoraTramiteBorrarTramite(HttpServletRequest request, Model modelo){
		String tramiteId 	= request.getParameter("TramiteId")==null?"0":request.getParameter("TramiteId");
		
		if (bitTramiteDao.existeReg(tramiteId)) {
			bitTramiteDao.deleteReg(tramiteId);
		}
		
		return "redirect:/bitacora/tramite/tramite";
	}
	
	@RequestMapping("/bitacora/tramite/agregarRequisitos")
	public String bitacoraTramiteAgregarRequisitos(HttpServletRequest request, Model modelo){
		String tramiteId 	= request.getParameter("TramiteId")==null?"0":request.getParameter("TramiteId");
		String mensaje	 	= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		
		BitTramite tramite	= new BitTramite();
		
		if (bitTramiteDao.existeReg(tramiteId)) {
			tramite = bitTramiteDao.mapeaRegId(tramiteId);
		}
		
		List<BitRequisito> lisRequisito = bitRequisitoDao.lisRequisitos("ORDER BY NOMBRE");
		
		HashMap<String, String> mapaRequisitosTramite = new HashMap<String, String>();
		if(bitRequisitoTramiteDao.existeReg(tramiteId)) {
			mapaRequisitosTramite = bitRequisitoTramiteDao.mapaRequisitosTramite(tramiteId);
		}
		
		modelo.addAttribute("tramite", tramite);
		modelo.addAttribute("lisRequisito", lisRequisito);
		modelo.addAttribute("mapaRequisitosTramite", mapaRequisitosTramite);
		modelo.addAttribute("mensaje", mensaje);
		
		return "bitacora/tramite/agregarRequisitos";
	}
	
	@RequestMapping("/bitacora/tramite/grabarRequisitos")
	public String bitacoraTramiteGrabarRequisitos(HttpServletRequest request, Model modelo){
		String tramiteId 	= request.getParameter("TramiteId")==null?"0":request.getParameter("TramiteId");
		String mensaje		= "0";
		
		List<BitRequisito> lisRequisito = bitRequisitoDao.lisRequisitos("ORDER BY NOMBRE");
		
		bitRequisitoTramiteDao.deleteTotalReg(tramiteId);
		
		if(tramiteId.equals("0")) {
			mensaje = "1";
		}
		for(BitRequisito requisito : lisRequisito){
			if(request.getParameter(requisito.getRequisitoId()) != null) {
				if(bitRequisitoTramiteDao.insertReg(request.getParameter(requisito.getRequisitoId()),tramiteId)) {
					mensaje = "1";
				}else {
					mensaje = "2";
				}
			}
		}
		
		return "redirect:/bitacora/tramite/agregarRequisitos?TramiteId="+tramiteId+"&Mensaje="+mensaje;
	}
}