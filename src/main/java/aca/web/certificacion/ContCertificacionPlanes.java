package aca.web.certificacion;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatTipoCurso;
import aca.catalogo.spring.CatTipoCursoDao;
import aca.cert.spring.CertCiclo;
import aca.cert.spring.CertCicloDao;
import aca.cert.spring.CertCurso;
import aca.cert.spring.CertCursoDao;
import aca.cert.spring.CertPlan;
import aca.cert.spring.CertPlanDao;
import aca.cert.spring.CertRelacion;
import aca.cert.spring.CertRelacionDao;
import aca.plan.spring.MapaCurso;
import aca.plan.spring.MapaCursoDao;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContCertificacionPlanes {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	private CatFacultadDao catFacultadDao;
	
	@Autowired
	private CertPlanDao certPlanDao;
	
	@Autowired
	private CertCicloDao certCicloDao;
	
	@Autowired
	private CertRelacionDao certRelacionDao;
	
	@Autowired
	private CertCursoDao certCursoDao;
	
	@Autowired
	private MapaPlanDao mapaPlanDao;
	
	@Autowired
	private CatCarreraDao catCarreraDao;
	
	@Autowired
	private CatTipoCursoDao catTipoCursoDao;

	@Autowired
	private MapaCursoDao mapaCursoDao;
	
	@Autowired
	private MaestrosDao maestrosDao;
	
	@RequestMapping("/certificacion/planes/carrera")
	public String certificacionPlanesCarrera(HttpServletRequest request, Model modelo) {

		List<CatFacultad> lisFac = catFacultadDao.getListAll(" ORDER BY 1");
		
		HashMap<String, String> mapaDirectores = maestrosDao.mapaDirectores();

		modelo.addAttribute("lisFac", lisFac);
		modelo.addAttribute("mapaDirectores", mapaDirectores);

		return "certificacion/planes/carrera";
	}

	@RequestMapping("/certificacion/planes/carrera_plan")
	public String certificacionPlanesCarreraPlan(HttpServletRequest request, Model modelo) {
		HttpSession sesion 	= ((HttpServletRequest) request).getSession();
		
		String facultad = request.getParameter("facultad") == null ? "0" : request.getParameter("facultad");
		String accion 	= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		String planId 	= request.getParameter("plan")==null?"0":request.getParameter("plan");
		boolean borro	= false;
		
		List<CertPlan> lisPlan 					= certPlanDao.listFacultad(facultad, "ORDER BY ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID)), CARRERA, PLAN_ID");
		HashMap<String,String> mapNumCursos		= certCursoDao.mapNumCursos(lisPlan);
		HashMap<String,String> mapCarreraPlan	= mapaPlanDao.mapCarreraPlan(lisPlan);
		HashMap<String,CatCarrera> mapCarreras	= catCarreraDao.getMapAll("");
		
		CertPlan certPlan = new CertPlan();
		
		if (facultad.equals("0")){
			facultad = sesion.getAttribute("facultad").toString();		
		}else{
			sesion.setAttribute("facultad", facultad);
		}
		
		if(accion.equals("3")) {
			certPlan.setPlanId(planId);
			if(certPlanDao.deleteReg(planId)) {
				borro = true;
			}
		}
		
		modelo.addAttribute("facultad", facultad);
		modelo.addAttribute("accion", accion);
		modelo.addAttribute("borro", borro);
		modelo.addAttribute("nombreFacultad", catFacultadDao.getNombreFacultad(facultad));
		modelo.addAttribute("lisPlan", lisPlan);
		modelo.addAttribute("mapNumCursos", mapNumCursos);
		modelo.addAttribute("mapCarreraPlan", mapCarreraPlan);
		modelo.addAttribute("mapCarreras", mapCarreras);
		
		return "certificacion/planes/carrera_plan";
	}

	@RequestMapping("/certificacion/planes/edita_plan")
	public String certificacionPlanesEditaPlan(HttpServletRequest request, Model modelo) {
		HttpSession sesion 	= ((HttpServletRequest) request).getSession();
		
		String facultad 	= request.getParameter("facultad") == null ? "0" : request.getParameter("facultad");
		String planId 		= request.getParameter("plan")==null?"0":request.getParameter("plan");
		String metodo		= "";
		
		CertPlan certPlan = new CertPlan();		
		if(certPlanDao.existeReg(planId)){
			certPlan = certPlanDao.mapeaRegId(planId);
			metodo = "Modificar";
		}else{
			metodo = "Guardar";
		}
		
		if (facultad.equals("0")){
			facultad = sesion.getAttribute("facultad").toString();		
		}else{
			sesion.setAttribute("facultad", facultad);
		}

		modelo.addAttribute("facultad", facultad);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("metodo", metodo);
		modelo.addAttribute("certPlan", certPlan);		
		
		return "certificacion/planes/edita_plan";
	}

	@RequestMapping("/certificacion/planes/grabar_plan")
	public String certificacionPlanesGrabar(HttpServletRequest request, Model modelo) {		
				
		String facultadId 	= request.getParameter("facultadId")==null?"0":request.getParameter("facultadId");
		String plan 		= request.getParameter("plan")==null?"0":request.getParameter("plan");
		String mensaje		= "0";
		
		CertPlan certPlan = new CertPlan();
		certPlan.setPlanId(plan);
		certPlan.setFacultad(request.getParameter("facultad"));
		certPlan.setCarrera(request.getParameter("carrera"));
		certPlan.setNumCursos(request.getParameter("numCursos"));
		certPlan.setSemanas(request.getParameter("semanas"));
		certPlan.settInicial(request.getParameter("tInicial"));
		certPlan.settFinal(request.getParameter("tFinal"));
		certPlan.setNota(request.getParameter("nota"));
		certPlan.setPie(request.getParameter("pie"));
		certPlan.setClave(request.getParameter("clave")==null?"N":request.getParameter("clave"));
		certPlan.setFst(request.getParameter("fst")==null?"N":request.getParameter("fst"));
		certPlan.setFsp(request.getParameter("fsp")==null?"N":request.getParameter("fsp"));
		certPlan.setComponente(request.getParameter("componente"));
		certPlan.setCurso(request.getParameter("curso")==null?"N":request.getParameter("curso"));
		certPlan.setRvoe(request.getParameter("rvoe")==null?"RVOE":request.getParameter("rvoe"));
		certPlan.setFechaRetro(request.getParameter("fechaRetro")==null?"01/01/1950":request.getParameter("fechaRetro"));
		certPlan.setTitulo1(request.getParameter("titulo1"));
		certPlan.setTitulo2(request.getParameter("titulo2"));
		certPlan.setTitulo3(request.getParameter("titulo3"));
		
		// Guardar
		if(certPlanDao.existeReg(plan)){
			if(certPlanDao.updateReg(certPlan)){			
				mensaje = "¡Se modificaron los datos del plan!";
			}else{
				mensaje = "¡Error al modificar los datos del plan!";
			}			
		}else{		
			if(certPlanDao.insertReg(certPlan)){
				mensaje = "¡Se grabaron los datos del plan!";		
			}else{
				mensaje = "¡Error al grabar los datos del plan!";
			}			
		}
		
		return "redirect:/certificacion/planes/edita_plan?Mensaje="+mensaje+"&facultad="+facultadId+"&plan="+plan;
		
	}
	
	@RequestMapping("/certificacion/planes/ciclos")
	public String certificacionPlanesCiclos(HttpServletRequest request, Model modelo) {
		
		String planId 		= request.getParameter("plan")==null?"0":request.getParameter("plan");
		String facultad		= request.getParameter("facultad")==null?"0":request.getParameter("facultad");
		String accion 		= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		String ciclo 		= request.getParameter("ciclo")==null?"0":request.getParameter("ciclo");
		String curso 		= request.getParameter("curso")==null?"0":request.getParameter("curso");
		String cursoCert	= request.getParameter("cursoCert")==null?"0":request.getParameter("cursoCert");
		boolean borro		= false;
		
		List<CertCiclo> lisCiclos = certCicloDao.getListPlan(planId, " ORDER BY CICLO_ID");
		List<CertCurso> lisCursos = certCursoDao.getListPlan(planId, " ORDER BY CICLO_ID, ORDEN");
		
		HashMap<String,CertRelacion> mapCertRelacion = certRelacionDao.mapCertRelacion(lisCursos);
		HashMap<String, MapaCurso> mapaCursosPlan	 = mapaCursoDao.mapaCursosPlan(planId);
		HashMap<String, CatTipoCurso> mapaTipoCurso	 = catTipoCursoDao.getMapAll("");
		HashMap<String,Integer> mapCursosPorPlan	 = certCursoDao.mapCursosPorPlan(planId);
		
		CertPlan certPlan 			= certPlanDao.mapeaRegId(planId);
		CertCurso certCurso 		= new CertCurso();
		CertRelacion certRelacion 	= new CertRelacion();		
		if(accion.equals("3")) {			
			if(certCicloDao.deleteReg(planId,ciclo)) {
				borro = true;
				lisCiclos = certCicloDao.getListPlan(planId, " ORDER BY CICLO_ID");
			}
		}

		if(accion.equals("6")) {
			certCurso.setCursoId(curso);
			if(certCursoDao.deleteReg(curso)) {
				borro = true;
				lisCursos = certCursoDao.getListPlan(planId, " ORDER BY CICLO_ID, ORDEN");
			}
		}

		if(accion.equals("7")) {
			certRelacion.setCursoCert(cursoCert);
			if(certRelacionDao.deleteReg(cursoCert)) {
				borro = true;
			}
		}

		modelo.addAttribute("planId", planId);
		modelo.addAttribute("facultad", facultad);
		modelo.addAttribute("accion", accion);
		modelo.addAttribute("ciclo", ciclo);
		modelo.addAttribute("curso", curso);
		modelo.addAttribute("cursoCert", cursoCert);
		modelo.addAttribute("certPlan", certPlan);
		modelo.addAttribute("borro", borro);
		modelo.addAttribute("lisCiclos", lisCiclos);
		modelo.addAttribute("lisCursos", lisCursos);
		modelo.addAttribute("mapCertRelacion", mapCertRelacion);
		modelo.addAttribute("mapaCursosPlan", mapaCursosPlan);
		modelo.addAttribute("mapaTipoCurso", mapaTipoCurso);
		modelo.addAttribute("mapCursosPorPlan", mapCursosPorPlan);

		return "certificacion/planes/ciclos";
	}
	
	@RequestMapping("/certificacion/planes/edita_ciclo")
	public String certificacionPlanesEditaCiclo(HttpServletRequest request, Model modelo) {

		String planId 	= request.getParameter("plan")==null?"0":request.getParameter("plan");	
		String facultad	= request.getParameter("facultad")==null?"0":request.getParameter("facultad");
		String accion 	= request.getParameter("Accion")==null?"0":request.getParameter("Accion");	
		String cicloId	= request.getParameter("ciclo")==null?"0":request.getParameter("ciclo");	
		String metodo	= "";
		String regresa	= "0";
		boolean inserta	= false;
		
		CertCiclo certCiclo = new CertCiclo();
		CertPlan certPlan 	= certPlanDao.mapeaRegId(planId);
		
		if(cicloId.equals("0"))
			metodo = "Guardar";
		else{
			metodo = "Modificar";
			certCiclo = certCicloDao.mapeaRegId(planId, cicloId);
		}
		
		if(accion.equals("1")){	//	Guardar
			certCiclo.setPlanId(planId);
			certCiclo.setCicloId(certCicloDao.maxReg(planId));
			certCiclo.setTitulo(request.getParameter("titulo"));
			certCiclo.setFst(request.getParameter("fst"));
			certCiclo.setFsp(request.getParameter("fsp"));
			certCiclo.setCreditos(request.getParameter("creditos"));
			if(certCicloDao.insertReg(certCiclo)) {
				inserta	= true;
				accion 	= "1";
				regresa	= "1";
			}else{
				
			}
		}else if(accion.equals("2")){
			certCiclo.setPlanId(planId);
			certCiclo.setCicloId(cicloId);
			certCiclo.setTitulo(request.getParameter("titulo"));
			certCiclo.setFst(request.getParameter("fst"));
			certCiclo.setFsp(request.getParameter("fsp"));
			certCiclo.setCreditos(request.getParameter("creditos"));
			if(certCicloDao.updateReg(certCiclo)) {
				inserta	= true;
				accion 	= "2";
				regresa	= "1";
			}else{
				
			}
		}else {
			
		}

		modelo.addAttribute("planId", planId);
		modelo.addAttribute("facultad", facultad);
		modelo.addAttribute("cicloId", cicloId);
		modelo.addAttribute("accion", accion);
		modelo.addAttribute("metodo", metodo);
		modelo.addAttribute("inserta", inserta);
		modelo.addAttribute("certCiclo", certCiclo);
		modelo.addAttribute("certPlan", certPlan);

		if(regresa.equals("1")) {
			return "redirect:/certificacion/planes/ciclos?Accion="+accion+"&plan="+planId;
		}else {
			return "certificacion/planes/edita_ciclo";
		}
	}
	
	@RequestMapping("/certificacion/planes/agregar_cursos")
	public String certificacionPlanesAgregarCursos(HttpServletRequest request, Model modelo) {
		
		String planId 	= request.getParameter("plan")==null?"0":request.getParameter("plan");
		String cursoId 	= request.getParameter("cursoId")==null?"0":request.getParameter("cursoId");
		String facultad	= request.getParameter("facultad")==null?"0":request.getParameter("facultad");
		String cicloId	= request.getParameter("ciclo")==null?"0":request.getParameter("ciclo");	
		String clave 	= request.getParameter("curso")==null?"0":request.getParameter("clave");		
		String accion 	= request.getParameter("Accion")==null?"0":request.getParameter("Accion");	
		boolean inserta	= false;
		
		CertCurso certCurso = certCursoDao.mapeaRegId(cursoId);
		CertPlan certPlan 	= certPlanDao.mapeaRegId(planId);
		
		List<CatTipoCurso> lis = catTipoCursoDao.getListAll(" ORDER BY 1" );
		
		if (accion.equals("1")){
			if (certCursoDao.existeReg(cursoId)==true) {
				certCurso.setPlanId(planId);
				certCurso.setCursoId(cursoId);
				certCurso.setClave(request.getParameter("clave"));
				certCurso.setCicloId(cicloId);
				certCurso.setCursoNombre(request.getParameter("nombre"));
				certCurso.setFst(request.getParameter("fst"));
				certCurso.setFsp(request.getParameter("fsp"));
				certCurso.setCreditos(request.getParameter("creditos"));
				certCurso.setOrden(request.getParameter("orden"));
				certCurso.setTipoCursoId(request.getParameter("tipoCursoId"));			
				if(certCursoDao.updateReg(certCurso)){
					inserta = true;
				}			
			}else{				
				String maximo = certCursoDao.maximoCurso(planId);		
				certCurso.setPlanId(planId);
				certCurso.setCursoId(planId+"-"+maximo);
				certCurso.setClave(request.getParameter("clave"));
				certCurso.setCicloId(cicloId);
				certCurso.setCursoNombre(request.getParameter("nombre"));
				certCurso.setFst(request.getParameter("fst"));
				certCurso.setFsp(request.getParameter("fsp"));
				certCurso.setCreditos(request.getParameter("creditos"));
				certCurso.setOrden(request.getParameter("orden"));
				certCurso.setTipoCursoId(request.getParameter("tipoCursoId"));
				if(certCursoDao.insertReg(certCurso)){
					inserta = true;
				}				
			}	
		}

		modelo.addAttribute("planId", planId);
		modelo.addAttribute("cicloId", cicloId);
		modelo.addAttribute("facultad", facultad);
		modelo.addAttribute("cursoId", cursoId);
		modelo.addAttribute("clave", clave);	
		modelo.addAttribute("inserta", inserta);
		modelo.addAttribute("lis", lis);
		modelo.addAttribute("certPlan", certPlan);
		modelo.addAttribute("certCurso", certCurso);		
		
		return "certificacion/planes/agregar_cursos";
	}
	
	@RequestMapping("/certificacion/planes/unir_materia")
	public String certificacionPlanesUnirMateria(HttpServletRequest request, Model modelo) {

		String planId 	= request.getParameter("plan")==null?"0":request.getParameter("plan");	
		String materia 	= request.getParameter("materia")==null?"0":request.getParameter("materia");
		
		List<MapaCurso> lisCursos = mapaCursoDao.getListPlan(planId, "ORDER BY CICLO, NOMBRE_CURSO");

		modelo.addAttribute("planId", planId);
		modelo.addAttribute("materia", materia);
		modelo.addAttribute("lisCursos", lisCursos);

		return "certificacion/planes/unir_materia";
	}

	@RequestMapping("/certificacion/planes/ciclosAccion")
	public String certificacionPlanesCiclosAccion(HttpServletRequest request, Model modelo) {
		
		String accion 		= request.getParameter("Accion")==null?"0":request.getParameter("Accion");	
		String certCurso 	= request.getParameter("certCurso")==null?"0":request.getParameter("certCurso");
		String cursoId 		= request.getParameter("curso")==null?"0":request.getParameter("curso");
		String planId 		= request.getParameter("plan")==null?"0":request.getParameter("plan");	
		String curso 		= "";
		boolean existe		= false;
		boolean inserto		= false;
		boolean borro		= false;
		
		CertRelacion certRelacion 	= new CertRelacion();
		
		if(accion.equals("1")){	
			curso = mapaCursoDao.getMateria(cursoId);
			
			certRelacion.setCursoCert(certCurso);
			certRelacion.setCursoId(cursoId);
			if(certRelacionDao.existeReg(certCurso)){
				existe = true;
			}else {
				if(certRelacionDao.insertReg(certRelacion)){
					inserto	= true;
				}
			}
		}else if(accion.equals("2")){	
			curso = mapaCursoDao.getMateria(cursoId);
			
			certRelacion.setCursoCert(certCurso);
			if(certRelacionDao.deleteReg(certCurso)){
				borro = true;
				certRelacion.setCursoId(cursoId);
				if(certRelacionDao.insertReg(certRelacion)){
					inserto	= true;
				}
			}
		}
		
		modelo.addAttribute("accion", accion);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("cursoId", cursoId);
		modelo.addAttribute("curso", curso);
		modelo.addAttribute("certCurso", certCurso);
		modelo.addAttribute("existe", existe);
		modelo.addAttribute("inserto", inserto);
		modelo.addAttribute("borro", borro);
		
		return "certificacion/planes/ciclosAccion";
	}

}
