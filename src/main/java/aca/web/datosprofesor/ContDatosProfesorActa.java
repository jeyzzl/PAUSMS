package aca.web.datosprofesor;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.carga.spring.CargaGrupoImp;
import aca.carga.spring.CargaGrupoImpDao;
import aca.plan.spring.MapaCurso;
import aca.plan.spring.MapaCursoDao;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContDatosProfesorActa {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	private CargaGrupoImpDao cargaGrupoImpDao;
	
	@Autowired
	private MaestrosDao maestrosDao;
	
	@Autowired
	private MapaCursoDao mapaCursoDao;
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/datos_profesor/acta/acta")
	public String datosProfesorActaActa(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContDatosProfesorActa|acta:");
		return "datos_profesor/acta/acta";
	}	
	
	@RequestMapping("/datos_profesor/acta/lista")
	public String datosProfesorActaLista(HttpServletRequest request, Model modelo){
		List<CargaGrupoImp> lisGrupos 				= cargaGrupoImpDao.getListPlan("TEOL11CL", "ORDER BY GRUPO, ENOC.CURSO_CICLO(CURSO_ID)");
		HashMap<String,String> mapaMaestros 		= maestrosDao.mapMaestroNombre("NOMBRE");
		HashMap<String,MapaCurso> mapaCursos 		= mapaCursoDao.mapaCursosEnGruposImp();
		
		modelo.addAttribute("lisGrupos", lisGrupos);
		modelo.addAttribute("mapaMaestros", mapaMaestros);
		modelo.addAttribute("mapaCursos", mapaCursos);
		
		return "datos_profesor/acta/lista";
	}	
	
}