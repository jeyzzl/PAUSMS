package aca.web.admlinea;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.admision.spring.AdmAcademico;
import aca.admision.spring.AdmAcademicoDao;
import aca.admision.spring.AdmEvaluacionNota;
import aca.admision.spring.AdmEvaluacionNotaDao;
import aca.admision.spring.AdmProcesoDao;
import aca.admision.spring.AdmPruebaAlumnoDao;
import aca.admision.spring.AdmSalud;
import aca.admision.spring.AdmSaludDao;
import aca.admision.spring.AdmSolicitud;
import aca.admision.spring.AdmSolicitudDao;
import aca.alumno.spring.AlumEstadoDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatEstado;
import aca.catalogo.spring.CatEstadoDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatModalidadDao;
import aca.catalogo.spring.CatPais;
import aca.catalogo.spring.CatPaisDao;
import aca.catalogo.spring.CatReligion;
import aca.catalogo.spring.CatReligionDao;
import aca.podium.spring.AspiranteDao;
import aca.podium.spring.Notas;
import aca.vista.spring.InscritosDao;

@Controller
public class ContAmdlineaEstadistica {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	AdmProcesoDao admProcesoDao;
	
	@Autowired
	AdmSolicitudDao admSolicitudDao;
	
	@Autowired
	AdmAcademicoDao admAcademicoDao;
	
	@Autowired
	AdmSaludDao admSaludDao;
	
	@Autowired
	CatPaisDao catPaisDao;
	
	@Autowired
	CatEstadoDao catEstadoDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	CatFacultadDao catFacultadDao;
	
	@Autowired
	CatReligionDao catReligionDao;
	
	@Autowired
	InscritosDao inscritosDao;
	
	@Autowired
	AlumEstadoDao alumEstadoDao;
	
	@Autowired
	CatModalidadDao catModalidadDao;
	
	@Autowired
	AspiranteDao aspiranteDao;
	
	@Autowired
	AdmEvaluacionNotaDao admEvaluacionNotaDao;
	
	@Autowired
	AdmPruebaAlumnoDao admPruebaAlumnoDao;
	
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/admlinea/estadistica/aceptados")
	public String admlineaEstadisticaAceptados(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContAdmLineaEstadistica|admlineaEstadisticaAceptados");
		return "admlinea/estadistica/aceptados";
	}
	
	@RequestMapping("/admlinea/estadistica/documentos")
	public String admlineaEstadisticaDocumentos(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContAdmLineaEstadistica|admlineaEstadisticaDocumentos");
		return "admlinea/estadistica/documentos";
	}
	
	@RequestMapping("/admlinea/estadistica/menu")
	public String admlineaEstadisticaMenu(HttpServletRequest request){		
		return "admlinea/estadistica/menu";
	}
	
	@RequestMapping("/admlinea/estadistica/reporte")
	public String admlineaEstadisticaReporte(HttpServletRequest request, Model modelo){	
		
		String fechaHoy			= aca.util.Fecha.getHoy();
		String yearNow			= fechaHoy.substring(6,10);
		String fechaIni 		= request.getParameter("fechaIni")==null?"01/01/"+String.valueOf(yearNow):request.getParameter("fechaIni");
		String fechaFin 		= request.getParameter("fechaFin")==null?fechaHoy:request.getParameter("fechaFin");		
		String estados 			= request.getParameter("Estados") == null ? "0" : request.getParameter("Estados");
		String modalidades		= (request.getParameter("Modalidades") == null ? "0" : request.getParameter("Modalidades"));
		String nombreAlumno		= (request.getParameter("NombreAlumno") == null ? "0" : request.getParameter("NombreAlumno"));
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();		
		if (sesion != null){
			sesion.setAttribute("fechaInicio",fechaIni);
			sesion.setAttribute("fechaFinal",fechaFin);
			sesion.setAttribute("estados",estados);			
			sesion.setAttribute("modalidades",modalidades);
		}
		
		if (estados.equals("0")) estados = "'0','1','2','3','4','5','6'"; else estados = "'"+estados+"'";		
		
		String condicion 		= " AND TO_DATE(TO_CHAR(SOL.FECHA,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY') AND SOL.ESTADO IN ("+estados+")";			
		
		if(modalidades.equals("0")) {
			condicion += " AND SOL.FOLIO IN (SELECT FOLIO FROM SALOMON.ADM_ACADEMICO)";
		}else if(modalidades.equals("1")) {
			condicion += " AND SOL.FOLIO IN (SELECT FOLIO FROM SALOMON.ADM_ACADEMICO WHERE MODALIDAD_ID = 1 )";			
		}else if(modalidades.equals("2")) {
			condicion += " AND SOL.FOLIO IN (SELECT FOLIO FROM SALOMON.ADM_ACADEMICO WHERE MODALIDAD_ID != 1 )";			
		}
		
		List<AdmSolicitud> lisAlumnos 					= admSolicitudDao.listIngreso(condicion," ORDER BY SOL.FECHA DESC");
		
		HashMap<String, AdmAcademico> mapaAcademico 	= admAcademicoDao.mapaTodos();
		HashMap<String, AdmSalud> mapaSalud			 	= admSaludDao.mapaTodos();
		HashMap<String,CatCarrera> mapaCarreras 		= catCarreraDao.getMapAll("");
		HashMap<String,CatPais> mapaPaises 				= catPaisDao.getMapAll();
		HashMap<String,CatEstado> mapaEstados			= catEstadoDao.getMapAll();
		HashMap<String,CatFacultad> mapaFacultades		= catFacultadDao.getMapFacultad("");
		HashMap<String,CatReligion> mapaReligion		= catReligionDao.getMapAll("");
		HashMap<String,String> mapFechaRegistro			= admProcesoDao.mapFechaRegistro();
		HashMap<String,String> mapFechaSolicitud		= admProcesoDao.mapFechaSolicitud();
		HashMap<String,String> mapFechaDocumento		= admProcesoDao.mapFechaDocumentos();
		HashMap<String,String> mapFechaAceptado			= admProcesoDao.mapFechaAceptado();
		HashMap<String,AdmAcademico> mapModalidadId		= admAcademicoDao.mapaTodos();
		HashMap<String,String> mapModalidades			= catModalidadDao.mapModalidades("");
		HashMap<String,String> mapInscritos				= alumEstadoDao.mapaInscritosPorFecha(fechaIni, fechaFin); 
		HashMap<String,Notas> mapaNotasPorFolio			= aspiranteDao.mapaNotasPorFechaFolio(fechaIni, fechaFin); 
		HashMap<String,Date> mapaFechasPorFolio			= aspiranteDao.mapaFechaPorFolio(fechaIni, fechaFin); 
		HashMap<String,AdmEvaluacionNota> mapaNotas		= admEvaluacionNotaDao.mapaNotaResultados();
		HashMap<String,String> mapaPruebas				= admPruebaAlumnoDao.mapaPruebas();
	
		modelo.addAttribute("modalidades",modalidades);
		modelo.addAttribute("nombreAlumno",nombreAlumno);	
		modelo.addAttribute("lisAlumnos",lisAlumnos);
		modelo.addAttribute("mapaAcademico",mapaAcademico);
		modelo.addAttribute("mapaSalud",mapaSalud);
		modelo.addAttribute("mapaCarreras",mapaCarreras);
		modelo.addAttribute("mapaPaises",mapaPaises);
		modelo.addAttribute("mapaEstados",mapaEstados);
		modelo.addAttribute("mapaFacultades",mapaFacultades);
		modelo.addAttribute("mapaReligion",mapaReligion);
		modelo.addAttribute("mapFechaRegistro",mapFechaRegistro);
		modelo.addAttribute("mapFechaSolicitud",mapFechaSolicitud);
		modelo.addAttribute("mapFechaDocumento",mapFechaDocumento);
		modelo.addAttribute("mapFechaAceptado",mapFechaAceptado);
		modelo.addAttribute("mapModalidadId",mapModalidadId);
		modelo.addAttribute("mapModalidades",mapModalidades);
		modelo.addAttribute("mapInscritos",mapInscritos);
		modelo.addAttribute("mapaNotasPorFolio",mapaNotasPorFolio);
		modelo.addAttribute("mapaFechasPorFolio",mapaFechasPorFolio);
		modelo.addAttribute("mapaNotas",mapaNotas);
		modelo.addAttribute("mapaPruebas",mapaPruebas);
		
		return "admlinea/estadistica/reporte";
	}
	
	@RequestMapping("/admlinea/estadistica/reportePodium")
	public String admlineaEstadisticaReportePodium(HttpServletRequest request, Model modelo){	
		
		String fechaHoy			= aca.util.Fecha.getHoy();
		String yearNow			= fechaHoy.substring(6,10);
		String fechaIni 		= request.getParameter("fechaIni")==null?"01/01/"+String.valueOf(yearNow):request.getParameter("fechaIni");
		String fechaFin 		= request.getParameter("fechaFin")==null?fechaHoy:request.getParameter("fechaFin");		
		String estados 			= request.getParameter("Estados") == null ? "0" : request.getParameter("Estados");
		String nombreAlumno		= (request.getParameter("NombreAlumno") == null ? "0" : request.getParameter("NombreAlumno"));
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();		
		if (sesion != null){
			sesion.setAttribute("fechaInicio",fechaIni);
			sesion.setAttribute("fechaFinal",fechaFin);
			sesion.setAttribute("estados",estados);			
		}
		
		if (estados.equals("0")) estados = "'0','1','2','3','4','5','6'"; else estados = "'"+estados+"'";		
		
		String condicion 		= " AND TO_DATE(TO_CHAR(SOL.FECHA,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY') AND SOL.ESTADO IN ("+estados+")";			
		
		condicion += " AND SOL.FOLIO IN (SELECT FOLIO FROM SALOMON.ADM_ACADEMICO)";
		
		List<AdmSolicitud> lisAlumnos 					= admSolicitudDao.listIngreso(condicion," ORDER BY SOL.FECHA DESC");
		
		HashMap<String, AdmAcademico> mapaAcademico 	= admAcademicoDao.mapaTodos();
		HashMap<String,CatCarrera> mapaCarreras 		= catCarreraDao.getMapAll("");
		HashMap<String,CatFacultad> mapaFacultades		= catFacultadDao.getMapFacultad("");
		HashMap<String,String> mapFechaDocumento		= admProcesoDao.mapFechaDocumentos();
		HashMap<String,String> mapFechaAceptado			= admProcesoDao.mapFechaAceptado();
		HashMap<String,AdmAcademico> mapModalidadId		= admAcademicoDao.mapaTodos();
		HashMap<String,String> mapModalidades			= catModalidadDao.mapModalidades("");
		HashMap<String,String> mapInscritos				= alumEstadoDao.mapaInscritosPorFecha(fechaIni, fechaFin); 
		HashMap<String,Notas> mapaNotasPorFechaFolio	= aspiranteDao.mapaNotasPorFechaFolio(fechaIni, fechaFin); 
		HashMap<String,Date> mapaFechaPorFolio			= aspiranteDao.mapaFechaPorFolio(fechaIni, fechaFin); 
		
		modelo.addAttribute("nombreAlumno",nombreAlumno);	
		modelo.addAttribute("lisAlumnos",lisAlumnos);
		modelo.addAttribute("mapaAcademico",mapaAcademico);
		modelo.addAttribute("mapaCarreras",mapaCarreras);
		modelo.addAttribute("mapaFacultades",mapaFacultades);
		modelo.addAttribute("mapFechaDocumento",mapFechaDocumento);
		modelo.addAttribute("mapFechaAceptado",mapFechaAceptado);
		modelo.addAttribute("mapModalidadId",mapModalidadId);
		modelo.addAttribute("mapModalidades",mapModalidades);
		modelo.addAttribute("mapInscritos",mapInscritos);
		modelo.addAttribute("mapaNotasPorFechaFolio",mapaNotasPorFechaFolio);
		modelo.addAttribute("mapaFechaPorFolio",mapaFechaPorFolio);
		
		return "admlinea/estadistica/reportePodium";
	}
	
	@RequestMapping("/admlinea/estadistica/estadistica")
	public String admlineaEstadisticaEstadistica(HttpServletRequest request, Model modelo){
		String year = request.getParameter("Year") == null ? java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)+"" : request.getParameter("Year");
		
		int cantidadReg = admProcesoDao.getCantidad("WHERE F_REGISTRO IS NOT NULL AND TO_CHAR(F_REGISTRO, 'YYYY')='"+year+"'");
		int cantidadSol = admProcesoDao.getCantidad("WHERE F_SOLICITUD IS NOT NULL AND TO_CHAR(F_SOLICITUD, 'YYYY')='"+year+"'");
		int cantidadDoc = admProcesoDao.getCantidad("WHERE F_DOCUMENTOS IS NOT NULL AND TO_CHAR(F_DOCUMENTOS, 'YYYY')='"+year+"'");
		int cantidadAdm = admProcesoDao.getCantidad("WHERE F_ADMISION IS NOT NULL AND TO_CHAR(F_ADMISION, 'YYYY')='"+year+"'");
		int cantidadCar = admProcesoDao.getCantidad("WHERE F_CARTA IS NOT NULL AND TO_CHAR(F_CARTA, 'YYYY')='"+year+"'");
		
		List<Integer> listaAnios = new ArrayList<Integer>();
		for(int i=2; i<=6; i++){
			List<String> tmpLista = admProcesoDao.listaAnios(i, " ORDER BY 1");
			for(String x : tmpLista) {
				if(!listaAnios.contains(Integer.parseInt(x))) listaAnios.add(Integer.parseInt(x));
			}
		}
		
		Collections.sort(listaAnios);
		
		List<Integer> listaMeses = new ArrayList<Integer>();
		for(int i=2; i<=6; i++){
			List<String> tmpLista = admProcesoDao.listaMeses(i, year, "ORDER BY 1");
			for(String x : tmpLista) if(!listaMeses.contains(Integer.parseInt(x))) listaMeses.add(Integer.parseInt(x));
		}
		Collections.sort(listaMeses);
		
		HashMap<String, int[]> catidadPorMeses = new HashMap<String, int[]>();
		 
		for(int numMes : listaMeses){
			String tmpMes = numMes < 10 ? "0"+numMes : ""+numMes;
			String mes = aca.util.Fecha.getMesNombre(numMes);
			int cantidadRegMes = admProcesoDao.getCantidad("WHERE F_REGISTRO IS NOT NULL AND TO_CHAR(F_REGISTRO, 'MM')='"+tmpMes+"' AND TO_CHAR(F_REGISTRO, 'YYYY')='"+year+"'");
			int cantidadSolMes = admProcesoDao.getCantidad("WHERE F_SOLICITUD IS NOT NULL AND TO_CHAR(F_SOLICITUD, 'MM')='"+tmpMes+"' AND TO_CHAR(F_SOLICITUD, 'YYYY')='"+year+"'");
			int cantidadDocMes = admProcesoDao.getCantidad("WHERE F_DOCUMENTOS IS NOT NULL AND TO_CHAR(F_DOCUMENTOS, 'MM')='"+tmpMes+"' AND TO_CHAR(F_DOCUMENTOS, 'YYYY')='"+year+"'");
			int cantidadAdmMes = admProcesoDao.getCantidad("WHERE F_ADMISION IS NOT NULL AND TO_CHAR(F_ADMISION, 'MM')='"+tmpMes+"' AND TO_CHAR(F_ADMISION, 'YYYY')='"+year+"'");
			int cantidadCarMes = admProcesoDao.getCantidad("WHERE F_CARTA IS NOT NULL AND TO_CHAR(F_CARTA, 'MM')='"+tmpMes+"' AND TO_CHAR(F_CARTA, 'YYYY')='"+year+"'");
		
			int intArray[] = {cantidadRegMes,cantidadSolMes,cantidadDocMes,cantidadAdmMes,cantidadCarMes};
			
			catidadPorMeses.put(mes, intArray);
		}
		
		modelo.addAttribute("year", year);
		modelo.addAttribute("cantidadReg", cantidadReg);
		modelo.addAttribute("cantidadSol", cantidadSol);
		modelo.addAttribute("cantidadDoc", cantidadDoc);
		modelo.addAttribute("cantidadAdm", cantidadAdm);
		modelo.addAttribute("cantidadCar", cantidadCar);
		modelo.addAttribute("listaAnios", listaAnios);
		modelo.addAttribute("listaMeses", listaMeses);
		modelo.addAttribute("catidadPorMeses", catidadPorMeses);
		
		return "admlinea/estadistica/estadistica";
	}	
	
	@RequestMapping("/admlinea/estadistica/cantidadRegMes")
	public String admlineaEstadisticaCantidadRegMes(HttpServletRequest request, Model modelo){
		String year = request.getParameter("Year") == null ? java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)+"" : request.getParameter("Year");
		String mes  = request.getParameter("Mes") == null ? "01" : request.getParameter("Mes");
		
		HashMap<String,String> mapFecha 			= admProcesoDao.mapFechaRegistro(year);
		HashMap<String,AdmAcademico> mapAcademico 	= admAcademicoDao.mapaAlumnos(" WHERE FOLIO IN(SELECT FOLIO FROM SALOMON.ADM_PROCESO WHERE F_REGISTRO IS NOT NULL AND TO_CHAR(F_REGISTRO,'YYYY')= '"+year+"')");
		HashMap<String,CatPais> mapaPais 			= catPaisDao.getMapAll();
		HashMap<String,CatEstado> mapaEstado 		= catEstadoDao.getMapAll();
		HashMap<String,CatCarrera> mapaCarrera 		= catCarreraDao.mapaCarreras();
		
		modelo.addAttribute("year", year);
		modelo.addAttribute("mapFecha", mapFecha);
		modelo.addAttribute("mapAcademico", mapAcademico);
		modelo.addAttribute("mapaPais", mapaPais);
		modelo.addAttribute("mapaEstado", mapaEstado);
		modelo.addAttribute("mapaCarrera", mapaCarrera);		
		modelo.addAttribute("lisAlumnos", admSolicitudDao.listRegMes(year, mes));
		
		return "admlinea/estadistica/cantidadRegMes";
	}	
	
	@RequestMapping("/admlinea/estadistica/cantidadSolMes")
	public String admlineaEstadisticaCantidadSolMes(HttpServletRequest request, Model modelo){
		String year = request.getParameter("Year") == null ? java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)+"" : request.getParameter("Year");
		String mes  = request.getParameter("Mes") == null ? "01" : request.getParameter("Mes");
		
		HashMap<String,String> mapFecha 			= admProcesoDao.mapFechaSolicitud(year);
		HashMap<String,AdmAcademico> mapAcademico 	= admAcademicoDao.mapaAlumnos(" WHERE FOLIO IN(SELECT FOLIO FROM SALOMON.ADM_PROCESO WHERE F_ADMISION IS NOT NULL AND TO_CHAR(F_ADMISION,'YYYY')= '"+year+"')");
		HashMap<String,CatPais> mapaPais 			= catPaisDao.getMapAll();
		HashMap<String,CatEstado> mapaEstado 		= catEstadoDao.getMapAll();
		HashMap<String,CatCarrera> mapaCarrera 		= catCarreraDao.mapaCarreras();
		
		modelo.addAttribute("year", year);
		modelo.addAttribute("mapFecha", mapFecha);
		modelo.addAttribute("mapAcademico", mapAcademico);
		modelo.addAttribute("mapaPais", mapaPais);
		modelo.addAttribute("mapaEstado", mapaEstado);
		modelo.addAttribute("mapaCarrera", mapaCarrera);
		modelo.addAttribute("lisAlumnos", admSolicitudDao.listSolMes(year, mes));
		
		return "admlinea/estadistica/cantidadSolMes";
	}	

	@RequestMapping("/admlinea/estadistica/cantidadDocMes")
	public String admlineaEstadisticaCantidadDocMes(HttpServletRequest request, Model modelo){
		String year = request.getParameter("Year") == null ? java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)+"" : request.getParameter("Year");
		String mes  = request.getParameter("Mes") == null ? "01" : request.getParameter("Mes");
		
		HashMap<String,String> mapFecha 			= admProcesoDao.mapFechaDocumentos(year);
		HashMap<String,AdmAcademico> mapAcademico 	= admAcademicoDao.mapaAlumnos(" WHERE FOLIO IN(SELECT FOLIO FROM SALOMON.ADM_PROCESO WHERE F_ADMISION IS NOT NULL AND TO_CHAR(F_ADMISION,'YYYY')= '"+year+"')");
		HashMap<String,CatPais> mapaPais 			= catPaisDao.getMapAll();
		HashMap<String,CatEstado> mapaEstado 		= catEstadoDao.getMapAll();
		HashMap<String,CatCarrera> mapaCarrera 		= catCarreraDao.mapaCarreras();
		
		modelo.addAttribute("year", year);
		modelo.addAttribute("mapFecha", mapFecha);
		modelo.addAttribute("mapAcademico", mapAcademico);
		modelo.addAttribute("mapaPais", mapaPais);
		modelo.addAttribute("mapaEstado", mapaEstado);
		modelo.addAttribute("mapaCarrera", mapaCarrera);
		modelo.addAttribute("lisAlumnos", admSolicitudDao.listDocMes(year, mes));
		
		return "admlinea/estadistica/cantidadDocMes";
	}	
	
	@RequestMapping("/admlinea/estadistica/cantidadAdmMes")
	public String admlineaEstadisticaCantidadAdmMes(HttpServletRequest request, Model modelo){
		String year = request.getParameter("Year") == null ? java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)+"" : request.getParameter("Year");
		String mes  = request.getParameter("Mes") == null ? "01" : request.getParameter("Mes");
		
		HashMap<String,String> mapFecha 			= admProcesoDao.mapFechaAceptado(year);
		HashMap<String,AdmAcademico> mapAcademico 	= admAcademicoDao.mapaAlumnos(" WHERE FOLIO IN(SELECT FOLIO FROM SALOMON.ADM_PROCESO WHERE F_ADMISION IS NOT NULL AND TO_CHAR(F_ADMISION,'YYYY')= '"+year+"')");
		HashMap<String,CatPais> mapaPais 			= catPaisDao.getMapAll();
		HashMap<String,CatEstado> mapaEstado 		= catEstadoDao.getMapAll();
		HashMap<String,CatCarrera> mapaCarrera 		= catCarreraDao.mapaCarreras();
		
		modelo.addAttribute("year", year);
		modelo.addAttribute("mapFecha", mapFecha);
		modelo.addAttribute("mapAcademico", mapAcademico);
		modelo.addAttribute("mapaPais", mapaPais);
		modelo.addAttribute("mapaEstado", mapaEstado);
		modelo.addAttribute("mapaCarrera", mapaCarrera);
		modelo.addAttribute("lisAlumnos", admSolicitudDao.listAdmMes(year, mes));
		
		return "admlinea/estadistica/cantidadAdmMes";
	}	
	
	@RequestMapping("/admlinea/estadistica/cantidadCarMes")
	public String admlineaEstadisticaCantidadCarMes(HttpServletRequest request, Model modelo){
		String year = request.getParameter("Year") == null ? java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)+"" : request.getParameter("Year");
		String mes  = request.getParameter("Mes") == null ? "01" : request.getParameter("Mes");
		
		HashMap<String,String> mapFecha 			= admProcesoDao.mapFechaCarta(year);
		HashMap<String,AdmAcademico> mapAcademico 	= admAcademicoDao.mapaAlumnos(" WHERE FOLIO IN(SELECT FOLIO FROM SALOMON.ADM_PROCESO WHERE F_ADMISION IS NOT NULL AND TO_CHAR(F_ADMISION,'YYYY')= '"+year+"')");
		HashMap<String,CatPais> mapaPais 			= catPaisDao.getMapAll();
		HashMap<String,CatEstado> mapaEstado 		= catEstadoDao.getMapAll();
		HashMap<String,CatCarrera> mapaCarrera 		= catCarreraDao.mapaCarreras();
		
		modelo.addAttribute("year", year);
		modelo.addAttribute("mapFecha", mapFecha);
		modelo.addAttribute("mapAcademico", mapAcademico);
		modelo.addAttribute("mapaPais", mapaPais);
		modelo.addAttribute("mapaEstado", mapaEstado);
		modelo.addAttribute("mapaCarrera", mapaCarrera);
		modelo.addAttribute("lisAlumnos", admSolicitudDao.listCarMes(year, mes));
		
		return "admlinea/estadistica/cantidadCarMes";
	}	
}
