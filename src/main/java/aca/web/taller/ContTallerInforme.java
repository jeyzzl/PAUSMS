package aca.web.taller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumPersonalDao;
import aca.bec.spring.BecAccesoDao;
import aca.bec.spring.BecCategoriaDao;
import aca.bec.spring.BecInforme;
import aca.bec.spring.BecInformeAlumno;
import aca.bec.spring.BecInformeAlumnoDao;
import aca.bec.spring.BecInformeDao;
import aca.bec.spring.BecPuestoAlumno;
import aca.bec.spring.BecPuestoAlumnoDao;
import aca.financiero.spring.ContCcosto;
import aca.financiero.spring.ContCcostoDao;
import aca.financiero.spring.ContEjercicio;
import aca.financiero.spring.ContEjercicioDao;
import aca.vista.spring.InscritosDao;

@Controller
public class ContTallerInforme {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	private BecInformeDao becInformeDao;
	
	@Autowired
	private BecAccesoDao becAccesoDao;
	
	@Autowired
	private AccesoDao accesoDao;
	
	@Autowired
	private BecInformeAlumnoDao becInformeAlumnoDao;
	
	@Autowired
	private ContEjercicioDao contEjercicioDao;
	
	@Autowired
	private ContCcostoDao contCcostoDao;	
	
	@Autowired
	private BecCategoriaDao becCategoriaDao;
	
	@Autowired
	private InscritosDao inscritosDao;
	
	@Autowired
	private BecPuestoAlumnoDao becPuestoAlumnoDao;
	
	@Autowired
	private AlumPersonalDao alumPersonalDao;
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	} 
	
	@RequestMapping("/taller/informe/accion")
	public String tallerInformeAccion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContTallerInforme|tallerInformeAccion:");
		return "taller/informe/accion";
	}	
	
	@RequestMapping("/taller/informe/informe")
	public String tallerInformeInforme(HttpServletRequest request, Model modelo){
		
		String ejercicioId 		= request.getParameter("EjercicioId")==null?"0":request.getParameter("EjercicioId");
		String deptoId 			= request.getParameter("DeptoId")==null?"0":request.getParameter("DeptoId");
		String informeId 		= request.getParameter("InformeId")==null?"0":request.getParameter("InformeId");
		String codigoPersonal	= "0";		

		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
			if (ejercicioId.equals("0")) {
				ejercicioId		= (String) sesion.getAttribute("ejercicioId");
			}else {
				sesion.setAttribute("ejercicioId", ejercicioId);
			}			
			if (deptoId.equals("0")) {
				deptoId		= (String) sesion.getAttribute("ccosto");
			}else {
				sesion.setAttribute("ccosto", deptoId);
			}			
		}
		
		System.out.println(codigoPersonal);
		
		String acceso			= becAccesoDao.getUsuarioCentrosCosto( ejercicioId, codigoPersonal);	
		boolean admin       	= accesoDao.getBecas(codigoPersonal);

		List<ContEjercicio> lisEjercicios 	= contEjercicioDao.getListAll("ORDER BY 1 DESC");
		List<BecInforme> lisInformes		= becInformeDao.getListActivos( ejercicioId," ORDER BY ORDEN");
		List<ContCcosto> lisDeptos			= new ArrayList<ContCcosto>();
		if (admin) {
			lisDeptos 						= contCcostoDao.listDeptosConPuestos( ejercicioId, "ORDER BY ID_CCOSTO");
		}else{
			lisDeptos						= contCcostoDao.listCentrosCostoUsuario( ejercicioId, codigoPersonal, "ORDER BY ID_CCOSTO");
		}
		boolean encontre = false;
		for (ContCcosto depto : lisDeptos) {
			if ( deptoId.equals(depto.getIdCcosto())) {
				encontre = true;
				break;
			}
		}
		if (encontre==false && lisDeptos.size() >= 1) deptoId = lisDeptos.get(0).getIdCcosto();
		if(informeId.equals("0") && lisInformes.size() > 0){
			informeId = (lisInformes.get(0).getInformeId());
		}
		BecInforme becInforme = new BecInforme();
		if (becInformeDao.existeReg(informeId)) {
			becInforme = becInformeDao.mapeaRegId(informeId);
		}
		
		HashMap<String,String> mapaCategorias 			= becCategoriaDao.getMapCategorias();			
		HashMap<String,String> mapaPrepa 				= inscritosDao.getMapaInscritosPrepa();
		HashMap<String,String> mapaInscritos			= inscritosDao.getMapaTodosInscritos();	
		
		List<BecPuestoAlumno> lisPuestos				= becPuestoAlumnoDao.getListPuestosSC(ejercicioId, deptoId, " ORDER BY CATEGORIA_ID"); 
		List<BecInforme> lisInfAnt 						= becInformeDao.getListAnteriores( deptoId, becInforme.getNivel(), " ORDER BY ID_EJERCICIO, ORDEN");
		
		HashMap<String,BecInformeAlumno> mapaInformes 	= becInformeAlumnoDao.mapaInformesDepto(ejercicioId, deptoId);
		HashMap<String,String> mapaHorasAlumno 			= becInformeAlumnoDao.mapHorasAlumnoEnPuesto();	
		HashMap<String,String> mapaHorasInforme			= becInformeAlumnoDao.mapInformeHoras(informeId);
		HashMap<String,String> mapaHorasAco 			= becInformeAlumnoDao.mapHoras( deptoId );
		HashMap<String,String> mapaHorasTotal 			= becInformeAlumnoDao.mapHorasTotales(ejercicioId, deptoId);
		HashMap<String,String> mapaAlumnos 				= alumPersonalDao.mapaAlumnosEnPuestos(ejercicioId);
		
		modelo.addAttribute("acceso", acceso);		
		modelo.addAttribute("admin", admin);
		modelo.addAttribute("deptoId", deptoId);
		modelo.addAttribute("informeId", informeId);
		modelo.addAttribute("becInforme", becInforme);
		modelo.addAttribute("lisEjercicios", lisEjercicios);
		modelo.addAttribute("lisInformes", lisInformes);
		modelo.addAttribute("lisDeptos", lisDeptos);
		modelo.addAttribute("lisPuestos", lisPuestos);
		modelo.addAttribute("lisInfAnt", lisInfAnt);
		
		modelo.addAttribute("mapaCategorias", mapaCategorias);
		modelo.addAttribute("mapaPrepa", mapaPrepa);
		modelo.addAttribute("mapaInscritos", mapaInscritos);
		modelo.addAttribute("mapaHorasAlumno", mapaHorasAlumno);
		modelo.addAttribute("mapaHorasInforme", mapaHorasInforme);
		modelo.addAttribute("mapaHorasAco", mapaHorasAco);		
		modelo.addAttribute("mapaHorasTotal", mapaHorasTotal);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("mapaInformes", mapaInformes);

		return "taller/informe/informe";
	}
	
	@RequestMapping("/taller/informe/autorizar")
	public String tallerInformeAutorizar(HttpServletRequest request, Model modelo){
		
		String ejercicioId 		= request.getParameter("EjercicioId")==null?"0":request.getParameter("EjercicioId");
		String deptoId 			= request.getParameter("DeptoId")==null?"0":request.getParameter("DeptoId");
		String informeId 		= request.getParameter("InformeId")==null?"0":request.getParameter("InformeId");
		//String modalidades 		= "1,4,27,28";
		String modalidadesPrepa = "1,4";
		String mensaje 			= "";
		int grabados 			= 0;	
		
		BecInforme becInforme = new BecInforme();
		if (becInformeDao.existeReg(informeId)) {
			becInforme = becInformeDao.mapeaRegId(informeId);
		}
		
		HashMap<String,String> mapaPrepa 				= inscritosDao.getMapaInscritosPrepa(modalidadesPrepa);		
		
		//Puesto de Alumnos 	
		List<BecPuestoAlumno> lisPuestos = becPuestoAlumnoDao.getListPuestosSC(ejercicioId, deptoId, "ORDER BY CATEGORIA_ID");		
		
		boolean error = false;		
		for(BecPuestoAlumno puesto: lisPuestos){
	
			boolean mostrarPrepa = false, mostrarUniversidad = false;
			if (becInforme.getNivel().equals("P") &&  mapaPrepa.containsKey(puesto.getCodigoPersonal())) mostrarPrepa = true;
			if (becInforme.getNivel().equals("U") && !mapaPrepa.containsKey(puesto.getCodigoPersonal())) mostrarUniversidad = true;
			
			if(mostrarPrepa||mostrarUniversidad){
				
				if(becInformeAlumnoDao.existeReg(informeId, puesto.getCodigoPersonal())){
					if(becInformeAlumnoDao.updateEstado("2", informeId, puesto.getCodigoPersonal())){
						grabados++;
					}else{
						error = true;
						break;
					}	
				}
				
			}
		}
		
		if(error){
			mensaje = "<div class='alert alert-danger'>Ocurrió un error al autorizar a los alumnos</div>";
		}else{			
			mensaje = "<div class='alert alert-success'>Se autorizaron "+grabados+" alumnos</div>";
		}

		return "redirect:/taller/informe/informe?Mensaje="+mensaje;
	}
	
	@RequestMapping("/taller/informe/desautorizar")
	public String tallerInformeDesautorizar(HttpServletRequest request, Model modelo){
		
		String ejercicioId 		= request.getParameter("EjercicioId")==null?"0":request.getParameter("EjercicioId");
		String deptoId 			= request.getParameter("DeptoId")==null?"0":request.getParameter("DeptoId");
		String informeId 		= request.getParameter("InformeId")==null?"0":request.getParameter("InformeId");
		//String modalidades 		= "1,4,27,28";
		String modalidadesPrepa = "1,4";
		String mensaje 			= "";
		int grabados 			= 0;
		
		BecInforme becInforme = new BecInforme();
		if (becInformeDao.existeReg(informeId)) {
			becInforme = becInformeDao.mapeaRegId(informeId);
		}
		
		HashMap<String,String> mapaPrepa 				= inscritosDao.getMapaInscritosPrepa(modalidadesPrepa);		
		//Puesto de Alumnos 	
		List<BecPuestoAlumno> lisPuestos = becPuestoAlumnoDao.getListPuestosSC(ejercicioId, deptoId, "ORDER BY CATEGORIA_ID");
		
		boolean error = false;		
		for(BecPuestoAlumno puesto: lisPuestos){
	
			boolean mostrarPrepa = false, mostrarUniversidad = false;
			if (becInforme.getNivel().equals("P") &&  mapaPrepa.containsKey(puesto.getCodigoPersonal())) mostrarPrepa = true;
			if (becInforme.getNivel().equals("U") && !mapaPrepa.containsKey(puesto.getCodigoPersonal())) mostrarUniversidad = true;
			
			if(mostrarPrepa||mostrarUniversidad){				
				if(becInformeAlumnoDao.existeReg(informeId, puesto.getCodigoPersonal())){
					BecInformeAlumno becInformeAlumno = new BecInformeAlumno();
					becInformeAlumno = becInformeAlumnoDao.mapeaRegId(informeId, puesto.getCodigoPersonal());
					if(becInformeAlumno.getEstado().equals("2")){
						if(becInformeAlumnoDao.updateEstado("1", informeId, puesto.getCodigoPersonal())){
							grabados++;
						}else{
							error = true;
							break;
						}	
					}					
				}				
			}
		}
		
		if(error){
			mensaje = "<div class='alert alert-danger'>Ocurrió un error al desautorizar a los alumnos</div>";
		}else{			
			mensaje = "<div class='alert alert-success'>Se Desautorizaron "+grabados+" alumnos</div>";
		}

		return "redirect:/taller/informe/informe?Mensaje="+mensaje;
	}
	
	@RequestMapping("/taller/informe/editar")
	public String tallerInformeEditar(HttpServletRequest request, Model modelo){
		
		String ejercicioId 		= "0";
		String ccosto 			= request.getParameter("CcostoId")==null?"0":request.getParameter("CcostoId");		
		String informeId		= request.getParameter("informeId")==null?"0":request.getParameter("informeId");
		String puestoId	 		= request.getParameter("puestoId");
		String codigoPersonal 	= request.getParameter("codigoPersonal")==null?"0":request.getParameter("codigoPersonal");
		String alumnoNombre		= "-";
		String informeNombre 	= becInformeDao.getNombreInforme(informeId);
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			ejercicioId			= (String) sesion.getAttribute("ejercicioId");
			if (ccosto.equals("0")) {
				ccosto 				= (String) sesion.getAttribute("ccosto");
			}else {
				sesion.setAttribute("ccosto", ccosto);
			}
			alumnoNombre 		= alumPersonalDao.getNombreAlumno(codigoPersonal, "NOMBRE");
		}
		String horasAcumuladas 					= becInformeAlumnoDao.horasEnPuesto(codigoPersonal, puestoId);
		String horasAcumuladasSinActual 		= becInformeAlumnoDao.horasEnPuestoExcluyendo(codigoPersonal, puestoId, informeId);	
		BecInformeAlumno becInformeAlumno = new BecInformeAlumno();
		boolean existeInforme 	= false;	
		if (becInformeAlumnoDao.existeReg(informeId, codigoPersonal)){
			existeInforme 		= true;
			becInformeAlumno 	= becInformeAlumnoDao.mapeaRegId(informeId, codigoPersonal);
		}
		
		modelo.addAttribute("existeInforme", existeInforme);
		modelo.addAttribute("alumnoNombre", alumnoNombre);
		modelo.addAttribute("informeNombre", informeNombre);
		modelo.addAttribute("becInformeAlumno", becInformeAlumno);
		modelo.addAttribute("horasAcumuladas", horasAcumuladas);
		modelo.addAttribute("horasAcumuladasSinActual", horasAcumuladasSinActual);
		
		return "taller/informe/editar";
	}
	
	@RequestMapping("/taller/informe/grabar")
	public String tallerInformeGrabar(HttpServletRequest request, Model modelo){
		
		String ejercicioId 		= "0";
		String ccosto 			= "0";
		String usuario 			= "0";		
		String informeId		= request.getParameter("informeId")==null?"0":request.getParameter("informeId");
		String puestoId	 		= request.getParameter("puestoId");
		String codigoPersonal 	= request.getParameter("codigoPersonal")==null?"0":request.getParameter("codigoPersonal");
		String horasContrato 	= request.getParameter("horasContrato");
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			ejercicioId			= (String) sesion.getAttribute("ejercicioId");
			ccosto 				= (String) sesion.getAttribute("ccosto");
			usuario 			= (String) sesion.getAttribute("codigoPersonal");
		}
		
		String puntualidad 		= request.getParameter("Puntualidad")==null?"0":request.getParameter("Puntualidad");
		String funcion	 		= request.getParameter("Funcion")==null?"0":request.getParameter("Funcion");
		String tiempo 			= request.getParameter("Tiempo")==null?"0":request.getParameter("Tiempo");
		String iniciativa 		= request.getParameter("Iniciativa")==null?"0":request.getParameter("Iniciativa");
		String relacion 		= request.getParameter("Relacion")==null?"0":request.getParameter("Relacion");
		String respeto 			= request.getParameter("Respeto")==null?"0":request.getParameter("Respeto");
		String productivo 		= request.getParameter("Productivo")==null?"0":request.getParameter("Productivo");
		String cuidado 			= request.getParameter("Cuidado")==null?"0":request.getParameter("Cuidado");
		String horas	 		= request.getParameter("Horas")==null?"0":request.getParameter("Horas");		
		String tardanzas 		= request.getParameter("Tardanzas")==null?"0":request.getParameter("Tardanzas");
		String ausencias 		= request.getParameter("Ausencias")==null?"0":request.getParameter("Ausencias");
		
		BecInformeAlumno becInformeAlumno = new BecInformeAlumno();
		
		becInformeAlumno.setIdEjercicio(ejercicioId);
		becInformeAlumno.setCodigoPersonal(codigoPersonal);
		becInformeAlumno.setInformeId(informeId);
		becInformeAlumno.setPuestoId(puestoId);
		becInformeAlumno.setFecha(aca.util.Fecha.getHoy());
		becInformeAlumno.setPuntualidad(puntualidad);
		becInformeAlumno.setFuncion(funcion);
		becInformeAlumno.setTiempo(tiempo);
		becInformeAlumno.setIniciativa(iniciativa);
		becInformeAlumno.setRelacion(relacion);
		becInformeAlumno.setRespeto(respeto);
		becInformeAlumno.setProductivo(productivo);
		becInformeAlumno.setCuidado(cuidado);
		becInformeAlumno.setHoras(horas);
		becInformeAlumno.setTardanzas(tardanzas);
		becInformeAlumno.setAusencias(ausencias);
		becInformeAlumno.setEstado("1");
		becInformeAlumno.setIdCcosto(ccosto);
		becInformeAlumno.setUsuario(usuario);		
		if (becInformeAlumnoDao.existeReg(informeId, codigoPersonal)){			
			if (becInformeAlumnoDao.updateReg(becInformeAlumno)){
			}
		}else{
			// Modificar el informe del alumno
			if (becInformeAlumnoDao.insertReg(becInformeAlumno)){
			}
		}

		return "redirect://taller/informe/editar?informeId="+informeId+"&puestoId="+puestoId+"&codigoPersonal="+codigoPersonal+"&horasContrato="+horasContrato;
	}
	
}