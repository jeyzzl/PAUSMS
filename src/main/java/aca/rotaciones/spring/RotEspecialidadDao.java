package aca.rotaciones.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class RotEspecialidadDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( RotEspecialidad especialidad) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.ROT_ESPECIALIDAD(ESPECIALIDAD_ID, ESPECIALIDAD_NOMBRE, CURSO_ID, SEMANAS, PLAN_ID) " +
					" VALUES(TO_NUMBER(?,'999'),?,?,TO_NUMBER(?,'999'),?)";
			Object[] parametros = new Object[] {
					especialidad.getEspecialidadId(), especialidad.getEspecialidadNombre(), 
					especialidad.getCursoId(), especialidad.getSemanas(), especialidad.getPlanId()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.spring.RotEspecialidadDao|insertReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateReg( RotEspecialidad especialidad ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.ROT_ESPECIALIDAD SET ESPECIALIDAD_NOMBRE = ? , " +
					" CURSO_ID =  ?, SEMANAS = ?, PLAN_ID = ? " +
					" WHERE ESPECIALIDAD_ID = ?  ";
			Object[] parametros = new Object[] {
					especialidad.getEspecialidadNombre(),especialidad.getCursoId(), especialidad.getSemanas(), especialidad.getPlanId(), 
					especialidad.getEspecialidadId()
			};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.spring.RotEspecialidadDao|updateReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean deleteReg( String especialidadId) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.ROT_ESPECIALIDAD WHERE ESPECIALIDAD_ID = ? ";
			Object[] parametros = new Object[] {especialidadId};					
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.spring.RotEspecialidadDao|deletetReg|:"+ex);
		}
		
		return ok;
	}
	
	public RotEspecialidad mapeaRegId( String especialidadId) {
		RotEspecialidad especialidad = new RotEspecialidad();
		
		try{
			String comando = "SELECT ESPECIALIDAD_ID, ESPECIALIDAD_NOMBRE, CURSO_ID, SEMANAS, PLAN_ID " +
					" FROM ENOC.ROT_ESPECIALIDAD WHERE ESPECIALIDAD_ID = ? ";
			Object[] parametros = new Object[] {especialidadId};
			especialidad = enocJdbc.queryForObject(comando, new RotEspecialidadMapper(), parametros);
		
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.spring.RotEspecialidadDao|mapeaRegId|:"+ex);
		}
		return especialidad;
	}
	
	public boolean existeReg(String especialidadId) {
		boolean ok 			= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ROT_ESPECIALIDAD WHERE ESPECIALIDAD_ID = ? "; 
			
			Object[] parametros = new Object[] {especialidadId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.spring.RotEspecialidadDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String maximoReg() {
		
		String maximo			= "1";
		
		try{
			String comando = "SELECT (MAX(ESPECIALIDAD_ID)+1) AS MAXIMO FROM ENOC.ROT_ESPECIALIDAD";

			Object[] parametros = new Object[] {};
			maximo = enocJdbc.queryForObject(comando, String.class, parametros);
	
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.spring.RotEspecialidadDao|maximoReg|:"+ex);
		}		
		return maximo;
	}	
	
	public String getNombre( String especialidadId) {
		
		String nombre			= "";
		
		try{
			String comando = "SELECT ESPECIALIDAD_NOMBRE FROM ENOC.ROT_ESPECIALIDAD WHERE ESPECIALIDAD_ID = "+especialidadId+" "; 
			
			Object[] parametros = new Object[] {especialidadId};
			nombre = enocJdbc.queryForObject(comando, String.class, parametros);
		
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.spring.RotEspecialidadDao|getNombre|:"+ex);
		}
		return nombre;
	}
	
	public HashMap<String,String> getNombres() {
		
		HashMap<String,String> map	= new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		String comando	             		= "";
		
		try{
			comando = "SELECT ESPECIALIDAD_ID AS LLAVE, ESPECIALIDAD_NOMBRE AS VALOR FROM ENOC.ROT_ESPECIALIDAD ";
			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			
			for(aca.Mapa rot : lista){
				map.put(rot.getLlave(), (String)rot.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.spring.RotEspecialidadDao|getNombres|:"+ex);
		}
		return map;
	}
	
	public String getSemanas( String especialidadId) {		
		String nombre			= "";		
		try{
			String comando = "SELECT SEMANAS FROM ENOC.ROT_ESPECIALIDAD WHERE ESPECIALIDAD_ID = ?";			
			Object[] parametros = new Object[] {especialidadId};
			nombre = enocJdbc.queryForObject(comando, String.class, parametros);		
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.spring.RotEspecialidadDao|getSemanas|:"+ex);
		}
		return nombre;
	}
	
	public List<RotEspecialidad> getListAll( String orden ) {
		List<RotEspecialidad> lista	= new ArrayList<RotEspecialidad>();
		String comando				= "";
		
		try{
			comando = "SELECT * FROM ENOC.ROT_ESPECIALIDAD "+orden; 
			
			lista = enocJdbc.query(comando, new RotEspecialidadMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.spring.RotEspecialidadDao|getListAll|:"+ex);
		}		
		return lista;
	}
	
	public List<RotEspecialidad> getEspNoAsignadas( String hospitalId, String orden ) {
		List<RotEspecialidad> lista	= new ArrayList<RotEspecialidad>();
		String comando				= "";
		
		try{
			comando = "SELECT * FROM ENOC.ROT_ESPECIALIDAD " +
					" WHERE ESPECIALIDAD_ID NOT IN(SELECT ESPECIALIDAD_ID FROM ENOC.ROT_HOSPITALESPECIALIDAD WHERE HOSPITAL_ID = "+hospitalId+" )"+orden; 
			
			lista = enocJdbc.query(comando, new RotEspecialidadMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.spring.RotEspecialidadDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String, RotEspecialidad> getMapAll( String orden ) {
		List<RotEspecialidad> lista	= new ArrayList<RotEspecialidad>();
		HashMap<String, RotEspecialidad> mapa	= new HashMap<String, RotEspecialidad>();
		String comando				= "";
		
		try{
			comando = "SELECT * FROM ENOC.ROT_ESPECIALIDAD "+orden; 
			
			lista = enocJdbc.query(comando,new RotEspecialidadMapper());
			for(RotEspecialidad rot : lista){				
				mapa.put(rot.getEspecialidadId(), rot);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.spring.RotEspecialidadDao|getListAll|:"+ex);
		}
		return mapa;
	}
}
