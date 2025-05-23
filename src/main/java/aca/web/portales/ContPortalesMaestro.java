package aca.web.portales;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumAcademico;
import aca.alumno.spring.AlumAcademicoDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanDao;
import aca.archivos.spring.ArchivosAlumno;
import aca.archivos.spring.ArchivosAlumnoDao;
import aca.archivos.spring.ArchivosProfesor;
import aca.archivos.spring.ArchivosProfesorDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaAlumnoDao;
import aca.carga.spring.CargaBloque;
import aca.carga.spring.CargaBloqueDao;
import aca.carga.spring.CargaDao;
import aca.carga.spring.CargaGrupo;
import aca.carga.spring.CargaGrupoActividad;
import aca.carga.spring.CargaGrupoActividadDao;
import aca.carga.spring.CargaGrupoAsistencia;
import aca.carga.spring.CargaGrupoAsistenciaDao;
import aca.carga.spring.CargaGrupoBorra;
import aca.carga.spring.CargaGrupoBorraDao;
import aca.carga.spring.CargaGrupoCursoDao;
import aca.carga.spring.CargaGrupoDao;
import aca.carga.spring.CargaGrupoEvaluacion;
import aca.carga.spring.CargaGrupoEvaluacionDao;
import aca.carga.spring.CargaGrupoHoraDao;
import aca.carga.spring.CargaGrupoProgramacion;
import aca.carga.spring.CargaGrupoProgramacionDao;
import aca.carga.spring.CargaPron;
import aca.carga.spring.CargaPronDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatCiudad;
import aca.catalogo.spring.CatCiudadDao;
import aca.catalogo.spring.CatEdificio;
import aca.catalogo.spring.CatEdificioDao;
import aca.catalogo.spring.CatEstado;
import aca.catalogo.spring.CatEstadoDao;
import aca.catalogo.spring.CatEstrategia;
import aca.catalogo.spring.CatEstrategiaDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatGradePointDao;
import aca.catalogo.spring.CatHorario;
import aca.catalogo.spring.CatHorarioDao;
import aca.catalogo.spring.CatHorarioFacultad;
import aca.catalogo.spring.CatHorarioFacultadDao;
import aca.catalogo.spring.CatModalidad;
import aca.catalogo.spring.CatModalidadDao;
import aca.catalogo.spring.CatPais;
import aca.catalogo.spring.CatPaisDao;
import aca.catalogo.spring.CatPeriodo;
import aca.catalogo.spring.CatPeriodoDao;
import aca.catalogo.spring.CatReligion;
import aca.catalogo.spring.CatReligionDao;
import aca.catalogo.spring.CatSalon;
import aca.catalogo.spring.CatSalonDao;
import aca.catalogo.spring.CatTipoCal;
import aca.catalogo.spring.CatTipoCalDao;
import aca.edo.spring.Edo;
import aca.edo.spring.EdoAlumnoPreg;
import aca.edo.spring.EdoAlumnoPregDao;
import aca.edo.spring.EdoAlumnoResp;
import aca.edo.spring.EdoAlumnoRespDao;
import aca.edo.spring.EdoDao;
import aca.edo.spring.EdoPeriodo;
import aca.edo.spring.EdoPeriodoDao;
import aca.graduacion.spring.AlumEgresoDao;
import aca.hca.spring.HcaActividad;
import aca.hca.spring.HcaActividadDao;
import aca.hca.spring.HcaMaestro;
import aca.hca.spring.HcaMaestroActividad;
import aca.hca.spring.HcaMaestroActividadDao;
import aca.hca.spring.HcaMaestroDao;
import aca.hca.spring.HcaRango;
import aca.hca.spring.HcaRangoDao;
import aca.hca.spring.HcaTipo;
import aca.hca.spring.HcaTipoDao;
import aca.kardex.spring.KrdxAlumnoActiv;
import aca.kardex.spring.KrdxAlumnoActivDao;
import aca.kardex.spring.KrdxAlumnoEval;
import aca.kardex.spring.KrdxAlumnoEvalDao;
import aca.kardex.spring.KrdxCursoAct;
import aca.kardex.spring.KrdxCursoActDao;
import aca.kardex.spring.KrdxCursoCal;
import aca.kardex.spring.KrdxCursoCalDao;
import aca.log.spring.LogOperacion;
import aca.log.spring.LogOperacionDao;
import aca.maestro.MaestroRango;
import aca.maestro.MaestroRangoDao;
import aca.maestro.MaestroRangoFechaDao;
import aca.musica.spring.MusiHorario;
import aca.musica.spring.MusiHorarioAlumnoDao;
import aca.musica.spring.MusiHorarioDao;
import aca.objeto.spring.Hora;
import aca.objeto.spring.HoraDao;
import aca.parametros.spring.Parametros;
import aca.parametros.spring.ParametrosDao;
import aca.plan.spring.MapaCurso;
import aca.plan.spring.MapaCursoDao;
import aca.plan.spring.MapaNuevoActividad;
import aca.plan.spring.MapaNuevoActividadDao;
import aca.plan.spring.MapaNuevoBibliografia;
import aca.plan.spring.MapaNuevoBibliografiaDao;
import aca.plan.spring.MapaNuevoCurso;
import aca.plan.spring.MapaNuevoProducto;
import aca.plan.spring.MapaNuevoProductoDao;
import aca.plan.spring.MapaNuevoUnidad;
import aca.plan.spring.MapaNuevoUnidadDao;
import aca.plan.spring.MapaPlanDao;
import aca.por.spring.PorHora;
import aca.por.spring.PorSalonDao;
import aca.pron.spring.PronBiblio;
import aca.pron.spring.PronBiblioDao;
import aca.pron.spring.PronEjes;
import aca.pron.spring.PronEjesDao;
import aca.pron.spring.PronEsquema;
import aca.pron.spring.PronEsquemaDao;
import aca.pron.spring.PronMateria;
import aca.pron.spring.PronMateriaDao;
import aca.pron.spring.PronSemana;
import aca.pron.spring.PronSemanaDao;
import aca.pron.spring.PronUnidad;
import aca.pron.spring.PronUnidadDao;
import aca.pron.spring.PronValor;
import aca.pron.spring.PronValorDao;
import aca.util.Fecha;
import aca.vista.spring.AlumnoCurso;
import aca.vista.spring.AlumnoCursoDao;
import aca.vista.spring.AlumnoEficiencia;
import aca.vista.spring.AlumnoEficienciaDao;
import aca.vista.spring.AlumnoEvaluacionDao;
import aca.vista.spring.CargaAcademica;
import aca.vista.spring.CargaAcademicaDao;
import aca.vista.spring.CargaHorario;
import aca.vista.spring.CargaHorarioDao;
import aca.vista.spring.Maestros;
import aca.vista.spring.MaestrosDao;
import aca.vista.spring.UsuariosDao;

@Controller
public class ContPortalesMaestro{
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	aca.por.spring.PorHoraDao porHoraDao;
	
	@Autowired
	aca.por.spring.PorRegistroDao porRegistroDao;
	
	@Autowired
	private UsuariosDao usuariosDao;
	
	@Autowired
	private MaestrosDao maestrosDao;
	
	@Autowired
	private PorSalonDao porSalonDao;
	
	@Autowired
	private PronMateriaDao pronMateriaDao;
	
	@Autowired
	private PronUnidadDao pronUnidadDao;
	
	@Autowired
	private PronEjesDao pronEjesDao;

	@Autowired
	private PronValorDao pronValorDao;

	@Autowired
	private PronSemanaDao pronSemanaDao;
	
	@Autowired
	private PronEsquemaDao pronEsquemaDao;

	@Autowired
	private PronBiblioDao pronBiblioDao;

	@Autowired
	private MapaNuevoUnidadDao mapaNuevoUnidadDao;

	@Autowired
	private MapaNuevoActividadDao mapaNuevoActividadDao;

	@Autowired
	private MapaCursoDao mapaCursoDao;

	@Autowired
	private MapaNuevoProductoDao mapaNuevoProductoDao;
	
	@Autowired
	private MapaNuevoBibliografiaDao mapaNuevoBibliografiaDao;

	@Autowired
	private CatCarreraDao catCarreraDao;

	@Autowired
	private CatFacultadDao catFacultadDao;
	
	@Autowired
	private CargaDao cargaDao;
	
	@Autowired
	private CargaGrupoDao cargaGrupoDao;
	
	@Autowired	
	private CargaGrupoCursoDao cargaGrupoCursoDao;

	@Autowired	
	private CargaPronDao cargaPronDao;
	
	@Autowired
	private aca.plan.spring.MapaNuevoCursoDao mapaNuevoCursoDao;
	
	@Autowired
	CargaGrupoEvaluacionDao cargaGrupoEvaluacionDao;
	
	@Autowired
	AlumnoCursoDao alumnoCursoDao;
	
	@Autowired
	CatModalidadDao catModalidadDao;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;
	
	@Autowired
	AlumAcademicoDao alumAcademicoDao;
	
	@Autowired
	EdoAlumnoPregDao edoAlumnoPregDao;
	
	@Autowired
	EdoAlumnoRespDao edoAlumnoRespDao;
		
	@Autowired
	CargaGrupoProgramacionDao cargaGrupoProgramacionDao;
	
	@Autowired
	CargaGrupoActividadDao cargaGrupoActividadDao;
	
	@Autowired
	CargaGrupoAsistenciaDao cargaGrupoAsistenciaDao;
	
	@Autowired
	CatTipoCalDao catTipoCalDao;
	
	@Autowired
	CatPaisDao catPaisDao;
	
	@Autowired
	CatEstadoDao catEstadoDao;
	
	@Autowired
	CatCiudadDao catCiudadDao;
	
	@Autowired
	CatEstrategiaDao catEstrategiaDao;
	
	@Autowired
	CatReligionDao catReligionDao;
	
	@Autowired
	CatPeriodoDao catPeriodoDao;
	
	@Autowired
	CargaAcademicaDao cargaAcademicaDao;
	
	@Autowired
	CargaGrupoHoraDao cargaGrupoHoraDao;
	
	@Autowired
	KrdxCursoActDao krdxCursoActDao;
	
	@Autowired
	LogOperacionDao logOperacionDao;
	
	@Autowired
	KrdxAlumnoEvalDao krdxAlumnoEvalDao;
	
	@Autowired
	AlumnoEvaluacionDao alumnoEvaluacionDao;
	
	@Autowired
	AlumPlanDao alumPlanDao;
	
	@Autowired
	KrdxAlumnoActivDao krdxAlumnoActivDao;
	
	@Autowired
	KrdxCursoCalDao krdxCursoCalDao;
	
	@Autowired
	AlumnoEficienciaDao alumnoEficienciaDao;
	
	@Autowired
	MusiHorarioDao musiHorarioDao;
	
	@Autowired
	MusiHorarioAlumnoDao musiHorarioAlumnoDao;
	
	@Autowired
	ArchivosProfesorDao archivosProfesorDao;
	
	@Autowired
	ArchivosAlumnoDao archivosAlumnoDao;
	
	@Autowired
	CargaBloqueDao cargaBloqueDao;
	
	@Autowired
	private CatHorarioFacultadDao catHorarioFacultadDao;
	
	@Autowired
	private HoraDao horaDao;
	
	@Autowired
	private CatEdificioDao catEdificioDao;
	
	@Autowired
	private CatSalonDao catSalonDao;
	
	@Autowired
	private CatHorarioDao catHorarioDao;
	
	@Autowired
	private CatGradePointDao catGradePointDao;
	
	@Autowired
	private MapaPlanDao mapaPlanDao;
	
	@Autowired	
	private ParametrosDao parametrosDao;
	
	@Autowired	
	private EdoDao edoDao;
	
	@Autowired	
	private EdoPeriodoDao edoPeriodoDao;
	
	@Autowired
	private CargaHorarioDao cargaHorarioDao;
	
	@Autowired
	private CargaAlumnoDao cargaAlumnoDao;
	
	@Autowired
	HcaMaestroDao hcaMaestroDao;
	
	@Autowired
	AccesoDao accesoDao;
	
	@Autowired
	HcaRangoDao hcaRangoDao;
	
	@Autowired
	HcaMaestroActividadDao hcaMaestroActividadDao;
	
	@Autowired
	HcaActividadDao hcaActividadDao;
	
	@Autowired
	HcaTipoDao hcaTipoDao;
	
	@Autowired
	CargaGrupoBorraDao cargaGrupoBorraDao;
	
	@Autowired
	MaestroRangoDao maestroRangoDao;
	
	@Autowired
	AlumEgresoDao alumEgresoDao;
	
	@Autowired
	MaestroRangoFechaDao maestroRangoFechaDao;
	
	@Autowired
	ServletContext context;	
	
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
			//System.out.println("Abrir conEnoc"+url);
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/portales/maestro/acta")
	public String portalesMaestroActa(HttpServletRequest request, Model modelo){
		
		String cursoCargaId		= request.getParameter("cursoCargaId")==null?"0":request.getParameter("cursoCargaId");
		String estado			= "X";
		String maestroNombre	= "-";
		String facultadNombre	= "-";
		String carreraNombre	= "-";
		String coordinador		= "-";
		CargaGrupo cargaGrupo 	= new CargaGrupo();
		MapaCurso mapaCurso		= new MapaCurso();
		Parametros parametros 	= parametrosDao.mapeaRegId("1");
		if (cargaGrupoDao.existeReg(cursoCargaId)){			
			cargaGrupo 			= cargaGrupoDao.mapeaRegId(cursoCargaId);
			estado 				= cargaGrupo.getEstado();
			mapaCurso 			= mapaCursoDao.mapeaRegId(cargaGrupoDao.getCursoId(cursoCargaId));
			maestroNombre		= usuariosDao.getNombreUsuario(cargaGrupo.getCodigoPersonal(), "NOMBRE");
			facultadNombre 		= catFacultadDao.getNombreFacultad(catCarreraDao.getFacultadId(cargaGrupo.getCarreraId()));
			carreraNombre 		= catCarreraDao.getNombreCarrera(cargaGrupo.getCarreraId());
			coordinador			= usuariosDao.getNombreUsuario(catCarreraDao.getCoordinador(cargaGrupo.getCarreraId()), "NOMBRE");
		}
		
		List<CargaGrupoEvaluacion> lisEvaluaciones 			= cargaGrupoEvaluacionDao.getLista( cursoCargaId, " ORDER BY SUBSTR(FECHA,7,4)||SUBSTR(FECHA,4,5)||SUBSTR(FECHA,1,2), EVALUACION_ID");
		List<AlumnoCurso> lisAlumnos 						= alumnoCursoDao.getListCurso(cursoCargaId, " ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");
		
		HashMap<String,CatEstrategia> mapaEstrategias 		= catEstrategiaDao.getMapAll("");
		HashMap<String,KrdxAlumnoEval> mapaEvaluaciones	 	= krdxAlumnoEvalDao.mapAlumEval(cursoCargaId);
		HashMap<String,KrdxCursoAct> mapaNotas	 			= krdxCursoActDao.mapaNotas(cursoCargaId);
		HashMap<String,CatTipoCal> mapaTipoCal	 			= catTipoCalDao.getMapAll("");
		HashMap<String,String> mapaEvaluadas				= alumnoEvaluacionDao.mapaSumaValor(cursoCargaId, "'%','P'");
		HashMap<String,String> mapaExtras					= alumnoEvaluacionDao.mapaSumaValor(cursoCargaId, "'E'");
		HashMap<String,String> mapaAlumnoPuntos				= alumnoEvaluacionDao.mapaPuntosAlumno(cursoCargaId, "'%','P'");
		HashMap<String,String> mapaAlumnoExtras				= alumnoEvaluacionDao.mapaPuntosAlumno(cursoCargaId, "'E'");
		HashMap<String,String> mapaAlumnos					= alumPersonalDao.mapAlumnosMateria(cursoCargaId);
		
		modelo.addAttribute("cargaGrupo", cargaGrupo);
		modelo.addAttribute("mapaCurso", mapaCurso);
		modelo.addAttribute("parametros", parametros);
		modelo.addAttribute("estado", estado);
		modelo.addAttribute("maestroNombre", maestroNombre);
		modelo.addAttribute("facultadNombre", facultadNombre);
		modelo.addAttribute("carreraNombre", carreraNombre);
		modelo.addAttribute("coordinador", coordinador);
				
		modelo.addAttribute("lisEvaluaciones", lisEvaluaciones);
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("mapaEstrategias", mapaEstrategias);
		modelo.addAttribute("mapaEvaluaciones", mapaEvaluaciones);
		modelo.addAttribute("mapaNotas", mapaNotas);
		modelo.addAttribute("mapaEvaluadas", mapaEvaluadas);
		modelo.addAttribute("mapaExtras", mapaExtras);
		modelo.addAttribute("mapaAlumnoPuntos", mapaAlumnoPuntos);
		modelo.addAttribute("mapaAlumnoExtras", mapaAlumnoExtras);
		modelo.addAttribute("mapaTipoCal", mapaTipoCal);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		
		return "portales/maestro/acta";
	}
	
	@RequestMapping("/portales/maestro/actames")
	public String portalesMaestroActaMes(HttpServletRequest request, Model modelo){
		//enviarConEnoc(request,"Error-aca.ContPortalesMaestro|portalesMaestroActaMes");
		
		String cursoCargaId		= request.getParameter("cursoCargaId")==null?"0":request.getParameter("cursoCargaId");
		String estado			= "X";
		String maestroNombre	= "-";
		String facultadNombre	= "-";
		String carreraNombre	= "-";
		String coordinador		= "-";
		CargaGrupo cargaGrupo 	= new CargaGrupo();
		MapaCurso mapaCurso		= new MapaCurso();
		Parametros parametros 	= parametrosDao.mapeaRegId("1");
		if (cargaGrupoDao.existeReg(cursoCargaId)){			
			cargaGrupo 			= cargaGrupoDao.mapeaRegId(cursoCargaId);
			estado 				= cargaGrupo.getEstado();
			mapaCurso 			= mapaCursoDao.mapeaRegId(cargaGrupoDao.getCursoId(cursoCargaId));
			maestroNombre		= usuariosDao.getNombreUsuario(cargaGrupo.getCodigoPersonal(), "NOMBRE");
			facultadNombre 		= catFacultadDao.getNombreFacultad(catCarreraDao.getFacultadId(cargaGrupo.getCarreraId()));
			carreraNombre 		= catCarreraDao.getNombreCarrera(cargaGrupo.getCarreraId());
			coordinador			= usuariosDao.getNombreUsuario(catCarreraDao.getCoordinador(cargaGrupo.getCarreraId()), "NOMBRE");
		}
		
		List<CargaGrupoEvaluacion> lisEvaluaciones 			= cargaGrupoEvaluacionDao.getLista( cursoCargaId, " ORDER BY SUBSTR(FECHA,7,4)||SUBSTR(FECHA,4,5)||SUBSTR(FECHA,1,2), EVALUACION_ID");
		List<AlumnoCurso> lisAlumnos 						= alumnoCursoDao.getListCurso(cursoCargaId, " ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");
		List<String> lisMeses								= alumnoEvaluacionDao.getActaMeses(cursoCargaId);
		
		HashMap<String,CatEstrategia> mapaEstrategias 		= catEstrategiaDao.getMapAll("");
		HashMap<String,KrdxAlumnoEval> mapaEvaluaciones	 	= krdxAlumnoEvalDao.mapAlumEval(cursoCargaId);
		HashMap<String,KrdxCursoAct> mapaNotas	 			= krdxCursoActDao.mapaNotas(cursoCargaId);
		HashMap<String,CatTipoCal> mapaTipoCal	 			= catTipoCalDao.getMapAll("");
		HashMap<String,AlumPlan> mapaPlanes					= alumPlanDao.mapaPorMateria(cursoCargaId);
		HashMap<String,String> mapaEvaluadas				= alumnoEvaluacionDao.mapaSumaValor(cursoCargaId, "'%','P'");
		HashMap<String,String> mapaExtras					= alumnoEvaluacionDao.mapaSumaValor(cursoCargaId, "'E'");
		HashMap<String,String> mapaAlumnoPuntos				= alumnoEvaluacionDao.mapaPuntosAlumno(cursoCargaId, "'%','P'");
		HashMap<String,String> mapaAlumnoExtras				= alumnoEvaluacionDao.mapaPuntosAlumno(cursoCargaId, "'E'");
		HashMap<String,String> mapaAlumnos					= alumPersonalDao.mapAlumnosMateria(cursoCargaId);
		HashMap<String,String> mapaPromediosPorMes			= alumnoEvaluacionDao.mapaPromedioPorMes(cursoCargaId);
		
		modelo.addAttribute("cargaGrupo", cargaGrupo);
		modelo.addAttribute("mapaCurso", mapaCurso);
		modelo.addAttribute("estado", estado);
		modelo.addAttribute("maestroNombre", maestroNombre);
		modelo.addAttribute("facultadNombre", facultadNombre);
		modelo.addAttribute("carreraNombre", carreraNombre);
		modelo.addAttribute("coordinador", coordinador);
		modelo.addAttribute("parametros", parametros);
				
		modelo.addAttribute("lisEvaluaciones", lisEvaluaciones);
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("lisMeses", lisMeses);
		
		modelo.addAttribute("mapaEstrategias", mapaEstrategias);
		modelo.addAttribute("mapaEvaluaciones", mapaEvaluaciones);
		modelo.addAttribute("mapaNotas", mapaNotas);
		modelo.addAttribute("mapaEvaluadas", mapaEvaluadas);
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		modelo.addAttribute("mapaExtras", mapaExtras);
		modelo.addAttribute("mapaAlumnoPuntos", mapaAlumnoPuntos);
		modelo.addAttribute("mapaAlumnoExtras", mapaAlumnoExtras);
		modelo.addAttribute("mapaTipoCal", mapaTipoCal);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("mapaPromediosPorMes", mapaPromediosPorMes);
		
		return "portales/maestro/actames";
	}
	
	@RequestMapping("/portales/maestro/actividad")
	public String portalesMaestroActividad(HttpServletRequest request, Model modelo){		
		
		String cursoCargaId			= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		String evaluacionId			= request.getParameter("EvaluacionId")==null?"0":request.getParameter("EvaluacionId");
		String actividadId			= request.getParameter("ActividadId")==null?"0":request.getParameter("ActividadId");
		CargaGrupo cargaGrupo 		= cargaGrupoDao.mapeaRegId(cursoCargaId);
		CargaGrupoActividad cga 	= new CargaGrupoActividad();
		CargaGrupoEvaluacion cge 	= new CargaGrupoEvaluacion();
		
		if (cargaGrupoActividadDao.existeRegId(actividadId)) {
			cga = cargaGrupoActividadDao.mapeaRegId(actividadId);
		}		
		if (cargaGrupoEvaluacionDao.existeReg(cursoCargaId, evaluacionId)){ 
			cge = cargaGrupoEvaluacionDao.mapeaRegId(cursoCargaId, evaluacionId);		
		}
		
		String maestroNombre 	= usuariosDao.getNombreUsuario(cargaGrupo.getCodigoPersonal(), "NOMBRE");
		String cursoOrigen 		= cargaGrupoCursoDao.cursoIdOrigen(cursoCargaId);
		String materiaNombre 	= mapaCursoDao.getNombreCurso(cursoOrigen);
		String metodo			= catEstrategiaDao.getNombreEstrategia(cge.getEstrategiaId().trim());
		
		modelo.addAttribute("cargaGrupo", cargaGrupo);		
		modelo.addAttribute("cga", cga);
		modelo.addAttribute("maestroNombre", maestroNombre);
		modelo.addAttribute("materiaNombre", materiaNombre);
		modelo.addAttribute("metodo", metodo);
		
		return "portales/maestro/actividad";
	}
	
	@RequestMapping("/portales/maestro/grabarActividad")
	public String portalesMaestroGrabarActividad(HttpServletRequest request, Model modelo){	
		
		String codigoPersonal 		= "0";
		String cursoCargaId			= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		String evaluacionId			= request.getParameter("EvaluacionId")==null?"0":request.getParameter("EvaluacionId");
		String actividadId			= request.getParameter("ActividadId")==null?"0":request.getParameter("ActividadId");
		String enviar				= request.getParameter("Enviar")==null?"X":request.getParameter("Enviar");
		
		CargaGrupoActividad cga 	= new CargaGrupoActividad();
		LogOperacion log			= new LogOperacion();		
		
		HttpSession sesion		= request.getSession();
		if (sesion!=null){
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
		}
		
		if (cargaGrupoActividadDao.existeRegId(actividadId)) {
			cga = cargaGrupoActividadDao.mapeaRegId(actividadId);
		}
		
		String resultado	= "-";	
		cga.setNombre(request.getParameter("Nombre"));
		cga.setFecha(request.getParameter("Fecha").substring(0,10)+" "+request.getParameter("Hora")+":"+request.getParameter("Minuto")+":00");
		if(actividadId.equals("0")) actividadId = cargaGrupoActividadDao.maximoReg();
		cga.setActividadId(actividadId);
		String valor = request.getParameter("Valor");
		if (valor==null || valor.equals("") || valor.equals("null")) 
			valor="0";
		cga.setValor(valor);
		cga.setCursoCargaId(cursoCargaId);
		cga.setEvaluacionId(evaluacionId);
		cga.setActividadE42("0");
		cga.setEnviar(enviar);
		
		/********* LOG ****/
		String datos = "CursoCargaId: "+cursoCargaId+", CodigoPersonal: "+codigoPersonal+ ", Valor: "+valor+", EvaluacionId: "+evaluacionId;			
		log.setDatos(datos);
		log.setIp(request.getRemoteAddr());
		log.setUsuario(codigoPersonal);
		log.setTabla("CARGA_GRUPO_ACTIVIDAD");
		
		if (!cargaGrupoActividadDao.existeReg(cga.getActividadId(), cursoCargaId)){
			if (cargaGrupoActividadDao.insertReg(cga)){
				/******* LOG ******/
				log.setOperacion("insert");
				logOperacionDao.insertReg(log);
				resultado = "Strategy saved: "+cga.getNombre();
			}else{
				resultado = "Strategy not saved: "+cga.getNombre();
			}
		}else{
			if (cargaGrupoActividadDao.updateReg(cga)){
				/******* LOG ******/
				log.setOperacion("update");
				logOperacionDao.insertReg(log);
				resultado = "Strategy Updated: "+cga.getNombre();
			}else{
				resultado = "Not saved: "+cga.getNombre();
			}
		}
		
		return "redirect:/portales/maestro/actividad?CursoCargaId="+cursoCargaId+"&EvaluacionId="+evaluacionId+"&ActividadId="+actividadId+"&Resultado="+resultado;
	}
	
	@RequestMapping("/portales/maestro/altafechas")
	public String portalesMaestroAltaFechas(HttpServletRequest request, Model modelo){
		String cursoCargaId 	= request.getParameter("CursoCargaId");
		String folio			= request.getParameter("Folio")==null?cargaGrupoProgramacionDao.maximoReg(cursoCargaId):request.getParameter("Folio");
		String fecha			= request.getParameter("Fecha")==null?aca.util.Fecha.getHoy():request.getParameter("Fecha");
		String orden			= request.getParameter("Orden")==null?"0":request.getParameter("Orden");	
		String materia 			= request.getParameter("Materia");
		String nombreMateria    = mapaCursoDao.getMateria(materia);
		String codigoPersonal	= "0";
		String nombreMaestro 	= "-"; 
		HttpSession sesion		= request.getSession();
		if (sesion!=null){
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
			nombreMaestro		= maestrosDao.getNombreMaestro(codigoPersonal, "NOMBRE"); 
		}	
		
		CargaGrupoProgramacion programa = new CargaGrupoProgramacion();
		programa.setCursoCargaId(cursoCargaId);
		programa.setFolio(folio);
		if(cargaGrupoProgramacionDao.existeReg(cursoCargaId, folio)){
			programa 	= cargaGrupoProgramacionDao.mapeaRegId(cursoCargaId, folio);
			fecha 		= programa.getFecha();
			orden 		= programa.getOrden();
		}
		
		modelo.addAttribute("folio", folio);
		modelo.addAttribute("fecha", fecha);
		modelo.addAttribute("orden", orden);
		modelo.addAttribute("nombreMateria", nombreMateria);
		modelo.addAttribute("nombreMaestro", nombreMaestro);
		
		return "portales/maestro/altafechas";		
	}
	
	@RequestMapping("/portales/maestro/grabarfecha")
	public String portalesMaestroGrabarFecha(HttpServletRequest request){		
		String cursoCargaId			= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		String materia	 			= request.getParameter("Materia")==null?"0":request.getParameter("Materia");
		String folio	 			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String fecha				= request.getParameter("Fecha")==null?aca.util.Fecha.getHoy():request.getParameter("Fecha");
		String orden				= request.getParameter("Orden")==null?"0":request.getParameter("Orden");
		String mensaje 				= "-";
		
		CargaGrupoProgramacion programa = new CargaGrupoProgramacion();
		programa.setCursoCargaId(cursoCargaId);
		programa.setFolio(folio);
		programa.setFecha(fecha);
		programa.setOrden(orden);
		
		if(cargaGrupoProgramacionDao.existeReg(cursoCargaId, folio)){
			if (cargaGrupoProgramacionDao.updateReg(programa)) {
				mensaje = "¡Data saved!";
			}			
		}else{
			if (cargaGrupoProgramacionDao.insertReg(programa)){
				mensaje = "¡Date inserted!";
			}
		}
		
		return "redirect:/portales/maestro/altafechas?CursoCargaId="+cursoCargaId+"&Materia="+materia+"&Folio="+folio+"&Mensaje="+mensaje;
	}	
	
	@RequestMapping("/portales/maestro/borrarfecha")
	public String portalesMaestroBorrarFecha(HttpServletRequest request){		
	
		String cursoCargaId			= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		String materia	 			= request.getParameter("Materia")==null?"0":request.getParameter("Materia");
		String folio	 			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		
		// Buscar la cantidad de registros de asistencia
		String regAsistencia = cargaGrupoAsistenciaDao.numRegAsistencia(cursoCargaId, folio);
		if (Integer.parseInt(regAsistencia) > 0){
			if (cargaGrupoAsistenciaDao.deleteRegistros(cursoCargaId, folio)){				
				cargaGrupoProgramacionDao.deleteReg(cursoCargaId, folio);
			}
		}else{
			cargaGrupoProgramacionDao.deleteReg(cursoCargaId, folio);
		}		
		
		return "redirect:/portales/maestro/asistencia?CursoCargaId="+cursoCargaId+"&Materia="+materia;
	}	
	
	@RequestMapping("/portales/maestro/alumnos")
	public String portalesMaestroAlumnos(HttpServletRequest request, Model modelo){
		
		String cursoCargaId				= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		
		List<AlumnoCurso> lisAlumnos 				= alumnoCursoDao.getListCurso(cursoCargaId, "ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");
		HashMap<String,AlumPersonal> mapaAlumnos	= alumPersonalDao.mapAlumnosEnMateria(cursoCargaId);
		
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		
		return "portales/maestro/alumnos";
	}
	
	@RequestMapping("/portales/maestro/asistencia")
	public String portalesMaestroAsistencia(HttpServletRequest request, Model modelo){
		
		String codigoPersonal		= "";
		String cursoCargaId			= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");	
		String materia	 			= request.getParameter("Materia")==null?"0":request.getParameter("Materia");
		String nombreMaestro 		= "";
		String nombreMateria		= "";
		HttpSession sesion			= request.getSession();
		if (sesion!=null){
			codigoPersonal			= (String) sesion.getAttribute("codigoPersonal");
			nombreMaestro			= maestrosDao.getNombreMaestro(codigoPersonal, "NOMBRE");
			nombreMateria			= mapaCursoDao.getMateria(materia);			
		}
		
		List<AlumnoCurso> lisAlumnos 				= alumnoCursoDao.getListCurso(cursoCargaId, " ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");		
		List<CargaGrupoProgramacion> lisProgra 		= cargaGrupoProgramacionDao.getListMateria(cursoCargaId, " ORDER BY ORDEN, TO_CHAR(ENOC.CARGA_GRUPO_PROGRAMACION.FECHA,'YYYY-MM-DD')");
		
		// Consulta la asistencia de los alumnos en cada clase
		HashMap<String, String> mapAsistencia 		= cargaGrupoAsistenciaDao.mapAsistenciaClase(cursoCargaId);
		HashMap<String, String> mapAsistenciaTotal 	= cargaGrupoAsistenciaDao.mapAsistenciaTotal(cursoCargaId);		
		HashMap<String, AlumPersonal> mapPersonal 	= alumPersonalDao.mapAlumnosEnMateria(cursoCargaId);
		
		modelo.addAttribute("nombreMaestro", nombreMaestro);		
		modelo.addAttribute("nombreMateria", nombreMateria);
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("lisProgra", lisProgra);
		modelo.addAttribute("mapAsistencia", mapAsistencia);
		modelo.addAttribute("mapAsistenciaTotal", mapAsistenciaTotal);
		modelo.addAttribute("mapPersonal", mapPersonal);
		
		return "portales/maestro/asistencia";
	}	
	
	@RequestMapping("/portales/maestro/autoEvaluacion")
	public String portalesMaestroAutoEvaluacion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPortalesMaestro|portalesMaestroAutoEvaluacion");
		return "portales/maestro/autoEvaluacion";
	}
	
	@RequestMapping("/portales/maestro/bibliografia")
	public String portalesMaestroBibliografia(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPortalesMaestro|portalesMaestroBibliografia");
		return "portales/maestro/bibliografia";
	}
	
	@RequestMapping("/portales/maestro/borrarNotas")
	public String portalesMaestroBorrarNotas(HttpServletRequest request, Model modelo){
		
		String codigoPersonal		= "0";
	 	String codigoEmpleado		= "0";
		String cursoCargaId			= request.getParameter("CursoCargaId")==null?"000000":request.getParameter("CursoCargaId");
		String cargaId 				= cursoCargaId.substring(0,6);
		String cargaNombre 			= cargaDao.getNombreCarga(cargaId);
		
		HttpSession sesion			= request.getSession();
		if (sesion!=null){
			codigoPersonal			= (String) sesion.getAttribute("codigoPersonal");
			codigoEmpleado			= (String) sesion.getAttribute("codigoEmpleado");
		}
		
		String cursoId			= cargaGrupoCursoDao.cursoIdOrigen(cursoCargaId);
		String nombreMaestro 	= maestrosDao.getNombreMaestro(codigoEmpleado,"Nombre");
		String nombreMateria 	= mapaCursoDao.getMateria(cursoId);
		
		int numInsc				= krdxCursoActDao.numAlumGrupo(cursoCargaId, "'I','1','2'");
		int numBaja				= krdxCursoActDao.numAlumGrupo(cursoCargaId, "'3'");
		
		int numEst				= cargaGrupoEvaluacionDao.getNumEst(cursoCargaId,"TODAS");
		int numEstReg			= krdxAlumnoEvalDao.getNumEst(cursoCargaId);		
		int numAct				= cargaGrupoActividadDao.getNumAct(cursoCargaId);
		int numActReg			= krdxAlumnoActivDao.getNumActividades(cursoCargaId);
		
		modelo.addAttribute("cursoCargaId", cursoCargaId);
		modelo.addAttribute("cursoId", cursoId);
		modelo.addAttribute("nombreMaestro", nombreMaestro);
		modelo.addAttribute("nombreMateria", nombreMateria);
		modelo.addAttribute("cargaNombre", cargaNombre);
		
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("numInsc", numInsc);
		modelo.addAttribute("numBaja", numBaja);
		
		modelo.addAttribute("numEst", numEst);
		modelo.addAttribute("numEstReg", numEstReg);
		modelo.addAttribute("numAct", numAct);
		modelo.addAttribute("numActReg", numActReg);
		
		return "portales/maestro/borrarNotas";
	}
	
	@Transactional
	@RequestMapping("/portales/maestro/deleteNotas")
	public String portalesMaestroDeleteNotas(HttpServletRequest request){
		
		String codigoPersonal		= "0";	 	
		String cursoCargaId			= request.getParameter("CursoCargaId")==null?"000000":request.getParameter("CursoCargaId");
		String cargaId 				= cursoCargaId.substring(0,6);
		
		HttpSession sesion			= request.getSession();
		if (sesion!=null){
			codigoPersonal			= (String) sesion.getAttribute("codigoPersonal");						
		}
		String folio 				= "";
		String mensaje				= "";
		
		int delEst=0,delEstReg=0,delAct=0,delActReg=0;	
		boolean bolDelete=false, bolEst=false, bolEstReg = false, bolAct=false, bolActReg=false;
		
		int numEst				= cargaGrupoEvaluacionDao.getNumEst(cursoCargaId,"TODAS");
		int numEstReg			= krdxAlumnoEvalDao.getNumEst(cursoCargaId);		
		int numAct				= cargaGrupoActividadDao.getNumAct(cursoCargaId);
		int numActReg			= krdxAlumnoActivDao.getNumActividades(cursoCargaId);
		
		// Borra las actividades calificadas de la materia
		if ( numActReg > 0 ){
			if ( krdxAlumnoActivDao.deleteAlumAct(cursoCargaId )==numActReg ){
				delActReg = numActReg;
				bolActReg = true;			
			}
		}else{
			bolActReg = true;
		}
	
		// Borra las actividades de la materia
		if ( bolActReg){
			if ( numAct > 0 ){
				if ( cargaGrupoActividadDao.deleteGpoAct(cursoCargaId)==numAct){
					delAct = numAct; 
					bolAct = true;
				}
			}else{
				bolAct = true;
			}
		}
		
		// Borra las evaluaciones calificadas en la materiaBorraNotas.setFecha( aca.util.Fecha.getHoy() );
		if (bolAct){
			if ( numEstReg > 0 ){
				if ( krdxAlumnoEvalDao.deleteAlumEval(cursoCargaId)==numEstReg ){
					delEstReg = numEstReg;
					bolEstReg = true;
				}
			}else{
				bolEstReg = true;
			}
		}
		
		// Borra las evaluaciones de la materia
		if ( bolEstReg ){
			if ( numEst > 0 ){
				if ( cargaGrupoEvaluacionDao.deleteGpoEval(cursoCargaId )==numEst ){
					delEst = numEst;
					bolEst = true;
				}
			}else{
				bolEst = true;
			}
		}
		
		// Registra los datos de quien borro las notas de la materia 
		if (bolActReg && bolAct && bolEst && bolEstReg){
			CargaGrupoBorra borrarNotas = new CargaGrupoBorra();
			borrarNotas.setCursoCargaId(cursoCargaId);
			folio = cargaGrupoBorraDao.maximoReg(cursoCargaId);
			borrarNotas.setFolio(folio);
			borrarNotas.setFecha( aca.util.Fecha.getHoy() );
			borrarNotas.setUsuario( codigoPersonal );
			borrarNotas.setIp(request.getRemoteAddr());
			borrarNotas.setNumEst( String.valueOf(numEst) );
			borrarNotas.setNumAct( String.valueOf(numAct) );
			if ( cargaGrupoBorraDao.insertReg(borrarNotas) ){			
				bolDelete = true;					
				borrarNotas = cargaGrupoBorraDao.mapeaRegId(cursoCargaId,folio);
				if (folio.length()==1) folio = 0+folio;
				mensaje = "<b>"+numEst+"</b> Strategies deleted and "+numAct+" Activities deleted. Operation number: <b>"+cursoCargaId+"-"+folio+"</b>";
			}			
		}
		return "redirect:/portales/maestro/borrarNotas?CursoCargaId="+cursoCargaId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/portales/maestro/cacta")
	public String portalesMaestroCacta(HttpServletRequest request){
		//enviarConEnoc(request,"Error-aca.ContPortalesMaestro|portalesMaestroCacta");
		return "portales/maestro/cacta";
	}
	
	@RequestMapping("/portales/maestro/cactames")
	public String portalesMaestroCactaMes(HttpServletRequest request){
		
		return "portales/maestro/cactames";
	}
	
	@RequestMapping("/portales/maestro/carga_docente")
	public String portalesMaestroCargaDocente(HttpServletRequest request, Model modelo){
		//enviarConEnoc(request,"Error-aca.ContPortalesMaestro|portalesMaestroCargaDocente");
		String periodoId			= request.getParameter("PeriodoId")==null?catPeriodoDao.getPeriodo():request.getParameter("PeriodoId");		
		String cargaId				= request.getParameter("carga")==null?"0":request.getParameter("carga");		 
		String cargaSesion 			= "0";
		
		String codigoPersonal		= "";
		String nombreMaestro 		= ""; 
		HttpSession sesion			= request.getSession();
		if (sesion!=null){
			codigoPersonal			= (String) sesion.getAttribute("codigoPersonal");
			nombreMaestro			= maestrosDao.getNombreMaestro(codigoPersonal, "NOMBRE");			
			cargaSesion 			= (String) sesion.getAttribute("cargaId");			
			if (cargaId.equals("0")) cargaId = cargaSesion; 
		}	
		String semanas 				= cargaDao.getSemanas(cargaId);
		HcaMaestro hcaMaestro 		= hcaMaestroDao.mapeaRegId(codigoPersonal);
		Acceso acceso 				= accesoDao.mapeaRegId(codigoPersonal); 
		
		List<CatPeriodo> lisPeriodos 	= catPeriodoDao.getListAll(" ORDER BY PERIODO_ID DESC");		
		List<Carga> lisCargas 			= cargaDao.getListMaestroPeriodo(codigoPersonal, periodoId);
		boolean existeCarga = false;
		for (Carga carga : lisCargas) {
			if (carga.getCargaId().equals(cargaId)) {
				existeCarga = true;
				break;
			}
		}
		if (!existeCarga && lisCargas.size()>=1) cargaId = lisCargas.get(0).getCargaId();
		
		List<CargaAcademica> lisCursos 					= cargaAcademicaDao.lisPorMaestro(codigoPersonal, "ORDER BY CARGA_ID, NOMBRE_CURSO");
		List<HcaRango> lisRangos 						= hcaRangoDao.lisTodos(" ORDER BY NIVEL_ID");
		List<HcaMaestroActividad> lisActividades 		= hcaMaestroActividadDao.lisPorMaestro(codigoPersonal," ORDER BY CARGA_ID, HCA_ACTORDEN(ACTIVIDAD_ID)");
		
		HashMap<String,CatModalidad> mapaModalidades 	= catModalidadDao.getMapAll(""); 
		HashMap<String,CatCarrera> mapaCarreras	 		= catCarreraDao.getMapAll("");
		HashMap<String,MapaCurso> mapaCursos 			= mapaCursoDao.mapaCursosPorMaestro(codigoPersonal);
		HashMap<String,HcaActividad> mapaActividades	= hcaActividadDao.mapaActividades();
		HashMap<String,HcaTipo> mapaTipos 				= hcaTipoDao.mapaTipos();
		
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("nombreMaestro", nombreMaestro);
		modelo.addAttribute("hcaMaestro", hcaMaestro);
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("semanas", semanas);
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisCursos", lisCursos);
		modelo.addAttribute("lisRangos", lisRangos);
		modelo.addAttribute("lisActividades", lisActividades);
		
		modelo.addAttribute("mapaModalidades", mapaModalidades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaCursos", mapaCursos);
		modelo.addAttribute("mapaActividades", mapaActividades);
		modelo.addAttribute("mapaTipos", mapaTipos);
		
		return "portales/maestro/carga_docente";
	}
	
	@RequestMapping("/portales/maestro/comentarios")
	public String portalesMaestroComentarios(HttpServletRequest request, Model modelo){
		
		String codigoPersonal		= "";
		String nombreMaestro 		= ""; 
		HttpSession sesion			= request.getSession();
		if (sesion!=null){
			codigoPersonal			= (String) sesion.getAttribute("codigoPersonal");
			nombreMaestro			= maestrosDao.getNombreMaestro(codigoPersonal, "NOMBRE"); 
		}	
		String cursoCargaId			= request.getParameter("cursoCargaId")==null?"0":request.getParameter("cursoCargaId");
		
		List<EdoAlumnoPreg> lisPreguntas 		= edoAlumnoPregDao.getListComentarios(cursoCargaId,"D", "ORDER BY 1");
		List<EdoAlumnoResp> lisRespuestas 		= edoAlumnoRespDao.lisRespuestas(cursoCargaId,"ORDER BY 1");
		
		modelo.addAttribute("nombreMaestro", nombreMaestro);
		modelo.addAttribute("lisPreguntas", lisPreguntas);
		modelo.addAttribute("lisRespuestas", lisRespuestas);
		
		return "portales/maestro/comentarios";
	}
	
	@RequestMapping("/portales/maestro/competencia")
	public String portalesMaestroCompetencia(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPortalesMaestro|portalesMaestroCompetencia");
		return "portales/maestro/competencia";
	}
	
	@RequestMapping("/portales/maestro/correccion")
	public String portalesMaestroCorrecion(HttpServletRequest request, Model modelo){
		Fecha fecha = new aca.util.Fecha();
		String cursoCargaId		= request.getParameter("CursoCargaId");
		String cursoId			= cargaGrupoCursoDao.cursoIdOrigen(cursoCargaId);
		String materia 			= mapaCursoDao.getMateria(cursoId);
		String maestro 			= cargaGrupoDao.getMaestro(cursoCargaId); 
		String carreraId		= cargaGrupoDao.getCarreraId(cursoCargaId);	
		
		String strFecha			= aca.util.Fecha.getHoy();
		String yearName			= aca.util.NumberToLetter.convertirYear(Integer.parseInt(fecha.getYear(strFecha))).trim();				
		
		String accion 		 	= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		int Accion				= Integer.parseInt(accion);
	    String mensaje    	= "";
		
	    List<KrdxCursoAct> lisAlumnos	= krdxCursoActDao.getListCurso(cursoCargaId, "ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");
		
	    KrdxCursoCal krdxCursoCal = new KrdxCursoCal();
	    
	    String nombreFacultad = catFacultadDao.getNombreFacultad(catCarreraDao.getFacultadId(carreraId));
	    String nombreCarrera  = catCarreraDao.getNombreCarrera(carreraId);
	    
	    HashMap<String, KrdxCursoCal> mapaCursoCargaCarga = krdxCursoCalDao.mapaCursoCargaCarga(cursoCargaId, cursoId);
	    
	    int ciclo = mapaCursoDao.getCiclo(cursoId);
	    String nombreModalidad = catModalidadDao.getNombreModalidad(cargaGrupoDao.getModalidad(cursoCargaId));
	    
	    HashMap<String, String> mapAlumnosMateria = alumPersonalDao.mapAlumnosMateria(cursoCargaId);
	    
	    HashMap<String,String> mapTipoCal = catTipoCalDao.mapTipoCal();
	    
	    //operaciones a realizar
		//fin del case 2
		if (Accion == 2) {//Grabar
			for (int i = 0; i < lisAlumnos.size(); i++) {
				KrdxCursoAct alumno = lisAlumnos.get(i);

				String nota = request.getParameter("Nota" + alumno.getCodigoPersonal());

				if (request.getParameter("Codigo" + alumno.getCodigoPersonal()) != null) {
					nota = nota.trim();
					krdxCursoCal.setCursoCargaId(cursoCargaId);
					krdxCursoCal.setCodigoPersonal(alumno.getCodigoPersonal());
					krdxCursoCal.setCursoId(cursoId);

					if (request.getParameter("FechaValue" + alumno.getCodigoPersonal()) == null) {
						krdxCursoCal.setFecha(Fecha.getHoy());
					} else {
						krdxCursoCal.setFecha(request.getParameter("FechaValue" + alumno.getCodigoPersonal()));
					}
					krdxCursoCal.setFechaFinal(Fecha.getHoy());
					krdxCursoCal.setNota(request.getParameter("Nota" + alumno.getCodigoPersonal()));
					krdxCursoCal.setTipo("C");
					krdxCursoCal.setEstado("S");
					krdxCursoCal.setTipoNota(request.getParameter("tipoNota" + alumno.getCodigoPersonal()));
					krdxCursoCal.setTipoCalId(request.getParameter("tipoCalId" + alumno.getCodigoPersonal()));

					if (!krdxCursoCalDao.existeReg(alumno.getCodigoPersonal(), cursoCargaId, cursoId)) {
						if (!nota.equals("") && !nota.contains(".") && !nota.contains(",")) {
							if (krdxCursoCalDao.insertReg(krdxCursoCal)) {
								mensaje = "Data has been saved correctly";
							} else {
								mensaje = "Error saving: " + krdxCursoCal.getCursoCargaId();
							}
						} else {
							mensaje = "Number cannot be null or decimal";
						}
					} else {
						if (!nota.equals("")) {
							if (krdxCursoCalDao.updateReg(krdxCursoCal)) {
								mensaje = "Data has been updated correctly";
							} else {
								mensaje = "Error updating: " + krdxCursoCal.getCursoCargaId();
							}
						}
					}
				}
			}//fin del for
			mapaCursoCargaCarga = krdxCursoCalDao.mapaCursoCargaCarga(cursoCargaId, cursoId);
		}// fin del switch*/

		modelo.addAttribute("cursoCargaId", cursoCargaId);
		 modelo.addAttribute("materia", materia);
		 modelo.addAttribute("maestro", maestro);
		 modelo.addAttribute("cursoId", cursoId);
		 modelo.addAttribute("yearName", yearName);
		 modelo.addAttribute("nombreFacultad", nombreFacultad);
		 modelo.addAttribute("nombreCarrera", nombreCarrera);
		 modelo.addAttribute("mensaje", mensaje);
		 modelo.addAttribute("lisAlumnos", lisAlumnos);
		 modelo.addAttribute("fecha", fecha);
		 modelo.addAttribute("mapaCursoCargaCarga", mapaCursoCargaCarga);
		 modelo.addAttribute("ciclo", ciclo);
		 modelo.addAttribute("nombreModalidad", nombreModalidad);
		 modelo.addAttribute("mapTipoCal", mapTipoCal);
		 modelo.addAttribute("mapAlumnosMateria", mapAlumnosMateria);
		
		return "portales/maestro/correccion";
	}
	
	@RequestMapping("/portales/maestro/borrarNota")
	public String portalesMaestroBorrarNota(HttpServletRequest request){
		String cursoCargaId 		= request.getParameter("CursoCargaId")==null?"-":request.getParameter("CursoCargaId");
		String cursoId 				= request.getParameter("CursoId")==null?"-":request.getParameter("CursoId");
		String codigoAlumno			= request.getParameter("CodigoAlumno")==null?"-":request.getParameter("CodigoAlumno");
		String mensaje				= "-";
		if (krdxCursoCalDao.existeReg(codigoAlumno, cursoCargaId, cursoId)) {
			if (krdxCursoCalDao.deleteReg(codigoAlumno, cursoCargaId, cursoId)){
				mensaje = "Deleted grade";
			}else {
				mensaje = "Error deleting grade";
			}
		}
		return "redirect:/portales/maestro/correccion?CursoCargaId="+cursoCargaId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/portales/maestro/correccionPDF")
	public String portalesMaestroCorrecionPDF(HttpServletRequest request, Model modelo){
		String institucion 		= "";
		String cursoCargaId 	= request.getParameter("CursoCargaId");
		String cursoId			= request.getParameter("CursoId");
		String materia			= request.getParameter("Materia");
		String maestro			= request.getParameter("Maestro");
		
		HttpSession sesion		= request.getSession();
		if (sesion!=null){
			institucion 		= (String) sesion.getAttribute("institucion");
		}
		
		HashMap<String, String> cursos = krdxCursoActDao.mapaAlumnoCurso(cursoCargaId);
		
		aca.util.Fecha fecha = new aca.util.Fecha();

		String carreraOrigen	= cargaGrupoCursoDao.cursoIdOrigen(cursoCargaId);
		String carreraId		= mapaPlanDao.getCarreraId(carreraOrigen.substring(0,8));
		String strFecha			= aca.util.Fecha.getHoy();
		String yearName			= aca.util.NumberToLetter.convertirYear(Integer.parseInt(fecha.getYear(strFecha))).trim();

		List<KrdxCursoCal> lisDiferida	= krdxCursoCalDao.getListHoy(cursoCargaId,"C", " ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");
		
		HashMap<String,KrdxCursoAct> mapaNotas	 	= krdxCursoActDao.mapaNotas(cursoCargaId);
		HashMap<String, String> mapAlumnosMateria 	= alumPersonalDao.mapAlumnosMateria(cursoCargaId);

		String nombreFacultad 	= catFacultadDao.getNombreFacultad(catCarreraDao.getFacultadId(carreraId));
	    String nombreCarrera  	= catCarreraDao.getNombreCarrera(carreraId);
	    String nombreModalidad 	= catModalidadDao.getNombreModalidad(cargaGrupoDao.getModalidad(cursoCargaId));

	    int ciclo = 0;
	    
	    if(mapaCursoDao.existeReg(cursoId)) {
	    	ciclo = mapaCursoDao.getCiclo(cursoId);
	    }
	    
	    String getCiclo = cargaDao.getCiclo(cursoCargaId.substring(0,6));
		
		modelo.addAttribute("institucion", institucion);
		modelo.addAttribute("cursoCargaId", cursoCargaId);
		modelo.addAttribute("cursoId", cursoId);
		modelo.addAttribute("materia", materia);
		modelo.addAttribute("maestro", maestro);
		modelo.addAttribute("cursos", cursos);
		modelo.addAttribute("lisDiferida", lisDiferida);
		modelo.addAttribute("carreraId", carreraId);
		modelo.addAttribute("yearName", yearName);
		modelo.addAttribute("mapaNotas", mapaNotas);
		modelo.addAttribute("mapAlumnosMateria", mapAlumnosMateria);
		modelo.addAttribute("nombreFacultad",nombreFacultad);	
		modelo.addAttribute("nombreCarrera",nombreCarrera);	
		modelo.addAttribute("nombreModalidad",nombreModalidad);	
		modelo.addAttribute("ciclo",ciclo);	
		modelo.addAttribute("getCiclo",getCiclo);	
		modelo.addAttribute("fecha",fecha);	
		
		return "portales/maestro/correccionPDF";
	}
	
	@RequestMapping("/portales/maestro/criterio")
	public String portalesMaestroCriterio(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPortalesMaestro|portalesMaestroCriterio");
		return "portales/maestro/criterio";
	}
	
	@RequestMapping("/portales/maestro/cursos")
	public String portalesMaestroCursos(HttpServletRequest request, Model modelo){
		
		String codigoPersonal	= "0";
		String maestroNombre	= "-";
		String equipoPor		= "0";		 
		String periodoSesion	= "0";
		String cargaSesion		= "0";
		String yearActual		= aca.util.Fecha.getHoyReversa().split("/")[0];
		boolean existeRango		= false; 
		HttpSession sesion		= request.getSession();
		if (sesion!=null){
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
			maestroNombre 		= maestrosDao.getNombreMaestro(codigoPersonal,"NOMBRE");
			equipoPor		 	= maestrosDao.getEquipo(codigoPersonal);
			periodoSesion		= (String) sesion.getAttribute("periodo");
			cargaSesion			= (String) sesion.getAttribute("cargaId");
			if (maestroRangoDao.existeReg(codigoPersonal, yearActual)) {
				existeRango = true;
			}	
		}
		
		String periodoId 		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String cargaId			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		
		List<CatPeriodo> lisPeriodos	= catPeriodoDao.getListPeriodosMaestro(codigoPersonal, "ORDER BY PERIODO_ID DESC");
		
		boolean existePeriodoSesion = false;
		for(CatPeriodo per :lisPeriodos) {
			if (per.getPeriodoId().equals(periodoSesion)) {
				existePeriodoSesion = true;
				break;
			}
		}
		if (periodoId.equals("0") && existePeriodoSesion) {
			periodoId = periodoSesion;
		}else if (periodoId.equals("0") && lisPeriodos.size()>=1){
			periodoId = lisPeriodos.get(0).getPeriodoId();
		}else if (!periodoId.equals("0") && sesion!=null){
			sesion.setAttribute("periodo", periodoId);
		}	 
		
		List<Carga> lisCargas 						= cargaDao.getListMaestroPeriodo(codigoPersonal, periodoId);
		
		boolean existeCargaSesion = false;
		for (Carga car : lisCargas) {
			if (car.getCargaId().equals(cargaSesion)) {
				existeCargaSesion = true;
				break;
			}
		}
		
		if (cargaId.equals("0") && existeCargaSesion) {
			cargaId = cargaSesion;
		}else if (cargaId.equals("0") && lisCargas.size()>= 1) {
			cargaId = lisCargas.get(0).getCargaId();
		}else if (!cargaId.equals("0") && sesion!=null) {
			sesion.setAttribute("cargaId", cargaId);
		}
		
		int horarios			= musiHorarioDao.tieneHorarios(codigoPersonal, cargaId);
		List<CargaAcademica> lisCursos 					= cargaAcademicaDao.getListaMaestro(cargaId, codigoPersonal, " ORDER BY NOMBRE_CURSO");
		
		HashMap<String,String> mapaPron 				= cargaPronDao.mapaTodos();
		HashMap<String,CatCarrera> mapaCarreras 		= catCarreraDao.getMapAll("");
		HashMap<String,CatModalidad> mapaModalidades 	= catModalidadDao.getMapAll("");
		HashMap<String,String> mapaEdo				 	= edoAlumnoRespDao.mapaEdoDeMateria(cargaId);
		HashMap<String,String> mapaHoras			 	= cargaGrupoHoraDao.mapaHorasPorMateria(cargaId);
		HashMap<String,String> mapaPromedioMaterias	 	= edoAlumnoRespDao.mapaPromedioMaterias(cargaId, codigoPersonal);
		HashMap<String,String> mapaContestaron		 	= edoAlumnoRespDao.mapaContestaron(cargaId);
		HashMap<String,String> mapaTotAlumnos		 	= krdxCursoActDao.mapaTotalAlumnos(cargaId, "'I','1','2','3','5','6'");
		HashMap<String,String> mapaPendientes		 	= krdxCursoActDao.mapaTotalAlumnos(cargaId, "'I','5','6'");
		HashMap<String,String> mapaAvance			 	= cargaGrupoEvaluacionDao.mapaAvanceMaestro(codigoPersonal, cargaId);
		HashMap<String,String> mapaEvalPendientes	 	= cargaGrupoEvaluacionDao.mapaEvalPendientes(codigoPersonal, cargaId);
		HashMap<String,String> mapaActPendientes	 	= cargaGrupoEvaluacionDao.mapaActPendientes(codigoPersonal, cargaId);
		HashMap<String,String> mapaCorrecciones		 	= krdxCursoCalDao.mapaCambiosNotas(codigoPersonal, "C");
		HashMap<String,String> mapaGraduandos		 	= krdxCursoActDao.mapaGruposConGraduandos(codigoPersonal);
		HashMap<String,String> mapaPuntos		 		= cargaGrupoEvaluacionDao.mapaSumaPorMateria(codigoPersonal);		
		
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("horarios", horarios);
		modelo.addAttribute("maestroNombre", maestroNombre);
		modelo.addAttribute("equipoPor", equipoPor);
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisCursos", lisCursos);
		modelo.addAttribute("existeRango", existeRango);
		
		modelo.addAttribute("mapaPron", mapaPron);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaModalidades", mapaModalidades);
		modelo.addAttribute("mapaEdo", mapaEdo);
		modelo.addAttribute("mapaHoras", mapaHoras);
		modelo.addAttribute("mapaPromedioMaterias", mapaPromedioMaterias);
		modelo.addAttribute("mapaContestaron", mapaContestaron);
		modelo.addAttribute("mapaTotAlumnos", mapaTotAlumnos);
		modelo.addAttribute("mapaPendientes", mapaPendientes);		
		modelo.addAttribute("mapaAvance", mapaAvance);
		modelo.addAttribute("mapaEvalPendientes", mapaEvalPendientes);
		modelo.addAttribute("mapaActPendientes", mapaActPendientes);
		modelo.addAttribute("mapaCorrecciones", mapaCorrecciones);
		modelo.addAttribute("mapaGraduandos", mapaGraduandos);
		modelo.addAttribute("mapaPuntos", mapaPuntos);
		
		return "portales/maestro/cursos";
	}
	
	@RequestMapping("/portales/maestro/materias")
	public String portalesMaestroMaterias(HttpServletRequest request, Model modelo){
		
		String codigoPersonal	= "0";
		String maestroNombre	= "-";		
		HttpSession sesion		= request.getSession();
		if (sesion!=null){
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
			maestroNombre 		= maestrosDao.getNombreMaestro(codigoPersonal,"NOMBRE");		
		}	
		
		List<CargaAcademica> lisCursos 					= cargaAcademicaDao.lisPorMaestro(codigoPersonal, " ORDER BY NOMBRE_CURSO");
		
		HashMap<String,CatCarrera> mapaCarreras 		= catCarreraDao.getMapAll("");
		HashMap<String,String> mapaGraduandos		 	= krdxCursoActDao.mapaGruposConGraduandos(codigoPersonal);
		HashMap<String,String> mapaAlumnos			 	= krdxCursoActDao.mapaAlumnosPorCurso(codigoPersonal);	
		HashMap<String,String> mapaAlumnosPorTipo	 	= krdxCursoActDao.mapaAlumnosPorCursoyTipo(codigoPersonal);
		
		HashMap<String,CatModalidad> mapaModalidades 	= catModalidadDao.getMapAll("");	
		
		modelo.addAttribute("maestroNombre", maestroNombre);		
		modelo.addAttribute("lisCursos", lisCursos);
		
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaModalidades", mapaModalidades);
		modelo.addAttribute("mapaGraduandos", mapaGraduandos);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("mapaAlumnosPorTipo", mapaAlumnosPorTipo);
		
		return "portales/maestro/materias";
	}
	
	@RequestMapping("/portales/maestro/avance")
	public String datosProfesorCursosEvaluacion(HttpServletRequest request, Model modelo){
		
		String cursoCargaId 	= request.getParameter("CursoCargaId") == null ? "0" : request.getParameter("CursoCargaId");
		String cursoId			= cargaGrupoCursoDao.cursoIdOrigen(cursoCargaId);
		String materiaNombre 	= mapaCursoDao.getNombreCurso(cursoId);
		String codigoPersonal 	= "0";
		String maestroNombre	= "-";
		int numAlumnos 			= krdxCursoActDao.numAlumnosMateria(cursoCargaId, "'I','1','2','3','4','5','6'");
		String avanceMateria	= cargaGrupoEvaluacionDao.getAvanceEvaluacion(cursoCargaId);		
		
		HttpSession sesion		= request.getSession();
		if (sesion!=null){			
			codigoPersonal		= cargaGrupoDao.getCodigoPersonal(cursoCargaId);
			maestroNombre 		= maestrosDao.getNombreMaestro(codigoPersonal,"NOMBRE");
		}
		
		List<CargaGrupoEvaluacion> lisEvaluaciones 		= cargaGrupoEvaluacionDao.getLista(cursoCargaId," ORDER BY ENOC.CARGA_GRUPO_EVALUACION.FECHA");
		List<CargaGrupoActividad> lisActividades 		= cargaGrupoActividadDao.getListCurso(cursoCargaId," ORDER BY EVALUACION_ID, ENOC.CARGA_GRUPO_ACTIVIDAD.FECHA");
		
		HashMap<String, String> mapaNotasEvaluaciones 			= cargaGrupoEvaluacionDao.mapaNotasEnEvaluaciones(cursoCargaId);
		HashMap<String, String> mapaNotasActividadesPorEval		= cargaGrupoEvaluacionDao.mapaNotasActividadesPorEvaluacion(cursoCargaId);
		HashMap<String, String> mapaAvanceEvaluaciones 			= cargaGrupoEvaluacionDao.mapaAvancePorEvaluacion(cursoCargaId);
		HashMap<String, String> mapaNotasActividades 			= cargaGrupoEvaluacionDao.mapaNotasEnActividades(cursoCargaId);
		HashMap<String, String> mapaAvanceActividades 			= cargaGrupoEvaluacionDao.mapaAvancePorActividades(cursoCargaId);
		HashMap<String, String> mapaActividadesPorEvaluacion	= cargaGrupoActividadDao.mapaActividadesPorEvaluacion(cursoCargaId);
		
		modelo.addAttribute("numAlumnos", numAlumnos);
		modelo.addAttribute("avanceMateria", avanceMateria);
		modelo.addAttribute("materiaNombre", materiaNombre);
		modelo.addAttribute("maestroNombre", maestroNombre);
		modelo.addAttribute("lisEvaluaciones", lisEvaluaciones);
		modelo.addAttribute("lisActividades", lisActividades);
		modelo.addAttribute("mapaNotasEvaluaciones", mapaNotasEvaluaciones);
		modelo.addAttribute("mapaNotasActividadesPorEval", mapaNotasActividadesPorEval);
		modelo.addAttribute("mapaAvanceEvaluaciones", mapaAvanceEvaluaciones);
		modelo.addAttribute("mapaNotasActividades", mapaNotasActividades);
		modelo.addAttribute("mapaAvanceActividades", mapaAvanceActividades);
		modelo.addAttribute("mapaActividadesPorEvaluacion", mapaActividadesPorEvaluacion);
			
		return "portales/maestro/avance";
	}
	
	@RequestMapping("/portales/maestro/horariocimum")
	public String portalesMaestroHorariocimum(HttpServletRequest request, Model modelo){
		
		String codigoPersonal	= "0";
		String maestroNombre	= "-";
		HttpSession sesion		= request.getSession();
		if (sesion!=null){
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
			maestroNombre 		= maestrosDao.getNombreMaestro(codigoPersonal,"NOMBRE");
		}
		
		String cargaId			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		
		List<MusiHorario> lisHorarioMaestro		= musiHorarioDao.getListHorarioMaestro(codigoPersonal, cargaId, "ORDER BY DIA");
		HashMap<String, String> mapaOcupados 	= musiHorarioAlumnoDao.mapaOcupados(cargaId);
		HashMap<String, String> mapaDisponibles = musiHorarioDao.mapaDisponibles(cargaId, codigoPersonal);
		
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("codigoPersonal", codigoPersonal);
		modelo.addAttribute("maestroNombre", maestroNombre);
		modelo.addAttribute("lisHorarioMaestro", lisHorarioMaestro);
		modelo.addAttribute("mapaOcupados", mapaOcupados);
		modelo.addAttribute("mapaDisponibles", mapaDisponibles);
		
		return "portales/maestro/horariocimum";
	}
	
	@RequestMapping("/portales/maestro/datosPlan")
	public String portalesMaestroDatosPlan(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPortalesMaestro|portalesMaestroDatosPlan");
		return "portales/maestro/datosPlan";
	}
	
	@RequestMapping("/portales/maestro/diferida")
	public String portalesMaestroDiferida(HttpServletRequest request, Model modelo){
		
		Fecha fecha 			= new Fecha();
		String codigoPersonal	= "-";
		HttpSession sesion		= request.getSession();
		if (sesion!=null){
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
		}
		
		String cursoCargaId		= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		
		String cursoId			= cargaGrupoCursoDao.cursoIdOrigen(cursoCargaId);
		String materia			= mapaCursoDao.getNombreCurso(cursoId);
		String maestro			= cargaGrupoDao.getMaestro(cursoCargaId);
		
		String strFecha			= aca.util.Fecha.getHoy();
		String yearName			= aca.util.NumberToLetter.convertirYear(Integer.parseInt(fecha.getYear(strFecha))).trim();
		String carreraOrigen	= cargaGrupoCursoDao.cursoIdOrigen(cursoCargaId);
		String carreraId		= mapaPlanDao.getCarreraId(carreraOrigen.substring(0,8));
		
		boolean esAdmin 		= Boolean.parseBoolean(sesion.getAttribute("admin")+""); 
		
		List<KrdxCursoAct> lisDiferidas					= krdxCursoActDao.getListDiferida(cursoCargaId, "ORDER BY CODIGO_PERSONAL");
		HashMap<String, KrdxCursoCal> mapaDiferidas 	= krdxCursoCalDao.mapaDiferidasPorMateria(cursoCargaId);
		HashMap<String, AlumPersonal> mapaAlumnos 		= alumPersonalDao.mapAlumnosEnMateria(cursoCargaId);
		HashMap<String, String> mapaTipos 				= catTipoCalDao.mapTipoCalCorto();
		
		String institucion 		= parametrosDao.getInstitucion("1");
		String nombreFacultad 	= catFacultadDao.getNombreFacultad(catCarreraDao.getFacultadId(carreraId));
		String nombreCarrera	= catCarreraDao.getNombreCarrera(carreraId);
		int ciclo				= mapaCursoDao.getCiclo(cursoId);
		String cargaCiclo		= cargaDao.getCiclo(cursoCargaId.substring(0,6));		
		
		modelo.addAttribute("codigoPersonal", codigoPersonal);
		modelo.addAttribute("cursoId", cursoId);
		modelo.addAttribute("materia", materia);
		modelo.addAttribute("maestro", maestro);
		modelo.addAttribute("yearName", yearName);
		modelo.addAttribute("carreraId", carreraId);
		modelo.addAttribute("esAdmin", esAdmin);		
		modelo.addAttribute("institucion", institucion);
		modelo.addAttribute("nombreFacultad", nombreFacultad);
		modelo.addAttribute("nombreCarrera", nombreCarrera);
		modelo.addAttribute("ciclo", ciclo);
		modelo.addAttribute("cargaCiclo", cargaCiclo);		
		modelo.addAttribute("lisDiferidas", lisDiferidas);
		modelo.addAttribute("mapaDiferidas", mapaDiferidas);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("mapaTipos", mapaTipos);
		
		return "portales/maestro/diferida";
	}
	
	@RequestMapping("/portales/maestro/grabarDiferida")
	public String portalesMaestroGrabarDiferida(HttpServletRequest request, Model modelo){

		String cursoCargaId		= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");				
		String resultado 		= "-";
		
		List<KrdxCursoAct> lisDiferidas					= krdxCursoActDao.getListDiferida(cursoCargaId, "ORDER BY CODIGO_PERSONAL");		
		for (int i=0; i<lisDiferidas.size();i++){
    		KrdxCursoAct diferida = lisDiferidas.get(i);
	    	String descripcion = request.getParameter("FFinal"+i);
	    	if (descripcion!= null){
	    		String cursoId = krdxCursoActDao.mapeaRegId(diferida.getCodigoPersonal(), cursoCargaId).getCursoId();
	    		KrdxCursoCal krdxCursoCal = new KrdxCursoCal();
	    		krdxCursoCal.setCursoCargaId(cursoCargaId);
	    		krdxCursoCal.setCodigoPersonal(diferida.getCodigoPersonal());
	    		krdxCursoCal.setCursoId(cursoId);
	    		krdxCursoCal.setEstado("S");
	    		krdxCursoCal.setFecha(aca.util.Fecha.getHoy());
	    		krdxCursoCal.setFechaFinal(request.getParameter("FFinal"+i));
	    		krdxCursoCal.setNota(request.getParameter("FCal"+i));
	    		krdxCursoCal.setTipo("D");
	    		krdxCursoCal.setTipoCalId("6");
	    		krdxCursoCal.setTipoNota("O");
	    	
		    	if (!krdxCursoCalDao.existeReg(diferida.getCodigoPersonal(), cursoCargaId)){
					if (krdxCursoCalDao.insertReg(krdxCursoCal)){
						resultado = "Data has been saved correctly";
					}else{
						resultado = "Error saving: "+cursoCargaId;
					}
				}else{	
					if (krdxCursoCalDao.updateReg(krdxCursoCal)){ 
						resultado = "Data has been updated correctly";
					}else{
						resultado = "Error updating: "+cursoCargaId;
					}
				}
	    	}		    	
      	}//fin del for		
		
		return "redirect:/portales/maestro/diferida?CursoCargaId="+cursoCargaId+"&Resultado="+resultado;
	}
	
	@RequestMapping("/portales/maestro/borrarDiferida")
	public String portalesMaestroBorrarDiferida(HttpServletRequest request, Model modelo){

		String cursoCargaId		= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");				
		String resultado 		= "-";		
		if(krdxCursoCalDao.tieneDiferidas(cursoCargaId)){
			if (krdxCursoCalDao.deleteDiferidasMateria(cursoCargaId)){
				resultado = "Delayed notes were deleted";
			}else{
				resultado = "Error while trying to cancel delayed notes";
			}
		}		
		return "redirect:/portales/maestro/diferida?CursoCargaId="+cursoCargaId+"&Resultado="+resultado;
	}
	
	@RequestMapping("/portales/maestro/diferidaPDF")
	public String portalesMaestroDiferidaPDF(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPortalesMaestro|portalesMaestroDiferidaPDF");
		return "portales/maestro/diferidaPDF";
	}
	
	@RequestMapping("/portales/maestro/ejes")
	public String portalesMaestroEjes(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPortalesMaestro|portalesMaestroEjes");
		return "portales/maestro/ejes";
	}
	
	@RequestMapping("/portales/maestro/estadistica")
	public String portalesMaestroEstadistica(HttpServletRequest request, Model modelo){
		
		String codigoEmpleado	= "0";
		String nombreEmpleado 	= "-";
		HttpSession sesion		= request.getSession();
		if (sesion!=null){
			codigoEmpleado		= (String) sesion.getAttribute("codigoEmpleado");
			nombreEmpleado 		= maestrosDao.getNombreMaestro(codigoEmpleado, "NOMBRE"); 
		}
		
		String cargaId			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String cargaNombre		= cargaDao.getNombreCarga(cargaId);
		String numMaterias		= cargaGrupoDao.getNumCursosMaestro( codigoEmpleado, cargaId);
		int numAlumnos 			= krdxCursoActDao.numEmpMatTipo(codigoEmpleado, cargaId, "'I','1','2','3','4','5','6'");
		int numInscritos		= krdxCursoActDao.numEmpMatTipo(codigoEmpleado, cargaId, "'I'");
		int numBajas			= krdxCursoActDao.numEmpMatTipo(codigoEmpleado, cargaId, "'3'");
		int numAcreditados		= krdxCursoActDao.numEmpMatTipo(codigoEmpleado, cargaId, "'1'");
		int numReprobados		= krdxCursoActDao.numEmpMatTipo(codigoEmpleado, cargaId, "'2','4'");
		int numPendientes		= krdxCursoActDao.numEmpMatTipo(codigoEmpleado, cargaId, "'5','6'");
		
		HashMap<String,String> mapaEstados 	= cargaGrupoDao.getTotEdoMaestro(cargaId, codigoEmpleado);
		
		modelo.addAttribute("nombreEmpleado", nombreEmpleado);
		modelo.addAttribute("cargaNombre", cargaNombre);
		modelo.addAttribute("numMaterias", numMaterias);
		modelo.addAttribute("numAlumnos", numAlumnos);
		modelo.addAttribute("numInscritos", numInscritos);
		modelo.addAttribute("numBajas", numBajas);
		modelo.addAttribute("numAcreditados", numAcreditados);
		modelo.addAttribute("numReprobados", numReprobados);
		modelo.addAttribute("numPendientes", numPendientes);
		
		modelo.addAttribute("mapaEstados", mapaEstados);
		
		return "portales/maestro/estadistica";
	}
	
	@RequestMapping("/portales/maestro/evaact")
	public String portalesMaestroEvaact(HttpServletRequest request, Model modelo){
		String codigoPersonal	= "0";
		
		HttpSession sesion		= request.getSession();
		if (sesion!=null){
			codigoPersonal	= (String) sesion.getAttribute("codigoPersonal");
		}

		String cursoCargaId		= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		String maestro 			= request.getParameter("Maestro")==null?"-":request.getParameter("Maestro");
		String materia 			= request.getParameter("Materia")==null?"0":request.getParameter("Materia");	
		String evaluacionId 	= request.getParameter("EvaluacionId")==null?"0":request.getParameter("EvaluacionId");
		String actividadId		= request.getParameter("ActividadId")==null?"0":request.getParameter("ActividadId");
		String sAccion 			= request.getParameter("Accion")==null?"1":request.getParameter("Accion");
		int nAccion				= Integer.parseInt(sAccion);	
		int numExtras 			= request.getParameter("numExtras")==null?0:Integer.parseInt(request.getParameter("numExtras"));	
		String estado 			= "";
		
		CargaGrupo cargaGrupo = new CargaGrupo();
		
		if(cargaGrupoDao.existeReg(cursoCargaId)){
			cargaGrupo = cargaGrupoDao.mapeaRegId(cursoCargaId);
		}
		
		estado = cargaGrupo.getEstado();	

		String resultado		= "";
		int j=0, i=0;
		
		// ArrayList que almacena la metodología de evaluacion de la Materia	
		List<CargaGrupoActividad> lisActividad  	= cargaGrupoActividadDao.getListCargaEvaluacion(cursoCargaId, evaluacionId);		
		List<KrdxCursoAct> lisAlumnos 				= krdxCursoActDao.getListCurso(cursoCargaId, "ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");
		HashMap<String,KrdxAlumnoActiv> mapaNotas	= krdxAlumnoActivDao.mapActividadesMateria(cursoCargaId);
		
		KrdxAlumnoActiv alumActividad = new KrdxAlumnoActiv();
		
		alumActividad.setCursoCargaId(cursoCargaId);
		alumActividad.setActividadId(actividadId);
		
		// Operaciones a realizar en la pantalla
		LogOperacion log = new LogOperacion();
		
		switch (nAccion){
			case 1: { // Nuevo
				//resultado = "Formulario para ingresar las evaluaciones";
				break;
			}		
			case 2: { // Grabar Ordinario
				nAccion	= 1;
				
				for (KrdxCursoAct curso : lisAlumnos){					
					alumActividad.setCodigoPersonal(curso.getCodigoPersonal());				
					alumActividad.setActividadE42(request.getParameter(curso.getCodigoPersonal()+"E42")==null?"0":request.getParameter(curso.getCodigoPersonal()+"E42"));
					
					String nota = request.getParameter(curso.getCodigoPersonal()+"Nota");					
					if(nota != null && !nota.equals("")) 
						alumActividad.setNota(nota);
					else
						alumActividad.setNota("0");				

	/********* LOG ****/ 
					String datos = "CursoCargaId: "+cursoCargaId+", CodigoPersonal: "+curso.getCodigoPersonal() + ", ActividadId: "+actividadId+", Nota: "+nota;
					log.setDatos(datos);
					log.setIp(request.getRemoteAddr());
					log.setUsuario((String) sesion.getAttribute("codigoPersonal"));
					log.setTabla("krdx_alumno_activ");
					
					if (krdxAlumnoActivDao.existeReg(curso.getCodigoPersonal(), actividadId)){
						if (krdxAlumnoActivDao.updateReg(alumActividad)){
							resultado = "Updated: "+alumActividad.getCodigoPersonal();
							/******* LOG ******/
							log.setOperacion("update");
							logOperacionDao.insertReg(log);
						}else{
							resultado = "Error updating: "+alumActividad.getCodigoPersonal();
						}
											
					}else{
						if (krdxAlumnoActivDao.insertReg(alumActividad)){
							resultado = "Saved: "+alumActividad.getCodigoPersonal();
							/******* LOG ******/
							log.setOperacion("insert");
							logOperacionDao.insertReg(log);						
						}else{
							resultado = "Error saving: "+alumActividad.getCodigoPersonal();
						}
					}							
				}
				actividadId="0";			
				//System.out.println("kaau.setPromEstrID("+conEnoc+", "+cursoCargaId+")");
				if(!krdxAlumnoActivDao.setPromEstrID(cursoCargaId, evaluacionId))
					if(!krdxAlumnoActivDao.setPromEstrID(cursoCargaId, evaluacionId))
						if(!krdxAlumnoActivDao.setPromEstrID(cursoCargaId, evaluacionId))
							resultado = "Saved. Error UPDATING Evaluations!";
				
				break;
			}		
			case 4: { // Borrar			
				for (KrdxCursoAct curso : lisAlumnos){
					
					/********* LOG ****/
					String nota = request.getParameter(curso.getCodigoPersonal()+"Nota");					
					if(nota != null && !nota.equals("")) 
						alumActividad.setNota(nota);
					else
						alumActividad.setNota("0");
					
					String datos = "CursoCargaId: "+cursoCargaId+", CodigoPersonal: "+curso.getCodigoPersonal() + ", ActividadId: "+actividadId+", Nota: "+nota;		
					
					log.setDatos(datos);
					log.setIp(request.getRemoteAddr());
					log.setUsuario((String) sesion.getAttribute("codigoPersonal"));
					log.setTabla("krdx_alumno_activ");
					
					alumActividad.setCodigoPersonal(curso.getCodigoPersonal());
					alumActividad.setNota(request.getParameter("Nota"+i));
					if (krdxAlumnoActivDao.existeReg(curso.getCodigoPersonal(), actividadId)){
						if (krdxAlumnoActivDao.deleteReg(curso.getCodigoPersonal(),actividadId)){
							resultado = "Deleted: "+alumActividad.getCodigoPersonal();
							/******* LOG ******/
							log.setOperacion("delete");
							logOperacionDao.insertReg(log);
						}else{
							resultado = "Error deleting: "+alumActividad.getCodigoPersonal();
					}					
					}else{
						resultado = "Not found: "+alumActividad.getCodigoPersonal(); 
					}
				}
				lisActividad  = cargaGrupoActividadDao.getListCargaEvaluacion(cursoCargaId, evaluacionId);
				
				break;			
			}		
		}
		
		MapaCurso mapaCurso = new MapaCurso();
		
		if(mapaCursoDao.existeReg(cargaGrupoCursoDao.cursoIdOrigen(cursoCargaId))) {
			mapaCurso = mapaCursoDao.mapeaRegId(cargaGrupoCursoDao.cursoIdOrigen(cursoCargaId));	
		}
		
		HashMap<String,AlumnoEficiencia> mapaEficiencia = alumnoEficienciaDao.mapaMateria(cursoCargaId);
		
		int nE 	= cargaGrupoEvaluacionDao.getNumEstrategias(cursoCargaId);
		int nEE = cargaGrupoEvaluacionDao.getNumEstrategiasEvaluadas(cursoCargaId);
		int nAB = cargaGrupoEvaluacionDao.getNumAlumnosBaja(cursoCargaId);	
		
		HashMap<String, AlumPersonal> mapAlumnosEnMateria = alumPersonalDao.mapAlumnosEnMateria(cursoCargaId);
		
		lisActividad  	= cargaGrupoActividadDao.getListCargaEvaluacion(cursoCargaId, evaluacionId);		
		lisAlumnos 		= krdxCursoActDao.getListCurso(cursoCargaId, "ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");
		mapaNotas		= krdxAlumnoActivDao.mapActividadesMateria(cursoCargaId);
		
		modelo.addAttribute("codigoPersonal", codigoPersonal);
		modelo.addAttribute("cursoCargaId", cursoCargaId);
		modelo.addAttribute("resultado", resultado);
		modelo.addAttribute("estado", estado);
		modelo.addAttribute("maestro", maestro);
		modelo.addAttribute("materia", materia);
		modelo.addAttribute("evaluacionId", evaluacionId);
		modelo.addAttribute("actividadId", actividadId);
		modelo.addAttribute("alumActividad", alumActividad);
		modelo.addAttribute("numExtras", numExtras);
		modelo.addAttribute("nE", nE);
		modelo.addAttribute("nEE", nEE);
		modelo.addAttribute("nAB", nAB);
		modelo.addAttribute("mapaEficiencia", mapaEficiencia);
		modelo.addAttribute("mapaCurso", mapaCurso);
		modelo.addAttribute("lisActividad", lisActividad);
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("mapAlumnosEnMateria", mapAlumnosEnMateria);
		modelo.addAttribute("mapaNotas", mapaNotas);
		
		return "portales/maestro/evaact";
	}
	
	@RequestMapping("/portales/maestro/evaluacion")
	public String portalesMaestroEvaluacion(HttpServletRequest request, Model modelo){
		
		String cursoCargaId		= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		String evaluacionId		= request.getParameter("EvaluacionId")==null?"0":request.getParameter("EvaluacionId");	
		String cursoId			= cargaGrupoCursoDao.cursoIdOrigen(cursoCargaId);
		String materiaNombre 	= mapaCursoDao.getNombreCurso(cursoId);
		String codigoPersonal 	= "0";
		
		HttpSession sesion		= request.getSession();
		if (sesion!=null){
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
		}
		
		CargaGrupoEvaluacion cargaGrupoEvaluacion = new CargaGrupoEvaluacion();
		if (cargaGrupoEvaluacionDao.existeReg(cursoCargaId, evaluacionId)) {
			cargaGrupoEvaluacion = cargaGrupoEvaluacionDao.mapeaRegId(cursoCargaId, evaluacionId);
		}else {
			cargaGrupoEvaluacion.setEvaluacionId(cargaGrupoEvaluacionDao.maximoReg(cursoCargaId));
		}
		
		CargaGrupo cargaGrupo 	= cargaGrupoDao.mapeaRegId(cursoCargaId);
		String maestroNombre 	= maestrosDao.getNombreMaestro(codigoPersonal, "NOMBRE");
		
		List<CatEstrategia> lisEstrategias		= catEstrategiaDao.getListAll("ORDER BY 2");
		
		modelo.addAttribute("cursoId", cursoId);		
		modelo.addAttribute("materiaNombre", materiaNombre);
		modelo.addAttribute("maestroNombre", maestroNombre);
		modelo.addAttribute("cargaGrupoEvaluacion", cargaGrupoEvaluacion);
		modelo.addAttribute("lisEstrategias", lisEstrategias);
		modelo.addAttribute("grupoEstado", cargaGrupo.getEstado());
		
		
		return "portales/maestro/evaluacion";
	}
	
	@RequestMapping("/portales/maestro/grabarEvaluacion")
	public String portalesMaestroGrabarEvaluacion(HttpServletRequest request, Model modelo){
		
		String cursoCargaId			= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		String evaluacionId			= request.getParameter("EvaluacionId")==null?"0":request.getParameter("EvaluacionId");	
		String estrategiaId			= request.getParameter("EstrategiaId")==null?"0":request.getParameter("EstrategiaId");
		String nombreEvaluacion		= request.getParameter("NombreEvaluacion")==null?"0":request.getParameter("NombreEvaluacion");
		String fecha				= request.getParameter("Fecha")==null?aca.util.Fecha.getHoyReversa():request.getParameter("Fecha");
		String hora					= request.getParameter("Hora")==null?"0":request.getParameter("Hora");
		String minuto				= request.getParameter("Minuto")==null?"0":request.getParameter("Minuto");
		String valor				= request.getParameter("Valor")==null?"0":request.getParameter("Valor");
		String tipo					= request.getParameter("Tipo")==null?"0":request.getParameter("Tipo");	
		String enviar				= request.getParameter("Enviar")==null?"0":request.getParameter("Enviar");
		String codigoPersonal 		= "0";
		String mensaje 				= "-";
		
		HttpSession sesion		= request.getSession();
		if (sesion!=null){
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
		}
		boolean existe = false;
		CargaGrupoEvaluacion cargaGrupoEvaluacion = new CargaGrupoEvaluacion();
		LogOperacion log = new LogOperacion();
		if (cargaGrupoEvaluacionDao.existeReg(cursoCargaId, evaluacionId)){
			cargaGrupoEvaluacion = cargaGrupoEvaluacionDao.mapeaRegId(cursoCargaId, evaluacionId);
			existe = true;
		}else{
			evaluacionId = cargaGrupoEvaluacionDao.maximoReg(cursoCargaId);
			cargaGrupoEvaluacion.setEvaluacionId(evaluacionId);
		}	
		cargaGrupoEvaluacion.setCursoCargaId(cursoCargaId);
		cargaGrupoEvaluacion.setEvaluacionId(evaluacionId);
		cargaGrupoEvaluacion.setEstrategiaId(estrategiaId);
		cargaGrupoEvaluacion.setFecha(fecha.substring(0,10)+" "+hora+":"+minuto+":00");
		cargaGrupoEvaluacion.setNombreEvaluacion(nombreEvaluacion);
		cargaGrupoEvaluacion.setValor(valor);
		cargaGrupoEvaluacion.setTipo(tipo);	
		cargaGrupoEvaluacion.setEnviar(enviar);
		if (existe) {
			if (cargaGrupoEvaluacionDao.updateReg(cargaGrupoEvaluacion)) {
				String datos = "CursoCargaId: "+cursoCargaId+", CodigoPersonal: "+codigoPersonal+ ", Valor: "+valor+", Tipo: "+tipo;			
				log.setDatos(datos);
				log.setIp(request.getRemoteAddr());
				log.setUsuario(codigoPersonal);
				log.setTabla("carga_grupo_evaluacion");
				log.setOperacion("update");
				logOperacionDao.insertReg(log);
				mensaje = "Data updated";
			}else {
				mensaje = "Error while updating";
			}
		}else {
			if (cargaGrupoEvaluacionDao.insertReg(cargaGrupoEvaluacion)) {
				String datos = "CursoCargaId: "+cursoCargaId+", CodigoPersonal: "+codigoPersonal+ ", Valor: "+valor+", Tipo: "+tipo;			
				log.setDatos(datos);
				log.setIp(request.getRemoteAddr());
				log.setUsuario(codigoPersonal);
				log.setTabla("carga_grupo_evaluacion");
				log.setOperacion("insert");
				logOperacionDao.insertReg(log);
				mensaje = "Data saved";
			}else {
				mensaje = "Error while saving";
			}
		}
		
		
		return "redirect:/portales/maestro/evaluacion?CursoCargaId="+cursoCargaId+"&EvaluacionId="+evaluacionId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/portales/maestro/borrarActividad")
	public String portalesMaestroBorrarActividad(HttpServletRequest request, Model modelo){
		
		String cursoCargaId			= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		String actividadId			= request.getParameter("ActividadId")==null?"0":request.getParameter("ActividadId");
		String codigoPersonal 		= "0";
		String mensaje 				= "-";
		
		HttpSession sesion		= request.getSession();
		if (sesion!=null){
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
		}
		LogOperacion log = new LogOperacion();
		String datos = "CursoCargaId: "+cursoCargaId+", CodigoPersonal: "+codigoPersonal+", ActividadId: "+actividadId;			
		log.setDatos(datos);
		log.setIp(request.getRemoteAddr());
		log.setUsuario(codigoPersonal);
		log.setTabla("CARGA_GRUPO_ACTIVIDAD");
		if (cargaGrupoActividadDao.existeRegId(actividadId)){
			if(cargaGrupoActividadDao.deleteReg(actividadId)){
				log.setOperacion("delete");
				logOperacionDao.insertReg(log);
				mensaje = "Activity Deleted";
			}else {
				mensaje = "Error while deleting activity";
			}
		}	
				
		return "redirect:/portales/maestro/metodo?CursoCargaId="+cursoCargaId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/portales/maestro/evaluar")
	public String portalesMaestroEvaluar(HttpServletRequest request, Model modelo){
		
		String cursoCargaId		= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		String crear	 		= request.getParameter("Crear")==null?"0":request.getParameter("Crear");
		String codigoPersonal	= "0";
		String maestroNombre 	= "";
		String cursoId			= cargaGrupoCursoDao.cursoIdOrigen(cursoCargaId);
		String materiaNombre 	= mapaCursoDao.getNombreCurso(cursoId);
		int diferida			= krdxCursoActDao.numAlumnosMateria(cursoCargaId, "'6'");
		CargaGrupo cargaGrupo	= new CargaGrupo();
		MapaCurso mapaCurso		= mapaCursoDao.mapeaRegId(cursoId);
		Parametros parametros 	= parametrosDao.mapeaRegId("1");
		
		int numEstrategias  	= cargaGrupoEvaluacionDao.getNumEstrategias(cursoCargaId);
		int numEvaluadas		= cargaGrupoEvaluacionDao.getNumEstrategiasEvaluadas(cursoCargaId);
		int numBajas 			= cargaGrupoEvaluacionDao.getNumAlumnosBaja(cursoCargaId);
		
		HttpSession sesion		= request.getSession();
		if (sesion!=null){
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
			if (cargaGrupoDao.getCodigoPersonal(cursoCargaId).equals(codigoPersonal) || cargaGrupoDao.getCodigoOtro(cursoCargaId).equals(codigoPersonal)) {
				//enviarConEnoc(request,"Error-aca.ContPortalesMaestro|portalesMaestroEvaluar");				
				// Modificar estado a ordinario				
				if (cargaGrupoDao.existeReg(cursoCargaId)){
					cargaGrupo = cargaGrupoDao.mapeaRegId(cursoCargaId);
					if (cargaGrupo.getEstado().equals("1") && krdxCursoActDao.numAlumnosMateria(cursoCargaId, "'M','I','1','2','3','4','5','6'") >= 1){
						cargaGrupo.setEstado("2");
						cargaGrupoDao.updateReg(cargaGrupo);
					}
				}				
				maestroNombre = maestrosDao.getNombreMaestro(cargaGrupo.getCodigoPersonal(), "NOMBRE");	
			}else{
				System.out.println(codigoPersonal+" is not a professor of "+cursoCargaId);
			}		
		}	
		
		CargaGrupoEvaluacion cargaGrupoEvaluacion = new CargaGrupoEvaluacion();
		
		cargaGrupoEvaluacion.setCursoCargaId(cursoCargaId);
		cargaGrupoEvaluacion.setEvaluacionId("1");
		cargaGrupoEvaluacion.setNombreEvaluacion("Evaluacion Sumativa - PROMEDIO FINAL");
		cargaGrupoEvaluacion.setFecha(Fecha.getHoy());
		cargaGrupoEvaluacion.setEstrategiaId("A14");
		cargaGrupoEvaluacion.setValor("100");
		cargaGrupoEvaluacion.setTipo("P");
		cargaGrupoEvaluacion.setEvaluacionE42("0");
		cargaGrupoEvaluacion.setEnviar("N");
		
		if(crear.equals("1")) {
			cargaGrupoEvaluacionDao.insertReg(cargaGrupoEvaluacion);
		}
		
		List<CargaGrupoEvaluacion> lisEvaluaciones 		= cargaGrupoEvaluacionDao.getLista(cursoCargaId, " ORDER BY TO_CHAR(ENOC.CARGA_GRUPO_EVALUACION.FECHA,'YYYY-MM-DD'), EVALUACION_ID");
		List<KrdxCursoAct> lisAlumnos 					= krdxCursoActDao.lisAlumnosEnMateria(cursoCargaId, " ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");
		HashMap<String,String> mapaEstrategias			= catEstrategiaDao.getMapNombre("");
		HashMap<String,String> mapaAlumnos				= alumPersonalDao.mapAlumnosMateria(cursoCargaId);
		HashMap<String,String> mapaNotas				= krdxAlumnoEvalDao.getMapAlumEval(cursoCargaId);
		HashMap<String,AlumnoEficiencia> mapaEficiencia	= alumnoEficienciaDao.mapaMateria(cursoCargaId);
		HashMap<String,String> mapaTipoCal				= catTipoCalDao.mapTipoCal();
		HashMap<String,String> mapaPuntos				= krdxCursoActDao.mapAlumnoPuntos(cursoCargaId);
		HashMap<String,String> mapaExtras				= krdxCursoActDao.mapAlumnoExtras(cursoCargaId);
		HashMap<String,String> mapaEvaluados			= alumnoEficienciaDao.mapPuntosEvaluadosMateria(cursoCargaId);
		HashMap<String,String> mapaLimiteExtras			= krdxCursoActDao.mapLimiteExtra(cursoCargaId);
		HashMap<String,MapaCurso> mapaCursoMateria		= mapaCursoDao.mapaCursosEnMateria(cursoCargaId);
		HashMap<String,String> mapaActividades			= cargaGrupoActividadDao.mapaActividadesPorEvaluacion(cursoCargaId);
		HashMap<String,String> mapaPromedios			= new HashMap<String,String>();
		HashMap<String,String> mapaArchivosEval			= archivosAlumnoDao.mapaArchivosPorEvaluacion(cursoCargaId);
		HashMap<String,String> mapaArchivosAlum			= archivosAlumnoDao.mapaArchivosPorAlumno(cursoCargaId);
		HashMap<String, String> mapaAlumGraduados 		= alumEgresoDao.mapaGraduadosEnMateria(cursoCargaId);
		HashMap<String, String> mapaLetrasDeNotas		= catGradePointDao.mapaLetrasDeNotas(" ORDER BY INICIO");
		
		// Obtiene los promedios de los alumnos
		if (cargaGrupo.getEstado().equals("2")) {
			for (KrdxCursoAct actual :lisAlumnos){
				String promedio = krdxAlumnoEvalDao.getAlumnoPromedio(cursoCargaId, actual.getCodigoPersonal());
				mapaPromedios.put(actual.getCodigoPersonal(), promedio);
			}
		}
		
		modelo.addAttribute("cargaGrupo", cargaGrupo);
		modelo.addAttribute("mapaCurso", mapaCurso);
		modelo.addAttribute("cursoId", cursoId);
		modelo.addAttribute("parametros", parametros);
		modelo.addAttribute("numEstrategias", numEstrategias);
		modelo.addAttribute("numEvaluadas", numEvaluadas);
		modelo.addAttribute("numBajas", numBajas);
		modelo.addAttribute("maestroNombre", maestroNombre);
		modelo.addAttribute("materiaNombre", materiaNombre);
		modelo.addAttribute("diferida", diferida);
		modelo.addAttribute("lisEvaluaciones", lisEvaluaciones);
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("mapaEstrategias", mapaEstrategias);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("mapaNotas", mapaNotas);
		modelo.addAttribute("mapaTipoCal", mapaTipoCal);
		modelo.addAttribute("mapaPuntos", mapaPuntos);
		modelo.addAttribute("mapaExtras", mapaExtras);
		modelo.addAttribute("mapaEvaluados", mapaEvaluados);
		modelo.addAttribute("mapaLimiteExtras", mapaLimiteExtras);		
		modelo.addAttribute("mapaCursoMateria", mapaCursoMateria);
		modelo.addAttribute("mapaActividades", mapaActividades);
		modelo.addAttribute("mapaPromedios", mapaPromedios);
		modelo.addAttribute("mapaArchivosEval", mapaArchivosEval);
		modelo.addAttribute("mapaArchivosAlum", mapaArchivosAlum);
		modelo.addAttribute("mapaEficiencia", mapaEficiencia);
		modelo.addAttribute("mapaAlumGraduados", mapaAlumGraduados);
		modelo.addAttribute("mapaLetrasDeNotas", mapaLetrasDeNotas);
		
		return "portales/maestro/evaluar";
	}
	
	@RequestMapping("/portales/maestro/accion")
	public String portalesMaestroAccion(HttpServletRequest request, Model modelo){
		
		String cursoCargaId			= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		String evaluacionId			= request.getParameter("EvaluacionId")==null?"0":request.getParameter("EvaluacionId");
		String accion 				= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		int numAccion 				= Integer.parseInt(accion);
		String contador				= request.getParameter("Contador")==null?"0":request.getParameter("Contador");
		int numContador 			= Integer.parseInt(contador);
		int i=0;
		
		String codigoAlumno			= "0";
		String notaEval				= "0";
		String notaExtra			= "0";
		String mensaje				= "-";
		
		CargaGrupo cargaGrupo		= new CargaGrupo();
		KrdxAlumnoEval alumEval 	= new KrdxAlumnoEval();
		LogOperacion log 			= new LogOperacion();
		KrdxCursoAct kardex 		= new KrdxCursoAct();
		KrdxAlumnoActiv alumnoActiv = new KrdxAlumnoActiv();
		KrdxAlumnoEval alumnoEval	= new KrdxAlumnoEval();
		
		System.out.println("INDEX EN ACCION");
		String codigoPersonal		= "0";
		
		HttpSession sesion			= request.getSession();
		if (sesion!=null){
			codigoPersonal 			= (String)sesion.getAttribute("codigoPersonal"); 
		}
		
		List<KrdxCursoAct> lisAlumnos 				= krdxCursoActDao.lisAlumnosEnMateria(cursoCargaId, " ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");
		List<CargaGrupoEvaluacion> lisEvaluaciones 	= cargaGrupoEvaluacionDao.getLista(cursoCargaId, " ORDER BY TO_CHAR(ENOC.CARGA_GRUPO_EVALUACION.FECHA,'YYYY-MM-DD'), EVALUACION_ID");		
		
		switch (numAccion){
		
		case 1: { // Nuevo
			//resultado = "Formulario para ingresar las evaluaciones";
			break;
		}		
		case 2: { // Grabar Ordinario			
			numAccion	=1;		
			for (i=0; i<lisAlumnos.size(); i++){
				
				codigoAlumno 	= lisAlumnos.get(i).getCodigoPersonal();
				notaEval 		= request.getParameter("Nota"+i)==null?"0":request.getParameter("Nota"+i);

				/********* LOG ****/
				String datos = "CursoCargaId: "+cursoCargaId+", CodigoPersonal: "+codigoAlumno+ ", Evaluacion: "+evaluacionId+", Nota: "+notaEval;
				log.setDatos(datos);
				log.setIp(request.getRemoteAddr());
				log.setUsuario(codigoPersonal);
				log.setTabla("KRDX_ALUMNO_EVAL");
				
				if (!notaEval.equals("-")){
					if (krdxAlumnoEvalDao.existeReg(codigoAlumno, cursoCargaId, evaluacionId)){
						alumEval = krdxAlumnoEvalDao.mapeaRegId(codigoAlumno, cursoCargaId, evaluacionId);
						alumEval.setNota(notaEval);
						if (krdxAlumnoEvalDao.updateReg(alumEval)){
							mensaje = "Updated: "+alumEval.getCodigoPersonal();
							/******* LOG ******/
							log.setOperacion("update");
							logOperacionDao.insertReg(log);
						}else{
							mensaje = "Error updating: "+alumEval.getCodigoPersonal();
						}					
					}else{
						alumEval.setCursoCargaId(cursoCargaId);
						alumEval.setEvaluacionId(evaluacionId);
						alumEval.setCodigoPersonal(codigoAlumno);
						alumEval.setNota(notaEval);
						alumEval.setEvaluacionE42("0");					
						if (krdxAlumnoEvalDao.insertReg(alumEval)){
							mensaje = "Saved: "+alumEval.getCodigoPersonal();
							/******* LOG ******/
							log.setOperacion("insert");
							logOperacionDao.insertReg(log);						
						}else{
							mensaje = "Error saving: "+alumEval.getCodigoPersonal();
						}
					}
				}	
			}
			//sEvaluacion="0";
			break;
		}
		
		// Simular el Cierre de la Materia (Coloca ceros a las evaluaciones y actividades que no han sido evaluadas)
		case 3:{	
			
			// Busca las actividades y coloca un cero en las que no fueron evaluadas en la e42...
			List<CargaGrupoActividad> lisActividades = cargaGrupoActividadDao.getListCurso( cursoCargaId, "ORDER BY ACTIVIDAD_ID");				
			for(KrdxCursoAct ac : lisAlumnos){
				
				for(CargaGrupoActividad cga : lisActividades){						
					alumnoActiv.setCodigoPersonal(ac.getCodigoPersonal());						
					alumnoActiv.setActividadId(cga.getActividadId());
					alumnoActiv.setCursoCargaId(cursoCargaId);
					if(!krdxAlumnoActivDao.existeReg(ac.getCodigoPersonal(), cga.getActividadId())){
						alumnoActiv.setNota("0");
						alumnoActiv.setActividadE42("0");
						krdxAlumnoActivDao.insertReg(alumnoActiv);						
					}
				}
			}	
			
			// Busca las evaluaciones y coloca un cero en las que no fueron evaluadas en la e42...				
			for(KrdxCursoAct ac : lisAlumnos){					
				for(CargaGrupoEvaluacion cge : lisEvaluaciones){						
					alumnoEval.setCodigoPersonal(ac.getCodigoPersonal());
					alumnoEval.setCursoCargaId(cursoCargaId);
					alumnoEval.setEvaluacionId(cge.getEvaluacionId());
					if(!krdxAlumnoEvalDao.existeReg(ac.getCodigoPersonal(), cursoCargaId, cge.getEvaluacionId())){
						alumnoEval.setNota("0");
						alumnoEval.setEvaluacionE42("0");
						krdxAlumnoEvalDao.insertReg(alumnoEval);
					}
				}
			}			
			break;
		} // fin de opcion		
		
		case 4: { // Borrar
			
			for (i=0; i<lisAlumnos.size(); i++){
				
				codigoAlumno 	= lisAlumnos.get(i).getCodigoPersonal();
				notaEval 		= request.getParameter("Nota"+i)==null?"0":request.getParameter("Nota"+i);

				/********* LOG ****/
				String datos = "CursoCargaId: "+cursoCargaId+", CodigoPersonal: "+codigoAlumno+ ", Evaluacion: "+evaluacionId+", Nota: "+notaEval;
				log.setDatos(datos);
				log.setIp(request.getRemoteAddr());
				log.setUsuario(codigoPersonal);
				log.setTabla("KRDX_ALUMNO_EVAL");

				alumEval.setCodigoPersonal(lisAlumnos.get(i).getCodigoPersonal());
				alumEval.setNota(request.getParameter("Nota"+i));
				if (krdxAlumnoEvalDao.existeReg(codigoAlumno, cursoCargaId, evaluacionId)){
					if (krdxAlumnoEvalDao.deleteReg(codigoAlumno, cursoCargaId, evaluacionId) ){
						mensaje = "Deleted: "+alumEval.getCodigoPersonal();
						/******* LOG ******/
						log.setOperacion("delete");
						logOperacionDao.insertReg(log);
					}else{
						mensaje = "Error deleting: "+alumEval.getCodigoPersonal();
				}					
				}else{
					mensaje = "Not found: "+alumEval.getCodigoPersonal();
				}
			}
			//sEvaluacion="0";
			break;
		}		
		case 5:{ //Cerrar Materia Ordinaria
			int rowValido = 0; int rowUpdate = 0;						
			
			// Busca las actividades y coloca un cero en las que no fueron evaluadas en la e42...
			List<CargaGrupoActividad> lisActividades = cargaGrupoActividadDao.getListCurso( cursoCargaId, "ORDER BY ACTIVIDAD_ID");
			for(KrdxCursoAct ac : lisAlumnos){
				
				for(CargaGrupoActividad cga : lisActividades){						
					alumnoActiv.setCodigoPersonal(ac.getCodigoPersonal());						
					alumnoActiv.setActividadId(cga.getActividadId());
					alumnoActiv.setCursoCargaId(cursoCargaId);
					if(!krdxAlumnoActivDao.existeReg(ac.getCodigoPersonal(), cga.getActividadId())){
						alumnoActiv.setNota("0");
						alumnoActiv.setActividadE42("0");
						krdxAlumnoActivDao.insertReg(alumnoActiv);
					}
				}
			}
			
			// Busca las evaluaciones y coloca un cero en las que no fueron evaluadas en la e42...				
			for(KrdxCursoAct ac : lisAlumnos){					
				for(CargaGrupoEvaluacion cge : lisEvaluaciones){						
					alumnoEval.setCodigoPersonal(ac.getCodigoPersonal());
					alumnoEval.setCursoCargaId(cursoCargaId);
					alumnoEval.setEvaluacionId(cge.getEvaluacionId());
					if(!krdxAlumnoEvalDao.existeReg(ac.getCodigoPersonal(), cursoCargaId, cge.getEvaluacionId())){
						alumnoEval.setNota("0");
						alumnoEval.setEvaluacionE42("0");
						krdxAlumnoEvalDao.insertReg(alumnoEval);							
					}
				}
			}
			
			// Modificar estado del grupo a cerrado
			if (cargaGrupoDao.existeReg(cursoCargaId)){
				cargaGrupo = cargaGrupoDao.mapeaRegId(cursoCargaId);					
				cargaGrupo.setEstado("3");
				cargaGrupo.setfEvaluacion(aca.util.Fecha.getHoy());
				if (cargaGrupoDao.updateReg(cargaGrupo)) {			
					
					for(KrdxCursoAct ac : lisAlumnos){				
						
						String promedio = krdxAlumnoEvalDao.getAlumnoPromedio(ac.getCursoCargaId(), ac.getCodigoPersonal());						
						if (promedio==null) promedio="0";
						promedio = promedio.trim();
						int numPromedio = Integer.parseInt(promedio);												
						
						if (krdxCursoActDao.existeReg(ac.getCodigoPersonal(), ac.getCursoCargaId()) ){
							kardex = krdxCursoActDao.mapeaRegId(ac.getCodigoPersonal(), ac.getCursoCargaId());								
							String tipoCalId = kardex.getTipoCalId();
							
							// Si el tipo de calificacion no es PA, FA, I ó M
							if ( 	   !kardex.getTipoCalId().equals("3") 
									&& !kardex.getTipoCalId().equals("4") 
									&& !kardex.getTipoCalId().equals("5") 
									&& !kardex.getTipoCalId().equals("6")
									&& !kardex.getTipoCalId().equals("7")
									&& !kardex.getTipoCalId().equals("8")
								){								
								if (numPromedio >= Integer.parseInt(mapaCursoDao.mapeaRegId(kardex.getCursoId()).getNotaAprobatoria())){
									tipoCalId = "1";																		
								}else{ 
									tipoCalId = "2";																		 
								}
							}
							
							if (kardex.getTitulo().equals("N")){
								rowValido++;								
								if (krdxCursoActDao.updateCerrar(ac.getCursoCargaId(), ac.getCodigoPersonal(), promedio, aca.util.Fecha.getHoy(), tipoCalId))
									rowUpdate++;								
							}
						}						
					}
					if (rowUpdate==rowValido){							
						mensaje = "Ordinary subject closed!";
					}else{							
						mensaje = "The process did not complete... Try again!"+rowUpdate+" : "+rowValido;
					}					
				}else{
					mensaje = "Unable to close subject!"+rowUpdate+" : "+rowValido;
				}					
									
			}
			break;
		} // fin de opcion
		case 6: { //Grabar Extraordinarios
			String extras = request.getParameter("numExtras")==null?"0":request.getParameter("numExtras");
			int numExtras = Integer.parseInt(extras);
			for (i=1;i<=numExtras;i++){
				codigoAlumno 	= request.getParameter("CodigoPersonalE"+i)==null?"0":request.getParameter("CodigoPersonalE"+i);
				notaExtra		= request.getParameter("NotaExtra"+i);
				kardex.setCodigoPersonal(codigoAlumno);
				kardex.setCursoCargaId(cursoCargaId);
				if (krdxCursoActDao.existeReg(codigoAlumno, cursoCargaId)) {
					kardex = krdxCursoActDao.mapeaRegId(codigoAlumno, cursoCargaId);
					kardex.setNotaExtra(notaExtra);
					kardex.setfExtra(aca.util.Fecha.getHoy());					
					if (kardex.getTipoCalId()!= "3" && !kardex.getTipoCalId().equals("5") && !kardex.getTipoCalId().equals("6")){
						if (Integer.parseInt(kardex.getNotaExtra()) >= Integer.parseInt(mapaCursoDao.mapeaRegId(kardex.getCursoId()).getNotaAprobatoria()))
							kardex.setTipoCalId("1");
						else 
							kardex.setTipoCalId("2");
					}	
					if (krdxCursoActDao.updateReg(kardex)){
						mensaje = "OK. Extraordinary Saved";
						/********* LOG ****/
						String datos = "CursoCargaId: "+cursoCargaId+", CodigoPersonal: "+codigoAlumno + ", Nota Extra: "+notaExtra+", Fecha: "+aca.util.Fecha.getHoy();
						log.setDatos(datos);
						log.setIp(request.getRemoteAddr());
						log.setUsuario(codigoPersonal);
						log.setTabla("krdx_curso_act");
						log.setOperacion("update");
						logOperacionDao.insertReg( log);
					}
					else mensaje = "An error occurred while updating extraordinary";
				}				
			}
			//sEvaluacion = "0";
			break;
		}		
		case 7: { //Cerrar Materia Extraordinaria
			
			if (cargaGrupoDao.existeReg(cursoCargaId)){
				cargaGrupo = cargaGrupoDao.mapeaRegId(cursoCargaId);					
				cargaGrupo.setEstado("4");				
				if (cargaGrupoDao.updateReg(cargaGrupo)) {
					for (KrdxCursoAct ac : lisAlumnos){					
						kardex.setCodigoPersonal(ac.getCodigoPersonal());
						kardex.setCursoCargaId(ac.getCursoCargaId());
						if (krdxCursoActDao.existeReg(ac.getCodigoPersonal(), ac.getCursoCargaId()) ){
							kardex = krdxCursoActDao.mapeaRegId(ac.getCodigoPersonal(), ac.getCursoCargaId());
							MapaCurso mapaCurso = mapaCursoDao.mapeaRegId(kardex.getCursoId());							
							// Si el tipo de calificación es diferente de RA, CD o CP actualiza el estado como AC o NA.
							//System.out.println(kardex.getCodigoPersonal()+" EX="+kardex.getNotaExtra()+" NA="+mapaCurso.getNotaAprobatoria()+" TC="+kardex.getTipoCalId());
							if ( !kardex.getTipoCalId().equals("3") && !kardex.getTipoCalId().equals("5") && !kardex.getTipoCalId().equals("6")){
								//System.out.println(Integer.parseInt(kardex.getNotaExtra()) >= Integer.parseInt(mapaCurso.getNotaAprobatoria()));
								if (Integer.parseInt(kardex.getNotaExtra()) >= Integer.parseInt(mapaCurso.getNotaAprobatoria()) || Integer.parseInt(kardex.getNota()) >= Integer.parseInt(mapaCurso.getNotaAprobatoria()))
									kardex.setTipoCalId("1");
								else
									kardex.setTipoCalId("2"); 
							}
							krdxCursoActDao.updateReg(kardex);
						}						
					}
					mensaje = "Extrordinary cycle closed!";
				}else{
					mensaje = "Unable to close extraordinary cycle!";
				}				
			}					
			break;
		}		
		case 8: {
			/* Opcion Cancelada */
			/*
			// Forzar la actualización de la página
			String time = aca.util.Fecha.getHora()+":"+aca.util.Fecha.getMinutos()+":"+aca.util.Fecha.getSegundos();
			Materia.mapeaRegId(conEnoc,sCursoCargaId);
			Materia.setEstado("4");
			Materia.updateReg(conEnoc);	
			mensaje = "Entregar el Acta!!!";
			//System.out.println("Antes de response:"+time);
			response.sendRedirect("cactames.jsp?cursoCargaId="+sCursoCargaId+"&imp=1&time="+time);
			*/
			break;
		}
		case 9: { //Grabar Tipo Calificación 
			for (i=0;i<=lisAlumnos.size()-1;i++){
				System.out.println(i);
				codigoAlumno = lisAlumnos.get(i).getCodigoPersonal();
				kardex.setCodigoPersonal(codigoAlumno);
				kardex.setCursoCargaId(cursoCargaId);
				if (krdxCursoActDao.existeReg(codigoAlumno,cursoCargaId)){
					kardex = krdxCursoActDao.mapeaRegId(codigoAlumno, cursoCargaId);
					kardex.setTipoCalId(request.getParameter("tipocalid"+i));
					if (krdxCursoActDao.updateReg(kardex)){
						mensaje = "Status Updated";
						/********* LOG ****/
						String datos = "CursoCargaId: "+cursoCargaId+", CodigoPersonal: "+codigoAlumno + ", TipoCalId: "+request.getParameter("tipocalid"+i)+", Fecha: "+aca.util.Fecha.getHoy();
						log.setDatos(datos);
						log.setIp(request.getRemoteAddr());
						log.setUsuario(codigoPersonal);
						log.setTabla("krdx_curso_act");
						log.setOperacion("update");
						logOperacionDao.insertReg(log);
					}else {
						mensaje = "Error while updating status";
					}	
				}
			}
			//sEvaluacion = "0";
			break;
		}
		} // fin de switch
		
		return "redirect:/portales/maestro/evaluar?CursoCargaId="+cursoCargaId;
	}
	
	@RequestMapping("/portales/maestro/filesMaestro")
	public String portalesMaestroFilesMaestro(HttpServletRequest request, Model modelo){
		
		String cursoCargaId		= request.getParameter("CursoCargaId") == null ? "0" : request.getParameter("CursoCargaId");
		String cursoId			= cargaGrupoCursoDao.cursoIdOrigen(cursoCargaId);
		String materiaNombre 	= mapaCursoDao.getNombreCurso(cursoId);
		
		List<ArchivosProfesor> lisArchivos 	= archivosProfesorDao.listaArchivosMateria(cursoCargaId);
		
		modelo.addAttribute("materiaNombre", materiaNombre);
		modelo.addAttribute("cursoCargaId", cursoCargaId);
		modelo.addAttribute("lisArchivos", lisArchivos);
				
		return "portales/maestro/filesMaestro";
	}
	
	@RequestMapping("/portales/maestro/editarComentario")
	public String portalesMaestroEditarComentario(HttpServletRequest request, Model modelo){
		String cursoCargaId 	= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		String folio		 	= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String profesor		 	= request.getParameter("Profesor")==null?"0":request.getParameter("Profesor");
		ArchivosProfesor archivosProfesor = new ArchivosProfesor(); 
		
		if (archivosProfesorDao.existeReg(cursoCargaId, Integer.parseInt(folio), profesor)) {
			archivosProfesor = archivosProfesorDao.mapeaRegId(cursoCargaId, Integer.parseInt(folio), profesor);
		}
		
		modelo.addAttribute("archivosProfesor", archivosProfesor);
		
		return "portales/maestro/editarComentario";
	}
	
	@RequestMapping("/portales/maestro/grabarComentario")
	public String portalesMaestroGrabarComentario(HttpServletRequest request){
		String cursoCargaId 	= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		String folio		 	= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String profesor		 	= request.getParameter("Profesor")==null?"0":request.getParameter("Profesor");
		String comentario		= request.getParameter("Comentario")==null?"-":request.getParameter("Comentario");
		if (archivosProfesorDao.existeReg(cursoCargaId, Integer.parseInt(folio), profesor)) {
			archivosProfesorDao.updateComentario(cursoCargaId, Integer.parseInt(folio),profesor, comentario);
		}
		return "redirect:/portales/maestro/filesMaestro?CursoCargaId="+cursoCargaId;
	}
	
	@RequestMapping("/portales/maestro/grabarNota")
	public String portalesMaestroGrabarNota(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPortalesMaestro|portalesMaestroGrabarNota");
		return "portales/maestro/grabarNota";
	}
	
	@RequestMapping("/portales/maestro/horario3")
	public String portalesMaestroHorario3(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPortalesMaestro|portalesMaestroHorario3");
		return "portales/maestro/horario3";
	}
	
	@RequestMapping("/portales/maestro/instrumento")
	public String portalesMaestroInstrumento(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPortalesMaestro|portalesMaestroInstrumento");
		return "portales/maestro/instrumento";
	}
	
	@RequestMapping("/portales/maestro/listado")
	public String portalesMaestroListado(HttpServletRequest request, Model modelo){	
		
		String codigoPersonal	= "";
		HttpSession sesion		= request.getSession();
		if (sesion!=null){
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
		}	
		String cursoCargaId	= request.getParameter("CursoCargaId");	
		String cargaId		= request.getParameter("CargaId");	
		String bloqueId		= request.getParameter("BloqueId");	
		
		List<AlumnoCurso> lisAlumnos 					= alumnoCursoDao.getListCurso(cursoCargaId, " ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");
		HashMap<String, CatModalidad> mapModalidad 		= catModalidadDao.getMapAll("");
		HashMap<String, AlumPersonal> mapPersonal 		= alumPersonalDao.mapAlumnosEnMateria(cursoCargaId);
		HashMap<String, String> mapaModoCargaAlumnos 	= cargaAlumnoDao.mapaModoCargaAlumnos(cargaId, bloqueId);
		
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("mapModalidad", mapModalidad);
		modelo.addAttribute("mapPersonal", mapPersonal);
		modelo.addAttribute("mapaModoCargaAlumnos", mapaModoCargaAlumnos);
		
		return "portales/maestro/listado";
	}
	
	@RequestMapping("/portales/maestro/metodo")
	public String portalesMaestroMetodo(HttpServletRequest request, Model modelo){
		
		String cursoCargaId			= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		String cargaId      		= cargaGrupoDao.getCargaId(cursoCargaId);		
		String cursoCargaIdOrigen	= request.getParameter("cc")==null?"0":request.getParameter("cc");
		String cursoId				= cargaGrupoCursoDao.cursoIdOrigen(cursoCargaId);
		String materiaNombre 		= mapaCursoDao.getNombreCurso(cursoId);
		String codigoPersonal 		= "0";
		
		HttpSession sesion		= request.getSession();
		if (sesion!=null){
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
		}		
		String maestroNombre 	= maestrosDao.getNombreMaestro(codigoPersonal, "NOMBRE");
		
		String sAccion 			= request.getParameter("Accion")==null?"1":request.getParameter("Accion");
		int nAccion				= Integer.parseInt(sAccion);
		
		String sResultado		= "";
		String sResultado2		= "";		
		String evaluaCarga		= cargaDao.evaluaCarga(cargaId);
		
//		System.out.println("STEP 1 " + cursoCargaId);
//		System.out.println("STEP 2 " + cargaId);
//		System.out.println("STEP 3 " + cursoCargaIdOrigen);
//		System.out.println("STEP 4 " + cursoId);
//		System.out.println("STEP 5 " + materiaNombre);
//		System.out.println("STEP 6 " + codigoPersonal);
		
		LogOperacion log 		= new LogOperacion();
		CargaGrupoEvaluacion evaluacion = new CargaGrupoEvaluacion();				  
		
		// Operaciones a realizar en la pantalla
		switch (nAccion){
			case 1: { // Nuevo			
			//	sResultado = "Llene el formulario correctamente ..¡¡";
				break;
			}
			case 4: { // Borrar
				/********* LOG ****/
				String datos = "CursoCargaId: "+cursoCargaId+", CodigoPersonal: "+codigoPersonal+ ", Valor: "+request.getParameter("Valor")+", Tipo: "+request.getParameter("Tipo");
			
				log.setDatos(datos);
				log.setIp(request.getRemoteAddr());
				log.setUsuario(codigoPersonal);
				log.setTabla("carga_grupo_evaluacion");
				
				evaluacion.setCursoCargaId( cursoCargaId );
				evaluacion.setEvaluacionId( request.getParameter("EvaluacionId"));
				//System.out.println("Evaluacion:"+request.getParameter("EvaluacionId"));
				if (cargaGrupoEvaluacionDao.existeReg(evaluacion.getCursoCargaId(), evaluacion.getEvaluacionId())){
				
					// Se borra si NO tiene notas registradas
					if (!cargaGrupoEvaluacionDao.notasReg(evaluacion.getCursoCargaId(), evaluacion.getEvaluacionId())){
						cargaGrupoEvaluacionDao.deleteNotas(evaluacion.getCursoCargaId(), evaluacion.getEvaluacionId());
						if (cargaGrupoEvaluacionDao.deleteReg(evaluacion.getCursoCargaId(), evaluacion.getEvaluacionId())){
							/****** LOG ******/
							log.setOperacion("delete");
							logOperacionDao.insertReg(log);
							
							sResultado = "Deleted: "+evaluacion.getEvaluacionId();
						}else{
							sResultado = "Error deleting: "+evaluacion.getEvaluacionId();
						}
					}else{
						sResultado = "Error while deleting! Grades still registered. Change grades to 0"+evaluacion.getEvaluacionId();
					}	
				}else{
					sResultado = "Not found: "+evaluacion.getEvaluacionId();
				}
				break;
			}
			case 6: { // Copiar Estrategias	de materia				
				if (cargaGrupoEvaluacionDao.copiarEstrategias(cursoCargaIdOrigen,cursoCargaId))
					sResultado2 = "Strategies copied!";
				else
					sResultado2 = "These strategies have not been copied. <br> Perhaps you already have strategies with the same evaluation number. ";
				break;
			}
			case 7: { // Copiar Estrategias de materia externa				
				if (cargaGrupoEvaluacionDao.copiarEstrategias(request.getParameter("externo"),cursoCargaId))
					sResultado2 = "Strategies copied!";
				else
					sResultado2 = "These strategies have not been copied. <br> Perhaps you already have strategies with the same evaluation number. ";
				break;
			}
		}
		
		List<CargaGrupoEvaluacion> lisEvaluacion 			= cargaGrupoEvaluacionDao.getLista(cursoCargaId, "ORDER BY TO_CHAR(ENOC.CARGA_GRUPO_EVALUACION.FECHA,'YYYY/MM/DD'), EVALUACION_ID");
		List<CargaAcademica> lisCursos 						= cargaAcademicaDao.getListaMaestro(cargaId, codigoPersonal, "ORDER BY NOMBRE_CURSO");
		List<CargaGrupoActividad> lisActividad 				= cargaGrupoActividadDao.getListAllCargaEvaluacion(cursoCargaId);		
		
		HashMap<String, String> mapNombreEstrategia 		= catEstrategiaDao.getMapNombre("ORDER BY ESTRATEGIA_ID");
		HashMap<String, Double> mapPromedioEstrategia 		= cargaGrupoEvaluacionDao.mapPromedioEstrategia(cursoCargaId);
		HashMap<String, KrdxAlumnoEval> mapNotasEval 		= cargaGrupoEvaluacionDao.mapNotasEval(cursoCargaId);
		HashMap<String, String> mapaActividadesEvaluadas	= krdxAlumnoActivDao.mapaActividadesEvaluadas(cursoCargaId);
		HashMap<String, String> mapaArchivosActividad		= archivosAlumnoDao.mapaArchivosPorMateria(cursoCargaId);
		
		modelo.addAttribute("cursoCargaId", cursoCargaId);
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("evaluaCarga", evaluaCarga);
		modelo.addAttribute("materiaNombre", materiaNombre);
		modelo.addAttribute("maestroNombre", maestroNombre);
		modelo.addAttribute("sResultado", sResultado);
		modelo.addAttribute("sResultado2", sResultado2);
		modelo.addAttribute("lisEvaluacion", lisEvaluacion);
		modelo.addAttribute("lisCursos", lisCursos);
		modelo.addAttribute("lisActividad", lisActividad);
		modelo.addAttribute("mapNombreEstrategia", mapNombreEstrategia);
		modelo.addAttribute("mapPromedioEstrategia", mapPromedioEstrategia);
		modelo.addAttribute("mapNotasEval", mapNotasEval);
		modelo.addAttribute("mapaActividadesEvaluadas", mapaActividadesEvaluadas);
		modelo.addAttribute("mapaArchivosActividad", mapaArchivosActividad);
		
		return "portales/maestro/metodo";
	}
	
	@RequestMapping("/portales/maestro/opinion_estudiantil")
	public String portalesMaestroOpinionEstudiantil(HttpServletRequest request, Model modelo){		
		//enviarConEnoc(request,"Error-aca.ContPortalesMaestro|portalesMaestroOpinionEstudiantil");
		
		String codigoPersonal	= "0";
		String empleadoNombre 	= "-";
		
		String periodoId		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String edoId			= request.getParameter("EdoId")==null?"0":request.getParameter("EdoId");
		HttpSession sesion		= request.getSession();
		if (sesion != null){
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
			empleadoNombre 		= maestrosDao.getNombreMaestro(codigoPersonal, "NOMBRE");
		}		
		List<EdoPeriodo> lisPeriodos			= edoPeriodoDao.getListAll("ORDER BY PERIODO_ID DESC");
		if(periodoId.equals("0") && lisPeriodos.size() > 0) periodoId = lisPeriodos.get(0).getPeriodoId();		
		List<Edo> lisEvaluaciones				= edoDao.getListTipo( "E", "AND PERIODO_ID = '"+periodoId+"' ORDER BY TO_DATE(F_INICIO, 'DD/MM/YYYY')");
		if (edoId.equals("0") && lisEvaluaciones.size() > 0) edoId = lisEvaluaciones.get(0).getEdoId();
		List<EdoAlumnoPreg> lisPreguntas		= edoAlumnoPregDao.getListEdo(edoId, "AND TIPO = 'O' AND SECCION = 'B' ORDER BY ORDEN DESC");
		
		HashMap<String,String> mapaPromedios	= edoAlumnoRespDao.mapaPromedioPreguntas(edoId, codigoPersonal);
		HashMap<String,String> mapaMinimos		= edoAlumnoRespDao.mapaMinimoPreguntas(edoId, codigoPersonal);
		HashMap<String,String> mapaMaximos		= edoAlumnoRespDao.mapaMaximoPreguntas(edoId, codigoPersonal);
		
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("edoId", edoId);
		modelo.addAttribute("empleadoNombre", empleadoNombre);
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("lisEvaluaciones", lisEvaluaciones);
		modelo.addAttribute("lisPreguntas", lisPreguntas);
		modelo.addAttribute("mapaPromedios", mapaPromedios);
		modelo.addAttribute("mapaMinimos", mapaMinimos);
		modelo.addAttribute("mapaMaximos", mapaMaximos);
		
		
		return "portales/maestro/opinion_estudiantil";
	}
	
	@RequestMapping("/portales/maestro/opinion_materia")
	public String portalesMaestroOpinionMateria(HttpServletRequest request, Model modelo){
		String codigoPersonal	= "";
		HttpSession sesion		= request.getSession();
		if (sesion!=null){
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
		}		
		
		String cursoCargaId			= request.getParameter("cursoCargaId");
		String edoId				= edoAlumnoRespDao.getEdoId(cursoCargaId);
		String nombreMaestro		= maestrosDao.getNombreMaestro(codigoPersonal, "NOMBRE");
		String participaron			= edoAlumnoRespDao.getAlumEvalMateria(cursoCargaId);
		String faltaron				= edoAlumnoRespDao.getAlumFaltantesMateria(cursoCargaId);
		List<EdoAlumnoPreg> lisPreg	= edoAlumnoPregDao.getListEdo(edoId, "AND TIPO = 'O' AND SECCION = 'B' ORDER BY ORDEN");
		
		HashMap <String, String> mapaPromedioRespuestas = new HashMap <String, String>();
		for (EdoAlumnoPreg pregunta : lisPreg) {
			mapaPromedioRespuestas.put(pregunta.getPreguntaId(), edoAlumnoRespDao.getPromedioPreguntaPorMateria(edoId, pregunta.getPreguntaId(), cursoCargaId));
		}
		
		HashMap <String, String> mapaMinimo = new HashMap <String, String>();
		for (EdoAlumnoPreg pregunta : lisPreg) {
			mapaMinimo.put(pregunta.getPreguntaId(), edoAlumnoRespDao.getMinPreguntaMateria(edoId, pregunta.getPreguntaId(), cursoCargaId));
		}
		
		HashMap <String, String> mapaMaximo = new HashMap <String, String>();
		for (EdoAlumnoPreg pregunta : lisPreg) {
			mapaMaximo.put(pregunta.getPreguntaId(), edoAlumnoRespDao.getMaxPreguntaMateria(edoId, pregunta.getPreguntaId(), cursoCargaId));
		}		
		
		modelo.addAttribute("participaron",participaron);
		modelo.addAttribute("faltaron",faltaron);
		modelo.addAttribute("nombreMaestro", nombreMaestro);
		modelo.addAttribute("lisPreg", lisPreg);
		modelo.addAttribute("mapaPromedioRespuestas", mapaPromedioRespuestas);
		modelo.addAttribute("mapaMinimo", mapaMinimo);
		modelo.addAttribute("mapaMaximo", mapaMaximo);
		
		
		return "portales/maestro/opinion_materia";
	}
	
	@RequestMapping("/portales/maestro/pasaAsistencia")
	public String portalesMaestroPasaAsistencia(HttpServletRequest request, Model modelo){
		//enviarConEnoc(request,"Error-aca.ContPortalesMaestro|portalesMaestroPasaAsistencia");
		String codigoPersonal	= "0";
		String maestroNombre	= "-";
		HttpSession sesion		= request.getSession();
		if (sesion != null){
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
			maestroNombre 		= maestrosDao.getNombreMaestro(codigoPersonal, "NOMBRE");
		}		
		String cursoCargaId		= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		String materia 			= request.getParameter("Materia")==null?"0":request.getParameter("Materia");	
		String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String accion			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		int accionFmt			= 0;
		
		List<AlumnoCurso> lisAlumnos = alumnoCursoDao.getListCurso(cursoCargaId, "ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");
		
		// if (accion.equals("1")) {
		// 	CargaGrupoAsistencia programa = new CargaGrupoAsistencia();
		// 	programa.setCargaGrupoId(cursoCargaId);
		// 	programa.setFolio(folio);		
		// 	programa.setCursoId(materia);
			
		// 	for (AlumnoCurso alumno : lisAlumnos){
				
		// 		String presente = request.getParameter("Presente"+alumno.getCodigoPersonal())==null?"0":request.getParameter("Presente"+alumno.getCodigoPersonal());
		// 		String tardanza = request.getParameter("Tardanza"+alumno.getCodigoPersonal())==null?"0":request.getParameter("Tardanza"+alumno.getCodigoPersonal());
		// 		String estado  	= "0";
				
		// 		if (presente.equals("0")&&tardanza.equals("0")) 
		// 			estado = "3";
		// 		else if ( (presente.equals("1")&&tardanza.equals("2")) || ( presente.equals("0")&&tardanza.equals("2") ))
		// 			estado = "2";
		// 		else if(presente.equals("1")&&tardanza.equals("0"))
		// 			estado = "1";			
				
		// 		programa.setCodigoPersonal(alumno.getCodigoPersonal());
		// 		programa.setEstado(estado);
				
		// 		if(!cargaGrupoAsistenciaDao.existeReg(cursoCargaId, folio, alumno.getCodigoPersonal())){
					
		// 			if(cargaGrupoAsistenciaDao.insertReg(programa)){
		// 				accionFmt = 1;
		// 			}else{
		// 				accionFmt = 2;
		// 			}
		// 		}else{//Modifica				
		// 			if(cargaGrupoAsistenciaDao.updateReg( programa)){
		// 				accionFmt = 3;
		// 			}else{
		// 				accionFmt = 4;
		// 			}			
		// 		}
		// 	}			
		// }		
		
		// Consulta la asistencai de los alumnos en la materia
		HashMap<String, String> mapAsistencia 		= cargaGrupoAsistenciaDao.mapAsistencia(cursoCargaId, folio);
		HashMap<String, AlumPersonal> mapAlumnos 	= alumPersonalDao.mapAlumnosEnMateria(cursoCargaId);
		HashMap<String, String>mapNombreCorto       = catTipoCalDao.mapTipoCalCorto();
		
		modelo.addAttribute("maestroNombre", maestroNombre);
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		// modelo.addAttribute("accionFmt", accionFmt);
		modelo.addAttribute("mapAsistencia", mapAsistencia);
		modelo.addAttribute("mapAlumnos", mapAlumnos);
		modelo.addAttribute("mapNombreCorto", mapNombreCorto);
		
		return "portales/maestro/pasaAsistencia";
	}

	@RequestMapping("/portales/maestro/grabarAsistencia")
	public String portalesMaestroGrabarAsistencia(HttpServletRequest request, Model modelo){
		String cursoCargaId		= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		String materia 			= request.getParameter("Materia")==null?"0":request.getParameter("Materia");	
		String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		int accionFmt			= 0;

		List<AlumnoCurso> lisAlumnos = alumnoCursoDao.getListCurso(cursoCargaId, "ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");

		CargaGrupoAsistencia programa = new CargaGrupoAsistencia();
		programa.setCargaGrupoId(cursoCargaId);
		programa.setFolio(folio);		
		programa.setCursoId(materia);

		for (AlumnoCurso alumno : lisAlumnos){
				
			String presente = request.getParameter("Presente"+alumno.getCodigoPersonal())==null?"0":request.getParameter("Presente"+alumno.getCodigoPersonal());
			String tardanza = request.getParameter("Tardanza"+alumno.getCodigoPersonal())==null?"0":request.getParameter("Tardanza"+alumno.getCodigoPersonal());
			String estado  	= "0";
			
			if (presente.equals("0")&&tardanza.equals("0")) 
				estado = "3";
			else if ( (presente.equals("1")&&tardanza.equals("2")) || ( presente.equals("0")&&tardanza.equals("2") ))
				estado = "2";
			else if(presente.equals("1")&&tardanza.equals("0"))
				estado = "1";			
			
			programa.setCodigoPersonal(alumno.getCodigoPersonal());
			programa.setEstado(estado);
			
			if(!cargaGrupoAsistenciaDao.existeReg(cursoCargaId, folio, alumno.getCodigoPersonal())){
				
				if(cargaGrupoAsistenciaDao.insertReg(programa)){
					accionFmt = 1;
				}else{
					accionFmt = 2;
				}
			}else{//Modifica				
				if(cargaGrupoAsistenciaDao.updateReg( programa)){
					accionFmt = 3;
				}else{
					accionFmt = 4;
				}			
			}
		}

		return "redirect:/portales/maestro/pasaAsistencia?CursoCargaId="+cursoCargaId+"&Materia="+materia+"&Folio="+folio+"&accionFmt="+accionFmt;
	}
	
	@RequestMapping("/portales/maestro/pautas")
	public String portalesMaestroPautas(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPortalesMaestro|portalesMaestroPautas");
		return "portales/maestro/pautas";
	}
	
	@RequestMapping("/portales/maestro/plan")
	public String portalesMaestroPlan(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPortalesMaestro|portalesMaestroPlan");
		return "portales/maestro/plan";
	}
	
	@RequestMapping("/portales/maestro/planPDF")
	public String portalesMaestroPlanPDF(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPortalesMaestro|portalesMaestroPlanPDF");
		return "portales/maestro/planPDF";
	}
	
	@RequestMapping("/portales/maestro/planPDFI")
	public String portalesMaestroPlanPDFI(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPortalesMaestro|portalesMaestroPlanPDFI");
		return "portales/maestro/planPDFI";
	}
	
	@RequestMapping("/portales/maestro/subirArchivo")
	public String portalesMaestroSubirArchivo(HttpServletRequest request, Model modelo){
		
		String cursoCargaId 	= request.getParameter("CursoCargaId") == null ? "0" : request.getParameter("CursoCargaId");
		String folio 			= request.getParameter("Folio") == null ? "0" : request.getParameter("Folio");
		String maestro 			= request.getParameter("Maestro") == null ? "0" : request.getParameter("Maestro");
		String mensaje 			= request.getParameter("Mensaje") == null ? "-" : request.getParameter("Mensaje");
		
		ArchivosProfesor archivo = new ArchivosProfesor();
		if (archivosProfesorDao.existeReg(cursoCargaId, Integer.parseInt(folio), maestro)) {
			archivo = archivosProfesorDao.mapeaRegId(cursoCargaId, Integer.parseInt(folio), maestro);
		}	
		
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("archivo", archivo);
		modelo.addAttribute("cursoCargaId", cursoCargaId);		
		
		return "portales/maestro/subirArchivo";
	}
	
	@RequestMapping("/portales/maestro/grabarArchivo")
	public String portalesMaestroGrabarArchivo(HttpServletRequest request, Model modelo, @RequestParam("archivo") MultipartFile archivo){
		
		String profesor			= "0";
		String cursoCargaId 	= request.getParameter("CursoCargaId") == null ? "0" : request.getParameter("CursoCargaId");
		String accion 			= request.getParameter("Accion") == null ? "0" : request.getParameter("Accion");
		String comentario		= request.getParameter("comentario") == null ? "-" : request.getParameter("comentario");
		//String accesoAlumnos	= request.getParameter("matriculas") == null ? "-" : request.getParameter("matriculas");
		String accesoAlumnos	= "-";
		String mensaje			= "-";
		
		HttpSession sesion = request.getSession();
		if (sesion != null) {
			profesor = (String) sesion.getAttribute("codigoEmpleado");
		}
		
		if (accion.equals("1")){
			try{				
				byte[] file = archivo.getBytes();
				
				ArchivosProfesor grabaArchivo = new ArchivosProfesor();				
				String nombreArchivo 	= archivo.getOriginalFilename();
				
				grabaArchivo.setArchivoId(cursoCargaId);
				grabaArchivo.setCodigoPersonal(profesor);
				grabaArchivo.setFecha(aca.util.Fecha.getHoy());
				grabaArchivo.setNombre(nombreArchivo);
				grabaArchivo.setAutorizacion(accesoAlumnos);
				grabaArchivo.setComentario(comentario);	
				grabaArchivo.setArchivoNuevo(file);
				
				if (archivosProfesorDao.guardaArchivo(grabaArchivo,"-",archivo.getName())){
					mensaje = "File Sent!";
				}else{
					mensaje = "Error sending!";
				}
				
			}catch( Exception e) {
				e.printStackTrace();
			}
		}
		
		return "redirect:/portales/maestro/subirArchivo?CursoCargaId="+cursoCargaId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/portales/maestro/borrarArchivo")
	public String portalesMaestroBorrarArchivo(HttpServletRequest request, Model modelo){
		
		String cursoCargaId = request.getParameter("CursoCargaId") == null ? "0" : request.getParameter("CursoCargaId");
		String folio 		= request.getParameter("Folio") == null ? "0" : request.getParameter("Folio");
		String profesor 	= request.getParameter("Profesor") == null ? "0" : request.getParameter("Profesor");

		archivosProfesorDao.eliminarArchivo(cursoCargaId, Integer.parseInt(folio), profesor);
		
		return "redirect:/portales/maestro/filesMaestro?CursoCargaId="+cursoCargaId;
	}

	@RequestMapping("/portales/maestro/bajarArchivo")
	public void portalesMaestroBajarArchivo(HttpServletRequest request, HttpServletResponse response) {
		String cursoCargaId = request.getParameter("CursoCargaId") == null ? "0" : request.getParameter("CursoCargaId");
		String folio 		= request.getParameter("Folio") == null ? "0" : request.getParameter("Folio");
		String profesor 	= request.getParameter("Profesor") == null ? "0" : request.getParameter("Profesor");
		
		ArchivosProfesor archivo = archivosProfesorDao.mapeaRegId(cursoCargaId, Integer.parseInt(folio), profesor);		
		try{
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition","attachment; filename=\""+archivo.getNombre()+ "\"");
			response.getOutputStream().write(archivo.getArchivoNuevo());						
			response.flushBuffer();
			
		}catch(Exception ex){
			System.out.println("Error /bajarArchivo:"+ex);
		}
	}
	
	@RequestMapping("/portales/maestro/tarjeta")
	public String portalesMaestroTarjeta(HttpServletRequest request, Model modelo){
		String cursoCargaId			= request.getParameter("CursoCargaId");
		
		List <AlumnoCurso> lisAlumnos 					= alumnoCursoDao.getListCurso(cursoCargaId, "ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");
		HashMap <String, CatTipoCal> mapaCat 			= catTipoCalDao.getMapAll("");
		HashMap <String, CatPais> mapaPaises 			= catPaisDao.getMapAll();
		HashMap <String, CatEstado> mapaEstados 		= catEstadoDao.getMapAll();
		HashMap <String, CatCiudad> mapaCiudades 		= catCiudadDao.getMapAll("");
		HashMap <String, CatReligion> mapaReligiones 	= catReligionDao.getMapAll("");
		HashMap <String, AlumPersonal> mapaPersonal		= alumPersonalDao.mapAlumnosEnMateria(cursoCargaId);
		HashMap <String, AlumAcademico> mapaAcademico	= alumAcademicoDao.mapAlumnosEnMateria(cursoCargaId);
		
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("mapaCat", mapaCat);
		modelo.addAttribute("mapaPersonal", mapaPersonal);
		modelo.addAttribute("mapaAcademico", mapaAcademico);
		modelo.addAttribute("mapaPaises", mapaPaises);
		modelo.addAttribute("mapaEstados", mapaEstados);
		modelo.addAttribute("mapaCiudades", mapaCiudades);
		modelo.addAttribute("mapaReligiones", mapaReligiones);
		
		return "portales/maestro/tarjeta";
	}
	
	@RequestMapping("/portales/maestro/tema")
	public String portalesMaestroTema(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPortalesMaestro|portalesMaestroTema");
		return "portales/maestro/tema";
	}
	
	@RequestMapping("/portales/maestro/transferir")
	public String portalesMaestroTransferir(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPortalesMaestro|portalesMaestroTransferir");
		return "portales/maestro/transferir";
	}
	
	@RequestMapping("/portales/maestro/transferir2")
	public String portalesMaestroTransferir2(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPortalesMaestro|portalesMaestroTransferir2");
		return "portales/maestro/transferir2";
	}
	
	@RequestMapping("/portales/maestro/unidad")
	public String portalesMaestroUnidad(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPortalesMaestro|portalesMaestroUnidad");
		return "portales/maestro/unidad";
	}
	

	@RequestMapping("/portales/maestro/unidadAct")
	public String portalesMaestroUnidadAct(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPortalesMaestro|portalesMaestroUnidadAct");
		return "portales/maestro/unidadAct";
	}
	
	@RequestMapping("/portales/maestro/unidadActEdita")
	public String portalesMaestroUnidadActEdita(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPortalesMaestro|portalesMaestroUnidadActEdita");
		return "portales/maestro/unidadActEdita";
	}
	
	@RequestMapping("/portales/maestro/unidadComp")
	public String portalesMaestroUnidadComp(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPortalesMaestro|portalesMaestroUnidadComp");
		return "portales/maestro/unidadComp";
	}
	
	@RequestMapping("/portales/maestro/verificarArchivo")
	public String portalesMaestroVerificarArchivo(HttpServletRequest request){
		//enviarConEnoc(request,"Error-aca.ContPortalesMaestro|portalesMaestroVerificarArchivo");
		return "portales/maestro/verificarArchivo";
	}	
	
	@RequestMapping("/portales/maestro/cita")
	public String portalesMaestroCita(HttpServletRequest request, Model modelo){
		String codigoEmpleado	= "0";
		HttpSession sesion		= request.getSession();
		String equipo 			= request.getParameter("Equipo")==null?"0":request.getParameter("Equipo");
		if (sesion!=null){
			codigoEmpleado		= (String) sesion.getAttribute("codigoPersonal");
		}
		
		boolean tieneHorario	= porHoraDao.tieneHorario(codigoEmpleado);
		String estado			= porRegistroDao.getEstado(codigoEmpleado);
		
		List<PorHora> lisEquipos = porHoraDao.lisEquipo(equipo," ORDER BY DIA, HORA");
		HashMap<String,String> mapMaestros 	= maestrosDao.mapMaestroNombre("NOMBRE");
		HashMap<String,String> mapSalones 	= porSalonDao.mapaAll();
		HashMap<String,String> mapEstados 	= porRegistroDao.mapaEstados();
		
		String nombreEmpleado = usuariosDao.getNombreUsuario(codigoEmpleado,"NOMBRE");
		
		modelo.addAttribute("lisEquipos",lisEquipos);
		modelo.addAttribute("nombreEmpleado",nombreEmpleado);
		modelo.addAttribute("mapMaestros",mapMaestros);
		modelo.addAttribute("tieneHorario",tieneHorario);
		modelo.addAttribute("mapSalones",mapSalones);
		modelo.addAttribute("estado",estado);
		modelo.addAttribute("mapEstados",mapEstados);
		
		return "portales/maestro/cita";
	}
	
	@RequestMapping("/portales/maestro/grabarCita")
	public String portalesMaestroGrabarCita(HttpServletRequest request, Model modelo){
		
		String folio 			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String equipo 			= request.getParameter("Equipo")==null?"0":request.getParameter("Equipo");
		HttpSession sesion		= request.getSession();
		PorHora hora 			= new PorHora();
		String codigoEmpleado	= "0";
		
        if (sesion!=null){
        	codigoEmpleado = (String)sesion.getAttribute("codigoPersonal");
        	if (porHoraDao.existeReg(folio)){
        		hora = porHoraDao.mapeaRegId(folio);        		
        		if (hora.getCodigoPersonal().equals("0")){
        			hora.setCodigoPersonal(codigoEmpleado);
        			porHoraDao.updateReg(hora);
        		}	
        	}
        }
        
		return "redirect:/portales/maestro/cita?Equipo="+equipo;
	}
	
	@RequestMapping("/portales/maestro/borrarCita")
	public String portalesMaestroBorrarCita(HttpServletRequest request, Model modelo){
		
		String folio 			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String equipo 			= request.getParameter("Equipo")==null?"0":request.getParameter("Equipo");
		HttpSession sesion		= request.getSession();
		PorHora hora 			= new PorHora();
		//String codigoEmpleado	= (String)sesion.getAttribute("codigoEmpleado");
		
        if (sesion!=null){        	
        	if (porHoraDao.existeReg(folio)){
        		hora = porHoraDao.mapeaRegId(folio);
        		hora.setCodigoPersonal("0");
        		porHoraDao.updateReg(hora);
        	}
        }
        
		return "redirect:/portales/maestro/cita?Equipo="+equipo;
	}
		
	@RequestMapping("/portales/maestro/pronMateria")
	public String portalesMaestroPronMateria(HttpServletRequest request, Model modelo){
		String cursoCargaId = request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		
		String cursoId 			= cargaGrupoCursoDao.cursoIdOrigen(cursoCargaId);
		String materia 			= mapaCursoDao.getMateria(cursoId);
		String maestro			= cargaGrupoDao.getCodigoPersonal(cursoCargaId);
		String maestroNombre	= usuariosDao.getNombreUsuario(maestro, "NOMBRE");
		
		String completoMateria 	= "No";
		String completoUnidades = "No";
		String completoEjes 	= "No";
		String completoValores 	= "No";
		String completoEsquema 	= "No";
		String completoBiblio 	= "No";

		PronMateria pronMateria =  new PronMateria();
		if (pronMateriaDao.existeReg(cursoCargaId)){
			pronMateria = pronMateriaDao.mapeaRegId(cursoCargaId);
		}
		
		if(!pronMateria.getHoraClase().equals("-") && !pronMateria.getHoraTutoria().equals("-") && !pronMateria.getCorreo().equals("-") 
				&& !pronMateria.getFormacion().equals("-") && !pronMateria.getEnfoque().equals("-") && !pronMateria.getDescripcion().equals("-")) {
			completoMateria = "Si";
		}
		
		MapaCurso mapaCurso 					= mapaCursoDao.mapeaRegId(cursoId);
		HashMap<String, PronUnidad> mapaUnidad 	= pronUnidadDao.mapaUnidad(cursoCargaId);
		
		List<MapaNuevoUnidad> unidades 			= mapaNuevoUnidadDao.getListCurso(mapaCurso.getCursoNuevo(), "ORDER BY UNIDAD_ID");
		if(unidades.size() == mapaUnidad.size()){
			completoUnidades = "Si";
		}
		
		PronEjes pronEjes = new PronEjes();
		
		if(pronEjesDao.existeReg(cursoCargaId)) {
			pronEjes = pronEjesDao.mapeaRegId(cursoCargaId);
			
			if(!pronEjes.getFe().equals("-") && !pronEjes.getPensamiento().equals("-") && !pronEjes.getAmbiente().equals("-") && !pronEjes.getLiderazgo().equals("-") 
					&& !pronEjes.getEmprendimiento().equals("-") && !pronEjes.getSustentabilidad().equals("-") && !pronEjes.getServicio().equals("-") && !pronEjes.getInvestigacion().equals("-")) {
				completoEjes 	= "Si";
			}
		}
		
		PronValor pronValor = new PronValor();
		
		if(pronValorDao.existeReg(cursoCargaId)) {
			pronValor = pronValorDao.mapeaRegId(cursoCargaId);
			
			if(!pronValor.getAmor().equals("-") && !pronValor.getLealtad().equals("-") && !pronValor.getConfianza().equals("-") && !pronValor.getReverencia().equals("-") 
					&& !pronValor.getObediencia().equals("-") && !pronValor.getArmonia().equals("-") && !pronValor.getRespeto().equals("-") && !pronValor.getPureza().equals("-")
					&& !pronValor.getHonestidad().equals("-") && !pronValor.getVeracidad().equals("-") && !pronValor.getContentamiento().equals("-") && !pronValor.getServicio().equals("-")) {
				completoValores 	= "Si";
			}
		}
		
		if( pronEsquemaDao.listaEsquema(cursoCargaId).size() > 0) {
			completoEsquema 	= "Si";
		}
		
		if( pronBiblioDao.listaBiblio(cursoCargaId).size() > 0) {
			completoBiblio 	= "Si";
		}
		
		modelo.addAttribute("pronMateria",pronMateria);
		modelo.addAttribute("materia",materia);	
		modelo.addAttribute("maestroNombre",maestroNombre);

		modelo.addAttribute("completoMateria",completoMateria);	
		modelo.addAttribute("completoUnidades",completoUnidades);	
		modelo.addAttribute("completoEjes",completoEjes);	
		modelo.addAttribute("completoValores",completoValores);
		modelo.addAttribute("completoEsquema",completoEsquema);	
		modelo.addAttribute("completoBiblio",completoBiblio);	
		modelo.addAttribute("envio", cargaPronDao.existeReg(cursoCargaId));	
		
		return "portales/maestro/pronMateria";
	}

	@RequestMapping("/portales/maestro/grabaPronMateria")
	public String grabaPronMateria(HttpServletRequest request, Model modelo){
		String cursoCargaId = request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		String cursoId 			= cargaGrupoCursoDao.cursoIdOrigen(cursoCargaId);
		
		String horaClase 	= request.getParameter("HorarioClase")==null?"0":request.getParameter("HorarioClase");
		String horaTutoria 	= request.getParameter("HorarioTutoria")==null?"0":request.getParameter("HorarioTutoria");
		String correo		= request.getParameter("Correo")==null?"0":request.getParameter("Correo");
		String formacion	= request.getParameter("Formacion")==null?"0":request.getParameter("Formacion");
		String descripcion	= request.getParameter("Descripcion")==null?"0":request.getParameter("Descripcion");
		String enfoque		= request.getParameter("Enfoque")==null?"0":request.getParameter("Enfoque");
		String lugar		= request.getParameter("Lugar")==null?"0":request.getParameter("Lugar");
		String especial		= request.getParameter("Especial")==null?"0":request.getParameter("Especial");
		String integridad	= request.getParameter("Integridad")==null?"0":request.getParameter("Integridad");
		
		PronMateria pronMateria = new PronMateria();
		
		pronMateria.setCursoCargaId(cursoCargaId);
		pronMateria.setCursoId(cursoId);
		pronMateria.setHoraClase(horaClase);
		pronMateria.setHoraTutoria(horaTutoria);
		pronMateria.setCorreo(correo);
		pronMateria.setFormacion(formacion);
		pronMateria.setDescripcion(descripcion);
		pronMateria.setEnfoque(enfoque);
		pronMateria.setLugar(lugar);
		pronMateria.setEspecial(especial);
		pronMateria.setIntegridad(integridad);
		
		if(!pronMateriaDao.existeReg(cursoCargaId)) {
			pronMateriaDao.insertReg(pronMateria);
		}else {
			pronMateriaDao.updateReg(pronMateria);
		}
		
		return "redirect:/portales/maestro/pronMateria?CursoCargaId="+cursoCargaId;
	}
	
	@RequestMapping("/portales/maestro/pronUnidad")
	public String pronUnidad(HttpServletRequest request, Model modelo){
		String cursoCargaId = request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		
		String cursoId 			= cargaGrupoCursoDao.cursoIdOrigen(cursoCargaId);
		String materia 			= mapaCursoDao.getMateria(cursoId);
		String maestro			= cargaGrupoDao.getCodigoPersonal(cursoCargaId);
		String maestroNombre	= usuariosDao.getNombreUsuario(maestro, "NOMBRE");
		
		String completoMateria 	= "No";
		String completoUnidades = "No";
		String completoEjes 	= "No";
		String completoValores 	= "No";
		String completoEsquema 	= "No";
		String completoBiblio 	= "No";
		
		PronMateria pronMateria =  new PronMateria();
		if (pronMateriaDao.existeReg(cursoCargaId)){
			pronMateria = pronMateriaDao.mapeaRegId(cursoCargaId);
		}
		
		if(!pronMateria.getHoraClase().equals("-") && !pronMateria.getHoraTutoria().equals("-") && !pronMateria.getCorreo().equals("-") 
				&& !pronMateria.getFormacion().equals("-") && !pronMateria.getEnfoque().equals("-") && !pronMateria.getDescripcion().equals("-")) {
			completoMateria = "Si";
		}
		
		MapaCurso mapaCurso = mapaCursoDao.mapeaRegId(cursoId);
		HashMap<String, PronUnidad> mapaUnidad = pronUnidadDao.mapaUnidad(cursoCargaId);
		
		List<MapaNuevoUnidad> unidades = mapaNuevoUnidadDao.getListCurso(mapaCurso.getCursoNuevo(), "ORDER BY ORDEN_UNIDAD('"+cursoCargaId+"',UNIDAD_ID)");
		
		if(unidades.size() == mapaUnidad.size()){
			completoUnidades = "Si";
		}
		
		HashMap<String,List<MapaNuevoProducto>> mapaListaProductos = new HashMap<String,List<MapaNuevoProducto>>();

		for(int i = 1; i < 20; i++) {
			List<MapaNuevoProducto> listaNuevoProducto = mapaNuevoProductoDao.getListUnidad(mapaCurso.getCursoNuevo(), String.valueOf(i), "");
			
			if(listaNuevoProducto.size() >= 1) {
				mapaListaProductos.put(String.valueOf(i), listaNuevoProducto);
			}
		}
		
		PronEjes pronEjes = new PronEjes();
		
		if(pronEjesDao.existeReg(cursoCargaId)) {
			pronEjes = pronEjesDao.mapeaRegId(cursoCargaId);
			
			if(!pronEjes.getFe().equals("-") && !pronEjes.getPensamiento().equals("-") && !pronEjes.getAmbiente().equals("-") && !pronEjes.getLiderazgo().equals("-") 
					&& !pronEjes.getEmprendimiento().equals("-") && !pronEjes.getSustentabilidad().equals("-") && !pronEjes.getServicio().equals("-") && !pronEjes.getInvestigacion().equals("-")) {
				completoEjes 	= "Si";
			}
		}
		
		PronValor pronValor = new PronValor();
		
		if(pronValorDao.existeReg(cursoCargaId)) {
			pronValor = pronValorDao.mapeaRegId(cursoCargaId);
			
			if(!pronValor.getAmor().equals("-") && !pronValor.getLealtad().equals("-") && !pronValor.getConfianza().equals("-") && !pronValor.getReverencia().equals("-") 
					&& !pronValor.getObediencia().equals("-") && !pronValor.getArmonia().equals("-") && !pronValor.getRespeto().equals("-") && !pronValor.getPureza().equals("-")
					&& !pronValor.getHonestidad().equals("-") && !pronValor.getVeracidad().equals("-") && !pronValor.getContentamiento().equals("-") && !pronValor.getServicio().equals("-")) {
				completoValores 	= "Si";
			}
		}
		
		if( pronEsquemaDao.listaEsquema(cursoCargaId).size() > 0) {
			completoEsquema 	= "Si";
		}
		
		if( pronBiblioDao.listaBiblio(cursoCargaId).size() > 0) {
			completoBiblio 	= "Si";
		}
		
		modelo.addAttribute("cursoCargaId",cursoCargaId);
		modelo.addAttribute("materia",materia);	
		modelo.addAttribute("maestroNombre",maestroNombre);
		modelo.addAttribute("unidades",unidades);	
		modelo.addAttribute("mapaUnidad",mapaUnidad);	
		modelo.addAttribute("mapaListaProductos",mapaListaProductos);	
		
		modelo.addAttribute("completoMateria",completoMateria);	
		modelo.addAttribute("completoUnidades",completoUnidades);	
		modelo.addAttribute("completoEjes",completoEjes);	
		modelo.addAttribute("completoValores",completoValores);
		modelo.addAttribute("completoEsquema",completoEsquema);
		modelo.addAttribute("completoBiblio",completoBiblio);	
		modelo.addAttribute("envio", cargaPronDao.existeReg(cursoCargaId));	
		
		return "portales/maestro/pronUnidad";
	}
	
	@RequestMapping("/portales/maestro/grabaPronUnidad")
	public String grabaPronUnidad(HttpServletRequest request, Model modelo){
		String cursoCargaId = request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");

		String unidadId 	= request.getParameter("UnidadId")==null?"0":request.getParameter("UnidadId");
		String aporte 		= request.getParameter("Aporte")==null?"0":request.getParameter("Aporte");
		String orden		= request.getParameter("Orden")==null?"0":request.getParameter("Orden");
		
		PronUnidad pronUnidad = new PronUnidad();
		
		pronUnidad.setCursoCargaId(cursoCargaId);
		pronUnidad.setUnidadId(unidadId);
		pronUnidad.setAporte(aporte);
		pronUnidad.setOrden(orden);
		
		if(!pronUnidadDao.existeReg(cursoCargaId, unidadId)){
			pronUnidadDao.insertReg(pronUnidad);
		}else {
			pronUnidadDao.updateReg(pronUnidad);
		}
		
		
		return "redirect:/portales/maestro/pronUnidad?CursoCargaId="+cursoCargaId;
	}

	@RequestMapping("/portales/maestro/pronEjes")
	public String pronEjes(HttpServletRequest request, Model modelo){
		String cursoCargaId = request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		
		String cursoId 			= cargaGrupoCursoDao.cursoIdOrigen(cursoCargaId);
		String materia 			= mapaCursoDao.getMateria(cursoId);
		String maestro			= cargaGrupoDao.getCodigoPersonal(cursoCargaId);
		String maestroNombre	= usuariosDao.getNombreUsuario(maestro, "NOMBRE");
		
		String completoMateria 	= "No";
		String completoUnidades = "No";
		String completoEjes 	= "No";
		String completoValores 	= "No";
		String completoEsquema 	= "No";
		String completoBiblio 	= "No";
		
		PronEjes pronEjes = new PronEjes();
		
		if(pronEjesDao.existeReg(cursoCargaId)) {
			pronEjes = pronEjesDao.mapeaRegId(cursoCargaId);
			
			if(!pronEjes.getFe().equals("-") && !pronEjes.getPensamiento().equals("-") && !pronEjes.getAmbiente().equals("-") && !pronEjes.getLiderazgo().equals("-") 
					&& !pronEjes.getEmprendimiento().equals("-") && !pronEjes.getSustentabilidad().equals("-") && !pronEjes.getServicio().equals("-") && !pronEjes.getInvestigacion().equals("-")) {
				completoEjes 	= "Si";
			}
		}
		
		PronMateria pronMateria =  pronMateriaDao.mapeaRegId(cursoCargaId);
		
		if(!pronMateria.getHoraClase().equals("-") && !pronMateria.getHoraTutoria().equals("-") && !pronMateria.getCorreo().equals("-") 
				&& !pronMateria.getFormacion().equals("-") && !pronMateria.getEnfoque().equals("-") && !pronMateria.getDescripcion().equals("-")) {
			completoMateria = "Si";
		}
		
		MapaCurso mapaCurso = mapaCursoDao.mapeaRegId(cursoId);
		HashMap<String, PronUnidad> mapaUnidad = pronUnidadDao.mapaUnidad(cursoCargaId);
		
		List<MapaNuevoUnidad> unidades = mapaNuevoUnidadDao.getListCurso(mapaCurso.getCursoNuevo(), "ORDER BY UNIDAD_ID");
		
		if(unidades.size() == mapaUnidad.size()){
			completoUnidades = "Si";
		}
		
		PronValor pronValor = new PronValor();
		
		if(pronValorDao.existeReg(cursoCargaId)) {
			pronValor = pronValorDao.mapeaRegId(cursoCargaId);
			
			if(!pronValor.getAmor().equals("-") && !pronValor.getLealtad().equals("-") && !pronValor.getConfianza().equals("-") && !pronValor.getReverencia().equals("-") 
					&& !pronValor.getObediencia().equals("-") && !pronValor.getArmonia().equals("-") && !pronValor.getRespeto().equals("-") && !pronValor.getPureza().equals("-")
					&& !pronValor.getHonestidad().equals("-") && !pronValor.getVeracidad().equals("-") && !pronValor.getContentamiento().equals("-") && !pronValor.getServicio().equals("-")) {
				completoValores 	= "Si";
			}
		}
		
		if( pronEsquemaDao.listaEsquema(cursoCargaId).size() > 0) {
			completoEsquema 	= "Si";
		}
		
		if( pronBiblioDao.listaBiblio(cursoCargaId).size() > 0) {
			completoBiblio 	= "Si";
		}
		
		modelo.addAttribute("pronEjes",pronEjes);	
		modelo.addAttribute("cursoCargaId",cursoCargaId);
		modelo.addAttribute("materia",materia);	
		modelo.addAttribute("maestroNombre",maestroNombre);

		modelo.addAttribute("completoMateria",completoMateria);	
		modelo.addAttribute("completoUnidades",completoUnidades);	
		modelo.addAttribute("completoEjes",completoEjes);	
		modelo.addAttribute("completoValores",completoValores);
		modelo.addAttribute("completoEsquema",completoEsquema);	
		modelo.addAttribute("completoBiblio",completoBiblio);	
		modelo.addAttribute("envio", cargaPronDao.existeReg(cursoCargaId));	
		
		return "portales/maestro/pronEjes";
	}
	
	@RequestMapping("/portales/maestro/grabaPronEjes")
	public String grabaPronEjes(HttpServletRequest request, Model modelo){
		String cursoCargaId = request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		
		String fe 				= request.getParameter("Fe")==null?"-":request.getParameter("Fe");
		String pensamiento 		= request.getParameter("Pensamiento")==null?"-":request.getParameter("Pensamiento");
		String ambiente			= request.getParameter("Ambiente")==null?"-":request.getParameter("Ambiente");
		String liderazgo		= request.getParameter("Liderazgo")==null?"-":request.getParameter("Liderazgo");
		String emprendimiento	= request.getParameter("Emprendimiento")==null?"-":request.getParameter("Emprendimiento");
		String sustentabilidad	= request.getParameter("Sustentabilidad")==null?"-":request.getParameter("Sustentabilidad");
		String servicio		 	= request.getParameter("Servicio")==null?"-":request.getParameter("Servicio");
		String investigacion 	= request.getParameter("Investigacion")==null?"-":request.getParameter("Investigacion");
		
		PronEjes pronEjes = new PronEjes();
		
		pronEjes.setCursoCargaId(cursoCargaId);
		pronEjes.setFe(fe);
		pronEjes.setPensamiento(pensamiento);
		pronEjes.setAmbiente(ambiente);
		pronEjes.setLiderazgo(liderazgo);
		pronEjes.setEmprendimiento(emprendimiento);
		pronEjes.setSustentabilidad(sustentabilidad);
		pronEjes.setServicio(servicio);
		pronEjes.setInvestigacion(investigacion);
		
		if(!pronEjesDao.existeReg(cursoCargaId)) {
			pronEjesDao.insertReg(pronEjes);
		}else {
			pronEjesDao.updateReg(pronEjes);
		}
		
		return "redirect:/portales/maestro/pronEjes?CursoCargaId="+cursoCargaId;
	}
	
	@RequestMapping("/portales/maestro/pronValor")
	public String pronValor(HttpServletRequest request, Model modelo){
		String cursoCargaId = request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		
		String cursoId 			= cargaGrupoCursoDao.cursoIdOrigen(cursoCargaId);
		String materia 			= mapaCursoDao.getMateria(cursoId);
		String maestro			= cargaGrupoDao.getCodigoPersonal(cursoCargaId);
		String maestroNombre	= usuariosDao.getNombreUsuario(maestro, "NOMBRE");
		
		String completoMateria 	= "No";
		String completoUnidades = "No";
		String completoEjes 	= "No";
		String completoValores 	= "No";
		String completoEsquema 	= "No";
		String completoBiblio 	= "No";
		
		PronValor pronValor = new PronValor();
		
		if(pronValorDao.existeReg(cursoCargaId)) {
			pronValor = pronValorDao.mapeaRegId(cursoCargaId);
			
			if(!pronValor.getAmor().equals("-") && !pronValor.getLealtad().equals("-") && !pronValor.getConfianza().equals("-") && !pronValor.getReverencia().equals("-") 
					&& !pronValor.getObediencia().equals("-") && !pronValor.getArmonia().equals("-") && !pronValor.getRespeto().equals("-") && !pronValor.getPureza().equals("-")
					&& !pronValor.getHonestidad().equals("-") && !pronValor.getVeracidad().equals("-") && !pronValor.getContentamiento().equals("-") && !pronValor.getServicio().equals("-")) {
				completoValores 	= "Si";
			}
		}

		PronEjes pronEjes = new PronEjes();
		
		if(pronEjesDao.existeReg(cursoCargaId)) {
			pronEjes = pronEjesDao.mapeaRegId(cursoCargaId);
			
			if(!pronEjes.getFe().equals("-") && !pronEjes.getPensamiento().equals("-") && !pronEjes.getAmbiente().equals("-") && !pronEjes.getLiderazgo().equals("-") 
					&& !pronEjes.getEmprendimiento().equals("-") && !pronEjes.getSustentabilidad().equals("-") && !pronEjes.getServicio().equals("-") && !pronEjes.getInvestigacion().equals("-")) {
				completoEjes 	= "Si";
			}
		}
		
		PronMateria pronMateria =  pronMateriaDao.mapeaRegId(cursoCargaId);
		
		if(!pronMateria.getHoraClase().equals("-") && !pronMateria.getHoraTutoria().equals("-") && !pronMateria.getCorreo().equals("-") 
				&& !pronMateria.getFormacion().equals("-") && !pronMateria.getEnfoque().equals("-") && !pronMateria.getDescripcion().equals("-")) {
			completoMateria = "Si";
		}
		
		MapaCurso mapaCurso = mapaCursoDao.mapeaRegId(cursoId);
		HashMap<String, PronUnidad> mapaUnidad = pronUnidadDao.mapaUnidad(cursoCargaId);
		
		List<MapaNuevoUnidad> unidades = mapaNuevoUnidadDao.getListCurso(mapaCurso.getCursoNuevo(), "ORDER BY UNIDAD_ID");
		
		if(unidades.size() == mapaUnidad.size()){
			completoUnidades = "Si";
		}
		
		if( pronEsquemaDao.listaEsquema(cursoCargaId).size() > 0) {
			completoEsquema 	= "Si";
		}
		
		if( pronBiblioDao.listaBiblio(cursoCargaId).size() > 0) {
			completoBiblio 	= "Si";
		}
		
		modelo.addAttribute("pronValor",pronValor);	
		modelo.addAttribute("cursoCargaId",cursoCargaId);
		modelo.addAttribute("materia",materia);	
		modelo.addAttribute("maestroNombre",maestroNombre);
		
		modelo.addAttribute("completoMateria",completoMateria);	
		modelo.addAttribute("completoUnidades",completoUnidades);	
		modelo.addAttribute("completoEjes",completoEjes);	
		modelo.addAttribute("completoValores",completoValores);
		modelo.addAttribute("completoEsquema",completoEsquema);
		modelo.addAttribute("completoBiblio",completoBiblio);	
		modelo.addAttribute("envio", cargaPronDao.existeReg(cursoCargaId));	
		
		return "portales/maestro/pronValor";
	}
	
	@RequestMapping("/portales/maestro/grabaPronValor")
	public String portalesMaestroGrabaPronValor(HttpServletRequest request, Model modelo){
		String cursoCargaId = request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		
		String amor 			= request.getParameter("Amor")==null?"-":request.getParameter("Amor");
		String lealtad 			= request.getParameter("Lealtad")==null?"-":request.getParameter("Lealtad");
		String confianza		= request.getParameter("Confianza")==null?"-":request.getParameter("Confianza");
		String reverencia 		= request.getParameter("Reverencia")==null?"-":request.getParameter("Reverencia");
		String obediencia 		= request.getParameter("Obediencia")==null?"-":request.getParameter("Obediencia");
		String armonia			= request.getParameter("Armonia")==null?"-":request.getParameter("Armonia");
		String respeto 			= request.getParameter("Respeto")==null?"-":request.getParameter("Respeto");
		String pureza 			= request.getParameter("Pureza")==null?"-":request.getParameter("Pureza");
		String honestidad 		= request.getParameter("Honestidad")==null?"-":request.getParameter("Honestidad");
		String veracidad 		= request.getParameter("Veracidad")==null?"-":request.getParameter("Veracidad");
		String contentamiento 	= request.getParameter("Contentamiento")==null?"-":request.getParameter("Contentamiento");
		String servicio 		= request.getParameter("Servicio")==null?"-":request.getParameter("Servicio");
		
		PronValor pronValor = new PronValor();
		
		pronValor.setCursoCargaId(cursoCargaId);
		pronValor.setAmor(amor);
		pronValor.setLealtad(lealtad);
		pronValor.setConfianza(confianza);
		pronValor.setReverencia(reverencia);
		pronValor.setObediencia(obediencia);
		pronValor.setArmonia(armonia);
		pronValor.setRespeto(respeto);
		pronValor.setPureza(pureza);
		pronValor.setHonestidad(honestidad);
		pronValor.setVeracidad(veracidad);
		pronValor.setContentamiento(contentamiento);
		pronValor.setServicio(servicio);
		
		if(!pronValorDao.existeReg(cursoCargaId)) {
			pronValorDao.insertReg(pronValor);
		}else {
			pronValorDao.updateReg(pronValor);
		}			
		
		return "redirect:/portales/maestro/pronValor?CursoCargaId="+cursoCargaId;
	}
	
	@RequestMapping("/portales/maestro/pronSemana")
	public String pronSemana(HttpServletRequest request, Model modelo){
		String cursoCargaId = request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		String unidadId		= request.getParameter("UnidadId")==null?"0":request.getParameter("UnidadId");
		String semanaId 	= request.getParameter("SemanaId")==null?"0":request.getParameter("SemanaId");
		String edita		= request.getParameter("Edita")==null?"N":request.getParameter("Edita");
		
		String cursoId 			= cargaGrupoCursoDao.cursoIdOrigen(cursoCargaId);
		
		MapaCurso mapaCurso = mapaCursoDao.mapeaRegId(cursoId);
		
		MapaNuevoUnidad mapaNuevoUnidad = mapaNuevoUnidadDao.mapeaRegId(mapaCurso.getCursoNuevo(), unidadId);
		List<MapaNuevoActividad> listNuevoActividades = mapaNuevoActividadDao.getListUnidad(mapaCurso.getCursoNuevo(), unidadId,"ORDER BY ACTIVIDAD_ID");
		
		PronSemana pronSemana = new PronSemana();
		if(edita.equals("S")) {
			pronSemana = pronSemanaDao.mapeaRegId(cursoCargaId, unidadId, semanaId);
		}
		
		modelo.addAttribute("pronSemana",pronSemana);	
		modelo.addAttribute("cursoCargaId",cursoCargaId);
		modelo.addAttribute("unidadId",unidadId);	
		modelo.addAttribute("mapaNuevoUnidad",mapaNuevoUnidad);
		modelo.addAttribute("listNuevoActividades",listNuevoActividades);
		modelo.addAttribute("unidad",mapaNuevoUnidadDao.mapeaRegId(mapaCurso.getCursoNuevo(), unidadId));
		modelo.addAttribute("semanasUnidad", pronSemanaDao.listaSemanasUnidad(cursoCargaId, unidadId));
		modelo.addAttribute("envio", cargaPronDao.existeReg(cursoCargaId));	
		
		return "portales/maestro/pronSemana";
	}
	
	@RequestMapping("/portales/maestro/grabaPronSemana")
	public String grabaPronSemana(HttpServletRequest request, Model modelo){
		String cursoCargaId = request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");	
		String unidadId 	= request.getParameter("UnidadId")==null?"0":request.getParameter("UnidadId");
		String semanaId 	= request.getParameter("SemanaId")==null?"0":request.getParameter("SemanaId");
		
		String semanaNombre	= request.getParameter("SemanaNombre")==null?"-":request.getParameter("SemanaNombre");
		String contenido 	= request.getParameter("Contenido")==null?"-":request.getParameter("Contenido");
		String actividades 	= request.getParameter("Actividades")==null?"-":request.getParameter("Actividades");
		String evidencias	= request.getParameter("Evidencias")==null?"-":request.getParameter("Evidencias");
		String orden	 	= request.getParameter("Orden")==null?"0":request.getParameter("Orden");
		
		PronSemana pronSemana = new PronSemana();

		if(semanaId.equals("0")){
			semanaId = pronSemanaDao.getSemanaId(cursoCargaId, unidadId);
		}
		
		pronSemana.setCursoCargaId(cursoCargaId);
		pronSemana.setUnidadId(unidadId);
		pronSemana.setSemanaId(semanaId);
		pronSemana.setSemanaNombre(semanaNombre);
		pronSemana.setContenido(contenido);
		pronSemana.setActividades(actividades);
		pronSemana.setEvidencias(evidencias);
		pronSemana.setOrden(orden);
		
		if(!pronSemanaDao.existeReg(cursoCargaId,unidadId,semanaId)) {
			pronSemanaDao.insertReg(pronSemana);
		}else {
			pronSemanaDao.updateReg(pronSemana);
		}			
		
		return "redirect:/portales/maestro/pronSemana?CursoCargaId="+cursoCargaId+"&UnidadId="+unidadId;
	}
	
	@RequestMapping("/portales/maestro/editaPronSemana")
	public String editaPronSemana(HttpServletRequest request, Model modelo){
		String cursoCargaId = request.getParameter("CursoCargaId")==null?"-":request.getParameter("CursoCargaId");
		String unidadId 	= request.getParameter("UnidadId")==null?"0":request.getParameter("UnidadId");
		String semanaId 	= request.getParameter("SemanaId")==null?"0":request.getParameter("SemanaId");
		String edita		= "N";
		
		if(pronSemanaDao.existeReg(cursoCargaId, unidadId, semanaId)) {
			edita = "S";
		}	
		
		return "redirect:/portales/maestro/pronSemana?CursoCargaId="+cursoCargaId+"&UnidadId="+unidadId+"&SemanaId="+semanaId+"&Edita="+edita;
	}
	
	@RequestMapping("/portales/maestro/borraPronSemana")
	public String borraPronSemana(HttpServletRequest request, Model modelo){
		String cursoCargaId = request.getParameter("CursoCargaId")==null?"-":request.getParameter("CursoCargaId");
		String unidadId 	= request.getParameter("UnidadId")==null?"0":request.getParameter("UnidadId");
		String semanaId 	= request.getParameter("SemanaId")==null?"0":request.getParameter("SemanaId");
		
		if(pronSemanaDao.existeReg(cursoCargaId, unidadId, semanaId)) {
			pronSemanaDao.deleteReg(cursoCargaId, unidadId, semanaId);
		}	
		
		return "redirect:/portales/maestro/pronSemana?CursoCargaId="+cursoCargaId+"&UnidadId="+unidadId;
	}
	
	@RequestMapping("/portales/maestro/pronEsquema")
	public String pronEsquema(HttpServletRequest request, Model modelo){
		String cursoCargaId = request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		
		String cursoId 			= cargaGrupoCursoDao.cursoIdOrigen(cursoCargaId);
		String materia 			= mapaCursoDao.getMateria(cursoId);
		String maestro			= cargaGrupoDao.getCodigoPersonal(cursoCargaId);
		String maestroNombre	= usuariosDao.getNombreUsuario(maestro, "NOMBRE");

		
		String completoMateria 	= "No";
		String completoUnidades = "No";
		String completoEjes 	= "No";
		String completoValores 	= "No";
		String completoEsquema 	= "No";
		String completoBiblio 	= "No";
		
		PronEsquema pronEsquema = new PronEsquema();
		
		if( pronEsquemaDao.listaEsquema(cursoCargaId).size() > 0) {
			completoEsquema 	= "Si";
		}
		
		if( pronBiblioDao.listaBiblio(cursoCargaId).size() > 0) {
			completoBiblio 	= "Si";
		}
		
		PronValor pronValor = new PronValor();
		
		if(pronValorDao.existeReg(cursoCargaId)) {
			pronValor = pronValorDao.mapeaRegId(cursoCargaId);
			
			if(!pronValor.getAmor().equals("-") && !pronValor.getLealtad().equals("-") && !pronValor.getConfianza().equals("-") && !pronValor.getReverencia().equals("-") 
					&& !pronValor.getObediencia().equals("-") && !pronValor.getArmonia().equals("-") && !pronValor.getRespeto().equals("-") && !pronValor.getPureza().equals("-")
					&& !pronValor.getHonestidad().equals("-") && !pronValor.getVeracidad().equals("-") && !pronValor.getContentamiento().equals("-") && !pronValor.getServicio().equals("-")) {
				completoValores 	= "Si";
			}
		}

		PronEjes pronEjes = new PronEjes();
		
		if(pronEjesDao.existeReg(cursoCargaId)) {
			pronEjes = pronEjesDao.mapeaRegId(cursoCargaId);
			
			if(!pronEjes.getFe().equals("-") && !pronEjes.getPensamiento().equals("-") && !pronEjes.getAmbiente().equals("-") && !pronEjes.getLiderazgo().equals("-") 
					&& !pronEjes.getEmprendimiento().equals("-") && !pronEjes.getSustentabilidad().equals("-") && !pronEjes.getServicio().equals("-") && !pronEjes.getInvestigacion().equals("-")) {
				completoEjes 	= "Si";
			}
		}
		
		PronMateria pronMateria =  pronMateriaDao.mapeaRegId(cursoCargaId);
		
		if(!pronMateria.getHoraClase().equals("-") && !pronMateria.getHoraTutoria().equals("-") && !pronMateria.getCorreo().equals("-") 
				&& !pronMateria.getFormacion().equals("-") && !pronMateria.getEnfoque().equals("-") && !pronMateria.getDescripcion().equals("-")) {
			completoMateria = "Si";
		}
		
		MapaCurso mapaCurso = mapaCursoDao.mapeaRegId(cursoId);
		HashMap<String, PronUnidad> mapaUnidad = pronUnidadDao.mapaUnidad(cursoCargaId);
		
		List<MapaNuevoUnidad> unidades = mapaNuevoUnidadDao.getListCurso(mapaCurso.getCursoNuevo(), "ORDER BY UNIDAD_ID");
		
		if(unidades.size() == mapaUnidad.size()){
			completoUnidades = "Si";
		}
		
		modelo.addAttribute("pronEsquema",pronEsquema);	
		modelo.addAttribute("cursoCargaId",cursoCargaId);
		modelo.addAttribute("materia",materia);	
		modelo.addAttribute("maestroNombre",maestroNombre);
		modelo.addAttribute("listaEsquema", pronEsquemaDao.listaEsquema(cursoCargaId));
		
		modelo.addAttribute("completoMateria",completoMateria);	
		modelo.addAttribute("completoUnidades",completoUnidades);	
		modelo.addAttribute("completoEjes",completoEjes);	
		modelo.addAttribute("completoValores",completoValores);
		modelo.addAttribute("completoEsquema",completoEsquema);
		modelo.addAttribute("completoBiblio",completoBiblio);	
		modelo.addAttribute("envio", cargaPronDao.existeReg(cursoCargaId));	
		
		return "portales/maestro/pronEsquema";
	}
	
	@RequestMapping("/portales/maestro/grabaPronEsquema")
	public String grabaPronEsquema(HttpServletRequest request, Model modelo){
		String cursoCargaId = request.getParameter("CursoCargaId")==null?"-":request.getParameter("CursoCargaId");
		
		String estrategiaNombre	= request.getParameter("EstrategiaNombre")==null?"-":request.getParameter("EstrategiaNombre");
		String valor			= request.getParameter("Valor")==null?"-":request.getParameter("Valor");
		String tipo				= request.getParameter("Tipo")==null?"-":request.getParameter("Tipo");
		String orden	 		= request.getParameter("Orden")==null?"0":request.getParameter("Orden");

		String estrategiaId 	= pronEsquemaDao.getEsquemaId(cursoCargaId);
		
		PronEsquema pronEsquema = new PronEsquema();
		
		pronEsquema.setCursoCargaId(cursoCargaId);
		pronEsquema.setEstrategiaId(estrategiaId);
		pronEsquema.setEstrategiaNombre(estrategiaNombre);
		pronEsquema.setValor(valor);
		pronEsquema.setTipo(tipo);
		pronEsquema.setOrden(orden);
		
		if(!pronEsquemaDao.existeReg(cursoCargaId, estrategiaId)) {
			pronEsquemaDao.insertReg(pronEsquema);
		}else {
			pronEsquemaDao.updateReg(pronEsquema);
		}			
		
		return "redirect:/portales/maestro/pronEsquema?CursoCargaId="+cursoCargaId;
	}
	
	@RequestMapping("/portales/maestro/borraPronEsquema")
	public String borraPronEsquema(HttpServletRequest request, Model modelo){
		String cursoCargaId = request.getParameter("CursoCargaId")==null?"-":request.getParameter("CursoCargaId");
		String estrategiaId 	= request.getParameter("EstrategiaId")==null?"0":request.getParameter("EstrategiaId");
		
		if(pronEsquemaDao.existeReg(cursoCargaId, estrategiaId)) {
			pronEsquemaDao.deleteReg(cursoCargaId, estrategiaId);
		}	
		
		return "redirect:/portales/maestro/pronEsquema?CursoCargaId="+cursoCargaId;
	}

	@RequestMapping("/portales/maestro/pronBiblio")
	public String pronBiblio(HttpServletRequest request, Model modelo){
		String cursoCargaId = request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		
		String cursoId 			= cargaGrupoCursoDao.cursoIdOrigen(cursoCargaId);
		String cursoNuevo 		= mapaCursoDao.getCursoNuevo(cursoId);
		String materia 			= mapaCursoDao.getMateria(cursoId);
		String maestro			= cargaGrupoDao.getCodigoPersonal(cursoCargaId);
		String maestroNombre	= usuariosDao.getNombreUsuario(maestro, "NOMBRE");
		
		String completoMateria 	= "No";
		String completoUnidades = "No";
		String completoEjes 	= "No";
		String completoValores 	= "No";
		String completoEsquema 	= "No";
		String completoBiblio 	= "No";
		
		List<MapaNuevoBibliografia> lisBiblio = new ArrayList<MapaNuevoBibliografia>();
		
		if( mapaNuevoBibliografiaDao.getListCurso(cursoNuevo, "ORDER BY BIBLIOGRAFIA").size() > 0) {
			lisBiblio = mapaNuevoBibliografiaDao.getListCurso(cursoNuevo, "ORDER BY BIBLIOGRAFIA");
		}
		
		PronBiblio pronBiblio = new PronBiblio();
		
		if( pronBiblioDao.listaBiblio(cursoCargaId).size() > 0) {
			completoBiblio 	= "Si";
		}

		PronValor pronValor = new PronValor();
		
		if(pronValorDao.existeReg(cursoCargaId)) {
			pronValor = pronValorDao.mapeaRegId(cursoCargaId);
			
			if(!pronValor.getAmor().equals("-") && !pronValor.getLealtad().equals("-") && !pronValor.getConfianza().equals("-") && !pronValor.getReverencia().equals("-") 
					&& !pronValor.getObediencia().equals("-") && !pronValor.getArmonia().equals("-") && !pronValor.getRespeto().equals("-") && !pronValor.getPureza().equals("-")
					&& !pronValor.getHonestidad().equals("-") && !pronValor.getVeracidad().equals("-") && !pronValor.getContentamiento().equals("-") && !pronValor.getServicio().equals("-")) {
				completoValores 	= "Si";
			}
		}

		PronEjes pronEjes = new PronEjes();
		
		if(pronEjesDao.existeReg(cursoCargaId)) {
			pronEjes = pronEjesDao.mapeaRegId(cursoCargaId);
			
			if(!pronEjes.getFe().equals("-") && !pronEjes.getPensamiento().equals("-") && !pronEjes.getAmbiente().equals("-") && !pronEjes.getLiderazgo().equals("-") 
					&& !pronEjes.getEmprendimiento().equals("-") && !pronEjes.getSustentabilidad().equals("-") && !pronEjes.getServicio().equals("-") && !pronEjes.getInvestigacion().equals("-")) {
				completoEjes 	= "Si";
			}
		}
		
		PronMateria pronMateria =  pronMateriaDao.mapeaRegId(cursoCargaId);
		
		if(!pronMateria.getHoraClase().equals("-") && !pronMateria.getHoraTutoria().equals("-") && !pronMateria.getCorreo().equals("-") 
				&& !pronMateria.getFormacion().equals("-") && !pronMateria.getEnfoque().equals("-") && !pronMateria.getDescripcion().equals("-")) {
			completoMateria = "Si";
		}
		
		MapaCurso mapaCurso = mapaCursoDao.mapeaRegId(cursoId);
		HashMap<String, PronUnidad> mapaUnidad = pronUnidadDao.mapaUnidad(cursoCargaId);
		
		List<MapaNuevoUnidad> unidades = mapaNuevoUnidadDao.getListCurso(mapaCurso.getCursoNuevo(), "ORDER BY UNIDAD_ID");
		
		if(unidades.size() == mapaUnidad.size()){
			completoUnidades = "Si";
		}
		
		if( pronEsquemaDao.listaEsquema(cursoCargaId).size() > 0) {
			completoEsquema 	= "Si";
		}
		
		modelo.addAttribute("pronBiblio",pronBiblio);	
		modelo.addAttribute("cursoCargaId",cursoCargaId);
		modelo.addAttribute("materia",materia);
		modelo.addAttribute("maestroNombre",maestroNombre);	
		modelo.addAttribute("listaBiblio", pronBiblioDao.listaBiblio(cursoCargaId));
		modelo.addAttribute("lisBiblio",lisBiblio);
		
		modelo.addAttribute("completoMateria",completoMateria);	
		modelo.addAttribute("completoUnidades",completoUnidades);	
		modelo.addAttribute("completoEjes",completoEjes);	
		modelo.addAttribute("completoValores",completoValores);
		modelo.addAttribute("completoEsquema",completoEsquema);	
		modelo.addAttribute("completoBiblio",completoBiblio);	
		modelo.addAttribute("envio", cargaPronDao.existeReg(cursoCargaId));	
		
		return "portales/maestro/pronBiblio";
	}
	
	@RequestMapping("/portales/maestro/grabaPronBiblio")
	public String grabaPronBiblio(HttpServletRequest request, Model modelo){
		String cursoCargaId = request.getParameter("CursoCargaId")==null?"-":request.getParameter("CursoCargaId");
		
		String biblioNombre	= request.getParameter("BiblioNombre")==null?"-":request.getParameter("BiblioNombre");
		String orden	 	= request.getParameter("Orden")==null?"0":request.getParameter("Orden");

		String biblioId 	= pronBiblioDao.getBiblioId(cursoCargaId);
		
		PronBiblio pronBiblio = new PronBiblio();
		
		pronBiblio.setCursoCargaId(cursoCargaId);
		pronBiblio.setBiblioId(biblioId);
		pronBiblio.setBiblioNombre(biblioNombre);
		pronBiblio.setOrden(orden);
		
		if(!pronBiblioDao.existeReg(cursoCargaId,biblioId)) {
			pronBiblioDao.insertReg(pronBiblio);
		}else {
			pronBiblioDao.updateReg(pronBiblio);
		}			
		
		return "redirect:/portales/maestro/pronBiblio?CursoCargaId="+cursoCargaId;
	}
	
	@RequestMapping("/portales/maestro/borraPronBiblio")
	public String borraPronBiblio(HttpServletRequest request, Model modelo){
		String cursoCargaId = request.getParameter("CursoCargaId")==null?"-":request.getParameter("CursoCargaId");
		
		String biblioId	= request.getParameter("BiblioId")==null?"0":request.getParameter("BiblioId");
		
		if(pronBiblioDao.existeReg(cursoCargaId,biblioId)) {
			pronBiblioDao.deleteReg(cursoCargaId,biblioId);
		}		
		
		return "redirect:/portales/maestro/pronBiblio?CursoCargaId="+cursoCargaId;
	}
	
	@RequestMapping("/portales/maestro/muestraEditaPronBiblio")
	public String muestraEditaPronBiblio(HttpServletRequest request, Model modelo){
		String cursoCargaId = request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		
		String biblioId			= request.getParameter("BiblioId")==null?"0":request.getParameter("BiblioId");
		
		PronBiblio pronBiblio 	= pronBiblioDao.mapeaRegId(cursoCargaId, biblioId);
		
		modelo.addAttribute("pronBiblio",pronBiblio);	
		
		return "portales/maestro/editaPronBiblio";
	}

	@RequestMapping("/portales/maestro/editaPronBiblio")
	public String editaPronBiblio(HttpServletRequest request, Model modelo){
		String cursoCargaId = request.getParameter("CursoCargaId")==null?"-":request.getParameter("CursoCargaId");
		
		String biblioId		= request.getParameter("BiblioId")==null?"0":request.getParameter("BiblioId");
		String biblioNombre	= request.getParameter("BiblioNombre")==null?"-":request.getParameter("BiblioNombre");
		String orden	 	= request.getParameter("Orden")==null?"0":request.getParameter("Orden");

		PronBiblio pronBiblio 	= pronBiblioDao.mapeaRegId(cursoCargaId, biblioId);
		
		pronBiblio.setBiblioNombre(biblioNombre);
		pronBiblio.setOrden(orden);
				
		if(pronBiblioDao.existeReg(cursoCargaId,biblioId)) {
			pronBiblioDao.updateReg(pronBiblio);
		}			
		
		return "redirect:/portales/maestro/pronBiblio?CursoCargaId="+cursoCargaId;
	}
	
	@RequestMapping("/portales/maestro/pdfPron")
	public String imprimirProntuario(HttpServletRequest request, Model modelo){
		String cursoCargaId = request.getParameter("CursoCargaId")==null?"-":request.getParameter("CursoCargaId");
		
		String cursoId 		= cargaGrupoCursoDao.cursoIdOrigen(cursoCargaId);
		String materia 		= mapaCursoDao.getMateria(cursoId);
		String carreraId	= cargaGrupoDao.getCarreraId(cursoCargaId);
		String codigoMaestro		= "0";
		String nombreMaestro		= "";
		CargaGrupo cargaGrupo = new CargaGrupo();
		if (cargaGrupoDao.existeReg(cursoCargaId) ) {
			cargaGrupo = cargaGrupoDao.mapeaRegId(cursoCargaId);
			
			codigoMaestro = cargaGrupo.getCodigoPersonal();
			nombreMaestro = usuariosDao.getNombreUsuario(codigoMaestro, "NOMBRE");
		}
		
		String nombreCarrera 	= catCarreraDao.getNombreCarrera(carreraId);
		CatCarrera catCarrera 	= catCarreraDao.mapeaRegIdsinFac(carreraId);
		CatFacultad catFacultad = catFacultadDao.mapeaRegId(catCarrera.getFacultadId());
		
		MapaCurso mapaCurso 	= new MapaCurso();
		if (mapaCursoDao.existeReg(cursoId)) {
			mapaCurso = mapaCursoDao.mapeaRegId(cursoId); 
		}
		
		MapaNuevoCurso mapaNuevoCurso = new MapaNuevoCurso();
		if (mapaNuevoCursoDao.existeReg(mapaCurso.getCursoNuevo()) ) {
			mapaNuevoCurso = mapaNuevoCursoDao.mapeaRegId(mapaCurso.getCursoNuevo());
		}
		
		List<MapaNuevoProducto> productos 		= mapaNuevoProductoDao.lisCurso(mapaCurso.getCursoNuevo(), " ORDER BY UNIDAD_ID, PRODUCTO_ID");
				
		HashMap<String, PronUnidad> mapaUnidad 	= pronUnidadDao.mapaUnidad(cursoCargaId);
		List<MapaNuevoUnidad> unidades 			= mapaNuevoUnidadDao.getListCurso(mapaCurso.getCursoNuevo(), " ORDER BY ORDEN_UNIDAD('"+cursoCargaId+"',UNIDAD_ID)");
		
		PronMateria pronMateria					= pronMateriaDao.mapeaRegId(cursoCargaId);
		PronValor pronValor						= pronValorDao.mapeaRegId(cursoCargaId);
		List<PronEsquema> lisEsquema			= pronEsquemaDao.listaEsquema(cursoCargaId);
		List<PronBiblio> lisBiblio				= pronBiblioDao.listaBiblio(cursoCargaId);
		PronEjes pronEjes =  new PronEjes();		
		if(pronEjesDao.existeReg(cursoCargaId)) {
			pronEjes = pronEjesDao.mapeaRegId(cursoCargaId);			
		}
		
		Carga carga = cargaDao.mapeaRegId(cargaGrupo.getCargaId());
		
		modelo.addAttribute("carga",carga);	
		modelo.addAttribute("nombreFacultad",catFacultad.getNombreFacultad());	
		modelo.addAttribute("nombreCarrera",nombreCarrera);	
		modelo.addAttribute("materia",materia);	
		modelo.addAttribute("unidades",unidades);			
		modelo.addAttribute("semanasUnidad", pronSemanaDao.listaSemanasUnidad(cursoCargaId));
		modelo.addAttribute("mapaCurso", mapaCurso);
		modelo.addAttribute("mapaNuevoCurso", mapaNuevoCurso);
		modelo.addAttribute("catCarrera", catCarrera);
		modelo.addAttribute("pronMateria", pronMateria);
		modelo.addAttribute("pronValor", pronValor);
		modelo.addAttribute("mapaUnidad",mapaUnidad);	
		modelo.addAttribute("pronEjes",pronEjes);
		modelo.addAttribute("lisEsquema",lisEsquema);
		modelo.addAttribute("lisBiblio",lisBiblio);
		modelo.addAttribute("codigoMaestro",codigoMaestro);
		modelo.addAttribute("nombreMaestro",nombreMaestro);
		modelo.addAttribute("productos",productos);
		
		return "portales/maestro/pdfPron";
		
	}
	
	@RequestMapping("/portales/maestro/enviarCursoCargaId")
	public String enviarCursoCargaId(HttpServletRequest request){
		String cursoCargaId 	= request.getParameter("CursoCargaId")==null?"-":request.getParameter("CursoCargaId");
		String origen 			= request.getParameter("Origen")==null?"pronMateria":request.getParameter("Origen");
		
		CargaPron cargaPron 	= new CargaPron();
		cargaPron.setCursoCargaId(cursoCargaId);
		
		if(!cargaPronDao.existeReg(cursoCargaId)) {
			cargaPronDao.insertReg(cargaPron);
		}		
		
		return "redirect:/portales/maestro/"+origen+"?CursoCargaId="+cursoCargaId;
	}
	
	@RequestMapping("/portales/maestro/nuevoHorario")
	public String portalesMaestroNuevoHorario(HttpServletRequest request, Model modelo){
		
		String cargaId 			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String bloqueId 		= request.getParameter("BloqueId")==null?"1":request.getParameter("BloqueId");
		String horarioId		= request.getParameter("HorarioId")==null?"0":request.getParameter("HorarioId");
		String codigoEmpleado 	= "";				
		
		HttpSession sesion		= request.getSession();
		if (sesion!=null){
			codigoEmpleado		= (String) sesion.getAttribute("codigoPersonal");			
		}
		
		List<CargaBloque> lisBloques	= cargaBloqueDao.lisBloquesDelMaestro(codigoEmpleado, cargaId, " ORDER BY BLOQUE_ID");
		boolean existe = false;
		for(CargaBloque bloque: lisBloques){
			if (bloque.getBloqueId().equals(bloqueId)) {
				existe = true;
				break;
			}
		}
		if (!existe && lisBloques.size()>=1) bloqueId = lisBloques.get(0).getBloqueId();
		
		List<CatHorario> lisHorarios = catHorarioDao.lisHorariosPorMaestro(codigoEmpleado, bloqueId, cargaId, "");
		boolean existeHora = false;
		for(CatHorario hora: lisHorarios){
			if (hora.getHorarioId().equals(horarioId)) {
				existeHora = true;
				break;
			}
		}
		if (!existeHora && lisHorarios.size() >= 1){
			horarioId = lisHorarios.get(0).getHorarioId();
		}
		
		List<String> turno = catHorarioFacultadDao.getTurno(horarioId, " ORDER BY TURNO");
		List<CatHorarioFacultad> listHorario = new ArrayList<CatHorarioFacultad>();
		HashMap<String, List<CatHorarioFacultad>> mapaListaHorario = new HashMap<String, List<CatHorarioFacultad>>(); 
		
		for(String tur : turno){
			listHorario = catHorarioFacultadDao.getListaTurno(horarioId, tur, " ORDER BY TURNO, PERIODO");
			mapaListaHorario.put(horarioId+tur, listHorario);
		}
		
		Maestros maestro = maestrosDao.mapeaRegId(codigoEmpleado);
		
		HashMap<String, String> mapaFacultadPorHorario 		= catFacultadDao.mapaFacultadPorHorario();
		HashMap<String, Hora> mapHoras 						= horaDao.mapaHorasDelMaestro(codigoEmpleado, cargaId, bloqueId);
		HashMap<String, List<Hora>> mapHorasPortalMaestro	= horaDao.mapaHorasDelMaestroPortalMaestro(codigoEmpleado, cargaId, bloqueId);
		HashMap<String, String> mapCursos 					= horaDao.mapaCursosDelMaestro(codigoEmpleado, cargaId);
		HashMap<String, CargaGrupo> mapGrupos				= cargaGrupoDao.mapaGruposDelMaestro(codigoEmpleado, cargaId);
		HashMap<String,String> mapaSalones 					= catSalonDao.mapaNombresSalones();
		
		modelo.addAttribute("maestro", maestro);
		modelo.addAttribute("bloqueId", bloqueId);
		modelo.addAttribute("horarioId", horarioId);		
		modelo.addAttribute("lisBloques", lisBloques);
		modelo.addAttribute("turno", turno);
		modelo.addAttribute("mapaFacultadPorHorario", mapaFacultadPorHorario);
		modelo.addAttribute("mapHoras", mapHoras);
		modelo.addAttribute("mapCursos", mapCursos);
		modelo.addAttribute("mapGrupos", mapGrupos);
		modelo.addAttribute("mapaSalones", mapaSalones);
		modelo.addAttribute("mapaListaHorario", mapaListaHorario);
		modelo.addAttribute("mapHorasPortalMaestro", mapHorasPortalMaestro);
		modelo.addAttribute("lisHorarios", lisHorarios);
		
		return "portales/maestro/nuevoHorario";
	}
	
	@RequestMapping("/portales/maestro/nuevoHorarioMateria")
	public String datosProfesorCursosNuevoHorarioMateria(HttpServletRequest request, Model modelo){
		String cursoCargaId 		= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		
		List<CargaHorario> lisHorarios 				= cargaHorarioDao.getLista(cursoCargaId, " ORDER BY DIA, HORA_INICIO, MINUTO_INICIO");		
		HashMap<String,CatEdificio> mapaEdificios 	= catEdificioDao.mapaEdificios();
		HashMap<String,CatSalon> mapaSalones 		= catSalonDao.getMapAll("");
		
		modelo.addAttribute("lisHorarios",lisHorarios);
		modelo.addAttribute("mapaEdificios",mapaEdificios);
		modelo.addAttribute("mapaSalones",mapaSalones);
		
		return "portales/maestro/nuevoHorarioMateria";
	}
	
	@RequestMapping("/portales/maestro/archivoEval")
	public String portalesMaestroArchivoEval(HttpServletRequest request, Model modelo){
		String cursoCargaId					= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		String evaluacionId					= request.getParameter("EvaluacionId")==null?"0":request.getParameter("EvaluacionId");
		String opcion						= request.getParameter("Opcion")==null?"1":request.getParameter("Opcion");
		String evaluacionNombre 			= cargaGrupoEvaluacionDao.getNombreEvaluacion(cursoCargaId, evaluacionId); 
		String cursoId						= cargaGrupoCursoDao.cursoIdOrigen(cursoCargaId);
		String materiaNombre 				= mapaCursoDao.getNombreCurso(cursoId);
		String codigoPersonal 				= "0";
		HashMap<String,String> mapaAlumnos	= alumPersonalDao.mapAlumnosMateria(cursoCargaId);
		
		HttpSession sesion		= request.getSession();
		if (sesion!=null){
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
		}	
		
		String maestroNombre 	= maestrosDao.getNombreMaestro(codigoPersonal, "NOMBRE");
		
		List<ArchivosAlumno> lisArchivos		= archivosAlumnoDao.lisPorEvaluacion(evaluacionId, " ORDER BY FECHA");	
		
		HashMap<String,CargaGrupoActividad> mapActividadesEnMateria 	= cargaGrupoActividadDao.mapaActividadesPorMateria(cursoCargaId);
		HashMap<String,KrdxAlumnoEval> mapAlumEval = krdxAlumnoEvalDao.mapAlumEval(cursoCargaId);
		
		modelo.addAttribute("cursoCargaId", cursoCargaId);
		modelo.addAttribute("evaluacionId", evaluacionId);
		modelo.addAttribute("materiaNombre", materiaNombre);
		modelo.addAttribute("maestroNombre", maestroNombre);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("evaluacionNombre", evaluacionNombre);
		modelo.addAttribute("opcion", opcion);
		
		modelo.addAttribute("lisArchivos", lisArchivos);
		modelo.addAttribute("mapAlumEval", mapAlumEval); 
		modelo.addAttribute("mapActividadesEnMateria", mapActividadesEnMateria);
		
		return "portales/maestro/archivoEval";
	}
	
	@RequestMapping("/portales/maestro/archivoAlum")
	public String portalesMaestroArchivoAlum(HttpServletRequest request, Model modelo){
		String cursoCargaId		= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		String codigoAlumno		= request.getParameter("CodigoAlumno")==null?"0":request.getParameter("CodigoAlumno");	
		
		String alumnoNombre		= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
		String cursoId			= cargaGrupoCursoDao.cursoIdOrigen(cursoCargaId);
		String materiaNombre 	= mapaCursoDao.getNombreCurso(cursoId);
		String codigoPersonal 	= "0";
		
		HttpSession sesion		= request.getSession();
		if (sesion!=null){
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
		}		
		String maestroNombre 	= maestrosDao.getNombreMaestro(codigoPersonal, "NOMBRE");
		
		List<ArchivosAlumno> lisArchivos		= archivosAlumnoDao.lisAlumnoCurso(cursoCargaId, codigoAlumno, "ORDER BY FECHA");	
		
		HashMap<String,KrdxAlumnoActiv> mapActividadesMateria 			= krdxAlumnoActivDao.mapActividadesMateria(cursoCargaId);
		HashMap<String,CargaGrupoActividad> mapActividadesEnMateria 	= cargaGrupoActividadDao.mapaActividadesPorMateria(cursoCargaId);
		HashMap<String,CargaGrupoEvaluacion> mapaEvaluacionPorMateria 	= cargaGrupoEvaluacionDao.mapaEvaluacionPorMateria(cursoCargaId);
		
		modelo.addAttribute("cursoCargaId", cursoCargaId);
		modelo.addAttribute("materiaNombre", materiaNombre);
		modelo.addAttribute("maestroNombre", maestroNombre);
		modelo.addAttribute("alumnoNombre", alumnoNombre);
		modelo.addAttribute("lisArchivos", lisArchivos);
		modelo.addAttribute("mapActividadesMateria", mapActividadesMateria);
		modelo.addAttribute("mapActividadesEnMateria", mapActividadesEnMateria);
		modelo.addAttribute("mapaEvaluacionPorMateria", mapaEvaluacionPorMateria);
		
		return "portales/maestro/archivoAlum";
	}
	
	@RequestMapping("/portales/maestro/alumnoArch")
	public String portalesMaestroAlumnoArch(HttpServletRequest request, Model modelo){
		
		String actividadId          = request.getParameter("ActividadId")==null?"0":request.getParameter("ActividadId");		
		String cursoCargaId			= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		
		String cursoId				= cargaGrupoCursoDao.cursoIdOrigen(cursoCargaId);
		String materiaNombre 		= mapaCursoDao.getNombreCurso(cursoId);
		String codigoPersonal 		= "0";
		
		CargaGrupoActividad cga 	= new CargaGrupoActividad();		
		if (cargaGrupoActividadDao.existeRegId(actividadId)) {
			cga = cargaGrupoActividadDao.mapeaRegId(actividadId);
		}
		
		CargaGrupoEvaluacion cge 	= new CargaGrupoEvaluacion();
		if (cargaGrupoEvaluacionDao.existeReg(cursoCargaId, cga.getEvaluacionId())){
			cge = cargaGrupoEvaluacionDao.mapeaRegId(cursoCargaId, cga.getEvaluacionId());
		}
		
		HttpSession sesion		= request.getSession();
		if (sesion!=null){
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
		}		
		String maestroNombre 	= maestrosDao.getNombreMaestro(codigoPersonal, "NOMBRE");
		
		List<ArchivosAlumno> lisArchivos						= archivosAlumnoDao.lisPorActividad(actividadId, "ORDER BY FECHA");		
		HashMap<String,String> mapAlumnoMaterias 				= alumPersonalDao.mapaAlumnosEnCurso(cursoId);	
		HashMap<String,KrdxAlumnoActiv> mapNotas 				= krdxAlumnoActivDao.mapActividadesMateria(cursoCargaId);

		
		modelo.addAttribute("cursoCargaId", cursoCargaId);
		modelo.addAttribute("materiaNombre", materiaNombre);
		modelo.addAttribute("maestroNombre", maestroNombre);
		modelo.addAttribute("lisArchivos", lisArchivos);
		modelo.addAttribute("cga", cga);
		modelo.addAttribute("cge", cge);
		modelo.addAttribute("mapAlumnoMaterias", mapAlumnoMaterias);
		modelo.addAttribute("mapNotas", mapNotas);
		
		return "portales/maestro/alumnoArch";
	}
	
	@RequestMapping("/portales/maestro/grabarNotaArchivoEval")
	public String portalesMaestroGrabarNotaArchivoEval(HttpServletRequest request){
		String cursoCargaId	= request.getParameter("CursoCargaId") == null ? "0" : request.getParameter("CursoCargaId");
		String codigoAlumno	= request.getParameter("CodigoAlumno") == null ? "0" : request.getParameter("CodigoAlumno");
		String evaluacionId	= request.getParameter("EvaluacionId") == null ? "0" : request.getParameter("EvaluacionId");
		String nota			= request.getParameter("Nota") == null ? "0" : request.getParameter("Nota");
		
		String  mensaje		= "";
		
		KrdxAlumnoEval krdxAlumnoEval = new KrdxAlumnoEval();
		krdxAlumnoEval.setCodigoPersonal(codigoAlumno);
		krdxAlumnoEval.setCursoCargaId(cursoCargaId);
		krdxAlumnoEval.setEvaluacionId(evaluacionId);
		krdxAlumnoEval.setNota(nota);
		
		if(krdxAlumnoEvalDao.existeReg(codigoAlumno, cursoCargaId, evaluacionId)){
			System.out.println(nota+" u");
			if( krdxAlumnoEvalDao.updateReg(krdxAlumnoEval) ) {
				mensaje = "1";
				System.out.println(mensaje);
			}else {
				mensaje = "2";
				System.out.println(mensaje);
			}
		}else {
			System.out.println(nota +" n");
			if( krdxAlumnoEvalDao.insertReg(krdxAlumnoEval) ) {
				mensaje = "1";
			}else {
				mensaje = "2";
			}
		}
		
		return "redirect:/portales/maestro/archivoEval?CursoCargaId="+cursoCargaId+"&EvaluacionId="+evaluacionId+"&Mensaje="+mensaje;
	}

	@RequestMapping("/portales/maestro/grabarNotaArchivoActiv")
	public String portalesMaestroGrabarNotaArchivoActiv(HttpServletRequest request){
		String cursoCargaId	= request.getParameter("CursoCargaId") == null ? "0" : request.getParameter("CursoCargaId");
		String codigoAlumno	= request.getParameter("CodigoAlumno")==null?"0":request.getParameter("CodigoAlumno");
		String actividadId	= request.getParameter("ActividadId")==null?"0":request.getParameter("ActividadId");
		String nota			= request.getParameter("Nota") == null ? "0" : request.getParameter("Nota");
		
		KrdxAlumnoActiv krdxAlumnoActiv = new KrdxAlumnoActiv();		

		if(krdxAlumnoActivDao.existeReg(codigoAlumno, actividadId)){
			krdxAlumnoActiv = krdxAlumnoActivDao.mapeaRegId(codigoAlumno, actividadId);
			krdxAlumnoActiv.setNota(nota);			
			krdxAlumnoActivDao.updateReg(krdxAlumnoActiv);			
		}else {
			String evaluacionId = cargaGrupoActividadDao.getEvaluacion(actividadId);
			krdxAlumnoActiv.setActividadId(actividadId);
			krdxAlumnoActiv.setCursoCargaId(cursoCargaId);
			krdxAlumnoActiv.setCodigoPersonal(codigoAlumno);
			krdxAlumnoActiv.setEvaluacionId(evaluacionId);
			krdxAlumnoActiv.setNota(nota);
			krdxAlumnoActiv.setActividadE42("0");			
			krdxAlumnoActivDao.insertReg(krdxAlumnoActiv);
		}
		
		return "redirect:/portales/maestro/archivoAlum?CodigoAlumno="+codigoAlumno+"&CursoCargaId="+cursoCargaId;
	}
	
	@RequestMapping("/portales/maestro/grabarNotaAlumnoArchivoActiv")
	public String portalesMaestroGrabarNotaAlumnoArchivoActiv(HttpServletRequest request){
		String cursoCargaId	= request.getParameter("CursoCargaId") == null ? "0" : request.getParameter("CursoCargaId");
		String codigoAlumno	= request.getParameter("CodigoAlumno")==null?"0":request.getParameter("CodigoAlumno");
		String actividadId	= request.getParameter("ActividadId")==null?"0":request.getParameter("ActividadId");
		String nota			= request.getParameter("Nota") == null ? "0" : request.getParameter("Nota");
		
		KrdxAlumnoActiv krdxAlumnoActiv = new KrdxAlumnoActiv();		

		if(krdxAlumnoActivDao.existeReg(codigoAlumno, actividadId)){
			krdxAlumnoActiv = krdxAlumnoActivDao.mapeaRegId(codigoAlumno, actividadId);
			krdxAlumnoActiv.setNota(nota);			
			krdxAlumnoActivDao.updateReg(krdxAlumnoActiv);			
		}else {
			String evaluacionId = cargaGrupoActividadDao.getEvaluacion(actividadId);
			krdxAlumnoActiv.setActividadId(actividadId);
			krdxAlumnoActiv.setCursoCargaId(cursoCargaId);
			krdxAlumnoActiv.setCodigoPersonal(codigoAlumno);
			krdxAlumnoActiv.setEvaluacionId(evaluacionId);
			krdxAlumnoActiv.setNota(nota);
			krdxAlumnoActiv.setActividadE42("0");			
			krdxAlumnoActivDao.insertReg(krdxAlumnoActiv);
		}
		
		return "redirect:/portales/maestro/alumnoArch?ActividadId="+actividadId+"&CursoCargaId="+cursoCargaId;
	}
	
	@RequestMapping("/portales/maestro/rangoPdf")
	public void portalesMaestroRangoPdf(HttpServletRequest request, HttpServletResponse response) throws DocumentException, IOException{
		
		MaestroRango maestroRango 	= new MaestroRango();
		Maestros maestro 			= new Maestros();
		String codigoEmpleado 		= "";
		String year					= request.getParameter("Year")==null?aca.util.Fecha.getHoyReversa().split("/")[0]:request.getParameter("Year");
		int yearAnterior			= Integer.parseInt(year)-1;		
		String fechaPublica			= aca.util.Fecha.getFechaOficial(maestroRangoFechaDao.getFecha(year));
		
		HttpSession sesion 	= request.getSession();
		if (sesion != null){			
			codigoEmpleado 		= (String) sesion.getAttribute("codigoEmpleado");
		}	
		
		if(maestrosDao.existeReg(codigoEmpleado)){
			maestro = maestrosDao.mapeaRegId(codigoEmpleado);
		}
		
		if(maestroRangoDao.existeReg(codigoEmpleado, year)) {
			maestroRango 	= maestroRangoDao.mapeaRegId(codigoEmpleado, year);			
		}
		
		// Para dejar espacio para la foto del alumno
		String carpeta 	= context.getRealPath("/WEB-INF/pdf/maestroRango/");
		if(!new java.io.File(carpeta).exists()) new java.io.File(carpeta).mkdirs();
		String dir 			= carpeta+codigoEmpleado.replace("*","")+".pdf";
		
		PdfPCell celda 		= null;
		
		int r = 0, g = 0, b = 0;
		int[] pagPrn = {1,0,0};
		
		float size0			= 6f;
		float size1			= 7.7f;
		float size2			= 9.7f;
		float size3			= 12f;		
		
		int paginaAnterior		= 1;
		boolean printEncabezado = false;
		
		Document document = new Document(PageSize.LETTER);
		//Crea un objeto para el documento PDF
		// Parametros: Izquierda,derecha,arriba,abajo
		document.setMargins(20,60,205,45);
		
		Parametros parametros = parametrosDao.mapeaRegId("1");
		
		try{
			PdfWriter pdf = PdfWriter.getInstance(document, new FileOutputStream(dir));
			document.addTitle(""+parametros.getInstitucion());
			document.addAuthor("Academic System");
	        document.addSubject(codigoEmpleado+" Rank");               
	            
	        /******* Clase para sobreescribir el metodo OnStartPage *******/
		    document.open();
		    
		    Image fondo = Image.getInstance(context.getRealPath("/imagenes/")+"/fondoRango.jpg");
		    fondo.setAlignment(Image.LEFT | Image.UNDERLYING);
		    fondo.scaleAbsolute(590,750);
		    fondo.setAbsolutePosition(0, 20);
            document.add(fondo);
            
            Image firma = Image.getInstance(context.getRealPath("/imagenes/")+"/firmaRango.png");
            firma.setAlignment(Image.LEFT | Image.UNDERLYING);
            firma.scaleAbsolute(100,50);
            firma.setAbsolutePosition(90, 185);
            document.add(firma);
            
            PdfPTable datos = new PdfPTable(1);
    		int[] datosWidths = {100};
    		datos.setWidths(datosWidths);
    		datos.setTotalWidth(200f);
    		  
            Paragraph parrafo = new Paragraph();
            //parrafo.add(new Phrase("23 de febrero de 2022", FontFactory.getFont(FontFactory.HELVETICA, 11, new BaseColor(r,g,b))));
            parrafo.add(new Phrase(fechaPublica, FontFactory.getFont(FontFactory.HELVETICA, 11, new BaseColor(r,g,b))));
            
            celda = new PdfPCell(parrafo);
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setPaddingBottom(25);
			datos.addCell(celda);
			
			parrafo = new Paragraph();
			// .getNombre()+" "+maestro.getApellidoPaterno()+" "+maestro.getApellidoMaterno().replace(".", "")
			parrafo.add(new Phrase(maestroRango.getNombre().toUpperCase(), FontFactory.getFont(FontFactory.HELVETICA, 11, new BaseColor(r,g,b))));
			
			celda = new PdfPCell(parrafo);
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setPaddingBottom(25);
			datos.addCell(celda);
			
			parrafo = new Paragraph();
			parrafo.add(new Phrase("Dear Professor: ", FontFactory.getFont(FontFactory.HELVETICA, 11, new BaseColor(r,g,b))));
			
			celda = new PdfPCell(parrafo);
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setPaddingBottom(18);
			datos.addCell(celda);
			
			parrafo = new Paragraph();
			parrafo.add(new Phrase("The Academic Ranks Committee considered the evaluation of your performance\r\n"
					+ "professor during the year "+String.valueOf(yearAnterior)+"."
					, FontFactory.getFont(FontFactory.HELVETICA, 11, new BaseColor(r,g,b))));
			
			celda = new PdfPCell(parrafo);
			celda.setLeading(0.7f, 1.5f);
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setPaddingBottom(10);
			datos.addCell(celda);
			
			parrafo = new Paragraph();
			parrafo.add(new Phrase("Based on the results obtained in the year's "+String.valueOf(yearAnterior)+" evaluation, the Governing\r\n"
					+ "Board voted the following ranking for the year "+year+":"
					, FontFactory.getFont(FontFactory.HELVETICA, 11, new BaseColor(r,g,b))));
			
			celda = new PdfPCell(parrafo);
			celda.setLeading(0.7f, 1.5f);
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setPaddingBottom(10);
			datos.addCell(celda);
			
			PdfPTable tabla = new PdfPTable(2);
			tabla.setTotalWidth(document.getPageSize().getWidth() - 72);
			tabla.setSpacingBefore(5f);
			tabla.setSpacingAfter(0f);
			celda = new PdfPCell(new Phrase("Previous rank",
	        		FontFactory.getFont(FontFactory.HELVETICA, size2, Font.BOLD, new BaseColor(0,0,0))));
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			celda.setBorder(Rectangle.BOX);
			tabla.addCell(celda);
			celda = new PdfPCell(new Phrase("Current range",
					FontFactory.getFont(FontFactory.HELVETICA, size2, Font.BOLD, new BaseColor(0,0,0))));
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			celda.setBorder(Rectangle.BOX);
			tabla.addCell(celda);
			
			PdfPTable tablaDatos = new PdfPTable(2);
			tabla.setTotalWidth(document.getPageSize().getWidth() - 72);
			tabla.setSpacingBefore(5f);
			tabla.setSpacingAfter(0f);
			celda = new PdfPCell(new Phrase(maestroRango.getRangoAnterior(),
	        		FontFactory.getFont(FontFactory.HELVETICA, size2, Font.NORMAL, new BaseColor(0,0,0))));
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			celda.setBorder(Rectangle.BOX);
			tabla.addCell(celda);
			celda = new PdfPCell(new Phrase(maestroRango.getRangoRecomendado(),
					FontFactory.getFont(FontFactory.HELVETICA, size2, Font.NORMAL, new BaseColor(0,0,0))));
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			celda.setBorder(Rectangle.BOX);
			tabla.addCell(celda);
			
			document.add(datos);
	        document.add(tabla);
	        document.add(tablaDatos);
	        
	        datos = new PdfPTable(1);
    		datos.setWidths(datosWidths);
    		datos.setTotalWidth(200f);
				
	        parrafo = new Paragraph();
			parrafo.add(new Phrase("We congratulate you on having these results and it is our prayer that, with\r\n"
					+"God's help, may continue to develop his performance and teaching ministry."
					, FontFactory.getFont(FontFactory.HELVETICA, 11, new BaseColor(r,g,b))));
			
			celda = new PdfPCell(parrafo);
			celda.setLeading(0.7f, 1.5f);
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setPaddingTop(10);
			celda.setPaddingBottom(47);
			datos.addCell(celda);
			
			parrafo = new Paragraph();
			parrafo.add(new Phrase("Cordially."
					, FontFactory.getFont(FontFactory.HELVETICA, 11, new BaseColor(r,g,b))));
			
			celda = new PdfPCell(parrafo);
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setPaddingBottom(45);
			datos.addCell(celda);

			parrafo = new Paragraph();
			parrafo.add(new Phrase("Dr. Alberto Valderrama Rincón"
					, FontFactory.getFont(FontFactory.HELVETICA, 11, new BaseColor(r,g,b))));
			
			celda = new PdfPCell(parrafo);
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			datos.addCell(celda);

			parrafo = new Paragraph();
			parrafo.add(new Phrase("Chairman of the Academic Ranking Committee"
					, FontFactory.getFont(FontFactory.HELVETICA, 10, new BaseColor(r,g,b))));
			
			celda = new PdfPCell(parrafo);
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			datos.addCell(celda);

			document.add(datos);
	        
		}catch(IOException ioe){
			System.err.println("PDF certificate error: "+ioe.getMessage());
		}	
		document.close();
		
		// Cambia la diagonal inversa por diagonal normal para que se pueda ver el archivo en windows(localhost)
		if (java.io.File.separator.equals("\\")){
			dir = dir.replace("\\", "/");		
		}
		
		String nombreArchivo = codigoEmpleado.replace("*","")+".pdf";
		java.io.File f = new java.io.File(dir);		
		byte[] archivo = null;
		java.io.FileInputStream instream = null;		
		if(f.exists()){
			archivo = new byte[(int)f.length()];
			instream = new java.io.FileInputStream(dir);
		}
		instream.read(archivo,0,(int)f.length());
		instream.close();
		
		OutputStream out = response.getOutputStream();
		response.setHeader("Content-Disposition","attachment; filename=\""+ nombreArchivo + "\"");
		out.write(archivo);
	
	}
	
	@RequestMapping("/portales/maestro/updateEvaluacion")
	public String portalesMaestroUpdateEvaluacion(HttpServletRequest request){
		int modificados = 0;
		List<ArchivosAlumno> lista = archivosAlumnoDao.lisTodos(" ORDER BY ARCHIVO_ID");
		String evaluacionId = "0";
		for (ArchivosAlumno archivo : lista) {
			if (!archivo.getActividadId().equals("0") && archivo.getEvaluacionId().equals("0") ) {
				evaluacionId = cargaGrupoActividadDao.getEvaluacion(archivo.getActividadId());
				if (archivosAlumnoDao.updateEvaluacion( archivo.getCodigoPersonal(), archivo.getArchivoId(), archivo.getFolio(), evaluacionId)){
					modificados++;
				}
			}
			if (modificados>0 && (modificados % 100)==0) System.out.println("Progress:"+archivo.getArchivoId()+":"+archivo.getCodigoPersonal());
		}	
		System.out.println("Modified:"+modificados);
		return "redirect:/portales/maestro/cursos";
	}
	
}