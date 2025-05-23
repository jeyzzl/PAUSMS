package aca.alumno.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AlumColorDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AlumColor alumColor){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.ALUM_COLOR(CODIGO_PERSONAL, COLOR, MENU, RELOJ, COLOR_RELOJ)"
					+ " VALUES(?, ?, ?, ?, ?)";
			
			Object[] parametros = new Object[] { alumColor.getCodigoPersonal(),alumColor.getColor(),alumColor.getMenu(),alumColor.getReloj(), alumColor.getColorReloj() };		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumColorDao|insertReg|:"+ex);
		
		}
		return ok;
	}	
	
	public boolean updateReg(AlumColor alumColor){
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.ALUM_COLOR"
					+ " SET COLOR = ?,"
					+ " MENU = ?,"
					+ " RELOJ = ?,"
					+ " COLOR_RELOJ = ?"
					+ " WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {
				alumColor.getColor(),alumColor.getMenu(),alumColor.getReloj(), alumColor.getColorReloj(), alumColor.getCodigoPersonal()
			};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumColorDao|updateReg|:"+ex);			
		
		}
		return ok;
	}	
	
	public boolean deleteReg(String codigoPersonal){
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.ALUM_COLOR WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.update(comando, parametros)==1){
 				ok = true;
 			} 	
			
		}catch(Exception ex){
			System.out.println("Error  - aca.alumno.spring.AlumColorDao|deleteReg|:"+ex);			
		
		}
		return ok;
	}
	
	public AlumColor mapeaRegId( String codigoPersonal){
		
		AlumColor color = new AlumColor();

		try{
			String comando = "SELECT CODIGO_PERSONAL, COLOR, MENU, RELOJ, COLOR_RELOJ FROM ENOC.ALUM_COLOR WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			color = enocJdbc.queryForObject(comando,  new AlumColorMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumColorDao|mapeaRegId|:"+ex);
		
		}
		
		return color;
	}

	public boolean existeReg(String codigoPersonal){
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_COLOR WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			} 
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumColorDao|existeReg|:"+ex);
		
		}
		
		return ok;
	}		
	
	public String getColor(String codigoPersonal){

		String color	= "#428bca";
		
		try{
			String comando = "SELECT COUNT(COLOR) FROM ENOC.ALUM_COLOR WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				comando = "SELECT COLOR FROM ENOC.ALUM_COLOR WHERE CODIGO_PERSONAL = ?";
				color = enocJdbc.queryForObject(comando, String.class, parametros);
				//if (color.equals("default")) color = "#4A76F2"; 
			}			 			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumColorDao|getFechaInicio|:"+ex);		
		}		
		return color;
	}
	
	public String getColorReloj(String matricula){ 
	    String color = "#545454"; //NEGRO
		try{
		    String comando = "SELECT COUNT(*) FROM ENOC.ALUM_COLOR WHERE CODIGO_PERSONAL = ?";
		    Object[] parametros = new Object[] {matricula};
		    if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
		    	comando = "SELECT COALESCE(color_reloj,'#000000') AS COLOR FROM ENOC.ALUM_COLOR WHERE CODIGO_PERSONAL = ?";
		    	color = enocJdbc.queryForObject(comando, String.class, parametros);
		    }		    
			if(color.equals("P"))color="";
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumColor|getColorReloj|:"+ex);
		}
		return color;
	}
	
	public String getColorMenu(String matricula){
	    String color="";
		try{
		    String comando = "SELECT COUNT(*) FROM ENOC.ALUM_COLOR WHERE CODIGO_PERSONAL = ?";
		    Object[] parametros = new Object[] {matricula};
		    if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
		    	comando = "SELECT MENU FROM ENOC.ALUM_COLOR WHERE CODIGO_PERSONAL = ?";
		    	color = enocJdbc.queryForObject(comando, String.class, parametros);
		    }
			if(color.equals("P"))color="";
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumColor|getColorMenu|:"+ex);
		}
		
		return color;
	}
	
	public ArrayList<AlumColor> getListAll(String orden ){		
		
		List<AlumColor> lista= new ArrayList<AlumColor>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, COLOR, MENU, RELOJ, COLOR_RELOJ FROM ENOC.ALUM_COLOR "+orden; 
			lista = enocJdbc.query(comando, new AlumColorMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumColorDao|getListAll|:"+ex);		
		}				
		
		return (ArrayList<AlumColor>) lista;
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
		}else{		
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
	
	

}