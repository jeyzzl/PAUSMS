package aca.portafolio.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PorCosmovisionDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(PorCosmovision porCosmovision){
		boolean ok = false;
		try{
			String comando = "INSERT INTO DANIEL.POR_COSMOVISION(CODIGO_PERSONAL, PERIODO_ID, FILOSOFIA,  MISION, VISION, VALORES, REFLEXION) "+
				" VALUES( ?, ?, ?, ?, ?, ?, ?)";
				
				Object[] parametros = new Object[] {
						porCosmovision.getCodigoPersonal(),porCosmovision.getPeriodoId(),porCosmovision.getFilosofia(),porCosmovision.getMision(),
						porCosmovision.getVision(),porCosmovision.getValores(),porCosmovision.getReflexion()
				};
				if (enocJdbc.update(comando,parametros)==1){
					ok = true;
				}
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.spring.PorCosmovision|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg(PorCosmovision porCosmovision){
		boolean ok = false;
		try{
			String comando = "UPDATE DANIEL.POR_COSMOVISION " 
						   + " SET FILOSOFIA = ?,  MISION = ?, VISION = ?, VALORES = ?, REFLEXION = ? WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = ? ";

			Object[] parametros = new Object[] {
					porCosmovision.getFilosofia(),porCosmovision.getMision(),porCosmovision.getVision(),porCosmovision.getValores(),porCosmovision.getReflexion(),
					porCosmovision.getCodigoPersonal(),porCosmovision.getPeriodoId()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorCosmovision|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	public PorCosmovision mapeaRegId(String codigoPersonal, String periodoId){
		PorCosmovision porCosmovision = new PorCosmovision();
		try{
			String comando = "SELECT * FROM DANIEL.POR_COSMOVISION WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = ?"; 
					
					Object[] parametros = new Object[] {codigoPersonal,periodoId};
					porCosmovision = enocJdbc.queryForObject(comando, new PorCosmovisionMapper(), parametros);
					
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorCosmovision|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}

		return porCosmovision;
	}
	
	public boolean existeReg(String codigoPersonal, String periodoId){
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM DANIEL.POR_COSMOVISION WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = ?"; 
			
			Object[] parametros = new Object[] {codigoPersonal,periodoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorCosmovision|existeReg|:"+ex);
		}
		
		return ok;
	}	

}
