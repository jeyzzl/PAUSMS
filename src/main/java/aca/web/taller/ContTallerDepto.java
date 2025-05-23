package aca.web.taller;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.bec.spring.BecAcceso;
import aca.bec.spring.BecAccesoDao;
import aca.bec.spring.BecPeriodo;
import aca.bec.spring.BecPeriodoDao;
import aca.bec.spring.BecPlazas;
import aca.bec.spring.BecPlazasDao;
import aca.bec.spring.BecPuestoAlumnoDao;
import aca.financiero.spring.ContCcosto;
import aca.financiero.spring.ContCcostoDao;
import aca.financiero.spring.ContEjercicio;
import aca.financiero.spring.ContEjercicioDao;
import aca.vista.spring.UsuariosDao;

@Controller
public class ContTallerDepto {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	private ContEjercicioDao conEjercicioDao;	
	
	@Autowired
	private BecPeriodoDao becPeriodoDao;
	
	@Autowired
	private ContCcostoDao contCcostoDao;
	
	@Autowired
	private BecAccesoDao becAccesoDao;
	
	@Autowired
	private BecPlazasDao becPlazasDao;
	
	@Autowired
	private BecPuestoAlumnoDao becPuestoAlumnoDao;
	
	@Autowired
	private UsuariosDao usuariosDao;
	
	@Autowired
	aca.vista.spring.MaestrosDao maestrosDao;
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	} 
	
	@RequestMapping("/taller/depto/depaccion")
	public String tallerDeptoDepaccion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContTallerDepto|tallerDeptoDepaccion:");
		return "taller/depto/depaccion";
	}	
	
	@RequestMapping("/taller/depto/depto")
	public String tallerDeptoDepto(HttpServletRequest request, Model modelo){
		
		String ejercicioId 			= request.getParameter("idEjercicio")==null?"0":request.getParameter("idEjercicio");
		String periodoId 			= request.getParameter("periodoBecas")==null?"0":request.getParameter("periodoBecas");
		String filtro				= request.getParameter("Filtro")==null?"1.01":request.getParameter("Filtro");
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion != null){			
			if(!ejercicioId.equals("0")){
				sesion.setAttribute("ejercicioId", ejercicioId);
			}else {
				ejercicioId				= (String)sesion.getAttribute("ejercicioId");
			}			
			if(!periodoId.equals("0")){
				sesion.setAttribute("periodoBecas", periodoId);
			}else {
				periodoId				= (String)sesion.getAttribute("periodoBecas");
			}			
		}
		
		List<ContEjercicio> lisEjercicios 		= conEjercicioDao.lisTodos(" ORDER BY ID_EJERCICIO DESC");
		if (ejercicioId==null && lisEjercicios.size() > 0) {
			ejercicioId = lisEjercicios.get(0).getIdEjercicio();
		}
		
		List<BecPeriodo> lisPeriodos 			= becPeriodoDao.getAllActivos(ejercicioId, " ORDER BY PERIODO_ID DESC");
		
		boolean encontro = false;
		for(BecPeriodo periodo : lisPeriodos){
			if(periodo.getPeriodoId().equals(periodoId)){
				encontro = true; break;
			}
		}
		if(encontro == false && lisPeriodos.size()>0){
			periodoId = lisPeriodos.get(0).getPeriodoId();
			sesion.setAttribute("periodoBecas", periodoId);
		}
		
		List<ContCcosto> lisDepartamentos				= contCcostoDao.getListAcceso(ejercicioId,"S",filtro," ORDER BY ID_CCOSTO");
		HashMap<String, String> mapaAccesos 			= becAccesoDao.getAccesoDepto(ejercicioId);
		HashMap <String,BecPlazas> mapaPlazas			= becPlazasDao.getBecPlazas(ejercicioId, periodoId);
		
		modelo.addAttribute("ejercicioId", ejercicioId);
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("lisEjercicios", lisEjercicios);
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("lisDepartamentos", lisDepartamentos);
		modelo.addAttribute("mapaAccesos", mapaAccesos);
		modelo.addAttribute("mapaPlazas", mapaPlazas);
		
		return "taller/depto/depto";
	}
	
	@RequestMapping("/taller/depto/plazas")
	public String tallerDeptoPlazas(HttpServletRequest request, Model modelo){
		
		String ejercicioId 			= "0";
		String periodoId 			= "0";
		String ccosto				= request.getParameter("Ccosto")==null?"0":request.getParameter("Ccosto");
		HttpSession sesion 			= ((HttpServletRequest)request).getSession();
		if (sesion != null){			
			ejercicioId				= (String)sesion.getAttribute("ejercicioId");
			periodoId				= (String)sesion.getAttribute("periodoBecas");	
		}
		String ccostoNombre 		= contCcostoDao.getNombre(ejercicioId, ccosto);
		String periodoNombre 		= becPeriodoDao.getPeriodoNombre(periodoId);
		
		BecPlazas becPlazas 		= new BecPlazas();
		if (becPlazasDao.existeReg(ejercicioId, ccosto, periodoId)) {
			becPlazas 				= becPlazasDao.mapeaRegId(ejercicioId, ccosto, periodoId);
		}
		
		int numBas		= Integer.parseInt(becPuestoAlumnoDao.getNumPuestosAlumnosActivos(ccosto , "B", ejercicioId, periodoId));
		int numInd		= Integer.parseInt(becPuestoAlumnoDao.getNumPuestosAlumnosActivos(ccosto , "I", ejercicioId, periodoId));
		int numTem 		= Integer.parseInt(becPuestoAlumnoDao.getNumPuestosAlumnosActivos(ccosto, "T", ejercicioId, periodoId));
		int numPre 		= Integer.parseInt(becPuestoAlumnoDao.getNumPuestosAlumnosActivos(ccosto , "P", ejercicioId, periodoId));
		int numPos 		= Integer.parseInt(becPuestoAlumnoDao.getNumPuestosAlumnosActivos(ccosto , "M", ejercicioId, periodoId));
		
		modelo.addAttribute("becPlazas", becPlazas);
		modelo.addAttribute("ccostoNombre", ccostoNombre);
		modelo.addAttribute("periodoNombre", periodoNombre);
		modelo.addAttribute("numBas", numBas);
		modelo.addAttribute("numInd", numInd);		
		modelo.addAttribute("numTem", numTem);
		modelo.addAttribute("numPre", numPre);
		modelo.addAttribute("numPos", numPos);
		
		return "taller/depto/plazas";
	}
	
	@RequestMapping("/taller/depto/grabar")
	public String tallerDeptoGrabar(HttpServletRequest request, Model modelo){
		
		String ejercicioId 			= "0";
		String periodoId 			= "0";
		String ccosto				= request.getParameter("Ccosto")==null?"0":request.getParameter("Ccosto");
		HttpSession sesion 			= ((HttpServletRequest)request).getSession();
		if (sesion != null){			
			ejercicioId				= (String)sesion.getAttribute("ejercicioId");
			periodoId				= (String)sesion.getAttribute("periodoBecas");	
		}
		String mensaje 				= "-";
		
		String numPlazas			= request.getParameter("NumBasicas")==null?"0":request.getParameter("NumBasicas");
		String numIndustriales		= request.getParameter("NumIndustriales")==null?"0":request.getParameter("NumIndustriales");
		String numTemporales		= request.getParameter("NumTemporales")==null?"0":request.getParameter("NumTemporales");
		String numPreindustriales	= request.getParameter("NumPreindustriales")==null?"0":request.getParameter("NumPreindustriales");
		String numPosgrado			= request.getParameter("NumPosgrado")==null?"0":request.getParameter("NumPosgrado");
		String contacto				= request.getParameter("Contacto")==null?"0":request.getParameter("Contacto");
		String telefono				= request.getParameter("Telefono")==null?"0":request.getParameter("Telefono");
		String correo				= request.getParameter("Correo")==null?"0":request.getParameter("Correo");
		
		
		
		int numBas		= Integer.parseInt(becPuestoAlumnoDao.getNumPuestosAlumnosActivos(ccosto , "B", ejercicioId, periodoId));
		int numInd		= Integer.parseInt(becPuestoAlumnoDao.getNumPuestosAlumnosActivos(ccosto , "I", ejercicioId, periodoId));
		int numTem 		= Integer.parseInt(becPuestoAlumnoDao.getNumPuestosAlumnosActivos(ccosto, "T", ejercicioId, periodoId));
		int numPre 		= Integer.parseInt(becPuestoAlumnoDao.getNumPuestosAlumnosActivos(ccosto , "P", ejercicioId, periodoId));
		int numPos 		= Integer.parseInt(becPuestoAlumnoDao.getNumPuestosAlumnosActivos(ccosto , "M", ejercicioId, periodoId));
		
		
		BecPlazas becPlazas 		= new BecPlazas();
		becPlazas.setIdEjercicio(ejercicioId);
		becPlazas.setPeriodoId(periodoId);
		becPlazas.setIdCcosto(ccosto);
		becPlazas.setContacto(contacto);
		becPlazas.setTelefono(telefono);
		becPlazas.setCorreo(correo);
		
		becPlazas.setNumPlazas(numPlazas);
		becPlazas.setNumIndustriales(numIndustriales);
		becPlazas.setNumTemporales(numTemporales);
		becPlazas.setNumPreIndustriales(numPreindustriales);
		becPlazas.setNumPosgrado(numPosgrado);
		
		if(becPlazasDao.existeReg(ejercicioId, ccosto, periodoId)==false){
			if(becPlazasDao.insertReg(becPlazas)){
				mensaje = "1";
			}else{
				mensaje = "2";
			}
		}else{

			if( Integer.parseInt(becPlazas.getNumPlazas()) < numBas ){
				mensaje = "3";
			} else if( Integer.parseInt(becPlazas.getNumIndustriales()) < numInd ){
				mensaje = "4";
			} else if( Integer.parseInt(becPlazas.getNumTemporales()) < numTem ){
				mensaje = "5";
			} else if( Integer.parseInt(becPlazas.getNumPreIndustriales()) < numPre ){
				mensaje = "6";
			}else if( Integer.parseInt(becPlazas.getNumPosgrado()) < numPos ){
				mensaje = "7";  
			}else{
				if(becPlazasDao.updateReg( becPlazas)){
					mensaje = "1";
				}else{
					mensaje = "2";
				}	
			}			
		}
		
		return "redirect:/taller/depto/plazas?Ccosto="+ccosto+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/taller/depto/acceso")
	public String tallerDeptoAcceso(HttpServletRequest request, Model modelo){
		String ejercicioId 		= "0";
		String periodoId 		= "0";
		String ccosto			= request.getParameter("Ccosto")==null?"0":request.getParameter("Ccosto");
		String periodoNombre	= "-";
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion != null){			
			ejercicioId 		= (String)sesion.getAttribute("ejercicioId");
			periodoId			= (String)sesion.getAttribute("periodoBecas");			
			periodoNombre 		= becPeriodoDao.getPeriodoNombre(periodoId); 
		}	
		
		String ccostoNombre 					= contCcostoDao.getNombre(ejercicioId, ccosto);		
		List<BecAcceso> lisAccesos 				= becAccesoDao.getListDepartamento(ejercicioId, ccosto," ORDER BY ENOC.EMP_APELLIDO(CODIGO_PERSONAL)");
		HashMap<String,String> mapaMaestros 	= maestrosDao.mapMaestroNombre("NOMBRE");		
		
		modelo.addAttribute("periodoNombre", periodoNombre);
		modelo.addAttribute("ccostoNombre", ccostoNombre);
		modelo.addAttribute("lisAccesos", lisAccesos);
		modelo.addAttribute("mapaMaestros", mapaMaestros);
		modelo.addAttribute("mapaMaestros", mapaMaestros);		
		
		return "taller/depto/acceso";
	}
	
	@RequestMapping("/taller/depto/grabaracceso")
	public String tallerDeptoGrabarAcceso(HttpServletRequest request, Model modelo){
		
		String ejercicioId 			= "0";
		String usuarioAlta			= "0";
		String codigoPersonal		= request.getParameter("CodigoPersonal")==null?"0":request.getParameter("CodigoPersonal");
		String ccosto				= request.getParameter("Ccosto")==null?"0":request.getParameter("Ccosto");
		String mensaje 				= "0";
		HttpSession sesion 			= ((HttpServletRequest)request).getSession();
		if (sesion != null){			
			ejercicioId				= (String)sesion.getAttribute("ejercicioId");
			usuarioAlta 			= (String)sesion.getAttribute("codigoPersonal");
		}
		BecAcceso becAcceso = new BecAcceso();
		becAcceso.setCodigoPersonal(codigoPersonal);
		becAcceso.setIdEjercicio(ejercicioId);
		becAcceso.setIdCcosto(ccosto);
		becAcceso.setFecha(aca.util.Fecha.getHoy());
		becAcceso.setUsuario(usuarioAlta);
		if(maestrosDao.existeReg( codigoPersonal)){//Si existe el empleado que estás metiendo
			if(becAccesoDao.existeReg( codigoPersonal, ejercicioId, ccosto)){//Si existe el empleado en la tabla de privilegios
				mensaje = "2";				
			}else{
				becAccesoDao.insertReg(becAcceso);
				mensaje = "1";
			}	
		}else{
			mensaje = "3";
		}
		
		return "redirect:/taller/depto/acceso?Ccosto="+ccosto+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/taller/depto/borraracceso")
	public String tallerDeptoBorrarAcceso(HttpServletRequest request){
		
		String ejercicioId 			= "0";
		String codigoPersonal		= request.getParameter("CodigoPersonal")==null?"0":request.getParameter("CodigoPersonal");		
		String ccosto				= request.getParameter("Ccosto")==null?"0":request.getParameter("Ccosto");
		String mensaje				= "";
		HttpSession sesion 			= ((HttpServletRequest)request).getSession();
		if (sesion != null){			
			ejercicioId				= (String)sesion.getAttribute("ejercicioId");
		}
		BecAcceso becAcceso = new BecAcceso();
		becAcceso.setCodigoPersonal(codigoPersonal);
		becAcceso.setIdEjercicio(ejercicioId);
		becAcceso.setIdCcosto(ccosto);
		if(becAccesoDao.existeReg(codigoPersonal, ejercicioId, ccosto)){//Si existe el empleado en la tabla de privilegios
			if (becAccesoDao.deleteReg(codigoPersonal, ejercicioId, ccosto)) {
				mensaje = "4";
			}							
		}		
		return "redirect:/taller/depto/acceso?Ccosto="+ccosto+"&Mensaje="+mensaje;
	}
	
}