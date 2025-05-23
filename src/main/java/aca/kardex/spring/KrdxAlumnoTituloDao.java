package aca.kardex.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class KrdxAlumnoTituloDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	

	public boolean insertReg( KrdxAlumnoTitulo titulo) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.KRDX_ALUMNO_TITULO(CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, CARRERA_ID, PRESIDENTE, SECRETARIO, MIEMBRO, COMENTARIO, NOTA, USUARIO, ESTADO)"
					+ " VALUES( ?, ?, ?, ?, ?, ?,?,?, TO_NUMBER(?,'999'),?,?)";
			Object[] parametros = new Object[]{ titulo.getCodigoPersonal(), titulo.getCursoCargaId(), titulo.getCursoId(), titulo.getCarreraId(), 
					titulo.getPresidente(), titulo.getSecretario(), titulo.getMiembro(), titulo.getComentario(), titulo.getNota(), titulo.getUsuario(), titulo.getEstado()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoTituloDao|insertReg|:"+ex);			
		}	
		
		return ok;
	}
	
	public boolean updateReg( KrdxAlumnoTitulo titulo ) {
		boolean ok = false;
		try{
			String comando = "UPDATE ENOC.KRDX_ALUMNO_TITULO"+ 
				" SET "+
				" CURSO_ID = ?,"+
				" CARRERA_ID = ?,"+
				" PRESIDENTE  = ?,"+
				" SECRETARIO = ?,"+
				" MIEMBRO = ?,"+
				" COMENTARIO = ?,"+
				" NOTA = TO_NUMBER(?,'999'),"+
				" USUARIO= ?,"+
				" ESTADO = ? "+
				" WHERE CODIGO_PERSONAL = ?"+
				" AND CURSO_CARGA_ID = ?";	
			Object[] parametros = new Object[]{ titulo.getCursoId(), titulo.getCarreraId(), 
					titulo.getPresidente(), titulo.getSecretario(), titulo.getMiembro(), titulo.getComentario(), titulo.getNota(), titulo.getUsuario(), titulo.getEstado(),
					titulo.getCodigoPersonal(), titulo.getCursoCargaId()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoTituloDao|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean existeReg(String codigoPersonal, String cursoCargaId) {
		boolean 			ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.KRDX_ALUMNO_TITULO "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, cursoCargaId};
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1){
				ok = true;
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoAct|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean deleteReg( String codigoPersonal, String cursoCargaId) {
		boolean ok = false;
		try{
			String comando = "DELETE FROM ENOC.KRDX_ALUMNO_TITULO WHERE CODIGO_PERSONAL = ? AND CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[]{ codigoPersonal, cursoCargaId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoTituloDao|deleteReg|:"+ex);	
		}
		
		return ok;
	}
	
	
	public KrdxAlumnoTitulo mapeaRegId( String codigoPersonal, String cursoCargaId ) {
		KrdxAlumnoTitulo titulo = new KrdxAlumnoTitulo();
		
		try{ 
			String comando = "SELECT COUNT(*) FROM ENOC.KRDX_ALUMNO_TITULO WHERE CODIGO_PERSONAL = ? AND CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[]{ codigoPersonal, cursoCargaId};
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1 ){
				comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, CARRERA_ID, PRESIDENTE, SECRETARIO, MIEMBRO, COMENTARIO, NOTA, USUARIO, ESTADO"
						+ " FROM ENOC.KRDX_ALUMNO_TITULO"
						+ " WHERE CODIGO_PERSONAL = ?"
						+ " AND CURSO_CARGA_ID = ?";
				titulo = enocJdbc.queryForObject(comando, new KrdxAlumnoTituloMapper(),parametros);
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoTituloDao|mapeaRegId|:"+ex);
		}
		
		return titulo; 
	}
	
	public KrdxAlumnoTitulo mapeaCursoAprobado( String codigoPersonal, String cursoId ) {
		KrdxAlumnoTitulo titulo = new KrdxAlumnoTitulo();
		
		try{ 
			String comando = "SELECT COUNT(*) FROM ENOC.KRDX_ALUMNO_TITULO WHERE CODIGO_PERSONAL = ? AND CURSO_ID = ? AND TIPOCAL_ID = '1'";
			Object[] parametros = new Object[]{ codigoPersonal, cursoId};
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1 ){
				comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, CARRERA_ID, PRESIDENTE, SECRETARIO, MIEMBRO, COMENTARIO, NOTA, USUARIO, ESTADO"
						+ " FROM ENOC.KRDX_ALUMNO_TITULO"
						+ " WHERE CODIGO_PERSONAL = ?"
						+ " AND CURSO_ID = ?"
						+ " AND TIPOCAL_ID = '1'";
				titulo = enocJdbc.queryForObject(comando, new KrdxAlumnoTituloMapper(),parametros);
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoTituloDao|mapeaCursoAprobado|:"+ex);
		}
		
		return titulo; 
	}
	
	public HashMap<String, KrdxAlumnoTitulo> mapaMateriasTitulo(String codigoPersonal ){
		
		HashMap<String,KrdxAlumnoTitulo> mapa	= new HashMap<String,KrdxAlumnoTitulo>();
		List<KrdxAlumnoTitulo> lista = new ArrayList<KrdxAlumnoTitulo>();	
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, CARRERA_ID, PRESIDENTE, SECRETARIO, MIEMBRO, COMENTARIO, NOTA, USUARIO, ESTADO"
					+ " FROM ENOC.KRDX_ALUMNO_TITULO WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[]{ codigoPersonal };
			lista = enocJdbc.query(comando, new KrdxAlumnoTituloMapper(),parametros);
			for(KrdxAlumnoTitulo titulo : lista){				
				mapa.put(titulo.getCursoCargaId(), titulo);			
			}
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoTituloDao|mapaMateriasTitulo| "+ex);
		}
		
		return mapa;
	}

	
}