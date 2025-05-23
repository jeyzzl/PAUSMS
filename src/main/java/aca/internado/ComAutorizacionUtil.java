/**
 * 
 */
package aca.internado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Elifo
 *
 */
public class ComAutorizacionUtil {
	
	public boolean insertReg(Connection conn, ComAutorizacion comAutorizacion ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO NOE.COM_AUTORIZACION" +
					" VALUES(?,?,?,TO_DATE(?,'DD/MM/YYYY'),TO_DATE(?,'DD/MM/YYYY'),?,?,?,?,?)");
			ps.setString(1, comAutorizacion.getMatricula());
			ps.setInt(2,Integer.parseInt(comAutorizacion.getNumComidas()));
			ps.setString(3, comAutorizacion.getTipoComida());
			ps.setString(4, comAutorizacion.getFechaInicial());
			ps.setString(5, comAutorizacion.getFechaFinal());
			ps.setString(6, comAutorizacion.getUsuario());
			ps.setString(7, comAutorizacion.getCliente());
			ps.setString(8, comAutorizacion.getPaquete());
			ps.setInt(9,Integer.parseInt(comAutorizacion.getCargaId()));
			ps.setString(10, comAutorizacion.getBloque());
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error aca.internado.ComAutorizacionUtil insertReg: "+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	public boolean updateReg(Connection conn, ComAutorizacion comAutorizacion ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE NOE.COM_AUTORIZACION"+
					" SET NUM_COMIDAS = ?, TIPO_COMIDA = ?," +
					" FECHA_INICIAL = TO_DATE(?,'DD/MM/YYYY')," +
					" FECHA_FINAL = TO_DATE(?,'DD/MM/YYYY'),"+
					" USUARIO = ?, CLIENTE = ?, PAQUETE = ?"+
					" WHERE MATRICULA = ?" +
					" AND CARGA_ID = ?" +
					" AND BLOQUE = ?");			
			ps.setInt(1,Integer.parseInt(comAutorizacion.getNumComidas()));
			ps.setString(2, comAutorizacion.getTipoComida());
			ps.setString(3, comAutorizacion.getFechaInicial());
			ps.setString(4, comAutorizacion.getFechaFinal());
			ps.setString(5, comAutorizacion.getUsuario());
			ps.setString(6, comAutorizacion.getCliente());
			ps.setString(7, comAutorizacion.getPaquete());
			ps.setString(8, comAutorizacion.getMatricula());
			ps.setInt(9,Integer.parseInt(comAutorizacion.getCargaId()));
			ps.setString(10, comAutorizacion.getBloque());
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error aca.internado.ComAutorizacionUtil|updateReg|: "+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String matricula, String cargaId, String bloque ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM NOE.COM_AUTORIZACION" +
					" WHERE MATRICULA = ?" +
					" AND CARGA_ID = ?" +
					" AND BLOQUE = ?");
			ps.setString(1,matricula);
			ps.setString(2, cargaId);
			ps.setInt(3, Integer.parseInt(bloque));
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error aca.internado.ComAutorizacionUtil|deleteReg|: "+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public ComAutorizacion mapeaRegId(Connection con, String matricula, String cargaId, String bloque) throws SQLException{
		ComAutorizacion comAutorizacion = new ComAutorizacion();
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{ 
			ps = con.prepareStatement("SELECT MATRICULA, NUM_COMIDAS," +
					" TIPO_COMIDA, TO_CHAR(FECHA_INICIAL, 'DD/MM/YYYY') AS FECHA_INICIAL," +
					" TO_CHAR(FECHA_FINAL, 'DD/MM/YYYY') AS FECHA_FINAL, USUARIO," +
					" CLIENTE, PAQUETE, CARGA_ID, BLOQUE" +
					" FROM NOE.COM_AUTORIZACION" +
					" WHERE MATRICULA = ?" +
					" AND CARGA_ID = ?" +
					" AND BLOQUE = ?");
			ps.setString(1,matricula);
			ps.setString(2, cargaId);
			ps.setInt(3, Integer.parseInt(bloque));
			rs = ps.executeQuery();
			if(rs.next()){
				comAutorizacion.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.ComAutorizacionUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return comAutorizacion;
	}
	
	public boolean existeReg(Connection con, String matricula, String cargaId, String bloque) throws SQLException{
		PreparedStatement ps = null;
		boolean ok = false;
		ResultSet rs = null;		
		try{ 
			ps = con.prepareStatement("SELECT * FROM NOE.COM_AUTORIZACION" +
					" WHERE MATRICULA = ?" +
					" AND CARGA_ID = ?" +
					" AND BLOQUE = ?");
			ps.setString(1,matricula);
			ps.setString(2, cargaId);
			ps.setInt(3, Integer.parseInt(bloque));
			rs = ps.executeQuery();
			if(rs.next()){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.ComAutorizacionUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
		
	public static String numComidas(Connection con, String matricula, String cargaId, String bloque) throws SQLException{
		PreparedStatement ps = null;		
		ResultSet rs = null;		
		String numComidas = "0";
		
		try{ 
			ps = con.prepareStatement("SELECT COALESCE(NUM_COMIDAS,0) NUM_COMIDAS FROM NOE.COM_AUTORIZACION"
					+ " WHERE MATRICULA = ?"
					+ " AND CARGA_ID = ?"
					+ " AND BLOQUE = ?");
			ps.setString(1,matricula);
			ps.setString(2, cargaId);
			ps.setInt(3, Integer.parseInt(bloque));
			rs = ps.executeQuery();
			if(rs.next()){
				numComidas = rs.getString("NUM_COMIDAS");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.ComAutorizacionUtil|numComidas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return numComidas;
	}
	
	public ArrayList<ComAutorizacion> getListAll(Connection conn, String orden) throws Exception {
		ArrayList<ComAutorizacion> listor = new ArrayList<ComAutorizacion>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{
			comando = "SELECT MATRICULA, NUM_COMIDAS, TIPO_COMIDA," +
					" TO_CHAR(FECHA_INICIAL, 'DD/MM/YYYY) AS FECHA_INICIAL," +
					" TO_CHAR(FECHA_FINAL, 'DD/MM/YYYY) AS FECHA_FINAL," +
					" USUARIO, CLIENTE, PAQUETE, CARGA_ID, BLOQUE" +
					" FROM NOE.COM_AUTORIZACION" +
					" WHERE MATRICULA IN (SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS) "+orden;			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ComAutorizacion dormi = new ComAutorizacion();
				dormi.mapeaReg(rs);
				listor.add(dormi);
			}
		}catch(Exception ex){
			System.out.println("Error  - aca.internado.ComAutorizacionUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return listor;
	}
	
	public ArrayList<ComAutorizacion> getListDormi(Connection conn, String dormitorio, String orden) throws Exception {
		ArrayList<ComAutorizacion> lisComidas = new ArrayList<ComAutorizacion>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{
			comando = "SELECT MATRICULA, NUM_COMIDAS, TIPO_COMIDA," +
					" TO_CHAR(FECHA_INICIAL, 'DD/MM/YYYY') AS FECHA_INICIAL," +
					" TO_CHAR(FECHA_FINAL, 'DD/MM/YYYY') AS FECHA_FINAL," +
					" USUARIO, CLIENTE, PAQUETE, CARGA_ID, BLOQUE" +
					" FROM NOE.COM_AUTORIZACION" +
					" WHERE ALUM_DORMITORIO(MATRICULA) = '"+dormitorio+"'" +
					" AND MATRICULA IN (SELECT CODIGO_PERSONAL" +
											" FROM ENOC.INSCRITOS" +
											" WHERE MODALIDAD_ID IN (1,2,3,4)" +
											" AND RESIDENCIA_ID = 'I')" +
					" AND CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA" + 
									" WHERE now() " +
									" BETWEEN F_INICIO AND F_FINAL) "+orden;	
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ComAutorizacion dormi = new ComAutorizacion();
				dormi.mapeaReg(rs);
				lisComidas.add(dormi);
			}
		}catch(Exception ex){
			System.out.println("Error  - aca.internado.ComAutorizacionUtil|getListDormi|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lisComidas;
	}
	
	public HashMap<String,ComAutorizacion> mapComidasAlumno( Connection conn, String cargas ) throws SQLException{
		
		HashMap<String,aca.internado.ComAutorizacion> mapa	= new HashMap<String,aca.internado.ComAutorizacion>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave 				= "";
		
		try{
			comando = " SELECT MATRICULA, NUM_COMIDAS, TIPO_COMIDA,"
					+ " TO_CHAR(FECHA_INICIAL, 'DD/MM/YYYY') AS FECHA_INICIAL,"
					+ " TO_CHAR(FECHA_FINAL, 'DD/MM/YYYY') AS FECHA_FINAL," 
					+ " USUARIO, CLIENTE, PAQUETE, CARGA_ID, BLOQUE"
					+ " FROM NOE.COM_AUTORIZACION"
					+ " WHERE CARGA_ID IN ("+cargas+")";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				aca.internado.ComAutorizacion comida = new aca.internado.ComAutorizacion();
				comida.mapeaReg(rs);
				
				llave = rs.getString("MATRICULA")+rs.getString("CARGA_ID")+rs.getString("BLOQUE");
				mapa.put(llave, comida);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.internado.ComAutorizacionUtil|mapComidasAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapa;
	}
}