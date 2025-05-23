package aca.web.horas;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;

import aca.emp.spring.EmpDocEmp;
import aca.emp.spring.EmpDocEmpDao;
import aca.emp.spring.EmpDocumento;
import aca.emp.spring.EmpDocumentoDao;
import aca.vista.spring.Maestros;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContHorasConsulta{	
	
	@Autowired
	EmpDocEmpDao empDocEmpDao;	
	
	@Autowired
	MaestrosDao maestrosDao;
	
	@Autowired
	EmpDocumentoDao empDocumentoDao;	
	
	
	@RequestMapping("/horas/consulta/lista")
	public String horasConsultaLista(HttpServletRequest request, Model modelo){
        
        List<Maestros> lisEmpleados				= maestrosDao.listConDocumentos(" ORDER BY APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE");
        HashMap<String, String> mapaImagenes 	= empDocEmpDao.mapaEmpImagen();
		
		modelo.addAttribute("lisEmpleados",lisEmpleados);	
		modelo.addAttribute("mapaImagenes",mapaImagenes);
		
		return "horas/consulta/lista";
	}
	
	@RequestMapping("/horas/consulta/documentos")
	public String horasConsultaDocumentos(HttpServletRequest request, Model modelo){
                
        String codigoEmpleado		= request.getParameter("CodigoEmpleado")==null?"0":request.getParameter("CodigoEmpleado");               
        String documentoId			= request.getParameter("DocumentoId")==null?"0":request.getParameter("DocumentoId");
        String hoja					= request.getParameter("Hoja")==null?"0":request.getParameter("Hoja");
        		
        String nombreEmpleado 		= maestrosDao.getNombreMaestro(codigoEmpleado, "NOMBRE");
        String nombreDocumento		= empDocumentoDao.getDocumentoNombre(documentoId);
        
        List<EmpDocumento> lisDocumentos		= empDocumentoDao.lisEmpleado(codigoEmpleado, " ORDER BY ORDEN");
        HashMap<String, String> mapaImagenes	= empDocEmpDao.mapaImagenes(codigoEmpleado);
        
        boolean existe = false;
        if (empDocEmpDao.existeReg(codigoEmpleado, documentoId, hoja)){
        	 existe = true;
        }
        
        modelo.addAttribute("codigoEmpleado",codigoEmpleado);
        modelo.addAttribute("nombreEmpleado",nombreEmpleado);
        modelo.addAttribute("existe",existe);
        
        modelo.addAttribute("lisDocumentos",lisDocumentos);
        modelo.addAttribute("mapaImagenes",mapaImagenes);
        modelo.addAttribute("nombreDocumento",nombreDocumento);
		
		return "horas/consulta/documentos";
	}
	
	@RequestMapping("/horas/consulta/docImagen")
	public void fotoEvento(HttpServletRequest request, HttpServletResponse response){
		
		String codigoEmpleado	= request.getParameter("CodigoEmpleado")==null?"0":request.getParameter("CodigoEmpleado");               
        String documentoId		= request.getParameter("DocumentoId")==null?"0":request.getParameter("DocumentoId");
        String hoja				= request.getParameter("Hoja")==null?"0":request.getParameter("Hoja");	
        
		EmpDocEmp empDoc		= new EmpDocEmp();	
		try{	
			if (empDocEmpDao.existeReg(codigoEmpleado, documentoId, hoja)){
				empDoc = empDocEmpDao.mapeaRegId(codigoEmpleado, documentoId, hoja);				
	        }			
			OutputStream out = response.getOutputStream();
			out.write(empDoc.getImagen());
			out.close();
		}catch(Exception ex){
			System.out.println("Error /horas/consulta/docImagen:"+ex);
		}
	}
	
}