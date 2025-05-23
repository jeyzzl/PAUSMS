// Clase para la tabla de Modulo
package aca.menu.spring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ConJspDao{
	
	@Autowired	
	@Qualifier("jdbcEnoc")	
	private JdbcTemplate enocJdbc;
	
	// Insert original---
	public boolean insertReg(Connection conn, ConJsp jsp) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CON_JSP"+
				"(RUTA, ABRIR, CERRAR) "+
				"VALUES(?, TO_NUMBER(?,'99999'), TO_NUMBER(?,'99999'))");
			ps.setString(1, jsp.getRuta());
			ps.setString(2, jsp.getAbrir() );
			ps.setString(3, jsp.getCerrar() );			
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;		
			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.ConJspDao|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	// Insert con JDBC Template
	public boolean insertReg( ConJsp jsp) throws Exception{
		
		boolean ok = false;
		try{
			String comando = "INSERT INTO ENOC.CON_JSP (RUTA, ABRIR, CERRAR)"
					+" VALUES(?, TO_NUMBER(?,'99999'), TO_NUMBER(?,'99999'))";
			Object[] parametros = new Object[] {jsp.getRuta(),jsp.getAbrir(),jsp.getCerrar()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch( Exception ex){
			System.out.println("Error-aca.menu.spring.ConJspDao||insertReg:"+ex);
		}
		
		return ok;		
	}
	
	
	public boolean updateReg(Connection conn, ConJsp jsp ) throws Exception{
		PreparedStatement ps 	= null;		
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CON_JSP SET"
					+ " ABRIR = TO_NUMBER(?,'99999'),"
					+ " CERRAR = TO_NUMBER(?,'99999')"					
					+ " WHERE RUTA = ?");
			ps.setString(1, jsp.getAbrir());
			ps.setString(2, jsp.getCerrar());
			ps.setString(3, jsp.getRuta());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;	
			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.ConJspDao|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	// Insert con JDBC Template
	public boolean updateReg( ConJsp jsp) throws Exception{
		
		boolean ok = false;
		try{
			String comando = "UPDATE ENOC.CON_JSP SET ABRIR = TO_NUMBER(?,'99999'),  CERRAR = TO_NUMBER(?,'99999')  WHERE RUTA = ?";
			System.out.println(comando+":"+jsp.getAbrir()+":"+jsp.getCerrar()+":"+jsp.getRuta());
			Object[] parametros = new Object[] {jsp.getAbrir(),jsp.getCerrar(),jsp.getRuta()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch( Exception ex){
			System.out.println("Error-aca.menu.spring.ConJspDao||updateReg:"+ex);
		}
		
		return ok;		
	}
	
	public boolean existeReg( Connection conn, String ruta) throws SQLException{
				
		PreparedStatement ps	= null;
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		
		try{				
			ps = conn.prepareStatement("SELECT RUTA FROM ENOC.CON_JSP"
					+ " WHERE RUTA = ?");
			ps.setString(1, ruta);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - Error-aca.menu.spring.ConJspDao||existeReg|:"+ex);
		}finally{			
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean existeReg(String ruta){
		boolean ok = false;
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CON_JSP WHERE RUTA = ?";
			Object[] parametros = new Object[]{ruta};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}
		}catch( Exception ex){
			System.out.println("Error-aca.menu.ConJspDao||existeReg"+ex);
		}
		
		return ok;
		
	}
	
	public ConJsp traePorRutaCorta(String ruta){
		ConJsp jsp = new ConJsp();
		
		try{			
			String query = "SELECT RUTA, ABRIR, CERRAR FROM ENOC.CON_JSP WHERE RUTA = ?";
			Object[] parametros = new Object[]{ruta};
			jsp = enocJdbc.queryForObject(query, new ConJspMapper(), parametros);
		}catch( Exception ex){
			System.out.println("Error: aca.menu.spring.ConJspDao||"+ex);
		}
		
		return jsp;
	}	
}