package aca.web.hca;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;
import aca.carga.spring.CargaGrupo;
import aca.carga.spring.CargaGrupoDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatModalidad;
import aca.catalogo.spring.CatModalidadDao;
import aca.catalogo.spring.CatNivel;
import aca.catalogo.spring.CatNivelDao;
import aca.hca.spring.HcaActividad;
import aca.hca.spring.HcaActividadDao;
import aca.hca.spring.HcaMaestro;
import aca.hca.spring.HcaMaestroActividad;
import aca.hca.spring.HcaMaestroActividadDao;
import aca.hca.spring.HcaMaestroDao;
import aca.hca.spring.HcaMaestroEstado;
import aca.hca.spring.HcaMaestroEstadoDao;
import aca.hca.spring.HcaRango;
import aca.hca.spring.HcaRangoDao;
import aca.hca.spring.HcaTipo;
import aca.hca.spring.HcaTipoDao;
import aca.plan.spring.MapaCursoDao;
import aca.vista.spring.AlumnoCursoDao;
import aca.vista.spring.CargaAcademica;
import aca.vista.spring.CargaAcademicaDao;
import aca.vista.spring.Maestros;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContHcaContrato {	
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	private HcaRangoDao hcaRangoDao;
	
	@Autowired
	private CatNivelDao catNivelDao;
	
	@Autowired
	private CatModalidadDao catModalidadDao;
	
	@Autowired
	private CargaDao cargaDao;
	
	@Autowired
	private CargaAcademicaDao cargaAcademicaDao;
	
	@Autowired
	private HcaActividadDao hcaActividadDao;
	
	@Autowired
	private MaestrosDao maestrosDao;
	
	@Autowired
	private HcaMaestroDao hcaMaestroDao;
	
	@Autowired
	private AccesoDao accesoDao;
	
	@Autowired
	private CargaGrupoDao cargaGrupoDao;

	@Autowired
	private HcaMaestroActividadDao hcaMaestroActividadDao;
	
	@Autowired
	private HcaMaestroEstadoDao hcaMaestroEstadoDao;
	
	@Autowired
	private CatCarreraDao catCarreraDao;
	
	@Autowired
	private MapaCursoDao mapaCursoDao;
	
	@Autowired
	private AlumnoCursoDao alumnoCursoDao;
	
	@Autowired
	private HcaTipoDao hcaTipoDao;
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}	
	
	@RequestMapping("/hca/contrato/buscar")
	public String hcaContratoBuscar(HttpServletRequest request, Model modelo){				
		
		String accion	= request.getParameter("accion")==null?"":request.getParameter("accion");
		String id 		= request.getParameter("id")==null?"":request.getParameter("id");
		
		String codigoPersonal	= 	"0";
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){		
			codigoPersonal	= (String)sesion.getAttribute("codigoPersonal");
		}
		
		boolean accionExiste = false;
		boolean esSupervisor = false;
		
		List<Maestros> empleados = new ArrayList<Maestros>();
		List<HcaMaestro> maestros = new ArrayList<HcaMaestro>();

		if(accion.equals("2")){
			sesion.setAttribute("codigoEmpleado",id);
		}else{		
			if(accesoDao.existeReg(codigoPersonal)==true){
				accionExiste = true;
				if(Boolean.parseBoolean(sesion.getAttribute("admin")+"") || accesoDao.esSupervisor(codigoPersonal)==true){					
					esSupervisor = true;
					empleados = maestrosDao.getListMaestros("ORDER BY APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE");
				}else{					
					maestros = hcaMaestroDao.lisPorAcceso(codigoPersonal, "ORDER BY ENOC.EMP_APELLIDO(CODIGO_PERSONAL)");
				}
			}
		}
		
		HashMap<String, Maestros> mapaMaestros = maestrosDao.mapaMaestros();
			
		modelo.addAttribute("accion", accion);
		modelo.addAttribute("codigoPersonal", codigoPersonal);
		modelo.addAttribute("id", id);
		modelo.addAttribute("empleados", empleados);
		modelo.addAttribute("accionExiste", accionExiste);
		modelo.addAttribute("esSupervisor", esSupervisor);
		modelo.addAttribute("maestros", maestros);
		modelo.addAttribute("mapaMaestros", mapaMaestros);		
		
		return "hca/contrato/buscar";
	}	
	
	@RequestMapping("/hca/contrato/docente")
	public String hcaContratoDocente(HttpServletRequest request, Model modelo){
		
		String cargaId			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String cargaSesion 		= "0"; 	
		String muestraCargas	= request.getParameter("MuestraCargas")==null?"N":request.getParameter("MuestraCargas");
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();	
		String codigoEmpleado	= "0";	 
		String codigoPersonal 	= "0";	 
		if (sesion!=null){		
			codigoEmpleado 		= (String) sesion.getAttribute("codigoEmpleado")==null?"0":(String) sesion.getAttribute("codigoEmpleado");
			codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal")==null?"0":(String) sesion.getAttribute("codigoPersonal");
			cargaSesion			= (String) sesion.getAttribute("cargaId");
		}
		if (cargaId.equals("0")) cargaId = cargaSesion;		
		
		List<Carga> lisCargasActivas 		= cargaDao.getListMaestro(codigoEmpleado);
		List<Carga> lisCargasInactivas 		= cargaDao.getListNoMaestro(codigoEmpleado);
		
		boolean existeLaCarga = false;
		for(Carga cargaActiva : lisCargasActivas){			
			if(cargaActiva.getCargaId().equals(cargaId)) existeLaCarga = true;
		}		
		if (muestraCargas.equals("S")) {
			for(Carga cargaInactiva : lisCargasInactivas){			
				if(cargaInactiva.getCargaId().equals(cargaId)) existeLaCarga = true;
			}
		}	
		if(!existeLaCarga){//Si en la carga que esta en sesion no da clase el maestro, entonces usar la ultima de la lista de cargas del maestro...
			if (lisCargasActivas.size()>=1){	
				cargaId = lisCargasActivas.get(0).getCargaId();
			}else if (muestraCargas.equals("S") && lisCargasInactivas.size()>=1){
				cargaId = lisCargasInactivas.get(0).getCargaId();
			}			
		}
		sesion.setAttribute("cargaId", cargaId);
		
		String accion				= request.getParameter("Accion")==null?"0":request.getParameter("Accion");	
		String actividad			= request.getParameter("actividad")==null?"1":request.getParameter("actividad");
		String horas				= request.getParameter("horas")==null?"1":request.getParameter("horas");	
		String modificable 			= hcaActividadDao.getModificable(actividad);
		String semanas				= cargaDao.getSemanas(cargaId);
		HashMap<String, CatNivel>	mapNivel = catNivelDao.getMapAll("");
		
		List<CargaAcademica> lisCursos = cargaAcademicaDao.getListaMaestro(cargaId, codigoEmpleado, " ORDER BY NOMBRE_CURSO");
		
		String nombreMaestro = maestrosDao.getNombreMaestro(codigoEmpleado,"NOMBRE");
		
		HcaMaestro hcaMaestro = new HcaMaestro();
		
		if(hcaMaestroDao.existeReg(codigoEmpleado)) {
			hcaMaestro = hcaMaestroDao.mapeaRegId(codigoEmpleado);
		}
		
		Acceso acceso = new Acceso();
		
		if(accesoDao.existeReg(codigoPersonal)) {
			acceso = accesoDao.mapeaRegId(codigoPersonal);
		}
		boolean esSupervisor 			= accesoDao.esSupervisor(codigoPersonal);
		boolean esAdministrador 		= accesoDao.esAdministrador(codigoPersonal);
		
		boolean insertoMaestroActividad = false;
		boolean updateMaestroActividad 	= false;
		boolean deleteMaestroActividad 	= false;
		boolean updateMaestroEstado 	= false;
		boolean insertMaestroEstado 	= false;
		
		CargaGrupo cargaGrupo 					= new CargaGrupo();
		HcaMaestroActividad hcaMaestroActividad = new HcaMaestroActividad();
		HcaMaestroEstado hcaMaestroEstado 		= new HcaMaestroEstado();
		HcaActividad hcaActividad 				= new HcaActividad();
		
		int error = 0;
		
		if(accion.equals("1")){	// Guardar Docencia
			for(int i = 0; i < lisCursos.size(); i++){
				CargaAcademica cargaAcademica = lisCursos.get(i);
				if(cargaGrupoDao.existeReg(cargaAcademica.getCursoCargaId())) {
					cargaGrupo = cargaGrupoDao.mapeaRegId(cargaAcademica.getCursoCargaId());
				}
				cargaGrupo.setValeucas(request.getParameter("vale"+cargaAcademica.getCursoCargaId()) != null ? "S":"N");
				cargaGrupo.setNumAlum(request.getParameter("numAlum"+cargaAcademica.getCursoCargaId()).equals("nul")?"0":request.getParameter("numAlum"+cargaAcademica.getCursoCargaId()).trim().equals("")?"0":request.getParameter("numAlum"+cargaAcademica.getCursoCargaId()));
				cargaGrupo.setSemanas(request.getParameter("semanas"+cargaAcademica.getCursoCargaId()).equals("nul")?"0":request.getParameter("semanas"+cargaAcademica.getCursoCargaId()).trim().equals("")?"0":request.getParameter("semanas"+cargaAcademica.getCursoCargaId()));
				
				if(!cargaGrupoDao.updateReg(cargaGrupo)){
					error++;
				}
			}			
			lisCursos = cargaAcademicaDao.getListaMaestro(cargaId, codigoEmpleado, "ORDER BY NOMBRE_CURSO");
		}else if(accion.equals("2")){	// Guarda Actividad
			hcaMaestroActividad.setCodigoPersonal(codigoEmpleado);
			hcaMaestroActividad.setCargaId(cargaId);
			hcaMaestroActividad.setActividadId(actividad);
			hcaMaestroActividad.setSemanas(request.getParameter("semanas"));
			hcaMaestroActividad.setHoras(horas);
			if(hcaMaestroActividadDao.existeReg(codigoEmpleado,cargaId,actividad)==false){
				if(hcaMaestroActividadDao.insertReg(hcaMaestroActividad)){
					insertoMaestroActividad = true;
				}
			}			
			if(hcaActividadDao.existeReg(actividad)){
				hcaActividad = hcaActividadDao.mapeaRegId(actividad);
			}
		}else if(accion.equals("3")){	// Prepara el modificar Actividad
			if(hcaMaestroActividadDao.existeReg(codigoEmpleado, cargaId, actividad)){
				hcaMaestroActividad = hcaMaestroActividadDao.mapeaRegId(codigoEmpleado, cargaId, actividad);		
			}
			if(hcaActividadDao.existeReg(hcaMaestroActividad.getActividadId())){
				hcaActividad = hcaActividadDao.mapeaRegId(hcaMaestroActividad.getActividadId());
			}
		}else if(accion.equals("4")){	// Modificar la Actividad
			if(hcaMaestroActividadDao.existeReg(codigoEmpleado, cargaId, actividad)){
				hcaMaestroActividad = hcaMaestroActividadDao.mapeaRegId(codigoEmpleado, cargaId, actividad);		
			}
			hcaMaestroActividad.setSemanas(request.getParameter("semanas"));
			if(request.getParameter("cambiaHora").equals("s")){
				hcaMaestroActividad.setHoras(request.getParameter("horas"));
			}else{
				hcaMaestroActividad.setHoras(hcaMaestroActividad.getHoras());
			}
			if(hcaMaestroActividadDao.updateReg(hcaMaestroActividad)){
				updateMaestroActividad = true;
			}
			//hcaMaestroActividad = new HcaMaestroActividad();
		}else if(accion.equals("5")){
			if(hcaMaestroActividadDao.existeReg(codigoEmpleado, cargaId, actividad)){
				hcaMaestroActividad = hcaMaestroActividadDao.mapeaRegId(codigoEmpleado, cargaId, actividad);		
			}
			if(hcaMaestroActividadDao.deleteReg(codigoEmpleado, cargaId, actividad)){
				deleteMaestroActividad = true;
			}
		}else if(accion.equals("6")){
			hcaMaestroEstado.setCodigoPersonal(codigoEmpleado);
			hcaMaestroEstado.setCargaId(cargaId);
			if(hcaMaestroEstadoDao.existeReg(codigoEmpleado, cargaId)){
				hcaMaestroEstado = hcaMaestroEstadoDao.mapeaRegId(codigoEmpleado, cargaId);
				hcaMaestroEstado.setSemanal(request.getParameter("tSemanal"));
				hcaMaestroEstado.setSemestral(request.getParameter("tSemestral"));
				hcaMaestroEstado.setEstado("2");
				if(hcaMaestroEstadoDao.updateReg(hcaMaestroEstado)){
					updateMaestroEstado = true;
				}
			}else{
				hcaMaestroEstado.setSemanal(request.getParameter("tSemanal"));
				hcaMaestroEstado.setSemestral(request.getParameter("tSemestral"));
				hcaMaestroEstado.setEstado("2");
				if(hcaMaestroEstadoDao.insertReg(hcaMaestroEstado)){
					insertMaestroEstado = true;
				}
			}
		}else if(accion.equals("7")){
			if(hcaMaestroEstadoDao.existeReg(codigoEmpleado, cargaId)) {
				hcaMaestroEstado = hcaMaestroEstadoDao.mapeaRegId(codigoEmpleado, cargaId);
			}
			
			hcaMaestroEstado.setSemanal("0");
			hcaMaestroEstado.setSemestral("0");
			hcaMaestroEstado.setEstado("1");
			if(hcaMaestroEstadoDao.updateReg(hcaMaestroEstado)){
				updateMaestroEstado = true;
			}
		}	
		
		boolean cerrado = false;
		hcaMaestroEstado.setCodigoPersonal(codigoEmpleado);
		hcaMaestroEstado.setCargaId(cargaId);
		if(hcaMaestroEstadoDao.existeReg(codigoEmpleado, cargaId)){
			hcaMaestroEstado = hcaMaestroEstadoDao.mapeaRegId(codigoEmpleado, cargaId);
			if(hcaMaestroEstado.getEstado().equals("2"))
				cerrado = true;
		}
		
		HashMap<String,String> mapaValor 			= new HashMap<String,String>();
		HashMap<String,Integer> mapaFs 				= new HashMap<String,Integer>();
		HashMap<String,String> mapaCarreraNivel		= new HashMap<String,String>();
		HashMap<String,CatCarrera> mapaCarrera		= catCarreraDao.getMapAll("");
		HashMap<String,CatModalidad> mapaModalidad	= catModalidadDao.getMapAll("");
		HashMap<String,Integer> mapaCursoCargaSize	= new HashMap<String,Integer>();
		HashMap<String,HcaTipo> mapaHcaTipo	= new HashMap<String,HcaTipo>();

		for(CargaAcademica cargaAcademica : lisCursos){
			
			String modalidadMateria = "0";
			if (!cargaAcademica.getModalidadId().equals("1")&&!cargaAcademica.getModalidadId().equals("4")) {
				modalidadMateria = "5";
			}else{
				modalidadMateria = cargaAcademica.getModalidadId();
			}

			String nivelId = catCarreraDao.getCarreraNivel(cargaAcademica.getCarreraId());

			String valor = hcaRangoDao.getValor(nivelId, modalidadMateria, cargaAcademica.getNumAlum());
			
			mapaValor.put(cargaAcademica.getCarreraId()+modalidadMateria+cargaAcademica.getNumAlum(), valor);
			
			int fs = Integer.parseInt(mapaCursoDao.getFS(cargaAcademica.getCursoId()));
			if (fs==0) {
				fs = Integer.parseInt(mapaCursoDao.getHH(cargaAcademica.getCursoId()));
			}

			mapaFs.put(cargaAcademica.getCursoId(), fs);
			
			mapaCarreraNivel.put(catCarreraDao.getNivelId(cargaAcademica.getCarreraId()), cargaAcademica.getCarreraId());
			
			mapaCursoCargaSize.put(cargaAcademica.getCursoCargaId(), alumnoCursoDao.getListCurso(cargaAcademica.getCursoCargaId(), "ORDER BY ENOC.EMP_APELLIDO(CODIGO_PERSONAL)").size());
			
		}
		
		HashMap<String, HcaTipo> mapaTipos = hcaTipoDao.mapaTipos();
		
		List<HcaActividad> lisActividad = hcaActividadDao.getListAll("ORDER BY TIPO_ID, NIVEL_ID, ACTIVIDAD_NOMBRE");
		
		List<HcaMaestroActividad> lisMA = hcaMaestroActividadDao.getListMaestroCarga(codigoEmpleado, cargaId, "ORDER BY ENOC.HCA_ACTORDEN(ACTIVIDAD_ID)");
		
		HashMap<String, HcaActividad> mapaActividades = hcaActividadDao.mapaActividades();
		
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("muestraCargas", muestraCargas);
		modelo.addAttribute("lisCargasActivas", lisCargasActivas);
		modelo.addAttribute("lisCargasInactivas", lisCargasInactivas);
		modelo.addAttribute("mapNivel", mapNivel);
		modelo.addAttribute("lisCursos", lisCursos);
		modelo.addAttribute("accion", accion);
		modelo.addAttribute("actividad", actividad);
		modelo.addAttribute("modificable", modificable);
		modelo.addAttribute("semanas", semanas);
		modelo.addAttribute("horas", horas);
		modelo.addAttribute("nombreMaestro", nombreMaestro);
		modelo.addAttribute("hcaMaestro", hcaMaestro);
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("esSupervisor", esSupervisor);
		modelo.addAttribute("error", error);
		modelo.addAttribute("insertoMaestroActividad", insertoMaestroActividad);
		modelo.addAttribute("hcaMaestroActividad", hcaMaestroActividad);
		modelo.addAttribute("updateMaestroActividad", updateMaestroActividad);
		modelo.addAttribute("deleteMaestroActividad", deleteMaestroActividad);
		modelo.addAttribute("updateMaestroEstado", updateMaestroEstado);
		modelo.addAttribute("insertMaestroEstado", insertMaestroEstado);
		modelo.addAttribute("cerrado", cerrado);
		modelo.addAttribute("mapaValor", mapaValor);
		modelo.addAttribute("mapaFs", mapaFs);
		modelo.addAttribute("mapaCarreraNivel", mapaCarreraNivel);
		modelo.addAttribute("mapaCarrera", mapaCarrera);
		modelo.addAttribute("mapaModalidad", mapaModalidad);
		modelo.addAttribute("mapaCursoCargaSize", mapaCursoCargaSize);
		modelo.addAttribute("hcaActividad", hcaActividad);
		modelo.addAttribute("lisActividad", lisActividad);
		modelo.addAttribute("mapaTipos", mapaTipos);
		modelo.addAttribute("mapaHcaTipo", mapaHcaTipo);
		modelo.addAttribute("lisMA", lisMA);
		modelo.addAttribute("mapaActividades", mapaActividades);
		modelo.addAttribute("esAdministrador", esAdministrador);
		
		return "hca/contrato/docente";
	}
	
	@RequestMapping("/hca/contrato/rango")
	public String hcaContratoRango(HttpServletRequest request, Model modelo){
		
		List<HcaRango> lisRangos 				= hcaRangoDao.lisTodos("ORDER BY NIVEL_ID, MODALIDAD_ID, RANGO_ID");
		HashMap<String, CatNivel> mapaTotal	 	= catNivelDao.getMapAll("");
		HashMap<String, CatModalidad> mapaTodos	= catModalidadDao.getMapAll("");

		
		modelo.addAttribute("lisRangos", lisRangos);
		modelo.addAttribute("mapaTotal", mapaTotal);
		modelo.addAttribute("mapaTodos", mapaTodos);

		return "hca/contrato/rango";
	}	
}