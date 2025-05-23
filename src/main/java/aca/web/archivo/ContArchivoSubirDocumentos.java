package aca.web.archivo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import aca.alumno.spring.AlumPersonalDao;
import aca.pg.archivo.spring.PosArchGeneral;
import aca.pg.archivo.spring.PosArchGeneralDao;

@Controller
public class ContArchivoSubirDocumentos {
	
	@Autowired	
	private AlumPersonalDao alumPersonalDao;	
	
	@Autowired	
	private PosArchGeneralDao posArchGeneralDao;
	
	
	@PostMapping("/archivo/subir_documentos/guardarArchivo")
	public String archivoSubirDocumentosGuardarArchivo(@RequestParam("archivos")  MultipartFile[] files, HttpServletRequest request){
		
		String codigoPersonal = "0";		 
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal			= (String) sesion.getAttribute("codigoPersonal");
		}
		
		int total			= files.length;;
		int subidas 		= 0;
		String codigoAlumno	= "0";
		for (int i = 0; i < files.length; i++) {
			MultipartFile file = files[i];
			String name = files[i].getOriginalFilename();						
			try {
				PosArchGeneral archGeneral	= new PosArchGeneral();
				byte[] imagenByte 		= file.getBytes();
				String codigoImagen 	= name.substring(0,7);
				if(alumPersonalDao.existeReg(codigoImagen)){
					if (codigoAlumno.equals("0")) codigoAlumno = codigoImagen;
					archGeneral.setMatricula(codigoImagen);					
					archGeneral.setFolio(posArchGeneralDao.maximoReg(codigoImagen));												
					archGeneral.setFecha(aca.util.Fecha.getHoy());					
					archGeneral.setUsuario(codigoPersonal);
					archGeneral.setImagenByte(imagenByte);
					if(posArchGeneralDao.insertReg(archGeneral)){					
						subidas++;
					}			
				}
			}catch(Exception ex) {
				System.out.println("Error:"+ex);
			}			
		}
		
		return "redirect:/archivo/subir_documentos/subir?CodigoAlumno="+codigoAlumno+"&Total="+String.valueOf(total)+"&Subidas="+String.valueOf(subidas);
	}
	
	@RequestMapping("/archivo/subir_documentos/subir")
	public String archivoSubirDocumentosSubir(HttpServletRequest request){
		return "archivo/subir_documentos/subir";
	}
}
