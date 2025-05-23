package aca.web.matricula;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.acceso.spring.Acceso;
import aca.alumno.spring.AlumPersonalDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatPeriodo;
import aca.catalogo.spring.CatPeriodoDao;
import aca.catalogo.spring.CatTipoCal;
import aca.musica.spring.MusiAutoriza;
import aca.musica.spring.MusiAutorizaDao;
import aca.vista.spring.AlumnoCurso;


@Controller
public class ContMatriculaAutoriza {	
	
	@Autowired
	AlumPersonalDao alumPersonalDao;	
	
	@Autowired
	CatPeriodoDao catPeriodoDao;
	
	@Autowired
	CargaDao cargaDao;	
	
	@Autowired
	MusiAutorizaDao musiAutorizaDao;
	
	@RequestMapping("/matricula/autoriza/horario")
	public String matriculaAutorizaHorario(HttpServletRequest request, Model modelo){
		
		String cargaSesion		= "000000";
		String periodoSesion	= "0000";
		String codigoPersonal	= "0";
		String codigoAlumno		= "0";
		String alumnoNombre		= "-";
		boolean esAlumno		= false;
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	cargaSesion 		= (String)sesion.getAttribute("cargaId");
        	periodoSesion 		= (String)sesion.getAttribute("periodo"); //cargaDao.getPeriodo(cargaSesion);
        	codigoPersonal 		= (String)sesion.getAttribute("codigoPersonal");       
        	codigoAlumno		= (String)sesion.getAttribute("codigoAlumno");
        	if (alumPersonalDao.existeAlumno(codigoAlumno)) {
        		esAlumno 		= true;
        		alumnoNombre 	= alumPersonalDao.getAlumno(codigoAlumno, "NOMBRE"); 
        	}
        }
        
        String periodoId	= request.getParameter("PeriodoId")==null?"0000":request.getParameter("PeriodoId");
		String cargaId		= request.getParameter("CargaId")==null?"000000":request.getParameter("CargaId");
		
		if (!periodoId.equals("0000") && !periodoId.equals(periodoSesion) && sesion!=null) {
			sesion.setAttribute("periodo", periodoId);			
			cargaId 	= "000000";
		}else if (!periodoId.equals("0000") && !cargaId.equals("000000") && sesion!=null){
			sesion.setAttribute("cargaId", cargaId);			
		}else if (periodoId.equals("0000") && cargaId.equals("000000") && !cargaSesion.equals("000000")){
			periodoId 	= periodoSesion;
			cargaId 	= cargaSesion;
		}		
        
        List<CatPeriodo> lisPeriodos	= catPeriodoDao.getListAll(" ORDER BY PERIODO_ID DESC");
		List<Carga> lisCargas			= cargaDao.getListPeriodo(periodoId," ORDER BY CARGA_ID");
		List<MusiAutoriza> lisAlumnos	= musiAutorizaDao.lisPorCarga(cargaId, "ORDER BY ENOC.MUSI_AUTORIZA.FECHA");
		
		HashMap<String,String> mapaAlumnos		= alumPersonalDao.mapaAlumnosEnMusica(cargaId, "NOMBRE");
		
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("esAlumno", esAlumno);
		modelo.addAttribute("alumnoNombre", alumnoNombre);
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		
		return "matricula/autoriza/horario";
	}	
	
	@RequestMapping("/matricula/autoriza/grabar")
	public String matriculaAutorizaGrabar(HttpServletRequest request, Model modelo){	
		
		String codigoPersonal	= "0";
		String codigoAlumno		= "0";
		MusiAutoriza autoriza 	= new MusiAutoriza();
		String mensaje 			= "-";
		
		String cargaId			= request.getParameter("CargaId")==null?"000000":request.getParameter("CargaId");
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){        	
        	codigoPersonal 		= (String)sesion.getAttribute("codigoPersonal");       
        	codigoAlumno		= (String)sesion.getAttribute("codigoAlumno");
        	if (musiAutorizaDao.existeReg(codigoAlumno, cargaId)==false) {
        		autoriza.setCodigoPersonal(codigoAlumno);
        		autoriza.setCargaId(cargaId);
        		autoriza.setUsuario(codigoPersonal);
        		if (musiAutorizaDao.insertReg(autoriza)) {
        			mensaje = "¡Grabado!";
        		}else {
        			mensaje = "¡Error al grabar!";
        		}
        	}else {
        		mensaje = "¡Ya está autorizado el alumno!";
        	}
        }      
		
		return "redirect:/matricula/autoriza/horario?Mensaje="+mensaje;
	}
	
	@RequestMapping("/matricula/autoriza/borrar")
	public String matriculaAutorizaBorrar(HttpServletRequest request, Model modelo){
		
		String mensaje 			= "-";
		String codigoAlumno		= request.getParameter("CodigoAlumno")==null?"0":request.getParameter("CodigoAlumno");
		String cargaId			= request.getParameter("CargaId")==null?"000000":request.getParameter("CargaId");
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){       	
        	if (musiAutorizaDao.existeReg(codigoAlumno, cargaId)) {        		
        		if (musiAutorizaDao.deleteReg(codigoAlumno, cargaId)) {
        			mensaje = "¡Borrado!";
        		}else {
        			mensaje = "¡Error al borrar!";
        		}
        	}
        }
        
		return "redirect:/matricula/autoriza/horario?Mensaje="+mensaje;
	}
}