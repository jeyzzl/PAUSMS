package aca.kardex;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class KrdxCursoCal {
	private String codigoPersonal;
	private String cursoCargaId;
	private String cursoId;
	private String fecha;
	private String fechaFinal; 
	private String nota;
	private String tipo;
	private String estado;
	private String tipoCalId;
	private String tipoNota;
	
	public KrdxCursoCal(){
		codigoPersonal	= "";
		cursoCargaId	= "";
		cursoId			= "";
		fecha			= "";
		fechaFinal		= "";
		nota			= "";
		tipo 			= "";
		estado			= "";
		tipoCalId		= "";
		tipoNota		= "";
	}
	
	
	public String getTipoCalId() {
		return tipoCalId;
	}


	public void setTipoCalId(String tipoCalId) {
		this.tipoCalId = tipoCalId;
	}


	public String getTipoNota() {
		return tipoNota;
	}


	public void setTipoNota(String tipoNota) {
		this.tipoNota = tipoNota;
	}


	public String getCodigoPersonal() {
		return codigoPersonal;
	}


	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}


	public String getCursoCargaId() {
		return cursoCargaId;
	}


	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}


	public String getCursoId() {
		return cursoId;
	}


	public void setCursoId(String cursoId) {
		this.cursoId = cursoId;
	}


	public String getFecha() {
		return fecha;
	}


	public void setFecha(String fecha) {
		this.fecha = fecha;
	}


	public String getNota() {
		return nota;
	}


	public void setNota(String nota) {
		this.nota = nota;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	/**
	 * @return the fechaFinal
	 */
	public String getFechaFinal() {
		return fechaFinal;
	}


	/**
	 * @param fechaFinal the fechaFinal to set
	 */
	public void setFechaFinal(String fechaFinal) {
		this.fechaFinal = fechaFinal;
	}


	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}


	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public boolean insertReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.KRDX_CURSO_CAL "+ 
				"(CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, FECHA, FECHA_FINAL, NOTA, TIPO, ESTADO, TIPO_NOTA, TIPOCAL_ID) "+		
				"VALUES( ?, ?, ?, TO_DATE(?,'DD/MM/YYYY'), TO_DATE(?,'DD/MM/YYYY'), TO_NUMBER(?,'999'), ?, "+
				" ?, ?, ? )");				
			ps.setString(1, codigoPersonal);
			ps.setString(2, cursoCargaId);
			ps.setString(3, cursoId);
			ps.setString(4, fecha);
			ps.setString(5, fechaFinal);
			ps.setString(6, nota);
			ps.setString(7, tipo);
			ps.setString(8, estado);
			ps.setString(9, tipoNota);
			ps.setString(10, tipoCalId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoCal|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.KRDX_CURSO_CAL "+ 
				" SET FECHA = TO_DATE(?,'DD/MM/YYYY'), " +
				" FECHA_FINAL = TO_DATE(?,'DD/MM/YYYY'), "+
				" NOTA = TO_NUMBER(?,'999')," +
				" TIPO = ?, "+
				" ESTADO = ?," +
				" TIPO_NOTA = ?, " +
				" TIPOCAL_ID = ? "+
				" WHERE CODIGO_PERSONAL = ? "+
				" AND CURSO_CARGA_ID = ? "+
				" AND CURSO_ID = ? ");
			ps.setString(1, fecha);
			ps.setString(2, fechaFinal);
			ps.setString(3, nota);
			ps.setString(4, tipo);
			ps.setString(5, estado);
			ps.setString(6, tipoNota);
			ps.setString(7, tipoCalId);
			ps.setString(8, codigoPersonal);
			ps.setString(9, cursoCargaId);
			ps.setString(10, cursoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoCal|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.KRDX_CURSO_CAL "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND CURSO_CARGA_ID = ? "+
				"AND CURSO_ID = ? ");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cursoCargaId);
			ps.setString(3, cursoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoCal|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteDiferidasMateria(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.KRDX_CURSO_CAL "+ 
				"WHERE CURSO_CARGA_ID = ? "+
				"AND CURSO_ID = ? ");			
			ps.setString(1, cursoCargaId);
			ps.setString(2, cursoId);
			
			if (ps.executeUpdate()>= 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoDif|deleteDiferidasMateria|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal 		= rs.getString("CODIGO_PERSONAL");
		cursoCargaId 		= rs.getString("CURSO_CARGA_ID");
		cursoId				= rs.getString("CURSO_ID");
		fecha	 			= rs.getString("FECHA");
		nota 				= rs.getString("NOTA");
		estado				= rs.getString("ESTADO");
		tipo				= rs.getString("TIPO");
		fechaFinal			=rs.getString("FECHA_FINAL");
		tipoNota			= rs.getString("TIPO_NOTA");
		tipoCalId			=rs.getString("TIPOCAL_ID");
	}
	
	public void mapeaRegId( Connection conn, String codigoPersonal, String cursoCargaId, String cursoId ) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{ 
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, NOTA, ESTADO, " +
					" TIPO, TO_CHAR(FECHA_FINAL,'DD/MM/YYYY') AS FECHA_FINAL, TIPO_NOTA, TIPOCAL_ID "+			
				" FROM ENOC.KRDX_CURSO_CAL "+ 
				" WHERE CODIGO_PERSONAL = ? "+
				" AND CURSO_CARGA_ID = ? "+
				" AND CURSO_ID = ? ");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cursoCargaId);
			ps.setString(3, cursoId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoCal|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.KRDX_CURSO_CAL "+ 
				" WHERE CODIGO_PERSONAL = ? "+
				" AND CURSO_CARGA_ID = ? "+
				" AND CURSO_ID = ? ");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cursoCargaId);
			ps.setString(3, cursoId);			
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoCal|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean tieneDiferidas(Connection conn) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.KRDX_CURSO_CAL "+ 
				" WHERE CURSO_CARGA_ID = ? "+
				" AND CURSO_ID = ? ");
			ps.setString(1, cursoCargaId);
			ps.setString(2, cursoId);			
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoCal|tieneDiferidas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static boolean tieneCambios(Connection conn, String cursoCargaId, String tipo) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.KRDX_CURSO_CAL WHERE CURSO_CARGA_ID = ? AND TIPO = '"+tipo+"'");
			ps.setString(1, cursoCargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoCal|tieneDiferidas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static int numCoreccion(Connection conn, String cursoCargaId) throws SQLException{		
		PreparedStatement 	ps	= null;
		ResultSet 			rs	= null;
		int numMat				= 0;
		try{
			ps = conn.prepareStatement("SELECT COUNT(*) AS NUMMAT FROM ENOC.KRDX_CURSO_CAL WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ? " +
					" AND TIPO = 'C'"); 
			ps.setString(1, cursoCargaId);			
			rs = ps.executeQuery();
			if (rs.next())
				numMat = rs.getInt("NUMMAT");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoCal|numMatCalculo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numMat;
	}
	
	public static String getFechaFinal(Connection conn, String codigoPersonal, String cursoCargaId, String cursoId, String tipo) throws SQLException{
		PreparedStatement 	ps	= null;
		ResultSet 			rs	= null;
		String fecha			= "-";
		try{
			ps = conn.prepareStatement("SELECT TO_CHAR(FECHA_FINAL,'DD/MM/YYYY') AS FECHA"
					+ " FROM ENOC.KRDX_CURSO_CAL"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CURSO_CARGA_ID = ?"
					+ " AND CURSO_ID = ?"
					+ " AND TIPO = ?"); 
			ps.setString(1, codigoPersonal);			
			ps.setString(2, cursoCargaId);
			ps.setString(3, cursoId);
			ps.setString(4, tipo);
			
			rs = ps.executeQuery();
			if (rs.next())
				fecha = rs.getString("FECHA");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoCal|getFechaFinal|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return fecha;
	}

}
