package aca.archivo.spring;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import aca.archivo.spring.ArchEntrega;

@Component
public class ArchEntregaDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( ArchEntrega arch ) {
		boolean ok = false;	
		try{
			String comando = "INSERT INTO ENOC.ARCH_ENTREGA(CODIGO_PERSONAL, FOLIO, DOCUMENTOS, FECHA) "
					+ " VALUES(?, TO_NUMBER(?,'99'), ?, TO_DATE(?,'DD/MM/YYYY'))";
			Object[] parametros = new Object[] {arch.getCodigoPersonal(), arch.getFolio(), arch.getDocumentos(), arch.getFecha()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchEntregaDao|insertReg|:"+ex);
		}
		
		return ok;
	}

	public boolean updateReg( ArchEntrega arch ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.ARCH_ENTREGA"
					+ " SET DOCUMENTOS = ?,"
					+ " FECHA = TO_DATE(?,'DD/MM/YYYY')"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND FOLIO = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {arch.getDocumentos(), arch.getFecha(), arch.getCodigoPersonal(), arch.getFolio()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchEntregaDao|updateReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateIdentificacion( byte[] identificacion, String codigoPersonal, String folio ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.ARCH_ENTREGA "
					+ " SET IDENTIFICACION = ? "
					+ " WHERE CODIGO_PERSONAL = ? "
					+ " AND FOLIO = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {identificacion, codigoPersonal, folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchEntregaDao|updateIdentificacion|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean updatePoder( byte[] poder, String codigoPersonal, String folio ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.ARCH_ENTREGA "
					+ " SET PODER = ? "
					+ " WHERE CODIGO_PERSONAL = ? "
					+ " AND FOLIO = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {poder, codigoPersonal, folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchEntregaDao|updatePoder|:"+ex);		
		}		
		return ok;
	}
	
	public boolean updateEnvio( byte[] envio, String codigoPersonal, String folio ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.ARCH_ENTREGA "
					+ " SET ENVIO = ? "
					+ " WHERE CODIGO_PERSONAL = ? "
					+ " AND FOLIO = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {envio, codigoPersonal, folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchEntregaDao|updateEnvio|:"+ex);		
		}		
		return ok;
	}
	
	public boolean updateFirma( byte[] firma, String codigoPersonal, String folio ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.ARCH_ENTREGA "
					+ " SET FIRMA = ? "
					+ " WHERE CODIGO_PERSONAL = ? "
					+ " AND FOLIO = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {firma, codigoPersonal, folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchEntregaDao|updateFirma|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean updateExtra( byte[] extra, String codigoPersonal, String folio ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.ARCH_ENTREGA "
					+ " SET EXTRA = ? "
					+ " WHERE CODIGO_PERSONAL = ? "
					+ " AND FOLIO = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {extra, codigoPersonal, folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchEntregaDao|updateExtra|:"+ex);		
		}
		
		return ok;
	}

	public boolean deleteReg( String codigoPersonal, String folio ) {
		boolean ok = false;
		try{
			String comando = "DELETE FROM ENOC.ARCH_ENTREGA "
					+ " WHERE CODIGO_PERSONAL = ? "
					+ " AND FOLIO = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {codigoPersonal, folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchEntregaDao|deleteReg|:"+ex);
		}
		return ok;
	}

	public ArchEntrega mapeaRegId(  String codigoPersonal, String folio ) {
		
		ArchEntrega objeto = new ArchEntrega();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, FOLIO, DOCUMENTOS, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA"
					+ " FROM ENOC.ARCH_ENTREGA"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND FOLIO = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {codigoPersonal, folio};
			objeto = enocJdbc.queryForObject(comando, new ArchEntregaMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchEntregaDao|mapeaRegId|:"+ex);
		}		
		return objeto;
	}

	public boolean existeReg( String codigoPersonal, String folio) {
		boolean 		ok 		= false;		
		try{
			String comando = "SELECT COUNT(*) "
					+ " FROM ENOC.ARCH_ENTREGA "
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND FOLIO = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {codigoPersonal, folio};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumCredencialDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public ArchEntrega getEntrega(String codigoPersonal, String folio ){
		ArchEntrega entrega 	= new ArchEntrega();		
		try{
			String comando = " SELECT CODIGO_PERSONAL, FOLIO, FECHA, DOCUMENTOS, IDENTIFICACION, PODER, ENVIO, FIRMA, EXTRA"
					+ " FROM ENOC.ARCH_ENTREGA WHERE CODIGO_PERSONAL = ? AND FOLIO = ?"; 			
			Object[] parametros = new Object[] {codigoPersonal, folio};
			entrega = enocJdbc.queryForObject(comando, new ArchEntregaCompletoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchEntregaDao|getEntrega|:"+ex);
		}
		return entrega;
	}

	public List<ArchEntrega> getListAll( String orden ) {		
		List<ArchEntrega> lista	= new ArrayList<ArchEntrega>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, FOLIO, DOCUMENTOS, FECHA FROM ENOC.ALUM_CREDENCIAL "+orden; 
			lista = enocJdbc.query(comando, new ArchEntregaMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumCredencialDao|getListAll|:"+ex);
		}		
		return lista;
	}
	
	public List<ArchEntrega> lisSinArchivos(String codigoPersonal){
		
		List<ArchEntrega> lista 	= new ArrayList<ArchEntrega>();		
		try{
			String comando = " SELECT CODIGO_PERSONAL, FOLIO, FECHA, DOCUMENTOS FROM ENOC.ARCH_ENTREGA WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new ArchEntregaMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchEntregaDao|getListaSinArchivos|:"+ex);
		}
		return lista;
	}
	
	public List<ArchEntrega> getListaSinArchivos(String codigoPersonal){
		
		List<ArchEntrega> lista 	= new ArrayList<ArchEntrega>();
		
		try{
			String comando = " SELECT CODIGO_PERSONAL, FOLIO, FECHA, DOCUMENTOS"
					+ " FROM ENOC.ARCH_ENTREGA"
					+ " WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new ArchEntregaMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchEntregaDao|getListaSinArchivos|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String,String> mapaIdentificacion( String codigoAlumno) {
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT FOLIO AS LLAVE, CODIGO_PERSONAL AS VALOR FROM ENOC.ARCH_ENTREGA WHERE CODIGO_PERSONAL = ? AND IDENTIFICACION IS NOT NULL";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), codigoAlumno);
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchEntregaDao|mapaIdentificacion|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaPoder( String codigoAlumno) {		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT FOLIO AS LLAVE, CODIGO_PERSONAL AS VALOR FROM ENOC.ARCH_ENTREGA WHERE CODIGO_PERSONAL = ? AND PODER IS NOT NULL";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), codigoAlumno);
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchEntregaDao|mapaPoder|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaEnvio( String codigoAlumno) {		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT FOLIO AS LLAVE, CODIGO_PERSONAL AS VALOR FROM ENOC.ARCH_ENTREGA WHERE CODIGO_PERSONAL = ? AND ENVIO IS NOT NULL";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), codigoAlumno);
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchEntregaDao|mapaEnvio|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaExtra( String codigoAlumno) {		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT FOLIO AS LLAVE, CODIGO_PERSONAL AS VALOR FROM ENOC.ARCH_ENTREGA WHERE CODIGO_PERSONAL = ? AND EXTRA IS NOT NULL";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), codigoAlumno);
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchEntregaDao|mapaExtra|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaFirma( String codigoAlumno) {		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT FOLIO AS LLAVE, CODIGO_PERSONAL AS VALOR FROM ENOC.ARCH_ENTREGA WHERE CODIGO_PERSONAL = ? AND FIRMA IS NOT NULL";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), codigoAlumno);
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchEntregaDao|mapaFirma|:"+ex);
		}
		
		return mapa;
	}

}