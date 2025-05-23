package aca.web.trabajo;


import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import aca.trabajo.spring.TrabInforme;
import aca.trabajo.spring.TrabInformeDao;
import aca.trabajo.spring.TrabPeriodo;
import aca.trabajo.spring.TrabPeriodoDao;

@Controller
public class ContTrabajoInforme {

	@Autowired
	private TrabInformeDao trabInformeDao;
	
	@Autowired
	private TrabPeriodoDao trabPeriodoDao;
	
	@RequestMapping("/trabajo/informe/informes")
	public String trabajoInformeInformes(HttpServletRequest request, Model model) {

		List<TrabInforme> lisInformes = (List<TrabInforme>) trabInformeDao.lisTodos(" ORDER BY INFORME_ID");
		List<TrabPeriodo> lisPeriodos = trabPeriodoDao.lisTodos(" ORDER BY PERIODO_ID");
		HashMap<String, String> mapaPeriodoNombre		= trabPeriodoDao.mapaPeriodoNombre();
		HashMap<String, String> mapaHorasRegistradas	= trabInformeDao.horasRegistradasPorReporte();
		
		model.addAttribute("lisInformes", lisInformes);
		model.addAttribute("lisPeriodos", lisPeriodos);
		model.addAttribute("mapaPeriodoNombre", mapaPeriodoNombre);
		model.addAttribute("mapaHorasRegistradas", mapaHorasRegistradas);

		return "trabajo/informe/informes";
	}
	
	@RequestMapping("/trabajo/informe/editarInforme")
	public String trabajoInformesEditarInforme(HttpServletRequest request, Model model) {
		String informeId = request.getParameter("informeId") == null ? "0" : request.getParameter("informeId");
		
		TrabInforme informe = new TrabInforme();
		List<TrabPeriodo> lisPeriodos = trabPeriodoDao.lisTodos(" ORDER BY PERIODO_ID");
		if (trabInformeDao.existeReg(informeId)) {
			informe = trabInformeDao.mapeaRegId(informeId);
		} else {
			informe.setInformeId(trabInformeDao.maximoReg());
		}
		
		model.addAttribute("informe", informe);
		model.addAttribute("lisPeriodos", lisPeriodos);
		
		return "trabajo/informe/editarInforme";
	}
	
	@RequestMapping("/trabajo/informe/grabarInforme")
	public String trabajoInformesGrabarInforme(HttpServletRequest request, Model model) {

		String informeId 		= request.getParameter("informeId") == null ? "0" : request.getParameter("informeId");
		String nombre 			= request.getParameter("Nombre") == null ? "-": request.getParameter("Nombre");
		String estado 			= request.getParameter("Estado") == null ? "-": request.getParameter("Estado");
		String periodoId		= request.getParameter("PeriodoId") == null ? "0": request.getParameter("PeriodoId");
		String mensaje 			= "0";
		
//		System.out.println(deptId);
		
		TrabInforme informe = new TrabInforme();
		informe.setInformeId(informeId);
		informe.setNombreInforme(nombre);
		informe.setEstado(estado);
		informe.setPeriodoId(periodoId);
				
		if (trabInformeDao.existeReg(informeId)) {
			if (trabInformeDao.updateReg(informe)) {
				mensaje = "Updated";
			} else {
				mensaje = "Error Updating";
			}
		} else {
			informe.setInformeId(trabInformeDao.maximoReg());
			if (trabInformeDao.insertReg(informe)) {
				mensaje = "Saved";
			} else {
				mensaje = "Error Saving";
			}
		}
		return "redirect:/trabajo/informe/editarInforme?informeId=" + informe.getInformeId() + "&Mensaje=" + mensaje;
	}
	
	@RequestMapping("/trabajo/informe/borrarInforme")
	public String trabajoInformesBorrarInforme(HttpServletRequest request, Model model) {

		String informeId = request.getParameter("informeId") == null ? "0" : request.getParameter("informeId");
		if (trabInformeDao.existeReg(informeId)) {
			trabInformeDao.deleteReg(informeId);
		}

		return "redirect:/trabajo/informe/informes";
	}
}
