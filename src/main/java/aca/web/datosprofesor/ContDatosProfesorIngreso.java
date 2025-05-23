package aca.web.datosprofesor;

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

import aca.alerta.spring.AlertaPeriodo;
import aca.alerta.spring.AlertaPeriodoDao;
import aca.alumno.spring.AlumCovid;
import aca.alumno.spring.AlumCovidDao;
import aca.vista.spring.Maestros;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContDatosProfesorIngreso {
	
	@Autowired
	private AlumCovidDao alumCovidDao;
	
	@Autowired
	private MaestrosDao maestrosDao;
	
	@Autowired
	private AlertaPeriodoDao alertaPeriodoDao;
	
	@RequestMapping("/datos_profesor/ingreso/datos")
	public String datosProfesorIngresoDatos(HttpServletRequest request, Model modelo){
		String codigoEmpleado		= "0";
		String nombreEmpleado		= "";
		String periodoId 			= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String mensaje		 		= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		
		Maestros maestro = new Maestros();
		
		HttpSession sesion 		= ((HttpServletRequest) request).getSession();
		if (sesion != null){			
			codigoEmpleado 		= (String) sesion.getAttribute("codigoEmpleado");
			if(maestrosDao.existeReg(codigoEmpleado)) {
				maestro = maestrosDao.mapeaRegId(codigoEmpleado);
				nombreEmpleado = maestro.getNombre()+" "+maestro.getApellidoPaterno()+" "+maestro.getApellidoMaterno();
			}
		}
		
		List<AlertaPeriodo> lisPeriodos 		= alertaPeriodoDao.getAllActivos(" ORDER BY 1 DESC");
		if (periodoId.equals("0") && lisPeriodos.size() >= 1){
			periodoId = lisPeriodos.get(0).getPeriodoId();
		}

		List<AlumCovid> lisDatos = alumCovidDao.lisPorPeriodo(periodoId, " AND CODIGO_PERSONAL LIKE '98%' ORDER BY PERIODO_ID DESC");
		//System.out.println(periodoId+":"+lisDatos.size());
		HashMap<String,String> mapaRegistrados 		=  alumCovidDao.mapaPorPeriodo(periodoId);
		HashMap<String,String> mapaMaestros 		=  maestrosDao.mapMaestroNombre("NOMBRE");
		
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("lisDatos", lisDatos);
		modelo.addAttribute("codigoEmpleado", codigoEmpleado);
		modelo.addAttribute("nombreEmpleado", nombreEmpleado);
		modelo.addAttribute("mapaRegistrados", mapaRegistrados);
		modelo.addAttribute("mapaMaestros", mapaMaestros);
		
		return "datos_profesor/ingreso/datos";
	}	
	
	@RequestMapping("/datos_profesor/ingreso/editar")
	public String datosProfesorIngresoEditar(HttpServletRequest request, Model modelo){
		
		String codigoEmpleado	= request.getParameter("CodigoEmpleado")==null?"0":request.getParameter("CodigoEmpleado");
		String periodoId		= request.getParameter("CodigoEmpleado")==null?"0":request.getParameter("PeriodoId");
		String mensaje			= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		String nombreEmpleado	= "";
		boolean existe 			= false;
		HttpSession sesion		= ((HttpServletRequest) request).getSession();
		AlumCovid alumCovid 	= new AlumCovid();
		Maestros maestro 		= new Maestros();
		
		if (codigoEmpleado.equals("0") && sesion != null) {
			codigoEmpleado 	= (String) sesion.getAttribute("codigoEmpleado");
		}
		if(alumCovidDao.existeReg(codigoEmpleado, periodoId)){
			existe 			= true;
			alumCovid 		= alumCovidDao.mapeaRegId(codigoEmpleado, periodoId);	
			if(maestrosDao.existeReg(codigoEmpleado)) {
				maestro = maestrosDao.mapeaRegId(codigoEmpleado);
				nombreEmpleado = maestro.getNombre()+" "+maestro.getApellidoPaterno()+" "+maestro.getApellidoMaterno();
			}
		}	
		modelo.addAttribute("codigoEmpleado", codigoEmpleado);
		modelo.addAttribute("nombreEmpleado", nombreEmpleado);
		modelo.addAttribute("existe", existe);		
		modelo.addAttribute("alumCovid", alumCovid);
		modelo.addAttribute("mensaje", mensaje);
		
		
		return "datos_profesor/ingreso/editar";		
	}
	
	@RequestMapping("/datos_profesor/ingreso/grabar")
	public String datosProfesorIngresoGrabar(HttpServletRequest request, Model model){				
		
		String usuario		= "-";
		HttpSession sesion	= ((HttpServletRequest) request).getSession();
		if (sesion != null) {
			usuario = (String) sesion.getAttribute("codigoPersonal");
		}
		
		String periodoId		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String codigoEmpleado	= request.getParameter("CodigoEmpleado")==null?"0":request.getParameter("CodigoEmpleado");
		String tipo				= request.getParameter("Tipo")==null?"0":request.getParameter("Tipo");
		String llegada			= request.getParameter("Fechallegada")==null?"0":request.getParameter("Fechallegada");
		String vacuna	 		= request.getParameter("Vacuna")==null?"N":request.getParameter("Vacuna");
		String fechaVacuna	 	= request.getParameter("FechaVacuna")==null?"0":request.getParameter("FechaVacuna");
		String positivo			= request.getParameter("Positivo")==null?"N":request.getParameter("Positivo");
		String fechaPositivo	= request.getParameter("FechaPositivo")==null?"0":request.getParameter("FechaPositivo");
		String sospechoso		= request.getParameter("Sospechoso")==null?"N":request.getParameter("Sospechoso");
		String fechaSospechoso	= request.getParameter("FechaSospechoso")==null?"0":request.getParameter("FechaSospechoso");
		String aislamiento 		= request.getParameter("Aislamiento")==null?"0":request.getParameter("Aislamiento");
		String finAislamiento	= request.getParameter("FinAislamiento")==null?"0":request.getParameter("FinAislamiento");
		String validado			= request.getParameter("Validado")==null?"0":request.getParameter("Validado");	
		String comentario		= request.getParameter("Comentario")==null?"0":request.getParameter("Comentario");
		String fechaAlta		= aca.util.Fecha.getHoy();
		String mensaje 			= "0";
		
		AlumCovid alumCovid = new AlumCovid();
		
		alumCovid.setPeriodoId(periodoId);
		alumCovid.setCodigoPersonal(codigoEmpleado);
		alumCovid.setTipo(tipo);
		alumCovid.setFechaLlegada(llegada);
		alumCovid.setVacuna(vacuna);
		alumCovid.setFechaVacuna(fechaVacuna);
		alumCovid.setPositivoCovid(positivo);
		alumCovid.setFechaPositivo(fechaPositivo);
		alumCovid.setSospechoso(sospechoso);
		alumCovid.setFechaSospechoso(fechaSospechoso);
		alumCovid.setAislamiento(aislamiento);
		alumCovid.setFinAislamiento(finAislamiento);
		alumCovid.setUsuarioAlta(usuario);
		alumCovid.setFechaAlta(fechaAlta);
		alumCovid.setComentario(comentario);
		alumCovid.setValidado(validado);

		if (alumCovidDao.existeReg(codigoEmpleado,periodoId)){
			if (alumCovidDao.updateReg(alumCovid)){				
				mensaje = "1";
			}else {
				mensaje = "2";
			}
		}else {
			if (alumCovidDao.insertReg(alumCovid)){
				mensaje = "1";
			}else {
				mensaje = "2";
			}
		}		
		return "redirect:/datos_profesor/ingreso/editar?CodigoEmpleado="+codigoEmpleado+"&PeriodoId="+periodoId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/datos_profesor/ingreso/borrar")
	public String alertaRegistroBorrar(HttpServletRequest request, Model model){
		
		String periodoId		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String codigoEmpleado	= request.getParameter("CodigoEmpleado")==null?"0":request.getParameter("CodigoEmpleado");
		
		String mensaje 			= "0";	
		if (alumCovidDao.existeReg(codigoEmpleado,periodoId)){
			if (alumCovidDao.deleteReg(codigoEmpleado, periodoId)){	
				mensaje = "¡Registro borrado!";
			}else {
				mensaje = "¡Error al intentar borrar!";
			}
		}
		
		return "redirect:/datos_profesor/ingreso/datos?Mensaje="+mensaje;
	}
	
}