package aca.web.datosprofesor;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.por.spring.PorHora;
import aca.por.spring.PorHoraDao;
import aca.por.spring.PorRegistro;
import aca.por.spring.PorRegistroDao;
import aca.por.spring.PorSalonDao;
import aca.vista.spring.MaestrosDao;
import aca.vista.spring.UsuariosDao;

@Controller
public class ContDatosProfesorPortafolio {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	private PorRegistroDao porRegistroDao;
	
	@Autowired
	private PorHoraDao porHoraDao;
	
	@Autowired
	private UsuariosDao usuariosDao;
	
	@Autowired
	private MaestrosDao maestrosDao;
	
	@Autowired
	private PorSalonDao porSalonDao;	
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/datos_profesor/portafolio/registro")
	public String datosProfesorPortafolioRegistro(HttpServletRequest request, Model modelo){
		String codigoEmpleado		= "0";		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoEmpleado		= (String) sesion.getAttribute("codigoEmpleado");			
		}
		List<PorRegistro> lisEquipos = porRegistroDao.getListAll(" ORDER BY EQUIPO_ID");
		HashMap<String,String> mapMaestros 	= maestrosDao.mapMaestroNombre("NOMBRE");
		HashMap<String,PorHora> mapaHoras 	= porHoraDao.mapaHora();
		HashMap<String,String> mapaSalones 	= porSalonDao.mapaAll();
		
		
		String nombreEmpleado = usuariosDao.getNombreUsuario(codigoEmpleado,"NOMBRE");
		
		modelo.addAttribute("lisEquipos",lisEquipos);
		modelo.addAttribute("nombreEmpleado",nombreEmpleado);
		modelo.addAttribute("mapMaestros",mapMaestros);
		modelo.addAttribute("mapaHoras",mapaHoras);
		modelo.addAttribute("mapaSalones",mapaSalones);
		
		return "datos_profesor/portafolio/registro";
	}
	
	@RequestMapping("/datos_profesor/portafolio/grabar")
	public String datosProfesorPortafolioGrabar(HttpServletRequest request, Model modelo){
		
		String codigoEmpleado	= "-";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoEmpleado		= (String) sesion.getAttribute("codigoEmpleado");
		}
		String codigoPersonal	= request.getParameter("CodigoPersonal")==null?"0":request.getParameter("CodigoPersonal");
		if (!codigoPersonal.equals("0")){
			codigoEmpleado = codigoPersonal;
		}
		String equipoId			= request.getParameter("EquipoId")==null?"0":request.getParameter("EquipoId");
		String estado			= request.getParameter("Estado")==null?"A":request.getParameter("Estado");
		
		PorRegistro registro = new PorRegistro();
		
		registro.setCodigoPersonal(codigoEmpleado);
		registro.setEquipoId(equipoId);
		registro.setEstado(estado);
		
		if (porRegistroDao.existeReg(codigoEmpleado)){
			porRegistroDao.updateReg(registro);
		}else{
			porRegistroDao.insertReg(registro);
		}		
		
		return "redirect:/datos_profesor/portafolio/registro";
	}	
	
	@RequestMapping("/datos_profesor/portafolio/borrar")
	public String datosProfesorPortafolioBorrar(HttpServletRequest request, Model modelo){	
		String codigoPersonal			= request.getParameter("CodigoPersonal")==null?"0":request.getParameter("CodigoPersonal");
		
		if (porRegistroDao.existeReg(codigoPersonal)){
			porRegistroDao.deleteReg(codigoPersonal);
		}		
		
		return "redirect:/datos_profesor/portafolio/registro";
	}
	
}