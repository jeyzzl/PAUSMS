package aca.exa.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ExaFamiliaDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(ExaFamilia exaFamilia ){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO "+
				"ENOC.EXA_FAMILIA(FAMILIA_ID, MATRICULA, NOMBRE, RELACION, FECHANACIMIENTO, FECHAACTUALIZACION, " +
				"ELIMINADO, CORREO, FECHAANIVERSARIO, IDFAMILIA) "+
				"VALUES( TO_NUMBER(?,'99999999'), ?, ? ,?, TO_DATE(?,'DD/MM/YYYY'),TO_DATE(?,'DD/MM/YYYY HH24:MI:SS'), " +
				"TO_NUMBER(?,'9'), ?, TO_DATE(?,'DD/MM/YYYY'), ? )";
			
			Object[] parametros = new Object[]{
					exaFamilia.getFamiliaId(), exaFamilia.getMatricula(), exaFamilia.getNombre(), exaFamilia.getRelacion(), exaFamilia.getFechaNac(), exaFamilia.getFechaAct(),
					exaFamilia.getEliminado(), exaFamilia.getCorreo(), exaFamilia.getFechaAniv(), exaFamilia.getIdFamilia()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaFamiliaDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg(ExaFamilia exaFamilia ){
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.EXA_FAMILIA"+ 
				" SET ID_FAMILIA = ?," +
				" MATRICULA = ?, " +
				" NOMBRE = ?," +
				" RELACION = ?," +
				" FECHANACIMIENTO = TO_DATE(?,'DD/MM/YYYY')," +
				" FECHAACTUALIZACION = TO_DATE(?,'DD/MM/YYYY HH24:MI:SS')," +
				" ELIMINADO = TO_NUMBER(?,'9')," +
				" CORREO = ?, " +
				" FECHAANIVERSARIO = TO_DATE(?,'DD/MM/YYYY') "+
				" WHERE FAMILIA_ID = TO_NUMBER(?,'99999999')";
			Object[] parametros = new Object[]{
					exaFamilia.getIdFamilia(), exaFamilia.getMatricula(), exaFamilia.getNombre(), exaFamilia.getRelacion(), exaFamilia.getFechaNac(), exaFamilia.getFechaAct(),
					exaFamilia.getEliminado(), exaFamilia.getCorreo(), exaFamilia.getFechaAniv(), exaFamilia.getFamiliaId()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaFamiliaDao|updateReg|:"+ex);		
		}
		
		return ok;
	}	
	
	public boolean deleteReg(String familiaId ){
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.EXA_FAMILIA"+ 
				" SET ELIMINADO = '1' " +
				" WHERE FAMILIA_ID = TO_NUMBER(?,'99999999')";
			Object[] parametros = new Object[] {familiaId};
			if(enocJdbc.update(comando, parametros)==1) {
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaFamiliaDao|deleteReg|:"+ex);		
		}
		
		return ok;
	}
	
	public ExaFamilia mapeaRegIdFam(String idFamilia){
		ExaFamilia exaFamilia = new ExaFamilia();

		try{
			String comando = "SELECT FAMILIA_ID, MATRICULA, NOMBRE, RELACION, " +
					" FECHANACIMIENTO, FECHAACTUALIZACION, ELIMINADO, CORREO, FECHAANIVERSARIO, IDFAMILIA "+
				"FROM ENOC.EXA_FAMILIA WHERE FAMILIA_ID = TO_NUMBER(?,'99999999')";
			Object[] parametros = new Object[] {idFamilia};
			exaFamilia = enocJdbc.queryForObject(comando, new ExaFamiliaMapper(), parametros);	

		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaFamiliaDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return exaFamilia;
	}
	
	public ExaFamilia mapeaRegId(String matricula){
		ExaFamilia exaFamilia = new ExaFamilia();		
		try{
			String comando = "SELECT FAMILIA_ID, MATRICULA, NOMBRE, RELACION, " +
				" FECHANACIMIENTO, FECHAACTUALIZACION, ELIMINADO, CORREO, FECHAANIVERSARIO, IDFAMILIA "+
				"FROM ENOC.EXA_FAMILIA WHERE MATRICULA = ?"; 
			Object[] parametros = new Object[] {matricula};
			exaFamilia = enocJdbc.queryForObject(comando, new ExaFamiliaMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaFamiliaDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return exaFamilia;
	}
	
	public boolean existeReg(String idFamilia){
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT * FROM ENOC.EXA_FAMILIA WHERE FAMILIA_ID = TO_NUMBER(?,'99999999') "; 
			Object[] parametros = new Object[] {idFamilia};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaFamiliaDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean existeAlumno(String matricula){
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EXA_FAMILIA WHERE MATRICULA = ? AND ELIMINADO!=1 "; 
			Object[] parametros = new Object[] {matricula};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			} 
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaFamiliaDao|existeAlumno|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoRegAlumno(String matricula){
		String maximo 			= "1";
		
		try{
			String comando = "SELECT MAX(FAMILIA_ID)+1 AS MAXIMO FROM ENOC.EXA_FAMILIA WHERE ELIMINADO !=1 AND MATRICULA = ?";
			Object[] parametros = new Object[] {matricula};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				maximo = String.valueOf(enocJdbc.queryForObject(comando, Integer.class, parametros));
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaFamiliaDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public String maximoReg(){
		String maximo 			= "1";
	
		try{
			String comando = "SELECT MAX(FAMILIA_ID)+1 AS MAXIMO FROM ENOC.EXA_FAMILIA"; 
			maximo = enocJdbc.queryForObject(comando,String.class);
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaFamiliaDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public List<ExaFamilia> getFamilia(String matricula, String orden){
		
		List<ExaFamilia> list		= new ArrayList<ExaFamilia>();
		
		try{
			String comando = "SELECT FAMILIA_ID, MATRICULA, NOMBRE, RELACION, TO_CHAR(FECHANACIMIENTO, 'DD/MM/YYYY') AS FECHANACIMIENTO, FECHAACTUALIZACION," +
					" ELIMINADO, CORREO, TO_CHAR(FECHAANIVERSARIO, 'DD/MM/YYYY') AS FECHAANIVERSARIO, IDFAMILIA " +
					" FROM ENOC.EXA_FAMILIA WHERE MATRICULA = ? AND ELIMINADO != 1 "+orden;
			Object[] parametros = new Object[] {matricula};	
			list = enocJdbc.query(comando, new ExaFamiliaMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaFamiliaDao|getFamilia|:"+ex);
		}
		
		return list;
	}
}