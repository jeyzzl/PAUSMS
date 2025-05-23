// Bean del Catalogo de Grupos
package  aca.carga;

import java.sql.*;

public class CargaGrupoHora{
	private String cursoCargaId;
	private String salonId;
	private String dia;
	private String horarioId;
	private String periodo;
	private String bloqueId;
	
	public CargaGrupoHora(){
		cursoCargaId	= "";
		salonId			= "";
		dia				= "";
		horarioId		= "";
		periodo			= "";
		bloqueId		= "";
	}
	
	public String getCursoCargaId() {
		return cursoCargaId;
	}

	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}

	public String getSalonId() {
		return salonId;
	}

	public void setSalonId(String salonId) {
		this.salonId = salonId;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public String getHorarioId() {
		return horarioId;
	}

	public void setHorarioId(String horarioId) {
		this.horarioId = horarioId;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	
	public String getBloqueId() {
		return bloqueId;
	}

	public void setBloqueId(String bloqueId) {
		this.bloqueId = bloqueId;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		cursoCargaId	= rs.getString("CURSO_CARGA_ID");
		salonId 		= rs.getString("SALON_ID");
		horarioId		= rs.getString("HORARIO_ID");
		dia	 			= rs.getString("DIA");
		periodo			= rs.getString("PERIODO");
		bloqueId		= rs.getString("BLOQUE_ID");
	}
	
	public CargaGrupoHora mapeaRegId( Connection conn, String cursoCargaId, String salonId, String horarioId, String dia, String periodo ) throws SQLException{
		
		CargaGrupoHora hora = new CargaGrupoHora();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT "+
				"CURSO_CARGA_ID, SALON_ID, HORARIO_ID, DIA, PERIODO, BLOQUE_ID "+
				"FROM ENOC.CARGA_GRUPO_HORA "+ 
				"WHERE CURSO_CARGA_ID = ? "+
				"AND SALON_ID = ?" +
				"AND HORARIO_ID = TO_NUMBER(?,'9999999')" +
				"AND PERIODO = TO_NUMBER(?,'99')" +
				"AND BLOQUE_ID = TO_NUMBER(?,'99')" +
				"AND DIA = TO_NUMBER(?,'9')");
			ps.setString(1, cursoCargaId);
			ps.setString(2, salonId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				hora.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoHora|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return hora;
	}	

}