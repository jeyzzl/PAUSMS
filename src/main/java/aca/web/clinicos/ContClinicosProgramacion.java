package aca.web.clinicos;

import java.util.HashMap;
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
import aca.rotaciones.spring.RotEspecialidad;
import aca.rotaciones.spring.RotEspecialidadDao;
import aca.rotaciones.spring.RotHospital;
import aca.rotaciones.spring.RotHospitalDao;
import aca.rotaciones.spring.RotHospitalEspecialidad;
import aca.rotaciones.spring.RotHospitalEspecialidadDao;
import aca.rotaciones.spring.RotProgramacion;
import aca.rotaciones.spring.RotProgramacionDao;

@Controller
public class ContClinicosProgramacion {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;	
	
	@Autowired
	RotHospitalDao rotHospitalDao;
	
	@Autowired
	RotHospitalEspecialidadDao rotHospitalEspecialidadDao;
	
	@Autowired
	RotProgramacionDao rotProgramacionDao;
	
	@Autowired
	RotEspecialidadDao rotEspecialidadDao;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;	
	
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/clinicos/programacion/existeAlumno")
	public String clinicosProgramacionExisteAlumno(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContClinicosProgramacion|ExisteAlumno");
		return "clinicos/programacion/existeAlumno";
	}
	
	@RequestMapping("/clinicos/programacion/setAlumno")
	public String clinicosEsphospitalSetAlumno(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContClinicosProgramacion|SetAlumno");
		return "clinicos/programacion/setAlumno";
	}
	
	@RequestMapping("/clinicos/programacion/existePago")
	public String clinicosProgramacionExistePago(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContClinicosProgramacion|ExistePago");
		return "clinicos/programacion/existePago";
	}
	
	@RequestMapping("/clinicos/programacion/eliminarProgramacion")
	public String clinicosProgramacionEliminarProgramacion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContClinicosProgramacion|EliminarProgramacion");
		return "clinicos/programacion/eliminarProgramacion";
	}
	
	@RequestMapping("/clinicos/programacion/eliminarAlumno")
	public String clinicosProgramacionEliminarAlumno(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContClinicosProgramacion|EliminarAlumno");
		return "clinicos/programacion/eliminarAlumno";
	}	

	@RequestMapping("/clinicos/programacion/agregarAlumno")
	public String clinicosProgramacionAgregarAlumno(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContClinicosProgramacion|AgregarAlumno");
		return "clinicos/programacion/agregarAlumno";
	}	
	
	@RequestMapping("/clinicos/programacion/programacion")
	public String clinicosProgramacionProgramacion(HttpServletRequest request, Model modelo){
		
		String accion = request.getParameter("Accion")==null?"":request.getParameter("Accion");
		
		List<RotHospital> lisHospitales = rotHospitalDao.getListHospitales("ORDER BY 1");	
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	//Subir hospital y la especialidad
    		if(accion.equals("1")){
    			sesion.setAttribute("rotHospitalId", request.getParameter("HospitalId"));
    			sesion.setAttribute("rotEspecialidadId", request.getParameter("EspecialidadId"));    			
    		}else if(accion.equals("2")){
    			sesion.setAttribute("rotEspecialidadId", request.getParameter("EspecialidadId"));
    		}
    		
    		if(sesion.getAttribute("rotHospitalId")==null){
    			sesion.setAttribute("rotHospitalId", lisHospitales.get(0).getHospitalId());
    		}    		
        	
        }
		
		String hospitalId 		= (String)sesion.getAttribute("rotHospitalId");
		List<RotHospitalEspecialidad> lisEspecialidades = rotHospitalEspecialidadDao.getListHospActivas(hospitalId, "ORDER BY 1");
		
		if(sesion.getAttribute("rotEspecialidadId")==null){
			sesion.setAttribute("rotEspecialidadId", lisEspecialidades.get(0).getEspecialidadId());
		}
		
		String especialidadId 	= (String)sesion.getAttribute("rotEspecialidadId");
		boolean encontrado = false;
		for(RotHospitalEspecialidad esp : lisEspecialidades){
			if(esp.getEspecialidadId().equals(especialidadId)){
				encontrado = true;
				break;
			}
		}
		if(!encontrado && !especialidadId.equals("todas")){
			especialidadId = lisEspecialidades.get(0).getEspecialidadId();
		}
		
		aca.util.Fecha fechaUtil = new aca.util.Fecha();		
		String mensaje = "";
		if(accion.equals("3")){
			String cantidad = request.getParameter("cantidad");
			String fechaInicio = request.getParameter("fechaInicio");
			
			for(int i=0; i<Integer.parseInt(cantidad); i++){
				RotProgramacion programacion = new RotProgramacion();
				programacion.setProgramacionId(rotProgramacionDao.maximoReg());
				programacion.setHospitalId(hospitalId);
				programacion.setEspecialidadId(especialidadId);
				programacion.setAnual("0");
				programacion.setInscripcion("0");
				programacion.setIntegrada("0");
				programacion.setPago("0");				
				programacion.setfInicio(fechaInicio);				
				String semanas = rotEspecialidadDao.getSemanas(especialidadId);
				try {
					programacion.setfFinal(fechaUtil.getDiaSiguiente(fechaInicio, (Integer.parseInt(semanas)*7)-3));
				}catch(Exception ex) {
					System.out.println("Error en fecha:"+ex);
				}
				
				if(rotProgramacionDao.insertReg(programacion)){
					mensaje = "plazas generadas";
				}else{
					mensaje = "ocurrio un error al intentar generar plazas";
				}
			}
		}	
		
		List<RotProgramacion> lisProgramaciones = null;
		if(!especialidadId.equals("todas")){
			lisProgramaciones = rotProgramacionDao.getListHospEsp( hospitalId, especialidadId, "ORDER BY 1 desc");
		}else{
			lisProgramaciones = rotProgramacionDao.getListHosp( hospitalId, "ORDER BY 1 desc");
		}		
		HashMap<String,String> mapaAlumnos 			=  alumPersonalDao.mapaAlumnosEnRotaciones();
		HashMap<String,RotEspecialidad> mapaEspecialidades 	=  rotEspecialidadDao.getMapAll("");
		
		modelo.addAttribute("especialidadId", especialidadId);
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("lisHospitales", lisHospitales);
		modelo.addAttribute("lisEspecialidades", lisEspecialidades);
		modelo.addAttribute("lisProgramaciones", lisProgramaciones);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("mapaEspecialidades", mapaEspecialidades);
		
		return "clinicos/programacion/programacion";
	}	

	@RequestMapping("/clinicos/programacion/PDFT")
	public String clinicosProgramacionPDFT(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContClinicosProgramacion|PDFT");
		return "clinicos/programacion/PDFT";
	}	
	
}