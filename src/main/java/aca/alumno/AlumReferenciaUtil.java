package aca.alumno;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class AlumReferenciaUtil {
	
	public boolean insertReg(Connection conn, AlumReferencia alumReferencia) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			
			ps = conn.prepareStatement("INSERT INTO ENOC.ALUM_REFERENCIA"+ 
				"(CODIGO_PERSONAL, SCOTIABANK, BANAMEX, SANTANDER) "+
				"VALUES( ?, ?, ?, ?)");
					
			ps.setString(1, alumReferencia.getCodigoPersonal());
			ps.setString(2, alumReferencia.getScotiabank());
			ps.setString(3, alumReferencia.getBanamex());
			ps.setString(4, alumReferencia.getSantander());
						
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumReferenciaUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, AlumReferencia alumReferencia) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ALUM_REFERENCIA "+ 
				"SET "+
				"SCOTIABANK = ?, "+
				"BANAMEX = ?, "+
				"SANTANDER = ? "+
				"WHERE CODIGO_PERSONAL = ?");
			ps.setString(1, alumReferencia.getScotiabank());
			ps.setString(2, alumReferencia.getBanamex());
			ps.setString(3, alumReferencia.getSantander());
			ps.setString(4, alumReferencia.getCodigoPersonal());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumReferenciaUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String codigoPersonal) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.ALUM_REFERENCIA "+ 
				"WHERE CODIGO_PERSONAL = ?");
			ps.setString(1, codigoPersonal);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumReferenciaUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public AlumReferencia mapeaRegId( Connection conn, String codigoPersonal ) throws SQLException, IOException{
		AlumReferencia alumReferencia = new AlumReferencia();
		PreparedStatement ps = null;
 		ResultSet rs = null;
 		try{
	 		ps = conn.prepareStatement("SELECT * FROM ENOC.ALUM_REFERENCIA WHERE CODIGO_PERSONAL = ?"); 
			ps.setString(1, codigoPersonal);
	 		
	 		rs = ps.executeQuery();
	 		if (rs.next()){	 			
	 			alumReferencia.mapeaReg(rs);
	 		}
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumReferenciaUtil|mapeaRegId|:"+ex);
 		}finally{
 			try { rs.close(); } catch (Exception ignore) { }
 	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		return alumReferencia;
 	}
	
	public boolean existeReg(Connection conn, String codigoPersonal) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ALUM_REFERENCIA "+ 
				"WHERE CODIGO_PERSONAL = ?");
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumReferenciaUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public static int generarReferenciaBanamex(String matricula){
		//String sucursal = "0870";
		//String cuenta = "0528570";
		int sumaSucursalCuenta = 1118;
		matricula = matricula.substring(1);
//----------------COMPLETAR CON CEROS---------------------
		/*if(sucursal.length()<4){
			String tmpSucursal = "";
			for(int i=4; i>sucursal.length(); i--) tmpSucursal+="0";
			sucursal = tmpSucursal;
		}
		if(cuenta.length()<7){
			String tmpCuenta = "";
			for(int i=7; i>cuenta.length(); i--) tmpCuenta+="0";
			cuenta = tmpCuenta;
		}*/
		sumaSucursalCuenta+=(Integer.parseInt(matricula.charAt(5)+"")*37);
		sumaSucursalCuenta+=(Integer.parseInt(matricula.charAt(4)+"")*31);
		sumaSucursalCuenta+=(Integer.parseInt(matricula.charAt(3)+"")*29);
		sumaSucursalCuenta+=(Integer.parseInt(matricula.charAt(2)+"")*23);
		sumaSucursalCuenta+=(Integer.parseInt(matricula.charAt(1)+"")*19);
		sumaSucursalCuenta+=(Integer.parseInt(matricula.charAt(0)+"")*17);
		return 99-(sumaSucursalCuenta%97);
	}
	
	public static String invertirTexto(String matricula){
		StringBuilder textoInvertido = new StringBuilder();
		if (matricula.length()>0){
			for (int i=matricula.length()-1;i>=0;i--){
				textoInvertido.append(matricula.charAt(i));
			}
		}else{
			textoInvertido.append("");
		}
		return textoInvertido.toString();
	}
	
	public static String generarReferenciaSantander(String matricula){
		
		String matInversa = invertirTexto(matricula);
		StringBuilder resultado = new StringBuilder();
		
		// Resultado de multiplicaciones
		for (int i=0; i < matInversa.length();i++){
			
			// Si es par multiplica por 2 e impar multiplica por 1  
			if ((i % 2)== 0) 
				resultado.append( String.valueOf(Integer.parseInt(matInversa.substring(i, i+1)) * 2));
			else
				resultado.append( String.valueOf(Integer.parseInt(matInversa.substring(i, i+1)) * 1));
		}		
		
		// Suma los números del resultado
		int suma = 0;
		for (int i=0; i < resultado.toString().length(); i++){
			suma += Integer.parseInt( resultado.toString().substring(i, i+1));
		}
		
		// Obtiene el digito verificador
		int digito = 10 - (suma % 10);
		if (digito == 10) digito = 0;
		
		return String.valueOf(digito);
	}	
	
	public ArrayList<AlumReferencia> getListAll(Connection conn, String orden) throws SQLException{
		ArrayList<AlumReferencia> lista= new ArrayList<AlumReferencia>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando		= "";
		
		try{
			comando = "SELECT * FROM ENOC.ALUM_REFERENCIA "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				AlumReferencia alum = new AlumReferencia();
				alum.mapeaReg(rs);
				lista.add(alum);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumReferenciaUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lista;
	}
}