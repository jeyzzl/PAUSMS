package aca.web.usuarios;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;

import javax.imageio.ImageIO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import aca.emp.spring.EmpFoto;
import aca.emp.spring.EmpFotoDao;
import aca.pg.archivo.FotoChica;
import aca.pg.archivo.FotoChicaDao;
import aca.pg.archivo.spring.PosFotoAlum;
import aca.pg.archivo.spring.PosFotoAlumDao;

@Controller
public class ContUsuariosSubir {	
	
	@Autowired	
	private EmpFotoDao empFotoDao;
	
	@Autowired	
	private PosFotoAlumDao posFotoAlumDao;
	
	@Autowired
	FotoChicaDao fotoChicaDao;
	
	@RequestMapping("/usuarios/subir/foto")
	public String usuariosSubirFoto(HttpServletRequest request){
		
		return "usuarios/subir/foto";
	}
	
	@PostMapping("/usuarios/subir/grabar")
	public String usuariosSubirGrabar(@RequestParam("imagen") MultipartFile imagen, HttpServletRequest request){		
		
		String codigo 			= request.getParameter("Codigo")==null?"0":request.getParameter("Codigo");
		String tipo 			= request.getParameter("Tipo")==null?"0":request.getParameter("Tipo");
		String mensaje 			= "-";
		String usuario 			= "0";
		
		HttpSession sesion	= ((HttpServletRequest)request).getSession();		
		if (sesion!=null){
			usuario  	= (String) sesion.getAttribute("codigoPersonal");
		}
		
		try {			
			if (tipo.equals("E")){
				PosFotoAlum fotoNormal 	= new PosFotoAlum();
				EmpFoto empFoto 		= new EmpFoto();
				empFoto.setCodigoPersonal(codigo);
				empFoto.setFecha(aca.util.Fecha.getHoy());
				empFoto.setFolio("0");
				empFoto.setUsuario("9800308");
				empFoto.setFoto(imagen.getBytes());
				if (empFotoDao.existeReg(codigo, "0")){
					if (empFotoDao.updateReg(empFoto)) {
						mensaje = "Imagen actualizada ("+codigo+")";
					}					
				}else {
					if (empFotoDao.insertReg(empFoto)) {
						mensaje = "Imagen grabada ("+codigo+")";
					}
				}
				
				try {				
					//InputStream fotoInput 	= new ByteArrayInputStream(file.getBytes());
					ByteArrayInputStream fotoInput = new ByteArrayInputStream(imagen.getBytes());
					String contentType = URLConnection.guessContentTypeFromStream(fotoInput);
				    BufferedImage imagenOriginal = ImageIO.read(fotoInput);
				      
	        		String tipoImagen = "jpg";
	        		if (contentType.equals("image/png")) 
	        			tipoImagen = "png";
	        		else if (contentType.equals("image/gif")) 
	        			tipoImagen = "gif";
	        		else if (contentType.equals("image/tiff")) 
	        			tipoImagen = "tiff";
				    
					// Libreria rt de WEB-INF/lib
					//@SuppressWarnings("restriction")
					//com.sun.image.codec.jpeg.JPEGImageDecoder decoder = com.sun.image.codec.jpeg.JPEGCodec.createJPEGDecoder(fotoInput);
					//BufferedImage imagenOriginal 	= decoder.decodeAsBufferedImage();
					
					//BufferedImage imagenOriginal 	= ImageIO.read(fotoInput);
					
					imagenOriginal= Scalr.resize(imagenOriginal, 1200, 900);
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					ImageIO.write(imagenOriginal, tipoImagen, baos);
					baos.flush();
					byte[] imagenReducida = baos.toByteArray();
					baos.close();     
					
					fotoNormal.setCodigoPersonal(codigo);
					fotoNormal.setTipo("O");
					fotoNormal.setFecha(aca.util.Fecha.getHoy());
					fotoNormal.setUsuario(usuario);
					fotoNormal.setFoto(imagenReducida);
					
					if (posFotoAlumDao.existeReg(codigo, "O")){
						posFotoAlumDao.updateReg(fotoNormal);
					}else{
						posFotoAlumDao.insertReg(fotoNormal);
					}
				} catch (Exception ex) {
					System.out.println("Error - aca.web.UsuariosSubir|usuariosSubirGrabar-AlumFoto|:"+ex);
					ex.printStackTrace();
				}
			
			}else if (tipo.equals("A")) {		
				PosFotoAlum fotoNormal 	= new PosFotoAlum();
				fotoNormal.setCodigoPersonal(codigo);
				fotoNormal.setTipo("O");
				fotoNormal.setFecha(aca.util.Fecha.getHoy());
				fotoNormal.setUsuario("9800308");
				fotoNormal.setFoto(imagen.getBytes());
				if (posFotoAlumDao.existeReg(codigo, "O")){
					if (posFotoAlumDao.updateReg(fotoNormal)){
						mensaje = "Imagen actualizada ("+codigo+")";
					}
				}else{
					if (posFotoAlumDao.insertReg(fotoNormal)){
						mensaje = "Imagen actualizada ("+codigo+")";
					}
				}
				
				//InputStream fotoInput 	= new ByteArrayInputStream(imagen.getBytes());
				ByteArrayInputStream fotoInput = new ByteArrayInputStream(imagen.getBytes());
				String contentType = URLConnection.guessContentTypeFromStream(fotoInput);
			    BufferedImage imagenOriginal = ImageIO.read(fotoInput);			      
        		String tipoImagen = "jpg";
        		if (contentType.equals("image/png")) 
        			tipoImagen = "png";
        		else if (contentType.equals("image/gif")) 
        			tipoImagen = "gif";
        		else if (contentType.equals("image/tiff")) 
        			tipoImagen = "tiff";
        		//@SuppressWarnings("restriction")
				//com.sun.image.codec.jpeg.JPEGImageDecoder decoder = com.sun.image.codec.jpeg.JPEGCodec.createJPEGDecoder(fotoInput);
				//BufferedImage imagenOriginal 	= decoder.decodeAsBufferedImage();
				int posX=0;
				if (imagenOriginal.getWidth() > imagenOriginal.getHeight()){ 
					posX = (imagenOriginal.getWidth() - imagenOriginal.getHeight()) / 2;
				}else if (posX == 0 && imagenOriginal.getWidth() < imagenOriginal.getHeight()){
					posX = (imagenOriginal.getHeight()-imagenOriginal.getWidth()) / 2;
				}
				
				BufferedImage recorte = imagenOriginal.getSubimage(posX, 0, imagenOriginal.getHeight(), imagenOriginal.getHeight());				
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(recorte, tipoImagen, baos);
				baos.flush();
				byte[] imagenRecortada = baos.toByteArray();
				baos.close();
				PosFotoAlum fotoCuadrada 	= new PosFotoAlum();
				fotoCuadrada.setCodigoPersonal(codigo);
				fotoCuadrada.setComentario("Cuadrada");
				fotoCuadrada.setFecha(aca.util.Fecha.getHoy());
				fotoCuadrada.setFoto(imagenRecortada);
				fotoCuadrada.setRechazada("N");
				fotoCuadrada.setTipo("C");
				fotoCuadrada.setUsuario(codigo);
				if (posFotoAlumDao.existeReg(codigo, "C")){
					posFotoAlumDao.updateReg(fotoCuadrada);
				}else {
					posFotoAlumDao.insertReg(fotoCuadrada);
				}
			}else {
				FotoChica fotoChica 		= new FotoChica();
				fotoChica.setCodigoPersonal(codigo);
				fotoChica.setFecha(aca.util.Fecha.getHoy());
				fotoChica.setFoto(imagen.getBytes());
				if (fotoChicaDao.existeReg(codigo)){
					if (fotoChicaDao.updateReg(fotoChica)){
						mensaje = "Imagen actualizada ("+codigo+")";
					}
				}else{
					if (fotoChicaDao.insertReg(fotoChica)){
						mensaje = "Imagen actualizada ("+codigo+")";
					}
				}
			}
		}catch(Exception ex){
			System.out.println(ex);
		}	
		return "redirect:/usuarios/subir/foto?Mensaje="+mensaje;
	}	
}