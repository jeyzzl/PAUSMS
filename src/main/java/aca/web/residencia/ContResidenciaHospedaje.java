package aca.web.residencia;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.residencia.spring.ResHospedaje;
import aca.residencia.spring.ResHospedajeDao;

@Controller
public class ContResidenciaHospedaje{
	
	@Autowired
	ResHospedajeDao resHospedajeDao;
	
	@RequestMapping("/residencia/hospedaje/listado")
	public String residenciaHospedajeListado(Model modelo){
		
		List<ResHospedaje> lisHospedaje	= resHospedajeDao.getListAll("");		
		modelo.addAttribute("lisHospedaje", lisHospedaje);
		
		return "residencia/hospedaje/listado";
	}

	@RequestMapping("/residencia/hospedaje/nuevo")
	public String residenciaHospedajeNuevo(HttpServletRequest request, Model modelo){
		
		String mensaje 	= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		String id 		= request.getParameter("Id")==null?"0":request.getParameter("Id");
		
		ResHospedaje hospedaje = new ResHospedaje();
		
		if(resHospedajeDao.existeReg(id)) {
			hospedaje = resHospedajeDao.mapeaRegId(id);
		}else {
			hospedaje.setId(resHospedajeDao.maximoReg());
		}
		
		modelo.addAttribute("hospedaje", hospedaje);
		modelo.addAttribute("mensaje", mensaje);
		
		return "residencia/hospedaje/nuevo";
	}
	
	@RequestMapping("/residencia/hospedaje/grabar")
	public String residenciaHospedajeGrabar(HttpServletRequest request, Model modelo){
		
		String id			= request.getParameter("Id")==null?"0":request.getParameter("Id");
		String nombre		= request.getParameter("Nombre")==null?"-":request.getParameter("Nombre");
		String apellidos	= request.getParameter("Apellidos")==null?"-":request.getParameter("Apellidos");
		String nomina		= request.getParameter("Nomina")==null?"0":request.getParameter("Nomina");
		String direccion	= request.getParameter("Direccion")==null?"-":request.getParameter("Direccion");
		String telefono		= request.getParameter("Telefono")==null?"-":request.getParameter("Telefono");
		String cupoH		= request.getParameter("CupoH")==null?"0":request.getParameter("CupoH");
		String cupoM		= request.getParameter("CupoM")==null?"0":request.getParameter("CupoM");
		String cuartos		= request.getParameter("Cuartos")==null?"0":request.getParameter("Cuartos");
		String estado		= request.getParameter("Estado")==null?"-":request.getParameter("Estado");
		String mensaje		= "0";
		
		ResHospedaje hospedaje = new ResHospedaje();
		
		hospedaje.setId(id);
		hospedaje.setNombre(nombre);
		hospedaje.setApellidos(apellidos);
		hospedaje.setNomina(nomina);
		hospedaje.setDireccion(direccion);
		hospedaje.setTelefono(telefono);
		hospedaje.setCupoHombres(cupoH);
		hospedaje.setCupoMujeres(cupoM);
		hospedaje.setCuartos(cuartos);
		hospedaje.setEstado(estado);
		
		if(resHospedajeDao.existeReg(id)) {
			if(resHospedajeDao.updateReg(hospedaje)) {
				mensaje = "1";
			}
		}else {
			if(resHospedajeDao.insertReg(hospedaje)) {
				mensaje = "1";
			}
		}
		
		modelo.addAttribute("hospedaje", hospedaje);
		modelo.addAttribute("mensaje", mensaje);
		
		return "residencia/hospedaje/nuevo";
	}
	
}