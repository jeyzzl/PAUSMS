package aca.web.servicio;

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

import aca.ssoc.spring.SsocDocAlumDao;
import aca.ssoc.spring.SsocRequisito;
import aca.ssoc.spring.SsocRequisitoDao;

@Controller
public class ContServicioCatalogos {	
	
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	SsocRequisitoDao ssocRequisitoDao;
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
			//System.out.println("Abrir conEnoc"+url);
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}

	@RequestMapping("/servicio/catalogos/catalogos")
	public String servicioCatalogosCatalogos(HttpServletRequest request, Model modelo){

		List<SsocRequisito> lisRequisitos = ssocRequisitoDao.getListAll("ORDER BY REQUISITO_ID");
		
		modelo.addAttribute("lisRequisitos", lisRequisitos);
		
		return "servicio/catalogos/catalogos";
	}	
	
	@RequestMapping("/servicio/catalogos/editar")
	public String servicioCatalogosEditar(HttpServletRequest request, Model modelo){
		
		String requisitoId 	= request.getParameter("RequisitoId")==null?"0":request.getParameter("RequisitoId");
		String mensaje		= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		
		SsocRequisito requisito = new SsocRequisito();
		
		if(ssocRequisitoDao.existeReg(requisitoId)){
			requisito = ssocRequisitoDao.mapeaRegId(requisitoId);
		}
		
		modelo.addAttribute("requisito", requisito);
		modelo.addAttribute("mensaje", mensaje);
		
		return "servicio/catalogos/editar";
	}	
	
	@RequestMapping("/servicio/catalogos/grabar")
	public String servicioCatalogosGrabar(HttpServletRequest request){
		
		String nombre 		= request.getParameter("RequisitoNombre")==null?"0":request.getParameter("RequisitoNombre");
		String orden 		= request.getParameter("Orden")==null?"0":request.getParameter("Orden");
		String requisitoId 	= request.getParameter("RequisitoId")==null?"0":request.getParameter("RequisitoId");
		String mensaje		= "0";
		
		SsocRequisito requisito = new SsocRequisito();
		
		requisito.setRequisitoId(requisitoId);
		if(requisitoId.equals("0")) {
			requisitoId = ssocRequisitoDao.maximoReg();
			requisito.setRequisitoId(requisitoId);
		}
		requisito.setRequisitoNombre(nombre);
		requisito.setOrden(orden);
		
		if(ssocRequisitoDao.existeReg(requisitoId)){
			if(ssocRequisitoDao.updateReg(requisito)) {
				mensaje = "1";
			}else {
				mensaje = "2";
			}
		}else {
			if(ssocRequisitoDao.insertReg(requisito)) {
				mensaje = "1";
			}else {
				mensaje = "2";
			}
		}
		
		return "redirect:/servicio/catalogos/editar?RequisitoId="+requisitoId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/servicio/catalogos/borrar")
	public String servicioCatalogosRequisito(HttpServletRequest request){
		
		String requisitoId 	= request.getParameter("RequisitoId")==null?"0":request.getParameter("RequisitoId");
		
		if(ssocRequisitoDao.existeReg(requisitoId)){
			ssocRequisitoDao.deleteReg(requisitoId);
		}
		
		return "redirect:/servicio/catalogos/catalogos";
	}	
	
	@RequestMapping("/servicio/catalogos/reglamento")
	public String servicioCatalogosReglamento(HttpServletRequest request, Model modelo){
		
		return "servicio/catalogos/reglamento";
	}	
	
}