package aca.web.investigacion;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.investiga.spring.InvActividad;
import aca.investiga.spring.InvActividadDao;
import aca.investiga.spring.InvBitacora;
import aca.investiga.spring.InvBitacoraDao;
import aca.investiga.spring.InvComentario;
import aca.investiga.spring.InvComentarioDao;
import aca.investiga.spring.InvMetodologia;
import aca.investiga.spring.InvMetodologiaDao;
import aca.investiga.spring.InvPresupuesto;
import aca.investiga.spring.InvPresupuestoDao;
import aca.investiga.spring.InvProyecto;
import aca.investiga.spring.InvProyectoDao;
import aca.investiga.spring.InvResultado;
import aca.investiga.spring.InvResultadoDao;
import aca.vista.spring.Usuarios;
import aca.vista.spring.UsuariosDao;

@Controller
public class ContInvestigacionProyecto {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	aca.investiga.spring.InvArchivoDao invArchivoDao;
	
	@Autowired
	private UsuariosDao usuariosDao;
	
	@Autowired
	private InvProyectoDao invProyectoDao;
	
	@Autowired
	private InvComentarioDao invComentarioDao;
	
	@Autowired
	private CatCarreraDao catCarreraDao;
	
	@Autowired
	private InvBitacoraDao invBitacoraDao;
	
	@Autowired
	private InvMetodologiaDao invMetodologiaDao;
	
	@Autowired
	private InvActividadDao invActividadDao;
	
	@Autowired
	private InvPresupuestoDao invPresupuestoDao;
	
	@Autowired
	private InvResultadoDao invResultadoDao;
	
	@Autowired
	private CatFacultadDao catFacultadDao;
	
	 
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/investigacion/proyecto/view")
	public String investigacionProyectoView(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ControllerInvestigacion|investigacionProyectoView:");
		return "investigacion/proyecto/view";
	}	
	
	@RequestMapping("/investigacion/proyecto/permisos")
	public String investigacionProyectoPermisos(HttpServletRequest request, Model modelo){
		
		String proyectoId		= request.getParameter("proyectoId")==null?"0":request.getParameter("proyectoId");
		
		String nombreArchivo 	= invArchivoDao.getNombre(proyectoId, "1");		
		String nombreArchivo2	= invArchivoDao.getNombre(proyectoId, "2");
		String nombreArchivo3	= invArchivoDao.getNombre(proyectoId, "3");
		String nombreArchivo4	= invArchivoDao.getNombre(proyectoId, "4");
		String nombreArchivo5	= invArchivoDao.getNombre(proyectoId, "5");
		String nombreArchivo6	= invArchivoDao.getNombre(proyectoId, "6");
		String nombreArchivo7	= invArchivoDao.getNombre(proyectoId, "7");
		String nombreArchivo8	= invArchivoDao.getNombre(proyectoId, "8");
		
		modelo.addAttribute("nombreArchivo",nombreArchivo);
		modelo.addAttribute("nombreArchivo2",nombreArchivo2);
		modelo.addAttribute("nombreArchivo3",nombreArchivo3);
		modelo.addAttribute("nombreArchivo4",nombreArchivo4);
		modelo.addAttribute("nombreArchivo5",nombreArchivo5);
		modelo.addAttribute("nombreArchivo6",nombreArchivo6);
		modelo.addAttribute("nombreArchivo7",nombreArchivo7);
		modelo.addAttribute("nombreArchivo8",nombreArchivo8);
		
		return "investigacion/proyecto/permisos";
	}	
	
	@PostMapping("/investigacion/proyecto/saveFile")
	public String investigacionProyectoSaveFile(@RequestParam("archivo") MultipartFile file, HttpServletRequest request){
		
		aca.investiga.spring.InvArchivo archivo = new aca.investiga.spring.InvArchivo();
		
		String proyectoId		= request.getParameter("proyectoId")==null?"0":request.getParameter("proyectoId");
		String folio			= request.getParameter("folio")==null?"0":request.getParameter("folio");		
		
		try {
			archivo.setProyectoId(proyectoId);
			archivo.setFolio(folio);			
			archivo.setNombre( file.getOriginalFilename() );
			archivo.setArchivo(file.getBytes());			
			if(invArchivoDao.existeReg(proyectoId, folio)){				
				invArchivoDao.updateReg(archivo);	
			}else{				
				invArchivoDao.insertReg(archivo);	
			}			
		}catch(Exception ex){			
		}		
		
		return "redirect:/investigacion/proyecto/permisos?Accion=3&proyectoId="+proyectoId;
	}
	
	@RequestMapping("/investigacion/proyecto/borrarDoc")
	public String investigacionProyectoBorrarDoc(HttpServletRequest request, Model modelo){	
		
		String proyectoId		= request.getParameter("proyectoId")==null?"0":request.getParameter("proyectoId");
		String folio			= request.getParameter("folio")==null?"0":request.getParameter("folio");
		
		aca.investiga.spring.InvArchivo archivo = new aca.investiga.spring.InvArchivo();
		archivo.setProyectoId(proyectoId);
		archivo.setFolio(folio);
		if(invArchivoDao.existeReg(proyectoId, folio)){	
			invArchivoDao.deleteReg(proyectoId, folio);
		}
		
		return "redirect:/investigacion/proyecto/permisos?Accion=3&proyectoId="+proyectoId;
	}

	@RequestMapping("/investigacion/proyecto/proyecto")
	public String investigacionProyectoProyecto(HttpServletRequest request, Model modelo){	
		
		HttpSession sesion 		= ((HttpServletRequest) request).getSession();
		String codigo	   		= "";
		String nombreUsuario	= "";
		
		if (sesion != null) {
			codigo = (String) sesion.getAttribute("codigoPersonal");
		}
		
		Usuarios usuario = new Usuarios();
		
		usuario.setCodigoPersonal(codigo);
		
		if (usuariosDao.existeReg(codigo)){
			usuario = usuariosDao.mapeaRegId(codigo);
			nombreUsuario	= usuario.getNombre()+" "+usuario.getApellidoPaterno()+" "+usuario.getApellidoMaterno();
		}else{
			nombreUsuario = "¡ No existe !";
		}	
		
		List<InvProyecto> lisProyectos			= invProyectoDao.getListProyectosEmpleado(codigo,"ORDER BY PROYECTO_ID, PROYECTO_NOMBRE");
	
		HashMap<String,String> mapArchivoLight 		= invArchivoDao.mapArchivoLight();
		HashMap<String, String> mapComentarios		= invComentarioDao.getComentarios();
		HashMap<String,CatCarrera> mapaCarreras 	= catCarreraDao.mapaCarreras();
		
		modelo.addAttribute("codigo",codigo);
		modelo.addAttribute("nombreUsuario",nombreUsuario);
		modelo.addAttribute("lisProyectos",lisProyectos);
		modelo.addAttribute("mapArchivoLight",mapArchivoLight);
		modelo.addAttribute("mapComentarios",mapComentarios);
		modelo.addAttribute("mapaCarreras",mapaCarreras);
				
		return "investigacion/proyecto/proyecto";
	}
	
	@RequestMapping("/investigacion/proyecto/accion")
	public String investigacionProyectoAccion(HttpServletRequest request, Model modelo){
		
		HttpSession sesion 		= ((HttpServletRequest) request).getSession();
		
		String codigo			= (String) sesion.getAttribute("codigoPersonal");
		String accion 			= request.getParameter("Accion")==null?"":request.getParameter("Accion");
		String resumen 			= request.getParameter("Resumen")==null?"":request.getParameter("Resumen");
		String estadoArte 		= request.getParameter("EstadoArte")==null?"-":request.getParameter("EstadoArte");
		String folio	 		= request.getParameter("Folio")==null?"":request.getParameter("Folio");
		String tipoDoc 			= request.getParameter("TipoDoc")==null?"1":request.getParameter("TipoDoc");
		String proyectoId 		= request.getParameter("proyectoId")==null?"0":request.getParameter("proyectoId");
		String antecedentes 	= request.getParameter("Antecedentes")==null?"":request.getParameter("Antecedentes");
		String justificacion 	= request.getParameter("Justificacion")==null?"":request.getParameter("Justificacion");
		String resDocente 		= request.getParameter("ResDocente")==null?"":request.getParameter("ResDocente");
		String resAlumno 		= request.getParameter("ResAlumno")==null?"":request.getParameter("ResAlumno");
		
		String year				= aca.util.Fecha.getHoy().substring(6,10);
			 
		String nombreUsuario	= " ";
		String msj				= " ";
		String documento		= "N"; 
		
		InvProyecto invProyecto 		= new InvProyecto();
		InvMetodologia invMetodologia 	= new InvMetodologia();
		InvActividad invActividad 		= new InvActividad();
		InvPresupuesto invPresupuesto 	= new InvPresupuesto();
		InvResultado invResultado 		= new InvResultado();
		
		Usuarios usuario = new Usuarios();
		
		usuario.setCodigoPersonal(codigo);
		
		if (usuariosDao.existeReg(codigo)){
			usuario = usuariosDao.mapeaRegId(codigo);
			nombreUsuario	= usuario.getNombre()+" "+usuario.getApellidoPaterno()+" "+usuario.getApellidoMaterno();
		}else{
			nombreUsuario = "¡ No existe !";
		}
		
		// PARA EL COMBO DE CARRERAS ---------------------------------->
		List<CatCarrera> lisCarreras = catCarreraDao.getListAll(" ORDER BY FACULTAD_ID, NOMBRE_CARRERA");
		
		if(accion.equals("0")){   //nuevo
			/* System.out.println(proyectoId); */
			proyectoId = "0";
			invProyecto.setProyectoId(proyectoId);
			
		}else if(accion.equals("1")) { //guardar			
			invProyecto.setCodigoPersonal(codigo);
			invProyecto.setCarreraId(request.getParameter("carrera"));
			invProyecto.setFechaInicio(request.getParameter("fechaIni"));
			invProyecto.setFechaFinal(request.getParameter("fechaFin"));
			invProyecto.setProyectoNombre(request.getParameter("titulo"));
			invProyecto.setLinea(request.getParameter("linea"));
			invProyecto.setTipo(request.getParameter("tipo"));
			invProyecto.setInvestigadores(request.getParameter("invSec"));
			invProyecto.setTipoDocumento(tipoDoc);
			String departamento = request.getParameter("departamento");
			if(departamento.equals("")){
				departamento = "-";
			}else{
				departamento = request.getParameter("departamento");
			}
			invProyecto.setDepartamento(departamento);
			invProyecto.setResumen(resumen);
			invProyecto.setEstadoArte(estadoArte);
			invProyecto.setDocumento(request.getParameter("documento"));
			invProyecto.setEstado("0");
			invProyecto.setFolio(folio);
			invProyecto.setAntecedentes(antecedentes);
			invProyecto.setJustificacion(justificacion);				
			invProyecto.setResDocente(resDocente);
			invProyecto.setResAlumno(resAlumno);
			
			if (proyectoId.equals("0") ){
				proyectoId = invProyectoDao.maxReg(year);
				invProyecto.setProyectoId(proyectoId);
			}else{
				invProyecto.setProyectoId(proyectoId);
			}	
			
			if(invProyectoDao.existeReg(proyectoId)==false){
				
				if(invProyectoDao.insertReg(invProyecto)){
					msj = "<div class='alert alert-success' style='text-align:center; font-size:16px;'><button class='close' data-dismiss='alert'>&times;</button><strong>¡Excelente!</strong> El Registro se guardó correctamente</div>";
				}else{
					msj = "<div class='alert alert-danger' style='text-align:center; font-size:16px;'><button class='close' data-dismiss='alert'>&times;</button><strong>¡Atención!</strong> Ocurrió un error al guardar el registro</div>";
				}
			}else{//Modifica
				
				if(invProyectoDao.updateReg(invProyecto)){
					msj = "<div class='alert alert-success' style='text-align:center; font-size:16px;'><button class='close' data-dismiss='alert'>&times;</button><strong>¡Buen trabajo!</strong> El registro se editó correctamente</div>";
				}else{
					msj = "<div class='alert alert-warning' style='text-align:center; font-size:16px;'><button class='close' data-dismiss='alert'>&times;</button><strong>¡Atención!</strong> Ocurrió un error al editar el registro</div>";
				}
				
			}
		}else if(accion.equals("2")){
			if (invProyectoDao.existeReg(proyectoId)) {
				if (invProyectoDao.deleteReg(proyectoId)) {
					msj = "<div class='alert alert-warning' style='text-align:center; font-size:16px;'><button class='close' data-dismiss='alert'>&times;</button> <strong>¡Bien hecho!</strong> El registro se eliminó correctamente</div>";
				} else {
					msj = "<div class='alert alert-danger' style='text-align:center; font-size:16px;'><button class='close' data-dismiss='alert'>&times;</button><strong>¡Atención!</strong> Ocurrió un error al eliminar el registro</div>";
				}
			} else {
				msj = "<div class='alert alert-danger' style='text-align:center; font-size:16px;'><button class='close' data-dismiss='alert'>&times;</button> Este Registro no existe</div>";
			}
			
			if (invMetodologiaDao.existeReg(proyectoId)) {
				invMetodologiaDao.deleteReg(proyectoId);
			}
			
			if (invActividadDao.existeReg(proyectoId)) {
				invActividadDao.deleteReg(proyectoId);
			} 
			
			if (invPresupuestoDao.existeReg(proyectoId)) {
				invPresupuestoDao.deleteReg(proyectoId);
			}
			
			if (invResultadoDao.existeReg(proyectoId)) {
				invResultadoDao.deleteReg(proyectoId);
			}
			
			if (invArchivoDao.existeReg(proyectoId,folio)) {
				invArchivoDao.deleteReg(proyectoId,folio);
			}
		}else if((accion.equals("3"))){
			invProyecto.setProyectoId(proyectoId);
			invProyecto = invProyectoDao.mapeaRegId(proyectoId);
			documento = invProyecto.getDocumento();
		}
		
		HashMap<String, CatFacultad> mapaFacultad = catFacultadDao.getMapFacultad("");
		
		modelo.addAttribute("msj", msj);
		modelo.addAttribute("proyectoId", proyectoId);
		modelo.addAttribute("documento", documento);
		modelo.addAttribute("nombreUsuario", nombreUsuario);
		modelo.addAttribute("lisCarreras", lisCarreras);
		modelo.addAttribute("accion", accion);
		modelo.addAttribute("mapaFacultad", mapaFacultad);
		modelo.addAttribute("invProyecto", invProyecto);
		
		return "investigacion/proyecto/accion";
	}
	
	@RequestMapping("/investigacion/proyecto/bitacora")
	public String investigacionProyectoBitacora(HttpServletRequest request, Model modelo) throws SQLException{
		
		String proyectoId 		= request.getParameter("ProyectoId")==null?"0":request.getParameter("ProyectoId");
		String nombreProyecto    = invProyectoDao.getNombreProyecto(proyectoId);

		List<InvBitacora> lisBitacora = invBitacoraDao.getListProyecto(proyectoId," ORDER BY FOLIO");
		HashMap<String, String> mapUsuarios = usuariosDao.mapaUsuariosEnBitacora();

		modelo.addAttribute("mapUsuarios", mapUsuarios);
		modelo.addAttribute("nombreProyecto", nombreProyecto);
		modelo.addAttribute("lisBitacora", lisBitacora);

		return "investigacion/proyecto/bitacora";

	}	
	
	@RequestMapping("/investigacion/proyecto/borrarInfo1")
	public String investigacionProyectoBorrarInfo1(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContInvestigacionProyecto|investigacionProyectoBorrarInfo1:");
		return "investigacion/proyecto/borrarInfo1";
	}
	
	@RequestMapping("/investigacion/proyecto/borrarInfo2")
	public String investigacionProyectoBorrarInfo2(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContInvestigacionProyecto|investigacionProyectoBorrarInfo2:");
		return "investigacion/proyecto/borrarInfo2";
	}
	
	@RequestMapping("/investigacion/proyecto/borrar")
	public String investigacionProyectoBorrar(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContInvestigacionProyecto|investigacionProyectoBorrar:");
		return "investigacion/proyecto/borrar";
	}
	
	@RequestMapping("/investigacion/proyecto/comentario")
	public String investigacionProyectoComentario(HttpServletRequest request, Model modelo){
		String proyectoId 		= request.getParameter("ProyectoId")==null?"0":request.getParameter("ProyectoId");

		String codigoPersonal	= "0";
		String nombreUsuario	= "-";
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();		
		if (sesion!=null){		
			codigoPersonal 		= sesion.getAttribute("codigoPersonal")==null?"0":(String)sesion.getAttribute("codigoPersonal");
		}	
		if (usuariosDao.existeReg(codigoPersonal)){
			nombreUsuario = usuariosDao.getNombreUsuario(codigoPersonal, "NOMBRE");			
		}else{
			nombreUsuario = "¡ No existe !";
		}
		
		List<InvComentario> lisComentarios = invComentarioDao.getListProyecto(proyectoId, "AND TIPO ='S' ORDER BY FOLIO");
		HashMap<String, String> mapUsuarios = usuariosDao.mapaUsuariosEnComentarios();
		
		modelo.addAttribute("mapUsuarios", mapUsuarios);
		modelo.addAttribute("lisComentarios", lisComentarios);
		modelo.addAttribute("nombreUsuario", nombreUsuario);
		
		return "investigacion/proyecto/comentario";
	}
	
	@RequestMapping("/investigacion/proyecto/guardarAsen")
	public String investigacionProyectoGuardarAsen(HttpServletRequest request){
		return "investigacion/proyecto/guardarAsen";
	}
	
	@RequestMapping("/investigacion/proyecto/guardarConf")
	public String investigacionProyectoGuardarConf(HttpServletRequest request){
		return "investigacion/proyecto/guardarCon";
	}
	
	@RequestMapping("/investigacion/proyecto/guardarConOral")
	public String investigacionProyectoGuardarConOral(HttpServletRequest request){
		return "investigacion/proyecto/guardarConOral";
	}
	
	@RequestMapping("/investigacion/proyecto/guardarConPadres")
	public String investigacionProyectoGuardarConPadres(HttpServletRequest request){
		//enviarConEnoc(request,"Error-aca.ContInvestigacionProyecto|investigacionProyectoGuardarConPadres:");
		return "investigacion/proyecto/guardarConPadres";
	}
	
	@RequestMapping("/investigacion/proyecto/guardarInfo1")
	public String investigacionProyectoGuardarInfo1(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContInvestigacionProyecto|investigacionProyectoGuardarInfo1:");
		return "investigacion/proyecto/guardarInfo1";
	}
	
	@RequestMapping("/investigacion/proyecto/guardarInfo2")
	public String investigacionProyectoGuardarInfo2(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContInvestigacionProyecto|investigacionProyectoGuardarInfo2:");
		return "investigacion/proyecto/guardarInfo2";
	}
	
	@RequestMapping("/investigacion/proyecto/guardarInst")
	public String investigacionProyectoGuardarInst(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContInvestigacionProyecto|investigacionProyectoGuardarInst:");
		return "investigacion/proyecto/guardarInst";
	}
	
	@RequestMapping("/investigacion/proyecto/guardar")
	public String investigacionProyectoGuardar(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContInvestigacionProyecto|investigacionProyectoGuardar:");
		return "investigacion/proyecto/guardar";
	}
	
	@RequestMapping("/investigacion/proyecto/resultados")
	public String investigacionProyectoResultados(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContInvestigacionProyecto|investigacionProyectoResultados:");
		return "investigacion/proyecto/resultados";
	}
	
	@RequestMapping("/investigacion/proyecto/metodologia")
	public String investigacionProyectoMetodologia(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContInvestigacionProyecto|investigacionProyectoMetodologia:");
		return "investigacion/proyecto/metodologia";
	}
	
	@RequestMapping("/investigacion/proyecto/subirInfo1")
	public String investigacionProyectoSubirInfo1(HttpServletRequest request){
		//enviarConEnoc(request,"Error-aca.ContInvestigacionProyecto|investigacionProyectoSubirInfo1:");
		return "investigacion/proyecto/subirInfo1";
	}
	
	@RequestMapping("/investigacion/proyecto/subirInfo2")
	public String investigacionProyectoSubirInfo2(HttpServletRequest request){
		return "investigacion/proyecto/subirInfo2";
	}
	
	@RequestMapping("/investigacion/proyecto/subirInst")
	public String investigacionProyectoSubirInst(HttpServletRequest request){
		//enviarConEnoc(request,"Error-aca.ContInvestigacionProyecto|investigacionProyectoSubirInst:");
		return "investigacion/proyecto/subirInst";
	}
	
	@RequestMapping("/investigacion/proyecto/subir")
	public String investigacionProyectoSubir(HttpServletRequest request){
		return "investigacion/proyecto/subir";
	}
	
}