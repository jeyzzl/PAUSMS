package aca.cultural.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AnuariosEntregadosDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public AnuariosEntregados mapeaRegId(String ejercicioId, String matricula) {
		AnuariosEntregados anuariosEntregados = new AnuariosEntregados();
		try{
		String comando = "SELECT "+
			" EJERICIO_ID, MATRICULA, NOMBRE, TO_CHAR(FECHA, 'DD/MM/YYYY/HH24:MI') AS FECHA, USUARIO, CARRERA, SEMESTRE"+
			" FROM ENOC.ANUARIOS_ENTREGADOS"+ 
			" WHERE EJERCICIO_ID = ? AND MATRICULA = ? ";
		Object[] parametros = new Object[] {ejercicioId, matricula};
		anuariosEntregados = enocJdbc.queryForObject(comando, new AnuariosEntregadosMapper(), parametros);
	
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.spring.AnuariosEntregadosDao|mapeaRegId|:"+ex);
		}
		return anuariosEntregados;
	}

	public boolean insertRegByte( AnuariosEntregados anuariosEntregados ) {
		boolean ok 				= false;
		
		try{
			String comando = "INSERT INTO ENOC.ANUARIOS_ENTREGADOS"+ 
				"(EJERCICIO_ID, MATRICULA, NOMBRE, FECHA, USUARIO, CARRERA, SEMESTRE) "+
				"VALUES( ?,?,?,TO_DATE(?, 'DD/MM/YYYY'),?,?,?)";
			
			Object[] parametros = new Object[] {
					anuariosEntregados.getEjercicioId(), anuariosEntregados.getMatricula(), anuariosEntregados.getNombre(), anuariosEntregados.getFecha(),
					anuariosEntregados.getUsuario(), anuariosEntregados.getCarrera(), anuariosEntregados.getSemestre()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.spring.AnuariosEntregadosDao|insertRegByte|:"+ex);			
		}
		return ok;
	}
	
	public boolean updateReg( AnuariosEntregados anuariosEntregados ) {
		boolean ok 				= false;
		
		try{
			String comando = "UPDATE ENOC.ANUARIOS_ENTREGADOS "+ 
				"SET FECHA = TO_DATE(?, 'DD/MM/YYYY HH24:MI'), "+
				"NOMBRE = ?, "+
				"USUARIO = ?, "+
				"CARRERA = ?, "+
				"SEMESTRE = ? "+
				"WHERE EJERCICIO_ID = ? AND MATRICULA = ?";
			
			Object[] parametros = new Object[] {
					anuariosEntregados.getFecha(), anuariosEntregados.getNombre(), anuariosEntregados.getUsuario(), anuariosEntregados.getCarrera(),
					anuariosEntregados.getSemestre(), anuariosEntregados.getEjercicioId(), anuariosEntregados.getMatricula()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.spring.AnuariosEntregadosDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	public boolean deleteReg( String ejercicioId, String matricula ) {
		boolean ok 				= false;
		try{
			String comando = "DELETE FROM ENOC.ANUARIOS_ENTREGADOS "+ 
				"WHERE EJERCICIO_ID = ? AND MATRICULA = ? ";
			
			Object[] parametros = new Object[] {ejercicioId, matricula};					
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.spring.AnuariosEntregadosDao|deleteReg|:"+ex);			
		}
		return ok;
	}

	public boolean existeReg( String ejercicioId, String matricula){
		boolean 		ok 		= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ANUARIOS_ENTREGADOS WHERE EJERCICIO_ID = ? AND MATRICULA = ?";			
			Object[] parametros = new Object[] {ejercicioId, matricula};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.spring.AnuariosEntregadosDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public List<AnuariosEntregados> getListAll( String orden){
		
		List<AnuariosEntregados> list		= new ArrayList<AnuariosEntregados>();
		String comando		= "";
		
		try{
			comando = "SELECT EJERCICIO_ID, MATRICULA, NOMBRE, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, USUARIO, CARRERA, SEMESTRE "+
					  " FROM ENOC.ANUARIOS_ENTREGADOS "+orden;
			
			list = enocJdbc.query(comando, new AnuariosEntregadosMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.spring.AnuariosEntregadosDao|getListAll|:"+ex);
		}
		return list;
	}
	
	public HashMap <String,AnuariosEntregados> getMapEvento( String orden ){
		
		HashMap<String,AnuariosEntregados> map = new HashMap<String,AnuariosEntregados>();
		List<AnuariosEntregados> list		= new ArrayList<AnuariosEntregados>();		
		try{
			String comando = " SELECT EJERCICIO_ID, MATRICULA, NOMBRE, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, USUARIO, CARRERA, SEMESTRE"+
					  " FROM ENOC.ANUARIOS_ENTREGADOS"+orden;
			
			list = enocJdbc.query(comando, new AnuariosEntregadosMapper());
			for(AnuariosEntregados anuario  : list){				
				map.put(anuario.getEjercicioId(), anuario);					
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.spring.AnuariosEntregadosDao|getMapEvento|:"+ex);
		}
		return map;
	}
}