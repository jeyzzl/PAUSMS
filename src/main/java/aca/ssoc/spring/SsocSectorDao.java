package aca.ssoc.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SsocSectorDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(SsocSector sector){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.SSOC_SECTOR (SECTOR_ID, SECTOR_NOMBRE) VALUES(?,?)";
			
			Object[] parametros = new Object[] {
				sector.getSectorId(),sector.getSectorNombre()
			};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.spring.SsocSectorDoa|insertReg|:"+ex);
		}

		return ok;
	}
	
	public boolean updateReg(SsocSector sector){
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.SSOC_SECTOR SET SECTOR_NOMBRE = ? WHERE SECTOR_ID = ?";
			
			Object[] parametros = new Object[] {
				sector.getSectorId(),sector.getSectorNombre()
			};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.spring.SsocSectorDoa|updateReg|:"+ex);
		}

		return ok;
	}
	
	public boolean deleteReg(String sectorId){
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.SSOC_SECTOR WHERE SECTOR_ID = ?"; 

			Object[] parametros = new Object[] {sectorId};
			if (enocJdbc.update(comando, parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.spring.SsocSectorDoa|deleteReg|:"+ex);
		}

		return ok;
	}
	
	public SsocSector mapeaRegId(String sectorId){
		SsocSector sector = new SsocSector();
				
		try{
			String comando = "SELECT SECTOR_ID, SECTOR_NOMBRE FROM ENOC.SSOC_SECTOR WHERE SECTOR_ID = ?";
			
			Object[] parametros = new Object[] {sectorId};
			sector = enocJdbc.queryForObject(comando, new SsocSectorMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.spring.SsocSectorDoa|mapeaRegId|:"+ex);
		}
		
		return sector;
	}
	
	public boolean existeReg(String sectorId){
		boolean ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.SSOC_SECTOR WHERE SECTOR_ID = ? ";
			Object[] parametros = new Object[] {sectorId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.spring.SsocSectorDoa|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg(String sectorId){
		String strMaximo 		= "01";
		int numMaximo			= 0;
		
		try{
			String comando = "SELECT MAX(SECTOR_ID)+1 MAXIMO FROM ENOC.SSOC_SECTOR"; 
			
			if (enocJdbc.queryForObject(comando,Integer.class) >=1 ){
				numMaximo = enocJdbc.queryForObject(comando,Integer.class);
			}	
			
			if (numMaximo<10) {
				strMaximo = "0"+String.valueOf(numMaximo);
			} else {
				strMaximo = String.valueOf(numMaximo);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.spring.SsocSectorDoa|maximoReg|:"+ex);
		}
		
		return strMaximo;
	}
		
	public String getNombre(String sectorId) {
		String nombre = "";		
		try{
			String comando = "SELECT SECTOR_NOMBRE FROM ENOC.SSOC_SECTOR WHERE SECTOR_ID = ? ";			
			Object[] parametros = new Object[] {sectorId};
			if ( enocJdbc.queryForObject(comando, Integer.class, parametros ) >= 1){
				nombre = enocJdbc.queryForObject(comando,String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.spring.SsocSectorDoa|getNombre|:"+ex);
		}		
		return nombre;
	} 
	
	public List<SsocSector> getListAll(){
		List<SsocSector> lista 	= new ArrayList<SsocSector>();
		
		try{
			String comando = "SELECT SECTOR_ID, SECTOR_NOMBRE FROM ENOC.SSOC_SECTOR"; 
			
			lista = enocJdbc.query(comando, new SsocSectorMapper());	
			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.spring.SsocSectorDoa|getListAll|:"+ex);
		}
		
		return lista;
	}

	public HashMap<String,String> mapaSectorNombre() {	
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT SECTOR_ID AS LLAVE, SECTOR_NOMBRE AS VALOR FROM ENOC.SSOC_SECTOR";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.spring.SsocSectorDoa|mapaSectorNombre|:"+ex);
			ex.printStackTrace();
		}
		
		return mapa;		
	}
	
	public HashMap<String,String> mapaNumSector() {	
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT SECTOR AS LLAVE, COUNT(*) AS VALOR FROM ENOC.SSOC_ASIGNACION GROUP BY SECTOR";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.spring.SsocSectorDao|mapaNumSector|:"+ex);
			ex.printStackTrace();
		}
		
		return mapa;		
	}
	
}
