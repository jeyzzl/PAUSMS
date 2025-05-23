package aca.web.cargagrupo;

import java.util.HashMap;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.acceso.spring.Acceso;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaAlumno;
import aca.carga.spring.CargaAlumnoDao;
import aca.carga.spring.CargaDao;
import aca.carga.spring.CargaGrupoDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatModalidad;
import aca.catalogo.spring.CatModalidadDao;

import aca.catalogo.spring.CatPeriodoDao;
import aca.catalogo.spring.CatTipoCalDao;
import aca.kardex.spring.KrdxCursoActDao;
import aca.vista.spring.AlumnoCurso;
import aca.vista.spring.AlumnoCursoDao;
import aca.vista.spring.CargaAcademica;
import aca.vista.spring.CargaAcademicaDao;
import aca.vista.spring.UsuariosDao;

@Controller
public class ContCargaGrupoMaterias {
	
	@Autowired
	CatPeriodoDao catPeriodoDao;
	
	@Autowired
	CargaDao cargaDao;		
	
	@Autowired
	AlumPersonalDao alumPersonalDao;
	
	@Autowired
	CargaAcademicaDao cargaAcademicaDao;
	
	@Autowired
	aca.acceso.spring.AccesoDao accesoDao;
	
	@Autowired
	CatFacultadDao catFacultadDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	CatModalidadDao catModalidadDao;
	
	@Autowired
	KrdxCursoActDao krdxCursoActDao;
	
	@Autowired
	AlumnoCursoDao alumnoCursoDao;	
	
	@Autowired
	UsuariosDao usuariosDao;
	
	@Autowired
	CatTipoCalDao catTipoCalDao;
	
	@Autowired
	CargaAlumnoDao cargaAlumnoDao;
	
	@Autowired
	CargaGrupoDao cargaGrupoDao;
	
	
	@RequestMapping("/carga_grupo/materias/activas")
	public String cargaGrupoMateriasActivas(HttpServletRequest request, Model modelo) {
		
		String cargaId 			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");		 
		String origen 			= request.getParameter("Origen")==null?"T":request.getParameter("Origen");
		String cargaSesion 		= "0";
		
		if (origen.equals("T")) 
			origen = "'O','E'";
		else
			origen = "'"+origen+"'";
		
		String codigoUsuario	= "0";		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion!=null) {
			codigoUsuario 		= (String) sesion.getAttribute("codigoPersonal");
			cargaSesion			= (String) sesion.getAttribute("cargaId");
		}
		
		Acceso acceso	= new Acceso();
		if (accesoDao.existeReg(codigoUsuario) ) {
			acceso = accesoDao.mapeaRegId(codigoUsuario);
		}
		
		List<Carga> lisCargas 							= cargaDao.getListAll("ORDER BY ORDEN DESC");
		boolean existe = false;
		for (Carga carga : lisCargas){
			if (carga.getCargaId().equals(cargaSesion)) existe = true;
		}
		if (existe && cargaId.equals("0")) {
			cargaId = cargaSesion;
		}else if (cargaId.equals("0") && lisCargas.size() >= 1) {
			cargaId = lisCargas.get(0).getCargaId();
		}
		if (sesion!=null && !cargaId.equals("0")) {
			sesion.setAttribute("cargaId", cargaId);
		}
		List<CargaAcademica> lisMaterias				= cargaAcademicaDao.lisPorCargayOrigen(cargaId, origen," ORDER BY FACULTAD(CARRERA_ID), CARRERA_ID, NOMBRE_CURSO");
		HashMap<String, CatFacultad> mapaFacultades		= catFacultadDao.getMapFacultad("");
		HashMap<String, CatCarrera> mapaCarreras		= catCarreraDao.getMapAll("");
		HashMap<String, CatModalidad> mapaModalidades	= catModalidadDao.getMapAll("");
		HashMap<String, String> mapaTotalAlumnos		= krdxCursoActDao.mapaTotalAlumnos(cargaId, "'I','1','2','4','5'");
		
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("origen", origen);
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisMaterias", lisMaterias);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaModalidades", mapaModalidades);
		modelo.addAttribute("mapaTotalAlumnos", mapaTotalAlumnos);
		
		return "carga_grupo/materias/activas";
	}	
	
	@RequestMapping("/carga_grupo/materias/activasreporte")
	public String cargaGrupoMateriasActivasReporte(HttpServletRequest request, Model modelo) {
		
		String codigoUsuario	= "0";
		String cargaId 			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String origen 			= request.getParameter("Origen")==null?"O":request.getParameter("Origen");
		
		if (origen.equals("T")) 
			origen = "'O','E'";
		else
			origen = "'"+origen+"'";
		
		
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion!=null) {
			codigoUsuario 	= (String) sesion.getAttribute("codigoPersonal");
		}
		
		Acceso acceso	= new Acceso();
		if (accesoDao.existeReg(codigoUsuario) ) {
			acceso = accesoDao.mapeaRegId(codigoUsuario);
		}
		
		List<Carga> lisCargas 							= cargaDao.getListAll("ORDER BY ORDEN DESC");
		if (cargaId.equals("0") && lisCargas.size() >= 1) {
			cargaId = lisCargas.get(0).getCargaId();
		}
		List<CargaAcademica> lisMaterias				= cargaAcademicaDao.lisPorCargayOrigen(cargaId, origen," ORDER BY FACULTAD(CARRERA_ID), CARRERA_ID, NOMBRE_CURSO");
		HashMap<String, CatFacultad> mapaFacultades		= catFacultadDao.getMapFacultad("");
		HashMap<String, CatCarrera> mapaCarreras		= catCarreraDao.getMapAll("");
		HashMap<String, CatModalidad> mapaModalidades	= catModalidadDao.getMapAll("");
		HashMap<String, String> mapaTotalAlumnos		= krdxCursoActDao.mapaTotalAlumnos(cargaId, "'I','1','2','4','5'");
		
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisMaterias", lisMaterias);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaModalidades", mapaModalidades);
		modelo.addAttribute("mapaTotalAlumnos", mapaTotalAlumnos);
		
		return "carga_grupo/materias/activasreporte";
	}	
	
	@RequestMapping("/carga_grupo/materias/alumnos")
	public String cargaGrupoMateriasListadoProfesorAlumnos(HttpServletRequest request, Model modelo){
		
		String cursoCargaId				= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		String cursoId					= request.getParameter("CursoId")==null?"0":request.getParameter("CursoId");
		String cargaId					= cargaGrupoDao.getCargaId(cursoCargaId);
		String maestroNombre 			= "-";
		
		CargaAcademica cargaAcademica	= new CargaAcademica();
		if (cargaAcademicaDao.existeReg(cursoCargaId, cursoId)) {
			cargaAcademica 		= cargaAcademicaDao.mapeaRegId(cursoCargaId, cursoId);
			maestroNombre 		= usuariosDao.getNombreUsuario(cargaAcademica.getCodigoPersonal(), "NOMBRE");
		}	
		
		List<AlumnoCurso> lisAlumnos				= alumnoCursoDao.getListCurso(cursoCargaId, " ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");
		HashMap<String,AlumPersonal> mapaAlumnos	= alumPersonalDao.mapAlumnosEnMateria(cursoCargaId);
		HashMap<String,CargaAlumno> mapaCargas		= cargaAlumnoDao.mapaCargasAlumno("'"+cargaId+"'");		
		HashMap<String,String> mapTipoCal 			= catTipoCalDao.mapTipoCal();
		
		modelo.addAttribute("cargaAcademica", cargaAcademica);
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("maestroNombre", maestroNombre);
		modelo.addAttribute("mapTipoCal", mapTipoCal);
		modelo.addAttribute("mapaCargas", mapaCargas);
		
		return "carga_grupo/materias/alumnos";
	}	
}