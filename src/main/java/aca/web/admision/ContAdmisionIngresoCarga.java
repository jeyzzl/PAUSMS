package aca.web.admision;

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

import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;
import aca.catalogo.spring.CatModalidad;
import aca.catalogo.spring.CatModalidadDao;
import aca.vista.spring.EstadisticaDao;

@Controller
public class ContAdmisionIngresoCarga {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;	
	
	@Autowired
	EstadisticaDao estadisticaDao;
	
	@Autowired
	CargaDao cargaDao;
	
	@Autowired
	CatModalidadDao catModalidadDao;
	
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/admision/ingreso_carga/cargas")
	public String admisionIngresoCargaCargas(HttpServletRequest request, Model modelo){
		
		String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		String fInscripcion		= request.getParameter("Fecha")==null?aca.util.Fecha.getHoy():request.getParameter("Fecha");
		
		String codigoPersonal 	= "0";	 
		String cargas			= "";		
		String mensaje 			= "-";
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
        if (sesion != null){
        	codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");	
        	cargas				= (String) sesion.getAttribute("cargas");
        }
        
        List<Carga> lisCarga	= cargaDao.getListPorFecha(fInscripcion, "ORDER BY CARGA_ID");
    	List<Carga> lisActivas	= cargaDao.getListCargasActivas(" ORDER BY CARGA_ID");
    	
    	 HashMap<String,String> mapaFechaIni = estadisticaDao.mapaFechaInicialCargas();
    	 HashMap<String,String> mapaFechaFin = estadisticaDao.mapaFechaFinalCargas();
    	
    	if (accion.equals("1")){
    		cargas = "";
     		for(Carga carga : lisCarga){
    			if(request.getParameter(carga.getCargaId()) != null ){
    				if(cargas.equals(""))
    					cargas = "'"+carga.getCargaId()+"'";
    				else
    					cargas += ",'"+carga.getCargaId()+"'";					
    			}
    		}
    		sesion.setAttribute("cargas", cargas);
    		mensaje = "¡Actualizado!";
    	}	
		
        modelo.addAttribute("codigoPersonal", codigoPersonal);
        modelo.addAttribute("cargas", cargas);
        modelo.addAttribute("accion", accion);
        modelo.addAttribute("fInscripcion", fInscripcion);
        modelo.addAttribute("mensaje", mensaje);
        
        modelo.addAttribute("lisCarga", lisCarga);
        modelo.addAttribute("lisActivas", lisActivas);
        modelo.addAttribute("mapaFechaIni", mapaFechaIni);
        modelo.addAttribute("mapaFechaFin", mapaFechaFin);
        
		return "admision/ingreso_carga/cargas";
	}
	
	@RequestMapping("/admision/ingreso_carga/ingreso")
	public String admisionIngresoCargaIngreso(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContAdmisionIngresoCarga|admisionIngresoCargaIngreso:");
		return "admision/ingreso_carga/ingreso";
	}

	@RequestMapping("/admision/ingreso_carga/modalidades")
	public String admisionIngresoCargaModalidades(HttpServletRequest request, Model modelo){
		
		String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		String codigoPersonal 	= "0";	 
		String cargas			= "";
		String modalidades		= "";		
		String mensaje 			= "-";
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
        if (sesion != null){
        	codigoPersonal 	= (String) sesion.getAttribute("codigoPersonal");	
        	cargas			= (String) sesion.getAttribute("cargas");
        	modalidades		= (String) sesion.getAttribute("modalidades");
        }
        
        List<CatModalidad> lisModalidad	= catModalidadDao.getListAll("ORDER BY MODALIDAD_ID");
    	
        if (accion.equals("1")){
    		modalidades = "";
    		for(CatModalidad modalidad : lisModalidad){
    			if(request.getParameter(modalidad.getModalidadId()) != null ){			
    				if(modalidades.equals(""))
    					modalidades = "'"+modalidad.getModalidadId()+"'";
    				else
    					modalidades += ",'"+modalidad.getModalidadId()+"'";						
    			}
    		}
    		sesion.setAttribute("modalidades", modalidades);
    		mensaje = "¡Actualizado!";
    	}		
        
        HashMap<String,String> mapa = estadisticaDao.mapaModalidadesEnCargas(cargas);
		
        modelo.addAttribute("codigoPersonal", codigoPersonal);
        modelo.addAttribute("cargas", cargas);
        modelo.addAttribute("accion", accion);
        modelo.addAttribute("modalidades", modalidades);
        modelo.addAttribute("mensaje", mensaje);
        
        modelo.addAttribute("lisModalidad", lisModalidad);
        modelo.addAttribute("mapa", mapa);
        
		return "admision/ingreso_carga/modalidades";
	}

}