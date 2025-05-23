package aca.investiga.spring;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class InvMetodologiaDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	
	
	public boolean insertReg(InvMetodologia invMetodologia) throws SQLException {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.INV_METODOLOGIA(PROYECTO_ID, HUMANOS, DISENO, MUESTRA, "
					+ "RECOLECCION, CONFIDENCIALIDAD, VINCULACION, RESPONSABLE, ACTIVIDADES, ENTREGABLE, PLAN, ORGANIZACION, PROBLEMA, OBJETIVO, HIPOTESIS, VALIDEZ) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			Object[] parametros = new Object[] {
				invMetodologia.getProyectoId(),invMetodologia.getHumanos(),invMetodologia.getDiseno(),invMetodologia.getMuestra(),invMetodologia.getRecoleccion(),
				invMetodologia.getConfidencialidad(),invMetodologia.getVinculacion(),invMetodologia.getResponsable(),invMetodologia.getActividades(),
				invMetodologia.getEntregable(),invMetodologia.getPlan(),invMetodologia.getOrganizacion(),invMetodologia.getProblema(),invMetodologia.getObjetivo(),
				invMetodologia.getHipotesis(),invMetodologia.getValidez()
			};
				
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
				
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvMetodologiaDao|insertReg|:"+ex);
		}

		return ok;
	}
	
	public boolean updateReg(InvMetodologia invMetodologia) throws SQLException{
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.INV_METODOLOGIA "
					+ " SET HUMANOS = ?,"
					+ " DISENO = ?,"
					+ " MUESTRA = ?,"
					+ " RECOLECCION = ?,"
					+ " CONFIDENCIALIDAD = ?,"
					+ " VINCULACION = ?,"
					+ " RESPONSABLE = ?,"
					+ " ACTIVIDADES = ?,"
					+ " ENTREGABLE = ?,"
					+ " PLAN = ?,"
					+ " ORGANIZACION = ?,"
					+ " PROBLEMA = ?,"
					+ " OBJETIVO = ?,"
					+ " HIPOTESIS = ?,"
					+ " VALDIEZ = ?"
					+ " WHERE PROYECTO_ID = ?";
			
			Object[] parametros = new Object[] {
				invMetodologia.getHumanos(),invMetodologia.getDiseno(),invMetodologia.getMuestra(),invMetodologia.getRecoleccion(),invMetodologia.getConfidencialidad(),
				invMetodologia.getVinculacion(),invMetodologia.getResponsable(),invMetodologia.getActividades(),invMetodologia.getEntregable(),
				invMetodologia.getPlan(),invMetodologia.getOrganizacion(),invMetodologia.getProblema(),invMetodologia.getObjetivo(),invMetodologia.getHipotesis(),
				invMetodologia.getValidez(),invMetodologia.getProyectoId()		
			};
				
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch (Exception ex){
			System.out.println("Error - aca.investiga.spring.InvMetodologiaDao|updateReg|:"+ex);
		}

		return ok;
	}
	
	public boolean deleteReg(String proyectoId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.INV_METODOLOGIA WHERE PROYECTO_ID = ?";
			
			Object[] parametros = new Object[] {proyectoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvMetodologiaDao|deleteReg|:"+ex);
		}

		return ok;
	}
	
	public InvMetodologia mapeaRegId(String proyectoId) throws SQLException{
		InvMetodologia objeto = new InvMetodologia();
		
		try{
			String comando = "SELECT PROYECTO_ID, HUMANOS, DISENO, MUESTRA, RECOLECCION,"
					+ " CONFIDENCIALIDAD, VINCULACION, RESPONSABLE, ACTIVIDADES, ENTREGABLE, PLAN, ORGANIZACION, PROBLEMA, OBJETIVO, HIPOTESIS, VALDIEZ"
					+ " FROM ENOC.INV_METODOLOGIA"
					+ " WHERE PROYECTO_ID = ?";

			Object[] parametros = new Object[] { proyectoId};
			objeto = enocJdbc.queryForObject(comando,new InvMetodologiaMapper(),parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvMetodologiaDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public boolean existeReg(String proyectoId) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.INV_METODOLOGIA WHERE PROYECTO_ID = ?";

			Object[] parametros = new Object[] {proyectoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvMetodologiaDao|existeReg|:"+ex);
		}

		return ok;
	}
	
	public List<InvMetodologia> getListAll(String orden) throws SQLException{
		List<InvMetodologia> lista = new ArrayList<InvMetodologia>();
		
		try{
			String comando = "SELECT PROYECTO_ID, HUMANOS, DISENO, MUESTRA, RECOLECCION,"
					+ " CONFIDENCIALIDAD, VINCULACION, RESPONSABLE, ACTIVIDADES, ENTREGABLE, PLAN, ORGANIZACION, PROBLEMA, OBJETIVO, HIPOTESIS, VALDIEZ"
					+ " FROM ENOC.INV_METODOLOGIA " + orden;
			
			lista = enocJdbc.query(comando, new InvMetodologiaMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvMetodologiaDao|getListAll|:"+ex);
		}

		return lista;
	}
	
	public List<InvMetodologia> getListMetodologiaEmpleado (String codigoPersonal, String orden) throws SQLException{
		List<InvMetodologia> lista = new ArrayList<InvMetodologia>();		
		try{
			String comando = "SELECT PROYECTO_ID, HUMANOS, DISENO, MUESTRA, RECOLECCION,"
					+ " CONFIDENCIALIDAD, VINCULACION, RESPONSABLE, ACTIVIDADES, ENTREGABLE, PLAN, ORGANIZACION, PROBLEMA, OBJETIVO, HIPOTESIS, VALIDEZ"
					+ " FROM ENOC.INV_METODOLOGIA WHERE CODIGO_PERSONAL = ? "+orden;			
			lista = enocJdbc.query(comando, new InvMetodologiaMapper(), codigoPersonal);			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvMetodologiaDao|getListMetodologiaEmpleado|:"+ex);
		}

		return lista;
	}

}
