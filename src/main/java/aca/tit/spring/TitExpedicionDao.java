package aca.tit.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TitExpedicionDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(TitExpedicion expedicion ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.TIT_EXPEDICION (FOLIO, FECHAEXPEDICION, MODALIDADID, MODALIDAD, FECHAEXAMEN,"
					+ " FECHAEXENCION, SERVICIO, FUNDAMENTOID, FUNDAMENTO, ENTIDADID, ENTIDAD)"
					+ " VALUES( ?,"
					+ " TO_DATE(?,'YYYY-MM-DD'), TO_NUMBER(?,'99'), ?, TO_DATE(?,'YYYY-MM-DD'), TO_DATE(?,'YYYY-MM-DD'), TO_NUMBER(?,'9'),"
					+ " TO_NUMBER(?,'99'), ?, ?, ?) ";
			
			Object[] parametros = new Object[] {
					expedicion.getFolio(), expedicion.getFechaExpedicion(),expedicion.getModalidadId(), expedicion.getModalidad(), 
					expedicion.getFechaExamen(),expedicion.getFechaExencion(),expedicion.getServicio(),expedicion.getFundamentoId(),
					expedicion.getFundamento(),expedicion.getEntidadId(),expedicion.getEntidad()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitExpedicionDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg(TitExpedicion expedicion) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.TIT_EXPEDICION "
					+ " SET FECHAEXPEDICION = TO_DATE(?,'YYYY-MM-DD'),"
					+ " MODALIDADID = TO_NUMBER(?,'99'),"
					+ " MODALIDAD = ?,"
					+ " FECHAEXAMEN = TO_DATE(?,'YYYY-MM-DD'),"
					+ " FECHAEXENCION = TO_DATE(?,'YYYY-MM-DD'),"
					+ " SERVICIO = TO_NUMBER(?,'9'),"
					+ " FUNDAMENTOID = TO_NUMBER(?,'99'),"
					+ " FUNDAMENTO = ?,"
					+ " ENTIDADID = ?,"
					+ " ENTIDAD = ?"
					+ " WHERE FOLIO = ?";
			
			Object[] parametros = new Object[] {
					expedicion.getFechaExpedicion(),expedicion.getModalidadId(),expedicion.getModalidad(),expedicion.getFechaExamen(),
					expedicion.getFechaExencion(),expedicion.getServicio(),expedicion.getFundamentoId(),expedicion.getFundamento(),
					expedicion.getEntidadId(),expedicion.getEntidad(),expedicion.getFolio()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitExpedicionDao|updateReg|:"+ex);		
		}
		return ok;
	}	
	
	public boolean deleteReg(String folio ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.TIT_EXPEDICION WHERE FOLIO = ?";
			
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitExpedicionDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public TitExpedicion mapeaRegId(String folio) {
		TitExpedicion expedicion = new TitExpedicion();
		 
		try{
			String comando = "SELECT FOLIO, TO_CHAR(FECHAEXPEDICION,'YYYY-MM-DD') AS FECHAEXPEDICION, MODALIDADID, MODALIDAD, "
					+ " TO_CHAR(FECHAEXAMEN,'YYYY-MM-DD') AS FECHAEXAMEN, TO_CHAR(FECHAEXENCION,'YYYY-MM-DD') AS FECHAEXENCION, SERVICIO,"
					+ " FUNDAMENTOID, FUNDAMENTO, ENTIDADID, ENTIDAD FROM ENOC.TIT_EXPEDICION "
					+ " WHERE FOLIO = ?";
			
			Object[] parametros = new Object[] {folio};
			expedicion = enocJdbc.queryForObject(comando, new TitExpedicionMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitExpedicionDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return expedicion;
		
	}	
	
	public boolean existeReg(String folio) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.TIT_EXPEDICION WHERE FOLIO = ?"; 
			
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitExpedicionDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public List<TitExpedicion> lisAll( String orden) {
		List<TitExpedicion> lista		= new ArrayList<TitExpedicion>();
		String comando		= "";
		
		try{
			comando = " SELECT FOLIO, TO_CHAR(FECHAEXPEDICION,'YYYY-MM-DD') AS FECHAEXPEDICION, MODALIDADID, MODALIDAD, "
					+ " TO_CHAR(FECHAEXAMEN,'YYYY-MM-DD') AS FECHAEXAMEN, TO_CHAR(FECHAEXENCION,'YYYY-MM-DD') AS FECHAEXENCION, SERVICIO,"
					+ " FUNDAMENTOID, FUNDAMENTO, ENTIDADID, ENTIDAD FROM ENOC.TIT_EXPEDICION "+orden;	 
			
			lista = enocJdbc.query(comando, new TitExpedicionMapper());	
			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitExpedicionDao|listAll|:"+ex);
		}
		return lista;
	}
	
	public List<TitExpedicion> lisExpedicion( String codigoPersonal, String orden) {
		List<TitExpedicion> lista		= new ArrayList<TitExpedicion>();
		String comando		= "";
		
		try{
			comando = " SELECT FOLIO, TO_CHAR(FECHAEXPEDICION,'YYYY-MM-DD') AS FECHAEXPEDICION, MODALIDADID, MODALIDAD,"
					+ " TO_CHAR(FECHAEXAMEN,'YYYY-MM-DD') AS FECHAEXAMEN, TO_CHAR(FECHAEXENCION,'YYYY-MM-DD') AS FECHAEXENCION, SERVICIO,"
					+ " FUNDAMENTOID, FUNDAMENTO, ENTIDADID, ENTIDAD FROM ENOC.TIT_EXPEDICION WHERE CODIGO_PERSONAL = ? "+orden;	 
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new TitExpedicionMapper(), parametros);	
			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitExpedicionDao|listAll|:"+ex);
		}
		return lista;
	}
	
	public String maximoReg() {
		String maximo 	= "1";
		
		try{
			String comando = "SELECT COALESCE(MAX(FOLIO)+1, 1) AS MAXIMO FROM ENOC.TIT_EXPEDICION"; 
					
			Object[] parametros = new Object[] {};
 			if (enocJdbc.queryForObject(comando, parametros, Integer.class) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class, parametros);
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitExpedicionDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public HashMap<String, TitExpedicion> mapaAll( ) {
		List<TitExpedicion> lista			= new ArrayList<TitExpedicion>();
		HashMap<String,TitExpedicion> mapa	= new HashMap<String,TitExpedicion>();	
		
		try{
			String comando	= "SELECT FOLIO, TO_CHAR(FECHAEXPEDICION,'YYYY-MM-DD') AS FECHAEXPEDICION, MODALIDADID, MODALIDAD,"
					+ " TO_CHAR(FECHAEXAMEN,'YYYY-MM-DD') AS FECHAEXAMEN, TO_CHAR(FECHAEXENCION,'YYYY-MM-DD') AS FECHAEXENCION, SERVICIO,"
					+ " FUNDAMENTOID, FUNDAMENTO, ENTIDADID, ENTIDAD FROM ENOC.TIT_EXPEDICION";							
			lista = enocJdbc.query(comando, new TitExpedicionMapper());
			
			for (TitExpedicion exp : lista){
				mapa.put(exp.getFolio(), exp);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitExpedicionDao|mapaAll|:"+ex);
		}
		return mapa;
	}
	
}