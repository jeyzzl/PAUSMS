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
public class ContInscritosInscritosVentas {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("dsArchivo")
	private DataSource archivo;
	
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
	
	public void enviarConArchivo(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conArchivo", archivo.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}

	@RequestMapping("/inscritos/ventas/cargas")
	public String inscritosVentasCargas(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContinscritosVentasCargas|inscritosVentasCargas:");
		//enviarConArchivo(request,"Error-aca.ControllerDatosAlumno|credencialEmpDatoDep:");		
		return "inscritos/ventas/cargas";
	}
	
	@RequestMapping("/inscritos/ventas/modalidades")
	public String inscritosVentasModalidades(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContinscritosVentasModalidades|inscritosVentasModalidades:");
		//enviarConArchivo(request,"Error-aca.ControllerDatosAlumno|credencialEmpDatoDep:");		
		return "inscritos/ventas/modalidades";
	}
	
	@RequestMapping("/inscritos/ventas/ventas")
	public String inscritosVentasVentas(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContinscritosVentasVentas|inscritosVentasVentas:");
		//enviarConArchivo(request,"Error-aca.ControllerDatosAlumno|credencialEmpDatoDep:");		
		return "inscritos/ventas/ventas";
	}
}
