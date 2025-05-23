// Clase Util para la tabla de Carga
package aca.carga.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CargaGrupoHorarioDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CargaGrupoHorario horario ){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CARGA_GRUPO_HORARIO"+ 
				"(CURSO_CARGA_ID, SALON_ID, HORARIO, TIPO, VALIDA_CRUCE ) "+
				"VALUES( ?, ?, ?, ?, ?)";
			Object[] parametros = new Object[] {horario.getCursoCargaId(),horario.getSalonId(),horario.getHorario(), horario.getTipo(),horario.getValidaCruce()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoHorarioDao|insertReg|:"+ex);			
		
		}
		
		return ok;
	}	
	
	public boolean updateReg( CargaGrupoHorario horario ){
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CARGA_GRUPO_HORARIO "+				 
				"SET HORARIO = ?, "+
				"TIPO = ?, "+
				"VALIDA_CRUCE = ? "+				
				"WHERE CURSO_CARGA_ID = ? "+
				"AND SALON_ID = ?";		
			Object[] parametros = new Object[] {horario.getHorario(),horario.getTipo(),horario.getValidaCruce(), horario.getCursoCargaId(), horario.getSalonId()}; 			 			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoHorarioDao|updateReg|:"+ex);		 
		
		}
		
		return ok;
	}	
	
	public boolean deleteReg( String cursoCargaId, String salonId){
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CARGA_GRUPO_HORARIO "+ 
				"WHERE CURSO_CARGA_ID = ? "+
				"AND SALON_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId,salonId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoHorarioDao|deleteReg|:"+ex);			
		
		}
		return ok;
	}
	
	public boolean deleteReg( String cursoCargaId ){
		boolean ok = false;

		try{
			String comando = "DELETE FROM ENOC.CARGA_GRUPO_HORARIO "+ 
				"WHERE CURSO_CARGA_ID = ? ";
			Object[] parametros = new Object[] {cursoCargaId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoHorarioDao|deleteReg|:"+ex);			
		
		}
		return ok;
	}
	
	public CargaGrupoHorario mapeaRegId(  String cursoCargaId, String salonId ){
		
		CargaGrupoHorario hora = new CargaGrupoHorario();
		
		try{
			String comando = "SELECT "+
				"CURSO_CARGA_ID, SALON_ID, HORARIO, TIPO, VALIDA_CRUCE "+
				"FROM ENOC.CARGA_GRUPO_HORARIO "+ 
				"WHERE CURSO_CARGA_ID = ? "+
				"AND SALON_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId,salonId};
			hora = enocJdbc.queryForObject(comando, new CargaGrupoHorarioMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoHorarioDao|mapeaRegId|:"+ex);
		
		}
		
		return hora;
	}
	
	public CargaGrupoHorario mapeaRegCurso(  String cursoCargaId ){
		
		CargaGrupoHorario hora = new CargaGrupoHorario();
		
		try{
			String comando = "SELECT "+
				"CURSO_CARGA_ID, SALON_ID, HORARIO, TIPO, VALIDA_CRUCE "+
				"FROM ENOC.CARGA_GRUPO_HORARIO "+ 
				"WHERE CURSO_CARGA_ID = ? ";
			Object[] parametros = new Object[] {cursoCargaId};
			hora = enocJdbc.queryForObject(comando, new CargaGrupoHorarioMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoHorarioDao|mapeaRegId|:"+ex);
		
		}
		
		return hora;
	}
	
	public boolean existeReg( String cursoCargaId, String salonId){
		boolean 			ok 	= false;
		
		try{
			String comando = "SELECT COUNT (*) FROM ENOC.CARGA_GRUPO_HORARIO "+ 
				"WHERE CURSO_CARGA_ID = ? "+
				"AND SALON_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId,salonId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoHorarioDao|existeReg|:"+ex);
		
		}
		
		return ok;
	}
	
	public boolean existeSalon( String salonId){
		boolean 			ok 	= false;
		
		try{
			String comando = "SELECT COUNT (*) FROM ENOC.CARGA_GRUPO_HORARIO "+ 
				"WHERE SALON_ID = ? ";
			Object[] parametros = new Object[] {salonId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoHorarioDao|existeSalon|:"+ex);
		
		}
		
		return ok;
	}
		
	public List<CargaGrupoHorario> getListAll( String orden ){
		
		List<CargaGrupoHorario> lista	= new ArrayList<CargaGrupoHorario>();
		
		try{
			String comando = "SELECT CURSO_CARGA_ID, SALON_ID, HORARIO, TIPO, VALIDA_CRUCE "+
					"FROM ENOC.CARGA_GRUPO_HORARIO "+ orden; 
			Object[] parametros = new Object[] {orden};	
			lista = enocJdbc.query(comando, new CargaGrupoHorarioMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.HorarioDao|getListAll|:"+ex);
		
		}
		
		return lista;
	}
	
	public List<CargaGrupoHorario> getLista( String cursoCargaId, String orden ){
		
		List<CargaGrupoHorario> lista	= new ArrayList<CargaGrupoHorario>();
		
		try{
			String comando = "SELECT "+
			"CURSO_CARGA_ID, SALON_ID, HORARIO, TIPO, VALIDA_CRUCE "+
			"FROM ENOC.CARGA_GRUPO_HORARIO "+ 
			"WHERE CURSO_CARGA_ID = ? "+ orden;
			Object[] parametros = new Object[] {cursoCargaId};	
			lista = enocJdbc.query(comando, new CargaGrupoHorarioMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.HorarioDao|getLista|:"+ex);
		
		}
		
		return lista;
	}
	
	public List<CargaGrupoHorario> getListaSalonesCargaId( String cursoCargaId, String salon, String orden ){
		
		List<CargaGrupoHorario> lista	= new ArrayList<CargaGrupoHorario>();		
		try{
			String comando = "SELECT * FROM ENOC.CARGA_GRUPO_HORARIO WHERE SUBSTR(CURSO_CARGA_ID, 0, 6) = ? AND SALON_ID = ? "+ orden;
			Object[] parametros = new Object[] {cursoCargaId.substring(0, 6),salon};	
			lista = enocJdbc.query(comando, new CargaGrupoHorarioMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.HorarioDao|getListaSalonesCargaId|:"+ex);		
		}		
		return lista;
	}
	public List<CargaGrupoHorario> getListaSalonesCargaIdBloque( String cursoCargaId, String bloqueId, String salon, String orden ){
		
		List<CargaGrupoHorario> lista	= new ArrayList<CargaGrupoHorario>();		
		try{
			String comando = " SELECT * FROM ENOC.CARGA_GRUPO_HORARIO "
					+ " WHERE SUBSTR(CURSO_CARGA_ID, 0, 6) = ? AND SALON_ID = ? "+
					  " AND GRUPO_BLOQUE(CURSO_CARGA_ID) = ? "+ orden;
			Object[] parametros = new Object[] {cursoCargaId.substring(0, 6), salon, bloqueId};	
			lista = enocJdbc.query(comando, new CargaGrupoHorarioMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.HorarioDao|getListaSalonesCargaId|:"+ex);		
		}		
		return lista;
	}
}