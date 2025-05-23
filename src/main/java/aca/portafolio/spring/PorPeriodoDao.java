package aca.portafolio.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PorPeriodoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(PorPeriodo porPeriodo) {
		boolean ok = false;

		try{
			String comando = "INSERT INTO DANIEL.POR_PERIODO(PERIODO_ID, PERIODO_NOMBRE, FECHA_INI, FECHA_FIN, ESTADO) VALUES( ?, ?, ?, ?, ?)";
			
			Object[] parametros = new Object[] {
				porPeriodo.getPeriodoId(),porPeriodo.getPeriodoNombre(),porPeriodo.getFechaIni(),porPeriodo.getFechaFin(),porPeriodo.getEstado()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorPeriodoDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg(PorPeriodo porPeriodo) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE DANIEL.POR_PERIODO SET PERIODO_NOMBRE = ?, FECHA_INI = ?, FECHA_FIN = ? WHERE PERIODO_ID = ? ";
			
			Object[] parametros = new Object[] {
				porPeriodo.getPeriodoNombre(),porPeriodo.getFechaIni(),porPeriodo.getFechaFin(),porPeriodo.getPeriodoId()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorPeriodoDao|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean deleteReg(String periodoId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM DANIEL.POR_PERIODO WHERE PERIODO_ID = ?";
			
			Object[] parametros = new Object[] {periodoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}

		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorPeriodoDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public PorPeriodo mapeaRegId(String periodoId) {
		PorPeriodo objeto = new PorPeriodo();
		
		try{
			String comando = "SELECT PERIODO_ID, PERIODO_NOMBRE, FECHA_INI, FECHA_FIN" +
					" FROM DANIEL.POR_PERIODO WHERE PERIODO_ID = ? "; 
			
			Object[] parametros = new Object[] {periodoId};
			objeto = enocJdbc.queryForObject(comando, new PorPeriodoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorPeriodoDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}

		return objeto;
	}
	
	public boolean existeReg(String periodoId) {
		boolean ok = false;
		
		try{
			String comando = "SELECT * FROM DANIEL.POR_PERIODO WHERE PERIODO_ID = ?"; 

			Object[] parametros = new Object[] {periodoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorPeriodoDao|existeReg|:"+ex);
		}
		
		return ok;
	}	
	
	public List<PorPeriodo> getListAll(String orden) {
		List<PorPeriodo> lista = new ArrayList<PorPeriodo>();
	 
		try{
			String comando = "SELECT PERIODO_ID, PERIODO_NOMBRE, FECHA_INI, FECHA_FIN, ESTADO "+
					" FROM DANIEL.POR_PERIODO "+orden; 
			
			lista = enocJdbc.query(comando, new PorPeriodoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorPeriodoDao|getListAll|:"+ex);
		}
	
		return lista;
	}
	
	public List<PorPeriodo> lisActivos(String orden) {
		List<PorPeriodo> lista = new ArrayList<PorPeriodo>();
	 
		try{
			String comando = "SELECT PERIODO_ID, PERIODO_NOMBRE, FECHA_INI, FECHA_FIN, ESTADO "+
					" FROM DANIEL.POR_PERIODO WHERE ESTADO = 'A' "+orden;			
			lista = enocJdbc.query(comando, new PorPeriodoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorPeriodoDao|lisActivos|:"+ex);
		}
	
		return lista;
	}

}
