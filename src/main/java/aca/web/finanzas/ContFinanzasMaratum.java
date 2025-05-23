package aca.web.finanzas;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.financiero.spring.FinMaratum;
import aca.financiero.spring.FinMaratumDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.Inscritos;
import aca.vista.spring.InscritosDao;

@Controller
public class ContFinanzasMaratum {
	
	@Autowired
	private FinMaratumDao finMaratumDao;
	
	@Autowired
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired
	private AccesoDao accesoDao;
	
	@Autowired
	private MapaPlanDao mapaPlanDao;
	
	@Autowired
	private InscritosDao inscritosDao;
	
	
	@RequestMapping("/finanzas/maratum/listado")
	public String finanzasMaratumListado(HttpServletRequest request, Model modelo){
		
		String estado			= request.getParameter("Estado") == null ? "'S','A','C'" : request.getParameter("Estado");		
		String codigoPersonal 	= "";		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");
        }		
		Acceso acceso 			= accesoDao.mapeaRegId(codigoPersonal);
		
		List<FinMaratum> lisSolicitudes		 		= new ArrayList<FinMaratum>();
		if (acceso.getAdministrador().equals("S") || acceso.getSupervisor().equals("S")) {
			lisSolicitudes = finMaratumDao.lisPorEstados(estado, "ORDER BY FECHA");
		}else {
			lisSolicitudes = finMaratumDao.lisPorAccesos(acceso.getAccesos().replace(" ", ",") , estado, "ORDER BY FECHA");
		}
		
		HashMap<String, AlumPersonal> mapaAlumnos 	= alumPersonalDao.mapaAlumnosEnMaratum();
		HashMap<String, MapaPlan> mapaPlanes 		= mapaPlanDao.mapPlanes("'A','V','I'");
		
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("lisSolicitudes", lisSolicitudes);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		
		return "finanzas/maratum/listado";
	}
	
	@RequestMapping("/finanzas/maratum/solicitudFinanciero")
	public String finanzasMaratumSolicitudFinanciero(HttpServletRequest request, Model modelo){
		
		return "finanzas/maratum/solicitudFinanciero";
	}
	
	@RequestMapping("/finanzas/maratum/editar")
	public String finanzasMaratumEditar(HttpServletRequest request, Model modelo){
		
		String id			= request.getParameter("Id") == null ? "0" : request.getParameter("Id");		
		String codigoPersonal 	= "";		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");
        }		
		Acceso acceso 			= accesoDao.mapeaRegId(codigoPersonal);
		FinMaratum finMaratum	= finMaratumDao.mapeaRegId(id);
		
		List<Inscritos> lisInscritos		 	= inscritosDao.getListAll(" ORDER BY PLAN_ID, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE");
		HashMap<String, MapaPlan> mapaPlanes 	= mapaPlanDao.mapPlanes("'A','V','I'");
		
		modelo.addAttribute("finMaratum", finMaratum);
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("lisInscritos", lisInscritos);		
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		
		return "finanzas/maratum/editar";
	}
	
	@PostMapping("/finanzas/maratum/grabar")
	public String finanzasMaratumGrabar(@RequestParam("archivo") MultipartFile file, HttpServletRequest request){
		
		String codigo 			= request.getParameter("Codigo")==null?"0000000PLAN0000":request.getParameter("Codigo");
		String planId			= codigo.substring(7, 15);
		String codigoAlumno		= codigo.substring(0,7);		
		String codigoPersonal 	= "";		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");
        }		
        String mensaje 			= "";
        FinMaratum finMaratum 	= new FinMaratum(); 
        
        try {
        	String id = finMaratumDao.maximoReg();
        	finMaratum.setId(id);
        	finMaratum.setCodigoPersonal(codigoAlumno);
        	finMaratum.setPlanId(planId);
        	finMaratum.setArchivo(file.getBytes());
        	finMaratum.setNombre(file.getOriginalFilename());
        	finMaratum.setUsuario(codigoPersonal);
        	finMaratum.setEstado("S");
        	
        	if(!finMaratumDao.existeReg(id)) {
        		if(finMaratumDao.insertReg(finMaratum)) {
        			mensaje = "El archivo se ha guardado";
        		}
        	}
        }catch(Exception ex) {
        	
        }
		
		return "redirect:/finanzas/maratum/editar?Mensaje="+mensaje;
	}
	
	
	@RequestMapping("/finanzas/maratum/bajarArchivo")
	public void finanzasFormatosBajarArchivo(HttpServletRequest request, HttpServletResponse response ) {
		
		String id		= request.getParameter("Id")==null?"0":request.getParameter("Id");		
		FinMaratum finMaratum 	= new FinMaratum();
		try {						
			if(finMaratumDao.existeReg(id)){
				finMaratum = finMaratumDao.mapeaRegId(id);				
				OutputStream out = response.getOutputStream();
				response.setHeader("Content-Disposition","attachment; filename=\""+ finMaratum.getNombre() + "\"");
				out.write(finMaratum.getArchivo());				
			}
		}catch(Exception ex){
			System.out.println("Error:finanzas/maratum/bajarArchivo:"+ex);
		}
	}
	
	@RequestMapping("/finanzas/maratum/cambiarEstado")
	public String finanzasMaratumCambiarEstado(HttpServletRequest request, Model modelo){

		String id		= request.getParameter("Id")==null?"0":request.getParameter("Id"); 
		String estado	= request.getParameter("Estado")==null?"0":request.getParameter("Estado");
		
		if(finMaratumDao.existeReg(id)){
			finMaratumDao.updateEstado(id, estado);
		}		
		
		return "redirect:/finanzas/maratum/listado";
	}
	
	@RequestMapping("/finanzas/maratum/borrar")
	public String finanzasMaratumBorrar(HttpServletRequest request, Model modelo){
		
		String id			= request.getParameter("Id")==null?"0":request.getParameter("Id");
		String mensaje		= "-";
		if(finMaratumDao.existeReg(id)) {
    		if(finMaratumDao.deleteReg(id)) {
    			mensaje = "¡ La solicitud fue eliminada !";
    		}
    	}        
		return "redirect:/finanzas/maratum/listado?Mensaje="+mensaje;
	}

}