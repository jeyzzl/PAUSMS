package aca.archivos.spring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ArchivosProfesorDao {
	
	@Autowired
	@Qualifier("jdbcArchivo")
	private JdbcTemplate archivoJdbc;
    
    public boolean guardaArchivo(ArchivosProfesor archivo, String ruta, String nombre){
		boolean ok = false;
		int folio = 0;
		
		try{
		    String comando = "SELECT COUNT(*) FROM PORTAL.ARCHIVOS_PROFESOR WHERE ARCHIVO_ID = ? AND CODIGO_PERSONAL = ?";

		    if(archivo.getNombre()==null) {
				archivo.setNombre("Mensaje:");
			}
			
			Object[] parametros = new Object[] {
				archivo.getArchivoId(),archivo.getCodigoPersonal()
			};
			
			if (archivoJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				comando = "SELECT COALESCE(MAX(FOLIO),0)+1 AS FOLIO FROM PORTAL.ARCHIVOS_PROFESOR WHERE ARCHIVO_ID = ? AND CODIGO_PERSONAL = ?"; 
				folio = archivoJdbc.queryForObject(comando, Integer.class, parametros);
			}
			
			String nombreTemp = archivo.getNombre();
			
			nombreTemp = nombreTemp.replaceAll("á","a");
		    nombreTemp = nombreTemp.replaceAll("é","e");
		    nombreTemp = nombreTemp.replaceAll("í","i");
		    nombreTemp = nombreTemp.replaceAll("ó","o");
		    nombreTemp = nombreTemp.replaceAll("ú","u");
		    nombreTemp = nombreTemp.replaceAll("ñ","n");
		    nombreTemp = nombreTemp.replaceAll("Á","A");
		    nombreTemp = nombreTemp.replaceAll("É","E");
		    nombreTemp = nombreTemp.replaceAll("Í","I");
		    nombreTemp = nombreTemp.replaceAll("Ó","O");
		    nombreTemp = nombreTemp.replaceAll("Ú","U");
		    nombreTemp = nombreTemp.replaceAll("Ñ","N");	
			
		    archivo.setNombre(nombreTemp);
			
			comando = ("INSERT INTO PORTAL.ARCHIVOS_PROFESOR(ARCHIVO_ID,FOLIO,CODIGO_PERSONAL,FECHA,NOMBRE,COMENTARIO,AUTORIZACION,ARCHIVO_NUEVO) " +
					"VALUES(?,?,?,now(),?,?,?,?)");
			
			parametros = new Object[] {
				archivo.getArchivoId(),folio,archivo.getCodigoPersonal(),archivo.getNombre(),archivo.getComentario(),archivo.getAutorizacion(),
				archivo.getArchivoNuevo()
			};
			
			if (archivoJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivos.spring.ArchivosProfesorDao|guardaArchivo|:"+ex);
		}

		return ok;
	}
    
    public boolean updateComentario(String archivoId, int folio, String profesor, String comentario){
		boolean ok = false;	
		try{
			String comando = "UPDATE PORTAL.ARCHIVOS_PROFESOR SET COMENTARIO = ?"
				+ " WHERE ARCHIVO_ID = ?"
				+ " AND FOLIO = ?"
				+ " AND CODIGO_PERSONAL = ?";
				Object[] parametros = new Object[] {comentario, archivoId, folio, profesor};		
			if (archivoJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.archivos.spring.ArchivosProfesorDao|updateComentario|:"+ex);
		}

		return ok;
	}   
    
	public boolean eliminarArchivo(String archivoId, int folio, String profesor) {
		boolean ok = false;

		try{
		    String comando = "DELETE FROM PORTAL.ARCHIVOS_PROFESOR " 
		    		+ " WHERE ARCHIVO_ID = ? "
		    		+ " AND FOLIO = ? "
		    		+ " AND CODIGO_PERSONAL = ?";
		    
		    Object[] parametros = new Object[] {archivoId, folio, profesor};	
		    
		    if (archivoJdbc.update(comando,parametros) == 1){
				ok = true;
			}
		    
		}catch(Exception ex){
			System.out.println("Error - aca.archivos.spring.ArchivosProfesorDao|eliminarArchivo|:"+ex);
		}
			
		return ok;
	}	
	
	public List<ArchivosProfesor> listaArchivosMateria(String cursoCargaId) {
		
		List<ArchivosProfesor> lisArchivos = new ArrayList<ArchivosProfesor>();
		try{
			String comando = "SELECT ARCHIVO_ID, FOLIO, CODIGO_PERSONAL, TO_CHAR(FECHA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA, NOMBRE, COMENTARIO, AUTORIZACION, TAMANO, ARCHIVO, ARCHIVO_NUEVO"
					+ " FROM PORTAL.ARCHIVOS_PROFESOR"
					+ " WHERE ARCHIVO_ID = ?"
					+ " ORDER BY COMENTARIO, FECHA";			
			Object[] parametros = new Object[] {cursoCargaId};
			lisArchivos = archivoJdbc.query(comando, new ArchivosProfesorMapper(), parametros);			
		}catch(Exception e){
			System.out.println("Error - aca.archivos.spring.ArchivosProfesorDao|listaArchivosMateria|:"+e);
		}
		return lisArchivos;
	}
	
	public boolean existeReg(String id, int folio, String profesor){
		boolean ok = false;
		try{
			String comando = "SELECT COUNT(*) FROM PORTAL.ARCHIVOS_PROFESOR "
					+ " WHERE ARCHIVO_ID = ? "
					+ " AND FOLIO = ? "
					+ " AND CODIGO_PERSONAL = ?";			
			Object[] parametros = new Object[] {id, folio, profesor};	
			if (archivoJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {					
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.archivos.spring.mapeaRegId|existeReg|:"+ex);
		}
		
		return ok;
	}

	public ArchivosProfesor mapeaRegId(String id, int folio, String profesor){
		
		ArchivosProfesor archivo = new ArchivosProfesor();		
		
		try{
			String comando = "SELECT COUNT(*) FROM PORTAL.ARCHIVOS_PROFESOR "
					+ " WHERE ARCHIVO_ID = ? "
					+ " AND FOLIO = ? "
					+ " AND CODIGO_PERSONAL = ?";			
			Object[] parametros = new Object[] {id, folio, profesor};	
			if (archivoJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = " SELECT ARCHIVO_ID, FOLIO, CODIGO_PERSONAL, TO_CHAR(FECHA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA, NOMBRE, "
						+ " COMENTARIO, AUTORIZACION, TAMANO, ARCHIVO, ARCHIVO_NUEVO"
						+ " FROM PORTAL.ARCHIVOS_PROFESOR "
						+ " WHERE ARCHIVO_ID = ?"
						+ " AND FOLIO = ?"
						+ " AND CODIGO_PERSONAL = ?";
				archivo = archivoJdbc.queryForObject(comando, new ArchivosProfesorMapper(), parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.archivos.spring.mapeaRegId|mapeaRegId|:"+ex);
		}
		
		return archivo;
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
			System.out.println("Error - aca.archivos.spring.ArchivosProfesorDao|obtenerArchivosDelAlumno|:"+e);
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
			System.out.println("Error - aca.archivos.spring.ArchivosProfesorDao|obtenerArchivosDelAlumnoxEstrategia|:"+e);
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
			System.out.println("Error - aca.archivos.spring.ArchivosProfesorDao|obtenerArchivosNuevosdeAlumno|:"+e);
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
			System.out.println("Error - aca.archivos.spring.ArchivosProfesorDao|cambiaEstadoArchivo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
			if (ps2!=null) ps2.close();
			
		}
	}	
}