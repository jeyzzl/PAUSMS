// CLASE PARA LA VISTA EST_INSCRITOS
package  aca.vista;

import java.sql.*;

public class EstInscritos{
	private String codigoPersonal;
	private String nombre;
	private String edad;
	private String sexo;
	private String residenciaId;
	private String nombreReligion;	
	private String tipo;
	private String cargaId;
	private String modalidad;	
	private String planId;	
		
	public EstInscritos(){
		codigoPersonal	= "";
		nombre			= "";
		edad			= "";
		sexo			= "";
		residenciaId	= "";
		nombreReligion	= "";
		tipo			= "";
		cargaId			= "";	
		modalidad		= "";
		planId			= "";
	}
	
	public String getCodigoPersonal(){
		return codigoPersonal;
	}
	
	public String getNombre(){
		return nombre;
	}	
	public String getEdad(){
		return edad;
	}
	
	public String getSexo(){
		return sexo;
	}	
	
	public String getResidenciaId(){
		return residenciaId;
	}	
	
	public String getNombreReligion(){
		return nombreReligion;
	}
	
	public String getTipo(){
		return tipo;
	}	
	
	public String getCargaId(){
		return cargaId;
	}	
	
	public String getModalidad(){
		return modalidad;
	}
	
	public String getPlanId(){
		return planId;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal 		= rs.getString("CODIGO_PERSONAL");
		nombre				= rs.getString("NOMBRE");
		edad		 		= rs.getString("EDAD");
		sexo 				= rs.getString("SEXO");
		residenciaId		= rs.getString("RESIDENCIA_ID");
		nombreReligion		= rs.getString("NOMBRE_RELIGION");
		tipo 				= rs.getString("TIPO");
		cargaId				= rs.getString("CARGA_ID");
		modalidad	 		= rs.getString("MODALIDAD");
		planId 				= rs.getString("PLAN_ID");
	}
	
	public void mapeaRegId( Connection conn, String codigoPersonal ) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL, "+
					"NOMBRE, EDAD, SEXO, RESIDENCIA_ID, NOMBRE_RELIGION,"+ 
					"TIPO, CARGA_ID, MODALIDAD, PLAN_ID "+
					"FROM ENOC.ESTINSCRITOS "+
					"WHERE CODIGO_PERSONAL = ? ");
			ps.setString(1, codigoPersonal);		
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstInscritos|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ESTINSCRITOS "+
			"WHERE CODIGO_PERSONAL = ? ");
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstInscritos|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}		
}