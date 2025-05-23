package aca.web.datosalumno;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.pg.archivo.spring.PosFotoAlum;
import aca.pg.archivo.spring.PosFotoAlumDao;

@Controller
public class ContDatosAlumnoFotosEnLinea {	
	
	@Autowired
	ServletContext context;
	
	@Autowired
	private PosFotoAlumDao posFotoAlumDao;
	
	@Autowired
	private AlumPersonalDao alumPersonalDao;
	
	
	@RequestMapping("/datos_alumno/fotosEnlinea/enLinea")
	public String datosAlumnoFotosEnLineaEnLinea(HttpServletRequest request, Model modelo){
		
		List<String> matriculas = posFotoAlumDao.tieneFoto("A");
		HashMap<String,AlumPersonal> mapaDatos = new HashMap<String,AlumPersonal>();
		HashMap<String,String> mapaRechazadas  = posFotoAlumDao.mapaRechazada("S");
		
		for(String matricula : matriculas){
			mapaDatos.put(matricula, alumPersonalDao.mapeaRegId(matricula));
		}
		
		modelo.addAttribute("matriculas", matriculas);
		modelo.addAttribute("mapaDatos", mapaDatos);
		modelo.addAttribute("mapaRechazadas", mapaRechazadas);
		
		return "datos_alumno/fotosEnlinea/enLinea";
	}	

	@RequestMapping("/datos_alumno/fotosEnlinea/decisionFoto")
	public String datosAlumnoFotosEnLineaDecisionFoto(HttpServletRequest request){
		
		String aceptada = request.getParameter("Aceptada") == null ? "0" : request.getParameter("Aceptada");

		List<String> matriculas = posFotoAlumDao.tieneFoto("A");
		
		PosFotoAlum foto = new PosFotoAlum();
		
		if(aceptada.equals("0")) {
			for(String matricula : matriculas){
				String comentario = request.getParameter("Comentario"+matricula) == null ? "-" : request.getParameter("Comentario"+matricula);
				String mat = request.getParameter(matricula) == null ? "0" : request.getParameter(matricula);
			
				if(!mat.equals("0")) {
					foto = posFotoAlumDao.mapeaRegId(mat, "A");
					
					foto.setRechazada("S");
					
					if(!comentario.equals("-")) {
						foto.setComentario(comentario);
					}
					
					posFotoAlumDao.updateReg(foto);
				}
			}
		}else {
			try{			
				foto = posFotoAlumDao.mapeaRegId(aceptada, "A");
				
				foto.setTipo("O");
				
				posFotoAlumDao.updateReg(foto);
				
			}catch( Exception ex){
				System.out.println("Error:datos_alumno/fotosEnlinea/decisionFoto|mapeaRegId|:"+ex);
			}
		}
		
		return "redirect:/datos_alumno/fotosEnlinea/enLinea";
	}	
}