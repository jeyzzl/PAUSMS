package aca.web.admision;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumAcademico;
import aca.alumno.spring.AlumAcademicoDao;
import aca.alumno.spring.AlumBanco;
import aca.alumno.spring.AlumBancoDao;
import aca.alumno.spring.AlumEstudio;
import aca.alumno.spring.AlumEstudioDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlanDao;
import aca.alumno.spring.AlumUbicacion;
import aca.alumno.spring.AlumUbicacionDao;
import aca.catalogo.CatNivel;
import aca.catalogo.spring.CatBanco;
import aca.catalogo.spring.CatBancoDao;
import aca.catalogo.spring.CatCiudad;
import aca.catalogo.spring.CatCiudadDao;
import aca.catalogo.spring.CatCultural;
import aca.catalogo.spring.CatCulturalDao;
import aca.catalogo.spring.CatEstado;
import aca.catalogo.spring.CatEstadoDao;
import aca.catalogo.spring.CatExtension;
import aca.catalogo.spring.CatExtensionDao;
import aca.catalogo.spring.CatInstitucion;
import aca.catalogo.spring.CatInstitucionDao;
import aca.catalogo.spring.CatModalidad;
import aca.catalogo.spring.CatModalidadDao;
import aca.catalogo.spring.CatNivelInicio;
import aca.catalogo.spring.CatNivelInicioDao;
import aca.catalogo.spring.CatPais;
import aca.catalogo.spring.CatPaisDao;
import aca.catalogo.spring.CatRecogida;
import aca.catalogo.spring.CatRecogidaDao;
import aca.catalogo.spring.CatRegion;
import aca.catalogo.spring.CatRegionDao;
import aca.catalogo.spring.CatReligion;
import aca.catalogo.spring.CatReligionDao;
import aca.catalogo.spring.CatTipoAlumno;
import aca.catalogo.spring.CatTipoAlumnoDao;
import aca.log.spring.LogAlumno;
import aca.log.spring.LogAlumnoDao;
import aca.parametros.spring.Parametros;
import aca.parametros.spring.ParametrosDao;
import aca.sep.spring.SepLugar;
import aca.sep.spring.SepLugarDao;
import aca.vista.spring.AlumnoCursoDao;
import aca.vista.spring.MaestrosDao;
import aca.vista.spring.UsuariosDao;

@Controller
public class ContAdmisionDatos {

	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;

	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;

	@Autowired
	private AlumPersonalDao alumPersonalDao;

	@Autowired
	private AlumUbicacionDao alumUbicacionDao;

	@Autowired
	private AlumAcademicoDao alumAcademicoDao;

	@Autowired
	private AlumPlanDao alumPlanDao;

	@Autowired
	private AlumnoCursoDao alumnoCursoDao;

	@Autowired
	private AccesoDao accesoDao;

	@Autowired
	private CatReligionDao catReligionDao;

	@Autowired
	private CatRecogidaDao catRecogidaDao;

	@Autowired
	private CatPaisDao catPaisDao;

	@Autowired
	private CatEstadoDao catEstadoDao;

	@Autowired
	private CatCiudadDao catCiudadDao;

	@Autowired
	private CatTipoAlumnoDao catTipoAlumnoDao;

	@Autowired
	private CatInstitucionDao catInstitucionDao;

	@Autowired
	private CatModalidadDao catModalidadDao;

	@Autowired
	private CatExtensionDao catExtensionDao;

	@Autowired
	private CatCulturalDao catCulturalDao;
	
	@Autowired
	private CatRegionDao catRegionDao;

	@Autowired
	private SepLugarDao sepLugarDao;

	@Autowired
	private LogAlumnoDao logAlumnoDao;

	@Autowired
	private UsuariosDao usuariosDao;

	@Autowired
	private MaestrosDao maestrosDao;

	@Autowired
	AlumEstudioDao	alumEstudioDao;

	@Autowired
	AlumBancoDao alumBancoDao;

	@Autowired
	CatNivelInicioDao catNivelInicioDao;

	@Autowired
	private CatBancoDao catBancoDao;

	@Autowired
	private ParametrosDao parametrosDao;


	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){
			System.out.println(url+" "+ex);
		}
	}

	@RequestMapping("/admision/datos/getEstados")
	@ResponseBody
	public String admisionDatosGetestados(HttpServletRequest request){
		String paisId 					= request.getParameter("paisId")==null?"":request.getParameter("paisId");
		String mensaje					= "";
		List<CatEstado> lisEstados 		= catEstadoDao.getLista(paisId,"ORDER BY 1,3");
		for(CatEstado edo: lisEstados){
			mensaje+= "<option value='"+edo.getEstadoId()+"'>"+ edo.getNombreEstado()+"</option>";
		}
		return mensaje;
	}

	@RequestMapping("/admision/datos/getCiudades")
	@ResponseBody
	public String admisionDatosGetciudades(HttpServletRequest request){
		String paisId 				= request.getParameter("paisId")==null?"":request.getParameter("paisId");
		String estadoId 			= request.getParameter("estadoId")==null?"":request.getParameter("estadoId");
		String mensaje 				= "";
		List<CatCiudad> lisCiudades = catCiudadDao.getLista(paisId, estadoId," ORDER BY 4");

		for(CatCiudad ciudad: lisCiudades){
			mensaje +=" <option value='"+ciudad.getCiudadId()+"'>"+ ciudad.getNombreCiudad()+"</option>";
		}
		return mensaje;
	}
	
	@RequestMapping("/admision/datos/getRegiones")
	@ResponseBody
	public String admisionDatosGetRegiones(HttpServletRequest request){
		String culturalId 			= request.getParameter("culturalId")==null?"":request.getParameter("culturalId");
		String mensaje 				= "";
		List<CatRegion> lisRegiones = catRegionDao.getLista(culturalId, "");

		for(CatRegion region: lisRegiones){
			mensaje +=" <option value='"+region.getRegionId()+"'>"+ region.getNombreRegion()+"</option>";
		}
		return mensaje;
	}

	@RequestMapping("/admision/datos/accion_e")
	public String admisionDatosAccionE(HttpServletRequest request, Model modelo){
		String codigoAlumno 			= "0";
		String mensaje 	= "";
		String colMens	= "";

		String id	 		= request.getParameter("Id")==null?"0":request.getParameter("Id");
		String titulo 		= request.getParameter("Titlulo")==null?"-":request.getParameter("Titlulo");
		String institucion 	= request.getParameter("Institucion")==null?"-":request.getParameter("Institucion");
		String completo 	= request.getParameter("Completo")==null?"-":request.getParameter("Completo");
		String inicio 		= request.getParameter("Inicio")==null?"-":request.getParameter("Inicio");
		String fin 			= request.getParameter("Fin")==null?"-":request.getParameter("Fin");
		String dependencia 	= request.getParameter("Dependencia")==null?"-":request.getParameter("Dependencia");
		String convalida 	= request.getParameter("Convalida")==null?"-":request.getParameter("Convalida");
		String accion 		= request.getParameter("Accion")==null?"0":request.getParameter("Accion");

		HttpSession sesion			= request.getSession();
        if (sesion!=null){
        	codigoAlumno			= (String)sesion.getAttribute("codigoAlumno");
        }
		String nombreUsuario 		= usuariosDao.getNombreUsuario(codigoAlumno,"NOMBRE");

		List<AlumEstudio> lisAdmisiones = alumEstudioDao.lisAlumEstudio(codigoAlumno);
		AlumEstudio alumEstudio = new AlumEstudio();

		if(accion.equals("1")){
			alumEstudio.setCodigoPersonal(codigoAlumno);
			alumEstudio.setTitulo(titulo);
			alumEstudio.setInstitucion(institucion);
			alumEstudio.setCompleto(completo);
			alumEstudio.setInicio(inicio);
			alumEstudio.setFin(fin);
			alumEstudio.setDependencia(dependencia);
			alumEstudio.setConvalida(convalida);

			// Valida si existe el registro
			if (alumEstudioDao.existeReg(codigoAlumno, id)){
				alumEstudio.setId(id);
				if(alumEstudioDao.updateReg(alumEstudio)){
					mensaje = "Updated";
					colMens = "success";
				}else{
					mensaje = "Error updating";
					colMens = "danger";
				}
			}else{
				alumEstudio.setId(alumEstudioDao.maximoReg(codigoAlumno));
				if(alumEstudioDao.insertReg(alumEstudio)){
					mensaje = "Saved";
					colMens = "success";
				}else{
					mensaje = "Error Saving";
					colMens = "danger";
				}
			}
		}else if (accion.equals("2")){

			if(alumEstudioDao.deleteReg(codigoAlumno, id )){
				mensaje = "Deleted";
				colMens = "success";
			}else{
				mensaje = "Error deleting";
				colMens = "danger";
			}
		}else if (accion.equals("3")){
			if (alumEstudioDao.existeReg(codigoAlumno, id)){
				alumEstudio = alumEstudioDao.mapeaRegId(codigoAlumno, id);
			}
		}

		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("codigoAlumno", codigoAlumno);
		modelo.addAttribute("nombreUsuario", nombreUsuario);
		modelo.addAttribute("alumEstudio", alumEstudio);

        modelo.addAttribute("lisAdmisiones", lisAdmisiones);

		return "admision/datos/accion_e";
	}

	@RequestMapping("/admision/datos/accion_a")
	public String admisionDatosAccionA(HttpServletRequest request, Model modelo){

		String codigoAlumno 			= "0";
		String nombreAlumno				= "-";
		String usuarioActualizo			= "-";
		String cotejado					= "N";
		AlumAcademico alumAcademico 	= new AlumAcademico();
		LogAlumno logAlumno				= new LogAlumno();
		Acceso acceso					= new Acceso();

		boolean existeAcademico			= false;
		HttpSession sesion		= request.getSession();
        if (sesion!=null){
        	codigoAlumno		= (String)sesion.getAttribute("codigoAlumno");
        	if (alumAcademicoDao.existeReg(codigoAlumno)){
        		alumAcademico = alumAcademicoDao.mapeaRegId(codigoAlumno);
        		existeAcademico = true;
        	}
        	if (alumPersonalDao.existeReg(codigoAlumno)) {
        		cotejado 		= alumPersonalDao.getCotejado(codigoAlumno);
        		nombreAlumno 	= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
        	}
        }

        boolean esInscrito = alumAcademicoDao.esInscrito(codigoAlumno);
        if (accesoDao.existeReg(codigoAlumno)) acceso = accesoDao.mapeaRegId(codigoAlumno);

        String maxUpdate = logAlumnoDao.maximoId("ALUM_ACADEMICO", codigoAlumno);
        if (!maxUpdate.equals("0")){
    		logAlumno = logAlumnoDao.mapeaRegId(maxUpdate);
    		if (maestrosDao.existeReg(logAlumno.getUsuario()) ) {
    			usuarioActualizo = maestrosDao.getNombreCorto(logAlumno.getUsuario(), "NOMBRE");
    		}else {
    			usuarioActualizo = alumPersonalDao.getNombreCorto(logAlumno.getUsuario());
    		}
    	}

        List<CatTipoAlumno> lisTiposAlumno		= catTipoAlumnoDao.getListAll(" ORDER BY NOMBRE_TIPO");
        List<CatInstitucion> lisInstituciones 	= catInstitucionDao.getListAll(" ORDER BY NOMBRE_INSTITUCION");
        List<SepLugar> lisSepLugares 			= sepLugarDao.lisAll("ORDER BY LUGAR_ID");
        List<CatModalidad> lisModalidades		= catModalidadDao.getListAll(" ORDER BY ENLINEA, NOMBRE_MODALIDAD");
        List<CatExtension> lisExtensiones		= catExtensionDao.getListAll(" ORDER BY NOMBRE_EXTENSION");
		List<CatNivelInicio> lisNivelInicio 	= catNivelInicioDao.getListAll(" ORDER BY NIVEL_INICIO_ID");

        modelo.addAttribute("nombreAlumno", nombreAlumno);
        modelo.addAttribute("existeAcademico", existeAcademico);
        modelo.addAttribute("alumAcademico", alumAcademico);
        modelo.addAttribute("logAlumno", logAlumno);
        modelo.addAttribute("maxUpdate", maxUpdate);
        modelo.addAttribute("usuarioActualizo", usuarioActualizo);
        modelo.addAttribute("cotejado", cotejado);
        modelo.addAttribute("acceso", acceso);
        modelo.addAttribute("esInscrito", esInscrito);
        modelo.addAttribute("lisTiposAlumno", lisTiposAlumno);
        modelo.addAttribute("lisInstituciones", lisInstituciones);
        modelo.addAttribute("lisSepLugares", lisSepLugares);
        modelo.addAttribute("lisModalidades", lisModalidades);
        modelo.addAttribute("lisExtensiones", lisExtensiones);
		modelo.addAttribute("lisNivelInicio", lisNivelInicio);

		return "admision/datos/accion_a";
	}

	@RequestMapping("/admision/datos/grabarAcademico")
	public String admisionDatosGrabarAcademico(HttpServletRequest request){

		String codigoAlumno 		= "0";
		String codigoPersonal 		= "0";
		String mensaje				= "-";
		AlumAcademico alumAcademico = new AlumAcademico();
		AlumPersonal alumPersonal 	= new AlumPersonal();
		LogAlumno logAlumno 		= new LogAlumno();
		HttpSession sesion			= request.getSession();
        if (sesion!=null){
        	codigoAlumno			= (String)sesion.getAttribute("codigoAlumno");
        	codigoPersonal			= (String)sesion.getAttribute("codigoPersonal");
        	alumAcademico.setCodigoPersonal(codigoAlumno);
        	alumAcademico.setResidenciaId(request.getParameter("ResidenciaId"));
        	alumAcademico.setClasFin(request.getParameter("ClasFin"));
        	alumAcademico.setObrero(request.getParameter("Obrero"));
        	alumAcademico.setObreroInstitucion(request.getParameter("ObreroInstitucion"));
        	alumAcademico.setTipoAlumno(request.getParameter("TipoAlumno"));
        	alumAcademico.setFSolicitud(request.getParameter("FSolicitud"));
        	alumAcademico.setFAdmision(request.getParameter("FAdmision"));
        	alumAcademico.setFAlta(request.getParameter("FAlta"));
        	alumAcademico.setModalidadId(request.getParameter("ModalidadId"));
        	alumAcademico.setPrepaLugar("0");
        	alumAcademico.setExtensionId(request.getParameter("ExtensionId")==null?"0":request.getParameter("ExtensionId"));
        	alumAcademico.setDormitorio(request.getParameter("Dormitorio"));
        	alumAcademico.setRequerido("S");
			alumAcademico.setNivelInicioId(request.getParameter("NivelInicioId")==null?"0":request.getParameter("NivelInicioId"));
        	if (alumPersonalDao.existeReg(codigoAlumno)) {
        		alumPersonal = alumPersonalDao.mapeaRegId(codigoAlumno);
        		if (alumPersonal.getReligionId().equals("1"))
        			alumAcademico.setClasFin("1");
        		else
        			alumAcademico.setClasFin("2");
        	}
        	if (!alumAcademicoDao.existeReg(codigoAlumno)) {
        		// insert
        		if (alumAcademicoDao.insertReg(alumAcademico)){
					logAlumno.setId( logAlumnoDao.maximoReg());
					logAlumno.setTabla("ALUM_ACADEMICO");
					logAlumno.setOperacion("INSERT");
					logAlumno.setUsuario(codigoPersonal);
					logAlumno.setIp(request.getRemoteAddr());
					logAlumno.setDatos(codigoAlumno);
					if (logAlumnoDao.insertReg(logAlumno)){
						mensaje = "Saved: "+alumAcademico.getCodigoPersonal();
					}else{
						mensaje = "Error saving: "+alumAcademico.getCodigoPersonal();
					}
        		}else{
					mensaje = "Error saving record: "+alumAcademico.getCodigoPersonal();
				}
			}else{
				if (alumAcademicoDao.updateReg(alumAcademico)){
					logAlumno.setId( logAlumnoDao.maximoReg());
					logAlumno.setTabla("ALUM_ACADEMICO");
					logAlumno.setOperacion("UPDATE");
					logAlumno.setUsuario(codigoPersonal);
					logAlumno.setIp(request.getRemoteAddr());
					logAlumno.setDatos(alumAcademico.getCodigoPersonal());
					if (logAlumnoDao.insertReg(logAlumno)){
						mensaje = "Modified: "+alumAcademico.getCodigoPersonal();
					}else{
						mensaje = "Error modifying: "+alumAcademico.getCodigoPersonal();
					}
				}else{
					mensaje = "Error updating: "+alumAcademico.getCodigoPersonal();
				}
        	}
        }
		return "redirect:/admision/datos/accion_a?Mensaje="+mensaje;
	}

	@RequestMapping("/admision/datos/borrarAcademico")
	public String admisionDatosBorrarAcademico(HttpServletRequest request){

		String codigoAlumno 		= "0";
		String mensaje				= "-";
		HttpSession sesion			= request.getSession();
        if (sesion!=null){
        	codigoAlumno			= (String)sesion.getAttribute("codigoAlumno");
        	if (alumAcademicoDao.existeReg(codigoAlumno)){
				if (alumAcademicoDao.deleteReg(codigoAlumno)){
					mensaje = "Deleted: "+codigoAlumno;
				}else{
					mensaje = "Error deleting: "+codigoAlumno;
				}
			}else{
				mensaje = "Not found: "+codigoAlumno;
			}
        }
		return "redirect:/admision/datos/accion_a?Mensaje="+mensaje;
	}

	@RequestMapping("/admision/datos/accion_o")
	public String admisionDatosAccionO(HttpServletRequest request, Model modelo) {

		String codigoAlumno 			= "0";
		String nombreAlumno				= "-";
		String usuarioActualizo			= "-";
		String cotejado					= "N";
		AlumBanco alumBanco 			= new AlumBanco();
		LogAlumno logAlumno				= new LogAlumno();

		boolean existeBanco			= false;
		HttpSession sesion		= request.getSession();
        if (sesion!=null){
        	codigoAlumno		= (String)sesion.getAttribute("codigoAlumno");
        	if (alumBancoDao.existeReg(codigoAlumno)){
				// System.out.println(codigoAlumno);
        		alumBanco = alumBancoDao.mapeaRegId(codigoAlumno);
        		existeBanco = true;
        	}
        	if (alumPersonalDao.existeReg(codigoAlumno)) {
        		cotejado 		= alumPersonalDao.getCotejado(codigoAlumno);
        		nombreAlumno 	= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
        	}
        }

		CatBanco catBanco = new CatBanco();
		if(catBancoDao.existeReg(alumBanco.getBancoId())){
			catBanco = catBancoDao.mapeaRegId(alumBanco.getBancoId());
		}

		List<CatBanco> lisBancos = catBancoDao.getListAll(" ORDER BY NOMBRE");

        String maxUpdate = logAlumnoDao.maximoId("ALUM_BANCO", codigoAlumno);
    	if (!maxUpdate.equals("0")){
    		logAlumno = logAlumnoDao.mapeaRegId(maxUpdate);
    		if (maestrosDao.existeReg(logAlumno.getUsuario())) {
    			usuarioActualizo = maestrosDao.getNombreCorto(logAlumno.getUsuario(), "NOMBRE");
    		}else {
    			usuarioActualizo = alumPersonalDao.getNombreCorto(logAlumno.getUsuario());
    		}
    	}

    	modelo.addAttribute("codigoAlumno", codigoAlumno);
    	modelo.addAttribute("nombreAlumno", nombreAlumno);
    	modelo.addAttribute("existeBanco", existeBanco);
    	modelo.addAttribute("usuarioActualizo", usuarioActualizo);
    	modelo.addAttribute("cotejado", cotejado);
    	modelo.addAttribute("alumBanco", alumBanco);
		modelo.addAttribute("lisBancos", lisBancos);
		modelo.addAttribute("catBanco", catBanco);
    	modelo.addAttribute("logAlumno", logAlumno);
    	modelo.addAttribute("maxUpdate", maxUpdate);

		return "admision/datos/accion_o";
	}

	@RequestMapping("/admision/datos/grabarBanco")
	public String admisionDatosGrabarBanco(HttpServletRequest request){
		String bancoId = request.getParameter("banco")==null?"0":request.getParameter("banco");
		String bancoRama = request.getParameter("bancoRama")==null?"0":request.getParameter("bancoRama");
		String cuentaNombre = request.getParameter("cuentaNombre")==null?"0":request.getParameter("cuentaNombre");
		String cuentaNumero = request.getParameter("cuentaNumero")==null?"0":request.getParameter("cuentaNumero");
		String cuentaTipo = request.getParameter("cuentaTipo")==null?"0":request.getParameter("cuentaTipo");
		String numeroBbs = request.getParameter("numeroBbs")==null?"0":request.getParameter("numeroBbs");
		String codigoSwift = request.getParameter("codigoSwift")==null?"0":request.getParameter("codigoSwift");

		String codigoAlumno 		= "0";
		String codigoPersonal 		= "0";
		String mensaje				= "-";
		AlumBanco alumBanco			= new AlumBanco();
		LogAlumno logAlumno 		= new LogAlumno();
		HttpSession sesion			= request.getSession();

        if (sesion!=null){
        	codigoAlumno			= (String)sesion.getAttribute("codigoAlumno");
        	codigoPersonal			= (String)sesion.getAttribute("codigoPersonal");
		}
			
		alumBanco.setCodigoPersonal(codigoAlumno);
		alumBanco.setBancoId(bancoId);
		alumBanco.setBancoRama(bancoRama);
		alumBanco.setCuentaNombre(cuentaNombre);
		alumBanco.setCuentaNumero(cuentaNumero);
		alumBanco.setCuentaTipo(cuentaTipo);
		alumBanco.setNumeroBBS(numeroBbs);
		alumBanco.setCodigoSwift(codigoSwift);

		if (!alumBancoDao.existeReg(codigoAlumno)) {
			if (alumBancoDao.insertReg(alumBanco)){
				logAlumno.setId( logAlumnoDao.maximoReg());
				logAlumno.setTabla("ALUM_BANCO");
				logAlumno.setOperacion("INSERT");
				logAlumno.setUsuario(codigoPersonal);
				logAlumno.setIp(request.getRemoteAddr());
				logAlumno.setDatos(codigoAlumno);
				if (logAlumnoDao.insertReg(logAlumno)){
					mensaje = "Saved: "+alumBanco.getCodigoPersonal();
				}else{
					mensaje = "Error saving: "+alumBanco.getCodigoPersonal();
				}
			}else{
				mensaje = "Error saving record: "+alumBanco.getCodigoPersonal();
			}
		}else{
			if (alumBancoDao.updateReg(alumBanco)){
				logAlumno.setId( logAlumnoDao.maximoReg());
				logAlumno.setTabla("ALUM_BANCO");
				logAlumno.setOperacion("UPDATE");
				logAlumno.setUsuario(codigoPersonal);
				logAlumno.setIp(request.getRemoteAddr());
				logAlumno.setDatos(alumBanco.getCodigoPersonal());
				if (logAlumnoDao.insertReg(logAlumno)){
					mensaje = "Modified: "+alumBanco.getCodigoPersonal();
				}else{
					mensaje = "Error modifying: "+alumBanco.getCodigoPersonal();
				}
			}else{
				mensaje = "Error updating: "+alumBanco.getCodigoPersonal();
			}
		}
		return "redirect:/admision/datos/accion_o?Mensaje="+mensaje;
	}

	@RequestMapping("/admision/datos/borrarBanco")
	public String admisionDatosBorrarBanco(HttpServletRequest request){

		String codigoAlumno 		= "0";
		String mensaje				= "-";
		HttpSession sesion			= request.getSession();
        if (sesion!=null){
        	codigoAlumno			= (String)sesion.getAttribute("codigoAlumno");
        	if (alumBancoDao.existeReg(codigoAlumno)){
				if (alumBancoDao.deleteReg(codigoAlumno)){
					mensaje = "Deleted: "+codigoAlumno;
				}else{
					mensaje = "Error deleting: "+codigoAlumno;
				}
			}else{
				mensaje = "Not found: "+codigoAlumno;
			}
        }
		return "redirect:/admision/datos/accion_o?Mensaje="+mensaje;
	}

	@RequestMapping("/admision/datos/accion_u")
	public String admisionDatosAccionU(HttpServletRequest request, Model modelo){

		String codigoAlumno 			= "0";
		String nombreAlumno				= "-";
		String usuarioActualizo			= "-";
		String cotejado					= "N";
		AlumUbicacion alumUbicacion 	= new AlumUbicacion();
		LogAlumno logAlumno				= new LogAlumno();
		Parametros parametros			= parametrosDao.mapeaRegId("1");

		boolean existeUbicacion			= false;
		HttpSession sesion		= request.getSession();
        if (sesion!=null){
        	codigoAlumno		= (String)sesion.getAttribute("codigoAlumno");
        	if (alumUbicacionDao.existeReg(codigoAlumno)){
        		alumUbicacion = alumUbicacionDao.mapeaRegId(codigoAlumno);
        		existeUbicacion = true;
        	}
        	if (alumPersonalDao.existeReg(codigoAlumno)) {
        		cotejado 		= alumPersonalDao.getCotejado(codigoAlumno);
        		nombreAlumno 	= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
        	}
        }

        List<CatReligion> lisReligiones 		= catReligionDao.getListAll(" ORDER BY 2");
        List<CatPais> lisPaises 				= catPaisDao.getListAllWithPriorityCountry(parametros.getPaisId());
        List<CatEstado> lisEstados 				= catEstadoDao.getLista(alumUbicacion.gettPais() ,"ORDER BY 1,3");
        List<CatCiudad> lisCiudades 			= catCiudadDao.getLista(alumUbicacion.gettPais(), alumUbicacion.gettEstado(), "ORDER BY 4");
        List<CatEstado> lisPEstados 			= catEstadoDao.getLista(alumUbicacion.getpPais() ,"ORDER BY 1,3");
        List<CatCiudad> lisPCiudades 			= catCiudadDao.getLista(alumUbicacion.getpPais(), alumUbicacion.getpEstado(), "ORDER BY 4");
        List<CatEstado> lisMEstados 			= catEstadoDao.getLista(alumUbicacion.getmPais() ,"ORDER BY 1,3");
        List<CatCiudad> lisMCiudades 			= catCiudadDao.getLista(alumUbicacion.getmPais(), alumUbicacion.getmEstado(), "ORDER BY 4");
		List<CatRecogida> lisRecogidas 			= catRecogidaDao.getListAll(" ORDER BY 2");

        String maxUpdate = logAlumnoDao.maximoId("ALUM_UBICACION", codigoAlumno);
    	if (!maxUpdate.equals("0")){
    		logAlumno = logAlumnoDao.mapeaRegId(maxUpdate);
    		if (maestrosDao.existeReg(logAlumno.getUsuario())) {
    			usuarioActualizo = maestrosDao.getNombreCorto(logAlumno.getUsuario(), "NOMBRE");
    		}else {
    			usuarioActualizo = alumPersonalDao.getNombreCorto(logAlumno.getUsuario());
    		}
    		//usuarioActualizo = usuariosDao.getNombreUsuarioCorto(logAlumno.getUsuario());
    	}

    	modelo.addAttribute("nombreAlumno", nombreAlumno);
        modelo.addAttribute("existeUbicacion", existeUbicacion);
        modelo.addAttribute("alumUbicacion", alumUbicacion);
        modelo.addAttribute("logAlumno", logAlumno);
        modelo.addAttribute("maxUpdate", maxUpdate);
        modelo.addAttribute("usuarioActualizo", usuarioActualizo);
        modelo.addAttribute("cotejado", cotejado);

        modelo.addAttribute("lisReligiones", lisReligiones);
		modelo.addAttribute("lisPaises", lisPaises);
		modelo.addAttribute("lisEstados", lisEstados);
		modelo.addAttribute("lisCiudades", lisCiudades);
		modelo.addAttribute("lisPEstados", lisPEstados);
		modelo.addAttribute("lisPCiudades", lisPCiudades);
		modelo.addAttribute("lisMEstados", lisMEstados);
		modelo.addAttribute("lisMCiudades", lisMCiudades);
		modelo.addAttribute("lisRecogidas", lisRecogidas);

		return "admision/datos/accion_u";
	}

	@RequestMapping("/admision/datos/grabarUbicacion")
	public String admisionDatosGrabarUbicacion(HttpServletRequest request){

		String codigoAlumno 		= "0";
		String codigoPersonal 		= "0";
		String mensaje				= "-";
		AlumUbicacion alumUbicacion = new AlumUbicacion();
		LogAlumno logAlumno 		= new LogAlumno();
		HttpSession sesion			= request.getSession();
        if (sesion!=null){
        	codigoAlumno			= (String)sesion.getAttribute("codigoAlumno");
        	codigoPersonal			= (String)sesion.getAttribute("codigoPersonal");
        	alumUbicacion.setCodigoPersonal(codigoAlumno);
        	alumUbicacion.setpNombre(request.getParameter("PNombre"));
        	alumUbicacion.setpReligion(request.getParameter("PReligion"));
        	alumUbicacion.setpNacionalidad(request.getParameter("PNacionalidad"));
        	alumUbicacion.setCodigoPadre(request.getParameter("mPadre"));
        	alumUbicacion.setCodigoMadre(request.getParameter("mMadre"));
        	alumUbicacion.setFecha(aca.util.Fecha.getHoy());
        	alumUbicacion.setmNombre(request.getParameter("MNombre"));
        	alumUbicacion.setmReligion(request.getParameter("MReligion"));
        	alumUbicacion.setmNacionalidad(request.getParameter("MNacionalidad"));
        	alumUbicacion.settNombre(request.getParameter("TNombre"));
        	alumUbicacion.settDireccion(request.getParameter("TDireccion"));
        	alumUbicacion.settColonia(request.getParameter("TColonia"));
        	alumUbicacion.settCodigo(request.getParameter("TCodigo"));
        	alumUbicacion.settApartado(request.getParameter("TApartado"));
        	alumUbicacion.settTelefono(request.getParameter("TTelefono"));
        	alumUbicacion.settEmail(request.getParameter("TEmail"));
        	alumUbicacion.settCelular(request.getParameter("TCelular"));
        	alumUbicacion.settComunica(request.getParameter("TComunica"));
        	alumUbicacion.settPais(request.getParameter("TPais"));
        	alumUbicacion.settEstado(request.getParameter("TEstado"));
        	alumUbicacion.settCiudad(request.getParameter("TCiudad"));
			alumUbicacion.setRecogidaId(request.getParameter("RecogidaId"));
			alumUbicacion.setpPais(request.getParameter("PPais"));
			alumUbicacion.setpEstado(request.getParameter("PEstado"));
			alumUbicacion.setpCiudad(request.getParameter("PCiudad"));
			alumUbicacion.setpOrigen(request.getParameter("POrigen"));
			alumUbicacion.setmPais(request.getParameter("MPais"));
			alumUbicacion.setmEstado(request.getParameter("MEstado"));
			alumUbicacion.setmCiudad(request.getParameter("MCiudad"));
			alumUbicacion.setmOrigen(request.getParameter("MOrigen"));

        	if (!alumUbicacionDao.existeReg(codigoAlumno)) {
        		// insert
        		if (alumUbicacionDao.insertReg(alumUbicacion)){
					logAlumno.setId( logAlumnoDao.maximoReg());
					logAlumno.setTabla("ALUM_UBICACION");
					logAlumno.setOperacion("INSERT");
					logAlumno.setUsuario(codigoPersonal);
					logAlumno.setIp(request.getRemoteAddr());
					logAlumno.setDatos(codigoAlumno);
					if (logAlumnoDao.insertReg(logAlumno)){
						mensaje = "Saved: "+alumUbicacion.getCodigoPersonal();
					}else{
						mensaje = "Error saving: "+alumUbicacion.getCodigoPersonal();
					}
        		}else{
					mensaje = "Error saving record: "+alumUbicacion.getCodigoPersonal();
				}
			}else{
				if (alumUbicacionDao.updateReg(alumUbicacion)){
					logAlumno.setId( logAlumnoDao.maximoReg());
					logAlumno.setTabla("ALUM_UBICACION");
					logAlumno.setOperacion("UPDATE");
					logAlumno.setUsuario(codigoPersonal);
					logAlumno.setIp(request.getRemoteAddr());
					logAlumno.setDatos(alumUbicacion.getCodigoPersonal());
					if (logAlumnoDao.insertReg(logAlumno)){
						mensaje = "Modified: "+alumUbicacion.getCodigoPersonal();
					}else{
						mensaje = "Error modifying: "+alumUbicacion.getCodigoPersonal();
					}
				}else{
					mensaje = "Error updating: "+alumUbicacion.getCodigoPersonal();
				}
        	}
        }
		return "redirect:/admision/datos/accion_u?Mensaje="+mensaje;
	}

	@RequestMapping("/admision/datos/borrarUbicacion")
	public String admisionDatosBorrarUbicacion(HttpServletRequest request){

		String codigoAlumno 		= "0";
		String mensaje				= "-";
		HttpSession sesion			= request.getSession();
        if (sesion!=null){
        	codigoAlumno			= (String)sesion.getAttribute("codigoAlumno");
        	if (alumUbicacionDao.existeReg(codigoAlumno)){
				if (alumUbicacionDao.deleteReg(codigoAlumno)){
					mensaje = "Deleted: "+codigoAlumno;
				}else{
					mensaje = "Error deleting: "+codigoAlumno;
				}
			}else{
				mensaje = "Not found: "+codigoAlumno;
			}
        }
		return "redirect:/admision/datos/accion_u?Mensaje="+mensaje;
	}

	@RequestMapping("/admision/datos/alumno")
	public String admisionDatosAlumno(HttpServletRequest request, Model modelo){

		String codigoAlumno 			= "0";
		String ingreso					= "Not registered";
		String nacionalidadNombre		= "-";
		String paisNombre				= "-";
		String estadoNombre				= "-";
		String ciudadNombre				= "-";
		String culturalNombre			= "-";
		String regionNombre				= "-";
		String resPaisNombre       		= "-";
		String resEstadoNombre 			= "-";
		String resCiudadNombre 			= "-";
		String usuarioActualizo			= "-";
		AlumPersonal alumPersonal 		= new AlumPersonal();
		LogAlumno logAlumno				= new LogAlumno();
		Acceso acceso 					= new Acceso();
		boolean existePersonal			= false;
		boolean existeUbicacion			= false;
		boolean existeAcademico			= false;
		boolean existePlan				= false;
		boolean existeCursos			= false;
		boolean borrarAlumno			= true;

		HttpSession sesion		= request.getSession();
        if (sesion!=null){
        	codigoAlumno		= (String)sesion.getAttribute("codigoAlumno");
        	sesion.setAttribute("mat", codigoAlumno);
        	if (alumPersonalDao.existeReg(codigoAlumno)) {
        		alumPersonal	= alumPersonalDao.mapeaRegId(codigoAlumno);
        		existePersonal 	= true;
        		nacionalidadNombre 	= catPaisDao.getNombrePais(alumPersonal.getNacionalidad());
        		paisNombre 			= catPaisDao.getNombrePais(alumPersonal.getPaisId());
        		estadoNombre 		= catEstadoDao.getNombreEstado(alumPersonal.getPaisId(), alumPersonal.getEstadoId());
        		ciudadNombre 		= catCiudadDao.getNombreCiudad(alumPersonal.getPaisId(), alumPersonal.getEstadoId(), alumPersonal.getCiudadId());
        		culturalNombre		= catCulturalDao.getNombreCultural(alumPersonal.getCulturalId());
        		regionNombre		= catRegionDao.getNombreRegion(alumPersonal.getCulturalId(), alumPersonal.getRegionId());
				resPaisNombre 		= catPaisDao.getNombrePais(alumPersonal.getResPaisId());
				resEstadoNombre 	= catEstadoDao.getNombreEstado(alumPersonal.getResPaisId(), alumPersonal.getResEstadoId());
				resCiudadNombre 	= catCiudadDao.getNombreCiudad(alumPersonal.getResCiudadId(), alumPersonal.getResEstadoId(), alumPersonal.getResCiudadId());
        	}
        	if (accesoDao.existeReg(codigoAlumno)){
        		acceso = accesoDao.mapeaRegId(codigoAlumno);
        		String tmp = acceso.getIngreso()==null?"":acceso.getIngreso();
        		if(tmp.equals("S")){
        			ingreso = "Registered!";
        		}
        	}
        	if (alumUbicacionDao.existeReg(codigoAlumno)) {
        		existeUbicacion = true;
        	}
        	if (alumAcademicoDao.existeReg(codigoAlumno)) {
        		existeUbicacion = true;
        	}
        	existePlan 			= alumPlanDao.existeCodigoPersonal(codigoAlumno);
        	existeCursos 		= alumnoCursoDao.existeCodigoPersonal(codigoAlumno);
        	if (existeUbicacion || existeAcademico || existePlan || existeCursos) borrarAlumno = false;
        }

        String maxUpdate = logAlumnoDao.maximoId("ALUM_PERSONAL", codigoAlumno);
    	if (!maxUpdate.equals("0")){
    		logAlumno = logAlumnoDao.mapeaRegId(maxUpdate);
    		if (maestrosDao.existeReg(logAlumno.getUsuario())) {
    			usuarioActualizo = maestrosDao.getNombreCorto(logAlumno.getUsuario(), "NOMBRE");
    		}else {
    			usuarioActualizo = alumPersonalDao.getNombreCorto(logAlumno.getUsuario());
    		}
    		//usuarioActualizo = usuariosDao.getNombreUsuarioCorto(logAlumno.getUsuario());
    	}

		Parametros parametros = parametrosDao.mapeaRegId("1");

        List<CatReligion> lisReligiones 		= catReligionDao.getListAll(" ORDER BY 2");
        List<CatPais> lisPaises 				= catPaisDao.getListAllWithPriorityCountry(alumPersonal.getPaisId());
        List<CatEstado> lisEstados 				= catEstadoDao.getLista(alumPersonal.getPaisId(),"ORDER BY 2,3");
        List<CatCiudad> lisCiudades 			= catCiudadDao.getLista(alumPersonal.getPaisId(), alumPersonal.getEstadoId(), "ORDER BY 3");
        List<CatCultural> lisGrupos				= catCulturalDao.getListAll("");
        List<CatRegion> lisRegiones				= catRegionDao.getLista(alumPersonal.getCulturalId(), "");
		List<CatPais> lisResPaises 				= catPaisDao.getListAllWithPriorityCountry(alumPersonal.getResPaisId());
		List<CatEstado> lisResEstados 			= catEstadoDao.getLista(alumPersonal.getResPaisId(), "ORDER BY 2,3");
		List<CatCiudad> lisResCiudades 			= catCiudadDao.getLista(alumPersonal.getResPaisId(), alumPersonal.getResEstadoId(), "ORDER BY 3");

		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("logAlumno", logAlumno);
		modelo.addAttribute("existePersonal", existePersonal);
		modelo.addAttribute("ingreso", ingreso);
		modelo.addAttribute("borrarAlumno", borrarAlumno);
		modelo.addAttribute("nacionalidadNombre", nacionalidadNombre);
		modelo.addAttribute("paisNombre", paisNombre);
		modelo.addAttribute("estadoNombre", estadoNombre);
		modelo.addAttribute("ciudadNombre", ciudadNombre);
		modelo.addAttribute("resPaisNombre", resPaisNombre);
		modelo.addAttribute("resEstadoNombre", resEstadoNombre);
		modelo.addAttribute("resCiudadNombre", resCiudadNombre);
		modelo.addAttribute("culturalNombre", culturalNombre);
		modelo.addAttribute("regionNombre", regionNombre);
		modelo.addAttribute("usuarioActualizo", usuarioActualizo);
		modelo.addAttribute("maxUpdate", maxUpdate);
		modelo.addAttribute("lisReligiones", lisReligiones);
		modelo.addAttribute("lisPaises", lisPaises);
		modelo.addAttribute("lisEstados", lisEstados);
		modelo.addAttribute("lisCiudades", lisCiudades);
		modelo.addAttribute("lisGrupos", lisGrupos);
		modelo.addAttribute("lisRegiones", lisRegiones);
		modelo.addAttribute("lisResPaises", lisResPaises);
		modelo.addAttribute("lisResEstados", lisResEstados);
		modelo.addAttribute("lisResCiudades", lisResCiudades);
		modelo.addAttribute("parametros", parametros);

		return "admision/datos/alumno";
	}

	@RequestMapping("/admision/datos/grabarAlumno")
	public String admisionDatosGrabarAlumno(HttpServletRequest request){

		String codigoAlumno 		= "0";
		String codigoPersonal 		= "0";
		String mensaje				= "-";
		AlumPersonal alumPersonal 	= new AlumPersonal();
		Acceso acceso 				= new Acceso();
		LogAlumno logAlumno 		= new LogAlumno();
		HttpSession sesion			= request.getSession();
        if (sesion!=null){
        	codigoAlumno			= (String)sesion.getAttribute("codigoAlumno");
        	codigoPersonal			= (String)sesion.getAttribute("codigoPersonal");
        	if (alumPersonalDao.existeReg(codigoAlumno)) {
        		alumPersonal 			= alumPersonalDao.mapeaRegId(codigoAlumno);
        	}
    		String cotejado 	= alumPersonal.getCotejado()==null?"N":alumPersonal.getCotejado();
    		if(cotejado.equals("N")){
    			alumPersonal.setCodigoPersonal(codigoAlumno);
    			String curp = request.getParameter("Curp")==null?"-":request.getParameter("Curp").toUpperCase();
    			alumPersonal.setCurp(curp);
    			alumPersonal.setNombre(request.getParameter("Nombre").toUpperCase());
    			alumPersonal.setApellidoPaterno(request.getParameter("ApellidoPaterno").toUpperCase());
    			alumPersonal.setApellidoMaterno(request.getParameter("ApellidoMaterno").toUpperCase());
    			alumPersonal.setNombreLegal(request.getParameter("NombreLegal").toUpperCase());
    			alumPersonal.setFNacimiento(request.getParameter("FNacimiento"));
    			alumPersonal.setSexo(request.getParameter("Sexo"));
    			alumPersonal.setPaisId(request.getParameter("PaisId"));
    			alumPersonal.setEstadoId(request.getParameter("EstadoId"));
    			alumPersonal.setCiudadId(request.getParameter("CiudadId"));
    			alumPersonal.setCulturalId(request.getParameter("CulturalId")==null?"1":request.getParameter("CulturalId"));
    			alumPersonal.setRegionId(request.getParameter("RegionId")==null?"1":request.getParameter("RegionId"));
				alumPersonal.setResPaisId(request.getParameter("ResPaisId"));
				alumPersonal.setResEstadoId(request.getParameter("ResEstadoId"));
				alumPersonal.setResCiudadId(request.getParameter("ResCiudadId"));
    			alumPersonal.setNacionalidad(request.getParameter("Nacionalidad"));
    			alumPersonal.setTelefono(request.getParameter("Telefono"));
    			alumPersonal.setCotejado(request.getParameter("Cotejado"));
				alumPersonal.setSync(request.getParameter("Sync"));
    			//Establecemos la clave
    			String codigoTemp = alumPersonal.getCodigoPersonal();
    			String claveDigest	= aca.util.Encriptar.sha512ConBase64(alumPersonal.getCodigoPersonal());
    			acceso.setCodigoPersonal(codigoTemp);
    			acceso.setClave(claveDigest);
    			acceso.setClaveInicial(alumPersonal.getCodigoPersonal());
    			String ingreso = acceso.getIngreso()==null?"N":acceso.getIngreso();
    			if(!accesoDao.existeReg(codigoTemp)){
    				acceso.setIngreso("N");
    				acceso.setAdministrador("N");
    				acceso.setCotejador("N");
    				acceso.setSupervisor("N");
    				acceso.setPortalAlumno("N");
    				acceso.setPortalMaestro("N");
    				acceso.setModalidad("0");
    				acceso.setUsuario(codigoTemp);
    				acceso.setConvalida("N");
    				acceso.setIdioma("en");
    				acceso.setMenu("1");
    				acceso.setEnLinea("N");
    				accesoDao.insertReg(acceso);
    			}else if(ingreso.equals("N")){
    				accesoDao.updateReg(acceso);
    			}
    			alumPersonal.setEstado(request.getParameter("Estado"));
    			alumPersonal.setFCreado(aca.util.Fecha.getHoy());
    			alumPersonal.setUsAlta(codigoPersonal);
    		}
    		alumPersonal.setEstadoCivil(request.getParameter("EstadoCivil"));
    		alumPersonal.setReligionId(request.getParameter("ReligionId"));
    		alumPersonal.setBautizado(request.getParameter("Bautizado"));
    		alumPersonal.setfBautizado(request.getParameter("FBautizo")==null?"01/01/2000":request.getParameter("FBautizo"));
    		alumPersonal.setEmail(request.getParameter("Email"));
    		alumPersonal.setTelefono(request.getParameter("Telefono"));
    		
    		if (alumPersonalDao.existeReg(alumPersonal.getCodigoPersonal())){
    			if (alumPersonalDao.updateReg(alumPersonal)){
    				if (alumAcademicoDao.existeReg(codigoAlumno)){
    					if (alumPersonal.getReligionId().equals("1")){
    						alumAcademicoDao.updateClasFin(codigoAlumno, "1");
    					}else {
    						alumAcademicoDao.updateClasFin(codigoAlumno, "2");
    					}
    				}
    				logAlumno.setId( logAlumnoDao.maximoReg());
    				logAlumno.setTabla("ALUM_PERSONAL");
    				logAlumno.setOperacion("UPDATE");
    				logAlumno.setUsuario(codigoPersonal);
    				logAlumno.setIp(request.getRemoteAddr());
    				logAlumno.setDatos(alumPersonal.getCodigoPersonal());
    				if (logAlumnoDao.insertReg(logAlumno)){
    					mensaje = "Saved: "+alumPersonal.getCodigoPersonal();
    					if (maestrosDao.existeReg(codigoAlumno))
    						sesion.setAttribute("codigoEmpleado", alumPersonal.getCodigoPersonal());
    					else if (codigoAlumno.substring(0,1).equals("P"))
    						sesion.setAttribute("codigoPadre", alumPersonal.getCodigoPersonal());
    					else if (codigoAlumno.substring(0,1).equals("0") || codigoAlumno.substring(0,1).equals("1") || codigoAlumno.substring(0,1).equals("2"))
    						sesion.setAttribute("codigoAlumno", alumPersonal.getCodigoPersonal());
    				}else{
    					mensaje = "Error saving: "+alumPersonal.getCodigoPersonal();
    				}
    			}else{
    				mensaje = "Error saving: "+alumPersonal.getCodigoPersonal();
    			}
    		}else{
    			mensaje = "ID not found: "+alumPersonal.getCodigoPersonal();
    		}
        }

		return "redirect:/admision/datos/alumno?Mensaje="+mensaje;
	}

	@RequestMapping("/admision/datos/borrarPersonales")
	public String admisionDatosBorrarPersonales(HttpServletRequest request){
		String codigoAlumno 		= request.getParameter("CodigoAlumno")==null?"0":request.getParameter("CodigoAlumno");
		String mensaje				= "-";
		if (alumPersonalDao.existeReg(codigoAlumno)){
			if (alumPersonalDao.deleteReg(codigoAlumno)){
				mensaje = "Deleted: "+codigoAlumno;
				if (accesoDao.existeReg(codigoAlumno)){
					accesoDao.deleteReg(codigoAlumno);
				}
			}else{
				mensaje = "Error deleting: "+codigoAlumno;
			}
		}else{
			mensaje = "Not found: "+codigoAlumno;
		}
		return "redirect:/admision/datos/alumno?Mensaje="+mensaje;
	}

	@RequestMapping("/admision/datos/datos")
	public String admisionDatosDatos(HttpServletRequest request, Model modelo){

		String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		String nombre			= request.getParameter("Nombre")==null?"":request.getParameter("Nombre");
		String paterno			= request.getParameter("Paterno")==null?"":request.getParameter("Paterno");
		String materno			= request.getParameter("Materno")==null?"":request.getParameter("Materno");
		String codigoAlumno		= request.getParameter("CodigoAlumno")==null?"":request.getParameter("CodigoAlumno");
		String codigoPersonal 	= "0";
		String regresar			= "admision/datos/datos";
		String mensaje			= "-";
		Parametros parametros = parametrosDao.mapeaRegId("1");

		String tipo = "PAU";
		if(parametros.getInstitucion().equals("Sonoma")){
			tipo = "SONOMA";
		}
		if(parametros.getInstitucion().equals("Fulton")){
			tipo = "FAC";
		}

		HttpSession sesion		= request.getSession();
		if (sesion!=null){
        	codigoPersonal		= (String)sesion.getAttribute("codigoPersonal");
        }

		List<AlumPersonal> lisAlumnos		= new ArrayList<>();

		if (accion.equals("1")){
			lisAlumnos = alumPersonalDao.BuscaDuplicados(nombre,paterno,materno,50);
		}
		if (accion.equals("3")){
			String nombreUsuario = "-";
			if (alumPersonalDao.existeReg(codigoAlumno)){
				nombreUsuario = alumPersonalDao.getNombre(codigoAlumno,"NOMBRE");
				if (sesion!=null){
		        	sesion.setAttribute("codigoAlumno",codigoAlumno);
		        	mensaje = "<b>Student registered: "+codigoAlumno+"</b></div>";
		        }
			}
		}

		if (accion.equals("4")){
			AlumPersonal alumPersonal = new AlumPersonal();
			alumPersonal.setNombre(request.getParameter("Nombre")==null?" ":request.getParameter("Nombre"));
			alumPersonal.setApellidoPaterno(request.getParameter("ApellidoPaterno")==null?"":request.getParameter("ApellidoPaterno"));
			alumPersonal.setApellidoMaterno(request.getParameter("ApellidoMaterno")==null?"":request.getParameter("ApellidoMaterno"));
			alumPersonal.setFNacimiento("01/01/1980");
			alumPersonal.setSexo("M");
			alumPersonal.setFCreado(aca.util.Fecha.getHoy());
			alumPersonal.setUsAlta(codigoPersonal);

			alumPersonal.setCodigoPersonal(alumPersonalDao.maximoReg(tipo));
			if(alumPersonalDao.insertReg(alumPersonal)){
				sesion.setAttribute("codigoAlumno", alumPersonal.getCodigoPersonal());
				regresar	= "redirect:/admision/datos/alumno";
			}else{
				mensaje = "<span style='font-color:red'>An error occurred while creating the student</span>";
			}
		}
		if (accion.equals("5")){
			AlumPersonal alumPersonal = new AlumPersonal();
			alumPersonal.setNombre(request.getParameter("Nombre"));
			alumPersonal.setApellidoPaterno(request.getParameter("ApellidoPaterno"));
			alumPersonal.setApellidoMaterno(request.getParameter("ApellidoMaterno"));
			alumPersonal.setFNacimiento("01/01/1980");
			alumPersonal.setSexo("M");
			alumPersonal.setFCreado(aca.util.Fecha.getHoy());
			alumPersonal.setUsAlta(codigoPersonal);
			alumPersonal.setCodigoPersonal(request.getParameter("Codigo"));

			if(alumPersonalDao.insertReg(alumPersonal)){
				sesion.setAttribute("codigoAlumno", alumPersonal.getCodigoPersonal());
				regresar	= "redirect:/admision/datos/alumno";
			}else{
				mensaje = "<font color=red>An error occurred while creating the student</font>";
			}
		}

		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("lisAlumnos", lisAlumnos);

		return regresar;
	}

	@ResponseBody
	@RequestMapping("/admision/datos/existeMatricula")
	public String admisionDatosExisteMatricula(HttpServletRequest request){
		String codigoAlumno = request.getParameter("CodigoAlumno")==null?"0":request.getParameter("CodigoAlumno");
		String respuesta = "NoExiste";
		if(alumPersonalDao.existeReg( codigoAlumno)){
			respuesta = "existe";
		}
		return respuesta;
	}

	@ResponseBody
	@RequestMapping("/admision/datos/siguienteMatricula")
	public String admisionDatosSiguienteMatricula(HttpServletRequest request){
		Parametros parametros = parametrosDao.mapeaRegId("1");

		System.out.println(parametros.getInstitucion()+"[]");

		String tipo = "PAU";
		if(parametros.getInstitucion().equals("Sonoma")){
			tipo = "SONOMA";
		}
		if(parametros.getInstitucion().equals("Fulton")){
			tipo = "FAC";
		}

		String respuesta = alumPersonalDao.maximoReg(tipo);

		return respuesta;
	}

	@RequestMapping("/admision/datos/imagen")
	public String admisionDatosImagen(HttpServletRequest request){
		return "admision/datos/imagen";
	}

	@RequestMapping("/admision/datos/imagenEnviada")
	public String admisionDatosImagenEnviada(HttpServletRequest request){
		return "admision/datos/imagenEnviada";
	}

	@RequestMapping("/admision/datos/semejantes")
	public String admisionDatosSemejantes(HttpServletRequest request, Model modelo ){
		String codigoAlumno 	= "0";
		String alumnoNombre		= "-";
		String nombre			= request.getParameter("Nomb")==null?"0":request.getParameter("Nomb");
		String paterno 			= request.getParameter("Pat")==null?"0":request.getParameter("Pat");
	 	String materno			= request.getParameter("Mat")==null?"0":request.getParameter("Mat");

		HttpSession sesion		= request.getSession();
        if (sesion!=null){
        	codigoAlumno		= (String)sesion.getAttribute("codigoAlumno");
        	alumnoNombre		= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
        }
        List<AlumPersonal> lisAlumnos = alumPersonalDao.getListIguales( nombre, paterno, materno);

		modelo.addAttribute("alumnoNombre", alumnoNombre);
		modelo.addAttribute("lisAlumnos", lisAlumnos);

		return "admision/datos/semejantes";
	}
}