// Bean de la tabla KRDX_ALUMNO_EVAL
package  aca.kardex;

import java.sql.*;

public class KrdxAlumnoEval{
	private String codigoPersonal;
	private String cursoCargaId;
	private String evaluacionId;
	private String nota;
	private String evaluacionE42;
	
	public KrdxAlumnoEval(){
		codigoPersonal		= "";
		cursoCargaId		= "";
		evaluacionId		= "";
		nota				= "";
		evaluacionE42		= "";
	}
	
	public String getCodigoPersonal(){
		return codigoPersonal;
	}
	
	public void setCodigoPersonal( String codigoPersonal){
		this.codigoPersonal = codigoPersonal;
	}	
	
	public String getCursoCargaId(){
		return cursoCargaId;
	}
	
	public void setCursoCargaId( String cursoCargaId){
		this.cursoCargaId = cursoCargaId;
	}	
	
	public String getEvaluacionId(){
		return evaluacionId;
	}
	
	public void setEvaluacionId( String evaluacionId){
		this.evaluacionId = evaluacionId;
	}
	
	public String getNota(){
		return nota;
	}
	
	public void setNota( String nota){
		this.nota = nota;
	}
	
	public String getEvaluacionE42() {
		return evaluacionE42;
	}

	public void setEvaluacionE42(String evaluacionE42) {
		this.evaluacionE42 = evaluacionE42;
	}

	public boolean insertReg(Connection conn ) throws Exception{
		boolean ok 				= false;
		PreparedStatement ps 	= null;
		try{
			if (evaluacionE42==null || evaluacionE42.equals("")) evaluacionE42="0";
			if (nota.equals("-")) nota = "0"; 
			ps = conn.prepareStatement("INSERT INTO ENOC.KRDX_ALUMNO_EVAL"+ 
				"(CODIGO_PERSONAL, CURSO_CARGA_ID, EVALUACION_ID, NOTA, EVALUACION_E42 ) "+
				"VALUES( ?, ?, TO_NUMBER(?,'99'), TO_NUMBER(?,'999.99')," +
				"TO_NUMBER(?,'9999999'))");				
			ps.setString(1, codigoPersonal);
			ps.setString(2, cursoCargaId);
			ps.setString(3, evaluacionId);
			ps.setString(4, nota);
			ps.setString(5, evaluacionE42);			
			if (ps.executeUpdate()== 1)
				ok = true;
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxAlumnoEval|insertReg|:"+ex);
			//System.out.println("Datos:"+codigoPersonal+":"+cursoCargaId+":"+evaluacionId+":"+nota+":"+evaluacionE42);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			if (evaluacionE42==null) evaluacionE42="0";
			ps = conn.prepareStatement("UPDATE ENOC.KRDX_ALUMNO_EVAL "+ 
				"SET NOTA = TO_NUMBER(?,'999.99'), " +
				"EVALUACION_E42 = TO_NUMBER(?,'9999999') "+
				"WHERE CODIGO_PERSONAL = ? "+
				"AND CURSO_CARGA_ID = ? "+
				"AND EVALUACION_ID = TO_NUMBER(?,'99')");
			ps.setString(1, nota);
			ps.setString(2, evaluacionE42);
			ps.setString(3, codigoPersonal);
			ps.setString(4, cursoCargaId);
			ps.setString(5, evaluacionId);
			if (ps.executeUpdate()== 1)
				ok = true;
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxAlumnoEval|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean updateRegE42(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			if (evaluacionE42!=null && !evaluacionE42.equals("0")){
				ps = conn.prepareStatement("UPDATE ENOC.KRDX_ALUMNO_EVAL "+ 
					"SET NOTA = TO_NUMBER(?,'999.99') "+
					"WHERE CODIGO_PERSONAL = ? "+
					"AND EVALUACION_E42 = TO_NUMBER(?,'9999999')");
				ps.setString(1, nota);			
				ps.setString(2, codigoPersonal);
				ps.setString(3, evaluacionE42);			
				if (ps.executeUpdate()== 1)
					ok = true;	
				else
					ok = false;				
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxAlumnoEval|updateRegE42|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.KRDX_ALUMNO_EVAL "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND CURSO_CARGA_ID = ? "+
				"AND EVALUACION_ID = TO_NUMBER(?,'99')");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cursoCargaId);
			ps.setString(3, evaluacionId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxAlumnoEval|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	public boolean deleteRegE42(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.KRDX_ALUMNO_EVAL "+ 
				"WHERE CODIGO_PERSONAL = ? "+				
				"AND EVALUACION_E42 = TO_NUMBER(?,'9999999')");
			ps.setString(1, codigoPersonal);		
			ps.setString(2, evaluacionE42);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxAlumnoEval|deleteRegE42|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal		= rs.getString("CODIGO_PERSONAL");
		cursoCargaId 		= rs.getString("CURSO_CARGA_ID");
		evaluacionId		= rs.getString("EVALUACION_ID");
		nota	 			= rs.getString("NOTA");
		evaluacionE42		= rs.getString("EVALUACION_E42");
	}
	
	public void mapeaRegArrayListSinNotas(ResultSet rs ) throws SQLException{
		cursoCargaId 		= rs.getString("CURSO_CARGA_ID");
		evaluacionId		= rs.getString("EVALUACION_ID");
		nota	 			= rs.getString("NOTA");	
	}
	
	public void mapeaRegId( Connection conn, String codigoPersonal, String cursoCargaId, String evaluacionId) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{ 
			ps = conn.prepareStatement("SELECT "+
				"CODIGO_PERSONAL, CURSO_CARGA_ID, EVALUACION_ID, NOTA, EVALUACION_E42 "+
				"FROM ENOC.KRDX_ALUMNO_EVAL "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND CURSO_CARGA_ID = ? "+
				"AND EVALUACION_ID = TO_NUMBER(?,'99')");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cursoCargaId);
			ps.setString(3, evaluacionId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxAlumnoEval|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean ok 				= false;
		ResultSet rs			= null;
		PreparedStatement ps	= null;		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.KRDX_ALUMNO_EVAL "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND CURSO_CARGA_ID = ? "+
				"AND EVALUACION_ID = TO_NUMBER(?,'99')");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cursoCargaId);
			ps.setString(3, evaluacionId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxAlumnoEval|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
		
	public boolean existeRegE42(Connection conn) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.KRDX_ALUMNO_EVAL "+ 
				"WHERE CODIGO_PERSONAL = ? "+				
				"AND EVALUACION_E42 = TO_NUMBER(?,'9999999')");
			ps.setString(1, codigoPersonal);
			ps.setString(2, evaluacionE42);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxAlumnoEval|existeRegE42|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static String getEvaluacionId(Connection conn, String codigoPersonal, String evaluacionE42 ) throws SQLException{
		ResultSet 			rs	= null;
		PreparedStatement 	ps	= null;
		String evaluacion		= "0";
		
		try{
			ps = conn.prepareStatement("SELECT EVALUACION_ID FROM ENOC.KRDX_ALUMNO_EVAL "+ 
				"WHERE CODIGO_PERSONAL = ? "+				
				"AND EVALUACION_E42 = TO_NUMBER(?,'9999999')");
			ps.setString(1, codigoPersonal);
			ps.setString(2, evaluacionE42);
			
			rs = ps.executeQuery();
			if (rs.next())
				 evaluacion = rs.getString("EVALUACION_ID");
			else
				evaluacion ="0";
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxAlumnoEval|getEvaluacionId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return evaluacion;
	}
	
	public static String getAlumnoPromedio(Connection Conn, String cursoCargaId, String codigoPersonal ) throws SQLException{
		
		Statement st 		= Conn.createStatement();
		ResultSet rs 		= null;
		ResultSet rs2 		= null;
		ResultSet rs3 		= null;
		String comando	= "";
		String dev = "";
		double evaluacion = 0;		
		try{
			// comando = "SELECT TO_CHAR(FLOOR(ROUND(((SUM(DECODE(TIPO, 'P', NOTA, (VALOR*NOTA/100)))*100)/SUM(VALOR))+0.5,2)),'999.99') AS PROMEDIO "+
			// decode(trunc((SUM(VALOR)-1)/100),0,SUM(VALOR),100) :
			// SELECT ((SUM(DECODE(TIPO, 'P', NOTA, (VALOR*NOTA/100)))*100)/decode(trunc((SUM(VALOR)-1)/100),0,SUM(VALOR),100)) AS PROMEDIO
			// Si la suma de las estrategias es mayor de 100 entonces devolvera 100, de lo contrario devuelve la suma.
			
			comando = " SELECT (COALESCE(SUM(VALOR*NOTA/100),0) /"+
					" COALESCE( CASE WHEN SUM(VALOR * (CASE EVALUADAS WHEN 0 THEN 100 ELSE EVALUADAS END)/100) = 0 THEN 1 ELSE SUM(VALOR * (CASE EVALUADAS WHEN 0 THEN 100 ELSE EVALUADAS END)/100) END,1) *100) AS PROMEDIO"+				 
				" FROM ENOC.ALUMNO_EFICIENCIA "+
				" WHERE CURSO_CARGA_ID = '"+cursoCargaId+"' "+
				" AND CODIGO_PERSONAL = '"+codigoPersonal+"'";
			
			rs = st.executeQuery(comando);
			if (rs.next()){
				evaluacion = rs.getDouble("PROMEDIO");
				
				double pextra = 0;
				comando = "SELECT COALESCE(SUM(NOTA),0) AS PEXTRA FROM ENOC.ALUMNO_EVALUACION WHERE CURSO_CARGA_ID = '"+cursoCargaId+"' "+ 
				"AND CODIGO_PERSONAL = '"+codigoPersonal+"' AND TIPO = 'E'";			
				rs2 = st.executeQuery(comando);
				if (rs2.next()) pextra = rs2.getDouble("PEXTRA");				
				evaluacion += pextra;
				if (evaluacion>100)	evaluacion = 100;
				
				comando = "SELECT ROUND("+evaluacion+") FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'";
				rs3 = st.executeQuery(comando);
				if (rs3.next()) dev = rs3.getString(1);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxAlumnoEval|getAlumnoPromedio|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return dev;
	}
	
	public static boolean existenEvaluaciones(Connection conn, String codigoPersonal, String cursoCargaId ) throws SQLException{
		ResultSet 			rs	= null;
		PreparedStatement 	ps	= null;
		boolean ok				= false;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.KRDX_ALUMNO_EVAL" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND CURSO_CARGA_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cursoCargaId);
			
			rs = ps.executeQuery();
			if(rs.next())
				ok = true;
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxAlumnoEval|existenEvaluaciones|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static boolean elimnarEvaluaciones(Connection conn, String codigoPersonal, String cursoCargaId ) throws SQLException{
		ResultSet 			rs	= null;
		PreparedStatement 	ps	= null;
		boolean ok				= false;
		
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.KRDX_ALUMNO_EVAL" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND CURSO_CARGA_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cursoCargaId);
			
			if(ps.executeUpdate() > 0)
				ok = true;
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxAlumnoEval|elimnarEvaluaciones|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return ok;
	}
	
	public static int getNumEst(Connection conn, String cursoCargaId ) throws SQLException{
		
		PreparedStatement 	ps	= null;
		ResultSet 			rs	= null;		
		int numReg			= 0;
		
		try{
			ps = conn.prepareStatement(" SELECT COALESCE(COUNT(CURSO_CARGA_ID),0) AS NUMREG FROM ENOC.KRDX_ALUMNO_EVAL" + 
					" WHERE CURSO_CARGA_ID = ?");			
			ps.setString(1, cursoCargaId);			
			rs = ps.executeQuery();
			if(rs.next())
				numReg = rs.getInt("NUMREG");
			
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxAlumnoEval|getNumEst|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numReg;
	}
	
	public static int deleteAlumEval(Connection conn, String cursoCargaId ) throws SQLException{
		
		PreparedStatement ps	= null;
		int numEval				= 0;
		
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.KRDX_ALUMNO_EVAL WHERE CURSO_CARGA_ID = ?"); 
			ps.setString(1, cursoCargaId);
			numEval = ps.executeUpdate();
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxAlumnoEval|deleteAlumEval|:"+ex);
		}finally{			
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numEval;
	}
	
}