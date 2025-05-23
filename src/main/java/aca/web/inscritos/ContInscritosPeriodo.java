package aca.web.inscritos;

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
import org.springframework.web.client.RestTemplate;

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumAsesor;
import aca.alumno.spring.AlumAsesorDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlanDao;
import aca.alumno.spring.AlumUbicacion;
import aca.alumno.spring.AlumUbicacionDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaAlumno;
import aca.carga.spring.CargaAlumnoDao;
import aca.carga.spring.CargaDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatCiudad;
import aca.catalogo.spring.CatCiudadDao;
import aca.catalogo.spring.CatEstado;
import aca.catalogo.spring.CatEstadoDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatModalidad;
import aca.catalogo.spring.CatModalidadDao;
import aca.catalogo.spring.CatPais;
import aca.catalogo.spring.CatPaisDao;
import aca.catalogo.spring.CatPeriodo;
import aca.catalogo.spring.CatPeriodoDao;
import aca.catalogo.spring.CatReligion;
import aca.catalogo.spring.CatReligionDao;
import aca.catalogo.spring.CatTipoAlumno;
import aca.catalogo.spring.CatTipoAlumnoDao;
import aca.financiero.spring.FesCcobro;
import aca.financiero.spring.FesCcobroDao;
import aca.financiero.spring.FinSaldo;
import aca.financiero.spring.SaldosAlumnos;
import aca.financiero.spring.SaldosAlumnosDao;
import aca.kardex.spring.KrdxCursoActDao;
import aca.matricula.spring.MatriculaDao;
import aca.parametros.spring.Parametros;
import aca.parametros.spring.ParametrosDao;
import aca.vista.spring.Estadistica;
import aca.vista.spring.EstadisticaDao;
import aca.vista.spring.Inscritos;

@Controller
public class ContInscritosPeriodo {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired	
	private CargaDao cargaDao;
	
	@Autowired	
	private CargaAlumnoDao cargaAlumnoDao;
	
	@Autowired	
	private CatModalidadDao catModalidadDao;
	
	@Autowired	
	private EstadisticaDao estadisticaDao;
	
	@Autowired
	private ParametrosDao parametrosDao;
	
	@Autowired	
	private AlumAsesorDao alumAsesorDao;
	
	@Autowired	
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired	
	private CatPeriodoDao catPeriodoDao;
	
	@Autowired	
	private CatTipoAlumnoDao catTipoAlumnoDao;
	
	@Autowired	
	private AccesoDao accesoDao;
	
	@Autowired	
	private KrdxCursoActDao krdxCursoActDao;
	
	@Autowired	
	private CatCarreraDao catCarreraDao;
	
	@Autowired	
	private CatFacultadDao catFacultadDao;
	
	@Autowired	
	private CatPaisDao catPaisDao;
	
	@Autowired	
	private CatEstadoDao catEstadoDao;

	@Autowired	
	private CatCiudadDao catCiudadDao;
	
	@Autowired	
	private CatReligionDao catReligionDao;

	@Autowired	
	private FesCcobroDao fesCcobroDao;
	
	@Autowired	
	private AlumUbicacionDao alumUbicacionDao;
	
	@Autowired	
	private SaldosAlumnosDao saldosAlumnosDao;
	
	@Autowired	
	private AlumPlanDao alumPlanDao;
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/inscritos/inscritos_periodo/modalidades")
	public String inscritosInscritosPeriodoModalidades(HttpServletRequest request, Model modelo){
		
		String cargas			= "0";
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();            
	    if (sesion != null){
	    	cargas = sesion.getAttribute("cargas")==null?"0":sesion.getAttribute("cargas").toString();
	    }	
	    
		List<CatModalidad> lisModalidad	= catModalidadDao.getListAll(" ORDER BY MODALIDAD_ID");
		HashMap<String,String> mapa = estadisticaDao.mapaModalidadesEnCargas(cargas);
		
		modelo.addAttribute("lisModalidad", lisModalidad);
		modelo.addAttribute("mapa", mapa);
		
		//enviarConArchivo(request,"Error-aca.ControllerDatosAlumno|credencialEmpDatoDep:");		
		return "inscritos/inscritos_periodo/modalidades";
	}
	
	@RequestMapping("/inscritos/inscritos_periodo/reporte")
	public String inscritosPeriodoReporte(HttpServletRequest request, Model modelo){
		String cargas = request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		
		HttpSession sesion 	= ((HttpServletRequest) request).getSession();
		if (sesion != null) {
			if(cargas.equals("0")) {
				cargas = (String)sesion.getAttribute("cargas");
			}
		}	
		
		if(!cargas.equals("'000000'")) { cargas = "'"+cargas+"'"; }
		cargas = cargas.replaceAll("''", "'"); 
		
		HashMap<String, String> mapAlumComidas 			= alumPersonalDao.mapAlumComidas(cargas);
		HashMap<String, CargaAlumno> mapaCargasAlumno 	= cargaAlumnoDao.mapaCargasAlumno(cargas);
		
		modelo.addAttribute("mapAlumComidas", mapAlumComidas);
		modelo.addAttribute("mapaCargasAlumno", mapaCargasAlumno);
		
		enviarConEnoc(request, "Error-aca.ContInscritosPeriodo|inscritosPeriodoReporte");        
		return "inscritos/inscritos_periodo/reporte";
	}	
	
	@RequestMapping("/inscritos/inscritos_periodo/inscritos_per")
	public String inscritosPeriodoInscritosPer(HttpServletRequest request, Model modelo){
		HashMap<String,AlumAsesor> mapaAsesores = alumAsesorDao.mapaTodos();
		String cargas 			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		String modalidades		= "0";
		String residencia		= request.getParameter("Residencia")==null?"T":request.getParameter("Residencia");
		String nacion			= request.getParameter("Nacionalidad")==null?"T":request.getParameter("Nacionalidad");
		String tipoAlumno		= request.getParameter("TipoAlumno")==null?"T":request.getParameter("TipoAlumno");
		String conSaldo 		= request.getParameter("ConSaldo")==null?"N":request.getParameter("ConSaldo");
		
		HttpSession sesion 	= ((HttpServletRequest) request).getSession();
		if (sesion != null) {
			if(cargas.equals("0")) {
				cargas = (String)sesion.getAttribute("cargas");
			}
			modalidades		= (String)sesion.getAttribute("modalidades") == null?"'1'":sesion.getAttribute("modalidades").toString();
		}
		
		if(!cargas.equals("'000000'")){
			cargas = "'"+cargas+"'";
		}
		
		java.text.DecimalFormat frmDecimal = new java.text.DecimalFormat("###,##0.00; -###,##0.00");
		
		String lisModo[] = modalidades.replace("'", "").split(",");

		List<CatPeriodo> listaPeriodos = catPeriodoDao.getListAll("ORDER BY PERIODO_ID DESC");

		if(request.getParameter("cambioPeriodo")!=null && !request.getParameter("cambioPeriodo").equals("")){
			sesion.setAttribute("periodo", request.getParameter("PeriodoId"));
		}
		
		String periodoId = (String)sesion.getAttribute("periodo");
		
		List<Carga> lisCarga = cargaDao.getListAll("WHERE PERIODO IN ('"+periodoId+"') AND ESTADO = '1' ORDER BY CARGA_ID");
		
		List<CatTipoAlumno> lisTipoAlumno = catTipoAlumnoDao.getListAll("ORDER BY TIPO_ID");
		
		if(request.getParameter("cambioPeriodo")!=null&&!request.getParameter("cambioPeriodo").equals("")&&!lisCarga.isEmpty()){
			sesion.setAttribute("cargaId", lisCarga.get(0).getCargaId());		
		}else if(request.getParameter("cambioCarga")!=null&&!request.getParameter("cambioCarga").equals("")){
			sesion.setAttribute("cargaId", request.getParameter("CargaId"));		
		}
		String cargaId 		= (String)sesion.getAttribute("cargaId");

		if(lisCarga.isEmpty()){
			sesion.setAttribute("cargaId", "");
		}

		String codigo			= (String) sesion.getAttribute("codigoPersonal");
		
		String sMatricula 		= "X";
		String sNombreAlumno 	= "X";
		String sFacultad 		= "X";
		String sCarrera 		= "X";
		String sCarreraTemp		= "X";
		String sNombreFacultad 	= "X";
		String sNombreCarrera	= "X";
		String sSexo 			= "X";
		String sResidencia 		= "X";
		String sModalidad		= "0";	
		String sFechaNac 		= "X";
		String religionId		= "0";
		String religion			= "";
		String pais				= "";
		
		String fechaIni			= request.getParameter("FechaIni")==null?(String) sesion.getAttribute("fechaIni"):request.getParameter("FechaIni");
		String fechaFin			= request.getParameter("FechaFin")==null?(String) sesion.getAttribute("fechaFin"):request.getParameter("FechaFin");
		
		if (accion.equals("1")){		
			sesion.setAttribute("fechaIni", fechaIni);
			sesion.setAttribute("fechaFin", fechaFin);
		} 
		
		String hoy 				= aca.util.Fecha.getHoy();
		String fechaHoy[]		= hoy.split("/");			
		
		int añoHoy				= Integer.parseInt(fechaHoy[2]);
		int	mesHoy				= Integer.parseInt(fechaHoy[1]);
		int diaHoy				= Integer.parseInt(fechaHoy[0]);
		
		int añoNac				= 0;
		int	mesNac				= 0;
		int diaNac				= 0;
		
		int edad				= 0;
		
		Acceso acceso = new Acceso();
		if(accesoDao.existeReg(codigo)) {
			acceso = accesoDao.mapeaRegId(codigo);	
		}
		
		String sBgcolor			= "";
		
		int cont 				= 1;
		int nInscritos 			= 0, nCalculos 		= 0;
		int nHombres			= 0, nMujeres		= 0;
		int nInternos			= 0, nExternos 		= 0;
		int nNacional			= 0, nExtranjero	= 0;
		int i 					= 0;
		
		int nMexicanos = 0, nExtranjeros = 0, nAdventistas = 0, nNoAdventistas = 0;
		int inscritosFac 		= 0, internosFac = 0, externosFac = 0, hombresFac = 0,	mujeresFac = 0, mexicanos = 0, extranjeros = 0, adventistas = 0, noAdventistas = 0;
		if (cargaId==null){ cargaId	= (String)sesion.getAttribute("cargaId");  }
		
		HashMap<String, String> mapAlumComidas = alumPersonalDao.mapAlumComidas(cargas);
		
		String paisOrganizacion = "0";
		Parametros parametros = new Parametros();
		
		if (parametrosDao.existeReg("1")) {
			parametros = parametrosDao.mapeaRegId("1");
		}
		
		if (residencia.equals("T")) residencia = " AND RESIDENCIA_ID IN ('I','E') ";
		if (residencia.equals("E")) residencia = " AND RESIDENCIA_ID IN ('E') ";
		if (residencia.equals("I")) residencia = " AND RESIDENCIA_ID IN ('I') ";	
		
		if (nacion.equals("T")) nacion = " AND NACIONALIDAD != 0 ";
		if (nacion.equals("M")) nacion = " AND NACIONALIDAD = TO_NUMBER("+parametros.getPaisId()+", '999') ";
		if (nacion.equals("E")) nacion = " AND NACIONALIDAD != TO_NUMBER("+parametros.getPaisId()+", '999') ";
		
		if(tipoAlumno.equals("T")) tipoAlumno = " AND TIPOALUMNO_ID != 0";
		if(tipoAlumno.equals("1")) tipoAlumno = " AND TIPOALUMNO_ID = 1";
		if(tipoAlumno.equals("2")) tipoAlumno = " AND TIPOALUMNO_ID = 2";
		if(tipoAlumno.equals("3")) tipoAlumno = " AND TIPOALUMNO_ID = 3";
		if(tipoAlumno.equals("4")) tipoAlumno = " AND TIPOALUMNO_ID = 4";
		if(tipoAlumno.equals("5")) tipoAlumno = " AND TIPOALUMNO_ID = 5";
		if(tipoAlumno.equals("6")) tipoAlumno = " AND TIPOALUMNO_ID = 6";
		if(tipoAlumno.equals("7")) tipoAlumno = " AND TIPOALUMNO_ID = 7";
		if(tipoAlumno.equals("8")) tipoAlumno = " AND TIPOALUMNO_ID = 8";
		if(tipoAlumno.equals("9")) tipoAlumno = " AND TIPOALUMNO_ID = 9";
		if(tipoAlumno.equals("10")) tipoAlumno = " AND TIPOALUMNO_ID = 10";
		if(tipoAlumno.equals("11")) tipoAlumno = " AND TIPOALUMNO_ID = 11";
		if(tipoAlumno.equals("12")) tipoAlumno = " AND TIPOALUMNO_ID = 12";
		
		// Map para contar numero total de materias
		HashMap<String, String> mapMaterias 			= krdxCursoActDao.getNumAlumMatTipoModalidad(cargaId, modalidades, "'I','1','2','3','4','5','6'");
		// Map para contar numero de materias acreditadas
		HashMap<String, String> mapAcreditadas			= krdxCursoActDao.getNumAlumMatTipoModalidad(cargaId, modalidades,"'1'");
		// Map para contar numero de materias acreditadas
		HashMap<String, String> mapReprobadas 			= krdxCursoActDao.getNumAlumMatTipoModalidad(cargaId,modalidades, "'2','4'");
		// Map para contar numero de materias acreditadas
		HashMap<String, String> mapPendientes			= krdxCursoActDao.getNumAlumMatTipoModalidad(cargaId,modalidades, "'I','5','6'");
		// Map para contar numero de materias acreditadas
		HashMap<String, String> mapBajas 				= krdxCursoActDao.getNumAlumMatTipoModalidad(cargaId,modalidades, "'3'");
		// Map para contar numero de materias acreditadas
		HashMap<String, String> mapCreditos 			= krdxCursoActDao.getNumAlumCreditosModalidad(cargaId, modalidades,"'I','1','2','3','4','5','6'");
		// Map para contar numero de materias acreditadas
		HashMap<String, String> mapCreditosRep			= krdxCursoActDao.getNumAlumCreditosModalidad(cargaId, modalidades,"'2','4'");	
		//Map de Carreras
		HashMap <String, CatCarrera> mapaCarrera 		= catCarreraDao.getMapAll(" ORDER BY NOMBRE_CARRERA");
		//Map de Carreras
		HashMap <String, CatTipoAlumno> mapaTipo		= catTipoAlumnoDao.getMapAll("");
		// Map de facultades
		HashMap <String, CatFacultad> mapaFacultad		= catFacultadDao.getMapFacultad("ORDER BY NOMBRE_FACULTAD");
		// Map de modalidades
		HashMap <String, CatModalidad> mapaModalidad 	= catModalidadDao.getMapAll(" ");
		// Map de países
		HashMap <String, CatPais> mapaPais 				= catPaisDao.getMapAll();
		// Map de estados
		HashMap <String, CatEstado> mapaEstado 			= catEstadoDao.getMapPaisEstado("");
		// Map de ciudades
		HashMap <String, CatCiudad> mapaCiudad 			= catCiudadDao.getMapAll("");
		// Map de semestres
		HashMap <String, FesCcobro> mapaCobro 			= fesCcobroDao.mapInscritosCargas(cargaId);
		// Map de religiones
		HashMap<String, CatReligion> mapReligion 		= catReligionDao.getMapAll("");		
		// Map de Datos personales
		HashMap<String, AlumPersonal> mapAlumnos 		= alumPersonalDao.mapAlumnosEnCargas(cargaId);
		// Map de Datos personales
		HashMap<String, AlumUbicacion> mapUbicacion 	= alumUbicacionDao.mapAlumnosEnCargas("'"+cargaId+"'");
		// Lista de alumnos inscritos en una carga
		List<Estadistica> lista = estadisticaDao.lisCargaModalidadesFechasYResidencia(cargaId, modalidades, fechaIni, fechaFin, residencia, nacion, tipoAlumno, " ORDER BY ENOC.FACULTAD(CARRERA_ID), CARRERA_ID, APELLIDO_PATERNO,APELLIDO_MATERNO,NOMBRE");
		// Saldos de alumnos en la carga
		HashMap<String, SaldosAlumnos> saldos 			= saldosAlumnosDao.mapInscritosCarga(cargaId);

		HashMap<String, String> mapaPrimerMatricula 	= alumPlanDao.mapaPrimerMatricula(cargaId);
		
		// Consulta el saldo del estudiante
		HashMap<String,FinSaldo> mapaSaldos = new HashMap<String,FinSaldo>();
		FinSaldo finSaldo 	= new FinSaldo();
		
		if(conSaldo.equals("S")) {
			for(Estadistica objeto : lista) {
				try {		
					RestTemplate restTemplate = new RestTemplate();
					finSaldo = restTemplate.getForObject("http://172.16.251.110:8080/infor/reportes/api/fe/saldo/"+objeto.getCodigoPersonal(), FinSaldo.class);
					mapaSaldos.put(objeto.getCodigoPersonal(), finSaldo);
				}catch(Exception ex) {
					System.out.println("Error en saldo:"+objeto.getCodigoPersonal());
				}
			}
		}
		
		HashMap<String, CargaAlumno> mapaCargasAlumno = cargaAlumnoDao.mapaCargasAlumno(cargas);
		
		Carga cargaElegida = new Carga();
		if(cargaDao.existeReg(cargas)) {
			cargaElegida = cargaDao.mapeaRegId(cargas);
		}else {
			cargaElegida = cargaDao.mapeaRegId(cargaId);
		}
		
		modelo.addAttribute("mapaAsesores", mapaAsesores);
		modelo.addAttribute("mapAlumComidas", mapAlumComidas);
		modelo.addAttribute("accion", accion);
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("modalidades", modalidades);
		modelo.addAttribute("residencia", residencia);
		modelo.addAttribute("nacion", nacion);
		modelo.addAttribute("tipoAlumno", tipoAlumno);
		modelo.addAttribute("listaPeriodos", listaPeriodos);
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("lisCarga", lisCarga);
		modelo.addAttribute("lisTipoAlumno", lisTipoAlumno);
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("codigo", codigo);
		modelo.addAttribute("fechaIni", fechaIni);
		modelo.addAttribute("fechaFin", fechaFin);
		modelo.addAttribute("mapaSaldos", mapaSaldos);
		modelo.addAttribute("mapMaterias", mapMaterias);
		modelo.addAttribute("mapAcreditadas", mapAcreditadas);
		modelo.addAttribute("mapReprobadas", mapReprobadas);
		modelo.addAttribute("mapPendientes", mapPendientes);
		modelo.addAttribute("mapBajas", mapBajas);
		modelo.addAttribute("mapCreditos", mapCreditos);
		modelo.addAttribute("mapCreditosRep", mapCreditosRep);
		modelo.addAttribute("mapaCarrera", mapaCarrera);
		modelo.addAttribute("mapaTipo", mapaTipo);
		modelo.addAttribute("mapaFacultad", mapaFacultad);
		modelo.addAttribute("mapaModalidad", mapaModalidad);
		modelo.addAttribute("mapaPais", mapaPais);
		modelo.addAttribute("mapaEstado", mapaEstado);
		modelo.addAttribute("mapaCiudad", mapaCiudad);
		modelo.addAttribute("mapaCobro", mapaCobro);
		modelo.addAttribute("mapReligion", mapReligion);
		modelo.addAttribute("mapAlumnos", mapAlumnos);
		modelo.addAttribute("mapUbicacion", mapUbicacion);
		modelo.addAttribute("lista", lista);
		modelo.addAttribute("saldos", saldos);
		modelo.addAttribute("mapaPrimerMatricula", mapaPrimerMatricula);
		modelo.addAttribute("mapaCargasAlumno", mapaCargasAlumno);
		modelo.addAttribute("cargaElegida", cargaElegida);
		modelo.addAttribute("conSaldo", conSaldo);
		modelo.addAttribute("paisOrganizacion", paisOrganizacion);
		modelo.addAttribute("paisOrganizacion",paisOrganizacion);
		return "inscritos/inscritos_periodo/inscritos_per";
	}

}
