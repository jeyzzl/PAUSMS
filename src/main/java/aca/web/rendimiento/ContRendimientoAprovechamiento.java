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

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;
import aca.carga.spring.CargaGrupoDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatPeriodo;
import aca.catalogo.spring.CatPeriodoDao;
import aca.catalogo.spring.CatTipoCal;
import aca.catalogo.spring.CatTipoCalDao;
import aca.kardex.spring.KrdxCursoActDao;
import aca.plan.spring.MapaCurso;
import aca.plan.spring.MapaCursoDao;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.AlumnoCurso;
import aca.vista.spring.AlumnoCursoDao;
import aca.vista.spring.CargaAcademica;
import aca.vista.spring.CargaAcademicaDao;
import aca.vista.spring.Estadistica;
import aca.vista.spring.EstadisticaDao;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContRendimientoAprovechamiento {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	

	@Autowired
	CargaDao cargaDao;	
	
	@Autowired
	CatFacultadDao catFacultadDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	CatPeriodoDao catPeriodoDao;
	
	@Autowired
	EstadisticaDao estadisticaDao;
	
	@Autowired
	AlumnoCursoDao alumnoCursoDao;
	
	@Autowired
	AccesoDao accesoDao;
	
	@Autowired
	CargaGrupoDao cargaGrupoDao;
	
	@Autowired
	MaestrosDao maestrosDao;

	@Autowired
	CargaAcademicaDao cargaAcademicaDao;
	
	@Autowired
	CatTipoCalDao catTipoCalDao;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;
	
	@Autowired
	MapaCursoDao mapaCursoDao;
	
	@Autowired
	MapaPlanDao mapaPlanDao;
	
	@Autowired
	KrdxCursoActDao krdxCursoActDao;
	
	
	@RequestMapping("/rendimiento/aprovechamiento/carga")
	public String rendimientoReprobadoListado(HttpServletRequest request, Model modelo){
		
		String periodoId 			= request.getParameter("periodoId")==null?"0":request.getParameter("periodoId");
		String cargaId 				= request.getParameter("cargaId")==null?"0":request.getParameter("cargaId");
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){        	
        	if (periodoId.equals("0")) periodoId 		= (String)sesion.getAttribute("periodo");
        	if (cargaId.equals("0")) cargaId			= (String)sesion.getAttribute("cargaId");
        }
		
    	List<CatPeriodo> lisPeriodos 				= catPeriodoDao.getListAll(" ORDER BY PERIODO_ID DESC");
    	List<Carga> lisCargas						= cargaDao.getListAll(" WHERE PERIODO = '"+periodoId+"' AND ESTADO = '1' ORDER BY CARGA_ID DESC");
    	List<CargaAcademica> lisMaterias			= cargaAcademicaDao.getListaCarga(cargaId, "Order by 4, 1");
    	
    	HashMap<String,String> mapaTotal			= krdxCursoActDao.mapaTotAlumnos(cargaId, "'I','1','2','3','4','5','6'");
    	HashMap<String,String> mapaPorTipos			= krdxCursoActDao.mapaTotAlumnos(cargaId);
    	
    	modelo.addAttribute("lisPeriodos", lisPeriodos);
    	modelo.addAttribute("lisCargas", lisCargas);
    	modelo.addAttribute("lisMaterias", lisMaterias);
    	modelo.addAttribute("mapaTotal", mapaTotal);
    	modelo.addAttribute("mapaPorTipos", mapaPorTipos);
    	
		return "rendimiento/aprovechamiento/carga";
	}	
}