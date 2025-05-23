package aca.kardex.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class KrdxCursoActDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(KrdxCursoAct curso){
		boolean ok = false;
		try{
			String comando = "INSERT INTO ENOC.KRDX_CURSO_ACT(CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, CURSO_ID2, TIPOCAL_ID, NOTA,"
					+ " F_NOTA, NOTA_EXTRA, F_EXTRA, TIPO, TITULO, COMENTARIO, CORRECCION, CANTIDAD, FRECUENCIA, FECHA, CONFIRMAR) "
					+ " VALUES( ?, ?, ?, ?, ?,"
					+ " TO_NUMBER(?,'999'),"
					+ " TO_DATE(?,'DD/MM/YYYY'),"
					+ " TO_NUMBER(?,'999'),"
					+ " TO_DATE(?,'DD/MM/YYYY'),"			
					+ " ?, ?, ?, ?,"
					+ " TO_NUMBER(?,'999'),"
					+ " TO_NUMBER(?,'99.99'),"
					+ " NOW(),"
					+ " ?)";			
			Object[] parametros = new Object[] {curso.getCodigoPersonal(), curso.getCursoCargaId(), curso.getCursoId(), curso.getCursoId2(), curso.getTipoCalId(), curso.getNota(),
					curso.getfNota(), curso.getNotaExtra(), curso.getfExtra(), curso.getTipo(), curso.getTitulo(), curso.getComentario(), curso.getCorreccion(), curso.getCantidad(),
					curso.getFrecuencia(),curso.getConfirmar()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|insertReg|:"+ex);
		}
		return ok;
	}	
	
	public boolean updateReg(KrdxCursoAct curso){
		boolean ok 	= false;		
		try{
			String comando = "UPDATE ENOC.KRDX_CURSO_ACT"
					+ " SET CURSO_ID = ?,"
					+ " CURSO_ID2 = ?,"
					+ " TIPOCAL_ID  = ?,"
					+ " NOTA = TO_NUMBER(?,'999'),"
					+ " F_NOTA = TO_DATE(?,'DD/MM/YYYY'),"
					+ " NOTA_EXTRA = TO_NUMBER(?,'999'),"
					+ " F_EXTRA = TO_DATE(?,'DD/MM/YYYY'),"
					+ " TIPO = ?,"
					+ " TITULO = ?,"
					+ " COMENTARIO = ?," 
					+ " CORRECCION = ?,"
					+ " CANTIDAD = TO_NUMBER(?,'999'),"
					+ " FRECUENCIA = TO_NUMBER(?,'99.99'),"
					+ " FECHA = NOW(),"
					+ " CONFIRMAR = ?"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CURSO_CARGA_ID = ?";		
			
			Object[] parametros = new Object[] {curso.getCursoId(), curso.getCursoId2(), curso.getTipoCalId(), curso.getNota(),curso.getfNota(), curso.getNotaExtra(),
					curso.getfExtra(), curso.getTipo(), curso.getTitulo(), curso.getComentario(), curso.getCorreccion(), curso.getCantidad(), curso.getFrecuencia(),
					curso.getConfirmar(),curso.getCodigoPersonal(), curso.getCursoCargaId()}; 			 			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	public boolean updateCerrar(String cursoCargaId, String codigoPersonal, String nota, String fechaNota, String tipoCalId){
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.KRDX_CURSO_ACT "
					+ " SET NOTA = TO_NUMBER(?,'999'), F_NOTA = TO_DATE(?,'DD/MM/YYYY'), TIPOCAL_ID  = ?, NOTA_EXTRA = 0, F_EXTRA = NULL"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CURSO_CARGA_ID = ?";		
			Object[] parametros = new Object[] {nota, fechaNota, tipoCalId, codigoPersonal, cursoCargaId};			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|updateCerrar|:"+ex);
		}
		return ok;
	}
	
	public boolean updateTipo(KrdxCursoAct curso){
		boolean ok = false;
		try{			
			String comando = "UPDATE ENOC.KRDX_CURSO_ACT SET TIPO = ? WHERE CODIGO_PERSONAL = ? AND CURSO_CARGA_ID = ?";				
			Object[] parametros = new Object[] {curso.getTipo(), curso.getCodigoPersonal(), curso.getCursoCargaId()}; 			 			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|updateTipo|:"+ex);		
		}
		return ok;
	}
	
	public boolean updateTipoCal(String codigoAlumno, String planId, String cargaId, String bloqueId, String tipo){
		boolean ok = false;
		try{			
			String comando = "UPDATE ENOC.KRDX_CURSO_ACT SET TIPOCAL_ID = ? WHERE CODIGO_PERSONAL = ? AND ENOC.PLAN(CURSO_ID) = ? AND CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ? AND BLOQUE_ID = TO_NUMBER(?,'99'))";				
			Object[] parametros = new Object[] {tipo, codigoAlumno, planId, cargaId, bloqueId}; 			 			
 			if (enocJdbc.update(comando,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|updateTipoCal|:"+ex);
		}
		return ok;
	}
	
	public boolean updateTitulo(KrdxCursoAct curso){
		boolean ok = false;
		
		try{
			
			String comando = "UPDATE ENOC.KRDX_CURSO_ACT"
					+ " SET TITULO = ?, F_TITULO = TO_DATE(?,'DD/MM/YYYY'),"
					+ " NOTA = TO_NUMBER(?,'999'),"
					+ " TIPOCAL_ID  = ? "
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CURSO_CARGA_ID = ?";
			
			Object[] parametros = new Object[] {curso.getTitulo(), curso.getfTitulo(), curso.getNota(), curso.getTipoCalId(), curso.getCodigoPersonal(), curso.getCursoCargaId()}; 			 			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|updateTitulo|:"+ex);		
		}
		return ok;
	}
	
	public boolean updateCantidad(KrdxCursoAct curso){
		boolean ok = false;
		
		try{
			
			String comando = "UPDATE ENOC.KRDX_CURSO_ACT"
					+ " SET CANTIDAD = TO_NUMBER(?,'999'), FRECUENCIA = TO_NUMBER(?,'99.99')"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CURSO_CARGA_ID = ?";
			
			Object[] parametros = new Object[] {curso.getCantidad(), curso.getFrecuencia(), curso.getCodigoPersonal(), curso.getCursoCargaId()}; 			 			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|updateTitulo|:"+ex);		
		}
		return ok;
	}
	
	public boolean updateConfirmar(String codigoPersonal, String cursoCargaId, String confirmar){
		boolean ok = false;
		
		try{
			
			String comando = "UPDATE ENOC.KRDX_CURSO_ACT SET CONFIRMAR = ? WHERE CODIGO_PERSONAL = ? AND CURSO_CARGA_ID = ?";
			
 			if (enocJdbc.update(comando,confirmar,codigoPersonal,cursoCargaId)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|updateCornfirmar|:"+ex);		
		}
		return ok;
	}
	
	public boolean deleteReg(String codigoPersonal, String cursoCargaId){
		boolean ok = false;

		try{
			String comando = "DELETE FROM ENOC.KRDX_CURSO_ACT"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CURSO_CARGA_ID = ?";
			
			Object[] parametros = new Object[] {codigoPersonal,cursoCargaId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public boolean deleteAlumnosEnProceso(String cargaId ){
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.KRDX_CURSO_ACT KCA"+ 
				" WHERE SUBSTR(KCA.CURSO_CARGA_ID,1,6) = ?" +
				" AND KCA.TIPOCAL_ID IN('M','C')" +
				" AND KCA.CURSO_CARGA_ID NOT IN" +
				"	(SELECT CURSO_CARGA_ID FROM ENOC.KRDX_ALUMNO_EVAL" + 
				"	WHERE CURSO_CARGA_ID||CODIGO_PERSONAL = KCA.CURSO_CARGA_ID||KCA.CODIGO_PERSONAL)";		
			Object[] parametros = new Object[] {cargaId};
			if (enocJdbc.update(comando,parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|deleteAlumnosEnProceso|:"+ex);			
		}
		
		return ok;
	}
	
	public boolean deleteAlumnosEnProceso(String cargaId, String codigoPersonal ){
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.KRDX_CURSO_ACT KCA"
					+ " WHERE SUBSTR(KCA.CURSO_CARGA_ID,1,6) = ?"
					+ " AND CODIGO_PERSONAL = ?"
					+ " AND KCA.TIPOCAL_ID IN('M','C')"
					+ " AND KCA.CURSO_CARGA_ID NOT IN"
					+ " 	(SELECT CURSO_CARGA_ID FROM ENOC.KRDX_ALUMNO_EVAL"
					+ " 	WHERE CURSO_CARGA_ID||CODIGO_PERSONAL = KCA.CURSO_CARGA_ID||KCA.CODIGO_PERSONAL)";
			Object[] parametros = new Object[] { cargaId, codigoPersonal };
			if (enocJdbc.update(comando,parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|deleteAlumnosEnProceso|:"+ex);			
		}
		
		return ok;
	}
	
	public KrdxCursoAct mapeaRegId(String codigoPersonal, String cursoCargaId){
		KrdxCursoAct krdxCurso = new KrdxCursoAct();
		
		try{ 
			String comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, CURSO_ID2, TIPOCAL_ID,"
				+ " NOTA, COALESCE(NOTA_EXTRA,0) AS NOTA_EXTRA,"
				+ " TO_CHAR(F_NOTA,'DD/MM/YYYY') AS F_NOTA,"
				+ " COALESCE(TO_CHAR(F_EXTRA,'DD/MM/YYYY'),'') AS F_EXTRA,"
				+ " TIPO, TITULO, "
				+ " TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO,"
				+ " COALESCE(COMENTARIO,'') AS COMENTARIO, CORRECCION, CANTIDAD, FRECUENCIA, TO_CHAR(FECHA, 'MM-DD-YYYY HH24:MI:SS') AS FECHA, CONFIRMAR"
				+ " FROM ENOC.KRDX_CURSO_ACT" 
				+ " WHERE CODIGO_PERSONAL = ?"
				+ " AND CURSO_CARGA_ID = ?";
			
			Object[] parametros = new Object[] {codigoPersonal, cursoCargaId};
			krdxCurso = enocJdbc.queryForObject(comando, new KrdxCursoActMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|mapeaRegId|:"+ex);
		}
		return krdxCurso;
	}
	
	public boolean existeReg(String codigoPersonal, String cursoCargaId){
		boolean 			ok 	= false;	
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.KRDX_CURSO_ACT WHERE CODIGO_PERSONAL = ? AND CURSO_CARGA_ID = ?";			
			Object[] parametros = new Object[] {codigoPersonal,cursoCargaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public boolean fueAlumnoDelMestro(String codigoAlumno, String codigoMaestro){
		boolean 			ok 	= false;		
		try{
			String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.KRDX_CURSO_ACT"
					+ " WHERE CODIGO_PERSONAL = ? AND CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL = ? OR CODIGO_OTRO = ?)";			
			Object[] parametros = new Object[] {codigoAlumno,codigoMaestro,codigoMaestro};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public int numAlumnosEnMateria( String cursoCargaId, String cursoId){
		int total = 0;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.KRDX_CURSO_ACT WHERE CURSO_CARGA_ID = ? AND CURSO_ID = ?";			
			Object[] parametros = new Object[] {cursoCargaId, cursoId};
			total = enocJdbc.queryForObject(comando,Integer.class,parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|tieneAlumnos|:"+ex);
		}
		return total;
	}
	
	public int numAlumGrupo(String cursoCargaId, String tipocalId){			
		int numMat				= 0;		
		try{
			String comando = "SELECT COUNT(CURSO_CARGA_ID) FROM ENOC.KRDX_CURSO_ACT"
					+ " WHERE CURSO_CARGA_ID = ? "
					+ " AND TIPOCAL_ID IN ("+tipocalId+")";
			Object[] parametros = new Object[] {cursoCargaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1) {
				comando = "SELECT COUNT(CURSO_CARGA_ID) FROM ENOC.KRDX_CURSO_ACT"
						+ " WHERE CURSO_CARGA_ID = ? "
						+ " AND TIPOCAL_ID IN ("+tipocalId+")";
				numMat = enocJdbc.queryForObject(comando,Integer.class,parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|numAlumGrupo|:"+ex);
		}
		
		return numMat;
	}
	
	public int totMaterias(String codigoPersonal, String cargaId, String bloqueId){
		int total 	= 0;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.KRDX_CURSO_ACT"
				+ " WHERE CODIGO_PERSONAL = ?"
				+ " AND SUBSTR(CURSO_CARGA_ID,1,6) = ?"
				+ " AND ";
			
			Object[] parametros = new Object[] {codigoPersonal,cargaId};
			total = enocJdbc.queryForObject(comando,Integer.class,parametros);
				
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|totMaterias|:"+ex);
		}
		return total;
	}
	
	public int numEmpMatTipo( String codigoPersonal, String cargaId, String tipoCal){
				
		int numAlum			= 0;
		try{
			String comando = "SELECT COALESCE(COUNT(CODIGO_PERSONAL),0) AS NUMERO" +
				" FROM ENOC.KRDX_CURSO_ACT" + 
				" WHERE CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ? AND CODIGO_PERSONAL= ?)"+
				" AND TIPOCAL_ID IN("+tipoCal+")";
			numAlum = enocJdbc.queryForObject(comando,Integer.class, cargaId, codigoPersonal);
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|numEmpMatTipo|:"+ex);
		}
		
		return numAlum;
	}
	
	public List<KrdxCursoAct> getLista(String codigoPersonal, String cursoCargaId, String orden) {
		List<KrdxCursoAct> lista	= new ArrayList<KrdxCursoAct>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, CURSO_ID2, "
					+ " TIPOCAL_ID, NOTA, NOTA_EXTRA, TO_CHAR(F_TITULO,'DD/MM/YYYY') F_TITULO,"
					+ " TIPO, TITULO, TO_CHAR(F_EXTRA,'DD/MM/YYYY') F_EXTRA,"
					+ " COMENTARIO, TO_CHAR(F_NOTA,'DD/MM/YYYY') F_NOTA,"
					+ " CORRECCION, CANTIDAD, FRECUENCIA, TO_CHAR(FECHA, 'MM-DD-YYYY HH24:MI:SS') AS FECHA, CONFIRMAR"
					+ " FROM ENOC.KRDX_CURSO_ACT"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CURSO_CARGA_ID = ? "+orden; 
			
			Object[] parametros = new Object[] {codigoPersonal, cursoCargaId};	
			lista = enocJdbc.query(comando, new KrdxCursoActMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|getLista|:"+ex);
		}
		return lista;
	}
	
	public List<KrdxCursoAct> lisAlumnosEnMateria(String cursoCargaId, String orden) {
		List<KrdxCursoAct> lista	= new ArrayList<KrdxCursoAct>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, CURSO_ID2, "
					+ " TIPOCAL_ID, NOTA, NOTA_EXTRA, TO_CHAR(F_TITULO,'DD/MM/YYYY') F_TITULO,"
					+ " TIPO, TITULO, TO_CHAR(F_EXTRA,'DD/MM/YYYY') F_EXTRA,"
					+ " COMENTARIO, TO_CHAR(F_NOTA,'DD/MM/YYYY') F_NOTA,"
					+ " CORRECCION, CANTIDAD, FRECUENCIA, TO_CHAR(FECHA, 'MM-DD-YYYY HH24:MI:SS') AS FECHA, CONFIRMAR"
					+ " FROM ENOC.KRDX_CURSO_ACT "
					+ " WHERE CURSO_CARGA_ID = ? "
					+ " AND ENOC.ALUM_INSCRITO_CARGA(CODIGO_PERSONAL,SUBSTR(CURSO_CARGA_ID,1,6)) IN('I','3')"
					+ " AND TIPOCAL_ID != 'M' "+orden; 
			
			Object[] parametros = new Object[] {cursoCargaId};	
			lista = enocJdbc.query(comando, new KrdxCursoActMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public List<KrdxCursoAct> lisMateriasEnCarga(String codigoPersonal, String cargaId, String orden) {
		List<KrdxCursoAct> lista	= new ArrayList<KrdxCursoAct>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, CURSO_ID2, "
					+ " TIPOCAL_ID, NOTA, NOTA_EXTRA, TO_CHAR(F_TITULO,'DD/MM/YYYY') F_TITULO,"
					+ " TIPO, TITULO, TO_CHAR(F_EXTRA,'DD/MM/YYYY') F_EXTRA,"
					+ " COMENTARIO, TO_CHAR(F_NOTA,'DD/MM/YYYY') F_NOTA,"
					+ " CORRECCION, CANTIDAD, FRECUENCIA, TO_CHAR(FECHA, 'MM-DD-YYYY HH24:MI:SS') AS FECHA, CONFIRMAR"
					+ " FROM ENOC.KRDX_CURSO_ACT "
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CARGA_DEL_GRUPO(CURSO_CARGA_ID) = ? "+orden; 
			
			Object[] parametros = new Object[] {codigoPersonal, cargaId};	
			lista = enocJdbc.query(comando, new KrdxCursoActMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public List<KrdxCursoAct> lisMateriasConfirmadasEnCarga(String codigoPersonal, String cargaId, String orden) {
		List<KrdxCursoAct> lista	= new ArrayList<KrdxCursoAct>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, CURSO_ID2, "
					+ " TIPOCAL_ID, NOTA, NOTA_EXTRA, TO_CHAR(F_TITULO,'DD/MM/YYYY') F_TITULO,"
					+ " TIPO, TITULO, TO_CHAR(F_EXTRA,'DD/MM/YYYY') F_EXTRA,"
					+ " COMENTARIO, TO_CHAR(F_NOTA,'DD/MM/YYYY') F_NOTA,"
					+ " CORRECCION, CANTIDAD, FRECUENCIA, TO_CHAR(FECHA, 'MM-DD-YYYY HH24:MI:SS') AS FECHA, CONFIRMAR"
					+ " FROM ENOC.KRDX_CURSO_ACT "
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CARGA_DEL_GRUPO(CURSO_CARGA_ID) = ?"
					+ " AND CONFIRMAR = 'S' "+orden;			
			Object[] parametros = new Object[] {codigoPersonal, cargaId};	
			lista = enocJdbc.query(comando, new KrdxCursoActMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public List<KrdxCursoAct> getListAll(String orden) {
		List<KrdxCursoAct> lista	= new ArrayList<KrdxCursoAct>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, CURSO_ID2, "
					+ " TIPOCAL_ID, NOTA, NOTA_EXTRA, TO_CHAR(F_TITULO,'DD/MM/YYYY') F_TITULO,"
					+ " TIPO, TITULO, TO_CHAR(F_EXTRA,'DD/MM/YYYY') F_EXTRA,"
					+ " COMENTARIO, TO_CHAR(F_NOTA,'DD/MM/YYYY') F_NOTA,"
					+ " CORRECCION, CANTIDAD, FRECUENCIA, TO_CHAR(FECHA, 'MM-DD-YYYY HH24:MI:SS') AS FECHA, CONFIRMAR"
					+ " FROM ENOC.KRDX_CURSO_ACT "+orden;
			lista = enocJdbc.query(comando, new KrdxCursoActMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public List<KrdxCursoAct> getListAll(String codigoPersonal, String orden) {
		List<KrdxCursoAct> lista	= new ArrayList<KrdxCursoAct>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, CURSO_ID2, "
					+ " TIPOCAL_ID, NOTA, NOTA_EXTRA, TO_CHAR(F_TITULO,'DD/MM/YYYY') F_TITULO,"
					+ " TIPO, TITULO, TO_CHAR(F_EXTRA,'DD/MM/YYYY') F_EXTRA,"
					+ " COMENTARIO, TO_CHAR(F_NOTA,'DD/MM/YYYY') F_NOTA,"
					+ " CORRECCION, CANTIDAD, FRECUENCIA, TO_CHAR(FECHA, 'MM-DD-YYYY HH24:MI:SS') AS FECHA, CONFIRMAR"
					+ " FROM ENOC.KRDX_CURSO_ACT "
					+ " WHERE CODIGO_PERSONAL = ? "+orden;			
			Object[] parametros = new Object[] {codigoPersonal};	
			lista = enocJdbc.query(comando, new KrdxCursoActMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public List<KrdxCursoAct> getListCurso(String cursoCargaId, String orden ) {
		
		List<KrdxCursoAct> lisActual	= new ArrayList<KrdxCursoAct>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, CURSO_ID2, TIPOCAL_ID, "+
					"NOTA, TO_CHAR(F_NOTA,'DD/MM/YYYY') AS F_NOTA, " +
					"NOTA_EXTRA, TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA, " +
					"TIPO, TITULO, TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, " +
					"COMENTARIO, CORRECCION, CANTIDAD, FRECUENCIA, TO_CHAR(FECHA, 'MM-DD-YYYY HH24:MI:SS') AS FECHA, CONFIRMAR "+
					"FROM ENOC.KRDX_CURSO_ACT "+ 
					"WHERE CURSO_CARGA_ID = ? "+ 
					"AND ENOC.ALUM_INSCRITO_CARGA(CODIGO_PERSONAL,SUBSTR(CURSO_CARGA_ID,1,6)) IN('I','3')" +
					" AND TIPOCAL_ID != 'M' "+orden;			
			Object[] parametros = new Object[] {cursoCargaId};			
			lisActual = enocJdbc.query(comando, new KrdxCursoActMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|getListCurso|:"+ex);
		}
		
		return lisActual;
	}
	
	public List<aca.Mapa> lisAlumnosCarga(String carga) {
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS LLAVE, ALUM_NOMBRE(CODIGO_PERSONAL) AS VALOR"
				+ " FROM KRDX_CURSO_ACT WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ? AND CURSO_ID IN (SELECT CURSO_ID FROM MAPA_CURSO WHERE LABORATORIO = 'S')";			
			Object[] parametros = new Object[] {carga};			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|lisAlumnosCarga|:"+ex);
		}		
		return lista;
	}
	
	public List<KrdxCursoAct> listExtrasPorCarga(String cargaId, String orden ){		
		List<KrdxCursoAct> lisActual	= new ArrayList<KrdxCursoAct>();		
		try{
			String comando = " SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, CURSO_ID2, TIPOCAL_ID,"
					+ " NOTA, TO_CHAR(F_NOTA,'DD/MM/YYYY') AS F_NOTA,"
					+ " NOTA_EXTRA, TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA,"
					+ " TIPO, TITULO, TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO,"
					+ " COMENTARIO, CORRECCION,  CANTIDAD, FRECUENCIA, FECHA, CONFIRMAR"
					+ " FROM ENOC.KRDX_CURSO_ACT"
					+ " WHERE CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ?)"
					+ " AND NOTA_EXTRA IS NOT NULL"
					+ " AND F_EXTRA IS NOT NULL "+orden;
			lisActual = enocJdbc.query(comando, new KrdxCursoActMapper(), cargaId);
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|listExtrasPorCarga|:"+ex);
		}
		
		return lisActual;
	}
	
	public List<KrdxCursoAct> lisDiferidas(String orden ){		
		List<KrdxCursoAct> lisActual	= new ArrayList<KrdxCursoAct>();	
		try{
			String comando = "SELECT KCA.CODIGO_PERSONAL, KCA.CURSO_CARGA_ID, KCA.CURSO_ID, KCA.CURSO_ID2, KCA.TIPOCAL_ID, KCA.NOTA, TO_CHAR(KCA.F_NOTA,'DD/MM/YYYY') AS F_NOTA,"
					+ " KCA.NOTA_EXTRA, TO_CHAR(KCA.F_EXTRA,'DD/MM/YYYY') AS F_EXTRA, KCA.TIPO, KCA.TITULO, TO_CHAR(KCA.F_TITULO,'DD/MM/YYYY') AS F_TITULO, KCA.COMENTARIO, "
					+ " KCA.CORRECCION, KCA.CANTIDAD, KCA.FRECUENCIA, KCA.FECHA, KCA.CONFIRMAR"
					+ " FROM ENOC.KRDX_CURSO_ACT KCA, ENOC.CARGA_GRUPO CG"
					+ " WHERE KCA.TIPOCAL_ID IN('5','6')"
					+ " AND CG.CURSO_CARGA_ID = KCA.CURSO_CARGA_ID"
					+ " AND CG.ESTADO IN ('3','4','5') "+orden;
			lisActual = enocJdbc.query(comando, new KrdxCursoActMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|lisDiferidas|:"+ex);
		}		
		return lisActual;
	}
	
	public List<KrdxCursoAct> getListDiferida(String cursoCargaId, String orden ){		
		List<KrdxCursoAct> lisActual	= new ArrayList<KrdxCursoAct>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, CURSO_ID2, TIPOCAL_ID,"+
					" NOTA, TO_CHAR(F_NOTA,'DD/MM/YYYY') AS F_NOTA," +
					" NOTA_EXTRA, TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA," +
					" TIPO, TITULO, TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO," +
					" COMENTARIO, CORRECCION,CANTIDAD, FRECUENCIA, TO_CHAR(FECHA, 'MM-DD-YYYY HH24:MI:SS') AS FECHA, CONFIRMAR" +
					" FROM ENOC.KRDX_CURSO_ACT" + 
					" WHERE CURSO_CARGA_ID = ?" +
					" AND TIPOCAL_ID = '6' "+orden;
			Object[] parametros = new Object[] {cursoCargaId};
			lisActual = enocJdbc.query(comando, new KrdxCursoActMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|getListDiferida|:"+ex);
		}		
		return lisActual;
	}
	
	public List<KrdxCursoAct> getListAlumno(String codigoAlumno, String cargaId, String bloqueId, String orden ) {
		List<KrdxCursoAct> lisActual	= new ArrayList<KrdxCursoAct>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, CURSO_ID2, TIPOCAL_ID,"
				+ " NOTA, TO_CHAR(F_NOTA,'DD/MM/YYYY') AS F_NOTA,"
				+ " NOTA_EXTRA, TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA,"
				+ " TIPO, TITULO, TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO,"
				+ " COMENTARIO, CORRECCION, CANTIDAD, FRECUENCIA, TO_CHAR(FECHA, 'MM-DD-YYYY HH24:MI:SS') AS FECHA, CONFIRMAR"
				+ " FROM ENOC.KRDX_CURSO_ACT"
				+ " WHERE CODIGO_PERSONAL = ?"
				+ " AND SUBSTR(CURSO_CARGA_ID,1,6) = ?"
				+ " AND ENOC.GRUPO_BLOQUE(CURSO_CARGA_ID) = TO_NUMBER(?,'99') "+orden;			
			lisActual = enocJdbc.query(comando, new KrdxCursoActMapper(), codigoAlumno, cargaId, bloqueId );
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|getListAlumno|:"+ex);
		}
		return lisActual;
	}

	public HashMap<String,KrdxCursoAct> getMapAll(String codigoPersonal, String cursoCargaId ) {
		HashMap<String,KrdxCursoAct> mapa = new HashMap<String,KrdxCursoAct>();
		List<KrdxCursoAct> lista 			= new ArrayList<KrdxCursoAct>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, CURSO_ID2, "
					+ " TIPOCAL_ID, NOTA, NOTA_EXTRA, TO_CHAR(F_TITULO,'DD/MM/YYYY') F_TITULO,"
					+ " TIPO, TITULO, TO_CHAR(F_EXTRA,'DD/MM/YYYY') F_EXTRA,"
					+ " COMENTARIO, TO_CHAR(F_NOTA,'DD/MM/YYYY') F_NOTA,"
					+ " CORRECCION, CANTIDAD, FRECUENCIA, TO_CHAR(FECHA, 'MM-DD-YYYY HH24:MI:SS') AS FECHA, CONFIRMAR"
					+ " FROM ENOC.KRDX_CURSO_ACT"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, cursoCargaId};
			lista = enocJdbc.query(comando, new KrdxCursoActMapper(), parametros);
			for(KrdxCursoAct objeto : lista){				
				mapa.put(objeto.getCodigoPersonal()+objeto.getCursoCargaId(), objeto);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|getMapAll|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,KrdxCursoAct> mapaNotas(String cursoCargaId ) {		
		HashMap<String,KrdxCursoAct> mapa = new HashMap<String,KrdxCursoAct>();
		List<KrdxCursoAct> lista 			= new ArrayList<KrdxCursoAct>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, CURSO_ID2, "
					+ " TIPOCAL_ID, NOTA, NOTA_EXTRA, TO_CHAR(F_TITULO,'DD/MM/YYYY') F_TITULO,"
					+ " TIPO, TITULO, TO_CHAR(F_EXTRA,'DD/MM/YYYY') F_EXTRA,"
					+ " COMENTARIO, TO_CHAR(F_NOTA,'DD/MM/YYYY') F_NOTA,"
					+ " CORRECCION, CANTIDAD, FRECUENCIA, TO_CHAR(FECHA, 'MM-DD-YYYY HH24:MI:SS') AS FECHA, CONFIRMAR"
					+ " FROM ENOC.KRDX_CURSO_ACT"
					+ " WHERE CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId};
			lista = enocJdbc.query(comando, new KrdxCursoActMapper(), parametros);
			for(KrdxCursoAct objeto : lista){				
				mapa.put(objeto.getCodigoPersonal(), objeto);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|mapaNotas|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,KrdxCursoAct> getMapAlumCurso(String codigoPersonal) {
		HashMap<String,KrdxCursoAct> mapa = new HashMap<String,KrdxCursoAct>();
		List<KrdxCursoAct> lista 			= new ArrayList<KrdxCursoAct>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, CURSO_ID2, "
					+ " TIPOCAL_ID, NOTA, NOTA_EXTRA, TO_CHAR(F_TITULO,'DD/MM/YYYY') F_TITULO,"
					+ " TIPO, TITULO, TO_CHAR(F_EXTRA,'DD/MM/YYYY') F_EXTRA,"
					+ " COMENTARIO, TO_CHAR(F_NOTA,'DD/MM/YYYY') F_NOTA,"
					+ " CORRECCION, CANTIDAD, FRECUENCIA, TO_CHAR(FECHA, 'MM-DD-YYYY HH24:MI:SS') AS FECHA, CONFIRMAR"
					+ " FROM ENOC.KRDX_CURSO_ACT"
					+ " WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new KrdxCursoActMapper(), parametros);
			for(KrdxCursoAct objeto : lista){				
				mapa.put(objeto.getCodigoPersonal()+objeto.getCursoId(), objeto);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|getMapCurso|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,KrdxCursoAct> mapaMateriasDelAlumno(String codigoPersonal) {
		HashMap<String,KrdxCursoAct> mapa = new HashMap<String,KrdxCursoAct>();
		List<KrdxCursoAct> lista 			= new ArrayList<KrdxCursoAct>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, CURSO_ID2, "
					+ " TIPOCAL_ID, NOTA, NOTA_EXTRA, TO_CHAR(F_TITULO,'DD/MM/YYYY') F_TITULO,"
					+ " TIPO, TITULO, TO_CHAR(F_EXTRA,'DD/MM/YYYY') F_EXTRA,"
					+ " COMENTARIO, TO_CHAR(F_NOTA,'DD/MM/YYYY') F_NOTA,"
					+ " CORRECCION, CANTIDAD, FRECUENCIA, TO_CHAR(FECHA, 'MM-DD-YYYY HH24:MI:SS') AS FECHA, CONFIRMAR"
					+ " FROM ENOC.KRDX_CURSO_ACT"
					+ " WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new KrdxCursoActMapper(), parametros);
			for(KrdxCursoAct objeto : lista){				
				mapa.put(objeto.getCursoCargaId(), objeto);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|mapaMateriasDelAlumno|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapaConfirmadas(String codigoPersonal) {
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL||CURSO_CARGA_ID AS LLAVE, CURSO_ID  AS VALOR"
					+ " FROM ENOC.KRDX_CURSO_ACT"
					+ " WHERE CODIGO_PERSONAL = ? AND CONFIRMAR = 'S'";
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa objeto : lista){				
				mapa.put(objeto.getLlave(), objeto.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|mapaConfirmadas|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,KrdxCursoAct> getMapAlumCurso(String codigoPersonal, String tipoCal) {
		HashMap<String,KrdxCursoAct> mapa = new HashMap<String,KrdxCursoAct>();
		List<KrdxCursoAct> lista 			= new ArrayList<KrdxCursoAct>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, CURSO_ID2, "
					+ " TIPOCAL_ID, NOTA, NOTA_EXTRA, TO_CHAR(F_TITULO,'DD/MM/YYYY') F_TITULO,"
					+ " TIPO, TITULO, TO_CHAR(F_EXTRA,'DD/MM/YYYY') F_EXTRA,"
					+ " COMENTARIO, TO_CHAR(F_NOTA,'DD/MM/YYYY') F_NOTA,"
					+ " CORRECCION, CANTIDAD, FRECUENCIA, TO_CHAR(FECHA, 'MM-DD-YYYY HH24:MI:SS') AS FECHA, CONFIRMAR"
					+ " FROM ENOC.KRDX_CURSO_ACT"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND TIPOCAL_ID IN ("+tipoCal+")";
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new KrdxCursoActMapper(), parametros);
			for(KrdxCursoAct objeto : lista){				
				mapa.put(objeto.getCursoId(), objeto);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|getMapCurso|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapaTotAlumnos(String cargaId, String tipoCal) {
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CURSO_CARGA_ID AS LLAVE, COUNT(*) AS VALOR FROM KRDX_CURSO_ACT"
					+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ? "
					+ " AND TIPOCAL_ID IN ("+tipoCal+")"
					+ " GROUP BY CURSO_CARGA_ID";					
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa objeto : lista){				
				mapa.put(objeto.getLlave(), objeto.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|mapaTotAlumnos|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapaTotAlumnos(String cargaId) {
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CURSO_CARGA_ID||TIPOCAL_ID AS LLAVE, COUNT(*) AS VALOR FROM KRDX_CURSO_ACT"
					+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ? "					
					+ " GROUP BY CURSO_CARGA_ID||TIPOCAL_ID";					
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa objeto : lista){				
				mapa.put(objeto.getLlave(), objeto.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|mapaTotAlumnos|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapInscritosPorMateria(String clave) {
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.KRDX_CURSO_ACT"
					+ " WHERE SUBSTR(CURSO_ID,9,7) = ?"					
					+ " GROUP BY CODIGO_PERSONAL";					
			Object[] parametros = new Object[] {clave};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa objeto : lista){				
				mapa.put(objeto.getLlave(), objeto.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|mapInscritosPorMateria|:"+ex);
		}
		return mapa;
	}	
	
	public HashMap<String,String> mapAlumnoPuntos(String cursoCargaId ){
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa 	= new HashMap<String,String>();				
		try{
			String comando =
					"SELECT"+
					  " CODIGO_PERSONAL AS LLAVE,"+
					  " SUM("+    
					    " CASE"+
					      " (SELECT TIPO FROM ENOC.CARGA_GRUPO_EVALUACION WHERE CURSO_CARGA_ID = KAE.CURSO_CARGA_ID AND EVALUACION_ID = KAE.EVALUACION_ID)"+
					    " WHEN '%' THEN COALESCE(NOTA,0)*(SELECT VALOR FROM ENOC.CARGA_GRUPO_EVALUACION WHERE CURSO_CARGA_ID = KAE.CURSO_CARGA_ID AND EVALUACION_ID = KAE.EVALUACION_ID)/100"+
					    " ELSE COALESCE(NOTA,0)"+
					    " END"+
					  " ) AS VALOR"+
					" FROM ENOC.KRDX_ALUMNO_EVAL KAE"+
					" WHERE CURSO_CARGA_ID = ?"+
					" AND (SELECT TIPO FROM ENOC.CARGA_GRUPO_EVALUACION WHERE CURSO_CARGA_ID = KAE.CURSO_CARGA_ID AND EVALUACION_ID = KAE.EVALUACION_ID) IN('%','P')"+
					" GROUP BY CODIGO_PERSONAL";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cursoCargaId);
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|mapAlumnoPuntos|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapAlumnoExtras(String cursoCargaId){
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa = new HashMap<String,String>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, SUM(COALESCE(NOTA,0)) AS VALOR " +
					" FROM ENOC.KRDX_ALUMNO_EVAL KAE"+
					" WHERE CURSO_CARGA_ID = ?"+
					" AND (SELECT TIPO FROM ENOC.CARGA_GRUPO_EVALUACION WHERE CURSO_CARGA_ID = KAE.CURSO_CARGA_ID AND EVALUACION_ID = KAE.EVALUACION_ID) = 'E'"+
					" GROUP BY CODIGO_PERSONAL";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cursoCargaId);
			for (aca.Mapa map : lista){			
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardx.apring.KrdxCursoActDao|mapAlumnoExtras|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaAlumnoCurso(String cursoCargaId ){
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa 	= new HashMap<String,String>();				
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, CURSO_ID AS VALOR FROM ENOC.KRDX_CURSO_ACT WHERE CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId}; 		
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|mapaAlumnoCurso|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapAlumnoEvaluados(String cursoCargaId, String orden ) {
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa 	= new HashMap<String,String>();		
		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE,"+
				  " (SELECT"+
				    " COALESCE(SUM(VALOR),0) AS PUNTOS"+ 
				  " FROM ENOC.CARGA_GRUPO_EVALUACION CGE"+
				  " WHERE CURSO_CARGA_ID = ?" +
				  " AND TIPO NOT IN ('E')"+				  
				  " AND EVALUACION_ID IN (SELECT EVALUACION_ID FROM ENOC.KRDX_ALUMNO_EVAL WHERE CURSO_CARGA_ID = CGE.CURSO_CARGA_ID AND CODIGO_PERSONAL = KCA.CODIGO_PERSONAL)" +
				  " )"+
				  " AS VALOR"+
				" FROM ENOC.KRDX_CURSO_ACT KCA"+
				" WHERE CURSO_CARGA_ID = ?";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cursoCargaId, cursoCargaId);
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|mapAlumnoEvaluados|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaTotalAlumnos(String cargaId, String tipoCal) {
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CURSO_CARGA_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.KRDX_CURSO_ACT WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ? AND TIPOCAL_ID IN ("+tipoCal+") GROUP BY CURSO_CARGA_ID";
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa objeto : lista){	
				mapa.put(objeto.getLlave(), objeto.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|mapaTotalAlumnos|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapaAlumnosPorMateria(String planId) {
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT KCA.CURSO_ID AS LLAVE, COUNT(MC.CURSO_ID) AS VALOR FROM ENOC.KRDX_CURSO_ACT KCA, MAPA_CURSO MC WHERE MC.PLAN_ID = ? AND MC.CURSO_ID = KCA.CURSO_ID GROUP BY KCA.CURSO_ID";
			Object[] parametros = new Object[] {planId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa objeto : lista){	
				mapa.put(objeto.getLlave(), objeto.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|mapaAlumnosPorMateria|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapaAlumnosEvaluaron(String codigoPersonal) {
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CURSO_CARGA_ID AS LLAVE, COUNT(DISTINCT(CODIGO_PERSONAL)) AS VALOR FROM ENOC.EDO_ALUMNORESP WHERE CODIGO_MAESTRO = ? GROUP BY CURSO_CARGA_ID";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), codigoPersonal);
			for(aca.Mapa objeto : lista){	
				mapa.put(objeto.getLlave(), objeto.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|mapaTotalAlumnos|:"+ex);
		}
		return mapa;
	}
	
	public int numAlumCredMat( String codigoPersonal, String cargaId, String tipoCal ) {	
		int numCred = 0;		
		try{
			String comando = "SELECT COALESCE(SUM(ENOC.CREDITOS(CURSO_ID)),0) AS CREDITOS" +
					" FROM ENOC.KRDX_CURSO_ACT" + 
					" WHERE CODIGO_PERSONAL = ?"+
					" AND SUBSTR(CURSO_CARGA_ID,1,6) = ?" +	
					" AND TIPOCAL_ID IN("+tipoCal+")";
			Object[] parametros = new Object[] {codigoPersonal, cargaId};
			numCred = enocJdbc.queryForObject(comando, Integer.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|numAlumCredMat|:"+ex);
		}
		
		return numCred;
	}
	
	public int numAlumDifMateria(String cursoCargaId, String cursoId){
		int numMat = 0;
		try{
			String comando = "SELECT COUNT(CURSO_CARGA_ID) AS NUMMAT FROM ENOC.KRDX_CURSO_ACT" + 
					" WHERE CURSO_CARGA_ID = ?" +
					" AND CURSO_ID = ? " +
					" AND TIPOCAL_ID = '6' ";

			Object[] parametros = new Object[] {cursoCargaId, cursoId};
			numMat = enocJdbc.queryForObject(comando, Integer.class, parametros);		
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|numAlumDifMateria|:"+ex);
		}
		
		return numMat;
	}	
	
	public int numAlumnosMateria( String cursoCargaId, String tipoCal ) {
		int numCred = 0;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.KRDX_CURSO_ACT" + 
					" WHERE CURSO_CARGA_ID = ?"+						
					" AND TIPOCAL_ID IN("+tipoCal+")";
			Object[] parametros = new Object[] {cursoCargaId };
			numCred = enocJdbc.queryForObject(comando, Integer.class, parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|numAlumnosMateria|:"+ex);
		}
		
		return numCred;
	}
	
	public int numMatInsc(String codigoPersonal, String planId) {
		int numMat	= 0;
		try{
			String comando = "SELECT COALESCE(COUNT(CODIGO_PERSONAL),0) AS NUMMAT " +
				"FROM ENOC.KRDX_CURSO_ACT "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND ENOC.CURSO_PLAN(CURSO_ID)= ?" +
				"AND TIPOCAL_ID = 'I'";
			
			Object[] parametros = new Object[] {codigoPersonal,planId};
			
			numMat = enocJdbc.queryForObject(comando, Integer.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|numMatInsc|:"+ex);
		}
		
		return numMat;
	}
	
	public float numFrecuenciasMateria( String codigoPersonal, String cursoCargaId) {	
		float total = 0;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.KRDX_CURSO_ACT WHERE CODIGO_PERSONAL = ? AND CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, cursoCargaId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT COALESCE(FRECUENCIA,0) FROM ENOC.KRDX_CURSO_ACT WHERE CODIGO_PERSONAL = ? AND CURSO_CARGA_ID = ?";
				total = enocJdbc.queryForObject(comando, Float.class, parametros);
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|numFrecuenciasMateria|:"+ex);
		}		
		return total;
	}
	
	public HashMap<String,String> mapLimiteExtra(String cursoCargaId){		
		HashMap<String,String> mapa 	= new HashMap<String,String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, "
					+ " (SELECT COALESCE(NOTA_EXTRA,0) FROM ENOC.MAPA_PLAN WHERE PLAN_ID = (SELECT PLAN_ID FROM ENOC.MAPA_CURSO WHERE CURSO_ID = KCA.CURSO_ID)) AS VALOR"
					+ " FROM ENOC.KRDX_CURSO_ACT KCA"
					+ " WHERE CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId}; 
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map:lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.kardx.spring.KrdxCursoActDao|mapLimiteExtra|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaGrupoAlumno(String cargas){		
		HashMap<String,String> mapa 	= new HashMap<String,String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CODIGO_PERSONAL||SUBSTR(CURSO_CARGA_ID,1,6) AS LLAVE, GRUPO_GRUPO(CURSO_CARGA_ID) AS VALOR, COUNT(*)"
					+ " FROM KRDX_CURSO_ACT"
					+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6) IN ("+cargas+")"
					+ " GROUP BY CODIGO_PERSONAL||SUBSTR(CURSO_CARGA_ID,1,6), GRUPO_GRUPO(CURSO_CARGA_ID)"
					+ " ORDER BY 1,3 DESC";
			//Object[] parametros = new Object[] {cursoCargaId}; 
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map:lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.kardx.spring.KrdxCursoActDao|mapLimiteExtra|:"+ex);
		}
		return mapa;
	}	
	
	public HashMap<String,String> mapaCreditosPorCargayModalidad(String cargas){
		HashMap<String,String> mapa 	= new HashMap<String,String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT KCA.CODIGO_PERSONAL||CG.CARGA_ID||CG.MODALIDAD_ID AS LLAVE, SUM(CREDITOS(CURSO_ID)) AS VALOR"
					+ " FROM KRDX_CURSO_ACT KCA, CARGA_GRUPO CG"
					+ " WHERE SUBSTR(KCA.CURSO_CARGA_ID,1,6) IN ("+cargas+")"
					+ " AND CG.CURSO_CARGA_ID = KCA.CURSO_CARGA_ID"
					+ " GROUP BY KCA.CODIGO_PERSONAL||CG.CARGA_ID||CG.MODALIDAD_ID";
			//Object[] parametros = new Object[] {cursoCargaId}; 
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map:lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.kardx.spring.KrdxCursoActDao|mapLimiteExtra|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaAlumMatPorTipos(String cargaId, String tipoCal){
		
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		try{
			String comando = " SELECT CURSO_CARGA_ID AS LLAVE, COALESCE(COUNT(*),0) AS VALOR FROM ENOC.KRDX_CURSO_ACT"
					+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ? AND TIPOCAL_ID IN ("+tipoCal+")"
					+ " GROUP BY CURSO_CARGA_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId);
			for (aca.Mapa map:lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|mapaAlumMatPorTipos|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaAlumnosPorCarrera(String cargaId){		
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT ENOC.GRUPO_CARRERA(CURSO_CARGA_ID) AS LLAVE, COALESCE(COUNT(DISTINCT(CURSO_CARGA_ID)),0) AS VALOR FROM ENOC.KRDX_CURSO_ACT"
					+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ?"
					+ " GROUP BY ENOC.GRUPO_CARRERA(CURSO_CARGA_ID)";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId);
			for (aca.Mapa map:lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|mapaAlumMatPorTipos|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaAlumnosPorCarrerayTipo(String cargaId, String tipoCal){
		
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		try{
			String comando = " SELECT ENOC.GRUPO_CARRERA(CURSO_CARGA_ID) AS LLAVE, COALESCE(COUNT(CODIGO_PERSONAL),0) AS VALOR FROM ENOC.KRDX_CURSO_ACT"
					+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ? AND TIPOCAL_ID IN ("+tipoCal+")"
					+ " GROUP BY ENOC.GRUPO_CARRERA(CURSO_CARGA_ID)";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId);
			for (aca.Mapa map:lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|mapaAlumMatPorTipos|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapMatPorTipo(String cargaId, String tipo){
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		
		try{
			String comando = " SELECT CODIGO_PERSONAL AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR"
				+ " FROM ENOC.KRDX_CURSO_ACT "
				+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ? AND TIPO = ?"
				+ " GROUP BY CODIGO_PERSONAL";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId, tipo);
			for(aca.Mapa map : lista){
				mapa.put(map.getLlave(), map.getValor());
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.kardx.spring.KrdxCursoActDao|mapMatPorTipo|:"+ex);
		}
		
		return mapa;
	}

	public HashMap<String, String> mapaPromediosPorMateria(String cargaId, String tipoCal){
		
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		try{
			String comando = " SELECT CURSO_CARGA_ID AS LLAVE, COALESCE(SUM(ENOC.ALUM_CURSO_PROMEDIO(CODIGO_PERSONAL,CURSO_CARGA_ID)),0) AS VALOR FROM ENOC.KRDX_CURSO_ACT"
					+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6) IN('"+cargaId+"') AND TIPOCAL_ID NOT IN ("+tipoCal+")";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map:lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|mapaPromediosPorMateria|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaPromedioMaterias(String codigoAlumno){
		
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CURSO_CARGA_ID AS LLAVE, ROUND(ALUM_CURSO_PROMEDIO(CODIGO_PERSONAL,CURSO_CARGA_ID)) AS VALOR"
					+ " FROM ENOC.KRDX_CURSO_ACT"
					+ " WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoAlumno};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map:lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|mapaPromedioMaterias|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaPromediosAconsejados(String mentorId, String periodoId, String tipos){		
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CODIGO_PERSONAL||SUBSTR(CURSO_CARGA_ID,1,6) AS LLAVE,"
					+ " ROUND(AVG(CASE TIPOCAL_ID WHEN 'I' THEN (ALUM_CURSO_PROMEDIO(CODIGO_PERSONAL,CURSO_CARGA_ID)) ELSE ALUM_CURSO_NOTA(CODIGO_PERSONAL,CURSO_CARGA_ID) END),2)  AS VALOR"
					+ " FROM ENOC.KRDX_CURSO_ACT"
					+ " WHERE CODIGO_PERSONAL IN(SELECT CODIGO_PERSONAL FROM ENOC.MENT_ALUMNO WHERE MENTOR_ID = ? AND PERIODO_ID = ?)"
					+ " AND TIPOCAL_ID IN ("+tipos+")"
					+ " AND ALUM_CURSO_PROMEDIO(CODIGO_PERSONAL,CURSO_CARGA_ID) > 1"
					+ " GROUP BY CODIGO_PERSONAL||SUBSTR(CURSO_CARGA_ID,1,6)";
			Object[] parametros = new Object[] {mentorId, periodoId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map:lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|mapaPromediosAconsejados|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaAvanceEvaluaciones(String mentorId, String periodoId, String tipos){		
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT  CODIGO_PERSONAL||SUBSTR(CURSO_CARGA_ID,1,6) AS LLAVE, ROUND(AVG(ALUM_CURSO_AVANCE(CODIGO_PERSONAL, CURSO_CARGA_ID)),2) AS VALOR"
					+ " FROM ENOC.KRDX_CURSO_ACT"
					+ " WHERE CODIGO_PERSONAL IN(SELECT CODIGO_PERSONAL FROM ENOC.MENT_ALUMNO WHERE MENTOR_ID = ? AND PERIODO_ID = ?)"
					+ " AND TIPOCAL_ID IN ("+tipos+")"					
					+ " GROUP BY CODIGO_PERSONAL||SUBSTR(CURSO_CARGA_ID,1,6)";
			Object[] parametros = new Object[] {mentorId, periodoId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map:lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|mapaPromediosAconsejados|:"+ex);
		}		
		return mapa;
	}

	public HashMap<String, String> mapaCreditosOrigen(String cargaId){		
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CODIGO_PERSONAL||GRUPO_BLOQUE(CURSO_CARGA_ID) AS LLAVE, SUM(CREDITOS(CURSO_ID)) AS VALOR FROM ENOC.KRDX_CURSO_ACT"
					+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ?"
					+ " AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ALUM_ESTADO WHERE CARGA_ID = ? AND ESTADO = '3')"
					+ " AND TIPO IN('O','B') AND TIPOCAL_ID ! = 'M'"
					+ " GROUP BY CODIGO_PERSONAL||GRUPO_BLOQUE(CURSO_CARGA_ID)";
			
			Object[] parametros = new Object[] {cargaId,cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|mapaCreditosOrigen|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, String> mapaCreditosAlta(String cargaId){		
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CODIGO_PERSONAL||GRUPO_BLOQUE(CURSO_CARGA_ID) AS LALVE, SUM(CREDITOS(CURSO_ID)) AS VALOR FROM ENOC.KRDX_CURSO_ACT"
					+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ?"
					+ " AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ALUM_ESTADO WHERE CARGA_ID = ? AND ESTADO = '3')"
					+ " AND TIPO IN('A') AND TIPOCAL_ID != 'M'"
					+ " GROUP BY CODIGO_PERSONAL||GRUPO_BLOQUE(CURSO_CARGA_ID)";
			
			Object[] parametros = new Object[] {cargaId,cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|mapaCreditosAlta|:"+ex);
		}		
		return mapa;
	}

	public HashMap<String, String> mapaCreditosBaja(String cargaId){		
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CODIGO_PERSONAL||GRUPO_BLOQUE(CURSO_CARGA_ID) AS LLAVE, SUM(CREDITOS(CURSO_ID)) AS VALOR FROM ENOC.KRDX_CURSO_ACT"
					+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ?"
					+ " AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ALUM_ESTADO WHERE CARGA_ID = ? AND ESTADO = '3')"
					+ " AND TIPO IN('B') AND TIPOCAL_ID = '3'"
					+ " GROUP BY CODIGO_PERSONAL||GRUPO_BLOQUE(CURSO_CARGA_ID)";
			Object[] parametros = new Object[] {cargaId,cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|mapaCreditosBaja|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> getMapInscritosPorCarrera(String cargaId){
		
		HashMap<String,String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
				
		try{
			String comando = "SELECT CARRERA(SUBSTR(CURSO_ID,1,8)) AS LLAVE, COUNT(DISTINCT(CODIGO_PERSONAL)) AS VALOR FROM KRDX_CURSO_ACT"
					+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ?"
					+ " GROUP BY CARRERA(SUBSTR(CURSO_ID,1,8))";
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}
				
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|getMapInscritosPorCarrera|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaMateriasEnCarga(String cargaId, String carreraId){
		
		HashMap<String,String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();				
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, COUNT(CURSO_CARGA_ID) AS VALOR FROM ENOC.KRDX_CURSO_ACT"
					+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ?"
					+ " AND ENOC.CARRERA(ENOC.CURSO_PLAN(CURSO_ID)) = ?"
					+ " AND TIPOCAL_ID NOT IN ('M')"
					+ " GROUP BY CODIGO_PERSONAL";			
			Object[] parametros = new Object[] {cargaId, carreraId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|mapaMateriasEnCarga|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaMateriasEnCarga(String cargaId){
		
		HashMap<String,String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();				
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, COUNT(CURSO_CARGA_ID) AS VALOR FROM ENOC.KRDX_CURSO_ACT"
					+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ?"
					+ " GROUP BY CODIGO_PERSONAL";			
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|mapaMateriasEnCarga|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaReprobadasEnCarga(String cargaId, String carreraId){
		
		HashMap<String,String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();				
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, COUNT(CURSO_CARGA_ID) AS VALOR FROM ENOC.KRDX_CURSO_ACT"
					+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ?"
					+ " AND ENOC.CARRERA(ENOC.CURSO_PLAN(CURSO_ID)) = ?"
					+ " AND TIPOCAL_ID IN ('2','4')"
					+ " GROUP BY CODIGO_PERSONAL";		
			Object[] parametros = new Object[] {cargaId, carreraId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|mapaReprobadasEnCarga|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaCreditosEnCarga(String cargaId, String cadena){		
		HashMap<String,String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();				
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, SUM(CREDITOS) AS VALOR"
					+ " FROM ENOC.KRDX_CURSO_ACT K, ENOC.MAPA_CURSO M"
					+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ?"
					+ " AND K.CURSO_ID = M.CURSO_ID"
					+ " "+cadena+" "
					+ " GROUP BY CODIGO_PERSONAL";		
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|mapaCreditosEnCarga|:"+ex);
		}
		
		return mapa;
	}
	
	public int reprobadosEnMateria(String cursoId){
		HashMap<String,String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> listaApro		= new ArrayList<aca.Mapa>();				
		List<String> lista				= new ArrayList<String>();	
		
		int reprobados = 0;
		
		try{
			String comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS LLAVE, CODIGO_PERSONAL AS VALOR FROM ENOC.KRDX_CURSO_ACT WHERE TIPOCAL_ID IN('I','1') AND CURSO_ID = ?"
					+ " AND CODIGO_PERSONAL IN(SELECT CODIGO_PERSONAL FROM ENOC.KRDX_CURSO_ACT WHERE CURSO_ID = ? GROUP BY CODIGO_PERSONAL)"
					+ " ORDER BY CODIGO_PERSONAL";		
			Object[] parametros = new Object[] {cursoId, cursoId};
			listaApro = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : listaApro){
				mapa.put(map.getLlave(), map.getValor());
			}			
			
			comando = "SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.KRDX_CURSO_ACT WHERE TIPOCAL_ID IN('2','4') AND CURSO_ID = ?"
					+ " AND CODIGO_PERSONAL IN(SELECT CODIGO_PERSONAL FROM ENOC.KRDX_CURSO_ACT WHERE CURSO_ID = ? GROUP BY CODIGO_PERSONAL)"
					+ " ORDER BY CODIGO_PERSONAL";		
			
			lista = enocJdbc.queryForList(comando, String.class, parametros);
			for (String reprobado : lista){
				if(!mapa.containsKey(reprobado)) {
					reprobados++;
				}
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|reprobadosEnMateria|:"+ex);
		}
		
		return reprobados;
	}

	public List<String> listaReprobadosEnMateria(String cursoId){
		HashMap<String,String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> listaApro		= new ArrayList<aca.Mapa>();				
		List<String> lista				= new ArrayList<String>();	
		List<String> listaReprobados	= new ArrayList<String>();	
		
		try{
			String comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS LLAVE, CODIGO_PERSONAL AS VALOR FROM ENOC.KRDX_CURSO_ACT WHERE TIPOCAL_ID IN('I','1') AND CURSO_ID = ?"
					+ " AND CODIGO_PERSONAL IN(SELECT CODIGO_PERSONAL FROM ENOC.KRDX_CURSO_ACT WHERE CURSO_ID = ? GROUP BY CODIGO_PERSONAL)"
					+ " ORDER BY CODIGO_PERSONAL";		
			Object[] parametros = new Object[] {cursoId, cursoId};
			listaApro = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : listaApro){
				mapa.put(map.getLlave(), map.getValor());
			}			
			
			comando = "SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.KRDX_CURSO_ACT WHERE TIPOCAL_ID IN('2','4') AND CURSO_ID = ?"
					+ " AND CODIGO_PERSONAL IN(SELECT CODIGO_PERSONAL FROM ENOC.KRDX_CURSO_ACT WHERE CURSO_ID = ? GROUP BY CODIGO_PERSONAL)"
					+ " ORDER BY CODIGO_PERSONAL";		
			
			lista = enocJdbc.queryForList(comando, String.class, parametros);
			for (String reprobado : lista){
				if(!mapa.containsKey(reprobado)) {
					listaReprobados.add(reprobado);
				}
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|listaReprobadosEnMateria|:"+ex);
		}
		
		return listaReprobados;
	}
	
	public HashMap<String,String> mapaMateriasGraduandos(String eventoId, String tipos ){		
		HashMap<String,String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();				
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, COUNT(CURSO_ID) AS VALOR FROM ENOC.KRDX_CURSO_ACT"
					+ " WHERE CODIGO_PERSONAL||SUBSTR(CURSO_ID,1,8) IN (SELECT CODIGO_PERSONAL||PLAN_ID FROM ALUM_EGRESO WHERE EVENTO_ID = ?)"
					+ " AND TIPOCAL_ID IN ("+tipos+")"
					+ " GROUP BY CODIGO_PERSONAL";		
			lista = enocJdbc.query(comando, new aca.MapaMapper(), eventoId);
			for (aca.Mapa map : lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|mapaMateriasGraduandos|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> getNumAlumMatTipoModalidad(String cargaId, String modalidadId, String tipoCal){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista		 = new ArrayList<aca.Mapa>();	
		try{
			String comando = "SELECT SUBSTR(CURSO_CARGA_ID,1,6), CODIGO_PERSONAL AS LLAVE, COUNT(CURSO_ID) AS VALOR "+
					" FROM ENOC.KRDX_CURSO_ACT"+ 
					" WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ?"+
					" AND TIPOCAL_ID IN ("+tipoCal+") AND ENOC.ALUM_MOD_CARGA(CODIGO_PERSONAL, SUBSTR(CURSO_CARGA_ID,1,6)) IN ("+modalidadId+") "+
					" GROUP BY SUBSTR(CURSO_CARGA_ID,1,6), CODIGO_PERSONAL";
			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId);
			for (aca.Mapa map : lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|getNumAlumMatTipoModalidad|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> getNumAlumCreditosModalidad(String cargaId, String modalidad, String tipoCal) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista		 = new ArrayList<aca.Mapa>();	
		
		try{
			String comando = "SELECT  SUBSTR(CURSO_CARGA_ID,1,6), CODIGO_PERSONAL AS LLAVE," +
					" COALESCE(SUM((SELECT CREDITOS FROM ENOC.MAPA_CURSO WHERE CURSO_ID = KRDX_CURSO_ACT.CURSO_ID )),0) AS VALOR, ENOC.ALUMNO_MODALIDAD_ID(CODIGO_PERSONAL)"+
					" FROM ENOC.KRDX_CURSO_ACT"+ 
					" WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ?"+
					" AND TIPOCAL_ID IN ("+tipoCal+") AND ENOC.ALUM_MOD_CARGA(CODIGO_PERSONAL, SUBSTR(CURSO_CARGA_ID,1,6)) IN ("+modalidad+")"+
					" GROUP BY SUBSTR(CURSO_CARGA_ID,1,6), CODIGO_PERSONAL";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId);
			for (aca.Mapa map : lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|getNumAlumCreditosModalidad|:"+ex);
		}
		return mapa;
	}

	public HashMap<String, String> mapaMateriasConfirmadas(String cargaId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista		 = new ArrayList<aca.Mapa>();	
		
		try{
			String comando = "SELECT KCA.CODIGO_PERSONAL||CG.BLOQUE_ID AS LLAVE, COUNT(KCA.CODIGO_PERSONAL) AS VALOR"
					+ " FROM KRDX_CURSO_ACT KCA, CARGA_GRUPO CG"
					+ " WHERE SUBSTR(KCA.CURSO_CARGA_ID,1,6) = ?"
					+ " AND CG.CURSO_CARGA_ID = KCA.CURSO_CARGA_ID"
					+ " AND CONFIRMAR = 'S'"
					+ " GROUP BY KCA.CODIGO_PERSONAL||CG.BLOQUE_ID";
			
			lista = enocJdbc.query(comando, new aca.MapaMapper(),cargaId);
			for (aca.Mapa map : lista){
				mapa.put(map.getLlave(), map.getValor());
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|mapaMateriasConfirmadas|:"+ex);
		}
		return mapa;
	}

	public HashMap<String, String> mapaCreditosConfirmadas(String cargaId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista		 = new ArrayList<aca.Mapa>();	
		
		try{
			String comando = "SELECT KCA.CODIGO_PERSONAL||CG.BLOQUE_ID AS LLAVE, SUM(MC.CREDITOS) AS VALOR"
					+ " FROM KRDX_CURSO_ACT KCA, CARGA_GRUPO CG, MAPA_CURSO MC"
					+ " WHERE SUBSTR(KCA.CURSO_CARGA_ID,1,6) = ?"
					+ " AND CG.CURSO_CARGA_ID = KCA.CURSO_CARGA_ID"					
					+ " AND KCA.CONFIRMAR = 'S'"
					+ " AND MC.CURSO_ID = KCA.CURSO_ID"
					+ " GROUP BY KCA.CODIGO_PERSONAL, CG.BLOQUE_ID";
			
			lista = enocJdbc.query(comando, new aca.MapaMapper(),cargaId);
			for (aca.Mapa map : lista){
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|mapaCreditosConfirmadas|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaTotalCreditosConfirmadas() {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista		 = new ArrayList<aca.Mapa>();	
		
		try{
			String comando = "SELECT KCA.CODIGO_PERSONAL||CG.BLOQUE_ID AS LLAVE, SUM(MC.CREDITOS) AS VALOR FROM KRDX_CURSO_ACT KCA, MAPA_CURSO MC, CARGA_GRUPO CG"
					+ " WHERE SUBSTR(KCA.CURSO_CARGA_ID,1,6) IN (SELECT CARGA_ID FROM ENOC.CARGA_ENLINEA WHERE ESTADO = 'A')"
					+ " AND MC.CURSO_ID = KCA.CURSO_ID"
					+ " AND CG.CURSO_CARGA_ID = KCA.CURSO_CARGA_ID"
					+ " GROUP BY KCA.CODIGO_PERSONAL,CG.BLOQUE_ID";
			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista){
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|mapaTotalCreditosConfirmadas|:"+ex);
		}
		return mapa;
	}

	public HashMap<String, String> mapaPendientesPlan(String eventoId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista		 = new ArrayList<aca.Mapa>();	
		
		try{
			String comando = "SELECT KCA.CODIGO_PERSONAL||MC.PLAN_ID AS LLAVE, COUNT(KCA.CURSO_CARGA_ID) AS VALOR FROM ENOC.KRDX_CURSO_ACT KCA, ENOC.CARGA_GRUPO CG, ENOC.MAPA_CURSO MC"
					+ " WHERE KCA.CODIGO_PERSONAL||MC.PLAN_ID IN (SELECT CODIGO_PERSONAL||PLAN_ID FROM ALUM_EGRESO WHERE EVENTO_ID = TO_NUMBER(?,'999'))"
					+ " AND CG.CURSO_CARGA_ID = KCA.CURSO_CARGA_ID"
					+ " AND MC.CURSO_ID = KCA.CURSO_ID"
					+ " AND CG.ESTADO IN ('1','2')"
					+ " GROUP BY KCA.CODIGO_PERSONAL, MC.PLAN_ID";
			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), eventoId);
			for (aca.Mapa map : lista){
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|mapaPendientesPlan|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaGruposConGraduandos(String codigoMaestro) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista		 = new ArrayList<aca.Mapa>();	
		
		try{
			String comando = "SELECT KCA.CURSO_CARGA_ID AS LLAVE, COUNT(KCA.CURSO_ID) AS VALOR FROM ENOC.KRDX_CURSO_ACT KCA, ENOC.MAPA_CURSO MC"
					+ " WHERE KCA.CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM CARGA_GRUPO WHERE CODIGO_PERSONAL = ?)"
					+ " AND MC.CURSO_ID = KCA.CURSO_ID"
					+ " AND KCA.CODIGO_PERSONAL||MC.PLAN_ID IN (SELECT CODIGO_PERSONAL||PLAN_ID FROM ALUM_EGRESO)"
					+ " GROUP BY KCA.CURSO_CARGA_ID";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), codigoMaestro);
			for (aca.Mapa map : lista){
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|mapaGruposConGraduandos|:"+ex);
		}
		return mapa;
	}	
	
	public List<KrdxCursoAct> listaPendientes(String eventoId ) {
		List<KrdxCursoAct> lisActual	= new ArrayList<KrdxCursoAct>();		
		try{
			String comando = "SELECT KCA.CODIGO_PERSONAL, KCA.CURSO_CARGA_ID, KCA.CURSO_ID, KCA.CURSO_ID2, KCA.TIPOCAL_ID, KCA.NOTA, KCA.NOTA_EXTRA, KCA.F_EXTRA, KCA.TIPO, "
					+ " KCA.TITULO, KCA.F_TITULO, KCA.COMENTARIO, KCA.F_NOTA, KCA.CORRECCION, KCA.CANTIDAD, KCA.FRECUENCIA, KCA.FECHA, KCA.CONFIRMAR "
					+ " FROM KRDX_CURSO_ACT KCA, ENOC.MAPA_CURSO MC"
					+ " WHERE KCA.CURSO_CARGA_ID IN (SELECT DISTINCT(CURSO_CARGA_ID) FROM ENOC.CARGA_GRUPO WHERE ESTADO IN ('1','2'))"
					+ " AND KCA.CODIGO_PERSONAL||MC.PLAN_ID IN (SELECT CODIGO_PERSONAL||PLAN_ID FROM ALUM_EGRESO WHERE EVENTO_ID = TO_NUMBER(?,'999'))"
					+ " AND MC.CURSO_ID = KCA.CURSO_ID";	
			lisActual = enocJdbc.query(comando, new KrdxCursoActMapper(), eventoId);
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|listaPendientes|:"+ex);
		}
		return lisActual;
	}
	
	public HashMap<String, String> mapaAlumnosPorMateria(String cargaId, String codigoMaestro) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista		 = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CURSO_CARGA_ID AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.KRDX_CURSO_ACT"
					+ " WHERE CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM CARGA_GRUPO WHERE CARGA_ID = ? AND CODIGO_PERSONAL = ?)"
					+ " GROUP BY CURSO_CARGA_ID";	
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId, codigoMaestro );
			for (aca.Mapa map : lista){
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|mapaAlumnosPorMateria|:"+ex);
		}
		return mapa;
	}	
	
	public HashMap<String, String> mapaNumAlumnosPorMateria(String cargas) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista		 = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CURSO_CARGA_ID AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.KRDX_CURSO_ACT"
					+ " WHERE CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CARGA_ID IN ("+cargas+"))"
					+ " GROUP BY CURSO_CARGA_ID";	
			lista = enocJdbc.query(comando, new aca.MapaMapper() );
			for (aca.Mapa map : lista){
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|mapaAlumnosPorMateria|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaAlumnosPorCurso(String codigoMaestro) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista		 = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CURSO_CARGA_ID AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.KRDX_CURSO_ACT"
					+ " WHERE CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL = ?)"
					+ " AND TIPOCAL_ID != 'M' "
					+ " GROUP BY CURSO_CARGA_ID";	
			lista = enocJdbc.query(comando, new aca.MapaMapper(), codigoMaestro );
			for (aca.Mapa map : lista){
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|mapaAlumnosPorCurso|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaAlumnosPorCursoyTipo(String codigoMaestro) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista		 = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CURSO_CARGA_ID||TIPOCAL_ID AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.KRDX_CURSO_ACT"
					+ " WHERE CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL = ?)"					
					+ " GROUP BY CURSO_CARGA_ID, TIPOCAL_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), codigoMaestro );
			for (aca.Mapa map : lista){
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|mapaAlumnosPorCursoyTipo|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaNumeroDeAlumnosPorCurso(String cursoCargaId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista		 = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CURSO_ID AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.KRDX_CURSO_ACT WHERE CURSO_CARGA_ID = ? GROUP BY CURSO_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cursoCargaId );
			for (aca.Mapa map : lista){
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|mapaAlumnosPorCursoId|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaAlumnosPorPlan( String cargas ) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista		 = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CURSO_CARGA_ID||SUBSTR(CURSO_ID,1,8) AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.KRDX_CURSO_ACT WHERE SUBSTR(CURSO_CARGA_ID,1,6) IN ("+cargas+") GROUP BY CURSO_CARGA_ID, SUBSTR(CURSO_ID,1,8)";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista){
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|mapaAlumnosPorPlan|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapaAlumnosDelMaestro( String maestro ) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista		 = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.KRDX_CURSO_ACT"
				+ " WHERE CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL = ? OR CODIGO_OTRO = ?)"
				+ " GROUP BY CODIGO_PERSONAL";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), maestro, maestro);
			for (aca.Mapa map : lista){
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|mapaAlumnosDelMaestro|:"+ex);
		}
		return mapa;
	}
	public HashMap<String, String> mapaAlumnosPorPlanyTipo(String cargaId, String tipoCal){		
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		try{
			String comando = " SELECT MC.PLAN_ID AS LLAVE, COALESCE(COUNT(KCA.CODIGO_PERSONAL),0) AS VALOR FROM ENOC.KRDX_CURSO_ACT KCA, ENOC.MAPA_CURSO MC"
					+ " WHERE SUBSTR(KCA.CURSO_CARGA_ID,1,6) = ? AND KCA.TIPOCAL_ID IN ("+tipoCal+")"
					+ " AND MC.CURSO_ID = KCA.CURSO_ID"
					+ " GROUP BY MC.PLAN_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId);
			for (aca.Mapa map:lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoActDao|mapaAlumMatPorTipos|:"+ex);
		}
		
		return mapa;
	}
}