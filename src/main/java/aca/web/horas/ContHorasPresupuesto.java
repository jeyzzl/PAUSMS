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

import aca.emp.spring.EmpPeriodoDao;
import aca.emp.spring.EmpRango;
import aca.emp.spring.EmpTipoPago;
import aca.financiero.spring.ContCcosto;
import aca.financiero.spring.ContCcostoDao;
import aca.carga.spring.Carga;
import aca.emp.spring.EmpHorasDao;
import aca.emp.spring.EmpHorasPres;

@Controller
public class ContHorasPresupuesto{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	
	
	@Autowired
	private aca.catalogo.spring.CatPeriodoDao catPeriodoDao;
	
	@Autowired
	private aca.carga.spring.CargaDao cargaDao;
	
	@Autowired
	private aca.catalogo.spring.CatFacultadDao catFacultadDao;
	
	@Autowired
	private aca.catalogo.spring.CatCarreraDao catCarreraDao;
	
	@Autowired
	private aca.emp.spring.EmpHorasPresDao empHorasPresDao;
	
	@Autowired
	private ContCcostoDao contCcostoDao;
	
	@Autowired
	private EmpHorasDao empHorasDao;
	
	@RequestMapping("/horas/presupuesto/lista")
	public String horasHorasElegir(HttpServletRequest request, Model modelo){
	
		String cargaSesion 		= "000000";
		String periodoSesion 	= "0000";
		String codigoPersonal	= "0";
		String year 			= aca.util.Fecha.getHoy().substring(6, 10);
		HttpSession sesion = ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	cargaSesion 		= (String)sesion.getAttribute("cargaId");
        	periodoSesion 		= cargaDao.getPeriodo(cargaSesion);
        	codigoPersonal		= (String)sesion.getAttribute("codigoPersonal");
        }
		
		String periodoId 		= request.getParameter("PeriodoId")==null?"0000":request.getParameter("PeriodoId");
		String cargaId			= request.getParameter("CargaId")==null?"000000":request.getParameter("CargaId");
		
		if (periodoId.equals("0000") && cargaId.equals("000000") && !cargaSesion.equals("000000")){
			periodoId 	= periodoSesion;
			cargaId 	= cargaSesion;
		}else if (!cargaId.equals("000000") && sesion!=null){
			sesion.setAttribute("cargaId", cargaId);
		}
		
		List<aca.catalogo.spring.CatPeriodo> lisPeriodos	= catPeriodoDao.getListAll(" ORDER BY PERIODO_ID DESC");
		List<aca.carga.spring.Carga> lisCargas				= cargaDao.getListPeriodo(periodoId," ORDER BY CARGA_ID");
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
		
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("lisPresupuestos", lisPresupuestos);
		modelo.addAttribute("mapFacultades", mapFacultades);
		modelo.addAttribute("mapCarreras", mapCarreras);
		modelo.addAttribute("mapDeptos", mapDeptos);
		modelo.addAttribute("mapGastos", mapGastos);
		
		return "horas/presupuesto/lista";
	}
	
	@RequestMapping("/horas/presupuesto/agregar")
	public String horasPresupuestoAgregar(HttpServletRequest request, Model modelo){		
		
		String cargaId 			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String departamentoId	= request.getParameter("DepartamentoId")==null?"0":request.getParameter("DepartamentoId");

		EmpHorasPres empHorasPres = new EmpHorasPres();
		if (empHorasPresDao.existeReg(departamentoId, cargaId)){
			empHorasPres = empHorasPresDao.mapeaRegId(departamentoId, cargaId);
    	}
		
        modelo.addAttribute("cargaId", cargaId);
        modelo.addAttribute("departamentoId", departamentoId);
        modelo.addAttribute("empHorasPres", empHorasPres);
		
		return "horas/presupuesto/agregar";
	}
	
	@RequestMapping("/horas/presupuesto/grabar")
	public String horasRangoGrabar(HttpServletRequest request){
		
		String cargaId 			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String departamentoId	= request.getParameter("DepartamentoId")==null?"0":request.getParameter("DepartamentoId");
		String year 			= request.getParameter("Year")==null?aca.util.Fecha.getHoy().substring(6, 10):request.getParameter("Year");
		String saldoAnt 		= request.getParameter("SaldoAnt")==null?"0":request.getParameter("SaldoAnt");
		String importe 			= request.getParameter("Importe")==null?"0":request.getParameter("Importe");
		
		EmpHorasPres empHorasPres = new EmpHorasPres();
		
		empHorasPres.setCargaId(cargaId);
		empHorasPres.setDepartamentoId(departamentoId);
		empHorasPres.setYear(year);
		empHorasPres.setSaldoAnt(saldoAnt);
		empHorasPres.setImporte(importe);
        if (empHorasPresDao.existeReg(departamentoId, cargaId) ){
        	empHorasPresDao.updateReg(empHorasPres);        	
        }else{
        	empHorasPresDao.insertReg(empHorasPres);        	
        }
        
		return "redirect:/horas/presupuesto/lista";
	}
	
	@RequestMapping("/horas/presupuesto/borrar")
	public String horasRangoBorrar(HttpServletRequest request){
		
		String cargaId 			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String departamentoId	= request.getParameter("DepartamentoId")==null?"0":request.getParameter("DepartamentoId");
        
        if (empHorasPresDao.existeReg(departamentoId, cargaId)){
        	empHorasPresDao.deleteReg(departamentoId, cargaId);
        }
        
		return "redirect:/horas/presupuesto/lista";
	}
	
}