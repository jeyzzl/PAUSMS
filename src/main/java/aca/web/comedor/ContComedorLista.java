package aca.web.comedor;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.saum.spring.SaumEtapa;
import aca.saum.spring.SaumMateria;
import aca.saum.spring.SaumReceta;
import aca.saum.spring.SaumIngrediente;

@Controller
public class ContComedorLista {	
	
	@Autowired
	aca.saum.spring.SaumRecetaDao saumRecetaDao;
	
	@Autowired
	aca.saum.spring.SaumMateriaDao saumMateriaDao;
	
	@Autowired
	aca.saum.spring.SaumEtapaDao saumEtapaDao;
	
	@Autowired
	aca.saum.spring.SaumIngredienteDao saumIngredienteDao;
	
	@RequestMapping("/comedor/lista/receta")
	public String comedorListaReceta(HttpServletRequest request, Model modelo){		
		
		List<SaumReceta> lisRecetas 			= saumRecetaDao.listAll(" ORDER BY NOMBRE");
		HashMap<String,String> mapaEtapas		= saumEtapaDao.mapaEtapasPorReceta();
		HashMap<String,String> mapaIngredientes	= saumIngredienteDao.mapaIngredientesPorReceta();	
						
		modelo.addAttribute("lisRecetas",lisRecetas);	
		modelo.addAttribute("mapaEtapas",mapaEtapas);
		modelo.addAttribute("mapaIngredientes",mapaIngredientes);
		
		return "comedor/lista/receta";
	}	
	
	@RequestMapping("/comedor/lista/proceso")
	public String comedorListaProceso(HttpServletRequest request, Model modelo){		
				
		String recetaId				= request.getParameter("RecetaId")==null?"0":request.getParameter("RecetaId");		
		SaumReceta receta			= saumRecetaDao.mapeaRegId(recetaId);
		String rendimientoPedido	= request.getParameter("Rendimiento")==null?receta.getRendimiento():request.getParameter("Rendimiento");
		
		List<SaumEtapa> lisEtapas 				= saumEtapaDao.lisReceta(recetaId, " ORDER BY NOMBRE");
		List<SaumIngrediente> lisIngredientes	= saumIngredienteDao.lisIngredientesReceta(recetaId, " ORDER BY MATERIA_ID");
		HashMap<String,SaumMateria> mapaMateria	= saumMateriaDao.mapaMateria();
		HashMap<String,String> mapaIngrediente	= new HashMap<String,String>();
		
		
		float porcentaje = Float.valueOf(rendimientoPedido) / Float.valueOf(receta.getRendimiento());					
		
		float cantidad 	= 0;
		float suma		= 0; 
			
		for (SaumIngrediente ing : lisIngredientes){
			
			String strCantidad = "0";
			if (mapaIngrediente.containsKey(ing.getEtapaId()+ing.getMateriaId())){
				strCantidad = mapaIngrediente.get(ing.getMateriaId());
				
				if (ing.getUnidadMedida().equals("kg") || ing.getUnidadMedida().equals("g")){
					if (ing.getUnidadMedida().equals("kg"))
						cantidad = Float.valueOf(ing.getCantidad()) * 1000 * porcentaje;
					else
						cantidad = Float.valueOf(ing.getCantidad()) * porcentaje;						
					suma = cantidad + Float.valueOf(strCantidad.replace("g",""));
					mapaIngrediente.put(ing.getEtapaId()+ing.getMateriaId(), String.valueOf(suma)+"g");						
				}else if (ing.getUnidadMedida().equals("l") || ing.getUnidadMedida().equals("ml")){
					if (ing.getUnidadMedida().equals("l")){
						cantidad = Float.valueOf(ing.getCantidad()) * 1000 * porcentaje;
					}else{
						cantidad = Float.valueOf(ing.getCantidad()) * porcentaje;
					}						
					suma = cantidad + Float.valueOf(strCantidad.replace("ml",""));
					mapaIngrediente.put(ing.getEtapaId()+ing.getMateriaId(), String.valueOf(suma)+"ml");
				}else{
					cantidad = Float.valueOf(ing.getCantidad()) * porcentaje;
					suma = cantidad + Float.valueOf(strCantidad.replace("pza",""));
					mapaIngrediente.put(ing.getEtapaId()+ing.getMateriaId(), String.valueOf(suma)+"pza");
				}					
				//System.out.println(ing.getMateriaId()+" Tenia:"+strCantidad+" sumar:"+cantidad+ing.getUnidadMedida()+" Total::"+suma);			
			}else{
				if (ing.getUnidadMedida().equals("kg")){
					cantidad = Float.valueOf(ing.getCantidad()) * 1000 * porcentaje;
					mapaIngrediente.put(ing.getEtapaId()+ing.getMateriaId(), cantidad+"g");
				}else if (ing.getUnidadMedida().equals("l")){
					cantidad = Float.valueOf(ing.getCantidad()) * 1000 * porcentaje;
					mapaIngrediente.put(ing.getEtapaId()+ing.getMateriaId(), cantidad+"ml");
				}else{
					cantidad = Float.valueOf(ing.getCantidad()) * porcentaje;
					mapaIngrediente.put(ing.getEtapaId()+ing.getMateriaId(), String.valueOf(cantidad)+ing.getUnidadMedida());
				}				
			}
		}		
		
		modelo.addAttribute("receta",receta);		
		modelo.addAttribute("lisEtapas",lisEtapas);
		modelo.addAttribute("lisIngredientes",lisIngredientes);
		modelo.addAttribute("mapaMateria",mapaMateria);
		modelo.addAttribute("mapaIngrediente",mapaIngrediente);
		
		return "comedor/lista/proceso";
	}
	
}