package aca.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Sep {
	
	public static void main(String[] args) {
		
		try{
			String matricula 	= "x";
			String plan			= "x";
			int cont 	= 0;
			int error 	= 0;
			
			Connection Conn = null;						
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Conn = DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.20:1521:ora1", "enoc", "caminacondios");
			PreparedStatement ps =null;			
			ResultSet rs = null;
			
			ps = Conn.prepareStatement("SELECT"+
					" CODIGO_PERSONAL, ENOC.ALUM_APELLIDO(CODIGO_PERSONAL) AS APELLIDO, PLAN_ID, CARGA_ID"+
					" FROM ENOC.ESTADISTICA WHERE CARGA_ID IN ('08091D','08095C','09101A','09105A','09102A')" + 
					" ORDER BY CODIGO_PERSONAL, PLAN_ID");
			
			rs = ps.executeQuery();			
			while (rs.next()){ cont++;
				if (matricula.equals("x")) matricula = rs.getString("CODIGO_PERSONAL");
				if (plan.equals("x")) plan = rs.getString("PLAN_ID");
				
				if (rs.getString("CODIGO_PERSONAL").equals(matricula)){
					if (rs.getString("PLAN_ID").equals(plan)){
						cont++;
					}else{
						error++;
						System.out.println(error+","+rs.getString("CODIGO_PERSONAL")+","+rs.getString("APELLIDO")+","+rs.getString("PLAN_ID")+","+rs.getString("CARGA_ID")+","+plan);
						matricula 	= rs.getString("CODIGO_PERSONAL");
						plan		= rs.getString("PLAN_ID");
					}					
				}else{
					matricula 	= rs.getString("CODIGO_PERSONAL");
					plan		= rs.getString("PLAN_ID");
				}
				
			}
			System.out.println("Cont:"+cont+" - Errores:"+error);
			if (Conn!=null) Conn.close();
			
		}catch(Exception e){
			System.out.println(e);
		}		
	}
	
}