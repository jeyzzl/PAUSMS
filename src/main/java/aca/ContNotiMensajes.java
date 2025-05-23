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

import aca.notifica.spring.NotiMensajes;
import aca.notifica.spring.NotiMensajesDao;


@Controller
public class ContNotiMensajes {
	
	@Autowired	
	private NotiMensajesDao notiMensajesDao;

	
	@ResponseBody
	@RequestMapping(value="/notificacionList",method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public List<NotiMensajes> notificacionList(HttpServletRequest request, Model modelo){	
		
		String codigoPersonal = (String) request.getSession().getAttribute("codigoPersonal");
		
		List<NotiMensajes> lisNotificaciones			= notiMensajesDao.lisPorPersona(codigoPersonal, "ORDER BY SILENCIADO, VISTO, NOTI_MENSAJES.FECHA");
		
		modelo.addAttribute("lisNotificaciones", lisNotificaciones);
		
		return lisNotificaciones;
	}
	
	@ResponseBody
	@RequestMapping(value="/borraNotificacion",method = RequestMethod.GET)
	public String borraNotificacion(HttpServletRequest request, Model modelo){	
		
		String codigoPersonal = (String) request.getSession().getAttribute("codigoPersonal");
		String id = (String) request.getParameter("id");
		String respuesta = "";
		
		if(notiMensajesDao.deleteReg(id, codigoPersonal)) {
			respuesta = "Borrado";
		}else {
			respuesta = "Error";
		}
		
		return respuesta;
	}
}