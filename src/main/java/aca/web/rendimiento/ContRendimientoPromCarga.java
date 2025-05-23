package aca.web.rendimiento;

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

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumAcademicoDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatTipoCurso;
import aca.catalogo.spring.CatTipoCursoDao;
import aca.kardex.spring.KrdxAlumnoEvalDao;
import aca.vista.spring.AlumnoCurso;
import aca.vista.spring.AlumnoCursoDao;
import aca.vista.spring.AlumnoDao;
import aca.vista.spring.Estadistica;
import aca.vista.spring.EstadisticaDao;

@Controller
public class ContRendimientoPromCarga {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	CargaDao cargaDao;	
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	EstadisticaDao estadisticaDao;
	
	@Autowired
	CatFacultadDao catFacultadDao;
	
	@Autowired
	AlumnoCursoDao alumnoCursoDao;
	
	@Autowired
	AccesoDao accesoDao;
	
	@Autowired
	AlumnoDao alumnoDao;
	
	@Autowired
	CatTipoCursoDao catTipoCursoDao;
	
	@Autowired
	KrdxAlumnoEvalDao krdxAlumnoEvalDao;
	
	
	
	@RequestMapping("/rendimiento/prom_carga/promedio")
	public String rendimientoPromCargaPromedio(HttpServletRequest request, Model modelo){
		 	
		String codigoPersonal	= "0";
		String cargaId 			= request.getParameter("cargaId")==null?"0":request.getParameter("cargaId");
		Acceso acceso 			= new Acceso();
		boolean tieneAcceso		= false;
		HttpSession sesion		= ((HttpServletRequest)request).getSession();		
        if (sesion!=null){
        	if (cargaId.equals("0")) {
        		cargaId = (String) sesion.getAttribute("cargaId");
        	}else {
        		sesion.setAttribute("cargaId",cargaId);
        	}
        	codigoPersonal 	= (String) sesion.getAttribute("codigoPersonal");
        	if (accesoDao.existeReg(codigoPersonal)) {
        		tieneAcceso 	= true;
        		acceso 			= accesoDao.mapeaRegId(codigoPersonal);
        	}        	
        }
        
		int numeroOrden			= request.getParameter("Ordenar")==null?0:Integer.parseInt(request.getParameter("Ordenar"));
		String orden			= "";
		switch(numeroOrden){
			case 1 : orden = ", CODIGO_PERSONAL";
				break;
			case 2 : orden = ", APELLIDO_PATERNO";
				break;
			case 3 : orden = ", NOMBRE";
				break;
			case 4 : orden = ", RESIDENCIA_ID";
				break;
			case 5 : orden = ", SEXO";
				break;
		}
		
		List<Carga> lisCargas 			= cargaDao.getListCargasPasadas(" ORDER BY ORDEN DESC,NOMBRE_CARGA");
		boolean existe = false;
		for (Carga carga : lisCargas) {
			if (carga.getCargaId().equals(cargaId)) existe = true;
		}
		if (existe==false && lisCargas.size()>0) {
			cargaId = lisCargas.get(0).getCargaId();			
		}
		
		List<Estadistica>lisInscritos 	= estadisticaDao.lisInscritosEnCarga(cargaId," ORDER BY FACULTAD_ID, CARRERA_ID "+orden);
		
		HashMap<String,CatFacultad> mapaFacultades	= catFacultadDao.getMapFacultad("");	
		HashMap<String,CatCarrera> mapaCarreras		= catCarreraDao.getMapAll("");		
		HashMap<String,String> mapaPromedios		= alumnoCursoDao.mapAlumPromCarga(cargaId);
		
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("tieneAcceso", tieneAcceso);
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisInscritos", lisInscritos);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaPromedios", mapaPromedios);
		
		return "rendimiento/prom_carga/promedio";
	}
	
	@RequestMapping("/rendimiento/prom_carga/promedioprint")
	public String rendimientoPromCargaPromedioPrint(HttpServletRequest request, Model modelo){
		 	
		String codigoPersonal	= "0";
		String cargaId 			= request.getParameter("cargaId")==null?"0":request.getParameter("cargaId");
		String cargaNombre		= cargaDao.getNombreCarga(cargaId);
		Acceso acceso 			= new Acceso();
		boolean tieneAcceso		= false;
		HttpSession sesion		= ((HttpServletRequest)request).getSession();		
        if (sesion!=null){        	
        	codigoPersonal 	= (String) sesion.getAttribute("codigoPersonal");
        	if (accesoDao.existeReg(codigoPersonal)) {
        		tieneAcceso 	= true;
        		acceso 			= accesoDao.mapeaRegId(codigoPersonal);
        	}        	
        }
        
		int numeroOrden			= request.getParameter("Ordenar")==null?0:Integer.parseInt(request.getParameter("Ordenar"));
		String orden			= "";
		switch(numeroOrden){
			case 1 : orden = ", CODIGO_PERSONAL";
				break;
			case 2 : orden = ", APELLIDO_PATERNO";
				break;
			case 3 : orden = ", NOMBRE";
				break;
			case 4 : orden = ", RESIDENCIA_ID";
				break;
			case 5 : orden = ", SEXO";
				break;
		}		
				 
		List<Estadistica>lisInscritos 	= estadisticaDao.lisInscritosEnCarga(cargaId," ORDER BY FACULTAD_ID, CARRERA_ID "+orden);
		
		HashMap<String,CatFacultad> mapaFacultades	= catFacultadDao.getMapFacultad("");	
		HashMap<String,CatCarrera> mapaCarreras		= catCarreraDao.getMapAll("");		
		HashMap<String,String> mapaPromedios		= alumnoCursoDao.mapAlumPromCarga(cargaId);
		
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("cargaNombre", cargaNombre);
		modelo.addAttribute("tieneAcceso", tieneAcceso);
		modelo.addAttribute("acceso", acceso);		
		modelo.addAttribute("lisInscritos", lisInscritos);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaPromedios", mapaPromedios);
		
		return "rendimiento/prom_carga/promedioprint";
	}
	
	@RequestMapping("/rendimiento/prom_carga/promedio_alumno")
	public String rendimientoPromCargaPromedioAlumno(HttpServletRequest request, Model modelo){
		 	
		String matricula			= request.getParameter("matricula")==null?"0":request.getParameter("matricula");
		String carga				= request.getParameter("carga")==null?"0":request.getParameter("carga");
		String promedio 			= request.getParameter("promedio")==null?"0":request.getParameter("promedio");
		String nombre 				= alumnoDao.getNombre(matricula, "NOMBRE");
		
		List<AlumnoCurso> lisCursos = alumnoCursoDao.getListAlumnoCarga(matricula, carga, "'3','M'"," ORDER BY CURSO_CARGA_ID, NOMBRE_CURSO");
		HashMap<String,CatTipoCurso> mapaTipos = catTipoCursoDao.getMapAll("");
		
		modelo.addAttribute("matricula", matricula);
		modelo.addAttribute("carga", carga);
		modelo.addAttribute("promedio", promedio);
		modelo.addAttribute("nombre", nombre);
		modelo.addAttribute("lisCursos", lisCursos);
		modelo.addAttribute("mapaTipos", mapaTipos);
		
		return "rendimiento/prom_carga/promedio_alumno";
	}
}