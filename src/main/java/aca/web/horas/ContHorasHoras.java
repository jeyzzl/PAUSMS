package aca.web.horas;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.carga.spring.Carga;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatPeriodo;
import aca.emp.spring.EmpConfirmar;
import aca.emp.spring.EmpConfirmarDao;
import aca.emp.spring.EmpContrato;
import aca.emp.spring.EmpContratoDao;
import aca.emp.spring.EmpHoras;
import aca.emp.spring.EmpHorasDao;
import aca.emp.spring.EmpHorasPres;
import aca.emp.spring.EmpHorasPresDao;
import aca.emp.spring.EmpPeriodoDao;
import aca.emp.spring.EmpTipoPago;
import aca.financiero.spring.ContCcosto;
import aca.financiero.spring.ContCcostoDao;

@Controller
public class ContHorasHoras{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	private EmpPeriodoDao empPeriodoDao;
	
	@Autowired
	private EmpHorasDao empHorasDao;
	
	@Autowired
	private EmpHorasPresDao empHorasPresDao;

	@Autowired
	private ContCcostoDao contCcostoDao;

	@Autowired
	private aca.vista.spring.MaestrosDao maestrosDao;
	
	@Autowired
	private aca.catalogo.spring.CatPeriodoDao catPeriodoDao;
	
	@Autowired
	private aca.carga.spring.CargaDao cargaDao;		
	
	@Autowired
	private aca.catalogo.spring.CatFacultadDao catFacultadDao;
	
	@Autowired
	private aca.catalogo.spring.CatCarreraDao catCarreraDao;
	
	@Autowired
	private aca.acceso.spring.AccesoDao accesoDao;
	
	@Autowired
	private aca.plan.spring.MapaCursoDao mapaCursoDao;		
	
	@Autowired
	private aca.emp.spring.EmpTipoPagoDao empTipoPagoDao;	
	
	@Autowired
	private EmpContratoDao empContratoDao;
	
	@Autowired
	EmpConfirmarDao empConfirmarDao;
	
	@RequestMapping("/horas/horas/elegir")
	public String horasHorasElegir(HttpServletRequest request, Model modelo){	
	
		HttpSession sesion		= null;
		String cargaSesion 		= "000000";
		String periodoSesion 	= "0000";
		String codigoPersonal 	= "0";
		sesion = ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	cargaSesion 		= (String)sesion.getAttribute("cargaId");
        	periodoSesion 		= cargaDao.getPeriodo(cargaSesion);
        	codigoPersonal		= (String)sesion.getAttribute("codigoPersonal");
        }
        
		boolean esAdmin 		= accesoDao.esAdministrador(codigoPersonal);
		boolean periodoActivo	= empPeriodoDao.periodoActivo(aca.util.Fecha.getHoy());
		String periodoId 		= request.getParameter("PeriodoId")==null?"0000":request.getParameter("PeriodoId");
		String cargaId			= request.getParameter("CargaId")==null?"000000":request.getParameter("CargaId");
		String filtro			= request.getParameter("Filtro")==null?"I":request.getParameter("Filtro");
		String tipo 			= request.getParameter("Tipo")==null?"P":request.getParameter("Tipo");
		
		if (periodoId.equals("0000") && cargaId.equals("000000") && !cargaSesion.equals("000000")){
			periodoId 	= periodoSesion;
			cargaId 	= cargaSesion; 
		}else if (!cargaId.equals("000000") && sesion!=null){
			sesion.setAttribute("cargaId", cargaId);
		}		
		
		List<CatPeriodo> lisPeriodos						= catPeriodoDao.getListAll(" ORDER BY PERIODO_ID DESC");
		List<Carga> lisCargas								= cargaDao.getListPeriodo(periodoId," ORDER BY CARGA_ID");
		List<CatCarrera> lisCarreras						= null;
		
		HashMap<String, CatFacultad> mapFacultades			= catFacultadDao.getMapFacultad("");
		HashMap<String, CatCarrera> mapCarreras 			= catCarreraDao.getMapAll("");
		HashMap<String, String> mapPresupuestoCarrera		= empHorasDao.mapPresupuestoCarrera(cargaId, tipo);
		HashMap<String, String> mapConfirmadas				= empConfirmarDao.mapaPorCargayTipo(cargaId, tipo);
		
		if (esAdmin){
			if (filtro.equals("I")){
				lisCarreras = catCarreraDao.lisConHoras(cargaId, "ORDER BY FACULTAD_ID, NIVEL_ID, NOMBRE_CARRERA");
			}else{
				lisCarreras = catCarreraDao.getListAll("ORDER BY FACULTAD_ID, NIVEL_ID, NOMBRE_CARRERA");
			}	
		}else{
			lisCarreras = catCarreraDao.getListAutorizadas(codigoPersonal, "ORDER BY FACULTAD_ID, NIVEL_ID, NOMBRE_CARRERA");
		}
		
		HashMap<String, String> mapEstados 					= empHorasDao.mapEstadoPorCarrera(cargaId);
		HashMap<String, String> mapEnContrato 				= empHorasDao.mapEnContrato(cargaId);
		HashMap<String, String> mapSinContrato				= empHorasDao.mapSinContrato(cargaId);
		
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("periodoActivo", periodoActivo);
		modelo.addAttribute("tipo", tipo);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("lisCarreras", lisCarreras);
		modelo.addAttribute("mapFacultades", mapFacultades);
		modelo.addAttribute("mapEstados", mapEstados);
		modelo.addAttribute("mapCarreras", mapCarreras);
		modelo.addAttribute("mapPresupuestoCarrera", mapPresupuestoCarrera);
		modelo.addAttribute("mapEnContrato", mapEnContrato);
		modelo.addAttribute("mapSinContrato", mapSinContrato);
		modelo.addAttribute("mapConfirmadas", mapConfirmadas);
		
		return "horas/horas/elegir";
	}
	
	@RequestMapping("/horas/horas/cargar")
	public String horasHorasCargar(HttpServletRequest request, Model modelo){
		
		String carreraId 	= request.getParameter("CarreraId")==null?"00000":request.getParameter("CarreraId");
		String tipo			= request.getParameter("Tipo")==null?"P":request.getParameter("Tipo");
		
		HttpSession sesion		= null;		
		sesion = ((HttpServletRequest)request).getSession();
        if (sesion!=null){        	
        	sesion.setAttribute("carreraId", carreraId);
        	sesion.setAttribute("tipo", tipo);
        }    
        
		return "redirect:/horas/horas/registro";
	}
	
	@RequestMapping("/horas/horas/registro")
	public String horasHorasRegistro(HttpServletRequest request, Model modelo){
		
		String cargaId			= "000000";
		String carreraId 		= "00000";
		String tipo 			= "-";
		String codigoPersonal	= "0";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){        	
        	carreraId 			= (String)sesion.getAttribute("carreraId");
        	cargaId 			= (String)sesion.getAttribute("cargaId");
        	tipo				= (String)sesion.getAttribute("tipo");
        	codigoPersonal		= (String)sesion.getAttribute("codigoPersonal");
        }
        
        boolean periodoActivo	= empPeriodoDao.periodoActivo(aca.util.Fecha.getHoy());
        boolean esDirector 		= catFacultadDao.esDirector(codigoPersonal);
		String nombreCarrera 	= catCarreraDao.getNombreCarrera(carreraId);
		
		List<aca.emp.spring.EmpHoras> listaHoras		= empHorasDao.lisPorCarreraYCarga(carreraId, cargaId, " ORDER BY BLOQUE_ID, CODIGO_PERSONAL");
		HashMap<String, String> mapaMaestros 			= maestrosDao.mapaMaestroCorto("APELLIDOS");
		HashMap<String, String> mapaEstados 			= maestrosDao.mapaMaestroEstado();
		HashMap<String, String> mapaMaterias 			= mapaCursoDao.mapCursos(carreraId);		
		HashMap<String, EmpTipoPago> mapaPagos 			= empTipoPagoDao.mapaTodos();
		HashMap<String, EmpContrato> mapaContratos		= empContratoDao.mapaContratos();
		HashMap<String, EmpConfirmar> mapaConfirmados	= empConfirmarDao.mapaPorCarga(cargaId);
		
		
		modelo.addAttribute("tipo", tipo);
		modelo.addAttribute("esDirector", esDirector);
		modelo.addAttribute("listaHoras", listaHoras);
		modelo.addAttribute("mapaMaestros", mapaMaestros);
		modelo.addAttribute("mapaEstados", mapaEstados);
		modelo.addAttribute("mapaMaterias", mapaMaterias);
		modelo.addAttribute("nombreCarrera", nombreCarrera);
		modelo.addAttribute("periodoActivo", periodoActivo);
		modelo.addAttribute("mapaPagos", mapaPagos);
		modelo.addAttribute("mapaContratos", mapaContratos);
		modelo.addAttribute("mapaConfirmados", mapaConfirmados);
		
		return "horas/horas/registro";
	}
	
	@RequestMapping("/horas/horas/confirmar")
	public String horasHorasConfirmar(HttpServletRequest request, Model modelo){
		String cargaId			= "000000";
		String carreraId 		= "00000";		
		String usuario			= "0";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){        	
        	carreraId 	= (String)sesion.getAttribute("carreraId");
        	cargaId 	= (String)sesion.getAttribute("cargaId");
        	usuario 	= (String)sesion.getAttribute("codigoPersonal");
        }      
		
		List<EmpHoras> lisHoras	= empHorasDao.lisPorCarreraYCarga(carreraId, cargaId, " ORDER BY BLOQUE_ID,CODIGO_PERSONAL");
		EmpConfirmar empConfirmar = new EmpConfirmar();
		for (EmpHoras horas : lisHoras) {
			empConfirmar.setCodigoPersonal(horas.getCodigoPersonal());
			empConfirmar.setFolio(horas.getFolio());
			empConfirmar.setUsuario(usuario);	
			String confirmado = request.getParameter(horas.getCodigoPersonal()+"-"+horas.getFolio())==null?"N":"S";
			if (confirmado.equals("S")){
				if (empConfirmarDao.existeReg(horas.getCodigoPersonal(),horas.getFolio())==false){
					empConfirmarDao.insertReg(empConfirmar);
				}
			}else{
				if (empConfirmarDao.existeReg(horas.getCodigoPersonal(),horas.getFolio())==true){
					empConfirmarDao.deleteReg(horas.getCodigoPersonal(), horas.getFolio());
				}
			}
		}		
		return "redirect:/horas/horas/registro";
	}
	
	@RequestMapping("/horas/horas/accion")
	public String horasHorasAccion(HttpServletRequest request, Model modelo){
		
		aca.emp.spring.EmpHoras empHoras = new aca.emp.spring.EmpHoras();
		
		String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		String codigoEmpleado 	= request.getParameter("CodigoEmpleado")==null?"0":request.getParameter("CodigoEmpleado");
		String folio 			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String maestroNombre 	= "-";
		String maestroEstado 	= "X";
		
		String carreraId 	= "00000";		
		HttpSession sesion		= null;		
		sesion = ((HttpServletRequest)request).getSession();
        if (sesion!=null){        	
        	carreraId = (String)sesion.getAttribute("carreraId");
        }
		String nombreCarrera = catCarreraDao.getNombreCarrera(carreraId);
		
		if (accion.equals("1")){
			empHoras 		= empHorasDao.mapeaRegId(codigoEmpleado, folio);
			maestroNombre 	= maestrosDao.getNombreCorto(codigoEmpleado,"APELLIDO");
			maestroEstado	= maestrosDao.getEstado(codigoEmpleado);
		}
				
		List<aca.vista.spring.Maestros> lisMaestros		= maestrosDao.lisMaestrosActivos("ORDER BY CODIGO_PERSONAL");
		List<aca.plan.spring.MapaCurso> lisMaterias		= mapaCursoDao.getListCarrera(carreraId, "ORDER BY PLAN_ID, NOMBRE_CURSO");
		List<aca.emp.spring.EmpTipoPago> lisPagos		= empTipoPagoDao.lisTodos(" ORDER BY TIPOPAGO_ID");
        
		modelo.addAttribute("empHoras", empHoras);
		modelo.addAttribute("nombreCarrera", nombreCarrera);
		modelo.addAttribute("maestroNombre", maestroNombre);
        modelo.addAttribute("maestroEstado", maestroEstado);
        
		modelo.addAttribute("lisMaestros", lisMaestros);
		modelo.addAttribute("lisMaterias", lisMaterias);
		modelo.addAttribute("lisPagos", lisPagos);
		
		return "horas/horas/accion";
	}	
	
	@RequestMapping("/horas/horas/grabar")
	public String horasHorasGrabar(HttpServletRequest request, Model modelo){
		
		String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		String codigoEmpleado 	= request.getParameter("CodigoEmpleado")==null?"0":request.getParameter("CodigoEmpleado");
		String folio 			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String contratoId 		= request.getParameter("ContratoId")==null?"0":request.getParameter("ContratoId");
		String tipoPagoId 		= request.getParameter("TipoPago")==null?"0":request.getParameter("TipoPago");
		String bloqueId			= request.getParameter("BloqueId")==null?"0":request.getParameter("BloqueId");
		String estado			= request.getParameter("Estado")==null?"S":request.getParameter("Estado");
		String mensaje 			= "-";
		
		boolean existe			= empHorasDao.existeReg(codigoEmpleado, folio); 
		
		aca.emp.spring.EmpHoras empHoras = new aca.emp.spring.EmpHoras();
		if (!existe){
			folio = empHorasDao.maximoReg(codigoEmpleado);
		}
		
		empHoras.setCodigoPersonal(codigoEmpleado);
		empHoras.setFolio(folio);
		empHoras.setCursoId(request.getParameter("CursoId"));
		empHoras.setMateria(request.getParameter("Materia"));
		empHoras.setTipo(request.getParameter("Tipo"));
		empHoras.setFechaIni(request.getParameter("FechaIni"));
		empHoras.setFechaFin(request.getParameter("FechaFin"));
		empHoras.setFrecuencia(request.getParameter("Frecuencia"));
		empHoras.setSemanas(request.getParameter("Semanas"));
		empHoras.setPrecio(request.getParameter("Precio")==null?"0":request.getParameter("Precio"));
		empHoras.setEstado(estado);
		empHoras.setFecha(aca.util.Fecha.getHoy());
		empHoras.setCarreraId(request.getParameter("Carrera"));
		empHoras.setCargaId(request.getParameter("CargaId"));
		empHoras.setContratoId(contratoId);
		empHoras.setTipoPagoId(tipoPagoId);
		empHoras.setBloqueId(bloqueId);
		
		if (existe && accion.equals("1")){
			if (empHorasDao.updateReg(empHoras)) {
				mensaje = "Modificado...";			
			}else {
				mensaje = "Error al modificar...";				
			}
		}else if (accion.equals("1")){
			if (empHorasDao.insertReg(empHoras)) {
				mensaje = "Grabado...";				
			}else {
				mensaje = "Error al grabar...";			
			}			
		}
		
		return "redirect:/horas/horas/registro?Mensaje="+mensaje;
	}
	
	@RequestMapping("/horas/horas/borrar")
	public String horasHorasBorrar(HttpServletRequest request, Model modelo) {
		
		String codigoEmpleado	= request.getParameter("CodigoEmpleado")==null?"":request.getParameter("CodigoEmpleado");
		String folio			= request.getParameter("Folio")==null?"":request.getParameter("Folio");
		
		empHorasDao.deleteReg(codigoEmpleado, folio);
		
		return "redirect:/horas/horas/registro";
	}
	
	@RequestMapping("/horas/horas/estado")
	public String horasHorasEstado(HttpServletRequest request, Model modelo) {
		
		String codigoEmpleado	= request.getParameter("CodigoEmpleado")==null?"0":request.getParameter("CodigoEmpleado");
		String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String estado			= request.getParameter("Estado")==null?"S":request.getParameter("Estado");
		
		empHorasDao.updateEstado(codigoEmpleado, folio, estado);
		
		return "redirect:/horas/horas/registro";
	}
	
	@RequestMapping("/horas/horas/estadoCarrera")
	public String horasHorasAutorizar(HttpServletRequest request, Model modelo) {
		
		String estado 		= request.getParameter("Estado")==null?"S":request.getParameter("Estado");
		String cargaId 		= "000000";
		String carreraId 	= "00000";
		
		HttpSession sesion		= null;		
		sesion = ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	cargaId 	= (String)sesion.getAttribute("cargaId");
        	carreraId 	= (String)sesion.getAttribute("carreraId");
        }		
		
		empHorasDao.estadoCarrera(cargaId, carreraId, estado);
		
		return "redirect:/horas/horas/registro";
	}
	
	@RequestMapping("/horas/horas/presupuesto")
	public String horasHorasPresupuesto(HttpServletRequest request, Model modelo){
		
		String year 			= aca.util.Fecha.getHoy().substring(6, 10);
		
		String cargaId			= request.getParameter("CargaId")==null?"000000":request.getParameter("CargaId");
		String tipo			= request.getParameter("Tipo")==null?"P":request.getParameter("Tipo");
		
		List<EmpHorasPres> lisPresupuestos					= empHorasPresDao.lisPorCarga(cargaId, " ORDER BY DEPARTAMENTO_ID");
		HashMap<String, aca.catalogo.spring.CatFacultad> mapFacultades	= catFacultadDao.getMapFacultad("");
		HashMap<String, aca.catalogo.spring.CatCarrera> mapCarreras 	= catCarreraDao.getMapAll("");
		HashMap<String, ContCcosto> mapDeptos							= contCcostoDao.mapaDeptosPorEjercicio("001-"+year);
		HashMap<String, String> mapGastos								= new HashMap<String, String>(); 
		
		for (EmpHorasPres pres :lisPresupuestos) {
			double presSol 	= empHorasDao.gastoPorDepartamento(cargaId, pres.getDepartamentoId(),"S");
			double presAut 	= empHorasDao.gastoPorDepartamento(cargaId, pres.getDepartamentoId(),"A");
			double presNom 	= empHorasDao.gastoPorDepartamento(cargaId, pres.getDepartamentoId(),"N");
			mapGastos.put(pres.getDepartamentoId()+"S", String.valueOf(presSol));
			mapGastos.put(pres.getDepartamentoId()+"A", String.valueOf(presAut));
			mapGastos.put(pres.getDepartamentoId()+"N", String.valueOf(presNom));
		}
		
		modelo.addAttribute("lisPresupuestos", lisPresupuestos);
		modelo.addAttribute("mapFacultades", mapFacultades);
		modelo.addAttribute("mapCarreras", mapCarreras);
		modelo.addAttribute("mapDeptos", mapDeptos);
		modelo.addAttribute("mapGastos", mapGastos);
		modelo.addAttribute("tipo", tipo);
		
		return "horas/horas/presupuesto";
	}
	
}