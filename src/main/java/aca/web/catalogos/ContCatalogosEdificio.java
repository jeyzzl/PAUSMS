package aca.web.catalogos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.carga.spring.CargaGrupoHoraDao;
import aca.catalogo.spring.CatEdificio;
import aca.catalogo.spring.CatEdificioUsuario;
import aca.catalogo.spring.CatEdificioDao;
import aca.catalogo.spring.CatEdificioUsuarioDao;
import aca.catalogo.spring.CatSalon;
import aca.catalogo.spring.CatSalonDao;
import aca.vista.spring.Maestros;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContCatalogosEdificio {	
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	private CatSalonDao catSalonDao;
	
	@Autowired
	CatEdificioDao catEdificioDao;
	
	@Autowired
	CatEdificioUsuarioDao catEdificioUsuarioDao;
	
	@Autowired
	MaestrosDao maestrosDao;
	
	@Autowired
	CargaGrupoHoraDao cargaGrupoHoraDao;
	
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
			//System.out.println("Abrir conEnoc"+url);
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/catalogos/edificios/edificios")
	public String catalogoEdificiosEdificios(HttpServletRequest request, Model model){
		
		List<CatEdificio> lisEdificios 			= catEdificioDao.lisTodos("ORDER BY EDIFICIO_ID");
		HashMap<String, String> mapaSalones 	= catSalonDao.mapaSalones();
		HashMap<String, String> mapaUsuarios 	= catEdificioUsuarioDao.mapaPorEdificio();
				
		model.addAttribute("lisEdificios", lisEdificios);
		model.addAttribute("mapaSalones", mapaSalones);
		model.addAttribute("mapaUsuarios", mapaUsuarios);

		return "catalogos/edificios/edificios";  
	}
	
	@RequestMapping("/catalogos/edificios/editarEdificio")
	public String catalogoEdificiosEditarEdificio(HttpServletRequest request, Model modelo) {		
		
		String edificioId 			= request.getParameter("EdificioId")==null?"0":request.getParameter("EdificioId");	
		CatEdificio catEdificio 	= new CatEdificio();
		if (catEdificioDao.existeReg(edificioId)) {
			catEdificio = catEdificioDao.mapeaRegId(edificioId);
		}
		modelo.addAttribute("catEdificio", catEdificio);
		
		return "catalogos/edificios/editarEdificio";
	}
	
	@RequestMapping("/catalogos/edificios/editarUsuarios")
	public String catalogoEdificiosEditarUsuarios(HttpServletRequest request, Model model){
		
		String edificioId		= request.getParameter("EdificioId")==null?"0":request.getParameter("EdificioId");
		
		List<String> lisUsuarios 				= catEdificioUsuarioDao.lisUsuarios(edificioId, "ORDER BY CODIGO_PERSONAL");
		HashMap<String,String> mapaEnEdificios 	= maestrosDao.mapaEnEdificios("NOMBRE");
		
		List<Maestros> lisMaestros = maestrosDao.getListAll(" ORDER BY APELLIDO_PATERNO");

		model.addAttribute("lisUsuarios", lisUsuarios);
		model.addAttribute("mapaEnEdificios", mapaEnEdificios);
		model.addAttribute("lisMaestros", lisMaestros);
		
		return "catalogos/edificios/editarUsuarios";
	}
	
	@RequestMapping("/catalogos/edificios/borrarUsuario")
	public String catalogoEdificiosBorrarUsuario(HttpServletRequest request, Model modelo){
		
		String edificioId		= request.getParameter("EdificioId")==null?"0":request.getParameter("EdificioId");		
		String codigoPersonal	= request.getParameter("CodigoPersonal")==null?"0":request.getParameter("CodigoPersonal");
		String usuarios			= "-";
		String mensaje 			= "-";				
		if (catEdificioUsuarioDao.existeReg(edificioId, codigoPersonal)){			
			if (catEdificioUsuarioDao.deleteReg(edificioId, codigoPersonal)){
				mensaje = "1";			
			}else{
				mensaje = "2";
			}					
		}		
		return "redirect:/catalogos/edificios/editarUsuarios?EdificioId="+edificioId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/catalogos/edificios/grabarUsuario")
	public String catalogoEdificiosGrabarUsuario(HttpServletRequest request, Model modelo) {
		
		String edificioId			= request.getParameter("EdificioId")==null?"0":request.getParameter("EdificioId");
		String maestroId			= request.getParameter("MaestroId")==null?"-":request.getParameter("MaestroId");
		String mensaje 				= "-";
		
		CatEdificioUsuario catEdificioUsuario = new CatEdificioUsuario();
		
		catEdificioUsuario.setCodigo_personal(maestroId);
		catEdificioUsuario.setEdificioId(edificioId);
		
		if (catEdificioUsuarioDao.existeReg(edificioId, maestroId)==false) {	
			if (catEdificioUsuarioDao.insertReg(catEdificioUsuario)) {
				mensaje = "1";			
			}else{
				mensaje = "2";
			}				
		}		
		return "redirect:/catalogos/edificios/editarUsuarios?EdificioId="+edificioId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/catalogos/edificios/grabarEdificio")
	public String catalogoEdificiosGrabarNombre (HttpServletRequest request, Model modelo) {
		
		String edificioId 		= request.getParameter("EdificioId")==null?"0":request.getParameter("EdificioId");
		String nombreEdificio 	= request.getParameter("NombreEdificio")==null?"-":request.getParameter("NombreEdificio");
		String mensaje 			= "-";
		
		CatEdificio catEdificio	= new CatEdificio();		
		catEdificio.setEdificioId(edificioId);
		catEdificio.setNombreEdificio(nombreEdificio);
		
		if (catEdificioDao.existeReg(edificioId)) {		
			if (catEdificioDao.updateReg(catEdificio)) {
				mensaje = "Update done!";			
			}else{
				mensaje = "a problem occurred while updating";
			}
		}else{
			edificioId = catEdificioDao.maximoReg();
			catEdificio.setEdificioId(edificioId);
			if (catEdificioDao.insertReg(catEdificio)) {
				mensaje = "Inserted data!";			
			}else{
				mensaje = "A problem occurred while inserting data!";
			}				
		}		
		
		return "redirect:/catalogos/edificios/editarEdificio?EdificioId="+edificioId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/catalogos/edificios/borrarEdificio")
	public String catalogoEdificiosBorrarEdificio(HttpServletRequest request, Model modelo) {		
		
		String edificioId 		= request.getParameter("EdificioId")==null?"0":request.getParameter("EdificioId");
		if (catEdificioDao.existeReg(edificioId)) {
			catEdificioDao.deleteReg(edificioId);
		}
		
		return "redirect:/catalogos/edificios/edificios";
	}
	
	@RequestMapping("/catalogos/edificios/salones")
	public String catalogoEdificiosSalones(HttpServletRequest request, Model model){
		
		String edificioId 		= request.getParameter("EdificioId")==null?"0":request.getParameter("EdificioId");
		
		List<CatSalon> lisSalones 			= catSalonDao.getListAll(" ORDER BY NOMBRE_SALON");
		HashMap<String, String> mapaSalones = cargaGrupoHoraDao.mapaSalones();
		
		model.addAttribute("lisSalones", lisSalones);		
		model.addAttribute("mapaSalones", mapaSalones);
		
		return "catalogos/edificios/salones";
	}
	
	@RequestMapping("/catalogos/edificios/editarSalon")
	public String catalogoEdificiosEditarSalon(HttpServletRequest request, Model modelo) {		
		
		String edificioId 		= request.getParameter("EdificioId")==null?"0":request.getParameter("EdificioId");
		String salonId 			= request.getParameter("SalonId")==null?"0":request.getParameter("SalonId");
		
		CatSalon salon 			= new CatSalon();
		if (catSalonDao.existeReg(salonId)) {
			salon = catSalonDao.mapeaRegId(salonId);
		}
		
		modelo.addAttribute("salon", salon);
		
		return "catalogos/edificios/editarSalon";
	}
	
	@RequestMapping("/catalogos/edificios/grabarSalon")
	public String catalogoEdificiosGrabarSalon(HttpServletRequest request, Model modelo) {
		
		String edificioId		= request.getParameter("EdificioId")==null?"0":request.getParameter("EdificioId");
		String salonId			= request.getParameter("SalonId")==null?"0":request.getParameter("SalonId");
		String nombreSalon		= request.getParameter("NombreSalon")==null?"-":request.getParameter("NombreSalon");
		String numAlumnos		= request.getParameter("NumAlumnos")==null?"0":request.getParameter("NumAlumnos");
		String estado			= request.getParameter("Estado")==null?"-0":request.getParameter("Estado");
		String mensaje 			= "-";
		
		CatSalon salon 			= new CatSalon();
		salon.setEdificioId(edificioId);
		salon.setNombreSalon(nombreSalon);
		salon.setNumAlumnos(numAlumnos);
		salon.setEstado(estado);
		
		if (catSalonDao.existeReg(salonId)){
			salon.setSalonId(salonId);
			if(catSalonDao.updateReg(salon)) {
				mensaje = "1";
			}else {
				mensaje = "2";
			}
		}else {
			salon.setSalonId(edificioId+"-"+catSalonDao.maximoReg(edificioId));
			if(catSalonDao.insertReg(salon)) {
				mensaje = "1";
			}else {
				mensaje = "2";
			}
		}
		
		return "redirect:/catalogos/edificios/editarSalon?EdificioId="+salon.getEdificioId()+"&SalonId="+salon.getSalonId()+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/catalogos/edificios/borrarSalon")
	public String catalogoEdificiosBorrarSalon(HttpServletRequest request, Model modelo) {		
		
		String edificioId 		= request.getParameter("EdificioId")==null?"0":request.getParameter("EdificioId");
		String salonId			= request.getParameter("SalonId")==null?"0":request.getParameter("SalonId");
		if (catSalonDao.existeReg(salonId)) {
			catSalonDao.deleteReg(salonId);
		}
		
		return "redirect:/catalogos/edificios/salones?EdificioId="+edificioId;
	}
	
	@RequestMapping("/catalogos/edificios/materiasporsalon")
	public String catalogoEdificiosMateriasPorSalon(HttpServletRequest request, Model modelo) {		
		
		enviarConEnoc(request,"Error-aca.ContCatalogosEdificio|catalogoEdificiosMateriasPorSalon");
		
		return "catalogos/edificios/materiasporsalon";
	}
}