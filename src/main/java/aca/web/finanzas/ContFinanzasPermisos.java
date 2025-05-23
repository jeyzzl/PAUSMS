package aca.web.finanzas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPlanDao;
import aca.emp.spring.EmpleadoDao;
import aca.financiero.spring.FinPermiso;
import aca.financiero.spring.FinSaldo;
import aca.plan.spring.MapaPlanDao;

@Controller
public class ContFinanzasPermisos {		
	
	@Autowired
	aca.alumno.spring.AlumPersonalDao alumPersonalDao;
	
	@Autowired
	aca.vista.spring.UsuariosDao usuariosDao;
	
	@Autowired
	aca.financiero.spring.FinPermisoDao finPermisoDao;
	
	@Autowired
	aca.financiero.spring.ContMovimientoDao contMovimientoDao;
	
	@Autowired
	aca.financiero.spring.A3lAsalfldgDao a3lAsalfldgDao;	

	@Autowired
	AccesoDao accesoDao;

	@Autowired
	AlumPlanDao alumPlanDao;

	@Autowired
	MapaPlanDao mapaPlanDao;
	
	@Autowired
	EmpleadoDao empleadoDao;
	
	@RequestMapping("/finanzas/permisos/permisos")
	public String finanzasPermisosPermisos(HttpServletRequest request, Model modelo){
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();		
		String codigoEmpleado	= "0";
		String codigoAlumno		= "0";
		String nombreAlumno		= "-";
		String autorizacion 	= "";		
		AlumPersonal personal 	= new AlumPersonal();
		
		List<FinPermiso> lisPermisos 		= new ArrayList<FinPermiso>();
		
		if (sesion != null) {
			codigoEmpleado	= (String) sesion.getAttribute("codigoEmpleado");
			codigoAlumno	= (String) sesion.getAttribute("codigoAlumno");
			nombreAlumno 	= usuariosDao.getNombreUsuario(codigoAlumno, "NOMBRE");
			
			if (codigoAlumno.subSequence(0, 1).equals("0") || codigoAlumno.subSequence(0, 1).equals("1") || codigoAlumno.subSequence(0, 1).equals("2")){
				personal 	= alumPersonalDao.mapeaRegId(codigoAlumno);
			}
			
			autorizacion 	= finPermisoDao.getAutorizacion(codigoAlumno);
			
			lisPermisos 	= finPermisoDao.getListAlumno(codigoAlumno, "ORDER BY FOLIO");
		}
		
		Acceso acceso = accesoDao.mapeaRegId(codigoEmpleado);
		
		List<String> lisPlanes = alumPlanDao.getPlanesAlumno(codigoAlumno);
		
		boolean mostrarPermisos = false;
		
		for(String plan : lisPlanes) {
			String carreraId = mapaPlanDao.getCarreraId(plan);
			if(acceso.getAccesos().indexOf(carreraId)!= -1 || acceso.getAdministrador().equals("S") || acceso.getSupervisor().equals("S") ){
				mostrarPermisos = true;
			}
		}
		// Consulta el saldo del estudiante
		FinSaldo finSaldo 	= new FinSaldo();
		try {		
			RestTemplate restTemplate = new RestTemplate();
			//finSaldo = restTemplate.getForObject("https://eduadvent.um.edu.mx/infor/reportes/api/fe/saldo/"+matricula, FinSaldo.class);
			finSaldo = restTemplate.getForObject("http://172.16.251.110:8080/infor/reportes/api/fe/saldo/"+codigoAlumno, FinSaldo.class);
		}catch(Exception ex) {
			System.out.println("Error en saldo:"+codigoAlumno);
		}
		
		HashMap<String, String> mapNombrePermiso = empleadoDao.mapEmpleado();
		
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("autorizacion", autorizacion);
		modelo.addAttribute("personal", personal);
		modelo.addAttribute("mostrarPermisos", mostrarPermisos);
		modelo.addAttribute("lisPermisos", lisPermisos);
		modelo.addAttribute("finSaldo", finSaldo);
		modelo.addAttribute("mapNombrePermiso", mapNombrePermiso);
		
		return "finanzas/permisos/permisos";
	}
	
	@RequestMapping("/finanzas/permisos/editar")
	public String finanzasPermisosEditar(HttpServletRequest request, Model modelo){
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		
		String codigoAlumno		= "0";
		String nombreAlumno		= "-";
		String autorizacion 	= "";
		
		if (sesion != null) {
			codigoAlumno	= (String) sesion.getAttribute("codigoAlumno");
			nombreAlumno 	= usuariosDao.getNombreUsuario(codigoAlumno, "NOMBRE");
			autorizacion 	= finPermisoDao.getAutorizacion(codigoAlumno);
		}
		
		// Consulta el saldo del estudiante
		FinSaldo finSaldo 	= new FinSaldo();
		try {		
			RestTemplate restTemplate = new RestTemplate();
			//finSaldo = restTemplate.getForObject("https://eduadvent.um.edu.mx/infor/reportes/api/fe/saldo/"+matricula, FinSaldo.class);
			finSaldo = restTemplate.getForObject("http://172.16.251.110:8080/infor/reportes/api/fe/saldo/"+codigoAlumno, FinSaldo.class);
		}catch(Exception ex) {
			System.out.println("Error en saldo:"+codigoAlumno);
		}
		
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("autorizacion", autorizacion);
		modelo.addAttribute("finSaldo", finSaldo);
		
		return "finanzas/permisos/editar";
	}
	
	@RequestMapping("/finanzas/permisos/grabarPermiso")
	public String finanzasPermisosGrabarPermiso(HttpServletRequest request){
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		
		String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		String fInicio 			= request.getParameter("fInicio")==null?"":request.getParameter("fInicio");
		String fLimite 			= request.getParameter("fLimite")==null?"":request.getParameter("fLimite");
		String comentario 		= request.getParameter("comentario")==null?"-":request.getParameter("comentario");
		
		String codigoPersonal 	= "0";
		String codigoAlumno		= "0";
		String folio			= "1";
		
		FinPermiso permiso 		= new FinPermiso();
		
		if (sesion != null) {
			codigoPersonal	= (String) sesion.getAttribute("codigoPersonal");
			codigoAlumno	= (String) sesion.getAttribute("codigoAlumno");
			
			folio 			= finPermisoDao.getNuevoFolio(codigoAlumno);
		}
		
		String link = "";
		
		if(accion.equals("1")) {
			permiso.setCodigoPersonal(codigoAlumno);
			permiso.setFolio(folio);
			permiso.setFInicio(fInicio);
			permiso.setFLimite(fLimite);
			permiso.setUsuario(codigoPersonal);
			permiso.setComentario(comentario);
			if(finPermisoDao.insertReg(permiso)){
				link = "redirect:/finanzas/permisos/permisos"+"?Accion="+accion+"&Error=N";
			}else {
				link = "redirect:/finanzas/permisos/editar"+"?Accion="+accion+"&Error=S";
			}
		}
		return link;
	}
	
	@RequestMapping("/finanzas/permisos/borrarPermiso")
	public String finanzasPermisosBorrarPermiso(HttpServletRequest request, Model modelo){
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		
		String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		String folio			= request.getParameter("folio")==null?"":request.getParameter("folio");
		
		String codigoAlumno		= "0";
		
		FinPermiso permiso 		= new FinPermiso();
		
		if (sesion != null) {
			codigoAlumno	= (String) sesion.getAttribute("codigoAlumno");
		}
		
		if(accion.equals("3")) {
			permiso.setCodigoPersonal(codigoAlumno);
			permiso.setFolio(folio);
			finPermisoDao.deleteReg(codigoAlumno, folio);
		}
		
		return "redirect:/finanzas/permisos/permisos";
	}
	
}