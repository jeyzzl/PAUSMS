package aca.web.horas;

import java.util.List;
import java.util.HashMap;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.emp.spring.EmpContrato;
import aca.emp.spring.EmpContratoDao;
import aca.emp.spring.EmpDocEmpDao;
import aca.emp.spring.EmpHorasDao;
import aca.emp.spring.EmpRango;
import aca.emp.spring.EmpRangoDao;
import aca.emp.spring.EmpRangoEmpDao;
import aca.emp.spring.EmpTipoPago;
import aca.emp.spring.EmpTipoPagoDao;
import aca.plan.spring.MapaCursoDao;
import aca.vista.spring.Maestros;
import aca.vista.spring.MaestrosDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;

@Controller
public class ContHorasReporte{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	private EmpHorasDao empHorasDao;
	
	@Autowired
	private MaestrosDao maestrosDao;		
	
	@Autowired
	private CatFacultadDao catFacultadDao;
	
	@Autowired
	private CatCarreraDao catCarreraDao;
	
	@Autowired
	private MapaCursoDao mapaCursoDao;
	
	@Autowired
	private EmpTipoPagoDao empTipoPagoDao;
	
	@Autowired
	private EmpRangoEmpDao empRangoEmpDao;

	@Autowired
	private EmpRangoDao empRangoDao;
	
	@Autowired
	EmpContratoDao empContratoDao;
	
	@Autowired
	EmpDocEmpDao empDocEmpDao;
	
	
	
	@RequestMapping("/horas/reporte/menu")
	public String horasReporteMenu(HttpServletRequest request, Model modelo){
		
		return "horas/reporte/menu";
	}
	
	@RequestMapping("/horas/reporte/reporte")
	public String horasReporteReporte(HttpServletRequest request, Model modelo){
		
		String estado 		= request.getParameter("Estado")==null?"S":request.getParameter("Estado");		
		String fecha 		= request.getParameter("Fecha")==null?aca.util.Fecha.getHoy():request.getParameter("Fecha");
		String cargaId 		= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String tipo 		= request.getParameter("Tipo")==null?"T":request.getParameter("Tipo");
		
		List<aca.emp.spring.EmpHoras> listaHoras	= empHorasDao.lisFiltros(estado,fecha,cargaId, tipo," ORDER BY ENOC.FACULTAD(CARRERA_ID), ENOC.CARRERA_NIVEL(CARRERA_ID), CARRERA_ID, ENOC.USUARIO_APELLIDO(CODIGO_PERSONAL)");
		HashMap<String, String> mapaMaestros 		= maestrosDao.mapMaestroNombre("APELLIDOS");
		HashMap<String, String> mapaMaterias 		= mapaCursoDao.mapCursosEnHoras();
		HashMap<String, CatFacultad> mapaFacultades	= catFacultadDao.getMapFacultad("");
		HashMap<String, CatCarrera> mapaCarreras	= catCarreraDao.getMapAll("");
		HashMap<String, EmpTipoPago> mapaPagos 		= empTipoPagoDao.mapaTodos();
	    HashMap<String, String> mapaRangosEmp		= empRangoEmpDao.mapaRangosDelEmpleado();
	    HashMap<String, EmpRango> mapaRangos		= empRangoDao.mapaRangosEmp();

		modelo.addAttribute("mapaPagos", mapaPagos);
		modelo.addAttribute("listaHoras", listaHoras);
		modelo.addAttribute("mapaMaestros", mapaMaestros);
		modelo.addAttribute("mapaMaterias", mapaMaterias);		
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaRangosEmp",mapaRangosEmp);
		modelo.addAttribute("mapaRangos",mapaRangos);
		
		return "horas/reporte/reporte";
	}	
	
	@RequestMapping("/horas/reporte/concentrado")
	public String horasReporteConcentrado(HttpServletRequest request, Model modelo){
		
		String estado 		= request.getParameter("Estado")==null?"S":request.getParameter("Estado");
		String tipo 		= request.getParameter("Tipo")==null?"O":request.getParameter("Tipo");
		String cargas 		= request.getParameter("Cargas")==null?"0":request.getParameter("Cargas");
		int row 			= 0;
		String[] arreglo 	= cargas.split(",");
		for (String arr : arreglo) {
			row++;
			if (row==1) cargas = "'"+arr+"'"; else cargas += ",'"+arr+"'";  
		}
		
		List<CatFacultad> lisFacultades				= catFacultadDao.lisPorHoras(cargas, " ORDER BY NOMBRE_FACULTAD");
		List<Maestros> lisMaestros					= maestrosDao.listMaestrosHoras(estado, "'"+tipo+"'", cargas, " ORDER BY APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE");
		HashMap<String, String> mapaGastos 			= empHorasDao.mapaMaestroGasto(cargas, estado, tipo);
		HashMap<String, String> mapaMaterias 		= empHorasDao.mapaMaestroMaterias(cargas, estado, tipo);
		HashMap<String, String> mapaHoras 			= empHorasDao.mapaMaestroHoras(cargas, estado, tipo);
		HashMap<String, String> mapaHorasSemana		= empHorasDao.mapaMaestroHorasSemana(cargas, estado, tipo);
		
		modelo.addAttribute("lisFacultades", lisFacultades);
		modelo.addAttribute("lisMaestros", lisMaestros);
		modelo.addAttribute("mapaGastos", mapaGastos);
		modelo.addAttribute("mapaMaterias", mapaMaterias);
		modelo.addAttribute("mapaHoras", mapaHoras);
		modelo.addAttribute("mapaHorasSemana", mapaHorasSemana);
		
		return "horas/reporte/concentrado";
	}
	
	@RequestMapping("/horas/reporte/lista")
	public String horasReporteLista(HttpServletRequest request, Model modelo){
		
		String estado 	= request.getParameter("Estado")==null?"S":request.getParameter("Estado");
		String tipo 	= request.getParameter("Tipo")==null?"O":request.getParameter("Tipo");
		String cargas 	= request.getParameter("Cargas")==null?"0":request.getParameter("Cargas");
		int row 		= 0;
		String[] arreglo 	= cargas.split(",");
		for (String arr : arreglo) {
			row++;
			if (row==1) cargas = "'"+arr+"'"; else cargas += ",'"+arr+"'";  
		}
		
		List<Maestros> lisMaestros				= maestrosDao.listMaestrosHoras(estado, "'"+tipo+"'", cargas, " ORDER BY APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE");
		HashMap<String, String> mapaGastos 		= empHorasDao.mapaMaestroGastoTotal(cargas, estado, tipo);
		HashMap<String, String> mapaMaterias 	= empHorasDao.mapaMaestroMateriasTotal(cargas, estado, tipo);
		HashMap<String, String> mapaHoras 		= empHorasDao.mapaMaestroHorasTotal(cargas, estado, tipo);		
		
		modelo.addAttribute("lisMaestros", lisMaestros);
		modelo.addAttribute("mapaGastos", mapaGastos);
		modelo.addAttribute("mapaMaterias", mapaMaterias);
		modelo.addAttribute("mapaHoras", mapaHoras);
		
		return "horas/reporte/lista";
	}
	
	@RequestMapping("/horas/reporte/documentos")
	public String horasRepdocLista(HttpServletRequest request, Model modelo){
		
		String opcion = request.getParameter("Opcion")==null?"T":request.getParameter("Opcion");
		
		List<aca.vista.spring.Maestros> lista	= maestrosDao.listMaestroConDocumentos(opcion," ORDER BY APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE");
		HashMap<String, String> mapaImagenes 	= empDocEmpDao.mapaEmpImagen();
		
		modelo.addAttribute("lista", lista);
		modelo.addAttribute("mapaImagenes", mapaImagenes);
		
		return "horas/reporte/documentos";
	}		
	
	@RequestMapping("/horas/reporte/contratos")
	public String horasReporteContratos(HttpServletRequest request, Model modelo){
		String year 		= request.getParameter("Year")==null?aca.util.Fecha.getHoyReversa().substring(0,4):request.getParameter("Year");
		
		String codigoEmpleado	= "0";
		
		HttpSession sesion 	= ((HttpServletRequest) request).getSession();
		if (sesion != null) {
			codigoEmpleado		= (String)sesion.getAttribute("codigoEmpleado");
		}
		
		List<EmpContrato> lisContratos				= empContratoDao.lisContratos(year, "ORDER BY CODIGO_PERSONAL");
		HashMap<String, String> mapaMaestros 		= maestrosDao.mapMaestroNombre("APELLIDOS");
		
		modelo.addAttribute("codigoEmpleado", codigoEmpleado);
		modelo.addAttribute("lisContratos", lisContratos);
		modelo.addAttribute("mapaMaestros", mapaMaestros);
		
		return "horas/reporte/contratos";
	}
	
	
	@RequestMapping("/horas/reporte/firmar")
	public String horasReporteFirmar(HttpServletRequest request){
		
		String firma 		= request.getParameter("Firma")==null?"S":request.getParameter("Firma");
		String contratoId 	= request.getParameter("ContratoId")==null?"0":request.getParameter("ContratoId");
		
		EmpContrato contrato = new EmpContrato();
		
		if(empContratoDao.existeReg(contratoId)) {
			contrato = empContratoDao.mapeaRegId(contratoId);
			contrato.setFirma(firma);
			
			if(empContratoDao.updateReg(contrato)) {
			}
		}
		
		return "redirect:/horas/reporte/contratos";
	}
	
}