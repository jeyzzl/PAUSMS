package aca.mentores.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MentAlumnoIngresosDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(MentAlumnoIngresos ingresos) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.MENT_ALUMNO_INGRESOS" + 
					" (CODIGO_PERSONAL, CARGA_ID, PROPIOS, SEMESTRE, COLPORTAJE, BECA)" +
					" VALUES( ?, ?, TO_NUMBER(?,'99999.99'), TO_NUMBER(?,'99999.99'), TO_NUMBER(?,'99999.99'), TO_NUMBER(?,'99999.99'))";
			Object[] parametros = new Object[] {ingresos.getCodigoPersonal(),ingresos.getCargaId(),ingresos.getPropios(),ingresos.getSemestre(),
					ingresos.getColportaje(), ingresos.getBeca()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.MentAlumnoIngresos|insertReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateReg(MentAlumnoIngresos ingresos) {
		boolean ok = true;
		
		try{
			String comando = "UPDATE ENOC.MENT_ALUMNO_INGRESOS" + 
					" SET PROPIOS = TO_NUMBER(?,'99999.99')," +
					" SEMESTRE = TO_NUMBER(?,'99999.99')," +
					" COLPORTAJE = TO_NUMBER(?,'99999.99')," +
					" BECA = TO_NUMBER(?,'99999.99')" +
					" WHERE CODIGO_PERSONAL = ?" +
					" AND CARGA_ID = ?";
			Object[] parametros = new Object[] {ingresos.getPropios(),ingresos.getSemestre(),ingresos.getColportaje(),ingresos.getBeca(),
					ingresos.getCodigoPersonal(), ingresos.getCargaId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.MentAlumnoIngresos|updateReg|:"+ex);
		}
		
		return ok;
	}
		
	public boolean deleteReg(String codigoPersonal, String cargaId){
		boolean ok = true;
		
		try{
			String comando = "DELETE FROM ENOC.MENT_ALUMNO_INGRESOS"+ 
				" WHERE CODIGO_PERSONAL = ?" +
				" AND CARGA_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, cargaId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.MentAlumnoIngresos|deleteReg|:"+ex);
		}
		
		return ok;
	}
	
	public MentAlumnoIngresos mapeaRegId(String codigoPersonal, String cargaId){
		MentAlumnoIngresos  ingresos = new MentAlumnoIngresos();
	
		try{ 
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID," +
					" PROPIOS, SEMESTRE, COLPORTAJE, BECA"+
					" FROM ENOC.MENT_ALUMNO_INGRESOS" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND CARGA_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, cargaId};
			ingresos = enocJdbc.queryForObject(comando, new MentAlumnoIngresosMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentAlumnoIngresos|mapeaRegId|:"+ex);
		}
		
		return ingresos;
	}
	
	public boolean existeReg(String codigoPersonal, String cargaId){
		boolean 		ok 		= false;
		
		try{
			String comando = "SELECT COUNT (*) FROM ENOC.MENT_ALUMNO_INGRESOS" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND CARGA_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, cargaId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentAlumnoIngresos|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	
	public List<MentAlumnoIngresos> getListAll(String orden ){
		
		List<MentAlumnoIngresos> lista		= new ArrayList<MentAlumnoIngresos>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, PROPIOS," +
					" SEMESTRE, COLPORTAJE, BECA" +
					" FROM ENOC.MENT_ALUMNO_INGRESOS "+ orden; 
			lista = enocJdbc.query(comando, new MentAlumnoIngresosMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentAlumnoIngresosUtil|getListAll|:"+ex);
		}
		
		return lista;
	}
	
}