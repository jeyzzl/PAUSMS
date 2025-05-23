package aca.web.extranjeros;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.catalogo.spring.CatPaisDao;
import aca.leg.spring.LegPermisos;
import aca.leg.spring.LegPermisosDao;

@Controller
public class ContExtranjerosPermisos {
	
	@Autowired	
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired	
	private LegPermisosDao legPermisosDao;
	
	@Autowired	
	private CatPaisDao catPaisDao;
	
	@RequestMapping("/extranjeros/permisos/lista")
	public String extranjerosExtranjeroExtranjero(HttpServletRequest request, Model modelo){
		String codigoAlumno 		= "0";
		String paisNombre			= "-";
		AlumPersonal alumPersonal 	= new AlumPersonal();

		HttpSession sesion			= ((HttpServletRequest)request).getSession();		
		if (sesion!=null){
			codigoAlumno			= (String)sesion.getAttribute("codigoAlumno");
			alumPersonal			= alumPersonalDao.mapeaRegId(codigoAlumno);		
			paisNombre				= catPaisDao.getNombrePais(alumPersonal.getNacionalidad());
		}
		
		List<LegPermisos> lisPermisos 				= legPermisosDao.getLista(codigoAlumno," ORDER BY FECHA_INI");
		HashMap<String, String> mapNombrePermiso	= legPermisosDao.mapNombrePermiso();
		
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("paisNombre", paisNombre);
		modelo.addAttribute("lisPermisos", lisPermisos);
		modelo.addAttribute("mapNombrePermiso", mapNombrePermiso);
		
		return "extranjeros/permisos/lista";
	}
	
	@RequestMapping("/extranjeros/permisos/editar")
	public String extranjerosPermisosEditar(HttpServletRequest request, Model modelo){
		
		String folio	= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String codigo	= "0";
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigo			= (String)sesion.getAttribute("codigoAlumno");
        }
        
        String nombreAlumno = alumPersonalDao.getNombreAlumno(codigo, "NOMBRE");
        boolean edita = false;
        
        LegPermisos legPermiso = new LegPermisos();
        
        if(!folio.equals("0") && !codigo.equals("0")) {
        	edita = true;
        }
        
        if (legPermisosDao.existeReg(codigo, folio)){
        	legPermiso = legPermisosDao.mapeaRegId(codigo, folio);
		}
                
        modelo.addAttribute("codigo", codigo);
        modelo.addAttribute("folio", folio);
        modelo.addAttribute("nombreAlumno", nombreAlumno);
        modelo.addAttribute("legPermiso", legPermiso);
		modelo.addAttribute("edita", edita);
		
		return "extranjeros/permisos/editar";
	}
	
	@RequestMapping("/extranjeros/permisos/grabarPermiso")
	public String extranjerosPermisosGrabarPermiso(HttpServletRequest request, Model model){
		String codigo			= request.getParameter("Codigo")==null?"0":request.getParameter("Codigo");
		String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String fInicial			= request.getParameter("FInicial")==null?aca.util.Fecha.getHoy():request.getParameter("FInicial");
		String fFinal			= request.getParameter("FFinal")==null?aca.util.Fecha.getHoy():request.getParameter("FFinal");
		String mensaje 			= "-";
		String codigoEmpleado 	= "-";
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoEmpleado		= (String)sesion.getAttribute("codigoPersonal");
        }
        
		LegPermisos legPermiso = new LegPermisos();
		legPermiso.setFechaIni(fInicial);
		legPermiso.setFechaLim(fFinal);
		if (legPermisosDao.existeReg(codigo, folio)){
			legPermiso.setCodigo(codigo);
			legPermiso.setFolio(folio);
			legPermiso.setUsuarioAlta(codigoEmpleado);
			if (legPermisosDao.updateReg(legPermiso)){
				mensaje = "Permiso grabado";
			}else {
				mensaje = "No se grabó el permiso";
			}
		}else {
			legPermiso.setCodigo(codigo);
			legPermiso.setFolio(legPermisosDao.maximoReg(codigo));
			legPermiso.setUsuarioAlta(codigoEmpleado);
			if (legPermisosDao.insertReg(legPermiso)){
				mensaje = "Permiso grabado";
			}else {
				mensaje = "No se grabó el permiso";
			}
		}		
		return "redirect:/extranjeros/permisos/editar?Folio="+legPermiso.getFolio()+"&Codigo="+legPermiso.getCodigo()+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/extranjeros/permisos/cancelar")
	public String extranjerosPermisosCancelar(HttpServletRequest request, Model model){
		
		String codigo			= request.getParameter("Codigo")==null?"0":request.getParameter("Codigo");
		String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String cancelar			= request.getParameter("Cancelar")==null?"0":request.getParameter("Cancelar");
		String codigoEmpleado 	= "-";
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoEmpleado		= (String)sesion.getAttribute("codigoPersonal");
        }
        
		LegPermisos legPermiso = new LegPermisos();
		if (legPermisosDao.existeReg(codigo, folio)){
			legPermiso = legPermisosDao.mapeaRegId(codigo, folio);
			if(cancelar.equals("1")){
				legPermiso.setUsuarioBaja(codigoEmpleado);
				legPermiso.setStatus("I");
				legPermisosDao.updateReg(legPermiso);
			}
		}
		
		return "redirect:/extranjeros/permisos/lista";
	}

}
