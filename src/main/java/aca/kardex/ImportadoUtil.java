// Clase para la tabla de Cursos Actuales
package aca.kardex;
import java.sql.*;
import java.util.ArrayList;
//import aca.kardex.CursosImportados;

import aca.catalogo.CatTipoCal;
import aca.plan.MapaCurso;

public class ImportadoUtil{
		
	public ArrayList<KrdxCursoImp> getLista(Connection conn, String codigoPersonal, String orden ) throws SQLException{
		
		ArrayList<KrdxCursoImp> lisImportado	= new ArrayList<KrdxCursoImp>();
		Statement st 						    = conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, FOLIO, TO_CHAR(F_CREADA,'DD/MM/YYYY') AS F_CREADA, CURSO_ID, CURSO_ID2, CONVALIDACION, "+
					"TITULO, OPTATIVA, TIPOCAL_ID, NOTA, NOTA_EXTRA, F_EXTRA, NOTA_CONVA, OBSERVACIONES, OPTATIVA_NOMBRE, USUARIO, FECHA "+
					"FROM ENOC.KRDX_CURSO_IMP WHERE CODIGO_PERSONAL =  '"+codigoPersonal+"' "+ orden; 
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				KrdxCursoImp importado = new KrdxCursoImp();
				importado.mapeaReg(rs);
				lisImportado.add(importado);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ImportadoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisImportado;
	}	
	
	public ArrayList<KrdxCursoImp> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<KrdxCursoImp> lisImportado	= new ArrayList<KrdxCursoImp>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, FOLIO, F_CREADA, CURSO_ID, CURSO_ID2, CONVALIDACION, "+
					"TITULO, OPTATIVA, TIPOCAL_ID, NOTA, NOTA_EXTRA, F_EXTRA, NOTA_CONVA, OBSERVACIONES, OPTATIVA_NOMBRE, USUARIO, FECHA  "+
					"FROM ENOC.KRDX_CURSO_IMP "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				KrdxCursoImp importado = new KrdxCursoImp();
				importado.mapeaReg(rs);
				lisImportado.add(importado);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ImportadoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisImportado;
	}
	
	// funcion que regresa los cursos importados del alumno
	
	public ArrayList<CursosImportados> getListImportados(Connection conn, String codigoPersonal, String orden ) throws SQLException{
		
		ArrayList<CursosImportados> lisImportado	= new ArrayList<CursosImportados>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando			= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, KCI.CURSO_ID AS CURSO_ID, CURSO_ID2, CICLO, " +
				"FOLIO, TO_CHAR(KCI.F_CREADA,'DD/MM/YYYY') AS F_CREADA,  " +
				"CONVALIDACION, TITULO, OPTATIVA,  " +
				"ALUMNO_CONDICION(TIPOCAL_ID) AS CONDICION,  " +
				"NOTA, NOTA_EXTRA, " +
				"TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA, " +
				"NOTA_CONVA, " + 
				"CASE COALESCE(OBSERVACIONES,'X') WHEN 'X' THEN ' ' ELSE SUBSTR(OBSERVACIONES,1,20) END AS OBSERVACIONES, "+
				"OPTATIVA_NOMBRE, "+
				"USUARIO, " +
				"FECHA " +
				"FROM ENOC.KRDX_CURSO_IMP KCI, ENOC.MAPA_CURSO MP " + 
				"WHERE KCI.CURSO_ID = MP.CURSO_ID " +
				"AND CODIGO_PERSONAL = '"+codigoPersonal+"' "+ orden;			
			rs = st.executeQuery(comando);
			while (rs.next()){
				lisImportado.add(new CursosImportados(rs.getString("codigo_Personal"), rs.getString("CURSO_ID"), rs.getString("CURSO_ID2"), rs.getInt("CICLO"), rs.getInt("FOLIO"), rs.getString("F_CREADA"),rs.getString("CONVALIDACION"), rs.getString("TITULO"), rs.getString("OPTATIVA"), rs.getString("CONDICION"), rs.getInt("NOTA"), rs.getInt("NOTA_EXTRA"),rs.getString("F_EXTRA"), rs.getString("OBSERVACIONES"),rs.getString("NOTA_CONVA"), rs.getString("USUARIO"), rs.getString("FECHA")) );
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ImportadoUtil|getListImportados|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisImportado;
	}
	
	// funcion que regresa las materias que el alumno no ha cursado
	
	public ArrayList<MapaCurso> getListMatPosibles(Connection conn, String codigoPersonal, String planId, String orden ) throws SQLException{
		
		ArrayList<MapaCurso> lisImportado	= new ArrayList<MapaCurso>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando			= "";		
		try{
			comando = "SELECT " +
				" PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO, CREDITOS, HT, HP, HI," +
				" F_CREADA, NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID, UNID, ON_LINE, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, HORARIO, AREA_ID" +
				" FROM ENOC.MAPA_CURSO  " + 
				" WHERE plan_Id = '"+planId+"'  " +
				" AND CURSO_ID NOT IN" +
					" (SELECT CURSO_ID FROM ENOC.KRDX_CURSO_IMP " + 
					" WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' " +
					" AND NOTA >= NOTA_APROBATORIA(CURSO_ID) " +					
					" AND TIPOCAL_ID = 1 ) "+ orden;			
			rs = st.executeQuery(comando);
			while (rs.next()){			
				aca.plan.MapaCurso cursos = new aca.plan.MapaCurso();
				cursos.mapeaReg(rs);				
				lisImportado.add(cursos);					
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ImportadoUtil|getListMatPosibles|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		return lisImportado;
	}
	
	
	// funcion que regresa el folio del alumno
	
	public int getFolioAlumno(Connection conn, String codigoPersonal) throws SQLException{
		
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		int folio 				= 0;
		
		try{
			comando = "SELECT COALESCE(MAX(FOLIO)+1, 1) AS FOLIO FROM ENOC.KRDX_CURSO_IMP WHERE codigo_Personal = '"+codigoPersonal+"' "; 
			
			rs = st.executeQuery(comando);
			if (rs.next()){				
				folio = rs.getInt("FOLIO"); 
				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ImportadoUtil|getFolioAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return folio;
	}	
	
	
	// funcion que regresa los tipos de calificaciones ejemplo Aprobado, Acreditado etc.
	
	public ArrayList<CatTipoCal> getListTipoCalificacion(Connection conn) throws SQLException{
		
		ArrayList<CatTipoCal> lisTipoCal		= new ArrayList<CatTipoCal>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";

		
		try{
			comando = "SELECT * FROM ENOC.CAT_TIPOCAL WHERE TIPOCAL_ID BETWEEN '1' AND '9'"; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){	
				aca.catalogo.CatTipoCal tipoCalf = new aca.catalogo.CatTipoCal();
				tipoCalf.mapeaReg(rs);
				lisTipoCal.add(tipoCalf);				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ImportadoUtil|getListTipoCalificacion|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisTipoCal;
	}
}