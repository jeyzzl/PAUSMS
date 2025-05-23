package aca.web.titulacion;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import aca.tit.spring.TitCadena;
import aca.tit.spring.TitCadenaDao;
import aca.tit.spring.TitInstitucionUsuario;
import aca.tit.spring.TitInstitucionUsuarioDao;
import aca.tit.spring.TitTramite;

@Controller
public class ContTitulacionFirmar {	
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired	
	private aca.tit.spring.TitTramiteDao titTramiteDao;	
	
	@Autowired	
	private aca.tit.spring.TitTramiteDocDao titTramiteDocDao;
	
	@Autowired	
	private aca.tit.spring.TitAlumnoDao titAlumnoDao;
	
	@Autowired	
	private aca.tit.spring.TitFirmaDao titFirmaDao;	
	
	@Autowired	
	private aca.alumno.spring.AlumPersonalDao alumPersonalDao;
	
	@Autowired	
	private TitCadenaDao titCadenaDao;
	
	@Autowired	
	private TitInstitucionUsuarioDao titInstitucionUsuarioDao;
	
	
	@RequestMapping("/titulacion/firmar/tramite")
	public String titulacionFirmarTramite(HttpServletRequest request, Model modelo){	
		
		String institucion 		= request.getParameter("Institucion")==null?"UM":request.getParameter("Institucion");	
		String codigoPersonal	= "0";
		
		HttpSession sesion = ((HttpServletRequest) request).getSession();
		if (sesion != null) {
			codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");
		}	
		
		List<aca.tit.spring.TitTramite> lisTramites	 	= titTramiteDao.lisInstitucion(institucion, "F", "ORDER BY FECHA");
		List<aca.tit.spring.TitFirma> lisFirmas	 		= titFirmaDao.lisInstitucion(institucion, " ORDER BY PRIMERAPELLIDO");
		List<TitInstitucionUsuario> lisInstituciones	= titInstitucionUsuarioDao.lisInstitucionesPorUsuario(codigoPersonal, " ORDER BY INSTITUCION DESC");
		HashMap<String,String> mapaTotTitulos			= titTramiteDocDao.mapaTotTitulos();
		
		modelo.addAttribute("lisInstituciones", lisInstituciones);
		modelo.addAttribute("lisTramites", lisTramites);
		modelo.addAttribute("lisFirmas", lisFirmas);
		modelo.addAttribute("mapaTotTitulos", mapaTotTitulos);
		
		return "titulacion/firmar/tramite";
	}
	
	@RequestMapping("/titulacion/firmar/alumnos")
	public String titulacionFirmarAlumnos(HttpServletRequest request, Model modelo){	
		String tramite	= request.getParameter("Tramite")==null?"1":request.getParameter("Tramite");	
		String estado 	= request.getParameter("Estado")==null?"A":request.getParameter("Estado");
		String institucion	= request.getParameter("Institucion")==null?"UM":request.getParameter("Institucion");	
		
		HashMap<String,String> mapAlumnos = new HashMap<String,String>();
		
		List<aca.tit.spring.TitAlumno> lisAlumnos	 	= titAlumnoDao.lisTramite(tramite, " ORDER BY PLAN_ID, FECHA");
		List<aca.tit.spring.TitFirma> lisFirmas	 		= titFirmaDao.lisInstitucion(institucion, " ORDER BY PRIMERAPELLIDO");

		for(aca.tit.spring.TitAlumno alumno: lisAlumnos) {
			mapAlumnos.put(alumno.getCodigoPersonal(), alumPersonalDao.getNombreAlumno(alumno.getCodigoPersonal(), "APELLIDO"));
		}
		
		TitTramite titTramite = new TitTramite();
		if (titTramiteDao.existeReg(tramite)) {
			titTramite = titTramiteDao.mapeaRegId(tramite);
		}
		modelo.addAttribute("mapAlumnos", mapAlumnos);
		modelo.addAttribute("titTramite", titTramite);
		modelo.addAttribute("lisFirmas", lisFirmas);
		modelo.addAttribute("lisAlumnos", lisAlumnos);		
		
		return "titulacion/firmar/alumnos";
	}
	
	@RequestMapping("/titulacion/firmar/subirFirma")
	public String titulacionFirmarSubirFirma(HttpServletRequest request, Model modelo){		
		
		return "titulacion/firmar/subirFirma";
	}
	
	@PostMapping("/titulacion/firmar/grabarSello")
	public String titulacionFirmarGrabarSello(@RequestParam("llave") MultipartFile llave, HttpServletRequest request){
		
		String codigoPersonal		= request.getParameter("CodigoPersonal")==null?"0":request.getParameter("CodigoPersonal");
		String tramite				= request.getParameter("Tramite")==null?"0":request.getParameter("Tramite");
		String clave				= request.getParameter("Clave")==null?"0":request.getParameter("Clave");
		String xmlTitulo			= "";
		String cadenaOriginal		= "";
		String mensaje				= "0";
		
		List<aca.tit.spring.TitAlumno> lisTitulos	 	= titAlumnoDao.lisTramite(tramite, " ORDER BY PLAN_ID, FECHA");
		
		try{
			
			for (aca.tit.spring.TitAlumno titulo : lisTitulos) {
				
				// Obtener el archivo key
				InputStream archivoKey = new ByteArrayInputStream(llave.getBytes());
				
				xmlTitulo 		= titulo.getXml();
				// Obtener cadena de tit_cadena
				TitCadena cadena	 	= titCadenaDao.mapeaRegId(titulo.getFolio(), codigoPersonal);
				cadenaOriginal 			= cadena.getCadena();					
								
				aca.tit.spring.Sello sello = new aca.tit.spring.Sello();
				
				if (sello.Sellado(archivoKey, clave, cadenaOriginal)) {
					
					if (codigoPersonal.equals("9800146")) { xmlTitulo = xmlTitulo.replace("SELLORAQUEL", sello.getSignature()); }
					if (codigoPersonal.equals("9800052")) {	xmlTitulo = xmlTitulo.replace("SELLOISMAEL", sello.getSignature()); }					
					if (codigoPersonal.equals("9800812")) {	xmlTitulo = xmlTitulo.replace("SELLOANA", sello.getSignature()); }
					if (codigoPersonal.equals("9801097")) {	xmlTitulo = xmlTitulo.replace("SELLOJOSE", sello.getSignature()); }
					if (codigoPersonal.equals("9801085")) {	xmlTitulo = xmlTitulo.replace("SELLOJAIME", sello.getSignature()); }
					if (codigoPersonal.equals("9800308")) {	xmlTitulo = xmlTitulo.replace("SELLOERY", sello.getSignature()); }
					
					//System.out.println("Datos:"+codigoPersonal+":"+tramite+":"+clave+":"+titulo.getCodigoPersonal()+":"+sello.getSignature());
					
					titulo.setXml(xmlTitulo);
					if (titAlumnoDao.updateReg(titulo)) {
						mensaje = "1";
					}
				}else {
					mensaje = "3";
				}
				
			}			
		}catch(Exception ex){
			System.out.println("Error al grabar el sello: "+ex);
			mensaje = "2";
		}	
		
		return "redirect:/titulacion/firmar/tramite";
	}
}