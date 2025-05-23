/**
 * 
 */
package aca;

import org.springframework.stereotype.Component;

import aca.financiero.spring.FesCcobro;
import aca.notifica.spring.NotiProcesoPendiente;
import aca.notifica.spring.NotiProcesoPendienteDao;
import aca.notifica.spring.NotiMensajes;
import aca.notifica.spring.NotiMensajesDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;

/**
 * @author elifo
 *
 */
@Component
@EnableScheduling
public class TareasProgramadas {
	private static final Logger log = LoggerFactory.getLogger(TareasProgramadas.class);
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	@Autowired	
	private NotiProcesoPendienteDao notiProcesoPendienteDao;
	
	@Autowired	
	private NotiMensajesDao notiMensajesDao;
	
	@Autowired
	private aca.financiero.spring.FesCcobroDao fesCcobroDao;
	
	@Autowired
	private AlumPersonalDao alumPersonalDao;
	
	@Scheduled(fixedRate = 300000)
	public void procesarNotificacionesPendientes() {
		
		List<NotiProcesoPendiente> lista	= notiProcesoPendienteDao.lisAll("ORDER BY CODIGO_PERSONAL, NOTI_PROCESO_PENDIENTE.FECHA_INICIO ASC");
		for(NotiProcesoPendiente npp: lista) {			
			
			/* El Tipo 1 se da de alta cuando se manda generar un cálculo de cobro
			 * y tiene que enviar a la pagina del paso 2 de inscripción justo en la
			 * parte donde se revisan las materias.
			 * Todo esto solo se da de alta en la notificación si ya existe el 
			 * cálculo de cobro.
			 * El campo de datos trae cargaId+","+bloqueId+","+periodoId
			 * (El número de Tipo siempre debe concordar entre NOTI_MENSAJES y NOTI_PROCESO_PENDIENTE)
			 */
			/*
			if(npp.getTipo().equals("1")) { 
				String[] datos = npp.getDatos().split(",");
				HashMap<String, FesCcobro> mapaFesCobro = fesCcobroDao.mapaFesCobroPorMatricula(npp.getCodigoPersonal());
				String inscrito 	= "N";
				NotiMensajes notiMensajes = new NotiMensajes();
				if (mapaFesCobro.containsKey(datos[0]+datos[1])) {
					inscrito 		= mapaFesCobro.get(datos[0]+datos[1]).getInscrito();
					if(inscrito.equals("N")) {
						String url = "/academico/portales/alumno/verMaterias?CargaId="+datos[0]+"&BloqueId="+datos[1];
						boolean nuevo = false, borrar = false;
						if(notiMensajesDao.existeReg(npp.getCodigoPersonal(), npp.getTipo())) {
							//si existe ahora revisamos si la url es la misma para saber si hacemos insert o update
							notiMensajes = notiMensajesDao.mapeaRegTipo(npp.getCodigoPersonal(), npp.getTipo());
							if(!notiMensajes.getUrl().equals(url)) { //solo si la url es diferente se crea un nuevo registro. Solo puede ser diferente por la carga y el bloque
								nuevo = true;
							}
						}else {
							nuevo = true;
						}
						notiMensajes.setCodigoPersonal(npp.getCodigoPersonal());
						notiMensajes.setTipo(npp.getTipo());
						notiMensajes.setMensaje("Tus materias han sido asignadas en "+datos[0]+"-"+datos[1]+", revisalas, especifica la forma de pagar, y confirma");
						notiMensajes.setUrl(url);
						notiMensajes.setFecha(npp.getFechaInicio());
						if(nuevo) {
							notiMensajes.setSilenciado("N");
							notiMensajes.setVisto("N");
							notiMensajes.setSms("S");
							if(notiMensajesDao.insertReg(notiMensajes)) {
								borrar = true;
							}
						}else {
							if(notiMensajesDao.updateReg(notiMensajes)) {
								borrar = true;
							}
						}
						if(borrar) {
							notiProcesoPendienteDao.deleteReg(npp.getId());
							continue;
						}
					}else {
						//Si ya esta inscrito entonces se borra el registro de NotiProcesoPendiente
						notiProcesoPendienteDao.deleteReg(npp.getId());
						continue;
					}
				}
			}
			*/
			/* Este codigo del try-catch debe estar siempre al final del ciclo for
			 * Primero dejamos que se procesen las notificaciones pendientes
			 * y si una vez procesadas borramos las que tengan mas de una hora
			 * porque el proceso que esperamos no puede durar mas de una hora en terminar.
			 * Algo que es importante hacer en el codigo del for es que si se borra
			 * una notificacion pendiente {notiProcesoPendienteDao.deleteReg(npp.getId());}
			 * entonces hay que poner un "continue;" para que no llegue a este chequeo 
			 * ya que no va a existir ese registro. Mas que nada es para no tener
			 * muchas excepciones en el log.
			 */
			try {
				Date haceUnaHora = new Date(System.currentTimeMillis() - 3600 * 1000);
				Date fechaNotificacion = new SimpleDateFormat("dd/MM/yy HH:mm:ss").parse(npp.getFechaInicio());
				if(fechaNotificacion.before(haceUnaHora)) {
					notiProcesoPendienteDao.deleteReg(npp.getId());
					continue;
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
	
	@Scheduled(fixedDelay = 600000, initialDelay=5000)
	public void procesarSMS() {
		
		//log.info("Iniciando procesarSMS (cada 600 segundos) despues de la última ejecución. Inicia 5 segundos despues de que inicia spring");
		//Envio de SMS
		/*
		List<NotiMensajes> listaNotificaciones = notiMensajesDao.lisSMSPendientes("ORDER BY NOTI_MENSAJES.FECHA ASC");
		for(NotiMensajes nm: listaNotificaciones) {
			try {
				String fechaHoy = aca.util.Fecha.getHoy();
				String fech = nm.getFecha().substring(0, 10);
			
				Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(fechaHoy);
				Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(fech);  
				
				int milisecondsByDay = 86400000;
				int dias = (int) ((date1.getTime()-date2.getTime()) / milisecondsByDay);
				
				if(dias < 1) {
					//log.info(nm.getCodigoPersonal()+","+nm.getSms()+","+nm.getMensaje());
					AlumPersonal alumPersonal = alumPersonalDao.mapeaRegId(nm.getCodigoPersonal());
					
					String telefono = alumPersonal.getTelefono()==null?"":alumPersonal.getTelefono();
					String telefonoBueno = telefono.replaceAll(" ", "").replaceAll("-", "").replaceAll("[()]", "").replaceAll("^044", "").replaceAll("^045", "");
					boolean telCorrecto = false;
					//Tal vez puedo solo comprobar que sean exactamente 10 y que solo sean números
					if(telefonoBueno.matches("[0-9]+") && telefonoBueno.length() == 10) {
						telCorrecto = true;
						//log.info("numero: "+telefono+"||Procesado: |"+telefonoBueno+"|");
						
						//Vamos a enviar el mensaje SMS con una petición http
						String url = "http://172.16.169.146:8097/SendSMS?username=admin&password=Jete17&phone="+telefonoBueno+"&message=";
						String mensaje = "UM-Informa: "+nm.getMensaje();
						boolean encoded = false;
						try{
							mensaje = URLEncoder.encode(mensaje, StandardCharsets.UTF_8.toString());
							encoded = true;
						}catch(Exception e) {
							log.info("Error en encode de procesarSMS()");
							e.printStackTrace();
						}
						if(encoded) {
							String respuesta = executePost(url+mensaje);
							//log.info("Respuesta web: "+respuesta);
							if(respuesta != null) {
								if(respuesta.indexOf("\"message\":\"Message has been sent\"") != -1) {
									//log.info("Mensaje enviado");
									nm.setSms("E");
									notiMensajesDao.updateReg(nm);
								}else {
									log.info("procesarSMS() - Respuesta web: "+respuesta);
								}
							}else {
								log.info("procesarSMS() - Servidor (telefono celular) no responde");
							}
						}
					}
					
					//Si el teléfono no es válido, entonces ponemos una D (Descartado) en el registro SMS del mensaje
					if(!telCorrecto) {
						nm.setSms("D");
						notiMensajesDao.updateReg(nm);
					}
				}
			} catch (ParseException e1) {
				e1.printStackTrace();
			}  
		}
		*/
		//log.info("Termina procesarSMS");
	}
	
	private static String executePost(String targetURL) {
		HttpURLConnection connection = null;
		
		try {
			//Create connection
			
			URL url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			
			connection.setUseCaches(false);
			connection.setDoOutput(true);
			
			//Send request
			/*DataOutputStream wr = new DataOutputStream (
			    connection.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.close();*/
			
			//Get Response  
			BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
			String line;
			while ((line = rd.readLine()) != null) {
			  response.append(line);
			  response.append('\r');
			}
			rd.close();
			return response.toString();
		} catch (Exception e) {
		    //e.printStackTrace();
		    return null;
		} finally {
		    if (connection != null) {
		      connection.disconnect();
		    }
		}
	}
}
