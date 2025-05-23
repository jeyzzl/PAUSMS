package aca.web.cargagrupo;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

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

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoPlanDao;
import aca.alumno.spring.AlumPersonalDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaBloque;
import aca.carga.spring.CargaBloqueDao;
import aca.carga.spring.CargaDao;
import aca.carga.spring.CargaGrupo;
import aca.carga.spring.CargaGrupoCurso;
import aca.carga.spring.CargaGrupoCursoDao;
import aca.carga.spring.CargaGrupoEvaluacionDao;
import aca.carga.spring.CargaGrupoHora;
import aca.carga.spring.CargaGrupoHoraDao;
import aca.carga.spring.CargaPermiso;
import aca.carga.spring.CargaPermisoPlan;
import aca.carga.spring.CargaPermisoPlanDao;
import aca.carga.spring.CargaPractica;
import aca.carga.spring.CargaPracticaDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatEdificio;
import aca.catalogo.spring.CatEdificioDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatHorario;
import aca.catalogo.spring.CatHorarioDao;
import aca.catalogo.spring.CatHorarioFacultad;
import aca.catalogo.spring.CatHorarioFacultadDao;
import aca.catalogo.spring.CatModalidad;
import aca.catalogo.spring.CatModalidadDao;
import aca.catalogo.spring.CatPeriodo;
import aca.catalogo.spring.CatPeriodoDao;
import aca.catalogo.spring.CatSalon;
import aca.catalogo.spring.CatSalonDao;
import aca.emp.spring.EmpCurriculumDao;
import aca.kardex.spring.KrdxCursoActDao;
import aca.objeto.spring.Hora;
import aca.objeto.spring.HoraDao;
import aca.plan.spring.MapaCurso;
import aca.plan.spring.MapaCursoDao;
import aca.plan.spring.MapaCursoElectiva;
import aca.plan.spring.MapaCursoElectivaDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.AlumnoCurso;
import aca.vista.spring.AlumnoCursoDao;
import aca.vista.spring.CargaAcademica;
import aca.vista.spring.CargaAcademicaDao;
import aca.vista.spring.CargaHorario;
import aca.vista.spring.CargaHorarioDao;
import aca.vista.spring.FinTablaDao;
import aca.vista.spring.Maestros;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContCargaGrupoGrupo {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	aca.vista.spring.MaestrosDao maestrosDao;

	@Autowired
	aca.carga.spring.CargaGrupoDao cargaGrupoDao;

	@Autowired
	EmpCurriculumDao empCurriculumDao;
	
	@Autowired
	private CatPeriodoDao catPeriodoDao;
	
	@Autowired
	private CargaDao cargaDao;
	
	@Autowired
	private aca.carga.spring.CargaPermisoDao cargaPermisoDao;
	
	@Autowired
	private CargaGrupoCursoDao cargaGrupoCursoDao;
	
	@Autowired
	private aca.acceso.spring.AccesoDao accesoDao;
	
	@Autowired
	private aca.catalogo.spring.CatFacultadDao catFacultadDao;
	
	@Autowired
	private aca.catalogo.spring.CatCarreraDao catCarreraDao;
	
	@Autowired
	private CargaBloqueDao cargaBloqueDao;
	
	@Autowired
	private CargaAcademicaDao cargaAcademicaDao;
	
	@Autowired
	private MapaCursoDao mapaCursoDao;
	
	@Autowired
	private FinTablaDao finTablaDao;
	
	@Autowired
	private CatModalidadDao catModalidadDao;
	
	@Autowired
	private CatEdificioDao catEdificioDao;
	
	@Autowired
	private CatSalonDao catSalonDao;
	
	@Autowired
	private CargaGrupoHoraDao cargaGrupoHoraDao;
	
	@Autowired
	private KrdxCursoActDao krdxCursoActDao;
	
	@Autowired
	private CatHorarioDao catHorarioDao;
	
	@Autowired
	private CatHorarioFacultadDao catHorarioFacultadDao;
	
	@Autowired
	private AlumnoCursoDao alumnoCursoDao;	
	
	@Autowired
	private AlumPersonalDao alumPersonalDao;

	@Autowired
	private CargaHorarioDao cargaHorarioDao;
	
	@Autowired
	private MapaPlanDao mapaPlanDao;
	
	@Autowired
	private CargaGrupoEvaluacionDao cargaGrupoEvaluacionDao;
	
	@Autowired
	CargaPracticaDao cargaPracticaDao;
	
	@Autowired
	MapaCursoElectivaDao mapaCursoElectivaDao;
	
	@Autowired
	private HoraDao horaDao;
	
	@Autowired
	private AccesoPlanDao accesoPlanDao;
	
	@Autowired
	CargaPermisoPlanDao cargaPermisoPlanDao;
	

	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/carga_grupo/grupo/alta")
	public String cargaGrupoGrupoAlta(HttpServletRequest request, Model modelo){
		
		String cargaId 			= "0";
		String cursoCargaId 	= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		String carreraId 		= request.getParameter("CarreraId")==null?"0":request.getParameter("CarreraId");			
		String planId 			= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String cursoId			= request.getParameter("CursoId")==null?"0":request.getParameter("CursoId");		
		String filtroTipoCurso 	= "9";
		boolean existeGrupo		= false;
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();		
        if (sesion!=null){
        	cargaId		= (String)sesion.getAttribute("cargaId");
        }
        
		Carga carga 			= cargaDao.mapeaRegId(cargaId);
		CargaGrupo cargaGrupo 	= new CargaGrupo();
		if (cargaGrupoDao.existeReg(cursoCargaId)) {
			cargaGrupo 	= cargaGrupoDao.mapeaRegId(cursoCargaId);
			existeGrupo	= true; 
		}
		
		List<MapaPlan> lisPlanes 		= mapaPlanDao.getLista(carreraId, "ORDER BY PLAN_ID");
		
		if(planId.equals("0") && lisPlanes.size() >= 1 ) planId =  lisPlanes.get(0).getPlanId();
		if (carreraId.equals("0") ) carreraId 	= mapaPlanDao.getCarreraId(planId);		
		
		List<MapaCurso> lisMaterias 			= mapaCursoDao.getMateriasElegibles(planId, filtroTipoCurso, " AND ESTADO = 1 ORDER BY NOMBRE_CURSO");		
		boolean existe = false;
		for(MapaCurso curso : lisMaterias) {
			if (curso.getCursoId().equals(cursoId)) {
				existe = true;
				break;
			}
		}
		if (existe==false && lisMaterias.size() >= 1) cursoId = lisMaterias.get(0).getCursoId();
		String practica = mapaCursoDao.mapeaRegId(cursoId).getLaboratorio();
		
		List<MapaCursoElectiva>	lisOptativas	= mapaCursoElectivaDao.getLista2(cursoId,"ORDER BY FOLIO");
		
		List<CargaBloque> lisBloques			= cargaBloqueDao.getListaActivo(cargaId, " ORDER BY BLOQUE_ID");
		
		List<CatModalidad> lisModalidades		= catModalidadDao.getListAll(" ORDER BY MODALIDAD_ID");
		
		String tienePrecio 						= mapaPlanDao.getPrecio(planId);
		
		HashMap<String,MapaCurso> mapaMaterias 	= mapaCursoDao.mapaCursosPlan(planId); 
		
		modelo.addAttribute("planId",planId);
		modelo.addAttribute("cursoId",cursoId);		
		modelo.addAttribute("practica",practica);
		modelo.addAttribute("tienePrecio",tienePrecio);
		modelo.addAttribute("existeGrupo",existeGrupo);
		modelo.addAttribute("carga",carga);		
		modelo.addAttribute("cargaGrupo",cargaGrupo);
		modelo.addAttribute("lisPlanes",lisPlanes);
		modelo.addAttribute("lisMaterias",lisMaterias);
		modelo.addAttribute("lisOptativas",lisOptativas);
		modelo.addAttribute("lisBloques",lisBloques);
		modelo.addAttribute("lisModalidades",lisModalidades);
		modelo.addAttribute("mapaMaterias",mapaMaterias);
		
		return "carga_grupo/grupo/alta";
	}
	
	@Transactional
	@RequestMapping("/carga_grupo/grupo/grabarGrupo")
	public String cargaGrupoGrupoGrabarGrupo(HttpServletRequest request){
		
		String cargaId			= "0";
		String carreraId 		= request.getParameter("CarreraId")==null?"0":request.getParameter("CarreraId");
		String planId 			= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String cursoId 			= request.getParameter("CursoId")==null?"0":request.getParameter("CursoId");
		String bloqueId 		= request.getParameter("BloqueId")==null?"0":request.getParameter("BloqueId");
		String fechaIni		    = request.getParameter("FechaInicio")==null?"0":request.getParameter("FechaInicio");
		String fechaFin		 	= request.getParameter("FechaFinal")==null?"0":request.getParameter("FechaFinal");
		String modalidadId		= request.getParameter("ModalidadId")==null?"1":request.getParameter("ModalidadId");
		String optativa 		= request.getParameter("Optativa")==null?"-":request.getParameter("Optativa");
		String grupo			= request.getParameter("Grupo")==null?"U":request.getParameter("Grupo");
		String precio 			= request.getParameter("Precio")==null?"0":request.getParameter("Precio").replace(" ","");
		String comentario		= request.getParameter("Comentario")==null?"0":request.getParameter("Comentario");
		String modo				= request.getParameter("Modo")==null?"0":request.getParameter("Modo");
		String cursoCargaId 	= "0";
		String usuario		 	= "0";
		
		
		String resultado 		= "-";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();		
        if (sesion != null){
        	cargaId		= (String)sesion.getAttribute("cargaId");        	
        	usuario		= (String)sesion.getAttribute("codigoPersonal"); 	
        }
        
		String horario		= "";
		for( int i=0; i<210; i++ ) horario +="0";
		CargaGrupo cargaGrupo = new CargaGrupo();
		cursoCargaId = cargaDao.maximoCurso(cargaId);
		cargaGrupo.setCursoCargaId( cursoCargaId);	
		cargaGrupo.setCargaId(cargaId);
		cargaGrupo.setBloqueId(bloqueId);
		cargaGrupo.setCarreraId(carreraId);
		cargaGrupo.setCodigoPersonal("0");
		cargaGrupo.setGrupo(grupo);
		cargaGrupo.setModalidadId(modalidadId);
		cargaGrupo.setfInicio(fechaIni);
		cargaGrupo.setfFinal(fechaFin);
		cargaGrupo.setfEntrega("01/01/1900");
		cargaGrupo.setRestriccion("S"); //Si valida horario
		cargaGrupo.setEstado("1");
		cargaGrupo.setHorario(horario);
		cargaGrupo.setValeucas("N");
		cargaGrupo.setNumAlum("0");
		cargaGrupo.setSemanas("0");
		cargaGrupo.setPrecio(precio);
		cargaGrupo.setComentario(comentario);
		cargaGrupo.setModo(modo);
		cargaGrupo.setUsuario(usuario);
		CargaGrupoCurso materia = new CargaGrupoCurso();
		materia.setCursoCargaId( cargaGrupo.getCursoCargaId() );
		materia.setCursoId(cursoId);
		materia.setOrigen("O");
		materia.setGrupoHorario("XX");
		
		boolean validaOptativa = false;
		
		MapaCurso mapaCurso = mapaCursoDao.mapeaRegId(cursoId);
		if (mapaCurso.getTipoCursoId().equals("2")||mapaCurso.getTipoCursoId().equals("7")){
			if (!optativa.equals("Elige")) validaOptativa = true;
			cargaGrupo.setOptativa(optativa);
		}else{
			cargaGrupo.setOptativa("-");
			validaOptativa = true; 
		}
		
		boolean onLine = false;
		if( modalidadId.trim().equals("5")||modalidadId.trim().equals("7")||modalidadId.trim().equals("8")||
			modalidadId.trim().equals("9")||modalidadId.trim().equals("10")||modalidadId.trim().equals("11")||
			modalidadId.trim().equals("12")||modalidadId.trim().equals("13")||modalidadId.trim().equals("14")||
			modalidadId.trim().equals("15")||modalidadId.trim().equals("16")||modalidadId.trim().equals("17"))
			if(mapaCurso.getOnLine().trim().equals("S"))
				onLine = true;
			else
				onLine = false;
		else
			onLine = true;
		
		boolean unid = false;
		if(modalidadId.trim().equals("2"))
			if(mapaCurso.getUnid().trim().equals("S"))
				unid = true;
			else
				unid = false;
		else
			unid = true;
		if (validaOptativa){
			if(unid){
				if(onLine){					
					if (cargaGrupoDao.existeReg(cursoCargaId) == false){
						if (cargaGrupoDao.insertReg(cargaGrupo)==true){
							if (cargaGrupoCursoDao.insertReg(materia)==true){
								if ( cargaDao.updateCurso(cargaId )==true){ 
									resultado = "Updayed: "+cargaGrupo.getCursoCargaId();									
								}else{									
									resultado = "Subject not incremented: "+cargaId;
								}						
							}else{								
								resultado = "Subject not saved: "+cargaGrupo.getCursoCargaId();
							}						
						}else{
							resultado = "Error saving cargaGrupo: "+cargaGrupo.getCursoCargaId();
						}
					}else{
						resultado = "Group already exists: "+cargaGrupo.getCursoCargaId();
					}						
				}else{
					resultado = "The course "+cursoId+" is not authorized online";
				}
			}else{
				resultado = "The course "+cursoId+" is not available";
			}
		}else{
			resultado = "The course is elective, record the name of the elective subject";
		}        	
		
		return "redirect:/carga_grupo/grupo/alta?CursoCargaId="+cursoCargaId+"&CarreraId="+carreraId+"&PlanId="+planId+"&CursoId="+cursoId+"&Mensaje="+resultado;
	}
	
	@RequestMapping("/carga_grupo/grupo/cambiaBloque")
	public String cargaGrupoGrupoCambiaBloque(HttpServletRequest request, Model modelo){
		
		String cargaId			= "0";
		String carreraId 		= request.getParameter("CarreraId")==null?"0":request.getParameter("CarreraId");
		String cursoCargaId		= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		String cursoOrigen		= cargaGrupoCursoDao.cursoIdOrigen(cursoCargaId);
		String nombreMateria	= mapaCursoDao.getNombreCurso(cursoOrigen);
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();		
        if (sesion!=null){
        	cargaId		= (String)sesion.getAttribute("cargaId");
        }

        CargaGrupo cargaGrupo 	= new CargaGrupo();
        if(cargaGrupoDao.existeReg(cursoCargaId)) {
        	cargaGrupo = cargaGrupoDao.mapeaRegId(cursoCargaId);
        }
        
        String nombreCarrera = catCarreraDao.getNombreCarrera(carreraId);
        String carga = cargaDao.getNombre(cargaId);
        
        List<CargaBloque> lisBloques	= 	cargaBloqueDao.getListBloqueCarga(cargaId, " ORDER BY BLOQUE_ID");
        
        modelo.addAttribute("cargaGrupo", cargaGrupo);
        modelo.addAttribute("nombreCarrera", nombreCarrera);
        modelo.addAttribute("nombreMateria", nombreMateria);
		modelo.addAttribute("carga", carga);
		modelo.addAttribute("lisBloques", lisBloques);

		
		return "carga_grupo/grupo/cambiaBloque";
	}
	
	@Transactional
	@RequestMapping("/carga_grupo/grupo/grabarBloque")
	public String cargaGrupoGrupoGrabarBloque(HttpServletRequest request){
	
		String cursoCargaId		= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		String carreraId 		= request.getParameter("CarreraId")==null?"0":request.getParameter("CarreraId");
		String planId 			= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String bloqueId		    = request.getParameter("BloqueId")==null?"0":request.getParameter("BloqueId");	
		String numPeriodos 		= cargaGrupoHoraDao.numPeriodosMateria(cursoCargaId);
		String mensaje			= "-";
		
		CargaGrupo cargaGrupo = new CargaGrupo();
		cargaGrupo.setCursoCargaId(cursoCargaId);
		cargaGrupo.setBloqueId(bloqueId);
		
		if (cargaGrupoDao.existeReg(cursoCargaId )){		
			if (cargaGrupoDao.updateBloque(cursoCargaId, bloqueId)){
				if (Integer.parseInt(numPeriodos) > 0){
					if (cargaGrupoHoraDao.updateReg(cursoCargaId, bloqueId)){
						mensaje = "Updated: "+cargaGrupo.getCursoCargaId();	
					}else{
						mensaje = "Error updating: "+cargaGrupo.getCursoCargaId();
					}
				}else{
					mensaje = "Updated: "+cargaGrupo.getCursoCargaId();
				}								
			}else{
				mensaje = "No change: "+cargaGrupo.getCursoCargaId();
			}
		}	
		
		return "redirect:/carga_grupo/grupo/cambiaBloque?CursoCargaId="+cursoCargaId+"&CarreraId="+carreraId+"&PlanId="+planId+"&BloqueId="+bloqueId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/carga_grupo/grupo/salon")
	public String cargaGrupoGrupoSalon(HttpServletRequest request, Model modelo){
		//enviarConEnoc(request,"Error-aca.ContCargaGrupoGrupo|cargaGrupoGrupoCambiaGrupo");
		
		HttpSession sesion		= null;
		String codigoPersonal 	= "0";
		sesion = ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoPersonal		= (String)sesion.getAttribute("codigoPersonal");
        }
        
		String carreraId		= request.getParameter("CarreraId")==null?"0":request.getParameter("CarreraId");
		String planId			= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String cursoCargaId		= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		String cursoId			= request.getParameter("CursoId")==null?"0":request.getParameter("CursoId");
		String edificioId		= request.getParameter("EdificioId")==null?"0":request.getParameter("EdificioId");
		String salonId			= request.getParameter("SalonId")==null?"0":request.getParameter("SalonId");
		String bloqueId			= request.getParameter("Bloque")==null?"0":request.getParameter("Bloque");
		String nombreCarrera	= catCarreraDao.getNombreCarrera(carreraId);
		String nombreMateria	= mapaCursoDao.getNombreCurso(cursoId);
		String cicloMateria 	= mapaCursoDao.mapeaRegId(cursoId).getCiclo();
		String mensaje			= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");

		if (cursoId.length()==14) cursoId=cursoId+" ";
		CargaGrupo cargaGrupo 	= cargaGrupoDao.mapeaRegId(cursoCargaId);
		
		List<CatEdificio> lisEdificios = catEdificioDao.lisEdificiosPorUsuario(codigoPersonal, "ORDER BY NOMBRE_EDIFICIO");
		if (edificioId.equals("0") && lisEdificios.size()>=1) {
			edificioId = lisEdificios.get(0).getEdificioId();
		}
		
		List<CatSalon> lisSalones 					= catSalonDao.getListaActivos(edificioId, "ORDER BY NOMBRE_SALON");
		if (salonId.equals("0")&&lisSalones.size()>=1) {
			salonId = lisSalones.get(0).getSalonId();
		}
		
		if(!catSalonDao.salonDeEdificio(edificioId, salonId) && lisSalones.size()>=1) {
			salonId = lisSalones.get(0).getSalonId();
		}
		
		List<CargaHorario> lisHorarios				= cargaHorarioDao.getLista(cursoCargaId, " ORDER BY DIA, HORA_INICIO, MINUTO_INICIO");
		List<CargaHorario> lisHorariosRegistrados	= cargaHorarioDao.lisHorarioCargaSalonBloque(cursoCargaId, cursoCargaId.substring(0,6), salonId, bloqueId);
		HashMap<String,CatEdificio> mapaEdificios	= catEdificioDao.mapaEdificios();
		HashMap<String,CatSalon> mapaSalones		= catSalonDao.getMapAll("");
		HashMap<String, MapaCurso> mapaCursos		= mapaCursoDao.mapaCursoEnCarga(cursoCargaId.substring(0,6));	
		HashMap <String, String> mapaCursosOrigen	= cargaGrupoCursoDao.mapaMateriasOrigen(cursoCargaId.substring(0,6));	

		modelo.addAttribute("nombreMateria", nombreMateria);
		modelo.addAttribute("nombreCarrera", nombreCarrera);
		modelo.addAttribute("edificioId", edificioId);
		modelo.addAttribute("salonId", salonId);
		modelo.addAttribute("bloqueId", bloqueId);
		modelo.addAttribute("cicloMateria", cicloMateria);
		modelo.addAttribute("cargaGrupo", cargaGrupo);
		modelo.addAttribute("lisEdificios", lisEdificios);
		modelo.addAttribute("lisSalones", lisSalones);
		modelo.addAttribute("lisHorarios", lisHorarios);
		modelo.addAttribute("lisHorariosRegistrados", lisHorariosRegistrados);
		modelo.addAttribute("mapaEdificios", mapaEdificios);
		modelo.addAttribute("mapaSalones", mapaSalones);
		modelo.addAttribute("mapaCursos", mapaCursos);
		modelo.addAttribute("mapaCursosOrigen", mapaCursosOrigen);
		modelo.addAttribute("mensaje", mensaje);
		
		return "carga_grupo/grupo/salon";
	}
	
	@RequestMapping("/carga_grupo/grupo/grabarSalon")
	public String cargaGrupoGrupoGrabarSalon(HttpServletRequest request, Model modelo){
		//enviarConEnoc(request,"Error-aca.ContCargaGrupoGrupo|cargaGrupoGrupoCambiaGrupo");
		
		String codigoPersonal 	= "0";
		HttpSession sesion  = ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoPersonal		= (String)sesion.getAttribute("codigoPersonal");
        }
        
        String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String carreraId		= request.getParameter("CarreraId")==null?"0":request.getParameter("CarreraId");
		String planId			= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String cursoCargaId		= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		String cursoId			= request.getParameter("CursoId")==null?"0":request.getParameter("CursoId");
		String edificioId		= request.getParameter("EdificioId")==null?"0":request.getParameter("EdificioId");
		String salonId			= request.getParameter("SalonId")==null?"0":request.getParameter("SalonId");
		String bloqueId			= request.getParameter("Bloque")==null?"0":request.getParameter("Bloque");
		String mensaje 			= "-";
		
		String dia				= request.getParameter("Dia")==null?"0":request.getParameter("Dia");
		String inicia			= request.getParameter("Inicia")==null?"0":request.getParameter("Inicia");
		String termina			= request.getParameter("Termina")==null?"0":request.getParameter("Termina");
		
		if (cursoId.length()==14) cursoId=cursoId+" ";		

		if(cargaHorarioDao.estaOcupado(cursoCargaId, salonId, dia, bloqueId, inicia, termina)) {
			mensaje = "1";
		}else {
			if (cargaGrupoHoraDao.existeReg(folio)) {				
				if (cargaGrupoHoraDao.updateSalon(folio, salonId)) {
					mensaje = "Timetable record for the class room saved!";
				}
			}
		}
		
		return "redirect:/carga_grupo/grupo/salon?CarreraId="+carreraId+"&PlanId="+planId+"&CursoCargaId="+cursoCargaId+"&CursoId="+cursoId+"&Mensaje="+mensaje+"&Bloque="+bloqueId+"&EdificioId="+edificioId+"&SalonId="+salonId;
	}
	
	@RequestMapping("/carga_grupo/grupo/borrarSalon")
	public String cargaGrupoGrupoBorrarSalon(HttpServletRequest request, Model modelo){
		//enviarConEnoc(request,"Error-aca.ContCargaGrupoGrupo|cargaGrupoGrupoCambiaGrupo");
		
		HttpSession sesion		= null;
		String codigoPersonal 	= "0";
		sesion = ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoPersonal		= (String)sesion.getAttribute("codigoPersonal");
        }
        
        String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String carreraId		= request.getParameter("CarreraId")==null?"0":request.getParameter("CarreraId");
		String planId			= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String cursoCargaId		= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		String cursoId			= request.getParameter("CursoId")==null?"0":request.getParameter("CursoId");	
		String bloqueId			= request.getParameter("Bloque")==null?"0":request.getParameter("Bloque");
		String edificioId			= request.getParameter("EdificioId")==null?"0":request.getParameter("EdificioId");
		String salonId			= request.getParameter("SalonId")==null?"0":request.getParameter("SalonId");
		String mensaje 			= "-";
		if (cursoId.length()==14) cursoId=cursoId+" ";
		if (cargaGrupoHoraDao.existeReg(folio)) {			
			if (cargaGrupoHoraDao.updateSalon(folio, "0")) {
				mensaje = "Timetable record deleted";
			}
		}
		
		return "redirect:/carga_grupo/grupo/salon?CarreraId="+carreraId+"&PlanId="+planId+"&CursoCargaId="+cursoCargaId+"&CursoId="+cursoId+"&Mensaje="+mensaje+"&Bloque="+bloqueId+"&EdificioId="+edificioId+"&SalonId="+salonId;
	}
	
	@RequestMapping("/carga_grupo/grupo/cambiaGrupo")
	public String cargaGrupoGrupoCambiaGrupo(HttpServletRequest request, Model modelo){
		//enviarConEnoc(request,"Error-aca.ContCargaGrupoGrupo|cargaGrupoGrupoCambiaGrupo");
		
		String carreraId		= request.getParameter("CarreraId")==null?"0":request.getParameter("CarreraId");
		String planId			= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String cursoCargaId		= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		String cursoId			= request.getParameter("CursoId")==null?"0":request.getParameter("CursoId");		
		String nombreCarrera	= catCarreraDao.getNombreCarrera(carreraId);
		String nombreMateria	= mapaCursoDao.getNombreCurso(cursoId);
		if (cursoId.length()==14) cursoId=cursoId+" ";
		String grupo			= "XX";
		if (cargaGrupoCursoDao.existeReg(cursoCargaId, cursoId)){
			grupo = cargaGrupoCursoDao.mapeaRegId(cursoCargaId, cursoId).getGrupoHorario();
		}
						
		modelo.addAttribute("nombreMateria", nombreMateria);
		modelo.addAttribute("nombreCarrera", nombreCarrera);
		modelo.addAttribute("grupo", grupo);
		
		return "carga_grupo/grupo/cambiaGrupo";
	}
	
	@RequestMapping("/carga_grupo/grupo/guardarGrupo")
	public String cargaGrupoGrupoGuardarGrupo(HttpServletRequest request, Model modelo){
		String carreraId 		= request.getParameter("CarreraId")==null?"0":request.getParameter("CarreraId");
		String planId 			= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String cursoCargaId 	= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		String cursoId 			= request.getParameter("CursoId")==null?"0":request.getParameter("CursoId");
		String grupo			= request.getParameter("Grupo")==null?"0":request.getParameter("Grupo");
		if (cursoId.length()==14) cursoId=cursoId+" ";
		CargaGrupoCurso cargaGrupoCurso = new CargaGrupoCurso();
		
		if (cargaGrupoCursoDao.existeReg(cursoCargaId, cursoId)){
			cargaGrupoCurso = cargaGrupoCursoDao.mapeaRegId(cursoCargaId, cursoId);
			cargaGrupoCurso.setGrupoHorario(grupo);
			cargaGrupoCursoDao.updateReg(cargaGrupoCurso);
		}
		
		return "redirect:/carga_grupo/grupo/cambiaGrupo?CarreraId="+carreraId+"&PlanId="+planId+"&CursoCargaId="+cursoCargaId+"&CursoId="+cursoId;
	}
	
	@RequestMapping("/carga_grupo/grupo/elegir")
	public String cargaGrupoGrupoElegir(HttpServletRequest request, Model modelo){
		//long inicio = System.currentTimeMillis();
		String cargaSesion 		= "000000";
		String periodoSesion 	= "0000";
		String codigoPersonal	= "0";
		
		Acceso acceso = new Acceso();
		HttpSession sesion = ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	cargaSesion 		= (String)sesion.getAttribute("cargaId");
        	periodoSesion 		= (String)sesion.getAttribute("periodo"); //cargaDao.getPeriodo(cargaSesion);
        	codigoPersonal		= (String)sesion.getAttribute("codigoPersonal");
        	if (accesoDao.existeReg(codigoPersonal)) {
        		acceso 			= accesoDao.mapeaRegId(codigoPersonal);
        	} 
        }
		//System.out.println("Time 1:"+(System.currentTimeMillis()-inicio));
		String periodoId 		= request.getParameter("PeriodoId")==null?"0000":request.getParameter("PeriodoId");
		String cargaId			= request.getParameter("CargaId")==null?"000000":request.getParameter("CargaId");
		
		if (periodoId.equals("0000") && cargaId.equals("000000") && !cargaSesion.equals("000000")){
			periodoId 	= periodoSesion;
			cargaId 	= cargaSesion;
		}else if (!periodoId.equals("0000") && sesion!=null) {
			sesion.setAttribute("periodo", periodoId);		
		}
		
		List<CatPeriodo> lisPeriodos				= catPeriodoDao.getListAll(" ORDER BY PERIODO_ID DESC");		
		List<Carga> lisCargas						= cargaDao.getListPeriodo(periodoId," ORDER BY CARGA_ID");
		
		boolean existeCarga = false;
		for (Carga carga : lisCargas) {
			if (carga.getCargaId().equals(cargaId)) existeCarga = true;
		}
		if (lisCargas.size()>= 1 && existeCarga==false){
			cargaId = lisCargas.get(0).getCargaId();
		}
		if (sesion!=null && !cargaId.equals("000000")){
			sesion.setAttribute("cargaId", cargaId);
		}		
		
		List<CargaPermisoPlan> lisPermisos 			= cargaPermisoPlanDao.lisPorCarga(cargaId, "ORDER BY ENOC.FACULTAD(CARRERA_ID), ENOC.CARRERA_NIVEL(CARRERA_ID), CARRERA_ID, PLAN_ID");	
		List<String> lisPlanesPorUsuario 			= accesoPlanDao.lisPorUsuario(codigoPersonal, "ORDER BY CARRERA_ID");
		
		HashMap<String, MapaPlan> mapPlanes 		= mapaPlanDao.mapPlanes("'A','V','I'");		
		HashMap<String, String> mapGrupos 			= cargaAcademicaDao.mapaGruposPorPlan(cargaId,"'O'");		
		HashMap<String, String> mapMaestros			= cargaAcademicaDao.mapMaestrosPorCargaAndPlan(cargaId);		
		HashMap<String, String> mapHorarios			= cargaAcademicaDao.mapHorariosPorPlanAndCarga(cargaId);
		HashMap<String, String> mapSalones			= cargaAcademicaDao.mapaTieneSalones(cargaId);		
		HashMap<String, CatFacultad> mapFacultades	= catFacultadDao.getMapFacultad("");
		HashMap<String, CatCarrera> mapCarreras		= catCarreraDao.getMapAll("");	
		HashMap<String, String> mapCursosPorCiclo	= cargaAcademicaDao.mapCursosPorCiclo(cargaId);		
		HashMap<String, String> mapaRequiereSalon	= cargaAcademicaDao.mapaRequiereSalon(cargaId);
		HashMap<String, String> mapaRequiereHorario	= cargaAcademicaDao.mapaRequiereHorario(cargaId);
		HashMap<String, String> mapaModos			= cargaAcademicaDao.mapaGruposModos(cargaId);
		HashMap<String, String> mapaAsignados		= krdxCursoActDao.mapaAlumnosPorPlanyTipo(cargaId, "'M'");		
		HashMap<String, String> mapaInscritos		= krdxCursoActDao.mapaAlumnosPorPlanyTipo(cargaId, "'I','1','2','3','4','5','6'");
		
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisPermisos", lisPermisos);
		modelo.addAttribute("lisPlanesPorUsuario", lisPlanesPorUsuario);
		
		modelo.addAttribute("mapPlanes", mapPlanes);		
		modelo.addAttribute("mapGrupos", mapGrupos);		
		modelo.addAttribute("mapMaestros", mapMaestros);
		modelo.addAttribute("mapHorarios", mapHorarios);
		modelo.addAttribute("mapSalones", mapSalones);
		modelo.addAttribute("mapFacultades", mapFacultades);
		modelo.addAttribute("mapCarreras", mapCarreras);
		modelo.addAttribute("mapCursosPorCiclo", mapCursosPorCiclo);		
		modelo.addAttribute("mapaRequiereSalon", mapaRequiereSalon);
		modelo.addAttribute("mapaRequiereHorario", mapaRequiereHorario);
		modelo.addAttribute("mapaModos", mapaModos);		
		modelo.addAttribute("mapaAsignados", mapaAsignados);
		modelo.addAttribute("mapaInscritos", mapaInscritos);
		
		return "carga_grupo/grupo/elegir";
	}
	
	@RequestMapping("/carga_grupo/grupo/equivalente")
	public String cargaGrupoGrupoEquivalente(HttpServletRequest request, Model modelo){
		String cursoCargaId 	= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	if (!cursoCargaId.equals("0")) {
        		sesion.setAttribute("cursoCargaId", cursoCargaId);
        	}else {
        		cursoCargaId = sesion.getAttribute("cursoCargaId").toString();  
        	}
        }
		
		String cursoOrigen 		= cargaGrupoCursoDao.cursoIdOrigen(cursoCargaId);
		String nombreMateria 	= mapaCursoDao.getNombreCurso(cursoOrigen);
		String cargaId			= cargaGrupoDao.getCargaId(cursoCargaId);
		
		List<CargaGrupoCurso> lisMaterias		    = cargaGrupoCursoDao.getLista(cursoCargaId, "ORDER BY ORIGEN DESC");
		HashMap<String,MapaCurso> mapaCursos 		= mapaCursoDao.mapaCursoEnCarga(cargaId);
		HashMap<String,String> mapaPlan				= mapaPlanDao.mapNombrePlan();
		HashMap<String,String> mapPlanId			= mapaCursoDao.mapCursosPlan();
		HashMap <String, String> mapaPorCurso		= krdxCursoActDao.mapaNumeroDeAlumnosPorCurso(cursoCargaId);

		
		modelo.addAttribute("nombreMateria", nombreMateria);
		modelo.addAttribute("lisMaterias", lisMaterias);
		modelo.addAttribute("mapaCursos", mapaCursos);
		modelo.addAttribute("mapaPorCurso", mapaPorCurso);
		modelo.addAttribute("mapaPlan", mapaPlan);
		modelo.addAttribute("mapPlanId", mapPlanId);
		
		return "carga_grupo/grupo/equivalente";
	}
	
	@RequestMapping("/carga_grupo/grupo/borrarEquivalente")
	public String cargaGrupoGrupoBorrarEquivalente(HttpServletRequest request){
		
		String cursoCargaId 	= request.getParameter("CursoCargaId");
		String carreraId 		= request.getParameter("CarreraId");		
		String planId 		    = request.getParameter("PlanId");
		String cursoId 		    = request.getParameter("CursoId");
		String mensaje 			= "-";
		
		if (krdxCursoActDao.numAlumnosEnMateria(cursoCargaId, cursoId)==0){			
			if (cargaGrupoCursoDao.existeReg(cursoCargaId, cursoId) == true){
				if (cargaGrupoCursoDao.deleteReg(cursoCargaId, cursoId)==true){ 
					mensaje = "Deleted: "+cursoCargaId+" - "+cursoId;								
				}else{
					mensaje = "Error deleting: "+cursoCargaId+" - "+cursoId;
				}
			}else{
				mensaje = "Not found: "+cursoCargaId+" - "+cursoId;
			} 
		}else{
			mensaje = "Couldn't delete: "+cursoCargaId+" - "+cursoId+" has students assigned";
		}		
		
		return "redirect:/carga_grupo/grupo/equivalente?CarreraId="+carreraId+"&PlanId="+planId+"&CursoCargaId="+cursoCargaId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/carga_grupo/grupo/equivalenteAccion")
	public String cargaGrupoGrupoEquivalenteAccion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContCargaGrupoGrupo|cargaGrupoGrupoEquivalenteAccion");
		return "carga_grupo/grupo/equivalenteAccion";
	}
	
	@RequestMapping("/carga_grupo/grupo/elegirplan")
	public String cargaGrupoGrupoElegirPlan(HttpServletRequest request, Model modelo){
		String cursoCargaId 	= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	if (!cursoCargaId.equals("0")) {
        		sesion.setAttribute("cursoCargaId", cursoCargaId);
        	}else {
        		cursoCargaId = sesion.getAttribute("cursoCargaId").toString();  
        	}
        }
		
		String cursoOrigen 		= cargaGrupoCursoDao.cursoIdOrigen(cursoCargaId);
		String nombreMateria 	= mapaCursoDao.getNombreCurso(cursoOrigen);
		
		List<CatCarrera> lisCarreras 				= catCarreraDao.getListAll(" ORDER BY FACULTAD_ID, NIVEL_ID, NOMBRE_CARRERA");
		List<MapaPlan> lisPlanes 					= mapaPlanDao.getListAll("ORDER BY CARRERA_ID, PLAN_ID");
		HashMap<String,CatFacultad> mapaFacultades 	= catFacultadDao.getMapFacultad("");
		
		modelo.addAttribute("nombreMateria", nombreMateria);
		modelo.addAttribute("lisCarreras", lisCarreras);
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		
		return "carga_grupo/grupo/elegirplan";
	}
	
	@RequestMapping("/carga_grupo/grupo/elegirmateria")
	public String cargaGrupoGrupoUnir(HttpServletRequest request, Model modelo){
		
		String codigoPersonal		= "0";
		String cursoCargaId 		= request.getParameter("CursoCargaId");
		String carreraId 			= request.getParameter("CarreraId");	
		String planElegir 		    = request.getParameter("PlanElegir");
		String cursoId				= request.getParameter("CursoId")==null?"0":request.getParameter("CursoId");
		String accion 			    = request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		String resultado 				= "-";
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoPersonal = (String) sesion.getAttribute("codigoPersonal");
        }
		
		// Consultar los planes con materias en el grupo
		String planesGrupo			= cargaGrupoCursoDao.getPlanes(cursoCargaId);
		
		if (accion.equals("1")){
			CargaGrupoCurso materia = new CargaGrupoCurso();
			materia.setCursoCargaId( cursoCargaId );
			materia.setCursoId( cursoId);
			materia.setOrigen("E");
			materia.setGrupoHorario("XX");
			
			String cargaId   		= cargaGrupoDao.getCargaId(cursoCargaId);		
			String carreraMateria 	= mapaPlanDao.getCarreraId(mapaCursoDao.getPlanId(cursoId));
			
			if (cargaPermisoPlanDao.carreraPermitida(cargaId, planElegir, carreraMateria)){
				
				if (cargaGrupoCursoDao.existeReg(cursoCargaId, cursoId) == false){
						
					if (cargaGrupoCursoDao.insertReg(materia)==true){							
						resultado = "Saved: "+materia.getCursoCargaId()+" - "+materia.getCursoId();
						planesGrupo			= cargaGrupoCursoDao.getPlanes(cursoCargaId);
					}else{
						resultado = "Not saved: "+materia.getCursoCargaId()+" - "+materia.getCursoId();
					}
				}else{
					resultado = "Already exists: "+materia.getCursoCargaId()+" - "+materia.getCursoId();
				}
			}else{
				resultado = "The course: <b>" + catCarreraDao.getNombreCarrera(carreraMateria) + "</b> of the <b> "+materia.getCursoId()+"</b> subject is not authorized for this Academic Load";				
			}
		}	
		
		// Lista de carreras
		List<MapaCurso> lisMaterias 			= mapaCursoDao.getLista(planElegir, " ORDER BY CICLO, NOMBRE_CURSO");
		// Mapa de materias del grupo
		HashMap<String, String> mapaMaterias 	= cargaGrupoCursoDao.mapMateriasDelGrupo(cursoCargaId);
		
		modelo.addAttribute("planesGrupo", planesGrupo);
		modelo.addAttribute("resultado", resultado);
		modelo.addAttribute("lisMaterias", lisMaterias);
		modelo.addAttribute("mapaMaterias", mapaMaterias);		
		
		return "carga_grupo/grupo/elegirmateria";
	}
	
	@RequestMapping("/carga_grupo/grupo/fechas")
	public String cargaGrupoGrupoFechas(HttpServletRequest request, Model modelo){
		
		String cursoCargaId		= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		String planId 			= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String cursoId 			= cargaGrupoCursoDao.cursoIdOrigen(cursoCargaId);
		String materia 			= mapaCursoDao.getMateria(cursoId);
		String codigoPersonal 	= "0"; 
		boolean esAdmin			= false;
		int numAlumnos			= krdxCursoActDao.numAlumnosMateria(cursoCargaId, "'M','I','1','2','3','4','5','6'");
		String practica 		= mapaCursoDao.mapeaRegId(cursoId).getLaboratorio();
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoPersonal		= (String)sesion.getAttribute("codigoPersonal");
        	esAdmin				= accesoDao.esAdministrador(codigoPersonal);
        }	
		
		CargaGrupo cargaGrupo = new CargaGrupo();
		if(cargaGrupoDao.existeReg(cursoCargaId)) {
			cargaGrupo	= cargaGrupoDao.mapeaRegId(cursoCargaId);
		}
		
		String nombreCarga		= cargaDao.getNombreCarga(cargaGrupo.getCargaId());
		String nombreModalidad	= catModalidadDao.getNombreModalidad(cargaGrupo.getModalidadId());
		int duracion			= aca.util.Fecha.getDiasEntreFechas(cargaGrupo.getfInicio() , cargaGrupo.getfFinal());
		
		List<CatModalidad> lisModalidades	= catModalidadDao.getListAll(" ORDER BY NOMBRE_MODALIDAD");
		
		modelo.addAttribute("cursoCargaId", cursoCargaId);
		modelo.addAttribute("numAlumnos", numAlumnos);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("cursoId", cursoId);
		modelo.addAttribute("practica", practica);
		modelo.addAttribute("materia", materia);
		modelo.addAttribute("cargaGrupo", cargaGrupo);
		modelo.addAttribute("nombreCarga", nombreCarga);
		modelo.addAttribute("nombreModalidad", nombreModalidad);
		modelo.addAttribute("duracion", duracion);
		modelo.addAttribute("esAdmin", esAdmin);
		modelo.addAttribute("lisModalidades", lisModalidades);		
		
		return "carga_grupo/grupo/fechas";
	}
	
	@RequestMapping("/carga_grupo/grupo/guardarFechas")
	public String cargaGrupoGrupoGuardarFechas(HttpServletRequest request, Model modelo){
		String planId 			= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String cursoCargaId 	= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		//String carreraId 		= request.getParameter("CarreraId")==null?"0":request.getParameter("CarreraId");
		String grupo	 		= request.getParameter("Grupo")==null?"0":request.getParameter("Grupo");
		String fInicio			= request.getParameter("FInicio")==null?"0":request.getParameter("FInicio");
		String fFinal			= request.getParameter("FFinal")==null?"0":request.getParameter("FFinal");
		String precio			= request.getParameter("Precio")==null?"0":request.getParameter("Precio").replace(" ","");
		String comentario		= request.getParameter("Comentario")==null?"0":request.getParameter("Comentario");
		String restriccion		= request.getParameter("Restriccion")==null?"0":request.getParameter("Restriccion");
		String modalidadId		= request.getParameter("ModalidadId")==null?"0":request.getParameter("ModalidadId");
		String modo				= request.getParameter("ModoId")==null?"0":request.getParameter("ModoId");
		String mensaje			= "-";
		String usuario			= "0";
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	usuario		= (String)sesion.getAttribute("codigoPersonal");
        }
		
		CargaGrupo cargaGrupo = new CargaGrupo();		
		if (cargaGrupoDao.existeReg(cursoCargaId)){
			cargaGrupo = cargaGrupoDao.mapeaRegId(cursoCargaId);			
			cargaGrupo.setGrupo(grupo);
			cargaGrupo.setfInicio(fInicio);
			cargaGrupo.setfFinal(fFinal);
			cargaGrupo.setPrecio(precio);
			cargaGrupo.setComentario(comentario);
			cargaGrupo.setRestriccion(restriccion);
			cargaGrupo.setModalidadId(modalidadId);
			cargaGrupo.setModo(modo);
			cargaGrupo.setUsuario(usuario);
			if (cargaGrupoDao.updateReg(cargaGrupo)) {
				mensaje = "Saved";
			}else {
				mensaje = "Error Saving";
			}			
		}else {
			System.out.println("Not found");			
		}
		
		return "redirect:/carga_grupo/grupo/fechas?CursoCargaId="+cursoCargaId+"&PlanId="+planId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/carga_grupo/grupo/grupo")
	public String cargaGrupoGrupoGrupo(HttpServletRequest request, Model modelo){
		
		String codigoPersonal	= "0";
		String cargaSesion 		= "0";
		String matricula 		= "0";
		String carreraId 		= request.getParameter("CarreraId")==null?"0":request.getParameter("CarreraId");
		String planId 		    = request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String bloqueId			= request.getParameter("BloqueId")==null?"0":request.getParameter("BloqueId");		
		String orden			= !bloqueId.equals("0")?"AND BLOQUE_ID = '"+bloqueId+"' ":" "+" ORDER BY PLAN_ID, MODALIDAD_ID, CICLO, NOMBRE_CURSO";
		String carreraNombre 	= "-";
		Acceso acceso 			= new Acceso();		
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	cargaSesion 		= (String)sesion.getAttribute("cargaId");       	
        	codigoPersonal		= (String)sesion.getAttribute("codigoPersonal");
			matricula			 = (String) sesion.getAttribute("codigoAlumno");
        	sesion.setAttribute("bloqueId", bloqueId);        	
        	if (accesoDao.existeReg(codigoPersonal)) {
        		acceso 			= accesoDao.mapeaRegId(codigoPersonal); 
        	}
        	if (!carreraId.equals("0")) {
        		sesion.setAttribute("carreraId", carreraId);
        	}else {
        		carreraId 		= sesion.getAttribute("carreraId").toString();  
        	}
        	if (!planId.equals("0")) {
        		sesion.setAttribute("planId", planId);
        	}else {
        		planId 		= sesion.getAttribute("planId").toString();
        	}        	
        	carreraNombre 		= catCarreraDao.getNombreCarrera(carreraId);
        }
        
		List<CargaBloque> lisBloques							= cargaBloqueDao.getListBloqueCarga(cargaSesion, " ORDER BY BLOQUE_ID");		
		List<CargaAcademica> lisMaterias						= cargaAcademicaDao.lisMateriasDelPlan( cargaSesion, planId, orden);		
		List<Hora> lisHoras 									= horaDao.listaHorasPorCarga(cargaSesion,carreraId);		
		HashMap<String, MapaCurso> mapaCursos		 			= mapaCursoDao.mapaCursoEnCarga(cargaSesion);		
		HashMap<String, CargaGrupo> mapaGrupos		 			= cargaGrupoDao.mapaGruposEnCarga(cargaSesion);
		HashMap <String, CatModalidad> mapaModalidades			= catModalidadDao.getMapAll("");
		HashMap <String, CatCarrera> mapaCarreras				= catCarreraDao.getMapAll("");
		HashMap <String, String> mapaCursosOrigen				= cargaGrupoCursoDao.mapaMateriasOrigen(cargaSesion);		
		HashMap <String, String> mapaCuentas					= finTablaDao.mapTablaCosto(cargaSesion);
		HashMap <String, String> mapaHorarios					= cargaGrupoDao.mapaHorarioMateria(cargaSesion);
		HashMap <String, String> mapaAlumnos					= krdxCursoActDao.mapaTotalAlumnos(cargaSesion,"'M','I','1','2','3','4','5','6'");
		HashMap <String, String> mapaAsignados					= krdxCursoActDao.mapaTotalAlumnos(cargaSesion,"'M'");
		HashMap <String, String> mapaInscritos					= krdxCursoActDao.mapaTotalAlumnos(cargaSesion,"'I','1','2','3','4','5','6'");
		HashMap<String, String> mapaHorasMateria				= cargaGrupoHoraDao.mapaHorasPorMateria(cargaSesion);
		HashMap<String, String> mapaSalonesFaltan				= cargaGrupoHoraDao.mapaSalonesFaltan(cargaSesion);
		HashMap<String, String> mapaNumEvalauciones				= cargaGrupoEvaluacionDao.mapaNumEvaluciones(cargaSesion);
		HashMap<String, CargaPractica> mapaPracticas			= cargaPracticaDao.mapaPorCarga(cargaSesion);
		HashMap<String, String> mapaMaestros					= maestrosDao.mapaMaestroCorto("NOMBRE");
		HashMap<String, String> mapaEstados						= maestrosDao.mapaEstadosMaestros();
		HashMap<String, String> mapaMateriasUnidas				= cargaGrupoCursoDao.mapCursosPorGrupo(cargaSesion);
		HashMap<String, String> mapaUsuariosMaterias			= maestrosDao.mapaUsuariosMaterias(cargaSesion);
	
			 
		modelo.addAttribute("carreraNombre", carreraNombre);
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("lisBloques", lisBloques);
		modelo.addAttribute("lisHoras", lisHoras);
		modelo.addAttribute("lisMaterias", lisMaterias);		
		modelo.addAttribute("mapaCursos", mapaCursos);
		modelo.addAttribute("mapaGrupos", mapaGrupos);
		modelo.addAttribute("mapaCuentas", mapaCuentas);
		modelo.addAttribute("mapaModalidades", mapaModalidades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaCursosOrigen", mapaCursosOrigen);
		modelo.addAttribute("mapaHorarios", mapaHorarios);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("mapaAsignados", mapaAsignados);
		modelo.addAttribute("mapaInscritos", mapaInscritos);
		modelo.addAttribute("mapaHorasMateria", mapaHorasMateria);
		modelo.addAttribute("mapaSalonesFaltan", mapaSalonesFaltan);
		modelo.addAttribute("mapaNumEvalauciones", mapaNumEvalauciones);	
		modelo.addAttribute("mapaPracticas", mapaPracticas);	
		modelo.addAttribute("mapaMaestros", mapaMaestros);
		modelo.addAttribute("mapaEstados", mapaEstados);
		modelo.addAttribute("mapaMateriasUnidas", mapaMateriasUnidas);
		modelo.addAttribute("mapaUsuariosMaterias", mapaUsuariosMaterias);
		
		return "carga_grupo/grupo/grupo";
	}
	
	@RequestMapping("/carga_grupo/grupo/borrarGrupo")
	public String cargaGrupoBorrarGrupo(HttpServletRequest request){
		
		String carreraId		= request.getParameter("CarreraId");
		String planId 			= request.getParameter("PlanId");
		String cursoCargaId 	= request.getParameter("CursoCargaId");
		String mensaje 			= "-";
		
		CargaGrupo grupo 		= new CargaGrupo();
		if (cargaGrupoDao.existeReg(cursoCargaId)) {
			grupo = cargaGrupoDao.mapeaRegId(cursoCargaId);
		}
		
		// Cuenta el numero de periodos registrados en el horario de la materia
		String numPeriodos = cargaGrupoHoraDao.numPeriodosMateria( cursoCargaId);
		
		if (cargaGrupoCursoDao.deleteGrupo(grupo.getCursoCargaId())==true){
			if (cargaGrupoDao.deleteReg(grupo.getCursoCargaId())==true){
				if (Integer.parseInt(numPeriodos)>0){
					if (cargaGrupoHoraDao.deleteRegCursoCargaId(grupo.getCursoCargaId())){
						mensaje = "Deleted: "+grupo.getCursoCargaId();									
					}else{						
						mensaje = "Error deleting timetable: "+grupo.getCursoCargaId();								
					}
				}else{
					mensaje = "Deleted: "+grupo.getCursoCargaId();							
				}												
			}else{				
				mensaje = "Error deleting group: "+grupo.getCursoCargaId();
			}
		}else{
			mensaje = "Error deleting subject: "+grupo.getCursoCargaId();
		}		
		
		return "redirect:/carga_grupo/grupo/grupo?CarreraId="+carreraId+"&PlanId="+planId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/carga_grupo/grupo/horariogrupo")
	public String cargaGrupoGrupoHorarioGrupo(HttpServletRequest request, Model modelo){
		
		String carreraId 		= request.getParameter("CarreraId")==null?"0":request.getParameter("CarreraId");
		String planId 		    = request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String cursoCargaId 	= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		String cursoId 			= request.getParameter("CursoId")==null?"0":request.getParameter("CursoId");
		String horarioOrigen 	= request.getParameter("HorarioOrigen")==null?"O":request.getParameter("HorarioOrigen");
		String cargaSesion		= "0";
		String actas			= "'0'";
		String verGrupo			= "";
		String facultadId		= "";
		String horarioId		= "";
		
		CargaGrupo grupo			= new CargaGrupo();	
		CargaGrupoCurso grupoCurso	= new CargaGrupoCurso();

		List<CargaAcademica> lisMaterias = new ArrayList<CargaAcademica>();
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			cargaSesion 		= (String)sesion.getAttribute("cargaId");
			
			if (!planId.equals("0")) {
				sesion.setAttribute("planId", planId);
			}else {
				planId 		= sesion.getAttribute("planId").toString();
			}        	
			if (!planId.equals("0")) {
				sesion.setAttribute("planId", planId);
			}else {
				planId 		= sesion.getAttribute("planId").toString();
			}        	
			if (!cursoCargaId.equals("0")) {
				sesion.setAttribute("cursoCargaId", cursoCargaId);
			}else {
				cursoCargaId = sesion.getAttribute("cursoCargaId").toString();
			}        	
			
			if (cargaGrupoDao.existeReg(cursoCargaId)) {
				grupo = cargaGrupoDao.mapeaRegId(cursoCargaId);			
			}
			
			if (cargaGrupoCursoDao.existeReg(cursoCargaId, cursoId)){
				grupoCurso = cargaGrupoCursoDao.mapeaRegId(cursoCargaId, cursoId);
			}
			
			if (grupoCurso.getOrigen().equals("O")) {
				verGrupo = grupo.getGrupo(); 
			}else if (!grupoCurso.getGrupoHorario().equals("XX")) {
				verGrupo = grupoCurso.getGrupoHorario();
			}else {
				verGrupo = grupo.getGrupo();
			}
			//System.out.println("VerGrupo "+cursoCargaId+":"+verGrupo);
			if (!verGrupo.equals("")) {
				sesion.setAttribute("verGrupo", verGrupo);
			}  else {
				verGrupo = sesion.getAttribute("verGrupo").toString();
			}
			
			if (!cursoCargaId.equals("0")) {
				sesion.setAttribute("cursoCargaId", cursoCargaId);
			}else {
				cursoCargaId = sesion.getAttribute("cursoCargaId").toString();
			} 
			
			if (!carreraId.equals("0")) {
				sesion.setAttribute("carreraId", carreraId);
				facultadId		= catCarreraDao.getFacultadId(carreraId);
				
				
				if (catHorarioDao.existeFacu(facultadId)) {
					horarioId		= catHorarioDao.getHorarioFacultad(facultadId, "A");
				}else {
					horarioId       = catHorarioDao.getHorarioFacultad("000", "A");
				}
				
				if (grupoCurso.getGrupoHorario().equals("XX")){
					lisMaterias 	= cargaAcademicaDao.lisMateriasDelGrupo(cargaSesion, carreraId, verGrupo, grupo.getBloqueId(), " ORDER BY ORIGEN DESC, NOMBRE_CURSO");
				}else {
					lisMaterias 	= cargaAcademicaDao.lisMateriasDelGrupo(cargaSesion, carreraId, verGrupo, grupo.getBloqueId(), " ORDER BY ORIGEN DESC, NOMBRE_CURSO");
				}
			}else {
				carreraId 		= sesion.getAttribute("carreraId").toString();  
				facultadId		= catCarreraDao.getFacultadId(carreraId);
				horarioId		= catHorarioDao.getHorarioFacultad(facultadId, "A");
				lisMaterias 	= cargaAcademicaDao.lisMateriasDelGrupo(cargaSesion, carreraId, verGrupo, grupo.getBloqueId(), " ORDER BY ORIGEN DESC, NOMBRE_CURSO");
			}
		}
		
		if (lisMaterias.size() >= 1) {
			int row=0;
			for (CargaAcademica carga : lisMaterias){
				row++;
				if (row==1) actas = "'"+carga.getCursoCargaId()+"'"; else actas += ",'"+carga.getCursoCargaId()+"'";
			}
		}

		if (cursoId.length()==14) cursoId=cursoId+" ";
		
		CatHorario catHorario	= new CatHorario();
		if (catHorarioDao.existeReg(horarioId)) {
			catHorario = catHorarioDao.mapeaRegId(horarioId);
		}
		
        String bloqueNombre 			 = cargaBloqueDao.getNombre(cargaSesion, grupo.getBloqueId());
		
		List<CatHorarioFacultad> lisPeriodos	= catHorarioFacultadDao.getLista(horarioId, " ORDER BY PERIODO");
		List<CargaGrupoHora> lisHorarios		= cargaGrupoHoraDao.lisHorariosActas(actas, " ORDER BY CURSO_CARGA_ID,DIA,PERIODO");
		
		HashMap <String, String> mapaMaestros	= maestrosDao.mapaMaestroEnCarga(cargaSesion,"NOMBRE");
		HashMap <String, String> mapaHorarios	= cargaGrupoHoraDao.mapaMateriasDelGrupo(actas);		
		HashMap <String, String> mapaHoras		= cargaGrupoHoraDao.mapaHorasPorMateria(cargaSesion);
		
		modelo.addAttribute("horarioOrigen", horarioOrigen);
		modelo.addAttribute("grupo", grupo);
		modelo.addAttribute("catHorario", catHorario);
		modelo.addAttribute("bloqueNombre", bloqueNombre);
		modelo.addAttribute("lisMaterias", lisMaterias);
		modelo.addAttribute("lisHorarios", lisHorarios);
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("mapaMaestros", mapaMaestros);
		modelo.addAttribute("mapaHorarios", mapaHorarios);
		modelo.addAttribute("mapaHoras", mapaHoras);
		modelo.addAttribute("carreraId", carreraId);
		modelo.addAttribute("planId", planId);
		
		return "carga_grupo/grupo/horariogrupo";
	}
	
	@RequestMapping("/carga_grupo/grupo/grabarHora")
	public String cargaGrupoGrupoGrabarHora(HttpServletRequest request){
		String carreraId 		= request.getParameter("CarreraId")==null?"0":request.getParameter("CarreraId");
		String planId 		    = request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String cursoCargaId 	= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		String cursoId 			= request.getParameter("CursoId")==null?"0":request.getParameter("CursoId");
		String modifica 		= request.getParameter("Modifica")==null?"N":request.getParameter("Modifica");
		String horarioId 		= request.getParameter("HorarioId")==null?"N":request.getParameter("HorarioId");
		String dia 	 	  		= request.getParameter("Dia")==null?"N":request.getParameter("Dia");
		String periodo 	   		= request.getParameter("PeriodoId")==null?"N":request.getParameter("PeriodoId");
		String cargaId 	   		= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String bloqueId	   		= request.getParameter("BloqueId")==null?"N":request.getParameter("BloqueId");
		String maestro	   		= request.getParameter("Maestro")==null?"0":request.getParameter("Maestro");
		String mensaje 			= "-";
		
		if (cursoId.length()==14) cursoId=cursoId+" ";
		
		CargaGrupoHora hora 	= new CargaGrupoHora();
		hora.setCursoCargaId(cursoCargaId);
		hora.setBloqueId(bloqueId);
		hora.setHorarioId(horarioId);
		hora.setDia(dia);
		hora.setPeriodo(periodo);
		hora.setSalonId("0");
		hora.setFolio(cargaGrupoHoraDao.maximoReg());
		
		if (cargaGrupoHoraDao.insertReg(hora)) {
			if(!horaDao.maestroHorarioOcupado(maestro, cargaId, bloqueId, dia, periodo)){
				mensaje = "1";
			}else {
				mensaje = "2";
			}		
		}		
		
		
		return "redirect:/carga_grupo/grupo/horariogrupo?CarreraId="+carreraId+"&PlanId="+planId+"&CursoCargaId="+cursoCargaId+"&Modifica="+modifica+"&CursoId="+cursoId+"&Mensaje="+mensaje+"&CargaId="+cargaId+"&Maestro="+maestro;
	}
	
	@RequestMapping("/carga_grupo/grupo/borrarHora")
	public String cargaGrupoGrupoBorrarHora(HttpServletRequest request){
		String carreraId 		= request.getParameter("CarreraId")==null?"0":request.getParameter("CarreraId");
		String planId 		    = request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String cursoCargaId 	= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		String cursoId 			= request.getParameter("CursoId")==null?"0":request.getParameter("CursoId");
		String horarioId 		= request.getParameter("HorarioId")==null?"N":request.getParameter("HorarioId");
		String dia 	 	  		= request.getParameter("Dia")==null?"N":request.getParameter("Dia");
		String periodo 	   		= request.getParameter("PeriodoId")==null?"N":request.getParameter("PeriodoId");	
		String maestro	   		= request.getParameter("Maestro")==null?"0":request.getParameter("Maestro");
		String cargaId 	   		= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		
		String mensaje 			= "-";
		
		if (cursoId.length()==14) cursoId=cursoId+" ";
		
		if (cargaGrupoHoraDao.existeReg(cursoCargaId, horarioId, dia, periodo)) {
			if (cargaGrupoHoraDao.deleteReg(cursoCargaId, horarioId, dia, periodo)){
				mensaje = "Deleted";
			}
		}
			
		return "redirect:/carga_grupo/grupo/horariogrupo?CarreraId="+carreraId+"&PlanId="+planId+"&CursoCargaId="+cursoCargaId+"&Modifica=S&CursoId="+cursoId+"&Mensaje="+mensaje+"&CargaId="+cargaId+"&Maestro="+maestro;
	}
	
	@RequestMapping("/carga_grupo/grupo/horario")
	public String cargaGrupoGrupoHorario(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContCargaGrupoGrupo|cargaGrupoGrupoHorario");
		return "carga_grupo/grupo/horario";
	}
	
	@RequestMapping("/carga_grupo/grupo/horarioOld")
	public String cargaGrupoGrupoHorarioOld(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContCargaGrupoGrupo|cargaGrupoGrupoHorarioOld");
		return "carga_grupo/grupo/horarioOld";
	}
	
	@RequestMapping("/carga_grupo/grupo/horarioSalon")
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
		List<CatEdificio> lisEdificios 				= catEdificioDao.lisEnHorario(cargaId,codigoPersonal," ORDER BY 1");
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
			
		return "carga_grupo/grupo/horarioSalon";
	}
	
	@RequestMapping("/carga_grupo/grupo/horarioSemestre")
	public String cargaGrupoGrupoHorarioSemestre(HttpServletRequest request, Model modelo){
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
		String ciclo			= request.getParameter("Ciclo")==null ? "1" : request.getParameter("Ciclo");
		String carreraId 		= mapaPlanDao.getCarreraId(planId);
		String facultadId		= catCarreraDao.getFacultadId(carreraId);
		String horarioId 		= catHorarioDao.getHorarioFacultad(facultadId, "A") == "0" ? "1" : catHorarioDao.getHorarioFacultad(facultadId, "A");
	
		List<String> lisTurnos 						= catHorarioFacultadDao.getTurno("1", " ORDER BY TURNO");
		List<CatHorarioFacultad> lisHorarios 		= catHorarioFacultadDao.getLista("1"," ORDER BY TURNO, PERIODO");
		
		
		List<CargaHorario> lisHoras								= cargaHorarioDao.lisHorarioPorCiclo(cargaId, planId, ciclo);
		HashMap<String, CargaAcademica> mapaMaterias 			= cargaAcademicaDao.mapaPorMateria(cargaId, planId, ciclo);
		
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("carreraId", carreraId);
		modelo.addAttribute("ciclo", ciclo);
		modelo.addAttribute("horarioId", horarioId);
		modelo.addAttribute("cargaNombre", cargaNombre);
		
		
		modelo.addAttribute("lisTurnos", lisTurnos);
		modelo.addAttribute("lisHorarios", lisHorarios);
		modelo.addAttribute("lisHoras", lisHoras);
		modelo.addAttribute("mapaMaterias", mapaMaterias);

			
		return "carga_grupo/grupo/horarioSemestre";
	}
	
	@RequestMapping("/carga_grupo/grupo/lista_alumnos")
	public String cargaGrupoGrupoListaAlumnos(HttpServletRequest request, Model modelo){
		//enviarConEnoc(request,"Error-aca.ContCargaGrupoGrupo|cargaGrupoGrupoListaAlumno");
		String institucion 		= "";
		String cargaId	 		= "";		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	institucion 	= (String)sesion.getAttribute("institucion");
        	cargaId 		= (String)sesion.getAttribute("cargaId"); 
        }
		
		String carreraId 		= request.getParameter("CarreraId")==null?"0":request.getParameter("CarreraId");
		String planId 		    = request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String cursoCargaId 	= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		String cursoId			= request.getParameter("CursoId")==null?"0":request.getParameter("CursoId");
		if (cursoId.length()==14) cursoId=cursoId+" ";
		
		CargaAcademica cargaAcademica	= new CargaAcademica();
		if(cargaAcademicaDao.existeReg(cursoCargaId, cursoId)){
			cargaAcademica = cargaAcademicaDao.mapeaRegId(cursoCargaId, cursoId);
		}
		
		List<AlumnoCurso> lisAlumnos	= alumnoCursoDao.getListCurso(cursoCargaId, " ORDER BY CURSO_ID,TIPOCAL_ID");
		HashMap<String, String> mapAlumnosMateria = alumPersonalDao.mapAlumnosMateria(cursoCargaId);
		
		modelo.addAttribute("institucion", institucion);
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("carreraId", carreraId);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("cursoCargaId", cursoCargaId);
		modelo.addAttribute("cargaAcademica", cargaAcademica);
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("mapAlumnosMateria", mapAlumnosMateria);
		
		return "carga_grupo/grupo/lista_alumnos";
	}
	
	@RequestMapping("/carga_grupo/grupo/maestro")
	public String cargaGrupoGrupoMaestro(HttpServletRequest request, Model modelo){
		
		String cursoCargaId 	= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		String planId		 	= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");		
		String cursoOrigen		= cargaGrupoCursoDao.cursoIdOrigen(cursoCargaId);
		String nombreMateria	= mapaCursoDao.getNombreCurso(cursoOrigen);
		
		CargaGrupo cargaGrupo 	= new CargaGrupo();
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	if (cursoCargaId.equals("0")) {
        		cursoCargaId = sesion.getAttribute("cursoCargaId").toString();
        	}else{
        		sesion.setAttribute("cursoCargaId", cursoCargaId);
        	}        	       
        	if (planId.equals("0")) {
        		planId = sesion.getAttribute("planId").toString();
        	}       	  
        }
        
        if (cargaGrupoDao.existeReg(cursoCargaId)) {
        	cargaGrupo 			= cargaGrupoDao.mapeaRegId(cursoCargaId);
        }
        
        String maestroTitular   = cargaGrupo.getCodigoPersonal();
		String maestroAuxiliar  = cargaGrupo.getCodigoOtro();	
		
		String nombreTitular	= maestrosDao.getNombreMaestro( maestroTitular,"NOMBRE");
		String nombreAuxiliar	= maestrosDao.getNombreMaestro( maestroAuxiliar,"NOMBRE");

		nombreTitular 			= nombreTitular.equals("0000000")?"Sin maestro":nombreTitular;
		nombreAuxiliar 			= nombreAuxiliar.equals("0000000")?"Sin maestro":nombreAuxiliar;
		
		List<Maestros> lisMaestros	= maestrosDao.lisPorEstado("'A','J'"," ORDER BY APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE");
		
		modelo.addAttribute("cargaGrupo", cargaGrupo);
		modelo.addAttribute("cursoCargaId", cursoCargaId);
		modelo.addAttribute("nombreTitular", nombreTitular);
		modelo.addAttribute("nombreAuxiliar", nombreAuxiliar);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("nombreMateria", nombreMateria);
		
		modelo.addAttribute("lisMaestros", lisMaestros);
		
		return "carga_grupo/grupo/maestro";
	}
	
	@RequestMapping("/carga_grupo/grupo/materiasPorSalon")
	public String cargaGrupoGrupoMateriasPorSalon(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContCargaGrupoGrupo|cargaGrupoGrupoMateriasPorSalon");
		return "carga_grupo/grupo/materiasPorSalon";
	}
	
	@RequestMapping("/carga_grupo/grupo/nuevoHorario")
	public String cargaGrupoGrupoNuevoHorario(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContCargaGrupoGrupo|cargaGrupoGrupoNuevoHorario");
		return "carga_grupo/grupo/nuevoHorario";
	}
	
	@RequestMapping("/carga_grupo/grupo/traduceHorario")
	public String cargaGrupoGrupoTraduceHorario(HttpServletRequest request){		
		return "carga_grupo/grupo/traduceHorario";
	}

	@RequestMapping("/carga_grupo/grupo/elegirMaestro")
	public String cargaGrupoGrupoElegirMaestro(HttpServletRequest request, Model modelo){
		String carreraId 		= request.getParameter("CarreraId")==null?"0":request.getParameter("CarreraId");
		String planId 			= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String cursoCargaId 	= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		String bloque 			= request.getParameter("bloque")==null?"0":request.getParameter("bloque");
		String materia 			= request.getParameter("Materia")==null?"0":request.getParameter("Materia");
		String asignaCambia 	= request.getParameter("AsignaCambia")==null?"0":request.getParameter("AsignaCambia");
		String maestroTitular 	= request.getParameter("MaestroTitular")==null?"-":request.getParameter("MaestroTitular");
		String maestroAuxiliar 	= request.getParameter("MaestroAuxiliar")==null?"-":request.getParameter("MaestroAuxiliar");
		String nombre 			= "-";
		String usuario 			= "-";
		String mensaje			= "";
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();		
		if (sesion!=null){
			usuario = (String) sesion.getAttribute("codigoEmpleado");
		}

		nombre = maestrosDao.getNombreMaestro(usuario,"NOMBRE");
		
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("usuario", usuario);
		modelo.addAttribute("nombre", nombre);
		modelo.addAttribute("carreraId", carreraId);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("cursoCargaId", cursoCargaId);
		modelo.addAttribute("bloque", bloque);
		modelo.addAttribute("materia", materia);
		modelo.addAttribute("asignaCambia", asignaCambia);
		modelo.addAttribute("maestroTitular", maestroTitular);
		modelo.addAttribute("maestroAuxiliar", maestroAuxiliar);
		
		return "carga_grupo/grupo/elegirMaestro";
	}

	@RequestMapping("/carga_grupo/grupo/guardarMaestro")
	public String cargaGrupoGrupoGuardarMaestro(HttpServletRequest request, Model modelo){
		
		String carreraId 		= request.getParameter("CarreraId")==null?"0":request.getParameter("CarreraId");
		String planId 			= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String cursoCargaId 	= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		String bloque 			= request.getParameter("bloque")==null?"0":request.getParameter("bloque");
		String materia 			= request.getParameter("Materia")==null?"0":request.getParameter("Materia");
		String asignaCambia 	= request.getParameter("AsignaCambia")==null?"0":request.getParameter("AsignaCambia");		
		String codigoPersonal	= request.getParameter("CodigoPersonal")==null?"0":request.getParameter("CodigoPersonal");		
		String mensaje			= "";
	
		CargaGrupo cargaGrupo = cargaGrupoDao.mapeaRegId(cursoCargaId);
		cargaGrupo.setUsuario(codigoPersonal);
		if(asignaCambia.equals("1")) {
			cargaGrupo.setCodigoPersonal(codigoPersonal);
		}else{
			cargaGrupo.setCodigoOtro(codigoPersonal);
		}
		
		if(cargaGrupoDao.existeReg(cursoCargaId)) {
			if(asignaCambia.equals("1")) {	
				if(cargaGrupoDao.updateMaestro(cargaGrupo)) {
					cargaGrupoDao.updateReg(cargaGrupo);
					mensaje	= "1";
				}else {
					mensaje	= "2";
				}
			}else {
				if(cargaGrupoDao.updateAuxiliar(cargaGrupo)) {
					cargaGrupoDao.updateReg(cargaGrupo);
					mensaje	= "1";
				}else {
					mensaje	= "2";
				}
			}
		}else {
			mensaje	= "3";
		}
		
		return "redirect:/carga_grupo/grupo/maestro?CarreraId="+carreraId+"&PlanId="+planId+"&CursoCargaId="+cursoCargaId+"&bloque="+bloque+"&Materia="+materia+"&AsignaCambia="+asignaCambia;		
	}
	
	@RequestMapping("/carga_grupo/grupo/quitarMaestro")
	public String cargaGrupoGrupoQuitarMaestro(HttpServletRequest request, Model modelo){
		
		String carreraId 		= request.getParameter("CarreraId")==null?"0":request.getParameter("CarreraId");
		String planId 			= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String cursoCargaId 	= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");

		String bloque 			= request.getParameter("bloque")==null?"0":request.getParameter("bloque");
		String materia 			= request.getParameter("Materia")==null?"0":request.getParameter("Materia");
		String opcion 			= request.getParameter("Opcion")==null?"0":request.getParameter("Opcion");
		
		CargaGrupo cargaGrupo = cargaGrupoDao.mapeaRegId(cursoCargaId);
		if(opcion.equals("1")) {
			cargaGrupo.setCodigoPersonal("0");
			cargaGrupoDao.updateMaestro(cargaGrupo);
		}else if(opcion.equals("2")){
			cargaGrupo.setCodigoOtro("0");
			cargaGrupoDao.updateAuxiliar(cargaGrupo);
		}		
		return "carga_grupo/grupo/maestro?PlanId="+planId+"&CursoCargaId="+cursoCargaId+"&Materia="+materia;
	}
	
	@RequestMapping("/carga_grupo/grupo/altaPracticas")
	public String cargaGrupoGrupoAltaPracticas(HttpServletRequest request, Model modelo){
		
		String cursoCargaId		= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");		
		String cursoOrigen 		= cargaGrupoCursoDao.cursoIdOrigen(cursoCargaId);
		String materiaNombre 	= mapaCursoDao.getNombreCurso(cursoOrigen);
		String planId			= mapaCursoDao.getPlanId(cursoOrigen);
		String carreraId		= mapaPlanDao.getCarreraId(planId);		
		Carga carga				= cargaDao.mapeaRegId(cursoCargaId.substring(0,6));
		
		List<CargaPractica> lisPracticas	= cargaPracticaDao.lisPorGrupo(cursoCargaId, " ORDER BY FOLIO");
		
		modelo.addAttribute("carreraId", carreraId);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("carga", carga);
		modelo.addAttribute("materiaNombre", materiaNombre);
		modelo.addAttribute("cursoOrigen", cursoOrigen);		
		modelo.addAttribute("lisPracticas", lisPracticas);
		
		return "carga_grupo/grupo/altaPracticas";
	}
	
	@RequestMapping("/carga_grupo/grupo/grabarPracticas")
	public String cargaGrupoGrupoGrabaPracticas(HttpServletRequest request){
		String cursoCargaId 	= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		String fechaIni		    = request.getParameter("FechaIni")==null?"0":request.getParameter("FechaIni");
		String fechaFin		 	= request.getParameter("FechaFin")==null?"0":request.getParameter("FechaFin");		
		String folio 			= "1"; //cargaPracticaDao.maximoReg(cursoCargaId); 
		String mensaje 			= "-";
		
		CargaPractica practica = new CargaPractica();
		practica.setCursoCargaId(cursoCargaId);
		practica.setFolio(folio);
		practica.setFechaIni(fechaIni);
		practica.setFechaFin(fechaFin);
		practica.setEstado("A");
		if (cargaPracticaDao.existeReg(cursoCargaId, folio)) {
			if (cargaPracticaDao.updateReg(practica)){
				mensaje = "Saved";
			}
		}else {
			if (cargaPracticaDao.insertReg(practica)){
				mensaje = "Saved";
			}
		}
		
		return "redirect:/carga_grupo/grupo/altaPracticas?CursoCargaId="+cursoCargaId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/carga_grupo/grupo/borrarPracticas")
	public String cargaGrupoGrupoBorrarPracticas(HttpServletRequest request){
		String cursoCargaId 	= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		String folio		    = request.getParameter("Folio")==null?"0":request.getParameter("Folio");		 
		String mensaje 			= "-";
		
		if (cargaPracticaDao.existeReg(cursoCargaId, folio)){
			if (cargaPracticaDao.deleteReg(cursoCargaId, folio)){
				mensaje = "Deleted";
			}else {
				mensaje = "Error deleting";
			}			
		}
		
		return "redirect:/carga_grupo/grupo/altaPracticas?CursoCargaId="+cursoCargaId+"&Mensaje="+mensaje;
	}
	@RequestMapping("/carga_grupo/grupo/materias")
	public String cargaGrupoGrupoMaterias(HttpServletRequest request, Model modelo){
		String cargaId 			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String planId		    = request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");		
		
		List<MapaCurso> lisMaterias 					= mapaCursoDao.getLista(planId, " ORDER BY CICLO, NOMBRE_CURSO");
		List<CargaAcademica> lisOfertadas				= cargaAcademicaDao.lisPorCargayPlan(cargaId, planId, " ORDER BY CICLO, NOMBRE_CURSO");
		HashMap<String,String> mapaRegistradas			= cargaAcademicaDao.mapaMateriasRegistradas(cargaId, planId);
		HashMap<String,CatModalidad> mapaModalidades	= catModalidadDao.getMapAll("");
		
		modelo.addAttribute("lisMaterias", lisMaterias);
		modelo.addAttribute("lisOfertadas", lisOfertadas);
		modelo.addAttribute("mapaRegistradas", mapaRegistradas);
		modelo.addAttribute("mapaModalidades", mapaModalidades);
		
		return "carga_grupo/grupo/materias";
	}
}