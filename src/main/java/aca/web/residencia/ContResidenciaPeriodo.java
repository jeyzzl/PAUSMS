package aca.web.residencia;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumAcademico;
import aca.alumno.spring.AlumAcademicoDao;
import aca.alumno.spring.AlumPersonalDao;
import aca.catalogo.spring.CatPeriodoDao;
import aca.catalogo.spring.ResRazonesDao;
import aca.residencia.spring.ResAlumno;
import aca.residencia.spring.ResAlumnoDao;
import aca.residencia.spring.ResDatos;
import aca.residencia.spring.ResDatosDao;
import aca.residencia.spring.ResPeriodo;
import aca.residencia.spring.ResPeriodoDao;
import aca.residencia.spring.ResRazon;
import aca.residencia.spring.ResRazonDao;

@Controller
public class ContResidenciaPeriodo{
	
	@Autowired
	private ResPeriodoDao resPeriodoDao;
	
	@Autowired
	private ResAlumnoDao resAlumnoDao;
	
	@Autowired
	private ResRazonDao resRazonDao;

	@Autowired
	ResRazonesDao resRazonesDao;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;
	
	@Autowired
	private AlumAcademicoDao alumAcademicoDao;
	
	@Autowired
	private ResDatosDao resDatosDao;
	
	
	@RequestMapping("/residencia/periodo/listado")
	public String residenciaPeriodoListado(Model modelo){
		
		List<ResPeriodo> lisPeriodo					= resPeriodoDao.lisTodos(" ORDER BY ENOC.RES_PERIODO.FECHA_INI");
		HashMap<String, String> mapaRegistrados 	= resAlumnoDao.mapaPorPeriodo();
		HashMap<String, String> mapaActualizados 	= resAlumnoDao.mapaActualizados();				
		
		modelo.addAttribute("lisPeriodo", lisPeriodo);
		modelo.addAttribute("mapaRegistrados", mapaRegistrados);
		modelo.addAttribute("mapaActualizados", mapaActualizados);	

		return "residencia/periodo/listado";
	}
	
	@RequestMapping("/residencia/periodo/alumnos")
	public String residenciaPeriodoAlumnos(HttpServletRequest request, Model modelo){
		
		String periodoId						= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String nombrePeriodo					= resPeriodoDao.mapeaRegId(periodoId).getPeriodoNombre();
		String mensaje							= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		String grabados							= request.getParameter("Grabados")==null?"0":request.getParameter("Grabados");
		String noGrabados						= request.getParameter("NoGrabados")==null?"0":request.getParameter("NoGrabados");
		
		
		List<ResAlumno> lisAlumnos				= resAlumnoDao.lisPorPeriodo(periodoId, " ORDER BY ALUM_APELLIDO(MATRICULA)");
		HashMap<String, String> mapaAlumnos 	= alumPersonalDao.mapaAlumnosEnResidencia(periodoId);
		HashMap<String, String> mapaRazones 	= resRazonesDao.mapaRazones();
		HashMap<String, String> mapaActualizados= resAlumnoDao.mapaActualizados(periodoId);
		
		modelo.addAttribute("periodoId", periodoId);		
		modelo.addAttribute("nombrePeriodo", nombrePeriodo);
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("grabados", grabados);
		modelo.addAttribute("noGrabados", noGrabados);
		
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("mapaRazones", mapaRazones);		
		modelo.addAttribute("mapaActualizados", mapaActualizados);
		
		return "residencia/periodo/alumnos";
	}

	@RequestMapping("/residencia/periodo/nuevo")
	public String residenciaPeriodoNuevo(HttpServletRequest request, Model modelo){
		
		String mensaje 		= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		String periodoId 	= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		
		ResPeriodo periodo = new ResPeriodo();
		
		if(resPeriodoDao.existeReg(periodoId)) {
			periodo = resPeriodoDao.mapeaRegId(periodoId);
		}else {
			periodo.setPeriodoId(resPeriodoDao.maximoReg());
		}
		
		modelo.addAttribute("periodo", periodo);
		modelo.addAttribute("mensaje", mensaje);
		
		return "residencia/periodo/nuevo";
	}
	
	@RequestMapping("/residencia/periodo/grabar")
	public String residenciaPeriodoGrabar(HttpServletRequest request, Model modelo){
		
		String periodoId 	= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String nombre		= request.getParameter("Nombre")==null?"-":request.getParameter("Nombre");
		String fechaIni		= request.getParameter("FechaIni")==null?"-":request.getParameter("FechaIni");
		String fechaFin		= request.getParameter("FechaFin")==null?"-":request.getParameter("FechaFin");
		String estado		= request.getParameter("Estado")==null?"-":request.getParameter("Estado");
		String mensaje		= "0";
		
		ResPeriodo periodo = new ResPeriodo();

		periodo.setPeriodoId(periodoId);
		periodo.setPeriodoNombre(nombre);
		periodo.setFechaIni(fechaIni);
		periodo.setFechaFin(fechaFin);
		periodo.setEstado(estado);
		
		if(resPeriodoDao.existeReg(periodoId)) {
			if(resPeriodoDao.updateReg(periodo)) {
				mensaje = "1";
			}
		}else {
			periodo.setPeriodoId(resPeriodoDao.maximoReg());
			if(resPeriodoDao.insertReg(periodo)) {
				mensaje = "1";
			}
		}
		
		modelo.addAttribute("periodo", periodo);
		modelo.addAttribute("mensaje", mensaje);
		
		return "residencia/periodo/nuevo";
	}
	
	@RequestMapping("/residencia/periodo/traspaso")
	public String residenciaPeriodoTraspaso(HttpServletRequest request, Model modelo){
		
		String mensaje 			= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		String periodoIdDe 		= request.getParameter("De")==null?"0":request.getParameter("De");
		String periodoIdPara 	= request.getParameter("Para")==null?"0":request.getParameter("Para");
		String razonId 			= request.getParameter("RazonId")==null?"0":request.getParameter("RazonId");
		String grabados			= request.getParameter("Grabados")==null?"0":request.getParameter("Grabados");
		String noGrabados		= request.getParameter("NoGrabados")==null?"0":request.getParameter("NoGrabados");
		
		List<ResRazon> lisResRazon 		= resRazonDao.getListAll("");
		List<ResPeriodo> lisPeriodo		= resPeriodoDao.lisTodos(" ORDER BY FECHA_INI");
		
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("lisResRazon", lisResRazon);
		modelo.addAttribute("lisPeriodo", lisPeriodo);
		modelo.addAttribute("grabados", grabados);
		modelo.addAttribute("noGrabados", noGrabados);
		
		return "residencia/periodo/traspaso";
	}

	@RequestMapping("/residencia/periodo/grabarTraspaso")
	public String residenciaPeriodoGrabarTraspaso(HttpServletRequest request, Model modelo){
		
		String periodoIdDe 		= request.getParameter("De")==null?"0":request.getParameter("De");
		String periodoIdPara 	= request.getParameter("Para")==null?"0":request.getParameter("Para");
		String razonId 			= request.getParameter("RazonId")==null?"0":request.getParameter("RazonId");
		String mensaje 			= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		
		String codigoEmpleado   = "";
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();		
		if (sesion!=null){			
			codigoEmpleado 		= (String) sesion.getAttribute("codigoEmpleado");
		}
		
		List<ResAlumno> lisPorPeriodoRazon = resAlumnoDao.lisPorPeriodoRazon(periodoIdDe, razonId);
		
		ResAlumno resAlumno = new ResAlumno();
		
		int grabados 	= 0;
		int noGrabados 	= 0;
				
		for(ResAlumno alumno : lisPorPeriodoRazon) {
			resAlumno.setMatricula(alumno.getMatricula());
			resAlumno.setFolio(resAlumnoDao.maximoReg(alumno.getMatricula()));
			resAlumno.setCalle(alumno.getCalle());
			resAlumno.setColonia(alumno.getColonia());
			resAlumno.setMunicipio(alumno.getMunicipio());
			resAlumno.setTelArea(alumno.getTelArea());
			resAlumno.setTelNum(alumno.getTelNum());
			resAlumno.setTutNombre(alumno.getTutNombre());
			resAlumno.setTutApellidos(alumno.getTutApellidos());
			resAlumno.setRazon(razonId);
			resAlumno.setUsuario(codigoEmpleado);
			resAlumno.setFecha(alumno.getFecha());
			resAlumno.setNumero(alumno.getNumero());
			resAlumno.setPeriodoId(periodoIdPara);
			resAlumno.setEsPermanente(alumno.getEsPermanente());
			resAlumno.setResidenciaId(alumno.getResidenciaId());
			resAlumno.setDormitorio(alumno.getDormitorio());
			resAlumno.setFechaInicio(alumno.getFechaInicio());
			resAlumno.setFechaFinal(alumno.getFechaFinal());
			
			if(resAlumnoDao.insertReg(resAlumno)) {
				mensaje = "1";
				grabados++;
			}else {
				noGrabados++;
			}
		}
		
		return "redirect:/residencia/periodo/traspaso?De="+periodoIdDe+"&Para="+periodoIdPara+"&RazonId="+razonId+"&Mensaje="+mensaje+"&Grabados="+grabados+"&NoGrabados="+noGrabados;
	}
	
	@RequestMapping("/residencia/periodo/actualizarResidencia")
	public String residenciaPeriodoActualizarResidencia(HttpServletRequest request, Model modelo){
		
		String periodoId	= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String mensaje 		= "";
		
		List<ResAlumno> lisAlumnos				= resAlumnoDao.lisPorPeriodo(periodoId, " ORDER BY ALUM_APELLIDO(MATRICULA)");
		
		ResDatos resDatos = new ResDatos();
		
		int grabados 	= 0;
		int noGrabados 	= 0;
		
		for (ResAlumno alumno : lisAlumnos){
			if(alumAcademicoDao.existeReg(alumno.getMatricula())) {
				AlumAcademico alumAcademico = alumAcademicoDao.mapeaRegId(alumno.getMatricula());
				if(!alumno.getResidenciaId().equals(alumAcademico.getResidenciaId())) {
					alumAcademico.setResidenciaId(alumno.getResidenciaId());
					if(alumno.getResidenciaId().equals("E")) {
						alumAcademico.setDormitorio("0");
						resDatos.setPeriodoId(periodoId);
						resDatos.setCalle(alumno.getCalle());
						resDatos.setColonia(alumno.getColonia());
						resDatos.setMpio(alumno.getMunicipio());
						resDatos.setTelArea(alumno.getTelArea());
						resDatos.setTelNum(alumno.getTelNum());
						resDatos.setNombreTut(alumno.getTutNombre());
						resDatos.setApellidoTut(alumno.getTutApellidos());
						resDatos.setRazon(alumno.getRazon());
						resDatos.setUsuario(alumno.getUsuario());
						resDatos.setFecha(alumno.getFecha());
						resDatos.setNumero(alumno.getNumero());
						
						if(resDatosDao.existeReg(alumno.getMatricula())) {
							resDatosDao.updateReg(resDatos);
						}else {
							resDatos.setMatricula(alumno.getMatricula());
							resDatosDao.insertReg(resDatos);
						}
					}else {
						alumAcademico.setDormitorio(alumno.getDormitorio());
					}
					
					if(alumAcademicoDao.updateReg(alumAcademico)) {
						grabados++;
						mensaje = "1";
					}else {
						noGrabados++;
					}
				}
			}
		}
		
		return "redirect:/residencia/periodo/alumnos?PeriodoId="+periodoId+"&Mensaje="+mensaje+"&Grabados="+grabados+"&NoGrabados="+noGrabados;
	}
	
}