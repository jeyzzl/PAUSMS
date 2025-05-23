package aca.acceso.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Order(1)
@Component
public class AccesoDao {
	
	@Autowired	
	@Qualifier("jdbcEnoc")	
	private JdbcTemplate enocJdbc;
	
	/* Verifica si existe */
	
	public boolean insertReg( Acceso acceso ){
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.ACCESO(CODIGO_PERSONAL, ADMINISTRADOR, SUPERVISOR, COTEJADOR, ACCESOS, MODALIDAD, USUARIO, CLAVE, INGRESO, CONVALIDA, BECAS, PORTAL_ALUMNO, PORTAL_MAESTRO, IDIOMA, MENU, ALUMNO_MOVIL, CLAVE_INICIAL, CLAVE_HEXA, ENLINEA, BUSCA_ADMIN)"
					+ " VALUES(?,?,?,?,?,TO_NUMBER(?,'99'),?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			Object[] parametros = new Object[] {
				acceso.getCodigoPersonal(), acceso.getAdministrador(), acceso.getSupervisor(), acceso.getCotejador(), acceso.getAccesos(),
				acceso.getModalidad(), acceso.getUsuario(), acceso.getClave(), acceso.getIngreso(), acceso.getConvalida(),acceso.getBecas(), 
				acceso.getPortalAlumno(), acceso.getPortalMaestro(), acceso.getIdioma(), acceso.getMenu(), acceso.getAlumnoMovil(), 
				acceso.getClaveInicial(), acceso.getClaveHexa(), acceso.getEnLinea(),acceso.getBuscaAdmin()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.AccesoDao|insertReg|:"+ex);		
		}
		return ok;
	}
	
	public boolean updateReg(Acceso acceso ){
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.ACCESO SET ADMINISTRADOR = ?, SUPERVISOR= ?, COTEJADOR= ?, ACCESOS= ?, MODALIDAD= TO_NUMBER(?,'99'), USUARIO= ?, CLAVE= ?,"
					+ " INGRESO = ?, CONVALIDA = ?, BECAS = ?, PORTAL_ALUMNO = ?, PORTAL_MAESTRO = ?, IDIOMA = ?, MENU = ?, ALUMNO_MOVIL = ?, CLAVE_INICIAL = ?,"
					+ " CLAVE_HEXA = ?, ENLINEA = ?, BUSCA_ADMIN = ?"
					+ " WHERE CODIGO_PERSONAL = ?";			 
			Object[] parametros = new Object[] {
				acceso.getAdministrador(), acceso.getSupervisor(), acceso.getCotejador(), acceso.getAccesos(), acceso.getModalidad(),
				acceso.getUsuario(), acceso.getClave(), acceso.getIngreso(), acceso.getConvalida(), acceso.getBecas(),
				acceso.getPortalAlumno(), acceso.getPortalMaestro(), acceso.getIdioma(), acceso.getMenu(), acceso.getAlumnoMovil(), 
				acceso.getClaveInicial(), acceso.getClaveHexa(), acceso.getEnLinea(),acceso.getBuscaAdmin(), acceso.getCodigoPersonal()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.AccesoDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	public boolean updateClave(String codigoPersonal, String clave ){
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.ACCESO SET CLAVE = ? WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] { clave, codigoPersonal };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.AccesoDao|updateClave|:"+ex);		
		}
		return ok;
	}
	
	public boolean updateClaveHexa(String codigoPersonal, String clave ){
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.ACCESO SET CLAVE_HEXA = ? WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] { clave, codigoPersonal };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.AccesoDao|updateClaveHexa|:"+ex);
		}
		return ok;
	}
	
	public boolean updateAccesos(String codigoPersonal, String accesos ){
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.ACCESO SET ACCESOS = ? WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] { accesos, codigoPersonal };
			if (enocJdbc.update(comando,parametros) == 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.AccesoDao|updateAccesos|:"+ex);		
		}
		return ok;
	}
	
	public boolean updateAlumnoMovil(String codigoPersonal, String movil ){
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.ACCESO SET ALUMNO_MOVIL = ? WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] { movil, codigoPersonal };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.AccesoDao|updateAlumnoMovil|:"+ex);
		}
		return ok;
	}
	
	public boolean deleteReg(String codigoPersonal ){
		boolean ok = false;		
		try{
			String comando ="DELETE FROM ENOC.ACCESO WHERE CODIGO_PERSONAL = ?"; 
			Object[] parametros = new Object[]{codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.AccesoDao|deleteReg|:"+ex);		
		}
		return ok;
	}
	
	public boolean existeReg(String codigoPersonal){
		boolean ok = false;
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ACCESO WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[]{codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				ok = true;
			}
		}catch( Exception ex){
			System.out.println("Error: aca.acceso.spring.AccesoDao||existeReg :"+ex);
		}
		
		return ok;		
	}
	
	public boolean existeUsuario(String usuario){
		boolean ok = false;
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ACCESO WHERE USUARIO = ?";
			Object[] parametros = new Object[]{usuario};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				ok = true;
			}
		}catch( Exception ex){
			System.out.println("Error: aca.acceso.spring.AccesoDao||existeReg :"+ex);
		}
		
		return ok;		
	}
	
	public boolean esAdministrador(String codigoPersonal){
		boolean ok		= false;	
		String comando	= "";		
		try{
			comando = "SELECT COUNT(*) FROM ENOC.ACCESO WHERE CODIGO_PERSONAL = ? AND ADMINISTRADOR = 'S'";
			if (enocJdbc.queryForObject(comando,Integer.class, codigoPersonal) >= 1){
				ok = true;			
			}
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoDao|esAdministrador|:"+ex);
		}
		
		return ok;
	}
	
	public boolean esSupervisor(String codigoPersonal){
		boolean ok		= false;				
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ACCESO WHERE CODIGO_PERSONAL = ? AND SUPERVISOR = 'S'";
			if (enocJdbc.queryForObject(comando,Integer.class, codigoPersonal) >= 1){
				ok = true;			
			}
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoDao|esSupervisor|:"+ex);
		}		
		return ok;
	}
	
	public boolean esCotejador(String codigoPersonal){
		boolean ok	=false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ACCESO WHERE CODIGO_PERSONAL = ? AND COTEJADOR = 'S'";			 
			if (enocJdbc.queryForObject(comando,Integer.class, codigoPersonal) >= 1){
				ok = true;			
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoDao|esCotejador|:"+ex);
		}
		
		return ok;
	}
	
	public boolean esConvalidador( String codigoPersonal){
		boolean ok	=false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ACCESO WHERE CODIGO_PERSONAL = ? AND CONVALIDA = 'S'";
			if (enocJdbc.queryForObject(comando,Integer.class, codigoPersonal) >= 1){
				ok = true;			
			}
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoDao|esConvalidador|:"+ex);
		}
		
		return ok;
	}
	
	public String getAccesos(String codigoPersonal){
		String accesos 		= " ";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ACCESO WHERE CODIGO_PERSONAL = ?";
			if (enocJdbc.queryForObject(comando,Integer.class, codigoPersonal) >= 1){
				comando = "SELECT COALESCE(TRIM(ACCESOS),' ') FROM ENOC.ACCESO WHERE CODIGO_PERSONAL = ?";
				accesos = enocJdbc.queryForObject(comando, String.class, codigoPersonal);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoDao|getAccesos|:"+ex);
		}		
		return accesos;
	}
	
	public boolean getValidaUsuario( String usuario){
		
 		boolean valida	 		= false; 		
 		try{
 			String comando = "SELECT COUNT(*) FROM ENOC.ACCESO WHERE USUARIO = ?";	
 			Object[] parametros = new Object[]{usuario};
 			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
 				valida = true;
			}
 		}catch(Exception ex){
 			System.out.println("Error - aca.acceso.spring.AccesoDao|getValidaUsuario|:"+ex);
 		}
 		
 		return valida;
 	}
	
	public String getCodigoDeUsuario( String usuario){
		
 		String codigo	= "0";
 		try{
 			String comando = "SELECT COUNT(*) FROM ENOC.ACCESO WHERE USUARIO = ?";	
 			Object[] parametros = new Object[]{usuario};
 			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
 				comando = "SELECT CODIGO_PERSONAL FROM ENOC.ACCESO WHERE USUARIO = ?";
 				codigo = enocJdbc.queryForObject(comando,String.class, parametros);
			}
 		}catch(Exception ex){
 			System.out.println("Error - aca.acceso.spring.AccesoDao|getCodigoDeUsuario|:"+ex);
 		}
 		
 		return codigo;
 	}

	 public String getUsuario( String codigoPersonal){
		
		String codigo	= "0";
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ACCESO WHERE CODIGO_PERSONAL = ?";	
			Object[] parametros = new Object[]{codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				comando = "SELECT USUARIO FROM ENOC.ACCESO WHERE CODIGO_PERSONAL = ?";
				codigo = enocJdbc.queryForObject(comando,String.class, parametros);
		   }
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoDao|getCodigoDeUsuario|:"+ex);
		}
		
		return codigo;
	}
 	
 	public boolean getValidaClave( String usuario, String clave) {
 		boolean valida	 		= false; 		
 		try{
 			String comando = "SELECT COUNT(*) FROM ENOC.ACCESO WHERE USUARIO = ? AND CLAVE = ?";
 			Object[] parametros = new Object[]{usuario, clave};	
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
 				valida = true;
			}
 		}catch(Exception ex){
 			System.out.println("Error - aca.acceso.AccesoUspring.AccesoDaotil|getValidaClave|:"+ex);
 		} 		
 		return valida;
 	}
 	
 	public boolean getValidaClaveHexa( String usuario, String clave) {
 		boolean valida	 		= false; 		
 		try{
 			String comando = "SELECT COUNT(*) FROM ENOC.ACCESO WHERE USUARIO = ? AND CLAVE_HEXA = ?";
 			Object[] parametros = new Object[]{usuario, clave};	
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
 				valida = true;
			}
 		}catch(Exception ex){
 			System.out.println("Error - aca.acceso.AccesoUspring.AccesoDaotil|getValidaClaveHexa|:"+ex);
 		} 		
 		return valida;
 	}
 	
 	public boolean getValidaClaveSha( String usuario, String clave) {
 		boolean valida	 		= false; 		
 		try{
 			String comando = "SELECT COUNT(*) FROM ENOC.ACCESO WHERE USUARIO = ? AND ALUMNO_MOVIL = ?";
 			Object[] parametros = new Object[]{usuario, clave};	
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
 				valida = true;
			}
 		}catch(Exception ex){
 			System.out.println("Error - aca.acceso.AccesoUspring.AccesoDaotil|getValidaClave|:"+ex);
 		} 		
 		return valida;
 	}
 	
 	public String getEsUsuario( String  usuario, String clave ){
		String codigoPersonal 		= "x";
		usuario = usuario.replaceAll("'", "");
		try{
			String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.ACCESO"
					+ " WHERE LENGTH(USUARIO) = LENGTH(?)"
					+ " AND LENGTH(CLAVE) = LENGTH(?)"
					+ " AND USUARIO = ? AND CLAVE = ?";
			Object[] parametros = new Object[]{usuario, clave, usuario, clave};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				comando = " SELECT CODIGO_PERSONAL FROM ENOC.ACCESO"
						+ " WHERE LENGTH(USUARIO) = LENGTH(?)"
						+ " AND LENGTH(CLAVE) = LENGTH(?)"
						+ " AND USUARIO = ?"
						+ " AND CLAVE = ?";
				codigoPersonal = enocJdbc.queryForObject(comando,String.class, parametros);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoDao|getEsUsuario|:"+ex);
		}
		
		return codigoPersonal;
	}
 	
 	public String getEsUsuario( String  usuario, String clave, String claveSha ){
		String codigoPersonal 		= "x";
		usuario = usuario.replaceAll("'", "");
		try{
			String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.ACCESO"
					+ " WHERE LENGTH(USUARIO) = LENGTH(?)"
					+ " AND USUARIO = ? AND ( CLAVE = ? OR CLAVE = ?)";
			Object[] parametros = new Object[]{usuario, usuario, clave, claveSha};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				comando = " SELECT CODIGO_PERSONAL FROM ENOC.ACCESO"
						+ " WHERE LENGTH(USUARIO) = LENGTH(?)"
						+ " AND LENGTH(CLAVE) = LENGTH(?)"
						+ " AND USUARIO = ?"
						+ " AND (CLAVE = ? OR CLAVE = ?)";
				codigoPersonal = enocJdbc.queryForObject(comando,String.class, parametros);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoDao|getEsUsuario|:"+ex);
		}
		
		return codigoPersonal;
	}
	
	/* Verifica si existe el codigo y la clave */
	public boolean validaClave(String codigoPersonal, String clave){
		boolean ok = false;
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ACCESO WHERE CODIGO_PERSONAL = ? AND CLAVE = ?";
			Object[] parametros = new Object[]{codigoPersonal, clave};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				ok = true;
			}
		}catch( Exception ex){
			System.out.println("Error: aca.acceso.spring.AccesoDao||validaClave :"+ex);
		}
		
		return ok;
	}
	
	public Acceso mapeaRegId(String codigoPersonal){
		Acceso objeto = new Acceso();		
		String accesos 	= "X";
		List<String> lisCarreras = new ArrayList<String>();
		try{			
			String query = "SELECT COUNT(*) FROM ENOC.ACCESO WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[]{codigoPersonal};
			if (enocJdbc.queryForObject(query, Integer.class, parametros)>= 1) {
				query = "SELECT CODIGO_PERSONAL, ADMINISTRADOR, SUPERVISOR, COTEJADOR, COALESCE(ACCESOS,'X') AS ACCESOS,"
					+ " MODALIDAD, USUARIO, CLAVE, INGRESO, CONVALIDA, BECAS, PORTAL_ALUMNO, PORTAL_MAESTRO, IDIOMA, MENU, ALUMNO_MOVIL,"
					+ " CLAVE_INICIAL, CLAVE_HEXA,ENLINEA,BUSCA_ADMIN"
					+ " FROM ENOC.ACCESO"
					+ " WHERE CODIGO_PERSONAL = ?";
				objeto = enocJdbc.queryForObject(query, new AccesoMapper(), parametros);				
				query = "SELECT COUNT(*) FROM ENOC.ACCESO_PLAN WHERE CODIGO_PERSONAL = ?";
				if (enocJdbc.queryForObject(query, Integer.class, parametros)>= 1) {
					query = "SELECT DISTINCT(ENOC.CARRERA(PLAN_ID)) FROM ENOC.ACCESO_PLAN WHERE CODIGO_PERSONAL = ?";
					lisCarreras = enocJdbc.queryForList(query, String.class, parametros);
					for (String carrera : lisCarreras) {
						if (accesos.equals("X")) accesos = carrera; else accesos += " "+carrera;
					}
				}
				objeto.setAccesos(accesos);
			}		
		}catch( Exception ex){
			System.out.println("Error: aca.acceso.spring.AccesoDao||"+ex);
		}
		
		return objeto;
	}
	
	public Acceso mapeaPorUsuario(String usuario){
		Acceso objeto = new Acceso();		
		try{			
			String query = "SELECT COUNT(*) FROM ENOC.ACCESO WHERE USUARIO = ?";
			Object[] parametros = new Object[]{usuario};
			if (enocJdbc.queryForObject(query, Integer.class, parametros)>= 1) {
				query = "SELECT CODIGO_PERSONAL, ADMINISTRADOR, SUPERVISOR, COTEJADOR, COALESCE(ACCESOS,'X') AS ACCESOS,"
						+ " MODALIDAD, USUARIO, CLAVE, INGRESO, CONVALIDA, BECAS, PORTAL_ALUMNO, PORTAL_MAESTRO, IDIOMA, MENU, ALUMNO_MOVIL, "
						+ " CLAVE_INICIAL, CLAVE_HEXA, ENLINEA,BUSCA_ADMIN"
						+ " FROM ENOC.ACCESO"
						+ " WHERE USUARIO = ?";
				objeto = enocJdbc.queryForObject(query, new AccesoMapper(), parametros);
			}			
		}catch( Exception ex){
			System.out.println("Error: aca.acceso.spring.AccesoDao||"+ex);
		}
		
		return objeto;
	}
	
	public boolean updateClave(String usuario, String clave, String codigoPersonal){
 		boolean ok = false; 		
 		try{ 			
 			String comando = "UPDATE ENOC.ACCESO SET USUARIO = ?, CLAVE = ? WHERE CODIGO_PERSONAL = ? ";
 			Object[] parametros = new Object[]{usuario, clave, codigoPersonal};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			} 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.acceso.spring.AccesoDao|updateClave|:"+ex);
 		}
 		
 		return ok;
 	}
	
	public boolean updateIngreso(String codigoPersonal, String ingreso){
 		boolean ok = false; 		
 		try{ 			
 			String comando = "UPDATE ENOC.ACCESO SET INGRESO = ? WHERE CODIGO_PERSONAL = ? ";
 			Object[] parametros = new Object[]{ingreso, codigoPersonal};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			} 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.acceso.spring.AccesoDao|updateIngreso|:"+ex);
 		}
 		
 		return ok;
 	}
	
	public String getModalidad(String  usuario){		
		String modalidad 	= "0";
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ACCESO WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[]{usuario};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT MODALIDAD FROM ENOC.ACCESO WHERE CODIGO_PERSONAL = ?";
				modalidad = enocJdbc.queryForObject(comando, String.class, parametros);
			}else{
				modalidad = "x";
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoDao|getModalidad|:"+ex);
		}
		
		return modalidad;
	}
	
	public String getPortalMaestro( String  usuario){		
		String portal	 		= "N";
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ACCESO WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[]{usuario};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>= 1){
				comando = "SELECT PORTAL_MAESTRO FROM ENOC.ACCESO WHERE CODIGO_PERSONAL = ?";
				portal = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoDao|getPortalMaestro|:"+ex);		
		}		
		return portal;
	}
	
	public boolean getBecas(String usuario){		
		boolean becas 			= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ACCESO WHERE CODIGO_PERSONAL = ? AND BECAS = 'S'";
			Object[] parametros = new Object[]{usuario};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {			
				becas = true;
			}			
		}catch(Exception ex){ 
			System.out.println("Error - aca.acceso.spring.AccesoDao|getBecas|:"+ex);
		}
		
		return becas;
	}
	
	public String getPortalAlumno(String  usuario){		
		String portal 			= "0";
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ACCESO WHERE CODIGO_PERSONAL = ? ";
			Object[] parametros = new Object[]{usuario};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>= 1){
				comando = "SELECT PORTAL_ALUMNO FROM ENOC.ACCESO WHERE CODIGO_PERSONAL = ? ";
				portal = enocJdbc.queryForObject(comando, String.class, parametros);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoDao|getPortalAlumno|:"+ex);
		}		
		return portal;
	}
	
	public String getIdioma(String  usuario){		
		String idioma 			= "es";
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ACCESO WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[]{usuario};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>= 1){
				comando = "SELECT IDIOMA FROM ENOC.ACCESO WHERE CODIGO_PERSONAL = ?";
				idioma = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoDao|getIdioma|:"+ex);			
		}		
		return idioma;
	}
	
	public String getIngreso(String  codigoPersonal){
		String ingreso 			= "N";
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ACCESO WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[]{codigoPersonal};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT COALESCE(INGRESO,'N') FROM ENOC.ACCESO WHERE CODIGO_PERSONAL = ?";
				ingreso = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoDao|getIdioma|:"+ex);			
		}
		
		return ingreso;
	}
	
	public String getMenu(String  usuario){		
		String idioma 			= "es";
		try{
			String comando = "SELECT MENU FROM ENOC.ACCESO WHERE CODIGO_PERSONAL = ? ";
			idioma = enocJdbc.queryForObject(comando, String.class, usuario);			
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoDao|getMenu|:"+ex);
			
		}		
		return idioma;
	}
	
	public List<Acceso> getListAll(String orden ){		
		List<Acceso> lista 	= new ArrayList<Acceso>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, ADMINISTRADOR, SUPERVISOR, COTEJADOR, ACCESOS, MODALIDAD, USUARIO, CLAVE, "
					+ " SUPERVISOR, EXPIRA, INGRESO, CONVALIDA, BECAS, PORTAL_MAESTRO, PORTAL_ALUMNO, IDIOMA, MENU, ALUMNO_MOVIL,"
					+ " CLAVE_INICIAL, CLAVE_HEXA, ENLINEA, BUSCA_ADMIN"
					+ " FROM ENOC.ACCESO "+orden;
			lista = enocJdbc.query(comando, new AccesoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoDao|getListAll|:"+ex);		
		}		
		return lista;
	}
	
	public boolean existeUsuario(String codigoPersonal, String usuario){	
		
		boolean existe 		= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ACCESO WHERE CODIGO_PERSONAL != ? AND USUARIO = ?";	
			Object[] parametros = new Object[]{codigoPersonal, usuario};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				existe = true;		
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoDao|existeUsuario|:"+ex);
		}		
		
		return existe;
	}
	
	public int cursoresPorUsuario(String usuario){
		int total 		= 0;		
		try{
			String comando = "SELECT SUM(B.VALUE) FROM V$SESSION A, V$SESSTAT B, V$STATNAME C" + 
					" WHERE C.NAME IN ('opened cursors current')" + 
					" AND A.MACHINE = 'tomcats'" + 
					" AND   B.STATISTIC# = C.STATISTIC#" + 
					" AND   A.SID = B.SID" + 
					" AND   A.USERNAME = ?" + 
					" AND   B.VALUE >0" + 
					" GROUP BY A.USERNAME";	
			Object[] parametros = new Object[]{usuario};
			total = enocJdbc.queryForObject(comando,Integer.class, parametros);					
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoDao|cursoresPorUsuario|:"+ex);
		}
		
		return total;
	}	
	
	public int maximoCursores(){
		int total 		= 0;		
		try{
			String comando = "SELECT COALESCE(MAX(A.VALUE),0) FROM V$SESSTAT A, V$STATNAME B, V$PARAMETER P"
					+ " WHERE A.STATISTIC# = B.STATISTIC# AND B.NAME = 'opened cursors current' AND P.NAME= 'open_cursors'  GROUP BY P.VALUE";
			total = enocJdbc.queryForObject(comando,Integer.class);					
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoDao|maximoCursores|:"+ex);
		}
		
		return total;
	}
	
	public HashMap<String, Acceso> mapaTodos(){
		List<Acceso> lista 				= new ArrayList<Acceso>();		
		HashMap<String, Acceso> mapa	= new HashMap<String,Acceso>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, ADMINISTRADOR, SUPERVISOR, COTEJADOR, ACCESOS, MODALIDAD, USUARIO, CLAVE,"
					+ " SUPERVISOR, EXPIRA, INGRESO, CONVALIDA, BECAS, PORTAL_MAESTRO, PORTAL_ALUMNO, IDIOMA, MENU, ALUMNO_MOVIL, "
					+ " CLAVE_INICIAL, CLAVE_HEXA, ENLINEA, BUSCA_ADMIN"
					+ " FROM ENOC.ACCESO";
			lista = enocJdbc.query(comando, new AccesoMapper());
			for (Acceso map : lista) {
				mapa.put(map.getCodigoPersonal(), map);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.MaestrosDao|mapaTodos|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, Acceso> mapaAccesosEnOpcion(String opcionId){
		List<Acceso> lista 				= new ArrayList<Acceso>();		
		HashMap<String, Acceso> mapa	= new HashMap<String,Acceso>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, ADMINISTRADOR, SUPERVISOR, COTEJADOR, ACCESOS, MODALIDAD, USUARIO, CLAVE,"
					+ " SUPERVISOR, EXPIRA, INGRESO, CONVALIDA, BECAS, PORTAL_MAESTRO, PORTAL_ALUMNO, IDIOMA, MENU, ALUMNO_MOVIL, "
					+ " CLAVE_INICIAL, CLAVE_HEXA, ENLINEA, BUSCA_ADMIN"
					+ " FROM ENOC.ACCESO"
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ACCESO_OPCION WHERE OPCION_ID = ?)";
			lista = enocJdbc.query(comando, new AccesoMapper(), opcionId);
			for (Acceso map : lista) {
				mapa.put(map.getCodigoPersonal(), map);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.MaestrosDao|mapaAccesosEnOpcion|:"+ex);
		}
		
		return mapa;
	}
	
}
