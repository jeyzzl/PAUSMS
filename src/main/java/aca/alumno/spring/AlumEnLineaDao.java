package aca.alumno.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AlumEnLineaDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AlumEnLinea alumEnlinea){
		
 		boolean ok 				= false;
 		
 		try{
 			String comando = "INSERT INTO ENOC.ALUM_ENLINEA"+ 
 				"(CODIGO_PERSONAL, CARGA_ID," +
 				" USUARIO, SOLICITUD, COMENTARIOS, FECHA, ESTADO, RESIDENCIA_ID, COORDINADOR)"+ 
 				" VALUES(?,?,?,?,?,now(),?,?,?)";	
 			
 			Object[] parametros = new Object[] {alumEnlinea.getCodigoPersonal(),alumEnlinea.getCargaId(),alumEnlinea.getUsuario(),alumEnlinea.getSolicitud(),
 					alumEnlinea.getComentarios(),alumEnlinea.getEstado(),alumEnlinea.getResidenciaId(),alumEnlinea.getCoordinador()};
 			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumEnlineaUtil|insertReg|:"+ex);			
 		}
 		
 		return ok;
 	}	
 	
 	public boolean updateReg(AlumEnLinea alumEnlinea){ 		
 		
 		boolean ok 				= false;
 		
 		try{
 			String comando = "UPDATE ENOC.ALUM_ENLINEA"+ 
 				" SET USUARIO = ?, " +
 				" SOLICITUD = ?, " +
 				" COMENTARIOS = ?, " +
 				" ESTADO = ?, " +
 				" RESIDENCIA_ID = ?, " +
 				" COORDINADOR = ? " +
 				" WHERE CODIGO_PERSONAL = ? " +
 				" AND CARGA_ID = ? ";
 			
 			Object[] parametros = new Object[] {alumEnlinea.getUsuario(),alumEnlinea.getSolicitud(),alumEnlinea.getComentarios(),alumEnlinea.getEstado(),
 					alumEnlinea.getResidenciaId(),alumEnlinea.getCoordinador(),alumEnlinea.getCodigoPersonal(),alumEnlinea.getCargaId()};
 			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
 				
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumEnlineaUtil|updateReg|:"+ex);
 		}
 		
 		return ok;
 	}
 	 	
 	public boolean deleteReg(String codigoPersonal, String cargaId ){
 		boolean ok = false;
 	
 		try{
 			String comando = "DELETE FROM ENOC.ALUM_ENLINEA"+ 
 				" WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? ";
 			
 			Object[] parametros = new Object[] {codigoPersonal, cargaId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumEnlineaUtil|deleteReg|:"+ex);			
 		}
 		
 		return ok;
 	}
 	
 	public AlumEnLinea mapeaRegId (String codigoPersonal, String cargaId ){
 		AlumEnLinea alumEnlinea = new AlumEnLinea();
 	
 		try{
	 		String comando = "SELECT"+
	 			" CODIGO_PERSONAL, CARGA_ID, USUARIO, SOLICITUD, COMENTARIOS, TO_CHAR(FECHA, 'DD/MM/YYYY HH:MI AM') AS FECHA, ESTADO, RESIDENCIA_ID, COORDINADOR" +
	 			" FROM ENOC.ALUM_ENLINEA WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ?"; 
	 		
	 		Object[] parametros = new Object[] {codigoPersonal, cargaId};
	 		alumEnlinea = enocJdbc.queryForObject(comando, new AlumEnLineaMapper(), parametros);
			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumEnlineaUtil|mapeaRegId|:"+ex);
 		}
 		
 		return alumEnlinea;
 	}
	
 	public boolean existeReg( String codigoPersonal, String cargaId ){

 		boolean 		ok 		= false;	
 		
 		try{
 			String comando = "SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ENLINEA"+ 
 				" WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ?";
 			
 			Object[] parametros = new Object[] {codigoPersonal, cargaId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumEnlineaUtil|existeReg|:"+ex);
 		}
 		
 		return ok;
 	}
 	
 	public String getStatusFinanciero( String matricula){

		String status = "A";
		
		try{
			String comando = "SELECT DISTINCT(STATUS) FROM NOE.FES_COBRO_ONLINE_SEC WHERE MATRICULA = ? AND STATUS = 'I'"; 
			
			Object[] parametros = new Object[] {matricula};
			status = enocJdbc.queryForObject(comando,String.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumEnlineaUtil|getStatusFinanciero|:"+ex);
		}
		
		return status;
	}
 	
	public List<AlumEnLinea> getListAll( String orden ){
		
		List<AlumEnLinea> lista	= new ArrayList<AlumEnLinea>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, USUARIO, SOLICITUD, COMENTARIOS, TO_CHAR(FECHA, 'DD/MM/YYYY HH:MI AM') AS FECHA, ESTADO, RESIDENCIA_ID, COORDINADOR" +
 				" FROM ENOC.ALUM_ENLINEA "+orden;			
			lista = enocJdbc.query(comando, new AlumEnLineaMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumEnlineaUtil|getListAll|:"+ex);
		}			
		
		return lista;
	}
	
	public List<AlumEnLinea> getListAlumno( String codigoPersonal, String orden ){
		List<AlumEnLinea> lista	= new ArrayList<AlumEnLinea>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, USUARIO, SOLICITUD, COMENTARIOS, TO_CHAR(FECHA, 'DD/MM/YYYY HH:MI AM') AS FECHA, ESTADO, RESIDENCIA_ID, COORDINADOR" +
 				" FROM ENOC.ALUM_ENLINEA WHERE CODIGO_PERSONAL = ? "+orden;			
			lista = enocJdbc.query(comando, new AlumEnLineaMapper(), codigoPersonal);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumEnlineaUtil|getListAlumno|:"+ex);
		}			
		
		return lista;
	}
	
	public List<AlumEnLinea> getListCargas( String cargaId, String orden ){
		List<AlumEnLinea> lista	= new ArrayList<AlumEnLinea>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, USUARIO, SOLICITUD, COMENTARIOS, TO_CHAR(FECHA, 'DD/MM/YYYY HH:MI AM') AS FECHA, ESTADO, RESIDENCIA_ID, COORDINADOR" +
 				" FROM ENOC.ALUM_ENLINEA WHERE CARGA_ID = ? "+orden;			
			lista = enocJdbc.query(comando, new AlumEnLineaMapper(), cargaId);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumEnlineaUtil|getListCargas|:"+ex);
		}			
		
		return lista;
	}
	
	public List<AlumEnLinea> getListaAlumnosCoordinador( String carrerasCoordinador, String orden){
		List<AlumEnLinea> lista	= new ArrayList<AlumEnLinea>();
	
		try{
			String comando = "SELECT A.CODIGO_PERSONAL, A.CARGA_ID, A.USUARIO, A.SOLICITUD, A.COMENTARIOS, TO_CHAR(A.FECHA, 'DD/MM/YYYY HH:MI AM') AS FECHA, A.ESTADO, A.RESIDENCIA_ID, A.COORDINADOR" +
						" FROM ENOC.ALUM_ENLINEA A INNER JOIN ALUMNO B" +
						" ON A.CODIGO_PERSONAL=B.CODIGO_PERSONAL" +
						" WHERE B.CARRERA_ID IN ("+carrerasCoordinador+") "+orden;	
			
			lista = enocJdbc.query(comando, new AlumEnLineaMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumEnlineaUtil|getListaAlumnosCoordinador|:"+ex);
		}			
		
		return lista;
	}
	
	public HashMap<String, String> getMapaCantidadAlumnosPorSolicitudFacultad( String cargaId, String carreras){
		HashMap<String, String> mapa			= new HashMap<String, String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		
		boolean tieneCarreras = !carreras.equals("todas");
		try{
			String comando = "SELECT ENOC.FACULTAD(ENOC.ALUM_CARRERA_ID(Z.CODIGO_PERSONAL)) AS FACULTAD_ID," +
					" (SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.ALUM_ENLINEA B" +
					"	WHERE SOLICITUD='E' AND CARGA_ID='"+cargaId+"'" +
					"	AND (SELECT FACULTAD_ID FROM ENOC.CAT_CARRERA WHERE CARRERA_ID=(ENOC.ALUM_CARRERA_ID(B.CODIGO_PERSONAL)))=ENOC.FACULTAD(ENOC.ALUM_CARRERA_ID(B.CODIGO_PERSONAL))" +
					"	AND B.CODIGO_PERSONAL NOT IN" +
					"		(SELECT MATRICULA FROM NOE.FES_COBRO_ONLINE_SEC  B" +
					"			WHERE STATUS = 'I' AND CARGA_ID='"+cargaId+"')" +
					"	AND B.CODIGO_PERSONAL NOT IN" +
					"		(SELECT MATRICULA FROM MATEO.FES_CCOBRO B" +
					"			WHERE CARGA_ID ='"+cargaId+"' AND INSCRITO='S' AND ON_LINE='S')) AS E," +
					
					" (SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.ALUM_ENLINEA B" +
					"	WHERE SOLICITUD='A' AND CARGA_ID='"+cargaId+"'" +
					"	AND (SELECT FACULTAD_ID FROM ENOC.CAT_CARRERA WHERE CARRERA_ID=(ENOC.ALUM_CARRERA_ID(B.CODIGO_PERSONAL)))=ENOC.FACULTAD(ENOC.ALUM_CARRERA_ID(B.CODIGO_PERSONAL))" +
					"	AND B.CODIGO_PERSONAL NOT IN" +
					"		(SELECT MATRICULA FROM NOE.FES_COBRO_ONLINE_SEC  B" +
					"			WHERE STATUS = 'I' AND CARGA_ID='"+cargaId+"')" +
					"	AND B.CODIGO_PERSONAL NOT IN" +
					"		(SELECT MATRICULA FROM MATEO.FES_CCOBRO B" +
					"			WHERE CARGA_ID ='"+cargaId+"' AND INSCRITO='S' AND ON_LINE='S')) AS A," +
					
					" (SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.ALUM_ENLINEA B" +
					"	WHERE SOLICITUD='N' AND CARGA_ID='"+cargaId+"'" +
					"	AND (SELECT FACULTAD_ID FROM ENOC.CAT_CARRERA WHERE CARRERA_ID=(ENOC.ALUM_CARRERA_ID(B.CODIGO_PERSONAL)))=ENOC.FACULTAD(ENOC.ALUM_CARRERA_ID(B.CODIGO_PERSONAL))" +
					"	AND B.CODIGO_PERSONAL NOT IN" +
					"		(SELECT MATRICULA FROM NOE.FES_COBRO_ONLINE_SEC  B" +
					"			WHERE STATUS = 'I' AND CARGA_ID='"+cargaId+"')" +
					"	AND B.CODIGO_PERSONAL NOT IN" +
					"		(SELECT MATRICULA FROM MATEO.FES_CCOBRO B" +
					"			WHERE CARGA_ID ='"+cargaId+"' AND INSCRITO='S' AND ON_LINE='S')) AS N," +
					
					" (SELECT COUNT(DISTINCT(MATRICULA)) FROM NOE.FES_COBRO_ONLINE_SEC  B" +
					"	WHERE STATUS = 'I' AND CARGA_ID='"+cargaId+"'" +
					"	AND (SELECT FACULTAD_ID FROM ENOC.CAT_CARRERA WHERE CARRERA_ID=(ENOC.ALUM_CARRERA_ID(B.MATRICULA)))=ENOC.FACULTAD(ENOC.ALUM_CARRERA_ID(B.MATRICULA))" +
					" 	AND B.MATRICULA NOT IN" +
						" (SELECT MATRICULA FROM MATEO.FES_CCOBRO C" +
							" WHERE CARGA_ID ='"+cargaId+"' AND INSCRITO='S' AND ON_LINE='S' "+
							" AND (SELECT FACULTAD_ID FROM ENOC.CAT_CARRERA WHERE CARRERA_ID=(ENOC.ALUM_CARRERA_ID(C.MATRICULA)))=ENOC.FACULTAD(ENOC.ALUM_CARRERA_ID(B.MATRICULA)))) AS C," +
					
					" (SELECT COUNT(DISTINCT(MATRICULA)) FROM MATEO.FES_CCOBRO B" +
					"	WHERE CARGA_ID ='"+cargaId+"' AND INSCRITO='S' AND ON_LINE='S'" +
					"	AND (SELECT FACULTAD_ID FROM ENOC.CAT_CARRERA WHERE CARRERA_ID=(ENOC.ALUM_CARRERA_ID(B.MATRICULA)))=ENOC.FACULTAD(ENOC.ALUM_CARRERA_ID(B.MATRICULA))) AS I" +
					
					" FROM ENOC.ALUM_ENLINEA Z WHERE CARGA_ID='"+cargaId+"' "+(tieneCarreras?"AND (ENOC.ALUM_CARRERA_ID(Z.CODIGO_PERSONAL)) IN ("+carreras+")":"") +
					" GROUP BY ENOC.FACULTAD(ENOC.ALUM_CARRERA_ID(Z.CODIGO_PERSONAL))";
			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			//System.out.println(comando);
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumEnlineaUtil|getMapaCantidadAlumnosPorSolicitudFacultad|:"+ex);
		}			
		
		return mapa;
	}
}
