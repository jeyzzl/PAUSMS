package aca.portafolio.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PorEmpDoctoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(PorEmpDocto porEmpDocto) {
		boolean ok = false;

		try{
			String comando = "INSERT INTO DANIEL.POR_EMPDOCTO(CODIGO_PERSONAL, PERIODO_ID, DOCUMENTO_ID, HOJAS, FECHA, USUARIO) "+
				"VALUES( ?, ?, ?, ?,  TO_DATE(?,'DD/MM/YYYY'), ?)";
			
			Object[] parametros = new Object[] {
				porEmpDocto.getCodigoPersonal(),porEmpDocto.getPeriodoId(),porEmpDocto.getDocumentoId(),porEmpDocto.getHojas(),porEmpDocto.getFecha(),porEmpDocto.getUsuario()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorEmpDoctoDao|insertReg|:"+ex);	
		}
		
		return ok;
	}	
	
	public boolean updateReg(PorEmpDocto porEmpDocto){
		boolean ok = false;
		
		try{
			String comando = "UPDATE DANIEL.POR_EMPDOCTO SET HOJAS = ?, FECHA = TO_DATE(?,'DD/MM/YYYY'), USUARIO = ?" +
				" WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = ? AND DOCUMENTO_ID = ?";
			
			Object[] parametros = new Object[] {
				porEmpDocto.getHojas(),porEmpDocto.getFecha(),porEmpDocto.getUsuario(),porEmpDocto.getCodigoPersonal(),porEmpDocto.getPeriodoId(),porEmpDocto.getDocumentoId()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorEmpDoctoDao|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean updateHojas(PorEmpDocto porEmpDocto){
		boolean ok = false;
		
		try{
			String comando = "UPDATE DANIEL.POR_EMPDOCTO SET HOJAS = ?, FECHA = TO_DATE(?,'DD/MM/YYYY'), USUARIO = ?" +
				" WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = ? AND DOCUMENTO_ID = ?";
			
			Object[] parametros = new Object[] {
				porEmpDocto.getHojas(),porEmpDocto.getFecha(),porEmpDocto.getUsuario(),porEmpDocto.getCodigoPersonal(),porEmpDocto.getPeriodoId(),porEmpDocto.getDocumentoId()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorEmpDoctoDao|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean deleteReg(String codigoPersonal, String periodoId, String documentoId){
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM DANIEL.POR_EMPDOCTO WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = ? AND DOCUMENTO_ID = ?";
			
			Object[] parametros = new Object[] {codigoPersonal,periodoId,documentoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorEmpDoctoDao|deleteReg|:"+ex);			
		}

		return ok;
	}
	
	public PorEmpDocto mapeaRegId(String codigoPersonal, String periodoId, String documentoId){
		PorEmpDocto porEmpDocto = new PorEmpDocto();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, PERIODO_ID, DOCUMENTO_ID, HOJAS, FECHA, USUARIO" +
					" FROM DANIEL.POR_EMPDOCTO WHERE CODIGO_PERTSONAL = ? AND PERIODO_ID = ? DOCUMENTO_ID = ?";		
			
			Object[] parametros = new Object[] {codigoPersonal,periodoId,documentoId};
			porEmpDocto = enocJdbc.queryForObject(comando, new PorEmpDoctoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorEmpDoctoDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return porEmpDocto;
	}
	
	public boolean existeReg(String codigoPersonal, String periodoId, String documentoId){
		boolean ok = false;
		
		try{
			String comando = "SELECT * FROM DANIEL.POR_EMPDOCTO WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = ? AND DOCUMENTO_ID = ? ";	
			
			Object[] parametros = new Object[] {codigoPersonal,periodoId,documentoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorEmpDoctoDao|existeReg|:"+ex);
		}
		
		return ok;
	}	
	
	public String maximoReg(String codigoPersonal, String periodoId, String documentoId){
		String maximo = "1";
		
		try{
			String comando = "SELECT * FROM DANIEL.POR_EMPDOCTO WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = ? AND DOCUMENTO_ID = ? ";	
			
			Object[] parametros = new Object[] {codigoPersonal,periodoId,documentoId};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class, parametros);
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorEmpDoctoDao|existeReg|:"+ex);
		}
		
		return maximo;
	}
	
	public String getHojas(String codigoPersonal, String periodoId, String documentoId){
		String hojas = "";
		
		try{
			String comando = "SELECT COALESCE(HOJAS,'-') AS HOJAS FROM DANIEL.POR_EMPDOCTO WHERE CODIGO_PERSONAL = ?"+
				" AND PERIODO_ID = ? AND DOCUMENTO_ID = ?";
			
			Object[] parametros = new Object[] {codigoPersonal,periodoId,documentoId};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				hojas = enocJdbc.queryForObject(comando, String.class, parametros);
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorEmpDoctoDao|maximoReg|:"+ex);
		}
		
		return hojas;
	}
	
	public List<PorEmpDocto> getListAll(String orden ){
		List<PorEmpDocto> lista = new ArrayList<PorEmpDocto>();
	 
		try{
			String comando = "SELECT CODIGO_PERSONAL, PERIODO_ID, DOCUMENTO_ID, HOJAS, FECHA, USUARIO"+
					" FROM DANIEL.POR_EMPDOCTO "+orden;

			lista = enocJdbc.query(comando, new PorEmpDoctoMapper());
		
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorEmpDoctoDao|getListAll|:"+ex);
		}
	
		return lista;
	}
	
	public List<PorEmpDocto> getListEmpleados(String usuario, String orden ){
		List<PorEmpDocto> lista	= new ArrayList<PorEmpDocto>();
	 
		try{
			String comando = "SELECT CODIGO_PERSONAL, PERIODO_ID, DOCUMENTO_ID, HOJAS, FECHA, USUARIO"+
					" FROM DANIEL.POR_EMPDOCTO WHERE USUARIO = ? "+orden;
			
			lista = enocJdbc.query(comando, new PorEmpDoctoMapper(), usuario);
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorEmpDoctoDao|getListEmpleados|:"+ex);
		}
	
		return lista;
	}
	
	public HashMap<String,PorEmpDocto> getMapAll(String orden ){
		HashMap<String,PorEmpDocto> mapa = new HashMap<String,PorEmpDocto>();
		List<PorEmpDocto> lista			= new ArrayList<PorEmpDocto>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, PERIODO_ID, DOCUMENTO_ID, HOJAS, FECHA, USUARIO"+
					" FROM DANIEL.POR_PERIODO "+ orden;		
			
			lista = enocJdbc.query(comando, new PorEmpDoctoMapper());
			
			for(PorEmpDocto emp : lista) {
				mapa.put(emp.getCodigoPersonal()+emp.getPeriodoId()+emp.getDocumentoId(), emp);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorEmpDoctoDao|getMapAll|:"+ex);
		}
		
		return mapa;
	}	

}
