package aca.web.trabajo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.trabajo.spring.TrabDepartamento;
import aca.trabajo.spring.TrabCategoria;
import aca.trabajo.spring.TrabCategoriaDao;
import aca.trabajo.spring.TrabDepartamentoDao;
import aca.trabajo.spring.TrabInformeAlumDao;
import aca.trabajo.spring.TrabPeriodo;
import aca.trabajo.spring.TrabPeriodoDao;
import aca.alumno.spring.AlumAcademico;
import aca.alumno.spring.AlumAcademicoDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanDao;
import aca.emp.spring.EmpMaestro;
import aca.emp.spring.EmpMaestroDao;
import aca.financiero.spring.FinQuoteConceptDao;
import aca.plan.spring.MapaPlanDao;
import aca.residencia.spring.ResComentario;
import aca.residencia.spring.ResComentarioDao;
import aca.trabajo.spring.TrabAlum;
import aca.trabajo.spring.TrabAlumDao;
import aca.trabajo.spring.TrabAsesor;
import aca.trabajo.spring.TrabAsesorDao;

@Controller
public class ContTrabajoReportes {

    private final FinQuoteConceptDao finQuoteConceptDao;

    @Autowired
	private TrabInformeAlumDao trabInformeAlumDao;

    @Autowired
    private TrabPeriodoDao trabPeriodoDao;

    @Autowired
    private TrabAlumDao trabAlumDao;

    @Autowired
    private TrabCategoriaDao trabCategoriaDao;

    @Autowired
    private TrabDepartamentoDao trabDepartamentoDao;

    @Autowired
    private MapaPlanDao mapaPlanDao;

    @Autowired
    private AlumPersonalDao alumPersonalDao;

    @Autowired
    private AlumAcademicoDao alumAcademicoDao;

    @Autowired
    private AlumPlanDao alumPlanDao;

    @Autowired
    private EmpMaestroDao EmpMaestroDao;

    @Autowired
    private TrabAsesorDao trabAsesorDao;

    @Autowired
    private ResComentarioDao resComentarioDao;

    ContTrabajoReportes(FinQuoteConceptDao finQuoteConceptDao) {
        this.finQuoteConceptDao = finQuoteConceptDao;
    }

    @RequestMapping("/trabajo/reportes/menu")
	public String trabajoReportesMenu(HttpServletRequest request){		
		return "trabajo/reportes/menu";
	}

    @RequestMapping("/trabajo/reportes/horasAlumno")
    public String trabajoReportesHorasAlumno(HttpServletRequest request, Model modelo){
        String periodoId  = request.getParameter("PeriodoId")==null?"1":request.getParameter("PeriodoId");
        String deptId     = request.getParameter("DeptId")==null?"0":request.getParameter("DeptId");
        String catId      = request.getParameter("CatId")==null?"0":request.getParameter("CatId");
        String grado      = request.getParameter("Grado")==null?"0":request.getParameter("Grado");

        List<TrabAlum> lisAlumnos = new ArrayList<>();
        String queryGrado = "";
        if(!grado.equals("0")) queryGrado = " AND MATRICULA IN (SELECT CODIGO_PERSONAL FROM ALUM_PLAN WHERE GRADO = '"+grado+"' AND PRINCIPAL = '1')";

        List<TrabPeriodo> lisPeriodos =trabPeriodoDao.lisTodos(" ORDER BY PERIODO_ID");
        List<TrabDepartamento> lisDepartamentos = trabDepartamentoDao.lisTodos("");
        List<TrabCategoria> lisCategorias = trabCategoriaDao.lisPorDepartamento(deptId, "");

        if(deptId.equals("0")){
            lisAlumnos = trabAlumDao.lisPorPeriodo(periodoId, queryGrado+" ORDER BY a.NOMBRE");
        }  
        else if(!deptId.equals("0") && catId.equals("0")){
            lisAlumnos = trabAlumDao.lisPorDeptyPeriodo(periodoId, deptId, queryGrado+" ORDER BY a.NOMBRE");
        }
        else if(!deptId.equals("0") && !catId.equals("0")){
            lisAlumnos = trabAlumDao.lisPorDeptyPeriodoCategoria(periodoId, deptId, catId, queryGrado+" ORDER BY a.NOMBRE");
        }

        HashMap<String,AlumPersonal> mapaAlumnos = alumPersonalDao.mapAlumnosTrabajos();
        HashMap<String,AlumAcademico> mapaAcademico = alumAcademicoDao.getMapAcademico();
        HashMap<String,AlumPlan> mapaAlumPlan = alumPlanDao.mapAlumPlanActivo();
        HashMap<String,String> mapHorasRegistradas = trabInformeAlumDao.mapHorasRegistradasPorPeriodo(periodoId);
        HashMap<String,String> mapHorasTotalesRegistradas = trabInformeAlumDao.mapHorasTotalesRegistradasPorPeriodo(periodoId);
        HashMap<String,String> mapCategorias = trabCategoriaDao.mapaCategoriaNombre();
        HashMap<String,String> mapDepartamentos = trabDepartamentoDao.mapaDeptNombre(" ORDER BY DEPT_ID");
        HashMap<String,String> mapaPlanes = mapaPlanDao.mapNombrePlan();
        HashMap<String,ResComentario> mapaMaxiComentario = resComentarioDao.mapMaxiComentario();

        modelo.addAttribute("periodoId", periodoId);
        modelo.addAttribute("deptId", deptId);
        modelo.addAttribute("catId", catId);
        modelo.addAttribute("grado", grado);
        modelo.addAttribute("lisAlumnos", lisAlumnos);
        modelo.addAttribute("lisPeriodos", lisPeriodos);
        modelo.addAttribute("lisDepartamentos", lisDepartamentos);
        modelo.addAttribute("lisCategorias", lisCategorias);
        modelo.addAttribute("mapaAlumnos", mapaAlumnos);
        modelo.addAttribute("mapaAcademico", mapaAcademico);
        modelo.addAttribute("mapaAlumPlan", mapaAlumPlan);
        modelo.addAttribute("mapHorasRegistradas", mapHorasRegistradas);
        modelo.addAttribute("mapHorasTotalesRegistradas", mapHorasTotalesRegistradas);
        modelo.addAttribute("mapCategorias", mapCategorias);
        modelo.addAttribute("mapDepartamentos", mapDepartamentos);
        modelo.addAttribute("mapaPlanes", mapaPlanes);
        modelo.addAttribute("mapaMaxiComentario", mapaMaxiComentario);


        return "trabajo/reportes/horasAlumno";
    }

    @RequestMapping("/trabajo/reportes/semanal")
    public String trabajoReportesSemanal(HttpServletRequest request, Model modelo){
        String periodoId    = request.getParameter("PeriodoId")==null?"1":request.getParameter("PeriodoId");
        String deptId       = request.getParameter("DeptId")==null?"0":request.getParameter("DeptId");
        String catId        = request.getParameter("CatId")==null?"0":request.getParameter("CatId");
        String grado        = request.getParameter("Grado")==null?"0":request.getParameter("Grado");
        String semana       = request.getParameter("Semana")==null?"0":request.getParameter("Semana");
        
        List<TrabPeriodo> lisPeriodos = trabPeriodoDao.lisTodos(" ORDER BY PERIODO_ID");
        List<TrabDepartamento> lisDepartamentos = trabDepartamentoDao.lisTodos(" ORDER BY DEPT_ID");
        List<TrabCategoria> lisCategorias = trabCategoriaDao.lisPorDepartamento(deptId, " ORDER BY CAT_ID");
        
        List<TrabAlum> lisAlumnos = new ArrayList<>(); 
        String queryGrado = "";
        if(!grado.equals("0")) queryGrado = " AND MATRICULA IN (SELECT CODIGO_PERSONAL FROM ALUM_PLAN WHERE GRADO = '"+grado+"' AND PRINCIPAL = '1')";

        if(deptId.equals("0")){
            lisAlumnos = trabAlumDao.lisPorPeriodo(periodoId, queryGrado+" ORDER BY a.NOMBRE");
        }  
        else if(!deptId.equals("0") && catId.equals("0")){
            lisAlumnos = trabAlumDao.lisPorDeptyPeriodo(periodoId, deptId, queryGrado+" ORDER BY a.NOMBRE");
        }
        else if(!deptId.equals("0") && !catId.equals("0")){
            lisAlumnos = trabAlumDao.lisPorDeptyPeriodoCategoria(periodoId, deptId, catId, queryGrado+" ORDER BY a.NOMBRE");
        }

        // Busca las semanas disponibles para reportes
        Date fechaMinimaDisponible = trabInformeAlumDao.minimaSemanaDisponible(periodoId) == null ? trabInformeAlumDao.minimaSemanaDisponible() : trabInformeAlumDao.minimaSemanaDisponible(periodoId);

        List<String> semanasDisponibles = trabInformeAlumDao.semanasDisponibles(fechaMinimaDisponible);
        if(semana.equals("0")){
            semana = semanasDisponibles.get(0).split(" to ")[0];
        }   
        
        HashMap<String,String> mapaHorasPorSemana = trabInformeAlumDao.mapHorasPorSemana(periodoId, " ORDER BY MATRICULA, LLAVE");
        HashMap<String,String> mapaHorasTotalesPorSemana = trabInformeAlumDao.mapHorasTotalesPorSemana(periodoId, " ORDER BY MATRICULA, LLAVE");
        HashMap<String,AlumPersonal> mapaAlumnos = alumPersonalDao.mapAlumnosTrabajos();
        HashMap<String,AlumAcademico> mapaAcademico = alumAcademicoDao.getMapAcademico();
        HashMap<String,AlumPlan> mapaAlumPlan = alumPlanDao.mapAlumPlanActivo();
        HashMap<String,String> mapCategorias = trabCategoriaDao.mapaCategoriaNombre();
        HashMap<String,String> mapaPlanes = mapaPlanDao.mapNombrePlan();

        modelo.addAttribute("periodoId", periodoId);
        modelo.addAttribute("deptId", deptId);
        modelo.addAttribute("catId", catId);
        modelo.addAttribute("grado", grado);
        modelo.addAttribute("semana", semana);
        modelo.addAttribute("lisAlumnos", lisAlumnos);
        modelo.addAttribute("lisPeriodos", lisPeriodos);
        modelo.addAttribute("lisDepartamentos", lisDepartamentos);
        modelo.addAttribute("lisCategorias", lisCategorias);
        modelo.addAttribute("semanasDisponibles", semanasDisponibles);
        modelo.addAttribute("mapaHorasPorSemana", mapaHorasPorSemana);
        modelo.addAttribute("mapaHorasTotalesPorSemana", mapaHorasTotalesPorSemana);
        modelo.addAttribute("mapaAlumnos", mapaAlumnos);
        modelo.addAttribute("mapaAcademico", mapaAcademico);
        modelo.addAttribute("mapaAlumPlan", mapaAlumPlan);
        modelo.addAttribute("mapCategorias", mapCategorias);
        modelo.addAttribute("mapaPlanes", mapaPlanes);

        return "trabajo/reportes/semanal";
    }   

    @RequestMapping("/trabajo/reportes/horasSupervisor")
    public String trabajoReportesHorasSupervisor(HttpServletRequest request, Model modelo){
        String periodoId  = request.getParameter("PeriodoId")==null?"1":request.getParameter("PeriodoId");
        String deptId     = request.getParameter("DeptId")==null?"0":request.getParameter("DeptId");
        String catId      = request.getParameter("CatId")==null?"0":request.getParameter("CatId");
        String usuario    = request.getParameter("Usuario")==null?"0":request.getParameter("Usuario");

        List<TrabAlum> lisAlumnos = new ArrayList<>();

        List<TrabPeriodo> lisPeriodos               = trabPeriodoDao.lisTodos(" ORDER BY PERIODO_ID");
        List<TrabDepartamento> lisDepartamentos     = trabDepartamentoDao.lisTodos(" ORDER BY DEPT_ID");
        List<TrabCategoria> lisCategorias           = trabCategoriaDao.lisPorDepartamento(deptId, " ORDER BY CAT_ID");

        // Llenar lista de asesores
        List<TrabAsesor> lisAsesores                = new ArrayList<>();
        if(deptId.equals("0")){
            lisAsesores = trabAsesorDao.lisTodos(" ORDER BY DEPT_ID");
        }else{
            lisAsesores = trabAsesorDao.lisPorDepartamento(deptId, " ORDER BY DEPT_ID");
        }

        // Llenar lista de alumnos
        if(usuario.equals("0") && deptId.equals("0") && catId.equals("0")){
            lisAlumnos = trabAlumDao.lisPorPeriodo(periodoId," ORDER BY MATRICULA");
        }
        else if(usuario.equals("0") && !deptId.equals("0") && catId.equals("0")){
            lisAlumnos = trabAlumDao.lisPorDeptyPeriodo(periodoId, deptId, " ORDER BY MATRICULA");
        }
        else if(usuario.equals("0") && !deptId.equals("0") && !catId.equals("0")){
            lisAlumnos = trabAlumDao.lisPorDeptyPeriodoCategoria(periodoId, deptId, catId, " ORDER BY MATRICULA");
        }
        else if(!usuario.equals("0") && deptId.equals("0")){
            lisAlumnos = trabAlumDao.lisPorSupervisor(usuario, periodoId, " ORDER BY MATRICULA");
        }
        else if(!usuario.equals("0") && !deptId.equals("0") && catId.equals("0")){
            lisAlumnos = trabAlumDao.lisPorSupervisor(usuario, periodoId, deptId, " ORDER BY MATRICULA");
        }
        else if(!usuario.equals("0") && !deptId.equals("0") && !catId.equals("0")){
            lisAlumnos = trabAlumDao.lisPorSupervisor(usuario, periodoId, deptId, catId, " ORDER BY MATRICULA");
        }

        HashMap<String,String> mapHorasRegistradas = trabInformeAlumDao.mapHorasRegistradasPorPeriodo(periodoId);
        HashMap<String,String> mapHorasTotalesRegistradas = trabInformeAlumDao.mapHorasTotalesRegistradasPorPeriodo(periodoId);
        HashMap<String,AlumPersonal> mapaAlumnos = alumPersonalDao.mapAlumnosTrabajos();
        HashMap<String,AlumAcademico> mapaAcademico = alumAcademicoDao.getMapAcademico();
        HashMap<String,AlumPlan> mapaAlumPlan = alumPlanDao.mapAlumPlanActivo();
        HashMap<String,EmpMaestro> mapaMaestros = EmpMaestroDao.mapaEmpMaestro();
        HashMap<String,String> mapCategorias = trabCategoriaDao.mapaCategoriaNombre();
        HashMap<String,String> mapDepartamentos = trabDepartamentoDao.mapaDeptNombre(" ORDER BY DEPT_ID");
        HashMap<String,String> mapaPlanes = mapaPlanDao.mapNombrePlan();

        modelo.addAttribute("periodoId", periodoId);
        modelo.addAttribute("deptId", deptId);
        modelo.addAttribute("catId", catId);
        modelo.addAttribute("usuario", usuario);
        modelo.addAttribute("lisAlumnos", lisAlumnos);
        modelo.addAttribute("lisPeriodos", lisPeriodos);
        modelo.addAttribute("lisDepartamentos", lisDepartamentos);
        modelo.addAttribute("lisCategorias", lisCategorias);
        modelo.addAttribute("lisAsesores", lisAsesores);
        modelo.addAttribute("mapHorasRegistradas", mapHorasRegistradas);
        modelo.addAttribute("mapHorasTotalesRegistradas", mapHorasTotalesRegistradas);
        modelo.addAttribute("mapaAlumnos", mapaAlumnos);
        modelo.addAttribute("mapaAcademico", mapaAcademico);
        modelo.addAttribute("mapaAlumPlan", mapaAlumPlan);
        modelo.addAttribute("mapaMaestros", mapaMaestros);
        modelo.addAttribute("mapCategorias", mapCategorias);
        modelo.addAttribute("mapDepartamentos", mapDepartamentos);
        modelo.addAttribute("mapaPlanes", mapaPlanes);

        return "trabajo/reportes/horasSupervisor";
    }

    @RequestMapping("/trabajo/reportes/semanalDeficit")
    public String trabajoReportesSemanalDeficit(HttpServletRequest request, Model modelo){
        String periodoId    = request.getParameter("PeriodoId")==null?"1":request.getParameter("PeriodoId");
        String deptId       = request.getParameter("DeptId")==null?"0":request.getParameter("DeptId");
        String catId        = request.getParameter("CatId")==null?"0":request.getParameter("CatId");
        String grado        = request.getParameter("Grado")==null?"0":request.getParameter("Grado");
        String semana       = request.getParameter("Semana")==null?"0":request.getParameter("Semana");
        
        List<TrabPeriodo> lisPeriodos = trabPeriodoDao.lisTodos(" ORDER BY PERIODO_ID");
        List<TrabDepartamento> lisDepartamentos = trabDepartamentoDao.lisTodos(" ORDER BY DEPT_ID");
        List<TrabCategoria> lisCategorias = trabCategoriaDao.lisPorDepartamento(deptId, " ORDER BY CAT_ID");
        
        List<TrabAlum> lisAlumnos = new ArrayList<>(); 
        String queryGrado = "";
        if(!grado.equals("0")) queryGrado = " AND MATRICULA IN (SELECT CODIGO_PERSONAL FROM ALUM_PLAN WHERE GRADO = '"+grado+"' AND PRINCIPAL = '1')";

        if(deptId.equals("0")){
            lisAlumnos = trabAlumDao.lisPorPeriodo(periodoId, queryGrado+" ORDER BY a.NOMBRE");
        }  
        else if(!deptId.equals("0") && catId.equals("0")){
            lisAlumnos = trabAlumDao.lisPorDeptyPeriodo(periodoId, deptId, queryGrado+" ORDER BY a.NOMBRE");
        }
        else if(!deptId.equals("0") && !catId.equals("0")){
            lisAlumnos = trabAlumDao.lisPorDeptyPeriodoCategoria(periodoId, deptId, catId, queryGrado+" ORDER BY a.NOMBRE");
        }

        // Busca las semanas disponibles para reportes
        Date fechaMinimaDisponible = trabInformeAlumDao.minimaSemanaDisponible(periodoId) == null ? trabInformeAlumDao.minimaSemanaDisponible() : trabInformeAlumDao.minimaSemanaDisponible(periodoId);

        List<String> semanasDisponibles = trabInformeAlumDao.semanasDisponibles(fechaMinimaDisponible);
        if(semana.equals("0")){
            semana = semanasDisponibles.get(0).split(" to ")[0];
        }   
        
        HashMap<String,String> mapaHorasPorSemana = trabInformeAlumDao.mapHorasPorSemana(periodoId, " ORDER BY MATRICULA, LLAVE");
        HashMap<String,String> mapaHorasTotalesPorSemana = trabInformeAlumDao.mapHorasTotalesPorSemana(periodoId, " ORDER BY MATRICULA, LLAVE");
        HashMap<String,AlumPersonal> mapaAlumnos = alumPersonalDao.mapAlumnosTrabajos();
        HashMap<String,AlumAcademico> mapaAcademico = alumAcademicoDao.getMapAcademico();
        HashMap<String,AlumPlan> mapaAlumPlan = alumPlanDao.mapAlumPlanActivo();
        HashMap<String,String> mapCategorias = trabCategoriaDao.mapaCategoriaNombre();
        HashMap<String,String> mapaPlanes = mapaPlanDao.mapNombrePlan();

        modelo.addAttribute("periodoId", periodoId);
        modelo.addAttribute("deptId", deptId);
        modelo.addAttribute("catId", catId);
        modelo.addAttribute("grado", grado);
        modelo.addAttribute("semana", semana);
        modelo.addAttribute("lisAlumnos", lisAlumnos);
        modelo.addAttribute("lisPeriodos", lisPeriodos);
        modelo.addAttribute("lisDepartamentos", lisDepartamentos);
        modelo.addAttribute("lisCategorias", lisCategorias);
        modelo.addAttribute("semanasDisponibles", semanasDisponibles);
        modelo.addAttribute("mapaHorasPorSemana", mapaHorasPorSemana);
        modelo.addAttribute("mapaHorasTotalesPorSemana", mapaHorasTotalesPorSemana);
        modelo.addAttribute("mapaAlumnos", mapaAlumnos);
        modelo.addAttribute("mapaAcademico", mapaAcademico);
        modelo.addAttribute("mapaAlumPlan", mapaAlumPlan);
        modelo.addAttribute("mapCategorias", mapCategorias);
        modelo.addAttribute("mapaPlanes", mapaPlanes);

        return "trabajo/reportes/semanalDeficit";
    }

    @RequestMapping("/trabajo/reportes/alumnosAsignados")
    public String trabajoReportesAlumnoAsignados(HttpServletRequest request, Model modelo){
        String periodoId  = request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
        String residencia = request.getParameter("Residencia")==null?"A":request.getParameter("Residencia");
        String asignacion = request.getParameter("Asignacion")==null?"0":request.getParameter("Asignacion");

        List<TrabPeriodo> lisPeriodos = trabPeriodoDao.lisTodos(" ORDER BY PERIODO_ID");

        String lisAlumQuery = " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM INSCRITOS)";

        if(residencia.equals("I")){
            lisAlumQuery += " AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ALUM_ACADEMICO WHERE RESIDENCIA_ID = 'I')";
            if(asignacion.equals("1")){
                lisAlumQuery += " AND CODIGO_PERSONAL IN (SELECT MATRICULA FROM TRAB_ALUM WHERE PERIODO_ID = "+periodoId+")";
            }
            if(asignacion.equals("2")){
                lisAlumQuery += " AND CODIGO_PERSONAL NOT IN (SELECT MATRICULA FROM TRAB_ALUM WHERE PERIODO_ID = "+periodoId+")";
            }
        }
        if(residencia.equals("E")){
            lisAlumQuery += " AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ALUM_ACADEMICO WHERE RESIDENCIA_ID = 'E')";
            if(asignacion.equals("1")){
                lisAlumQuery += " AND CODIGO_PERSONAL IN (SELECT MATRICULA FROM TRAB_ALUM WHERE PERIODO_ID = "+periodoId+")";
            }
            if(asignacion.equals("2")){
                lisAlumQuery += " AND CODIGO_PERSONAL NOT IN (SELECT MATRICULA FROM TRAB_ALUM WHERE PERIODO_ID = "+periodoId+")";
            }
        }

        List<AlumPersonal> lisAlumnos = null;

        HashMap<String,TrabAlum> mapTrabAlum = trabAlumDao.mapaTrab(" AND PERIODO_ID = "+periodoId);

        if(mapTrabAlum.size() > 0){
            lisAlumnos = alumPersonalDao.getListAll(lisAlumQuery);
        }

        HashMap<String,AlumPersonal> mapaAlumnos = alumPersonalDao.mapAlumnosTrabajos();
        HashMap<String,AlumAcademico> mapaAcademico = alumAcademicoDao.getMap();
        HashMap<String,AlumPlan> mapaAlumPlan = alumPlanDao.mapAlumPlanActivo();
        HashMap<String,String> mapCategorias = trabCategoriaDao.mapaCategoriaNombre();
        HashMap<String,String> mapDepartamentos = trabDepartamentoDao.mapaDeptNombre(" ORDER BY DEPT_ID");
        HashMap<String,String> mapaPlanes = mapaPlanDao.mapNombrePlan();
        HashMap<String,ResComentario> mapaMaxiComentario = resComentarioDao.mapMaxiComentario();

        modelo.addAttribute("periodoId", periodoId);
        modelo.addAttribute("residencia", residencia);
        modelo.addAttribute("asignacion", asignacion);
        modelo.addAttribute("lisAlumnos", lisAlumnos);
        modelo.addAttribute("lisPeriodos", lisPeriodos);
        modelo.addAttribute("mapTrabAlum", mapTrabAlum);
        modelo.addAttribute("mapaAlumnos", mapaAlumnos);
        modelo.addAttribute("mapaAcademico", mapaAcademico);
        modelo.addAttribute("mapaAlumPlan", mapaAlumPlan);
        modelo.addAttribute("mapCategorias", mapCategorias);
        modelo.addAttribute("mapDepartamentos", mapDepartamentos);
        modelo.addAttribute("mapaPlanes", mapaPlanes);
        modelo.addAttribute("mapaMaxiComentario", mapaMaxiComentario);


        return "trabajo/reportes/alumnosAsignados";
    }
 
    @RequestMapping("/trabajo/reportes/mensual")
    public String trabajoReportesMensual(HttpServletRequest request, Model modelo){
        String periodoId    = request.getParameter("PeriodoId")==null?"1":request.getParameter("PeriodoId");
        String deptId       = request.getParameter("DeptId")==null?"0":request.getParameter("DeptId");
        String catId        = request.getParameter("CatId")==null?"0":request.getParameter("CatId");
        String grado        = request.getParameter("Grado")==null?"0":request.getParameter("Grado");
        String mes          = request.getParameter("Mes")==null?"01":request.getParameter("Mes");
        
        List<TrabPeriodo> lisPeriodos = trabPeriodoDao.lisTodos(" ORDER BY PERIODO_ID");
        List<TrabDepartamento> lisDepartamentos = trabDepartamentoDao.lisTodos(" ORDER BY DEPT_ID");
        List<TrabCategoria> lisCategorias = trabCategoriaDao.lisPorDepartamento(deptId, " ORDER BY CAT_ID");
        
        List<TrabAlum> lisAlumnos = new ArrayList<>(); 
        String queryGrado = "";
        if(!grado.equals("0")) queryGrado = " AND MATRICULA IN (SELECT CODIGO_PERSONAL FROM ALUM_PLAN WHERE GRADO = '"+grado+"' AND PRINCIPAL = '1')";

        if(deptId.equals("0")){
            lisAlumnos = trabAlumDao.lisPorPeriodo(periodoId, queryGrado+" ORDER BY a.NOMBRE");
        }  
        else if(!deptId.equals("0") && catId.equals("0")){
            lisAlumnos = trabAlumDao.lisPorDeptyPeriodo(periodoId, deptId, queryGrado+" ORDER BY a.NOMBRE");
        }
        else if(!deptId.equals("0") && !catId.equals("0")){
            lisAlumnos = trabAlumDao.lisPorDeptyPeriodoCategoria(periodoId, deptId, catId, queryGrado+" ORDER BY a.NOMBRE");
        }
        
        TrabPeriodo periodo = trabPeriodoDao.mapeaRegId(periodoId);
        String periodoYear = periodo.getFechaIni();
        if(periodoYear != null && !periodoYear.equals("")) { 
            periodoYear = periodoYear.substring(6,10);
        }else{
            periodoYear = aca.util.Fecha.getHoy().substring(6,10);
        }
       
        HashMap<String,String> mapaHorasPorMes= trabInformeAlumDao.mapHorasPorMes(periodoId, "");
        HashMap<String,String> mapaHorasTotalesPorMes = trabInformeAlumDao.mapHorasTotPorMes(periodoId, "");
        HashMap<String,AlumPersonal> mapaAlumnos = alumPersonalDao.mapAlumnosTrabajos();
        HashMap<String,AlumAcademico> mapaAcademico = alumAcademicoDao.getMapAcademico();
        HashMap<String,AlumPlan> mapaAlumPlan = alumPlanDao.mapAlumPlanActivo();
        HashMap<String,String> mapCategorias = trabCategoriaDao.mapaCategoriaNombre();
        HashMap<String,String> mapaPlanes = mapaPlanDao.mapNombrePlan();

        modelo.addAttribute("periodoId", periodoId);
        modelo.addAttribute("deptId", deptId);
        modelo.addAttribute("catId", catId);
        modelo.addAttribute("grado", grado);
        modelo.addAttribute("mes", mes);
        modelo.addAttribute("periodoYear", periodoYear);
        modelo.addAttribute("lisAlumnos", lisAlumnos);
        modelo.addAttribute("lisPeriodos", lisPeriodos);
        modelo.addAttribute("lisDepartamentos", lisDepartamentos);
        modelo.addAttribute("lisCategorias", lisCategorias);
        modelo.addAttribute("mapaHorasPorMes", mapaHorasPorMes);
        modelo.addAttribute("mapaHorasTotalesPorMes", mapaHorasTotalesPorMes);
        modelo.addAttribute("mapaAlumnos", mapaAlumnos);
        modelo.addAttribute("mapaAcademico", mapaAcademico);
        modelo.addAttribute("mapaAlumPlan", mapaAlumPlan);
        modelo.addAttribute("mapCategorias", mapCategorias);
        modelo.addAttribute("mapaPlanes", mapaPlanes);

        return "trabajo/reportes/mensual";
    }  

    @RequestMapping("/trabajo/reportes/mensualFin")
    public String trabajoReportesMensualFin(HttpServletRequest request, Model modelo){
        String periodoId    = request.getParameter("PeriodoId")==null?"1":request.getParameter("PeriodoId");
        String deptId       = request.getParameter("DeptId")==null?"0":request.getParameter("DeptId");
        String catId        = request.getParameter("CatId")==null?"0":request.getParameter("CatId");
        String grado        = request.getParameter("Grado")==null?"0":request.getParameter("Grado");
        String mes          = request.getParameter("Mes")==null?"01":request.getParameter("Mes");
        
        List<TrabPeriodo> lisPeriodos = trabPeriodoDao.lisTodos(" ORDER BY PERIODO_ID");
        List<TrabDepartamento> lisDepartamentos = trabDepartamentoDao.lisTodos(" ORDER BY DEPT_ID");
        List<TrabCategoria> lisCategorias = trabCategoriaDao.lisPorDepartamento(deptId, " ORDER BY CAT_ID");
        
        List<TrabAlum> lisAlumnos = new ArrayList<>(); 
        String queryGrado = "";
        if(!grado.equals("0")) queryGrado = " AND MATRICULA IN (SELECT CODIGO_PERSONAL FROM ALUM_PLAN WHERE GRADO = '"+grado+"' AND PRINCIPAL = '1')";

        if(deptId.equals("0")){
            lisAlumnos = trabAlumDao.lisPorPeriodo(periodoId, queryGrado+" ORDER BY a.NOMBRE");
        }  
        else if(!deptId.equals("0") && catId.equals("0")){
            lisAlumnos = trabAlumDao.lisPorDeptyPeriodo(periodoId, deptId, queryGrado+" ORDER BY a.NOMBRE");
        }
        else if(!deptId.equals("0") && !catId.equals("0")){
            lisAlumnos = trabAlumDao.lisPorDeptyPeriodoCategoria(periodoId, deptId, catId, queryGrado+" ORDER BY a.NOMBRE");
        }
        
        TrabPeriodo periodo = trabPeriodoDao.mapeaRegId(periodoId);
        String periodoYear = periodo.getFechaIni();
        if(periodoYear != null && !periodoYear.equals("")) { 
            periodoYear = periodoYear.substring(6,10);
        }else{
            periodoYear = aca.util.Fecha.getHoy().substring(6,10);
        }
       
        HashMap<String,String> mapaHorasPorMes= trabInformeAlumDao.mapHorasPorMes(periodoId, "");
        HashMap<String,String> mapaHorasTotalesPorMes = trabInformeAlumDao.mapHorasTotPorMes(periodoId, "");
        HashMap<String,AlumPersonal> mapaAlumnos = alumPersonalDao.mapAlumnosTrabajos();
        HashMap<String,AlumAcademico> mapaAcademico = alumAcademicoDao.getMapAcademico();
        HashMap<String,AlumPlan> mapaAlumPlan = alumPlanDao.mapAlumPlanActivo();
        HashMap<String,String> mapCategorias = trabCategoriaDao.mapaCategoriaNombre();
        HashMap<String,String> mapaPlanes = mapaPlanDao.mapNombrePlan();

        modelo.addAttribute("periodoId", periodoId);
        modelo.addAttribute("deptId", deptId);
        modelo.addAttribute("catId", catId);
        modelo.addAttribute("grado", grado);
        modelo.addAttribute("mes", mes);
        modelo.addAttribute("periodoYear", periodoYear);
        modelo.addAttribute("lisAlumnos", lisAlumnos);
        modelo.addAttribute("lisPeriodos", lisPeriodos);
        modelo.addAttribute("lisDepartamentos", lisDepartamentos);
        modelo.addAttribute("lisCategorias", lisCategorias);
        modelo.addAttribute("mapaHorasPorMes", mapaHorasPorMes);
        modelo.addAttribute("mapaHorasTotalesPorMes", mapaHorasTotalesPorMes);
        modelo.addAttribute("mapaAlumnos", mapaAlumnos);
        modelo.addAttribute("mapaAcademico", mapaAcademico);
        modelo.addAttribute("mapaAlumPlan", mapaAlumPlan);
        modelo.addAttribute("mapCategorias", mapCategorias);
        modelo.addAttribute("mapaPlanes", mapaPlanes);

        return "trabajo/reportes/mensualFin";
    } 
}
