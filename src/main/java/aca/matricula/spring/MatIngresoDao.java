package aca.matricula.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MatIngresoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(MatIngreso objeto){
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.MAT_INGRESO (EVENTO_ID, MATRICULA, PROPIOS, BECARIO, COLPORTAJE, OTRO) "
					+ " VALUES(TO_NUMBER(?,'99'), ?, ?, ?, ?, ?)";
			Object[] parametros = new Object[] { 
				objeto.getEventoId(),objeto.getMatricula(),objeto.getPropios(),objeto.getBecario(),objeto.getColportaje(),objeto.getOtro()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.MatIngresoDao|insertReg|:"+ex);			
	
		}
		return ok;
	}	

	public boolean updateReg(MatIngreso objeto){
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.MAT_INGRESO SET"
					+ " PROPIOS = ?,"
					+ " BECARIO = ?,"
					+ " COLPORTAJE = ?,"
					+ " OTRO = ?"
					+ " WHERE EVENTO_ID = TO_NUMBER(?,'99') AND MATRICULA = ?";							
			Object[] parametros = new Object[] {
				objeto.getPropios(),objeto.getBecario(),objeto.getColportaje(),objeto.getOtro(),objeto.getEventoId(),objeto.getMatricula()	
			};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.MatIngresoDao|updateReg|:"+ex);		
	
		}
		return ok;
	}
		
	public boolean deleteReg(String eventoId, String matricula){
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.MAT_INGRESO WHERE EVENTO_ID = TO_NUMBER(?,'99') AND MATRICULA = ?";
			Object[] parametros = new Object[] {eventoId, matricula};
 			if (enocJdbc.update(comando, parametros)==1){
 				ok = true;
 			}		
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.MatIngresoDao|deleteReg|:"+ex);
		}
		return ok;
	}
	
	public MatIngreso mapeaRegId(String eventoId, String matricula){
		MatIngreso objeto = new MatIngreso();		
		try{
			String comando = "SELECT EVENTO_ID, MATRICULA, PROPIOS, BECARIO, COLPORTAJE, OTRO"
					+ " FROM ENOC.MAT_INGRESO"
					+ " WHERE EVENTO_ID = TO_NUMBER(?,'99') AND MATRICULA = ?";
			Object[] parametros = new Object[] {eventoId,matricula};			
			objeto = enocJdbc.queryForObject(comando, new MatIngresoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.MatIngresoDao|mapeaRegId|:"+ex);
		
		}
		return objeto;
	}	
	
	public boolean existeReg(String eventoId, String matricula){
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MAT_INGRESO "+ 
				"WHERE EVENTO_ID = TO_NUMBER(?,'99') AND MATRICULA = ?";
			Object[] parametros = new Object[] {eventoId,matricula};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			} 
			
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.MatIngresoDao|existeReg|:"+ex);

		}
		return ok;
	}
	
	public List<MatIngreso> lisMatIngreso(String orden) {
		
		List<MatIngreso> lista= new ArrayList<MatIngreso>();
		try{
			String comando = "SELECT EVENTO_ID, MATRICULA, PROPIOS, BECARIO, COLPORTAJE, OTRO FROM ENOC.MAT_INGRESO"+ orden;
			lista = enocJdbc.query(comando, new MatIngresoMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.MatIngresoDao|lisMatIngreso|:"+ex);
		}
		
		return lista;
	}

	public HashMap<String,MatIngreso> mapaMatIngreso() {
		List<MatIngreso> lista = new ArrayList<MatIngreso>();
		HashMap<String,MatIngreso> mapa = new HashMap<String,MatIngreso>();
		
		try{
			String comando = "SELECT EVENTO_ID, MATRICULA, PROPIOS, BECARIO, COLPORTAJE, OTRO FROM ENOC.MAT_INGRESO";
			lista = enocJdbc.query(comando, new MatIngresoMapper());
			
			for(MatIngreso ingreso : lista) {
				mapa.put(ingreso.getMatricula()+ingreso.getEventoId(), ingreso);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.MatIngresoDao|mapaMatIngreso|:"+ex);
		}
		
		return mapa;
	}
}