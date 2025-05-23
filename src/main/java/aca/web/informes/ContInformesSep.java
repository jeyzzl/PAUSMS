package aca.web.informes;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumEstadoDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaAlumno;
import aca.carga.spring.CargaAlumnoDao;
import aca.carga.spring.CargaDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatEstado;
import aca.catalogo.spring.CatEstadoDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatModalidad;
import aca.catalogo.spring.CatModalidadDao;
import aca.catalogo.spring.CatPais;
import aca.catalogo.spring.CatPaisDao;
import aca.catalogo.spring.CatPeriodo;
import aca.catalogo.spring.CatPeriodoDao;
import aca.plan.spring.MapaCursoDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.sep.spring.SepAlumno;
import aca.sep.spring.SepAlumnoDao;
import aca.vista.spring.AlumnoCurso;
import aca.vista.spring.AlumnoCursoDao;
import aca.vista.spring.Estadistica;
import aca.vista.spring.EstadisticaDao;

@Controller
public class ContInformesSep {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	aca.ssoc.spring.SsocAsignacionDao ssocAsignacionDao;

	@Autowired
	aca.ssoc.spring.SsocSectorDao ssocScetornDao;
	
	@Autowired
	AlumPlanDao alumPlanDao;
	
	@Autowired
	EstadisticaDao estadisticaDao;
	
	@Autowired
	CatPaisDao catPaisDao;
	
	@Autowired
	CatEstadoDao catEstadoDao;
	
	@Autowired
	CatModalidadDao catModalidadDao;
	
	@Autowired	
	CatFacultadDao catFacultadDao;
	
	@Autowired	
	CatCarreraDao catCarreraDao;
	
	@Autowired	
	MapaPlanDao mapaPlanDao;
	
	@Autowired	
	AlumEstadoDao alumEstadoDao;
	
	@Autowired	
	AlumPersonalDao alumPersonalDao;
	
	@Autowired	
	CargaAlumnoDao cargaAlumnoDao;
	
	@Autowired	
	CargaDao cargaDao;
	
	@Autowired	
	CatPeriodoDao catPeriodoDao;

	@Autowired	
	MapaCursoDao mapaCursoDao;
	
	@Autowired	
	AlumnoCursoDao alumnoCursoDao;
	
	@Autowired	
	SepAlumnoDao sepAlumnoDao;
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/informes/sep/accion")
	public String informesSepAccion(HttpServletRequest request, Model modelo){
		enviarConEnoc(request,"Error-aca.ControllerInformes|informesSepAccion");
		
		HashMap<String, String> mapaSector 			= ssocAsignacionDao.mapaSector();
		HashMap<String, String> mapaSectorNombre 	= ssocScetornDao.mapaSectorNombre();
		
		modelo.addAttribute("mapaSector", mapaSector);
		modelo.addAttribute("mapaSectorNombre", mapaSectorNombre);
		
		return "informes/sep/accion";
	}
	
	@RequestMapping("/informes/sep/reporte")
	public String informesSepReporte(HttpServletRequest request, Model modelo){
		
		String periodo 			= request.getParameter("Periodo")==null?"0":request.getParameter("Periodo");
		String periodoPas 		= request.getParameter("PeriodoPas")==null?"0":request.getParameter("PeriodoPas");
		String modalidades		= "";
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){       	
        	modalidades			= (String)sesion.getAttribute("modalidades")==null?"'1'":sesion.getAttribute("modalidades").toString();
        }
        
        String lisModo[] 		= modalidades.replace("'", "").split(",");
        
		// Lista de periodos
		List<CatPeriodo> lisPeriodos 	= catPeriodoDao.getListAll("ORDER BY PERIODO_ID ASC");		
		if (periodo.equals("0")){
			periodo 		= lisPeriodos.get(lisPeriodos.size()-1).getPeriodoId();
		}
		if (periodoPas.equals("0")){
			periodoPas 	= lisPeriodos.get(lisPeriodos.size()-2).getPeriodoId();
		}
		
		// Lista de cargas 
		List<Carga> lisCargas 							= cargaDao.getListCargaPeriodo(periodo, "ORDER BY 1");		 
		List<Carga> lisCargasPas  						= cargaDao.getListCargaPeriodo(periodoPas, "ORDER BY 1");
		List<CatModalidad> lisModalidades 				= catModalidadDao.getListAll( " ORDER BY MODALIDAD_ID");
		HashMap<String, CatModalidad> mapaModalidades	= catModalidadDao.getMapAll("");
		if(modalidades.equals("")){ modalidades			= "' '"; }
		
		modelo.addAttribute("periodo", periodo);
		modelo.addAttribute("periodoPas", periodoPas);
		modelo.addAttribute("modalidades", modalidades);		
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisCargasPas", lisCargasPas);
		modelo.addAttribute("lisModalidades", lisModalidades);
		modelo.addAttribute("lisModo", lisModo);
		modelo.addAttribute("mapaModalidades", mapaModalidades);
		
		return "informes/sep/reporte";
	}
	
	@RequestMapping("/informes/sep/actual")
	public String informesSepActual(HttpServletRequest request, Model modelo){
		String cargas 					= request.getParameter("alumnos");
		boolean updateCiclo 			= false;
		boolean updateCicloPostgrado 	= false;
		String condicion 				= " AND SUBSTR(PLAN_ID,7,8) != 'SR' AND ( PLAN_ID != 'TEOL2000') AND CARRERA_ID NOT IN ('10209','10210')"; 
		
		String modalidades		= "";
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){       	
        	modalidades			= (String)sesion.getAttribute("modalidades")==null?"'1'":sesion.getAttribute("modalidades").toString();
        }
        
		if ( alumPlanDao.actualizaCiclo(cargas) ){
			updateCiclo = true;
		}		
		if ( alumPlanDao.actualizaCicloPostgrado(cargas) ){
			updateCicloPostgrado = true;
		}		
		if (updateCiclo || updateCicloPostgrado){
			alumPlanDao.actualizaGrado(cargas);			
		}
		
		List<Estadistica> lisEstadistica = estadisticaDao.lisInscritosSE(cargas, modalidades, condicion+" AND CODIGO_PERSONAL||CARGA_ID||PLAN_ID NOT IN (SELECT CODIGO_PERSONAL||CARGA_ID||PLAN_ID FROM ENOC.ALUM_SEQUITA) "
		+"ORDER BY FACULTAD_ID, CARRERA_ID, ALUMNO_CICLO(CODIGO_PERSONAL,PLAN_ID),APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE");
		
		HashMap<String, AlumPersonal> mapaAlumPersonal = alumPersonalDao.mapAlumnosEnCargasyModalidades(cargas, modalidades);
		
		List<AlumnoCurso> listaAlumnos = alumnoCursoDao.getListAll(" WHERE CARGA_ID IN("+cargas+") ORDER BY CODIGO_PERSONAL");
		
		HashMap<String,String> mapaMasDeUnPlan = new HashMap<String,String>();
		
		String matriculaTmp = listaAlumnos.get(0).getCodigoPersonal();
		String planTmp		= listaAlumnos.get(0).getPlanId();
		int cont = 0;
		
		for(AlumnoCurso alumno : listaAlumnos) {
			if(cont < listaAlumnos.size()) {
				matriculaTmp = listaAlumnos.get(cont).getCodigoPersonal();
				planTmp		= listaAlumnos.get(cont).getPlanId();
				cont++;
			}
			
			if(matriculaTmp.equals(alumno.getCodigoPersonal())) {
				if(!planTmp.equals(alumno.getPlanId())) {
					mapaMasDeUnPlan.put(alumno.getCodigoPersonal(), alumno.getCodigoPersonal());
				}
			}
		}
		
		HashMap<String,CatModalidad> mapaModalidades	= catModalidadDao.getMapAll("");
		HashMap<String,CatPais> mapaPaises				= catPaisDao.getMapAll();
		HashMap<String,CatEstado> mapaEstados			= catEstadoDao.getMapAll();
		HashMap<String,CatFacultad> mapaFacultades		= catFacultadDao.getMapFacultad("");
		HashMap<String,CatCarrera> mapaCarreras			= catCarreraDao.getMapAll("");
		HashMap<String,MapaPlan> mapaPlanes				= mapaPlanDao.mapPlanes("'A','V'");		
		HashMap<String, String> mapaAlumPlanes			= alumPlanDao.mapAlumCicloSepCarga(cargas);		
		HashMap<String, AlumPlan> mapaGradosyCiclos		= alumPlanDao.mapCicloActual(cargas);
		HashMap<String,String> mapaEdades				= alumPersonalDao.getMapEdad(cargas);
		HashMap<String,CargaAlumno> mapaCargasAlumno	= cargaAlumnoDao.mapaCargasAlumno(cargas);
		HashMap<String, String> mapaCreditosPlan	 	= mapaCursoDao.mapaCreditosPlan();
		HashMap<String, String> mapaCreditosAcreditados	= alumPlanDao.mapaCreditosAcreditados(cargas);
		HashMap<String,SepAlumno> mapaUltimoPlan 		= sepAlumnoDao.mapaAlumnosCompletosUltimoPlan(cargas);	
		
		modelo.addAttribute("lisEstadistica", lisEstadistica);		
		modelo.addAttribute("mapaModalidades", mapaModalidades);
		modelo.addAttribute("mapaPaises", mapaPaises);
		modelo.addAttribute("mapaEstados", mapaEstados);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		modelo.addAttribute("mapaAlumPlanes", mapaAlumPlanes);
		modelo.addAttribute("mapaGradosyCiclos", mapaGradosyCiclos);
		modelo.addAttribute("mapaEdades", mapaEdades);
		modelo.addAttribute("mapaCargasAlumno", mapaCargasAlumno);
		modelo.addAttribute("mapaCreditosAcreditados", mapaCreditosAcreditados);
		modelo.addAttribute("mapaCreditosPlan", mapaCreditosPlan);
		modelo.addAttribute("mapaUltimoPlan", mapaUltimoPlan);
		modelo.addAttribute("mapaMasDeUnPlan", mapaMasDeUnPlan);
		modelo.addAttribute("mapaAlumPersonal", mapaAlumPersonal);
		
		return "informes/sep/actual";
	}
	
	@RequestMapping("/informes/sep/historico")
	public String informesSepHistorico(HttpServletRequest request, Model modelo){
		String cargas 					= request.getParameter("alumnos");
		boolean updateCiclo 			= false;
		boolean updateCicloPostgrado 	= false;
		String condicion 				= " AND SUBSTR(PLAN_ID,7,8) != 'SR' AND ( PLAN_ID != 'TEOL2000') AND CARRERA_ID NOT IN ('10209','10210')"; 
		
		if ( alumPlanDao.actualizaCiclo(cargas) ){
			updateCiclo = true;
		}		
		if ( alumPlanDao.actualizaCicloPostgrado(cargas) ){
			updateCicloPostgrado = true;
		}		
		if (updateCiclo || updateCicloPostgrado){
			alumPlanDao.actualizaGrado(cargas);			
		}
		
		List<Estadistica> lisEstadistica = estadisticaDao.lisInscritosSE(cargas, condicion+" AND CODIGO_PERSONAL||CARGA_ID||PLAN_ID NOT IN (SELECT CODIGO_PERSONAL||CARGA_ID||PLAN_ID FROM ENOC.ALUM_SEQUITA) ORDER BY FACULTAD_ID, CARRERA_ID, ALUMNO_CICLO(CODIGO_PERSONAL,PLAN_ID),APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE");
		
		HashMap<String,CatModalidad> mapaModalidades	= catModalidadDao.getMapAll("");
		HashMap<String,CatPais> mapaPaises				= catPaisDao.getMapAll();
		HashMap<String,CatEstado> mapaEstados			= catEstadoDao.getMapAll();
		HashMap<String,CatFacultad> mapaFacultades		= catFacultadDao.getMapFacultad("");
		HashMap<String,CatCarrera> mapaCarreras			= catCarreraDao.getMapAll("");
		HashMap<String,MapaPlan> mapaPlanes				= mapaPlanDao.mapPlanes("'A','V'");		
		HashMap<String, String> mapaAlumPlanes			= alumPlanDao.mapAlumCicloSepCarga(cargas);		
		HashMap<String, String> mapaGradosyCiclos		= alumEstadoDao.mapGradoyCiclo(cargas, "I");
		HashMap<String,String> mapaEdades				= alumPersonalDao.getMapEdad(cargas);
		HashMap<String,CargaAlumno> mapaCargasAlumno	= cargaAlumnoDao.mapaCargasAlumno(cargas);
		
		modelo.addAttribute("lisEstadistica", lisEstadistica);
		
		modelo.addAttribute("mapaModalidades", mapaModalidades);
		modelo.addAttribute("mapaPaises", mapaPaises);
		modelo.addAttribute("mapaEstados", mapaEstados);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		modelo.addAttribute("mapaAlumPlanes", mapaAlumPlanes);
		modelo.addAttribute("mapaGradosyCiclos", mapaGradosyCiclos);
		modelo.addAttribute("mapaEdades", mapaEdades);
		modelo.addAttribute("mapaCargasAlumno", mapaCargasAlumno);
		
		return "informes/sep/historico";
	}
	
	@RequestMapping("/informes/sep/modalidades")
	public String inscritosInscritosFacModalidades(HttpServletRequest request, Model modelo){
		
		String codigoPersonal	= "";
		String cargas			= "";
		String modalidades		= "";
		String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal 	= (String) sesion.getAttribute("codigoPersonal");
			cargas			= (String) sesion.getAttribute("cargas") == null ? cargaDao.getCargasActivas(aca.util.Fecha.getHoy()) : sesion.getAttribute("cargas").toString();
			modalidades		= (String)sesion.getAttribute("modalidades") == null ? "" : sesion.getAttribute("modalidades").toString();
		}
		
		HashMap<String, String> mapaModalidadesEnCargas 	= estadisticaDao.mapaModalidadesEnCargas(cargas);
		List<CatModalidad> lisModalidad	= catModalidadDao.getListAll("ORDER BY MODALIDAD_ID");
		
		modelo.addAttribute("codigoPersonal", codigoPersonal);
		modelo.addAttribute("cargas", cargas);
		modelo.addAttribute("modalidades", modalidades);
		modelo.addAttribute("accion", accion);
		modelo.addAttribute("lisModalidad", lisModalidad);
		modelo.addAttribute("mapaModalidadesEnCargas", mapaModalidadesEnCargas);
		
		return "informes/sep/modalidades";
	}
	
}