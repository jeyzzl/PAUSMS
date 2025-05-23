package aca;

import java.util.HashMap;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import aca.notifica.spring.NotiProcesoPendiente;
import aca.notifica.spring.NotiProcesoPendienteDao;


@Controller
public class ContNotiProcesoPendiente {
	
	@Autowired	
	private NotiProcesoPendienteDao notiProcesoPendienteDao;

	
	@ResponseBody
	@RequestMapping(value="/agregaProcesoNotificacion",method = RequestMethod.GET)
	public String agregaNotificacion(HttpServletRequest request, Model modelo){	
		
		String codigoAlumno = (String) request.getParameter("codigoAlumno");
		String cargaId		= (String) request.getParameter("cargaId");
		String bloqueId		= (String) request.getParameter("bloqueId");
		String periodoId		= (String) request.getParameter("periodoId");
		
		NotiProcesoPendiente notiProcesoPendiente = new NotiProcesoPendiente();
		
		notiProcesoPendiente.setCodigoPersonal(codigoAlumno);
		notiProcesoPendiente.setTipo("1");
		notiProcesoPendiente.setDatos(cargaId+","+bloqueId+","+periodoId);
		
		
		String respuesta = "";
		
		if(notiProcesoPendienteDao.insertReg(notiProcesoPendiente)) {
			respuesta = "Borrado";
		}else {
			respuesta = "Error";
		}
		
		return respuesta;
	}
}