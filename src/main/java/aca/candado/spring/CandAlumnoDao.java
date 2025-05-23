package aca.candado.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CandAlumnoDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CandAlumno candado ) {
		boolean ok = false;
		try{
			String comando = "INSERT INTO ENOC.CAND_ALUMNO"
					+ "( CODIGO_PERSONAL, "
					+ "FOLIO, "
					+ "CANDADO_ID, "
					+ "F_CREADO, "
					+ "US_ALTA, "
					+ "COMENTARIO, "
					+ "ESTADO, "
					+ "TIPO_ID) "
					+ "VALUES(?,TO_NUMBER(?,'999'),?, "
					+ "TO_DATE(?,'DD/MM/YYYY'),"+ "?,?,?,?)";		
							
			Object[] parametros = new Object[] {candado.getCodigoPersonal(),candado.getFolio(),candado.getCandadoId(),candado.getfCreado(),candado.getUsAlta(),candado.getComentario(),candado.getEstado(),candado.getTipoId()};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandAlumnoDao|insertReg|:"+ex);
		}
		return ok;
	}
	
	public boolean updateReg( CandAlumno candado ) {
		boolean ok = false;
		try{
			String comando = "UPDATE ENOC.CAND_ALUMNO "
					+ "SET CANDADO_ID = ?, "
					+ "F_CREADO = TO_DATE(?,'DD/MM/YYYY'), "
					+ "F_BORRADO = TO_DATE(?,'DD/MM/YYYY'), "
					+ "US_ALTA = ?, "
					+ "US_BAJA= ?, "
					+ "COMENTARIO = ?, "
					+ "ESTADO = ?, "
					+ "TIPO_ID = ? "
					+ "WHERE CODIGO_PERSONAL = ? "
					+ "AND FOLIO = TO_NUMBER(?,'999')";			
			Object[] parametros = new Object[] {candado.getCandadoId(),candado.getfCreado(),candado.getfBorrado(),candado.getUsAlta(),candado.getUsBaja(),candado.getComentario(),candado.getEstado(),candado.getTipoId(),candado.getCodigoPersonal(),candado.getFolio()};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandAlumnoDao|updateReg|:"+ex); 
		}			
		return ok;
	}
	
	public boolean deleteReg( String codigoPersonal, String folio ) {
		boolean ok = false;
		try{
			String comando = "DELETE FROM ENOC.CAND_ALUMNO WHERE CODIGO_PERSONAL = ? AND FOLIO = ?";			
			Object[] parametros = new Object[] {codigoPersonal,folio};		
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandAlumnoDao|deleteReg|:"+ex);
		}
		return ok;
	}
	
	public boolean deleteRegExtranjero( String codigoPersonal, String usAlta, String candadoId, String comentario ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CAND_ALUMNO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND US_ALTA = ?"
					+ " AND CANDADO_ID = ?"
					+ " AND COMENTARIO = ?";
			
			Object[] parametros = new Object[] {codigoPersonal,usAlta,candadoId,comentario};		
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandAlumnoDao|deleteRegExtranjero|:"+ex);
		}
		return ok;
	}
	
	public boolean deleteCandadoActivo( String codigoPersonal, String candadoId, String estado) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CAND_ALUMNO"
					+ " WHERE CODIGO_PERSONAL = ?"					
					+ " AND CANDADO_ID = ?"
					+ " AND ESTADO = ?";
			
			Object[] parametros = new Object[] {codigoPersonal,candadoId,estado};		
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandAlumnoDao|deleteRegExtranjero|:"+ex);
		}
		return ok;
	}
	
	public CandAlumno mapeaRegId( String codigoPersonal, String folio) {
		
		CandAlumno candado = new CandAlumno();				
		try{
			String comando = "SELECT"
					+ " CODIGO_PERSONAL, FOLIO, CANDADO_ID,"
					+ " TO_CHAR(F_CREADO,'DD/MM/YYYY') AS F_CREADO,"
					+ " TO_CHAR(F_BORRADO,'DD/MM/YYYY') AS F_BORRADO,"
					+ " US_ALTA,"
					+ " US_BAJA,"
					+ " COMENTARIO,"
					+ " ESTADO,"
					+ " TIPO_ID"
					+ " FROM ENOC.CAND_ALUMNO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND FOLIO = TO_NUMBER(?,'999')";
							
			Object[] parametros = new Object[] {codigoPersonal,folio};
			candado = enocJdbc.queryForObject(comando, new CandAlumnoMapper(),parametros);		
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandAlumnoDao|mapeaRegId|:"+ex);
		}
		return candado;
	}
	
	public boolean existeReg( String codigoPersonal, String folio) {
		boolean ok 	= false;
				
		try{
			String comando = "SELECT COUNT (*) FROM ENOC.CAND_ALUMNO WHERE CODIGO_PERSONAL = ? AND FOLIO = ? ";
			
			Object[] parametros = new Object[] {codigoPersonal,folio};		
			
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandAlumnoDao|existeReg|:"+ex);
		}		
		return ok;
	}	
	
	public String maximoReg( String codigoPersonal) {		
		String maximo 			= "1";		
		try{
			String comando = "SELECT COALESCE(MAX(FOLIO)+1,1) AS MAXIMO FROM ENOC.CAND_ALUMNO WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			maximo = enocJdbc.queryForObject(comando, String.class,parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandAlumnoDao|maximoReg|:"+ex);
		}
		return maximo;
	}
	
	public boolean conCandados( String codigoPersonal) {		
		
		boolean bOk 			= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAND_ALUMNO WHERE CODIGO_PERSONAL = ? AND ESTADO = 'A'";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				bOk = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandAlumnoDao|conCandados|:"+ex);
		}		
		return bOk;
	}	
	
	public boolean tieneCandado( String codigoPersonal, String candadoId) {
		
		boolean bOk 			= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAND_ALUMNO "+ 
				"WHERE CODIGO_PERSONAL = ?" +
				" AND CANDADO_ID = ?" +
				" AND ESTADO = 'A'";
			Object[] parametros = new Object[] {codigoPersonal, candadoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				bOk = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandAlumnoDao|conCandados|:"+ex);
		}
		
		return bOk;
	}
	
	public boolean removerCand( CandAlumno candado ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CAND_ALUMNO "+
				" SET F_BORRADO = TO_DATE(?,'DD/MM/YYYY'), "+
				" US_BAJA= ?, "+
				" ESTADO = ? "+
				" WHERE CODIGO_PERSONAL = ? "+
				" AND CANDADO_ID = ?";			
			Object[] parametros = new Object[] {candado.getfBorrado(),candado.getUsBaja(),candado.getEstado(),candado.getCodigoPersonal(),candado.getCandadoId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandAlumnoDao|updateReg|:"+ex); 
		}
		
		return ok;
	}
		
	public List<CandAlumno> getListAll( String orden ) {
		
		List<CandAlumno> lista 	= new ArrayList<CandAlumno>();		
		try{
			String comando = "SELECT "+
			"CODIGO_PERSONAL, FOLIO, CANDADO_ID,  "+
			"TO_DATE(F_CREADO,'DD/MM/YYYY') AS F_CREADO, "+
			"TO_DATE(F_BORRADO,'DD/MM/YYYY') AS F_BORRADO, "+
			"US_ALTA, "+
			"US_BAJA, "+
			"COMENTARIO, "+
			"ESTADO, "+
			"TIPO_ID "+
			"FROM ENOC.CAND_ALUMNO "+orden;		
			lista = enocJdbc.query(comando, new CandAlumnoMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandAlumnoDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public List<CandAlumno> getLista( String codigoPersonal, String orden ) {
		
		List<CandAlumno> lista 	= new ArrayList<CandAlumno>();
		
		try{
			String comando = "SELECT "+
			"CODIGO_PERSONAL, FOLIO, CANDADO_ID,  "+
			"TO_CHAR(F_CREADO,'DD/MM/YYYY') AS F_CREADO, "+
			"TO_CHAR(F_BORRADO,'DD/MM/YYYY') AS F_BORRADO, "+
			"US_ALTA, "+
			"US_BAJA, "+
			"COMENTARIO, "+
			"ESTADO, "+
			"TIPO_ID "+
			"FROM ENOC.CAND_ALUMNO "+ 
			"WHERE CODIGO_PERSONAL = ? "+orden;	
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new CandAlumnoMapper(),parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandAlumnoDao|getLista|:"+ex);
		}
		
		return lista;
	}
	
	public List<CandAlumno> getLista( String codigoPersonal, String estado, String orden ) {
		
		List<CandAlumno> lista 	= new ArrayList<CandAlumno>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, FOLIO, CANDADO_ID, TO_CHAR(F_CREADO,'DD/MM/YYYY') AS F_CREADO,"
			+ " TO_CHAR(F_BORRADO,'DD/MM/YYYY') AS F_BORRADO, US_ALTA, US_BAJA, COMENTARIO, ESTADO, TIPO_ID"
			+ " FROM ENOC.CAND_ALUMNO" 
			+ " WHERE CODIGO_PERSONAL = ?"
			+ " AND ESTADO = ? "+orden;
			Object[] parametros = new Object[] {codigoPersonal, estado};
			lista = enocJdbc.query(comando, new CandAlumnoMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandAlumnoDao|getLista|:"+ex);
		}				
		
		return lista;
	}
	
	public List<CandAlumno> getListCandadoFecha( String fecha, String estados, String orden ) {
		
		List<CandAlumno> lista 	= new ArrayList<CandAlumno>();
				
		try{
			String comando = " SELECT CODIGO_PERSONAL, FOLIO, CANDADO_ID, TO_CHAR(F_CREADO,'DD/MM/YYYY') AS F_CREADO,"
					+ " TO_CHAR(F_BORRADO,'DD/MM/YYYY') AS F_BORRADO, US_ALTA, US_BAJA, COMENTARIO, ESTADO, TIPO_ID"
					+ " FROM ENOC.CAND_ALUMNO"					
					+ " WHERE ESTADO IN("+estados+")"
					+ " AND F_CREADO > TO_DATE(?,'DD/MM/YYYY') "+orden;
			Object[] parametros = new Object[] {fecha};
			lista = enocJdbc.query(comando, new CandAlumnoMapper(),parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandAlumnoDao|getListCandado|:"+ex);
		}
		
		return lista;
	}	
	
	public List<CandAlumno> getListCandado( String periodoId, String tipoId, String orden ) {
		
		List<CandAlumno> lista 	= new ArrayList<CandAlumno>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, FOLIO, CANDADO_ID," +
			" TO_CHAR(F_CREADO,'DD/MM/YYYY') AS F_CREADO," +
			" TO_CHAR(F_BORRADO,'DD/MM/YYYY') AS F_BORRADO," +
			" US_ALTA, US_BAJA, COMENTARIO, ESTADO, TIPO_ID" +
			" FROM ENOC.CAND_ALUMNO" + 
			" WHERE TIPO_ID = ?" +
			" AND ESTADO = 'A' " +
			" AND ? IN(SELECT PERIODO_ID FROM ENOC.CAT_PERIODO WHERE CAND_ALUMNO.F_CREADO BETWEEN F_INICIO AND F_FINAL ) " + orden;
			Object[] parametros = new Object[] {tipoId, periodoId};
			lista = enocJdbc.query(comando, new CandAlumnoMapper(),parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandAlumnoDao|getListCand|:"+ex);
		}
		
		return lista;
	}
	
	public List<CandAlumno> getListCand( String codigoPersonal, String tipoId, String orden ) {
		
		List<CandAlumno> lista 	= new ArrayList<CandAlumno>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, FOLIO, CANDADO_ID," +
			" TO_CHAR(F_CREADO,'DD/MM/YYYY') AS F_CREADO," +
			" TO_CHAR(F_BORRADO,'DD/MM/YYYY') AS F_BORRADO," +
			" US_ALTA, US_BAJA, COMENTARIO, ESTADO, TIPO_ID" +
			" FROM ENOC.CAND_ALUMNO WHERE CODIGO_PERSONAL = ?" + 
			" AND TIPO_ID = ? "+orden;
			Object[] parametros = new Object[] {codigoPersonal, tipoId};
			lista = enocJdbc.query(comando, new CandAlumnoMapper(),parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandAlumnoDao|getListCand|:"+ex);
		}
		
		return lista;
	}
	
	public List<CandAlumno> getListCandAlumno( String codigoPersonal, String tipoId, String estado, String orden ) {
		
		List<CandAlumno> lista 	= new ArrayList<CandAlumno>();
		
		try{
			String comando = "SELECT"
					+ " CODIGO_PERSONAL, FOLIO, CANDADO_ID,"
					+ " TO_CHAR(F_CREADO,'DD/MM/YYYY') AS F_CREADO,"
					+ " TO_CHAR(F_BORRADO,'DD/MM/YYYY') AS F_BORRADO,"
					+ " US_ALTA, US_BAJA, COMENTARIO, ESTADO, TIPO_ID"
					+ " FROM ENOC.CAND_ALUMNO "
					+ " WHERE CODIGO_PERSONAL = ?" 
					+ " AND TIPO_ID = ?"
					+ " AND ESTADO = ? "+orden;
			Object[] parametros = new Object[] {codigoPersonal, tipoId, estado};
			lista = enocJdbc.query(comando, new CandAlumnoMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandAlumnoDao|getListCand|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String, CandAlumno> mapAlumnosConCandado( String candadoId, String estado ) {
		List<CandAlumno> list 				= new ArrayList<CandAlumno>();
		HashMap<String, CandAlumno> map = new HashMap<String, CandAlumno>();
		
		try{
			String comando = "SELECT"
					+ " CODIGO_PERSONAL, FOLIO, CANDADO_ID,"
					+ " TO_CHAR(F_CREADO,'DD/MM/YYYY') AS F_CREADO,"
					+ " TO_CHAR(F_BORRADO,'DD/MM/YYYY') AS F_BORRADO,"
					+ " US_ALTA, US_BAJA, COMENTARIO, ESTADO, TIPO_ID"
					+ " FROM ENOC.CAND_ALUMNO "
					+ " WHERE CANDADO_ID = ? "
					+ " AND ESTADO = ? ";
			
			Object[] parametros = new Object[] {candadoId, estado};
			
			list = enocJdbc.query(comando,  new CandAlumnoMapper(),parametros);
			for (CandAlumno candado : list ) {
				map.put(candado.getCodigoPersonal(), candado );
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapAlumnosConCandado|:"+ex);
		}
		
		return map;
	}
	
	public HashMap<String,String> mapaAlumnosConCandado( String cargaId) {
		List<aca.Mapa> list 				= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa = new HashMap<String, String>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL||TIPO_ID AS LLAVE, COUNT(*) AS VALOR FROM CAND_ALUMNO"
				+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ESTADISTICA WHERE CARGA_ID = ?)"
				+ " AND ESTADO = 'A'"
				+ " GROUP BY CODIGO_PERSONAL||TIPO_ID";
			Object[] parametros = new Object[] {cargaId};
			
			list = enocJdbc.query(comando, new aca.MapaMapper(),parametros);
			for (aca.Mapa map : list ) {
				mapa.put(map.getLlave(), map.getValor());
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapAlumnosConCandado|:"+ex);
		}
		
		return mapa;
	}
	
	public boolean validaCandadoDeudor( String codigoPersonal) {
		
		boolean bOk 			= false;		
		int folio=0;
		
		try{
			//Busca el saldo del alumno
			String comando = "SELECT COALESCE(SVENCIDO,0) AS DEUDA FROM NOE.SALDOS_ALUMNOS WHERE MATRICULA = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			double saldo = 0;
			if (enocJdbc.queryForObject(comando,Double.class,parametros) != null) {
				saldo = Double.valueOf(enocJdbc.queryForObject(comando,String.class,parametros));
			}	
						
			// Quita candado en caso de que lo tenga			
			if (saldo >= -3000){
				comando = "SELECT COLESCE(FOLIO,0) AS FOLIO FROM ENOC.CAND_ALUMNO WHERE CODIGO_PERSONAL = ? AND ESTADO = 'A' AND CANDADO_ID = '0401'";
				if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1) {
					folio = enocJdbc.queryForObject(comando,Integer.class,parametros);
				}				
				comando = "DELETE CAND_ALUMNO WHERE CODIGO_PERSONAL = ? AND FOLIO = ?";
				Object[] deleteParametros = new Object[] {codigoPersonal, folio};
				if (enocJdbc.update(comando, deleteParametros) >= 1) {
					bOk = true;
				}				
			}else{
				comando = "SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.INSCRITOS WHERE CODIGO_PERSONAL = ?";
				if (enocJdbc.update(comando,parametros) >= 1){
					
					comando = "SELECT FOLIO FROM ENOC.CAND_ALUMNO" + 
					" WHERE CODIGO_PERSONAL = ? AND CANDADO_ID = '0401'" +
					" AND (ESTADO = 'A' OR (ESTADO = 'I' AND MONTHS_BETWEEN(TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY'), F_BORRADO)<2))";
					if (enocJdbc.queryForObject(comando,Integer.class,parametros)==null) {						
						folio = enocJdbc.queryForObject(comando,Integer.class,parametros);
						comando = "INSERT INTO ENOC.CAND_ALUMNO(CODIGO_PERSONAL, FOLIO, CANDADO_ID, F_CREADO, US_ALTA, COMENTARIO, ESTADO)" +
								" VALUES(?,?,'0401',TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY')," +
								" '9800260','DEUDA MAYOR DE $3,000','A')";
						Object[] insertParametros = new Object[] {codigoPersonal, folio};
						if (enocJdbc.update(comando, insertParametros) >= 1){
							bOk = true;
						}
					}
				}				
			}
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandAlumnoDao|validaCandadoDeudor|:"+ex);
		}
		
		return bOk;
	}
	
}