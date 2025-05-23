package aca.leg.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class LegPermisosDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(LegPermisos leg) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.LEG_PERMISOS(CODIGO, USUARIO_ALTA, USUARIO_BAJA, FECHA_INI, FECHA_LIM, STATUS, FOLIO)"
					+ " VALUES(?, ?, ?, TO_DATE(?,'DD/MM/YYYY'), TO_DATE(?,'DD/MM/YYYY'), ?, ?)";
			Object[] parametros = new Object[] {
				leg.getCodigo(), leg.getUsuarioAlta(), leg.getUsuarioBaja(), leg.getFechaIni(),
				leg.getFechaLim(), leg.getStatus(), leg.getFolio()
			};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.leg.spring.LegPermisosDao|insertReg|:"+ex);		
		}
		return ok;
	}	
	
	public boolean updateReg(LegPermisos leg) {
		boolean ok = false;
		try{
			String comando = "UPDATE ENOC.LEG_PERMISOS "
					+ " SET FECHA_INI = TO_DATE(?,'DD/MM/YYYY'), "
					+ " FECHA_LIM = TO_DATE(?,'DD/MM/YYYY'),"
					+ " USUARIO_ALTA = ?,"
					+ " USUARIO_BAJA = ?,"
					+ " STATUS = ? "
					+ " WHERE CODIGO = ? AND FOLIO = ?";
			Object[] parametros = new Object[] {leg.getFechaIni(),leg.getFechaLim(),
					leg.getUsuarioAlta(),leg.getUsuarioBaja(),leg.getStatus(),leg.getCodigo(), leg.getFolio()
			};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.spring.LegPermisosDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	public boolean deleteReg(String codigo, String folio) {
		boolean ok = false;
		try{
			String comando = "DELETE FROM ENOC.LEG_PERMISOS WHERE CODIGO = ? AND FOLIO = ?"; 
			Object[] parametros = new Object[] {codigo,folio};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.leg.spring.LegPermisosDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public LegPermisos mapeaRegId(String codigo, String folio)	{
		LegPermisos legPer = new LegPermisos();
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.LEG_PERMISOS WHERE CODIGO = ? AND FOLIO = ?"; 
			Object[] parametros = new Object[] {codigo,folio};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = " SELECT CODIGO, USUARIO_ALTA, USUARIO_BAJA, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_LIM,'DD/MM/YYYY') AS FECHA_LIM, STATUS, FOLIO " + 
						"FROM ENOC.LEG_PERMISOS WHERE CODIGO = ? AND FOLIO = ?";
				legPer = enocJdbc.queryForObject(comando, new LegPermisosMapper(), parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.spring.LegPermisosDao|mapeaRegId|:"+ex);
		}
		return legPer;
	}
	
	public boolean existeReg(String codigo, String folio) {
		boolean ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.LEG_PERMISOS "
					+ " WHERE CODIGO = ? AND FOLIO = TO_NUMBER(?,'99')"; 
			Object[] parametros = new Object[] {codigo,folio};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.leg.spring.LegPermisosDao|existeReg|:"+ex);
		}	
		return ok;
	}
	
	public boolean tienePermiso(String codigo, String fecha, String estado){
		boolean ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.LEG_PERMISOS WHERE CODIGO = ? AND TO_DATE(?,'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_LIM AND STATUS=?";
			Object[] parametros = new Object[] {codigo,fecha, estado};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.leg.spring.LegPermisosDao|tienePermiso|:"+ex);
		}	
		return ok;
	}	
	
 	public String maximoReg(String codigo) {
		String maximo	= "";
		
		try{
			String comando = "SELECT COALESCE(MAX(FOLIO)+1,1) AS MAXIMO FROM ENOC.LEG_PERMISOS WHERE CODIGO = ?";
			Object[] parametros = new Object[] {codigo};
			maximo = enocJdbc.queryForObject(comando,String.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.spring.LegPermisosDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public List<LegPermisos> getLista(String codigoPersonal, String orden) {
		List<LegPermisos> lista 	= new ArrayList<LegPermisos>();		
		try{
			String comando = "SELECT CODIGO, USUARIO_ALTA, USUARIO_BAJA, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_LIM,'DD/MM/YYYY') AS FECHA_LIM, STATUS, FOLIO" +
					" FROM ENOC.LEG_PERMISOS" + 
					" WHERE CODIGO = ? "+orden;			
			lista = enocJdbc.query(comando, new LegPermisosMapper(), codigoPersonal);			
		}catch (Exception ex){
			System.out.println("Error - aca.leg.spring.LegPermisosDao|getLista|:"+ex);
		}		
		return lista;
	}
	
	public HashMap<String, String> mapNombrePermiso() {
		
		HashMap<String, String> mapa	= new HashMap<String, String>();	
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		
		try{			
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, "
					+ " NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR "
					+ " FROM MAESTROS WHERE CODIGO_PERSONAL IN (SELECT USUARIO_ALTA FROM LEG_PERMISOS)"
					+ " OR CODIGO_PERSONAL IN (SELECT USUARIO_BAJA FROM LEG_PERMISOS)";
			lista = enocJdbc.query(comando, new aca.MapaMapper());		
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.spring.LegPermisosDao|mapNombrePermiso|:"+ex);
		}
		
		return mapa;
	}
	
}