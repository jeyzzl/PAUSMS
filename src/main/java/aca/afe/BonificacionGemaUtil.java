package aca.afe;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BonificacionGemaUtil {
	
	public ColportorBonificacionGema mapeaRegId( Connection conn, String matricula ) throws SQLException, IOException{
		ColportorBonificacionGema colportorBG = new ColportorBonificacionGema();
 		PreparedStatement ps =  null;
 		ResultSet rs = null;
 		try{
 		ps = conn.prepareStatement("SELECT "+
 			" MATRICULA, DEP_CAJA, DEP_BANCO, DEP_DIEZMOS, DEP_COMPRAS, "+
 			" DEP_COMPRAS_BONIFICABLES,TOTAL_DEPOSITOS, BONIFICACION_GEMA, BONIFICACION_UM "+
 			" FROM NOE.COLPORTOR_BONIFICACION_GEMA WHERE MATRICULA = ? ");
 		ps.setString(1, matricula);	
 		rs = ps.executeQuery();
 		if (rs.next()){
 			colportorBG.mapeaReg(rs);
 		}
 		}catch(Exception ex){
			System.out.println("Error - aca.afe.BonifiacionGemaUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
 		return colportorBG;
 	}

	
	public boolean existeReg(Connection conn, String matricula) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM NOE.COLPORTOR_BONIFICACION_GEMA "+
				"WHERE MATRICULA = ? ");
			ps.setString(1, matricula);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.afe.BonificacionGemaUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public ArrayList<ColportorBonificacionGema> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<ColportorBonificacionGema> lisAcceso 	= new ArrayList<ColportorBonificacionGema>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{			
			comando = "SELECT MATRICULA,DEP_CAJA, DEP_BANCO, DEP_DIEZMOS, DEP_COMPRAS,DEP_COMPRAS_BONIFICABLES," +
					" TOTAL_DEPOSITOS, BONIFICACION_GEMA, BONIFICACION_UM " +
					" FROM NOE.COLPORTOR_BONIFICACION_GEMA "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ColportorBonificacionGema acceso = new ColportorBonificacionGema();
				acceso.mapeaReg(rs);
				lisAcceso.add(acceso);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.afe.BonifiacionGemaUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAcceso;
	}	

}