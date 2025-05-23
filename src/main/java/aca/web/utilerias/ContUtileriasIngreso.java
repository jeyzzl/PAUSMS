package aca.web.utilerias;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import aca.alumno.spring.AlumPlanDao;
import aca.mov.MovAcademico;

@Controller
public class ContUtileriasIngreso {
	
	@Autowired	
	private AlumPlanDao alumPlanDao;	
	
	
	@RequestMapping("utilerias/ingreso/actualizarFechas")
	public String utileriasIngresoActualizarFechas(HttpServletRequest request){		
		return "utilerias/ingreso/actualizarFechas";
	}
	
	@RequestMapping("utilerias/ingreso/actualizar")
	public String utileriasIngresoActualizar(HttpServletRequest request, Model modelo){
		
		int rows = alumPlanDao.updatePrimerMatricula();
		modelo.addAttribute("rows",rows);
		
		return "utilerias/ingreso/actualizar";
	}
	
	@RequestMapping("utilerias/ingreso/prueba")
	@ResponseBody
	public String utileriasIngresoPrueba(HttpServletRequest request, Model modelo){
		String token = "X";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);		  
		String ruta = "http://localhost:18083/serweb/login?user=sys&password=admin";
		RestTemplate rest = new RestTemplate();
		token = rest.getForObject(ruta,String.class);
		return token;
	}
	
	@RequestMapping("utilerias/ingreso/consulta")
	@ResponseBody
	public MovAcademico utileriasIngresoConsulta(HttpServletRequest request, Model modelo){
		
		String token = "X";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);		  
		String ruta = "http://localhost:18083/serweb/login?user=sys&password=admin";		
		RestTemplate rest = new RestTemplate();
		token = rest.getForObject(ruta,String.class);		
		System.out.println(token);
		HttpHeaders headers2 = new HttpHeaders();
		headers2.setContentType(MediaType.APPLICATION_JSON);
		headers2.add("Authorization", token);
		RestTemplate restTemplate = new RestTemplate();
		String ruta2 = "http://localhost:18083/serweb/academico?CodigoAlumno=1050355";
		HttpEntity<String> requestEntity = new HttpEntity<String>(headers2);
	    ResponseEntity<MovAcademico> responseEntity = restTemplate.exchange(ruta2, HttpMethod.GET, requestEntity, MovAcademico.class);
	    MovAcademico academico = responseEntity.getBody();
	    
		return academico;
	}	
}