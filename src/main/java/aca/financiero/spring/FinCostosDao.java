package aca.financiero.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class FinCostosDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(FinCostos costos) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.FIN_COSTOS(CARGA_ID,INTERNADO,COMEDOR,MAT_MEX,MAT_EXT,PAGARE)"
					+ " VALUES (?,TO_NUMBER(?,'9999999'),TO_NUMBER(?,'9999999'),TO_NUMBER(?,'9999999'),TO_NUMBER(?,'9999999'),TO_NUMBER(?,'9999999'))";			
			Object[] parametros = new Object[] {
				costos.getCargaId(), costos.getInternado(), costos.getComedor(), costos.getMatMex(), costos.getMatExt(), costos.getPagare()
			};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch (Exception ex){
			System.out.println("Error - aca.financiero.spring.FinCostosDao|insertReg|:"+ex);
		}
		return ok;
	}
	
	public boolean updateReg(FinCostos costos ) {
		boolean ok = false;		
		try{
			String comando ="UPDATE ENOC.FIN_COSTOS"
					+ " SET INTERNADO = TO_NUMBER(?,'9999999'),"
					+ " COMEDOR = TO_NUMBER(?,'9999999'),"
					+ " MAT_MEX = TO_NUMBER(?,'9999999'),"
					+ " MAT_EXT = TO_NUMBER(?,'9999999'),"
					+ " PAGARE = TO_NUMBER(?,'9999999')"
					+ " WHERE CARGA_ID = ?";
			Object[] parametros = new Object[] { 
				costos.getCargaId(), costos.getInternado(), costos.getComedor(), costos.getMatMex(), costos.getMatExt(), costos.getPagare()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinCostosDao|updateReg|:"+ex);
		}
		
		return ok;
	}
	
	public FinCostos mapeaRegId(String cargaId){
		
		FinCostos objeto = new FinCostos();		
		try{
			String comando = "SELECT CARGA_ID,INTERNADO,COMEDOR,MAT_MEX,MAT_EXT,PAGARE"
				+ " FROM ENOC.FIN_COSTOS WHERE CARGA_ID = ?";
			Object[] parametros = new Object[] {cargaId};
			objeto = enocJdbc.queryForObject(comando, new FinCostosMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinCostosDao|mapeaRegId|:"+ex);
		}
		return objeto;
	}
	
	public boolean deleteReg(String cargaId){
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.FIN_COSTOS WHERE CARGA_ID = ?";
			
			Object[] parametros = new Object[] {
					cargaId
			};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinCostosDao|deleteReg|:"+ex);
		}

		return ok;
	}
	
	public boolean existeReg(String cargaId){
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.FIN_COSTOS WHERE CARGA_ID = ?";
			
			Object[] parametros = new Object[] {cargaId};	
			
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1) {
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinCostosDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public List<FinCostos> listFinCostos(String orden){
		List<FinCostos> lista 	= new ArrayList<FinCostos>();		
		try{			
			String comando = "SELECT CARGA_ID,INTERNADO,COMEDOR,MAT_MEX,MAT_EXT,PAGARE FROM ENOC.FIN_COSTOS " +orden;
			lista = enocJdbc.query(comando, new FinCostosMapper());		
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinCostosDao|listFinCostos|:"+ex);
		}
		
		return lista;
	}
	
	public List<FinCostos> lisFinCostosActivos(String orden){
		List<FinCostos> lista 	= new ArrayList<FinCostos>();		
		try{			
			String comando = "SELECT CARGA_ID,INTERNADO,COMEDOR,MAT_MEX,MAT_EXT,PAGARE FROM ENOC.FIN_COSTOS WHERE ESTADO = 'A'" +orden;
			lista = enocJdbc.query(comando, new FinCostosMapper());		
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinCostosDao|listFinCostos|:"+ex);
		}
		
		return lista;
	}

}
