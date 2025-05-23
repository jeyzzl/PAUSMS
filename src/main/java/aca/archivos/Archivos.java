/*
 * Created on 13/10/2005
 *
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

import aca.alumno.AlumUtil;
/**
 * @author pedro
 */
public class Archivos {
	
	public boolean guardaArchivo(Connection conn, ArchivoVO archivo,String ruta,String nombre) throws SQLException{
		boolean ok = false;
		int folio=0;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		try{
		    String sql="SELECT COALESCE(MAX(FOLIO),0)+1 FROM PORTAL.ARCHIVOS_ALUMNO WHERE ARCHIVO_ID=? AND CODIGO_PERSONAL=?"; 
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
		    String comentario = archivo.getComentario();
		    comentario=comentario.replaceAll("\"","-");
		    comentario=comentario.replaceAll("#", "No ");
		    archivo.setComentario(comentario);

			ps2 = conn.prepareStatement("INSERT INTO PORTAL.ARCHIVOS_ALUMNO(ARCHIVO_ID, " + 
					"FOLIO,CODIGO_PERSONAL,FECHA,NOMBRE,COMENTARIO,TAMANO,STATUS) " +
					"VALUES(?,?,?,now(),?,?,?,?)");
			
			ps2.setString(1,archivo.getId());
			ps2.setInt(2,folio);
			ps2.setString(3,archivo.getCodigoPersonal());
			ps2.setString(4,archivo.getNombre());
			ps2.setString(5,archivo.getComentario());
			
			if(archivo.getNombre().equals("Mensaje:")){
			    ps2.setLong(6,0);
				ps2.setString(7,"N");
				ps2.executeUpdate();
			}else{
			    File fi = new File(ruta+"/"+nombre);
			    ps2.setLong(6,fi.length());
			    ps2.setString(7,"N");
				ps2.executeUpdate();
			
				File fd = new File(ruta+"/"+archivo.getId()+"M"+archivo.getCodigoPersonal()+"F"+folio+"N "+nombreTemp);
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
			System.out.println("Error - aca.archivos.Archivos|guardaArchivo|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
			if (ps2!=null) ps2.close();			
		}
		return ok;
	}
	
	
	public boolean guardaArchivoenBD(Connection conn, ArchivoVO archivo,String ruta,String nombre) throws SQLException{
		boolean ok = false;
		int folio=0;
		PreparedStatement ps=null;
		PreparedStatement ps2=null;
		try{
			conn.setAutoCommit(false);			
			String sql="SELECT COALESCE(MAX(FOLIO),0)+1 FROM PORTAL.ARCHIVOS_ALUMNO WHERE ARCHIVO_ID=? AND CODIGO_PERSONAL=?"; 
		    ps2 = conn.prepareStatement(sql);
			ps2.setString(1,archivo.getId());
			ps2.setString(2,archivo.getCodigoPersonal());
			ResultSet rs = ps2.executeQuery();
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
		    String comentario = archivo.getComentario();
		    comentario=comentario.replaceAll("\"","-");
		    comentario=comentario.replaceAll("#", "No ");
		    archivo.setComentario(comentario);
	
			
		    ps = conn.prepareStatement("INSERT INTO PORTAL.ARCHIVOS_ALUMNO(ARCHIVO_ID, " + 
				"FOLIO,CODIGO_PERSONAL,FECHA,NOMBRE,COMENTARIO,TAMANO,STATUS,ARCHIVO) " +
				"VALUES(?,?,?,localtimestamp,?,?,?,?,?)");
	    
		    ps.setString(1,archivo.getId());
			ps.setInt(2,folio);
			ps.setString(3,archivo.getCodigoPersonal());
			ps.setString(4,archivo.getNombre());
			ps.setString(5,archivo.getComentario());
			
			
			if(archivo.getNombre().equals("Mensaje:")){
			    ps.setLong(6,0);
				ps.setString(7,"N");
				ps.setBytes(8,null);
				ps.executeUpdate();
				conn.commit();
			}else{
			    File fi = new File(ruta+"/"+nombre);
			    ps.setLong(6,fi.length());
			    ps.setString(7,"N");
				FileInputStream fis = new FileInputStream(fi);
				
				//Objeto que administra los largeObject 
				org.postgresql.largeobject.LargeObjectManager lom = ((org.postgresql.PGConnection)conn).getLargeObjectAPI();
				// Crear el oid (atributo numerico que sirve de referencia)
				long oid = lom.createLO(org.postgresql.largeobject.LargeObjectManager.READ | org.postgresql.largeobject.LargeObjectManager.WRITE);				
				// Crear objeto largeObject
				org.postgresql.largeobject.LargeObject obj = lom.open(oid, org.postgresql.largeobject.LargeObjectManager.WRITE);				
				// buffer para llenar el largeobject
				byte buf[] = new byte[(int)fi.length()];
				int le; 
				while ((le=fis.read(buf)) !=-1){
					obj.write(buf,0,le);
				}
				ps.setLong(8,oid);
				//ps.setBinaryStream(8,fis,fis.available());
			   	ps.executeUpdate();
			    conn.commit();
				fis.close();
				fi.delete();
			}
			
		/*}else{
			System.out.println("1");
		    File fi = new File(ruta+"/"+nombre);
			System.out.println("2");
		    ps.setLong(6,fi.length());
			System.out.println("3");
		    ps.setString(7,"N");
			System.out.println("4");
			FileInputStream fis = new FileInputStream(fi);	
			System.out.println("5");
			ps.setBinaryStream(8,fis,(int)fi.length());
			System.out.println("6");
		   	ps.executeUpdate();
			System.out.println("7");
		    conn.commit();
			System.out.println("8");
			fis.close();
			fi.delete();
			
			
			// PAra sacar un archivo... byte[] archivo = rs.getBytes(campo);
		}*/			
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivos.Archivos|guardaArchivoenBD|:"+ex);
		}finally{
			if(ps!=null)	ps.close();
			if(ps2!=null)	ps2.close();
			conn.setAutoCommit(true);
		}
		return ok;
	}
	
	public boolean eliminarArchivo(Connection conn, String id, String matricula, int folio) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ResultSet rs = null;
		    String sql="SELECT LO_UNLINK(ARCHIVO) AS RESULTADO FROM PORTAL.ARCHIVOS_ALUMNO "+ 
		    		"WHERE ARCHIVO_ID=? "+
		    		"AND FOLIO=? "+
		    		"AND CODIGO_PERSONAL=? ";
			ps = conn.prepareStatement(sql);
			ps.setString(1,id);
			ps.setInt(2,folio);
			ps.setString(3,matricula);
			
			rs = ps.executeQuery();
			if(rs.next()){
			    ok=true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivos.Archivo|eliminarArchivo(OID)|:"+ex);
		}finally{
			ps.close();
		}
		ps = null;
		if(ok){
			ok = false;
			try{
			    String sql="DELETE FROM PORTAL.ARCHIVOS_ALUMNO "+ 
			    		"WHERE ARCHIVO_ID=? "+
			    		"AND FOLIO=? "+
			    		"AND CODIGO_PERSONAL=? ";
				ps = conn.prepareStatement(sql);
				ps.setString(1,id);
				ps.setInt(2,folio);
				ps.setString(3,matricula);
				if(ps.executeUpdate()==1){
				    ok=true;					
				}
			}catch(Exception ex){
				System.out.println("Error - aca.archivos.Archivo|eliminarArchivo(fila)|:"+ex);
			}finally{
				ps.close();
			}
		}else{
			System.out.println("No se borro la imagen y tampoco se borro la fila en alumno");
		}
		return ok;
	}
	
	
	public ArrayList<ArchivoVO> obtenerDatosArchivos(Connection conn, ArchivoVO archivo) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		ArrayList<ArchivoVO> archivos = new ArrayList<ArchivoVO>();
		try{
			ps = conn.prepareStatement("SELECT * FROM PORTAL.ARCHIVOS_ALUMNO " + 
					"WHERE CODIGO_PERSONAL = ? ");
			ps.setString(1,archivo.getCodigoPersonal());
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
				archivo.setAutorizacion(rs.getString(8));
				archivos.add(archivo);
			}
		}
		catch(Exception e){
			System.out.println("Error - aca.archivos.Archivos|obtenerDatosArchivos|:"+e);
		}
		finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return archivos;
	}

	public ArrayList<ArchivoVO> obtenerArchivosxExtrategia(Connection conn, ArchivoVO archivo) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		ArrayList<ArchivoVO> archivos = new ArrayList<ArchivoVO>();
		try{
			ps = conn.prepareStatement("SELECT ARCHIVO_ID, FOLIO, CODIGO_PERSONAL," +
					" TO_CHAR(FECHA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA, NOMBRE, COMENTARIO, TAMANO, STATUS, ARCHIVO" +
					" FROM PORTAL.ARCHIVOS_ALUMNO " + 
					"WHERE CODIGO_PERSONAL = ? AND ARCHIVO_ID=?");
			ps.setString(1,archivo.getCodigoPersonal());
			ps.setString(2,archivo.getId());
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
				archivo.setAutorizacion(rs.getString(8));
				archivos.add(archivo);
			}
		}
		catch(Exception e){
			System.out.println("Error  - aca.archivos.Archivos|obtenerArchivosxEstrategia|:"+e);
		}
		finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return archivos;
	}

	public ArrayList<ArchivoVO> obtenerArchivosxAlumnoExtrategia(Connection conn, Connection conPostgres, String id) throws SQLException{
		ResultSet rs 			= null;		
		PreparedStatement ps 	= null;		
		ArrayList<ArchivoVO> archivos 		= new ArrayList<ArchivoVO>();
		ArchivoVO archivo 		= null;
		String matricula="",nombre="";
		
		try{
			ps = conPostgres.prepareStatement("SELECT " +
					"ARCHIVO_ID,FOLIO,CODIGO_PERSONAL,TO_CHAR(FECHA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA,NOMBRE,COMENTARIO,TAMANO,STATUS " +
					"FROM PORTAL.ARCHIVOS_ALUMNO WHERE ARCHIVO_ID=? ORDER BY 3,4"); 
			ps.setString(1,id);
			rs = ps.executeQuery();
			while(rs.next()){
				matricula=rs.getString(3);
			    archivo=new ArchivoVO();
			    archivo.setId(rs.getString(1));
			    archivo.setFolio(rs.getInt(2));
			    archivo.setCodigoPersonal(matricula);
				archivo.setFecha(rs.getString(4));
				archivo.setNombre(rs.getString(5));
				archivo.setComentario(rs.getString(6));
				archivo.setTamano(rs.getLong(7));
				archivo.setAutorizacion(rs.getString(8));				
							
				nombre= AlumUtil.getNombreAlumno(conn,matricula,"NOMBRE");
				archivo.setNombreAlumno(nombre);
				
				archivos.add(archivo);
			}
		}
		catch(Exception e){
			System.out.println("Error - aca.archivos.Archivo|obtenerArchivosxAlumnoExtrategia|:"+e);
		}
		finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }			
		}
		return archivos;
	}
	
	public ArrayList<ArchivoVO> obtenerArchivosxExtrategiasEnAlumno(Connection conn, Connection conPostgres, String matricula, String id) throws SQLException{
		ResultSet rs 			= null;		
		ResultSet rs2 			= null;
		ResultSet rs3 			= null;
		PreparedStatement ps 	= null;
		PreparedStatement ps2 	= null;
		PreparedStatement ps3 	= null;
		ArrayList<ArchivoVO> archivos 		= new ArrayList<ArchivoVO>();
		ArchivoVO archivo 		= null;
		String aid="",estrategia="";
		
		try{
			
			ps3 = conn.prepareStatement("SELECT NOMBRE_EVALUACION FROM ENOC.CARGA_GRUPO_EVALUACION " + 
					"WHERE CURSO_CARGA_ID=SUBSTR(?,0,11) AND EVALUACION_ID=TO_NUMBER(SUBSTR(?,13))");
			
			ps2 = conn.prepareStatement("SELECT NOMBRE_ESTRATEGIA " +
					"FROM ENOC.CAT_ESTRATEGIA " + 
					"WHERE ESTRATEGIA_ID= " +
						"(SELECT ESTRATEGIA_ID FROM ENOC.CARGA_GRUPO_EVALUACION " + 
						"WHERE CURSO_CARGA_ID=SUBSTR(?,0,11) " +
						"AND EVALUACION_ID=TO_NUMBER(SUBSTR(?,13)))");
			
			ps = conPostgres.prepareStatement("SELECT ARCHIVO_ID, FOLIO, CODIGO_PERSONAL, TO_CHAR(FECHA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA, NOMBRE, COMENTARIO, TAMANO, STATUS "+
					" FROM PORTAL.ARCHIVOS_ALUMNO WHERE CODIGO_PERSONAL=? AND ARCHIVO_ID LIKE ?"); 
			ps.setString(1,matricula);
			ps.setString(2,id+"%");
			rs = ps.executeQuery();
			while(rs.next()){
			    archivo=new ArchivoVO();
				aid=rs.getString(1);				
				
				ps2.setString(1,aid);
				ps2.setString(2,aid);
				rs2 = ps2.executeQuery();
				if(rs2.next()) estrategia=rs2.getString(1);
				
				ps3.setString(1,aid);
				ps3.setString(2,aid);
				rs3 = ps3.executeQuery();
				if(rs3.next()) estrategia+=" - "+rs3.getString(1);

			    archivo.setId(rs.getString(1));
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
			System.out.println("Error - aca.archivos.Archivo|obtenerArchivosxExtrategiasEnAlumno|:"+e);
		}
		finally{			
			try { ps.close(); } catch (Exception ignore) { }			
			if (ps2!=null) ps2.close();
			if (ps3!=null) ps3.close();
			try { rs.close(); } catch (Exception ignore) { }
			if (rs2!=null) rs2.close();
			if (rs3!=null) rs3.close();
		}
		return archivos;
	}


	public ArrayList<ArchivoVO> obtenerArchivosxMateria(Connection conEnoc,Connection conPostgres, String id,String matricula) throws SQLException{
		ResultSet rs 			= null;		
		ResultSet rs2 			= null;
		ResultSet rs3 			= null;
		PreparedStatement ps 	= null;
		PreparedStatement ps2 	= null;
		PreparedStatement ps3 	= null;
		
		ArrayList<ArchivoVO> archivos 		= new ArrayList<ArchivoVO>();
		ArchivoVO archivo		= null;
		
		String aid="",estrategia= "";
		
		try{
			ps3 = conEnoc.prepareStatement("SELECT NOMBRE_EVALUACION " +
					"FROM ENOC.CARGA_GRUPO_EVALUACION " + 
					"WHERE CURSO_CARGA_ID=SUBSTR(?,0,11) " +
					"AND EVALUACION_ID=TO_NUMBER(SUBSTR(?,13))");
			
			ps2 = conEnoc.prepareStatement("SELECT NOMBRE_ESTRATEGIA FROM ENOC.CAT_ESTRATEGIA " + 
					"WHERE ESTRATEGIA_ID= " +
						"(SELECT ESTRATEGIA_ID FROM ENOC.CARGA_GRUPO_EVALUACION " + 
						"WHERE CURSO_CARGA_ID=SUBSTR(?,0,11) " +
						"AND EVALUACION_ID=TO_NUMBER(SUBSTR(?,13)))");
									
			ps = conPostgres.prepareStatement("SELECT ARCHIVO_ID,FOLIO,CODIGO_PERSONAL,TO_CHAR(FECHA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA ,NOMBRE,COMENTARIO,TAMANO,STATUS "+
			    	" FROM PORTAL.ARCHIVOS_ALUMNO WHERE ARCHIVO_ID LIKE ? AND CODIGO_PERSONAL=?"); 
			ps.setString(1,id+"%");
			ps.setString(2,matricula);
			rs = ps.executeQuery();
			while(rs.next()){
				archivo=new ArchivoVO();
				aid=rs.getString(1);				
				
				ps2.setString(1,aid);
				ps2.setString(2,aid);
				rs2 = ps2.executeQuery();
				if(rs2.next()) estrategia=rs2.getString(1);				
				
				ps3.setString(1,aid);
				ps3.setString(2,aid);
				rs3 = ps3.executeQuery();
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
			System.out.println("Error - aca.archivos.Archivo|obtenerArchivosxMateria|:"+e);
		}
		finally{			
			try { ps.close(); } catch (Exception ignore) { }			
			if (ps2!=null) ps2.close();
			if (ps3!=null) ps3.close();
			try { rs.close(); } catch (Exception ignore) { }
			if (rs2!=null) rs2.close();
			if (rs3!=null) rs3.close();
		}
		return archivos;
	}

	public ArrayList<ArchivoVO> obtenerArchivosxAlumno(Connection conn, String matricula,String id) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		ArrayList<ArchivoVO> archivos = new ArrayList<ArchivoVO>();
		ArchivoVO archivo=null;
		try{
			ps = conn.prepareStatement("SELECT * FROM PORTAL.ARCHIVOS_ALUMNO " + 
					"WHERE CODIGO_PERSONAL=? AND ARCHIVO_ID LIKE ?");
			ps.setString(1,matricula);
			ps.setString(2,id+"%");
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
				archivo.setAutorizacion(rs.getString(8));
				archivos.add(archivo);
			}
		}
		catch(Exception e){
			System.out.println("Error - aca.archivos.Archivo|obtenerArchivosxAlumno|:"+e);
		}
		finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return archivos;
	}
	
	public void cambiaEstadoArchivoAlumno(Connection conn, String id,int folio,String matricula,String estado) throws SQLException{
		PreparedStatement ps = null;
		try{
		    
			
		    ps = conn.prepareStatement("UPDATE PORTAL.ARCHIVOS_ALUMNO SET STATUS=? " + 
					"WHERE ARCHIVO_ID=? AND FOLIO=? AND CODIGO_PERSONAL=?");
			
			ps.setString(1,estado);
			ps.setString(2,id);
			ps.setInt(3,folio);
			ps.setString(4,matricula);
			ps.executeUpdate();			
		}catch(Exception ex){
			System.out.println("Error - aca.archivos.Archivo|cambiaEstadoArchivoAlumno|:"+ex);
		}finally{
			ps.close();
		}
	}
	
	public ArrayList<ArchivoVO> obtenerArchivosNuevosdeProfesor(Connection conn, Connection conPostgres, String cProfesor, String cargaId) throws SQLException{
		
		ResultSet rs			= null;
		ResultSet rs2 			= null;
		ResultSet rs3 			= null;
		PreparedStatement ps	= null;		
		PreparedStatement ps2 	= null;
		PreparedStatement ps3 	= null;
		ArchivoVO archivo		= null;
		ArrayList<ArchivoVO> archivos 		= new ArrayList<ArchivoVO>();
		
		String id				="";
		String cursoCargaId		="";
		String nombreMateria	="";
		String matricula		="";
		String nombreAlumno		="";
		
		try{	
			ps3 = conn.prepareStatement("SELECT NOMBRE_CURSO FROM ENOC.MAPA_CURSO " + 
				"WHERE CURSO_ID= "+
    				"(SELECT CURSO_ID FROM ENOC.CARGA_GRUPO_CURSO " + 
    				"WHERE CURSO_CARGA_ID=? " +
    				"AND INSTR(CURSO_ID,ALUM_PLAN_ID(?))>0)");
			
			ps2 = conn.prepareStatement("SELECT CURSO_CARGA_ID  FROM ENOC.CARGA_ACADEMICA " + 
					"WHERE CARGA_ID = ?  AND CODIGO_PERSONAL = ? AND ORIGEN = 'O' AND CURSO_CARGA_ID=SUBSTR(?,0,11)");			
			
			ps = conPostgres.prepareStatement("SELECT ARCHIVO_ID, FOLIO, CODIGO_PERSONAL, TO_CHAR(FECHA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA, NOMBRE, COMENTARIO, TAMANO " +
					"FROM PORTAL.ARCHIVOS_ALUMNO "+ 
	    			"WHERE STATUS='N' " +
	    			"ORDER BY ARCHIVO_ID, CODIGO_PERSONAL");
			rs = ps.executeQuery();
			
			while(rs.next()){
				cursoCargaId="";
			    archivo=new ArchivoVO();
			    id=rs.getString(1);		
				
				ps2.setString(1,cargaId);
				ps2.setString(2,cProfesor);
				ps2.setString(3,id);
				rs2 = ps2.executeQuery();
				if(rs2.next()) cursoCargaId=rs2.getString(1);

			    if(!cursoCargaId.equals("")){
			    	matricula=rs.getString(3);	    	
			    	
					ps3.setString(1,cursoCargaId);
					ps3.setString(2,matricula);
					rs3 = ps3.executeQuery();
					if(rs3.next()) nombreMateria=rs3.getString(1);
					
					nombreAlumno = AlumUtil.getNombreAlumno(conn,matricula,"NOMBRE");				    
					
				    archivo.setId(id);
				    archivo.setFolio(rs.getInt(2));
				    archivo.setCodigoPersonal(matricula);
					archivo.setFecha(rs.getString(4));
					archivo.setNombre(rs.getString(5));
					archivo.setComentario(rs.getString(6));
					archivo.setTamano(rs.getLong(7));
					archivo.setNombreAlumno(nombreAlumno);
					archivo.setNombreProfesor(nombreMateria);
					archivos.add(archivo);
			    }
			}
		}
		catch(Exception e){
			System.out.println("Error - aca.archivos.Archivo|obtenerArchivosNuevosdeProfesor|:"+e);
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
}