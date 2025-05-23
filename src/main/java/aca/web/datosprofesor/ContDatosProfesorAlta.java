package aca.web.datosprofesor;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.net.URLConnection;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import aca.acceso.spring.AccesoDao;
import aca.acceso.spring.AccesoOpcionDao;
import aca.carga.spring.CargaGrupoDao;
import aca.emp.spring.EmpMaestro;
import aca.emp.spring.EmpMaestroDao;
import aca.emp.spring.EmpDatos;
import aca.emp.spring.EmpDatosDao;
import aca.emp.spring.EmpFoto;
import aca.emp.spring.EmpFotoDao;
//import aca.emp.spring.EmpFoto;
//import aca.emp.spring.EmpFotoDao;
import aca.emp.spring.Empleado;
import aca.emp.spring.EmpleadoDao;
import aca.pg.archivo.spring.PosFotoAlum;
import aca.pg.archivo.spring.PosFotoAlumDao;
import aca.vista.spring.Maestros;
import aca.vista.spring.MaestrosDao;
import net.glxn.qrgen.javase.QRCode;

@Controller
public class ContDatosProfesorAlta {
	
	@Autowired
 	ServletContext context;
	
	@Autowired
	EmpMaestroDao empMaestroDao;	
	
	@Autowired
	CargaGrupoDao cargaGrupoDao;
	
	@Autowired
	EmpDatosDao empDatosDao;
	
	@Autowired
	EmpleadoDao empleadoDao;
	
	@Autowired
	MaestrosDao maestrosDao;	
		
	@Autowired
	private EmpFotoDao empFotoDao;
	
	@Autowired
	private PosFotoAlumDao posFotoAlumDao;
	
	@Autowired 
	private AccesoDao accesoDao;
	
	@Autowired
	private AccesoOpcionDao accesoOpcionDao;
	
	
	@RequestMapping("/datos_profesor/alta/listado")
	public String datosProfesorAltaListado(HttpServletRequest request, Model modelo){
		
		List<EmpMaestro> lista 					= empMaestroDao.getListAll("ORDER BY NOMBRE");
		HashMap<String, String> mapaMaestros	= (HashMap<String, String>) cargaGrupoDao.mapMateriasMaestros();
		HashMap<String, String> mapaAuxiliares	= (HashMap<String, String>) cargaGrupoDao.mapMateriasAuxiliares();
		
		modelo.addAttribute("lista", lista);
		modelo.addAttribute("mapaMaestros", mapaMaestros);
		modelo.addAttribute("mapaAuxiliares", mapaAuxiliares);		
		
		return "datos_profesor/alta/listado";
	}
	
	@RequestMapping("/datos_profesor/alta/borrar")
	public String datosProfesorAltaListadoBorrar(HttpServletRequest request, Model modelo){	
		String codigoPersonal	= request.getParameter("CodigoPersonal")==null?"0":request.getParameter("CodigoPersonal");
		String resultado 		= "-"; 
		
		if (empMaestroDao.existeReg(codigoPersonal)){
			if(empMaestroDao.deleteReg(codigoPersonal)){
				if(accesoDao.existeReg(codigoPersonal)) {
					if(accesoDao.deleteReg(codigoPersonal)) {
						resultado = "Deleted: "+codigoPersonal;	
					}
					if((accesoOpcionDao.deletePorUsuario(codigoPersonal) > 0)) {
						resultado = "Deleted: "+codigoPersonal;
					}
				}
				
				resultado = "Deleted: "+codigoPersonal;	
			}else{
				resultado = "Error deleting data:"+codigoPersonal;
			}
			
			
		}else{
			resultado = "Does not exist";
		}		
		return "redirect:/datos_profesor/alta/listado?Resultado="+resultado;
	}
	
	
	@RequestMapping("/datos_profesor/alta/maestro")
	public String datosProfesorAltaMaestro(HttpServletRequest request, Model modelo){
		
		String codigoPersonal 	= request.getParameter("CodigoPersonal")==null?"0":request.getParameter("CodigoPersonal");
		String nombre 			= request.getParameter("Nombre")==null?"-":request.getParameter("Nombre");
		String materno 			= request.getParameter("AMaterno")==null?"-":request.getParameter("AMaterno");
		String paterno 			= request.getParameter("APaterno")==null?"-":request.getParameter("APaterno");
		String email 			= request.getParameter("Email")==null?"-":request.getParameter("Email");
		String fechaNac			= request.getParameter("FechaNac")==null?"01/01/2000":request.getParameter("FechaNac");
		String edoCivil			= request.getParameter("EdoCivil")==null?"-":request.getParameter("EdoCivil");
		String genero 			= request.getParameter("Genero")==null?"-":request.getParameter("Genero");
		String telefono 		= request.getParameter("Telefono")==null?"-":request.getParameter("Telefono");
		String estado 			= request.getParameter("Estado")==null?"-":request.getParameter("Estado");		
		
		String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		String resultado 		= "-"; 
		EmpMaestro empMaestro 	= new EmpMaestro();		
		if(empMaestroDao.existeReg(codigoPersonal)) {
			empMaestro = empMaestroDao.mapeaRegId(codigoPersonal);
		}
		
		if(accion.equals("1") || codigoPersonal.equals("0")){
			empMaestro.setCodigoPersonal(empMaestroDao.maximoReg());
			//resultado = "¡Fill the form correctly!";
		}else{
			empMaestro.setCodigoPersonal(codigoPersonal);
		}
		
		if (accion.equals("2")){
			empMaestro.setCodigoPersonal(codigoPersonal);
			empMaestro.setNombre(nombre);
			empMaestro.setApellidoMaterno(materno);
			empMaestro.setApellidoPaterno(paterno);
			empMaestro.setEmail(email);
			empMaestro.setFNacimiento(fechaNac);
			empMaestro.setEstadoCivil(edoCivil);
			empMaestro.setGenero(genero);
			empMaestro.setTelefono(telefono);
			empMaestro.setEstado(estado);
			if (empMaestroDao.existeReg(codigoPersonal) == false){
				if (empMaestroDao.insertReg(empMaestro)){
					resultado = "Record saved: "+empMaestro.getCodigoPersonal();
				}else{
					resultado = "Error saving data: "+empMaestro.getCodigoPersonal();
				}
			}else{				
				if (empMaestroDao.updateReg(empMaestro)){
					resultado = "Record saved: "+empMaestro.getCodigoPersonal();					
				}else{
					resultado = "Error saving data: "+empMaestro.getCodigoPersonal();
				}
			}				
		}else if (accion.equals("3")){				
			if (empMaestroDao.existeReg( codigoPersonal) == true){
				if (empMaestroDao.deleteReg( empMaestro.getCodigoPersonal())){
					resultado = "Deleted: "+empMaestro.getCodigoPersonal();					
				}else{
					resultado = "Error: "+empMaestro.getCodigoPersonal();
				}	
			}else{
				resultado = "The code does not exist: "+empMaestro.getCodigoPersonal();
			}		
		}
		
		modelo.addAttribute("empMaestro", empMaestro);
		modelo.addAttribute("resultado", resultado);
		
		return "datos_profesor/alta/maestro";
	}	
	
	@RequestMapping("/datos_profesor/alta/fotocredencial")
	public String datosProfesorAltaFotoCredencial(HttpServletRequest request, Model modelo){
		
		String codigoPersonal 	= request.getParameter("CodigoPersonal")==null?"0":request.getParameter("CodigoPersonal");		
		String empleadoNombre 	= maestrosDao.getNombreMaestro(codigoPersonal, "NOMBRE"); 
		boolean tieneFoto = false;	
		if(empFotoDao.existeReg(codigoPersonal, "0")) {
			tieneFoto = true;
		}	
		
		String dimensiones 	= "Width:0,Heigth:0,X:0,Y:0";  
 		boolean tieneFotoNormal 	= false;	
 		
 		if(posFotoAlumDao.existeReg(codigoPersonal, "C")){
 			tieneFotoNormal	= true;
 			dimensiones 	= posFotoAlumDao.getDimensiones(codigoPersonal, "C"); 			
 		}
		
		modelo.addAttribute("tieneFoto", tieneFoto);
		modelo.addAttribute("empleadoNombre", empleadoNombre);
		modelo.addAttribute("tieneFotoNormal", tieneFotoNormal);
		modelo.addAttribute("dimensiones", dimensiones);
		
		return "datos_profesor/alta/fotocredencial";
	}
	
	@RequestMapping("/datos_profesor/alta/tomarfoto")
	public String datosProfesorAltaTomarFoto(HttpServletRequest request, Model modelo) {	
		
		String codigoPersonal 	= request.getParameter("CodigoPersonal")==null?"0":request.getParameter("CodigoPersonal");		

		Maestros maestro 		= maestrosDao.mapeaRegId(codigoPersonal);
		
		modelo.addAttribute("maestro", maestro);
		
		return "datos_profesor/alta/tomarfoto";
	}
	
 	@RequestMapping("/datos_profesor/alta/subir")
 	public String datosProfesorAltaSubir(HttpServletRequest request, Model modelo){
 		
 		String codigoPersonal 	= "0";
 		String codigoEmpleado 	= request.getParameter("CodigoEmpleado")==null?"0":request.getParameter("CodigoEmpleado");
 		String empleadoNombre	= "-";
 		HttpSession sesion 		= ((HttpServletRequest) request).getSession();
 		if (sesion != null) {
 			codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");
 			empleadoNombre		= maestrosDao.getNombreMaestro(codigoEmpleado, "NOMBRE"); 
 		} 
 		modelo.addAttribute("empleadoNombre", empleadoNombre);
 		modelo.addAttribute("codigoEmpleado", codigoEmpleado);
 		
 		return "datos_profesor/alta/subir";
 	}
	
	@PostMapping("/datos_profesor/alta/addFoto")
	public String datosProfesorAltaAddFoto(HttpServletRequest request){
		
		EmpFoto empFoto 		= new EmpFoto();
		PosFotoAlum fotoNormal 	= new PosFotoAlum();
		
		
		String codigoEmpleado	= request.getParameter("codigoMaestro")==null?"":request.getParameter("codigoMaestro");
		String codigoPersonal 	= "";
		String fotoBase64 		= request.getParameter("foto")==null?"":request.getParameter("foto");
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoPersonal  	= (String)sesion.getAttribute("codigoPersonal"); 
        }
        String mensaje 			= "-";
        
        try {
        	byte[] fotoByte = Base64.getDecoder().decode(fotoBase64.split(",")[1]);
			empFoto.setCodigoPersonal(codigoEmpleado);
			empFoto.setFolio("0");
			empFoto.setFecha(aca.util.Fecha.getHoy());
			empFoto.setFoto(fotoByte);
			empFoto.setUsuario(codigoPersonal);
			if (empFotoDao.existeReg(codigoEmpleado, "0")){
				if(empFotoDao.updateReg(empFoto)) {
					mensaje = "Updated Picture ("+codigoEmpleado+")";
				}
			}else{
				if(empFotoDao.insertReg(empFoto))
					mensaje = "Saved Picture ("+codigoEmpleado+")";
			}						
		
			ByteArrayInputStream fotoInput = new ByteArrayInputStream(fotoByte);
			String contentType = URLConnection.guessContentTypeFromStream(fotoInput);
		    BufferedImage imagenOriginal = ImageIO.read(fotoInput);
		      
    		String tipoImagen = "jpg";
    		if (contentType.equals("image/png")) 
    			tipoImagen = "png";
    		else if (contentType.equals("image/gif")) 
    			tipoImagen = "gif";
    		else if (contentType.equals("image/tiff")) 
    			tipoImagen = "tiff";
    		
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
			byte[] imagenReducida = baos.toByteArray();
			baos.close();     
			PosFotoAlum fotoCuadrada 	= new PosFotoAlum();
			fotoCuadrada.setCodigoPersonal(codigoEmpleado);
			fotoCuadrada.setTipo("C");
			fotoCuadrada.setFecha(aca.util.Fecha.getHoy());
			fotoCuadrada.setUsuario(codigoPersonal);
			fotoCuadrada.setFoto(imagenReducida);
			if (posFotoAlumDao.existeReg(codigoEmpleado, "C")){
				posFotoAlumDao.updateReg(fotoCuadrada);
			}else{
				posFotoAlumDao.insertReg(fotoCuadrada);
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.web.CredencialEmpDatosEmp|datosProfesorAltaAddFoto-AlumFoto|:"+ex);
			ex.printStackTrace();
		}
		
		return "redirect:/datos_profesor/alta/tomarfoto?CodigoPersonal="+codigoEmpleado;
	}
	
	@RequestMapping("/datos_profesor/alta/guardar")
	public String datosProfesorAltaGuardar(@RequestParam("archivo") MultipartFile file, HttpServletRequest request) {
		
		EmpFoto empFoto 			= new EmpFoto();
		
		String codigoEmpleado	= request.getParameter("codigoEmpleado")==null?"0":request.getParameter("codigoEmpleado");
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
		if(sesion!=null) {
        	String codigoPersonal  	= (String)	sesion.getAttribute("codigoPersonal"); 
        	String dir				= context.getRealPath("/WEB-INF/fotos/")+codigoEmpleado+".jpg";
        	
        	try {
        		byte[] fotoByte = file.getBytes();
        		// Escribir el archivo en el directorio del servidor de aplicaciones con el objeto FileOutputStream 
 				FileOutputStream fos = new FileOutputStream(dir);
 				fos.write(fotoByte,0,(int)file.getSize());
 				fos.flush();
 				fos.close();
 				 
 				//Grabar la foto en postgres
 				empFoto.setCodigoPersonal(codigoEmpleado);
 				empFoto.setFolio("0");
 				empFoto.setFecha(aca.util.Fecha.getHoy());
 				empFoto.setFoto(fotoByte);
 				empFoto.setUsuario(codigoPersonal);
 				if (empFotoDao.existeReg(codigoEmpleado, "0")){
 					empFotoDao.updateReg(empFoto);
 				}else{
 					empFotoDao.insertReg(empFoto);
 				}
 				
 				ByteArrayInputStream fotoInput = new ByteArrayInputStream(fotoByte);
 				String contentType = URLConnection.guessContentTypeFromStream(fotoInput);
 			    BufferedImage imagenOriginal = ImageIO.read(fotoInput);	
 			    
         		String tipoImagen = "jpg";
         		if (contentType.equals("image/png")) 
         			tipoImagen = "png";
         		else if (contentType.equals("image/gif")) 
         			tipoImagen = "gif";
         		else if (contentType.equals("image/tiff")) 
         			tipoImagen = "tiff";
         		
         		
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
 				byte[] imagenReducida = baos.toByteArray();
 				baos.close();     
 				PosFotoAlum fotoCuadrada 	= new PosFotoAlum();
 				fotoCuadrada.setCodigoPersonal(codigoEmpleado);
 				fotoCuadrada.setTipo("C");
 				fotoCuadrada.setFecha(aca.util.Fecha.getHoy());
 				fotoCuadrada.setUsuario(codigoPersonal);
 				fotoCuadrada.setFoto(imagenReducida);
 				if (posFotoAlumDao.existeReg(codigoEmpleado, "C")){
 					posFotoAlumDao.updateReg(fotoCuadrada);
 				}else{
 					posFotoAlumDao.insertReg(fotoCuadrada);
 				}
 				
        	}catch(Exception e) {
        		e.printStackTrace();
        	}
		}
		
		return "redirect:/datos_profesor/alta/fotocredencial?CodigoPersonal="+codigoEmpleado;
	}
	
	
	@RequestMapping("/datos_profesor/alta/borrarFoto")
	public String datosProfesorAltaFotoBorrar(HttpServletRequest request, HttpServletResponse response) {
		String codigo	 	= request.getParameter("Codigo")==null?"0":request.getParameter("Codigo");
		String tipo			= request.getParameter("Tipo")==null?"O":request.getParameter("Tipo");;	
 		String dir			= "";
		
 		HttpSession sesion	= null;
 		if (request instanceof HttpServletRequest) {
             sesion = ((HttpServletRequest)request).getSession();   
        }
 		
 		if(sesion!=null) {
 			
 			// BORRAR LA IMAGEN EN DIRECTORIO WEB-INF/FOTOS
 			if (tipo.equals("C")){
 				// Si es alumno o empleado
 				dir				= context.getRealPath("/WEB-INF/fotos/")+codigo+".jpg";
 			}else{
 				// Si es dependiente o es segunda foto de alumno
 				dir				= context.getRealPath("/WEB-INF/fotos/")+codigo+"-"+tipo+".jpg";
 			}
 			
 			boolean borrar = false;
 			java.io.File fileFoto = new java.io.File(dir);
 			int i=0;
 			if (fileFoto.exists()){				
 				// Ciclo para corregir error en sistemas operativos windows(No es necesario si el servidor de aplicaciones está en linux)
 				while(!borrar && i<50000){
 					if(fileFoto.delete()){
 						borrar = true;				
 					}
 					i++;
 				}
 			}
 			
 			// BORRAR FOTO EN BASE DE DATOS
 			try{			
 				// Busca la imagen
 				if(empFotoDao.existeReg(codigo, "0")) {
 					empFotoDao.deleteReg(codigo, "0");
 				}
 				
 				if(tipo.equals("C")) {
 					if (posFotoAlumDao.existeReg(codigo, tipo)){
 	 					posFotoAlumDao.deleteReg(codigo, tipo);
 	 				}
 				}
 			}catch(Exception ex){
 				System.out.println("Error:/fotoBorrar"+ex);
 				
 			}
 		}
 		
		return "redirect:/datos_profesor/alta/fotocredencial?CodigoPersonal="+codigo;
	}
	
	@RequestMapping("datos_profesor/alta/recortarFoto")
	public String datosProfesorAltaRecortarFoto(HttpServletRequest request, HttpServletResponse response) {
		String codigo 			= request.getParameter("Codigo")==null?"0":request.getParameter("Codigo");
 		String tipo				= request.getParameter("Tipo")==null?"C":request.getParameter("Tipo");
 		String valX				= request.getParameter("X")==null?"0":request.getParameter("X");
 		String valY				= request.getParameter("Y")==null?"0":request.getParameter("Y");
 		PosFotoAlum foto 		= new PosFotoAlum();       
		
 		HttpSession sesion		= ((HttpServletRequest)request).getSession();
 		if(sesion!=null) {
 			try {
 				String codigoPersonal = (String) sesion.getAttribute("codigoPersonal");
 				// Encontrar imagen
 				if(posFotoAlumDao.existeReg(codigo, tipo)) {
 					foto = posFotoAlumDao.mapeaRegId(codigo, tipo);   
 				}else {
 					foto = posFotoAlumDao.mapeaRegId("usWhite", "O");
 				}
 				
 				ByteArrayInputStream fotoInput 	= new ByteArrayInputStream(foto.getFoto());
 				String contentType 				= URLConnection.guessContentTypeFromStream(fotoInput);
 				BufferedImage imagenOriginal 	= ImageIO.read(fotoInput);
 				String tipoImagen 				= "jpg";
 				
 				if(contentType.equals("image/png")) {
 					tipoImagen = "png";
 				}else if(contentType.equals("image/gif")) {
 					tipoImagen = "gif";
 				}else if(contentType.equals("image/tiff")) {
 					tipoImagen = "tiff";
 				}
 				
 				int posX 	= 0;
 				int posY 	= 0;
 				int largo 	= 0;
 				if(imagenOriginal.getWidth() > imagenOriginal.getHeight()) {
 					posX 	= valX.equals("0")?(imagenOriginal.getWidth() - imagenOriginal.getHeight())/2:Integer.parseInt(valX);
 					posY 	= 0;
 					largo 	= imagenOriginal.getHeight();
 				}
 				if (imagenOriginal.getWidth() < imagenOriginal.getHeight()) {
     				posX	= 0;
     				posY 	= valY.equals("0")?(imagenOriginal.getHeight()-imagenOriginal.getWidth())/2:Integer.parseInt(valY);
     				largo 	= imagenOriginal.getWidth();
     			}
 				
 				BufferedImage recorte = ((BufferedImage) imagenOriginal).getSubimage(posX, posY, largo, largo);
 				ByteArrayOutputStream baos = new ByteArrayOutputStream();
 				ImageIO.write(recorte, tipoImagen, baos);
 				baos.flush();
 				byte[] imagenRecortada = baos.toByteArray();
 				
 				baos.close();
 				PosFotoAlum fotoCuadrada = new PosFotoAlum ();
 				fotoCuadrada.setCodigoPersonal(codigo);
 				fotoCuadrada.setComentario("Cuadrada");
 				fotoCuadrada.setFecha(aca.util.Fecha.getHoy());
 				fotoCuadrada.setFoto(imagenRecortada);
 				fotoCuadrada.setRechazada("N");
 				fotoCuadrada.setTipo("C");
 				fotoCuadrada.setUsuario(codigoPersonal);
 				if(posFotoAlumDao.existeReg(codigo, "C")) {
 					posFotoAlumDao.updateReg(fotoCuadrada);
 				}else {
 					posFotoAlumDao.insertReg(fotoCuadrada);
 				}
 			}catch(Exception ex) {
 				System.out.println("Error /recortarFoto:" + ex);
 			}
 		}
 		
		return "redirect:/datos_profesor/alta/fotocredencial?CodigoPersonal="+codigo;
	}
	
}