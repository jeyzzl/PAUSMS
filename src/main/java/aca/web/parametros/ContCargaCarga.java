package aca.web.parametros;

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

import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;

@Controller
public class ContCargaCarga {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	CargaDao cargaDao;	
	
	@RequestMapping("/parametros/carga/carga")
	public String usuariosCargaCarga(HttpServletRequest request, Model modelo){		
		
		String codigoPersonal 		= request.getParameter("codigoPersonal")==null?"-":request.getParameter("codigoPersonal");		
		String parametro 			= request.getParameter("parametro")==null?"-":request.getParameter("parametro");	
		String mensaje				= "";
		String sPeriodo 			= "xxxx";		
		
		List<Carga> lisCarga	 		= cargaDao.getListAll("ORDER BY SUBSTR(CASE CARGA_ID WHEN '2002-1' THEN '01021B' ELSE CARGA_ID END,1,4) DESC,SUBSTR(CARGA_ID,5,2)");
		
		
		modelo.addAttribute("codigoPersonal", codigoPersonal);
		modelo.addAttribute("parametro", parametro);
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("sPeriodo", sPeriodo);		
		modelo.addAttribute("lisCarga", lisCarga);
		
		return "parametros/carga/carga";
	}
	
	@RequestMapping("/parametros/carga/subir")
	public String usuariosCargaSubir(HttpServletRequest request, Model modelo){		
		
		String cargaId 			= (String)request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String mensaje 			= "-";
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();            
	    if (sesion!=null){
			if (cargaDao.existeReg(cargaId)) {
				sesion.setAttribute("cargaId", cargaId);
				mensaje = "La carga "+cargaId+" ha sido registrada como predeterminada";
			}
	    }	
		
		return "redirect:/parametros/carga/carga?CargaId="+cargaId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/parametros/carga/grabar")
	public String usuariosCargaEditar(HttpServletRequest request, Model modelo){		
		
		String cargaId 			= (String)request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String nombreCarga 		= (String)request.getParameter("Nombre")==null?"":request.getParameter("Nombre");
		String periodo 			= (String)request.getParameter("Periodo")==null?"":request.getParameter("Periodo");
		String fechaInicio 		= (String)request.getParameter("Inicio")==null?"":request.getParameter("Inicio");
		String fechaFinal		= (String)request.getParameter("Fin")==null?"":request.getParameter("Fin");
		String fechaExtra		= (String)request.getParameter("fExtra")==null?"":request.getParameter("fExtra");
		String finServicios		= (String)request.getParameter("FinServicios")==null?"":request.getParameter("FinServicios");
		String estado 			= (String)request.getParameter("Estado")==null?"":request.getParameter("Estado");
		String mensaje 			= "-";
		
		Carga carga = new Carga();
		carga.setNombreCarga(nombreCarga);
		carga.setPeriodo(periodo);
		carga.setFInicio(fechaInicio);
		carga.setFFinal(fechaFinal);
		carga.setFExtra(fechaExtra);
		carga.setFinServicios(finServicios);
		carga.setEstado(estado);
		if (cargaDao.existeReg(cargaId)) {
			if (cargaDao.updateReg(carga)) {
				mensaje = "Grabado...";
			}else {
				mensaje = "Error al grabar...";
			}
		}
		
		return "redirect:/parametros/carga/carga?CargaId="+cargaId+"&Mensaje="+mensaje;
	}	
}