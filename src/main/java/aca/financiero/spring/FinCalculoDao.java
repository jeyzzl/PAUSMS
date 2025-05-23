package aca.financiero.spring;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class FinCalculoDao {
	 
	@Autowired
	@Qualifier("jdbcAdmision")
	private JdbcTemplate admisionJdbc;
	
	public boolean insertReg(FinCalculo calculo) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO FIN_CALCULO(CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, ARCHIVO, NOMBRE, FECHA)"
					+ " VALUES(?, ?, TO_NUMBER(?,'99'), ?, ?, TO_TIMESTAMP(?,'DD-MM-YYYY HH:MI:SS'))";
			
			Object[] parametros = new Object[] {
				calculo.getCodigoPersonal(), calculo.getCargaId(), calculo.getBloqueId(), calculo.getArchivo(), calculo.getNombre(), calculo.getFecha()
			};
			if (admisionJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinCalculoDao|insertReg|:"+ex);
		}

		return ok;
	}
	
	public boolean updateReg(FinCalculo calculo) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE FIN_CALCULO"
					+ " SET ARCHIVO = ?, NOMBRE = ?, FECHA = TO_TIMESTAMP(?,'DD-MM-YYYY HH:MI:SS')"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CARGA_ID = ?"
					+ " AND BLOQUE_ID = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {
					calculo.getArchivo(), calculo.getNombre(), calculo.getFecha(), calculo.getCodigoPersonal(), calculo.getCargaId(), calculo.getBloqueId()
			};
			if (admisionJdbc.update(comando,parametros)==1){
				ok = true;
			}	

		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinCalculoDao|updateReg|:"+ex);
		}

		return ok;
	}
	
	public boolean deleteReg(String codigoPersonal, String cargaId, String bloqueId) {
		boolean ok = false;	
		try{
			String comando = "DELETE FROM FIN_CALCULO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CARGA_ID = ?"
					+ " AND BLOQUE_ID = TO_NUMBER(?,'99')";			
			Object[] parametros = new Object[]{codigoPersonal, cargaId, bloqueId};
			if (admisionJdbc.update(comando,parametros) == 1){
				ok = true;
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinCalculoDao|deleteReg|:"+ex);
		}
		
		return ok;
	}
	
	public FinCalculo mapeaRegId(String codigoPersonal, String cargaId, String bloqueId) {
		FinCalculo objeto = new FinCalculo();
		
		try {
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, ARCHIVO, NOMBRE, FECHA"
					+ " FROM FIN_CALCULO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CARGA_ID = ?"
					+ "	AND BLOQUE_ID = TO_NUMBER(?,'99')";
		
			Object[] parametros = new Object[] {codigoPersonal, cargaId , bloqueId};
			objeto = admisionJdbc.queryForObject(comando, new FinCalculoMapper(), parametros);
			
		} catch (Exception ex) {
			System.out.println("Error - aca.financiero.spring.FinCalculoDao|mapeaRegId|:"+ex);
		}
		
		return objeto;	
	}
	
	public boolean existeReg(String codigoPersonal, String cargaId, String bloqueId) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM FIN_CALCULO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CARGA_ID = ?"
					+ " AND BLOQUE_ID = TO_NUMBER(?, '99')";
			
			Object[] parametros = new Object[] {codigoPersonal, cargaId, bloqueId};
			if (admisionJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinCalculoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public HashMap<String, FinCalculo> mapaImagen(String codigoPersonal, String cargaId, String bloqueId) {
		
		HashMap<String,FinCalculo> mapa 	= new HashMap<String,FinCalculo>();
		List<FinCalculo> lista				= new ArrayList<FinCalculo>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, ARCHIVO, NOMBRE, FECHA FROM FIN_CALCULO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ "	AND CARGA_ID = ?"
					+ "	AND BLOQUE_ID = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {codigoPersonal, cargaId, bloqueId};
			lista = admisionJdbc.query(comando, new FinCalculoMapper(), parametros);
			for (FinCalculo map : lista ) {
				mapa.put(map.getCodigoPersonal(), map);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinCalculoDao|mapaImagen|:"+ex);
		}
		
		return mapa;
	}
}
