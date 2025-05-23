package aca.bitacora.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BitRequisitoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( BitRequisito objeto) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.BIT_REQUISITO (REQUISITO_ID, NOMBRE, TIPO, COMENTARIO) VALUES(TO_NUMBER(?, '9999'), ?, ?, ?)";
			Object[] parametros = new Object[] {
				objeto.getRequisitoId(), objeto.getNombre(), objeto.getTipo(), objeto.getComentario()
			};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitRequisitoDao|insertReg|:"+ex);	
		}
		return ok;
	}	
	
	public boolean updateReg( BitRequisito objeto) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.BIT_REQUISITO"
					+ " SET NOMBRE = ?, TIPO = ?, COMENTARIO = ?"
					+ " WHERE REQUISITO_ID = TO_NUMBER(?, '9999')";
			Object[] parametros = new Object[] {
				objeto.getNombre(), objeto.getTipo(), objeto.getComentario(),objeto.getRequisitoId()
			};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitRequisitoDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	public boolean deleteReg(String requisitoId) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.BIT_REQUISITO WHERE REQUISITO_ID = TO_NUMBER(?, '9999')";
			Object[] parametros = new Object[] {requisitoId};
			if (enocJdbc.update(comando,parametros)==1){
//				comando = "DELETE FROM ENOC.BIT_REQUISITO_TRAMITE WHERE REQUISITO_ID = TO_NUMBER(?, '9999')";
//				enocJdbc.update(comando,parametros);
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitRequisitoDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public boolean existeReg(String requisitoId) {
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BIT_REQUISITO WHERE REQUISITO_ID = TO_NUMBER(?, '9999')";
			Object[] parametros = new Object[] {requisitoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitRequisitoDao|existeReg|:"+ex);
			ex.printStackTrace();
		}
		return ok;
	}
	
	public BitRequisito mapeaRegId( String requisitoId) {
		BitRequisito objeto = new BitRequisito();		
		try{
			String comando = " SELECT REQUISITO_ID, NOMBRE, TIPO, COMENTARIO "
						+ " FROM ENOC.BIT_REQUISITO WHERE REQUISITO_ID = "+requisitoId;
			objeto = enocJdbc.queryForObject(comando, new BitRequisitoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitRequisitoDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return objeto;
	}
	
	public List<BitRequisito> lisRequisitos( String orden) {		
		List<BitRequisito> lista = new ArrayList<BitRequisito>();		
		try{
			String comando = " SELECT REQUISITO_ID, NOMBRE, TIPO, COMENTARIO "
					+ " FROM BIT_REQUISITO "+orden;
			lista = enocJdbc.query(comando, new BitRequisitoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitRequisitoDao|lisRequisitos|:"+ex);
		}
		return lista;
	}	
	
	public String maximoReg() {
		String maximo = "1";
		
		try{
			String comando =  "SELECT COALESCE(MAX(REQUISITO_ID)+1,1) FROM ENOC.BIT_REQUISITO";
			maximo = enocJdbc.queryForObject(comando, String.class);
 			
		}catch(Exception ex){
			System.out.println("Error -  aca.bitacora.spring.BitRequisitoDao|maximoReg|:"+ex);
		}
		return maximo;
	}

	public HashMap<String, BitRequisito> mapRequisitos(){
		HashMap<String, BitRequisito> mapa = new HashMap<String, BitRequisito>();
		List<BitRequisito> lista			 = new ArrayList<BitRequisito>();		
		try{
			String comando = " SELECT REQUISITO_ID, NOMBRE, TIPO, COMENTARIO"
					+ " FROM ENOC.BIT_REQUISITO";
			lista = enocJdbc.query(comando,new BitRequisitoMapper());
			for(BitRequisito objeto : lista){				
				mapa.put(objeto.getRequisitoId(), objeto);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitRequisitoDao|mapRequisitos|:"+ex);
		}
		return mapa;
	}
	
}
