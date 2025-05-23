package  aca.plan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Materia{

	public String getNombre(Connection conn,String idCurso)throws SQLException{
		String query;
		String nombre="";
		query="select nombre_curso from ENOC.mapa_curso where curso_id=?"; 
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement(query);
			ps.setString(1, idCurso);
			rs = ps.executeQuery();
			if(rs.next())
				nombre=rs.getString(1);
		}catch(Exception ex){
			System.out.println("Error - aca.plan.Materia|getNombre|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
			try { rs.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
		
	}
	
	public ArrayList<MateriaVO> getCursos(Connection conn,int ciclo,String planId)throws SQLException{
		String query;
		MateriaVO materia = null;
		ArrayList<MateriaVO> materias=new ArrayList<MateriaVO>();
		
		query="select curso_id,nombre_curso,ciclo,curso_clave from ENOC.mapa_curso where ciclo<=? and plan_id=? order by ciclo asc"; 
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement(query);
					
			ps.setInt(1, ciclo);
			ps.setString(2, planId);
			rs = ps.executeQuery();
			while(rs.next()){
				materia = new MateriaVO();
				materia.curso_id=rs.getString(1);
				materia.nombre_curso=rs.getString(2);
				materia.setCiclo(rs.getInt(3));
				materias.add(materia);
				materia.curso_clave = rs.getString(4);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.Materia|getCursos|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
			try { rs.close(); } catch (Exception ignore) { }
		}
		return materias;
	}
	
	public ArrayList<String> getPrerrequisitos(Connection conn,String cursoId)throws SQLException{
		String query;
		ArrayList<String> preCurso=new ArrayList<String>();
		query="select curso_id_pre from ENOC.mapa_curso_pre where curso_id=?"; 
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement(query);
			ps.setString(1, cursoId);
			rs = ps.executeQuery();
			while(rs.next())
				preCurso.add(rs.getString(1));
		}catch(Exception ex){
			System.out.println("Error-aca.plan.Materia|getPrerrequisitos|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
			try { rs.close(); } catch (Exception ignore) { }
		}
		return preCurso;
	}

	
	public void grabaPrerrequisitos(Connection conn,String idCurso,String sCursos) throws SQLException{
		String query;
		String[] cursos=sCursos.split(",");
		PreparedStatement ps = conn.prepareStatement("delete from ENOC.mapa_curso_pre where curso_id=?"); 
		ps.setString(1,idCurso);
		ps.executeUpdate();
		query="insert into ENOC.mapa_curso_pre values(?,?)"; 
		try{
			ps = conn.prepareStatement(query);
			for(int i=0;i<cursos.length;i++)
			{
				ps.setString(1, idCurso);
				ps.setString(2, cursos[i]);
				ps.executeUpdate();
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.Materia|grabaPrerrequisitos|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
}