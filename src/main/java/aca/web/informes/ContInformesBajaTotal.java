package aca.web.informes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaBloque;
import aca.carga.spring.CargaBloqueDao;
import aca.carga.spring.CargaDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatPeriodo;
import aca.catalogo.spring.CatPeriodoDao;
import aca.financiero.spring.FesCcMateriaDao;
import aca.reportes.spring.AltasBajas;
import aca.reportes.spring.AltasBajasDao;

@Controller
public class ContInformesBajaTotal {	
	
	@Autowired
	CatPeriodoDao catPeriodoDao;
	
	@Autowired
	CargaDao cargaDao;
	
	@Autowired
	CargaBloqueDao cargaBloqueDao;
	
	@Autowired
	AccesoDao accesoDao;
	
	@Autowired
	CatFacultadDao catFacultadDao;
	
	@Autowired
	AltasBajasDao altasBajasDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;	
	
	@Autowired
	FesCcMateriaDao fesCcMateriaDao;	
	
	
	@RequestMapping("/informes/baja_total/bajas")
	public String informesBajaTotalBajas(HttpServletRequest request, Model modelo){		
		
		String codigoPersonal	= "0";		
		String periodoId		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String cargaId			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String bloqueId			= request.getParameter("BloqueId")==null?"0":request.getParameter("BloqueId");
		String accion			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		String periodoSesion 	= "0";
		String cargaSesion 		= "0";
		String bloqueSesion		= "0";
		
		HttpSession sesion 		= ((HttpServletRequest) request).getSession();
		if (sesion != null) {
			codigoPersonal		= (String)sesion.getAttribute("codigoPersonal");
			periodoSesion 		= (String)sesion.getAttribute("periodo");
        	cargaSesion 		= (String)sesion.getAttribute("cargaId");
        	bloqueSesion 		= (String)sesion.getAttribute("bloqueId");
		}
		
		List<CatPeriodo> lisPeriodos = catPeriodoDao.getListAll(" ORDER BY PERIODO_ID DESC");		
		if(periodoId.equals("0") && lisPeriodos.size() > 0) {
			if(periodoSesion != null) {
				periodoId = periodoSesion;
			}else {
				periodoId = lisPeriodos.get(0).getPeriodoId();
			}
		}else if(!periodoId.equals("0")){
			sesion.setAttribute("periodo", periodoId);
		}		
		
		List<Carga> lisCargas 	= cargaDao.getListPeriodo(periodoId," AND ESTADO = '1' ORDER BY PERIODO,CARGA_ID");
		if (cargaId.equals("0")) cargaId = cargaSesion;
		boolean existeCarga 	= false;
		for (Carga carga : lisCargas) {
			if (carga.getCargaId().equals(cargaId)) existeCarga = true;
		}
		if (existeCarga==false && lisCargas.size() > 0) {
			cargaId = lisCargas.get(0).getCargaId();
			sesion.setAttribute("cargaId", cargaId);
		}else if(existeCarga) {
			sesion.setAttribute("cargaId", cargaId);
		}
		
		List<CargaBloque> lisBloques = cargaBloqueDao.getListAll("ORDER BY CARGA_ID, BLOQUE_ID");		
		if (bloqueId.equals("0") && lisBloques.size() > 0) {
			if(bloqueSesion != null) {
				bloqueId = bloqueSesion;
			}else {
				bloqueId = lisBloques.get(0).getCargaId();
			}
		}else if(!bloqueId.equals("0")) {
			sesion.setAttribute("bloqueId", bloqueId);
		}
		
		List<AltasBajas> lisBajas			= new ArrayList<AltasBajas>();
		if (accion.equals("1")) {
			lisBajas = altasBajasDao.getListBajas(cargaId,bloqueId);
		}	
		
		Acceso acceso = accesoDao.mapeaRegId(codigoPersonal);			
		
		HashMap<String,CatFacultad> mapaFacultad 		= catFacultadDao.getMapFacultad("");
		HashMap<String,CatCarrera> mapaCarrera  		= catCarreraDao.mapaCarreras();		
		HashMap<String, String> mapaCreditosEnCalculo 	= fesCcMateriaDao.mapaCreditosEnCalculo(cargaId, bloqueId);
		HashMap<String, String> mapaNumCursosAltas		= altasBajasDao.mapaNumCursos(cargaId, "AND TIPO = 'A' AND TIPOCAL_ID ! = 'M' ");
		HashMap<String, String> mapaNumCursosBajas 		= altasBajasDao.mapaNumCursos(cargaId, "AND TIPO = 'B' AND TIPOCAL_ID  = '3' ");
		
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("bloqueId", bloqueId);
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisBloques", lisBloques);
		modelo.addAttribute("lisBajas", lisBajas);
		modelo.addAttribute("acceso", acceso);		
		modelo.addAttribute("mapaFacultad", mapaFacultad);
		modelo.addAttribute("mapaCarrera", mapaCarrera);	
		modelo.addAttribute("mapaCreditosEnCalculo", mapaCreditosEnCalculo);	
		modelo.addAttribute("mapaNumCursosAltas", mapaNumCursosAltas);	
		modelo.addAttribute("mapaNumCursosBajas", mapaNumCursosBajas);	
		
		return "informes/baja_total/bajas";
	}		

}