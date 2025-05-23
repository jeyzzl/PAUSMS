package aca.web.musica;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.carga.spring.Carga;
import aca.carga.spring.CargaPlanExterno;
import aca.catalogo.spring.CatPeriodo;
import aca.musica.spring.MusiHorario;
import aca.musica.spring.MusiHorarioAlumno;
import aca.vista.spring.Maestros;

@Controller
public class ContMusicaHorario {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	aca.musica.spring.MusiHorarioDao musiHorarioDao;

	@Autowired
	aca.musica.spring.MusiHorarioAlumnoDao musiHorarioAlumnoDao;
	
	@Autowired
	aca.vista.spring.MaestrosDao maestrosDao;
	
	@Autowired
	private aca.catalogo.spring.CatPeriodoDao catPeriodoDao;
	
	@Autowired
	private aca.carga.spring.CargaDao cargaDao;
	
	@Autowired
	private aca.emp.spring.EmpleadoDao empleadoDao;
	
	@RequestMapping("/musica/horario/horario")
	public String musicaHorarioHorario(HttpServletRequest request, Model modelo){
		 
        String periodoId	= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String cargaId		= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		
		List<CatPeriodo> lisPeriodos = catPeriodoDao.getListAll(" ORDER BY PERIODO_ID DESC");
		if(periodoId.equals("0") && lisPeriodos.size() > 0 ) {
			periodoId = lisPeriodos.get(0).getPeriodoId();
		}
		
		List<Carga> lisCargas = cargaDao.getListPeriodo(periodoId," ORDER BY CARGA_ID");
		if (cargaId.equals("0") && lisCargas.size()>0) {
			cargaId = lisCargas.get(0).getCargaId();
    	}
		
		List<Maestros> lisMaestros 			= maestrosDao.lisMaestrosHorarioMusica(cargaId, " ORDER BY NOMBRE");
		HashMap<String,String> mapMaestros 	= maestrosDao.mapMaestrosHorarioMusica(cargaId);
		
		modelo.addAttribute("PeriodoId", periodoId);
		modelo.addAttribute("CargaId", cargaId);
		modelo.addAttribute("lisPeriodos",lisPeriodos);
		modelo.addAttribute("lisCargas",lisCargas);
		modelo.addAttribute("lisMaestros",lisMaestros);
		modelo.addAttribute("mapMaestros",mapMaestros);
		
		return "musica/horario/horario";
	}
	
	@RequestMapping("/musica/horario/horarioMaestro")
	public String musicaHorarioHorarioMaestro(HttpServletRequest request, Model modelo){
		 
        String periodoId		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String cargaId			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String codigoPersonal	= request.getParameter("MaestroId")==null?"0":request.getParameter("MaestroId");
		String nombreMaestro	= empleadoDao.getNombreEmpleado(codigoPersonal, "NOMBRE");
		
		List<MusiHorario> lisHorarioMaestro = musiHorarioDao.getListHorarioMaestro(codigoPersonal, cargaId, " ORDER BY FOLIO");
		
		HashMap<String, MusiHorarioAlumno> mapaHorarioALumno = musiHorarioAlumnoDao.mapaHorarioAlumno(cargaId);
		
		modelo.addAttribute("PeriodoId", periodoId);
		modelo.addAttribute("CargaId", cargaId);
		modelo.addAttribute("codigoPersonal", codigoPersonal);
		modelo.addAttribute("nombreMaestro", nombreMaestro);
		modelo.addAttribute("lisHorarioMaestro",lisHorarioMaestro);
		modelo.addAttribute("mapaHorarioALumno",mapaHorarioALumno);
		
		return "musica/horario/horarioMaestro";
	}

	@RequestMapping("/musica/horario/agregarHorario")
	public String musicaHorarioAgregarHorario(HttpServletRequest request, Model modelo){
		
		String pag			= request.getParameter("Pag")==null?"0":request.getParameter("Pag");
		String periodoId	= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String cargaId		= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String maestroId	= request.getParameter("MaestroId")==null?"0":request.getParameter("MaestroId");
		String folio		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		
		String codigoPersonal	= request.getParameter("MaestroId")==null?"0":request.getParameter("MaestroId");
		String nombreMaestro	= empleadoDao.getNombreEmpleado(codigoPersonal, "NOMBRE");
		
		MusiHorario horario = new MusiHorario();
		
		horario.setCargaId(cargaId);
		horario.setMaestroId(maestroId);
		horario.setFolio(musiHorarioDao.maximoReg());
		
		if(musiHorarioDao.existeReg(folio)) {
			horario = musiHorarioDao.mapeaRegId(folio);
		}
		
		modelo.addAttribute("horario", horario);
		modelo.addAttribute("PeriodoId", periodoId);
		modelo.addAttribute("CargaId", cargaId);
		modelo.addAttribute("codigoPersonal", codigoPersonal);
		modelo.addAttribute("nombreMaestro", nombreMaestro);
		modelo.addAttribute("pag", pag);
		
		return "musica/horario/agregarHorario";
	}
	
	@RequestMapping("/musica/horario/guardar")
	public String musicaHorarioGuardar(HttpServletRequest request){
		
		String cargaId		= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String folio		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String horaInicio	= request.getParameter("HoraInicio")==null?"0":request.getParameter("HoraInicio");
		String horaFinal	= request.getParameter("HoraFinal")==null?"0":request.getParameter("HoraFinal");
		String cupo			= request.getParameter("Cupo")==null?"0":request.getParameter("Cupo");
		String valor		= request.getParameter("Valor")==null?"0":request.getParameter("Valor");
		String dia			= request.getParameter("Dia")==null?"0":request.getParameter("Dia");
		String estado		= request.getParameter("Estado")==null?"0":request.getParameter("Estado");
		String minInicio	= request.getParameter("MinInicio")==null?"0":request.getParameter("MinInicio");
		String minFinal		= request.getParameter("MinFinal")==null?"0":request.getParameter("MinFinal");
		String maestroId	= request.getParameter("MaestroId")==null?"0":request.getParameter("MaestroId");
		String mensaje 		= "0";
		
		MusiHorario musiHorario = new MusiHorario();
		musiHorario.setCargaId(cargaId);
		musiHorario.setHoraInicio(horaInicio);
		musiHorario.setHoraFinal(horaFinal);
		musiHorario.setCupo(cupo);
		musiHorario.setValor(valor);
		musiHorario.setDia(dia);
		musiHorario.setEstado(estado);
		musiHorario.setMinInicio(minInicio);
		musiHorario.setMinFinal(minFinal);
		musiHorario.setMaestroId(maestroId);
		
		if (musiHorarioDao.existeReg(folio)){
			musiHorario.setFolio(folio);
			if (musiHorarioDao.updateReg(musiHorario)){
				mensaje = "1";
			}else {
				mensaje = "2";
			}
		}else {
			musiHorario.setFolio(musiHorarioDao.maximoReg());
			if (musiHorarioDao.insertReg(musiHorario)){
				mensaje = "1";
			}else {
				mensaje = "2";
			}
		}
		
		return "redirect:/musica/horario/horarioMaestro?CargaId="+cargaId+"&MaestroId="+maestroId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/musica/horario/borrarHorario")
	public String musicaHorarioBorrarHorario(HttpServletRequest request, Model modelo) {		
		
		String periodoId		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String cargaId			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String codigoPersonal	= request.getParameter("MaestroId")==null?"0":request.getParameter("MaestroId");
		String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		if (musiHorarioDao.existeReg(folio)) {
			musiHorarioDao.deleteReg(folio);
		}
		
		return "redirect:/musica/horario/horarioMaestro?Folio="+folio+"&PeriodoId="+periodoId+"&CargaId="+cargaId+"&MaestroId="+codigoPersonal;
	}

}
