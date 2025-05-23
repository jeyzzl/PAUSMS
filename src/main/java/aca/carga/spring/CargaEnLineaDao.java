package aca.carga.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CargaEnLineaDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	
	
	public boolean insertReg( CargaEnLinea linea ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CARGA_ENLINEA"
					+ " (CARGA_ID, NOMBRE, DESCRIPCION, F_INICIO, F_FINAL, ESTADO, CARTA) "
					+ " VALUES( ?, ?, ?, TO_DATE(?,'DD/MM/YYYY'), TO_DATE(?,'DD/MM/YYYY'), ?, ?)";	
			
			Object[] parametros = new Object[] {
					linea.getCargaId(), linea.getNombre(), linea.getDescripcion(), linea.getfInicio(), linea.getfFinal(), linea.getEstado(), linea.getCarta()
 		 	};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.cargaEnLineaDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg( CargaEnLinea linea ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CARGA_ENLINEA "
					+ " SET NOMBRE = ?, DESCRIPCION = ? ,"
					+ " F_INICIO = TO_DATE(?,'DD/MM/YYYY'), "
					+ " F_FINAL = TO_DATE(?,'DD/MM/YYYY'), "
					+ " ESTADO = ? ,"
					+ " CARTA = ? "
					+ " WHERE CARGA_ID = ? ";
			
			Object[] parametros = new Object[] {
					linea.getNombre(), linea.getDescripcion(), linea.getfInicio(), linea.getfFinal(), linea.getEstado(), linea.getCarta(), linea.getCargaId()
 		 	};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.cargaEnLineaDao|updateReg|:"+ex);		 
		}
		
		return ok;
	}	
	
	public boolean deleteReg( String cargaId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CARGA_ENLINEA "+ 
				"WHERE CARGA_ID = ? ";
			
			Object[] parametros = new Object[] {cargaId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.cargaEnLineaDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	

	public CargaEnLinea mapeaRegId(  String cargaId) {
		
		CargaEnLinea objeto = new CargaEnLinea();
		
		try{
			String comando = "SELECT "
					+ " CARGA_ID, NOMBRE, DESCRIPCION, "
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY')  AS F_INICIO, "
					+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL, "
					+ " ESTADO,"
					+ " CARTA "
					+ " FROM ENOC.CARGA_ENLINEA WHERE CARGA_ID = ? ";
			
			Object[] parametros = new Object[] {cargaId};
			objeto = enocJdbc.queryForObject(comando, new CargaEnLineaMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.cargaEnLineaDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public boolean existeReg( String cargaId) {
		boolean 			ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_ENLINEA "+ 
				"WHERE CARGA_ID = ? ";
			
			Object[] parametros = new Object[] {cargaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.cargaEnLineaDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public List<CargaEnLinea> getListAll( String orden ) {
		
		List<CargaEnLinea> lista	= new ArrayList<CargaEnLinea>();
		
		try{
			String comando = "SELECT CARGA_ID, NOMBRE, DESCRIPCION, TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO, "
					+ " TO_CHAR(F_FINAL, 'DD/MM/YYYY') AS F_FINAL, "
					+ " ESTADO, CARTA FROM ENOC.CARGA_ENLINEA "+orden; 
			
			lista = enocJdbc.query(comando, new CargaEnLineaMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.cargaEnLineaDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public List<CargaEnLinea> getListActivas( String orden ) {
		
		List<CargaEnLinea> lista		= new ArrayList<CargaEnLinea>();	
		try{
			String comando = 	" SELECT CARGA_ID, NOMBRE, DESCRIPCION, TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO,"
					+ " TO_CHAR(F_FINAL, 'DD/MM/YYYY') AS F_FINAL,"
					+ " ESTADO, CARTA FROM ENOC.CARGA_ENLINEA WHERE ESTADO = 'A' "+orden;			
			lista = enocJdbc.query(comando, new CargaEnLineaMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.cargaEnLineaDao|getListActivas|:"+ex);
		}		
		return lista;
	}

	public List<CargaEnLinea> getListCargasCartas(String carta, String orden) {
		
		List<CargaEnLinea> lista = new ArrayList<CargaEnLinea>();		
		try{
			String comando = "SELECT CARGA_ID, NOMBRE, DESCRIPCION, TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO,"
					+ " TO_CHAR(F_FINAL, 'DD/MM/YYYY') AS F_FINAL,"
					+ " ESTADO, CARTA FROM ENOC.CARGA_ENLINEA WHERE CARTA = ? "+orden;			
			lista = enocJdbc.query(comando, new CargaEnLineaMapper(),new Object[] {carta});			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.cargaEnLineaDao|getListCargasCartas|:"+ex);
		}		
		return lista;
	}

	public String cargasConCartas(String carta) {
		
		List<String> lista = new ArrayList<String>();		
		String cargas = "";		
		try{
			String comando = "SELECT CARGA_ID FROM ENOC.CARGA_ENLINEA WHERE CARTA = ?";			
			lista = enocJdbc.queryForList(comando,String.class,new Object[] {carta});			
			for(String carga : lista) {
				cargas += carga+",";
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.cargaEnLineaDao|cargasConCartas|:"+ex);
		}
		
		return cargas;
	}
	
	public String cargasActivas(String estado) {
		
		List<String> lista = new ArrayList<String>();		
		String cargas = "";		
		try{
			String comando = "SELECT CARGA_ID FROM ENOC.CARGA_ENLINEA WHERE ESTADO = ?";	
			lista = enocJdbc.queryForList(comando,String.class,new Object[] {estado});
			for(String carga : lista) {
				cargas += carga+",";
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.cargaEnLineaDao|cargasConCartas|:"+ex);
		}
		
		return cargas;
	}
}
