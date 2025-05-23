package aca.web.archivo;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumUbicacion;
import aca.alumno.spring.AlumUbicacionDao;
import aca.catalogo.spring.CatCiudad;
import aca.catalogo.spring.CatCiudadDao;
import aca.catalogo.spring.CatEstado;
import aca.catalogo.spring.CatEstadoDao;
import aca.catalogo.spring.CatPais;
import aca.catalogo.spring.CatPaisDao;
import aca.log.spring.LogAlumno;
import aca.log.spring.LogAlumnoDao;
import aca.vista.spring.MaestrosDao;


@Controller
public class ContArchivoCotejarAlumno {
	
	
	@Autowired	
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired	
	private AlumUbicacionDao alumUbicacionDao;
	
	@Autowired	
	private AccesoDao accesoDao;
	
	@Autowired	
	private CatPaisDao catPaisDao;
	
	@Autowired	
	private CatEstadoDao catEstadoDao;
	
	@Autowired	
	private CatCiudadDao catCiudadDao;
	
	@Autowired	
	private LogAlumnoDao logAlumnoDao;
	
	@Autowired
	MaestrosDao maestrosDao;
	
	
	@RequestMapping("/archivo/cotejar_alumno/cotejar")
	public String archivoCotejarAlumnoCotejar(HttpServletRequest request, Model modelo){		
		
		String codigoPersonal 		= request.getParameter("codigoPersonal");
		String codigoAlumno			= request.getParameter("codigoAlumno");
		String paisNombre			= "-";
		String estadoNombre			= "-";
		String ciudadNombre			= "-";
		String nacionNombre			= "-";
		String resultado			= "-";
		
		HttpSession session		= ((HttpServletRequest)request).getSession();
		if (session!=null){
			codigoPersonal		= (String) session.getAttribute("codigoPersonal");
			codigoAlumno 		= (String) session.getAttribute("codigoAlumno");
		}
		
		AlumPersonal datos 			= alumPersonalDao.mapeaRegId(codigoAlumno);
		AlumUbicacion ubicacion 	= alumUbicacionDao.mapeaRegId(codigoAlumno);
		paisNombre 					= catPaisDao.getNombrePais(datos.getPaisId());
		estadoNombre 				= catEstadoDao.getNombreEstado(datos.getPaisId(), datos.getEstadoId());
		ciudadNombre 				= catCiudadDao.getNombreCiudad(datos.getPaisId(), datos.getEstadoId(), datos.getCiudadId());
		nacionNombre 				= catPaisDao.getNacionalidad(datos.getPaisId());
		
		List<CatPais> lisPaises 		= catPaisDao.getListAll("ORDER BY 2");
		List<CatEstado> lisEstados 		= catEstadoDao.getListAll("ORDER BY PAIS_ID");		
		List<CatCiudad> lisCiudades 	= catCiudadDao.getListAll("ORDER BY ESTADO_ID");		
		
		modelo.addAttribute("resultado", resultado);
		modelo.addAttribute("datos", datos);
		modelo.addAttribute("ubicacion", ubicacion);
		modelo.addAttribute("lisPaises", lisPaises);
		modelo.addAttribute("lisEstados", lisEstados);
		modelo.addAttribute("lisCiudades", lisCiudades);
		modelo.addAttribute("paisNombre", paisNombre);
		modelo.addAttribute("estadoNombre", estadoNombre);
		modelo.addAttribute("ciudadNombre", ciudadNombre);
		modelo.addAttribute("nacionNombre", nacionNombre);
		//modelo.addAttribute("paises", paises);
		
		return "archivo/cotejar_alumno/cotejar";
	}
	
	@RequestMapping("/archivo/cotejar_alumno/grabar")
	public String archivoCotejarGrabarAlumno(HttpServletRequest request){
		
		String codigoAlumno 		= "0";
		String codigoPersonal 		= "0"; 
		String mensaje				= "-";
		AlumPersonal datos		 	= new AlumPersonal();
		Acceso acceso 				= new Acceso();
		LogAlumno logAlumno 		= new LogAlumno();
		HttpSession sesion			= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoAlumno			= (String)sesion.getAttribute("codigoAlumno");        	
        	codigoPersonal			= (String)sesion.getAttribute("codigoPersonal");
        	if (alumPersonalDao.existeReg(codigoAlumno)) {
        		datos 			= alumPersonalDao.mapeaRegId(codigoAlumno);
        	} 
        	
    		String cotejado 	= datos.getCotejado()==null?"N":datos.getCotejado();
    		if(cotejado.equals("N")){    			
    			datos.setCodigoPersonal(codigoAlumno);
    			String curp = request.getParameter("Curp")==null?"-":request.getParameter("Curp").toUpperCase();
    			datos.setCurp(curp);    			
    			datos.setNombre(request.getParameter("Nombre").toUpperCase());
    			datos.setApellidoPaterno(request.getParameter("ApellidoPaterno").toUpperCase());
    			datos.setApellidoMaterno(request.getParameter("ApellidoMaterno").toUpperCase());    			
    			datos.setNombreLegal(request.getParameter("NombreLegal").toUpperCase());
    			datos.setFNacimiento(request.getParameter("FNacimiento"));			
    			datos.setSexo(request.getParameter("Sexo"));
    			datos.setPaisId(request.getParameter("PaisId")==null?"0":request.getParameter("PaisId"));    			
    			datos.setEstadoId(request.getParameter("EstadoId"));
    			datos.setCiudadId(request.getParameter("CiudadId"));			
    			datos.setNacionalidad(request.getParameter("Nacionalidad")==null?"0":request.getParameter("Nacionalidad"));    			
    			datos.setEstado("A");
    			datos.setFCreado(aca.util.Fecha.getHoy());
    			datos.setUsAlta(codigoPersonal);
    			datos.setCotejado("S");
    			
    			//Establecemos la clave    			    				
    			String claveDigest	= aca.util.Encriptar.sha512ConBase64(codigoAlumno);	
    			acceso.setCodigoPersonal(codigoAlumno);				
    			acceso.setClave(claveDigest);
    			acceso.setClaveInicial(codigoAlumno);
    			String ingreso = acceso.getIngreso()==null?"N":acceso.getIngreso();    			
    			if(!accesoDao.existeReg(codigoAlumno)){
    				acceso.setIngreso("N");
    				acceso.setAdministrador("N");
    				acceso.setCotejador("N");
    				acceso.setSupervisor("N");
    				acceso.setPortalAlumno("N");
    				acceso.setPortalMaestro("N");			
    				acceso.setModalidad("0");
    				acceso.setUsuario(codigoAlumno);
    				acceso.setConvalida("N");
    				acceso.setIdioma("en");
    				acceso.setMenu("1");
    				acceso.setEnLinea("N");
    				accesoDao.insertReg(acceso);
    			}else if(ingreso.equals("N")){
    				accesoDao.updateReg(acceso);
    			}    				
    		}
    		
    		if (alumPersonalDao.existeReg(datos.getCodigoPersonal())){    			
    			if (alumPersonalDao.updateReg(datos)){
    				logAlumno.setId( logAlumnoDao.maximoReg());
    				logAlumno.setTabla("ALUM_PERSONAL");
    				logAlumno.setOperacion("UPDATE");
    				logAlumno.setUsuario(codigoPersonal);
    				logAlumno.setIp(request.getRemoteAddr());
    				logAlumno.setDatos(datos.getCodigoPersonal());
    				if (logAlumnoDao.insertReg(logAlumno)){    					
    					mensaje = "Saved: "+datos.getCodigoPersonal();					
    					if (maestrosDao.existeReg(codigoAlumno))
    						sesion.setAttribute("codigoEmpleado", datos.getCodigoPersonal());
    					else if (codigoAlumno.substring(0,1).equals("P"))
    						sesion.setAttribute("codigoPadre", datos.getCodigoPersonal());
    					else if (codigoAlumno.substring(0,1).equals("0") || codigoAlumno.substring(0,1).equals("1") || codigoAlumno.substring(0,1).equals("2"))
    						sesion.setAttribute("codigoAlumno", datos.getCodigoPersonal());					
    				}else{    					
    					mensaje = "Error saivng: "+datos.getCodigoPersonal();
    				}				
    			}else{    				
    				mensaje = "Error saving: "+datos.getCodigoPersonal();
    			}
    		}else{    			
    			mensaje = "Code not found: "+datos.getCodigoPersonal();
    		}
        }
		
		return "redirect:/archivo/cotejar_alumno/cotejar?Mensaje="+mensaje;
	}
	
	@RequestMapping("/archivo/cotejar_alumno/getEstados")
	@ResponseBody
	public String admisionDatosGetestados(HttpServletRequest request){		
		String paisId 					= request.getParameter("paisId")==null?"0":request.getParameter("paisId");	
		String mensaje					= "";
		
		List<CatEstado> lisEstados 		= catEstadoDao.getLista(paisId,"ORDER BY 1,3");	
		for(CatEstado edo: lisEstados){
			mensaje+= "<option value='"+edo.getEstadoId()+"'>"+ edo.getNombreEstado()+"</option>";
		}	
		return mensaje;
	}
	
	@RequestMapping("/archivo/cotejar_alumno/getCiudades")
	@ResponseBody
	public String admisionDatosGetciudades(HttpServletRequest request){
		String paisId 				= request.getParameter("paisId")==null?"":request.getParameter("paisId");	
		String estadoId 			= request.getParameter("estadoId")==null?"":request.getParameter("estadoId");
		String mensaje 				= ""; 
		List<CatCiudad> lisCiudades = catCiudadDao.getLista(paisId, estadoId," ORDER BY 4");
		
		for(CatCiudad ciudad: lisCiudades){
			mensaje +=" <option value='"+ciudad.getCiudadId()+"'>"+ ciudad.getNombreCiudad()+"</option>";
		}		
		return mensaje;
	}
	
	@RequestMapping("/archivo/cotejar_alumno/quitarCotejado")
	public String archivoCotejarAlumnoQuitarCotejado(HttpServletRequest request){		
		String codigoAlumno			= request.getParameter("codigoAlumno");
		String mensaje					= "";		
		
		HttpSession session		= ((HttpServletRequest)request).getSession();
		if (session!=null){
			codigoAlumno 		= (String) session.getAttribute("codigoAlumno");
		}
		
		if (alumPersonalDao.existeReg(codigoAlumno) == true){
			if (alumPersonalDao.updateCotejado(codigoAlumno, "N")){				
				mensaje = "Not verified";
			}else{
				mensaje = "Unable to remove verificaiton: "+codigoAlumno;
			}
		}else{
			mensaje = "Not found: "+codigoAlumno;
		}
		
		return "redirect:/archivo/cotejar_alumno/cotejar?Mensaje="+mensaje;
	}
	
}
