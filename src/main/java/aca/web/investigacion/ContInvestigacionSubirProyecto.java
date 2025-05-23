package aca.web.investigacion;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContInvestigacionSubirProyecto {	
	
	@Autowired
	aca.investiga.spring.InvArchivoDao invArchivoDao;
	
	
	@RequestMapping(value={"/investigacion/subirProyecto","/academico/investigacion/subirProyecto"})
	public void fotoBajar(HttpServletRequest request, HttpServletResponse response){
		
		String proyectoId	= request.getParameter("proyectoId")==null?"0":request.getParameter("proyectoId");
		String ruta			= request.getParameter("ruta")==null?"":request.getParameter("ruta");
		String nombre		= request.getParameter("nombre")==null?"vacio.txt":request.getParameter("nombre");
		
		aca.investiga.spring.InvArchivo archivo = new aca.investiga.spring.InvArchivo();		
		
		// Busca la imagen 
		try{
			if(request.getParameter("ruta") != null){
				java.io.File f = new java.io.File(ruta);		
				byte[] archivoPdf = null;
				java.io.FileInputStream instream = null;		
				if(f.exists()){
					archivoPdf = new byte[(int)f.length()];
					instream = new java.io.FileInputStream(ruta);
				}
				instream.read(archivoPdf,0,(int)f.length());
				
				archivo.setProyectoId(proyectoId);
				archivo.setFolio("10");			
				archivo.setNombre( nombre );
				archivo.setArchivo(archivoPdf);			
				if(invArchivoDao.existeReg(proyectoId, "10")){				
					invArchivoDao.updateReg(archivo);	
				}else{				
					invArchivoDao.insertReg(archivo);	
				}
				
				response.setContentType("application/octet-stream");
				response.setHeader("Content-Disposition","attachment; filename=\""+nombre+ "\"");
				response.getOutputStream().write(archivoPdf);
				response.flushBuffer();
				instream.close();
			}		
			
		}catch(Exception ex){
			System.out.println("Error /investigacion/subirProyecto:"+ex);
		}
	}
	
}