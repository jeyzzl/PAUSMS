package aca.web.cultural;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.cultural.spring.CompAsistencia;
import aca.cultural.spring.CompAsistenciaAlumno;
import aca.cultural.spring.CompAsistenciaAlumnoDao;
import aca.cultural.spring.CompAsistenciaDao;
import aca.vista.spring.InscritosDao;

@Controller
public class ContCulturalAsistencia {
	
	@Autowired
	private CompAsistenciaDao compAsistenciaDao;
	
	@Autowired
	private CompAsistenciaAlumnoDao compAsistenciaAlumnoDao;
	
	@Autowired
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired
	private InscritosDao inscritosDao;
	
	
	@RequestMapping("/cultural/asistencia/listado")
	public String culturalAsistenciaListado(HttpServletRequest request, Model modelo){
		
		String eventoId			= request.getParameter("EventoId")==null?"0":request.getParameter("EventoId");
		String codigoPersonal	= request.getParameter("CodigoPersonal")==null?"0":request.getParameter("CodigoPersonal");
		String tipo				= request.getParameter("Tipo")==null?"E":request.getParameter("Tipo");	
		
		List<CompAsistencia> lisEventos 	= compAsistenciaDao.lisTodos(" ORDER BY FECHA");		
		if(eventoId.equals("0") && lisEventos.size() >= 1) {
			eventoId = lisEventos.get(0).getEventoId();
		}
		
		List<CompAsistenciaAlumno> lisAlumnos 		= new ArrayList<CompAsistenciaAlumno>();
		if (tipo.equals("E")) {
			lisAlumnos = compAsistenciaAlumnoDao.lisPorEvento(eventoId, " ORDER BY ENTRADA_HORA DESC");
		}else {
			lisAlumnos = compAsistenciaAlumnoDao.lisPorEvento(eventoId, " ORDER BY SALIDA_HORA DESC");
		}
		
		HashMap<String, AlumPersonal> mapaAlumnos 	= alumPersonalDao.mapaAlumnosEnCultural();
		HashMap<String, String> mapaInscritos 		= inscritosDao.getMapaInscritos();
		
		modelo.addAttribute("eventoId", eventoId);
		modelo.addAttribute("codigoPersonal", codigoPersonal);		
		modelo.addAttribute("lisEventos", lisEventos);	
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("mapaInscritos", mapaInscritos);
		
		return "cultural/asistencia/listado";
	}	

	@RequestMapping("/cultural/asistencia/grabar")
	public String culturalAsistenciaGrabar(HttpServletRequest request){
		
		String eventoId			= request.getParameter("EventoId")==null?"0":request.getParameter("EventoId");
		String tipo				= request.getParameter("Tipo")==null?"E":request.getParameter("Tipo");
		String size				= request.getParameter("Size")==null?"250px":request.getParameter("Size");
		String codigoPersonal	= request.getParameter("CodigoPersonal")==null?"0":request.getParameter("CodigoPersonal");		
		String horas			= aca.util.Fecha.getHoraDelDia();
		String minutos			= aca.util.Fecha.getMinutos();
		String segundos			= aca.util.Fecha.getSegundos();		
		String mensaje			= "-";
		boolean existe 			= false;
		
		if ((codigoPersonal.substring(0,1).equals("0")||codigoPersonal.substring(0,1).equals("1")) && codigoPersonal.length()==7 && alumPersonalDao.existeReg(codigoPersonal)){		
			existe = true;			
		}else {		
			if (alumPersonalDao.existeCodigoCredencial(codigoPersonal)){
				codigoPersonal 		= alumPersonalDao.getCodigoCredencial(codigoPersonal); 
			}else {
				mensaje = "¡No existe ningún alumno registrado con este código!";
			}
		}		
		
		CompAsistenciaAlumno alumno = new CompAsistenciaAlumno();
		
		if(existe) {
			
			if(compAsistenciaAlumnoDao.existeReg(eventoId, codigoPersonal)) {
				alumno = compAsistenciaAlumnoDao.mapeaRegId(eventoId, codigoPersonal);
			}else {
				alumno.setCodigoPersonal(codigoPersonal);
				alumno.setEventoId(eventoId);
			}
			
			if (minutos.length()==1) minutos = "0"+minutos;
			if (segundos.length()==1) segundos = "0"+segundos;
			
			if(tipo.equals("E")) {
				alumno.setEntrada("S");
				alumno.setEntradaHora(aca.util.Fecha.getHoyReversa()+" "+horas+":"+minutos+":"+segundos);				
			}
			if(tipo.equals("S")) {
				alumno.setSalida("S");
				alumno.setSalidaHora(aca.util.Fecha.getHoyReversa()+" "+horas+":"+minutos+":"+segundos);
			}
			
			if(compAsistenciaAlumnoDao.existeReg(eventoId, codigoPersonal)) {
				alumno.setPlanId(alumPersonalDao.getPlanActivo(codigoPersonal));
				if (compAsistenciaAlumnoDao.updateReg(alumno)) {
					mensaje = "¡Actualizado!"; 
				}else {
					mensaje = "¡Error al actualizar!";
				}
			}else {
				alumno.setPlanId(alumPersonalDao.getPlanActivo(codigoPersonal));
				if (compAsistenciaAlumnoDao.insertReg(alumno)) {
					mensaje = "¡Grabado!";
				}else {
					mensaje = "¡Error al grabar!";
				}
			}
		}
		
		return "redirect:/cultural/asistencia/listado?EventoId="+eventoId+"&CodigoPersonal="+codigoPersonal+"&Tipo="+tipo+"&Size="+size+"&Mensaje="+mensaje;
	}	
	
	@RequestMapping("/cultural/asistencia/grabarLista")
	public String culturalAsistenciaGrabarLista(HttpServletRequest request){
		
		String eventoId			= request.getParameter("EventoId")==null?"0":request.getParameter("EventoId");
		String tipo				= request.getParameter("Tipo")==null?"E":request.getParameter("Tipo");
		String size				= request.getParameter("Size")==null?"250px":request.getParameter("Size");
		String codigos			= request.getParameter("Codigos")==null?"0":request.getParameter("Codigos");
		String horas			= aca.util.Fecha.getHoraDelDia();
		String minutos			= aca.util.Fecha.getMinutos();
		String segundos			= aca.util.Fecha.getSegundos();		
		String mensaje			= "-";

		CompAsistenciaAlumno alumno = new CompAsistenciaAlumno();
		
		if(!codigos.equals("0")) {
			codigos = codigos.replaceAll("\n", "-");
			codigos = codigos.replaceAll("\r", "");
	
			String array[] = codigos.split("-");
			for(String matricula : array) {
				boolean existeAlumno = false;
				if( (matricula.substring(0,1).equals("0") || matricula.substring(0,1).equals("1") ) && alumPersonalDao.existeAlumno(matricula)) {
					existeAlumno= true;
					if(compAsistenciaAlumnoDao.existeReg(eventoId, matricula)) {
						alumno = compAsistenciaAlumnoDao.mapeaRegId(eventoId, matricula);
					}else {
						alumno.setCodigoPersonal(matricula);
						alumno.setEventoId(eventoId);
					}
					
					if (minutos.length()==1) minutos = "0"+minutos;
					if (segundos.length()==1) segundos = "0"+segundos;
					
					if(tipo.equals("E")) {
						alumno.setEntrada("S");
						alumno.setEntradaHora(aca.util.Fecha.getHoyReversa()+" "+horas+":"+minutos+":"+segundos);				
					}
					if(tipo.equals("S")) {
						alumno.setSalida("S");
						alumno.setSalidaHora(aca.util.Fecha.getHoyReversa()+" "+horas+":"+minutos+":"+segundos);
					}
					
					if(compAsistenciaAlumnoDao.existeReg(eventoId, matricula)) {
						alumno.setPlanId(alumPersonalDao.getPlanActivo(matricula));
						if (compAsistenciaAlumnoDao.updateReg(alumno)) {
							mensaje = "¡Actualizado!"; 
						}
					}else {
						alumno.setPlanId(alumPersonalDao.getPlanActivo(matricula));
						if (compAsistenciaAlumnoDao.insertReg(alumno)) {
							mensaje = "¡Grabado!";
						}
					}					
				}else{					
					alumno.setEventoId(eventoId);
					if (matricula.length()==9) matricula = "0"+matricula;
					if (matricula.length()==8) matricula = "00"+matricula;
					if (matricula.length()==7) matricula = "000"+matricula;
					if (matricula.length()==6) matricula = "0000"+matricula;
					if (alumPersonalDao.existeCodigoCredencial(matricula)){
						matricula 		= alumPersonalDao.getCodigoCredencial(matricula);
						existeAlumno = true;
						alumno.setCodigoPersonal(matricula);
					}else {
						mensaje = "¡No existe ningún alumno registrado con este coódigo de credencial!";
					}	
					if (existeAlumno) {
						if (minutos.length()==1) minutos = "0"+minutos;
						if (segundos.length()==1) segundos = "0"+segundos;
						
						if(compAsistenciaAlumnoDao.existeReg(eventoId, matricula)) {
							alumno = compAsistenciaAlumnoDao.mapeaRegId(eventoId, matricula);
							if(tipo.equals("E")) {
								alumno.setEntrada("S");
								alumno.setEntradaHora(aca.util.Fecha.getHoyReversa()+" "+horas+":"+minutos+":"+segundos);				
							}
							if(tipo.equals("S")) {
								alumno.setSalida("S");
								alumno.setSalidaHora(aca.util.Fecha.getHoyReversa()+" "+horas+":"+minutos+":"+segundos);
							}
							alumno.setPlanId(alumPersonalDao.getPlanActivo(matricula));
							if (compAsistenciaAlumnoDao.updateReg(alumno)) {
								mensaje = "¡Actualizado!"; 
							}
						}else {
							if(tipo.equals("E")) {
								alumno.setEntrada("S");
								alumno.setEntradaHora(aca.util.Fecha.getHoyReversa()+" "+horas+":"+minutos+":"+segundos);				
							}
							if(tipo.equals("S")) {
								alumno.setSalida("S");
								alumno.setSalidaHora(aca.util.Fecha.getHoyReversa()+" "+horas+":"+minutos+":"+segundos);
							}
							alumno.setPlanId(alumPersonalDao.getPlanActivo(matricula));
							if (compAsistenciaAlumnoDao.insertReg(alumno)) {
								mensaje = "¡Grabado!";
							}
						}
					}
				}	
			}
		}
		
		return "redirect:/cultural/asistencia/listado?EventoId="+eventoId+"&Tipo="+tipo+"&Size="+size+"&Mensaje="+mensaje;
	}	
	
	@RequestMapping("/cultural/asistencia/borrarAlumno")
	public String culturalAsistenciaBorrarAlumno(HttpServletRequest request){
		
		String eventoId			= request.getParameter("EventoId")==null?"0":request.getParameter("EventoId");
		String codigoPersonal	= request.getParameter("CodigoPersonal")==null?"0":request.getParameter("CodigoPersonal");
		
		if(compAsistenciaAlumnoDao.existeReg(eventoId, codigoPersonal)) {
			compAsistenciaAlumnoDao.deleteReg(eventoId, codigoPersonal);
		}
		
		return "redirect:/cultural/asistencia/listado?EventoId="+eventoId;
	}	
}