package aca.web.taller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import aca.acceso.spring.AccesoDao;
import aca.afe.spring.FesCcAfeAcuerdosDao;
import aca.alumno.spring.AlumAcademicoDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanDao;
import aca.bec.spring.BecAccesoDao;
import aca.bec.spring.BecAcuerdo;
import aca.bec.spring.BecAcuerdoDao;
import aca.bec.spring.BecCategoria;
import aca.bec.spring.BecCategoriaDao;
import aca.bec.spring.BecCompetencia;
import aca.bec.spring.BecCompetenciaDao;
import aca.bec.spring.BecContrato;
import aca.bec.spring.BecContratoDao;
import aca.bec.spring.BecFija;
import aca.bec.spring.BecFijaDao;
import aca.bec.spring.BecInforme;
import aca.bec.spring.BecInformeAlumno;
import aca.bec.spring.BecInformeAlumnoDao;
import aca.bec.spring.BecInformeDao;
import aca.bec.spring.BecParametroDao;
import aca.bec.spring.BecPeriodo;
import aca.bec.spring.BecPeriodoDao;
import aca.bec.spring.BecPlazas;
import aca.bec.spring.BecPlazasDao;
import aca.bec.spring.BecPuesto;
import aca.bec.spring.BecPuestoAlumno;
import aca.bec.spring.BecPuestoAlumnoDao;
import aca.bec.spring.BecPuestoDao;
import aca.bec.spring.BecTipo;
import aca.bec.spring.BecTipoDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.financiero.spring.CcpPresupuestoDao;
import aca.financiero.spring.ContCcosto;
import aca.financiero.spring.ContCcostoDao;
import aca.financiero.spring.ContEjercicio;
import aca.financiero.spring.ContEjercicioDao;
import aca.financiero.spring.ContMovimientoDao;
import aca.parametros.spring.ParametrosDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.util.Fecha;
import aca.vista.spring.InscritosDao;

@Controller
public class ContTallerPuesto {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	private BecParametroDao becParametroDao;

	@Autowired
	private BecCompetenciaDao becCompetenciaDao;
	
	@Autowired
	private BecCategoriaDao becCategoriaDao;
	
	@Autowired
	private BecPuestoDao becPuestoDao;
	
	@Autowired
	private BecPuestoAlumnoDao becPuestoAlumnoDao;
	
	@Autowired
	private BecPeriodoDao becPeriodoDao;
	
	@Autowired
	private BecAccesoDao becAccesoDao;

	@Autowired
	private BecAcuerdoDao becAcuerdoDao;
	
	@Autowired
	private BecTipoDao becTipoDao;
	
	@Autowired
	private ContCcostoDao contCcostoDao;
	
	@Autowired
	private ContEjercicioDao contEjercicioDao;
	
	@Autowired
	private CatCarreraDao catCarreraDao;
	
	@Autowired
	private AlumPlanDao alumPlanDao;
	
	@Autowired
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired
	private MapaPlanDao mapaPlanDao;
	
	@Autowired
	private AccesoDao accesoDao;
	
	@Autowired
	private AlumAcademicoDao alumAcademicoDao;
	
	@Autowired
	private FesCcAfeAcuerdosDao fesCcAfeAcuerdosDao;
	
	@Autowired
	private ParametrosDao parametrosDao;
	
	@Autowired
	private BecContratoDao becContratoDao;
	
	@Autowired
	private BecPlazasDao becPlazasDao;
	
	@Autowired
	ContMovimientoDao contMovimientoDao;
	
	@Autowired
	InscritosDao inscritosDao;
	
	@Autowired
	BecInformeAlumnoDao becInformeAlumnoDao;
	
	@Autowired
	BecInformeDao becInformeDao;
	
	@Autowired
	BecFijaDao becFijaDao;
	
	@Autowired
	CcpPresupuestoDao ccpPresupuestoDao;
	
	@Autowired
	ServletContext context;	
	
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/taller/puesto/puesto")
	public String tallerPuestoPuesto(HttpServletRequest request, Model modelo){
		
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if( (String) sesion.getAttribute("fechaPuesto") == null ){
			sesion.setAttribute("fechaPuesto", aca.util.Fecha.getHoy());
		}
		
		String cambioDeFecha           	= request.getParameter("cambioDeFecha")==null?"":request.getParameter("cambioDeFecha");
		String errorFecha 				= "";
		boolean fechaCambiaElEjercicio	= false;
		boolean fechaCambiaElPeriodo   	= false;
		if( request.getParameter("fechaPuesto") != null && cambioDeFecha.equals("1") ){
			
			String f 				= request.getParameter("fechaPuesto");
			String nuevoEjercicio 	= "001-"+f.split("/")[2];
			String nuevoPeriodo     = becPeriodoDao.getPeriodoDeFecha(f, nuevoEjercicio);
			
			if(!nuevoPeriodo.equals("")){
				fechaCambiaElEjercicio 	= true;
				fechaCambiaElPeriodo 	= true;
				sesion.setAttribute("ejercicioId", nuevoEjercicio);
				sesion.setAttribute("periodoBecas", nuevoPeriodo);
				sesion.setAttribute("fechaPuesto", f);
			}else{
				errorFecha = " <div class='alert alert-danger'>Error, no hubo ningún periodo activo en la fecha que seleccionaste</div> ";
			}
		}

		String fechaPuesto = (String) sesion.getAttribute("fechaPuesto");

		String idEjercicio = (String) sesion.getAttribute("ejercicioId");
		
		List<ContEjercicio> ejercicios = contEjercicioDao.getListAll("ORDER BY 1 DESC");
		if(request.getParameter("idEjercicio")!=null && fechaCambiaElEjercicio==false){
			idEjercicio = request.getParameter("idEjercicio");	
			sesion.setAttribute("ejercicioId", idEjercicio);
		}
		
		String codigo		= (String)sesion.getAttribute("codigoPersonal");
		
		String acceso		= becAccesoDao.getUsuarioCentrosCosto(idEjercicio, codigo);	
			
		boolean admin       = accesoDao.getBecas(codigo);
		
		List<BecPuesto> puestos 				= becPuestoDao.getListAllEjercicio(idEjercicio, " ORDER BY ID_CCOSTO, CATEGORIA_ID");
		HashMap <String, String> categorias 	= becCategoriaDao.getMapCategorias();		
		HashMap <String, String> mapaCarreraSe 	= mapaPlanDao.mapCarreraPlan();

		
		// PARA EL COMBO DE CENTRO DE COSTOS ---------------------------------->		
		List<ContCcosto> lisDeptos 		= contCcostoDao.listCentrosCosto(idEjercicio, " ORDER BY ID_CCOSTO");
		List<String> lisAccesos 		= new ArrayList<String>();
		if (admin) {
			lisAccesos = contCcostoDao.listClavesCentrosDeCosto(idEjercicio);
		}else {
			lisAccesos = becAccesoDao.lisPorUsuario(codigo);
		}
		
		String idCcosto 		= "0";		
		if(request.getParameter("ccosto") != null){
			idCcosto = request.getParameter("ccosto");			
		}else if((String)sesion.getAttribute("ccosto") != null){
			idCcosto = (String) sesion.getAttribute("ccosto");			
		}
		boolean existeDepto 	= false;
		for (String depto : lisAccesos) {
			if (depto.equals(idCcosto)) existeDepto = true;
		}
		if (existeDepto==false && lisAccesos.size() >= 1){
			idCcosto = lisAccesos.get(0);
		}
		
		sesion.setAttribute("ccosto", idCcosto);
		
		java.util.HashMap<String, String> niveles = new java.util.HashMap<String, String>();

		for(ContCcosto ccosto: lisDeptos){
			int puntos = 0;
			
			for(int i=0; i<ccosto.getIdCcosto().length(); i++){
				char chr = ccosto.getIdCcosto().charAt(i);
				
				if(chr == '.')puntos++;
			}
			
			if(puntos == 4){
				//do nothing
			}else{
				
				niveles.put(ccosto.getIdCcosto(), ccosto.getNombre());
			}
		}	
		
		List<BecPeriodo> periodos 			= becPeriodoDao.getAllActivos(idEjercicio, "ORDER BY PERIODO_ID DESC");
		
		String periodoId 					= (String) sesion.getAttribute("periodoBecas");
		if(sesion.getAttribute("periodoBecas") == null && periodos.size() > 0){
			periodoId = periodos.get(0).getPeriodoId();
			sesion.setAttribute("periodoBecas", periodoId);
		}
		
		if(request.getParameter("periodoBecas")!=null && fechaCambiaElPeriodo == false){
			periodoId = request.getParameter("periodoBecas");
			sesion.setAttribute("periodoBecas", periodoId);
		}
		
		boolean encontro = false;
		for(BecPeriodo periodo : periodos){
			if(periodo.getPeriodoId().equals(periodoId)){
				encontro = true; break;
			}
		}
		
		if(encontro == false && periodos.size()>0){
			periodoId = periodos.get(0).getPeriodoId();
			sesion.setAttribute("periodoBecas", periodoId);
		}
		
		String tipoBasicaInd 	= becTipoDao.getTipo(idEjercicio, "I").equals("none")?"0":becTipoDao.getTipo(idEjercicio, "I");
		String tipoBasica 		= becTipoDao.getTipo(idEjercicio, "B").equals("none")?"0":becTipoDao.getTipo(idEjercicio, "B");
		String tipoAdicional 	= becTipoDao.getTipo(idEjercicio, "A").equals("none")?"0":becTipoDao.getTipo(idEjercicio, "A");
		
		List<BecPuestoAlumno> puestosAlumno = new ArrayList<BecPuestoAlumno>();
		HashMap<String,List<BecPuestoAlumno>> mapaDePuestosAlumno = new HashMap<String,List<BecPuestoAlumno>>();
		
		for(BecPuesto puesto: puestos){
			puestosAlumno = becPuestoAlumnoDao.getListPuestosFecha(fechaPuesto, idEjercicio, idCcosto, puesto.getCategoriaId(), "ORDER BY PUESTO_ID DESC");
			mapaDePuestosAlumno.put(fechaPuesto+idEjercicio+idCcosto+puesto.getCategoriaId(), puestosAlumno);
		}
		
		List<BecAcuerdo> acuerdos = new ArrayList<BecAcuerdo>();
		HashMap<String,List<BecAcuerdo>> mapaDeAcuerdosAlumno = new HashMap<String,List<BecAcuerdo>>();
		HashMap<String,String> mapaDefolios = new HashMap<String,String>();
		HashMap<String,String> mapaDefolios2 = new HashMap<String,String>();
		for(BecPuestoAlumno pue: puestosAlumno){
			acuerdos = becAcuerdoDao.getAcuerdosAlumno(idEjercicio, pue.getCodigoPersonal(), tipoBasica+","+tipoAdicional, " AND PUESTO_ID = '"+pue.getPuestoId()+"'");
			mapaDeAcuerdosAlumno.put(idEjercicio+ pue.getCodigoPersonal(), acuerdos);
			
			String tipoB = tipoBasica;
			if(pue.getTipo().equals("I")){
				tipoB = tipoBasicaInd;
			}
			
			String folio = becAcuerdoDao.getFolio(pue.getCodigoPersonal(), idEjercicio, tipoB, idCcosto, pue.getPuestoId());			
			mapaDefolios.put(pue.getCodigoPersonal()+idEjercicio+tipoB+idCcosto+pue.getPuestoId(), folio);			
			String folio2 = "0";
			if(becAcuerdoDao.existeTipo(pue.getCodigoPersonal(), idEjercicio, tipoAdicional, idCcosto, pue.getPuestoId())) {
				folio2 = becAcuerdoDao.getFolio(pue.getCodigoPersonal(), idEjercicio, tipoAdicional, idCcosto, pue.getPuestoId());
			}			
			mapaDefolios2.put(pue.getCodigoPersonal()+idEjercicio+tipoB+idCcosto+pue.getPuestoId(), folio2);
		}
		
		HashMap<String, String> mapaAlumnosEnPuestos = alumPersonalDao.mapaAlumnosEnPuestos(idEjercicio);
		
		modelo.addAttribute("cambioDeFecha", cambioDeFecha);
		modelo.addAttribute("errorFecha", errorFecha);
		modelo.addAttribute("fechaPuesto", fechaPuesto);
		modelo.addAttribute("idEjercicio", idEjercicio);
		modelo.addAttribute("ejercicios", ejercicios);
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("admin", admin);
		modelo.addAttribute("puestos", puestos);
		modelo.addAttribute("categorias", categorias);		
		modelo.addAttribute("mapaCarreraSe", mapaCarreraSe);
		modelo.addAttribute("idCcosto", idCcosto);
		modelo.addAttribute("niveles", niveles);
		modelo.addAttribute("periodos", periodos);
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("encontro", encontro);
		modelo.addAttribute("lisDeptos", lisDeptos);
		modelo.addAttribute("tipoBasicaInd", tipoBasicaInd);
		modelo.addAttribute("tipoBasica", tipoBasica);
		modelo.addAttribute("tipoAdicional", tipoAdicional);
		modelo.addAttribute("mapaDePuestosAlumno", mapaDePuestosAlumno);
		modelo.addAttribute("mapaDeAcuerdosAlumno", mapaDeAcuerdosAlumno);
		modelo.addAttribute("mapaDefolios", mapaDefolios);
		modelo.addAttribute("mapaDefolios2", mapaDefolios2);
		modelo.addAttribute("mapaAlumnosEnPuestos", mapaAlumnosEnPuestos);
		
		return "taller/puesto/puesto";
	}	

	@RequestMapping("/taller/puesto/accion")
	public String tallerPuestoAccion(HttpServletRequest request, Model modelo){
		String ejercicioId 		= request.getParameter("EjercicioId")==null?"0":request.getParameter("EjercicioId");	
		String cCostoId 		= request.getParameter("ccosto")==null?"0":request.getParameter("ccosto");
		String categoriaId 		= request.getParameter("categoriaId")==null?"0":request.getParameter("categoriaId");
		String msj 				= request.getParameter("msj")==null?"":request.getParameter("msj");
		String accion			= request.getParameter("Accion")==null?"":request.getParameter("Accion");
		
		String periodoId 		= "";
		String codigoPersonal	= "";
		
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			if (ejercicioId.equals("0")) {
				ejercicioId = (String)sesion.getAttribute("ejercicioId");
			}else {
				sesion.setAttribute("ejercicioId", ejercicioId);
			}
			periodoId 		= (String)sesion.getAttribute("periodoBecas");
			codigoPersonal	= (String)sesion.getAttribute("codigoPersonal");
		}		
		
		List<BecCompetencia> competencias 	= becCompetenciaDao.getListAll(" ORDER BY COMPETENCIA_NOMBRE");
		List <BecCategoria> categorias 		= becCategoriaDao.getListAll(" WHERE ESTADO = 'A' ORDER BY CATEGORIA_NOMBRE");
		List<ContCcosto> ccostos 			= contCcostoDao.listCentrosCosto(ejercicioId, " ORDER BY ID_CCOSTO");
		
		boolean tieneAlumnos = becPuestoDao.tieneAlumnos(ejercicioId, cCostoId, categoriaId, periodoId);
		boolean admin        = accesoDao.getBecas(codigoPersonal);
		
		String periodoNombre = becPeriodoDao.getPeriodoNombre(periodoId); 
		String acceso		 = becAccesoDao.getUsuarioCentrosCosto(ejercicioId, codigoPersonal);
		
		BecPuesto becPuesto = new BecPuesto();

		if(becPuestoDao.existeReg(ejercicioId, cCostoId, categoriaId, periodoId)) {
			becPuesto = becPuestoDao.mapeaRegId(ejercicioId, cCostoId, categoriaId, periodoId);
		}else {
			becPuesto.setIdEjercicio(ejercicioId);
	 		becPuesto.setIdCcosto(cCostoId);
	 		becPuesto.setCategoriaId(categoriaId);
	 		becPuesto.setPeriodoId(periodoId);
		}
		
		modelo.addAttribute("competencias", competencias);
		modelo.addAttribute("categorias", categorias);
		modelo.addAttribute("ccostos", ccostos);
		modelo.addAttribute("becPuesto", becPuesto);
		modelo.addAttribute("ejercicioId", ejercicioId);
		modelo.addAttribute("msj", msj);
		modelo.addAttribute("accion", accion);
		modelo.addAttribute("periodoNombre", periodoNombre);
		modelo.addAttribute("tieneAlumnos", tieneAlumnos);
		modelo.addAttribute("admin", admin);
		modelo.addAttribute("acceso", acceso);
		
		return "taller/puesto/accion";
	}

	@RequestMapping("/taller/puesto/grabar")
	public String tallerPuestoGrabar(HttpServletRequest request, Model modelo){
		
		BecPuesto becPuesto 	= new BecPuesto();		
		String periodoId 		= "0";		
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion != null){			
			periodoId 		= (String)sesion.getAttribute("periodoBecas");			
		}		
		
		String ejercicioId 		= request.getParameter("ejercicioId")==null?"0":request.getParameter("ejercicioId");	
		
		String ccosto 			= request.getParameter("ccosto")==null?"0":request.getParameter("ccosto");
		String categoriaId 		= request.getParameter("categoria")==null?"0":request.getParameter("categoria");
		String justificacion 	= request.getParameter("justificacion")==null?"0":request.getParameter("justificacion");
		String funcion 			= request.getParameter("funcion")==null?"0":request.getParameter("funcion");
		String otrasComp 		= request.getParameter("otrasComp")==null?"0":request.getParameter("otrasComp");
		String certificaciones 	= request.getParameter("certificaciones")==null?"0":request.getParameter("certificaciones");
		String cantidad 		= request.getParameter("cantidad")==null?"0":request.getParameter("cantidad");
		String estado 			= request.getParameter("estado")==null?"0":request.getParameter("estado");
		
		becPuesto.setIdEjercicio(ejercicioId);
		becPuesto.setPeriodoId(periodoId);
		becPuesto.setIdCcosto(ccosto);
		becPuesto.setCategoriaId(categoriaId);
		becPuesto.setJustificacion(justificacion);
		becPuesto.setFuncion(funcion);
		becPuesto.setOtrasComp(otrasComp);
		
		List<BecCompetencia> competencias 	= becCompetenciaDao.getListAll("ORDER BY 1");
		
		String strCompetencias = "";
		for(BecCompetencia comp: competencias){
			String tmp = request.getParameter("competencia"+comp.getCompetenciaId());
			
			if(tmp != null && !tmp.equals("null")){
				strCompetencias+=comp.getCompetenciaId()+",";
			}
		}
		
		becPuesto.setCompetencias(strCompetencias);
		becPuesto.setCertificaciones(certificaciones);
		becPuesto.setCantidad(cantidad);
		becPuesto.setEstado(estado);
		
		String msj = "";
		
		if(becPuestoDao.existeReg(ejercicioId, ccosto, categoriaId, periodoId)){
			if(becPuestoDao.updateReg(becPuesto)){
				msj = "0";
			}else{
				msj = "1";
			}
		}else{
			if(becPuestoDao.insertReg(becPuesto)){
				msj = "2";
			}else{
				msj = "3";
			}
		}
				
		return "redirect:/taller/puesto/accion?EjercicioId="+ejercicioId+"&ccosto="+ccosto+"&categoriaId="+categoriaId+"&msj="+msj;
	}
	@RequestMapping("/taller/puesto/borrar")
	public String tallerPuestoBorrar(HttpServletRequest request, Model modelo){
		String ejercicioId 		= request.getParameter("ejercicioId")==null?"0":request.getParameter("ejercicioId");	
		String periodoId 		= request.getParameter("periodoId")==null?"0":request.getParameter("periodoId");
		String ccosto 			= request.getParameter("ccosto")==null?"0":request.getParameter("ccosto");
		String categoriaId 		= request.getParameter("categoria")==null?"0":request.getParameter("categoria");
		
		String msj = "";
		
		if(!becPuestoDao.existeReg(ejercicioId, ccosto, categoriaId, periodoId)){
			msj = "<div class='alert alert-error'><button class='close' data-dismiss='alert'>&times;</button> Este Registro no existe</div>";
		}else{
			if(becPuestoDao.deleteReg(ejercicioId, ccosto, categoriaId, periodoId)){
				msj = "<div class='alert alert-success'><button class='close' data-dismiss='alert'>&times;</button> El Registro se Eliminó correctamente</div>";
			}else{
				msj = "<div class='alert alert-error'><button class='close' data-dismiss='alert'>&times;</button> Ocurrió un error al eliminar el registro</div>";
			}
		}
		
		return "redirect:/taller/puesto/accion?EjercicioId="+ejercicioId+"&ccosto="+ccosto+"&categoriaId="+categoriaId+"&msj="+msj;
	}
	
	@ResponseBody	
	@RequestMapping("/taller/puesto/getFechasConvenio")
	public String tallerPuestoGetFechasConvenio(HttpServletRequest request){
		
		String codigoAlumno 	= request.getParameter("Matricula")==null?"0":request.getParameter("Matricula");
		String planId	 		= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		
		HashMap<Integer, String> fechas = becParametroDao.getMapFechas();
		String fechaIni = fechas.get(3);
		String fechaFin = fechas.get(4);
		
		//NIVEL DEL ALUMNO
		String nivel = catCarreraDao.getNivelId(mapaPlanDao.getCarreraId(planId));
		if(nivel.equals("1")){//PREPA
			fechaIni = fechas.get(1);
			fechaFin = fechas.get(2);
		}
		
		return fechaIni+"&"+fechaFin;
	}
	
	@ResponseBody
	@RequestMapping("/taller/puesto/getNombreAlumno")
	public String tallerPuestoGetNombreAlumno(HttpServletRequest request){
		//enviarConEnoc(request,"Error-aca.ControllerTaller|tallerPuestoGetNombreAlumno");
		String matricula 	= request.getParameter("matricula")==null?"0":request.getParameter("matricula");
		String nombre 		= alumPersonalDao.getNombreAlumno(matricula, "NOMBRE");
		
		List<String> planes = alumPlanDao.getPlanesAlumno( matricula);
		String select = "";
		String nombrePlan = "";
		
		for(String plan : planes){
			nombrePlan = mapaPlanDao.getNombrePlan(plan);
			select += "<option value="+plan+">"+plan+"-"+nombrePlan+"</option>";
		}		
		if(nombre.equals("0000000"))nombre = "Numero de Matrícula No Válido";		
		
		return nombre+"&"+select;
	}
	
	@RequestMapping("/taller/puesto/addBecas")
	public String tallerPuestoAddBecas(HttpServletRequest request, Model modelo){		
		
		String codigoPersonal 	= "0";	
		String periodoId 		= "0";
		String fechaPuesto 		= aca.util.Fecha.getHoy();
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			codigoPersonal 		= (String)sesion.getAttribute("codigoPersonal");
			periodoId 			= (String)sesion.getAttribute("periodoBecas");
			fechaPuesto 		= (String)sesion.getAttribute("fechaPuesto");			
		}	
		String puestoId			= request.getParameter("puestoId")==null?"0":request.getParameter("puestoId");
		String idEjercicio 		= request.getParameter("idEjercicio")==null?"0":request.getParameter("idEjercicio");
		String idCcosto 		= request.getParameter("idCcosto")==null?"0":request.getParameter("idCcosto");
		String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		String mensaje			= "-";
		
		//TRAER LA INFORMACION DEL PUESTO QUE SELECCIONO DE LA PAGINA ANTERIOR		
		BecPuestoAlumno becPuestoAlumno = becPuestoAlumnoDao.mapeaRegId(puestoId);
		String tipoNombre 		= alumAcademicoDao.getTipoAlumno(becPuestoAlumno.getCodigoPersonal());			
		boolean esAdmin			= accesoDao.getBecas(codigoPersonal);		
		String alumnoNombre 	= alumPersonalDao.getNombreAlumno(becPuestoAlumno.getCodigoPersonal(), "NOMBRE");
		String nivel 			= catCarreraDao.getNivelId(alumPlanDao.getCarreraId(becPuestoAlumno.getCodigoPersonal()));
		
		//BASICA
		String Basica = "B";
		if(becPuestoAlumno.getTipo().equals("I")){//INDUSTRIAL
			Basica = "I";
		}
		
		String BasicaImporteYHoras	= Basica;
		if(becPuestoAlumno.getTipo().equals("M")){//MAESTRIA O POSTGRADO
			BasicaImporteYHoras 	= "M";
		}else if(becPuestoAlumno.getTipo().equals("P")){//PREINDUSTRIAL
			BasicaImporteYHoras 	= "P";
		}
		
		String horasBasicas = "";
		String horasAdicionales 	= "";		
		//NIVEL DEL ALUMNO	
		if(nivel.equals("1")){
			//PREPA
			horasBasicas 			= becTipoDao.getHorasPrepa(idEjercicio, BasicaImporteYHoras);
			horasAdicionales 		= becTipoDao.getHorasPrepa(idEjercicio, "A");
		}else{
			// UNIVERSITARIO
			horasBasicas 			= becTipoDao.getHoras(idEjercicio, BasicaImporteYHoras);
			horasAdicionales 		= becTipoDao.getHoras(idEjercicio, "A");
		}
		
		String importeBasico 		= becTipoDao.getImporte(idEjercicio, BasicaImporteYHoras);
		String tipoBasica 			= becTipoDao.getTipo(idEjercicio, Basica);
		String tipoAdicional 		= becTipoDao.getTipo(idEjercicio, "A");
		BecTipo becTipoBasico		= becTipoDao.mapeaRegId(idEjercicio, tipoBasica);
		String meses = becTipoBasico.getMeses();
		if(meses!=null && !meses.equals("null")){
			meses = meses.substring(1);
			meses = meses.replace('-', ',');
		}else{
			meses = "";
		}		
		String presupuestoBasica 	= ccpPresupuestoDao.getSumaCcosto(becTipoBasico.getPorcentaje(), meses, becTipoBasico.getCuenta(), idEjercicio, idCcosto);	
		
		BecTipo becTipoAdicional	= becTipoDao.mapeaRegId(idEjercicio, tipoAdicional);		
		String meses2 = becTipoAdicional.getMeses();
		if(meses2!=null && !meses2.equals("null")){
			meses2 = meses.substring(1);
			meses2 = meses.replace('-', ',');
		}else{
			meses2 = "";
		}		
		String presupuestoAdicional = ccpPresupuestoDao.getSumaCcosto(becTipoAdicional.getPorcentaje(), meses2, becTipoAdicional.getCuenta(), idEjercicio, idCcosto);
		
		//PRESUPUESTO USADO BECA BASICA	
		String usadoBasica 			=  becAcuerdoDao.getBecaBasicaUsado(idEjercicio, tipoBasica, tipoAdicional, idCcosto, fechaPuesto, periodoId);
		//PRESUPUESTO USADO BECA ADICIONAL
		String usadoAdicional 		=  becAcuerdoDao.getBecaAdicionalUsado(idEjercicio, tipoAdicional, idCcosto, fechaPuesto, periodoId);
		
		double basicaDisponible 	= Double.parseDouble(presupuestoBasica)-Double.parseDouble(usadoBasica);
		double adicionalDisponible 	= Double.parseDouble(presupuestoAdicional)-Double.parseDouble(usadoAdicional);
		
		if(accion.equals("1")){//GRABAR BECA BASICA
			BecAcuerdo becAcuerdo = new BecAcuerdo();
			becAcuerdo.setCodigoPersonal(becPuestoAlumno.getCodigoPersonal());
			becAcuerdo.setTipo( tipoBasica );
			becAcuerdo.setFecha(aca.util.Fecha.getHoy());
			becAcuerdo.setEnsenanza(importeBasico);
			becAcuerdo.setHoras(horasBasicas);
			becAcuerdo.setMatricula("0");
			becAcuerdo.setInternado("0");
			becAcuerdo.setPromesa(request.getParameter("promesa")==null?"0":request.getParameter("promesa"));
			becAcuerdo.setValor("C");
			becAcuerdo.setEstado("A");
			becAcuerdo.setFolio(becAcuerdoDao.maximoReg(becAcuerdo.getCodigoPersonal()));
			becAcuerdo.setIdEjercicio(idEjercicio);
			becAcuerdo.setIdCcosto(idCcosto);
			becAcuerdo.setPuestoId(puestoId);
			becAcuerdo.setVigencia(request.getParameter("vigenciaBasica"));
			becAcuerdo.setTipoadicional("X");
			becAcuerdo.setUsuario(codigoPersonal);
			
			double cantidad = Double.parseDouble(importeBasico)*Double.parseDouble(horasBasicas);
			
			if( cantidad > basicaDisponible && esAdmin == false && becPuestoAlumno.getTipo().equals("B") ){
				mensaje = "<div class='alert alert-danger'>No se puede grabar porque esta beca básica sobrepasa el presupuesto disponible</div>";
			}else{
				
				if(!becAcuerdoDao.existeTipo(becPuestoAlumno.getCodigoPersonal(), idEjercicio, tipoBasica, idCcosto, puestoId)){
					if( becAcuerdoDao.insertReg(becAcuerdo)){
						mensaje = "<div class='alert alert-success'>Se Guardó Correctamente</div>";
					}else{
						mensaje = "<div class='alert alert-danger'>Ocurrió un Error al Guardar</div>";
					}
				}else{
					mensaje = "<div class='alert alert-danger'>Este alumno ya tiene una Beca Básica</div>";
				}				
			}
			
		}else if(accion.equals("2")){
			String folio 		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
			String codigoAlumno = becPuestoAlumno.getCodigoPersonal();
			if(becAcuerdoDao.existeReg(codigoAlumno, folio)){
				if(becAcuerdoDao.deleteReg(folio, codigoAlumno)){
					mensaje = "<div class='alert alert-success'>Se Eliminó Correctamente</div>";
				}else{
					mensaje = "<div class='alert alert-danger'>Ocurrió un Error al Eliminar</div>";
				}
			}			
		}else if(accion.equals("3")){// GRABAR BECA ADICIONAL
			BecAcuerdo becAcuerdo = new BecAcuerdo();
			becAcuerdo.setCodigoPersonal(becPuestoAlumno.getCodigoPersonal());
			becAcuerdo.setTipo( tipoAdicional );
			becAcuerdo.setFecha(aca.util.Fecha.getHoy());
			becAcuerdo.setEnsenanza(request.getParameter("porcentaje"));
			becAcuerdo.setHoras(horasAdicionales);
			becAcuerdo.setMatricula("0");
			becAcuerdo.setInternado("0");
			becAcuerdo.setValor("P");
			becAcuerdo.setEstado("A");
			becAcuerdo.setFolio(becAcuerdoDao.maximoReg(becAcuerdo.getCodigoPersonal()));
			becAcuerdo.setIdEjercicio(idEjercicio);
			becAcuerdo.setIdCcosto(idCcosto);
			becAcuerdo.setPuestoId(puestoId);
			becAcuerdo.setVigencia(request.getParameter("vigenciaAdicional"));
			becAcuerdo.setTipoadicional(request.getParameter("tipoadicional"));
			becAcuerdo.setPromesa(request.getParameter("promesa")==null?"0":request.getParameter("promesa"));
			becAcuerdo.setUsuario(codigoPersonal);
			
			if(becAcuerdo.getTipoadicional().equals("D")){
				becAcuerdo.setPromesa("");
				becAcuerdo.setValor("C");
				becAcuerdo.setEnsenanza(request.getParameter("cantidadfija"));
			}
			
			double cantidad = 0;
			if(becAcuerdo.getTipoadicional().equals("D")){
				cantidad = Double.parseDouble(becAcuerdo.getEnsenanza());
			}else{
				cantidad = (Double.parseDouble(becAcuerdo.getEnsenanza())/100)*Double.parseDouble(becAcuerdo.getPromesa());
			}
			
			if( cantidad > adicionalDisponible && esAdmin == false){
				mensaje = "<div class='alert alert-danger'>No se puede grabar porque esta beca adicional sobrepasa el presupuesto disponible</div>";
			}else{		
			
				if(!becAcuerdoDao.existeTipo(becPuestoAlumno.getCodigoPersonal(), idEjercicio, tipoAdicional, idCcosto, puestoId)){
					if( becAcuerdoDao.insertReg(becAcuerdo) ){
						mensaje = "<div class='alert alert-success'>Se Guardó Correctamente</div>";
					}else{
						mensaje = "<div class='alert alert-danger'>Ocurrió un Error al Guardar</div>";
					}
				}else{
					mensaje = "<div class='alert alert-danger'>Este alumno ya tiene una Beca Adicional</div>";
				}			
			}
			
		}else if(accion.equals("4")){// ELIMINAR BECA ADICIONAL
			String folio 			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
			String codigoAlumno 	= becPuestoAlumno.getCodigoPersonal();
			if(becAcuerdoDao.existeReg(codigoAlumno, folio)){
				if(becAcuerdoDao.deleteReg(folio, codigoAlumno)){
					mensaje = "<div class='alert alert-success'>Se Eliminó Correctamente</div>";
				}else{
					mensaje = "<div class='alert alert-danger'>Ocurrió un Error al Eliminar</div>";
				}
			}			
		}else if(accion.equals("5")){
			//ASOCIAR BECA ADICIONAL Y ACTUALIZAR HORAS DEL ACUERDO
			String folio 		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
			String codigoAlumno = becPuestoAlumno.getCodigoPersonal();
			
			if(becAcuerdoDao.updatePuestoId(becPuestoAlumno.getPuestoId(), folio, codigoAlumno)){
				becAcuerdoDao.updateHorasDelPuesto(codigoAlumno, becPuestoAlumno.getPuestoId(), horasBasicas);
				mensaje = "<div class='alert alert-success'>Se Asoció Correctamente</div>";
			}else{
				mensaje = "<div class='alert alert-danger'>Ocurrió un Error al Asociar</div>";
			}
		}else if(accion.equals("6")){
			//ELIMINAR ASOCIACION BECA ADICIONAL
			String folio 		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
			String codigoAlumno = becPuestoAlumno.getCodigoPersonal();
			
			if(becAcuerdoDao.updatePuestoId("0", folio, codigoAlumno)){
				mensaje = "<div class='alert alert-success'>Se Desasoció Correctamente</div>";
			}else{
				mensaje = "<div class='alert alert-danger'>Ocurrió un Error al Desasociar</div>";
			}
		}else if(accion.equals("7")){//EDITAR NUMERO HORAS BECA BASICA O ADICIONAL
			String folio 			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
			String codigoAlumno 	= becPuestoAlumno.getCodigoPersonal();			
			String numHoras 		= request.getParameter("numHoras")==null?"0":request.getParameter("numHoras");
			
			BecAcuerdo becAcuerdo 	= becAcuerdoDao.mapeaRegId(folio, codigoAlumno);			
			// Busca el tipo de acuerdo a actualizar
			String tipoAcuerdo 		= becTipoDao.getAcuerdo(idEjercicio, becAcuerdo.getTipo());
			
			horasBasicas 	= numHoras;
			becAcuerdo.setHoras(numHoras);
			
			// Modifica las horas de todos los acuerdos del alumno en el puesto		
			if (tipoAcuerdo.equals("B")||tipoAcuerdo.equals("I")){
				if (becAcuerdoDao.updateHorasDelPuesto(codigoAlumno, becPuestoAlumno.getPuestoId(), numHoras)){
					mensaje = "<div class='alert alert-success'>Se actualizó el Número de horas Correctamente</div>";
				}else{
					mensaje = "<div class='alert alert-success'>Ocurrió un error al actualizar el Número de horas</div>";
				}
			// Modifica unicamente las horas del acuerdo adicional	
			}else{				
				if(becAcuerdoDao.updateHoras(numHoras, folio, codigoAlumno)){
					mensaje = "<div class='alert alert-success'>Se Actualizo el Número de horas Correctamente</div>";
				}else{
					mensaje = "<div class='alert alert-success'>Ocurrió un error al actualizar el Número de horas</div>";
				}
			}		
		}
		
		List<BecAcuerdo> lisAcuerdos = becAcuerdoDao.getAcuerdosAlumno(idEjercicio, becPuestoAlumno.getCodigoPersonal(), tipoBasica+","+tipoAdicional, "");
		
		//EL ALUMNO YA TIENE ASIGNADA UNA BECA BASICA AQUI?
		boolean existeBecaBasicaDelAlumno = false;
		if(becAcuerdoDao.existeTipo(becPuestoAlumno.getCodigoPersonal(), idEjercicio, tipoBasica, idCcosto, puestoId)){
			existeBecaBasicaDelAlumno = true;
		}
		
		//HAY UNA BECA BASICA DADA DE ALTA EN EL EJERCICIO ACTUAL
		boolean becaBasicaDisponible = false;
		if(!becTipoDao.getTipo( idEjercicio, Basica).equals("none")){
			becaBasicaDisponible = true;
		}
		
		//EL ALUMNO YA TIENE ASIGNADA UNA BECA ADICIONAL AQUI?
		boolean existeBecaAdicionalDelAlumno = false;
		if(becAcuerdoDao.existeTipo(becPuestoAlumno.getCodigoPersonal(), idEjercicio, tipoAdicional, idCcosto, puestoId)){
			existeBecaAdicionalDelAlumno = true;
		}
		
		//HAY UNA BECA BASICA DADA DE ALTA EN EL EJERCICIO ACTUAL
		boolean becaAdicionalDisponible = false;
		if(!becTipoDao.getTipo(idEjercicio, "A").equals("none")){
			becaAdicionalDisponible = true;
		}	
		
		// Refresca los valores en caso de relizar alguna operaión de grabar o borrar en las acciones de arriba
		usadoBasica 			= becAcuerdoDao.getBecaBasicaUsado(idEjercicio, tipoBasica, tipoAdicional, idCcosto, fechaPuesto, periodoId);
		usadoAdicional 			= becAcuerdoDao.getBecaAdicionalUsado(idEjercicio, tipoAdicional, idCcosto, fechaPuesto, periodoId);	
		basicaDisponible 		= Double.parseDouble(presupuestoBasica)-Double.parseDouble(usadoBasica);
		adicionalDisponible 	= Double.parseDouble(presupuestoAdicional)-Double.parseDouble(usadoAdicional);
		
		
		String folioBasico 				= becAcuerdoDao.getFolio(becPuestoAlumno.getCodigoPersonal(), idEjercicio, tipoBasica, idCcosto, puestoId);
		BecAcuerdo becAcuerdoBasico 	= becAcuerdoDao.mapeaRegId(folioBasico, becPuestoAlumno.getCodigoPersonal());		
		String tiposAlumnoAdicional 	= becTipoDao.getTipoAlumno(idEjercicio, tipoAdicional);
		String tipoAlumno 				= alumAcademicoDao.getTipoAlumnoId(becPuestoAlumno.getCodigoPersonal());
		
		String folioAdicional 			= becAcuerdoDao.getFolio(becPuestoAlumno.getCodigoPersonal(), idEjercicio, tipoAdicional, idCcosto, puestoId);
		BecAcuerdo becAcuerdoAdicional 	= becAcuerdoDao.mapeaRegId(folioAdicional, becPuestoAlumno.getCodigoPersonal());
		
		HashMap<String, String> mapaDepositos	= becPuestoAlumnoDao.getColportaje();
		HashMap<String,BecFija> mapaFija 		= becFijaDao.getMapAll("");
		HashMap<String,ContCcosto> mapaDeptos 	= contCcostoDao.mapaCentrosCosto(idEjercicio);
		HashMap<String,String> mapaTipos 		= becTipoDao.mapaTipos(idEjercicio);
		
		modelo.addAttribute("esAdmin", esAdmin);
		modelo.addAttribute("alumnoNombre", alumnoNombre);
		modelo.addAttribute("tipoNombre", tipoNombre);
		modelo.addAttribute("nivel", nivel);
		modelo.addAttribute("horasBasicas", horasBasicas);
		modelo.addAttribute("horasAdicionales", horasAdicionales);
		modelo.addAttribute("importeBasico", importeBasico);
		modelo.addAttribute("tipoBasica", tipoBasica);
		modelo.addAttribute("tipoAdicional", tipoAdicional);
		modelo.addAttribute("becTipoBasico", becTipoBasico);
		modelo.addAttribute("becTipoAdicional", becTipoAdicional);
		modelo.addAttribute("presupuestoBasica", presupuestoBasica);
		modelo.addAttribute("presupuestoAdicional", presupuestoAdicional);
		modelo.addAttribute("usadoBasica", usadoBasica);
		modelo.addAttribute("usadoAdicional", usadoAdicional);
		modelo.addAttribute("mensaje", mensaje);		
		modelo.addAttribute("becPuestoAlumno", becPuestoAlumno);
		modelo.addAttribute("tiposAlumnoAdicional", tiposAlumnoAdicional);
		modelo.addAttribute("tipoAlumno", tipoAlumno);
		modelo.addAttribute("becAcuerdoBasico", becAcuerdoBasico);
		modelo.addAttribute("becAcuerdoAdicional", becAcuerdoAdicional);
		modelo.addAttribute("folioBasico", folioBasico);
		modelo.addAttribute("folioAdicional", folioAdicional);		
		modelo.addAttribute("existeBecaBasicaDelAlumno", existeBecaBasicaDelAlumno);
		modelo.addAttribute("becaBasicaDisponible", becaBasicaDisponible);
		modelo.addAttribute("existeBecaAdicionalDelAlumno", existeBecaAdicionalDelAlumno);
		modelo.addAttribute("becaAdicionalDisponible", becaAdicionalDisponible);
		
		modelo.addAttribute("lisAcuerdos", lisAcuerdos);
		modelo.addAttribute("mapaDepositos", mapaDepositos);
		modelo.addAttribute("mapaFija", mapaFija);
		modelo.addAttribute("mapaDeptos", mapaDeptos);
		modelo.addAttribute("mapaTipos", mapaTipos);
		
		return "taller/puesto/addBecas";
	}	
	
	@RequestMapping("/taller/puesto/addPuestoAlumno")
	public String tallerPuestoAddPuestoAlumno(HttpServletRequest request, Model modelo){
		
		String codigoPersonal 	= "0";
		String periodoId 		= "0";
		String periodoNombre 	= "-";
		String fechaPuesto 		= aca.util.Fecha.getHoy();
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			codigoPersonal 		= (String)sesion.getAttribute("codigoPersonal");
			periodoId 			= (String)sesion.getAttribute("periodoBecas");
			periodoNombre 		= becPeriodoDao.getPeriodoNombre(periodoId);
			if( sesion.getAttribute("fechaPuesto") == null ){
				sesion.setAttribute("fechaPuesto", aca.util.Fecha.getHoy());
			}else {
				fechaPuesto = (String)sesion.getAttribute("fechaPuesto"); 
			}
		}		
		
		String idEjercicio		= request.getParameter("idEjercicio")==null?"0":request.getParameter("idEjercicio");
		String idCcosto   		= request.getParameter("idCcosto")==null?"0":request.getParameter("idCcosto");
		String tipoAcuerdo 		= becTipoDao.getTipo(idEjercicio, request.getParameter("tipo")==null?"0":request.getParameter("tipo"));
		String codigoAlumno		= request.getParameter("codigoAlumno")==null?"0":request.getParameter("codigoAlumno");
		String puestoId 		= request.getParameter("puestoId")==null?"0":request.getParameter("puestoId");
		
		boolean existePuesto 	= false;
		BecPuestoAlumno becPuestoAlumno = new BecPuestoAlumno();
		if( becPuestoAlumnoDao.existeReg(puestoId)){
			becPuestoAlumno 	= becPuestoAlumnoDao.mapeaRegId(puestoId);
			codigoAlumno 		= becPuestoAlumno.getCodigoPersonal(); 
			existePuesto		= true;
		}
		
		
		boolean admin       = accesoDao.getBecas( codigoPersonal);		
		BecPlazas becPlazas = becPlazasDao.mapeaRegId(idEjercicio, idCcosto, periodoId);
		int cantidadT 		= Integer.parseInt(becPuestoDao.getCantidadBecasUsadas( idEjercicio, idCcosto, "T", fechaPuesto, periodoId));
		int cantidadI 		= Integer.parseInt(becPuestoDao.getCantidadBecasUsadas( idEjercicio, idCcosto, "I", fechaPuesto, periodoId));
		int cantidadB 		= Integer.parseInt(becPuestoDao.getCantidadBecasUsadas( idEjercicio, idCcosto, "B", fechaPuesto, periodoId));
		int cantidadP 		= Integer.parseInt(becPuestoDao.getCantidadBecasUsadas( idEjercicio, idCcosto, "P", fechaPuesto, periodoId));
		int cantidadM 		= Integer.parseInt(becPuestoDao.getCantidadBecasUsadas( idEjercicio, idCcosto, "M", fechaPuesto, periodoId));
		
		List<AlumPlan> lisPlanes 					= alumPlanDao.getLista(codigoAlumno, " ORDER BY ENOC.ALUM_PLAN.F_INICIO DESC");
		HashMap<String,ContCcosto> mapaCostos 		= contCcostoDao.mapaCentrosCosto(idEjercicio);
		HashMap<String,MapaPlan> mapaPlanes 		= mapaPlanDao.mapPlanes("'A','V','I'");
		
		modelo.addAttribute("codigoPersonal", codigoPersonal);
		modelo.addAttribute("admin", admin);		
		modelo.addAttribute("becPuestoAlumno", becPuestoAlumno);
		modelo.addAttribute("becPlazas", becPlazas);
		modelo.addAttribute("cantidadT", cantidadT);
		modelo.addAttribute("cantidadI", cantidadI);
		modelo.addAttribute("cantidadB", cantidadB);
		modelo.addAttribute("cantidadP", cantidadP);
		modelo.addAttribute("cantidadM", cantidadM);
		modelo.addAttribute("tipoAcuerdo", tipoAcuerdo);		
		modelo.addAttribute("periodoNombre", periodoNombre);
		modelo.addAttribute("existePuesto", existePuesto);
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("mapaCostos", mapaCostos);	
				
		return "taller/puesto/addPuestoAlumno";
	}	
	
	@Transactional
	@RequestMapping("/taller/puesto/grabarPuestoAlumno")
	public String tallerPuestoGrabarPuestoAlumno(HttpServletRequest request, Model modelo){
		String codigoPersonal 	= "0";
		String periodoId 		= "0";		
		String fechaPuesto 		= aca.util.Fecha.getHoy();
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			codigoPersonal 		= (String)sesion.getAttribute("codigoPersonal");
			periodoId 			= (String)sesion.getAttribute("periodoBecas");			
			if( sesion.getAttribute("fechaPuesto") == null ){
				sesion.setAttribute("fechaPuesto", aca.util.Fecha.getHoy());
			}else {
				fechaPuesto = (String)sesion.getAttribute("fechaPuesto"); 
			}
		}		
		String codigoAlumno		= request.getParameter("alumno")==null?"0":request.getParameter("alumno");
		String idEjercicio		= request.getParameter("idEjercicio")==null?"0":request.getParameter("idEjercicio");
		String idCcosto   		= request.getParameter("idCcosto")==null?"0":request.getParameter("idCcosto");
		String categoriaId		= request.getParameter("categoriaId")==null?"0":request.getParameter("categoriaId");
		String puestoId 		= request.getParameter("puestoId")==null?"0":request.getParameter("puestoId");
		BecPlazas becPlazas 	= becPlazasDao.mapeaRegId(idEjercicio, idCcosto, periodoId);
		String mensaje			= "-";
		
		HashMap<String,ContCcosto> mapaCostos 		= contCcostoDao.mapaCentrosCosto(idEjercicio);
		
		BecPuestoAlumno becPuestoAlumno = new BecPuestoAlumno();
		
		becPuestoAlumno.setIdEjercicio(idEjercicio);
		becPuestoAlumno.setIdCcosto(idCcosto);
		becPuestoAlumno.setCategoriaId(categoriaId);
		becPuestoAlumno.setCodigoPersonal(codigoAlumno);
		becPuestoAlumno.setFechaIni(request.getParameter("fechaIni"));
		becPuestoAlumno.setFechaFin(request.getParameter("fechaFin"));
		becPuestoAlumno.setTipo(request.getParameter("tipo")==null?"0":request.getParameter("tipo"));
		becPuestoAlumno.setUsuario(codigoPersonal);
		becPuestoAlumno.setEstado("I");
		becPuestoAlumno.setPeriodoId(periodoId);
		becPuestoAlumno.setPlanId(request.getParameter("PlanId")==null?"-":request.getParameter("PlanId"));
		becPuestoAlumno.setDescripcion(request.getParameter("descripcion")==null?"-":request.getParameter("descripcion"));
		
		boolean existe = false;
		if(alumPersonalDao.existeReg(codigoAlumno)){
			becPuestoAlumno.setEstado("P");
			existe = true;
		}else {
			mensaje = "<div class='alert alert-danger'>La Matricula "+becPuestoAlumno.getCodigoPersonal()+" No Existe</div>";
		}
		
		boolean existePuesto = becPuestoAlumnoDao.existeReg(puestoId);
		
		
		if(existe){	
			if( existePuesto==false && (
				becPuestoAlumnoDao.existeFechaBetween( becPuestoAlumno.getCodigoPersonal(), becPuestoAlumno.getFechaIni(), becPuestoAlumno.getPuestoId()) || 	
				becPuestoAlumnoDao.existeFechaBetween( becPuestoAlumno.getCodigoPersonal(), becPuestoAlumno.getFechaFin(), becPuestoAlumno.getPuestoId()) || 	
				becPuestoAlumnoDao.existeFechaINIBetween( becPuestoAlumno.getCodigoPersonal(), becPuestoAlumno.getFechaIni(), becPuestoAlumno.getFechaFin(), becPuestoAlumno.getPuestoId()) || 	
				becPuestoAlumnoDao.existeFechaFINBetween( becPuestoAlumno.getCodigoPersonal(), becPuestoAlumno.getFechaIni(), becPuestoAlumno.getFechaFin(), becPuestoAlumno.getPuestoId())
			)
			){					
				BecPuestoAlumno becPuestoAlumnoActual	= becPuestoAlumnoDao.mapeaPuestoActual(becPuestoAlumno.getCodigoPersonal());
				String nombreCosto = "-";
				if(mapaCostos.containsKey(becPuestoAlumnoActual.getIdCcosto())){
					nombreCosto = mapaCostos.get( becPuestoAlumnoActual.getIdCcosto() ).getNombre();
				}
				mensaje = "<div class='alert alert-danger'>Este alumno ya tiene un puesto asignado que se encuentra dentro de las fechas que seleccionaste. Su puesto actual es en: <strong>"+  nombreCosto +"</strong></div>";
			}else{	
				
				// UPDATE
				if( existePuesto ){	
					becPuestoAlumno.setPuestoId(puestoId);
					if(becPuestoAlumnoDao.updateReg(becPuestoAlumno)){
						mensaje = "<div class='alert alert-success'>Se actualizó correctamente</div>";
					}else{
						mensaje = "<div class='alert alert-danger'>Ocurrió un error al actualizar</div>";
					}				
				}else{					
					// INSERT				
					int cantidad 	= Integer.parseInt(becPuestoDao.getCantidadBecasUsadas( idEjercicio, idCcosto, becPuestoAlumno.getTipo(), fechaPuesto, periodoId));					
					boolean error = false;
					if(becPuestoAlumno.getTipo().equals("B")){
						if( cantidad+1 > Integer.parseInt(becPlazas.getNumPlazas()) ){							
							mensaje = "<div class='alert alert-danger'>No grabó porque ha excedido el límite de plazas Básicas disponibles</div>";
							error = true;
						}	
					}else if(becPuestoAlumno.getTipo().equals("I")){
						if( cantidad+1 > Integer.parseInt(becPlazas.getNumIndustriales()) ){
							mensaje = "<div class='alert alert-danger'>No grabó porque ha excedido el limite de plazas Institucionales disponibles</div>";
							error = true;
						}
					}else if(becPuestoAlumno.getTipo().equals("T")){
						if( cantidad+1 > Integer.parseInt(becPlazas.getNumTemporales()) ){
							mensaje = "<div class='alert alert-danger'>No grabó porque ha excedido el limite de plazas Temporales disponibles</div>";
							error = true;
						}
					}else if(becPuestoAlumno.getTipo().equals("P")){
						if( cantidad+1 > Integer.parseInt(becPlazas.getNumPreIndustriales()) ){
							mensaje = "<div class='alert alert-danger'>No grabó porque ha excedido el limite de plazas Preindustriales disponibles</div>";
							error = true;
						}
					}else if(becPuestoAlumno.getTipo().equals("M")){
						if( cantidad+1 > Integer.parseInt(becPlazas.getNumPosgrado()) ){
							mensaje = "<div class='alert alert-danger'>No grabó porque ha excedido el limite de plazas de Postgrado disponibles</div>";
							error = true;
						}
					}
					
					String maximo = becPuestoAlumnoDao.maximoReg(idEjercicio);			
					puestoId = idEjercicio+maximo; 
					becPuestoAlumno.setPuestoId(idEjercicio+maximo);
					
					if((error == false || codigoPersonal.equals("9800308") || codigoPersonal.equals("1120329"))  && becPuestoAlumnoDao.insertReg(becPuestoAlumno)){
						BecPuesto becPuesto = new BecPuesto();					
						if( becPuestoDao.existeReg( idEjercicio, idCcosto, categoriaId, periodoId) ){
							becPuesto = becPuestoDao.mapeaRegId(idEjercicio, idCcosto, categoriaId, periodoId);
							becPuesto.setCantidad((Integer.parseInt(becPuesto.getCantidad())+1)+"");
							if( becPuestoDao.updateReg(becPuesto) ){
								mensaje = "<div class='alert alert-success'>Se guardó correctamente</div>";
							}							
 						}				
					}else{
						puestoId = "0";
					}							
				}			
			}
		}
		
		return "redirect:/taller/puesto/addPuestoAlumno?idEjercicio="+idEjercicio+"&idCcosto="+idCcosto+"&categoriaId="+categoriaId+"&puestoId="+puestoId+"&mensaje="+mensaje;
	}	

	
	@Transactional
	@RequestMapping("/taller/puesto/borrarPuestoAlumno")
	public String tallerPuestoBorrarPuestoAlumno(HttpServletRequest request, Model modelo){
		
		String idEjercicio		= request.getParameter("idEjercicio")==null?"0":request.getParameter("idEjercicio");
		String idCcosto   		= request.getParameter("idCcosto")==null?"0":request.getParameter("idCcosto");
		String categoriaId		= request.getParameter("categoriaId")==null?"0":request.getParameter("categoriaId");
		String puestoId 		= request.getParameter("puestoId")==null?"0":request.getParameter("puestoId");
		String mensaje			= "-";
		String periodoId 		= "0";		
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion != null){			
			periodoId 			= (String)sesion.getAttribute("periodoBecas");
		}	
		if( becPuestoAlumnoDao.existeReg(puestoId) ){		
		
			if(becPuestoAlumnoDao.deleteReg(puestoId)){
				
				BecPuesto becPuesto = new BecPuesto();						
				if( becPuestoDao.existeReg(idEjercicio, idCcosto, categoriaId, periodoId) ){					
					becPuesto = becPuestoDao.mapeaRegId(idEjercicio, idCcosto, categoriaId, periodoId);					
					becPuesto.setCantidad((Integer.parseInt(becPuesto.getCantidad())-1)+"");
					if( becPuestoDao.updateReg(becPuesto)){
						mensaje = "El registro se ha eliminado...";
					}				
				}				
			}
		}		
		return "redirect:/taller/puesto/puestoAlumno?idEjercicio="+idEjercicio+"&idCcosto="+idCcosto+"&categoriaId="+categoriaId+"&Mensaje="+mensaje;
	}
	
	@Transactional
	@RequestMapping("/taller/puesto/contratarAlumno")
	public String tallerPuestoContratarAlumno(HttpServletRequest request){
		
		String idEjercicio		= request.getParameter("idEjercicio")==null?"0":request.getParameter("idEjercicio");
		String idCcosto   		= request.getParameter("idCcosto")==null?"0":request.getParameter("idCcosto");
		String categoriaId		= request.getParameter("categoriaId")==null?"0":request.getParameter("categoriaId");
		String puestoId 		= request.getParameter("puestoId")==null?"0":request.getParameter("puestoId");
		String codigoAlumno		= request.getParameter("codigoAlumno")==null?"0":request.getParameter("codigoAlumno");
		boolean esInscrito		= alumPersonalDao.esInscrito(codigoAlumno);
		String mensaje			= "-";		
		if( becPuestoAlumnoDao.existeReg(puestoId) ){
			if(becPuestoAlumnoDao.contratar( "C", puestoId)){
				if (esInscrito){
					if (becAcuerdoDao.updateEstadoDelAcuerdo(codigoAlumno, puestoId, "I")){						
						mensaje = "<div class='alert alert-success'>Alumno contratado:"+codigoAlumno+"</div>";
					}else{
						mensaje = "<div class='alert alert-danger'>Error al Contratar al Alumno</div>";
					}
				}else{
					mensaje = "<div class='alert alert-success'>Alumno contratado:"+codigoAlumno+"</div>";					
				}	
			}else{
				mensaje = "<div class='alert alert-danger'>Error al Contratar al Alumno</div>";
			}				
		}		
		return "redirect:/taller/puesto/puestoAlumno?idEjercicio="+idEjercicio+"&idCcosto="+idCcosto+"&categoriaId="+categoriaId+"&Mensaje="+mensaje;
	}
	
	@Transactional
	@RequestMapping("/taller/puesto/despedirAlumno")
	public String tallerPuestoDespedirAlumno(HttpServletRequest request){
		
		String idEjercicio		= request.getParameter("idEjercicio")==null?"0":request.getParameter("idEjercicio");
		String idCcosto   		= request.getParameter("idCcosto")==null?"0":request.getParameter("idCcosto");
		String categoriaId		= request.getParameter("categoriaId")==null?"0":request.getParameter("categoriaId");
		String puestoId 		= request.getParameter("puestoId")==null?"0":request.getParameter("puestoId");
		String codigoAlumno		= request.getParameter("codigoAlumno")==null?"0":request.getParameter("codigoAlumno");		
		String mensaje			= "-";	
		
		if( becPuestoAlumnoDao.existeReg(puestoId) ){			
			if(becPuestoAlumnoDao.contratar("P", puestoId)){
				if (becAcuerdoDao.updateEstadoDelAcuerdo(codigoAlumno, puestoId, "A")){
					mensaje = "<div class='alert alert-danger'> Alumno descontratado</div>";
					
				}else{
					mensaje = "<div class='alert alert-danger'> Alumno descontratado </div>";	
				}				
			}else{
				mensaje = "<div class='alert alert-danger'>Error al descontratar al Alumno</div>";
			}	
		}		
		
		return "redirect:/taller/puesto/puestoAlumno?idEjercicio="+idEjercicio+"&idCcosto="+idCcosto+"&categoriaId="+categoriaId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/taller/puesto/contratoPDF")
	public String tallerPuestoContratoPDF(HttpServletRequest request) throws DocumentException, IOException{
		Fecha fecha = new Fecha();
		String fechaPdf = fecha.getFechaPDF("2");
		
		String rutaCarpeta 	= context.getRealPath("/WEB-INF/pdf/contrato/");
		if(!new java.io.File(rutaCarpeta).exists()) new java.io.File(rutaCarpeta).mkdirs();
		String idEjercicio 						= request.getParameter("idEjercicio");
		String institucion 						= "";
		String codigoAlumno	 					= request.getParameter("CodigoAlumno");
		String puesto 							= request.getParameter("Puesto");		
		String dir 								= rutaCarpeta+codigoAlumno+puesto+".pdf";
		String ejercicioId						= request.getParameter("EjercicioId");
		String idCcosto							= request.getParameter("idCcosto");
		String categoriaId						= request.getParameter("categoriaId");
		java.text.DecimalFormat getformato		= new java.text.DecimalFormat("###,##0.00;-###,##0.00");
		
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion != null){			
			institucion 		= (String) sesion.getAttribute("institucion");
		}		
		
		//Mapeo para carrera del alumno
		CatCarrera carrera = catCarreraDao.mapeaRegIdsinFac(alumPlanDao.getCarreraId(codigoAlumno));	
		
		BecPuestoAlumno becPuestoAlumno = new BecPuestoAlumno();
		//Mapeo para puesto
		if(becPuestoAlumnoDao.existeReg(puesto)){
			becPuestoAlumno = becPuestoAlumnoDao.mapeaRegId(puesto);	
		}
		
		// Busca el tipo de beca del alumno(Básica o Institucional).
		String tipoBasico = becAcuerdoDao.getAcuerdoBasico(idEjercicio, codigoAlumno, puesto);
		
		//Mapeo para nombre de centro de costo	
		ContCcosto contCcosto = new ContCcosto();
		
		if(contCcostoDao.existeReg(becPuestoAlumno.getIdEjercicio(), becPuestoAlumno.getIdCcosto())) {
			contCcosto = contCcostoDao.mapeaRegId(becPuestoAlumno.getIdEjercicio(), becPuestoAlumno.getIdCcosto());
		}
		
		List<BecAcuerdo> listaAcuerdos = becAcuerdoDao.getAcuerdosAlumnoPuesto(ejercicioId, codigoAlumno, becPuestoAlumno.getPuestoId(), " ORDER BY TIPO");
		BecAcuerdo obj = becAcuerdoDao.getAcuerdoAlumno(codigoAlumno, tipoBasico, puesto);
		
		// Mapa de tipos de becas
		HashMap<String,BecTipo> mapaTipoBeca = becTipoDao.mapaBecTipos(ejercicioId);
		
		BecPeriodo becPeriodo = new BecPeriodo();
		
		if(becPeriodoDao.existeReg(becPuestoAlumno.getPeriodoId())) {
			becPeriodo = becPeriodoDao.mapeaRegId(becPuestoAlumno.getPeriodoId());
		}
		
		String promesa	= "0";
		
		int posX 	= 0; 
		int posY	= 0;
		
		ArrayList<String> SemLineas = new ArrayList<String>();
		PdfPCell celda = null;
		int[] pagPrn = {1,0,0};
		
		float size0		= 6f;
		float size1		= 7f;
		float size2		= 9;
		float size3		= 11f;	
		
		int paginaAnterior		= 1;
		boolean printEncabezado = false;
		
		AlumPersonal alumPersonal = new AlumPersonal();
		alumPersonal = alumPersonalDao.mapeaRegId(codigoAlumno);
		
		//Creación de la fuente
		BaseFont base = BaseFont.createFont("../../fonts/adventsanslogo.ttf", BaseFont.WINANSI,true);
		
		Font fontAdvNormal11 	= new Font(base, 11f, Font.NORMAL);
		Font fontAdvNormal10 	= new Font(base, 10f, Font.NORMAL);
		Font fontAdvNormal9 	= new Font(base, 9f, Font.NORMAL);
		Font fontAdvNormal7 	= new Font(base, 7f, Font.NORMAL);
		Font fontAdvNormal6 	= new Font(base, 6f, Font.NORMAL);
		
		Font fontAdvBold11 		= new Font(base, 11f, Font.BOLD);
		Font fontAdvBold10 		= new Font(base, 10f, Font.BOLD);
		Font fontAdvBold9 		= new Font(base, 9f, Font.BOLD);
		Font fontAdvBold7 		= new Font(base, 7f, Font.BOLD);
		Font fontAdvBold6 		= new Font(base, 6f, Font.BOLD);
		
		Document document = new Document(PageSize.LETTER); //Crea un objeto para el documento PDF
		document.setMargins(-30,-30,50,30);
		//document.setMargins(-40,-40,84,30);	
		
		try{
			if(!new File(rutaCarpeta).exists()) new File(rutaCarpeta).mkdirs();
			PdfWriter pdf = PdfWriter.getInstance(document, new FileOutputStream(dir));
			document.addTitle(""+parametrosDao.getInstitucion("1"));
			document.addAuthor("Sistema Académico");
	        document.addSubject("Contrato de "+codigoAlumno);    
		    document.open();
		    
		    Image jpg = Image.getInstance(context.getRealPath("/imagenes/")+"/tituloConvenio.png");
		    jpg.setAlignment(Image.LEFT | Image.UNDERLYING);
		    jpg.scaleAbsolute(600,100);
		    jpg.setAbsolutePosition(0, posY+692);
		    //jpg.setAbsolutePosition(posX+65, posY+732);
		    document.add(jpg);
		    
		    
		    int r = 0, g = 0, b = 0;	   
		     
		    PdfContentByte canvas = pdf.getDirectContentUnder();
		   /* 
		    Phrase encabezado = new Phrase( "CREADA POR EL GOBIERNO DEL ESTADO DE NUEVO LEÓN, MÉXICO MEDIANTE LA RESOLUCIÓN OFICIAL PUBLICADA EL 5 DE MAYO DE 1973. CLAVE DE LA INSTITUCIÓN ANTE LA SEP Y DIRECCIÓN",  FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, Font.NORMAL, new BaseColor(0,0,0)) ); 
	    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, encabezado, posX+25, posY+724, 0);    	
	    	Phrase encabezado2 = new Phrase( "GENERAL DE ESTADÍSTICA 19MSU1017U",  FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, Font.NORMAL, new BaseColor(0,0,0)) ); 
	    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, encabezado2, posX+250, posY+718, 0);
	    	*/ 
		    
		    /*Phrase uni = new Phrase( institucion.toUpperCase(),  FontFactory.getFont(FontFactory.TIMES_ROMAN, 18, Font.BOLD, new BaseColor(0,0,0)) );   	
	    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, uni, posX+340, posY+720, 0);*/
	    	/*
	    	Phrase servicio = new Phrase( "CONVENIO DE SERVICIO BECARIO",  FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0,0,0)) );   	
	    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, servicio, posX+308, posY+695, 0);	     
	    	*/
	    	Phrase generales = new Phrase( "DATOS GENERALES",  new Font(base, 10F, Font.NORMAL)); 
	    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, generales, posX+25, posY+680, 0);
	    	
	    	/*String universidad = "UNIVERSIDAD DE MONTEMORELOS";
	    	Phrase prueba = new Phrase( universidad,  font );   	
	    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, prueba, posX+340, posY+717, 0);*/
	    	
	    	// Insertar linea debajo de DATOS GENERALES
	    	canvas.setLineWidth(2.0f); 
	        canvas.setGrayStroke(0.5f);     
	        canvas.moveTo(posX+25, posY+675);
	        canvas.lineTo(posX+25 + document.getPageSize().getWidth()-50, posY+675); 
	        canvas.stroke();
	        
	        // Rectangulo Matricula
	        canvas.saveState();        
	        canvas.setColorStroke(new BaseColor(0,0,0));        
	        canvas.setLineWidth(0.5f);
	        canvas.roundRectangle(posX+25, posY+655, 70, 15, 7);
	        canvas.stroke();
	        canvas.restoreState();
	        
	        // Dato de Matrícula
	        //Phrase matricula = new Phrase( codigoAlumno,  FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.BOLD, new BaseColor(0,0,0)) ); 
	        Phrase matricula = new Phrase( codigoAlumno,  new Font(base, 10F, Font.BOLD) );        
	    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, matricula, posX+41, posY+659, 0);
	    	
	    	// Texto de Matrícula
	        //Phrase textoMatricula = new Phrase( "MATRÍCULA",  FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL, new BaseColor(0,0,0)) );
	    	Phrase textoMatricula = new Phrase( "MATRÍCULA",  new Font(base, 7f, Font.NORMAL));
	    	
	    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, textoMatricula, posX+42, posY+647, 0);
	        
	    	// Rectangulo nombre
	        canvas.saveState();        
	        canvas.setColorStroke(new BaseColor(0,0,0));        
	        canvas.setLineWidth(0.5f);
	        canvas.roundRectangle(posX+100, posY+655, 487, 15, 7);
	        canvas.stroke();
	        canvas.restoreState();
	        
	     	// Contenido de nombre
	        Phrase valueNombre= new Phrase( alumPersonalDao.getNombreAlumno(codigoAlumno, "APELLIDO"),  new Font(base, 10F, Font.BOLD) ); 
	    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, valueNombre, posX+110, posY+659, 0);
	        
	     	// Texto de nombre
	        Phrase textoNombre= new Phrase( "NOMBRE",  new Font(base, 7f, Font.NORMAL) ); 
	    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, textoNombre, posX+325, posY+647, 0);
	    	
	    	// Rectangulo carrera
	        canvas.saveState();        
	        canvas.setColorStroke(new BaseColor(0,0,0));        
	        canvas.setLineWidth(0.5f);
	        canvas.roundRectangle(posX+25, posY+620, 562, 15, 7);
	        canvas.stroke();
	        canvas.restoreState();
	        
	     	// Contenido de carrera
	     	String realPlan = becPuestoAlumno.getPlanId();
	     	String nombrePlan = mapaPlanDao.getCarreraSe(realPlan);
	        Phrase valueCarrera = new Phrase(nombrePlan,  new Font(base, 10F, Font.BOLD) ); 
	    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, valueCarrera, posX+35, posY+625, 0);        
	        
	     	// Texto de carrera
	        Phrase textoCarrera = new Phrase( "CARRERA",  new Font(base, 7f, Font.NORMAL) ); 
	    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, textoCarrera, posX+285, posY+612, 0);
	        
	        Phrase beca = new Phrase( "DATOS DE LA BECA",  new Font(base, 10f, Font.NORMAL) ); 
	    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, beca, posX+25, posY+600, 0);
	    	
	    	// Insertar linea
	    	canvas.setLineWidth(2.0f);	 // Make a bit thicker than 1.0 default 
	        canvas.setGrayStroke(0.5f); // 0 = black, 1 = white         
	        canvas.moveTo(posX+25, posY+595);
	        canvas.lineTo(posX+25 + document.getPageSize().getWidth()-50, posY+595); 
	        canvas.stroke();
	        
	     	// Rectangulo número de plaza
	        canvas.saveState();        
	        canvas.setColorStroke(new BaseColor(0,0,0));        
	        canvas.setLineWidth(0.5f);
	        canvas.roundRectangle(posX+25, posY+575, 70, 15, 7);
	        canvas.stroke();
	        canvas.restoreState();
	        
	     	// Contenido de número de plaza
	        Phrase valuePlazaNo = new Phrase( puesto,  new Font(base, 9F, Font.BOLD) ); 
	    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, valuePlazaNo, posX+28, posY+579, 0);
	       
	     	// Texto de número de plaza
	        Phrase textoPlazaNo = new Phrase( "PLAZA No.",  new Font(base, 7F, Font.NORMAL) ); 
	    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, textoPlazaNo, posX+40, posY+565, 0);
	    	
	    	// Rectangulo importe por hora
	        canvas.saveState();        
	        canvas.setColorStroke(new BaseColor(0,0,0));        
	        canvas.setLineWidth(0.5f);
	        canvas.roundRectangle(posX+130, posY+575, 70, 15, 7);
	        canvas.stroke();
	        canvas.restoreState();

	     	// Contenido importe por hora
	     	String importe = "$"+obj.getEnsenanza();
	     	if(importe.length()<=1){
	     		importe = " ";
	     	}
	     	Phrase valueImporte = new Phrase( importe,  new Font(base, 10F, Font.BOLD) ); 
	    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, valueImporte, posX+151, posY+579, 0);
	        
	     	// Texto importe por hora
	     	Phrase textoImporte = new Phrase( "IMPORTE POR HORA",  new Font(base, 7F, Font.NORMAL) ); 
	    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, textoImporte, posX+131, posY+565, 0);
	        
	    	// Rectangulo max horas por reportar
	        canvas.saveState();        
	        canvas.setColorStroke(new BaseColor(0,0,0));        
	        canvas.setLineWidth(0.5f);
	        canvas.roundRectangle(posX+240, posY+575, 70, 15, 7);
	        canvas.stroke();
	        canvas.restoreState();
	        
	        //Contenido de max horas a reportar
	     	Phrase valueMaxHoras = new Phrase( obj.getHoras(),  new Font(base, 10F, Font.BOLD) ); 
	    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, valueMaxHoras, posX+268, posY+579, 0);        
	        
	     	// Texto max horas a reportar
	     	Phrase textoMaxHoras = new Phrase( "MAX HORAS A REPORTAR",  new Font(base, 7F, Font.NORMAL) ); 
	    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, textoMaxHoras, posX+230, posY+565, 0);
		    
	    	// Rectangulo período de aplicación de beca
	        canvas.saveState();        
	        canvas.setColorStroke(new BaseColor(0,0,0));        
	        canvas.setLineWidth(0.5f);
	        canvas.roundRectangle(posX+360, posY+575, 228, 15, 7);
	        canvas.stroke();
	        canvas.restoreState();
	        
	        //Contenido de período de aplicación de beca
	        String fechas = becPuestoAlumno.getFechaIni()+" - "+becPuestoAlumno.getFechaFin();
	        if(fechas.length()<=1){
	        	fechas = " ";
	        }
	        Phrase valuePeriodoBeca = new Phrase( fechas,  new Font(base, 10F, Font.BOLD) ); 
	    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, valuePeriodoBeca, posX+417, posY+579, 0);
	        
	     	// Texto período de aplicación de beca
	     	Phrase textoPeriodoBeca = new Phrase( "PERÍODO DE APLICACIÓN DE BECA",  new Font(base, 7F, Font.NORMAL) ); 
	    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, textoPeriodoBeca, posX+409, posY+565, 0);
	    	
	    	// Rectangulo departamento
	        canvas.saveState();        
	        canvas.setColorStroke(new BaseColor(0,0,0));        
	        canvas.setLineWidth(0.5f);
	        canvas.roundRectangle(posX+25, posY+535, 562, 15, 7);
	        canvas.stroke();
	        canvas.restoreState();
	        
	        //Contenido de departamento
	        Phrase valueDepartamento = new Phrase( contCcosto.getNombre(),  new Font(base, 10F, Font.BOLD) ); 
	    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, valueDepartamento, posX+145, posY+539, 0);
	    	
	    	 //Nombre de departamento
	    	String texto = "";
	    	if(becPuestoAlumno.getDescripcion().equals("-")){
	    		texto = "";
	    	}else{
	    		texto = "("+becPuestoAlumno.getDescripcion()+")";	
	    	}
	        Phrase nombreDepartamento = new Phrase( texto,  new Font(base, 8F, Font.BOLD) ); 
	    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, nombreDepartamento, posX+330, posY+539, 0);
	        
	     	// Texto departamento
	     	Phrase textoDepartamento = new Phrase( "DEPARTAMENTO",  new Font(base, 7F, Font.NORMAL) ); 
	    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, textoDepartamento, posX+273, posY+525, 0);    	    	
	    	
	    	/*******************/    	
	        Phrase montos = new Phrase( "DETALLE DE MONTOS Y DEDUCCIONES",  new Font(base, 10F, Font.NORMAL) ); 
	    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, montos, posX+25, posY+505, 0);
	    	
	    	// Insertar linea
	    	canvas.setLineWidth(2.0f);	 // Make a bit thicker than 1.0 default
	        canvas.setGrayStroke(0.5f); // 0 = black, 1 = white
	        canvas.moveTo(posX+25, posY+500); //405
	        canvas.lineTo(posX+25 + document.getPageSize().getWidth()-50, posY+500);
	        canvas.stroke();    	
	        
	        PdfPCell cell = null;
	        
	        // TOP TABLE (Uso temporal para poder crear espacio)
	        float anchoColumnas[] = {100f};
			PdfPTable tableTop = new PdfPTable(anchoColumnas);		
			tableTop.setTotalWidth(document.getPageSize().getWidth()-50);		
			tableTop.setSpacingAfter(10f);
			
			// Renglón en blanco
			cell = new PdfPCell(new Phrase(" ", new Font(base, size3, Font.BOLD) ));		
			cell.setBorder(0);
			tableTop.addCell(cell);
					
			document.add(tableTop);
		
			// TABLA DE ACUERDOS
			float headerwidths[]= {5f,50f,15f,15f,15f};
			PdfPTable tableAcuerdo = new PdfPTable(headerwidths);
			//tableAcuerdo.setWidths(headerwidths);
			tableAcuerdo.getDefaultCell().setBorder(1);
			tableAcuerdo.setTotalWidth(document.getPageSize().getWidth()-40);
			tableAcuerdo.setSpacingBefore(220);
			
			//PRIMERA FILA**********************
			// Primer columna 5% 
			cell = new PdfPCell(new Phrase("#", new Font(base, size2, Font.NORMAL) ));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);		
			cell.setBorder(1);		
			cell.setBorder(Rectangle.BOX);
			tableAcuerdo.addCell(cell);
			
			// Segunda columna 50%
			cell = new PdfPCell(new Phrase("Acuerdo", new Font(base, size2, Font.NORMAL) ));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);		
			cell.setBorder(1);		
			cell.setBorder(Rectangle.BOX);
			tableAcuerdo.addCell(cell);
			
			//Tercer columna 15%
	        cell = new PdfPCell(new Phrase("Importe", new Font(base, size2, Font.NORMAL) ));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(1);
			cell.setBorder(Rectangle.BOX);		
			tableAcuerdo.addCell(cell);
			
			//Cuarta columna 15%
	        cell = new PdfPCell(new Phrase("Donativo", new Font(base, size2, Font.NORMAL) ));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(1);
			cell.setBorder(Rectangle.BOX);		
			tableAcuerdo.addCell(cell);
			
			//Quinta columna 15%
	        cell = new PdfPCell(new Phrase("Total", new Font(base, size2, Font.NORMAL)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(1);		
			cell.setBorder(Rectangle.BOX);
			tableAcuerdo.addCell(cell);
			
			//TRAER EL TIPO DE LA BECA INDUSTRIAL BASICA
			String tipoBasicaInd 		= becTipoDao.getTipo(idEjercicio, "I").equals("none")?"0":becTipoDao.getTipo(idEjercicio, "I");

			//TRAER EL TIPO DE LA BECA BASICA
			String tipoBasica 			= becTipoDao.getTipo(idEjercicio, "B").equals("none")?"0":becTipoDao.getTipo(idEjercicio, "B");
			
			//TRAER EL TIPO DE LA BECA ADICIONAL
			String tipoAdicional 		= becTipoDao.getTipo(idEjercicio, "A").equals("none")?"0":becTipoDao.getTipo(idEjercicio, "A");			
			
			String tipoB   = tipoBasica;
			if(becPuestoAlumno.getTipo().equals("I")){
				tipoB = tipoBasicaInd;
			}
			
			int cont = 1;
			float suma = 0;
			float sumaImporte = 0;
			
			// CALCULA LOS CREDITOS DE AYUDA PARA LOS ALUMNOS CON BECA ADICIONAL
			String totalBecaAdicional = fesCcAfeAcuerdosDao.getBecaAdicional(codigoAlumno, puesto, idEjercicio, "'B','I'");
			
			String clasFin		= alumAcademicoDao.getClasFinAlumno(codigoAlumno);
			clasFin = clasFin.equals("1")?"S":"N";
			String modalidadId	= alumAcademicoDao.getModalidadId(codigoAlumno);
			
			String cargaId		= fesCcAfeAcuerdosDao.getCargaDelPuesto(codigoAlumno, ejercicioId, puesto, "'B','I'");
			String tablaFin		= fesCcAfeAcuerdosDao.getTablaFin(cargaId);
			float costo 		= fesCcAfeAcuerdosDao.getCostoCredito(tablaFin, clasFin);
			
			float porcentaje 	= fesCcAfeAcuerdosDao.getPorcentaje(tablaFin, carrera.getCarreraId(), modalidadId);
			float costoCredito = costo*porcentaje;	
			double creditosBeca = 0;
			if (costoCredito > 0)
				creditosBeca = Double.parseDouble(totalBecaAdicional) / costoCredito;
			
			//System.out.println("Datos:"+codigoAlumno+":"+ejercicioId+":"+puesto+":"+cargaId+":"+costo+":"+porcentaje+":"+carrera.getCarreraId()+" Costo:"+costoCredito+":"+totalBecaAdicional+":"+modalidadId+":"+tablaFin);		
			// *********** SI NO TIENE ACUERDOS ENTONCES LA INFORMACION SE SACA DE ENOC **********************************/		
			//ACUERDOS DEL ALUMNO EN ESTE PUESTO (SI ES QUE TIENE)
		List<BecAcuerdo> acuerdosAsociados = becAcuerdoDao.getAcuerdosAlumno(idEjercicio, becPuestoAlumno.getCodigoPersonal(), tipoB+","+tipoAdicional, " AND PUESTO_ID = '"+becPuestoAlumno.getPuestoId()+"'");
			
		boolean tieneAcuerdoTemporal = false;
		
		if(acuerdosAsociados.size()==0){
			
			// PRIMERA FILA
			for(int i=0; i < listaAcuerdos.size(); i++){
				
				//Nombre del tipo de beca
				BecTipo tipoBeca = becTipoDao.mapeaRegId(ejercicioId, listaAcuerdos.get(i).getTipo());
				
				boolean temporal = false;
				
				if(tipoBeca.getAcuerdo().equals("O")){
					temporal = true;
				}
				
				// Primer columna 5%
				cell = new PdfPCell(new Phrase(String.valueOf(cont), new Font(base, size2, Font.BOLD) ));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);		
				cell.setBorder(1);		
				cell.setBorder(Rectangle.BOX);
				tableAcuerdo.addCell(cell);	
				
				String nombreTipoBeca = tipoBeca.getNombre();
				
				if(tieneAcuerdoTemporal){
					nombreTipoBeca = nombreTipoBeca+" (Temporal)";
				}
				
				// Segunda columna-Nombre del tipo de beca (50%)
				cell = new PdfPCell(new Phrase(nombreTipoBeca, new Font(base, size2, Font.BOLD) ));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);		
				cell.setBorder(1);		
				cell.setBorder(Rectangle.BOX);
				tableAcuerdo.addCell(cell);
				
				//Tercer columna-Importe de la beca (15%)
				double imp = 0.0;
				if(listaAcuerdos.get(i).getTipo().equals(tipoAdicional)){
					if(listaAcuerdos.get(i).getTipoadicional().equals("D")){
						imp = Double.parseDouble(listaAcuerdos.get(i).getEnsenanza());	
					}else{
						promesa = listaAcuerdos.get(i).getPromesa();
						if(promesa==null || promesa.equals("") || promesa.equals("null") || promesa.equals(" ")){
							promesa = "1";
						}
						imp = (Double.parseDouble(listaAcuerdos.get(i).getEnsenanza())/100)*Double.parseDouble(promesa);
					}
					
				}else{
					imp = Double.parseDouble(listaAcuerdos.get(i).getEnsenanza())*Double.parseDouble(listaAcuerdos.get(i).getHoras());
				}
				
				sumaImporte += imp;
				
		        cell = new PdfPCell(new Phrase("$"+getformato.format(imp), new Font(base, size2, Font.BOLD) ));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setBorder(1);
				cell.setBorder(Rectangle.BOX);		
				tableAcuerdo.addCell(cell);
				
				//Cuarta columna 15%  - Diezmo
				float diezmo = 0f; 
				if(tipoBeca.getTipo().equals(tipoB)){
					diezmo = Float.valueOf ((float) (imp*0.10));	
				}
				
		        cell = new PdfPCell(new Phrase("$"+getformato.format(diezmo), new Font(base, size2, Font.BOLD)));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setBorder(1);
				cell.setBorder(Rectangle.BOX);		
				tableAcuerdo.addCell(cell);
				
				//Quinta columna 15%
				float total = Float.valueOf((float) (imp-diezmo));
				
		        cell = new PdfPCell(new Phrase("$"+getformato.format(total), new Font(base, size2, Font.BOLD)));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setBorder(1);
				cell.setBorder(Rectangle.BOX);
				tableAcuerdo.addCell(cell);
				
				suma += total;
				cont++;
			}
		}else{		
			// *********** ------DEL FINANCIERO----- SI TIENE ACUERDOS Y YA HIZO SU CARGA DE MATERIAS ENTONCES LA INFORMACION SE SACA DE MATEO **********************************/
			
			List<String> listaAcuerdosFinanciero = becAcuerdoDao.getAcuerdosAlumnoFinanciero(ejercicioId, codigoAlumno, becPuestoAlumno.getPuestoId(), "ORDER BY TIPO_ID");
			
			for(int i=0; i < listaAcuerdosFinanciero.size(); i++){
				String [] arr = listaAcuerdosFinanciero.get(i).split("@@");
				
				String nombreBeca 	= arr[0];
				String impo    		= arr[1];
				String tipoId   	= arr[2];
				//System.out.println("Datos:"+nombreBeca+":"+tipoId);
				
				if (mapaTipoBeca.containsKey(ejercicioId+tipoId) ){
					if (mapaTipoBeca.get(ejercicioId+tipoId).getAcuerdo().equals("O") )
						tieneAcuerdoTemporal = true;
				}
			}	

			//SEGUNDA FILA************		
			for(int i=0; i < listaAcuerdosFinanciero.size(); i++){
				String [] arr = listaAcuerdosFinanciero.get(i).split("@@");
				
				String nombreBeca 	= arr[0];
				String impo    		= arr[1];
				String tipoId   	= arr[2];
				
				// Primer columna 5%
				cell = new PdfPCell(new Phrase(String.valueOf(cont), new Font(base, size2, Font.BOLD) ));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(1);
				cell.setBorder(Rectangle.BOX);
				tableAcuerdo.addCell(cell);
				
				if(tieneAcuerdoTemporal && !tipoId.equals("1") && !tipoId.equals("3")){
					String porcentajes = "";
					// Porcentaje --> P=% y Cantidad ---> C=$ 
					String valor = "-";
					
					if(listaAcuerdos.size() <= i){
						valor = listaAcuerdos.get(i).getValor().equals("P")?"%":"$";
					}
					
					if(!listaAcuerdos.get(i).getMatricula().equals("0"))
						porcentajes = " M:"+listaAcuerdos.get(i).getMatricula()+valor;
					if(!listaAcuerdos.get(i).getEnsenanza().equals("0"))
						porcentajes = porcentajes + " E:"+listaAcuerdos.get(i).getEnsenanza()+valor;
					if(!listaAcuerdos.get(i).getInternado().equals("0"))
						porcentajes = porcentajes + " I:"+listaAcuerdos.get(i).getInternado()+valor;
					
					nombreBeca = nombreBeca+" (Temporal "+porcentajes+")";
				}
				
				// Segunda columna 50%- Nombre del tipo de beca
				cell = new PdfPCell(new Phrase(nombreBeca, new Font(base, size2, Font.BOLD) ));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);		
				cell.setBorder(1);		
				cell.setBorder(Rectangle.BOX);
				tableAcuerdo.addCell(cell);
				
				//Tercer columna 15% - importe de la beca
				float imp = 0.0f;
				imp = Float.parseFloat(impo);
				
		        cell = new PdfPCell(new Phrase("$"+getformato.format(imp), new Font(base, size2, Font.BOLD) ));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setBorder(1);
				cell.setBorder(Rectangle.BOX);		
				tableAcuerdo.addCell(cell);
				
				sumaImporte += imp;
				
				//Cuarta columna 15% - Diezmo de la beca
				float diezmo = 0f; 
				if(tipoId.equals(tipoB)){
					diezmo = imp * 0.10f;	
				}
				
		        cell = new PdfPCell(new Phrase("$"+getformato.format(diezmo), new Font(base, size2, Font.BOLD)));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setBorder(1);
				cell.setBorder(Rectangle.BOX);		
				tableAcuerdo.addCell(cell);
				
				//Quinta columna 15%
				float total = imp-diezmo;
				
		        cell = new PdfPCell(new Phrase("$"+getformato.format(total), new Font(base, size2, Font.BOLD) ));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setBorder(1);
				cell.setBorder(Rectangle.BOX);
				tableAcuerdo.addCell(cell);		
				suma += total;
				cont++;			
			}
		}
	// *********** FIN ACUERDOS **********************************/		

			//FILA DE TOTAL IMPORTE*****************
			cell = new PdfPCell(new Phrase(" Totales ", new Font(base, size2, Font.BOLD) ));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setColspan(2);
			cell.setBorder(1);
			cell.setBorder(Rectangle.BOX);
			tableAcuerdo.addCell(cell);
			
			cell = new PdfPCell(new Phrase("$"+getformato.format(sumaImporte), new Font(base, size2, Font.BOLD) ));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorder(1);
			cell.setBorder(Rectangle.BOX);
			tableAcuerdo.addCell(cell);
			
			
			//FILA DE TOTAL*****************
			cell = new PdfPCell(new Phrase(" ", new Font(base, size2, Font.BOLD) ));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setColspan(1);
			cell.setBorder(1);
			cell.setBorder(Rectangle.BOX);
			tableAcuerdo.addCell(cell);
				
			cell = new PdfPCell(new Phrase("$"+getformato.format(suma), new Font(base, size2, Font.BOLD)));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorder(1);
			cell.setBorder(Rectangle.BOX);
			tableAcuerdo.addCell(cell);		
			
			document.add(tableAcuerdo);
					
//	         Phrase terminos = new Phrase( "TÉRMINOS Y CONDICIONES",  FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(0,0,0)) ); 
//	     	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, terminos, posX+25, posY+340, 0);		


			PdfPTable informacion = new PdfPTable(1);
			informacion.getDefaultCell().setBorder(0);
			informacion.setTotalWidth(document.getPageSize().getWidth()-40);
			informacion.setSpacingBefore(0);
			
			float toBecAdi = Float.parseFloat(totalBecaAdicional);
			
			cell = new PdfPCell(new Phrase("Total de beca adicional $"+getformato.format(toBecAdi)+",  Costo del crédito $"+getformato.format(costoCredito)+",  Créditos de Beca = "+getformato.format(creditosBeca),
					new Font(base, size2, Font.NORMAL) ));	
	        
			cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			cell.setBorder(0);
			informacion.addCell(cell);
			
			document.add(informacion);
	    
			// TABLA DE TERMINOS Y CONDICIONES
			//float headerwidths2[] = {10f,45f,15f,15f,15f};
			PdfPTable tableTerminos = new PdfPTable(1);
			tableTerminos.getDefaultCell().setBorder(0);
			tableTerminos.setTotalWidth(document.getPageSize().getWidth()-40);
			tableTerminos.setSpacingBefore(0);
			
			cell = new PdfPCell(new Phrase("\n\nTÉRMINOS Y CONDICIONES PARA ACREDITAR EL SERVICIO BÁSICO Y LA BECA ADICIONAL", new Font(base, size1, Font.BOLD) ));
	        
			cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			cell.setBorder(0);
			tableTerminos.addCell(cell);	
			
			cell = new PdfPCell();
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorder(2);
			cell.setBorder(Rectangle.BOTTOM);
			cell.setBorderColor(new BaseColor(80,80,80));
			cell.setBorderWidth(2f);
		
			tableTerminos.setHorizontalAlignment(Element.ALIGN_CENTER); 
			tableTerminos.setWidthPercentage(80f);
			tableTerminos.addCell(cell);
			
			Phrase phrase = new Phrase();
			phrase.add(new Chunk("\n1. El ", new Font(base, size0, Font.NORMAL)));
			phrase.add(new Chunk(" Servicio Becario Básicó ", new Font(base, size0, Font.BOLD)));
			phrase.add(new Chunk("se acreditará en base al cumplimiento de sus horas mensuales.", new Font(base, size0, Font.NORMAL)));
			
			cell = new PdfPCell(phrase);		
	        
			cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			cell.setBorder(0);
			tableTerminos.addCell(cell);
			
			phrase = new Phrase();
			phrase.add(new Chunk("\n2. El ", new Font(base, size0, Font.NORMAL)));
			phrase.add(new Chunk(" Servicio Becario Adicional ", new Font(base, size0, Font.BOLD)));
			phrase.add(new Chunk("se aplicará siempre y cuando el estudiante haya cumplido con la totalidad de las horas. Si no lo cumple se cancela el Servicio Becario adicional."+
					" Se acreditará al finalizar el semestre o tetramestre, una vez pagado su último pagaré, y todo lo que corresponda pagar quede totalmente saldado.", new Font(base, size0, Font.NORMAL)));			
			cell = new PdfPCell(phrase);		
			cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			cell.setBorder(0);
			tableTerminos.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Fechas para recibir su beca adicional:",
				FontFactory.getFont(FontFactory.HELVETICA, size0, Font.BOLDITALIC, new BaseColor(0,0,0))));
			cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			cell.setBorder(0);
			tableTerminos.addCell(cell);

			cell = new PdfPCell(new Phrase("\n         Universidad*",
					FontFactory.getFont(FontFactory.HELVETICA, size0, Font.BOLD, new BaseColor(0,0,0))));
			cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			cell.setBorder(0);
			tableTerminos.addCell(cell);

			// Texto para alumnos universitarios
			String textoUniversidad = "         "+becPeriodo.getFechasUniversidad();
			if(textoUniversidad.contains("-")) {
				textoUniversidad = textoUniversidad.replace("-", "\n-");
			}
			
			// Texto para alumnos universitarios
			String textoPrepa = "         "+becPeriodo.getFechasPrepa();
			if(textoPrepa.contains("-")) {
				textoPrepa = textoPrepa.replace("-", "\n-");
			}
			
			// Texto para el periodo
			String textoPeriodo = "         "+becPeriodo.getFechasPeriodo();
			
			cell = new PdfPCell(new Phrase(textoUniversidad, FontFactory.getFont(FontFactory.HELVETICA, size0, Font.BOLDITALIC, new BaseColor(0,0,0))));		
			cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			cell.setBorder(0);
			tableTerminos.addCell(cell);
		
			cell = new PdfPCell(new Phrase("\n         Preparatoria*",
					FontFactory.getFont(FontFactory.HELVETICA, size0, Font.BOLD, new BaseColor(0,0,0))));
			cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			cell.setBorder(0);
			tableTerminos.addCell(cell);
			
			// Texto para alumnos de preparatoria
			cell = new PdfPCell(new Phrase(textoPrepa, FontFactory.getFont(FontFactory.HELVETICA, size0, Font.BOLD, new BaseColor(0,0,0))));            
			cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			cell.setBorder(0);
			tableTerminos.addCell(cell);
			
			cell = new PdfPCell(new Phrase("\n         *Los alumnos que participan de un Servicio Becario(Diamante, Promocional, C. Unión, Ingreso de prepa o cualquier otro) \n"+
					" por medio de EmprendUM deben tomar en cuenta las fechas intersemestrales (para bonificación) las cuales son: ",
					FontFactory.getFont(FontFactory.HELVETICA, size0, Font.BOLD, new BaseColor(0,0,0))));        
			cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			cell.setBorder(0);
			tableTerminos.addCell(cell);		
			cell = new PdfPCell(new Phrase("\n"+textoPeriodo,
					FontFactory.getFont(FontFactory.HELVETICA, size0, Font.BOLD, new BaseColor(0,0,0))));        
			cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			cell.setBorder(0);
			tableTerminos.addCell(cell);		
			cell = new PdfPCell(new Phrase("\n3. Si el alumno recibe ingresos por cualquier concepto distinto a beca o talleres y su saldo queda a favor, entonces la parte de beca se \n"+
										   "ajustará hasta que el saldo quede en ceros. Si queda a favor se usará para trámites internos en la Universidad. ",
				   new Font(base, size0, Font.NORMAL) ));        
			cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			cell.setBorder(0);
			tableTerminos.addCell(cell);		
			cell = new PdfPCell(new Phrase("\n4. En caso de no cumplir satisfactoriamente con este programa, ya sea por falta de rendimiento, insuficiencia en el desempeño de las actividades "+
										   "asignadas, problemas de conducta o deficiencia en el aprovechamiento académico; la Universidad se reserva el derecho de anular la beca. \n",
				   new Font(base, size0, Font.NORMAL) ));
			
			cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			cell.setBorder(0);
			tableTerminos.addCell(cell);
			
			cell = new PdfPCell(new Phrase(" ", new Font(base, size0, Font.NORMAL) ));
			cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			cell.setBorder(0);
			tableTerminos.addCell(cell);		
			
			cell = new PdfPCell(new Phrase(" ", new Font(base, size0, Font.NORMAL) ));
			cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			cell.setBorder(0);
			tableTerminos.addCell(cell);
			
	        cell = new PdfPCell(new Phrase("Montemorelos, Nuevo León, México   "+ fechaPdf, new Font(base, size1, Font.BOLD) ));
			cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			cell.setBorder(0);
			tableTerminos.addCell(cell);
			
			document.add(tableTerminos);
			
			Image marcaAgua = Image.getInstance(context.getRealPath("/imagenes/")+"/logoConvenio.png");
			marcaAgua.scaleAbsolute(600,300);
			marcaAgua.setAbsolutePosition(0f, 0f);
			
		   
			// Imagen de la flama detrás del texto de las firmas
			canvas.addImage(marcaAgua);

		}catch(IOException ioe){
			System.err.println("Error certificado en PDF: "+ioe.getMessage());
		}
		
		document.close();	
		// Cambia la diagonal inversa por diagonal normal para que se pueda ver el archivo en windows(localhost)
		if (java.io.File.separator.equals("\\")){
			dir = dir.replace("\\", "/");		
		}
		String nombreArchivo = codigoAlumno+puesto+".pdf";
		java.io.File f = new java.io.File(dir);		
		byte[] archivo = null;
		java.io.FileInputStream instream = null;		
		if(f.exists()){
			archivo = new byte[(int)f.length()];
			instream = new java.io.FileInputStream(dir);
		}
		instream.read(archivo,0,(int)f.length());
		instream.close();
		
		BecContrato becContrato = new BecContrato();
		
		becContrato.setCodigoPersonal(codigoAlumno);
		becContrato.setFecha(aca.util.Fecha.getHoy());
		becContrato.setNombre(nombreArchivo);
		becContrato.setArchivo(archivo);
		becContrato.setPuestoId(puesto);
		
		if(becContratoDao.existeReg(codigoAlumno, puesto)) {
			becContrato = becContratoDao.mapeaRegId(codigoAlumno, puesto);
		}else {
			becContratoDao.insertReg(becContrato);			
		}
		
		return "redirect:/taller/puesto/puestoAlumno?idEjercicio="+idEjercicio+"&idCcosto="+idCcosto+"&categoriaId="+categoriaId;
	}	
	
	@RequestMapping("/taller/puesto/descargarContrato")
	public void tallerPuestoDescargarContrato(HttpServletResponse response, HttpServletRequest request){
		String codigoAlumno 	= request.getParameter("CodigoAlumno")==null?"0":request.getParameter("CodigoAlumno");
		String puesto		 	= request.getParameter("Puesto")==null?"0":request.getParameter("Puesto");
		
		BecContrato becContrato = new BecContrato();
		
		if(becContratoDao.existeReg(codigoAlumno, puesto)) {
			becContrato = becContratoDao.mapeaRegId(codigoAlumno, puesto);
		}
		
		try {
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition","attachment; filename=\""+becContrato.getNombre()+ "\"");
			response.getOutputStream().write(becContrato.getArchivo());
			response.flushBuffer();
		} catch (IOException e) {
			System.out.println("ERROR");
			e.printStackTrace();
		}
	}	
	
	@RequestMapping("/taller/puesto/eliminarPdf")
	public String tallerPuestoEliminarPdf(HttpServletResponse response, HttpServletRequest request){
		String codigoAlumno = request.getParameter("CodigoAlumno")==null?"0":request.getParameter("CodigoAlumno");
		String puesto		= request.getParameter("Puesto")==null?"0":request.getParameter("Puesto");
		String idEjercicio 	= request.getParameter("idEjercicio")==null?"0":request.getParameter("idEjercicio");
		String idCcosto		= request.getParameter("idCcosto")==null?"0":request.getParameter("idCcosto");
		String categoriaId 	= request.getParameter("categoriaId")==null?"0":request.getParameter("categoriaId");
		
		if(becContratoDao.existeReg(codigoAlumno, puesto)) {
			becContratoDao.deleteReg(codigoAlumno, puesto);
		}
		
		return "redirect:/taller/puesto/puestoAlumno?idEjercicio="+idEjercicio+"&idCcosto="+idCcosto+"&categoriaId="+categoriaId;
	}	
	
	@RequestMapping("/taller/puesto/mover")
	public String tallerPuestoMover(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContTallerPuesto|tallerPuestoMover:");
		return "taller/puesto/mover";
	}	
	
	@RequestMapping("/taller/puesto/puestoAlumno")
	public String tallerPuestoPuestoAlumno(HttpServletRequest request, Model modelo){		
		
		String fechaPuesto 		= aca.util.Fecha.getHoy();
		String codigoPersonal	= "0";
		String periodoId 		= "0";
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();		
		if (sesion != null){		
			fechaPuesto 		= (String)sesion.getAttribute("fechaPuesto");
			codigoPersonal		= (String)sesion.getAttribute("codigoPersonal");
			periodoId 			= (String)sesion.getAttribute("periodoBecas");
		}
		
		String idEjercicio 		= request.getParameter("idEjercicio")==null?"0":request.getParameter("idEjercicio");
		String idCcosto    		= request.getParameter("idCcosto")==null?"0":request.getParameter("idCcosto");
		String categoriaId    	= request.getParameter("categoriaId")==null?"0":request.getParameter("categoriaId");
		boolean adminBecas     	= accesoDao.getBecas(codigoPersonal);	
		String periodoNombre 	= becPeriodoDao.getPeriodoNombre(periodoId);
		BecPlazas becPlazas 	= becPlazasDao.mapeaRegId(idEjercicio, idCcosto, periodoId);
		// Claves de los tipos de beca Basica, adicional, basico institucional y adicional institucional
		String tiposRegulares	= becTipoDao.getBasicosyAdicionales(idEjercicio);
		String tiposBasicos		= becTipoDao.getBasicos(idEjercicio);		
		
		int cantidadB = Integer.parseInt(becPuestoDao.getCantidadBecasUsadas(idEjercicio, idCcosto, "B", fechaPuesto, periodoId));
		int cantidadT = Integer.parseInt(becPuestoDao.getCantidadBecasUsadas(idEjercicio, idCcosto, "T", fechaPuesto, periodoId));
		int cantidadI = Integer.parseInt(becPuestoDao.getCantidadBecasUsadas(idEjercicio, idCcosto, "I", fechaPuesto, periodoId));	
		int cantidadP = Integer.parseInt(becPuestoDao.getCantidadBecasUsadas(idEjercicio, idCcosto, "P", fechaPuesto, periodoId));
		int cantidadM = Integer.parseInt(becPuestoDao.getCantidadBecasUsadas(idEjercicio, idCcosto, "M", fechaPuesto, periodoId));		
				
		//TRAER EL TIPO DE LA BECA BASICA
		String tipoBasica 			= becTipoDao.getTipo(idEjercicio, "B");
		
		String meses = "";
		BecTipo becBasica = new BecTipo();
		if (becTipoDao.existeReg(idEjercicio, tipoBasica)){
			becBasica = becTipoDao.mapeaRegId(idEjercicio, tipoBasica);
			meses = becBasica.getMeses();
			if(meses!=null && !meses.equals("null")){
				meses 	= meses.substring(1);
				meses 	= meses.replace('-', ',');
			}
		}
		
		// PRESUPUESTO BECA BASICA
		String presBasica = contMovimientoDao.getSumaCcosto(becBasica.getPorcentaje(), meses, becBasica.getCuenta(), idEjercicio, idCcosto);		
		
		//TRAER EL TIPO DE LA BECA ADICIONAL
		String tipoAdicional			= becTipoDao.getTipo(idEjercicio, "A");
		
		meses = "";
		BecTipo becAdicional = new BecTipo();
		if (becTipoDao.existeReg(idEjercicio, tipoBasica)){
			becAdicional = becTipoDao.mapeaRegId(idEjercicio, tipoAdicional);
			meses = becAdicional.getMeses();
			if(meses!=null && !meses.equals("null")){
				meses 	= meses.substring(1);
				meses 	= meses.replace('-', ',');
			}
		}
		
		// PRESUPUESTO BECA ADICIONAL
		String presAdicional = contMovimientoDao.getSumaCcosto(becAdicional.getPorcentaje(), meses, becAdicional.getCuenta(), idEjercicio, idCcosto);
		
		
		//TRAER EL TIPO DE LA BECA INSTITUCIONAL
		String tipoInstBasica	= becTipoDao.getTipo( idEjercicio, "I");
		
		//PRESUPUESTO BECA BASICA
		meses = "";
		BecTipo becInstBasica = new BecTipo();
		if (becTipoDao.existeReg(idEjercicio, tipoBasica)){
			becInstBasica = becTipoDao.mapeaRegId(idEjercicio, tipoInstBasica);
			meses = becInstBasica.getMeses();
			if(meses!=null && !meses.equals("null")){
				meses 	= meses.substring(1);
				meses 	= meses.replace('-', ',');
			}
		}		

		String presInstBasica = contMovimientoDao.getSumaCcosto(becInstBasica.getPorcentaje(), meses, becInstBasica.getCuenta(), idEjercicio, idCcosto);
		
		String usadoBasica 			= becAcuerdoDao.getBecaBasicaUsado(idEjercicio, tipoBasica, tipoAdicional, idCcosto, fechaPuesto, periodoId);
		String usadoAdicional 		= becAcuerdoDao.getBecaAdicionalUsado(idEjercicio, tipoAdicional, idCcosto, fechaPuesto, periodoId);
		String usadoInstitucional	= becAcuerdoDao.getBecaInstitucionalUsado(idEjercicio, tipoInstBasica, idCcosto, fechaPuesto, periodoId);
		
		List<BecPuestoAlumno> lisPuestosAlumno 	= becPuestoAlumnoDao.getListPuestosFecha(fechaPuesto, idEjercicio, idCcosto, categoriaId, "ORDER BY PUESTO_ID DESC");
		List<BecAcuerdo> lisAsociados 			= becAcuerdoDao.lisAsociados(idEjercicio, tiposRegulares, " ORDER BY CODIGO_PERSONAL, PUESTO_ID");
		List<BecInformeAlumno> lisInformes 		= becInformeAlumnoDao.lisInformesPorEjercicio(idEjercicio, " ORDER BY ORDEN");
		
		HashMap<String,BecContrato> mapaBecContrato 	= becContratoDao.mapaBecContrato(idEjercicio);
		HashMap<String,ContCcosto> mapaCostos 			= contCcostoDao.mapaCentrosCosto(idEjercicio);
		HashMap<String,BecCategoria> mapaCategorias 	= becCategoriaDao.mapaCategorias();
		HashMap<String, String> mapaAlumnos			 	= alumPersonalDao.mapaAlumnosEnPuestos(idEjercicio);
		HashMap<String, String> mapaInscritos		 	= inscritosDao.getMapaInscritos();
		HashMap<String,String> mapaFoliosDeAcuerdos		= becAcuerdoDao.mapaAcuerdosEnDepto(idEjercicio, idCcosto);
		HashMap<String,BecAcuerdo> mapaAcuerdos			= becAcuerdoDao.mapaAcuerdos(idEjercicio);
		HashMap<String,String> mapaFinancieros			= becAcuerdoDao.mapaAcuerdosFinancieros(idEjercicio);
		HashMap<String,String> mapaAsociados			= becAcuerdoDao.mapaAsociados(idEjercicio, tiposBasicos);
		HashMap<String,String> mapaVigentes				= becAcuerdoDao.mapaVigentes(idEjercicio, tiposBasicos);
		HashMap<String,BecInforme> mapaInformes			= becInformeDao.mapaInformes();
		HashMap<String,String> mapaEvaluaciones			= becInformeAlumnoDao.mapaEvaluacionPorAlumno(idEjercicio);
		
		modelo.addAttribute("adminBecas",adminBecas);		
		modelo.addAttribute("periodoNombre",periodoNombre);
		modelo.addAttribute("becPlazas",becPlazas);				
		modelo.addAttribute("cantidadB",cantidadB);
		modelo.addAttribute("cantidadT",cantidadT);
		modelo.addAttribute("cantidadI",cantidadI);
		modelo.addAttribute("cantidadP",cantidadP);
		modelo.addAttribute("cantidadM",cantidadM);
		modelo.addAttribute("tipoBasica",tipoBasica);
		modelo.addAttribute("presBasica",presBasica);
		modelo.addAttribute("tipoAdicional",tipoAdicional);
		modelo.addAttribute("presAdicional",presAdicional);
		modelo.addAttribute("tipoInstBasica",tipoInstBasica);
		modelo.addAttribute("presInstBasica",presInstBasica);
		modelo.addAttribute("usadoBasica",usadoBasica);
		modelo.addAttribute("usadoAdicional",usadoAdicional);
		modelo.addAttribute("usadoInstitucional",usadoInstitucional);
		
		modelo.addAttribute("lisPuestosAlumno",lisPuestosAlumno);
		modelo.addAttribute("lisAsociados",lisAsociados);
		modelo.addAttribute("lisInformes",lisInformes);
		
		modelo.addAttribute("mapaBecContrato",mapaBecContrato);
		modelo.addAttribute("mapaCostos",mapaCostos);
		modelo.addAttribute("mapaCategorias",mapaCategorias);
		modelo.addAttribute("mapaAlumnos",mapaAlumnos);
		modelo.addAttribute("mapaInscritos",mapaInscritos);
		modelo.addAttribute("mapaFoliosDeAcuerdos",mapaFoliosDeAcuerdos);
		modelo.addAttribute("mapaAcuerdos",mapaAcuerdos);
		modelo.addAttribute("mapaFinancieros",mapaFinancieros);
		modelo.addAttribute("mapaAsociados",mapaAsociados);
		modelo.addAttribute("mapaVigentes",mapaVigentes);
		modelo.addAttribute("mapaInformes",mapaInformes);
		modelo.addAttribute("mapaEvaluaciones",mapaEvaluaciones);
		
		return "taller/puesto/puestoAlumno";
	}	
}