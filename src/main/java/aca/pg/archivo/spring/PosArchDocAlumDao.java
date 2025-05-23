package aca.pg.archivo.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PosArchDocAlumDao {
	
	@Autowired
	@Qualifier("jdbcArchivo")
	private JdbcTemplate archivoJdbc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(PosArchDocAlum arch) {
		boolean ok 				= false;
		try{
			String comando = "INSERT INTO ARCH_DOCALUM (MATRICULA,IDDOCUMENTO,IMAGEN,HOJA,FUENTE,TIPO,ORIGEN,F_INSERT,F_UPDATE,USUARIO, IMAGEN_BYTE, ESTADO)"
					+ " VALUES(?, TO_NUMBER(?, '999'), ?,"
					+ " TO_NUMBER(?,'99'), ?, ?, ?,"
					+ " TO_DATE(?,'DD/MM/YYYY'),"
					+ " TO_DATE(?,'DD/MM/YYYY'), ?, ?, ?)";
			
			Object[] parametros = new Object[] {
				arch.getMatricula(), arch.getIddocumento(), arch.getImagen(), arch.getHoja(), arch.getFuente(),
				arch.getTipo(), arch.getOrigen(), arch.getFinsert(), arch.getFupdate(), arch.getUsuario(), arch.getImagenByte(), arch.getEstado()
	 		};
			if (archivoJdbc.update(comando,parametros)==1){
				ok = true;
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.spring.PosArchDocAlumDao|insertReg|:"+ex);	
		}
		
		return ok;
	}
	
	public boolean updateReg(PosArchDocAlum arch) {
		boolean ok 				= false;
		try{
			String comando = "UPDATE ARCH_DOCALUM"
				+ " SET IMAGEN = ?,"
				+ " FUENTE = ?,"
				+ " TIPO = ?,"
				+ " ORIGEN = ?,"
				+ " F_INSERT = TO_DATE(?,'DD/MM/YYYY'),"
				+ " F_UPDATE = TO_DATE(?,'DD/MM/YYYY'),"
				+ " USUARIO = ?, "
				+ " IMAGEN_BYTE = ?,"
				+ " ESTADO = ?"				
				+ " WHERE MATRICULA = ?"
				+ " AND IDDOCUMENTO = TO_NUMBER(?, '999')"
				+ " AND HOJA = TO_NUMBER(?, '99')";			
			Object[] parametros = new Object[] {
				arch.getImagen(), arch.getFuente(), arch.getTipo(), arch.getOrigen(), arch.getFinsert(), arch.getFupdate(), 
				arch.getUsuario(), arch.getImagenByte(), arch.getEstado(), arch.getMatricula(), arch.getIddocumento(), arch.getHoja()
			};
			if (archivoJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.spring.PosArchDocAlumDao|updateReg|:"+ex);
		}
		
		return ok;
	}
		
	
	public boolean deleteReg( String matricula, String iddocumento, String hoja) {
		boolean ok = false;
		try{
		    String comando = "SELECT LO_UNLINK(IMAGEN) AS RESULTADO FROM ARCH_DOCALUM"+ 
	    		" WHERE MATRICULA = ?"+
	    		" AND IDDOCUMENTO = TO_NUMBER(?, '999')"+
	    		" AND HOJA = TO_NUMBER(?,'99')";
		    Object[] parametros = new Object[] {matricula, iddocumento, hoja};	
			if (archivoJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.spring.PosArchDocAlumDao|deleteReg(OID)|:"+ex);
		}
		
		if(ok){
			ok = false;
			try{
			    String comando = "DELETE FROM ARCH_DOCALUM"+ 
		    		" WHERE MATRICULA = ?"+
		    		" AND IDDOCUMENTO = ?"+
		    		" AND HOJA = ?";			    
			    Object[] parametros = new Object[] {matricula, iddocumento, hoja};	
				if (archivoJdbc.update(comando,parametros)==1){
					ok = true;
				}
			}catch(Exception ex){
				System.out.println("Error - aca.pg.archivo.spring.PosArchDocAlumDao|deleteReg(fila)|:"+ex);
			}
		}else{
			System.out.println("No se borro la imagen y tampoco se borro la fila en alumno");
		}
		return ok;
	}
	
	public boolean deleteRegDoc(String matricula, String iddocumento) {
		boolean ok = false;		
		try{
		    String comando = "DELETE FROM ARCH_DOCALUM WHERE MATRICULA = ? AND IDDOCUMENTO = TO_NUMBER(?,'999')";
		    Object[] parametros = new Object[] {matricula, iddocumento};	
		    if (archivoJdbc.update(comando,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.spring.PosArchDocAlumDao|deleteRegDoc(fila)|:"+ex);
		}
		
		return ok;
	}

	public boolean deleteRegDoc(String matricula, String iddocumento, String hoja) {
		boolean ok = false;		
		try{
		    String comando = "DELETE FROM ARCH_DOCALUM WHERE MATRICULA = ? AND IDDOCUMENTO = TO_NUMBER(?,'999') AND HOJA = TO_NUMBER(?,'99')";
		    Object[] parametros = new Object[] {matricula, iddocumento, hoja};	
		    if (archivoJdbc.update(comando,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.spring.PosArchDocAlumDao|deleteRegDoc(fila)|:"+ex);
		}
		
		return ok;
	}
	
	public PosArchDocAlum mapeaRegId(String matricula, String iddocumento, String hoja) {
		PosArchDocAlum arch = new PosArchDocAlum();
		try{ 
			String comando = "SELECT COUNT(*) FROM ARCH_DOCALUM WHERE MATRICULA = ? AND IDDOCUMENTO = TO_NUMBER(?,'999') AND HOJA = TO_NUMBER(?, '99')";			
			Object[] parametros = new Object[] {matricula, iddocumento, hoja};
			if (archivoJdbc.queryForObject(comando, Integer.class, parametros) >= 1 ){
				comando = "SELECT MATRICULA, IDDOCUMENTO, IMAGEN, HOJA, FUENTE, TIPO, ORIGEN," +
						" TO_CHAR(F_INSERT, 'DD/MM/YYYY') AS F_INSERT," +
						" TO_CHAR(F_UPDATE, 'DD/MM/YYYY') AS F_UPDATE, USUARIO, ESTADO, IMAGEN_BYTE" +
					    " FROM ARCH_DOCALUM" + 
						" WHERE MATRICULA = ?" +
						" AND IDDOCUMENTO = TO_NUMBER(?,'999')" +
						" AND HOJA = TO_NUMBER(?, '99')";
				arch = archivoJdbc.queryForObject(comando, new PosArchDocAlumMapperLargo(), parametros);
			}						
		}catch(Exception ex){ 			
 			System.out.println("Error - aca.pg.archivo.spring.PosArchDocAlumDao|mapeaRegId|:"+ex);
 		}		
		return arch;
	}
	
	
	public boolean existeReg(String matricula, String iddocumento, String hoja) {
 		boolean ok 		= false; 		
 		try{
 			String comando = "SELECT COUNT(*) FROM ARCH_DOCALUM"+ 
 				" WHERE MATRICULA = ? " +
 				" AND IDDOCUMENTO = TO_NUMBER(?,'999')" +
 				" AND HOJA = TO_NUMBER(?,'99')"; 			
 			Object[] parametros = new Object[] {matricula, iddocumento, hoja};
			if (archivoJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}	 			
 		}catch(Exception ex){ 			
 			System.out.println("Error - aca.pg.archivo.spring.PosArchDocAlumDao|existeReg|:"+ex);
 		} 		
 		return ok;
 	}
	
	public String getEstado(String matricula, String iddocumento, String hoja) {	
 		String estado = "X";
 		try{
 			String comando = "SELECT COUNT(*) FROM ARCH_DOCALUM WHERE MATRICULA = ? AND IDDOCUMENTO = TO_NUMBER(?,'999') AND HOJA = TO_NUMBER(?,'99')"; 			
 			Object[] parametros = new Object[] {matricula, iddocumento, hoja};
			if (archivoJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				comando = "SELECT ESTADO FROM ARCH_DOCALUM WHERE MATRICULA = ? AND IDDOCUMENTO = TO_NUMBER(?,'999') AND HOJA = TO_NUMBER(?,'99')";
				estado 	= archivoJdbc.queryForObject(comando, String.class, parametros);
			}	 			
 		}catch(Exception ex){ 			
 			System.out.println("Error - aca.pg.archivo.spring.PosArchDocAlumDao|getEstado|:"+ex);
 		} 		
 		return estado;
 	}
	
	public boolean existeImagenHoja(String matricula, String iddocumento, String hoja) {
 		boolean ok 		= false; 		
 		try{
 			String comando = "SELECT COUNT(*) FROM ARCH_DOCALUM"
 					+ " WHERE MATRICULA = ?"
 					+ " AND IDDOCUMENTO = TO_NUMBER(?,'999')"
 					+ " AND HOJA = TO_NUMBER(?,'99')"
 					+ " AND IMAGEN != 0"; 			
 			Object[] parametros = new Object[] {matricula, iddocumento, hoja};
			if (archivoJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}	 			
 		}catch(Exception ex){ 			
 			System.out.println("Error - aca.pg.archivo.spring.PosArchDocAlumDao|existeReg|:"+ex);
 		} 		
 		return ok;
 	}
	
	public boolean existeOID(String matricula, String iddocumento, String hoja) {
 		boolean ok 		= false; 		
 		try{
 			String comando = "SELECT COUNT(*) FROM ARCH_DOCALUM"
 					+ " WHERE MATRICULA = ?"
 					+ " AND IDDOCUMENTO = TO_NUMBER(?,'999')"
 					+ " AND HOJA = TO_NUMBER(?,'99')"
 					+ " AND MERGE_OID(IMAGEN) IS NULL";
 			Object[] parametros = new Object[] {matricula, iddocumento, hoja};
			if (archivoJdbc.queryForObject(comando, Integer.class, parametros) == 0){
				ok = true;
			}	 			
 		}catch(Exception ex){ 			
 			System.out.println("Error - aca.pg.archivo.spring.PosArchDocAlumDao|existeOID|:"+ex);
 		} 		
 		return ok;
 	}
	
	public String getImagenHoja(String matricula, String idDocumento, String hoja) {
 		String imagen			= "0"; 		
 		try{
 			String comando = "SELECT COALESCE(IMAGEN,0) AS IMAGEN FROM ARCH_DOCALUM"+ 
 				" WHERE MATRICULA = ? " +
 				" AND IDDOCUMENTO = TO_NUMBER(?,'999')" +
 				" AND HOJA = TO_NUMBER(?,'99')"; 				
			Object[] parametros = new Object[] {matricula, idDocumento, hoja};
			imagen = archivoJdbc.queryForObject(comando,String.class,parametros); 			
 		}catch(Exception ex){ 			
 			System.out.println("Error - aca.pg.archivo.spring.PosArchDocAlumDao|existeReg|:"+ex);
 		} 		
 		return imagen;
 	}	
	
	/* 
	 * Verifica si existen imagenes de un documento del alumno
	 */
	public boolean existeRegDoc(String matricula, String iddocumento) {
 		boolean ok 		= false; 		
 		try{
 			String comando = "SELECT COUNT(IMAGEN) FROM ARCH_DOCALUM"+ 
 				" WHERE MATRICULA = ? " +
 				" AND IDDOCUMENTO = TO_NUMBER(?,'999')"; 			
 			Object[] parametros = new Object[] {matricula, iddocumento};
			if (archivoJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}			
 		}catch(Exception ex){ 			
 			System.out.println("Error - aca.pg.archivo.spring.PosArchDocAlumDao|existeRegDoc|:"+ex);
 		} 		
 		return ok;
 	}
	
	public boolean unlinkImagen(String matricula, String iddocumento, String hoja) {
		boolean ok = false;		
		try{
			int imagen = 0;
			String comando = "SELECT COUNT(*) FROM ARCH_DOCALUM WHERE MATRICULA = ? AND IDDOCUMENTO = TO_NUMBER(?, '999') AND HOJA = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {matricula, iddocumento, hoja};
			if (archivoJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				//Busca la imagen
				comando = "SELECT COALESCE(IMAGEN,0) FROM ARCH_DOCALUM WHERE MATRICULA = ? AND IDDOCUMENTO = TO_NUMBER(?, '999') AND HOJA = TO_NUMBER(?,'99')";
				imagen = archivoJdbc.queryForObject(comando, Integer.class, parametros);
				
				//Verifica si existe
				comando = "SELECT COUNT(LOID) FROM PG_CATALOG.PG_LARGEOBJECT WHERE LOID = ?";
				Object[] parImagen = new Object[] {imagen};
				if (archivoJdbc.queryForObject(comando, Integer.class, parImagen) >= 1){					
					// Borra la imagen
					comando = "SELECT LO_UNLINK("+imagen+")";		    
					archivoJdbc.execute(comando);
					//Verifica si ya no existe
					comando = "SELECT COUNT(LOID) FROM PG_CATALOG.PG_LARGEOBJECT WHERE LOID = ?";	
					if (archivoJdbc.queryForObject(comando, Integer.class, parImagen) == 0){
						ok = true;
					}
				}else {
					ok = true;
				}			
			}else {
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.spring.PosArchDocAlumDao|unlinkImagen|:"+ex);
		}

		return ok;
	}
	
	public int numImagenes(String matricula, String iddocumento) {
 		int numImagen = 0;
 		
 		try{
 			String comando = "SELECT COUNT(IMAGEN) AS NUMIMAGEN FROM ARCH_DOCALUM"+ 
 				" WHERE MATRICULA = ? " +
 				" AND IDDOCUMENTO = TO_NUMBER(?,'999')" +
 				" AND IMAGEN != 0";
 			
 			Object[] parametros = new Object[] {matricula, iddocumento};
			numImagen = archivoJdbc.queryForObject(comando,Integer.class,parametros);
 			
 		}catch(Exception ex){ 			
 			System.out.println("Error - aca.pg.archivo.spring.PosArchDocAlumDao|numImagenes(matricula,iddocumento)|:"+ex);
 		}
 		
 		return numImagen;
 	}
	
	public int numImagenes(String matricula) {
 		int numImagen			= 0;
 		
 		try{
 			String comando = "SELECT COUNT(IMAGEN) AS NUMIMAGEN FROM ARCH_DOCALUM"+ 
 				" WHERE MATRICULA = ? " + 				
 				" AND IMAGEN != 0";
 			
 			Object[] parametros = new Object[] {matricula};
			numImagen = archivoJdbc.queryForObject(comando,Integer.class,parametros);
 			
 		}catch(Exception ex){ 			
 			System.out.println("Error - aca.pg.archivo.spring.PosArchDocAlumDao|numImagenes(matricula)|:"+ex);
 		}
 		
 		return numImagen;
 	}
	
	public List<PosArchDocAlum> lisImagenesAlumno(String matricula, String orden) {		
		List<PosArchDocAlum> lista 		 = new ArrayList<PosArchDocAlum>();

		try{
			String comando = "SELECT MATRICULA, IDDOCUMENTO, IMAGEN, HOJA, FUENTE, TIPO, ORIGEN, TO_CHAR(F_INSERT, 'DD/MM/YYYY') AS F_INSERT, TO_CHAR(F_UPDATE, 'DD/MM/YYYY') AS F_UPDATE, USUARIO, ESTADO"
					+ " FROM ARCH_DOCALUM"
					+ " WHERE MATRICULA = ? "+orden;	
			Object[] parametros = new Object[] {matricula};
			lista = archivoJdbc.query(comando, new PosArchDocAlumMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.spring.PosArchDocAlumDao|lisImagenesAlumno|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String, String> mapAlumnosEnDocumento(String documentoId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();

		try{
			String comando = "SELECT MATRICULA AS LLAVE, COUNT(MATRICULA) AS VALOR FROM ARCH_DOCALUM WHERE IDDOCUMENTO = ? GROUP BY MATRICULA";
			
			Object[] parametros = new Object[] {documentoId};
			lista = archivoJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.spring.PosArchDocAlumDao|mapAlumnosEnDocumento|:"+ex);
		}
		
		return mapa;
	}	
	
	public HashMap<String, String> mapaNumDocumentos(String matricula) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT MATRICULA||IDDOCUMENTO AS LLAVE, COUNT(MATRICULA) AS VALOR FROM ARCH_DOCALUM WHERE MATRICULA = ? GROUP BY MATRICULA,IDDOCUMENTO";			
			Object[] parametros = new Object[] {matricula};
			lista = archivoJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.spring.PosArchDocAlumDao|mapaNumDocumentos|:"+ex);
		}
		
		return mapa;
	}
	
	public String TMP(String matricula) {
		String dato = "";
		List<PosArchDocAlum> lista = new ArrayList<PosArchDocAlum>();
		try{ 
			String comando = "SELECT COUNT(*) FROM ARCH_DOCALUM WHERE MATRICULA = ?";			
			if (archivoJdbc.queryForObject(comando, Integer.class, matricula) >= 1 ){
				comando = "SELECT MATRICULA, IDDOCUMENTO, IMAGEN, HOJA, FUENTE, TIPO, ORIGEN," +
						" TO_CHAR(F_INSERT, 'DD/MM/YYYY') AS F_INSERT," +
						" TO_CHAR(F_UPDATE, 'DD/MM/YYYY') AS F_UPDATE, USUARIO, ESTADO, IMAGEN_BYTE" +
						" FROM ARCH_DOCALUM" + 
						" WHERE MATRICULA = ?";
				lista = archivoJdbc.query(comando, new PosArchDocAlumMapperLargo(), matricula);
			}	
			
			for(PosArchDocAlum arch : lista) {
				comando = "UPDATE ARCH_DOCALUM SET IMAGEN_BYTE = MERGE_OID(IMAGEN) WHERE MATRICULA = ? AND IDDOCUMENTO = ? AND HOJA = ? AND IMAGEN_BYTE IS NULL";			
				if(archivoJdbc.update(comando, arch.getMatricula(),arch.getIddocumento(), arch.getHoja()) >= 1){
					comando = "UPDATE ARCH_DOCALUM SET ESTADO = 'B' WHERE MATRICULA = ? AND IDDOCUMENTO = ? AND HOJA = ? AND IMAGEN != 0";
					if(archivoJdbc.update(comando, arch.getMatricula(),arch.getIddocumento(), arch.getHoja()) >= 1){
						comando = "SELECT LO_UNLINK(IMAGEN) FROM ARCH_DOCALUM WHERE MATRICULA = '"+arch.getMatricula()+"' AND IDDOCUMENTO = "+arch.getIddocumento()+" AND HOJA = "+arch.getHoja()+" AND IMAGEN != 0";
						archivoJdbc.execute(comando);
						comando = "UPDATE ARCH_DOCALUM SET IMAGEN = 0 WHERE MATRICULA = ? AND IDDOCUMENTO = ? AND HOJA = ?";
						archivoJdbc.update(comando, arch.getMatricula(),arch.getIddocumento(), arch.getHoja());
					}
				}else {
					comando = "UPDATE ARCH_DOCALUM SET ESTADO = 'X' WHERE MATRICULA = ? AND IDDOCUMENTO = ? AND HOJA = ?";
					archivoJdbc.update(comando, arch.getMatricula(),arch.getIddocumento(), arch.getHoja());
				}
			}
			comando = "DELETE FROM TMP WHERE CODIGO_PERSONAL = ?";
			enocJdbc.update(comando, matricula);
			
			comando = "SELECT COUNT(*) FROM ARCH_DOCALUM WHERE ESTADO != 'B'";
			dato = archivoJdbc.queryForObject(comando, String.class);
			
			comando = "SELECT COUNT(*) FROM TMP";
			dato += " - "+enocJdbc.queryForObject(comando, String.class);
			
		}catch(Exception ex){ 			
			System.out.println("Error - aca.pg.archivo.spring.PosArchDocAlumDao|TMP|:"+ex);
		}
		
		return dato;
	}
	
	public List<String> listaTMP() {
		List<String> lista = new ArrayList<String>();

		try{
			String comando = "SELECT CODIGO_PERSONAL FROM TMP ORDER BY 1";	
			lista = enocJdbc.queryForList(comando, String.class);
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.spring.PosArchDocAlumDao|listaTMP|:"+ex);
		}
		
		return lista;
	}
	public List<PosArchDocAlum> lisHojasporDoc(String matricula, String iddocumento) {		
		List<PosArchDocAlum> lista 		 = new ArrayList<PosArchDocAlum>();

		try{
			String comando = " SELECT MATRICULA, IDDOCUMENTO, IMAGEN, HOJA, FUENTE, TIPO, ORIGEN, TO_CHAR(F_INSERT, 'DD/MM/YYYY') AS F_INSERT, "
					+ " TO_CHAR(F_UPDATE, 'DD/MM/YYYY') AS F_UPDATE, USUARIO, ESTADO, "
					+ " CASE ESTADO WHEN 'B' THEN IMAGEN_BYTE ELSE MERGE_OID(IMAGEN) END AS IMAGEN_BYTE" 
					+ " FROM ARCH_DOCALUM"  
					+ " WHERE MATRICULA = ?" 
					+ " AND IDDOCUMENTO = TO_NUMBER(?,'999')";	
							
			Object[] parametros = new Object[] {matricula,iddocumento};
			lista = archivoJdbc.query(comando, new  PosArchDocAlumMapperLargo(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.spring.PosArchDocAlumDao|lisHojasporDoc|:"+ex);
		}
		
		return lista;
	}
}