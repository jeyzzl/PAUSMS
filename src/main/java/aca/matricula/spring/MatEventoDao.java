package aca.matricula.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MatEventoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(MatEvento matEvento){
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.MAT_EVENTO"+ 
				"(EVENTO_ID, CARGA_ID, EVENTO_NOMBRE, ESTADO) "+
				"VALUES( TO_NUMBER(?,'99'), ?, ?, ?)";
			Object[] parametros = new Object[] { matEvento.getEventoId(), matEvento.getCargaId(), matEvento.getEventoNombre(), matEvento.getEstado()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.MateEventoDao|insertReg|:"+ex);			
	
		}
		return ok;
	}	

	public boolean updateReg(MatEvento evento) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.MAT_EVENTO SET EVENTO_NOMBRE = ?, ESTADO = ?, CARGA_ID = ? WHERE EVENTO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {evento.getEventoNombre(), evento.getEstado(), evento.getCargaId(), evento.getEventoId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.MateEventoDao|updateReg|:"+ex);		 
		}
		
		return ok;
	}
		
	public boolean deleteReg(String eventoId){
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.MAT_EVENTO WHERE EVENTO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {eventoId};
 			if (enocJdbc.update(comando, parametros)==1){
 				ok = true;
 			}		
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.MateEventoDao|deleteReg|:"+ex);
		}
		return ok;
	}
	
	public MatEvento mapeaRegId(String eventoId){
		MatEvento matEvento = new MatEvento();		
		try{
			String comando = "SELECT EVENTO_ID, CARGA_ID, EVENTO_NOMBRE, ESTADO"
					+ " FROM ENOC.MAT_EVENTO"
					+ " WHERE EVENTO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {eventoId};			
			matEvento = enocJdbc.queryForObject(comando, new MatEventoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.MateEventoDao|mapeaRegId|:"+ex);
		
		}
		return matEvento;
	}	

	public MatEvento mapeaRegIdCarga(String cargaId){
		MatEvento matEvento = new MatEvento();		
		try{
			String comando = "SELECT EVENTO_ID, CARGA_ID, EVENTO_NOMBRE, ESTADO"
					+ " FROM ENOC.MAT_EVENTO"
					+ " WHERE CARGA_ID = ?";
			Object[] parametros = new Object[] {cargaId};			
			matEvento = enocJdbc.queryForObject(comando, new MatEventoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.MateEventoDao|mapeaRegIdCarga|:"+ex);
		
		}
		return matEvento;
	}

	public MatEvento mapeaRegId(String eventoId, String cargaId){
		MatEvento matEvento = new MatEvento();		
		try{
			String comando = "SELECT EVENTO_ID, CARGA_ID, EVENTO_NOMBRE, ESTADO"
					+ " FROM ENOC.MAT_EVENTO"
					+ " WHERE EVENTO_ID = TO_NUMBER(?,'99') AND CARGA_ID = ?";
			Object[] parametros = new Object[] {eventoId, cargaId};			
			matEvento = enocJdbc.queryForObject(comando, new MatEventoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.MateEventoDao|mapeaRegId|:"+ex);
		
		}
		return matEvento;
	}	

	public String maximoReg() {
		String maximo = "1";		
		try{
			String comando = "SELECT COALESCE(MAX(EVENTO_ID)+1,1) FROM ENOC.MAT_EVENTO";
			maximo = enocJdbc.queryForObject(comando, String.class);			
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.MateEventoDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public boolean existeReg(String eventoId){
		boolean 			ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MAT_EVENTO "+ 
				"WHERE EVENTO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {eventoId};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			} 
			
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.MateEventoDao|existeReg|:"+ex);

		}
		return ok;
	}

	public boolean existeReg(String eventoId, String cargaId){
		boolean 			ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MAT_EVENTO "+ 
				"WHERE EVENTO_ID = TO_NUMBER(?,'99') AND CARGA_ID = ?";
			Object[] parametros = new Object[] {eventoId, cargaId};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			} 
			
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.MateEventoDao|existeReg|:"+ex);

		}
		return ok;
	}

	public boolean existeRegCarga(String cargaId){
		boolean 			ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MAT_EVENTO "+ 
				"WHERE CARGA_ID = ?";
			Object[] parametros = new Object[] {cargaId};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			} 
			
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.MateEventoDao|existeRegCarga|:"+ex);

		}
		return ok;
	}

	public List<MatEvento> lisTodos(String orden) {
		
		List<MatEvento> lista= new ArrayList<MatEvento>();
		try{
			String comando = "SELECT EVENTO_ID, CARGA_ID, EVENTO_NOMBRE, ESTADO FROM ENOC.MAT_EVENTO "+ orden;
			lista = enocJdbc.query(comando, new MatEventoMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.MateEventoDao|lisTodos|:"+ex);
		}
		
		return lista;
	}
	
	public List<MatEvento> lisMatEvento(String estado, String orden) {
		
		List<MatEvento> lista= new ArrayList<MatEvento>();
		try{
			String comando = "SELECT EVENTO_ID, CARGA_ID, EVENTO_NOMBRE, ESTADO FROM ENOC.MAT_EVENTO WHERE ESTADO = ? "+ orden;
			Object[] parametros = new Object[] {estado};
			lista = enocJdbc.query(comando, new MatEventoMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.MateEventoDao|lisMatEvento|:"+ex);
		}
		
		return lista;
	}	

	public HashMap<String, String> mapAlumnosEnEvento() {
		
		List<aca.Mapa> list 			= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String,String>();
		String comando = "";
		
		try{			
			comando = "SELECT EVENTO_ID AS LLAVE,(SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.MAT_ALUMNO WHERE EVENTO_ID = MAT_EVENTO.EVENTO_ID) AS VALOR FROM ENOC.MAT_EVENTO";
			list = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa obj : list){
				mapa.put(obj.getLlave(), obj.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.MateEventoDao|mapAlumnosEnEvento|:"+ex);
		}
		return mapa;
	}
}