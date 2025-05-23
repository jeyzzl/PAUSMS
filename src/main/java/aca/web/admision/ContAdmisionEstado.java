package aca.web.admision;

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

import aca.alumno.spring.AlumEstado;
import aca.alumno.spring.AlumEstadoDao;
import aca.alumno.spring.AlumFamilia;
import aca.alumno.spring.AlumPersonal;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaAlumno;
import aca.carga.spring.CargaBloque;
import aca.carga.spring.CargaBloqueDao;
import aca.carga.spring.CargaDao;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatTipoCal;
import aca.catalogo.spring.CatTipoCalDao;
import aca.kardex.spring.KrdxCursoAct;
import aca.kardex.spring.KrdxCursoActDao;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.AlumnoCursoDao;

@Controller
public class ContAdmisionEstado {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@Autowired
	aca.alumno.spring.AlumPersonalDao alumPersonalDao;
	
	@Autowired
	aca.carga.spring.CargaAlumnoDao cargaAlumnoDao;
	
	@Autowired
	CatTipoCalDao catTipoCalDao;
	
	@Autowired
	AlumEstadoDao alumEstadoDao;
	
	@Autowired
	CargaBloqueDao cargaBloqueDao;
	
	@Autowired
	CargaDao cargaDao;
	
	@Autowired
	KrdxCursoActDao krdxCursoActDao;
	
	@Autowired
	AlumnoCursoDao alumnoCursoDao;
	
	@Autowired
	MapaPlanDao mapaPlanDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@RequestMapping("/admision/estado/elige_bloque")
	public String admisionEstadoElige_Bloque(HttpServletRequest request, Model modelo) {
		
		String cargaId 		= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String bloqueId 	= request.getParameter("BloqueId")==null?"0":request.getParameter("BloqueId");
		String codigoAlumno = "0";		 
		
		AlumEstado alumEstado = new AlumEstado();
		HttpSession sesion = ((HttpServletRequest) request).getSession();
		if (sesion != null) {
			codigoAlumno 	= (String) sesion.getAttribute("codigoAlumno");
			alumEstado 		= alumEstadoDao.mapeaRegId(codigoAlumno, cargaId, bloqueId);
		}
		
		String nombreAlumno	= alumPersonalDao.getNombre(codigoAlumno, "NOMBRE");
		String cargaNombre 	= cargaAlumnoDao.getCargaNombre(cargaId);
		
		List<CatTipoCal> lisStatus		= catTipoCalDao.getListAll("");
		List<CargaBloque> lisBloques	= cargaBloqueDao.getLista(cargaId, " ORDER BY BLOQUE_ID");

		modelo.addAttribute("alumEstado", alumEstado);
		modelo.addAttribute("codigoAlumno", codigoAlumno);
		modelo.addAttribute("nombreAlumno", nombreAlumno);		
		modelo.addAttribute("cargaNombre", cargaNombre);
		modelo.addAttribute("lisStatus", lisStatus);
		modelo.addAttribute("lisBloques", lisBloques);

		return "admision/estado/elige_bloque";
	}

	@RequestMapping("/admision/estado/nuevoEstado")
	public String admisionEstadoNuevoEstado(HttpServletRequest request, Model modelo) {
		
		String cargaId 		= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String bloqueId 	= request.getParameter("BloqueId")==null?"0":request.getParameter("BloqueId");
		String estado 		= request.getParameter("Estado")==null?"0":request.getParameter("Estado");
		String mensaje 		= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		String codigoAlumno = "0";		 
		
		AlumEstado alumEstado = new AlumEstado();
		HttpSession sesion = ((HttpServletRequest) request).getSession();
		if (sesion != null) {
			codigoAlumno 	= (String) sesion.getAttribute("codigoAlumno");
		}
		
		if (!cargaId.equals("0") && !bloqueId.equals("0") && !estado.equals("0")) {
			alumEstado.setCodigoPersonal(codigoAlumno);
			alumEstado.setCargaId(cargaId);
			alumEstado.setBloqueId(bloqueId);
			alumEstado.setEstado(estado);
			if(alumEstadoDao.insertReg(alumEstado)) {
				mensaje = "1";
			}
		}		
		
		String nombreAlumno	= alumPersonalDao.getNombre(codigoAlumno, "NOMBRE");
		String cargaNombre 	= cargaAlumnoDao.getCargaNombre(cargaId);
		
		List<CatTipoCal> lisStatus		= catTipoCalDao.getListAll("");
		List<CargaAlumno> lisCargas	 	= cargaAlumnoDao.getCargasAlumno(codigoAlumno);
		
		if(cargaId.equals("0")) {
			cargaId = lisCargas.get(0).getCargaId();
		}
		
		HashMap<String,Carga> mapaCargas	= cargaDao.mapaCargas();

		List<CargaBloque> lisBloques	= cargaBloqueDao.getLista(cargaId, " ORDER BY BLOQUE_ID");
		
		modelo.addAttribute("alumEstado", alumEstado);
		modelo.addAttribute("codigoAlumno", codigoAlumno);
		modelo.addAttribute("nombreAlumno", nombreAlumno);		
		modelo.addAttribute("cargaNombre", cargaNombre);
		modelo.addAttribute("lisStatus", lisStatus);
		modelo.addAttribute("lisBloques", lisBloques);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("alumEstado", alumEstado);
		modelo.addAttribute("mapaCargas", mapaCargas);
		modelo.addAttribute("mensaje", mensaje);
		
		return "admision/estado/nuevoEstado";
	}
	
	@RequestMapping("/admision/estado/grabar")
	public String admisionEstadoGrabar(HttpServletRequest request, Model modelo) {
		
		String cargaId 		= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String bloqueId 	= request.getParameter("BloqueId")==null?"0":request.getParameter("BloqueId");
		String estado 		= request.getParameter("Estado")==null?"0":request.getParameter("Estado");
		String codigoAlumno	= "0";
		String mensaje 		= "-";
		
		HttpSession sesion = ((HttpServletRequest) request).getSession();
		if (sesion != null) {
			codigoAlumno 	= (String) sesion.getAttribute("codigoAlumno");			
		}
		AlumEstado alumEstado = new AlumEstado();
		if (alumEstadoDao.existeReg(codigoAlumno, cargaId, bloqueId)) {
			alumEstado 		= alumEstadoDao.mapeaRegId(codigoAlumno, cargaId, bloqueId);
			alumEstado.setEstado(estado);
			if (alumEstadoDao.updateEstadoReg(alumEstado)) {
				mensaje = "¡Saved data!";
			}else {
				mensaje = "¡Error!";
			}
		}else {
			alumEstado.setCodigoPersonal(codigoAlumno);
			alumEstado.setCargaId(cargaId);
			alumEstado.setBloqueId(bloqueId);
			alumEstado.setEstado(estado);
			if (alumEstadoDao.insertReg(alumEstado)){
				mensaje = "¡Saved data!";
			}else {
				mensaje = "¡Error!";
			}			
		}		
		return "redirect:/admision/estado/elige_bloque?CargaId="+cargaId+"&BloqueId="+bloqueId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/admision/datos/elige_carga")
	public String admisionDatosEligeCarga(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContAdmisionEstado|admisionDatosEligeCarga:");
		return "admision/datos/elige_carga";
	}
	
	@RequestMapping("/admision/estado/inscribir")
	public String admisionEstadoInscribir(HttpServletRequest request){
		
		String planId		= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String cargaId		= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String bloqueId		= request.getParameter("Bloque")==null?"0":request.getParameter("Bloque");
		String codigoAlumno = "0";
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion != null){	        	        	
			codigoAlumno	= (String) sesion.getAttribute("codigoAlumno");
		}
		
		boolean existe 		= false;
		AlumEstado alumEstado = new AlumEstado();
		if (alumEstadoDao.existeReg(codigoAlumno, cargaId, bloqueId)){
			alumEstado = alumEstadoDao.mapeaRegId(codigoAlumno, cargaId, bloqueId);
			alumEstado.setEstado("I");
			existe = true;
		}else {
			String carreraId 	= mapaPlanDao.getCarreraId(planId);
			String facultadId 	= catCarreraDao.getFacultadId(carreraId);
			alumEstado.setCodigoPersonal(codigoAlumno);
			alumEstado.setCargaId(cargaId);
			alumEstado.setBloqueId(bloqueId);
			alumEstado.setEstado("I");
			alumEstado.setPlanId(planId);
			alumEstado.setFacultadId(facultadId);
			alumEstado.setCarreraId(carreraId);
			alumEstado.setClasFin("1");			
		}
		if (krdxCursoActDao.updateTipoCal(codigoAlumno, planId, cargaId, bloqueId, "I")){
			if (existe) {
				alumEstadoDao.updateEstadoReg(alumEstado);
			}else {
				alumEstadoDao.insertReg(alumEstado);
			}	
		}
		return "redirect:/admision/estado/estado";
	}
	
	@RequestMapping("/admision/estado/asignar")
	public String admisionEstadoAsignar(HttpServletRequest request){
		
		String planId		= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String cargaId		= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String bloqueId		= request.getParameter("Bloque")==null?"0":request.getParameter("Bloque");
		String codigoAlumno = "0";
		String mensaje		= "";
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion != null){	        	        	
			codigoAlumno	= (String) sesion.getAttribute("codigoAlumno");
		}
		
		boolean existe 		= false;
		AlumEstado alumEstado = new AlumEstado();
		if (alumEstadoDao.existeReg(codigoAlumno, cargaId, bloqueId)){
			alumEstado = alumEstadoDao.mapeaRegId(codigoAlumno, cargaId, bloqueId);
			alumEstado.setEstado("M");
			existe = true;
		}else {
			mensaje = "Not found";
		}
		if (krdxCursoActDao.updateTipoCal(codigoAlumno, planId, cargaId, bloqueId, "M")){
			if (existe) {
				alumEstadoDao.updateEstadoReg(alumEstado);
			}	
		}else {
			mensaje = "Error rolling Student back to Assigned Status";
		}
		
		return "redirect:/admision/estado/estado";
	}

	@RequestMapping("/admision/estado/estado")
	public String admisionEstadoEstado(HttpServletRequest request, Model modelo){
		
		String codigoAlumno		= "0";
		String nombreAlumno		= "-";
		String planId 			= "-";
		HttpSession sesion 		= ((HttpServletRequest) request).getSession();
		if (sesion != null) {
			codigoAlumno 		= (String) sesion.getAttribute("codigoAlumno");
			nombreAlumno		= alumPersonalDao.getNombre(codigoAlumno, "NOMBRE");
			planId				= alumPersonalDao.getPlanActivo(codigoAlumno);
		}
		
		List<AlumEstado> lisEstados			= alumEstadoDao.getLista(codigoAlumno);
		
		HashMap<String,Carga> mapaCargas	= cargaDao.mapaCargas();
		HashMap<String,CatTipoCal> mapaTipoCal = catTipoCalDao.getMapAll("");
		HashMap<String, String> mapaMaterias = alumnoCursoDao.mapaMateriasPorCargayBloque(codigoAlumno);
		
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("lisEstados", lisEstados);
		modelo.addAttribute("mapaCargas", mapaCargas);
		modelo.addAttribute("mapaTipoCal", mapaTipoCal);
		modelo.addAttribute("mapaMaterias", mapaMaterias);
		
		return "admision/estado/estado";
	}
}