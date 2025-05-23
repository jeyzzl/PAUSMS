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

import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.archivo.spring.ArchGruposCarrera;
import aca.archivo.spring.ArchGruposCarreraDao;
import aca.bitacora.spring.BitArea;
import aca.bitacora.spring.BitEstado;
import aca.bitacora.spring.BitEstadoDao;
import aca.bitacora.spring.BitEtiqueta;
import aca.bitacora.spring.BitTramite;
import aca.bitacora.spring.BitTramiteAlumno;
import aca.vista.spring.MaestrosDao;
import aca.vista.spring.UsuariosDao;

@Controller
public class ContBitacoraSeguimiento {
	
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
	BitEstadoDao bitEstadoDao;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;

	@Autowired
	ArchGruposCarreraDao archGruposCarreraDao;
	
	@Autowired
	UsuariosDao usuariosDao;
	
	@Autowired
	MaestrosDao maestrosDao;
	
	
	@RequestMapping("/bitacora/seguimiento/nuevaEtiqueta")
	public String bitacoraSeguimientoNuevaEtiqueta(HttpServletRequest request, Model modelo){
		
		String areaId 		= request.getParameter("AreaId")==null?"0":request.getParameter("AreaId");
		String tramiteId 	= request.getParameter("TramiteId")==null?"0":request.getParameter("TramiteId");
		String estadoId 	= request.getParameter("EstadoId")==null?"0":request.getParameter("EstadoId");
		String fechaInicio	= request.getParameter("FechaInicio")==null?"0":request.getParameter("FechaInicio");
		String fechaFinal	= request.getParameter("FechaFinal")==null?"0":request.getParameter("FechaFinal");
		String nombreArea 	= bitAreaDao.getAreaNombre(areaId);
		
		List<BitTramiteAlumno> lisTramitesFiltro 	= bitTramiteAlumnoDao.lisTramiteFiltros(areaId, tramiteId, estadoId, fechaInicio, fechaFinal, " ORDER BY TRAMITE_ID");
		HashMap<String, BitTramite> mapaTramites	= bitTramiteDao.mapTramites(); 
		HashMap<String, String> mapaAlumnos			= alumPersonalDao.mapaTramites();
		HashMap<String, String> mapaEstados			= bitEstadoDao.mapEstados();
		
		modelo.addAttribute("nombreArea",nombreArea);
		modelo.addAttribute("lisTramitesFiltro",lisTramitesFiltro);
		modelo.addAttribute("mapaTramites",mapaTramites);
		modelo.addAttribute("mapaAlumnos",mapaAlumnos);
		modelo.addAttribute("mapaEstados",mapaEstados);
		
		return "bitacora/seguimiento/nuevaEtiqueta";
	}	
	
	@RequestMapping("/bitacora/seguimiento/grabarEtiqueta")
	public String bitacoraSeguimientoGrabarEtiqueta(HttpServletRequest request, Model modelo){
		
		String codigoUsuario 	= "";  
		String areaId 			= request.getParameter("AreaId")==null?"0":request.getParameter("AreaId");
		String tramiteId 		= request.getParameter("TramiteId")==null?"0":request.getParameter("TramiteId");
		String estadoId 		= request.getParameter("EstadoId")==null?"0":request.getParameter("EstadoId");
		String fechaInicio		= request.getParameter("FechaInicio")==null?"0":request.getParameter("FechaInicio");
		String fechaFinal		= request.getParameter("FechaFinal")==null?"0":request.getParameter("FechaFinal");
		String comentario 		= request.getParameter("Comentario")==null?"0":request.getParameter("Comentario");
		String mensaje			= "-";
		int row 				= 0;
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoUsuario = (String)sesion.getAttribute("codigoPersonal");
        }
        
		// Guardar las etiquetas en los tramites seleccionados
		List<BitTramiteAlumno> lisTramitesFiltro 	= bitTramiteAlumnoDao.lisTramiteFiltros(areaId, tramiteId, estadoId, fechaInicio, fechaFinal, " ORDER BY TRAMITE_ID");
		
		for (BitTramiteAlumno tramite : lisTramitesFiltro) {
			if (request.getParameter(tramite.getFolio()) != null) {
				//Grabar
				BitEtiqueta etiqueta = new BitEtiqueta();
				etiqueta.setFolio(tramite.getFolio());				
				etiqueta.setAreaId(areaId);
				etiqueta.setComentario(comentario);
				etiqueta.setUsuario(codigoUsuario);
				etiqueta.setTurnado("-");
				etiqueta.setEtiquetaId(bitEtiquetaDao.maximoReg(tramite.getFolio()));
				if (bitEtiquetaDao.insertReg(etiqueta)) {
					 row++;
				}				
			}
		}
		if (row > 0) mensaje = "Se grabaron "+String.valueOf(row)+" etiquetas";
		
		return "redirect:/bitacora/seguimiento/nuevaEtiqueta?AreaId="+areaId+"&TramiteId="+tramiteId+"&EstadoId="+estadoId+"&FechaInicio="+fechaInicio+"&FechaFinal="+fechaFinal+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/bitacora/seguimiento/seguimiento")
	public String bitacoraSeguimientoSeguimiento(HttpServletRequest request, Model modelo){	
		
		String codigoPersonal	= "0";
		String tramiteSesion	= "0";
		
		String areaId			= request.getParameter("AreaId") == null ? "0" : request.getParameter("AreaId");
		String tramiteId 		= request.getParameter("TramiteId") == null ? "0" : request.getParameter("TramiteId");
		String estadoId  		= request.getParameter("EstadoId") == null ? "0" : request.getParameter("EstadoId");
		
		String year 			= aca.util.Fecha.getHoy().substring(6, 10);
		String yearInicio		= String.valueOf(Integer.parseInt(year)-1);
		String fechaInicio 		= request.getParameter("FechaInicio") == null ? "01/01/"+yearInicio : request.getParameter("FechaInicio");
		String fechaFinal  		= request.getParameter("FechaFinal") == null ? "31/12/"+year : request.getParameter("FechaFinal");

		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal = (String)sesion.getAttribute("codigoPersonal");
			tramiteSesion = (String)sesion.getAttribute("tramite");
			// Actualizar tramite de sesion
			if ( !tramiteSesion.equals(tramiteId) && !tramiteId.equals("0")){
				sesion.setAttribute("tramite", tramiteId);
			}else if (tramiteId.equals("0") && request.getParameter("TramiteId") == null){
		 		tramiteId = (String)sesion.getAttribute("tramite");
		 	}
			
			sesion.setAttribute("fechaInicio",fechaInicio);
			sesion.setAttribute("fechaFinal",fechaFinal);
		}	
		
		
		List<BitEstado> lisEstados  = bitEstadoDao.lisEstados("ORDER BY ESTADO");
		List<BitArea> lisAreas		= bitAreaDao.lisAreasUsuario(codigoPersonal, "ORDER BY AREA_ID");
		
		List<BitTramite> lisTramites 					= bitTramiteDao.lisTramites(areaId, "ORDER BY TRAMITE_ID");
		List<BitTramiteAlumno> lisTramitesAlumnos 		= bitTramiteAlumnoDao.lisTramiteFiltros(areaId, tramiteId, estadoId, fechaInicio, fechaFinal, " ORDER BY ENOC.BIT_TRAMITE_ALUMNO.FECHA_INICIO");
		HashMap<String,BitTramite> mapaTramite 			= bitTramiteDao.mapTramites();
		HashMap<String,String> mapaEstado 				= bitEstadoDao.mapEstados();
		HashMap<String,String> mapaHijos 				= bitTramiteAlumnoDao.mapCuentaHijos("1,2,3");	
		HashMap<String,String> mapaTramSinEtiSinOrig	= bitTramiteAlumnoDao.mapTramitesSinOrigenSinEtiquetas();		
		HashMap<String,String> mapaCuentaEtiquetas		= bitEtiquetaDao.mapaCuentaEtiquetas();
		HashMap<String,String> mapaAreas				= bitAreaDao.mapaAreas();				
		HashMap<String,AlumPersonal> mapaAlumnos		= alumPersonalDao.mapaAlumnosConTramites();
		
		modelo.addAttribute("codigoPersonal",codigoPersonal);
		modelo.addAttribute("tramiteSesion",tramiteSesion);
		modelo.addAttribute("areaId",areaId);
		modelo.addAttribute("tramiteId",tramiteId);
		modelo.addAttribute("estadoId",estadoId);
		modelo.addAttribute("lisEstados",lisEstados);
		modelo.addAttribute("lisAreas",lisAreas);
		modelo.addAttribute("lisTramites",lisTramites);
		modelo.addAttribute("lisTramitesAlumnos",lisTramitesAlumnos);
		modelo.addAttribute("mapaTramite",mapaTramite);
		modelo.addAttribute("mapaEstado",mapaEstado);
		modelo.addAttribute("mapaHijos",mapaHijos);
		modelo.addAttribute("mapaTramSinEtiSinOrig",mapaTramSinEtiSinOrig);
		modelo.addAttribute("mapaCuentaEtiquetas",mapaCuentaEtiquetas);
		modelo.addAttribute("mapaAreas",mapaAreas);
		modelo.addAttribute("mapaAlumnos",mapaAlumnos);
		modelo.addAttribute("fechaInicio",fechaInicio);
		modelo.addAttribute("fechaFinal",fechaFinal);
		
		return "bitacora/seguimiento/seguimiento";
	}
	
	@RequestMapping("/bitacora/seguimiento/borrarTramite")
	public String bitacoraSeguimientoBorrarTramite(HttpServletRequest request, Model modelo){
		
		String folio = request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String areaId			= request.getParameter("AreaId") == null ? "0" : request.getParameter("AreaId");
		String tramiteId 		= request.getParameter("TramiteId") == null ? "0" : request.getParameter("TramiteId");
		String estadoId  		= request.getParameter("EstadoId") == null ? "0" : request.getParameter("EstadoId");
		String year 			= aca.util.Fecha.getHoy().substring(6, 10);
		String yearInicio		= String.valueOf(Integer.parseInt(year)-1);
		String fechaInicio 		= request.getParameter("FechaInicio") == null ? "01/01/"+yearInicio : request.getParameter("FechaInicio");
		String fechaFinal  		= request.getParameter("FechaFinal") == null ? "31/12/"+year : request.getParameter("FechaFinal");
		
		bitTramiteAlumnoDao.deleteReg(folio);

		return "redirect:/bitacora/seguimiento/seguimiento?AreaId="+areaId+"&TramiteId="+tramiteId+"&EstadoId="+estadoId+"&FechaInicio="+fechaInicio+"&FechaFinal="+fechaFinal;
	}
	
	@RequestMapping("/bitacora/seguimiento/etiquetas")
	public String bitacoraSeguimientoEtiquetas(HttpServletRequest request, Model modelo){
		
		String folio 		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");		
		String codigoPersonal 	= "0";
		
		HttpSession sesion	= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal 	= (String)sesion.getAttribute("codigoPersonal");			
		}
		
		BitTramiteAlumno bitTramiteAlumno = bitTramiteAlumnoDao.mapeaRegId(folio);
		BitTramite bitTramite			  = bitTramiteDao.mapeaRegId(bitTramiteAlumno.getTramiteId());
		
		String minimo   	= bitTramiteAlumnoDao.getTiempoMinimoTramite(bitTramiteAlumno.getTramiteId());
		String maximo  		= bitTramiteAlumnoDao.getTiempoMaximoTramite(bitTramiteAlumno.getTramiteId());
		String promedio 	= bitTramiteAlumnoDao.getPromedioTramite(bitTramiteAlumno.getTramiteId());
		int total			= bitTramiteAlumnoDao.getNumTerminados(bitTramiteAlumno.getTramiteId());
		
		// Ajustar días para los trámites de tiempo corto
		if (total >= 1 && minimo.equals("0")) minimo = "1";
		if (total >= 1 && maximo.equals("0")) maximo = "1";
		if (total >= 1 && promedio.equals("0")) promedio = "1";
		
		bitTramiteDao.actualizaTiempos(minimo, maximo, promedio, bitTramiteAlumno.getTramiteId());
		
		BitEtiqueta bitEtiqueta = new BitEtiqueta();
		
		if (bitTramiteAlumno.getEstado().equals("2")){
			bitTramiteAlumno.setEstado("3");
			if(bitTramiteAlumnoDao.updateReg(bitTramiteAlumno)){
				bitEtiqueta.setFolio(folio);				
				bitEtiqueta.setAreaId(bitTramiteAlumno.getAreaId());
				bitEtiqueta.setComentario("Inicio del proceso");
				bitEtiqueta.setUsuario(codigoPersonal);
				bitEtiqueta.setTurnado("-");
				bitEtiqueta.setEtiquetaId(bitEtiquetaDao.maximoReg(folio));
				if (bitEtiquetaDao.insertReg(bitEtiqueta)){
					bitTramite	= bitTramiteDao.mapeaRegId(bitTramiteAlumno.getTramiteId());
				}
			}
		}
		
		List<BitEtiqueta> lisEtiquetas 				= bitEtiquetaDao.listEtiquetas(folio, " ORDER BY ENOC.BIT_ETIQUETA.FECHA");
		List<BitEtiqueta> lisTurnadas				= bitEtiquetaDao.listEtiquetasAlumno(bitTramiteAlumno.getCodigoPersonal(), "ORDER BY FOLIO, ENOC.BIT_ETIQUETA.FECHA");
		
		String dependientes		= String.valueOf( bitTramiteAlumnoDao.getNumDependientesSinTerminar(folio));
		String foliosDep 		= bitTramiteAlumnoDao.getDependientes(folio);
		
		HashMap<String, BitTramiteAlumno> mapTramitesAlumno = bitTramiteAlumnoDao.mapTramitesAlumno(bitTramiteAlumno.getCodigoPersonal());
		HashMap<String, String> mapaAreas 					= bitAreaDao.mapaAreas();
		HashMap<String, String> mapEstados 					= bitEstadoDao.mapEstados();
		HashMap<String, BitTramite> mapTramites				= bitTramiteDao.mapTramites();
		HashMap<String,String> mapaMaestros					= maestrosDao.mapaMaestroCorto("NOMBRE");
		
		String nombreUsuario 	= usuariosDao.getNombreUsuario(bitTramiteAlumno.getCodigoPersonal(), "NOMBRE");
		String nombreCorto	 	= usuariosDao.getNombreUsuarioCorto(bitTramiteAlumno.getCodigoPersonal());
		 			
		modelo.addAttribute("codigoPersonal",codigoPersonal);
		modelo.addAttribute("folio",folio);
		modelo.addAttribute("bitTramiteAlumno",bitTramiteAlumno);
		modelo.addAttribute("bitTramite",bitTramite);
		modelo.addAttribute("bitEtiqueta",bitEtiqueta);
		modelo.addAttribute("minimo",minimo);
		modelo.addAttribute("maximo",maximo);
		modelo.addAttribute("promedio",promedio);
		modelo.addAttribute("dependientes",dependientes);
		modelo.addAttribute("foliosDep",foliosDep);
		modelo.addAttribute("nombreUsuario",nombreUsuario);
		modelo.addAttribute("nombreCorto",nombreCorto);
		modelo.addAttribute("lisEtiquetas",lisEtiquetas);
		modelo.addAttribute("lisTurnadas",lisTurnadas);
		modelo.addAttribute("mapTramitesAlumno",mapTramitesAlumno);
		modelo.addAttribute("mapaAreas",mapaAreas);
		modelo.addAttribute("mapEstados",mapEstados);
		modelo.addAttribute("mapTramites",mapTramites);
		modelo.addAttribute("mapaMaestros",mapaMaestros);
		
		return "bitacora/seguimiento/etiquetas";
	}
	
	@RequestMapping("/bitacora/seguimiento/borrarEtiqueta")
	public String bitacoraSeguimientoBorrarEtiqueta(HttpServletRequest request, Model modelo){
		
		String etiquetaId 	= request.getParameter("EtiquetaId") == null ? "0" : request.getParameter("EtiquetaId");
		String folio 		= request.getParameter("Folio") == null ? "0" : request.getParameter("Folio");
		
		bitEtiquetaDao.deleteReg(folio,etiquetaId);
		
		return "redirect:/bitacora/seguimiento/etiquetas?Folio="+folio;
	}

	@RequestMapping("/bitacora/seguimiento/editarEtiqueta")
	public String bitacoraSeguimientoEditarEtiqueta(HttpServletRequest request, Model modelo){
		String codigoPersonal = "0";
		
		HttpSession sesion	= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal = (String)sesion.getAttribute("codigoPersonal");
		}	
		
		String folio 		= request.getParameter("Folio") == null ? "0" : request.getParameter("Folio");
		String etiquetaId 	= request.getParameter("EtiquetaId") == null ? "0" : request.getParameter("EtiquetaId");
		String comentario 	= request.getParameter("Comentario") == null ? "-" : request.getParameter("Comentario");
		
		BitTramiteAlumno bitTramiteAlumno	= bitTramiteAlumnoDao.mapeaRegId(folio);
		BitTramite bitTramite				= bitTramiteDao.mapeaRegId(bitTramiteAlumno.getTramiteId());
		BitEtiqueta	bitEtiqueta				= bitEtiquetaDao.mapeaRegId(folio, etiquetaId);
		
		String nombreUsuario 	= usuariosDao.getNombreUsuario(bitTramiteAlumno.getCodigoPersonal(), "NOMBRE");
		String nombreEstado 	= bitEstadoDao.getEstadoNombre(bitTramiteAlumno.getEstado());
		String nombreTramite 	= bitTramiteDao.getNombre(bitTramiteAlumno.getTramiteId());
		
		modelo.addAttribute("codigoPersonal",codigoPersonal);
		modelo.addAttribute("folio",folio);
		modelo.addAttribute("etiquetaId",etiquetaId);
		modelo.addAttribute("comentario",comentario);
		modelo.addAttribute("bitTramiteAlumno",bitTramiteAlumno);
		modelo.addAttribute("bitTramite",bitTramite);
		modelo.addAttribute("bitEtiqueta",bitEtiqueta);
		modelo.addAttribute("nombreUsuario",nombreUsuario);
		modelo.addAttribute("nombreEstado",nombreEstado);
		modelo.addAttribute("nombreTramite",nombreTramite);
		
		return "bitacora/seguimiento/editarEtiqueta";
	}

	@RequestMapping("/bitacora/seguimiento/updateComentario")
	public String bitacoraSeguimientoUpdateComentario(HttpServletRequest request, Model modelo){
		
		String folio 		= request.getParameter("Folio") == null ? "0" : request.getParameter("Folio");
		String etiquetaId 	= request.getParameter("EtiquetaId") == null ? "0" : request.getParameter("EtiquetaId");
		String comentario 	= request.getParameter("Comentario")==null?"-":request.getParameter("Comentario");
		
		if (bitEtiquetaDao.updateComentario(folio, etiquetaId, comentario)) {
			//System.out.println("Modificado...");
		}
		
		return "redirect:/bitacora/seguimiento/editarEtiqueta?Folio="+folio+"&EtiquetaId="+etiquetaId;
	}

	@RequestMapping("/bitacora/seguimiento/crearEtiqueta")
	public String bitacoraSeguimientoCrearEtiqueta(HttpServletRequest request, Model modelo){
		String codigoPersonal		= "0";

		HttpSession sesion	= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal = (String)sesion.getAttribute("codigoPersonal");
		}	
		
		String folio 		= request.getParameter("Folio") == null ? "0" : request.getParameter("Folio");
		String areaId 		= request.getParameter("AreaId") == null ? "0" : request.getParameter("AreaId");
		String etiquetaId	= request.getParameter("EtiquetaId")==null?"0":request.getParameter("EtiquetaId");
		String comentario	= request.getParameter("Comentario")==null?"-":request.getParameter("Comentario");
		String mensaje		= request.getParameter("Mensaje")==null?"X":request.getParameter("Mensaje");
		
		BitTramiteAlumno bitTramiteAlumno 	= bitTramiteAlumnoDao.mapeaRegId(folio);
		
		String nombreUsuario = usuariosDao.getNombreUsuario(bitTramiteAlumno.getCodigoPersonal(), "NOMBRE");
		String nombreArea = bitAreaDao.getAreaNombre(areaId);
		
		modelo.addAttribute("codigoPersonal",codigoPersonal);
		modelo.addAttribute("folio",folio);
		modelo.addAttribute("areaId",areaId);
		modelo.addAttribute("etiquetaId",etiquetaId);
		modelo.addAttribute("comentario",comentario);
		modelo.addAttribute("mensaje",mensaje);
		modelo.addAttribute("nombreUsuario",nombreUsuario);
		modelo.addAttribute("nombreArea",nombreArea);
		modelo.addAttribute("bitTramiteAlumno",bitTramiteAlumno);
		
		return "bitacora/seguimiento/crearEtiqueta";
	}

	@RequestMapping("/bitacora/seguimiento/guardarEtiqueta")
	public String bitacoraSeguimientoGuardarEtiqueta(HttpServletRequest request, Model modelo){
		String codigoPersonal		= "0";
		
		HttpSession sesion	= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal = (String)sesion.getAttribute("codigoPersonal");
		}	
		
		String folio 				= request.getParameter("Folio") == null ? "0" : request.getParameter("Folio");
		String areaId 				= request.getParameter("AreaId") == null ? "0" : request.getParameter("AreaId");
		String etiquetaId			= request.getParameter("EtiquetaId")==null?"0":request.getParameter("EtiquetaId");
		String comentario			= request.getParameter("Comentario")==null?"-":request.getParameter("Comentario");
		
		BitEtiqueta bitEtiqueta = new BitEtiqueta();
		
		bitEtiqueta.setAreaId(areaId);
		bitEtiqueta.setComentario(comentario);
		
		String mensaje = "X"; 				
 		bitEtiqueta.setFolio(folio);
 		bitEtiqueta.setUsuario(codigoPersonal);
 		bitEtiqueta.setTurnado("-");
 		if (etiquetaId.equals("0")){
 			etiquetaId = bitEtiquetaDao.maximoReg(folio);
 			bitEtiqueta.setEtiquetaId(etiquetaId);			
 		}else{
 			bitEtiqueta.setEtiquetaId(etiquetaId);
 		}
 		if (bitEtiquetaDao.existeRegId(folio, etiquetaId) ){
 			if(bitEtiquetaDao.updateReg(bitEtiqueta)){
 				mensaje = "Guardó";
 			}else{
 				mensaje = "No Guardó";
 			}
 		}else{
 			if(bitEtiquetaDao.insertReg(bitEtiqueta)){
 				mensaje = "Guardó";
 			}else{
 				mensaje = "No Guardó";
 			}
 		}
		
		return "redirect:/bitacora/seguimiento/crearEtiqueta?Folio="+folio+"&AreaId="+areaId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/bitacora/seguimiento/turnar")
	public String bitacoraSeguimientoTurnar(HttpServletRequest request, Model modelo){
		
		String folio			= request.getParameter("Folio") == null ? "0" : request.getParameter("Folio");		
		String codigoPersonal 	= "0";		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal 		= (String)sesion.getAttribute("codigoPersonal");			
		}
		
		BitTramiteAlumno bitTramiteAlumno = new BitTramiteAlumno();
		if (bitTramiteAlumnoDao.existeReg(folio)) {
			bitTramiteAlumno = bitTramiteAlumnoDao.mapeaRegId(folio);
		}
		
		String nombreAlumno 	= alumPersonalDao.getNombreAlumno(bitTramiteAlumno.getCodigoPersonal(), "NOMBRE");
		String nombreArea		 = bitAreaDao.getAreaNombre(bitTramiteAlumno.getAreaId());
		
		List<BitTramite> lisTramites 		= bitTramiteDao.lisTramitesPorTipos("'G','I'"," ORDER BY NOMBRE");
		HashMap<String,String> mapaAreas 	= bitAreaDao.mapaAreas(); 
		
		modelo.addAttribute("codigoPersonal",codigoPersonal);				
		modelo.addAttribute("nombreAlumno",nombreAlumno);
		modelo.addAttribute("nombreArea",nombreArea);
		modelo.addAttribute("bitTramiteAlumno",bitTramiteAlumno);
		modelo.addAttribute("lisTramites",lisTramites);
		modelo.addAttribute("mapaAreas",mapaAreas);
		
		return "bitacora/seguimiento/turnar";
	}

	@RequestMapping("/bitacora/seguimiento/grabarNuevoTramite")
	public String bitacoraSeguimientoGrabarNuevoTramite(HttpServletRequest request, Model modelo){
		
		String folio 			= request.getParameter("Folio") == null ? "0" : request.getParameter("Folio");
		String tramiteId 		= request.getParameter("TramiteId") == null ? "0" : request.getParameter("TramiteId");
		String comentario 		= request.getParameter("Comentario") == null ? "-" : request.getParameter("Comentario");
		
		String codigoPersonal 	= "0";		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal 		= (String)sesion.getAttribute("codigoPersonal");			
		}
		BitTramiteAlumno bitTramiteAlumno = new BitTramiteAlumno();
		if (bitTramiteAlumnoDao.existeReg(folio)) {
			bitTramiteAlumno = bitTramiteAlumnoDao.mapeaRegId(folio);
		}
		
		String mensaje			= "-";
		
		String areaNueva 		= bitTramiteDao.getAreaId(tramiteId);
		String areaNuevaNombre 	= bitAreaDao.getAreaNombre(areaNueva);
		String areaOrigenNombre	= bitAreaDao.getAreaNombre(bitTramiteAlumno.getAreaId());
		String tramiteNombre 	= bitTramiteDao.getNombre(tramiteId);
		
		String year 			= aca.util.Fecha.getHoy().substring(6, 10);		
				
		BitEtiqueta bitEtiqueta = new BitEtiqueta();
		
		String maxFolio 		= bitTramiteAlumnoDao.maximoReg(year);
		bitTramiteAlumno.setFolio(maxFolio);
		bitTramiteAlumno.setCodigoPersonal(bitTramiteAlumno.getCodigoPersonal());
		bitTramiteAlumno.setTramiteId(tramiteId);		
		bitTramiteAlumno.setAreaId(bitTramiteDao.getAreaId(tramiteId));
		bitTramiteAlumno.setFechaInicio(aca.util.Fecha.getHoy());
		bitTramiteAlumno.setFechaFinal(null);
		bitTramiteAlumno.setFolioOrigen(folio);
		bitTramiteAlumno.setAreaOrigen(bitTramiteAlumno.getAreaId());
		bitTramiteAlumno.setEstado("2");
		bitTramiteAlumno.setUsuario(codigoPersonal);
		bitTramiteAlumno.setComentario(comentario);		
		/* INSERTA EL NUEVO TRAMITE */
		if (bitTramiteAlumnoDao.insertReg(bitTramiteAlumno)){
			bitEtiqueta.setFolio(folio);			
			bitEtiqueta.setAreaId(bitTramiteAlumno.getAreaId()); //Mesa de entrada
			bitEtiqueta.setComentario("Turnado a "+areaNuevaNombre+" para tramite "+tramiteNombre);
			bitEtiqueta.setUsuario(codigoPersonal);
			bitEtiqueta.setTurnado(maxFolio);
			bitEtiqueta.setEtiquetaId(bitEtiquetaDao.maximoReg(folio));
			/*GRABA LA ETIQUETA DEL TRAMITE DE ORIGEN*/
			if (bitEtiquetaDao.insertReg(bitEtiqueta)){
			
				bitEtiqueta.setFolio(maxFolio);				
				bitEtiqueta.setAreaId(areaNueva); //Mesa de entrada
				bitEtiqueta.setComentario("Turnado de "+areaOrigenNombre);
				bitEtiqueta.setUsuario(codigoPersonal);
				bitEtiqueta.setTurnado("-");
				bitEtiqueta.setEtiquetaId(bitEtiquetaDao.maximoReg(maxFolio));
				/*GRABA LA ETIQUETA DEL TRAMITE DE DESTINO*/				
				if (bitEtiquetaDao.insertReg(bitEtiqueta)){
					mensaje = "¡ Turnado a "+areaNuevaNombre+" para tramite "+tramiteNombre+"!";
				}else{
					mensaje = "¡ No grabó la etiqueta de destino !";	
				}
			}else{
				mensaje = "¡ No grabó la etiqueta de origen !";
			}
		}else{
			mensaje = "¡ No grabó el tramite !";
		}	
		
		return "redirect:/bitacora/seguimiento/etiquetas?Folio="+folio+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/bitacora/seguimiento/cambiarEstado")
	public String bitacoraSeguimientoCambiarEstado(HttpServletRequest request, Model modelo){
		String codigoPersonal = "0";

		HttpSession sesion	= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal = (String)sesion.getAttribute("codigoPersonal");
		}	
		
		String folio 		= request.getParameter("Folio") == null ? "0" : request.getParameter("Folio");
		String comentario 	= request.getParameter("Comentario") == null ? "-" : request.getParameter("Comentario");
		String estadoId 	= request.getParameter("EstadoId") == null ? "-" : request.getParameter("EstadoId");
		String mensaje	 	= request.getParameter("Mensaje") == null ? "X" : request.getParameter("Mensaje");
		
		List<BitEstado> lisEstados 	= bitEstadoDao.lisEstados(" ORDER BY ESTADO");	
		
		BitTramiteAlumno bitTramiteAlumno 	= bitTramiteAlumnoDao.mapeaRegId(folio);
		BitTramite bitTramite	 			= bitTramiteDao.mapeaRegId(bitTramiteAlumno.getTramiteId());

		String nombreArea	 = bitAreaDao.getAreaNombre(bitTramite.getAreaId());
		
		modelo.addAttribute("codigoPersonal",codigoPersonal);
		modelo.addAttribute("folio",folio);
		modelo.addAttribute("comentario",comentario);
		modelo.addAttribute("estadoId",estadoId);
		modelo.addAttribute("mensaje",mensaje);
		modelo.addAttribute("nombreArea",nombreArea);
		modelo.addAttribute("lisEstados",lisEstados);
		
		return "bitacora/seguimiento/cambiarEstado";
	}

	@RequestMapping("/bitacora/seguimiento/grabarEstado")
	public String bitacoraSeguimientoGrabaCambiarEstado(HttpServletRequest request, Model modelo){
		String codigoPersonal = "0";

		HttpSession sesion	= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal = (String)sesion.getAttribute("codigoPersonal");
		}	
		
		String folio 		= request.getParameter("Folio") == null ? "0" : request.getParameter("Folio");
		String estadoId 	= request.getParameter("EstadoId") == null ? "0" : request.getParameter("EstadoId");
		String comentario 	= request.getParameter("Comentario") == null ? "-" : request.getParameter("Comentario");
		String mensaje		= "X";
		
		BitTramiteAlumno bitTramiteAlumno 	= bitTramiteAlumnoDao.mapeaRegId(folio);
		BitTramite bitTramite	 			= bitTramiteDao.mapeaRegId(bitTramiteAlumno.getTramiteId());	
		
		boolean grabar = false;

		BitEtiqueta bitEtiqueta = new BitEtiqueta();
		
		String hoy = aca.util.Fecha.getHoy();
		
		bitEtiqueta.setAreaId(bitTramiteAlumno.getAreaId());
		bitEtiqueta.setComentario(comentario);		
		bitEtiqueta.setFolio(folio);
		bitEtiqueta.setUsuario(codigoPersonal);
		bitEtiqueta.setTurnado("-");
		bitEtiqueta.setEtiquetaId(bitEtiquetaDao.maximoReg(folio));
		if(bitEtiquetaDao.insertReg(bitEtiqueta)){
			// Poner fecha final al tramite
			if (estadoId.equals("4")||estadoId.equals("5") && bitTramiteAlumno.getFechaFinal()==null){
				bitTramiteAlumno.setFechaFinal(hoy);				
			}			
			bitTramiteAlumno.setEstado(estadoId);
			if (bitTramiteAlumnoDao.updateReg(bitTramiteAlumno)){
				mensaje = "Guardo";
				grabar = true;
			}else{
				mensaje = "¡ No Guardó !";
			}			
		}else{
			mensaje = "¡ No Guardó !";
		}

		if (grabar){
			// Promediar los tiempos estadísticos
			if (estadoId.equals("4") || estadoId.equals("5")){			
				String minimo 	= bitTramiteDao.minTiempoTramite(bitTramite.getTramiteId(), "4,5");
				if (minimo.equals("0")) minimo = "1"; 
				String maximo 	= bitTramiteDao.maxTiempoTramite(bitTramite.getTramiteId(), "4,5");
				String promedio = bitTramiteDao.promedioTiempoTramite(bitTramite.getTramiteId(), "4,5");
				//System.out.println("Datos:"+minimo+":"+maximo+":"+promedio+" Tramite="+bitTramite.getTramiteId());
				if (bitTramiteDao.actualizaTiempos(minimo, maximo, promedio, bitTramite.getTramiteId())){
					mensaje = "Guardo y actualizó tiempos";
				}
			}
		}
		
		return "redirect:/bitacora/seguimiento/cambiarEstado?Folio="+folio+"&Comentario="+comentario+"&EstadoId="+estadoId+"&Mensaje="+mensaje;
	}
		
}