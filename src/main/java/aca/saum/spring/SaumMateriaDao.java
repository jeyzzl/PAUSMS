package aca.saum.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SaumMateriaDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(SaumMateria saum ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.SAUM_MATERIA(ID, NOMBRE, VERSION, ENERGIA, HUMEDAD, FIBRA, CARBOHIDRATO, PROTEINA, LIPIDO, CENIZA,"
					+ " SATURADO, MONO, POLI, COLESTEROL, CALCIO, FOSFORO, HIERRO, MAGNESIO, SELENIO, SODIO, POTASIO, CINC, VITAMINAA, ASCORBICO,"
					+ " TIAMINA, RIBO, NIACINA, PIRIDOXINA, FOLICO, COBALAMINA) VALUES( TO_NUMBER(?,'9999999'), ?, TO_NUMBER(?,'9999999'),"
					+ " TO_NUMBER(?,'9999.99'), TO_NUMBER(?,'9999.99'), TO_NUMBER(?,'9999.99'), TO_NUMBER(?,'9999.99'), TO_NUMBER(?,'9999.99'),"
					+ " TO_NUMBER(?,'9999.99'), TO_NUMBER(?,'9999.99'), TO_NUMBER(?,'9999.99'), TO_NUMBER(?,'9999.99'), TO_NUMBER(?,'9999.99'),"
					+ " TO_NUMBER(?,'9999.99'), TO_NUMBER(?,'9999.99'), TO_NUMBER(?,'9999.99'), TO_NUMBER(?,'9999.99'), TO_NUMBER(?,'9999.99'),"
					+ " TO_NUMBER(?,'9999.99'), TO_NUMBER(?,'9999.99'), TO_NUMBER(?,'9999.99'), TO_NUMBER(?,'9999.99'), TO_NUMBER(?,'9999.99'),"
					+ " TO_NUMBER(?,'9999.99'), TO_NUMBER(?,'9999.99'), TO_NUMBER(?,'9999.99'), TO_NUMBER(?,'9999.99'), TO_NUMBER(?,'9999.99'),"
					+ " TO_NUMBER(?,'9999.99'), TO_NUMBER(?,'9999.99') ) ";
			
			Object[] parametros = new Object[] {saum.getId(),saum.getNombre(),saum.getVersion(),saum.getEnergia(),saum.getHumedad(),saum.getFibra(),
						saum.getCarbohidrato(),saum.getProteina(),saum.getLipido(),saum.getCeniza(),saum.getSaturado(),saum.getMono(),saum.getPoli(),
						saum.getColesterol(),saum.getCalcio(),saum.getFosforo(),saum.getHierro(),saum.getMagnesio(),saum.getSelenio(),saum.getSodio(),
						saum.getPotasio(),saum.getCinc(),saum.getVitaminaa(),saum.getAscorbico(),saum.getTiamina(),saum.getRibo(),saum.getNiacina(),
						saum.getPiridoxina(),saum.getFolico(),saum.getCobalamina()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumMateriaDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg(SaumMateria saum ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.SAUM_MATERIA SET NOMBRE = ?, VERSION = TO_NUMBER(?,'9999999'), ENERGIA = TO_NUMBER(?,'9999.99'),"
					+ " HUMEDAD = TO_NUMBER(?,'9999.99'), FIBRA = TO_NUMBER(?,'9999.99'), CARBOHIDRATO = TO_NUMBER(?,'9999.99'),"
					+ " PROTEINA = TO_NUMBER(?,'9999.99'), LIPIDO = TO_NUMBER(?,'9999.99'), CENIZA = TO_NUMBER(?,'9999.99'),"
					+ " SATURADO = TO_NUMBER(?,'9999.99'), MONO = TO_NUMBER(?,'9999.99'), POLI = TO_NUMBER(?,'9999.99'),"
					+ " COLESTEROL = TO_NUMBER(?,'9999.99'), CALCIO = TO_NUMBER(?,'9999.99'), FOSFORO = TO_NUMBER(?,'9999.99'),"
					+ " HIERRO = TO_NUMBER(?,'9999.99'), MAGNESIO = TO_NUMBER(?,'9999.99'), SELENIO = TO_NUMBER(?,'9999.99'),"
					+ " SODIO = TO_NUMBER(?,'9999.99'), POTASIO = TO_NUMBER(?,'9999.99'), CINC = TO_NUMBER(?,'9999.99'),"
					+ " VITAMINAA = TO_NUMBER(?,'9999.99'), ASCORBICO = TO_NUMBER(?,'9999.99'), TIAMINA = TO_NUMBER(?,'9999.99'),"
					+ " RIBO = TO_NUMBER(?,'9999.99'), NIACINA = TO_NUMBER(?,'9999.99'), PIRIDOXINA = TO_NUMBER(?,'9999.99'),"
					+ " FOLICO = TO_NUMBER(?,'9999.99'), COBALAMINA = TO_NUMBER(?,'9999.99')"
					+ " WHERE ID = TO_NUMBER(?,'9999999')";
			
			Object[] parametros = new Object[] {saum.getNombre(),saum.getVersion(),saum.getEnergia(),saum.getHumedad(),saum.getFibra(),
					saum.getCarbohidrato(),saum.getProteina(),saum.getLipido(),saum.getCeniza(),saum.getSaturado(),saum.getMono(),saum.getPoli(),
					saum.getColesterol(),saum.getCalcio(),saum.getFosforo(),saum.getHierro(),saum.getMagnesio(),saum.getSelenio(),saum.getSodio(),
					saum.getPotasio(),saum.getCinc(),saum.getVitaminaa(),saum.getAscorbico(),saum.getTiamina(),saum.getRibo(),saum.getNiacina(),
					saum.getPiridoxina(),saum.getFolico(),saum.getCobalamina(),saum.getId()}; 			 			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumMateriaDao|updateReg|:"+ex);		
		}
		return ok;
	}	
	
	public boolean deleteReg(String saumId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.SAUM_MATERIA WHERE ID = TO_NUMBER(?,'9999999')";
			
			Object[] parametros = new Object[] {saumId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumMateriaDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public SaumMateria mapeaRegId(  String saumId) {
		 SaumMateria rol = new SaumMateria();
		 
		try{
			String comando = "SELECT ID, NOMBRE, VERSION, ENERGIA, HUMEDAD, FIBRA, CARBOHIDRATO, PROTEINA, LIPIDO, CENIZA, SATURADO,"
					+ " MONO, POLI, COLESTEROL, CALCIO, FOSFORO, HIERRO, MAGNESIO, SELENIO, SODIO, POTASIO, CINC, VITAMINAA, ASCORBICO,"
					+ " TIAMINA, RIBO, NIACINA, PIRIDOXINA, FOLICO, COBALAMINA FROM ENOC.SAUM_MATERIA WHERE ID = TO_NUMBER(?,'9999999')";			
			Object[] parametros = new Object[] {saumId};
			rol = enocJdbc.queryForObject(comando, new SaumMateriaMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumMateriaDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return rol;
		
	}
	
	public boolean existeReg(String saumId) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.SAUM_MATERIA WHERE ID = TO_NUMBER(?,'9999999') ";			
			Object[] parametros = new Object[] {saumId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumMateriaDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String maximoReg() {
		int maximo = 1;
		
		try{
			String comando = "SELECT COALESCE(MAX(ID)+1,1) FROM ENOC.SAUM_MATERIA";
			
			if (enocJdbc.queryForObject(comando,Integer.class)>=1){
				maximo = enocJdbc.queryForObject(comando,Integer.class);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumMateriaDao|maximoReg|:"+ex);
		}
		return String.valueOf(maximo);
	}
	
	public List<SaumMateria> listAll( String orden) {
		List<SaumMateria> lista		= new ArrayList<SaumMateria>();
		String comando		= "";
		
		try{
			comando = "SELECT ID, NOMBRE, VERSION, ENERGIA, HUMEDAD, FIBRA, CARBOHIDRATO, PROTEINA, LIPIDO, CENIZA, SATURADO,"
					+ " MONO, POLI, COLESTEROL, CALCIO, FOSFORO, HIERRO, MAGNESIO, SELENIO, SODIO, POTASIO, CINC, VITAMINAA, ASCORBICO," 
					+ " TIAMINA, RIBO, NIACINA, PIRIDOXINA, FOLICO, COBALAMINA FROM ENOC.SAUM_MATERIA "+orden;	 
			
			lista = enocJdbc.query(comando, new SaumMateriaMapper());	
			
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumMateriaDao|listAll|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String,SaumMateria> mapaMateria( ) {
		
		HashMap<String,SaumMateria> mapa = new HashMap<String,SaumMateria>();
		List<SaumMateria> lista		= new ArrayList<SaumMateria>();
		
		try{
			String comando = "SELECT ID, NOMBRE, VERSION, ENERGIA, HUMEDAD, FIBRA, CARBOHIDRATO, PROTEINA, LIPIDO, CENIZA, SATURADO,"
					+ " MONO, POLI, COLESTEROL, CALCIO, FOSFORO, HIERRO, MAGNESIO, SELENIO, SODIO, POTASIO, CINC, VITAMINAA, ASCORBICO," 
					+ " TIAMINA, RIBO, NIACINA, PIRIDOXINA, FOLICO, COBALAMINA FROM ENOC.SAUM_MATERIA ";
			lista = enocJdbc.query(comando, new SaumMateriaMapper());
			for (SaumMateria materia : lista ) {
				mapa.put(materia.getId(), materia);
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumMateriaDao|mapaMateria|:"+ex);
			ex.printStackTrace();
		}
		
		return mapa;		
	}
	}