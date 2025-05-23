package aca.web.hca;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.acceso.spring.AccesoDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.hca.spring.HcaMaestro;
import aca.hca.spring.HcaMaestroDao;
import aca.vista.spring.MaestrosDao;
import aca.vista.spring.UsuariosDao;

@Controller
public class ContHcaMaestro {	
	
	@Autowired
	AccesoDao accesoDao;
	
	@Autowired
	MaestrosDao maestrosDao;
	
	@Autowired
	CatFacultadDao catFacultadDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	HcaMaestroDao hcaMaestroDao;
	
	@Autowired
	UsuariosDao usuariosDao;
	
	
	@RequestMapping("/hca/maestro/facultad")
	public String hcaMaoestroFacultad(HttpServletRequest request, Model modelo){
		
		String codigoPersonal	= "0";		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();		
		if (sesion!=null){		
			codigoPersonal 		= sesion.getAttribute("codigoPersonal")==null?"0":(String)sesion.getAttribute("codigoPersonal");
		}	
		boolean esAdmin			= accesoDao.esAdministrador(codigoPersonal);
		boolean esSuper			= accesoDao.esSupervisor(codigoPersonal);
		String accesos 			= accesoDao.getAccesos(codigoPersonal);
		String carreras[]		= accesos.split(" ");		
		List<String> lisPermisos	= new ArrayList<String>();
		for(String carrera : carreras) {
			String facultad = catCarreraDao.getFacultadId(carrera);
			if (!lisPermisos.contains(facultad)) lisPermisos.add(facultad);
		}
		
		List<CatFacultad> lisFacultades 		= catFacultadDao.getListAll("ORDER BY 1");
		HashMap<String,String> mapaMaestros 	= maestrosDao.mapaDirectores();		
		
		modelo.addAttribute("esAdmin", esAdmin);
		modelo.addAttribute("esSuper", esSuper);
		modelo.addAttribute("lisFacultades", lisFacultades);
		modelo.addAttribute("lisPermisos", lisPermisos);
		modelo.addAttribute("mapaMaestros", mapaMaestros);

		return "hca/maestro/facultad";
	}	
	
	@RequestMapping("/hca/maestro/maestro")
	public String hcaMaestroMaestro(HttpServletRequest request, Model modelo){
		String codigoEmpleado	= "0";
		String empleadoNombre 	= "-";
		String facultadId 		= request.getParameter("FacultadId")==null?"0":request.getParameter("FacultadId");
		String carreraId 		= request.getParameter("CarreraId")==null?"0":request.getParameter("CarreraId");
		String maestroId		= request.getParameter("MaestroId")==null?"0":request.getParameter("MaestroId");
		String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		String facultadNombre	= catFacultadDao.getNombreFacultad(facultadId);
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();		
		if (sesion!=null){		
			codigoEmpleado 		= (String) sesion.getAttribute("codigoEmpleado");
			empleadoNombre 		= usuariosDao.getNombreUsuario(codigoEmpleado, "NOMBRE");
		}		
		HcaMaestro maestro = new HcaMaestro();		
		
		if (accion.equals("1")){
			maestro.setCodigoPersonal(codigoEmpleado);
			maestro.setFacultadId(facultadId);
			maestro.setCarreraId(carreraId);
			maestro.setEstado("B");
			if (hcaMaestroDao.existeReg(codigoEmpleado)){ 
				if (hcaMaestroDao.updateReg(maestro)){					
				}									
			}else{
				if (hcaMaestroDao.insertReg(maestro)){					
				}					
			}			
		}		
		if (accion.equals("2")){
			maestro.setCodigoPersonal(request.getParameter("MaestroId"));
			if (hcaMaestroDao.existeReg(maestroId)){
				if (hcaMaestroDao.deleteReg(maestroId)){ 
				}					
			}			
		}	
		List<HcaMaestro> lisMaestros 		= hcaMaestroDao.lisPorFacultad(facultadId," ORDER BY ENOC.CARRERA_NIVEL(CARRERA_ID), ENOC.NOMBRE_CARRERA(CARRERA_ID), ENOC.EMP_APELLIDO(CODIGO_PERSONAL)");		
		List<CatCarrera> lisCarreras 		= catCarreraDao.getListCarrera(facultadId,"ORDER BY NOMBRE_CARRERA");
		
		HashMap<String,String> mapaMaestros 		= maestrosDao.mapaMaestroEnBase("NOMBRE");
		HashMap<String,CatCarrera> mapaCarreras 	= catCarreraDao.getMapAll("");
		
		modelo.addAttribute("empleadoNombre", empleadoNombre);
		modelo.addAttribute("facultadNombre", facultadNombre);
		modelo.addAttribute("lisMaestros", lisMaestros);
		modelo.addAttribute("lisCarreras", lisCarreras);
		modelo.addAttribute("mapaMaestros", mapaMaestros);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		
		return "hca/maestro/maestro";
	}
	
}