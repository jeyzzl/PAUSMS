package aca.alumno.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AlumDireccionDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( AlumDireccion alumDireccion ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.ALUM_DIRECCION"+ 
				"(CODIGO_PERSONAL, CALLE, COLONIA, COD_POSTAL, APARTADO, " +
				"PAIS_ID, ESTADO_ID, CIUDAD_ID, " +
				"FECHA, ESTADO, TELEFONO, NUMERO) "+
				"VALUES( ?, ?, ?, ?,?," +
				"TO_NUMBER(?,'999'), "+
				"TO_NUMBER(?,'999'), "+
				"TO_NUMBER(?,'999'), "+
				"TO_DATE(?,'DD/MM/YYYY'), ?, ?, ? ) ";
			Object[] parametros = new Object[] {alumDireccion.getCodigoPersonal(), alumDireccion.getCalle(),
					alumDireccion.getColonia(), alumDireccion.getCodPostal(), alumDireccion.getApartado(),
					alumDireccion.getPaisId(), alumDireccion.getEstadoId(), alumDireccion.getCiudadId(),
					alumDireccion.getFecha(), alumDireccion.getEstado(), alumDireccion.getTelefono(), alumDireccion.getNumero()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumDireccionDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	

	public boolean updateReg( AlumDireccion alumDireccion ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.ALUM_DIRECCION"+ 
				" SET "+
				" CALLE = ?,"+
				" COLONIA = ?,"+
				" COD_POSTAL = ?,"+
				" APARTADO = ?,"+
				" PAIS_ID = TO_NUMBER(?,'999'),"+
				" ESTADO_ID = TO_NUMBER(?,'999'),"+
				" CIUDAD_ID = TO_NUMBER(?,'999'),"+
				" FECHA = TO_DATE(?,'DD/MM/YYYY')," +
				" ESTADO = ? ," +
				" TELEFONO = ?, " +
				" NUMERO = ? "+
				" WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {alumDireccion.getCalle(),alumDireccion.getColonia(), alumDireccion.getCodPostal(),
					alumDireccion.getApartado(), alumDireccion.getPaisId(), alumDireccion.getEstadoId(), alumDireccion.getCiudadId(), alumDireccion.getFecha(),
					alumDireccion.getEstado(), alumDireccion.getTelefono(), alumDireccion.getNumero(),alumDireccion.getCodigoPersonal()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumDireccionDao|updateReg|:"+ex);		
		}
		
		return ok;
	}	
		

	public boolean deleteReg( String codigoPersonal ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.ALUM_DIRECCION "+ 
				"WHERE CODIGO_PERSONAL = ? ";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumDireccionDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public AlumDireccion mapeaRegId(  String codigoPersonal ) {
		
		AlumDireccion objeto = new AlumDireccion();
		
		try{
			String comando = "SELECT"+
				" CODIGO_PERSONAL, "+
				" CALLE, COLONIA, COD_POSTAL, APARTADO, " +
				" PAIS_ID, ESTADO_ID, CIUDAD_ID, " +
				" TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, ESTADO, TELEFONO, NUMERO " +
				" FROM ENOC.ALUM_DIRECCION "+ 
				" WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			objeto = enocJdbc.queryForObject(comando, new AlumDireccionMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumDireccionDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public boolean existeReg( String codigoPersonal ) {
		boolean 			ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_DIRECCION "+ 
				"WHERE CODIGO_PERSONAL = ? ";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumDireccionDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public List<AlumDireccion> getListAll( String orden ) {
		
		List<AlumDireccion> lista = new ArrayList<AlumDireccion>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CALLE, COLONIA, " +
					" COD_POSTAL, APARATO, PAIS_ID, ESTADO_ID, CIUDAD_ID, TO_CHAR (FECHA, 'DD/MM/YYYY') AS FECHA," +
					" ESTADO, TELEFONO, NUMERO FROM ENOC.ALUM_DIRECCION "+ orden;
			lista = enocJdbc.query(comando, new AlumDireccionMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumDireccionDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public List<AlumDireccion> getLista( String codigoPersonal, String orden ) {
		
		List<AlumDireccion> lista = new ArrayList<AlumDireccion>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CALLE, COLONIA," +
					" COD_POSTAL, APARTADO, PAIS_ID, ESTADO_ID, CIUDAD_ID, TO_CHAR (FECHA, 'DD/MM/YYYY') AS FECHA, "+
					" ESTADO, TELEFONO, NUMERO FROM ENOC.ALUM_DIRECCION "+
					"WHERE CODIGO_PERSONAL = ? "+ orden;
			lista = enocJdbc.query(comando, new AlumDireccionMapper(), codigoPersonal);
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumDireccionDao|getLista|:"+ex);
		}
		
		return lista;
	}

}