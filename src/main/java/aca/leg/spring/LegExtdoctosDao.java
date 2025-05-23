package aca.leg.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import aca.leg.spring.LegExtdoctosDao;

@Component
public class LegExtdoctosDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(LegExtdoctos legExtdoc){
		boolean ok = false;

		try{
			String comando = "INSERT INTO ENOC.LEG_EXTDOCTOS(CODIGO, IDDOCUMENTO, FECHA_VENCE, NUM_DOCTO, FECHA, FECHA_TRAMITE, ESTADO) " +
				" VALUES( ?, TO_NUMBER(?,'99'), TO_DATE(?,'DD/MM/YYYY'), ?, " +
				" TO_DATE(?,'DD/MM/YYYY')," +
				" TO_DATE(?,'DD/MM/YYYY')," +
				" TO_NUMBER(?,'99') )";
				
			Object[] parametros = new Object[] {
					legExtdoc.getCodigo(),legExtdoc.getIdDocumento(),legExtdoc.getFechaVence(),legExtdoc.getNumDocto(),legExtdoc.getFecha(),legExtdoc.getFechaTramite(),
					legExtdoc.getEstado()
			};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.spring.LegExtdoctosDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg(LegExtdoctos legExtdoc ){
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.LEG_EXTDOCTOS"+ 
				" SET FECHA_VENCE = TO_DATE(?,'DD/MM/YYYY')," +
				" NUM_DOCTO = ?," +
				" FECHA = TO_DATE(?,'DD/MM/YYYY')," +
				" FECHA_TRAMITE = TO_DATE(?,'DD/MM/YYYY')," +
				" ESTADO = TO_NUMBER(?,'99')"+
				" WHERE CODIGO = ? " +
				" AND IDDOCUMENTO = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {
					legExtdoc.getFechaVence(),
					legExtdoc.getNumDocto(),
					legExtdoc.getFecha(),
					legExtdoc.getFechaTramite(),
					legExtdoc.getEstado(),
					legExtdoc.getCodigo(),
					legExtdoc.getIdDocumento()
			}; 	
			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
 			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.spring.LegExtdoctosDao|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean deleteReg(String codigo, String idDocumento ){
		boolean ok = false;

		try{
			String comando = "DELETE FROM ENOC.LEG_EXTDOCTOS "+ 
				"WHERE CODIGO = ? AND IDDOCUMENTO = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {codigo,idDocumento};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.spring.LegExtdoctosDao|deleteReg|:"+ex);			
		}

		return ok;
	}
	
	public LegExtdoctos mapeaRegId(String codigo, String idDocumento) {
		LegExtdoctos legExtdoc = new LegExtdoctos();

		try{
			String comando = "SELECT CODIGO, IDDOCUMENTO, TO_CHAR(FECHA_VENCE,'DD/MM/YYYY') AS FECHA_VENCE,"
					+ " NUM_DOCTO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, TO_CHAR(FECHA_TRAMITE,'DD/MM/YYYY') AS FECHA_TRAMITE, ESTADO"
					+ " FROM ENOC.LEG_EXTDOCTOS"
					+ " WHERE CODIGO = ?"
					+ " AND IDDOCUMENTO= TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {codigo, idDocumento};
			legExtdoc = enocJdbc.queryForObject(comando, new LegExtdoctosMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.spring.LegExtdoctosDao|mapeaRegId|:"+ex);
		}

		return legExtdoc;
	}
	
	public boolean existeReg(String codigo, String idDocumento) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.LEG_EXTDOCTOS WHERE  CODIGO= ? " + 
					"AND IDDOCUMENTO= TO_NUMBER(?,'99') ";

			Object[] parametros = new Object[] {codigo,idDocumento};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.spring.LegExtdoctosDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String getFechaVenceFM3(String codigoPersonal) {
		String 	fecha 	= "00/00/0000";
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.LEG_EXTDOCTOS WHERE CODIGO = ? AND IDDOCUMENTO = TO_NUMBER(?,'99')";			
			Object[] parametros = new Object[] {codigoPersonal,"2"};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>= 1){
				comando = " SELECT TO_CHAR(FECHA_VENCE,'DD/MM/YYYY') AS FECHA_VENCE FROM ENOC.LEG_EXTDOCTOS "
						+ " WHERE CODIGO = ? AND IDDOCUMENTO = TO_NUMBER(?,'99') ";
				fecha = enocJdbc.queryForObject(comando, String.class, parametros);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.leg.spring.LegExtdoctosDao|getFechaVenceFM3|:"+ex);
		}		
		return fecha;
	}
	
	public String getFechaVenceDocumento(String codigoPersonal, String documento){
		String 			fecha 	= "00/00/0000";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.LEG_EXTDOCTOS WHERE CODIGO = ? AND IDDOCUMENTO = TO_NUMBER(?,'99')";				
			Object[] parametros = new Object[] {codigoPersonal, documento};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>= 1){
				comando = "SELECT TO_CHAR(FECHA_VENCE,'DD/MM/YYYY') AS FECHA_VENCE FROM ENOC.LEG_EXTDOCTOS WHERE CODIGO = ? AND IDDOCUMENTO = TO_NUMBER(?,'99')";
				fecha = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.spring.LegExtdoctosDao|getFechaVenceDocumento|:"+ex);
		}
		
		return fecha;
	}
	
	public String getFechaVencePasaporte(String codigoPersonal) {
		String 	fecha 	= "00/00/0000";
		
		try{
			String comando = "SELECT TO_CHAR(FECHA_VENCE,'DD/MM/YYYY') AS FECHA_VENCE FROM ENOC.LEG_EXTDOCTOS" + 
				" WHERE  CODIGO = ? AND IDDOCUMENTO = TO_NUMBER(?,'99') ";
			
			Object[] parametros = new Object[] {codigoPersonal,"3"};
			fecha = enocJdbc.queryForObject(comando, String.class, parametros);
			
			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.spring.LegExtdoctosDao|getFechaVencePasaporte|:"+ex);
		}
		
		return fecha;
	}
	
	public List<LegExtdoctos> getLista(String codigoPersonal, String orden) {
		List<LegExtdoctos> lisExdoc 	= new ArrayList<LegExtdoctos>();
		
		try{
			String comando = "SELECT CODIGO, COALESCE(IDDOCUMENTO,0) AS IDDOCUMENTO," +
					" TO_CHAR(FECHA_VENCE,'DD/MM/YYYY') AS FECHA_VENCE," +
					" NUM_DOCTO," +
					" TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA," +
					" TO_CHAR(FECHA_TRAMITE,'DD/MM/YYYY') AS FECHA_TRAMITE," +
					" ESTADO" +
					" FROM ENOC.LEG_EXTDOCTOS" + 
					" WHERE CODIGO = ? "+orden;			
			lisExdoc = enocJdbc.query(comando, new LegExtdoctosMapper(), codigoPersonal);			
		}catch (Exception ex){
			System.out.println("Error - aca.leg.spring.LegExtdoctosDao|getLista|:"+ex);
		}
		
		return lisExdoc;
	}
	
	public HashMap<String, String> mapaEstadoDocumento( String codigoPersonal) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT COALESCE(IDDOCUMENTO,0) AS LLAVE, ESTADO AS VALOR" +
					" FROM ENOC.LEG_EXTDOCTOS" + 
					" WHERE CODIGO = ?";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), codigoPersonal);			
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.spring.LegExtdoctosDao|mapaEstadoDocumento|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, String> mapaExtDocumento(String codigoPersonal) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT COALESCE(IDDOCUMENTO,0) AS LLAVE, COALESCE(IDDOCUMENTO,0) AS VALOR FROM ENOC.LEG_EXTDOCTOS WHERE CODIGO = ?";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), codigoPersonal);			
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.spring.LegExtdoctosDao|mapaExtDocumento|:"+ex);
		}		
		return mapa;
	}
	
	public List<LegExtdoctos> lisAlumno(String codigoPersonal) {
		List<LegExtdoctos> lista 	= new ArrayList<LegExtdoctos>();
		
		try{
			String comando = "SELECT CODIGO, IDDOCUMENTO, TO_CHAR(FECHA_VENCE,'DD/MM/YYYY') AS FECHA_VENCE, NUM_DOCTO,"
					+ " TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA,"
					+ " TO_CHAR(FECHA_TRAMITE,'DD/MM/YYYY') AS FECHA_TRAMITE,"
					+ " ESTADO"
					+ " FROM ENOC.LEG_EXTDOCTOS"
					+ " WHERE CODIGO = ?";			
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new LegExtdoctosMapper(), parametros);
			
		}catch (Exception ex){
			System.out.println("Error - aca.leg.spring.LegExtdoctosDao|listaDocVigentes|:"+ex);
		}
		
		return lista;
	}
	
	public List<LegExtdoctos> listaDocVigentes(String codigoPersonal) {
		List<LegExtdoctos> lista 	= new ArrayList<LegExtdoctos>();
		
		try{
			String comando = "SELECT CODIGO, IDDOCUMENTO, TO_CHAR(FECHA_VENCE,'DD/MM/YYYY') AS FECHA_VENCE, NUM_DOCTO,"
					+ " TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA,"
					+ " TO_CHAR(FECHA_TRAMITE,'DD/MM/YYYY') AS FECHA_TRAMITE,"
					+ " ESTADO"
					+ " FROM ENOC.LEG_EXTDOCTOS"
					+ " WHERE CODIGO = ?"
					+ " AND ENOC.LEG_EXTDOCTOS.FECHA_VENCE >= SYSDATE";
			
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new LegExtdoctosMapper(), parametros);
			
		}catch (Exception ex){
			System.out.println("Error - aca.leg.spring.LegExtdoctosDao|listaDocVigentes|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String, String> mapaDocumentosExtranjeros( String cargaId ) {
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CODIGO||IDDOCUMENTO AS LLAVE, TO_CHAR(FECHA_VENCE,'YYYY-MM-DD') AS VALOR FROM ENOC.LEG_EXTDOCTOS"
					+ " WHERE CODIGO IN (SELECT CODIGO_PERSONAL FROM ESTADISTICA WHERE CARGA_ID = ?)";
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);	
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.spring.LegDocumentoDao|mapaDocumentosExtranjeros|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaDocExtranjeros( ) {
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CODIGO||IDDOCUMENTO AS LLAVE, TO_CHAR(FECHA_VENCE,'YYYY/MM/DD') AS VALOR FROM ENOC.LEG_EXTDOCTOS"
					+ " WHERE CODIGO IN (SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS)";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());	
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.spring.LegDocumentoDao|mapaDocumentosExtranjeros|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaDocumentosExtranjeros( ) {
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CODIGO||IDDOCUMENTO AS LLAVE, TO_CHAR(FECHA_VENCE,'YYYY-MM-DD') AS VALOR FROM ENOC.LEG_EXTDOCTOS"
					+ " WHERE CODIGO IN (SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS)";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());	
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.spring.LegDocumentoDao|mapaDocumentosExtranjeros|:"+ex);
		}
		
		return mapa;
	}
	

}
