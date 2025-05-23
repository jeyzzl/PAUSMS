package aca.pg.archivo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AlumFotoUtil {
	
	@Autowired
	@Qualifier("jdbcArchivo")
	private JdbcTemplate archivoJdbc;
	
	// Insert original---
	public boolean insertRegByte(Connection conn, AlumFoto foto) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;		
		try{
			ps = conn.prepareStatement("INSERT INTO ALUM_FOTO"+
				"(CODIGO_PERSONAL, TIPO, FOTO, FECHA, USUARIO) "+
				"VALUES(?, ?, ?, TO_DATE(?, 'DD/MM/YYYY'), ? )");
			ps.setString(1, foto.getCodigoPersonal());
			ps.setString(2, foto.getTipo());
			ps.setBytes(3, foto.getFoto());			
			ps.setString(4, foto.getFecha());
			ps.setString(5, foto.getUsuario());			
			if (ps.executeUpdate()== 1)
				ok = true;
			
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.AlumFotoUtil|insertRegByte|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	// Insert con JDBC Template
	public boolean insertRegByte( AlumFoto foto) throws Exception{
		
		boolean ok = false;
		try{
			String comando = "INSERT INTO ALUM_FOTO (CODIGO_PERSONAL, TIPO, FOTO, FECHA, USUARIO)"
					+" VALUES(?, ?, ?, TO_DATE(?,'DD/MM/YYYY'), ?)";
			Object[] parametros = new Object[] {foto.getCodigoPersonal(),foto.getTipo(),foto.getFoto(), foto.getFecha(),foto.getUsuario()};
			if (archivoJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch( Exception ex){
			System.out.println("Error-aca.pg.archivo.AlumFotoUtil||insertRegByte:"+ex);
		}
		
		return ok;		
	}

	public boolean updateReg(Connection conn, AlumFoto foto ) throws Exception{
		PreparedStatement ps 	= null;		
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement("UPDATE ALUM_FOTO SET"
					+ " FOTO = ?,"					
					+ " FECHA = TO_DATE(?,'DD/MM/YYYY'),"
					+ " USUARIO = ?"
					+ " WHERE CODIGO_PERSONAL = ? AND TIPO = ?");
			ps.setBytes(1, foto.getFoto());			
			ps.setString(2, foto.getFecha());
			ps.setString(3, foto.getUsuario());
			ps.setString(4, foto.getCodigoPersonal());
			ps.setString(5, foto.getTipo());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;	
			
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.AlumFotoUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	// Update con JDBC Template
	public boolean updateReg( AlumFoto foto) throws Exception{
		
		boolean ok = false;
		try{
			String comando = "UPDATE ALUM_FOTO SET FOTO = ?, FECHA = TO_DATE(?,'DD/MM/YYYY'), USUARIO = ? WHERE CODIGO_PERSONAL = ? AND TIPO = ?";
			Object[] parametros = new Object[] {foto.getFoto(),foto.getFecha(),foto.getUsuario(), foto.getCodigoPersonal(),foto.getTipo()};
			if (archivoJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch( Exception ex){
			System.out.println("Error-aca.pg.archivo.AlumFotoUtil||updateReg:"+ex);
		}
		
		return ok;		
	}

	public boolean deleteReg(Connection conn, String codigoPersonal, String tipo ) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("DELETE FROM ALUM_FOTO WHERE CODIGO_PERSONAL = ? AND TIPO = ? ");
			ps.setString(1, codigoPersonal);
			ps.setString(2, tipo);			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.AlumFotoUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	// Delete con JDBC Template
	public boolean deleteReg( String codigoPersonal, String tipo) throws Exception{		
		boolean ok = false;
		try{
			String comando = "DELETE FROM ALUM_FOTO WHERE CODIGO_PERSONAL = ? AND TIPO = ?";
			Object[] parametros = new Object[] {codigoPersonal,tipo};
			if (archivoJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch( Exception ex){
			System.out.println("Error-aca.pg.archivo.AlumFotoUtil||deleteReg:"+ex);
		}
		
		return ok;		
	}
	
	public AlumFoto mapeaRegId( Connection conn, String codigoPersonal, String tipo) throws SQLException{
		PreparedStatement ps 	= null;
		ResultSet rs 			= null;
		AlumFoto foto 	= new AlumFoto();
		
		try{
		ps = conn.prepareStatement("SELECT CODIGO_PERSONAL, TIPO, FOTO, FECHA, USUARIO"
				+ " FROM ALUM_FOTO"
				+ " WHERE CODIGO_PERSONAL = ? AND TIPO = ? ");
		ps.setString(1, codigoPersonal);
		ps.setString(2, tipo);
		
		rs = ps.executeQuery();
		if (rs.next()){
			foto.mapeaReg(rs);
		}
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.AlumFotoUtil|mapeaRegId|:"+codigoPersonal+":"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return foto;
	}
	
	public AlumFoto mapeaRegId(String codigoPersonal, String tipo) throws SQLException{
		AlumFoto foto = new AlumFoto();
		
		try{			
			String query = "SELECT CODIGO_PERSONAL, TIPO, FOTO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, USUARIO"
					+ " FROM ALUM_FOTO"
					+ " WHERE CODIGO_PERSONAL = ? AND TIPO = ?";
			Object[] parametros = new Object[]{codigoPersonal,tipo};
			foto = archivoJdbc.queryForObject(query, parametros, new AlumFotoMapper());
		}catch( Exception ex){
			System.out.println("Error:aca.pg.archivo.AlumFotoUtil|mapeaRegId|:"+ex);
		}
		
		return foto;
	}
	
	public boolean existeReg(String codigoPersonal, String tipo) throws SQLException{
		boolean ok = false;
		try{
			String query = "SELECT COUNT(CODIGO_PERSONAL) FROM ALUM_FOTO"
					+ " WHERE CODIGO_PERSONAL = ? AND TIPO = ?";
			Object[] parametros = new Object[]{codigoPersonal,tipo};
			if (archivoJdbc.queryForObject(query, parametros, Integer.class) >= 1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.AlumFotoUtil|existeReg|:"+ex);
		}
		
		return ok;
	}
/*	
	public boolean existeRegistro( String codigoPersonal, String tipo) throws SQLException{
		
		aca.conecta.Conectar conecta		= new aca.conecta.Conectar();
		Connection conArchivo 	= null;		
		PreparedStatement ps	= null;
		boolean 		ok 		= false;
		ResultSet 		rs		= null;		
		try{
			conArchivo = conecta.conPostgres();	
			ps = conArchivo.prepareStatement("SELECT CODIGO_PERSONAL FROM ALUM_FOTO"
					+ " WHERE CODIGO_PERSONAL = ? AND TIPO = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, tipo);			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.AlumFotoUtil|existeReg|:"+ex);
		}finally{
			if (conArchivo!=null) conArchivo.close();
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
*/
	public boolean existeReg(Connection conn, String codigoPersonal, String tipo) throws SQLException{
		
		PreparedStatement ps	= null;
		boolean 		ok 		= false;
		ResultSet 		rs		= null;		
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL FROM ALUM_FOTO"
					+ " WHERE CODIGO_PERSONAL = ? AND TIPO = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, tipo);			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.AlumFotoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String getFoto(String codigoPersonal, String tipo){
		
		AlumFoto alumFoto		= new AlumFoto();
		byte[] fotoByte			= null;
		String fotoString		= "";		
		try{
			String query = "SELECT COUNT(CODIGO_PERSONAL) FROM ALUM_FOTO WHERE CODIGO_PERSONAL = ? AND TIPO = ?";
			Object[] parametros = new Object[]{codigoPersonal,tipo};
			if (archivoJdbc.queryForObject(query, parametros, Integer.class) >= 1){				
				// Busca la foto 
				query = "SELECT CODIGO_PERSONAL, TIPO, FOTO, FECHA, USUARIO FROM ALUM_FOTO WHERE CODIGO_PERSONAL = ? AND TIPO = ?";				
				alumFoto = archivoJdbc.queryForObject(query, parametros, new AlumFotoMapper());				
				fotoByte = alumFoto.getFoto();				
			}else {
				// Busca la foto 
				query = "SELECT CODIGO_PERSONAL, TIPO, FOTO, FECHA, USUARIO FROM ALUM_FOTO WHERE CODIGO_PERSONAL = 'nofoto' AND TIPO = 'O'";				
				alumFoto = archivoJdbc.queryForObject(query, new AlumFotoMapper());				
				fotoByte = alumFoto.getFoto();
			}
			if (fotoByte!=null) {
				fotoString = java.util.Base64.getEncoder().encodeToString(fotoByte);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.AlumFotoUtil|getFoto|:"+ex);
		}
		
		return fotoString;
	}
	
	public byte[] getFotoByte(String codigoPersonal, String tipo){
		
		AlumFoto alumFoto		= new AlumFoto();
		byte[] fotoByte			= null;	
		
		try{
			String query = "SELECT COUNT(CODIGO_PERSONAL) FROM ALUM_FOTO WHERE CODIGO_PERSONAL = ? AND TIPO = ?";
			Object[] parametros = new Object[]{codigoPersonal,tipo};
			if (archivoJdbc.queryForObject(query, parametros, Integer.class) >= 1){				
				// Busca la foto 
				query = "SELECT CODIGO_PERSONAL, TIPO, FOTO, FECHA, USUARIO FROM ALUM_FOTO WHERE CODIGO_PERSONAL = ? AND TIPO = ?";				
				alumFoto = archivoJdbc.queryForObject(query, parametros, new AlumFotoMapper());				
				fotoByte = alumFoto.getFoto();				
			}else {
				// Busca la foto 
				query = "SELECT CODIGO_PERSONAL, TIPO, FOTO, FECHA, USUARIO FROM ALUM_FOTO WHERE CODIGO_PERSONAL = 'nofoto' AND TIPO = 'O'";				
				alumFoto = archivoJdbc.queryForObject(query, new AlumFotoMapper());				
				fotoByte = alumFoto.getFoto();
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.AlumFotoUtil|getFotoByte|:"+ex);
		}
		
		return fotoByte;
	}
	
	public List<AlumFoto> fotoAlumnoTipo(String tipo){
		List<AlumFoto> lista 	= new ArrayList<AlumFoto>();	
		
		try{
			String query = "SELECT CODIGO_PERSONAL, TIPO, FOTO, FECHA, USUARIO FROM ALUM_FOTO WHERE TIPO = ?";
			Object[] parametros = new Object[]{tipo};				
			lista = archivoJdbc.queryForList(query,parametros, AlumFoto.class);			
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.AlumFotoUtil|fotoAlumnoTipo|:"+ex);
		}
		
		return lista;
	}	
	
	public List<String> tieneFoto(String tipo){
		List<String> lista = new ArrayList<String>();		
		try{
			String query = "SELECT CODIGO_PERSONAL FROM ALUM_FOTO WHERE TIPO = ?";
			Object[] parametros = new Object[]{tipo};				
			lista = archivoJdbc.queryForList(query,parametros, String.class);			
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.AlumFotoUtil|getFoto|:"+ex);
		}
		
		return lista;
	}	

	public List<AlumFoto> lisEmpleados(String tipo){
		
		List<AlumFoto> lista 	= new ArrayList<AlumFoto>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, TIPO, FECHA, USUARIO FROM ALUM_FOTO WHERE CODIGO_PERSONAL LIKE '9%' AND FOTO IS NOT NULL AND TIPO = ?";
			Object[] parametros = new Object[]{"O"};
			lista = archivoJdbc.query(comando, parametros, new AlumFotoMapperCorto());			
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.AlumFotoUtil|listaEmpleados|:"+ex);
		}	
		
		return lista;
	}
	
}
