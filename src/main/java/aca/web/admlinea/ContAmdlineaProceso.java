package aca.web.admlinea;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.admision.spring.AdmDocAlumDao;
import aca.admision.spring.AdmEvaluacionNota;
import aca.admision.spring.AdmEvaluacionNotaDao;
import aca.admision.spring.AdmSolicitud;
import aca.admision.spring.AdmSolicitudDao;
import aca.admision.spring.AdmUsuario;
import aca.admision.spring.AdmUsuarioDao;
import aca.emp.spring.EmpleadoDao;
import aca.admision.spring.AdmDocAlum;

@Controller
public class ContAmdlineaProceso {	
	
	@Autowired
	AdmDocAlumDao admDocAlumDao;
	
	@Autowired
	AdmSolicitudDao admSolicitudDao;
	
	@Autowired
	AdmUsuarioDao admUsuarioDao;
	
	@Autowired
	EmpleadoDao empleadoDao;
	
	@RequestMapping("/admlinea/proceso/alumnos")
	public String admlineaProcesoAlumnos(HttpServletRequest request, Model modelo){
		
		List<AdmDocAlum> lisDocumentos  				= admDocAlumDao.lisTodos(" WHERE ESTADO = 'E' AND FOLIO IN (SELECT FOLIO FROM SALOMON.ADM_SOLICITUD WHERE ESTADO = '2') ORDER BY FOLIO");
		HashMap<String,AdmSolicitud> mapaSolicitudes	= admSolicitudDao.mapaAlumnosPorEstado("2");
		HashMap<String,AdmUsuario> mapaUsuarios			= admUsuarioDao.mapaUsuariosPorEstado("2");
		
		HashMap<String,String> mapaAsesores				= empleadoDao.mapaAsesoresAdmision();
		HashMap<String,String> mapaPendientes			= admDocAlumDao.mapaDocumentosPendientes();
		
		modelo.addAttribute("lisDocumentos", lisDocumentos);
		
		modelo.addAttribute("mapaSolicitudes", mapaSolicitudes);
		modelo.addAttribute("mapaUsuarios", mapaUsuarios);		
		modelo.addAttribute("mapaAsesores", mapaAsesores);
		modelo.addAttribute("mapaPendientes", mapaPendientes);
				
		return "admlinea/proceso/alumnos";
	}
	
	
}
