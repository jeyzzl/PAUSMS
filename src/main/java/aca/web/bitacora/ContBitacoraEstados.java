package aca.web.bitacora;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.archivo.spring.ArchGruposCarrera;
import aca.archivo.spring.ArchStatus;
import aca.bitacora.spring.BitEstado;
import aca.bitacora.spring.BitEtiqueta;
import aca.bitacora.spring.BitTramite;
import aca.bitacora.spring.BitTramiteAlumno;

@Controller
public class ContBitacoraEstados {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	aca.bitacora.spring.BitTramiteAlumnoDao bitTramiteAlumnoDao;
	
	@Autowired
	aca.bitacora.spring.BitAreaDao bitAreaDao;
	
	@Autowired
	aca.bitacora.spring.BitTramiteDao bitTramiteDao;
	
	@Autowired
	aca.bitacora.spring.BitEtiquetaDao bitEtiquetaDao;
	
	@Autowired
	aca.bitacora.spring.BitEstadoDao bitEstadoDao;
	
	@Autowired
	aca.alumno.spring.AlumPersonalDao alumPersonalDao;

	@Autowired
	aca.archivo.spring.ArchGruposCarreraDao archGruposCarreraDao;
	
	@RequestMapping("/bitacora/status/estado")
	public String bitacoraStatusEstado(HttpServletRequest request, Model modelo){	
		
		List<BitEstado> lisEstados = bitEstadoDao.lisEstados("ORDER BY ESTADO");
		
		modelo.addAttribute("lisEstados",lisEstados);
		
		return "bitacora/status/estado";
	}	
	

	@RequestMapping("/bitacora/status/editar")
	public String bitacoraStatusEditar(HttpServletRequest request, Model modelo){
		
		String estadoId 	= request.getParameter("EstadoId");
		
		BitEstado estado	= new BitEstado();
		
		if (bitEstadoDao.existeReg(estadoId) ) {
			estado = bitEstadoDao.mapeaRegId(estadoId) ;
		}
		
		modelo.addAttribute("estado",estado);
		
		return "bitacora/status/editar";
	}
	
	@RequestMapping("/bitacora/status/grabar")
	public String bitacoraStatusGrabar(HttpServletRequest request, Model modelo){
		
		String estadoId 	= request.getParameter("EstadoId")==null?"0":request.getParameter("EstadoId");
		String nombre 		= request.getParameter("Nombre")==null?"-":request.getParameter("Nombre");
		
		BitEstado estado	= new BitEstado();
		estado.setEstado_nombre(nombre);

		if (bitEstadoDao.existeReg(estadoId) ) {
			estado.setEstado(estadoId);
			bitEstadoDao.updateReg(estado);
		}
		else {
			estado.setEstado(bitEstadoDao.maximoReg());
			bitEstadoDao.insertReg(estado);
		}
		
		return "redirect:/bitacora/status/editar?EstadoId="+estado.getEstado();
	}
	
	@RequestMapping("/bitacora/status/borrar")
	public String bitacoraStatusBorrar(HttpServletRequest request, Model modelo){
				
		String estadoId 	= request.getParameter("EstadoId")==null?"0":request.getParameter("EstadoId");
		
		if (bitEstadoDao.existeReg(estadoId) ) {
			bitEstadoDao.deleteReg(estadoId) ;
		}
		
		return "redirect:/bitacora/status/estado";
	}

	
}