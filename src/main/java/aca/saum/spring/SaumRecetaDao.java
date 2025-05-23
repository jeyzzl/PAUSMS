package aca.saum.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SaumRecetaDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(SaumReceta receta ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.SAUM_RECETA"
					+ " (ID, VERSION, CALORIAS, CARBOHIDRATOS, COLESTEROL, COLOR,"
					+ " FIBRA, FORMA, GRASA, NOMBRE, PORCION, PROTEINAS, RENDIMIENTO,"
					+ " SABOR, SODIO, TEMPERATURA, TEXTURA, TIEMPO, TIPO_PLATO) "
					+ " VALUES( TO_NUMBER(?,'9999999'), TO_NUMBER(?,'9999999'),"
					+ " ?, ?, ?,"
					+ " ?, ?, ?, ?,"
					+ " ?, ?, ?, TO_NUMBER(?,'99999999.99'),"
					+ " ?, ?, ?, ?,"
					+ " ?, ? ) ";
			
			Object[] parametros = new Object[] {receta.getId(), receta.getVersion(),
					receta.getCalorias(), receta.getCarbohidratos(), receta.getColesterol(),
					receta.getColor(), receta.getFibra(), receta.getForma(), receta.getGrasa(),
					receta.getNombre(), receta.getPorcion(), receta.getProteinas(),	receta.getRendimiento(),
					receta.getSabor(), receta.getSodio(), receta.getTemperatura(), receta.getTextura(),
					receta.getTiempo(), receta.getTipoPlato()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumRecetaDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg(SaumReceta receta ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.SAUM_RECETA"
				+ " SET VERSION = TO_NUMBER(?,'9999999'),"
				+ " CALORIAS = ?,"
				+ " CARBOHIDRATOS = ?,"
				+ " COLESTEROL = ?,"
				+ " COLOR = ?,"
				+ " FIBRA = ?,"
				+ " FORMA = ?,"
				+ " GRASA = ?,"
				+ " NOMBRE = ?,"
				+ " PORCION = ?,"
				+ " PROTEINAS = ?,"
				+ " RENDIMIENTO = TO_NUMBER(?,'99999999.99'),"
				+ " SABOR = ?,"
				+ " SODIO = ?,"
				+ " TEMPERATURA = ?,"
				+ " TEXTURA = ?,"
				+ " TIEMPO = ?,"
				+ " TIPO_PLATO = ?"
				+ " WHERE ID = TO_NUMBER(?,'9999999')";
			
			Object[] parametros = new Object[] {receta.getVersion(),
					receta.getCalorias(), receta.getCarbohidratos(), receta.getColesterol(),
					receta.getColor(), receta.getFibra(), receta.getForma(), receta.getGrasa(),
					receta.getNombre(), receta.getPorcion(), receta.getProteinas(),	receta.getRendimiento(),
					receta.getSabor(), receta.getSodio(), receta.getTemperatura(), receta.getTextura(),
					receta.getTiempo(), receta.getTipoPlato(), receta.getId()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumRecetaDao|updateReg|:"+ex);		
		}
		return ok;
	}	
	
	public boolean updateImagen(String id, byte[] imagen) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.SAUM_RECETA"
				+ " SET IMAGEN = ?"
				+ " WHERE ID = TO_NUMBER(?,'9999999')";
			
			Object[] parametros = new Object[] {imagen,id};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumRecetaDao|updateImagen|:"+ex);		
		}
		return ok;
	}
	
	public boolean updateImagenVacia(String id) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.SAUM_RECETA"
				+ " SET IMAGEN = NULL"
				+ " WHERE ID = TO_NUMBER(?,'9999999')";
			
			Object[] parametros = new Object[] {id};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumRecetaDao|updateImagenVacia|:"+ex);		
		}
		return ok;
	}
	
	public boolean deleteReg(String id ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.SAUM_RECETA WHERE ID = TO_NUMBER(?,'9999999')";
			
			Object[] parametros = new Object[] {id};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumRecetaDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public SaumReceta mapeaRegId( String id ){
		SaumReceta receta = new SaumReceta();		 
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.SAUM_RECETA WHERE ID = TO_NUMBER(?,'9999999')";		
			Object[] parametros = new Object[] {id};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				comando = "SELECT ID, VERSION, CALORIAS, CARBOHIDRATOS, COLESTEROL, COLOR,"
					+ " FIBRA, FORMA, GRASA, NOMBRE, PORCION, PROTEINAS, RENDIMIENTO,"
					+ " SABOR, SODIO, TEMPERATURA, TEXTURA, TIEMPO, TIPO_PLATO "
					+ " FROM ENOC.SAUM_RECETA "
					+ " WHERE ID = TO_NUMBER(?,'9999999')";				
				receta = enocJdbc.queryForObject(comando, new SaumRecetaMapper(), parametros);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumRecetaDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return receta;	
	}	
	
	public SaumReceta mapeaRegLargo(String id) {
		SaumReceta receta = new SaumReceta();		 
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.SAUM_RECETA WHERE ID = TO_NUMBER(?,'9999999')";		
			Object[] parametros = new Object[] {id};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				comando = "SELECT ID, VERSION, CALORIAS, CARBOHIDRATOS, COLESTEROL, COLOR,"
					+ " FIBRA, FORMA, GRASA, NOMBRE, PORCION, PROTEINAS, RENDIMIENTO,"
					+ " SABOR, SODIO, TEMPERATURA, TEXTURA, TIEMPO, TIPO_PLATO, IMAGEN "
					+ " FROM ENOC.SAUM_RECETA "
					+ " WHERE ID = TO_NUMBER(?,'9999999')";			
				receta = enocJdbc.queryForObject(comando, new SaumRecetaMapperLargo(), parametros);
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumRecetaDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return receta;		
	}	
	
	public boolean existeReg(String id) {
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.SAUM_RECETA WHERE ID = TO_NUMBER(?,'9999999') ";			
			Object[] parametros = new Object[] {id};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumRecetaDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public boolean existeImagen(String id) {
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.SAUM_RECETA WHERE ID = TO_NUMBER(?,'9999999') AND IMAGEN IS NOT NULL";			
			Object[] parametros = new Object[] {id};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumRecetaDao|existeImagen|:"+ex);
		}
		return ok;
	}
	
	public String getNombre( String id ) {
		String nombre = "X";		 
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.SAUM_RECETA WHERE ID = TO_NUMBER(?,'9999999')";			
			Object[] parametros = new Object[] {id};
			if ( enocJdbc.queryForObject(comando, Integer.class, parametros ) >= 1){
				comando = "SELECT NOMBRE FROM ENOC.SAUM_RECETA WHERE ID = TO_NUMBER(?,'9999999')";
				nombre = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumRecetaDao|getNombre|:"+ex);
			ex.printStackTrace();
		}
		return nombre;
		
	}
	
	public String maximoReg() {
		int maximo = 1;		
		try{
			String comando = "SELECT COALESCE(MAX(ID)+1,1) FROM ENOC.SAUM_RECETA";			
			if (enocJdbc.queryForObject(comando,Integer.class)>=1){
				maximo = enocJdbc.queryForObject(comando,Integer.class);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumRecetaDao|maximoReg|:"+ex);
		}
		return String.valueOf(maximo);
	}
	
	public List<SaumReceta> listAll( String orden) {
		List<SaumReceta> lista		= new ArrayList<SaumReceta>();
		try{
			String comando = " SELECT ID, VERSION, CALORIAS, CARBOHIDRATOS, COLESTEROL, COLOR,"
				+ " FIBRA, FORMA, GRASA, NOMBRE, PORCION, PROTEINAS, RENDIMIENTO,"
				+ " SABOR, SODIO, TEMPERATURA, TEXTURA, TIEMPO, TIPO_PLATO"
				+ " FROM ENOC.SAUM_RECETA "+orden;			
			lista = enocJdbc.query(comando, new SaumRecetaMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumRecetaDao|listAll|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String,SaumReceta> mapaRecetasCompletas( ) {	
		
		HashMap<String,SaumReceta> mapa = new HashMap<String,SaumReceta>();
		List<SaumReceta> lista		= new ArrayList<SaumReceta>();		
		try{
			String comando = "SELECT ID, VERSION, CALORIAS, CARBOHIDRATOS, COLESTEROL, COLOR,"
					+ " FIBRA, FORMA, GRASA, NOMBRE, PORCION, PROTEINAS, RENDIMIENTO,"
					+ " SABOR, SODIO, TEMPERATURA, TEXTURA, TIEMPO, TIPO_PLATO"
					+ " FROM SAUM_RECETA";
			lista = enocJdbc.query(comando, new SaumRecetaMapper());
			for (SaumReceta objeto : lista ) {
				mapa.put(objeto.getId(), objeto);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumRecetaDao|mapaRecetasCompletas|:"+ex);
			ex.printStackTrace();
		}		
		return mapa;		
	}
	
	public HashMap<String,String> mapaRecetas( ) {	
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT ID AS LLAVE, NOMBRE AS VALOR FROM SAUM_RECETA";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumRecetaDao|mapaRecetas|:"+ex);
			ex.printStackTrace();
		}		
		return mapa;		
	}
	
	public HashMap<String,String> mapaImagenes() {
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT ID AS LLAVE, ID AS VALOR"
					+ " FROM ENOC.SAUM_RECETA"
					+ " WHERE IMAGEN IS NOT NULL";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumRecetaDao|mapaImagenes|:"+ex);
		}		
		return mapa;
	}
	
}