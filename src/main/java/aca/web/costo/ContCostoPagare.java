package aca.web.costo;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.calcula.spring.CalPagare;
import aca.calcula.spring.CalPagareDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaBloque;
import aca.carga.spring.CargaBloqueDao;
import aca.carga.spring.CargaDao;
import aca.catalogo.spring.CatPeriodo;
import aca.catalogo.spring.CatPeriodoDao;

@Controller
public class ContCostoPagare {	
	
	@Autowired
	CalPagareDao calPagareDao;

	@Autowired
	CatPeriodoDao catPeriodoDao;
	
	@Autowired
	CargaDao cargaDao;
	
	@Autowired
	CargaBloqueDao cargaBloqueDao;
	
	@RequestMapping("/costo/pagare/lista")
	public String costoPagareLista(HttpServletRequest request, Model modelo){
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
		
		HashMap<String, CargaBloque> mapaBloques = cargaBloqueDao.mapaBloques();
		
		List<CalPagare> lista = calPagareDao.lisPorCarga(cargaId, bloqueId, " ORDER BY TO_DATE(FECHA,'DD-MM-YYYY')");
		
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("bloqueId", bloqueId);
		modelo.addAttribute("lista", lista);
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisBloques", lisBloques);
		modelo.addAttribute("mapaBloques", mapaBloques);
		
		return "costo/pagare/lista";
	}
	
	@RequestMapping("/costo/pagare/editar")
	public String costoPagareEditar(HttpServletRequest request, Model modelo){		
		String pagareId	= request.getParameter("PagareId")==null?"0":request.getParameter("PagareId");
		String mensaje		= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		
		String periodoId 	= "0";
		String cargaId 		= "0";
		String bloqueId 	= "0";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){			
			periodoId		= (String)sesion.getAttribute("periodo");
			cargaId 		= (String)sesion.getAttribute("cargaId");
			bloqueId 		= (String)sesion.getAttribute("bloqueId");
		}	
		
		CalPagare objeto	= new CalPagare();
		
		if (calPagareDao.existeReg(pagareId)) {
			objeto = calPagareDao.mapeaRegId(pagareId);				
		}
		
		modelo.addAttribute("periodoId", periodoId);
	    modelo.addAttribute("cargaId", cargaId);
	    modelo.addAttribute("bloqueId", bloqueId);
	    modelo.addAttribute("pagare", objeto);
	    modelo.addAttribute("mensaje", mensaje);
	    
	    return "costo/pagare/editar";
	}
	
	@RequestMapping("/costo/pagare/grabar")
	public String costoPagareGrabar(HttpServletRequest request){
		String pagareId		= request.getParameter("PagareId")==null?"0":request.getParameter("PagareId");
		String pagareNombre	= request.getParameter("Nombre")==null?"-":request.getParameter("Nombre");
		String cargaId		= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String bloqueId		= request.getParameter("BloqueId")==null?"0":request.getParameter("BloqueId");
		String fecha		= request.getParameter("Fecha")==null?"0":request.getParameter("Fecha");
		String mensaje			= "0";
		
		CalPagare objeto	 	= new CalPagare();

		if (calPagareDao.existeReg(pagareId)) {
			objeto = calPagareDao.mapeaRegId(pagareId);		
			
			objeto.setCargaId(cargaId);
			objeto.setBloqueId(bloqueId);
			objeto.setFecha(fecha);
			objeto.setPagareNombre(pagareNombre);
			if(calPagareDao.updateReg(objeto)) {
				mensaje = "1";
			}else {
				mensaje = "2";
			}	
		}else {
			pagareId = calPagareDao.maximoReg();
			objeto.setPagareId(pagareId);
			objeto.setCargaId(cargaId);
			objeto.setBloqueId(bloqueId);
			objeto.setFecha(fecha);
			objeto.setPagareNombre(pagareNombre);
			
			if(calPagareDao.insertReg(objeto)) {
				mensaje = "1";
			}else {
				mensaje = "2";
			}	
		}
		
		return "redirect:/costo/pagare/editar?PagareId="+pagareId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/costo/pagare/borrar")
	public String costoPagareBorrar(HttpServletRequest request){		
		String pagareId		= request.getParameter("PagareId")==null?"0":request.getParameter("PagareId");
		
		if (calPagareDao.existeReg(pagareId)) {
			calPagareDao.deleteReg(pagareId);
		}
		
	    return "redirect:/costo/pagare/lista";
	}
}