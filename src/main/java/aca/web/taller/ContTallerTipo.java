package aca.web.taller;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.bec.spring.BecAccesoDao;
import aca.bec.spring.BecAcuerdoDao;
import aca.bec.spring.BecTipo;
import aca.bec.spring.BecTipoCarrera;
import aca.bec.spring.BecTipoCarreraDao;
import aca.bec.spring.BecTipoDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatNivel;
import aca.catalogo.spring.CatNivelDao;
import aca.catalogo.spring.CatTipoAlumno;
import aca.catalogo.spring.CatTipoAlumnoDao;
import aca.financiero.spring.ContCcosto;
import aca.financiero.spring.ContCcostoDao;
import aca.financiero.spring.ContEjercicio;
import aca.financiero.spring.ContEjercicioDao;
import aca.financiero.spring.SaldosAlumnosDao;
import aca.vista.spring.UsuariosDao;

@Controller
public class ContTallerTipo {
	
	@Autowired
	private ContEjercicioDao contEjercicioDao;
	
	@Autowired
	private ContCcostoDao contCcostoDao;
	
	@Autowired
	private BecTipoDao becTipoDao;
	
	@Autowired
	private CatTipoAlumnoDao catTipoAlumnoDao;
	
	@Autowired
	private BecAccesoDao becAccesoDao;
	
	@Autowired
	private BecAcuerdoDao becAcuerdoDao;
	
	@Autowired
	private UsuariosDao usuariosDao;
	
	@Autowired
	private SaldosAlumnosDao saldoAlumnosDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	BecTipoCarreraDao becTipoCarreraDao;	
	
	@Autowired
	CatFacultadDao catFacultadDao;
	
	@Autowired
	CatNivelDao catNivelDao;	
	
	
	@RequestMapping("/taller/tipo/becas")
	public String tallerTipoBecas(HttpServletRequest request, Model modelo){
		String ejercicioId = request.getParameter("EjercicioId")==null?"0":request.getParameter("EjercicioId");
		
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			if (ejercicioId.equals("0")) {
				ejercicioId = (String)sesion.getAttribute("ejercicioId");
			}else {
				sesion.setAttribute("ejercicioId", ejercicioId);
			}
		}
		
		List<ContEjercicio> lisEjercicios 			= contEjercicioDao.lisTodos(" ORDER BY ID_EJERCICIO DESC");
		List<BecTipo> lisTipos						= becTipoDao.listPorEjercicio(ejercicioId, " ORDER BY 2");
		HashMap<String, String> mapaAcuerdos 		= becAcuerdoDao.mapaTotalAcuerdos(ejercicioId);
		HashMap<String, String> mapaPresupuestos 	= saldoAlumnosDao.mapaPresupuestosPorMeses(ejercicioId);
		HashMap<String, String> mapaCarreras	 	= becTipoCarreraDao.mapaCarrerasPorTipo(ejercicioId);
		
		modelo.addAttribute("lisEjercicios", lisEjercicios);	
		modelo.addAttribute("lisTipos", lisTipos);
		modelo.addAttribute("mapaAcuerdos",mapaAcuerdos);
		modelo.addAttribute("mapaPresupuestos",mapaPresupuestos);
		modelo.addAttribute("mapaCarreras",mapaCarreras);
		
		return "taller/tipo/becas";
	}
	
	@RequestMapping("/taller/tipo/editar")
	public String tallerTipoAgregar(HttpServletRequest request, Model modelo){
		String ejercicioId 		= request.getParameter("EjercicioId")==null?"0":request.getParameter("EjercicioId");
		String tipo   			= request.getParameter("Tipo")==null?"0":request.getParameter("Tipo");
		String codigoUsuario	= "-";
		String usuarioNombre	= "-";
			
		HttpSession sesion = ((HttpServletRequest)request).getSession();
		if (sesion != null){
			codigoUsuario 		= (String)sesion.getAttribute("codigoPersonal");
			usuarioNombre 		= usuariosDao.getNombreUsuario(codigoUsuario, "NOMBRE");
			if (ejercicioId.equals("0")) {
				ejercicioId = (String)sesion.getAttribute("ejercicioId");
			}else {
				sesion.setAttribute("ejercicioId", ejercicioId);
			}
		}
	
		BecTipo becTipo = new BecTipo();	
		if (becTipoDao.existeReg(ejercicioId, tipo)) {
			becTipo = becTipoDao.mapeaRegId(ejercicioId, tipo);			 
		}
		
		modelo.addAttribute("usuarioNombre", usuarioNombre);
		modelo.addAttribute("becTipo", becTipo);		
	
		return "taller/tipo/editar";
	} 
	
	@RequestMapping("/taller/tipo/grabar")
	public String tallerTipoGrabar(HttpServletRequest request, Model model){
		String ejercicioId  	= "0";
		String tipo 			= request.getParameter("Tipo")==null?"0":request.getParameter("Tipo");
		String nombre 			= request.getParameter("Nombre")==null?"-":request.getParameter("Nombre");
		String departamentos	= request.getParameter("Departamentos")==null?"0":request.getParameter("Departamentos");
		String meses 			= request.getParameter("Meses")==null?"0":request.getParameter("Meses");
		String cuenta 			= request.getParameter("Cuenta")==null?"0":request.getParameter("Cuenta");
		String porcentaje 		= request.getParameter("Porcentaje")==null?"-":request.getParameter("Porcentaje");
		String horas 			= request.getParameter("Horas")==null?"0":request.getParameter("Horas");
		String horasPrepa 		= request.getParameter("HorasPrepa")==null?"0":request.getParameter("HorasPrepa");
		String acuerdo 			= request.getParameter("Acuerdo")==null?"0":request.getParameter("Acuerdo");
		String importe 			= request.getParameter("Importe")==null?"0":request.getParameter("Importe");
		String tipoAlumno 		= request.getParameter("TipoAlumno")==null?"0":request.getParameter("TipoAlumno");
		String diezmo 			= request.getParameter("Diezmo")==null?"-":request.getParameter("Diezmo");
		String estado 			= request.getParameter("Estado")==null?"0":request.getParameter("Estado");
		String acumula 			= request.getParameter("Acumula")==null?"0":request.getParameter("Acumula");
		String colporta			= request.getParameter("Colporta")==null?"0":request.getParameter("Colporta");
		String aplicaAdicional	= "N";
		String maximo 			= request.getParameter("Maximo")==null?"0":request.getParameter("Maximo");
		String limite 			= request.getParameter("Limite")==null?"0":request.getParameter("Limite");
		String cuentaSunplus	= request.getParameter("CuentaSunplus")==null?"0":request.getParameter("CuentaSunplus");
		String flag				= request.getParameter("Flag")==null?"0":request.getParameter("Flag");
		String mostrar			= request.getParameter("Flag")==null?"0":request.getParameter("Mostrar");
		String descripcion 		= request.getParameter("Descripcion")==null?"0":request.getParameter("Descripcion");

		String mensaje			= "-";	
		
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			ejercicioId = (String)sesion.getAttribute("ejercicioId");
		}	
		
		BecTipo becTipo 	= new BecTipo();
		
		becTipo.setIdEjercicio(ejercicioId);
		becTipo.setTipo(tipo);
		becTipo.setNombre(nombre);
		becTipo.setDepartamentos(departamentos);
		becTipo.setMeses(meses);
		becTipo.setCuenta(cuenta);
		becTipo.setPorcentaje(porcentaje);
		becTipo.setHoras(horas);
		becTipo.setHorasPrepa(horasPrepa);
		becTipo.setAcuerdo(acuerdo);
		becTipo.setImporte(importe);		
		becTipo.setTipoAlumno(tipoAlumno);
		becTipo.setDiezmo(diezmo);
		becTipo.setEstado(estado);
		becTipo.setAcumula(acumula);
		becTipo.setColporta(colporta);
		becTipo.setAplicaAdicional(aplicaAdicional);
		becTipo.setMaximo(maximo);
		becTipo.setLimite(limite);
		becTipo.setCuentaSunplus(cuentaSunplus);
		becTipo.setFlag(flag);
		becTipo.setMostrar(mostrar);
		becTipo.setDescripcion(descripcion);
		
		// Grabar			
		if (becTipoDao.existeReg(ejercicioId, tipo)){
			if (becTipoDao.updateReg(becTipo)) {
				mensaje = "1";
			}else {
				mensaje = "2";
			}
		}else{
			tipo = becTipoDao.maximoReg(ejercicioId);
			becTipo.setTipo(tipo);
			if (becTipoDao.insertReg(becTipo)) {
				mensaje = "1";
			}else {
				mensaje = "2";
			}
		}	
		
		return "redirect:/taller/tipo/editar?Ejercicio="+becTipo.getIdEjercicio()+"&Tipo="+tipo+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/taller/tipo/borrar")
	public String tallerTipoBorrar(HttpServletRequest request, Model model){
		String ejercicioId  	= request.getParameter("EjercicioId")==null?"0":request.getParameter("EjercicioId");
		String tipo 			= request.getParameter("Tipo")==null?"0":request.getParameter("Tipo");		
		String mensaje			= "-";
					
		if (becTipoDao.existeReg(ejercicioId, tipo)){
			if (becTipoDao.deleteReg(ejercicioId, tipo)) {
				mensaje = "Borrado..";
			}else {
				mensaje = "Error al borrar...";
			}
		}
		
		return "redirect:/taller/tipo/becas?EjercicioId="+ejercicioId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/taller/tipo/editartipo")
	public String tallerTipoEditarTipo(HttpServletRequest request, Model modelo){
		
		String ejercicioId = request.getParameter("EjercicioId")==null?"0":request.getParameter("EjercicioId");
		String tipo		   = request.getParameter("Tipo")==null?"0":request.getParameter("Tipo");
		String mensaje	   = "0";
		
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			if (ejercicioId.equals("0")) {
				ejercicioId = (String)sesion.getAttribute("ejercicioId");
			}else {
				sesion.setAttribute("ejercicioId", ejercicioId);
			}
		}
		
		List<CatTipoAlumno> lisTipos				= catTipoAlumnoDao.getListAll("ORDER BY TIPO_ID ");
		HashMap<String, String> mapaAccesos 		= becAccesoDao.getAccesoDepto(ejercicioId);
		
		BecTipo becTipo 	= new BecTipo();		
		if (becTipoDao.existeReg(ejercicioId, tipo)) {
			becTipo = becTipoDao.mapeaRegId(ejercicioId, tipo);
		}
		
		modelo.addAttribute("becTipo", becTipo);
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("lisTipos", lisTipos);
		modelo.addAttribute("mapaAccesos", mapaAccesos);		
		
		return "taller/tipo/editartipo";
	}	
	
	@RequestMapping("/taller/tipo/grabartipo")
	public String tallerTipoGrabarTipo(HttpServletRequest request, Model modelo){
		
		String ejercicioId = request.getParameter("EjercicioId")==null?"0":request.getParameter("EjercicioId");
		String tipo		   = request.getParameter("Tipo")==null?"0":request.getParameter("Tipo");
		String mensaje	   = "-";
		
		List<CatTipoAlumno> lisTipos				= catTipoAlumnoDao.getListAll("ORDER BY TIPO_ID ");		
 		String tipoAlumno = "";
 		for(CatTipoAlumno tipoalum : lisTipos){
 			if (request.getParameter(tipoalum.getTipoId()) != null){
 				tipoAlumno += "-"+tipoalum.getTipoId();
 			}
 		}	
 		
 		if(becTipoDao.existeReg( ejercicioId, tipo)==true){
 			if(becTipoDao.updateTipoAlumno(tipoAlumno, ejercicioId, tipo)){
 				mensaje = "Grabado...";
 			}else {
 				mensaje = "Error al grabar...";
 			}
 		}	
		
		return "redirect:/taller/tipo/editartipo?EjercicioId="+ejercicioId+"&Tipo="+tipo+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/taller/tipo/editarccosto")
	public String tallerTipoEditarCcosto(HttpServletRequest request, Model modelo){
		
		String ejercicioId 		= request.getParameter("EjercicioId")==null?"0":request.getParameter("EjercicioId");
		String tipo		   		= request.getParameter("Tipo")==null?"0":request.getParameter("Tipo");	
		
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			if (ejercicioId.equals("0")) {
				ejercicioId = (String)sesion.getAttribute("ejercicioId");
			}else {
				sesion.setAttribute("ejercicioId", ejercicioId);
			}
		}
		
		List<ContCcosto> lisDeptos					= contCcostoDao.listCentrosCosto(ejercicioId,"S","ORDER BY ID_CCOSTO");
		//HashMap<String, String> mapaAccesos			= becAccesoDao.getAccesoDepto(ejercicioId);	
		
		BecTipo becTipo 	= new BecTipo();
		if (becTipoDao.existeReg(ejercicioId, tipo)) {
			becTipo = becTipoDao.mapeaRegId(ejercicioId, tipo);
		}
		
		modelo.addAttribute("becTipo", becTipo);		
		modelo.addAttribute("lisDeptos", lisDeptos);
		//modelo.addAttribute("mapaAccesos", mapaAccesos);
		
		return "taller/tipo/editarccosto";
	}
	
	@RequestMapping("/taller/tipo/editarcarreras")
	public String tallerTipoEditarCarreras(HttpServletRequest request, Model modelo){
		
		String ejercicioId 		= request.getParameter("EjercicioId")==null?"0":request.getParameter("EjercicioId");
		String tipo		   		= request.getParameter("Tipo")==null?"0":request.getParameter("Tipo");
		String facultadId 		= request.getParameter("FacultadId")==null?"0":request.getParameter("FacultadId");
		String tipoNombre 		= becTipoDao.getTipoNombre(tipo, ejercicioId);
		
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			if (ejercicioId.equals("0")) {
				ejercicioId = (String)sesion.getAttribute("ejercicioId");
			}else {
				sesion.setAttribute("ejercicioId", ejercicioId);
			}
		}
		
		List<CatFacultad> lisFacultades			= catFacultadDao.getListAll(" ORDER BY NOMBRE_FACULTAD");
		List<CatCarrera> lisCarreras			= new ArrayList<CatCarrera>();
		if (facultadId.equals("99")){
			lisCarreras 	= catCarreraDao.lisCarrerasVigentes(" ORDER BY FACULTAD_ID, NIVEL_ID, NOMBRE_CARRERA");
		}else if (facultadId.equals("0")){
			lisCarreras 	= catCarreraDao.lisEnBecas(ejercicioId, tipo, "ORDER BY FACULTAD_ID, NIVEL_ID, NOMBRE_CARRERA");
		}else {
			lisCarreras 	= catCarreraDao.getLista(facultadId," ORDER BY FACULTAD_ID, NIVEL_ID, NOMBRE_CARRERA");
		}
		
		List<String> lisGrabadas					= becTipoCarreraDao.lisCarreras(ejercicioId, tipo, " ORDER BY CARRERA_ID");
		HashMap<String,CatFacultad> mapaFacultades 	= catFacultadDao.getMapFacultad("");
		HashMap<String,CatNivel> mapaNiveles 		= catNivelDao.getMapAll("");
		
		modelo.addAttribute("tipoNombre", tipoNombre);
		modelo.addAttribute("lisFacultades", lisFacultades);
		modelo.addAttribute("lisCarreras", lisCarreras);
		modelo.addAttribute("lisGrabadas", lisGrabadas);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaNiveles", mapaNiveles);
		
		return "taller/tipo/editarcarreras";
	}
	
	@RequestMapping("/taller/tipo/grabarcarreras")
	public String tallerTipoGrabarCarreras(HttpServletRequest request, Model modelo){
		
		String ejercicioId 		= request.getParameter("EjercicioId")==null?"0":request.getParameter("EjercicioId");
		String tipo		   		= request.getParameter("Tipo")==null?"0":request.getParameter("Tipo");
		String facultadId 		= request.getParameter("FacultadId")==null?"0":request.getParameter("FacultadId");
		//String tipoNombre 		= becTipoDao.getTipoNombre(tipo, ejercicioId);
		String mensaje	   		= "-";		
		
		List<CatCarrera> lisCarreras			= new ArrayList<CatCarrera>();
		if (facultadId.equals("0")){
			lisCarreras 	= catCarreraDao.getListAll(" ORDER BY FACULTAD_ID, NIVEL_ID, NOMBRE_CARRERA");
		}else {
			lisCarreras 	= catCarreraDao.getLista(facultadId," ORDER BY FACULTAD_ID, NIVEL_ID, NOMBRE_CARRERA");
		}
		
		BecTipoCarrera becTipoCarrera = new BecTipoCarrera();
		becTipoCarrera.setIdEjercicio(ejercicioId);
		becTipoCarrera.setTipo(tipo);
		for(CatCarrera carrera : lisCarreras){
			if (request.getParameter(carrera.getCarreraId()) != null){
				becTipoCarrera.setCarreraId(carrera.getCarreraId());
				if (becTipoCarreraDao.existeReg(ejercicioId, tipo, carrera.getCarreraId())==false){
					becTipoCarreraDao.insertReg(becTipoCarrera);
				}
			}else {
				becTipoCarrera.setCarreraId(carrera.getCarreraId());
				if (becTipoCarreraDao.existeReg(ejercicioId, tipo, carrera.getCarreraId())==true){
					becTipoCarreraDao.deleteReg(ejercicioId, tipo, carrera.getCarreraId());
				}
			}
		}
		
		return "redirect:/taller/tipo/editarcarreras?EjercicioId="+ejercicioId+"&Tipo="+tipo+"&FacultadId="+facultadId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/taller/tipo/grabarccosto")
	public String tallerTipoGrabarCcosto(HttpServletRequest request, Model modelo){
		
		String ejercicioId = request.getParameter("EjercicioId")==null?"0":request.getParameter("EjercicioId");
		String tipo		   = request.getParameter("Tipo")==null?"0":request.getParameter("Tipo");
		String mensaje	   = "-";
		
		List<ContCcosto> lisDeptos		= contCcostoDao.listCentrosCosto(ejercicioId,"S","ORDER BY ID_CCOSTO");
		
		String departamentos = "";
		for(ContCcosto depto : lisDeptos){
			if (request.getParameter(depto.getIdCcosto()) != null){
				departamentos += "-"+depto.getIdCcosto();
			}
		}
 		
 		if(becTipoDao.existeReg( ejercicioId, tipo)==true){
 			if(becTipoDao.updateDepartamentos(departamentos, ejercicioId, tipo)){
 				mensaje = "Grabado...";
 			}else {
 				mensaje = "Error al grabar...";
 			}
 		}	
		
		return "redirect:/taller/tipo/editarccosto?EjercicioId="+ejercicioId+"&Tipo="+tipo+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/taller/tipo/editarmeses")
	public String tallerTipoEditarMeses(HttpServletRequest request, Model modelo){
		
		String ejercicioId 		= request.getParameter("EjercicioId")==null?"0":request.getParameter("EjercicioId");
		String tipo		   		= request.getParameter("Tipo")==null?"0":request.getParameter("Tipo");	
		
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			if (ejercicioId.equals("0")) {
				ejercicioId = (String)sesion.getAttribute("ejercicioId");
			}else {
				sesion.setAttribute("ejercicioId", ejercicioId);
			}
		}
		
		List<ContCcosto> lisDeptos					= contCcostoDao.listCentrosCosto(ejercicioId,"S","ORDER BY ID_CCOSTO");
		//HashMap<String, String> mapaAccesos			= becAccesoDao.getAccesoDepto(ejercicioId);	
		
		BecTipo becTipo 	= new BecTipo();
		if (becTipoDao.existeReg(ejercicioId, tipo)) {
			becTipo = becTipoDao.mapeaRegId(ejercicioId, tipo);
		}
		
		modelo.addAttribute("becTipo", becTipo);		
		modelo.addAttribute("lisDeptos", lisDeptos);
		//modelo.addAttribute("mapaAccesos", mapaAccesos);
		
		return "taller/tipo/editarmeses";
	}
	
	@RequestMapping("/taller/tipo/grabarmeses")
	public String tallerTipoGrabarMeses(HttpServletRequest request, Model modelo){
		
		String ejercicioId = request.getParameter("EjercicioId")==null?"0":request.getParameter("EjercicioId");
		String tipo		   = request.getParameter("Tipo")==null?"0":request.getParameter("Tipo");
		String mensaje	   = "-";
		
			String months = "";
			for(int i=1; i <= 12; i++){
				if(request.getParameter(String.valueOf(i))!=null){
					months += "-"+String.valueOf(i);
				}
			}
			if(becTipoDao.existeReg(ejercicioId, tipo)==true){
				if(becTipoDao.updateMeses(months, ejercicioId, tipo)){
					mensaje = "Grabado...";
				}else {
	 				mensaje = "Error al grabar...";
	 			}
			}		
		
		return "redirect:/taller/tipo/editarmeses?EjercicioId="+ejercicioId+"&Tipo="+tipo+"&Mensaje="+mensaje;
	}
}