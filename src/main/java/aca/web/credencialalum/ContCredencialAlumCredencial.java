package aca.web.credencialalum;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumPersonalDao;
import aca.catalogo.spring.CatSalonDao;
import aca.matricula.spring.MatAlumno;
import aca.matricula.spring.MatAlumnoDao;
import aca.matricula.spring.MatEvento;
import aca.matricula.spring.MatEventoDao;
import aca.vista.spring.Alumno;
import aca.vista.spring.AlumnoDao;

@Controller
public class ContCredencialAlumCredencial {

	@Autowired
	private MatEventoDao matEventoDao;

	@Autowired
	private MatAlumnoDao matAlumnoDao;

	@Autowired
	private AlumnoDao alumnoDao;

	@Autowired
	private AlumPersonalDao alumPersonalDao;
	
	@RequestMapping("/credencial_alum/credencial/credencial")
	public String credencialAlumCredencialCredencial(HttpServletRequest request, Model modelo){
		String codigoAlumno = "";
		String usuario  	= "";
		String eventoId 	= request.getParameter("EventoId")==null?"0":request.getParameter("EventoId");
		String estado 		= request.getParameter("Estado")==null?"A":request.getParameter("Estado");

		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){        	
        	codigoAlumno 		= (String) sesion.getAttribute("codigoAlumno");
        	usuario 			= (String) sesion.getAttribute("codigoPersonal");
        }

		List<MatEvento> lisEventos = matEventoDao.lisMatEvento(estado, " ORDER BY EVENTO_ID");
		if(eventoId.equals("0")) {eventoId = lisEventos.get(0).getEventoId();}

		List<MatAlumno> lisAlumnos = matAlumnoDao.lisPorEvento(eventoId, " ORDER BY FECHA");

		HashMap<String,String> mapaNombreAlumno = alumnoDao.mapaNombreAlumno();

		modelo.addAttribute("codigoAlumno", codigoAlumno);
		modelo.addAttribute("eventoId", eventoId);
		modelo.addAttribute("estado", estado);
		modelo.addAttribute("lisEventos", lisEventos);
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("mapaNombreAlumno", mapaNombreAlumno);
		
		return "credencial_alum/credencial/credencial";
	}
	
	@RequestMapping("/credencial_alum/credencial/agregar")
	public String credencialAlumCredencialAgregar(HttpServletRequest request, Model modelo){
		String codigoAlumno = request.getParameter("CodigoAlumno")==null?"0":request.getParameter("CodigoAlumno");
		String eventoId 	= request.getParameter("EventoId")==null?"0":request.getParameter("EventoId");
		String usuario  	= "";
		String mensaje 		= "";
		String fecha 		= aca.util.Fecha.getHoy();

		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){        	
        	codigoAlumno 		= (String) sesion.getAttribute("codigoAlumno");
        	usuario 			= (String) sesion.getAttribute("codigoPersonal");
        }

		MatAlumno matAlumno = new MatAlumno();
		Alumno alumno = alumnoDao.mapeaRegId(codigoAlumno);

		matAlumno.setEventoId(eventoId);	
		matAlumno.setCodigoPersonal(codigoAlumno);
		matAlumno.setPlanId(alumno.getPlanId());
		matAlumno.setFecha(fecha);
		matAlumno.setUsuario(usuario);
		matAlumno.setModo("P");
		matAlumno.setEstado("I");

		if(matAlumnoDao.existeReg(eventoId, codigoAlumno, alumno.getPlanId())){
			mensaje = "Student already registered in current event";
		}else{
			if(matAlumnoDao.insertReg(matAlumno)){
				mensaje = "Saved";
			}else{
				mensaje = "Error saving";
			}
		}
		
		return "redirect:/credencial_alum/credencial/credencial?EventoId="+eventoId+"&Mensaje="+mensaje;
	}	
	
	@RequestMapping("/credencial_alum/credencial/borrar")
	public String credencialAlumCredencialBorrar(HttpServletRequest request){
		String codigoAlumno = request.getParameter("CodigoAlumno")==null?"0":request.getParameter("CodigoAlumno");
		String eventoId 	= request.getParameter("EventoId")==null?"0":request.getParameter("EventoId");
		String mensaje		= "";

		Alumno alumno = alumnoDao.mapeaRegId(codigoAlumno);

		if(matAlumnoDao.existeReg(eventoId, codigoAlumno, alumno.getPlanId())){
			if(matAlumnoDao.deleteReg(eventoId, codigoAlumno, alumno.getPlanId())){
				mensaje = "Deleted";
			}else{
				mensaje = "Error deleting";
			}
		}

		return "redirect:/credencial_alum/credencial/credencial?EventoId="+eventoId+"&Mensaje="+mensaje;
	}	
	
	@RequestMapping("/credencial_alum/credencial/guardar")
	public String credencialAlumCredencialGuardar(HttpServletRequest request, Model modelo){		
		String codigoAlumno = request.getParameter("CodigoAlumno")==null?"0":request.getParameter("CodigoAlumno");
		String eventoId 	= request.getParameter("EventoId")==null?"A":request.getParameter("EventoId");
		String estado 		= request.getParameter("Estado")==null?"0":request.getParameter("Estado");
		String mensaje 		= "";

		Alumno alumno = alumnoDao.mapeaRegId(codigoAlumno);

		if(matAlumnoDao.existeReg(eventoId, codigoAlumno, alumno.getPlanId())){
			if(matAlumnoDao.updateEstado(eventoId, codigoAlumno, estado, alumno.getPlanId())){
				mensaje = "Saved";
			}else{
				mensaje = "Error saving";
			}
		}else{
			mensaje = "No record found";
		}

		return "redirect:/credencial_alum/credencial/credencial?EventoId="+eventoId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/credencial_alum/credencial/generaCredencial")
	public String credencialAlumCredencialGeneraCredencial(HttpServletRequest request){		

		return "credencial_alum/credencial/generaCredencial";
	}		
	
	@RequestMapping("/credencial_alum/credencial/subir")
	public String credencialAlumCredencialSubir(HttpServletRequest request){
	
		return "credencial_alum/credencial/subir";
	}
	
	@RequestMapping("/credencial_alum/credencial/tomarFoto")
	public String credencialAlumCredencialTomarFoto(HttpServletRequest request){

		return "credencial_alum/credencial/tomarFoto";
	}	
}

	