package aca;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

@WebListener
public class SesionListener implements HttpSessionListener{
	
	private static int activeSessions = 0;
	
	@Override
	public void sessionCreated(HttpSessionEvent se){
		activeSessions++;
		//System.out.println("Creando sesion.."+se.getSession().getId());
	}
	
	@Override
	public void sessionDestroyed(HttpSessionEvent se){
		if (activeSessions>0) {
			activeSessions--;
			//System.out.println("Destruyendo sesion.."+se.getSession().getId());
		}
	}
	
	public static int getActiveSessions() {
		return activeSessions;		
	}
}