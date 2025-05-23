package aca.pg.archivos;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ArchivosAlumno {
	private String archivoId;
	private String folio;
	private String codigoPersonal;
	private String fecha;
	private String nombre;
	private String comentario;
	private String tamano;
	private String status;
	private int archivo;
	
	public ArchivosAlumno(){
		archivoId		= "";
		folio			= "";
		codigoPersonal	= "";
		fecha			= "";
		nombre			= "";
		comentario		= "";
		tamano			= "";
		status			= "";
		archivo			= 0;
	}

	/**
	 * @return the archivoId
	 */
	public String getArchivoId() {
		return archivoId;
	}

	/**
	 * @param archivoId the archivoId to set
	 */
	public void setArchivoId(String archivoId) {
		this.archivoId = archivoId;
	}

	/**
	 * @return the folio
	 */
	public String getFolio() {
		return folio;
	}

	/**
	 * @param folio the folio to set
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}

	/**
	 * @return the codigoPersonal
	 */
	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	/**
	 * @param codigoPersonal the codigoPersonal to set
	 */
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	/**
	 * @return the fecha
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the comentario
	 */
	public String getComentario() {
		return comentario;
	}

	/**
	 * @param comentario the comentario to set
	 */
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	/**
	 * @return the tamano
	 */
	public String getTamano() {
		return tamano;
	}

	/**
	 * @param tamano the tamano to set
	 */
	public void setTamano(String tamano) {
		this.tamano = tamano;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the archivo
	 */
	public int getArchivo() {
		return archivo;
	}

	/**
	 * @param archivo the archivo to set
	 */
	public void setArchivo(int archivo) {
		this.archivo = archivo;
	}
	
	public boolean insertReg(Connection conn) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("INSERT INTO" +
				" PORTAL.ARCHIVOS_ALUMNO(ARCHIVO_ID, FOLIO, CODIGO_PERSONAL, FECHA," +
		    	" NOMBRE, COMENTARIO, TAMANO, STATUS, ARCHIVO)" +
				" VALUES(?, TO_NUMBER(?, '9999999999'), ?, now()," +
				" ?, ?, TO_NUMBER(?, '9999999999'), ?, ?)");
			
			ps.setString(1, archivoId);
			ps.setString(2, folio);			
			ps.setString(3, codigoPersonal);
			ps.setString(4, nombre);
			ps.setString(5, comentario);
			ps.setString(6, tamano);
			ps.setString(5, status);
			ps.setInt(6, archivo);
			
			if(ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;			
			
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivos.ArchivosAlumno|insertReg|:"+ex);			
		}finally{
			if(ps!=null) ps.close();
		}
		
		return ok;
	}
	
	public boolean updateStatus(Connection conn) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE PORTAL.ARCHIVOS_ALUMNO" + 
				" SET STATUS = ?" +
				" WHERE CODIGO_PERSONAL = ?" +
				" AND ARCHIVO_ID = ?" +
				" AND FOLIO = TO_NUMBER(?, '99999')");
			
			ps.setString(1, status);
			ps.setString(2, codigoPersonal);
			ps.setString(3, archivoId);
			ps.setString(4, folio);
						
			if(ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivos.ArchivosAlumno|updateStatus|:"+ex);		
		}finally{
			if(ps!=null) ps.close();
		}
		
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		archivoId		= rs.getString("ARCHIVO_ID");
		folio			= rs.getString("FOLIO");
		codigoPersonal	= rs.getString("CODIGO_PERSONAL");
		fecha			= rs.getString("FECHA");
		nombre			= rs.getString("NOMBRE");
		comentario		= rs.getString("COMENTARIO");
		tamano			= rs.getString("TAMANO");
		status			= rs.getString("STATUS");
		archivo			= rs.getInt("ARCHIVO");
	}
	
	public void mapeaRegId(Connection conn, String codigoPersonal, String archivoId, String folio) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{ 
			ps = conn.prepareStatement("SELECT ARCHIVO_ID, FOLIO, CODIGO_PERSONAL, FECHA," +
			    		" NOMBRE, COMENTARIO, TAMANO, STATUS, ARCHIVO" +
			    		" FROM PORTAL.ARCHIVOS_ALUMNO" + 
						" WHERE CODIGO_PERSONAL = ?" +
						" AND ARCHIVO_ID = ?" +
						" AND FOLIO = TO_NUMBER(?, '999999')");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, archivoId);
			ps.setString(3, folio);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivos.ArchivosAlumno|mapeaRegId|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
		}
	}
	
	public boolean deleteReg(Connection conn) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ResultSet rs = null;
		    String sql="SELECT LO_UNLINK(ARCHIVO) AS RESULTADO FROM PORTAL.ARCHIVOS_ALUMNO "+ 
		    		"WHERE ARCHIVO_ID = ? "+
		    		"AND FOLIO = TO_NUMBER(?, '9999999999') "+
		    		"AND CODIGO_PERSONAL = ? ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, archivoId);
			ps.setString(2, folio);
			ps.setString(3, codigoPersonal);
			
			rs = ps.executeQuery();
			if(rs.next()){
			    ok=true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivos.ArchivosAlumno|deleteReg(OID)|:"+ex);
		}finally{
			if(ps!=null) ps.close();
		}
		ps = null;
		if(ok){
			ok = false;
			try{
			    String sql="DELETE FROM PORTAL.ARCHIVOS_ALUMNO "+ 
			    		"WHERE ARCHIVO_ID = ? "+
			    		"AND FOLIO = ? "+
			    		"AND CODIGO_PERSONAL = ? ";
				ps = conn.prepareStatement(sql);
				ps.setString(1, archivoId);
				ps.setString(2, folio);
				ps.setString(3, codigoPersonal);
				if(ps.executeUpdate()==1){
				    ok=true;					
				}
			}catch(Exception ex){
				System.out.println("Error - aca.pg.archivos.ArchivosAlumno|deleteReg(fila)|:"+ex);
			}finally{
				ps.close();
			}
		}else{
			System.out.println("No se borro la imagen y tampoco se borro la fila en alumno");
		}
		return ok;
	}
	
	public boolean guardaArchivoEnBD(Connection conn, String nombre, String ruta) throws SQLException{
		boolean ok = false;
		int folio=0;
		PreparedStatement ps	=null;
		PreparedStatement ps2	=null;
		try{
			conn.setAutoCommit(false);			
			String sql="SELECT COALESCE(MAX(FOLIO),0)+1 FROM PORTAL.ARCHIVOS_ALUMNO WHERE ARCHIVO_ID=? AND CODIGO_PERSONAL=?"; 
		    ps = conn.prepareStatement(sql);
			ps.setString(1, this.archivoId);
			ps.setString(2, this.codigoPersonal);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
				folio = rs.getInt(1);
		
			if(nombre == null)
				this.nombre = "Mensaje:";	
			String nombreTemp = this.nombre;
			nombreTemp=nombreTemp.replaceAll("x","a");
		    nombreTemp=nombreTemp.replaceAll("x","e");
		    nombreTemp=nombreTemp.replaceAll("x","i");
		    nombreTemp=nombreTemp.replaceAll("x","o");
		    nombreTemp=nombreTemp.replaceAll("x","u");
		    nombreTemp=nombreTemp.replaceAll("x","n");
		    
		    nombreTemp=nombreTemp.replaceAll("x","A");
		    nombreTemp=nombreTemp.replaceAll("x","E");
		    nombreTemp=nombreTemp.replaceAll("x","I");
		    nombreTemp=nombreTemp.replaceAll("x","O");
		    nombreTemp=nombreTemp.replaceAll("x","U");
		    nombreTemp=nombreTemp.replaceAll("x","N");
		    
		    nombreTemp=nombreTemp.replaceAll("#","Num.");
		    nombreTemp=nombreTemp.replaceAll("\"","-");
		    
		    this.nombre = nombreTemp;
		    
		    String comentario = this.comentario;
		    comentario = comentario.replaceAll("\"","-");
		    comentario = comentario.replaceAll("#", "Num.");
		    this.comentario = comentario;
	
			
		    ps2 = conn.prepareStatement("INSERT INTO PORTAL.ARCHIVOS_ALUMNO(ARCHIVO_ID, " + 
				"FOLIO,CODIGO_PERSONAL,FECHA,NOMBRE,COMENTARIO,TAMANO,STATUS,ARCHIVO) " +
				"VALUES(?,?,?,localtimestamp,?,?,?,?,?)");
	    
		    ps2.setString(1, this.archivoId);
			ps2.setInt(2, folio);
			ps2.setString(3, this.codigoPersonal);
			ps2.setString(4, this.nombre);
			ps2.setString(5, this.comentario);
			
			
			if(this.nombre.equals("Mensaje:")){
			    ps2.setLong(6, 0);
				ps2.setString(7, "N");
				ps2.setBytes(8, null);
				ps2.executeUpdate();
				conn.commit();
			}else{
			    File fi = new File(ruta+"/"+nombre);
			    ps2.setLong(6,fi.length());
			    ps2.setString(7,"N");
				FileInputStream fis = new FileInputStream(fi);
				org.postgresql.largeobject.LargeObjectManager lom = ((org.postgresql.PGConnection)conn).getLargeObjectAPI();
				long oid = lom.createLO(org.postgresql.largeobject.LargeObjectManager.READ | org.postgresql.largeobject.LargeObjectManager.WRITE);
				org.postgresql.largeobject.LargeObject obj = lom.open(oid, org.postgresql.largeobject.LargeObjectManager.WRITE);
				byte buf[] = new byte[(int)fi.length()];
				int le; 
				while ((le=fis.read(buf)) !=-1){
					obj.write(buf,0,le);
				}
				ps2.setLong(8,oid);
				
			   	ps2.executeUpdate();
			    conn.commit();
				fis.close();
				fi.delete();
			}			
			
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivos.ArchivosAlumno|guardaArchivoEnBD|:"+ex);
		}
		finally{
			if(ps!=null)ps.close();
			if(ps2!=null)ps2.close();
			conn.setAutoCommit(true);
		}
		return ok;
	}
}