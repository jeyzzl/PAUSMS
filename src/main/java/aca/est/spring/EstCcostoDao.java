// Clase Util para la tabla de Carga
package aca.est.spring;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class EstCcostoDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	
	
	public boolean insertReg( EstCcosto costo ) {		
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.EST_CCOSTO "
					+ " (ID, CODIGO_PERSONAL, CURSO_CARGA_ID, CCOSTO_ID, CURSO_ID,"
					+ " ALUMNOS, TOTAL, PORCENTAJE, UBICACION, ESTADO, HORAS, PORTOTAL, IMPORTE)"
					+ " VALUES(TO_NUMBER(?,'99999'), ?, ?, ?, ?, TO_NUMBER(?,'999'), TO_NUMBER(?,'999'),"
					+ " TO_NUMBER(?,'999.9999'), ?, ?, TO_NUMBER(?,'999.99'),TO_NUMBER(?,'999.9999'), TO_NUMBER(?,'999999.9999'))";
			Object[] parametros = new Object[] {costo.getId(), costo.getCodigoPersonal(), costo.getCursoCargaId(),
					costo.getcCostoId(), costo.getCursoId(), costo.getAlumnos(), costo.getTotal(), costo.getPorcentaje(),
					costo.getUbicacion(), costo.getEstado(), costo.getHoras(), costo.getPortotal(), costo.getImporte()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.est.spring.EstCcostoDao|insertReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateReg( EstCcosto costo ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.EST_CCOSTO"
				+ " SET CURSO_ID = ?,"
				+ " CURSO_CARGA_ID = ?,"
				+ " CODIGO_PERSONAL = ?,"
				+ "	CCOSTO_ID = ?,"
				+ " ALUMNOS = TO_NUMBER(?,'999'),"
				+ " TOTAL = TO_NUMBER(?,'999'),"
				+ " PORCENTAJE = TO_NUMBER(?,'999.9999'),"
				+ " UBICACION = ?,"
				+ " ESTADO = ?,"
				+ " HORAS = TO_NUMBER(?,'999.99'),"
				+ " PORTOTAL = TO_NUMBER(?,'999999.999999'),"
				+ " IMPORTE = TO_NUMBER(?,'999999.999999')"
				+ " WHERE ID = TO_NUMBER(?,'99999')";
			Object[] parametros = new Object[] {costo.getCursoId(), costo.getCursoCargaId(),
					costo.getCodigoPersonal(), costo.getcCostoId(),	costo.getAlumnos(),
					costo.getTotal(), costo.getPorcentaje(), costo.getUbicacion(), costo.getEstado(),
					costo.getHoras(), costo.getPortotal(), costo.getImporte(), costo.getId()};
			if (enocJdbc.update(comando,parametros) == 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.est.spring.EstCcostoDao|updateReg|:"+ex);		 
		}
		
		return ok;
	}
	
	public boolean updatePorTotal( ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.EST_CCOSTO"
				+ " SET PORTOTAL = COALESCE(((HORAS/(SELECT CASE HORAS WHEN 0 THEN 1 ELSE HORAS END FROM EST_MAESTRO WHERE CODIGO_PERSONAL = EST_CCOSTO.CODIGO_PERSONAL)) *100)*(PORCENTAJE/100),0)";			
			if (enocJdbc.update(comando) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.est.spring.EstCcostoDao|updateReg|:"+ex);		 
		}
		
		return ok;
	}
	
	public boolean updateImporte( ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.EST_CCOSTO"
				+ " SET IMPORTE = COALESCE((PORTOTAL*(SELECT IMPORTE FROM ENOC.EST_MAESTRO WHERE CODIGO_PERSONAL = EST_CCOSTO.CODIGO_PERSONAL))/100,0)";			
			if (enocJdbc.update(comando) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.est.spring.EstCcostoDao|updateReg|:"+ex);		 
		}
		
		return ok;
	}
	
	public boolean deleteReg( String id ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.EST_CCOSTO WHERE ID = TO_NMBER(?,'99999')";
			Object[] parametros = new Object[] {id};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.est.spring.EstCcostoDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public EstCcosto mapeaRegId( String id ) {
		
		EstCcosto costo = new EstCcosto();
		try{
			String comando = "SELECT ID, CODIGO_PERSONAL, CURSO_CARGA_ID, CCOSTO_ID, CURSO_ID,"
					+ " ALUMNOS, TOTAL, PORCENTAJE, UBICACION, ESTADO, HORAS, PORTOTAL, IMPORTE"
					+ " FROM ENOC.EST_CCOSTO"
					+ " WHERE ID = TO_NUMBER(?,'99999')";
			Object[] parametros = new Object[] {id};
			costo = enocJdbc.queryForObject(comando, new EstCcostoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.est.spring.EstCcostoDao|mapeaRegId|:"+ex);
		}
		
		return costo;
	}
	
	public String maximoReg( ) throws SQLException{		
		int maximo				= 1;
		
		try{
			String comando = "SELECT COALESCE(MAX(ID)+1,1) AS MAXIMO FROM ENOC.EST_CCOSTO";			
			maximo = enocJdbc.queryForObject(comando,Integer.class);
			
		}catch(Exception ex){
			System.out.println("Error - aca.est.spring.EstCcostoDao|maximoReg|:"+ex);
		}
		
		return String.valueOf(maximo);
	}	
	
	public boolean existeReg(String codigoPersonal, String cursoCargaId, String cCostoId) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EST_CCOSTO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CURSO_CARGA_ID = ?"
					+ " AND CCOSTO_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, cursoCargaId, cCostoId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.est.spring.EstCcostoDao|existeReg|:"+ex);
		}
		
		return ok;
	}	
	
	public boolean existeReg(String id) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EST_CCOSTO WHERE ID = TO_NUMBER(?,'99999')";
			Object[] parametros = new Object[] {id};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.est.spring.EstCcostoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public List<EstCcosto> listAll( String orden ) {
		
		List<EstCcosto> lista	= new ArrayList<EstCcosto>();	
		String comando	= "";
		
		try{
			comando = " SELECT ID, CODIGO_PERSONAL, CURSO_CARGA_ID, CCOSTO_ID, CURSO_ID,"
					+ " ALUMNOS, TOTAL, PORCENTAJE, UBICACION, ESTADO, HORAS, PORTOTAL, IMPORTE"
					+ " FROM ENOC.EST_CCOSTO "+orden; 
			lista = enocJdbc.query(comando, new EstCcostoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.est.spring.EstCcostoDao|listAll|:"+ex);
		}
		
		return lista;
	}	
	
	public List<String> listDepartamentos( String orden ) {
		
		List<String> lista	= new ArrayList<String>();
		String comando	= "";
		
		try{
			comando = " SELECT DISTINCT(CCOSTO_ID) AS CCOSTO_ID FROM ENOC.EST_CCOSTO "+orden;
			lista = enocJdbc.queryForList(comando, String.class);			
		}catch(Exception ex){
			System.out.println("Error - aca.est.spring.EstCcostoDao|ListDepartamentos|:"+ex);
		}
		
		return lista;
	}
	
	public List<String> listMaestrosDepto( String costoId, String orden ){
		
		List<String> lista	= new ArrayList<String>();
		String comando	= "";
		
		try{
			comando = " SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL FROM ENOC.EST_CCOSTO WHERE CCOSTO_ID = ? "+orden;
			Object[] parametros = new Object[] {costoId};
			lista = enocJdbc.queryForList(comando, String.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.est.spring.EstCcostoDao|listMaestrosDepto|:"+ex);
		}
		
		return lista;
	}
	
	public List<EstCcosto> listFacultad( String facultad, String estado, String orden ) {
		
		List<EstCcosto> lista	= new ArrayList<EstCcosto>();	
		String comando	= "";
		
		try{
			comando = " SELECT ID, CODIGO_PERSONAL, CURSO_CARGA_ID, CCOSTO_ID, CURSO_ID,"
					+ " ALUMNOS, TOTAL, PORCENTAJE, UBICACION, ESTADO, HORAS, PORTOTAL, IMPORTE"
					+ " FROM ENOC.EST_CCOSTO "
					+ " WHERE UBICACION = ?"
					+ " AND ESTADO = ? "+orden; 
			Object[] parametros = new Object[] {facultad, estado};
			lista = enocJdbc.query(comando, new EstCcostoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.est.spring.EstCcostoDao|listFacultad|:"+ex);
		}
		
		return lista;
	}
	
	public List<EstCcosto> listFacultad( String estado, String orden ) {
		
		List<EstCcosto> lista	= new ArrayList<EstCcosto>();	
		String comando	= "";
		
		try{
			comando = " SELECT ID, CODIGO_PERSONAL, CURSO_CARGA_ID, CCOSTO_ID, CURSO_ID,"
					+ " ALUMNOS, TOTAL, PORCENTAJE, UBICACION, ESTADO, HORAS, PORTOTAL, IMPORTE"
					+ " FROM ENOC.EST_CCOSTO "
					+ " WHERE ESTADO = ? "+orden; 
			Object[] parametros = new Object[] {estado};
			lista = enocJdbc.query(comando, new EstCcostoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.est.spring.EstCcostoDao|listFacultad|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,EstCcosto> mapaTodos( ) {
		
		HashMap<String,EstCcosto> mapa = new HashMap<String,EstCcosto>();
		List<EstCcosto> lista	= new ArrayList<EstCcosto>();
		
		try{
			String comando = "SELECT ID, CODIGO_PERSONAL, CURSO_CARGA_ID, CCOSTO_ID, CURSO_ID, ALUMNOS, TOTAL, PORCENTAJE, UBICACION, ESTADO, HORAS, PORTOTAL, IMPORTE"
					+ " FROM ENOC.EST_CCOSTO";
			lista = enocJdbc.query(comando, new EstCcostoMapper());
			for (EstCcosto costo : lista ) {
				mapa.put(costo.getId(), costo );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.est.spring.EstCcostoDao|mapaTodos|:"+ex);
		}
		
		return mapa;
	}
	
	/*  + " COALESCE( ((HORAS/(SELECT CASE HORAS WHEN 0 THEN 1 ELSE HORAS END FROM EST_MAESTRO WHERE CODIGO_PERSONAL = EST_CCOSTO.CODIGO_PERSONAL)) *100)*(PORCENTAJE/100),0) AS VALOR"   */
	public HashMap<String, String> mapaPorGlobal(){
		
		HashMap<String, String> mapa	= new HashMap<String,String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		
		try{
			String comando = " SELECT ID AS LLAVE, PORTOTAL AS VALOR FROM ENOC.EST_CCOSTO";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.spring.EstCcostoDao|mapaPorGlobal|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaTotalDepartamento(){
		
		HashMap<String, String> mapa	= new HashMap<String,String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		
		try{
			String comando = " SELECT CCOSTO_ID AS LLAVE, SUM(IMPORTE) AS VALOR FROM ENOC.EST_CCOSTO GROUP BY CCOSTO_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.spring.EstCcostoDao|mapaTotalDepartamento|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaImporte(String costoId){
		
		HashMap<String, String> mapa	= new HashMap<String,String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		
		try{
			String comando = " SELECT CODIGO_PERSONAL AS LLAVE, SUM(IMPORTE) AS VALOR FROM ENOC.EST_CCOSTO WHERE CCOSTO_ID = ? GROUP BY CODIGO_PERSONAL";
			Object[] parametros = new Object[] {costoId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.spring.EstCcostoDao|mapaImporte|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaPorMaestro(String costoId){
		
		HashMap<String, String> mapa	= new HashMap<String,String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, SUM(PORTOTAL) AS VALOR FROM ENOC.EST_CCOSTO"
					+ " WHERE CCOSTO_ID = ?"
					+ " GROUP BY CODIGO_PERSONAL";
			Object[] parametros = new Object[] {costoId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.spring.EstCcostoDao|mapaImporte|:"+ex);
		}
		
		return mapa;
	}
		
	public HashMap<String, String> mapaMateriasPorMaestro(){
		
		HashMap<String, String> mapa	= new HashMap<String,String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, COUNT(DISTINCT(CURSO_CARGA_ID)) AS VALOR FROM EST_CCOSTO GROUP BY CODIGO_PERSONAL";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.spring.EstCcostoDao|mapaMateriasPorMaestro|:"+ex);
		}
		
		return mapa;
	}
	
	/*SELECT CODIGO_PERSONAL, SUM(ALUMNOS) FROM EST_CCOSTO GROUP BY CODIGO_PERSONAL;*/
	public HashMap<String, String> mapaAlumPorMaestro(){
		
		HashMap<String, String> mapa	= new HashMap<String,String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, SUM(ALUMNOS) AS VALOR FROM EST_CCOSTO GROUP BY CODIGO_PERSONAL";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.spring.EstCcostoDao|mapaAlumPorMaestro|:"+ex);
		}
		
		return mapa;
	}
	
	/* SELECT CODIGO_PERSONAL, COUNT(DISTINCT(CURSO_CARGA_ID)) FROM EST_CCOSTO WHERE TOTAL BETWEEN 1 AND 1 GROUP BY CODIGO_PERSONAL; */	
	public HashMap<String, String> mapaMatPorRango(int inicio, int fin){
		
		HashMap<String, String> mapa	= new HashMap<String,String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, COUNT(DISTINCT(CURSO_CARGA_ID)) AS VALOR FROM EST_CCOSTO WHERE TOTAL >= ? AND TOTAL <= ? GROUP BY CODIGO_PERSONAL";
			Object[] parametros = new Object[] {inicio, fin};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.spring.EstCcostoDao|mapaMatPorRango|:"+ex);
		}
		
		return mapa;
	}
	
	/* SELECT CODIGO_PERSONAL||CCOSTO_ID AS LLAVE, SUM(PORTOTAL) AS VALOR FROM EST_CCOSTO GROUP BY CODIGO_PERSONAL, CCOSTO_ID;*/
	public HashMap<String, String> mapaPorTotDepto(){
		
		HashMap<String, String> mapa	= new HashMap<String,String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL||CCOSTO_ID AS LLAVE, SUM(PORTOTAL) AS VALOR FROM EST_CCOSTO GROUP BY CODIGO_PERSONAL, CCOSTO_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.spring.EstCcostoDao|mapaPorTotDepto|:"+ex);
		}
		
		return mapa;
	}
}