package aca.web.datosprofesor;

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

import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;
import aca.carga.spring.CargaGrupo;
import aca.carga.spring.CargaGrupoActividad;
import aca.carga.spring.CargaGrupoActividadDao;
import aca.carga.spring.CargaGrupoCursoDao;
import aca.carga.spring.CargaGrupoDao;
import aca.carga.spring.CargaGrupoEvaluacion;
import aca.carga.spring.CargaGrupoEvaluacionDao;
import aca.carga.spring.CargaGrupoHoraDao;
import aca.carga.spring.CargaPronDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatEstrategia;
import aca.catalogo.spring.CatEstrategiaDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatModalidad;
import aca.catalogo.spring.CatModalidadDao;
import aca.catalogo.spring.CatPeriodo;
import aca.catalogo.spring.CatPeriodoDao;
import aca.catalogo.spring.CatTipoCal;
import aca.catalogo.spring.CatTipoCalDao;
import aca.edo.spring.EdoAlumnoPreg;
import aca.edo.spring.EdoAlumnoPregDao;
import aca.edo.spring.EdoAlumnoResp;
import aca.edo.spring.EdoAlumnoRespDao;
import aca.financiero.spring.FesContratoFinancieroDao;
import aca.financiero.spring.FinPermisoDao;
import aca.graduacion.spring.AlumEgresoDao;
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
import aca.plan.spring.MapaCurso;
import aca.plan.spring.MapaCursoDao;
import aca.plan.spring.MapaNuevoCurso;
import aca.plan.spring.MapaNuevoProducto;
import aca.plan.spring.MapaNuevoProductoDao;
import aca.plan.spring.MapaNuevoUnidad;
import aca.plan.spring.MapaNuevoUnidadDao;
import aca.plan.spring.MapaPlanDao;
import aca.pron.spring.PronBiblio;
import aca.pron.spring.PronBiblioDao;
import aca.pron.spring.PronEjes;
import aca.pron.spring.PronEjesDao;
import aca.pron.spring.PronEsquema;
import aca.pron.spring.PronEsquemaDao;
import aca.pron.spring.PronMateria;
import aca.pron.spring.PronMateriaDao;
import aca.pron.spring.PronSemanaDao;
import aca.pron.spring.PronUnidad;
import aca.pron.spring.PronUnidadDao;
import aca.pron.spring.PronValor;
import aca.pron.spring.PronValorDao;
import aca.util.Fecha;
import aca.vista.spring.AlumnoCurso;
import aca.vista.spring.AlumnoCursoDao;
import aca.vista.spring.CargaAcademica;
import aca.vista.spring.CargaAcademicaDao;
import aca.vista.spring.MaestrosDao;
import aca.vista.spring.UsuariosDao;

@Controller
public class ContDatosProfesorEvalua {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	CatModalidadDao catModalidadDao; 
	
	@Autowired
	private UsuariosDao usuariosDao;
	
	@Autowired
	private CargaGrupoDao cargaGrupoDao;
	
	@Autowired	
	private CargaGrupoCursoDao cargaGrupoCursoDao;
	
	@Autowired	
	private CargaGrupoEvaluacionDao cargaGrupoEvaluacionDao;
	
	@Autowired	
	private CargaGrupoActividadDao cargaGrupoActividadDao;
	
	@Autowired
	private aca.plan.spring.MapaNuevoCursoDao mapaNuevoCursoDao;
	
	@Autowired
	private MapaCursoDao mapaCursoDao;

	@Autowired
	private MapaNuevoProductoDao mapaNuevoProductoDao;
	
	@Autowired
	private MapaNuevoUnidadDao mapaNuevoUnidadDao;

	@Autowired
	private CatCarreraDao catCarreraDao;
	
	@Autowired
	private CatFacultadDao catFacultadDao;
	
	@Autowired
	private CatTipoCalDao catTipoCalDao;
	
	@Autowired
	private CatEstrategiaDao catEstrategiaDao;
	
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
	private MaestrosDao maestrosDao;	
	
	@Autowired
	private CatPeriodoDao catPeriodoDao;
	
	@Autowired
	private CargaDao cargaDao;
	
	@Autowired
	CargaAcademicaDao cargaAcademicaDao;
	
	@Autowired	
	private CargaPronDao cargaPronDao;
	
	@Autowired	
	private LogOperacionDao logOperacionDao;
	
	@Autowired	
	private AlumEgresoDao alumEgresoDao;
	
	@Autowired	
	private FinPermisoDao finPermisoDao;
	
	@Autowired	
	private FesContratoFinancieroDao fesContratoFinancieroDao;
	
	@Autowired
	EdoAlumnoRespDao edoAlumnoRespDao;
	
	@Autowired
	CargaGrupoHoraDao cargaGrupoHoraDao;
	
	@Autowired
	KrdxCursoActDao krdxCursoActDao;
	
	@Autowired
	KrdxCursoCalDao krdxCursoCalDao;
	
	@Autowired
	KrdxAlumnoEvalDao krdxAlumnoEvalDao;
	
	@Autowired
	KrdxAlumnoActivDao krdxAlumnoActivDao;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;
	
	@Autowired
	MapaPlanDao mapaPlanDao;
	
	@Autowired
	EdoAlumnoPregDao edoAlumnoPregDao;
	
	@Autowired
	AlumnoCursoDao alumnoCursoDao;
	
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/datos_profesor/evalua/correccion")
	public String datosProfesorEvaluaCorrecion(HttpServletRequest request, Model modelo){
		
		aca.util.Fecha fecha = new aca.util.Fecha();
		
		String cursoCargaId		= request.getParameter("CursoCargaId");				
		String strFecha			= aca.util.Fecha.getHoy();
		String yearName			= aca.util.NumberToLetter.convertirYear(Integer.parseInt(fecha.getYear(strFecha))).trim();		
		String cursoId			= cargaGrupoCursoDao.cursoIdOrigen(cursoCargaId);
		String materia 			= mapaCursoDao.getMateria(cursoId);
		String maestro 			= cargaGrupoDao.getMaestro(cursoCargaId); 
		String carreraId		= cargaGrupoDao.getCarreraId(cursoCargaId);
		
		String accion 		 	= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		int Accion				= Integer.parseInt(accion);
	    String mensaje    		= "-";
	    
	    int ciclo = 0;
	    
	    if(mapaCursoDao.existeReg(cursoId)) {
	    	ciclo = mapaCursoDao.getCiclo(cursoId);
	    }
	    
	    String nombreFacultad 			= catFacultadDao.getNombreFacultad(catCarreraDao.getFacultadId(carreraId));
	    String nombreCarrera  			= catCarreraDao.getNombreCarrera(carreraId);
	    String nombreModalidad 			= catModalidadDao.getNombreModalidad(cargaGrupoDao.getModalidad(cursoCargaId));
		
	    List<KrdxCursoAct> lisAlumnos	= krdxCursoActDao.getListCurso(cursoCargaId, "ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");
	    
	    HashMap<String, KrdxCursoCal> mapaCursoCargaCarga 	= krdxCursoCalDao.mapaCursoCargaCarga(cursoCargaId, cursoId);
	    HashMap<String,String> mapTipoCal 					= catTipoCalDao.mapTipoCal();
	    HashMap<String, String> mapAlumnosMateria 			= alumPersonalDao.mapAlumnosMateria(cursoCargaId);
	    
	    KrdxCursoCal krdxCursoCal = new KrdxCursoCal();
		
	    //operaciones a realizar 
		switch (Accion){			
			case 2: { //Grabar
				
		    	for (int i=0; i<lisAlumnos.size();i++){
		    		KrdxCursoAct alumno = lisAlumnos.get(i);
		    		
			    	String nota = request.getParameter("Nota"+alumno.getCodigoPersonal());
			    	
			    	if(request.getParameter("Codigo"+alumno.getCodigoPersonal()) != null){
			    		
			    		nota = nota.trim();
			    		krdxCursoCal.setCursoCargaId(cursoCargaId);
			    		krdxCursoCal.setCodigoPersonal(alumno.getCodigoPersonal());
			    		krdxCursoCal.setCursoId(cursoId);
			    		
			    		if(request.getParameter("FechaValue"+alumno.getCodigoPersonal())==null){
			    			krdxCursoCal.setFecha(fecha.getHoy());
			    		}
			    		else{
			    			krdxCursoCal.setFecha(request.getParameter("FechaValue"+alumno.getCodigoPersonal()));
			    		}
			    		krdxCursoCal.setFechaFinal(fecha.getHoy());
			    		krdxCursoCal.setNota(request.getParameter("Nota"+alumno.getCodigoPersonal()));
			    		krdxCursoCal.setTipo("C");
			    		krdxCursoCal.setEstado("S");
			    		krdxCursoCal.setTipoNota(request.getParameter("tipoNota"+alumno.getCodigoPersonal()));
			    		krdxCursoCal.setTipoCalId(request.getParameter("tipoCalId"+alumno.getCodigoPersonal()));
			    	
				    	if (!krdxCursoCalDao.existeReg(alumno.getCodigoPersonal(),cursoCargaId,cursoId)){				    		
				    		if (!nota.equals("") && !nota.contains(".") && !nota.contains(",")){
								if (krdxCursoCalDao.insertReg(krdxCursoCal)){
									mensaje = "!! Los Datos han sido Guardados Correctamente !!";								
								}else{
									mensaje = "No Grabó: "+krdxCursoCal.getCursoCargaId();
								}
							}else{
								mensaje = "¡El número no puede ser nulo o decimal!";
							}
						}else{							
							if (!nota.equals("")){
								if (krdxCursoCalDao.updateReg(krdxCursoCal)){ 
									mensaje = "!! Los Datos han sido Modificados Correctamente !!";								
								}else{
									mensaje = "No Cambió: "+krdxCursoCal.getCursoCargaId();
								}
							}
					    }
			    	}
		    	}
		    	mapaCursoCargaCarga 	= krdxCursoCalDao.mapaCursoCargaCarga(cursoCargaId, cursoId);
		    	break;
			}//fin del case 2
		}// fin del switch*/		
		 
	 	modelo.addAttribute("cursoCargaId",cursoCargaId);	
		modelo.addAttribute("materia",materia);	
		modelo.addAttribute("maestro",maestro);	
		modelo.addAttribute("cursoId",cursoId);	
		modelo.addAttribute("yearName",yearName);			
		modelo.addAttribute("carreraId",carreraId);	
		modelo.addAttribute("mensaje",mensaje);
		modelo.addAttribute("lisAlumnos",lisAlumnos);	
		modelo.addAttribute("fecha",fecha);	
		modelo.addAttribute("mapaCursoCargaCarga", mapaCursoCargaCarga);
		modelo.addAttribute("ciclo", ciclo);
		modelo.addAttribute("nombreFacultad", nombreFacultad);
	 	modelo.addAttribute("nombreCarrera", nombreCarrera);
	 	modelo.addAttribute("nombreModalidad", nombreModalidad);
	 	modelo.addAttribute("mapTipoCal", mapTipoCal);
	 	modelo.addAttribute("mapAlumnosMateria", mapAlumnosMateria);
		
		return "datos_profesor/evalua/correccion";
	}
	
	@RequestMapping("/datos_profesor/evalua/borrarNota")
	public String datosProfesorEvaluaBorrarNota(HttpServletRequest request){
		String cursoCargaId 		= request.getParameter("CursoCargaId")==null?"-":request.getParameter("CursoCargaId");
		String cursoId 				= request.getParameter("CursoId")==null?"-":request.getParameter("CursoId");
		String codigoAlumno			= request.getParameter("CodigoAlumno")==null?"-":request.getParameter("CodigoAlumno");
		String mensaje 				= "-";
		if (krdxCursoCalDao.existeReg(codigoAlumno, cursoCargaId, cursoId)) {
			if (krdxCursoCalDao.deleteReg(codigoAlumno, cursoCargaId, cursoId)) {
				mensaje = "¡Nota borrada!";
			}else {
				mensaje = "¡Error al borrar!";
			}
		}		
		return "redirect:/datos_profesor/evalua/correccion?CursoCargaId="+cursoCargaId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/datos_profesor/evalua/correccionPDF")
	public String datosProfesorEvaluaCorrecionPDF(HttpServletRequest request, Model modelo){
		String institucion 		= "";
		String cursoCargaId		= request.getParameter("CursoCargaId");			
		String cursoId			= cargaGrupoCursoDao.cursoIdOrigen(cursoCargaId);
		String materia 			= mapaCursoDao.getMateria(cursoId);
		String opcion 			= request.getParameter("Opcion")==null?"0":request.getParameter("Opcion"); 
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			institucion 		= (String) sesion.getAttribute("institucion");
		}
		
		aca.util.Fecha fecha = new aca.util.Fecha();
		
		String maestro			= request.getParameter("Maestro");
		
		HashMap<String, String> cursos = krdxCursoActDao.mapaAlumnoCurso(cursoCargaId);
		
		String carreraOrigen	= cargaGrupoCursoDao.cursoIdOrigen(cursoCargaId);
		String carreraId		= mapaPlanDao.getCarreraId(carreraOrigen.substring(0,8));
		String strFecha			= aca.util.Fecha.getHoy();
		String yearName			= aca.util.NumberToLetter.convertirYear(Integer.parseInt(fecha.getYear(strFecha))).trim();
		
		List<KrdxCursoCal> lisDiferida = null;
		if (opcion.equals("0")){
			lisDiferida	= krdxCursoCalDao.getListHoy(cursoCargaId,"C", " ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");	
		}else{
			lisDiferida	= krdxCursoCalDao.getListDiferidas(cursoCargaId,"C", " ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");
		}
		
		HashMap<String,KrdxCursoAct> mapaNotas	 	= krdxCursoActDao.mapaNotas(cursoCargaId);
		
		String nombreFacultad 	= catFacultadDao.getNombreFacultad(catCarreraDao.getFacultadId(carreraId));
	    String nombreCarrera  	= catCarreraDao.getNombreCarrera(carreraId);
	    String nombreModalidad 	= catModalidadDao.getNombreModalidad(cargaGrupoDao.getModalidad(cursoCargaId));
	    
	    HashMap<String, String> mapAlumnosMateria 			= alumPersonalDao.mapAlumnosMateria(cursoCargaId);
	    
	    int ciclo = 0;
	    
	    if(mapaCursoDao.existeReg(cursoId)) {
	    	ciclo = mapaCursoDao.getCiclo(cursoId);
	    }
	    
	    String getCiclo = cargaDao.getCiclo(cursoCargaId.substring(0,6));
		
		modelo.addAttribute("institucion",institucion);	
		modelo.addAttribute("cursoCargaId",cursoCargaId);	
		modelo.addAttribute("cursoId",cursoId);	
		modelo.addAttribute("materia",materia);	
		modelo.addAttribute("carreraId",carreraId);	
		modelo.addAttribute("yearName",yearName);	
		modelo.addAttribute("maestro",maestro);	
		modelo.addAttribute("lisDiferida",lisDiferida);	
		modelo.addAttribute("cursos",cursos);	
		modelo.addAttribute("fecha",fecha);	
		modelo.addAttribute("mapaNotas",mapaNotas);	
		modelo.addAttribute("nombreFacultad",nombreFacultad);	
		modelo.addAttribute("nombreCarrera",nombreCarrera);	
		modelo.addAttribute("nombreModalidad",nombreModalidad);	
		modelo.addAttribute("ciclo",ciclo);	
		modelo.addAttribute("mapAlumnosMateria", mapAlumnosMateria);
		modelo.addAttribute("getCiclo", getCiclo);
		
		return "datos_profesor/evalua/correccionPDF";
	}
	
	@RequestMapping("/datos_profesor/evalua/pdfPron")
	public String datosProfesorEvaluarPdfPron(HttpServletRequest request, Model modelo){
		
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
		List<MapaNuevoUnidad> unidades 			= mapaNuevoUnidadDao.getListCurso(mapaCurso.getCursoNuevo(), "ORDER BY UNIDAD_ID");
		
		PronMateria pronMateria					= pronMateriaDao.mapeaRegId(cursoCargaId);
		PronValor pronValor						= pronValorDao.mapeaRegId(cursoCargaId);
		List<PronEsquema> lisEsquema			= pronEsquemaDao.listaEsquema(cursoCargaId);
		List<PronBiblio> lisBiblio				= pronBiblioDao.listaBiblio(cursoCargaId);
		PronEjes pronEjes =  new PronEjes();		
		if(pronEjesDao.existeReg(cursoCargaId)) {
			pronEjes = pronEjesDao.mapeaRegId(cursoCargaId);			
		}
		
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
		
		return "datos_profesor/evalua/pdfPron";
	}
	
	@RequestMapping("/datos_profesor/evalua/actividad")
	public String datosProfesorEvaluaActividad(HttpServletRequest request, Model modelo){
		
		String cursoCargaId		= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		String evaluacionId			= request.getParameter("EvaluacionId")==null?"0":request.getParameter("EvaluacionId");
		String actividadId		= request.getParameter("ActividadId")==null?"0":request.getParameter("ActividadId");
		
		CargaGrupo cargaGrupo 	= cargaGrupoDao.mapeaRegId(cursoCargaId);
		String estadoGrupo		= cargaGrupo.getEstado();
		String maestro 			= cargaGrupo.getCodigoPersonal();
		String maestroNombre 	= maestrosDao.getNombreMaestro(maestro, "NOMBRE");
		String cursoId		 	= cargaGrupoCursoDao.cursoIdOrigen(cursoCargaId);
		String cursoNombre 		= mapaCursoDao.getNombreCurso(cursoId);
		String evaluacionNombre	= cargaGrupoEvaluacionDao.getNombreEvaluacion(cursoCargaId, evaluacionId);
		
		CargaGrupoActividad cga = new CargaGrupoActividad();
		cga.setCursoCargaId(cursoCargaId);
		if (cargaGrupoActividadDao.existeRegId(actividadId)){
			cga = cargaGrupoActividadDao.mapeaRegId(actividadId);
		}
		
		modelo.addAttribute("cga", cga);
		modelo.addAttribute("estadoGrupo", estadoGrupo);
		modelo.addAttribute("maestroNombre", maestroNombre);
		modelo.addAttribute("cursoNombre", cursoNombre);
		modelo.addAttribute("evaluacionNombre", evaluacionNombre);
		
		return "datos_profesor/evalua/actividad";
	}	
	
	@RequestMapping("/datos_profesor/evalua/grabarActividad")
	public String datosProfesorEvaluaGrabarActividad(HttpServletRequest request, Model modelo){
		
		String codigoPersonal 	= "0";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");
		}
				
		String cursoCargaId			= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		String evaluacionId			= request.getParameter("EvaluacionId")==null?"0":request.getParameter("EvaluacionId");
		String actividadId			= request.getParameter("ActividadId")==null?"0":request.getParameter("ActividadId");
		String nombreActividad	 	= request.getParameter("Nombre")==null?"0":request.getParameter("Nombre");		
		String fecha			 	= request.getParameter("Fecha")==null?aca.util.Fecha.getHoyReversa():request.getParameter("Fecha");
		String hora			 		= request.getParameter("Hora")==null?"05":request.getParameter("Hora");
		String minuto			 	= request.getParameter("Minuto")==null?"00":request.getParameter("Minuto");
		String valor			 	= request.getParameter("Valor")==null?"0":request.getParameter("Valor");		
		String actividadE42			= request.getParameter("ActividadE42")==null?"0":request.getParameter("ActividadE42");
		String resultado			= "-";
		if (fecha.length()>=10) fecha = fecha.substring(0,10);
		
		CargaGrupoActividad cga = new CargaGrupoActividad();
		if (cargaGrupoActividadDao.existeRegId(actividadId)){
			cga= cargaGrupoActividadDao.mapeaRegId(actividadId);
		}
		
		cga.setCursoCargaId(cursoCargaId);
		cga.setEvaluacionId(evaluacionId);
		cga.setActividadId(actividadId);
		cga.setNombre(nombreActividad);
		cga.setFecha(fecha+" "+hora+":"+minuto+":00");		
		cga.setValor(valor);
		
		LogOperacion log = new LogOperacion();		
		/********* LOG ****/
		String datos = "CursoCargaId: "+cursoCargaId+", CodigoPersonal: "+codigoPersonal+ ", Valor: "+valor+", EvaluacionId: "+evaluacionId;			
		log.setDatos(datos);
		log.setIp(request.getRemoteAddr());
		log.setUsuario(codigoPersonal);
		log.setTabla("CARGA_GRUPO_ACTIVIDAD");
		
		if (cargaGrupoActividadDao.existeRegId(actividadId) == false){
			actividadId = cargaGrupoActividadDao.maximoReg();
			cga.setActividadId(actividadId);
			if (cargaGrupoActividadDao.insertReg(cga)){
				/******* LOG ******/
				log.setOperacion("insert");
				logOperacionDao.insertReg(log);				
				resultado = "Grabó la actividad: "+cga.getNombre();								
			}else{
				resultado = "No Grabó: "+cga.getNombre();
			}
		}else{
			if (cargaGrupoActividadDao.updateReg(cga)){
				/******* LOG ******/
				log.setOperacion("update");
				logOperacionDao.insertReg(log);
				resultado = "Se Modifica la actividad: "+cga.getNombre();				
			}else{
				resultado = "No Cambió: "+cga.getNombre();
			}
		}
		
		return "redirect:/datos_profesor/evalua/actividad?CursoCargaId="+cursoCargaId+"&EvaluacionId="+evaluacionId+"&ActividadId="+actividadId+"&Resultado="+resultado;
	}
	
	@RequestMapping("/datos_profesor/evalua/alumnos")
	public String datosProfesorEvaluaAlumnos(HttpServletRequest request, Model modelo){
		String cursoCargaId		= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		String maestro 			= request.getParameter("Maestro")==null?"0":request.getParameter("Maestro");
		String materia 			= request.getParameter("Materia")==null?"0":request.getParameter("Materia");
		
		List<AlumnoCurso> lisAlumnos		= alumnoCursoDao.getListCurso(cursoCargaId, " ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");
		HashMap<String,AlumPersonal> mapaAlumnos	= alumPersonalDao.mapAlumnosEnMateria(cursoCargaId);
		
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		
		return "datos_profesor/evalua/alumnos";
	}	
	
	@RequestMapping("/datos_profesor/evalua/autoevaluacion")
	public String datosProfesorEvaluaAutoevaluacion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContDatosProfesorEvalua|Autoevaluacion:");
		return "datos_profesor/evalua/autoevaluacion";
	}	
	
	@RequestMapping("/datos_profesor/evalua/bibliografia")
	public String datosProfesorEvaluaBibliografia(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContDatosProfesorEvalua|Bibliografia:");
		return "datos_profesor/evalua/bibliografia";
	}	
	
	@RequestMapping("/datos_profesor/evalua/borrarNotas")
	public String datosProfesorEvaluaBorrarNotas(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContDatosProfesorEvalua|BorrarNotas:");
		return "datos_profesor/evalua/borrarNotas";
	}	
	
	@RequestMapping("/datos_profesor/evalua/carga_docente")
	public String datosProfesorEvaluaCargaDocente(HttpServletRequest request, Model modelo){
		
		String codigoPersonal 	= "0";
		String maestroNombre	= "-";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal		= (String)sesion.getAttribute("codigoEmpleado");
			maestroNombre 		= maestrosDao.getNombreMaestro(codigoPersonal,"NOMBRE");
		}
		
		modelo.addAttribute("maestroNombre", maestroNombre);
		
		enviarConEnoc(request,"Error-aca.ContDatosProfesorEvalua|CargaDocente:");		
		return "datos_profesor/evalua/carga_docente";
	}	
	
	@RequestMapping("/datos_profesor/evalua/comentarios")
	public String datosProfesorEvaluaComentarios(HttpServletRequest request, Model modelo){
		
		String codigoPersonal	= "0";	
		String cursoCargaId		= request.getParameter("cursoCargaId");
		String nombreCurso		= request.getParameter("nombreCurso");
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal		= (String)sesion.getAttribute("codigoEmpleado");
		}
		
		String nombreMaestro = maestrosDao.getNombreMaestro(codigoPersonal, "NOMBRE");
		
		List<EdoAlumnoPreg> lisPreguntas = edoAlumnoPregDao.getListComentarios(cursoCargaId, "D", "ORDER BY 1");
		
		List<EdoAlumnoResp> listaPreguntasCursoId = edoAlumnoRespDao.listaPreguntasCursoId(cursoCargaId);
		
		modelo.addAttribute("codigoPersonal", codigoPersonal);
		modelo.addAttribute("cursoCargaId", cursoCargaId );
		modelo.addAttribute("nombreCurso", nombreCurso);
		modelo.addAttribute("nombreMaestro", nombreMaestro);
		modelo.addAttribute("lisPreguntas", lisPreguntas);
		modelo.addAttribute("listaPreguntasCursoId", listaPreguntasCursoId);
		
		return "datos_profesor/evalua/comentarios";
	}	
	
	@RequestMapping("/datos_profesor/evalua/competencia")
	public String datosProfesorEvaluaCompetencia(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContDatosProfesorEvalua|Competencia:");
		return "datos_profesor/evalua/competencia";
	}	
	/*
	@RequestMapping("/datos_profesor/evalua/correccion")
	public String datosProfesorEvaluaCorreccion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContDatosProfesorEvalua|Correccion:");
		return "datos_profesor/evalua/correccion";
	}	
	*/
	@RequestMapping("/datos_profesor/evalua/criterio")
	public String datosProfesorEvaluaCriterio(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContDatosProfesorEvalua|Criterio:");
		return "datos_profesor/evalua/criterio";
	}	
	
	@RequestMapping("/datos_profesor/evalua/cambiaEstado")
	public String datosProfesorEvaluaCambiaEstado(HttpServletRequest request){
		
		String cursoCargaId 	= request.getParameter("CursoCargaId")==null?"-":request.getParameter("CursoCargaId");
		String estado	 		= request.getParameter("Estado")==null?"-":request.getParameter("Estado");
		
		boolean carga 		= cargaGrupoDao.updateEstado(cursoCargaId, estado);
		
		return "redirect:/datos_profesor/evalua/cursos?Carga="+carga;
	}	
	
	@RequestMapping("/datos_profesor/evalua/cursos")
	public String datosProfesorEvaluaCursos(HttpServletRequest request, Model modelo){
		
		String codigoPersonal	= "0";
		String maestroNombre	= "-";				 
		String periodoSesion	= "0";
		String cargaSesion		= "0";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal		= (String) sesion.getAttribute("codigoEmpleado");
			maestroNombre 		= maestrosDao.getNombreMaestro(codigoPersonal,"NOMBRE");			
			periodoSesion		= (String) sesion.getAttribute("periodo");
			cargaSesion			= (String) sesion.getAttribute("cargaId");
		}
		
		String periodoId 		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String cargaId			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		
		List<CatPeriodo> lisPeriodos 	= catPeriodoDao.getListPeriodosMaestro(codigoPersonal, " ORDER BY PERIODO_ID DESC");
		
		boolean existePeriodoSesion = false;
		for(CatPeriodo per :lisPeriodos) {
			if (per.getPeriodoId().equals(periodoSesion)) existePeriodoSesion = true;
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
			if (car.getCargaId().equals(cargaSesion)) existeCargaSesion = true;
		}
		
		if (cargaId.equals("0") && existeCargaSesion) {
			cargaId = cargaSesion;
		}else if (cargaId.equals("0") && lisCargas.size()>= 1) {
			cargaId = lisCargas.get(0).getCargaId();
		}else if (!cargaId.equals("0") && sesion!=null) {
			sesion.setAttribute("cargaId", cargaId);
		}
		
		List<CargaAcademica> lisCursos 					= cargaAcademicaDao.getListaMaestro(cargaId, codigoPersonal, " ORDER BY NOMBRE_CURSO");
		
		HashMap<String,String> mapaPron 				= cargaPronDao.mapaTodos();
		HashMap<String,CatCarrera> mapaCarreras 		= catCarreraDao.getMapAll("");
		HashMap<String,CatModalidad> mapaModalidades 	= catModalidadDao.getMapAll("");
		HashMap<String,String> mapaEdo				 	= edoAlumnoRespDao.mapaEdoDeMateria(cargaId);
		HashMap<String,String> mapaHoras			 	= cargaGrupoHoraDao.mapaHorasPorMateria(cargaId);
		HashMap<String,String> mapaPromedioMaterias	 	= edoAlumnoRespDao.mapaPromedioMaterias(cargaId, codigoPersonal);
		HashMap<String,String> mapaTotAlumnos		 	= krdxCursoActDao.mapaTotalAlumnos(cargaId, "'I','1','2','3','5','6'");
		HashMap<String,String> mapaContestaron		 	= edoAlumnoRespDao.mapaContestaron(cargaId);				
		HashMap<String,String> mapaTotEvaluaron		 	= krdxCursoActDao.mapaAlumnosEvaluaron(codigoPersonal);
		HashMap<String,String> mapaDiferidas		 	= krdxCursoCalDao.mapaCambiosNotas(codigoPersonal, "D");
		HashMap<String,String> mapaCorrecciones		 	= krdxCursoCalDao.mapaCambiosNotas(codigoPersonal, "C");
		HashMap<String,String> mapaGraduandos		 	= krdxCursoActDao.mapaGruposConGraduandos(codigoPersonal);
		HashMap<String,String> mapaEsquemas			 	= cargaGrupoEvaluacionDao.mapaEvaluacionPorMateria(cargaId, codigoPersonal);
		
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("maestroNombre", maestroNombre);		
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisCursos", lisCursos);
		
		modelo.addAttribute("mapaPron", mapaPron);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaModalidades", mapaModalidades);
		modelo.addAttribute("mapaEdo", mapaEdo);
		modelo.addAttribute("mapaHoras", mapaHoras);
		modelo.addAttribute("mapaPromedioMaterias", mapaPromedioMaterias);
		modelo.addAttribute("mapaContestaron", mapaContestaron);
		modelo.addAttribute("mapaTotAlumnos", mapaTotAlumnos);
		modelo.addAttribute("mapaTotEvaluaron", mapaTotEvaluaron);
		modelo.addAttribute("mapaDiferidas", mapaDiferidas);
		modelo.addAttribute("mapaCorrecciones", mapaCorrecciones);
		modelo.addAttribute("mapaGraduandos", mapaGraduandos);
		modelo.addAttribute("mapaEsquemas", mapaEsquemas);
		
		return "datos_profesor/evalua/cursos";
	}	
	
	@RequestMapping("/datos_profesor/evalua/datosPlan")
	public String datosProfesorEvaluaDatosPlan(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContDatosProfesorEvalua|DatosPlan:");
		return "datos_profesor/evalua/datosPlan";
	}	
	
	@RequestMapping("/datos_profesor/evalua/diferida")
	public String datosProfesorEvaluaDiferida(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContDatosProfesorEvalua|Diferida:");
		return "datos_profesor/evalua/diferida";
	}	
	
	@RequestMapping("/datos_profesor/evalua/diferidaPDF")
	public String datosProfesorEvaluaDiferidaPDF(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContDatosProfesorEvalua|DiferidaPDF:");
		return "datos_profesor/evalua/diferidaPDF";
	}	
	
	@RequestMapping("/datos_profesor/evalua/ejes")
	public String datosProfesorEvaluaEjes(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContDatosProfesorEvalua|Ejes:");
		return "datos_profesor/evalua/ejes";
	}	
	
	@RequestMapping("/datos_profesor/evalua/evaact")
	public String datosProfesorEvaluaEvaact(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContDatosProfesorEvalua|Evaact:");
		return "datos_profesor/evalua/evaact";
	}	
	
	@RequestMapping("/datos_profesor/evalua/evaluar")
	public String datosProfesorEvaluaEvaluar(HttpServletRequest request, Model modelo){
		//java.text.DecimalFormat getFormato= new java.text.DecimalFormat("###,##0.00;(###,##0.00)");
		
		String codigoPersonal	= "";		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal	= (String) sesion.getAttribute("codigoEmpleado");
		}

		String cursoCargaId		= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		String evaluacionId 		= request.getParameter("EvaluacionId")==null?"0":request.getParameter("EvaluacionId");	
		
		String cursoId			= cargaGrupoCursoDao.cursoIdOrigen(cursoCargaId);
		String maestroId		= cargaGrupoDao.getMaestroClave(cursoCargaId);
		String maestroNombre 	= maestrosDao.getNombreMaestro(maestroId, "NOMBRE");
		String materiaNombre	= mapaCursoDao.getNombreCurso(cursoId);	
		String crear	 		= request.getParameter("Crear")==null?"0":request.getParameter("Crear");
		
		CargaGrupoEvaluacion cargaGrupoEvaluacion = new CargaGrupoEvaluacion();
		
		cargaGrupoEvaluacion.setCursoCargaId(cursoCargaId);
		cargaGrupoEvaluacion.setEvaluacionId("1");
		cargaGrupoEvaluacion.setNombreEvaluacion("Comulative Evaluation- FINAL GRADE");
		cargaGrupoEvaluacion.setFecha(Fecha.getHoy());
		cargaGrupoEvaluacion.setEstrategiaId("A14");
		cargaGrupoEvaluacion.setValor("100");
		cargaGrupoEvaluacion.setTipo("P");
		cargaGrupoEvaluacion.setEvaluacionE42("0");
		cargaGrupoEvaluacion.setEnviar("N");
		
		if(crear.equals("1")) {
			cargaGrupoEvaluacionDao.insertReg(cargaGrupoEvaluacion);
		}
		
		String sAccion 			= request.getParameter("Accion")==null?"1":request.getParameter("Accion");		
		int nAccion				= Integer.parseInt(sAccion);		
		String numExtras 		= request.getParameter("numExtras")==null?"0":request.getParameter("numExtras");	
		int intExtras 			= Integer.parseInt(numExtras);
		
		int diferida			= krdxCursoActDao.numAlumDifMateria(cursoCargaId, cursoId);	
		String estado 			= "";
		
		CargaGrupo materia =  new CargaGrupo();
		
		if(cargaGrupoDao.existeReg(cursoCargaId)) {
			materia = cargaGrupoDao.mapeaRegId(cursoCargaId);
		}
		
		estado = materia.getEstado();	
		
		if (estado.equals("1")){
			// Si tiene alumnos
			if (cargaGrupoDao.conAlumnos(cursoCargaId)){			
				materia.setEstado("2");
				cargaGrupoDao.updateReg(materia);
			}
		}
		
		estado = materia.getEstado();
		
		String sNota			= "";
		String sPromedio		= "";
		int nPromedio 			= 0;
		double sePromedio		= 0;	
		double sumaPuntos  		= 0;
		String eficiencia       = "";	
		String sContador 		= "";
		String sMatricula		= "";
		String sResultado		= "";
		
		String opc1="", opc4="",opc5="",opc6="";
		int j=0, i=0;
		int nContador = 0;
		
		aca.util.Fecha f 		= new aca.util.Fecha();
		String sFecha 			= f.getFecha("1");
			
		CargaGrupo grupoU 					= new CargaGrupo(); // Información del grupo
		AlumPersonal alumnoU				= new AlumPersonal(); // Informacion del alumno
		KrdxAlumnoEval krdxEvaluacionUtil	= new KrdxAlumnoEval(); // Informacion de notas del alumno
		KrdxCursoAct kardex					= new KrdxCursoAct(); // Informacion de la materia del alumno
		CatTipoCal TipocalU 				= new CatTipoCal(); // Catalogo de calificaciones.

		// ArrayList que almacena la metodología de evaluacion de la Materia	
		List<CargaGrupoEvaluacion> lisEvaluacion = cargaGrupoEvaluacionDao.getLista(cursoCargaId, " ORDER BY ENOC.CARGA_GRUPO_EVALUACION.FECHA, EVALUACION_ID");
		
		// ArrayList que almacena los Alumnos inscritos en la materia	 
		List<KrdxCursoAct> lisAlumnos = krdxCursoActDao.getListCurso(cursoCargaId, " ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");
		
		KrdxAlumnoEval alumEvaluacion = new KrdxAlumnoEval();
		KrdxAlumnoActiv alumActividad = new KrdxAlumnoActiv();
		MapaCurso mapaCurso 		  = new MapaCurso();
		
		alumEvaluacion.setCursoCargaId(cursoCargaId);
		alumEvaluacion.setEvaluacionId(request.getParameter("EvaluacionId"));
		// Operaciones a realizar en la pantalla
		
		LogOperacion log 			= new LogOperacion();
		CargaGrupoActividad cga 	= new CargaGrupoActividad();
		CargaGrupoEvaluacion cge 	= new CargaGrupoEvaluacion();
		
		int nE = cargaGrupoEvaluacionDao.getNumEstrategias(cursoCargaId);
		int nEE = cargaGrupoEvaluacionDao.getNumEstrategiasEvaluadas(cursoCargaId);
		int nAB = cargaGrupoEvaluacionDao.getNumAlumnosBaja(cursoCargaId);	

		switch (nAccion){
			case 2: { 
				// Grabar Ordinario
				try{				
					nAccion	=1;
					sContador 	= request.getParameter("Contador");
					nContador 	= Integer.parseInt(sContador);
					for (i=0; i<nContador; i++){
						alumEvaluacion.setCodigoPersonal(request.getParameter("CodigoPersonal"+i));
						alumEvaluacion.setNota(request.getParameter("Nota"+i));
		
						/********* LOG ****/
						String datos = "CursoCargaId: "+request.getParameter("CursoCargaId")+", CodigoPersonal: "+request.getParameter("CodigoPersonal"+i) + ", Evaluacion: "+request.getParameter("EvaluacionId")+", Nota: "+request.getParameter("Nota"+i);
						log.setDatos(datos);
						log.setIp(request.getRemoteAddr());
						log.setUsuario((String) sesion.getAttribute("codigoPersonal"));
						log.setTabla("krdx_alumno_eval");
						
						if (krdxAlumnoEvalDao.existeReg(alumEvaluacion.getCodigoPersonal(),cursoCargaId,evaluacionId)){
							if (krdxAlumnoEvalDao.updateReg(alumEvaluacion)){
								sResultado = "Updated: "+alumEvaluacion.getCodigoPersonal();
								/******* LOG ******/
								log.setOperacion("update");
								logOperacionDao.insertReg(log);
							}else{
								sResultado = "Error updating: "+alumEvaluacion.getCodigoPersonal();
							}					
						}else{
							if (krdxAlumnoEvalDao.insertReg(alumEvaluacion)){
								sResultado = "Saved: "+alumEvaluacion.getCodigoPersonal();
								/******* LOG ******/
								log.setOperacion("insert");
								logOperacionDao.insertReg(log);						
							}else{
								sResultado = "Error saving: "+alumEvaluacion.getCodigoPersonal();
							}
						}
					}
				}catch(Exception ex){
					sResultado = "Error in process. Try again later.";
				}	
			
				evaluacionId="0";
				
				break;
			}
			
			// Simular el Cierre de la Materia (Coloca ceros a las evaluaciones y actividades que no han sido evaluadas)
			case 3:{			
				try{
					// Busca las actividades y coloca un cero en las que no fueron evaluadas en la e42...
					List<CargaGrupoActividad> lisAct = cargaGrupoActividadDao.getListCurso(cursoCargaId, "ORDER BY ACTIVIDAD_ID");
					for(i = 0; i < lisAlumnos.size(); i++){
						KrdxCursoAct ac = (KrdxCursoAct) lisAlumnos.get(i);
						
						for(j = 0; j < lisAct.size(); j++){
							cga = lisAct.get(j);
							
							alumActividad.setCodigoPersonal(ac.getCodigoPersonal());						
							alumActividad.setActividadId(cga.getActividadId());
							alumActividad.setCursoCargaId(cursoCargaId);
							if(!krdxAlumnoActivDao.existeReg(ac.getCodigoPersonal(),cga.getActividadId())){
								alumActividad.setNota("0");
								alumActividad.setActividadE42("0");
								if (krdxAlumnoActivDao.insertReg(alumActividad)){
								}else{
									System.out.println("Error al grabar la actividad");
								}
							}else{
								
							}
						}
					}
					
					// Busca las evaluaciones y coloca un cero en las que no fueron evaluadas en la e42...
					List<CargaGrupoEvaluacion> lisEv = cargaGrupoEvaluacionDao.getLista(cursoCargaId, "ORDER BY EVALUACION_ID");
					for(i = 0; i < lisAlumnos.size(); i++){
						KrdxCursoAct ac = lisAlumnos.get(i);
						for(j = 0; j < lisEv.size(); j++){
							cge = lisEv.get(j);
							alumEvaluacion.setCodigoPersonal(ac.getCodigoPersonal());
							alumEvaluacion.setCursoCargaId(cursoCargaId);
							alumEvaluacion.setEvaluacionId(cge.getEvaluacionId());
							if(!krdxAlumnoEvalDao.existeReg(ac.getCodigoPersonal(),cursoCargaId,cge.getEvaluacionId())){
								alumEvaluacion.setNota("0");
								alumEvaluacion.setEvaluacionE42("0");
								if (krdxAlumnoEvalDao.insertReg(alumEvaluacion)){
								}else{
									System.out.println("Error al grabar las evaluaciones");
								}
							}else{
								
							}
						}
					}				
				}catch(Exception ex){
					sResultado = "Error in process. Try again later.";
				}			
				break;
			} // fin de opcion
			
			case 4: { // Borrar

				sContador = request.getParameter("Contador");
				nContador = Integer.parseInt(sContador);
				for (i=0; i<nContador; i++){

	/********* LOG ****/
					String datos = "CursoCargaId: "+request.getParameter("CursoCargaId")+", CodigoPersonal: "+request.getParameter("CodigoPersonal"+i) + ", Evaluacion: "+request.getParameter("EvaluacionId")+", Nota: "+request.getParameter("Nota"+i);
					log.setDatos(datos);
					log.setIp(request.getRemoteAddr());
					log.setUsuario((String) sesion.getAttribute("codigoPersonal"));
					log.setTabla("krdx_alumno_eval");

					alumEvaluacion.setCodigoPersonal(request.getParameter("CodigoPersonal"+i));
					alumEvaluacion.setNota(request.getParameter("Nota"+i));
					if (krdxAlumnoEvalDao.existeReg(alumEvaluacion.getCodigoPersonal(),cursoCargaId,evaluacionId)){
						if (krdxAlumnoEvalDao.deleteReg(alumEvaluacion.getCodigoPersonal(),cursoCargaId,evaluacionId)){
							sResultado = "Borrado: "+alumEvaluacion.getCodigoPersonal();
							/******* LOG ******/
							log.setOperacion("delete");
							logOperacionDao.insertReg(log);
						}else{
							sResultado = "Error deleting: "+alumEvaluacion.getCodigoPersonal();
					}					
					}else{
						sResultado = "Not found: "+alumEvaluacion.getCodigoPersonal();
					}
				}
				evaluacionId="0";
				break;
			}		
			case 5:{ //Cerrar Materia Ordinaria
				int rowValido = 0; int rowUpdate = 0;
				try{				
					// Busca las actividades y coloca un cero en las que no fueron evaluadas en la e42...
					List<CargaGrupoActividad> lisAct = cargaGrupoActividadDao.getListCurso(cursoCargaId, "ORDER BY ACTIVIDAD_ID");
					for(i = 0; i < lisAlumnos.size(); i++){
						KrdxCursoAct ac = lisAlumnos.get(i);
						
						for(j = 0; j < lisAct.size(); j++){
							cga = lisAct.get(j);
							
							alumActividad.setCodigoPersonal(ac.getCodigoPersonal());						
							alumActividad.setActividadId(cga.getActividadId());
							alumActividad.setCursoCargaId(cursoCargaId);
							if(!krdxAlumnoActivDao.existeReg(ac.getCodigoPersonal(),cga.getActividadId())){
								alumActividad.setNota("0");
								alumActividad.setActividadE42("0");
								if (krdxAlumnoActivDao.insertReg(alumActividad)){
									//System.out.println("Grabe:"+i+":"+AlumActividad.getCodigoPersonal()+":"+AlumActividad.getActividadId());
								}else{
									System.out.println("Error al grabar la actividad");
								}
							}else{
							}
						}
					}
					
					// Busca las evaluaciones y coloca un cero en las que no fueron evaluadas en la e42...
					List<CargaGrupoEvaluacion> lisEv = cargaGrupoEvaluacionDao.getLista(cursoCargaId, "ORDER BY EVALUACION_ID");
					for(i = 0; i < lisAlumnos.size(); i++){
						KrdxCursoAct ac = lisAlumnos.get(i);
						for(j = 0; j < lisEv.size(); j++){
							cge = lisEv.get(j);
							alumEvaluacion.setCodigoPersonal(ac.getCodigoPersonal());
							alumEvaluacion.setCursoCargaId(cursoCargaId);
							alumEvaluacion.setEvaluacionId(cge.getEvaluacionId());
							if(!krdxAlumnoEvalDao.existeReg(ac.getCodigoPersonal(),cursoCargaId,cge.getEvaluacionId())){
								alumEvaluacion.setNota("0");
								alumEvaluacion.setEvaluacionE42("0");
								krdxAlumnoEvalDao.insertReg(alumEvaluacion);
							}else{
								
							}
						}
					}
					materia = cargaGrupoDao.mapeaRegId(cursoCargaId);
					materia.setEstado("3");
					materia.setfEvaluacion(sFecha);
					
					if (cargaGrupoDao.updateReg(materia)){
						estado = "3";
						for (i=0; i<lisAlumnos.size(); i++){
							KrdxCursoAct ac = lisAlumnos.get(i);
							
							/* ES POSIBLE MEJORAR EL RENDIMIENTO */
							sPromedio = krdxAlumnoEvalDao.getAlumnoPromedio(ac.getCursoCargaId(), ac.getCodigoPersonal());
							if (sPromedio==null) sPromedio="0";
							sPromedio = sPromedio.trim();
							nPromedio = Double.valueOf(sPromedio).intValue();
							sPromedio = String.valueOf(nPromedio);
			
							kardex.setCodigoPersonal(ac.getCodigoPersonal());
							kardex.setCursoCargaId(ac.getCursoCargaId());
							
							if (krdxCursoActDao.existeReg(ac.getCodigoPersonal(),ac.getCursoCargaId())){
								kardex = krdxCursoActDao.mapeaRegId(ac.getCodigoPersonal(), ac.getCursoCargaId());
								mapaCurso = mapaCursoDao.mapeaRegId(kardex.getCursoId());
								kardex.setNota(sPromedio);
								kardex.setfNota(sFecha);
								kardex.setfExtra(null);
								// Si el tipo de calificación es diferente de RA, CD o CP actualiza el estado como AC o NA.
								if ( !kardex.getTipoCalId().equals("3") && !kardex.getTipoCalId().equals("4") && 
									!kardex.getTipoCalId().equals("5") && !kardex.getTipoCalId().equals("6")){
									if (nPromedio >= Integer.parseInt(mapaCurso.getNotaAprobatoria())){
										kardex.setTipoCalId("1");
									}else{ 
										kardex.setTipoCalId("2"); 
									}
								}	
							}
							if (kardex.getTitulo().equals("N")){
								rowValido++;
								if (krdxCursoActDao.updateReg(kardex)){								
									rowUpdate++;
								}
							}
						}
						if (rowUpdate==rowValido){
							sResultado = "Materia Ordinaria Cerrada !!!";
						}else{
							sResultado = "Error in process. Try again later "+rowUpdate+" : "+rowValido;
						}					
					}else{
						sResultado = "There was an error closing subject "+rowUpdate+" : "+rowValido;
					}
				}catch(Exception ex){
					sResultado = "Error in process. Try again later";
				}
				break;
			} // fin de opcion
			case 6: { //Grabar Extraordinarios
				for (i=1;i<=intExtras;i++){
					kardex.setCodigoPersonal(request.getParameter("CodigoPersonalE"+i));
					kardex.setCursoCargaId(cursoCargaId);
					if (krdxCursoActDao.existeReg(kardex.getCodigoPersonal(),cursoCargaId)){
						kardex = krdxCursoActDao.mapeaRegId(request.getParameter("CodigoPersonalE"+i), cursoCargaId);
						kardex.setNotaExtra(request.getParameter("NotaExtra"+i));
						kardex.setfExtra(sFecha);
						mapaCurso = mapaCursoDao.mapeaRegId(kardex.getCursoId());
						if (kardex.getTipoCalId()!= "3" &&
						 	!kardex.getTipoCalId().equals("5") && !kardex.getTipoCalId().equals("6")){
							if (Integer.parseInt(kardex.getNotaExtra()) >= Integer.parseInt(mapaCurso.getNotaAprobatoria()))
								kardex.setTipoCalId("1");
							else 
								kardex.setTipoCalId("2");
						}	
						if (krdxCursoActDao.updateReg(kardex)){
							sResultado = "Extraordinary Data Saved";
	/********* LOG ****/
							String datos = "CursoCargaId: "+cursoCargaId+", CodigoPersonal: "+request.getParameter("CodigoPersonalE"+i) + ", Nota Extra: "+request.getParameter("NotaExtra"+i)+", Fecha: "+sFecha;
							log.setDatos(datos);
							log.setIp(request.getRemoteAddr());
							log.setUsuario((String) sesion.getAttribute("codigoPersonal"));
							log.setTabla("krdx_curso_act");
							log.setOperacion("update");
							logOperacionDao.insertReg(log);
						}
						else sResultado = "There was an error updating extraordinary data";
					}				
				}
				evaluacionId="0";
				break;
			}		
			case 7: { //Cerrar Materia Extraordinaria
			
				materia = cargaGrupoDao.mapeaRegId(cursoCargaId);
				materia.setEstado("4");
				if (cargaGrupoDao.updateReg(materia)){
					estado = "4";
					for (i=0; i<lisAlumnos.size(); i++){
						KrdxCursoAct ac = lisAlumnos.get(i);
						kardex.setCodigoPersonal(ac.getCodigoPersonal());
						kardex.setCursoCargaId(ac.getCursoCargaId());
						if (krdxCursoActDao.existeReg(ac.getCodigoPersonal(),ac.getCursoCargaId())){
							kardex = krdxCursoActDao.mapeaRegId(ac.getCodigoPersonal(), ac.getCursoCargaId());
							mapaCurso = mapaCursoDao.mapeaRegId(kardex.getCursoId());
							// Si el tipo de calificación es diferente de RA, CD o CP actualiza el estado como AC o NA.
							//System.out.println(kardex.getCodigoPersonal()+" EX="+kardex.getNotaExtra()+" NA="+mapaCurso.getNotaAprobatoria()+" TC="+kardex.getTipoCalId());
							if ( !kardex.getTipoCalId().equals("3") && 
								!kardex.getTipoCalId().equals("5") && !kardex.getTipoCalId().equals("6")){
								//System.out.println(Integer.parseInt(kardex.getNotaExtra()) >= Integer.parseInt(mapaCurso.getNotaAprobatoria()));
								if (Integer.parseInt(kardex.getNotaExtra()) >= Integer.parseInt(mapaCurso.getNotaAprobatoria()) || Integer.parseInt(kardex.getNota()) >= Integer.parseInt(mapaCurso.getNotaAprobatoria()))
									kardex.setTipoCalId("1");
								else
									kardex.setTipoCalId("2"); 
							}
						}
						krdxCursoActDao.updateReg(kardex);
					}
					sResultado = "Closed Extraordinary";
				}else{
					sResultado = "Error closing extraordinary";
				}
				break;
			}		
			case 8: { 
				String URL = "../../portales/maestro/cactames?cursoCargaId="+cursoCargaId+"&imp=1";
				materia = cargaGrupoDao.mapeaRegId(cursoCargaId);
				materia.setEstado("4");
				cargaGrupoDao.updateReg(materia);
				sResultado = "Deliver documents";
				//response.sendRedirect(URL); 
				break;
			}
			case 9: { //Grabar Tipo Calificación 
				for (i=0;i<=lisAlumnos.size();i++){
					kardex.setCodigoPersonal(request.getParameter("CodigoPersonal"+i));
					kardex.setCursoCargaId(cursoCargaId);
					if (krdxCursoActDao.existeReg(kardex.getCodigoPersonal(),cursoCargaId)){
						kardex = krdxCursoActDao.mapeaRegId(request.getParameter("CodigoPersonal"+i), cursoCargaId);
							kardex.setTipoCalId(request.getParameter("tipocalid"+i));
						if (krdxCursoActDao.updateReg(kardex)){
							sResultado = "Updated";
	/********* LOG ****/
							String datos = "CursoCargaId: "+cursoCargaId+", CodigoPersonal: "+request.getParameter("CodigoPersonal"+i) + ", TipoCalId: "+request.getParameter("tipocalid"+i)+", Fecha: "+sFecha;
							log.setDatos(datos);
							log.setIp(request.getRemoteAddr());
							log.setUsuario((String) sesion.getAttribute("codigoPersonal"));
							log.setTabla("krdx_curso_act");
							log.setOperacion("update");
							logOperacionDao.insertReg(log);
						}
						else sResultado = "Error updating status";
					}
				}
				evaluacionId="0";
				break;
			}
		}
		CatEstrategia estrategiaU = new CatEstrategia();
		// Actualiza la lista de alumnos
		lisAlumnos 		= krdxCursoActDao.getListCurso(cursoCargaId, "ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");
		// Map de saldos vencidos del alumno
		HashMap<String,String> mapAlumnos = alumPersonalDao.mapAlumnosMateria(cursoCargaId);	
		// Map que obtiene las notas de todos los alumnos en la materia
		HashMap<String,String> mapNotas = krdxAlumnoEvalDao.getMapAlumEval(cursoCargaId);
		// Map de saldos vencidos del alumno
		HashMap<String,String> mapDeuda = fesContratoFinancieroDao.mapSaldoVencido(cursoCargaId);
		// Map de tipos de calificación
		HashMap<String,String> mapTipoCal = catTipoCalDao.mapTipoCal();
		// Map de permisos
		HashMap<String,String> mapPermiso 				= finPermisoDao.mapAlumnoPermiso(" WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.KRDX_CURSO_ACT WHERE CURSO_CARGA_ID = '"+cursoCargaId+"') AND now() BETWEEN F_INICIO AND F_LIMITE");	
		//Map de puntos
		HashMap<String, String> mapAlumnoPuntos 		= krdxAlumnoEvalDao.mapAlumnoPuntos(cursoCargaId, "");	
		//Map de puntos Extras
		HashMap<String, String> mapAlumnoExtras 		= krdxAlumnoEvalDao.mapAlumnoExtras(cursoCargaId, "");	
		//Map de puntos evaluados por alumno
		HashMap<String, String> mapAlumnoEvaluados 		= krdxCursoActDao.mapAlumnoEvaluados(cursoCargaId, "");
		//Map de limite de extraordinario en el plan del alumno
		HashMap<String, String> mapExtraPlan 			= krdxCursoActDao.mapLimiteExtra(cursoCargaId);
		//Map de alumnos graduados para identificarlos
		HashMap<String, String> mapaAlumGraduados 		= alumEgresoDao.mapaGraduadosEnMateria(cursoCargaId);
		
		// Trae los datos de la materia 
		mapaCurso = mapaCursoDao.mapeaRegId(cargaGrupoCursoDao.cursoIdOrigen(cursoCargaId));	
		
		HashMap<String, String> mapaActiviPorEval 		= cargaGrupoActividadDao.mapaActividadesPorEvaluacion(cursoCargaId);		
		HashMap<String, MapaCurso> mapaCursoEnCarga 	= mapaCursoDao.getAllMapaCursos("");		
		HashMap<String, CatEstrategia> mapaEstrategias 	= catEstrategiaDao.getMapAll("");
		
		modelo.addAttribute("materia",materia);	
		modelo.addAttribute("cursoCargaId",cursoCargaId);	
		modelo.addAttribute("maestroNombre",maestroNombre);	
		modelo.addAttribute("materiaNombre",materiaNombre);	
		modelo.addAttribute("cursoId",cursoId);	
		modelo.addAttribute("evaluacionId",evaluacionId);	
		modelo.addAttribute("numExtras",numExtras);	
		modelo.addAttribute("intExtras",intExtras);	
		modelo.addAttribute("diferida",diferida);	
		modelo.addAttribute("estado",estado);	
		modelo.addAttribute("lisEvaluacion",lisEvaluacion);	
		modelo.addAttribute("lisAlumnos",lisAlumnos);	
		modelo.addAttribute("mapAlumnos",mapAlumnos);	
		modelo.addAttribute("mapNotas",mapNotas);	
		modelo.addAttribute("mapDeuda",mapDeuda);	
		modelo.addAttribute("mapTipoCal",mapTipoCal);	
		modelo.addAttribute("mapPermiso",mapPermiso);	
		modelo.addAttribute("mapAlumnoPuntos",mapAlumnoPuntos);	
		modelo.addAttribute("mapAlumnoExtras",mapAlumnoExtras);	
		modelo.addAttribute("mapAlumnoEvaluados",mapAlumnoEvaluados);	
		modelo.addAttribute("mapExtraPlan",mapExtraPlan);	
		modelo.addAttribute("mapaAlumGraduados",mapaAlumGraduados);	
		modelo.addAttribute("mapaCurso",mapaCurso);	
		modelo.addAttribute("nE",nE);	
		modelo.addAttribute("nEE",nEE);	
		modelo.addAttribute("nAB",nAB);	
			
		modelo.addAttribute("mapaActiviPorEval",mapaActiviPorEval);	
		modelo.addAttribute("mapaCursoEnCarga",mapaCursoEnCarga);
		modelo.addAttribute("mapaEstrategias",mapaEstrategias);
		
		return "datos_profesor/evalua/evaluar";
	}	
	
	@RequestMapping("/datos_profesor/evalua/instrumento")
	public String datosProfesorEvaluaInstrumento(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContDatosProfesorEvalua|Instrumento:");
		return "datos_profesor/evalua/instrumento";
	}	
	
	@RequestMapping("/datos_profesor/evalua/metodo")
	public String datosProfesorEvaluaMetodo(HttpServletRequest request, Model modelo){
				
		String cursoCargaId		= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		CargaGrupo cargaGrupo 	= cargaGrupoDao.mapeaRegId(cursoCargaId);
		String estadoGrupo		= cargaGrupo.getEstado();
		String maestro 			= cargaGrupo.getCodigoPersonal();
		String maestroNombre 	= maestrosDao.getNombreMaestro(maestro, "NOMBRE");
		String cursoId		 	= cargaGrupoCursoDao.cursoIdOrigen(cursoCargaId);
		String cursoNombre 		= mapaCursoDao.getNombreCurso(cursoId);		
		
		List<CargaGrupoEvaluacion> lisEvaluaciones 		= cargaGrupoEvaluacionDao.getLista( cursoCargaId, " ORDER BY TO_CHAR(ENOC.CARGA_GRUPO_EVALUACION.FECHA,'YYYY-MM-DD'), EVALUACION_ID");
		List<CatEstrategia> lisEstrategias				= catEstrategiaDao.getListAll(" ORDER BY 2");
		List<CargaGrupoActividad> lisActividades 		= cargaGrupoActividadDao.getListCurso(cursoCargaId, " ORDER BY ENOC.CARGA_GRUPO_ACTIVIDAD.FECHA, ACTIVIDAD_ID");
		
		HashMap<String,CatEstrategia> mapaEstrategias 	= catEstrategiaDao.getMapAll("");
		HashMap<String,String> mapaActividades 			= krdxAlumnoActivDao.mapaActividadesEvaluadas(cursoCargaId);
		HashMap<String,String> mapaPromedios 			= krdxAlumnoEvalDao.mapaPromedios(cursoCargaId);
		
		modelo.addAttribute("estadoGrupo", estadoGrupo);
		modelo.addAttribute("maestroNombre", maestroNombre);
		modelo.addAttribute("cursoNombre", cursoNombre);
		
		modelo.addAttribute("lisEstrategias", lisEstrategias);
		modelo.addAttribute("lisEvaluaciones", lisEvaluaciones);
		modelo.addAttribute("lisActividades", lisActividades);
		
		modelo.addAttribute("mapaEstrategias", mapaEstrategias);
		modelo.addAttribute("mapaActividades", mapaActividades);
		modelo.addAttribute("mapaPromedios", mapaPromedios);
		
		return "datos_profesor/evalua/metodo";
	}	
	
	@RequestMapping("/datos_profesor/evalua/editarMetodo")
	public String datosProfesorEvaluaEditarMetodo(HttpServletRequest request, Model modelo){
				
		String cursoCargaId		= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		String evaluacionId		= request.getParameter("EvaluacionId")==null?"0":request.getParameter("EvaluacionId");
		CargaGrupo cargaGrupo 	= cargaGrupoDao.mapeaRegId(cursoCargaId);
		String estadoGrupo		= cargaGrupo.getEstado();
		String maestro 			= cargaGrupo.getCodigoPersonal();
		String maestroNombre 	= maestrosDao.getNombreMaestro(maestro, "NOMBRE");
		String cursoId		 	= cargaGrupoCursoDao.cursoIdOrigen(cursoCargaId);
		String cursoNombre 		= mapaCursoDao.getNombreCurso(cursoId);
		
		CargaGrupoEvaluacion cge = new CargaGrupoEvaluacion();
		cge.setCursoCargaId(cursoCargaId);
		if (cargaGrupoEvaluacionDao.existeReg(cursoCargaId, evaluacionId)){
			cge = cargaGrupoEvaluacionDao.mapeaRegId(cursoCargaId, evaluacionId);
		}
		
		List<CatEstrategia> lisEstrategias				= catEstrategiaDao.getListAll(" ORDER BY 2");
		
		modelo.addAttribute("cge", cge);
		modelo.addAttribute("estadoGrupo", estadoGrupo);
		modelo.addAttribute("maestroNombre", maestroNombre);
		modelo.addAttribute("cursoNombre", cursoNombre);
		
		modelo.addAttribute("lisEstrategias", lisEstrategias);
		
		return "datos_profesor/evalua/editarMetodo";
	}
	
	@RequestMapping("/datos_profesor/evalua/grabarMetodo")
	public String datosProfesorEvaluaGrabarMetodo(HttpServletRequest request, Model modelo){
		
		String codigoPersonal 	= "0";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");
		}
				
		String cursoCargaId			= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		String evaluacionId			= request.getParameter("EvaluacionId")==null?"0":request.getParameter("EvaluacionId");
		String nombreEvaluacion 	= request.getParameter("NombreEvaluacion")==null?"0":request.getParameter("NombreEvaluacion");
		String estrategiaId		 	= request.getParameter("EstrategiaId")==null?"0":request.getParameter("EstrategiaId");
		String fecha			 	= request.getParameter("Fecha")==null?aca.util.Fecha.getHoyReversa():request.getParameter("Fecha");		
		String hora			 		= request.getParameter("Hora")==null?"05":request.getParameter("Hora");
		String minuto			 	= request.getParameter("Minuto")==null?"00":request.getParameter("Minuto");
		String valor			 	= request.getParameter("Valor")==null?"0":request.getParameter("Valor");
		String tipo			 		= request.getParameter("Tipo")==null?"%":request.getParameter("Tipo");
		String evaluacionE42		= request.getParameter("EvaluacionE42")==null?"0":request.getParameter("EvaluacionE42");
		String resultado			= "-";
		if (fecha.length()>=10) fecha = fecha.substring(0,10);
		
		CargaGrupoEvaluacion cge = new CargaGrupoEvaluacion();
		cge.setCursoCargaId(cursoCargaId);
		cge.setEvaluacionId(evaluacionId);
		cge.setNombreEvaluacion(nombreEvaluacion);
		cge.setFecha(fecha+" "+hora+":"+minuto+":00");		
		cge.setEstrategiaId(estrategiaId);
		cge.setValor(valor);
		cge.setTipo(tipo);		
		cge.setEvaluacionE42(evaluacionE42);		
		
		LogOperacion log = new LogOperacion();
		/********* LOG ****/
		String datos = "CursoCargaId: "+cursoCargaId+", CodigoPersonal: "+codigoPersonal+ ", Valor: "+valor+", Tipo: "+tipo;		
		log.setDatos(datos);
		log.setIp(request.getRemoteAddr());
		log.setUsuario(codigoPersonal);
		log.setTabla("carga_grupo_evaluacion");			
		if (cargaGrupoEvaluacionDao.existeReg(cursoCargaId, evaluacionId) == false){
			if (evaluacionId.equals("0")) evaluacionId = cargaGrupoEvaluacionDao.maximoReg(cursoCargaId);
			cge.setEvaluacionId(evaluacionId);
			if (cargaGrupoEvaluacionDao.insertReg(cge)){
				/******* LOG ******/
				log.setOperacion("insert");
				logOperacionDao.insertReg(log);				
				resultado = "Grabó estrategia: "+cge.getEvaluacionId();				
			}else{
				resultado = "No Grabó: "+cge.getEvaluacionId();
			}
		}else{			
			if (cargaGrupoEvaluacionDao.updateReg(cge)){
				/******* LOG ******/
				log.setOperacion("update");
				logOperacionDao.insertReg(log);
				resultado = "Se Modifica la estrategia: "+cge.getEvaluacionId();				
			}else{
				resultado = "No Cambió: "+cge.getEvaluacionId();
			}
		}		
		return "redirect:/datos_profesor/evalua/editarMetodo?CursoCargaId="+cursoCargaId+"&EvaluacionId="+evaluacionId+"&Resultado="+resultado;
	}
	
	@RequestMapping("/datos_profesor/evalua/borrarMetodo")
	public String datosProfesorEvaluaBorrarMetodo(HttpServletRequest request ){
		String codigoPersonal 	= "0";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");
		}
		
		String resultado 			= "-";
		String cursoCargaId			= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		String evaluacionId			= request.getParameter("EvaluacionId")==null?"0":request.getParameter("EvaluacionId");		
		CargaGrupoEvaluacion cge = new CargaGrupoEvaluacion();
		cge.setCursoCargaId(cursoCargaId);
		if (cargaGrupoEvaluacionDao.existeReg(cursoCargaId, evaluacionId)){
			cge = cargaGrupoEvaluacionDao.mapeaRegId(cursoCargaId, evaluacionId);
		}
		
		String datos = "CursoCargaId: "+cursoCargaId+", CodigoPersonal: "+codigoPersonal+ ", Valor: "+cge.getValor()+", Tipo: "+cge.getTipo();
		
		LogOperacion log = new LogOperacion();		
		log.setDatos(datos);
		log.setIp(request.getRemoteAddr());
		log.setUsuario(codigoPersonal);
		log.setTabla("carga_grupo_evaluacion");
		if (cargaGrupoEvaluacionDao.existeReg(cursoCargaId, evaluacionId) == true){
			// Se borra si NO tiene notas registradas
			if (cargaGrupoEvaluacionDao.notasReg(cursoCargaId, evaluacionId)==false){
				cargaGrupoEvaluacionDao.deleteNotas(cursoCargaId, evaluacionId);
				if (cargaGrupoEvaluacionDao.deleteReg(cursoCargaId, evaluacionId)){					
					/****** LOG ******/
					log.setOperacion("delete");
					logOperacionDao.insertReg(log);					
					resultado = "Borrado: "+evaluacionId;
				}else{
					resultado = "No Borró: "+evaluacionId;
				}
			}else{
				resultado = "No borró porque tiene notas registradas ¡¡ (ponga notas en 0)"+evaluacionId;
			}	
		}else{
			resultado = "No existe: "+evaluacionId;
		}
		
		return "redirect:/datos_profesor/evalua/metodo?CursoCargaId="+cursoCargaId+"&Resultado="+resultado;
	}
	
	@RequestMapping("/datos_profesor/evalua/borrarActividad")
	public String datosProfesorEvaluaBorrarActividad(HttpServletRequest request){
		String codigoPersonal 	= "0";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");
		}
		
		String cursoCargaId			= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		String actividadId			= request.getParameter("ActividadId")==null?"0":request.getParameter("ActividadId");		
		String datos = "CursoCargaId: "+cursoCargaId+", CodigoPersonal: "+codigoPersonal+", ActividadId: "+actividadId;
		
		CargaGrupoActividad cga = new CargaGrupoActividad();
		cga.setCursoCargaId(cursoCargaId);
		if (cargaGrupoActividadDao.existeRegId(actividadId)){
			cga = cargaGrupoActividadDao.mapeaRegId(actividadId);
		}
		
		LogOperacion log = new LogOperacion();
		log.setDatos(datos);
		log.setIp(request.getRemoteAddr());
		log.setUsuario(codigoPersonal);
		log.setTabla("CARGA_GRUPO_ACTIVIDAD");
		cga.setActividadId(actividadId);		
		if (cargaGrupoActividadDao.existeRegId(actividadId) == true){
			cargaGrupoActividadDao.deleteReg(actividadId);
		}
		
		return "redirect:/datos_profesor/evalua/metodo?CursoCargaId="+cursoCargaId;
	}
	
	@RequestMapping("/datos_profesor/evalua/opinion_estudiantil")
	public String datosProfesorEvaluaOpinionEstudiantil(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContDatosProfesorEvalua|OpinionEstudiantil:");
		
		return "datos_profesor/evalua/opinion_estudiantil";
	}	
	
	@RequestMapping("/datos_profesor/evalua/opinion_materia")
	public String datosProfesorEvaluaOpinionMateria(HttpServletRequest request, Model modelo){
		
		String codigoPersonal	= "";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal		= (String) sesion.getAttribute("codigoEmpleado");
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
		
		return "datos_profesor/evalua/opinion_materia";
	}	
	
	@RequestMapping("/datos_profesor/evalua/pautas")
	public String datosProfesorEvaluaPautas(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContDatosProfesorEvalua|Pautas:");
		return "datos_profesor/evalua/pautas";
	}	
	
	@RequestMapping("/datos_profesor/evalua/planEvaluacion")
	public String datosProfesorEvaluaPlanEvaluacion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContDatosProfesorEvalua|PlanEvaluacion:");
		return "datos_profesor/evalua/planEvaluacion";
	}	
	
	@RequestMapping("/datos_profesor/evalua/plan")
	public String datosProfesorEvaluaPlan(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContDatosProfesorEvalua|Plan:");
		return "datos_profesor/evalua/plan";
	}	
	
	@RequestMapping("/datos_profesor/evalua/planPDF")
	public String datosProfesorEvaluaPlanPDF(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContDatosProfesorEvalua|PlanPDF:");
		return "datos_profesor/evalua/planPDF";
	}	
	
	@RequestMapping("/datos_profesor/evalua/tema")
	public String datosProfesorEvaluaTema(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContDatosProfesorEvalua|Tema:");
		return "datos_profesor/evalua/tema";
	}	
	
	@RequestMapping("/datos_profesor/evalua/unidadActEdita")
	public String datosProfesorEvaluaUnidadActEdita(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContDatosProfesorEvalua|UnidadActEdita:");
		return "datos_profesor/evalua/unidadActEdita";
	}	
	
	@RequestMapping("/datos_profesor/evalua/unidadAct")
	public String datosProfesorEvaluaUnidadAct(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContDatosProfesorEvalua|UnidadAct:");
		return "datos_profesor/evalua/unidadAct";
	}	
	
	@RequestMapping("/datos_profesor/evalua/unidad")
	public String datosProfesorEvaluaUnidad(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContDatosProfesorEvalua|Unidad:");
		return "datos_profesor/evalua/unidad";
	}	
	
	@RequestMapping("/datos_profesor/evalua/unidadComp")
	public String datosProfesorEvaluaUnidadComp(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContDatosProfesorEvalua|UnidadComp:");
		return "datos_profesor/evalua/unidadComp";
	}	
	
}