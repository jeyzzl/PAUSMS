package aca.web.salida;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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

import aca.acceso.spring.AccesoDao;
import aca.salida.spring.SalBitacora;
import aca.salida.spring.SalBitacoraDao;
import aca.salida.spring.SalInforme;
import aca.salida.spring.SalInformeDao;
import aca.salida.spring.SalSolicitud;
import aca.salida.spring.SalSolicitudDao;

@Controller
public class ContSalidaPermiso {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
		
	@Autowired
	SalSolicitudDao salSolicitudDao;
	
	@Autowired
	SalBitacoraDao salBitacoraDao;
	
	@Autowired
	AccesoDao accesoDao;
	
	@Autowired
	aca.vista.spring.MaestrosDao maestrosDao;
	
	@Autowired
	SalInformeDao salInformeDao;
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/salida/permiso/permiso")
	public String salidaPermisoPermiso(HttpServletRequest request, Model modelo){
		
		String codigoPersonal 	= "0";
		String fechaIni			= request.getParameter("FechaIni")==null?"01/01/"+aca.util.Fecha.getHoy().substring(6,10):request.getParameter("FechaIni");
		String fechaFin			= request.getParameter("FechaFin")==null?"31/12/"+aca.util.Fecha.getHoy().substring(6,10):request.getParameter("FechaFin");
		String salida 			= request.getParameter("salida") == null ? "0" : request.getParameter("salida");	
		String periodo 			= "";
		String accion 			= request.getParameter("Accion")!=null?request.getParameter("Accion"):"0";		
		
		SimpleDateFormat sdf 	= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Timestamp timestamp 	= new Timestamp(System.currentTimeMillis());
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal 		= (String)sesion.getAttribute("codigoPersonal");
			periodo 			= (String)sesion.getAttribute("periodo");
		}
		boolean esAdmin 		= accesoDao.esAdministrador(codigoPersonal);
		
		SalBitacora bitacora = new SalBitacora();
		
		List<SalSolicitud> lisSolicitud 		= null;
		HashMap<String,String> mapaGrupoNombre 	= salSolicitudDao.mapNombreGrupo();
		HashMap<String,String> mapaMaestros 	= maestrosDao.mapMaestroNombre("NOMBRE");
		HashMap<String,SalInforme> mapaInformes = salInformeDao.mapaInformes();
		
		
		boolean autorizar = false;	
		if(codigoPersonal.equals("9800308") || codigoPersonal.equals("9801161") || codigoPersonal.equals("9800058")){
			autorizar = true;
		}
		
		timestamp = new Timestamp(System.currentTimeMillis());
		
		// Preautorizacion
		if(accion.equals("1")){
			if(salSolicitudDao.updatePreautorizo(codigoPersonal,"0",salida)){
				if(salBitacoraDao.existeReg(salida,"3")) {
					bitacora = salBitacoraDao.mapeaRegId(salida, "3");
					bitacora.setFecha(sdf.format(timestamp));
					salBitacoraDao.updateReg(bitacora);
				}else{
					bitacora.setSalidaId(salida);
					bitacora.setEstado("3");
					bitacora.setFecha(sdf.format(timestamp));
					salBitacoraDao.insertReg(bitacora);
				}
			}	
		}	
		
		// Cancela preautoriacion
		if(accion.equals("2")){		
			if(salSolicitudDao.updatePreautorizo("0","0",salida)){
				if(salBitacoraDao.existeReg(salida,"2")) {
					bitacora = salBitacoraDao.mapeaRegId(salida, "2");
					bitacora.setFecha(sdf.format(timestamp));
					salBitacoraDao.updateReg(bitacora);
				}else{
					bitacora.setSalidaId(salida);
					bitacora.setEstado("2");
					bitacora.setFecha(sdf.format(timestamp));
					salBitacoraDao.insertReg(bitacora);
				}
			}	
		}
		
		// Autorizacion de Actividades Complementarias
		if(accion.equals("3")){
			if(salida.length()==1){
				salida = "00"+salida;
			}else if(salida.length()==2){
				salida = "0"+salida;
			}
			if(salSolicitudDao.updateRegAutorizo(codigoPersonal,periodo+"-"+salida,salida)){
				if(salBitacoraDao.existeReg(salida,"4")) {
					bitacora = salBitacoraDao.mapeaRegId(salida, "4");
					bitacora.setFecha(sdf.format(timestamp));
					salBitacoraDao.updateReg(bitacora);
				}else{
					bitacora.setSalidaId(salida);
					bitacora.setEstado("4");
					bitacora.setFecha(sdf.format(timestamp));
					salBitacoraDao.insertReg(bitacora);
				}
			}	
		}
		
		// Cancelar autoizacion de actividades complementarias
		if(accion.equals("4")){
			if(salSolicitudDao.updateRegAutorizo("0","0",salida)){
				if(salBitacoraDao.existeReg(salida,"3")) {
					bitacora = salBitacoraDao.mapeaRegId(salida, "3");
					bitacora.setFecha(sdf.format(timestamp));
					salBitacoraDao.updateReg(bitacora);
				}else{
					bitacora.setSalidaId(salida);
					bitacora.setEstado("3");
					bitacora.setFecha(sdf.format(timestamp));
					salBitacoraDao.insertReg(bitacora);
				}
			}	
		}
		
		if (esAdmin){
			lisSolicitud = salSolicitudDao.lisUsuarioPorFecha(fechaIni, fechaFin, " ORDER BY ENOC.SAL_SOLICITUD.FECHA_SALIDA DESC");
		}else{
			lisSolicitud = salSolicitudDao.lisUsuarioPorFecha(codigoPersonal, fechaIni, fechaFin, " ORDER BY ENOC.SAL_SOLICITUD.FECHA_SALIDA DESC"); 
		}

		modelo.addAttribute("esAdmin", esAdmin);
		modelo.addAttribute("autorizar",autorizar);
		modelo.addAttribute("lisSolicitud",lisSolicitud);
		modelo.addAttribute("mapaGrupoNombre",mapaGrupoNombre);
		modelo.addAttribute("mapaInformes",mapaInformes);
		modelo.addAttribute("mapaMaestros",mapaMaestros);
		
		return "salida/permiso/permiso";
	}
	
	@RequestMapping("/salida/permiso/regresar")
	public String salidaPermisoRegresar(HttpServletRequest request){
		
		String salidaId  = request.getParameter("SalidaId")==null?"0":request.getParameter("SalidaId");
		
		SalSolicitud salSolicitud = new SalSolicitud();
		salSolicitud = salSolicitudDao.mapeaRegId(salidaId);
		salSolicitud.setEstado("S");
		
		salSolicitudDao.updateReg(salSolicitud);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		SalBitacora salBitacora = new SalBitacora();
		if(salBitacoraDao.existeReg(salidaId, "1")) {
			salBitacora = salBitacoraDao.mapeaRegId(salidaId, "1");
			salBitacora.setFecha(sdf.format(timestamp));
			salBitacoraDao.updateReg(salBitacora);
		}else{
			salBitacora.setSalidaId(salidaId);
			salBitacora.setEstado("1");
			salBitacora.setFecha(sdf.format(timestamp));
			salBitacoraDao.insertReg(salBitacora);
		}
		
		salBitacoraDao.updateReg(salBitacora);
		
		return "redirect:/salida/permiso/permiso";
	}
	
	@RequestMapping("/salida/permiso/verInforme")
	public String salidaPermisoVerInforme(HttpServletRequest request, Model modelo){		
		
		String salidaId  				= request.getParameter("SalidaId")==null?"0":request.getParameter("SalidaId");
		SalInforme salInforme 			= new SalInforme();
		boolean existe					= false;
		
		if (salInformeDao.existeReg(salidaId)) {
			salInforme 	= salInformeDao.mapeaRegId(salidaId);
			existe 		= true;
		}
		
		modelo.addAttribute("salidaId", salidaId);
		modelo.addAttribute("existe", existe);
		modelo.addAttribute("salInforme", salInforme);
		
		return "salida/permiso/verInforme";
	}
	
	@RequestMapping("/salida/permiso/grabarPermiso")
	public String salidaPermisoGrabarPermiso(HttpServletRequest request, Model modelo){	
		String salidaId		= request.getParameter("salida")==null?"0":request.getParameter("salida");
		String permiso		= request.getParameter("permiso")==null?"N":request.getParameter("permiso");
		String filtroEstado	= request.getParameter("FiltroEstado")==null?"0":request.getParameter("FiltroEstado");
		
		if (salSolicitudDao.existeReg(salidaId)) {
			salSolicitudDao.updatePermiso(permiso,salidaId);
		}
		
		return "redirect:/salida/permiso/permiso?FiltroEstado="+filtroEstado;
	}
	
}