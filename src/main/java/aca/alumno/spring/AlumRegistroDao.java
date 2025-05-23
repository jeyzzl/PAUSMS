package aca.alumno.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AlumRegistroDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( AlumRegistro objeto ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.ALUM_REGISTRO"
					+ " (ID, NOMBRE, PATERNO, MATERNO, CORREO, TELEFONO, CODIGO, FECHA, PLAN_ID) "
					+ " VALUES(TO_NUMBER(?,'99999'), ?, ?, ?, ?, ?, ?, NOW(), ?)";
			Object[] parametros = new Object[] {
				objeto.getId(),objeto.getNombre(),objeto.getPaterno(),objeto.getMaterno(), objeto.getCorreo(), objeto.getTelefono(),objeto.getCodigo(),objeto.getPlanId()
			};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumRegistroDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	

	public boolean updateReg( AlumRegistro objeto ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.ALUM_REGISTRO"+ 
				" SET "+
				" NOMBRE = ?,"+
				" PATERNO = ?,"+
				" MATERNO = ?,"+
				" CORREO = ?,"+
				" TELEFONO = ?,"+
				" CODIGO = ?,"+
				" FECHA = TO_DATE(?,'DD/MM/YYYY'),"+
				" PLAN_ID = ?"+
				" WHERE ID = TO_NUMBER(?,'99999')";
			Object[] parametros = new Object[] {
				objeto.getNombre(),objeto.getPaterno(),objeto.getMaterno(),objeto.getCorreo(),objeto.getTelefono(),objeto.getCodigo(),objeto.getPlanId(),objeto.getId()
			};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumRegistroDao|updateReg|:"+ex);		
		}
		
		return ok;
	}	
		
	public boolean deleteReg(String id) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.ALUM_REGISTRO WHERE ID = TO_NUMBER(?,'99999')";
			Object[] parametros = new Object[] {id};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumRegistroDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public AlumRegistro mapeaRegId(String id) {
		
		AlumRegistro objeto = new AlumRegistro();
		
		try{
			String comando = "SELECT ID, NOMBRE, PATERNO, MATERNO, CORREO, TELEFONO, CODIGO, FECHA, PLAN_ID "
					+ " FROM ENOC.ALUM_REGISTRO WHERE ID = TO_NUMBER(?,'99999')";
			Object[] parametros = new Object[] {id};
			objeto = enocJdbc.queryForObject(comando, new AlumRegistroMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumRegistroDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public AlumRegistro mapeaRegPorCorreoyCodigo(String correo, String codigo) {
		
		AlumRegistro objeto = new AlumRegistro();
		
		try{
			String comando = "SELECT ID, NOMBRE, PATERNO, MATERNO, CORREO, TELEFONO, CODIGO, FECHA, PLAN_ID "
					+ " FROM ENOC.ALUM_REGISTRO WHERE CORREO = ? AND CODIGO = ?";
			Object[] parametros = new Object[] {correo, codigo};
			objeto = enocJdbc.queryForObject(comando, new AlumRegistroMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumRegistroDao|mapeaRegPorCorreoyCodigo|:"+ex);
		}
		
		return objeto;
	}
	
	public boolean existeReg(String id) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_REGISTRO WHERE ID = TO_NUMBER(?,'99999')";
			Object[] parametros = new Object[] {id};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumRegistroDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean existeRegPorCorreoyCodigo(String correo, String codigo) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_REGISTRO WHERE CORREO = ? AND CODIGO = ?";
			Object[] parametros = new Object[] {correo, codigo};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumRegistroDao|existeRegPorCorreoyCodigo|:"+ex);
		}
		
		return ok;
	}
	
	public boolean existeCuenta(String correo, String codigo) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_REGISTRO WHERE CORREO = ? AND CODIGO = ?";
			Object[] parametros = new Object[] {correo, codigo};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumRegistroDao|existeCuenta|:"+ex);
		}
		
		return ok;
	}

	public boolean existeCorreo(String correo) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_REGISTRO WHERE CORREO = ?";
			Object[] parametros = new Object[] {correo};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumRegistroDao|existeCorreo|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg(){
		String maximo = "1";
		
		try{
			String comando = "SELECT COALESCE((MAX(TO_NUMBER(ID,'99999')+1)),1) AS MAXIMO FROM ENOC.ALUM_REGISTRO";
			Object[] parametros = new Object[] {};
 			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
 				maximo = enocJdbc.queryForObject(comando,String.class, parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumRegistroDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}	
	
	public List<AlumRegistro> getListAll( String orden ) {
		List<AlumRegistro> lista = new ArrayList<AlumRegistro>();
		
		try{
			String comando = "SELECT ID, NOMBRE, PATERNO, MATERNO, CORREO, TELEFONO, CODIGO, TO_CHAR (FECHA, 'DD/MM/YYYY') AS FECHA, PLAN_ID"
					+ " FROM ENOC.ALUM_REGISTRO "+ orden;
			lista = enocJdbc.query(comando, new AlumRegistroMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumRegistroDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
}
