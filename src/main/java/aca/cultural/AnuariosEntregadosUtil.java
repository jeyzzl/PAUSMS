package aca.cultural;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import aca.cultural.AnuariosEntregados;

public class AnuariosEntregadosUtil{
	
	public boolean insertRegByte(Connection conn, AnuariosEntregados anuariosEntregados ) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.ANUARIOS_ENTREGADOS"+ 
				"(EJERCICIO_ID, MATRICULA, NOMBRE, FECHA, USUARIO, CARRERA, SEMESTRE) "+
				"VALUES( ?,?,?,TO_DATE(?, 'DD/MM/YYYY'),?,?,?)");
			
			ps.setString(1, anuariosEntregados.getEjercicioId());
			ps.setString(2, anuariosEntregados.getMatricula());			
			ps.setString(3, anuariosEntregados.getNombre());
			ps.setString(4, anuariosEntregados.getFecha());
			ps.setString(5, anuariosEntregados.getUsuario());
			ps.setString(6, anuariosEntregados.getCarrera());
			ps.setString(7, anuariosEntregados.getSemestre());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;		
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.AnuariosEntregadosUtil|insertRegByte|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn, AnuariosEntregados anuariosEntregados ) throws Exception{
		PreparedStatement ps 	= null;		
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ANUARIOS_ENTREGADOS "+ 
				"SET FECHA = TO_DATE(?, 'DD/MM/YYYY HH24:MI'), "+
				"NOMBRE = ?, "+
				"USUARIO = ?, "+
				"CARRERA = ?, "+
				"SEMESTRE = ? "+
				"WHERE EJERCICIO_ID = ? AND MATRICULA = ?");
			
			ps.setString(1, anuariosEntregados.getFecha());			
			ps.setString(2, anuariosEntregados.getNombre());
			ps.setString(3, anuariosEntregados.getUsuario());
			ps.setString(4, anuariosEntregados.getCarrera());
			ps.setString(5, anuariosEntregados.getSemestre());
			ps.setString(6, anuariosEntregados.getEjercicioId());
			ps.setString(7, anuariosEntregados.getMatricula());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;	
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.AnuariosEntregadosUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String ejercicioId, String matricula ) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.ANUARIOS_ENTREGADOS "+ 
				"WHERE EJERCICIO_ID = ? AND MATRICULA = ? ");
			ps.setString(1, ejercicioId);
			ps.setString(2, matricula);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;		
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.AnuariosEntregadosUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public AnuariosEntregados mapeaRegId( Connection conn, String ejercicioId, String matricula) throws SQLException{
		AnuariosEntregados anuariosEntregados = new AnuariosEntregados();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
		ps = conn.prepareStatement("SELECT "+
			" EJERICIO_ID, MATRICULA, NOMBRE, TO_CHAR(FECHA, 'DD/MM/YYYY/HH24:MI') AS FECHA, USUARIO, CARRERA, SEMESTRE"+
			" FROM ENOC.ANUARIOS_ENTREGADOS"+ 
			" WHERE EJERCICIO_ID = ? AND MATRICULA = ? ");
		ps.setString(1, ejercicioId);
		ps.setString(2, matricula);
		
		rs = ps.executeQuery();
		if (rs.next()){			
			anuariosEntregados.mapeaReg(rs);
		}
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.AnuariosEntregadosUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return anuariosEntregados;
	}

	public boolean existeReg(Connection conn, String ejercicioId, String matricula) throws SQLException{
		
		PreparedStatement ps	= null;
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ANUARIOS_ENTREGADOS "+ 
					" WHERE EJERCICIO_ID = '"+ejercicioId+"' AND MATRICULA = '"+matricula+"' ");
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.AnuariosEntregadosUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public ArrayList<AnuariosEntregados> getListAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<AnuariosEntregados> lis		= new ArrayList<AnuariosEntregados>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT EJERCICIO_ID, MATRICULA, NOMBRE, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, USUARIO, CARRERA, SEMESTRE "+
					  " FROM ENOC.ANUARIOS_ENTREGADOS "+orden;	 
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AnuariosEntregados obj = new AnuariosEntregados();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.AnuariosEntregadosUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lis;
	}
	
	public static HashMap <String,AnuariosEntregados> getMapEvento(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,AnuariosEntregados> map = new HashMap<String,AnuariosEntregados>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";		
		
		try{
			comando = " SELECT EJERCICIO_ID, MATRICULA, NOMBRE, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, USUARIO, CARRERA, SEMESTRE"+
					  " FROM ENOC.ANUARIOS_ENTREGADOS"+orden;
			
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				AnuariosEntregados obj = new AnuariosEntregados();
				obj.mapeaReg(rs);
				map.put(obj.getMatricula()+"@@"+obj.getEjercicioId(), obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.AnuariosEntregadosUtil|getMapEvento|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return map;
	}
}