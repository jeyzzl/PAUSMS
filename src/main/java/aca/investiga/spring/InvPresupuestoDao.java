package aca.investiga.spring;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import aca.investiga.spring.InvPresupuestoDao;

@Component
public class InvPresupuestoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	
	
	public boolean insertReg(InvPresupuesto invPresupuesto) throws SQLException{
		boolean ok = false;
		
		try {
			String comando = "INSERT INTO ENOC.INV_PRESUPUESTO (PROYECTO_ID,"
					+ " PRESUPUESTO_ID, PRESUPUESTO_NOMBRE, TIPO, MONTO)"
					+ " VALUES (?, TO_NUMBER(?,'99'), ?, ?, TO_NUMBER(?,'999999999'))";
			
			Object[] parametros = new Object[] {
				invPresupuesto.getProyectoId(),invPresupuesto.getPresupuestoId(),invPresupuesto.getPresupuestoNombre(),
				invPresupuesto.getTipo(),invPresupuesto.getMonto()	
			};
				
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		} catch (Exception ex) {
			System.out.println("Error - aca.investiga.spring.InvPresupuestoDao|insertReg|:"+ex);
		}

		return ok;
	}
	
	public boolean updateReg(InvPresupuesto invPresupuesto) throws SQLException{
		boolean ok = false;
		
		try {
			String comando = "UPDATE ENOC.INV_PRESUPUESTO"
					+ " SET PRESUPUESTO_NOMBRE = ?, "
					+ " TIPO = ?, "
					+ " MONTO = TO_NUMBER(?,'999999999')"
					+ " WHERE PROYECTO_ID = ? AND PRESUPUESTO_ID = ?";
			
			Object[] parametros = new Object[] {
				invPresupuesto.getPresupuestoNombre(),invPresupuesto.getTipo(),invPresupuesto.getMonto(),invPresupuesto.getProyectoId(),
				invPresupuesto.getPresupuestoId()
			};
				
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		} catch (Exception ex) {
			System.out.println("Error - aca.investiga.spring.InvPresupuestoDao|updateReg|:"+ex);
		} 
		
		return ok;
	}
	
	public boolean deleteRegId(String proyectoId, String presupuestoId) throws SQLException{
		boolean ok = false;
		
		try {
			String comando = "DELETE FROM ENOC.INV_PRESUPUESTO WHERE PROYECTO_ID = ? AND PRESUPUESTO_ID = ?";
			
			Object[] parametros = new Object[] {proyectoId, presupuestoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		} catch (Exception ex) {
			System.out.println("Error - aca.investiga.spring.InvPresupuestoDao|deleteRegId|:"+ex);
		}
		
		return ok;
	}
	
	public boolean deleteReg(String proyectoId) {
		boolean ok = false;
		
		try {
			String comando = "DELETE FROM ENOC.INV_PRESUPUESTO WHERE PROYECTO_ID = ?";

			Object[] parametros = new Object[] {proyectoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		} catch (Exception ex) {
			System.out.println("Error - aca.investiga.spring.InvPresupuestoDao|deleteReg|:"+ex);
		}
		
		return ok;
	}
	
	public InvPresupuesto mapeaRegId(String proyectoId, String presupuestoId) throws SQLException{
		InvPresupuesto objeto = new InvPresupuesto();
		
		try {
			String comando = "SELECT PROYECTO_ID, PRESUPUESTO_ID, "
					+ "PRESUPUESTO_NOMBRE, MONTO, TIPO FROM ENOC.INV_PRESUPUESTO "
					+ "WHERE PROYECTO_ID = ? AND PRESUPUESTO_ID = ?";
			
			Object[] parametros = new Object[] {proyectoId,presupuestoId};
			objeto = enocJdbc.queryForObject(comando,new InvPresupuestoMapper(),parametros);
			
		} catch (Exception ex) {
			System.out.println("Error - aca.investiga.InvPresupuestoDao|mapeaRegId|:"+ex);
		} 
		return objeto;
	}
	
	public boolean existeRegId(String proyectoId, String presupuestoId) throws SQLException{
		boolean ok = false;
		
		try {
			String comando = "SELECT * FROM ENOC.INV_PRESUPUESTO"
					+ " WHERE PROYECTO_ID = ? AND PRESUPUESTO_ID = ?";

			Object[] parametros = new Object[] {proyectoId, presupuestoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
					
		} catch (Exception ex) {
			System.out.println("Error - aca.investiga.spring.InvPresupuestoDao|existeRegId|:"+ex);
		}
		
		return ok;
	}
	
	public boolean existeReg(String proyectoId) {
		boolean ok = false;
		
		try {
			String comando = "SELECT COUNT(*) FROM ENOC.INV_PRESUPUESTO WHERE PROYECTO_ID = ?";
					
			Object[] parametros = new Object[] {proyectoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
					
		} catch (Exception ex) {
			System.out.println("Error - aca.investiga.spring.InvPresupuestoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg() throws SQLException{
		String maximo = "1";
		
		try{
			String comando = "SELECT (MAX(PRESUPUESTO_ID)+1) AS MAXIMO FROM ENOC.INV_PRESUPUESTO";
			
			maximo = enocJdbc.queryForObject(comando,String.class);
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvPresupuestoDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}

	public List<InvPresupuesto> getListPresupuesto(String proyectoId, String orden ) throws SQLException{
		List<InvPresupuesto> lista	= new ArrayList<InvPresupuesto>();	 
		try{
			String comando = " SELECT PROYECTO_ID, PRESUPUESTO_ID, PRESUPUESTO_NOMBRE, MONTO, TIPO"
					+ " FROM ENOC.INV_PRESUPUESTO"
					+ " WHERE PROYECTO_ID = ? "+orden;			
			lista = enocJdbc.query(comando, new InvPresupuestoMapper(), proyectoId);			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvPresupuestoDao|getListPresupuesto|:"+ex);
		}
	
		return lista;
	}

}
