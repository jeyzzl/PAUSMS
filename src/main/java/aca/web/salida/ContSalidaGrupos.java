package aca.web.salida;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.acceso.spring.AccesoOpcion;
import aca.acceso.spring.AccesoOpcionDao;
import aca.menu.spring.ModuloOpcionDao;
import aca.salida.spring.SalGrupo;
import aca.salida.spring.SalGrupoDao;
import aca.salida.spring.SalSolicitudDao;
import aca.vista.spring.Maestros;
import aca.vista.spring.MaestrosDao;
import aca.vista.spring.Usuarios;
import aca.vista.spring.UsuariosDao;

@Controller
public class ContSalidaGrupos {	
	
	@Autowired
	SalGrupoDao salGrupoDao;
	
	@Autowired
	SalSolicitudDao salSolicitudDao;
	
	@Autowired
	MaestrosDao maestrosDao;
	
	@Autowired
	ModuloOpcionDao moduloOpcionDao;
	
	@Autowired
	UsuariosDao usuariosDao;
	
	@Autowired
	AccesoOpcionDao accesoOpcionDao;
	 
	
	
	@RequestMapping("/salida/grupos/grupos")
	public String salidaGruposGrupos(HttpServletRequest request, Model modelo){				
		
		List<SalGrupo> lisGrupos 				= salGrupoDao.getListAll(" ORDER BY GRUPO_ID");
		HashMap<String,String> mapaGrupos		= salSolicitudDao.mapaPorGrupo();
		
		modelo.addAttribute("lisGrupos", lisGrupos);		
		modelo.addAttribute("mapaGrupos",mapaGrupos);		
		
		return "salida/grupos/grupos";
	}
	
	@RequestMapping("/salida/grupos/accesoPermiso")
	public String salidaGruposAccesoPermiso(HttpServletRequest request, Model modelo){
				
		List<SalGrupo> lisGrupos 	= salGrupoDao.getListAll(" ORDER BY GRUPO_ID");
		String mensaje 				= "-";		
		int borrados 	= accesoOpcionDao.deletePorOpcion("753");
		int grabados	= 0;			
		for(SalGrupo grupo: lisGrupos){
			String [] arr = grupo.getUsuarios().split("-");
			for(String str: arr){
				if (accesoOpcionDao.existeReg(str, "753")==false) {
					AccesoOpcion accesoOpcion = new AccesoOpcion();		
					accesoOpcion.setCodigoPersonal(str);
					accesoOpcion.setOpcionId("753");				
					accesoOpcion.setFecha(aca.util.Fecha.getHoy());
					accesoOpcion.setUsuario("9800308");
					if (str.length()==7 && accesoOpcionDao.insertReg(accesoOpcion)){
						grabados++;
					}
				}				 
			}
		}		
		if (grabados >= 1) {
			mensaje = "<div class='alert alert-success'>Se actualizaron:"+grabados+" registros</div>";
		}else{
			mensaje = "<div class='alert alert-danger'>Ocurrió un error al actualizar los privilegios</div>";
		}
		
		return "redirect:/salida/grupos/grupos?Mensaje="+mensaje;
	}
	
	@RequestMapping("/salida/grupos/accesoSolicitud")
	public String salidaGruposAccesoSolicitud(HttpServletRequest request, Model modelo){	
		
		String mensaje 				= "-";		
		int grabados 	= 0;
		List<Usuarios> lisEmpleados = usuariosDao.lisPorPrefijo("98", "");			
		for(Usuarios usuario: lisEmpleados){
			if (accesoOpcionDao.existeReg(usuario.getCodigoPersonal(), "751") == false) {
				AccesoOpcion accesoOpcion = new AccesoOpcion();		
				accesoOpcion.setCodigoPersonal(usuario.getCodigoPersonal());
				accesoOpcion.setOpcionId("751");				
				accesoOpcion.setFecha(aca.util.Fecha.getHoy());
				accesoOpcion.setUsuario("9800308");
				if (accesoOpcionDao.insertReg(accesoOpcion)){
					grabados++;
				}
			}
		}
		if (grabados >= 1) {
			mensaje = "<div class='alert alert-success'>Se agrego el privilegio a :"+grabados+" nuevos empleado(s)</div>";
		}else{
			mensaje = "<div class='alert alert-danger'>¡No se egregaron nuevos privilegios!</div>";
		}
		
		return "redirect:/salida/grupos/grupos?Mensaje="+mensaje;
	}
	
	@RequestMapping("/salida/grupos/accion")
	public String salidaGruposAccion(HttpServletRequest request, Model modelo){
		String grupoId 		= request.getParameter("GrupoId")==null?"0":request.getParameter("GrupoId");
		List<Maestros> lisMaestros = maestrosDao.getListAll(" ORDER BY NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO");
		SalGrupo salGrupo = new SalGrupo();
		if (salGrupoDao.existeReg(grupoId)){
			salGrupo = salGrupoDao.mapeaRegId(grupoId); 
		}
		
		modelo.addAttribute("salGrupo", salGrupo);
		modelo.addAttribute("lisMaestros", lisMaestros);
		
		return "salida/grupos/accion";
	}	
	
	@RequestMapping("/salida/grupos/grabar")
	public String salidaGruposGrabar(HttpServletRequest request){
		
		SalGrupo salGrupo 	= new SalGrupo();		
		String grupoId 		= request.getParameter("GrupoId")==null?"0":request.getParameter("GrupoId");		
		salGrupo.setGrupoNombre(request.getParameter("NombreGrupos")==null?"-":request.getParameter("NombreGrupos"));
		salGrupo.setResponsable(request.getParameter("Responsable")==null?"-":request.getParameter("Responsable"));		
		salGrupo.setCorreo(request.getParameter("Correo")==null?"-":request.getParameter("Correo"));
		salGrupo.setUsuarios(request.getParameter("Usuarios")==null?"-":request.getParameter("Usuarios"));
		String mensaje 		= "-";
		
		if (salGrupoDao.existeReg(grupoId) == false) {
			grupoId		= salGrupoDao.maximoReg();
			salGrupo.setGrupoId(grupoId);
			if (salGrupoDao.insertReg(salGrupo)) {
				mensaje = "Registro grabado: " + grupoId;
			} else {
				mensaje = "Error al grabar: " + grupoId;
			}
		} else {
			salGrupo.setGrupoId(grupoId);
			if (salGrupoDao.updateReg(salGrupo)) {
				mensaje = "Registro modificado: " + grupoId;
			} else {
				mensaje = "Error al modificar: " + grupoId;
			}
		}		
		return "redirect:/salida/grupos/accion?GrupoId="+grupoId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/salida/grupos/borrar")
	public String salidaGruposBorrar(HttpServletRequest request){
		
				
		String grupoId 		= request.getParameter("GrupoId")==null?"0":request.getParameter("GrupoId");		
		String mensaje 		= "-";
		
		if (salGrupoDao.existeReg(grupoId)) {			
			if (salGrupoDao.deleteReg(grupoId)) {
				mensaje = "Registro emliminado: " + grupoId;
			} else {
				mensaje = "No se pudo borrar: " + grupoId;
			}
		} 	
		return "redirect:/salida/grupos/grupos?Mensaje="+mensaje;
	}
	
	@RequestMapping("/salida/grupos/testAgenda")
	public String salidaGruposTestAgenda(HttpServletRequest request){
		//enviarConEnoc(request,"Error-aca.ContSalidaGrupos|salidaGruposTestAgenda:");
		return "salida/grupos/testAgenda";
	}	
	
}