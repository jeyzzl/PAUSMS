package aca.web.credencialemp;

//import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import aca.cred.spring.CredVisitante;
import aca.cred.spring.CredVisitanteDao;
import aca.pg.archivo.spring.PosFotoAlum;
import aca.pg.archivo.spring.PosFotoAlumDao;

@Controller
public class ContCredencialEmpVisitantes {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("dsArchivo")
	private DataSource archivo;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	private PosFotoAlumDao posFotoAlumDao;
	
	@Autowired
	private CredVisitanteDao credVisitanteDao;
	
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
	
	@RequestMapping("/credencial_emp/visitantes/visitante")
	public String credencialEmpVisitantes(HttpServletRequest request, Model modelo) throws SQLException{
		
		List<aca.cred.spring.CredVisitante> lista = credVisitanteDao.getListAll("");
		List<String> conFoto = posFotoAlumDao.tieneFoto("V");
		
		modelo.addAttribute("lista", lista);
		modelo.addAttribute("conFoto", conFoto);
		
		return "credencial_emp/visitantes/visitante";
	}
	
	@RequestMapping("/credencial_emp/visitantes/subirFoto")
	public String credencialEmpVisitantesSubirFoto(HttpServletRequest request, Model modelo){		
		
		String codigoPersonal	= request.getParameter("CodigoPersonal")==null?"":request.getParameter("CodigoPersonal");
		
		modelo.addAttribute("codigoPersonal", codigoPersonal);
		
		return "credencial_emp/visitantes/subirFoto";
	}
	
	@RequestMapping("/credencial_emp/visitantes/guardaFoto")
	public String credencialEmpVisitantesGuardaFoto(@RequestParam("archivo") MultipartFile file, HttpServletRequest request, Model modelo){		
		
		String codigoVisitante	= request.getParameter("CodigoPersonal")==null?"":request.getParameter("CodigoPersonal");
		PosFotoAlum AlumFotoPos = new PosFotoAlum();
		
		HttpSession sesion	= null;
		if (request instanceof HttpServletRequest) {
            sesion = ((HttpServletRequest)request).getSession();   
        }
		if (sesion!=null){
			String usuario  		= (String) sesion.getAttribute("codigoPersonal");
			//String dir				= context.getRealPath("/WEB-INF/fotos/")+codigoVisitante+".jpg";
			
			try{				
				byte[] fotoByte = file.getBytes();
				/*
				// Escribir el archivo en el directorio del servidor de aplicaciones con el objeto FileOutputStream 
				FileOutputStream fos = new FileOutputStream(dir);
				fos.write(fotoByte,0,(int)file.getSize());
				fos.flush();
				fos.close();
				*/
				// Grabar la foto en postgres
				AlumFotoPos.setCodigoPersonal(codigoVisitante);
				AlumFotoPos.setFecha(aca.util.Fecha.getHoy());
				AlumFotoPos.setFoto(fotoByte);
				AlumFotoPos.setTipo("V");
				AlumFotoPos.setUsuario(usuario);
				if (posFotoAlumDao.existeReg(codigoVisitante, "V")){
					posFotoAlumDao.updateReg(AlumFotoPos);
				}else{
					posFotoAlumDao.insertReg(AlumFotoPos);
				}
				
			}catch( Exception e) {
	            e.printStackTrace();
			}	
		}	
		
		modelo.addAttribute("codigoPersonal", codigoVisitante);
		
		return "credencial_emp/visitantes/subirFoto";
	}
	
	@RequestMapping("/credencial_emp/visitantes/nuevo")
	public String credencialEmpVisitantesNuevo(HttpServletRequest request, Model modelo) throws SQLException{
		
		String codigoPersonal		= request.getParameter("CodigoPersonal")==null?"":request.getParameter("CodigoPersonal");
		String generoCodigo     	= credVisitanteDao.generaCodigoVisitante(aca.util.Fecha.getHoy());
		List<String> conFoto		= posFotoAlumDao.tieneFoto("V");
		
		CredVisitante visitante 	= new CredVisitante();
		if (credVisitanteDao.existeReg(codigoPersonal)){
			visitante = credVisitanteDao.mapeaRegId(codigoPersonal);
		}
		
		modelo.addAttribute("conFoto", conFoto);
		modelo.addAttribute("generoCodigo", generoCodigo);
		modelo.addAttribute("visitante", visitante);
		
		return "credencial_emp/visitantes/agregar";
	}
	
	@RequestMapping("/credencial_emp/visitantes/agregar")
	public String credencialEmpVisitantesAgregar(HttpServletRequest request, Model modelo) throws SQLException{		
		
		String mensaje			= "X";
		String colorMensaje		= "";
		
		String accion			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		List<String> conFoto 	= posFotoAlumDao.tieneFoto("V");
		
		String codigoPersonal	= request.getParameter("CodigoPersonal")==null?"":request.getParameter("CodigoPersonal");
		String nombre 			= request.getParameter("Nombre")==null?"":request.getParameter("Nombre");
		String paterno 			= request.getParameter("Paterno")==null?"":request.getParameter("Paterno");
		String materno 			= request.getParameter("Materno")==null?"":request.getParameter("Materno");
		String rfid 			= request.getParameter("Rfid")==null?"":request.getParameter("Rfid");
		String rfidTag 			= request.getParameter("RfidTag")==null?"":request.getParameter("RfidTag");
		String estado 			= request.getParameter("Estado")==null?"":request.getParameter("Estado");
		String comentario 		= request.getParameter("Comentario")==null?"":request.getParameter("Comentario");
		
		CredVisitante visitante = new CredVisitante();
		
		switch(accion){
		case "1": //Grabar
			visitante.setCodigoPersonal(codigoPersonal);
			visitante.setNombre(nombre);
			visitante.setPaterno(paterno);
			visitante.setMaterno(materno);
			visitante.setRfid(rfid);
			visitante.setRfidTag(rfidTag);
			visitante.setEstado(estado);
			visitante.setFecha(aca.util.Fecha.getHoy());
			visitante.setComentario(comentario);
			
				if(!credVisitanteDao.existeReg(codigoPersonal)){
					if(credVisitanteDao.insertReg(visitante)){
						mensaje 		= "Guardado";
						colorMensaje	= "success";
					}else{
						mensaje 		= "No guardo";
						colorMensaje	= "danger";
					}
				}
			break;
		case "2" : //Modificar
			visitante.setCodigoPersonal(codigoPersonal);
			visitante.setNombre(nombre);
			visitante.setPaterno(paterno);
			visitante.setMaterno(materno);
			visitante.setRfid(rfid);
			visitante.setRfidTag(rfidTag);
			visitante.setEstado(estado);
			visitante.setFecha(aca.util.Fecha.getHoy());
			visitante.setComentario(comentario);
				if(credVisitanteDao.existeReg(codigoPersonal)){
					if(credVisitanteDao.updateReg(visitante)){
						mensaje 		= "Modificado";
						colorMensaje	= "success";
					}else{
						mensaje 		= "No modifico";
						colorMensaje	= "danger";
					}
				}
			break;
		}
		
		modelo.addAttribute("conFoto", conFoto);
		modelo.addAttribute("visitante", visitante);
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("colorMensaje", colorMensaje);
		
		return "credencial_emp/visitantes/agregar";
	}
	
	@RequestMapping("/credencial_emp/visitantes/eliminar")
	public String credencialEmpVisitantesEliminar(HttpServletRequest request, Model modelo) throws Exception{		
		String codigoPersonal	= request.getParameter("CodigoPersonal")==null?"":request.getParameter("CodigoPersonal");
		String tipo				= request.getParameter("Tipo")==null?"":request.getParameter("Tipo");
		
		if(credVisitanteDao.deleteReg(codigoPersonal)) {
			posFotoAlumDao.deleteReg(codigoPersonal, tipo);
		}

		List<aca.cred.spring.CredVisitante> lista = credVisitanteDao.getListAll("");
		List<String> conFoto = posFotoAlumDao.tieneFoto(tipo);
		
		modelo.addAttribute("lista", lista);
		modelo.addAttribute("conFoto", conFoto);
		
		return "credencial_emp/visitantes/visitante";
	}
	
	@RequestMapping("/credencial_emp/visitantes/borrar")
	public String credencialEmpVisitantesBorrar(HttpServletRequest request, Model modelo) throws SQLException{
		String codigoPersonal 	= request.getParameter("CodigoPersonal")==null?"0":request.getParameter("CodigoPersonal");
		String tipo				= request.getParameter("Tipo")==null?"V":request.getParameter("Tipo");		
		HttpSession sesion		= null;
		
		if (request instanceof HttpServletRequest) {
            sesion = ((HttpServletRequest)request).getSession();   
        }
		if (sesion!=null){
			// BORRAR FOTO EN BASE DE DATOS
			try{			
				// Busca la imagen
				if (posFotoAlumDao.existeReg(codigoPersonal, tipo)){
					posFotoAlumDao.deleteReg(codigoPersonal, tipo);
				}
			}catch(Exception ex){
				System.out.println("Error:/fotoBorrar"+ex);
				
			}			
		}
		
		return "redirect:/credencial_emp/visitantes/nuevo?CodigoPersonal="+codigoPersonal;
	}	
}