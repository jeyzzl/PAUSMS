package aca.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ValidaCurp {
	
	public static boolean validarCurp(String curp){
 		if (curp==null) curp = "-";
 		curp=curp.toUpperCase().trim();
 		return curp.matches("[A-Z]{4}[0-9]{6}[H,M][A-Z]{5}[0-9]{2}");
	}
	
	public static void main(String[] args) throws SQLException{
		
		Connection conOracle 	= null;
		Connection conPostgres	= null;
		
		int row	= 0, row2=0;
		boolean	curpPatron		= false;		
		boolean	curpImagen		= false;
		
		int numPatron	= 0;
		int numImagen	= 0;
		
		try{					
			// Conectar a Oracle						
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conOracle = DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.20:1521:ora1","enoc","caminacondios");
			PreparedStatement psOracle =null;			
			ResultSet rsOracle = null;
			
			// Conectar a Postgres			
			DriverManager.registerDriver (new org.postgresql.Driver());
        	conPostgres=DriverManager.getConnection("jdbc:postgresql://172.16.251.11/archivo","postgres","jete17");
			PreparedStatement psPostgres =null;
			ResultSet rsPostgres = null;
			
			psOracle = conOracle.prepareStatement("SELECT CARRERA_ID, CODIGO_PERSONAL, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE, CURP, SEXO, " +
					" TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS FECHA, RESIDENCIA_ID"+
					" FROM ENOC.INSCRITOS WHERE MODALIDAD_ID = 1" + 
					" ORDER BY 1,3,4,5");			
			rsOracle = psOracle.executeQuery();			
			while (rsOracle.next()){
				
				// Validar el patron de escritura del curp 
				curpPatron = validarCurp(rsOracle.getString("CURP"));
				if (curpPatron == true) numPatron++;
				
				// Validar si exite la imagen
				psPostgres= conPostgres.prepareStatement("SELECT MATRICULA FROM ENOC.ARCH_DOCALUM WHERE MATRICULA = '"+rsOracle.getString("CODIGO_PERSONAL")+"' AND IDDOCUMENTO = 40");
				rsPostgres = psPostgres.executeQuery();
				if (rsPostgres.next()){
					curpImagen = true;
					numImagen++;
				}else{
					curpImagen = false;
				}
				
				row++;
				if (curpPatron==false||curpImagen==false){
					row2++;
					System.out.println(row2+","+rsOracle.getString("CARRERA_ID")+","+rsOracle.getString("CODIGO_PERSONAL")+","+
						rsOracle.getString("APELLIDO_PATERNO")+","+rsOracle.getString("APELLIDO_MATERNO")+","+rsOracle.getString("NOMBRE")+","+
						rsOracle.getString("CURP")+","+rsOracle.getString("SEXO")+","+rsOracle.getString("FECHA")+","+
						rsOracle.getString("RESIDENCIA_ID")+","+
						curpPatron+","+curpImagen);			
				}
			}					
			
			System.out.println("Total:"+row+" Pattern correcto:"+numPatron+" Pattern error:"+(row-numPatron)+""+" Con imagen:"+numImagen+" Sin imagen:"+(row-numImagen));
			
		}catch(Exception e){ 
			System.out.println(e);
		}finally{
			if (conOracle!=null) conOracle.close();
			if (conPostgres!=null) conPostgres.close();
		}		
	}
	
}