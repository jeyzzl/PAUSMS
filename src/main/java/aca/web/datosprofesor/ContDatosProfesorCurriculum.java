package aca.web.datosprofesor;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.acceso.spring.AccesoDao;
import aca.catalogo.spring.CatNivel;
import aca.catalogo.spring.CatNivelDao;
import aca.catalogo.spring.CatPaisDao;
import aca.emp.spring.EmpCurriculum;
import aca.emp.spring.EmpCurriculumDao;
import aca.pg.archivo.spring.PosFotoAlumDao;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContDatosProfesorCurriculum {	
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;	
	
	@Autowired
	private AccesoDao accesoDao;
	
	@Autowired
	private PosFotoAlumDao posFotoAlumDao;
	
	@Autowired
	private EmpCurriculumDao empCurriculumDao;	
	
	@Autowired
	private MaestrosDao maestrosDao;
	
	@Autowired
	private CatPaisDao catPaisDao;
	
	@Autowired
	CatNivelDao catNivelDao; 
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/datos_profesor/curriculum/vitaePdf")
	public String datosProfesorCurriculumVitaePDF(HttpServletRequest request, Model modelo){
		
		String codigoPersonal 			= request.getParameter("codigoPersonal")==null?"0":request.getParameter("codigoPersonal");	
		EmpCurriculum empCurriculum 	= new EmpCurriculum();		
		String empleadoNombre			= "-";
		String nacionalidad				= "-";
		if (empCurriculumDao.existeReg(codigoPersonal)) {
			empCurriculum 	= empCurriculumDao.mapeaRegId(codigoPersonal);
			nacionalidad 	= catPaisDao.getNacionalidad(empCurriculum.getNacionalidad()); 
		}		
		
		if (maestrosDao.existeReg(codigoPersonal)) {
			empleadoNombre = maestrosDao.getNombreMaestro(codigoPersonal, "NOMBRE");
		}
		
		byte[] fotoByte = posFotoAlumDao.getFotoByte(codigoPersonal, "O");
		
		modelo.addAttribute("empCurriculum", empCurriculum);
		modelo.addAttribute("empleadoNombre", empleadoNombre);
		modelo.addAttribute("nacionalidad", nacionalidad);
		modelo.addAttribute("fotoByte", fotoByte);
		
		return "datos_profesor/curriculum/vitaePdf";
	}	
	
	@RequestMapping("/datos_profesor/curriculum/listado")
	public String datosProfesorCurriculumListado(HttpServletRequest request, Model modelo){
		
		String codigoPersonal	= "0";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal 		=(String)sesion.getAttribute("codigoPersonal"); 
		}
		String accesos			= accesoDao.getAccesos(codigoPersonal);
		boolean esAdmin 		= accesoDao.esAdministrador(codigoPersonal);
		
		List<EmpCurriculum> listCurriculum 		= empCurriculumDao.getListAll(" ORDER BY ENOC.EMP_NOMBRE(ID_EMPLEADO)");
		HashMap<String,String> mapaMaestros 	= maestrosDao.mapMaestroNombre("NOMBRE");
		HashMap<String,CatNivel> mapaNiveles 	= catNivelDao.getMapAll("");
		
		modelo.addAttribute("accesos", accesos);
		modelo.addAttribute("esAdmin", esAdmin);
		
		modelo.addAttribute("listCurriculum", listCurriculum);
		modelo.addAttribute("mapaMaestros", mapaMaestros);
		modelo.addAttribute("mapaNiveles", mapaNiveles);
		
		return "datos_profesor/curriculum/listado";
	}	
	
	@RequestMapping("/datos_profesor/curriculum/vitae")
	public String datosProfesorCurriculumVitae(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContDatosProfesorCurriculum|Vitae:");
		return "datos_profesor/curriculum/vitae";
	}	
	
}