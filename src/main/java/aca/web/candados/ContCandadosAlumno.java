package aca.web.candados;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumPersonalDao;
import aca.candado.spring.CandAlumno;
import aca.candado.spring.CandAlumnoDao;
import aca.candado.spring.CandTipo;
import aca.candado.spring.CandTipoDao;
import aca.candado.spring.Candado;
import aca.candado.spring.CandadoDao;

@Controller
public class ContCandadosAlumno {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired
	private CandadoDao candadoDao;
	
	@Autowired
	private CandTipoDao candTipoDao;
	
	@Autowired
	private CandAlumnoDao candAlumnoDao;
	
	
	
	@RequestMapping("/candados/alumno/cand_alum")
	public String candadosAlumnoCand_alum(HttpServletRequest request, Model modelo){	
		String codigoPersonal 	= "0";
		String usuario 			= "0";
		String tipoId 			= request.getParameter("tipoId")==null?"0":request.getParameter("tipoId");	
		String nombreAlumno	 	= "";
		String folio	 		= request.getParameter("folio");
		String accion	 		= request.getParameter("accion") == null ? "1" : request.getParameter("accion");
		String resultado	 	= request.getParameter("resultado") == null ? "" : request.getParameter("resultado");
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){        	
        	codigoPersonal 		= (String) sesion.getAttribute("codigoAlumno");
        	usuario 			= (String) sesion.getAttribute("codigoPersonal");
        	nombreAlumno		= alumPersonalDao.getNombreAlumno(codigoPersonal, "NOMBRE");
        }
        
        List<CandTipo> lisTipo = candTipoDao.getLista(usuario, " ORDER BY TIPO_ID");
        if (tipoId.equals("0") && lisTipo.size() >= 1) {
        	tipoId 	= lisTipo.get(0).getTipoId();
        }               
        
        List<Candado> lisCandados 		= candadoDao.getLista(tipoId, "ORDER BY CANDADO_ID");
    	List<CandAlumno> lisDelAlumno 	= candAlumnoDao.getListCand(codigoPersonal, tipoId, "ORDER BY F_CREADO");
    	List<CandAlumno> lisTodos 		= candAlumnoDao.getLista(codigoPersonal, "A", "ORDER BY F_CREADO");
    	
    	boolean existe 			= false;
    	CandAlumno candAlumno 	= new CandAlumno();    	
    	if(candAlumnoDao.existeReg(codigoPersonal, folio)) {
    		candAlumno 			= candAlumnoDao.mapeaRegId(codigoPersonal, folio);
    		existe 				= true;
    	}
    	
    	HashMap<String,Candado> getMapaCandados = candadoDao.getMapaCandados();
    	HashMap<String,CandTipo> mapTipo 		= candTipoDao.mapTipo();
    	
    	modelo.addAttribute("codigoPersonal", codigoPersonal);
    	modelo.addAttribute("usuario", usuario);
    	modelo.addAttribute("nombreAlumno", nombreAlumno);
    	modelo.addAttribute("candAlumno", candAlumno);
    	modelo.addAttribute("existe", existe);
    	modelo.addAttribute("tipoId", tipoId);    	
    	modelo.addAttribute("lisCandados", lisCandados);
    	modelo.addAttribute("lisDelAlumno", lisDelAlumno);
    	modelo.addAttribute("lisTodos", lisTodos);
    	modelo.addAttribute("lisTipo", lisTipo);    	
    	modelo.addAttribute("getMapaCandados", getMapaCandados);
    	modelo.addAttribute("mapTipo", mapTipo);
    	modelo.addAttribute("accion", accion);
    	modelo.addAttribute("resultado", resultado);
		
		return "candados/alumno/cand_alum";
	}

	@RequestMapping("/candados/alumno/grabar")
	public String candadosAlumnoCand_alumGrabar(HttpServletRequest request, Model modelo){	
		String codigoPersonal 	= "0";
		String usuario 			= "0";
		String folio	 		= request.getParameter("folio")==null?"0":request.getParameter("folio");
		String comentario	 	= request.getParameter("comentario")==null?"-":request.getParameter("comentario");
		String estado	 		= request.getParameter("estado")==null?"-":request.getParameter("estado");
		String candadoId 		= request.getParameter("candadoId")==null?"0":request.getParameter("candadoId");
		String tipoId 			= request.getParameter("tipoId")==null?"0":request.getParameter("tipoId");
		String resultado	 	= "";
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
		if (sesion!=null){        	
			codigoPersonal 	= (String) sesion.getAttribute("codigoAlumno");
			usuario 		= (String) sesion.getAttribute("codigoPersonal");
		}
		
		String fechaHoy 	  = aca.util.Fecha.getHoy();
		
		CandAlumno candAlumno = new CandAlumno();
		candAlumno.setCodigoPersonal(codigoPersonal);
		candAlumno.setFolio(folio);
		candAlumno.setCandadoId(candadoId);
		candAlumno.setfCreado(fechaHoy);
		candAlumno.setUsAlta(usuario);
		candAlumno.setUsBaja(" ");
		candAlumno.setComentario(comentario);
		candAlumno.setEstado(estado);	
		candAlumno.setTipoId(tipoId);	
		if (!candAlumnoDao.existeReg(codigoPersonal, folio)) {
			candAlumno.setFolio(candAlumnoDao.maximoReg(codigoPersonal));
			if (candAlumnoDao.insertReg(candAlumno)) {				
				resultado = "Saved: " + candAlumno.getCodigoPersonal() + " - " + candAlumno.getFolio();
			} else {				
				resultado = "Error saving: " + candAlumno.getCodigoPersonal() + " - " + candAlumno.getFolio();
			}
		} else {
			candAlumno = candAlumnoDao.mapeaRegId(codigoPersonal, folio);
			candAlumno.setfBorrado(fechaHoy);
			candAlumno.setUsBaja(usuario);
			candAlumno.setComentario(comentario);
			candAlumno.setEstado("I");
			if (candAlumnoDao.updateReg(candAlumno)) {
				resultado = "Updated: " + candAlumno.getCodigoPersonal();				
			} else {				
				resultado = "Not updated: " + candAlumno.getCodigoPersonal();
			}
		}

		
		return "redirect:/candados/alumno/cand_alum?tipoId="+tipoId+"&resultado="+resultado;
	}
	
	@RequestMapping("/candados/alumno/desactivar")
	public String candadosAlumnoCand_alumDesactivar(HttpServletRequest request, Model modelo){
		
		String codigoAlumno 	= "0";
		String usuario		 	= "0";
		String tipoId	 		= request.getParameter("tipoId");
		String folio	 		= request.getParameter("folio");
		String resultado	 	= "";
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
		if (sesion!=null){        	
			codigoAlumno 	= (String) sesion.getAttribute("codigoAlumno");
			usuario 		= (String) sesion.getAttribute("codigoPersonal");
		}
		
		CandAlumno candAlumno = new CandAlumno();		
		if (candAlumnoDao.existeReg(codigoAlumno, folio)) {
			candAlumno = candAlumnoDao.mapeaRegId(codigoAlumno, folio);
			candAlumno.setEstado("I");
			candAlumno.setUsBaja(usuario);
			
			if (candAlumnoDao.updateReg(candAlumno)) {
				resultado = "Deactivate: " + candAlumno.getCodigoPersonal() + " - " + candAlumno.getFolio();
			} else {
				resultado = "Error deactivating: " + candAlumno.getCodigoPersonal() + " - " + candAlumno.getFolio();
			}
		} else {
			resultado = "Not found: " + candAlumno.getCodigoPersonal() + " - " + candAlumno.getFolio();
		}
		
		return "redirect:/candados/alumno/cand_alum?tipoId="+tipoId+"&folio="+folio+"&resultado="+resultado;
	}
	
}