// Clase Util para la tabla de Cat_Carrera
package aca.catalogo.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CatSalonDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CatSalon salon ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CAT_SALON(EDIFICIO_ID, SALON_ID, NOMBRE_SALON, NUM_ALUMNOS, ESTADO)"
					+ " VALUES( ?, ?, ?, TO_NUMBER(?,'999'), ?)";
			Object[] parametros = new Object[] {salon.getEdificioId(), salon.getSalonId(),
					salon.getNombreSalon(), salon.getNumAlumnos(), salon.getEstado()};
			
			if (enocJdbc.update(comando,parametros)==1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.SalonDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg( CatSalon salon ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CAT_SALON" 
				+" SET EDIFICIO_ID = ?,"
				+" NOMBRE_SALON = ?,"
				+" NUM_ALUMNOS = TO_NUMBER(?,'999'),"
				+" ESTADO = ?"
				+" WHERE SALON_ID = ? ";
			Object[] parametros = new Object[] {salon.getEdificioId(), salon.getNombreSalon(), 
			salon.getNumAlumnos(), salon.getEstado(), salon.getSalonId()};
			
			if (enocJdbc.update(comando,parametros)==1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.SalonDao|updateReg|:"+ex);
		}
		
		return ok;
	}	
	
	public boolean deleteReg( String salonId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CAT_SALON WHERE SALON_ID = ? ";
			Object[] parametros = new Object[] {salonId};
			
			if (enocJdbc.update(comando,parametros)==1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.SalonDao|deleteReg|: "+ex);
		}
		
		return ok;
	}
	
	public CatSalon mapeaRegId(  String salonId) {
		
		CatSalon salon = new CatSalon();
		 
		try{
			String comando = "SELECT EDIFICIO_ID, SALON_ID, NOMBRE_SALON, NUM_ALUMNOS, ESTADO"
					+ " FROM ENOC.CAT_SALON WHERE SALON_ID = ?";
			Object[] parametros = new Object[] {salonId};
			salon = enocJdbc.queryForObject(comando, new CatSalonMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.SalonDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return salon;
	}
	
	public boolean existeReg( String salonId) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_SALON WHERE SALON_ID = ?";
			Object[] parametros = new Object[] {salonId};
			
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.SalonDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean salonDeEdificio(String edificioId, String salonId) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_SALON WHERE EDIFICIO_ID = ? AND SALON_ID = ?";
			Object[] parametros = new Object[] {edificioId,salonId};
			
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.SalonDao|salonDeEdificio|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg( String edificioId) {
		
		String maximo 			= "1";
		
		try{
			String comando = "SELECT COALESCE(MAX(TO_NUMBER(SUBSTR(SALON_ID,4,3),'999')+1),1) AS MAXIMO FROM ENOC.CAT_SALON WHERE EDIFICIO_ID = ?";
			Object[] parametros = new Object[] {edificioId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class, parametros);
 				if (maximo.length()==1) maximo = "00"+maximo;
 				if (maximo.length()==2) maximo = "0"+maximo;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.SalonDao|maximoReg|:"+ex);
		}
		
		return maximo;		
	}
	
	public String nombreSalon( String salonId) {
		
		String nombreSalon = "";
		
		try{
			String comando = "SELECT NOMBRE_SALON FROM ENOC.CAT_SALON WHERE SALON_ID = ?";
			Object[] parametros = new Object[] {salonId};
			nombreSalon = enocJdbc.queryForObject(comando, String.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.SalonDao|nombreSalon|:"+ex);
		}
		
		return nombreSalon;
	}	
	
	public String getEstado( String salonId) {
		
		String nombreSalon = "";
		
		try{
			String comando = ("SELECT ESTADO FROM ENOC.CAT_SALON WHERE SALON_ID = ?");
			nombreSalon = enocJdbc.queryForObject(comando, String.class);
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.SalonDao|getEstado|:"+ex);
		}
		
		return nombreSalon;
	}
	
	public boolean existeEdificio( String facultadId) {
		boolean ok = false;
		
		try{
			String comando = ("SELECT * FROM ENOC.CAT_SALON WHERE EDIFICIO_ID = ?"); 
			if (enocJdbc.queryForObject(comando, Integer.class)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.existeEdificio|existeReg|:"+ex);
		}
		
		return ok;
	}
		
	public List<CatSalon> getLista( String edificioId, String orden ) {
		
		List<CatSalon> lista = new ArrayList<CatSalon>();
		
		try{
			String comando = "SELECT EDIFICIO_ID, SALON_ID, NOMBRE_SALON, NUM_ALUMNOS, ESTADO FROM ENOC.CAT_SALON WHERE EDIFICIO_ID = ? "+ orden;
			lista = enocJdbc.query(comando, new CatSalonMapper(), edificioId);			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.SalonDao|getLista|:"+ex);
		}		
		return lista;
	}
	
	public List<CatSalon> getListaActivos( String edificioId, String orden ){
		
		List<CatSalon> lista = new ArrayList<CatSalon>();		
		try{
			String comando = "SELECT EDIFICIO_ID, SALON_ID, NOMBRE_SALON, NUM_ALUMNOS, ESTADO"
			+ " FROM ENOC.CAT_SALON"
			+ " WHERE EDIFICIO_ID = ?"
			+ " AND ESTADO = 'A' "+ orden;
			lista = enocJdbc.query(comando, new CatSalonMapper(), edificioId);			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.SalonDao|getListaActivos|:"+ex);
		}
		
		return lista;
	}
	
	public List<CatSalon> lisEnHorario( String edificioId, String cargaId, String orden ){
		
		List<CatSalon> lista = new ArrayList<CatSalon>();		
		try{
			String comando = "SELECT EDIFICIO_ID, SALON_ID, NOMBRE_SALON, NUM_ALUMNOS, ESTADO"
			+ " FROM ENOC.CAT_SALON"
			+ " WHERE EDIFICIO_ID = ? AND SALON_ID IN (SELECT SALON_ID FROM ENOC.CARGA_GRUPO_HORA WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ? AND SALON_ID != '0') "+ orden;
			lista = enocJdbc.query(comando, new CatSalonMapper(), edificioId, cargaId);	
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.SalonDao|lisEnHorario|:"+ex);
		}		
		return lista;
	}
	
	public List<CatSalon> getListAll( String orden ) {
		
		List<CatSalon> lista = new ArrayList<CatSalon>();		
		try{
			String comando = "SELECT EDIFICIO_ID, SALON_ID, NOMBRE_SALON, NUM_ALUMNOS, ESTADO FROM ENOC.CAT_SALON "+ orden;
			lista = enocJdbc.query(comando, new CatSalonMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.SalonDao|getListAll|:"+ex);
		}		
		return lista;
	}
	
	public HashMap<String,CatSalon> getMapAll( String orden ) {
		
		HashMap<String,CatSalon> mapa = new HashMap<String,CatSalon>();
		List<CatSalon> lista = new ArrayList<CatSalon>();
		
		try{
			String comando = "SELECT EDIFICIO_ID, SALON_ID, NOMBRE_SALON, NUM_ALUMNOS, ESTADO FROM ENOC.CAT_SALON  "+ orden;
			lista = enocJdbc.query(comando, new CatSalonMapper());
			for(CatSalon objeto:lista){
				mapa.put(objeto.getSalonId(), objeto);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.SalonDao|getMapAll|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaNombresSalones() {
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT SALON_ID AS LLAVE, NOMBRE_SALON AS VALOR FROM ENOC.CAT_SALON";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.SalonDao|mapaNombresSalones|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaSalones( ) {
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT EDIFICIO_ID AS LLAVE, COUNT(SALON_ID) AS VALOR FROM ENOC.CAT_SALON GROUP BY EDIFICIO_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.SalonDao|mapaSalones|:"+ex);
		}
		
		return mapa;
	}
}