package aca.saum.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class SaumIngredienteDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(SaumIngrediente saum ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.SAUM_INGREDIENTE(ID, VERSION, CANTIDAD, ETAPA_ID, MATERIA_ID, PRESENTACION, UNIDAD_MEDIDA, RECETA_ID)"
					+ " VALUES( TO_NUMBER(?,'9999999'), TO_NUMBER(?,'9999999'), "
					+ " TO_NUMBER(?,'99999999.99'), TO_NUMBER(?,'9999999'),"
					+ " TO_NUMBER(?,'9999999'), ?, ?,"
					+ " TO_NUMBER(?,'9999999') )";
			
			Object[] parametros = new Object[] {saum.getId(),saum.getVersion(),saum.getCantidad(),saum.getEtapaId(),
					saum.getMateriaId(),saum.getPresentacion(),saum.getUnidadMedida(),saum.getRecetaId()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumIngredienteDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg(SaumIngrediente saum ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.SAUM_INGREDIENTE"
				+ " SET VERSION = TO_NUMBER(?,'9999999'),"
				+ " CANTIDAD = TO_NUMBER(?,'99999999.99'),"
				+ " ETAPA_ID = TO_NUMBER(?,'9999999'), "
				+ " MATERIA_ID = TO_NUMBER(?,'9999999'),"
				+ " PRESENTACION = ?,"
				+ " UNIDAD_MEDIDA = ?,"
				+ " RECETA_ID = TO_NUMBER(?,'9999999')"	
				+ " WHERE ID = TO_NUMBER(?,'9999999')";
			
			Object[] parametros = new Object[] {saum.getVersion(),saum.getCantidad(),saum.getEtapaId(),
					saum.getMateriaId(),saum.getPresentacion(),saum.getUnidadMedida(), saum.getRecetaId(),
					saum.getId()}; 			 			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumIngredienteDao|updateReg|:"+ex);		
		}
		return ok;
	}	
	
	public boolean deleteReg(String saumId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.SAUM_INGREDIENTE"+ 
				" WHERE ID = TO_NUMBER(?,'9999999')";
			
			Object[] parametros = new Object[] {saumId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumIngredienteDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public SaumIngrediente mapeaRegId(  String saumId) {
		SaumIngrediente ingrediente = new SaumIngrediente();
		 
		try{
			String comando = "SELECT ID, VERSION, CANTIDAD, ETAPA_ID, MATERIA_ID, PRESENTACION, UNIDAD_MEDIDA, RECETA_ID"+
				" FROM ENOC.SAUM_INGREDIENTE WHERE ID = TO_NUMBER(?,'9999999')";			
			Object[] parametros = new Object[] {saumId};
			ingrediente = enocJdbc.queryForObject(comando, new SaumIngredienteMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumIngredienteDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return ingrediente;
		
	}
	
	public boolean existeReg(String saumId) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.SAUM_INGREDIENTE WHERE ID = TO_NUMBER(?,'9999999')"; 
			
			Object[] parametros = new Object[] {saumId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumIngredienteDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String maximoReg() {
		int maximo = 1;
		
		try{
			String comando = "SELECT COALESCE(MAX(ID)+1,1) FROM ENOC.SAUM_INGREDIENTE";
			
			if (enocJdbc.queryForObject(comando,Integer.class)>=1){
				maximo = enocJdbc.queryForObject(comando,Integer.class);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumIngredienteDao|maximoReg|:"+ex);
		}
		return String.valueOf(maximo);
	}
	
	public int getNumIngredientes(  String recetaId ) {
		int numIngredientes = 0;		 
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.SAUM_INGREDIENTE WHERE ETAPA_ID IN (SELECT ID FROM SAUM_ETAPA WHERE RECETA_ID = TO_NUMBER(?,'9999999'))";			
			Object[] parametros = new Object[] {recetaId};
			numIngredientes = enocJdbc.queryForObject(comando, Integer.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumEtapaDao|getNumIngredientes|:"+ex);
			ex.printStackTrace();
		}
		return numIngredientes;
		
	}
	
	public List<SaumIngrediente> lisIngredientesReceta( String recetaId, String orden) {
		List<SaumIngrediente> lista		= new ArrayList<SaumIngrediente>();
		String comando		= "";
		
		try{
			comando = " SELECT ID, VERSION, CANTIDAD, ETAPA_ID, MATERIA_ID, PRESENTACION, UNIDAD_MEDIDA, RECETA_ID"
					+ " FROM ENOC.SAUM_INGREDIENTE"
					+ " WHERE RECETA_ID = ? "+orden;	 
			Object[] parametros = new Object[] {recetaId};
			lista = enocJdbc.query(comando, new SaumIngredienteMapper(), parametros);	
			
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumIngredienteDao|lisIngredientesReceta|:"+ex);
		}
		return lista;
	}
	
	public List<SaumIngrediente> lisIngredientesRecetas( String fechaIni, String fechaFin, String orden) {
		List<SaumIngrediente> lista		= new ArrayList<SaumIngrediente>();
		try{
			String comando = " SELECT ID, VERSION, CANTIDAD, ETAPA_ID, MATERIA_ID, PRESENTACION, UNIDAD_MEDIDA, RECETA_ID"
					+ " FROM ENOC.SAUM_INGREDIENTE"
					+ " WHERE RECETA_ID IN (SELECT RECETA_ID FROM SAUM_COMIDA WHERE FECHA BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')) "+orden;
			Object[] parametros = new Object[] {fechaIni, fechaFin};
			lista = enocJdbc.query(comando, new SaumIngredienteMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumIngredienteDao|lisIngredientesRecetas|:"+ex);
		}
		return lista;
	}
	
	public List<SaumIngrediente> lisIngredientes( String etapaId, String orden) {
		List<SaumIngrediente> lista		= new ArrayList<SaumIngrediente>();
		try{
			String comando = " SELECT ID, VERSION, CANTIDAD, ETAPA_ID, MATERIA_ID, PRESENTACION, UNIDAD_MEDIDA, RECETA_ID"
					+ " FROM ENOC.SAUM_INGREDIENTE"
					+ " WHERE ETAPA_ID = ? "+orden;	 
			Object[] parametros = new Object[] {etapaId};
			lista = enocJdbc.query(comando, new SaumIngredienteMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumIngredienteDao|lisIngredientes|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String,String> mapaIngredientesPorReceta( ) {	
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT RECETA_ID AS LLAVE, COUNT(*) AS VALOR FROM SAUM_INGREDIENTE GROUP BY RECETA_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumIngredienteDao|mapaIngredientesPorReceta|:"+ex);
			ex.printStackTrace();
		}
		
		return mapa;		
	}
	
	public HashMap<String,String> mapaMateriaEnRecetas( ) {	
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT MATERIA_ID AS LLAVE, COUNT(DISTINCT(RECETA_ID)) AS VALOR FROM SAUM_INGREDIENTE GROUP BY MATERIA_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumIngredienteDao|mapaMateriaEnRecetas|:"+ex);
			ex.printStackTrace();
		}
		
		return mapa;		
	}
	
	public HashMap<String,String> mapaIngredientesPorEtapa( ) {	
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT ETAPA_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.SAUM_INGREDIENTE GROUP BY ETAPA_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumIngredienteDao|mapaIngredientesPorEtapa|:"+ex);
			ex.printStackTrace();
		}
		
		return mapa;		
	}

}