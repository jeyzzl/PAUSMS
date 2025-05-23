package aca.web.convalida;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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
import aca.conva.spring.ConvPeriodoDao;
import aca.kardex.spring.KrdxCursoAct;
import aca.kardex.spring.KrdxCursoActDao;
import aca.kardex.spring.KrdxCursoImp;
import aca.kardex.spring.KrdxCursoImpDao;
import aca.parametros.spring.Parametros;
import aca.parametros.spring.ParametrosDao;
import aca.plan.spring.MapaCreditoDao;
import aca.plan.spring.MapaCurso;
import aca.plan.spring.MapaCursoDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.util.Fecha;
import aca.vista.spring.AlumnoCurso;
import aca.vista.spring.AlumnoCursoDao;
import aca.vista.spring.Maestros;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContConvalidaSolinterna {
	
	@Autowired
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired
	private AccesoDao accesoDao;
	
	@Autowired
	private ConvEventoDao convEventoDao;
	
	@Autowired
	private ConvHistorialDao convHistorialDao;
	
	@Autowired
	private ConvMateriaDao convMateriaDao;
	
	@Autowired
	private ConvPeriodoDao convPeriodoDao;
	
	@Autowired
	private KrdxCursoActDao krdxCursoActDao;
	
	@Autowired
	private CandAlumnoDao candAlumnoDao;
	
	@Autowired
	private CatCarreraDao catCarreraDao;
	
	@Autowired
	private AlumAcademicoDao alumAcademicoDao;
	
	@Autowired
	private MapaCursoDao mapaCursoDao;
	
	@Autowired
	private CatModalidadDao catModalidadDao;
	
	@Autowired
	private AlumnoCursoDao alumnoCursoDao;
	
	@Autowired
	private MapaPlanDao mapaPlanDao;
	
	@Autowired	
	private MaestrosDao maestrosDao;
	
	@Autowired	
	private ParametrosDao  parametrosDao;
	
	@Autowired	
	private MapaCreditoDao  mapaCreditoDao;
	
	@Autowired	
	private KrdxCursoImpDao  krdxCursoImpDao;	
	
	
	@RequestMapping("/convalida/sol_interna/editar")
	public String convalidasolInternaEditar(HttpServletRequest request, Model modelo){
		
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
		
		return "convalida/sol_interna/editar";
	}	

	@RequestMapping("/convalida/sol_interna/dictamen")
	public String convalidasolInternaDictamen(HttpServletRequest request, Model modelo){
		
		String convalidacionId		= request.getParameter("ConvalidacionId")==null?"0":request.getParameter("ConvalidacionId");
		String accion				= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		String checked				= request.getParameter("check");
		String cantidadMaterias		= request.getParameter("cantidadMaterias")==null?"0":request.getParameter("cantidadMaterias");
		String codigoPersonal		= "";
		String codigoAlumno			= "";
		String escribir				= "";
		String carreraId			= "";
		String nombreCarrera		= "";
		String convalida			= "";
		String planActivo			= "";
		
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion!=null){		
			codigoPersonal 	= (String)sesion.getAttribute("codigoPersonal");				
		}
		
		List<ConvMateria> lisMaterias = convMateriaDao.getListCicloNombre(convalidacionId,"ORDER BY CICLO_CURSO(CURSO_ID), CONVALIDACION_ID");
		
		ConvMateria convMateria 	= new ConvMateria();
		ConvEvento convEvento 		= new ConvEvento();
		ConvHistorial convHistorial = new ConvHistorial();
		KrdxCursoImp cursoImp		= new KrdxCursoImp();
		AlumPersonal alumPersonal	= new AlumPersonal();
		
		String curso = "";
		
		boolean guardo = true;
		if(checked != null && !checked.equals("")) {
			if(checked.equals("1")) {
				checked = "checked";
			}else {
				checked = "";
			}
		}
		
		if(cantidadMaterias.equals("")) {
			cantidadMaterias = "0";
		}
		
		if(accion.equals("1") || accion.equals("2")){
			for(int j = 0; j < lisMaterias.size(); j++){
				convMateria = lisMaterias.get(j);
				curso = request.getParameter("check"+j);
				convMateria.setConvalidacionId(convalidacionId);
				convMateria.setTipo("I");
				convMateria.setCreditos_O(request.getParameter("Cred"+j));
				convMateria.setNota_O(request.getParameter("FCal"+j));			
				if(curso != null){
					convMateria.setEstado("S");
				}else{
					convMateria.setEstado("N");
				}
				guardo &= convMateriaDao.updateReg(convMateria);
			}		
			
			if(guardo == true){
				if (accion.equals("2")){
					convEvento = convEventoDao.mapeaRegId(convMateria.getConvalidacionId());
					convEvento.setEstado("C");
					convEvento.setFecha(Fecha.getHoy());
					if (convEventoDao.updateReg( convEvento)&&!convHistorialDao.existeEstado( convMateria.getConvalidacionId(), "C")){
						convHistorial.setConvalidacionId(convMateria.getConvalidacionId());
						convHistorial.setFolio(convHistorialDao.getMaxReg( convMateria.getConvalidacionId()));
						convHistorial.setFecha(Fecha.getHoy());
						convHistorial.setUsuario(codigoPersonal);
						convHistorial.setEstado("C");
						if (convHistorialDao.insertReg( convHistorial)){
							escribir = "Se guardó satisfactoriamente";
						}else{
							escribir = "No guardó el historial";
						}
					}else{
					}
				}else{
					escribir = "Se guardó satisfactoriamente";
				}
			}	
		}
		lisMaterias = convMateriaDao.getListCicloNombre(convalidacionId,"ORDER BY CICLO_CURSO(CURSO_ID), CONVALIDACION_ID");	
		convEvento = convEventoDao.mapeaRegId(convalidacionId);
		codigoAlumno 	= convEvento.getCodigoPersonal();
		
		if(alumPersonalDao.existeAlumno(codigoAlumno)) {
			alumPersonal 	= alumPersonalDao.mapeaRegId(codigoAlumno);
			planActivo 		= alumPersonalDao.getPlanActivo(codigoAlumno);
		}
		
		carreraId		= alumPersonalDao.getCarreraId(alumPersonalDao.getPlanActivo(codigoAlumno));
		nombreCarrera	= catCarreraDao.getNombreCarrera(carreraId);
		
		if(accion.equals("1") || accion.equals("2")){
			for(int j=0; j<lisMaterias.size(); j++){
				String estado = request.getParameter("check"+j);
				if(estado!=null){
					convMateria = lisMaterias.get(j);
					
					String observaciones 	= request.getParameter("observaciones"+j);				
					String maximo 			= krdxCursoImpDao.maximoReg(codigoAlumno);
					
					cursoImp.setCodigoPersonal(codigoAlumno);
					cursoImp.setFolio(maximo);
					cursoImp.setFCreada(request.getParameter("FFinal"+j)==null||request.getParameter("FFinal"+j).equals("null")?"":request.getParameter("FFinal"+j));
					cursoImp.setCursoId(convMateria.getCursoId());
					cursoImp.setCursoId2(convMateria.getCursoId());

					convalida = "I";
					cursoImp.setConvalidacion(convalida);
					
					cursoImp.setTitulo("N");
					cursoImp.setOptativa("N");
					cursoImp.setTipoCalId("1");
					cursoImp.setNotaExtra("0");
					
					cursoImp.setNota(request.getParameter("FCal"+j)==null?"0":request.getParameter("FCal"+j));
					cursoImp.setNotaConva("0");
						
					cursoImp.setOptativaNombre("*");
					cursoImp.setObservaciones(observaciones);
					cursoImp.setUsuario(codigoPersonal);
					cursoImp.setFecha(aca.util.Fecha.getHoy());
					
					// Nuevos valores en datos de las convalidaciones 
					convMateria.setTipo("I");
					convMateria.setCreditos_O(request.getParameter("Cred"+j)==null?"0":request.getParameter("Cred"+j));
					convMateria.setNota_O(request.getParameter("FCal"+j));
					convMateria.setfNota(request.getParameter("FFinal"+j)==null||request.getParameter("FFinal"+j).equals("null")?"":request.getParameter("FFinal"+j));
				
					if(!krdxCursoImpDao.existeCursoIdReg(codigoAlumno,convMateria.getCursoId())){
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
		String borrarFolio = request.getParameter("Folio")==null?"":request.getParameter("Folio");
		String cursoId = request.getParameter("CursoId")==null?"":request.getParameter("CursoId");
		boolean borrar = true;
		if( accion.equals("3")){
			cursoImp.setFolio(borrarFolio);
			cursoImp.setCodigoPersonal(codigoAlumno);
			if (krdxCursoImpDao.existeReg(codigoAlumno,borrarFolio)){			
				if(!krdxCursoImpDao.deleteReg(codigoAlumno,borrarFolio)) {
					borrar = false;
				}
			}	
			if (borrar){
				convMateria = convMateriaDao.mapeaRegId(convalidacionId, cursoId);
				convMateria.setFolio("0");			
				if(!convMateriaDao.updateReg(convMateria)){
					System.out.println("Ocurrió un error al modificar el folio en convMateria!");
				}	
			}else{
				System.out.println("Ocurrió un error al borrar de krdxCursoImp!");
			}
		}
		
		lisMaterias = convMateriaDao.getListCicloNombre(convalidacionId,"ORDER BY CICLO_CURSO(CURSO_ID),CONVALIDACION_ID");
		
		HashMap<String, KrdxCursoImp> mapaCursosAlumno 	= krdxCursoImpDao.mapaCursosAlumno(codigoAlumno);
		HashMap<String,MapaCurso> mapaCursosPlan 		= mapaCursoDao.mapaCursosPlan(convEvento.getPlanId());
		
		modelo.addAttribute("codigoPersonal", codigoPersonal);
		modelo.addAttribute("convalidacionId", convalidacionId);
		modelo.addAttribute("codigoAlumno", codigoAlumno);
		modelo.addAttribute("checked", checked);
		modelo.addAttribute("carreraId", carreraId);
		modelo.addAttribute("nombreCarrera", nombreCarrera);
		modelo.addAttribute("escribir", escribir);
		modelo.addAttribute("convEvento", convEvento);
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("planActivo", planActivo);
		modelo.addAttribute("lisMaterias", lisMaterias);
		modelo.addAttribute("mapaCursosAlumno", mapaCursosAlumno);
		modelo.addAttribute("mapaCursosPlan", mapaCursosPlan);
		
		return "convalida/sol_interna/dictamen";
	}	
	
	@RequestMapping("/convalida/sol_interna/formato")
	public String convalidasolInternaFormato(HttpServletRequest request, Model modelo){

		String codigoAlumno			= "0";
		
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion!=null){		
			codigoAlumno 	= (String)sesion.getAttribute("codigoAlumno");
		}

		String convalidacionId	= request.getParameter("ConvalidacionId")==null?"0":request.getParameter("ConvalidacionId");
		String modalidad 		= alumAcademicoDao.getModalidadId(codigoAlumno); 
		
		Parametros parametros = new Parametros();
		if(parametrosDao.existeReg("1")){
			parametros = parametrosDao.mapeaRegId("1");
		}
		
		ConvEvento convEvento = new ConvEvento();
		if(convEventoDao.existeCovaliadacion(convalidacionId)){
			convEvento = convEventoDao.mapeaRegId(convalidacionId);
		}
		
		String nombrePlan		= mapaPlanDao.getNombrePlan(convEvento.getPlanId());
		
		String institucion		= convEvento.getInstitucion();
		String programa 		= convEvento.getPrograma();
		
		MapaPlan mapaPlan = new MapaPlan();
		if(mapaPlanDao.existeReg(convEvento.getPlanId())){
			mapaPlan = mapaPlanDao.mapeaRegId(convEvento.getPlanId());
		}
		
		List<ConvMateria> lisMaterias = convMateriaDao.getListPorEstado(convalidacionId, " 'S','-','R' ", "ORDER BY CICLO_CURSO(CURSO_ID), ENOC.NOMBRE_MATERIA(CURSO_ID)");
		
		AlumPersonal alumPersonal = new AlumPersonal();
		if(alumPersonalDao.existeAlumno(codigoAlumno)){
			alumPersonal = alumPersonalDao.mapeaRegId(codigoAlumno);
		}
		
		if(!convEvento.getPlanOrigen().equals("-")) {
			if(mapaPlanDao.existeReg(convEvento.getPlanOrigen())) {
				programa = mapaPlanDao.getNombrePlan(convEvento.getPlanOrigen())+" plan "+convEvento.getPlanOrigen(); 
			}	
		}	
		
		HashMap<String, MapaCurso> mapaMaterias = mapaCursoDao.getAllMapaCursos("");
		
		modelo.addAttribute("codigoAlumno", codigoAlumno);
		modelo.addAttribute("modalidad", modalidad);
		modelo.addAttribute("parametros", parametros);
		modelo.addAttribute("convEvento", convEvento);
		modelo.addAttribute("nombrePlan", nombrePlan);
		modelo.addAttribute("institucion", institucion);
		modelo.addAttribute("programa", programa);
		modelo.addAttribute("mapaPlan", mapaPlan);
		modelo.addAttribute("lisMaterias", lisMaterias);
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("mapaMaterias", mapaMaterias);
		
		return "convalida/sol_interna/formato";
	}	
	
	@RequestMapping("/convalida/sol_interna/historial")
	public String convalidasolInternaHistorial(HttpServletRequest request, Model modelo){
		String convalidacionId = request.getParameter("ConvalidacionId")==null?"0":request.getParameter("ConvalidacionId");
		ConvEvento convEvento = new ConvEvento();
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
		
		return "convalida/sol_interna/historial";
	}	
	
	@RequestMapping("/convalida/sol_interna/interna")
	public String convalidasolInternaInterna(HttpServletRequest request, Model modelo){	
		String seleccion		= request.getParameter("estado")==null?"":request.getParameter("estado");
		String cursoId			= request.getParameter("cursoId");
		String codigoAlumno		= "";	
		String codigoPersonal	= "";
		String carreraId		= "";
		String nombreCarrera 	= "";
		String nombreAlumno		= "";
		String escribir			= " ";
		String periodoId 		= "0";
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();            
	    if (sesion != null){
			codigoAlumno		= (String)sesion.getAttribute("codigoAlumno");	
			codigoPersonal		= (String)sesion.getAttribute("codigoPersonal");
			sesion.setAttribute("MateriaConNota", "0");
		 	sesion.setAttribute("MateriaSinNota", "0");
	    }
		
		String planId			= alumPersonalDao.getPlanActivo(codigoAlumno); 
		String convalidacionId	= request.getParameter("ConvalidacionId");
		String comentarioNum	= request.getParameter("numeroCom");
		String plan				= alumPersonalDao.getPlanActivo(codigoAlumno);
		if(comentarioNum == null || comentarioNum.equals("")) {
			comentarioNum = "0";
		}
		String comentario = request.getParameter("comentario"+Integer.parseInt(comentarioNum));
		if(comentario == null || comentario.equals("null")) {
			comentario = "";
		}
		
		int matAceptadas = 0;
		int matRechazadas = 0;
		
		String proceso 			= "";
		boolean esAdmin			= accesoDao.esAdministrador(codigoPersonal);
		
		int accion				= 0;
		
		List<ConvMateria> lisMaterias	= new ArrayList<ConvMateria>();
		List<ConvEvento> lisConv		= new ArrayList<ConvEvento>();
		List<KrdxCursoAct> lisActual	= new ArrayList<KrdxCursoAct>();	
		
		if(request.getParameter("Accion")!=null) {
			accion = Integer.parseInt(request.getParameter("Accion"));
		}
		
		ConvEvento convEvento 		= new ConvEvento();
		ConvHistorial convHistorial = new ConvHistorial();
		ConvMateria convMateria 	= new ConvMateria();
		CandAlumno candAlumno 		= new CandAlumno();

		// utilizacion de las variables
		switch(accion){
			case 1:{//Borrar
					convEvento.setConvalidacionId(convalidacionId);
					convHistorial.setConvalidacionId(convalidacionId);
					if(convEventoDao.deleteReg(convalidacionId) && convHistorialDao.deleteAllRegs(convalidacionId))
						escribir = "Convalidacion Eliminada";
				}break;
			case 2:{//Nuevo
					convEvento.setConvalidacionId(String.valueOf(convEventoDao.getMaxReg()));
					convEvento.setFecha(Fecha.getHoy());
					convEvento.setUsuario(codigoPersonal);
					convEvento.setCodigoPersonal(codigoAlumno);
					convEvento.setPlanId(planId);
					convEvento.setEstado("S");
					convEvento.setComentario("");
					convEvento.setTipo("I");
					convEvento.setTipoConv("M");
					convEvento.setDictamen("-");
					convEvento.setInstitucion("UNIVERSIDAD DE MONTEMORELOS");
					convHistorial.setConvalidacionId(convEvento.getConvalidacionId());
					convHistorial.setFolio(convHistorialDao.getMaxReg(convEvento.getConvalidacionId()));
					convHistorial.setEstado("S");
					convHistorial.setFecha(Fecha.getHoy());
					convHistorial.setUsuario(codigoPersonal);
					if(planId.equals("Vacio") && planId.equals("") && planId == null){
						escribir = "No se pudo crear la convalidacion. No tienen Plan.";
					}else{
					if(!convEventoDao.insertReg(convEvento)&&!convHistorialDao.insertReg(convHistorial))
						escribir = "No se pudo crear la convalidacion. Intentelo de nuevo.";
					}
				}break;
			case 3:{//Grabar comentario
					convEvento = convEventoDao.mapeaRegId(convalidacionId);
					convEvento.setComentario(comentario);
					convEventoDao.updateReg(convEvento);
				}break;
			case 4:{//Confirmar
					lisActual 		= krdxCursoActDao.getListAll("WHERE CODIGO_PERSONAL = '"+codigoAlumno+"' AND CURSO_ID LIKE '"+plan+"%' AND TIPOCAL_ID IN ('1','2','4','5','6')");
					String estado = "P";
//	 				if(lisActual.size() == 0){
//	 					estado = "C";
//	 				}else{
//	 					estado = "P";
//	 				}
					convEvento = convEventoDao.mapeaRegId(convalidacionId);
					convEvento.setEstado(estado);
//	 				convEvento.setFecha(Fecha.getHoy());
					convHistorial.setConvalidacionId(convalidacionId);
					convHistorial.setFolio(convHistorialDao.getMaxReg(convEvento.getConvalidacionId()));
					convHistorial.setFecha(Fecha.getHoy());
					convHistorial.setUsuario(codigoPersonal);
					convHistorial.setEstado(estado);
					
					if(!convHistorialDao.insertReg(convHistorial))
						escribir = "No grabó correctamente";				
					convEventoDao.updateReg(convEvento);
				}break;
			case 5:{//Borrar materia
					convMateria = convMateriaDao.mapeaRegId(convalidacionId,cursoId);
					if(convMateriaDao.deleteReg(convalidacionId,cursoId))
						escribir = "Materia Eliminada";
					else
						escribir = "Error eliminando la Materia";
				}break;
			case 6:{
				String estado			= request.getParameter("estado");
				
				if(estado==null)break;
				
				convEvento = convEventoDao.mapeaRegId(convalidacionId);
				convEvento.setEstado(estado);
//	 			convEvento.setFecha(Fecha.getHoy());			
				convHistorial.setConvalidacionId(convalidacionId);
				convHistorial.setFolio(convHistorialDao.getMaxReg(convalidacionId));
				convHistorial.setFecha(Fecha.getHoy());
				convHistorial.setUsuario(codigoPersonal);
				convHistorial.setEstado(estado);
				
				if(convEventoDao.updateReg(convEvento)){
					if(convHistorialDao.insertReg(convHistorial)){
						if (estado.equals("X")){
							candAlumno.setCodigoPersonal(codigoAlumno);
							candAlumno.setFolio(candAlumnoDao.maximoReg(codigoAlumno));
							candAlumno.setCandadoId("0801");
							candAlumno.setfCreado(aca.util.Fecha.getHoy());
							candAlumno.setUsAlta(codigoPersonal);
							candAlumno.setComentario("¡ Convalidación cancelada !");
							candAlumno.setEstado("A");
							if (candAlumnoDao.insertReg(candAlumno)){
								escribir = "¡ Modificado !";
							}else{
								escribir = "¡No se guardó la información(Candado)!";
							}
						}else if (estado.equals("F")){
							candAlumno.setCodigoPersonal(codigoAlumno);
							candAlumno.setFolio(candAlumnoDao.maximoReg(codigoAlumno));
							candAlumno.setCandadoId("0801");
							candAlumno.setfCreado(aca.util.Fecha.getHoy());
							candAlumno.setUsAlta(codigoPersonal);
							candAlumno.setComentario("¡ Sin documento !");
							candAlumno.setEstado("A");
							if (candAlumnoDao.insertReg(candAlumno)){
								escribir = "¡ Modificado !";
							}else{
								escribir = "¡No se guardó la información(Candado)!";
							}
						}else if (estado.equals("D")){
							candAlumno.setCodigoPersonal(codigoAlumno);
							candAlumno.setFolio(candAlumnoDao.maximoReg(codigoAlumno));
							candAlumno.setCandadoId("0801");
							candAlumno.setfCreado(aca.util.Fecha.getHoy());
							candAlumno.setUsAlta(codigoPersonal);
							candAlumno.setComentario("¡ Sin pago !");
							candAlumno.setEstado("A");
							if (candAlumnoDao.insertReg(candAlumno)){
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
				
				if (candAlumnoDao.removerCand(candAlumno)){
					escribir = "¡ Modificado !";
				}else{
					escribir = "¡No se guardó la información(Candado)!";
				}	
				
			}break;
			case 8:{//Nuevo por ciclo
				convEvento.setConvalidacionId(String.valueOf(convEventoDao.getMaxReg()));
				convEvento.setFecha(Fecha.getHoy());
				convEvento.setUsuario(codigoPersonal);
				convEvento.setCodigoPersonal(codigoAlumno);
				convEvento.setPlanId(planId);
				convEvento.setEstado("S");
				convEvento.setComentario("");
				convEvento.setTipo("I");
				convEvento.setTipoConv("C");
				convEvento.setDictamen("-");
				convEvento.setInstitucion("UNIVERSIDAD DE MONTEMORELOS");
				convHistorial.setConvalidacionId(convEvento.getConvalidacionId());
				convHistorial.setFolio(convHistorialDao.getMaxReg(convEvento.getConvalidacionId()));
				convHistorial.setEstado("S");
				convHistorial.setFecha(Fecha.getHoy());
				convHistorial.setUsuario(codigoPersonal);	
				if(planId.equals("Vacio") && planId.equals("") && planId == null){
					escribir = "No se pudo crear la convalidacion. No tienen Plan.";
				}else{
					if(convEventoDao.insertReg(convEvento)){
						if (convHistorialDao.insertReg(convHistorial)){
							escribir = "¡Grabado!";
						}else{
							escribir = "¡No se grabó la bitacora!";
						}						
					}else{
						escribir = "¡No se pudo grabar la solicitud!";
					}	
				}
			}break;
		}
		
		if(planId==null){  // si el planId viene igual  a null le asignamos el plan de alumno para que muestre el las materias del plan
			planId = alumPersonalDao.getPlanActivo(codigoAlumno);	
		}
		
		nombreAlumno	= alumPersonalDao.getNombre(codigoAlumno, "NOMBRE");
		carreraId		= alumPersonalDao.getCarreraId(alumPersonalDao.getPlanActivo(codigoAlumno) );	
		nombreCarrera	= catCarreraDao.getNombreCarrera(carreraId);
		
		// Consulta la lista de convalidaciones del alumno
		lisConv			= convEventoDao.getSolicPorTipo(codigoAlumno, "'I'", "ORDER BY CONVALIDACION_ID");
		
		// Consulta la lista de candados de convalidación del alumno 
		List <CandAlumno> lisCandados		= candAlumnoDao.getListCandAlumno(codigoAlumno,"08","A"," ORDER BY F_CREADO");
		
		AlumAcademico alumAcademico = alumAcademicoDao.mapeaRegId(codigoAlumno);
		
		Acceso acceso = accesoDao.mapeaRegId(codigoPersonal);
		
		if(!carreraId.equals("Vacio")) {
			periodoId = convPeriodoDao.carreraHabilitada(carreraId);
		}
		
		HashMap<String,String> mapCarreras 	= convPeriodoDao.getMapCarreras(periodoId);
		
		lisMaterias = convMateriaDao.getListCicloNombre(convEvento.getConvalidacionId(),"ORDER BY CICLO_CURSO(CURSO_ID), ENOC.NOMBRE_MATERIA(CURSO_ID)");	
		
		HashMap<String, String> mapCursos = mapaCursoDao.mapCursos();
		HashMap<String, String> mapaExisteConv = convEventoDao.mapExisteConv(codigoAlumno);

		HashMap<String, List<ConvMateria>> mapaLisConvMaterias = new HashMap<String, List<ConvMateria>>();
		
		for(ConvEvento envento : lisConv) {
			mapaLisConvMaterias.put(envento.getConvalidacionId(), convMateriaDao.getListCicloNombre(envento.getConvalidacionId(),"ORDER BY CICLO_CURSO(CURSO_ID), ENOC.NOMBRE_MATERIA(CURSO_ID)"));
		}
		
		boolean convalidador = accesoDao.esConvalidador(codigoPersonal);
		
		String nombreModalidad = catModalidadDao.getNombreModalidad(alumAcademico.getModalidadId());
		
		modelo.addAttribute("codigoAlumno", codigoAlumno);
		modelo.addAttribute("codigoPersonal", codigoPersonal);
		modelo.addAttribute("seleccion", seleccion);
		modelo.addAttribute("cursoId", cursoId);
		modelo.addAttribute("carreraId", carreraId);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("nombreCarrera", nombreCarrera);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("escribir", escribir);
		modelo.addAttribute("convalidacionId", convalidacionId);
		modelo.addAttribute("comentarioNum", comentarioNum);
		modelo.addAttribute("plan", plan);
		modelo.addAttribute("comentario", comentario);
		modelo.addAttribute("proceso", proceso);
		modelo.addAttribute("esAdmin", esAdmin);
		modelo.addAttribute("mapaLisConvMaterias", mapaLisConvMaterias);
		modelo.addAttribute("lisConv", lisConv);
		modelo.addAttribute("lisActual", lisActual);
		modelo.addAttribute("lisCandados", lisCandados);
		modelo.addAttribute("alumAcademico", alumAcademico);
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("mapCarreras", mapCarreras);
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("convEvento", convEvento);
		modelo.addAttribute("convMateria", convMateria);
		modelo.addAttribute("mapCursos", mapCursos);
		modelo.addAttribute("mapaExisteConv", mapaExisteConv);
		modelo.addAttribute("convalidador", convalidador);
		modelo.addAttribute("nombreModalidad", nombreModalidad);
		
		return "convalida/sol_interna/interna";
	}	
	
	@RequestMapping("/convalida/sol_interna/reporte")
	public String convalidasolInternaReporte(HttpServletRequest request, Model modelo){	
		String nomInstitucion 	= "";
		String codigoAlumno		= "";
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();            
	    if (sesion != null){
	    	nomInstitucion 	= (String)sesion.getAttribute("institucion");
			codigoAlumno	= (String)sesion.getAttribute("codigoAlumno");
	    }
		
		String accion			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");	
		String convalidacionId	= request.getParameter("ConvalidacionId")==null?"0":request.getParameter("ConvalidacionId");	
		
		ConvEvento convEvento = new ConvEvento();
		AlumPersonal alumPersonal = new AlumPersonal();
		
		if(convEventoDao.existeCovaliadacion(convalidacionId)) {
			convEvento = convEventoDao.mapeaRegId(convalidacionId);	 
		}

		if(alumPersonalDao.existeReg(codigoAlumno)) {
			alumPersonal = alumPersonalDao.mapeaRegId(codigoAlumno);	
		}
		
		String planId			= convEvento.getPlanId();	
		String mensaje			= "0";
		
		String institucion 		= request.getParameter("Institucion")==null?convEvento.getInstitucion():request.getParameter("Institucion");
		String programa 		= request.getParameter("Programa")==null?convEvento.getPrograma():request.getParameter("Programa");
		
		String nombrePlan = mapaPlanDao.getNombrePlan(planId); 
		
		if(accion.equals("1")){		
			convEvento.setInstitucion(institucion);
			convEvento.setPrograma(programa);
			
			if(convEventoDao.updateReg(convEvento)){			
				mensaje = "Guardado";
			}
		}
		
		if(!convEvento.getPlanOrigen().equals("-")) {
			if(mapaPlanDao.existeReg(convEvento.getPlanOrigen())) {
				programa = mapaPlanDao.getNombrePlan(convEvento.getPlanOrigen())+" plan "+convEvento.getPlanOrigen(); 
			}
		}
		
		// Lista de Materias para convalidar
		List<ConvMateria> lisMaterias = convMateriaDao.getList(convalidacionId, "ORDER BY CICLO_CURSO(CURSO_ID), ENOC.NOMBRE_MATERIA(CURSO_ID)");
		
		// Mapa de materias
		HashMap<String,MapaCurso> mapaMaterias = mapaCursoDao.getAllMapaCursos("");
		
		aca.util.Fecha fechaFecha = new aca.util.Fecha();
		
		String fecha = fechaFecha.getFechaTexto(convEvento.getFecha());
		
		modelo.addAttribute("nomInstitucion", nomInstitucion);
		modelo.addAttribute("codigoAlumno", codigoAlumno);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("institucion", institucion);
		modelo.addAttribute("programa", programa);
		modelo.addAttribute("lisMaterias", lisMaterias);
		modelo.addAttribute("mapaMaterias", mapaMaterias);
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("nombrePlan", nombrePlan);
		modelo.addAttribute("fecha", fecha);
		
		return "convalida/sol_interna/reporte";
	}	
	
	@RequestMapping("/convalida/sol_interna/solicitudInterna")
	public String convalidasolInternaSolicitudInterna(HttpServletRequest request, Model modelo){		
		
		String convalidacionId  	= request.getParameter("ConvalidacionId")==null?"0":request.getParameter("ConvalidacionId");
		String convalidaOrigen 		= request.getParameter("MatOrigen")==null?"0":request.getParameter("MatOrigen");
		String convalidaDestino   	= request.getParameter("MatDestino")==null?"0":request.getParameter("MatDestino");
		String planOrigen 			= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String accion	 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");		
		String codigoAlumno			= "";		
		HttpSession sesion 			= ((HttpServletRequest)request).getSession();            
	    if (sesion != null){
			codigoAlumno			= (String)sesion.getAttribute("codigoAlumno");		
	    }
		
		String nombreAlumno  		= alumPersonalDao.getNombre(codigoAlumno, "NOMBRE");
		String planActual			= alumPersonalDao.getPlanActivo(codigoAlumno);		 
		String carreraId			= alumPersonalDao.getCarreraId(planActual);	
		String nombreCarrera		= catCarreraDao.getNombreCarrera(carreraId);
		
		List<String> lisPlanes 					= alumnoCursoDao.getPlanAalumnoPorTipo(codigoAlumno, "1");
		String planElegido   					= request.getParameter("PlanId")==null?lisPlanes.get(0):request.getParameter("PlanId");		
		List<AlumnoCurso> lisAcreditadas 		= alumnoCursoDao.getMatAproPorPlan(codigoAlumno, planElegido, " ORDER BY CICLO, NOMBRE_CURSO");
		List<MapaCurso> lisDisponibles		 	= mapaCursoDao.getCursosNoInscritos(codigoAlumno, planActual, "'1','I'", " ORDER BY CICLO, NOMBRE_CURSO");
		
		HashMap<String, ConvMateria> mapPorSolicitud	= convMateriaDao.mapPorConvIdSolicitud( convalidacionId);
		HashMap<String, ConvMateria> mapPorOrigen		= convMateriaDao.mapPorConvIdMatOrigen( convalidacionId);
		HashMap<String,MapaPlan> mapPlanes				= mapaPlanDao.mapPlanes("'A','V','I'");
		
		AlumnoCurso cursoOrigen 	= alumnoCursoDao.getCursoPorIdyAlumno(codigoAlumno, convalidaOrigen,"'1'");
		MapaCurso cursoDestino 		= mapaCursoDao.mapeaRegId( convalidaDestino);		
		
		String carreraTemp = "0";
		if (lisAcreditadas.size() > 0) carreraTemp = lisAcreditadas.get(0).getCarreraId();
		
		String nivelOrigen 			= catCarreraDao.getNivelId(carreraTemp);
		String nivelDestino 		= catCarreraDao.getNivelId(carreraId);
		
		ConvEvento convEvento = new ConvEvento();		
		if(accion.equals("1")) {
			if(convEventoDao.existeCovaliadacion(convalidacionId)){
				convEvento = convEventoDao.mapeaRegId(convalidacionId);
				convEvento.setPlanOrigen(planOrigen);
				convEventoDao.updateReg(convEvento);
			}
		}
		
		modelo.addAttribute("nombreAlumno",nombreAlumno);		
		modelo.addAttribute("planElegido",planElegido);
		modelo.addAttribute("planActual",planActual);
		modelo.addAttribute("carreraId",carreraId);
		modelo.addAttribute("nombreCarrera",nombreCarrera);
		modelo.addAttribute("cursoOrigen",cursoOrigen);
		modelo.addAttribute("cursoDestino",cursoDestino);	
		modelo.addAttribute("nivelOrigen",nivelOrigen);
		modelo.addAttribute("nivelDestino",nivelDestino);
		
		modelo.addAttribute("lisPlanes",lisPlanes);
		modelo.addAttribute("lisAcreditadas",lisAcreditadas);
		modelo.addAttribute("lisDisponibles",lisDisponibles);
		modelo.addAttribute("mapPorSolicitud",mapPorSolicitud);
		modelo.addAttribute("mapPorOrigen",mapPorOrigen);
		modelo.addAttribute("mapPlanes",mapPlanes);		
		
		return "convalida/sol_interna/solicitudInterna";
	}
	
	@RequestMapping("/convalida/sol_interna/grabarNota")
	public String convalidasolInternaGrabarNota(HttpServletRequest request){
		
		String convalidacionId		= request.getParameter("ConvalidacionId");
		String planElegido   		= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String convalidaNota   		= request.getParameter("MatOrigen")==null?"0":request.getParameter("MatOrigen");
		String convalidaSinNota   	= request.getParameter("MatDestino")==null?"0":request.getParameter("MatDestino");
		String mensaje				= "-";
		
		String codigoAlumno			= "";		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();            
	    if (sesion != null){
			codigoAlumno		= (String)sesion.getAttribute("codigoAlumno");		
	    }
	    
		AlumnoCurso cursoConNota = new AlumnoCurso();
		
		if(alumnoCursoDao.existeCursoPorIdyAlumno(codigoAlumno, convalidaNota,"'1'")) {
			cursoConNota = alumnoCursoDao.getCursoPorIdyAlumno(codigoAlumno, convalidaNota,"'1'");
		}
		
		String tipoCursoConNota = "";
		String tipoCursoSinNota = "";
		
		if(mapaCursoDao.existeReg(convalidaNota)){
			tipoCursoConNota = mapaCursoDao.getTipoCurso(convalidaNota);
		}
		
		if(mapaCursoDao.existeReg(convalidaSinNota)){
			tipoCursoSinNota = mapaCursoDao.getTipoCurso(convalidaSinNota);
		}
		
		ConvMateria convMateria = new ConvMateria();	
			
		String nota = "";
		String fecha = "";
		
		if(!cursoConNota.getNotaExtra().equals("0")){
			nota 	= cursoConNota.getNotaExtra();
			fecha 	= cursoConNota.getFExtra();
		}else {
			nota 	= cursoConNota.getNota();
			fecha 	= cursoConNota.getFEvaluacion();
		}
		
		// Cambia la nota a 100 cuando la materia es componente, tesis o tipo AC/NA
		if (tipoCursoConNota.equals("3") || tipoCursoConNota.equals("5") || tipoCursoConNota.equals("8")){
			if (tipoCursoSinNota.equals("1")){
				nota = "100";	
			}			
		}
		
		fecha = fecha.substring(0, 10);
		String arrayFecha[] = fecha.split("-");
		fecha = arrayFecha[2]+"/"+arrayFecha[1]+"/"+arrayFecha[0];
		
		convMateria.setConvalidacionId(convalidacionId);
		convMateria.setCursoId(convalidaSinNota);
		convMateria.setFecha(aca.util.Fecha.getHoy());
		convMateria.setTipo("I");
		convMateria.setMateria_O(cursoConNota.getCursoId());
		convMateria.setCreditos_O(cursoConNota.getCreditos());
		convMateria.setNota_O(nota);
		convMateria.setEstado("-");
		convMateria.setFolio(convMateriaDao.getMaxReg(convalidacionId));		
		convMateria.setfNota(fecha);
		if(convMateriaDao.existeReg(convalidacionId, cursoConNota.getCursoId())){
			convMateriaDao.updateReg(convMateria);
			mensaje = "Modificado";
		}else{ 
			convMateriaDao.insertReg(convMateria);
			mensaje = "Guardado";
		}
		
		return "redirect:/convalida/sol_interna/solicitudInterna?ConvalidacionId="+convalidacionId+"&PlanId="+planElegido+"&Mensaje="+mensaje;
	}
	
	
	@RequestMapping("/convalida/sol_interna/solicitudInternaNotas")
	public String convalidasolInternaSolicitudInternaNotas(HttpServletRequest request, Model modelo){	
		
		String codigoPersonal		= "";
		String codigoAlumno			= "";
		String mensaje				= "";
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession(); 
	    if (sesion != null){
			codigoAlumno		= (String)sesion.getAttribute("codigoAlumno");	
			codigoPersonal		= (String)sesion.getAttribute("codigoPersonal");
	    }
		
		String nombreAlumno  		= alumPersonalDao.getNombre(codigoAlumno, "NOMBRE");		 
		String carreraId			= alumPersonalDao.getCarreraId(alumPersonalDao.getPlanActivo(codigoAlumno) );	
		String nombreCarrera		= catCarreraDao.getNombreCarrera(carreraId);	
		
		String convalidacionId		= request.getParameter("ConvalidacionId");
		String accion				= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		
		String planElegido   		= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String planActual			= alumPersonalDao.getPlanActivo(codigoAlumno);
		
		List<String> lisPlanes = alumnoCursoDao.getPlanAalumnoPorTipo(codigoAlumno, "1");
		
		if(planElegido.equals("0") && lisPlanes.size() >= 1 ){
			planElegido = lisPlanes.get(0);
		}

		List<MapaCurso> lisCursos 						= mapaCursoDao.lisCursosSinRegistrar(codigoAlumno, planActual, "'1','I'", " ORDER BY CICLO, NOMBRE_CURSO");				
		HashMap<String, String> mapaPlanes 				= mapaPlanDao.mapNombrePlan();
		
		// Grabar
		if (accion.equals("2")) {			
			for (MapaCurso curso:lisCursos) {				
				if (request.getParameter("Nota"+curso.getCursoId()) != null && !request.getParameter("Nota"+curso.getCursoId()).equals("0") && request.getParameter("Fecha"+curso.getCursoId())!=null){					
					String nota 	= request.getParameter("Nota"+curso.getCursoId());
					String fecha 	= request.getParameter("Fecha"+curso.getCursoId());
					ConvMateria convMateria = new ConvMateria();
					convMateria.setConvalidacionId(convalidacionId);
					convMateria.setCursoId(curso.getCursoId());
					convMateria.setFecha(aca.util.Fecha.getHoy());
					convMateria.setTipo("I");
					convMateria.setMateria_O("Por ciclo");
					convMateria.setCreditos_O(curso.getCreditos());
					convMateria.setNota_O(request.getParameter("Nota"+curso.getCursoId()).trim());
					convMateria.setEstado("-");
					convMateria.setFolio(convMateriaDao.getMaxReg(convalidacionId));
					convMateria.setfNota(fecha);
					if(convMateriaDao.existeReg(convalidacionId, curso.getCursoId())){
						if (convMateriaDao.updateReg(convMateria)){
							mensaje = "Modificado";
						}						
					}else{ 
						if (convMateriaDao.insertReg(convMateria)){
							mensaje = "Guardado";
						}					
					}
				}
			}
			lisCursos = mapaCursoDao.lisCursosSinRegistrar(codigoAlumno, planActual, "'1','I'", " ORDER BY CICLO, NOMBRE_CURSO");
		}
				
		modelo.addAttribute("codigoAlumno", codigoAlumno);
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("codigoPersonal", codigoPersonal);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("carreraId", carreraId);
		modelo.addAttribute("nombreCarrera", nombreCarrera);		
		modelo.addAttribute("convalidacionId", convalidacionId);
		modelo.addAttribute("planElegido", planElegido);
		modelo.addAttribute("planActual", planActual);	
		
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("lisCursos", lisCursos);		
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		
		return "convalida/sol_interna/solicitudInternaNotas";
	}	
	
}