//Clase  para la tabla ARCH_DOCALUM
package aca.archivo.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ArchPermisosDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( ArchPermisos permisos ) {
		boolean ok = false;
		try{
			String comando = "INSERT INTO ENOC.ARCH_PERMISOS"
					+ " (MATRICULA, USUARIO_ALTA, USUARIO_BAJA, FECHA_INI, FECHA_LIM, ESTADO, FOLIO, COMENTARIO, PLAN_ID) "
					+ " VALUES(?, ?, ?, TO_DATE(?, 'DD/MM/YYYY'), TO_DATE(?, 'DD/MM/YYYY'), ?, TO_NUMBER(?,'999'), ?, ?)";
			
			Object[] parametros = new Object[] {permisos.getMatricula(), permisos.getUsuarioAlta(), 
					permisos.getUsuarioBaja(), permisos.getFechaIni(), permisos.getFechaLim(), 
					permisos.getEstado(), permisos.getFolio(), permisos.getComentario(), permisos.getPlanId()};
			
			if (enocJdbc.update(comando,parametros)==1)
				ok = true;
			else
				ok = false;
			
			}catch(Exception ex){
				System.out.println("Error - aca.archivo.spring.ArchPermisosDao|insertReg|:"+ex);
			}
		return ok;
	}
	
	public boolean updateReg(ArchPermisos permisos ) {
		boolean ok = false;
		try{
			String comando = "UPDATE ENOC.ARCH_PERMISOS"
					+ " SET USUARIO_ALTA = ?, "
					+ " USUARIO_BAJA = ?, "
					+ " FECHA_INI = TO_DATE(?,'DD/MM/YYYY'), "
					+ " FECHA_LIM = TO_DATE(?,'DD/MM/YYYY'), "
					+ " ESTADO = ?,"
					+ " COMENTARIO = ?,"
					+ " PLAN_ID = ?"
					+ " WHERE MATRICULA = ? AND FOLIO = TO_NUMBER(?,'999')";			
			Object[] parametros = new Object[] {permisos.getUsuarioAlta(), permisos.getUsuarioBaja(), 
					permisos.getFechaIni(), permisos.getFechaLim(), permisos.getEstado(), 
					permisos.getComentario(), permisos.getPlanId(), permisos.getMatricula(), permisos.getFolio()};			
			if (enocJdbc.update(comando,parametros)==1)
				ok = true;
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchPermisosDao|updateReg|:"+ex);
		}
		return ok;
	}
	
	public boolean deleteReg(String matricula, String folio ) {
		boolean ok = false;
		try{
			String comando = "DELETE FROM ENOC.ARCH_PERMISOS WHERE MATRICULA = ? AND FOLIO = TO_NUMBER(?,'999')"; 
			
			Object[] parametros = new Object[] {matricula, folio};
			
			if (enocJdbc.update(comando,parametros)==1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchPermisosDao|deleteReg|:"+ex);
		}
		return ok;
	}
	
	public ArchPermisos mapeaRegId(String matricula, String folio) {
		ArchPermisos permisos = new ArchPermisos();
		try{
			String comando = "SELECT MATRICULA, USUARIO_ALTA, USUARIO_BAJA, "
					+ " TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_LIM, 'DD/MM/YYYY') AS FECHA_LIM, "
					+ " ESTADO, FOLIO, COMENTARIO, PLAN_ID "
					+ " FROM ENOC.ARCH_PERMISOS "
					+ " WHERE MATRICULA = ? AND FOLIO = TO_NUMBER(?,'999')";
			
			Object[] parametros = new Object[] {matricula, folio};
			permisos = enocJdbc.queryForObject(comando, new ArchPermisosMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchPermisosDao|mapeaRegId|:"+ex);
		}
		return permisos;
	}
	
	public boolean existeReg(String matricula, String folio) {
		boolean ok 			= false;
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ARCH_PERMISOS WHERE MATRICULA = ? AND FOLIO = TO_NUMBER(?,'999') ";
			Object[] parametros = new Object[] {matricula, folio};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchPermisosDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public boolean tienePermiso(String matricula) {
		boolean ok 			= false;
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ARCH_PERMISOS "
					+ " WHERE MATRICULA = ? AND NOW() BETWEEN FECHA_INI "
					+ " AND FECHA_LIM AND ESTADO = 'A'";
			Object[] parametros = new Object[] {matricula};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchPermisosDao|tienePermiso|:"+ex);
		}
		return ok;
	}
	
	public String getFolioPermiso(String matricula) {
		String folio = "0";
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ARCH_PERMISOS WHERE MATRICULA = ? AND NOW() BETWEEN FECHA_INI AND FECHA_LIM AND ESTADO = 'A'";
			Object[] parametros = new Object[] {matricula};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>=1){
				comando = "SELECT FOLIO FROM ENOC.ARCH_PERMISOS WHERE MATRICULA = ? AND NOW() BETWEEN FECHA_INI AND FECHA_LIM AND ESTADO = 'A' AND ROWNUM = 1";
				folio 	= enocJdbc.queryForObject(comando,String.class, parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchPermisosDao|tienePermiso|:"+ex);
		}
		return folio;
	}
	
	public String maximoReg( String codigoPersonal) {
		String maximo			= "1";
		try{
			String comando = "SELECT COALESCE(MAX(FOLIO)+1,1) AS MAXIMO FROM ENOC.ARCH_PERMISOS WHERE MATRICULA = ? ";
			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class, parametros);
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchPermisosDao|maximoReg|:"+ex);
		}
		return maximo;
	}
	
	public ArrayList<ArchPermisos> getListAll(String matricula, String orden ) {
		
		List<ArchPermisos> lista	= new ArrayList<ArchPermisos>();
		String comando			= "";
		
		try{
			comando = "SELECT MATRICULA, USUARIO_ALTA, USUARIO_BAJA, FECHA_INI, FECHA_LIM, "
					+ " ESTADO, FOLIO, COMENTARIO FROM ENOC.ARCH_PERMISOS WHERE MATRICULA = ? "+orden;
			Object[] parametros = new Object[] {matricula};
			lista = enocJdbc.query(comando, new ArchPermisosMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchPermisosDao|getListAll|:"+ex);
		}
		return (ArrayList<ArchPermisos>)lista;
	}
	
	public ArrayList<ArchPermisos> getListThis(String matricula, String orden ){
		
		List<ArchPermisos> lista = new ArrayList<ArchPermisos>();
		String comando			= "";
		
		try{
			comando = "SELECT " +
					"COALESCE(MATRICULA, 'NO ENCONTRO') AS MATRICULA, COALESCE(USUARIO_ALTA, 'NO ENCONTRO') AS USUARIO_ALTA, " +
					"COALESCE(USUARIO_BAJA, 'NINGUNO') AS USUARIO_BAJA, " +
					"COALESCE(TO_CHAR(FECHA_INI, 'DD/MM/YYYY'), 'NO ENCONTRO') AS FECHA_INI, " +
					"COALESCE(TO_CHAR(FECHA_LIM, 'DD/MM/YYYY'), 'NO ENCONTRO') AS FECHA_LIM, " +
					"COALESCE(ESTADO, 'NO ENCONTRO') AS ESTADO, " +
					"COALESCE(FOLIO, 0) AS FOLIO, " +
					"COALESCE(COMENTARIO, 'NO ENCONTRO') AS COMENTARIO, PLAN_ID " +
					"FROM ENOC.ARCH_PERMISOS " +
					"WHERE MATRICULA = ? " +orden;
			Object[] parametros = new Object[] {matricula};
			lista = enocJdbc.query(comando, new ArchPermisosMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchPermisosDao|getListThis|:"+ex);
		}		
		
		return (ArrayList<ArchPermisos>)lista;
	}
	

	public ArrayList<ArchPermisos> getListMax(String matricula) {
	
		List<ArchPermisos> lista = new ArrayList<ArchPermisos>();
		String comando				= "";
		
		try{
			comando = "Select COALESCE(max(folio) + 1, 1) folio " +
			"from ENOC.arch_permisos " + 
			"where matricula = ? ";
			
			lista = enocJdbc.query(comando, new ArchPermisosMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchPermisosDao|getListMax|:"+ex);
		}
		
		return (ArrayList<ArchPermisos>)lista;
	}	
	
	public ArrayList<ArchPermisos> getListFecha(String matricula){
		
		List<ArchPermisos> lista = new ArrayList<ArchPermisos>();
		String comando			= "";
		
		try{
			comando = "SELECT FECHA_LIM FROM ENOC.ARCH_PERMISOS "
					+ " WHERE MATRICULA =  ? ";
			
			lista = enocJdbc.query(comando, new ArchPermisosMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchPermisosDao|getListFecha|:"+ex);
		}
		
		return (ArrayList<ArchPermisos>)lista;
	}
	
}