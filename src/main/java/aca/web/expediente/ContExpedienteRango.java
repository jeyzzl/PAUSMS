package aca.web.expediente;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.maestro.MaestroRango;
import aca.maestro.MaestroRangoDao;

@Controller
public class ContExpedienteRango {	
	
	@Autowired
	MaestroRangoDao maestroRangoDao;
	
	@RequestMapping("/expediente/rango/listado")
	public String expedienteEmpleadoListado(HttpServletRequest request, Model modelo){	
				
		String year 		= request.getParameter("Year")==null?"0":request.getParameter("Year");		
		HttpSession sesion	= ((HttpServletRequest)request).getSession();
		if (sesion!=null){					
		}
		
		List<String> lisYears 					= maestroRangoDao.lisYears(" ORDER BY YEAR DESC");
		if (year.equals("0") && lisYears.size() >= 1) year = lisYears.get(0);
		
		List<MaestroRango> lisRangos			= maestroRangoDao.lisPorYear(year, " ORDER BY AREA, NOMBRE");
		
		modelo.addAttribute("year",year);
		modelo.addAttribute("lisYears",lisYears);
		modelo.addAttribute("lisRangos",lisRangos);	
		
		return "expediente/rango/listado";
	}
	
	@RequestMapping("/expediente/rango/pdf")
	public String expedienteEmpleadoPdf(HttpServletRequest request){	
				
		String year 			= request.getParameter("Year")==null?"0":request.getParameter("Year");
		String codigoEmpleado 	= request.getParameter("CodigoEmpleado")==null?"0":request.getParameter("CodigoEmpleado");
		HttpSession sesion	= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			sesion.setAttribute("codigoEmpleado", codigoEmpleado); 
		}		
		return "redirect:/portales/maestro/rangoPdf?Year="+year;
	}
	
}