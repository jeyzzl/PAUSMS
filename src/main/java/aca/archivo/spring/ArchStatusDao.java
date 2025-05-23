//Clase  para la tabla ARCH_STATUS
package aca.archivo.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ArchStatusDao {	 
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
     
     public boolean insertReg( ArchStatus status ) {
 		boolean ok = false;
 		
 		try{
 			String comando = "INSERT INTO ENOC.ARCH_STATUS(IDSTATUS, DESCRIPCION) VALUES(TO_NUMBER(?,'99'),?)";
 			
 			Object[] parametros = new Object[] {status.getIdStatus(), status.getDescripcion()};
 			
 			if (enocJdbc.update(comando,parametros)==1)
				ok = true;
			else
				ok = false;
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.archivo.spring.ArchStatusDao|insertReg|:"+ex);
 		}
 		
 		return ok;
 	}
 	
 	public boolean updateReg( ArchStatus status ){
 		boolean ok = false;
 		
 		try{
 			String comando = "UPDATE ENOC.ARCH_STATUS " + 
 			"SET DESCRIPCION = ? WHERE IDSTATUS = TO_NUMBER(?,'99')";
 			
 			Object[] parametros = new Object[] {status.getDescripcion(), status.getIdStatus()};
 		
 			if (enocJdbc.update(comando,parametros)==1)
				ok = true;
			else
				ok = false;
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.archivo.spring.ArchStatusDao|updateReg|:"+ex);
 		}
 		
 		return ok;
 	}
 	
 	public boolean deleteReg( String idStatus ){
 		boolean ok = false;
 		
 		try{
 			String comando = "DELETE FROM ENOC.ARCH_STATUS WHERE IDSTATUS = TO_NUMBER(?,'99')";
 			
 			Object[] parametros = new Object[] {idStatus};
			
			if (enocJdbc.update(comando,parametros)==1)
				ok = true;
			else
				ok = false;
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.archivo.spring.ArchStatusDao|deleteReg|:"+ex);
 		}
 		
 		return ok;
 	}
 	
 	public ArchStatus mapeaRegId(String IdStatus){
 		ArchStatus status = new ArchStatus();
 			
 		try{
 			String comando = "SELECT IDSTATUS,DESCRIPCION " +
 					"FROM ENOC.ARCH_STATUS WHERE IDSTATUS = TO_NUMBER(?,'99')";
 			
 			Object[] parametros = new Object[] {IdStatus};
 			status = enocJdbc.queryForObject(comando, new ArchStatusMapper(), parametros);
			
 		}catch(Exception ex){
 			System.out.println("Error - aca.archivo.spring.ArchStatusDao|mapeaRegId|:"+ex);
 		}
 		
 		return status;
 	}
 	
 	public boolean existeReg( String idStatus){
 		boolean ok 			= false;
 		
 		try{
 			String comando = "SELECT COUNT(*) FROM ENOC.ARCH_STATUS WHERE IDSTATUS = TO_NUMBER(?,'99')";
 			Object[] parametros = new Object[] {idStatus};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.archivo.spring.ArchStatusDao|existeReg|:"+ex);
 		}
 		
 		return ok;
 	}
 	
 	public String maximoReg(){
 		String maximo 			= "1";
 		
 		try{
 			String comando = "SELECT MAX(IDSTATUS)+1 MAXIMO FROM ENOC.ARCH_STATUS"; 
 			Object[] parametros = new Object[] {};
 			if (enocJdbc.queryForObject(comando,  Integer.class, parametros) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class, parametros);
			}	
 		
 		}catch(Exception ex){
 			System.out.println("Error - aca.archivo.spring.ArchStatusDao|maximoReg|:"+ex);
 		}
 		
 		return maximo;
 	}
 	
 	public String getDescripcion( String IdStatus){
 		
 		String descripcion		= ""; 		
 		try{
 			String comando = "SELECT COUNT(DESCRIPCION) FROM ENOC.ARCH_STATUS WHERE IDSTATUS = TO_NUMBER(?,'99')";
 			Object[] parametros = new Object[] {IdStatus};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>=1){
				comando = "SELECT DESCRIPCION FROM ENOC.ARCH_STATUS WHERE IDSTATUS = TO_NUMBER(?,'99')";
				descripcion = enocJdbc.queryForObject(comando,  String.class, parametros);
			} 	
 		}catch(Exception ex){
 			System.out.println("Error - aca.archivo.spring.ArchStatus|getDescripcion|:"+ex);
 		} 		
 		return descripcion;
 	} 

	public ArrayList<ArchStatus> getListAll( String orden ){
		
		List<ArchStatus> lista 	= new ArrayList<ArchStatus>();
		
		try{
			String comando = "SELECT IDSTATUS, DESCRIPCION " +					
					"FROM ENOC.ARCH_STATUS "+orden; 
			Object[] parametros = new Object[] {};
			lista = enocJdbc.query(comando, new ArchStatusMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchStatusDao|getListAll|:"+ex);
		}
		
		return (ArrayList<ArchStatus>)lista;
	}
	
	public List<String> getListStatusUsados( String orden){
		   List<String> lista 	= new ArrayList<String>();
		
		try{
			String comando = "SELECT DISTINCT(ENOC.ARCH_STATUS.IDSTATUS) FROM ENOC.ARCH_STATUS INNER JOIN ENOC.ARCH_DOCALUM ON ENOC.ARCH_STATUS.IDSTATUS=ENOC.ARCH_DOCALUM.IDSTATUS "+orden; 
			lista = enocJdbc.queryForList(comando, String.class);			
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchStatusDao|getListStatusUsados|:"+ex);
		}
		
		return lista;
	}
	
	public List<ArchStatus> getListNow( String IdDocumento, String orden ){		
		List<ArchStatus> lista 	= new ArrayList<ArchStatus>();		
		try{
			String comando = "SELECT IDSTATUS, DESCRIPCION FROM ENOC.ARCH_STATUS " + 
					"WHERE IDSTATUS IN(SELECT IDSTATUS FROM ENOC.ARCH_DOCSTATUS WHERE IDDOCUMENTO = ?) "+orden;
			lista = enocJdbc.query(comando, new ArchStatusMapper(), IdDocumento);			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchStatusDao|getListNow|:"+ex);
		}		
		return lista;
	}
	
	public List<ArchStatus> lisPorDocumentoyEstado( String idDocumento, String estado, String orden ){
		
		List<ArchStatus> lista 	= new ArrayList<ArchStatus>();		
		try{
			String comando 	= " SELECT IDSTATUS, DESCRIPCION FROM ENOC.ARCH_STATUS"
							+ " WHERE IDSTATUS IN(SELECT IDSTATUS FROM ENOC.ARCH_DOCSTATUS WHERE IDDOCUMENTO = ? AND ESTADO = ?) "+orden;
			Object[] parametros = new Object[] {idDocumento, estado};
			lista = enocJdbc.query(comando, new ArchStatusMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchStatusDao|getListNow|:"+ex);
		}		
		return lista;
	} 
	
	public List<ArchStatus> getListRest( String IdDocumento, String orden ){
		
		List<ArchStatus> lista	= new ArrayList<ArchStatus>();
		
		try{
			String comando = "SELECT IDSTATUS, DESCRIPCION FROM ENOC.ARCH_STATUS " + 
					"WHERE IDSTATUS NOT IN(SELECT IDSTATUS FROM ENOC.ARCH_DOCSTATUS WHERE IDDOCUMENTO = ?) " +orden;
			lista = enocJdbc.query(comando, new ArchStatusMapper(), IdDocumento);
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchStatusDao|getListRest|:"+ex);
		}
		
		return lista;
	}	
	
	public HashMap<String, String> mapaStatus() {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();
		String comando		= "";
		try{
			comando = "SELECT IDSTATUS AS LLAVE, DESCRIPCION AS VALOR FROM ENOC.ARCH_STATUS";	
			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchStatusDao|mapaStatus|:"+ex);
		}
		return mapa;
	}
}