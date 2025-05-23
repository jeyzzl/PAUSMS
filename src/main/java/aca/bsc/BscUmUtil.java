package aca.bsc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BscUmUtil {
	
	public ArrayList<BscUm> getListAll(Connection conn, String orden) throws SQLException{
		ArrayList<BscUm> listor		= new ArrayList<BscUm>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT * FROM ENOC.BSC_UM "+ orden;			 
			rs = st.executeQuery(comando);
			while (rs.next()){				
				BscUm bscUm = new BscUm();
				bscUm.mapeaReg(rs);
				listor.add(bscUm);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bsc.BscUmUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return listor;
	}

	public boolean insertReg(Connection conn, BscUm bscUm) throws SQLException {
		boolean ok = false;
		PreparedStatement ps = null;
		try {
			ps = conn
					.prepareStatement("INSERT INTO ENOC.BSC_UM("
							+ "PERIODO_ID, CARGA_ID, CICLO, INSCRITOS, ORDEN) VALUES(?,?,?,?,TO_NUMBER(?, '99'))");
			ps.setString(1, bscUm.getPeriodoId());
			ps.setString(2, bscUm.getCargaId());
			ps.setString(3, bscUm.getCiclo());
			ps.setString(4, bscUm.getInscritos());
			ps.setString(5, bscUm.getOrden());
			if (ps.executeUpdate() == 1) {
				ok = true;
			} else {
				ok = false;
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.bsc.BscUmUtil|insertReg|:" + ex);
		} finally {
			if (ps != null)
				ps.close();
		}
		return ok;
	}

	public boolean updateReg(Connection conn, BscUm bscUm) throws SQLException {
		boolean ok = false;
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("UPDATE ENOC.BSC_UM SET INSCRITOS = ?, ORDEN = TO_NUMBER(?, '99')" +
							" WHERE PERIODO_ID = ? AND CARGA_ID = ? AND CICLO = ?");
			ps.setString(1, bscUm.getInscritos());
			ps.setString(2, bscUm.getPeriodoId());
			ps.setString(3, bscUm.getCargaId());
			ps.setString(4, bscUm.getCiclo());
			ps.setString(5, bscUm.getOrden());
			if (ps.executeUpdate() == 1) {
				ok = true;
			} else {
				ok = false;
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.bsc.BscUmUtil|updateReg|:" + ex);
		} finally {
			if (ps != null)
				ps.close();
		}
		return ok;
	}

	public boolean deleteReg(Connection conn, String periodoId, String cargaId, String ciclo) throws SQLException {
		boolean ok = false;
		PreparedStatement ps = null;
		try {
			ps = conn
					.prepareStatement("DELETE FROM ENOC.BSC_UM WHERE PERIODO_ID = ? AND CARGA_ID = ? AND CICLO = ?");
			ps.setString(1, periodoId);
			ps.setString(2, cargaId);
			ps.setString(3, ciclo);
			if (ps.executeUpdate() == 1) {
				ok = true;
			} else {
				ok = false;
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.bsc.BscUmUtil|deleteReg|:" + ex);
		} finally {
			if (ps != null)
				ps.close();
		}
		return ok;
	}
	public BscUm mapeaRegId(Connection con, String periodoId, String cargaId, String ciclo) throws SQLException {
		BscUm bscUm = new BscUm();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("SELECT * FROM ENOC.BSC_UM WHERE PERIODO_ID = ? AND CARGA_ID = ? AND CICLO = ? ");
			ps.setString(1, periodoId);
			ps.setString(2, cargaId);
			ps.setString(3, ciclo);
			rs = ps.executeQuery();

			if (rs.next()) {
				bscUm.mapeaReg(rs);
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.bsc.BscUmUtil|mapeaRegId|:" + ex);
		} finally {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
		}
		return bscUm;
	}
}