package aca.web.exalumnos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.exa.spring.ExaCorreo;
import aca.exa.spring.ExaCorreoDao;
import aca.exa.spring.ExaDireccion;
import aca.exa.spring.ExaDireccionDao;
import aca.exa.spring.ExaEgreso;
import aca.exa.spring.ExaEgresoDao;
import aca.exa.spring.ExaEstado;
import aca.exa.spring.ExaEstadoDao;
import aca.exa.spring.ExaPais;
import aca.exa.spring.ExaPaisDao;
import aca.exa.spring.ExaTelefono;
import aca.exa.spring.ExaTelefonoDao;
import aca.graduacion.spring.AlumEgresoDao;

@Controller
public class ContExalumnosReportes {
	
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
	
	@Autowired
	private AlumEgresoDao alumEgresoDao;
	
	@Autowired
	private ExaDireccionDao exaDireccionDao;
	
	@Autowired
	private ExaPaisDao exaPaisDao;
	
	@Autowired
	private ExaEgresoDao exaEgresoDao;
	
	@Autowired
	private ExaEstadoDao exaEstadoDao;
	
	@Autowired
	private ExaTelefonoDao exaTelefonoDao;
	
	@Autowired
	private ExaCorreoDao exaCorreoDao;
	
	@Autowired
	private CatCarreraDao catCarreraDao;
	
	@Autowired
	private AlumPersonalDao alumPersonalDao;
	
	
	@RequestMapping("/exalumnos/reportes/carrerasPais")
	public String exalumnosReportesCarrerasPais(HttpServletRequest request, Model modelo){
		List<ExaEgreso> lisEgresos = exaEgresoDao.getEgresos(" ORDER BY CARRERAID");
		
		HashMap<String, ExaDireccion> mapDireccion 		= exaDireccionDao.getMapAll(" AND MATRICULA IN(SELECT MATRICULA FROM ENOC.EXA_EGRESO WHERE ELIMINADO != 1 )");
		HashMap<String, ExaPais> mapaPaises 			= exaPaisDao.getMapAll("");	
		HashMap<String, ExaEstado> mapaEstados 			= exaEstadoDao.getMapAll("");
		HashMap<String, CatCarrera> mapaCarreras 		= catCarreraDao.getMapAll("");
		HashMap<String, ExaTelefono> mapaTelefono 		= exaTelefonoDao.getMapTelefono("ORDER BY MATRICULA, TELEFONO_ID DESC");
		HashMap<String, ExaCorreo> mapaCorreo 			= exaCorreoDao.getMapCorreo("ORDER BY MATRICULA, CORREO_ID DESC");
		HashMap<String, AlumPersonal> mapaEgreso 		= alumPersonalDao.mapaEgreso();
		
		modelo.addAttribute("lisEgresos", lisEgresos);
		modelo.addAttribute("mapDireccion", mapDireccion);
		modelo.addAttribute("mapaPaises", mapaPaises);
		modelo.addAttribute("mapaEstados", mapaEstados);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaTelefono", mapaTelefono);
		modelo.addAttribute("mapaCorreo", mapaCorreo);
		modelo.addAttribute("mapaEgreso", mapaEgreso);
		
		return "exalumnos/reportes/carrerasPais";
	}
	
	@RequestMapping("/exalumnos/reportes/anioEgreso")
	public String exalumnosReportesAnioEgreso(HttpServletRequest request, Model modelo){
		
		String year			 		= request.getParameter("Year")==null?"Selecciona":request.getParameter("Year");	
		String carreraId			= request.getParameter("CarreraId")==null?"Selecciona":request.getParameter("CarreraId");	
		 
		String condicion			= "";
		String condicionCarreras	= "";
		String orden 				= "";
		String ordenCarreras		= "";
		
		// Si alguno de los dos filtros en valido agrega el where y las condiciones
		if (!year.equals("Selecciona") || !carreraId.equals("Selecciona")){
			condicion = "AND ";
			if (!year.equals("Selecciona")){
				
				condicion += " YEAR = "+year;
				condicionCarreras = "WHERE YEAR = "+year;
				
				if (!carreraId.equals("Selecciona")){ 
					condicion += " AND CARRERAID = "+carreraId;				
				}
				
				orden = "ORDER BY YEAR, ENOC.NOMBRE_CARRERA(CARRERAID)";
				ordenCarreras = "ORDER BY ENOC.NOMBRE_CARRERA(CARRERAID)";
			}else{
				condicion += "CARRERAID = "+carreraId;
				condicionCarreras = " ";
				orden = "ORDER BY ENOC.NOMBRE_CARRERA(CARRERAID)";
				ordenCarreras = "ORDER BY ENOC.NOMBRE_CARRERA(CARRERAID)";
			}		
		}else{
			// No traer renglones
			condicion 			= " ";
			condicionCarreras 	= " ";
			orden 				= " ORDER BY CARRERAID, YEAR";
			ordenCarreras 		= " ORDER BY ENOC.NOMBRE_CARRERA(CARRERAID)";
		}	
		
		List<String> lisYears				= exaEgresoDao.getAnios(" ORDER BY YEAR DESC");
		List<String> lisCarreras			= exaEgresoDao.getCarreras(condicionCarreras+" "+ordenCarreras);	
		List<ExaEgreso> lisEgresos			= exaEgresoDao.getEgresos(condicion+" "+orden);
		
		
		HashMap<String, ExaDireccion> mapaDirecciones	= exaDireccionDao.getMapAll(" AND MATRICULA IN(SELECT MATRICULA FROM ENOC.EXA_EGRESO WHERE ELIMINADO != 1 "+condicion+")");
		HashMap<String, CatCarrera> mapaCarreras 		= catCarreraDao.getMapAll("");
		HashMap<String, ExaPais> mapaPaises 			= exaPaisDao.getMapAll("");	
		HashMap<String, ExaEstado> mapaEstados 			= exaEstadoDao.getMapAll("");
		HashMap<String, ExaCorreo> mapaCorreo 			= exaCorreoDao.getMapCorreo("");
		HashMap<String, ExaTelefono> mapaTel 			= exaTelefonoDao.getMapTelefono("");
		HashMap<String, AlumPersonal> mapaEgreso 		= alumPersonalDao.mapaExalumnos();
		
		modelo.addAttribute("lisYears", lisYears);
		modelo.addAttribute("lisCarreras", lisCarreras);
		modelo.addAttribute("lisEgresos", lisEgresos);
		modelo.addAttribute("mapaDirecciones", mapaDirecciones);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaPaises", mapaPaises);
		modelo.addAttribute("mapaEstados", mapaEstados);
		modelo.addAttribute("mapaCorreo", mapaCorreo);
		modelo.addAttribute("mapaTel", mapaTel);
		modelo.addAttribute("mapaEgreso", mapaEgreso);
		
		return "exalumnos/reportes/anioEgreso";
	}
	
	@RequestMapping("/exalumnos/reportes/carreras")
	public String exalumnosReportesCarrera(HttpServletRequest request, Model modelo){
		String carreraId = request.getParameter("CarreraId")==null?"X":request.getParameter("CarreraId");
		if(carreraId.equals("Selecciona")){
			carreraId	 = "X";
		}

		List<String> getCarreras = exaEgresoDao.getCarreras("ORDER BY CARRERAID");
		List<ExaEgreso> lisEgresos;

		if(carreraId.equals("X")){
			lisEgresos			= exaEgresoDao.getEgresos(" ORDER BY CARRERAID");
		}else{
			lisEgresos			= exaEgresoDao.getEgresadosPorCarrera(carreraId," ORDER BY CARRERAID");
		}
		
		HashMap<String, ExaDireccion> mapDireccion = exaDireccionDao.getMapAll(" AND MATRICULA IN(SELECT MATRICULA FROM ENOC.EXA_EGRESO WHERE ELIMINADO != 1 )");
		HashMap<String, ExaPais> mapaPaises = exaPaisDao.getMapAll("");	
		HashMap<String, ExaEstado> mapaEstados =   exaEstadoDao.getMapAll("");
		HashMap<String, CatCarrera> mapaCarreras =   catCarreraDao.getMapAll("");
		
		HashMap<String, ExaTelefono> mapaTelefono 	= exaTelefonoDao.getMapTelefono("ORDER BY MATRICULA, TELEFONO_ID DESC");
		HashMap<String, ExaCorreo> mapaCorreo 		= exaCorreoDao.getMapCorreo("ORDER BY MATRICULA, CORREO_ID DESC");
		
		HashMap<String, AlumPersonal> mapaEgreso = alumPersonalDao.mapaExalumnos();
		
		modelo.addAttribute("carreraId", carreraId);
		modelo.addAttribute("getCarreras", getCarreras);
		modelo.addAttribute("lisEgresos", lisEgresos);
		modelo.addAttribute("mapDireccion", mapDireccion);
		modelo.addAttribute("mapaPaises", mapaPaises);
		modelo.addAttribute("mapaEstados", mapaEstados);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaTelefono", mapaTelefono);
		modelo.addAttribute("mapaCorreo", mapaCorreo);
		modelo.addAttribute("mapaEgreso", mapaEgreso);
		
		return "exalumnos/reportes/carreras";
	}
	
	@RequestMapping("/exalumnos/reportes/cumple")
	public String exalumnosReportesCumple(HttpServletRequest request, Model modelo){
		String dia = request.getParameter("Dia")==null?"":request.getParameter("Dia"); 
		String mes = request.getParameter("Mes")==null?aca.util.Fecha.getMes():request.getParameter("Mes"); 
		
		String queryMes = " AND SUBSTR(TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY'),4,2)= '"+mes+"' ";
		String queryDia = "";

		if (!dia.equals("0") && !dia.equals("")){
			if(dia.length()==1)dia="0"+dia;
			
			queryDia =  " AND SUBSTR(TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY'),1,2)= '"+dia+"' ";				
		}

		List<AlumPersonal> alumnos = alumPersonalDao.alumnosCumpleExaAlumno(queryMes+queryDia+" ORDER BY F_NACIMIENTO");
		
		HashMap<String, ExaCorreo> correos 		= exaCorreoDao.getMapCorreo("");
		HashMap<String, ExaTelefono> telefonos 	= exaTelefonoDao.getMapTelefono("");
		
		modelo.addAttribute("dia", dia);
		modelo.addAttribute("mes", mes);
		modelo.addAttribute("alumnos", alumnos);
		modelo.addAttribute("correos", correos);
		modelo.addAttribute("telefonos", telefonos);
		
		return "exalumnos/reportes/cumple";
	}
	
	@RequestMapping("/exalumnos/reportes/menu")
	public String exalumnosReportesMenu(HttpServletRequest request, Model modelo){
		
		int totTraspasos = alumEgresoDao.existenTraspasos();
		modelo.addAttribute("totTraspasos", totTraspasos);
		
		return "exalumnos/reportes/menu";
	}
	
	@RequestMapping("/exalumnos/reportes/paisEdo")
	public String exalumnosReportesPaisEdo(HttpServletRequest request, Model modelo){
		String paisId 	= request.getParameter("PaisId")==null?"T":request.getParameter("PaisId");
		String estadoId = request.getParameter("EstadoId")==null?"T":request.getParameter("EstadoId");

		List<ExaDireccion> listaExa = exaDireccionDao.getListAll("WHERE ELIMINADO!='-1' "+(paisId.equals("T")?"":"AND PAIS_ID='"+paisId+"' ")+(estadoId.equals("T")?"":"AND ESTADO_ID='"+estadoId+"' ")+"ORDER BY PAIS_ID, ESTADO_ID, CIUDAD");

		List<ExaPais> listaPaises 	= exaPaisDao.getListPaisesDistintos("INNER JOIN ENOC.EXA_DIRECCION B ON A.PAIS_ID = B.PAIS_ID ORDER BY A.PAIS_NOMBRE");
		HashMap<String, ExaPais> mapPaises = exaPaisDao.getMapAll("");
		
		List<ExaEstado> listaEstados = new ArrayList<ExaEstado>();
		HashMap<String, ExaEstado> mapaEstados = new HashMap<String, ExaEstado>();
		if(!paisId.equals("T")){
			listaEstados 	= exaEstadoDao.getLista(paisId, "ORDER BY ESTADO_NOMBRE");
			mapaEstados 	= exaEstadoDao.getMapAll("WHERE PAIS_ID='"+paisId+"'");
		}
		else{
			mapaEstados = exaEstadoDao.getMapAll("");
		}

		HashMap<String, ExaTelefono> mapaTelefono = exaTelefonoDao.getMapTelefono("ORDER BY MATRICULA, TELEFONO_ID DESC");
		HashMap<String, ExaCorreo> mapaCorreo = exaCorreoDao.getMapCorreo("ORDER BY MATRICULA, CORREO_ID DESC");
		
		HashMap<String, AlumPersonal> mapaEgreso = alumPersonalDao.mapaExalumnos();
		
		modelo.addAttribute("paisId", paisId);
		modelo.addAttribute("estadoId", estadoId);
		modelo.addAttribute("listaExa", listaExa);
		modelo.addAttribute("listaPaises", listaPaises);
		modelo.addAttribute("mapPaises", mapPaises);
		modelo.addAttribute("mapaTelefono", mapaTelefono);
		modelo.addAttribute("mapaCorreo", mapaCorreo);
		modelo.addAttribute("listaEstados", listaEstados);
		modelo.addAttribute("mapaEstados", mapaEstados);
		modelo.addAttribute("mapaEgreso", mapaEgreso);
		
		return "exalumnos/reportes/paisEdo";
	}	
	
	@RequestMapping("/exalumnos/reportes/traspasar")
	public String exalumnosExalumnoTraspasar(HttpServletRequest request, Model modelo){
		
		int grabados = alumEgresoDao.traspasar();
		
		modelo.addAttribute("grabados", "grabados");
		
		return "redirect:/exalumnos/reportes/menu?Grabados="+grabados;
	}
}