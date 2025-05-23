/**
 * 
 */
package aca.hca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author elifo
 *
 */
public class HcaActividadUtil {	
	
	public boolean insertReg(Connection conn, HcaActividad hcaActividad) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.HCA_ACTIVIDAD(TIPO_ID, ACTIVIDAD_ID, ACTIVIDAD_NOMBRE, VALOR, NIVEL_ID, MODIFICABLE)" +
				" VALUES(TO_NUMBER(?, '99'), TO_NUMBER(?, '999')," +
				" ?, TO_NUMBER(?, '999.99'), TO_NUMBER(?, '99'), ?)");
			
			ps.setString(1, hcaActividad.getTipoId());
			ps.setString(2, hcaActividad.getActividadId());			
			ps.setString(3, hcaActividad.getActividadNombre());
			ps.setString(4, hcaActividad.getValor());
			ps.setString(5, hcaActividad.getNivelId());
			ps.setString(6, hcaActividad.getModificable());
			
			if(ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;			
			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaActividadUtil|insertReg|:"+ex);			
		}finally{
			if(ps!=null) ps.close();
		}
		return ok;
	}	
	
	public boolean updateReg(Connection conn, HcaActividad hcaActividad) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.HCA_ACTIVIDAD" + 
				" SET TIPO_ID = TO_NUMBER(?, '99')," +
				" ACTIVIDAD_NOMBRE = ?," +
				" VALOR = TO_NUMBER(?, '999.99')," +
				" NIVEL_ID = TO_NUMBER(?, '99')," +
				" MODIFICABLE = ?" +
				" WHERE ACTIVIDAD_ID = TO_NUMBER(?, '999')");
			
			ps.setString(1, hcaActividad.getTipoId());
			ps.setString(2, hcaActividad.getActividadNombre());
			ps.setString(3, hcaActividad.getValor());
			ps.setString(4, hcaActividad.getNivelId());
			ps.setString(5, hcaActividad.getModificable());
			ps.setString(6, hcaActividad.getActividadId());
						
			if(ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaActividadUtil|updateReg|:"+ex);		
		}finally{
			if(ps!=null) ps.close();
		}
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String actividadId) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.HCA_ACTIVIDAD"+ 
				" WHERE ACTIVIDAD_ID = TO_NUMBER(?, '999')");
			
			ps.setString(1, actividadId);
			
			if(ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaActividadUtil|deleteReg|:"+ex);			
		}finally{
			if(ps!=null) ps.close();
		}
		return ok;
	}
	
	public HcaActividad mapeaRegId(Connection con, String actividadId) throws SQLException{
		HcaActividad hcaActividad = new HcaActividad();
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{ 
			ps = con.prepareStatement("SELECT TIPO_ID, ACTIVIDAD_ID," +
					" ACTIVIDAD_NOMBRE, VALOR, NIVEL_ID, MODIFICABLE " +
					" FROM ENOC.HCA_ACTIVIDAD" + 
					" WHERE ACTIVIDAD_ID = TO_NUMBER(?, '999')");
			
			ps.setString(1, actividadId);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				
				hcaActividad.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaActividadUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		return hcaActividad;
	}
	
	public boolean existeReg(Connection conn, String actividadId) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.HCA_ACTIVIDAD" + 
					" WHERE ACTIVIDAD_ID = TO_NUMBER(?, '999')");
			
			ps.setString(1, actividadId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaActividadUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return ok;
	}
	
	public String maximoReg(Connection conn) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String maximo			= "1";
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(ACTIVIDAD_ID)+1,1) AS MAXIMO FROM ENOC.HCA_ACTIVIDAD"); 
			
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaActividadUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		return maximo;
	}
	
	public static String getModificable(Connection conn, String actividadId) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String modificable		= "1";
		
		try{
			ps = conn.prepareStatement("SELECT MODIFICABLE FROM ENOC.HCA_ACTIVIDAD WHERE ACTIVIDAD_ID = TO_NUMBER(?,'999')"); 
			
			ps.setString(1, actividadId);
			
			rs = ps.executeQuery();
			if (rs.next())
				modificable = rs.getString("MODIFICABLE");
			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaActividadUtil|getModificable|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		return modificable;
	}
	
	public ArrayList<HcaActividad> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<HcaActividad> lisActividad	= new ArrayList<HcaActividad>();
		Statement st 						= conn.createStatement();
		ResultSet rs 						= null;
		String comando						= "";
		
		try{
			comando = "SELECT ACTIVIDAD_ID, TIPO_ID, ACTIVIDAD_NOMBRE, VALOR, NIVEL_ID, MODIFICABLE" +
					" FROM ENOC.HCA_ACTIVIDAD "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				HcaActividad actividad = new HcaActividad();
				actividad.mapeaReg(rs);
				lisActividad.add(actividad);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaActividadUtil|getListAll|:"+ex);
		}
		
		try { rs.close(); } catch (Exception ignore) { }
		try { st.close(); } catch (Exception ignore) { }		
		
		return lisActividad;
	}
	
	public ArrayList<String> getListModificable(Connection conn, String orden ) throws SQLException{
		
		ArrayList<String> lisMod	= new ArrayList<String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		
		String comando				= "";
		
		try{
			comando = "SELECT ACTIVIDAD_ID, MODIFICABLE FROM ENOC.HCA_ACTIVIDAD "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				lisMod.add(rs.getInt("ACTIVIDAD_ID"), rs.getString("MODIFICABLE"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaActividadUtil|getListModificable|:"+ex);
		}
		
		try { rs.close(); } catch (Exception ignore) { }
		try { st.close(); } catch (Exception ignore) { }		
		
		return lisMod;
	}
	
}