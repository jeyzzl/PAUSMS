package aca.musica.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MusiAutorizaDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(MusiAutoriza musiAutoriza) {
 		boolean ok = false; 		
 		try{
 			String comando = "INSERT INTO ENOC.MUSI_AUTORIZA(CODIGO_PERSONAL,CARGA_ID, FECHA, USUARIO) "+
 				"VALUES(?, ?, SYSDATE, ?)"; 			
 			Object[] parametros = new Object[] {
				musiAutoriza.getCodigoPersonal(),musiAutoriza.getCargaId(), musiAutoriza.getUsuario()
			};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.spring.MusiAutorizaDao|insertReg|:"+ex);			
 		} 		
 		return ok;
 	}	
 	
 	
 	public boolean deleteReg(String codigoPersonal, String cargaId) {
 		boolean ok = false; 		
 		try{
 			String comando = "DELETE FROM ENOC.MUSI_AUTORIZA WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ?"; 			
 			Object[] parametros = new Object[] {codigoPersonal,cargaId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.spring.MusiAutorizaDao|deleteReg|:"+ex);			
 		}
 		return ok;
 	}
 	
 	public MusiAutoriza mapeaRegId(String codigoPersonal, String cargaId) {
 		MusiAutoriza objeto = new MusiAutoriza(); 		
 		try{
	 		String comando = "SELECT CODIGO_PERSONAL,CARGA_ID,TO_CHAR(FECHA,'YYYY/MM/DD HH:MI:SS AM') AS FECHA, USUARIO FROM ENOC.MUSI_AUTORIZA"
	 				+ " WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ?";
	 		Object[] parametros = new Object[] {codigoPersonal,cargaId};
			objeto = enocJdbc.queryForObject(comando, new MusiAutorizaMapper(), parametros);
			
 		}catch(Exception ex){
			System.out.println("Error - aca.musica.spring.MusiAutorizaDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		} 		
 		return objeto;
 	}

 	public boolean existeReg(String codigoPersonal, String cargaId) {
 		boolean ok 	= false;
 		try{
 			String comando = "SELECT COUNT(*) FROM ENOC.MUSI_AUTORIZA WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ?";
 			Object[] parametros = new Object[] {codigoPersonal,cargaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			} 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.spring.MusiAutorizaDao|existeReg|:"+ex);
 		} 		
 		return ok;
 	} 	
 	
 	public List<MusiAutoriza> getListAll(String orden) {
		List<MusiAutoriza> lista	= new ArrayList<MusiAutoriza>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL,CARGA_ID,TO_CHAR(FECHA,'YYYY/MM/DD HH:MI:SS AM') AS FECHA, USUARIO FROM ENOC.MUSI_AUTORIZA "+orden;
			lista = enocJdbc.query(comando, new MusiAutorizaMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.spring.MusiAutorizaDao|getListAll|:"+ex);
		}		
		return lista;
	}
 	
 	public List<MusiAutoriza> lisPorCarga(String cargaId, String orden) {
		List<MusiAutoriza> lista	= new ArrayList<MusiAutoriza>();
		try{
			String comando = "SELECT CODIGO_PERSONAL,CARGA_ID,TO_CHAR(FECHA,'YYYY/MM/DD HH:MI:SS AM') AS FECHA, USUARIO"
					+ " FROM ENOC.MUSI_AUTORIZA"
					+ " WHERE CARGA_ID = ? "+orden;
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new MusiAutorizaMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.musica.spring.MusiAutorizaDao|lisPorCarga|:"+ex);
		}		
		return lista;
	}
 	
 	public HashMap<String, String> mapCargasAlumno(String codigoPersonal) {
		HashMap<String, String> mapa	= new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
				
		try{
			String comando = "SELECT CARGA_ID AS LLAVE, CARGA_ID AS VALOR "
					+ " FROM ENOC.MUSI_AUTORIZA WHERE CODIGO_PERSONAL = ?";
			
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa objeto : lista){				
				mapa.put(objeto.getLlave(), objeto.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.spring.MusiAutorizaDao|mapCargasAlumno|:"+ex);
		}
		
		return mapa;
	}

}
