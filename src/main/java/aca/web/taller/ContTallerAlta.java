package aca.web.taller;

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

import aca.afe.spring.FesCcAfeAcuerdos;
import aca.afe.spring.FesCcAfeAcuerdosDao;
import aca.alumno.spring.AlumPersonalDao;
import aca.bec.spring.BecAcuerdo;
import aca.bec.spring.BecAcuerdoDao;
import aca.bec.spring.BecCategoria;
import aca.bec.spring.BecCategoriaDao;
import aca.bec.spring.BecPuestoAlumno;
import aca.bec.spring.BecPuestoAlumnoDao;
import aca.bec.spring.BecTipo;
import aca.bec.spring.BecTipoDao;
import aca.financiero.spring.ContCcosto;
import aca.financiero.spring.ContCcostoDao;
import aca.vista.spring.Inscritos;
import aca.vista.spring.InscritosDao;
import aca.vista.spring.UsuariosDao;

@Controller
public class ContTallerAlta {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired	
	private InscritosDao inscritosDao;
	
	@Autowired	
	private BecAcuerdoDao becAcuerdoDao;
	
	@Autowired	
	private BecTipoDao becTipoDao;
	
	@Autowired
	FesCcAfeAcuerdosDao fesCcAfeAcuerdosDao;
	
	@Autowired
	ContCcostoDao contCcostoDao;
	
	@Autowired
	UsuariosDao usuariosDao;
	
	@Autowired
	BecPuestoAlumnoDao becPuestoAlumnoDao;
	
	@Autowired
	BecCategoriaDao becCategoriaDao;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;
	
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	} 
	
	@RequestMapping("/taller/alta/contrato")
	public String tallerAltaContrato(HttpServletRequest request, Model modelo){
		
		String idEjercicio 			= "";
		String codigoAlumno 		= "";
		String nombreAlumno			= "-";
		
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			idEjercicio 			= (String) sesion.getAttribute("ejercicioId");
			codigoAlumno 			= (String) sesion.getAttribute("codigoAlumno");
			nombreAlumno			= usuariosDao.getNombreUsuario(codigoAlumno, "NOMBRE");
		}
		String accion 				= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		String puestoId 			= request.getParameter("PuestoId")==null?"":request.getParameter("PuestoId");
		String horas  				= request.getParameter("Horas")==null?"":request.getParameter("Horas");	
		boolean inscrito    		= false;		
		if(inscritosDao.existeReg(codigoAlumno)){
			inscrito = true;
		}		
				
		// Actualizar el número de horas
		if(accion.equals("1")){
			becAcuerdoDao.updateHorasDelPuesto(codigoAlumno, puestoId, horas);
			fesCcAfeAcuerdosDao.updateHoras(horas, codigoAlumno, puestoId);
		}
		
		List<BecAcuerdo> lisAcuerdos 					= becAcuerdoDao.getAcuerdosAlumnoVigentes(idEjercicio, codigoAlumno, "ORDER BY TIPO");
		HashMap<String,ContCcosto> mapaCentroCostos 	= contCcostoDao.mapaCentrosCosto(idEjercicio);
		HashMap<String,FesCcAfeAcuerdos> mapaAcuerdos	= fesCcAfeAcuerdosDao.mapaAcuerdosEnEjercicio(idEjercicio);
		HashMap<String,BecTipo> mapaTipos				= becTipoDao.mapaBecTipos(idEjercicio);
		
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("inscrito", inscrito);
		modelo.addAttribute("lisAcuerdos", lisAcuerdos);
		modelo.addAttribute("mapaCentroCostos", mapaCentroCostos);
		modelo.addAttribute("mapaAcuerdos", mapaAcuerdos);
		modelo.addAttribute("mapaTipos", mapaTipos);
		
		return "taller/alta/contrato";
	}	
	
	@RequestMapping("/taller/alta/horas")
	public String tallerAltaHoras(HttpServletRequest request, Model modelo){
		
		String folio 			= request.getParameter("folio")==null?"0":request.getParameter("folio");
		String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		String idEjercicio 		= "0";
		String codigoAlumno 	= "0";		
		String alumnoNombre 	= "-";
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			idEjercicio 		= (String) sesion.getAttribute("ejercicioId");
			codigoAlumno 		= (String) sesion.getAttribute("codigoAlumno");
			alumnoNombre 		= alumPersonalDao.getNombre(codigoAlumno, "NOMBRE");
		}		
		String msj 				= "";
		FesCcAfeAcuerdos afeAcuerdos 	= new FesCcAfeAcuerdos();
		
		if(accion.equals("1")){//GRABAR
			//MAPEA SU INFORMACION DE LA VISTA DE INSCRITOS (EN ENOC)
			Inscritos inscritos 			= inscritosDao.mapeaRegId(codigoAlumno);
			BecAcuerdo becAcuerdo 			= becAcuerdoDao.mapeaRegId( folio, codigoAlumno);
			BecTipo becTipo 				= becTipoDao.mapeaRegId(idEjercicio, becAcuerdo.getTipo());						
			BecPuestoAlumno becPuestoAlumno = becPuestoAlumnoDao.mapeaRegId(becAcuerdo.getPuestoId());			
			BecCategoria becCategoria 		= becCategoriaDao.mapeaRegId(becPuestoAlumno.getCategoriaId());
			
			//SET LA INFORMACION PARA GRABAR EN MATEO			
			afeAcuerdos.setMatricula( codigoAlumno );
			afeAcuerdos.setCargaId( inscritos.getCargaId() );
			afeAcuerdos.setBloque( inscritos.getBloqueId() );
			afeAcuerdos.setTipoId( becTipo.getTipo() );
			afeAcuerdos.setTipoNombre( becTipo.getNombre() );
			afeAcuerdos.setTipoCuenta( becTipo.getCuenta() );
			afeAcuerdos.setTipoImporte(becTipo.getImporte());
			afeAcuerdos.setTipoAcuerdo( becTipo.getAcuerdo() );
			afeAcuerdos.setAcuerdoFecha( becAcuerdo.getFecha() );
			afeAcuerdos.setAcuerdoImpMatricula( request.getParameter("matricula") );
			afeAcuerdos.setAcuerdoImpEnsenanza( request.getParameter("ensenanza") );
			afeAcuerdos.setAcuerdoImpInternado( request.getParameter("internado") );
			afeAcuerdos.setAcuerdoVigencia( becAcuerdo.getVigencia() );
			afeAcuerdos.setAcuerdoFolio( folio );
			afeAcuerdos.setAcuerdoPromesa( becAcuerdo.getPromesa() );
			afeAcuerdos.setAcuerdoHoras( becAcuerdo.getHoras() );
			afeAcuerdos.setAcuerdoEjercicioId( becAcuerdo.getIdEjercicio() );
			afeAcuerdos.setAcuerdoCcostoId( becAcuerdo.getIdCcosto() );
			afeAcuerdos.setAlpuestoPuestoId( becPuestoAlumno.getPuestoId() );
			afeAcuerdos.setCategoriaId( becCategoria.getCategoriaId() );
			afeAcuerdos.setCategoriaNombre( becCategoria.getCategoriaNombre() );
			afeAcuerdos.setAlpuestoFechaInical( becPuestoAlumno.getFechaIni() );
			afeAcuerdos.setAlpuestoFechaFinal( becPuestoAlumno.getFechaFin() );
			afeAcuerdos.setAlpuestoTipo( becPuestoAlumno.getTipo() );
			afeAcuerdos.setTotalBecaAdic( request.getParameter("totalBecAd") );
			afeAcuerdos.setValor( becAcuerdo.getValor() );
			
			if(fesCcAfeAcuerdosDao.existeReg(afeAcuerdos.getMatricula(), afeAcuerdos.getAcuerdoFolio(), afeAcuerdos.getAlpuestoPuestoId())){
				if(fesCcAfeAcuerdosDao.updateReg(afeAcuerdos)){
					msj = "<div class='alert alert-success'>Se Modificó Correctamente</div>";
				}else{
					msj = "<div class='alert alert-danger'>Ocurrió un Error al Modificar</div>";
				}
			}else{
				if(fesCcAfeAcuerdosDao.insertReg(afeAcuerdos)){
					msj = "<div class='alert alert-success'>Se Guardó Correctamente</div>";
				}else{
					msj = "<div class='alert alert-danger'>Ocurrió un Error al Guardar</div>";
				}
			}
		}else{			
			if(fesCcAfeAcuerdosDao.existeReg(afeAcuerdos.getMatricula(), afeAcuerdos.getAcuerdoFolio(), afeAcuerdos.getAlpuestoPuestoId())){
				afeAcuerdos = fesCcAfeAcuerdosDao.mapeaRegId(afeAcuerdos.getMatricula(), afeAcuerdos.getAcuerdoFolio(), afeAcuerdos.getAlpuestoPuestoId());
			}
		}
		modelo.addAttribute("afeAcuerdos", afeAcuerdos);
		modelo.addAttribute("alumnoNombre", alumnoNombre);
		
		return "taller/alta/horas";
	}	
	
	@RequestMapping("/taller/alta/registrar")
	public String tallerAltaRegistrar(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContTallerAlta|tallerAltaRegistrar:");
		return "taller/alta/registrar";
	}		
	
}
