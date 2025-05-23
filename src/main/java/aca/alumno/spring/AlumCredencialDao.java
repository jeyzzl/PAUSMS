// Clase para la tabla de Modulo
package aca.alumno.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AlumCredencialDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( AlumCredencial alumCredencial ) {
		boolean ok 				= false;
		
		try{
			String comando = "INSERT INTO ENOC.ALUM_CREDENCIAL"+ 
				"(CODIGO_PERSONAL, NOMBRES, APELLIDOS, CARRERA, FECHA, COTEJADO, PERIODO1, PERIODO2, PERIODO3) "+
				"VALUES( ?, ?, ?, UPPER(?)," +
				"TO_DATE( TO_CHAR(now(),'DD-MM-YYYY')||TO_CHAR(now(),'HH:MI:SS AM'),'DD-MM-YYYY HH:MI:SS AM'), ?, ?, ?, ?)";
			Object[] parametros = new Object[] {alumCredencial.getCodigoPersonal(), alumCredencial.getNombres(),
					alumCredencial.getApellidos(), alumCredencial.getCarrera(),	alumCredencial.getCotejado(),
					alumCredencial.getPeriodo1(), alumCredencial.getPeriodo2(), alumCredencial.getPeriodo3()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumCredencialDao|insertReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateReg( AlumCredencial alumCredencial ) {
		boolean ok 				= false;
		
		try{
			String comando = "UPDATE ENOC.ALUM_CREDENCIAL "+ 
				" SET NOMBRES = ?,"+
				" APELLIDOS = ?,"+
				" CARRERA = UPPER(?),"+
				" FECHA = TO_DATE( TO_CHAR(now(),'DD-MM-YYYY')||TO_CHAR(now(),'HH:MI:SS AM'),'DD-MM-YYYY HH:MI:SS AM'),"+
				" COTEJADO = ?,"+
				" PERIODO1 = ?,"+
				" PERIODO2 = ?,"+
				" PERIODO3 = ?"+
				" WHERE CODIGO_PERSONAL = ? ";
			Object[] parametros = new Object[] {alumCredencial.getNombres(), alumCredencial.getApellidos(),
			alumCredencial.getCarrera(), alumCredencial.getCotejado(), alumCredencial.getPeriodo1(),
			alumCredencial.getPeriodo2(), alumCredencial.getPeriodo3(), alumCredencial.getCodigoPersonal()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumCredencialDao|updateReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateFoto( byte[] foto, String codigoPersonal ) {
		boolean ok 				= false;
		
		try{
			String comando = "UPDATE ENOC.ALUM_CREDENCIAL "+ 
				"SET FOTO = ? "+				
				"WHERE CODIGO_PERSONAL = ? ";
			Object[] parametros = new Object[] {foto, codigoPersonal};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumCredencialDao|updateFoto|:"+ex);		
		}
		
		return ok;
	}
	
	/*
	 * Actualiza la foto del alumno, tomandola del directorio WEB_INF/fotos en el servidor de aplicaciones 
	 * */
//	public boolean updateFoto( String dirFoto ) {
//		PreparedStatement ps 	= null;
//		boolean ok 				= false;
//		byte[] foto				= null;
//		try{
//			java.io.File f = new java.io.File(dirFoto);
//			if(f.exists()){
//				foto = new byte[(int)f.length()];
//			}
//			
//			String comando = ("UPDATE ENOC.ALUM_CREDENCIAL "+ 
//				"SET FOTO = ? "+				
//				"WHERE CODIGO_PERSONAL = ? ");
//			ps.setBytes(1, foto);
//			ps.setString(2, codigoPersonal);
//			
//			if (ps.executeUpdate()== 1)
//				ok = true;	
//			else
//				ok = false;	
//			
//		}catch(Exception ex){
//			System.out.println("Error - aca.alumno.AlumCredencialUtil|updateFoto|:"+ex);		
//		}finally{
//			try { ps.close(); } catch (Exception ignore) { }
//			foto=null;
//		}
//		return ok;
//	}
	
	public boolean deleteReg( String codigoPersonal ) {
		boolean ok 				= false;
		
		try{
			String comando = "DELETE FROM ENOC.ALUM_CREDENCIAL "+ 
				"WHERE CODIGO_PERSONAL = ? ";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumCredencialDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public AlumCredencial mapeaRegId(  String codigoPersonal, String folio ) {
		
		AlumCredencial objeto = new AlumCredencial();
		
		try{
			String comando = "SELECT"+
				" CODIGO_PERSONAL, NOMBRES, CARRERA, COTEJADO, PERIODO1, PERIODO2, PERIODO3"+
				" FROM ENOC.ALUM_CREDENCIAL"+ 
				" WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			objeto = enocJdbc.queryForObject(comando, new AlumCredencialMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumCredencialDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}

	public boolean existeReg( String codigoPersonal) {
		boolean 		ok 		= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_CREDENCIAL "+
				"WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumCredencialDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	
	public boolean existeFotoArchivo(String dirFoto) {
		boolean ok = false;
		
		try{
			java.io.File f = new java.io.File(dirFoto);		
			if(f.exists()){
				ok	= true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumCredencialDao|existeFotoArchivo|:"+ex);
		}		
		return ok;
	}	
	
	public String getFechaActualizacion( String codigoPersonal) {
		String actualizado 		= "01/01/2000";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_CREDENCIAL WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1) {
				comando = "SELECT COALESCE(TO_CHAR(FECHA,'DD/MM/YYYY HH:MI:SS AM'),'01/01/2000') FROM ENOC.ALUM_CREDENCIAL WHERE CODIGO_PERSONAL = ?";
				actualizado = enocJdbc.queryForObject(comando,String.class, parametros);
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumCredencialDao|getFechaActualizacion|:"+ex);
		}		
		return actualizado;
	}
	
	public List<AlumCredencial> getListAll( String orden ) {
		
		List<AlumCredencial> lista	= new ArrayList<AlumCredencial>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRES, APELLIDOS, CARRERA, COTEJADO, PERIODO1, PERIODO2, PERIODO3 FROM ENOC.ALUM_CREDENCIAL "+orden; 
			lista = enocJdbc.query(comando, new AlumCredencialMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumCredencialDao|getListAll|:"+ex);
		}
		
		return lista;
	}	
}