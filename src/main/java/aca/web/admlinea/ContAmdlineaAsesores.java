package aca.web.admlinea;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import aca.admision.spring.AdmAsesor;
import aca.admision.spring.AdmAsesorDao;
import aca.emp.spring.EmpMaestro;
import aca.emp.spring.EmpMaestroDao;
import aca.vista.spring.Maestros;
import aca.vista.spring.MaestrosDao;



@Controller
public class ContAmdlineaAsesores {
	
	@Autowired
	private AdmAsesorDao admAsesorDao;
	
	@Autowired
	private MaestrosDao maestrosDao;
	
	@Autowired
	private EmpMaestroDao empMaestroDao;
	
	
	@RequestMapping("/admlinea/asesores/asesor")
	public String admLineaAsesoresAsesor(HttpServletRequest request, Model modelo) {
		
		List<AdmAsesor> lisAsesores = admAsesorDao.lisAsesores();
		
		HashMap<String, String> mapEmpleadoNombre = maestrosDao.mapaMaestroCorto("NOMBRE");
		
		modelo.addAttribute("lisAsesores",lisAsesores);
		modelo.addAttribute("mapEmpleadoNombre",mapEmpleadoNombre);
		
		return "admlinea/asesores/asesor";
	}
	
	@RequestMapping("/admlinea/asesores/editarAsesor")
	public String admLineaAsesoresEditarAsesor(HttpServletRequest request, Model modelo) {
		
		String asesorId 		= request.getParameter("AsesorId") == null ? "0" : request.getParameter("AsesorId");
		
		AdmAsesor asesor = admAsesorDao.mapeaRegId(asesorId);
		
//		List<Maestros> lisMaestros = maestrosDao.getListAll("");
		List<EmpMaestro> lisMaestros = empMaestroDao.getListAll(" ORDER BY APELLIDO_PATERNO");
		
		modelo.addAttribute("asesor", asesor);
		modelo.addAttribute("lisMaestros", lisMaestros);
		
		
		return "admlinea/asesores/editarAsesor";
	}
	
	@RequestMapping("/admlinea/asesores/grabarAsesor")
	public String admLineaAsesoresGrabarAsesor(HttpServletRequest request, Model modelo) {
		
		String asesorId 		= request.getParameter("AsesorId") == null ? "0" : request.getParameter("AsesorId");
		String email 			= request.getParameter("Email") == null ? "-" : request.getParameter("Email");
		String telefono 		= request.getParameter("Telefono") == null ? "-" : request.getParameter("Telefono");
		String estado 			= request.getParameter("Estado") == null ? "A" : request.getParameter("Estado");
		String mensaje = "-";
		
		AdmAsesor asesor = new AdmAsesor();
		
		asesor.setAsesorId(asesorId);
		asesor.setCorreo(email);
		asesor.setTelefono(telefono);
		asesor.setEstado(estado);

		if(admAsesorDao.existeReg(asesorId)){
			if(admAsesorDao.updateReg(asesor)){
				mensaje = "Updated";
			}else{
				mensaje = "Error updated";
			}
		}else{
			if(admAsesorDao.insertReg(asesor)) {
				mensaje = "Saved";
				
			}else {
				mensaje = "Error saving";
			}
		}
		
		return "redirect:/admlinea/asesores/asesor?mensaje="+mensaje;
	}
	
	@RequestMapping("/admlinea/asesores/borrarAsesor")
	public String admLineaAsesoresBorrarAsesor(HttpServletRequest request, Model modelo) {
		
		String asesorId 		= request.getParameter("AsesorId") == null ? "0" : request.getParameter("AsesorId");
		if(admAsesorDao.existeReg(asesorId)) {
			admAsesorDao.deleteReg(asesorId);
		}	
		
		
		return "redirect:/admlinea/asesores/asesor";
	}

	
}
