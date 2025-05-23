package aca.alumno.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AlumGraduaDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AlumGradua alumGradua){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.ALUM_GRADUA"+ 
				"(CODIGO_PERSONAL, PLAN_ID, FECHA, FECHA_GRADUACION, EVENTO, "+
				"AVANCE, MAT_AC, MAT_INS, MAT_PEN, DIPLOMA) "+
				"VALUES( ?, ?, TO_DATE(?,'DD/MM/YYYY'), TO_DATE(?,'DD/MM/YYYY')," +
				"?,?," +
				"TO_NUMBER(?,'999.99'), "+
				"TO_NUMBER(?,'999'), "+
				"TO_NUMBER(?,'999'), ? )";
			Object[] parametros = new Object[] {alumGradua.getCodigoPersonal(),alumGradua.getPlanId(), alumGradua.getFecha(), alumGradua.getFechaGraduacion(),
					alumGradua.getEvento(),alumGradua.getAvance(), alumGradua.getMatAc(), alumGradua.getMatIns(), alumGradua.getMatPen(), alumGradua.getDiploma()};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumGraduaDao|insertReg|:"+ex);			
	
		}
		return ok;
	}	

	public boolean updateReg(AlumGradua alumGradua){
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.ALUM_GRADUA"+ 
				" SET "+
				" FECHA = TO_DATE(?,'DD/MM/YYYY'),"+
				" FECHA_GRADUACION = TO_DATE(?,'DD/MM/YYYY'),"+
				" EVENTO  = ?,"+
				" AVANCE = ?,"+
				" MAT_AC = TO_NUMBER(?,'999.99'),"+
				" MAT_INS = TO_NUMBER(?,'999'),"+
				" MAT_PEN = TO_NUMBER(?,'999')," +
				" DIPLOMA = ? "+
				" WHERE CODIGO_PERSONAL = ?"+
				" AND PLAN_ID = ?";			
			Object[] parametros = new Object[] {alumGradua.getFecha(),alumGradua.getFechaGraduacion(), alumGradua.getEvento(), alumGradua.getAvance(),
					alumGradua.getMatAc(),alumGradua.getMatIns(), alumGradua.getMatPen(), alumGradua.getDiploma(), alumGradua.getCodigoPersonal(), alumGradua.getPlanId()};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
	
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumGraduaDao|updateReg|:"+ex);		
	
		}
		return ok;
	}
		
	public boolean deleteReg(String codigoPersonal, String planId){
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.ALUM_GRADUA "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND PLAN_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, planId};
 			if (enocJdbc.update(comando, parametros)==1){
 				ok = true;
 			} 	
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumGraduaDao|deleteReg|:"+ex);			

		}
		return ok;
	}
	
	public AlumGradua mapeaRegId(String codigoPersonal, String planId){
		AlumGradua alumGradua = new AlumGradua();
		
		try{
			String comando = "SELECT"+
				" CODIGO_PERSONAL, PLAN_ID,"+
				" TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, TO_CHAR(FECHA_GRADUACION,'DD/MM/YYYY') AS FECHA_GRADUACION," +
				" EVENTO, AVANCE," +
				" MAT_AC, MAT_INS, MAT_PEN, DIPLOMA " +
				" FROM ENOC.ALUM_GRADUA "+ 
				" WHERE CODIGO_PERSONAL = ?"+
				" AND PLAN_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, planId};
			alumGradua = enocJdbc.queryForObject(comando, new AlumGraduaMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumGraduaDao|mapeaRegId|:"+ex);
		
		}
		return alumGradua;
	}	
	
	public boolean existeReg(String codigoPersonal, String planId){
		boolean 			ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_GRADUA "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND PLAN_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, planId};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			} 
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumGraduaDao|existeReg|:"+ex);

		}
		return ok;
	}
	
	public ArrayList<AlumGradua> getListAll(String orden){
		
		List<AlumGradua> lista		= new ArrayList<AlumGradua>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, PLAN_ID, TO_CHAR (FECHA, 'DD/MM/YYYY') AS FECHA, TO_CHAR (FECHA_GRADUACION, 'DD/MM/YYYY') AS FECHA_GRADUACION,"
					+ " EVENTO, AVANCE, MAT_AC, MAT_INS, MAT_PEN, DIPLOMA"
					+ " FROM ENOC.ALUM_GRADUA "+ orden; 
			lista = enocJdbc.query(comando, new AlumGraduaMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumGraduaDao|getListAll|:"+ex);

		}
		
		return (ArrayList<AlumGradua>) lista;
	}
	
	public List<AlumGradua> lisPorFechas( String fechaInicio, String fechaFinal, String orden ){		
		List<AlumGradua> lista	= new ArrayList<AlumGradua>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, PLAN_ID, TO_CHAR (FECHA, 'DD/MM/YYYY') AS FECHA, TO_CHAR (FECHA_GRADUACION, 'DD/MM/YYYY') AS FECHA_GRADUACION,"
					+ " EVENTO, AVANCE, MAT_AC, MAT_INS, MAT_PEN, DIPLOMA"
					+ " FROM ENOC.ALUM_GRADUA"
					+ " WHERE FECHA_GRADUACION >= TO_DATE(?,'DD/MM/YYYY') AND FECHA_GRADUACION <= TO_DATE(?, 'DD/MM/YYYY') "
					+ " AND CODIGO_PERSONAL||PLAN_ID IN (SELECT CODIGO_PERSONAL||PLAN_ID FROM ENOC.ALUM_EGRESO) "+ orden;
			Object[] parametros = new Object[] {fechaInicio, fechaFinal};
			lista = enocJdbc.query(comando, new AlumGraduaMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumGraduaDao|lisPorFechas|:"+ex);
		}
		
		return lista;
	}
	
	public ArrayList<AlumGradua> getLista( String codigoPersonal, String planId, String orden ){
		
		List<AlumGradua> lista	= new ArrayList<AlumGradua>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, PLAN_ID, TO_CHAR (FECHA, 'DD/MM/YYYY') AS FECHA, TO_CHAR (FECHA_GRADUACION, 'DD/MM/YYYY') AS FECHA_GRADUACION," +
					" EVENTO, AVANCE, MAT_AC, MAT_INS, MAT_PEN, DIPLOMA "+
					"FROM ENOC.ALUM_GRADUA "+ 
					"WHERE CODIGO_PERSONAL = ? "+
					"AND PLAN_ID = ? "+ orden;
			lista = enocJdbc.query(comando, new AlumGraduaMapper(), codigoPersonal, planId);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumGraduaDao|getLista|:"+ex);
		}
		
		return (ArrayList<AlumGradua>) lista;
	}
	
	
	public HashMap<String, String> getMapGraduados(){
		
		HashMap<String, String> mapa				= new HashMap<String,String>();
		List<aca.Mapa> lista						= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL||PLAN_ID AS LLAVE, CODIGO_PERSONAL AS VALOR FROM ENOC.ALUM_GRADUA";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumGraduaDao|getMapGraduados|:"+ex);
		}
		return mapa;
	}
}