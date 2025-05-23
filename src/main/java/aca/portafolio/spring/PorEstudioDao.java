package aca.portafolio.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PorEstudioDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(PorEstudio porEstudio){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO DANIEL.POR_ESTUDIO( CODIGO_PERSONAL, FOLIO, FECHA, NIVEL_ID, TITULO, HOJAS) "+
				"VALUES( ?, TO_NUMBER(?,'99'), TO_DATE(?,'DD/MM/YYYY'), TO_NUMBER(?,'99'), ?, ?)";
			
			Object[] parametros = new Object[] {
				porEstudio.getCodigoPersonal(),porEstudio.getFolio(),porEstudio.getFecha(),porEstudio.getNivelId(),porEstudio.getTitulo(),porEstudio.getHojas()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
				
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorEstudioDao|insertReg|:"+ex);	
		}
		
		return ok;
	}	
	
	public boolean updateReg(PorEstudio porEstudio){
		boolean ok = false;
		
		try{
			String comando = "UPDATE DANIEL.POR_ESTUDIO"+ 
				" SET FECHA = TO_DATE(?,'DD/MM/YYYY'), NIVEL_ID = ?, TITULO = ?, HOJAS = ?" +
				" WHERE CODIGO_PERSONAL = ? AND FOLIO = ?";
			
			Object[] parametros = new Object[] {
				porEstudio.getFecha(),porEstudio.getNivelId(),porEstudio.getTitulo(),porEstudio.getHojas(),porEstudio.getCodigoPersonal(),porEstudio.getFolio()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorEstudioDao|updateReg|:"+ex);
		}
		
		return ok;
	}
	
	
	public boolean deleteReg(String codigoPersonal, String folio){
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM DANIEL.POR_ESTUDIO WHERE CODIGO_PERSONAL = ? AND FOLIO = ?";
			
			Object[] parametros = new Object[] {codigoPersonal,folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorEstudioDao|deleteReg|:"+ex);			
		}

		return ok;
	}
	
	public PorEstudio mapeaReg(String codigoPersonal, String folio){
		PorEstudio porEstudio = new PorEstudio();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, FOLIO, FECHA, NIVEL_ID, TITULO, HOJAS "+													
					" FROM DANIEL.POR_ESTUDIO WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'99')";
			
				Object[] parametros = new Object[] {codigoPersonal,folio};
				porEstudio = enocJdbc.queryForObject(comando, new PorEstudioMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorEstudioDao|mapeaRegId|:"+ex);
		}
		return porEstudio;		
	}
	
	public boolean existeReg(String codigoPersonal, String folio) { 
		boolean ok = false;
		
		try{
			String comando = "SELECT * FROM DANIEL.POR_ESTUDIO WHERE CODIGO_PERSONAL = ? AND FOLIO = ?";
			
			Object[] parametros = new Object[] {codigoPersonal,folio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorEstudioDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg(String codigoPersonal) {
		String maximo = "1";
		
		try{
			String comando = "SELECT COALESCE(MAX(FOLIO)+1, 1) AS MAXIMO"+
				" FROM DANIEL.POR_ESTUDIO WHERE CODIGO_PERSONAL = ? "; 
			
			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class, parametros);
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorEstudioDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public List<PorEstudio> getListAll(String orden ){
		List<PorEstudio> lista	= new ArrayList<PorEstudio>();
	 
		try{
			String comando = "SELECT CODIGO_PERSONAL, FOLIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, NIVEL_ID, TITULO, HOJAS"+
					" FROM DANIEL.POR_ESTUDIO "+orden; 
			
			lista = enocJdbc.query(comando, new PorEstudioMapper());
		
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorEstudioDao|getListAll|:"+ex);
		}
	
		return lista;
	}
	
	public List<PorEstudio> getListEmpleado(String codigoPersonal, String orden ) {
		List<PorEstudio> lista	= new ArrayList<PorEstudio>();
	 
		try{
			String comando = "SELECT CODIGO_PERSONAL, FOLIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, NIVEL_ID, TITULO, COALESCE(HOJAS,'0') AS HOJAS"+
					" FROM DANIEL.POR_ESTUDIO " +
					" WHERE CODIGO_PERSONAL = ? "+orden;

			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new PorEstudioMapper(), parametros);
		
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorEstudioDao|getListEmpleado|:"+ex);
		}
	
		return lista;
	}
	
	public HashMap<String,PorEstudio> getMapAll(String orden ) {
		HashMap<String,PorEstudio> mapa = new HashMap<String,PorEstudio>();
		List<PorEstudio> lista		 = new ArrayList<PorEstudio>();
		
		try{
			String comando = "SELECT SELECT CODIGO_PERSONAL, FOLIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, NIVEL_ID, TITULO, COALESCE(HOJAS,'0') AS HOJAS"+
					" FROM DANIEL.POR_ESTUDIO "+ orden;
			
			lista = enocJdbc.query(comando, new PorEstudioMapper());
			for (PorEstudio unidad : lista ) {
				mapa.put(unidad.getNivelId(),unidad);
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorEstudioDao|getMapAll|:"+ex);
		}
		
		return mapa;
	}	

}
