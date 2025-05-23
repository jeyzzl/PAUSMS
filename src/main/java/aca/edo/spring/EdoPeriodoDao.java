package aca.edo.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class EdoPeriodoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(EdoPeriodo objeto){
		boolean ok = false;
		try{
			String comando = "INSERT INTO ENOC.EDO_PERIODO(PERIODO_ID, PERIODO_NOMBRE, F_INICIO, F_FINAL, ESTADO)"
					+ " VALUES(?,?, TO_DATE(?, 'DD/MM/YYYY'), TO_DATE(?, 'DD/MM/YYYY'),?)";
			
			Object[] parametros = new Object[] {
				objeto.getPeriodoId(),objeto.getPeriodoNombre(),objeto.getfInicio(),objeto.getfFinal(),objeto.getEstado()
			};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoPeriodoDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg(EdoPeriodo objeto){
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.EDO_PERIODO"
					+ " SET PERIODO_NOMBRE = ?,"
					+ " F_INICIO = TO_DATE(?, 'DD/MM/YYYY'),"
					+ " F_FINAL = TO_DATE(?, 'DD/MM/YYYY'),"
					+ " ESTADO = ? "
					+ " WHERE PERIODO_ID = ? ";
				
			Object[] parametros = new Object[] {
				objeto.getPeriodoNombre(),objeto.getfInicio(),objeto.getfFinal(),objeto.getEstado(),objeto.getPeriodoId()
			};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoPeriodoDao|updateReg|:"+ex);		 
		}
		
		return ok;
	}	
	
	public boolean deleteReg(String periodoId){
		boolean ok = false;
		try{
			String comando = "DELETE FROM ENOC.EDO_PERIODO"
					+ " WHERE PERIODO_ID = ?";
			
			Object[] parametros = new Object[] {periodoId};
			
			if (enocJdbc.update(comando,parametros) >= 1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoPeriodoDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public EdoPeriodo mapeaRegId(String periodoId){
		EdoPeriodo objeto = new EdoPeriodo();
		
		try{
			String comando = "SELECT "
					+ " PERIODO_ID, PERIODO_NOMBRE,"
					+ " TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO,"
					+ " TO_CHAR(F_FINAL, 'DD/MM/YYYY') AS F_FINAL, "
					+ " ESTADO"
					+ " FROM ENOC.EDO_PERIODO"
					+ " WHERE PERIODO_ID = ?";
			
			Object[] parametros = new Object[] {periodoId};
			objeto = enocJdbc.queryForObject(comando, new EdoPeriodoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoPeriodoDao|mapeaRegId|:"+ex);
		}	
		
		return objeto;
	}
	
	public boolean existeReg(String periodoId){
		boolean ok = false;
		
		try{
			String comando = "SELECT * FROM ENOC.EDO_PERIODO WHERE PERIODO_ID = ?";
			
			Object[] parametros = new Object[] {periodoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoPeriodoDao|existeReg|:"+ex);
		}		
		
		return ok;
	}	
	
	public String getPeriodoNombre(String periodoId) {
		String nombre = "";
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EDO_PERIODO WHERE PERIODO_ID = ?";			
			Object[] parametros = new Object[] {periodoId};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				comando = "SELECT PERIODO_NOMBRE FROM ENOC.EDO_PERIODO WHERE PERIODO_ID = ?";	
 				nombre = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoPeriodoDao|getPeriodoNombre|:"+ex);
		}
		
		return nombre;
	}
	
	public String getActual(String fecha){
		String periodoId = "";		
		try{
			String comando = "SELECT PERIODO_ID FROM ENOC.EDO_PERIODO WHERE TO_DATE(?,'DD-MM-YY') BETWEEN F_INICIO AND F_FINAL";			
			Object[] parametros = new Object[] {fecha};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
 				periodoId = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoPeriodoDao|getAcual|:"+ex);
		}
		
		return periodoId;
	}
	
	public List<EdoPeriodo> getListAll(String orden){
		List<EdoPeriodo> lista = new ArrayList<EdoPeriodo>();		
		try{
			String comando = "SELECT PERIODO_ID, PERIODO_NOMBRE,"
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO,"
					+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL,"
					+ " ESTADO"
					+ " FROM ENOC.EDO_PERIODO "+orden;			
			lista = enocJdbc.query(comando, new EdoPeriodoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoPeriodoDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public List<EdoPeriodo> getListActivos(String orden){
		List<EdoPeriodo> lista = new ArrayList<EdoPeriodo>();
		try{
			String comando = "SELECT PERIODO_ID, PERIODO_NOMBRE,"
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO,"
					+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL,"
					+ " ESTADO"
					+ " FROM ENOC.EDO_PERIODO"
					+ " WHERE ESTADO = 'A' "+orden;			
			lista = enocJdbc.query(comando, new EdoPeriodoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoPeriodoDao|getListActivos|:"+ex);
		}
		
		return lista;
	}

	public HashMap<String, EdoPeriodo> mapaPeriodos(){
		List<EdoPeriodo> lista = new ArrayList<EdoPeriodo>();
		
		HashMap<String, EdoPeriodo> mapa = new HashMap<String, EdoPeriodo>();		
		try{
			String comando = "SELECT PERIODO_ID, PERIODO_NOMBRE, TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO, TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL, ESTADO FROM ENOC.EDO_PERIODO";			
			lista = enocJdbc.query(comando, new EdoPeriodoMapper());			
			for(EdoPeriodo periodo : lista) {
				mapa.put(periodo.getPeriodoId(), periodo);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoPeriodoDao|mapaPeriodos|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaPreguntas(){
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa = new HashMap<String,String>();		
		try{
			String comando = "SELECT EDO_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.EDO_ALUMNOPREG GROUP BY EDO_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());			
			for(aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoPeriodoDao|mapaPreguntas|:"+ex);
		}
		
		return mapa;
	}
}