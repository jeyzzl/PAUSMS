package aca.web.informes;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumEstado;
import aca.alumno.spring.AlumEstadoDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatModalidadDao;
import aca.catalogo.spring.CatPeriodo;
import aca.catalogo.spring.CatPeriodoDao;
import aca.financiero.spring.FesCcMateriaDao;
import aca.kardex.spring.KrdxCursoActDao;
import aca.reportes.spring.AltasBajas;
import aca.reportes.spring.AltasBajasDao;

@Controller
public class ContInformesAltaBaja {	
	
	
	@Autowired
	private CatPeriodoDao catPeriodoDao;
	
	@Autowired
	private CargaDao cargaDao;
	
	@Autowired
	private AccesoDao accesoDao; 
	
	@Autowired
	private FesCcMateriaDao fesCcMateriaDao; 
	
	@Autowired
	private AltasBajasDao altasBajasDao; 
	
	@Autowired
	private KrdxCursoActDao krdxCursoActDao; 
	
	@Autowired
	private CatFacultadDao catFacultadDao;
	
	@Autowired
	private CatCarreraDao catCarreraDao; 
	
	@Autowired
	private AlumEstadoDao alumEstadoDao; 

	@Autowired
	private CatModalidadDao catModaldadDao; 
	
	
	@RequestMapping("/informes/alta_baja/listado")
	public String informesAltaBajaListado(HttpServletRequest request, Model modelo){
		
		String codigoPersonal 		= "-";
		HttpSession sesion			= ((HttpServletRequest)request).getSession();
		if(sesion != null){
			codigoPersonal			= (String)sesion.getAttribute("codigoPersonal");
		}
		
		String periodoId 			= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");				
		String cargaId 				= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		
		List<CatPeriodo> lisPeriodos = catPeriodoDao.getListAll("ORDER BY PERIODO_ID DESC");	
		if (periodoId.equals("0") && lisPeriodos.size()>=1) {
			periodoId 	= lisPeriodos.get(0).getPeriodoId(); 
		}
		if (!periodoId.equals("0")){
			sesion.setAttribute("periodo", periodoId);
		}
		
		List<Carga> lisCargas 		= cargaDao.listPeriodoAndEstado(periodoId, "1", " ORDER BY CARGA_ID");		
		if(cargaId.equals("0") && sesion.getAttribute("cargaId") != null){
			cargaId = (String)sesion.getAttribute("cargaId");
		}else if(cargaId.equals("0") && lisCargas.size() >= 1){
			cargaId = lisCargas.get(0).getCargaId();
		}		
		for(Carga carga : lisCargas){
			if (carga.getCargaId().equals(cargaId)){				
				sesion.setAttribute("cargaId",cargaId);
			}
		}
		
		List<AltasBajas> lisAltas		= altasBajasDao.getListAltasBajas(cargaId);		
		
		// Map de costos por credito en la carga
		HashMap<String, String> mapCostoCredito 		= fesCcMateriaDao.mapCostoCreditoCarga("'"+cargaId+"'", "'I','3'");
		HashMap<String, String> mapMatCalculo 			= fesCcMateriaDao.mapMatEnCalculo(cargaId);
	 	HashMap<String, String> mapMatOrigen 			= krdxCursoActDao.mapMatPorTipo(cargaId, "O"); 	
	 	HashMap<String, String> mapMatAlta 				= krdxCursoActDao.mapMatPorTipo(cargaId, "A");
	 	HashMap<String, String> mapMatBaja 				= krdxCursoActDao.mapMatPorTipo(cargaId, "B");
	 	HashMap<String, CatFacultad> mapaFacultades		= catFacultadDao.getMapFacultad("");
	 	HashMap<String, CatCarrera> mapaCarreras		= catCarreraDao.getMapAll("");	 	
	 	HashMap<String, String> mapCreditosCalculo		= fesCcMateriaDao.mapaCreditosEnCalculo(cargaId);	 	
	 	HashMap<String, String> mapCreditosOrigen		= krdxCursoActDao.mapaCreditosEnCarga(cargaId, "AND TIPO IN('O','B') AND TIPOCAL_ID != 'M'");	 	
	 	HashMap<String, String> mapCreditosAlta			= krdxCursoActDao.mapaCreditosEnCarga(cargaId, "AND TIPO = 'A' AND TIPOCAL_ID != 'M' ");
	 	HashMap<String, String> mapCreditosBaja			= krdxCursoActDao.mapaCreditosEnCarga(cargaId, "AND TIPO = 'B' AND TIPOCAL_ID  = '3'");	
	 	HashMap<String, String> mapaModalidadesEnCarga = alumEstadoDao.mapaModalidadesEnCarga(cargaId, "ORDER BY CODIGO_PERSONAL, FECHA");
	 	HashMap<String,String> mapModalidades 			= catModaldadDao.mapModalidades("");
	 	
	 	Acceso acceso = accesoDao.mapeaRegId(codigoPersonal);
	 	
		modelo.addAttribute("codigoPersonal", codigoPersonal);
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("lisAltas", lisAltas);
		modelo.addAttribute("mapCostoCredito", mapCostoCredito);
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("mapMatCalculo", mapMatCalculo);
		modelo.addAttribute("mapMatOrigen", mapMatOrigen);
		modelo.addAttribute("mapMatAlta", mapMatAlta);
		modelo.addAttribute("mapMatBaja", mapMatBaja);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapCreditosCalculo", mapCreditosCalculo);
		modelo.addAttribute("mapCreditosOrigen", mapCreditosOrigen);
		modelo.addAttribute("mapCreditosAlta", mapCreditosAlta);
		modelo.addAttribute("mapCreditosBaja", mapCreditosBaja);
		modelo.addAttribute("mapaModalidadesEnCarga", mapaModalidadesEnCarga);
		modelo.addAttribute("mapModalidades", mapModalidades);
		
		return "informes/alta_baja/listado";
	}		

}