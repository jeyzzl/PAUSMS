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

import aca.emp.spring.EmpConfirmar;
import aca.emp.spring.EmpConfirmarDao;
import aca.emp.spring.EmpContrato;
import aca.emp.spring.EmpContratoDao;
import aca.emp.spring.EmpHoras;
import aca.emp.spring.EmpHorasDao;
import aca.emp.spring.EmpTipoPago;
import aca.plan.spring.MapaCurso;
import aca.plan.spring.MapaCursoDao;
import aca.vista.spring.MaestrosDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;

@Controller
public class ContHorasContrato{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	private EmpContratoDao empContratoDao;
	
	@Autowired
	private EmpHorasDao empHorasDao;
	
	@Autowired
	private MaestrosDao maestrosDao;
	
	@Autowired
	private MapaCursoDao mapaCursoDao;
	
	@Autowired
	private CatCarreraDao catCarreraDao;
	
	@Autowired
	private CatFacultadDao catFacultadDao;
	
	@Autowired
	private aca.emp.spring.EmpTipoPagoDao empTipoPagoDao;
	
	@Autowired
	EmpConfirmarDao empConfirmarDao;
	
	
	@RequestMapping("/horas/contrato/lista")
	public String horasContratoLista(HttpServletRequest request, Model modelo){
		
		String yearActual		= aca.util.Fecha.getHoy().substring(6,10);
		String year 			= request.getParameter("Year")==null?yearActual:request.getParameter("Year");		
		
		List<String> lisEmpleados 						= empHorasDao.lisPorYear(year, " ORDER BY CODIGO_PERSONAL");
		HashMap<String, String> mapaMaestros			= maestrosDao.mapaMaestroEnHoras(year,"APELLIDOS");
		HashMap<String, String> mapaNumContratos		= empContratoDao.mapaNumContratos(year);
		HashMap<String, String> mapaFirmados			= empContratoDao.mapaFirmados(year);
		HashMap<String, String> mapaImporteContratos 	= empContratoDao.mapaImporteContratos(year);
		HashMap<String, String> mapaMaterias 			= empHorasDao.mapaMaterias(year);
		HashMap<String, String> mapaRegistradas			= empHorasDao.mapaRegistradas(year);
		HashMap<String, String> mapaPendientes 			= empHorasDao.mapaPendientes(year);
		
		modelo.addAttribute("lisEmpleados",lisEmpleados);
		modelo.addAttribute("mapaMaestros",mapaMaestros);
		modelo.addAttribute("mapaNumContratos",mapaNumContratos);
		modelo.addAttribute("mapaImporteContratos",mapaImporteContratos);
		modelo.addAttribute("mapaMaterias",mapaMaterias);
		modelo.addAttribute("mapaPendientes",mapaPendientes);
		modelo.addAttribute("mapaRegistradas",mapaRegistradas);
		modelo.addAttribute("mapaFirmados",mapaFirmados);
		
		return "horas/contrato/lista";
	}	
	
	@RequestMapping("/horas/contrato/listaMaterias")
	public String horasContratoListaMaterias(HttpServletRequest request, Model modelo){
		String yearActual		= aca.util.Fecha.getHoy().substring(6,10);
		String codigoEmpleado 	= request.getParameter("CodigoEmpleado")==null?"0":request.getParameter("CodigoEmpleado");
		String year 			= request.getParameter("Year")==null?yearActual:request.getParameter("Year");
		String nombreEmpleado	= maestrosDao.getNombreMaestro(codigoEmpleado, "NOMBRE");
		
		List<EmpHoras> lisHoras 						= empHorasDao.lisPorMaestro(codigoEmpleado, year," ORDER BY CURSO_ID");
		HashMap<String, EmpConfirmar> mapaConfirmados	= empConfirmarDao.mapaPorEmpleado(codigoEmpleado);
		HashMap<String, String> mapaCursos				= mapaCursoDao.mapCursosHoras(codigoEmpleado);
		
		modelo.addAttribute("codigoEmpleado",codigoEmpleado);
		modelo.addAttribute("nombreEmpleado",nombreEmpleado);
		modelo.addAttribute("lisHoras",lisHoras);
		modelo.addAttribute("mapaConfirmados",mapaConfirmados);
		modelo.addAttribute("mapaCursos",mapaCursos);
		
		return "horas/contrato/listaMaterias";
	}	
	
	@RequestMapping("/horas/contrato/contratos")
	public String horasContratoContratos(HttpServletRequest request, Model modelo){
		
		String yearActual		= aca.util.Fecha.getHoy().substring(6,10);
		String year 			= request.getParameter("Year")==null?yearActual:request.getParameter("Year");
		String empleadoId 		= request.getParameter("EmpleadoId")==null?"0":request.getParameter("EmpleadoId");
		String empleadoNombre 	= maestrosDao.getNombreMaestro(empleadoId, "NOMBRE");
		
		HttpSession sesion 	= ((HttpServletRequest) request).getSession();
		if (sesion != null) {
			if (!empleadoId.equals("0")) {
				sesion.setAttribute("codigoEmpleado", empleadoId);
			}
		}		
		List<EmpContrato> lisContratos 					= empContratoDao.lisContratosEmpleado(empleadoId, year, " ORDER BY FECHA");
		HashMap<String,String> mapaMaterias				= empHorasDao.mapContratoMaterias();
		
		modelo.addAttribute("empleadoNombre",empleadoNombre);
		modelo.addAttribute("lisContratos",lisContratos);
		modelo.addAttribute("mapaMaterias",mapaMaterias);
		
		return "horas/contrato/contratos";
	}

	@RequestMapping("/horas/contrato/editar")	
	public String horasContratoEditar(HttpServletRequest request, Model modelo){
		
		String contratoId		= request.getParameter("ContratoId")==null?"0":request.getParameter("ContratoId");
		EmpContrato empContrato = new EmpContrato();
		String empleadoId		= "0";
		String empleadoNombre	= "-";
		
		HttpSession sesion 	= ((HttpServletRequest) request).getSession();
		if (sesion != null) {
			empleadoId		= (String)sesion.getAttribute("codigoEmpleado");
			empleadoNombre 	= maestrosDao.getNombreMaestro(empleadoId, "NOMBRE");
		}
		
		empContrato.setContratoId(contratoId);
        if (!contratoId.equals("0")) {
        	if (empContratoDao.existeReg(contratoId)) {
        		empContrato 	= empContratoDao.mapeaRegId(contratoId);  		
        	}
        }      	
		
        modelo.addAttribute("empleadoNombre",empleadoNombre);
		modelo.addAttribute("empContrato",empContrato);
		
		return "horas/contrato/editar";
	}	
	
	@RequestMapping("/horas/contrato/grabar")	
	public String horasContratoGrabar(HttpServletRequest request, Model modelo){
		
		String year 			= request.getParameter("Year")==null?"2019":request.getParameter("Year");
		String contratoId		= request.getParameter("ContratoId")==null?"0":request.getParameter("ContratoId");
		String codigoPersonal	= request.getParameter("CodigoPersonal")==null?"0":request.getParameter("CodigoPersonal");
		String fecha			= request.getParameter("Fecha")==null?"0":request.getParameter("Fecha");		
		String importe			= request.getParameter("Costo")==null?"0":request.getParameter("Costo");
		String comentario		= request.getParameter("Comentario")==null?"0":request.getParameter("Comentario");	
		String estado			= request.getParameter("Estado")==null?"0":request.getParameter("Estado");
		String fechaIni			= request.getParameter("FechaIni")==null?"0":request.getParameter("FechaIni");	
		String fechaFin			= request.getParameter("FechaFin")==null?"0":request.getParameter("FechaFin");
		String institucion		= request.getParameter("Institucion")==null?"0":request.getParameter("Institucion");
		String firma			= request.getParameter("Firma")==null?"0":request.getParameter("Firma");
        
		EmpContrato empContrato = new EmpContrato();
		
		if (contratoId.equals("0")) {			
			empContrato.setContratoId(empContratoDao.siguienteContrato(year));
		}else {
			empContrato.setContratoId(contratoId);
		}
		empContrato.setCodigoPersonal(codigoPersonal);
		empContrato.setFecha(fecha);
		empContrato.setCosto(importe);
		empContrato.setComentario(comentario);
		empContrato.setEstado(estado);
		empContrato.setFechaIni(fechaIni);
		empContrato.setFechaFin(fechaFin);
		empContrato.setInstitucion(institucion);
		empContrato.setFirma(firma);		
		if (empContratoDao.existeReg(contratoId)) {
			empContratoDao.updateReg(empContrato);
		}else{			
			empContratoDao.insertReg(empContrato);
		}	
		
		return "redirect:/horas/contrato/editar?Year="+year+"&ContratoId="+empContrato.getContratoId();
	}

	@RequestMapping("/horas/contrato/grabarContrato")	
	public String horasContratoGrabarContrato(HttpServletRequest request, Model modelo){
		
		String year 				= request.getParameter("Year")==null?"2019":request.getParameter("Year");
		String contratoId			= request.getParameter("ContratoId")==null?"0":request.getParameter("ContratoId");
		String codigoPersonal		= request.getParameter("CodigoPersonal")==null?"0":request.getParameter("CodigoPersonal");
		String mensaje 				= "-";
		int grabados				= 0;
		int borrados				= 0;
		
		EmpHoras empHoras 			= new EmpHoras();
		EmpContrato empContrato		= new EmpContrato();
		
		List<EmpHoras> lisMaterias 	= empHorasDao.lisPorContrato(codigoPersonal, contratoId, year, " ORDER BY CARGA_ID");
		
		for (EmpHoras horas : lisMaterias) {
			String dato = request.getParameter(codigoPersonal+String.valueOf(horas.getFolio()))==null?"N":request.getParameter(codigoPersonal+String.valueOf(horas.getFolio()));
			
			if(dato.equals("S")) {								
				if (empHorasDao.existeReg(codigoPersonal, String.valueOf(horas.getFolio()))) {
					empHoras = empHorasDao.mapeaRegId(codigoPersonal, String.valueOf(horas.getFolio()));
					empHoras.setContratoId(contratoId);
					if (empHorasDao.updateReg(empHoras)) {
						grabados++;
					}
				}	
			}else {				
				if (empHorasDao.existeReg(codigoPersonal, String.valueOf(horas.getFolio()))) {					
					empHoras = empHorasDao.mapeaRegId(codigoPersonal, String.valueOf(horas.getFolio()));
					if (!empHoras.getContratoId().equals("0")) { 
						empHoras.setContratoId("0");
						if (empHorasDao.updateReg(empHoras)) {
							borrados++;
						}
					}	
				}
			}		
		}
		
		double totalContrato 		= empHorasDao.totalContrato(contratoId);
		if (empContratoDao.existeReg(contratoId)) {
			empContrato = empContratoDao.mapeaRegId(contratoId);
			empContrato.setCosto(String.valueOf(totalContrato));
			empContratoDao.updateReg(empContrato);
		}	
		if (grabados>0 || borrados > 0) {
			mensaje = "1";
			
		}
		
		return "redirect:/horas/contrato/materias?Year="+year+"&ContratoId="+contratoId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/horas/contrato/materias")
	public String horasContratoMaterias(HttpServletRequest request, Model modelo){
			
		String yearActual			= aca.util.Fecha.getHoy().substring(6,10);	
		String year 				= request.getParameter("Year")==null?yearActual:request.getParameter("Year");
		String contratoId 			= request.getParameter("ContratoId")==null?"0":request.getParameter("ContratoId");		
		String empleadoNombre		= "-";		
		double totalContrato 		= empHorasDao.totalContrato(contratoId);
		EmpContrato empContrato		= new EmpContrato();		
		if (empContratoDao.existeReg(contratoId)) {
			empContrato 		= empContratoDao.mapeaRegId(contratoId);			
			empleadoNombre 		= maestrosDao.getNombreMaestro(empContrato.getCodigoPersonal(), "NOMBRE");
		}
		
		List<String> lisCarreras					= empHorasDao.lisCarreraEnContrato(contratoId);
		List<EmpHoras> lisMaterias 					= empHorasDao.lisPorContrato(empContrato.getCodigoPersonal(), contratoId, year, " ORDER BY ENOC.EMP_HORAS.FECHA_INI DESC");
		HashMap<String, String> mapaCursos			= mapaCursoDao.mapCursosHoras(empContrato.getCodigoPersonal());
		HashMap<String, String> mapaElegidas		= empHorasDao.mapContratoId(contratoId);
		HashMap<String, EmpTipoPago> mapaPagos	 	= empTipoPagoDao.mapaTodos();
		HashMap<String, CatCarrera> mapaCarreras 	= catCarreraDao.mapaCarreras();
		HashMap<String, CatFacultad> mapaFacultades = catFacultadDao.getMapFacultad("");
		HashMap<String, String> mapaImportes		= empHorasDao.mapaImportePorCarrera(contratoId);
		
		modelo.addAttribute("empContrato", empContrato);
		modelo.addAttribute("empleadoNombre", empleadoNombre);
		modelo.addAttribute("lisCarreras", lisCarreras);
		modelo.addAttribute("lisMaterias", lisMaterias);
		modelo.addAttribute("mapaCursos", mapaCursos);
		modelo.addAttribute("mapaElegidas", mapaElegidas);
		modelo.addAttribute("contratoId", contratoId);
		modelo.addAttribute("totalContrato", totalContrato);
		modelo.addAttribute("mapaPagos", mapaPagos);
		modelo.addAttribute("mapaCarreras",mapaCarreras);
		modelo.addAttribute("mapaFacultades",mapaFacultades);
		modelo.addAttribute("mapaImportes",mapaImportes);
		
		return "horas/contrato/materias";
	}	
	
	@RequestMapping("/horas/contrato/borrar")
	public String horasContratoBorrar(HttpServletRequest request, Model modelo){
		
		String year 			= request.getParameter("Year")==null?"2019":request.getParameter("Year");
		String contratoId		= request.getParameter("ContratoId")==null?"0":request.getParameter("ContratoId");
		String empleadoId		= "0";
			
		if (empContratoDao.existeReg(contratoId)) {
			empleadoId = empContratoDao.mapeaRegId(contratoId).getCodigoPersonal();
			empContratoDao.deleteReg(contratoId);
		}
		
		return "redirect:/horas/contrato/contratos?EmpleadoId="+empleadoId+"&Year="+year;
	}
	
	@RequestMapping("/horas/contrato/contratoPdf")
	public String horasContratoContratoPdf(HttpServletRequest request, Model modelo){
		
		// Es un solo pdf para todas las materias seleccionadas..
		// El pdf debe llevar El nombre de la UM..
		// El numero de contrato, las fechas de inicio y fin del contrato
		// el nombre del maestro..
		// La lista de materias que contempla..
		// en una tabla con los datos de cada materia en un renglón

		// En otro renglón resaltado deben aparecer los datos del Total a pagar y Días [16.0] - Salario diario [7,158.50] - Salario 15 [107,377.50]  - Salario 16 [114,536.00]
		// Los datos del salario..

		// y por ultimo una pequeña tabla, con la información de la distrubicion del gasto por escuelas..
		
		String year 				= request.getParameter("Year")==null?"2019":request.getParameter("Year");
		String contratoId 			= request.getParameter("ContratoId")==null?"0":request.getParameter("ContratoId");		
		String empleadoNombre		= "-";		
		double totalContrato 		= empHorasDao.totalContrato(contratoId);
		EmpContrato empContrato		= new EmpContrato();
		
		if (empContratoDao.existeReg(contratoId)) {
			empContrato 		= empContratoDao.mapeaRegId(contratoId);			
			empleadoNombre 		= maestrosDao.getNombreMaestro(empContrato.getCodigoPersonal(), "NOMBRE");
		}
		
		List<String> lisCarreras					= empHorasDao.lisCarreraEnContrato(contratoId);
		List<EmpHoras> lisMaterias 					= empHorasDao.lisPorContrato(empContrato.getCodigoPersonal(), contratoId, " ORDER BY ENOC.EMP_HORAS.FECHA_INI DESC");
		HashMap<String, String> mapaCursos			= mapaCursoDao.mapCursosHoras(empContrato.getCodigoPersonal());
		HashMap<String, CatCarrera> mapaCarreras 	= catCarreraDao.mapaCarreras();
		HashMap<String, String> mapaImportes		= empHorasDao.mapaImportePorCarrera(contratoId);
		
		modelo.addAttribute("empContrato",empContrato);
		modelo.addAttribute("empleadoNombre",empleadoNombre);
		modelo.addAttribute("totalContrato",totalContrato);
		modelo.addAttribute("lisCarreras",lisCarreras);
		modelo.addAttribute("lisMaterias",lisMaterias);
		modelo.addAttribute("mapaCursos",mapaCursos);
		modelo.addAttribute("mapaCarreras",mapaCarreras);
		modelo.addAttribute("mapaImportes",mapaImportes);
		
		return "horas/contrato/contratoPdf";
	}
}
