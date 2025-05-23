package aca.por.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PorHoraDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( PorHora hora ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.POR_HORA"+ 
				"(FOLIO, DIA, SALON_ID, EQUIPO_ID, HORA, CODIGO_PERSONAL ) "+
				"VALUES( TO_NUMBER(?,'99999'), TO_NUMBER(?,'9'), TO_NUMBER(?,'99'), TO_NUMBER(?,'99'), ?, ? )";

			Object[] parametros = new Object[] {hora.getFolio(), hora.getDia(), hora.getSalonId(),
					hora.getEquipoId(), hora.getHora(), hora.getCodigoPersonal()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.por.spring.PorHoraDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg( PorHora hora) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.POR_HORA"
					+ " SET DIA = TO_NUMBER(?,'9'),"
					+ " SALON_ID = TO_NUMBER(?,'99'),"
					+ " EQUIPO_ID = TO_NUMBER(?,'99'),"
					+ " HORA = ?,"
					+ " CODIGO_PERSONAL = ?"					
					+ " WHERE FOLIO = TO_NUMBER(?,'99999')";
			
			Object[] parametros = new Object[] {hora.getDia(), hora.getSalonId(),
					hora.getEquipoId(), hora.getHora(), hora.getCodigoPersonal(), hora.getFolio()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.por.spring.PorHoraDao|updateReg|:"+ex);
		}
		return ok;
	}
	
	public boolean deleteReg( String folio) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.POR_HORA WHERE FOLIO = TO_NUMBER(?,'99999') ";
			
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.por.spring.PorHoraDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public PorHora mapeaRegId(  String folio) {
		PorHora hora = new PorHora();
		try{
			String comando = "SELECT FOLIO, DIA, SALON_ID, EQUIPO_ID, HORA, CODIGO_PERSONAL "+													
					" FROM ENOC.POR_HORA WHERE FOLIO = TO_NUMBER(?,'99999') ";
			
				Object[] parametros = new Object[] {folio};
				hora = enocJdbc.queryForObject(comando, new PorHoraMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.por.spring.PorHoraDao|mapeaRegId|:"+ex);
		}
		return hora;		
	}	
	
	public boolean existeReg( String folio) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.POR_HORA WHERE FOLIO = TO_NUMBER(?,'99999') ";
			
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.por.spring.PorHoraDao|existeReg|:"+ex);
		}
		return ok;
	}	
		
	public List<PorHora> lisEquipo( String equipo, String orden ) {
		List<PorHora> lista	= new ArrayList<PorHora>();
		String comando	= "";
		
		try{
			comando = "SELECT FOLIO, DIA, SALON_ID, EQUIPO_ID, HORA, CODIGO_PERSONAL FROM ENOC.POR_HORA WHERE EQUIPO_ID = TO_NUMBER(?,'99') "+orden; 
			Object[] parametros = new Object[] {equipo};
			lista = enocJdbc.query(comando, new PorHoraMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.por.spring.PorHoraDao|lisEquipo|:"+ex);
		}
		return lista;
	}
	
	public boolean tieneHorario( String codigoPersonal) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.POR_HORA WHERE CODIGO_PERSONAL = ? ";
			
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.por.spring.PorHoraDao|tieneHorario|:"+ex);
		}
		return ok;
	}
	
	public HashMap<String,PorHora> mapaHora() {	
		
		HashMap<String,PorHora> mapa = new HashMap<String,PorHora>();
		List<PorHora> lista		= new ArrayList<PorHora>();
		
		try{
			String comando = "SELECT FOLIO, DIA, SALON_ID, EQUIPO_ID, HORA, CODIGO_PERSONAL FROM ENOC.POR_HORA";			
			lista = enocJdbc.query(comando, new PorHoraMapper());
			for (PorHora hora : lista ) {
				mapa.put(hora.getCodigoPersonal(),hora);
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.por.spring.PorHoraDao|mapaHora|:"+ex);
			ex.printStackTrace();
		}
		
		return mapa;		
	}
	
}