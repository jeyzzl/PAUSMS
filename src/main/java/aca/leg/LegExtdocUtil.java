package aca.leg;

import java.sql.*;
import java.util.ArrayList;

public class LegExtdocUtil {
	

	public boolean insertReg(Connection conn, LegExtdoc legExtdoc) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.LEG_EXTDOCTOS(CODIGO, IDDOCUMENTO, FECHA_VENCE, NUM_DOCTO, FECHA, FECHA_TRAMITE, ESTADO) " +
				" VALUES( ?, TO_NUMBER(?,'99'), TO_DATE(?,'DD/MM/YYYY'), ?, " +
				" TO_DATE(?,'DD/MM/YYYY')," +
				" TO_DATE(?,'DD/MM/YYYY')," +
				" TO_NUMBER(?,'99') )");			
			ps.setString(1, legExtdoc.getCodigo());	
			ps.setString(2, legExtdoc.getIdDocumento());
			ps.setString(3, legExtdoc.getFechaVence());
			ps.setString(4, legExtdoc.getNumDocto());
			ps.setString(5, legExtdoc.getFecha());
			ps.setString(6, legExtdoc.getFechaTramite());
			ps.setString(7, legExtdoc.getEstado());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegExtdoc|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, LegExtdoc legExtdoc ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.LEG_EXTDOCTOS"+ 
				" SET FECHA_VENCE = TO_DATE(?,'DD/MM/YYYY')," +
				" NUM_DOCTO = ?," +
				" FECHA = TO_DATE(?,'DD/MM/YYYY')," +
				" FECHA_TRAMITE = TO_DATE(?,'DD/MM/YYYY')," +
				" ESTADO = TO_NUMBER(?,'99')"+
				" WHERE CODIGO = ? " +
				" AND IDDOCUMENTO = TO_NUMBER(?,'99')");
			ps.setString(1, legExtdoc.getFechaVence());
			ps.setString(2, legExtdoc.getNumDocto());
			ps.setString(3, legExtdoc.getFecha());
			ps.setString(4, legExtdoc.getFechaTramite());
			ps.setString(5, legExtdoc.getEstado());
			ps.setString(6, legExtdoc.getCodigo());
			ps.setString(7, legExtdoc.getIdDocumento());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegExtdoc|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String codigo, String idDocumento ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.LEG_EXTDOCTOS "+ 
				"WHERE CODIGO = ? AND IDDOCUMENTO= TO_NUMBER(?,'99')");
			ps.setString(1, codigo);
			ps.setString(2, idDocumento);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegExtdoc|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public LegExtdoc mapeaRegId(Connection conn, String codigo, String idDocumento)	throws SQLException{
		LegExtdoc legExtdoc = new LegExtdoc();
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		try{
			ps=conn.prepareStatement("SELECT CODIGO, IDDOCUMENTO," +
					" TO_CHAR(FECHA_VENCE,'DD/MM/YYYY') AS FECHA_VENCE," +
					" NUM_DOCTO," +
					" TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA," +
					" TO_CHAR(FECHA_TRAMITE,'DD/MM/YYYY') AS FECHA_TRAMITE," +
					" ESTADO"+
					" FROM ENOC.LEG_EXTDOCTOS " + 
					" WHERE CODIGO = ?" +
					" AND IDDOCUMENTO= TO_NUMBER(?,'99')");
			ps.setString(1, codigo);
			ps.setString(2, idDocumento);
			rs=ps.executeQuery();
			if(rs.next()){
				legExtdoc.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegExtdoc|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return legExtdoc;
	}
	
	public boolean existeReg(Connection conn, String codigo, String idDocumento) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.LEG_EXTDOCTOS WHERE  CODIGO= ? " + 
					"AND IDDOCUMENTO= TO_NUMBER(?,'99') ");
			ps.setString(1,codigo);
			ps.setString(2,idDocumento);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegExtdoc|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static String getFechaVenceFM3(Connection conn, String codigoPersonal) throws SQLException{
		String 			fecha 	= "00/00/0000";
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT TO_CHAR(FECHA_VENCE,'DD/MM/YYYY') AS FECHA_VENCE FROM ENOC.LEG_EXTDOCTOS" + 
				" WHERE  CODIGO = ? AND IDDOCUMENTO = TO_NUMBER(?,'99') ");
			ps.setString(1, codigoPersonal);
			ps.setString(2, "2");
			rs = ps.executeQuery();
			if (rs.next())
				fecha = rs.getString("FECHA_VENCE");
			else
				fecha = "00/00/0000";
			
			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegExtdoc|getFechaVenceFM3|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return fecha;
	}
	
	public static String getFechaVenceDocumento(Connection conn, String codigoPersonal, String documento) throws SQLException{
		String 			fecha 	= "00/00/0000";
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT TO_CHAR(FECHA_VENCE,'DD/MM/YYYY') AS FECHA_VENCE FROM ENOC.LEG_EXTDOCTOS"
					+ " WHERE CODIGO = ? AND IDDOCUMENTO = TO_NUMBER(?,'99')");
			ps.setString(1, codigoPersonal);
			ps.setString(2, documento);
			rs = ps.executeQuery();
			if (rs.next())
				fecha = rs.getString("FECHA_VENCE");
			else
				fecha = "00/00/0000";
			
			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegExtdoc|getFechaVenceFM3|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return fecha;
	}
	
	public static String getFechaVencePasaporte(Connection conn, String codigoPersonal) throws SQLException{
		String 			fecha 	= "00/00/0000";
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT TO_CHAR(FECHA_VENCE,'DD/MM/YYYY') AS FECHA_VENCE FROM ENOC.LEG_EXTDOCTOS" + 
				" WHERE  CODIGO = ? AND IDDOCUMENTO = TO_NUMBER(?,'99') ");
			ps.setString(1, codigoPersonal);
			ps.setString(2, "3");
			rs = ps.executeQuery();
			if (rs.next())
				fecha = rs.getString("FECHA_VENCE");
			else
				fecha = "00/00/0000";
			
			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegExtdoc|getFechaVencePasaporte|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return fecha;
	}
	/*
	public static void main(String args[]){
		boolean variable=true;
		try{
			Connection Conn= null;
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Conn= DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.14:1521:ora1", "enoc","caminacondios");
			LegExtdoc act= new LegExtdoc();
			act.setCodigo("1010937");
			act.setIdDocumento("1");
			variable= act.existeReg(Conn);
			System.out.println("ArrayList="+variable);
			Conn.commit();
			Conn.close();
		}catch(Exception e){
			System.out.println("Error main="+e);
		}
	}*/
	
	public ArrayList<LegExtdoc> getLista(Connection conn, String codigoPersonal, String orden) throws SQLException{
		ArrayList<LegExtdoc> lisExdoc 	= new ArrayList<LegExtdoc>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando="SELECT CODIGO, COALESCE(IDDOCUMENTO,0) AS IDDOCUMENTO," +
					" TO_CHAR(FECHA_VENCE,'DD/MM/YYYY') AS FECHA_VENCE," +
					" NUM_DOCTO," +
					" TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA," +
					" TO_CHAR(FECHA_TRAMITE,'DD/MM/YYYY') AS FECHA_TRAMITE," +
					" ESTADO" +
					" FROM ENOC.LEG_EXTDOCTOS" + 
					" WHERE CODIGO = '"+codigoPersonal+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				LegExtdoc doc = new LegExtdoc();
				doc.mapeaReg(rs);
				lisExdoc.add(doc);
			}
		}catch (Exception ex){
			System.out.println("Error - aca.leg.LegExtdocUtil|getLista|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(st!=null) st.close();
		}
		
		return lisExdoc;
	}
	
}