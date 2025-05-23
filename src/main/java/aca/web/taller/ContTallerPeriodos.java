package aca.web.taller;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.bec.spring.BecPeriodo;
import aca.bec.spring.BecPeriodoDao;
import aca.bec.spring.BecPuestoDao;
@Controller
public class ContTallerPeriodos {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	private BecPeriodoDao becPeriodoDao;
	
	@Autowired
	private BecPuestoDao becPuestoDao;
	
	@RequestMapping("/taller/periodos/listado")
	public String tallerPeriodosListado(HttpServletRequest request, Model modelo){
		
		List<BecPeriodo> listaPeriodos 			= becPeriodoDao.getAll("ORDER BY PERIODO_ID DESC");
		HashMap<String,String> mapaPeriodos		= becPuestoDao.mapaPeriodos();
		
		modelo.addAttribute("listaPeriodos", listaPeriodos);
		modelo.addAttribute("mapaPeriodos", mapaPeriodos);

		return "taller/periodos/listado";
	}
	
	@RequestMapping("/taller/periodos/editar")
	public String tallerPeriodosEditar(HttpServletRequest request, Model modelo){
		
		String periodoId  			= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");		
		BecPeriodo becPeriodo 		= new BecPeriodo();
		if(becPeriodoDao.existeReg(periodoId)){
			becPeriodo = becPeriodoDao.mapeaRegId(periodoId);
		}
		
		modelo.addAttribute("becPeriodo", becPeriodo);
		
		return "taller/periodos/editar";
	}
	
	@RequestMapping("/taller/periodos/grabar")
	public String tallerPeriodosGrabar(HttpServletRequest request, Model modelo){
		
		String periodoId  			= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String periodoNombre 		= request.getParameter("PeriodoNombre")==null?"0":request.getParameter("PeriodoNombre");
		String fechaIni 			= request.getParameter("FechaIni")==null?"0":request.getParameter("FechaIni");
		String fechaFin				= request.getParameter("FechaFin")==null?"0":request.getParameter("FechaFin");
		String estado				= request.getParameter("Estado")==null?"0":request.getParameter("Estado");
		String idEjercicio			= request.getParameter("IdEjercicio")==null?"0":request.getParameter("IdEjercicio");
		String tipo					= request.getParameter("Tipo")==null?"0":request.getParameter("Tipo");
		String fechasUniversidad	= request.getParameter("FechasUniversidad")==null?"0":request.getParameter("FechasUniversidad");
		String fechasPrepa			= request.getParameter("FechasPreparatoria")==null?"0":request.getParameter("FechasPreparatoria");
		String fechasPeriodo		= request.getParameter("FechasPeriodo")==null?"0":request.getParameter("FechasPeriodo");
		String mensaje 				= "";		
		BecPeriodo objeto = new BecPeriodo();
		
		if(becPeriodoDao.existeReg(periodoId)) {
			objeto = becPeriodoDao.mapeaRegId(periodoId);
			
			objeto.setPeriodoNombre(periodoNombre);
			objeto.setFechaIni(fechaIni);
			objeto.setFechaFin(fechaFin);
			objeto.setEstado(estado);
			objeto.setIdEjercicio(idEjercicio);
			objeto.setTipo(tipo);
			objeto.setFechasUniversidad(fechasUniversidad);
			objeto.setFechasPrepa(fechasPrepa);
			objeto.setFechasPeriodo(fechasPeriodo);

			if(becPeriodoDao.updateReg(objeto)) {				
				mensaje = "Actualizado";
			}
		}else {
			objeto.setPeriodoId(periodoId);
			objeto.setPeriodoNombre(periodoNombre);
			objeto.setFechaIni(fechaIni);
			objeto.setFechaFin(fechaFin);
			objeto.setEstado(estado);		
			objeto.setIdEjercicio(idEjercicio);
			objeto.setTipo(tipo);
			objeto.setFechasUniversidad(fechasUniversidad);
			objeto.setFechasPrepa(fechasPrepa);
			objeto.setFechasPeriodo(fechasPeriodo);

			if(becPeriodoDao.insertReg(objeto)) {
				mensaje = "Guardado";
			}
		}
		
		return "redirect:/taller/periodos/editar?Mensaje="+mensaje+"&PeriodoId="+periodoId;
	}
	
	@RequestMapping("/taller/periodos/borrar")
	public String tallerPeriodosBorrar(HttpServletRequest request, Model modelo){	
		
		String periodoId		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String mensaje			= "-";
		
		if (becPeriodoDao.existeReg(periodoId)) {			
			if (becPeriodoDao.deleteReg(periodoId)) {
				mensaje = "¡ Borrado !";
			}else {
				mensaje = "¡ Error al borrar !";
			}
		}		
		
		return "redirect:/taller/periodos/listado?Mensaje="+mensaje;
	}
	
}