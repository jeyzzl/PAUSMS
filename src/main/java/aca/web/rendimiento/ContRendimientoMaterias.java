package aca.web.rendimiento;

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

import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;
import aca.carga.spring.CargaGrupoDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatPeriodo;
import aca.catalogo.spring.CatPeriodoDao;
import aca.vista.spring.AlumnoCursoDao;
import aca.vista.spring.CargaAcademicaDao;
import aca.vista.spring.EstadisticaDao;

@Controller
public class ContRendimientoMaterias {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	
	

	@Autowired
	CargaDao cargaDao;	
	
	@Autowired
	CatPeriodoDao catPeriodoDao;
	
	@Autowired
	CatFacultadDao catFacultadDao;
	
	@Autowired
	CargaGrupoDao cargaGrupoDao;
	
	@Autowired
	AlumnoCursoDao alumnoCursoDao;
	
	@RequestMapping("/rendimiento/materias/facultades")
	public String rendimientoMateriasFacultades(HttpServletRequest request, Model modelo){		
		
		String periodoId 			= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String cargaId 				= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");				
		boolean existeCarga			= false;
		
		List<CatPeriodo> lisPeriodos 				= catPeriodoDao.getListAll("ORDER BY PERIODO_ID DESC");
		if (periodoId.equals("0") && lisPeriodos.size() >= 1){
			periodoId = lisPeriodos.get(0).getPeriodoId();
		}
		
		List<Carga> lisCargas 						= cargaDao.getListAll(" WHERE PERIODO = '"+periodoId+"' AND ESTADO = '1' ORDER BY CARGA_ID");
		for (Carga carga : lisCargas) {
			if (carga.getCargaId().equals(cargaId)) existeCarga = true;
		}
		if ( (cargaId.equals("0") || existeCarga == false) && lisCargas.size()>= 1) {
			cargaId = lisCargas.get(0).getCargaId();
		}
		
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("lisCargas", lisCargas);
		
		return "rendimiento/materias/facultades";
	}
	
}