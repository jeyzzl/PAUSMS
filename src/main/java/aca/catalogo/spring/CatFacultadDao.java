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
public class CatFacultadDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CatFacultad facultad) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CAT_FACULTAD (AREA_ID, FACULTAD_ID, TITULO, NOMBRE_FACULTAD, CODIGO_PERSONAL, NOMBRE_CORTO, INV_REFERENTE)"
					+ " VALUES( ?, ?, ? , ? , ?, ?, ?)";
			Object[] parametros = new Object[] {facultad.getAreaId(),facultad.getFacultadId(),facultad.getTitulo(),facultad.getNombreFacultad(),facultad.getCodigoPersonal(),facultad.getNombreCorto(),facultad.getInvReferente()};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatFacultadDao|insertReg|:"+ex);		
		}
		
		return ok;
	}	
	
	public boolean updateReg( CatFacultad facultad ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.CAT_FACULTAD"
					+ " SET AREA_ID = ?,"
					+ " TITULO = ?,"
					+ " NOMBRE_FACULTAD = ?,"
					+ " CODIGO_PERSONAL = ?,"
					+ " NOMBRE_CORTO = ?,"
					+ " INV_REFERENTE = ? "
					+ " WHERE FACULTAD_ID = ?";
			Object[] parametros = new Object[] {facultad.getAreaId(),facultad.getTitulo(),facultad.getNombreFacultad(),facultad.getCodigoPersonal(),facultad.getNombreCorto(),facultad.getInvReferente(), facultad.getFacultadId()};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatFacultadDao|updateReg|:"+ex);		
		}
		
		return ok;
	}	
	
	public boolean deleteReg( String facultadId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CAT_FACULTAD WHERE FACULTAD_ID = ?";
			Object[] parametros = new Object[] {facultadId};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatFacultadDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public CatFacultad mapeaRegId(  String facultadId, String areaId) {
		
		CatFacultad facultad 	= new CatFacultad();		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_FACULTAD WHERE FACULTAD_ID = ? AND AREA_ID = ?";			
			Object[] parametros = new Object[] {facultadId,areaId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				comando = " SELECT AREA_ID, FACULTAD_ID, TITULO, NOMBRE_FACULTAD, CODIGO_PERSONAL, NOMBRE_CORTO, INV_REFERENTE"
						+ " FROM ENOC.CAT_FACULTAD WHERE FACULTAD_ID = ? AND AREA_ID = ?";			
				facultad = enocJdbc.queryForObject(comando, new CatFacultadMapper(), parametros);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatFacultadDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return facultad;
	}
	
	public CatFacultad mapeaRegId(  String facultadId) {		
		CatFacultad facultad 	= new CatFacultad();
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_FACULTAD WHERE FACULTAD_ID = ?";			
			Object[] parametros = new Object[] {facultadId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				comando = " SELECT AREA_ID, FACULTAD_ID, TITULO, NOMBRE_FACULTAD, CODIGO_PERSONAL, NOMBRE_CORTO, INV_REFERENTE"
						+ " FROM ENOC.CAT_FACULTAD WHERE FACULTAD_ID = ?";					
				facultad = enocJdbc.queryForObject(comando, new CatFacultadMapper(), parametros);
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatFacultadDao|mapeaRegId|:"+ex);
		}		
		return facultad;		
	}
	
	public boolean existeReg( String facultadId) {
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_FACULTAD WHERE FACULTAD_ID = ?";			
			Object[] parametros = new Object[] {facultadId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatFacultadDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg( String areaId ) {	
		
		String maximo			= "1";		
		try{
			String comando = "SELECT MAX(FACULTAD_ID)+1 MAXIMO FROM ENOC.CAT_FACULTAD WHERE AREA_ID = ?";
			Object[] parametros = new Object[] {areaId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				maximo = enocJdbc.queryForObject(comando, String.class, parametros);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatFacultadDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public String getNombreFacultad( String facultadId ) {
		
		String nombre			= "";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_FACULTAD WHERE FACULTAD_ID = ?";
			Object[] parametros = new Object[] {facultadId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = " SELECT NOMBRE_FACULTAD FROM ENOC.CAT_FACULTAD WHERE FACULTAD_ID = ?";						
				nombre = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatFacultadDao|getNombreFacultad|:"+ex);
		}
		
		return nombre;
	}
	
	public String getNombreCorto( String facultadId ) {
		
		String nombre			= "";	
		try{
			String comando = "SELECT NOMBRE_CORTO FROM ENOC.CAT_FACULTAD WHERE FACULTAD_ID = ?";
			Object[] parametros = new Object[] {facultadId};			
			nombre = enocJdbc.queryForObject(comando, String.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatFacultadDao|getNombreCorto|:"+ex);
		}
		
		return nombre;
	}
	
	public boolean esDirector( String codigoPersonal ) {
		
		boolean ok 				= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_FACULTAD WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatFacultadDao|esDirector|:"+ex);
		}		
		return ok;
	}
	
	
	public String Director( String facultadId ) {
		
		String director 		= "";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_FACULTAD WHERE FACULTAD_ID = ?";
			Object[] parametros = new Object[] {facultadId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				comando = "SELECT CODIGO_PERSONAL FROM ENOC.CAT_FACULTAD WHERE FACULTAD_ID = ?";
				director = enocJdbc.queryForObject(comando, String.class, parametros);
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatFacultadDao|Director|:"+ex);
		}
		
		return director;
	}
	
	public String getNombreHorarioId( String horarioId ) {
		
		String nombre			= "1";		
		try{
			String comando = "SELECT NOMBRE_FACULTAD FROM ENOC.CAT_FACULTAD WHERE FACULTAD_ID IN (SELECT FACULTAD_ID FROM ENOC.CAT_HORARIO WHERE HORARIO_ID = ?)";
			Object[] parametros = new Object[] {horarioId};
			nombre = enocJdbc.queryForObject(comando, String.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatFacultadDao|getNombreHorarioId|:"+ex);
		}
		
		return nombre;
	}
	
	public String getFacultadInvReferente( String codigoPersonal ) {
		
		List<String> lista	= new ArrayList<String>();
		
		String facultades		= "";
		int row = 0;
		try{ 
			String comando = "SELECT FACULTAD_ID FROM ENOC.CAT_FACULTAD WHERE INV_REFERENTE = ? OR CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal, codigoPersonal};
			lista = enocJdbc.queryForList(comando, String.class, parametros);			
			for(String fac: lista){
				if (row>0) facultades = facultades + ",";
				facultades = facultades + "'"+fac+"'";
				row++;
			}
			if (row==0) facultades = "'X'";
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatFacultadDao|getFacultadInvReferente|:"+ex);
		}
		
		return facultades;
	}
	
	public String getFacultad( String codigoPersonal ) {
		
		String codigo 		= "";
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_FACULTAD WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};			
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT FACULTAD_ID FROM ENOC.CAT_FACULTAD WHERE CODIGO_PERSONAL = ?";
				codigo = enocJdbc.queryForObject(comando, String.class, parametros);
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatFacultadDao|getFacultad|:"+ex);
		}
		
		return codigo;
	}
	
	public String getAreaId( String facultadId ) {
		
		String codigo 		= "";
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_FACULTAD WHERE FACULTAD_ID = ?";			
			Object[] parametros = new Object[] {facultadId};			
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT AREA_ID FROM ENOC.CAT_FACULTAD WHERE FACULTAD_ID = ?";
				codigo = enocJdbc.queryForObject(comando, String.class, parametros);
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatFacultadDao|getAreaId|:"+ex);
		}
		
		return codigo;
	}
	
	public List<CatFacultad> getListAll( String orden) {
		
		List<CatFacultad> lista	= new ArrayList<CatFacultad>();
		
		try{
			String comando = "SELECT AREA_ID, FACULTAD_ID, TITULO, NOMBRE_FACULTAD, CODIGO_PERSONAL, NOMBRE_CORTO, INV_REFERENTE" +
					" FROM ENOC.CAT_FACULTAD "+orden;			
			lista = enocJdbc.query(comando, new CatFacultadMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatFacultadDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	// Lista de Facultades en un area
	public List<CatFacultad> lisPorArea( String areaId, String orden) {
		
		List<CatFacultad> lista	= new ArrayList<CatFacultad>();
				
		try{
			// Busca las facultades que tengan carreras autorizadas en la cargaId de parámetro y
			String comando = "SELECT AREA_ID, FACULTAD_ID, TITULO, NOMBRE_FACULTAD, CODIGO_PERSONAL, NOMBRE_CORTO, INV_REFERENTE" +
					" FROM ENOC.CAT_FACULTAD" + 
					" WHERE AREA_ID = ? "+orden;
			Object[] parametros = new Object[] {areaId};
			lista = enocJdbc.query(comando, new CatFacultadMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatFacultadDao|lisPorArea|:"+ex);
		}
		
		return lista;
	}
	
	// Lista de Facultades con alumnos inscritos en una carga
	public List<CatFacultad> lisPorCarga( String cargaId, String orden) {
		
		List<CatFacultad> lista	= new ArrayList<CatFacultad>();				
		try{			
			String comando = "SELECT AREA_ID, FACULTAD_ID, TITULO, NOMBRE_FACULTAD, CODIGO_PERSONAL, NOMBRE_CORTO, INV_REFERENTE" +
					" FROM ENOC.CAT_FACULTAD" + 
					" WHERE FACULTAD_ID IN (SELECT FACULTAD_ID FROM ALUM_ESTADO WHERE CARGA_ID = ? AND ESTADO = 'I') "+orden;
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new CatFacultadMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatFacultadDao|lisPorCarga|:"+ex);
		}
		
		return lista;
	}
	
	// Lista de Facultades en un area
	public List<CatFacultad> lisPorHorario( String cargaId, String orden) {
		
		List<CatFacultad> lista	= new ArrayList<CatFacultad>();				
		try{
			// Busca las facultades que tengan carreras autorizadas en la cargaId de parámetro y
			String comando = "SELECT AREA_ID, FACULTAD_ID, TITULO, NOMBRE_FACULTAD, CODIGO_PERSONAL, NOMBRE_CORTO, INV_REFERENTE"
					+ " FROM ENOC.CAT_FACULTAD"
					+ " WHERE FACULTAD_ID IN (SELECT FACULTAD_ID FROM CAT_HORARIO WHERE HORARIO_ID IN (SELECT HORARIO_ID FROM ENOC.CARGA_GRUPO_HORA WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ? AND SALON_ID != '0')) "+orden;			
			lista = enocJdbc.query(comando, new CatFacultadMapper(), cargaId);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatFacultadDao|lisPorHorario|:"+ex);
		}
		
		return lista;
	}
	
	// Lista de Facultades en un area
		public List<CatFacultad> lisPorHoras( String cargas, String orden) {
			
			List<CatFacultad> lista	= new ArrayList<CatFacultad>();				
			try{
				// Busca las facultades que tengan carreras autorizadas en la cargaId de parámetro y
				String comando = "SELECT AREA_ID, FACULTAD_ID, TITULO, NOMBRE_FACULTAD, CODIGO_PERSONAL, NOMBRE_CORTO, INV_REFERENTE" +
						" FROM ENOC.CAT_FACULTAD" + 
						" WHERE FACULTAD_ID IN (SELECT FACULTAD(CARRERA_ID) FROM ENOC.EMP_HORAS WHERE CARGA_ID IN ("+cargas+")) "+orden;
				//Object[] parametros = new Object[] {areaId};
				lista = enocJdbc.query(comando, new CatFacultadMapper());
			}catch(Exception ex){
				System.out.println("Error - aca.catalogo.spring.CatFacultadDao|lisPorArea|:"+ex);
			}
			
			return lista;
		}
	
	// Lista de Facultades con permiso en una carga academica
	public List<CatFacultad> getListCarga( String cargaId, String orden) {
		
		List<CatFacultad> lista	= new ArrayList<CatFacultad>();
				
		try{
			// Busca las facultades que tengan carreras autorizadas en la cargaId de parámetro y
			String comando = "SELECT AREA_ID, FACULTAD_ID, TITULO, NOMBRE_FACULTAD, CODIGO_PERSONAL, NOMBRE_CORTO, INV_REFERENTE" +
					" FROM ENOC.CAT_FACULTAD" + 
					" WHERE FACULTAD_ID IN " +
					"	(SELECT FACULTAD_ID FROM ENOC.CAT_CARRERA " +
					"	WHERE CARRERA_ID IN (SELECT CARRERA_ID FROM ENOC.CARGA_PERMISO_PLAN WHERE CARGA_ID = ?) " +
						") "+orden;
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new CatFacultadMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatFacultadDao|getListCarga|:"+ex);
		}
		
		return lista;
	}
	
	// Lista de Facultades con permiso en una carga academica
	public List<CatFacultad> lisConExtranjerosEnCarga( String cargaId, String orden) {		
		List<CatFacultad> lista	= new ArrayList<CatFacultad>();				
		try{
			// Busca las facultades que tengan carreras autorizadas en la cargaId de parámetro y
			String comando = "SELECT AREA_ID, FACULTAD_ID, TITULO, NOMBRE_FACULTAD, CODIGO_PERSONAL, NOMBRE_CORTO, INV_REFERENTE" +
					" FROM ENOC.CAT_FACULTAD" + 
					" WHERE FACULTAD_ID IN (SELECT FACULTAD_ID FROM ENOC.ESTADISTICA WHERE CARGA_ID = ? AND NACIONALIDAD != 91) " +orden;
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new CatFacultadMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatFacultadDao|lisConExtranjerosEnCarga|:"+ex);
		}		
		return lista;
	}
	
	// Lista de Facultades con permiso de acceso para el usuario
	public List<CatFacultad> filtroPorAcceso( String codigoPersonal, String orden) {
		
		List<CatFacultad> lista	= new ArrayList<CatFacultad>();
				
		try{			
			String comando = "SELECT AREA_ID, FACULTAD_ID, TITULO, NOMBRE_FACULTAD, CODIGO_PERSONAL, NOMBRE_CORTO, INV_REFERENTE" +
					" FROM ENOC.CAT_FACULTAD" + 
					" WHERE FACULTAD_ID IN " +
					"	(SELECT FACULTAD_ID FROM ENOC.CAT_CARRERA WHERE INSTR((SELECT ACCESOS FROM ENOC.ACCESO WHERE CODIGO_PERSONAL = ? ),CARRERA_ID)>0) " +orden;
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new CatFacultadMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatFacultadDao|filtroPorAcceso|:"+ex);
		}
		
		return lista;
	}
	
	public List<CatFacultad> listFacultadesConCarga( String cargaId, String orden) {
		
		List<CatFacultad> lista	= new ArrayList<CatFacultad>();
		
		try{
			// Busca las facultades con materias en una carga
			String comando = "SELECT AREA_ID, FACULTAD_ID, TITULO, NOMBRE_FACULTAD, CODIGO_PERSONAL, NOMBRE_CORTO, INV_REFERENTE"
					+ " FROM ENOC.CAT_FACULTAD"
					+ " WHERE FACULTAD_ID IN"
					+ " 	(SELECT ENOC.FACULTAD(CARRERA_ID) FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ?) "+orden;
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new CatFacultadMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatFacultadDao|getListConCarga|:"+ex);
		}
		
		return lista;
	}
	
	// Lista de Facultades con permiso en una carga academica con carreras oficiales ante la secretaria
	public List<CatFacultad> getListCargaOficial( String cargaId, String orden) {
		
		List<CatFacultad> lista	= new ArrayList<CatFacultad>();
				
		try{
			// Busca las facultades que tengan carreras autorizadas en la cargaId de parámetro y
			// que tengan carreras oficiales ante la secretaría de educación.
			String comando = "SELECT AREA_ID, FACULTAD_ID, TITULO, NOMBRE_FACULTAD, CODIGO_PERSONAL, NOMBRE_CORTO, INV_REFERENTE" +
					" FROM ENOC.CAT_FACULTAD" + 
					" WHERE FACULTAD_ID IN " +
					"	(SELECT FACULTAD_ID FROM ENOC.CAT_CARRERA " +
					"	WHERE CARRERA_ID IN (SELECT CARRERA_ID FROM ENOC.CARGA_PERMISO WHERE CARGA_ID = ?) " +
					"	AND CARRERA_ID IN (SELECT CARRERA_ID FROM ENOC.MAPA_PLAN WHERE OFICIAL = 'S')" +
						") "+orden;
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new CatFacultadMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatFacultadDao|getListCargaOficial|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String, String> getAlumnosPorFacultad (String cargas, String modalidades, String fechaIni, String fechaFin){
		HashMap<String, String> mapa	= new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
		
		try {
			String comando = "SELECT FACULTAD_ID AS LLAVE,"
					  + " (SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.ESTADISTICA"
					  + " WHERE ENOC.FACULTAD(CARRERA_ID) = ENOC.CAT_FACULTAD.FACULTAD_ID"
					  + " AND CARGA_ID IN ("+cargas+") "
					  + " AND MODALIDAD_ID IN ("+modalidades+")"
					  + " AND F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY')"			  
					  + " ) AS VALOR"
					  + " FROM ENOC.CAT_FACULTAD"
					  + " WHERE FACULTAD_ID IN (SELECT DISTINCT(ENOC.FACULTAD(CARRERA_ID)) FROM ENOC.ESTADISTICA WHERE CARGA_ID IN ("+cargas+") AND MODALIDAD_ID IN ("+modalidades+"))";
//			Object[] parametros = new Object[] {fechaIni, fechaFin};
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}
		}catch(Exception ex) {
			System.out.println("Error - aca.catalogo.spring.CatFacultadDao|getListAlumnosPorFacultad|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, CatFacultad> getMapFacultad(String orden) {
		
		HashMap<String, CatFacultad> mapa	= new HashMap<String, CatFacultad>();
		List<CatFacultad> lista	= new ArrayList<CatFacultad>();
		
		try{
			String comando = "SELECT AREA_ID, FACULTAD_ID, TITULO, NOMBRE_FACULTAD, CODIGO_PERSONAL, NOMBRE_CORTO, INV_REFERENTE"
					+ " FROM ENOC.CAT_FACULTAD " + orden;
			lista = enocJdbc.query(comando, new CatFacultadMapper());
			for(CatFacultad facultad : lista){
				mapa.put(facultad.getFacultadId(), facultad);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatFacultadDao|getMapFacultad|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaAreasPorFacultad(){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{			
			String comando = "SELECT AREA_ID AS LLAVE, "
					+ " COUNT(*) AS VALOR "
					+ " FROM ENOC.CAT_FACULTAD "
					+ " GROUP BY AREA_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());		
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatFacultadDao|mapaTotalAcuerdos|:"+ex);
		}
		
		return mapa;
	}

	public HashMap<String, String> mapaFacultadPorHorario(){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista		 = new ArrayList<aca.Mapa>();	
			
		try{
			String comando = "SELECT FACULTAD_ID AS LLAVE, NOMBRE_FACULTAD AS VALOR FROM ENOC.CAT_FACULTAD WHERE FACULTAD_ID IN (SELECT FACULTAD_ID FROM ENOC.CAT_HORARIO)";
			lista = enocJdbc.query(comando, new aca.MapaMapper());		
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatFacultadDao|mapaFacultadPorHorario|:"+ex);
		}
		
		return mapa;
	}
	
	
}