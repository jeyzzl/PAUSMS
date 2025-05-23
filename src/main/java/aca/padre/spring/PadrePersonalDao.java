package aca.padre.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PadrePersonalDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(PadrePersonal padrePersonal ) {
 		boolean ok = false;
 		
 		try{
 			String comando = "INSERT INTO ENOC.PADRE_PERSONAL(PADRE_ID, NOMBRE, PATERNO, MATERNO, CORREO, TELEFONO, TIPO)";
 			
 			Object[] parametros = new Object[] {
 					padrePersonal.getPadreId(),padrePersonal.getNombre(),padrePersonal.getPaterno(),padrePersonal.getMaterno(),padrePersonal.getCorreo(),padrePersonal.getTelefono(),padrePersonal.getTipo()
 			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
 		}catch(Exception ex){
 			System.out.println("Error - aca.padre.spring.PadrePersonalDao|insertReg|:"+ex);	
 		}
 		
 		return ok;
 	}	
 	
 	public boolean updateReg(PadrePersonal padrePersonal) {
 		boolean ok = false;

 		try{
 			String comando = "UPDATE ENOC.PADRE_PERSONAL "+ 
 				"SET "+				
 				"NOMBRE = ?, "+
 				"PADRE_ID = ?, "+
 				"NOMBRE = ?, "+
 				"PATERNO = ?, "+
 				"MATERNO = ?, "+
 				"CORREO = ?, "+
 				"TELEFONO = ?, "+
 				"TIPO = TO_NUMBER(COALESCE(?, '1'),'99')";
 			
 			Object[] parametros = new Object[] {
				padrePersonal.getPadreId(),padrePersonal.getNombre(),padrePersonal.getPaterno(),padrePersonal.getMaterno(),padrePersonal.getCorreo(),padrePersonal.getTelefono(),padrePersonal.getTipo()
 	 		};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
 		}catch(Exception ex){
 			System.out.println("Error - aca.padre.spring.PadrePersonalDao|updateReg|:"+ex);		
 		}
 		
 		return ok;
 	}
 	
 	public boolean deleteReg(String padreId ) {
 		boolean ok = false;

 		try{
 			String comando = "DELETE FROM ENOC.PADRE_PERSONAL "+ 
 				"WHERE PADRE_ID = ? " ;
 			
 			Object[] parametros = new Object[] {padreId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
 		}catch(Exception ex){
 			System.out.println("Error - aca.padre.spring.PadrePersonalDao|deleteReg|:"+ex);			
 		}

 		return ok;
 	}
 	
 	public PadrePersonal mapeaRegId(String padreId ){
 		PadrePersonal padrePersonal = new PadrePersonal();
 		
 		try{
	 		String comando = "SELECT PADRE_ID, NOMBRE, PATERNO, MATERNO, CORREO, TELEFONO, TIPO"+	
	 			" FROM ENOC.PADRE_PERSONAL WHERE PADRE_ID = ?";
	 		
	 		Object[] parametros = new Object[] {padreId};
	 		padrePersonal = enocJdbc.queryForObject(comando, new PadrePersonalMapper(), parametros);
 		}catch(Exception ex){
 			System.out.println("Error - aca.padre.spring.PadrePersonalDao|mapeaRegId|:"+ex);
 		}

 		return padrePersonal;
 	}

 	public boolean existeReg(String padreId) {
 		boolean 		ok 	= false;
 		
 		try{
 			String comando = "SELECT COUNT(*) FROM ENOC.PADRE_PERSONAL WHERE PADRE_ID = ?";

 			Object[] parametros = new Object[] {padreId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}
 		}catch(Exception ex){
 			System.out.println("Error - aca.padre.spring.PadrePersonalDao|existeReg|:"+ex);
 		}
 		
 		return ok;
 	}
 	
 	public String maximoReg() {
 		int maximo 				= 1;
 		String padreId			= "P000001";
 		
 		try{
 			String comando = "SELECT COALESCE(MAX(TO_NUMBER(SUBSTR(PADRE_ID,2,6))+1),1) AS MAXIMO FROM ENOC.PADRE_PERSONAL";
 			
 			maximo = enocJdbc.queryForObject(comando,Integer.class);
 			
			if (String.valueOf(maximo).length() == 1) {
				padreId = "P00000"+ String.valueOf(maximo);
			}else if (String.valueOf(maximo).length() == 2) {
				padreId = "P0000"+ String.valueOf(maximo);
			}else if (String.valueOf(maximo).length() == 3) { 
				padreId = "P000"+ String.valueOf(maximo);
			}else if (String.valueOf(maximo).length() == 4) { 
				padreId = "P00"+ String.valueOf(maximo);
			}else if (String.valueOf(maximo).length() == 5) { 
				padreId = "P0"+ String.valueOf(maximo);
			}else if (String.valueOf(maximo).length() == 6) { 
				padreId = "P"+ String.valueOf(maximo); 		
			}
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.padre.spring.PadrePersonalDao|maximoReg|:"+ex);
 		}
 		
 		return padreId;
 	}
 	
 	public boolean esPadre(String padreId ){
 		boolean ok 				= false;
 		
 		try{
 			String comando = "SELECT COUNT(*) FROM ENOC.PADRE_PERSONAL WHERE PADRE_ID = ? ";
 			
 			Object[] parametros = new Object[] {padreId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}		
 		}catch(Exception ex){
 			System.out.println("Error - aca.padre.spring.PadrePersonalDao|esPadre|:"+ex);
 		}
 		
 		return ok;
 	}
 	
 	public String getNombrePadre(String padre_Id, String opcion) {
 		String nombre	= "0000000";
 		String comando  = "";
 		
 		try{
 			if (opcion.equals("NOMBRE")){
 				comando = "SELECT NOMBRE||' '||PATERNO||' '||MATERNO AS NOMBRE FROM ENOC.PADRE_PERSONAL WHERE PADRE_ID = ? ";
 			}else if (opcion.equals("APELLIDO")){
 				comando = "SELECT PATERNO||' '||MATERNO||' '||NOMBRE AS NOMBRE FROM ENOC.PADRE_PERSONAL WHERE PADRE_ID = ? ";
 			}else{
 				comando = "SELECT NOMBRE||' '||PATERNO||' '||MATERNO AS NOMBRE FROM ENOC.PADRE_PERSONAL WHERE PADRE_ID = ? ";
 			}
 			
 			Object[] parametros = new Object[] {padre_Id};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				nombre =  enocJdbc.queryForObject(comando,String.class);
			}	
 		}catch(Exception ex){
 			System.out.println("Error - aca.padre.spring.PadrePersonalDao|getNombrePadre|:"+ex);
 		}
 		
 		return nombre;
 	}
 	
 	public String getNombreCorto(String padreId) {
 		String nombre	 		= "";
 		String apellido			= "";
 		String temp				= "";
 		
 		try{
 			String comando = "SELECT NOMBRE || '-' || PATERNO || '-' || COALESCE(MATERNO,'.') AS NOMBRE FROM ENOC.PADRE_PERSONAL WHERE PADRE_ID = ?";
 			
 			Object[] parametros = new Object[] {padreId};
 			nombre =  enocJdbc.queryForObject(comando,String.class,parametros);
 			
 			String [] arrayNombre = nombre.split("-");
 			
 			nombre = arrayNombre[0];
 			apellido = arrayNombre[1]+" "+arrayNombre[2].substring(0,1)+".";
 			
 			if(!nombre.equals("") && !apellido.equals("")){
 				StringTokenizer token = new StringTokenizer(nombre," ");
 				temp = token.nextToken();
 				
 				if(!temp.equals("")){
 					nombre = temp;
 					if(token.countTokens() >= 1){
 						temp = token.nextToken();
 						nombre += " " + temp.charAt(0) + ".";
 					}
 				}
 				nombre += " " + apellido;
 			}

 		}catch(Exception ex){
 			System.out.println("Error  - aca.padre.spring.PadrePersonalDao|getNombreCorto|: "+ex);
 		}
 		
 		return nombre;
 	}
 	
 	public String getNombreMuyCorto(String padreId) {
 		String nombre	 		= "";
 		String apellido			= "";
 		String temp				= "";
 		
 		try{
 			String comando = "SELECT NOMBRE || '-' || PATERNO AS NOMBRE FROM ENOC.PADRE_PERSONAL WHERE PADRE_ID = ?";
 			
 			Object[] parametros = new Object[] {padreId};
 			nombre =  enocJdbc.queryForObject(comando,String.class,parametros);
 			
 			String [] arrayNombre = nombre.split("-");
 			
 			nombre = arrayNombre[0];
 			apellido = arrayNombre[1];
 			
 			if(!nombre.equals("") && !apellido.equals("")){
 				StringTokenizer token = new StringTokenizer(nombre," ");
 				temp = token.nextToken();
 				
 				if(!temp.equals("")){
 					nombre = temp;
 					if(token.countTokens() >= 1){
 						temp = token.nextToken();
 						nombre += " " + temp.charAt(0) + ".";
 					}
 				}
 				nombre += " " + apellido;
 			}

 		}catch(Exception ex){
 			System.out.println("Error  - aca.padre.spring.PadrePersonalDao|getNombreMuyCorto|: "+ex);
 		}
 		
 		return nombre;
 	}
 	
 	public HashMap<String, PadrePersonal> mapaTodos(){		
		List<PadrePersonal> lista 			= new ArrayList<PadrePersonal>();
		HashMap<String, PadrePersonal> mapa	= new HashMap<String,PadrePersonal>();
		try{
			String comando = "SELECT PADRE_ID, NOMBRE, PATERNO, MATERNO, CORREO, TELEFONO, TIPO FROM ENOC.PADRE_PERSONAL";			
			lista = enocJdbc.query(comando, new PadrePersonalMapper());
			for ( PadrePersonal padre : lista) {
				mapa.put(padre.getPadreId(), padre);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.padre.spring.PadrePersonalDao|mapaTodos|:"+ex);
		}		
		return mapa;
	}
}
