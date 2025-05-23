 package aca.web.datosalumno;

 import java.awt.Color;
 import java.awt.image.BufferedImage;
 import java.io.ByteArrayInputStream;
 import java.io.ByteArrayOutputStream;
 import java.io.FileOutputStream;
 import java.net.URLConnection;
 import java.util.Base64;
 import java.util.List;

 import javax.imageio.ImageIO;
 import jakarta.servlet.ServletContext;
 import jakarta.servlet.http.HttpServletRequest;
 import jakarta.servlet.http.HttpServletResponse;
 import jakarta.servlet.http.HttpSession;
 import javax.sql.DataSource;

 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.beans.factory.annotation.Qualifier;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.Model;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RequestParam;
 import org.springframework.web.multipart.MultipartFile;

 import aca.alumno.spring.AlumAcademico;
 import aca.alumno.spring.AlumAcademicoDao;
 import aca.alumno.spring.AlumCredencial;
 import aca.alumno.spring.AlumCredencialDao;
 import aca.alumno.spring.AlumFotografia;
 import aca.alumno.spring.AlumFotografiaDao;
 import aca.alumno.spring.AlumPersonal;
 import aca.alumno.spring.AlumPersonalDao;
 import aca.alumno.spring.AlumPlan;
 import aca.alumno.spring.AlumPlanDao;
 import aca.catalogo.spring.CatCarreraDao;
 import aca.catalogo.spring.CatReligionDao;
import aca.matricula.spring.MatAlumno;
import aca.matricula.spring.MatAlumnoDao;
import aca.pg.archivo.spring.PosFotoAlum;
 import aca.pg.archivo.spring.PosFotoAlumDao;
 import aca.plan.spring.MapaPlanDao;
 import aca.vista.spring.InscritosDao;

 @Controller
 public class ContDatosAlumnoFotoCredencial {
 	
 	@Autowired
 	@Qualifier("dsEnoc")
 	private DataSource enoc;
 	
 	@Autowired
 	@Qualifier("dsArchivo")
 	private DataSource archivo;
 	
 	@Autowired
 	private PosFotoAlumDao posFotoAlumDao;
 	
 	@Autowired
 	ServletContext context;
 	
 	@Autowired
 	AlumPersonalDao alumPersonalDao;
 	
 	@Autowired
 	AlumPlanDao alumPlanDao;
 	
 	@Autowired
 	AlumAcademicoDao alumAcademicoDao;
 	
 	@Autowired
 	CatCarreraDao catCarreraDao;
 	
 	@Autowired
 	CatReligionDao catReligionDao;
 	
 	@Autowired
 	AlumCredencialDao alumCredencialDao;
 	
 	@Autowired
 	AlumFotografiaDao alumFotografiaDao;
 	
 	@Autowired
 	MapaPlanDao mapaPlanDao;
 	
 	@Autowired
 	InscritosDao inscritosDao;

	@Autowired
 	MatAlumnoDao matAlumnoDao;
 	

 	public void enviarConEnoc(HttpServletRequest request, String url){
 		// Enviar de la conexión
 		try{ 
 			request.setAttribute("conEnoc", enoc.getConnection());			
 		}catch(Exception ex){ 
 			System.out.println(url+" "+ex);
 		}
 	}
 	
 	public void enviarConArchivo(HttpServletRequest request, String url){		
 		try{
 			request.setAttribute("conArchivo", archivo.getConnection());
 		}catch(Exception ex){ 
 			System.out.println(url+" "+ex);
 		}
 	}
 	
 	@RequestMapping("/datos_alumno/fotocredencial/actualizar_credencial")
 	public String datosAlumnoFotoCredencialActualizarCredencial(HttpServletRequest request){		
 		String codigoAlumno		= "0";
 		String carreraId 		= "0";		
 		HttpSession sesion	= ((HttpServletRequest)request).getSession();		
 		if (sesion!=null){
 			codigoAlumno		= (String) sesion.getAttribute("codigoAlumno");
 			carreraId 			= alumPlanDao.getCarreraId(codigoAlumno);
 		}			
 		String periodo1			= "";
 		String periodo2			= "";
 		String periodo3			= "";
 		String mensaje 			= "-";
 		
 		if ( !carreraId.substring(0,3).equals("107") ){
 			periodo1 = "Agosto a Enero";
 			periodo2 = "Enero a Mayo";
 			periodo3 = "Junio a Agosto";
 		}else{
 			periodo1 = "Agosto a Noviembre";
 			periodo2 = "Noviembre a Marzo";
 			periodo3 = "Marzo a Julio";
 		}		
 		try{			
 			if (alumPersonalDao.existeReg(codigoAlumno)){
 				
 				AlumPersonal alumPersonal 		= alumPersonalDao.mapeaRegId(codigoAlumno);
 				AlumCredencial alumCredencial 	= new AlumCredencial();
 				
 				alumCredencial.setCodigoPersonal(codigoAlumno);			
 				alumCredencial.setNombres(alumPersonal.getNombre());
 				alumCredencial.setApellidos(alumPersonal.getApellidoPaterno()+" "+alumPersonal.getApellidoMaterno());
 				alumCredencial.setCarrera(catCarreraDao.getNombreCarrera(carreraId));
 				alumCredencial.setCotejado(alumPersonal.getCotejado());
 				alumCredencial.setPeriodo1(periodo1);
 				alumCredencial.setPeriodo2(periodo2);
 				alumCredencial.setPeriodo3(periodo3);
 				if (alumCredencialDao.existeReg(codigoAlumno)){					
 					if (alumCredencialDao.updateReg(alumCredencial)){					
 						mensaje = "Updated!";
 					}
 				}else{
 					if (posFotoAlumDao.existeReg(codigoAlumno, "O")){
 						if (alumCredencialDao.insertReg(alumCredencial)){												
 							mensaje = "Updated!";						
 						}else{
 							mensaje = "Error: No file found";
 						}												
 					}					
 				}	
 			}		
 		}catch(Exception e){
 			System.out.println("Error al subir los datos de la credencial: "+e);
 		}
 		
 		return "redirect:/datos_alumno/fotocredencial/datos?Mensaje="+mensaje;
 	}
 	
 	
 	@RequestMapping("/datos_alumno/fotocredencial/upload")
 	public String datosAlumnoFotoCredencialUpload(HttpServletRequest request, Model modelo) {
 		enviarConEnoc(request,"Error-aca.ControllerDatosAlumno|datosAlumnoFotoCredencialUpload:");
 		enviarConArchivo(request,"Error-aca.ContDatosAlumnoFotoCredencial|datosAlumnoFotoCredencialUpload:");	
 		return "datos_alumno/fotocredencial/upload";
 	}
 	
 	@RequestMapping("/datos_alumno/fotocredencial/guardar")
 	public String datosAlumnoFotoCredencialGuardar(@RequestParam("archivo") MultipartFile file, HttpServletRequest request){
 		
 		PosFotoAlum alumFotoPos = new PosFotoAlum();
 		
 		HttpSession sesion	= ((HttpServletRequest)request).getSession();		
 		if (sesion!=null){
 			String codigoAlumno		= (String) sesion.getAttribute("codigoAlumno");
 			String codigoPersonal  	= (String) sesion.getAttribute("codigoPersonal");
 			String dir				= context.getRealPath("/WEB-INF/fotos/")+codigoAlumno+".jpg";
 			
 			
 			try{
 				byte[] fotoByte = file.getBytes();
 				// Escribir el archivo en el directorio del servidor de aplicaciones con el objeto FileOutputStream 
 				FileOutputStream fos = new FileOutputStream(dir);
 				fos.write(fotoByte,0,(int)file.getSize());
 				fos.flush();
 				fos.close();
 				
 				// Grabar la foto en postgres
 				alumFotoPos.setCodigoPersonal(codigoAlumno);
 				alumFotoPos.setFecha(aca.util.Fecha.getHoy());
 				alumFotoPos.setFoto(fotoByte);
 				alumFotoPos.setTipo("O");
 				alumFotoPos.setUsuario(codigoPersonal);
 				
 				if (posFotoAlumDao.existeReg(codigoAlumno, "O")){
 					posFotoAlumDao.updateReg(alumFotoPos);
 				}else{
 					posFotoAlumDao.insertReg(alumFotoPos);
 				}
 				
 				//InputStream fotoInput 	= new ByteArrayInputStream(fotoByte);
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
 				fotoCuadrada.setCodigoPersonal(codigoAlumno);
 				fotoCuadrada.setComentario("Cuadrada");
 				fotoCuadrada.setFecha(aca.util.Fecha.getHoy());
 				fotoCuadrada.setFoto(imagenRecortada);
 				fotoCuadrada.setRechazada("N");
 				fotoCuadrada.setTipo("C");
 				fotoCuadrada.setUsuario(codigoPersonal);
 				if (posFotoAlumDao.existeReg(codigoAlumno, "C")){
 					posFotoAlumDao.updateReg(fotoCuadrada);
 				}else {
 					posFotoAlumDao.insertReg(fotoCuadrada);
 				}
 				
 			}catch( Exception e) {
 	            e.printStackTrace();
 			}	
 		}		
 		
 		return "redirect:/datos_alumno/fotocredencial/datos";
 	}
 	
 	@RequestMapping("/datos_alumno/fotocredencial/borrar")
 	public String fotoBorrar(HttpServletRequest request, HttpServletResponse response){		
 		String codigo 		= request.getParameter("Codigo")==null?"0":request.getParameter("Codigo");
 		String tipo			= request.getParameter("Tipo")==null?"O":request.getParameter("Tipo");	
 		String dir			= "";
 		
 		HttpSession sesion	= null;
 		if (request instanceof HttpServletRequest) {
             sesion = ((HttpServletRequest)request).getSession();   
         }
 		if (sesion!=null){
 			
 			// BORRAR LA IMAGEN EN DIRECTORIO WEB-INF/FOTOS
 			if (tipo.equals("O")){
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
 				if (posFotoAlumDao.existeReg(codigo, tipo)){
 					posFotoAlumDao.deleteReg(codigo, tipo);
 				}
 			}catch(Exception ex){
 				System.out.println("Error:/fotoBorrar"+ex);
 				
 			}			
 		}		
 		
 		return "redirect:/datos_alumno/fotocredencial/datos";
 	}
 	
 	@RequestMapping("/datos_alumno/fotocredencial/tomarfoto")
 	public String datosAlumnoFotoCredencialTomarFoto(HttpServletRequest request, Model modelo) {	
 		
 		String matricula = "";
 		
 		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
         if (sesion!=null){
         	matricula 	   		= (String) sesion.getAttribute("codigoAlumno");        	
         }

 		AlumPersonal alumno = alumPersonalDao.mapeaRegId(matricula);

 		AlumFotografia alumFoto = new AlumFotografia();
 		
 		String resAlumno = "1";
 		String nombreAlumno = alumPersonalDao.getNombreAlumno(matricula, "NOMBRE");
 		
 		if (alumFotografiaDao.existeReg(matricula)){
 			alumFoto = alumFotografiaDao.mapeaRegId(matricula);
 			resAlumno = alumFoto.getResolucion();
 		}
 		
 		modelo.addAttribute("alumno", alumno);
 		modelo.addAttribute("alumFoto", alumFoto);
 		modelo.addAttribute("nombreAlumno", nombreAlumno);
 		
 		return "datos_alumno/fotocredencial/tomarfoto";
 	}
 	
 	@PostMapping("/datos_alumno/fotocredencial/addFoto")
 	public String datosAlumnoFotoCredencialAddFoto(HttpServletRequest request){
 		
 		String codigoAlumno		= "";
 		String codigoPersonal 	= "";
 		String fotoBase64 		= request.getParameter("foto")==null?"":request.getParameter("foto");
 		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
         if (sesion!=null){
         	codigoAlumno		= (String)sesion.getAttribute("codigoAlumno");
         	codigoPersonal  	= (String) sesion.getAttribute("codigoPersonal"); 
         }
         String mensaje 		= "-";
         
         try {
         	byte[] imagen = Base64.getDecoder().decode(fotoBase64.split(",")[1]);
 			PosFotoAlum fotoNormal = new PosFotoAlum();
 			fotoNormal.setCodigoPersonal(codigoAlumno);
 			fotoNormal.setTipo("O");
 			fotoNormal.setFecha(aca.util.Fecha.getHoy());
 			fotoNormal.setUsuario(codigoPersonal);
 			fotoNormal.setFoto(imagen);
 			if (posFotoAlumDao.existeReg(codigoAlumno, "O")){
 				if (posFotoAlumDao.updateReg(fotoNormal)){
 					mensaje = "Imagen actualizada ("+codigoAlumno+")";
 				}
 			}else{
 				if (posFotoAlumDao.insertReg(fotoNormal)){
 					mensaje = "Imagen grabada ("+codigoAlumno+")";
 				}
 			}
 			
 			ByteArrayInputStream fotoInput = new ByteArrayInputStream(imagen);
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
 			byte[] imagenRecortada = baos.toByteArray();
 			baos.close();
 			PosFotoAlum fotoCuadrada 	= new PosFotoAlum();
 			fotoCuadrada.setCodigoPersonal(codigoAlumno);
 			fotoCuadrada.setComentario("Cuadrada");
 			fotoCuadrada.setFecha(aca.util.Fecha.getHoy());
 			fotoCuadrada.setFoto(imagenRecortada);
 			fotoCuadrada.setRechazada("N");
 			fotoCuadrada.setTipo("C");
 			fotoCuadrada.setUsuario(codigoAlumno);
 			if (posFotoAlumDao.existeReg(codigoAlumno, "C")){
 				posFotoAlumDao.updateReg(fotoCuadrada);
 			}else {
 				posFotoAlumDao.insertReg(fotoCuadrada);
 			}
         }catch(Exception ex){
 			System.out.println(ex);
 		}
 		
 		return "redirect:/datos_alumno/fotocredencial/tomarfoto?Mensaje="+mensaje;
 	}

 	@RequestMapping("/datos_alumno/fotocredencial/subir")
 	public String datosAlumnoFotoCredencialSubir(HttpServletRequest request, Model modelo){
 		
 		String matricula 		= "0";
 		String alumnoNombre		= "-";
 		HttpSession sesion 		= ((HttpServletRequest) request).getSession();
 		if (sesion != null) {
 			matricula 			= (String) sesion.getAttribute("codigoAlumno");
 			alumnoNombre 		= alumPersonalDao.getNombreAlumno(matricula, "NOMBRE");
 		} 
 		modelo.addAttribute("alumnoNombre", alumnoNombre);
 		
 		return "datos_alumno/fotocredencial/subir";
 	}
 	
 	@RequestMapping("/datos_alumno/fotocredencial/datos")
 	public String datosAlumnoFotoCredencialDatos(HttpServletRequest request, Model modelo) {
 		
 		String codigoRFID				= request.getParameter("CodigoRFID")==null?"-":request.getParameter("CodigoRFID");
 		String codigoCred				= request.getParameter("CodigoCred")==null?"-":request.getParameter("CodigoCred");
 		String accion					= request.getParameter("Accion")==null?"0":request.getParameter("Accion");		

 		AlumPersonal alumPersonal 		= new AlumPersonal();
 		AlumPlan alumPlan				= new AlumPlan();
 		AlumAcademico alumAcademico		= new AlumAcademico();
 		String facultadId				= "0";
 		String carreraId				= "0";
 		String religionNombre			= "-";
 		String fechaActualizacion		= "01/01/2000";
 		String carreraNombre 			= "-";
 		boolean esInscrito 				= false;		
 		String matricula 				= "0";
 		HttpSession sesion 		= ((HttpServletRequest) request).getSession();
 		if (sesion != null) {
 			matricula 			= (String) sesion.getAttribute("codigoAlumno");
 			
 			// Modificar los datos de los codigos
 			if (accion.equals("2")) {
 				// Update
 				codigoRFID = codigoRFID.replace(" ","");
 				if (alumPersonalDao.updateCodigoSe(matricula, codigoRFID)){
 				}		
 				if (alumPersonalDao.updateCredencial(matricula, codigoCred.replace(" ",""))){
 					
 				}
 			}
 			
 			alumPersonal		= alumPersonalDao.mapeaRegId(matricula);
 			alumPlan 			= alumPlanDao.mapeaRegId(matricula);
 			alumAcademico		= alumAcademicoDao.mapeaRegId(matricula);
 			carreraId 			= alumPlanDao.getCarreraDelPlan(alumPlan.getPlanId());
 			carreraNombre 		= mapaPlanDao.getCarreraSe(alumPlan.getPlanId());
 			facultadId 			= catCarreraDao.getFacultadId(carreraId);
 			religionNombre 		= catReligionDao.getNombreReligion(alumPersonal.getReligionId());			
 			fechaActualizacion 	= alumCredencialDao.getFechaActualizacion(matricula);
 			esInscrito 			= alumPersonalDao.esInscrito(matricula);
 		}
 		
 		String dimensiones 	= "Ancho:0,Alto:0,X:0,Y:0";  
 		boolean tieneFotoNormal 	= false;		
 		if(posFotoAlumDao.existeReg(matricula, "O")){
 			tieneFotoNormal	= true;
 			dimensiones 	= posFotoAlumDao.getDimensiones(matricula, "O");
 		}
 		
 		boolean tieneFotoCuadrada 	= false;		
 		if(posFotoAlumDao.existeReg(matricula, "C")){
 			tieneFotoCuadrada 		= true;			
 		}	

		MatAlumno matAlumno = new MatAlumno();
		if(matAlumnoDao.existeRegEstado("I", matricula, alumPlan.getPlanId())){
			matAlumno = matAlumnoDao.mapeaRegIdPorEstado("I", matricula, alumPlan.getPlanId());
		}
 		
 		modelo.addAttribute("tieneFotoNormal", tieneFotoNormal);
 		modelo.addAttribute("tieneFotoCuadrada", tieneFotoCuadrada);
 		modelo.addAttribute("alumPersonal", alumPersonal);
 		modelo.addAttribute("alumPlan", alumPlan);
 		modelo.addAttribute("alumAcademico", alumAcademico);
 		modelo.addAttribute("facultadId", facultadId);
 		modelo.addAttribute("carreraId", carreraId);
 		modelo.addAttribute("religionNombre", religionNombre);
 		modelo.addAttribute("carreraNombre", carreraNombre);
 		modelo.addAttribute("fechaActualizacion", fechaActualizacion);
 		modelo.addAttribute("esInscrito", esInscrito);
 		modelo.addAttribute("dimensiones", dimensiones);
		modelo.addAttribute("matAlumno", matAlumno);
 		
 		return "datos_alumno/fotocredencial/datos";
 	}
 	
 	@RequestMapping("datos_alumno/fotocredencial/recortarFoto")
 	public String datosAlumnoFotoCredencialRecortarFoto(HttpServletRequest request, HttpServletResponse response){
 		String codigo 			= request.getParameter("Codigo")==null?"0":request.getParameter("Codigo");
 		String tipo				= request.getParameter("Tipo")==null?"O":request.getParameter("Tipo");
 		String valX				= request.getParameter("X")==null?"0":request.getParameter("X");
 		String valY				= request.getParameter("Y")==null?"0":request.getParameter("Y");
 		PosFotoAlum foto 		= new PosFotoAlum();       
         
 		HttpSession sesion		= ((HttpServletRequest)request).getSession();
         if (sesion!=null){        	
         	try{
         		String codigoPersonal = (String) sesion.getAttribute("codigoPersonal");
         		// Busca la imagen
         		if (posFotoAlumDao.existeReg(codigo, tipo)){
         			foto = posFotoAlumDao.mapeaRegId(codigo, tipo);
         		}else{
         			foto = posFotoAlumDao.mapeaRegId("usWhite", "O");
         		} 		
         		      		
         		ByteArrayInputStream fotoInput 	= new ByteArrayInputStream(foto.getFoto());
         		String contentType 				= URLConnection.guessContentTypeFromStream(fotoInput);
         		BufferedImage imagenOriginal 	= ImageIO.read(fotoInput);        		
         		String tipoImagen = "jpg";
         		
         		if (contentType.equals("image/png")) 
         			tipoImagen = "png";
         		else if (contentType.equals("image/gif")) 
         			tipoImagen = "gif";
         		else if (contentType.equals("image/tiff")) 
         			tipoImagen = "tiff";   
         		
 				int posX=0;
 				int posY=0;				
     			int largo	= 0;    			
     			if (imagenOriginal.getWidth() > imagenOriginal.getHeight()){ 
     				posX 	= valX.equals("0")?(imagenOriginal.getWidth() - imagenOriginal.getHeight())/2:Integer.parseInt(valX);
     				posY	= 0;    				
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
 				PosFotoAlum fotoCuadrada 	= new PosFotoAlum();
 				fotoCuadrada.setCodigoPersonal(codigo);
 				fotoCuadrada.setComentario("Cuadrada");
 				fotoCuadrada.setFecha(aca.util.Fecha.getHoy());
 				fotoCuadrada.setFoto(imagenRecortada);
 				fotoCuadrada.setRechazada("N");
 				fotoCuadrada.setTipo("C");
 				fotoCuadrada.setUsuario(codigoPersonal);
 				if (posFotoAlumDao.existeReg(codigo, "C")){
 					posFotoAlumDao.updateReg(fotoCuadrada);
 				}else {
 					posFotoAlumDao.insertReg(fotoCuadrada);
 				}
         	}catch(Exception ex){
         		System.out.println("Error /foto:"+ex);
         	}
         }
         return "redirect:/datos_alumno/fotocredencial/datos";
 	}
 	
 	@RequestMapping("datos_alumno/fotocredencial/centrar")
 	public String datosAlumnoFotoCredencialCentrar(HttpServletRequest request, HttpServletResponse response){		
 		PosFotoAlum foto 	= new PosFotoAlum();       
         
 		HttpSession sesion	= ((HttpServletRequest)request).getSession();
         if (sesion!=null){        	
         	try{
         		//List<Inscritos> lisInscritos = inscritosDao.getAlumnos(" ORDER BY CODIGO_PERSONAL");
         		List<AlumPersonal> lisAlumnos = alumPersonalDao.getListAll("ORDER BY CODIGO_PERSONAL");
         		int row = 0; 
         		for (AlumPersonal alumno : lisAlumnos){
         			row++;
         			if ((row%1000)==0) System.out.println("Complete:"+row);
         			String codigoPersonal = (String) sesion.getAttribute("codigoPersonal");
         			// Busca la imagen
         			boolean existeOriginal = false;
         			boolean existeCuadrada = false;
         			if (posFotoAlumDao.existeReg(alumno.getCodigoPersonal(), "O")){
         				foto 			= posFotoAlumDao.mapeaRegId(alumno.getCodigoPersonal(), "O");
         				existeOriginal	= true;
         				existeCuadrada 	= posFotoAlumDao.existeReg(alumno.getCodigoPersonal(), "C");
         			}else{
         				foto = posFotoAlumDao.mapeaRegId("usWhite", "O");
         			}
         			if (existeOriginal && existeCuadrada == false) {
         				ByteArrayInputStream fotoInput = new ByteArrayInputStream(foto.getFoto());
         				String contentType = URLConnection.guessContentTypeFromStream(fotoInput);
         			    BufferedImage imagenOriginal = ImageIO.read(fotoInput);        			      
                 		String tipoImagen = "jpg";
                 		if (contentType.equals("image/png")) 
                 			tipoImagen = "png";
                 		else if (contentType.equals("image/gif")) 
                 			tipoImagen = "gif";
                 		else if (contentType.equals("image/tiff")) 
                 			tipoImagen = "tiff";
 	        			
 	        			boolean horizontal = false;
 	        			int largo	= 0; 
 	        			int posX	= 0;
 	        			int posY	= 0;
 	        			if (imagenOriginal.getWidth() > imagenOriginal.getHeight()){ 
 	        				posX 	= (imagenOriginal.getWidth() - imagenOriginal.getHeight()) / 2;
 	        				posY	= 0;
 	        				horizontal = true;
 	        				largo 	= imagenOriginal.getHeight();
 	        			}
 	        			if (imagenOriginal.getWidth() < imagenOriginal.getHeight()) {
 	        				posX	= 0;
 	        				posY 	= (imagenOriginal.getHeight()-imagenOriginal.getWidth()) / 2;
 	        				largo 	= imagenOriginal.getWidth();
 	        			}	
 					
 	        			int posIni=0, posFin=0;				
 	        			int mediaPixel,colorSRGB;
 	        			Color colorAux;
 	        			//System.out.println("Datos:"+imagenOriginal.getWidth()+":"+imagenOriginal.getHeight()+" Superior:"+imagenOriginal.getHeight()/3);
 	        			for( int i = 0; i < imagenOriginal.getWidth(); i++ ){		            
 	        				//Almacenamos el color del píxel
 	        				colorAux=new Color(imagenOriginal.getRGB(i, imagenOriginal.getHeight()/3));
 	        				//Calculamos la media de los tres canales (rojo, verde, azul)
 	        				mediaPixel=(int)((colorAux.getRed()+colorAux.getGreen()+colorAux.getBlue())/3);
 	        				if (mediaPixel<200 && posIni == 0)
 	        					posIni = i;
 	        				else if (mediaPixel<200)
 	        					posFin = i;	                
 	        				//System.out.println(i+"="+mediaPixel);
 	        				//Cambiamos a formato sRGB
 	        				//colorSRGB=(mediaPixel << 16) | (mediaPixel << 8) | mediaPixel;
 	        				//Asignamos el nuevo valor al BufferedImage
 	        				//imageActual.setRGB(i, j,colorSRGB);		           
 	        			}		
 					
 						//System.out.println("Valores:"+posIni+" : "+posFin);
 						int posTemp = imagenOriginal.getWidth()-posFin;
 						int diferencia = 0;
 						if (horizontal && posTemp > posIni){
 							diferencia = (posTemp-posIni) /2;
 							posX = posX-diferencia;
 						}else if (horizontal && posTemp < posIni) {
 							diferencia = (posIni-posTemp) /2;
 							posX = posX+diferencia;
 						}	
 						if (posX < 0) posX=0;
 						if (posY < 0) posY=0;
 						System.out.println(alumno.getCodigoPersonal()+" Inicial:"+posX+" Final:"+(posX+imagenOriginal.getHeight()));
 						if (imagenOriginal.getWidth() != imagenOriginal.getHeight() && (posX+largo)<=imagenOriginal.getWidth() && posY+largo<=imagenOriginal.getHeight()) {
 							BufferedImage recorte = ((BufferedImage) imagenOriginal).getSubimage(posX, posY, largo, largo);
 							ByteArrayOutputStream baos = new ByteArrayOutputStream();
 							ImageIO.write(recorte, tipoImagen, baos);
 							baos.flush();
 							byte[] imagenRecortada = baos.toByteArray();
 							baos.close();
 							PosFotoAlum fotoCuadrada 	= new PosFotoAlum();
 							fotoCuadrada.setCodigoPersonal(alumno.getCodigoPersonal());
 							fotoCuadrada.setComentario("Cuadrada");
 							fotoCuadrada.setFecha(aca.util.Fecha.getHoy());
 							fotoCuadrada.setFoto(imagenRecortada);
 							fotoCuadrada.setRechazada("N");
 							fotoCuadrada.setTipo("C");
 							fotoCuadrada.setUsuario(codigoPersonal);
 							if (posFotoAlumDao.existeReg(alumno.getCodigoPersonal(), "C")){
 								posFotoAlumDao.updateReg(fotoCuadrada);
 							}else {
 								posFotoAlumDao.insertReg(fotoCuadrada);
 							}
 						}	
         			}					
         		}				
         	}catch(Exception ex){
         		System.out.println("Error /foto:"+ex);
         	}
         }
         return "redirect:/datos_alumno/fotocredencial/datos";
 	}
 	
 }