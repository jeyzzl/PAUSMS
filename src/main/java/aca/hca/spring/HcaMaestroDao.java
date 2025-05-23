/**
 * 
 */
package aca.hca.spring;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class HcaMaestroDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( HcaMaestro hcaMaestro) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.HCA_MAESTRO(CODIGO_PERSONAL, FACULTAD_ID, CARRERA_ID, ESTADO) VALUES(?, ?, ?, ?)";
			Object[] parametros = new Object[] {hcaMaestro.getCodigoPersonal(),	hcaMaestro.getFacultadId(),	hcaMaestro.getCarreraId(), hcaMaestro.getEstado()};	
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaMaestroDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg( HcaMaestro hcaMaestro) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.HCA_MAESTRO" + 
				" SET FACULTAD_ID = ?," +
				" CARRERA_ID = ?," +
				" ESTADO = ?" +
				" WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {hcaMaestro.getFacultadId(),	hcaMaestro.getCarreraId(), hcaMaestro.getEstado(), hcaMaestro.getCodigoPersonal()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaMaestroDao|updateReg|:"+ex);		
		}
		return ok;
	}	
	
	public boolean deleteReg( String codigoPersonal) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.HCA_MAESTRO WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};				
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaMaestroDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public HcaMaestro mapeaRegId(String codigoPersonal) {
		HcaMaestro hcaMaestro = new HcaMaestro();			
		try{ 
			String comando = "SELECT COUNT(*) FROM ENOC.HCA_MAESTRO WHERE CODIGO_PERSONAL = ?";
			if (enocJdbc.queryForObject(comando,Integer.class,codigoPersonal)>=1){
				comando = "SELECT CODIGO_PERSONAL, FACULTAD_ID, CARRERA_ID, ESTADO FROM ENOC.HCA_MAESTRO WHERE CODIGO_PERSONAL = ?";
				hcaMaestro = enocJdbc.queryForObject(comando, new HcaMaestroMapper(),codigoPersonal);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaMaestroDao|mapeaRegId|:"+ex);
		}
		return hcaMaestro;
	}
	
	public boolean existeReg( String codigoPersonal) {
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.HCA_MAESTRO WHERE CODIGO_PERSONAL = ?";		
			if (enocJdbc.queryForObject(comando,Integer.class,codigoPersonal)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaMaestroDao|existeReg|:"+ex);
		}	
		return ok;
	}
	
	public String getEmpFacBase( String codigoPersonal) {		
		String fac				= "xxx";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.HCA_MAESTRO WHERE CODIGO_PERSONAL = ? AND ESTADO = 'B'";		
			if (enocJdbc.queryForObject(comando,Integer.class,codigoPersonal)>=1){
				comando = "SELECT FACULTAD_ID FROM ENOC.HCA_MAESTRO WHERE CODIGO_PERSONAL = ? AND ESTADO = 'B'";
				fac = enocJdbc.queryForObject(comando,String.class,codigoPersonal);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaMaestroDao||getEmpFacBase:"+ex);
		}
		return fac;
	}
	
	public String getEmpCarreraBase( String codigoPersonal) {		
		String carrera			= "xxxxx";	
		try{
			String comando = "SELECT CARRERA_ID FROM ENOC.HCA_MAESTRO WHERE CODIGO_PERSONAL = ? AND ESTADO = 'B'";
			if (enocJdbc.queryForObject(comando,Integer.class,codigoPersonal)>=1){
				comando = "SELECT CARRERA_ID FROM ENOC.HCA_MAESTRO WHERE CODIGO_PERSONAL = ? AND ESTADO = 'B'";
				carrera = enocJdbc.queryForObject(comando,String.class,codigoPersonal);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaMaestroDao||getEmpCarreraBase:"+ex);
		}	
		return carrera;
	}
	
	public String numMaeReg( String cargaId, String facultadId){		
		String total			= "0";
		try{
			String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.HCA_MAESTRO"
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ? AND ENOC.FACULTAD(CARRERA_ID) = ?)";
			total = enocJdbc.queryForObject(comando,String.class,cargaId, facultadId);			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaMaestroDao||numMaeReg:"+ex);
		}
		return total;
	}
	
	public String numMaeRegFac( String cargaId, String facultadId) {		
		String total			= "0";		
		try{
			String comando = "SELECT COUNT(CODIGO_PERSONAL) AS TOTAL FROM ENOC.HCA_MAESTRO" 
				+ " WHERE ENOC.FACULTAD(CARRERA_ID) = ?"
				+ " AND CODIGO_PERSONAL IN "
				+ "(SELECT CODIGO_PERSONAL FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ? AND ENOC.FACULTAD(CARRERA_ID) = ?)";
			total = enocJdbc.queryForObject(comando,String.class,facultadId, cargaId, facultadId);		
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaMaestroDao||numMaeRegFac:"+ex);
		}
		return total;
	}
	
	public List<HcaMaestro> lisTodos( String orden ) {		
		List<HcaMaestro> lista	= new ArrayList<HcaMaestro>();
		try{
			String comando = "SELECT CODIGO_PERSONAL, FACULTAD_ID, CARRERA_ID, ESTADO FROM ENOC.HCA_MAESTRO " +orden;
			lista = enocJdbc.query(comando, new HcaMaestroMapper());		
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaMaestroDao|lisTodos|:"+ex);
		}		
		return lista;
	}
	
	public List<HcaMaestro> lisPorFacultad( String facultadId, String orden ) {		
		List<HcaMaestro> lista	= new ArrayList<HcaMaestro>();	
		try{
			String comando = "SELECT CODIGO_PERSONAL, FACULTAD_ID, CARRERA_ID, ESTADO FROM ENOC.HCA_MAESTRO WHERE FACULTAD_ID = ? " +orden;
			lista = enocJdbc.query(comando, new HcaMaestroMapper(), facultadId);
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaMaestroDao|lisPorFacultad|:"+ex);
		}	
		return lista;
	}
	
	public List<aca.Mapa> lisConMaterias( String cargaId, String facultadId, String orden ) {		
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();	
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ? AND ENOC.FACULTAD(CARRERA_ID) = ? GROUP BY CODIGO_PERSONAL " +orden;
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId, facultadId);
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaMaestroDao|lisConMaterias|:"+ex);
		}	
		return lista;
	}

	public List<HcaMaestro> lisPorAcceso( String codigoPersonal, String orden) {
		String carreras = "";
		List<HcaMaestro> lista 	= new ArrayList<HcaMaestro>();	
		try{
			String comando = "SELECT COUNT(ACCESOS) FROM ENOC.ACCESO WHERE CODIGO_PERSONAL = ?";
			if (enocJdbc.queryForObject(comando, Integer.class, codigoPersonal)>=1){
				comando 	= "SELECT ACCESOS FROM ENOC.ACCESO WHERE CODIGO_PERSONAL = ?";				 
				carreras 	= enocJdbc.queryForObject(comando, String.class, codigoPersonal);
			}
			String arrCarreras[] = carreras.split(" ");
			carreras = "'0'";
			for (String carrera : arrCarreras) {
				if (carrera.length()==5) {
					if (carreras.equals("'0'"))carreras = "'"+carrera+"'"; else carreras = carreras+",'"+carrera+"'";
				}
			}			
			comando = "SELECT CODIGO_PERSONAL, FACULTAD_ID, CARRERA_ID, ESTADO FROM ENOC.HCA_MAESTRO WHERE CARRERA_ID IN ("+carreras+") "+orden;
			lista = enocJdbc.query(comando, new HcaMaestroMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaMaestroDao|lisPorAcceso|:"+ex);
		}		
		return lista;
	}
	
	public HashMap<String, HcaMaestro> mapTodos( ) {
		HashMap<String, HcaMaestro> mapaGrupo = new HashMap<String, HcaMaestro>();
		List<HcaMaestro> lista 	= new ArrayList<HcaMaestro>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, FACULTAD_ID, CARRERA_ID, ESTADO FROM ENOC.HCA_MAESTRO";
			lista = enocJdbc.query(comando, new HcaMaestroMapper());
			for(HcaMaestro objeto: lista) {
				mapaGrupo.put(objeto.getCodigoPersonal(), objeto);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaMaestroDao|mapTodos|:"+ex);
		}
		return mapaGrupo;
	}
	
	public HashMap<String, String> mapMaestrosPorFacultad( ) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista 	= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT ENOC.FACULTAD(CARRERA_ID) AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.HCA_MAESTRO GROUP BY ENOC.FACULTAD(CARRERA_ID)";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa grupo : lista) {
				mapaGrupo.put(grupo.getLlave(), (String)grupo.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaMaestroDao|mapMaestrosPorFacultad|:"+ex);
		}
		return mapaGrupo;
	}
	
	public HashMap<String, String> mapMaestrosConMaterias( String cargaId) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista 	= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT ENOC.FACULTAD(CARRERA_ID) AS LLAVE, COUNT(DISTINCT(CODIGO_PERSONAL)) AS VALOR FROM ENOC.CARGA_GRUPO"
					+ " WHERE CARGA_ID = ? AND CODIGO_PERSONAL != '0'"
					+ " GROUP BY ENOC.FACULTAD(CARRERA_ID)";	
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa grupo : lista) {
				mapaGrupo.put(grupo.getLlave(), (String)grupo.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaMaestroDao|mapMaestrosConMaterias|:"+ex);
		}

		return mapaGrupo;
	}
	
	public HashMap<String, String> mapMaestrosRegistrados( String cargaId) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista 	= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT ENOC.FACULTAD(CARRERA_ID) AS LLAVE, COUNT(DISTINCT(CODIGO_PERSONAL)) AS VALOR FROM ENOC.CARGA_GRUPO"					
					+ " WHERE CARGA_ID = ? AND CODIGO_PERSONAL != '0'"
					+ " AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.HCA_MAESTRO_ACTIVIDAD WHERE CARGA_ID = ?)"					
					+ " GROUP BY ENOC.FACULTAD(CARRERA_ID)";	
			Object[] parametros = new Object[] {cargaId, cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa grupo : lista) {
				mapaGrupo.put(grupo.getLlave(), (String)grupo.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaMaestroDao|mapMaestrosRegistrados|:"+ex);
		}

		return mapaGrupo;
	}
	
	public HashMap<String, String> mapMaestrosCapturados( String cargaId) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista 	= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, SEMANAS AS VALOR FROM ENOC.HCA_MAESTRO_ACTIVIDAD WHERE CARGA_ID = ?";	
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa grupo : lista) {
				mapaGrupo.put(grupo.getLlave(), (String)grupo.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaMaestroDao|mapMaestrosCapturados|:"+ex);
		}

		return mapaGrupo;
	}
	
	public HashMap<String, String> mapMaestrosConPermiso( String carreras) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, CARRERA_ID AS VALOR FROM ENOC.HCA_MAESTRO WHERE CARRERA_ID IN ("+carreras+")";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa maestro : lista) {
				mapaGrupo.put(maestro.getLlave(), (String)maestro.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.HcaMaestroDao|mapMaestrosConPermiso|:"+ex);
		}
		return mapaGrupo;
	}
	
}