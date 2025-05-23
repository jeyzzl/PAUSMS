package aca.web.bitacora;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.bitacora.spring.BitRequisito;
import aca.bitacora.spring.BitTramite;

@Controller
public class ContBitacoraRequisitos {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	aca.bitacora.spring.BitRequisitoDao bitRequisitoDao;
	
	@Autowired
	aca.bitacora.spring.BitTramiteDao bitTramiteDao;
	
	@Autowired
	aca.bitacora.spring.BitTramiteRequisitoDao bitTramiteRequisitoDao;
	
	@RequestMapping("/bitacora/requisito/listado")
	public String bitacoraRequisitoListado(HttpServletRequest request, Model modelo){
		List<BitRequisito> lisRequisito			= bitRequisitoDao.lisRequisitos("");
		

		HashMap<String, String> mapaRequisitoAsignados = bitTramiteRequisitoDao.mapaRequisitoAsignados();
		
		modelo.addAttribute("lisRequisito", lisRequisito);
		modelo.addAttribute("mapaRequisitoAsignados", mapaRequisitoAsignados);
		
		return "bitacora/requisito/listado";
	}	
	
	@RequestMapping("/bitacora/requisito/editar")
	public String bitacoraRequisitoEditar(HttpServletRequest request, Model modelo){
		String requisitoId 				= request.getParameter("RequisitoId")==null?"0":request.getParameter("RequisitoId");
		String mensaje	 				= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");

		BitRequisito requisito 	= new BitRequisito();
		
		if (bitRequisitoDao.existeReg(requisitoId)) {
			requisito = bitRequisitoDao.mapeaRegId(requisitoId);
		}
		
		modelo.addAttribute("requisito", requisito);
		modelo.addAttribute("mensaje", mensaje);
		
		return "bitacora/requisito/editar";
	}
	
	@RequestMapping("/bitacora/requisito/grabar")
	public String bitacoraRequisitoGrabar(HttpServletRequest request, Model modelo){
		String requisitoId 	= request.getParameter("RequisitoId")==null?"0":request.getParameter("RequisitoId");
		String nombre 		= request.getParameter("Nombre")==null?"-":request.getParameter("Nombre");
		String tipo		 	= request.getParameter("Tipo")==null?"0":request.getParameter("Tipo");
		String comentario 	= request.getParameter("Comentario")==null?"":request.getParameter("Comentario");
		String mensaje		= "0";
		
		BitRequisito requisito	= new BitRequisito();
		if(requisitoId.equals("0")) {
			requisitoId = bitRequisitoDao.maximoReg();
		}
		requisito.setRequisitoId(requisitoId);
		requisito.setNombre(nombre);
		requisito.setTipo(tipo);
		requisito.setComentario(comentario);
		
		if (bitRequisitoDao.existeReg(requisitoId)) {
			if(bitRequisitoDao.updateReg(requisito)) {
				mensaje	= "1";
			}else {
				mensaje	= "2";
			}
		} else {
			if(bitRequisitoDao.insertReg(requisito)) {
				mensaje	= "1";
			}else {
				mensaje	= "2";
			}
		}
		
		return "redirect:/bitacora/requisito/editar?RequisitoId="+requisitoId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/bitacora/requisito/borrar")
	public String bitacoraRequisitoBorrar(HttpServletRequest request, Model modelo){
		String requisitoId 	= request.getParameter("RequisitoId")==null?"0":request.getParameter("RequisitoId");
		
		if (bitRequisitoDao.existeReg(requisitoId)) {
			bitRequisitoDao.deleteReg(requisitoId);
		}
		
		return "redirect:/bitacora/requisito/listado";
	}
}