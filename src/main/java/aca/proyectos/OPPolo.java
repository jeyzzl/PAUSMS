package aca.proyectos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

public class OPPolo {
	Connection c;
	public OPPolo(Connection con) {
		c = con;
	}

	public List<String> getPolos(int id_objetivo){
		List salida = new ArrayList<String>();
		try{
			PreparedStatement pst = c.prepareStatement("SELECT * FROM DANIEL.DPTO_OBJPOLO WHERE ID_OBJETIVO=?");
			pst.setInt(1, id_objetivo);
			
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				salida.add(rs.getString("polo"));
			}
			rs.close();
			pst.close();
			
		}catch(SQLException sqle){
			System.err.println("error en getPolos " + sqle);
		}
		
		return salida;
	}
	
	public void rmPolo(String polo, int id_objetivo){
		
		try{
			PreparedStatement pst = c.prepareStatement("DELETE FROM DANIEL.DPTO_OBJPOLO WHERE ID_OBJETIVO=? AND POLO=?");
			pst.setInt(1, id_objetivo);
			pst.setString(2, polo);
			
			pst.executeUpdate();

			pst.close();
			
		}catch(SQLException sqle){
			System.err.println("error en rmPolo " + sqle);
		}
	}
	
	public void addPolo(HttpServletRequest request){
		
		try{
			PreparedStatement pst = c.prepareStatement("INSERT INTO DANIEL.DPTO_OBJPOLO (POLO,ID_OBJETIVO) VALUES(?,?)");
			pst.setString(1, request.getParameter("TKL_polo"));
			pst.setInt(2, Integer.valueOf(request.getParameter("id_objetivo")));
			pst.executeUpdate();
			pst.close();
		}catch(SQLException sqle){
			System.err.println("error en addPolo " + sqle);
		}
		
	}
	
}
