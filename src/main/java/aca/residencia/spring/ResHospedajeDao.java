package aca.residencia.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ResHospedajeDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(ResHospedaje objeto){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.RES_HOSPEDAJE(ID, NOMBRE, APELLIDOS, "
					+ " NOMINA, DIRECCION,TELEFONO,CUPO_HOMBRES,CUPO_MUJERES,CUARTOS,ESTADO) "
					+ " VALUES(TO_NUMBER(?,'99999'),?,?,?,?,?,TO_NUMBER(?,'99'),TO_NUMBER(?,'99'),TO_NUMBER(?,'99'),?)";
			Object[] parametros = new Object[] {
				objeto.getId(),objeto.getNombre(),objeto.getApellidos(),objeto.getNomina(),objeto.getDireccion(),objeto.getTelefono(),objeto.getCupoHombres(),objeto.getCupoMujeres(),
				objeto.getCuartos(),objeto.getEstado()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResHospedajeDao|insertReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateReg(ResHospedaje objeto){
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.RES_HOSPEDAJE SET NOMBRE = ?,"
					+ " APELLIDOS =  ?, "
					+ " NOMINA = ?, "
					+ " DIRECCION = ?, "
					+ " TELEFONO = ?, " 
					+ " CUPO_HOMBRES = TO_NUMBER(?,'99'), "
					+ " CUPO_MUJERES = TO_NUMBER(?,'99'), "
					+ " CUARTOS = TO_NUMBER(?,'99'), "
					+ " ESTADO = ? "
					+ " WHERE ID = TO_NUMBER(?,'99999')";	
			Object[] parametros = new Object[] {
				objeto.getNombre(),objeto.getApellidos(),objeto.getNomina(),objeto.getDireccion(),objeto.getTelefono(),objeto.getCupoHombres(),objeto.getCupoMujeres(),
				objeto.getCuartos(),objeto.getEstado(),objeto.getId()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResHospedajeDao|updateReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean deleteReg(String id){
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.RES_HOSPEDAJE WHERE ID = TO_NUMBER(?,'99999')"; 
			Object[] parametros = new Object[] {id};
			if (enocJdbc.update(comando, parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResHospedajeDao|deletetReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean existeReg(String id ){
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT (*) FROM ENOC.RES_HOSPEDAJE WHERE ID = TO_NUMBER(?,'99999')"; 
			Object[] parametros = new Object[] {id};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResHospedajeDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public ResHospedaje mapeaRegId(String id) {
		ResHospedaje objeto = new ResHospedaje();
		
		try{
			Object[] parametros = new Object[] {id};
			String comando = "SELECT ID, NOMBRE, APELLIDOS,"
					+ " NOMINA, DIRECCION,TELEFONO,CUPO_HOMBRES,CUPO_MUJERES,CUARTOS,ESTADO"
					+ " FROM ENOC.RES_HOSPEDAJE"
					+ " WHERE ID = TO_NUMBER(?,'99999')";
			objeto = enocJdbc.queryForObject(comando, new ResHospedajeMapper(), parametros);
				
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResHospedajeDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public String maximoReg() {
 		String maximo = "1";
 		
 		try{
 			String comando = "SELECT COALESCE (MAX(ID)+1,1) AS MAXIMO FROM ENOC.RES_HOSPEDAJE";
 			
 			if (enocJdbc.queryForObject(comando,Integer.class)>=1){
 				maximo = enocJdbc.queryForObject(comando,String.class);
			}
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.residencia.spring.ResHospedajeDao|maximoReg|:"+ex);
 		}
 		
 		return maximo;
 	}
	
	public List<ResHospedaje> getListAll(String orden){
		
	List<ResHospedaje> lista = new ArrayList<ResHospedaje>();
	
	try{
		String comando = "SELECT ID, NOMBRE, APELLIDOS, NOMINA, DIRECCION, TELEFONO, CUPO_HOMBRES, CUPO_MUJERES, CUARTOS, ESTADO FROM ENOC.RES_HOSPEDAJE "+ orden; 
		lista = enocJdbc.query(comando, new ResHospedajeMapper());
		
	}catch(Exception ex){
		System.out.println("Error - aca.residencia.spring.ResHospedajeDao|getListAll|:"+ex);
	}
	
	return lista;
	}

}
