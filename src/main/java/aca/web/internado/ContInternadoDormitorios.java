package aca.web.internado;


import java.util.ArrayList;
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
import aca.carga.spring.CargaGrupoHoraDao;
import aca.catalogo.spring.CatEdificioDao;
import aca.internado.spring.IntAccesoDao;
import aca.internado.spring.IntAlumnoDao;
import aca.internado.spring.IntCuartoDao;
import aca.internado.spring.IntDormitorio;
import aca.internado.spring.IntDormitorioDao;
import aca.vista.spring.InscritosDao;
import aca.vista.spring.Maestros;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContInternadoDormitorios {	
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	

	@Autowired
	CatEdificioDao catEdificioDao;
	
	@Autowired
	MaestrosDao maestrosDao;
	
	@Autowired
	CargaGrupoHoraDao cargaGrupoHoraDao;
	
	@Autowired
	IntDormitorioDao intDormitorioDao;
	
	@Autowired
	IntCuartoDao intCuartoDao;
	
	@Autowired
	IntAlumnoDao intAlumnoDao;

	@Autowired
	IntAccesoDao intAccesoDao;
	
	@Autowired
	InscritosDao inscritosDao;
	
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
			//System.out.println("Abrir conEnoc"+url);
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/internado/dormitorios/buscar")
	public String internadoDormitoriosBuscar(HttpServletRequest request, Model modelo){
		
		String codigoPersonal 			= request.getParameter("CodigoPersonal")==null?"0":request.getParameter("CodigoPersonal");
		List<Maestros> lisMaestros	 	= new ArrayList<Maestros>();
		Maestros maestro				= new Maestros();
		HttpSession sesion 			= ((HttpServletRequest) request).getSession();		
		String accion				= request.getParameter("Accion")==null?"0":request.getParameter("Accion");	
		int numAccion 				= Integer.parseInt(accion);	
		boolean existe 				= false;
		
		String resultado			= "Select Query Option";		
		String nombre 				= ""; 
		String paterno				= "";
		String materno				= "";	
		
		switch (numAccion){
			case 1:{
				nombre			= request.getParameter("Nombre")==null?"":request.getParameter("Nombre");	
				paterno			= request.getParameter("Paterno")==null?"":request.getParameter("Paterno");
				materno			= request.getParameter("Materno")==null?"":request.getParameter("Materno");				
				lisMaestros 	= maestrosDao.getListMaestros(nombre, paterno, materno, "ORDER BY 2,3,4");
				if (lisMaestros.size() > 0)
					resultado	= "Click over the student";
				else
					resultado	= "Not found";
				break;
			}	
			case 2:{
				if (maestrosDao.existeReg(codigoPersonal)){
					existe 		= true;
					maestro 	= maestrosDao.mapeaRegId(request.getParameter("CodigoPersonal"));
					resultado 	= "Clock over the student";
				}else{
					resultado 	= "Not found: "+maestro.getCodigoPersonal();
				}
				break;			
			}
			case 3:{
				if (maestrosDao.existeReg(codigoPersonal)){
					if (sesion != null) {
						sesion.setAttribute("codigoEmpleado", codigoPersonal);
					}					
				}else{
					if (sesion != null) {
						sesion.setAttribute("codigoAlumno", codigoPersonal);
					}
				}			
				resultado = "Uploaded to session: "+codigoPersonal;
				// if(!origen.equals("X"))response.sendRedirect("../../"+origen+"&carpeta="+sCarpetab);
			}
		}
		
		modelo.addAttribute("resultado", resultado);
		modelo.addAttribute("maestro", maestro);
		modelo.addAttribute("existe", existe);
		modelo.addAttribute("lisMaestros", lisMaestros);
		
		return "internado/dormitorios/buscar";
	}
	
	@RequestMapping("/internado/dormitorios/dormitorios")
	public String internadoDormitoriosDormitorios(HttpServletRequest request, Model modelo){
		
		String fechaIni			= request.getParameter("FechaIni")==null?"0":request.getParameter("FechaIni");
		String fechaFin			= request.getParameter("FechaFin")==null?"0":request.getParameter("FechaFin");
		boolean admin 			= false;
		String codigoPersonal 	= "0";
		
		HttpSession sesion 	= ((HttpServletRequest) request).getSession();
		if (sesion != null) {
			if (!fechaIni.equals("0")) { sesion.setAttribute("fechaIni", fechaIni);	}else {	fechaIni = (String) sesion.getAttribute("fechaIni");}
			if (!fechaFin.equals("0")) { sesion.setAttribute("fechaFin", fechaFin);	}else {	fechaFin = (String) sesion.getAttribute("fechaFin");}
			admin = (boolean) sesion.getAttribute("admin");
			codigoPersonal = (String) sesion.getAttribute("codigoPersonal");
		}
		
		String accion 			= request.getParameter("accion");
		String id 				= request.getParameter("id");
		String did 				= request.getParameter("did");
		String nombre 			= request.getParameter("nombre");
		String nomina			= request.getParameter("nomina");
		String sexo	 			= request.getParameter("sexo");	
		String codigoId			= request.getParameter("codigoId");
		String dormiId			= request.getParameter("dormiId");
		
		String redireccionar	= "X"; 
		
		
		IntDormitorio dormi 		= new IntDormitorio();		
		if (id == null) id = "";
		if (nombre== null) nombre = "";
		if (nomina== null) nomina = "";
		if (accion == null) accion = "";
		if (accion.equals("guardar")){
			dormi.setDormitorioId(id);
			dormi.setNombre(nombre);
			dormi.setPreceptor(nomina);
			dormi.setSexo(sexo);
			intDormitorioDao.insertReg( dormi);
		}else if (accion.equals("cambios")){
			intDormitorioDao.mapeaRegId(id);
			dormi.setDormitorioId(id);
			dormi.setNombre(nombre);
			dormi.setPreceptor(nomina);
			dormi.setSexo(sexo);
			intDormitorioDao.updateReg( dormi);
		}else if (accion.equals("eliminar")){
			if (intDormitorioDao.existeReg(did)) {
				intDormitorioDao.deleteReg(did);
			}
		}else if(accion.equals("session")){
			if (sesion != null) {
				sesion.setAttribute("codigoPreceptor",codigoId);
				sesion.setAttribute("dormitorioId",dormiId);
			}			
			redireccionar = "../../portales/preceptor/personal";
		}	
		List<IntDormitorio> lisDormitorios = new ArrayList<IntDormitorio>();

		boolean esPreceptor = intDormitorioDao.existePreceptor(codigoPersonal);
		boolean esAsistente = intAccesoDao.getRolUsuairo(codigoPersonal).equals("A");
		
		if(admin) {
			lisDormitorios		 = intDormitorioDao.getListAll(" ORDER BY DORMITORIO_ID");
		}else if(esPreceptor){
			lisDormitorios		 = intDormitorioDao.getListPorPreceptor(codigoPersonal, " ORDER BY DORMITORIO_ID");
		}else if(esAsistente){
			lisDormitorios 		= intDormitorioDao.getListPorAsistente(codigoPersonal, " ORDER BY DORMITORIO_ID");
		}
		
		List<Maestros> lisMaestros				 = maestrosDao.getListAll(" ORDER BY APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE");
		
		// Map de alumnos por dormitorio
		HashMap<String,String> mapaDormi 		= inscritosDao.mapaInscDormiEntreFechas("I", fechaIni, fechaFin, "1,4");
		
		HashMap<String,Integer> mapCupoDormitorios 			= intCuartoDao.mapCupoDormitorios("'A'");
		HashMap<String,Integer> mapCuartosDormitorios 		= intCuartoDao.mapCuartosDormitorios();
		HashMap<String,Integer> mapRegistradosEnDormitorios = intAlumnoDao.mapRegistradosEnDormitorios("'A'");
		
		HashMap<String,Integer> mapaAsignados 	= inscritosDao.mapaAsignados(fechaIni, fechaFin);
		HashMap<String,Integer> mapaOtros	  	= intAlumnoDao.mapaOtros(fechaIni, fechaFin);
		HashMap<String, Maestros> mapaMaestros	= maestrosDao.mapaMaestros();

		modelo.addAttribute("fechaIni", fechaIni);
		modelo.addAttribute("fechaFin", fechaFin);
		modelo.addAttribute("redireccionar", redireccionar);
		modelo.addAttribute("accion", accion);
		modelo.addAttribute("did", did);
		
		modelo.addAttribute("lisDormitorios", lisDormitorios);
		modelo.addAttribute("lisMaestros", lisMaestros);
		modelo.addAttribute("mapaDormi", mapaDormi);
		modelo.addAttribute("mapCupoDormitorios", mapCupoDormitorios);
		modelo.addAttribute("mapRegistradosEnDormitorios", mapRegistradosEnDormitorios);
		modelo.addAttribute("mapaAsignados", mapaAsignados);
		modelo.addAttribute("mapaOtros", mapaOtros);
		modelo.addAttribute("mapaMaestros", mapaMaestros);
		modelo.addAttribute("mapCuartosDormitorios", mapCuartosDormitorios);
		
		
		return "internado/dormitorios/dormitorios";
	}
}