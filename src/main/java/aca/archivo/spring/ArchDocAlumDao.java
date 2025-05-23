package aca.archivo.spring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ArchDocAlumDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( ArchDocAlum arch ) {
		boolean ok = false;	
		try{
			String comando = "INSERT INTO ENOC.ARCH_DOCALUM(MATRICULA, " + 
					"IDDOCUMENTO, IDSTATUS, FECHA, USUARIO, CANTIDAD, UBICACION, INCORRECTO) " +
					"VALUES(?, TO_NUMBER(?,'999'), TO_NUMBER(?,'99'), " +
					"TO_DATE(?,'DD/MM/YYYY'), ?, TO_NUMBER(?,'99'), TO_NUMBER(?,'99'), ?)";
			Object[] parametros = new Object[] {
				arch.getMatricula(), arch.getIdDocumento(), arch.getIdStatus(), arch.getFecha(), arch.getUsuario(), arch.getCantidad(), arch.getUbicacion(), arch.getIncorrecto()
 		 	};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchDocAlumDao|insertReg|:"+ex);
		}
		
		return ok;
	}

	public boolean updateReg( ArchDocAlum arch ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.ARCH_DOCALUM"
					+ " SET IDSTATUS = TO_NUMBER(?,'99'),"
					+ " FECHA = TO_DATE(?,'DD/MM/YYYY'),"
					+ " USUARIO = ?, "
					+ " CANTIDAD = TO_NUMBER(?,'99'),"
					+ " UBICACION = TO_NUMBER(?,'99'),"
					+ " INCORRECTO = ?"
					+ " WHERE MATRICULA = ?"
					+ " AND IDDOCUMENTO = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {
				arch.getIdStatus(), arch.getFecha(), arch.getUsuario(), arch.getCantidad(), arch.getUbicacion(), arch.getIncorrecto(), arch.getMatricula(), arch.getIdDocumento()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchDocAlumDao|updateReg|:"+ex);
		}
		
		return ok;
	}

	public boolean updateUbicacion( String codigoPersonal, String documentoId, String ubicacion ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.ARCH_DOCALUM "
					+ " SET UBICACION = ?"
					+ " WHERE MATRICULA = ? AND IDDOCUMENTO = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {
				ubicacion,codigoPersonal,documentoId			
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchDocAlumDao|updateUbicacion|:"+ex);
		}
		
		return ok;
	}

	public boolean deleteReg( String matricula, String idDocumento ) {
		boolean ok = false;

		try{
			String comando = "DELETE FROM ENOC.ARCH_DOCALUM WHERE MATRICULA = ? AND IDDOCUMENTO = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {matricula, idDocumento};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchDocAlumDao|deleteReg|:"+ex);
		}
		return ok;
	}

	public ArchDocAlum mapeaRegId(String matricula, String idDocumento) {
		ArchDocAlum arch = new ArchDocAlum();			
		try{
			String comando = "SELECT MATRICULA, IDDOCUMENTO, IDSTATUS, "
					+ " TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, USUARIO, CANTIDAD, UBICACION, INCORRECTO"
					+ " FROM ENOC.ARCH_DOCALUM "
					+ " WHERE MATRICULA = ? "
					+ " AND IDDOCUMENTO = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {matricula, idDocumento};
			arch = enocJdbc.queryForObject(comando, new ArchDocAlumMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchDocAlumDao|mapeaRegId|:"+ex);
		}
		
		return arch;
	}

	public boolean existeReg(String matricula, String idDocumento){
		boolean ok 				= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ARCH_DOCALUM WHERE MATRICULA = ? AND IDDOCUMENTO = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {matricula, idDocumento};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchDocAlumDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean marcarIncorrecto(String matricula, String documentoId, String marca) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ARCH_DOCALUM SET INCORRECTO = ? WHERE MATRICULA = ? AND IDDOCUMENTO = TO_NUMBER(?,'999')";
			
			Object[] parametros = new Object[] {marca,matricula,documentoId};
			if(enocJdbc.update(comando,parametros) >= 1){
				ok = true;
			}
			
		}catch(Exception ex){ 			
			System.out.println("Error - aca.archivo.spring.ArchDocAlumDao|marcarIncorrecto|:"+ex);
		}
		
		return ok;
	}

	public String maximoReg() {
		String maximo 			= "1";
		
		try{
			String comando = "SELECT COALESCE(MAX(IDDOCUMENTO)+1,1) MAXIMO FROM ENOC.ARCH_DOCALUM"; 
			
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchDocAlumDao|maximoReg|:"+ex);
		}
		return maximo;
	}
	
	public String getDescripcion(String matricula, String idDocumento) {
		String descripcion		= "";
		
		try{
			String comando = "SELECT DESCRIPCION FROM ENOC.ARCH_DOCUMENTOS WHERE MATRICULA = ? AND IDDOCUMENTO = TO_NUMBER(?,'999')";
			
			Object[] parametros = new Object[] {matricula, idDocumento};
			descripcion = enocJdbc.queryForObject(comando,String.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchDocAlumno|getDescripcion|:"+ex);
		}
		return descripcion;
	}
	
	public List<ArchDocAlum> getListAll(String matricula, String orden ) {
		List<ArchDocAlum> lista = new ArrayList<ArchDocAlum>();
		String comando	= "";
		
		try{
			comando = " SELECT DA.MATRICULA AS MATRICULA, "
					+ " DA.IDDOCUMENTO AS IDDOCUMENTO, "
					+ " DOC.DESCRIPCION AS DOCUMENTO, "
					+ " DA.IDSTATUS AS IDSTATUS, "
					+ " DS.DESCRIPCION AS STATUS, "
					+ " COALESCE(TO_CHAR(DA.FECHA,'DD/MM/YYYY'),'01/01/1900') AS FECHA, "
					+ " COALESCE(DA.CANTIDAD, 0) AS CANTIDAD, "
					+ " COALESCE(DA.USUARIO, 'vacio') AS USUARIO, "
					+ " DA.UBICACION AS UBICACION,"
					+ " DA.INCORRECTO AS INCORRECTO"
					+ " FROM ENOC.ARCH_DOCALUM DA, ENOC.ARCH_DOCUMENTOS DOC, ENOC.ARCH_STATUS DS "
					+ " WHERE MATRICULA = ? AND DOC.IDDOCUMENTO = DA.IDDOCUMENTO AND "
					+ " DS.IDSTATUS = DA.IDSTATUS "+orden;		
			
			Object[] parametros = new Object[] {matricula};
			lista = enocJdbc.query(comando, new ArchDocAlumMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchDocAlumDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public List<ArchDocAlum> getListOne(String matricula, String orden ) {
		List<ArchDocAlum> lista 	= new ArrayList<ArchDocAlum>();
		String comando	= "";
		
		try{
			comando = " SELECT MATRICULA, IDDOCUMENTO, IDSTATUS, TO_CHAR(FECHA,'YYYY/MM/DD') AS FECHA, "
					+ " CANTIDAD, USUARIO, UBICACION, INCORRECTO FROM ENOC.ARCH_DOCALUM WHERE MATRICULA = ? "+orden;
			
			Object[] parametros = new Object[] {matricula};
			lista = enocJdbc.query(comando, new ArchDocAlumMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchDocAlumDao|getListOne|:"+ex);
		}
		return lista;
	}

	public List<ArchDocAlum> getListDocFiltrados(String matricula, String mostrar, String orden ) {
		List<ArchDocAlum> lista 	= new ArrayList<ArchDocAlum>();
		String comando	= "";
		
		try{
			comando = " SELECT MATRICULA, IDDOCUMENTO, IDSTATUS, TO_CHAR(FECHA,'YYYY/MM/DD') AS FECHA, "
					+ " CANTIDAD, USUARIO, UBICACION, INCORRECTO FROM ENOC.ARCH_DOCALUM WHERE MATRICULA = ? "
					+ " AND IDDOCUMENTO IN (SELECT IDDOCUMENTO FROM ARCH_DOCUMENTOS WHERE MOSTRAR = ?) "+orden;
			
			Object[] parametros = new Object[] {matricula,mostrar};
			lista = enocJdbc.query(comando, new ArchDocAlumMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchDocAlumDao|getListDocFiltrados|:"+ex);
		}
		return lista;
	}
	
	public List<ArchDocAlum> getListAlumno( String matricula, String ubicacion, String orden ) {
		List<ArchDocAlum> lista 	= new ArrayList<ArchDocAlum>();
		String comando	= "";
		
		try{
			comando = " SELECT MATRICULA, IDDOCUMENTO, IDSTATUS, FECHA, CANTIDAD, USUARIO, UBICACION, INCORRECTO"
					+ " FROM ENOC.ARCH_DOCALUM"
					+ " WHERE MATRICULA = ?"
					+ " AND UBICACION NOT IN ("+ubicacion+") "+orden;
			
			Object[] parametros = new Object[] {matricula, ubicacion};
			lista = enocJdbc.query(comando, new ArchDocAlumMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchDocAlumDao|getListAlumno|:"+ex);
		}
		return lista;
	}
	
	public List<ArchDocAlum> lisAlumno( String matricula, String orden ) {
		List<ArchDocAlum> lista 	= new ArrayList<ArchDocAlum>();
		String comando	= "";
		
		try{
			comando = " SELECT MATRICULA, IDDOCUMENTO, IDSTATUS, TO_CHAR(FECHA,'YYYY/MM/DD') AS FECHA, CANTIDAD, USUARIO, UBICACION, INCORRECTO"
					+ " FROM ENOC.ARCH_DOCALUM"
					+ " WHERE MATRICULA = ? "+ orden;
			
			Object[] parametros = new Object[] {matricula};
			lista = enocJdbc.query(comando, new ArchDocAlumMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchDocAlumDao|lisAlumno|:"+ex);
		}
		return lista;
	}

	public List<ArchDocAlum> lisIncorrectos(String orden ) {
		List<ArchDocAlum> lista 	= new ArrayList<ArchDocAlum>();
		
		try{
			String comando = " SELECT MATRICULA, IDDOCUMENTO, IDSTATUS, TO_CHAR(FECHA,'YYYY/MM/DD') AS FECHA, CANTIDAD, USUARIO, UBICACION, INCORRECTO"
					+ " FROM ENOC.ARCH_DOCALUM"
					+ " WHERE INCORRECTO = 'S' "+ orden;
			
			lista = enocJdbc.query(comando, new ArchDocAlumMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchDocAlumDao|lisIncorrectos|:"+ex);
		}
		return lista;
	}
	
	public List<ArchDocAlum> lisAlumnosEnDocumento( String idDocumento, String orden ) {
		List<ArchDocAlum> lista 	= new ArrayList<ArchDocAlum>();
		String comando	= "";
		
		try{
			comando = " SELECT MATRICULA, IDDOCUMENTO, IDSTATUS, FECHA, CANTIDAD, USUARIO, UBICACION, INCORRECTO"
					+ " FROM ENOC.ARCH_DOCALUM WHERE IDDOCUMENTO = TO_NUMBER(?,'999') "+orden;		 
			
			Object[] parametros = new Object[] {idDocumento};
			lista = enocJdbc.query(comando, new ArchDocAlumMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchDocAlumDao|lisAlumnosEnDocumento|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String, ArchDocAlum> mapArchDocAlumno(String matricula) {
		HashMap<String, ArchDocAlum> mapa = new HashMap<String, ArchDocAlum>();
		List<ArchDocAlum> lista 		 = new ArrayList<ArchDocAlum>();
		String comando		= "";
		try{
			comando = "SELECT MATRICULA, IDDOCUMENTO, IDSTATUS, FECHA, CANTIDAD, USUARIO, UBICACION, INCORRECTO FROM ENOC.ARCH_DOCALUM WHERE MATRICULA = ?";
			
			Object[] parametros = new Object[] {matricula};
			lista = enocJdbc.query(comando, new ArchDocAlumMapper(), parametros);
			for(ArchDocAlum m : lista){
				mapa.put(m.getIdDocumento()+m.getIdStatus(), m);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchDocAlumDao|mapArchDocAlumno|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, ArchDocAlum> mapaBuscaPorDocumento(String matricula) {
		HashMap<String, ArchDocAlum> mapa = new HashMap<String, ArchDocAlum>();
		List<ArchDocAlum> lista 		 = new ArrayList<ArchDocAlum>();
		String comando		= "";
		try{
			comando = "SELECT MATRICULA, IDDOCUMENTO, IDSTATUS, FECHA, CANTIDAD, USUARIO, UBICACION, INCORRECTO FROM ENOC.ARCH_DOCALUM WHERE MATRICULA = ?";
			
			Object[] parametros = new Object[] {matricula};
			lista = enocJdbc.query(comando, new ArchDocAlumMapper(), parametros);
			for(ArchDocAlum m : lista){
				mapa.put(m.getIdDocumento(), m);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchDocAlumDao|mapaBuscaPorDocumento|:"+ex);
		}
		return mapa;
	}

	public HashMap<String, String> mapAlumnosEnDocumento(String idDocumento) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();
		String comando		= "";
		try{
			comando = "SELECT MATRICULA AS LLAVE, COUNT(MATRICULA) AS VALOR FROM ENOC.ARCH_DOCALUM WHERE IDDOCUMENTO = TO_NUMBER(?,'999') GROUP BY MATRICULA";
					
			Object[] parametros = new Object[] {idDocumento};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchDocAlumDao|mapAlumnosEnDocumento|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapDocumentosUsados() {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT IDDOCUMENTO AS LLAVE, COUNT(MATRICULA) AS VALOR FROM ENOC.ARCH_DOCALUM GROUP BY IDDOCUMENTO";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchDocAlumDao|mapDocumentosUsados|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapDocEstadoUsados() {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT IDDOCUMENTO||'-'||IDSTATUS AS LLAVE, COUNT(*) AS VALOR FROM ENOC.ARCH_DOCALUM GROUP BY IDDOCUMENTO||'-'||IDSTATUS";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchDocAlumDao|mapDocEstadoUsados|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaDocEventoId(String eventoId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT MATRICULA AS LLAVE, MATRICULA AS VALOR FROM ENOC.ARCH_DOCALUM"
					+ " WHERE MATRICULA IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_EGRESO WHERE EVENTO_ID = ?)"
					+ " AND IDDOCUMENTO = 14";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(),eventoId);
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchDocAlumDao|mapaDocEventoId|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaAlumnosEnDocumento(String documentoId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT MATRICULA AS LLAVE, TO_CHAR(FECHA,'YYYY/MM/DD') AS VALOR FROM ENOC.ARCH_DOCALUM WHERE IDDOCUMENTO = TO_NUMBER(?,'999')";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), documentoId);
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchDocAlumDao|mapaAlumnosEnDocumento|:"+ex);
		}
		return mapa;
	}

}