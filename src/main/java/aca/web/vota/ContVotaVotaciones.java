package aca.web.vota;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import aca.voto.spring.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumPersonalDao;

@Controller
public class ContVotaVotaciones {
	
	@Autowired
	private VotoEventoDao votoEventoDao;
	
	@Autowired
	private VotoCandidatoDao votoCandidatoDao; 
	
	@Autowired
	private AlumPersonalDao alumPersonalDao;

	@Autowired
	private VotoAlumnoDao votoAlumnoDao;
	
	@Autowired
	private VotoCandidatoAlumnoDao votoCandidatoAlumnoDao;
	
	
	@RequestMapping("/vota/votaciones/voto")
	public String votaVotacionesVoto(HttpServletRequest request, Model modelo){	
		
		String eventoId		= request.getParameter("EventoId")==null?"0":request.getParameter("EventoId");
		
		List<VotoEvento> listaEvento = votoEventoDao.getLista("ORDER BY EVENTO_ID DESC");		
		if(eventoId.equals("0")) {
			eventoId = listaEvento.get(0).getEventoId();
		}		
		List<VotoCandidato> listaCandidato 		= votoCandidatoDao.getListaEvento(eventoId, "");
		
		HashMap<String,List<VotoCandidatoAlumno>> mapa 	= new HashMap<String,List<VotoCandidatoAlumno>>();
		HashMap<String,String> mapaGanadores 			= votoCandidatoDao.mapaGanadores();
		HashMap<String,String> mapaCandidatos			= alumPersonalDao.mapaCandidatosEnEvento(eventoId);
		HashMap<String,String> mapaVotos 				= votoAlumnoDao.mapaVotos(eventoId);

		for(VotoCandidato candidato : listaCandidato) {
			List<VotoCandidatoAlumno> listaAlumno = votoCandidatoAlumnoDao.lisEventos(candidato.getEventoId(), candidato.getCandidatoId(), "");
			mapa.put(candidato.getEventoId()+candidato.getCandidatoId(), listaAlumno);
		}
		
		String candidatos = votoCandidatoDao.getCandidatosPorEvento(eventoId);
		HashMap<String,String> mapaEnMatriculas = alumPersonalDao.mapaEnMatriculas(candidatos);
		
		modelo.addAttribute("eventoId", eventoId);		
		modelo.addAttribute("listaEvento", listaEvento);
		modelo.addAttribute("listaCandidato", listaCandidato);
		modelo.addAttribute("mapa", mapa);
		modelo.addAttribute("mapaGanadores", mapaGanadores);
		modelo.addAttribute("mapaCandidatos", mapaCandidatos);
		modelo.addAttribute("mapaVotos", mapaVotos);
		modelo.addAttribute("mapaEnMatriculas", mapaEnMatriculas);
		
		return "vota/votaciones/voto";
	}	

	@RequestMapping("/vota/votaciones/cambiaEstado")
	public String votaVotacionesCambiaEstado(HttpServletRequest request, Model modelo){	
		
		String eventoId		= request.getParameter("EventoId")==null?"0":request.getParameter("EventoId");
		String candidatoId	= request.getParameter("CandidatoId")==null?"0":request.getParameter("CandidatoId");
		
		if(votoCandidatoDao.existeReg(eventoId, candidatoId)){
			VotoCandidato candidato = votoCandidatoDao.mapeaRegId(eventoId, candidatoId);
			if(candidato.getEstado().equals("A")) {
				candidato.setEstado("I");
			}else {
				candidato.setEstado("A");
			}
			votoCandidatoDao.updateReg(candidato);
		}
		
		return "redirect:/vota/votaciones/voto?EventoId="+eventoId;
	}	
	
	@RequestMapping("/vota/votaciones/cambiaGanador")
	public String votaVotacionesCambiaGanador(HttpServletRequest request, Model modelo){
		
		String eventoId		= request.getParameter("EventoId")==null?"0":request.getParameter("EventoId");
		String candidatoId 	= request.getParameter("CandidatoId")==null?"0":request.getParameter("CandidatoId");
		String ganador		= request.getParameter("Ganador")==null?"0":request.getParameter("Ganador");	

		if(votoCandidatoDao.existeReg(eventoId, candidatoId)){
			VotoCandidato candidato = votoCandidatoDao.mapeaRegId(eventoId, candidatoId);
			candidato.setGanador(ganador);
			votoCandidatoDao.updateReg(candidato);
		}
		
		return "redirect:/vota/votaciones/voto?EventoId="+eventoId;
	}	

}
