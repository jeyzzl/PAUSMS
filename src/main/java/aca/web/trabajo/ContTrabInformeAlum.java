package aca.web.trabajo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.time.LocalDate;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumAcademico;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanDao;
import aca.bec.spring.BecInforme;
import aca.bec.spring.BecInformeAlumno;
import aca.bec.spring.BecPuestoAlumno;
import aca.financiero.spring.ContCcosto;
import aca.financiero.spring.ContEjercicio;
import aca.trabajo.spring.TrabAlum;
import aca.trabajo.spring.TrabAlumDao;
import aca.trabajo.spring.TrabCategoria;
import aca.trabajo.spring.TrabCategoriaDao;
import aca.trabajo.spring.TrabDepartamento;
import aca.trabajo.spring.TrabDepartamentoDao;
import aca.trabajo.spring.TrabInforme;
import aca.trabajo.spring.TrabInformeAlum;
import aca.trabajo.spring.TrabInformeAlumDao;
import aca.trabajo.spring.TrabInformeDao;
import aca.trabajo.spring.TrabPeriodo;
import aca.trabajo.spring.TrabPeriodoDao;
import aca.util.Fecha;



@Controller
public class ContTrabInformeAlum {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	private TrabInformeAlumDao trabInformeAlumDao;
	
	@Autowired
	private TrabDepartamentoDao trabDepartamentoDao;
	
	@Autowired
	private TrabCategoriaDao trabCategoriaDao;
	
	@Autowired
	private TrabInformeDao trabInformeDao;
	
	@Autowired
	private TrabAlumDao trabAlumDao;
	
	@Autowired
	private TrabPeriodoDao trabPeriodoDao;

	@Autowired
	private AlumPlanDao	alumPlanDao;

	@Autowired
	private AlumPersonalDao	alumPersonalDao;
	
	
	@RequestMapping("/trabajo/informeAlum/informe")
	public String trabajoInformeInforme(HttpServletRequest request, Model modelo){
		String codigoPersonal	= "0";
		String codigoAlumno	 	= "0";
		String periodoId 		= request.getParameter("periodoId")==null?"1":request.getParameter("periodoId");
		String informeId 		= request.getParameter("informeId")==null?"0":request.getParameter("informeId");
		String categoriaId 		= request.getParameter("categoriaId")==null?"0":request.getParameter("categoriaId");
		String deptoId 			= request.getParameter("deptoId")==null?"1":request.getParameter("deptoId");
		String editar 			= request.getParameter("editar")==null?"0":request.getParameter("editar");
		String sAccion 			= request.getParameter("Accion")==null?"1":request.getParameter("Accion");
		int nAccion				= Integer.parseInt(sAccion);
		String mensaje 			= "";
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
			codigoAlumno		= (String) sesion.getAttribute("codigoAlumno");			
		}
		Fecha fecha = new Fecha();
		
		TrabAlum alumno = trabAlumDao.mapeaRegId(codigoAlumno, deptoId, categoriaId, periodoId);
		
		List<TrabInforme> lisInformes = null;
		if(periodoId == null) {
			lisInformes		= trabInformeDao.lisTodos(" ORDER BY INFORME_ID");
		}else {
			lisInformes		= trabInformeDao.lisPorPeriodo(periodoId, " ORDER BY INFORME_ID");
		}
		List<TrabAlum> lisAlums			 		= trabAlumDao.lisPorDeptyPeriodo(periodoId, deptoId, " AND ESTADO = 'A'");
		List<TrabPeriodo> lisPeriodos 			= trabPeriodoDao.lisTodos(" ORDER BY PERIODO_ID");
		List<TrabDepartamento> lisDeptos		= trabDepartamentoDao.lisTodos(" ORDER BY DEPT_ID");
		List<TrabCategoria> lisCategorias		= trabCategoriaDao.lisPorDepartamento("1", " ORDER BY CAT_ID");
		
		switch(nAccion) {
		case 1: {
			break;
		}case 2:
		{
			nAccion = 1;
			int succ = 0;
			int err = 0;
			for (TrabAlum alum: lisAlums) {
				
				TrabInformeAlum registro = new TrabInformeAlum();
				String horas = request.getParameter(alum.getMatricula()+"Hora");
				
				registro.setPeriodoId(periodoId);
				registro.setMatricula(alum.getMatricula());
				registro.setInformeId(informeId);
				registro.setDeptId(deptoId);
				registro.setCatId(alum.getCatId());
				registro.setUsuario(codigoPersonal);	
				System.out.println(aca.util.Fecha.getFechayHora());
				registro.setFecha(aca.util.Fecha.getFechayHora());
				
				if(horas != null && !horas.equals("") && alum.getPago().equals("0")) {
					registro.setHoras(horas);
				}
				
				if (trabInformeAlumDao.existeRegPorInforme(alum.getMatricula(), informeId)){
					if (trabInformeAlumDao.updateRegPorInforme(registro)){
						mensaje = "Updated: "+alum.getMatricula();
						succ++;
					}else{
						mensaje = "Error updating: "+alum.getMatricula();
						err++;
					}
										
				}else{
					if (trabInformeAlumDao.insertReg(registro)){
						mensaje = "Saved: "+alum.getMatricula();
						succ++;
					}else{
						mensaje = "Error saving: "+alum.getMatricula();
						err++;
					}
				}	
				
			}
			mensaje = "Saved: " + succ + "\n" + "Errors: " + err;
		}
		}

		TrabPeriodo periodo = trabPeriodoDao.mapeaRegId(periodoId);
		String fechaInicio = periodo.getFechaIni();
		String fechaFin = periodo.getFechaFin();
		String fechaHoy = aca.util.Fecha.getHoy();

		boolean editarActivo = aca.util.Fecha.getFechaBetweenFecha(fechaInicio, fechaFin, fechaHoy);
		
		HashMap<String, String> mapaAlumNombre	= trabAlumDao.mapaAlumNombre(""); 
		HashMap<String, String> mapaPeriodoNombre	= trabPeriodoDao.mapaPeriodoNombre();
		HashMap<String, String> mapaCatNombre	= trabCategoriaDao.mapaCategoriaNombre();
		HashMap<String, String> mapaAlumHoras	= trabInformeAlumDao.mapaAlumHorasPorInformeyDept(deptoId, "");
		HashMap<String, String> mapaAlumHorasCompletadas	= trabInformeAlumDao.mapaAlumHorasCompletadasPorDept(deptoId, "");
		HashMap<String, String> mapaInformeAlum	= trabInformeAlumDao.mapInformesPorAlumno();
		
		modelo.addAttribute("alumno", alumno);		
		modelo.addAttribute("deptoId", deptoId);
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("informeId", informeId);
		modelo.addAttribute("editar", editar);
		modelo.addAttribute("Mensaje", mensaje);
		modelo.addAttribute("categoriaId", categoriaId);
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("lisInformes", lisInformes);
		modelo.addAttribute("lisAlums", lisAlums);
		modelo.addAttribute("lisDeptos", lisDeptos);
		modelo.addAttribute("lisCategorias", lisCategorias);
		modelo.addAttribute("mapaAlumNombre", mapaAlumNombre);
		modelo.addAttribute("mapaPeriodoNombre", mapaPeriodoNombre);
		modelo.addAttribute("mapaCatNombre", mapaCatNombre);
		modelo.addAttribute("mapaAlumHoras", mapaAlumHoras);
		modelo.addAttribute("mapaAlumHorasCompletadas", mapaAlumHorasCompletadas);
		modelo.addAttribute("mapaInformeAlum", mapaInformeAlum);
		modelo.addAttribute("editarActivo", editarActivo);
		

		return "trabajo/informeAlum/informe";
	}
}
