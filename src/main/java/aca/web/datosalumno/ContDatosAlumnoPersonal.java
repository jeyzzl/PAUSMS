package aca.web.datosalumno;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumAcademico;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumUbicacion;
import aca.carga.spring.CargaAlumno;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatTipoAlumno;
import aca.plan.spring.MapaPlanDao;

@Controller
public class ContDatosAlumnoPersonal {		
	
	
	@Autowired
	private aca.carga.spring.CargaAlumnoDao cargaAlumnoDao;	
	
	@Autowired
	aca.acceso.spring.AccesoDao accesoDao;
	
	@Autowired
	aca.alumno.spring.AlumAcademicoDao alumAcademicoDao;
	
	@Autowired
	aca.alumno.spring.AlumPersonalDao alumPersonalDao;
	
	@Autowired
	aca.alumno.spring.AlumUbicacionDao alumUbicacionDao;
	
	@Autowired
	aca.carga.spring.CargaBloqueDao cargaBloqueDao;
	
	@Autowired
	aca.catalogo.spring.CatReligionDao catReligionDao;
	
	@Autowired
	aca.catalogo.spring.CatPaisDao catPaisDao;
	
	@Autowired
	aca.catalogo.spring.CatInstitucionDao catInstitucionDao;
	
	@Autowired
	aca.catalogo.spring.CatTipoAlumnoDao catTipoAlumnoDao;
	
	@Autowired
	private CatCarreraDao catCarreraDao;
	
	@Autowired
	private CatFacultadDao catFacultadDao;
	
	
	@RequestMapping("/datos_alumno/personal/datos")
	public String datosAlumnoPersonalDatos(HttpServletRequest request, Model modelo){
		
		String mensaje		= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		
		HttpSession sesion		= null;		
		String matricula 		= "0";
		String codigoPersonal	= "0";
		String cargaId			= "-";
		
		sesion = ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	matricula 		= (String) sesion.getAttribute("codigoAlumno");
        	cargaId 		= (String) sesion.getAttribute("cargaId");
        	codigoPersonal 	= (String) sesion.getAttribute("codigoPersonal");
        }
        
        AlumPersonal alumno = new AlumPersonal();
        AlumUbicacion ubicacion = new AlumUbicacion();
        AlumAcademico academico = new AlumAcademico();
        
        String nombreCarrera = "-";        
        String nombreReligion = "-";
        
        if(alumPersonalDao.existeAlumno(matricula)) {
        	alumno = alumPersonalDao.mapeaRegId(matricula);
            ubicacion = alumUbicacionDao.mapeaRegId(matricula);
            academico = alumAcademicoDao.mapeaRegId(matricula);
            nombreCarrera = alumPersonalDao.getCarrera(matricula);
            nombreReligion = catReligionDao.getNombreReligion(alumno.getReligionId());
        }
        
        String modalidadUsuario = accesoDao.getModalidad(matricula);
        String modalidadAlumno = alumAcademicoDao.getModalidadId(matricula);
        boolean cargaActiva = cargaBloqueDao.CargaActiva(cargaId);
        String nombreNacionalidad = catPaisDao.getNacionalidad(alumno.getNacionalidad());
        String nombreInstitucion = catInstitucionDao.getNombreInstitucion(academico.getObreroInstitucion());
        String nombreTipo = catTipoAlumnoDao.getNombreTipo(academico.getTipoAlumno());
        boolean alumnoInscrito = alumPersonalDao.esInscrito(matricula);
        
        List<CargaAlumno> lisPlanes 	= cargaAlumnoDao.lisCargasActivas(matricula);    

        boolean puedeEditar	 	= false;
        boolean esCoordinador 	= catCarreraDao.esCoordinador(codigoPersonal);
        boolean esDirector 		= catFacultadDao.esDirector(codigoPersonal);                	
        if(esCoordinador || esDirector){
        	puedeEditar = true;
        }    
        
        List<CatTipoAlumno> lista = catTipoAlumnoDao.getListAll(" ORDER BY NOMBRE_TIPO");
        
        modelo.addAttribute("alumno",alumno);
        modelo.addAttribute("ubicacion",ubicacion);
        modelo.addAttribute("academico",academico);
        modelo.addAttribute("modalidadUsuario",modalidadUsuario);
        modelo.addAttribute("modalidadAlumno",modalidadAlumno);
        modelo.addAttribute("cargaActiva",cargaActiva);
        modelo.addAttribute("nombreCarrera",nombreCarrera);
        modelo.addAttribute("nombreReligion",nombreReligion);
        modelo.addAttribute("nombreNacionalidad",nombreNacionalidad);
        modelo.addAttribute("nombreInstitucion",nombreInstitucion);
        modelo.addAttribute("nombreTipo",nombreTipo);
        modelo.addAttribute("alumnoInscrito",alumnoInscrito);
		modelo.addAttribute("lisPlanes",lisPlanes);
		modelo.addAttribute("puedeEditar",puedeEditar);
		modelo.addAttribute("lista",lista);
		modelo.addAttribute("mensaje",mensaje);
		
		return "datos_alumno/personal/datos";
	}
	
	@RequestMapping("/datos_alumno/personal/editarDatos")
	public String datosAlumnoPersonalEditarDatos(HttpServletRequest request){
		
		String email		= request.getParameter("email")==null?"0":request.getParameter("email");
		String telefono		= request.getParameter("telefono")==null?"0":request.getParameter("telefono");
		
		String matricula 		= "0";
		HttpSession sesion = ((HttpServletRequest)request).getSession();
		
        if (sesion != null){
        	matricula 		= (String) sesion.getAttribute("codigoAlumno");
        }
        
        AlumPersonal alumno = new AlumPersonal();
        
        if(alumPersonalDao.existeAlumno(matricula)) {
        	alumno = alumPersonalDao.mapeaRegId(matricula);
        }
		
		if(!email.equals("0")) {
			alumno.setEmail(email);
		}
		if(!telefono.equals("0")) {
			alumno.setTelefono(telefono);
		}
		
		alumPersonalDao.updateReg(alumno);
		
		return "redirect:/datos_alumno/personal/datos";
	}
	
	@RequestMapping("/datos_alumno/personal/cambiarTipo")
	public String datosAlumnoPersonalCambiarTipo(HttpServletRequest request){
		
		String tipoId	= request.getParameter("TipoId")==null?"0":request.getParameter("TipoId");
		String mensaje 	= "0";
		
		String matricula 		= "0";
		HttpSession sesion = ((HttpServletRequest)request).getSession();
		
        if (sesion != null){
        	matricula 		= (String) sesion.getAttribute("codigoAlumno");
        }
        
        AlumAcademico alumno = new AlumAcademico();
		
		if(alumAcademicoDao.existeReg(matricula)) {
			alumno = alumAcademicoDao.mapeaRegId(matricula);
		}
		
		if(!tipoId.equals("0")) {
			alumno.setTipoAlumno(tipoId);
		}
		
		if(alumAcademicoDao.updateReg(alumno)) {
			mensaje = "1";
		}
		
		return "redirect:/datos_alumno/personal/datos?Mensaje="+mensaje;
	}
	
}