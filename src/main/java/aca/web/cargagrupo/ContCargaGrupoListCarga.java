package aca.web.cargagrupo;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.carga.spring.Carga;
import aca.carga.spring.CargaBloque;
import aca.carga.spring.CargaBloqueDao;
import aca.carga.spring.CargaDao;
import aca.carga.spring.CargaGrupoCursoDao;
import aca.carga.spring.CargaGrupoDao;
import aca.carga.spring.CargaGrupoHora;
import aca.carga.spring.CargaGrupoHoraDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatEdificio;
import aca.catalogo.spring.CatEdificioDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatHorarioDao;
import aca.catalogo.spring.CatHorarioFacultad;
import aca.catalogo.spring.CatHorarioFacultadDao;
import aca.catalogo.spring.CatModalidad;
import aca.catalogo.spring.CatModalidadDao;
import aca.catalogo.spring.CatSalon;
import aca.catalogo.spring.CatSalonDao;
import aca.plan.spring.MapaCurso;
import aca.plan.spring.MapaCursoDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.CargaAcademica;
import aca.vista.spring.CargaHorario;
import aca.vista.spring.CargaHorarioDao;
import aca.vista.spring.MaestrosDao;


@Controller
public class ContCargaGrupoListCarga {	
	
	@Autowired
	CargaDao cargaDao;
	
	@Autowired
	MaestrosDao maestrosDao;
	
	@Autowired
	CatFacultadDao catFacultadDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	CargaGrupoDao cargaGrupoDao;
	
	@Autowired
	aca.vista.spring.CargaAcademicaDao cargaAcademicaDao;
	
	@Autowired
	CatModalidadDao catModalidadDao;
	
	@Autowired
	CargaGrupoCursoDao cargaGrupoCursoDao;
	
	@Autowired
	MapaPlanDao mapaPlanDao;
	
	@Autowired
	CatHorarioDao catHorarioDao;
	
	@Autowired
	CargaBloqueDao cargaBloqueDao;
	
	@Autowired
	CatHorarioFacultadDao catHorarioFacultadDao; 
	
	@Autowired
	CargaGrupoHoraDao cargaGrupoHoraDao;
	
	@Autowired
	CatEdificioDao catEdificioDao;
	
	@Autowired
	CatSalonDao catSalonDao;
	
	@Autowired
	CargaHorarioDao cargaHorarioDao;
	
	@Autowired
	MapaCursoDao mapaCursoDao;
	
	
	@RequestMapping("/carga_grupo/lista_carga/elegir")
	public String cargaGrupoListaCargaElegir(HttpServletRequest request, Model modelo){
		
		String cargaId 		= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		System.out.println(cargaId);
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();		
		
		List<Carga> lisCargas 			= cargaDao.getListAllActivas(" ORDER BY ORDEN DESC");
		if (sesion != null){
			if (cargaId.equals("0")) { 
				if (sesion.getAttribute("cargaId")!=null) {					
					cargaId = (String)sesion.getAttribute("cargaId");				
				}else if (lisCargas.size()>0){
					sesion.setAttribute("cargaId", lisCargas.get(0).getCargaId());					
				}	
			}else{
				sesion.setAttribute("cargaId", cargaId);				
			}				
		}	
		
		List<CatCarrera> lisCarreras	= catCarreraDao.getListaEnCarga(cargaId, " ORDER BY FACULTAD_ID, NIVEL_ID, NOMBRE_CARRERA");
		List<MapaPlan> lisPlanes	 	= mapaPlanDao.lisPlanesEnCarga(cargaId);
		
		HashMap<String, CatFacultad> mapaFacultades 	= catFacultadDao.getMapFacultad("");
		HashMap<String,String> mapaMaestros 			= maestrosDao.mapMaestroNombre("NOMBRE");
		HashMap<String,String> mapaMaterias 			= cargaGrupoDao.mapaMateriasEnCarga(cargaId);
		
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisCarreras", lisCarreras);
		modelo.addAttribute("lisPlanes", lisPlanes);
		
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaMaestros", mapaMaestros);
		modelo.addAttribute("mapaMaterias", mapaMaterias);
		
		return "carga_grupo/lista_carga/elegir";
	}
	
	@RequestMapping("/carga_grupo/lista_carga/carga")
	public String cargaGrupoListaCargaCarga(HttpServletRequest request, Model modelo){
		
		String cargaId 			= "0";
		String carreraId 		= request.getParameter("CarreraId")==null?"0":request.getParameter("CarreraId");
		String nombreCarga		= "-";
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();	
		
		if (sesion != null){
			cargaId 		= sesion.getAttribute("cargaId").toString();
			nombreCarga 	= cargaDao.getNombreCarga(cargaId); 
		}		
		
		List<CargaAcademica> lisMaterias				= cargaAcademicaDao.getListaCargaCarrera(cargaId, carreraId, " ORDER BY  PLAN_ID, CICLO, NOMBRE_CURSO");
		List<CargaHorario> lisHorarios					= cargaHorarioDao.lisHorariosPorCarga(cargaId, " ORDER BY DIA, PERIODO");
		HashMap<String,String> mapaMaestros 			= maestrosDao.mapMaestroNombre("NOMBRE");
		HashMap<String,CatModalidad> mapaModalidades 	= catModalidadDao.getMapAll("");
		HashMap<String,CatSalon> mapaSalones 			= catSalonDao.getMapAll("");
		HashMap<String,MapaCurso> mapaCursos 			= mapaCursoDao.mapaCursosEnCarrera(carreraId);
		
		modelo.addAttribute("nombreCarga", nombreCarga);
		modelo.addAttribute("lisMaterias", lisMaterias);
		modelo.addAttribute("lisHorarios", lisHorarios);
		modelo.addAttribute("mapaMaestros", mapaMaestros);
		modelo.addAttribute("mapaModalidades", mapaModalidades);
		modelo.addAttribute("mapaSalones", mapaSalones);
		modelo.addAttribute("mapaCursos", mapaCursos);
		
		return "carga_grupo/lista_carga/carga";
	}
	
	@RequestMapping("/carga_grupo/lista_carga/horarioSalon")
	public String cargaGrupoGrupoHorarioSalon(HttpServletRequest request, Model modelo){
		//enviarConEnoc(request,"Error-aca.ContCargaGrupoGrupo|cargaGrupoGrupoHorarioSalon");
		String cargaId 			= "0";
		String cargaNombre 		= "-";
		String codigoPersonal	= "0";
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
        if (sesion!=null){        	
        	cargaId 			= (String)sesion.getAttribute("cargaId");
        	codigoPersonal 		= (String)sesion.getAttribute("codigoPersonal");
        	cargaNombre 		= cargaDao.getNombreCarga(cargaId);
        }      
		
		String planId			= request.getParameter("PlanId")==null ? "0" : request.getParameter("PlanId");
		String bloqueId 		= request.getParameter("BloqueId")==null?"0":request.getParameter("BloqueId");
		String edificioId 		= request.getParameter("EdificioId")==null ? "0" : request.getParameter("EdificioId");
		String salonId 			= request.getParameter("SalonId")==null ? "0" : request.getParameter("SalonId");
		String carreraId 		= mapaPlanDao.getCarreraId(planId);
		String facultadId		= catCarreraDao.getFacultadId(carreraId);
		String horarioId 		= catHorarioDao.getHorarioFacultad(facultadId, "A");
		
		List<CargaBloque> lisBloques 				=  cargaBloqueDao.lisBloquesEnHorario(cargaId, " ORDER BY BLOQUE_ID");
		if ( bloqueId.equals("0") && lisBloques.size() >= 1 ) bloqueId = lisBloques.get(0).getBloqueId();
		List<CatEdificio> lisEdificios 				= catEdificioDao.lisEnHorarioyPlan(cargaId,planId,codigoPersonal," ORDER BY 1");
		if (lisEdificios.size() >= 1 && edificioId.equals("0")) edificioId = lisEdificios.get(0).getEdificioId();		
		List<CatSalon> lisSalones 					= catSalonDao.lisEnHorario(edificioId, cargaId, " ORDER BY 1");
		boolean existeSalon = false;
		for (CatSalon salon : lisSalones){
			if (salon.getSalonId().equals(salonId)) {
				existeSalon = true;
			}
		}
		if (lisSalones.size() >= 1 && (salonId.equals("0") || existeSalon==false)) salonId = lisSalones.get(0).getSalonId();
		List<String> lisTurnos 						= catHorarioFacultadDao.getTurno(horarioId, " ORDER BY TURNO");
		List<CatHorarioFacultad> lisHorarios 		= catHorarioFacultadDao.getLista(horarioId," ORDER BY TURNO, PERIODO");
		List<CargaGrupoHora> lisClases 				= cargaGrupoHoraDao.lisHorariosPorCargaBloqueySalon(cargaId, bloqueId, salonId, " ORDER BY DIA");
		HashMap<String,String> mapaOcupados			= cargaGrupoHoraDao.mapaSalonesOcupados(cargaId, bloqueId);
		HashMap<String,CargaAcademica> mapaMaterias = cargaAcademicaDao.mapaPorCargaBloqueyOrigen(cargaId, bloqueId, "'O'");
		HashMap<String,String> mapaMateriasEnPlan	= cargaAcademicaDao.mapaMateriasEnPlan(cargaId, bloqueId, planId);
		HashMap<String,String> mapaEdificiosHoras	= cargaGrupoHoraDao.mapaHorasEnEdificio(cargaId, bloqueId, planId);
		HashMap<String,String> mapaSalonesHoras		= cargaGrupoHoraDao.mapaHorasEnSalon(cargaId, bloqueId, planId);
		
		modelo.addAttribute("horarioId", horarioId);
		modelo.addAttribute("cargaNombre", cargaNombre);
		modelo.addAttribute("carreraId", carreraId);
		modelo.addAttribute("bloqueId", bloqueId);
		modelo.addAttribute("edificioId", edificioId);
		modelo.addAttribute("salonId", salonId);
		
		modelo.addAttribute("lisBloques", lisBloques);
		modelo.addAttribute("lisEdificios", lisEdificios);
		modelo.addAttribute("lisSalones", lisSalones);
		modelo.addAttribute("lisTurnos", lisTurnos);
		modelo.addAttribute("lisHorarios", lisHorarios);
		modelo.addAttribute("lisClases", lisClases);
		modelo.addAttribute("mapaMaterias", mapaMaterias);
		modelo.addAttribute("mapaMateriasEnPlan", mapaMateriasEnPlan);
		modelo.addAttribute("mapaOcupados", mapaOcupados);
		modelo.addAttribute("mapaEdificiosHoras", mapaEdificiosHoras);
		modelo.addAttribute("mapaSalonesHoras", mapaSalonesHoras);
			
		return "carga_grupo/lista_carga/horarioSalon";
	}
	
}