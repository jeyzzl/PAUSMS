package aca.web.credencialalum;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;
import aca.catalogo.spring.CatSalonDao;
import aca.matricula.spring.MatAlumnoDao;
import aca.matricula.spring.MatEvento;
import aca.matricula.spring.MatEventoDao;
import aca.mensaje.mensaje;

@Controller
public class ContCredencialAlumEventos {
 
	@Autowired
	private MatEventoDao matEventoDao;

	@Autowired
	private MatAlumnoDao matAlumnoDao;

	@Autowired
	private CargaDao cargaDao;

    @RequestMapping("/credencial_alum/evento/eventos")
	public String credencialAlumCredencialEventos(HttpServletRequest request, Model modelo){
		String estado = request.getParameter("Estado")==null?"A":request.getParameter("Estado");
		String mensaje = request.getParameter("Mensaje")==null?"":request.getParameter("Mensaje");

		List<MatEvento> lisEventos = matEventoDao.lisTodos(" ORDER BY EVENTO_ID");

		HashMap<String,String> mapAlumnosEnEvento = matEventoDao.mapAlumnosEnEvento();

		modelo.addAttribute("estado", estado);
		modelo.addAttribute("lisEventos", lisEventos);
		modelo.addAttribute("mapAlumnosEnEvento", mapAlumnosEnEvento);
		modelo.addAttribute("mensaje", mensaje);

		return "credencial_alum/evento/eventos";
	}

    @RequestMapping("/credencial_alum/evento/agregar")
	public String credencialAlumCredencialAgregarEvento(HttpServletRequest request, Model modelo){
		String eventoId = request.getParameter("EventoId")==null?"0":request.getParameter("EventoId");

		List<Carga> lisCargas = cargaDao.getListAllActivas("");

		MatEvento evento = new MatEvento();

        if(matEventoDao.existeReg(eventoId)){
           evento = matEventoDao.mapeaRegId(eventoId);
        }

		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("eventoId", eventoId);
		modelo.addAttribute("evento", evento);

		return "credencial_alum/evento/agregar";
	}

    @RequestMapping("/credencial_alum/evento/grabar")
	public String credencialAlumCredencialGrabarEvento(HttpServletRequest request, Model modelo){
		String eventoId = request.getParameter("EventoId")==null?"0":request.getParameter("EventoId");
		String cargaId = request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
        String nombre = request.getParameter("Nombre")==null?"0":request.getParameter("Nombre");
        String estado = request.getParameter("Estado")==null?"A":request.getParameter("Estado");
		String mensaje = "";

		MatEvento evento = new MatEvento();

        if(matEventoDao.existeReg(eventoId)){
			evento.setEventoId(eventoId);
			evento.setCargaId(cargaId);
            evento.setEventoNombre(nombre);
            evento.setEstado(estado);

			if(matEventoDao.updateReg(evento)){
				mensaje = "Updated";
			}else{
				mensaje = "Error updating";
			}
        }else{
            evento.setEventoId(matEventoDao.maximoReg());
			evento.setCargaId(cargaId);
			evento.setEventoNombre(nombre);
			evento.setEstado(estado);

			if(matEventoDao.insertReg(evento)){
				mensaje = "Saved";
			}else{
				mensaje = "Error saving";
			}
        }

		modelo.addAttribute("eventoId", eventoId);
		modelo.addAttribute("evento", evento);
		modelo.addAttribute("mensaje", mensaje);

		return "redirect:/credencial_alum/evento/eventos?Mensaje="+mensaje;
	}

	@RequestMapping("/credencial_alum/evento/eliminar")
	public String credencialAlumCredencialEliminarEvento(HttpServletRequest request, Model modelo){
		String eventoId = request.getParameter("EventoId")==null?"0":request.getParameter("EventoId");
		String mensaje = "";

        if(matEventoDao.existeReg(eventoId)){
			if(matEventoDao.deleteReg(eventoId)){
				mensaje = "Deleted";
			}else{
				mensaje = "Error deleting";
			}
        }else{
			mensaje = "Event not found";
        }

		modelo.addAttribute("mensaje", mensaje);

		return "redirect:/credencial_alum/evento/eventos?Mensaje="+mensaje;
	}

}