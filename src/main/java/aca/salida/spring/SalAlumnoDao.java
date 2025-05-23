package aca.salida.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SalAlumnoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( SalAlumno salAlumno ) {
		boolean ok = false;
		
		try{			
			String comando = "INSERT INTO ENOC.SAL_ALUMNO " + 
					"(SALIDA_ID, CODIGO_PERSONAL, FECHA, USUARIO) " +
					" VALUES(TO_NUMBER(?,'99999'), ?, TO_DATE(?,'DD/MM/YYYY'),? ) ";
			Object[] parametros = new Object[] {salAlumno.getSalidaId(), salAlumno.getCodigoPersonal(), salAlumno.getFecha(), salAlumno.getUsuario()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;			
			}
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalAlumnoDao|insertReg|:"+ex);
		}
		return ok;
	}
	
	public boolean updateReg( SalAlumno salAlumno ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.SAL_ALUMNO "+ 
				" SET FECHA = TO_DATE(?,'DD/MM/YYYY'), " +
				" USUARIO = ? " +
				" WHERE SALIDA_ID = TO_NUMBER(?,'99999')" +
				" AND  CODIGO_PERSONAL = ? ";
			Object[] parametros = new Object[]{salAlumno.getFecha(), salAlumno.getUsuario(), salAlumno.getSalidaId(), salAlumno.getCodigoPersonal()};
			if (enocJdbc.update(comando,parametros)==1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalAlumnoDao|updateReg|:"+ex);
		}
		return ok;
	}	

    public boolean deleteReg( SalAlumno salAlumno) {
    	boolean ok = false;

        try {
             String comando =
                    " DELETE FROM ENOC.SAL_ALUMNO" + 
                    " WHERE SALIDA_ID = TO_NUMBER(?, '99999')" +
                    " AND CODIGO_PERSONAL = ?";
             Object[] parametros = new Object[]{salAlumno.getSalidaId(), salAlumno.getCodigoPersonal()};
             if (enocJdbc.update(comando, parametros)==1)
 				ok = true;
 			else
 				ok = false;
        }catch (Exception ex){
            System.out.println("Error - aca.salida.spring.spring.SalAlumnoDao|deleteReg|:" + ex);
        }
        return ok;
    }
    
    public boolean deleteAlumnos( String salidaId ) {
    	boolean ok = false;

        try {
             String comando = "DELETE FROM ENOC.SAL_ALUMNO WHERE SALIDA_ID = TO_NUMBER(?, '99999')";
             Object[] parametros = new Object[]{salidaId};
             if (enocJdbc.update(comando, parametros) >= 1)
 				ok = true;
 			else
 				ok = false;
        }catch (Exception ex){
            System.out.println("Error - aca.salida.spring.spring.SalAlumnoDao|deleteAlumnos|:" + ex);
        }
        return ok;
    }
    
    public boolean existeReg(String salida, String codigoPersonal) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.SAL_ALUMNO WHERE SALIDA_ID = TO_NUMBER(?, '99999') AND CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {salida, codigoPersonal};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) == 1) {
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalAlumnoDao|existeReg|:"+ex);
		}
		return ok;
	}
    
    public boolean tieneAlumnos( String salida ) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.SAL_ALUMNO WHERE SALIDA_ID = TO_NUMBER(?, '99999')";
			Object[] parametros = new Object[] {salida};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalAlumnoDao|existeReg|:"+ex);
		}
		return ok;
	}
    
    public SalAlumno mapeaRegId( String salidaId, String codigoPersonal ) {
    	SalAlumno salAlumno = new SalAlumno();
		try{ 
	    	String comando = "SELECT SALIDA_ID, CODIGO_PERSONAL, FECHA, USUARIO"
	    			+ " FROM ENOC.SAL_ALUMNO WHERE SALIDA_ID = TO_NUMBER(?,'99999') AND CODIGO_PERSONAL = ? ";
	    	Object[] parametros = new Object[] {salidaId, codigoPersonal};
	    	salAlumno = enocJdbc.queryForObject(comando, new SalAlumnoMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalAlumnoDao|mapeaRegId|:"+ex);
		}
		return salAlumno;
	}
    
    public List<SalAlumno> lisTodos( String orden ) {
		List<SalAlumno> lista = new ArrayList<SalAlumno>();
		String comando	= "";
		
		try{
			comando = "SELECT SALIDA_ID, CODIGO_PERSONAL, FECHA, USUARIO "+			        
					" FROM ENOC.SAL_ALUMNO "+orden;
			lista = enocJdbc.query(comando, new SalAlumnoMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalAlumnoDao|listAll|:"+ex);
		}
		return lista;
	}
	
	public List<SalAlumno> lisPorSalida( String salidaId, String orden ) {
		List<SalAlumno> lista = new ArrayList<SalAlumno>();
		try{
			String comando = "SELECT SALIDA_ID, CODIGO_PERSONAL, FECHA, USUARIO FROM ENOC.SAL_ALUMNO WHERE SALIDA_ID = ? "+orden;
			lista = enocJdbc.query(comando, new SalAlumnoMapper(),salidaId);
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalAlumnoDao|lisTodos|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String,String> mapaPorSolicitud() {
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT SALIDA_ID AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.SAL_ALUMNO GROUP BY SALIDA_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalAlumnoDao|mapaPorSolicitud|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaAlumnosPorDormitorio() {
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT SA.SALIDA_ID||'-'||AA.DORMITORIO AS LLAVE, COUNT(SA.CODIGO_PERSONAL) AS VALOR"
					+ " FROM ENOC.SAL_ALUMNO SA, ENOC.ALUM_ACADEMICO AA"
					+ " WHERE AA.CODIGO_PERSONAL = SA.CODIGO_PERSONAL"
					+ " GROUP BY SA.SALIDA_ID,AA.DORMITORIO";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalAlumnoDao|mapaAlumnosPorDormitorio|:"+ex);
		}		
		return mapa;
	}
}