
package aca.financiero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class FinPeriodoUtil {
	
	
	public boolean insertReg(Connection conn, FinPeriodo periodo ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.FIN_PERIODO" + 
					" (PERIODO_ID, FECHA_INI, FECHA_FIN, CARGAS, MODALIDADES, MENSAJE, ESTADO)" +
					" VALUES(TO_NUMBER(?, '99'), TO_DATE(?, 'DD/MM/YYYY'), TO_DATE(?, 'DD/MM/YYYY'), ?, ?, ?, ?)");
			
			ps.setString(1, periodo.getPeriodoId());
			ps.setString(2, periodo.getFechaIni());
			ps.setString(3, periodo.getFechaFin());
			ps.setString(4, periodo.getCargas());
			ps.setString(5, periodo.getModalidades());
			ps.setString(6, periodo.getMensaje());
			ps.setString(7, periodo.getEstado());
			
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinPeriodo|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn, FinPeriodo periodo ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.FIN_PERIODO" + 
					" SET FECHA_INI = TO_DATE(?, 'DD/MM/YYYY')," +
					" FECHA_FIN = TO_DATE(?, 'DD/MM/YYYY')," +
					" CARGAS = ?," +
					" MODALIDADES = ? ," +
					" MENSAJE = ? ," +
					" ESTADO = ? " +
					" WHERE PERIODO_ID = TO_NUMBER(?, '99')");		
			
			ps.setString(1, periodo.getFechaIni());
			ps.setString(2, periodo.getFechaFin());
			ps.setString(3, periodo.getCargas());
			ps.setString(4, periodo.getModalidades());
			ps.setString(5, periodo.getMensaje());
			ps.setString(6, periodo.getEstado());
			ps.setString(7, periodo.getPeriodoId());		
			
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinPeriodo|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateCargas(Connection conn, String periodoId, String cargas ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.FIN_PERIODO" +					
					" SET CARGAS = ?" +					
					" WHERE PERIODO_ID = TO_NUMBER(?, '99')");
			
			
			ps.setString(1, cargas);			
			ps.setString(2, periodoId);
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinPeriodo|updateCargas|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateModalidades(Connection conn, String periodoId, String modalidades ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.FIN_PERIODO" +					
					" SET MODALIDADES = ?" +					
					" WHERE PERIODO_ID = TO_NUMBER(?, '99')");
			
			
			ps.setString(1, modalidades);			
			ps.setString(2, periodoId);
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinPeriodo|updateModalidades|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String periodoId ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.FIN_PERIODO" + 
					" WHERE PERIODO_ID  = TO_NUMBER(?, '99')");
			
			ps.setString(1, periodoId);
		if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinPeriodo|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public FinPeriodo mapeaRegId(Connection con, String periodoId) throws SQLException{
		
		FinPeriodo periodo = new FinPeriodo(); 
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT PERIODO_ID, " +
					" TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI," +
					" TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN," +
					" MODALIDADES, CARGAS, ESTADO, MENSAJE"+				
					" FROM ENOC.FIN_PERIODO" + 
					" WHERE PERIODO_ID =  '"+periodoId+"' ");
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				periodo.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinPeriodo|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return periodo;
	}
	
	public boolean existeReg(Connection conn, String periodoId) throws SQLException{
		boolean ok 			= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.FIN_PERIODO" + 
					" WHERE PERIODO_ID = "+periodoId+" ");
			
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinPeriodo|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(PERIODO_ID)+1 MAXIMO FROM ENOC.FIN_PERIODO"); 
			//ps.setString(1,  asambleaId);
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
				if(maximo == null){
					maximo = "1";
				}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinPeriodo|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;		
	}
	
	public static String getMensaje(Connection conn) throws SQLException{
		
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		String mensaje			= "X";
		
		try{
			ps = conn.prepareStatement("SELECT MENSAJE, MODALIDADES, ESTADO FROM ENOC.FIN_PERIODO" 
					+ " WHERE TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'), 'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN");
		
			rs= ps.executeQuery();		
			if(rs.next()){
				mensaje = rs.getString("MENSAJE")+"@@"+rs.getString("MODALIDADES")+"@@"+rs.getString("ESTADO");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinPeriodo|getMensajes|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return mensaje;
	}
	
	public boolean getTieneCargas(Connection conn, String codigoPersonal) throws SQLException{
		boolean ok 			= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.INSCRITOS WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' "
					+ " AND INSTR((SELECT CARGAS FROM ENOC.FIN_PERIODO WHERE TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY')"
					+ " BETWEEN FECHA_INI AND FECHA_FIN AND ESTADO IN ('A','B')),CARGA_ID) > 0 ");
			
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinPeriodo|getTieneCargas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}

	public ArrayList<FinPeriodo> getListAll(Connection conn, String orden ) throws SQLException{
		ArrayList<FinPeriodo> lisAlumno	= new ArrayList<FinPeriodo>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT PERIODO_ID, " +
					" TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI," +
					" TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN," +
					" MODALIDADES, CARGAS, ESTADO, MENSAJE"+				
					" FROM ENOC.FIN_PERIODO "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				FinPeriodo periodo = new FinPeriodo();
				periodo.mapeaReg(rs);
				lisAlumno.add(periodo);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinPeriodoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAlumno;
	}
	
	public ArrayList<FinPeriodo> getMensajes(Connection conn) throws SQLException{
		
		ArrayList<FinPeriodo> lis		= new ArrayList<FinPeriodo>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT * FROM ENOC.FIN_PERIODO" + 
					" WHERE TO_DATE(now(), 'DD/MM/YYYY') BETWEEN TO_DATE(FECHA_INI, 'DD/MM/YYYY') AND TO_DATE(FECHA_FIN, 'DD/MM/YYYY')";	 
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				FinPeriodo obj = new FinPeriodo();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinPeriodoUtil|getMensajes|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
}