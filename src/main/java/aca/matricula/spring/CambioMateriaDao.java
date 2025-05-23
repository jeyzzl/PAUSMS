package aca.matricula.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CambioMateriaDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(CambioMateria objeto){
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.CAMBIO_MATERIA"
					+ " (SOLICITUD_ID, CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, TIPO) "+
				"VALUES(TO_NUMBER(?,'99999'), ?, ?, ?, ? )";
			Object[] parametros = new Object[] { 
				objeto.getSolicitudId(), objeto.getCodigoPersonal(), objeto.getCursoCargaId(), objeto.getCursoId(), objeto.getTipo()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.CambioMateriaDao|insertReg|:"+ex);			
	
		}
		return ok;
	}	

	public CambioMateria mapeaRegId(String solicitudId, String cursoCargaId, String cursoId){
		CambioMateria objeto = new CambioMateria();		
		try{
			String comando = "SELECT SOLICITUD_ID, CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, TIPO"
					+ " FROM ENOC.CAMBIO_MATERIA"
					+ " WHERE SOLICITUD_ID = TO_NUMBER(?,'99999') AND CURSO_CARGA_ID = ? AND CURSO_ID = ?";
			Object[] parametros = new Object[] {solicitudId, cursoCargaId, cursoId};			
			objeto = enocJdbc.queryForObject(comando, new CambioMateriaMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.CambioMateriaDao|mapeaRegId|:"+ex);
		
		}
		return objeto;
	}	
	
	public boolean existeReg(String solicitudId, String cursoCargaId, String cursoId){
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAMBIO_MATERIA"
					+ " WHERE SOLICITUD_ID = TO_NUMBER(?,'99999') AND CURSO_CARGA_ID = ? AND CURSO_ID = ?";
			Object[] parametros = new Object[] {solicitudId, cursoCargaId, cursoId};		
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			} 
			
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.CambioMateriaDao|existeReg|:"+ex);

		}
		return ok;
	}
	
	public boolean tieneMaterias(String solicitudId){
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAMBIO_MATERIA WHERE SOLICITUD_ID = TO_NUMBER(?,'99999')";
			Object[] parametros = new Object[] {solicitudId};		
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.CambioMateriaDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public boolean deleteReg(String solicitudId, String cursoCargaId, String cursoId){
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.CAMBIO_MATERIA WHERE SOLICITUD_ID = TO_NUMBER(?,'99999') AND CURSO_CARGA_ID = ? AND CURSO_ID = ?";
			Object[] parametros = new Object[] {solicitudId,cursoCargaId,cursoId};
			if (enocJdbc.update(comando,parametros)==1){			
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.CambioMateriaDao|deleteReg|:"+ex);			
		}		
		return ok;
	}
	
	public boolean deletePorSolicitud(String solicitudId){
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.CAMBIO_MATERIA WHERE SOLICITUD_ID = TO_NUMBER(?,'99999')";
			Object[] parametros = new Object[] {solicitudId};
			if (enocJdbc.update(comando,parametros) >= 1){			
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.CambioMateriaDao|deletePorSolicitud|:"+ex);			
		}		
		return ok;
	}
	
	public List<CambioMateria> lisCambioMateria(String solicitudId, String orden ){
		List<CambioMateria> lista = new ArrayList<CambioMateria>();	
		try{
			String comando = "SELECT SOLICITUD_ID, CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, TIPO"
					+ " FROM ENOC.CAMBIO_MATERIA WHERE SOLICITUD_ID = TO_NUMBER(?,'99999') "+ orden;
			
			Object[] parametros = new Object[] {solicitudId};
			
			lista = enocJdbc.query(comando, new CambioMateriaMapper(), parametros);		
			
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.CambioMateriaDao|lisCambioMateria|:"+ex);
		}		
		return lista;
	}	

	public HashMap<String,CambioMateria> mapaCambioMateria(String solicitudId ){
		List<CambioMateria> lista			= new ArrayList<CambioMateria>();
		HashMap<String,CambioMateria> mapa 	= new HashMap<String,CambioMateria>();
		try{
			String comando = "SELECT SOLICITUD_ID, CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, TIPO"
					+ " FROM ENOC.CAMBIO_MATERIA WHERE SOLICITUD_ID = TO_NUMBER(?,'99999') ";
			Object[] parametros = new Object[] {solicitudId};
			lista = enocJdbc.query(comando, new CambioMateriaMapper(), parametros);			
			for(CambioMateria materia : lista) {
				mapa.put(materia.getCursoCargaId(), materia);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.CambioMateriaDao|mapaCambioMateria|:"+ex);
		}		
		return mapa;
	}	

}
