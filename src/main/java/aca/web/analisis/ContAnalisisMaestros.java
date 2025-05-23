package aca.web.analisis;

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

import aca.est.spring.EstCcosto;
import aca.est.spring.EstMaestro;
import aca.plan.spring.MapaCurso;

@Controller
public class ContAnalisisMaestros {

	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	private aca.est.spring.EstCcostoDao estCcostoDao;
	
	@Autowired
	private aca.vista.spring.MaestrosDao maestrosDao;
	
	@Autowired
	private aca.plan.spring.MapaCursoDao mapaCursoDao;
	
	@Autowired
	private aca.financiero.spring.ContCcostoDao contCcostoDao;
	
	@Autowired
	private aca.est.spring.EstMaestroDao estMaestroDao;	
	
	
	@RequestMapping("/analisis/maestros/maestros")
	public String analisisMaestrosMaestros(HttpServletRequest request, Model modelo){
		String facultad			= request.getParameter("Facultad")==null?"1":request.getParameter("Facultad");
		String estado			= request.getParameter("Estado")==null?"1":request.getParameter("Estado");
		String cargas			= request.getParameter("Cargas")==null?"'18194A','18191A','18195A','18193A'":request.getParameter("Cargas");
		
		List<EstCcosto> lisMaestros 				= estCcostoDao.listFacultad(facultad, estado, "ORDER BY CODIGO_PERSONAL,CURSO_CARGA_ID, CCOSTO_ID");
		HashMap<String,String> mapaMaestros 		= maestrosDao.mapMaestroAnalisis(cargas, "NOMBRE");
		HashMap<String,MapaCurso> mapaCursos		= mapaCursoDao.mapCursosAnalisis();
		HashMap<String,String> mapaDeptos 			= contCcostoDao.mapaCcosto("'S'");
		HashMap<String,EstCcosto> mapaEstCcosto		= estCcostoDao.mapaTodos();
		HashMap<String,EstMaestro> mapaEstMaestro	= estMaestroDao.mapaTodos();
		
		modelo.addAttribute("lisMaestros", lisMaestros);
		modelo.addAttribute("mapaMaestros", mapaMaestros);
		modelo.addAttribute("mapaCursos", mapaCursos);
		modelo.addAttribute("mapaDeptos", mapaDeptos);
		modelo.addAttribute("mapaEstCcosto", mapaEstCcosto);
		modelo.addAttribute("mapaEstMaestro", mapaEstMaestro);
		
		return "analisis/maestros/maestros";
	}
	
	@RequestMapping("/analisis/maestros/editar")
	public String analisisMaestrosEditar(HttpServletRequest request, Model modelo){
		String id			= request.getParameter("Id")==null?"1":request.getParameter("Id");		
		
		EstCcosto costo = new EstCcosto();
		if (estCcostoDao.existeReg(id)) {
			costo = estCcostoDao.mapeaRegId(id);
		}
		
		modelo.addAttribute("costo", costo);
		
		return "analisis/maestros/editar";
	}
	
	@RequestMapping("/analisis/maestros/grabar")
	public String analisisMaestrosGrabar(HttpServletRequest request, Model modelo){
		String facultad		= request.getParameter("Facultad")==null?"1":request.getParameter("Facultad");
		String id			= request.getParameter("Id")==null?"1":request.getParameter("Id");
		
		EstCcosto costo = new EstCcosto();
		if (estCcostoDao.existeReg(id)) {
			costo = estCcostoDao.mapeaRegId(id);
			costo.setUbicacion(request.getParameter("Facultad"));
			costo.setCodigoPersonal(request.getParameter("CodigoPersonal"));
			costo.setHoras(request.getParameter("Horas"));
			costo.setTotal(request.getParameter("Total"));
			costo.setAlumnos(request.getParameter("Alumnos"));
			costo.setPorcentaje(request.getParameter("Porcentaje"));
			estCcostoDao.updateReg(costo);
		}
		
		modelo.addAttribute("costo", costo);
		
		return "redirect:/analisis/maestros/editar?Id="+id+"&Facultad="+facultad;
	}
	
	@RequestMapping("/analisis/maestros/estado")
	public String analisisMaestrosEstado(HttpServletRequest request, Model modelo){
		String facultad		= request.getParameter("Facultad")==null?"1":request.getParameter("Facultad");
		String id			= request.getParameter("Id")==null?"1":request.getParameter("Id");	
		String estado		= request.getParameter("Estado")==null?"1":request.getParameter("Estado");
		
		EstCcosto costo = new EstCcosto();
		if (estCcostoDao.existeReg(id)) {
			costo = estCcostoDao.mapeaRegId(id);			
			costo.setEstado(estado);
			estCcostoDao.updateReg(costo);
		}
		
		modelo.addAttribute("costo", costo);
		
		return "redirect:/analisis/maestros/maestros?Facultad="+facultad+"&Estado="+estado;
	}
	
	@RequestMapping("/analisis/maestros/actualizar")
	public String analisisMaestrosActualizar(HttpServletRequest request, Model modelo){
		String facultad		= request.getParameter("Facultad")==null?"1":request.getParameter("Facultad");			
		String estado		= request.getParameter("Estado")==null?"1":request.getParameter("Estado");
		
		List<EstCcosto> lisMaestros 			= estCcostoDao.listFacultad(estado, "ORDER BY CODIGO_PERSONAL,CURSO_CARGA_ID");

		EstMaestro maestro = new EstMaestro();
		String codigo 	= "0";
		String curso	= "0";
		int horas 		= 0;
		for (EstCcosto costo : lisMaestros) {
			
			if (!codigo.equals(costo.getCodigoPersonal()) && !codigo.equals("0")){
				
				// Modificar el total de horas del empleado
				if (estMaestroDao.existeReg(codigo)){
					maestro = estMaestroDao.mapeaRegId(codigo);
					maestro.setHoras(String.valueOf(horas));
					estMaestroDao.updateReg(maestro);			
				}
				horas 	= Integer.valueOf(costo.getHoras());
				codigo 	= costo.getCodigoPersonal();
				curso	= costo.getCursoCargaId();
			}else {
				codigo 	= costo.getCodigoPersonal();
				
				if (!curso.equals(costo.getCursoCargaId())) {
					
					horas += Integer.valueOf(costo.getHoras());
					curso = costo.getCursoCargaId();
				}
			}
		}
		
		// Actualizar el ultimo empleado
		if (estMaestroDao.existeReg(codigo)){
			maestro = estMaestroDao.mapeaRegId(codigo);
			maestro.setHoras(String.valueOf(horas));
			estMaestroDao.updateReg(maestro);
		}
		
		// Actualizar los porcentajes
		estCcostoDao.updatePorTotal();
		
		// Actualizar los importes
		estCcostoDao.updateImporte();
		
		return "redirect:/analisis/maestros/maestros?Facultad="+facultad+"&Estado="+estado;
	}
	
}
