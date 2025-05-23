package aca.web.disciplina;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.disciplina.spring.CondReporte;
import aca.disciplina.spring.CondReporteDao;

@Controller
public class ContDisciplinaCatReportes {	
	
	@Autowired
	private CondReporteDao condReporteDao;
		
	
	@RequestMapping("/disciplina/cat_reportes/reportes")
	public String disciplinaCatReportesReportes(HttpServletRequest request, Model modelo){	

		List<CondReporte> lisReportes	= condReporteDao.getListAll(" ORDER BY 1");
		modelo.addAttribute("lisReportes", lisReportes);
		
		return "disciplina/cat_reportes/reportes";
	}
	
	@RequestMapping("/disciplina/cat_reportes/editar")
	public String disciplinaCatReportesEditar(HttpServletRequest request, Model modelo){
		
		CondReporte reporte 		= new CondReporte(); 
		String idReporte 			= request.getParameter("IdReporte")==null?"0":request.getParameter("IdReporte");
		if (condReporteDao.existeReg(idReporte)) {
			reporte = condReporteDao.mapeaRegId(idReporte);
		}				
		modelo.addAttribute("reporte", reporte);
		
		return "disciplina/cat_reportes/editar";
	}	
	
	@RequestMapping("/disciplina/cat_reportes/grabar")
	public String disciplinaCatReportesGrabar(HttpServletRequest request){
		
		CondReporte reporte 		= new CondReporte(); 
		String idReporte 			= request.getParameter("IdReporte")==null?"0":request.getParameter("IdReporte");
		String nombre 				= request.getParameter("Nombre")==null?"-":request.getParameter("Nombre");
		String tipo 				= request.getParameter("Tipo")==null?"0":request.getParameter("Tipo");
		if (condReporteDao.existeReg(idReporte)) {
			reporte.setIdReporte(idReporte);
			reporte.setNombre(nombre);
			reporte.setTipo(tipo);
			condReporteDao.updateReg(reporte);
		}else{ 
			idReporte=condReporteDao.maximoReg();
			reporte.setIdReporte(idReporte);
			reporte.setNombre(nombre);
			reporte.setTipo(tipo);
			condReporteDao.insertReg(reporte);
		}
		
		return "redirect:/disciplina/cat_reportes/editar?IdReporte="+idReporte;
	}	
	
	@RequestMapping("/disciplina/cat_reportes/borrar")
	public String disciplinaCatReportesBorrar(HttpServletRequest request){
		
		String idReporte 		= request.getParameter("IdReporte")==null?"0":request.getParameter("IdReporte");
		if (condReporteDao.existeReg(idReporte)) {
			condReporteDao.deleteReg(idReporte);
		}				
		
		return "redirect:/disciplina/cat_reportes/reportes";
	}	

}