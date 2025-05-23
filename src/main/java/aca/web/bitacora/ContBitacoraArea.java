package aca.web.bitacora;

import java.util.Arrays;
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

import aca.bitacora.spring.BitArea;
import aca.bitacora.spring.BitAreaUsuario;
import aca.vista.spring.Usuarios;

@Controller
public class ContBitacoraArea {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;

	@Autowired
	aca.bitacora.spring.BitAreaDao bitAreaDao;
	
	@Autowired
	aca.bitacora.spring.BitAreaUsuarioDao bitAreaUsuarioDao;
	
	@Autowired
	aca.vista.spring.UsuariosDao usuariosDao;
	
	@Autowired
	aca.vista.spring.MaestrosDao maestrosDao;
	
	@RequestMapping("/bitacora/area/area")
	public String bitacoraAreaArea(HttpServletRequest request, Model modelo){
		List<BitArea> lisAreas							= bitAreaDao.lisAreas("ORDER BY AREA_NOMBRE");
		HashMap <String, String> mapaMaestros			= maestrosDao.mapMaestroNombre("APELLIDOS");
		HashMap <String, String> mapaUsuariosPorArea	= bitAreaUsuarioDao.mapaUsuariosPorArea();
		
		modelo.addAttribute("lisAreas", lisAreas);
		modelo.addAttribute("mapaMaestros", mapaMaestros);
		modelo.addAttribute("mapaUsuariosPorArea", mapaUsuariosPorArea);
		
		return "bitacora/area/area";
	}	
	
	@RequestMapping("/bitacora/area/editar")
	public String bitacoraAreaEditar(HttpServletRequest request, Model modelo){
		String areaId 		= request.getParameter("AreaId")==null?"0":request.getParameter("AreaId");
		BitArea area	 	= new BitArea();
		
		if (bitAreaDao.existeReg(areaId)) {
			area = bitAreaDao.mapeaRegId(areaId);
		}
		modelo.addAttribute("area", area);		
		return "bitacora/area/editar";
	}
	
	@RequestMapping("/bitacora/area/agregarAcceso")
	public String bitacoraAreaAgregarAcceso(HttpServletRequest request){
		String areaId 			= request.getParameter("AreaId")==null?"0":request.getParameter("AreaId");
		String codigoActual     = "0";
		
		BitAreaUsuario areaUsuario	 		= new BitAreaUsuario();
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoActual		= (String) sesion.getAttribute("codigoUltimo");
		}
		
		if (!bitAreaUsuarioDao.existeReg(areaId, codigoActual)) {
			areaUsuario.setAreaId(areaId);
			areaUsuario.setCodigoPersonal(codigoActual);
			bitAreaUsuarioDao.insertReg(areaUsuario);
		}
		
		return "redirect:/bitacora/area/acceso?AreaId="+areaId;
	}
	
	@RequestMapping("/bitacora/area/quitarAcceso")
	public String bitacoraAreaQuitarAcceso(HttpServletRequest request){
		String areaId 	= request.getParameter("AreaId")==null?"0":request.getParameter("AreaId");
		String usuario 	= request.getParameter("Usuario")==null?"0":request.getParameter("Usuario");
		
		if (bitAreaUsuarioDao.existeReg(areaId, usuario)) {
			bitAreaUsuarioDao.deleteReg(areaId, usuario);
		}
		
		return "redirect:/bitacora/area/acceso?AreaId="+areaId;
	}
	
	@RequestMapping("/bitacora/area/acceso")
	public String bitacoraAreaAcceso(HttpServletRequest request, Model modelo){
		String areaId 			= request.getParameter("AreaId")==null?"0":request.getParameter("AreaId");
		String codigoActual     = "0";
		String nombreActual		= "";
		
		BitArea area	 		= new BitArea();
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoActual		= (String) sesion.getAttribute("codigoUltimo");
		}

		nombreActual			= usuariosDao.getNombreUsuario(codigoActual, "NOMBRE");
		
		List <String> lisAreaUsuarios = bitAreaUsuarioDao.lisAreasUsuarios(areaId);
		
		String codigos = "\'\'";
		if(lisAreaUsuarios.size() > 0) {
			codigos = "";
			for(String usuario : lisAreaUsuarios){
				codigos = codigos+"\'"+usuario+"\'";
			}
			codigos = codigos.replace("\'\'", "\',\'");
		}
		
		HashMap <String, String> mapaUsuarios = usuariosDao.mapCandidatos(codigos);
		
		if (bitAreaDao.existeReg(areaId)) {
			area = bitAreaDao.mapeaRegId(areaId);
		}
		
		
		modelo.addAttribute("area", area);
		modelo.addAttribute("lisAreaUsuarios", lisAreaUsuarios);
		modelo.addAttribute("nombreActual", nombreActual);
		modelo.addAttribute("mapaUsuarios", mapaUsuarios);
		return "bitacora/area/acceso";
	}
	
	@RequestMapping("/bitacora/area/grabar")
	public String bitacoraAreaGrabar(HttpServletRequest request, Model modelo){
		String areaId 		= request.getParameter("AreaId")==null?"0":request.getParameter("AreaId");
		String nombre 		= request.getParameter("Nombre")==null?"-":request.getParameter("Nombre");
		String responsable 	= request.getParameter("Responsable")==null?"-":request.getParameter("Responsable");

		BitArea area	= new BitArea();
		area.setAreaNombre(nombre);
		area.setResponsable(responsable);
		
		if (bitAreaDao.existeReg(areaId)) {
			area.setAreaId(areaId);
			bitAreaDao.updateReg(area);
		} else {
			area.setAreaId(bitAreaDao.maximoReg());
			bitAreaDao.insertReg(area);
		}
		
		return "redirect:/bitacora/area/editar?AreaId="+area.getAreaId();
	}
	
	@RequestMapping("/bitacora/area/borrar")
	public String bitacoraAreaBorrar(HttpServletRequest request, Model modelo){
		String areaId 	= request.getParameter("AreaId")==null?"0":request.getParameter("AreaId");
		
		if (bitAreaDao.existeReg(areaId)) {
			bitAreaDao.deleteReg(areaId);
		}
		
		return "redirect:/bitacora/area/area";
	}
}