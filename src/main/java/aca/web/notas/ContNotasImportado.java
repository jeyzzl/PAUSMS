package aca.web.notas;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlanDao;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatGradePoint;
import aca.catalogo.spring.CatGradePointDao;
import aca.catalogo.spring.CatTipoCal;
import aca.catalogo.spring.CatTipoCalDao;
import aca.emp.spring.EmpleadoDao;
import aca.kardex.spring.KrdxCursoImp;
import aca.kardex.spring.KrdxCursoImpDao;
import aca.plan.spring.MapaCurso;
import aca.plan.spring.MapaCursoDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.AlumnoCursoDao;

@Controller
public class ContNotasImportado {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired	
	private AlumPersonalDao alumnPersonalDao;
	
	@Autowired	
	private CatCarreraDao catCarreraDao ;
	
	@Autowired	
	private EmpleadoDao empleadoDao ;
	
	@Autowired	
	private KrdxCursoImpDao krdxCursoImpDao ;
	
	@Autowired
	MapaCursoDao mapaCursoDao;	
	
	@Autowired
	MapaPlanDao mapaPlanDao;
	
	@Autowired
	CatTipoCalDao catTipoCalDao;	
	
	@Autowired
	AlumPersonalDao alumPersonalDao;
	
	@Autowired
	AlumPlanDao alumPlanDao;
	
	@Autowired
	CatGradePointDao catGradePointDao;
	
	@Autowired
	AlumnoCursoDao alumnoCursoDao;
	
	
	@RequestMapping("/notas/importado/accion")
	public String notasImportadoAccion(HttpServletRequest request, Model modelo ){	
		
		String planId			= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String codigoAlumno		= "0"; 
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
        	codigoAlumno 		= sesion.getAttribute("codigoAlumno")==null?"0":(String)sesion.getAttribute("codigoAlumno");
		}
		String nombreAlumno 	= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
		
		List<MapaPlan> lisPlanes		= mapaPlanDao.listPlanesDelAlumno(codigoAlumno, "ORDER BY PLAN_ID");
		if (planId.equals("0") && lisPlanes.size()>=1){
			planId = lisPlanes.get(0).getPlanId();
		}
		
		List<MapaCurso> lisMaterias		= krdxCursoImpDao.lisMateriasSinRegistrar(codigoAlumno, planId, "ORDER BY CICLO, ORDEN, NOMBRE_CURSO" );
		List<CatTipoCal> lisTipos		= catTipoCalDao.getListAll("ORDER BY TIPOCAL_ID");
		List<CatGradePoint> lisGrades	= catGradePointDao.lisTodos(" ORDER BY GP_ID");		
		
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("lisMaterias", lisMaterias);
		modelo.addAttribute("lisTipos", lisTipos);
		modelo.addAttribute("lisGrades", lisGrades);		
		
		return "notas/importado/accion";
	}
	
	@RequestMapping("/notas/importado/cursos")
	public String notasImportadoCursos(HttpServletRequest request, Model modelo){
		
		String codigoAlumno		= "0"; 
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
        	codigoAlumno 		= sesion.getAttribute("codigoAlumno")==null?"0":(String)sesion.getAttribute("codigoAlumno");
		}
		
		String nombreAlumno		= alumnPersonalDao.getNombre(codigoAlumno, "NOMBRE");
		String planId 		 	= alumnPersonalDao.getPlanActivo(codigoAlumno);
	    String carreraId	 	= alumnPersonalDao.getCarreraId(planId);
		String nombreCarrera 	= catCarreraDao.getNombreCarrera(carreraId);
		
		List<KrdxCursoImp> lisImportados  = krdxCursoImpDao.lisImportados(codigoAlumno, "ORDER BY ENOC.PLAN(CURSO_ID), ENOC.CICLO_CURSO(CURSO_ID), ENOC.NOMBRE_MATERIA(CURSO_ID)");
		List<CatTipoCal> lisTipos	      = catTipoCalDao.getListAll(" ORDER BY 1");
		HashMap<String,String> mapaEmpleados 	= empleadoDao.mapEmpleado();
		HashMap<String,MapaCurso> mapaCursos	= mapaCursoDao.mapaCursoEnImportado(codigoAlumno);		
		HashMap<String,String> mapaGradePoint	= alumnoCursoDao.mapaGradePoint(codigoAlumno);
		
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("nombreCarrera", nombreCarrera);
		modelo.addAttribute("carreraId", carreraId);
		modelo.addAttribute("lisImportados", lisImportados);
		modelo.addAttribute("mapaEmpleados", mapaEmpleados);
		modelo.addAttribute("mapaCursos", mapaCursos);
		modelo.addAttribute("mapaGradePoint", mapaGradePoint);
		modelo.addAttribute("lisTipos", lisTipos);
		
		return "notas/importado/cursos";
	}
	
	@RequestMapping("/notas/importado/grabar")
	public String notasImportadoGrabar(HttpServletRequest request){
		
		String planId	= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String codigoAlumno		= "0"; 
		String codigoPersonal 	= "0"; 
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
        	codigoAlumno 		= sesion.getAttribute("codigoAlumno")==null?"0":(String)sesion.getAttribute("codigoAlumno");
        	codigoPersonal 		= sesion.getAttribute("codigoPersonal")==null?"0":(String)sesion.getAttribute("codigoPersonal");
		}
		List<MapaCurso> lisMaterias		= krdxCursoImpDao.lisMateriasSinRegistrar(codigoAlumno, planId, "ORDER BY CICLO, ORDEN, NOMBRE_CURSO" );
		KrdxCursoImp krdxCursoImp 		= new KrdxCursoImp(); 
		for(int i= 0; i < lisMaterias.size();  i++){	
			String check			= request.getParameter("check"+i);
			String cursoId			= request.getParameter("cursoId"+i);
			String fecha			= request.getParameter("fecha"+i);
			String fechaExtra		= request.getParameter("fechaExtra"+i);
			String observaciones	= request.getParameter("observaciones"+i);
			String condicion		= request.getParameter("condicion"+i);
			String convalidada		= request.getParameter("convalidada"+i);
			String grade			= request.getParameter("grade"+i);
			String ciclo			= request.getParameter("ciclo"+i);
			String califExtr		= request.getParameter("califExtra"+i);
			String calificacion		= request.getParameter("calificacion"+i);
			String titulo			= request.getParameter("titulo"+i);
			String optativa			= request.getParameter("optativa"+i);
			String notaConva 		= "-";
			String optativaNombre  	= request.getParameter("tipoCursoId"+i);			
			
		 	if( titulo==null || titulo.equals(" ")|| titulo.equals("")){
		 		titulo = "N";
			}
		 	if( optativa==null || optativa.equals(" ") || optativa.equals("")){
		 		optativa = "N";
			}
		 	if (convalidada.equals("S")){
		 		notaConva = calificacion;
		 		calificacion = "0";
		 	}
		 	if (!grade.equals("N")){
		 		calificacion = grade;
		 	}
			
			if(check != null){
				if((!calificacion.equals("")) && (!fecha.equals(""))){
					String folio = krdxCursoImpDao.maximoReg(codigoAlumno);
					krdxCursoImp.setCodigoPersonal(codigoAlumno);
					krdxCursoImp.setFolio(folio);
					krdxCursoImp.setFCreada(fecha);
					krdxCursoImp.setCursoId(cursoId);
					krdxCursoImp.setCursoId2(cursoId);
					krdxCursoImp.setConvalidacion(convalidada);
					krdxCursoImp.setTipoCalId(condicion);
					krdxCursoImp.setNota(calificacion);
					krdxCursoImp.setCiclo(ciclo);
					krdxCursoImp.setNotaExtra(califExtr);
					krdxCursoImp.setFExtra(fechaExtra);
					krdxCursoImp.setObservaciones(observaciones);
					krdxCursoImp.setNotaConva(notaConva);
					krdxCursoImp.setTitulo(titulo);
					krdxCursoImp.setOptativa(optativa);
					krdxCursoImp.setOptativaNombre(optativaNombre);
					krdxCursoImp.setUsuario(codigoPersonal);
					krdxCursoImp.setFecha(aca.util.Fecha.getHoy());
					
					if(!krdxCursoImpDao.existeReg(codigoAlumno, folio)){
						krdxCursoImpDao.insertReg(krdxCursoImp);
					}					
				}
			}
		}
		
		return "redirect:/notas/importado/cursos?PlanId="+planId;
	}
	
	@RequestMapping("/notas/importado/borrar")
	public String notasImportadoBorrar(HttpServletRequest request){	
		String codigoAlumno		= "0"; 
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
        	codigoAlumno 		= sesion.getAttribute("codigoAlumno")==null?"0":(String)sesion.getAttribute("codigoAlumno");
		}
		String folio 			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		if (krdxCursoImpDao.existeReg(codigoAlumno, folio)) {
			krdxCursoImpDao.deleteReg(codigoAlumno, folio);
		}		
		return "redirect:/notas/importado/cursos";
	}
	
	@RequestMapping("/notas/importado/curso_update")
	public String notasImportadoCursosUpdate(HttpServletRequest request, Model modelo){		
		
		String codigoAlumno		= "0"; 
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
        	codigoAlumno 		= sesion.getAttribute("codigoAlumno")==null?"0":(String)sesion.getAttribute("codigoAlumno");
		}
		String folio 			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String planId			= alumPlanDao.getPlanActual(codigoAlumno);
		String nombreAlumno		= alumPersonalDao.getNombre(codigoAlumno, "NOMBRE");
		String carreraId		= alumPersonalDao.getCarreraId(planId);
		String nombreCarrera	= catCarreraDao.getNombreCarrera(carreraId);
		
		KrdxCursoImp krdxCursoImp = new KrdxCursoImp();
		if (krdxCursoImpDao.existeReg(codigoAlumno, folio)) {
			krdxCursoImp = krdxCursoImpDao.mapeaRegId(codigoAlumno,folio);
		}
		
		List<CatTipoCal> lisTipos	= catTipoCalDao.getListAll(" ORDER BY 1");
		List<CatGradePoint> lisGrades	= catGradePointDao.lisTodos(" ORDER BY GP_ID");	
		HashMap<String,String> mapaGradePoint	= alumnoCursoDao.mapaGradePoint(codigoAlumno);
		
		modelo.addAttribute("krdxCursoImp", krdxCursoImp);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("carreraId", carreraId);
		modelo.addAttribute("nombreCarrera", nombreCarrera);
		modelo.addAttribute("lisTipos", lisTipos);
		modelo.addAttribute("lisGrades", lisGrades);
		modelo.addAttribute("mapaGradePoint", mapaGradePoint);
		
		return "notas/importado/curso_update";
	}
	
	@RequestMapping("/notas/importado/modificar")
	public String notasImportadoModificar(HttpServletRequest request){
		String codigoAlumno			= "0";
		String codigoPersonal		= "0";
		HttpSession sesion			= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
        	codigoAlumno 			= sesion.getAttribute("codigoAlumno")==null?"0":(String)sesion.getAttribute("codigoAlumno");
        	codigoPersonal 			= sesion.getAttribute("codigoPersonal")==null?"0":(String)sesion.getAttribute("codigoPersonal");
		}
		String folio 				= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		KrdxCursoImp krdxCursoImp	= new KrdxCursoImp();
		String mensaje 				= "-";
		String fechaCreada 			= request.getParameter("fCreada")==null?aca.util.Fecha.getHoy():request.getParameter("fCreada");
		String fechaExtra 			= request.getParameter("fechaExtra")==null?"0":request.getParameter("fechaExtra");	
		String nota 				= request.getParameter("nota")==null?"0":request.getParameter("nota");
		
		//System.out.println("Datos:"+folio+":"+fechaCreada+":"+fechaExtra+"x");
		
		if(krdxCursoImpDao.existeReg(codigoAlumno, folio)==true){
			krdxCursoImp = krdxCursoImpDao.mapeaRegId(codigoAlumno, folio);
			
			krdxCursoImp.setFCreada(fechaCreada);
			if (fechaExtra!=null && !fechaExtra.equals("0")) krdxCursoImp.setFExtra(fechaExtra);
			
			String convalidacion 	= request.getParameter("convalidacion")==null?"N":request.getParameter("convalidacion");
			String titulo 			= request.getParameter("titulo")==null?"N":request.getParameter("titulo");			
			String optativa 		= request.getParameter("optativa")==null?"N":request.getParameter("optativa");
			String tipoCal 			= request.getParameter("tipoCalId")==null?"1":request.getParameter("tipoCalId");
			String grade			= request.getParameter("grade");
			
		 	if (!grade.equals("N") && nota.equals("")){
		 		nota = grade;
		 	}
			
			if ( convalidacion.equals("S") ){ 
				krdxCursoImp.setNotaConva(nota);
				krdxCursoImp.setNota("0");
			}else{
				krdxCursoImp.setNota(nota);
				krdxCursoImp.setNotaConva("0");				
			}
			
			krdxCursoImp.setConvalidacion(convalidacion);
			krdxCursoImp.setTitulo(titulo);
			krdxCursoImp.setOptativa(optativa);
			krdxCursoImp.setTipoCalId(tipoCal);
			
			krdxCursoImp.setOptativaNombre(request.getParameter("nombreOptativa")==null?"-":request.getParameter("nombreOptativa"));
			krdxCursoImp.setUsuario(codigoPersonal);
			krdxCursoImp.setFecha(aca.util.Fecha.getHoy());
			
			if(krdxCursoImpDao.updateReg(krdxCursoImp)){
				mensaje = "Updated";				
			}else{
				mensaje = "Error updating";				
			}		
		}		
		return "redirect:/notas/importado/curso_update?Folio="+folio+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/notas/importado/alumnos")
	public String admisionReporteAlumnos(HttpServletRequest request, Model modelo){	
		
		List<AlumPersonal> lisAlumnos		= alumPersonalDao.getListAll(" ORDER BY APELLIDO_PATERNO");
		List<MapaPlan> lisPlanes 			= mapaPlanDao.getListAll(" ORDER BY PLAN_ID");
		HashMap<String,String> mapaCursos 	= krdxCursoImpDao.mapaCursosPorAlumnoAndPlan();
		
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("mapaCursos", mapaCursos);
		
		return "notas/importado/alumnos";
	}	
}
