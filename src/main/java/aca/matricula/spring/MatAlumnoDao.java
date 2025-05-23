package aca.matricula.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MatAlumnoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(MatAlumno matAlumno){
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.MAT_ALUMNO"+ 
				"(EVENTO_ID, CODIGO_PERSONAL, PLAN_ID, FECHA, USUARIO, MODO, ESTADO) "+
				"VALUES( TO_NUMBER(?,'99'), ?, ?, TO_DATE(?,'DD/MM/YYYY'), ?, ?, ? )";
			Object[] parametros = new Object[] { matAlumno.getEventoId(), matAlumno.getCodigoPersonal(), matAlumno.getPlanId(), matAlumno.getFecha(), matAlumno.getUsuario(), matAlumno.getModo(), matAlumno.getEstado() };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.MatAlumnoDao|insertReg|:"+ex);			
	
		}
		return ok;
	}	

	public boolean updateReg(MatAlumno matAlumno){
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.MAT_ALUMNO SET FECHA = TO_DATE(?,'DD/MM/YYYY'), USUARIO = ?, MODO = ?"
					+ " WHERE EVENTO_ID = TO_NUMBER(?,'99') AND CODIGO_PERSONAL = ? AND PLAN_ID = ?";							
			Object[] parametros = new Object[] { matAlumno.getFecha(),matAlumno.getUsuario(),matAlumno.getModo(), matAlumno.getEventoId(), matAlumno.getCodigoPersonal(), matAlumno.getPlanId() };	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.MatAlumnoDao|updateReg|:"+ex);	
		}
		return ok;
	}
	
	public boolean updateEstado(String eventoId, String codigoPersonal, String estado, String planId){
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.MAT_ALUMNO SET ESTADO = ?"
					+ " WHERE EVENTO_ID = TO_NUMBER(?,'99') AND CODIGO_PERSONAL = ? AND PLAN_ID = ?";							
			Object[] parametros = new Object[] { estado, eventoId, codigoPersonal, planId};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.MatAlumnoDao|updateEstado|:"+ex);			
		}
		return ok;
	}
	
	public boolean updateModo(String eventoId, String codigoPersonal, String modo, String planId){
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.MAT_ALUMNO SET MODO = ?"
					+ " WHERE EVENTO_ID = TO_NUMBER(?,'99') AND CODIGO_PERSONAL = ? AND PLAN_ID = ?";							
			Object[] parametros = new Object[] { modo, eventoId, codigoPersonal, planId};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.MatAlumnoDao|updateModo|:"+ex);
			
		}
		return ok;
	}
		
	public boolean deleteReg(String eventoId, String codigoPersonal, String planId ){
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.MAT_ALUMNO WHERE EVENTO_ID = TO_NUMBER(?,'99') AND CODIGO_PERSONAL = ? AND PLAN_ID = ?";
			Object[] parametros = new Object[] {eventoId, codigoPersonal, planId};
 			if (enocJdbc.update(comando, parametros)==1){
 				ok = true;
 			}		
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.MatAlumnoDao|deleteReg|:"+ex);
		}
		return ok;
	}
	
	public MatAlumno mapeaRegId(String  eventoId, String codigoPersonal, String planId ){
		MatAlumno matAlumno = new MatAlumno();		
		try{
			String comando = "SELECT EVENTO_ID, CODIGO_PERSONAL, PLAN_ID, FECHA, USUARIO, MODO, ESTADO"
					+ " FROM ENOC.MAT_ALUMNO"
					+ " WHERE EVENTO_ID = TO_NUMBER(?,'99') AND CODIGO_PERSONAL = ? AND PLAN_ID = ?";
			Object[] parametros = new Object[] {eventoId, codigoPersonal, planId};			
			matAlumno = enocJdbc.queryForObject(comando, new MatAlumnoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.MatAlumnoDao|mapeaRegId|:"+ex);
		
		}
		return matAlumno;
	}	

	public MatAlumno mapeaRegIdPorEstado(String  estado, String codigoPersonal, String planId ){
		MatAlumno matAlumno = new MatAlumno();		
		try{
			String comando = "SELECT EVENTO_ID, CODIGO_PERSONAL, PLAN_ID, FECHA, USUARIO, MODO, ESTADO"
					+ " FROM ENOC.MAT_ALUMNO"
					+ " WHERE ESTADO = ? AND CODIGO_PERSONAL = ? AND PLAN_ID = ?";
			Object[] parametros = new Object[] {estado, codigoPersonal, planId};			
			matAlumno = enocJdbc.queryForObject(comando, new MatAlumnoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.MatAlumnoDao|mapeaRegId|:"+ex);
		
		}
		return matAlumno;
	}

	public MatAlumno mapeaRegIdPorCarga(String  cargaId, String codigoPersonal, String planId ){
		MatAlumno matAlumno = new MatAlumno();		
		try{
			String comando = "SELECT EVENTO_ID, CODIGO_PERSONAL, PLAN_ID, FECHA, USUARIO, MODO, ESTADO"
					+ " FROM ENOC.MAT_ALUMNO"
					+ " WHERE CARGA_ID = ? AND CODIGO_PERSONAL = ? AND PLAN_ID = ?";
			Object[] parametros = new Object[] {cargaId, codigoPersonal, planId};			
			matAlumno = enocJdbc.queryForObject(comando, new MatAlumnoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.MatAlumnoDao|mapeaRegId|:"+ex);
		
		}
		return matAlumno;
	}
	
	public boolean existeReg(String eventoId, String codigoPersonal, String planId){
		boolean 			ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MAT_ALUMNO "+ 
				"WHERE EVENTO_ID = TO_NUMBER(?,'99') AND CODIGO_PERSONAL = ? "+
				"AND PLAN_ID = ?";
			Object[] parametros = new Object[] {eventoId, codigoPersonal, planId};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			} 
			
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.MatAlumnoDao|existeReg|:"+ex);

		}
		return ok;
	}

	public boolean existeRegEstado(String estado, String codigoPersonal, String planId){
		boolean 			ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MAT_ALUMNO "+ 
				"WHERE ESTADO = ? AND CODIGO_PERSONAL = ? "+
				"AND PLAN_ID = ?";
			Object[] parametros = new Object[] {estado, codigoPersonal, planId};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			} 
			
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.MatAlumnoDao|existeRegEstado|:"+ex);

		}
		return ok;
	}

	public boolean existeRegCarga(String cargaId, String codigoPersonal, String planId){
		boolean 			ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MAT_ALUMNO "+ 
				"WHERE CARGA_ID = ? AND CODIGO_PERSONAL = ? "+
				"AND PLAN_ID = ?";
			Object[] parametros = new Object[] {cargaId, codigoPersonal, planId};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			} 
			
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.MatAlumnoDao|existeRegCarga|:"+ex);

		}
		return ok;
	}
	
	public List<MatAlumno> lisPorEvento( String eventoId, String orden ){
		
		List<MatAlumno> lista	= new ArrayList<MatAlumno>();		
		try{
			String comando = "SELECT EVENTO_ID, CODIGO_PERSONAL, PLAN_ID, TO_CHAR (FECHA, 'DD/MM/YYYY') AS FECHA, USUARIO, MODO, ESTADO"
					+ " FROM ENOC.MAT_ALUMNO WHERE EVENTO_ID = TO_NUMBER(?,'99') "+ orden;
			Object[] parametros = new Object[] {eventoId};
			lista = enocJdbc.query(comando, new MatAlumnoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.MatAlumnoDao|lisPorEvento|:"+ex);
		}		
		return lista;
	}	

	public HashMap<String, MatAlumno> mapaMatAlumno(String eventoId){
		HashMap<String, MatAlumno> mapa = new HashMap<String, MatAlumno>();
		List<MatAlumno> lista	= new ArrayList<MatAlumno>();		
		
		try{
			String comando = "SELECT EVENTO_ID, CODIGO_PERSONAL, PLAN_ID, TO_CHAR (FECHA, 'DD/MM/YYYY') AS FECHA, USUARIO, MODO, ESTADO"
					+ " FROM ENOC.MAT_ALUMNO WHERE EVENTO_ID = TO_NUMBER(?,'99') ";
			Object[] parametros = new Object[] {eventoId};
			lista = enocJdbc.query(comando, new MatAlumnoMapper(), parametros);		
			for(MatAlumno alumno : lista) {
				mapa.put(alumno.getCodigoPersonal()+alumno.getPlanId(), alumno);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.MatAlumnoDao|mapaMatAlumno|:"+ex);
		}		
		return mapa;
	}	

	public HashMap<String, String> mapaMatAlumnoEstado(String eventoId, String estado) {
		
		List<aca.Mapa> list 			= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String,String>();
		String comando = "";
		
		try{			
			comando = "SELECT CODIGO_PERSONAL AS LLAVE, ESTADO AS VALOR FROM ENOC.MAT_ALUMNO WHERE EVENTO_ID = TO_NUMBER(?,'99') AND ESTADO = ?";
			Object[] parametros = new Object[] {eventoId, estado};
			list = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa obj : list){
				mapa.put(obj.getLlave(), obj.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.MatAlumnoDao|mapaMatAlumnoEstado|:"+ex);
		}
		return mapa;
	}
}