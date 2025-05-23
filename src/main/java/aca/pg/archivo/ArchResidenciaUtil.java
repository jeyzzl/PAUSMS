package aca.pg.archivo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ArchResidenciaUtil {
	
	@Autowired
	@Qualifier("jdbcArchivo")
	private JdbcTemplate archivoJdbc;
	
	// Insert original---
	public boolean insertRegByte(Connection conn, ArchResidencia imagen) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement("INSERT INTO ARCH_RESIDENCIA"+
				"(CODIGO_PERSONAL, FOLIO, IMAGEN ) "+
				"VALUES(?, ?, ?)");
			ps.setString(1, imagen.getCodigoPersonal());
			ps.setString(2, imagen.getFolio());
			ps.setBytes(3, imagen.getImagen());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;		
			
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.ArchResidenciaUtil|insertRegByte|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	// Insert con JDBC Template
	public boolean insertRegByte( ArchResidencia imagen) throws Exception{
		
		boolean ok = false;
		try{
			String comando = "INSERT INTO ARCH_RESIDENCIA (CODIGO_PERSONAL, FOLIO, IMAGEN)"
					+" VALUES(?, ?, ?)";
			Object[] parametros = new Object[] {imagen.getCodigoPersonal(),imagen.getFolio(),imagen.getImagen()};
			if (archivoJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch( Exception ex){
			System.out.println("Error-aca.pg.archivo.ArchResidenciaUtil||insertRegByte:"+ex);
		}
		
		return ok;		
	}

	public boolean updateReg(Connection conn, ArchResidencia imagen ) throws Exception{
		PreparedStatement ps 	= null;		
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement("UPDATE ARCH_RESIDENCIA SET"
					+ " IMAGEN = ?"					
					+ " WHERE CODIGO_PERSONAL = ? AND FOLIO = ?");
			ps.setBytes(1, imagen.getImagen());			
			ps.setString(2, imagen.getCodigoPersonal());
			ps.setString(3, imagen.getFolio());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;	
			
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.ArchResidenciaUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}

	public boolean deleteReg(Connection conn, String codigoPersonal, String folio ) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("DELETE FROM ARCH_RESIDENCIA"
					+ " WHERE CODIGO_PERSONAL = ? AND FOLIO = ? ");
			ps.setString(1, codigoPersonal);
			ps.setString(2, folio);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;		
			
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.ArchResidenciaUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public ArchResidencia mapeaRegId( Connection conn, String codigoPersonal, String folio) throws SQLException{
		PreparedStatement ps 	= null;
		ResultSet rs 			= null;
		ArchResidencia imagen 	= new ArchResidencia();
		
		try{
		ps = conn.prepareStatement("SELECT CODIGO_PERSONAL, FOLIO, IMAGEN "
				+ " FROM ARCH_RESIDENCIA"
				+ " WHERE CODIGO_PERSONAL = ? AND FOLIO = ? ");
		ps.setString(1, codigoPersonal);
		ps.setString(2, folio);
		
		rs = ps.executeQuery();
		if (rs.next()){
			imagen.mapeaReg(rs);
		}
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.ArchResidenciaUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return imagen;
	}
	
	public ArchResidencia mapeaRegId(String codigoPersonal, String folio) throws SQLException{
		ArchResidencia imagen = new ArchResidencia();
		
		try{			
			String query = "SELECT CODIGO_PERSONAL, FOLIO, IMAGEN"
					+ " FROM ARCH_RESIDENCIA"
					+ " WHERE CODIGO_PERSONAL = ? AND FOLIO = ?";
			Object[] parametros = new Object[]{codigoPersonal,folio};
			imagen = archivoJdbc.queryForObject(query, parametros, new ArchResidenciaMapper());
		}catch( Exception ex){
			System.out.println("Error: aca.pg.archivo.ArchResidenciaUtil|mapeaRegId|:"+ex);
		}
		
		return imagen;
	}

	public boolean existeReg(Connection conn, String codigoPersonal, String folio) throws SQLException{
		
		PreparedStatement ps	= null;
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL FROM ARCH_RESIDENCIA"
					+ " WHERE CODIGO_PERSONAL = ? AND FOLIO = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, folio);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.ArchResidenciaUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn, String codigoPersonal) throws SQLException{
		
		PreparedStatement ps	= null;		
		ResultSet 		rs		= null;
		String folio 			= "000";
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(TO_NUMBER(FOLIO,'999'))+1,0) AS MAXIMO FROM ARCH_RESIDENCIA WHERE CODIGO_PERSONAL = ?");
			ps.setString(1, codigoPersonal);			
			
			rs = ps.executeQuery();
			if (rs.next()){
				folio = rs.getString("MAXIMO");				
				if (folio.length()==1) folio = "00"+folio;
				if (folio.length()==2) folio = "0"+folio;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.ArchResidenciaUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return folio;
	}
	
	public ArrayList<ArchResidencia> getLista(Connection Conn, String codigoPersonal, String orden ) throws SQLException{
		
		ArrayList<ArchResidencia> list 	= new ArrayList<ArchResidencia>();
		Statement st 					= Conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = " SELECT * FROM ARCH_RESIDENCIA"
					+ " WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				ArchResidencia obj = new ArchResidencia();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.ArchResidenciaUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		
		return list;
	}
	
}
