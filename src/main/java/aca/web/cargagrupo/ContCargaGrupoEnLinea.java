package aca.web.cargagrupo;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;
import aca.carga.spring.CargaEnLinea;
import aca.carga.spring.CargaEnLineaDao;

@Controller
public class ContCargaGrupoEnLinea {
	
	@Autowired
	CargaEnLineaDao cargaEnLineaDao;
	
	@Autowired
	CargaDao cargaDao;
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}	
	
	@RequestMapping("/carga_grupo/enlinea/accion")
	public String cargaGrupoEnLineaAccion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContCargaGrupoEnLinea|cargaGrupoEnLineaAccion");
		return "carga_grupo/enlinea/accion";
	}
	
	@RequestMapping("/carga_grupo/enlinea/carga")
	public String cargaGrupoEnLineaCarga(HttpServletRequest request, Model modelo){
		
		List<CargaEnLinea> lisCargas    	= 	cargaEnLineaDao.getListAll(" ORDER BY CARGA_ID DESC");
		modelo.addAttribute("lisCargas", lisCargas);
		
		return "carga_grupo/enlinea/carga";
	}
	
	@RequestMapping("/carga_grupo/enlinea/editar")
	public String cargaGrupoEnLineaEditar(HttpServletRequest request, Model modelo) {
		
		String cargaId 		= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");	
		CargaEnLinea carga 	= new CargaEnLinea();
		
		List<Carga> lisCargas    	= 	cargaDao.getListAll("ORDER BY CARGA_ID DESC");
		
		if (cargaEnLineaDao.existeReg(cargaId)) {
			carga = cargaEnLineaDao.mapeaRegId(cargaId);
		}
		
		modelo.addAttribute("carga", carga);
		modelo.addAttribute("lisCargas", lisCargas);
		
		return "carga_grupo/enlinea/editar";
	}
	
	@RequestMapping("/carga_grupo/enlinea/grabar")
	public String cargaGrupoEnLineaGrabar(HttpServletRequest request, Model model){
		
		String cargaId 		= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String nombre		= request.getParameter("Nombre")==null?"-":request.getParameter("Nombre");
		String descripcion	= request.getParameter("Descripcion")==null?"-":request.getParameter("Descripcion");
		String fInicio		= request.getParameter("fInicio")==null?aca.util.Fecha.getHoy():request.getParameter("fInicio");
		String fFinal		= request.getParameter("fFinal")==null?aca.util.Fecha.getHoy():request.getParameter("fFinal");
		String estado		= request.getParameter("Estado")==null?"A":request.getParameter("Estado");
		String carta		= request.getParameter("Carta")==null?"S":request.getParameter("Carta");
		String mensaje 		= "-";
		
		CargaEnLinea cargaEnLinea	= new CargaEnLinea();
		cargaEnLinea.setCargaId(cargaId);
		cargaEnLinea.setNombre(nombre);
		cargaEnLinea.setDescripcion(descripcion);
		cargaEnLinea.setfInicio(fInicio);
		cargaEnLinea.setfFinal(fFinal);
		cargaEnLinea.setEstado(estado);
		cargaEnLinea.setCarta(carta);
		if (cargaEnLineaDao.existeReg(cargaId)) {
			if (cargaEnLineaDao.updateReg(cargaEnLinea)) {
				mensaje = "Load "+cargaEnLinea.getCargaId()+" updated";
			}else{
				mensaje = "Error updating";
			}
		}else {
			if (cargaEnLineaDao.insertReg(cargaEnLinea)) {
				mensaje = "Load "+cargaEnLinea.getCargaId()+" saved";
			}else{
				mensaje = "Error saving";
			}
		}	
		
		return "redirect:/carga_grupo/enlinea/editar?CargaId="+cargaEnLinea.getCargaId()+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/carga_grupo/enlinea/borrar")
	public String cargaGrupoEnLineaBorrar(HttpServletRequest request, Model model){
		
		String cargaId	= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		
		if (cargaEnLineaDao.existeReg(cargaId)) {
			cargaEnLineaDao.deleteReg(cargaId);
		}
		
		return "redirect:/carga_grupo/enlinea/carga";
	}
	
}