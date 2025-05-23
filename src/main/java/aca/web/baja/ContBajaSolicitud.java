package aca.web.baja;

import java.util.List;
import java.util.HashMap;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumAcademicoDao;
import aca.alumno.spring.AlumEstado;
import aca.alumno.spring.AlumEstadoDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanDao;
import aca.baja.spring.BajaAlumno;
import aca.baja.spring.BajaAlumnoDao;
import aca.baja.spring.BajaAlumpaso;
import aca.baja.spring.BajaAlumpasoDao;
import aca.baja.spring.BajaPaso;
import aca.baja.spring.BajaPasoDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaBloque;
import aca.carga.spring.CargaBloqueDao;
import aca.carga.spring.CargaDao;
import aca.catalogo.spring.CatCarreraDao;
import aca.internado.spring.ComAutorizacionDao;
import aca.plan.spring.MapaCursoDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.AlumnoCurso;
import aca.vista.spring.AlumnoCursoDao;
import aca.vista.spring.MaestrosDao;
import adm.fecha.Fecha;

@Controller
public class ContBajaSolicitud {
	
	@Autowired
	AlumPersonalDao alumPersonalDao;
	
	@Autowired
	BajaAlumnoDao bajaAlumnoDao;
	
	@Autowired
	CargaDao cargaDao;
	
	@Autowired
	CargaBloqueDao cargaBloqueDao;
	
	@Autowired
	AlumPlanDao alumPlanDao;
	
	@Autowired
	MapaPlanDao mapaPlanDao;
	
	@Autowired
	MapaCursoDao mapaCursoDao;
	
	@Autowired
	AlumnoCursoDao alumnoCursoDao;
	
	@Autowired
	AccesoDao accesoDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	MaestrosDao maestrosDao;
	
	@Autowired
	BajaPasoDao bajaPasoDao;
	
	@Autowired
	BajaAlumpasoDao bajaAlumpasoDao;
	
	@Autowired
	ComAutorizacionDao comAutorizacionDao;
	
	@Autowired
	AlumAcademicoDao alumAcademicoDao;	
	
	@Autowired
	AlumEstadoDao alumEstadoDao;
	
	
	
	@RequestMapping("/baja/solicitud/solicitud")
	public String bajaSolicitudSolicitud(HttpServletRequest request, Model modelo){ 
		
		String cargaId 		= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String bloqueId 	= request.getParameter("BloqueId")==null?"0":request.getParameter("BloqueId");	
		boolean existe		= false;
		String codigoAlumno = "0";
		String alumnoNombre	= "-";		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();		
		if (sesion!=null){
			codigoAlumno		= (String) sesion.getAttribute("codigoAlumno");
			alumnoNombre 		= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
		}
		
		BajaAlumno bajaAlumno = new BajaAlumno();		
		List<Carga> lisCargas 			= cargaDao.lisCargasActivasAlumno(codigoAlumno, " ORDER BY CARGA_ID");
		if (cargaId.equals("0")&&lisCargas.size()>0) {
			cargaId = lisCargas.get(0).getCargaId();
		}
		
		List<CargaBloque> lisBloques 	= cargaBloqueDao.lisBloquesAlumno(cargaId, codigoAlumno, "ORDER BY CARGA_ID,BLOQUE_ID");
		if (bloqueId.equals("0") && lisBloques.size() >= 1) {
			bloqueId = lisBloques.get(0).getBloqueId();
		}
		
		if (bajaAlumnoDao.existeSolicitud(codigoAlumno, cargaId, bloqueId)){
			bajaAlumno = bajaAlumnoDao.mapeaSolicitud(codigoAlumno, cargaId, bloqueId);
			existe = true;
		}
		
		AlumEstado alumEstado 	= new AlumEstado();		
		if (alumEstadoDao.existeReg(codigoAlumno, cargaId, bloqueId)){
			alumEstado 	= alumEstadoDao.mapeaRegId(codigoAlumno, cargaId, bloqueId);
		}
		
		String carrera = mapaPlanDao.getCarreraSe(alumEstado.getPlanId());
		
		modelo.addAttribute("alumEstado", alumEstado);
		modelo.addAttribute("carrera", carrera);
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("bloqueId", bloqueId);		
		modelo.addAttribute("bajaAlumno", bajaAlumno);
		modelo.addAttribute("existe", existe);
		modelo.addAttribute("alumnoNombre", alumnoNombre);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisBloques", lisBloques);
		
		return "baja/solicitud/solicitud";
	}

	@RequestMapping("/baja/solicitud/guardarSolicitud")
	public String bajaSolicitudGuardarSolicitud(HttpServletRequest request, Model modelo){
		
		String cargaId 		= request.getParameter("CargaId") == null ? "0" : request.getParameter("CargaId");
		String bloqueId 	= request.getParameter("BloqueId") == null ? "0" : request.getParameter("BloqueId");
		String razonId	 	= request.getParameter("RazonId") == null ? "0" : request.getParameter("RazonId");
		String comentario 	= request.getParameter("Comentario") == null ? "0" : request.getParameter("Comentario");
		String mensaje		= "-";
		
		String codigoAlumno = "0";
		
		HttpSession sesion	= ((HttpServletRequest)request).getSession();		
		if (sesion!=null){
			codigoAlumno	= (String) sesion.getAttribute("codigoAlumno");
		}
		
		AlumEstado alumEstado 	= new AlumEstado();		
		if (alumEstadoDao.existeReg(codigoAlumno, cargaId, bloqueId)){
			alumEstado 	= alumEstadoDao.mapeaRegId(codigoAlumno, cargaId, bloqueId);			 
		}
		AlumPlan alumPlan 	= alumPlanDao.mapeaRegId(codigoAlumno, alumEstado.getPlanId());
		
		BajaAlumno baja 	= new BajaAlumno();		
		baja.setCodigoPersonal(codigoAlumno);
		baja.setCargaId(cargaId);
		baja.setBloqueId(bloqueId);
		baja.setCarreraId(alumEstado.getCarreraId());
		baja.setPlanId(alumEstado.getPlanId());
		baja.setfInicio(alumPlan.getFInicio());
		baja.setfBaja(Fecha.getHoy());
		baja.setComentario(comentario);
		baja.setRazonId(razonId);
		baja.setCreditos(String.valueOf(alumnoCursoDao.getCreditos(codigoAlumno, cargaId, "'I','1','2','4','5','6'")));
		if(bajaAlumnoDao.existeSolicitud(codigoAlumno, cargaId, bloqueId)){			
			baja.setBajaId(bajaAlumnoDao.getBajaId(codigoAlumno, cargaId, bloqueId));
			if(bajaAlumnoDao.updateReg(baja)) {
				mensaje = "Solicitud guardada";
			}
		} else {
			baja.setBajaId(bajaAlumnoDao.maximo());
			if(bajaAlumnoDao.insertReg(baja)) {
				mensaje = "Solicitud guardada";
			}
		}
		
		return "redirect:/baja/solicitud/solicitud?BloqueId="+bloqueId+"&CargaId="+cargaId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/baja/solicitud/formato")
	public String bajaSolicitudFormato(HttpServletRequest request, Model modelo){
		
		String bajaId 			= request.getParameter("BajaId")==null?"0":request.getParameter("BajaId");
		
		Acceso acceso 			= new Acceso();
		String codigoAlumno 	= "0";
		String codigoPersonal	= "0";
		String alumnoNombre		= "-";
		String carreraNombre	= "-";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();		
		if (sesion!=null){
			codigoAlumno		= (String) sesion.getAttribute("codigoAlumno");
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
			alumnoNombre 		= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
			if (accesoDao.existeReg(codigoPersonal)){
				acceso = accesoDao.mapeaRegId(codigoPersonal);
			}
		}
		
		BajaAlumno bajaAlumno = new BajaAlumno();		
		if (bajaAlumnoDao.existeReg(bajaId) ){
			bajaAlumno = bajaAlumnoDao.mapeaRegId(bajaId);
			carreraNombre 		= catCarreraDao.getNombreCarrera(bajaAlumno.getCarreraId());
		}
		
		List<AlumnoCurso> lisCursos 			= alumnoCursoDao.getListAlumnoCargaBloque(codigoAlumno, bajaAlumno.getCargaId(), bajaAlumno.getBloqueId(), " AND TIPOCAL_ID = 'I' ORDER BY NOMBRE_CURSO");
		List<BajaPaso> lisPasos 				= bajaPasoDao.getListAll("ORDER BY PASO_ID");		
		HashMap<String,String> mapaMaestros		= maestrosDao.mapMaestroNombre("NOMBRE");
		HashMap<String,BajaPaso> mapaPasos		= bajaPasoDao.mapPasos();
		
		boolean paso1 = false;		
		BajaAlumpaso bajaAlumpaso	= new BajaAlumpaso();
		if (bajaAlumpasoDao.existeReg(bajaId, "1")==false) {
			bajaAlumpaso.setBajaId(bajaAlumno.getBajaId());
			bajaAlumpaso.setPasoId("1");
			bajaAlumpaso.setFecha(Fecha.getHoy());
			bajaAlumpaso.setEstado("N");
			if (bajaAlumpasoDao.insertReg(bajaAlumpaso)) {
				paso1 = true;
			}			
		}else {
			paso1 = true;
		}
		
		boolean paso2 	= false;
		bajaAlumpaso	= new BajaAlumpaso();
		if (bajaAlumpasoDao.existeReg(bajaId, "2") == false){
			bajaAlumpaso.setBajaId(bajaAlumno.getBajaId());
			bajaAlumpaso.setPasoId("2");
			bajaAlumpaso.setFecha(Fecha.getHoy());
			bajaAlumpaso.setEstado("N");
			if (bajaAlumpasoDao.insertReg(bajaAlumpaso)){
				paso2 = true;
			}			
		}else{
			paso2 = true;
		}
		
		boolean paso3 	= false;
		bajaAlumpaso	= new BajaAlumpaso();
		if (comAutorizacionDao.existeReg(codigoAlumno, bajaAlumno.getCargaId(), bajaAlumno.getBloqueId())) {
			if (bajaAlumpasoDao.existeReg(bajaId, "3")==false) {
				bajaAlumpaso.setBajaId(bajaAlumno.getBajaId());
				bajaAlumpaso.setPasoId("3");
				bajaAlumpaso.setFecha(Fecha.getHoy());
				bajaAlumpaso.setEstado("N");
				if (bajaAlumpasoDao.insertReg(bajaAlumpaso)) {
					paso3 = true;
				}
			}else {
				paso3 = true;
			}
		}
		
		boolean paso4 = false;
		bajaAlumpaso	= new BajaAlumpaso();
		if ( alumAcademicoDao.getResidencia(codigoAlumno).equals("I")) {
			if (bajaAlumpasoDao.existeReg(bajaId, "4")==false) {
				bajaAlumpaso.setBajaId(bajaAlumno.getBajaId());
				bajaAlumpaso.setPasoId("4");
				bajaAlumpaso.setFecha(Fecha.getHoy());
				bajaAlumpaso.setEstado("N");
				if (bajaAlumpasoDao.insertReg(bajaAlumpaso)) {
					paso4 = true;
				}
			}else {
				paso4 = true;
			}
		}
		
		boolean paso5 = false;
		bajaAlumpaso	= new BajaAlumpaso();
		if ( !alumPersonalDao.getNacionalidad(codigoAlumno).equals("91")) {
			if (bajaAlumpasoDao.existeReg(bajaId, "5")==false) {
				bajaAlumpaso.setBajaId(bajaAlumno.getBajaId());
				bajaAlumpaso.setPasoId("5");
				bajaAlumpaso.setFecha(Fecha.getHoy());
				bajaAlumpaso.setEstado("N");
				if (bajaAlumpasoDao.insertReg(bajaAlumpaso)) {
					paso5 = true;
				}
			}else {
				paso5 = true;
			}
		}	
		
		boolean paso6 = false;
		bajaAlumpaso	= new BajaAlumpaso();
		if ( bajaAlumpasoDao.existeReg(bajaId, "6")==false) {
			bajaAlumpaso.setBajaId(bajaAlumno.getBajaId());
			bajaAlumpaso.setPasoId("6");
			bajaAlumpaso.setFecha(Fecha.getHoy());
			bajaAlumpaso.setEstado("N");
			if (bajaAlumpasoDao.insertReg(bajaAlumpaso)) {
				paso6 = true;
			}			
		}else {
			paso6 = true;
		}
		
		boolean paso7 = false;
		bajaAlumpaso	= new BajaAlumpaso();
		if ( bajaAlumpasoDao.existeReg(bajaId, "7")==false) {
			bajaAlumpaso.setBajaId(bajaAlumno.getBajaId());
			bajaAlumpaso.setPasoId("7");
			bajaAlumpaso.setFecha(Fecha.getHoy());
			bajaAlumpaso.setEstado("N");
			if (bajaAlumpasoDao.insertReg(bajaAlumpaso)) {
				paso7 = true;
			}
		}else {
			paso7 = true;
		}		
		
		modelo.addAttribute("acceso", acceso);		
		modelo.addAttribute("bajaAlumno", bajaAlumno);
		modelo.addAttribute("alumnoNombre", alumnoNombre);
		modelo.addAttribute("carreraNombre", carreraNombre);
		modelo.addAttribute("lisCursos", lisCursos);
		modelo.addAttribute("lisPasos", lisPasos);
		modelo.addAttribute("mapaMaestros", mapaMaestros);
		modelo.addAttribute("mapaPasos", mapaPasos);
		modelo.addAttribute("paso1", paso1);
		modelo.addAttribute("paso2", paso2);
		modelo.addAttribute("paso3", paso3);
		modelo.addAttribute("paso4", paso4);
		modelo.addAttribute("paso5", paso5);
		modelo.addAttribute("paso6", paso6);
		modelo.addAttribute("paso7", paso7);
		
		return "baja/solicitud/formato";
	}	
}