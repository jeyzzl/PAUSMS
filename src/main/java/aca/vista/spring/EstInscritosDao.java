// Clase para la vista EstInscritos
package  aca.vista.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class EstInscritosDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;

	public EstInscritos mapeaRegId(  String codigoPersonal ) {
		EstInscritos estIns = new EstInscritos();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL,"
					+ " NOMBRE, EDAD, SEXO, RESIDENCIA_ID, NOMBRE_RELIGION,"
					+ " TIPO, CARGA_ID, MODALIDAD, PLAN_ID"
					+ " FROM ENOC.ESTINSCRITOS"
					+ " WHERE CODIGO_PERSONAL = ? ";
			
			Object[] parametros = new Object[] {codigoPersonal};
			estIns = enocJdbc.queryForObject(comando, new EstInscritosMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstInscritosDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return estIns;
	}
	
	public boolean existeReg(String codigoPersonal) {
		boolean 			ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ESTINSCRITOS WHERE CODIGO_PERSONAL = ? ";
		
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstInscritosDao|existeReg|:"+ex);
		}
		return ok;
	}		
	
	public List<EstInscritos> getListAll( String orden ) {
		List<EstInscritos> lista	= new ArrayList<EstInscritos>();
		String comando			= "";
		
		try{
			comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, NOMBRE,"+
						"EDAD, SEXO, RESIDENCIA_ID, NOMBRE_RELIGION, "+
						"TIPO, CARGA_ID, MODALIDAD, PLAN_ID " +
						"FROM ENOC.ESTINSCRITOS "+ orden;
			
			lista = enocJdbc.query(comando, new EstInscritosMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstInscritosDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public List<EstInscritos> getListPrimerIngreso( String orden ) {
		List<EstInscritos> lista	= new ArrayList<EstInscritos>();
		String comando			= "";
		
		try{
			comando = "SELECT DISTINCT(E.CODIGO_PERSONAL) AS CODIGO_PERSONAL, "+ 
				"E.NOMBRE,EDAD, E.SEXO, E.RESIDENCIA_ID, E.NOMBRE_RELIGION,E.TIPO, " +
				"E.CARGA_ID, E.MODALIDAD, E.PLAN_ID "+
				"FROM ENOC.ESTINSCRITOS E " +
				"WHERE E.CODIGO_PERSONAL NOT IN" +
					"(SELECT KA.CODIGO_PERSONAL FROM ENOC.KRDX_CURSO_ACT KA " + 
					"WHERE KA.CODIGO_PERSONAL = E.CODIGO_PERSONAL " +
					"AND SUBSTR(KA.CURSO_ID,1,8)= E.PLAN_ID " +
					"AND KA.TIPOCAL_ID NOT IN('I','M','3')) " +
				"AND E.CODIGO_PERSONAL NOT IN "+
					"(SELECT KI.CODIGO_PERSONAL FROM ENOC.KRDX_CURSO_IMP KI " + 
					"WHERE KI.CODIGO_PERSONAL = E.CODIGO_PERSONAL " +
					"AND SUBSTR(KI.CURSO_ID,1,8)= E.PLAN_ID " +
					"AND KI.TIPOCAL_ID NOT IN('I','M')) "+ orden;
			
			lista = enocJdbc.query(comando, new EstInscritosMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstInscritosDao|getListPrimerIngreso|:"+ex);
		}
		return lista;
	}
	
	public List<EstInscritos> getListPrimerIngresoCargaModalidad( String cargas, String modalidades, String orden ) {
		List<EstInscritos> lista	= new ArrayList<EstInscritos>();
		String comando			= "";
		
		try{
			comando = "SELECT DISTINCT(E.CODIGO_PERSONAL) AS CODIGO_PERSONAL, "+ 
				"E.NOMBRE,EDAD, E.SEXO, E.RESIDENCIA_ID, E.NOMBRE_RELIGION,E.TIPO, " +
				"E.CARGA_ID, E.MODALIDAD, E.PLAN_ID "+
				"FROM ENOC.ESTINSCRITOS E " +
				"WHERE E.CARGA_ID IN ("+cargas+") AND E.MODALIDAD IN ("+modalidades+") AND E.CODIGO_PERSONAL NOT IN" +
					"(SELECT KA.CODIGO_PERSONAL FROM ENOC.KRDX_CURSO_ACT KA " + 
					"WHERE KA.CODIGO_PERSONAL = E.CODIGO_PERSONAL " +
					"AND SUBSTR(KA.CURSO_ID,1,8)= E.PLAN_ID " +
					"AND KA.TIPOCAL_ID NOT IN('I','M','3')) " +
				"AND E.CODIGO_PERSONAL NOT IN "+
					"(SELECT KI.CODIGO_PERSONAL FROM ENOC.KRDX_CURSO_IMP KI " + 
					"WHERE KI.CODIGO_PERSONAL = E.CODIGO_PERSONAL " +
					"AND SUBSTR(KI.CURSO_ID,1,8)= E.PLAN_ID " +
					"AND KI.TIPOCAL_ID NOT IN('I','M')) "+ orden;
			
			lista = enocJdbc.query(comando, new EstInscritosMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstInscritosDao|getListPrimerIngresoCargaModalidad|:"+ex);
		}
		return lista;
	}	
	
	public HashMap<String,String> getAlumIngreso( String cargas, String modalidades) {
		HashMap<String,String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		String comando							= "";
				
		try{
			comando = "SELECT CODIGO_PERSONAL AS LLAVE, " +
					" TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY')||'%'||ENOC.ALUM_INGRESO_UM(CODIGO_PERSONAL)||'%'|| ENOC.ALUM_INGRESO_PLAN(CODIGO_PERSONAL, PLAN_ID) AS VALOR  " +
					" FROM ENOC.ESTINSCRITOS" +
					" WHERE CARGA_ID IN ("+cargas+") AND MODALIDAD IN ("+modalidades+") ";
			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista){
				mapa.put(map.getLlave(), map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring..EstInscritosDao|getAlumIngreso|:"+ex);
		}
		
		return mapa;
	}
}