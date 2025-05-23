package aca.web.cargagrupo;

import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestMapping;

import aca.catalogo.spring.CatFacultad;
import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;

import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatHorarioAcceso;
import aca.catalogo.spring.CatHorarioAccesoDao;
import aca.catalogo.spring.CatHorario;
import aca.catalogo.spring.CatHorarioDao;
import aca.catalogo.spring.CatHorarioFacultad;
import aca.catalogo.spring.CatHorarioFacultadDao;
import aca.emp.spring.EmpMaestro;
import aca.emp.spring.EmpMaestroDao;
import aca.emp.spring.EmpleadoDao;
import aca.vista.spring.Maestros;
import aca.vista.spring.MaestrosDao;
import aca.carga.spring.CargaGrupoDao;

@Controller
public class ContCargaGrupoHorario {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	CargaGrupoDao cargaGrupoDao;
	
	@Autowired
	AccesoDao accesoDao;	
	
	@Autowired
	CatHorarioDao catHorarioDao;
	
	@Autowired
	CatFacultadDao catFacultadDao;
	
	@Autowired
	CatHorarioFacultadDao catHorarioFacultadDao;
	
	@Autowired
	EmpMaestroDao empMaestroDao; 
	
	@Autowired
	MaestrosDao maestrosDao;
	
	@Autowired
	CatHorarioAccesoDao catHorarioAccesoDao; 
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
			//System.out.println("Abrir conEnoc"+url);
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/carga_grupo/horario/listado")
	public String cargaGrupoHorarioListado(HttpServletRequest request, Model modelo){
		Acceso acceso = new Acceso();
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		String codigoEmpleado 	= "";
		if (sesion!=null) {
			codigoEmpleado = (String) sesion.getAttribute("codigoPersonal");
			if (accesoDao.existeReg(codigoEmpleado)) {
				acceso = accesoDao.mapeaRegId(codigoEmpleado);
			}			 
		}
		
		List <CatHorario> lisHorarios = new ArrayList<CatHorario>();
		if (accesoDao.esAdministrador(codigoEmpleado)) {
			lisHorarios = catHorarioDao.getListAll(" ORDER BY HORARIO_ID");
		}else {
			lisHorarios = catHorarioDao.filtroporAcceso(codigoEmpleado," ORDER BY HORARIO_ID");
		}		
		
		HashMap<String,CatFacultad> mapaFacultades		= catFacultadDao.getMapFacultad("");
		HashMap<String,String> mapaHorarios				= catHorarioFacultadDao.mapaHorarioConPeriodos();
		HashMap<String,String> mapaUsers				= catHorarioAccesoDao.mapUsersByHorarioId();
		
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("lisHorarios", lisHorarios);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaHorarios", mapaHorarios);
		modelo.addAttribute("mapaUsers", mapaUsers);
		
		return "carga_grupo/horario/listado";
	}
	
	@RequestMapping("/carga_grupo/horario/editar")
	public String cargaGrupoHorarioEditar(HttpServletRequest request, Model modelo){				
		String horarioId 		= request.getParameter("HorarioId")==null?"0":request.getParameter("HorarioId");
		
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		String codigoEmpleado 	= "";		
		if (sesion!=null) {
			codigoEmpleado = (String) sesion.getAttribute("codigoPersonal");
		}
		
		CatHorario horario = new CatHorario(); 
		if (catHorarioDao.existeReg(horarioId)) {
			horario = catHorarioDao.mapeaRegId(horarioId);
		}else {
			horario.setHorarioId(catHorarioDao.maximoReg());
		}
		
		List <CatFacultad> lisFacultades = new ArrayList<CatFacultad>();
		if (accesoDao.esAdministrador(codigoEmpleado)) {
			lisFacultades = catFacultadDao.getListAll(" ORDER BY NOMBRE_FACULTAD");
		}else {
			lisFacultades = catFacultadDao.filtroPorAcceso(codigoEmpleado, " ORDER BY NOMBRE_FACULTAD");
		}		
		
		modelo.addAttribute("lisFacultades", lisFacultades);
		modelo.addAttribute("horario", horario);
		
		
		return "carga_grupo/horario/editar";
	}
	
	@RequestMapping("/carga_grupo/horario/grabar")
	public String cargaGrupoHorarioGrabar(HttpServletRequest request, Model modelo) {
		
		String horarioId 		= request.getParameter("HorarioId")==null?"0":request.getParameter("HorarioId");
		String facultadId		= request.getParameter("FacultadId")==null?"0":request.getParameter("FacultadId");
		String descripcion		= request.getParameter("Descripcion")==null?"-":request.getParameter("Descripcion");		
		String estado			= request.getParameter("Estado")==null?"A":request.getParameter("Estado");
		String acceso			= request.getParameter("Acceso")==null?"A":request.getParameter("Acceso");
		String mensaje 			= "-";
		
		CatHorario horario 		= new CatHorario();
		horario.setHorarioId(horarioId);
		horario.setFacultadId(facultadId);
		horario.setDescripcion(descripcion);
		horario.setEstado(estado);
		horario.setAcceso(acceso);
		if (catHorarioDao.existeReg(horarioId)) {
			if (catHorarioDao.updateReg(horario)) mensaje = "Updated"; else mensaje = "Error has ocurred while saving";				
		}else {
			horario.setHorarioId(catHorarioDao.maximoReg());
			if (catHorarioDao.insertReg(horario)) mensaje = "Saved"; else mensaje = "Error has ocurred while saving";
		}	
		
		return "redirect:/carga_grupo/horario/editar?HorarioId="+horario.getHorarioId()+"&Mensaje="+mensaje;
	}	

	@RequestMapping("/carga_grupo/horario/borrar")
	public String cargaGrupoHorarioBorrar(HttpServletRequest request, Model modelo) {
		String horarioId 		= request.getParameter("HorarioId")==null?"0":request.getParameter("HorarioId");
		String mensaje 			= "-";
		
		if (catHorarioDao.existeReg(horarioId)) {
			if (catHorarioDao.deleteReg(horarioId)) mensaje = "Updated"; else mensaje = "Error has ocurred while deleting";				
		}else {
			mensaje = "Error has ocurred while saving";
		}	
		
		return "redirect:/carga_grupo/horario/listado?Mensaje="+mensaje;
	}
	
	@RequestMapping("/carga_grupo/horario/nuevoAcceso")
	public String cargaGrupoHorarioNuevoAcceso(HttpServletRequest request, Model modelo){				
		 
		String horarioId 		= request.getParameter("HorarioId")==null?"0":request.getParameter("HorarioId");
		
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		String codigoEmpleado 	= "";		
		if (sesion!=null) {
			codigoEmpleado = (String) sesion.getAttribute("codigoPersonal");
		}
		
		CatHorario horario = new CatHorario(); 
		if (catHorarioDao.existeReg(horarioId)) {
			horario = catHorarioDao.mapeaRegId(horarioId);
		}
		//Actualizar la lista para que estén disponibles todos excepto los que ya están elegidos
		List<Maestros> listaEmpleados				= maestrosDao.getListAll("ORDER BY CODIGO_PERSONAL");
		List<Maestros> listaFilterUsuarios			= maestrosDao.getListFilterByCodigoPersonal(horarioId, "ORDER BY CODIGO_PERSONAL");
		HashMap<String, String> mapaEmpleados	 	= maestrosDao.mapMaestroNombre("NOMBRE");
		List<CatHorarioAcceso> listaHorarioId 		= catHorarioAccesoDao.getListByHorarioId(horarioId);
	
		modelo.addAttribute("listaEmpleados", listaEmpleados);
		modelo.addAttribute("listaFilterUsuarios", listaFilterUsuarios);
		modelo.addAttribute("mapaNombreEmpleado", mapaEmpleados);
		modelo.addAttribute("listaHorarioId", listaHorarioId);
		modelo.addAttribute("horario", horario);
		
		return "carga_grupo/horario/nuevoAcceso";
	}
	
	@RequestMapping("/carga_grupo/horario/grabarAcceso")
	public String cargaGrupoHorarioGrabarAcceso(HttpServletRequest request, Model modelo) {
		
		String horarioId 		= request.getParameter("HorarioId")==null?"0":request.getParameter("HorarioId");
		String empleadoId		= request.getParameter("EmpleadoId")==null?"0":request.getParameter("EmpleadoId");
		String mensaje 			= "-";
				
		CatHorarioAcceso horario 		= new CatHorarioAcceso();
		horario.setHorarioId(horarioId);
		horario.setCodigoPersonal(empleadoId);
		//Confirmación para saber si existe el empleado
			if (catHorarioAccesoDao.insertReg(horario)) {
				mensaje = "Saved"; 
			} else{
				mensaje = "Error saving";
			}
			
		return "redirect:/carga_grupo/horario/nuevoAcceso?HorarioId="+horario.getHorarioId()+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/carga_grupo/horario/borrarUsuario")
	public String cargaGrupoHorarioBorrarUsuario(HttpServletRequest request, Model modelo) {
		
		String horarioId 		= request.getParameter("HorarioId")==null?"0":request.getParameter("HorarioId");
		String codigoPersonal 	= request.getParameter("CodigoPersonal")==null?"0":request.getParameter("CodigoPersonal");
		String mensaje 			= "-";
		
		CatHorarioAcceso horario 		= new CatHorarioAcceso();
		horario.setHorarioId(horarioId);
		
		if (catHorarioAccesoDao.deleteReg(horarioId, codigoPersonal)) {
			mensaje = "Deleted"; 
		} else {
			mensaje = "Error deleting";
		}

		return "redirect:/carga_grupo/horario/nuevoAcceso?HorarioId="+horario.getHorarioId()+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/carga_grupo/horario/altaHorario")
	public String cargaGrupoHorarioAltaHorario(HttpServletRequest request, Model modelo){	
			
		String horarioId 				= request.getParameter("horarioId")==null?"0":request.getParameter("horarioId");
		String periodo 					= request.getParameter("Periodo")==null?"0":request.getParameter("Periodo");
		String facultadId				= request.getParameter("facultadId")==null?"0":request.getParameter("facultadId");
		String facultadNombre 			= catFacultadDao.getNombreFacultad(facultadId);
		String accion 					= request.getParameter("Accion") == null ? "1" : request.getParameter("Accion");		
		int numAccion 					= Integer.parseInt(accion);
		CatHorarioFacultad HorarioFac 	= new CatHorarioFacultad();
		String mensaje 					= "-";
		
		List<CatHorarioFacultad> lisHorarios 	= catHorarioFacultadDao.getLista(horarioId, "ORDER BY PERIODO");
		 
		switch (numAccion){
		case 2: { // Grabar
			int hI = Integer.parseInt(request.getParameter("HoraInicio"));
			int mI = Integer.parseInt(request.getParameter("MinInicio"));
			Date horaIni = new Date(2000, 1, 1, hI, mI);
			int hF = Integer.parseInt(request.getParameter("HoraFinal"));
			int mF = Integer.parseInt(request.getParameter("MinFinal"));
			Date horaFin = new Date(2000, 1, 1, hF, mF);
	
			String turno = request.getParameter("Turno");
			HorarioFac.setHorarioId(horarioId);
			HorarioFac.setTurno(turno);		
			
			if(turno.equals("2")) periodo = (Integer.parseInt(periodo)+10)+"";
			else if(turno.equals("3")) periodo = (Integer.parseInt(periodo)+20)+"";
			
			HorarioFac.setPeriodo(periodo);
			HorarioFac.setHoraInicio(request.getParameter("HoraInicio"));
			HorarioFac.setHoraFinal(request.getParameter("HoraFinal"));
			HorarioFac.setMinutoInicio(request.getParameter("MinInicio"));
			HorarioFac.setMinutoFinal(request.getParameter("MinFinal"));
			HorarioFac.setNombre(request.getParameter("Nombre")==null?"-":request.getParameter("Nombre"));
			HorarioFac.setTipo(request.getParameter("Tipo")==null?"-":request.getParameter("Tipo"));
			
			if(horaIni.before(horaFin)){				
				boolean grabar = true;
				
				for(CatHorarioFacultad horario : lisHorarios){
					if(!horario.getPeriodo().equals(periodo)){
						int tmpHI 	= Integer.parseInt(horario.getHoraInicio());
		  				int tmpMI	= Integer.parseInt(horario.getMinutoInicio());
						Date tmpHoraIni = new Date(2000, 1, 1, tmpHI, tmpMI);
						
						int tmpHF 	= Integer.parseInt(horario.getHoraFinal());
		  				int tmpMF	= Integer.parseInt(horario.getMinutoFinal());
						Date tmpHoraFin = new Date(2000, 1, 1, tmpHF, tmpMF);
						
						if((tmpHoraIni.before(horaFin)) && (tmpHoraFin.after(horaIni))){
							grabar = false;
							String p = horario.getPeriodo();
							String t = "Morning";
							if(horario.getTurno().equals("2")){
								p = (Integer.parseInt(p)-10)+"";
								t = "Afternoon";
							}
							else if(horario.getTurno().equals("3")){
								p = (Integer.parseInt(p)-20)+"";
								t = "Night";
							}
							mensaje = "Error, the cycle hours <font color='black'>"+t+"</font> in the period <font color='black'>"+p+"</font> interfere";
						}
					}
				}
				
				if(grabar){
					CatHorarioFacultad periodoAnt = null;
					CatHorarioFacultad periodoSig = null;
					
					if(catHorarioFacultadDao.existeReg(horarioId, periodo)){
						for(int i=0; i<lisHorarios.size(); i++){
							CatHorarioFacultad horario = lisHorarios.get(i);
							if(horario.getPeriodo().equals(periodo)){
								if(i-1>=0) periodoAnt = lisHorarios.get(i-1);
								if(i+1<lisHorarios.size()) periodoSig = lisHorarios.get(i+1);
							}
						}
					}else{
						int per = Integer.parseInt(periodo);
						for(int i=0; i<lisHorarios.size(); i++){
							if(Integer.parseInt(lisHorarios.get(i).getPeriodo())>per){
								if(i-1>=0) periodoAnt = lisHorarios.get(i-1);
								break;
							}
						}
						for(int i=lisHorarios.size()-1; i>=0; i--){
							if(Integer.parseInt(lisHorarios.get(i).getPeriodo())<per){
								if(i+1<lisHorarios.size()) periodoSig = lisHorarios.get(i+1);
								break;
							}
						}
					}
					
					if(periodoAnt!=null){
						int tmpHI 	= Integer.parseInt(periodoAnt.getHoraInicio());
		  				int tmpMI	= Integer.parseInt(periodoAnt.getMinutoInicio());
						Date tmpHoraIni = new Date(2000, 1, 1, tmpHI, tmpMI);
						
						int tmpHF 	= Integer.parseInt(periodoAnt.getHoraFinal());
		  				int tmpMF	= Integer.parseInt(periodoAnt.getMinutoFinal());
						Date tmpHoraFin = new Date(2000, 1, 1, tmpHF, tmpMF);
						
						if(tmpHoraFin.after(horaIni)){
							grabar = false;
							String p = periodoAnt.getPeriodo();
							String t = "Morning";
							if(periodoAnt.getTurno().equals("2")){
								p = (Integer.parseInt(p)-10)+"";
								t = "Afternoon";
							}
							else if(periodoAnt.getTurno().equals("3")){
								p = (Integer.parseInt(p)-20)+"";
								t = "Night";
							}
							mensaje = "Error, the cycle hours <font color='black'>"+t+"</font> in the period <font color='black'>"+p+"</font> interfere";
						}
					}
					if(periodoSig!=null){
						int tmpHI 	= Integer.parseInt(periodoSig.getHoraInicio());
		  				int tmpMI	= Integer.parseInt(periodoSig.getMinutoInicio());
						Date tmpHoraIni = new Date(2000, 1, 1, tmpHI, tmpMI);
						
						int tmpHF 	= Integer.parseInt(periodoSig.getHoraFinal());
		  				int tmpMF	= Integer.parseInt(periodoSig.getMinutoFinal());
						Date tmpHoraFin = new Date(2000, 1, 1, tmpHF, tmpMF);
						
						if(tmpHoraIni.before(horaFin)){
							grabar = false;
							String p = periodoSig.getPeriodo();
							String t = "Morning";
							if(periodoSig.getTurno().equals("2")){
								p = (Integer.parseInt(p)-10)+"";
								t = "Afternoon";
							}
							else if(periodoSig.getTurno().equals("3")){
								p = (Integer.parseInt(p)-20)+"";
								t = "Night";
							}
							mensaje = "Error, the cycle hours <font color='black'>"+t+"</font> in the period <font color='black'>"+p+"</font> interfere";
						}
					}
				}
				
				if(grabar){
					if (!catHorarioFacultadDao.existeReg(horarioId, periodo)) {
						if (catHorarioFacultadDao.insertReg(HorarioFac)) {
							mensaje = "Saved";
						} else {
							mensaje = "Error saving";
						}
					} else {
						if (catHorarioFacultadDao.updateReg(HorarioFac)) {
							mensaje = "Updated";
						} else {
							mensaje = "Error updating";
						}
					}
				}
			}
			else{
				mensaje = "Error, START time is the SAME or is AFTER the END time";
			}
			break;
		}
		case 3: {//borrar			
			if (catHorarioFacultadDao.existeReg(horarioId, periodo)) {
				if (catHorarioFacultadDao.deleteReg(horarioId, periodo)) {
					mensaje = "Deleted: " + HorarioFac.getPeriodo();
				} else {
					mensaje = "Error deleting: " + HorarioFac.getPeriodo();
				}
			} else {
				mensaje = "Not found: " + HorarioFac.getPeriodo();
			}
			break;
		}
	
		case 4: { //Consultar		
			if (catHorarioFacultadDao.existeReg(horarioId, periodo)==true) {
				HorarioFac = catHorarioFacultadDao.mapeaRegId( horarioId, periodo);
			} else {
				mensaje = "Not found: " + HorarioFac.getPeriodo();
			}
			break;
		}
		}
		
		lisHorarios 	= catHorarioFacultadDao.getLista(horarioId, "ORDER BY PERIODO");
		
		modelo.addAttribute("facultadNombre", facultadNombre);
		modelo.addAttribute("HorarioFac", HorarioFac);
		modelo.addAttribute("mensaje", mensaje);
		
		modelo.addAttribute("lisHorarios", lisHorarios);	
		
		return "carga_grupo/horario/altaHorario";
	}
}