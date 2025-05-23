// CLASE DE LA TABLA CONV_SOLICITUD
package aca.convenio.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ConConvenioDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(ConConvenio conv) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.CON_CONVENIO"
					+ " (ID, NOMBRE, FECHA_FIRMA, FECHA_VIGENCIA, PROGRAMA, OBJETIVO, ESTADO, TIPO_ID) "
					+ " VALUES(TO_NUMBER(?,'999'),?,TO_DATE(?,'YYYY/MM/DD'),TO_DATE(?,'YYYY/MM/DD'),?,?,?,TO_NUMBER(?,'99'))";			
			Object[] parametros = new Object[]{
				conv.getId(),conv.getNombre(),conv.getFechaFirma(),
				conv.getFechaVigencia(),conv.getPrograma(), conv.getObjetivo(),conv.getEstado(), conv.getTipoId()
			};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.convenio.spring.ConvConvenioDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg(ConConvenio conv ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.CON_CONVENIO "
					+ " SET "
					+ " NOMBRE = ?, "
					+ " FECHA_FIRMA = TO_DATE(?,'YYYY/MM/DD'), "
					+ " FECHA_VIGENCIA = TO_DATE(?,'YYYY/MM/DD'), "
					+ " PROGRAMA = ?,"
					+ " OBJETIVO = ?,"
					+ " ESTADO = ?,"
					+ " TIPO_ID = TO_NUMBER(?,'99')"					
					+ " WHERE ID = TO_NUMBER(?,'999') ";			
			Object[] parametros = new Object[] {conv.getNombre(),conv.getFechaFirma(),conv.getFechaVigencia(),conv.getPrograma(),conv.getObjetivo(),conv.getEstado(), conv.getTipoId(), conv.getId()};			
			if (enocJdbc.update(comando,parametros)==1){			
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.convenio.spring.ConvConvenioDao|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean deleteReg(String id ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CON_CONVENIO WHERE ID = TO_NUMBER(?,'999')";			
			Object[] parametros = new Object[] { id };			
			if (enocJdbc.update(comando,parametros)==1){			
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.convenio.spring.ConvConvenioDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public boolean existeReg( String id) {
		boolean ok 	= false;				
		try{
			String comando = "SELECT COUNT (*) FROM ENOC.CON_CONVENIO WHERE ID = ? ";			
			Object[] parametros = new Object[] {id};
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.convenio.spring.ConvConvenioDao|existeReg|:"+ex);
		}		
		return ok;
	}
	
	public ConConvenio mapeaRegId( String id ) {
		ConConvenio objeto = new ConConvenio();		
		try{
			String comando = "SELECT COUNT (*) FROM ENOC.CON_CONVENIO WHERE ID = ? ";			
			Object[] parametros = new Object[] {id};
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1){
				comando = "SELECT ID, NOMBRE, TO_CHAR(FECHA_FIRMA,'YYYY/MM/DD') AS FECHA_FIRMA, TO_CHAR(FECHA_VIGENCIA,'YYYY/MM/DD') AS FECHA_VIGENCIA, PROGRAMA, OBJETIVO, ESTADO, TIPO_ID"
						+ " FROM ENOC.CON_CONVENIO WHERE ID = TO_NUMBER(?,'999')";				
				objeto = enocJdbc.queryForObject(comando, new ConConvenioMapper(), parametros);
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.convenio.spring.ConvConvenioDao|mapeaRegId|:"+ex);
		}		
		return objeto;		
	}
	
	public String maximoReg( ) {
		int maximo = 1;		
		try{
			String comando = "SELECT COALESCE(MAX(ID)+1,1) FROM ENOC.CON_CONVENIO";
			maximo = enocJdbc.queryForObject(comando, Integer.class);		
		}catch(Exception ex){
			System.out.println("Error - aca.convenio.spring.ConvConvenioDao|mapeaRegId|:"+ex);
		}
		
		return String.valueOf(maximo);		
	}
	
	public List<ConConvenio> lisTodos( String orden ) {
		
		List<ConConvenio> lista	= new ArrayList<ConConvenio>();
		try{
			String comando = " SELECT ID, NOMBRE, TO_CHAR(FECHA_FIRMA,'YYYY/MM/DD') AS FECHA_FIRMA, TO_CHAR(FECHA_VIGENCIA,'YYYY/MM/DD') AS FECHA_VIGENCIA, PROGRAMA, OBJETIVO, ESTADO, TIPO_ID"
					+ " FROM ENOC.CON_CONVENIO "+orden;				
			lista = enocJdbc.query(comando, new ConConvenioMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.convenio.spring.ConvConvenioDao|lisTodos|:"+ex);
		}
		
		return lista;
	}
	
	public List<ConConvenio> lisPorEstado( String estado, String orden ) {		
		List<ConConvenio> lista	= new ArrayList<ConConvenio>();
		try{
			String comando = " SELECT ID, NOMBRE, TO_CHAR(FECHA_FIRMA,'YYYY/MM/DD') AS FECHA_FIRMA, TO_CHAR(FECHA_VIGENCIA,'YYYY/MM/DD') AS FECHA_VIGENCIA, PROGRAMA, OBJETIVO, ESTADO"
					+ " FROM ENOC.CON_CONVENIO"
					+ " WHERE ESTADO = ? "+orden;				
			lista = enocJdbc.query(comando, new ConConvenioMapper(), estado);			
		}catch(Exception ex){
			System.out.println("Error - aca.convenio.spring.ConvConvenioDao|lisPorEstado|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,String> mapaConvenioConTipo() {
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<String> lista 	= new ArrayList<String>();
		
		try{
			String comando = "SELECT DISTINCT(TIPO_ID) FROM ENOC.CON_CONVENIO"; 
			
			lista = enocJdbc.queryForList(comando, String.class);
			for(String tipo : lista){				
				mapa.put(tipo, tipo);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.convenio.spring.ConvConvenioDao|mapaConvenioConTipo|:"+ex);
		}
		
		return mapa;		
	}
}