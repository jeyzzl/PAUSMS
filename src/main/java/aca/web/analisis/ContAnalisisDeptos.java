package aca.web.analisis;

import java.util.HashMap;

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
public class ContAnalisisDeptos {

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
	private aca.financiero.spring.ContCcostoDao contCcostoDao;
	
	@Autowired
	private aca.est.spring.EstMaestroDao estMaestroDao;
	
	
	@RequestMapping("/analisis/deptos/deptos")
	public String analisisDepartamentosDepartamentos(HttpServletRequest request, Model modelo){	
		
		modelo.addAttribute("mapaDeptos", contCcostoDao.mapaCcosto("'S'"));
		modelo.addAttribute("listDepartamentos", estCcostoDao.listDepartamentos(""));
		modelo.addAttribute("mapaTotalDepartamento", estCcostoDao.mapaTotalDepartamento());		
		return "analisis/deptos/deptos";
	}

	@RequestMapping("/analisis/deptos/infoDepto") 
	public String analisisDepartamentosInfoDepartamento(HttpServletRequest request, Model modelo){
		String costoId	= request.getParameter("CcostoId")==null?"0":request.getParameter("CcostoId");	
		String cargas	= request.getParameter("Cargas")==null?"'18194A','18191A','18195A','18193A'":request.getParameter("Cargas");
		
		HashMap<String,String> mapaDeptos 			= contCcostoDao.mapaCcosto("'S'");
		HashMap<String,EstMaestro> mapaEstMaestro 	= estMaestroDao.mapaTodos();
		HashMap<String,String> mapaPorMaestro	 	= estCcostoDao.mapaPorMaestro(costoId);
		
		String nombreDepto = "-";
		if (mapaDeptos.containsKey(costoId)){
			nombreDepto = mapaDeptos.get(costoId);
		}
		
		modelo.addAttribute("mapaMaestros", maestrosDao.mapMaestroAnalisis(cargas, "APELLIDOS"));
		modelo.addAttribute("mapaDeptos", mapaDeptos);
		modelo.addAttribute("listMaestrosDepto", estCcostoDao.listMaestrosDepto(costoId, " ORDER BY EMP_APELLIDO(CODIGO_PERSONAL)"));
		modelo.addAttribute("mapaImporte", estCcostoDao.mapaImporte(costoId));		
		modelo.addAttribute("nombreDepto", nombreDepto);
		modelo.addAttribute("mapaEstMaestro", mapaEstMaestro);
		modelo.addAttribute("mapaPorMaestro", mapaPorMaestro);
		
		return "analisis/deptos/infoDepto";
	}
	
}
