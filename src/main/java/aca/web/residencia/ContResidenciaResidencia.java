package aca.web.residencia;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumAcademico;
import aca.alumno.spring.AlumAcademicoDao;
import aca.alumno.spring.AlumDireccion;
import aca.alumno.spring.AlumDireccionDao;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlanDao;
import aca.alumno.spring.AlumUbicacion;
import aca.alumno.spring.AlumUbicacionDao;
import aca.carga.spring.CargaAlumno;
import aca.carga.spring.CargaAlumnoDao;
import aca.catalogo.spring.CatAcomodo;
import aca.catalogo.spring.CatAcomodoDao;
import aca.catalogo.spring.CatCiudadDao;
import aca.catalogo.spring.CatPaisDao;
import aca.catalogo.spring.CatRecogida;
import aca.catalogo.spring.CatRecogidaDao;
import aca.internado.spring.IntDormitorio;
import aca.internado.spring.IntDormitorioDao;
import aca.log.spring.LogOperacion;
import aca.log.spring.LogOperacionDao;
import aca.pg.archivo.spring.PosArchResidencia;
import aca.pg.archivo.spring.PosArchResidenciaDao;
import aca.residencia.spring.ResComentario;
import aca.residencia.spring.ResComentarioDao;
import aca.residencia.spring.ResDatos;
import aca.residencia.spring.ResDatosDao;
import aca.residencia.spring.ResRazon;
import aca.residencia.spring.ResRazonDao;
import aca.vista.spring.UsuariosDao;

@Controller
public class ContResidenciaResidencia {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	private AlumAcademicoDao alumAcademicoDao;
	
	@Autowired
	private UsuariosDao usuariosDao;	
	
	@Autowired
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired
	private ResDatosDao resDatosDao;
	
	@Autowired
	private AlumDireccionDao alumDireccionDao;
	
	@Autowired
	private AlumUbicacionDao alumUbicacionDao;
	
	@Autowired
	private IntDormitorioDao intDormitorioDao;
	
	@Autowired
	private ResRazonDao resRazonDao;
	
	@Autowired
	private ResComentarioDao resComentarioDao;
	
	@Autowired
	private CatPaisDao catPaisDao;
	
	@Autowired
	private CatCiudadDao catCiudadDao;

	@Autowired
	private CatRecogidaDao catRecogidaDao;
	
	@Autowired	
	private PosArchResidenciaDao posArchResidenciaDao;
	
	@Autowired	
	private LogOperacionDao logOperacionDao;
	
	@Autowired	
	private CargaAlumnoDao cargaAlumnoDao;
	
	@Autowired	
	private AccesoDao accesoDao;
	
	@Autowired	
	private AlumPlanDao alumPlanDao;

	@Autowired
	private CatAcomodoDao catAcomodoDao;
	
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/residencia/residencia/documentosExternos")
	public String residenciaResidenciaDocumentosExternos(HttpServletRequest request, Model modelo){
		String codigoAlumno 		= "";
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();            
	    if (sesion != null){
	    	codigoAlumno = sesion.getAttribute("codigoAlumno").toString();
	    }	
		List<PosArchResidencia> lisImagenes = posArchResidenciaDao.getLista(codigoAlumno, " ORDER BY FOLIO");
		
		modelo.addAttribute("lisImagenes", lisImagenes);
		
		return "residencia/residencia/documentosExternos";
	}		

	@RequestMapping("/residencia/residencia/documentosExternosRes")
	public String residenciaResidenciaDocumentosExternosRes(HttpServletRequest request){
		//enviarConEnoc(request,"Error-aca.ContResidenciaResidencia|DocumentosExternosRes:");
		return "residencia/residencia/documentosExternosRes";
	}		
	
	@RequestMapping("/residencia/residencia/externo")
	public String residenciaResidenciaExterno(HttpServletRequest request){
		//enviarConEnoc(request,"Error-aca.ContResidenciaResidencia|Externo:");
			
		String codigoPersonal		= "0";
		String codigoAlumno			= "0";
		
		HttpSession sesion 			= ((HttpServletRequest)request).getSession();            
	    if (sesion != null){
	    	codigoPersonal			= (String) sesion.getAttribute("codigoPersonal");
	    	codigoAlumno 			= (String) sesion.getAttribute("codigoAlumno");
	    }	
		
		String razon 				= request.getParameter("Razon");
		String municipio			= request.getParameter("Municipio");
		String colonia				= request.getParameter("Colonia");
		String calle				= request.getParameter("Calle");
		String numero				= request.getParameter("Numero");
		String telArea				= request.getParameter("Area");
		String telNumero			= request.getParameter("Telefono");
		String nombreTutor			= request.getParameter("Nombre");
		String apellidosTutor		= request.getParameter("Apellidos");	
		String fecha				= request.getParameter("Fecha");
		// String comentario			= request.getParameter("Comentario");	
		String comentario 			= "From Boarding Student to Day Student : "+fecha;	
		String comentarioB 			= request.getParameter("ComentarioB");
		String acomodoId 			= request.getParameter("Acomodo");
		String recogidaId 			= request.getParameter("Recogida");
		
		String mensaje 				= "-";
		LogOperacion log = new LogOperacion();
		log.setTabla("RES_DATOS");
		log.setIp(request.getRemoteAddr());
		log.setUsuario(codigoPersonal);
		log.setDatos(codigoAlumno+"-Day Student-"+aca.util.Fecha.getHoy());
		
		ResDatos datos 	= new ResDatos();
		AlumAcademico academico = new AlumAcademico();
		AlumUbicacion ubicacion = new AlumUbicacion();	
		if (alumAcademicoDao.existeReg(codigoAlumno)){
			academico = alumAcademicoDao.mapeaRegId(codigoAlumno);
			academico.setResidenciaId("E");
			academico.setDormitorio("0");
			academico.setAcomodoId(acomodoId);
			
			if (alumAcademicoDao.updateReg(academico)){

				if(alumUbicacionDao.existeReg(codigoAlumno)){
					ubicacion = alumUbicacionDao.mapeaRegId(codigoAlumno);
					ubicacion.setRecogidaId(recogidaId);

					if(alumUbicacionDao.updateReg(ubicacion)){

						datos.setMatricula(codigoAlumno);			
						datos.setPeriodoId(aca.util.Fecha.getHoy().substring(6,10));
						datos.setRazon(razon);
						datos.setMpio(municipio);
						datos.setColonia(colonia);
						datos.setCalle(calle);
						datos.setNumero(numero);
						datos.setTelArea(telArea);
						datos.setTelNum(telNumero);
						datos.setNombreTut(nombreTutor);
						datos.setApellidoTut(apellidosTutor);
						datos.setFecha(fecha);
						datos.setUsuario(codigoPersonal);
						
						ResComentario resComentario = new ResComentario();
						String folioCom = resComentarioDao.maximoReg(codigoAlumno);		
						resComentario.setCodigoPersonal(codigoAlumno);
						resComentario.setFolio(folioCom);
						resComentario.setResidenciaId("E");	
						resComentario.setComentario(comentario);
						resComentario.setComentarioB(comentarioB);
						resComentarioDao.insertReg(resComentario);
					
						if (resDatosDao.existeReg(codigoAlumno)){			
							// Update
							if (resDatosDao.updateReg(datos)){
								log.setOperacion("update Residencia");
								if (logOperacionDao.insertReg(log)){							
									mensaje = "Updated to Day Student";
								}						
							}
						}else{
							// Insert
							if (resDatosDao.insertReg(datos)){					
								log.setOperacion("insert Residencia");
								if (logOperacionDao.insertReg(log)){						
									mensaje = "Saved as Day Student";
								}
							}
						}	
					} // si se actualiza AlumUbicacion
				}		
			} // si se actualiza AlumAcademico
		}else{
			mensaje = "No academic data found";
		}
		
		return "redirect:/residencia/residencia/residencia?Mensaje="+mensaje;
	}		
	
	@RequestMapping("/residencia/residencia/guardarArchivo")
	public String residenciaResidenciaGuardarArchivo(HttpServletRequest request, @RequestParam("archivo") MultipartFile imagen) throws IOException, FileUploadException{
		String codigoAlumno = "0";
		//imagen.getBytes();
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();            
	    if (sesion != null){
	    	codigoAlumno = sesion.getAttribute("codigoAlumno").toString();
	    	PosArchResidencia archivo = new PosArchResidencia();
	    	archivo.setCodigoPersonal(codigoAlumno);
	    	archivo.setFolio(posArchResidenciaDao.maximoReg(codigoAlumno));
	    	archivo.setImagen(imagen.getBytes());
	    	posArchResidenciaDao.insertRegByte(archivo);
	    }		
		return "redirect:/residencia/residencia/documentosExternos";
	}	
	
	@RequestMapping("/residencia/residencia/borrarArchivo")
	public String residenciaResidenciaBorrarArchivo(HttpServletRequest request, Model modelo){
		String folio = request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String codigoAlumno = "0";
		String mensaje		= "-";
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();            
	    if (sesion != null){
	    	codigoAlumno = sesion.getAttribute("codigoAlumno").toString();
	    }	

		if (posArchResidenciaDao.deleteReg(codigoAlumno, folio)){
			mensaje = "Deleted file";
		}
		return "redirect:/residencia/residencia/documentosExternos?Mensaje="+mensaje;
	}
	
	@RequestMapping("/residencia/residencia/guardarArchivoRes")
	public String residenciaResidenciaGuardarArchivoRes(HttpServletRequest request){		
		return "residencia/residencia/guardarArchivoRes";
	}		
	
	@RequestMapping("/residencia/residencia/historial")
	public String residenciaResidenciaHistorial(HttpServletRequest request, Model modelo){
		
		String codigoAlumno = "0";		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();            
	    if (sesion != null){
	    	codigoAlumno = sesion.getAttribute("codigoAlumno").toString();
	    }
	    
		List<LogOperacion> lisHistorial			= logOperacionDao.lisPorDatos(codigoAlumno, " ORDER BY FECHA");
		HashMap<String,String> mapaUsuarios		= usuariosDao.mapaUsuariosEnResidencia(codigoAlumno);
		
		modelo.addAttribute("lisHistorial", lisHistorial);
		modelo.addAttribute("mapaUsuarios", mapaUsuarios);
	
		return "residencia/residencia/historial";
	}		
	
	@RequestMapping("/residencia/residencia/interno")
	public String residenciaResidenciaInterno(HttpServletRequest request){
		
		String codigoPersonal	= "0";
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();            
	    if (sesion != null){	    	
	    	codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
	    }
		
		String fecha 				= aca.util.Fecha.getHoy();
		String codigoAlumno			= request.getParameter("codigoAlumno")==null?"0":request.getParameter("codigoAlumno");
		String dormitorio 			= request.getParameter("Dormitorio")==null?"0":request.getParameter("Dormitorio");
		String requerido 			= request.getParameter("Requerido")==null?"0":request.getParameter("Requerido");
		// String comentario			= request.getParameter("Comentario")==null?"-":request.getParameter("Comentario");
		String comentario 			= "From Day Student to Boarding Student : "+fecha;	 
		String comentarioB			= request.getParameter("ComentarioB")==null?"-":request.getParameter("ComentarioB");
		String acomodo 				= request.getParameter("Acomodo")==null?"0":request.getParameter("Acomodo");
		String mensaje				= "-";				
		LogOperacion log 	= new LogOperacion();
		log.setTabla("RES_DATOS");
		log.setIp(request.getRemoteAddr());
		log.setUsuario(codigoPersonal);
		log.setDatos(codigoAlumno+"-Boarding Student-"+dormitorio);
		
		ResComentario resComentario = new ResComentario(); 
		if (alumAcademicoDao.existeReg(codigoAlumno)){
			log.setOperacion("UPDATE");
			if (alumAcademicoDao.updateResidencia(codigoAlumno, "I", dormitorio, requerido, acomodo) && logOperacionDao.insertReg(log)){
				String folioCom = resComentarioDao.maximoReg(codigoAlumno);
				resComentario.setCodigoPersonal(codigoAlumno);
				resComentario.setFolio(folioCom);
				resComentario.setResidenciaId("I");	
				resComentario.setComentario(comentario);	
				resComentario.setComentarioB(comentarioB);			
				if (resComentarioDao.existeReg(codigoAlumno, folioCom)){					
					if (resComentarioDao.updateReg(resComentario)){					
						mensaje="Boarding data updated";
					}
				}else{					
					if (resComentarioDao.insertReg(resComentario)){						
						mensaje="Boarding data updated";
					}else{
						mensaje="Error saving comment";
					}
				}	
			}else{
				mensaje="Error saving boarding status";
			}
		}
		
		return "redirect:/residencia/residencia/residencia?Mensaje="+mensaje;
	}		
	
	@RequestMapping("/residencia/residencia/residencia")
	public String residenciaResidenciaResidencia(HttpServletRequest request, Model modelo){
		String codigoPersonal	= "";
		String codigoAlumno		= "";	
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();            
	    if (sesion != null){
	    	codigoPersonal	= (String) sesion.getAttribute("codigoPersonal");
	    	codigoAlumno	= (String) sesion.getAttribute("codigoAlumno");	
	    }		
		
		String residencia	= "";
		String requerido 	= "";
		
		if(alumAcademicoDao.existeReg(codigoAlumno)) {
			residencia = alumAcademicoDao.getResidencia(codigoAlumno);
			requerido = alumAcademicoDao.getRequerido(codigoAlumno);
		}
		
		String usuario				= "";
		String fecha				= "";	
		String inscrito				= "";
		String comentario 			= "";
		
		String folioCom 			= resComentarioDao.maximoRegi(codigoAlumno, residencia);
		ResComentario resComentario = new ResComentario();
		resComentario 				= resComentarioDao.mapeaRegFolio(codigoAlumno, folioCom);
		
		int numAccion 				= 0;	
		if (request.getParameter("Accion")!=null) numAccion = Integer.parseInt(request.getParameter("Accion"));	
		
		if (alumPersonalDao.esInscrito(codigoAlumno)){ 
			inscrito = "Enrolled"; 
		}else{ 
			inscrito = "Not enrolled"; 
		}
		
		ResDatos dato = new ResDatos();
		
		dato.setMatricula(codigoAlumno);
		if(resDatosDao.existeReg(codigoAlumno)){
			dato = resDatosDao.mapeaRegId(codigoAlumno);
			usuario = dato.getUsuario();
			fecha = dato.getFecha();			
		}else{		
		 	fecha	= aca.util.Fecha.getHoy();
		 	usuario = codigoPersonal;
		}
		
		AlumDireccion direccion = new AlumDireccion();
		AlumUbicacion ubicacion = new AlumUbicacion();
		AlumAcademico academico = new AlumAcademico();

		String acomodoId 	= "";
		String recogidaId 	= "";
		
		if(alumDireccionDao.existeReg(codigoAlumno)) {
			direccion = alumDireccionDao.mapeaRegId(codigoAlumno);
		}

		if(alumUbicacionDao.existeReg(codigoAlumno)) {
			ubicacion = alumUbicacionDao.mapeaRegId(codigoAlumno);
			recogidaId = ubicacion.getRecogidaId();
		}

		if(alumAcademicoDao.existeReg(codigoAlumno)) {
			academico = alumAcademicoDao.mapeaRegId(codigoAlumno);
			acomodoId = academico.getAcomodoId();
		}
		
		List<ResRazon> lisRazon = resRazonDao.getListAll("ORDER BY 2");
		
		String nombreAlumno 	= "";
		String nombrePais 		= "";
		String nombreCarrera 	= "";
		String planId		 	= "";

		if(alumPersonalDao.existeAlumno(codigoAlumno)) {
			nombreAlumno 	= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
			nombrePais 		= catPaisDao.getNombrePais(alumPersonalDao.getPaisId(codigoAlumno));
			nombreCarrera 	= alumPersonalDao.getCarrera(codigoAlumno);
			planId 			= alumPersonalDao.getPlanActivo(codigoAlumno);
		}
		
		String nombreCiudad = catCiudadDao.getNombreCiudad(direccion.getPaisId(), direccion.getEstadoId(), direccion.getCiudadId());
		String dormitorio 	= "";
		
		if(!codigoAlumno.subSequence(0, 2).equals("98")) {			
			dormitorio = alumAcademicoDao.getDormi(codigoAlumno);
		}

		List<CargaAlumno> lista = cargaAlumnoDao.lisCargasActivasPorModo(codigoAlumno, "'1'", "P");
		
		boolean posibleExternado = true;
		
		if(lista.size() >= 1) {
			posibleExternado = false;
		}
		
		int edad = 0;
		if(alumPersonalDao.existeAlumno(codigoAlumno)) {
			edad = alumPersonalDao.getEdad(codigoAlumno);
		}
		
		String accesos = accesoDao.getAccesos(codigoPersonal);
		String carrera = alumPlanDao.getCarreraId(codigoAlumno);
		
		boolean accesoCarrera = false;
		
		if(accesos.contains(carrera)) {
			accesoCarrera = true;
		}
		
		String sexo = alumPersonalDao.getGenero(codigoAlumno);
		
		List<IntDormitorio> lisDormitorios = intDormitorioDao.getListPorSexo(sexo, "");

		String acomodoS = "";
		if(numAccion == 0) acomodoS = residencia;
		if(numAccion == 1) acomodoS = "E";
		if(numAccion == 2) acomodoS = "I";

		List<CatAcomodo> lisAcomodos = catAcomodoDao.getListTipo(acomodoS);

		List<CatRecogida> lisRecogidas = catRecogidaDao.getListAll(" ORDER BY RECOGIDA_ID");
		
		modelo.addAttribute("codigoPersonal", codigoPersonal);
		modelo.addAttribute("codigoAlumno", codigoAlumno);
		modelo.addAttribute("residencia", residencia);
		modelo.addAttribute("requerido", requerido);
		modelo.addAttribute("fecha", fecha);
		modelo.addAttribute("inscrito", inscrito);		
		modelo.addAttribute("direccion", direccion);
		modelo.addAttribute("ubicacion", ubicacion);
		modelo.addAttribute("numAccion", numAccion);
		modelo.addAttribute("dato", dato);
		modelo.addAttribute("lisRazon", lisRazon);
		modelo.addAttribute("usuario", usuario);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("nombrePais", nombrePais);
		modelo.addAttribute("nombreCarrera", nombreCarrera);
		modelo.addAttribute("dormitorio", dormitorio);
		modelo.addAttribute("nombreCiudad", nombreCiudad);
		modelo.addAttribute("posibleExternado", posibleExternado);
		modelo.addAttribute("resComentario", resComentario);
		modelo.addAttribute("edad", edad);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("accesoCarrera", accesoCarrera);
		modelo.addAttribute("lisDormitorios", lisDormitorios);
		modelo.addAttribute("lisAcomodos", lisAcomodos);
		modelo.addAttribute("lisRecogidas", lisRecogidas);
		modelo.addAttribute("acomodoId", acomodoId);
		modelo.addAttribute("recogidaId", recogidaId);

		return "residencia/residencia/residencia";
	}		

}