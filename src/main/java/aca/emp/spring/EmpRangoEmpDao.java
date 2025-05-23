// Clase Util para la tabla de Carga
package aca.emp.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class EmpRangoEmpDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( EmpRangoEmp rangoEmp ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.EMP_RANGOEMP (CODIGO_PERSONAL, CARGA_ID, RANGO_ID, FECHA, ESTADO) "+
				"VALUES(?, ?, TO_NUMBER(?,'99'), TO_DATE(?,'DD/MM/YYYY'), ?)";
			Object[] parametros = new Object[] {
					rangoEmp.getCodigoPersonal(), rangoEmp.getCargaId(), rangoEmp.getRangoId(), rangoEmp.getFecha(), rangoEmp.getEstado()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpRangoEmpDao|insertReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateReg( EmpRangoEmp rangoEmp ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.EMP_RANGOEMP SET "
				+ " FECHA = TO_DATE(?,'DD/MM/YYYY'),"
				+ " RANGO_ID = TO_NUMBER(?,'99'),"
				+ " ESTADO = ? "
				+ " WHERE CODIGO_PERSONAL = ? "
				+ " AND CARGA_ID = ?";
			Object[] parametros = new Object[] {
					rangoEmp.getFecha(), rangoEmp.getRangoId(), rangoEmp.getEstado(), rangoEmp.getCodigoPersonal(), rangoEmp.getCargaId()
		 	};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpRangoEmpDao|updateReg|:"+ex);		 
		}		
		return ok;
	}
	
	public boolean deleteReg( String codigoPersonal, String cargaId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.EMP_RANGOEMP WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, cargaId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpRangoEmpDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public EmpRangoEmp mapeaRegId( String codigoPersonal, String cargaId ) {
		
		EmpRangoEmp objeto = new EmpRangoEmp();
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, RANGO_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, ESTADO"
					+ " FROM ENOC.EMP_RANGOEMP"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CARGA_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, cargaId};
			objeto = enocJdbc.queryForObject(comando, new EmpRangoEmpMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpRangoEmpDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public boolean existeReg( String codigoPersonal, String cargaId) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EMP_RANGOEMP WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, cargaId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpRangoEmpDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public List<EmpRangoEmp> lisTodos( String orden ) {
		
		List<EmpRangoEmp> lista	= new ArrayList<EmpRangoEmp>();
		String comando	= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, CARGA_ID, RANGO_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, ESTADO"
					+ " FROM ENOC.EMP_RANGOEMP "+orden;
			lista = enocJdbc.query(comando, new EmpRangoEmpMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpRangoEmpDao|listAll|:"+ex);
		}
		
		return lista;
	}
	
	public List<EmpRangoEmp> lisEmpleado( String codigoEmpleado, String orden ) {
		
		List<EmpRangoEmp> lista	= new ArrayList<EmpRangoEmp>();
		String comando	= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, CARGA_ID, RANGO_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, ESTADO"
					+ " FROM ENOC.EMP_RANGOEMP "
					+ " WHERE CODIGO_PERSONAL = ? "+orden;
			Object[] parametros = new Object[] {codigoEmpleado};
			lista = enocJdbc.query(comando, new EmpRangoEmpMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpRangoEmpDao|lisEmpleado|:"+ex);
		}		
		return lista;
	}
	
	public HashMap<String,String> mapaRangosDelEmpleado() {
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<EmpRangoEmp> lista		= new ArrayList<EmpRangoEmp>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, RANGO_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, ESTADO"
						  + " FROM ENOC.EMP_RANGOEMP";				
			
			lista = enocJdbc.query(comando, new EmpRangoEmpMapper());
			for (EmpRangoEmp rangoEmp : lista ) {
				mapa.put(rangoEmp.getCodigoPersonal()+rangoEmp.getCargaId(), rangoEmp.getRangoId());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpRangoEmpDao|mapaRangosDelEmpleado|:"+ex);
		}
		
		return mapa;
	}

}