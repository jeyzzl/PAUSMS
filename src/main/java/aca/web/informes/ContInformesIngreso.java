package aca.web.informes;

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

import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.financiero.spring.FesCcobroDao;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContInformesIngreso {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired	
	private MaestrosDao maestrosDao;
	
	@Autowired	
	private CatFacultadDao catFacultadDao;
	
	@Autowired	
	private FesCcobroDao fesCcobroDao;	
	
	@Autowired	
	private CatCarreraDao catCarreraDao;
	
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/informes/ingreso/cohorte")
	public String informesIngresoCohorte(HttpServletRequest request, Model modelo){
		
		String facultad 			= request.getParameter("facultad")==null?"0":request.getParameter("facultad");
		String cargaId      		= request.getParameter("CargaId")==null ? "000000" : request.getParameter("CargaId");
		String facultadNombre		= catFacultadDao.getNombreFacultad(facultad);
		List<String> lisCargas 		= fesCcobroDao.lisCargasIngreso(facultad, " ORDER BY 1 DESC");
		
		// Coloca por default la primera cargaId
		if (cargaId.equals("000000")&&lisCargas.size()>0) cargaId= lisCargas.get(0);
		
		List<String> lisCarreras	= fesCcobroDao.lisCarrerasPorCarga( cargaId, facultad, " ORDER BY 1");
		List<aca.Mapa> lisPlanes	= fesCcobroDao.lisPlanPorCarga( cargaId, facultad, " ORDER BY 1");
		
		/* HashMap de los nombres de las carreras */
		HashMap<String,CatCarrera> mapCarrera 	= catCarreraDao.getMapAll("");
		
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("facultadNombre", facultadNombre);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisCarreras", lisCarreras);
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("mapCarrera", mapCarrera);
		
		return "informes/ingreso/cohorte";
	}	
	
	@RequestMapping("/informes/ingreso/facultad")
	public String informesIngresoFacultad(HttpServletRequest request, Model modelo){
		
		List<CatFacultad> lisFacultades				= catFacultadDao.getListAll("ORDER BY 1");
		HashMap<String,String> mapaDirectores		= maestrosDao.mapaDirectores();
		
		modelo.addAttribute("lisFacultades", lisFacultades);
		modelo.addAttribute("mapaDirectores", mapaDirectores);
		
		return "informes/ingreso/facultad";
	}		
	
	@RequestMapping("/informes/ingreso/promedio")
	public String informesIngresoPromedio(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContInformesIngreso|Promedio:");
		return "informes/ingreso/promedio";
	}		

}