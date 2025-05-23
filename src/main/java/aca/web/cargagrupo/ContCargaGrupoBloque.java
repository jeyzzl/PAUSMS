package aca.web.cargagrupo;

import java.util.HashMap;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.carga.spring.Carga;
import aca.carga.spring.CargaBloque;
import aca.carga.spring.CargaBloqueDao;
import aca.carga.spring.CargaDao;
import aca.carga.spring.CargaGrupoDao;
import aca.catalogo.spring.CatPeriodo;
import aca.catalogo.spring.CatPeriodoDao;

@Controller
public class ContCargaGrupoBloque {	
	
	@Autowired
	CatPeriodoDao catPeriodoDao;
	
	@Autowired
	CargaDao cargaDao;
	
	@Autowired
	CargaBloqueDao cargaBloqueDao;
	
	@Autowired
	CargaGrupoDao cargaGrupoDao;
	
	
	@RequestMapping("/carga_grupo/bloque/bloque")
	public String cargaGrupoBloqueBloque(HttpServletRequest request, Model modelo) {
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();	
		
		List<CatPeriodo> lisPeriodos = catPeriodoDao.getListAll(" ORDER BY PERIODO_ID DESC");
		if(request.getParameter("cambioPeriodo")!=null && !request.getParameter("cambioPeriodo").equals("")){
			if (sesion!=null) sesion.setAttribute("periodo", request.getParameter("PeriodoId"));
		}
		
		String periodoId = (String)sesion.getAttribute("periodo");	
		if(periodoId.equals("0")) {
			periodoId = lisPeriodos.get(0).getPeriodoId();
		}
		
		List<Carga> lisCargas = cargaDao.listPeriodoAndEstado(periodoId, "1", " ORDER BY CARGA_ID");
		
		if(request.getParameter("cambioPeriodo")!=null&&!request.getParameter("cambioPeriodo").equals("")&&!lisCargas.isEmpty()){
			sesion.setAttribute("cargaId", lisCargas.get(0).getCargaId());		
		}else if(request.getParameter("cambioCarga")!=null&&!request.getParameter("cambioCarga").equals("")){
			if (sesion!=null) sesion.setAttribute("cargaId", request.getParameter("CargaId"));		
		}
		String cargaId = (String)sesion.getAttribute("cargaId");	
		if(lisCargas.isEmpty()){
			sesion.setAttribute("cargaId", "");
		}
		
		List<CargaBloque> lisBloques			= cargaBloqueDao.getLista(cargaId, " ORDER BY BLOQUE_ID");
		
		HashMap<String,String> mapaConMaterias	= cargaGrupoDao.mapaPorCargayBloque(cargaId);
		
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisBloques", lisBloques);
		modelo.addAttribute("mapaConMaterias", mapaConMaterias);
		
		return "carga_grupo/bloque/bloque"; 
	}
	
	@RequestMapping("/carga_grupo/bloque/editarBloque")
	public String cargaGrupoBloqueEditarBloque(HttpServletRequest request, Model modelo) {
		
		String cargaId 		= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId"); 
		String bloqueId 	= request.getParameter("BloqueId")==null?"0":request.getParameter("BloqueId");
		String nombreCarga	= cargaDao.mapeaRegId(cargaId).getNombreCarga();
		
		CargaBloque bloque 			= new CargaBloque();		
		if (cargaBloqueDao.existeReg(cargaId, bloqueId)) {
			bloque 			= cargaBloqueDao.mapeaRegId(cargaId, bloqueId);			
		}else {
			bloque.setCargaId(cargaId);
			bloque.setBloqueId(cargaBloqueDao.maximoReg(cargaId));
		}		
		
		modelo.addAttribute("bloque", bloque);
		modelo.addAttribute("nombreCarga", nombreCarga);
		
		return "carga_grupo/bloque/editarBloque";
	}
	
	@RequestMapping("/carga_grupo/bloque/grabarBloque")
	public String cargaGrupoBloqueGrabarBloque(HttpServletRequest request, Model modelo) {
		
		String cargaId 			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String bloqueId 		= request.getParameter("BloqueId")==null?"0":request.getParameter("BloqueId");
		String nombreBloque		= request.getParameter("NombreBloque")==null?"-":request.getParameter("NombreBloque");	
		String fechaInicio		= request.getParameter("FechaInicio")==null?aca.util.Fecha.getHoy():request.getParameter("FechaInicio");
		String fechaCierre		= request.getParameter("FechaCierre")==null?aca.util.Fecha.getHoy():request.getParameter("FechaCierre");
		String fechaFinal		= request.getParameter("FechaFinal")==null?aca.util.Fecha.getHoy():request.getParameter("FechaFinal");		
		String inicioClases		= request.getParameter("InicioClases")==null?aca.util.Fecha.getHoy():request.getParameter("InicioClases");
		String mensaje 			= "-";
		
		CargaBloque bloque 		= new CargaBloque();
		bloque.setCargaId(cargaId);
		bloque.setBloqueId(bloqueId);
		bloque.setNombreBloque(nombreBloque);		
		bloque.setFInicio(fechaInicio);
		bloque.setFCierre(fechaCierre);
		bloque.setFFinal(fechaFinal);	
		bloque.setInicioClases(inicioClases);
		
		if (cargaBloqueDao.existeReg(cargaId, bloqueId)) {
			if (cargaBloqueDao.updateReg(bloque)){
				mensaje = "Updated";
			}else {	
				mensaje = "Error updating";
			}
		}else {
			if (cargaBloqueDao.insertReg(bloque)){
				mensaje = "Saved";
			}else {	
				mensaje = "Error saving";
			}
		}	
		
		return "redirect:/carga_grupo/bloque/editarBloque?CargaId="+cargaId+"&BloqueId="+bloqueId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/carga_grupo/bloque/borrarBloque")
	public String cargaGrupoBloqueBorrarBloque(HttpServletRequest request, Model modelo) {
		
		String cargaId 			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String bloqueId 		= request.getParameter("BloqueId")==null?"0":request.getParameter("BloqueId");
		
		if (cargaBloqueDao.existeReg(cargaId, bloqueId)) {
			cargaBloqueDao.deleteReg(cargaId, bloqueId);
		}		
		
		return "redirect:/carga_grupo/bloque/bloque";
	}
	
}