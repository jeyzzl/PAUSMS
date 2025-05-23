package aca.web.hca;

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

import aca.acceso.spring.AccesoDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;
import aca.carga.spring.CargaGrupoDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatPeriodo;
import aca.catalogo.spring.CatPeriodoDao;
import aca.financiero.spring.ContCcostoDao;
import aca.hca.spring.HcaMaestro;
import aca.hca.spring.HcaMaestroActividadDao;
import aca.hca.spring.HcaMaestroDao;
import aca.hca.spring.HcaMaestroEstado;
import aca.hca.spring.HcaMaestroEstadoDao;
import aca.hca.spring.HcaTipo;
import aca.hca.spring.HcaTipoDao;
import aca.vista.spring.Maestros;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContHcaInforme {	
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	private MaestrosDao maestrosDao;
	
	@Autowired
	private CargaGrupoDao cargaGrupoDao;
	
	@Autowired
	private CatPeriodoDao catPeriodoDao;
	
	@Autowired
	private CargaDao cargaDao;
	
	@Autowired
	private CatFacultadDao catFacultadDao;
	
	@Autowired
	HcaMaestroDao hcaMaestroDao;
	
	@Autowired
	HcaTipoDao hcaTipoDao;
	
	@Autowired
	HcaMaestroActividadDao hcaMaestroActividadDao;
	
	@Autowired
	HcaMaestroEstadoDao hcaMaestroEstadoDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	AccesoDao accesoDao;
	
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}	
	
	@RequestMapping("/hca/informe/carrera")
	public String hcaInformeCarrera(HttpServletRequest request, Model modelo){		
		
		String facultadId 	= request.getParameter("FacultadId");
		String cargaId 		= request.getParameter("CargaId");

		List<aca.Mapa> lisMaestros 				= hcaMaestroDao.lisConMaterias(cargaId, facultadId, " ORDER BY EMP_NOMBRE(CODIGO_PERSONAL)");
		List<HcaTipo> lisTipos					= hcaTipoDao.lisTodos("ORDER BY ORDEN");
		HashMap<String,String> mapaTotalHoras	= hcaMaestroActividadDao.mapaTotalHoras(cargaId);
		HashMap<String,HcaMaestroEstado> mapaMaestroEstado= hcaMaestroEstadoDao.getMapAll(cargaId);
		HashMap<String,CatCarrera> mapaCarreras	= catCarreraDao.getMapAll("");
		HashMap<String,Maestros> mapaMaestros	= maestrosDao.mapaMaestros();		

		Carga carga =  cargaDao.mapeaRegId(cargaId);
		
		modelo.addAttribute("carga", carga);
		modelo.addAttribute("lisMaestros", lisMaestros);
		modelo.addAttribute("lisTipos", lisTipos);
		modelo.addAttribute("mapaTotalHoras", mapaTotalHoras);
		modelo.addAttribute("mapaMaestroEstado", mapaMaestroEstado);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaMaestros", mapaMaestros);
		
		return "hca/informe/carrera";
	}	
	
	@RequestMapping("/hca/informe/elige")
	public String hcaInformeElige(HttpServletRequest request, Model modelo){
		
		String periodoId		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String cargaId 			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		
		List<CatPeriodo> lisPeriodos 			= catPeriodoDao.getListAll(" ORDER BY PERIODO_ID DESC");
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			if (cargaId.equals("0")) cargaId =  (String)sesion.getAttribute("cargaId");
			if (periodoId.equals("0")) periodoId =  (String)sesion.getAttribute("periodo");			
		}
		
		List<Carga> lisCargas 					= cargaDao.getListAll(" WHERE PERIODO IN ('"+periodoId+"') AND ESTADO = '1' ORDER BY PERIODO, CARGA_ID");
		boolean existeCarga = false;
		for (Carga carga : lisCargas) {
			if (carga.getCargaId().equals(cargaId)) existeCarga = true;
		}
		if (existeCarga==false && lisCargas.size() >= 1) {
			cargaId = lisCargas.get(0).getCargaId();
		}		
		if (sesion!=null){
			sesion.setAttribute("periodo", periodoId);
			sesion.setAttribute("cargaId", cargaId);
		}
		
		List<CatFacultad> lisFacultades 				= catFacultadDao.listFacultadesConCarga(cargaId, " ORDER BY 1" );		 
		
		HashMap<String,String> mapMaestrosEnCarga		= hcaMaestroDao.mapMaestrosConMaterias(cargaId);
		HashMap<String,String> mapMaestrosRegistrados	= hcaMaestroDao.mapMaestrosRegistrados(cargaId);		

		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisFacultades", lisFacultades);		
		
		modelo.addAttribute("mapMaestrosEnCarga", mapMaestrosEnCarga);
		modelo.addAttribute("mapMaestrosRegistrados", mapMaestrosRegistrados);	
		
		return "hca/informe/elige";
	}
	
	@RequestMapping("/hca/informe/listaMaestros")
	public String hcaInformeListaMestros(HttpServletRequest request, Model modelo){
		
		String cargaId 			= request.getParameter("CargaId")==null?("cargaId").toString():request.getParameter("CargaId");
		String opcion 			= request.getParameter("FacultadId")==null?"1":request.getParameter("Opcion");
		String facultadId		= request.getParameter("FacultadId")==null?"101":request.getParameter("FacultadId");
		String cargaNombre 		= cargaDao.getNombreCarga(cargaId);
		String codigoPersonal 	= "0";
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal 		=  (String)sesion.getAttribute("codigoPersonal");						
		}
		
		String accesos 			= accesoDao.getAccesos(codigoPersonal);
		String arreglo[] = accesos.split(" ");
		String carreras = "";
		for (String carrera : arreglo) {
			if (catCarreraDao.existeReg(carrera)) {
				if (carreras.length()==0) carreras = "'"+carrera+"'"; else carreras += ",'"+carrera+"'";
			}
		}
		if (carreras.length()==0) carreras = "'X'";
		
		List <String> lisMaestros			= null;		
		if(opcion.equals("1")){
			lisMaestros 	= cargaGrupoDao.lisMaestrosSinFac(cargaId, facultadId, " ORDER BY USUARIO_APELLIDO(CODIGO_PERSONAL)");
		}else if (opcion.equals("2")){
			lisMaestros 	= cargaGrupoDao.lisMaestrosCargayFac(cargaId, facultadId, " ORDER BY USUARIO_APELLIDO(CODIGO_PERSONAL)"); 
		}	
		HashMap<String, String> mapMaestros				= maestrosDao.mapMaestroNombre("NOMBRE");
		HashMap<String, String> mapMaterias				= cargaGrupoDao.mapMateriasMaestro(cargaId);
		HashMap<String,String> mapMaestrosCapturados	= hcaMaestroDao.mapMaestrosCapturados(cargaId);
		HashMap<String,String> mapMaestrosPermiso		= hcaMaestroDao.mapMaestrosConPermiso(carreras);
		HashMap<String,HcaMaestro> mapBases				= hcaMaestroDao.mapTodos();
		HashMap<String,CatCarrera> mapCarreras			= catCarreraDao.getMapAll("");

		modelo.addAttribute("cargaNombre", cargaNombre);
		modelo.addAttribute("lisMaestros", lisMaestros);
		modelo.addAttribute("mapMaestros", mapMaestros);
		modelo.addAttribute("mapMaterias", mapMaterias);		
		modelo.addAttribute("mapMaestrosCapturados", mapMaestrosCapturados);
		modelo.addAttribute("mapMaestrosPermiso", mapMaestrosPermiso);
		modelo.addAttribute("mapBases", mapBases);
		modelo.addAttribute("mapCarreras", mapCarreras);

		return "hca/informe/listaMaestros";
	}
}