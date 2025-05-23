package aca.alumno.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AlumDiasDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AlumDias objeto){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.ALUM_DIAS(CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, DIAS)"
					+ " VALUES(?, ?, ?, ?)";
			
			Object[] parametros = new Object[] {
				objeto.getCodigoPersonal(),objeto.getCargaId(),objeto.getBloqueId(),objeto.getDias() 
			};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumDiasDao|insertReg|:"+ex);
		
		}
		return ok;
	}	
	
	public boolean updateReg(AlumDias objeto){
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.ALUM_DIAS SET DIAS = ? WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND BLOQUE_ID = ?";
			Object[] parametros = new Object[] {
				objeto.getDias(), objeto.getCodigoPersonal(),objeto.getCargaId(),objeto.getBloqueId()
			};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumDiasDao|updateReg|:"+ex);			
		
		}
		return ok;
	}	
	
	public boolean deleteReg(String codigoPersonal, String cargaId, String bloqueId){
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.ALUM_DIAS WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND BLOQUE_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal,cargaId,bloqueId};
 			if (enocJdbc.update(comando, parametros)==1){
 				ok = true;
 			} 	
			
		}catch(Exception ex){
			System.out.println("Error  - aca.alumno.spring.AlumDiasDao|deleteReg|:"+ex);			
		
		}
		return ok;
	}
	
	public AlumDias mapeaRegId(String codigoPersonal, String cargaId, String bloqueId){
		
		AlumDias color = new AlumDias();

		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, DIAS, CARGA_ID_DIAS FROM ENOC.ALUM_DIAS WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND BLOQUE_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal,cargaId,bloqueId};
			color = enocJdbc.queryForObject(comando, new AlumDiasMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumDiasDao|mapeaRegId|:"+ex);
		
		}
		
		return color;
	}

	public boolean existeReg(String codigoPersonal, String cargaId, String bloqueId){
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_DIAS WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND BLOQUE_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal,cargaId,bloqueId};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			} 
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumDiasDao|existeReg|:"+ex);
		
		}
		
		return ok;
	}
	
	public HashMap<String, String> mapaDiasInternado() {
		HashMap<String,String> mapa	= new HashMap<String,String>();
		List<AlumDias>	lista		= new ArrayList<AlumDias>();		
		try{
			String comando ="SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, DIAS FROM ENOC.ALUM_DIAS";
			lista = enocJdbc.query(comando, new AlumDiasMapper());
			for (AlumDias objeto : lista){
				mapa.put(objeto.getCodigoPersonal()+objeto.getCargaId()+objeto.getBloqueId(), objeto.getDias());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumDiasDao|mapaDiasInternado|:"+ex);
		}		
		return mapa;
	}
	
}