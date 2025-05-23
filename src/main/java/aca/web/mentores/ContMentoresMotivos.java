package aca.web.mentores;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.mentores.spring.MentMotivo;
import aca.mentores.spring.MentMotivoDao;

@Controller
public class ContMentoresMotivos {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	MentMotivoDao mentMotivoDao;
	
	@RequestMapping("/mentores/motivos/motivos")
	public String mentoresMotivosMotivos(HttpServletRequest request, Model modelo){
		
		List<MentMotivo> lisAll	= mentMotivoDao.getListAll(" ORDER BY MOTIVO_NOMBRE");
		
		modelo.addAttribute("lisAll", lisAll);
				
		return "mentores/motivos/motivos";
	}
	
	@RequestMapping("/mentores/motivos/editarMotivo")
	public String mentoresMotivosEditarMotivo(HttpServletRequest request, Model model){
		
		String motivoId 		= request.getParameter("MotivoId")==null?"0":request.getParameter("MotivoId");
		MentMotivo motivo		= new MentMotivo();
		
		if (mentMotivoDao.existeReg(motivoId)) {
			motivo = mentMotivoDao.mapeaRegId(motivoId);
		}
		
		model.addAttribute("motivo", motivo);
		
		return "mentores/motivos/editarMotivo";
	}
	
	@RequestMapping("/mentores/motivos/grabarMotivos")
	public String mentoresMotivosGrabarMotivos(HttpServletRequest request, Model model){				
		
		String motivoId 	= request.getParameter("MotivoId")==null?"0":request.getParameter("MotivoId");
		String motivoNombre	= request.getParameter("MotivoNombre")==null?"-":request.getParameter("MotivoNombre");
		String comentario	= request.getParameter("Comentario")==null?"-":request.getParameter("Comentario");
		String mensaje 		= "-";
		
		MentMotivo motivo	= new MentMotivo();
		motivo.setMotivoNombre(motivoNombre);
		motivo.setComentario(comentario);
		
		if (mentMotivoDao.existeReg(motivoId)) {
			motivo.setMotivoId(motivoId);
		}else {
			motivo.setMotivoId(mentMotivoDao.maximoReg());
		}
		
		// Grabar
		if (mentMotivoDao.existeReg(motivoId)) {
			if (mentMotivoDao.updateReg(motivo)) {
				mensaje = "1";
			}else {
				mensaje = "2";
			}			
		}else {
			if (mentMotivoDao.insertReg(motivo)) {
				mensaje = "1";
			}else {
				mensaje = "2";
			}			
		}
		
		return "redirect:/mentores/motivos/editarMotivo?MotivoId="+motivo.getMotivoId()+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/mentores/motivos/borrarMotivos")
	public String mentoresMotivosBorrarMotivos(HttpServletRequest request, Model model){
		
		String motivoId	= request.getParameter("MotivoId")==null?"0":request.getParameter("MotivoId");
		if (mentMotivoDao.existeReg(motivoId)) {
			mentMotivoDao.deleteReg(motivoId);
		}
		
		return "redirect:/mentores/motivos/motivos";
	}
}