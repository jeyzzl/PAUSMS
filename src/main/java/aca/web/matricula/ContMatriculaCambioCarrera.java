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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlanDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaAlumno;
import aca.carga.spring.CargaAlumnoDao;
import aca.carga.spring.CargaDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatTipoCal;
import aca.catalogo.spring.CatTipoCalDao;
import aca.matricula.spring.CambioCarrera;
import aca.matricula.spring.CambioCarreraDao;
import aca.matricula.spring.CambioMateria;
import aca.matricula.spring.CambioMateriaDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.AlumnoCurso;
import aca.vista.spring.AlumnoCursoDao;
import aca.vista.spring.CargaAcademica;
import aca.vista.spring.CargaAcademicaDao;
import aca.vista.spring.InscritosDao;

@Controller
public class ContMatriculaCambioCarrera {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired
	private CambioCarreraDao cambioCarreraDao;
	
	@Autowired
	private CambioMateriaDao cambioMateriaDao;
	
	@Autowired
	private InscritosDao inscritosDao;
	
	@Autowired
	private CargaDao cargaDao;
	
	@Autowired
	private AlumnoCursoDao alumnoCursoDao;
	
	@Autowired
	private MapaPlanDao mapaPlanDao;
	
	@Autowired
	private CatTipoCalDao catTipoCalDao;
	
	@Autowired
	private CargaAcademicaDao cargaAcademicaDao;
	
	@Autowired
	private CargaAlumnoDao cargaAlumnoDao;
	
	@Autowired
	private CatCarreraDao catCarreraDao;
	
	@RequestMapping("/matricula/cambioCarrera/listado")
	public String matriculaCambioCarreraListado(HttpServletRequest request, Model modelo){
		
		String codigoPersonal		= "0";		
		HttpSession sesion 			= ((HttpServletRequest)request).getSession();		
		if (sesion != null){
			codigoPersonal 	= (String) sesion.getAttribute("codigoAlumno");
        }
		
		AlumPersonal alumno = alumPersonalDao.mapeaRegId(codigoPersonal);
		
		List<CambioCarrera> lisCambioCarrera = cambioCarreraDao.lisCambioCarreraAlumno(codigoPersonal);
		
		HashMap<String,CatCarrera> mapaCarreras = catCarreraDao.getMapAll("");
		HashMap<String, String> mapCarreraPlan = mapaPlanDao.mapCarreraPlan();
		
		modelo.addAttribute("alumno", alumno);
		modelo.addAttribute("lisCambioCarrera", lisCambioCarrera);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapCarreraPlan", mapCarreraPlan);
		
		return "matricula/cambioCarrera/listado";
	}
	
	@Transactional
	@RequestMapping("/matricula/cambioCarrera/eliminar")
	public String matriculaCambioCarreraEliminar(HttpServletRequest request, Model modelo){	
		
		String solicitudId	= request.getParameter("SolicitudId")==null?"0":request.getParameter("SolicitudId");
		String mensaje = "";
		
		boolean borroMaterias = true;
		if (cambioMateriaDao.tieneMaterias(solicitudId)) {
			if (cambioMateriaDao.deletePorSolicitud(solicitudId)==false){
				borroMaterias=false;
			}
		}		
		if(borroMaterias && cambioCarreraDao.existeReg(solicitudId)){			
			if(cambioCarreraDao.deleteReg(solicitudId)) {				
				mensaje = "¡Se eliminó la carrera!";
			}else {				
				mensaje = "!No se pudo realizar la operación";
			}
		}
		return "redirect:/matricula/cambioCarrera/listado?Mensaje="+mensaje;
	}

	@RequestMapping("/matricula/cambioCarrera/nuevaSolicitud")
	public String matriculaCambioCarreraNuevaSolicitud(HttpServletRequest request, Model modelo){
		String baja			= request.getParameter("Baja")==null?"0":request.getParameter("Baja");
		String alta			= request.getParameter("Alta")==null?"0":request.getParameter("Alta");
		String solicitudId	= request.getParameter("SolicitudId")==null?"0":request.getParameter("SolicitudId");
		String mensaje		= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		String cargaEligio	= request.getParameter("CargaEligio")==null?"0":request.getParameter("CargaEligio");
		String cargaId 		= "0";
		String bloqueId 	= "0";
		
		String codigoPersonal		= "0";		
		HttpSession sesion 			= ((HttpServletRequest)request).getSession();		
		if (sesion != null){
			codigoPersonal 	= (String) sesion.getAttribute("codigoAlumno");
		}
		
		AlumPersonal alumno = alumPersonalDao.mapeaRegId(codigoPersonal);
		
		boolean esInscrito	= inscritosDao.inscrito(codigoPersonal);
		if(!baja.equals("0") && baja.length()>=2) {
			String tmp[] = baja.split("-");
			cargaId = tmp[0];
			bloqueId = tmp[1];
		}
		
		List<Carga> lisCargas 				= cargaDao.getListCargasActivas(" ORDER BY NOMBRE_CARGA");
		
		if(cargaEligio.equals("0")) {
			cargaEligio = lisCargas.get(0).getCargaId();
		}
		
		List<CargaAlumno> lisPlanesAlumno 	= cargaAlumnoDao.getListaPlanesAlumno(codigoPersonal, "'3','4'");
		List<MapaPlan> lisPlanes	 		= mapaPlanDao.getListPlanActivoEnCarga(cargaEligio, "ORDER BY CARRERA_SE");
		List<AlumnoCurso> listaMaterias 	= alumnoCursoDao.getListAlumnoCargaBloque(codigoPersonal, cargaId, bloqueId, "");
		HashMap<String,CatTipoCal> mapaTipoCal 	= catTipoCalDao.getMapAll("");
		HashMap<String, String> mapNombrePlan 	= mapaPlanDao.mapNombrePlan();
		
		List<AlumnoCurso> lisAlumnoCursoBaja 	= new ArrayList<AlumnoCurso>();
		List<CargaAcademica> lisAlumnoCursoAlta = new ArrayList<CargaAcademica>();
		if(!baja.equals("0")) {
			lisAlumnoCursoBaja = alumnoCursoDao.lisMateriasPorCargayBloque(codigoPersonal,  cargaId, bloqueId,"'I'"," ORDER BY CICLO, NOMBRE_CURSO");
		}else if (lisPlanesAlumno.size()>=1){
			baja = lisPlanesAlumno.get(0).getPlanId();
		}
		if(!alta.equals("0")) {
			lisAlumnoCursoAlta = cargaAcademicaDao.lisMateriasDelPlan(cargaEligio, alta, "ORDER BY CICLO,NOMBRE");
		}else if (lisPlanes.size()>=1){
			alta = lisPlanes.get(0).getPlanId();
			lisAlumnoCursoAlta = cargaAcademicaDao.lisMateriasDelPlan(cargaEligio, alta, "ORDER BY CICLO,NOMBRE");
		}
		
		HashMap<String,CambioMateria> mapaCambioMateria = new HashMap<String,CambioMateria>();	
		
		if(cambioCarreraDao.existeReg(solicitudId)) {
			mapaCambioMateria = cambioMateriaDao.mapaCambioMateria(solicitudId );
		}
		
		modelo.addAttribute("baja", baja);
		modelo.addAttribute("alta", alta);
		modelo.addAttribute("cargaEligio", cargaEligio);
		modelo.addAttribute("alumno", alumno);
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("lisAlumnoCursoBaja", lisAlumnoCursoBaja);
		modelo.addAttribute("lisAlumnoCursoAlta", lisAlumnoCursoAlta);
		modelo.addAttribute("esInscrito", esInscrito);
		modelo.addAttribute("lisPlanesAlumno", lisPlanesAlumno);
		modelo.addAttribute("lisPlanesAlumno", lisPlanesAlumno);
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("listaMaterias", listaMaterias);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("mapaTipoCal", mapaTipoCal);
		modelo.addAttribute("mapNombrePlan", mapNombrePlan);
		modelo.addAttribute("mapaCambioMateria", mapaCambioMateria);
		
		return "matricula/cambioCarrera/nuevaSolicitud";
	}
	
	@RequestMapping("/matricula/cambioCarrera/grabar")
	public String matriculaCambioCarreraGrabar(HttpServletRequest request, Model modelo){
		
		String baja			= request.getParameter("Baja")==null?"0":request.getParameter("Baja");
		String alta			= request.getParameter("Alta")==null?"0":request.getParameter("Alta");
		String cargaEligio	= request.getParameter("CargaEligio")==null?"0":request.getParameter("CargaEligio");
		String solicitudId 	= "";
		String cargaId 		= "0";
		String bloqueId 	= "0";
		
		if(!baja.equals("0")) {
			String tmp[] = baja.split("-");
			cargaId = tmp[0];
			bloqueId = tmp[1];
		}
		
		String codigoPersonal		= "0";		
		String codigoEmpleado		= "0";		
		HttpSession sesion 			= ((HttpServletRequest)request).getSession();		
		if (sesion != null){
			codigoPersonal 	= (String) sesion.getAttribute("codigoAlumno");
			codigoEmpleado 	= (String) sesion.getAttribute("codigoEmpleado");
        }
		
		List<AlumnoCurso> lisAlumnoCursoBaja 	= new ArrayList<AlumnoCurso>();
		List<CargaAcademica> lisAlumnoCursoAlta = new ArrayList<CargaAcademica>();
		
		if(!baja.equals("0")) {
			lisAlumnoCursoBaja = alumnoCursoDao.lisMateriasPorCargayBloque(codigoPersonal,  cargaId, bloqueId,"'I'", " ORDER BY CICLO, NOMBRE_CURSO");
		}
		
		if(!alta.equals("0")) {
			lisAlumnoCursoAlta = cargaAcademicaDao.lisMateriasDelPlan(cargaEligio, alta, "ORDER BY CICLO,NOMBRE");
		}
		
		CargaAlumno cargaAlumno = new CargaAlumno();
		
		if(cargaAlumnoDao.existeReg(codigoPersonal, cargaId, bloqueId)) {
			cargaAlumno = cargaAlumnoDao.mapeaRegId(codigoPersonal, cargaId, bloqueId);
		}
		
		CambioCarrera cambioCarrera = new CambioCarrera();
		CambioMateria cambioMateria = new CambioMateria();
		
		solicitudId = cambioCarreraDao.getSolitidudId();
		
		cambioCarrera.setSolicitudId(solicitudId);
		cambioCarrera.setCodigoPersonal(codigoPersonal);
		cambioCarrera.setCarreraBaja(cargaAlumno.getPlanId());
		cambioCarrera.setCarreraAlta(alta);
		cambioCarrera.setFecha(aca.util.Fecha.getHoy());
		cambioCarrera.setUsuario(codigoEmpleado);
		
		String mensaje = "0";
		
		boolean esInscrito = inscritosDao.inscrito(codigoPersonal);
		
		if(cambioCarreraDao.insertReg(cambioCarrera)) {
			if(esInscrito) {
				for(AlumnoCurso curso : lisAlumnoCursoBaja){
					boolean ok	= request.getParameter(curso.getCursoId()) == null ? false : true;
					if(ok) {
						cambioMateria.setCodigoPersonal(codigoPersonal);
						cambioMateria.setSolicitudId(solicitudId);
						cambioMateria.setCursoCargaId(curso.getCursoCargaId());
						cambioMateria.setCursoId(curso.getCursoId());
						cambioMateria.setTipo("B");
						
						cambioMateriaDao.insertReg(cambioMateria);
					}
				}
	
				for(CargaAcademica curso : lisAlumnoCursoAlta){
					boolean ok	= request.getParameter(curso.getCursoCargaId()) == null ? false : true;
					if(ok) {
						cambioMateria.setCodigoPersonal(codigoPersonal);
						cambioMateria.setSolicitudId(solicitudId);
						cambioMateria.setCursoCargaId(curso.getCursoCargaId());
						cambioMateria.setCursoId(curso.getCursoId());
						cambioMateria.setTipo("A");
						
						cambioMateriaDao.insertReg(cambioMateria);
					}
				}
			}
			mensaje = "1";
		}
		
		return "redirect:/matricula/cambioCarrera/nuevaSolicitud?Baja="+baja+"&Alta="+alta+"&SolicitudId="+solicitudId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/matricula/cambioCarrera/pdf")
	public String matriculaCambioCarreraPdf(HttpServletRequest request, Model modelo){
		String solicitudId	= request.getParameter("SolicitudId")==null?"0":request.getParameter("SolicitudId");
		
		CambioCarrera cambioCarrera = new CambioCarrera();
		
		if(cambioCarreraDao.existeReg(solicitudId)) {
			cambioCarrera = cambioCarreraDao.mapeaRegId(solicitudId);
		}
		
		AlumPersonal alumno = alumPersonalDao.mapeaRegId(cambioCarrera.getCodigoPersonal());
		
		boolean esInscrito = inscritosDao.inscrito(cambioCarrera.getCodigoPersonal());
		
		HashMap<String,CatTipoCal> mapaTipoCal 	= catTipoCalDao.getMapAll("");
		HashMap<String, String> mapNombrePlan 	= mapaPlanDao.mapNombrePlan();
		
		List<AlumnoCurso>  lisAlumnoCursoBaja 	= alumnoCursoDao.lisMateriasBaja(cambioCarrera.getCodigoPersonal(), solicitudId, " ORDER BY CICLO, NOMBRE_CURSO");
		List<CargaAcademica> lisAlumnoCursoAlta = cargaAcademicaDao.lisMateriasAlta(cambioCarrera.getCarreraAlta(), cambioCarrera.getSolicitudId(), "ORDER BY CICLO, NOMBRE_CURSO");
		
		HashMap<String,CambioMateria> mapaCambioMateria = new HashMap<String,CambioMateria>();	
		
		if(cambioCarreraDao.existeReg(solicitudId)) {
			mapaCambioMateria = cambioMateriaDao.mapaCambioMateria(solicitudId);
		}
		
		HashMap<String,CatCarrera> mapaCarreras = catCarreraDao.getMapAll("");
		HashMap<String, String> mapCarreraPlan 	= mapaPlanDao.mapCarreraPlan();
			
		modelo.addAttribute("cambioCarrera", cambioCarrera);
		modelo.addAttribute("alumno", alumno);
		modelo.addAttribute("esInscrito", esInscrito);
		modelo.addAttribute("lisAlumnoCursoBaja", lisAlumnoCursoBaja);
		modelo.addAttribute("lisAlumnoCursoAlta", lisAlumnoCursoAlta);
		modelo.addAttribute("mapaTipoCal", mapaTipoCal);
		modelo.addAttribute("mapNombrePlan", mapNombrePlan);
		modelo.addAttribute("mapaCambioMateria", mapaCambioMateria);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapCarreraPlan", mapCarreraPlan);
		
		return "matricula/cambioCarrera/pdf";
	}
	
}