// Bean del Catálogo de Grupos
package  aca.carga;

import java.sql.*;

public class CargaGrupoHorario{
	private String cursoCargaId;
	private String salonId;
	private String horario;
	private String tipo;
	private String validaCruce;
	
	public CargaGrupoHorario(){
		cursoCargaId	= "";
		salonId			= "";
		horario			= "";
		tipo			= "";
		validaCruce	= "";		
	}
	
	public String getCursoCargaId(){
		return cursoCargaId;
	}
	
	public void setCursoCargaId( String cursoCargaId){
		this.cursoCargaId = cursoCargaId;
	}	
	
	public String getSalonId(){
		return salonId;
	}
	
	public void setSalonId( String salonId){
		this.salonId = salonId;
	}	
	
	public String getHorario(){
		return horario;
	}
	
	public void setHorario( String horario){
		this.horario = horario;
	}
	
	public String getTipo(){
		return tipo;
	}
	
	public void setTipo( String tipo){
		this.tipo = tipo;
	}
	
	public String getValidaCruce(){
		return validaCruce;
	}
	
	public void setValidaCruce( String validaCruce){
		this.validaCruce = validaCruce;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		cursoCargaId	= rs.getString("CURSO_CARGA_ID");
		salonId 		= rs.getString("SALON_ID");
		horario			= rs.getString("HORARIO");
		tipo	 		= rs.getString("TIPO");
		validaCruce	= rs.getString("VALIDA_CRUCE");
	}
	
	public void mapeaRegCurso( Connection conn, String cursoCargaId ) throws SQLException{		
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT "+
				"CURSO_CARGA_ID, SALON_ID, HORARIO, TIPO, VALIDA_CRUCE "+
				"FROM ENOC.CARGA_GRUPO_HORARIO "+ 
				"WHERE CURSO_CARGA_ID = ? ");
			ps.setString(1, cursoCargaId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoHorario|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		
	}
	
}