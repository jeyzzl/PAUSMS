package aca.objeto;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class Hora {
	private String cursoCargaId;
	private String salon;
	private String dia;
	private String horarioId;
	private String periodo;
	private String inicio;
	private String fin;
	
	public Hora(){
		cursoCargaId			= "";
		dia						= "";
		periodo					= "";
		inicio					= "";
		fin						= "";
	}	

	public String getCursoCargaId() {
		return cursoCargaId;
	}

	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}	

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public String getInicio() {
		return inicio;
	}

	public void setInicio(String inicio) {
		this.inicio = inicio;
	}

	public String getFin() {
		return fin;
	}

	public void setFin(String fin) {
		this.fin = fin;
	}	
	
	/**
	 * @return the salon
	 */
	public String getSalon() {
		return salon;
	}

	/**
	 * @param salon the salon to set
	 */
	public void setSalon(String salon) {
		this.salon = salon;
	}

	/**
	 * @return the horarioId
	 */
	public String getHorarioId() {
		return horarioId;
	}

	/**
	 * @param horarioId the horarioId to set
	 */
	public void setHorarioId(String horarioId) {
		this.horarioId = horarioId;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		cursoCargaId	= rs.getString("CURSO_CARGA_ID");
		salon			= rs.getString("SALON_ID");
		dia 			= rs.getString("DIA");
		horarioId		= rs.getString("HORARIO_ID");
		periodo			= rs.getString("PERIODO");
		inicio 			= rs.getString("INICIO");
		fin				= rs.getString("FIN");
	}
	
	public static ArrayList<Hora> listaHorasDelAlumno(Connection conn, String codigoPersonal, String cargaId, String tipoCal, String bloqueId ) throws SQLException{		
		ArrayList<Hora> listaHorarios = new ArrayList<Hora>();
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";		
		try{
			comando =	"SELECT" +
					" CGH.CURSO_CARGA_ID, CGH.SALON_ID, CGH.DIA, CGH.HORARIO_ID, CGH.PERIODO, DIA||CHF.HORA_INICIO||CHF.MINUTO_INICIO AS INICIO, DIA||CHF.HORA_FINAL||CHF.MINUTO_FINAL AS FIN"+
					" FROM ENOC.CARGA_GRUPO_HORA CGH, ENOC.CAT_HORARIOFACULTAD CHF"+
					" WHERE CURSO_CARGA_ID IN "+
					"	(SELECT CURSO_CARGA_ID FROM ENOC.KRDX_CURSO_ACT " +
					"	WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' " +
					"	AND CARGA_DEL_GRUPO(CURSO_CARGA_ID)='"+cargaId+"' AND TIPOCAL_ID IN ("+tipoCal+")" +
					"	AND GRUPO_BLOQUE(CURSO_CARGA_ID) = "+bloqueId+")"+
					" AND CHF.HORARIO_ID = CGH.HORARIO_ID"+
					" AND CHF.PERIODO = CGH.PERIODO";
			//System.out.println("Comando="+comando);
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Hora horario = new Hora();
				horario.mapeaReg(rs);
				listaHorarios.add(horario);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.objeto.Hora|listaHorasDelAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return listaHorarios;
	}
	
	public static ArrayList<Hora> listaHorasDelAlumno(Connection conn, String codigoPersonal, String cargaId, String tipoCal, String bloqueId, String horarioId ) throws SQLException{		
		ArrayList<Hora> listaHorarios = new ArrayList<Hora>();
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando =	"SELECT" +
					" CGH.CURSO_CARGA_ID, CGH.SALON_ID, CGH.DIA, CGH.HORARIO_ID, CGH.PERIODO, DIA||CHF.HORA_INICIO||CHF.MINUTO_INICIO AS INICIO, DIA||CHF.HORA_FINAL||CHF.MINUTO_FINAL AS FIN"+
					" FROM ENOC.CARGA_GRUPO_HORA CGH, ENOC.CAT_HORARIOFACULTAD CHF"+
					" WHERE CGH.HORARIO_ID != "+horarioId+
					" AND CURSO_CARGA_ID IN "+
					"	(SELECT CURSO_CARGA_ID FROM ENOC.KRDX_CURSO_ACT " +
					"	WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' " +
					"	AND CARGA_DEL_GRUPO(CURSO_CARGA_ID)='"+cargaId+"' AND TIPOCAL_ID IN ("+tipoCal+")" +
					"	AND GRUPO_BLOQUE(CURSO_CARGA_ID) = "+bloqueId+")"+
					" AND CHF.HORARIO_ID = CGH.HORARIO_ID"+
					" AND CHF.PERIODO = CGH.PERIODO";
			//System.out.println("Comando="+comando);
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Hora horario = new Hora();
				horario.mapeaReg(rs);
				listaHorarios.add(horario);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.objeto.Hora|listaHorasDelAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return listaHorarios;
	}
	
	public static HashMap<String, Hora> mapaHorasDelAlumno(Connection conn, String codigoPersonal, String cargaId, String tipoCal, String bloqueId, String horarioId) throws SQLException{
		HashMap<String, Hora> mapa = new HashMap<String, Hora>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";		
	
		try{			
			comando = "SELECT" +
					" CGH.CURSO_CARGA_ID, CGH.SALON_ID, CGH.DIA, CGH.HORARIO_ID, CGH.PERIODO, DIA||CHF.HORA_INICIO||CHF.MINUTO_INICIO AS INICIO, DIA||CHF.HORA_FINAL||CHF.MINUTO_FINAL AS FIN"+
					" FROM ENOC.CARGA_GRUPO_HORA CGH, ENOC.CAT_HORARIOFACULTAD CHF"+				
					" WHERE CGH.HORARIO_ID = "+horarioId+
					" AND CGH.CURSO_CARGA_ID IN "+
					"	(SELECT CURSO_CARGA_ID FROM ENOC.KRDX_CURSO_ACT " +
					"	WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' " +
					"	AND CARGA_DEL_GRUPO(CURSO_CARGA_ID)='"+cargaId+"' AND TIPOCAL_ID IN ("+tipoCal+")" +
					"	AND GRUPO_BLOQUE(CURSO_CARGA_ID) = "+bloqueId+")" +
					" AND CHF.HORARIO_ID = CGH.HORARIO_ID"+
					" AND CHF.PERIODO = CGH.PERIODO";
			rs = st.executeQuery(comando);			
			while (rs.next()){
				Hora horario = new Hora();
				horario.mapeaReg(rs);
				mapa.put(rs.getString("DIA")+rs.getString("PERIODO"), horario);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.objeto.Hora|mapaHorasDelAlumno|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public static ArrayList<Hora> getListaAlumnoHorario(Connection conn, String codigoPersonal, String cargaId, String bloqueId, String tipoCal, String horarioId ) throws SQLException{
		ArrayList<Hora> listaHorarios = new ArrayList<Hora>();
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT" +
					" CGH.CURSO_CARGA_ID, CGH.SALON_ID, CGH.DIA, CGH.HORARIO_ID, CGH.PERIODO, DIA||CHF.HORA_INICIO||CHF.MINUTO_INICIO AS INICIO, DIA||CHF.HORA_FINAL||CHF.MINUTO_FINAL AS FIN"+
					" FROM ENOC.CARGA_GRUPO_HORA CGH, ENOC.CAT_HORARIOFACULTAD CHF"+
					" WHERE CGH.HORARIO_ID = "+horarioId+
					" AND CURSO_CARGA_ID IN "+
					"	(SELECT CURSO_CARGA_ID FROM ENOC.KRDX_CURSO_ACT " +
					"	WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' " +
					"	AND CARGA_DEL_GRUPO(CURSO_CARGA_ID)='"+cargaId+"' AND TIPOCAL_ID IN ("+tipoCal+")" +
					"	AND GRUPO_BLOQUE(CURSO_CARGA_ID) = "+bloqueId+")" +					
					" AND CHF.HORARIO_ID = CGH.HORARIO_ID"+
					" AND CHF.PERIODO = CGH.PERIODO";
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Hora horario = new Hora();
				horario.mapeaReg(rs);
				listaHorarios.add(horario);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.objeto.Hora|getListaAlumnoHorario|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return listaHorarios;
	}
	
	public static ArrayList<Hora> getListaHorasDeMateria(Connection conn, String cursoCargaId ) throws SQLException{
		ArrayList<Hora> listaHorarios = new ArrayList<Hora>();
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando =	"SELECT" +
					" CGH.CURSO_CARGA_ID, CGH.SALON_ID, CGH.DIA, CGH.HORARIO_ID, CGH.PERIODO, DIA||CHF.HORA_INICIO||CHF.MINUTO_INICIO AS INICIO, DIA||CHF.HORA_FINAL||CHF.MINUTO_FINAL AS FIN"+
					" FROM ENOC.CARGA_GRUPO_HORA CGH, ENOC.CAT_HORARIOFACULTAD CHF"+
					" WHERE CURSO_CARGA_ID = '"+cursoCargaId+"'"+	
					" AND CHF.HORARIO_ID = CGH.HORARIO_ID"+
					" AND CHF.PERIODO = CGH.PERIODO";
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Hora horario = new Hora();
				horario.mapeaReg(rs);
				listaHorarios.add(horario);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.objeto.Hora|getListaHorasDeMateria|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return listaHorarios;
	}
	
	public static HashMap<String, String> mapaCursosDelAlumnos(Connection conn, String codigoPersonal, String cargaId, String tipo) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";		
	
		try{			
			comando = "SELECT CURSO_CARGA_ID, CURSO_NOMBRE(CURSO_ID) AS CURSO FROM ENOC.KRDX_CURSO_ACT" +
					" WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'" +
					" AND CARGA_DEL_GRUPO(CURSO_CARGA_ID) = '"+cargaId+"'" +
					" AND TIPOCAL_ID IN ("+tipo+")";
			rs = st.executeQuery(comando);			
			while (rs.next()){			
				mapa.put(rs.getString("CURSO_CARGA_ID"), rs.getString("CURSO"));
			}
		}catch(Exception ex){
			System.out.println("Error - aca.objeto.Hora|mapaCursosDelAlumnos|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public static HashMap<String, String> mapaCursosDelMaestro(Connection conn, String codigoPersonal, String cargaId) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";		
	
		try{			
			comando = " SELECT CURSO_CARGA_ID, CURSO_NOMBRE(CURSO_ID) AS CURSO FROM ENOC.CARGA_GRUPO_CURSO" +
					  " WHERE ORIGEN = 'O'" +
					  " AND CURSO_CARGA_ID IN " +
				      "	(SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' AND CARGA_ID = '"+cargaId+"')";
			rs = st.executeQuery(comando);			
			while (rs.next()){			
				mapa.put(rs.getString("CURSO_CARGA_ID"), rs.getString("CURSO"));
			}
		}catch(Exception ex){
			System.out.println("Error - aca.objeto.Hora|mapaCursosDelMaestro|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public static HashMap<String, Hora> mapaHorasDelMaestro(Connection conn, String codigoPersonal, String cargaId, String bloqueId) throws SQLException{
		HashMap<String, Hora> mapa = new HashMap<String, Hora>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";		
	
		try{			
			comando = "SELECT" +
					" CGH.CURSO_CARGA_ID, CGH.SALON_ID, CGH.DIA, CGH.HORARIO_ID, CGH.PERIODO, DIA||CHF.HORA_INICIO||CHF.MINUTO_INICIO AS INICIO, DIA||CHF.HORA_FINAL||CHF.MINUTO_FINAL AS FIN"+
					" FROM ENOC.CARGA_GRUPO_HORA CGH, ENOC.CAT_HORARIOFACULTAD CHF"+				
					" WHERE CGH.CURSO_CARGA_ID IN "+
					"	(SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' AND CARGA_ID = '"+cargaId+"' AND BLOQUE_ID = "+bloqueId+")"+
					" AND CHF.HORARIO_ID = CGH.HORARIO_ID"+
					" AND CHF.PERIODO = CGH.PERIODO";
			rs = st.executeQuery(comando);			
			while (rs.next()){
				Hora horario = new Hora();
				horario.mapeaReg(rs);
				mapa.put(rs.getString("HORARIO_ID")+rs.getString("DIA")+rs.getString("PERIODO"), horario);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.objeto.Hora|mapaHorasDelMaestro|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public static ArrayList<String> listaHorariosDelMaestro(Connection conn, String codigoPersonal, String cargaId, String bloqueId ) throws SQLException{
		ArrayList<String> listaHorarios = new ArrayList<String>();
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando =	"SELECT" +					 
				" DISTINCT(HORARIO_ID) AS HORARIO"+
				" FROM ENOC.CARGA_GRUPO_HORA"+
				" WHERE CURSO_CARGA_ID IN"+ 
				"	(SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' AND CARGA_ID = '"+cargaId+"' AND BLOQUE_ID = "+bloqueId+")";
			rs = st.executeQuery(comando);
			while (rs.next()){				
				listaHorarios.add(rs.getString("HORARIO"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.objeto.Hora|listaHorariosDelMaestro|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return listaHorarios;
	}
	
}
