/*
 * Created on 28-feb-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package aca.kardex;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author CarlosF
 *
 */
public class TituloSuficiencia {
	public static ArrayList<String> getFacTitulos(Connection conn, String cargaId) throws SQLException{
		ArrayList<String> listor = new ArrayList<String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando			= "";
		try{
			comando = "select ENOC.FACULTAD(ENOC.CARRERA(ENOC.CURSO_PLAN(CURSO_ID))) from ENOC.krdx_curso_act "+ 
					"where titulo='S' and substr(curso_carga_id,1,6)='"+cargaId+"' group by ENOC.FACULTAD(ENOC.CARRERA(ENOC.CURSO_PLAN(CURSO_ID))) order by 1 ";				
			rs = st.executeQuery(comando);
			while (rs.next()){
				listor.add(rs.getString(1));
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.TituloSuficiencia|getFacTitulos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return listor;		
	}
	public static ArrayList<String> getAlumnosTitulo(Connection conn, String cargaId, String facultadId) throws SQLException{
		ArrayList<String> listor = new ArrayList<String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando			= "";
		String sFacultad		="";
		try{
			comando = "select ENOC.FACULTAD(b.carrera_id), a.codigo_personal,b.codigo_personal,a.curso_carga_id, "+
					"COALESCE(a.nota,0),to_char(a.f_titulo,'DD/MM/YYYY'),c.nombre_curso,a.tipocal_id "+
					"from ENOC.krdx_curso_act a, ENOC.CARGA_GRUPO b, ENOC.MAPA_CURSO c " + 
					"where a.titulo='S' and substr(a.curso_carga_id,1,6)='"+cargaId+"' "+ 
					"and a.CURSO_CARGA_ID = b.CURSO_CARGA_ID and a.curso_id = c.curso_id order by 3,7,2";
			rs = st.executeQuery(comando);
			while (rs.next()){
				sFacultad = rs.getString(1);
				if (sFacultad.equals(facultadId)){
					listor.add(rs.getString(2));
					listor.add(rs.getString(3));
					listor.add(rs.getString(4));
					listor.add(rs.getString(5));
					listor.add(rs.getString(6));
					listor.add(rs.getString(7));
					listor.add(rs.getString(8));
				}
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.TituloSuficiencia|getAlumnosTitulo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return listor;		
	}	
	public static int notaAprobatoria(Connection conn, String cursoCargaId) throws SQLException{
		int nota =0;
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando			= "";
		try{
			comando = "select distinct nota_aprobatoria FROM ENOC.ALUMNO_CURSO where curso_Carga_id='"+cursoCargaId+"'";				
			rs = st.executeQuery(comando);
			if (rs.next()) nota = rs.getInt(1);
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.TituloSuficiencia|notaAprobatoria|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return nota;		
	}
}