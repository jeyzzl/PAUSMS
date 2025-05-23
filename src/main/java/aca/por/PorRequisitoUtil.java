package aca.por;

import java.sql.*;
import java.util.ArrayList;

public class PorRequisitoUtil {
	
	public ArrayList<PorRequisito> getListAll(Connection conn, String orden ) throws SQLException{
	
		ArrayList<PorRequisito> lista	= new ArrayList<PorRequisito>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
	 
		try{
			comando = "SELECT REQUISITO_ID, DESCRIPCION, CATEGORIA_ID, VALOR, SECCION_ID, TIPO FROM ENOC.POR_REQUISITO "+orden; 
			rs = st.executeQuery(comando);
			while (rs.next()){
			
				PorRequisito objeto = new PorRequisito();
				objeto.mapeaReg(rs);
				lista.add(objeto);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorRequisitoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
	
		return lista;
	}	
	
	public ArrayList<PorRequisito> getListCategoria(Connection conn, String categoriaId, String orden ) throws SQLException{
		
		ArrayList<PorRequisito> lista	= new ArrayList<PorRequisito>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
	 
		try{
			comando = " SELECT REQUISITO_ID, DESCRIPCION, CATEGORIA_ID, VALOR, SECCION_ID, TIPO"
					+ " FROM ENOC.POR_REQUISITO"
					+ " WHERE CATEGORIA_ID = '"+categoriaId+"' "+orden; 
			rs = st.executeQuery(comando);
			while (rs.next()){
			
				PorRequisito objeto = new PorRequisito();
				objeto.mapeaReg(rs);
				lista.add(objeto);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorRequisitoUtil|getListCategoria|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
	
		return lista;
	}
	
	public ArrayList <PorRequisito> getListReq(Connection conn, String codigoPersonal ) throws SQLException{
		
		ArrayList <PorRequisito> lista	= new ArrayList <PorRequisito>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
	 
		try{
			comando = "SELECT * FROM ENOC.POR_REQUISITO WHERE REQUISITO_ID IN (SELECT REQUISITO_ID FROM ENOC.POR_REQUISITO_EMP WHERE EMPLEADO_ID = '"+ codigoPersonal +"')";
			rs = st.executeQuery(comando);
			while (rs.next()){
				PorRequisito obj = new PorRequisito();
				obj.mapeaReg(rs);
				lista.add(obj);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorRequisitoUtil|getListReq|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
	
		return lista;
	}
}