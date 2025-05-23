// Clase para la tabla de Modulo
package aca.candado;
import java.sql.*;

public class Candado{	
	private String candadoId;
	private String nombreCandado;		
	
	// Constructor
	public Candado(){		
		candadoId		= "";
		nombreCandado	= "";		
	}
	
	/**
	 * @return Returns the candadoId.
	 */
	public String getCandadoId() {
		return candadoId;
	}
	/**
	 * @param candadoId The candadoId to set.
	 */
	public void setCandadoId(String candadoId) {
		this.candadoId = candadoId;
	}
	/**
	 * @return Returns the nombreCandado.
	 */
	public String getNombreCandado() {
		return nombreCandado;
	}
	/**
	 * @param nombreCandado The nombreCandado to set.
	 */
	public void setNombreCandado(String nombreCandado) {
		this.nombreCandado = nombreCandado;
	}
	
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		candadoId  		= rs.getString("CANDADO_ID");
		nombreCandado	= rs.getString("NOMBRE_CANDADO");				
	}
	
	public void mapeaRegId(Connection con, String candadoId) throws SQLException{
		Candado canda = new Candado();
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT CANDADO_ID, NOMBRE_CANDADO "+				
					"FROM ENOC.CANDADO WHERE CANDADO_ID = ? "); 
			ps.setString(1,candadoId);
			rs = ps.executeQuery();
			
			if(rs.next()){
				canda.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.candado.CandadoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}

}