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
import aca.bitacora.spring.BitRequisitoTramite;
import aca.bitacora.spring.BitRequisitoTramiteDao;
import aca.bitacora.spring.BitTramite;
import aca.bitacora.spring.BitTramiteAlumno;

@Controller
public class ContBitacoraTramiteRequisitos {
	
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
	BitRequisitoDao bitRequisitoDao;
	
	@Autowired
	BitRequisitoTramiteDao bitRequisitoTramiteDao;
	
	@RequestMapping("/bitacora/tramiteRequi/tramite")
	public String bitacoraTramiteTramite(HttpServletRequest request, Model modelo){
		List<BitTramite> lisTramites 			= bitTramiteDao.lisTramites("ORDER BY AREA_NOMBRE(AREA_ID), NOMBRE");
		
		HashMap<String, String> mapaAreas 		= bitAreaDao.mapaAreas();
		HashMap<String, String> mapaTramite 	= bitTramiteAlumnoDao.mapTramites();
		
		modelo.addAttribute("lisTramites", lisTramites);
		modelo.addAttribute("mapaAreas", mapaAreas);
		modelo.addAttribute("mapaTramite", mapaTramite);
		return "bitacora/tramiteRequi/tramite";
	}	
	
	@RequestMapping("/bitacora/tramiteRequi/agregarRequisitos")
	public String bitacoraTramiteEditar(HttpServletRequest request, Model modelo){
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
		
		return "bitacora/tramiteRequi/agregarRequisitos";
	}
	
	@RequestMapping("/bitacora/tramiteRequi/grabar")
	public String bitacoraTramiteGrabarTramite(HttpServletRequest request, Model modelo){
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
		
		return "redirect:/bitacora/tramiteRequi/agregarRequisitos?TramiteId="+tramiteId+"&Mensaje="+mensaje;
	}
}