package aca;
import java.io.IOException;
import java.util.List;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumPlanDao;
import aca.kardex.spring.KrdxCursoActDao;
import aca.mentores.spring.MentAlumnoDao;

@Order(2)
@Component
public class FilterPortal implements Filter {
	
	@Autowired
	AccesoDao accesoDao;
	
	@Autowired
	AlumPlanDao alumPlanDao;
	
	@Autowired
	KrdxCursoActDao krdxKursoActDao;
	
	@Autowired
	MentAlumnoDao mentAlumnoDao;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("Init de filtro");
	}

	@Override
	public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
		String uriOriginal		= ((HttpServletRequest)request).getRequestURI();		
		String contexto			= ((HttpServletRequest)request).getContextPath();
		HttpSession sesion		= null;	
		String codigoLogin		= "0";
		String codigoPersonal	= "0";
		String codigoAlumno		= "0";
		
		// Codificacion de caracteres
		request.setCharacterEncoding("ISO-8859-1");
		response.setCharacterEncoding("ISO-8859-1");
		
		if (request instanceof HttpServletRequest) {
            sesion 			= ((HttpServletRequest)request).getSession();
            codigoLogin 	= sesion.getAttribute("codigoLogeado")==null?"0":(String)sesion.getAttribute("codigoLogeado");
            codigoAlumno 	= sesion.getAttribute("codigoAlumno")==null?"0":(String)sesion.getAttribute("codigoAlumno");
            codigoPersonal	= sesion.getAttribute("codigoPersonal")==null?"0":(String)sesion.getAttribute("codigoPersonal");           
        }		
		if (uriOriginal.contains("/portales/alumno/") && !uriOriginal.contains(".gif") && !uriOriginal.contains(".png") && !uriOriginal.contains(".pdf")){
			// Valida si el usuario tiene permiso de accesar el portal del alumno
			
			String usuarioAdmin 		= accesoDao.mapeaRegId(codigoPersonal).getAdministrador();
			String usuarioSuper 		= accesoDao.mapeaRegId(codigoPersonal).getSupervisor();
			String accesosUsuario		= accesoDao.getAccesos(codigoPersonal);
			List<String> lisCarrerasAlum= alumPlanDao.lisCarrerasPorAlumno(codigoAlumno);
			boolean fueAlumno			= krdxKursoActDao.fueAlumnoDelMestro(codigoAlumno, codigoPersonal);
			boolean fueAconsejado		= mentAlumnoDao.fueAconsejadoDelMentor(codigoAlumno, codigoPersonal);
			
			boolean permisoDeAcceso		= false;
			if (usuarioAdmin.equals("S")) permisoDeAcceso = true;
			if (usuarioSuper.equals("S")) permisoDeAcceso = true;
			if (fueAlumno) permisoDeAcceso = true;
			if (fueAconsejado) permisoDeAcceso = true;
			if (permisoDeAcceso == false) {
				for (String carrera : lisCarrerasAlum){
					if (accesosUsuario.contains(carrera)) permisoDeAcceso = true;
				}
			}
			if (codigoLogin.equals(codigoPersonal) || codigoLogin.equals("9800308") || codigoLogin.equals("9800058") || permisoDeAcceso) {
				chain.doFilter( request, response );
			}else {
				System.out.println("Ruta error:"+contexto);
				((HttpServletResponse)response).sendRedirect( contexto+"/sinAcceso");
			}
						
			//chain.doFilter( request, response );
		}else {
			chain.doFilter( request, response );
		}		
	}

	@Override
	public void destroy() {
		System.out.println("Fin de filtro");
	}
}