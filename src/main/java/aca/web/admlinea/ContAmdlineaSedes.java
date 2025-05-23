package aca.web.admlinea;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.admision.spring.AdmSolicitud;
import aca.admision.spring.AdmAcademico;
import aca.admision.spring.AdmAcademicoDao;
import aca.admision.spring.AdmEvento;
import aca.admision.spring.AdmEventoDao;
import aca.admision.spring.AdmPeriodo;
import aca.admision.spring.AdmPeriodoDao;
import aca.admision.spring.AdmReservada;
import aca.admision.spring.AdmReservadaDao;
import aca.admision.spring.AdmSolicitudDao;
import aca.admision.spring.AdmTutor;
import aca.admision.spring.AdmTutorDao;
import aca.admision.spring.AdmUsuario;
import aca.admision.spring.AdmUsuarioDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCiudad;
import aca.catalogo.spring.CatCiudadDao;
import aca.catalogo.spring.CatEstado;
import aca.catalogo.spring.CatEstadoDao;
import aca.catalogo.spring.CatPais;
import aca.catalogo.spring.CatPaisDao;

@Controller
public class ContAmdlineaSedes {	
	
	@Autowired
	private AdmEventoDao admEventoDao;	

	@Autowired
	private AdmReservadaDao admReservadaDao;
	
	@Autowired
	private AdmSolicitudDao admSolicitudDao;
	
	@Autowired
	private AdmUsuarioDao admUsuarioDao;
	
	@Autowired
	private AdmAcademicoDao admAcademicoDao;
	
	@Autowired
	private aca.catalogo.spring.CatCarreraDao catCarreraDao;
	
	@Autowired
	private AccesoDao accesoDao;
	
	@Autowired
	private CatPaisDao catPaisDao;
	
	@Autowired
	private CatEstadoDao catEstadoDao;
	
	@Autowired
	private CatCiudadDao catCiudadDao;
	
	@Autowired
	private AdmPeriodoDao admPeriodoDao;
	
	@Autowired
	private AdmTutorDao admTutorDao;
	
	
	@RequestMapping("/admlinea/sedes/lista")
	public String admLineaSedesLista(HttpServletRequest request, Model modelo) {
		
		String codigoPersonal 	= "0";
		Acceso acceso 			= new Acceso();
		HttpSession sesion		= ((HttpServletRequest)request).getSession();		
		if (sesion != null){
			codigoPersonal 		= sesion.getAttribute("codigoPersonal").toString();
			acceso 				= accesoDao.mapeaRegId(codigoPersonal);
		}
		List<AdmEvento> lisEventos 					= admEventoDao.lisEstados("'A','I'","ORDER BY FECHA DESC");
		HashMap<String,String> mapaOcupados 		= admReservadaDao.mapaOcupadosPorEvento("'A','C'");
		HashMap<String, AdmPeriodo> mapaAdmPeriodo 	= admPeriodoDao.mapaAdmPerido();
		
		modelo.addAttribute("acceso",acceso);
		modelo.addAttribute("lisEventos",lisEventos);
		modelo.addAttribute("mapaOcupados",mapaOcupados);
		modelo.addAttribute("mapaAdmPeriodo",mapaAdmPeriodo);
		
		return "admlinea/sedes/lista";
	}
	
	@RequestMapping("/admlinea/sedes/modificaEstado")
	public String admLineaSedesModificaEstado(HttpServletRequest request, Model modelo) {	
		
		String eventoId = request.getParameter("EventoId")==null?"0":request.getParameter("EventoId");
		String estado 	= request.getParameter("Estado")==null?"A":request.getParameter("Estado");
		if (admEventoDao.existeReg(eventoId)) {
			admEventoDao.updateEstado(eventoId,estado);
		}
		
		return "redirect:/admlinea/sedes/lista";
	}
	
	@RequestMapping("/admlinea/sedes/nuevoEvento")
	public String admLineaSedesNuevoEvento(HttpServletRequest request, Model modelo) {	
		
		String eventoId = request.getParameter("EventoId")==null?"0":request.getParameter("EventoId");
		
		AdmEvento evento = new AdmEvento();
		
		if (admEventoDao.existeReg(eventoId)) {
			evento = admEventoDao.mapeaRegId(eventoId);
		}else {
			evento.setEventoId(admEventoDao.maximoReg());
		}

		modelo.addAttribute("evento",evento);
		modelo.addAttribute("mensaje","0");
		
		return "admlinea/sedes/nuevoEvento";
	}
	
	@RequestMapping("/admlinea/sedes/grabar")
	public String admLineaSedesGrabarEvento(HttpServletRequest request, Model modelo) {	
		
		String eventoId = request.getParameter("EventoId")==null?"0":request.getParameter("EventoId");
		String sede 	= request.getParameter("Sede")==null?"0":request.getParameter("Sede");
		String lugar 	= request.getParameter("Lugar")==null?"0":request.getParameter("Lugar");
		String fecha 	= request.getParameter("Fecha")==null?"0":request.getParameter("Fecha");
		String estado 	= request.getParameter("Estado")==null?"0":request.getParameter("Estado");
		String mensaje	= "0";
		
		AdmEvento evento = new AdmEvento();
		
		if (admEventoDao.existeReg(eventoId)) {
			evento = admEventoDao.mapeaRegId(eventoId);
			evento.setEventoNombre(sede);
			evento.setLugar(lugar);
			evento.setEstado(estado);
			evento.setFecha(fecha);
			
			if(admEventoDao.updateReg(evento)) {
				mensaje = "1";
			}
		}else {
			evento.setEventoId(eventoId);
			evento.setEventoNombre(sede);
			evento.setLugar(lugar);
			evento.setEstado(estado);
			evento.setFecha(fecha);
			
			if(admEventoDao.insertReg(evento)) {
				mensaje = "1";
			}
		}
		
		modelo.addAttribute("evento",evento);
		modelo.addAttribute("mensaje",mensaje);
		
		return "admlinea/sedes/nuevoEvento";
	}
	
	@RequestMapping("/admlinea/sedes/eliminarEvento")
	public String admLineaSedesEliminarEvento(HttpServletRequest request, Model modelo) {	
		
		String eventoId 	= request.getParameter("EventoId")==null?"0":request.getParameter("EventoId");
		String periodoId 	= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		
		if (admEventoDao.existeReg(eventoId)) {
			if(admEventoDao.deleteReg(eventoId)) {
				if(admPeriodoDao.existeRegEvento(eventoId)){
					AdmPeriodo admPeriodo = admPeriodoDao.mapeaRegId(eventoId, periodoId);
					admPeriodoDao.deleteReg(admPeriodo.getEventoId(), admPeriodo.getPeriodoId());
				}
			}
		}		
		return "redirect:/admlinea/sedes/lista";
	}

	@RequestMapping("/admlinea/sedes/alumnos")
	public String admLineaSedesAlumnos(HttpServletRequest request, Model modelo) {
		
		String eventoId = request.getParameter("EventoId")==null?"0":request.getParameter("EventoId");
		
		List<AdmReservada> lisReservados = admReservadaDao.lisEvento(eventoId,"'A','C'", "ORDER BY FECHA");
		
		HashMap<String,AdmSolicitud> mapaSolicitudes 	= admSolicitudDao.mapaAlumnosSede(eventoId);
		HashMap<String,AdmUsuario> mapaUsuarios 		= admUsuarioDao.mapaAlumnosSede(eventoId);
		HashMap<String,AdmAcademico> mapaAcademico 		= admAcademicoDao.mapaAlumnosSede(eventoId);
		HashMap<String,AdmTutor> mapaUbicacion 			= admTutorDao.mapaUbicacion();
		HashMap<String,CatCarrera> mapaCarreras 		= catCarreraDao.getMapAll("");
		HashMap<String,CatPais> mapaPais 				= catPaisDao.getMapAll();
		HashMap<String,CatEstado> mapaEstado 			= catEstadoDao.getMapAll();
		HashMap<String,CatCiudad> mapaCiudad 			= catCiudadDao.getMapAll("");
		
		modelo.addAttribute("lisReservados",lisReservados);
		modelo.addAttribute("mapaSolicitudes",mapaSolicitudes);
		modelo.addAttribute("mapaUsuarios",mapaUsuarios);
		modelo.addAttribute("mapaAcademico",mapaAcademico);
		modelo.addAttribute("mapaCarreras",mapaCarreras);
		modelo.addAttribute("mapaPais",mapaPais);
		modelo.addAttribute("mapaEstado",mapaEstado);
		modelo.addAttribute("mapaCiudad",mapaCiudad);
		modelo.addAttribute("mapaUbicacion",mapaUbicacion);
		
		return "admlinea/sedes/alumnos";
	}
	
	@RequestMapping("/admlinea/sedes/agregarPeriodo")
	public String admLineaSedesAgregarPeriodo(HttpServletRequest request, Model modelo) {	
		
		String eventoId 	= request.getParameter("EventoId")==null?"0":request.getParameter("EventoId");
		String periodoId 	= request.getParameter("PeriodoId")==null?"1":request.getParameter("PeriodoId");
		String mensaje 		= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		boolean existe 		= false;
		
		AdmPeriodo admPeriodo 			= new AdmPeriodo();
		List<AdmPeriodo> listPeriodos 	= new ArrayList<AdmPeriodo>();
		
		if (admPeriodoDao.existeRegEvento(eventoId)) {
			admPeriodo 		= admPeriodoDao.mapeaRegId(eventoId, periodoId);
			listPeriodos 	= admPeriodoDao.getListEvento(eventoId, "ORDER BY DIA");
			existe			= true;
		}
		
		modelo.addAttribute("admPeriodo",admPeriodo);
		modelo.addAttribute("listPeriodos",listPeriodos);
		modelo.addAttribute("mensaje",mensaje);
		modelo.addAttribute("existe",existe);
		
		return "admlinea/sedes/agregarPeriodo";
	}

	@RequestMapping("/admlinea/sedes/grabarPeriodo")
	public String admLineaSedesGrabarPeriodo(HttpServletRequest request, Model modelo) {	
		
		String eventoId 	= request.getParameter("EventoId")==null?"0":request.getParameter("EventoId");
		String dia 			= request.getParameter("Dia")==null?"0":request.getParameter("Dia");
		String periodo 		= request.getParameter("Periodo")==null?"1":request.getParameter("Periodo");
		String cupo 		= request.getParameter("Cupo")==null?"0":request.getParameter("Cupo");
		String horaInicia 	= request.getParameter("HoraInicia")==null?"0":request.getParameter("HoraInicia");
		String minutoInicia = request.getParameter("MinutoInicia")==null?"0":request.getParameter("MinutoInicia");
		String horaFin 		= request.getParameter("HoraFin")==null?"0":request.getParameter("HoraFin");
		String minutoFin 	= request.getParameter("MinutoFin")==null?"0":request.getParameter("MinutoFin");
		String mensaje = "0";
		
		AdmPeriodo admPeriodo 			= new AdmPeriodo();
		
		if (admPeriodoDao.existeReg(eventoId,periodo)) {
			admPeriodo = admPeriodoDao.mapeaRegId(eventoId,periodo);
			admPeriodo.setDia(dia);
			admPeriodo.setPeriodoId(periodo);
			admPeriodo.setCupo(cupo);
			admPeriodo.setHoraInicio(horaInicia);
			admPeriodo.setMinInicio(minutoInicia);
			admPeriodo.setHoraFin(horaFin);
			admPeriodo.setMinFin(minutoFin);
			
			if(admPeriodoDao.updateReg(admPeriodo)) {
				mensaje = "1";
			}
		}else {
			admPeriodo.setEventoId(eventoId);
			admPeriodo.setDia(dia);
			admPeriodo.setPeriodoId(periodo);
			admPeriodo.setCupo(cupo);
			admPeriodo.setHoraInicio(horaInicia);
			admPeriodo.setMinInicio(minutoInicia);
			admPeriodo.setHoraFin(horaFin);
			admPeriodo.setMinFin(minutoFin);
			
			if(admPeriodoDao.insertReg(admPeriodo)) {
				mensaje = "1";
			}
		}
		
		return "redirect:/admlinea/sedes/agregarPeriodo?Mensaje="+mensaje+"&EventoId="+eventoId+"&PeriodoId="+periodo;
	}
	
}
