//Clase para la tabla de Alum_Academico
package aca.alumno.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AlumHermanosDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AlumHermanos alumHermanos ){
		boolean ok = false;
	
		try{
			String comando = "INSERT INTO ENOC.ALUM_HERMANOS"+ 
				"(FAMILIA_ID, CODIGO_PERSONAL, ESTADO)"+
				" VALUES( ?, ?, ?)";
			Object[] parametros = new Object[] {alumHermanos.getFamiliaId(),alumHermanos.getCodigoPersonal(),alumHermanos.getEstado()};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumHermanosUtil|insertReg|:"+ex);			

		}
		return ok;
	}	
	
	public boolean updateReg(AlumHermanos alumHermanos ){
		boolean ok = false;	
		try{
			String comando = "UPDATE ENOC.ALUM_HERMANOS "+ 
				"SET"+
				" FECHA = now(),"+
				" ESTADO = ?"+
				" WHERE FAMILIA_ID = ? AND CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {alumHermanos.getEstado(),alumHermanos.getFamiliaId(), alumHermanos.getCodigoPersonal()};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumHermanosUtil|updateReg|:"+ex);		
	
		}
		return ok;
	}
	
	public boolean updateEstado(String matricula, String estado){
		boolean ok = false;	
		try{
			String comando = "UPDATE ENOC.ALUM_HERMANOS SET ESTADO = ? WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {estado,matricula};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumHermanosUtil|updateEstado|:"+ex);
		}
		return ok;
	}
	
	public boolean deleteReg(String familiaId, String codigoPersonal ){
		boolean ok = false;

		try{
			String comando = "DELETE FROM ENOC.ALUM_HERMANOS"+ 
				" WHERE FAMILIA_ID = TO_NUMBER(?,'99999') AND CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {familiaId, codigoPersonal};
 			if (enocJdbc.update(comando, parametros)==1){
 				ok = true;
 			} 	
	
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumHermanosUtil|deleteReg|:"+ex);			
		
		}
		return ok;
	}
	
	public AlumHermanos mapeaRegId(String familiaId, String codigoPersonal){
		
		AlumHermanos alumHermanos = new AlumHermanos();

		try{
			String comando = "SELECT"+
				" FAMILIA_ID, CODIGO_PERSONAL, TO_CHAR(FECHA, 'DD/MM/YYYY HH:MI AM') AS FECHA, ESTADO"+
				" FROM ENOC.ALUM_HERMANOS WHERE FAMILIA_ID = ? AND CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {familiaId, codigoPersonal};
			alumHermanos = enocJdbc.queryForObject(comando, new AlumHermanosMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumHermanosUtil|mapeaRegId|:"+ex);
	
		}
		return alumHermanos;
	}
	
	public AlumHermanos mapeaRegIdPorMatricula(String matricula){
		
		AlumHermanos alumHermanos = new AlumHermanos();

		try{
			String comando = "SELECT FAMILIA_ID, CODIGO_PERSONAL, TO_CHAR(FECHA, 'DD/MM/YYYY HH:MI AM') AS FECHA, ESTADO"
					+ " FROM ENOC.ALUM_HERMANOS"
					+ " WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {matricula};
			alumHermanos = enocJdbc.queryForObject(comando, new AlumHermanosMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumHermanosUtil|mapeaRegIdPorMatricula|:"+ex);
	
		}
		return alumHermanos;
	}
	
	public boolean existeReg(String familiaId, String codigoPersonal){

		boolean 		ok 		= false;
		
		try{
			String comando = "SELECT COUNT(FAMILIA_ID) FROM ENOC.ALUM_HERMANOS WHERE FAMILIA_ID = TO_NUMBER(?,'99999') AND CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {familiaId, codigoPersonal};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
 				ok = true;
 			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumHermanosUtil|existeReg|:"+ex);
	
		}
		return ok;
	}
	
	public boolean existeReg(String codigoPersonal){

		boolean 		ok 		= false;
		
		try{
			String comando = "SELECT COUNT(FAMILIA_ID) FROM ENOC.ALUM_HERMANOS WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
 				ok = true;
 			} 	
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumHermanosUtil|existeReg|:"+ex);
	
		}
		return ok;
	}
	
	public String getFamiliaId(String codigoPersonal){

		String familiaId = "0";
		
		try{
			String comando = "SELECT COUNT(FAMILIA_ID) FROM ENOC.ALUM_HERMANOS WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				comando = "SELECT FAMILIA_ID FROM ENOC.ALUM_HERMANOS WHERE CODIGO_PERSONAL = ?";
				familiaId = enocJdbc.queryForObject(comando, String.class, parametros);
 			} 	
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumHermanosUtil|getFamiliaId|:"+ex);
	
		}
		return familiaId;
	}
	
	public int getTotalHermanos(String familiaId){

		int hermanos = 0;
		
		try{
			String comando = "SELECT COUNT(FAMILIA_ID) FROM ENOC.ALUM_HERMANOS WHERE FAMILIA_ID = TO_NUMBER(?,'99999')";
			Object[] parametros = new Object[] {familiaId};
			hermanos = enocJdbc.queryForObject(comando, Integer.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumHermanosUtil|getFamiliaId|:"+ex);
	
		}
		return hermanos;
	}
		
	public ArrayList<AlumHermanos> getListAll(String orden ){
		
		List<AlumHermanos> lista= new ArrayList<AlumHermanos>();
		
		try{
			String comando = "SELECT FAMILIA_ID, CODIGO_PERSONAL, TO_CHAR(FECHA, 'DD/MM/YYYY HH:MI AM') AS FECHA, ESTADO"+
				" FROM ENOC.ALUM_HERMANOS "+orden; 
			lista = enocJdbc.query(comando, new AlumHermanosMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumHermanosUtil|getListAll|:"+ex);
		
		}				
		return (ArrayList<AlumHermanos>) lista;
	}
	
	public List<AlumHermanos> getListFamilia(String familiaId, String orden ){
		
		List<AlumHermanos> lista= new ArrayList<AlumHermanos>();
		
		try{
			String comando = "SELECT FAMILIA_ID, CODIGO_PERSONAL, TO_CHAR(FECHA, 'DD/MM/YYYY HH:MI AM') AS FECHA, ESTADO"
				+ " FROM ENOC.ALUM_HERMANOS "
				+ " WHERE FAMILIA_ID = TO_NUMBER(?,'99999') "+orden;
			Object[] parametros = new Object[] {familiaId};
			lista = enocJdbc.query(comando, new AlumHermanosMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumHermanosUtil|getListFamilia|:"+ex);
		
		}				
		return lista;
	}
	
	public HashMap<String, AlumHermanos> getMapAll(String condiciones){
		HashMap<String, AlumHermanos> mapa = new HashMap<String, AlumHermanos>();
		List<AlumHermanos> lista= new ArrayList<AlumHermanos>();

		try{
			String comando = "SELECT FAMILIA_ID, CODIGO_PERSONAL, TO_CHAR(FECHA, 'DD/MM/YYYY HH:MI AM') AS FECHA, ESTADO"+
					" FROM ENOC.ALUM_HERMANOS "+condiciones; 
			lista = enocJdbc.query(comando, new AlumHermanosMapper());
			for (AlumHermanos hermanos : lista){
				mapa.put(hermanos.getCodigoPersonal()+"-"+hermanos.getCodigoPersonal(), hermanos);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumHermanosUtil|getMapAll|:"+ex);
		
		}
		return mapa;
	}
}