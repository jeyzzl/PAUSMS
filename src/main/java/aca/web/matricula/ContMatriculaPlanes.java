package aca.web.matricula;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumPlanDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaAlumno;
import aca.carga.spring.CargaAlumnoDao;
import aca.carga.spring.CargaBloque;
import aca.carga.spring.CargaBloqueDao;
import aca.carga.spring.CargaDao;
import aca.carga.spring.CargaPlanExterno;
import aca.carga.spring.CargaPlanExternoDao;
import aca.catalogo.spring.CatPeriodo;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.alumno.spring.AlumPlan;

@Controller
public class ContMatriculaPlanes {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	
	@Autowired
	aca.catalogo.spring.CatPeriodoDao catPeriodoDao;
	
	@Autowired
	CargaDao cargaDao;
	
	@Autowired
	MapaPlanDao mapaPlanDao;
	
	@Autowired
	CargaPlanExternoDao cargaPlanExternoDao;
	
	
	@RequestMapping("/matricula/planes/planes")
	public String matriculaCargaAlumno(HttpServletRequest request, Model modelo){
		
		String periodoId	= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String cargaId		= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String filtro		= request.getParameter("Filtro")==null?"T":request.getParameter("Filtro");
		
		List<CatPeriodo> lisPeriodos = catPeriodoDao.getListAll(" ORDER BY PERIODO_ID DESC");
		if(periodoId.equals("0") && lisPeriodos.size() > 0 ) {
			periodoId = lisPeriodos.get(0).getPeriodoId();
		}
		
		List<Carga> lisCargas = cargaDao.getListPeriodo(periodoId," AND ESTADO = '1' ORDER BY PERIODO,CARGA_ID");
		if (cargaId.equals("0") && lisCargas.size()>0) {
			cargaId = lisCargas.get(0).getCargaId();
    	}
		
		List<MapaPlan> lisPlanes = new ArrayList<MapaPlan>();
		
		if(filtro.equals("T")){
			lisPlanes = mapaPlanDao.getListAll("");
		}else if(filtro.equals("R")){
			lisPlanes = mapaPlanDao.getListRegistrados("");
		}
		
		HashMap<String, String> mapaPlanesExternos	= cargaPlanExternoDao.mapaCargaPlanExterno(cargaId);
		
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("mapaPlanesExternos", mapaPlanesExternos);
		modelo.addAttribute("PeriodoId", periodoId);
		modelo.addAttribute("CargaId", cargaId);
		modelo.addAttribute("Filtro", filtro);
		
		return "matricula/planes/planes";
	}
	
	@RequestMapping("/matricula/planes/guardar")
	public String matriculaPlanGuardar(HttpServletRequest request){
		
		String periodoId	= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String cargaId		= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String planId		= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String mensaje 		= "0";
		
		CargaPlanExterno externo = new CargaPlanExterno();
		
		if(cargaPlanExternoDao.existeReg(cargaId, planId)){
			mensaje = "1";
		}else{
			externo.setCargaId(cargaId);
			externo.setPlanId(planId);
			if(cargaPlanExternoDao.insertReg(externo)) {
				mensaje = "2";
			}
		}
		
		return "redirect:/matricula/planes/planes?PeriodoId="+periodoId+"&CargaId="+cargaId+"&Mensaje="+mensaje;
	}

	@RequestMapping("/matricula/planes/borrarPlanes")
	public String matriculaPlanBorrarCarga(HttpServletRequest request){
		
		String periodoId	= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String cargaId		= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String planId		= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		
		if(cargaPlanExternoDao.existeReg(cargaId, planId)){
			cargaPlanExternoDao.deleteReg(cargaId,planId);
		}
		
		return "redirect:/matricula/planes/planes?PeriodoId="+periodoId+"&CargaId="+cargaId;
	}
	
}