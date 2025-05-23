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
import java.util.HashMap;

/**
 * @author elifo
 *
 */
public class HcaMAUtil {
	
	public boolean insertReg(Connection conn, HcaMaestroActividad hcaMaestroActividad) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			
			// valor de horas a 1
			if (hcaMaestroActividad.getHoras().equals("")||hcaMaestroActividad.getHoras()==null||hcaMaestroActividad.getHoras().equals(" ")) hcaMaestroActividad.setHoras("1");
			
			ps = conn.prepareStatement("INSERT INTO ENOC.HCA_MAESTRO_ACTIVIDAD(CODIGO_PERSONAL, CARGA_ID, ACTIVIDAD_ID, SEMANAS, HORAS)" +
				" VALUES(?, ?, TO_NUMBER(?, '999'), TO_NUMBER(?, '99'), TO_NUMBER(?,'999.99'))");
			
			ps.setString(1, hcaMaestroActividad.getCodigoPersonal());
			ps.setString(2, hcaMaestroActividad.getCargaId());
			ps.setString(3, hcaMaestroActividad.getActividadId());
			ps.setString(4, hcaMaestroActividad.getSemanas());
			ps.setString(5, hcaMaestroActividad.getHoras());
			
			if(ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;			
			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaMAUtil|insertReg|:"+ex);			
		}finally{
			if(ps!=null) ps.close();
		}		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, HcaMaestroActividad hcaMaestroActividad) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.HCA_MAESTRO_ACTIVIDAD" + 
				" SET SEMANAS = TO_NUMBER(?, '99')," +
				" HORAS = TO_NUMBER(?,'999.99')" +
				" WHERE CODIGO_PERSONAL = ?" +
				" AND CARGA_ID = ?" +
				" AND ACTIVIDAD_ID = TO_NUMBER(?, '999')");
			
			ps.setString(1, hcaMaestroActividad.getSemanas());
			ps.setString(2, hcaMaestroActividad.getHoras());
			ps.setString(3, hcaMaestroActividad.getCodigoPersonal());
			ps.setString(4, hcaMaestroActividad.getCargaId());
			ps.setString(5, hcaMaestroActividad.getActividadId());
						
			if(ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaMAUtil|updateReg|:"+ex);		
		}finally{
			if(ps!=null) ps.close();
		}		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String codigoPersonal, String cargaId, String actividadId) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.HCA_MAESTRO_ACTIVIDAD"+ 
				" WHERE CODIGO_PERSONAL = ?" +
				" AND CARGA_ID = ?" +
				" AND ACTIVIDAD_ID = TO_NUMBER(?, '999')");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, cargaId);
			ps.setString(3, actividadId);
			
			if(ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaMAUtil|deleteReg|:"+ex);			
		}finally{
			if(ps!=null) ps.close();
		}
		return ok;
	}
	
	public HcaMaestroActividad mapeaRegId(Connection con, String codigoPersonal, String cargaId, String actividadId) throws SQLException{
		HcaMaestroActividad hcaMaestroActividad = new HcaMaestroActividad();
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT CODIGO_PERSONAL, CARGA_ID, ACTIVIDAD_ID, SEMANAS, HORAS" +
					" FROM ENOC.HCA_MAESTRO_ACTIVIDAD" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND CARGA_ID = ?" +
					" AND ACTIVIDAD_ID = TO_NUMBER(?, '999')");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, cargaId);
			ps.setString(3, actividadId);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				
				hcaMaestroActividad.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaMAUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return hcaMaestroActividad;
	}
	
	public boolean existeReg(Connection conn, String codigoPersonal, String cargaId, String actividadId) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.HCA_MAESTRO_ACTIVIDAD" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND CARGA_ID = ?" +
					" AND ACTIVIDAD_ID = TO_NUMBER(?, '999')");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, cargaId);
			ps.setString(3, actividadId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaMAUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return ok;
	}
	
	public static boolean existeActividad(Connection conn, String actividadId) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		boolean ok		= false;
		
		try{
			ps = conn.prepareStatement("SELECT ACTIVIDAD_ID FROM ENOC.HCA_MAESTRO_ACTIVIDAD WHERE ACTIVIDAD_ID = TO_NUMBER(?,'999')");
			ps.setString(1, actividadId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaMAUtil|existeActividad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}			
		return ok;
	}
	
	public ArrayList<HcaMaestroActividad> getListMaestroCarga(Connection conn, String codigoPersonal, String cargaId, String orden ) throws SQLException{	
		
		ArrayList<HcaMaestroActividad> lisMaestro	= new ArrayList<HcaMaestroActividad>();
		Statement st 						= conn.createStatement();
		ResultSet rs 						= null;
		String comando						= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CARGA_ID, ACTIVIDAD_ID, SEMANAS, HORAS" +
					" FROM ENOC.HCA_MAESTRO_ACTIVIDAD" + 
					" WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'" +
					" AND CARGA_ID = '"+cargaId+"' " +orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				HcaMaestroActividad ma = new HcaMaestroActividad();
				ma.mapeaReg(rs);
				lisMaestro.add(ma);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaMAUtil|getListMaestroCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisMaestro;
	}
	
	public HashMap<String, String> getMapTotalHoras(Connection conn, String cargaId) throws SQLException{
		
		HashMap<String, String> mapa		= new HashMap<String, String>();
		Statement st 						= conn.createStatement();
		ResultSet rs 						= null;
		String comando						= "";
		
		try{
			comando = "SELECT A.CODIGO_PERSONAL, A.CARGA_ID, B.TIPO_ID, SUM(SEMANAS*HORAS*B.VALOR) AS TOTAL " +
					"FROM ENOC.HCA_MAESTRO_ACTIVIDAD A, ENOC.HCA_ACTIVIDAD B WHERE A.CARGA_ID = '"+cargaId+"' " +
					"AND B.ACTIVIDAD_ID = A.ACTIVIDAD_ID GROUP BY A.CODIGO_PERSONAL, A.CARGA_ID, B.TIPO_ID";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("CODIGO_PERSONAL")+rs.getString("CARGA_ID")+rs.getString("TIPO_ID"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaMAUtil|getMapTotalHoras|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapa;
	}
}