package aca.web.cargagrupo;

import java.util.HashMap;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.carga.spring.Carga;
import aca.carga.spring.CargaBloque;
import aca.carga.spring.CargaBloqueDao;
import aca.carga.spring.CargaDao;
import aca.catalogo.spring.CatPeriodo;
import aca.catalogo.spring.CatPeriodoDao;

@Controller
public class ContCargaGrupoCarga {	
	
	@Autowired
	CargaDao cargaDao;
	
	@Autowired
	CargaBloqueDao cargaBloqueDao;
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	
	
	@Autowired
	CatPeriodoDao catPeriodoDao; 
	
	public void enviarConEnoc(HttpServletRequest request, String url){ 
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}	
	
	@RequestMapping("/carga_grupo/carga/accion")
	public String cargaGrupoCargaAccion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContCargaGrupoCarga|cargaGrupoCargaAccion");
		return "carga_grupo/carga/accion";
	}
	
	@RequestMapping("/carga_grupo/carga/carga")
	public String cargaGrupoCargaCarga(HttpServletRequest request, Model modelo) {
		
		List<Carga> lisCargas 					= cargaDao.getListAll(" ORDER BY ORDEN DESC");
		HashMap<String, String> mapaBloques 	= cargaBloqueDao.mapaTotalBloques();
		
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("mapaBloques", mapaBloques);
		
		return "carga_grupo/carga/carga";
	}
	
	@RequestMapping("/carga_grupo/carga/bloques")
	public String cargaGrupoCargaBloques(HttpServletRequest request, Model modelo) {
		
		String cargaId 		= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		
		List<CargaBloque> lisBloques 			= cargaBloqueDao.getLista(cargaId, " ORDER BY BLOQUE_ID, NOMBRE_BLOQUE");
		modelo.addAttribute("lisBloques", lisBloques);
		
		return "carga_grupo/carga/bloques";
	}
	
	@RequestMapping("/carga_grupo/carga/editarCarga")
	public String cargaGrupoCargaEditarCarga(HttpServletRequest request, Model modelo){
		
		String cargaId 		= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		Carga carga 		= cargaDao.mapeaRegId(cargaId);
		
		List<CatPeriodo> lisPeriodos 			= catPeriodoDao.getListAll(" ORDER BY PERIODO_ID DESC");
		
		modelo.addAttribute("carga", carga);
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		
		return "carga_grupo/carga/editarCarga";
	}
	
	@RequestMapping("/carga_grupo/carga/grabarCarga")
	public String cargaGrupoCargaGrabarCarga(HttpServletRequest request, Model modelo) {
		
		String cargaId 		= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String nombreCarga	= request.getParameter("NombreCarga")==null?"-":request.getParameter("NombreCarga");
		String fechaCreada	= request.getParameter("FCreada")==null?aca.util.Fecha.getHoy():request.getParameter("FCreada");
		String periodo		= request.getParameter("Periodo")==null?"0":request.getParameter("Periodo");
		String ciclo		= request.getParameter("Ciclo")==null?"0":request.getParameter("Ciclo");
		String fechaInicio	= request.getParameter("fInicio")==null?aca.util.Fecha.getHoy():request.getParameter("fInicio");
		String fechaFinal	= request.getParameter("fFinal")==null?aca.util.Fecha.getHoy():request.getParameter("fFinal");
		String fechaExtra	= request.getParameter("fExtra")==null?aca.util.Fecha.getHoy():request.getParameter("fExtra");
		String numCursos	= request.getParameter("NumCursos")==null?"0":request.getParameter("NumCursos");
		String estado		= request.getParameter("Estado")==null?"A":request.getParameter("Estado");
		String tipo			= request.getParameter("TipoCarga")==null?"O":request.getParameter("TipoCarga");
		String semanas		= request.getParameter("Semanas")==null?"16":request.getParameter("Semanas");
		String evalua		= request.getParameter("Evalua")==null?"S":request.getParameter("Evalua");
		String bloqueo 		= request.getParameter("Bloqueo")==null?"0":request.getParameter("Bloqueo");
		
		String mensaje 		= "-";
		
		Carga carga 		= new Carga();
		carga.setCargaId(cargaId);
		carga.setNombreCarga(nombreCarga);
		carga.setFCreada(fechaCreada);
		carga.setPeriodo(periodo);
		carga.setCiclo(ciclo);
		carga.setFInicio(fechaInicio);
		carga.setFFinal(fechaFinal);
		carga.setFExtra(fechaExtra);
		carga.setNumCursos(numCursos);
		carga.setEstado(estado);
		carga.setTipoCarga(tipo);
		carga.setSemanas(semanas);
		carga.setEvalua(evalua);
		carga.setBloqueo(bloqueo);
		
		if (cargaDao.existeReg(cargaId)) {
			if (cargaDao.updateReg(carga)) {
				mensaje = "1";
			}else{
				mensaje = "2";
			}
		}else {
			if (cargaDao.insertReg(carga)) {
				mensaje = "1";
			}else{
				mensaje = "2";
			}
		}	
		
		return "redirect:/carga_grupo/carga/editarCarga?CargaId="+carga.getCargaId()+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/carga_grupo/carga/borrarCarga")
	public String cargaGrupoCargaBorrarCarga(HttpServletRequest request, Model modelo) {
		
		String cargaId 		= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");		
		
		if (cargaDao.existeReg(cargaId)) {
			cargaDao.deleteReg(cargaId);
		}		
		
		return "redirect:/carga_grupo/carga/carga";
	}	
}