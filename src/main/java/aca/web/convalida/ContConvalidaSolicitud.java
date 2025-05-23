package aca.web.convalida;

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

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumAcademico;
import aca.alumno.spring.AlumAcademicoDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.candado.spring.CandAlumno;
import aca.candado.spring.CandAlumnoDao;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatModalidadDao;
import aca.conva.spring.ConvEvento;
import aca.conva.spring.ConvEventoDao;
import aca.conva.spring.ConvHistorial;
import aca.conva.spring.ConvHistorialDao;
import aca.conva.spring.ConvMateria;
import aca.conva.spring.ConvMateriaDao;
import aca.conva.spring.ConvPeriodo;
import aca.conva.spring.ConvPeriodoDao;
import aca.kardex.spring.KrdxCursoAct;
import aca.kardex.spring.KrdxCursoActDao;
import aca.kardex.spring.KrdxCursoImp;
import aca.kardex.spring.KrdxCursoImpDao;
import aca.parametros.spring.Parametros;
import aca.parametros.spring.ParametrosDao;
import aca.plan.spring.MapaCurso;
import aca.plan.spring.MapaCursoDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.Maestros;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContConvalidaSolicitud {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;	
		
	@Autowired	
	private ConvEventoDao convEventoDao; 
	
	@Autowired	
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired	
	private AlumAcademicoDao alumAcademicoDao;
	
	@Autowired	
	private MapaCursoDao  mapaCursoDao; 
	
	@Autowired	
	private ConvMateriaDao  convMateriaDao;
	
	@Autowired	
	private CatCarreraDao  catCarreraDao;
	
	@Autowired	
	private ConvHistorialDao  convHistorialDao;
	
	@Autowired	
	private MaestrosDao  maestrosDao; 
	
	@Autowired	
	private ParametrosDao  parametrosDao; 
	
	@Autowired	
	private MapaPlanDao  mapaPlanDao;	
	
	@Autowired	
	private KrdxCursoImpDao  krdxCursoImpDao;	
	
	@Autowired	
	private KrdxCursoActDao  krdxCursoActDao;	
	
	@Autowired	
	private AccesoDao accesoDao;	
	
	@Autowired	
	private CandAlumnoDao candAlumnoDao;	
	
	@Autowired	
	private ConvPeriodoDao convPeriodoDao;	
	
	@Autowired	
	private CatModalidadDao catModalidadDao;	
	
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/convalida/solicitud/accionCiclo")
	public String convalidaSolicitudAccionCiclo(HttpServletRequest request, Model modelo){

		String codigoPersonal 		= "0";
		String codigoAlumno			= "0";
		
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion!=null){		
			codigoPersonal 	= (String)sesion.getAttribute("codigoPersonal");				
			codigoAlumno 	= (String)sesion.getAttribute("codigoAlumno");
		}
		
		String planId			= request.getParameter("PlanId")==null?alumPersonalDao.getPlanActivo(codigoAlumno):request.getParameter("PlanId");
		String carreraId		= "";
		String nombreCarrera 	= "";
		String nombreAlumno		= "";
		String convalidacionId	= request.getParameter("convalidacionId");
		String ciclosParam    	= request.getParameter("CicloParam")==null?"-":request.getParameter("CicloParam");
		String planActivo		= alumPersonalDao.getPlanActivo(codigoAlumno);
		
		List<MapaCurso> lisMaterias	= mapaCursoDao.getListConvalida(planId, codigoAlumno,"ORDER BY CICLO, NOMBRE_CURSO" );
		
		String buscaCiclo		= "-";
		
		for(int i = 1; i < ciclosParam.length(); i++){
			String cicloSelect = request.getParameter("ClicoId"+i)==null?"0":request.getParameter("ClicoId"+i);
			if(!cicloSelect.equals("0")){
				buscaCiclo += cicloSelect+"-";
			}
		}
		
		ConvMateria materia = new ConvMateria();
		
		if(request.getParameter("Accion")!=null){
			if(request.getParameter("Accion").equals("1")){
				for(int i= 0; i < lisMaterias.size();  i++){	
					if(buscaCiclo.contains(lisMaterias.get(i).getCiclo())){
						materia.setConvalidacionId(convalidacionId);
						materia.setCursoId(lisMaterias.get(i).getCursoId());
						materia.setFecha(aca.util.Fecha.getHoy());
						materia.setTipo("E");
						materia.setEstado("-");
						materia.setNota_O("AC");
						materia.setCreditos_O("0");
						materia.setfNota(aca.util.Fecha.getHoy());
						materia.setMateria_O(lisMaterias.get(i).getNombreCurso());
						materia.setFolio("0");						
						if(convMateriaDao.existeReg(convalidacionId, materia.getCursoId())){
							convMateriaDao.updateReg(materia);
						}else{ 
							convMateriaDao.insertReg(materia);
						}	
					}			
				}		
				lisMaterias	= mapaCursoDao.getListConvalida(planId,codigoAlumno,"ORDER BY CICLO, NOMBRE_CURSO" );
			}
		}
		// utilizacion de las variables
		
		nombreAlumno	= alumPersonalDao.getNombre(codigoAlumno, "NOMBRE");
		carreraId		= alumPersonalDao.getCarreraId(alumPersonalDao.getPlanActivo(codigoAlumno) );	
		nombreCarrera	= catCarreraDao.getNombreCarrera(carreraId);
		
		modelo.addAttribute("codigoPersonal", codigoPersonal);
		modelo.addAttribute("codigoAlumno", codigoAlumno);
		modelo.addAttribute("carreraId", carreraId);
		modelo.addAttribute("nombreCarrera", nombreCarrera);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("lisMaterias", lisMaterias);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("planActivo", planActivo);
		
		return "convalida/solicitud/accionCiclo";
	}	
	
	@RequestMapping("/convalida/solicitud/accion")
	public String convalidaSolicitudAccion(HttpServletRequest request, Model modelo){
		
		String codigoPersonal 		= "0";
		String codigoAlumno			= "0";
		
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion!=null){		
			codigoPersonal 	= (String)sesion.getAttribute("codigoPersonal");				
			codigoAlumno 	= (String)sesion.getAttribute("codigoAlumno");
		}
		String planId			= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String convalidacionId	= request.getParameter("convalidacionId")==null?"0":request.getParameter("convalidacionId");
		String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		
		String carreraId		= alumPersonalDao.getCarreraId(alumPersonalDao.getPlanActivo(codigoAlumno) );
		String nombreCarrera 	= catCarreraDao.getNombreCarrera(carreraId);
		String nombreAlumno	 	= alumPersonalDao.getNombre(codigoAlumno, "NOMBRE");		 
		String check			= "-";
		String cursoId			= "-";		
		
		List<MapaCurso> lisMaterias	= mapaCursoDao.getListConvalida(planId,codigoAlumno,"ORDER BY CICLO, NOMBRE_CURSO" );
		
		ConvMateria convMateria = new ConvMateria();
		if(convMateriaDao.existeReg(convalidacionId, cursoId)){
			convMateria = convMateriaDao.mapeaRegId(convalidacionId, cursoId);
		}
		
		if(accion.equals("1")){			
			for(MapaCurso curso:  lisMaterias){
				if(request.getParameter("check-"+curso.getCursoId()) != null){					
					cursoId			= request.getParameter("curso-"+curso.getCursoId());										
					convMateria.setConvalidacionId(convalidacionId);
					convMateria.setCursoId(cursoId);
					convMateria.setFecha(aca.util.Fecha.getHoy());
					convMateria.setTipo("E");
					convMateria.setEstado("-");
					convMateria.setFolio("0");						
					if(convMateriaDao.existeReg(convalidacionId, cursoId)){
						convMateriaDao.updateReg(convMateria);
					}else{ 
						convMateriaDao.insertReg(convMateria);
					}	
				}			
			}		
			lisMaterias	= mapaCursoDao.getListConvalida(planId,codigoAlumno,"ORDER BY CICLO, NOMBRE_CURSO" );
		}		
		
		
		// utilizacion de las variables
		
		if(planId==null){  // si el planId viene igual  a null le asignamos el plan de alumno para que muestre el las materias del plan
			planId = alumPersonalDao.getPlanActivo(codigoAlumno);	
		}	
		
		String planActivo = alumPersonalDao.getPlanActivo(codigoAlumno);
		
		modelo.addAttribute("codigoPersonal", codigoPersonal);
		modelo.addAttribute("codigoAlumno", codigoAlumno);
		modelo.addAttribute("nombreCarrera", nombreCarrera);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		//modelo.addAttribute("check", check);
		//modelo.addAttribute("cursoId", cursoId);
		modelo.addAttribute("lisMaterias", lisMaterias);
		modelo.addAttribute("planActivo", planActivo);
		
		return "convalida/solicitud/accion";
	}	
	
	@RequestMapping("/convalida/solicitud/dictamen")
	public String convalidaSolicitudDictamen(HttpServletRequest request, Model modelo){
		
		String codigoAlumno			= "0";
		String alumnoNombre			= "-";
		HttpSession sesion 			= ((HttpServletRequest)request).getSession();
		if (sesion!=null){							
			codigoAlumno 			= (String)sesion.getAttribute("codigoAlumno");
			alumnoNombre 			= alumPersonalDao.getNombre(codigoAlumno, "NOMBRE");
		}
		
		String convalidacionId		= request.getParameter("ConvalidacionId")==null?"0":request.getParameter("ConvalidacionId");		
		ConvEvento convEvento 		= convEventoDao.mapeaRegId(convalidacionId);
		String carreraId			= mapaPlanDao.getCarreraId(convEvento.getPlanId());
		String carreraNombre 		= mapaPlanDao.getCarreraSe(convEvento.getPlanId());
		
		List<ConvMateria> lisMaterias 				= convMateriaDao.getList(convalidacionId, "ORDER BY CICLO_CURSO(CURSO_ID), ENOC.NOMBRE_MATERIA(CURSO_ID)");
		HashMap<String,MapaCurso> mapaMaterias 		= mapaCursoDao.getAllMapaCursos("");
		HashMap<String,KrdxCursoImp> mapaImportados	= krdxCursoImpDao.mapaCursosAlumno(codigoAlumno);
		
		modelo.addAttribute("codigoAlumno", codigoAlumno);	
		modelo.addAttribute("alumnoNombre", alumnoNombre);
		modelo.addAttribute("carreraId", carreraId);		
		modelo.addAttribute("carreraNombre", carreraNombre);
		modelo.addAttribute("convEvento", convEvento);
		
		modelo.addAttribute("lisMaterias", lisMaterias);
		modelo.addAttribute("mapaMaterias", mapaMaterias);
		modelo.addAttribute("mapaImportados", mapaImportados);
		
		return "convalida/solicitud/dictamen";
	}	
	
	@RequestMapping("/convalida/solicitud/grabarDictamen")
	public String convalidaSolicitudGrabarDictamen(HttpServletRequest request, Model modelo){
		
		String codigoPersonal		= "0";
		String codigoAlumno			= "0";		
		HttpSession sesion 			= ((HttpServletRequest)request).getSession();
		if (sesion!=null){							
			codigoAlumno 			= (String)sesion.getAttribute("codigoAlumno");
			codigoPersonal 			= (String)sesion.getAttribute("codigoPersonal");
		}
		
		String convalidacionId		= request.getParameter("ConvalidacionId")==null?"0":request.getParameter("ConvalidacionId");
		String accion 				= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		
		ConvEvento convEvento 		= convEventoDao.mapeaRegId(convalidacionId);
		
		List<ConvMateria>lisMaterias 	= convMateriaDao.getListCicloNombre(convalidacionId," ORDER BY CICLO_CURSO(CURSO_ID), CONVALIDACION_ID");		
		
		String mensaje 	= "-";
		boolean guardo 	= false;
		int row = -1;
		if( accion.equals("1") || accion.equals("2")){			
			for(ConvMateria convMateria : lisMaterias){
				row++;
				String curso = request.getParameter("check"+row)==null?"-":request.getParameter("check"+row);
				convMateria.setConvalidacionId(convalidacionId);
				convMateria.setTipo(request.getParameter("Tipo"+row));
				convMateria.setCreditos_O(request.getParameter("Cred"+row));
				convMateria.setNota_O(request.getParameter("FCal"+row));
				if(curso != null){
					convMateria.setEstado("S");
				}else{
					convMateria.setEstado("N");
				}
				
				guardo &= convMateriaDao.updateReg(convMateria);
			}		
			if(guardo == true){
				if (accion.equals("2")){					
					convEvento.setEstado("C");
					convEvento.setFecha(aca.util.Fecha.getHoy());
					if (convEventoDao.updateReg(convEvento) && !convHistorialDao.existeEstado(convalidacionId, "C")){
						ConvHistorial convHistorial = new ConvHistorial();
						convHistorial.setConvalidacionId(convalidacionId);
						convHistorial.setFolio(convHistorialDao.getMaxReg(convalidacionId));
						convHistorial.setFecha(aca.util.Fecha.getHoy());
						convHistorial.setUsuario(codigoPersonal);
						convHistorial.setEstado("C");
						if (convHistorialDao.insertReg(convHistorial)){
							mensaje = "Se guardó satisfactoriamente";
						}else{
							mensaje = "No guardó el historial";
						}
					}else{
					}
				}else{
					mensaje = "Se guardó satisfactoriamente";
				}
			}	
		}		
		
		row = -1;
		if(accion.equals("1") || accion.equals("2")){
			for(ConvMateria convMateria : lisMaterias){
				row++;
				String estado = request.getParameter("check"+row)==null?"X":request.getParameter("check"+row);
				if(!estado.equals("X")){
					
					String maximo 			= krdxCursoImpDao.maximoReg(codigoAlumno);
					KrdxCursoImp cursoImp 	= new KrdxCursoImp();
					cursoImp.setCodigoPersonal(codigoAlumno);
					cursoImp.setFolio(convMateria.getFolio());
					cursoImp.setFCreada(request.getParameter("FFinal"+row)==null||request.getParameter("FFinal"+row).equals("null")?"":request.getParameter("FFinal"+row));
					cursoImp.setCursoId(convMateria.getCursoId());
					cursoImp.setCursoId2(convMateria.getCursoId());
					
					//Tipo de la convalidación
					String convalida = request.getParameter("Tipo"+row).equals("E")?"S":"I";				
					cursoImp.setConvalidacion(convalida);
					
					cursoImp.setTitulo("N");
					cursoImp.setOptativa("N");
					cursoImp.setTipoCalId("1");
					cursoImp.setNotaExtra("0");
					
					if(request.getParameter("Tipo"+row).equals("I")){
						cursoImp.setNota(request.getParameter("FCal"+row)==null?"0":request.getParameter("FCal"+row));
						cursoImp.setNotaConva("0");
					}else{
						cursoImp.setNota("0");
						cursoImp.setNotaConva(request.getParameter("FCal"+row));
					}
					cursoImp.setOptativaNombre("*");
					cursoImp.setObservaciones("");
					cursoImp.setUsuario(codigoPersonal);
					cursoImp.setFecha(aca.util.Fecha.getHoy());
					
					// Nuevos valores en datos de las convalidaciones 
					convMateria.setTipo(request.getParameter("Tipo"+row));
					convMateria.setCreditos_O(request.getParameter("Cred"+row)==null?"0":request.getParameter("Cred"+row));
					convMateria.setNota_O(request.getParameter("FCal"+row));
					convMateria.setfNota(request.getParameter("FFinal"+row)==null||request.getParameter("FFinal"+row).equals("null")?"":request.getParameter("FFinal"+row));
					
					if(!krdxCursoImpDao.existeReg(codigoAlumno, convMateria.getFolio())){
						convMateria.setFolio("0");
						if(accion.equals("2") && !cursoImp.getFCreada().equals("")){
							convMateria.setFolio(maximo);
							cursoImp.setFolio(maximo);
							if(!krdxCursoImpDao.insertReg(cursoImp)){
								System.out.println("Ocurrió un error al grabar en krdxCursoImp!");
							}
						}
						convMateria.setConvalidacionId(convalidacionId);
						convMateriaDao.updateReg(convMateria);
					}
				}				
			}
		}
		
		return "redirect:/convalida/solicitud/dictamen?ConvalidacionId="+convalidacionId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/convalida/solicitud/borrarDictamen")
	public String convalidaSolicitudBorrarDictamen(HttpServletRequest request, Model modelo){
		
		String codigoAlumno			= "0";		
		HttpSession sesion 			= ((HttpServletRequest)request).getSession();
		if (sesion != null){							
			codigoAlumno 			= (String)sesion.getAttribute("codigoAlumno");			
		}
		
		String convalidacionId		= request.getParameter("ConvalidacionId")==null?"0":request.getParameter("ConvalidacionId");
		String mensaje 				= "";
		
		String folio 				= request.getParameter("Folio")==null?"":request.getParameter("Folio");
		String cursoId 				= request.getParameter("CursoId")==null?"":request.getParameter("CursoId");
		boolean borrar = true;		
		
		if (krdxCursoImpDao.existeReg(codigoAlumno, folio)){			
			if(!krdxCursoImpDao.deleteReg(codigoAlumno, folio)) borrar = false;
		}	
		if (borrar){
			ConvMateria convMateria = convMateriaDao.mapeaRegId(convalidacionId, cursoId);
			convMateria.setFolio("0");			
			if(convMateriaDao.updateReg(convMateria)){
				mensaje = "¡ Convalidación modificada !";
			}else{				
				mensaje = "¡Ocurrió un error al modificar el folio en convMateria!";
			}	
		}else{
			mensaje = "¡Ocurrió un error al borrar la nota de convalidación!";
		}
		
		return "redirect:/convalida/solicitud/dictamen?ConvalidacionId="+convalidacionId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/convalida/solicitud/editar")
	public String convalidaSolicitudEditar(HttpServletRequest request, Model modelo){

		String codigoPersonal 		= "0";
		String codigoAlumno			= "0";
		
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion!=null){		
			codigoPersonal 	= (String)sesion.getAttribute("codigoPersonal");				
			codigoAlumno 	= (String)sesion.getAttribute("codigoAlumno");
		}
		
		String convalidacionId	= request.getParameter("ConvalidacionId")==null?"0":request.getParameter("ConvalidacionId");
		String accion			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		String nombreAlumno		= alumPersonalDao.getNombre(codigoAlumno, "NOMBRE");
		
		String mensaje			= "-";
		String colorMensaje		= "-";
		String estado 			= "-"; 
		
		ConvEvento evento = new ConvEvento();
		if(convEventoDao.existeCovaliadacion(convalidacionId)){
			evento = convEventoDao.mapeaRegId(convalidacionId);
		}
		
		if (accion.equals("1")){
			String dictamen 	= request.getParameter("Dictamen")==null?"-":request.getParameter("Dictamen");
			String comentario 	= request.getParameter("Comentario")==null?"-":request.getParameter("Comentario");
			if (convEventoDao.updateDicCom(dictamen, comentario, convalidacionId)){
				mensaje = "¡ Grabado !";
				colorMensaje = "alert-info";
				convEventoDao.mapeaRegId(convalidacionId);
			}else{
				mensaje = "¡ No grabó !";
				colorMensaje = "alert-danger";
			}
		}
		
		// Nombre del estado de la convalidación
		estado = evento.getEstado();
		if (estado.equals("S"))
			estado = "Solicitud";
		else if (estado.equals("C")) 
			estado = "Confirmado";
		else if (estado.equals("G")) 
			estado = "Gradual";
		else if (estado.equals("F")) 
			estado = "Sin Documento";
		else if (estado.equals("D")) 
			estado = "Sin Pago";
		else if (estado.equals("A")) 
			estado = "Aceptado";
		else if (estado.equals("T"))
			estado = "Terminado";
		else if (estado.equals("X"))
			estado = "Cancelado";
		else if (estado.equals("R"))
			estado = "Registrado";
		else
			estado = "-";
		
		modelo.addAttribute("codigoPersonal", codigoPersonal);
		modelo.addAttribute("codigoAlumno", codigoAlumno);
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("colorMensaje", colorMensaje);
		modelo.addAttribute("estado", estado);
		modelo.addAttribute("evento", evento);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		
		return "convalida/solicitud/editar";
	}	
	
	@RequestMapping("/convalida/solicitud/formatoExternaCiclos")
	public String convalidaSolicitudFormatoExternaCiclos(HttpServletRequest request, Model modelo){
		String codigoAlumno		= "0";
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){							
			codigoAlumno 		= (String)sesion.getAttribute("codigoAlumno");
		}
		
		String convalidacionId	= request.getParameter("ConvalidacionId")==null?"0":request.getParameter("ConvalidacionId");
		
		Parametros parametros 		= parametrosDao.mapeaRegId("1");
		ConvEvento convEvento 		= convEventoDao.mapeaRegId(convalidacionId);
		AlumPersonal alumPersonal 	= alumPersonalDao.mapeaRegId(codigoAlumno);
		
		String modalidad 		= alumAcademicoDao.getModalidadId(codigoAlumno);
		String planNombre 			= mapaPlanDao.getNombrePlan(convEvento.getPlanId());
		
		List<ConvMateria> lisMaterias 			= convMateriaDao.getList(convalidacionId, "ORDER BY CICLO_CURSO(CURSO_ID), ENOC.NOMBRE_MATERIA(CURSO_ID)");
		HashMap<String,MapaCurso> mapaMaterias 	= mapaCursoDao.getAllMapaCursos("");
		
		modelo.addAttribute("modalidad", modalidad);
		modelo.addAttribute("planNombre", planNombre);
		modelo.addAttribute("parametros", parametros);
		modelo.addAttribute("convEvento", convEvento);
		modelo.addAttribute("alumPersonal", alumPersonal);
		
		modelo.addAttribute("lisMaterias", lisMaterias);
		modelo.addAttribute("mapaMaterias", mapaMaterias);
		
		return "convalida/solicitud/formatoExternaCiclos";
	}	
	
	@RequestMapping("/convalida/solicitud/formatoExterna")
	public String convalidaSolicitudFormatoExterna(HttpServletRequest request, Model modelo){
		
		String codigoAlumno		= "0";
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){							
			codigoAlumno 		= (String)sesion.getAttribute("codigoAlumno");
		}
		
		String convalidacionId	= request.getParameter("ConvalidacionId")==null?"0":request.getParameter("ConvalidacionId");
		
		Parametros parametros 		= parametrosDao.mapeaRegId("1");
		ConvEvento convEvento 		= convEventoDao.mapeaRegId(convalidacionId);
		AlumPersonal alumPersonal 	= alumPersonalDao.mapeaRegId(codigoAlumno);
		
		String modalidad 		= alumAcademicoDao.getModalidadId(codigoAlumno);
		String planNombre 			= mapaPlanDao.getNombrePlan(convEvento.getPlanId());
		
		List<ConvMateria> lisMaterias 			= convMateriaDao.getList(convalidacionId, "ORDER BY CICLO_CURSO(CURSO_ID), ENOC.NOMBRE_MATERIA(CURSO_ID)");
		HashMap<String,MapaCurso> mapaMaterias 	= mapaCursoDao.getAllMapaCursos("");
		
		modelo.addAttribute("modalidad", modalidad);
		modelo.addAttribute("planNombre", planNombre);
		modelo.addAttribute("parametros", parametros);
		modelo.addAttribute("convEvento", convEvento);
		modelo.addAttribute("alumPersonal", alumPersonal);
		
		modelo.addAttribute("lisMaterias", lisMaterias);
		modelo.addAttribute("mapaMaterias", mapaMaterias);
		
		return "convalida/solicitud/formatoExterna";
	}	

	@RequestMapping("/convalida/solicitud/formato")
	public String convalidaSolicitudFormato(HttpServletRequest request, Model modelo){
		
		String codigoAlumno		= "0";
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion!=null){							
			codigoAlumno 	= (String)sesion.getAttribute("codigoAlumno");
		}
		
		String convalidacionId		= request.getParameter("ConvalidacionId")==null?"0":request.getParameter("ConvalidacionId");
		
		Parametros parametros 		= parametrosDao.mapeaRegId("1");	
		ConvEvento convEvento 		= convEventoDao.mapeaRegId(convalidacionId);		
		MapaPlan mapaPlan 			= mapaPlanDao.mapeaRegId(convEvento.getPlanId());
		AlumPersonal alumPersonal 	= alumPersonalDao.mapeaRegId(codigoAlumno);
		String modalidad 			= alumAcademicoDao.getModalidadId(codigoAlumno);
		String planNombre 			= mapaPlanDao.getNombrePlan(convEvento.getPlanId());
		
		List<ConvMateria> lisMaterias 			= convMateriaDao.getListPorEstado( convalidacionId, "'S','R'", "ORDER BY CICLO_CURSO(CURSO_ID), ENOC.NOMBRE_MATERIA(CURSO_ID)");		
		HashMap<String,MapaCurso> mapaMaterias	= mapaCursoDao.getAllMapaCursos("");
		
		modelo.addAttribute("parametros", parametros);
		modelo.addAttribute("convEvento", convEvento);
		modelo.addAttribute("mapaPlan", mapaPlan);
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("modalidad", modalidad);
		modelo.addAttribute("planNombre", planNombre);
		
		modelo.addAttribute("lisMaterias", lisMaterias);
		modelo.addAttribute("mapaMaterias", mapaMaterias);
		
		return "convalida/solicitud/formato";
	}	
	
	@RequestMapping("/convalida/solicitud/historial")
	public String convalidaSolicitudHistorial(HttpServletRequest request, Model modelo){

		String convalidacionId 	= request.getParameter("ConvalidacionId")==null?"0":request.getParameter("ConvalidacionId");
		ConvEvento convEvento 	= new ConvEvento();
		if(convEventoDao.existeCovaliadacion(convalidacionId)){
			convEvento = convEventoDao.mapeaRegId(convalidacionId);
		}
		String nombreAlumno 					= alumPersonalDao.getNombre(convEvento.getCodigoPersonal(), "NOMBRE");
		List<ConvHistorial> lisConv 			= convHistorialDao.getList(convalidacionId, " ORDER BY FOLIO");
		HashMap<String, Maestros> mapaMaestros	= maestrosDao.mapaMaestros();
		
		modelo.addAttribute("convEvento", convEvento);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("lisConv", lisConv);
		modelo.addAttribute("mapaMaestros", mapaMaestros);
		
		return "convalida/solicitud/historial";
	}	
	
	@RequestMapping("/convalida/solicitud/reporte")
	public String convalidaSolicitudReporte(HttpServletRequest request, Model modelo){
		
		String codigoAlumno			= "0";
		
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion!=null){							
			codigoAlumno 	= (String)sesion.getAttribute("codigoAlumno");
		}
		
		String accion			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		String convalidacionId	= request.getParameter("ConvalidacionId")==null?"0":request.getParameter("ConvalidacionId");	
		String estado			= request.getParameter("Estado")==null?"0":request.getParameter("Estado");		
		
		ConvEvento convEvento = new ConvEvento();
		if(convEventoDao.existeCovaliadacion(convalidacionId)){
			convEvento = convEventoDao.mapeaRegId(convalidacionId);
		}
	
		String institucion 		= request.getParameter("Institucion")==null?convEvento.getInstitucion():request.getParameter("Institucion");
		String programa 		= request.getParameter("Programa")==null?convEvento.getPrograma():request.getParameter("Programa");
		String periodo			= request.getParameter("Periodo")==null?convEvento.getPeriodo()==null?"-":convEvento.getPeriodo():request.getParameter("Periodo");
		
		String planId			= convEvento.getPlanId();	
		String nombrePlan		= mapaPlanDao.getNombrePlan(planId);
		String mensaje			= "";
		String alert			= "";
		
		List<ConvMateria> lisMaterias 				= convMateriaDao.getList(convalidacionId, "ORDER BY CICLO_CURSO(CURSO_ID), ENOC.NOMBRE_MATERIA(CURSO_ID)");		
		HashMap<String, MapaCurso> mapaCursos		= mapaCursoDao.getMapaCursos(planId,"");
		
		int guardadas = 0;	
		
		AlumPersonal alumPersonal = new AlumPersonal();
		if(alumPersonalDao.existeReg(codigoAlumno)){
			alumPersonal = alumPersonalDao.mapeaRegId(codigoAlumno);
		}

		String disable = "disabled";		
		if(estado.equals("S") || estado.equals("C")){
			disable = "";		
		}
		
		ConvMateria convMateria = new ConvMateria();
		
		if(accion.equals("1")){		
			convEvento.setInstitucion(institucion);
			convEvento.setPrograma(programa);
			convEvento.setPeriodo(periodo);
			
			if(convEventoDao.updateReg(convEvento)){			
				
				for(int i = 0; i<lisMaterias.size(); i++){
					convMateria = lisMaterias.get(i);
					// Si todos los campos tienen información intentar grabar
					if (!request.getParameter("materiaO"+i).equals("") && !request.getParameter("creditosO"+i).equals("") && !request.getParameter("notaO"+i).equals("") && !request.getParameter("FNota"+i).equals("") ){
						convMateria = lisMaterias.get(i);
						convMateria.setMateria_O(request.getParameter("materiaO"+i));
						convMateria.setCreditos_O(request.getParameter("creditosO"+i));
						convMateria.setNota_O(request.getParameter("notaO"+i));
						convMateria.setfNota(request.getParameter("FNota"+i));
						convMateria.setFolio("0");					
						if (convMateriaDao.updateReg(convMateria)){
							// Actualizado
							guardadas++;
						}
					}
				}
			}
					
			if(guardadas > 0){
				alert   = "success";
				mensaje = "Guardadas";
				lisMaterias = convMateriaDao.getList(convalidacionId, "ORDER BY CICLO_CURSO(CURSO_ID), ENOC.NOMBRE_MATERIA(CURSO_ID)");
			}else {
				alert   = "warning";
				mensaje = "No se guardó ninguna materia";		
			}		
		}		
		
		modelo.addAttribute("codigoAlumno", codigoAlumno);		
		modelo.addAttribute("convEvento", convEvento);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("nombrePlan", nombrePlan);
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("alert", alert);
		modelo.addAttribute("lisMaterias", lisMaterias);		
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("guardadas", guardadas);		
		modelo.addAttribute("disable", disable);			
		modelo.addAttribute("mapaCursos", mapaCursos);
		
		return "convalida/solicitud/reporte";
	}	
	
	@RequestMapping("/convalida/solicitud/solicitud")
	public String convalidaSolicitudSolicitud(HttpServletRequest request, Model modelo){
		String codigoAlumno		= "0";
		String codigoPersonal	= "0";
		
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion!=null){							
			codigoAlumno 	= (String)sesion.getAttribute("codigoAlumno");
			codigoPersonal 	= (String)sesion.getAttribute("codigoPersonal");
		}
		
		String cursoId			= request.getParameter("cursoId");
		String carreraId		= "";
		String planId			= alumPersonalDao.getPlanActivo(codigoAlumno); 
		String nombreCarrera 	= "";
		String nombreAlumno		= "";
		String escribir			= " ";
		String convalidacionId	= request.getParameter("convalidacionId")==null?"0":request.getParameter("convalidacionId");
		String comentarioNum	= request.getParameter("numeroCom")==null?"0":request.getParameter("numeroCom");
		String plan				= alumPersonalDao.getPlanActivo(codigoAlumno);
		if(comentarioNum == null || comentarioNum.equals(""))
			comentarioNum = "0";
		String comentario		= request.getParameter("comentario"+Integer.parseInt(comentarioNum));
		if(comentario == null || comentario.equals("null"))
			comentario = "";		
		String proceso 			= "";
		boolean esAdmin			= accesoDao.esAdministrador( codigoPersonal);
		//boolean esSuper			= aca.acceso.AccesoUtil.esSupervisor( codigoPersonal);
		
		int accion			= 0;
		
		List<ConvEvento> lisConv		= new ArrayList<ConvEvento>();
		List<KrdxCursoAct> lisActual	= new ArrayList<KrdxCursoAct>();
		
		ConvEvento convEvento		= new ConvEvento();
		ConvHistorial convHistorial	= new ConvHistorial();
		CandAlumno candAlumno		= new CandAlumno();
		
		if(request.getParameter("Accion")!=null) {
			accion =Integer.parseInt(request.getParameter("Accion"));
		}
		
		// utilizacion de las variables
		switch(accion){
			case 1:{//Borrar
					convEvento.setConvalidacionId(convalidacionId);
					convHistorial.setConvalidacionId(convalidacionId);
					if(convEventoDao.deleteReg( convalidacionId) && convHistorialDao.deleteAllRegs( convalidacionId))
						escribir = "Convalidacion Eliminada";
				}break;
			case 2:{//Nuevo por materia
					convEvento.setConvalidacionId(String.valueOf(convEventoDao.getMaxReg()));
					convEvento.setFecha(aca.util.Fecha.getHoy());
					convEvento.setUsuario(codigoPersonal);
					convEvento.setCodigoPersonal(codigoAlumno);
					convEvento.setPlanId(planId);
					convEvento.setEstado("S");
					convEvento.setComentario("");
					convEvento.setTipo("E");
					convEvento.setTipoConv("M");
					convEvento.setDictamen("-");
					convHistorial.setConvalidacionId(convEvento.getConvalidacionId());
					convHistorial.setFolio(convHistorialDao.getMaxReg( convEvento.getConvalidacionId()));
					convHistorial.setEstado("S");
					convHistorial.setFecha(aca.util.Fecha.getHoy());
					convHistorial.setUsuario(codigoPersonal);				
					if(planId.equals("Vacio") || planId.equals("") || planId == null){
						escribir = "No se pudo crear la convalidacion. No tienen Plan.";
					}else{
						if(convEventoDao.insertReg( convEvento)){
							if (convHistorialDao.insertReg( convHistorial)){
								escribir = "¡Grabado!";
							}else{
								escribir = "¡No se grabó la bitacora!";
							}						
						}else{
							escribir = "¡No se pudo grabar la solicitud!";
						}	
					}
				}break;
			case 3:{//Grabar comentario
					convEvento = convEventoDao.mapeaRegId(convalidacionId);
					convEvento.setComentario(comentario);
					convEventoDao.updateReg( convEvento);
				}break;
			case 4:{//Confirmar
					lisActual 		= krdxCursoActDao.getListAll( "WHERE CODIGO_PERSONAL = '"+codigoAlumno+"' AND CURSO_ID LIKE '"+plan+"%' AND TIPOCAL_ID IN ('1','2','4','5','6')");
					String estado = "P";
					convEvento = convEventoDao.mapeaRegId(convalidacionId);
					convEvento.setEstado(estado);
					convHistorial.setConvalidacionId(convalidacionId);
					convHistorial.setFolio(convHistorialDao.getMaxReg( convEvento.getConvalidacionId()));
					convHistorial.setFecha(aca.util.Fecha.getHoy());
					convHistorial.setUsuario(codigoPersonal);
					convHistorial.setEstado(estado);
					if(!convHistorialDao.insertReg( convHistorial))
						escribir = "No grabó correctamente";
					
					convEventoDao.updateReg( convEvento);
				}break;
			case 5:{//Borrar materia					
					if(convMateriaDao.deleteReg( convalidacionId,cursoId))
						escribir = "Materia Eliminada";
					else
						escribir = "Error eliminando la Materia";
				}break;
			case 6:{
				String estado			= request.getParameter("estado");
				
				if(estado==null)break;
				
				convEvento = convEventoDao.mapeaRegId( convalidacionId);
				convEvento.setEstado(estado);			
				convHistorial.setConvalidacionId(convalidacionId);
				convHistorial.setFolio(convHistorialDao.getMaxReg( convalidacionId));
				convHistorial.setFecha(aca.util.Fecha.getHoy());
				convHistorial.setUsuario(codigoPersonal);
				convHistorial.setEstado(estado);
				
				if(convEventoDao.updateReg( convEvento)){
					if(convHistorialDao.insertReg( convHistorial)){
						if (estado.equals("X")){
							candAlumno.setCodigoPersonal(codigoAlumno);
							candAlumno.setFolio(candAlumnoDao.maximoReg( codigoAlumno));
							candAlumno.setCandadoId("0801");
							candAlumno.setfCreado(aca.util.Fecha.getHoy());
							candAlumno.setUsAlta(codigoPersonal);
							candAlumno.setComentario("¡ Convalidación cancelada !");
							candAlumno.setEstado("A");
							if (candAlumnoDao.insertReg( candAlumno)){
								escribir = "¡ Modificado !";
							}else{
								escribir = "¡No se guardó la información(Candado)!";
							}
						}else if (estado.equals("F")){
							candAlumno.setCodigoPersonal(codigoAlumno);
							candAlumno.setFolio(candAlumnoDao.maximoReg( codigoAlumno));
							candAlumno.setCandadoId("0801");
							candAlumno.setfCreado(aca.util.Fecha.getHoy());
							candAlumno.setUsAlta(codigoPersonal);
							candAlumno.setComentario("¡ Sin documento !");
							candAlumno.setEstado("A");
							if (candAlumnoDao.insertReg( candAlumno)){
								escribir = "¡ Modificado !";
							}else{
								escribir = "¡No se guardó la información(Candado)!";
							}
						}else if (estado.equals("D")){
							candAlumno.setCodigoPersonal(codigoAlumno);
							candAlumno.setFolio(candAlumnoDao.maximoReg( codigoAlumno));
							candAlumno.setCandadoId("0801");
							candAlumno.setfCreado(aca.util.Fecha.getHoy());
							candAlumno.setUsAlta(codigoPersonal);
							candAlumno.setComentario("¡ Sin pago !");
							candAlumno.setEstado("A");
							if (candAlumnoDao.insertReg( candAlumno)){
								escribir = "¡ Modificado !";
							}else{
								escribir = "¡No se guardó la información(Candado)!";
							}						
						}else{
							escribir = "¡ Modificado !";
						}
						
					}else{
						escribir = "¡No se guardó la información(Historial)!";
					}
				}else{
					escribir = "¡No se guardó la información(Evento)!";
				}
				
			}break;
			
			//Borrar Candado
			case 7:{						
				candAlumno.setfBorrado(aca.util.Fecha.getHoy());
				candAlumno.setUsBaja(codigoPersonal);
				candAlumno.setEstado("I");
				candAlumno.setCodigoPersonal(codigoAlumno);
				candAlumno.setCandadoId("0801");				
				if (candAlumnoDao.removerCand( candAlumno)){
					escribir = "¡ Modificado !";
				}else{				
					escribir = "¡No se guardó la información(Candado)!";
				}
				
			}break;
			case 8:{//Nuevo por ciclo
				convEvento.setConvalidacionId(String.valueOf(convEventoDao.getMaxReg()));
				convEvento.setFecha(aca.util.Fecha.getHoy());
				convEvento.setUsuario(codigoPersonal);
				convEvento.setCodigoPersonal(codigoAlumno);
				convEvento.setPlanId(planId);
				convEvento.setEstado("S");
				convEvento.setComentario("");
				convEvento.setTipo("E");
				convEvento.setTipoConv("C");
				convEvento.setDictamen("-");
				convHistorial.setConvalidacionId(convEvento.getConvalidacionId());
				convHistorial.setFolio(convHistorialDao.getMaxReg( convEvento.getConvalidacionId()));
				convHistorial.setEstado("S");
				convHistorial.setFecha(aca.util.Fecha.getHoy());
				convHistorial.setUsuario(codigoPersonal);				
				if(planId.equals("Vacio") || planId.equals("") || planId == null){
					escribir = "No se pudo crear la convalidacion. No tienen Plan. ";
				}else{
					if(convEventoDao.insertReg( convEvento)){
						if (convHistorialDao.insertReg( convHistorial)){
							escribir = "¡Grabado!";
						}else{
							escribir = "¡No se grabó la bitacora!";
						}						
					}else{
						escribir = "¡No se pudo grabar la solicitud!";
					}	
				}
			}break;
			case 9:{
				
				//Borrar convalidacion y sus materias
				convEvento.setConvalidacionId(convalidacionId);
				convHistorial.setConvalidacionId(convalidacionId);
				if(convEventoDao.deleteReg( convalidacionId) && convHistorialDao.deleteAllRegs( convalidacionId)){
					
					escribir = "Convalidación Eliminada";
					if ( convMateriaDao.getNumMaterias( convalidacionId) >= 1 ){
						if(convMateriaDao.deleteMateriasDeConvalidacion(convalidacionId)){
							escribir += "y materias Eliminadas";
						}else{						
							escribir = "Error eliminando las Materias";
						}					
					}				
				}else{
					System.out.println("No borre convalidaciones ...");
				}		
				
			}break;
		}
		
		if(planId==null){  // si el planId viene igual  a null le asignamos el plan de alumno para que muestre el las materias del plan
			planId = alumPersonalDao.getPlanActivo(codigoAlumno);	
		}
		nombreAlumno	= alumPersonalDao.getNombre(codigoAlumno, "NOMBRE");
		carreraId		= alumPersonalDao.getCarreraId(alumPersonalDao.getPlanActivo(codigoAlumno) );	
		nombreCarrera	= catCarreraDao.getNombreCarrera(carreraId);
		lisConv			= convEventoDao.getSolicPorTipo(codigoAlumno, "'E','-'", "ORDER BY CONVALIDACION_ID");
		List <CandAlumno> lisCandados	= candAlumnoDao.getListCandAlumno(codigoAlumno,"08","A"," ORDER BY F_CREADO");
		
		AlumAcademico alumAcademico = new AlumAcademico();
		Acceso acceso = new Acceso();
		
		if(alumAcademicoDao.existeReg(codigoAlumno)) {
			alumAcademico = alumAcademicoDao.mapeaRegId(codigoAlumno);
		}
		acceso = accesoDao.mapeaRegId(codigoPersonal);
		
		String periodoId = convPeriodoDao.carreraHabilitada(carreraId);
		
		HashMap<String,String> mapCarreras 	= convPeriodoDao.getMapCarreras(periodoId);
		
		String nombreModalidad = catModalidadDao.getNombreModalidad(alumAcademico.getModalidadId());
		
		boolean esConvalidador = accesoDao.esConvalidador(codigoPersonal);
		
		HashMap<String, Float> mapaCreditos 		= new HashMap<String, Float>();
		HashMap<String, String> mapaTotalMaterias 	= convMateriaDao.mapaMateriasPorConvalidacion(codigoAlumno, "'-','S','N'");
		
		ConvPeriodo convPeriodo = new ConvPeriodo();
		if (!periodoId.equals("X")){		
			convPeriodo = convPeriodoDao.mapeaRegId(periodoId);
		}
		
		List<ConvMateria> lisMaterias	= new ArrayList<ConvMateria>();
		HashMap<String,List<ConvMateria>> mapaLisMaterias = new HashMap<String,List<ConvMateria>>();
		HashMap<String,String> mapaNombreMaterias = new HashMap<String,String>();
		for(ConvEvento evento : lisConv) {
			lisMaterias	= convMateriaDao.getListCicloNombre(evento.getConvalidacionId(),"ORDER BY ENOC.CICLO_CURSO(CURSO_ID), ENOC.NOMBRE_MATERIA(CURSO_ID)");
			mapaLisMaterias.put(evento.getConvalidacionId(), lisMaterias);
			for(ConvMateria materia : lisMaterias) {
				mapaNombreMaterias.put(materia.getCursoId(), mapaCursoDao.getNombreCurso(materia.getCursoId()));
				mapaCreditos.put(materia.getCursoId(), mapaCursoDao.getCreditosCurso(materia.getCursoId()));
			}
		}
		
		modelo.addAttribute("nombreAlumno", nombreAlumno);		
		modelo.addAttribute("nombreCarrera", nombreCarrera);		
		modelo.addAttribute("carreraId", carreraId);		
		modelo.addAttribute("periodoId", periodoId);		
		modelo.addAttribute("planId", planId);		
		modelo.addAttribute("escribir", escribir);		
		modelo.addAttribute("comentario", comentario);		
		modelo.addAttribute("plan", plan);		
		modelo.addAttribute("nombreModalidad", nombreModalidad);		
		modelo.addAttribute("esAdmin", esAdmin);		
		modelo.addAttribute("acceso", acceso);				
		modelo.addAttribute("esConvalidador", esConvalidador);		
		modelo.addAttribute("lisCandados", lisCandados);		
		modelo.addAttribute("mapaLisMaterias", mapaLisMaterias);		
		modelo.addAttribute("lisConv", lisConv);		
		modelo.addAttribute("lisActual", lisActual);		
		modelo.addAttribute("mapCarreras", mapCarreras);		
		modelo.addAttribute("mapaCreditos", mapaCreditos);		
		modelo.addAttribute("mapaNombreMaterias", mapaNombreMaterias);		
		modelo.addAttribute("alumAcademico", alumAcademico);		
		modelo.addAttribute("convPeriodo", convPeriodo);
		modelo.addAttribute("mapaTotalMaterias", mapaTotalMaterias);
		
		return "convalida/solicitud/solicitud";
	}	
	
}