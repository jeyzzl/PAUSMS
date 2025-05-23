package aca.salida.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class SalSolicitudDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(SalSolicitud salidaSolicitud){
		boolean ok = false;
		
		try{			
			String comando = "INSERT INTO ENOC.SAL_SOLICITUD"
					+ " (SALIDA_ID, FECHA, PROPOSITO_ID, OTRO_PROPOSITO, GRUPO_ID, FECHA_SALIDA,FECHA_LLEGADA, LUGAR, ALIMENTO, DESAYUNO, COMIDA, CENA, HOSPEDAJE, TRANSPORTE,"
					+ " USUARIO, RESPONSABLE, AUTORIZO, FOLIO, TOTAL, TOTAL_PERSONA, COMENTARIO, ESTADO, LUGAR_SALIDA, PREAUTORIZO, TELEFONO, PAIS_ID, ESTADO_ID, PERMISO)"
					+ " VALUES(TO_NUMBER(?,'99999'),TO_DATE(?, 'DD/MM/YYYY'), TO_NUMBER(?,'99'), ?, TO_NUMBER(?,'99'), TO_DATE(?, 'DD/MM/YYYY HH24:MI:SS'), TO_DATE(?, 'DD/MM/YYYY HH24:MI:SS'),"
					+ " ?, ?, TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), TO_NUMBER(?,'99999.99'), TO_NUMBER(?,'99999.99'), ?, ?, ?, ?, TO_NUMBER(?,'99999999.99'), TO_NUMBER(?,'99999.99'), ?, ? ,?, ?, ?,"
					+ " TO_NUMBER(?,'999'),TO_NUMBER(?,'999'),?)";			
			Object[] parametros = new Object[] {
					salidaSolicitud.getSalidaId(),salidaSolicitud.getFecha(),salidaSolicitud.getPropositoId(),salidaSolicitud.getOtroProposito(),salidaSolicitud.getGrupoId(),salidaSolicitud.getFechaSalida(),
					salidaSolicitud.getFechaLlegada(),salidaSolicitud.getLugar(),salidaSolicitud.getAlimento(),salidaSolicitud.getDesayuno(),salidaSolicitud.getComida(),salidaSolicitud.getCena(),
					salidaSolicitud.getHospedaje(),salidaSolicitud.getTransporte(),salidaSolicitud.getUsuario(),salidaSolicitud.getResponsable(),salidaSolicitud.getAutorizo(),salidaSolicitud.getFolio(),
					salidaSolicitud.getTotal(),salidaSolicitud.getTotalPersona(), salidaSolicitud.getComentario(), salidaSolicitud.getEstado(),salidaSolicitud.getLugarSalida(), salidaSolicitud.getPreautorizo(),
					salidaSolicitud.getTelefono(),salidaSolicitud.getPaisId(),salidaSolicitud.getEstadoId(),salidaSolicitud.getPermiso()
			};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;			
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalSolicitudDao|insertReg|:"+ex);
		}

		return ok;
	}
	
	public boolean updateReg(SalSolicitud salidaSolicitud){
        boolean ok = false;
        
        try{        	
            String comando = "UPDATE ENOC.SAL_SOLICITUD "
                    + " SET FECHA = TO_DATE(?, 'DD/MM/YYYY HH24:MI:SS'),"                    
                    + " PROPOSITO_ID = TO_NUMBER(?,'99'),"
                    + " OTRO_PROPOSITO = ?,"
                    + " GRUPO_ID= TO_NUMBER(?, '99'),"
                    + " FECHA_SALIDA = TO_DATE(?, 'DD/MM/YYYY HH24:MI:SS'),"
                    + " FECHA_LLEGADA = TO_DATE(?, 'DD/MM/YYYY HH24:MI:SS'),"
                    + " LUGAR = ?,"
                    + " ALIMENTO = TO_NUMBER(?,'99999.99'),"
                    + " DESAYUNO = TO_NUMBER(?, '999'),"
                    + " COMIDA = TO_NUMBER(?, '999'),"
                    + " CENA = TO_NUMBER(?, '999'),"
                    + " HOSPEDAJE = TO_NUMBER(?, '99999.99'),"
                    + " TRANSPORTE = TO_NUMBER(?, '99999.99'),"
                    + " USUARIO = ?, "
                    + " RESPONSABLE = ?,"
                    + " AUTORIZO = ?,"
                    + " FOLIO = ?,"
                    + " TOTAL = TO_NUMBER(?, '99999999.99'),"
                    + " TOTAL_PERSONA = TO_NUMBER(?, '99999.99'),"
                    + " COMENTARIO = ?,"
                    + " ESTADO = ?,"
                    + " LUGAR_SALIDA = ?,"
                    + " PREAUTORIZO = ?,"
                    + " TELEFONO = ?,"
                    + " PAIS_ID = TO_NUMBER(?,'999'),"
                    + " ESTADO_ID = TO_NUMBER(?,'999'),"                    
                    + " PERMISO = ?"                    
                    + " WHERE SALIDA_ID = TO_NUMBER(?,'99999')";   
            Object[] parametros = new Object[] {
				salidaSolicitud.getFecha(),salidaSolicitud.getPropositoId(),salidaSolicitud.getOtroProposito(),salidaSolicitud.getGrupoId(),salidaSolicitud.getFechaSalida(),
				salidaSolicitud.getFechaLlegada(),salidaSolicitud.getLugar(),salidaSolicitud.getAlimento(),salidaSolicitud.getDesayuno(),salidaSolicitud.getComida(),salidaSolicitud.getCena(),
				salidaSolicitud.getHospedaje(),salidaSolicitud.getTransporte(),salidaSolicitud.getUsuario(),salidaSolicitud.getResponsable(),salidaSolicitud.getAutorizo(),salidaSolicitud.getFolio(),
				salidaSolicitud.getTotal(),salidaSolicitud.getTotalPersona(),salidaSolicitud.getComentario(), salidaSolicitud.getEstado(),salidaSolicitud.getLugarSalida(), salidaSolicitud.getPreautorizo(),
				salidaSolicitud.getTelefono(),salidaSolicitud.getPaisId(),salidaSolicitud.getEstadoId(), salidaSolicitud.getPermiso(), salidaSolicitud.getSalidaId()
			};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;			
			}

        }catch (Exception ex){
            System.out.println("Error - aca.salida.spring.SalSolicitudDao|updateReg|:" + ex);
        }

        return ok;
    }
	
	public boolean updateRegAutorizo(String autorizo, String folio, String salidaId){
        boolean ok = false;
        
        try{
            String comando = "UPDATE ENOC.SAL_SOLICITUD"
                    + " SET AUTORIZO = ? ,"
                    + " FOLIO =  ?"
                    + " WHERE SALIDA_ID = TO_NUMBER(?,'99999')";            
            Object[] parametros = new Object[] {autorizo,folio,salidaId};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;			
			}
     
        }catch (Exception ex){
            System.out.println("Error - aca.salida.spring.SalSolicitudDao|updateRegAutorizo|:" + ex);
        }

        return ok;
    }
	
	public boolean updatePreautorizo(String autorizo, String folio, String salidaId){
        boolean ok = false;        
        try{
            String comando = "UPDATE ENOC.SAL_SOLICITUD SET PREAUTORIZO = ?, FOLIO =  ? WHERE SALIDA_ID = TO_NUMBER(?,'99999')";            
            Object[] parametros = new Object[] {autorizo,folio,salidaId};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;			
			}     
        }catch (Exception ex){
            System.out.println("Error - aca.salida.spring.SalSolicitudDao|updatePreautorizo|:" + ex);
        }
        return ok;
    }
	
	public boolean updatePermiso(String permiso, String salidaId){
		boolean ok = false;        
		try{
			String comando = "UPDATE ENOC.SAL_SOLICITUD SET PERMISO = ? WHERE SALIDA_ID = TO_NUMBER(?,'99999')";            
			Object[] parametros = new Object[] {permiso,salidaId};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;			
			}     
		}catch (Exception ex){
			System.out.println("Error - aca.salida.spring.SalSolicitudDao|updatePermiso|:" + ex);
		}
		return ok;
	}

    public boolean deleteReg(String salidaId){
    	boolean ok = false;
        try {
            String comando = "DELETE FROM ENOC.SAL_SOLICITUD WHERE SALIDA_ID = TO_NUMBER(?, '99999')";            
            Object[] parametros = new Object[]{
        		salidaId
            };            
            if (enocJdbc.update(comando, parametros)==1) {
            	ok = true;
            }            
        }catch (Exception ex){
            System.out.println("Error - aca.salida.spring.SalSolicitudDao|deleteReg|:" + ex);
        }
        
        return ok;
    }

	public SalSolicitud mapeaRegId(String salidaId){
		SalSolicitud salidaSolicitud = new SalSolicitud();
		try{ 
			String comando = "SELECT SALIDA_ID, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, PROPOSITO_ID, OTRO_PROPOSITO,"
					+ " GRUPO_ID, TO_CHAR(FECHA_SALIDA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA_SALIDA,"
					+ " TO_CHAR(FECHA_LLEGADA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA_LLEGADA, LUGAR, ALIMENTO,"
					+ " DESAYUNO, COMIDA, CENA, HOSPEDAJE, TRANSPORTE, USUARIO, RESPONSABLE, AUTORIZO, FOLIO, TOTAL,"
					+ " TOTAL_PERSONA, COMENTARIO, ESTADO, LUGAR_SALIDA, PREAUTORIZO, TELEFONO, PAIS_ID, ESTADO_ID, PERMISO"
					+ " FROM ENOC.SAL_SOLICITUD"
					+ " WHERE SALIDA_ID = TO_NUMBER(?,'99999')";
			Object[] parametros = new Object[] {salidaId};
			salidaSolicitud = enocJdbc.queryForObject(comando, new SalSolicitudMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalSolicitudDao|mapeaRegId|:"+ex);
		}

		return salidaSolicitud; 
	}
	
	public boolean existeReg(String salidaId){
		boolean ok 		= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.SAL_SOLICITUD WHERE SALIDA_ID = TO_NUMBER(?,'99999')";					
			Object[] parametros = new Object[] {salidaId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalSolicitudDao|existeReg|:"+ex);
		}		
		return ok;
	}
	
	public int salidasPorEstado(String usuario, String estado){
		int total 		= 0;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.SAL_SOLICITUD"
					+ " WHERE ESTADO = ?"
					+ " AND AUTORIZO = '0' "
					+ " AND FOLIO = 0"
					+ " AND INSTR(SAL_GRUPO_USUARIOS(GRUPO_ID),?) >= 1";					
			Object[] parametros = new Object[] {estado, usuario};
			total = enocJdbc.queryForObject(comando,Integer.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalSolicitudDao|salidasPorEstado|:"+ex);
		}
		
		return total;
	}
	
	public String maximoReg(){
		String maximo 	= "1";		
		try{
			String comando = "SELECT COALESCE(MAX(SALIDA_ID)+1, 1) AS MAXIMO FROM ENOC.SAL_SOLICITUD";					
			Object[] parametros = new Object[] {};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalSolicitudDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public List<SalSolicitud> getListAll(String orden ){
		List<SalSolicitud> lista 	= new ArrayList<SalSolicitud>();
		
		try{
			String comando = "SELECT SALIDA_ID, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, PROPOSITO_ID, OTRO_PROPOSITO, GRUPO_ID,"
					+ " TO_CHAR(FECHA_SALIDA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA_SALIDA, "
					+ " TO_CHAR(FECHA_LLEGADA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA_LLEGADA,"
					+ " LUGAR, ALIMENTO, DESAYUNO, COMIDA, CENA, HOSPEDAJE, TRANSPORTE, USUARIO, RESPONSABLE, AUTORIZO, FOLIO, "
					+ " TOTAL, TOTAL_PERSONA, COMENTARIO, ESTADO, LUGAR_SALIDA, PREAUTORIZO, TELEFONO, PAIS_ID, ESTADO_ID, PERMISO"
					+ " FROM ENOC.SAL_SOLICITUD "+orden; 
			
			lista = enocJdbc.query(comando, new SalSolicitudMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalSolicitudDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public List<SalSolicitud> getListUsuario(String codigoPersonal, String orden ){
		List<SalSolicitud> lista 	= new ArrayList<SalSolicitud>();
		
		try{
			String comando = "SELECT SALIDA_ID, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, PROPOSITO_ID, OTRO_PROPOSITO, GRUPO_ID,"
					+ " TO_CHAR(FECHA_SALIDA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA_SALIDA,"
					+ " TO_CHAR(FECHA_LLEGADA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA_LLEGADA,"
					+ " LUGAR, ALIMENTO, DESAYUNO, COMIDA, CENA, HOSPEDAJE, TRANSPORTE, USUARIO, RESPONSABLE, AUTORIZO, FOLIO,"
					+ " TOTAL, TOTAL_PERSONA, COMENTARIO, ESTADO, LUGAR_SALIDA, PREAUTORIZO, TELEFONO, PAIS_ID, ESTADO_ID, PERMISO"
					+ " FROM ENOC.SAL_SOLICITUD "
					+ " WHERE GRUPO_ID IN (SELECT GRUPO_ID FROM ENOC.SAL_GRUPO WHERE USUARIOS LIKE '%-"+codigoPersonal+"-%') "+orden;			
			lista = enocJdbc.query(comando, new SalSolicitudMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalSolicitudDao|getListUsuario|:"+ex);
		}
		
		return lista;
	}
	
	public List<SalSolicitud> lisUsuario(String codigoPersonal, String orden ){
		List<SalSolicitud> lista 	= new ArrayList<SalSolicitud>();		
		try{
			String comando = "SELECT SALIDA_ID, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, PROPOSITO_ID, OTRO_PROPOSITO, GRUPO_ID,"
					+ " TO_CHAR(FECHA_SALIDA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA_SALIDA,"
					+ " TO_CHAR(FECHA_LLEGADA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA_LLEGADA,"
					+ " LUGAR, ALIMENTO, DESAYUNO, COMIDA, CENA, HOSPEDAJE, TRANSPORTE, USUARIO, RESPONSABLE, AUTORIZO, FOLIO,"
					+ " TOTAL, TOTAL_PERSONA, COMENTARIO, ESTADO, LUGAR_SALIDA, PREAUTORIZO, TELEFONO, PAIS_ID, ESTADO_ID, PERMISO"
					+ " FROM ENOC.SAL_SOLICITUD "
					+ " WHERE USUARIO = ? "+orden;			
			lista = enocJdbc.query(comando, new SalSolicitudMapper(),codigoPersonal);			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalSolicitudDao|getListUsuario|:"+ex);
		}
		
		return lista;
	}
	
	public List<SalSolicitud> lisAutorizadasPorUsuario(String codigoPersonal, String orden ){
		List<SalSolicitud> lista 	= new ArrayList<SalSolicitud>();
		try{
			String comando = "SELECT SALIDA_ID, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, PROPOSITO_ID, OTRO_PROPOSITO, GRUPO_ID,"
					+ " TO_CHAR(FECHA_SALIDA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA_SALIDA,"
					+ " TO_CHAR(FECHA_LLEGADA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA_LLEGADA,"
					+ " LUGAR, ALIMENTO, DESAYUNO, COMIDA, CENA, HOSPEDAJE, TRANSPORTE, USUARIO, RESPONSABLE, AUTORIZO, FOLIO,"
					+ " TOTAL, TOTAL_PERSONA, COMENTARIO, ESTADO, LUGAR_SALIDA, PREAUTORIZO, TELEFONO, PAIS_ID, ESTADO_ID, PERMISO"
					+ " FROM ENOC.SAL_SOLICITUD "
					+ " WHERE USUARIO = ?"
					+ " AND FOLIO != '0' "+orden;			
			Object[] parametros = new Object[]{ codigoPersonal };
			lista = enocJdbc.query(comando, new SalSolicitudMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalSolicitudDao|lisAutorizadasPorUsuario|:"+ex);
		}
		
		return lista;
	}
	
	public List<SalSolicitud> lisAutorizadasPorUsuario(String codigoPersonal, String fecha, String orden ){
		List<SalSolicitud> lista 	= new ArrayList<SalSolicitud>();
		try{
			String comando = "SELECT SALIDA_ID, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, PROPOSITO_ID, OTRO_PROPOSITO, GRUPO_ID,"
					+ " TO_CHAR(FECHA_SALIDA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA_SALIDA,"
					+ " TO_CHAR(FECHA_LLEGADA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA_LLEGADA,"
					+ " LUGAR, ALIMENTO, DESAYUNO, COMIDA, CENA, HOSPEDAJE, TRANSPORTE, USUARIO, RESPONSABLE, AUTORIZO, FOLIO,"
					+ " TOTAL, TOTAL_PERSONA, COMENTARIO, ESTADO, LUGAR_SALIDA, PREAUTORIZO, TELEFONO, PAIS_ID, ESTADO_ID, PERMISO"
					+ " FROM ENOC.SAL_SOLICITUD "
					+ " WHERE USUARIO = ?"
					+ " AND FECHA_LLEGADA < TO_DATE(?,'DD/MM/YYYY')"
					+ " AND FOLIO != '0' "+orden;
			 Object[] parametros = new Object[]{ codigoPersonal, fecha };
			lista = enocJdbc.query(comando, new SalSolicitudMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalSolicitudDao|lisAutorizadasPorUsuario|:"+ex);
		}		
		return lista;
	}
	
	public List<SalSolicitud> listConComidas(String fechaIni, String fechaFin, String orden ) {
		List<SalSolicitud> lista 	= new ArrayList<SalSolicitud>();
		
		try{
			String comando = "SELECT SALIDA_ID, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, PROPOSITO_ID, OTRO_PROPOSITO, GRUPO_ID,"
					+ " TO_CHAR(FECHA_SALIDA, 'YYYY/MM/DD HH24:MI:SS') AS FECHA_SALIDA,"
					+ " TO_CHAR(FECHA_LLEGADA, 'YYYY/MM/DD HH24:MI:SS') AS FECHA_LLEGADA,"
					+ " LUGAR, ALIMENTO, DESAYUNO, COMIDA, CENA, HOSPEDAJE, TRANSPORTE, USUARIO, RESPONSABLE, "
					+ " AUTORIZO, FOLIO, TOTAL, TOTAL_PERSONA, COMENTARIO, ESTADO, LUGAR_SALIDA, PREAUTORIZO, TELEFONO, PAIS_ID, ESTADO_ID, PERMISO"
					+ " FROM ENOC.SAL_SOLICITUD"
					+ " WHERE FECHA_SALIDA BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')"
					+ " AND DESAYUNO+COMIDA+CENA > 0 "+orden; 
			Object[] parametros = new Object[] {fechaIni, fechaFin};
			lista = enocJdbc.query(comando, new SalSolicitudMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalSolicitudDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public List<SalSolicitud> lisUsuarioPorFecha( String codigoPersonal, String fechaIni, String fechaFin, String orden ){
		List<SalSolicitud> lista 	= new ArrayList<SalSolicitud>();
		
		try{
			String comando = " SELECT SALIDA_ID, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, PROPOSITO_ID, OTRO_PROPOSITO, GRUPO_ID,"
					+ " TO_CHAR(FECHA_SALIDA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA_SALIDA,"
					+ " TO_CHAR(FECHA_LLEGADA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA_LLEGADA,"
			        + " LUGAR, ALIMENTO, DESAYUNO, COMIDA, CENA, HOSPEDAJE, TRANSPORTE, USUARIO, RESPONSABLE, AUTORIZO,"
			        + " FOLIO, TOTAL, TOTAL_PERSONA, COMENTARIO, ESTADO, LUGAR_SALIDA, PREAUTORIZO, TELEFONO, PAIS_ID, ESTADO_ID, PERMISO"
					+ " FROM ENOC.SAL_SOLICITUD"
					+ " WHERE GRUPO_ID IN (SELECT GRUPO_ID FROM ENOC.SAL_GRUPO WHERE USUARIOS LIKE '%-"+codigoPersonal+"-%')"
					+ " AND ENOC.SAL_SOLICITUD.FECHA_SALIDA BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY') "+orden; 
			Object[] parametros = new Object[] {fechaIni, fechaFin};
			lista = enocJdbc.query(comando, new SalSolicitudMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.SalidaSolicitudUtil|getListUsuarioPorFecha|:"+ex);
		}
		
		return lista;
	}
	
	public List<SalSolicitud> lisUsuarioPorFecha( String fechaIni, String fechaFin, String orden ){
		List<SalSolicitud> lista 	= new ArrayList<SalSolicitud>();
		try{
			String comando = " SELECT SALIDA_ID, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, PROPOSITO_ID, OTRO_PROPOSITO, GRUPO_ID,"
					+ " TO_CHAR(FECHA_SALIDA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA_SALIDA,"
					+ " TO_CHAR(FECHA_LLEGADA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA_LLEGADA,"
			        + " LUGAR, ALIMENTO, DESAYUNO, COMIDA, CENA, HOSPEDAJE, TRANSPORTE, USUARIO, RESPONSABLE, AUTORIZO, "
			        + " FOLIO, TOTAL, TOTAL_PERSONA, COMENTARIO, ESTADO, LUGAR_SALIDA, PREAUTORIZO, TELEFONO, PAIS_ID, ESTADO_ID, PERMISO"
					+ " FROM ENOC.SAL_SOLICITUD"
					+ " WHERE ENOC.SAL_SOLICITUD.FECHA_SALIDA BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY') "+orden; 
			Object[] parametros = new Object[] {fechaIni, fechaFin};
			lista = enocJdbc.query(comando, new SalSolicitudMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalSolicitudDao|lisUsuarioPorFecha|:"+ex);
		}
		
		return lista;
	}
	
	public List<SalSolicitud> lisPorGrupoyFecha(String grupoId, String fechaIni, String fechaFin, String orden ){
		List<SalSolicitud> listSalidaSolicitud 	= new ArrayList<SalSolicitud>();
		String filtro 	= "";
		if (!grupoId.equals("0")) {
			filtro = " AND GRUPO_ID = "+grupoId+" ";
		}
		try{
			String comando = " SELECT SALIDA_ID, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, PROPOSITO_ID, OTRO_PROPOSITO, GRUPO_ID,"
					+ " TO_CHAR(FECHA_SALIDA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA_SALIDA,"
					+ " TO_CHAR(FECHA_LLEGADA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA_LLEGADA,"
			        + " LUGAR, ALIMENTO, DESAYUNO, COMIDA, CENA, HOSPEDAJE, TRANSPORTE, USUARIO, RESPONSABLE, AUTORIZO, FOLIO, TOTAL, TOTAL_PERSONA, COMENTARIO, ESTADO, LUGAR_SALIDA, PREAUTORIZO, "
			        + " TELEFONO, PAIS_ID, ESTADO_ID, PERMISO"
			        + " FROM ENOC.SAL_SOLICITUD"
					+ " WHERE ENOC.SAL_SOLICITUD.FECHA_SALIDA BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY') "
				    + filtro + orden; 
			
			listSalidaSolicitud = enocJdbc.query(comando, new SalSolicitudMapper(),fechaIni,fechaFin);
			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalSolicitudDao|lisPorGrupoyFecha|:"+ex);
		}
		
		return listSalidaSolicitud;
	}

	public HashMap<String,String> mapNombreGrupo() {
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT GRUPO_ID AS LLAVE, GRUPO_NOMBRE AS VALOR FROM ENOC.SAL_GRUPO ORDER BY GRUPO_ID"; 
			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalSolicitudDao|MapNombreGrupo|:"+ex);
		}
		
		return mapa;
	}

	public HashMap<String,String> mapAutorizadas() {
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT SALIDA_ID AS LLAVE, SALIDA_ID AS VALOR FROM ENOC.SAL_SOLICITUD WHERE AUTORIZO != '0'"; 
			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalSolicitudDao|mapAutorizadas|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaPorGrupo() {
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT GRUPO_ID AS LLAVE, COUNT(SALIDA_ID) AS VALOR FROM ENOC.SAL_SOLICITUD GROUP BY GRUPO_ID";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalSolicitudDao|mapaPorGrupo|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,SalSolicitud> mapaConInforme() {
		HashMap<String,SalSolicitud> mapa = new HashMap<String,SalSolicitud>();
		List<SalSolicitud> lista		= new ArrayList<SalSolicitud>();
		
		try{
			String comando = " SELECT SALIDA_ID, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, PROPOSITO_ID, OTRO_PROPOSITO, GRUPO_ID,"
					+ " TO_CHAR(FECHA_SALIDA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA_SALIDA,"
					+ " TO_CHAR(FECHA_LLEGADA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA_LLEGADA,"
			        + " LUGAR, ALIMENTO, DESAYUNO, COMIDA, CENA, HOSPEDAJE, TRANSPORTE, USUARIO, RESPONSABLE, AUTORIZO, FOLIO, TOTAL, TOTAL_PERSONA, COMENTARIO, ESTADO, LUGAR_SALIDA, PREAUTORIZO,"
			        + " TELEFONO, PAIS_ID, ESTADO_ID, PERMISO"
			        + " FROM ENOC.SAL_SOLICITUD"
			        + " WHERE SALIDA_ID IN (SELECT SALIDA_ID FROM ENOC.SAL_INFORME)";		
			lista = enocJdbc.query(comando, new SalSolicitudMapper());
			for (SalSolicitud solicitud : lista ) {
				mapa.put(solicitud.getSalidaId(), solicitud);
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalSolicitudDao|mapaConInforme|:"+ex);
		}
		
		return mapa;
	}

	public HashMap<String,String> mapaComAutorizacion(String salidaId) {
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = " SELECT MATRICULA AS LLAVE,TIPO_COMIDA AS VALOR FROM NOE.COM_AUTORIZACION WHERE MATRICULA "
					+ " IN (SELECT CODIGO_PERSONAL FROM ENOC.SAL_ALUMNO WHERE SALIDA_ID = "+salidaId+") AND INSCRITO = 'S' "
					+ " AND (SELECT FECHA_SALIDA FROM ENOC.SAL_SOLICITUD WHERE SALIDA_ID = "+salidaId+") BETWEEN FECHA_INICIAL AND FECHA_FINAL";
			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalSolicitudDao|mapaComAutorizacion|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaPropositos() {
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT PROPOSITO_ID AS LLAVE, COUNT(PROPOSITO_ID) AS VALOR FROM ENOC.SAL_SOLICITUD GROUP BY PROPOSITO_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalSolicitudDao|mapaPropositos|:"+ex);
		}		
		return mapa;
	}
	
}