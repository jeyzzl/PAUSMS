package aca.web.catalogos;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import aca.catalogo.spring.CatPais;
import aca.catalogo.spring.CatEstado;
import aca.catalogo.spring.CatCiudad;
import aca.catalogo.spring.CatCiudadDao;
import aca.catalogo.spring.CatEstadoDao;
import aca.catalogo.spring.CatPaisDao;
import org.json.*;

@Controller
public class ContCatalogosPais {
		
	@Autowired
	private CatPaisDao catPaisDao;
	
	@Autowired
	private CatEstadoDao catEstadoDao;
	
	@Autowired
	private CatCiudadDao catCiudadDao;	
	
	@RequestMapping("/catalogos/pais/paises")
	public String catalogosPaisPaises(HttpServletRequest request, Model model){
		
		List<CatPais> lisPaises 					= (List<CatPais>)catPaisDao.getListAll(" ORDER BY NOMBRE_PAIS");
		HashMap<String,String> mapaEstadosPorPais 	= catEstadoDao.mapaTotalEstados();
		
		model.addAttribute("lisPaises", lisPaises);
		model.addAttribute("mapaEstadosPorPais", mapaEstadosPorPais);
		
		return "catalogos/pais/paises";
	}
	
	@RequestMapping("/catalogos/pais/editarPais")
	public String catalogosPaisEditarPais(HttpServletRequest request, Model model){
		String paisId 	= request.getParameter("PaisId")==null?"0":request.getParameter("PaisId");
		String mensaje 	= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		
		CatPais pais 	= new CatPais();		
		if (catPaisDao.existeReg(paisId)) {
			pais = catPaisDao.mapeaRegId(paisId);
		}else {
			pais.setPaisId(catPaisDao.maximoReg());
		}
		
		model.addAttribute("pais", pais);
		model.addAttribute("mensaje", mensaje);
		
		return "catalogos/pais/editarPais";
	}
	
	@RequestMapping("/catalogos/pais/grabarPais")
	public String catalogosPaisGrabarPais(HttpServletRequest request, Model model){
		String paisId 			= request.getParameter("PaisId")==null?"0":request.getParameter("PaisId");
		String nombre 			= request.getParameter("NombrePais")==null?"-":request.getParameter("NombrePais");
		String nacionalidad 	= request.getParameter("Nacionalidad")==null?"-":request.getParameter("Nacionalidad");
		String interamericana 	= request.getParameter("Interamerica")==null?"-":request.getParameter("Interamerica");
		String mensaje			= "-";
		
		CatPais pais = new CatPais();
		
		pais.setPaisId(paisId);
		pais.setNombrePais(nombre);
		pais.setNacionalidad(nacionalidad);
		pais.setInteramerica(interamericana);
		
		if (catPaisDao.existeReg(paisId)) {
			if (catPaisDao.updateReg(pais)) {
				mensaje = "2";
			}			
		}else {
			//Insertar
			paisId = catPaisDao.maximoReg();
			pais.setPaisId(paisId);
			if (catPaisDao.insertReg(pais)) {
				mensaje = "1";
			}			
		}
		
		return "redirect:/catalogos/pais/editarPais?PaisId="+paisId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/catalogos/pais/deletePais")
	public String catalogosPaisDeletePais(HttpServletRequest request){
		String paisId 			= request.getParameter("PaisId")==null?"0":request.getParameter("PaisId");
		String mensaje			= "-";		
		if (catPaisDao.existeReg(paisId)) {
			if (catPaisDao.deleteReg(paisId)){
				mensaje = "Deleted";
			}
		}
				
		return "redirect:/catalogos/pais/paises?Mensaje="+mensaje;
	}
	
	@RequestMapping("/catalogos/pais/estados")
	public String catalogosPaisEstados(HttpServletRequest request, Model model){
		
		String paisId 		= request.getParameter("PaisId")==null?"0":request.getParameter("PaisId");
		
		List<CatEstado> lisEstados = (List<CatEstado>)catEstadoDao.getLista(paisId, " ORDER BY NOMBRE_ESTADO");
		HashMap<String,String> mapaTotalCiudadPorEstado = catCiudadDao.mapaCiudadPorEstado(paisId, "GROUP BY ESTADO_ID");
		
		model.addAttribute("paisId", paisId);
		model.addAttribute("lisEstados", lisEstados);
		model.addAttribute("mapaTotalCiudadPorEstado", mapaTotalCiudadPorEstado);
		
		return "catalogos/pais/estados";
	}
	
	@RequestMapping("/catalogos/pais/editarEstado")
	public String catalogosPaisEditarEstado(HttpServletRequest request, Model model){
		String paisId 	= request.getParameter("PaisId")==null?"0":request.getParameter("PaisId");
		String estadoId = request.getParameter("EstadoId")==null?"0":request.getParameter("EstadoId");
		String mensaje 	= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		
		CatEstado estado 	 = new CatEstado();	
		
		if (catEstadoDao.existeReg(paisId, estadoId)) {
			estado = (CatEstado)catEstadoDao.mapeaRegId(paisId, estadoId);
		}else {
			estado.setEstadoId(catEstadoDao.maximoReg(paisId));
		}
		
		
		model.addAttribute("paisId", paisId);
		model.addAttribute("estado", estado);
		model.addAttribute("mensaje", mensaje);

		return "catalogos/pais/editarEstado";
	}
	
	@RequestMapping("/catalogos/pais/grabarEstado")
	public String catalogosPaisGrabarEstado(HttpServletRequest request, Model model){
		String paisId 		= request.getParameter("PaisId")==null?"0":request.getParameter("PaisId");
		String estadoId 	= request.getParameter("EstadoId")==null?"0":request.getParameter("EstadoId");
		String nombre 		= request.getParameter("NombreEstado")==null?"-":request.getParameter("NombreEstado");
		String nombreCorto 	= request.getParameter("NombreCorto")==null?"-":request.getParameter("NombreCorto");
		String semaforo 	= request.getParameter("Semaforo")==null?"-":request.getParameter("Semaforo");
		String claveSep 	= request.getParameter("ClaveSep")==null?"-":request.getParameter("ClaveSep");
		String nombreSep 	= request.getParameter("NombreSep")==null?"-":request.getParameter("NombreSep");
		String mensaje		= "-";
		
		CatEstado estado = new CatEstado();
		estado.setPaisId(paisId);
		estado.setEstadoId(estadoId);
		estado.setNombreEstado(nombre);
		estado.setCorto(nombreCorto);
		estado.setSepCorto(nombreSep);
		estado.setSepId(claveSep);
		estado.setSemaforo(semaforo);
		
		if (catEstadoDao.existeReg(paisId,estadoId)) {			
			catEstadoDao.updateReg(estado);
			mensaje = "2";
		}else {
			//Insertar
			estado.setEstadoId(catEstadoDao.maximoReg(paisId));
			catEstadoDao.insertReg(estado);
			mensaje = "1";
		}
		
		return "redirect:/catalogos/pais/editarEstado?PaisId="+estado.getPaisId()+"&EstadoId="+estado.getEstadoId()+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/catalogos/pais/borrarEstado")
	public String catalogosPaisBorrarEstado(HttpServletRequest request, Model model){
		String paisId 	= request.getParameter("PaisId")==null?"0":request.getParameter("PaisId");
		String estadoId = request.getParameter("EstadoId")==null?"0":request.getParameter("EstadoId");	
		
		if (catEstadoDao.existeReg(paisId,estadoId)) {
			catEstadoDao.deleteReg(paisId,estadoId);
		}
		
		return "redirect:/catalogos/pais/estados?PaisId="+paisId;
	}
	
	@RequestMapping("/catalogos/pais/ciudades")
	public String catalogosPaisCiudades(HttpServletRequest request, Model model){
		String paisId 	= request.getParameter("PaisId")==null?"0":request.getParameter("PaisId");
		String estadoId = request.getParameter("EstadoId")==null?"0":request.getParameter("EstadoId");
		
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			if (paisId.equals("0")) {
				paisId = (String)sesion.getAttribute("paisId");
			}else {
				sesion.setAttribute("paisId", paisId);
			}
			if (estadoId.equals("0")) {
				estadoId = (String)sesion.getAttribute("estadoId");
			}else {
				sesion.setAttribute("estadoId", estadoId);
			}
		}	
		
		List<CatCiudad> lisCiudades = (List<CatCiudad>)catCiudadDao.getLista(paisId, estadoId, " ORDER BY NOMBRE_CIUDAD");
		
		model.addAttribute("paisId", paisId);
		model.addAttribute("estadoId", estadoId);
		model.addAttribute("lisCiudades", lisCiudades);
		
		return "catalogos/pais/ciudades";
	}
	
	@RequestMapping("/catalogos/pais/editarCiudad")
	public String catalogosPaisEditarCiudad(HttpServletRequest request, Model model){
		String paisId 	= "0";
		String estadoId = "0";
		String ciudadId = request.getParameter("CiudadId")==null?"0":request.getParameter("CiudadId");
		String mensaje 	= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			paisId 	 = (String)sesion.getAttribute("paisId");
			estadoId = (String)sesion.getAttribute("estadoId");
		}
		
		CatCiudad ciudad 	 = new CatCiudad();			
		
		if (catCiudadDao.existeReg( paisId, estadoId, ciudadId)) {
			ciudad = (CatCiudad)catCiudadDao.mapeaRegId(paisId, estadoId, ciudadId);
		}else {
			ciudad.setCiudadId(catCiudadDao.maximoReg(paisId, estadoId));
		}
		
		model.addAttribute("paisId", paisId);
		model.addAttribute("estadoId", estadoId);
		model.addAttribute("ciudad", ciudad);
		model.addAttribute("mensaje", mensaje);
		
		return "catalogos/pais/editarCiudad";
	}	
	
	@RequestMapping("/catalogos/pais/grabarCiudad")
	public String catalogosPaisGrabarCiudad(HttpServletRequest request, Model model){
		String paisId 	= "0";
		String estadoId = "0";
		String ciudadId = request.getParameter("CiudadId")==null?"0":request.getParameter("CiudadId");
		String nombre 	= request.getParameter("NombreCiudad")==null?"-":request.getParameter("NombreCiudad");
		String mensaje	= "-";
		
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			paisId = (String)sesion.getAttribute("paisId");
			estadoId = (String)sesion.getAttribute("estadoId");
		}
		
		CatCiudad ciudad 	 = new CatCiudad();
		
		ciudad.setPaisId(paisId);
		ciudad.setEstadoId(estadoId);
		ciudad.setCiudadId(ciudadId);
		ciudad.setNombreCiudad(nombre);		
		if (catCiudadDao.existeReg(paisId, estadoId, ciudadId)) {
			// Modificar
			catCiudadDao.updateReg(ciudad);
			mensaje = "2";
		}else {
			//Insertar
			ciudad.setCiudadId(catCiudadDao.maximoReg(paisId,estadoId));
			catCiudadDao.insertReg(ciudad);
			mensaje = "1";
		}
		
		return "redirect:/catalogos/pais/editarCiudad?PaisId="+ciudad.getPaisId()+"&EstadoId="+ciudad.getEstadoId()+"&CiudadId="+ciudad.getCiudadId()+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/catalogos/pais/borrarCiudad")
	public String catalogosPaisBorrarCiudad(HttpServletRequest request, Model model){
		String paisId 	= request.getParameter("PaisId")==null?"0":request.getParameter("PaisId");
		String estadoId = request.getParameter("EstadoId")==null?"0":request.getParameter("EstadoId");
		String ciudadId = request.getParameter("CiudadId")==null?"0":request.getParameter("CiudadId");		
			
		if (catCiudadDao.existeReg(paisId, estadoId, ciudadId)) {
			catCiudadDao.deleteReg(paisId, estadoId, ciudadId);
		}
		
		return "redirect:/catalogos/pais/ciudades?PaisId="+paisId+"&EstadoId="+estadoId;
	}
	
}