package aca.rotaciones.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class RotHospitalDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	

	public boolean insertReg(RotHospital rot ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.ROT_HOSPITAL(HOSPITAL_ID, HOSPITAL_NOMBRE, HOSPITAL_CORTO, " + 
					" INSTITUCION_ID, CALLE, COLONIA, MUN_EDO, PAIS, TELEFONO, FAX, MEDICO, PUESTO, SALUDO) " +
					" VALUES(TO_NUMBER(?,'999'),?,?,TO_NUMBER(?,'99'),?,?,?,?,?,?,?,?,?)";
		
			
		Object[] parametros = new Object[] {rot.getHospitalId(),rot.getHospitalNombre(),rot.getHospitalCorto(),
				rot.getInstitucionId(),rot.getCalle(),rot.getColonia(),rot.getMunEdo(),rot.getPais(),rot.getTelefono(),
				rot.getFax(),rot.getMedico(),rot.getPuesto(),rot.getSaludo()
			};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.spring.RotHospitalDao|insertReg|:"+ex);
		}
		return ok;
	}
	
	public boolean updateReg(RotHospital rot) {
		boolean ok = false;

		try{
			String comando = "UPDATE ENOC.ROT_HOSPITAL SET HOSPITAL_NOMBRE = ? , " +
					" HOSPITAL_CORTO =  ?, INSTITUCION_ID = TO_NUMBER(?,'99'), CALLE = ?, COLONIA = ?," +
					" MUN_EDO = ?, PAIS = ?, TELEFONO = ?, FAX = ?, MEDICO = ?, PUESTO = ?, SALUDO = ?  " +
					" WHERE HOSPITAL_ID = ?";			
		
		
		Object[] parametros = new Object[] {rot.getHospitalNombre(),rot.getHospitalCorto(),
				rot.getInstitucionId(),rot.getCalle(),rot.getColonia(),rot.getMunEdo(),
				rot.getPais(),rot.getTelefono(),rot.getFax(),
				rot.getMedico(),rot.getPuesto(),rot.getSaludo(),rot.getHospitalId()
				};
				
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.spring.RotHospitalDao|updateReg|:"+ex);
		}
		return ok;
	}
	
	public boolean deleteReg(String hospitalId ) {
		boolean ok = false;

		try{
			String comando = "DELETE FROM ENOC.ROT_HOSPITAL WHERE HOSPITAL_ID = ?"; 
			
			Object[] parametros = new Object[] {hospitalId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.spring.RotHospitalDao|deletetReg|:"+ex);
		}
		return ok;
	}
	

	public RotHospital mapeaRegId( String hospitalId) {
		RotHospital rot = new RotHospital();
		
		try{
			String comando = "SELECT HOSPITAL_ID, HOSPITAL_NOMBRE, HOSPITAL_CORTO, " +
					" INSTITUCION_ID, CALLE, COLONIA, MUN_EDO, PAIS, TELEFONO, FAX, MEDICO, PUESTO, SALUDO" +
					" FROM ENOC.ROT_HOSPITAL WHERE HOSPITAL_ID = ? "; 
			
			Object[] parametros = new Object[] {hospitalId};
			rot = enocJdbc.queryForObject(comando, new RotHospitalMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.spring.RotHospitalDao|mapeaRegId|:"+ex);
		}
		return rot;
	}
	
	public boolean existeReg(String hospitalId) {
		boolean ok 			= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ROT_HOSPITAL WHERE HOSPITAL_ID = ? "; 
			Object[] parametros = new Object[] {hospitalId};	
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.spring.RotHospitalDao|existeReg|:"+ex);
		}		
		return ok;
	}
	
	public boolean existeInstitucion( String institucionId) {
		boolean ok 			= false;
				
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ROT_HOSPITAL WHERE INSTITUCION_ID = ? "; 
			Object[] parametros = new Object[] {institucionId};	
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.spring.RotHospitalDao|existeInstitucion|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg() {

		String maximo			= "1";
		
		try{
			String comando = "SELECT MAX(HOSPITAL_ID)+1 MAXIMO FROM ENOC.ROT_HOSPITAL"; 
			Object[] parametros = new Object[] {};
			maximo = enocJdbc.queryForObject(comando, String.class, parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.spring.RotHospitalDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public String getNombre( String hospitalId) {

		String nombre			= "";
		
		try{
			String comando = "SELECT HOSPITAL_NOMBRE FROM ENOC.ROT_HOSPITAL WHERE HOSPITAL_ID = "+hospitalId+" "; 
			Object[] parametros = new Object[] {hospitalId};
			nombre = enocJdbc.queryForObject(comando, String.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.spring.RotHospitalDao|getNombre|:"+ex);
		}
		
		return nombre;
	}	
	
	public HashMap<String,String> mapaHospitales() {
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
		HashMap<String,String> map	= new HashMap<String, String>();
		try{
			String comando = "SELECT HOSPITAL_ID AS LLAVE, HOSPITAL_NOMBRE AS VALOR FROM ENOC.ROT_HOSPITAL";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa rot : lista) {
				map.put(rot.getLlave(), (String)rot.getValor());
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.spring.RotHospitalDao|getNombres|:"+ex);
		}		
		return map;
	}	

	public String getNombreCorto( String hospitalId) {

		String nombre			= "";
		
		try{
			String comando = "SELECT HOSPITAL_CORTO FROM ENOC.ROT_HOSPITAL WHERE HOSPITAL_ID = "+hospitalId+" "; 
			Object[] parametros = new Object[] {hospitalId};
			nombre = enocJdbc.queryForObject(comando, String.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.spring.RotHospitalDao|getNombre|:"+ex);
		}
		
		return nombre;
	}	
	
	public List<RotHospital> getListAll( String orden ) {
		List<RotHospital> lista	= new ArrayList<RotHospital>();
		String comando				= "";
		
		try{
			comando = "SELECT * FROM ENOC.ROT_HOSPITAL "+orden; 
			
			lista = enocJdbc.query(comando, new RotHospitalMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.spring.RotHospitalDao|getListAll|:"+ex);
		}		
		return lista;
	}
	
	public List<RotHospital> getListHospitales( String orden ) {
		List<RotHospital> lista	= new ArrayList<RotHospital>();
		String comando				= "";
		
		try{
			comando = "SELECT * FROM ENOC.ROT_HOSPITAL WHERE HOSPITAL_ID IN (SELECT HOSPITAL_ID FROM ENOC.ROT_HOSPITALESPECIALIDAD)"+orden; 
			
			lista = enocJdbc.query(comando, new RotHospitalMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.spring.RotHospitalDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String, String> mapaTotHospitales() {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa>	lista 		 = new ArrayList<aca.Mapa>();		
		try{
			String comando = " SELECT INSTITUCION_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.ROT_HOSPITAL GROUP BY INSTITUCION_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.spring.RotHospitalDao|mapaTotHospitales|:"+ex);
		}
		return mapa;
	}
}
