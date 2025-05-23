package aca.carga.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CargaArchivoDao {
	
	/**
	 * @param conn
	 * @return boolean
	 */
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CargaArchivo archivo ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CARGA_GRUPO_ARCHIVO_GRUPO_ARCHIVO"+ 
				"(CURSO_CARGA_ID, FOLIO, FECHA, NOMBRE, EVALUACION_ID, ACTIVIDAD_ID, "+
				"USUARIO_ORIGEN, USUARIO_DESTINO, COMENTARIO, RUTA, RUTA) "+
				"VALUES( ?,"+
				"TO_NUMBER(?,'99'), "+
				"TO_DATE(?,'DD/MM/YYYY'), "+
				"?, "+
				"TO_NUMBER(?,'99'), "+
				"TO_NUMBER(?,'99'), "+
				"?,?,?,?,?";
			Object[] parametros = new Object[] {archivo.getCursoCargaId(), archivo.getFolio(), archivo.getFecha(),
			archivo.getNombre(), archivo.getEvaluacionId(), archivo.getActividadId(), archivo.getUsuarioOrigen(),
			archivo.getUsuarioDestino(), archivo.getComentario(), archivo.getRuta(), archivo.getEstado()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaArchivoDao|insertReg|:"+ex);			
		}		
		return ok;
	}	
	
	public boolean updateReg( CargaArchivo archivo ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CARGA_GRUPO_ARCHIVO "+ 
				"SET FOLIO 				= ?, "+
				"FECHA 					= TO_DATE(?,'DD/MM/YYYY'), "+
				"NOMRE 					= ?, "+
				"EVALUCION_ID 			= TO_NUMBER(?,'99'), "+
				"ACTIVIDAD_ID 			= TO_NUMBER(?,'99'), "+
				"USUARIO_ORIGEN 		= ?, "+
				"USUARIO_DESTINO 		= ?, "+				
				"COMENTARIO 			= ?, "+
				"RUTA 					= ?, " +
				"ESTADO 				= ?," +
				"WHERE CURSO_CARGA_ID 	= ? ";
			Object[] parametros = new Object[] {archivo.getFolio(), archivo.getFecha(), archivo.getNombre(),
			archivo.getEvaluacionId(), archivo.getActividadId(), archivo.getUsuarioOrigen(), archivo.getUsuarioDestino(),
			archivo.getComentario(), archivo.getRuta(), archivo.getEstado(), archivo.getCursoCargaId()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaArchivoDao|updateReg|:"+ex);		 
		}
		return ok;
	}
	
	public boolean deleteReg( String cursoCargaId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CARGA_GRUPO_ARCHIVO "+ 
				"WHERE CURSO_CARGA_ID = ? ";
			Object[] parametros = new Object[] {cursoCargaId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaArchivoDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public CargaArchivo mapeaRegId(  String cursoCargaId ) {
		
		CargaArchivo objeto = new CargaArchivo();
		
		try{
			String comando = "SELECT CURSO_CARGA_ID, FOLIO, "+
				"TO_CHAR(FECHA,'DD/MM/YYYY') FECHA, NOMRE, EVALUCION_ID, "+
				"ACTIVIDAD_ID, USUARIO_ORIGEN,USUARIO_DESTINO, COMENTARIO, RUTA, ESTADO "+
				"FROM ENOC.CARGA_GRUPO_ARCHIVO WHERE CURSO_CARGA_ID = ? ";
			Object[] parametros = new Object[] {cursoCargaId};
			objeto = enocJdbc.queryForObject(comando, new CargaArchivoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaArchivoDao|mapeaRegId|:"+ex);
		}
		return objeto;
	}
	
	public boolean existeReg( String cursoCargaId) {
		boolean 			ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_GRUPO_ARCHIVO "+ 
				"WHERE CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaArchivoDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public List<CargaArchivo> getListAll( String orden ) {
		
		List<CargaArchivo> lista = new ArrayList<CargaArchivo>();
		
		try{
			String comando = "SELECT * FROM ENOC.CARGA_GRUPO_ARCHIVO "+orden;
			lista = enocJdbc.query(comando, new CargaArchivoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaArchivoDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public List<CargaArchivo> getList( String nombre ) {
		
		List<CargaArchivo> lista = new ArrayList<CargaArchivo>();
		
		try{
			String comando = " SELECT * FROM ENOC.CARGA_ENLINEA WHERE nombre = nombre ";
			lista = enocJdbc.query(comando, new CargaArchivoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaArchivoDao|getList|:"+ex);
		}		
		return lista;
	}
}