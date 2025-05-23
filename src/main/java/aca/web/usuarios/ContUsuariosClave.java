package aca.web.usuarios;

import java.math.BigInteger;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoClave;
import aca.acceso.spring.AccesoClaveDao;
import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumPlanDao;
import aca.catalogo.spring.CatCarreraDao;
import aca.conecta.Conectar;
import aca.radius.Radcheck;
import aca.radius.Radreply;
import aca.vista.spring.Usuarios;
import aca.vista.spring.UsuariosDao;

@Controller
public class ContUsuariosClave {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	private UsuariosDao usuariosDao;
	
	@Autowired
	private AccesoDao accesoDao;
	
	@Autowired
	private AlumPlanDao alumPlanDao;
	
	@Autowired
	private CatCarreraDao catCarreraDao;
	
	@Autowired
	private AccesoClaveDao accesoClaveDao;
	
	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;	
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/usuarios/clave/usuario")
	public String usuariosClaveUsuario(HttpServletRequest request, Model modelo){
		String codigoUsuario	= "";
		String codigoPersonal	= "";
		String respuesta		= request.getParameter("Res")==null?"":request.getParameter("Res");
		String nombreCarrera	= "";
		boolean alumnoCimum		= false;
		
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			codigoUsuario	= (String)sesion.getAttribute("codigoPersonal");
			codigoPersonal 	= (String) sesion.getAttribute("codigoUltimo");
		}	
		
		if (alumPlanDao.existeReg(codigoPersonal, "CONS2018") || alumPlanDao.existeReg(codigoPersonal, "CONS2019")) alumnoCimum = true;
		
		Usuarios usuarioDatos 	= new Usuarios();
		Acceso acceso 			= new Acceso();
		
		if(usuariosDao.existeReg(codigoPersonal)) {
			usuarioDatos = usuariosDao.mapeaRegId(codigoPersonal);
		}
		
		if(accesoDao.existeReg(codigoPersonal)) {
			acceso = accesoDao.mapeaRegId(codigoPersonal);
		}
		
		nombreCarrera = catCarreraDao.getNombreCarrera(alumPlanDao.getCarreraId(codigoPersonal));
		
		modelo.addAttribute("codigoUsuario", codigoUsuario);
		modelo.addAttribute("codigoPersonal", codigoPersonal);
		modelo.addAttribute("alumnoCimum", alumnoCimum);
		modelo.addAttribute("usuarioDatos", usuarioDatos);
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("nombreCarrera", nombreCarrera);
		modelo.addAttribute("respuesta", respuesta);		
		
		return "usuarios/clave/usuario";
	}

	@RequestMapping("/usuarios/clave/grabar")
	public String usuariosClaveGrabar(HttpServletRequest request){
		
		String claveOriginal 	= request.getParameter("clave")==null?"-":request.getParameter("clave");
		String clave 			= aca.util.Encriptar.sha512ConBase64(claveOriginal);
		String claveHexa		= bCryptPasswordEncoder.encode(claveOriginal);
		String cuenta 			= request.getParameter("Cuenta")==null?"":request.getParameter("Cuenta");
		String codigoPersonal	= "";
		String codigoUsuario	= "";
		String respuesta		= "";
		
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			codigoPersonal 	= (String) sesion.getAttribute("codigoUltimo");
			codigoUsuario	= (String)sesion.getAttribute("codigoPersonal");
		}
		
		Acceso acceso 		= new Acceso();	
		AccesoClave aClave 	= new AccesoClave();
		
		aClave.setCodigoPersonal(codigoPersonal);
		aClave.setFecha(aca.util.Fecha.getHoy());
		aClave.setIp(request.getRemoteAddr());
		aClave.setClave(clave);
		aClave.setFolio(1);
		
		if(accesoDao.existeReg(codigoPersonal)) {
			acceso = accesoDao.mapeaRegId(codigoPersonal);
		}
			
   		if (codigoUsuario.equals("9800308")){
   			acceso.setUsuario(cuenta);
   		}	   		
   		acceso.setClave(clave);
   		if(accesoDao.existeReg(acceso.getCodigoPersonal())){	   			
   			if(accesoDao.updateClave(acceso.getUsuario(), clave ,acceso.getCodigoPersonal())){
	   			respuesta = "1";
	   			if(!accesoClaveDao.existeReg(codigoPersonal, 1)) {
					accesoClaveDao.insertReg(aClave);		
				}else {
					accesoClaveDao.updateReg(aClave);
				}
	   			accesoDao.updateClaveHexa(acceso.getCodigoPersonal(), claveHexa);
	   		}else{
	   			respuesta = "2";
	   		}
   		}else{	
   			acceso.setClaveInicial(claveOriginal);
   			if(accesoDao.insertReg(acceso)){
	   			respuesta = "3";
	   			if(!accesoClaveDao.existeReg(codigoPersonal, 1)) {
					accesoClaveDao.insertReg(aClave);		
				}else {
					accesoClaveDao.updateReg(aClave);
				}
	   		}else{
	   			respuesta = "2";
	   		}
   		}
	   		
		return "redirect:/usuarios/clave/usuario?Res="+respuesta;
	}
	
}