package aca.web.inscritos;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumAcademico;
import aca.alumno.spring.AlumAcademicoDao;
import aca.alumno.spring.AlumEstadoDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatCiudad;
import aca.catalogo.spring.CatCiudadDao;
import aca.catalogo.spring.CatEstado;
import aca.catalogo.spring.CatEstadoDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatModalidad;
import aca.catalogo.spring.CatModalidadDao;
import aca.catalogo.spring.CatPais;
import aca.catalogo.spring.CatPaisDao;
import aca.catalogo.spring.CatReligion;
import aca.catalogo.spring.CatReligionDao;
import aca.catalogo.spring.CatTipoAlumno;
import aca.catalogo.spring.CatTipoAlumnoDao;
import aca.financiero.spring.SaldosAlumnosDao;
import aca.kardex.spring.KrdxCursoActDao;
import aca.vista.spring.EstadisticaDao;
import aca.vista.spring.Inscritos;
import aca.vista.spring.InscritosDao;

@Controller
public class ContInscritosInscritosSe {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;	
	
	@Autowired	
	private CargaDao cargaDao;
	
	@Autowired	
	private CatModalidadDao catModalidadDao;
	
	@Autowired	
	private EstadisticaDao estadisticaDao;
	
	@Autowired
	ServletContext context;
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	
	@RequestMapping("/inscritos/inscritos_se/inscritos")
	public String inscritosInscritosSeInscritos(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContInscritosSe|inscritosInscritosSeInscritos:");
		return "inscritos/inscritos_se/inscritos";
	}
	
	@RequestMapping("/inscritos/inscritos_se/cargasActivas")
	public String inscritosInscritosSeCargasActivas(HttpServletRequest request, Model modelo){
		
		String fInscripcion					= request.getParameter("Fecha")==null?aca.util.Fecha.getHoy():request.getParameter("Fecha");
		
		List<Carga> lisCarga		= cargaDao.getListPorFecha(fInscripcion, "ORDER BY CARGA_ID");
		List<Carga> lisActivas	= cargaDao.getListCargasActivas( " ORDER BY CARGA_ID");
		HashMap<String,String> mapaPrimerDia 	= cargaDao.mapaPrimerDia();
		HashMap<String,String> mapaUltimoDia 	= cargaDao.mapaPrimerDia();
		
		modelo.addAttribute("lisCarga", lisCarga);
		modelo.addAttribute("lisActivas", lisActivas);
		modelo.addAttribute("mapaPrimerDia", mapaPrimerDia);
		modelo.addAttribute("mapaUltimoDia", mapaUltimoDia);
		
		return "inscritos/inscritos_se/cargasActivas";
	}
	
	@RequestMapping("/inscritos/inscritos_se/modalidades")
	public String inscritosInscritosSeModalidades(HttpServletRequest request, Model modelo){
		
		String cargas			= "0";
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();            
	    if (sesion != null){
	    	cargas = sesion.getAttribute("cargas")==null?"0":sesion.getAttribute("cargas").toString();
	    }		
	    
	    List<CatModalidad> lisModalidad	= catModalidadDao.getListAll( "ORDER BY MODALIDAD_ID");
	    HashMap<String,String> mapa = estadisticaDao.mapaModalidadesEnCargas(cargas);
	    
		modelo.addAttribute("lisModalidad", lisModalidad);
		modelo.addAttribute("mapa", mapa);    
				
		return "inscritos/inscritos_se/modalidades";
	}
	
}
