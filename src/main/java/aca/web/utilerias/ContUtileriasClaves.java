package aca.web.utilerias;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.emp.spring.EmpMaestro;
import aca.emp.spring.EmpMaestroDao;
import aca.vista.spring.Maestros;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContUtileriasClaves {	
	
	@Autowired
	private AccesoDao accesoDao;
	
	@Autowired
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;	
	
	@Autowired
	EmpMaestroDao empMaestroDao;
	
	
	@RequestMapping("utilerias/claves/claves")
	public String utileriasClavesClaves(HttpServletRequest request){
		
		return "utilerias/claves/claves";
	}
	
	
	@Transactional
	@RequestMapping("utilerias/claves/grabarMaestros")
	public String usuariosPrivilegiosGrabarMaestros(HttpServletRequest request, Model modelo){				
		String mensaje	= "-";
		int grabados	= 0;
		int errores		= 0;
		
		List<EmpMaestro> lisMaestros = empMaestroDao.getListAll("ORDER BY CODIGO_PERSONAL");
		
		for(EmpMaestro maestro : lisMaestros) {		
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
			if (accesoDao.existeReg(maestro.getCodigoPersonal())==false){
				
				if (accesoDao.insertReg(acceso)){				
					if (accesoDao.updateClaveHexa(acceso.getCodigoPersonal(), claveHexa)) {
						grabados++;						
					}
				}else{
					errores++;
				}					
			}			
		}
		mensaje = "Cuentas de maestros Grabados:"+grabados+" Errores:"+errores;
		
		return "redirect:/utilerias/claves/claves?Mensaje="+mensaje;
	}
	
	@Transactional
	@RequestMapping("utilerias/claves/grabarAlumnos")
	public String usuariosPrivilegiosGrabarAlumnos(HttpServletRequest request, Model modelo){
				
		String mensaje	= "";
		int grabados	= 0;
		int errores		= 0;
		
		List<AlumPersonal> lisAlumnos = alumPersonalDao.getListAll("");
		
		for(AlumPersonal alumno: lisAlumnos) {
		
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
			if (accesoDao.existeReg(alumno.getCodigoPersonal())==false){
				if (accesoDao.insertReg(acceso)){						
					if (accesoDao.updateClaveHexa(acceso.getCodigoPersonal(), claveHexa)) {
						grabados++;
					}
				}else{
					errores++;				
				}
			}			
		}	
		mensaje = "Cuentas de alumnos grabadas:"+grabados+" Errores:"+errores;
		
		return "redirect:/utilerias/claves/claves?Mensaje="+mensaje;
	}	
}