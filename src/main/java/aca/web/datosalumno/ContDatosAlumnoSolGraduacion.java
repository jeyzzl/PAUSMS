package aca.web.datosalumno;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumGradua;
import aca.alumno.spring.AlumGraduaDao;
import aca.alumno.spring.AlumGraduaMat;
import aca.alumno.spring.AlumGraduaMatDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatTipoCurso;
import aca.catalogo.spring.CatTipoCursoDao;
import aca.kardex.spring.KrdxCursoActDao;
import aca.plan.spring.MapaCredito;
import aca.plan.spring.MapaCreditoDao;
import aca.plan.spring.MapaCurso;
import aca.plan.spring.MapaCursoDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.AlumnoCursoDao;

@Controller
public class ContDatosAlumnoSolGraduacion {		
	
	@Autowired
	ServletContext context;
	
	@Autowired
	MapaPlanDao mapaPlanDao;
	
	@Autowired
	AlumPlanDao alumPlanDao;
	
	@Autowired
	AlumnoCursoDao alumnoCursoDao;
	
	@Autowired
	KrdxCursoActDao krdxCursoActDao;
	
	@Autowired
	MapaCursoDao mapaCursoDao;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;
	
	@Autowired
	MapaCreditoDao mapaCreditoDao;
	
	@Autowired
	AlumGraduaDao alumGraduaDao;
	
	@Autowired
	AlumGraduaMatDao alumGraduaMatDao;
	
	@Autowired
	CatTipoCursoDao catTipoCursoDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	AlumPlanDao   alumnPlanDao;
	
	@Autowired
	CatFacultadDao   catFacultadDao;		
	
	@Autowired
	KrdxCursoActDao KrdxCursoActDao;	
	
	
	@RequestMapping("/datos_alumno/solgraduacion/formato")
	public String datosAlumnoSolGraduacionFormato(HttpServletRequest request, Model modelo){
		String codigoAlumno = "";
		String institucion  = "";
		String facultad     = "";
		String carrera		= "";
		String planId 			= (String)request.getParameter("plan")==null?alumnPlanDao.getPlanActual(codigoAlumno):(String)request.getParameter("plan");
		int numMatIns 			= KrdxCursoActDao.numMatInsc(codigoAlumno, planId);		

		HttpSession sesion 		= ((HttpServletRequest)request).getSession();		
		if (sesion!=null){
			codigoAlumno 		= (String)sesion.getAttribute("codigoAlumno");
			institucion 		= (String)sesion.getAttribute("institucion");
		}
		
		AlumPersonal alumPersonal	= alumPersonalDao.mapeaRegId(codigoAlumno);
		List<MapaCurso> lisCursos 	= mapaCursoDao.getListCursosPendientessinRemedial(codigoAlumno, planId, "ORDER BY CICLO, TIPOCURSO_ID, NOMBRE_CURSO");
		AlumPlan Alumno 			= alumPlanDao.mapeaRegId(codigoAlumno);   
		AlumGradua Gradua 			= new AlumGradua();
		
		Gradua.setCodigoPersonal(codigoAlumno);
		Gradua.setPlanId(planId);
		if (alumGraduaDao.existeReg(codigoAlumno, planId)){
			Gradua = alumGraduaDao.mapeaRegId( codigoAlumno, planId);
		}				
		String carreraId		= alumPlanDao.getCarreraId(codigoAlumno);

		facultad = catFacultadDao.getNombreFacultad(catCarreraDao.getFacultadId(carreraId));
		carrera = mapaPlanDao.getCarrera(codigoAlumno,planId);
		
		HashMap<String,String> mapaAcreditados 				= alumnoCursoDao.mapaCursosAprobados(codigoAlumno);
		HashMap<String,AlumGraduaMat> mapaGraduaMaterias 	= alumGraduaMatDao.mapaAlumnoMaterias(codigoAlumno);
		HashMap<String,CatTipoCurso> mapaTipos 				= catTipoCursoDao.getMapAll("");

		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("carreraId", carreraId);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("numMatIns", numMatIns);
		modelo.addAttribute("lisCursos", lisCursos);
		modelo.addAttribute("Gradua", Gradua);
		modelo.addAttribute("Alumno", Alumno);
		modelo.addAttribute("facultad", facultad);
		modelo.addAttribute("carrera", carrera);
		modelo.addAttribute("mapaAcreditados", mapaAcreditados);
		modelo.addAttribute("mapaGraduaMaterias", mapaGraduaMaterias);
		modelo.addAttribute("mapaTipos", mapaTipos);
		
		return "datos_alumno/solgraduacion/formato";
	}
	
	@RequestMapping("/datos_alumno/solgraduacion/solicitud")
	public String datosAlumnoSolGraduacionSolicitud(HttpServletRequest request, Model modelo){
		String codigoAlumno 		= "";
		String institucion 			= "";
		//long inicio = System.currentTimeMillis();
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();		
		if (sesion!=null){
			codigoAlumno 		= (String) sesion.getAttribute("codigoAlumno");
			institucion 		= (String)sesion.getAttribute("institucion");
		}
		
		int numAccion 				= request.getParameter("Accion")==null?0:Integer.parseInt(request.getParameter("Accion"));
		String planId 				= (String)request.getParameter("plan")==null?alumPlanDao.getPlanActual(codigoAlumno):(String)request.getParameter("plan");
		String carreraId 			= mapaPlanDao.getCarreraId(planId);		
		
		float numMatAc				= alumnoCursoDao.creditosAprobados(codigoAlumno,planId);
		int numMatIns				= krdxCursoActDao.numMatInsc(codigoAlumno, planId);
		int numMatPen				= 0;
		float numCreditos			= 0;
		int ciclo					= 0;
		float optativo				= 0;
		float credOptaAC			= 0;
		float credOptaFaltan		= 0;	
		boolean existeSol 			= false;
		
		List<String> lisPlanes		= alumPlanDao.getPlanesAlumno(codigoAlumno);		
		List<MapaCurso> lisCursos 	= mapaCursoDao.getListCursosPendientessinRemedial(codigoAlumno, planId, "ORDER BY CICLO, TIPOCURSO_ID, NOMBRE_CURSO");
		AlumPersonal alumPersonal = new AlumPersonal();
		
		if(alumPersonalDao.existeReg(codigoAlumno)) {
			alumPersonal = alumPersonalDao.mapeaRegId(codigoAlumno);	
		}
		
		// Cuenta la cantidad de creditos que le faltan al alumno para terminar su plan de estudios
		int j = 0;
		
		MapaCredito mapaCredito 		= new MapaCredito();
		AlumGradua alumGradua 			= new AlumGradua();
		AlumGraduaMat alumGraduaMat 	= new AlumGraduaMat();
		
		if (alumGraduaDao.existeReg(codigoAlumno, planId)){
			alumGradua = alumGraduaDao.mapeaRegId(codigoAlumno,planId);
			existeSol = true;
		}	
		
		while ( j<lisCursos.size()){
			MapaCurso curso = lisCursos.get(j);
			if ( ciclo != Integer.parseInt( curso.getCiclo()) ){
				ciclo = Integer.parseInt( curso.getCiclo());
				mapaCredito.setPlanId(planId);
				mapaCredito.setCiclo(curso.getCiclo());
				if ( mapaCreditoDao.existeReg( planId, curso.getCiclo()) ){
					mapaCredito = mapaCreditoDao.mapeaRegId(planId,curso.getCiclo());
					optativo 	= Float.parseFloat(mapaCredito.getOptativos().trim());
					credOptaAC 	= alumnoCursoDao.numCredOptaCiclo(codigoAlumno,planId,curso.getCiclo());
					credOptaFaltan = optativo-credOptaAC;
				}	
			}
			if ( curso.getTipoCursoId().equals("1")||curso.getTipoCursoId().equals("3")||curso.getTipoCursoId().equals("2")||
				 curso.getTipoCursoId().equals("4")||curso.getTipoCursoId().equals("5")||curso.getTipoCursoId().equals("8") )
			{
				numCreditos	 += Float.parseFloat(curso.getCreditos());
				numMatPen++;
				j++;
			}else if (curso.getTipoCursoId().equals("7")){
				credOptaFaltan = credOptaFaltan - Float.parseFloat(curso.getCreditos());
				if ( !alumnoCursoDao.esMateriaAc( codigoAlumno, curso.getCursoId()) && credOptaFaltan >= 0){
					numCreditos += Float.parseFloat(curso.getCreditos());
					numMatPen++;
					j++;
				}else{
					lisCursos.remove(j);
				}			
			}
		}	
		
		ciclo		= 0;	credOptaFaltan	= 0;
		optativo	= 0;	credOptaAC		= 0;	
		
		switch (numAccion){
			case 1: {// Grabar			
				String evento 	= request.getParameter("Evento")==null?"R":request.getParameter("Evento");
				String avance 	= request.getParameter("Avance")==null?"P":request.getParameter("Avance");
				String prog		= ""; 
				numMatPen			= request.getParameter("NumMat")==null?0:Integer.parseInt(request.getParameter("NumMat")); 
				
				alumGradua.setCodigoPersonal(codigoAlumno);
				alumGradua.setPlanId(planId);
				alumGradua.setFecha(aca.util.Fecha.getHoy());
				alumGradua.setFechaGraduacion(request.getParameter("FechaGradua"));
				alumGradua.setEvento(evento);
				alumGradua.setAvance(avance);
				alumGradua.setMatAc(String.valueOf(numMatAc));
				alumGradua.setMatIns(String.valueOf(numMatIns));
				alumGradua.setMatPen(String.valueOf(numMatPen));
				alumGradua.setDiploma(request.getParameter("Diploma"));
				
				if((!alumGraduaDao.existeReg( codigoAlumno, planId) && alumGraduaDao.insertReg(alumGradua)==true) || alumGraduaDao.updateReg( alumGradua)){				
					for(int i=0; i<numMatPen; i++){
						MapaCurso curso = lisCursos.get(i);
						alumGraduaMat.setCodigoPersonal(codigoAlumno);
						alumGraduaMat.setCursoId(curso.getCursoId());
						alumGraduaMat.setProgramada(request.getParameter(curso.getCursoId()));
						alumGraduaMat.setComentario("-");
						if (alumGraduaMatDao.existeReg( codigoAlumno, curso.getCursoId())){
							alumGraduaMatDao.updateReg(alumGraduaMat);
						}else{
							alumGraduaMatDao.insertReg( alumGraduaMat);
						}
					}
				}				
				break;
			}
			
			case 2: {// Borrar
				
				alumGradua.setCodigoPersonal(codigoAlumno);
				alumGradua.setPlanId(planId);						
				if (alumGraduaDao.deleteReg(codigoAlumno,planId)){									
					if (alumGraduaMatDao.tieneReg( codigoAlumno, planId)){						
						alumGraduaMatDao.deleteMateriasDelPlan( codigoAlumno, planId);
					}
					break;
				}
			}
		}
		
		HashMap<String, MapaCredito> mapaCreditos 			= mapaCreditoDao.mapaCredito(planId);
		HashMap<String, String> mapaCreditosAlumno   		= alumnoCursoDao.mapaCreditosAlumno(planId, codigoAlumno);
		HashMap<String, AlumGraduaMat> mapaAlumnoMaterias 	= alumGraduaMatDao.mapaAlumnoMaterias(codigoAlumno);
		HashMap<String, CatTipoCurso> mapaTipoCurso			= catTipoCursoDao.getMapAll("");
		//System.out.println("Time 7:"+(System.currentTimeMillis()-inicio));
		HashMap<String,String> mapMateriaAc					= alumnoCursoDao.mapMateriaAc(codigoAlumno);
		HashMap<String,CatCarrera> mapaCarreras 			= catCarreraDao.mapaCarreras();
		HashMap<String,MapaPlan> mapaPlanes 				= mapaPlanDao.mapPlanes("'A','V','I'") ;
		
		modelo.addAttribute("numMatAc", numMatAc);
		modelo.addAttribute("numMatIns", numMatIns);
		modelo.addAttribute("numMatPen", numMatPen);
		modelo.addAttribute("numMatIns", numMatIns);
		modelo.addAttribute("numCreditos", numCreditos);
		modelo.addAttribute("ciclo", ciclo);
		modelo.addAttribute("optativo", optativo);
		modelo.addAttribute("credOptaAC", credOptaAC);
		modelo.addAttribute("credOptaFaltan", credOptaFaltan);
		modelo.addAttribute("existeSol", existeSol);
		modelo.addAttribute("carreraId", carreraId);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("numAccion", numAccion);
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("alumGradua", alumGradua);
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("lisCursos", lisCursos);
		modelo.addAttribute("mapaCreditos", mapaCreditos);
		modelo.addAttribute("mapaCreditosAlumno", mapaCreditosAlumno);
		modelo.addAttribute("mapaAlumnoMaterias", mapaAlumnoMaterias);
		modelo.addAttribute("mapaTipoCurso", mapaTipoCurso);
		modelo.addAttribute("mapMateriaAc", mapMateriaAc);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		
		return "datos_alumno/solgraduacion/solicitud";
	}
}