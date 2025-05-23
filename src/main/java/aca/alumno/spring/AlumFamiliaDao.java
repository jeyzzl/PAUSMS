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
public class AlumFamiliaDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( AlumFamilia alumFamilia ){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.ALUM_FAMILIA"+ 
				"(FAMILIA_ID, FECHA, ESTADO)"+
				" VALUES( ?, now(), ?)";
			Object[] parametros = new Object[] {alumFamilia.getFamiliaId(),alumFamilia.getEstado()};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumFamiliaDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg( AlumFamilia alumFamilia ){
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.ALUM_FAMILIA SET ESTADO = ? WHERE FAMILIA_ID = TO_NUMBER(?,'99999')";
			Object[] parametros = new Object[] {
					alumFamilia.getEstado(),alumFamilia.getFamiliaId()};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumFamiliaDao|updateReg|:"+ex);		
			
		}
		return ok;
	}
	
	public boolean updateEstado( String familiaId, String estado ){
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.ALUM_FAMILIA SET ESTADO = ? WHERE FAMILIA_ID = TO_NUMBER(?,'99999')";
			Object[] parametros = new Object[] {estado,familiaId};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumFamiliaDao|updateEstado|:"+ex);	
		}
		return ok;
	}
	
	public boolean deleteReg( String familiaId ){
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.ALUM_FAMILIA"+ 
				" WHERE FAMILIA_ID = ?";
			Object[] parametros = new Object[] {familiaId};
 			if (enocJdbc.update(comando, parametros)==1){
 				ok = true;
 			} 	
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumFamiliaDao|deleteReg|:"+ex);			
	
		}
		return ok;
	}
	
	public AlumFamilia mapeaRegId(  String familiaId){
		
		AlumFamilia alumFamilia = new AlumFamilia();
	
		try{
			String comando = "SELECT"+
				" FAMILIA_ID, TO_CHAR(FECHA, 'DD/MM/YYYY HH:MM AM') AS FECHA, ESTADO"+
				" FROM ENOC.ALUM_FAMILIA WHERE FAMILIA_ID = ?"; 
			Object[] parametros = new Object[] {familiaId};
			alumFamilia = enocJdbc.queryForObject(comando, new AlumFamiliaMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumFamiliaDao|mapeaRegId|:"+ex);
	
		}
		return alumFamilia;
	}
	
	public boolean existeReg( String familiaId){
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(FAMILIA_ID) FROM ENOC.ALUM_FAMILIA "+ 
				"WHERE FAMILIA_ID = ?";
			Object[] parametros = new Object[] {familiaId};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			} 
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumFamiliaDao|existeReg|:"+ex);
	
		}
		return ok;
	}
	
	public String maximoReg(){
		String maximo			= "1";
		
		try{
			String comando = "SELECT COALESCE(MAX(FAMILIA_ID)+1, 1) AS MAXIMO FROM ENOC.ALUM_FAMILIA";
			if(enocJdbc.queryForObject(comando,Integer.class) >= 1){
				maximo = String.valueOf(enocJdbc.queryForObject(comando,String.class));
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumFamiliaDao|maximoReg|:"+ex);		
		}
		return maximo;
	}
		
	public ArrayList<AlumFamilia> getListAll( String orden ){
		
		List<AlumFamilia> lista= new ArrayList<AlumFamilia>();
		
		try{
			String comando = "SELECT FAMILIA_ID, TO_CHAR(FECHA, 'DD/MM/YYYY HH:MI AM')AS FECHA, ESTADO"+
				" FROM ENOC.ALUM_FAMILIA "+orden; 
			lista = enocJdbc.query(comando, new AlumFamiliaMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumFamiliaDao|getListAll|:"+ex);

		}				
		return (ArrayList<AlumFamilia>) lista;
	}
	
	public HashMap<String, AlumFamilia> getMapAll( String condiciones){
		HashMap<String, AlumFamilia> mapa = new HashMap<String, AlumFamilia>();
		List<AlumFamilia> lista= new ArrayList<AlumFamilia>();
		
		try{
			String comando = "SELECT FAMILIA_ID, TO_CHAR(FECHA, 'DD/MM/YYYY HH:MI AM')AS FECHA, ESTADO"+
					" FROM ENOC.ALUM_FAMILIA "+condiciones; 
			lista = enocJdbc.query(comando, new AlumFamiliaMapper());
			for (AlumFamilia familia : lista){
				mapa.put(familia.getFamiliaId(), familia);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumFamiliaDao|getMapAll|:"+ex);
		
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaMiembros(){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT FAMILIA_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.ALUM_FAMILIA GROUP BY FAMILIA_ID"; 
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista){
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumFamiliaDao|mapaMiembros|:"+ex);
		}
		return mapa;
	}
}