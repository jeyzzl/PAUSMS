package aca.podium.spring;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Component;

@Component
public class ExamenDao {
	
	@Autowired
	@Qualifier("jdbcExa")
	private JdbcTemplate jdbcExamen;	
	
	public Examen buscaExamen(int folio) {
		Examen objeto = new Examen();
		String query = "SELECT * FROM EXA.EXAMEN WHERE FOLIO = ? AND ACTIVO = TRUE";
		
		try {
			jdbcExamen.query(query, new RowCallbackHandler()	{
				public void processRow(ResultSet rs) throws SQLException {
					objeto.setId(rs.getInt("ID"));
					objeto.setFolio(rs.getInt("FOLIO"));
					objeto.setFecha(rs.getDate("FECHA"));			
					objeto.setInicio(rs.getString("INICIO"));
					objeto.setTermino(rs.getString("TERMINO"));
					objeto.setTiempo(rs.getString("TIEMPO"));
					objeto.setActivo(rs.getBoolean("ACTIVO"));														
				}
			}, folio);
		} catch (Exception e) {
			System.out.println("ERROR : ExamenDao | buscaExamen | "+e);
		}
		
		return objeto;
	}
	
	public boolean existeActivo(int folio) {
		boolean ok = false;
		String query = "SELECT COUNT(*) FROM EXA.EXAMEN WHERE FOLIO = ? AND ACTIVO = TRUE";		
		try {
			if(jdbcExamen.queryForObject(query, Integer.class, folio) >= 1){
				ok = true;
			}
		} catch (Exception e) {
			System.out.println("ERROR : ExamenDao | existeActivo | "+e);
		}		
		return ok;
	}

	public boolean creaExamen(int folio) {
		boolean ok = false;
		String query = "INSERT INTO EXA.EXAMEN(FOLIO,FECHA,INICIO,TIEMPO,ACTIVO)"
				+ " VALUES(?,NOW(),NOW(),'0',TRUE)";
		
		try {
			if(jdbcExamen.update(query, new Object[]{folio}) >= 1){
				ok = true;
			}
		} catch (Exception e) {
			System.out.println("ERROR : ExamenDao | creaExamen | "+e);
		}
		
		return ok;
	}
	
	public String fechaExamen(String examenId) {
		String fecha 	= "-";		
		int exaId 		= Integer.parseInt(examenId);
		try {
			String query = "SELECT FECHA FROM EXA.EXAMEN WHERE ID = ?";
			fecha = jdbcExamen.queryForObject(query,  String.class, exaId);
		} catch (Exception e) {
			System.out.println("ERROR : ExamenDao | fechaExamen | "+e);
		}
		
		return fecha;
	}
	
	public List<Examen> lisExamenesPorFolio(int folio, String orden) {
		List<Examen> lista = new ArrayList<Examen>();				
		try {
			String query = "SELECT * FROM EXA.EXAMEN WHERE FOLIO = ? "+orden;	
			lista = jdbcExamen.query(query, new ExamenMapper(), folio);
		} catch (Exception e) {
			System.out.println("ERROR : ExamenDao | lisExamenesPorFolio | "+e);
		}		
		return lista;
	}	
	
	public HashMap<String, String> mapaPorFolio() {
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa = new HashMap<String,String>();			
		try {
			String query = "SELECT FOLIO AS LLAVE, COUNT(ID) AS VALOR FROM EXA.EXAMEN GROUP BY FOLIO";
			lista = jdbcExamen.query(query, new aca.MapaMapper());
			for(aca.Mapa map :lista) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		} catch (Exception e) {
			System.out.println("ERROR : ExamenAreaDao | mapaExamenes | "+e);
		}		
		return mapa;
	}
		
}
