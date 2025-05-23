package aca.web.admision;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.musica.spring.MusiAlumno;
import aca.musica.spring.MusiPadres;
import aca.parametros.spring.Parametros;
import aca.parametros.spring.ParametrosDao;
import aca.catalogo.spring.CatReligion;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumUbicacion;

@Controller
public class ContAdmisionCimum {	
	
	@Autowired
	aca.musica.spring.MusiAlumnoDao musiAlumnoDao;
	
	@Autowired
	aca.musica.spring.MusiPadresDao musiPadresDao;
	
	@Autowired
	aca.musica.spring.MusiInstitucionDao musiInstitucionDao;
	
	@Autowired
	aca.catalogo.spring.CatReligionDao catReligionDao;
	
	@Autowired
	aca.catalogo.spring.CatPaisDao catPaisDao;
	
	@Autowired
	aca.catalogo.spring.CatEstadoDao catEstadoDao;
	
	@Autowired
	aca.catalogo.spring.CatCiudadDao catCiudadDao;
	
	@Autowired
	aca.alumno.spring.AlumPersonalDao alumPersonalDao;
	
	@Autowired
	aca.alumno.spring.AlumUbicacionDao alumUbicacionDao;	
	
	@Autowired
	ParametrosDao parametrosDao;
	
	@RequestMapping("/admision/cimum/alumnos")
	public String admisionCimumAlumnos(HttpServletRequest request, Model modelo){
		String estado 		= request.getParameter("Estado")==null?"S":request.getParameter("Estado");
		
		List<MusiAlumno> lisAlumnos	= musiAlumnoDao.lisAdmision(estado, "ORDER BY APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE");	
		HashMap<String, MusiPadres> mapaPadres 		= musiPadresDao.mapaPadres();
		HashMap<String, String> mapaPersonal 	= alumPersonalDao.mapaConservatorio("'S','A'");
		HashMap<String, String> mapaUbicacion 	= alumPersonalDao.mapaUbicacion("'S','A'");
		HashMap<String, String> mapaAcademico 	= alumPersonalDao.mapaAcademico("'S','A'");
		HashMap<String, String> mapaPlan 		= alumPersonalDao.mapaPlan("'S','A'","'CONS2018'");
		
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("mapaPersonal", mapaPersonal);
		modelo.addAttribute("mapaPadres", mapaPadres);
		modelo.addAttribute("mapaUbicacion", mapaUbicacion);
		modelo.addAttribute("mapaAcademico", mapaAcademico);
		modelo.addAttribute("mapaPlan", mapaPlan);
		
		return "admision/cimum/alumnos";
	}	
	
	@RequestMapping("/admision/cimum/editarCodigo")
	public String admisionCimumEditarCodigo(HttpServletRequest request, Model modelo){	
		String codigo 			= request.getParameter("Codigo")==null?"0":request.getParameter("Codigo");
		
		MusiAlumno musiAlumno 	= new MusiAlumno();
		musiAlumno = musiAlumnoDao.mapeaRegId(codigo);
		
		modelo.addAttribute("musiAlumno", musiAlumno);	
		
		return "admision/cimum/editarCodigo";
	}
	
	@RequestMapping("/admision/cimum/grabarCodigo")
	public String admisionCimumGrabarCodigo(HttpServletRequest request, Model modelo){	
		String codigo 			= request.getParameter("Codigo")==null?"0":request.getParameter("Codigo");
		String estado 			= request.getParameter("Estado")==null?"S":request.getParameter("Estado");
		String codigoCimum		= request.getParameter("CodigoCimum")==null?"0":request.getParameter("CodigoCimum");
		String estadoCimum 		= request.getParameter("EstadoCimum")==null?"P":request.getParameter("EstadoCimum");
		String mensaje 			= "-";
		
		MusiAlumno musiAlumno 	= new MusiAlumno();
		musiAlumno = musiAlumnoDao.mapeaRegId(codigo);
		musiAlumno.setCodigoUM(codigoCimum);
		musiAlumno.setEstado(estadoCimum);
		
		if (musiAlumnoDao.updateReg(musiAlumno)){
			mensaje = "¡Modificado!";
		}	
		
		return "redirect:/admision/cimum/editarCodigo?Codigo="+codigo+"&Estado="+estado+"&mensaje="+mensaje;
	}
	
	@RequestMapping("/admision/cimum/traspasar")
	public String admisionCimumTraspasar(HttpServletRequest request, Model modelo){	
		String codigo 			= request.getParameter("Codigo")==null?"0":request.getParameter("Codigo");
		boolean tienePadres		= false;
		
		MusiAlumno musiAlumno 	= new MusiAlumno();
		MusiPadres musiPadres 	= new MusiPadres();
		musiAlumno = musiAlumnoDao.mapeaRegId(codigo);
		String institucionNombre	= musiInstitucionDao.getInstitucionNombre(musiAlumno.getInstitucionId()); 
		String paisNombre			= catPaisDao.getNombrePais(musiAlumno.getPaisId());
		String nacionalidad			= catPaisDao.getNacionalidad(musiAlumno.getPaisId());
		String estadoNombre			= catEstadoDao.getNombreEstado(musiAlumno.getPaisId(), musiAlumno.getEstadoId());
		String ciudadNombre			= catCiudadDao.getNombreCiudad(musiAlumno.getPaisId(), musiAlumno.getEstadoId(), musiAlumno.getCiudadId());
		
		if (musiPadresDao.existeReg(codigo)) {
			tienePadres = true;			
			musiPadres = musiPadresDao.mapeaRegId(codigo);
		}
		
		HashMap<String, CatReligion> mapaReligiones = catReligionDao.getMapAll("");
		
			
		modelo.addAttribute("musiAlumno", musiAlumno);	
		modelo.addAttribute("musiPadres", musiPadres);
		modelo.addAttribute("tienePadres", tienePadres);
		modelo.addAttribute("mapaReligiones", mapaReligiones);
		modelo.addAttribute("institucionNombre", institucionNombre);
		modelo.addAttribute("paisNombre", paisNombre);
		modelo.addAttribute("nacionalidad", nacionalidad);
		modelo.addAttribute("estadoNombre", estadoNombre);
		modelo.addAttribute("ciudadNombre", ciudadNombre);	
		
		return "admision/cimum/traspasar";
	}
	
	@RequestMapping("/admision/cimum/grabarAlumno")
	@Transactional
	public String admisionCimumGrabarAlumno(HttpServletRequest request, Model modelo){
		
		MusiAlumno musiAlumno 		= new MusiAlumno();
		MusiPadres musiPadres		= new MusiPadres();
		AlumPersonal alumPersonal	= new AlumPersonal();
		AlumUbicacion alumUbicacion	= new AlumUbicacion();
		
		String codigoId 			= request.getParameter("Codigo")==null?"0":request.getParameter("Codigo");
		String estado 				= request.getParameter("Estado")==null?"0":request.getParameter("Estado");
		String mensaje 				= "-";
		String codigoPersonal 		= "0";
		HttpSession sesion			= ((HttpServletRequest)request).getSession();
        if (sesion!=null){ 
        	codigoPersonal 			= (String) sesion.getAttribute("codigoPersonal");
        }

		Parametros parametros = parametrosDao.mapeaRegId("1");

		String tipo = "PAU";
		if(parametros.getInstitucion().equals("Sonoma")){
			tipo = "SONOMA";
		}
		if(parametros.getInstitucion().equals("Fulton")){
			tipo = "FAC";
		}
		
		if (musiAlumnoDao.existeReg(codigoId)) {
			musiAlumno = musiAlumnoDao.mapeaRegId(codigoId);
			if (musiPadresDao.existeReg(codigoId)) {
				musiPadres = musiPadresDao.mapeaRegId(codigoId);
				
				// Asignar valores a las Tablas AlumPersonal y AlumUbicacion 
				alumPersonal.setCodigoPersonal(alumPersonalDao.maximoReg(tipo));
				alumPersonal.setNombre(musiAlumno.getNombre());
				alumPersonal.setApellidoPaterno(musiAlumno.getApellidoPaterno());
				alumPersonal.setApellidoMaterno(musiAlumno.getApellidoMaterno());
				alumPersonal.setNombreLegal(musiAlumno.getNombre()+" "+musiAlumno.getApellidoPaterno()+" "+musiAlumno.getApellidoMaterno());				
				alumPersonal.setFNacimiento(musiAlumno.getFechaNac());
				alumPersonal.setSexo(musiAlumno.getGenero());
				alumPersonal.setReligionId(musiAlumno.getReligionId());
				alumPersonal.setTelefono(musiAlumno.getCelular());
				alumPersonal.setEmail(musiAlumno.getEmail());
				alumPersonal.setPaisId(musiAlumno.getPaisId());
				alumPersonal.setEstadoId(musiAlumno.getEstadoId());
				alumPersonal.setCiudadId(musiAlumno.getCiudadId());
				alumPersonal.setNacionalidad(musiAlumno.getNacionalidad());
				alumPersonal.setFCreado(aca.util.Fecha.getHoy());
				alumPersonal.setUsAlta(codigoPersonal);
				
				if (alumPersonalDao.insertReg(alumPersonal)) {
					alumUbicacion.setCodigoPersonal(alumPersonal.getCodigoPersonal());
					alumUbicacion.setCodigoPadre("0");
					alumUbicacion.setCodigoMadre("0");
					alumUbicacion.setpNombre(musiPadres.getPadNombre()+" "+musiPadres.getPadPaterno()+" "+musiPadres.getPadMaterno());
					alumUbicacion.setmNombre(musiPadres.getMadNombre()+" "+musiPadres.getMadPaterno()+" "+musiPadres.getMadMaterno());
					alumUbicacion.setpReligion(musiPadres.getPadReligionId());
					alumUbicacion.setmReligion(musiPadres.getMadReligionId());
					alumUbicacion.settNombre(musiPadres.getPadNombre()+" "+musiPadres.getPadPaterno()+" "+musiPadres.getPadMaterno());
					alumUbicacion.settDireccion(musiPadres.getPadDireccion());
					alumUbicacion.settTelefono(musiPadres.getPadTelcelular());
					alumUbicacion.settEmail(musiPadres.getPadCorreo());
					
					if (alumUbicacionDao.insertReg(alumUbicacion)) {
						musiAlumno.setCodigoUM(alumPersonal.getCodigoPersonal());
						musiAlumno.setEstado("A");
						if (musiAlumnoDao.updateReg(musiAlumno)) {
							mensaje = "¡Grabado!";
						}
					}
				}				
			}
		}
		
		return "redirect:/admision/cimum/traspasar?Codigo="+codigoId+"&Estado="+estado+"&mensaje="+mensaje;
	}
		
}