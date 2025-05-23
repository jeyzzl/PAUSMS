// CLASE DE LA TABLA CONV_SOLICITUD
package aca.conva.spring;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ConvEventoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(ConvEvento objeto) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CONV_EVENTO"
					+ " (CONVALIDACION_ID, FECHA, USUARIO, CODIGO_PERSONAL, PLAN_ID, ESTADO, COMENTARIO, INSTITUCION, PROGRAMA, TIPO, DICTAMEN, TIPO_CONV, PERIODO, PLAN_ORIGEN) "
					+ " VALUES( TO_NUMBER(?,'9999999'),TO_DATE(?,'DD/MM/YYYY'),?,?,?,?,?,?,?,?,?,?,?,?)";
			
			Object[] parametros = new Object[] {objeto.getConvalidacionId(),objeto.getFecha(),objeto.getUsuario(),objeto.getCodigoPersonal(),objeto.getPlanId(),objeto.getEstado(),
					objeto.getComentario(),objeto.getInstitucion(),objeto.getPrograma(),objeto.getTipo(),objeto.getDictamen(),objeto.getTipoConv(),
					objeto.getPeriodo(),objeto.getPlanOrigen()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvEventoDaoDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg(ConvEvento objeto ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CONV_EVENTO "
					+ " SET "
					+ " FECHA = TO_DATE(?,'DD/MM/YYYY'), "
					+ " USUARIO = ?, "
					+ " CODIGO_PERSONAL = ?, "
					+ " PLAN_ID = ?, "
					+ " ESTADO = ?, "
					+ " COMENTARIO = ?, "
					+ " INSTITUCION = ?, "
					+ " PROGRAMA = ?, "
					+ " TIPO = ?, "
					+ " DICTAMEN = ?, "
					+ " TIPO_CONV = ?, "
					+ " PERIODO = ? ,"
					+ " PLAN_ORIGEN = ? "
					+ " WHERE CONVALIDACION_ID = TO_NUMBER(?,'9999999') ";	
			
			Object[] parametros = new Object[] {objeto.getFecha(),objeto.getUsuario(),objeto.getCodigoPersonal(),objeto.getPlanId(),objeto.getEstado(),objeto.getComentario(),
					objeto.getInstitucion(),objeto.getPrograma(),objeto.getTipo(),objeto.getDictamen(),objeto.getTipoConv(),objeto.getPeriodo(),objeto.getPlanOrigen(),objeto.getConvalidacionId()};
			
			if (enocJdbc.update(comando,parametros)==1){			
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvEventoDao|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean updateInstitucion(String institucion, String programa, String convalidacionId  ) {
		
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CONV_EVENTO "
					+ " SET INSTITUCION = ?, "
					+ " PROGRAMA = ? "
					+ " WHERE CONVALIDACION_ID = TO_NUMBER(?,'9999999') ";
			
			Object[] parametros = new Object[] { institucion, programa, convalidacionId };
			
			if (enocJdbc.update(comando,parametros)==1){			
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvEventoDao|updateInstitucion|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean updateDicCom(String dictamen, String comentario, String convalidacionId ) {		
		boolean ok = false;
		
		try{
			String comando = " UPDATE ENOC.CONV_EVENTO"
					+ " SET DICTAMEN = ?,"
					+ " COMENTARIO = ?"
					+ " WHERE CONVALIDACION_ID = TO_NUMBER(?,'9999999')";
			
			Object[] parametros = new Object[] { dictamen, comentario, convalidacionId };
			
			if (enocJdbc.update(comando,parametros)==1){			
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvEventoDao|updateDicCom|:"+ex);
		}	
		return ok;
	}
	
	public boolean deleteReg(String convalidacionId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CONV_EVENTO "
					+ " WHERE CONVALIDACION_ID = TO_NUMBER(?,'9999999')";
			
			Object[] parametros = new Object[] { convalidacionId };
			
			if (enocJdbc.update(comando,parametros)==1){			
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvEventoDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public ConvEvento mapeaRegId( String convalidacionId ) {
		ConvEvento objeto = new ConvEvento();
		
		try{
			String comando = "SELECT "
					+ " CONVALIDACION_ID, TO_CHAR(FECHA,'DD/MM/YYYY') FECHA, USUARIO, CODIGO_PERSONAL, "
					+ " PLAN_ID, ESTADO, COMENTARIO, INSTITUCION, PROGRAMA, TIPO, DICTAMEN, TIPO_CONV, PERIODO, PLAN_ORIGEN "
					+ " FROM ENOC.CONV_EVENTO WHERE CONVALIDACION_ID = TO_NUMBER(?,'9999999')"; 
			
			Object[] parametros = new Object[] { convalidacionId };
			objeto = enocJdbc.queryForObject(comando, new ConvEventoMapper(), parametros);
		
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvEventoDao|mapeaRegId|:"+ex);
		}
		
		return objeto;		
	}
	
	public boolean existeCovaliadacion(String convalidacionId) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CONV_EVENTO "
					+ " WHERE CONVALIDACION_ID = TO_NUMBER(?,'9999999')";
					
			Object[] parametros = new Object[] { convalidacionId };	
			
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){	
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvEventoDao|existeCovaliadacion|:"+ex);
		}
		
		return ok;
	}
	
	public boolean existeMatricula( String matricula) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CONV_EVENTO "
					+ " WHERE CODIGO_PERSONAL = ? ";
			
			Object[] parametros = new Object[] { matricula };	
			
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){	
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvEventoDao|existeMatricula|:"+ex);
		}
		
		return ok;
	}
	
	public boolean existeEvento( String codigoPersonal) {
		boolean 		ok 		= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CONV_EVENTO "
					+ " WHERE CODIGO_PERSONAL = ? ";
			Object[] parametros = new Object[] { codigoPersonal };	
			
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){	
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvEventoDao|existeEvento|:"+ex);
		}
		
		return ok;
	}
	
	public boolean aplicaConvalidacion( String codigoPersonal, String planId) {
		boolean 			ok 	= false;
		
		try{
			String comando = "SELECT "
					+ " COALESCE(COUNT(CODIGO_PERSONAL),0) AS NUMCURSOS "
					+ " FROM ENOC.ALUMNO_CURSO "
					+ " WHERE CODIGO_PERSONAL = ? "
					+ " AND PLAN_ID = ? ";		
			
			Object[] parametros = new Object[] { codigoPersonal,planId };	
			
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)<1){	
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvEventoDao|aplicaConvalidacion|:"+ex);
		}
		
		return ok;
	}
	
	public int getMaxReg() {
 		int maximo	= 1;
 		
 		try{
 			
 			String comando = "SELECT COALESCE(MAX(CONVALIDACION_ID)+1,1) AS MAXIMO FROM ENOC.CONV_EVENTO "; 
 			maximo = enocJdbc.queryForObject(comando,Integer.class);
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.conva.spring.ConvEventoDao|getMaxReg|:"+ex);
 		}
 		
 		return maximo;
	}
	
	public int numConvPorEstado( String facultad, String estado) {
 		int total	= 0;
 		
 		try{
 			
 			String comando = "SELECT COUNT(CODIGO_PERSONAL) AS TOTAL FROM CONV_EVENTO"
 					+ " WHERE ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID)) = ? "
 					+ " AND ESTADO = ?"; 
 			
 			Object[] parametros = new Object[] { facultad,estado };	
 			
 			total = enocJdbc.queryForObject(comando,Integer.class,parametros);
 		}catch(Exception ex){
 			System.out.println("Error - aca.conva.spring.ConvEventoDao|numConvPorEstado|:"+ex);
 		}
 		
 		return total;
	}	
	
	public ArrayList<ConvEvento> getListAll( String orden ) {
		List<ConvEvento> lista		= new ArrayList<ConvEvento>();
		
		try{
			String comando = "SELECT CONVALIDACION_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, USUARIO, CODIGO_PERSONAL,"
					+ " PLAN_ID, ESTADO, COMENTARIO, INSTITUCION, PROGRAMA, TIPO, DICTAMEN, TIPO_CONV, PERIODO, PLAN_ORIGEN "
					+ " FROM ENOC.CONV_EVENTO "+orden; 
			
			lista = enocJdbc.query(comando, new ConvEventoMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvEventoDao|getListAll|:"+ex);
		}
		
		return (ArrayList<ConvEvento>)lista;
	}
	
	public ArrayList<ConvEvento> getListPorFechaTipo( String fechaIni, String fechaFin, String tipo, String orden) {
		List<ConvEvento> lista		= new ArrayList<ConvEvento>();		
		try{
			String comando = " SELECT CONVALIDACION_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, USUARIO, CODIGO_PERSONAL, "
					+ " PLAN_ID, ESTADO, COMENTARIO, INSTITUCION, PROGRAMA, TIPO, DICTAMEN, TIPO_CONV, PERIODO, PLAN_ORIGEN "
					+ " FROM ENOC.CONV_EVENTO "
					+ " WHERE TIPO IN("+tipo+") AND FECHA BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY') "+orden; 
			
			Object[] parametros = new Object[] { fechaIni,fechaFin };			
			lista = enocJdbc.query(comando, new ConvEventoMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvEventoDao|getListPorFechaTipo|:"+ex);
		}
		return (ArrayList<ConvEvento>)lista;
	}	
	
	public ArrayList<ConvEvento> getListAllRecientes( String periodoAnterior, String periodoActual, String orden ) {
		List<ConvEvento> lista		= new ArrayList<ConvEvento>();
		
		try{
			String comando = " SELECT CONVALIDACION_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, USUARIO, CODIGO_PERSONAL, "
					+ " PLAN_ID, ESTADO, COMENTARIO, INSTITUCION, PROGRAMA, TIPO, DICTAMEN, TIPO_CONV, PERIODO, PLAN_ORIGEN "					
					+ " FROM ENOC.CONV_EVENTO "
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE SUBSTR(CARGA_ID,1,4) IN (?,?)) "+orden;			
			
			Object[] parametros = new Object[] { periodoAnterior,periodoActual };	
			
			lista = enocJdbc.query(comando, new ConvEventoMapper(),parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvEventoDao|getListAllRecientes|:"+ex);
		}
		
		return (ArrayList<ConvEvento>)lista;
	}
	
	public ArrayList<ConvEvento> getList( String convalidacionId, String orden ) {
		List<ConvEvento> lista		= new ArrayList<ConvEvento>();

		try{
			String comando = "SELECT CONVALIDACION_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, USUARIO, CODIGO_PERSONAL, "
					+ " PLAN_ID, ESTADO, COMENTARIO, INSTITUCION, PROGRAMA, TIPO, DICTAMEN, TIPO_CONV, PERIODO, PLAN_ORIGEN "
					+ " FROM ENOC.CONV_EVENTO WHERE CONVALIDACION_ID = ? "+orden; 
			
			Object[] parametros = new Object[] { convalidacionId };	
			
			lista = enocJdbc.query(comando, new ConvEventoMapper(),parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvEventoDao|getList|:"+ex);
		}
		
		return (ArrayList<ConvEvento>)lista;
	}
	
	public List<ConvEvento> getConvaVacio() {
		List<ConvEvento> lista		= new ArrayList<ConvEvento>();		
		try{
			String comando = "SELECT * FROM ENOC.CONV_EVENTO "
					+ " WHERE ESTADO = 'S'"
					+ " AND now() - FECHA > 7 "
					+ " AND CONVALIDACION_ID NOT IN (SELECT DISTINCT(CONVALIDACION_ID) FROM ENOC.CONV_MATERIA)";			
			lista = enocJdbc.query(comando, new ConvEventoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvEventoDao|getConvaVacio|:"+ex);
		}
		
		return lista;
	}

		
	public ArrayList<ConvEvento> getListPersonal( String matricula, String orden ) {
		List<ConvEvento> lista		= new ArrayList<ConvEvento>();
		
		try{
			String comando = "SELECT CONVALIDACION_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, USUARIO, CODIGO_PERSONAL, "
					+ " PLAN_ID, ESTADO, COMENTARIO, INSTITUCION, PROGRAMA, TIPO, DICTAMEN, TIPO_CONV, PERIODO, PLAN_ORIGEN "
					+ " FROM ENOC.CONV_EVENTO WHERE CODIGO_PERSONAL = ? "+orden; 
			
			Object[] parametros = new Object[] { matricula };	
			
			lista = enocJdbc.query(comando, new ConvEventoMapper(),parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvEventoDao|getListPersonal|:"+ex);
		}
		
		return (ArrayList<ConvEvento>)lista;
	}
	
	public List<ConvEvento> getSolicPorTipo( String matricula, String tipo, String orden ) {
		List<ConvEvento> lista		= new ArrayList<ConvEvento>();
		
		try{
			String comando = "	SELECT CONVALIDACION_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, USUARIO, CODIGO_PERSONAL, "
					+ " PLAN_ID, ESTADO, COMENTARIO, INSTITUCION, PROGRAMA, TIPO, DICTAMEN, TIPO_CONV, PERIODO, PLAN_ORIGEN "
					+ " FROM ENOC.CONV_EVENTO WHERE CODIGO_PERSONAL = ? AND TIPO IN ("+tipo+") "+orden; 
			
			Object[] parametros = new Object[] {matricula};	
			
			lista = enocJdbc.query(comando, new ConvEventoMapper(),parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvEventoDao|getSolicPorTipo|:"+ex);
		}
		
		return lista;
	}
	
	public ArrayList<ConvEvento> getListInscritos( String orden) {
		List<ConvEvento> lista		= new ArrayList<ConvEvento>();
		
		try{
			String comando = "SELECT CONVALIDACION_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, USUARIO, CODIGO_PERSONAL, "
					+ " PLAN_ID, ESTADO, COMENTARIO, INSTITUCION, PROGRAMA, TIPO, DICTAMEN, TIPO_CONV, PERIODO, PLAN_ORIGEN "
					+ " FROM ENOC.CONV_EVENTO "+orden; 
			
			lista = enocJdbc.query(comando, new ConvEventoMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvEventoDao|getListInscritos|:"+ex);
		}
		
		return (ArrayList<ConvEvento>)lista;
	}
	
	public ArrayList<ConvEvento> getListInscritosEstado(String estado, String orden) {
		List<ConvEvento> lista		= new ArrayList<ConvEvento>();
		
		try{
			String comando = "SELECT CONVALIDACION_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, USUARIO, CODIGO_PERSONAL, "
					+ " PLAN_ID, ESTADO, COMENTARIO, INSTITUCION, PROGRAMA, TIPO, DICTAMEN, TIPO_CONV, PERIODO, PLAN_ORIGEN "
					+ " FROM ENOC.CONV_EVENTO WHERE ESTADO IN(?) "+orden; 
			
			Object[] parametros = new Object[] {estado};	
			
			lista = enocJdbc.query(comando, new ConvEventoMapper(),parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvEventoDao|getListInscritosEstado|:"+ex);
		}
		
		return (ArrayList<ConvEvento>)lista;
	}
	
	public ArrayList<ConvEvento> getListEventosCumplidos( String orden ) {
		List<ConvEvento> lista		= new ArrayList<ConvEvento>();
		
		try{
			String comando = "SELECT CONVALIDACION_ID, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, USUARIO, CODIGO_PERSONAL,"
					+ " PLAN_ID, ESTADO, COMENTARIO, INSTITUCION, PROGRAMA, TIPO, DICTAMEN, TIPO_CONV, PERIODO, PLAN_ORIGEN "
					+ " FROM ENOC.CONV_EVENTO"
					+ " WHERE ESTADO = 'T'"
					+ " AND CONVALIDACION_ID NOT IN (SELECT CONVALIDACION_ID FROM ENOC.CONV_MATERIA"
					+ " WHERE ESTADO <> 'R' AND ESTADO <> 'N')"+orden;
			
			lista = enocJdbc.query(comando, new ConvEventoMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvEventoDao|getListEventosCumplidos|:"+ex);
		}
		
		return (ArrayList<ConvEvento>)lista;
	}
	
	public ArrayList<ConvEvento> getListEventosRegInc( String orden ) {
		List<ConvEvento> lista		= new ArrayList<ConvEvento>();
		
		try{
			String comando = "SELECT CONVALIDACION_ID, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, USUARIO, CODIGO_PERSONAL,"
					+ " PLAN_ID, ESTADO, COMENTARIO, INSTITUCION, PROGRAMA, TIPO, DICTAMEN, TIPO_CONV, PERIODO, PLAN_ORIGEN "
					+ " FROM ENOC.CONV_EVENTO "
					+ " WHERE ESTADO = 'T' "
					+ " AND CONVALIDACION_ID IN (SELECT CONVALIDACION_ID FROM ENOC.CONV_MATERIA"
					+ " WHERE ESTADO = 'R')"+orden;
			
			lista = enocJdbc.query(comando, new ConvEventoMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvEventoDao|getListEventosRegInc|:"+ex);
		}
		
		return (ArrayList<ConvEvento>)lista;
	}
	
	public ArrayList<ConvEvento> getListInscritosConv( String orden) {
		List<ConvEvento> lista		= new ArrayList<ConvEvento>();
		
		try{
			String comando = " SELECT CONVALIDACION_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, USUARIO, CODIGO_PERSONAL,"
					+ " PLAN_ID, ESTADO, COMENTARIO, INSTITUCION, PROGRAMA, TIPO, DICTAMEN, TIPO_CONV, PERIODO, PLAN_ORIGEN "
					+ " FROM ENOC.CONV_EVENTO WHERE CODIGO_PERSONAL "
					+ " IN (SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS WHERE INSCRITOS.CODIGO_PERSONAL = ENOC.CONV_EVENTO.CODIGO_PERSONAL)"+orden; 
			
			lista = enocJdbc.query(comando, new ConvEventoMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvEventoDao|getListInscritosConv|:"+ex);
		}
		
		return (ArrayList<ConvEvento>)lista;
	}
	
	public HashMap<String,String> mapConvPorFacultad() {
		HashMap<String,String> mapa = new HashMap<String,String>();
		
		try{
			String comando = " SELECT ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID)) AS FACULTAD||ESTADO AS LLAVE, COALESCE(COUNT(CODIGO_PERSONAL),0) AS TOTAL"
					+ " FROM ENOC.CONV_EVENTO"
					+ " GROUP BY ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID)), ESTADO";
			List<Map<String,Object>> lista = enocJdbc.queryForList(comando);
			for(Map<String,Object> row: lista){
				mapa.put((String)row.get("LLAVE"), (String)row.get("TOTAL"));
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvEventoDao|mapConvPorFacultad|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapConvPorFacultad( String fechaIni, String fechaFin, String tipo) {
		
		HashMap<String,String> mapa 	= new HashMap<String,String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();		
		try{
			String comando = " SELECT ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID))||ESTADO AS LLAVE, COALESCE(COUNT(CODIGO_PERSONAL),0) AS VALOR"
					+ " FROM ENOC.CONV_EVENTO"
					+ " WHERE FECHA BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY') AND TIPO IN("+tipo+")"
					+ " GROUP BY ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID)), ESTADO";
			Object[] parametros = new Object[] {fechaIni, fechaFin};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa row : lista) {
				mapa.put(row.getLlave(), (String)row.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvEventoDao|mapConvPorFacultad|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapConvReciente( String periodoAnterior, String periodoActual) {
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = " SELECT ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID)) AS FACULTAD||ESTADO AS LLAVE, COALESCE(COUNT(CODIGO_PERSONAL),0) AS VALOR"
					+ " FROM ENOC.CONV_EVENTO"
					+ " WHERE CODIGO_PERSONAL"
					+ " IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE SUBSTR(CARGA_ID,1,4) IN (?,?))"
					+ " GROUP BY ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID)), ESTADO";
			Object[] parametros = new Object[] {periodoAnterior, periodoActual};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa row : lista) {
				mapa.put(row.getLlave(), (String)row.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvEventoDao|mapConvReciente|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapConvPorCarrera() {
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = " SELECT ENOC.CARRERA(PLAN_ID) AS CARRERA||ESTADO AS LLAVE, COALESCE(COUNT(CODIGO_PERSONAL),0) AS VALOR"
					+ " FROM ENOC.CONV_EVENTO"
					+ " GROUP BY ENOC.CARRERA(PLAN_ID), ESTADO";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa row : lista) {
				mapa.put(row.getLlave(), (String)row.getValor());
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvEventoDao|mapConvPorCarrera|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapConvPorCarreraReciente( String periodoAnterior, String periodoActual) {
		
		HashMap<String,String> mapa	= new HashMap<String,String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = " SELECT ENOC.CARRERA(PLAN_ID) AS CARRERA||ESTADO AS LLAVE, COALESCE(COUNT(CODIGO_PERSONAL),0) AS VALOR"
					+ " FROM ENOC.CONV_EVENTO"
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE SUBSTR(CARGA_ID,1,4) IN (?,?))"
					+ " GROUP BY ENOC.CARRERA(PLAN_ID), ESTADO";
			Object[] parametros = new Object[] {periodoAnterior, periodoActual};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa row : lista) {
				mapa.put(row.getLlave(), (String)row.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvEventoDao|mapConvPorCarreraReciente|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapConvPorFechaTipo( String fechaIni, String fechaFin, String tipo) {
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();		
		try{
			String comando = " SELECT ENOC.CARRERA(PLAN_ID)||ESTADO AS LLAVE, COALESCE(COUNT(CODIGO_PERSONAL),0) AS VALOR"
					+ " FROM ENOC.CONV_EVENTO "
					+ " WHERE FECHA BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY') AND TIPO IN("+tipo+") "
					+ " GROUP BY ENOC.CARRERA(PLAN_ID), ESTADO";
			Object[] parametros = new Object[] {fechaIni, fechaFin};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa row : lista) {
				mapa.put(row.getLlave(), (String)row.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvEventoDao|mapConvPorFechaTipo|:"+ex);
		}		
		return mapa;
	}
	
	
	public HashMap<String, String> mapExisteConv(String matricula) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CONVALIDACION_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.CONV_MATERIA"
					+ " WHERE CONVALIDACION_ID IN (SELECT CONVALIDACION_ID FROM ENOC.CONV_EVENTO WHERE CODIGO_PERSONAL = ?)"
					+ " GROUP BY CONVALIDACION_ID"; 
			
			Object[] parametros = new Object[] {matricula};
			lista = enocJdbc.query(comando, new aca.MapaMapper(),parametros);

			for(aca.Mapa row : lista) {
				mapa.put(row.getLlave(), (String)row.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvEventoDao|mapExisteConv|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapMateriasEnConvalidacion() {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CONVALIDACION_ID AS LLAVE, COUNT(CONVALIDACION_ID) AS VALOR FROM ENOC.CONV_MATERIA GROUP BY CONVALIDACION_ID";	
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa row : lista) {
				mapa.put(row.getLlave(), row.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvEventoDao|mapMateriasEnConvalidacion|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapMateriasPorEstado() {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CONVALIDACION_ID||ESTADO AS LLAVE, COUNT(CONVALIDACION_ID) AS VALOR FROM ENOC.CONV_MATERIA GROUP BY CONVALIDACION_ID||ESTADO";	
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa row : lista) {
				mapa.put(row.getLlave(), row.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvEventoDao|mapMateriasPorEstado|:"+ex);
		}
		return mapa;
	}
	
}