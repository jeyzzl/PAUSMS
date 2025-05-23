package aca.web.parametros;

import java.util.ArrayList;
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

import aca.alumno.spring.AlumColorDao;
import aca.parametros.spring.Parametros;
import aca.parametros.spring.ParametrosDao;
import aca.vista.spring.MaestrosDao;
import aca.vista.spring.Usuarios;
import aca.vista.spring.UsuariosDao;

@Controller
public class ContParametrosAlumnos {	
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	UsuariosDao usuariosDao;	
	
	@Autowired
	AlumColorDao alumColorDao;
	
	@Autowired
	MaestrosDao maestrosDao;
	
	
	@RequestMapping("/parametros/alumno/buscar")
	public String parametrosAlumnoBuscar(HttpServletRequest request, Model modelo){		
		
		List<Usuarios> lisUsuarios 	= new ArrayList<Usuarios>();
		Usuarios usuario 			= new Usuarios();
		String codigoPersonal 		= request.getParameter("CodigoPersonal")==null?"0":request.getParameter("CodigoPersonal");
		String accion				= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		String opcion				= request.getParameter("Opcion")==null?"Alumno":request.getParameter("Opcion");		
		String mensaje				= "<font size='2'>Elija la opción de consulta</font>";		
		int numAccion 				= Integer.parseInt(accion);		
		String nombreCompleto 		= "";		
		
		switch (numAccion){
			case 1:{
				nombreCompleto	= request.getParameter("NombreCompleto")==null ? "" : request.getParameter("NombreCompleto");
				if(!nombreCompleto.equals("")){
					nombreCompleto = nombreCompleto.replaceAll(" ","%");
					lisUsuarios = usuariosDao.getListAllFltro( nombreCompleto, opcion, " ORDER BY NOMBRE||APELLIDO_PATERNO||APELLIDO_MATERNO");
		
					//lisUsuarios = UsuariosU.getLista(conEnoc, sNombre, sPaterno, sMaterno, opcion,"ORDER BY 2,3,4");
					if (lisUsuarios.size() > 0){
						mensaje	= "<font size='2'>Clic sobre el nombre del "+opcion.toLowerCase()+" para guardarlo en sesión</font>";
					}
					else{
						mensaje = "<font size='2' color='#AE2113'>No hubo resultados</font>";
					}
				}else {
					System.out.println("Texto de busqueda vacio...");
				}
			break;
			} 
			case 2:{				
				if(usuariosDao.existeReg(codigoPersonal)){
					usuario 	= usuariosDao.mapeaRegId(request.getParameter("CodigoPersonal"));
					mensaje 	= "<font size='2'>Clic sobre el nombre del "+opcion+"</font>";
				}
				else{
					mensaje 	= "<font size='2' color='#AE2113'>No existe: "+codigoPersonal+"</font>";
				}
			break;
			}
			case 3:{
				HttpSession sesion 		= ((HttpServletRequest)request).getSession();
				if (sesion != null){
					if(maestrosDao.existeReg(codigoPersonal)){
						sesion.setAttribute("codigoEmpleado", request.getParameter("CodigoPersonal"));
						sesion.setAttribute("codigoUltimo", request.getParameter("CodigoPersonal"));
					}else{
						sesion.setAttribute("codigoAlumno", request.getParameter("CodigoPersonal"));
						sesion.setAttribute("colorPortal", alumColorDao.getColor(codigoPersonal));
						sesion.setAttribute("codigoUltimo", request.getParameter("CodigoPersonal"));
					}					
				}				
				mensaje = "<font size='2'>Registrado en tú sesión: "+request.getParameter("CodigoPersonal")+"</font>";				
			}
		}
		
		modelo.addAttribute("usuario", usuario);
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("lisUsuarios", lisUsuarios);
		
		return "parametros/alumno/buscar";
	}
	
}