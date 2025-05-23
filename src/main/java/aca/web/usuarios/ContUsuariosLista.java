package aca.web.usuarios;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.acceso.spring.AccesoOpcion;
import aca.acceso.spring.AccesoOpcionDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanDao;
import aca.emp.spring.EmpMaestro;
import aca.emp.spring.EmpMaestroDao;
import aca.menu.spring.Menu;
import aca.menu.spring.MenuDao;
import aca.menu.spring.Modulo;
import aca.menu.spring.ModuloDao;
import aca.menu.spring.ModuloOpcion;
import aca.menu.spring.ModuloOpcionDao;
import aca.pg.archivo.spring.PosFotoAlumDao;
import aca.plan.spring.MapaCurso;
import aca.vista.spring.InscritosDao;
import aca.vista.spring.Maestros;
import aca.vista.spring.MaestrosDao;
import aca.vista.spring.Usuarios;
import aca.vista.spring.UsuariosDao;

@Controller
public class ContUsuariosLista {
	
	@Autowired	
	private AccesoDao accesoDao;
	
	@Autowired
	MaestrosDao mestrosDao;
	
	@Autowired
	UsuariosDao usuariosDao;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;	
	
	@Autowired
	private AccesoOpcionDao accesoOpcionDao;
	
	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;	
	
	@Autowired
	private EmpMaestroDao empMaestroDao;
	
	@Autowired
	private AlumPlanDao alumPlanDao;
	
	@Autowired
	private PosFotoAlumDao posFotoAlumDao;
	
	@RequestMapping("/usuarios/lista/usuarios")
	public String usuariosListaUsuarios(HttpServletRequest request, Model modelo){
		String tipo 			= request.getParameter("Tipo")==null?"E":request.getParameter("Tipo");
		String codigoPersonal 	= "0";
		String admin			= "N";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
			admin				= accesoDao.mapeaRegId(codigoPersonal).getAdministrador();
		}
		
		List<Maestros> lisMaestros			= mestrosDao.getListAll(" ORDER BY APELLIDO_PATERNO,APELLIDO_MATERNO,NOMBRE");
		List<AlumPersonal> lisAlumnos		= alumPersonalDao.getListAll(" ORDER BY APELLIDO_PATERNO,APELLIDO_MATERNO,NOMBRE");
		
		HashMap<String,String> mapaOpciones =  accesoOpcionDao.mapOpciones();
		HashMap<String,Acceso> mapaAccesos 	= accesoDao.mapaTodos();
		HashMap<String,String> mapaAlumPlan = alumPlanDao.mapaPlanesActivosTodos();
		HashMap<String,String> mapaFoto = posFotoAlumDao.mapaFoto();
		
		
		modelo.addAttribute("admin", admin);
		modelo.addAttribute("lisMaestros", lisMaestros);
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("mapaAccesos", mapaAccesos);
		modelo.addAttribute("mapaOpciones", mapaOpciones);
		modelo.addAttribute("mapaAlumPlan", mapaAlumPlan);
		modelo.addAttribute("mapaFoto", mapaFoto);

		return "usuarios/lista/usuarios";
	}
	
	@Transactional
	@RequestMapping("usuarios/lista/grabarMaestros")
	public String usuariosListaGrabarMaestros(HttpServletRequest request, Model modelo){				
		String mensaje	= "-";
		int grabados		= 0;
		int errores			= 0;
		int grabadosOpcion 	= 0;	
		int erroresOpcion 	= 0;
		
		List<EmpMaestro> lisMaestros = empMaestroDao.getListAll("ORDER BY CODIGO_PERSONAL");
		
		for(EmpMaestro maestro : lisMaestros) {		
			
			
			if (accesoDao.existeReg(maestro.getCodigoPersonal())==false){
				String clave 		= aca.util.Encriptar.sha512ConBase64(maestro.getCodigoPersonal());
				String claveHexa 	= bCryptPasswordEncoder.encode(maestro.getCodigoPersonal());
				
				Acceso acceso = new Acceso();
				acceso.setCodigoPersonal(maestro.getCodigoPersonal());
				acceso.setUsuario(maestro.getCodigoPersonal());
				acceso.setClave(clave);
				//System.out.println(maestro.getCodigoPersonal()+"::"+" Usuario:"+maestro.getEmail());
				acceso.setAdministrador("N");
				acceso.setSupervisor("N");
				acceso.setCotejador("N");
				acceso.setPortalAlumno("N");
				acceso.setPortalMaestro("N");
				acceso.setModalidad("0");
				acceso.setAccesos("-");
				
				if (accesoDao.insertReg(acceso)){				
					if (accesoDao.updateClaveHexa(acceso.getCodigoPersonal(), claveHexa)) {
						grabados++;					
					}
				}else{
					errores++;
				}	
				
				
			}
			
			if(accesoOpcionDao.existeReg(maestro.getCodigoPersonal(), "1160") == false) {
				AccesoOpcion accesoOpcion = new AccesoOpcion();
				
				accesoOpcion.setCodigoPersonal(maestro.getCodigoPersonal());
				accesoOpcion.setFecha(aca.util.Fecha.getHoy());
				accesoOpcion.setOpcionId("1160");
				accesoOpcion.setUsuario("9800308");
				
				if(accesoOpcionDao.insertReg(accesoOpcion)) {
					grabadosOpcion++;
				}else {
					erroresOpcion++;
				}
			}
			
			
			
		}
		mensaje = "Total inserted accounts: "+grabados+" Errors: "+errores+"%0ATotal Permissions Given: "+grabadosOpcion+" Errors: "+erroresOpcion;
		
		return "redirect:/usuarios/lista/usuarios?Mensaje="+mensaje+"&Tipo=E";
	}
	
	@Transactional
	@RequestMapping("usuarios/lista/grabarAlumnos")
	public String usuariosListaGrabarAlumnos(HttpServletRequest request, Model modelo){
		
		String mensaje	= "";
		int grabados		= 0;
		int errores			= 0;
		int grabadosOpcion 	= 0;	
		int erroresOpcion 	= 0;
		
		List<AlumPersonal> lisAlumnos = alumPersonalDao.getListAll("");
		
		for(AlumPersonal alumno: lisAlumnos) {
		
			if (accesoDao.existeReg(alumno.getCodigoPersonal())==false){
				String clave = aca.util.Encriptar.sha512ConBase64(alumno.getCodigoPersonal());
				String claveHexa = bCryptPasswordEncoder.encode(alumno.getCodigoPersonal());
				
				Acceso acceso = new Acceso();
				acceso.setCodigoPersonal(alumno.getCodigoPersonal());
				acceso.setUsuario(alumno.getCodigoPersonal());
				acceso.setClave(clave);
				
				acceso.setAdministrador("N");
				acceso.setSupervisor("N");
				acceso.setCotejador("N");
				acceso.setPortalAlumno("N");
				acceso.setPortalMaestro("N");
				acceso.setModalidad("0");
				acceso.setAccesos("-");
				
				if (accesoDao.insertReg(acceso)){						
					if (accesoDao.updateClaveHexa(acceso.getCodigoPersonal(), claveHexa)){
						grabados++;
					}
				}else{
					errores++;				
				}
			}
			if(accesoOpcionDao.existeReg(alumno.getCodigoPersonal(), "1160") == false) {
				AccesoOpcion accesoOpcion = new AccesoOpcion();
				
				accesoOpcion.setCodigoPersonal(alumno.getCodigoPersonal());
				accesoOpcion.setFecha(aca.util.Fecha.getHoy());
				accesoOpcion.setOpcionId("1160");
				accesoOpcion.setUsuario("9800308");
				
				if(accesoOpcionDao.insertReg(accesoOpcion)) {
					grabadosOpcion++;
				}else {
					erroresOpcion++;
				}
			}
			
			
		}	
		mensaje = "Total inserted accounts: "+grabados+" Errors: "+errores+"%0ATotal Permissions Given: "+grabadosOpcion+" Errors: "+erroresOpcion;
		
		return "redirect:/usuarios/lista/usuarios?Mensaje="+mensaje+"&Tipo=A";
	}

	@RequestMapping("usuarios/lista/restaurarContrasenas")
	public String usuariosListaRestaurarContrasenasAlumnos(HttpServletRequest request, Model modelo){
		String mensaje	= "";

		List<AlumPersonal> lisAlumnos = alumPersonalDao.getListAll("");

		int grabados		= 0;
		int errores			= 0;
		int noEncontrados 	= 0;

		System.out.println("Inicio");

		for(AlumPersonal alumno: lisAlumnos){
			String clave = aca.util.Encriptar.sha512ConBase64(alumno.getCodigoPersonal());
			String claveHexa = bCryptPasswordEncoder.encode(alumno.getCodigoPersonal());

			Acceso acceso = new Acceso();

			System.out.println("Alumno "+alumno.getCodigoPersonal());

			if(accesoDao.existeReg(alumno.getCodigoPersonal())){
				acceso = accesoDao.mapeaRegId(alumno.getCodigoPersonal());

				acceso.setUsuario(alumno.getCodigoPersonal());
				acceso.setClave(clave);
				acceso.setClaveHexa(claveHexa);

				if(accesoDao.updateReg(acceso)){
					grabados++;
				}else{
					errores++;
				}
			}else{
				noEncontrados++;
			}
		}

		mensaje = "Total reset accounts: "+grabados+" Errors: "+errores+" Students without account: "+noEncontrados;

		return "redirect:/usuarios/lista/usuarios?Mensaje="+mensaje+"&Tipo=A";
	}
}