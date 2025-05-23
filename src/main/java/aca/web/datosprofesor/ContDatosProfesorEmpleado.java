package aca.web.datosprofesor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.emp.spring.EmpDatos;
import aca.emp.spring.EmpDatosDao;
import aca.emp.spring.Empleado;
import aca.emp.spring.EmpleadoDao;
import aca.pg.archivo.spring.PosFotoAlumDao;

@Controller
public class ContDatosProfesorEmpleado {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired	
	private EmpDatosDao empDatosDao;
	
	@Autowired	
	private EmpleadoDao empleadoDao;
	
	@Autowired
	PosFotoAlumDao posFotoAlumDao;
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/datos_profesor/empleado/foto")
	public String datosProfesorEmpleadoFoto(HttpServletRequest request, Model modelo){
		//enviarConEnoc(request,"Error-aca.ContDatosProfesorEmpleado|Foto:");
		String codigoEmpleado	= "0";
		boolean existe			= false;
		boolean existeAcademico	= false;
		boolean existeFoto 		= false;
		EmpDatos empDatos		= new EmpDatos();
		Empleado empleado		= new Empleado();
		String puesto			= "-";
		String depto 			= "-";
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			existe				= true;
			codigoEmpleado 	 	= sesion.getAttribute("codigoEmpleado").toString();
			if (empDatosDao.existeReg(codigoEmpleado)){
				empDatos			= empDatosDao.mapeaRegId(codigoEmpleado);
				existeAcademico		= true;
				if (posFotoAlumDao.existeReg(codigoEmpleado, "O")){
					existeFoto = true;
				}
			}
			if (empleadoDao.existeRegClave(codigoEmpleado)){
				empleado			= empleadoDao.mapeaRegIdNomina(codigoEmpleado);
				puesto				= empleadoDao.getPuesto(codigoEmpleado);
				depto 				= empleadoDao.getDepartamentoRH(codigoEmpleado);
			}
		}
		
		modelo.addAttribute("existe", existe);
		modelo.addAttribute("existeAcademico", existeAcademico);
		modelo.addAttribute("existeFoto", existeFoto);
		modelo.addAttribute("puesto", puesto);
		modelo.addAttribute("depto", depto);
		modelo.addAttribute("empDatos", empDatos);
		modelo.addAttribute("empleado", empleado);
		
		return "datos_profesor/empleado/foto";
	}	
	
}