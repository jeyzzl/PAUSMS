package aca.web.mentores;

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

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumAcademicoDao;
import aca.alumno.spring.AlumActualiza;
import aca.alumno.spring.AlumActualizaDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlanDao;
import aca.alumno.spring.AlumUbicacion;
import aca.alumno.spring.AlumUbicacionDao;
import aca.carga.spring.Carga;
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
import aca.catalogo.spring.CatNivel;
import aca.catalogo.spring.CatNivelDao;
import aca.catalogo.spring.CatPais;
import aca.catalogo.spring.CatPaisDao;
import aca.catalogo.spring.CatPeriodo;
import aca.catalogo.spring.CatPeriodoDao;
import aca.catalogo.spring.CatTipoAlumno;
import aca.catalogo.spring.CatTipoAlumnoDao;
import aca.kardex.spring.KrdxCursoActDao;
import aca.mentores.spring.MentAcceso;
import aca.mentores.spring.MentAccesoDao;
import aca.mentores.spring.MentAlumno;
import aca.mentores.spring.MentAlumnoDao;
import aca.mentores.spring.MentCarreraDao;
import aca.mentores.spring.MentContacto;
import aca.mentores.spring.MentContactoDao;
import aca.mentores.spring.MentMotivo;
import aca.mentores.spring.MentMotivoDao;
import aca.vista.spring.CargaAcademica;
import aca.vista.spring.CargaAcademicaDao;
import aca.vista.spring.EstadisticaDao;
import aca.vista.spring.Inscritos;
import aca.vista.spring.InscritosDao;
import aca.vista.spring.MaestrosDao;
import aca.vista.spring.MenCarrera;
import aca.vista.spring.MenCarreraDao;
import aca.vista.spring.UsuariosDao;

@Controller
public class ContMentoresReportes {	
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	MentAccesoDao mentAccesoDao;
	
	@Autowired
	MaestrosDao maestrosDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	CatFacultadDao catFacultadDao;
	
	@Autowired
	UsuariosDao usuariosDao;
	
	@Autowired
	CatPeriodoDao catPeriodoDao;	
	
	@Autowired
	MenCarreraDao menCarreraDao;
	
	@Autowired
	MentCarreraDao mentCarreraDao;
	
	@Autowired
	MentAlumnoDao mentAlumnoDao;
	
	@Autowired
	CargaDao cargaDao;
	
	@Autowired	
	private EstadisticaDao estadisticaDao;
	
	@Autowired	
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired	
	private AccesoDao accesoDao;
	
	@Autowired	
	private MentContactoDao mentContactoDao;
	
	@Autowired	
	private MentMotivoDao mentMotivoDao;
	
	@Autowired	
	private InscritosDao inscritosDao;
	
	@Autowired	
	private CatNivelDao catNivelDao;
	
	@Autowired	
	private CatTipoAlumnoDao catTipoAlumnoDao;
	
	@Autowired	
	private CatModalidadDao catModalidadDao;
	
	@Autowired	
	private AlumAcademicoDao alumAcademicoDao;
	
	@Autowired	
	private AlumUbicacionDao alumUbicacionDao;
	
	@Autowired	
	private CatPaisDao catPaisDao;
	
	@Autowired	
	private CatEstadoDao catEstadoDao;
	
	@Autowired	
	private CatCiudadDao catCiudadDao;
	
	@Autowired	
	private AlumActualizaDao alumActualizaDao;	
	
	@Autowired	
	private CargaAcademicaDao cargaAcademicaDao;
	
	@Autowired	
	private KrdxCursoActDao krdxCursoActDao;
	
	@Autowired	
	private AlumPlanDao alumPlanDao;
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
			//System.out.println("Abrir conEnoc"+url);
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/mentores/reportes/menu")
	public String mentoresReportesMenu(HttpServletRequest request, Model modelo){		
		return "mentores/reportes/menu";
	}
	
	@RequestMapping("/mentores/reportes/cargas")
	public String mentoresReportesCargas(HttpServletRequest request, Model modelo){		
		  
		String fInscripcion		= request.getParameter("Fecha")==null?aca.util.Fecha.getHoy():request.getParameter("Fecha");		
		List<Carga> lisCarga	= cargaDao.getListPorFecha(fInscripcion, "ORDER BY CARGA_ID");
		List<Carga> lisActivas	= cargaDao.getListCargasActivas(" ORDER BY CARGA_ID");
		HashMap<String, String> mapaPrimerInscripcion 		= estadisticaDao.mapaPrimerInscripcion();
		HashMap<String, String> mapaUltimaInscripcion 	 	= estadisticaDao.mapaUltimaInscripcion();
		
		modelo.addAttribute("lisCarga", lisCarga);
    	modelo.addAttribute("lisActivas", lisActivas);
    	modelo.addAttribute("mapaPrimerInscripcion", mapaPrimerInscripcion);
    	modelo.addAttribute("mapaUltimaInscripcion", mapaUltimaInscripcion);
    	
		return "mentores/reportes/cargas";
	}
	
	@RequestMapping("/mentores/reportes/estPerfilMent")
	public String mentoresReportesEstPerfilMent(HttpServletRequest request, Model modelo){		
		
		return "mentores/reportes/estPerfilMent";
	}
	
	@RequestMapping("/mentores/reportes/estPerfil")
	public String mentoresReportesEstPerfil(HttpServletRequest request, Model modelo){		
		
		return "mentores/reportes/estPerfil";
	}
		
	@RequestMapping("/mentores/reportes/rep_mentor_facultad")
	public String mentoresReportesRepMentorFacultad(HttpServletRequest request, Model modelo){		
		String periodo			= catPeriodoDao.getPeriodo();
		String fechaAconsejado 	= request.getParameter("FechaAconsejado")==null?aca.util.Fecha.getHoy():request.getParameter("FechaAconsejado");	
		
		List<MenCarrera> lisCarMentor			= menCarreraDao.getListMentPeriodo(periodo, " ORDER BY FACULTAD_ID, USUARIO_APELLIDO(MENTOR_ID)");		
		HashMap<String, String> mapaAlumnos		= mentAlumnoDao.getAlumPorMent(periodo, fechaAconsejado);
		
		modelo.addAttribute("lisCarMentor", lisCarMentor);
    	modelo.addAttribute("mapaAlumnos", mapaAlumnos);
       	
		return "mentores/reportes/rep_mentor_facultad";
	}
	
	@RequestMapping("/mentores/reportes/facultad")
	public String mentoresReportesFacultad(HttpServletRequest request, Model modelo){		
		
		List<CatFacultad> lisFacultades			= catFacultadDao.getListAll("ORDER BY 1");
		HashMap<String,String> mapaDirectores	= maestrosDao.mapaDirectores();
		
		modelo.addAttribute("lisFacultades", lisFacultades);		
		modelo.addAttribute("mapaDirectores", mapaDirectores);
		
		return "mentores/reportes/facultad";
	}	
	
	@RequestMapping("/mentores/reportes/rep_alum_ment_facu")
	public String mentoresReportesRepAlumMentFacu(HttpServletRequest request, Model modelo){
		
		String periodoId		= catPeriodoDao.getPeriodo();
		String facultad			= request.getParameter("facultad");		
		String fechaAconsejado 	= request.getParameter("FechaAconsejado")==null?aca.util.Fecha.getHoy():request.getParameter("FechaAconsejado");
		String facultadNombre	= catFacultadDao.getNombreFacultad(facultad);
		
		List<String> lisMentores 					= mentCarreraDao.listMentorFacultad(periodoId, facultad, fechaAconsejado, " ORDER BY USUARIO_APELLIDO(MENTOR_ID)");
		List<MentAlumno> lisAconsejados				= mentAlumnoDao.listAlumMentor(periodoId, fechaAconsejado, " ORDER BY ENOC.FACULTAD(CARRERA_ID), MENTOR_ID");
		HashMap<String,CatCarrera> mapaCarrera 		= catCarreraDao.getMapAll("");		
		HashMap<String,String> mapaAlumnos 			= alumPersonalDao.mapMentAlumno(periodoId);
		HashMap<String,String> mapaMentores 		= maestrosDao.mapMaestroNombre("APELLIDO");
		HashMap<String,String> mapaSemestreAlumno 	= alumPlanDao.mapaSemestreAlumno(periodoId,"1");
		
		modelo.addAttribute("facultadNombre", facultadNombre);
		modelo.addAttribute("lisMentores", lisMentores);
    	modelo.addAttribute("lisAconsejados", lisAconsejados);
    	modelo.addAttribute("mapaCarreras", mapaCarrera);
    	modelo.addAttribute("mapaAlumnos", mapaAlumnos);
    	modelo.addAttribute("mapaMentores", mapaMentores);
    	modelo.addAttribute("mapaSemestreAlumno", mapaSemestreAlumno);
       	
		return "mentores/reportes/rep_alum_ment_facu";
	}
	
	@RequestMapping("/mentores/reportes/rep_entrevista")
	public String mentoresReportesRepEntrevista(HttpServletRequest request, Model modelo){
		
		String codigoPersonal	= "0";
		String periodoId		= catPeriodoDao.getPeriodo();
		String periodoNombre 	= catPeriodoDao.getNombre(periodoId);
		String facultad			= request.getParameter("facultad");		
		String fechaAconsejado 	= request.getParameter("FechaAconsejado")==null?aca.util.Fecha.getHoy():request.getParameter("FechaAconsejado");
		String facultadNombre	= catFacultadDao.getNombreFacultad(facultad);
		Acceso acceso 			= new Acceso();
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoPersonal = (String)sesion.getAttribute("codigoPersonal");
        	acceso 			= accesoDao.mapeaRegId( codigoPersonal);
        }
        
        List<MenCarrera> lisMentores				= menCarreraDao.getListMentPeriodo(periodoId, facultad, " ORDER BY FACULTAD_NOMBRE, MENTOR_NOMBRE");
    	List<MentContacto> lisContactos				= mentContactoDao.listaEntrevistasPeriodo(periodoId, "ORDER BY MENTOR_ID, CODIGO_PERSONAL, FECHA");
    	HashMap<String, AlumPersonal> mapaAlumnos 	= alumPersonalDao.mapMentorContacto( periodoId );
    	HashMap<String, MentMotivo> mapaMotivos 	= mentMotivoDao.mapMotivo();    	
    	 	
    	modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("facultadNombre", facultadNombre);
		modelo.addAttribute("periodoNombre", periodoNombre);
		modelo.addAttribute("lisMentores", lisMentores);
    	modelo.addAttribute("lisContactos", lisContactos);
    	modelo.addAttribute("mapaAlumnos", mapaAlumnos);
    	modelo.addAttribute("mapaMotivos", mapaMotivos);
       	
		return "mentores/reportes/rep_entrevista";
	}
	
	@RequestMapping("/mentores/reportes/rep_alum_sin_mentor")
	public String mentoresReportesRepAlumSinMentor(HttpServletRequest request, Model modelo){
		
		String periodoId		= catPeriodoDao.getPeriodo();		
		String periodoNombre 	= catPeriodoDao.getNombre(periodoId);
		String facultad			= request.getParameter("facultad");		
		String facultadNombre	= catFacultadDao.getNombreFacultad(facultad);
		
		List<Inscritos> lisAlumnos 						= inscritosDao.getListAlumSinMentor(periodoId, facultad, " ORDER BY CARRERA_ID");
		
		HashMap<String,CatCarrera> mapaCarreras			= catCarreraDao.getMapAll("");
		HashMap <String,String> mapaAcademicos			= alumAcademicoDao.getTipoAlumnoSinMentor(periodoId);
		HashMap<String,CatTipoAlumno> mapaTipos			= catTipoAlumnoDao.getMapAll("");
		HashMap<String,AlumPersonal> mapaAlumnos		= alumPersonalDao.getMapInscritos();
		HashMap<String,CatNivel> mapaNiveles			= catNivelDao.getMapAll(""); 		
		HashMap<String,CatModalidad> mapaModalidades	= catModalidadDao.getMapAll("");
		
		modelo.addAttribute("facultadNombre", facultadNombre);	
		modelo.addAttribute("periodoNombre", periodoNombre);
		modelo.addAttribute("lisAlumnos", lisAlumnos);
    	modelo.addAttribute("mapaCarreras", mapaCarreras);
    	modelo.addAttribute("mapaAcademicos", mapaAcademicos);
    	modelo.addAttribute("mapaTipos", mapaTipos);
    	modelo.addAttribute("mapaAlumnos", mapaAlumnos);
    	modelo.addAttribute("mapaNiveles", mapaNiveles);
    	modelo.addAttribute("mapaModalidades", mapaModalidades);
    	
		return "mentores/reportes/rep_alum_sin_mentor";
	}	
	
	@RequestMapping("/mentores/reportes/mentor_cambio")
	public String mentoresReportesMentorCambio(HttpServletRequest request, Model modelo){
		
		String periodoId		= catPeriodoDao.getPeriodo();		
		String periodoNombre 	= catPeriodoDao.getNombre(periodoId);
		String facultad			= request.getParameter("facultad");
		String facultadNombre	= catFacultadDao.getNombreFacultad(facultad);
		 
		List<String> lisAlumnos					= mentAlumnoDao.getListAconsejados(periodoId, " ORDER BY 1");	
		List<MentAlumno> lisAconsejados			= mentAlumnoDao.getListMentores(periodoId, " ORDER BY CODIGO_PERSONAL, FECHA");
		HashMap<String,String> mapaMaestros		= maestrosDao.mapMaestroNombre("NOMBRE");	
		HashMap<String,String> mapaAlumnos		= alumPersonalDao.mapaAlumnosEnAconsejados(periodoId);
		
		modelo.addAttribute("facultadNombre", facultadNombre);
		modelo.addAttribute("periodoNombre", periodoNombre);
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("lisAconsejados", lisAconsejados);
		modelo.addAttribute("mapaMaestros", mapaMaestros);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
    	
		return "mentores/reportes/mentor_cambio";
	}
	
	@RequestMapping("/mentores/reportes/listDirecciones")
	public String mentoresReportesListDirecciones(HttpServletRequest request, Model modelo){
		
		String periodoId				= catPeriodoDao.getPeriodo();
		String periodoNombre 			= catPeriodoDao.getNombre(periodoId);
		
		List<Inscritos> lisInscritos					= inscritosDao.getListAllUM(" ORDER BY ENOC.FACULTAD(CARRERA_ID), CARRERA_ID, MODALIDAD_ID, APELLIDO_PATERNO,APELLIDO_MATERNO,NOMBRE");		
		HashMap<String,AlumUbicacion> mapaUbicaciones 	= alumUbicacionDao.mapInscritos();
		HashMap<String,CatFacultad> mapaFacultades 		= catFacultadDao.getMapFacultad("");
		HashMap<String,CatCarrera> mapaCarreras 		= catCarreraDao.getMapAll("");
		HashMap<String,CatPais> mapaPaises 		 		= catPaisDao.getMapAll();
		HashMap<String,CatEstado> mapaEstados 	 		= catEstadoDao.getMapAll();
		HashMap<String,CatCiudad> mapaCiudades 	 		= catCiudadDao.getMapAll("");
		HashMap<String,AlumPersonal> mapaPersonales 	= alumPersonalDao.getMapInscritos();
		HashMap<String,AlumActualiza> mapaActualizados	= alumActualizaDao.mapaInscritos();
		
		modelo.addAttribute("periodoNombre", periodoNombre);
		modelo.addAttribute("lisInscritos", lisInscritos);
		modelo.addAttribute("mapaUbicaciones", mapaUbicaciones);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaPaises", mapaPaises);
		modelo.addAttribute("mapaEstados", mapaEstados);
		modelo.addAttribute("mapaCiudades", mapaCiudades);
		modelo.addAttribute("mapaPersonales", mapaPersonales);
		modelo.addAttribute("mapaActualizados", mapaActualizados);
    	
		return "mentores/reportes/listDirecciones";
	}
	
	@RequestMapping("/mentores/reportes/historico")
	public String mentoresEntrevistasEntraFacultad(HttpServletRequest request, Model modelo){
		String codigoPersonal	= "0";
		String periodoId		= request.getParameter("periodo")==null?"0":request.getParameter("periodo"); 	
		MentAcceso mentAcceso = new MentAcceso();
		Acceso acceso = new Acceso();
		HttpSession sesion		= ((HttpServletRequest)request).getSession();		
        if (sesion!=null){
        	codigoPersonal = (String) sesion.getAttribute("codigoPersonal");
        	if (periodoId.equals("0")) {
    			periodoId = (String) sesion.getAttribute("ciclo");
    		}
        	mentAcceso = mentAccesoDao.mapeaRegId(codigoPersonal);
        	acceso = accesoDao.mapeaRegId(codigoPersonal);
        }	
    	
        List<CatPeriodo> lisPeriodo 						= catPeriodoDao.getListAll("ORDER BY PERIODO_ID");
       	List<CatCarrera> lisCarrera							= catCarreraDao.getListAll("ORDER BY 1,4"); 	
       	HashMap<String,String> mapaMentores					= mentCarreraDao.mapNumMentoresCarrera(periodoId);
       	HashMap<String,CatFacultad> mapaFacultades 			= catFacultadDao.getMapFacultad("");
       	
    	modelo.addAttribute("mentAcceso", mentAcceso);
    	modelo.addAttribute("acceso", acceso);
       	modelo.addAttribute("lisPeriodo", lisPeriodo);
		modelo.addAttribute("lisCarrera", lisCarrera);
		modelo.addAttribute("mapaMentores", mapaMentores);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		
		return "mentores/reportes/historico";
	}
	
	@RequestMapping("/mentores/reportes/aconsejados")
	public String mentoresReportesAconsejados(HttpServletRequest request, Model modelo){
		String periodoId		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String carreraId		= request.getParameter("CarreraId")==null?"0":request.getParameter("CarreraId");
		String carreraNombre 	= catCarreraDao.getNombreCarrera(carreraId);	
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	periodoId 		= (String) sesion.getAttribute("ciclo");
        }
        
		// Lista de aconsejados
		List<MentAlumno> lisAlumnos				= mentAlumnoDao.getListMentores(periodoId, " AND CARRERA_ID = '"+carreraId+"' ORDER BY MENTOR_ID, ALUM_APELLIDO(CODIGO_PERSONAL)");
		HashMap<String,String> mapaMaestros		= maestrosDao.mapMaestroNombre("NOMBRE");	
		HashMap<String,String> mapaAlumnos		= alumPersonalDao.mapaAlumnosEnAconsejados(periodoId);
		
		modelo.addAttribute("carreraId", carreraId);
		modelo.addAttribute("carreraNombre", carreraNombre);
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("mapaMaestros", mapaMaestros);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
    	
		return "mentores/reportes/aconsejados";
	}
	
	@RequestMapping("/mentores/reportes/modalidades")
	public String mentoresReportesModalidades(HttpServletRequest request, Model modelo){
		String cargas				= "0";
		String accion 				= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		
		List<CatModalidad> lisModalidades		= catModalidadDao.getListAll("");
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	cargas = (String)sesion.getAttribute("cargas")==null?"0":sesion.getAttribute("cargas").toString();
        	if (accion.equals("1")){		
    			String modalidades = "";
    			for(int i = 0; i < lisModalidades.size(); i++){
    				CatModalidad modalidad = (CatModalidad) lisModalidades.get(i);
    				if(request.getParameter(modalidad.getModalidadId()) != null ){			
    					if(modalidades.equals(""))
    						modalidades = "'"+modalidad.getModalidadId()+"'";
    					else
    						modalidades += ",'"+modalidad.getModalidadId()+"'";						
    				}
    			}
    			sesion.setAttribute("modalidades", modalidades);	
    		}
        }
        
		HashMap<String,String> mapaAlumnos 		= estadisticaDao.mapaModalidadesEnCargas(cargas);
		
		modelo.addAttribute("lisModalidades", lisModalidades);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);		
    	
		return "mentores/reportes/modalidades";
	}
	
	@RequestMapping("/mentores/reportes/perfilMaterias")
	public String mentoresReportesPerfilMaterias(HttpServletRequest request, Model modelo){
				        
		String periodoId 		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String cargaId 			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String modalidades		= "1";
		
        List<CatPeriodo> lisPeriodos = catPeriodoDao.getListAll(" ORDER BY PERIODO_ID DESC");
        
        HttpSession sesion		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	if (periodoId.equals("0")) {
        		periodoId = (String)sesion.getAttribute("periodo");
        	}else {
        		sesion.setAttribute("periodo", periodoId);
        	}
        	if (cargaId.equals("0")) {
        		cargaId = (String)sesion.getAttribute("cargaId");
        	}
        }
        
        List<Carga> lisCargas = cargaDao.getListAll(" WHERE PERIODO IN ('"+periodoId+"') AND ESTADO = '1' ORDER BY CARGA_ID");
        boolean existe = false;
        for (Carga carga: lisCargas) {
        	if (carga.getCargaId().equals(cargaId)) existe=true;
        }
        if (!existe && lisCargas.size()>0) {
        	cargaId = lisCargas.get(0).getCargaId();
        }
        if (sesion != null){        	
        	sesion.setAttribute("cargaId", cargaId);           
        	modalidades			= (String)sesion.getAttribute("modalidades") == null?"'1'":sesion.getAttribute("modalidades").toString();
        }	
        
        //Lista de Materias reprobadas
    	List<CargaAcademica> lisReprobadas					= cargaAcademicaDao.getMatReprobadas(cargaId, modalidades);	
    	//Mapa de inscritos
    	HashMap<String,String> mapaInscritos 				= krdxCursoActDao.mapaAlumMatPorTipos(cargaId, "'I','1','2','3','4','5','6'");
    	//Mapa de reprobados
    	HashMap<String,String> mapaReprobados 				= krdxCursoActDao.mapaAlumMatPorTipos(cargaId, "'2','4'");
    	//Mapa de reprobados por ausencia
    	HashMap<String,String> mapaReproAusencias	 		= krdxCursoActDao.mapaAlumMatPorTipos(cargaId, "'4'");
    	//Mapa dados de baja
    	HashMap<String,String> mapaBajas					= krdxCursoActDao.mapaAlumMatPorTipos(cargaId, "'3'");
    	//Mapa de las carreras
    	HashMap<String,CatCarrera> mapaCarreras 			= catCarreraDao.getMapAll("");
    	//Mapa de las facultades
    	HashMap<String,CatFacultad> mapaFacultades 			= catFacultadDao.getMapFacultad("");
    	HashMap<String,CatModalidad> mapaModalidades 		= catModalidadDao.getMapAll("");
    	
        modelo.addAttribute("periodoId", periodoId);
        modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("lisCargas", lisCargas);		
		modelo.addAttribute("lisReprobadas", lisReprobadas);
		
		modelo.addAttribute("mapaInscritos", mapaInscritos);
		modelo.addAttribute("mapaReprobados", mapaReprobados);
		modelo.addAttribute("mapaReproAusencias", mapaReproAusencias);
		modelo.addAttribute("mapaBajas", mapaBajas);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaModalidades", mapaModalidades);
		
		return "mentores/reportes/perfilMaterias";
	}
	
	@RequestMapping("/mentores/reportes/estadistica")
	public String mentoresReportesEstadistica(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMentoresReportes|mentoresReportesEstadistica:");
		return "mentores/reportes/estadistica";
	}	
	
	@RequestMapping("/mentores/reportes/est_perfil")
	public String mentoresReportesEst_perfil(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMentoresReportes|mentoresReportesEst_perfil:");
		return "mentores/reportes/est_perfil";
	}	
	
	@RequestMapping("/mentores/reportes/est_perfil_ment")
	public String mentoresReportesEst_perfil_ment(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMentoresReportes|mentoresReportesEst_perfil_ment:");
		return "mentores/reportes/est_perfil_ment";
	}	
	
	@RequestMapping("/mentores/reportes/fecha")
	public String mentoresReportesFecha(HttpServletRequest request){
		//enviarConEnoc(request,"Error-aca.ContMentoresReportes|mentoresReportesFecha:");
		return "mentores/reportes/fecha";
	}	
	
	@RequestMapping("/mentores/reportes/menu_mentor_admin")
	public String mentoresReportesMenu_mentor_admin(HttpServletRequest request){
		//enviarConEnoc(request,"Error-aca.ContMentoresReportes|mentoresReportesMenu_mentor_admin:");
		return "mentores/reportes/menu_mentor_admin";	
	}
	
	@RequestMapping("/mentores/reportes/mentor_perfil")
	public String mentoresReportesMentor_perfil(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMentoresReportes|mentoresReportesMentor_perfil:");
		return "mentores/reportes/mentor_perfil";
	}	
	
	@RequestMapping("/mentores/reportes/perfilAlumnos")
	public String mentoresReportesPerfilAlumnos(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMentoresReportes|mentoresReportesPerfilAlumnos:");
		return "mentores/reportes/perfilAlumnos";
	}	
	
	@RequestMapping("/mentores/reportes/perfilReprobados")
	public String mentoresReportesPerfilReprobados(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMentoresReportes|mentoresReportesPerfilReprobados:");
		return "mentores/reportes/perfilReprobados";
	}	
	
}