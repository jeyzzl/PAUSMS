package aca.portal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Alumno{

	public Alumno(){
	}
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
	
	public void guardaColor(Connection conn, String matricula, String color) throws SQLException{
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		try{
		    String sql="select color from ENOC.alum_color where codigo_personal=?"; 
		    ps2 = conn.prepareStatement(sql);
			ps2.setString(1,matricula);
			rs = ps2.executeQuery();
			if(rs.next()){
			    ps = conn.prepareStatement("update ENOC.alum_color set color=? " + 
				"where codigo_personal=?");
			    ps.setString(1,color);
			    ps.setString(2,matricula);
			    ps.executeUpdate();			    
			}else{
			    ps = conn.prepareStatement("INSERT INTO ENOC.alum_color(CODIGO_PERSONAL, COLOR, MENU) " + 
				"VALUES(?,?,?)");
			    ps.setString(1,matricula);
			    ps.setString(2,color);
			    ps.setString(3,"default");
			    ps.executeUpdate();			    
			}
		}catch(Exception ex){
			System.out.println("Error - aca.portal.Alumno|guardaColor|:"+ex);
		}finally{			
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
			try { ps2.close(); } catch (Exception ignore) { }			
		}
	}
	
	public String obtenColor(Connection conn, String matricula) throws SQLException{
	    String color="";
	    PreparedStatement ps = null;
	    ResultSet rs = null;
		try{
		    String sql="select color from ENOC.alum_color where codigo_personal=?"; 
		    ps = conn.prepareStatement(sql);
			ps.setString(1,matricula);
			rs = ps.executeQuery();
			if(rs.next())color=rs.getString(1);
			if(color.equals("P"))color="";
		}catch(Exception ex){
			System.out.println("Error - aca.portal.Alumno|obtenColor|:"+ex);
		}finally{			
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }			
		}
		return color;
	}
	
	public void guardaColorMenu(Connection conn, String matricula, String colorMenu) throws SQLException{
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		try{
		    String sql="select color from ENOC.alum_color where codigo_personal=?"; 
		    ps2 = conn.prepareStatement(sql);
			ps2.setString(1,matricula);
			rs = ps2.executeQuery();
			if(rs.next()){
			    ps = conn.prepareStatement("update ENOC.alum_color set menu=? " + 
				"where codigo_personal=?");
			    ps.setString(1,colorMenu);
			    ps.setString(2,matricula);
			    ps.executeUpdate();			    
			}else{
			    ps = conn.prepareStatement("INSERT INTO ENOC.alum_color(CODIGO_PERSONAL, COLOR, MENU) " + 
				"VALUES(?,?,?)");
			    ps.setString(1,matricula);
			    ps.setString(2,"default");
			    ps.setString(3,colorMenu);
			    ps.executeUpdate();			    
			}
		}catch(Exception ex){
			System.out.println("Error - aca.portal.Alumno|guardaColorMenu|:"+ex);
		}finally{			
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }	
			try { ps2.close(); } catch (Exception ignore) { }
		}
	}
	
	public String obtenColorMenu(Connection conn, String matricula) throws SQLException{
	    String color="";
	    PreparedStatement ps = null;
	    ResultSet rs = null;
		try{
		    String sql="select menu from ENOC.alum_color where codigo_personal=?"; 
		    ps = conn.prepareStatement(sql);
			ps.setString(1,matricula);
			rs = ps.executeQuery();
			if(rs.next())color=rs.getString(1);
			if(color.equals("P"))color="";
		}catch(Exception ex){
			System.out.println("Error - aca.portal.Alumno|obtenColorMenu|:"+ex);
		}finally{		
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
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
	
	public void guardaReloj(Connection conn, String matricula, String reloj) throws SQLException{
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		try{
		    String sql="select reloj from ENOC.alum_color where codigo_personal=?"; 
		    ps2 = conn.prepareStatement(sql);
			ps2.setString(1,matricula);
			rs = ps2.executeQuery();
			if(rs.next()){
			    ps = conn.prepareStatement("update ENOC.alum_color set reloj=? " + 
				"where codigo_personal=?");
			    ps.setString(1,reloj);
			    ps.setString(2,matricula);
			    ps.executeUpdate();			   
			}else{
			    ps = conn.prepareStatement("INSERT INTO ENOC.alum_color(CODIGO_PERSONAL, RELOJ) " + 
				"VALUES(?,?)");
			    ps.setString(1,matricula);
			    ps.setString(2,reloj);
			    ps.executeUpdate();			    
			}
		}catch(Exception ex){
			System.out.println("Error - aca.portal.Alumno|guardaReloj|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
			try { ps2.close(); } catch (Exception ignore) { }
		}
	}
	
	public String obtenReloj(Connection conn, String matricula) throws SQLException{
	    String color="";
	    PreparedStatement ps = null;
	    ResultSet rs = null;
		try{
		    String sql="select reloj from ENOC.alum_color where codigo_personal=?"; 
		    ps = conn.prepareStatement(sql);
			ps.setString(1,matricula);
			rs = ps.executeQuery();
			if(rs.next())color=rs.getString(1);
			if(color.equals("P"))color="";
		}catch(Exception ex){
			System.out.println("Error - aca.portal.Alumno|obtenReloj|:"+ex);
		}finally{		
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return color;
	}
	
	public void guardaColorReloj(Connection conn, String matricula, String colorReloj) throws SQLException{
		PreparedStatement ps 	= null;
		PreparedStatement ps2 	= null;
		ResultSet rs = null;
		try{
		    String sql="select color_reloj from ENOC.alum_color where codigo_personal=?"; 
		    ps2 = conn.prepareStatement(sql);
			ps2.setString(1,matricula);
			rs = ps2.executeQuery();
			if(rs.next()){
			    ps = conn.prepareStatement("update ENOC.alum_color set color_reloj=? " + 
				"where codigo_personal=?");
			    ps.setString(1,colorReloj);
			    ps.setString(2,matricula);
			    ps.executeUpdate();			    
			}else{
			    ps = conn.prepareStatement("INSERT INTO ENOC.alum_color(CODIGO_PERSONAL, color_reloj) " + 
				"VALUES(?,?)");
			    ps.setString(1,matricula);
			    ps.setString(2,colorReloj);
			    ps.executeUpdate();			    
			}
		}catch(Exception ex){
			System.out.println("Error - aca.portal.Alumno|guardaColorReloj|:"+ex);
		}finally{			
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
			try { ps2.close(); } catch (Exception ignore) { }
		}
	}
	
	public String obtenColorReloj(Connection conn, String matricula) throws SQLException{	    
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    String color = "#545454"; //NEGRO
		try{
		    String sql="SELECT COALESCE(color_reloj,'#000000') AS COLOR FROM ENOC.ALUM_COLOR WHERE CODIGO_PERSONAL=?"; 
		    ps = conn.prepareStatement(sql);
			ps.setString(1,matricula);
			rs = ps.executeQuery();
			if(rs.next())color=rs.getString(1);
			if(color.equals("P"))color="";
		}catch(Exception ex){
			System.out.println("Error - aca.portal.Alumno|obtenColorReloj|:"+ex);
		}finally{			
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return color;
	}
}