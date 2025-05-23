package aca.web.cargagrupo;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.carga.spring.Carga;
import aca.carga.spring.CargaBloque;
import aca.carga.spring.CargaBloqueDao;
import aca.carga.spring.CargaDao;

@Controller
public class ContCargaGrupoListBloque {
	
	@Autowired
	CargaDao cargaDao;
	
	@Autowired
	CargaBloqueDao cargaBloqueDao;
	
	
	@RequestMapping("/carga_grupo/lista_bloque/bloques")
	public String cargaGrupoListaBloqueBloques(HttpServletRequest request, Model modelo){
		
		List<CargaBloque> lisBloques 		= cargaBloqueDao.getListAll(" ORDER BY CARGA_ID, BLOQUE_ID");
		HashMap<String, Carga> mapaCargas 	= cargaDao.mapaCargas();
				
		modelo.addAttribute("lisBloques", lisBloques);
		modelo.addAttribute("mapaCargas", mapaCargas);
		
		return "carga_grupo/lista_bloque/bloques";
	}
}