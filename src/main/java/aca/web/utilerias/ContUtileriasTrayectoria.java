package aca.web.utilerias;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;
import aca.inscrito.spring.InsAlumno;
import aca.inscrito.spring.InsAlumnoDao;

@Controller
public class ContUtileriasTrayectoria {
	
	@Autowired	
	private CargaDao cargaDao;
	
	@Autowired	
	private InsAlumnoDao insAlumnoDao;
	
	@RequestMapping("utilerias/trayectoria/listado")
	public String utileriasTrayectoriaListado(HttpServletRequest request, Model modelo){
		
		List<Carga> lisCargas 				= cargaDao.lisTodas( " ORDER BY ORDEN");
		HashMap<String,String> mapaTotales 	= insAlumnoDao.mapaPorCarga(); 
		
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("mapaTotales", mapaTotales);
		
		return "utilerias/trayectoria/listado";
	}
	
	@RequestMapping("utilerias/trayectoria/grabar")
	public String utileriasTrayectoriaGrabar(HttpServletRequest request, Model modelo){
		
		List<Carga> lisCargas = cargaDao.lisTodas( " ORDER BY ORDEN");
		for (Carga carga : lisCargas){			
			List<InsAlumno> lista = insAlumnoDao.lisBuscar(carga.getCargaId());
			System.out.println("Carga:"+carga.getCargaId()+":"+lista.size());
			for (InsAlumno alumno : lista) {
				if (insAlumnoDao.existeReg(alumno.getCodigoPersonal(), carga.getCargaId(), alumno.getPlanId(), alumno.getCiclo())){
					insAlumnoDao.updateReg(alumno);
				}else {
					insAlumnoDao.insertReg(alumno);
				}
			}			
		}
		return "redirect:/utilerias/trayectoria/listado";
	}
	
}