package aca.web.finanzas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumFamilia;
import aca.alumno.spring.AlumFamiliaDao;
import aca.alumno.spring.AlumHermanos;
import aca.alumno.spring.AlumHermanosDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;

@Controller
public class ContFinanzasHermanos {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired	
	AlumHermanosDao alumHermanosDao;
	
	@Autowired	
	AlumFamiliaDao alumFamiliaDao;
	
	@Autowired	
	AlumPersonalDao alumPersonalDao;
	
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/finanzas/hermanos/hermanos")
	public String finanzasHermanosHermanos(HttpServletRequest request, Model modelo){
		String filtro = request.getParameter("Filtro")==null?"A":request.getParameter("Filtro");
		String mensaje = request.getParameter("Mensaje")==null?"":request.getParameter("Mensaje");
		
		List<AlumFamilia> listaFamilias 		= alumFamiliaDao.getListAll(" WHERE ESTADO IN ('"+filtro+"') ORDER BY 1");
		List<AlumPersonal> listaAlumPersonal 	= new ArrayList<AlumPersonal>();	
		
		HashMap<String, List<AlumPersonal>> mapaFamilias 	= new HashMap<String, List<AlumPersonal>>();
		HashMap<String, AlumPersonal> mapaInscritos 		= alumPersonalDao.mapaInscritos();
		
		AlumPersonal alumPersonal = new AlumPersonal();
		
		for(AlumFamilia alumFamilia : listaFamilias){
			List<AlumHermanos> listaHermanos = alumHermanosDao.getListFamilia(alumFamilia.getFamiliaId(), "");
			for(AlumHermanos hermano : listaHermanos) {
				if(alumPersonalDao.existeAlumno(hermano.getCodigoPersonal())){
					alumPersonal = alumPersonalDao.mapeaRegId(hermano.getCodigoPersonal());
					listaAlumPersonal.add(alumPersonal);
				}
			}
			mapaFamilias.put(alumFamilia.getFamiliaId(), listaAlumPersonal);
			listaAlumPersonal = new ArrayList<AlumPersonal>();	
		}
		
		modelo.addAttribute("listaFamilias", listaFamilias);	
		modelo.addAttribute("mapaFamilias", mapaFamilias);	
		modelo.addAttribute("mapaInscritos", mapaInscritos);	
		modelo.addAttribute("mensaje", mensaje);	
		
		return "finanzas/hermanos/hermanos";
	}
	
	@RequestMapping("/finanzas/hermanos/hermanosAccion")
	public String finanzasHermanosHermanosAccion(HttpServletRequest request){		
		enviarConEnoc(request,"Error-aca.ControllerFinanzas|finanzasHermanosHermanosAccion");
		return "finanzas/hermanos/hermanosAccion";
	}
	
	@Transactional
	@RequestMapping("/finanzas/hermanos/cancelarAlumno")
	public String finanzasHermanoscancelarAlumno(HttpServletRequest request){
		
		String mensaje 	= "-"; 
		int grabados 	= 0;
		int errores 	= 0;
		
		List<AlumHermanos> lisHermanos = alumHermanosDao.getListAll("WHERE ESTADO IN('F') AND CODIGO_PERSONAL NOT IN (SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS) ");		
		for(AlumHermanos hermano: lisHermanos){			
			if(alumHermanosDao.updateEstado(hermano.getCodigoPersonal(),"A")){
				grabados++;				
			}else{
				errores++;
			}
		}
		
		mensaje = "Alumnos cancelados: "+grabados+", alumnos que no se pudieron cancelar:"+errores;
		
		return "redirect:/finanzas/hermanos/hermanos?Mensaje="+mensaje;
	}
	
	@RequestMapping("/finanzas/hermanos/cancelarFamilia")
	public String finanzasHermanoscancelarFamilia(HttpServletRequest request){
				
		int grabados	= 0;
		int errores		= 0;
		String mensaje 	= "-";
		
		List<AlumFamilia> lisFamilias 			= alumFamiliaDao.getListAll(" WHERE ESTADO IN('F') ");
		HashMap<String,String> mapaMiembros 	= alumFamiliaDao.mapaMiembros();
		
		for(AlumFamilia fam: lisFamilias){
			
			int numMiembros = 0;
			if (mapaMiembros.containsKey(fam.getFamiliaId())) {
				numMiembros = Integer.parseInt(mapaMiembros.get(fam.getFamiliaId()));
			}
			if(numMiembros <= 1){				
				if (alumFamiliaDao.updateEstado("A",fam.getFamiliaId())){
					grabados++;
				}else{
					errores++;
				}
			}			
		}
		mensaje = "Familias canceladas: "+grabados+", familias que no se pudieron cancelar:"+errores;
		
		return "redirect:/finanzas/hermanos/hermanos?Mensaje="+mensaje;
	}
	
	@RequestMapping("/finanzas/hermanos/reporte")
	public String finanzasHermanosReporte(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContFinanzasHermanos|finanzasHermanosReporte");
		return "finanzas/hermanos/reporte";
	}
	
}