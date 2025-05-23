package aca.alumno.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AlumBecaDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AlumBeca objeto ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.ALUM_BECA"
					+ " (CODIGO_PERSONAL, BECA)"
					+ " VALUES( ?, ?)";
			Object[] parametros = new Object[] {objeto.getCodigoPersonal(),objeto.getBeca()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumBecaDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg(AlumBeca objeto ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.ALUM_BECA"
					+ " SET BECA = ?"
					+ " WHERE CODIGO_PERSONAL = ? ";
			Object[] parametros = new Object[] {objeto.getBeca(), objeto.getCodigoPersonal()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumBecaDao|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean deleteReg(String codigoPersonal ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.ALUM_BECA"
					+ " WHERE CODIGO_PERSONAL = ? ";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumBecaDao|deletetReg|:"+ex);			
		}
		
		return ok;
	}
	
	public AlumBeca mapeaRegId(String codigoPersonal) {
		AlumBeca objeto = new AlumBeca();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, BECA"
					+ " FROM ENOC.ALUM_BECA"
					+ " WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			objeto = enocJdbc.queryForObject(comando, new AlumBecaMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumBecaDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public boolean existeReg(String codigoPersonal) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_BECA"
					+ " WHERE CODIGO_PERSONAL = ? ";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumBecaDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public List<AlumBeca> getListAll(String orden ) {
		
		List<AlumBeca> lista = new ArrayList<AlumBeca>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, BECA"
					+ " FROM ENOC.ALUM_BECA "+orden;
			lista = enocJdbc.query(comando, new AlumBecaMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumBecaDao|getListAll|:"+ex);
		}		
		return lista;
	}
	
	public HashMap<String, AlumBeca> mapaAlumBeca() {
		HashMap<String,AlumBeca> mapa	= new HashMap<String, AlumBeca>();
		List<AlumBeca>	lista		= new ArrayList<AlumBeca>();		
		try{
			String comando ="SELECT CODIGO_PERSONAL, BECA"
					+ " FROM ENOC.ALUM_BECA ";
			lista = enocJdbc.query(comando, new AlumBecaMapper());
			for (AlumBeca objeto : lista){
				mapa.put(objeto.getCodigoPersonal(), objeto);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumBecaDao|mapaAlumBeca|:"+ex);
		}		
		return mapa;
	}
	
}
