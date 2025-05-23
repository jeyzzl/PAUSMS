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
import aca.admision.spring.AdmAcademico;
import aca.admision.spring.AdmAcademicoDao;
import aca.admision.spring.AdmEvento;
import aca.admision.spring.AdmEventoDao;
import aca.admision.spring.AdmPeriodo;
import aca.admision.spring.AdmPeriodoDao;
import aca.admision.spring.AdmReservada;
import aca.admision.spring.AdmReservadaDao;
import aca.admision.spring.AdmSolicitud;
import aca.admision.spring.AdmSolicitudDao;
import aca.admision.spring.AdmTutor;
import aca.admision.spring.AdmTutorDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCiudad;
import aca.catalogo.spring.CatCiudadDao;
import aca.catalogo.spring.CatEstado;
import aca.catalogo.spring.CatEstadoDao;
import aca.catalogo.spring.CatPais;
import aca.catalogo.spring.CatPaisDao;

@Controller
public class ContAmdlineaConsulta {	
	
	@Autowired
	private AdmEventoDao admEventoDao;	

	@Autowired
	private AdmReservadaDao admReservadaDao;	
	
	@Autowired
	private AdmSolicitudDao admSolicitudDao;
	
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
	
	
	@RequestMapping("/admlinea/consulta/lista")
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
		
		return "admlinea/consulta/lista";
	}
	
	@RequestMapping("/admlinea/consulta/alumnos")
	public String admLineaSedesAlumnos(HttpServletRequest request, Model modelo) {
		
		String eventoId = request.getParameter("EventoId")==null?"0":request.getParameter("EventoId");
		
		List<AdmReservada> lisReservados = admReservadaDao.lisEvento(eventoId,"'A','C'", " ORDER BY FECHA");
		
		HashMap<String,AdmSolicitud> mapaReservados 	= admSolicitudDao.mapaAlumnosSede(eventoId);
		HashMap<String,AdmAcademico> mapaAcademico 		= admAcademicoDao.mapaAlumnosSede(eventoId);
		HashMap<String,AdmTutor> mapaUbicacion 			= admTutorDao.mapaUbicacion();
		HashMap<String,CatCarrera> mapaCarreras 		= catCarreraDao.getMapAll("");
		HashMap<String,CatPais> mapaPais 				= catPaisDao.getMapAll();
		HashMap<String,CatEstado> mapaEstado 			= catEstadoDao.getMapAll();
		HashMap<String,CatCiudad> mapaCiudad 			= catCiudadDao.getMapAll("");
		
		modelo.addAttribute("lisReservados",lisReservados);
		modelo.addAttribute("mapaReservados",mapaReservados);
		modelo.addAttribute("mapaAcademico",mapaAcademico);
		modelo.addAttribute("mapaCarreras",mapaCarreras);
		modelo.addAttribute("mapaPais",mapaPais);
		modelo.addAttribute("mapaEstado",mapaEstado);
		modelo.addAttribute("mapaCiudad",mapaCiudad);
		modelo.addAttribute("mapaUbicacion",mapaUbicacion);
		
		return "admlinea/consulta/alumnos";
	}
	
	@RequestMapping("/admlinea/consulta/proyecto")
	public String admLineaSedesProyecto(HttpServletRequest request, Model modelo) {
		
		String numUno 	= request.getParameter("NumUno")==null?"0":request.getParameter("NumUno");
		String numDos 	= request.getParameter("NumDos")==null?"0":request.getParameter("NumDos");
		String accion 	= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		float resultado = 0;
		//System.out.println("Valores:"+numUno+":"+numDos+":"+accion);
		if (accion.equals("1")) resultado 	= Float.parseFloat(numUno) + Float.parseFloat(numDos);
		if (accion.equals("2")) resultado 	= Float.parseFloat(numUno) * Float.parseFloat(numDos);
		int numMayor = 0;
		if (accion.equals("3")) {
			for (int i=1; i<=5; i++){
				String numero 	= request.getParameter("Num"+i)==null?"0":request.getParameter("Num"+i);
				if (Integer.parseInt(numero)>numMayor) numMayor =Integer.parseInt(numero); 
			}
			System.out.println("Numero mayor:"+numMayor);
		}
		
		modelo.addAttribute("numUno", numUno);
		modelo.addAttribute("numDos", numDos);
		modelo.addAttribute("resultado", resultado);
		
		return "admlinea/consulta/proyecto";
	}
}
