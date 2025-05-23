package aca.web.inscritos;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.catalogo.spring.CatModalidad;
import aca.catalogo.spring.CatModalidadDao;
import aca.vista.spring.EstadisticaDao;

@Controller
public class ContInscritosLocaliza {	
	
	@Autowired	
	private EstadisticaDao estadisticaDao;
	
	@Autowired	
	private CatModalidadDao catModalidadDao;	
	
	@RequestMapping("/inscritos/localiza/inscritos")
	public String inscritosLocalizaInscritos(HttpServletRequest request, Model modelo){
		String fechaIni 	= request.getParameter("FechaIni")==null?"0":request.getParameter("FechaIni");
		String fechaFin		= request.getParameter("FechaFin")==null?"0":request.getParameter("FechaFin");		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	if (fechaIni.equals("0")) fechaIni = (String) sesion.getAttribute("fechaIni");
        	if (fechaFin.equals("0")) fechaFin = (String) sesion.getAttribute("fechaFin");        	
        }
        
        
		List<String> lisCargas 						= estadisticaDao.listCargasEntreFechas(fechaIni, fechaFin);
		List<CatModalidad> lisModalidades			= catModalidadDao.getListAll("ORDER BY MODALIDAD_ID");
		HashMap<String,String> mapaAlumnos 			= estadisticaDao.mapAlumnosPorCarga(fechaIni, fechaFin);		
		java.util.Map<String,String> mapaAlumMod	= estadisticaDao.mapAlumnosModalidadPorCarga(fechaIni, fechaFin);
		
		
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisModalidades", lisModalidades);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);		
		modelo.addAttribute("mapaAlumMod", mapaAlumMod);

		return "inscritos/localiza/inscritos";
	}	
}