package aca.web.comedor;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.saum.spring.SaumMateria;

@Controller
public class ContComedorPrima {		
	
	@Autowired
	aca.saum.spring.SaumMateriaDao saumMateriaDao;
	
	@Autowired
	aca.saum.spring.SaumIngredienteDao saumIngredienteDao;
	
	@RequestMapping("/comedor/prima/materia")
	public String comedorPrimaMateria(HttpServletRequest request, Model modelo){			
		List<SaumMateria> lisMaterias 			= saumMateriaDao.listAll(" ORDER BY NOMBRE");
		HashMap<String,String> mapaMateria		= saumIngredienteDao.mapaMateriaEnRecetas();
		
		modelo.addAttribute("lisMaterias",lisMaterias);
		modelo.addAttribute("mapaMateria",mapaMateria);
		
		return "comedor/prima/materia";
	}
	
	@RequestMapping("/comedor/prima/editar")
	public String comedorPrimaEditar(HttpServletRequest request, Model modelo){
		String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		String materiaId		= request.getParameter("MateriaId")==null?"0":request.getParameter("MateriaId");
		
		SaumMateria materia = new SaumMateria(); 
		
		if (accion.equals("0")){
			materia.setId( saumMateriaDao.maximoReg() );
		}else{			
			materia = saumMateriaDao.mapeaRegId(materiaId);
		}
		
		modelo.addAttribute("materia",materia);
		
		return "comedor/prima/editar";
	}
	
	@RequestMapping("/comedor/prima/grabar")
	public String comedorPrimaGrabar(HttpServletRequest request, Model modelo){		
		String id			= request.getParameter("Id")==null?"0":request.getParameter("Id");
		String nombre		= request.getParameter("Nombre")==null?"-":request.getParameter("Nombre");
		String energia		= request.getParameter("Energia")==null?"0":request.getParameter("Energia");
		String humedad		= request.getParameter("Humedad")==null?"0":request.getParameter("Humedad");
		String fibra		= request.getParameter("Fibra")==null?"0":request.getParameter("Fibra");
		String carbohidrato	= request.getParameter("Carbohidrato")==null?"0":request.getParameter("Carbohidrato");
		String proteina		= request.getParameter("Proteina")==null?"0":request.getParameter("Proteina");
		String lipido		= request.getParameter("Lipido")==null?"0":request.getParameter("Lipido");
		String ceniza		= request.getParameter("Ceniza")==null?"0":request.getParameter("Ceniza");
		String saturado		= request.getParameter("Saturado")==null?"0":request.getParameter("Saturado");
		String mono			= request.getParameter("Mono")==null?"0":request.getParameter("Mono");
		String poli			= request.getParameter("Poli")==null?"0":request.getParameter("Poli");
		String colesterol	= request.getParameter("Colesterol")==null?"0":request.getParameter("Colesterol");
		String calcio		= request.getParameter("Calcio")==null?"0":request.getParameter("Calcio");
		String fosforo		= request.getParameter("Fosforo")==null?"0":request.getParameter("Fosforo");
		String hierro		= request.getParameter("Hierro")==null?"0":request.getParameter("Hierro");
		String magnesio		= request.getParameter("Magnesio")==null?"0":request.getParameter("Magnesio");
		String selenio		= request.getParameter("Selenio")==null?"0":request.getParameter("Selenio");
		String sodio		= request.getParameter("Sodio")==null?"0":request.getParameter("Sodio");
		String potasio		= request.getParameter("Potasio")==null?"0":request.getParameter("Potasio");
		String cinc			= request.getParameter("Cinc")==null?"0":request.getParameter("Cinc");
		String vitaminaa	= request.getParameter("Vitaminaa")==null?"0":request.getParameter("Vitaminaa");
		String ascorbico	= request.getParameter("Ascorbico")==null?"0":request.getParameter("Ascorbico");
		String tiamina		= request.getParameter("Tiamina")==null?"0":request.getParameter("Tiamina");
		String ribo			= request.getParameter("Ribo")==null?"0":request.getParameter("Ribo");
		String niacina		= request.getParameter("Niacina")==null?"0":request.getParameter("Niacina");
		String piridoxina	= request.getParameter("Piridioxina")==null?"0":request.getParameter("Piridioxina");
		String folico		= request.getParameter("Folico")==null?"0":request.getParameter("Folico");
		String cobalamina	= request.getParameter("Cobalamina")==null?"0":request.getParameter("Cobalamina");
		
		SaumMateria materia = new SaumMateria();
		materia.setId(id);
		materia.setNombre(nombre);
		materia.setVersion("0");
		materia.setEnergia(energia);
		materia.setHumedad(humedad);
		materia.setFibra(fibra);
		materia.setCarbohidrato(carbohidrato);
		materia.setProteina(proteina);
		materia.setLipido(lipido);
		materia.setCeniza(ceniza);
		materia.setSaturado(saturado);
		materia.setMono(mono);
		materia.setPoli(poli);
		materia.setColesterol(colesterol);
		materia.setCalcio(calcio);
		materia.setFosforo(fosforo);
		materia.setHierro(hierro);
		materia.setMagnesio(magnesio);
		materia.setSelenio(selenio);
		materia.setSodio(sodio);
		materia.setPotasio(potasio);
		materia.setCinc(cinc);
		materia.setVitaminaa(vitaminaa);
		materia.setAscorbico(ascorbico);
		materia.setTiamina(tiamina);
		materia.setRibo(ribo);
		materia.setNiacina(niacina);
		materia.setPiridoxina(piridoxina);
		materia.setFolico(folico);
		materia.setCobalamina(cobalamina);
		
		if (saumMateriaDao.existeReg(id)){
			saumMateriaDao.updateReg(materia);
		}else{
			materia.setId(saumMateriaDao.maximoReg());
			saumMateriaDao.insertReg(materia);
		}
		
		
		return "redirect:/comedor/prima/materia";
	}
	
	@RequestMapping("/comedor/prima/borrar")
	public String comedorPrimaBorrar(HttpServletRequest request, Model modelo){		
		String id			= request.getParameter("Id")==null?"0":request.getParameter("Id");		
		if (saumMateriaDao.existeReg(id)){
			saumMateriaDao.deleteReg(id);
		}
		
		return "redirect:/comedor/prima/materia";
	}
	
}