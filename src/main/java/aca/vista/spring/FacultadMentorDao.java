package aca.vista.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class FacultadMentorDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public FacultadMentor mapeaRegId( String idMentor) {
		FacultadMentor objeto = new FacultadMentor();
		
		try{
			String comando = "SELECT " +
				"ID_MENTOR, FACULTAD_ID, NOMBRE_FACULTAD "+
				"FROM FACULTAD_MENTOR " +
				"WHERE ID_MENTOR = ? ";
			Object[] parametros = new Object[] {idMentor};
			objeto = enocJdbc.queryForObject(comando, new FacultadMentorMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.vista.FacultadMentor|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return objeto;
	}
	
	public boolean existeReg(String idMentor) {
		boolean  ok				= false;
		
		try{
			String comando = "SELECT COUNT(*)" +
					"ID_MENTOR, FACULTAD_ID, NOMBRE_FACULTAD "+
					"FROM FACULTAD_MENTOR " +
					"WHERE ID_MENTOR = ? ";
					Object[] parametros = new Object[] {idMentor};
					if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
						ok = true;
					}
			
		}catch (Exception ex){
			System.out.println("Error - aca.vista.FacultadMentor|existeReg|:"+ex);
		}
		return ok;
	}
	
	public List<FacultadMentor> getListAll(String orden ) {		
		List<FacultadMentor> lista	= new ArrayList<FacultadMentor>();
		String comando	= "";
	
		try{
			comando = "SELECT ID_MENTOR, FACULTAD_ID, NOMBRE_FACULTAD FROM FACULTAD_MENTOR "+orden;
		
			lista = enocJdbc.query(comando, new FacultadMentorMapper());
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.FacultadMentorUtil|getListAll|:"+ex);
		}
		return lista;	
	}
}