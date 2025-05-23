// CLASE DE LA TABLA CONV_SOLICITUD
package aca.conva.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ConvMateriaDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( ConvMateria objeto ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CONV_MATERIA"
					+ " (CONVALIDACION_ID, CURSO_ID, FECHA, TIPO, ESTADO, MATERIA_O, CREDITOS_O, NOTA_O, FECHA_NOTA, FOLIO) "
					+ " VALUES( TO_NUMBER(?,'9999999'),?,TO_DATE(?,'DD/MM/YYYY'),?,?,?,TO_NUMBER(?,'99'), ?, TO_DATE(?,'DD/MM/YYYY'), TO_NUMBER(?,'999'))";
			
			Object[] parametros = new Object[] {objeto.getConvalidacionId(),objeto.getCursoId(),objeto.getFecha(),objeto.getTipo(),objeto.getEstado(),objeto.getMateria_O(),
					objeto.getCreditos_O(),objeto.getNota_O(),objeto.getfNota(),objeto.getFolio()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvMateriaDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg( ConvMateria objeto ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CONV_MATERIA "
					+ " SET FECHA = TO_DATE(?,'DD/MM/YYYY'), "
					+ " TIPO = ?, "
					+ " ESTADO = ?, "
					+ " MATERIA_O = ?, "
					+ " CREDITOS_O = TO_NUMBER(?,'99'), "
					+ " NOTA_O = ?, "
					+ " FECHA_NOTA = TO_DATE(?,'DD/MM/YYYY'), FOLIO = TO_NUMBER(?,'999') "
					+ " WHERE CONVALIDACION_ID = TO_NUMBER(?,'9999999') AND CURSO_ID = ?";
			
			Object[] parametros = new Object[] {objeto.getFecha(),objeto.getTipo(),objeto.getEstado(),objeto.getMateria_O(),objeto.getCreditos_O(),objeto.getNota_O(),objeto.getfNota(),
					objeto.getFolio(),objeto.getConvalidacionId(),objeto.getCursoId()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvMateriaDao|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean deleteReg( String convalidacionId, String cursoId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CONV_MATERIA "
					+ " WHERE CONVALIDACION_ID = TO_NUMBER(?,'9999999') "
					+ " AND CURSO_ID = ?";
			
			Object[] parametros = new Object[] {convalidacionId,cursoId};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvMateriaDao|deleteReg|:"+ex);			
		}

		return ok;
	}
	
	public boolean deleteMateriasDeConvalidacion( String convalidacionId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CONV_MATERIA "
					+ " WHERE CONVALIDACION_ID = TO_NUMBER(?,'9999999') ";
				
			Object[] parametros = new Object[] {convalidacionId};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvMateriaDao|deleteMateriasDeConvalidacion|:"+ex);			
		}

		return ok;
	}
	
	public ConvMateria mapeaRegId(  String convalidacionId, String cursoId ) {
		ConvMateria objeto = new ConvMateria();
		
		try{
			String comando = "SELECT CONVALIDACION_ID, CURSO_ID, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, TIPO, ESTADO, MATERIA_O, CREDITOS_O, NOTA_O, TO_CHAR(FECHA_NOTA,'DD/MM/YYYY') AS FECHA_NOTA, FOLIO "
					+ " FROM ENOC.CONV_MATERIA WHERE CONVALIDACION_ID = TO_NUMBER(?,'9999999') AND CURSO_ID = ?";
			
			Object[] parametros = new Object[] {convalidacionId,cursoId};
			objeto = enocJdbc.queryForObject(comando, new ConvMateriaMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvMateriaDao|mapeaRegId|:"+ex);
		}
		
		return objeto;		
	}
	
	public boolean existeReg( String convalidacionId, String cursoId) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CONV_MATERIA "
					+ " WHERE CONVALIDACION_ID = TO_NUMBER(?,'9999999') AND CURSO_ID = ?";
			
			Object[] parametros = new Object[] {convalidacionId,cursoId};

			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){	
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvMateriaDao|existeReg|:"+ex);
		}
		
		return ok;
	}	
	
	public boolean existeConv( String convalidacionId) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CONV_MATERIA WHERE CONVALIDACION_ID = TO_NUMBER(?,'9999999') ";
			Object[] parametros = new Object[] {convalidacionId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){	
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvMateriaDao|existeConv|:"+ex);
		}		
		return ok;
	}
	
	public int getAceptadas( String convalidacionId) {
		int cantidad = 0;
		
		try{
			String comando = "SELECT COUNT(CONVALIDACION_ID) AS MATERIAS"
					+ " FROM ENOC.CONV_MATERIA WHERE CONVALIDACION_ID = ? AND ESTADO = 'S'";
			
			Object[] parametros = new Object[] {convalidacionId};
			cantidad = enocJdbc.queryForObject(comando,Integer.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvMateriaDao|getAceptadas|:"+ex);
		}
		
		return cantidad;
	}

	public int getMatAceptadas( String convalidacionId) {
		int cantidad = 0;
	
		try{
			String comando = "SELECT COUNT(CONVALIDACION_ID) AS MATERIAS"
					+ " FROM ENOC.CONV_MATERIA WHERE CONVALIDACION_ID = ? AND ESTADO = 'S'";
		
			Object[] parametros = new Object[] {convalidacionId};
			cantidad = enocJdbc.queryForObject(comando,Integer.class,parametros);
		
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvMateriaDao|getMatAceptadas|:"+ex);
		}
	
		return cantidad;
	}
	
	public int getRechazadas( String convalidacionId) {
		int cantidad = 0;
		
		try{
			String comando = "SELECT COUNT(CONVALIDACION_ID) AS MATERIAS"
					+ " FROM ENOC.CONV_MATERIA WHERE CONVALIDACION_ID = ? AND ESTADO = 'N'";
			
			Object[] parametros = new Object[] {convalidacionId};
			cantidad = enocJdbc.queryForObject(comando,Integer.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvMateriaDao|getRechazadas|:"+ex);
		}
		
		return cantidad;
	}
	
	public int getMatRechazadas( String convalidacionId) {
		int cantidad = 0;
		
		try{
			String comando = "SELECT COUNT(CONVALIDACION_ID) AS MATERIAS"
					+ " FROM ENOC.CONV_MATERIA WHERE CONVALIDACION_ID = ? AND ESTADO = 'N'";
			
			Object[] parametros = new Object[] {convalidacionId};
			cantidad = enocJdbc.queryForObject(comando,Integer.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvMateriaDao|getMatRechazadas|:"+ex);
		}
		
		return cantidad;
	}	
	
	public String getMaxReg(String convalidacionId) {
 		String maximo	= "1"; 		
 		try{ 			
 			String comando = "SELECT COALESCE(MAX(FOLIO)+1,1) AS MAXIMO FROM ENOC.CONV_MATERIA WHERE CONVALIDACION_ID = TO_NUMBER(?,'9999999')"; 
 			maximo = enocJdbc.queryForObject(comando,String.class,convalidacionId); 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.conva.spring.ConvMateriaDao|getMaxReg|:"+ex);
 		}
 		
 		return maximo;
	}

	public ArrayList<ConvMateria> getListAll( String orden ) {
		List<ConvMateria> lista	= new ArrayList<ConvMateria>();
		
		try{
			String comando = "SELECT CONVALIDACION_ID, CURSO_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, TIPO, ESTADO, MATERIA_O, CREDITOS_O, NOTA_O, TO_CHAR(FECHA_NOTA,'DD/MM/YYYY') AS FECHA_NOTA, FOLIO "
					+ " FROM ENOC.CONV_MATERIA "+orden; 
			
			lista = enocJdbc.query(comando, new ConvMateriaMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvMateriaDao|getListAll|:"+ex);
		}
		
		return (ArrayList<ConvMateria>)lista;
	}
	
	public ArrayList<ConvMateria> getList( String convalidacionId, String orden ) {
		List<ConvMateria> lista		= new ArrayList<ConvMateria>();
		
		try{
			String comando = "SELECT CONVALIDACION_ID, CURSO_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, TIPO, ESTADO, MATERIA_O, CREDITOS_O, NOTA_O, TO_CHAR(FECHA_NOTA,'DD/MM/YYYY') AS FECHA_NOTA, FOLIO "
					+ " FROM ENOC.CONV_MATERIA WHERE CONVALIDACION_ID = ? "+orden; 
			
			Object[] parametros = new Object[] {convalidacionId};
			lista = enocJdbc.query(comando, new ConvMateriaMapper(),parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvMateriaDao|getList|:"+ex);
		}
		
		return (ArrayList<ConvMateria>)lista;
	}
	
	public List<ConvMateria> getListPorEstado(String convalidacionId, String estados, String orden ){		
		List<ConvMateria> lista		= new ArrayList<ConvMateria>();		
		try{
			String comando = "SELECT CONVALIDACION_ID, CURSO_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, TIPO, ESTADO, MATERIA_O, CREDITOS_O, NOTA_O, TO_CHAR(FECHA_NOTA,'DD/MM/YYYY') AS FECHA_NOTA, FOLIO"
				+ " FROM ENOC.CONV_MATERIA WHERE CONVALIDACION_ID = ?"
				+ " AND ESTADO IN ("+estados+") "+orden;			
			Object[] parametros = new Object[] {convalidacionId};
			lista = enocJdbc.query(comando, new ConvMateriaMapper(),parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvMateriaDao|getListPorEstado|:"+ex);
		}
		
		return lista;
	}
	
	public ArrayList<ConvMateria> getListCicloNombre( String convalidacionId, String orden ) {
		List<ConvMateria> lista		= new ArrayList<ConvMateria>();
		
		try{
			String comando = " SELECT (ENOC.CICLO_CURSO(CURSO_ID)||'  '||CURSO_ID) AS CONVALIDACION_ID,"
					+ " CURSO_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA,"
					+ " TIPO, ESTADO, MATERIA_O, COALESCE(CREDITOS_O,0) AS CREDITOS_O,"
					+ " COALESCE(NOTA_O,'0') AS NOTA_O, TO_CHAR(FECHA_NOTA,'DD/MM/YYYY') AS FECHA_NOTA, FOLIO"
					+ " FROM ENOC.CONV_MATERIA"
					+ " WHERE CONVALIDACION_ID = ? "+orden;
			
			Object[] parametros = new Object[] {convalidacionId};
			lista = enocJdbc.query(comando, new ConvMateriaMapper(),parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvMateriaDao|getListCicloNombre|:"+ex);
		}
		
		return (ArrayList<ConvMateria>)lista;
	}

	public int getNumMaterias( String convalidacionId) {
		int cantidad = 0;
		
		try{
			String comando = "SELECT COUNT(CONVALIDACION_ID) AS MATERIAS "
					+ " FROM ENOC.CONV_MATERIA WHERE CONVALIDACION_ID = ?"; 
			
			Object[] parametros = new Object[] {convalidacionId};
			
			cantidad = enocJdbc.queryForObject(comando,Integer.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvMateriaDao|getNumMaterias|:"+ex);
		}
		
		return cantidad;
	}
	
	public ArrayList<ConvMateria> getListResgistradas( String orden ) {
		List<ConvMateria> lista		= new ArrayList<ConvMateria>();

		try{
			String comando = "SELECT A.CONVALIDACION_ID, B.CURSO_ID, TO_CHAR(B.FECHA,'DD/MM/YYYY') AS FECHA, B.TIPO, B.ESTADO, "
					+ " B.MATERIA_O, B.CREDITOS_O, B.NOTA_O, TO_CHAR(B.FECHA_NOTA,'DD/MM/YYYY') AS FECHA_NOTA, B.FOLIO "
					+ " FROM ENOC.CONV_EVENTO A, ENOC.CONV_MATERIA B, ENOC.KRDX_CURSO_IMP C "
					+ " WHERE A.ESTADO = 'T' "
					+ " AND A.CONVALIDACION_ID = B.CONVALIDACION_ID "
					+ " AND B.CURSO_ID = C.CURSO_ID "
					+ " AND A.CODIGO_PERSONAL = C.CODIGO_PERSONAL "
					+ " AND C.TIPOCAL_ID = '1' "
					+ " AND B.ESTADO <> '-' "
					+ " AND B.ESTADO <> 'N' "+orden;
			
			lista = enocJdbc.query(comando, new ConvMateriaMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvMateriaDao|getListRegistradas|:"+ex);
		}
		
		return (ArrayList<ConvMateria>)lista;
	}
	
	public ArrayList<ConvMateria> getListAlumno( String codigoPersonal, String planId, String orden ) {
		List<ConvMateria> lista		= new ArrayList<ConvMateria>();
		
		try{
			String comando = "SELECT CONVALIDACION_ID, CURSO_ID, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, "
					+ " TIPO, ESTADO, MATERIA_O, CREDITOS_O, NOTA_O, TO_CHAR(FECHA_NOTA,'DD/MM/YYYY') AS FECHA_NOTA, FOLIO FROM ENOC.CONV_MATERIA "
					+ " WHERE CONVALIDACION_ID IN (SELECT CONVALIDACION_ID FROM ENOC.CONV_EVENTO "
					+ " WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?)"+orden;
			
			Object[] parametros = new Object[] {codigoPersonal,planId};
			lista = enocJdbc.query(comando, new ConvMateriaMapper(),parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvMateriaDao|getListAlumno|:"+ex);
		}
		
		return (ArrayList<ConvMateria>)lista;
	}
	
	public ArrayList<ConvMateria> getListAlumno( String codigoPersonal, String orden ) {
		List<ConvMateria> lista		= new ArrayList<ConvMateria>();
		
		try{
			String comando = "SELECT CONVALIDACION_ID, CURSO_ID, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, "
					+ " TIPO, ESTADO, MATERIA_O, CREDITOS_O, NOTA_O, TO_CHAR(FECHA_NOTA,'DD/MM/YYYY') AS FECHA_NOTA, FOLIO"
					+ " FROM ENOC.CONV_MATERIA "
					+ " WHERE CONVALIDACION_ID IN (SELECT CONVALIDACION_ID FROM ENOC.CONV_EVENTO WHERE CODIGO_PERSONAL = ?) "+orden;			
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new ConvMateriaMapper(),parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvMateriaDao|getListAlumno|:"+ex);
		}
		
		return (ArrayList<ConvMateria>)lista;
	}
	
	public HashMap<String, ConvMateria> mapPorConvIdSolicitud( String convalidacionId) {
		HashMap<String, ConvMateria> mapa = new HashMap<String, ConvMateria>();
		List<ConvMateria> lista			 = new ArrayList<ConvMateria>();
		
		try{
			String comando = " SELECT CONVALIDACION_ID, CURSO_ID, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, "
					+ " TIPO, ESTADO, MATERIA_O, CREDITOS_O, NOTA_O, TO_CHAR(FECHA_NOTA,'DD/MM/YYYY') AS FECHA_NOTA, FOLIO "
					+ " FROM ENOC.CONV_MATERIA WHERE CONVALIDACION_ID = ? "; 
			
			Object[] parametros = new Object[] {convalidacionId};
			lista = enocJdbc.query(comando, new ConvMateriaMapper(),parametros);

			for(ConvMateria materia: lista) {
				mapa.put(materia.getCursoId(), materia);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvMateriaDao|mapPorConvIdSolicitud|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, ConvMateria> mapPorConvIdMatOrigen( String convalidacionId) {
		HashMap<String, ConvMateria> mapa = new HashMap<String, ConvMateria>();
		List<ConvMateria> lista			 = new ArrayList<ConvMateria>();
		
		try{
			String comando = " SELECT CONVALIDACION_ID, CURSO_ID, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, "
					+ " TIPO, ESTADO, MATERIA_O, CREDITOS_O, NOTA_O, TO_CHAR(FECHA_NOTA,'DD/MM/YYYY') AS FECHA_NOTA, FOLIO "
					+ " FROM ENOC.CONV_MATERIA WHERE CONVALIDACION_ID = ?"; 
			
			Object[] parametros = new Object[] {convalidacionId};
			lista = enocJdbc.query(comando, new ConvMateriaMapper(),parametros);
			
			for(ConvMateria materia: lista) {
				mapa.put(materia.getMateria_O(), materia);
			}

		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvMateriaDao|mapPorConvIdMatOrigen|:"+ex);
		}

		return mapa;
	}
	
	public HashMap<String, String> mapaMatConv(String codigoPersonal, String estado){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();		
		try{
			String comando = " SELECT A.CURSO_ID AS LLAVE, B.TIPO AS VALOR FROM CONV_MATERIA A, CONV_EVENTO B"
					+ " WHERE B.CONVALIDACION_ID = A.CONVALIDACION_ID "
					+ " AND B.CODIGO_PERSONAL = ? "
					+ " AND B.ESTADO IN ("+estado+")"; 
			
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa row : lista) {
				mapa.put(row.getLlave(), (String)row.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvMateriaDao|mapaMatConv|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaMateriasPorConvalidacion(String codigoPersonal, String estado){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();		
		try{
			String comando = " SELECT CONVALIDACION_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.CONV_MATERIA"
					+ " WHERE CONVALIDACION_ID IN (SELECT CONVALIDACION_ID FROM ENOC.CONV_EVENTO WHERE CODIGO_PERSONAL = ?)"
					+ " AND ESTADO IN("+estado+")"
					+ " GROUP BY CONVALIDACION_ID";
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa row : lista) {
				mapa.put(row.getLlave(), (String)row.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvMateriaDao|mapaMateriasPorConvalidacion|:"+ex);
		}
		
		return mapa;
	}
	
	/* ESTADOS_CONV 'S','X',   ESTADOS_MAT '-','R','S' */
	public HashMap<String, ConvMateria> mapaMatConv(String codigoPersonal, String estadosConv, String estadosMat) {
		HashMap<String, ConvMateria> mapa = new HashMap<String, ConvMateria>();
		List<ConvMateria> lista 		= new ArrayList<ConvMateria>();
		
		try{
			String comando = "SELECT CONVALIDACION_ID, CURSO_ID, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA,"
						+ " TIPO, ESTADO, MATERIA_O, CREDITOS_O, NOTA_O, TO_CHAR(FECHA_NOTA,'DD/MM/YYYY') AS FECHA_NOTA, FOLIO"
						+ " FROM ENOC.CONV_MATERIA"
						+ " WHERE CONVALIDACION_ID IN (SELECT CONVALIDACION_ID FROM ENOC.CONV_EVENTO WHERE CODIGO_PERSONAL = ? AND ESTADO NOT IN ("+estadosConv+"))"
						+ " AND ESTADO IN ("+estadosMat+")"; 
			
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new ConvMateriaMapper(), parametros);
			for(ConvMateria materia : lista) {
				mapa.put(materia.getCursoId(), materia);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvMateriaDao|mapaMatConv|:"+ex);
		}
		
		return mapa;
	}
	
	/* 'C','A','T','R' */
	public HashMap<String,String> mapaCursosConvalidados(String codigoPersonal, String estados) {
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String,String>();
		try{
			String comando = "SELECT CURSO_ID AS LLAVE, ESTADO AS VALOR FROM ENOC.CONV_MATERIA"
					+ " WHERE CONVALIDACION_ID IN (SELECT CONVALIDACION_ID FROM ENOC.CONV_EVENTO WHERE CODIGO_PERSONAL = ? AND ESTADO IN ("+estados+"))"
					+ " AND ESTADO != 'N'";
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map:lista){		
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|mapaCursosConvalidados|:"+ex);
		}
		return mapa;
	}
	
}