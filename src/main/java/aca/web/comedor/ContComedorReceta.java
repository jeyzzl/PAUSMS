package aca.web.comedor;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import aca.saum.spring.SaumEtapa;
import aca.saum.spring.SaumMateria;
import aca.saum.spring.SaumReceta;
import aca.saum.spring.SaumIngrediente;

@Controller
public class ContComedorReceta {
	
	@Autowired
	aca.saum.spring.SaumRecetaDao saumRecetaDao;
	
	@Autowired
	aca.saum.spring.SaumMateriaDao saumMateriaDao;
	
	@Autowired
	aca.saum.spring.SaumEtapaDao saumEtapaDao;
	
	@Autowired
	aca.saum.spring.SaumIngredienteDao saumIngredienteDao;
	
	@RequestMapping("/comedor/receta/lista")
	public String comedorRecetaLista(HttpServletRequest request, Model modelo){
		List<SaumReceta> lisRecetas 			= saumRecetaDao.listAll(" ORDER BY NOMBRE");
		HashMap<String,String> mapaEtapas		= saumEtapaDao.mapaEtapasPorReceta();
		HashMap<String,String> mapaIngredientes	= saumIngredienteDao.mapaIngredientesPorReceta();
		HashMap<String,String> mapaImagenes		= saumRecetaDao.mapaImagenes();
		
		modelo.addAttribute("lisRecetas",lisRecetas);	
		modelo.addAttribute("mapaEtapas",mapaEtapas);
		modelo.addAttribute("mapaIngredientes",mapaIngredientes);
		modelo.addAttribute("mapaImagenes",mapaImagenes);
		
		return "comedor/receta/lista";
	}	
	
	@RequestMapping("/comedor/receta/editar")
	public String comedorRecetaEditar(HttpServletRequest request, Model modelo){
		
		String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		String recetaId			= request.getParameter("RecetaId")==null?"0":request.getParameter("RecetaId");
		
		SaumReceta receta = new SaumReceta(); 
		
		if (accion.equals("0")){
			receta.setId( saumRecetaDao.maximoReg() );
		}else{			
			receta = saumRecetaDao.mapeaRegId(recetaId);
		}
		
		modelo.addAttribute("receta",receta);
		
		return "comedor/receta/editar";
	}
	
	@RequestMapping("/comedor/receta/grabar")
	public String comedorRecetaGrabar(HttpServletRequest request){
		
		String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		String id 				= request.getParameter("Id")==null?"0":request.getParameter("Id");
		String nombre			= request.getParameter("Nombre")==null?"0":request.getParameter("Nombre");
		String calorias 		= request.getParameter("Calorias")==null?"0":request.getParameter("Calorias");
		String carbohidratos 	= request.getParameter("Carbohidratos")==null?"0":request.getParameter("Carbohidratos");
		String colesterol 		= request.getParameter("Colesterol")==null?"0":request.getParameter("Colesterol");
		String color 			= request.getParameter("Color")==null?"0":request.getParameter("Color");
		String fibra 			= request.getParameter("Fibra")==null?"0":request.getParameter("Fibra");
		String forma 			= request.getParameter("Forma")==null?"0":request.getParameter("Forma");
		String grasa 			= request.getParameter("Grasa")==null?"0":request.getParameter("Grasa");
		String porcion 			= request.getParameter("Porcion")==null?"0":request.getParameter("Porcion");
		String proteina 		= request.getParameter("Proteina")==null?"0":request.getParameter("Proteina");
		String rendimiento 		= request.getParameter("Rendimiento")==null?"0":request.getParameter("Rendimiento");
		String sabor 			= request.getParameter("Sabor")==null?"0":request.getParameter("Sabor");
		String sodio 			= request.getParameter("Sodio")==null?"0":request.getParameter("Sodio");
		String temperatura 		= request.getParameter("Temperatura")==null?"0":request.getParameter("Temperatura");
		String textura 			= request.getParameter("Textura")==null?"0":request.getParameter("Textura");
		String tiempo 			= request.getParameter("Tiempo")==null?"0":request.getParameter("Tiempo");
		String tipoPlato 		= request.getParameter("TipoPlato")==null?"0":request.getParameter("TipoPlato");
		
		SaumReceta receta 	= new SaumReceta();		
		receta.setNombre(nombre);
		receta.setCalorias(calorias);
		receta.setCarbohidratos(carbohidratos);
		receta.setColesterol(colesterol);
		receta.setColor(color);
		receta.setFibra(fibra);
		receta.setForma(forma);
		receta.setGrasa(grasa);
		receta.setPorcion(porcion);
		receta.setProteinas(proteina);
		receta.setRendimiento(rendimiento);
		receta.setSabor(sabor);
		receta.setSodio(sodio);
		receta.setTemperatura(temperatura);
		receta.setTextura(textura);
		receta.setTiempo(tiempo);
		receta.setTipoPlato(tipoPlato);
		receta.setVersion("0");
		
		if (accion.equals("1")){
			receta.setId(saumRecetaDao.maximoReg());
			saumRecetaDao.insertReg(receta);			
		}else{
			receta.setId(id);
			saumRecetaDao.updateReg(receta);
		}
		
		return "redirect:/comedor/receta/lista";
	}
	
	@RequestMapping("/comedor/receta/borrar")
	public String comedorRecetaBorrar(HttpServletRequest request, Model modelo){		
		String id			= request.getParameter("Id")==null?"0":request.getParameter("Id");		
		if (saumRecetaDao.existeReg(id)){
			saumRecetaDao.deleteReg(id);
		}
		
		return "redirect:/comedor/receta/lista";
	}
	
	@RequestMapping("/comedor/receta/etapas")
	public String comedorRecetaEtapas(HttpServletRequest request, Model modelo){		
		
		String recetaId			= request.getParameter("RecetaId")==null?"0":request.getParameter("RecetaId");
		String recetaNombre		= saumRecetaDao.getNombre(recetaId);
		
		List<SaumEtapa> lisEtapas 				= saumEtapaDao.lisReceta(recetaId, " ORDER BY NOMBRE");
		HashMap<String,String> mapaIngrediente	= saumIngredienteDao.mapaIngredientesPorEtapa();
		List<SaumIngrediente> lisIngredientes	= saumIngredienteDao.lisIngredientesReceta(recetaId, " ORDER BY MATERIA_ID");
		HashMap<String,SaumMateria> mapaMateria	= saumMateriaDao.mapaMateria();
		
		modelo.addAttribute("lisEtapas",lisEtapas);			
		modelo.addAttribute("recetaNombre",recetaNombre);
		modelo.addAttribute("mapaIngrediente",mapaIngrediente);
		modelo.addAttribute("lisIngredientes",lisIngredientes);
		modelo.addAttribute("mapaMateria",mapaMateria);
		
		return "comedor/receta/etapas";
	}
	
	@RequestMapping("/comedor/receta/editarEtapa")
	public String comedorRecetaEditarEtapas(HttpServletRequest request, Model modelo){		
		
		String etapaId		= request.getParameter("EtapaId")==null?"0":request.getParameter("EtapaId");
		String accion		= request.getParameter("Accion")==null?"0":request.getParameter("Accion");		
		
		SaumEtapa etapa 	= new SaumEtapa();
		if (accion.equals("0")){
			etapa.setId(saumEtapaDao.maximoReg());
		}else{
			etapa = saumEtapaDao.mapeaRegId(etapaId);
		}
		
		modelo.addAttribute("etapa", etapa);	
		
		return "comedor/receta/editarEtapa";
	}
	
	@RequestMapping("/comedor/receta/grabarEtapa")
	public String comedorRecetaGrabarEtapa(HttpServletRequest request, Model modelo){		
		
		String accion			= request.getParameter("Accion")==null?"-":request.getParameter("Accion");
		String recetaId			= request.getParameter("RecetaId")==null?"-":request.getParameter("RecetaId");
		String etapaId			= request.getParameter("Id")==null?"0":request.getParameter("Id");		
		String nombre			= request.getParameter("Nombre")==null?"-":request.getParameter("Nombre");
		String procedimiento	= request.getParameter("Procedimiento")==null?"-":request.getParameter("Procedimiento");
		
		SaumEtapa etapa 	= new SaumEtapa();
		etapa.setRecetaId(recetaId);		
		etapa.setNombre(nombre);
		etapa.setProcedimiento(procedimiento);
		etapa.setVersion("0");
		
		if (accion.equals("1")){
			etapa.setId(saumEtapaDao.maximoReg());
			saumEtapaDao.insertReg(etapa);			
		}else{
			etapa.setId(etapaId);
			saumEtapaDao.updateReg(etapa);
		}
		
		return "redirect:/comedor/receta/etapas?RecetaId="+recetaId;
	}
	
	@RequestMapping("/comedor/receta/borrarEtapa")
	public String comedorRecetaBorrarEtapa(HttpServletRequest request, Model modelo){		
		String recetaId		= request.getParameter("RecetaId")==null?"0":request.getParameter("RecetaId");		
		String etapaId		= request.getParameter("EtapaId")==null?"0":request.getParameter("EtapaId");
		if (saumEtapaDao.existeReg(etapaId)){
			saumEtapaDao.deleteReg(etapaId);
		}
		
		return "redirect:/comedor/receta/etapas?RecetaId="+recetaId;
	}
	
	@RequestMapping("/comedor/receta/subirImagen")
	public String comedorRecetaSubirImagen(HttpServletRequest request, Model modelo){		
		String id 			= request.getParameter("Id")==null?"0":request.getParameter("Id");	
		
		SaumReceta receta = new SaumReceta();
		if (saumRecetaDao.existeReg(id)) {
			receta = saumRecetaDao.mapeaRegId(id);
		}	
		
		modelo.addAttribute("receta", receta);
		
		return "comedor/receta/subirImagen";
	}
	
	@PostMapping("/comedor/receta/guardarImagen")
	public String comedorRecetaGuardarImagen(@RequestParam("imagen") MultipartFile imagen, HttpServletRequest request){
		String id 			= request.getParameter("Id")==null?"0":request.getParameter("Id");		
		try{						
			if (saumRecetaDao.existeReg(id)){
				saumRecetaDao.updateImagen(id,imagen.getBytes());
			}
		}catch(Exception ex){
			System.out.println("Error al grabar imagen: "+ex);
		}
		
		return "redirect:/comedor/receta/lista";
	}
	
	@RequestMapping("/comedor/receta/borrarImagen")
	public String comedorRecetaBorrarImagen(HttpServletRequest request, Model modelo){		
		String id		= request.getParameter("id")==null?"0":request.getParameter("id");		
		try {
			if (saumRecetaDao.existeImagen(id)){
				saumRecetaDao.updateImagenVacia(id);
			}
		}catch(Exception ex){
			System.out.println("Error:documentosBorrarImagen:"+ex);
		}		
		return "redirect:/comedor/receta/lista";		
	}
	
	@RequestMapping("/comedor/receta/verImagen")
	public String comedorRecetaVerImagen(HttpServletRequest request, Model modelo){
		String id		= request.getParameter("id")==null?"0":request.getParameter("id");
		
        SaumReceta receta 		= new SaumReceta();
        byte imagenByte[] 		= null;        
        boolean tieneImagen		= false;        		
        
        // Buscar la imagen
        if (saumRecetaDao.existeImagen(id)){
        	tieneImagen = true;
        	receta = saumRecetaDao.mapeaRegLargo(id);
        	imagenByte = receta.getImagen();
        }
        
		modelo.addAttribute("tieneImagen",tieneImagen);
		modelo.addAttribute("imagenByte",imagenByte);
		modelo.addAttribute("receta",receta);
		
		return "comedor/receta/verImagen";
	}
	
	
	
	
	@RequestMapping("/comedor/receta/ingredientes")
	public String comedorRecetaIngredientes(HttpServletRequest request, Model modelo){		
		
		String recetaId			= request.getParameter("RecetaId")==null?"0":request.getParameter("RecetaId");
		String etapaId			= request.getParameter("EtapaId")==null?"0":request.getParameter("EtapaId");
		String recetaNombre		= saumRecetaDao.getNombre(recetaId);
		String etapaNombre		= saumEtapaDao.getNombre(etapaId);
		
		List<SaumIngrediente> lisIngredientes				= saumIngredienteDao.lisIngredientes(etapaId, " ORDER BY MATERIA_ID");
		HashMap<String,SaumMateria> mapaMateria				= saumMateriaDao.mapaMateria();		
		
		modelo.addAttribute("etapaNombre",etapaNombre);	
		modelo.addAttribute("recetaNombre",recetaNombre);
		modelo.addAttribute("lisIngredientes",lisIngredientes);
		modelo.addAttribute("mapaMateria",mapaMateria);
		
		return "comedor/receta/ingredientes";
	}
	
	@RequestMapping("/comedor/receta/editarIngrediente")
	public String comedorRecetaEditarIngrediente(HttpServletRequest request, Model modelo){
		String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		String recetaId			= request.getParameter("RecetaId")==null?"0":request.getParameter("RecetaId");
		String etapaId			= request.getParameter("EtapaId")==null?"0":request.getParameter("EtapaId");
		String ingredienteId	= request.getParameter("IngredienteId")==null?"0":request.getParameter("IngredienteId");
		
		String recetaNombre		= saumRecetaDao.getNombre(recetaId);
		String etapaNombre		= saumEtapaDao.getNombre(etapaId);
		
		List<SaumMateria> lisMaterias 			= saumMateriaDao.listAll(" ORDER BY NOMBRE");		
		SaumIngrediente ingrediente  = new SaumIngrediente();
		
		if (accion.equals("0")){
			ingredienteId = saumIngredienteDao.maximoReg();
			ingrediente.setId(ingredienteId);
		}else{
			ingrediente = saumIngredienteDao.mapeaRegId(ingredienteId);
		}
		modelo.addAttribute("ingrediente", ingrediente);
		modelo.addAttribute("lisMaterias", lisMaterias);
		modelo.addAttribute("recetaNombre", recetaNombre);
		modelo.addAttribute("etapaNombre", etapaNombre);
		
		return "comedor/receta/editarIngrediente";
	}
	
	@RequestMapping("/comedor/receta/grabarIngrediente")
	public String comedorRecetaGrabarIngrediente(HttpServletRequest request, Model modelo){
		
		String accion			= request.getParameter("Accion")==null?"-":request.getParameter("Accion");
		String recetaId			= request.getParameter("RecetaId")==null?"0":request.getParameter("RecetaId");
		String etapaId			= request.getParameter("EtapaId")==null?"0":request.getParameter("EtapaId");
		String ingredienteId	= request.getParameter("IngredienteId")==null?"0":request.getParameter("IngredienteId");
		String materiaId		= request.getParameter("MateriaId")==null?"0":request.getParameter("MateriaId");
		String presentacion		= request.getParameter("Presentacion")==null?"0":request.getParameter("Presentacion");
		String cantidad			= request.getParameter("Cantidad")==null?"0":request.getParameter("Cantidad");
		String unidadMedida		= request.getParameter("UnidadMedida")==null?"0":request.getParameter("UnidadMedida");		
		
		SaumIngrediente ing = new SaumIngrediente();		
		ing.setVersion("0");
		ing.setRecetaId(recetaId);
		ing.setEtapaId(etapaId);
		ing.setMateriaId(materiaId);
		ing.setPresentacion(presentacion);
		ing.setCantidad(cantidad);
		ing.setUnidadMedida(unidadMedida);
		
		if (accion.equals("1")){
			ing.setId(saumIngredienteDao.maximoReg());
			saumIngredienteDao.insertReg(ing);			
		}else{
			ing.setId(ingredienteId);
			saumIngredienteDao.updateReg(ing);
		}
		
		return "redirect:/comedor/receta/ingredientes?RecetaId="+recetaId+"&EtapaId="+etapaId;
	}
	
	@RequestMapping("/comedor/receta/borrarIngrediente")
	public String comedorRecetaBorrarIngrediente(HttpServletRequest request, Model modelo){
		String recetaId			= request.getParameter("RecetaId")==null?"0":request.getParameter("RecetaId");
		String etapaId			= request.getParameter("EtapaId")==null?"0":request.getParameter("EtapaId");
		String ingredienteId	= request.getParameter("IngredienteId")==null?"0":request.getParameter("IngredienteId");		
		if (saumIngredienteDao.existeReg(ingredienteId)){
			saumIngredienteDao.deleteReg(ingredienteId);
		}
		
		return "redirect:/comedor/receta/ingredientes?RecetaId="+recetaId+"&EtapaId="+etapaId;
	}
	
}