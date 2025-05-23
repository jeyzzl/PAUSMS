//Clase  para la tabla ARCH_DOCSTATUS
package aca.archivo.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ArchDocStatusDao {
	
	@Autowired	
	@Qualifier("jdbcEnoc")	
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( ArchDocStatus status )  {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.ARCH_DOCSTATUS(IDDOCUMENTO,IDSTATUS,ESTADO, VALIDO)"
					+ " VALUES(TO_NUMBER(?,'999'),TO_NUMBER(?,'99'),?, ?)";	
			Object[] parametros = new Object[] {status.getIdDocumento(), status.getIdStatus(), status.getEstado(), status.getValido()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchDocStatusDao|insertReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateReg( ArchDocStatus status) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.ARCH_DOCSTATUS"
					+ " SET ESTADO = ?, VALIDO = ?"
					+ " WHERE IDDOCUMENTO = TO_NUMBER(?,'999')"
					+ " AND IDSTATUS = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {status.getEstado(), status.getValido(), status.getIdDocumento(), status.getIdStatus()};
			
			if (enocJdbc.update(comando,parametros)==1)
				ok = true;
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchDocStatusDao|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean deleteReg( String idDocumento, String idStatus ) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.ARCH_DOCSTATUS WHERE IDDOCUMENTO = TO_NUMBER(?,'999') AND IDSTATUS = TO_NUMBER(?,'999')"; 
			Object[] parametros = new Object[] {idDocumento, idStatus};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchDocStatusDao|deleteReg|:"+ex);
		}
		
		return ok;
	}

	public ArchDocStatus mapeaRegId( String idDocumento, String idStatus) {
		
		ArchDocStatus archDoc = new ArchDocStatus();			
		try{
			String comando =  "SELECT IDDOCUMENTO, IDSTATUS, ESTADO, VALIDO"
					+ " FROM ENOC.ARCH_DOCSTATUS"
					+ " WHERE IDDOCUMENTO = TO_NUMBER(?,'999') AND IDSTATUS = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {idDocumento, idStatus};
			archDoc = enocJdbc.queryForObject(comando, new ArchDocStatusMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchDocStatusDao|mapeaRegId|:"+ex);
		}
		
		return archDoc;
	}
	
	public boolean existeReg( String idDocumento, String idStatus) {
		boolean ok 				= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ARCH_DOCSTATUS WHERE IDDOCUMENTO = TO_NUMBER(?,'999') AND IDSTATUS = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {idDocumento, idStatus};			
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.academico.ArchDocStatusDao|existeReg|:"+ex);
		}
		
		return ok;
	}	
	
	public List<ArchDocStatus> lisStatus( String documentoId, String orden ) {
		
		List<ArchDocStatus> lista 	= new ArrayList<ArchDocStatus>();		
		String comando				= "";
		
		try{
			comando = "SELECT IDDOCUMENTO, IDSTATUS, ESTADO, VALIDO FROM ENOC.ARCH_DOCSTATUS WHERE IDDOCUMENTO = ? "+orden;
			Object[] parametros = new Object[] {documentoId};
			lista = enocJdbc.query(comando, new ArchDocStatusMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchDocStatusDao|lisStatus|:"+ex);
		}
		
		return lista;
	}
	
	public List<ArchDocStatus> lisStatusValidos( String planId, String orden ) {		
		List<ArchDocStatus> lista 	= new ArrayList<ArchDocStatus>();				
		try{
			String comando = "SELECT IDDOCUMENTO, IDSTATUS, ESTADO, VALIDO FROM ENOC.ARCH_DOCSTATUS"
					+ " WHERE ESTADO = 'A' AND VALIDO = 'S' AND IDDOCUMENTO IN (SELECT IDDOCUMENTO FROM ARCH_GRUPO_PLAN WHERE PLAN_ID = ?) "+orden;
			Object[] parametros = new Object[] {planId};
			lista = enocJdbc.query(comando, new ArchDocStatusMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchDocStatusDao|lisStatusValidos|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String, String> mapDocumentosConEstados() {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT IDDOCUMENTO AS LLAVE, COUNT(IDSTATUS) AS VALOR FROM ENOC.ARCH_DOCSTATUS GROUP BY IDDOCUMENTO";	
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchDocStatusDao|mapDocumentosConEstados|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, ArchDocStatus> mapaEstadosValidos(){
		HashMap<String, ArchDocStatus> mapa = new HashMap<String, ArchDocStatus>();
		List<ArchDocStatus> lista 		 = new ArrayList<ArchDocStatus>();
		try{
			String comando = "SELECT IDDOCUMENTO, IDSTATUS, ESTADO, VALIDO FROM ENOC.ARCH_DOCSTATUS WHERE ESTADO = 'A' AND VALIDO = 'S'";
			lista = enocJdbc.query(comando, new ArchDocStatusMapper());
			for(ArchDocStatus estado : lista){
				mapa.put(estado.getIdDocumento()+"-"+estado.getIdStatus(), estado);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchDocStatusDao|mapaEstadosValidos|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapEstadosUsados(){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT IDSTATUS AS LLAVE, COUNT(IDSTATUS) AS VALOR FROM ENOC.ARCH_DOCSTATUS GROUP BY IDSTATUS";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchDocStatusDao|mapEstadosUsados|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapEstadosValidos(){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT IDDOCUMENTO AS LLAVE, COUNT(*) AS VALOR FROM ENOC.ARCH_DOCSTATUS WHERE ESTADO = 'A' AND VALIDO = 'S' GROUP BY IDDOCUMENTO";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchDocStatusDao|mapEstadosValidos|:"+ex);
		}
		return mapa;
	}
	
}