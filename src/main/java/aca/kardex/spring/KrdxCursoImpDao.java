package aca.kardex.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import aca.plan.spring.MapaCurso;
import aca.plan.spring.MapaCursoMapper;

@Component
public class KrdxCursoImpDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;

	public boolean insertReg(KrdxCursoImp curso){
		boolean ok = false;
		try{
			String comando = "INSERT INTO ENOC.KRDX_CURSO_IMP  ( CODIGO_PERSONAL, FOLIO, F_CREADA, CURSO_ID, CURSO_ID2, CONVALIDACION,  TITULO," 
				+ " OPTATIVA, TIPOCAL_ID, NOTA, NOTA_EXTRA, F_EXTRA, NOTA_CONVA, OBSERVACIONES,OPTATIVA_NOMBRE, USUARIO, FECHA, CICLO )" 
				+ " VALUES( ?, TO_NUMBER(?,'999'), TO_DATE(?,'DD/MM/YYYY'), ?, ?, ?, ?, ?, TO_NUMBER(?,'9999'), ?, TO_NUMBER(?,'999'), TO_DATE(?,'DD/MM/YYYY')," 
				+ " ?, ?, ?, ?, TO_DATE(?,'DD/MM/YYYY'), TO_NUMBER(?,'999') )";			
			Object[] parametros = new Object[] {curso.getCodigoPersonal(),curso.getFolio(),curso.getFCreada(),curso.getCursoId(),curso.getCursoId2(),curso.getConvalidacion(),
					curso.getTitulo(),curso.getOptativa(),curso.getTipoCalId(),curso.getNota().trim(),curso.getNotaExtra(),curso.getFExtra(),curso.getNotaConva(),curso.getObservaciones(),
					curso.getOptativaNombre(),curso.getUsuario(),curso.getFecha(),curso.getCiclo()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}					
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoImpDao|insertReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean insertConvalida(KrdxCursoImp curso){
		boolean ok = false;
		try{
			String comando = "INSERT INTO ENOC.KRDX_CURSO_IMP "  
								+ "(CODIGO_PERSONAL, CURSO_ID, TIPOCAL_ID, F_CREADA, NOTA , OBSERVACIONES, CURSO_ID2, CONVALIDACION, OPTATIVA, FOLIO, TITULO, USUARIO, FECHA, CICLO ) "
								+ "VALUES (?, ?, '1', TO_DATE(?,'DD/MM/YYYY'), " +
								"TO_NUMBER(?,'999'), 'Materia convalidada', ?, 'I', 'N', " +
								"TO_NUMBER(?,'999'), 'N', ?, TO_DATE(?,'DD/MM/YYYY'), ?)";
			Object[] parametros = new Object[] {curso.getCodigoPersonal(),curso.getCursoId(),curso.getFCreada(),curso.getNota(),curso.getCursoId2(),
					curso.getFolio(), curso.getUsuario(), curso.getFecha(), curso.getCiclo()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		
		}catch(Exception eCurso_id2){
			System.out.println("Error - aca.kardex.spring.KrdxCursoImpDao|insertConvalida|:"+eCurso_id2);			
		}
		
		return ok;
	}	
	
	public boolean updateReg(KrdxCursoImp curso){
		boolean ok = false;
		try{
			//System.out.println("Datos:"+nota+":"+notaConva+":"+folio);
			String comando = "UPDATE ENOC.KRDX_CURSO_IMP" + 
				" SET" +
				" F_CREADA = TO_DATE(?,'DD/MM/YYYY')," +
				" CURSO_ID = ?," +
				" CURSO_ID2 = ?," +
				" CONVALIDACION = ?," +
				" TITULO = ?," +
				" OPTATIVA = ?," +				
				" TIPOCAL_ID  = ?," +
				" NOTA = TO_NUMBER(?,'999.99')," +
				" NOTA_EXTRA = TO_NUMBER(?,'999.99')," +
				" F_EXTRA = TO_DATE(?,'DD/MM/YYYY')," +
				" NOTA_CONVA = ?,"+
				" OBSERVACIONES = ?," +
				" OPTATIVA_NOMBRE = ?," +
				" USUARIO = ?, "+
				" FECHA = TO_DATE(?,'DD/MM/YYYY')," +
				" CICLO = ? "+
				" WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'999')";
			
			Object[] parametros = new Object[] {curso.getFCreada(),curso.getCursoId(),curso.getCursoId2(),curso.getConvalidacion(),curso.getTitulo(),
					curso.getOptativa(),curso.getTipoCalId(),curso.getNota(),curso.getNotaExtra(),curso.getFExtra(),curso.getNotaConva(),curso.getObservaciones(),
					curso.getOptativaNombre(),curso.getUsuario(),curso.getFecha(), curso.getCiclo(), curso.getCodigoPersonal(), curso.getFolio() };
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception eCurso_id2){
			System.out.println("Error - aca.kardex.spring.KrdxCursoImpDao|updateReg|:"+eCurso_id2);
		}
		
		return ok;
	}	
	
	public boolean deleteReg(String codigoPersonal, String folio){
		boolean ok = false;
		try{
			String comando = "DELETE FROM ENOC.KRDX_CURSO_IMP "+ 
				"WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {codigoPersonal,folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception eCurso_id2){
			System.out.println("Error - aca.kardex.spring.KrdxCursoImpDao|deletReg|:"+eCurso_id2);
		}
		
		return ok;
	}
	
	public KrdxCursoImp mapeaRegId(String codigoPersonal, String folio ){
		
		KrdxCursoImp krdxCurso = new KrdxCursoImp();	
		try{ 
			String comando = " SELECT CODIGO_PERSONAL, FOLIO,  " +
				" TO_CHAR(F_CREADA,'DD/MM/YYYY') AS F_CREADA," +
				" CURSO_ID, CURSO_ID2, CONVALIDACION, TITULO, OPTATIVA," +
				" TIPOCAL_ID, NOTA, NOTA_EXTRA," +
				" TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA," +
				" NOTA_CONVA," +													
				" OBSERVACIONES," +
				" OPTATIVA_NOMBRE," +
				" USUARIO, " +
				" TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA," +
				" CICLO"+
				" FROM ENOC.KRDX_CURSO_IMP" + 
				" WHERE CODIGO_PERSONAL = ?" +
				" AND FOLIO = TO_NUMBER(?,'999')";			
			Object[] parametros = new Object[] {codigoPersonal, folio};
			krdxCurso = enocJdbc.queryForObject(comando, new KrdxCursoImpMapper(), parametros);			
		}catch(Exception eCurso_id2){
			System.out.println("Error - aca.kardex.spring.KrdxCursoImpDao|mapeaRegId|:"+eCurso_id2);
		}
		
		return krdxCurso;
	}
	
	public boolean existeReg(String codigoPersonal, String folio){
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.KRDX_CURSO_IMP WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {codigoPersonal,folio};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}			
		}catch(Exception eCurso_id2){
			System.out.println("Error - aca.kardex.spring.KrdxCursoImpDao|existeReg|:"+eCurso_id2);
		}		
		return ok;
	}
	
	public boolean existeCursoIdReg(String codigoPersonal, String cursoId){
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.KRDX_CURSO_IMP WHERE CODIGO_PERSONAL = ? AND CURSO_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal,cursoId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}			
		}catch(Exception eCurso_id2){
			System.out.println("Error - aca.kardex.spring.KrdxCursoImpDao|existeCursoIdReg|:"+eCurso_id2);
		}		
		return ok;
	}
	
	public boolean registrarConvInternas(){
		boolean ok = false;	
		try{
			String comando = "UPDATE ENOC.KRDX_CURSO_IMP" + 
					" SET CONVALIDACION = 'I'" +
					" WHERE CONVALIDACION = 'S'" +
					" AND TIPOCAL_ID = '1'" +
					" AND (NOTA >= 70 OR NOTA_EXTRA >= 70)";
			if (enocJdbc.update(comando)>=1){
				ok = true;
			}
		}catch(Exception eCurso_id2){
			System.out.println("Error - aca.kardex.spring.KrdxCursoImpDao|registrarConvInternas|:"+eCurso_id2);		
		}
		
		return ok;
	}
	
	public boolean tieneImportados(String codigoPersonal, String year, String planId){
		boolean ok 				= false;		
		try{
			String comando = "SELECT CURSO_ID FROM ENOC.KRDX_CURSO_IMP" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND F_CREADA BETWEEN TO_DATE('01/08/"+year+"','DD/MM/YYYY') AND TO_DATE('31/07/"+(Integer.parseInt(year)+1)+"','DD/MM/YYYY') " +
					" AND ENOC.CURSO_PLAN(CURSO_ID) = ?";
			Object[] parametros = new Object[] {codigoPersonal,planId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}			
		}catch(Exception eCurso_id2){
			System.out.println("Error - aca.kardex.spring.KrdxCursoImpDao|tieneImportados|:"+eCurso_id2);		
		}
		
		return ok;
	}
	
	public String maximoReg(String codigoPersonal){
		
		String maximo			= "1";		
		try{
			String comando = "SELECT COALESCE(MAX(FOLIO)+1,1) AS MAXIMO FROM ENOC.KRDX_CURSO_IMP WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			maximo =  enocJdbc.queryForObject(comando,String.class, parametros);	
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoImpDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public boolean tieneImp(String codigoPersonal, String cursoId ){
		boolean ok 				= false;		
		try{
			String comando = "SELECT CURSO_ID FROM ENOC.KRDX_CURSO_IMP WHERE CODIGO_PERSONAL = ? AND CURSO_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal,cursoId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}		
		}catch(Exception eCurso_id2){
			System.out.println("Error - aca.kardex.spring.KrdxCursoImpDao|tieneImp|:"+eCurso_id2);		
		}		
		return ok;
	}
	
	public List<KrdxCursoImp> lisImportados(String codigoPersonal, String orden ){		
		List<KrdxCursoImp> lisImportados	= new ArrayList<KrdxCursoImp>();	
		try{
			String comando = " SELECT CODIGO_PERSONAL, FOLIO," +
					" TO_CHAR(F_CREADA,'DD/MM/YYYY') AS F_CREADA," +
					" CURSO_ID, CURSO_ID2, CONVALIDACION, TITULO, OPTATIVA," +
					" TIPOCAL_ID, NOTA, NOTA_EXTRA," +
					" TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA," +
					" NOTA_CONVA," +													
					" OBSERVACIONES," +
					" OPTATIVA_NOMBRE," +
					" USUARIO," +
					" TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, "
					+ " CICLO" +
					" FROM ENOC.KRDX_CURSO_IMP" + 
					" WHERE CODIGO_PERSONAL = ? " + orden;
			lisImportados = enocJdbc.query(comando, new KrdxCursoImpMapper(), codigoPersonal);		
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoImpDao|lisImportados|:"+ex);
		}		
		return lisImportados;
	}
	
	public List<MapaCurso> lisMateriasSinRegistrar( String codigoPersonal, String planId, String orden ){		
		List<MapaCurso> lista	= new ArrayList<MapaCurso>();		
		try{
			String comando = "SELECT " +
				" PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO, CREDITOS, HT, HP, HI," +
				" F_CREADA, NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID, UNID, ON_LINE, CURSO_NUEVO, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, HORARIO, AREA_ID, LABORATORIO, SALON, ORDEN" +
				" FROM ENOC.MAPA_CURSO  " + 
				" WHERE PLAN_ID = ?  " +
				" AND CURSO_ID NOT IN" +
					" (SELECT CURSO_ID FROM ENOC.KRDX_CURSO_IMP " + 
					" WHERE CODIGO_PERSONAL = ? " +
					" AND NOTA >= ENOC.NOTA_APROBATORIA(CURSO_ID) " +					
					" AND TIPOCAL_ID = 1 ) "+ orden;
			lista = enocJdbc.query(comando, new MapaCursoMapper(), planId, codigoPersonal);
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoImpDao|lisMateriasSinRegistrar|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String, KrdxCursoImp> mapaCursosAlumno( String codigoAlumno) {
		HashMap<String, KrdxCursoImp> lisCurso	= new HashMap<String, KrdxCursoImp>();
		List<KrdxCursoImp> lista	= new ArrayList<KrdxCursoImp>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, FOLIO, TO_CHAR(F_CREADA,'DD/MM/YYYY') AS F_CREADA, CURSO_ID, CURSO_ID2, CONVALIDACION, TITULO, OPTATIVA,"
					+ " TIPOCAL_ID, NOTA, NOTA_EXTRA, TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA, NOTA_CONVA, OBSERVACIONES, OPTATIVA_NOMBRE, USUARIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA"
					+ " FROM ENOC.KRDX_CURSO_IMP"
					+ " WHERE CODIGO_PERSONAL = ?";	
			Object[] parametros = new Object[] {codigoAlumno};
			lista = enocJdbc.query(comando, new KrdxCursoImpMapper(), parametros);
			for(KrdxCursoImp curso : lista){				
				lisCurso.put(curso.getFolio(), curso);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoImpDao|mapaCursosAlumno|:"+ex);
		}
			
		return lisCurso;
	}
	
	public HashMap<String, String> mapaCursosPorAlumnoAndPlan() {
		HashMap<String, String> map	= new HashMap<String, String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT KCI.CODIGO_PERSONAL||'-'||MC.PLAN_ID AS LLAVE, COUNT(MC.CURSO_ID) AS VALOR FROM ENOC.KRDX_CURSO_IMP KCI, MAPA_CURSO MC WHERE MC.CURSO_ID = KCI.CURSO_ID GROUP BY KCI.CODIGO_PERSONAL||'-'||MC.PLAN_ID";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa mapa : lista){				
				map.put(mapa.getLlave(), mapa.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxCursoImpDao|mapaCursosPorAlumnoAndPlan|:"+ex);
		}
			
		return map;
	}
	
}