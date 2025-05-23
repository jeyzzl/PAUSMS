package aca.web.mentores;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.mentores.spring.MentAcceso;
import aca.mentores.spring.MentAccesoDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.MaestrosDao;
import aca.vista.spring.UsuariosDao;

@Controller
public class ContMentoresReferente {	
	
	@Autowired
	MentAccesoDao mentAccesoDao;
	
	@Autowired
	MaestrosDao maestrosDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	CatFacultadDao catFacultadDao;
	
	@Autowired
	UsuariosDao usuariosDao;
	
	@Autowired
	MapaPlanDao mapaPlanDao;
	
	@RequestMapping("/mentores/referente/referente")
	public String mentoresReferenteReferente(HttpServletRequest request, Model modelo){
		List<MentAcceso> lisAccesos				= mentAccesoDao.getListAll(" ORDER BY ENOC.EMP_APELLIDO(CODIGO_PERSONAL)");
		HashMap<String,String> mapaEmpleados	= maestrosDao.mapaEmpleadosReferentes("APELLIDOS");
		
		modelo.addAttribute("lisAccesos", lisAccesos);
		modelo.addAttribute("mapaEmpleados", mapaEmpleados);
		
		return "mentores/referente/referente";
	}
	
	@RequestMapping("/mentores/referente/grabar")
	public String mentoresReferenteGrabar(HttpServletRequest request, Model modelo){		
		String codigoPersonal 	= request.getParameter("CodigoPersonal")==null?"0":request.getParameter("CodigoPersonal");
		String mensaje			= "-"; 
		MentAcceso mentAcceso	= new MentAcceso();
		if (mentAccesoDao.existeReg(codigoPersonal)==false) {
			mentAcceso.setCodigoPersonal(codigoPersonal);
			mentAcceso.setAcceso("X");
			if (mentAccesoDao.insertReg(mentAcceso)){
				mensaje = "Saved!";
			}else {
				mensaje = "Error Saving!";
			}
		}
		
		return "redirect:/mentores/referente/referente?Mensaje="+mensaje;
	}
	
	@RequestMapping("/mentores/referente/borrar")
	public String mentoresReferenteBorrar(HttpServletRequest request, Model modelo){		
		String codigoPersonal 	= request.getParameter("CodigoPersonal")==null?"0":request.getParameter("CodigoPersonal");
		String mensaje			= "-"; 
		
		if (mentAccesoDao.existeReg(codigoPersonal)) {
			if (mentAccesoDao.deleteReg(codigoPersonal)){
				mensaje = "Deleted!";
			}else {
				mensaje = "Error deleting!";
			}
		}
		
		return "redirect:/mentores/referente/referente?Mensaje="+mensaje;
	}
	
	@RequestMapping("/mentores/referente/privilegios")
	public String mentoresReferentePrivilegios(HttpServletRequest request, Model modelo){
		String codigoEmpleado 				= request.getParameter("Codigo")==null?"0":request.getParameter("Codigo");
		
		List<MapaPlan> lisPlanes 					= mapaPlanDao.getListAll("ORDER BY PLAN_ID");
		List<CatCarrera> lisCarreras				= catCarreraDao.getListAll(" ORDER BY FACULTAD_ID, NOMBRE_CARRERA");
		
		HashMap<String,CatFacultad> mapaFacultades	= catFacultadDao.getMapFacultad("");
		
		MentAcceso mentAcceso				= new MentAcceso();
		String empleadoNombre				= "-";
		if (mentAccesoDao.existeReg(codigoEmpleado)) {
			empleadoNombre 	= usuariosDao.getNombreUsuario(codigoEmpleado, "NOMBRE");
			mentAcceso 		= mentAccesoDao.mapeaRegId(codigoEmpleado);
		}
		
		modelo.addAttribute("mentAcceso", mentAcceso);
		modelo.addAttribute("empleadoNombre", empleadoNombre);
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("lisCarreras", lisCarreras);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		
		return "mentores/referente/privilegios";
	}
	
	@RequestMapping("/mentores/referente/grabarPrivilegios")
	public String mentoresReferenteGrabarPrivilegios(HttpServletRequest request, Model modelo){
		String codigoEmpleado 				= request.getParameter("Codigo")==null?"0":request.getParameter("Codigo");
		List<CatCarrera> lisCarreras		= catCarreraDao.getListAll(" ORDER BY FACULTAD_ID, NOMBRE_CARRERA");
		MentAcceso mentAcceso				= new MentAcceso();
		String strAcceso 					= "";
		
		int row=0;
		for(CatCarrera carrera : lisCarreras){
			row++;
			String strCheck	= request.getParameter("Check"+row); 
			if(strCheck==null){ strCheck="X"; }
			if(!strCheck.equals("X")){
				strAcceso += carrera.getCarreraId()+" ";
			}
		}
		
		mentAcceso.setCodigoPersonal(codigoEmpleado);
		mentAcceso.setAcceso(strAcceso);
		if (mentAccesoDao.existeReg(codigoEmpleado)){
			if (mentAccesoDao.updateReg(mentAcceso)){				
			}
		}else{
			if (mentAccesoDao.insertReg(mentAcceso)){					
			}
		}
		
		return "redirect:/mentores/referente/privilegios?Codigo="+codigoEmpleado;
	}
}