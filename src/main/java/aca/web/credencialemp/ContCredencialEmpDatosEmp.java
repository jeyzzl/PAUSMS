package aca.web.credencialemp;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Base64;
import java.util.List;

import javax.imageio.ImageIO;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import aca.alumno.spring.AlumFotografia;
import aca.cred.spring.CredDependiente;
import aca.cred.spring.CredDependienteDao;
import aca.cred.spring.CredEmpleado;
import aca.cred.spring.CredEmpleadoDao;
import aca.emp.spring.EmpDatos;
import aca.emp.spring.EmpDatosDao;
import aca.emp.spring.EmpDependiente;
import aca.emp.spring.EmpDependienteDao;
import aca.emp.spring.EmpFoto;
import aca.emp.spring.EmpFotoDao;
import aca.emp.spring.Empleado;
import aca.emp.spring.EmpleadoDao;
import aca.emp.spring.EmpleadoDependientes;
import aca.emp.spring.EmpleadoDependientesDao;
import aca.pg.archivo.spring.PosFotoAlum;
import aca.pg.archivo.spring.PosFotoAlumDao;
import aca.vista.spring.Maestros;
import aca.vista.spring.MaestrosDao;
import net.glxn.qrgen.javase.QRCode;

@Controller
public class ContCredencialEmpDatosEmp {
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("dsArchivo")
	private DataSource archivo;	
	
	@Autowired
	private PosFotoAlumDao posFotoAlumDao;
	
	@Autowired
	private EmpDatosDao empDatosDao;
	
	@Autowired
	private EmpleadoDao empleadoDao;
	
	@Autowired
	private EmpleadoDependientesDao empleadoDependientesDao;
	
	@Autowired
	private EmpDependienteDao empDependienteDao;
	
	@Autowired
	private CredDependienteDao credDependienteDao;
	
	@Autowired
	private EmpFotoDao empFotoDao;
	
	@Autowired
	private CredEmpleadoDao credEmpleadoDoa;
	
	@Autowired
	MaestrosDao maestrosDao;
	
	
	@Autowired
	ServletContext context;	
	
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	public void enviarConArchivo(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conArchivo", archivo.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}

	@RequestMapping("/credencial_emp/datos_emp/tomarfoto")
	public String datosAlumnoFotoCredencialTomarFoto(HttpServletRequest request, Model modelo) {	
		
		String codigoEmpleado 	= "";		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoEmpleado 	   	= (String) sesion.getAttribute("codigoEmpleado");        	
        }

		Maestros maestro 		= maestrosDao.mapeaRegId(codigoEmpleado);
		
		modelo.addAttribute("maestro", maestro);
		
		return "credencial_emp/datos_emp/tomarfoto";
	}
	
	@PostMapping("/credencial_emp/datos_emp/addFoto")
	public String datosAlumnoFotoCredencialAddFoto(HttpServletRequest request){
		
		EmpFoto empFoto 		= new EmpFoto();
		PosFotoAlum fotoNormal 	= new PosFotoAlum();
		
		
		String codigoEmpleado	= "";
		String codigoPersonal 	= "";
		String fotoBase64 		= request.getParameter("foto")==null?"":request.getParameter("foto");
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoEmpleado		= (String)sesion.getAttribute("codigoEmpleado");
        	codigoPersonal  	= (String) sesion.getAttribute("codigoPersonal"); 
        }
        String mensaje 			= "-";
        
        try {
        	//byte[] imagen = Base64.getDecoder().decode(new String(fotoBase64).getBytes("UTF-8"));
        	byte[] fotoByte = Base64.getDecoder().decode(fotoBase64.split(",")[1]);
        	// Grabar la foto en postgres
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
        }catch(Exception ex){
			System.out.println(ex);
		}
        
        try {
        	byte[] fotoByte = Base64.getDecoder().decode(fotoBase64.split(",")[1]);			
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
			
			imagenOriginal= Scalr.resize(imagenOriginal, 1200, 900);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(imagenOriginal, tipoImagen, baos);
			baos.flush();
			byte[] imagenReducida = baos.toByteArray();
			baos.close();     
			
			fotoNormal.setCodigoPersonal(codigoEmpleado);
			fotoNormal.setTipo("O");
			fotoNormal.setFecha(aca.util.Fecha.getHoy());
			fotoNormal.setUsuario(codigoPersonal);
			fotoNormal.setFoto(imagenReducida);
			
			if (posFotoAlumDao.existeReg(codigoEmpleado, "O")){
				posFotoAlumDao.updateReg(fotoNormal);
			}else{
				posFotoAlumDao.insertReg(fotoNormal);
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.web.CredencialEmpDatosEmp|credencialEmpDatosEmpGuardar-AlumFoto|:"+ex);
			ex.printStackTrace();
		}
		
		return "redirect:/credencial_emp/datos_emp/tomarfoto?Mensaje="+mensaje;
	}
	
	@RequestMapping("/credencial_emp/datos_emp/tomarFotoDep")
	public String credencialEmpDatosEmpTomarFotoDep(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContCcredencialEmp|credencialEmpDatosEmpTomarFotoDep:");
		enviarConArchivo(request,"Error-aca.ControllerDatosAlumno|credencialEmpDatoDep:");		
		return "credencial_emp/datosEmp/tomarFotoDep";
	}
	
	@RequestMapping("/credencial_emp/datos_emp/dato_emp")
	public String credencialEmpDatosEmpDatoEmp(HttpServletRequest request, Model modelo){ 
		
		String codigoEmpleado 	= "0";
		String departamento		= "";
		String puesto 			= "";
		String mensaje 			= request.getParameter("Mensaje") == null ? "0" : request.getParameter("Mensaje");
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		EmpDatos empDatos 		= new EmpDatos();
		Empleado empleado 		= new Empleado();
		boolean existe			= false;
		boolean tieneFoto 		= false;
		if (sesion!=null){
			codigoEmpleado	= (String)sesion.getAttribute("codigoEmpleado");
			if (empDatosDao.existeReg(codigoEmpleado)) {
				existe = true;
				empDatos 		= empDatosDao.mapeaRegId(codigoEmpleado);
			}			
			empleado 		= empleadoDao.mapeaRegClave(codigoEmpleado);
			departamento 	= empDatosDao.getDepartamento(empDatos.getId());
			puesto		 	= empDatosDao.getPuesto(empDatos.getId());
			
			if(posFotoAlumDao.existeReg(codigoEmpleado, "O")){
				tieneFoto = true;
			}
		}
		
		List<EmpleadoDependientes> lisDependientes = empleadoDependientesDao.getLista(empDatos.getId(), " ORDER BY NOMBRE");
		
		modelo.addAttribute("existe", existe);
		modelo.addAttribute("tieneFoto", tieneFoto);
		modelo.addAttribute("empDatos", empDatos);
		modelo.addAttribute("empleado", empleado);
		modelo.addAttribute("departamento", departamento);
		modelo.addAttribute("puesto", puesto);
		modelo.addAttribute("lisDependientes", lisDependientes);
		modelo.addAttribute("mensaje", mensaje);
		
		return "credencial_emp/datos_emp/dato_emp";
	}
	
	@RequestMapping("/credencial_emp/datos_emp/qr")
	public ResponseEntity<byte[]> qr(HttpServletRequest request){
		
		String codigoEmpleado 	= "0";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		EmpDatos empDatos 		= new EmpDatos();
		Empleado empleado 		= new Empleado();
		if (sesion!=null){
			codigoEmpleado	= (String)sesion.getAttribute("codigoEmpleado");
			if (empDatosDao.existeReg(codigoEmpleado)) {
				empDatos 		= empDatosDao.mapeaRegId(codigoEmpleado);
			}			
			empleado 		= empleadoDao.mapeaRegClave(codigoEmpleado);
		}
		
//		String textoQR = codigoEmpleado+", "+empleado.getNombre()+" "+empleado.getAppaterno()+" "+empleado.getApmaterno()+", "+empDatos.getDepartamento()+", "+empDatos.getPuesto();
		String textoQR = "https://admision.um.edu.mx/admision/verificaQr?Codigo=123";
		
		byte[] bytes = QRCode.from(textoQR).withSize(120, 120).stream().toByteArray();
		
		 final HttpHeaders headers = new HttpHeaders();
		  headers.setContentType(MediaType.IMAGE_PNG);
		  headers.setContentLength(bytes.length);		  
		  return new ResponseEntity<byte[]> (bytes, headers, HttpStatus.CREATED); 
	}
	
	@RequestMapping("/credencial_emp/datos_emp/guardar")
	public String credencialEmpDatosEmpGuardar(@RequestParam("archivo") MultipartFile file, HttpServletRequest request) throws IOException{
		
		EmpFoto empFoto 		= new EmpFoto();
		PosFotoAlum fotoNormal 	= new PosFotoAlum();
		
		HttpSession sesion	= null;
		if (request instanceof HttpServletRequest){
            sesion = ((HttpServletRequest)request).getSession();   
        }
		if (sesion!=null){
			String codigoEmpleado	= (String) sesion.getAttribute("codigoEmpleado");
			String usuario  		= (String) sesion.getAttribute("codigoPersonal");
			try{
				byte[] fotoByte = file.getBytes();
				
				// Grabar la foto en postgres
				empFoto.setCodigoPersonal(codigoEmpleado);
				empFoto.setFolio("0");
				empFoto.setFecha(aca.util.Fecha.getHoy());
				empFoto.setFoto(fotoByte);
				empFoto.setUsuario(usuario);
				if (empFotoDao.existeReg(codigoEmpleado, "0")){
					empFotoDao.updateReg(empFoto);
				}else{
					empFotoDao.insertReg(empFoto);
				}

			}catch( Exception ex) {
				System.out.println("Error - aca.web.CredencialEmpDatosEmp|credencialEmpDatosEmpGuardar-EmpFoto|:"+ex);
	            ex.printStackTrace();
			}	
			
			try {				
				//InputStream fotoInput 	= new ByteArrayInputStream(file.getBytes());
				ByteArrayInputStream fotoInput = new ByteArrayInputStream(file.getBytes());
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
				
				fotoNormal.setCodigoPersonal(codigoEmpleado);
				fotoNormal.setTipo("O");
				fotoNormal.setFecha(aca.util.Fecha.getHoy());
				fotoNormal.setUsuario(usuario);
				fotoNormal.setFoto(imagenReducida);
				
				if (posFotoAlumDao.existeReg(codigoEmpleado, "O")){
					posFotoAlumDao.updateReg(fotoNormal);
				}else{
					posFotoAlumDao.insertReg(fotoNormal);
				}
			} catch (Exception ex) {
				System.out.println("Error - aca.web.CredencialEmpDatosEmp|credencialEmpDatosEmpGuardar-AlumFoto|:"+ex);
				ex.printStackTrace();
			}
		}		
		
		return "redirect:/credencial_emp/datos_emp/dato_emp";
	}
	
	@RequestMapping("/credencial_emp/datos_emp/borrar")
	public String borrarEmp(HttpServletRequest request, HttpServletResponse response){		
		String codigo 		= request.getParameter("Codigo")==null?"0":request.getParameter("Codigo");
		String folio		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");	
		String dir			= "";
		
		HttpSession sesion	= null;
		if (request instanceof HttpServletRequest) {
            sesion = ((HttpServletRequest)request).getSession();   
        }
		if (sesion!=null){
			
			// BORRAR LA IMAGEN EN DIRECTORIO WEB-INF/FOTOS
			if (folio.equals("0")){
				// Si es alumno o empleado
				dir				= context.getRealPath("/WEB-INF/fotos/")+codigo+".jpg";
			}else{
				// Si es dependiente o es segunda foto de alumno
				dir				= context.getRealPath("/WEB-INF/fotos/")+codigo+"-"+folio+".jpg";
			}			
			boolean borrar = false;
			java.io.File fileFoto = new java.io.File(dir);	
			int i=0;
			if (fileFoto.exists()){				
				// Ciclo para corregir error en sistemas operativos windows(No es necesario si el servidor de aplicaciones estÃ¡ en linux)
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
				if (empFotoDao.existeReg(codigo, folio)){
					if(empFotoDao.deleteReg(codigo, folio)) {
						if (posFotoAlumDao.existeReg(codigo, "O")){
							posFotoAlumDao.deleteReg(codigo, "O");
						}
					}
				}
			}catch(Exception ex){
				System.out.println("Error:/fotoBorrar"+ex);
				
			}			
		}		
		
		return "redirect:/credencial_emp/datos_emp/dato_emp";
	}
	
	@RequestMapping("/credencial_emp/datos_emp/subir")
	public String credencialEmpDatosSubir(HttpServletRequest request, Model modelo){
		
		String empleadoId 		= "0";		
		String nombreEmp 		= "-";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			empleadoId 		= (String) sesion.getAttribute("codigoEmpleado");
			nombreEmp 		= empleadoDao.getNombreEmpleado(empleadoId, "NOMBRE");
		}
		
		modelo.addAttribute("nombreEmp", nombreEmp);
		
		return "credencial_emp/datos_emp/subir";
	}
	
	@RequestMapping("/credencial_emp/datos_emp/add")
	public String credencialEmpDatosEmpAdd(HttpServletRequest request){
		
		String mensaje 			= "";
		String s_id				= request.getParameter("f_id");
		String s_id_empleado	= request.getParameter("f_empleado");
		String s_nombre			= request.getParameter("f_nombre");
		String nombreCredencial = request.getParameter("nombreCredencial");
		String s_apellidoP 		= request.getParameter("f_apellidoP");
		String s_apellidoM  	= request.getParameter("f_apellidoM");
		String s_puesto 		= request.getParameter("f_puesto");
		String s_depto 			= request.getParameter("f_depto");
		String s_status			= request.getParameter("f_activo");
		String s_coteja			= request.getParameter("f_cotejado");
		String s_tipocred		= request.getParameter("f_tipocred");
		
		//System.out.println("Datos:"+s_id+":"+s_id_empleado+":"+s_nombre+":"+nombreCredencial+":"+s_puesto+":"+s_status+":"+s_tipocred);
		
		String apellidos = s_apellidoP+" "+s_apellidoM;
		
		EmpDatos empDatos = new EmpDatos();
		
		empDatos.setId(s_id);
		empDatos.setIdEmpleado(s_id_empleado);
		empDatos.setNombre(nombreCredencial);
		empDatos.setApellidos(apellidos);
		empDatos.setPuesto(s_puesto);
		empDatos.setDepartamento(s_depto);
		empDatos.setStatus(s_status);
		empDatos.setCotejado(s_coteja);
		empDatos.setImpreso("N");
		empDatos.setTipocred(s_tipocred);
		
		Empleado empleado = new Empleado();
		
		empleado.setNombre(s_nombre);
		empleado.setAppaterno(s_apellidoP);
		empleado.setApmaterno(s_apellidoM);
		empleado.setClave(s_id_empleado);

		CredEmpleado credEmpleado = new CredEmpleado();
		
		credEmpleado.setId(s_id);
		credEmpleado.setClave(s_id_empleado);
		credEmpleado.setNombre(nombreCredencial);
		credEmpleado.setApellidos(apellidos);
		credEmpleado.setPuesto(s_puesto);
		credEmpleado.setDepartamento(s_depto);
		credEmpleado.setStatus(s_status);
		credEmpleado.setCotejado(s_coteja);
		credEmpleado.setImprimir("N");
		credEmpleado.setTipoCred(s_tipocred);
		
		if(!empDatosDao.existeReg(s_id_empleado)) {
			if(empDatosDao.insertReg(empDatos)) {
				empleadoDao.updateReg(empleado);
				mensaje = "1";
				
				if(credEmpleadoDoa.existeReg(s_id, s_id_empleado)){
					credEmpleadoDoa.updateReg(credEmpleado);
				}else {
					credEmpleadoDoa.insertReg(credEmpleado);
				}
			}
		}else { //MODIFICA DATOS	
			if(empDatosDao.updateReg(empDatos)) {
				empleadoDao.updateReg(empleado);
				mensaje = "2";
			
				if(credEmpleadoDoa.existeReg(s_id, s_id_empleado)){
					credEmpleadoDoa.updateReg(credEmpleado);
				}else {
					credEmpleadoDoa.insertReg(credEmpleado);
				}
			}
		}
		
		return "redirect:/credencial_emp/datos_emp/dato_emp?Mensaje="+mensaje;
	}
	
	@RequestMapping("/credencial_emp/datos_emp/dato_dep")
	public String credencialEmpDatosEmpDatoDep(HttpServletRequest request,  Model modelo){
		String idEmpleado 			= request.getParameter("f_empleado")==null?"0":request.getParameter("f_empleado");
		String folio				= request.getParameter("f_folio")==null?"0":request.getParameter("f_folio");
		
		Empleado empleado 						= new Empleado();
		EmpleadoDependientes aronDependiente 	= new EmpleadoDependientes();
		EmpDependiente enocDependiente 			= new EmpDependiente();
		CredDependiente	credDependiente			= new CredDependiente();
		
		String departamento 					= "0";
		String puesto							= "0";
		boolean existe							= false;
		boolean tieneFoto 						= false;	
		
		HttpSession sesion			= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			
			if (idEmpleado.equals("0")) {
				idEmpleado = (String)sesion.getAttribute("empleadoId");
			}else {
				sesion.setAttribute("empleadoId",idEmpleado);
			}
			if (folio.equals("0")) {
				folio = (String)sesion.getAttribute("dependienteId");
			}else {
				sesion.setAttribute("dependienteId", folio);
			}
			if (empleadoDao.existeRegId(idEmpleado)) {
				empleado 			= empleadoDao.mapeaRegId(idEmpleado);				
				existe				= true;
				departamento 		= empDatosDao.getDepartamento(empleado.getClave());
				puesto		 		= empDatosDao.getPuesto(empleado.getClave());
				enocDependiente 	= empDependienteDao.mapeaRegId(idEmpleado, folio);
				aronDependiente		= empleadoDependientesDao.mapeaRegId(idEmpleado, folio);			 
				credDependiente 	= credDependienteDao.mapeaRegId(empleado.getClave()+"-"+folio);
				if(posFotoAlumDao.existeReg(empleado.getClave(), folio)){
					tieneFoto = true;
				}
			}			
		}
		
		modelo.addAttribute("empleado", empleado);
		modelo.addAttribute("aronDependiente", aronDependiente);
		modelo.addAttribute("enocDependiente", enocDependiente);
		modelo.addAttribute("credDependiente", credDependiente);
		modelo.addAttribute("departamento", departamento);
		modelo.addAttribute("puesto", puesto);
		modelo.addAttribute("existe", existe);
		modelo.addAttribute("tieneFoto", tieneFoto);
		
		return "credencial_emp/datos_emp/dato_dep";
	}
	
	@RequestMapping("/credencial_emp/datos_emp/subirDep")
	public String credencialEmpDatosSubirDep(HttpServletRequest request, Model modelo){
		
		String empleadoId 		= "";
		String dependienteId 	= "";
		String nombreDep 		= "-";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			empleadoId 		= (String) sesion.getAttribute("empleadoId");
			dependienteId 	= (String) sesion.getAttribute("dependienteId");			
			nombreDep 		= empDependienteDao.getNombre(empleadoId, dependienteId);
		}
		
		modelo.addAttribute("nombreDep", nombreDep);
		
		return "credencial_emp/datos_emp/subirDep";
	}
	
	@RequestMapping("/credencial_emp/datos_emp/guardarDep")
	public String credencialEmpDatosEmpGuardarDep(@RequestParam("archivo") MultipartFile file, HttpServletRequest request){
		
		String codigoEmpleado	= "0";
		String dependienteId	= "0";
		String usuario  		= "0";
		
		EmpFoto empFoto 		= new EmpFoto();
		PosFotoAlum fotoNormal	= new PosFotoAlum();
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();        
		if (sesion!=null){
			codigoEmpleado		= (String) sesion.getAttribute("codigoEmpleado");
			dependienteId		= (String) sesion.getAttribute("dependienteId");
			usuario  			= (String) sesion.getAttribute("codigoPersonal");
			//String dir					= context.getRealPath("/WEB-INF/fotos/")+codigoEmpleado+".jpg";
			try{				
				byte[] fotoByte = file.getBytes();				
				// Grabar la foto en postgres
				empFoto.setCodigoPersonal(codigoEmpleado);
				empFoto.setFolio(dependienteId);
				empFoto.setFecha(aca.util.Fecha.getHoy());
				empFoto.setFoto(fotoByte);
				empFoto.setUsuario(usuario);
				if (empFotoDao.existeReg(codigoEmpleado, dependienteId)){
					empFotoDao.updateReg(empFoto);
				}else{
					empFotoDao.insertReg(empFoto);
				}
				
			}catch( Exception e) {
	            e.printStackTrace();
			}	
		
			try {
				InputStream fotoInput 	= new ByteArrayInputStream(empFoto.getFoto());
				BufferedImage imagenOriginal = ImageIO.read(fotoInput);
				imagenOriginal= Scalr.resize(imagenOriginal, 1200, 900);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(imagenOriginal, "jpg", baos);
				baos.flush();
				byte[] imagenReducida = baos.toByteArray();
				baos.close();     
				
				fotoNormal.setCodigoPersonal(codigoEmpleado);
				fotoNormal.setTipo(dependienteId);
				fotoNormal.setFecha(aca.util.Fecha.getHoy());
				fotoNormal.setUsuario(usuario);
				fotoNormal.setFoto(imagenReducida);
				
				if (posFotoAlumDao.existeReg(codigoEmpleado, dependienteId)){
					posFotoAlumDao.updateReg(fotoNormal);
				}else{
					posFotoAlumDao.insertReg(fotoNormal);
				}
			} catch (Exception e) {
				 e.printStackTrace();
			}
		}	
		
		return "redirect:/credencial_emp/datos_emp/dato_dep";
	}
	
	@RequestMapping("/credencial_emp/datos_emp/add_dep")
	public String credencialEmpDatosEmpAddDep(HttpServletRequest request){
		String idDep			= request.getParameter("f_dep");
		String idEmp			= request.getParameter("f_empleado");
		String nombre			= request.getParameter("f_nombre").trim();
		String apellido			= request.getParameter("f_apellido").trim();
		String fecha			= request.getParameter("f_fecha");
		String relacion			= request.getParameter("f_relacion");
		String cotejado			= request.getParameter("f_cotejado").toUpperCase();
		String folio 			= idDep.split("-")[1];
		String departamento 	= "-";
		String puesto		 	= "-";
		
		Empleado empleado 						= new Empleado();
		EmpleadoDependientes aronDependiente 	= new EmpleadoDependientes();
		EmpDependiente enocDependiente 			= new EmpDependiente();
		CredDependiente	credDep					= new CredDependiente();	
		
		if (empleadoDao.existeReg(idEmp)) {
			empleado 			= empleadoDao.mapeaRegId(idEmp);
			
			departamento 		= empDatosDao.getDepartamento(empleado.getClave());
			puesto		 		= empDatosDao.getPuesto(empleado.getClave());
			
			if (empDependienteDao.existeReg(idEmp, folio)) {
				// Update 
				enocDependiente = empDependienteDao.mapeaRegId(idEmp, folio);
				enocDependiente.setRelacion(relacion);
				enocDependiente.setCotejado(cotejado);
				empDependienteDao.updateReg(enocDependiente);
			}else {
				//insert
				enocDependiente = empDependienteDao.mapeaRegId(idEmp, folio);
				enocDependiente.setIdDependiente(idDep);
				enocDependiente.setIdEmpleado(idEmp);
				enocDependiente.setFolio(folio);
				enocDependiente.setRelacion(relacion);
				enocDependiente.setCotejado(cotejado);
				empDependienteDao.insertReg(enocDependiente);
			}
			
			if (empleadoDependientesDao.existeReg(idEmp, folio)) {
				aronDependiente = empleadoDependientesDao.mapeaRegId(idEmp, folio);
				aronDependiente.setBday(fecha);
				aronDependiente.setNombre(nombre+" "+apellido);
				empleadoDependientesDao.updateReg(aronDependiente);
			}
			
			// Actualizar datos de la credencial
			credDep.setIdDependiente(idDep);
			credDep.setIdEmpleado(empleado.getClave());
			credDep.setDepNombre(nombre);
			credDep.setDepApellidos(apellido);
			credDep.setEmpNombre(empleado.getNombre());
			credDep.setEmpApellidos(empleado.getAppaterno()+" "+empleado.getApmaterno());
			credDep.setRelacion(relacion);
			credDep.setDepartamento(departamento);
			credDep.setPuesto(puesto);
			credDep.setFecha(fecha);
			credDep.setCotejado(cotejado);
			credDep.setFolio(folio);
			if (credDependienteDao.existeReg(idDep)){				
				if(credDependienteDao.updateReg(credDep)){
				}
			}else{
				//insert
				if (credDependienteDao.insertReg(credDep)){
				}
			}
		}
						
		return "redirect://credencial_emp/datos_emp/dato_dep";
	}
	
	@RequestMapping("/credencial_emp/datos_emp/borrarDep")
	public String borrarDep(HttpServletRequest request, HttpServletResponse response){		
		String codigo 		= request.getParameter("Codigo")==null?"0":request.getParameter("Codigo");
		String folio		= request.getParameter("Folio")==null?"99":request.getParameter("Folio");		
		
		HttpSession sesion	= ((HttpServletRequest)request).getSession();		
		if (sesion!=null){
			
			// BORRAR FOTO EN BASE DE DATOS
			try{			
				// Busca la imagen
				if (empFotoDao.existeReg(codigo, folio)){
					if(empFotoDao.deleteReg(codigo, folio)) {
						if (posFotoAlumDao.existeReg(codigo, folio)){
							posFotoAlumDao.deleteReg(codigo, folio);
						}
					}
				}
			}catch(Exception ex){
				System.out.println("Error:/fotoBorrar"+ex);
			}			
		}
		
		return "redirect:/credencial_emp/datos_emp/dato_dep";
	}	
}