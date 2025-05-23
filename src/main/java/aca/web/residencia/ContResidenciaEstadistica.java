package aca.web.residencia;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumAcademicoDao;
import aca.alumno.spring.AlumDiasDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumUbicacion;
import aca.alumno.spring.AlumUbicacionDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;
import aca.carga.spring.CargaPracticaAlumno;
import aca.carga.spring.CargaPracticaAlumnoDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatModalidad;
import aca.catalogo.spring.CatModalidadDao;
import aca.catalogo.spring.CatPais;
import aca.catalogo.spring.CatPaisDao;
import aca.catalogo.spring.CatReligion;
import aca.catalogo.spring.CatReligionDao;
import aca.catalogo.spring.CatTipoAlumno;
import aca.catalogo.spring.CatTipoAlumnoDao;
import aca.catalogo.spring.ResRazonesDao;
import aca.financiero.spring.FesCcobro;
import aca.financiero.spring.FesCcobroDao;
import aca.internado.spring.ComAutorizacion;
import aca.internado.spring.ComAutorizacionDao;
import aca.pg.archivo.ArchResidencia;
import aca.pg.archivo.ArchResidenciaUtil;
import aca.residencia.spring.InternosDao;
import aca.residencia.spring.ResComentario;
import aca.residencia.spring.ResComentarioDao;
import aca.residencia.spring.ResDatos;
import aca.residencia.spring.ResDatosDao;
import aca.residencia.spring.ResRazonDao;
import aca.vista.spring.EstExternos;
import aca.vista.spring.EstExternosDao;
import aca.vista.spring.EstInternos;
import aca.vista.spring.EstInternosDao;
import aca.vista.spring.Estadistica;
import aca.vista.spring.EstadisticaDao;
import aca.vista.spring.Inscritos;
import aca.vista.spring.InscritosDao;

@Controller
public class ContResidenciaEstadistica{
	
	@Autowired	
	private InscritosDao inscritosDao;
	
	@Autowired	
	private CatFacultadDao catFacultadDao;
	
	@Autowired	
	private CatCarreraDao catCarreraDao;
	
	@Autowired	
	private ResRazonDao resRazonDao;
	
	@Autowired	
	private ResDatosDao resDatosDao;
	
	@Autowired	
	private CargaPracticaAlumnoDao cargaPracticaAlumnoDao;
	
	@Autowired	
	private CatModalidadDao catModalidadDao;
	
	@Autowired	
	private InternosDao internosDao;
	
	@Autowired	
	private CatReligionDao catReligionDao;
	
	@Autowired	
	private CatPaisDao catPaisDao;
	
	@Autowired	
	private CatTipoAlumnoDao catTipoAlumnoDao;
	
	@Autowired
	EstInternosDao estInternosDao;
	
	@Autowired
	EstExternosDao estExternosDao;
	
	@Autowired
	AccesoDao accesoDao;
	
	@Autowired
	AlumUbicacionDao alumUbicacionDao;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;
	
	@Autowired
	AlumAcademicoDao alumAcademicoDao;
	
	@Autowired
	EstadisticaDao estadisticaDao;
	
	@Autowired
	FesCcobroDao fesCcobroDao;
	
	@Autowired
	CargaDao cargaDao;
	
	@Autowired
	ComAutorizacionDao comAutorizacionDao;
	
	@Autowired
	AlumDiasDao alumDiasDao;
	
	@Autowired
	ResComentarioDao resComentarioDao;
	
	
	@RequestMapping("/residencia/estadistica/menu")
	public String residenciaEstadisticaMenu(HttpServletRequest request, Model modelo){
		
		String modalidades		= "1";
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){			
			modalidades 		= (String) sesion.getAttribute("modalidadesReportes");
		}
		
		List<CatModalidad> lisModalidades	= catModalidadDao.getListAll(" ORDER BY MODALIDAD_ID");		
		modelo.addAttribute("lisModalidades", lisModalidades);
		
		return "residencia/estadistica/menu";
	}
	
	@RequestMapping("/residencia/estadistica/internos_genero")
	public String residenciaEstadisticaInternosGenero(HttpServletRequest request, Model modelo){
		
		String fechaIni			= request.getParameter("FechaIni")==null?"0":request.getParameter("FechaIni");
		String fechaFin			= request.getParameter("FechaFin")==null?"0":request.getParameter("FechaFin");
		String modalidades		= "1";
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			if (fechaIni.equals("0")){ 
				fechaIni = (String) sesion.getAttribute("fechaIni");
			}else {
				sesion.setAttribute("fechaIni", fechaIni);				
			}
			if (fechaFin.equals("0")) {
				fechaFin = (String) sesion.getAttribute("fechaFin");
			}else {
				sesion.setAttribute("fechaFin", fechaFin);
			}
			modalidades 		= (String) sesion.getAttribute("modalidadesReportes");
		}
		List<Inscritos> lisInscritos 		 		= inscritosDao.getInternosPorGeneroModalidad(modalidades, fechaIni, fechaFin, "ORDER BY SEXO, APELLIDO_PATERNO,APELLIDO_MATERNO,NOMBRE,CARGA_PRIORIDAD(CARGA_ID),CARRERA_ID");
		List<CargaPracticaAlumno> lisFechas 		= cargaPracticaAlumnoDao.lisAlumnoFechas();
		
		HashMap<String,CatCarrera> mapaCarreras 	= catCarreraDao.getMapAll("");
		HashMap<String,String> mapaRequerido    	= alumAcademicoDao.mapaRequeridos();
		HashMap<String,Carga> mapaFechas			= cargaDao.mapaCargas(); 
		HashMap<String,String> mapaResidencias		= fesCcobroDao.mapaResidencia();
		HashMap<String,ComAutorizacion> mapaComidas	= comAutorizacionDao.mapComidasVigentes();
		HashMap<String,String> mapaDias				= alumDiasDao.mapaDiasInternado();
		
		
		modelo.addAttribute("lisInscritos", lisInscritos);
		modelo.addAttribute("lisFechas", lisFechas);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaRequerido", mapaRequerido);
		modelo.addAttribute("mapaFechas", mapaFechas);
		modelo.addAttribute("mapaResidencias", mapaResidencias);
		modelo.addAttribute("mapaComidas", mapaComidas);
		modelo.addAttribute("mapaDias", mapaDias);
		
		return "residencia/estadistica/internos_genero";
	}	
	
	@RequestMapping("/residencia/estadistica/externos_genero")
	public String residenciaEstadisticaExternosGenero(HttpServletRequest request, Model modelo){
		
		String fechaIni			= request.getParameter("FechaIni")==null?"0":request.getParameter("FechaIni");
		String fechaFin			= request.getParameter("FechaFin")==null?"0":request.getParameter("FechaFin");
		String modalidades		= "1";
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			if (fechaIni.equals("0")){ 
				fechaIni = (String) sesion.getAttribute("fechaIni");
			}else {
				sesion.setAttribute("fechaIni", fechaIni);				
			}
			if (fechaFin.equals("0")) {
				fechaFin = (String) sesion.getAttribute("fechaFin");
			}else {
				sesion.setAttribute("fechaFin", fechaFin);
			}
			modalidades 		= (String) sesion.getAttribute("modalidadesReportes");
		}
		List<Inscritos> lisInscritos 				= inscritosDao.getListInscritosExternos("ORDER BY SEXO, APELLIDO_PATERNO,APELLIDO_MATERNO,NOMBRE,ENOC.CARGA_PRIORIDAD(CARGA_ID),CARRERA_ID");
		List<ResDatos> lisDatos						= resDatosDao.getListExtInscritos("ORDER BY ALUM_APELLIDO(MATRICULA)");
		HashMap<String,CatCarrera> mapaCarreras 	= catCarreraDao.getMapAll("");
		HashMap<String, String>	mapaRazon  			= resRazonDao.getMapRazon(); 
		HashMap<String, ResComentario> mapaComentarios 	= resComentarioDao.mapaComentario();
		
		modelo.addAttribute("lisInscritos", lisInscritos);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaRazon", mapaRazon);
		modelo.addAttribute("lisDatos", lisDatos);
		modelo.addAttribute("mapaComentarios", mapaComentarios);
		
		return "residencia/estadistica/externos_genero";
	}
	
	@RequestMapping("/residencia/estadistica/estadisticas")
	public String residenciaEstadisticaEstadisticas(HttpServletRequest request, Model modelo){
		
		String fechaIni			= request.getParameter("FechaIni")==null?"0":request.getParameter("FechaIni");
		String fechaFin			= request.getParameter("FechaFin")==null?"0":request.getParameter("FechaFin");
		String opcion  			= request.getParameter("opcion")==null?"Todos":request.getParameter("opcion");		
		String modalidades		= "1";
		String dormitorio 		= "";
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){			
			if (fechaIni.equals("0")){ 
				fechaIni = (String) sesion.getAttribute("fechaIni");
			}else {
				sesion.setAttribute("fechaIni", fechaIni);				
			}
			if (fechaFin.equals("0")) {
				fechaFin = (String) sesion.getAttribute("fechaFin");
			}else {
				sesion.setAttribute("fechaFin", fechaFin);
			}
			modalidades 		= (String) sesion.getAttribute("modalidadesReportes");
		}
		
		if (opcion.equals("Todos")) 
			dormitorio = "1,2,3,4";
		else
			dormitorio = opcion;
		
		List<String> lisEdades 				= internosDao.getListEdadInternoModalidad( modalidades, dormitorio, fechaIni, fechaFin, "ORDER BY 1");
		List<String> lisReligiones 			= internosDao.getListReligionInternoModalidad( modalidades, dormitorio, fechaIni, fechaFin, "ORDER BY ENOC.NOMBRE_RELIGION(RELIGION_ID)");
		List<String> lisNaciones			= internosDao.getListNacionInternoModalidad( modalidades, dormitorio, fechaIni, fechaFin, "ORDER BY ENOC.NOMBRE_PAIS(NACIONALIDAD)");		
		List<String> lisTipos				= internosDao.getListTipoAlumnoInternoModalidad( modalidades, dormitorio, fechaIni, fechaFin, "ORDER BY NOMBRE_TIPOALUMNO(TIPO)");
		
		// map de edades
		java.util.HashMap<String, String> mapaEdades 		= internosDao.mapaEdadInternoModalidad(modalidades, dormitorio);
		// map de religiones
		java.util.HashMap<String, String> mapaReligiones	= internosDao.mapaReligionInternoModalidad(modalidades, dormitorio);
		// map de Nacionalidades
		java.util.HashMap<String, String> mapaNaciones		= internosDao.mapaNacionInternoModalidad( modalidades, dormitorio);
		// map de tipo de alumnos
		java.util.HashMap<String, String> mapaTipos			= internosDao.mapaTipoAlumnoInternoModalidad(modalidades, dormitorio);
		// map de catalogo de religiones
		HashMap<String, CatReligion> mapaCatReligiones		= catReligionDao.getMapAll("");
		// map de catalogo de religiones
		HashMap<String, CatPais> mapaPaises					= catPaisDao.getMapAll();
		// map de catalogo de religiones
		HashMap<String, CatTipoAlumno> mapaCatTipos			= catTipoAlumnoDao.getMapAll("");
		
		modelo.addAttribute("lisEdades", lisEdades);
		modelo.addAttribute("lisReligiones", lisReligiones);
		modelo.addAttribute("lisNaciones", lisNaciones);
		modelo.addAttribute("lisTipos", lisTipos);
		
		modelo.addAttribute("mapaEdades", mapaEdades);
		modelo.addAttribute("mapaReligiones", mapaReligiones);
		modelo.addAttribute("mapaNaciones", mapaNaciones);
		modelo.addAttribute("mapaTipos", mapaTipos);
		modelo.addAttribute("mapaCatReligiones", mapaCatReligiones);
		modelo.addAttribute("mapaPaises", mapaPaises);
		modelo.addAttribute("mapaCatTipos", mapaCatTipos);
		
		return "residencia/estadistica/estadisticas";
	}
	
	@RequestMapping("/residencia/estadistica/internos")
	public String residenciaEstadisticaInternos(HttpServletRequest request, Model modelo){
		
		String fechaIni			= request.getParameter("FechaIni")==null?"0":request.getParameter("FechaIni");
		String fechaFin			= request.getParameter("FechaFin")==null?"0":request.getParameter("FechaFin");				
		String modalidades		= "1";
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){			
			if (fechaIni.equals("0")){ 
				fechaIni = (String) sesion.getAttribute("fechaIni");
			}else {
				sesion.setAttribute("fechaIni", fechaIni);				
			}
			if (fechaFin.equals("0")) {
				fechaFin = (String) sesion.getAttribute("fechaFin");
			}else {
				sesion.setAttribute("fechaFin", fechaFin);
			}
			modalidades 		= (String) sesion.getAttribute("modalidadesReportes");
		}		
		
		List<CatCarrera> lisCarreras  		= catCarreraDao.getListAll("ORDER BY ENOC.FACULTAD(CARRERA_ID), CARRERA_ID");
		List<EstInternos> lisInternos	 	= estInternosDao.getListAll("WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS WHERE MODALIDAD_ID IN ("+modalidades+")) ORDER BY ENOC.FACULTAD(CARRERA_ID), CARRERA_ID");
		List<Inscritos> lisInscritos		= inscritosDao.getListAllPorFechas(modalidades, fechaIni, fechaFin, " ORDER BY ENOC.FACULTAD(CARRERA_ID), CARRERA_ID");
		
		HashMap<String,CatFacultad> mapaFacultades 	= catFacultadDao.getMapFacultad("");
		HashMap<String,CatCarrera> mapaCarreras 	= catCarreraDao.getMapAll("");				
		
		modelo.addAttribute("lisCarreras", lisCarreras);
		modelo.addAttribute("lisInternos", lisInternos);
		modelo.addAttribute("lisInscritos", lisInscritos);
		
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		
		return "residencia/estadistica/internos";
	}
	
	@RequestMapping("/residencia/estadistica/externos")
	public String residenciaEstadisticaExternos(HttpServletRequest request, Model modelo){
		
		String fechaIni			= request.getParameter("FechaIni")==null?"0":request.getParameter("FechaIni");
		String fechaFin			= request.getParameter("FechaFin")==null?"0":request.getParameter("FechaFin");
		String modalidades		= "1";
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){			
			if (fechaIni.equals("0")){ 
				fechaIni = (String) sesion.getAttribute("fechaIni");
			}else {
				sesion.setAttribute("fechaIni", fechaIni);				
			}
			if (fechaFin.equals("0")) {
				fechaFin = (String) sesion.getAttribute("fechaFin");
			}else {
				sesion.setAttribute("fechaFin", fechaFin);
			}
			modalidades 		= (String) sesion.getAttribute("modalidadesReportes");
		}
		
		List<CatCarrera> lisCarreras 					= catCarreraDao.lisEnExternos("ORDER BY ENOC.FACULTAD(CARRERA_ID), CARRERA_ID");
		List<EstExternos> lisExternos 					= estExternosDao.getListAllPorFecha(fechaIni, fechaFin, " AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS WHERE MODALIDAD_ID IN ("+modalidades+") ) ORDER BY ENOC.FACULTAD(CARRERA_ID), CARRERA_ID");		
		List<Inscritos> lisInscritos					= inscritosDao.getListAllPorFecha(fechaIni, fechaFin, " AND MODALIDAD_ID IN ("+modalidades+") ORDER BY ENOC.FACULTAD(CARRERA_ID), CARRERA_ID");		
		HashMap<String, CatCarrera> mapaCarreras  		= catCarreraDao.getMapAll("");		
		HashMap<String, CatFacultad> mapaFacultades		= catFacultadDao.getMapFacultad("");
		
		modelo.addAttribute("lisCarreras", lisCarreras);
		modelo.addAttribute("lisExternos", lisExternos);
		modelo.addAttribute("lisInscritos", lisInscritos);
		
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		
		return "residencia/estadistica/externos";
	}
	
	@RequestMapping("/residencia/estadistica/dormiX")
	public String residenciaEstadisticaDormiX(HttpServletRequest request, Model modelo){
		
		String fechaIni			= request.getParameter("FechaIni")==null?"0":request.getParameter("FechaIni");
		String fechaFin			= request.getParameter("FechaFin")==null?"0":request.getParameter("FechaFin");
		String modalidades		= "1";
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){			
			if (fechaIni.equals("0")){ 
				fechaIni = (String) sesion.getAttribute("fechaIni");
			}else {
				sesion.setAttribute("fechaIni", fechaIni);				
			}
			if (fechaFin.equals("0")) {
				fechaFin = (String) sesion.getAttribute("fechaFin");
			}else {
				sesion.setAttribute("fechaFin", fechaFin);
			}
			modalidades 		= (String) sesion.getAttribute("modalidadesReportes");
		}
		List<Inscritos> lisAlumnos		= inscritosDao.getListDormitorioModalidad(modalidades, "X", fechaIni, fechaFin, "ORDER BY APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE");				
		HashMap<String, CatCarrera> mapaCarreras  		= catCarreraDao.getMapAll("");
		
		modelo.addAttribute("lisAlumnos", lisAlumnos);	
		modelo.addAttribute("mapaCarreras", mapaCarreras);		
		
		return "residencia/estadistica/dormiX";
	}
	
	@RequestMapping("/residencia/estadistica/internos_tutor")
	public String residenciaEstadisticaInternosTutor(HttpServletRequest request, Model modelo){
		
		String fechaIni			= request.getParameter("FechaIni")==null?"0":request.getParameter("FechaIni");
		String fechaFin			= request.getParameter("FechaFin")==null?"0":request.getParameter("FechaFin");		
		String modalidades		= "1";
		String codigoPersonal 	= "-";
		Acceso acceso			= new Acceso();		
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){			
			if (fechaIni.equals("0")){ 
				fechaIni = (String) sesion.getAttribute("fechaIni");
			}else {
				sesion.setAttribute("fechaIni", fechaIni);				
			}
			if (fechaFin.equals("0")) {
				fechaFin = (String) sesion.getAttribute("fechaFin");
			}else {
				sesion.setAttribute("fechaFin", fechaFin);
			}
			modalidades 		= (String) sesion.getAttribute("modalidadesReportes");
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
			acceso 				= accesoDao.mapeaRegId(codigoPersonal);
		}
		
		List<Inscritos> lisAlumnos		= inscritosDao.lisModalidadesPorFechasyResidencia(modalidades, fechaIni, fechaFin, "I"," ORDER BY FACULTAD(CARRERA_ID), CARRERA_ID, NOMBRE, SEXO");				
		HashMap<String, CatCarrera> mapaCarreras  		= catCarreraDao.getMapAll("");		
		HashMap<String, CatFacultad> mapaFacultades 	= catFacultadDao.getMapFacultad("");
		HashMap<String, AlumUbicacion> mapaUbicaciones 	= alumUbicacionDao.mapInscritos();
		
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("lisAlumnos", lisAlumnos);	
		modelo.addAttribute("mapaCarreras", mapaCarreras);		
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaUbicaciones", mapaUbicaciones);
		
		return "residencia/estadistica/internos_tutor";
	}
	
	@RequestMapping("/residencia/estadistica/externos_sin_registrar")
	public String residenciaEstadisticaExternosSinRegistrar(HttpServletRequest request, Model modelo){
		
		String fechaIni			= request.getParameter("FechaIni")==null?"0":request.getParameter("FechaIni");
		String fechaFin			= request.getParameter("FechaFin")==null?"0":request.getParameter("FechaFin");		
		String modalidades		= "1";						
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){			
			if (fechaIni.equals("0")){ 
				fechaIni = (String) sesion.getAttribute("fechaIni");
			}else {
				sesion.setAttribute("fechaIni", fechaIni);				
			}
			if (fechaFin.equals("0")) {
				fechaFin = (String) sesion.getAttribute("fechaFin");
			}else {
				sesion.setAttribute("fechaFin", fechaFin);
			}
			modalidades 		= (String) sesion.getAttribute("modalidadesReportes");		
		}
		
		List<Inscritos> lisAlumnos						= inscritosDao.getListExtSinRegModalidad( modalidades, fechaIni, fechaFin, " ORDER BY SEXO, NOMBRE");
		HashMap<String, CatCarrera> mapaCarreras  		= catCarreraDao.getMapAll("");		
		HashMap<String, String> mapaEdades 				= inscritosDao.mapaEdades();		
		
		modelo.addAttribute("lisAlumnos", lisAlumnos);	
		modelo.addAttribute("mapaCarreras", mapaCarreras);		
		modelo.addAttribute("mapaEdades", mapaEdades);
		
		return "residencia/estadistica/externos_sin_registrar";
	}
	
	@RequestMapping("/residencia/estadistica/externos_tutor")
	public String residenciaEstadisticaExternosTutor(HttpServletRequest request, Model modelo){
		
		String fechaIni			= request.getParameter("FechaIni")==null?"0":request.getParameter("FechaIni");
		String fechaFin			= request.getParameter("FechaFin")==null?"0":request.getParameter("FechaFin");		
		String modalidades		= "1";						
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){			
			if (fechaIni.equals("0")){ 
				fechaIni = (String) sesion.getAttribute("fechaIni");
			}else {
				sesion.setAttribute("fechaIni", fechaIni);				
			}
			if (fechaFin.equals("0")) {
				fechaFin = (String) sesion.getAttribute("fechaFin");
			}else {
				sesion.setAttribute("fechaFin", fechaFin);
			}
			modalidades 		= (String) sesion.getAttribute("modalidadesReportes");
		}
		
		List<Inscritos> lisAlumnos = inscritosDao.getExternosPorFechas( fechaIni, fechaFin, modalidades, " ORDER BY FACULTAD(CARRERA_ID), CARRERA_ID, NOMBRE");
		
		HashMap<String, ResDatos> mapaDatos 			= resDatosDao.mapaDatosInscritos();
		HashMap<String, CatFacultad> mapaFacultades 	= catFacultadDao.getMapFacultad("");
		HashMap<String, CatCarrera> mapaCarreras  		= catCarreraDao.getMapAll("");		
		HashMap<String, String>	mapaRazones 			= resRazonDao.getMapRazon();
		HashMap<String, ResComentario> mapaComentarios 	= resComentarioDao.mapaComentario();
		
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("mapaDatos", mapaDatos);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);		
		modelo.addAttribute("mapaRazones", mapaRazones);
		modelo.addAttribute("mapaComentarios", mapaComentarios);
		
		return "residencia/estadistica/externos_tutor";
	}
	
	@RequestMapping("/residencia/estadistica/tutor")
	public String residenciaEstadisticaTutor(HttpServletRequest request, Model modelo){
		
		String fechaIni			= request.getParameter("FechaIni")==null?"0":request.getParameter("FechaIni");
		String fechaFin			= request.getParameter("FechaFin")==null?"0":request.getParameter("FechaFin");		
		String modalidades		= "1";						
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){			
			if (fechaIni.equals("0")){ 
				fechaIni = (String) sesion.getAttribute("fechaIni");
			}else {
				sesion.setAttribute("fechaIni", fechaIni);				
			}
			if (fechaFin.equals("0")) {
				fechaFin = (String) sesion.getAttribute("fechaFin");
			}else {
				sesion.setAttribute("fechaFin", fechaFin);
			}
			modalidades 		= (String) sesion.getAttribute("modalidadesReportes");			
		}
		
		List<Inscritos> lisAlumnos = inscritosDao.getListAllModalidadesPorFechas( modalidades, fechaIni, fechaFin, "ORDER BY ENOC.FACULTAD_NOMBRE(ENOC.FACULTAD(CARRERA_ID)), ENOC.NOMBRE_CARRERA(CARRERA_ID), APELLIDO_PATERNO||' '||APELLIDO_MATERNO||' '||NOMBRE");
		
		HashMap<String,CatTipoAlumno> mapaTipos 		= catTipoAlumnoDao.getMapAll("");		
		HashMap<String, AlumUbicacion> mapaUbicaciones 	= alumUbicacionDao.mapInscritos();
		HashMap<String, CatCarrera> mapaCarreras 		= catCarreraDao.getMapAll("");
		
		modelo.addAttribute("lisAlumnos", lisAlumnos);		
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaTipos", mapaTipos);
		modelo.addAttribute("mapaUbicaciones", mapaUbicaciones);
		
		return "residencia/estadistica/tutor";
	}
	
	@RequestMapping("/residencia/estadistica/direcciones")
	public String residenciaEstadisticaDirecciones(HttpServletRequest request, Model modelo){
		
		String fechaIni			= request.getParameter("FechaIni")==null?"0":request.getParameter("FechaIni");
		String fechaFin			= request.getParameter("FechaFin")==null?"0":request.getParameter("FechaFin");
		String modalidades		= "1";						
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){			
			if (fechaIni.equals("0")){ 
				fechaIni = (String) sesion.getAttribute("fechaIni");
			}else {
				sesion.setAttribute("fechaIni", fechaIni);				
			}
			if (fechaFin.equals("0")) {
				fechaFin = (String) sesion.getAttribute("fechaFin");
			}else {
				sesion.setAttribute("fechaFin", fechaFin);
			}
			modalidades 		= (String) sesion.getAttribute("modalidadesReportes");			
		}
		
		List<Estadistica> lisAlumnos 					= estadisticaDao.lisModalidadesPorFechas( modalidades, fechaIni, fechaFin, "ORDER BY ENOC.FACULTAD_NOMBRE(ENOC.FACULTAD(CARRERA_ID)), ENOC.NOMBRE_CARRERA(CARRERA_ID), APELLIDO_PATERNO||' '||APELLIDO_MATERNO||' '||NOMBRE, CARGA_PRIORIDAD(CARGA_ID)");
		
		HashMap<String,ResDatos> mapaDatos 				= resDatosDao.mapaDatosInscritosPorFechas(fechaIni, fechaFin);
		HashMap<String, String>	mapaRazones 			= resRazonDao.getMapRazon();
		HashMap<String, CatFacultad> mapaFacultades 	= catFacultadDao.getMapFacultad("");
		HashMap<String, CatCarrera> mapaCarreras  		= catCarreraDao.getMapAll("");
		HashMap<String, String> mapaInscritos  		= inscritosDao.getMapaInscritos();
		
		modelo.addAttribute("lisAlumnos", lisAlumnos);		
		modelo.addAttribute("mapaDatos", mapaDatos);
		modelo.addAttribute("mapaRazones", mapaRazones);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaInscritos", mapaInscritos);
		
		return "residencia/estadistica/direcciones";
	}
}