package aca.web.datosprofesor;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.catalogo.spring.CatPais;
import aca.catalogo.spring.CatPaisDao;
import aca.emp.spring.EmpCurriculum;
import aca.emp.spring.EmpCurriculumDao;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContDatosProfesorVitae {	
	
	@Autowired
	AccesoDao accesoDao;
	
	@Autowired
	CatPaisDao catPaisDao;
	
	@Autowired
	EmpCurriculumDao empCurriculumDao;
	
	@Autowired
	MaestrosDao maestrosDao;
	
	
	@RequestMapping("/datos_profesor/vitae/vitae")
	public String datosProfesorVitaeVitae(HttpServletRequest request, Model modelo){
		
		String codigoPersonal 	= "0";
		String codigoEmpleado 	= "0";
		String nombreEmpleado 	= "-";		 
		boolean revisado 		= false;
		boolean administrador 	= false;		
		Acceso acceso 			= new Acceso();
		String respuesta 		= "-";
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal 	 	= sesion.getAttribute("codigoPersonal").toString();	
			codigoEmpleado 	 	= sesion.getAttribute("codigoEmpleado").toString();
			nombreEmpleado 		= maestrosDao.getNombreMaestro(codigoEmpleado, "NOMBRE"); 
			acceso 				= accesoDao.mapeaRegId(codigoPersonal);
			if(accesoDao.existeReg(codigoPersonal)){
				if( acceso.getAdministrador().equals("S") || acceso.getSupervisor().equals("S") ){
					administrador = true;								
				}
			}
		}
		
		String empleadoReviso	= request.getParameter("revisado")==null?"0":codigoPersonal;
		String nombreReviso		= maestrosDao.getNombreMaestro(empleadoReviso, "NOMBRE");
		
		int accion 			= Integer.parseInt(request.getParameter("Accion")==null?"0":request.getParameter("Accion"));
		boolean error 		= false;
		
		EmpCurriculum  empCurriculum = new EmpCurriculum();					
		if(empCurriculumDao.existeReg(codigoPersonal)){
			empCurriculum = empCurriculumDao.mapeaRegId(codigoPersonal);
			if (maestrosDao.existeReg(empCurriculum.getRevisado()) || maestrosDao.existeReg(empleadoReviso)){
				revisado = true;
				nombreReviso		= maestrosDao.getNombreMaestro(empCurriculum.getRevisado(), "NOMBRE");
			}	
		}else if (request.getParameter("revisado")==null){
			revisado = false;
		}else{
			revisado = true;
		}
		 
		switch(accion){
			case 2:{//Guardar				
				empCurriculum.setIdEmpleado(codigoPersonal);
				empCurriculum.setLugarNac(request.getParameter("lugarNac")==null?"-":request.getParameter("lugarNac"));
				empCurriculum.setNivelId(request.getParameter("nivelId"));
				empCurriculum.setTProfesional(request.getParameter("tProfesional")==null?"-":request.getParameter("tProfesional"));
				empCurriculum.setRevisado(empleadoReviso);
				empCurriculum.setGPosgrado(request.getParameter("gPosgrado")==null?"-":request.getParameter("gPosgrado"));
				empCurriculum.setTUniversitario(request.getParameter("tUniversitario")==null?"-":request.getParameter("tUniversitario"));
				empCurriculum.setRespActual(request.getParameter("respActual")==null?"-":request.getParameter("respActual"));
				//empCurriculum.setRespAnterior(request.getParameter("respAnterior"));
				empCurriculum.setExpDocente(request.getParameter("expDocente")==null?"-":request.getParameter("expDocente"));
				empCurriculum.setFNacimiento(request.getParameter("fNacimiento"));
				empCurriculum.setNacionalidad(request.getParameter("nacionalidad"));
				empCurriculum.settDoctorado(request.getParameter("tDoctorado")==null?"-":request.getParameter("tDoctorado"));
				empCurriculum.setFechaDoc(request.getParameter("fechaDoc"));
				empCurriculum.setFechaMae(request.getParameter("fechaMae"));
				empCurriculum.setFechaLic(request.getParameter("fechaLic"));
				empCurriculum.setInstLic(request.getParameter("instLic")==null?"-":request.getParameter("instLic"));
				empCurriculum.setInstMae(request.getParameter("instMae")==null?"-":request.getParameter("instMae"));
				empCurriculum.setInstDoc(request.getParameter("instDoc")==null?"-":request.getParameter("instDoc"));				
				if(empCurriculumDao.existeReg(codigoPersonal)){					
					if(!empCurriculumDao.updateReg(empCurriculum)){						
						error = true;
					}
				}else{
					if(!empCurriculumDao.insertReg(empCurriculum))
						error = true;
				}				
				if(error){					
					respuesta = "<font color=red size=3><b>Ocurri&oacute; un error al guardar los datos. Int&eacute;ntelo de nuevo</b></font>";
				}else{					
					respuesta = "<font color=blue size=3><b>Se guardaron los datos correctamente</b></font>";
				}
				
				empCurriculum = empCurriculumDao.mapeaRegId(codigoPersonal);				
				// Validacion del campo revisado
				if (empCurriculum.getRevisado().equals("0")) revisado = false; 
			}break;
		}
		
		List<CatPais> lisPaises 	= catPaisDao.getListAll(" ORDER BY NACIONALIDAD");		
		
		modelo.addAttribute("administrador", administrador);
		modelo.addAttribute("revisado", revisado);
		modelo.addAttribute("respuesta", respuesta);
		modelo.addAttribute("nombreEmpleado", nombreEmpleado);
		modelo.addAttribute("nombreReviso", nombreReviso);
		modelo.addAttribute("empCurriculum", empCurriculum);
		
		modelo.addAttribute("lisPaises", lisPaises);
		
		return "datos_profesor/vitae/vitae";
	}	
	
}