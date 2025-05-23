package aca.web.horas;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.emp.spring.EmpPeriodo;
import aca.emp.spring.EmpPeriodoDao;

@Controller
public class ContHorasPeriodo{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	private EmpPeriodoDao empPeriodoDao;
	
	@RequestMapping("/horas/periodo/periodo")
	public String horasPeriodoPeriodo(HttpServletRequest request, Model modelo){
		
		List<aca.emp.spring.EmpPeriodo> lista	= empPeriodoDao.listAll(" ORDER BY PERIODO_NOMBRE");
		
		modelo.addAttribute("lista", lista);
		
		return "horas/periodo/periodo";
	}	
	
	@RequestMapping("/horas/periodo/nuevo")
	public String horasPeriodoNuevo(HttpServletRequest request, Model modelo){
		String periodoId	= request.getParameter("PeriodoId")==null?"":request.getParameter("PeriodoId");
		
		EmpPeriodo empPeriodo 	= new EmpPeriodo();
		if (empPeriodoDao.existeReg(periodoId)){
			empPeriodo = empPeriodoDao.mapeaRegId(periodoId);
		}
		
		modelo.addAttribute("empPeriodo", empPeriodo);
		
		return "horas/periodo/agregar";
	}
	
	@RequestMapping("/horas/periodo/agregar")
	public String horasPeriodoAgregar(HttpServletRequest request, Model modelo){		
		
		String mensaje			= "X";
		String colorMensaje		= "";
		
		String accion			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		
		String periodoId 		= request.getParameter("PeriodoId").equals("0")?empPeriodoDao.maximoReg():request.getParameter("PeriodoId");
		String periodoNombre	= request.getParameter("PeriodoNombre")==null?"":request.getParameter("PeriodoNombre");
		String fechaIni 		= request.getParameter("FechaIni")==null?"":request.getParameter("FechaIni");
		String fechaFin			= request.getParameter("FechaFin")==null?"":request.getParameter("FechaFin");
		
		EmpPeriodo empPeriodo 	= new EmpPeriodo();
		
		switch(accion){
		case "1": //Grabar
			empPeriodo.setPeriodoId(periodoId);
			empPeriodo.setPeriodoNombre(periodoNombre);
			empPeriodo.setFechaIni(fechaIni);
			empPeriodo.setFechaFin(fechaFin);
			empPeriodo.setEstado("A");
			
				if(!empPeriodoDao.existeReg(periodoId)){
					if(empPeriodoDao.insertReg(empPeriodo)){
						mensaje 		= "Guardado";
						colorMensaje	= "success";
					}else{
						mensaje 		= "No guardo";
						colorMensaje	= "danger";
					}
				}
			break;
		case "2" : //Modificar
			empPeriodo.setPeriodoId(periodoId);
			empPeriodo.setPeriodoNombre(periodoNombre);
			empPeriodo.setFechaIni(fechaIni);
			empPeriodo.setFechaFin(fechaFin);
			empPeriodo.setEstado("A");		
			
			if(empPeriodoDao.existeReg(periodoId)){
					if(empPeriodoDao.updateReg(empPeriodo)){
						mensaje 		= "Modificado";
						colorMensaje	= "success";
					}else{
						mensaje 		= "No modifico";
						colorMensaje	= "danger";
					}
				}
			break;
		}
		
		modelo.addAttribute("empPeriodo", empPeriodo);
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("colorMensaje", colorMensaje);
		
		return "horas/periodo/agregar";
	}
	
	@RequestMapping("/horas/periodo/eliminar")
	public String horasPeriodoEliminar(HttpServletRequest request, Model modelo) {	
		
		String periodoId	= request.getParameter("PeriodoId")==null?"":request.getParameter("PeriodoId");
		
		empPeriodoDao.deleteReg(periodoId); 

		List<aca.emp.spring.EmpPeriodo> lista	= empPeriodoDao.listAll("");
		
		modelo.addAttribute("lista", lista);
		
		return "horas/periodo/periodo";
	}
	
}