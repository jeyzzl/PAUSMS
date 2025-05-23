package aca.bitacora.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BitEtiquetaDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( BitEtiqueta etiqueta){
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.BIT_ETIQUETA(FOLIO, ETIQUETA_ID, AREA_ID, COMENTARIO, FECHA, USUARIO, TURNADO)"
				+ " VALUES(?, TO_NUMBER(?,'99'), TO_NUMBER(?,'99'), ?, NOW(), ?, ?)";
			Object[] parametros = new Object[] {etiqueta.getFolio(), etiqueta.getEtiquetaId(),
			etiqueta.getAreaId(), etiqueta.getComentario(), etiqueta.getUsuario(), etiqueta.getTurnado()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitEtiquetaDao|insertReg|:"+etiqueta.getFolio()+":"+etiqueta.getEtiquetaId()+":"+etiqueta.getUsuario()+"::"+ex);
		}
		return ok;
	}	
	
	public boolean updateReg( BitEtiqueta etiqueta) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.BIT_ETIQUETA "
					+ " SET AREA_ID = TO_NUMBER(?,'99'), "
					+ " COMENTARIO = ?, "
					+ " FECHA = NOW(),"			
					+ " USUARIO = ?,"
					+ " TURNADO = ?"
					+ " WHERE FOLIO = ?"
					+ " AND ETIQUETA_ID = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {etiqueta.getAreaId(), etiqueta.getComentario(),			
			etiqueta.getUsuario(), etiqueta.getTurnado(), etiqueta.getFolio(), etiqueta.getEtiquetaId()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitEtiquetaDao|updateReg|:"+ex);
		}
		return ok;
	}
	
	public boolean updateComentario( String folio, String etiquetaId, String comentario) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.BIT_ETIQUETA "
					+ " SET COMENTARIO = ? "					
					+ " WHERE FOLIO = ?"
					+ " AND ETIQUETA_ID = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {comentario, folio, etiquetaId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitEtiquetaDao|updateComentario|:"+ex);
		}
		return ok;
	}
	
	public boolean deleteReg( String folio, String etiquetaId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.BIT_ETIQUETA"
					+ " WHERE FOLIO = ?"
					+ " AND ETIQUETA_ID = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {folio, etiquetaId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitEtiquetaDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public boolean deleteAllReg( String folio ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.BIT_ETIQUETA WHERE FOLIO = ?";
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.update(comando,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitEtiquetaDao|deleteAllReg|:"+ex);			
		}
		return ok;
	}
	
	public BitEtiqueta mapeaRegId(  String folio, String etiquetaId) {
		
		BitEtiqueta objeto = new BitEtiqueta();
		try{
			String comando = " SELECT FOLIO, ETIQUETA_ID, AREA_ID, COMENTARIO, TO_CHAR(FECHA,'DD/MM/YYYY HH24:MI:SS') AS FECHA, USUARIO, TURNADO "
					+ " FROM ENOC.BIT_ETIQUETA WHERE FOLIO = ? AND ETIQUETA_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {folio, etiquetaId};
			objeto = enocJdbc.queryForObject(comando, new BitEtiquetaMapper(),parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitEtiquetaDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return objeto;
	}
	
	public boolean existeRegId(  String folio, String etiquetaId) {
		boolean ok = false;		
		try{
			String comando = " SELECT COUNT(*) FROM ENOC.BIT_ETIQUETA WHERE FOLIO = ? AND ETIQUETA_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {folio, etiquetaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitEtiquetaDao|existeRegId|:"+ex);
			ex.printStackTrace();
		}
		return ok;
	}
	
	public String getComentario(  String folio, String etiquetaId) {
		String nombre = "-";
		
		try{
			String comando = " SELECT COMENTARIO FROM ENOC.BIT_ETIQUETA WHERE FOLIO = ? AND ETIQUETA_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {folio, etiquetaId};
			nombre = enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitEtiquetaDao|getNombre|:"+ex);
			ex.printStackTrace();
		}
		return nombre;
	}
	
	public int numEtiquetas(  String folio ) {
		int total = 0; 
		
		try{
			String comando = " SELECT COUNT(ETIQUETA_ID) AS TOTAL FROM ENOC.BIT_ETIQUETA WHERE FOLIO = ?";
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1) {
				total = enocJdbc.queryForObject(comando, Integer.class,parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitEtiquetaDao|numEtiquetas|:"+ex);
			ex.printStackTrace();
		}
		return total;
	}
	
	public String maximoReg( String folio) {
		
		String maximo = "1";
		
		try{
			String comando =  "SELECT COALESCE(MAX(ETIQUETA_ID)+1,1) AS MAXIMO FROM ENOC.BIT_ETIQUETA"
					+ " WHERE FOLIO = ?";
			Object[] parametros = new Object[] {folio};
 			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class,parametros);
			}
 			
		}catch(Exception ex){
			System.out.println("Error -  aca.bitacora.spring.BitEtiquetaDao|maximoReg|:"+ex);
		}
		return maximo;
	}

	
	public List<BitEtiqueta> listEtiquetas( String folio, String orden) {
		
		List<BitEtiqueta> lista = new ArrayList<BitEtiqueta>();
		
		try{
			String comando = " SELECT FOLIO, ETIQUETA_ID, AREA_ID, COMENTARIO, TO_CHAR(FECHA,'DD/MM/YYYY HH24:MI:SS') AS FECHA, USUARIO, TURNADO"
					+ " FROM ENOC.BIT_ETIQUETA"
					+ " WHERE FOLIO = ? "+orden;
			lista = enocJdbc.query(comando, new BitEtiquetaMapper(), folio);			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitEtiquetaDao|listEtiquetas|:"+ex);
		}
		return lista;
	}
	
	public List<BitEtiqueta> listEtiquetasAlumno( String codigoPersonal, String orden) {
		
		List<BitEtiqueta> lista = new ArrayList<BitEtiqueta>();
		
		try{
			String comando = "SELECT FOLIO, ETIQUETA_ID, AREA_ID, COMENTARIO, TO_CHAR(FECHA,'DD/MM/YYYY HH24:MI:SS') AS FECHA, USUARIO, TURNADO"
					+ " FROM ENOC.BIT_ETIQUETA"
					+ " WHERE FOLIO IN (SELECT FOLIO FROM ENOC.BIT_TRAMITE_ALUMNO WHERE CODIGO_PERSONAL = ?) "+orden;
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new BitEtiquetaMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitEtiquetaDao|listEtiquetasAlumno|:"+ex);
		}
		return lista;
	}	

	public HashMap<String, String> mapaCuentaEtiquetas( String codigoPersonal) {
		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();
		
		try{
			String comando = " SELECT FOLIO AS LLAVE, COUNT(*) AS VALOR FROM ENOC.BIT_ETIQUETA"
					+ " WHERE FOLIO IN (SELECT FOLIO FROM BIT_TRAMITE_ALUMNO WHERE CODIGO_PERSONAL = ?)"
					+ " GROUP BY FOLIO";		
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new aca.MapaMapper(),parametros);
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitEtiquetaDao|mapaCuentaEtiquetas|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaCuentaEtiquetas() {
		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();
		
		try{
			String comando = " SELECT FOLIO AS LLAVE, COUNT(*) AS VALOR FROM ENOC.BIT_ETIQUETA GROUP BY FOLIO";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitEtiquetaDao|mapaCuentaEtiquetas|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaCuentaEtiquetasPorArea( String areaId ) {
		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT FOLIO AS LLAVE, COUNT(*) AS VALOR FROM ENOC.BIT_ETIQUETA WHERE AREA_ID = ? GROUP BY FOLIO";
			Object[] parametros = new Object[] {areaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(),parametros);
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitEtiquetaDao|mapaCuentaEtiquetasPorArea|:"+ex);
		}
		return mapa;
	}
	
}
