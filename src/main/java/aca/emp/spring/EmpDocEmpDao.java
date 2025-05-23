// Clase Util para la tabla de Carga
package aca.emp.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class EmpDocEmpDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( EmpDocEmp docEmp ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.EMP_DOCEMP"+ 
				"(CODIGO_PERSONAL, DOCUMENTO_ID, HOJA, IMAGEN, USUARIO, FECHA) "+
				"VALUES(?, TO_NUMBER(?,'999'), TO_NUMBER(?,'99'), ?, ?, TO_DATE(?, 'DD/MM/YYYY'))";
			Object[] parametros = new Object[] {
					docEmp.getCodigoPersonal(), docEmp.getDocumentoId(), docEmp.getHoja(), docEmp.getImagen(), docEmp.getUsuario(), docEmp.getFecha()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpDocEmpDao|insertReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateReg( EmpDocEmp docEmp ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.EMP_DOCEMP"
				+ " SET IMAGEN = ?,"
				+ " USUARIO = ?, "
				+ " FECHA = TO_DATE(?, 'DD/MM/YYYY') "
				+ " WHERE CODIGO_PERSONAL = ? "
				+ " AND DOCUMENTO_ID = TO_NUMBER(?,'999')"
				+ " AND HOJA = TO_NUMBER(?,'99')";
				
			Object[] parametros = new Object[] {
					docEmp.getImagen(), docEmp.getUsuario(), docEmp.getFecha(), docEmp.getCodigoPersonal(), docEmp.getDocumentoId(), docEmp.getHoja()
		 	};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpDocEmpDao|updateReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateImagen( byte[] imagen, String codigoPersonal, String documentoId, String hoja ) {
		boolean ok 				= false;
		
		try{
			String comando = "UPDATE ENOC.EMP_DOCEMP "
				+ " SET IMAGEN = ? "
				+ " WHERE CODIGO_PERSONAL = ?"
				+ " AND DOCUMENTO_ID = TO_NUMBER(?,'999')"
				+ " AND HOJA = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {imagen, codigoPersonal, documentoId, hoja};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpDocEmpDaoDao|updateFoto|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean deleteReg( String codigoPersonal, String documentoId, String hoja ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.EMP_DOCEMP"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND DOCUMENTO_ID = TO_NUMBER(?,'999')"
					+ " AND HOJA = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {codigoPersonal, documentoId, hoja};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpDocEmpDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public EmpDocEmp mapeaRegId( String codigoPersonal, String documentoId, String hoja ) {
		
		EmpDocEmp objeto = new EmpDocEmp();
		try{
			String comando = "SELECT CODIGO_PERSONAL, DOCUMENTO_ID, HOJA, IMAGEN, USUARIO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA"
					+ " FROM ENOC.EMP_DOCEMP"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND DOCUMENTO_ID = TO_NUMBER(?,'999')"
					+ " AND HOJA = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {codigoPersonal, documentoId, hoja};
			objeto = enocJdbc.queryForObject(comando, new EmpDocEmpMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpDocEmpDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public boolean existeReg( String codigoPersonal, String documentoId, String hoja) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EMP_DOCEMP "
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND DOCUMENTO_ID = TO_NUMBER(?,'999')"
					+ " AND HOJA = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {codigoPersonal, documentoId, hoja};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpDocEmpDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean existeImagen( String codigoPersonal, String documentoId, String hoja) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EMP_DOCEMP "
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND DOCUMENTO_ID = TO_NUMBER(?,'999')"
					+ " AND HOJA = TO_NUMBER(?,'99')"
					+ " AND IMAGEN IS NOT NULL";
			Object[] parametros = new Object[] {codigoPersonal, documentoId, hoja};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpDocEmpDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public int totDocConImagen( String codigoPersonal ) {
		int total 	= 0;
		
		try{
			String comando = "SELECT COUNT(DISTINCT(DOCUMENTO_ID)) FROM ENOC.EMP_DOCEMP "
					+ " WHERE CODIGO_PERSONAL = ?"					
					+ " AND IMAGEN IS NOT NULL";
			Object[] parametros = new Object[] {codigoPersonal};			 
			total = enocJdbc.queryForObject(comando,Integer.class,parametros);					
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpDocEmpDao|totDocConImagen|:"+ex);
		}
		
		return total;
	}
	
	public List<EmpDocEmp> lisTodos( String orden ) {
		
		List<EmpDocEmp> lista	= new ArrayList<EmpDocEmp>();
		String comando	= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, DOCUMENTO_ID, HOJA, USUARIO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA "
					+ " FROM ENOC.EMP_DOCEMP "+orden;
			lista = enocJdbc.query(comando, new EmpDocEmpMapperCorto());
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpDocEmpDao|lisTodos|:"+ex);
		}
		
		return lista;
	}
	
	public List<EmpDocEmp> lisEmpleado( String codigoEmpleado, String orden ) {
		
		List<EmpDocEmp> lista	= new ArrayList<EmpDocEmp>();
		String comando	= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, DOCUMENTO_ID, HOJA, USUARIO, FECHA FROM ENOC.EMP_DOCEMP WHERE CODIGO_PERSONAL = ?"+orden;
			Object[] parametros = new Object[] {codigoEmpleado};
			lista = enocJdbc.query(comando, new EmpDocEmpMapperCorto(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpDocEmpDao|lisEmpleado|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,String> mapaImagenes(String codigoPersonal) {
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL||DOCUMENTO_ID||HOJA AS LLAVE, CODIGO_PERSONAL AS VALOR"
					+ " FROM ENOC.EMP_DOCEMP"
					+ " WHERE CODIGO_PERSONAL = ? "
					+ " AND IMAGEN IS NOT NULL";
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpDocEmpDao|mapaImagenes|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaEmpImagen() {
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, COUNT(DISTINCT(DOCUMENTO_ID)) AS VALOR"
					+ " FROM ENOC.EMP_DOCEMP"
					+ " WHERE IMAGEN IS NOT NULL GROUP BY CODIGO_PERSONAL";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpDocEmpDao|mapaEmpImagen|:"+ex);
		}
		
		return mapa;
	}

}