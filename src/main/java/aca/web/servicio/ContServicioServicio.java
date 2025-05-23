package aca.web.servicio;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.log.spring.LogOperacion;
import aca.log.spring.LogOperacionDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.ssoc.spring.SsocAsignacion;
import aca.ssoc.spring.SsocAsignacionDao;
import aca.ssoc.spring.SsocDocAlum;
import aca.ssoc.spring.SsocDocAlumDao;
import aca.ssoc.spring.SsocDocumentos;
import aca.ssoc.spring.SsocDocumentosDao;
import aca.ssoc.spring.SsocInicio;
import aca.ssoc.spring.SsocInicioDao;
import aca.ssoc.spring.SsocRequisito;
import aca.ssoc.spring.SsocRequisitoDao;
import aca.ssoc.spring.SsocSector;
import aca.ssoc.spring.SsocSectorDao;
import aca.util.Fecha;

@Controller
public class ContServicioServicio {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;		
	
	@Autowired
	aca.salida.spring.SalSolicitudDao salSolicitudDao;
	
	@Autowired
	aca.salida.spring.SalGrupoDao salGrupoDao;
	
	@Autowired
	aca.vista.spring.MaestrosDao maestrosDao;
	
	@Autowired
	SsocInicioDao ssocInicioDao;
	
	@Autowired
	SsocDocAlumDao ssocDocAlumDao;
	
	@Autowired
	SsocDocumentosDao ssocDocumentosDao;
	
	@Autowired
	SsocAsignacionDao ssocAsignacionDao;
	
	@Autowired
	SsocSectorDao ssocSectorDao;
	
	@Autowired
	CatFacultadDao catFacultadDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	MapaPlanDao mapaPlanDao;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;	
	
	@Autowired
	AlumPlanDao alumPlanDao;
	
	@Autowired
	AccesoDao accesoDao;
	
	@Autowired
	private SsocRequisitoDao ssocRequisitoDao;
	
	@Autowired
	private LogOperacionDao logOperacionDao;

	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}	
	
	
	@RequestMapping("/servicio/servicio/documentos")
	public String servicioServicioDocumentos(HttpServletRequest request, Model modelo){
		
		String matricula		= "-";
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			matricula 			= (String) sesion.getAttribute("codigoAlumno");
		}		

		String sPlan	= request.getParameter("PlanId");
		int accion=0;
		if(request.getParameter("accion")!=null)
			accion=Integer.parseInt(request.getParameter("accion"));
		String grupoDocs=request.getParameter("grupoDocs");
		//System.out.println(grupoDocs);
		if (grupoDocs==null)grupoDocs="";
		
		List<SsocDocumentos> listaDocumentos = ssocDocumentosDao.lisDocumentos("ORDER BY DOCUMENTO_ID");
		
		if(accion==1){
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
			String[] arrGrupoDocs = grupoDocs.split(",");
			for(int i=0;i<arrGrupoDocs.length;i++){
				SsocDocAlum documento = new SsocDocAlum();
				documento.setCodigoPersonal(matricula);
				documento.setDocumentoId(Integer.parseInt(arrGrupoDocs[i]));
				documento.setPlanId(sPlan);
				//System.out.println("array:"+arrGrupoDocs[i]+"-");
				documento.setAsignacionId(0);
				documento.setFecha(sdf.format(new java.util.Date()));
				documento.setNumHoras(0);
				documento.setComentario("");
				documento.setEntregado("N");
				ssocDocAlumDao.guardaDocumento(documento);
			}
		}
		
		modelo.addAttribute("sPlan", sPlan);
		modelo.addAttribute("accion", accion);
		modelo.addAttribute("listaDocumentos", listaDocumentos);
		
		return "servicio/servicio/documentos";
	}	
	
	@RequestMapping("/servicio/servicio/reglamento")
	public String servicioServicioReglamento(HttpServletRequest request){		
		return "servicio/servicio/reglamento";
	}
	
	@RequestMapping("/servicio/servicio/requisitos")
	public String servicioServicioRequisitos(HttpServletRequest request, Model modelo){
		
		 List<SsocRequisito> Lisrequisitos =  ssocRequisitoDao.getRequisitos("ORDER BY REQUISITO_ID");		 
		 modelo.addAttribute("Lisrequisitos", Lisrequisitos);
		 
		return "servicio/servicio/requisitos";
	}
	
	@RequestMapping("/servicio/servicio/servicioTST")
	public String servicioServicioServicioTST(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContServicioServicio|servicioServicioTST:");
		return "servicio/servicio/servicioTST";
	}
	
	@Transactional
	@RequestMapping("/servicio/servicio/social")
	public String servicioServicioSocial(HttpServletRequest request, Model modelo){
		
		String codigoPersonal 	= "0";
		String codigoAlumno		= "0";

		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");
			codigoAlumno 		= (String) sesion.getAttribute("codigoAlumno");
		}
		
		AlumPersonal alumPersonal	= alumPersonalDao.mapeaRegId(codigoAlumno);
		Acceso acceso 				= accesoDao.mapeaRegId(codigoPersonal);
		AlumPlan alumPlan			= alumPlanDao.mapeaRegId(codigoAlumno);
		String planId				= request.getParameter("PlanId")==null?alumPlan.getPlanId():request.getParameter("PlanId");
		String accion				= request.getParameter("Accion")==null?alumPlan.getPlanId():request.getParameter("Accion");	
		String carreraId			= mapaPlanDao.getCarreraId(planId);
		
		List<AlumPlan> lisPlanes	= alumPlanDao.getLista(codigoAlumno, "ORDER BY PLAN_ID");
		boolean existePlan = false;		
		for (AlumPlan plan : lisPlanes ) {
			if (plan.getPlanId().equals(planId)) existePlan = true;
		}
		if (existePlan==false && lisPlanes.size() >= 1) planId = lisPlanes.get(0).getPlanId();
		
		String porcentaje								= alumPlanDao.getAlumPromCreditos(codigoAlumno, alumPlan.getPlanId()); 
		String fechaServicio							= ssocInicioDao.getFecha(codigoAlumno, planId);				
		boolean tieneDocumentos							= ssocDocAlumDao.getTieneDoctos(codigoAlumno, planId);			
		
		int requisitos									= ssocDocumentosDao.totalPorTipo("P");
		int requisitosAlumno							= ssocDocAlumDao.totalRequisitos(codigoAlumno, planId);
		boolean cumplePrerrequisitos 					= requisitosAlumno >= requisitos;
		List<SsocAsignacion> lisAsignaciones 			= ssocAsignacionDao.getAsignaciones(codigoAlumno);
		List<SsocDocAlum> lisDocumentos					= ssocDocAlumDao.getDocPlan(codigoAlumno, planId);
		List<SsocDocAlum> lisFaltantes					= ssocDocAlumDao.lisDocumentosFaltantes(codigoAlumno, planId);
		HashMap<String,SsocDocumentos> mapaDocumentos	= ssocDocumentosDao.mapaTodos();
		HashMap<String,SsocAsignacion> mapaAsignaciones	= ssocAsignacionDao.mapaAsignaciones(codigoAlumno);		
		HashMap<String,String> mapaRegistradas			= ssocDocAlumDao.mapaAsignacionesReg(codigoAlumno);
		HashMap<String,String> mapaEmpleados			= ssocDocAlumDao.mapaNombreEmpleado();
		
		SsocInicio ssocInicio 		= new SsocInicio();		
				
		if (accion.equals("6")) {
			ssocInicio = ssocInicioDao.mapeaRegId(codigoAlumno, planId);
			ssocInicio.setFecha(ssocInicioDao.getMinFecha(codigoAlumno, planId));
			ssocInicioDao.updateReg(ssocInicio);
		}
		
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("carreraId", carreraId);
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("alumPlan", alumPlan);		
		modelo.addAttribute("porcentaje", porcentaje);
		modelo.addAttribute("fechaServicio", fechaServicio);
		modelo.addAttribute("tieneDocumentos", tieneDocumentos);
		modelo.addAttribute("cumplePrerrequisitos", cumplePrerrequisitos);
		
		modelo.addAttribute("lisPlanes", lisPlanes);		
		modelo.addAttribute("lisDocumentos", lisDocumentos);
		modelo.addAttribute("lisAsignaciones", lisAsignaciones);
		modelo.addAttribute("lisFaltantes", lisFaltantes);
		modelo.addAttribute("mapaDocumentos", mapaDocumentos);
		modelo.addAttribute("mapaAsignaciones", mapaAsignaciones);
		modelo.addAttribute("mapaRegistradas", mapaRegistradas);
		modelo.addAttribute("mapaEmpleados", mapaEmpleados);
		
		return "servicio/servicio/social";
	}
	
	@RequestMapping("/servicio/servicio/editarDocumento")
	public String servicioServicioEditarDocumento(HttpServletRequest request, Model modelo){
		
		String matricula 		= "0";
		String codigoPersonal 	= "0";
		String alumnoNombre		= "-";
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
			matricula 			= (String) sesion.getAttribute("codigoAlumno");
			alumnoNombre 		= alumPersonalDao.getNombreAlumno(matricula, "NOMBRE");
		}
		
		String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String planId			= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String carreraId		= mapaPlanDao.getCarreraId(planId);
		String mensaje			= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		Acceso acceso 			= accesoDao.mapeaRegId(codigoPersonal);
		
		SsocDocAlum ssocDocAlum	= new SsocDocAlum();		
		if (ssocDocAlumDao.existeReg(matricula, String.valueOf(folio) )){
			ssocDocAlum 		= ssocDocAlumDao.mapeaReg(matricula, folio);
		}
		
		List<SsocDocumentos> lisRequisitos 		= ssocDocumentosDao.lisDocumentos(" ORDER BY ORDEN");
		List<SsocAsignacion> lisAsignaciones 	= ssocAsignacionDao.getAsignaciones(matricula);		
		
		modelo.addAttribute("alumnoNombre", alumnoNombre);
		modelo.addAttribute("carreraId", carreraId);
		modelo.addAttribute("ssocDocAlum", ssocDocAlum);		
		modelo.addAttribute("acceso", acceso);				
		modelo.addAttribute("mensaje", mensaje);		
		
		modelo.addAttribute("lisAsignaciones", lisAsignaciones);
		modelo.addAttribute("lisRequisitos", lisRequisitos);
		
		return "servicio/servicio/editarDocumento";
	}
	
	@RequestMapping("/servicio/servicio/grabarDocumento")
	public String servicioServicioGrabarDocumento(HttpServletRequest request, Model modelo){
		
		String matricula 		= "0";	
		String codigoPersonal	= "0";	
		String mensaje			= "0";	
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");
			matricula 			= (String) sesion.getAttribute("codigoAlumno");			
		}		
		String planId			= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");		
		String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String entregado		= request.getParameter("Entregado")==null?"N":request.getParameter("Entregado");
		int documentoId			= request.getParameter("DocumentoId")==null?0:Integer.parseInt(request.getParameter("DocumentoId"));
		int asignacionId		= request.getParameter("AsignacionId")==null?0:Integer.parseInt(request.getParameter("AsignacionId"));
		int numHoras			= request.getParameter("NumHoras")==null?0:Integer.parseInt(request.getParameter("NumHoras"));				
		String fecha			= request.getParameter("Fecha")==null?"":request.getParameter("Fecha");
		String comentario		= request.getParameter("Comentario")==null?"":request.getParameter("Comentario");
		
		
		SsocDocAlum documento	= new SsocDocAlum();
		documento.setCodigoPersonal(matricula);
		documento.setFolio(Integer.parseInt(folio));
		documento.setPlanId(planId);
		documento.setEntregado(entregado);
		documento.setDocumentoId(documentoId);
		documento.setAsignacionId(asignacionId);
		documento.setNumHoras(numHoras);
		documento.setFecha(fecha);
		documento.setComentario(comentario);
		documento.setUsuario(codigoPersonal);
		if (ssocDocAlumDao.existeReg(matricula, folio)) {
			if(ssocDocAlumDao.modificaDocumento(documento)) {
				mensaje = "1";
			}
		}else {
			folio = ssocDocAlumDao.maximoReg(matricula);
			documento.setFolio(Integer.parseInt(folio));
			if(ssocDocAlumDao.guardaDocumento(documento)) {
				mensaje = "1";
			}
		}
		
		return "redirect:/servicio/servicio/editarDocumento?PlanId="+planId+"&Folio="+folio+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/servicio/servicio/borrarDocumento")
	public String servicioServicioBorrarDocumento(HttpServletRequest request){
		String planId			= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String matricula 		= "0";		
		String usuario	 		= "0";		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			usuario 			= (String) sesion.getAttribute("codigoPersonal");
			matricula 			= (String) sesion.getAttribute("codigoAlumno");
		}		
		
		LogOperacion log = new LogOperacion();		
		
		log.setTabla("ssoc_docalum");
		log.setDatos("CodigoPersonal: "+matricula+", Folio: "+folio);
		log.setIp(request.getRemoteAddr());
		log.setFecha(aca.util.Fecha.getHoy());
		log.setOperacion("delete");
		log.setUsuario(usuario);
		
		if (ssocDocAlumDao.existeReg(matricula, folio)) {
			if(ssocDocAlumDao.eliminaDocumento(matricula, Integer.parseInt(folio))) {
				logOperacionDao.insertReg(log);
			}
			if (ssocDocAlumDao.numDocAlum(matricula,planId)==0){				
				if (ssocInicioDao.existeReg(matricula, planId)){
					ssocInicioDao.deleteReg(matricula, planId);
				}
			}						
		}
		return "redirect:/servicio/servicio/social?PlanId="+planId;
	}
	
	@RequestMapping("/servicio/servicio/agregarAsignacion")
	public String servicioServicioAgregarAsignacion(HttpServletRequest request, Model modelo){
		
		String matricula 		= "0";		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			matricula 			= (String) sesion.getAttribute("codigoAlumno");
		}	
		
		String nombreAlumno = alumPersonalDao.getNombreAlumno(matricula, "NOMBRE");
		String asId			= request.getParameter("asId")==null?"0":request.getParameter("asId");
			
		AlumPlan plan = new AlumPlan();
		plan = alumPlanDao.mapeaRegId(matricula);
		String	planId		= request.getParameter("PlanId")==null?plan.getPlanId():request.getParameter("PlanId");		
		
		SsocAsignacion asignacion = new SsocAsignacion();
		if(ssocAsignacionDao.existeReg(matricula, asId)) {
			asignacion = ssocAsignacionDao.mapeaRegId(matricula, asId);
		}
		
		if(asId.equals("0")){
			asId = ssocAsignacionDao.MaximoReg(matricula);
			asignacion.setAsignacionId(asId);
		}
		
		modelo.addAttribute("matricula", matricula);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("asignacion", asignacion);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("asId", asId);
		
		return "servicio/servicio/agregarAsignacion";
	}
	
	@RequestMapping("/servicio/servicio/grabarAsignacion")
	public String servicioServicioGrabarAsignacion(HttpServletRequest request, Model modelo){
		
		String matricula 		= "0";		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			matricula 			= (String) sesion.getAttribute("codigoAlumno");			
		}			
		
		int mensaje 			= 0;
		String	planId			= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String direccion		= request.getParameter("direccion")==null?"":request.getParameter("direccion");	
		String telefono			= request.getParameter("telefono")==null?"":request.getParameter("telefono");	
		String asId				= request.getParameter("asId")==null?"0":request.getParameter("asId");
		String sector			= request.getParameter("sector")==null?"0":request.getParameter("sector");
		String dependencia		= request.getParameter("dependencia")==null?"0":request.getParameter("dependencia");
		String responsable		= request.getParameter("responsable")==null?"0":request.getParameter("responsable");
		String fechaInicial		= request.getParameter("fechaInicial")==null?"0":request.getParameter("fechaInicial");
		
		SsocAsignacion asignacion = new SsocAsignacion();
		asignacion.setCodigoPersonal(matricula);
		asignacion.setAsignacionId(asId);
		asignacion.setSector(sector);
		asignacion.setDependencia(dependencia);
		asignacion.setDireccion(direccion);
		asignacion.setTelefono(telefono);	
		asignacion.setResponsable(responsable);
		asignacion.setFechaInicio(fechaInicial);
		
		if (!ssocAsignacionDao.existeReg(matricula, asId)) {
			
			if (ssocAsignacionDao.insertReg(asignacion)) {
				mensaje = 1;
			} else {
				mensaje = 2;
			}
			
		} else {
			if (ssocAsignacionDao.updateReg(asignacion)) {
				mensaje = 3;
			} else {
				mensaje = 4;
			}
		}
		
		return "redirect:/servicio/servicio/agregarAsignacion?PlanId="+planId+"&asId="+asId;
	}
	
	@RequestMapping("/servicio/servicio/borrarAsignacion")
	public String servicioServicioBorrarAsignacion(HttpServletRequest request){
		
		String planId			= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String asId				= request.getParameter("asId")==null?"0":request.getParameter("asId");
		int mensaje 			= 0;
		String matricula 		= "0";		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			matricula 			= (String) sesion.getAttribute("codigoAlumno");
		}		
		
		if(ssocAsignacionDao.existeReg(matricula, asId)){
			if(ssocAsignacionDao.deleteReg(matricula, asId)){
				mensaje = 5;
			}else{
				mensaje = 6;
			}
		}else{
			mensaje = 7;
		}
	
		return "redirect:/servicio/servicio/social?PlanId="+planId;
	}
	
}