package aca.web.comedor;

import java.util.ArrayList;
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
import aca.saum.spring.SaumComida;

@Controller
public class ContComedorSuma {
	
	@Autowired
	aca.saum.spring.SaumRecetaDao saumRecetaDao;
	
	@Autowired
	aca.saum.spring.SaumMateriaDao saumMateriaDao;
	
	@Autowired
	aca.saum.spring.SaumEtapaDao saumEtapaDao;
	
	@Autowired
	aca.saum.spring.SaumIngredienteDao saumIngredienteDao;
	
	@Autowired
	aca.saum.spring.SaumComidaDao saumComidaDao;
	
	@RequestMapping("/comedor/suma/receta")
	public String comedorSumaReceta(HttpServletRequest request, Model modelo){
		
		String fechaIni		=  request.getParameter("FechaIni")==null?"01"+aca.util.Fecha.getHoy().substring(2,10):request.getParameter("FechaIni");
		String fechaFin		=  request.getParameter("FechaFin")==null?aca.util.Fecha.getHoy():request.getParameter("FechaFin");
		
		List<SaumComida> lisComidas 			= saumComidaDao.lisComidas(fechaIni, fechaFin, " ORDER BY ENOC.SAUM_COMIDA.FECHA, COMIDA");
		HashMap<String,String> mapaRecetas		= saumRecetaDao.mapaRecetas();
		
		modelo.addAttribute("lisComidas",lisComidas);
		modelo.addAttribute("mapaRecetas", mapaRecetas);
		
		return "comedor/suma/receta";
	}
	
	@RequestMapping("/comedor/suma/editar")
	public String comedorSumaEditar(HttpServletRequest request, Model modelo){		
		
		List<SaumReceta> lisRecetas 			= saumRecetaDao.listAll(" ORDER BY NOMBRE");
		
		modelo.addAttribute("lisRecetas",lisRecetas);
		
		return "comedor/suma/editar";
	}
	
	@RequestMapping("/comedor/suma/grabar")
	public String comedorSumaSumar(HttpServletRequest request, Model modelo){
		
		String fechaIni		=  request.getParameter("FechaIni")==null?aca.util.Fecha.getHoy():request.getParameter("FechaIni");
		String fechaFin		=  request.getParameter("FechaFin")==null?aca.util.Fecha.getHoy():request.getParameter("FechaFin");
		String recetaId		= request.getParameter("RecetaId")==null?"0":request.getParameter("RecetaId");		
		String fecha		= request.getParameter("Fecha")==null?"0":request.getParameter("Fecha");
		String rendimiento	= request.getParameter("Rendimiento")==null?"0":request.getParameter("Rendimiento");
		String tipo			= request.getParameter("Comida")==null?"0":request.getParameter("Comida");
		
		String mensaje 		= "-";
		
		SaumComida comida 	= new SaumComida();
		
		comida.setFolio(saumComidaDao.maximoReg());
		comida.setFecha(fecha);
		comida.setRecetaId(recetaId);
		comida.setRendimiento(rendimiento);
		comida.setComida(tipo);
		
		if (saumComidaDao.insertReg(comida) ){
			mensaje = "¡ Grabado !";
		}
		
		return "redirect:/comedor/suma/editar?mensaje="+mensaje+"&FechaIni="+fechaIni+"&FechaFin="+fechaFin;
	}
	
	@RequestMapping("/comedor/suma/calcular")
	public String comedorSumaCalcular(HttpServletRequest request, Model modelo){
		
		String fechaIni		=  request.getParameter("FechaIni")==null?aca.util.Fecha.getHoy():request.getParameter("FechaIni");
		String fechaFin		=  request.getParameter("FechaFin")==null?aca.util.Fecha.getHoy():request.getParameter("FechaFin");
		String recetaId		=  request.getParameter("RecetaId")==null?"0":request.getParameter("RecetaId");
		String recetaNombre	= "-";
		
		List<SaumComida> lisComidas 		= new ArrayList<SaumComida>();
		if (recetaId.equals("0")){
			// Lista de comidas en un rango de fechas
			lisComidas 		= saumComidaDao.lisComidas(fechaIni, fechaFin, " ORDER BY ENOC.SAUM_COMIDA.FECHA, COMIDA");
		}else{
			// Trae una sola comida
			lisComidas 		= saumComidaDao.lisComidas(recetaId);
			recetaNombre	= saumRecetaDao.getNombre(recetaId);
		}
		List<SaumIngrediente> lisIngredientes	= null;
		HashMap<String,SaumReceta> mapaRecetas	= saumRecetaDao.mapaRecetasCompletas();
		HashMap<String,String> mapaIngrediente	= new HashMap<String,String>();
		HashMap<String,SaumMateria> mapaMateria	= saumMateriaDao.mapaMateria();
		
		for (SaumComida comida : lisComidas){
			float rendimiento = 0;
			if (mapaRecetas.containsKey(comida.getRecetaId())){				
				rendimiento = Float.valueOf( mapaRecetas.get(comida.getRecetaId()).getRendimiento() );		
			}
			float porcentaje = Float.valueOf(comida.getRendimiento()) / Float.valueOf(rendimiento);
			
			lisIngredientes = saumIngredienteDao.lisIngredientesReceta(comida.getRecetaId(), " ORDER BY MATERIA_ID");			
			//System.out.println("Rendimiento receta: "+rendimiento+" sol:"+comida.getRendimiento()+" %"+porcentaje+"::"+lisIngredientes.size());			
			
			float cantidad 	= 0;
			float suma		= 0; 
			
			for (SaumIngrediente ing : lisIngredientes){
				
				String strCantidad = "0";
				if (mapaIngrediente.containsKey(ing.getMateriaId())){
					strCantidad = mapaIngrediente.get(ing.getMateriaId());
					
					if (ing.getUnidadMedida().equals("kg") || ing.getUnidadMedida().equals("g")){
						if (ing.getUnidadMedida().equals("kg"))
							cantidad = Float.valueOf(ing.getCantidad()) * 1000 * porcentaje;
						else
							cantidad = Float.valueOf(ing.getCantidad()) * porcentaje;						
						suma = cantidad + Float.valueOf(strCantidad.replace("g",""));
						mapaIngrediente.put(ing.getMateriaId(), String.valueOf(suma)+"g");						
					}else if (ing.getUnidadMedida().equals("l") || ing.getUnidadMedida().equals("ml")){
						if (ing.getUnidadMedida().equals("l")){
							cantidad = Float.valueOf(ing.getCantidad()) * 1000 * porcentaje;
						}else{
							cantidad = Float.valueOf(ing.getCantidad()) * porcentaje;
						}						
						suma = cantidad + Float.valueOf(strCantidad.replace("ml",""));
						mapaIngrediente.put(ing.getMateriaId(), String.valueOf(suma)+"ml");
					}else{
						cantidad = Float.valueOf(ing.getCantidad()) * porcentaje;
						suma = cantidad + Float.valueOf(strCantidad.replace("pza",""));
						mapaIngrediente.put(ing.getMateriaId(), String.valueOf(suma)+"pza");
					}					
					//System.out.println(ing.getMateriaId()+" Tenia:"+strCantidad+" sumar:"+cantidad+ing.getUnidadMedida()+" Total::"+suma);			
				}else{
					if (ing.getUnidadMedida().equals("kg")){
						cantidad = Float.valueOf(ing.getCantidad()) * 1000 * porcentaje;
						mapaIngrediente.put(ing.getMateriaId(), cantidad+"g");
					}else if (ing.getUnidadMedida().equals("l")){
						cantidad = Float.valueOf(ing.getCantidad()) * 1000 * porcentaje;
						mapaIngrediente.put(ing.getMateriaId(), cantidad+"ml");
					}else{
						cantidad = Float.valueOf(ing.getCantidad()) * porcentaje;
						mapaIngrediente.put(ing.getMateriaId(), String.valueOf(cantidad)+ing.getUnidadMedida());
					}
					//System.out.println("Nuevo:"+ing.getMateriaId()+":"+cantidad+ing.getUnidadMedida());
				}
			}			
		}
		
		modelo.addAttribute("lisComidas",lisComidas);
		modelo.addAttribute("mapaRecetas", mapaRecetas);
		modelo.addAttribute("mapaIngrediente", mapaIngrediente);
		modelo.addAttribute("mapaMateria", mapaMateria);		
		modelo.addAttribute("recetaNombre", recetaNombre);
		
		return "comedor/suma/calcular";
	}	
	
	@RequestMapping("/comedor/suma/borrar")
	public String comedorSumaBorrar(HttpServletRequest request, Model modelo){
		
		String folio		=  request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String fechaIni		=  request.getParameter("FechaIni")==null?aca.util.Fecha.getHoy():request.getParameter("FechaIni");
		String fechaFin		=  request.getParameter("FechaFin")==null?aca.util.Fecha.getHoy():request.getParameter("FechaFin");
		
		System.out.println("Fechas:"+fechaIni+"::"+fechaFin);
		
		if (saumComidaDao.existeReg(folio)){
			saumComidaDao.deleteReg(folio);
		}
		
		return "redirect:/comedor/suma/receta?FechaIni="+fechaIni+"&FechaFin="+fechaFin;
	}
	
	@RequestMapping("/comedor/suma/imprimir")
	public String comedorSumaImprimir(HttpServletRequest request, Model modelo){
		
		String recetaId				=  request.getParameter("RecetaId")==null?"0":request.getParameter("RecetaId");
		String rendimientoComida	=  request.getParameter("Rendimiento")==null?"0":request.getParameter("Rendimiento");
		String rendimientoReceta	= "0";
		
		SaumReceta receta 	= new SaumReceta();
		if (saumRecetaDao.existeReg(recetaId) ){
			receta = saumRecetaDao.mapeaRegId(recetaId);
			rendimientoReceta = receta.getRendimiento();
		}		
		
		List<SaumIngrediente> lisIngredientes	= saumIngredienteDao.lisIngredientesReceta(recetaId, " ORDER BY MATERIA_ID");		
		List<SaumEtapa> lisEtapas				= saumEtapaDao.lisReceta(recetaId, "ORDER BY ID");
		
		HashMap<String,String> mapaIngrediente	= new HashMap<String,String>();
		HashMap<String,SaumMateria> mapaMateria	= saumMateriaDao.mapaMateria();		
				
		float porcentaje = Float.valueOf(rendimientoComida) / Float.valueOf(rendimientoReceta);					
			
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
				//System.out.println("Nuevo:"+ing.getMateriaId()+":"+cantidad+ing.getUnidadMedida());
			}
		}	
		
		
		modelo.addAttribute("receta", receta);
		modelo.addAttribute("lisEtapas", lisEtapas);
		modelo.addAttribute("lisIngredientes", lisIngredientes);
		modelo.addAttribute("mapaIngrediente", mapaIngrediente);
		modelo.addAttribute("mapaMateria", mapaMateria);
		
		return "comedor/suma/imprimir";
	}
}