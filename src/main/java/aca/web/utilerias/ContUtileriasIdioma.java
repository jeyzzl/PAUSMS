package aca.web.utilerias;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.idioma.spring.MsgEsp;
import aca.idioma.spring.MsgEspDao;
import aca.idioma.spring.MsgIng;
import aca.idioma.spring.MsgIngDao;
import java.io.*;

@Controller
public class ContUtileriasIdioma {	
	
	@Autowired	
	private MsgEspDao msgEspDao;
	
	@Autowired	
	private MsgIngDao msgIngDao;
	
	@Autowired
	ServletContext context;

	
	@RequestMapping("utilerias/idioma/sincronizar")
	public String utileriasIdiomaActualizar(HttpServletRequest request, Model modelo) throws Exception{
		
		String accion 		= request.getParameter("Accion")==null?"0":request.getParameter("Accion");		
		String contexto 	= context.getRealPath("/");
		String archivoEsp 	= contexto+"../resources/i18n/messages_es.properties";
		String archivoIng 	= contexto+"../resources/i18n/messages_en.properties";
		int grabados		= 0; 
		int errores			= 0;
		
		// Cambia la diagonal inversa por diagonal normal para que se pueda ver el archivo en windows(localhost)
		if (java.io.File.separator.equals("\\")){		
			archivoEsp 	= archivoEsp.replace("/", "\\");
			archivoIng 	= archivoIng.replace("/", "\\");
		}		
		
		if (accion.equals("1")){
			System.out.println("¡Inicia grabar Español!");
			msgEspDao.deleteTodos();
			
			java.util.HashMap<String,String> mapEsp 	= new java.util.HashMap<String,String>();	 
			
			String llave 		= "";
			String valor 		= "";
			String lineaEsp 	= "";
			BufferedReader fileEsp = new BufferedReader(new InputStreamReader(new FileInputStream(archivoEsp), "iso-8859-1"));

			while (fileEsp.ready()){
				lineaEsp = fileEsp.readLine();
				int pos = lineaEsp.indexOf("=");
				if ( pos >= 0){
					llave = lineaEsp.substring(0, pos);			
					llave = llave.trim();			
					valor = lineaEsp.substring(pos+1, lineaEsp.length());
					valor = valor.trim();
					if (mapEsp.containsKey(llave)){
						System.out.println("Duplicado en Español:"+llave+"="+valor+"<br>");
					}else{
						mapEsp.put(llave, valor);
						MsgEsp msgEsp = new MsgEsp();
						msgEsp.setClave(llave);
						msgEsp.setValor(valor);
						if (msgEspDao.insertReg(msgEsp)){
							grabados++;
						}else {
							errores++;
						}
					}			
				}		
			}		
			fileEsp.close();
			
		}else if (accion.equals("2")){
			System.out.println("¡Inicia grabar Inglés!");
			
			msgIngDao.deleteTodos();
			
			java.util.HashMap<String,String> mapIng 	= new java.util.HashMap<String,String>();
			
			String lineaIng 	= "";
			String llave 	= "";
			String valor 	= "";
			BufferedReader fileIng = new BufferedReader(new InputStreamReader(new FileInputStream(archivoIng), "iso-8859-1"));
			while (fileIng.ready()){
				lineaIng = fileIng.readLine();
				int pos = lineaIng.indexOf("=");
				if ( pos >= 0){
					llave = lineaIng.substring(0, pos);
					valor = lineaIng.substring(pos+1, lineaIng.length());
					llave = llave.trim();
					valor = valor.trim();
					if (mapIng.containsKey(llave)){
						System.out.println("Duplicado en Inglés:"+llave+"="+valor+"<br>");
					}else{
						MsgIng msgIng = new MsgIng();
						mapIng.put(llave, valor);
						msgIng.setClave(llave);
						msgIng.setValor(valor);
						if (msgIngDao.insertReg(msgIng)){
							grabados++;
						}else {
							errores++;
						}
					}			
				}
			}
			fileIng.close();			
		}
		
		modelo.addAttribute("grabados",grabados);
		modelo.addAttribute("errores",errores);
		
		return "utilerias/idioma/sincronizar";
	}	
}