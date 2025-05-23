package aca.web.servicio;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumPersonalDao;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultadDao;
import aca.plan.spring.MapaPlanDao;
import aca.ssoc.spring.SsocDocAlumDao;
import aca.ssoc.spring.SsocInicioDao;
import aca.ssoc.spring.SsocRequisito;
import aca.ssoc.spring.SsocRequisitoDao;
import aca.ssoc.spring.SsocSectorDao;

@Controller
public class ContServicioRequisitos {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	
	
	@Autowired
	aca.salida.spring.SalSolicitudDao salSolicitudDao;
	
	@Autowired
	aca.salida.spring.SalGrupoDao salGrupoDao;
	
	@Autowired
	aca.vista.spring.MaestrosDao maestrosDao;
	
	@Autowired
	SsocInicioDao ssocInicioDao;
	
	@Autowired
	SsocDocAlumDao ssocDocAlumDao;
	
	@Autowired
	SsocRequisitoDao ssocRequisitoDao;
	
	@Autowired
	SsocSectorDao ssocSectorDao;
	
	@Autowired
	CatFacultadDao catFacultadDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	MapaPlanDao mapaPlanDao;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;	
	
	@RequestMapping("/servicio/requisitos/catalogos")
	public String servicioRequisitosCatalogos(HttpServletRequest request, Model modelo){
		
		 List<SsocRequisito> lisRequisitos = ssocRequisitoDao.getListAll("");
		 
		 modelo.addAttribute("lisRequisitos",lisRequisitos);
		
		return "servicio/requisitos/catalogos";
	}
	
	@RequestMapping("/servicio/requisitos/editar")
	public String servicioRequisitosEditar(HttpServletRequest request, Model modelo){
		
		String requisitoId 				= request.getParameter("RequisitoId")==null?"0":request.getParameter("RequisitoId");
		SsocRequisito requisito	= new SsocRequisito();
			
		if (ssocRequisitoDao.existeReg(requisitoId)) {
			requisito = ssocRequisitoDao.mapeaRegId(requisitoId);
		}
				
		modelo.addAttribute("requisito", requisito);
		
		return "servicio/requisitos/editar";
	}
	
	@RequestMapping("/servicio/requisitos/grabar")
	public String servicioRequisitosGrabar(HttpServletRequest request, Model model){				
		
		String requisitoId 			= request.getParameter("RequisitoId")==null?"0":request.getParameter("RequisitoId");
		String requisitoNombre		= request.getParameter("RequisitoNombre")==null?"-":request.getParameter("RequisitoNombre");
		String orden 				= request.getParameter("Orden")==null?"0":request.getParameter("Orden");
		String mensaje 				= "-";
		
		SsocRequisito requisito	= new SsocRequisito();
		requisito.setRequisitoNombre(requisitoNombre);
		requisito.setOrden(orden);
		
		if (ssocRequisitoDao.existeReg(requisitoId)){
			requisito.setRequisitoId(requisitoId);
			if (ssocRequisitoDao.updateReg(requisito)){
				mensaje = "1";
			}else {
				mensaje = "2";
			}
		}else {
			requisito.setRequisitoId(ssocRequisitoDao.maximoReg());
			if (ssocRequisitoDao.insertReg(requisito)){
				mensaje = "1";
			}else {
				mensaje = "2";
			}
		}		
		return "redirect:/servicio/requisitos/editar?RequisitoId="+requisito.getRequisitoId()+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/servicio/requisitos/borrar")
	public String servicioRequisitosBorrar(HttpServletRequest request, Model model){
		
		String requisitoId 			= request.getParameter("RequisitoId")==null?"0":request.getParameter("RequisitoId");
		if (ssocRequisitoDao.existeReg(requisitoId)) {
			ssocRequisitoDao.deleteReg(requisitoId);
		}
		
		return "redirect:/servicio/requisitos/catalogos";
	}
	
	@RequestMapping("/servicio/requisitos/reglamento")
	public String servicioRequisitosReglamento(HttpServletRequest request, Model modelo){		
		return "servicio/requisitos/reglamento";
	}
}
	
	