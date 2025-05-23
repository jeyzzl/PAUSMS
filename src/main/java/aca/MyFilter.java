package aca;
import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.core.annotation.Order;
import org.springframework.web.client.RestTemplate;

import aca.metrics.Metricas;

import java.util.HashMap;


@Order(5)
public class MyFilter implements Filter {		

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("Init de filtro");
	}

	@Override
	public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
		String uriOriginal		= ((HttpServletRequest)request).getRequestURI();
		String uri				= uriOriginal.replaceAll("/academico", "");
		String contexto			= ((HttpServletRequest)request).getContextPath();
		HttpSession sesion		= null;	
		String codigoLogin		= "0";
		String codigoPersonal	= "0";		
		String verMonitor		= "S";
		//String rutaMetrics 	= "http://localhost:8082/academico/monitor/metrics/hikaricp.connections.active";
		String rutaMetrics		= "http://172.16.251.13:58080/academico/monitor/metrics/hikaricp.connections.active";
		
		// Codificacion de caracteres
		request.setCharacterEncoding("ISO-8859-1");
		response.setCharacterEncoding("ISO-8859-1");
		
		if (request instanceof HttpServletRequest) {
            sesion 			= ((HttpServletRequest)request).getSession();
            codigoLogin 	= sesion.getAttribute("codigoLogeado")==null?"0":(String)sesion.getAttribute("codigoLogeado");
            codigoPersonal	= sesion.getAttribute("codigoPersonal")==null?"0":(String)sesion.getAttribute("codigoPersonal");
            rutaMetrics 	= sesion.getAttribute("verMonitor")==null?"N":(String)sesion.getAttribute("verMonitor");
        }
		System.out.println("Entre1:"+uri+":"+codigoLogin);
        if ( verMonitor.equals("S") && codigoLogin.equals("9800308")){
        	
        	uri = uri.replace("//", "/");        	
        	String ruta[] = uri.split("/");        		
        	if (ruta.length > 2 && !uri.contains("cerrarPortal") && !uri.contains(".css") && !uri.contains(".jpg") && !uri.contains(".js") && !uri.contains(".png") && !uri.contains(".gif") 
        			&& !uri.contains(".html") && !uri.contains(".ico") && !uri.contains(".json") && !uri.contains("/metrics") && !uri.contains(".woff2")){
        		
        		String codPerUser		= (String) sesion.getAttribute("codigoPersonal");
        		String codAlumUser		= (String) sesion.getAttribute("codigoAlumno");
        		String porAlumnoAdmin	= (String) sesion.getAttribute("portalAlumno");     		
            	
            	boolean esEmpleado 		= (boolean) sesion.getAttribute("esEmpleado");            	
            	
            	@SuppressWarnings("unchecked")
				HashMap<String,String> mapCarpeta	= (HashMap<String,String>) sesion.getAttribute("mapCarpetas");
            	//Codificacion de caracteres
            	
        		String carpeta = ruta[1]+"/"+ruta[2]+"/";
        		if (!carpeta.substring(0,1).equals("/")) carpeta = "/"+carpeta;
        		if( mapCarpeta.containsKey(carpeta) ||       				
        			((carpeta.equals("/portales/portalAlumno/") || carpeta.equals("/portales/inscEnlinea/")) && (codPerUser.equals(codAlumUser) || porAlumnoAdmin.equals("S")) ) ||        				
        			(carpeta.equals("/pdf/aprendizaje/") && esEmpleado ) 
        			)       		
        		{
        			sesion.setAttribute("paginaBusca", uri);
        			if (verMonitor.equals("S")) {
	        			Metricas medidas 	= new Metricas();
	        			try{
	        				RestTemplate restTemplate = new RestTemplate();
	        				medidas = restTemplate.getForObject(rutaMetrics, Metricas.class);
	        				java.util.Date hora = new java.util.Date();
	        				System.out.println("Pool:"+medidas.getMeasurements().get(0).getValue()+" Time:"+hora+" Usuario:"+codPerUser+" Alumno:"+codAlumUser+" Ruta:"+uri);
	        			}catch(Exception ex) {
	        				System.out.println("Error Rest:"+ex);
	        			}        		
	        			//System.out.println("Medidas: "+medidas.getName()+":"+medidas.getDescription()+":"+medidas.getBaseUnit()+":"+ medidas.getMeasurements().get(0).getValue());
        			}        			
        			chain.doFilter( request, response );        			
        		}else{        			
        			System.out.println("Error:"+uri+" Codigo:"+codigoPersonal);
        			((HttpServletResponse)response).sendRedirect( contexto+"/errorAcceso?Error=1&Ruta="+uri);
        		}
        	}else{        		
        		chain.doFilter( request , response );
        	}            
        }else {       	
        	chain.doFilter( request , response );
        }  
	}

	@Override
	public void destroy() {
		System.out.println("Fin de filtro");
	}
}