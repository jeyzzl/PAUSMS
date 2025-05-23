package aca.investiga.spring;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import aca.investiga.spring.InvBitacoraDao;

@Component
public class InvBitacoraDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	
	
	public boolean insertReg(InvBitacora InvBitacora) throws SQLException{
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.INV_BITACORA(PROYECTO_ID, FOLIO, FECHA, USUARIO, ESTADO_OLD, ESTADO_NEW) "
					+ "VALUES(?, TO_NUMBER(?,'999'), TO_DATE(?, 'DD/MM/YYYY HH24:MI:SS'), ?, ?, ?)";
			
			Object[] parametros = new Object[] {
				InvBitacora.getProyectoId(),InvBitacora.getFolio(),InvBitacora.getFecha(),InvBitacora.getUsuario(),
				InvBitacora.getEstadoOld(),InvBitacora.getEstadoNew()
			};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvBitacoraDao|insertReg|:"+ex);
		}

		return ok;
	}
	
	public InvBitacora mapeaRegId(String proyectoId, String folio) throws SQLException{		
		InvBitacora objeto = new InvBitacora();
		
		try{ 
			String comando = "SELECT PROYECTO_ID, FOLIO, TO_DATE(?, 'DD/MM/YYYY HH24:MI:SS') AS FECHA, USUARIO, ESTADO_OLD, ESTADO_NEW"
					+ " FROM ENOC.INV_BITACORA"
					+ " WHERE PROYECTO_ID = ? AND FOLIO = TO_NUMBER(?,'999')"; 
			
			Object[] parametros = new Object[] { proyectoId, folio };
			objeto = enocJdbc.queryForObject(comando,new InvBitacoraMapper(),parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvBitacoraDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public boolean existeReg(String proyectoId, String folio) throws SQLException{
		boolean ok 	= false;
		
		try{
			String comando = "SELECT * FROM ENOC.INV_BITACORA WHERE PROYECTO_ID = ? AND FOLIO = TO_NUMBER(?,'999') "; 
			
			Object[] parametros = new Object[] {proyectoId, folio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvBitacoraDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg(String proyectoId) throws SQLException{
		String maximo = "1";
		
		try{
			String comando = "SELECT COALESCE(MAX(FOLIO)+1,1) AS MAXIMO FROM ENOC.INV_BITACORA WHERE PROYECTO_ID = ?"; 
			
			Object[] parametros = new Object[] {proyectoId};
			maximo =  enocJdbc.queryForObject(comando,String.class,parametros);
			
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvBitacoraDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public String nombreEstado(String proyectoId) throws SQLException{
		String estado = "-";

		try{
			String comando = "SELECT ESTADO_NEW FROM ENOC.INV_BITACORA WHERE PROYECTO_ID = ?"; 
			
			Object[] parametros = new Object[] {proyectoId};
			estado =  enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvBitacoraDao|nombreEstado|:"+ex);
		}
		
		return estado;
	}

	public List<InvBitacora> getListAll(String orden ) throws SQLException{
		List<InvBitacora> lista	= new ArrayList<InvBitacora>();
		
		try{
			String comando = "SELECT PROYECTO_ID, FOLIO, TO_CHAR(FECHA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA, USUARIO, ESTADO_OLD, ESTADO_NEW FROM ENOC.INV_BITACORA "+orden; 
			
			lista = enocJdbc.query(comando, new InvBitacoraMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvBitacoraDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public List<InvBitacora> getListProyecto(String proyectoId, String orden ) throws SQLException{
		List<InvBitacora> lista	= new ArrayList<InvBitacora>();
		
		try{
			String comando = " SELECT PROYECTO_ID, FOLIO, TO_CHAR(FECHA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA, USUARIO, ESTADO_OLD, ESTADO_NEW"
					+ " FROM ENOC.INV_BITACORA"
					+ " WHERE PROYECTO_ID = ? "+orden;			
			lista = enocJdbc.query(comando, new InvBitacoraMapper(), proyectoId);			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvBitacoraDao|getListProyecto|:"+ex);
		}
		
		return lista;
	}

}
