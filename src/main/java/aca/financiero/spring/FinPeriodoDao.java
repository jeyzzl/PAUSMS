package aca.financiero.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class FinPeriodoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(FinPeriodo periodo){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.FIN_PERIODO" + 
					" (PERIODO_ID, FECHA_INI, FECHA_FIN, CARGAS, MODALIDADES, MENSAJE, ESTADO, CANTIDAD)" +
					" VALUES(TO_NUMBER(?, '99'), TO_DATE(?, 'DD/MM/YYYY'), TO_DATE(?, 'DD/MM/YYYY'), ?, ?, ?, ?,TO_NUMBER(?, '99999.99'))";
			
			Object[] parametros = new Object[] {
				periodo.getPeriodoId(),periodo.getFechaIni(),periodo.getFechaFin(),periodo.getCargas(),periodo.getModalidades(),periodo.getMensaje(),periodo.getEstado(),periodo.getCantidad()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	

		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinPeriodoDao|insertReg|:"+ex);
		}

		return ok;
	}
	
	public boolean updateReg(FinPeriodo periodo ){
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.FIN_PERIODO" + 
					" SET FECHA_INI = TO_DATE(?, 'DD/MM/YYYY')," +
					" FECHA_FIN = TO_DATE(?, 'DD/MM/YYYY')," +
					" CARGAS = ?," +
					" MODALIDADES = ? ," +
					" MENSAJE = ? ," +
					" ESTADO = ?, " +
					" CANTIDAD = TO_NUMBER(?, '99999.99') " +
					" WHERE PERIODO_ID = TO_NUMBER(?, '99')";
			
			Object[] parametros = new Object[] {
				periodo.getFechaIni(),periodo.getFechaFin(),periodo.getCargas(),periodo.getModalidades(),periodo.getMensaje(),periodo.getEstado(),periodo.getCantidad(),periodo.getPeriodoId()
			};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinPeriodoDao|updateReg|:"+ex);
		}

		return ok;
	}
	
	public boolean updateCargas(String periodoId, String cargas){
		
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.FIN_PERIODO SET CARGAS = ? WHERE PERIODO_ID = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {cargas, periodoId };			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinPeriodoDao|updateCargas|:"+ex);
		}

		return ok;
	}
	
	public boolean updateModalidades(String periodoId, String modalidades ){
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.FIN_PERIODO SET MODALIDADES = ? WHERE PERIODO_ID = TO_NUMBER(?, '99')";
			
			Object[] parametros = new Object[] {
				modalidades,periodoId
			};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinPeriodoDao|updateModalidades|:"+ex);
		}

		return ok;
	}
	
	public boolean deleteReg(String periodoId ){
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.FIN_PERIODO WHERE PERIODO_ID = TO_NUMBER(?, '99')";
			
			Object[] parametros = new Object[] {
				periodoId
			};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinPeriodoDao|deleteReg|:"+ex);
		}

		return ok;
	}
	
	public FinPeriodo mapeaRegId(String periodoId){
		FinPeriodo periodo = new FinPeriodo(); 
		
		try{
			String comando = "SELECT PERIODO_ID, TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI,"
					+ " TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN,"
					+ " MODALIDADES, CARGAS, ESTADO, MENSAJE, CANTIDAD"
					+ " FROM ENOC.FIN_PERIODO"
					+ " WHERE PERIODO_ID = ?";			
			Object[] parametros = new Object[] {periodoId};
			periodo = enocJdbc.queryForObject(comando, new FinPeriodoMapper(),parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinPeriodoDao|mapeaRegId|:"+ex);
		}
		
		return periodo;
	}
	
	public boolean existeReg(String periodoId){
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.FIN_PERIODO" + 
					" WHERE PERIODO_ID = ?";
			
			Object[] parametros = new Object[] {periodoId};	
			
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1) {
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinPeriodoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String getPeriodoActivo(String fecha){
		
		String periodoId	= "0";
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.FIN_PERIODO WHERE TO_DATE(?,'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN";
			Object[] parametros = new Object[] {fecha};			
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1) {
				comando = "SELECT PERIODO_ID FROM ENOC.FIN_PERIODO WHERE TO_DATE(?,'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN";
				periodoId = enocJdbc.queryForObject(comando, String.class,parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinPeriodoDao|getPeriodoActivo|:"+ex);
		}
		
		return periodoId;
	}
	
	public String maximoReg(){
		String maximo = "1";
		
		try{
			String comando = "SELECT COALESCE(MAX(PERIODO_ID)+1,1) FROM ENOC.FIN_PERIODO";
			maximo = enocJdbc.queryForObject(comando, String.class);
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinPeriodoDao|maximoReg|:"+ex);
		}
		
		return maximo;		
	}
	
	public String getMensaje(){
		String mensaje = "X";
		
		try{
			String comando = "SELECT MENSAJE || '@@' || MODALIDADES || '@@' || ESTADO FROM ENOC.FIN_PERIODO" 
					+ " WHERE TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'), 'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN";
			
			mensaje = enocJdbc.queryForObject(comando, String.class);
		
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinPeriodoDao|getMensajes|:"+ex);
		}
		
		return mensaje;
	}
	
	public boolean getTieneCargas(String codigoPersonal){
		boolean ok 			= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.INSCRITOS WHERE CODIGO_PERSONAL = ? "
					+ " AND INSTR((SELECT CARGAS FROM ENOC.FIN_PERIODO WHERE TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY')"
					+ " BETWEEN FECHA_INI AND FECHA_FIN AND ESTADO IN ('A','B')),CARGA_ID) > 0 ";
			
			Object[] parametros = new Object[] {codigoPersonal};	
			
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1) {
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinPeriodoDao|getTieneCargas|:"+ex);
		}
		
		return ok;
	}

	public List<FinPeriodo> getListAll(String orden ){
		
		List<FinPeriodo> lisAlumno	= new ArrayList<FinPeriodo>();		
		try{
			String comando = "SELECT PERIODO_ID, TO_CHAR(FECHA_INI, 'YYYY/MM/DD') AS FECHA_INI, TO_CHAR(FECHA_FIN, 'YYYY/MM/DD') AS FECHA_FIN,"
					+ " MODALIDADES, CARGAS, ESTADO, MENSAJE, CANTIDAD"
					+ " FROM ENOC.FIN_PERIODO "+orden;			
			lisAlumno = enocJdbc.query(comando, new FinPeriodoMapper());		
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinPeriodoDao|getListAll|:"+ex);
		}
		
		return lisAlumno;
	}
	
	public List<FinPeriodo> getMensajes(){
		List<FinPeriodo> lista = new ArrayList<FinPeriodo>();
		
		try{
			String comando = " SELECT * FROM ENOC.FIN_PERIODO" + 
					" WHERE TO_DATE(now(), 'DD/MM/YYYY') BETWEEN TO_DATE(FECHA_INI, 'DD/MM/YYYY') AND TO_DATE(FECHA_FIN, 'DD/MM/YYYY')";	
			
			lista = enocJdbc.query(comando, new FinPeriodoMapper());		
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinPeriodoDao|getMensajes|:"+ex);
		}
		
		return lista;
	}

}
