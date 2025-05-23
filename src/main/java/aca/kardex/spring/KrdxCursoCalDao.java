package aca.kardex.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class KrdxCursoCalDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( KrdxCursoCal curso) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.KRDX_CURSO_CAL "+ 
				"(CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, FECHA, FECHA_FINAL, NOTA, TIPO, ESTADO, TIPO_NOTA, TIPOCAL_ID) "+		
				"VALUES( ?, ?, ?, TO_DATE(?,'DD/MM/YYYY'), TO_DATE(?,'DD/MM/YYYY'), TO_NUMBER(?,'999'), ?, "+
				" ?, ?, ? )";				
			
			Object[] parametros = new Object[] {curso.getCodigoPersonal(),curso.getCursoCargaId(),curso.getCursoId(),curso.getFecha(),curso.getFechaFinal(),curso.getNota(),curso.getTipo(),curso.getEstado(),curso.getTipoNota(),curso.getTipoCalId()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoCalDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg( KrdxCursoCal curso) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.KRDX_CURSO_CAL "+ 
				" SET FECHA = TO_DATE(?,'DD/MM/YYYY'), " +
				" FECHA_FINAL = TO_DATE(?,'DD/MM/YYYY'), "+
				" NOTA = TO_NUMBER(?,'999')," +
				" TIPO = ?, "+
				" ESTADO = ?," +
				" TIPO_NOTA = ?, " +
				" TIPOCAL_ID = ? "+
				" WHERE CODIGO_PERSONAL = ? "+
				" AND CURSO_CARGA_ID = ? "+
				" AND CURSO_ID = ? ";
			
			Object[] parametros = new Object[] {curso.getFecha(),curso.getFechaFinal(),curso.getNota(),curso.getTipo(),curso.getEstado(),curso.getTipoNota(),curso.getTipoCalId(),curso.getCodigoPersonal(),curso.getCursoCargaId(),curso.getCursoId()}; 			 			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoCalDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	
	public boolean deleteReg(String codigoPersonal, String cursoCargaId, String cursoId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.KRDX_CURSO_CAL "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND CURSO_CARGA_ID = ? "+
				"AND CURSO_ID = ? ";
			
			Object[] parametros = new Object[] {codigoPersonal,cursoCargaId,cursoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoCalDao|deleteReg|:"+ex);	
		}
		return ok;
	}
	
	public boolean deleteDiferidasMateria(String cursoCargaId) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.KRDX_CURSO_CAL WHERE CURSO_CARGA_ID = ?";		
			Object[] parametros = new Object[] {cursoCargaId};
			if (enocJdbc.update(comando,parametros) >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoCalDao|deleteDiferidasMateria|:"+ex);			
		}
		return ok;
	}
	
	public KrdxCursoCal mapeaRegId(  String codigoPersonal, String cursoCargaId, String cursoId ) {
		KrdxCursoCal krdxCurso = new KrdxCursoCal();
		
		try{ 
			String comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, NOTA, ESTADO, " +
					" TIPO, TO_CHAR(FECHA_FINAL,'DD/MM/YYYY') AS FECHA_FINAL, TIPO_NOTA, TIPOCAL_ID "+			
				" FROM ENOC.KRDX_CURSO_CAL "+ 
				" WHERE CODIGO_PERSONAL = ? "+
				" AND CURSO_CARGA_ID = ? "+
				" AND CURSO_ID = ? ";
				
				Object[] parametros = new Object[] {codigoPersonal, cursoCargaId, cursoId};
				krdxCurso = enocJdbc.queryForObject(comando, new KrdxCursoCalMapper(), parametros);
	
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoCalDao|mapeaRegId|:"+ex);
		}
		return krdxCurso;
	}
	
	public boolean existeReg(String codigoPersonal, String cursoCargaId, String cursoId) {
		boolean 			ok 	= false;				
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.KRDX_CURSO_CAL "+ 
				" WHERE CODIGO_PERSONAL = ? "+
				" AND CURSO_CARGA_ID = ? "+
				" AND CURSO_ID = ? ";			
			Object[] parametros = new Object[] {codigoPersonal,cursoCargaId,cursoId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoCalDao|existeReg|:"+ex);
		}		
		return ok;
	}
	
	public boolean existeReg(String codigoPersonal, String cursoCargaId) {
		boolean 			ok 	= false;				
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.KRDX_CURSO_CAL "+ 
				" WHERE CODIGO_PERSONAL = ? "+
				" AND CURSO_CARGA_ID = ? ";			
			Object[] parametros = new Object[] {codigoPersonal,cursoCargaId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoCalDao|existeReg|:"+ex);
		}		
		return ok;
	}
	
	public boolean tieneDiferidas(String cursoCargaId) {
		boolean 			ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.KRDX_CURSO_CAL WHERE CURSO_CARGA_ID = ?";			
			Object[] parametros = new Object[] {cursoCargaId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoCalDao|tieneDiferidas|:"+ex);
		}
		return ok;
	}
	
	public boolean tieneCambios( String cursoCargaId, String tipo) {
		boolean 			ok 	= false;
				
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.KRDX_CURSO_CAL WHERE CURSO_CARGA_ID = ? AND TIPO = ?";			
			Object[] parametros = new Object[] {cursoCargaId, tipo};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoCalDao|tieneCambios|:"+ex);
		}
		return ok;
	}
	
	public int numCoreccion( String cursoCargaId) {		
		int numMat				= 0;
		try{
			String comando = "SELECT COUNT(*) AS NUMMAT FROM ENOC.KRDX_CURSO_CAL WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ? " +
					" AND TIPO = 'C'"; 
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1) {
				numMat = enocJdbc.queryForObject(comando, Integer.class);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.krdxCursoCalDao|numMatCalculo|:"+ex);
		}
		return numMat;
	}
	
	public String getFechaFinal( String codigoPersonal, String cursoCargaId, String cursoId, String tipo) {
		String fecha			= "-";
		try{
			String comando = "SELECT TO_CHAR(FECHA_FINAL,'DD/MM/YYYY') AS FECHA"
					+ " FROM ENOC.KRDX_CURSO_CAL"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CURSO_CARGA_ID = ?"
					+ " AND CURSO_ID = ?"
					+ " AND TIPO = ?"; 
			
				Object[] parametros = new Object[] {codigoPersonal,cursoCargaId,cursoId,tipo};
				fecha = enocJdbc.queryForObject(comando, String.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.krdxCursoCalDao|getFechaFinal|:"+ex);
		}
		return fecha;
	}

	
	public List<KrdxCursoCal> getListAll( String orden ) {
		List<KrdxCursoCal> lista		= new ArrayList<KrdxCursoCal>();
		String comando			= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, " +
					" NOTA, ESTADO, TO_CHAR(FECHA_FINAL,'DD/MM/YYYY') AS FECHA_FINAL," +
					" TIPO, TIPO_NOTA, TIPOCAL_ID  "+
					" FROM ENOC.KRDX_CURSO_CAL "+ orden; 
			
			lista = enocJdbc.query(comando, new KrdxCursoCalMapper());	
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.krdxCursoCalDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public List<KrdxCursoCal> getListDiferidas( String cursoCargaId, String tipo,String orden ) {
		List<KrdxCursoCal> lista		= new ArrayList<KrdxCursoCal>();
		String comando			= "";		
		try{
			comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID,TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, NOTA, ESTADO, " +
					" TO_CHAR(FECHA_FINAL,'DD/MM/YYYY') AS FECHA_FINAL, TIPO, TIPO_NOTA, TIPOCAL_ID  "+
				" FROM ENOC.KRDX_CURSO_CAL WHERE CURSO_CARGA_ID = ? " +
				" AND TIPO = ? "+ orden;			
			lista = enocJdbc.query(comando, new KrdxCursoCalMapper(), cursoCargaId, tipo);			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.krdxCursoCalDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public List<KrdxCursoCal> getListHoy( String cursoCargaId, String tipo, String orden ) {
		List<KrdxCursoCal> lista		= new ArrayList<KrdxCursoCal>();
		String comando			= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID,TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, NOTA, ESTADO, "+
					" TO_CHAR(FECHA_FINAL,'DD/MM/YYYY') AS FECHA_FINAL, TIPO, TIPO_NOTA, TIPOCAL_ID  "+
				" FROM ENOC.KRDX_CURSO_CAL " +
				" WHERE CURSO_CARGA_ID = ?";
			if (tipo.equals("D")){
				comando = comando + " AND TO_CHAR(FECHA,'DD/MM/YYYY') = TO_CHAR(now(),'DD/MM/YYYY')";
			}else{
				comando += " AND TO_CHAR(FECHA,'DD/MM/YYYY') = TO_CHAR(now(),'DD/MM/YYYY')";
			}			
				comando +=" AND TIPO = ? "+ orden; 
			
			lista = enocJdbc.query(comando, new KrdxCursoCalMapper(), cursoCargaId, tipo);			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.krdxCursoCalDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String, String> mapaCambiosNotas(String codigoPersonal, String tipo){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();		
		try{
			String comando 	= "SELECT CURSO_CARGA_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.KRDX_CURSO_CAL"
					+ " WHERE CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL = ?)"
					+ " AND TIPO = ?"
					+ " GROUP BY CURSO_CARGA_ID";
			Object[] parametros = new Object[] {codigoPersonal,tipo};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa row : lista) {
				mapa.put(row.getLlave(), (String)row.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.krdxCursoCalDao|mapaCambiosNotas|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaFechaFinal(){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();		
		try{
			String comando 	= "SELECT CODIGO_PERSONAL||CURSO_CARGA_ID AS LLAVE, TO_CHAR(FECHA_FINAL,'DD/MM/YYYY') AS VALOR"
				+ " FROM ENOC.KRDX_CURSO_CAL"
				+ " WHERE CURSO_ID IN (SELECT DISTINCT(CURSO_ID) FROM ENOC.KRDX_CURSO_ACT WHERE TIPOCAL_ID IN ('5','6'))";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa row : lista) {
				mapa.put(row.getLlave(), (String)row.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.krdxCursoCalDao|mapaFechaFinal|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, KrdxCursoCal> mapaCursoCargaCarga(String cursoCargaId, String cursoId){
		HashMap<String, KrdxCursoCal> mapa = new HashMap<String, KrdxCursoCal>();
		List<KrdxCursoCal> lista 		= new ArrayList<KrdxCursoCal>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, NOTA, ESTADO, " +
					" TIPO, TO_CHAR(FECHA_FINAL,'DD/MM/YYYY') AS FECHA_FINAL, TIPO_NOTA, TIPOCAL_ID "+			
				" FROM ENOC.KRDX_CURSO_CAL "+ 
				" WHERE CURSO_CARGA_ID = ? "+
				" AND CURSO_ID = ? ";
			Object[] parametros = new Object[] {cursoCargaId,cursoId};
			lista = enocJdbc.query(comando, new KrdxCursoCalMapper(), parametros);
			for(KrdxCursoCal objeto : lista) {
				mapa.put(objeto.getCodigoPersonal(), objeto);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.krdxCursoCalDao|mapaCursoCargaCarga|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, KrdxCursoCal> mapaDiferidasPorMateria(String cursoCargaId){
		HashMap<String, KrdxCursoCal> mapa = new HashMap<String, KrdxCursoCal>();
		List<KrdxCursoCal> lista 		= new ArrayList<KrdxCursoCal>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, NOTA, ESTADO,"
					+ " TIPO, TO_CHAR(FECHA_FINAL,'DD/MM/YYYY') AS FECHA_FINAL, TIPO_NOTA, TIPOCAL_ID"
					+ " FROM ENOC.KRDX_CURSO_CAL"
					+ " WHERE CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId};
			lista = enocJdbc.query(comando, new KrdxCursoCalMapper(), parametros);
			for(KrdxCursoCal objeto : lista) {
				mapa.put(objeto.getCodigoPersonal(), objeto);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.krdxCursoCalDao|mapaDiferidasPorMateria|:"+ex);
		}
		return mapa;
	}
}