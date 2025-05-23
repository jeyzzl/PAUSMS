package aca.musica.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MusiPadresDao {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(MusiPadres musiPadres) {
 		boolean ok = false;
 		
 		try{
 			String comando = "INSERT INTO ENOC.MUSI_PADRES"+ 
 				"(CODIGO_ID, PAD_NOMBRE, PAD_PATERNO, PAD_MATERNO, PAD_DIRECCION, PAD_CORREO, PAD_OCUPACION, PAD_TELCASA, PAD_TELTRABAJO, PAD_TELCELULAR, "+
 						   " MAD_NOMBRE, MAD_PATERNO, MAD_MATERNO, MAD_OCUPACION, MAD_DIRECCION, MAD_CORREO, MAD_TELCASA, MAD_TELTRABAJO, MAD_TELCELULAR, " +
 						   " PAD_VIVE, MAD_VIVE, CODIGO_USUARIO, PAD_RELIGION_ID, MAD_RELIGION_ID ) "+
 				"VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, TO_NUMBER(?,'99'), TO_NUMBER(?,'99'))";
 			
 			Object[] parametros = new Object[] {
	 			musiPadres.getCodigoId(),musiPadres.getPadNombre(),musiPadres.getPadPaterno(),musiPadres.getPadMaterno(),musiPadres.getPadDireccion(),
	 			musiPadres.getPadCorreo(),musiPadres.getPadOcupacion(),musiPadres.getPadTelcasa(),musiPadres.getPadTeltrabajo(),musiPadres.getPadTelcelular(),
	 			musiPadres.getMadNombre(),musiPadres.getMadPaterno(),musiPadres.getMadMaterno(),musiPadres.getMadOcupacion(),musiPadres.getMadDireccion(),
	 			musiPadres.getMadCorreo(),musiPadres.getMadTelcasa(),musiPadres.getMadTeltrabajo(),musiPadres.getMadTelcelular(),musiPadres.getPadVive(),
	 			musiPadres.getMadVive(),musiPadres.getCodigoUsuario(),musiPadres.getPadReligionId(),musiPadres.getMadReligionId()
 			};
 			
 			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.spring.MusiPadresDao|insertReg|:"+ex);			
 		}
 		
 		return ok;
 	}	
 	
 	public boolean updateReg(MusiPadres musiPadres) { 		
 		boolean ok = false;
 		
 		try{
 			String comando = "UPDATE ENOC.MUSI_PADRES "+			 
 				" SET PAD_NOMBRE 	= ?,"+
 				" PAD_PATERNO 		= ?,"+
 				" PAD_MATERNO 		= ?,"+
 				" PAD_DIRECCION 	= ?,"+
 				" PAD_CORREO 		= ?,"+
 				" PAD_OCUPACION 	= ?,"+
 				" PAD_TELCASA 		= ?,"+
 				" PAD_TELTRABAJO 	= ?,"+
 				" PAD_TELCELULAR	= ?,"+
 				" MAD_NOMBRE	 	= ?,"+
 				" MAD_PATERNO 		= ?,"+
 				" MAD_MATERNO 		= ?," +
 				" MAD_OCUPACION		= ?," + 
 				" MAD_DIRECCION 	= ?," + 
 				" MAD_CORREO 		= ?," + 
 				" MAD_TELCASA 		= ?," + 
 				" MAD_TELTRABAJO 	= ?," + 
 				" MAD_TELCELULAR 	= ?," + 
 				" PAD_VIVE 			= ?," + 
 				" MAD_VIVE 			= ?," + 
 				" CODIGO_USUARIO	= ?," + 
 				" PAD_RELIGION_ID	= TO_NUMBER(?,'99')," + 
 				" MAD_RELIGION_ID	= TO_NUMBER(?,'99')" + 
 				" WHERE CODIGO_ID 	= ?";

 			Object[] parametros = new Object[] {
	 			musiPadres.getPadNombre(),musiPadres.getPadPaterno(),musiPadres.getPadMaterno(),musiPadres.getPadDireccion(),
	 			musiPadres.getPadCorreo(),musiPadres.getPadOcupacion(),musiPadres.getPadTelcasa(),musiPadres.getPadTeltrabajo(),musiPadres.getPadTelcelular(),
	 			musiPadres.getMadNombre(),musiPadres.getMadPaterno(),musiPadres.getMadMaterno(),musiPadres.getMadOcupacion(),musiPadres.getMadDireccion(),
	 			musiPadres.getMadCorreo(),musiPadres.getMadTelcasa(),musiPadres.getMadTeltrabajo(),musiPadres.getMadTelcelular(),musiPadres.getPadVive(),
	 			musiPadres.getMadVive(),musiPadres.getCodigoUsuario(),musiPadres.getPadReligionId(),musiPadres.getMadReligionId(),musiPadres.getCodigoId()
 			};
 			
 			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.spring.MusiPadresDao|updateReg|:"+ex);
 		}
 		
 		return ok;
 	} 
 	
 	public boolean deleteReg(String codigoId) {
 		boolean ok = false;
 		
 		try{
 			String comando = "DELETE FROM ENOC.MUSI_PADRES "+ 
 				"WHERE CODIGO_ID = ? ";

 			Object[] parametros = new Object[] {codigoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
 		}catch(Exception ex){
 			System.out.println("Error - cimum.usuario.MusiPadresDao|deleteReg|:"+ex);			
 		}

 		return ok;
 	}
 	
 	public MusiPadres mapeaRegId(String codigoId){
 		MusiPadres objeto = new MusiPadres();
 		
 		try{
 			String comando = "SELECT CODIGO_ID, PAD_NOMBRE, PAD_PATERNO, PAD_MATERNO, PAD_DIRECCION, PAD_CORREO, PAD_OCUPACION, PAD_TELCASA, PAD_TELTRABAJO, PAD_TELCELULAR, "+
 						   " MAD_NOMBRE, MAD_PATERNO, MAD_MATERNO, MAD_OCUPACION, MAD_DIRECCION, MAD_CORREO, MAD_TELCASA, MAD_TELTRABAJO, MAD_TELCELULAR, " +
 						   " PAD_VIVE, MAD_VIVE, CODIGO_USUARIO, PAD_RELIGION_ID, MAD_RELIGION_ID "+
 						   " FROM ENOC.MUSI_PADRES WHERE CODIGO_ID = ? "; 
	 	
	 		Object[] parametros = new Object[] {codigoId};
			objeto = enocJdbc.queryForObject(comando, new MusiPadresMapper(), parametros);
		
 		}catch(Exception ex){
			System.out.println("Error - aca.musica.spring.MusiPadresDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}

 		return objeto;
 	}

 	public boolean existeReg(String codigoId) {
 		boolean ok 	= false;
 		
 		try{
 			String comando = "SELECT COUNT(*) FROM ENOC.MUSI_PADRES WHERE CODIGO_ID = ?";
 			
 			Object[] parametros = new Object[] {codigoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.spring.MusiPadresDao|existeReg|:"+ex);
 		}
 		
 		return ok;
 	} 
 	
 	public List<MusiPadres> getListAll() {
		List<MusiPadres> lista	= new ArrayList<MusiPadres>();
		
		try{
			String comando = "SELECT CODIGO_ID, PAD_NOMBRE, PAD_PATERNO, PAD_MATERNO, PAD_DIRECCION, PAD_CORREO,"
					+ " PAD_OCUPACION, PAD_TELCASA, PAD_TELTRABAJO, PAD_TELCELULAR, MAD_NOMBRE, MAD_PATERNO, MAD_MATERNO, "
					+ " MAD_OCUPACION, MAD_DIRECCION, MAD_CORREO, MAD_TELCASA, MAD_TELTRABAJO, MAD_TELCELULAR,"
					+ " PAD_VIVE, MAD_VIVE, CODIGO_USUARIO, PAD_RELIGION_ID, MAD_RELIGION_ID"
					+ " FROM ENOC.MUSI_PADRES";
			
			lista = enocJdbc.query(comando, new MusiPadresMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.spring.MusiPadresDao|getListAll|:"+ex);
		}
		
		return lista;
	}
 	
 	public HashMap<String, MusiPadres> mapaPadres(){
		List<MusiPadres> lista 			= new ArrayList<MusiPadres>();		
		HashMap<String, MusiPadres> mapa	= new HashMap<String,MusiPadres>();		
		try{
			String comando = "SELECT CODIGO_ID, PAD_NOMBRE, PAD_PATERNO, PAD_MATERNO, PAD_DIRECCION, PAD_CORREO,"
					+ " PAD_OCUPACION, PAD_TELCASA, PAD_TELTRABAJO, PAD_TELCELULAR, MAD_NOMBRE, MAD_PATERNO, MAD_MATERNO, "
					+ " MAD_OCUPACION, MAD_DIRECCION, MAD_CORREO, MAD_TELCASA, MAD_TELTRABAJO, MAD_TELCELULAR,"
					+ " PAD_VIVE, MAD_VIVE, CODIGO_USUARIO, PAD_RELIGION_ID, MAD_RELIGION_ID"
					+ " FROM ENOC.MUSI_PADRES";					
			lista = enocJdbc.query(comando, new MusiPadresMapper());
			for ( MusiPadres padres : lista) {
				mapa.put(padres.getCodigoId(), padres );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.spring.MusiPadresDao|mapaPadres|:"+ex);
		}
		
		return mapa;
	}

}
