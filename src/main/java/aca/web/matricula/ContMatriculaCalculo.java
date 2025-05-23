package aca.web.matricula;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import aca.alumno.spring.AlumAcademico;
import aca.alumno.spring.AlumAcademicoDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanDao;
import aca.attache.spring.AttacheCustomer;
import aca.attache.spring.AttacheCustomerDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaAlumno;
import aca.carga.spring.CargaAlumnoDao;
import aca.carga.spring.CargaBloque;
import aca.carga.spring.CargaBloqueDao;
import aca.carga.spring.CargaDao;
import aca.carga.spring.CargaFinanciero;
import aca.carga.spring.CargaFinancieroDao;
import aca.carga.spring.CargaGrupoDao;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatPeriodo;
import aca.catalogo.spring.CatPeriodoDao;
import aca.financiero.spring.FesCcobro;
import aca.financiero.spring.FesCcobroDao;
import aca.financiero.spring.FinCalculo;
import aca.financiero.spring.FinCalculoDao;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.AlumnoCursoDao;

@Controller
public class ContMatriculaCalculo {		
	
	@Autowired
	CargaDao cargaDao;	
	
	@Autowired
	CargaFinancieroDao cargaFinancieroDao;
		
	@Autowired
	CargaBloqueDao cargaBloqueDao;
	
	@Autowired
	CatFacultadDao catFacultadDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	FesCcobroDao fesCcobroDao;
	
	@Autowired
	AlumnoCursoDao alumnoCursoDao;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;
	
	@Autowired
	CatPeriodoDao catPeriodoDao;
	
	@Autowired
	AlumPlanDao alumPlanDao;
	
	@Autowired
	CargaGrupoDao cargaGrupoDao;

	@Autowired
	CargaAlumnoDao cargaAlumnoDao;
	
	@Autowired
	FinCalculoDao finCalculoDao;
	
	@Autowired
	private MapaPlanDao mapaPlanDao;
	
	@Autowired
	private AlumAcademicoDao alumAcademicoDao;
	
	@Autowired
	private AttacheCustomerDao attacheCustomerDao;
	
	@RequestMapping("/matricula/calculo/listado")
	public String matriculaCalculoListado(HttpServletRequest request, Model modelo){
		
		String codigoAlumno			= "0";
		String alumnoNombre 		= "-";
		String periodoId		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String cargaId			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String bloqueId			= request.getParameter("BloqueId")==null?"0":request.getParameter("BloqueId");
		String mensaje			= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		String grabados			= request.getParameter("grabados")==null?"0":request.getParameter("grabados");
		String errores			= request.getParameter("errores")==null?"0":request.getParameter("errores");

		HttpSession sesion 			= ((HttpServletRequest)request).getSession();		
		if (sesion != null){
			codigoAlumno 		= request.getParameter("CodigoAlumno")==null?(String)sesion.getAttribute("codigoAlumno"):request.getParameter("CodigoAlumno");
			alumnoNombre 		= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
			
        }
		
		AlumAcademico alumno = alumAcademicoDao.mapeaRegId(codigoAlumno);
		
		List<CatPeriodo> lisPeriodos = catPeriodoDao.getListAll("ORDER BY PERIODO_ID DESC");
		if(periodoId.equals("0") && lisPeriodos.size() > 0) {
			periodoId = lisPeriodos.get(0).getPeriodoId();
		}
		
		List<Carga> lisCargas 	= cargaDao.getListPeriodo(periodoId," AND ESTADO = '1' ORDER BY CARGA_ID ASC");
		if(cargaId.equals("0") && lisCargas.size() > 0) {
				cargaId = lisCargas.get(0).getCargaId();
		}
		
		List<CargaBloque> lisBloques = cargaBloqueDao.getLista(cargaId, "");
		
		if (bloqueId.equals("0") && lisBloques.size() > 0) {
				bloqueId = lisBloques.get(0).getBloqueId();
		}
		 
		List<CargaFinanciero> lisAlum = cargaFinancieroDao.getListPorCargayBloque(cargaId, bloqueId, " ORDER BY FECHA DESC");
		
		
		
		HashMap<String, String> mapaAlumPlan					= alumPlanDao.mapaPlanesActivosTodos();
		HashMap<String,AlumPersonal> mapaAlumPersonal			= alumPersonalDao.mapaAlumnosAlumnos("");
		HashMap<String,CargaBloque> mapaBloques					= cargaBloqueDao.mapaBloques();
		HashMap<String,CargaBloque> mapaBloquesActivos			= cargaBloqueDao.mapaBloquesActivos();
		HashMap<String,AttacheCustomer> mapaCustomer 			= null;
		HashMap<String,String> mapaBalances = null;
		
		if(sesion.getAttribute("institucion").equals("Pacific Adventist University")){
			mapaCustomer = attacheCustomerDao.mapaAttacheCustomer();
			mapaBalances = attacheCustomerDao.mapaAttacheBalance();
		}
		
		modelo.addAttribute("PeriodoId", periodoId);
		modelo.addAttribute("CargaId", cargaId);
		modelo.addAttribute("BloqueId", bloqueId);
		modelo.addAttribute("grabados", grabados);
		modelo.addAttribute("errores", errores);
		modelo.addAttribute("Mensaje", mensaje);
		modelo.addAttribute("alumno", alumno);
		modelo.addAttribute("codigoAlumno", codigoAlumno);
		modelo.addAttribute("alumnoNombre", alumnoNombre);
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisBloques", lisBloques);
		modelo.addAttribute("lisAlum", lisAlum);
		modelo.addAttribute("mapaAlumPlan", mapaAlumPlan);
		modelo.addAttribute("mapaAlumPersonal", mapaAlumPersonal);
		modelo.addAttribute("mapaBloques", mapaBloques);
		modelo.addAttribute("mapaBloquesActivos", mapaBloquesActivos);
		modelo.addAttribute("mapaCustomer", mapaCustomer);
		modelo.addAttribute("mapaBalances", mapaBalances);
		
		return "matricula/calculo/listado";
	}
	
	@RequestMapping("/matricula/calculo/editar")
	public String matriculaCalculoEditar(HttpServletRequest request, Model modelo){
		
		String cargaId 		= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String bloqueId 	= request.getParameter("BloqueId")==null?"0":request.getParameter("BloqueId");
		String mensaje 		= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		String planId				= "0";
		String nombrePlan			= "-";
		String codigoAlumno = request.getParameter("CodigoAlumno")==null?"0":request.getParameter("CodigoAlumno");
		FinCalculo finCalc;
		boolean esAlumno = false;
		
		String alumnoNombre 		= "-";
		HttpSession sesion 			= ((HttpServletRequest)request).getSession();		
		if (sesion != null){
			if(codigoAlumno.equals("0") || codigoAlumno == null){
				codigoAlumno 		= request.getParameter("CodigoAlumno")==null?(String)sesion.getAttribute("codigoAlumno"):request.getParameter("CodigoAlumno");
			}
			alumnoNombre 		= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
			if (codigoAlumno.subSequence(0, 1).equals("0") || codigoAlumno.subSequence(0, 1).equals("1") || codigoAlumno.subSequence(0, 1).equals("2")){
        		esAlumno 	= true;
        	}
        }
		
		planId = alumPlanDao.getPlanActual(codigoAlumno);
		
		nombrePlan = mapaPlanDao.getNombrePlan(planId);
		
		AlumAcademico alumAcademico = new AlumAcademico();		
		
		if(alumAcademicoDao.existeReg(codigoAlumno)) {
			alumAcademico = alumAcademicoDao.mapeaRegId(codigoAlumno);
		}
		
		if(finCalculoDao.existeReg(codigoAlumno, cargaId, bloqueId)) {
			finCalc = finCalculoDao.mapeaRegId(codigoAlumno, cargaId, bloqueId);
		}else {
			finCalc = new FinCalculo();
		}
		
		HashMap<String, CargaFinanciero> mapCargaFinanciero = cargaFinancieroDao.mapaAlumnoCarga("");	

		// Trae info de Attache
		AttacheCustomer customer = new AttacheCustomer();
		if(sesion.getAttribute("institucion").equals("Pacific Adventist University")){
			if(attacheCustomerDao.existeReg(codigoAlumno)){
				customer = attacheCustomerDao.mapeaRegId(codigoAlumno);
			}
		}
		
		modelo.addAttribute("codigoAlumno", codigoAlumno);
		modelo.addAttribute("alumnoNombre", alumnoNombre);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("nombrePlan", nombrePlan);
		modelo.addAttribute("esAlumno", esAlumno);	
		modelo.addAttribute("alumAcademico", alumAcademico);
		modelo.addAttribute("Mensaje", mensaje);
		modelo.addAttribute("finCalc", finCalc);
		modelo.addAttribute("CargaId", cargaId);
		modelo.addAttribute("BloqueId", bloqueId);
		modelo.addAttribute("customer", customer);
		modelo.addAttribute("mapCargaFinanciero", mapCargaFinanciero);
		
		return "matricula/calculo/editar";
	}
	
	@RequestMapping("/matricula/calculo/editarMult")
	public String matriculaCalculoEditarMult(HttpServletRequest request, Model modelo){
		
		String cargaId 		= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String bloqueId 	= request.getParameter("BloqueId")==null?"0":request.getParameter("BloqueId");
		String mensaje 		= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		String status		= request.getParameter("Status")==null?"0":request.getParameter("Status");
		String codigoAlumno = "0";
		String accion 		= request.getParameter("accion")==null?"0":request.getParameter("accion");
		String usuario 		= "0";
		int count			= Integer.parseInt(request.getParameter("count")==null?"0":request.getParameter("count"));
		
		FinCalculo finCalc;
		
		String alumnoNombre 		= "-";
		HttpSession sesion 			= ((HttpServletRequest)request).getSession();		
		if (sesion != null){
			codigoAlumno 		= request.getParameter("CodigoAlumno")==null?"0":request.getParameter("CodigoAlumno");
			alumnoNombre 		= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
			usuario = (String) sesion.getAttribute("codigoPersonal");
        }
		Set<String> lisMatriculas = new HashSet<String>();
		List<CargaFinanciero> lisCargaFinanciero = cargaFinancieroDao.getListAll("");
		if(accion.equals("1")) {
			CargaFinanciero cargaFinanciero = new CargaFinanciero();
			int grabados = 0;
			int errores = 0;
			for(int i = 1; i < count; i++) {
				String matricula = request.getParameter("matricula"+i);
				if(cargaFinancieroDao.existeReg(matricula, cargaId, bloqueId) == false) {
					cargaFinanciero.setBloqueId(bloqueId);
					cargaFinanciero.setCargaId(cargaId);
					cargaFinanciero.setCodigoPersonal(matricula);
					cargaFinanciero.setComentario("Bulk Input");
					cargaFinanciero.setFecha(aca.util.Fecha.getHoy());
					cargaFinanciero.setStatus(status);
					cargaFinanciero.setUsuario(usuario);
					if(cargaFinancieroDao.insertReg(cargaFinanciero)) {
						grabados++;
					}else {
						errores++;
					}
				}
				
				
			}
			return "matricula/calculo/listado?grabados="+grabados+"&CargaId="+cargaId+"&BloqueId="+bloqueId+"&errores="+errores;
		}
		if(codigoAlumno.equals("0") == false) {
			for(int i = 1; i < count; i++) {
				String matricula = request.getParameter("matricula"+i);
				lisMatriculas.add(matricula);
				
			}
			lisMatriculas.add(codigoAlumno);
		}
				
		List<AlumPersonal> lisAlumnos		= alumPersonalDao.getListAll(" ORDER BY APELLIDO_PATERNO,APELLIDO_MATERNO,NOMBRE");
		
		HashMap<String,AlumPersonal> mapAlum		= alumPersonalDao.mapaAlumnosAlumnos("");
		
		modelo.addAttribute("CargaId", cargaId);
		modelo.addAttribute("BloqueId", bloqueId);
		modelo.addAttribute("lisMatriculas", lisMatriculas);
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("mapAlum", mapAlum);
		
		return "matricula/calculo/editarMult";
	}
	
	
	@RequestMapping("/matricula/calculo/grabar") 
	public String matriculaCalculoGrabar(@RequestParam("imagen") MultipartFile imagen, HttpServletRequest request, Model modelo){
	  
	  String usuario = "0";
	  String cargaId = request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
	  String bloqueId = request.getParameter("BloqueId")==null?"0":request.getParameter("BloqueId");
	  String status = request.getParameter("Status")==null?"0":request.getParameter("Status");
	  String comentario = request.getParameter("Comentario")==null?"0":request.getParameter("Comentario");
	  String hasImage = request.getParameter("hasImage")==null?"0":request.getParameter("hasImage");
	  String codigoAlumno = request.getParameter("CodigoAlumno")==null?"0": request.getParameter("CodigoAlumno");
	  String alumnoNombre = "-"; 
	  String mensaje = "0";
	  
	  HttpSession sesion = ((HttpServletRequest)request).getSession();
	  if (sesion != null){ 
		  if(codigoAlumno.equals("0") || codigoAlumno == null) {
			  codigoAlumno = (String)sesion.getAttribute("codigoAlumno");
		  }
		  usuario = (String) sesion.getAttribute("codigoPersonal");
	  }
	  
	  alumnoNombre = alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE"); 
	  
	  CargaFinanciero carga = new CargaFinanciero();
	  FinCalculo finCalculo = new FinCalculo();
	  
	  if(cargaFinancieroDao.existeReg(codigoAlumno, cargaId, bloqueId)) {
		  
		  carga = cargaFinancieroDao.mapeaRegId(codigoAlumno, cargaId, bloqueId);
		  
		  carga.setStatus(status);
		  carga.setComentario(comentario);
		  
		  if(finCalculoDao.existeReg(codigoAlumno, cargaId, bloqueId)) {
			  finCalculo = finCalculoDao.mapeaRegId(codigoAlumno, cargaId, bloqueId);
		  }else {
			  finCalculo.setCodigoPersonal(codigoAlumno);
			  finCalculo.setCargaId(cargaId);
			  finCalculo.setBloqueId(bloqueId);
		  } 
		  
		  finCalculo.setFecha(aca.util.Fecha.getHoy());
		  
		  if(!hasImage.equals("1")) {
			  try {
				finCalculo.setArchivo(imagen.getBytes());
				finCalculo.setNombre(imagen.getOriginalFilename());
			  }catch (IOException e) {
				System.out.println("Error saving image: " + e);
			  }
		  }
		  
		  
		  if(cargaFinancieroDao.updateReg(carga)) {
			  if(finCalculoDao.existeReg(codigoAlumno, cargaId, bloqueId)) {
				  if(finCalculoDao.updateReg(finCalculo)) {
					  mensaje = "3";	
				  }else {
					  mensaje = "7";
				  }
			  }else {
				  if(finCalculoDao.insertReg(finCalculo)) {
					  mensaje = "3";	
				  }else {
					  mensaje = "7";
				  }
			  }
		  }else {
			  mensaje = "4";
		  }
	  }else {
		  carga.setCodigoPersonal(codigoAlumno);
		  carga.setCargaId(cargaId);
		  carga.setBloqueId(bloqueId);
		  carga.setFecha(aca.util.Fecha.getFechayHora());
		  carga.setUsuario(usuario);
		  carga.setStatus(status);
		  carga.setComentario(comentario);
		  finCalculo.setCodigoPersonal(codigoAlumno);
		  finCalculo.setCargaId(cargaId);
		  finCalculo.setBloqueId(bloqueId);
		  finCalculo.setFecha(aca.util.Fecha.getHoy());
		  try {
				finCalculo.setArchivo(imagen.getBytes());
				finCalculo.setNombre(imagen.getOriginalFilename());
			} catch (IOException e) {
				System.out.println("Error saving image: " + e);
			}
		  if(cargaFinancieroDao.insertReg(carga) && finCalculoDao.insertReg(finCalculo)) {
			  mensaje = "1";
		  }else {
			  mensaje = "2";
		  }
	  }
	  
	  
	  
	  return "redirect:/matricula/calculo/listado?CodigoAlumno="+codigoAlumno+"&CargaId="+cargaId+"&BloqueId="+bloqueId+"&Mensaje="+mensaje; 
	}

	@RequestMapping("/matricula/calculo/quickEdit") 
	public String matriculaCalculoQuickEdit(HttpServletRequest request, Model modelo){
		String usuario = "0";
		String cargaId = request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String bloqueId = request.getParameter("BloqueId")==null?"0":request.getParameter("BloqueId");
		String status = request.getParameter("Status")==null?"0":request.getParameter("Status");
		String codigoAlumno = request.getParameter("CodigoAlumno")==null?"0": request.getParameter("CodigoAlumno");
		String mensaje = "0";

		HttpSession sesion = ((HttpServletRequest)request).getSession();
		if (sesion != null){ 
			usuario = (String) sesion.getAttribute("codigoPersonal");
		}

		CargaFinanciero carga = new CargaFinanciero();

		if(cargaFinancieroDao.existeReg(codigoAlumno, cargaId, bloqueId)) {
			carga = cargaFinancieroDao.mapeaRegId(codigoAlumno, cargaId, bloqueId);
		  
			carga.setStatus(status);

			if(cargaFinancieroDao.updateReg(carga)) {
				mensaje = "3";
			}

		}

		return "redirect:/matricula/calculo/listado?CodigoAlumno="+codigoAlumno+"&CargaId="+cargaId+"&BloqueId="+bloqueId+"&Mensaje="+mensaje; 
	}
	  
	@RequestMapping("/matricula/calculo/borrar") 
	public String matriculaCalculoBorrar(HttpServletRequest request, Model modelo){
	  
	  String usuario = "0";
	  String cargaId = request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
	  String bloqueId = request.getParameter("BloqueId")==null?"0":request.getParameter("BloqueId");
	  String codigoAlumno = "0";
	  String alumnoNombre = "-"; 
	  String mensaje = "0";
	  
	  HttpSession sesion = ((HttpServletRequest)request).getSession();
	  if (sesion != null){ 
		  codigoAlumno = request.getParameter("CodigoAlumno")==null?(String)sesion.getAttribute("codigoAlumno"):request.getParameter("CodigoAlumno"); 
		  alumnoNombre = alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE"); 
		  usuario = (String) sesion.getAttribute("codigoPersonal");
	  }

	  CargaAlumno cargaAlumno = new CargaAlumno();
	  
	  if(cargaFinancieroDao.deleteReg(codigoAlumno, cargaId, bloqueId)) {
		// Actualiza CALCULO en CARGA_ALUMNO
		if(cargaAlumnoDao.existeReg(codigoAlumno, cargaId, bloqueId)){
			cargaAlumno = cargaAlumnoDao.mapeaRegId(codigoAlumno, cargaId, bloqueId);
			cargaAlumno.setCalculo("N");
			cargaAlumnoDao.updateReg(cargaAlumno);
		}

		if(finCalculoDao.deleteReg(codigoAlumno, cargaId, bloqueId)) {
			mensaje = "5";
		}else {
			mensaje = "6";
		}
	  }else {
		  mensaje = "6";
	  }
	  
	  
	  
	  return "redirect:/matricula/calculo/listado?CodigoAlumno="+codigoAlumno+"&CargaId="+cargaId+"&BloqueId="+bloqueId+"&Mensaje="+mensaje; 
	  }
	  
	  @RequestMapping("/matricula/calculo/borrarImg") 
	  public String matriculaCalculoBorrarImg(HttpServletRequest request, Model modelo){
	  
	  String usuario = "0";
	  String cargaId = request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
	  String bloqueId = request.getParameter("BloqueId")==null?"0":request.getParameter("BloqueId");
	  String codigoAlumno = request.getParameter("CodigoAlumno")==null?"0":request.getParameter("CodigoAlumno");
	  String alumnoNombre = "-"; 
	  String mensaje = "0";
	  
	  HttpSession sesion = ((HttpServletRequest)request).getSession();
	  if (sesion != null){ 
		  alumnoNombre = alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE"); 
		  usuario = (String) sesion.getAttribute("codigoPersonal");
	  }
	  
	  if(finCalculoDao.deleteReg(codigoAlumno, cargaId, bloqueId)) {
		  mensaje = "1";
	  }else {
		  mensaje = "2";
	  }
	  
	  
	  
	  return "redirect:/matricula/calculo/editar?CodigoAlumno="+codigoAlumno+"&CargaId="+cargaId+"&BloqueId="+bloqueId+"&Mensaje="+mensaje; 
	  }

	
	 
}