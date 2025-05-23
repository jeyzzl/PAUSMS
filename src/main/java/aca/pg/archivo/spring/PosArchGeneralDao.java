/**
 * 
 */
package aca.pg.archivo.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PosArchGeneralDao {	

	@Autowired
	@Qualifier("jdbcArchivo")
	private JdbcTemplate archivoJdbc;
		
	public boolean insertReg( PosArchGeneral general){
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ARCH_GENERAL(MATRICULA, FOLIO, FECHA, USUARIO, IMAGEN, IMAGEN_BYTE)" +
					" VALUES(?, TO_NUMBER(?,'999'),TO_DATE(?, 'DD/MM/YYYY'), ?, ?, ?)";
			Object[] parametros = new Object[] {
					general.getMatricula(), general.getFolio(), general.getFecha(), general.getUsuario(), 0, general.getImagenByte()  
		 	};
			if (archivoJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.spring.PosArchGeneralDao|insertReg|:"+ex);
		}
		return ok;
	}
	
	public int deletePorAlumno(String matricula){
		int borrados = 0;		
		try{
			List<PosArchGeneral> lisAlumnos = this.lisAlumnos(matricula, " ORDER BY FOLIO");
			for (PosArchGeneral general : lisAlumnos) {
				String comando = "SELECT COUNT(LOID) FROM PG_CATALOG.PG_LARGEOBJECT WHERE LOID = ?";
				Object[] parImagen = new Object[] {general.getImagen()};				
				boolean unlink = true;
				if (archivoJdbc.queryForObject(comando, Integer.class, parImagen) >= 1) {
					comando = "SELECT LO_UNLINK("+general.getImagen()+")";
					archivoJdbc.execute(comando);
					comando = "SELECT COUNT(LOID) FROM PG_CATALOG.PG_LARGEOBJECT WHERE LOID = ?";
					if (archivoJdbc.queryForObject(comando, Integer.class, parImagen) >= 1){
						unlink = false;
					}
				}			
				if (unlink) {
					Object[] parametros = new Object[] {matricula,general.getFolio()};
					comando = "DELETE FROM ARCH_GENERAL WHERE MATRICULA = ? AND FOLIO = TO_NUMBER(?,'999')";
					if (archivoJdbc.update(comando,parametros) == 1) {
						borrados++;
					}							
				}					
			}		    		
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.spring.PosArchGeneralDao|deletePorAlumno|:"+ex);
		}	
		return borrados;
	}
	
	public boolean deletePorMatriculaAndFolio(String matricula, String folio){		
		int imagen = 0;
		boolean unlink 	= false;
		boolean ok 		= false;
		try{		
			String comando = "SELECT COUNT(*) FROM ARCH_GENERAL WHERE MATRICULA = ? AND FOLIO = TO_NUMBER(?,'999')";			
			Object[] parametros = new Object[] {matricula,folio};
			if (archivoJdbc.queryForObject(comando, Integer.class, parametros) >= 1){				
				comando = "SELECT COALESCE(IMAGEN,0) FROM ARCH_GENERAL WHERE MATRICULA = ? AND FOLIO = TO_NUMBER(?,'999')";
				imagen = archivoJdbc.queryForObject(comando, Integer.class, parametros);
				
				comando = "SELECT COUNT(LOID) FROM PG_CATALOG.PG_LARGEOBJECT WHERE LOID = ?";
				Object[] parImagen = new Object[] {imagen};
				if (archivoJdbc.queryForObject(comando, Integer.class, parImagen) >= 1){
					comando = "SELECT LO_UNLINK("+imagen+")";
					archivoJdbc.execute(comando);
					comando = "SELECT COUNT(LOID) FROM PG_CATALOG.PG_LARGEOBJECT WHERE LOID = ?";
					if (archivoJdbc.queryForObject(comando, Integer.class, parImagen) == 0){
						unlink = true;
					}
				}else {
					unlink = true;
				}
				
				if (unlink) {				
					comando = "DELETE FROM ARCH_GENERAL WHERE MATRICULA = ? AND FOLIO = TO_NUMBER(?,'999')";
					if (archivoJdbc.update(comando,parametros) == 1) {
						ok  = true;
					}							
				}
			}
						    		
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.spring.PosArchGeneralDao|deletePorMatriculaAndFolio|:"+ex);
		}	
		return ok;
	}
	
	public boolean deleteReg(String matricula, String folio) {
		boolean ok = false;	
		try{
		    String sql = "DELETE FROM ARCH_GENERAL WHERE MATRICULA = ? AND FOLIO = TO_NUMBER(?,'999')";
		    Object[] parametros = new Object[] {matricula,folio };
			if (archivoJdbc.update(sql,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.ArchGeneral|deleteReg(fila)|:"+ex);
		}
		
		return ok;
	}	
	
	public PosArchGeneral mapeaRegId(String matricula, String folio){
		PosArchGeneral general = new PosArchGeneral();
		try{ 
			String comando = "SELECT COUNT(*) FROM ARCH_GENERAL WHERE MATRICULA = ? AND FOLIO = TO_NUMBER(?,'999')";			
			Object[] parametros = new Object[] {matricula,folio};
			if (archivoJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				comando = "SELECT MATRICULA, FOLIO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, USUARIO, IMAGEN, IMAGEN_BYTE"
						+ " FROM ARCH_GENERAL"
						+ " WHERE MATRICULA = ?"
						+ " AND FOLIO = TO_NUMBER(?,'999')";
				general = archivoJdbc.queryForObject(comando, new PosArchGeneralMapperLargo(), parametros);
			}
		}catch(Exception ex){ 			
 			System.out.println("Error - aca.pg.archivo.ArchGeneral|mapeaRegId|:"+ex);
 		}
		return general;
	}
	
	/*
	public boolean updateReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ARCH_GENERAL" +
					" SET FECHA = TO_DATE(?, 'DD/MM/YYY')," +
					" USUARIO = ?," +
					" IMAGEN = ?" +
					" WHERE MATRICULA = ?" +
					" AND FOLIO = TO_NUMBER(?,'999')");
			
			ps.setString(1, fecha);
			ps.setString(2, usuario);
			ps.setInt(3, imagen);
			ps.setString(4, matricula);
			ps.setString(5, folio);
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.ArchGeneral|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		
		try{
		    String sql="DELETE FROM ARCH_GENERAL"+
		    		" WHERE MATRICULA = ?"+
		    		" AND FOLIO = TO_NUMBER(?,'999')";
			ps = conn.prepareStatement(sql);
			ps.setString(1, matricula);
			ps.setString(2, folio);
			
			if(ps.executeUpdate()==1){
			    ok=true;				
			}
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.ArchGeneral|deleteReg(fila)|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		matricula		= rs.getString("MATRICULA");
		folio			= rs.getString("FOLIO");
		fecha			= rs.getString("FECHA");
		usuario			= rs.getString("USUARIO");
		imagen			= rs.getInt("IMAGEN");
	}
	
	
	
	
	public boolean existeReg(Connection conn) throws SQLException{
 		
 		PreparedStatement ps	= null;
 		ResultSet 		rs		= null;
 		boolean 		ok 		= false;
 		
 		try{
 			ps = conn.prepareStatement("SELECT IMAGEN FROM ARCH_GENERAL"+
 				" WHERE MATRICULA = ? " +
 				" AND FOLIO = TO_NUMBER(?,'999')");
 			ps.setString(1, matricula);
 			ps.setString(2, folio); 			
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				ok = true;
 			else
 				ok = false;
 			
 		}catch(Exception ex){ 			
 			System.out.println("Error - aca.pg.archivo.ArchGeneral|existeReg|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}
*/	
	public int numImagenes(String matricula){
 		int numImagen			= 0;
 		try{
 			String comando = "SELECT COUNT(IMAGEN) AS NUMIMAGEN FROM ARCH_GENERAL"+
 				" WHERE MATRICULA = ? " + 				
 				" AND IMAGEN != 0";
 			
			if (archivoJdbc.queryForObject(comando, Integer.class, matricula) >= 1) {		
				numImagen = archivoJdbc.queryForObject(comando, Integer.class, matricula);
			}
 			
 		}catch(Exception ex){ 			
			System.out.println("Error - aca.pg.archivo.ArchGeneral|numImagenes|:"+ex);
		}
		return numImagen;
 	}
	
	public String maximoReg(String matricula){
		int maximo 			= 1;		
		try{
			String comando = "SELECT COALESCE(MAX(FOLIO)+1,1) AS MAXIMO FROM ARCH_GENERAL WHERE MATRICULA = ?";	
			maximo = archivoJdbc.queryForObject(comando, Integer.class, matricula);
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.ArchGeneral|maximoReg|:"+ex);
		}
		
		return String.valueOf(maximo);
	}
	
	public boolean existeImagenReg(String matricula, String folio){
		boolean existe	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ARCH_GENERAL WHERE MATRICULA = ? AND FOLIO = TO_NUMBER(?,'999') AND IMAGEN = 0 AND IMAGEN_BYTE IS NOT NULL";	
			if (archivoJdbc.queryForObject(comando, Integer.class, matricula, folio) >= 1) {
				existe = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.ArchGeneral|existeImagenReg|:"+ex);
		}
		
		return existe;
	}
	
	public List<PosArchGeneral> lisAlumnos(String codigoAlumno, String orden){				
		List<PosArchGeneral> lista			= new ArrayList<PosArchGeneral>();		
		try{
			String comando = "SELECT MATRICULA, FOLIO, FECHA, USUARIO, IMAGEN"
				+ " FROM ARCH_GENERAL"
				+ " WHERE MATRICULA = ? "+orden;
			Object[] parametros = new Object[] {codigoAlumno};
			lista = archivoJdbc.query(comando, new PosArchGeneralMapper(), parametros);
		}catch(Exception e){
			System.out.println("Error - aca.pg.archivos.spring.PosArchGeneralDao|lisAlumnos|:"+e);
		}		
		return lista;
	}
	
	public List<String> lisAlumnosPendientes(String orden){		
		
		List<String> lista		= new ArrayList<String>();		
		try{
			String comando = "SELECT DISTINCT(MATRICULA) FROM ARCH_GENERAL "+orden;			
			lista = archivoJdbc.queryForList(comando, String.class);
		}
		catch(Exception e){
			System.out.println("Error - aca.pg.archivos.spring.PosArchGeneralDao|lisAlumnosPendientes|:"+e);
		}
		
		return lista;
	}	
	
	public HashMap<String, String> mapaNumImagenes() {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT MATRICULA AS LLAVE, COUNT(MATRICULA) AS VALOR FROM ARCH_GENERAL GROUP BY MATRICULA";		
			lista = archivoJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.spring.PosArchGeneralDao|mapaNumImagenes|:"+ex);
		}
		
		return mapa;
	}
}