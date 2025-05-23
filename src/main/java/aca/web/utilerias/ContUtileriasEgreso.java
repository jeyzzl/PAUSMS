package aca.web.utilerias;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContUtileriasEgreso {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("utilerias/egreso/actualizar")
	public String utileriasEgresoActualizar(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContUtileriasEgreso|utileriasEgresoActualizar:");
		return "utilerias/egreso/actualizar";
	}
	
	@RequestMapping("utilerias/egreso/actualizarFechas")
	public String utileriasEgresoActualizarFechas(HttpServletRequest request){
		return "utilerias/egreso/actualizarFechas";
	}
	
	@RequestMapping("utilerias/egreso/hospital/inscritos")
	public String utileriasEgresoHospitalInscritos(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContUtileriasEgreso|utileriasEgresoHospitalInscritos");
		return "utilerias/egreso/hospital/inscritos";
	}
	
}