package aca.web.finanzas;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;
import aca.catalogo.spring.CatModalidad;
import aca.catalogo.spring.CatModalidadDao;
import aca.catalogo.spring.CatPeriodo;
import aca.catalogo.spring.CatPeriodoDao;
import aca.financiero.spring.FinBloqueoDao;
import aca.financiero.spring.FinPeriodo;

@Controller
public class ContFinanzasPeriodo {
	
	@Autowired
	aca.financiero.spring.FinPeriodoDao finPeriodoDao;
	
	@Autowired
	CatPeriodoDao catPeriodoDao;
	
	@Autowired
	CargaDao cargaDao;
	
	@Autowired
	CatModalidadDao catModalidadDao;
	
	@Autowired
	FinBloqueoDao finBloqueoDao;
	
	
	@RequestMapping("/finanzas/periodo/periodo")
	public String finanzasPeriodoPeriodo(HttpServletRequest request, Model modelo){	
		
		List<FinPeriodo> lisPeriodos 			= finPeriodoDao.getListAll("ORDER BY FECHA_INI DESC");
		HashMap<String,String> mapaBloqueados 	= finBloqueoDao.mapaBloqueados();
		
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("mapaBloqueados", mapaBloqueados);
		
		return "finanzas/periodo/periodo";
	}
	
	@RequestMapping("/finanzas/periodo/accion")
	public String finanzasPeriodoAccion(HttpServletRequest request, Model modelo){
		
		String periodoId 	= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		
		FinPeriodo periodo 	= new FinPeriodo();
		if (finPeriodoDao.existeReg(periodoId)) {
			periodo = finPeriodoDao.mapeaRegId(periodoId);
		}		
		modelo.addAttribute("periodo", periodo);	
		
		return "finanzas/periodo/accion";
	}
	
	@RequestMapping("/finanzas/periodo/grabar")
	public String finanzasPeriodoGrabar(HttpServletRequest request, Model modelo){		
		
		String periodoId 	= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");		
		
		String fechaFin 	= request.getParameter("FechaFin")==null?"-":request.getParameter("FechaFin");
		String fechaIni 	= request.getParameter("FechaIni")==null?"-":request.getParameter("FechaIni");
		String mensaje 		= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		String estado 		= request.getParameter("Estado")==null?"-":request.getParameter("Estado");
		String cantidad 	= request.getParameter("Cantidad")==null?"0":request.getParameter("Cantidad");		
		String cargas 		= request.getParameter("Cargas")==null?"-":request.getParameter("Cargas");	
		String modalidades 	= request.getParameter("Modalidades")==null?"-":request.getParameter("Modalidades");
		String resultado 	= "-";
		
		FinPeriodo periodo = new FinPeriodo();
		
		periodo.setEstado(estado);
		periodo.setFechaFin(fechaFin);
		periodo.setFechaIni(fechaIni);
		periodo.setMensaje(mensaje);
		periodo.setCargas(cargas);
		periodo.setModalidades(modalidades);
		periodo.setCantidad(cantidad);
		
		if(finPeriodoDao.existeReg(periodoId)){
			periodo.setPeriodoId(periodoId);			
			if (finPeriodoDao.updateReg(periodo)){				
				resultado = "¡ Se modific&oacute; correctamente el periodo "+periodo.getPeriodoId()+"!";
			}else{
				resultado = "¡ Error al modificar !";
			}
		}else{				
			periodoId = finPeriodoDao.maximoReg();
			periodo.setPeriodoId( periodoId );
			if (finPeriodoDao.insertReg(periodo)){				
				resultado = "¡ Se guard&oacute; correctamente el periodo "+periodo.getPeriodoId()+"!";
			}
		}
		
		return "redirect:/finanzas/periodo/accion?PeriodoId="+periodo.getPeriodoId()+"&Resultado="+resultado;
	}	
	
	@RequestMapping("/finanzas/periodo/borrar")
	public String finanzasPeriodoBorrar(HttpServletRequest request, Model modelo){		
		
		String periodoId 	= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		
		if(finPeriodoDao.existeReg(periodoId)){						
			finPeriodoDao.deleteReg(periodoId);				
		}
		
		return "redirect:/finanzas/periodo/periodo";
	}
	
	@RequestMapping("/finanzas/periodo/cargas")
	public String finanzasPeriodoCargas(HttpServletRequest request, Model modelo){		
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();		
		String periodoId 		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String finPeriodoId 	= request.getParameter("FinPeriodoId")==null?"0":request.getParameter("FinPeriodoId");
		
		if (sesion != null && !finPeriodoId.equals("0")) {
			sesion.setAttribute("finPeriodoId", finPeriodoId);
		}else {
			finPeriodoId = sesion.getAttribute("finPeriodoId").toString(); 
		}
		
		FinPeriodo finPeriodo 	= new FinPeriodo();
		if (finPeriodoDao.existeReg(finPeriodoId)) {
			finPeriodo = finPeriodoDao.mapeaRegId(finPeriodoId);
		}
		
		List<CatPeriodo> lisPeriodos = catPeriodoDao.getListAll("ORDER BY PERIODO_ID DESC");
		if (periodoId.equals("0") && lisPeriodos.size() >= 1){
			periodoId = lisPeriodos.get(0).getPeriodoId();
		}		
		
		List<Carga> lisCargas 		= cargaDao.getListCargaPeriodo(periodoId, "ORDER BY CARGA_ID");
		
		modelo.addAttribute("finPeriodo", finPeriodo);
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("lisCargas", lisCargas);
		
		return "finanzas/periodo/cargas";
	}
	
	@RequestMapping("/finanzas/periodo/grabarCargas")
	public String finanzasPeriodoGrabarCargas(HttpServletRequest request, Model modelo){	
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();		
		String periodoId 		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String finPeriodoId		= "0";
		String cargas			= "";
		
		if (sesion != null ) {		
			finPeriodoId = sesion.getAttribute("finPeriodoId").toString(); 
		}
		
		FinPeriodo finPeriodo 	= new FinPeriodo();
		if (finPeriodoDao.existeReg(finPeriodoId)) {
			finPeriodo = finPeriodoDao.mapeaRegId(finPeriodoId);
		}		
		List<Carga> lisCargas 		= cargaDao.getListCargaPeriodo(periodoId, "ORDER BY CARGA_ID");
		
		// Recorre las cargas, busca las seleccionadas y las graba		
		for(Carga carga : lisCargas){
			if (request.getParameter(carga.getCargaId()) != null){
				cargas += "-"+carga.getCargaId();				
			}
		}
		cargas += "-";
		
		finPeriodo.setPeriodoId(finPeriodoId);				
		if(finPeriodoDao.existeReg(finPeriodoId)){			
			finPeriodoDao.updateCargas(finPeriodoId, cargas);
		}
		
		return "redirect:/finanzas/periodo/cargas?PeriodoId="+periodoId;		
	}
	
	@RequestMapping("/finanzas/periodo/modalidades")
	public String finanzasPeriodoModalidades(HttpServletRequest request, Model modelo){		
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();		
		String finPeriodoId 	= request.getParameter("FinPeriodoId")==null?"0":request.getParameter("FinPeriodoId");
		
		if (sesion != null && !finPeriodoId.equals("0")) {
			sesion.setAttribute("finPeriodoId", finPeriodoId);
		}else {
			finPeriodoId = sesion.getAttribute("finPeriodoId").toString(); 
		}
		
		FinPeriodo finPeriodo 	= new FinPeriodo();
		if (finPeriodoDao.existeReg(finPeriodoId)) {
			finPeriodo = finPeriodoDao.mapeaRegId(finPeriodoId);
		}
		
		List <CatModalidad> lisModalidades	= catModalidadDao.getListAll("ORDER BY MODALIDAD_ID");
		
		modelo.addAttribute("finPeriodo", finPeriodo);		
		modelo.addAttribute("lisModalidades", lisModalidades);
		
		return "finanzas/periodo/modalidades";
	}
	
	@RequestMapping("/finanzas/periodo/grabarModalidades")
	public String finanzasPeriodoGrabarModalidades(HttpServletRequest request, Model modelo){	
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();		
		String finPeriodoId		= "0";
		String modalidades		= "";
		
		if (sesion != null ) {		
			finPeriodoId = sesion.getAttribute("finPeriodoId").toString(); 
		}
		
		FinPeriodo finPeriodo 	= new FinPeriodo();
		if (finPeriodoDao.existeReg(finPeriodoId)) {
			finPeriodo = finPeriodoDao.mapeaRegId(finPeriodoId);
		}		
		List <CatModalidad> lisModalidades	= catModalidadDao.getListAll("ORDER BY MODALIDAD_ID");
		
		// Recorre las cargas, busca las seleccionadas y las graba		
		for(CatModalidad modalidad : lisModalidades){
			if (request.getParameter(modalidad.getModalidadId()) != null){
				modalidades += "-"+modalidad.getModalidadId();			
			}
		}
		modalidades += "-";
		
		finPeriodo.setPeriodoId(finPeriodoId);				
		if(finPeriodoDao.existeReg(finPeriodoId)){			
			finPeriodoDao.updateModalidades(finPeriodoId, modalidades);
		}
		
		return "redirect:/finanzas/periodo/modalidades";		
	}
}