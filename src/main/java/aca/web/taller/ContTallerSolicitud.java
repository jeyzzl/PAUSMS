package aca.web.taller;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumDocumento;
import aca.alumno.spring.AlumDocumentoDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.bec.spring.BecAcuerdo;
import aca.bec.spring.BecAcuerdoDao;
import aca.bec.spring.BecInforme;
import aca.bec.spring.BecInformeAlumno;
import aca.bec.spring.BecInformeAlumnoDao;
import aca.bec.spring.BecInformeDao;
import aca.bec.spring.BecPuestoAlumno;
import aca.bec.spring.BecPuestoAlumnoDao;
import aca.bec.spring.BecSolPeriodo;
import aca.bec.spring.BecSolPeriodoDao;
import aca.bec.spring.BecSolicitud;
import aca.bec.spring.BecSolicitudDao;
import aca.bec.spring.BecTipo;
import aca.bec.spring.BecTipoDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.financiero.spring.ContCcosto;
import aca.financiero.spring.ContCcostoDao;
import aca.financiero.spring.ContEjercicio;
import aca.financiero.spring.ContEjercicioDao;
import aca.financiero.spring.FesCcobroDao;
import aca.financiero.spring.SunPlusFuncionDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;

@Controller
public class ContTallerSolicitud {
	
	@Autowired
	private AlumDocumentoDao alumDocumentoDao;
	
	@Autowired
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired
	private MapaPlanDao mapaPlanDao;
	
	@Autowired
	private BecSolicitudDao becSolicitudDao;
	
	@Autowired	
	private AccesoDao accesoDao;
	
	@Autowired	
	private BecSolPeriodoDao becSolPeriodoDao;
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;	
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/taller/solicitud/listado")
	public String tallerSolicitudListado(HttpServletRequest request, Model modelo){
		
		String fechaIni			=  request.getParameter("FechaIni")==null?"01"+aca.util.Fecha.getHoy().substring(2,10):request.getParameter("FechaIni");
		String fechaFin			=  request.getParameter("FechaFin")==null?aca.util.Fecha.getHoy():request.getParameter("FechaFin");
		String periodoId		=  request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		
		String tipos 			= request.getParameter("Tipos")==null?"T":request.getParameter("Tipos");
		if (tipos.equals("T")) tipos = "'S','A','I'"; else tipos = "'"+tipos+"'";
		
		List<AlumDocumento> lisSolicitudes						= alumDocumentoDao.lisTodosPorTipo("1", tipos, fechaIni+" 00:00:00", fechaFin+" 23:59:59", "ORDER BY PLAN_ID, FECHA");
		HashMap<String, AlumPersonal> mapaAlumnosEnSolicitud	= alumPersonalDao.mapaAlumnosEnSolicitud();
		HashMap<String, MapaPlan> mapaPlanes 					= mapaPlanDao.mapPlanes("'A','V','I'");
		HashMap<String, BecSolicitud> mapaSolicitudes			= becSolicitudDao.mapaSolicitudes();
		String codigo		= "";
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigo 	= (String) sesion.getAttribute("codigoPersonal");
        	sesion.setAttribute("fechaInicio",fechaIni);
			sesion.setAttribute("fechaFinal",fechaFin);
        }
		
		Acceso acceso = accesoDao.mapeaRegId(codigo);
		//BecSolicitud = becSolicitudDao.mapeaRegId(codigo, folio);
		
		HashMap<String,BecSolPeriodo> mapaBecSolPeriodo = becSolPeriodoDao.mapaBecSolPeriodo();
		List<BecSolPeriodo> lisBecSolPeriodo			= becSolPeriodoDao.getAll("");
			
		modelo.addAttribute("lisSolicitudes", lisSolicitudes);
		modelo.addAttribute("mapaAlumnosEnSolicitud", mapaAlumnosEnSolicitud);
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("mapaSolicitudes", mapaSolicitudes);
		modelo.addAttribute("mapaBecSolPeriodo", mapaBecSolPeriodo);
		modelo.addAttribute("lisBecSolPeriodo", lisBecSolPeriodo);
		
		return "taller/solicitud/listado";
	}
	
	@RequestMapping("/taller/solicitud/cambiarestado")
	public String tallerSolicitudCambiarEstado(HttpServletRequest request, Model modelo){		
		String codigoPersonal	= request.getParameter("Matricula")==null?"0":request.getParameter("Matricula");
		String folio 			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String estado 			= request.getParameter("Estado")==null?"0":request.getParameter("Estado");		
		
		if(alumDocumentoDao.existeReg(codigoPersonal, folio)) {
			if(alumDocumentoDao.updateEstado(codigoPersonal, folio, estado)){
				
			}
		}		
		return "redirect:/taller/solicitud/listado";
	}
	
	@RequestMapping("/taller/solicitud/nuevaBecSolicitud")
	public String tallerSolicitudNuevaBecSolicitud(HttpServletRequest request, Model modelo){
		String matricula 	= request.getParameter("Matricula")==null?"0":request.getParameter("Matricula");
		String folio 		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String mensaje 		= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");		
		
		BecSolicitud objeto = new BecSolicitud();
		
		if(becSolicitudDao.existeReg(matricula, folio)) {
			objeto = becSolicitudDao.mapeaRegId(matricula, folio);
		}else {
			objeto.setCodigoPersonal(matricula);
			objeto.setFolio(folio);
		}
		
		String nombreAlumno = alumPersonalDao.getNombre(objeto.getCodigoPersonal(), "");
		
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("becSolicitud", objeto);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		
		return "taller/solicitud/nuevaBecSolicitud";
	}
	
	@RequestMapping("/taller/solicitud/grabarBecSolicitud")
	public String tallerSolicitudGrabarBecSolicitud(HttpServletRequest request, Model modelo){
		String porCoordinador 	= request.getParameter("PorCoordinador")==null?"0":request.getParameter("PorCoordinador");
		String matricula 		= request.getParameter("Matricula")==null?"0":request.getParameter("Matricula");
		String folio 			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String tipos		= request.getParameter("Tipos")==null?"0":request.getParameter("Tipos");
		
		String comentario		= request.getParameter("Comentario")==null?"-":request.getParameter("Comentario");
		String mensaje 			= "";
		
		String codigoPersonal = "";	
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoPersonal 	= (String) sesion.getAttribute("codigoPersonal");
        }
		
		BecSolicitud objeto = new BecSolicitud();
		
		if(becSolicitudDao.existeReg(matricula, folio)) {
			objeto = becSolicitudDao.mapeaRegId(matricula, folio);
			objeto.setPorCoordinador(porCoordinador);
			objeto.setCoordinador(codigoPersonal);
			objeto.setComCoordinador(comentario);

			if(becSolicitudDao.updateReg(objeto)) {
				mensaje = "1";
			}
		}else {
			objeto.setCodigoPersonal(matricula);
			objeto.setFolio(folio);
			objeto.setPorCoordinador(porCoordinador);
			objeto.setCoordinador(codigoPersonal);
			objeto.setPorComision("0");			
			objeto.setUsuario(codigoPersonal);
			objeto.setComCoordinador(comentario);

			if(becSolicitudDao.insertReg(objeto)) {
				mensaje = "1";
			}
		}
		
		return "redirect:/taller/solicitud/nuevaBecSolicitud?Matricula="+matricula+"&Folio="+folio+"&Tipos="+tipos+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/taller/solicitud/nuevaPorComision")
	public String tallerSolicitudNuevaPorComision(HttpServletRequest request, Model modelo){
		String matricula 	= request.getParameter("Matricula")==null?"0":request.getParameter("Matricula");
		String folio 		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String mensaje 		= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");	
		
		BecSolicitud objeto = new BecSolicitud();
		
		if(becSolicitudDao.existeReg(matricula, folio)) {
			objeto = becSolicitudDao.mapeaRegId(matricula, folio);
		}else {
			objeto.setCodigoPersonal(matricula);
			objeto.setFolio(folio);
		}
		
		String nombreAlumno = alumPersonalDao.getNombre(objeto.getCodigoPersonal(), "");
		
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("becSolicitud", objeto);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		
		return "taller/solicitud/nuevaPorComision";
	}
	
	@RequestMapping("/taller/solicitud/grabarPorComision")
	public String tallerSolicitudGrabarPorComision(HttpServletRequest request, Model modelo){
		String porComision	 	= request.getParameter("PorComision")==null?"0":request.getParameter("PorComision");
		String matricula 		= request.getParameter("Matricula")==null?"0":request.getParameter("Matricula");
		String folio 			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String tipos			= request.getParameter("Tipos")==null?"0":request.getParameter("Tipos");
		String comentario		= request.getParameter("Comentario")==null?"0":request.getParameter("Comentario");
		String fecha 			= request.getParameter("Fecha")==null?"0":request.getParameter("Fecha");
		String mensaje 			= "";
		
		String codigoPersonal = "";	
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoPersonal 	= (String) sesion.getAttribute("codigoPersonal");
        }
		
		BecSolicitud objeto = new BecSolicitud();
		
		if(becSolicitudDao.existeReg(matricula, folio)) {
			objeto = becSolicitudDao.mapeaRegId(matricula, folio);
			objeto.setPorComision(porComision);
			objeto.setUsuario(codigoPersonal);
			objeto.setComentario(comentario);
			objeto.setFecha(fecha);

			if(becSolicitudDao.updateReg(objeto)) {
				mensaje = "1";
			}
		}else {
			objeto.setCodigoPersonal(matricula);
			objeto.setFolio(folio);
			objeto.setPorComision(porComision);
			objeto.setUsuario(codigoPersonal);
			objeto.setComentario(comentario);
			objeto.setFecha(fecha);

			if(becSolicitudDao.insertReg(objeto)) {
				mensaje = "1";
			}
		}
		
		return "redirect:/taller/solicitud/nuevaPorComision?Matricula="+matricula+"&Folio="+folio+"&Tipos="+tipos+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/taller/solicitud/bajarArchivo")
	public void tallerSolicitudBajarArchivo(HttpServletRequest request, HttpServletResponse response ) {
		
		String codigoPersonal		= request.getParameter("CodigoPersonal")==null?"0":request.getParameter("CodigoPersonal"); 
		String folio				= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		AlumDocumento alumDocumento = new AlumDocumento();
		try {						
			if(alumDocumentoDao.existeReg(codigoPersonal, folio)){
				alumDocumento = alumDocumentoDao.mapeaRegId(codigoPersonal, folio);			
				
				OutputStream out = response.getOutputStream();
				response.setHeader("Content-Disposition","attachment; filename=\""+ alumDocumento.getNombre() + "\"");
				out.write(alumDocumento.getArchivo());
				
			}
		}catch(Exception ex){
			System.out.println("Error:taller/solicitud/bajarArchivo:"+ex);
		}		
	}
	
}