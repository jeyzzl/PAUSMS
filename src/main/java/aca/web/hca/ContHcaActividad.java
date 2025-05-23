package aca.web.hca;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.catalogo.spring.CatNivel;
import aca.catalogo.spring.CatNivelDao;
import aca.hca.spring.HcaActividad;
import aca.hca.spring.HcaActividadDao;
import aca.hca.spring.HcaMaestroActividadDao;
import aca.hca.spring.HcaTipo;
import aca.hca.spring.HcaTipoDao;

@Controller
public class ContHcaActividad {	
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	private HcaMaestroActividadDao hcaMaestroActividadDao;	
	
	@Autowired
	private HcaActividadDao hcaActividadDao;
	
	@Autowired
	private CatNivelDao catNivelDao;
	
	@Autowired
	HcaTipoDao hcaTipoDao;
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}	
	
	@RequestMapping("/hca/actividad/edita_actividad")
	public String hcaActividadEditaActividad(HttpServletRequest request, Model modelo){		
		String actividadId 	= request.getParameter("ActividadId")==null?"0":request.getParameter("ActividadId");		
		boolean existe 		= false; 
		HcaActividad hcaActividad = new HcaActividad(); 
		if (hcaActividadDao.existeReg(actividadId)) {
			hcaActividad = hcaActividadDao.mapeaRegId(actividadId);
			existe = true;
		}
		
		List<CatNivel> lisNiveles 	= catNivelDao.getListAll("ORDER BY NIVEL_ID");
		List<HcaTipo> lisTipos 		= hcaTipoDao.lisTodos("ORDER BY ORDEN");
		
		modelo.addAttribute("existe", existe);
		modelo.addAttribute("hcaActividad", hcaActividad);
		modelo.addAttribute("lisNiveles", lisNiveles);
		modelo.addAttribute("lisTipos", lisTipos);
		
		return "hca/actividad/edita_actividad";
	}	
	
	@RequestMapping("/hca/actividad/listado")
	public String hcaActividadListado(HttpServletRequest request, Model modelo){		
		
		List<HcaActividad> lisActividad 		=	hcaActividadDao.getListAll("ORDER BY TIPO_ID, NIVEL_ID, ACTIVIDAD_NOMBRE, VALOR");        

		HashMap<String,String> mapaActividades 	= hcaMaestroActividadDao.mapaActividades();
		HashMap<String,HcaTipo> mapaTipos 		= hcaTipoDao.mapaTipos();
		HashMap<String,CatNivel> mapaNiveles 	= catNivelDao.getMapAll("");
		
		modelo.addAttribute("lisActividad", lisActividad);
		modelo.addAttribute("mapaActividades", mapaActividades);
		modelo.addAttribute("mapaTipos", mapaTipos);
		modelo.addAttribute("mapaNiveles", mapaNiveles);
		
		return "hca/actividad/listado";
	}	
	
	@RequestMapping("/hca/actividad/eliminar")
	public String hcaActividadEliminar(HttpServletRequest request, Model modelo){	
		String actividadId 	= request.getParameter("ActividadId")==null?"0":request.getParameter("ActividadId");
		String mensaje 		= "-";
		if (hcaActividadDao.existeReg(actividadId)) {
			if (hcaActividadDao.deleteReg(actividadId)) {
				mensaje = "¡Se eliminó la actividad!";
			}else {
				mensaje = "¡Error al intentar borrar la actividad!";
			}
		}		
		return "redirect:/hca/actividad/listado?Mensaje="+mensaje;
	}
	
	@RequestMapping("/hca/actividad/grabar")
	public String hcaActividadGrabar(HttpServletRequest request, Model modelo){	
		String actividadId 		= request.getParameter("ActividadId")==null?"0":request.getParameter("ActividadId");
		String nivelId			= request.getParameter("Nivel");
		String tipoId			= request.getParameter("Tipo");
		String nombre			= request.getParameter("Nombre");
		String valor			= request.getParameter("Valor");
		String modificable		= request.getParameter("Modificable");
		String mensaje 		= "-";
		HcaActividad hcaActividad = new HcaActividad();
		if (hcaActividadDao.existeReg(actividadId)){			
			hcaActividad.setActividadId(actividadId);
			hcaActividad.setTipoId(tipoId);
			hcaActividad.setActividadNombre(nombre);
			hcaActividad.setValor(valor);
			hcaActividad.setNivelId(nivelId);
			hcaActividad.setModificable(modificable);
			
			if(hcaActividadDao.updateReg(hcaActividad)){
				mensaje = "¡Modificado!";
			}else {
				mensaje = "¡Error al modificar!";
			}				
		}else {
			actividadId = hcaActividadDao.maximoReg();
			hcaActividad.setActividadId(actividadId);
			hcaActividad.setTipoId(tipoId);
			hcaActividad.setActividadNombre(nombre);
			hcaActividad.setValor(valor);
			hcaActividad.setNivelId(nivelId);
			hcaActividad.setModificable(modificable);
			if(hcaActividadDao.insertReg(hcaActividad)){
				mensaje = "¡Grabado!";
			}else {
				mensaje = "¡Error al grabar!";
			}
		}		
		return "redirect:/hca/actividad/edita_actividad?ActividadId="+actividadId+"&Mensaje="+mensaje;
	}
	
	
}