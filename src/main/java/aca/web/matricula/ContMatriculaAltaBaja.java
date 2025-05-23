package aca.web.matricula;

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
import aca.alumno.spring.AlumAcademico;
import aca.alumno.spring.AlumAcademicoDao;
import aca.alumno.spring.AlumEstado;
import aca.alumno.spring.AlumEstadoDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaAlumno;
import aca.carga.spring.CargaAlumnoDao;
import aca.carga.spring.CargaBloque;
import aca.carga.spring.CargaBloqueDao;
import aca.carga.spring.CargaDao;
import aca.carga.spring.CargaGrupo;
import aca.carga.spring.CargaGrupoDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatModalidad;
import aca.catalogo.spring.CatModalidadDao;
import aca.catalogo.spring.CatTipoCal;
import aca.catalogo.spring.CatTipoCalDao;
import aca.conva.spring.ConvMateriaDao;
import aca.kardex.spring.KrdxAlumnoEvalDao;
import aca.kardex.spring.KrdxCursoAct;
import aca.kardex.spring.KrdxCursoActDao;
import aca.log.spring.LogOperacion;
import aca.log.spring.LogOperacionDao;
import aca.plan.spring.MapaCursoDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaCurso;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.AlumnoCurso;
import aca.vista.spring.AlumnoCursoDao;
import aca.vista.spring.CargaAcademicaCarga;
import aca.vista.spring.CargaAcademicaCargaDao;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContMatriculaAltaBaja {
	
	@Autowired	
	private CargaAlumnoDao cargaAlumnoDao;
	
	@Autowired	
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired	
	private AlumPlanDao alumPlanDao;
	
	@Autowired	
	private AlumEstadoDao alumEstadoDao;
	
	@Autowired	
	private AlumnoCursoDao alumnoCursoDao;
	
	@Autowired	
	private AlumAcademicoDao alumAcademicoDao;
	
	@Autowired	
	private MapaPlanDao mapaPlanDao;
	
	@Autowired	
	private MapaCursoDao mapaCursoDao;
	
	@Autowired	
	private ConvMateriaDao convMateriaDao;
	
	@Autowired	
	private KrdxCursoActDao krdxCursoActDao;
	
	@Autowired	
	private KrdxAlumnoEvalDao krdxAlumnoEvalDao;

	@Autowired	
	private CargaGrupoDao cargaGrupoDao;

	@Autowired	
	private AccesoDao accesoDao;
	
	@Autowired	
	private CargaDao cargaDao;
	
	@Autowired	
	private CargaBloqueDao cargaBloqueDao;

	@Autowired	
	private CatCarreraDao catCarreraDao;
	
	@Autowired	
	private CatModalidadDao catModalidadDao;
	
	@Autowired	
	private LogOperacionDao logOperacionDao;
	
	@Autowired	
	private CatTipoCalDao catTipoCalDao;
	
	@Autowired	
	private CargaAcademicaCargaDao cargaAcademicaCargaDao;
	
	@Autowired	
	private MaestrosDao maestrosDao;	
	
	@RequestMapping("/matricula/altabaja/materias")
	public String matriculaMatriculaMaterias(HttpServletRequest request, Model modelo){
		HttpSession sesion 			= ((HttpServletRequest)request).getSession();		
		String matricula 			= (String) sesion.getAttribute("codigoAlumno");
		String codigoUsuario 		= (String) sesion.getAttribute("codigoPersonal");
		
		String cargaId 				= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String bloqueId 			= request.getParameter("BloqueId")==null?"0":request.getParameter("BloqueId");
		String cursoCargaId 		= request.getParameter("cursoCargaId");
		String accion 				= request.getParameter("accion")==null?"0":request.getParameter("accion");
		String planId 				= request.getParameter("planId")==null?"0":request.getParameter("planId");	
		boolean esAdmin 			= accesoDao.esAdministrador(codigoUsuario); 
		
		Acceso acceso 				= accesoDao.mapeaRegId(codigoUsuario);		
		
		List<Carga> lisCarga 			= cargaDao.getListCargasAlumno(matricula, "ORDER BY 1 DESC");
		
		if (cargaId.equals("0") && lisCarga.size()>0) cargaId = lisCarga.get(0).getCargaId();
		if (cursoCargaId==null) cursoCargaId ="";
		
		List<CargaBloque> lisBloques	= cargaBloqueDao.getLista(cargaId, " ORDER BY BLOQUE_ID");
		
		if (bloqueId.equals("0") && lisBloques.size() > 0) bloqueId = lisBloques.get(0).getBloqueId();
		
		String planAlumno		= cargaAlumnoDao.getPlanId(matricula, cargaId, bloqueId);
		String carreraId		= mapaPlanDao.getCarreraId(planAlumno);
		
		AlumPersonal alumno 	= alumPersonalDao.mapeaRegId(matricula);
		AlumPlan plan 			= alumPlanDao.mapeaRegId(matricula, planAlumno);
		AlumAcademico academico = alumAcademicoDao.mapeaRegId(matricula);
		boolean cargaActiva		= cargaBloqueDao.CargaActiva(cargaId);
		boolean esLinea 		= catModalidadDao.esLinea(academico.getModalidadId());
		
		AlumEstado alumEstado 	= new AlumEstado();
		
		if(alumEstadoDao.existeReg(matricula, cargaId, bloqueId)) {
			alumEstado = alumEstadoDao.mapeaRegId(matricula,cargaId,bloqueId);
		}

		List<MapaPlan> planes 			= mapaPlanDao.getPlanGeneral("ORDER BY PLAN_ID");	
		
		// Map de Grupos
		HashMap<String,CargaGrupo> cargaGrupo				= cargaGrupoDao.mapCargaGrupo(cargaId);
		// Trae nombre de carreras
		HashMap<String,CatCarrera> mapCarrera 				= catCarreraDao.getMapAll("");
		// map de modalidades
		HashMap<String,CatModalidad> mapModalidad 			= catModalidadDao.getMapAll("");	
		HashMap<String, String> mapOrigen 					= cargaGrupoDao.mapCarreraOrigen(cargaId);
		HashMap<String,String> mapTipoCal					= catTipoCalDao.mapTipoCal();	
		
		KrdxCursoAct kardex = new KrdxCursoAct();
		LogOperacion log 	= new LogOperacion();
		
		if (accion.equals("2")){
			kardex = krdxCursoActDao.mapeaRegId(matricula, cursoCargaId);
			krdxCursoActDao.deleteReg(matricula, cursoCargaId);
		}else if (accion.equals("3")){
			String nuevoEstado 	= request.getParameter("nuevoEstado");
			kardex = krdxCursoActDao.mapeaRegId(matricula,cursoCargaId);
			kardex.setTipoCalId(nuevoEstado);
			if (nuevoEstado.equals("3")){
				kardex.setTipo("B");
			}else if ( nuevoEstado.equals("I") && kardex.getTipo().equals("B") ){
				kardex.setTipo("O");
			}	
			krdxCursoActDao.updateReg(kardex);
		}else if (accion.equals("4")){
			int OKs=0,Grab=0;		
			for (int i=1;i<=Integer.parseInt(request.getParameter("Total"));i++){
				if (request.getParameter("chkMateria"+i)!=null){
					kardex.setCodigoPersonal(matricula);
					kardex.setCursoCargaId(request.getParameter("cursoCargaId"+i));
					kardex.setCursoId(request.getParameter("cursoId"+i));
					kardex.setCursoId2(request.getParameter("cursoId"+i));
					if(acceso.getAdministrador().equals("S")){
						kardex.setTipoCalId("I");
					}else{
						kardex.setTipoCalId("M");	
					}
					kardex.setTipo("A");
					Grab++;
					if (krdxCursoActDao.insertReg(kardex))OKs++;
				}
			}
		}else if (accion.equals("5")){
			alumnoCursoDao.bajaTotal(matricula,cargaId);
		}else if(accion.equals("6")){
			kardex = krdxCursoActDao.mapeaRegId(matricula,cursoCargaId);
			if (kardex.getTitulo().equals("N")){
				kardex.setTitulo("S");
			}else{
				kardex.setTitulo("N");
			}
			krdxCursoActDao.updateReg(kardex);
		}else if(accion.equals("7")){
			kardex = krdxCursoActDao.mapeaRegId(matricula,cursoCargaId);
			kardex.setTipoCalId("I");
			kardex.setTipo("B");
					
			String datos = "CursoCargaId: "+cursoCargaId+", CodigoPersonal: "+matricula + ",  CursoId: "+kardex.getCursoId();
			log.setDatos(datos);
			log.setIp(request.getRemoteAddr());
			log.setUsuario(codigoUsuario);
			log.setTabla("krdx_curso_act");
			
			if (krdxCursoActDao.updateReg(kardex)){
				log.setOperacion("update");
				logOperacionDao.insertReg(log);
			}	
			
		}else if(accion.equals("8")){
			alumnoCursoDao.bajaTotalCoordinador(matricula,cargaId);	
		}else if(accion.equals("9")){
			kardex = krdxCursoActDao.mapeaRegId(matricula,cursoCargaId);
			kardex.setTipoCalId("I");
			kardex.setTipo("O");
			krdxCursoActDao.updateReg(kardex);
		}else if (accion.equals("10")){
			krdxAlumnoEvalDao.elimnarEvaluaciones(matricula, cursoCargaId);
			kardex = krdxCursoActDao.mapeaRegId(matricula,cursoCargaId);
			krdxCursoActDao.deleteReg(matricula, cursoCargaId);
		}else if(accion.equals("11")){
			kardex = krdxCursoActDao.mapeaRegId(matricula,cursoCargaId);
			kardex.setTipo("O");
			kardex.setCodigoPersonal(matricula);
			kardex.setCursoCargaId(request.getParameter("cursoCargaId"));
			krdxCursoActDao.updateTipo(kardex);
			
		}else if(accion.equals("12")){
			int OKs=0,Grab=0;		
			for (int i=1;i<=Integer.parseInt(request.getParameter("TotalGen"));i++){
				if (request.getParameter("chkMateriaGen"+i)!=null){
					kardex.setCodigoPersonal(matricula);
					kardex.setCursoCargaId(request.getParameter("cursoCargaGen"+i));
					kardex.setCursoId(request.getParameter("cursoGen"+i));
					kardex.setCursoId2(request.getParameter("cursoGen"+i));
					kardex.setTipoCalId(acceso.getAdministrador().equals("S")?"I":"M");	
					kardex.setTipo("A");
					Grab++;
					if (krdxCursoActDao.insertReg(kardex))OKs++;
				}
			}
		}
		
		List<KrdxCursoAct> lisActual 	= krdxCursoActDao.getListAlumno(matricula, cargaId, bloqueId, " ORDER BY CURSO_CARGA_ID");
		List<AlumnoCurso> lisCursos 	= alumnoCursoDao.lisMateriasDelAlumno(matricula, cargaId, bloqueId, " ORDER BY NOMBRE_CURSO");
		List<CatTipoCal> lisTipoCal 	= catTipoCalDao.getListAll("ORDER BY 1");	
		
		String sModalidad 	= "";
		
		if(esLinea){
			sModalidad = catModalidadDao.modalidadesEnLinea();
		}else{					
			sModalidad = academico.getModalidadId();
		}
		
		List<CargaAcademicaCarga> listCursosDisponibles 		= cargaAcademicaCargaDao.listCursosDisponibles(matricula,cargaId,plan.getPlanId(),sModalidad);
		
		HashMap<String,String> mapaAprobados				= alumnoCursoDao.mapaCursosAprobados(matricula);
		HashMap<String,String> mapaConvalidados				= convMateriaDao.mapaCursosConvalidados(matricula, "'C','A','T','R'");
		
		HashMap<String,String> mapaPrerrequisitos			= new HashMap<String,String>();
		for(CargaAcademicaCarga objeto : listCursosDisponibles) {
			
			List<String> lisPrerrequisitos = new ArrayList<String>();
			mapaCursoDao.lisPrerrequisitos(objeto.getCursoId(), lisPrerrequisitos);
			String cumple = "S";
			for (String pre : lisPrerrequisitos) {				
				if (!mapaAprobados.containsKey(pre) && !mapaConvalidados.containsKey(pre)) {
					cumple = "N";
					break;
				}
			}			
			mapaPrerrequisitos.put(objeto.getCursoId(), cumple);			
		}
		
		String nombrePlan = mapaPlanDao.getNombrePlan(planAlumno);
		
		HashMap<String,String> mapaExistenEvaluaciones = krdxAlumnoEvalDao.mapaExistenEvaluaciones(matricula);
		
		HashMap<String,String> mapaMaestros 		= maestrosDao.mapaMaestroCorto("NOMBRE");
		HashMap<String,KrdxCursoAct> mapaKardex 	= new HashMap<String,KrdxCursoAct>();
		
		for(AlumnoCurso objeto : lisCursos) {
			KrdxCursoAct kardexCursoAct = new KrdxCursoAct();
			kardexCursoAct = krdxCursoActDao.mapeaRegId(objeto.getCodigoPersonal(),objeto.getCursoCargaId());
			
			mapaKardex.put(objeto.getCodigoPersonal()+objeto.getCursoCargaId(), kardexCursoAct);
		}
		
		modelo.addAttribute("matricula", matricula);
		modelo.addAttribute("codigoUsuario", codigoUsuario);
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("bloqueId", bloqueId);
		modelo.addAttribute("cursoCargaId", cursoCargaId);
		modelo.addAttribute("accion", accion);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("esAdmin", esAdmin);
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("carreraId", carreraId);
		modelo.addAttribute("planAlumno", planAlumno);
		modelo.addAttribute("lisCarga", lisCarga);
		modelo.addAttribute("lisBloques", lisBloques);
		modelo.addAttribute("planes", planes);
		modelo.addAttribute("cargaGrupo", cargaGrupo);
		modelo.addAttribute("mapCarrera", mapCarrera);
		modelo.addAttribute("mapModalidad", mapModalidad);
		modelo.addAttribute("lisActual", lisActual);
		modelo.addAttribute("mapOrigen", mapOrigen);
		modelo.addAttribute("alumno", alumno);
		modelo.addAttribute("plan", plan);
		modelo.addAttribute("academico", academico);
		modelo.addAttribute("cargaActiva", cargaActiva);
		modelo.addAttribute("alumEstado", alumEstado);
		modelo.addAttribute("esLinea", esLinea);
		modelo.addAttribute("lisCursos", lisCursos);
		modelo.addAttribute("lisTipoCal", lisTipoCal);
		modelo.addAttribute("mapTipoCal", mapTipoCal);
		modelo.addAttribute("sModalidad", sModalidad);
		modelo.addAttribute("listCursosDisponibles", listCursosDisponibles);
		modelo.addAttribute("mapaPrerrequisitos", mapaPrerrequisitos);
		modelo.addAttribute("nombrePlan", nombrePlan);
		modelo.addAttribute("mapaMaestros", mapaMaestros);
		modelo.addAttribute("mapaExistenEvaluaciones", mapaExistenEvaluaciones);
		modelo.addAttribute("mapaKardex", mapaKardex);
		
		return "matricula/altabaja/materias";
	}
	
	@RequestMapping("/matricula/altabaja/materiasPDF")
	public String matriculaMatriculaMateriasPDF(HttpServletRequest request, Model modelo){
		
		String codigoAlumno 		= request.getParameter("CodigoAlumno")==null?"0":request.getParameter("CodigoAlumno");
		String cargaId 				= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String bloqueId 			= request.getParameter("BloqueId")==null?"0":request.getParameter("BloqueId");
		
		CargaAlumno cargaAlumno 	= new CargaAlumno(); 
		if (cargaAlumnoDao.existeReg(codigoAlumno, cargaId, bloqueId)){
			cargaAlumno = cargaAlumnoDao.mapeaRegId(codigoAlumno, cargaId, bloqueId);
		}
		String alumnoNombre			= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
		String carreraNombre 		= mapaPlanDao.getCarreraSe(cargaAlumno.getPlanId());
		String modalidad			= alumAcademicoDao.getModalidad(codigoAlumno);
		String ciclo	 			= alumPlanDao.mapeaRegIdE(codigoAlumno, cargaAlumno.getPlanId()).getCiclo();
		
		List<KrdxCursoAct> lisActuales 			= krdxCursoActDao.lisMateriasEnCarga(codigoAlumno, cargaId, "ORDER BY CURSO_CARGA_ID");
		HashMap<String, CargaGrupo> mapaGrupos 	= cargaGrupoDao.mapCargaGrupo(cargaId);
		HashMap<String, MapaCurso> mapaCursos 	= mapaCursoDao.mapaCursosDelAlumno(codigoAlumno);
		
		modelo.addAttribute("cargaAlumno", cargaAlumno);
		modelo.addAttribute("alumnoNombre", alumnoNombre);
		modelo.addAttribute("carreraNombre", carreraNombre);
		modelo.addAttribute("modalidad", modalidad);
		modelo.addAttribute("ciclo", ciclo);
		modelo.addAttribute("lisActuales", lisActuales);
		modelo.addAttribute("mapaGrupos", mapaGrupos);
		modelo.addAttribute("mapaCursos", mapaCursos);
		
		return "matricula/altabaja/materiasPDF";
	}
	
}