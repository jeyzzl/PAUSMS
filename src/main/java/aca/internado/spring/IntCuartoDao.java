package aca.internado.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class IntCuartoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	

	public boolean insertReg(IntCuarto cuarto ){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.INT_CUARTO(DORMITORIO_ID,CUARTO_ID,PASILLO,CUPO,ESTADO) VALUES(?,TRIM(TO_CHAR(?,'000')),?,?,?)"; 
			Object[] parametros = new Object[] {
					cuarto.getDormitorioId(),cuarto.getCuartoId(),cuarto.getPasillo(), cuarto.getCupo(), cuarto.getEstado()
				};
				if (enocJdbc.update(comando,parametros)==1){
					ok = true;
				}	
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.CuartoDao|insertReg|:"+ex);
		}
		
		return ok;
	}
	public boolean updateReg(IntCuarto cuarto ){
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.INT_CUARTO SET PASILLO = ?, CUPO = ?, ESTADO = ? WHERE DORMITORIO_ID = ? AND CUARTO_ID = ?";	
			Object[] parametros = new Object[] {
					cuarto.getPasillo(),cuarto.getCupo(),cuarto.getEstado(), cuarto.getDormitorioId(), cuarto.getCuartoId()
				};
				if (enocJdbc.update(comando,parametros)==1){
					ok = true;
				}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.CuartoDao|updateReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean deleteReg(String dormitorioId, String cuartoId ){
		boolean ok = false;

		try{
			String comando = "DELETE FROM ENOC.INT_CUARTO WHERE DORMITORIO_ID = ? AND CUARTO_ID = ?"; 
			Object[] parametros = new Object[] { dormitorioId, cuartoId};
			
		 	if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.CuartoDao|deleteReg|:"+ex);
		}
		
		return ok;
	}
	
	public IntCuarto mapeaRegId(String dormitorioId, String cuartoId){
		
		IntCuarto cuarto = new IntCuarto();
		
		try{ 
			String comando = "SELECT * FROM ENOC.INT_CUARTO WHERE DORMITORIO_ID = ? AND CUARTO_ID = ? "; 
			Object[] parametros = new Object[] { dormitorioId, cuartoId };
			cuarto = enocJdbc.queryForObject(comando, new IntCuartoMapper(),parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.CuartoDao|mapeaRegId|:"+ex);
		}
		
		return cuarto;
	}
	
	public int tieneCuartos(String dormitorioId){
		
		int cuartos 			= 0;
		
		try{ 
			String comando = "SELECT COUNT(*) AS TOTAL FROM ENOC.INT_CUARTO WHERE DORMITORIO_ID = TO_NUMBER(?,'99')"; 
			Object[] parametros = new Object[] {dormitorioId};
			cuartos = enocJdbc.queryForObject(comando,Integer.class,parametros);	
			
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.CuartoDao|tieneCuartos|:"+ex);
		}
		
		return cuartos; 
	}
	
	public int getCupoDormi(String dormitorioId, String estado){
		
		int cupo 		= 0;
		
		try{ 
			String comando = "SELECT COALESCE(SUM(CUPO),0) AS TOTAL FROM ENOC.INT_CUARTO WHERE DORMITORIO_ID = ? AND ESTADO IN("+estado+")";
			cupo = enocJdbc.queryForObject(comando, Integer.class, dormitorioId);	
			
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.CuartoDao|getCupoDormi|:"+ex);
		}
		
		return cupo; 
	}
	
	public HashMap<String,IntCuarto> mapCuartos(String dormitorioId){
		
		HashMap<String,IntCuarto> map		= new HashMap<String,IntCuarto>();
		List<IntCuarto> lista 				= new ArrayList<IntCuarto>();		
		try{
			String comando = " SELECT DORMITORIO_ID, CUARTO_ID, PASILLO, CUPO, ESTADO FROM ENOC.INT_CUARTO WHERE DORMITORIO_ID = ?";
			lista =	 enocJdbc.query(comando,new IntCuartoMapper(), dormitorioId);
			for (IntCuarto objeto : lista){				
				map.put(objeto.getCuartoId(), objeto);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.DormitorioDao|mapCuartos|:"+ex);
		}		
		return map;
	}

	public HashMap<String,Integer> mapCupoDormitorios(String estado){		
		HashMap<String,Integer> map	= new HashMap<String,Integer>();
		List<aca.Mapa> lista 				= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT DORMITORIO_ID AS LLAVE, COALESCE(SUM(CUPO),0) AS VALOR FROM ENOC.INT_CUARTO WHERE ESTADO IN("+estado+") GROUP BY DORMITORIO_ID";
			lista =	 enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa objeto : lista){					
				map.put(objeto.getLlave(), Integer.parseInt(objeto.getValor()));
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.DormitorioDao|mapCupoDormitorios|:"+ex);
		}		
		return map;
	}
	
	public HashMap<String,Integer> mapCuartosDormitorios(){
		
		HashMap<String,Integer> map	= new HashMap<String,Integer>();
		List<String> lista 				= new ArrayList<String>();
		
		try{
			String comando = " SELECT DISTINCT(DORMITORIO_ID) FROM ENOC.INT_CUARTO";
			lista =	 enocJdbc.queryForList(comando, String.class);
			for (String dormi : lista){	
				int cupo = 0;
				comando = "SELECT COUNT(*) AS TOTAL FROM ENOC.INT_CUARTO WHERE DORMITORIO_ID = "+dormi;			
				
				cupo = enocJdbc.queryForObject(comando,Integer.class);	
				
				map.put(dormi, cupo);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.DormitorioDao|mapCuartosDormitorios|:"+ex);
		}
		
		return map;
	}
}
