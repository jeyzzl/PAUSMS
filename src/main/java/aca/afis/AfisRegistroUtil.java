package aca.afis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class AfisRegistroUtil {	
	
	public ArrayList<AfisRegistro> getListAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<AfisRegistro> lista	= new ArrayList<AfisRegistro>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT PERIODO_ID, CODIGO_PERSONAL, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, PESO, TALLA, CINTURA, GRASA, MUSCULO, IMC, CARRERA_ID, DIETA, RESIDENCIA FROM ENOC.AFIS_REGISTRO "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				AfisRegistro registro = new AfisRegistro();
				registro.mapeaReg(rs);
				lista.add(registro);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.afis.AfisRegistroUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lista;
	}
	
	public ArrayList<AfisRegistro> getListAllPorPeriodo(Connection conn, String periodoId, String orden) throws SQLException{
		
		ArrayList<AfisRegistro> lista	= new ArrayList<AfisRegistro>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT PERIODO_ID, CODIGO_PERSONAL, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, PESO, TALLA, CINTURA, GRASA, MUSCULO, IMC, CARRERA_ID, DIETA, RESIDENCIA FROM ENOC.AFIS_REGISTRO "
					+ " WHERE PERIODO_ID = "+periodoId+" "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				AfisRegistro registro = new AfisRegistro();
				registro.mapeaReg(rs);
				lista.add(registro);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.afis.AfisRegistroUtil|getListAllPorPeriodo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lista;
	}
	
	public HashMap<String, AfisRegistro> mapAlumnosPorPeriodo(Connection conn, String periodoId) throws SQLException{
		
		HashMap<String, AfisRegistro> map		= new HashMap<String,AfisRegistro>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = " SELECT PERIODO_ID, CODIGO_PERSONAL, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, PESO, TALLA, CINTURA, GRASA, MUSCULO, IMC, CARRERA_ID, DIETA, RESIDENCIA FROM ENOC.AFIS_REGISTRO "
					+ " WHERE PERIODO_ID = '"+periodoId+"' ";
			
			rs = st.executeQuery(comando);
			while (rs.next()){	
				AfisRegistro alum = new AfisRegistro();
				alum.mapeaReg(rs);
				map.put(rs.getString("CODIGO_PERSONAL"), alum);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.afis.AfisRegistroUtil|mapAlumnosPorPeriodo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}	
		return map;
	}
	


	public boolean insertReg(Connection conn, AfisRegistro reg ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement(" INSERT INTO ENOC.AFIS_REGISTRO(PERIODO_ID, CODIGO_PERSONAL, FECHA, PESO, TALLA, CINTURA, GRASA, MUSCULO, IMC, CARRERA_ID, DIETA, RESIDENCIA) " 
									 + " VALUES(TO_NUMBER(?,'999'), ?, "
									 + " TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY'), "
									 + " TO_NUMBER(?, '999.99'),"
									 + " TO_NUMBER(?, '999.99'), TO_NUMBER(?, '999.99'), TO_NUMBER(?, '999.99'), TO_NUMBER(?, '999.99'), "
									 + " TO_NUMBER(?, '999.99'), ?, TO_NUMBER(?, '99'), ?)");
			
			ps.setString(1, reg.getPeriodoId());
			ps.setString(2, reg.getCodigoPersonal());
			ps.setString(3, reg.getPeso());
			ps.setString(4, reg.getTalla());
			ps.setString(5, reg.getCintura());
			ps.setString(6, reg.getGrasa());
			ps.setString(7, reg.getMusculo());
			ps.setString(8, reg.getImc());
			ps.setString(9, reg.getCarreraId());
			ps.setString(10, reg.getDieta());
			ps.setString(11, reg.getResidencia());
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.afis.AfisRegistro|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}

	public boolean updateReg(Connection conn, AfisRegistro reg) throws SQLException{
		boolean ok = false;
		try{
			PreparedStatement ps = conn.prepareStatement(" UPDATE ENOC.AFIS_REGISTRO"
					+ " SET CODIGO_PERSONAL = ?,"
					+ " PESO = TO_NUMBER(?, '999.99'),"
					+ " TALLA = TO_NUMBER(?, '999.99'),"
					+ " CINTURA = TO_NUMBER(?, '999.99'),"
					+ " GRASA = TO_NUMBER(?, '999.99'),"
					+ " MUSCULO = TO_NUMBER(?, '999.99'),"
					+ " IMC = TO_NUMBER(?, '999.99'),"
					+ " CARRERA_ID = ?,"
					+ " DIETA = TO_NUMBER(?,'99'),"
					+ "	RESIDENCIA = ? "
					+ " WHERE PERIODO_ID = TO_NUMBER(?,'999')"
					+ " AND CODIGO_PERSONAL = ? ");
			
			ps.setString(1, reg.getCodigoPersonal());
			ps.setString(2, reg.getPeso());
			ps.setString(3, reg.getTalla());
			ps.setString(4, reg.getCintura());
			ps.setString(5, reg.getGrasa());
			ps.setString(6, reg.getMusculo());
			ps.setString(7, reg.getImc());
			ps.setString(8, reg.getCarreraId());
			ps.setString(9, reg.getDieta());
			ps.setString(10, reg.getResidencia());
			ps.setString(11, reg.getPeriodoId());
			
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
			try { ps.close(); } catch (Exception ignore) { }
		}catch(Exception ex){
			System.out.println("Error - aca.afis.AfisRegistro|updateReg|: "+ex);
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String periodoId, String codigoPersonal ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.AFIS_REGISTRO WHERE PERIODO_ID = TO_NUMBER(?,'999') AND CODIGO_PERSONAL = ?");
			ps.setString(1, periodoId);
			ps.setString(2, codigoPersonal);
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.afis.AfisRegistroUtil|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public AfisRegistro mapeaRegId(Connection con, String codigoPersonal, String periodoId) throws SQLException{		
		
		AfisRegistro registro = new AfisRegistro();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = con.prepareStatement(" SELECT PERIODO_ID, CODIGO_PERSONAL, TO_DATE(FECHA, 'DD/MM/YYYY') AS FECHA, PESO, TALLA, CINTURA, GRASA, MUSCULO, IMC, CARRERA_ID, DIETA, RESIDENCIA "
									+ " FROM ENOC.AFIS_REGISTRO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'"
									+ " AND PERIODO_ID = '"+periodoId+"'");
			rs = ps.executeQuery();
			if (rs.next()){
				registro.mapeaReg(rs);
			}
		}catch(Exception ex){
				System.out.println("Error - aca.afis.AfisRegistroUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
		}
		
		return registro;
	}
	
	public boolean existeReg(Connection conn, String codigoPersonal, String periodoId) throws SQLException{
		boolean ok 			= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.AFIS_REGISTRO WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = TO_NUMBER(?, '999')");
			ps.setString(1, codigoPersonal);
			ps.setString(2, periodoId);
			
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.afis.AfisRegistroUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		return ok;
	}

}
