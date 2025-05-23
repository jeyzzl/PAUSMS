/*
 * Created on 20/10/2005
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package aca.archivos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Pedro
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ArchivosProfesor {
    
    public boolean guardaArchivo(Connection conn, ArchivoVO archivo,String ruta,String nombre) throws SQLException{
		boolean ok = false;
		int folio=0;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		
		try{
		    String sql="SELECT COALESCE(MAX(FOLIO),0)+1 FROM PORTAL.ARCHIVOS_PROFESOR WHERE ARCHIVO_ID=? AND CODIGO_PERSONAL=?"; 
		    ps = conn.prepareStatement(sql);
			ps.setString(1,archivo.getId());
			ps.setString(2,archivo.getCodigoPersonal());
			ResultSet rs = ps.executeQuery();
			if(rs.next())folio=rs.getInt(1);
			if(archivo.getNombre()==null)archivo.setNombre("Mensaje:");
			
			String nombreTemp=archivo.getNombre();
			
			nombreTemp=nombreTemp.replaceAll("á","a");
		    nombreTemp=nombreTemp.replaceAll("é","e");
		    nombreTemp=nombreTemp.replaceAll("í","i");
		    nombreTemp=nombreTemp.replaceAll("ó","o");
		    nombreTemp=nombreTemp.replaceAll("ú","u");
		    nombreTemp=nombreTemp.replaceAll("ñ","n");
		    nombreTemp=nombreTemp.replaceAll("Á","A");
		    nombreTemp=nombreTemp.replaceAll("É","E");
		    nombreTemp=nombreTemp.replaceAll("Í","I");
		    nombreTemp=nombreTemp.replaceAll("Ó","O");
		    nombreTemp=nombreTemp.replaceAll("Ú","U");
		    nombreTemp=nombreTemp.replaceAll("Ñ","N");	
			
		    archivo.setNombre(nombreTemp);
			
			ps2 = conn.prepareStatement("INSERT INTO PORTAL.ARCHIVOS_PROFESOR(ARCHIVO_ID, " + 
					"FOLIO,CODIGO_PERSONAL,FECHA,NOMBRE,COMENTARIO,AUTORIZACION,TAMANO) " +
					"VALUES(?,?,?,now(),?,?,?,?)");
			
			ps2.setString(1,archivo.getId());
			ps2.setInt(2,folio);
			ps2.setString(3,archivo.getCodigoPersonal());
			ps2.setString(4,archivo.getNombre());
			ps2.setString(5,archivo.getComentario());
			ps2.setString(6,archivo.getAutorizacion());
			
			if(archivo.getNombre().equals("Mensaje:")){
				ps2.setLong(7,0);
				ps2.executeUpdate();
			}else{
				File fi = new File(ruta+"/"+nombre);
				ps2.setLong(7,fi.length());
				ps2.executeUpdate();
				
				File fd = new File(ruta+"/P"+archivo.getId()+"M"+archivo.getCodigoPersonal()+"F"+folio+"N "+nombreTemp);
				FileInputStream fis = new FileInputStream(fi);
				FileOutputStream fos = new FileOutputStream(fd);
				byte[] zipped = new byte[ (int) fi.length()];
				int length = -1;
			    while ((length = fis.read(zipped)) != -1)
			    {
			        fos.write(zipped, 0, length);
			        fos.flush();
			    }
				fis.close();
				fos.close();
				fi.delete();
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.archivos.ArchivosProfesor|guardaArchivo|:"+ex);
		}finally{
			if (ps !=null) ps.close();
			if (ps2 !=null) ps2.close();
		}
		return ok;
	}
    
    public boolean guardaArchivoBD(Connection conn, ArchivoVO archivo,String ruta,String nombre) throws SQLException{
		boolean ok = false;
		int folio=0;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		try{
			
			conn.setAutoCommit(false);
		    String sql="SELECT COALESCE(MAX(FOLIO),0)+1 FROM PORTAL.ARCHIVOS_PROFESOR WHERE ARCHIVO_ID=? AND CODIGO_PERSONAL=?"; 
		    ps = conn.prepareStatement(sql);
			ps.setString(1,archivo.getId());
			ps.setString(2,archivo.getCodigoPersonal());
			ResultSet rs = ps.executeQuery();
			if(rs.next())folio=rs.getInt(1);
			if(archivo.getNombre()==null)archivo.setNombre("Mensaje:");
			
			String nombreTemp=archivo.getNombre();
			nombreTemp=nombreTemp.replaceAll("á","a");
		    nombreTemp=nombreTemp.replaceAll("é","e");
		    nombreTemp=nombreTemp.replaceAll("í","i");
		    nombreTemp=nombreTemp.replaceAll("ó","o");
		    nombreTemp=nombreTemp.replaceAll("ú","u");
		    nombreTemp=nombreTemp.replaceAll("ñ","n");
		    nombreTemp=nombreTemp.replaceAll("Á","A");
		    nombreTemp=nombreTemp.replaceAll("É","E");
		    nombreTemp=nombreTemp.replaceAll("Í","I");
		    nombreTemp=nombreTemp.replaceAll("Ó","O");
		    nombreTemp=nombreTemp.replaceAll("Ú","U");
		    nombreTemp=nombreTemp.replaceAll("Ñ","N");
		    
		    archivo.setNombre(nombreTemp);
			
			ps2 = conn.prepareStatement("INSERT INTO PORTAL.ARCHIVOS_PROFESOR(ARCHIVO_ID," + 
					" FOLIO,CODIGO_PERSONAL,FECHA,NOMBRE,COMENTARIO,AUTORIZACION,TAMANO,ARCHIVO)" +
					" VALUES(?,?,?,localtimestamp,?,?,?,?,?)");
			
			ps2.setString(1,archivo.getId());
			ps2.setInt(2,folio);
			ps2.setString(3,archivo.getCodigoPersonal());
			ps2.setString(4,archivo.getNombre());
			ps2.setString(5,archivo.getComentario());
			ps2.setString(6,archivo.getAutorizacion());
			
			if(archivo.getNombre().equals("Mensaje:")){
				ps2.setLong(7,0);
				ps2.setBytes(8,null);
				ps2.executeUpdate();
			}else{
				File fi = new File(ruta+"/"+nombre);
				ps2.setLong(7,fi.length());
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
				fis.close();
				fi.delete();
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.archivos.ArchivosProfesor|guardaArchivoBd|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
			if (ps2!=null) ps2.close();
			conn.setAutoCommit(true);
		}
		return ok;
	}

    
	public boolean eliminarArchivo(Connection conn, String id, String matricula, int folio) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		 //System.out.println("Datos:"+(id.substring(1,id.length()))+":"+folio+":"+matricula);
		try{
			ResultSet rs = null;
		    String sql="select lo_unlink(archivo) from portal.archivos_profesor "+ 
		    		"where archivo_id = ?"+
		    		"and folio = ?"+
		    		"and codigo_personal = ? ";		    
			ps = conn.prepareStatement(sql);
			
			ps.setString(1,id.substring(1,id.length()));
			ps.setInt(2,folio);
			ps.setString(3,matricula);			
			
			rs = ps.executeQuery();
			if(rs.next()){
			    ok = true;
			    System.out.println("Desligo la imagen...");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivos.ArchivosProfesor|eliminarArchivo(OID)|:"+ex);
		}finally{
			ps.close();
		}
		ps = null;
		if(ok){
			ok = false;
			try{
				 System.out.println("Entró a borrar...");
			    String sql="delete from portal.archivos_profesor "+ 
			    		"where archivo_id=? "+
			    		"and folio=? "+
			    		"and codigo_personal=? ";
				ps = conn.prepareStatement(sql);
				ps.setString(1,id.substring(1,id.length()));
				ps.setInt(2,folio);
				ps.setString(3,matricula);
				if(ps.executeUpdate()==1){
				    ok=true;					
				}
			}catch(Exception ex){
				System.out.println("Error - aca.archivos.ArchivosProfesor|eliminarArchivo(fila)|:"+ex);
			}finally{
				ps.close();
			}
		}else{
			System.out.println("No se borro la imagen y tampoco se borro la fila en profesor");
		}
		return ok;
	}	
	
	public boolean eliminarArchivoProfesor(Connection conn, String id, int folio, String matricula ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;		 
		try{
			ResultSet rs = null;
		    String sql="select lo_unlink(archivo) from portal.archivos_profesor "+ 
		    		"where archivo_id = ?"+
		    		"and folio = ?"+
		    		"and codigo_personal = ? ";		    
			ps = conn.prepareStatement(sql);
			
			ps.setString(1,id);
			ps.setInt(2,folio);
			ps.setString(3,matricula);			
			
			rs = ps.executeQuery();
			if(rs.next()){
			    ok = true;			    
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivos.ArchivosProfesor|eliminarArchivoProfesor(OID)|:"+ex);
		}finally{
			ps.close();
		}
		ps = null;
		if(ok){
			ok = false;
			try{				
			    String sql="delete from portal.archivos_profesor "+ 
			    		"where archivo_id=? "+
			    		"and folio=? "+
			    		"and codigo_personal=? ";
				ps = conn.prepareStatement(sql);
				ps.setString(1, id);
				ps.setInt(2, folio);
				ps.setString(3, matricula);
				if(ps.executeUpdate()==1){
				    ok=true;
				}
			}catch(Exception ex){
				System.out.println("Error - aca.archivos.ArchivosProfesor|eliminarArchivoProfesor(fila)|:"+ex);
			}finally{
				ps.close();
			}
		}else{
			System.out.println("No se borro la imagen y tampoco se borro la fila en profesor");
		}
		return ok;
	}
	
	 /*
     * Busca todos los archivos enviados por un maestro en una materia 
     * */
	public ArrayList<ArchivoVO> listaArchivosMateria(Connection conn, String cursoCargaId) throws SQLException{
		
		ArrayList<ArchivoVO> lisArchivos = new ArrayList<ArchivoVO>();
		PreparedStatement ps = null;
		ResultSet rs = null;		
		ArchivoVO archivo=null;
		try{
			ps = conn.prepareStatement("SELECT ARCHIVO_ID, FOLIO, CODIGO_PERSONAL," +
					" TO_CHAR(FECHA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA, NOMBRE, COMENTARIO, TAMANO, ARCHIVO, AUTORIZACION" +
					" FROM PORTAL.ARCHIVOS_PROFESOR " + 
					" WHERE ARCHIVO_ID= ?");
			ps.setString(1, cursoCargaId);			
			rs = ps.executeQuery();
			while(rs.next()){
				archivo=new ArchivoVO();
				archivo.setId(rs.getString(1));
				archivo.setFolio(rs.getInt(2));
				archivo.setCodigoPersonal(rs.getString(3));
				archivo.setFecha(rs.getString(4));
				archivo.setNombre(rs.getString(5));
				archivo.setComentario(rs.getString(6));
				archivo.setTamano(rs.getLong(7));
				archivo.setAutorizacion(rs.getString(9));
				lisArchivos.add(archivo);
			}
		}catch(Exception e){
			System.out.println("Error - aca.archivos.ArchivosProfesor|listaArchivosMateria|:"+e);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return lisArchivos;
	}
	
    
    /*
     * Busca todos los archivos que se han enviado a un alumno 
     * */
	public ArrayList<ArchivoVO> obtenerArchivosMandadosAlumno(Connection conn, String id, String matricula) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		ArrayList<ArchivoVO> archivos = new ArrayList<ArchivoVO>();
		ArchivoVO archivo=null;
		try{
			ps = conn.prepareStatement("SELECT ARCHIVO_ID, FOLIO, CODIGO_PERSONAL," +
					" TO_CHAR(FECHA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA, NOMBRE, COMENTARIO, TAMANO, ARCHIVO" +
					" FROM portal.archivos_profesor " + 
					"WHERE archivo_id=? and position(? in autorizacion)>0");
			ps.setString(1,id);
			ps.setString(2,matricula);
			rs = ps.executeQuery();
			while(rs.next()){
			    archivo=new ArchivoVO();
			    archivo.setId(rs.getString(1));
			    archivo.setFolio(rs.getInt(2));
			    archivo.setCodigoPersonal(rs.getString(3));
				archivo.setFecha(rs.getString(4));
				archivo.setNombre(rs.getString(5));
				archivo.setComentario(rs.getString(6));
				archivo.setTamano(rs.getLong(7));
				archivos.add(archivo);
			}
		}catch(Exception e){
			System.out.println("Error - aca.archivos.ArchivosProfesor|obtenerArchivosMandadosAlumno|:"+e);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return archivos;
	}

		
	 /*
     * Busca todos los archivos que se han enviado a un alumno 
     * */	
	public ArrayList<ArchivoVO> obtenerArchivosDelAlumno(Connection conn,Connection conPostgres, String id, String matricula) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		ResultSet rs2 = null;
		PreparedStatement ps2 = null;
		ResultSet rs3 = null;
		PreparedStatement ps3 = null;
		ArrayList<ArchivoVO> archivos = new ArrayList<ArchivoVO>();
		ArchivoVO archivo=null;
		try{
		    String sql="SELECT ARCHIVO_ID,FOLIO,CODIGO_PERSONAL,TO_CHAR(FECHA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA,NOMBRE,COMENTARIO,TAMANO, "+ 
		    			"coalesce(SUBSTR(AUTORIZACION,position(? in autorizacion)+8,1),'-') AS AUTORIZACION "+
		    			"FROM portal.archivos_profesor WHERE archivo_id like ? and position(? in autorizacion)>0 order by 1,4 desc"; 
			ps = conPostgres.prepareStatement(sql);
			ps.setString(1,matricula);
			ps.setString(2,id+"%");
			ps.setString(3,matricula);
			rs = ps.executeQuery();
			
			String aid="",estrategia="";
			while(rs.next()){
			    archivo=new ArchivoVO();
			    aid=rs.getString(1);
			    
				sql="SELECT NOMBRE_ESTRATEGIA FROM ENOC.CAT_ESTRATEGIA WHERE ESTRATEGIA_ID= "+ 
		    			"	   (SELECT ESTRATEGIA_ID FROM ENOC.CARGA_GRUPO_EVALUACION "+ 
		    			"		WHERE CURSO_CARGA_ID=SUBSTR(?,0,11) "+
		    			"		AND EVALUACION_ID=TO_NUMBER(SUBSTR(?,13)) "+
		    			"		)";
				ps2 = conn.prepareStatement(sql);
				ps2.setString(1,aid);
				ps2.setString(2,aid);
				rs2 = ps2.executeQuery();
				if(rs2.next()) estrategia=rs2.getString(1);

				sql="SELECT NOMBRE_EVALUACION FROM ENOC.CARGA_GRUPO_EVALUACION "+ 
		    			"	WHERE CURSO_CARGA_ID=SUBSTR(?,0,11) AND EVALUACION_ID=TO_NUMBER(SUBSTR(?,13))";
				ps3 = conn.prepareStatement(sql);
				ps3.setString(1,aid);
				ps3.setString(2,aid);
				rs3 = ps2.executeQuery();
				if(rs3.next()) estrategia+=" - "+rs2.getString(1);

			    archivo.setId(aid);
			    archivo.setFolio(rs.getInt(2));
			    archivo.setCodigoPersonal(rs.getString(3));
				archivo.setFecha(rs.getString(4));
				archivo.setNombre(rs.getString(5));
				archivo.setComentario(rs.getString(6));
				archivo.setTamano(rs.getLong(7));
				archivo.setNombreAlumno(estrategia);
				archivo.setAutorizacion(rs.getString(8));
				archivos.add(archivo);
			}
		}
		catch(Exception e){
			System.out.println("Error - aca.archivos.ArchivosProfesor|obtenerArchivosDelAlumno|:"+e);
		}
		finally{
			try { rs.close(); } catch (Exception ignore) { }			
			if (rs2!=null) rs2.close();
			if (rs3!=null) rs3.close();
			try { ps.close(); } catch (Exception ignore) { }
			if (ps2!=null) ps2.close();
			if (ps3!=null) ps3.close();
		}
		return archivos;
	}

	public ArrayList<ArchivoVO> obtenerArchivosDelAlumnoxEstrategia(Connection conn, Connection conPostgres, String id, String matricula) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		ResultSet rs2 = null;
		PreparedStatement ps2 = null;
		ResultSet rs3 = null;
		PreparedStatement ps3 = null;
		ArrayList<ArchivoVO> archivos = new ArrayList<ArchivoVO>();
		ArchivoVO archivo=null;
		try{
			String aid="",estrategia="";
			
		    String sql="SELECT ARCHIVO_ID,FOLIO,CODIGO_PERSONAL,TO_CHAR(FECHA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA,NOMBRE,COMENTARIO,TAMANO,coalesce(SUBSTR(AUTORIZACION,position(? in autorizacion)+8,1),'-') AS AUTORIZACION "+
		    			"FROM portal.archivos_profesor WHERE archivo_id = ? and position(? in autorizacion)>0"; 
			ps = conPostgres.prepareStatement(sql);
			ps.setString(1,matricula);
			ps.setString(2,id);
			ps.setString(3,matricula);
			rs = ps.executeQuery();
			while(rs.next()){
			    archivo=new ArchivoVO();
			    aid=rs.getString(1);
			    
				sql="SELECT NOMBRE_ESTRATEGIA FROM ENOC.CAT_ESTRATEGIA WHERE ESTRATEGIA_ID= "+ 
		    			"	   (SELECT ESTRATEGIA_ID FROM ENOC.CARGA_GRUPO_EVALUACION "+ 
		    			"		WHERE CURSO_CARGA_ID=SUBSTR(?,0,11) "+
		    			"		AND EVALUACION_ID=TO_NUMBER(SUBSTR(?,13)) "+
		    			"		)";
				ps2 = conn.prepareStatement(sql);
				ps2.setString(1,aid);
				ps2.setString(2,aid);
				rs2 = ps2.executeQuery();
				if(rs2.next()) estrategia=rs2.getString(1);
				
				sql="SELECT NOMBRE_EVALUACION FROM ENOC.CARGA_GRUPO_EVALUACION "+ 
		    			"	WHERE CURSO_CARGA_ID=SUBSTR(?,0,11) AND EVALUACION_ID=TO_NUMBER(SUBSTR(?,13))";
				ps3 = conn.prepareStatement(sql);
				ps3.setString(1,aid);
				ps3.setString(2,aid);
				rs3 = ps2.executeQuery();
				if(rs3.next()) estrategia+=" - "+rs3.getString(1);
				

			    archivo.setId(aid);
			    archivo.setFolio(rs.getInt(2));
			    archivo.setCodigoPersonal(rs.getString(3));
				archivo.setFecha(rs.getString(4));
				archivo.setNombre(rs.getString(5));
				archivo.setComentario(rs.getString(6));
				archivo.setTamano(rs.getLong(7));
				archivo.setNombreAlumno(estrategia);
				archivo.setAutorizacion(rs.getString(8));
				archivos.add(archivo);
			}
		}
		catch(Exception e){
			System.out.println("Error - aca.archivos.ArchivosProfesor|obtenerArchivosDelAlumnoxEstrategia|:"+e);
		}
		finally{
			try { rs.close(); } catch (Exception ignore) { }			
			if (rs2!=null) rs2.close();
			if (rs3!=null) rs3.close();
			
			try { ps.close(); } catch (Exception ignore) { }
			if (ps2!=null) ps2.close();
			if (ps3!=null) ps3.close();
		}
		return archivos;
	}

	public ArrayList<ArchivoVO> obtenerArchivosNuevosdeAlumno(Connection conn, Connection conPostgres, String matricula) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		ResultSet rs2 = null;
		PreparedStatement ps2 = null;
		ResultSet rs3 = null;
		PreparedStatement ps3 = null;
		ArrayList<ArchivoVO> archivos = new ArrayList<ArchivoVO>();
		ArchivoVO archivo=null;
		try{		    
		    String sql="select archivo_id,folio,codigo_personal,TO_CHAR(FECHA, 'DD/MM/YYYY HH24:MI:SS') AS fecha,nombre,comentario,tamano"+
		    	" from portal.archivos_profesor where position(? in autorizacion)>0  and substr(autorizacion,position(? in autorizacion)+8,1)='N'"; 
			ps = conPostgres.prepareStatement(sql);
			ps.setString(1,matricula);
			ps.setString(2,matricula);
			rs = ps.executeQuery();
			
			String id="";
			String nombreMateria="";
			String cProfesor="";
			String nombreProfesor="";
			while(rs.next()){
			    archivo=new ArchivoVO();
			    id=rs.getString(1);
			    cProfesor=rs.getString(3);

		    	sql="SELECT NOMBRE_CURSO FROM ENOC.MAPA_CURSO WHERE CURSO_ID="+ 
	    		"(SELECT CURSO_ID FROM ENOC.CARGA_GRUPO_CURSO WHERE CURSO_CARGA_ID=SUBSTR(?,0,11) AND INSTR(CURSO_ID,ENOC.ALUM_PLAN_ID(?))>0)"; 
				ps2 = conn.prepareStatement(sql);
				ps2.setString(1,id);
				ps2.setString(2,matricula);
				rs2 = ps2.executeQuery();
				if(rs2.next()) nombreMateria=rs2.getString(1);

				sql="SELECT NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS NOMBRE FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL=?"; 
				ps3 = conn.prepareStatement(sql);
				ps3.setString(1,cProfesor);
				rs3 = ps2.executeQuery();
				if(rs3.next()) nombreProfesor=rs3.getString(1);

			    archivo.setId(id);
			    archivo.setFolio(rs.getInt(2));
			    archivo.setCodigoPersonal(cProfesor);
				archivo.setFecha(rs.getString(4));
				archivo.setNombre(rs.getString(5));
				archivo.setComentario(rs.getString(6));
				archivo.setTamano(rs.getLong(7));
				archivo.setNombreAlumno(nombreMateria);
				archivo.setNombreProfesor(nombreProfesor);
				archivos.add(archivo);
			}
		}
		catch(Exception e){
			System.out.println("Error - aca.archivos.ArchivosProfesor|obtenerArchivosNuevosdeAlumno|:"+e);
		}
		finally{
			try { rs.close(); } catch (Exception ignore) { }			
			if (rs2!=null) rs2.close();
			if (rs3!=null) rs3.close();
			
			try { ps.close(); } catch (Exception ignore) { }
			if (ps2!=null) ps2.close();
			if (ps3!=null) ps3.close();
		}
		return archivos;
	}
	
	public void cambiaEstadoArchivo(Connection conn, String id,int folio,String matricula) throws SQLException{
		PreparedStatement ps 	= null;
		PreparedStatement ps2 	= null;
		ResultSet rs			= null;
		try{
		    
		    String sql="SELECT AUTORIZACION FROM PORTAL.ARCHIVOS_PROFESOR WHERE ARCHIVO_ID=? AND FOLIO=? ";
		    String autorizacion="";
		    ps = conn.prepareStatement(sql);
			ps.setString(1,id);
			ps.setInt(2,folio);
			rs = ps.executeQuery();
			if(rs.next())autorizacion=rs.getString(1);
			String estado = "",tmp="";
			int inicio=autorizacion.indexOf(matricula);
			if(inicio>=0){
				inicio+=8;
				if(autorizacion.length()>inicio){
				    estado=autorizacion.substring(inicio,inicio+1);
					if(estado.equals("N")){
					    tmp=autorizacion.substring(0,inicio-1);
					    tmp+=autorizacion.substring(inicio+1,autorizacion.length());
				    	autorizacion=tmp;
					}
				}
			}

			sql="UPDATE PORTAL.ARCHIVOS_PROFESOR SET AUTORIZACION=? WHERE ARCHIVO_ID=? AND FOLIO=?";
		    ps2 = conn.prepareStatement(sql);
			ps2.setString(1,autorizacion);
			ps2.setString(2,id);
			ps2.setInt(3,folio);
			ps2.executeUpdate();			
		}catch(Exception ex){
			System.out.println("Error - aca.archivos.ArchivosProfesor|cambiaEstadoArchivo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
			if (ps2!=null) ps2.close();
			
		}
	}

	
}