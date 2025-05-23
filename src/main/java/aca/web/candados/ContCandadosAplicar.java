package aca.web.candados;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import aca.candado.spring.CandAlumno;
import aca.carga.spring.Carga;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatPeriodo;
import aca.catalogo.spring.CatTipoAlumno;
import aca.catalogo.spring.CatTipoAlumnoDao;
import aca.financiero.spring.FinSaldo;
import aca.vista.spring.Estadistica;
import aca.vista.spring.EstadisticaDao;
import aca.vista.spring.InscritosDao;

@Controller
public class ContCandadosAplicar {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	aca.vista.spring.AlumSaldosDao alumSaldosDao;
	
	@Autowired
	aca.alumno.spring.AlumPersonalDao alumPersonalDao;
	
	@Autowired
	aca.candado.spring.CandAlumnoDao candAlumnoDao;
	
	@Autowired
	private aca.catalogo.spring.CatPeriodoDao catPeriodoDao;
	
	@Autowired
	private aca.carga.spring.CargaDao cargaDao;
	
	@Autowired
	private EstadisticaDao estadisticaDao;
	
	@Autowired
	private CatCarreraDao catCarreraDao;
	
	@Autowired
	private CatTipoAlumnoDao catTipoAlumnoDao;
	
	@Autowired
	private InscritosDao inscritosDao;
	
	
	
	@RequestMapping("/candados/aplicar/candado")
	public String solicitudSalud(HttpServletRequest request, Model modelo){
		
		String periodoSesion 	= "0000";
		String cargaSesion 		= "000000";		
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
        if (sesion!=null){        	
        	periodoSesion 		= (String)sesion.getAttribute("periodo");
        	cargaSesion 		= (String)sesion.getAttribute("cargaId");        	
        }
		
		String periodoId 		= request.getParameter("PeriodoId")==null?"0000":request.getParameter("PeriodoId");
		String cargaId			= request.getParameter("CargaId")==null?"000000":request.getParameter("CargaId");
		String mostrar			= request.getParameter("Mostrar")==null?"N":request.getParameter("Mostrar");
		
		if (!periodoId.equals("0000") && !periodoId.equals(periodoSesion) && sesion!=null) {
			sesion.setAttribute("periodo", periodoId);			
			cargaId 	= "000000";
		}else if (!periodoId.equals("0000") && !cargaId.equals("000000") && sesion!=null){
			sesion.setAttribute("cargaId", cargaId);			
		}else if (periodoId.equals("0000") && cargaId.equals("000000") && !cargaSesion.equals("000000")){
			periodoId 	= periodoSesion;
			cargaId 	= cargaSesion;
		}
		
		List<CatPeriodo> lisPeriodos			= catPeriodoDao.getListAll(" ORDER BY PERIODO_ID DESC");
		//if (periodoId.equals("0000") && lisPeriodos.size()>=1) periodoId = lisPeriodos.get(0).getPeriodoId();
		
		List<Carga> lisCargas					= cargaDao.getListPeriodo(periodoId," ORDER BY CARGA_ID");
		//if (cargaId.equals("000000") && lisCargas.size()>=1) cargaId = lisCargas.get(0).getCargaId();
		
		List<Estadistica> lisInscritos			= estadisticaDao.lisInscritosEnCarga(cargaId, " ORDER BY FACULTAD_ID, CARRERA_ID");
		//System.out.println("Datos:"+cargaId+":"+periodoId+":"+cargaSesion+":"+periodoSesion+":"+lisInscritos.size());
		
		HashMap<String,String> mapaDeudores		= new HashMap<String,String>(); 
		if (mostrar.equals("S")) {
			for (Estadistica est : lisInscritos) {			
				FinSaldo finSaldo 	= new FinSaldo();
				boolean encontro 	= false;
				try {		
					RestTemplate restTemplate = new RestTemplate();
					finSaldo = restTemplate.getForObject("http://172.16.251.110:8080/infor/reportes/api/fe/saldo/"+est.getCodigoPersonal(), FinSaldo.class);
					encontro = true;					
				}catch(Exception ex) {
					System.out.println("Error en saldo:"+est.getCodigoPersonal());
				}
				if (encontro) {				
					if (Double.valueOf(finSaldo.getSaldoSP()) >= 85000){
						mapaDeudores.put(est.getCodigoPersonal(), finSaldo.getSaldoSP());
					}
				}
			}	
		}	
		
		HashMap<String,CandAlumno> mapaCandado 		= candAlumnoDao.mapAlumnosConCandado("1001","A");
		HashMap<String,CatCarrera> mapaCarreras 	= catCarreraDao.getMapAll("");
		HashMap<String,CatTipoAlumno> mapaTipos 	= catTipoAlumnoDao.getMapAll("");
		HashMap<String,String> mapaInscritos		= inscritosDao.getMapaInscritos();
		
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisInscritos", lisInscritos);		
		modelo.addAttribute("mapaDeudores", mapaDeudores);
		modelo.addAttribute("mapaCandado", mapaCandado);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaTipos", mapaTipos);
		modelo.addAttribute("mapaInscritos", mapaInscritos);
		
		return "candados/aplicar/candado";
	}
	
	@RequestMapping("/candados/aplicar/grabarDeudores")
	public String grabarDeudores(HttpServletRequest request, Model modelo){
		
		String deudores	= request.getParameter("Deudores")==null?"0":request.getParameter("Deudores"); 
		String mensaje 	= "-";
		int grabados 	= 0;
		int errores		= 0;
		String[] lista 	= deudores.split(",");
		for (String deudor : lista){
			// Grabar el candado
			CandAlumno candado = new CandAlumno();
			candado.setCodigoPersonal(deudor);
			candado.setFolio(candAlumnoDao.maximoReg(deudor));
			candado.setCandadoId("1001");
			candado.setComentario("Deudas mayor a 85,000.00");
			candado.setEstado("A");
			candado.setfCreado(aca.util.Fecha.getHoy());
			candado.setUsAlta("9800291");
			if (!candAlumnoDao.tieneCandado(deudor, "1001")){
				if (candAlumnoDao.insertReg(candado)){
					grabados++;
				}else {
					errores++;
				}
			}	
		}
		mensaje = "Grabados:"+grabados+" Errores:"+errores; 
		
		return "redirect:/candados/aplicar/candado?Mensaje="+mensaje;
	}
	
}