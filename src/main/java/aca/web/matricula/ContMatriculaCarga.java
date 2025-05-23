package aca.web.matricula;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import aca.acceso.spring.Acceso;
import aca.admision.spring.AdmEvaluacionNota;
import aca.admision.spring.AdmEvaluacionNotaDao;
import aca.admision.spring.AdmSolicitudDao;
import aca.alerta.spring.AlertaDatos;
import aca.alerta.spring.AlertaPeriodo;
import aca.alumno.spring.AlumAcademico;
import aca.alumno.spring.AlumDias;
import aca.alumno.spring.AlumDiasDao;
import aca.alumno.spring.AlumEstado;
import aca.alumno.spring.AlumEstadoDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumRemedial;
import aca.alumno.spring.AlumRemedialDao;
import aca.alumno.spring.AlumUbicacionDao;
import aca.archivo.spring.ArchivoDao;
import aca.attache.spring.AttacheCustomer;
import aca.attache.spring.AttacheCustomerDao;
import aca.calcula.spring.CalAlumno;
import aca.calcula.spring.CalAlumnoDao;
import aca.calcula.spring.CalConcepto;
import aca.calcula.spring.CalConceptoDao;
import aca.calcula.spring.CalCosto;
import aca.calcula.spring.CalCostoDao;
import aca.calcula.spring.CalMovimiento;
import aca.calcula.spring.CalMovimientoDao;
import aca.calcula.spring.CalPagare;
import aca.calcula.spring.CalPagareAlumno;
import aca.calcula.spring.CalPagareAlumnoDao;
import aca.calcula.spring.CalPagareDao;
import aca.candado.spring.CandAlumno;
import aca.candado.spring.CandTipo;
import aca.candado.spring.Candado;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaAlumno;
import aca.carga.spring.CargaAlumnoDao;
import aca.carga.spring.CargaBloque;
import aca.carga.spring.CargaBloqueDao;
import aca.carga.spring.CargaDao;
import aca.carga.spring.CargaFinanciero;
import aca.carga.spring.CargaFinancieroDao;
import aca.carga.spring.CargaGrupo;
import aca.carga.spring.CargaGrupoCurso;
import aca.carga.spring.CargaGrupoCursoDao;
import aca.carga.spring.CargaGrupoDao;
import aca.carga.spring.CargaPermiso;
import aca.carga.spring.CargaPermisoDao;
import aca.carga.spring.CargaPractica;
import aca.carga.spring.CargaPracticaAlumno;
import aca.carga.spring.CargaPracticaAlumnoDao;
import aca.carga.spring.CargaPracticaDao;
import aca.carga.spring.CargaPron;
import aca.catalogo.spring.CatModalidad;
import aca.catalogo.spring.CatModalidadDao;
import aca.catalogo.spring.CatPais;
import aca.catalogo.spring.CatPaisDao;
import aca.catalogo.spring.CatPeriodo;
import aca.catalogo.spring.CatTipoAlumnoDao;
import aca.conva.spring.ConvMateria;
import aca.financiero.spring.FinCalculo;
import aca.financiero.spring.FinCalculoDao;
import aca.financiero.spring.FinSaldo;
import aca.graduacion.spring.AlumEgreso;
import aca.graduacion.spring.AlumEvento;
import aca.kardex.spring.KrdxCursoAct;
import aca.leg.spring.LegExtdoctos;
import aca.leg.spring.LegExtdoctosDao;
import aca.leg.spring.LegPermisosDao;
import aca.log.spring.LogProceso;
import aca.log.spring.LogProcesoDao;
import aca.nse.spring.NseRespuestaDao;
import aca.parametros.spring.Parametros;
import aca.parametros.spring.ParametrosDao;
import aca.plan.spring.MapaCurso;
import aca.plan.spring.MapaCursoDao;
import aca.plan.spring.MapaCursoPre;
import aca.plan.spring.MapaPlan;
import aca.util.Fecha;
import aca.vista.spring.AlumnoCurso;
import aca.vista.spring.CargaAcademica;
import aca.vista.spring.CargaHorario;
import aca.vista.spring.EstadisticaDao;
import aca.vista.spring.FinTabla;

@Controller
public class ContMatriculaCarga {	
	
	@Autowired
	AlumPersonalDao alumPersonalDao;
	
	@Autowired
	EstadisticaDao estadisticaDao;
	
	@Autowired
	aca.alumno.spring.AlumAcademicoDao alumAcademicoDao;
	
	@Autowired
	aca.vista.spring.UsuariosDao usuariosDao;
	
	@Autowired
	aca.catalogo.spring.CatPeriodoDao catPeriodoDao;
	
	@Autowired
	aca.graduacion.spring.AlumEgresoDao alumEgresoDao;
	
	@Autowired
	aca.graduacion.spring.AlumEventoDao alumEventoDao;

	@Autowired
	aca.leg.spring.LegCondicionesDao legCondicionesDao;	
	
	@Autowired
	CargaDao cargaDao;
	
	@Autowired
	CargaGrupoDao cargaGrupoDao;
	
	@Autowired
	CargaBloqueDao cargaBloqueDao;
	
	@Autowired
	CatTipoAlumnoDao catTipoAlumnoDao;
	
	@Autowired
	CatModalidadDao catModalidadDao;
	
	@Autowired
	aca.plan.spring.MapaPlanDao mapaPlanDao;
	
	@Autowired
	aca.alumno.spring.AlumPlanDao alumPlanDao;
	
	@Autowired
	aca.catalogo.spring.CatCarreraDao catCarreraDao;
	
	@Autowired
	aca.catalogo.spring.CatFacultadDao catFacultadDao;	
	
	@Autowired
	aca.acceso.spring.AccesoDao accesoDao;
	
	@Autowired
	MapaCursoDao mapaCursoDao;

	@Autowired
	CargaAlumnoDao cargaAlumnoDao;
	
	@Autowired
	aca.vista.spring.AlumnoCursoDao alumnoCursoDao;
	
	@Autowired
	aca.vista.spring.CargaAcademicaDao cargaAcademicaDao;
	
	@Autowired
	aca.kardex.spring.KrdxCursoActDao krdxCursoActDao;
	
	@Autowired
	aca.vista.spring.CargaHorarioDao cargaHorarioDao;
	
	@Autowired
	aca.vista.spring.MaestrosDao maestrosDao;
	
	@Autowired
	aca.vista.spring.FinTablaDao finTablaDao;
	
	@Autowired
	aca.carga.spring.CargaGrupoHoraDao cargaGrupoHoraDao;
	
	@Autowired
	aca.plan.spring.MapaCursoPreDao mapaCursoPreDao;
	
	@Autowired
	aca.vista.spring.AlumSaldosDao alumSaldosDao;
	
	@Autowired
	aca.candado.spring.CandAlumnoDao candAlumnoDao;
	
	@Autowired
	aca.candado.spring.CandadoDao candadoDao; 
	
	@Autowired
	aca.alerta.spring.AlertaDatosDao alertaDatosDao; 
	
	@Autowired
	aca.alerta.spring.AlertaPeriodoDao alertaPeriodoDao;
	
	@Autowired
	aca.conva.spring.ConvMateriaDao convMateriaDao;
	
	@Autowired
	aca.carga.spring.CargaPronDao cargaPronDao; 
	
	@Autowired
	aca.archivo.spring.ArchGruposCarreraDao archGruposCarreraDao;
	
	@Autowired
	aca.archivo.spring.ArchGruposDao archGruposDao;
	
	@Autowired
	aca.archivo.spring.ArchDocAlumDao archDocAlumDao;
	
	@Autowired
	aca.financiero.spring.FinPermisoDao finPermisoDao;
	
	@Autowired
	aca.archivo.spring.ArchPermisosDao archPermisosDao;
	
	@Autowired
	aca.candado.spring.CandTipoDao candTipoDao;	

	@Autowired
	aca.leg.spring.LegExtdoctosDao legExtDocDao;
	
	@Autowired
	aca.financiero.spring.ContMovimientoDao contMovimientoDao;
	
	@Autowired	
	private LegPermisosDao legPermisosDao;
	
	@Autowired	
	private LogProcesoDao logProcesoDao;
	
	@Autowired
	aca.financiero.spring.A3lAsalfldgDao a3lAsalfldgDao;
	
	@Autowired
	private LegExtdoctosDao legExtdoctosDao;
	
	@Autowired
	private AdmSolicitudDao admSolicitudDao;
	
	@Autowired
	private AdmEvaluacionNotaDao admEvaluacionNotaDao;
	
	@Autowired
	ArchivoDao archivoDao;	
	
	@Autowired
	CargaPracticaDao cargaPracticaDao;
	
	@Autowired
	private CargaPracticaAlumnoDao cargaPracticaAlumnoDao;
	
	@Autowired
	private AlumDiasDao alumDiasDao;  
	
	@Autowired
	private CargaPermisoDao cargaPermisoDao;
	
	@Autowired
	CalCostoDao calCostoDao;
	
	@Autowired
	CalPagareDao calPagareDao;
	
	@Autowired
	CalConceptoDao calConceptoDao;
	
	@Autowired
	CalAlumnoDao calAlumnoDao;
	
	@Autowired
	CalMovimientoDao calMovimientoDao;
	
	@Autowired
	CalPagareAlumnoDao calPagareAlumnoDao;

	@Autowired
	AlumRemedialDao alumRemedialDao;

	@Autowired
	NseRespuestaDao nseRespuestaDao;

	@Autowired
	AlumUbicacionDao alumUbicacionDao;
	
	@Autowired
	AlumEstadoDao alumEstadoDao;
	
	@Autowired
	FinCalculoDao finCalculoDao;
	
	@Autowired
	CargaFinancieroDao cargaFinancieroDao;
	
	@Autowired
	ParametrosDao parametrosDao;
	
	@Autowired
	CatPaisDao catPaisDao;

	@Autowired
	CargaGrupoCursoDao cargaGrupoCursoDao;

	@Autowired
	AttacheCustomerDao attacheCustomerDao;
	
	
	@RequestMapping("/matricula/carga/radiografia")
	public String matriculaCargaRadiografia(HttpServletRequest request, Model modelo){
		
		String codigoAlumno		= "0";		
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion != null){	        	        	
			codigoAlumno		= (String) sesion.getAttribute("codigoAlumno");
		}
		
		if(request.getParameter("Matricula")!=null) sesion.setAttribute("codigoAlumno", request.getParameter("Matricula"));
		String cargaId			= request.getParameter("carga")==null?"-":request.getParameter("carga");
		String planAlumno		= request.getParameter("planAlumno")==null?"-":request.getParameter("planAlumno");
		//String pagina 			= request.getParameter("pagedropdown")==null?"-":request.getParameter("pagedropdown");
		
		AlumPersonal alumPersonal 	= new AlumPersonal();
		AlumAcademico alumAcademico = new AlumAcademico();
		Carga carga					= new Carga();
		CargaPermiso cargaPermiso	= new CargaPermiso();
		
		alumPersonal 		= alumPersonalDao.mapeaRegId(codigoAlumno);
		alumAcademico 		= alumAcademicoDao.mapeaRegId(codigoAlumno);
		carga 				= cargaDao.mapeaRegId(cargaId);
		String carrera		= mapaPlanDao.getCarreraSe(planAlumno);
		String carreraId	= mapaPlanDao.getCarreraId(planAlumno);
		cargaPermiso 		= cargaPermisoDao.mapeaRegId(cargaId, carreraId);
		
		List<MapaCurso> lisCursos 				= mapaCursoDao.getLista(planAlumno, "ORDER BY CICLO, NOMBRE_CURSO");
		List<AlumnoCurso> lisCursosAlumno 		= alumnoCursoDao.getListAlumno(codigoAlumno," AND PLAN_ID='"+planAlumno+"' AND TIPOCAL_ID != '2' ORDER BY CICLO, NOMBRE_CURSO");
		List<MapaCursoPre> lisPrerrequisitos 	= mapaCursoPreDao.getLista(planAlumno, "ORDER BY CURSO_ID, CURSO_ID_PRE");
		List<ConvMateria> lisConvalidaciones 	= convMateriaDao.getListAlumno(codigoAlumno, planAlumno, "ORDER BY CURSO_ID");
		HashMap<String,String> mapaOfertadas	= cargaAcademicaDao.mapaEsOfertada(cargaId, alumAcademico.getModalidadId());
		HashMap<String,MapaCurso> mapaCursos	= mapaCursoDao.getMapaCursos(planAlumno, "");		
		
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("alumAcademico", alumAcademico);
		modelo.addAttribute("carga", carga);
		modelo.addAttribute("cargaPermiso", cargaPermiso);
		modelo.addAttribute("carrera", carrera);
		modelo.addAttribute("carreraId", carreraId);	
		
		modelo.addAttribute("lisCursos", lisCursos);
		modelo.addAttribute("lisCursosAlumno", lisCursosAlumno);
		modelo.addAttribute("lisPrerrequisitos", lisPrerrequisitos);
		modelo.addAttribute("lisConvalidaciones", lisConvalidaciones);
		modelo.addAttribute("mapaOfertadas", mapaOfertadas);
		modelo.addAttribute("mapaCursos", mapaCursos);
		
		return "matricula/carga/radiografia";
	}
	
	@RequestMapping("/matricula/carga/alumno")
	public String matriculaCargaAlumno(HttpServletRequest request, Model modelo){
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		String codigoPersonal	= "0";
		String codigoAlumno		= "0";		
		String nombreAlumno 	= "-";
		boolean esAlumno		= false;
		boolean existePlan		= false;
		String periodoId		= "0";
		String cargaId			= "0";
		String bloqueId			= "0";
		String tipoAlumno		= "-";
		String carreraId		= "0";
		String facultadId		= "0"; 
		String plan 			= "-";
		String modo				= "-";
		String paisId			= "0";
		boolean esInscrito		= false;
		
		AlumPersonal personal 	= new AlumPersonal();
		AlumAcademico academico	= new AlumAcademico();
		AlumRemedial remedial	= new AlumRemedial();
		Acceso acceso			= new Acceso();
		
		if(parametrosDao.existeReg("1")) {
			paisId = parametrosDao.getPaisId("1");
		}
		CatPais pais = catPaisDao.mapeaRegId(paisId);
		
        if (sesion != null){
        	
        	codigoPersonal	= (String) sesion.getAttribute("codigoPersonal");        	
        	codigoAlumno	= (String) sesion.getAttribute("codigoAlumno");
        	periodoId		= (String) sesion.getAttribute("periodo");
        	cargaId			= (String) sesion.getAttribute("cargaId");
        	bloqueId		= (String) sesion.getAttribute("bloqueId");
        	
        	if (bloqueId.equals("0") || bloqueId.isEmpty()) {
        		bloqueId = cargaAlumnoDao.getMejorBloque(codigoAlumno, cargaId);
        		if (!bloqueId.equals("0")) sesion.setAttribute("bloqueId", bloqueId);
        	}
        	
        	nombreAlumno 	= usuariosDao.getNombreUsuario(codigoAlumno, "NOMBRE");
        	esInscrito 		= estadisticaDao.existeReg(codigoAlumno, cargaId, bloqueId, "I");
        	
        	if (codigoAlumno.subSequence(0, 1).equals("0") || codigoAlumno.subSequence(0, 1).equals("1") || codigoAlumno.subSequence(0, 1).equals("2")){
        		esAlumno 	= true;
        		plan 		= cargaAlumnoDao.getPlanId(codigoAlumno, cargaId, bloqueId);
        		modo		= cargaAlumnoDao.mapeaRegId(codigoAlumno, cargaId, bloqueId).getModo();
        		// Si encontró el plan
        		if (!plan.equals("-")){
        			carreraId	= mapaPlanDao.getCarreraId(plan);
            		facultadId	= catCarreraDao.getFacultadId(carreraId);
        		}        		
        		personal 	= alumPersonalDao.mapeaRegId(codigoAlumno);
        		academico	= alumAcademicoDao.mapeaRegId(codigoAlumno);
        		tipoAlumno	= catTipoAlumnoDao.getNombreTipo(academico.getTipoAlumno());
        		acceso		= accesoDao.mapeaRegId(codigoPersonal);
        	}
        	
        } 

        if(cargaAlumnoDao.existeReg(codigoAlumno, cargaId, bloqueId, plan)) {
        	existePlan = true;
        }
        
        List<AlumRemedial> listaRemedial = alumRemedialDao.listaAlumno(codigoAlumno);
        HashMap<String, String> mapCursos = mapaCursoDao.mapCursos();
        
        MapaPlan mapaPlan = new MapaPlan();
        
        if(mapaPlanDao.existeReg(plan)) {
        	mapaPlan =  mapaPlanDao.mapeaRegId(plan);
        }

		AttacheCustomer customer = new AttacheCustomer();
		if(sesion.getAttribute("institucion").equals("Pacific Adventist University")){
			if(attacheCustomerDao.existeReg(codigoAlumno)){
				customer = attacheCustomerDao.mapeaRegId(codigoAlumno);
			}
		}
		
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("esAlumno", esAlumno);
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("bloqueId", bloqueId);				
		modelo.addAttribute("periodoNombre", catPeriodoDao.getNombre(periodoId));
		modelo.addAttribute("cargaNombre", cargaDao.getNombreCarga(cargaId));
		modelo.addAttribute("bloqueNombre", cargaBloqueDao.getNombre(cargaId, bloqueId));
		modelo.addAttribute("plan", plan);
		modelo.addAttribute("modo", modo);
		modelo.addAttribute("personal", personal);
		modelo.addAttribute("academico", academico);
		modelo.addAttribute("tipoAlumno", tipoAlumno);
		modelo.addAttribute("carreraId", carreraId);
		modelo.addAttribute("facultadId", facultadId);
		modelo.addAttribute("facultadNombre", catFacultadDao.getNombreFacultad(facultadId));
		modelo.addAttribute("carreraNombre", catCarreraDao.getNombreCarrera(carreraId));
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("existePlan", existePlan);
		modelo.addAttribute("esInscrito", esInscrito);
		modelo.addAttribute("listaRemedial", listaRemedial);
		modelo.addAttribute("mapCursos", mapCursos);
		modelo.addAttribute("mapaPlan", mapaPlan);
		modelo.addAttribute("pais", pais);
		modelo.addAttribute("customer", customer);
		
		return "matricula/carga/alumno";
	}
	
	@RequestMapping("/matricula/carga/similares")
	public String matriculaCargaSimilares(HttpServletRequest request, Model modelo){
		
		String cargaId			= "0";
		String bloqueId			= "0";
		String codigoAlumno		= "0";
		
		AlumAcademico academico			= new AlumAcademico();
		
		String cursoId			= request.getParameter("CursoId")==null?"0":request.getParameter("CursoId");
		String materiaNombre	= mapaCursoDao.getMateria(cursoId);
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
        if (sesion != null){        	
        	codigoAlumno	= (String) sesion.getAttribute("codigoAlumno");
        	cargaId			= (String) sesion.getAttribute("cargaId");    
        	bloqueId		= (String) sesion.getAttribute("bloqueId");
        	if (codigoAlumno.subSequence(0, 1).equals("0") || codigoAlumno.subSequence(0, 1).equals("1") || codigoAlumno.subSequence(0, 1).equals("2")){
          		academico	= alumAcademicoDao.mapeaRegId(codigoAlumno);        		
  	        }       
        }        
        
        List<CargaAcademica> lisMaterias 				= cargaAcademicaDao.lisMateriasSimilares(cargaId, cursoId, "");
        HashMap<String, CatModalidad> mapaModalidades 	= catModalidadDao.getMapAll("");
        HashMap<String, CargaBloque> mapaBloques		= cargaBloqueDao.mapaBloques();
        
        modelo.addAttribute("materiaNombre", materiaNombre);
        modelo.addAttribute("lisMaterias", lisMaterias);
        modelo.addAttribute("mapaModalidades", mapaModalidades);
        modelo.addAttribute("bloqueId", bloqueId);
        modelo.addAttribute("bloqueNombre", cargaBloqueDao.getNombre(cargaId, bloqueId));
        modelo.addAttribute("mapaBloques", mapaBloques);
        modelo.addAttribute("academico", academico);
        
		return "matricula/carga/similares";
	}
	
	@RequestMapping("/matricula/carga/periodo")
	public String matriculaCargaPeriodo(HttpServletRequest request, Model modelo){
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		
		String periodoId		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String cargaId			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String bloqueId			= request.getParameter("BloqueId")==null?"0":request.getParameter("BloqueId");
		String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		
		List<CatPeriodo> lisPeriodos 	= catPeriodoDao.getListAll(" ORDER BY PERIODO_ID DESC");
		List<Carga> lisCargas 			= cargaDao.getListAll(" WHERE ESTADO = '1' ORDER BY PERIODO,CARGA_ID");	
		List<CargaBloque> lisBloques 	= cargaBloqueDao.getListAll("ORDER BY CARGA_ID, BLOQUE_ID");
		CatPeriodo periodo 		= new CatPeriodo();
		Carga carga				= new Carga();
		CargaBloque bloque 		= new CargaBloque();
		
        if (sesion!=null){
        	// Grabar valores en la sesion
        	if (accion.equals("1")) {
        		sesion.setAttribute("periodo", periodoId);
        		sesion.setAttribute("cargaId", cargaId);
        		sesion.setAttribute("bloqueId", bloqueId);
        		periodo 	= catPeriodoDao.mapeaRegId(periodoId);
        		carga 		= cargaDao.mapeaRegId(cargaId);
        		bloque		= cargaBloqueDao.mapeaRegId(cargaId, bloqueId);
        	}
        }
        
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisBloques", lisBloques);		
		modelo.addAttribute("periodo", periodo);
		modelo.addAttribute("carga", carga);
		modelo.addAttribute("bloque", bloque);
		
		return "matricula/carga/periodo";
	}
	
	@RequestMapping("/matricula/carga/inscribir")
	public String matriculaCargaInscribir(HttpServletRequest request){
		
		String planId		= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String codigoAlumno = "0";
		String cargaId		= "0";
		String bloqueId		= "0";
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion != null){	        	        	
			codigoAlumno	= (String) sesion.getAttribute("codigoAlumno");
			cargaId			= (String) sesion.getAttribute("cargaId");
			bloqueId		= (String) sesion.getAttribute("bloqueId");
		}
		boolean existe 		= false;
		AlumEstado alumEstado = new AlumEstado();
		if (alumEstadoDao.existeReg(codigoAlumno, cargaId, bloqueId)){
			alumEstado = alumEstadoDao.mapeaRegId(codigoAlumno, cargaId, bloqueId);
			alumEstado.setEstado("I");
			existe = true;
		}else {
			String carreraId 	= mapaPlanDao.getCarreraId(planId);
			String facultadId 	= catCarreraDao.getFacultadId(carreraId);
			alumEstado.setCodigoPersonal(codigoAlumno);
			alumEstado.setCargaId(cargaId);
			alumEstado.setBloqueId(bloqueId);
			alumEstado.setEstado("I");
			alumEstado.setPlanId(planId);
			alumEstado.setFacultadId(facultadId);
			alumEstado.setCarreraId(carreraId);
			alumEstado.setClasFin("1");			
		}
		if (krdxCursoActDao.updateTipoCal(codigoAlumno, planId, cargaId, bloqueId, "I")){
			if (existe) {
				alumEstadoDao.updateEstadoReg(alumEstado);
			}else {
				alumEstadoDao.insertReg(alumEstado);
			}	
		}
		return "redirect:/matricula/carga/materias";
	}
	
	@RequestMapping("/matricula/carga/desinscribir")
	public String matriculaCargaDesinscribir(HttpServletRequest request){
		
		String planId		= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String codigoAlumno = "0";
		String cargaId		= "0";
		String bloqueId		= "0";
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion != null){	        	        	
			codigoAlumno	= (String) sesion.getAttribute("codigoAlumno");
			cargaId			= (String) sesion.getAttribute("cargaId");
			bloqueId		= (String) sesion.getAttribute("bloqueId");
		}
		boolean existe 		= false;
		AlumEstado alumEstado = new AlumEstado();
		if (alumEstadoDao.existeReg(codigoAlumno, cargaId, bloqueId)){
			alumEstado = alumEstadoDao.mapeaRegId(codigoAlumno, cargaId, bloqueId);
			alumEstado.setEstado("M");
			existe = true;
		}
		if (krdxCursoActDao.updateTipoCal(codigoAlumno, planId, cargaId, bloqueId, "M")){
			if (existe) {
				alumEstadoDao.updateEstadoReg(alumEstado);
			}
		}
		return "redirect:/matricula/carga/materias";
	}
	
	@RequestMapping("/matricula/carga/materias")
	public String matriculaCargaMaterias(HttpServletRequest request, Model modelo){
		
		String ciclo			= request.getParameter("Ciclo")==null?"0":request.getParameter("Ciclo");		
		String mensaje			= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");		
		String codigoAlumno		= "0";
		String nombreAlumno		= "-";
		String periodoId		= "0";
		String cargaId			= "0";
		String bloqueId			= "0";
		String tipoAlumno		= "-";
		String modalidad		= "1";
		String alumnoEnLinea	= "N";
		String paisId			= "0";
		
		String plan 						= "";
		AlumPersonal personal 				= new AlumPersonal();
		AlumAcademico academico				= new AlumAcademico();
		MapaPlan mapaPlan					= new MapaPlan();
		List<MapaCurso> lisMaterias			= new ArrayList<MapaCurso>();
		List<CargaAcademica> lisOferta		= new ArrayList<CargaAcademica>();
		List<KrdxCursoAct> lisCurso			= new ArrayList<KrdxCursoAct>();
		HashMap<String,List<CargaHorario>> mapaListaCargaHorario = new HashMap<String,List<CargaHorario>>();
		
		if(parametrosDao.existeReg("1")) {
			paisId = parametrosDao.getPaisId("1");
		}
		CatPais pais = catPaisDao.mapeaRegId(paisId);
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion != null){	        	        	
			codigoAlumno	= (String) sesion.getAttribute("codigoAlumno");
			periodoId		= (String) sesion.getAttribute("periodo");
		    cargaId			= (String) sesion.getAttribute("cargaId");
		    bloqueId		= (String) sesion.getAttribute("bloqueId");
	        nombreAlumno 	= usuariosDao.getNombreUsuario(codigoAlumno, "NOMBRE");
	        
	        if (codigoAlumno.subSequence(0, 1).equals("0") || codigoAlumno.subSequence(0, 1).equals("1") || codigoAlumno.subSequence(0, 1).equals("2")){
	        	plan 		= cargaAlumnoDao.getPlanId(codigoAlumno, cargaId, bloqueId);
	        	personal 	= alumPersonalDao.mapeaRegId(codigoAlumno);
        		academico	= alumAcademicoDao.mapeaRegId(codigoAlumno);        		
        		mapaPlan	= mapaPlanDao.mapeaRegId(plan);
        		tipoAlumno	= catTipoAlumnoDao.getNombreTipo(academico.getTipoAlumno());
        		modalidad 	= academico.getModalidadId();
        		if (catModalidadDao.esLinea(modalidad)) {
        			alumnoEnLinea = "S";
        		}
	        }       
	        
	        lisMaterias	= mapaCursoDao.getLista(plan, " ORDER BY CICLO, NOMBRE_CURSO");
	        if (alumnoEnLinea.equals("S")) {
	        	lisOferta	= cargaAcademicaDao.lisCargaEnLinea(cargaId, plan, "ORDER BY CICLO, GRUPO, NOMBRE_CURSO");
	        }else {
	        	lisOferta	= cargaAcademicaDao.getListaCarga(cargaId, plan,modalidad," ORDER BY CICLO, GRUPO, NOMBRE_CURSO");	      
	        }
	        
	        lisCurso	= krdxCursoActDao.lisMateriasEnCarga(codigoAlumno, cargaId, " ORDER BY CURSO_ID");	        
	        if(!ciclo.equals("0")) {
	        	sesion.setAttribute("cicloId", ciclo);
	        }
		}
		AlumEstado alumEstado = new AlumEstado();
		if (alumEstadoDao.existeReg(codigoAlumno, cargaId, bloqueId)){
			alumEstado = alumEstadoDao.mapeaRegId(codigoAlumno, cargaId, bloqueId);						
		}
		
		CargaFinanciero financiero 		= new CargaFinanciero();
		if(cargaFinancieroDao.existeReg(codigoAlumno, cargaId, bloqueId)) {
			financiero = cargaFinancieroDao.mapeaRegId(codigoAlumno, cargaId, bloqueId);
		}
		
		for(CargaAcademica carga : lisOferta) {
			List<CargaHorario> lista = cargaHorarioDao.getLista(carga.getCursoCargaId(), "");
			mapaListaCargaHorario.put(carga.getCursoCargaId(), lista);
		}
		
		String folio							= admSolicitudDao.getFolio(codigoAlumno);
		List<AdmEvaluacionNota> lisReprobadas	= admEvaluacionNotaDao.getListReprobadas(folio);
		List<String> lisCiclos					= mapaCursoDao.lisCiclosPlan(plan);
		List<MapaCursoPre> lisPre				= mapaCursoPreDao.getLista(plan, " ORDER BY CURSO_ID");
		List<CargaPracticaAlumno> lisPracticas 	= cargaPracticaAlumnoDao.lisPorAlumno(codigoAlumno, cargaId, "");
		List<CargaPractica> lisFechas 			= cargaPracticaDao.lisPorCarga(cargaId, " ORDER BY CURSO_CARGA_ID");
		
		boolean practicaRegistrada = false;		
		if(lisPracticas.size() > 0) {
			practicaRegistrada = true;
		}

		AttacheCustomer customer = new AttacheCustomer();
		if(sesion.getAttribute("institucion").equals("Pacific Adventist University")){
			if(attacheCustomerDao.existeReg(codigoAlumno)){
				customer = attacheCustomerDao.mapeaRegId(codigoAlumno);
			}
		}
		
		HashMap<String,AlumnoCurso> mapaCursosAcreditados	= alumnoCursoDao.mapaCursosAcreditados(codigoAlumno, plan, "1");
		HashMap<String,String> mapaMaestros					= maestrosDao.mapMaestroNombre("CORTO");		
		HashMap<String,CatModalidad> mapaModalidades		= catModalidadDao.getMapAll("");		
		HashMap<String,FinTabla> mapaCostos					= finTablaDao.mapTablaCargas("'"+cargaId+"'");		
		HashMap<String,MapaCurso> mapaCurso					= mapaCursoDao.getMapaCursos(plan, "");		
		HashMap<String,KrdxCursoAct> mapaKardexCurso		= krdxCursoActDao.getMapAlumCurso(codigoAlumno,"'M','I'");
		HashMap<String,String> mapaHorasMateria				= cargaGrupoHoraDao.mapaHorasPorMateria(cargaId);
		HashMap<String,String> mapaSalonesMateria			= cargaGrupoHoraDao.mapaSalonesPorMateria(cargaId);
		HashMap<String,String> mapaMateriaCosto 			= cargaGrupoDao.mapaMateriaCosto(cargaId);
		HashMap<String,ConvMateria> mapaMatConv 			= convMateriaDao.mapaMatConv(codigoAlumno, "'S','X'", "'-','R','S'");
		HashMap<String,CargaPron> mapaCargaPron 			= cargaPronDao.getMapCargaPron();
		HashMap<String,CargaGrupo> mapaCargaGrupo 			= cargaGrupoDao.mapCargaGrupo(cargaId);
		HashMap<String,String> mapaRemediales				= alumnoCursoDao.mapaRemediales(codigoAlumno);
		HashMap<String,String> mapaPracticas				= mapaCursoDao.mapaCursosEnPracticas();
		HashMap<String,CargaGrupoCurso> mapaCargaGrupoCurso = cargaGrupoCursoDao.mapaCursosOrigenPorCarga(cargaId);
		HashMap<String,String> mapaNombresMapaCurso 		= mapaCursoDao.getMapaNombresCursosPorCondicion("");
		
		CargaAlumno cargaAlumno = cargaAlumnoDao.mapeaRegId(codigoAlumno, cargaId, bloqueId);
		
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("bloqueId", bloqueId);
		modelo.addAttribute("pais", pais);
		modelo.addAttribute("periodoNombre", catPeriodoDao.getNombre(periodoId));
		modelo.addAttribute("cargaNombre", cargaDao.getNombreCarga(cargaId));
		modelo.addAttribute("bloqueNombre", cargaBloqueDao.getNombre(cargaId, bloqueId));
		modelo.addAttribute("plan", plan);
		modelo.addAttribute("personal", personal);
		modelo.addAttribute("academico", academico);
		modelo.addAttribute("tipoAlumno", tipoAlumno);
		modelo.addAttribute("alumnoEnLinea", alumnoEnLinea);
		modelo.addAttribute("exceptoPron", catPeriodoDao.getExceptoPron(periodoId));
		modelo.addAttribute("practicaRegistrada", practicaRegistrada);
		modelo.addAttribute("alumEstado", alumEstado);
		modelo.addAttribute("financiero", financiero);
		modelo.addAttribute("customer", customer);
		
		modelo.addAttribute("lisMaterias", lisMaterias);
		modelo.addAttribute("lisCiclos", lisCiclos);		
		modelo.addAttribute("lisPre", lisPre);
		modelo.addAttribute("lisOferta", lisOferta);
		modelo.addAttribute("lisCurso", lisCurso);
		modelo.addAttribute("lisReprobadas", lisReprobadas);
		modelo.addAttribute("lisPracticas", lisPracticas);
		modelo.addAttribute("lisFechas", lisFechas);
		
		modelo.addAttribute("mapaCursosAcreditados", mapaCursosAcreditados);
		modelo.addAttribute("mapaPlan", mapaPlan);
		modelo.addAttribute("mapaKardexCurso", mapaKardexCurso);
		modelo.addAttribute("mapaMaestros", mapaMaestros);
		modelo.addAttribute("mapaModalidades", mapaModalidades);
		modelo.addAttribute("mapaCostos", mapaCostos);
		modelo.addAttribute("mapaCurso", mapaCurso);
		modelo.addAttribute("mapaHorasMateria", mapaHorasMateria);	
		modelo.addAttribute("mapaSalonesMateria", mapaSalonesMateria);
		modelo.addAttribute("mapaMateriaCosto", mapaMateriaCosto);
		modelo.addAttribute("mapaMatConv", mapaMatConv);
		modelo.addAttribute("mapaCargaPron", mapaCargaPron);
		modelo.addAttribute("mapaCargaGrupo", mapaCargaGrupo);						
		modelo.addAttribute("mapaRemediales", mapaRemediales);
		modelo.addAttribute("mapaListaCargaHorario", mapaListaCargaHorario);
		modelo.addAttribute("mapaPracticas", mapaPracticas);
		modelo.addAttribute("mapaCargaGrupoCurso", mapaCargaGrupoCurso);
		modelo.addAttribute("mapaNombresMapaCurso", mapaNombresMapaCurso);
		modelo.addAttribute("cargaAlumno", cargaAlumno);
		modelo.addAttribute("mensaje", mensaje);
		
		LogProceso proceso = new LogProceso();
		
		proceso.setFolio(String.valueOf(logProcesoDao.maximoReg()));
		proceso.setCodigoPersonal(codigoAlumno);
		proceso.setModulo("CARGA MATERIAS");
		proceso.setFecha(Fecha.getHoy());
		proceso.setEvento("INICIA-INSERTAR");
		
		logProcesoDao.insertReg(proceso);
		
		return "matricula/carga/materias";
	}
	
	@RequestMapping("/matricula/carga/grabarComentario")
	public String matriculaCargaGrabarComentario(HttpServletRequest request, Model modelo){
		
		String comentario		= request.getParameter("Comentario")==null?"0":request.getParameter("Comentario");	
		String codigoAlumno		= "0";
		String cargaId			= "0";
		String bloqueId			= "0";
		String mensaje			= "0";
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion != null){	        	        	
			codigoAlumno	= (String) sesion.getAttribute("codigoAlumno");
		    cargaId			= (String) sesion.getAttribute("cargaId");
		    bloqueId		= (String) sesion.getAttribute("bloqueId");
		}
		
		CargaAlumno cargaAlumno = cargaAlumnoDao.mapeaRegId(codigoAlumno, cargaId, bloqueId);
		
		if(!comentario.equals("0")) {
			cargaAlumno.setComentario(comentario);
		}
		
		if(cargaAlumnoDao.updateReg(cargaAlumno)) {
			mensaje = "1";
		}else {
			mensaje = "2";
		}
		
		return "redirect:/matricula/carga/materias?Mensaje="+mensaje;
	}
	
	@RequestMapping("/matricula/carga/practicas")
	public String matriculaCargaPracticas(HttpServletRequest request, Model modelo){
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();		
		String bloqueId			= "0";
		String cargaId			= "0";		
		String codigoAlumno		= "0";

        if (sesion != null){
    		codigoAlumno	= (String) sesion.getAttribute("codigoAlumno");
    		cargaId			= (String) sesion.getAttribute("cargaId");
 		    bloqueId		= (String) sesion.getAttribute("bloqueId");
        }
		
        
        List<CargaPracticaAlumno> listaPracticas = cargaPracticaAlumnoDao.lisPorAlumno(codigoAlumno, cargaId, "ORDER BY FOLIO");
        
        AlumPersonal alumno = new AlumPersonal();
        
        if(alumPersonalDao.existeAlumno(codigoAlumno)) {
        	alumno = alumPersonalDao.mapeaRegId(codigoAlumno);
        }
        
        modelo.addAttribute("listaPracticas", listaPracticas);
        modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("bloqueId", bloqueId);
        modelo.addAttribute("inicia", cargaPracticaDao.fechaIni(codigoAlumno, cargaId, bloqueId));
        modelo.addAttribute("termina", cargaPracticaDao.fechaFin(codigoAlumno, cargaId, bloqueId));
        modelo.addAttribute("codigoAlumno", codigoAlumno);
        modelo.addAttribute("alumno", alumno);
		
		return "matricula/carga/practicas";
	}
	
	@RequestMapping("/matricula/carga/grabarPractica")
	public String matriculaCargaGrabarPractica(HttpServletRequest request, Model modelo){
		
		String cargaId				= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String bloqueId				= request.getParameter("BloqueId")==null?"0":request.getParameter("BloqueId");
		String fechaIni				= request.getParameter("Inicia")==null?"0":request.getParameter("Inicia");
		String fechaFin				= request.getParameter("Termina")==null?"0":request.getParameter("Termina");
		String comentario			= request.getParameter("Comentario")==null?"":request.getParameter("Comentario");
		String estado				= request.getParameter("Estado")==null?"A":request.getParameter("Estado");
		String codigoAlumno			= request.getParameter("CodigoAlumno")==null?"0":request.getParameter("CodigoAlumno");		
		CargaPracticaAlumno practica = new CargaPracticaAlumno();
		
		practica.setCodigoPersonal(codigoAlumno);
		practica.setFolio(cargaPracticaAlumnoDao.maxFolio(codigoAlumno));
		practica.setCargaId(cargaId);
		practica.setBloqueId(bloqueId);
		practica.setFechaIni(fechaIni);
		practica.setFechaFin(fechaFin);
		practica.setComentario(comentario);
		practica.setEstado(estado);
		if(cargaPracticaAlumnoDao.existeLibre(codigoAlumno, cargaId, "L") == false) {
			cargaPracticaAlumnoDao.insertReg(practica);
		}else {
			
		}
		
		return "redirect:/matricula/carga/practicas";
	}
	
	@RequestMapping("/matricula/carga/borrarPractica")
	public String matriculaCargaBorrarPractica(HttpServletRequest request, Model modelo){
		
		String codigoAlumno		= request.getParameter("CodigoAlumno")==null?"0":request.getParameter("CodigoAlumno");
		String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		
		if(cargaPracticaAlumnoDao.existeReg(codigoAlumno, folio)){
			cargaPracticaAlumnoDao.deleteReg(codigoAlumno, folio);
		}
		
		return "redirect:/matricula/carga/practicas";
	}
	
	@RequestMapping("/matricula/carga/grabaMateria")
	public String matriculaCargaGrabaMateria(HttpServletRequest request){
		String cursoCargaId 	= request.getParameter("CursoCargaId")==null?"-":request.getParameter("CursoCargaId");
		String cursoId 			= request.getParameter("CursoId")==null?"-":request.getParameter("CursoId");
		String cicloId 			= request.getParameter("Ciclo")==null?"-":request.getParameter("Ciclo");
		String bloqueId 		= request.getParameter("Bloque")==null?"0":request.getParameter("Bloque");
				
		String codigoAlumno		= "0";		
		boolean choca			= false;
		String restriccion 		= "S";
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion != null){	        	        	
			codigoAlumno	= (String) sesion.getAttribute("codigoAlumno");			
		}
		
		if (cargaGrupoDao.existeReg(cursoCargaId)) {
			restriccion = cargaGrupoDao.mapeaRegId(cursoCargaId).getRestriccion();
		}
		
		List<CargaHorario> lisCarga 	= cargaHorarioDao.getListCarga(codigoAlumno, cursoCargaId.substring(0, 6), bloqueId, "ORDER BY HORARIO_ID");
		List<CargaHorario> lisMateria 	= cargaHorarioDao.getLista(cursoCargaId, "ORDER BY HORARIO_ID");
		
		String chocaHorario = "-";
		if (restriccion.equals("S")) {
			for (CargaHorario carga : lisCarga){
				int horaCIni = Integer.parseInt(carga.getHoraInicio()+carga.getMinutoInicio());
				int horaCFin = Integer.parseInt(carga.getHoraFinal()+carga.getMinutoFinal());
				for (CargaHorario materia : lisMateria){
					int horaMIni = Integer.parseInt(materia.getHoraInicio()+materia.getMinutoInicio());
					int horaMFin = Integer.parseInt(materia.getHoraFinal()+materia.getMinutoFinal());
					if (carga.getDia().equals(materia.getDia()) && horaMIni >= horaCIni && horaMFin <= horaCFin ){					
						chocaHorario = "The timetable of "+cursoCargaId+" collides with "+carga.getCursoCargaId();
						choca = true;
					}				
				}
			}
		}
		
		KrdxCursoAct kardex 	= new KrdxCursoAct();
		String cursoOrigen 		= cargaGrupoDao.getCursoId(cursoCargaId);
		
		kardex.setCursoCargaId(cursoCargaId);
		kardex.setCodigoPersonal(codigoAlumno);
		kardex.setCursoId(cursoId);
		kardex.setCursoId2(cursoOrigen);
		kardex.setComentario("-");
		kardex.setCorreccion("N");
		kardex.setNota("0");
		kardex.setfNota(null);
		kardex.setNotaExtra("0");
		kardex.setfExtra(null);
		kardex.setTitulo("N");
		kardex.setfTitulo(null);
		kardex.setTipo("O");
		kardex.setTipoCalId("M");
		kardex.setCantidad("0");
		kardex.setConfirmar("S");
		
		if (choca==false) {
			if(!krdxCursoActDao.existeReg(codigoAlumno, cursoCargaId)) {
				krdxCursoActDao.insertReg(kardex);
			}else {
				krdxCursoActDao.updateReg(kardex);
			}
			// Verifica el estado del alumno en la carga
			String estado = cargaAlumnoDao.getEstado(codigoAlumno, cursoCargaId.substring(0, 6), bloqueId);
			if (estado.equals("1")){
				CargaAlumno alumno = cargaAlumnoDao.mapeaRegId(codigoAlumno, cursoCargaId.substring(0, 6), bloqueId);
				alumno.setEstado("2");
				cargaAlumnoDao.updateReg(alumno);
			}
		}
		
		return "redirect:/matricula/carga/materias?ChocaHorario="+chocaHorario+"&Ciclo="+cicloId;
	}
	
	@RequestMapping("/matricula/carga/semanas")
	public String matriculaCargaSemanas(HttpServletRequest request){
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();		
		String codigoAlumno		= "0";
		String cargaId			= "0";
		String cursoCargaId		= "0";
		String semanas			= "0";
		String frecuencia		= "0";
		
		List<KrdxCursoAct> lisCurso		= new ArrayList<KrdxCursoAct>();
		
		if (sesion != null){	        	        	
			codigoAlumno	= (String) sesion.getAttribute("codigoAlumno");
			 cargaId		= (String) sesion.getAttribute("cargaId");
			 lisCurso	= krdxCursoActDao.lisMateriasEnCarga(codigoAlumno, cargaId, " ORDER BY CURSO_ID");
		}
		
		int row = 0;
		for(KrdxCursoAct kardex :lisCurso) {
			row++;
			cursoCargaId 	= request.getParameter("CursoCargaId-"+row)==null?"0":request.getParameter("CursoCargaId-"+row);
			semanas			= request.getParameter("Semanas-"+row)==null?"0":request.getParameter("Semanas-"+row);
			frecuencia		= request.getParameter("Frecuenacia-"+row)==null?"0":request.getParameter("Frecuenacia-"+row);
			
			if(krdxCursoActDao.existeReg(codigoAlumno, cursoCargaId)) {
				kardex = krdxCursoActDao.mapeaRegId(codigoAlumno, cursoCargaId);
				kardex.setCantidad(semanas);
				kardex.setFrecuencia(frecuencia);
				krdxCursoActDao.updateCantidad(kardex);
			}		
		}
		
		return "redirect:/matricula/carga/materias";
	}
	
	@RequestMapping("/matricula/carga/borraMateria")
	public String matriculaCargaBorraMateria(HttpServletRequest request){
		String cursoCargaId 	= request.getParameter("CursoCargaId")==null?"-":request.getParameter("CursoCargaId");
		String bloqueId 		= request.getParameter("BloqueId")==null?"0":request.getParameter("BloqueId");
		String cargaId			= "0";
		boolean tienePractica	= false;
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();		
		String codigoAlumno		= "0";
		
		if (sesion != null){	        	        	
			codigoAlumno	= (String) sesion.getAttribute("codigoAlumno");
			cargaId			= (String) sesion.getAttribute("cargaId");
		}	
		
		HashMap<String,String> mapaPracticas 	= mapaCursoDao.mapaCursosEnPracticas();
		
		int total = 0;
		if(krdxCursoActDao.existeReg(codigoAlumno, cursoCargaId)) {
			if (krdxCursoActDao.deleteReg(codigoAlumno, cursoCargaId)) {
				total = alumnoCursoDao.totMaterias(codigoAlumno, cursoCargaId.substring(0, 6), bloqueId);
				// Si ya no hay materias registradas devolver el estado de la tabla Carga_Alumno a 1 = Solicitud
				if ( total == 0) {
					CargaAlumno alumno = cargaAlumnoDao.mapeaRegId(codigoAlumno, cursoCargaId.substring(0, 6), bloqueId);
					alumno.setEstado("1");
					cargaAlumnoDao.updateReg(alumno);
				}
				
				List<KrdxCursoAct> lisCurso	= krdxCursoActDao.lisMateriasEnCarga(codigoAlumno, cargaId, " ORDER BY CURSO_ID");	    
				
				for(KrdxCursoAct kardex : lisCurso){
					if (mapaPracticas.containsKey(kardex.getCursoId())){
						tienePractica = true;
					}
					if(tienePractica) break;
				}
				
				if(!tienePractica) {
					if (alumDiasDao.deleteReg(codigoAlumno, cargaId, bloqueId)) {
						// System.out.println("Borro dias");
					}						
					if (cargaPracticaAlumnoDao.deleteTodoReg(codigoAlumno, cargaId, bloqueId)){
						//System.out.println("Borro practicas");
					}
				}
			}			
		}
		
		return "redirect:/matricula/carga/materias";
	}
	
	@RequestMapping("/matricula/carga/costos")
	public String matriculaCargaCostos(HttpServletRequest request, Model modelo){
		
		String mensaje			= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		String codigoAlumno		= "0";
		String nombreAlumno		= "-";
		String cargaId			= "0";
		String bloqueId			= "0";
		String tipoAlumno		= "-";
		String planCarga		= "0";
		String ingreso			= "N";
		boolean existePagare	= false;
		
		AlumPlan plan 					= new AlumPlan();
		AlumPersonal personal 			= new AlumPersonal();
		AlumAcademico academico			= new AlumAcademico();
		MapaPlan mapaPlan				= new MapaPlan();
		CalAlumno calAlumno 			= new CalAlumno();
		List<MapaCurso> lisMaterias		= new ArrayList<MapaCurso>();
		List<CargaAcademica> lisOferta	= new ArrayList<CargaAcademica>();
		List<KrdxCursoAct> lisCurso		= new ArrayList<KrdxCursoAct>();
		
		if (sesion != null){	        	        	
			codigoAlumno	= (String) sesion.getAttribute("codigoAlumno");
	        nombreAlumno 	= usuariosDao.getNombreUsuario(codigoAlumno, "NOMBRE");
	        cargaId			= (String) sesion.getAttribute("cargaId");
	        bloqueId		= (String) sesion.getAttribute("bloqueId");
	        ingreso			= cargaAlumnoDao.getIngreso(codigoAlumno, cargaId, bloqueId);
	        
	        if (codigoAlumno.subSequence(0, 1).equals("0") || codigoAlumno.subSequence(0, 1).equals("1") || codigoAlumno.subSequence(0, 1).equals("2")){
	        	planCarga	= cargaAlumnoDao.getPlanId(codigoAlumno, cargaId, bloqueId);
	        	plan 		= alumPlanDao.mapeaRegId(codigoAlumno, planCarga);
	        	mapaPlan	= mapaPlanDao.mapeaRegId(plan.getPlanId());
	        	personal 	= alumPersonalDao.mapeaRegId(codigoAlumno);
        		academico	= alumAcademicoDao.mapeaRegId(codigoAlumno);
        		tipoAlumno	= catTipoAlumnoDao.getNombreTipo(academico.getTipoAlumno());
	        }
	        
	        lisMaterias		= mapaCursoDao.getLista(plan.getPlanId(), " ORDER BY CICLO, NOMBRE_CURSO");	        
	        lisOferta		= cargaAcademicaDao.getListaCarga(cargaId, plan.getPlanId(),academico.getModalidadId()," ORDER BY CICLO, NOMBRE_CURSO");
	        lisCurso		= krdxCursoActDao.lisMateriasConfirmadasEnCarga(codigoAlumno, cargaId, " ORDER BY CURSO_ID");
		}	
		
		if(calAlumnoDao.existeReg(codigoAlumno, cargaId, bloqueId)) {
			calAlumno = calAlumnoDao.mapeaRegId(codigoAlumno, cargaId, bloqueId);
			existePagare = true;
		}
		
		// Consulta el saldo del estudiante
		FinSaldo finSaldo 	= new FinSaldo();
		try {		
			RestTemplate restTemplate = new RestTemplate();
			finSaldo = restTemplate.getForObject("http://172.16.251.110:8080/infor/reportes/api/fe/saldo/"+codigoAlumno, FinSaldo.class);
		}catch(Exception ex) {
			System.out.println("Balance error:"+codigoAlumno);
		}
		
		List<CalPagareAlumno> listaParageAlumno = calPagareAlumnoDao.lisPorCarga(cargaId, bloqueId, "AND CODIGO_PERSONAL = "+codigoAlumno+" ORDER BY ENOC.CAL_PAGARE_ALUMNO.FECHA");
		
		List<CalMovimiento> listaMovimientos = calMovimientoDao.lisTodos("WHERE CODIGO_PERSONAL = '"+codigoAlumno+"' AND CARGA_ID = '"+cargaId+"' AND BLOQUE_ID = "+bloqueId);
		
		List<CalCosto> lisCostos						= calCostoDao.lisPorCarga(cargaId, bloqueId, "ORDER BY CONCEPTO_ID");		
		List<CalPagare> lisPagares						= calPagareDao.lisPorCarga(cargaId, bloqueId,"");
		List<CalConcepto> lisDescuentos					= calConceptoDao.lisTodos("WHERE TIPO = 'C'");
		HashMap<String,FinTabla> mapaCostos 			= finTablaDao.mapTablaCargas("'"+cargaId+"'");		
		HashMap<String,String> mapaMateriaCosto 		= cargaGrupoDao.mapaMateriaCosto(cargaId);
		HashMap<String,CalConcepto> mapaConceptos 		= calConceptoDao.mapaTodos();
		HashMap<String,String> mapaConceptoMovimientos  = calMovimientoDao.mapaConceptoMovimientos("WHERE CODIGO_PERSONAL = '"+codigoAlumno+"' AND CARGA_ID = '"+cargaId+"' AND BLOQUE_ID = "+bloqueId);
		
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("mapaConceptoMovimientos", mapaConceptoMovimientos);
		modelo.addAttribute("existePagare", existePagare);
		modelo.addAttribute("bloqueId", bloqueId);
		modelo.addAttribute("cargaNombre", cargaDao.getNombreCarga(cargaId));
		modelo.addAttribute("bloqueNombre", cargaBloqueDao.getNombre(cargaId, bloqueId));
		modelo.addAttribute("personal", personal);
		modelo.addAttribute("academico", academico);
		modelo.addAttribute("tipoAlumno", tipoAlumno);
		modelo.addAttribute("calAlumno", calAlumno);
		modelo.addAttribute("ingreso", ingreso);
		modelo.addAttribute("finSaldo", finSaldo);
		
		modelo.addAttribute("lisMaterias", lisMaterias);
		modelo.addAttribute("lisCostos", lisCostos);		
		modelo.addAttribute("lisOferta", lisOferta);
		modelo.addAttribute("lisCurso", lisCurso);
		modelo.addAttribute("lisPagares", lisPagares);
		modelo.addAttribute("listaMovimientos", listaMovimientos);
		modelo.addAttribute("lisDescuentos", lisDescuentos);
		modelo.addAttribute("listaParageAlumno", listaParageAlumno);		
		
		modelo.addAttribute("mapaCostos", mapaCostos);
		modelo.addAttribute("mapaMateriaCosto", mapaMateriaCosto);
		modelo.addAttribute("mapaPlan", mapaPlan);
		modelo.addAttribute("mapaConceptos", mapaConceptos);
		
		return "matricula/carga/costos";
	}
	
	@RequestMapping("/matricula/carga/verCostos")
	public ResponseEntity<byte[]> portalesAlumnoVerCosto(HttpServletRequest request, Model modelo) throws IOException {
		String matricula = "0";
		String codigoPersonal = "0";
		String cargaId = "0";
		String bloqueId = "0";
		
		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
			codigoPersonal = (String) sesion.getAttribute("codigoPersonal");
			if (cargaId.equals("0")) {
				cargaId = (String) sesion.getAttribute("cargaId");
			} else {
				sesion.setAttribute("cargaId", cargaId);
			}
			if (bloqueId.equals("0")) {
				bloqueId = (String) sesion.getAttribute("bloqueId");
			} else {
				sesion.setAttribute("bloqueId", bloqueId);
			}
		}
		
		FinCalculo finCalculo = finCalculoDao.mapeaRegId(matricula, cargaId, bloqueId);
		byte[] pdf = finCalculo.getArchivo();
		
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", matricula + ".pdf");
        
        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);	
	}
	
	@Transactional
	@RequestMapping("/matricula/carga/grabarPagare")
	public String matriculaCargaGrabarPagare(HttpServletRequest request){
		String cargaId			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String bloqueId			= request.getParameter("BloqueId")==null?"0":request.getParameter("BloqueId");
		String numPagares		= request.getParameter("NumPagare")==null?"0":request.getParameter("NumPagare");
		String cobroMatricula	= request.getParameter("CobroMatricula")==null?"0":request.getParameter("CobroMatricula");
		String saldo			= request.getParameter("Saldo")==null?"0":request.getParameter("Saldo");
		String porcentaje		= request.getParameter("Porcentaje")==null?"35":request.getParameter("Porcentaje");
		String totMat			= request.getParameter("TotMat")==null?"0":request.getParameter("TotMat");
		String totEns			= request.getParameter("TotEns")==null?"0":request.getParameter("TotEns");
		String totDes			= request.getParameter("TotDes")==null?"0":request.getParameter("TotDes");		
		String mensaje 			= "0";
		String ingreso 			= "N";
		String planId			= "00000000";
		String facultadId		= "000";
		
		if (cobroMatricula.equals("N")) totMat = "0";
		
		float netoSaldo			= Float.valueOf(saldo);
		float netoMat			= Float.valueOf(totMat);
		float netoEns			= Float.valueOf(totEns);
		float netoDes			= Float.valueOf(totDes);
		float netoTotal 		= 0;
		float netoInicial 		= 0;
		float sumaPagares		= 0;		
		
		java.text.DecimalFormat formato = new java.text.DecimalFormat("###,##0.00;-###,##0.00");
		//System.out.println("Saldo:"+saldo);
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		String codigoAlumno		= "0";		
		if (sesion != null){	        	        	
			codigoAlumno	= (String) sesion.getAttribute("codigoAlumno");
			ingreso			= cargaAlumnoDao.getIngreso(codigoAlumno, cargaId, bloqueId);
			planId			= cargaAlumnoDao.getPlanId(codigoAlumno, cargaId, bloqueId);
			facultadId		= catCarreraDao.getFacultadId(mapaPlanDao.getCarreraId(planId));
			if (Float.valueOf(saldo) < 0) { 
				netoMat 	= netoMat + Math.abs(Float.valueOf(saldo));
				netoSaldo 	= 0;
			}else{
				if (netoSaldo > netoMat){
					netoSaldo 	= netoSaldo - netoMat;
					netoMat 	= 0;
				}else {
					netoMat 	= netoMat - netoSaldo;
					netoSaldo 	= 0;
				}
			}
			//System.out.println("NetoMat:"+netoMat+" NetoSaldo:"+netoSaldo);
			if (netoDes > netoEns) {
				 netoEns = 0;
				 netoDes = netoDes-netoEns;
			}else {
				netoEns = netoEns - netoDes;
			}
			 
			netoTotal = netoMat + netoEns;
			// La facultad 113 es UM Virtual
			if (ingreso.equals("R") && facultadId.equals("113") ){
				if (netoSaldo>=netoTotal){
					netoTotal 	= 0;
					netoSaldo 	= netoSaldo - netoTotal; 
					netoInicial = 0;
					sumaPagares	= 0;
				}else {
					netoTotal 	= netoTotal - netoSaldo;
					netoSaldo 	= 0; 
					netoInicial = netoTotal/Integer.parseInt(numPagares);					
					sumaPagares = netoTotal - netoInicial;
				}				
			}else {
				netoInicial = netoMat + (netoEns*Float.valueOf(porcentaje)/100);
				if (netoSaldo >= netoTotal) {
					netoInicial = 0;
					netoTotal 	= 0;
					netoSaldo 	= netoSaldo - netoTotal;
					sumaPagares = 0;
				}else if (netoSaldo > netoInicial) {
					netoTotal = netoTotal - netoSaldo;
					netoInicial = 0;
					netoSaldo = netoSaldo - netoInicial;
					sumaPagares = netoTotal;
				}else {					
					netoInicial = netoInicial - netoSaldo;
					netoTotal 	= netoTotal - netoInicial - netoSaldo;
					netoSaldo 	= 0;					
					sumaPagares = netoTotal;
				}
				//sumaPagares = netoTotal - netoInicial;
			}
		}
		
		CalAlumno alumno = new CalAlumno();
		
		alumno.setCodigoPersonal(codigoAlumno);
		alumno.setCargaId(cargaId);
		alumno.setBloqueId(bloqueId);
		alumno.setNumPagare(numPagares);
		alumno.setCobroMatricula(cobroMatricula);
		alumno.setSaldo(saldo);
		alumno.setPorcentaje(porcentaje);		
		
		if(calAlumnoDao.existeReg(codigoAlumno, cargaId, bloqueId)) {
			if(calAlumnoDao.updateReg(alumno)) {
				mensaje = "1";
			}else {
				mensaje = "2";
			}
		}else {
			if(calAlumnoDao.insertReg(alumno)) {
				mensaje = "1";
			}else {
				mensaje = "2";
			}
		}
		
		CalPagareAlumno pagareAlumno = new CalPagareAlumno();

		List<CalPagare> lisPagares 				= calPagareDao.lisPorCarga(cargaId, bloqueId,"");
		
		pagareAlumno.setCodigoPersonal(codigoAlumno);
		pagareAlumno.setBloqueId(bloqueId);
		pagareAlumno.setCargaId(cargaId);
		if(mensaje.equals("1")) {
			if(calPagareAlumnoDao.existeReg(codigoAlumno,cargaId,bloqueId)){
				calPagareAlumnoDao.deleteReg(codigoAlumno,cargaId,bloqueId);
			}
			
			int row = 1;		
			for(CalPagare pagare : lisPagares){
				if(numPagares.equals("1")){	
					pagareAlumno.setPagareNombre("One-time Payment");
					pagareAlumno.setFecha(pagare.getFecha());
					pagareAlumno.setImporte(String.valueOf(formato.format(netoTotal)));
					pagareAlumno.setPagareId(pagare.getPagareId());					
					calPagareAlumnoDao.insertReg(pagareAlumno);					
					break;
				}else if(row <= Integer.parseInt(numPagares)){					
					
					float pago = sumaPagares / (Integer.parseInt(numPagares)-1);				
					
					if(row == 1) {
						pagareAlumno.setPagareNombre(pagare.getPagareNombre());
						pagareAlumno.setImporte(String.valueOf(formato.format(netoInicial)));
					}else {
						pagareAlumno.setPagareNombre(pagare.getPagareNombre());
						pagareAlumno.setImporte(String.valueOf(formato.format(pago)));
					}					
					pagareAlumno.setPagareId(pagare.getPagareId());
					pagareAlumno.setFecha(pagare.getFecha());
					
					calPagareAlumnoDao.insertReg(pagareAlumno);					
 				}
				row++;
			}
		}
		
		return "redirect:/matricula/carga/costos?Mensaje="+mensaje;
	}
	
	@RequestMapping("/matricula/carga/modificarPagare")
	public String matriculaCargaModificarPagare(HttpServletRequest request){
		String cargaId	= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String bloqueId	= request.getParameter("BloqueId")==null?"0":request.getParameter("BloqueId");
		String mensaje 	= "0";
		
		java.text.DecimalFormat formato = new java.text.DecimalFormat("###,##0.00;-###,##0.00");
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		String codigoAlumno		= "0";		
		if (sesion != null){	        	        	
			codigoAlumno	= (String) sesion.getAttribute("codigoAlumno");	
		}
		
		CalPagareAlumno pagareAlumno = new CalPagareAlumno();
		
		List<CalPagare> lisPagares = calPagareDao.lisPorCarga(cargaId, bloqueId,"");
		
		for(CalPagare pagare : lisPagares){
			String pagar	= request.getParameter(pagare.getPagareId())==null?"0":request.getParameter(pagare.getPagareId());
			String fecha	= request.getParameter("Fecha"+pagare.getPagareId())==null?"0":request.getParameter("Fecha"+pagare.getPagareId());
			if(!pagar.equals("0")){
				if(calPagareAlumnoDao.existePagareReg(codigoAlumno,pagare.getPagareId())){
					pagareAlumno = calPagareAlumnoDao.mapeaRegId(codigoAlumno, pagare.getPagareId());
					String importe = formato.format(Float.parseFloat(pagar));
					pagareAlumno.setImporte(importe);
					pagareAlumno.setFecha(fecha);
					if(calPagareAlumnoDao.updateReg(pagareAlumno)) {
						mensaje = "1";
					}else {
						mensaje = "2";
					}
				}
			}
		}
		
		return "redirect:/matricula/carga/costos?Mensaje="+mensaje;
	}
	
	@RequestMapping("/matricula/carga/grabarMovimiento")
	public String matriculaCargaGrabarMovimiento(HttpServletRequest request){
		String conceptoId	= request.getParameter("ConceptoId")==null?"0":request.getParameter("ConceptoId");
		String tipo			= request.getParameter("Tipo")==null?"0":request.getParameter("Tipo");
		String importe		= request.getParameter("Importe")==null?"0":request.getParameter("Importe");
		String cargaId		= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String bloqueId		= request.getParameter("BloqueId")==null?"0":request.getParameter("BloqueId");
		String movimientoId = calMovimientoDao.maximoReg();
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		String codigoAlumno		= "0";		
		if (sesion != null){	        	        	
			codigoAlumno	= (String) sesion.getAttribute("codigoAlumno");	
		}
		
		CalMovimiento objeto = new CalMovimiento();
		
		objeto.setMovimientoId(movimientoId);
		objeto.setCodigoPersonal(codigoAlumno);
		objeto.setConceptoId(conceptoId);
		objeto.setTipo(tipo);
		objeto.setImporte(importe);
		objeto.setCargaId(cargaId);
		objeto.setBloqueId(bloqueId);
		
		calMovimientoDao.insertReg(objeto);
		
		return "redirect:/matricula/carga/costos";
	}
	
	@RequestMapping("/matricula/carga/grabarDescuento")
	public String matriculaCargaGrabarDescuento(HttpServletRequest request){
		String conceptoId	= request.getParameter("ConceptoId")==null?"0":request.getParameter("ConceptoId");
		String tipo			= request.getParameter("Tipo")==null?"0":request.getParameter("Tipo");
		String importe		= request.getParameter("Importe")==null?"0":request.getParameter("Importe");
		String cargaId		= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String bloqueId		= request.getParameter("BloqueId")==null?"0":request.getParameter("BloqueId");
		String movimientoId = calMovimientoDao.maximoReg();
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		String codigoAlumno		= "0";		
		if (sesion != null){	        	        	
			codigoAlumno	= (String) sesion.getAttribute("codigoAlumno");	
		}
		
		CalMovimiento objeto = new CalMovimiento();
		
		objeto.setMovimientoId(movimientoId);
		objeto.setCodigoPersonal(codigoAlumno);
		objeto.setConceptoId(conceptoId);
		objeto.setTipo(tipo);
		objeto.setImporte(importe);
		objeto.setCargaId(cargaId);
		objeto.setBloqueId(bloqueId);
		
		calMovimientoDao.insertReg(objeto);
		
		return "redirect:/matricula/carga/costos";
	}
	
	@RequestMapping("/matricula/carga/quitarMovimiento")
	public String matriculaCargaQuitarMovimiento(HttpServletRequest request){
		String movimientoId	= request.getParameter("MovimientoId")==null?"0":request.getParameter("MovimientoId");
		
		if(calMovimientoDao.existeReg(movimientoId)) {
			calMovimientoDao.deleteReg(movimientoId);
		}
		
		return "redirect:/matricula/carga/costos";
	}
	
	@RequestMapping("/matricula/carga/quitarConfirmacion")
	public String matriculaCargaQuitarConfirmacion(HttpServletRequest request){
		String codigoAlumno	= request.getParameter("CodigoPersonal")==null?"0":request.getParameter("CodigoPersonal");
		String cargaId		= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String bloqueId		= request.getParameter("BloqueId")==null?"0":request.getParameter("BloqueId");
		
		if(calAlumnoDao.existeReg(codigoAlumno, cargaId, bloqueId)) {
			calAlumnoDao.updateConfirmar(codigoAlumno, cargaId, bloqueId, "N");
		}
		
		return "redirect:/matricula/carga/costos";
	}
	
	@RequestMapping("/matricula/carga/calcularCostos")
	public String matriculaCargaCalcularCostos(HttpServletRequest request){
		/*
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		String codigoAlumno		= "0";		
		if (sesion != null){	        	        	
			codigoAlumno	= (String) sesion.getAttribute("codigoAlumno");	
		}
		*/
		return "redirect:/matricula/carga/costos";
	}
	
	@RequestMapping("/matricula/carga/resumen")
	public String matriculaCargaResumen(HttpServletRequest request, Model modelo) {
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		
		String candidato		= request.getParameter("sltCandidato")==null?"N":request.getParameter("sltCandidato");
		String eventoId			= request.getParameter("sltEvento")==null?"0":request.getParameter("sltEvento");
		String dedicatoria		= request.getParameter("txtDedicatoria")==null?"":request.getParameter("txtDedicatoria");
		String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		
		String codigoPersonal 	= "0";
		String codigoAlumno		= "0";
		String nombreAlumno		= "-";
		String periodoId		= "0";
		String cargaId			= "0";
		String bloqueId			= "0";
		String tipoAlumno		= "-";
		float saldoAnterior		= 0;
		double saldoGlobal		= 0;
		String carreraId		= "0";
		String modalidadId		= "1";
		String planCarga		= "0";
		String estado			= "";
		String fechaEgreso		= "";
		
		AlumEgreso alumnoEgreso			= new AlumEgreso();
		AlumPlan plan 					= new AlumPlan();
		MapaPlan mapaPlan				= new MapaPlan();
		AlumPersonal personal 			= new AlumPersonal();
		AlumAcademico academico			= new AlumAcademico();
		List<KrdxCursoAct> lisCurso		= new ArrayList<KrdxCursoAct>();
		List<MapaCurso> lisMaterias		= new ArrayList<MapaCurso>();
		List<AlumEvento> lisEventos		= new ArrayList<AlumEvento>();
		
		if (sesion != null) {
			codigoPersonal	= (String) sesion.getAttribute("codigoPersonal");
			codigoAlumno	= (String) sesion.getAttribute("codigoAlumno");
	        nombreAlumno 	= usuariosDao.getNombreUsuario(codigoAlumno, "NOMBRE");
	        saldoGlobal 	= 0;	        	        
	        periodoId		= (String) sesion.getAttribute("periodo");
	        cargaId			= (String) sesion.getAttribute("cargaId");
	        bloqueId		= (String) sesion.getAttribute("bloqueId");
	        
	        if (codigoAlumno.subSequence(0, 1).equals("0") || codigoAlumno.subSequence(0, 1).equals("1") || codigoAlumno.subSequence(0, 1).equals("2")){	        	
	        	planCarga	= cargaAlumnoDao.getPlanId(codigoAlumno, cargaId, bloqueId);
	        	plan 		= alumPlanDao.mapeaRegId(codigoAlumno, planCarga);
	        	mapaPlan 	= mapaPlanDao.mapeaRegId(plan.getPlanId());
	        	personal 	= alumPersonalDao.mapeaRegId(codigoAlumno);
        		academico	= alumAcademicoDao.mapeaRegId(codigoAlumno);
        		tipoAlumno	= catTipoAlumnoDao.getNombreTipo(academico.getTipoAlumno());
        		carreraId	= mapaPlan.getCarreraId();
        		modalidadId = academico.getModalidadId();
        		
        		estado = cargaAlumnoDao.getEstado(codigoAlumno, cargaId, bloqueId);
    			if (estado.equals("1")||estado.equals("2")){
    				CargaAlumno alumno = cargaAlumnoDao.mapeaRegId(codigoAlumno, cargaId.substring(0, 6), bloqueId);
    				alumno.setEstado("3");
    				cargaAlumnoDao.updateReg(alumno);
    			}		
	        }
	        
	        lisCurso		= krdxCursoActDao.lisMateriasEnCarga(codigoAlumno, cargaId, " ORDER BY CURSO_ID");
	        lisMaterias 	= mapaCursoDao.getMateriasFaltantes(codigoAlumno, plan.getPlanId(), " ORDER BY CURSO_ID");
	        lisEventos 		= alumEventoDao.getListProximo(Integer.toString(Calendar.getInstance().get(Calendar.YEAR)), " ORDER BY EVENTO_ID");
	        
	        if(accion.equals("1")) {
	        	sesion.setAttribute("accion", accion);
	        }else if(sesion.getAttribute("accion") == null) {
	        	sesion.setAttribute("accion", "0");
	        }
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String fecha 	= sdf.format(Calendar.getInstance().getTime());
		int days 		= 0;
		if(alumEgresoDao.existeRegAlum(codigoAlumno)) {
			fechaEgreso = alumEgresoDao.mapeaRegFecha(codigoAlumno).getFecha();
			Date date1;
			Date date2;
			long diff = 0;
			try {
				date1 = new SimpleDateFormat("yyyy-MM-dd").parse(fechaEgreso);
				fechaEgreso = sdf.format(date1);
				date1 = sdf.parse(fechaEgreso);
				date2 = sdf.parse(fecha);
				diff = date2.getTime() - date1.getTime();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			days = (int)(diff / (1000*60*60*24));
		}
		
		/* VERIFICAR SI ES UTIL ESTA FUNCION INSERT ALUM_EGRESO **/
		if(accion.equals("1") && candidato.equals("S") && !alumEgresoDao.existeReg(eventoId, codigoAlumno)) {
			alumnoEgreso.setEventoId(eventoId);
			alumnoEgreso.setCodigoPersonal(codigoAlumno);
			alumnoEgreso.setCarreraId(carreraId);
			alumnoEgreso.setPlanId(plan.getPlanId());
			alumnoEgreso.setAvance("C");
			alumnoEgreso.setTitulado(candidato);
			alumnoEgreso.setPromedio("0");
			alumnoEgreso.setFecha(fecha);
			alumnoEgreso.setUsuario(codigoPersonal);
			alumnoEgreso.setDiploma(dedicatoria);
			alumnoEgreso.setPermiso("-");
			alumnoEgreso.setConfirmar("N");
			alumnoEgreso.setComentario("-");
			alumEgresoDao.insertReg(alumnoEgreso);
		}
		
		boolean autorizado = archivoDao.autorizaDocumentosAlumno(codigoAlumno, plan.getPlanId());			
		
		List<CandAlumno> lisCandados	= candAlumnoDao.getLista(codigoAlumno, "A", " ORDER BY FOLIO");
		boolean permisoExtranjero 		= legPermisosDao.tienePermiso(codigoAlumno, aca.util.Fecha.getHoy(), "A");
		
		// Validar extranjeros
		boolean autorizaExt = false;
		if(!personal.getNacionalidad().equals("91")) {	
			List<String> lisGrupos 			= legCondicionesDao.lisGrupoId();
			List<LegExtdoctos> lisVigentes = legExtDocDao.listaDocVigentes(codigoAlumno);
			for(String grupo : lisGrupos) {
				List<String> lisDocumentos = legCondicionesDao.lisDocsGrupoId(grupo);
				int row=0;
				for (String doc : lisDocumentos){
					for (LegExtdoctos legDoc : lisVigentes) {
						if (legDoc.getIdDocumento().equals(doc)) {
							row++;
						}
					}
				}
				if (lisDocumentos.size() == row) {
					autorizaExt = true;
				}
			}
			
			if(autorizaExt == false && permisoExtranjero == false) {
				boolean tiene = false;
				for (CandAlumno candado : lisCandados) {
					if (candado.getCandadoId().equals("0601")) {
						tiene = true;
					}
				}
				
				CandAlumno can = new CandAlumno();
				
				if(!tiene) {
					can.setCodigoPersonal(codigoAlumno);
					can.setFolio(candAlumnoDao.maximoReg(codigoAlumno));
					can.setfCreado(aca.util.Fecha.getHoy());
					can.setUsAlta("9801187");
					can.setEstado("A");
					can.setComentario("COMENTARIO");
					can.setCandadoId("0601");
					candAlumnoDao.insertReg(can);
				}
			}else {
				candAlumnoDao.deleteCandadoActivo(codigoAlumno, "0601", "A");
			}

			LegExtdoctos legExtDoctos 	= new LegExtdoctos();
			String fechaVencimiento		= alumPersonalDao.getVencimientoFM3(codigoAlumno);  
			
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    		Date hoy 			= new Date();
    		Date fechav 		= null;
			try {
				fechav = df.parse(fechaVencimiento);
			} catch (ParseException e) {				
				e.printStackTrace();
			}
    		int dias 			= new Long((fechav.getTime()-hoy.getTime())/1000/60/60/24).intValue();
    		if(dias < 30){
    			if(dias <= 0){
    				if(legExtdoctosDao.existeReg(codigoAlumno, "4")) {
    					legExtDoctos = legExtdoctosDao.mapeaRegId(codigoAlumno, "4");
    					legExtDoctos.setFechaVence("31/12/20");
    					legExtdoctosDao.updateReg(legExtDoctos);
    				}else {
    					legExtDoctos.setCodigo(codigoAlumno);
    					legExtDoctos.setEstado("1");
    					legExtDoctos.setFecha(Fecha.getHoy());
    					legExtDoctos.setFechaTramite(Fecha.getHoy());
    					legExtDoctos.setFechaVence("31/12/20");
    					legExtDoctos.setIdDocumento("4");
    					legExtDoctos.setNumDocto("-");
    				}
    			}
    		}						
			
		}else {
			autorizaExt = true;
		}
		
		HashMap<String,String> mapaMaestros 			= maestrosDao.mapMaestroNombre("CORTO");
		HashMap<String,CatModalidad> mapaModalidades 	= catModalidadDao.getMapAll("");
		HashMap<String,MapaCurso> mapaCurso 			= mapaCursoDao.getMapaCursos(plan.getPlanId(), "");
		HashMap<String,CandTipo> mapaCandTipo 			= candTipoDao.mapTipo();
		HashMap<String,FinTabla> mapaCostos				= finTablaDao.mapTablaCargas("'"+cargaId+"'");	

		HashMap<String,Candado> mapaCandados = candadoDao.getMapaCandados();
		
		//Candados
		String fechaHoy			= aca.util.Fecha.getHoy();
		for (CandAlumno candado : lisCandados) {
			if (candado.getCandadoId().equals("0401") && saldoGlobal > -3000) {
				candado.setfBorrado(fechaHoy);
				candado.setUsBaja("9800000");
				candado.setEstado("I");
				candAlumnoDao.removerCand(candado);
			}
			if (candado.getCandadoId().equals("1001") && saldoGlobal > -60000) {
				candado.setfBorrado(fechaHoy);
				candado.setUsBaja("9800000");
				candado.setEstado("I");
				candAlumnoDao.removerCand(candado);
			}
		}

		// Actualizar lista
		lisCandados		= candAlumnoDao.getLista(codigoAlumno, "A", " ORDER BY FOLIO");		
		boolean permisoFin = false;
		if (finPermisoDao.tienePermiso(codigoAlumno)) {
			permisoFin = true;
		}
		
		for (int row=0; row< lisCandados.size(); row++) {
			CandAlumno candAlumno = lisCandados.get(0);
			if (candAlumno.getCandadoId().equals("0401") && permisoFin) {				
				lisCandados.remove(row);
			}
		}
		
		
		//Candado Salud
		String msj = "-";
		AlertaPeriodo	alertaPeriodo 	= new AlertaPeriodo();
		AlertaDatos	alertaDatos			= new AlertaDatos();
		String periodoAlerta 			= alertaPeriodoDao.getPeriodoActual();
		if (!periodoAlerta.equals("0")) {
			alertaPeriodo = alertaPeriodoDao.mapeaRegId(periodoAlerta);
			if (!alertaDatosDao.existeReg(periodoAlerta, codigoAlumno)) {
				if (!alertaPeriodo.getExcepto().contains(plan.getPlanId()) && alertaPeriodo.getModalidades().contains("-"+modalidadId+"-")) {
					msj = "El Alumno tiene un candado en el módulo de salud";
				}
			}else{
				alertaDatos = alertaDatosDao.mapeaRegId(periodoAlerta, codigoAlumno);
				if (!alertaDatos.getEstado().equals("A") && alertaPeriodo.getModalidades().contains("-"+modalidadId+"-")) {
					msj = "El Alumno tiene un candado en el módulo de salud";
				}
			}
		}
		
		List<CargaPracticaAlumno> lisPracticas 	= cargaPracticaAlumnoDao.lisPorAlumnoyEstado(codigoAlumno, cargaId, "A","");		
		int dias = 0;	
		Carga carga = new Carga();
		if(cargaDao.existeReg(cargaId)) {
			carga = cargaDao.mapeaRegId(cargaId);
		}		
		// Si el alumno es interno y es requerido todo el semestre
		if (academico.getResidenciaId().equals("I") && academico.getRequerido().equals("S")){
			try {
				dias = aca.util.Fecha.diasEntreFechas(carga.getIniInternado(), carga.getFinInternado()) + 1;
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}else if(academico.getResidenciaId().equals("I") && lisPracticas.size() > 0) {			
			for (CargaPracticaAlumno practica : lisPracticas){
				try {
					dias += aca.util.Fecha.diasEntreFechas(practica.getFechaIni(), practica.getFechaFin()) + 1;
				} catch (Exception e) {
					e.printStackTrace();
				}	
			}
		}
		
		AlumDias alumDias = new AlumDias();
			
		alumDias.setCodigoPersonal(codigoAlumno);
		alumDias.setCargaId(cargaId);
		alumDias.setBloqueId(bloqueId);
		alumDias.setDias(String.valueOf(dias));
			
		if(alumDiasDao.existeReg(codigoAlumno, cargaId, bloqueId)) {
			alumDiasDao.updateReg(alumDias);
		}else {
			alumDiasDao.insertReg(alumDias);
		}
		
		String planOficial			= mapaPlanDao.getOficial(planCarga);
		boolean asuntosPreviosPendientes = false;
		if((modalidadId.equals("1") || modalidadId.equals("4")) && (nseRespuestaDao.encuestaPendiente(codigoAlumno)==false || alumUbicacionDao.tieneVacuna(codigoAlumno)==false) && planOficial.equals("S")){
			asuntosPreviosPendientes = true;
		}
		
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("bloqueId", bloqueId);
		modelo.addAttribute("periodoNombre", catPeriodoDao.getNombre(periodoId));
		modelo.addAttribute("cargaNombre", cargaDao.getNombreCarga(cargaId));
		modelo.addAttribute("bloqueNombre", cargaBloqueDao.getNombre(cargaId, bloqueId));
		modelo.addAttribute("plan", plan);
		modelo.addAttribute("personal", personal);
		modelo.addAttribute("academico", academico);	
		modelo.addAttribute("tipoAlumno", tipoAlumno);	
		modelo.addAttribute("lisCurso", lisCurso);
		modelo.addAttribute("lisMaterias", lisMaterias);
		modelo.addAttribute("lisEventos", lisEventos);
		modelo.addAttribute("lisCandados", lisCandados);
		modelo.addAttribute("mapaMaestros", mapaMaestros);
		modelo.addAttribute("mapaModalidades", mapaModalidades);
		modelo.addAttribute("mapaCurso", mapaCurso);
		modelo.addAttribute("mapaCandados", mapaCandados);
		modelo.addAttribute("msj", msj);
		modelo.addAttribute("autorizado", autorizado);
		modelo.addAttribute("mapaCandTipo", mapaCandTipo);
		modelo.addAttribute("days", days);
		modelo.addAttribute("autorizaExt", autorizaExt);
		modelo.addAttribute("mapaCostos", mapaCostos);
		modelo.addAttribute("mapaPlan", mapaPlan);
		modelo.addAttribute("dias", String.valueOf(dias));
		modelo.addAttribute("asuntosPreviosPendientes", asuntosPreviosPendientes);
		
		return "matricula/carga/resumen";
	}
	
	@RequestMapping("/matricula/carga/verCobro")
	public String matriculaCargaVerCobro(HttpServletRequest request){
		
		String codigoAlumno = "0";
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion != null){	        	        	
			codigoAlumno	= (String) sesion.getAttribute("codigoAlumno");
		}
		
		LogProceso proceso = new LogProceso();
		
		proceso.setFolio(String.valueOf(logProcesoDao.maximoReg()));
		proceso.setCodigoPersonal(codigoAlumno);
		proceso.setModulo("CARGA MATERIAS");
		proceso.setFecha(Fecha.getHoy());
		proceso.setEvento("TERMINA-INSERTAR");
		
		logProcesoDao.insertReg(proceso);
		return "redirect:/matricula/carga/resumen";
	}	
}