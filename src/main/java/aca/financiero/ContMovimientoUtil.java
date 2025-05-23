package aca.financiero;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class ContMovimientoUtil {	
	
	public ArrayList<ContMovimiento> getListAll(Connection conn, String orden) throws SQLException{
		ArrayList<ContMovimiento> lista 	= new ArrayList<ContMovimiento>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{			
			comando = " SELECT ID_EJERCICIO, ID_LIBRO, ID_CCOSTO, FOLIO, NUMMOVTO, FECHA, DESCRIPCION, IMPORTE, NATURALEZA,"
					+ " REFERENCIA, REFERENCIA2, ID_CTAMAYORM, ID_CCOSTOM, ID_AUXILIARM, STATUS, TIPO_CUENTA, CONCEPTO_ID"
					+ " FROM MATEO.CONT_MOVIMIENTO "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				ContMovimiento movto = new ContMovimiento();
				movto.mapeaReg(rs);
				lista.add(movto);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.ContMovimientoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lista;
	}	
	
	public ArrayList<ContMovimiento> getListAlumno(Connection conn, String codigoPersonal, String libros, String year, String naturaleza ) throws SQLException{
		ArrayList<ContMovimiento> lista 	= new ArrayList<ContMovimiento>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{			
			comando = " SELECT * FROM MATEO.CONT_MOVIMIENTO "
					+ " WHERE ID_AUXILIARM = "+codigoPersonal+" "
					+ " AND ID_LIBRO IN("+libros+")"
					+ " AND TO_CHAR(FECHA,'YYYY') IN ("+year+")"
					+ " AND NATURALEZA IN ("+naturaleza+")";
			rs = st.executeQuery(comando);
			while (rs.next()){
				ContMovimiento movto = new ContMovimiento();
				movto.mapeaReg(rs);
				lista.add(movto);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.ContMovimientoUtil|getListAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lista;
	}
}