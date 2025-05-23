package aca.residencia;

import java.sql.*;
import java.util.ArrayList;



public class DatosUtil {
	
	public ArrayList<ResDatos> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<ResDatos> lisDato	= new ArrayList<ResDatos>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT MATRICULA, PERIODO_ID, CALLE, COLONIA, " +
					"MUNICIPIO, TEL_AREA, TEL_NUMERO, TUT_NOMBRE, " +
					"TUT_APELLIDOS, RAZON, USUARIO, " +
					"TO_CHAR(FECHA,'DD/MM/YYYY') FECHA, NUMERO " +
					" FROM ENOC.RES_DATOS "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ResDatos dato = new ResDatos();
				dato.mapeaReg(rs);
				lisDato.add(dato);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.DatosUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisDato;
	}
	
	public ArrayList<ResDatos> getLista(Connection conn, String matricula, String orden ) throws SQLException{
		
		ArrayList<ResDatos> lisDato	= new ArrayList<ResDatos>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT MATRICULA, PERIODO_ID, CALLE, COLONIA, " +
					"MUNICIPIO, TEL_AREA, TEL_NUMERO, TUT_NOMBRE, " +
					"TUT_APELLIDOS, RAZON, USUARIO, " +
					"TO_CHAR(FECHA,'DD/MM/YYYY') FECHA, NUMERO " +
					"FROM ENOC.RES_DATOS WHERE MATRICULA = '"+matricula+"' "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ResDatos dato = new ResDatos();
				dato.mapeaReg(rs);
				lisDato.add(dato);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.DatosUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisDato;
	}

	
	public ArrayList<ResDatos> getExternos(Connection conn, String orden ) throws SQLException{
		
		ArrayList<ResDatos> lisDato	= new ArrayList<ResDatos>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT ENOC.ALUM_APELLIDO(MATRICULA) AS NOMBRE, " +			
			"CALLE||' '|| CASE NUMERO WHEN 's/n' THEN NUMERO ELSE '# '||NUMERO END AS CALLE, "+
			"COLONIA, "+
			"MUNICIPIO, TEL_AREA, " +
			"TEL_NUMERO AS TELEFONO, "+
			"TUT_NOMBRE||' '||TUT_APELLIDOS AS TUTOR, "+
			"RAZON, "+
			"USUARIO, FECHA, NUMERO, PERIODO_ID "+			
		 	"FROM ENOC.RES_DATOS A, ENOC.INSCRITOS B "+ 
			"WHERE B.CODIGO_PERSONAL = A.MATRICULA ORDER BY FACULTAD, CARRERA, NOMBRE ";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ResDatos dato = new ResDatos();
				dato.mapeaReg(rs);
				lisDato.add(dato);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.DatosUtil|getExternos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisDato;
	}

	/**
	 * @author Elifo
	 * @param conn Conexixn a la base de datos
	 * @param orden Orden en el que se traerxn los datos
	 * @return ArrayList de ResDatos de los alumnos externos inscritos
	 * */
	public ArrayList<ResDatos> getListExtInscritos(Connection conn, String orden ) throws SQLException{
		
		ArrayList<ResDatos> lisDato	= new ArrayList<ResDatos>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT MATRICULA, PERIODO_ID, CALLE, COLONIA," +
					" MUNICIPIO, TEL_AREA, TEL_NUMERO, TUT_NOMBRE," +
					" TUT_APELLIDOS, RAZON, USUARIO," +
					" TO_CHAR(FECHA,'DD/MM/YYYY') FECHA, NUMERO" +
					" FROM ENOC.RES_DATOS" + 
					" WHERE MATRICULA IN (SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS" +
										" WHERE RESIDENCIA_ID = 'E') "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ResDatos dato = new ResDatos();
				dato.mapeaReg(rs);
				lisDato.add(dato);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.DatosUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisDato;
	}
	
	public ArrayList<ResDatos> getListExtInscritosModalidad(Connection conn, String modalidades, String fechaIni, String fechaFin, String orden ) throws SQLException{
		
		ArrayList<ResDatos> lisDato	= new ArrayList<ResDatos>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT MATRICULA, PERIODO_ID, CALLE, COLONIA," +
					" MUNICIPIO, TEL_AREA, TEL_NUMERO, TUT_NOMBRE," +
					" TUT_APELLIDOS, RAZON, USUARIO," +
					" TO_CHAR(FECHA,'DD/MM/YYYY') FECHA, NUMERO" +
					" FROM ENOC.RES_DATOS" + 
					" WHERE MATRICULA IN (SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS" +
										" WHERE RESIDENCIA_ID = 'E' AND MODALIDAD_ID IN ("+modalidades+")) "+
					" AND FECHA BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY') "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ResDatos dato = new ResDatos();
				dato.mapeaReg(rs);
				lisDato.add(dato);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.DatosUtil|getListExtInscritosModalidad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisDato;
	}

}