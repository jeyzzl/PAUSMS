package aca.investiga.spring;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class InvActividadDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	
	
	public boolean insertReg(InvActividad invActividad) throws SQLException{
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.INV_ACTIVIDAD(PROYECTO_ID, ACTIVIDAD_ID, ACTIVIDAD_NOMBRE, FECHA_INI, FECHA_FIN) "
					+ "VALUES (?, TO_NUMBER(?,'99'), ?, TO_DATE(?,'DD/MM/YYYY'), TO_DATE(?,'DD/MM/YYYY'))";
			
			Object[] parametros = new Object[] {
				invActividad.getProyectoId(),invActividad.getActividadId(),invActividad.getActividadNombre(),
				invActividad.getFechaIni(),invActividad.getFechaFin()
			};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch (Exception ex){
			System.out.println("Error - aca.investiga.spring.InvActividadDao|insertReg|:"+ex);
		}

		return ok;
	}
	
	public boolean updateReg(InvActividad invActividad) throws SQLException {
		boolean ok = false;
		
		try {
			String comando = "UPDATE ENOC.INV_ACTIVIDAD "
					+ "SET ACTIVIDAD_NOMBRE = ?, "
					+ "FECHA_INI = TO_DATE(?,'DD/MM/YYYY'), "
					+ "FECHA_FIN = TO_DATE(?,'DD/MM/YYYY') "
					+ "WHERE PROYECTO_ID = ? AND ACTIVIDAD_ID = ?";		
			
			Object[] parametros = new Object[] {
				invActividad.getActividadNombre(),invActividad.getFechaIni(),invActividad.getFechaFin(),
				invActividad.getProyectoId(),invActividad.getActividadId(),
				
			};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
				
		} catch (Exception ex) {
			System.out.println("Error - aca.investiga.spring.InvActividadDao|updateReg|:"+ex);
		} 
		
		return ok;
	}
	
	public boolean deleteRegId(String proyectoId, String actividadId) throws SQLException {
		boolean ok = false;
		
		try {
			String comando = "DELETE FROM ENOC.INV_ACTIVIDAD WHERE PROYECTO_ID = ?"
					+ " AND ACTIVIDAD_ID = ?";
			
			Object[] parametros = new Object[] {proyectoId, actividadId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		} catch (Exception ex) {
			System.out.println("Error - aca.investiga.spring.InvActividadDao|deleteRegId|:"+ex);
		} 
		
		return ok;
	}
	
	public boolean deleteReg(String proyectoId) {
		boolean ok = false;
		
		try {
			String comando = "DELETE FROM ENOC.INV_ACTIVIDAD WHERE PROYECTO_ID = ?";
			
			Object[] parametros = new Object[] {proyectoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		} catch (Exception ex) {
			System.out.println("Error - aca.investiga.spring.InvActividadDao|deleteReg|:"+ex);
		} 
		
		return ok;
	}
	
	public InvActividad mapeaRegId(String proyectoId, String actividadId) throws SQLException {
		InvActividad objeto = new InvActividad();
		
		try {
			String comando = "SELECT PROYECTO_ID, ACTIVIDAD_ID, ACTIVIDAD_NOMBRE,"
					+ " TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI,  TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_FIN"
					+ " FROM ENOC.INV_ACTIVIDAD"
					+ " WHERE PROYECTO_ID = ? AND ACTIVIDAD_ID = ?";
					
			Object[] parametros = new Object[] { proyectoId, actividadId };
			objeto = enocJdbc.queryForObject(comando,new InvActividadMapper(),parametros);
					
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvActividadDao|mapeaRegId|:"+ex);
		}

		return objeto;
	}
	
	public boolean existeRegId(String proyectoId, String actividadId) throws SQLException {
		boolean ok = false;
		
		try {
			String comando = "SELECT PROYECTO_ID, ACTIVIDAD_ID, ACTIVIDAD_NOMBRE,"
					+ " TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_FIN"
					+ " FROM ENOC.INV_ACTIVIDAD WHERE PROYECTO_ID = ? AND ACTIVIDAD_ID = ?";
			
			Object[] parametros = new Object[] {proyectoId, actividadId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		} catch (Exception ex) {
			System.out.println("Error - aca.investiga.spring.InvActividadDao|existeRegId|:"+ex);
		} 
				
		return ok;
	}
	
	public boolean existeReg(String proyectoId) {
		boolean ok = false;
		
		try {
			String comando = "SELECT COUNT(*) FROM ENOC.INV_ACTIVIDAD WHERE PROYECTO_ID = ?";
			
			Object[] parametros = new Object[] {proyectoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		} catch (Exception ex) {
			System.out.println("Error - aca.investiga.spring.InvActividadDao|existeReg|:"+ex);
		} 
				
		return ok;
	}
	
	public String maximoReg() throws SQLException{
		String maximo 	= "1";
		
		try{
			String comando = "SELECT (MAX(ACTIVIDAD_ID)+1) AS MAXIMO FROM ENOC.INV_ACTIVIDAD";
			
			maximo = enocJdbc.queryForObject(comando,String.class);
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvActividadDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public List<InvActividad> getListActividad(String proyectoId, String orden ) throws SQLException{
		List<InvActividad> lista	= new ArrayList<InvActividad>();
	 
		try{
			String comando = " SELECT PROYECTO_ID, ACTIVIDAD_ID, ACTIVIDAD_NOMBRE,"
					+ " TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN"
					+ " FROM ENOC.INV_ACTIVIDAD"
					+ " WHERE PROYECTO_ID = ? "+orden; 			
			lista = enocJdbc.query(comando, new InvActividadMapper(), proyectoId);			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvActividadDao|getListActividad|:"+ex);
		}
	
		return lista;
	}
	
}
