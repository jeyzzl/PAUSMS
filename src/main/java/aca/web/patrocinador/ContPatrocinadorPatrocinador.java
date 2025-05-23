package aca.web.patrocinador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.trabajo.spring.TrabDepartamento;
import aca.catalogo.spring.CatAcomodo;
import aca.catalogo.spring.CatAcomodoDao;
import aca.catalogo.spring.CatPatrocinador;
import aca.catalogo.spring.CatPatrocinadorDao;
import aca.catalogo.spring.CatPeriodo;
import aca.catalogo.spring.CatPeriodoDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumAcademico;
import aca.alumno.spring.AlumAcademicoDao;
import aca.alumno.spring.AlumPatrocinador;
import aca.alumno.spring.AlumPatrocinadorDao;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;

@Controller
public class ContPatrocinadorPatrocinador {
	
	@Autowired
	aca.vista.spring.UsuariosDao usuariosDao;

	@Autowired
	AlumPersonalDao alumPersonalDao;

	@Autowired
	AlumAcademicoDao alumAcademicoDao;

	@Autowired
	AlumPlanDao alumPlanDao;
	
	@Autowired
	CatPatrocinadorDao catPatrocinadorDao;
	
	@Autowired
	AlumPatrocinadorDao alumPatrocinadorDao;
	
	@Autowired
	CargaDao cargaDao;

	@Autowired
	CatPeriodoDao catPeriodoDao;

	@Autowired
	CatAcomodoDao catAcomodoDao;

	@RequestMapping("/patrocinador/patrocinador/patrocinador")
	public String patrocinadorPatrocinadorPatrocinador(HttpServletRequest request, Model model) {
		String codigoAlumno = "";
		String nombreAlumno = "";
		
		HttpSession sesion  = ((HttpServletRequest)request).getSession();
		if(sesion  != null) {
			codigoAlumno 		= (String) sesion.getAttribute("codigoAlumno");
			nombreAlumno 		= usuariosDao.getNombreUsuario(codigoAlumno, "NOMBRE");
		}
		
		// List<Carga> lisCargas 									= cargaDao.getListAll("ORDER BY CARGA_ID DESC");
		List<CatPeriodo> lisPeriodos 							= catPeriodoDao.getListAll(" ORDER BY PERIODO_ID");
		List<CatPatrocinador> lisPatrocinadores		 			= catPatrocinadorDao.lisTodos("ORDER BY PATROCINADOR_ID");
		List<AlumPatrocinador> lisAlumPatrocinadores 			= alumPatrocinadorDao.lisTodosPorAlum(codigoAlumno, "ORDER BY PERIODO_ID");		
		// HashMap <String, Carga> mapCargas					 	= cargaDao.mapaCargas();
		HashMap <String, CatPeriodo> mapPeriodos 				= catPeriodoDao.getMapAll(" ORDER BY PERIODO_ID");
		HashMap <String, CatPatrocinador> mapPatrocinadores 	= catPatrocinadorDao.mapaCatPatrocinador();
		
		
		model.addAttribute("nombreAlumno", nombreAlumno);
		model.addAttribute("lisPeriodos", lisPeriodos);
		model.addAttribute("lisPatrocinadores", lisPatrocinadores);
		model.addAttribute("lisAlumPatrocinadores", lisAlumPatrocinadores);
		model.addAttribute("mapPeriodos", mapPeriodos);
		model.addAttribute("mapPatrocinadores", mapPatrocinadores);
		
		return "patrocinador/patrocinador/patrocinador";
	}
	
	@RequestMapping("/patrocinador/patrocinador/grabar")
	public String patrocinadorPatrocinadorGrabar(HttpServletRequest request, Model model) {
		String codigoAlumno = "";
		String periodoId	= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String patId		= request.getParameter("PatrocinadorId")==null?"0":request.getParameter("PatrocinadorId");
		String porcentaje	= request.getParameter("Porcentaje")==null?"0":request.getParameter("Porcentaje");
		String cantidad		= request.getParameter("Cantidad")==null?"0":request.getParameter("Cantidad");
		String mensaje = "";
		
		HttpSession sesion  = ((HttpServletRequest)request).getSession();
		if(sesion  != null) {
			codigoAlumno 		= (String) sesion.getAttribute("codigoAlumno");
		}
		
		AlumPatrocinador alumPat = new AlumPatrocinador();
		if(alumPatrocinadorDao.existeReg(codigoAlumno, periodoId, patId)) {
			mensaje = "1";
		}else {
			alumPat.setCodigoPersonal(codigoAlumno);
			alumPat.setPeriodoId(periodoId);
			alumPat.setPatrocinadorId(patId);
			alumPat.setPorcentaje(porcentaje);
			alumPat.setCantidad(cantidad);
			if(alumPatrocinadorDao.insertReg(alumPat)) {
				mensaje = "2";
			}else {
				mensaje = "3";
			}
		}
		
		
		return "redirect:/patrocinador/patrocinador/patrocinador?mensaje="+mensaje;
	}
	
	@RequestMapping("/patrocinador/patrocinador/borrar")
	public String patrocinadorPatrocinadorBorrar(HttpServletRequest request, Model model) {
		String codigoAlumno = "";
		String periodoId		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String patId		= request.getParameter("PatrocinadorId")==null?"0":request.getParameter("PatrocinadorId");
		String mensaje = "";
		
		HttpSession sesion  = ((HttpServletRequest)request).getSession();
		if(sesion  != null) {
			codigoAlumno 		= (String) sesion.getAttribute("codigoAlumno");
		}
		
		if(alumPatrocinadorDao.existeReg(codigoAlumno, periodoId, patId)) {
			if(alumPatrocinadorDao.deleteReg(codigoAlumno, periodoId, patId)) {
				mensaje = "4";
			}else {
				mensaje = "5";
			}
		}else {
			mensaje = "6";
		}
		
		
		return "redirect:/patrocinador/patrocinador/patrocinador?mensaje="+mensaje;
	}
	
	@RequestMapping("/patrocinador/patrocinador/patrocinadores")
	public String patrocinadorPatrocinadorPatrocinadores(HttpServletRequest request, Model model) {	
		String periodoId = request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String grado 	= request.getParameter("Grado")==null?"0":request.getParameter("Grado");
		String acomodo 	= request.getParameter("Acomodo")==null?"0":request.getParameter("Acomodo");

		// List<Carga> lisCargas = cargaDao.getListAll("ORDER BY CARGA_ID DESC");
		List<CatPeriodo> lisPeriodos = catPeriodoDao.getListAll(" ORDER BY PERIODO_ID");

		List<CatPatrocinador> lisPatrocinadores		 			= catPatrocinadorDao.lisTodos("ORDER BY PATROCINADOR_ID");

		String queryFilter = "";
		if(!grado.equals("0")) queryFilter = " AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ALUM_ACADEMICO WHERE GRADO = '"+grado+"')";

		if(!acomodo.equals("0")) queryFilter += " AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ALUM_ACADEMICO WHERE ACOMODO_ID = "+acomodo+")";

		List<AlumPersonal> lisAlumnos = alumPersonalDao.getListAlumnosEnPeriodo(periodoId, queryFilter+" ORDER BY NOMBRE");
		// alumPersonalDao.

										

		// List<AlumPatrocinador> lisPatrocinadosPorCarga 			= alumPatrocinadorDao.lisPatrocinadosPorCarga(periodoId, "ORDER BY CARGA_ID");		

		List<CatAcomodo> lisAcomodos = catAcomodoDao.getListAll(" ORDER BY TIPO, ACOMODO_ID");

		HashMap <String, AlumAcademico> mapAcademico 			= alumAcademicoDao.getMapAcademico();
		HashMap <String, AlumPlan> mapAlumPlan 					= alumPlanDao.mapAlumPlanActivo();
		HashMap <String, AlumPatrocinador> mapAlumPatrocinador 	= alumPatrocinadorDao.mapaAlumPatrocinador(periodoId);
		// HashMap <String, Carga> mapCargas					 	= cargaDao.mapaCargas();
		HashMap <String, CatPeriodo> mapPeriodos 				= catPeriodoDao.getMapAll(" ORDER BY PERIODO_ID");
		HashMap <String, CatPatrocinador> mapPatrocinadores 	= catPatrocinadorDao.mapaCatPatrocinador();
		
		model.addAttribute("periodoId", periodoId);
		model.addAttribute("grado", grado);
		model.addAttribute("acomodo", acomodo);
		model.addAttribute("lisPeriodos", lisPeriodos);
		model.addAttribute("lisAlumnos", lisAlumnos);
		model.addAttribute("lisPatrocinadores", lisPatrocinadores);
		// model.addAttribute("lisPatrocinadosPorCarga", lisPatrocinadosPorCarga);
		model.addAttribute("lisAcomodos", lisAcomodos);
		model.addAttribute("mapAcademico", mapAcademico);
		model.addAttribute("mapAlumPlan", mapAlumPlan);
		model.addAttribute("mapAlumPatrocinador", mapAlumPatrocinador);
		model.addAttribute("mapPeriodos", mapPeriodos);
		model.addAttribute("mapPatrocinadores", mapPatrocinadores);
		
		return "patrocinador/patrocinador/patrocinadores";
	}

	@RequestMapping("/patrocinador/patrocinador/asignar")
	public String patrocinadorPatrocinadorAsignar(HttpServletRequest request, Model model) {	
		String periodoId 			= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String cantidad 			= request.getParameter("Cantidad")==null?"0":request.getParameter("Cantidad");
		String patrocinador 	= request.getParameter("Patrocinador")==null?"0":request.getParameter("Patrocinador");
		String mensaje  		= "";

		int assigned = 0;
		int errors = 0;
		List<String> errorStudents = new ArrayList<>();

		List<AlumPersonal> lisAlumnos = alumPersonalDao.getListAlumnosEnPeriodo(periodoId, "");
		for(AlumPersonal alumno : lisAlumnos){
			String codigoAlumno = request.getParameter(alumno.getCodigoPersonal());
			AlumPatrocinador alumPatrocinador = new AlumPatrocinador();

			if (codigoAlumno != null && alumno.getCodigoPersonal().equals(codigoAlumno)) {
				if(alumPatrocinadorDao.existeReg(codigoAlumno, periodoId, patrocinador)){
					errors++;
					errorStudents.add(codigoAlumno);
				}else{
					alumPatrocinador.setCodigoPersonal(codigoAlumno);
					alumPatrocinador.setPatrocinadorId(patrocinador);
					alumPatrocinador.setPeriodoId(periodoId);
					alumPatrocinador.setCantidad(cantidad);
					alumPatrocinador.setPorcentaje("0");
					if(alumPatrocinadorDao.insertReg(alumPatrocinador)){
						assigned++;
					}
				}
			}
		}

		if(errors <= 0){
			mensaje = "Successfully assigned sponsorship type to "+assigned+" students ";
		}else if(errors >= 1){
			mensaje = "Error saving students: "+String.join(", ", errorStudents);
		}
		
		return "redirect:/patrocinador/patrocinador/patrocinadores?PeriodoId="+periodoId+"&Mensaje="+mensaje;
	}
}
