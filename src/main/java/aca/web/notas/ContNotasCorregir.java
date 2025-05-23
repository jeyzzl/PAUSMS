package aca.web.notas;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanDao;
import aca.catalogo.spring.CatTipoCal;
import aca.catalogo.spring.CatTipoCalDao;
import aca.kardex.spring.KrdxCursoAct;
import aca.kardex.spring.KrdxCursoActDao;
import aca.log.spring.LogOperacion;
import aca.log.spring.LogOperacionDao;
import aca.vista.spring.AlumnoCurso;
import aca.vista.spring.AlumnoCursoDao;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContNotasCorregir {

	@Autowired
	private KrdxCursoActDao krdxCursoActDao;
	
	@Autowired
	private AlumPlanDao alumPlanDao;
	
	@Autowired
	private AlumnoCursoDao alumnoCursoDao;
	
	@Autowired
	private MaestrosDao maestrosDao;
	
	@Autowired
	private CatTipoCalDao catTipoCalDao;
	
	@Autowired
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired
	private LogOperacionDao logOperacionDao;	
	
	@RequestMapping("/notas/corregir/notas")
	public String notasCorregirNotas(HttpServletRequest request, Model modelo){		
	
		String codigoAlumno 	= "0";
		String alumnoNombre 	= "-";
		String planActual		= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoAlumno		= (String)sesion.getAttribute("codigoAlumno");
        	alumnoNombre 		= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
        	if (planActual.equals("0")) planActual = alumPlanDao.getPlanActual(codigoAlumno);
        }	
		
        List<AlumPlan> lisPlanes					= alumPlanDao.getLista(codigoAlumno, " ORDER BY PLAN_ID");
		List<AlumnoCurso> lisCursos		 			= alumnoCursoDao.getListAlumno(codigoAlumno," AND PLAN_ID = '"+planActual+"' AND ESTADO != 'I' ORDER BY CICLO, NOMBRE_CURSO");
		HashMap<String,String> mapaMaestros 		= maestrosDao.mapMaestroNombre("NOMBRE");
		HashMap<String,CatTipoCal> mapaTipos 		= catTipoCalDao.getMapAll("");
		HashMap<String,String> mapaPromedios 		= krdxCursoActDao.mapaPromedioMaterias(codigoAlumno);
		
		modelo.addAttribute("alumnoNombre", alumnoNombre);
		modelo.addAttribute("planActual", planActual);
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("lisCursos", lisCursos);
		modelo.addAttribute("mapaMaestros", mapaMaestros);
		modelo.addAttribute("mapaTipos", mapaTipos);
		modelo.addAttribute("mapaPromedios", mapaPromedios);
		
		return "notas/corregir/notas";
	}
	
	@RequestMapping("/notas/corregir/editar")
	public String notasCorregirEditar(HttpServletRequest request, Model modelo){
		
		String cursoCargaId		= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		String codigoAlumno 	= "0";
		String alumnoNombre 	= "-";
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoAlumno		= (String)sesion.getAttribute("codigoAlumno");
        	alumnoNombre 		= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
        }
        
        KrdxCursoAct kardex		= new KrdxCursoAct();
        if (krdxCursoActDao.existeReg(codigoAlumno, cursoCargaId)){
        	kardex 				= krdxCursoActDao.mapeaRegId(codigoAlumno, cursoCargaId); 
        }
		
        modelo.addAttribute("alumnoNombre", alumnoNombre);
		modelo.addAttribute("kardex", kardex);
		
		return "notas/corregir/editar";
	}
	
	@RequestMapping("/notas/corregir/grabar")
	public String notasCorregirGrabar(HttpServletRequest request, Model modelo){
		
		String cursoCargaId		= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		String materia			= request.getParameter("Materia")==null?"0":request.getParameter("Materia");
		String planId			= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String codigoPersonal	= "0";
		String codigoAlumno 	= "0";
		String mensaje 			= "-";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoPersonal		= (String)sesion.getAttribute("codigoPersonal");        	
        	codigoAlumno		= (String)sesion.getAttribute("codigoAlumno");
        }
        
        KrdxCursoAct kardex		= new KrdxCursoAct();
        LogOperacion log		= new LogOperacion();
        if (krdxCursoActDao.existeReg(codigoAlumno, cursoCargaId)){
        	kardex 				= krdxCursoActDao.mapeaRegId(codigoAlumno, cursoCargaId); 
        	String tipoNota	 	= kardex.getTipoCalId();
        	
        	String datos = "CursoCargaId:"+cursoCargaId+", CodigoAlumno:"+codigoAlumno+",Nota:"+kardex.getNota()+", Extra: "+kardex.getNotaExtra()+" NewNota:"+request.getParameter("nota")+" NewExtra:"+request.getParameter("notaExtra");
			log.setDatos(datos);
			log.setIp(request.getRemoteAddr());
			log.setUsuario(codigoPersonal);
			log.setTabla("KRDX_CURSO_ACT");
			
			String nota 		= request.getParameter("nota")==null?"0":request.getParameter("nota");
			String fechaNota	= request.getParameter("fechaNota")==null?"01/01/2000":request.getParameter("fechaNota");
			String notaExtra	= request.getParameter("notaExtra")==null?"0":request.getParameter("notaExtra");
			String fechaExtra	= request.getParameter("fechaExtra")==null?"01/01/2000":request.getParameter("fechaExtra");
			String tipoCal		= request.getParameter("tipoCal")==null?"0":request.getParameter("tipoCal");
			String comentario	= request.getParameter("comentario")==null?" ":request.getParameter("comentario");
			
			kardex.setNota(nota);
			kardex.setfNota(fechaNota);
			kardex.setNotaExtra(notaExtra);
			kardex.setfExtra(fechaExtra);
			kardex.setTipoCalId(tipoCal);
			kardex.setComentario(comentario);
			kardex.setCorreccion("S");		
			if (!tipoNota.equals("3")){
				if (krdxCursoActDao.updateReg(kardex)){	
					mensaje = "Nota Modificada: "+kardex.getCodigoPersonal()+"-"+kardex.getCursoCargaId();
					log.setOperacion("update");
					logOperacionDao.insertReg(log);
				}else{
					mensaje = "No Grabó: "+kardex.getCodigoPersonal()+"-"+kardex.getCursoCargaId();
				}
			}else{
				mensaje = "No es posible cambiar la nota de una materia dada de baja:"+kardex.getCodigoPersonal()+"-"+kardex.getCursoCargaId();
			}	
        }else{
			mensaje = "No existe el registro: "+codigoAlumno+"-"+cursoCargaId;
		}		
        
		return "redirect:/notas/corregir/editar?CursoCargaId="+cursoCargaId+"&Materia="+materia+"&PlanId="+planId+"&Mensaje="+mensaje;
	}

}
