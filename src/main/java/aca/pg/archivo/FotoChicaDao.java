package aca.pg.archivo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FotoChicaDao {
	
	@Autowired
	@Qualifier("jdbcArchivo")
	private JdbcTemplate archivoJdbc;
	
	// Insert con JDBC Template
	public boolean insertReg( FotoChica foto) throws Exception{
		
		boolean ok = false;
		try{
			String comando = "INSERT INTO FOTO_CHICA (CODIGO_PERSONAL, FOTO, FECHA)"
					+" VALUES(?, ?, TO_DATE(?,'DD/MM/YYYY'))";
			Object[] parametros = new Object[] {foto.getCodigoPersonal(),foto.getFoto(), foto.getFecha()};
			if (archivoJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch( Exception ex){
			System.out.println("Error-aca.pg.archivo.FotoChicaDao||insertRegByte:"+ex);
		}
		
		return ok;		
	}
	
	// Update con JDBC Template
	public boolean updateReg( FotoChica foto) throws Exception{
		
		boolean ok = false;
		try{
			String comando = "UPDATE FOTO_CHICA SET FOTO = ?, FECHA = TO_DATE(?,'DD/MM/YYYY') WHERE CODIGO_PERSONAL = ? ";
			Object[] parametros = new Object[] {foto.getFoto(),foto.getFecha(), foto.getCodigoPersonal()};
			if (archivoJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch( Exception ex){
			System.out.println("Error-aca.pg.archivo.FotoChicaDao||updateReg:"+ex);
		}
		
		return ok;		
	}
	
	// Delete con JDBC Template
	public boolean deleteReg( String codigoPersonal) throws Exception{		
		boolean ok = false;
		try{
			String comando = "DELETE FROM FOTO_CHICA WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			if (archivoJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch( Exception ex){
			System.out.println("Error-aca.pg.archivo.FotoChicaDao||deleteReg:"+ex);
		}
		
		return ok;		
	}	
	
	public FotoChica mapeaRegId(String codigoPersonal){
		FotoChica foto = new FotoChica();
		
		try{			
			String query = "SELECT CODIGO_PERSONAL, FOTO, FECHA FROM FOTO_CHICA WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[]{codigoPersonal};
			foto = archivoJdbc.queryForObject(query, new FotoChicaMapper(), parametros);
		}catch( Exception ex){
			System.out.println("Error:aca.pg.archivo.FotoChicaDao|mapeaRegId|:"+ex);
		}
		
		return foto;
	}
	
	public boolean existeReg(String codigoPersonal){
		boolean ok = false;
		try{
			String query = "SELECT COUNT(CODIGO_PERSONAL) FROM FOTO_CHICA WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[]{codigoPersonal};
			if (archivoJdbc.queryForObject(query, Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.FotoChicaDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String getFoto(String codigoPersonal){
		
		FotoChica alumFoto		= new FotoChica();
		byte[] fotoByte			= null;
		String fotoString		= "";
		
		try{
			String query = "SELECT COUNT(CODIGO_PERSONAL) FROM FOTO_CHICA WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[]{codigoPersonal};
			if (archivoJdbc.queryForObject(query, Integer.class, parametros) >= 1){
				
				// Busca la foto 
				query = "SELECT CODIGO_PERSONAL, FOTO, FECHA FROM FOTO_CHICA WHERE CODIGO_PERSONAL = ?";				
				alumFoto = archivoJdbc.queryForObject(query, new FotoChicaMapper(), parametros);
				fotoByte = alumFoto.getFoto();				
			}else {
				// Busca la foto 
				query = "SELECT CODIGO_PERSONAL, FOTO, FECHA FROM FOTO_CHICA WHERE CODIGO_PERSONAL = 'nofoto'";				
				alumFoto = archivoJdbc.queryForObject(query, new FotoChicaMapper());				
				fotoByte = alumFoto.getFoto();
			}
			if (fotoByte!=null) {
				fotoString = java.util.Base64.getEncoder().encodeToString(fotoByte);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.FotoChicaDao|getFoto|:"+ex);
		}
		
		return fotoString;
	}
	
	public byte[] getFotoByte(String codigoPersonal ){
		
		FotoChica alumFoto		= new FotoChica();
		byte[] fotoByte			= null;		
		try{
			String query = "SELECT COUNT(CODIGO_PERSONAL) FROM FOTO_CHICA WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[]{codigoPersonal};
			if (archivoJdbc.queryForObject(query, Integer.class, parametros) >= 1){				
				// Busca la foto 
				query = "SELECT CODIGO_PERSONAL, FOTO, FECHA FROM FOTO_CHICA WHERE CODIGO_PERSONAL = ?";				
				alumFoto = archivoJdbc.queryForObject(query, new FotoChicaMapper(), parametros);				
				fotoByte = alumFoto.getFoto();				
			}else {
				// Busca la foto 
				query = "SELECT CODIGO_PERSONAL, FOTO, FECHA FROM FOTO_CHICA WHERE CODIGO_PERSONAL = 'nofoto'";				
				alumFoto = archivoJdbc.queryForObject(query, new FotoChicaMapper());
				fotoByte = alumFoto.getFoto();
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.FotoChicaDao|getFotoByte|:"+ex);
		}		
		return fotoByte;
	}
	
	public List<String> lisConFoto(){
		List<String> lista = new ArrayList<String>();		
		try{
			String query = "SELECT CODIGO_PERSONAL FROM FOTO_CHICA WHERE FOTO IS NOT NULL";			
			lista = archivoJdbc.queryForList(query, String.class);				
			
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.FotoChicaDao|getFoto|:"+ex);
		}		
		return lista;
	}	
	
}
