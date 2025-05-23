package aca.web.trabajo;

import java.util.HashMap;
import java.util.List;


import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.trabajo.spring.TrabPeriodo;
import aca.trabajo.spring.TrabPeriodoDao;
import aca.trabajo.spring.TrabInformeDao;
//import aca.trabajo.spring.BecPuestoDao;

@Controller
public class ContTrabajoPeriodo {

	@Autowired
	private TrabPeriodoDao trabPeriodoDao;
	
	@Autowired
	private TrabInformeDao trabInformeDao;

//	private BecPuestoDao becPuestoDao;

	@RequestMapping("/trabajo/periodo/periodos")
	public String trabajoPeriodoPeriodos(HttpServletRequest request, Model modelo) {
		
		List<TrabPeriodo> listaPeriodos 			= trabPeriodoDao.lisTodos("ORDER BY PERIODO_ID DESC");
		
		HashMap<String, String> mapaPorPeriodo 	= trabInformeDao.mapaPorPeriodo("");
//		HashMap<String,String> mapaPeriodos		= becPuestoDao.mapaPeriodos();

		modelo.addAttribute("listaPeriodos", listaPeriodos);
		modelo.addAttribute("mapaPorPeriodo", mapaPorPeriodo);
//		modelo.addAttribute("mapaPeriodos", mapaPeriodos);

		return "trabajo/periodo/periodos";
	}

	@RequestMapping("/trabajo/periodo/editarPeriodo")
	public String trabajoPeriodoEditarPeriodo(HttpServletRequest request, Model modelo){

		String periodoId  			= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		TrabPeriodo trabPeriodo 	= new TrabPeriodo();
		if(trabPeriodoDao.existeReg(periodoId)){
			trabPeriodo = trabPeriodoDao.mapeaRegId(periodoId);
		} else {
			trabPeriodo.setPeriodoId(trabPeriodoDao.maximoReg());
		}

		modelo.addAttribute("trabPeriodo", trabPeriodo);

		return "trabajo/periodo/editarPeriodo";
	}

	@RequestMapping("/trabajo/periodo/grabarPeriodo")
	public String trabajoPeriodoGrabarTrabajo(HttpServletRequest request, Model modelo){

		String periodoId  			= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String nombre 				= request.getParameter("Nombre")==null?"0":request.getParameter("Nombre");
		String fechaIni 			= request.getParameter("FechaIni")==null?"0":request.getParameter("FechaIni");
		String fechaFin				= request.getParameter("FechaFin")==null?"0":request.getParameter("FechaFin");
		String estado				= request.getParameter("Estado")==null?"0":request.getParameter("Estado");
//		String tipo					= request.getParameter("Tipo")==null?"0":request.getParameter("Tipo");
		String mensaje 				= "";
		TrabPeriodo objeto = new TrabPeriodo();

		if(trabPeriodoDao.existeReg(periodoId)) {
			objeto = trabPeriodoDao.mapeaRegId(periodoId);
			objeto.setNombrePeriodo(nombre);
			objeto.setFechaIni(fechaIni);
			objeto.setFechaFin(fechaFin);
			objeto.setEstado(estado);

			if(trabPeriodoDao.updateReg(objeto)) {
				mensaje = "Updated";
			}
		}else {
			objeto.setPeriodoId(periodoId);
			objeto.setNombrePeriodo(nombre);
			objeto.setFechaIni(fechaIni);
			objeto.setFechaFin(fechaFin);
			objeto.setEstado(estado);

			if(trabPeriodoDao.insertReg(objeto)) {
				mensaje = "Saved";
			}
		}

		return "redirect:/trabajo/periodo/editarPeriodo?Mensaje="+mensaje+"&PeriodoId="+periodoId;
	}

	@RequestMapping("/trabajo/periodo/borrarPeriodo")
	public String trabajoPeriodoBorrarPeriodo(HttpServletRequest request, Model modelo){
		String periodoId		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String mensaje			= "-";

		if (trabPeriodoDao.existeReg(periodoId)) {
			if (trabPeriodoDao.deleteReg(periodoId)) {
				mensaje = "Deleted";
			}else {
				mensaje = "Error deleting";
			}
		}

		return "redirect:/trabajo/periodo/periodos?Mensaje="+mensaje;
	}

}