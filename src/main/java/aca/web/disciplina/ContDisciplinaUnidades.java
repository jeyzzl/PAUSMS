package aca.web.disciplina;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanDao;
import aca.catalogo.spring.CatPeriodo;
import aca.catalogo.spring.CatPeriodoDao;
import aca.disciplina.spring.CondAlumno;
import aca.disciplina.spring.CondAlumnoDao;
import aca.disciplina.spring.CondJuez;
import aca.disciplina.spring.CondJuezDao;
import aca.disciplina.spring.CondLugar;
import aca.disciplina.spring.CondLugarDao;
import aca.disciplina.spring.CondReporte;
import aca.disciplina.spring.CondReporteDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.Maestros;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContDisciplinaUnidades {
	
	@Autowired
	private CatPeriodoDao catPeriodoDao;
	
	@Autowired
	private CondAlumnoDao condAlumnoDao;
	
	@Autowired
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired
	private MaestrosDao maestrosDao;
	
	@Autowired
	private CondReporteDao condReporteDao;
	
	@Autowired
	private CondJuezDao condJuezDao;
	
	@Autowired
	private CondLugarDao condLugarDao;
	
	@Autowired
	AlumPlanDao alumPlanDao;
	
	@Autowired
	MapaPlanDao mapaPlanDao;
	
	@Autowired
	ServletContext context;
	
	
	@RequestMapping("/disciplina/unidades/unidad")
	public String disciplinaUnidadesUnidad(HttpServletRequest request, Model modelo){
		
		String codigo	= "";
		String periodo	= request.getParameter("periodo") == null ? catPeriodoDao.getPeriodo() : request.getParameter("periodo");
		String nombre 	= "";
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigo 	= (String) sesion.getAttribute("codigoAlumno");
        }
        
        nombre 	= alumPersonalDao.getNombre(codigo, "NOMBRE");
		
        List<CatPeriodo> lisPeriodo 	= catPeriodoDao.getListAll("ORDER BY NOMBRE_PERIODO DESC");
    	List<CondAlumno> lisAlumno		= condAlumnoDao.getLista(periodo, codigo, "ORDER BY ENOC.COND_ALUMNO.FECHA");
        
    	HashMap<String,String> mapMaestroNombre 	= maestrosDao.mapMaestroNombre("NOMBRE");
    	HashMap<String, CondReporte> mapaReportes 	= condReporteDao.mapaReportes();
    	HashMap<String, String> mapaJuez  			= condJuezDao.mapaJuez();
    	HashMap<String, String> mapaLugar			= condLugarDao.mapaLugar();
    	HashMap<String,String> mapaMovimientos	 	= condAlumnoDao.mapaMovimientosPorPeriodo(codigo);
    	
    	modelo.addAttribute("nombre", nombre);
		modelo.addAttribute("periodo", periodo);
		modelo.addAttribute("codigo", codigo);
		
		modelo.addAttribute("lisPeriodo", lisPeriodo);
		modelo.addAttribute("lisAlumno", lisAlumno);
		modelo.addAttribute("mapMaestroNombre", mapMaestroNombre);
		modelo.addAttribute("mapaReportes", mapaReportes);
		modelo.addAttribute("mapaLugar", mapaLugar);
		modelo.addAttribute("mapaJuez", mapaJuez);
		modelo.addAttribute("mapaMovimientos", mapaMovimientos);
		
		return "disciplina/unidades/unidad";
	}

	@RequestMapping("/disciplina/unidades/grabar")
	public String disciplinaUnidadesGrabar(HttpServletRequest request, Model modelo){
		
		String codigo		= request.getParameter("codigoPersonal") == null ? "" : request.getParameter("codigoPersonal");
		String periodo		= request.getParameter("Periodo") == null ? "" : request.getParameter("Periodo");
		String fecha		= request.getParameter("fecha") == null ? "" : request.getParameter("fecha");	
		String accion		= request.getParameter("Accion")  == null ? "" : request.getParameter("Accion");
		String codigoEmp	= request.getParameter("empleado") == null ? "" : request.getParameter("empleado");
		String folio		= request.getParameter("folio") == null ? "" : request.getParameter("folio");
		String reporte		= request.getParameter("reporte") == null ? "" : request.getParameter("reporte");
		String lugar		= request.getParameter("lugar") == null ? "" : request.getParameter("lugar");
		String juez			= request.getParameter("juez") == null ? "" : request.getParameter("juez");
		String empleado		= request.getParameter("empleado") == null ? "" : request.getParameter("empleado");
		String comentario	= request.getParameter("comentario") == null ? "" : request.getParameter("comentario");
		String planId		= request.getParameter("planId") == null ? "0" : request.getParameter("planId");
		
		String alumnoNombre = "-"; 
		if (alumPersonalDao.existeAlumno(codigo)) {
			alumnoNombre	= alumPersonalDao.getNombreAlumno(codigo, "NOMBRE"); 
		}
		
		String cantidad		= "0";		
		String resultado	= "-";		
		
		if (request.getParameter("cantidad") != null && request.getParameter("cantidad") != "null"){
			cantidad = request.getParameter("cantidad");
		}else{
			cantidad = "0";
		}
		
		CondAlumno alumno = new CondAlumno(); 
		
		if(accion.equals("1")){
			alumno.setFolio(condAlumnoDao.maximoReg(codigo, periodo)); 
			alumno.setFecha(aca.util.Fecha.getHoy());
		}else if(accion.equals("2")){
			alumno.setMatricula(codigo);
			alumno.setPeriodoId(periodo);
			alumno.setFolio(folio);
			alumno.setIdReporte(reporte);
			alumno.setIdLugar(lugar);
			alumno.setIdJuez(juez);
			alumno.setFecha(fecha);
			alumno.setEmpleado(empleado);
			alumno.setCantidad(cantidad);
			alumno.setComentario(comentario);
			alumno.setPlanId(planId);
			
			if (condAlumnoDao.existeReg(codigo, periodo, folio) == false){
				if (condAlumnoDao.insertReg(alumno)){
					resultado = "Saved: "+alumno.getMatricula();				
				}else{
					resultado = "Error saving: "+alumno.getMatricula();
				}
			}else{
				if (condAlumnoDao.updateReg(alumno)){
					resultado = "Updated: "+alumno.getMatricula();					
				}else{
					resultado = "Error Updating: "+alumno.getMatricula();
				}
			}
		}else if(accion.equals("5")) {
			if (condAlumnoDao.existeReg(codigo, periodo, folio) == true){
				alumno = condAlumnoDao.mapeaRegId(codigo, periodo, folio);				
			}else{
				resultado = "Not found: "+alumno.getMatricula();
			}	
		}
		
		List<CondReporte> lisReporte 	= condReporteDao.getListAll("ORDER BY 3,2");
		List<CondLugar> lisLugar 		= condLugarDao.getListAll("ORDER BY 2");
		List<CondJuez> lisJuez 			= condJuezDao.getListAll("ORDER BY 2");
		List<Maestros> lisMaestros		= maestrosDao.getListAll(" ORDER BY APELLIDO_PATERNO,APELLIDO_MATERNO, NOMBRE");
		List<AlumPlan> lisPlanes		= alumPlanDao.getLista(codigo, " ORDER BY ESTADO DESC, PLAN_ID");
		
		HashMap<String, CondReporte> mapaReportes 	= condReporteDao.mapaReportes();
    	HashMap<String, String> mapaJuez  			= condJuezDao.mapaJuez();
    	HashMap<String, String> mapaLugar			= condLugarDao.mapaLugar();
    	HashMap<String, MapaPlan> mapaPlanes			= mapaPlanDao.mapPlanes("'A','V','I'");
		
		modelo.addAttribute("codigo", codigo);
		modelo.addAttribute("periodo", periodo);
		modelo.addAttribute("fecha", fecha);
		modelo.addAttribute("accion", accion);
		modelo.addAttribute("codigoEmp", codigoEmp);
		modelo.addAttribute("cantidad", cantidad);
		modelo.addAttribute("folio", folio);
		modelo.addAttribute("alumno", alumno);
		modelo.addAttribute("resultado", resultado);
		modelo.addAttribute("alumnoNombre", alumnoNombre);
		
		modelo.addAttribute("lisReporte", lisReporte);
		modelo.addAttribute("lisLugar", lisLugar);
		modelo.addAttribute("lisJuez", lisJuez);
		modelo.addAttribute("lisMaestros", lisMaestros);
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("mapaReportes", mapaReportes);
		modelo.addAttribute("mapaLugar", mapaLugar);
		modelo.addAttribute("mapaJuez", mapaJuez);
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		
		return "disciplina/unidades/grabar";
	}
	
	@RequestMapping("/disciplina/unidades/borrar")
	public String disciplinaUnidadesBorrar(HttpServletRequest request, Model modelo){
		
		String codigo		= request.getParameter("codigoPersonal") == null ? "" : request.getParameter("codigoPersonal");
		String periodo		= request.getParameter("Periodo") == null ? "" : request.getParameter("Periodo");
		String folio		= request.getParameter("folio") == null ? "" : request.getParameter("folio");

		CondAlumno alumno = new CondAlumno(); 
		
		alumno.setMatricula(codigo);
		alumno.setPeriodoId(periodo);
		alumno.setFolio(folio);
		if (condAlumnoDao.existeReg(codigo, periodo, folio) == true){
			if (condAlumnoDao.deleteReg(codigo, periodo, folio)){
				alumno.setFecha(aca.util.Fecha.getHoy());					
			}	
		}	
		
		return "redirect:/disciplina/unidades/unidad";
	}	
}
