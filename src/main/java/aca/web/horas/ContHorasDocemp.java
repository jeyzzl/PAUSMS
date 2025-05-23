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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import aca.util.Fecha;
import aca.emp.spring.EmpDocEmp;


@Controller
public class ContHorasDocemp{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	
	
	@Autowired
	private aca.vista.spring.UsuariosDao usuariosDao;
	
	@Autowired
	private aca.emp.spring.EmpDocEmpDao empDocEmpDao;
	
	@Autowired
	private aca.emp.spring.EmpDocumentoDao empDocumentoDao;
	
	
	@RequestMapping("/horas/docemp/documentos")
	public String horasDocempDocumentos(HttpServletRequest request, Model modelo){
		
		String codigoEmpleado	= "0";
		String nombreEmpleado	= "-";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoEmpleado 	= (String)sesion.getAttribute("codigoEmpleado");
        }
        nombreEmpleado = usuariosDao.getNombreUsuario(codigoEmpleado, "NOMBRE");
        List<aca.emp.spring.EmpDocumento> lisDocumentos		= empDocumentoDao.lisEmpleado(codigoEmpleado, " ORDER BY ORDEN");
        HashMap<String, String> mapaImagenes				= empDocEmpDao.mapaImagenes(codigoEmpleado);
        
        modelo.addAttribute("nombreEmpleado",nombreEmpleado);
		
		modelo.addAttribute("lisDocumentos",lisDocumentos);
		modelo.addAttribute("mapaImagenes",mapaImagenes);
		
		return "horas/docemp/documentos";
	}
	
	@RequestMapping("/horas/docemp/nuevo")
	public String horasDocempNuevo(HttpServletRequest request, Model modelo){
		
		String codigoEmpleado	= "0";
		String codigoPersonal   = "0";
		String nombreEmpleado	= "-";
		String nombreUsuario	= "-";
		String nombreDocumento	= "-";
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoEmpleado 		= (String)sesion.getAttribute("codigoEmpleado");
        	codigoPersonal 		= (String)sesion.getAttribute("codigoPersonal");
        }        
        EmpDocEmp docEmp 	= new EmpDocEmp();
        byte imagenByte[] 		= null;
        
        String documentoId		= request.getParameter("DocumentoId")==null?"0":request.getParameter("DocumentoId");
        String hoja				= request.getParameter("Hoja")==null?"0":request.getParameter("Hoja");
        boolean tieneImagen		= false;        
        		
        nombreEmpleado 			= usuariosDao.getNombreUsuario(codigoEmpleado, "NOMBRE");        
        nombreDocumento			= empDocumentoDao.getDocumentoNombre(documentoId);
        
        // Buscar la imagen
        if (empDocEmpDao.existeImagen(codigoEmpleado, documentoId, hoja)){
        	tieneImagen = true;
        	docEmp = empDocEmpDao.mapeaRegId(codigoEmpleado, documentoId, hoja);
        	imagenByte = docEmp.getImagen();
        	nombreUsuario 			= usuariosDao.getNombreUsuario(docEmp.getUsuario(), "NOMBRE");
        }else{
        	docEmp = empDocEmpDao.mapeaRegId("0", "0", "0");
        	imagenByte = docEmp.getImagen();
        }
        
        List<aca.emp.spring.EmpDocumento> lisDocumentos		= empDocumentoDao.lisTodos(" ORDER BY ORDEN");
        
        modelo.addAttribute("codigoEmpleado",codigoEmpleado);
        modelo.addAttribute("codigoPersonal",codigoPersonal);
        modelo.addAttribute("lisDocumentos",lisDocumentos);
		modelo.addAttribute("nombreEmpleado",nombreEmpleado);
		modelo.addAttribute("nombreUsuario",nombreUsuario);
		modelo.addAttribute("nombreDocumento",nombreDocumento);
		modelo.addAttribute("tieneImagen",tieneImagen);
		modelo.addAttribute("imagenByte",imagenByte);
		modelo.addAttribute("docEmp",docEmp);
		
		return "horas/docemp/nuevo";
	}
	
	@PostMapping("/horas/docemp/guardarimagen")
	public String documentosGuardarImagen(@RequestParam("imagen") MultipartFile imagen, HttpServletRequest request){
		String documentoId 			= request.getParameter("DocumentoId")==null?"0":request.getParameter("DocumentoId");
		String hoja 				= request.getParameter("Hoja")==null?"0":request.getParameter("Hoja");
		String codigoEmpleado 		= "0";
		String codigoUsuario		= "0";
		String fecha				= Fecha.getHoy();
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoEmpleado 	= (String)sesion.getAttribute("codigoEmpleado");
        	codigoUsuario	= (String)sesion.getAttribute("codigoPersonal");
        }
        
		EmpDocEmp documento = new EmpDocEmp();
		try{
			documento.setCodigoPersonal(codigoEmpleado);
			documento.setDocumentoId(documentoId);
			documento.setHoja(hoja);		
			documento.setImagen(imagen.getBytes());
			documento.setUsuario(codigoUsuario);
			documento.setFecha(fecha);
			if (empDocEmpDao.existeReg(codigoEmpleado, documentoId, hoja)){
				empDocEmpDao.updateReg(documento);
			}else{
				empDocEmpDao.insertReg(documento);
			}
		}catch(Exception ex){
			System.out.println("Error al grabar imagen: "+ex);
		}	

		
		return "redirect:/horas/docemp/nuevo?DocumentoId="+documentoId+"&Hoja="+hoja;
	}
	
	@RequestMapping("/horas/docemp/borrarimagen")
	public String horasDocempBorrarImagen(HttpServletRequest request, Model modelo){		
		String codigoEmpleado 	= "0"; 
		String documentoId		= request.getParameter("documentoId")==null?"0":request.getParameter("documentoId");
		String hoja				= request.getParameter("hoja")==null?"0":request.getParameter("hoja");
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoEmpleado 	= (String)sesion.getAttribute("codigoEmpleado");	
        }
		try {
			if (empDocEmpDao.existeReg(codigoEmpleado, documentoId, hoja)){			
				empDocEmpDao.deleteReg(codigoEmpleado, documentoId, hoja);
			}			
		}catch(Exception ex){
			System.out.println("Error:documentosBorrarImagen:"+ex);
		}
		
		return "redirect:/horas/docemp/nuevo?DocumentoId="+documentoId+"&Hoja="+hoja;		
	}
	
}