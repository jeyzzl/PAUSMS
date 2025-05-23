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
import aca.NotificationService;
import aca.acceso.spring.AccesoDao;
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
import aca.trabajo.spring.TrabAsesorDao;
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
public class ContTrabInformeHoras {
	
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
	private TrabAsesorDao trabAsesorDao;

	@Autowired
	private AlumPlanDao	alumPlanDao;

	@Autowired
	private AlumPersonalDao	alumPersonalDao;

	@Autowired
	private AccesoDao accesoDao;	

	@Autowired
    private NotificationService notificationService;

    @RequestMapping("/trabajo/informeHoras/informeHoras")
	public String trabajoInformesHoras(HttpServletRequest request, Model modelo){
		String deptId 			= request.getParameter("DeptId")==null?"0":request.getParameter("DeptId");
		String catId 			= request.getParameter("CatId")==null?"0":request.getParameter("CatId");
		String periodoId 		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String fecha 			= request.getParameter("Fecha")==null?aca.util.Fecha.getHoy():request.getParameter("Fecha");
		String codigoPersonal 	= "";
		boolean esAdmin 		= false;

		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
			esAdmin 			= (boolean) sesion.getAttribute("admin");
		}

		List<TrabPeriodo> lisPeriodos 			= trabPeriodoDao.lisTodos(" WHERE ESTADO = 'A' ORDER BY PERIODO_ID");

		List<TrabDepartamento> lisDepartamentos = trabDepartamentoDao.lisTodos(" ORDER BY DEPT_ID");		
		if (periodoId.equals("0")&&lisPeriodos.size()>= 1) periodoId =lisPeriodos.get(0).getPeriodoId();
		if (deptId.equals("0")&&lisDepartamentos.size()>= 1) deptId =lisDepartamentos.get(0).getDeptId();

		List<TrabCategoria> lisCategorias 		= trabCategoriaDao.lisPorDepartamento(deptId, " ORDER BY CAT_ID");
		if (catId.equals("0")&&lisCategorias.size()>= 1) catId = lisCategorias.get(0).getCategoriaId();

		List<TrabInformeAlum> lisInformes		= trabInformeAlumDao.lisInformes(deptId, catId, periodoId, fecha, " ORDER BY CAT_ID");

		boolean esAsesor = trabAsesorDao.esAsesor(codigoPersonal);
		boolean esDirector = codigoPersonal.equals(trabDepartamentoDao.getDirector(deptId))?true:false;

		HashMap<String, AlumPersonal> mapaAlumPersonal 		= alumPersonalDao.mapaAlumnosAlumnos("");
		HashMap<String, String> mapaNombreCategorias 		= trabCategoriaDao.mapaCategoriaNombre();

		modelo.addAttribute("esAsesor", esAsesor);
		modelo.addAttribute("esDirector", esDirector);
		modelo.addAttribute("esAdmin", esAdmin);
		modelo.addAttribute("deptId", deptId);
		modelo.addAttribute("catId", catId);
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("fecha", fecha);
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("lisDepartamentos", lisDepartamentos);
		modelo.addAttribute("lisCategorias", lisCategorias);
		modelo.addAttribute("lisInformes", lisInformes);
		modelo.addAttribute("mapaAlumPersonal", mapaAlumPersonal);
		modelo.addAttribute("mapaNombreCategorias", mapaNombreCategorias);

		return "trabajo/informeHoras/informeHoras";
	}

	@RequestMapping("/trabajo/informeHoras/autorizar")
	public String trabajoInformeAutorizar(HttpServletRequest request, Model modelo){
		String informeId 		= request.getParameter("InformeId")==null?"0":request.getParameter("InformeId");
		String deptId 			= request.getParameter("DeptId")==null?"0":request.getParameter("DeptId");
		String catId 			= request.getParameter("CatId")==null?"0":request.getParameter("CatId");
		String periodoId 		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String fecha 			= request.getParameter("Fecha")==null?aca.util.Fecha.getHoy():request.getParameter("Fecha");
		String matricula 		= request.getParameter("Matricula")==null?"0":request.getParameter("Matricula");
		String codigoPersonal 	= "";
		String mensaje 			= "";

		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
		}

		TrabInformeAlum informe = new TrabInformeAlum();
		if(trabInformeAlumDao.existeReg(matricula, informeId)){
			informe = trabInformeAlumDao.mapeaRegId(matricula, informeId);
			informe.setStatus("A");
			informe.setUsuario(codigoPersonal);
			if(trabInformeAlumDao.updateReg(informe)){
				String notificationMessage = " Your report ("+informeId+") has been AUTHORIZED.";
				notificationService.sendNotificationToAdmin(notificationMessage, accesoDao.getUsuario(matricula));	
				mensaje = "Authorized";
			}else{
				mensaje = "Error authorizing report";
			}
		}else{
			mensaje = "Error, no matching report";
		}

		return "redirect:/trabajo/informeHoras/informeHoras?DeptId="+deptId+"&CatId="+catId+"&PeriodoId="+periodoId+"&Fecha="+fecha+"&Mensaje="+mensaje;
	}

	@RequestMapping("/trabajo/informeHoras/aprovar")
	public String trabajoInformeAprovar(HttpServletRequest request, Model modelo){
		String informeId 		= request.getParameter("InformeId")==null?"0":request.getParameter("InformeId");
		String deptId 			= request.getParameter("DeptId")==null?"0":request.getParameter("DeptId");
		String catId 			= request.getParameter("CatId")==null?"0":request.getParameter("CatId");
		String periodoId 		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String fecha 			= request.getParameter("Fecha")==null?aca.util.Fecha.getHoy():request.getParameter("Fecha");
		String matricula 		= request.getParameter("Matricula")==null?"0":request.getParameter("Matricula");
		String codigoPersonal 	= "";
		String mensaje 			= "";

		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
		}

		TrabInformeAlum informe = new TrabInformeAlum();
		if(trabInformeAlumDao.existeReg(matricula, informeId)){
			informe = trabInformeAlumDao.mapeaRegId(matricula, informeId);
			informe.setStatus("C");
			informe.setUsuario(codigoPersonal);
			informe.setHoras(trabInformeAlumDao.horasPorReporte(matricula, informeId));

			if(trabInformeAlumDao.updateReg(informe)){
				String notificationMessage = " Your report ("+informeId+") has been APPROVED.";
				notificationService.sendNotificationToAdmin(notificationMessage, accesoDao.getUsuario(matricula));	
				mensaje = "Approved";
			}else{
				mensaje = "Error approving report";
			}
		}else{
			mensaje = "Error, no matching report";
		}

		return "redirect:/trabajo/informeHoras/informeHoras?DeptId="+deptId+"&CatId="+catId+"&PeriodoId="+periodoId+"&Fecha="+fecha+"&Mensaje="+mensaje;
	}

	@RequestMapping("/trabajo/informeHoras/eliminar")
	public String trabajoInformeEliminar(HttpServletRequest request, Model modelo){
		String informeId 		= request.getParameter("InformeId")==null?"0":request.getParameter("InformeId");
		String deptId 			= request.getParameter("DeptId")==null?"0":request.getParameter("DeptId");
		String catId 			= request.getParameter("CatId")==null?"0":request.getParameter("CatId");
		String periodoId 		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String fecha 			= request.getParameter("Fecha")==null?aca.util.Fecha.getHoy():request.getParameter("Fecha");
		String matricula 		= request.getParameter("Matricula")==null?"0":request.getParameter("Matricula");
		String codigoPersonal 	= "";
		String mensaje 			= "";

		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
		}

		TrabInformeAlum informe = new TrabInformeAlum();
		if(trabInformeAlumDao.existeReg(matricula, informeId)){
			if(trabInformeAlumDao.deleteReg(matricula, informeId)){
				mensaje = "Deleted";
			}else{
				mensaje = "Error deleting report";
			}
		}else{
			mensaje = "Error, no matching report";
		}

		return "redirect:/trabajo/informeHoras/informeHoras?DeptId="+deptId+"&CatId="+catId+"&PeriodoId="+periodoId+"&Fecha="+fecha+"&Mensaje="+mensaje;
	}

	@RequestMapping("/trabajo/informeHoras/sync")
	public String trabajoInformeSync(HttpServletRequest request, Model modelo){
		String informeId 		= request.getParameter("InformeId")==null?"0":request.getParameter("InformeId");
		String deptId 			= request.getParameter("DeptId")==null?"0":request.getParameter("DeptId");
		String catId 			= request.getParameter("CatId")==null?"0":request.getParameter("CatId");
		String periodoId 		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String fecha 			= request.getParameter("Fecha")==null?aca.util.Fecha.getHoy():request.getParameter("Fecha");
		String matricula 		= request.getParameter("Matricula")==null?"0":request.getParameter("Matricula");
		String codigoPersonal 	= "";
		String mensaje 			= "";

		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
		}

		TrabInformeAlum informe = new TrabInformeAlum();
		if(trabInformeAlumDao.existeReg(matricula, informeId)){
			if(trabInformeAlumDao.syncHorasPorReporte(matricula, informeId)){
				mensaje = "Synced";
			}else{
				mensaje = "Error syncing report";
			}
		}else{
			mensaje = "Error, no matching report";
		}

		return "redirect:/trabajo/informeHoras/informeHoras?DeptId="+deptId+"&CatId="+catId+"&PeriodoId="+periodoId+"&Fecha="+fecha+"&Mensaje="+mensaje;
	}

} 
