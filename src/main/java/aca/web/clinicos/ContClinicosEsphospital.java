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
import org.springframework.web.bind.annotation.ResponseBody;

import aca.rotaciones.spring.RotEspecialidad;
import aca.rotaciones.spring.RotEspecialidadDao;
import aca.rotaciones.spring.RotHospital;
import aca.rotaciones.spring.RotHospitalDao;
import aca.rotaciones.spring.RotHospitalEspecialidad;
import aca.rotaciones.spring.RotHospitalEspecialidadDao;
import aca.rotaciones.spring.RotInstitucion;
import aca.rotaciones.spring.RotInstitucionDao;
import aca.rotaciones.spring.RotProgramacionDao;

@Controller
public class ContClinicosEsphospital {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	RotHospitalDao rotHospitalDao;
	
	@Autowired
	RotEspecialidadDao rotEspecialidadDao;
	
	@Autowired
	RotInstitucionDao rotInstitucionDao;
	
	@Autowired
	RotHospitalEspecialidadDao rotHospitalEspecialidadDao;

	@Autowired
	RotProgramacionDao rotProgramacionDao;
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	
	@RequestMapping("/clinicos/hospital/hospital")
	public String clinicosHospitalHospital(HttpServletRequest request, Model modelo){
		
		List<RotHospital> lisHospitales						 = rotHospitalDao.getListAll( "ORDER BY 1");
		HashMap<String, RotInstitucion> mapaInstituciones	 = rotInstitucionDao.mapaAll();
		HashMap<String, String> mapaTotEspecialidades 		 = rotHospitalEspecialidadDao.mapTotEspecialidades();
		
		modelo.addAttribute("lisHospitales", lisHospitales);
		modelo.addAttribute("mapaInstituciones", mapaInstituciones);
		modelo.addAttribute("mapaTotEspecialidades", mapaTotEspecialidades);
		
		return "clinicos/hospital/hospital";
	}
	
	@RequestMapping("/clinicos/hospital/editar")
	public String clinicosHospitalEditar(HttpServletRequest request, Model modelo){
		
		String hospitalId			= request.getParameter("HospitalId")==null?"0":request.getParameter("HospitalId");
		RotHospital rotHospital = new RotHospital();
		if (rotHospitalDao.existeReg(hospitalId)){
			rotHospital = rotHospitalDao.mapeaRegId(hospitalId);
		}
		List<RotInstitucion> lisInstituciones = rotInstitucionDao.getListAll( "ORDER BY 1");
		
		modelo.addAttribute("lisInstituciones", lisInstituciones);
		modelo.addAttribute("rotHospital", rotHospital);
		
		return "clinicos/hospital/editar";
	}
	
	@RequestMapping("/clinicos/hospital/grabarHospital")
	public String clinicosHospitalGrabarHospital(HttpServletRequest request, Model modelo){
		
		String hospitalId			= request.getParameter("HospitalId")==null?"0":request.getParameter("HospitalId");
		String hospitalNombre		= request.getParameter("HospitalNombre")==null?"0":request.getParameter("HospitalNombre");
		String hospitalCorto		= request.getParameter("HospitalCorto")==null?"0":request.getParameter("HospitalCorto");
		String institucionId		= request.getParameter("InstitucionId")==null?"0":request.getParameter("InstitucionId");
		String calle				= request.getParameter("Calle")==null?"0":request.getParameter("Calle");
		String colonia				= request.getParameter("Colonia")==null?"0":request.getParameter("Colonia");
		String munEdo				= request.getParameter("MunEdo")==null?"0":request.getParameter("MunEdo");
		String pais					= request.getParameter("Pais")==null?"0":request.getParameter("Pais");
		String telefono				= request.getParameter("Telefono")==null?"0":request.getParameter("Telefono");
		String fax					= request.getParameter("Fax")==null?"0":request.getParameter("Fax");
		String medico				= request.getParameter("Medico")==null?"0":request.getParameter("Medico");
		String puesto				= request.getParameter("Puesto")==null?"0":request.getParameter("Puesto");
		String saludo				= request.getParameter("Saludo")==null?"0":request.getParameter("Saludo");
		
		RotHospital rotHospital = new RotHospital();
		if (rotHospitalDao.existeReg(hospitalId)){
			rotHospital.setHospitalId(hospitalId);
			rotHospital.setHospitalNombre(hospitalNombre);
			rotHospital.setHospitalCorto(hospitalCorto);
			rotHospital.setInstitucionId(institucionId);
			rotHospital.setCalle(calle);
			rotHospital.setColonia(colonia);
			rotHospital.setMunEdo(munEdo);
			rotHospital.setPais(pais);
			rotHospital.setTelefono(telefono);
			rotHospital.setFax(fax);
			rotHospital.setMedico(medico);
			rotHospital.setPuesto(puesto);
			rotHospital.setSaludo(saludo);
			rotHospitalDao.updateReg(rotHospital);
		}else {
			hospitalId=rotHospitalDao.maximoReg();
			rotHospital.setHospitalId(hospitalId);
			rotHospital.setHospitalNombre(hospitalNombre);
			rotHospital.setHospitalCorto(hospitalCorto);
			rotHospital.setInstitucionId(institucionId);
			rotHospital.setCalle(calle);
			rotHospital.setColonia(colonia);
			rotHospital.setMunEdo(munEdo);
			rotHospital.setPais(pais);
			rotHospital.setTelefono(telefono);
			rotHospital.setFax(fax);
			rotHospital.setMedico(medico);
			rotHospital.setPuesto(puesto);
			rotHospital.setSaludo(saludo);
			rotHospitalDao.insertReg(rotHospital);
			
		}
		
		
		return "redirect:/clinicos/hospital/editar?HospitalId="+hospitalId;
	}
	@RequestMapping("/clinicos/hospital/borrar")
	public String clinicosHospitalBorrar(HttpServletRequest request, Model modelo){
		
		String hospitalId			= request.getParameter("HospitalId")==null?"0":request.getParameter("HospitalId");
		
		if (rotHospitalDao.existeReg(hospitalId)){			
			rotHospitalDao.deleteReg(hospitalId);
		}
		
		return "redirect:/clinicos/hospital/hospital";
	}
	@RequestMapping("/clinicos/esphospital/getEspAsignadas")
	public String clinicosEsphospitalGetEspAsignadas(HttpServletRequest request, Model modelo){
		String hospitalId 		= request.getParameter("hospitalId")==null?"0":request.getParameter("hospitalId");
		
		List<RotHospitalEspecialidad> lisEspecialidades 	= rotHospitalEspecialidadDao.getListHosp(hospitalId,"ORDER BY 1");
		HashMap<String, RotEspecialidad> mapaEspecialidades = rotEspecialidadDao.getMapAll(""); 
		
		modelo.addAttribute("lisEspecialidades", lisEspecialidades);
		modelo.addAttribute("mapaEspecialidades", mapaEspecialidades);
		
		return "clinicos/esphospital/getEspAsignadas";
	}
	
	@ResponseBody	
	@RequestMapping("/clinicos/esphospital/asignarEsp")
	public String clinicosEsphospitalAsignarEsp(HttpServletRequest request){
		
		String especialidadId 	= request.getParameter("especialidadId")==null?"0":request.getParameter("especialidadId");
		String hospitalId 	  	= request.getParameter("hospitalId")==null?"0":request.getParameter("hospitalId");
		String respuesta 		= "error";
		RotHospitalEspecialidad rotHosEsp = new RotHospitalEspecialidad();
		
		rotHosEsp.setHospitalId(hospitalId);
		rotHosEsp.setEspecialidadId(especialidadId);
			
		if(rotHospitalEspecialidadDao.insertReg(rotHosEsp)){
			respuesta = "grabo";
		}
		
		return respuesta;
	}
	
	@ResponseBody
	@RequestMapping("/clinicos/esphospital/existeProgramacion")
	public String clinicosEsphospitalExisteProgramacion(HttpServletRequest request){
		
		String especialidadId 	= request.getParameter("especialidadId") == null ? "0" : request.getParameter("especialidadId");
		String hospitalId 	  	= request.getParameter("hospitalId") == null ? "0" : request.getParameter("hospitalId");
		String respuesta 		= "noExiste";
		
		if(rotProgramacionDao.existeProgramacion(hospitalId, especialidadId)){
			respuesta = "existe";
		}
		
		return respuesta;
	}
	
	@ResponseBody
	@RequestMapping("/clinicos/esphospital/desasignarEsp")
	public String clinicosEsphospitalDesasignarEsp(HttpServletRequest request){
		
		String especialidadId 	= request.getParameter("especialidadId")==null?"0":request.getParameter("especialidadId");
		String hospitalId 	  	= request.getParameter("hospitalId")==null?"0":request.getParameter("hospitalId");
		String respuesta 		= "error";
		RotHospitalEspecialidad rotHosEsp = new RotHospitalEspecialidad();
		
		rotHosEsp.setHospitalId(hospitalId);
		rotHosEsp.setEspecialidadId(especialidadId);
		
		if(rotHospitalEspecialidadDao.deleteReg(hospitalId,especialidadId)){
			respuesta = "borro";
		}
		
		return respuesta;
	}
	
	@ResponseBody
	@RequestMapping("/clinicos/esphospital/grabarHospEsp")
	public String clinicosEsphospitalGrabarHospEsp(HttpServletRequest request){
		
		String hospitalId 	  = request.getParameter("hositalId") == null ? "0" : request.getParameter("hositalId");
		String especialidadId = request.getParameter("especialidadId") == null ? "0" : request.getParameter("especialidadId");
		String cPrincipal 	  = request.getParameter("cPrincipal") == null ? "0" : request.getParameter("cPrincipal");
		String pPrincipal 	  = request.getParameter("pPrincipal") == null ? "0" : request.getParameter("pPrincipal");
		String cSecundario 	  = request.getParameter("cSecundario") == null ? "0" : request.getParameter("cSecundario");
		String pSecundario 	  = request.getParameter("pSecundario") == null ? "0" : request.getParameter("pSecundario");
		String estado		  = request.getParameter("estado") == null ? "0" : request.getParameter("estado");
		
		String respuesta 	  = "error";
		
		RotHospitalEspecialidad rotHospitalEspecialidad = new RotHospitalEspecialidad();
		
		rotHospitalEspecialidad.setHospitalId(hospitalId);
		rotHospitalEspecialidad.setEspecialidadId(especialidadId);
		rotHospitalEspecialidad.setContactoPrincipal(cPrincipal);
		rotHospitalEspecialidad.setPuestoPrincipal(pPrincipal);
		rotHospitalEspecialidad.setContactoSecundario(cSecundario);
		rotHospitalEspecialidad.setPuestoSecundario(pSecundario);
		rotHospitalEspecialidad.setEstado(estado);
		
		if(rotHospitalEspecialidadDao.updateReg(rotHospitalEspecialidad)){
			respuesta = "actualizo";
		}
		
		return respuesta;
	}
	
	@RequestMapping("/clinicos/esphospital/getEspNoAsignadas")
	public String clinicosEsphospitalGetEspNoAsignadas(HttpServletRequest request, Model modelo){
		
		String hospitalId 		= request.getParameter("hospitalId")==null?"0":request.getParameter("hospitalId");
		
		List<RotEspecialidad> lisEspecialidades = rotEspecialidadDao.getEspNoAsignadas( hospitalId,"ORDER BY 1");
		
		modelo.addAttribute("lisEspecialidades", lisEspecialidades);	
		
		return "clinicos/esphospital/getEspNoAsignadas";
	}

	@RequestMapping("/clinicos/esphospital/especialidad")
	public String clinicosEsphospitalEspecialidad(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContClinicosEspHospital|Especialidad:");
		return "clinicos/esphospital/especialidad";
	}	
	
}