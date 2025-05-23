package aca.web.admlinea;

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
import aca.alumno.spring.AlumRemedial;
import aca.alumno.spring.AlumRemedialDao;
import aca.plan.spring.MapaCurso;
import aca.plan.spring.MapaCursoDao;

@Controller
public class ContAmdlineaRemedial {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	private AlumRemedialDao alumRemedialDao;
	
	@Autowired
	private MapaCursoDao mapaCursoDao;

	@Autowired
	private AlumPersonalDao alumPersonalDao;
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/admlinea/remedial/lista")
	public String admlineaRemedialLista(HttpServletRequest request, Model modelo){
		
		List<String> lista 						= alumRemedialDao.listaMatriculas();
		
		HashMap<String, AlumPersonal> mapaAlumnosAlumnos 	= alumPersonalDao.mapaAlumnosAlumnos(" WHERE CODIGO_PERSONAL IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM ALUM_REMEDIAL)");
		HashMap<String, String> mapaRemedial 				= alumRemedialDao.mapaRemedial("");
		
		modelo.addAttribute("lista", lista);
		modelo.addAttribute("mapaAlumnosAlumnos", mapaAlumnosAlumnos);
		modelo.addAttribute("mapaRemedial", mapaRemedial);
		
		return "admlinea/remedial/lista";
	}
	
	@RequestMapping("/admlinea/remedial/borrar")
	public String admlineaRemedialBorrar(HttpServletRequest request, Model modelo){
		
		String codigoPersonal 	= request.getParameter("CodigoPersonal")==null?"0":request.getParameter("CodigoPersonal");
		
		alumRemedialDao.deleteReg(codigoPersonal);
		
		return "redirect:/admlinea/remedial/lista";
	}
	
	@RequestMapping("/admlinea/remedial/editar")
	public String admlineaRemedialEditar(HttpServletRequest request, Model modelo){
		
		String codigoPersonal 	= request.getParameter("CodigoPersonal")==null?"0":request.getParameter("CodigoPersonal");
		String mensaje			= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();		
		if (sesion != null){
			if(!codigoPersonal.equals("0")) {
				sesion.setAttribute("codigoAlumno",codigoPersonal);
			}
			codigoPersonal 		= sesion.getAttribute("codigoAlumno").toString();
		}	
		
		AlumPersonal alumPersonal = new AlumPersonal();
		
		if(alumPersonalDao.existeAlumno(codigoPersonal)) {
			alumPersonal = alumPersonalDao.mapeaRegId(codigoPersonal);
		}
		
		List<MapaCurso> listaCursos =  mapaCursoDao.getListAll(" WHERE PLAN_ID = 'REMPOD20'");
		HashMap<String, String> mapaRemedial = alumRemedialDao.mapaRemedial(" WHERE CODIGO_PERSONAL = "+codigoPersonal);
		
		modelo.addAttribute("listaCursos", listaCursos);
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("mapaRemedial", mapaRemedial);
		
		return "admlinea/remedial/editar";
	}
	
	@RequestMapping("/admlinea/remedial/grabar")
	public String admlineaRemedialGrabar(HttpServletRequest request){
		
		String codigoPersonal	= request.getParameter("CodigoPersonal")==null?"0":request.getParameter("CodigoPersonal");
		String mensaje			= "1";
		
		List<MapaCurso> listaCursos =  mapaCursoDao.getListAll(" WHERE PLAN_ID = 'REMPOD20'");
		
		AlumRemedial remedial = new AlumRemedial();
		alumRemedialDao.updateEstadoReg(codigoPersonal,"I");
		
		if(alumPersonalDao.existeAlumno(codigoPersonal)) {
			for(MapaCurso curso : listaCursos) {
				String cursoId	= request.getParameter("CursoId"+curso.getCursoId())==null?"0":request.getParameter("CursoId"+curso.getCursoId());
				if(!cursoId.equals("0")) {
					if(!alumRemedialDao.existeReg(codigoPersonal, cursoId)) {
						remedial.setCodigoPersonal(codigoPersonal);
						remedial.setCursoId(cursoId);
						remedial.setEstado("A");
						if(alumRemedialDao.insertReg(remedial)) {
							mensaje = "1";
						}else {
							mensaje = "2";
						}
					}else {
						if(alumRemedialDao.updateReg(codigoPersonal,curso.getCursoId())){
							mensaje = "1";
						}else {
							mensaje = "2";
						}
					}
				}
			}
		}else {
			mensaje = "3";
		}
		
		return "redirect:/admlinea/remedial/editar?Mensaje="+mensaje;
	}
}
