package aca.web.finanzas;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumDescuentoDao;
import aca.catalogo.spring.CatDescuento;
import aca.catalogo.spring.CatDescuentoDao;

@Controller
public class ContFinanzasTipo {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired	
	private CatDescuentoDao catDescuentoDao;
	
	@Autowired
	AlumDescuentoDao alumDescuentoDao;	
	
	
	@RequestMapping("/finanzas/tipo/accion")
	public String finanzasTipoAccion(HttpServletRequest request, Model modelo){
		String descuentoId		= request.getParameter("DescuentoId")==null?"0":request.getParameter("DescuentoId");
		CatDescuento catDescuento = new CatDescuento();
		if (catDescuentoDao.existeReg(descuentoId)){
			catDescuento = catDescuentoDao.mapeaRegId(descuentoId);
		}
		
		modelo.addAttribute("catDescuento", catDescuento);
		
		return "finanzas/tipo/accion";
	}
	
	@RequestMapping("/finanzas/tipo/descuentos")
	public String finanzasTipoDescuentos(HttpServletRequest request, Model modelo){
				
		List<CatDescuento> lisDescuentos		= catDescuentoDao.getListAll("ORDER BY DESCUENTO_ID");
		HashMap<String,String> mapaDescuentos	= alumDescuentoDao.mapaDescuentos();
		
		modelo.addAttribute("lisDescuentos", lisDescuentos);
		modelo.addAttribute("mapaDescuentos", mapaDescuentos);
		
		return "finanzas/tipo/descuentos";
	}
	
	@RequestMapping("/finanzas/tipo/grabar")
	public String finanzasTipoGrabar(HttpServletRequest request){
		
		String descuentoId 	= request.getParameter("DescuentoId")==null?"0":request.getParameter("DescuentoId");
		String nombre 		= request.getParameter("Nombre")==null?"0":request.getParameter("Nombre");
		String usuarios 	= request.getParameter("Usuario")==null?"0":request.getParameter("Usuario");
		String mensaje 		= "-";
		CatDescuento catDescuento = new CatDescuento();
		catDescuento.setDescuentoNombre(nombre);
		catDescuento.setDescuentoId(descuentoId);
		catDescuento.setUsuarios(usuarios+"-");
		if(catDescuentoDao.existeReg(descuentoId)==false){
			descuentoId = catDescuentoDao.maximoReg();	
			catDescuento.setDescuentoId(descuentoId);
			if(catDescuentoDao.insertReg(catDescuento)){
				mensaje = "<div class='alert alert-success'>Registro grabado</div>";				
			}else{
				mensaje = "<div class='alert alert-danger'>Error al grabar el registro</div>";			
			}
		}else{//Modifica
			if(catDescuentoDao.updateReg(catDescuento)){
				mensaje = "<div class='alert alert-success'>Registro modificado</div>";					
			}else{
				mensaje = "<div class='alert alert-danger'>Error al modificar el registro</div>";
			}		
		}
		
		return "redirect:/finanzas/tipo/accion?DescuentoId="+descuentoId+"&Mensaje="+mensaje;
	}	
	
	@RequestMapping("/finanzas/tipo/borrar")
	public String finanzasTipoBorrar(HttpServletRequest request){
		
		String descuentoId 	= request.getParameter("DescuentoId")==null?"0":request.getParameter("DescuentoId");
		String mensaje 		= "-";
		
		if( catDescuentoDao.existeReg( descuentoId)){
			if(catDescuentoDao.deleteReg(descuentoId)){
				mensaje = "<div class='alert alert-success'>Registro Eliminado</div>";	
			}else{
				mensaje = "<div class='alert alert-danger'>Ocurrió un error al eliminar el registro</div>";
			}
		}		
		return "redirect:/finanzas/tipo/descuentos?Mensaje="+mensaje;
	}
}