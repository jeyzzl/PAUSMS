package aca.web.bsc;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.saii.spring.SaiiPeriodo;

@Controller
public class ContBscPeriodo {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	aca.saii.spring.SaiiPeriodoDao saiiPeriodoDao;
	
	@RequestMapping("/bsc/periodo/listado")
	public String bscPeriodoListado(HttpServletRequest request, Model modelo){
		
		String periodoId = request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		
		SaiiPeriodo periodo = new SaiiPeriodo();
		if (saiiPeriodoDao.existeReg(periodoId)) {
			periodo = saiiPeriodoDao.mapeaRegId(periodoId);
		}
		
		List<SaiiPeriodo> lisPeriodo = saiiPeriodoDao.listAll(" ORDER BY PERIODO_ID DESC");
		
		modelo.addAttribute("lisPeriodo",lisPeriodo);		
		modelo.addAttribute("periodo",periodo);
		
		return "bsc/periodo/listado";
	}
	
	@RequestMapping("/bsc/periodo/guardar")
	public String bscPeriodoGuardar(HttpServletRequest request, Model modelo){
		
		String periodoId 	= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String nombre 	= request.getParameter("Nombre")==null?"0":request.getParameter("Nombre");
		String estado 	= request.getParameter("Estado")==null?"0":request.getParameter("Estado");
		String fecha 	= request.getParameter("Fecha")==null?"0":request.getParameter("Fecha");
		aca.saii.spring.SaiiPeriodo saiiPeriodo = new aca.saii.spring.SaiiPeriodo();
		
		String mensaje = "-";
		
		saiiPeriodo.setPeriodoId(periodoId);
		saiiPeriodo.setPeriodoNombre(nombre);
		saiiPeriodo.setEstado(estado);
		saiiPeriodo.setFecha(fecha);
		
		if(saiiPeriodoDao.existeReg(periodoId)) {
			if (saiiPeriodoDao.updateReg(saiiPeriodo)) {
				mensaje = "0";
			}			
		}else {
			if (saiiPeriodoDao.insertReg(saiiPeriodo)) {
				mensaje = "1";
			}							
		}
		
		return "redirect:/bsc/periodo/listado?Mensaje="+mensaje;
	}
	
	@RequestMapping("/bsc/periodo/borrar")
	public String bscPeriodoBorrar(HttpServletRequest request, Model modelo){
		
		String periodoId 	= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");	
		String mensaje = "-";
		
		if(saiiPeriodoDao.existeReg(periodoId)) {
			if (saiiPeriodoDao.deleteReg(periodoId)) {
				mensaje = "2";
			}			
		}
		
		return "redirect:/bsc/periodo/listado?Mensaje="+mensaje;
	}
	
}