package aca.web.horas;

import java.util.List;
import java.util.HashMap;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;
import aca.emp.spring.EmpHorasDao;
import aca.emp.spring.EmpRango;
import aca.emp.spring.EmpRangoDao;
import aca.emp.spring.EmpRangoEmp;
import aca.emp.spring.EmpRangoEmpDao;
import aca.vista.spring.UsuariosDao;

@Controller
public class ContHorasRango{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	
	
	@Autowired
	private UsuariosDao usuariosDao;	
	
	@Autowired
	private EmpRangoDao empRangoDao;
	
	@Autowired
	private EmpRangoEmpDao empRangoEmpDao;
	
	@Autowired
	private CargaDao cargaDao;
	
	@Autowired
	private EmpHorasDao empHorasDao;
	
	
	@RequestMapping("/horas/rango/lista")
	public String horasRangoLista(HttpServletRequest request, Model modelo) {
		
		String cargaId 	= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		
		List<Carga> lisCargas 		= cargaDao.lisCargasConMaestros("ORDER BY ORDEN DESC");

		if(cargaId.equals("0")) {
			cargaId = lisCargas.get(0).getCargaId();
		}
		
		List<aca.Mapa> lisMaestros 	= empHorasDao.lisMaestrosPorCarga(cargaId, "ORDER BY VALOR");
		
		HashMap<String, EmpRango> mapaRangos			= empRangoDao.mapaRangosEmp();
		HashMap<String, String> mapaRangosEmpCargas		= empRangoEmpDao.mapaRangosDelEmpleado();
		
		modelo.addAttribute("cargaId",cargaId);
		modelo.addAttribute("lisMaestros",lisMaestros);        
		modelo.addAttribute("lisCargas",lisCargas);        
		modelo.addAttribute("mapaRangos",mapaRangos);
		modelo.addAttribute("mapaRangosEmpCargas",mapaRangosEmpCargas);
		
		return "horas/rango/lista";
	}

	@RequestMapping("/horas/rango/rango")
	public String horasRangoRango(HttpServletRequest request, Model modelo) {
		
		String cargaId 			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String codigoEmpleado	= request.getParameter("CodigoEmpleado")==null?"0":request.getParameter("CodigoEmpleado");
		String nombreEmpleado	= usuariosDao.getNombreUsuario(codigoEmpleado, "NOMBRE");
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){     
        	if(codigoEmpleado.equals("0")) {
        		codigoEmpleado = (String)sesion.getAttribute("codigoEmpleado");
        		nombreEmpleado = usuariosDao.getNombreUsuario(codigoEmpleado, "NOMBRE");
        	}
        	sesion.setAttribute("codigoEmpleado",codigoEmpleado);
        }	
        
        List<aca.emp.spring.EmpRangoEmp> lisRangos		= empRangoEmpDao.lisEmpleado(codigoEmpleado, " ORDER BY CODIGO_PERSONAL");
        HashMap<String, EmpRango> mapaRangos			= empRangoDao.mapaRangosEmp();
        
        modelo.addAttribute("codigoEmpleado",codigoEmpleado);        
        modelo.addAttribute("nombreEmpleado",nombreEmpleado);        
        modelo.addAttribute("lisRangos",lisRangos);
        modelo.addAttribute("mapaRangos",mapaRangos);
        modelo.addAttribute("cargaId",cargaId);
        
		return "horas/rango/rango";
	}
	
	@RequestMapping("/horas/rango/accion")
	public String horasRangoAccion(HttpServletRequest request, Model modelo){		
		
		EmpRangoEmp rangoEmp = new EmpRangoEmp();
		String cargaId 			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String codigoEmpleado 	= "0";
		String nombreEmpleado	= "0";
		
		HttpSession sesion		= null;		
		sesion = ((HttpServletRequest)request).getSession();
        if (sesion!=null){        	
        	codigoEmpleado = (String)sesion.getAttribute("codigoEmpleado");
        	nombreEmpleado = usuariosDao.getNombreUsuario(codigoEmpleado, "NOMBRE");
        	if (!cargaId.equals("0")){
        		if (empRangoEmpDao.existeReg(codigoEmpleado, cargaId)){
        			rangoEmp = empRangoEmpDao.mapeaRegId(codigoEmpleado, cargaId);
        		}
        	}
        }
			
		List<aca.emp.spring.EmpRango> lisRangos		= empRangoDao.lisTodos("ORDER BY RANGO_NOMBRE");
		List<Carga> lisCargas 						= cargaDao.lisCargasDelMaestro(codigoEmpleado,"ORDER BY ORDEN DESC");
		
		modelo.addAttribute("cargaId",cargaId);
		modelo.addAttribute("lisRangos",lisRangos);
		modelo.addAttribute("lisCargas",lisCargas);
        modelo.addAttribute("nombreEmpleado", nombreEmpleado);       
        modelo.addAttribute("rangoEmp", rangoEmp);
		
		return "horas/rango/accion";
	}
	
	@RequestMapping("/horas/rango/grabar")
	public String horasRangoGrabar(HttpServletRequest request){
		
		EmpRangoEmp rangoEmp 	= new EmpRangoEmp();
		String cargaId 			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String rangoId 			= request.getParameter("RangoId")==null?"0":request.getParameter("RangoId");
		String fecha 			= request.getParameter("Fecha")==null?"01/01/2000":request.getParameter("Fecha");
		String estado 			= request.getParameter("Estado")==null?"0":request.getParameter("Estado");
		String codigoEmpleado 	= "0";		
				
		HttpSession sesion		= null;		
		sesion = ((HttpServletRequest)request).getSession();
        if (sesion!=null){        	
        	codigoEmpleado = (String)sesion.getAttribute("codigoEmpleado");
        }
        
        rangoEmp.setCodigoPersonal(codigoEmpleado);
        rangoEmp.setCargaId(cargaId);
        rangoEmp.setRangoId(rangoId);
        rangoEmp.setFecha(fecha);
        rangoEmp.setEstado(estado);
        if (empRangoEmpDao.existeReg(codigoEmpleado, cargaId)){
        	empRangoEmpDao.updateReg(rangoEmp);
        }else{
        	empRangoEmpDao.insertReg(rangoEmp);
        }
        
		return "redirect:/horas/rango/rango?CargaId="+cargaId;
	}
	
	@RequestMapping("/horas/rango/borrar")
	public String horasRangoBorrar(HttpServletRequest request){
		
		String cargaId 			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String codigoEmpleado 	= "0";
				
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){        	
        	codigoEmpleado = (String)sesion.getAttribute("codigoEmpleado");
        }
        
        if (empRangoEmpDao.existeReg(codigoEmpleado, cargaId)){
        	empRangoEmpDao.deleteReg(codigoEmpleado, cargaId);
        }
        
		return "redirect:/horas/rango/rango?CargaId="+cargaId;
	}	
}