package aca.tit.spring;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TitAlumnoDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(TitAlumno alumno ) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.TIT_ALUMNO (FOLIO, CODIGO_PERSONAL, PLAN_ID, FECHA, ESTADO, INSTITUCION, XML, RESPUESTA, FOLIO_FISICO, FOLIO_CONTROL)"
					+ " VALUES( ?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'), ?, ?, ?, ?, ?, ?)";			
			Object[] parametros = new Object[] {
				alumno.getFolio(), alumno.getCodigoPersonal(), alumno.getPlanId(), alumno.getFecha(), alumno.getEstado(), alumno.getInstitucion(), 
				alumno.getXml(), alumno.getRespuesta(), alumno.getFolioFisico(), alumno.getFolioControl()
			};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitAlumnoDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg(TitAlumno alumno) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.TIT_ALUMNO"
					+ " SET CODIGO_PERSONAL = ?,"
					+ " PLAN_ID = ?,"
					+ " FECHA = TO_DATE(?, 'YYYY-MM-DD'),"
					+ " ESTADO = ?,"
					+ " INSTITUCION = ?,"
					+ " XML = ?,"
					+ " RESPUESTA = ?,"
					+ " XML_SEP = ?,"
					+ " FOLIO_FISICO = ?,"
					+ " FOLIO_CONTROL = ?"
					+ " WHERE FOLIO = ?";			
			Object[] parametros = new Object[] {
					alumno.getCodigoPersonal(), alumno.getPlanId(), alumno.getFecha(), alumno.getEstado(), alumno.getInstitucion(), alumno.getXml(), alumno.getRespuesta(), alumno.getXmlSep(), 
					alumno.getFolioFisico(), alumno.getFolioControl(), alumno.getFolio() 
			};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitAlumnoDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	public boolean updateInstitucion(String folio, String institucion) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.TIT_ALUMNO SET INSTITUCION = ? WHERE FOLIO = ?";
			
			Object[] parametros = new Object[] {institucion, folio};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitAlumnoDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	public boolean updateEstado(String folio, String estado) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.TIT_ALUMNO SET ESTADO = ? WHERE FOLIO = ?";
			
			Object[] parametros = new Object[] {estado, folio};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitAlumnoDao|updateEstado|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean updateXml(String folio, String xml) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.TIT_ALUMNO SET XML = ? WHERE FOLIO = ?";
			
			Object[] parametros = new Object[] {xml, folio};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitAlumnoDao|updateXml|:"+ex);		
		}
		return ok;
	}
	
	public boolean updateXmlSep(String folio, byte[] xml) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.TIT_ALUMNO SET XML_SEP = ? WHERE FOLIO = ?";			
			Object[] parametros = new Object[] {xml, folio};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitAlumnoDao|updateXmlSep|:"+ex);		
		}
		return ok;
	}
	
	public boolean updateRespuesta(String folio, String respuesta){
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.TIT_ALUMNO SET RESPUESTA = ? WHERE FOLIO = ?";			
			Object[] parametros = new Object[] {respuesta, folio};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitAlumnoDao|updateRespuesta|:"+ex);		
		}
		return ok;
	}

	public boolean updateFechaRes(String folio, String fechaRes){
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.TIT_ALUMNO SET FECHA_RES = TO_DATE(?, 'YYYY-MM-DD') WHERE FOLIO = ?";			
			Object[] parametros = new Object[] {fechaRes,folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitAlumnoDao|updateFechaRes|:"+ex);		
		}
		return ok;
	}
	
	public boolean updateFolioFisico(String folio, String folioFisico){
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.TIT_ALUMNO SET FOLIO_FISICO = ? WHERE FOLIO = ?";			
			Object[] parametros = new Object[] {folioFisico, folio};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitAlumnoDao|updateFolioFisico|:"+ex);		
		}
		return ok;
	}
	
	public boolean updateFolioControl(String folio, String folioControl) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.TIT_ALUMNO SET FOLIO_CONTROL = ? WHERE FOLIO = ?";			
			Object[] parametros = new Object[] {folioControl, folio};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitAlumnoDao|updateFolioControl|:"+ex);		
		}
		return ok;
	}
	
	public boolean quitarXml(String tramiteId) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.TIT_ALUMNO SET XML = 'XML', RESPUESTA = 'X', ESTADO = 'A'"
					+ " WHERE FOLIO IN(SELECT FOLIO FROM ENOC.TIT_TRAMITE_DOC WHERE TRAMITE_ID = TO_NUMBER(?,'9999999'))";			
			Object[] parametros = new Object[] {tramiteId};
 			if (enocJdbc.update(comando,parametros) >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitAlumnoDao|quitarXml|:"+ex);
		}
		return ok;
	}
	
	public boolean quitarXmlFolio(String folioId) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.TIT_ALUMNO SET XML = 'XML', RESPUESTA = 'X', XML_SEP = NULL WHERE FOLIO = ?";
			Object[] parametros = new Object[] {folioId};
 			if (enocJdbc.update(comando,parametros) >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitAlumnoDao|quitarXml|:"+ex);
		}
		return ok;
	}
	
	public boolean deleteReg(String folio ) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.TIT_ALUMNO WHERE FOLIO = ?";			
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitAlumnoDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public TitAlumno mapeaRegId(String folio) {
		TitAlumno alumno = new TitAlumno();		 
		try{
			String comando = "SELECT FOLIO, CODIGO_PERSONAL, PLAN_ID, FECHA, INSTITUCION, XML, RESPUESTA, ESTADO, XML_SEP, FOLIO_FISICO, FOLIO_CONTROL, TO_CHAR(FECHA_RES,'YYYY-MM-DD') AS FECHA_RES FROM ENOC.TIT_ALUMNO WHERE FOLIO = ?";			
			Object[] parametros = new Object[] {folio};
			alumno = enocJdbc.queryForObject(comando, new TitAlumnoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitAlumnoDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return alumno;		
	}	
	
	public boolean existeReg(String folio) {
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.TIT_ALUMNO WHERE FOLIO = ?";			
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitAlumnoDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String getInstitucion(String folio) {
		String institucion = "UM";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.TIT_ALUMNO WHERE FOLIO = ?";			
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				comando = "SELECT INSTITUCION FROM ENOC.TIT_ALUMNO WHERE FOLIO = ?";
				institucion = enocJdbc.queryForObject(comando,String.class,parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitAlumnoDao|getInstitucion|:"+ex);
		}
		return institucion;
	}
	
	public String getRespuesta(String folio){
		String respuesta = "UM";
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.TIT_ALUMNO WHERE FOLIO = ?";
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				comando = "SELECT RESPUESTA FROM ENOC.TIT_ALUMNO WHERE FOLIO = ?";
				respuesta = enocJdbc.queryForObject(comando,String.class,parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitAlumnoDao|getRespuesta|:"+ex);
		}
		return respuesta;
	}
	
	public String getXmlSep(String xmlSep) {
		String xml = "";		
		try{
			byte[] tituloDesencriptado = java.util.Base64.getDecoder().decode(xmlSep);
			InputStream is = new ByteArrayInputStream(tituloDesencriptado);
			System.out.println("Paso 2");
			ZipInputStream zis = new ZipInputStream(is);
			ZipEntry zipEntry = zis.getNextEntry();
			System.out.println("Paso 3"+zipEntry.getName());
			InputStream instream = new FileInputStream(zipEntry.getName());
			System.out.println("Paso 4");
			byte[] tituloByte = new byte[(int)zipEntry.getSize()];
			instream.read(tituloByte);
			xml = new String(tituloByte);
			System.out.println(xml);
			instream.close();
		    zis.closeEntry();
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitAlumnoDao|getXmlSep|:"+ex);
		}
		
		return xml;
	}
	
	public String getFolioControl(String folio) {
		String recibo = "X";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.TIT_ALUMNO WHERE FOLIO = ?";			
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				comando = "SELECT FOLIO_CONTROL FROM ENOC.TIT_ALUMNO WHERE FOLIO = ?";
				recibo = enocJdbc.queryForObject(comando,String.class,parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitAlumnoDao|getInstitucion|:"+ex);
		}
		return recibo;
	}
	
	public List<TitAlumno> lisAll( String orden) {
		List<TitAlumno> lista		= new ArrayList<TitAlumno>();
		try{
			String comando = " SELECT FOLIO, CODIGO_PERSONAL, PLAN_ID, FECHA, ESTADO, INSTITUCION, XML, RESPUESTA, FOLIO_FISICO, FOLIO_CONTROL, TO_CHAR(FECHA_RES,'YYYY-MM-DD') AS FECHA_RES FROM ENOC.TIT_ALUMNO "+orden;			
			lista = enocJdbc.query(comando, new TitAlumnoMapperCorto());				
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitAlumnoDao|listAll|:"+ex);
		}
		return lista;
	}
	
	public List<TitAlumno> lisSinTramites( String orden) {
		List<TitAlumno> lista		= new ArrayList<TitAlumno>();
		try{
			String comando = " SELECT FOLIO, CODIGO_PERSONAL, PLAN_ID, FECHA, ESTADO, INSTITUCION, XML, RESPUESTA, FOLIO_FISICO, FOLIO_CONTROL, TO_CHAR(FECHA_RES,'YYYY-MM-DD') AS FECHA_RES"
					+ " FROM ENOC.TIT_ALUMNO"
					+ " WHERE FOLIO NOT IN (SELECT FOLIO FROM TIT_TRAMITE_DOC) "+orden;			
			lista = enocJdbc.query(comando, new TitAlumnoMapperCorto());				
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitAlumnoDao|lisSinTramites|:"+ex);
		}
		return lista;
	}
	
	public List<TitAlumno> lisEnTramites( String orden) {
		List<TitAlumno> lista		= new ArrayList<TitAlumno>();
		try{
			String comando = " SELECT FOLIO, CODIGO_PERSONAL, PLAN_ID, FECHA, ESTADO, INSTITUCION, XML, RESPUESTA, FOLIO_FISICO, FOLIO_CONTROL, TO_CHAR(FECHA_RES,'YYYY-MM-DD') AS FECHA_RES"
					+ " FROM ENOC.TIT_ALUMNO"
					+ " WHERE FOLIO IN (SELECT FOLIO FROM TIT_TRAMITE_DOC)"
					+ " AND TO_CHAR(RESPUESTA) != 'ZIP-SEP' "+orden;
			lista = enocJdbc.query(comando, new TitAlumnoMapperCorto());				
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitAlumnoDao|lisEnTramites|:"+ex);
		}
		return lista;
	}
	
	public List<TitAlumno> lisTerminado( String orden) {
		List<TitAlumno> lista		= new ArrayList<TitAlumno>();
		try{
			String comando = " SELECT FOLIO, CODIGO_PERSONAL, PLAN_ID, FECHA, ESTADO, INSTITUCION, XML, RESPUESTA, FOLIO_FISICO, FOLIO_CONTROL, TO_CHAR(FECHA_RES,'YYYY-MM-DD') AS FECHA_RES"
					+ " FROM ENOC.TIT_ALUMNO"
					+ " WHERE TO_CHAR(RESPUESTA)  = 'ZIP-SEP' "+orden;
			lista = enocJdbc.query(comando, new TitAlumnoMapperCorto());				
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitAlumnoDao|lisTerminado|:"+ex);
		}
		return lista;
	}
	
	public List<TitAlumno> lisAlumno( String codigoPersonal, String orden) {
		List<TitAlumno> lista		= new ArrayList<TitAlumno>();
		try{
			String comando = " SELECT FOLIO, CODIGO_PERSONAL, PLAN_ID, TO_CHAR(FECHA,'YYYY-MM-DD') AS FECHA, ESTADO, INSTITUCION, XML, RESPUESTA, FOLIO_FISICO, FOLIO_CONTROL, TO_CHAR(FECHA_RES,'YYYY-MM-DD') AS FECHA_RES FROM ENOC.TIT_ALUMNO WHERE CODIGO_PERSONAL = ? "+orden;	 
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new TitAlumnoMapperCorto(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitAlumnoDao|lisAlumno|:"+ex);
		}
		return lista;
	}
	
	public List<TitAlumno> lisConXml( String orden) {
		List<TitAlumno> lista		= new ArrayList<TitAlumno>();
		try{
			String comando = " SELECT FOLIO, CODIGO_PERSONAL, PLAN_ID, TO_CHAR(FECHA,'YYYY-MM-DD') AS FECHA, ESTADO, INSTITUCION, XML, RESPUESTA, FOLIO_FISICO, FOLIO_CONTROL, TO_CHAR(FECHA_RES,'YYYY-MM-DD') AS FECHA_RES"
					+ " FROM ENOC.TIT_ALUMNO"
					+ " WHERE XML NOT LIKE 'XML' AND FOLIO NOT IN (SELECT FOLIO FROM ENOC.TIT_TRAMITE_DOC) "+orden;	 
			lista = enocJdbc.query(comando, new TitAlumnoMapperCorto());	
			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitAlumnoDao|lisConXml|:"+ex);
		}
		return lista;
	}
	
	public List<TitAlumno> lisInstitucion( String institucion, String orden) {
		List<TitAlumno> lista		= new ArrayList<TitAlumno>();
		try{
			String comando = " SELECT FOLIO, CODIGO_PERSONAL, PLAN_ID, TO_CHAR(FECHA,'YYYY-MM-DD') AS FECHA, ESTADO, INSTITUCION, XML, RESPUESTA, FOLIO_FISICO, FOLIO_CONTROL, TO_CHAR(FECHA_RES,'YYYY-MM-DD') AS FECHA_RES"
					+ " FROM ENOC.TIT_ALUMNO "
					+ " WHERE INSTITUCION = ?"
					+ " AND LENGTH(XML) > 3 "+orden;	 
			Object[] parametros = new Object[] {institucion};
			lista = enocJdbc.query(comando, new TitAlumnoMapperCorto(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitAlumnoDao|lisInstitucion|:"+ex);
		}
		return lista;
	}
	
	public List<TitAlumno> lisInstitucion( String institucion, String estado, String orden) {
		List<TitAlumno> lista		= new ArrayList<TitAlumno>();
		try{
			String comando = " SELECT FOLIO, CODIGO_PERSONAL, PLAN_ID, TO_CHAR(FECHA,'YYYY-MM-DD') AS FECHA, ESTADO, INSTITUCION, XML, RESPUESTA, FOLIO_FISICO, FOLIO_CONTROL, TO_CHAR(FECHA_RES,'YYYY-MM-DD') AS FECHA_RES"
					+ " FROM ENOC.TIT_ALUMNO "
					+ " WHERE INSTITUCION = ?"
					+ " AND ESTADO = ? "
					+ " AND LENGTH(XML) > 3 "+orden;	 
			Object[] parametros = new Object[] {institucion, estado};
			lista = enocJdbc.query(comando, new TitAlumnoMapperCorto(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitAlumnoDao|lisInstitucion|:"+ex);
		}
		return lista;
	}
	
	public List<TitAlumno> lisTramite( String tramite, String orden) {
		List<TitAlumno> lista		= new ArrayList<TitAlumno>();
		try{
			String comando = " SELECT FOLIO, CODIGO_PERSONAL, PLAN_ID, TO_CHAR(FECHA,'YYYY-MM-DD') AS FECHA, ESTADO, INSTITUCION, XML, RESPUESTA, FOLIO_FISICO, FOLIO_CONTROL, TO_CHAR(FECHA_RES,'YYYY-MM-DD') AS FECHA_RES"
					+ " FROM ENOC.TIT_ALUMNO "
					+ " WHERE FOLIO IN (SELECT FOLIO FROM ENOC.TIT_TRAMITE_DOC WHERE TRAMITE_ID = TO_NUMBER(?,'9999999')) "+orden;	 
			Object[] parametros = new Object[] {tramite,};
			lista = enocJdbc.query(comando, new TitAlumnoMapperCorto(), parametros);	
			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitAlumnoDao|lisInstitucion|:"+ex);
		}
		return lista;
	}
	
	public String maximoReg(String institucion, String year) {
		String maximo 	= "1";
		String siglas 	= "";
		try{			
			if (institucion.equals("COVOPROM")) siglas = "CVP"; else siglas = "UMO";			
			String comando = "SELECT COALESCE(MAX(SUBSTR(FOLIO,9,4))+1, 1) AS MAXIMO FROM ENOC.TIT_ALUMNO WHERE INSTITUCION = ?";
			Object[] parametros = new Object[] {institucion};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class, parametros); 				
 				if (maximo.length()==1) maximo = "000"+maximo;
 				if (maximo.length()==2) maximo = "00"+maximo;
 				if (maximo.length()==3) maximo = "0"+maximo; 				
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitAlumnoDao|maximoReg|:"+ex);
		}		
		return siglas+year+"-"+maximo;
	}
	
	public String maximoReg(String institucion, String codigoAlumno, String year) {
		String maximo 	= "1";
		String siglas 	= "";
		try{
			
			if (institucion.equals("COVOPROM")) siglas = "CVP"; else siglas = "UMO";			
			String comando = "SELECT COALESCE(MAX(SUBSTR(FOLIO,9,4))+1, 1) AS MAXIMO FROM ENOC.TIT_ALUMNO WHERE INSTITUCION = ?";
			Object[] parametros = new Object[] {institucion};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class, parametros); 				
 				if (maximo.length()==1) maximo = "000"+maximo;
 				if (maximo.length()==2) maximo = "00"+maximo;
 				if (maximo.length()==3) maximo = "0"+maximo; 				
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitAlumnoDao|maximoReg|:"+ex);
		}		
		return siglas+year+"-"+maximo;
	}
	
	public HashMap<String, String> mapTitulados(){
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();		
		HashMap<String, String> mapa	= new HashMap<String,String>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR"
					+ " FROM ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM TIT_ALUMNO)";		
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor() );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.TitAlumnoDao|mapTitulados|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaMatriculas(){
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();		
		HashMap<String, String> mapa	= new HashMap<String,String>();		
		try{
			String comando = "SELECT FOLIO AS LLAVE, CODIGO_PERSONAL AS VALOR FROM TIT_ALUMNO";		
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor() );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.TitAlumnoDao|mapTitulados|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaPlanes(){
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();		
		HashMap<String, String> mapa	= new HashMap<String,String>();		
		try{
			String comando = "SELECT FOLIO AS LLAVE, PLAN_ID AS VALOR FROM TIT_ALUMNO";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor() );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.TitAlumnoDao|mapaPlanes|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaTerminados(){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT FOLIO AS LLAVE, PLAN_ID AS VALOR FROM TIT_ALUMNO WHERE TO_CHAR(RESPUESTA)='XML-SEP'";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitAlumnoDao|mapaTerminados|:"+ex);
		}
		return mapa;
	}
	
}