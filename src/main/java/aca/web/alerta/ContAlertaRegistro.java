package aca.web.alerta;

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
import org.springframework.web.bind.annotation.ResponseBody;

import aca.alerta.spring.AlertaAntro;
import aca.alerta.spring.AlertaAntroDao;
import aca.alerta.spring.AlertaDatos;
import aca.alerta.spring.AlertaDatosDao;
import aca.alerta.spring.AlertaHistorial;
import aca.alerta.spring.AlertaHistorialDao;
import aca.alerta.spring.AlertaPeriodoDao;
import aca.alumno.spring.AlumPersonalDao;
import aca.emp.spring.EmpleadoDao;
import aca.log.spring.LogProceso;
import aca.log.spring.LogProcesoDao;
import aca.util.Fecha;

@Controller
public class ContAlertaRegistro {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	private AlertaPeriodoDao alertaPeriodoDao;

	@Autowired
	private AlertaDatosDao alertaDatosDao;
	
	@Autowired
	private AlertaHistorialDao alertaHistorialDao;

	@Autowired
	private AlertaAntroDao alertaAntroDao;
	
	@Autowired
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired
	private EmpleadoDao empleadoDao;
	
	@Autowired
	private LogProcesoDao logProcesoDao;

	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/alerta/registro/datos")
	public String alertaRegistroDatos(HttpServletRequest request, Model modelo){

		HttpSession sesion = ((HttpServletRequest) request).getSession();
		
		String periodoId 	= "";
		String codigoAlumno	= "-";
		String accion 	 	= request.getParameter("Accion")==null?"":request.getParameter("Accion");
		String msj		 	= "";
		
		if (sesion != null) {
			periodoId = (String) sesion.getAttribute("periodoSanitaria");
			codigoAlumno = (String) sesion.getAttribute("codigoAlumno");
			if(sesion.getAttribute("periodoSanitaria") == null || periodoId.equals("")){
				periodoId = alertaPeriodoDao.getPeriodoActual();
				sesion.setAttribute("periodoSanitaria", periodoId);
			}
		}
		
		List<aca.alerta.spring.AlertaDatos> lisDatos 		= alertaDatosDao.getAll(" WHERE PERIODO_ID = '"+periodoId+"' ");
		List<aca.alerta.spring.AlertaPeriodo> lisPeriodos 	= alertaPeriodoDao.getAllActivos(" ORDER BY 1 DESC");
		
		HashMap<String, AlertaHistorial> mapaHistorial 	= alertaHistorialDao.mapHistorial(periodoId);
		HashMap<String, AlertaAntro> mapaAntro			= alertaAntroDao.mapAntro(periodoId);
		HashMap<String, String> mapaAlumnosAlerta		= alumPersonalDao.mapaAlumnosAlerta(periodoId);
		HashMap<String, String> mapaAlumnosEnAlerta		= alumPersonalDao.mapaAlumnosEnAlerta(periodoId);
		HashMap<String, String> mapaEmpleadosEnAlerta	= empleadoDao.mapaEmpleadosEnAlerta();
		
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("codigoAlumno", codigoAlumno);
		modelo.addAttribute("accion", accion);
		modelo.addAttribute("msj", msj);
		modelo.addAttribute("lisDatos", lisDatos);
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("mapaHistorial", mapaHistorial);
		modelo.addAttribute("mapaAntro", mapaAntro);
		modelo.addAttribute("mapaAlumnosAlerta", mapaAlumnosAlerta);
		modelo.addAttribute("mapaAlumnosEnAlerta", mapaAlumnosEnAlerta);
		modelo.addAttribute("mapaEmpleadosEnAlerta", mapaEmpleadosEnAlerta);
		
		return "alerta/registro/datos";
	}
	
	@RequestMapping("/alerta/registro/accion")
	public String alertaRegistroAccion(HttpServletRequest request, Model modelo){
		
		String periodoId 	= request.getParameter("PeriodoId")==null?"":request.getParameter("PeriodoId");
		String codigoAlumno	= request.getParameter("matricula")==null?"":request.getParameter("matricula");
		String msj			= request.getParameter("Mensaje")==null?"":request.getParameter("Mensaje");
		
		HttpSession sesion = ((HttpServletRequest) request).getSession();
		if (sesion != null) {
			if(sesion.getAttribute("periodoSanitaria") == null || periodoId.equals("")){
				periodoId = alertaPeriodoDao.getPeriodoActual();
				sesion.setAttribute("periodoSanitaria", periodoId);
			}
		}
		String periodoNombre = alertaPeriodoDao.getPeriodoNombre(periodoId);
		boolean existe = alertaDatosDao.existeReg(periodoId, codigoAlumno);
		
		AlertaDatos alertaDatos = new AlertaDatos();
		if(existe){
			alertaDatos = alertaDatosDao.mapeaRegId(periodoId, codigoAlumno);
		}
		
		modelo.addAttribute("msj", msj);
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("periodoNombre", periodoNombre);
		modelo.addAttribute("existe", existe);
		modelo.addAttribute("alertaDatos", alertaDatos);
		
		return "alerta/registro/accion";
	}
	
	@RequestMapping("/alerta/registro/grabar")
	public String alertaRegistroGrabar(HttpServletRequest request, Model model){				
		
		String usuario		= "-";
		HttpSession sesion	= ((HttpServletRequest) request).getSession();
		if (sesion != null) usuario = (String) sesion.getAttribute("codigoPersonal");
		
		String periodoId	= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String matricula	= request.getParameter("matricula")==null?"0":request.getParameter("matricula");
		String direccion	= request.getParameter("direccion")==null?"-":request.getParameter("direccion");
		String procedencia 	= request.getParameter("procedencia")==null?"0":request.getParameter("procedencia");
		String correo		= request.getParameter("correo")==null?"0":request.getParameter("correo");
		String celular		= request.getParameter("celular")==null?"0":request.getParameter("celular");
		String lugar1		= request.getParameter("lugar1")==null?"-":request.getParameter("lugar1");
		String lugar2 		= request.getParameter("lugar2")==null?"-":request.getParameter("lugar2");
		String estado		= request.getParameter("estado")==null?"0":request.getParameter("estado");
		String referente	= request.getParameter("referente")==null?"0":request.getParameter("referente");
		String sintoma		= request.getParameter("sintoma")==null?"0":"1";
		String fiebre 		= request.getParameter("fiebre")==null?"0":"1";
		String muscular		= request.getParameter("muscular")==null?"0":"1";
		String cutanea 		= request.getParameter("cutanea")==null?"0":"1";
		String malestar		= request.getParameter("malestar")==null?"0":"1";
		String nauseas		= request.getParameter("nauseas")==null?"0":"1";
		String fatiga		= request.getParameter("fatiga")==null?"0":"1";
		String cabeza 		= request.getParameter("cabeza")==null?"0":"1";
		String sudoracion	= request.getParameter("sudoracion")==null?"0":"1";
		String tos 			= request.getParameter("tos")==null?"0":"1";
		String articular	= request.getParameter("articular")==null?"0":"1";
		String otro			= request.getParameter("otro")==null?"0":request.getParameter("otro");
		String mensaje 		= "-";
		
		AlertaDatos alertaDatos	= new AlertaDatos();
		alertaDatos.setFecha(aca.util.Fecha.getHoy());
		alertaDatos.setDireccion(direccion);
		alertaDatos.setProcedencia(procedencia); 
		alertaDatos.setCorreo(correo);
		alertaDatos.setCelular(celular);
		alertaDatos.setLugar1(lugar1);
		alertaDatos.setLugar2(lugar2);
		alertaDatos.setEstado(estado);
		alertaDatos.setReferente(referente);
		alertaDatos.setSintomas(fiebre+muscular+cutanea+malestar+nauseas+fatiga+cabeza+sudoracion+tos+articular+sintoma);
		alertaDatos.setUsuario(usuario);
		alertaDatos.setOtro(otro);

		LogProceso proceso = new LogProceso();
		
		if (alertaDatosDao.existeReg(periodoId, matricula)){
			alertaDatos.setPeriodoId(periodoId);
			alertaDatos.setCodigoPersonal(matricula);
			if (alertaDatosDao.updateReg(alertaDatos)){				
				mensaje = "1";
			}else {
				mensaje = "2";
			}
		}else {
			alertaDatos.setPeriodoId(periodoId);
			alertaDatos.setCodigoPersonal(matricula);
			if (alertaDatosDao.insertReg(alertaDatos)){
				
				proceso.setFolio(String.valueOf(logProcesoDao.maximoReg()));
				proceso.setCodigoPersonal(matricula);
				proceso.setModulo("SALUD");
				proceso.setFecha(Fecha.getHoy());
				proceso.setEvento("TERMINA-INSERTAR");
				
				logProcesoDao.insertReg(proceso);
				
				mensaje = "1";
			}else {
				mensaje = "2";
			}
		}		
		return "redirect:/alerta/registro/accion?PeriodoId="+alertaDatos.getPeriodoId()+"&Mensaje="+mensaje+"&matricula="+matricula;
	}
	
	@ResponseBody
	@RequestMapping("/alerta/registro/alumnoExiste")
	public String alertaRegistroAlumnoExiste(HttpServletRequest request){		
		String matricula 	= request.getParameter("matricula");		
		String periodoId	= "-";		
		String mensaje		= "";
		HttpSession sesion	= ((HttpServletRequest) request).getSession();
		if (sesion != null) {
			periodoId = (String) sesion.getAttribute("periodoSanitaria");			
			if(alertaDatosDao.existeAlumno(periodoId, matricula)==true){ 
				mensaje = "¡El alumno ya está registrado!";
			}			
		}	
		return mensaje;
	}
	
	@ResponseBody
	@RequestMapping("/alerta/registro/getNombreAlumno")
	public String alertaRegistroGetNombreALumno(HttpServletRequest request){		
		String matricula 	= request.getParameter("matricula");
		String nombre 		= "Numero de Matrícula No Válido";
		if (alumPersonalDao.existeAlumno(matricula)) {
			nombre = alumPersonalDao.getNombreAlumno(matricula,"NOMBRE");
		}		
		return nombre;
	}
	
	@RequestMapping("/alerta/registro/datosAlumno")
	public String alertaRegistroDatosAlumno(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContAlertaRegistro|alertaRegistroDatosAlumno:");
		
		String codigoAlumno	= request.getParameter("matricula")==null?"":request.getParameter("matricula");
		
		LogProceso proceso = new LogProceso();
		
		proceso.setFolio(String.valueOf(logProcesoDao.maximoReg()));
		proceso.setCodigoPersonal(codigoAlumno);
		proceso.setModulo("SALUD");
		proceso.setFecha(Fecha.getHoy());
		proceso.setEvento("INICIO-INSERTAR");
		
		logProcesoDao.insertReg(proceso);
		
		return "alerta/registro/datosAlumno";
	}
	
	@RequestMapping("/alerta/registro/antro")
	public String alertaRegistroAntro(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContAlertaRegistro|alertaRegistroAntro");
		return "alerta/registro/antro";
	}
	
	@RequestMapping("/alerta/registro/historial")
	public String alertaRegistroHistorial(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContAlertaRegistro|alertaRegistroHistorial");
		return "alerta/registro/historial";
	}
	
	@RequestMapping("/alerta/registro/vacunas")
	public String alertaRegistroVacunas(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContAlertaRegistro|alertaRegistroVacunas");
		return "alerta/registro/vacunas";
	}
	
}