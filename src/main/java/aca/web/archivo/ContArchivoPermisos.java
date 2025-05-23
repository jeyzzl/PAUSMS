package aca.web.archivo;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumPersonalDao;
import aca.archivo.spring.ArchPermisos;
import aca.archivo.spring.ArchPermisosDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContArchivoPermisos {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	ArchPermisosDao archPermisosDao;
	
	@Autowired
	MaestrosDao maestrosDao;
	
	@Autowired	
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired	
	private MapaPlanDao mapaPlanDao;
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/archivo/permisos/actualiza_permiso")
	public String archivoActaulaizaPermisos(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContArchivoPermisos|archivoPermisosAccion");
		return "archivo/permisos/actualiza_permiso";
	}
	
	@RequestMapping("/archivo/permisos/permiso")
	public String archivoPermisosPermiso(HttpServletRequest request, Model modelo){	
		
		String codigoAlumno		= "0";
		String nombreAlumno 	= "-";		
		
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoAlumno 	= (String) sesion.getAttribute("codigoAlumno");			
			nombreAlumno	= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");			
		}
		
		List<ArchPermisos> lisPermisos 	= archPermisosDao.getListThis(codigoAlumno, "ORDER BY FOLIO");
		
		modelo.addAttribute("nombreAlumno", nombreAlumno);		
		modelo.addAttribute("lisPermisos", lisPermisos);
		
		return "archivo/permisos/permiso";
	}
	
	@RequestMapping("/archivo/permisos/accion")
	public String archivoPermisosAccion(HttpServletRequest request, Model modelo){
		
		String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");		
		String codigoAlumno		= "0";
		String nombreAlumno 	= "";		
		
		ArchPermisos permiso 	= new ArchPermisos();
		HttpSession sesion	= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoAlumno 	= (String) sesion.getAttribute("codigoAlumno");			
			nombreAlumno	= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
			if (archPermisosDao.existeReg(codigoAlumno, folio)) {
				permiso 		=  archPermisosDao.mapeaRegId(codigoAlumno, folio);
				///nombreEmpleado	= maestrosDao.getNombreMaestro(codigoPersonal, "NOMBRE");
			}
		}
		
		List<MapaPlan> lisPlanes 	= mapaPlanDao.listPlanesDelAlumno(codigoAlumno, " ORDER BY NOMBRE_PLAN");
		
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("permiso", permiso);
		modelo.addAttribute("lisPlanes", lisPlanes);
		
		return "archivo/permisos/accion";
	}
	
	@RequestMapping("/archivo/permisos/grabar")
	public String archivoPermisosGrabar(HttpServletRequest request, Model modelo){
		
		String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String fechaIni			= request.getParameter("FechaIni")==null?"0":request.getParameter("FechaIni");
		String fechaLim			= request.getParameter("FechaLim")==null?"0":request.getParameter("FechaLim");
		String comentario		= request.getParameter("Comentario")==null?"0":request.getParameter("Comentario");
		String planId			= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String codigoPersonal	= "0";
		String codigoAlumno		= "0";				
		
		ArchPermisos permiso 	= new ArchPermisos();
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoAlumno 		= (String) sesion.getAttribute("codigoAlumno");
			codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");
			permiso.setMatricula(codigoAlumno);
			if (folio.equals("0")) {
				permiso.setFolio(archPermisosDao.maximoReg(codigoAlumno));
			}else {
				permiso.setFolio(folio);
			}
			permiso.setFechaIni(fechaIni);
			permiso.setFechaLim(fechaLim);
			permiso.setEstado("A");
			permiso.setUsuarioAlta(codigoPersonal);
			permiso.setUsuarioBaja("0");
			permiso.setComentario(comentario);
			permiso.setPlanId(planId);
			if (archPermisosDao.existeReg(codigoAlumno, folio)){	
				archPermisosDao.updateReg(permiso);
			}else {
				archPermisosDao.insertReg(permiso);				
			}
		}
		
		return "redirect:/archivo/permisos/permiso";
	}
	
	
	@RequestMapping("/archivo/permisos/borrar")
	public String archivoPermisosBorrar(HttpServletRequest request, Model modelo){
		
		String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");		
		String codigoAlumno		= "0";				
		
		ArchPermisos permiso 	= new ArchPermisos();
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoAlumno 		= (String) sesion.getAttribute("codigoAlumno");			
			permiso.setMatricula(codigoAlumno);
			permiso.setFolio(folio);		
			
			if (archPermisosDao.existeReg(codigoAlumno, folio)){
				archPermisosDao.deleteReg(codigoAlumno, folio);
			}
		}
		
		return "redirect:/archivo/permisos/permiso";
	}
	
	@RequestMapping("/archivo/permisos/cancelar")
	public String archivoPermisosCancelar(HttpServletRequest request, Model modelo){
		
		String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");		
		String codigoPersonal	= "0";
		String codigoAlumno		= "0";				
		
		ArchPermisos permiso 	= new ArchPermisos();
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoAlumno 		= (String) sesion.getAttribute("codigoAlumno");
			codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");						
			if (archPermisosDao.existeReg(codigoAlumno, folio)){
				permiso = archPermisosDao.mapeaRegId(codigoAlumno, folio);						
				permiso.setEstado("I");
				permiso.setUsuarioBaja(codigoPersonal);
				archPermisosDao.updateReg(permiso);
			}
		}		
		return "redirect:/archivo/permisos/permiso";
	}
	
}
