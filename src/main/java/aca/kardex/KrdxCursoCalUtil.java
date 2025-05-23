package aca.kardex;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class KrdxCursoCalUtil {
	public ArrayList<KrdxCursoCal> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<KrdxCursoCal> lisDiferidas		= new ArrayList<KrdxCursoCal>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando			= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, " +
					" NOTA, ESTADO, TO_CHAR(FECHA_FINAL,'DD/MM/YYYY') AS FECHA_FINAL," +
					" TIPO, TIPO_NOTA, TIPOCAL_ID  "+
					" FROM ENOC.KRDX_CURSO_CAL "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				KrdxCursoCal diferida = new KrdxCursoCal();
				diferida.mapeaReg(rs);
				lisDiferidas.add(diferida);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.krdxCursoDifUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisDiferidas;
	}
	
	public ArrayList<KrdxCursoCal> getListDiferidas(Connection conn, String cursoCargaId, String tipo,String orden ) throws SQLException{
		
		ArrayList<KrdxCursoCal> lisDiferidas		= new ArrayList<KrdxCursoCal>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando			= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID,TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, NOTA, ESTADO, " +
					" TO_CHAR(FECHA_FINAL,'DD/MM/YYYY') AS FECHA_FINAL, TIPO, TIPO_NOTA, TIPOCAL_ID  "+
				" FROM ENOC.KRDX_CURSO_CAL WHERE CURSO_CARGA_ID = '"+cursoCargaId+"' " +
				" AND TIPO = '"+tipo+"' "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				KrdxCursoCal diferida = new KrdxCursoCal();
				diferida.mapeaReg(rs);
				lisDiferidas.add(diferida);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.krdxCursoDifUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisDiferidas;
	}
	
	public ArrayList<KrdxCursoCal> getListHoy(Connection conn, String cursoCargaId, String tipo, String orden ) throws SQLException{
		
		ArrayList<KrdxCursoCal> lisDiferidas		= new ArrayList<KrdxCursoCal>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID,TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, NOTA, ESTADO, "+
					" TO_CHAR(FECHA_FINAL,'DD/MM/YYYY') AS FECHA_FINAL, TIPO, TIPO_NOTA, TIPOCAL_ID  "+
				" FROM ENOC.KRDX_CURSO_CAL " +
				" WHERE CURSO_CARGA_ID = '"+cursoCargaId+"'";
			if (tipo.equals("D")){
				comando = comando + " AND TO_CHAR(FECHA,'DD/MM/YYYY') = TO_CHAR(now(),'DD/MM/YYYY')";
			}else{
				comando += " AND TO_CHAR(FECHA,'DD/MM/YYYY') = TO_CHAR(now(),'DD/MM/YYYY')";
			}			
				comando +=" AND TIPO = '"+tipo+"' "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				KrdxCursoCal diferida = new KrdxCursoCal();
				diferida.mapeaReg(rs);
				lisDiferidas.add(diferida);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.krdxCursoDifUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisDiferidas;
	}
	
	
}