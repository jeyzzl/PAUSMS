/**
 * 
 */
package aca.pg.archivos;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author ifo
 *
 */
public class ArchivosProfesor {
	private String archivoId;
	private String folio;
	private String codigoPersonal;
	private String fecha;
	private String nombre;
	private String comentario;
	private String autorizacion;
	private String tamano;
	private long archivo;
	
	public ArchivosProfesor(){
		archivoId		= "";
		folio			= "";
		codigoPersonal	= "";
		fecha			= "";
		nombre			= "";
		comentario		= "";
		autorizacion	= "";
		tamano			= "";
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
	 * @return the autorizacion
	 */
	public String getAutorizacion() {
		return autorizacion;
	}

	/**
	 * @param autorizacion the autorizacion to set
	 */
	public void setAutorizacion(String autorizacion) {
		this.autorizacion = autorizacion;
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
	 * @return the archivo
	 */
	public long getArchivo() {
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
				" PORTAL.ARCHIVOS_PROFESOR(ARCHIVO_ID, FOLIO, CODIGO_PERSONAL, FECHA," +
		    	" NOMBRE, COMENTARIO, AUTORIZACION, TAMANO, ARCHIVO)" +
				" VALUES(?, TO_NUMBER(?, '9999999999'), ?, now()," +
				" ?, ?, ?, TO_NUMBER(?, '9999999999'), ?)");
			
			ps.setString(1, archivoId);
			ps.setString(2, folio);			
			ps.setString(3, codigoPersonal);
			ps.setString(4, fecha);
			ps.setString(5, nombre);
			ps.setString(6, comentario);
			ps.setString(7, autorizacion);
			ps.setString(8, tamano);
			ps.setLong(9, archivo);
			
			if(ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;			
			
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivos.ArchivosProfesor|insertReg|:"+ex);			
		}finally{
			if(ps!=null) ps.close();
		}
		
		return ok;
	}
	
	public boolean updateAutorizacion(Connection conn) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE PORTAL.ARCHIVOS_ALUMNO" + 
				" SET AUTORIZACION = ?" +
				" WHERE ARCHIVO_ID = ?" +
				" AND FOLIO = TO_NUMBER(?, '99999')");
			
			ps.setString(1, autorizacion);
			ps.setString(2, archivoId);
			ps.setString(3, folio);
						
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
		autorizacion	= rs.getString("AUTORIZACION");
		tamano			= rs.getString("TAMANO");
		archivo			= rs.getInt("ARCHIVO");
	}
	
	public void mapeaRegId(Connection conn, String archivoId, String folio) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{ 
			ps = conn.prepareStatement("SELECT ARCHIVO_ID, FOLIO, CODIGO_PERSONAL, FECHA," +
			    		" NOMBRE, COMENTARIO, AUTORIZACION, TAMANO, ARCHIVO" +
			    		" FROM PORTAL.ARCHIVOS_PROFESOR" + 
						" WHERE ARCHIVO_ID = ?" +
						" AND FOLIO = TO_NUMBER(?, '999999')");
			
			ps.setString(1, archivoId);
			ps.setString(2, folio);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivos.ArchivosProfesor|mapeaRegId|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
		}
	}
	
	public boolean deleteReg(Connection conn, String nombre) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			if(!nombre.equals("Mensaje:")){
				ResultSet rs = null;
			    String sql="SELECT LO_UNLINK(ARCHIVO) AS RESULTADO FROM PORTAL.ARCHIVOS_PROFESOR "+ 
			    		"WHERE ARCHIVO_ID = ? "+
			    		"AND FOLIO = TO_NUMBER(?, '9999999999') ";
				ps = conn.prepareStatement(sql);
				ps.setString(1, archivoId);
				ps.setString(2, folio);
				
				rs = ps.executeQuery();
				if(rs.next()){
				    ok = true;
				}
			}else{
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivos.ArchivosProfesor|deleteReg(OID)|:"+ex);
		}finally{
			if(ps != null)ps.close();
		}
		ps = null;
		if(ok){
			ok = false;
			try{
			    String sql="DELETE FROM PORTAL.ARCHIVOS_PROFESOR "+ 
			    		"WHERE ARCHIVO_ID = ? "+
			    		"AND FOLIO = ? "+
			    		"AND CODIGO_PERSONAL = ? ";
				ps = conn.prepareStatement(sql);
				ps.setString(1, archivoId);
				ps.setString(2, folio);
				ps.setString(3, codigoPersonal);
				
				if(ps.executeUpdate()==1){
				    ok=true;
					conn.commit();
				}
			}catch(Exception ex){
				System.out.println("Error - aca.pg.archivos.ArchivosProfesor|deleteReg(fila)|:"+ex);
			}finally{
				if(ps != null)ps.close();
			}
		}else{
			System.out.println("No se borro la imagen y tampoco se borro la fila en alumno");
		}
		return ok;
	}
	
	public boolean guardaArchivoBD(Connection conn, String ruta, String nombre) throws SQLException{
		boolean ok = false;
		int folio=0;
		PreparedStatement ps 	= null;
		PreparedStatement ps2 	= null;
		try{
			
			conn.setAutoCommit(false);
		    String sql="SELECT COALESCE(MAX(FOLIO),0)+1 FROM PORTAL.ARCHIVOS_PROFESOR WHERE ARCHIVO_ID=? AND CODIGO_PERSONAL=?"; 
		    ps2 = conn.prepareStatement(sql);
			ps2.setString(1,this.getArchivoId());
			ps2.setString(2,this.getCodigoPersonal());
			ResultSet rs = ps2.executeQuery();
			if(rs.next())folio=rs.getInt(1);
			
			if(this.getNombre()==null)this.setNombre("Mensaje:");
			
			String nombreTemp=this.getNombre();
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
		    this.setNombre(nombreTemp);
			
			ps = conn.prepareStatement("INSERT INTO PORTAL.ARCHIVOS_PROFESOR(ARCHIVO_ID, " + 
					"FOLIO,CODIGO_PERSONAL,FECHA,NOMBRE,COMENTARIO,AUTORIZACION,TAMANO,ARCHIVO) " +
					"VALUES(?,?,?,localtimestamp,?,?,?,?,?)");
			
			ps.setString(1,this.getArchivoId());
			ps.setInt(2, folio);
			ps.setString(3,this.getCodigoPersonal());
			ps.setString(4,this.getNombre());
			ps.setString(5,this.getComentario());
			ps.setString(6,this.getAutorizacion());
			
			if(this.getNombre().equals("Mensaje:")){
				ps.setLong(7,0);
				ps.setInt(8, 0);
				if(ps.executeUpdate() >= 1)
					ok = true;
			}else{
				File fi = new File(ruta+"/"+nombre);
				ps.setLong(7,fi.length());
				FileInputStream fis = new FileInputStream(fi);
				org.postgresql.largeobject.LargeObjectManager lom = ((org.postgresql.PGConnection)conn).getLargeObjectAPI();
				long oid = lom.createLO(org.postgresql.largeobject.LargeObjectManager.READ | org.postgresql.largeobject.LargeObjectManager.WRITE);
				org.postgresql.largeobject.LargeObject obj = lom.open(oid, org.postgresql.largeobject.LargeObjectManager.WRITE);
				byte buf[] = new byte[(int)fi.length()];
				int le; 
				while ((le=fis.read(buf)) !=-1){
					obj.write(buf,0,le);
				}
				ps.setLong(8,oid);
			   	if(ps.executeUpdate() >= 1)
			   		ok = true;
			    conn.commit();
				fis.close();
				fi.delete();
			}
			conn.commit();
			
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivos.ArchivosProfesor|guardaArchivoBd|:"+ex);
		}finally{
			if(ps != null)ps.close();
			if(ps2 != null)ps2.close();
			conn.setAutoCommit(true);
		}
		return ok;
	}
	
	public static boolean tieneArchivos(Connection conn, String archivoId, String codigoAlumno) throws SQLException{
		PreparedStatement ps = null;
		boolean ok		= false;
		ResultSet rs	= null;		
		try{ 
			ps = conn.prepareStatement("SELECT ARCHIVO_ID, FOLIO, CODIGO_PERSONAL, TO_CHAR(FECHA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA, NOMBRE, COMENTARIO, TAMANO, ARCHIVO" +
						" FROM PORTAL.ARCHIVOS_PROFESOR" + 
						" WHERE ARCHIVO_ID LIKE ?||'%' AND POSITION(? IN AUTORIZACION) > 0");
			
			ps.setString(1, archivoId);
			ps.setString(2, codigoAlumno);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivos.ArchivosProfesor|tieneArchivos|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
		}
		
		return ok;
	}
}