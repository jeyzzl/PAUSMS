package aca.web.clinicos;
 import java.util.HashMap;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.catalogo.spring.CatFacultadDao;
import aca.graduacion.spring.AlumEgresoDao;
import aca.graduacion.spring.AlumEvento;
import aca.rotaciones.spring.RotHospitalDao;
import aca.rotaciones.spring.RotInstitucion;
import aca.rotaciones.spring.RotInstitucionDao;

@Controller
public class ContClinicosInstituciones {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	RotInstitucionDao rotInstitucionDao;
	
	@Autowired
	RotHospitalDao rotHospitalDao;
	
	@RequestMapping("/clinicos/institucion/institucion")
	public String clinicosInstitucionInstitucion(HttpServletRequest request, Model modelo){
		
		List<RotInstitucion> instituciones 			= rotInstitucionDao.getListAll("ORDER BY 1 ");
		HashMap<String,String> mapaHospitales		= rotHospitalDao.mapaTotHospitales();
		
		modelo.addAttribute("instituciones",instituciones);
		modelo.addAttribute("mapaHospitales",mapaHospitales);
		
		return "clinicos/institucion/institucion";
	}
	
	@RequestMapping("/clinicos/institucion/editar")
	public String clinicosInstitucionEditar(HttpServletRequest request, Model modelo){
		
		String institucionId					= request.getParameter("InstitucionId")==null?"0":request.getParameter("InstitucionId");
		RotInstitucion rotInstitucion			= new RotInstitucion();		
		
		if (rotInstitucionDao.existeReg(institucionId)) {
			rotInstitucion = rotInstitucionDao.mapeaRegId(institucionId) ;
		}
		
		modelo.addAttribute("rotInstitucion", rotInstitucion);
		
		return "clinicos/institucion/editar";
		
	}
	
	@RequestMapping("/clinicos/institucion/grabarInstitucion")
	public String graduacionEventosGrabarInstitucion(HttpServletRequest request, Model model){		
		String institucionId			= request.getParameter("InstitucionId")==null?"0":request.getParameter("InstitucionId");
		String institucionNombre		= request.getParameter("Nombre")==null?"0":request.getParameter("Nombre");
		String mensaje 					= "-"; 
		
		RotInstitucion rotInstitucion		= new RotInstitucion();		
		if (rotInstitucionDao.existeReg(institucionId)) {
			rotInstitucion.setInstitucionId(institucionId);
			rotInstitucion.setInstitucionNombre(institucionNombre);
			if (rotInstitucionDao.updateReg(rotInstitucion)) {
				mensaje = "Modificado...";
			}else {
				mensaje = "Error al modificar...";
			}
		}else{
			rotInstitucion.setInstitucionId(rotInstitucionDao.maximoReg());
			rotInstitucion.setInstitucionNombre(institucionNombre);
			if (rotInstitucionDao.insertReg(rotInstitucion)) {
				mensaje = "Grabado...";
			}else {
				mensaje = "Error al grabar...";
			}
		}	
		
		return "redirect:/clinicos/institucion/institucion?Mensaje="+mensaje;
	}
	
	@RequestMapping("/clinicos/institucion/borrarInstitucion")
	public String clinicosInstitucionBorrarInstitucion(HttpServletRequest request, Model model){
		
		String institucionId		= request.getParameter("InstitucionId")==null?"0":request.getParameter("InstitucionId");
		String mensaje 				= "-"; 
		if (rotInstitucionDao.existeReg(institucionId)) {
			if (rotInstitucionDao.deleteReg(institucionId)) {
				mensaje = "Borrado...";
			}else {
				mensaje = "Error al borrar...";
			}
		}
		
		return "redirect:/clinicos/institucion/institucion?Mensaje="+mensaje;
	}
	
}