package aca.web.trabajo;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.trabajo.spring.TrabAlum;
import aca.trabajo.spring.TrabAlumDao;
import aca.trabajo.spring.TrabCategoria;
import aca.trabajo.spring.TrabDepartamento;
import aca.trabajo.spring.TrabCategoriaDao;
import aca.trabajo.spring.TrabDepartamentoDao;
import aca.trabajo.spring.TrabInformeAlum;
import aca.trabajo.spring.TrabPeriodo;
import aca.trabajo.spring.TrabPeriodoDao;
import aca.trabajo.spring.TrabInformeAlumDao;
import aca.alumno.spring.AlumPersonal;
import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumAcademico;
import aca.alumno.spring.AlumAcademicoDao;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;
import aca.internado.spring.IntDormitorio;
import aca.internado.spring.IntDormitorioDao;
import aca.plan.spring.MapaPlanDao;
import aca.residencia.spring.ResComentario;
import aca.residencia.spring.ResComentarioDao;

@Controller
public class ContTrabajoPeriodoAlum {
	
	@Autowired
	aca.vista.spring.UsuariosDao usuariosDao;

	@Autowired
	AccesoDao accesoDao;

	@Autowired
	private CargaDao cargaDao;
	
	@Autowired
	private TrabDepartamentoDao trabDepartamentoDao;
	
	@Autowired
	private TrabCategoriaDao trabCategoriaDao;
	
	@Autowired
	private TrabPeriodoDao trabPeriodoDao;
	
	@Autowired
	private TrabAlumDao trabAlumDao;
	
	@Autowired
	private TrabInformeAlumDao trabInformeAlumDao;
	
	@Autowired
	private AlumAcademicoDao alumAcademicoDao;
	
	@Autowired
	private AlumPlanDao	alumPlanDao;

	@Autowired
	private AlumPersonalDao	alumPersonalDao;
	
	@Autowired
	private MapaPlanDao mapaPlanDao;

	@Autowired
	private IntDormitorioDao intDormitorioDao;

	@Autowired
	private ResComentarioDao resComentarioDao;
	
	@RequestMapping("/trabajo/periodoAlum/periodoAlum")
	public String trabajoPeriodoAlumPeriodoAlum(HttpServletRequest request, Model modelo){
		String deptId 		= request.getParameter("DeptId")==null?"0":request.getParameter("DeptId");
		String catId 		= request.getParameter("CatId")==null?"0":request.getParameter("CatId");
		String periodoId 	= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String planId		= "0";
		String nombrePlan 	= "-";
		String codigoAlumno = "0";
		String codigoPersonal = "0";
		String nombreAlumno	= "-";
		String alumnoHorasAlc = "";
		String alumnoHorasTotAlc = "";
		String residenciaEstado = "";
		String mensaje = "0";
		boolean esAlumno = false;
		boolean esAdmin = false;
		boolean esSupervisor = false;

		HttpSession sesion  = ((HttpServletRequest)request).getSession();
		if(sesion  != null) {
			codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");
			codigoAlumno 		= (String) sesion.getAttribute("codigoAlumno");
			nombreAlumno 		= usuariosDao.getNombreUsuario(codigoAlumno, "NOMBRE");
			if (codigoAlumno.subSequence(0, 1).equals("0") || codigoAlumno.subSequence(0, 1).equals("1") || codigoAlumno.subSequence(0, 1).equals("2")){
        		esAlumno 	= true;
        	}
			alumnoHorasAlc 		= trabInformeAlumDao.AlumHorasAlcanzadas(codigoAlumno, "");
			alumnoHorasTotAlc 	= trabInformeAlumDao.AlumHorasTotalesAlcanzadas(codigoAlumno, "");
			residenciaEstado 	= alumAcademicoDao.getResidencia(codigoAlumno);
			esAdmin 			= accesoDao.esAdministrador(codigoPersonal);
		}
		
		planId = alumPlanDao.getPlanActual(codigoAlumno);
		
		nombrePlan = mapaPlanDao.getNombrePlan(planId);
		
		AlumAcademico alumAcademico = new AlumAcademico();		
		
		if(alumAcademicoDao.existeReg(codigoAlumno)) {
			alumAcademico = alumAcademicoDao.mapeaRegId(codigoAlumno);
		}
		
		List<TrabPeriodo> lisPeriodos = trabPeriodoDao.lisActivos(" ORDER BY PERIODO_ID");
		List<TrabDepartamento> lisDepartamentos = trabDepartamentoDao.lisTodos(" ORDER BY DEPT_ID");		
		//List<TrabDepartamento> lisDepartamentos = trabDepartamentoDao.lisPorSupervisor(codigoPersonal, " ORDER BY DEPT_ID");		
		if (periodoId.equals("0")&&lisPeriodos.size()>= 1) periodoId =lisPeriodos.get(0).getPeriodoId();
		if (deptId.equals("0")&&lisDepartamentos.size()>= 1) deptId =lisDepartamentos.get(0).getDeptId();
		List<TrabCategoria> lisCategorias = trabCategoriaDao.lisPorDepartamento(deptId, " ORDER BY CAT_ID");
		
		HashMap<String, String> mapaAlumnoNombre 		= trabAlumDao.mapaAlumNombre("");
		HashMap<String, String> mapaDeptNombre			= trabDepartamentoDao.mapaDeptNombre("");
		HashMap<String, String> mapaCatNombre			= trabCategoriaDao.mapaCategoriaNombre();
		HashMap<String, String> mapaPeriodoNombre		= trabPeriodoDao.mapaPeriodoNombre();
		
		
		HashMap<String, String> mapaPeriodoHoras 			= trabInformeAlumDao.mapPeriodoyHoras(codigoAlumno);
		HashMap<String, String> mapaPeriodoHorasTotales 	= trabInformeAlumDao.mapPeriodoyHorasTotales(codigoAlumno);
		
		List<TrabAlum> lisAlumPeriodos = trabAlumDao.lisPorAlumno(codigoAlumno, " ORDER BY PERIODO_ID");
		
		boolean esDirector = codigoPersonal.equals(trabDepartamentoDao.getDirector(deptId))?true:false;
		
		modelo.addAttribute("deptId", deptId);
		modelo.addAttribute("catId", catId);
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("nombrePlan", nombrePlan);
		modelo.addAttribute("alumnoHorasAlc", alumnoHorasAlc);
		modelo.addAttribute("alumnoHorasTotAlc", alumnoHorasTotAlc);
		modelo.addAttribute("residenciaEstado", residenciaEstado);
		modelo.addAttribute("Mensaje", mensaje);
		modelo.addAttribute("esAlumno", esAlumno);
		modelo.addAttribute("esAdmin", esAdmin);
		modelo.addAttribute("esDirector", esDirector);
		
		modelo.addAttribute("alumAcademico", alumAcademico);
		
		modelo.addAttribute("lisDepartamentos", lisDepartamentos);
		modelo.addAttribute("lisCategorias", lisCategorias);
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("lisAlumPeriodos", lisAlumPeriodos);
		
		modelo.addAttribute("mapaAlumnoNombre", mapaAlumnoNombre);
		modelo.addAttribute("mapaDeptNombre", mapaDeptNombre);
		modelo.addAttribute("mapaCatNombre", mapaCatNombre);
		modelo.addAttribute("mapaPeriodoNombre", mapaPeriodoNombre);
		modelo.addAttribute("mapaPeriodoHoras", mapaPeriodoHoras);
		modelo.addAttribute("mapaPeriodoHorasTotales", mapaPeriodoHorasTotales);
		
		return "trabajo/periodoAlum/periodoAlum";
	}
	
	@RequestMapping("/trabajo/periodoAlum/grabar")
	public String trabajoPeriodoAlumGrabar(HttpServletRequest request, Model modelo) {
		String periodoId 	= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String deptId 		= request.getParameter("DeptId")==null?"0":request.getParameter("DeptId");
		String catId 		= request.getParameter("CatId")==null?"0":request.getParameter("CatId");
		String horas 		= request.getParameter("Horas")==null?"0":request.getParameter("Horas");
		String pago 		= request.getParameter("Pago")==null?"0":request.getParameter("Pago");
		String codigoAlumno = "0";
		String mensaje 		= "0";
		
		TrabAlum alumno = new TrabAlum();
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion != null){
        	codigoAlumno	= (String) sesion.getAttribute("codigoAlumno");   	
        } 
	
		if(trabAlumDao.existeEnPeriodo(codigoAlumno, periodoId)) {
				mensaje = "1";
		}else {
			alumno.setMatricula(codigoAlumno);
			alumno.setPeriodoId(periodoId);
			alumno.setDeptId(deptId);
			alumno.setCatId(catId);
			alumno.setHoras(horas);
			alumno.setEstado("A");
			
			if(trabAlumDao.desactivarTrab(codigoAlumno)){
				if(trabAlumDao.insertReg(alumno)) {
					mensaje = "2";
				}
			}else{
				if(trabAlumDao.insertReg(alumno)) {
					mensaje = "2";
				}else{
					mensaje = "4";
				}
			}
		}
		
		return "redirect:/trabajo/periodoAlum/periodoAlum?PeriodoId="+periodoId+"&DeptId="+deptId+"&CatId="+catId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/trabajo/periodoAlum/borrarPeriodo")
	public String trabajoPeriodoAlumBorrar(HttpServletRequest request, Model modelo) {
		String periodoId 	= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String deptId 		= request.getParameter("DeptId")==null?"0":request.getParameter("DeptId");
		String catId 		= request.getParameter("CatId")==null?"0":request.getParameter("CatId");

		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();	
		String codigoAlumno		= "0";		
		
		if (sesion != null){
			codigoAlumno	= (String) sesion.getAttribute("codigoAlumno");
		} 
		
		if(trabAlumDao.existeReg(codigoAlumno, periodoId, deptId, catId)) {
			trabAlumDao.deleteReg(codigoAlumno,  periodoId, deptId, catId);
		}
		
		
		return "redirect:/trabajo/periodoAlum/periodoAlum";
	}
	
	@RequestMapping("trabajo/periodoAlum/cambiarDept")
	public String trabajoPeriodoAlumCambiarDept(HttpServletRequest request, Model modelo) {
		String codigoAlumno 	= "0";
		String periodoId 	= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String deptId 		= request.getParameter("DeptId")==null?"0":request.getParameter("DeptId");
		String catId 		= request.getParameter("CatId")==null?"0":request.getParameter("CatId");
		String horas		= request.getParameter("horas")==null?"0":request.getParameter("horas");
		String nuevoDept 	= request.getParameter("NuevoDept")==null?"1":request.getParameter("NuevoDept");
		
		HttpSession sesion  = ((HttpServletRequest)request).getSession();
		if(sesion  != null) {
			codigoAlumno = (String) sesion.getAttribute("codigoAlumno");	
		}
		
		TrabAlum alumno = new TrabAlum();
		
		alumno.setMatricula(codigoAlumno);
		alumno.setPeriodoId(periodoId);
		alumno.setDeptId(deptId);
		alumno.setCatId(catId);
		alumno.setHoras(horas);
		
		List<TrabDepartamento> lisDepartamentos = trabDepartamentoDao.lisTodos(" ORDER BY DEPT_ID");
		List<TrabCategoria> lisCategorias = trabCategoriaDao.lisPorDepartamento(nuevoDept, " ORDER BY CAT_ID");
		
		HashMap<String, String> mapaAlumnoNombre 	= trabAlumDao.mapaAlumNombre("");
		HashMap<String, String> mapaDeptNombre		= trabDepartamentoDao.mapaDeptNombre("");
		HashMap<String, String> mapaCatNombre		= trabCategoriaDao.mapaCategoriaNombre();
		HashMap<String, String> mapaPeriodoNombre	= trabPeriodoDao.mapaPeriodoNombre();
		
		modelo.addAttribute("nuevoDept", nuevoDept);
		modelo.addAttribute("alumno", alumno);
		modelo.addAttribute("lisDepartamentos", lisDepartamentos);
		modelo.addAttribute("lisCategorias", lisCategorias);
		modelo.addAttribute("nombreAlumno", mapaAlumnoNombre.get(codigoAlumno));
		modelo.addAttribute("nombreDept", mapaDeptNombre.get(deptId));
		modelo.addAttribute("nombreCat", mapaCatNombre.get(catId));
		modelo.addAttribute("nombrePeriodo", mapaPeriodoNombre.get(periodoId));
		
		return "trabajo/periodoAlum/cambiarDept";
	}
	
	@RequestMapping("/trabajo/periodoAlum/grabarNuevoDept")
	public String trabajoPeriodoAlumGrabarNuevoDept(HttpServletRequest request, Model modelo) {
		String periodoId 	= request.getParameter("periodoId")==null?"0":request.getParameter("periodoId");
		String deptId 		= request.getParameter("deptId")==null?"0":request.getParameter("deptId");
		String catId 		= request.getParameter("catId")==null?"0":request.getParameter("catId");
		String nuevoDept 	= request.getParameter("NuevoDept")==null?"0":request.getParameter("NuevoDept");
		String nuevoCat		= request.getParameter("NuevoCat")==null?"0":request.getParameter("NuevoCat");
		String horas		= request.getParameter("Horas")==null?"0":request.getParameter("Horas");
		String codigoAlumno = "0";
		String mensaje 		= "0";
		
		TrabAlum alumno = new TrabAlum();
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion != null){
        	codigoAlumno	= (String) sesion.getAttribute("codigoAlumno");   	
        } 
		TrabAlum alumAntes = trabAlumDao.mapeaRegId(codigoAlumno, deptId, catId, periodoId);
		
		System.out.println(nuevoDept + " : "+nuevoCat);

		if(trabAlumDao.existeRegDept(codigoAlumno, nuevoDept, nuevoCat)) {
			alumno = trabAlumDao.mapeaRegId(codigoAlumno, nuevoDept, nuevoCat, periodoId);
			alumno.setEstado("A");
			if(trabAlumDao.updateReg(alumno)) {
				mensaje = "3";
			}
		}else {
			alumno.setMatricula(codigoAlumno);
			alumno.setPeriodoId(periodoId);
			alumno.setDeptId(nuevoDept);
			alumno.setCatId(nuevoCat);
			alumno.setHoras(horas);
			if(trabAlumDao.insertReg(alumno)) {
				mensaje = "2";
			}
		}
		
		alumAntes.setEstado("I");
		
		trabAlumDao.updateReg(alumAntes);
		
		return "redirect:/trabajo/periodoAlum/periodoAlum?PeriodoId="+periodoId+"&DeptId="+deptId+"&CatId="+catId+"&Mensaje="+mensaje;
	}
	
	
	@RequestMapping("/trabajo/periodoAlum/editarPeriodo")
	public String trabajoPeriodoAlumEditarPeriodo(HttpServletRequest request, Model modelo) {
		String periodoId 	= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String deptId 		= request.getParameter("DeptId")==null?"0":request.getParameter("DeptId");
		String catId 		= request.getParameter("CatId")==null?"0":request.getParameter("CatId");
		String codigoAlumno = "0";
		String mensaje 		= "0";
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion != null){
        	codigoAlumno	= (String) sesion.getAttribute("codigoAlumno");   	
        } 
		
		TrabAlum alumno = trabAlumDao.mapeaRegId(codigoAlumno, deptId, catId, periodoId);
		HashMap<String, String> mapaPeriodoNombre	= trabPeriodoDao.mapaPeriodoNombre();
		HashMap<String, String> mapaDeptNombre		= trabDepartamentoDao.mapaDeptNombre("");
		HashMap<String, String> mapaCatNombre		= trabCategoriaDao.mapaCategoriaNombre();

		modelo.addAttribute("alumno", alumno);
		modelo.addAttribute("nombrePeriodo", mapaPeriodoNombre.get(alumno.getPeriodoId()));
		modelo.addAttribute("nombreDept", mapaDeptNombre.get(alumno.getDeptId()));
		modelo.addAttribute("nombreCat", mapaCatNombre.get(alumno.getCatId()));
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("deptId", deptId);
		modelo.addAttribute("catId", catId);
		modelo.addAttribute("mensaje", mensaje);

		return "trabajo/periodoAlum/editarPeriodo";
	}
	
	@RequestMapping("/trabajo/periodoAlum/editar")
	public String trabajoPeriodoAlumEditar(HttpServletRequest request, Model modelo) {
		String periodoId 	= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String deptId 		= request.getParameter("DeptId")==null?"0":request.getParameter("DeptId");
		String catId 		= request.getParameter("CatId")==null?"0":request.getParameter("CatId");
		String horas 		= request.getParameter("Horas")==null?"0":request.getParameter("Horas");
		String pago 		= request.getParameter("Pago")==null?"0":request.getParameter("Pago");
		String estado 		= request.getParameter("Estado")==null?"A":request.getParameter("Estado");
		String codigoAlumno = "0";
		String mensaje 		= "0";
		
		TrabAlum alumno = new TrabAlum();
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion != null){
        	codigoAlumno	= (String) sesion.getAttribute("codigoAlumno");   	
        } 
		
		if(trabAlumDao.existeReg(codigoAlumno, periodoId, deptId, catId)) {
			
			// alumno = trabAlumDao.mapeaRegId(codigoAlumno, deptId, catId, periodoId);
			alumno.setMatricula(codigoAlumno);
			alumno.setDeptId(deptId);
			alumno.setCatId(catId);
			alumno.setPeriodoId(periodoId);
			alumno.setHoras(horas);
			alumno.setPago(pago);
			alumno.setEstado(estado);
			
			if(trabAlumDao.updateReg(alumno)) {
				mensaje = "3";
			}
			
		}else {
			mensaje = "4";
		}
		
		return "redirect:/trabajo/periodoAlum/periodoAlum?PeriodoId="+periodoId+"&DeptId="+deptId+"&CatId="+catId+"&Mensaje="+mensaje;
	}

	@RequestMapping("/trabajo/periodoAlum/acreditarHoras")
	public String trabajoPeriodoAlumAcreditarHoras(HttpServletRequest request, Model modelo){
		String periodoId 	= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String deptId 		= request.getParameter("DeptId")==null?"0":request.getParameter("DeptId");
		String catId 		= request.getParameter("CatId")==null?"0":request.getParameter("CatId");
		String fecha 		= aca.util.Fecha.getHoy();

		String codigoAlumno = "0";
		String mensaje 		= "0";
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion != null){
        	codigoAlumno	= (String) sesion.getAttribute("codigoAlumno");   	
        } 

		AlumPersonal alumPersonal = alumPersonalDao.mapeaRegId(codigoAlumno);

		TrabAlum trabAlum = trabAlumDao.mapeaRegId(codigoAlumno, deptId, catId, periodoId);

		List<TrabInformeAlum> lisInformesAcreditados = trabInformeAlumDao.lisInformesAlumnoPorEstado(codigoAlumno, "X", " ORDER BY FECHA");

		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("deptId", deptId);
		modelo.addAttribute("catId", catId);
		modelo.addAttribute("fecha", fecha);
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("trabAlum", trabAlum);
		modelo.addAttribute("lisInformesAcreditados", lisInformesAcreditados);

		return "trabajo/periodoAlum/acreditarHoras";
	}

	@RequestMapping("/trabajo/periodoAlum/grabarHoras")
	public String trabajoPeriodoAlumGrabarHoras(HttpServletRequest request, Model modelo){
		String periodoId 	= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String deptId 		= request.getParameter("DeptId")==null?"0":request.getParameter("DeptId");
		String catId 		= request.getParameter("CatId")==null?"0":request.getParameter("CatId");
		String horas 		= request.getParameter("Horas")==null?"0":request.getParameter("Horas");
		String descripcion 	= request.getParameter("Descripcion")==null?"CTP HOURS CREDITED.":request.getParameter("Descripcion");
		String fecha 		= request.getParameter("Fecha")==null?aca.util.Fecha.getHoy():request.getParameter("Fecha");
		String codigoAlumno = "0";
		String codigoPersonal 	= "0";
		String mensaje 		= "0";

		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			codigoPersonal 	= (String) sesion.getAttribute("codigoPersonal");
        	codigoAlumno	= (String) sesion.getAttribute("codigoAlumno");   	
        } 

		TrabInformeAlum trabInformeAlum = new TrabInformeAlum();

		TrabPeriodo trabPeriodo = trabPeriodoDao.mapeaRegId(periodoId);
		String fechaPeriodo = trabPeriodo.getFechaIni();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate date1 = LocalDate.parse(fecha, formatter);
        LocalDate date2 = LocalDate.parse(fechaPeriodo, formatter);
        
        // Compare the dates
        if (date1.isBefore(date2)) {
            mensaje = "Error. Choose a date inside the Period's range.";

        } else if (date1.isAfter(date2) || date1.equals(date2)) {
			trabInformeAlum.setMatricula(codigoAlumno); 
			trabInformeAlum.setInformeId(trabInformeAlumDao.maximoReg()); 
			trabInformeAlum.setDeptId(deptId); 
			trabInformeAlum.setCatId(catId); 
			trabInformeAlum.setFecha(fecha); 
			trabInformeAlum.setUsuario(codigoPersonal);
			trabInformeAlum.setHoras(horas);  
			trabInformeAlum.setPeriodoId(periodoId); 
			trabInformeAlum.setStatus("X"); 
			trabInformeAlum.setDescripcion(descripcion);
			
			if(trabInformeAlumDao.insertReg(trabInformeAlum)){ 
				mensaje = "Report started";
			}else{
				mensaje = "Error sending report";
			}
        }

		return "redirect:/trabajo/periodoAlum/acreditarHoras?PeriodoId="+periodoId+"&DeptId="+deptId+"&CatId="+catId+"&Mensaje="+mensaje;
	}

	@RequestMapping("/trabajo/periodoAlum/eliminarHoras")
	public String trabajoPeriodoAlumEliminarHoras(HttpServletRequest request, Model modelo){
		String codigoAlumno 	= "0";
		String codigoPersonal 	= "0";
		String nombreAlumno 	= "-";
		String periodoId 	= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String deptId 		= request.getParameter("DeptId")==null?"0":request.getParameter("DeptId");
		String catId 		= request.getParameter("CatId")==null?"0":request.getParameter("CatId");
		String informeId 		= request.getParameter("InformeId")==null?"0":request.getParameter("InformeId");
		String mensaje 			= "";

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			codigoAlumno 		= (String) sesion.getAttribute("codigoAlumno");
			codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");
			nombreAlumno 		= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
		}

		TrabAlum trabAlum = trabAlumDao.mapeaRegId(codigoAlumno, "A");
		TrabInformeAlum trabInformeAlum = new TrabInformeAlum();

		if(trabInformeAlumDao.existeReg(codigoAlumno, informeId)){
			if(trabInformeAlumDao.deleteReg(codigoAlumno, informeId)){
				mensaje = "Report deleted";
			}else{
				mensaje = "Error updating report";
			}
		}else{
			mensaje = "Erro, no report found";
		}

		return "redirect:/trabajo/periodoAlum/acreditarHoras?PeriodoId="+periodoId+"&DeptId="+deptId+"&CatId="+catId+"&Mensaje="+mensaje;
	}

	@RequestMapping("/trabajo/periodoAlum/periodoAlums")
	public String trabajoPeriodoAlumPeriodoAlums(HttpServletRequest request, Model modelo){
		String cargaId 			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String deptId 			= request.getParameter("DeptId")==null?"0":request.getParameter("DeptId");
		String catId 			= request.getParameter("CatId")==null?"0":request.getParameter("CatId");
		String grado 			= request.getParameter("Grado")==null?"0":request.getParameter("Grado");
		String periodoId 		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String residencia 		= request.getParameter("Residencia")==null?"I":request.getParameter("Residencia");
		String codigoPersonal 	= "";

		HttpSession sesion  = ((HttpServletRequest)request).getSession();
		if(sesion  != null) {
			codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");
		}

		List<Carga> lisCargas = cargaDao.getListAllActivas(" ORDER BY F_INICIO");
		if (cargaId.equals("0")&&lisCargas.size()>=1) cargaId = lisCargas.get(0).getCargaId();

		List<TrabPeriodo> lisPeriodos = trabPeriodoDao.lisTodos(" ORDER BY PERIODO_ID");
		
		List<TrabDepartamento> lisDepartamentos = trabDepartamentoDao.lisTodos(" ORDER BY DEPT_ID");		
		if (periodoId.equals("0")&&lisPeriodos.size()>= 1) periodoId =lisPeriodos.get(0).getPeriodoId();
		if (deptId.equals("0")&&lisDepartamentos.size()>= 1) deptId =lisDepartamentos.get(0).getDeptId();

		List<TrabCategoria> lisCategorias = trabCategoriaDao.lisPorDepartamento(deptId, " ORDER BY CAT_ID");

		String residenciaQuery = "";
		if(residencia.equals("I"))
			residenciaQuery = " AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ACADEMICO WHERE RESIDENCIA_ID = 'I')";
		if(residencia.equals("E"))
			residenciaQuery = " AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ACADEMICO WHERE RESIDENCIA_ID = 'E')";

		String periodoQuery = " AND CODIGO_PERSONAL NOT IN(SELECT MATRICULA FROM TRAB_ALUM WHERE PERIODO_ID = '"+periodoId+"' AND ESTADO = 'A')";

		List<AlumPersonal> lisAlumnos = null;
		if(grado.equals("0")){
			lisAlumnos = alumPersonalDao.getListAlumnosEnCarga(cargaId, residenciaQuery+periodoQuery);
		}else{
			lisAlumnos = alumPersonalDao.getListAlumnosEnCargaGrado(cargaId, Integer.parseInt(grado), residenciaQuery+periodoQuery);
		}

		HashMap<String, String> mapaAlumnosEnPeriodo 		= trabPeriodoDao.mapaPeriodoNombre(periodoId);
		HashMap<String, AlumPlan> mapaAlumPlan 				= alumPlanDao.mapaPlanPorCarga(cargaId, " AND ESTADO = 1");
		HashMap<String, AlumAcademico> mapaAlumAcademico 	= alumAcademicoDao.mapaEnCarga(cargaId);
		HashMap<String, IntDormitorio> mapaIntDormitorio	= intDormitorioDao.getMapAll(" ORDER BY DORMITORIO_ID");
		HashMap<String,ResComentario> mapaMaxiComentario = resComentarioDao.mapMaxiComentario();

		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("deptId", deptId);
		modelo.addAttribute("catId", catId);
		modelo.addAttribute("grado", grado);
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("residencia", residencia);
		modelo.addAttribute("codigoPersonal", codigoPersonal);
		
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("lisDepartamentos", lisDepartamentos);
		modelo.addAttribute("lisCategorias", lisCategorias);

		modelo.addAttribute("mapaAlumPlan", mapaAlumPlan);
		modelo.addAttribute("mapaAlumnosEnPeriodo", mapaAlumnosEnPeriodo);
		modelo.addAttribute("mapaAlumAcademico", mapaAlumAcademico);
		modelo.addAttribute("mapaIntDormitorio", mapaIntDormitorio);
		modelo.addAttribute("mapaMaxiComentario", mapaMaxiComentario);

		return "trabajo/periodoAlum/periodoAlums";
	}
	
	@RequestMapping("/trabajo/periodoAlum/grabarPeriodoAlums")
	public String trabajoPeriodoAlumGrabarPeriodoAlums(HttpServletRequest request, Model modelo){
		String cargaId 			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String deptId 			= request.getParameter("DeptId")==null?"0":request.getParameter("DeptId");
		String catId 			= request.getParameter("CatId")==null?"0":request.getParameter("CatId");
		String periodoId 		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String horas 			= request.getParameter("Horas")==null?"0":request.getParameter("Horas");
		String codigoPersonal 	= "";
		String mensaje 			= "";

		HttpSession sesion  = ((HttpServletRequest)request).getSession();
		if(sesion  != null) {
			codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");
		}

		int assigned = 0;
		int errors = 0;
		List<String> errorStudents = new ArrayList<>();

		List<AlumPersonal> lisAlumnos = alumPersonalDao.getListAlumnosEnCarga(cargaId, "");
		for(AlumPersonal alumno : lisAlumnos){
			String codigoAlumno = request.getParameter(alumno.getCodigoPersonal());
			TrabAlum trabAlum = new TrabAlum();
			if (codigoAlumno != null && alumno.getCodigoPersonal().equals(codigoAlumno)) {
				if(trabAlumDao.existeEnPeriodo(codigoAlumno, periodoId)){
					errors++;
					errorStudents.add(codigoAlumno);
				}else{
					trabAlum.setDeptId(deptId);
					trabAlum.setCatId(catId);
					trabAlum.setMatricula(codigoAlumno);
					trabAlum.setPeriodoId(periodoId);
					trabAlum.setHoras(horas);
					trabAlum.setEstado("A");
					trabAlum.setPago("0");
					if(trabAlumDao.insertReg(trabAlum)){
						assigned++;
					}
				}
			}
		}

		if(errors <= 0){
			mensaje = "Successfully assigned Workline to "+assigned+" students ";
		}else if(errors >= 1){
			mensaje = "Error saving students: "+String.join(", ", errorStudents);
		}

		return "redirect:/trabajo/periodoAlum/periodoAlums";
	}
}