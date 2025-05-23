package aca.web.catalogos;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.catalogo.spring.CatCiudad;
import aca.catalogo.spring.CatCiudadDao;
import aca.catalogo.spring.CatEscuela;
import aca.catalogo.spring.CatEscuelaDao;
import aca.catalogo.spring.CatEstado;
import aca.catalogo.spring.CatEstadoDao;
import aca.catalogo.spring.CatPais;
import aca.catalogo.spring.CatPaisDao;

@Controller
public class ContCatalogosEscuela {	
	
	@Autowired
	private CatEscuelaDao escuelaDao;

	@Autowired
	private CatPaisDao catPaisDao;
	
	@Autowired
	private CatEstadoDao catEstadoDao;
	
	@Autowired
	private CatCiudadDao catCiudadDao;
	
	@RequestMapping("/catalogos/escuela/escuela")
	public String catalogoEscuelaEscuela(HttpServletRequest request, Model model){
		
		List<CatEscuela> lista = escuelaDao.getListAll("ORDER BY 2");
		model.addAttribute("lista", lista);

		return "catalogos/escuela/escuela";
	}

	@RequestMapping("/catalogos/escuela/accion_e")
	public String catalogosEscuelaAccion_e(HttpServletRequest request, Model modelo){
		String paisId  		= request.getParameter("PaisId") == null ? "0" : request.getParameter("PaisId");	
		String estadoId  	= request.getParameter("EstadoId") == null ? "0" : request.getParameter("EstadoId");	
		String ciudadId  	= request.getParameter("CiudadId") == null ? "0" : request.getParameter("CiudadId");	
		String nombreEsc	= request.getParameter("NombreEscuela") == null ? "-" : request.getParameter("NombreEscuela");	
		String escuelaId  	= request.getParameter("EscuelaId") == null ? "0" : request.getParameter("EscuelaId");	
		
		CatEscuela catEscuela = new CatEscuela();
		if(!escuelaId.equals("0")) {
			if(escuelaDao.existeReg(escuelaId)) {
				catEscuela = escuelaDao.mapeaRegId(escuelaId);
			}else {
				catEscuela.setEscuelaId(escuelaDao.maximoReg());
			}
		}

		int accion	  		= Integer.parseInt(request.getParameter("Accion"));	
		int accionFmt 		= 0;
		String sResultado 	= "";
		
		List<CatPais> lisPais		= catPaisDao.getListAll("ORDER BY 2");
		List<CatEstado> lisEstado	= new ArrayList<CatEstado>();

		if(paisId.equals("0") && escuelaId.equals("0")) {
			for(CatPais pais : lisPais) {
				lisEstado	= catEstadoDao.getLista(pais.getPaisId(),"");
				if(lisEstado.size() >= 1) {
					paisId = pais.getPaisId();
					break;
				}
			}
		}

		if(!catEscuela.getPaisId().equals("")) {
			paisId = catEscuela.getPaisId();
		}

		lisEstado	= catEstadoDao.getLista(paisId,"");
		
		if(estadoId.equals("0")) {
			estadoId = lisEstado.get(0).getEstadoId();
		}
		
		if(!catEscuela.getEstadoId().equals("")) {
			estadoId = catEscuela.getEstadoId();
		}

		List<CatCiudad> lisCiudad = catCiudadDao.getLista(paisId, estadoId, "ORDER BY 4");
		
		if ( accion == 1 ) {
			catEscuela.setEscuelaId(escuelaDao.maximoReg());
		}
		
		if (escuelaDao.existeReg(catEscuela.getEscuelaId()) && accion==2) {
			accion = 3;
		}

		System.out.println("ENTRE Cont");
		
		switch (accion){
			case 2: { // Grabar
				catEscuela.setNombreEscuela(nombreEsc);
				catEscuela.setPaisId(paisId);
				catEscuela.setEstadoId(estadoId);
				catEscuela.setCiudadId(ciudadId);
				if (escuelaDao.existeReg(catEscuela.getEscuelaId()) == false){
					if (escuelaDao.insertReg(catEscuela)){
						accionFmt = 1;
					}else{
						accionFmt = 2;
					}
				}else{
					accionFmt = 8;
				}
				
				break;
			}
			case 3: { // Modificar
				catEscuela.setNombreEscuela(nombreEsc);
				catEscuela.setPaisId(paisId);
				catEscuela.setEstadoId(estadoId);
				catEscuela.setCiudadId(ciudadId);
				if (escuelaDao.existeReg(catEscuela.getEscuelaId()) == true){
					if (escuelaDao.updateReg(catEscuela)){
						accionFmt = 3;
					}else{
						accionFmt = 4;
					}
				}else{
					accionFmt = 7;
				}
				break;
			}
			case 4: { // Borrar
				if (escuelaDao.existeReg(catEscuela.getEscuelaId()) == true){
					if (escuelaDao.deleteReg(catEscuela.getEscuelaId())){
						accionFmt = 5;
					}else{
						accionFmt = 6;
					}	
	
				}else{
					accionFmt = 7;
				}
				
				break;
			}
			case 5: { // Consultar
			//	Escuela.setCiudadId(request.getParameter("CiudadId"));			
				if (escuelaDao.existeReg(catEscuela.getEscuelaId()) == true){
					accionFmt = 9;
				}else{
					accionFmt = 7; 
				}	
				break;			
			}
			case 6: { // Refrescar combos PEC
				catEscuela.setNombreEscuela(nombreEsc);
				catEscuela.setPaisId(paisId);
				if (request.getParameter("Pec").equals("2")){
					catEscuela.setEstadoId(estadoId);
				}else{
					catEscuela.setEstadoId("0");
					catEscuela.setCiudadId("0");
				}			
			}
		}	

		sResultado = ": "+catEscuela.getEscuelaId();
		
		modelo.addAttribute("sResultado", sResultado);
		modelo.addAttribute("accionFmt", accionFmt);
		modelo.addAttribute("catEscuela", catEscuela);
		modelo.addAttribute("lisPais", lisPais);
		modelo.addAttribute("lisEstado", lisEstado);
		modelo.addAttribute("lisCiudad", lisCiudad);
		
		return "catalogos/escuela/accion_e";
	}
}