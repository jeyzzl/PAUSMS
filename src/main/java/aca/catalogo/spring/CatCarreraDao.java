//Clase  para la tabla Materias_Insc
package aca.catalogo.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CatCarreraDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CatCarrera carrera) {
		boolean ok = false;
		
		try{			 
			String comando = "INSERT INTO ENOC.CAT_CARRERA"
					+ " (FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA, NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL, GLSETNO, COSTCENTCD, DSCNTPCT)"
					+ " VALUES( ?, ?, TO_NUMBER(?,'99') , ? , ?, ?, ?, ?, ?, ?, ?)";
			Object[] parametros = new Object[] {carrera.getFacultadId(),carrera.getCarreraId(),carrera.getNivelId(),
					carrera.getTitulo(),carrera.getNombreCarrera(),carrera.getNombreCorto(),carrera.getCcostoId(),
					carrera.getCodigoPersonal(), carrera.getGlsetno(), carrera.getCostcentcd(), carrera.getDscntpct()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|insertReg|:"+ex);
		}
		
		return ok;
	}	
	
	public boolean updateReg( CatCarrera carrera ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CAT_CARRERA "+ 
				"SET FACULTAD_ID = ?, NIVEL_ID = TO_NUMBER(?,'99'), TITULO = ?, "+
				"NOMBRE_CARRERA = ?, NOMBRE_CORTO = ?, CCOSTO_ID = ?, "+
				"CODIGO_PERSONAL = ?, GLSETNO = ?, COSTCENTCD = ?, DSCNTPCT = ? "+
				"WHERE CARRERA_ID = ?";
			Object[] parametros = new Object[] {carrera.getFacultadId(),carrera.getNivelId(),
					carrera.getTitulo(),carrera.getNombreCarrera(),carrera.getNombreCorto(),carrera.getCcostoId(),
					carrera.getCodigoPersonal(), carrera.getGlsetno(), carrera.getCostcentcd(), carrera.getDscntpct(), carrera.getCarreraId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	
	public boolean deleteReg( String carreraId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = ?";
			Object[] parametros = new Object[] {carreraId};
			if (enocJdbc.update(comando, parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|deleteReg|:"+ex);	
		}
		
		return ok;
	}
	
	public CatCarrera mapeaRegId( String facultadId, String carreraId) {		
		
		CatCarrera carrera		= new CatCarrera();		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_CARRERA WHERE FACULTAD_ID = ? AND CARRERA_ID = ?";
			Object[] parametros = new Object[] {facultadId, carreraId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >=1 ){
				comando = "SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA, NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL, GLSETNO, COSTCENTCD, DSCNTPCT"
					+ " FROM ENOC.CAT_CARRERA WHERE FACULTAD_ID = ? AND CARRERA_ID = ?";					
				carrera = enocJdbc.queryForObject(comando, new CatCarreraMapper(), parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}		
		return carrera;
	}
	
	public CatCarrera mapeaRegId( String carreraId) {		
		CatCarrera carrera		= new CatCarrera();		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = ?";
			Object[] parametros = new Object[] {carreraId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >=1 ){
				comando = " SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA, NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL, GLSETNO, COSTCENTCD, DSCNTPCT"
						+ " FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = ?";				
				carrera = enocJdbc.queryForObject(comando, new CatCarreraMapper(), parametros);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}		
		return carrera;
	}
	
	public CatCarrera mapeaRegIdsinFac( String carreraId) {
		
		CatCarrera carrera		= new CatCarrera();		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = ?";
			Object[] parametros = new Object[] {carreraId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >=1 ){
				comando = "SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA,"
						+ " NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL, GLSETNO, COSTCENTCD, DSCNTPCT"
						+ " FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = ?";			
				carrera = enocJdbc.queryForObject(comando, new CatCarreraMapper(), parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|mapeaRegIdsinFac|:"+ex);
			ex.printStackTrace();
		}
		
		return carrera;
	}
	
	public boolean existeReg( String carreraId) {
		
		boolean 		ok 	= false;	
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = ?";
			Object[] parametros = new Object[] {carreraId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >=1 ){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg( String facultadId ) {
		
		String maximo			= "1";		
		try{
			String comando = "SELECT COLESCE(MAX(CARRERA_ID)+1,1) MAXIMO FROM ENOC.CAT_CARRERA WHERE FACULTAD_ID = ?";
			Object[] parametros = new Object[] {facultadId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >=1){
				maximo = String.valueOf(enocJdbc.queryForObject(comando, Integer.class, parametros));
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public String getNombreCarrera( String carreraId ) {	
		String nombre = "-";
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = ?";
			Object[] parametros = new Object[] {carreraId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT NOMBRE_CARRERA FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = ?";			
				nombre = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|getNombreCarrera|:"+ex);
		}
		
		return nombre;
	}
	
	public String getCarreraNivel(String carreraId) {	
		String nivel = "0";
		try{
			String comando = "SELECT NIVEL_ID FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = ? ";
			Object[] parametros = new Object[] {carreraId};
			nivel = enocJdbc.queryForObject(comando, String.class, parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|getCarreraNivel|:"+ex);
		}
		
		return nivel;
	}
	
	public String getNombreCorto( String carreraId ) {	
	
		String nombre			= " ";
		
		try{
			String comando = "SELECT NOMBRE_CORTO FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = ?"; 
			Object[] parametros = new Object[] {carreraId};
			nombre = enocJdbc.queryForObject(comando, String.class, parametros);			
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|getNombreCorto|:"+ex);
		}
		
		return nombre;
	}
	
	public String getFacultadId( String carreraId ) {	
		
		String facultadId		= "";
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = ?";
			Object[] parametros = new Object[] {carreraId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT FACULTAD_ID FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = ?";
				facultadId = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|getFacultadId|:"+ex);
		}		
		return facultadId;
	}
	
	public boolean esCoordinador( String codigoPersonal ) {	
	
		boolean ok 				= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_CARRERA WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >=1 ){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|esCoodinador|:"+ex);
		}		
		return ok;
	}
	
	public String getCoordinador( String carreraId) {
		
		String coordinadorId		= "";		
		try{
			String comando = "SELECT CODIGO_PERSONAL FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = ?";
			Object[] parametros = new Object[] {carreraId};
			coordinadorId = enocJdbc.queryForObject(comando, String.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|getCoordinador|:"+ex);
		}		
		return coordinadorId;
	}
	
	public String getNivelId( String carreraId) {
		
		String nivel				= "0";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = ?";
			Object[] parametros = new Object[] {carreraId};			
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				comando = "SELECT COALESCE(NIVEL_ID,0) AS NIVEL FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = ?";			
				nivel = enocJdbc.queryForObject(comando, String.class, parametros);
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|getNivel|:"+ex);
		}		
		return nivel;
	}
	
	public String getCarrerasCoordinador( String codigoPersonal) {
		
		List<String> lista	= new ArrayList<String>();
		String carreras		= "";		
		int row				= 0;
		try{
			String comando = "SELECT CARRERA_ID FROM ENOC.CAT_CARRERA WHERE CODIGO_PERSONAL = ?"; 
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.queryForList(comando, String.class, parametros);			
			row=1;
			for (String carrera : lista){
				if (row==1){
					carreras += "'"+carrera+"'";
				}else{
					carreras += ",'"+carrera+"'";
				}		
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|getCarrerasCoordinador|:"+ex);
		}		
		return carreras;
	}
	
	public List<CatCarrera> getListAll( String orden) {
		
		List<CatCarrera> lista		= new ArrayList<CatCarrera>();		
		try{
			String comando = "SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA, NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL, GLSETNO, COSTCENTCD, DSCNTPCT" +
					" FROM ENOC.CAT_CARRERA "+orden;
			lista = enocJdbc.query(comando, new CatCarreraMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|getListAll|:"+ex);
		}		
		return lista;
	}
	
	public List<CatCarrera> lisCarrerasVigentes( String orden) {		
		List<CatCarrera> lista		= new ArrayList<CatCarrera>();
		String yearActual 			= aca.util.Fecha.getHoyReversa().substring(0,4);
		String yearAnterior			= String.valueOf(Integer.parseInt(yearActual)-1);		
		try{
			String comando = "SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA, NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL, GLSETNO, COSTCENTCD, DSCNTPCT FROM ENOC.CAT_CARRERA"
					+ " WHERE CARRERA_ID IN "
					+ "	( SELECT DISTINCT(CARRERA_ID) FROM ENOC.CARGA_GRUPO "
					+ "		WHERE TO_CHAR(F_INICIO,'YYYY') = ? "
					+ " 	OR TO_CHAR(F_FINAL,'YYYY') = ?"
					+ " 	OR TO_CHAR(F_INICIO,'YYYY') = ? "
					+ "		OR TO_CHAR(F_FINAL,'YYYY') = ?"
					+ " ) "+orden;
			lista = enocJdbc.query(comando, new CatCarreraMapper(), yearAnterior, yearAnterior, yearActual, yearActual);			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|getListAll|:"+ex);
		}		
		return lista;
	}
	
	public List<CatCarrera> lisVigentesPorFacultad( String facultadId, String orden ) {
		List<CatCarrera> lista	= new ArrayList<CatCarrera>();
		String yearActual 			= aca.util.Fecha.getHoyReversa().substring(0,4);
		String yearAnterior			= String.valueOf(Integer.parseInt(yearActual)-1);
		try{
			String comando = " SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA, NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL, GLSETNO, COSTCENTCD, DSCNTPCT"
					+ " FROM ENOC.CAT_CARRERA "
					+ " WHERE FACULTAD_ID = ? "
					+ " AND CARRERA_ID IN "
					+ "(SELECT DISTINCT(CARRERA_ID) FROM ENOC.CARGA_GRUPO "
					+ "	WHERE TO_CHAR(F_INICIO,'YYYY') = ? "
					+ " OR TO_CHAR(F_FINAL,'YYYY') = ?"
					+ " OR TO_CHAR(F_INICIO,'YYYY') = ? "
					+ " OR TO_CHAR(F_FINAL,'YYYY') = ?"
					+ ") "+ orden;
			Object[] parametros = new Object[] {facultadId, yearAnterior, yearAnterior, yearActual, yearActual};
			lista = enocJdbc.query(comando, new CatCarreraMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|getLista|:"+ex);
		}
		
		return lista;
	}
	
	public List<CatCarrera> lisConPlanes( String estados, String orden) {
		
		List<CatCarrera> lista		= new ArrayList<CatCarrera>();		
		try{
			String comando = "SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA, NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL, GLSETNO, COSTCENTCD, DSCNTPCT"
					+ " FROM ENOC.CAT_CARRERA "
					+ " WHERE CARRERA_ID IN (SELECT DISTINCT(CARRERA_ID) FROM ENOC.MAPA_PLAN WHERE ESTADO IN ("+estados+")) "+orden;
			lista = enocJdbc.query(comando, new CatCarreraMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|lisConPlanes|:"+ex);
		}		
		return lista;
	}
	
	public List<aca.Mapa> lisPlanesAlumno(String matricula ){		
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();	
		try{
			String comando = "SELECT PLAN_ID AS LLAVE, CARRERA_SE AS VALOR FROM ENOC.MAPA_PLAN WHERE PLAN_ID IN (SELECT PLAN_ID FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = ?)";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), matricula);			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|getPlanAlumno|:"+ex);
		}
		
		return lista;
	}
	public List<CatCarrera> lisConPlanesPorFacultad( String estados, String facultadId, String orden) {
		
		List<CatCarrera> lista		= new ArrayList<CatCarrera>();		
		try{
			String comando = "SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA, NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL, GLSETNO, COSTCENTCD, DSCNTPCT"
					+ " FROM ENOC.CAT_CARRERA "
					+ " WHERE CARRERA_ID IN (SELECT DISTINCT(CARRERA_ID) FROM ENOC.MAPA_PLAN WHERE ESTADO IN ("+estados+")) "
					+ " AND FACULTAD_ID = ? "+orden;
			lista = enocJdbc.query(comando, new CatCarreraMapper(), facultadId);			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|lisConPlanesPorFacultad|:"+ex);
		}		
		return lista;
	}
	
	public List<aca.Mapa> lisPlanesPorFacultad(String facultadId, String orden ){		
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT PLAN_ID AS LLAVE, CARRERA_SE AS VALOR FROM ENOC.MAPA_PLAN "
					+ " WHERE CARRERA_ID IN(SELECT CARRERA_ID FROM ENOC.CAT_CARRERA WHERE FACULTAD_ID = ?) "+ orden;		
			lista = enocJdbc.query(comando, new aca.MapaMapper(), facultadId);			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|lisPlanesPorFacultad|:"+ex);
		}		
		return lista;
	}
	
	public List<CatCarrera> listCarrerasConGrupo( String grupo, String orden) {	
		List<CatCarrera> lista		= new ArrayList<CatCarrera>();		
		try{
			String comando = "SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA, NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL, GLSETNO, COSTCENTCD, DSCNTPCT"
				+ " FROM ENOC.CAT_CARRERA WHERE CARRERA_ID IN (SELECT CARRERA FROM ENOC.ARCH_GRUPOS_CARRERA WHERE REPLACE(GRUPOS,' ','-') LIKE '%-"+grupo+"-%') "+orden;			
			lista = enocJdbc.query(comando, new CatCarreraMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|ListCarrerasConMentores|:"+ex);
		}		
		return lista;
	}

	public List<CatCarrera> lisCarrerasEnFacultad(String cargaId, String facultadId, String orden) {	
		List<CatCarrera> lista		= new ArrayList<CatCarrera>();		
		try{
			String comando = "SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA, NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL, GLSETNO, COSTCENTCD, DSCNTPCT"
					+ " FROM ENOC.CAT_CARRERA WHERE FACULTAD_ID = ? AND CARRERA_ID IN (SELECT CARRERA_ID FROM ENOC.ALUM_ESTADO WHERE CARGA_ID = ? AND ESTADO = 'I') "+orden;			
			Object[] parametros = new Object[] {facultadId, cargaId};			
			lista = enocJdbc.query(comando, new CatCarreraMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|lisCarrerasEnFacultad|:"+ex);
		}		
		return lista;
	}
	
	public List<CatCarrera> listCarrerasSinGrupo( String grupo, String orden) {	
		List<CatCarrera> lista		= new ArrayList<CatCarrera>();		
		try{
			String comando = "SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA, NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL, GLSETNO, COSTCENTCD, DSCNTPCT"
				+ " FROM ENOC.CAT_CARRERA WHERE CARRERA_ID NOT IN (SELECT CARRERA FROM ENOC.ARCH_GRUPOS_CARRERA WHERE REPLACE(GRUPOS,' ','-') LIKE '%-"+grupo+"-%') "+orden;
			lista = enocJdbc.query(comando, new CatCarreraMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|ListCarrerasConMentores|:"+ex);
		}		
		return lista;
	}
	
	public List<CatCarrera> ListCarrerasConMentores( String periodoId, String orden) {	
		List<CatCarrera> lista		= new ArrayList<CatCarrera>();		
		try{
			String comando = "SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA, NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL, GLSETNO, COSTCENTCD, DSCNTPCT"
					+ " FROM ENOC.CAT_CARRERA WHERE CARRERA_ID IN (SELECT CARRERA_ID FROM MENT_CARRERA WHERE PERIODO_ID = ?) "+orden;
			Object[] parametros = new Object[] {periodoId};
			lista = enocJdbc.query(comando, new CatCarreraMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|ListCarrerasConMentores|:"+ex);
		}		
		return lista;
	}
	
	public List<CatCarrera> lisEnExternos( String orden) {
		
		List<CatCarrera> lista		= new ArrayList<CatCarrera>();		
		
		try{
			String comando = "SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA, NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL, GLSETNO, COSTCENTCD, DSCNTPCT"
					+ " FROM ENOC.CAT_CARRERA "
					+ " WHERE CARRERA_ID IN (SELECT DISTINCT(CARRERA_ID) FROM ENOC.ESTEXTERNOS) "+orden;
			lista = enocJdbc.query(comando, new CatCarreraMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public List<CatCarrera> getLista( String facultadId, String orden ) {
		
		List<CatCarrera> lista	= new ArrayList<CatCarrera>();
		
		try{
			String comando = " SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO,"
					+ " NOMBRE_CARRERA, NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL,"
					+ " GLSETNO, COSTCENTCD, DSCNTPCT"
					+ " FROM ENOC.CAT_CARRERA WHERE FACULTAD_ID = ? "+ orden;
			Object[] parametros = new Object[] {facultadId};
			lista = enocJdbc.query(comando, new CatCarreraMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|getLista|:"+ex);
		}
		
		return lista;
	}
	
	public List<CatCarrera> getListaEnCarga( String cargaId, String orden ) {
		
		List<CatCarrera> lista	= new ArrayList<CatCarrera>();
		
		try{
			String comando = " SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO,"
					+ " NOMBRE_CARRERA, NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL, GLSETNO, COSTCENTCD, DSCNTPCT"
					+ " FROM ENOC.CAT_CARRERA WHERE CARRERA_ID IN (SELECT CARRERA_ID FROM CARGA_GRUPO WHERE CARGA_ID = ?) "+ orden;
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new CatCarreraMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|getListaEnCarga|:"+ex);
		}
		
		return lista;
	}
	
	public List<CatCarrera> lisFacultadyCarga( String facultadId, String cargaId, String orden ) {
		
		List<CatCarrera> lista	= new ArrayList<CatCarrera>();
		
		try{
			String comando = " SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA, NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL, GLSETNO, COSTCENTCD, DSCNTPCT"
					+ " FROM ENOC.CAT_CARRERA"
					+ " WHERE FACULTAD_ID = ? "
					+ " AND CARRERA_ID IN (SELECT CARRERA_ID FROM CARGA_GRUPO WHERE CARGA_ID = ?) "+ orden;
			Object[] parametros = new Object[] {facultadId, cargaId};
			lista = enocJdbc.query(comando, new CatCarreraMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|lisFacultadyCarga|:"+ex);
		}
		
		return lista;
	}

	public List<CatCarrera> lisExtranjerosEnCarga( String cargaId, String orden ){
		
		List<CatCarrera> lista	= new ArrayList<CatCarrera>();		
		try{
			String comando = " SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA, NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL, GLSETNO, COSTCENTCD, DSCNTPCT"
					+ " FROM ENOC.CAT_CARRERA"
					+ " WHERE CARRERA_ID IN (SELECT CARRERA_ID FROM ENOC.ESTADISTICA WHERE CARGA_ID = ? AND NACIONALIDAD != 91) "+ orden;
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new CatCarreraMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|lisExtranjerosEnCarga|:"+ex);
		}
		
		return lista;
	}
	
	public List<CatCarrera> getListaEnEstadistica( String cargaId, String orden ) {
		
		List<CatCarrera> lista	= new ArrayList<CatCarrera>();
		
		try{
			String comando = " SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO,"
					+ " NOMBRE_CARRERA, NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL, GLSETNO, COSTCENTCD, DSCNTPCT"
					+ " FROM ENOC.CAT_CARRERA WHERE CARRERA_ID IN (SELECT CARRERA_ID FROM ENOC.ESTADISTICA WHERE CARGA_ID = ?) "+ orden;
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new CatCarreraMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|getListaEnCarga|:"+ex);
		}
		
		return lista;
	}
	
	public List<CatCarrera> lisEnGraduacion( String eventoId, String orden ) {
		List<CatCarrera> lista	= new ArrayList<CatCarrera>();		
		try{
			String comando = " SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO,"
					+ " NOMBRE_CARRERA, NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL, GLSETNO, COSTCENTCD, DSCNTPCT"
					+ " FROM ENOC.CAT_CARRERA WHERE CARRERA_ID IN (SELECT CARRERA(PLAN_ID) FROM ENOC.ALUM_EGRESO WHERE EVENTO_ID = TO_NUMBER(?, '999')) "+ orden;
			Object[] parametros = new Object[] {eventoId};
			lista = enocJdbc.query(comando, new CatCarreraMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|lisEnGraduacion|:"+ex);
		}
		
		return lista;
	}
	
	public List<CatCarrera> lisEnEstadistica( String fechaIni, String fechaFin, String orden ) {
		List<CatCarrera> lista	= new ArrayList<CatCarrera>();		
		try{
			String comando = " SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA, NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL, GLSETNO, COSTCENTCD, DSCNTPCT"
					+ " FROM ENOC.CAT_CARRERA WHERE CARRERA_ID IN (SELECT CARRERA_ID FROM ESTADISTICA WHERE F_INSCRIPCION BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY') AND ESTADO = 'I') "+ orden;
			Object[] parametros = new Object[] {fechaIni, fechaFin};
			lista = enocJdbc.query(comando, new CatCarreraMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|lisEnEstadistica|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,CatCarrera> getMapAll( String orden ) {
		
		HashMap<String,CatCarrera> mapa = new HashMap<String,CatCarrera>();
		List<CatCarrera> lista	= new ArrayList<CatCarrera>();
		
		try{
			String comando = "SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, "+
				"NOMBRE_CARRERA, NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL, GLSETNO, COSTCENTCD, DSCNTPCT "+
				"FROM ENOC.CAT_CARRERA "+ orden;
			lista = enocJdbc.query(comando, new CatCarreraMapper());
			for (CatCarrera carrera : lista ) {
				mapa.put(carrera.getCarreraId(), carrera );
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|getMapAll|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaCarreraApt() {
		
		HashMap<String, String> map			= new HashMap<String,String>();
		List<aca.Mapa> list 				= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CARRERA_ID AS LLAVE, NOMBRE_CARRERA AS VALOR FROM ENOC.CAT_CARRERA";
			
			list = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa edad : list ) {
				map.put(edad.getLlave(), edad.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoDao|mapaCarreraApt|:"+ex);
		}		
		return map;
	}
	
	public HashMap<String,String> getMapNombre() {
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CARRERA_ID AS LLAVE, NOMBRE_CORTO AS VALOR"+
				" FROM ENOC.CAT_CARRERA ";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			
			for (aca.Mapa map: lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|getMapNombre|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapEmpresa() {
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<CatCarrera> lista		= new ArrayList<CatCarrera>();
		
		try{
			String comando = "SELECT CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA, NOMBRE_CORTO,"
					+ " SUBSTR(COALESCE(TRIM(CCOSTO_ID),'1.01'),1,4) AS CCOSTO_ID, CODIGO_PERSONAL,"
					+"  GLSETNO, COSTCENTCD, DSCNTPCT"
					+ " FROM ENOC.CAT_CARRERA WHERE CCOSTO_ID IS NOT NULL";
			lista = enocJdbc.query(comando, new CatCarreraMapper());
			for (CatCarrera carrera : lista ) {
				mapa.put(carrera.getCarreraId(), carrera.getCcostoId());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|mapEmpresa|:"+ex);
		}
		
		return mapa;
	}
	
	public List<CatCarrera> getListCarga( String cargaId, String orden) {
		
		List<CatCarrera> lista		= new ArrayList<CatCarrera>();
		
		try{
			String comando = "SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA,"
					+ " NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL, GLSETNO, COSTCENTCD, DSCNTPCT"
					+ " FROM ENOC.CAT_CARRERA"
					+ " WHERE CARRERA_ID IN (SELECT CARRERA_ID FROM ENOC.CARGA_PERMISO_PLAN WHERE CARGA_ID = ?) "+orden;	
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new CatCarreraMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|getListCarga|:"+ex);
		}
		
		return lista;
	}
	
	public List<CatCarrera> lisEnCarga( String cargaId, String orden) {		
		List<CatCarrera> lista		= new ArrayList<CatCarrera>();		
		try{
			String comando = "SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA,"
					+ " NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL, GLSETNO, COSTCENTCD, DSCNTPCT"
					+ " FROM ENOC.CAT_CARRERA"
					+ " WHERE CARRERA_ID IN (SELECT CARRERA_ID FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ?) "+orden;	
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new CatCarreraMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|lisEnCarga|:"+ex);
		}
		
		return lista;
	}
	
	public List<CatCarrera> getListCarrera( String facultadId, String orden) {
		
		List<CatCarrera> lista		= new ArrayList<CatCarrera>();
		
		try{
			String comando = "SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA,"
					+ " NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL, GLSETNO, COSTCENTCD, DSCNTPCT"
					+ " FROM ENOC.CAT_CARRERA WHERE FACULTAD_ID = ? "+orden;
			Object[] parametros = new Object[] {facultadId};
			lista = enocJdbc.query(comando, new CatCarreraMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|getListCarrera|:"+ex);
		}
		
		return lista;
	}
	
	public List<CatCarrera> listCarreraConMentor( String periodoId, String fecha, String orden) {
		
		List<CatCarrera> lista		= new ArrayList<CatCarrera>();
		
		try{
			String comando = " SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA, NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL, GLSETNO, COSTCENTCD, DSCNTPCT"
					+ " FROM ENOC.CAT_CARRERA"
					+ " WHERE CARRERA_ID IN"
					+ " 	(SELECT CARRERA_ID FROM ENOC.MENT_CARRERA WHERE PERIODO_ID = ?"
					+ "		AND TO_DATE(?,'DD/MM/YYYY') BETWEEN FECHA_INICIO AND FECHA_FINAL)) "+orden;
			Object[] parametros = new Object[] {periodoId, fecha};
			lista = enocJdbc.query(comando, new CatCarreraMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|listCarreraConMentor|:"+ex);
		}
		
		return lista;
	}
	
	public List<CatCarrera> getListAutorizadas( String codigoPersonal, String orden) {
		List<CatCarrera> lista		= new ArrayList<CatCarrera>();		
		try{
			String comando = "SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA, NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL, GLSETNO, COSTCENTCD, DSCNTPCT"
					+ " FROM ENOC.CAT_CARRERA"
					+ " WHERE (SELECT ACCESOS FROM ENOC.ACCESO WHERE CODIGO_PERSONAL = ?) LIKE '%'||CARRERA_ID||'%' "+orden;
			lista = enocJdbc.query(comando, new CatCarreraMapper(), codigoPersonal);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|getListAutorizadas|:"+ex);
		}		
		return lista;
	}
	/**/
	public List<CatCarrera> getListAutorizadasyEstadistica( String codigoPersonal, String cargaId, String orden) {
		List<CatCarrera> lista		= new ArrayList<CatCarrera>();
		
		try{
			String comando = "SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA, NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL, GLSETNO, COSTCENTCD, DSCNTPCT"
					+ " FROM ENOC.CAT_CARRERA"
					+ " WHERE (SELECT ACCESOS FROM ENOC.ACCESO WHERE CODIGO_PERSONAL = ?') LIKE '%'||CARRERA_ID||'%'"
					+ " AND CARRERA_ID IN (SELECT CARRERA_ID FROM ENOC.ESTADISTICA WHERE CARGA_ID = ?) "+orden;
			Object[] parametros = new Object[] {codigoPersonal, cargaId};
			lista = enocJdbc.query(comando, new CatCarreraMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|getListAutorizadas|:"+ex);
		}
		
		return lista;
	}
	
	public List<CatCarrera> lisConHoras( String cargaId, String orden) {
		List<CatCarrera> lista		= new ArrayList<CatCarrera>();
		
		try{
			String comando = "SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA, NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL, GLSETNO, COSTCENTCD, DSCNTPCT"
					+ " FROM ENOC.CAT_CARRERA"
					+ " WHERE CARRERA_ID IN (SELECT CARRERA_ID FROM ENOC.EMP_HORAS WHERE CARGA_ID = ?) "+orden;
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new CatCarreraMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|lisConHoras|:"+ex);
		}
		
		return lista;
	}
	
	public List<CatCarrera> lisEnBecas( String ejercicioId, String tipo, String orden) {
		List<CatCarrera> lista		= new ArrayList<CatCarrera>();		
		try{
			String comando = "SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA, NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL, GLSETNO, COSTCENTCD, DSCNTPCT"
					+ " FROM ENOC.CAT_CARRERA"
					+ " WHERE CARRERA_ID IN (SELECT CARRERA_ID FROM ENOC.BEC_TIPO_CARRERA WHERE ID_EJERCICIO = ? AND TIPO = ?) "+orden;
			Object[] parametros = new Object[] {ejercicioId, tipo};
			lista = enocJdbc.query(comando, new CatCarreraMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|lisEnBecas|:"+ex);
		}
		
		return lista;
	}
	
	public List<CatCarrera> lisEnBecas( String ejercicioId, String tipo, String facultadId, String orden) {
		List<CatCarrera> lista		= new ArrayList<CatCarrera>();		
		try{
			String comando = "SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA, NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL, GLSETNO, COSTCENTCD, DSCNTPCT"
					+ " FROM ENOC.CAT_CARRERA"
					+ " WHERE FACULTAD_ID = ?"
					+ " AND CARRERA_ID IN (SELECT CARRERA_ID FROM ENOC.BEC_TIPO_CARRERA WHERE ID_EJERCICIO = ? AND TIPO = ?) "+orden;
			Object[] parametros = new Object[] {facultadId, ejercicioId, tipo};
			lista = enocJdbc.query(comando, new CatCarreraMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|lisEnBecas|:"+ex);
		}
		
		return lista;
	}
	
	public List<CatCarrera> lisEnCargas( String cargaId, String orden) {
		List<CatCarrera> lista		= new ArrayList<CatCarrera>();		
		try{
			String comando = "SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA, NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL, GLSETNO, COSTCENTCD, DSCNTPCT"
					+ " FROM ENOC.CAT_CARRERA"
					+ " WHERE CARRERA_ID IN (SELECT DISTINCT(CARRERA_ID) FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ?) "+orden;
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new CatCarreraMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|getListAutorizadas|:"+ex);
		}		
		return lista;
	}
	
	public List<CatCarrera> getListConReprobados( String cargaId, String orden) {
		List<CatCarrera> lista		= new ArrayList<CatCarrera>();		
		try{
			String comando = "SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA, NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL, GLSETNO, COSTCENTCD, DSCNTPCT"
				+ " FROM ENOC.CAT_CARRERA"
				+ " WHERE CARRERA_ID IN (SELECT ENOC.CARRERA(ENOC.CURSO_PLAN(CURSO_ID))"
				+ " 	FROM ENOC.KRDX_CURSO_ACT"
				+ " 	WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ?"
				+ " 	AND ENOC.TIPOCURSO_ID(CURSO_ID) NOT IN ('4','6')) "+orden;
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new CatCarreraMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|getListConReprobados|:"+ex);
		}		
		return lista;
	}
	
	// Lista de carreras con permiso en una carga academica
	public List<CatCarrera> getListCargaCarrera(String facultad, String cargaId, String orden) {
		
		List<CatCarrera> lista		= new ArrayList<CatCarrera>();
		
		try{
			String comando = " SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA, " +
					" NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL, GLSETNO, COSTCENTCD, DSCNTPCT" +
					" FROM ENOC.CAT_CARRERA " + 
					" WHERE FACULTAD_ID = ? " +
					" AND CARRERA_ID IN (SELECT CARRERA_ID FROM ENOC.CARGA_PERMISO WHERE CARGA_ID = ?)" +
					"  " +orden;
			Object[] parametros = new Object[] {facultad,cargaId};
			lista = enocJdbc.query(comando, new CatCarreraMapper(), parametros);
						
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|getListCargaCarrera|:"+ex);
		}
		
		return lista;
	}

	// Lista de carreras con materias en una carga
	public List<CatCarrera> getListCargaCarreraOficial(String facultad, String cargaId, String orden) {
		
		List<CatCarrera> lista		= new ArrayList<CatCarrera>();		
		
		
		try{
			String comando = " SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA, " +
					" NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL, GLSETNO, COSTCENTCD, DSCNTPCT" +
					" FROM ENOC.CAT_CARRERA " + 
					" WHERE FACULTAD_ID = ? " +
					" AND CARRERA_ID IN (SELECT CARRERA_ID FROM ENOC.CARGA_PERMISO WHERE CARGA_ID = ?)" +
					" AND CARRERA_ID IN (SELECT CARRERA_ID FROM ENOC.MAPA_PLAN WHERE OFICIAL = 'S') " +orden;			
			Object[] parametros = new Object[] {facultad,cargaId};
			lista = enocJdbc.query(comando, new CatCarreraMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|getListCargaCarreraOficial|:"+ex);
		}
		
		return lista;
	}
	
	// Lista de carreras con materias en una carga
	public List<CatCarrera> getListCargaCarreraOficial( String cargaId, String orden) {
		
		List<CatCarrera> lista		= new ArrayList<CatCarrera>();		
		
		try{
			String comando = " SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA, " +
					" NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL, GLSETNO, COSTCENTCD, DSCNTPCT" +
					" FROM ENOC.CAT_CARRERA " + 
					" WHERE  CARRERA_ID IN (SELECT CARRERA_ID FROM ENOC.CARGA_PERMISO WHERE CARGA_ID = ?)" +
					" AND CARRERA_ID IN (SELECT CARRERA_ID FROM ENOC.MAPA_PLAN WHERE OFICIAL = 'S') " +orden;		
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new CatCarreraMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|getListCargaCarreraOficial|:"+ex);
		}
		
		return lista;
	}
	
	public List<CatCarrera> carrerasPorCarga( String facultadId, String cargaId, String orden) {
	
		List<CatCarrera> lista		= new ArrayList<CatCarrera>();
		
		try{
			String comando = "SELECT * FROM ENOC.CAT_CARRERA WHERE CARRERA_ID IN (SELECT CARRERA_ID FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ?) AND FACULTAD_ID = ? " +orden;
			Object[] parametros = new Object[] {cargaId, facultadId};
			lista = enocJdbc.query(comando, new CatCarreraMapper(), parametros);		
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|carrerasPorCarga|:"+ex);
		}
		
		return lista;
	}
	
	public List<String> getCarrerasAlumno( String matricula, String orden ) {
		
		List<String> lista				= new ArrayList<String>();		
		try{
			String comando = "SELECT DISTINCT(SELECT CARRERA_ID FROM ENOC.MAPA_PLAN WHERE PLAN_ID = ALUM_PLAN.PLAN_ID) AS CARRERA " +
					"FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = ? "+ orden; 
			Object[] parametros = new Object[] {matricula};
			lista = enocJdbc.queryForList(comando, String.class, parametros );			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|getCarrerasAlumno|:"+ex);
		}
		return lista;
	}
	
	public List<CatCarrera> lisCarrerasEnAgenda( String orden ) {		
		List<CatCarrera> lista		= new ArrayList<CatCarrera>();		
		try{
			String comando = "SELECT * FROM ENOC.CAT_CARRERA"
					+ " WHERE CARRERA_ID IN ("
					+ "		SELECT DISTINCT(CARRERA(PLAN_ID)) FROM ENOC.CARGA_ALUMNO WHERE CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID FROM ENOC.ALUM_AGENDA)"
					+ ") "+ orden;			
			lista = enocJdbc.query(comando, new CatCarreraMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|lisCarrerasEnAgenda|:"+ex);
		}
		
		return lista;
	}
	
	// Mapa que regresa un conjunto de carreras que comienzan igual que la clave de la facultad
	public HashMap<String,String> mapaCarrerasFacultad( String facultadId ) {
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa = new HashMap<String,String>();		
		try{
			String comando = "SELECT CARRERA_ID AS LLAVE, NOMBRE_CARRERA AS VALOR FROM ENOC.CAT_CARRERA WHERE SUBSTR(CARRERA_ID,1,3) = ?";
			Object[] parametros = new Object[] {facultadId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa map: lista){
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|mapaCarrerasFacultad|:"+ex);
		}
		
		return mapa;
	}

	public HashMap<String,CatCarrera> mapaCarreras() {
		HashMap<String,CatCarrera> mapa = new HashMap<String,CatCarrera>();		
		List<CatCarrera> lista			= new ArrayList<CatCarrera>();
		try{
			String comando = "SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA, NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL, GLSETNO, COSTCENTCD, DSCNTPCT FROM ENOC.CAT_CARRERA";
			
			lista = enocJdbc.query(comando, new CatCarreraMapper());
			for(CatCarrera carrera: lista){
				mapa.put(carrera.getCarreraId(), carrera);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|mapaCarreras|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaCarrerasAlumno() {
		HashMap<String,String> mapa = new HashMap<String,String>();		
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE_CARRERA AS VALOR FROM ENOC.CAT_CARRERA";
			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map: lista){
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|mapaCarreras|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaCarrerasPorFacultad(){		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{			
			String comando = "SELECT FACULTAD_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.CAT_CARRERA GROUP BY FACULTAD_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());		
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|mapaCarrerasPorFacultad|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaFacultadPorCarrera(){		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{			
			String comando = "SELECT CARRERA_ID AS LLAVE,FACULTAD_ID AS VALOR FROM ENOC.CAT_CARRERA";
			lista = enocJdbc.query(comando, new aca.MapaMapper());		
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|mapaFacultadPorCarrera|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaPlanesPorFacultad(String estados){		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{			
			String comando = "SELECT FACULTAD_ID AS LLAVE, COUNT(CARRERA_ID) AS VALOR "
					+ " FROM ENOC.CAT_CARRERA "
					+ " WHERE CARRERA_ID IN (SELECT DISTINCT(CARRERA_ID) FROM ENOC.MAPA_PLAN WHERE ESTADO IN ("+estados+")) "
					+ " GROUP BY FACULTAD_ID ";
			lista = enocJdbc.query(comando, new aca.MapaMapper());		
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|mapaPlanesPorFacultad|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaCarrerasConPermico(String cargaId){		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista		 = new ArrayList<aca.Mapa>();		
		try{			
			String comando = "SELECT FACULTAD_ID AS LLAVE, COUNT(CARRERA_ID) AS VALOR"
					+ " FROM CAT_CARRERA WHERE CARRERA_ID"
					+ " IN(SELECT CARRERA_ID FROM ENOC.CARGA_PERMISO WHERE CARGA_ID = ?) GROUP BY FACULTAD_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), new Object[] {cargaId});		
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|mapaCarrerasConPermico|:"+ex);
		}
		
		return mapa;
	}
	
}