package aca.portal.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AlumnoPortalDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;

	public String dibujaTab(int numTab, String tab, String Nombre){
		String ret="",sOn="";
		if (Integer.parseInt(tab)==numTab){ 
			sOn="on";
		}
		else sOn="off";
		ret = 
		"<table onclick=\"CambiaTab("+String.valueOf(numTab)+")\" style=\"cursor:pointer;\" border='0' cellspacing='0' cellpadding='0' align='left'>"+
			"<tr>"+ 
				"<td><img src='img/tab_"+sOn+"_left.gif' height='24' width='16'></td>"+
				"<td ONSELECTSTART='return false' background='img/tab_"+sOn+"_middle.gif'>"+
					"<font color=\"#000000\" size=\"1\" face=\"Arial, Helvetica, sans-serif\">"+Nombre+"</font>"+
				"</td>"+
				"<td><img src='img/tab_"+sOn+"_right.gif' height='24' width='16'></td>"+
			"</tr>"+
		"</table>";		
		return ret;
	}
	
	public String dibujaTab2(int num, int ac, String pagina, String nombre){
		String script= "<li onClick=\"cambia("+num+");parent.contenidoP.location.href='"+pagina+"';\"";
		if(ac==num)script+=" id='current'";
		script+="><a ";
		if(ac==num)script+="style=\"font-size:11px;\" ";
		script+="onmouseover=\"this.style.color='#765'\" onMouseOut=\"this.style.color=''\">"+nombre+"</a></li>";
		return script;
	}
	
	public void guardaColor(String matricula, String color){
		try{
		    String comando = "SELECT COLOR FROM ENOC.ALUM_COLOR WHERE CODIGO_PERSONAL = ?"; 
			
			Object[] parametros = new Object[] {matricula};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				
				parametros = new Object[] {color,matricula};
				comando = "UPDATE ENOC.ALUM_COLOR SET COLOR = ? WHERE CODIGO_PERSONAL = ?"; 
				
				enocJdbc.update(comando,parametros);
			}else {
				parametros = new Object[] {matricula,color,"default"};
				comando = "INSERT INTO ENOC.ALUM_COLOR(CODIGO_PERSONAL, COLOR, MENU) VALUES(?,?,?)";
				
				enocJdbc.update(comando,parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.portal.spring.AlumnoDao|guardaColor|:"+ex);
		}
	}
	
	public String obtenColor(String matricula){
	    String color = "";

	    try{
		    String comando = "SELECT COLOR FROM ENOC.ALUM_COLOR WHERE CODIGO_PERSONAL = ?"; 
			
			Object[] parametros = new Object[] {matricula};
			
			if (enocJdbc.update(comando, parametros) >= 1) {
				color = enocJdbc.queryForObject(comando, String.class, parametros);
			}
		
			if(color.equals("P")) {
				color = "";
			}
	    
	    }catch(Exception ex){
			System.out.println("Error - aca.portal.spring.AlumnoDao|obtenColor|:"+ex);
		}
		return color;
	}
	
	public void guardaColorMenu(String matricula, String colorMenu){
		try{
		    String comando = "SELECT COLOR FROM ENOC.ALUM_COLOR WHERE CODIGO_PERSONAL = ?"; 
			
			Object[] parametros = new Object[] {matricula};
			
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				
				parametros = new Object[] {colorMenu,matricula};
				comando = "UPDATE ENOC.ALUM_COLOR SET MENU = ? WHERE CODIGO_PERSONAL = ?";
				
				enocJdbc.update(comando,parametros);
			}else {
				parametros = new Object[] {matricula,"default",colorMenu};
				comando = "INSERT INTO ENOC.ALUM_COLOR(CODIGO_PERSONAL, COLOR, MENU) VALUES(?,?,?)";
				
				enocJdbc.update(comando,parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.portal.spring.AlumnoDao|guardaColorMenu|:"+ex);
		}
	}
	
	public String obtenColorMenu(String matricula){
	    String color = "";
		try{
		    String comando = "SELECT MENU FROM ENOC.ALUM_COLOR WHERE CODIGO_PERSONAL = ?";			
			Object[] parametros = new Object[] {matricula};			
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				color = enocJdbc.queryForObject(comando, String.class, parametros);
			}
			if(color.equals("P")) {
				color = "";
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.portal.spring.AlumnoDao|obtenColorMenu|:"+ex);
		}
		return color;
	}
	
	public String modificarColor(String color, int cambio){
		
		if(color.length()==7) color = color.substring(1);
		
		int r = Integer.parseInt(color.substring(0,2), 16);
		int g = Integer.parseInt(color.substring(2,4), 16);
		int b = Integer.parseInt(color.substring(4,6), 16);
		
		if((cambio+"").charAt(0)=='-'){
			cambio = cambio*-1;
			if((r-cambio)<0){
				r=0;
			}else{
				r = r-cambio;
			}			
			if((g-cambio)<0){
				g=0;
			}else{
				g = g-cambio;
			}
			if((b-cambio)<0){
				b=0;
			}else{
				b = b-cambio;
			}
			
			String tmpColor = "#";
			String Sr = Integer.toHexString(r);
			String Sg = Integer.toHexString(g);
			String Sb = Integer.toHexString(b);
			
			if(Sr.length()==1) tmpColor+=("0"+Sr);
			else tmpColor+=Sr;
				
			if(Sg.length()==1) tmpColor+=("0"+Sg);
			else tmpColor+=Sg; 
			
			if(Sb.length()==1) tmpColor+=("0"+Sb);
			else tmpColor+=Sb;
			
			return tmpColor;
		}
		else{		
			if((r+cambio)>255){
				r=255;
			}else{
				r+=cambio;
			}
			if((g+cambio)>255){
				g=255;
			}else{
				g+=cambio;
			}		
			if((b+cambio)>255){
				b=255;
			}else{
				b+=cambio;
			}
		}
		
		return "#"+Integer.toHexString(r)+Integer.toHexString(g)+Integer.toHexString(b);
	}
	
	public boolean colorNegroLetra(String colorH){//Determina la luminosidad de un color, regresa si la letra es negra(true) o blanca
		if(colorH.length()==7) colorH = colorH.substring(1);
		
		float r = Integer.parseInt(colorH.substring(0,2), 16);
		float g = Integer.parseInt(colorH.substring(2,4), 16);
		float b = Integer.parseInt(colorH.substring(4,6), 16);
		
		r /= 255;
		g /= 255;
		b /= 255;
		
	    float tmpMax = Math.max(r, g);
	    float max = Math.max(tmpMax, b);
	    float tmpMin = Math.min(r, g);
	    float min = Math.min(tmpMin, b);
	    
	    float l =(max + min)/2;
		return l>0.5;
	}
	
	public void guardaReloj(String matricula, String reloj) {
		try{
		    String comando = "SELECT RELOJ FROM ENOC.ALUM_COLOR WHERE CODIGO_PERSONAL = ?"; 
			
			Object[] parametros = new Object[] {matricula};
			
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				
				parametros = new Object[] {reloj,matricula};
				comando = "UPDATE ENOC.ALUM_COLOR SET RELOJ = ? WHERE CODIGO_PERSONAL = ?";
				
				enocJdbc.update(comando,parametros);
			}else {
				parametros = new Object[] {matricula,reloj};
				comando = "INSERT INTO ENOC.ALUM_COLOR(CODIGO_PERSONAL, RELOJ) VALUES(?,?)";
				
				enocJdbc.update(comando,parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.portal.spring.AlumnoDao|guardaReloj|:"+ex);
		}
	}
	
	public String obtenReloj(String matricula) {
	    String reloj = "";
		try{
		    String comando = "SELECT RELOJ FROM ENOC.ALUM_COLOR WHERE CODIGO_PERSONAL = ?"; 
			
			Object[] parametros = new Object[] {matricula};
			
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				reloj = enocJdbc.queryForObject(comando, String.class, parametros);
			}
			
			if(reloj.equals("P")) {
				reloj="";
			}
		}catch(Exception ex){
			System.out.println("Error - aca.portal.spring.AlumnoDao|obtenReloj|:"+ex);
		}

		return reloj;
	}
	
	public void guardaColorReloj(String matricula, String colorReloj) {
		try{
		    String comando = "SELECT COLOR_RELOJ FROM ENOC.ALUM_COLOR WHERE CODIGO_PERSONAL = ?"; 
			
			Object[] parametros = new Object[] {matricula};
			
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				
				parametros = new Object[] {colorReloj,matricula};
				comando = "UPDATE ENOC.ALUM_COLOR SET COLOR_RELOJ = ? WHERE CODIGO_PERSONAL = ?";
				
				enocJdbc.update(comando,parametros);
			}else {
				parametros = new Object[] {matricula,colorReloj};
				comando = "INSERT INTO ENOC.ALUM_COLOR(CODIGO_PERSONAL, COLOR_RELOJ) VALUES(?,?)";
				
				enocJdbc.update(comando,parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.portal.spring.AlumnoDao|guardaColorReloj|:"+ex);
		}
	}
	
	public String obtenColorReloj(String matricula) {	    
	    String color = "#545454"; //NEGRO
		try{
		    String comando = "SELECT COALESCE(COLOR_RELOJ,'#000000') AS COLOR FROM ENOC.ALUM_COLOR WHERE CODIGO_PERSONAL = ?"; 
			
			Object[] parametros = new Object[] {matricula};
			
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				color = enocJdbc.queryForObject(comando, String.class, parametros);
			}
			
			if(color.equals("P")) {
				color = "";
			}
		}catch(Exception ex){
			System.out.println("Error - aca.portal.spring.AlumnoDao|obtenColorReloj|:"+ex);
		}

		return color;
	}
}