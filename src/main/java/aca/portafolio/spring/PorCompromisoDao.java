package aca.portafolio.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PorCompromisoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(PorCompromiso objeto){
		boolean ok = false;
		try{
			String comando = "INSERT INTO DANIEL.POR_COMPROMISO(CODIGO_PERSONAL, PERIODO_ID, DEPARTAMENTO, EDUCAR, MODELAR, INVESTIGAR, SERVIR, PROCLAMAR, ESPERANZA, ESTADO, FECHA) "+
				" VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE)";
				
				Object[] parametros = new Object[] {
					objeto.getCodigoPersonal(),objeto.getPeriodoId(),objeto.getDepartamento(),objeto.getEducar(),
					objeto.getModelar(),objeto.getInvestigar(),objeto.getServir(),objeto.getProclamar(),objeto.getEsperanza(),objeto.getEstado()
				};
				if (enocJdbc.update(comando,parametros)==1){
					ok = true;
				}
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.spring.PorCompromiso|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg(PorCompromiso objeto){
		boolean ok = false;
		try{
			String comando = "UPDATE DANIEL.POR_COMPROMISO " 
						   + " SET DEPARTAMENTO = ?, EDUCAR = ?, MODELAR = ?, INVESTIGAR = ?, SERVIR = ?, PROCLAMAR = ?, ESPERANZA = ?, ESTADO = ?, FECHA = SYSDATE WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = ? ";

			Object[] parametros = new Object[] {
				objeto.getDepartamento(),objeto.getEducar(),objeto.getModelar(),objeto.getInvestigar(),objeto.getServir(),objeto.getProclamar(),objeto.getEsperanza(),objeto.getEstado(),
				objeto.getCodigoPersonal(),objeto.getPeriodoId()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorCompromiso|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean updateEstado(String codigoPersonal, String periodoId, String estado) {
		
		boolean ok = false;
		try{
			String comando = "UPDATE DANIEL.POR_COMPROMISO "+
				"SET ESTADO = ? WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = ?";
			Object[] parametros = new Object[] {estado, codigoPersonal, periodoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex) {
			System.out.println("Error - aca.portafolio.spring.PorCompromiso|updateEstado|:"+ex);
		}
		
		return ok;
	}
	
	public PorCompromiso mapeaRegId(String codigoPersonal, String periodoId){
		PorCompromiso objeto = new PorCompromiso();
		try{
			String comando = "SELECT CODIGO_PERSONAL, PERIODO_ID, DEPARTAMENTO, EDUCAR, MODELAR, INVESTIGAR, SERVIR, PROCLAMAR, ESPERANZA, ESTADO, TO_CHAR(FECHA,'YYYY/MM/DD HH24:MI:SS') AS FECHA "
					+ " FROM DANIEL.POR_COMPROMISO WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = ?"; 
					
					Object[] parametros = new Object[] {codigoPersonal,periodoId};
					objeto = enocJdbc.queryForObject(comando, new PorCompromisoMapper(), parametros);
					
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorCompromiso|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}

		return objeto;
	}
	
	public boolean existeReg(String codigoPersonal, String periodoId){
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM DANIEL.POR_COMPROMISO WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = ?"; 
			
			Object[] parametros = new Object[] {codigoPersonal,periodoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorCompromiso|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public HashMap<String,PorCompromiso> mapaCompromiso(String periodoId) {
		HashMap<String,PorCompromiso> mapa = new HashMap<String,PorCompromiso>();
		List<PorCompromiso> lista = new ArrayList<PorCompromiso>();
		try{
			String comando = "SELECT CODIGO_PERSONAL, PERIODO_ID, DEPARTAMENTO, EDUCAR, MODELAR, INVESTIGAR, SERVIR, PROCLAMAR, ESPERANZA, ESTADO, TO_CHAR(FECHA,'YYYY/MM/DD HH24:MI:SS') AS FECHA "
					+ " FROM DANIEL.POR_COMPROMISO"
					+ " WHERE PERIODO_ID = ?";
			lista = enocJdbc.query(comando, new PorCompromisoMapper(), periodoId);
			for(PorCompromiso compromiso : lista) {
				mapa.put(compromiso.getCodigoPersonal(), compromiso);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorDocumentoDao|mapaCompromiso|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaEducar(String campo) {
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		String comando = "";
		
		try{
			if (campo.equals("Educar")) {
				comando = "SELECT CODIGO_PERSONAL||PERIODO_ID AS LLAVE, LENGTH(EDUCAR) AS VALOR FROM DANIEL.POR_COMPROMISO";
			}
			if (campo.equals("Modelar")) {
				comando = "SELECT CODIGO_PERSONAL||PERIODO_ID AS LLAVE, LENGTH(MODELAR) AS VALOR FROM DANIEL.POR_COMPROMISO";
			}
		    if (campo.equals("Investigar")) {
			    comando = "SELECT CODIGO_PERSONAL||PERIODO_ID AS LLAVE, LENGTH(INVESTIGAR) AS VALOR FROM DANIEL.POR_COMPROMISO";
		    }
		    if (campo.equals("Servir")) {
			    comando = "SELECT CODIGO_PERSONAL||PERIODO_ID AS LLAVE, LENGTH(SERVIR) AS VALOR FROM DANIEL.POR_COMPROMISO";
			}
			if (campo.equals("Proclamar")) {
				comando = "SELECT CODIGO_PERSONAL||PERIODO_ID AS LLAVE, LENGTH(PROCLAMAR) AS VALOR FROM DANIEL.POR_COMPROMISO";
			}
			if (campo.equals("Esperanza")) {
				comando = "SELECT CODIGO_PERSONAL||PERIODO_ID AS LLAVE, LENGTH(ESPERANZA) AS VALOR FROM DANIEL.POR_COMPROMISO";
			}

			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorDocumentoDao|mapaEducar|:"+ex);
		}		
		return mapa;
	}
}
