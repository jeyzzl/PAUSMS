package aca.matricula.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MatCostosDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(MatCostos objeto){
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.MAT_COSTOS (EVENTO_ID, MATRICULA, CREDITOS, CARGA_ID) "
					+ " VALUES(TO_NUMBER(?,'99'), ?, TO_NUMBER(?,'99'), ?)";
			Object[] parametros = new Object[] { 
				objeto.getEventoId(),objeto.getMatricula(),objeto.getCreditos(), objeto.getCargaId()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.MatCostosDao|insertReg|:"+ex);			
	
		}
		return ok;
	}	

	public boolean updateReg(MatCostos objeto){
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.MAT_COSTOS SET"
					+ " CREDITOS = ?, CARGA_ID = ?"
					+ " WHERE EVENTO_ID = TO_NUMBER(?,'99') AND MATRICULA = ?";							
			Object[] parametros = new Object[] {
				objeto.getCreditos(), objeto.getCargaId(), objeto.getEventoId(),objeto.getMatricula()
			};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.MatCostosDao|updateReg|:"+ex);		
	
		}
		return ok;
	}
		
	public boolean deleteReg(String eventoId, String matricula){
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.MAT_COSTOS WHERE EVENTO_ID = TO_NUMBER(?,'99') AND MATRICULA = ?";
			Object[] parametros = new Object[] {eventoId, matricula};
 			if (enocJdbc.update(comando, parametros)==1){
 				ok = true;
 			}		
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.MatCostosDao|deleteReg|:"+ex);
		}
		return ok;
	}
	
	public MatCostos mapeaRegId(String eventoId, String matricula){
		MatCostos objeto = new MatCostos();		
		try{
			String comando = "SELECT EVENTO_ID, MATRICULA, CREDITOS, CARGA_ID"
					+ " FROM ENOC.MAT_COSTOS"
					+ " WHERE EVENTO_ID = TO_NUMBER(?,'99') AND MATRICULA = ?";
			Object[] parametros = new Object[] {eventoId,matricula};			
			objeto = enocJdbc.queryForObject(comando, new MatCostosMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.MatCostosDao|mapeaRegId|:"+ex);
		
		}
		return objeto;
	}	
	
	public boolean existeReg(String eventoId, String matricula){
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MAT_COSTOS"
					+ " WHERE EVENTO_ID = TO_NUMBER(?,'99') AND MATRICULA = ?";
			Object[] parametros = new Object[] {eventoId,matricula};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			} 
			
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.MatCostosDao|existeReg|:"+ex);

		}
		return ok;
	}
	
	public List<MatCostos> lisMatCostos(String orden) {
		
		List<MatCostos> lista= new ArrayList<MatCostos>();
		try{
			String comando = "SELECT EVENTO_ID, MATRICULA, CREDITOS, CARGA_ID FROM ENOC.MAT_COSTOS"+ orden;
			lista = enocJdbc.query(comando, new MatCostosMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.MatCostosDao|lisMatCostos|:"+ex);
		}
		
		return lista;
	}

	public HashMap<String,MatCostos> mapaMatCostos() {
		List<MatCostos> lista = new ArrayList<MatCostos>();
		HashMap<String,MatCostos> mapa = new HashMap<String,MatCostos>();
		
		try{
			String comando = "SELECT EVENTO_ID, MATRICULA, CREDITOS, CARGA_ID FROM ENOC.MAT_COSTOS";
			lista = enocJdbc.query(comando, new MatCostosMapper());
			
			for(MatCostos ingreso : lista) {
				mapa.put(ingreso.getMatricula()+ingreso.getEventoId(), ingreso);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.MatCostosDao|mapaMatCostos|:"+ex);
		}
		
		return mapa;
	}
}