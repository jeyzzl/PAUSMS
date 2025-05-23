package aca.internado.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;	

@Component
public class IntAlumReporteDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	
		
	public boolean insertReg(IntAlumReporte intAlumRep ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.INT_ALUM_REPORTE(CODIGO_PERSONAL, FOLIO, FECHA, REPORTE_ID, COMENTARIO, USUARIO, CANTIDAD, DORMITORIO)"
					+ " VALUES(?,?,TO_DATE(?, 'DD/MM/YYYY'),?,?,?,?,?)";
			
			Object[] parametros = new Object[] {
				intAlumRep.getCodigoPersonal(),intAlumRep.getFolio(),intAlumRep.getFecha(),intAlumRep.getReporteId(),intAlumRep.getComentario(),
				intAlumRep.getUsuario(),intAlumRep.getCantidad(),intAlumRep.getDormitorio()
			};
			
			if (enocJdbc.update(comando,parametros) == 1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntAlumReporteDao|insertReg|:"+ex);
		}
		return ok;
	}
	
	
	public boolean updateReg(IntAlumReporte intAlumRep) {
		boolean ok = false;
		try{
			String comando = "UPDATE ENOC.INT_ALUM_REPORTE"
					+ " SET FECHA = TO_DATE(?, 'DD/MM/YYYY'),"
					+ " REPORTE_ID = ?,"
					+ " COMENTARIO = ?,"
					+ " USUARIO = ?,"
					+ " CANTIDAD = ?"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND FOLIO = TO_NUMBER(?,'99')";	
			
			Object[] parametros = new Object[] {
				intAlumRep.getFecha(),intAlumRep.getReporteId(),intAlumRep.getComentario(),intAlumRep.getUsuario(),intAlumRep.getCantidad(),
				intAlumRep.getCodigoPersonal(),intAlumRep.getFolio()
			};
			
			if (enocJdbc.update(comando,parametros) == 1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntAlumReporteDao|updateReg|:"+ex);
		}
		return ok;
	}
	
	public boolean deleteReg(String codigoPersonal, String folio ) {
		boolean ok = false;
		try{
			String comando = "DELETE FROM ENOC.INT_ALUM_REPORTE WHERE CODIGO_PERSONAL = ? AND FOLIO = ?";
			
			Object[] parametros = new Object[] {
				codigoPersonal,folio
			};
			
			if (enocJdbc.update(comando,parametros) == 1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntAlumReporteDao|deleteReg|:"+ex);
		}
		return ok;
	}
	
	public IntAlumReporte mapeaRegId(String codigoPersonal, String folio) {
		IntAlumReporte intAlumRep = new IntAlumReporte();
		try{ 
			String comando = "SELECT CODIGO_PERSONAL, FOLIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, REPORTE_ID, COMENTARIO, USUARIO, CANTIDAD, DORMITORIO"
					+ " FROM ENOC.INT_ALUM_REPORTE WHERE CODIGO_PERSONAL = ? AND FOLIO = ?";
			
			Object[] parametros = new Object[] {
				codigoPersonal,folio
			};
			
			intAlumRep = enocJdbc.queryForObject(comando,new IntAlumReporteMapper(),parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntAlumReporteDao|mapeaRegId|:"+ex);
		}
		return intAlumRep;
	}
	
	public boolean existeReg(String codigoPersonal, String folio ) {
		boolean ok = false;
		try{ 
			String comando = "SELECT * FROM ENOC.INT_ALUM_REPORTE WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?, '99')";
			 
			Object[] parametros = new Object[] {
				codigoPersonal,folio
			};
			
			if (enocJdbc.update(comando,parametros) == 1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntAlumReporteDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String maximoReg(String codigoPersonal ) {
		String maximo = "1";
		
		try{ 
			String comando = "SELECT COALESCE(MAX(FOLIO)+1,1) AS MAXIMO FROM ENOC.INT_ALUM_REPORTE WHERE CODIGO_PERSONAL = ?";
			 
			Object[] parametros = new Object[] {
				codigoPersonal
			};
				
			maximo = enocJdbc.queryForObject(comando,String.class,parametros);
				
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntAlumReporteDao|existeReg|:"+ex);
		}
		return maximo;
	}
	
	public List<IntAlumReporte> getListAll(String orden) {
		List<IntAlumReporte> lista 	= new ArrayList<IntAlumReporte>();
		
		try{			
			String comando = "SELECT CODIGO_PERSONAL, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, REPORTE_ID, COMENTARIO, USUARIO, CANTIDAD, DORMITORIO" +
	 				" FROM ENOC.INT_ALUM_REPORTE "+orden;
			
			lista = enocJdbc.query(comando, new IntAlumReporteMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntAlumReporteDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public List<IntAlumReporte> listIntAlumReporte(String codigoPersonal ) {
		List<IntAlumReporte> lista 	= new ArrayList<IntAlumReporte>();
		
		try{			
			String comando = "SELECT CODIGO_PERSONAL, FOLIO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, REPORTE_ID, COMENTARIO, USUARIO, CANTIDAD, DORMITORIO" +
	 				" FROM ENOC.INT_ALUM_REPORTE "+
					" WHERE CODIGO_PERSONAL="+codigoPersonal;
			
			lista = enocJdbc.query(comando, new IntAlumReporteMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntAlumReporteDao|listIntAlumReporte|:"+ex);
		}
		return lista;
	}
	
	public List<IntAlumReporte> listReportesPorDormi(String fechaIni, String fechaFin, String dormitorio, String orden) {
		List<IntAlumReporte> lista 	= new ArrayList<IntAlumReporte>();
		try{			
			String comando = " SELECT CODIGO_PERSONAL, FOLIO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, REPORTE_ID, COMENTARIO, USUARIO, CANTIDAD, DORMITORIO"
					+ " FROM ENOC.INT_ALUM_REPORTE"
					+ " WHERE DORMITORIO = ?"
					+ " AND FECHA BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY') "+ orden;			
			lista = enocJdbc.query(comando, new IntAlumReporteMapper(), dormitorio, fechaIni, fechaFin);			
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntAlumReporteDao|listReportesPorDormi|:"+ex);
		}
		return lista;
	}
		
}