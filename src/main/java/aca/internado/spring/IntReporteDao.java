package aca.internado.spring;

import java.util.ArrayList;	
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class IntReporteDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	
	
	public boolean insertReg(IntReporte intRep ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.INT_REPORTE(REPORTE_ID, REPORTE_NOMBRE)"
					+ " VALUES(TO_NUMBER(?,'99'), ?)";
			
			Object[] parametros = new Object[] {
				intRep.getReporteId(),intRep.getReporteNombre()
			};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntReporteDao|insertReg|:"+ex);
		}
		return ok;
	}
	
	public boolean updateReg(IntReporte intRep ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.INT_REPORTE SET REPORTE_NOMBRE = ? WHERE REPORTE_ID = TO_NUMBER(?,'99')";
		
			Object[] parametros = new Object[] {
				intRep.getReporteNombre(),intRep.getReporteId()
			};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntReporteDao|updateReg|:"+ex);
		}
		return ok;
	}
	
	public boolean deleteReg(String reporteId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.INT_REPORTE WHERE REPORTE_ID = TO_NUMBER(?,'99')";

			Object[] parametros = new Object[] {
				reporteId
			};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntReporteDao|deleteReg|:"+ex);
		}
		return ok;
	}
	
	public IntReporte mapeaRegId(String reporteId) {
		IntReporte intRep = new IntReporte();
		
		try{ 
			String comando = "SELECT * FROM ENOC.INT_REPORTE WHERE REPORTE_ID = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {
				reporteId
			};
			
			intRep = enocJdbc.queryForObject(comando,new IntReporteMapper(),parametros);
			 
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntReporteDao|mapeaRegId|:"+ex);
		}
		return intRep;
	}
	
	public boolean existeReg(String reporteId, String CodigoPersonal, String CargaId, String BloqueId) {
		boolean ok = false;
		try{ 
			String comando = "SELECT * FROM ENOC.INT_REPORTE WHERE REPORTE_ID = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {
				reporteId
			};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntReporteDao|existeReg|:"+ex);
		}
		return ok;
	}
		
	public List<IntReporte> lisAll(String orden) {
		List<IntReporte> lista 	= new ArrayList<IntReporte>();
		try{			
			String comando = "SELECT REPORTE_ID, REPORTE_NOMBRE FROM ENOC.INT_REPORTE "+orden;
			
			lista = enocJdbc.query(comando, new IntReporteMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntReporteDao|lisAll|:"+ex);
		}
		return lista;
	}	
	
	public HashMap<String, IntReporte> mapAll(String orden) {
		HashMap<String, IntReporte> mapReporte = new HashMap<String, IntReporte>();
		List<IntReporte> lista = new ArrayList<IntReporte>();
		
		try{
			String comando = " SELECT REPORTE_ID, REPORTE_NOMBRE FROM ENOC.INT_REPORTE"; 
			
			lista = enocJdbc.query(comando, new IntReporteMapper());
			
			for(IntReporte objeto : lista) {
				mapReporte.put(objeto.getReporteId(), objeto);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntReporteDao|mapAll|:"+ex);
		}
		return mapReporte;
	}
}