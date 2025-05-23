package aca.web.residencia;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumAcademico;
import aca.alumno.spring.AlumAcademicoDao;
import aca.alumno.spring.AlumPersonalDao;
import aca.residencia.spring.ResAlumno;
import aca.residencia.spring.ResAlumnoDao;
import aca.residencia.spring.ResDatos;
import aca.residencia.spring.ResDatosDao;
import aca.residencia.spring.ResPeriodo;
import aca.residencia.spring.ResPeriodoDao;
import aca.residencia.spring.ResRazon;
import aca.residencia.spring.ResRazonDao;

@Controller
public class ContResidenciaAlumno{
	
	@Autowired
	ResAlumnoDao resAlumnoDao;

	@Autowired
	ResPeriodoDao resPeriodoDao;
	
	@Autowired
	ResRazonDao resRazonDao;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;
	
	@Autowired
	AlumAcademicoDao alumAcademicoDao;
	
	@Autowired
	private ResDatosDao resDatosDao;
	
	@RequestMapping("/residencia/alumno/listado")
	public String residenciaAlumnoListado(HttpServletRequest request, Model modelo){
		String codigoAlumno 	= "0";
		String nombreAlumno		= "-";
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();		
		if (sesion!=null){			
			codigoAlumno 		= (String) sesion.getAttribute("codigoAlumno");
			nombreAlumno 		= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
		}
		
		AlumAcademico alumAcademico	= alumAcademicoDao.mapeaRegId(codigoAlumno);
		
		List<ResAlumno> lisAlumno					= resAlumnoDao.lisPorAlumno(codigoAlumno, " ORDER BY FECHA_INICIO");	
		HashMap<String, String> mapaRazon 			= resRazonDao.getMapRazon();
		HashMap<String, ResPeriodo> mapaPeriodos		= resPeriodoDao.mapaPeriodo();
		
		modelo.addAttribute("alumAcademico", alumAcademico);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("lisAlumno", lisAlumno);
		modelo.addAttribute("mapaRazon", mapaRazon);
		modelo.addAttribute("mapaPeriodos", mapaPeriodos);
		
		return "residencia/alumno/listado";
	}

	@RequestMapping("/residencia/alumno/nuevoExterno")
	public String residenciaAlumnoNuevoExterno(HttpServletRequest request, Model modelo){
		
		String periodoId 	= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String folio	 	= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String mensaje 		= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");

		String codigoAlumno 	= "0";
		String nombreAlumno		= "-";
		String codigoPersonal	= "-";
		HttpSession sesion = ((HttpServletRequest)request).getSession();		
		if (sesion!=null){
			codigoAlumno 		= (String) sesion.getAttribute("codigoAlumno");
			nombreAlumno 		= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
		}
		
		List<ResPeriodo> lisPeriodo = resPeriodoDao.lisTodos("");
		List<ResRazon> lisRazon 	= resRazonDao.getListAll(" ORDER BY 2");	
		
		ResAlumno alumno = new ResAlumno();		
		if(resAlumnoDao.existeReg(codigoAlumno,folio)){
			alumno = resAlumnoDao.mapeaRegId(codigoAlumno,folio);
		}else {
			alumno.setFolio(resAlumnoDao.maximoReg(codigoAlumno));
			alumno.setMatricula(codigoAlumno);
			alumno.setPeriodoId(periodoId);
		}

		ResPeriodo periodo = new ResPeriodo();	
		if(resPeriodoDao.existeReg(periodoId)){
			periodo = resPeriodoDao.mapeaRegId(periodoId);
			alumno.setFechaInicio(periodo.getFechaIni());
			alumno.setFechaFinal(periodo.getFechaFin());
		}else {
			periodo = lisPeriodo.get(0);
			alumno.setFechaInicio(periodo.getFechaIni());
			alumno.setFechaFinal(periodo.getFechaFin());
		}

		modelo.addAttribute("alumno", alumno);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("lisPeriodo", lisPeriodo);
		modelo.addAttribute("lisRazon", lisRazon);
		
		return "residencia/alumno/nuevoExterno";
	}

	@RequestMapping("/residencia/alumno/nuevoInterno")
	public String residenciaAlumnoNuevoInterno(HttpServletRequest request, Model modelo){
		
		String mensaje 		= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		String periodoId 	= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String folio	 	= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		
		String codigoAlumno 	= "0";
		String nombreAlumno		= "-";			
		HttpSession sesion = ((HttpServletRequest)request).getSession();		
		if (sesion!=null){
			codigoAlumno 		= (String) sesion.getAttribute("codigoAlumno");
			nombreAlumno 		= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");			
		}
		
		List<ResPeriodo> lisPeriodo = resPeriodoDao.lisTodos("");
		List<ResRazon> lisRazon 	= resRazonDao.getListAll(" ORDER BY 2");

		ResAlumno alumno = new ResAlumno();
		
		if(resAlumnoDao.existeReg(codigoAlumno,folio)) {
			alumno = resAlumnoDao.mapeaRegId(codigoAlumno,folio);
		}else {
			alumno.setFolio(resAlumnoDao.maximoReg(codigoAlumno));
			alumno.setMatricula(codigoAlumno);
			alumno.setPeriodoId(periodoId);
		}
		
		ResPeriodo periodo = new ResPeriodo();	
		if(resPeriodoDao.existeReg(periodoId)){
			periodo = resPeriodoDao.mapeaRegId(periodoId);
			alumno.setFechaInicio(periodo.getFechaIni());
			alumno.setFechaFinal(periodo.getFechaFin());
		}else {
			periodo = lisPeriodo.get(0);
			alumno.setFechaInicio(periodo.getFechaIni());
			alumno.setFechaFinal(periodo.getFechaFin());
		}
		
		modelo.addAttribute("alumno", alumno);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("lisPeriodo", lisPeriodo);
		modelo.addAttribute("lisRazon", lisRazon);
		
		return "residencia/alumno/nuevoInterno";
	}
	
	@RequestMapping("/residencia/alumno/grabar")
	public String residenciaAlumnoGrabar(HttpServletRequest request, Model modelo){
		
		String matricula 		= request.getParameter("Matricula")==null?"0":request.getParameter("Matricula");
		String folio	 		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String calle			= request.getParameter("Calle")==null?"-":request.getParameter("Calle");
		String colonia			= request.getParameter("Colonia")==null?"-":request.getParameter("Colonia");
		String municipio		= request.getParameter("Municipio")==null?"-":request.getParameter("Municipio");
		String telArea			= request.getParameter("TelArea")==null?"-":request.getParameter("TelArea");
		String telNum			= request.getParameter("TelNum")==null?"-":request.getParameter("TelNum");
		String tutNombre		= request.getParameter("TutNombre")==null?"-":request.getParameter("TutNombre");
		String tutApellidos		= request.getParameter("TutApellidos")==null?"-":request.getParameter("TutApellidos");
		String razon			= request.getParameter("Razon")==null?"0":request.getParameter("Razon");
		String usuario			= request.getParameter("Usuario")==null?"-":request.getParameter("Usuario");
		String fechaIni			= request.getParameter("FechaInicio")==null?"-":request.getParameter("FechaInicio");
		String fechaFin			= request.getParameter("FechaFinal")==null?"-":request.getParameter("FechaFinal");
		String numero			= request.getParameter("Numero")==null?"-":request.getParameter("Numero");
		String periodoId 		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String esPermanente		= request.getParameter("Permanente")==null?"-":request.getParameter("Permanente");
		String residenciaId		= request.getParameter("ResidenciaId")==null?"-":request.getParameter("ResidenciaId");
		String dormitorio		= request.getParameter("Dormitorio")==null?"0":request.getParameter("Dormitorio");
		String mensaje			= "0";
		
		ResAlumno objeto = new ResAlumno();

		objeto.setFolio(folio);
		objeto.setMatricula(matricula);
		objeto.setCalle(calle);
		objeto.setColonia(colonia);
		objeto.setMunicipio(municipio);
		objeto.setTelArea(telArea);
		objeto.setTelNum(telNum);
		objeto.setTutNombre(tutNombre);
		objeto.setTutApellidos(tutApellidos);
		objeto.setRazon(razon);
		objeto.setUsuario(usuario);
		objeto.setFechaInicio(fechaIni);
		objeto.setFechaFinal(fechaFin);
		objeto.setNumero(numero);
		objeto.setPeriodoId(periodoId);
		objeto.setEsPermanente(esPermanente);
		objeto.setResidenciaId(residenciaId);
		objeto.setDormitorio(dormitorio);
		
		if(resAlumnoDao.existeReg(matricula,folio)) {
			if(resAlumnoDao.updateReg(objeto)) {
				mensaje = "1";
			}
		}else {
			if(resAlumnoDao.insertReg(objeto)) {
				mensaje = "1";
			}
		}
		
		if(residenciaId.equals("E")) {
			return "redirect:/residencia/alumno/nuevoExterno?Mensaje="+mensaje+"&PeriodoId="+periodoId+"&Folio="+folio;
		}else {
			return "redirect:/residencia/alumno/nuevoInterno?Mensaje="+mensaje+"&PeriodoId="+periodoId+"&Folio="+folio;
		}
	}
	
	@RequestMapping("/residencia/alumno/borrarListado")
	public String residenciaAlumnoBorrarListado(HttpServletRequest request, Model modelo) {
		
		String matricula 		= request.getParameter("Matricula")==null?"0":request.getParameter("Matricula");		
		String folio	 		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");		
		if (resAlumnoDao.existeReg(matricula, folio)) {
			resAlumnoDao.deleteReg(matricula, folio);
		}		
		
		return "redirect:/residencia/alumno/listado";
	}
	
	@RequestMapping("/residencia/alumno/copiaExterno")
	public String residenciaAlumnoCopiaExterno(HttpServletRequest request, Model modelo){
		
		String periodoId 	= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String folio	 	= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String mensaje 		= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");

		String codigoAlumno 	= "0";
		String nombreAlumno		= "-";
		String codigoPersonal	= "-";
		HttpSession sesion = ((HttpServletRequest)request).getSession();		
		if (sesion!=null){
			codigoAlumno 		= (String) sesion.getAttribute("codigoAlumno");
			nombreAlumno 		= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
		}
		
		List<ResPeriodo> lisPeriodo = resPeriodoDao.lisTodos("");
		List<ResRazon> lisRazon 	= resRazonDao.getListAll(" ORDER BY 2");	
		
		ResAlumno alumno = new ResAlumno();		
		if(resAlumnoDao.existeReg(codigoAlumno,folio)){
			alumno = resAlumnoDao.mapeaRegId(codigoAlumno,folio);
			alumno.setFolio(resAlumnoDao.maximoReg(codigoAlumno));
		}

		ResPeriodo periodo = new ResPeriodo();	
		if(resPeriodoDao.existeReg(periodoId)){
			periodo = resPeriodoDao.mapeaRegId(periodoId);
			alumno.setFechaInicio(periodo.getFechaIni());
			alumno.setFechaFinal(periodo.getFechaFin());
		}else {
			periodo = lisPeriodo.get(0);
			alumno.setFechaInicio(periodo.getFechaIni());
			alumno.setFechaFinal(periodo.getFechaFin());
		}

		modelo.addAttribute("alumno", alumno);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("lisPeriodo", lisPeriodo);
		modelo.addAttribute("lisRazon", lisRazon);
		modelo.addAttribute("folio", folio);
		modelo.addAttribute("periodoId", periodoId);
		
		return "residencia/alumno/copiaExterno";
	}

	@RequestMapping("/residencia/alumno/copiaInterno")
	public String residenciaAlumnoCopiaInterno(HttpServletRequest request, Model modelo){
		
		String mensaje 		= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		String periodoId 	= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String folio	 	= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		
		String codigoAlumno 	= "0";
		String nombreAlumno		= "-";			
		HttpSession sesion = ((HttpServletRequest)request).getSession();		
		if (sesion!=null){
			codigoAlumno 		= (String) sesion.getAttribute("codigoAlumno");
			nombreAlumno 		= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");			
		}
		
		List<ResPeriodo> lisPeriodo = resPeriodoDao.lisTodos("");
		List<ResRazon> lisRazon 	= resRazonDao.getListAll(" ORDER BY 2");

		ResAlumno alumno = new ResAlumno();
		
		if(resAlumnoDao.existeReg(codigoAlumno,folio)) {
			alumno = resAlumnoDao.mapeaRegId(codigoAlumno,folio);
			alumno.setFolio(resAlumnoDao.maximoReg(codigoAlumno));
		}
		
		ResPeriodo periodo = new ResPeriodo();	
		if(resPeriodoDao.existeReg(periodoId)){
			periodo = resPeriodoDao.mapeaRegId(periodoId);
			alumno.setFechaInicio(periodo.getFechaIni());
			alumno.setFechaFinal(periodo.getFechaFin());
		}else {
			periodo = lisPeriodo.get(0);
			alumno.setFechaInicio(periodo.getFechaIni());
			alumno.setFechaFinal(periodo.getFechaFin());
		}
		
		modelo.addAttribute("alumno", alumno);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("lisPeriodo", lisPeriodo);
		modelo.addAttribute("lisRazon", lisRazon);
		modelo.addAttribute("folio", folio);
		modelo.addAttribute("periodoId", periodoId);
		
		return "residencia/alumno/copiaInterno";
	}
	
	@Transactional
	@RequestMapping("/residencia/alumno/cambiarResidencia")
	public String residenciaAlumnoCambiarResidencia(HttpServletRequest request, Model modelo){
		
		String folio	 	= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String codigoAlumno	= "0";
		HttpSession sesion	= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoAlumno 	= (String) sesion.getAttribute("codigoAlumno");
		}
		
		ResDatos resDatos = new ResDatos();		
		ResAlumno resAlumno			= resAlumnoDao.mapeaRegId(codigoAlumno, folio);
		if (alumAcademicoDao.updateResidencia(codigoAlumno, resAlumno.getResidenciaId(), resAlumno.getDormitorio(), "N", "1")) {
			if(resAlumno.getResidenciaId().equals("E")) {		
				resDatos.setPeriodoId(resAlumno.getPeriodoId());
				resDatos.setCalle(resAlumno.getCalle());
				resDatos.setColonia(resAlumno.getColonia());
				resDatos.setMpio(resAlumno.getMunicipio());
				resDatos.setTelArea(resAlumno.getTelArea());
				resDatos.setTelNum(resAlumno.getTelNum());
				resDatos.setNombreTut(resAlumno.getTutNombre());
				resDatos.setApellidoTut(resAlumno.getTutApellidos());
				resDatos.setRazon(resAlumno.getRazon());
				resDatos.setUsuario(resAlumno.getUsuario());
				resDatos.setFecha(resAlumno.getFecha());
				resDatos.setNumero(resAlumno.getNumero());			
				if(resDatosDao.existeReg(resAlumno.getMatricula())) {
					resDatosDao.updateReg(resDatos);
				}else {
					resDatos.setMatricula(resAlumno.getMatricula());
					resDatosDao.insertReg(resDatos);
				}
			}
		}
		
		return "redirect:/residencia/alumno/listado";
	}
	
}