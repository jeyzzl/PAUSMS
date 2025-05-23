package aca.web.candados;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.candado.spring.CandTipo;
import aca.candado.spring.CandTipoDao;
import aca.candado.spring.Candado;
import aca.candado.spring.CandadoDao;
import aca.acceso.spring.AccesoDao;
import aca.candado.spring.CandPermiso;
import aca.candado.spring.CandPermisoDao;
import aca.emp.spring.EmpDatosDao;
import aca.emp.spring.EmpMaestro;
import aca.emp.spring.EmpMaestroDao;
import aca.vista.spring.UsuariosDao;

@Controller
public class ContCandadosCatalogo {
	
	
	@Autowired	
	private UsuariosDao usuariosDao;
	
	@Autowired	
	private CandadoDao candadoDao;
	
	@Autowired	
	private CandTipoDao candTipoDao;
	
	@Autowired
	private CandPermisoDao candPermisoDao;
	
	@Autowired
	private EmpMaestroDao empMaestroDao;

	@Autowired
	private EmpDatosDao empDatosDao;
	
	@Autowired
	private AccesoDao accesoDao;
	
	
	@RequestMapping("/candados/catalogo/candados")
	public String candadosCatalogoCandados(HttpServletRequest request, Model modelo){
		
		String codigoPersonal	= "0";
		String usuarioNombre 	= "-";
		boolean tieneAcceso		= false;
		boolean esAdmin			= false;
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion != null){			
			codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");
			usuarioNombre 		= usuariosDao.getNombreUsuario(codigoPersonal, "NOMBRE");
			tieneAcceso 		= candPermisoDao.esResponsable(codigoPersonal);
			esAdmin				= accesoDao.esAdministrador(codigoPersonal);
		}
		
		List<CandTipo> lisTipos = new ArrayList<>();
		
		HashMap<String, String> mapaPermisosPorTipo = candPermisoDao.mapaPermisosPorTipo();
		HashMap<String, String> mapaCandadosPorTipo = candTipoDao.mapaCandadosPorTipo();
		
		if(esAdmin) {
			lisTipos = candTipoDao.getLista(" ORDER BY TIPO_ID");
		}
		
		modelo.addAttribute("usuarioNombre", usuarioNombre);
		modelo.addAttribute("esAdmin", esAdmin);
		modelo.addAttribute("lisTipos", lisTipos);
		modelo.addAttribute("mapaPermisosPorTipo", mapaPermisosPorTipo);
		modelo.addAttribute("mapaCandadosPorTipo", mapaCandadosPorTipo);
		
		return "candados/catalogo/candados";
	}
	
	@RequestMapping("/candados/catalogo/cand_tipo")
	public String candadosCatalogoCandTipo(HttpServletRequest request, Model modelo){	
		
		String codigoPersonal	= "0";
		String tipoId			= request.getParameter("TipoId")==null?"0":request.getParameter("TipoId");
		
		CandTipo candTipo 		= new CandTipo();
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");
		}

		if (candTipoDao.existeReg(tipoId)){
			candTipo 		= candTipoDao.mapeaRegId(tipoId);			
		}else {
			candTipo.setTipoId(candTipoDao.maximoReg());
		}

		String nombre 			= usuariosDao.getNombreUsuario(codigoPersonal,"NOMBRE");
		
		modelo.addAttribute("codigoPersonal", codigoPersonal);
		modelo.addAttribute("nombre", nombre);
		modelo.addAttribute("candTipo", candTipo);
				
		return "candados/catalogo/cand_tipo";
	}

	@RequestMapping("/candados/catalogo/grabarCandado")
	public String candadosCatalogoGrabarCandadoTipo(HttpServletRequest request, Model model){
		
		String tipoId 			= request.getParameter("TipoId")==null?"0":request.getParameter("TipoId");
		String lugar 			= request.getParameter("Lugar")==null?"-":request.getParameter("Lugar");
		String nombre			 = request.getParameter("Nombre")==null?"-":request.getParameter("Nombre");
		String tel			 	= request.getParameter("Tel")==null?"-":request.getParameter("Tel");
//		String responsable	 	= request.getParameter("Responsable")==null?"-":request.getParameter("Responsable");
		String estado		 	= request.getParameter("Estado")==null?"-":request.getParameter("Estado");
		String mensaje 			= "-"; 
		
		CandTipo candTipo 		= new CandTipo();
		
		candTipo.setLugar(lugar);
		candTipo.setNombreTipo(nombre);
		candTipo.setTelefono(tel);
//		candTipo.setResponsable(responsable);
		candTipo.setEstado(estado);
		if (candTipoDao.existeReg(tipoId)) {
			candTipo.setTipoId(tipoId);
			if (candTipoDao.updateReg(candTipo)) {
				mensaje = "1";
			}else {
				mensaje	= "2";
			}
		}else{
			candTipo.setTipoId(candTipoDao.maximoReg());
			if (candTipoDao.insertReg(candTipo)) {
				mensaje = "1";
			}else {
				mensaje	= "2";			
			}
		}
	
		return "redirect:/candados/catalogo/cand_tipo?TipoId="+candTipo.getTipoId()+"&Mensaje="+mensaje;
	}

	@RequestMapping("/candados/catalogo/borrarTipo")
	public String candadosCatalogoBorrarCandadoTipo(HttpServletRequest request, Model model){
		String tipoId 			= request.getParameter("TipoId")==null?"0":request.getParameter("TipoId");
		String mensaje 			= "-"; 

		if (candTipoDao.existeReg(tipoId)) {
			if (candTipoDao.deleteReg(tipoId)) {
				mensaje = "1";
			}else {
				mensaje	= "2";
			}
		}
	
		return "redirect:/candados/catalogo/candados?Mensaje="+mensaje;
	}
	
	@RequestMapping("/candados/catalogo/alta_candado")
	public String candadosCatalogoAltaCandado(HttpServletRequest request, Model modelo){	
		
		String tipoId				= request.getParameter("TipoId");
		String nombreTipo			= candTipoDao.getNombreTipo(tipoId);
		
		List<Candado> lisCandado	= candadoDao.getLista( tipoId, "ORDER BY CANDADO_ID");
		
		modelo.addAttribute("nombreTipo", nombreTipo);
		modelo.addAttribute("lisCandado", lisCandado);
		
		return "candados/catalogo/alta_candado";
	}
	
	@RequestMapping("/candados/catalogo/editarCandado")
	public String candadosCatalogoEditarCandado(HttpServletRequest request, Model modelo){	
		
		String tipoId				= request.getParameter("TipoId");
		String candadoId			= request.getParameter("CandadoId");
		String nombreCandado		= candTipoDao.getNombreTipo(tipoId);
		String codigoPersonal		= "-";
		String nombreEmpleado		= "-";	
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion != null){			
			codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");
			nombreEmpleado 		= usuariosDao.getNombreUsuario(codigoPersonal, "NOMBRE");
		}
		
		Candado candado = new Candado();
		
		if(candadoDao.existeReg(candadoId, tipoId)) {
			candado = candadoDao.mapeaRegId(candadoId);
		}else {
			candado.setCandadoId(candadoDao.maximoReg(tipoId));
		}
		
		modelo.addAttribute("tipoId", tipoId);
		modelo.addAttribute("candadoId", candadoId);
		modelo.addAttribute("nombreCandado", nombreCandado);
		modelo.addAttribute("codigoPersonal", codigoPersonal);
		modelo.addAttribute("nombreEmpleado", nombreEmpleado);
		modelo.addAttribute("candado", candado);
		
		return "candados/catalogo/editarCandado";
	}
	
	@RequestMapping("/candados/catalogo/grabar")
	public String candadosCatalogoGrabarCandado(HttpServletRequest request, Model model){
		
		String tipoId 				= request.getParameter("TipoId")==null?"0":request.getParameter("TipoId");
		String candadoId 			= request.getParameter("CandadoId")==null?"0":request.getParameter("CandadoId");
		String nombreCandado 		= request.getParameter("NombreCandado")==null?"-":request.getParameter("NombreCandado");
		String mensaje				=	 "";
		
		Candado candado	= new Candado();
		candado.setTipoId(tipoId);
		candado.setNombreCandado(nombreCandado);

		if (candadoDao.existeReg(candadoId, tipoId)) {
			candado.setCandadoId(candadoId);
			if (candadoDao.updateReg(candado)) {
				mensaje = "Updated";
			}else {
				mensaje = "Error updating";
			}
		}else{
			candado.setCandadoId(candadoDao.maximoReg(tipoId));
			if (candadoDao.insertReg(candado)) {
				mensaje = "Saved";
			}else {		
				mensaje = "Error saving";
			}
		}
	
		return "redirect:/candados/catalogo/alta_candado?TipoId="+tipoId;
	}

	@RequestMapping("/candados/catalogo/borrarCandado")
	public String candadosCatalogoBorrarCandado(HttpServletRequest request, Model model){
		String tipoId 				= request.getParameter("TipoId")==null?"0":request.getParameter("TipoId");
		String candadoId 			= request.getParameter("CandadoId")==null?"0":request.getParameter("CandadoId");
		String mensaje				=	 "";
		
		if (candadoDao.existeReg(candadoId, tipoId)) {
			if (candadoDao.deleteReg(candadoId, tipoId)) {
				mensaje = "Deleted";
			}else {
				mensaje = "Could not Delete"; 
			}
		}else{
			mensaje= "Does not Exist";
		}
	
		return "redirect:/candados/catalogo/alta_candado?TipoId="+tipoId+"&Mensaje="+mensaje;
	}

	@RequestMapping("/candados/catalogo/cand_permiso")
	public String candadosCatalogoCandPermiso(HttpServletRequest request, Model modelo) {
		
		String codigoPersonal	= "0";
		String tipoId			= request.getParameter("TipoId")==null?"0":request.getParameter("TipoId");
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");
		}
		
		List<EmpMaestro> lisEmpleados = empMaestroDao.getListAll(" ORDER BY CODIGO_PERSONAL, APELLIDO_PATERNO"); 
		List<CandPermiso> lisPermisos = candPermisoDao.getListaPorTipo(tipoId, " ORDER BY TIPO_ID");
		
		HashMap<String, EmpMaestro> mapaMaestros = empMaestroDao.mapaEmpMaestro();
		
		modelo.addAttribute("TipoId", tipoId);
		modelo.addAttribute("lisEmpleados", lisEmpleados);
		modelo.addAttribute("lisPermisos", lisPermisos);
		modelo.addAttribute("mapaMaestros", mapaMaestros);
		
		return "candados/catalogo/cand_permiso";
		
	}
	
	@RequestMapping("/candados/catalogo/grabarPermiso")
	public String candadosCatalogoGrabarPermiso(HttpServletRequest request, Model model){
		
		String usuario 		= "";
		String tipoId 				= request.getParameter("tipoId")==null?"0":request.getParameter("tipoId");
		String codigoPersonal 		= request.getParameter("codigoPersonal")==null?"0":request.getParameter("codigoPersonal");
		String mensaje				=	 "";
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			usuario 		= (String) sesion.getAttribute("codigoPersonal");
		}
		
		CandPermiso candPermiso = new CandPermiso();
		
		String fecha = aca.util.Fecha.getHoy();
		
		candPermiso.setCodioPersonal(codigoPersonal);
		candPermiso.setTipoId(tipoId);
		candPermiso.setUsAlta(usuario);
		candPermiso.setFechaAlta(fecha);
		if (candPermisoDao.existeReg(tipoId, codigoPersonal)) {
			if (candPermisoDao.updateReg(candPermiso)) {
				mensaje = "Updated";
			}else {
				mensaje = "Could not Update";
			}
		}else{
			if (candPermisoDao.insertReg(candPermiso)) {
				mensaje = "Saved";
			}else {		
				mensaje = "Could not Save";
			}
		}
	
		return "redirect:/candados/catalogo/cand_permiso?TipoId="+tipoId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/candados/catalogo/borrarPermiso")
	public String candadosCatalogoBorrarPermiso(HttpServletRequest request, Model model){
		String tipoId 				= request.getParameter("tipoId")==null?"0":request.getParameter("tipoId");
		String codigoPersonal 		= request.getParameter("codigoPersonal")==null?"0":request.getParameter("codigoPersonal");
		String mensaje				=	 "";
		
		if (candPermisoDao.existeReg(tipoId, codigoPersonal)) {
			if (candPermisoDao.deleteReg(tipoId, codigoPersonal)) {
				mensaje = "Deleted";
			}else {
				mensaje = "Could not Delete"; 
			}
		}else{
			mensaje= "Does not Exist";
		}
	
		return "redirect:/candados/catalogo/cand_permiso?TipoId="+tipoId+"&Mensaje="+mensaje;
	}
}