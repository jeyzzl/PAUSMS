package aca.web.patrocinador;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.catalogo.spring.CatPatrocinador;
import aca.catalogo.spring.CatPatrocinadorDao;

@Controller
public class ContPatrocinadorPatrocinadores {

	@Autowired
	private CatPatrocinadorDao catPatrocinadorDao;

	@RequestMapping("/patrocinador/patrocinadores/patrocinador")
	public String patrocinadorPatrocinadoresPatrocinador(HttpServletRequest request, Model model) {

		List<CatPatrocinador> lisPatrocinadores = (List<CatPatrocinador>) catPatrocinadorDao
				.lisTodos(" ORDER BY PATROCINADOR_ID");
		model.addAttribute("lisPatrocinadores", lisPatrocinadores);

		return "patrocinador/patrocinadores/patrocinador";
	}

	@RequestMapping("/patrocinador/patrocinadores/editarPatrocinador")
	public String patrocinadorPatrocinadoresEditarPatrocinador(HttpServletRequest request, Model model) {

		String patrocinadorId = request.getParameter("PatrocinadorId") == null ? "0" : request.getParameter("PatrocinadorId");
		CatPatrocinador patrocinador = new CatPatrocinador();
		if (catPatrocinadorDao.existeReg(patrocinadorId)) {
			patrocinador = catPatrocinadorDao.mapeaRegId(patrocinadorId);
		} else {
			patrocinador.setPatrocinadorId(catPatrocinadorDao.maximoReg());
		}
		model.addAttribute("patrocinador", patrocinador);
		return "patrocinador/patrocinadores/editarPatrocinador";
	}

	@RequestMapping("/patrocinador/patrocinadores/grabarPatrocinador")
	public String patrocinadorPatrocinadoresGrabarPatrocinador(HttpServletRequest request, Model model) {

		String patrocinadorId = request.getParameter("PatrocinadorId") == null ? "0" : request.getParameter("PatrocinadorId");
		String nombrePatrocinador = request.getParameter("NombrePatrocinador") == null ? "-" : request.getParameter("NombrePatrocinador");
		String detallesPatrocinador = request.getParameter("DetallesPatrocinador") == null ? "" : request.getParameter("DetallesPatrocinador");
		String direccionPatrocinador = request.getParameter("DireccionPatrocinador") == null ? "" : request.getParameter("DireccionPatrocinador");
		String telefonoPatrocinador = request.getParameter("TelefonoPatrocinador") == null ? "" : request.getParameter("TelefonoPatrocinador");
		String emailPatrocinador = request.getParameter("EmailPatrocinador") == null ? "" : request.getParameter("EmailPatrocinador");
		String tipoPatrocinador = request.getParameter("TipoPatrocinador") == null ? "C" : request.getParameter("TipoPatrocinador");
		String mensaje = "0";
		
		CatPatrocinador patrocinador = new CatPatrocinador();
		patrocinador.setPatrocinadorId(patrocinadorId);
		patrocinador.setNombrePatrocinador(nombrePatrocinador);
		patrocinador.setDetalles(detallesPatrocinador);
		patrocinador.setDireccion(direccionPatrocinador);
		patrocinador.setTelefono(telefonoPatrocinador);
		patrocinador.setEmail(emailPatrocinador);
		patrocinador.setTipo(tipoPatrocinador);
		
		if (catPatrocinadorDao.existeReg(patrocinadorId)) {
			if (catPatrocinadorDao.updateReg(patrocinador)) {
				mensaje = "Updated";
			} else {
				mensaje = "Error Updating";
			}
		} else {
			patrocinador.setPatrocinadorId(catPatrocinadorDao.maximoReg());
			if (catPatrocinadorDao.insertReg(patrocinador)) {
				mensaje = "Saved";
			} else {
				mensaje = "Error Saving";
			}
		}
		return "redirect:/patrocinador/patrocinadores/editarPatrocinador?PatrocinadorId="
				+ patrocinador.getPatrocinadorId() + "&Mensaje=" + mensaje;
	}

	@RequestMapping("/patrocinador/patrocinadores/borrarPatrocinador")
	public String patrocinadorPatrocinadoresBorrarPatrocinador(HttpServletRequest request, Model model) {

		String patrocinadorId = request.getParameter("PatrocinadorId") == null ? "0" : request.getParameter("PatrocinadorId");
		if (catPatrocinadorDao.existeReg(patrocinadorId)) {
			catPatrocinadorDao.deleteReg(patrocinadorId);
		}

		return "redirect:/patrocinador/patrocinadores/patrocinador";
	}

}