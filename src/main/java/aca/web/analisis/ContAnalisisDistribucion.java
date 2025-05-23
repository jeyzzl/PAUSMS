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

import aca.est.spring.EstMaestro;

@Controller
public class ContAnalisisDistribucion {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	
	
	@Autowired
	private aca.vista.spring.UsuariosDao usuariosDao;
	
	@Autowired
	private aca.vista.spring.MaestrosDao maestrosDao;
		
	@Autowired
	private aca.est.spring.EstMaestroDao estMaestroDao;	
	
	@Autowired
	private aca.est.spring.EstCcostoDao estCcostoDao;
	
	@Autowired
	private aca.financiero.spring.ContCcostoDao contCcostoDao;
	
	@RequestMapping("/analisis/distribucion/maestros")
	public String analisisDistribucionMaestros(HttpServletRequest request, Model modelo){
	
		List<EstMaestro> lisMaestros 			= estMaestroDao.lisAll("");
		List<String> lisDepartamentos 			= estCcostoDao.listDepartamentos(" ORDER BY CCOSTO_ID");
		HashMap<String,String> mapaMaestros 	= maestrosDao.mapMaestroNombre("NOMBRE");
		HashMap<String,String> mapaMaterias 	= estCcostoDao.mapaMateriasPorMaestro();
		HashMap<String,String> mapaAlumnos	 	= estCcostoDao.mapaAlumPorMaestro();
		HashMap<String,String> mapaRango1	 	= estCcostoDao.mapaMatPorRango(1, 1);
		HashMap<String,String> mapaRango2	 	= estCcostoDao.mapaMatPorRango(2, 9);
		HashMap<String,String> mapaPorcentaje 	= estCcostoDao.mapaPorTotDepto();
		HashMap<String,String> mapaIniciales 	= contCcostoDao.mapaDeptoIniciales(aca.util.Fecha.getEjercicioHoy());
		
		modelo.addAttribute("lisMaestros", lisMaestros);
		modelo.addAttribute("lisDepartamentos", lisDepartamentos);
		modelo.addAttribute("mapaMaestros", mapaMaestros);
		modelo.addAttribute("mapaMaterias", mapaMaterias);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("mapaRango1", mapaRango1);
		modelo.addAttribute("mapaRango2", mapaRango2);
		modelo.addAttribute("mapaPorcentaje", mapaPorcentaje);
		modelo.addAttribute("mapaIniciales", mapaIniciales);
		
		return "analisis/distribucion/maestros";
	}
	
}
