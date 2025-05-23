package aca.web.taller;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.bec.spring.BecInforme;
import aca.bec.spring.BecInformeAlumnoDao;
import aca.bec.spring.BecInformeDao;
import aca.financiero.spring.ContEjercicio;
import aca.financiero.spring.ContEjercicioDao;

@Controller
public class ContTallerCatInforme {
	
	@Autowired
	private BecInformeDao becInformeDao;
	
	@Autowired
	private BecInformeAlumnoDao becInformeAlumnoDao;
	
	@Autowired
	private ContEjercicioDao contEjercicioDao;
	
	
	@RequestMapping("/taller/catinforme/informe")
	public String tallerInformeInforme(HttpServletRequest request, Model modelo){
		
		String ejercicioId = request.getParameter("EjercicioId")==null?"0":request.getParameter("EjercicioId");	
		
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			if (ejercicioId.equals("0")) {
				ejercicioId = (String)sesion.getAttribute("ejercicioId");
			}else {
				sesion.setAttribute("ejercicioId", ejercicioId);
			}
		}
		
		List<ContEjercicio> lisEjercicios 		= contEjercicioDao.lisTodos("ORDER BY ID_EJERCICIO DESC");	
		List<BecInforme> lisInformes	 		= becInformeDao.getListEjercicio(ejercicioId, " ORDER BY NIVEL,ORDEN");
		HashMap<String,String> mapaInformes 	= becInformeAlumnoDao.mapaTotalInformes();
		
		modelo.addAttribute("ejercicioId", ejercicioId);
		modelo.addAttribute("lisEjercicios", lisEjercicios);
		modelo.addAttribute("lisInformes", lisInformes);
		modelo.addAttribute("mapaInformes", mapaInformes);
		
		return "taller/catinforme/informe";
	}
	
	
	@RequestMapping("/taller/catinforme/editar")
	public String tallerInformeEditar(HttpServletRequest request, Model modelo){
		
		String ejercicioId 		= "0";	
	 	String informeId		= request.getParameter("InformeId")==null?"0":request.getParameter("InformeId");	
	 	BecInforme becInforme 	= new BecInforme();
		
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion != null){
				ejercicioId = (String)sesion.getAttribute("ejercicioId");			
		}
		if (becInformeDao.existeReg(informeId)) {
			becInforme = becInformeDao.mapeaRegId(informeId);
		}
		
		modelo.addAttribute("ejercicioId", ejercicioId);
		modelo.addAttribute("becInforme", becInforme);
		
		return "taller/catinforme/editar";
	}
	
	@RequestMapping("/taller/catinforme/guardar")
	public String tallerInformeGuardar(HttpServletRequest request, Model modelo){
		
		String fechaHoy 		= aca.util.Fecha.getHoy();
		String informeId		= request.getParameter("InformeId")==null?"0":request.getParameter("InformeId");
	 	String informeNombre 	= request.getParameter("Nombre")==null?" ":request.getParameter("Nombre");	
	 	String fechaIni	 		= request.getParameter("fechaIni")==null?fechaHoy:request.getParameter("fechaIni");
	 	String fechaFin		 	= request.getParameter("fechaFin")==null?fechaHoy:request.getParameter("fechaFin");
	 	String nivel			= request.getParameter("nivel");
	 	String orden 			= request.getParameter("orden");
	 	String estado			= request.getParameter("estado");
	 	String ejercicioId		= "0";
	 	String mensaje 			= "-";
		
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			ejercicioId = (String)sesion.getAttribute("ejercicioId");			
		}
		BecInforme becInforme 	= new BecInforme();
		becInforme.setInformeId(informeId);
		becInforme.setInformeNombre(informeNombre);
		becInforme.setIdEjercicio(ejercicioId);
		becInforme.setFechaIni(fechaIni);		
		becInforme.setFechaFin(fechaFin);
		becInforme.setNivel(nivel);
		becInforme.setEstado(estado);
		becInforme.setOrden(orden);
		if (becInformeDao.existeReg(informeId)) {			 
			if (becInformeDao.updateReg(becInforme)) {
				mensaje = "Modificado...";
			}else {
				mensaje = " Error al modificar...";
			}
		}else {			
			informeId = becInformeDao.maximoReg();
			becInforme.setInformeId(informeId);
			if (becInformeDao.insertReg(becInforme)) {
				mensaje = "Grabado...";
			}else {
				mensaje = " Error al modificar...";
			}
		}
		
		return "redirect:/taller/catinforme/editar?InformeId="+informeId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/taller/catinforme/borrar")
	public String tallerInformeBorrar(HttpServletRequest request, Model modelo){		
			
	 	String informeId		= request.getParameter("InformeId")==null?"0":request.getParameter("InformeId");
	 	String mensaje 			= "-";
		
		if (becInformeDao.existeReg(informeId)) {
			if (becInformeDao.deleteReg(informeId) ) {
				mensaje = "Registro borrado...";
			}
		}		
		return "redirect:/taller/catinforme/informe?Mensaje="+mensaje;
	}
}