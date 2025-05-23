package aca.web.costo;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.calcula.spring.CalConcepto;
import aca.calcula.spring.CalConceptoDao;
import aca.calcula.spring.CalCosto;
import aca.calcula.spring.CalCostoDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaBloque;
import aca.carga.spring.CargaBloqueDao;
import aca.carga.spring.CargaDao;
import aca.catalogo.spring.CatPeriodo;
import aca.catalogo.spring.CatPeriodoDao;

@Controller
public class ContCostoCosto {	
	
	@Autowired
	CalCostoDao calCostoDao;
	
	@Autowired
	CalConceptoDao calConceptoDao;
	
	@Autowired
	CatPeriodoDao catPeriodoDao;
	
	@Autowired
	CargaDao cargaDao;
	
	@Autowired
	CargaBloqueDao cargaBloqueDao;
	
	
	@RequestMapping("/costo/costo/lista")
	public String costoCostoLista(HttpServletRequest request, Model modelo){
		String periodoId		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String cargaId			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String bloqueId			= request.getParameter("BloqueId")==null?"0":request.getParameter("BloqueId");
		String periodoSesion 	= "0";
		String cargaSesion 		= "0";
		String bloqueSesion 	= "0";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			
			periodoSesion 		= (String)sesion.getAttribute("periodo");
			cargaSesion 		= (String)sesion.getAttribute("cargaId");
			bloqueSesion 		= (String)sesion.getAttribute("bloqueId");
			
			if (!periodoId.equals("0")) sesion.setAttribute("periodo", periodoId); else periodoId = periodoSesion;
			if (!cargaId.equals("0")) sesion.setAttribute("cargaId", cargaId); else cargaId	= cargaSesion;
			if (!bloqueId.equals("0")) sesion.setAttribute("bloqueId", bloqueId); else bloqueId = bloqueSesion;
			
		}
		
		List<CatPeriodo> lisPeriodos = catPeriodoDao.getListAll(" ORDER BY PERIODO_ID DESC");		
		if(periodoId.equals("0") && lisPeriodos.size() > 0) {
			if(periodoSesion != null) {
				periodoId = periodoSesion;
			}else {
				periodoId = lisPeriodos.get(0).getPeriodoId();
			}
		}else if(!periodoId.equals("0")){
			sesion.setAttribute("periodoId", periodoId);
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
		
		List<CalCosto> lista = calCostoDao.lisTodos(" WHERE CARGA_ID = '"+cargaId+"' AND BLOQUE_ID = '"+bloqueId+"' ORDER BY COSTO_ID,CONCEPTO_ID");
	
		HashMap<String,CalConcepto> mapaConceptos = calConceptoDao.mapaTodos();			
	
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("bloqueId", bloqueId);
		modelo.addAttribute("lista", lista);		
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisBloques", lisBloques);
		modelo.addAttribute("mapaConceptos", mapaConceptos);
		
		return "costo/costo/lista";
	}
	
	@RequestMapping("/costo/costo/editar")
	public String costoCostoEditar(HttpServletRequest request, Model modelo){		
		String costoId		= request.getParameter("CostoId")==null?"0":request.getParameter("CostoId");
		String conceptoId	= request.getParameter("ConceptoId")==null?"0":request.getParameter("ConceptoId");
		
		String periodoId 	= "0";
		String cargaId 		= "0";
		String bloqueId 	= "0";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){			
			periodoId		= (String)sesion.getAttribute("periodo");
			cargaId 		= (String)sesion.getAttribute("cargaId");
			bloqueId 		= (String)sesion.getAttribute("bloqueId");
		}	
				
		List<CalConcepto> listaConceptos 	= calConceptoDao.lisTodos(" ORDER BY TIPO, CONCEPTO_NOMBRE");
		
		String mensaje		= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		
		CalCosto objeto	= new CalCosto();
		
		if (calCostoDao.existeReg(costoId,conceptoId)) {
			objeto = calCostoDao.mapeaRegId(costoId,conceptoId);				
		}
		
		modelo.addAttribute("periodoId", periodoId);
	    modelo.addAttribute("cargaId", cargaId);
	    modelo.addAttribute("bloqueId", bloqueId);
	    modelo.addAttribute("costo", objeto);
	    modelo.addAttribute("mensaje", mensaje);
	    modelo.addAttribute("listaConceptos", listaConceptos);
	    
	    return "costo/costo/editar";
	}
	
	@RequestMapping("/costo/costo/grabar")
	public String costoCostoGrabar(HttpServletRequest request){
		String costoId		= request.getParameter("CostoId")==null?"0":request.getParameter("CostoId");
		String conceptoId	= request.getParameter("ConceptoId")==null?"0":request.getParameter("ConceptoId");		
		String cargaId		= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String bloqueId		= request.getParameter("BloqueId")==null?"0":request.getParameter("BloqueId");
		String importe		= request.getParameter("Importe")==null?"0":request.getParameter("Importe");
		String comentario	= request.getParameter("Comentario")==null?"0":request.getParameter("Comentario");
		String mensaje		= "0";
		
		CalCosto objeto	 	= new CalCosto();
		
		CalConcepto calConcepto = new CalConcepto(); 

		if (calConceptoDao.existeReg(conceptoId)) {
			calConcepto = calConceptoDao.mapeaRegId(conceptoId);
		}
		
		if (calCostoDao.existeReg(costoId,conceptoId)) {
			objeto = calCostoDao.mapeaRegId(costoId,conceptoId);		
			
			objeto.setCargaId(cargaId);
			objeto.setImporte(importe);
			objeto.setBloqueId(bloqueId);
			objeto.setTipo(calConcepto.getTipo());
			objeto.setComentario(comentario);
			
			if(calCostoDao.updateReg(objeto)) {
				mensaje = "1";
			}else {
				mensaje = "2";
			}	
		}else {
			costoId = calCostoDao.maximoReg();
			objeto.setCostoId(costoId);
			objeto.setConceptoId(conceptoId);
			objeto.setCargaId(cargaId);
			objeto.setImporte(importe);
			objeto.setBloqueId(bloqueId);
			objeto.setTipo(calConcepto.getTipo());
			objeto.setComentario(comentario);
			
			if(calCostoDao.insertReg(objeto)) {
				mensaje = "1";
			}else {
				mensaje = "2";
			}	
		}
		
		return "redirect:/costo/costo/editar?CostoId="+costoId+"&ConceptoId="+conceptoId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/costo/costo/borrar")
	public String costoCostoBorrar(HttpServletRequest request){		
		String costoId		= request.getParameter("CostoId")==null?"0":request.getParameter("CostoId");
		String conceptoId	= request.getParameter("ConceptoId")==null?"0":request.getParameter("ConceptoId");		
		
		if (calCostoDao.existeReg(costoId,conceptoId)) {
			calCostoDao.deleteReg(costoId,conceptoId);
		}
		
	    return "redirect:/costo/costo/lista";
	}
}