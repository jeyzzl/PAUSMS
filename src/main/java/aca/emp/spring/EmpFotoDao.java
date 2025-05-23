package aca.emp.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class EmpFotoDao {
	
	@Autowired
	@Qualifier("jdbcArchivo")
	private JdbcTemplate archivoJdbc;
	
	// Insert con JDBC Template
		public boolean insertReg(EmpFoto foto) throws Exception{
			
			boolean ok = false;
			try{
				String comando = "INSERT INTO EMP_FOTO (CODIGO_PERSONAL, FOLIO, FECHA, USUARIO, FOTO)"
						+" VALUES(?, TO_NUMBER(?,'99'), TO_DATE(?,'DD/MM/YYYY'), ?, ?)";
				Object[] parametros = new Object[] {foto.getCodigoPersonal(),foto.getFolio(), foto.getFecha(), foto.getUsuario(), foto.getFoto()};
				if (archivoJdbc.update(comando,parametros)==1){
					ok = true;
				}
			}catch( Exception ex){
				System.out.println("Error-aca.emp.spring.EmpFotoDao||insertRegByte:"+ex);
			}
			
			return ok;		
		}
		
		// Update con JDBC Template
		public boolean updateReg(EmpFoto foto) throws Exception{
			
			boolean ok = false;
			try{
				String comando = "UPDATE EMP_FOTO SET FOTO = ?, FECHA = TO_DATE(?,'DD/MM/YYYY'), USUARIO = ? WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'99') ";
				Object[] parametros = new Object[] {foto.getFoto(),foto.getFecha(), foto.getUsuario(), foto.getCodigoPersonal(), foto.getFolio() };
				if (archivoJdbc.update(comando,parametros)==1){
					ok = true;
				}
			}catch( Exception ex){
				System.out.println("Error-aca.emp.spring.EmpFotoDao||updateReg:"+ex);
			}
			
			return ok;		
		}
		
		// Delete con JDBC Template
		public boolean deleteReg(String codigoPersonal, String folio) throws Exception{		
			boolean ok = false;
			try{
				String comando = "DELETE FROM EMP_FOTO WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'99')";
				Object[] parametros = new Object[] {codigoPersonal, folio};
				if (archivoJdbc.update(comando,parametros)==1){
					ok = true;
				}
			}catch( Exception ex){
				System.out.println("Error-aca.emp.spring.EmpFotoDao||deleteReg:"+ex);
			}
			
			return ok;		
		}	
		
		public EmpFoto mapeaRegId(String codigoPersonal, String folio){
			EmpFoto foto = new EmpFoto();			
			try{			
				String query = "SELECT COUNT(CODIGO_PERSONAL) FROM EMP_FOTO WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'99')";
				Object[] parametros = new Object[]{codigoPersonal, folio};
				if (archivoJdbc.queryForObject(query, Integer.class, parametros) >= 1){
					query = "SELECT CODIGO_PERSONAL, FOLIO, FECHA, USUARIO, FOTO FROM EMP_FOTO WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'99')";					
					foto = archivoJdbc.queryForObject(query, new EmpFotoMapper(), parametros);
				}			
			}catch( Exception ex){
				System.out.println("Error:aca.emp.spring.EmpFotoDao|mapeaRegId|:"+ex);
			}			
			return foto;
		}
		
		public boolean existeReg(String codigoPersonal, String folio){
			boolean ok = false;
			try{
				String query = "SELECT COUNT(CODIGO_PERSONAL) FROM EMP_FOTO WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'99')";
				Object[] parametros = new Object[]{codigoPersonal, folio};
				if (archivoJdbc.queryForObject(query, Integer.class, parametros) >= 1){
					ok = true;
				}			
			}catch(Exception ex){
				System.out.println("Error - aca.emp.spring.EmpFotoDao|existeReg|:"+ex);
			}			
			return ok;
		}
		
		public String getFoto(String codigoPersonal, String folio){
			
			EmpFoto alumFoto		= new EmpFoto();
			byte[] fotoByte			= null;
			String fotoString		= "";
			
			try{
				String query = "SELECT COUNT(CODIGO_PERSONAL) FROM EMP_FOTO WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'99')";
				Object[] parametros = new Object[]{codigoPersonal, folio};
				if (archivoJdbc.queryForObject(query, Integer.class, parametros) >= 1){
					
					// Busca la foto 
					query = "SELECT CODIGO_PERSONAL, FOLIO, FECHA, USUARIO, FOTO FROM EMP_FOTO WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'99')";				
					alumFoto = archivoJdbc.queryForObject(query, new EmpFotoMapper(), parametros);				
					fotoByte = alumFoto.getFoto();				
				}else {
					// Busca la foto 
					query = "SELECT CODIGO_PERSONAL, FOLIO, FECHA, USUARIO, FOTO FROM EMP_FOTO WHERE CODIGO_PERSONAL = 'nofoto'";				
					alumFoto = archivoJdbc.queryForObject(query, new EmpFotoMapper());				
					fotoByte = alumFoto.getFoto();
				}
				if (fotoByte!=null) {
					fotoString = java.util.Base64.getEncoder().encodeToString(fotoByte);
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.emp.spring.EmpFotoDao|getFoto|:"+ex);
			}
			
			return fotoString;
		}
		
		public byte[] getFotoByte(String codigoPersonal, String folio){
			
			EmpFoto alumFoto		= new EmpFoto();
			byte[] fotoByte			= null;	
			
			try{
				String query = "SELECT COUNT(CODIGO_PERSONAL) FROM EMP_FOTO WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'99')";
				Object[] parametros = new Object[]{codigoPersonal, folio};
				if (archivoJdbc.queryForObject(query, Integer.class, parametros) >= 1){
					
					// Busca la foto 
					query = "SELECT CODIGO_PERSONAL, FOLIO, FECHA, USUARIO, FOTO FROM EMP_FOTO WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'99')";				
					alumFoto = archivoJdbc.queryForObject(query, new EmpFotoMapper(), parametros);				
					fotoByte = alumFoto.getFoto();				
				}else {
					// Busca la foto 
					query = "SELECT CODIGO_PERSONAL, FOLIO, FECHA, USUARIO, FOTO FROM EMP_FOTO WHERE CODIGO_PERSONAL = 'nofoto'";				
					alumFoto = archivoJdbc.queryForObject(query, new EmpFotoMapper());				
					fotoByte = alumFoto.getFoto();
				}		
				
			}catch(Exception ex){
				System.out.println("Error - aca.emp.spring.EmpFotoDao|getFotoByte|:"+ex);
			}
			
			return fotoByte;
		}
		
}
