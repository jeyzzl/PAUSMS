package aca.web.inscritos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumAcademico;
import aca.alumno.spring.AlumAcademicoDao;
import aca.alumno.spring.AlumEstadoDao;
import aca.alumno.spring.AlumImagen;
import aca.alumno.spring.AlumImagenDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaAlumno;
import aca.carga.spring.CargaAlumnoDao;
import aca.carga.spring.CargaDao;
import aca.carga.spring.CargaGrupoDao;
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
import aca.financiero.spring.FinSaldo;
import aca.kardex.spring.KrdxCursoActDao;
import aca.vista.spring.EstadisticaDao;
import aca.vista.spring.Inscritos;
import aca.vista.spring.InscritosDao;

@Controller
public class ContInscritosInscritosFac {
	
	@Autowired	
	private CatModalidadDao catModalidadDao;

	@Autowired	
	private CatFacultadDao catFacultadDao;

	@Autowired	
	private CatCarreraDao catCarreraDao;

	@Autowired	
	private CatReligionDao catReligionDao;
	
	@Autowired	
	private CatPaisDao catPaisDao;
	
	@Autowired	
	private CatEstadoDao catEstadoDao;

	@Autowired	
	private CatCiudadDao catCiudadDao;

	@Autowired	
	private CargaDao cargaDao;

	@Autowired	
	private AccesoDao accesoDao;
	
	@Autowired	
	private CatTipoAlumnoDao catTipoAlumnoDao;
	
	@Autowired	
	private AlumAcademicoDao alumAcademicoDao;
	
	@Autowired	
	private AlumPlanDao alumPlanDao;
	
	@Autowired	
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired	
	private AlumEstadoDao alumEstadoDao;
	
	@Autowired	
	private InscritosDao inscritosDao;
	
	@Autowired	
	private EstadisticaDao estadisticaDao;
	
	@Autowired	
	private KrdxCursoActDao krdxCursoActDao;
	
	@Autowired	
	private CargaAlumnoDao cargaAlumnoDao;
	
	@Autowired	
	private CargaGrupoDao cargaGrupoDao;
	
	@Autowired	
	private AlumImagenDao alumImagenDao;
	
	@RequestMapping("/inscritos/inscritos_fac/inscritos")
	public String inscritosInscritosFacInscritos(HttpServletRequest request, Model modelo){
		long inicio = System.currentTimeMillis();
		String codigo		= "";	
		String fechaIni 	= request.getParameter("FechaIni")==null?"0":request.getParameter("FechaIni");
		String fechaFin		= request.getParameter("FechaFin")==null?"0":request.getParameter("FechaFin");	
		String accion 		= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		String conSaldo 	= request.getParameter("ConSaldo")==null?"N":request.getParameter("ConSaldo");
		String cargas 		= "";
		String modalidades 	= "";
		String fInscripcion = request.getParameter("fecha")==null?aca.util.Fecha.getHoy():request.getParameter("fecha");
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigo 	= (String) sesion.getAttribute("codigoPersonal");
        	if (fechaIni.equals("0")) fechaIni = (String) sesion.getAttribute("fechaIni");
        	if (fechaFin.equals("0")) fechaFin = (String) sesion.getAttribute("fechaFin");  
        	
        	cargas		= (String)sesion.getAttribute("cargas") == null ? cargaDao.getCargasActivas(aca.util.Fecha.getHoy()) : sesion.getAttribute("cargas").toString();
        	modalidades	= (String)sesion.getAttribute("modalidades") == null ? "'1'" : sesion.getAttribute("modalidades").toString();
        }
        if (accion.equals("1")){		
        	sesion.setAttribute("fechaIni", fechaIni);
        	sesion.setAttribute("fechaFin", fechaFin);
        } 
        
        boolean tieneAcceso = false;	
        List<CatModalidad> lisModalidad = catModalidadDao.getListAll( " ORDER BY MODALIDAD_ID");
        List<Carga> lisCarga 			= cargaDao.getListPorFecha(fInscripcion, "ORDER BY CARGA_ID");
    	Acceso acceso = accesoDao.mapeaRegId(codigo);
    	
    	if(accesoDao.existeReg(codigo) == true){
    		tieneAcceso = true;
    	}
    		
		String lisModo[] 		= modalidades.replace("'", "").split(",");
		HashMap<String, CatFacultad> mapFacultad 	= catFacultadDao.getMapFacultad("");
		HashMap<String, CatCarrera> mapCarrera 		= catCarreraDao.getMapAll("");					
		HashMap<String, CatReligion> mapReligion 	= catReligionDao.getMapAll("");	
		HashMap<String, CatModalidad> mapModalidad 	= catModalidadDao.getMapAll("");
		HashMap<String, CatPais> mapPais 			= catPaisDao.getMapAll();
		HashMap<String, CatEstado> mapEstado 		= catEstadoDao.getMapPaisEstado("");
		HashMap<String, CatTipoAlumno> mapTipo 		= catTipoAlumnoDao.getMapAll("");
		HashMap<String, AlumAcademico> mapAcademico = alumAcademicoDao.getMapAcademico();
		HashMap<String, AlumPlan> mapAlumPlan 		= alumPlanDao.getMapInscritos("");
		HashMap<String, String> mapEdad 			= alumPersonalDao.mapEdadInscritos();
		HashMap<String, AlumPersonal> mapAlumno		= alumPersonalDao.getMapInscritos();
		HashMap<String, String> mapCreditos			= krdxCursoActDao.mapaCreditosPorCargayModalidad(cargas);
		HashMap<String, String> mapGradoyCiclo		= alumEstadoDao.mapaGradoyCiclo(cargas, "I");
		if(modalidades.equals("")) {
			modalidades	= "' '";
		}		
		if(cargas.equals("")) {
			cargas	= "' '";
		}
		
		HashMap<String, String> mapaFormal			= inscritosDao.mapaFormal();
		HashMap<String, String> mapaCimum			= inscritosDao.mapaCimum();
		HashMap<String, String> mapaIdiomas			= inscritosDao.mapaIdiomas();
		
		List<Inscritos> lisInsc	= inscritosDao.getListAllUM("WHERE CARGA_ID IN("+cargas+") AND MODALIDAD_ID IN ("+modalidades+") "+
				" AND F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY')"+
				" ORDER BY ENOC.FACULTAD(CARRERA_ID), CARRERA_ID, MODALIDAD_ID, APELLIDO_PATERNO,APELLIDO_MATERNO,NOMBRE");	
		
		// Consulta el saldo del estudiante
		HashMap<String,FinSaldo> mapaSaldos = new HashMap<String,FinSaldo>();
		FinSaldo finSaldo 	= new FinSaldo();
		//System.out.println( "Paso 6.0:"+(System.currentTimeMillis()-inicio) );
		if(conSaldo.equals("S")) {
			for(Inscritos objeto : lisInsc) {
				try {		
					RestTemplate restTemplate = new RestTemplate();
					finSaldo = restTemplate.getForObject("http://172.16.251.110:8080/infor/reportes/api/fe/saldo/"+objeto.getCodigoPersonal(), FinSaldo.class);
					mapaSaldos.put(objeto.getCodigoPersonal(), finSaldo);
				}catch(Exception ex) {
					System.out.println("Error en saldo:"+objeto.getCodigoPersonal());
				}
			}
		}
		
		//System.out.println( "Paso 6.1:"+(System.currentTimeMillis()-inicio) );
		HashMap<String, CargaAlumno> mapaCargasAlumno = cargaAlumnoDao.mapaCargasAlumno(cargas);
		
		HashMap<String, String> mapaUsoImagen = alumImagenDao.mapaUsoImagen(" WHERE CODIGO_PERSONAL IN (SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL FROM ENOC.INSCRITOS WHERE CARGA_ID IN("+cargas+") AND MODALIDAD_ID IN ("+modalidades+")"
				+ " AND F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY'))");
		
		modelo.addAttribute("codigo", codigo);
		modelo.addAttribute("fechaIni", fechaIni);
		modelo.addAttribute("fechaFin", fechaFin);		
		modelo.addAttribute("accion", accion);
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("tieneAcceso", tieneAcceso);
		modelo.addAttribute("lisModalidad", lisModalidad);
		modelo.addAttribute("lisCarga", lisCarga);		
		modelo.addAttribute("mapFacultad", mapFacultad);
		modelo.addAttribute("mapCarrera", mapCarrera);
		modelo.addAttribute("mapReligion", mapReligion);
		modelo.addAttribute("mapModalidad", mapModalidad);
		modelo.addAttribute("mapPais", mapPais);
		modelo.addAttribute("mapEstado", mapEstado);
		modelo.addAttribute("mapTipo", mapTipo);
		modelo.addAttribute("mapAcademico", mapAcademico);
		modelo.addAttribute("mapAlumPlan", mapAlumPlan);
		modelo.addAttribute("mapEdad", mapEdad);
		modelo.addAttribute("mapAlumno", mapAlumno);
		modelo.addAttribute("mapCreditos", mapCreditos);
		modelo.addAttribute("mapGradoyCiclo", mapGradoyCiclo);
		modelo.addAttribute("lisModo", lisModo);
		modelo.addAttribute("cargas", cargas);
		modelo.addAttribute("modalidades", modalidades);
		modelo.addAttribute("fInscripcion", fInscripcion);
		modelo.addAttribute("lisInsc", lisInsc);		
		modelo.addAttribute("mapaFormal", mapaFormal);
		modelo.addAttribute("mapaCimum", mapaCimum);
		modelo.addAttribute("mapaIdiomas", mapaIdiomas);
		modelo.addAttribute("mapaSaldos", mapaSaldos);
		modelo.addAttribute("mapaCargasAlumno", mapaCargasAlumno);
		modelo.addAttribute("conSaldo", conSaldo);
		modelo.addAttribute("mapaUsoImagen", mapaUsoImagen);

		return "inscritos/inscritos_fac/inscritos";
	}
	
	@RequestMapping("/inscritos/inscritos_fac/cargasActivas")
	public String inscritosInscritosFacCargasActivas(HttpServletRequest request, Model modelo){
		
		String codigoPersonal	= "";
		String fInscripcion		= request.getParameter("Fecha")==null?aca.util.Fecha.getHoy():request.getParameter("Fecha");
		String cargas			= "";
		String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoPersonal 	= (String) sesion.getAttribute("codigoPersonal");
        	cargas			= (String) sesion.getAttribute("cargas") == null ? cargaDao.getCargasActivas(aca.util.Fecha.getHoy()) : sesion.getAttribute("cargas").toString();
        }
		
		List<Carga> lisCargas	= cargaDao.getListPorFecha(fInscripcion, "ORDER BY CARGA_ID");
		List<Carga> lisActivas	= cargaDao.getListCargasActivas(" ORDER BY CARGA_ID");
		
		
		modelo.addAttribute("codigoPersonal", codigoPersonal);
		modelo.addAttribute("fInscripcion", fInscripcion);
		modelo.addAttribute("cargas", cargas);
		modelo.addAttribute("accion", accion);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisActivas", lisActivas);
		
		return "inscritos/inscritos_fac/cargasActivas";
	}

	@RequestMapping("/inscritos/inscritos_fac/modalidades")
	public String inscritosInscritosFacModalidades(HttpServletRequest request, Model modelo){
		
		String codigoPersonal	= "";
		String cargas			= "";
		String modalidades		= "";
		String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal 	= (String) sesion.getAttribute("codigoPersonal");
			cargas			= (String) sesion.getAttribute("cargas") == null ? cargaDao.getCargasActivas(aca.util.Fecha.getHoy()) : sesion.getAttribute("cargas").toString();
			modalidades		= (String)sesion.getAttribute("modalidades") == null ? "" : sesion.getAttribute("modalidades").toString();
		}
		
		HashMap<String, String> mapaModalidadesEnCargas 	= estadisticaDao.mapaModalidadesEnCargas(cargas);
		List<CatModalidad> lisModalidad	= catModalidadDao.getListAll("ORDER BY MODALIDAD_ID");
		
		modelo.addAttribute("codigoPersonal", codigoPersonal);
		modelo.addAttribute("cargas", cargas);
		modelo.addAttribute("modalidades", modalidades);
		modelo.addAttribute("accion", accion);
		modelo.addAttribute("lisModalidad", lisModalidad);
		modelo.addAttribute("mapaModalidadesEnCargas", mapaModalidadesEnCargas);
		
		return "inscritos/inscritos_fac/modalidades";
	}

	@RequestMapping("/inscritos/inscritos_fac/tarjeta")
	public String inscritosInscritosFacTarjeta(HttpServletRequest request, Model modelo){
		
		String codigoPersonal   = "";
		String facultad			= request.getParameter("facultad");
		String cargas 			= request.getParameter("cargas");
		String modalidades = request.getParameter("modalidades");
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal 	= (String) sesion.getAttribute("codigoPersonal");
		}
		
		String nombreFacultad = catFacultadDao.getNombreFacultad(facultad);
		
		HashMap<String,String> mapaCarrerasFacultad = catCarreraDao.mapaCarrerasFacultad(facultad);
		HashMap<String,CatReligion> mapaReligion 	= catReligionDao.getMapAll("");
		HashMap<String, String> mapaEdades          = inscritosDao.mapaEdades();
		HashMap<String,CatPais> mapaPais 			= catPaisDao.getMapAll();
		HashMap<String,String> mapaEstados 			= catEstadoDao.getMapEstado();
		HashMap<String,CatCiudad> mapaCiudad 		= catCiudadDao.getMapAll("");
		
		HashMap<String, AlumPersonal> mapInscritosEnCargas = alumPersonalDao.mapInscritosEnCargas(cargas);
		
		Acceso acceso = accesoDao.mapeaRegId(codigoPersonal);
				
		List<Inscritos> lisInsc	= inscritosDao.getListAllUM(" WHERE ENOC.FACULTAD(CARRERA_ID) = '"+facultad+"' AND CARGA_ID IN("+cargas+") AND MODALIDAD_ID IN ("+modalidades+")  ORDER BY ENOC.FACULTAD(CARRERA_ID), CARRERA_ID, APELLIDO_PATERNO,APELLIDO_MATERNO,NOMBRE");
		
		modelo.addAttribute("codigoPersonal", codigoPersonal);
		modelo.addAttribute("facultad", facultad);
		modelo.addAttribute("cargas", cargas);
		modelo.addAttribute("modalidades", modalidades);
		modelo.addAttribute("nombreFacultad", nombreFacultad);
		modelo.addAttribute("mapaCarrerasFacultad", mapaCarrerasFacultad);
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("lisInsc", lisInsc);
		modelo.addAttribute("mapaReligion", mapaReligion);
		modelo.addAttribute("mapaEdades", mapaEdades);
		modelo.addAttribute("mapaPais", mapaPais);
		modelo.addAttribute("mapaEstados", mapaEstados);
		modelo.addAttribute("mapaCiudad", mapaCiudad);
		modelo.addAttribute("mapInscritosEnCargas", mapInscritosEnCargas);
		
		return "inscritos/inscritos_fac/tarjeta";
	}

}
