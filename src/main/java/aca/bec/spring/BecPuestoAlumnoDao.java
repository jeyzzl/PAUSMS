package aca.bec.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BecPuestoAlumnoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(BecPuestoAlumno becPuestoAlumno){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.BEC_PUESTO_ALUMNO"+ 
				"(PUESTO_ID, ID_EJERCICIO," +
				" ID_CCOSTO, CATEGORIA_ID, CODIGO_PERSONAL, FECHA_INI, FECHA_FIN, TIPO, ESTADO, USUARIO, PERIODO_ID, PLAN_ID, DESCRIPCION)"+
				" VALUES( ?, ?, ?, TO_NUMBER(?,'999'), ?, TO_DATE(?, 'DD/MM/YYYY'), TO_DATE(?, 'DD/MM/YYYY'), ?, ?, ?, ?, ?, ?)";
			Object[] parametros = new Object[] {
				becPuestoAlumno.getPuestoId(), becPuestoAlumno.getIdEjercicio(), becPuestoAlumno.getIdCcosto(),
				becPuestoAlumno.getCategoriaId(), becPuestoAlumno.getCodigoPersonal(), becPuestoAlumno.getFechaIni(), becPuestoAlumno.getFechaFin(),
				becPuestoAlumno.getTipo(), becPuestoAlumno.getEstado(), becPuestoAlumno.getUsuario(), becPuestoAlumno.getPeriodoId(),becPuestoAlumno.getPlanId(),
				becPuestoAlumno.getDescripcion()
			};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoAlumnoDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg( BecPuestoAlumno becPuestoAlumno){
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.BEC_PUESTO_ALUMNO"+ 
				" SET ID_EJERCICIO = ?, ID_CCOSTO = ?, CATEGORIA_ID = TO_NUMBER(?, '999'), CODIGO_PERSONAL = ?, FECHA_INI = TO_DATE(?, 'DD/MM/YYYY')," +
				" FECHA_FIN = TO_DATE(?, 'DD/MM/YYYY'), TIPO = ?, ESTADO = ?, USUARIO = ?, PERIODO_ID = ?, PLAN_ID = ?, DESCRIPCION = ? "+				
				" WHERE PUESTO_ID = ? ";
			Object[] parametros = new Object[] {becPuestoAlumno.getIdEjercicio(), becPuestoAlumno.getIdCcosto(), becPuestoAlumno.getCategoriaId(),
					becPuestoAlumno.getCodigoPersonal(), becPuestoAlumno.getFechaIni(), becPuestoAlumno.getFechaFin(), becPuestoAlumno.getTipo(),
					becPuestoAlumno.getEstado(), becPuestoAlumno.getUsuario(), becPuestoAlumno.getPeriodoId(),becPuestoAlumno.getPlanId(), becPuestoAlumno.getDescripcion(), 
					becPuestoAlumno.getPuestoId()					
			};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoAlumnoDao|updateReg|:"+ex);		
		
		}
		
		return ok;
	}
	
	public boolean update( String idCcosto, String categoriaId, String puestoId){
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.BEC_PUESTO_ALUMNO"
					+ " SET ID_CCOSTO = ?, CATEGORIA_ID = TO_NUMBER(?, '999')"
					+ " WHERE PUESTO_ID = ?";
			Object[] parametros = new Object[] {idCcosto, categoriaId, puestoId};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoAlumnoDao|update|:"+ex);	
		}
		
		return ok;
	}	 
	
	public boolean contratar( String estado, String puestoId){
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.BEC_PUESTO_ALUMNO SET ESTADO = ? WHERE PUESTO_ID = ?";
			Object[] parametros = new Object[] {estado, puestoId};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoAlumnoDao|contratar|:"+ex);		
		}		
		return ok;
	}	
	
	public boolean deleteReg( String puestoId){
		boolean ok = false;
				try{
			String comando = "DELETE FROM ENOC.BEC_PUESTO_ALUMNO WHERE PUESTO_ID = ? ";
			Object[] parametros = new Object[] {puestoId};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoAlumnoDao|deleteReg|:"+ex);		
		}
		return ok;
	}
	
	public BecPuestoAlumno mapeaRegId( String puestoId){
		BecPuestoAlumno becPuestoAlumno = new BecPuestoAlumno();	
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BEC_PUESTO_ALUMNO WHERE PUESTO_ID = ?";
			Object[] parametros = new Object[] {puestoId};	
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1){
				comando = " SELECT PUESTO_ID, ID_EJERCICIO, ID_CCOSTO, CATEGORIA_ID, CODIGO_PERSONAL,"
						+ " TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN, TIPO, ESTADO, USUARIO, PERIODO_ID, NIVEL_ID, PLAN_ID, DESCRIPCION"
						+ " FROM ENOC.BEC_PUESTO_ALUMNO WHERE PUESTO_ID = ?";
				becPuestoAlumno = enocJdbc.queryForObject(comando, new BecPuestoAlumnoMapper(),parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoAlumnoDao|mapeaRegId|:"+ex);		
		}
		return becPuestoAlumno;
	}
	
	public BecPuestoAlumno mapeaRegIdPuesto( String puestoId){
		BecPuestoAlumno becPuestoAlumno = new BecPuestoAlumno();
			
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BEC_PUESTO_ALUMNO WHERE PUESTO_ID = ? "; 
			Object[] parametros = new Object[] {puestoId};	
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1){
				comando = "SELECT PUESTO_ID, ID_EJERCICIO, ID_CCOSTO, CATEGORIA_ID, CODIGO_PERSONAL," +
						" TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN, TIPO, ESTADO, USUARIO, PERIODO_ID, NIVEL_ID, PLAN_ID, DESCRIPCION" +
						" FROM ENOC.BEC_PUESTO_ALUMNO WHERE PUESTO_ID = ? "; 
				becPuestoAlumno = enocJdbc.queryForObject(comando, new BecPuestoAlumnoMapper(),parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoAlumnoDao|mapeaRegIdPuesto|:"+ex);
		}
		return becPuestoAlumno;
	}
	
	public BecPuestoAlumno mapeaPuestoActual(String codigoPersonal){
		BecPuestoAlumno becPuestoAlumno = new BecPuestoAlumno();
		try{
			String comando = "SELECT PUESTO_ID, ID_EJERCICIO, ID_CCOSTO, CATEGORIA_ID, CODIGO_PERSONAL,"
				+ " TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN, TIPO, ESTADO, USUARIO, PERIODO_ID, NIVEL_ID, PLAN_ID, DESCRIPCION"
				+ " FROM ENOC.BEC_PUESTO_ALUMNO WHERE CODIGO_PERSONAL = ? AND now() BETWEEN FECHA_INI AND FECHA_FIN";			
			Object[] parametros = new Object[] {codigoPersonal};
			becPuestoAlumno = enocJdbc.queryForObject(comando, new BecPuestoAlumnoMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAcuerdoDao|mapeaPuestoActual|:"+ex);
		}
		return becPuestoAlumno;
	}
	
	public boolean existeReg( String puestoId){
		boolean ok 			= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BEC_PUESTO_ALUMNO WHERE PUESTO_ID = ? "; 
			Object[] parametros = new Object[] {puestoId};	
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoAlumnoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg( String ejercicioId){
 		String 		maximo 		= "1";
 		
 		try{
 			String comando = "SELECT COALESCE(TO_NUMBER(MAX(SUBSTR(PUESTO_ID,9,4)), '9999')+1,1) AS MAXIMO FROM ENOC.BEC_PUESTO_ALUMNO " +
 					"WHERE PUESTO_ID LIKE ?||'%'";
 			if (enocJdbc.queryForObject(comando, Integer.class, ejercicioId) >= 1) {
				maximo = enocJdbc.queryForObject(comando, String.class, ejercicioId);
			} 			
 			if(maximo.length() == 0) maximo = "0001";
 			if(maximo.length() == 1) maximo = "000"+maximo;
 			if(maximo.length() == 2) maximo = "00"+maximo;
 			if(maximo.length() == 3) maximo = "0"+maximo;
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.bec.spring.BecPuestoAlumnoDao|maximoReg|:"+ex);
 		}
 		
 		return maximo;
 	}
	
	public String minimoReg( String ejercicioId){
		
 		String 		minimo 		= "0001";
 		
 		try{
 			String comando = "SELECT COALESCE(TO_NUMBER(SUBSTR(MIN(PUESTO_ID),9,4),'9999')-1,1) AS MINIMO FROM ENOC.BEC_PUESTO_ALUMNO " +
 					"WHERE PUESTO_ID LIKE ?||'%'";
 			if (enocJdbc.queryForObject(comando, Integer.class) >= 1) {
 				minimo = enocJdbc.queryForObject(comando, String.class);
			}		
 			
 			if(minimo.length() == 0) minimo = "0001";
 			if(minimo.length() == 1) minimo = "000"+minimo;
 			if(minimo.length() == 2) minimo = "00"+minimo;
 			if(minimo.length() == 3) minimo = "0"+minimo;
 				
 		}catch(Exception ex){
 			System.out.println("Error - aca.bec.spring.BecPuestoAlumnoDao|minimoReg|:"+ex);
 		}
 		
 		return minimo;
 	}
	
	public String maximoPuesto( String codigoPersonal){
		
 		String 		maximo 		= "1"; 		
 		try{
 			String comando = "SELECT COUNT(*) FROM ENOC.BEC_PUESTO_ALUMNO WHERE CODIGO_PERSONAL = ?";
 			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1) {
 				comando = "SELECT COALESCE(MAX(PUESTO_ID),'0') FROM ENOC.BEC_PUESTO_ALUMNO WHERE CODIGO_PERSONAL = ?";
				maximo = enocJdbc.queryForObject(comando, String.class,parametros);
			} 	
 		}catch(Exception ex){
 			System.out.println("Error - aca.bec.spring.BecPuestoAlumnoDao|maximoPuesto|:"+ex);
 		}
 		
 		return maximo;
 	}
	
	public boolean existeFechaBetween( String codigoPersonal, String fecha, String puestoId){
		boolean ok 				= false;	
		try{
			String comando =	"SELECT COUNT(*) FROM ENOC.BEC_PUESTO_ALUMNO " +
								" WHERE CODIGO_PERSONAL = ? " +
								" AND TO_DATE(?, 'DD/MM/YYYY') >= FECHA_INI" +
								" AND TO_DATE(?, 'DD/MM/YYYY') <= FECHA_FIN" +
								" AND PUESTO_ID NOT IN ('"+puestoId+"')" ; 
			Object[] parametros = new Object[] {codigoPersonal, fecha, fecha};
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1) {			
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoAlumnoDao|existeFechaBetween|:"+ex);
		}
		
		return ok;
	}
	
	public boolean existeFechaINIBetween( String codigoPersonal, String fechaIni, String fechaFin, String puestoId){
		boolean ok 				= false;		
		try{
			String comando	= " SELECT COUNT(*) FROM ENOC.BEC_PUESTO_ALUMNO"
							+ " WHERE CODIGO_PERSONAL = ?"
							+ " AND FECHA_INI >= TO_DATE(?, 'DD/MM/YYYY')"
							+ " AND FECHA_INI <= TO_DATE(?, 'DD/MM/YYYY')"
							+ " AND PUESTO_ID NOT IN (?)" ; 
			Object[] parametros = new Object[] {codigoPersonal, fechaIni, fechaFin, puestoId};	
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1) {
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoAlumnoDao|existeFechaBetween|:"+ex);
		}
		
		return ok;
	}
	
	public boolean existeFechaFINBetween( String codigoPersonal, String fechaIni, String fechaFin, String puestoId){
		boolean ok 				= false;
		
		try{
			String comando = " SELECT COUNT(*) FROM ENOC.BEC_PUESTO_ALUMNO" 
					+ " WHERE CODIGO_PERSONAL = ?" 
					+ " AND FECHA_FIN >= TO_DATE(?, 'DD/MM/YYYY')"
					+ " AND FECHA_FIN <= TO_DATE(?, 'DD/MM/YYYY')"
					+ " AND PUESTO_ID NOT IN (?)" ; 
			Object[] parametros = new Object[] {codigoPersonal, fechaIni, fechaFin, puestoId};
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1) {			
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoAlumnoDao|existeFechaBetween|:"+ex);
		}		
		return ok;
	}
 	
	public HashMap<String,String> getColportaje(){
		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		String comando			= "";
		
		try{
			comando = "SELECT MATRICULA AS LLAVE, SALDO AS VALOR FROM NOE.CLP_DOCS_CONFIRMADOS";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoAlumnoDao|getColportaje|:"+ex);
		}
		
		return mapa;
	}
	
	public String getNumPuestosAlumno( String idCcosto, String tipo, String idEjercicio){
 		int 		cantidad 	= 0;
 		
 		try{
 			String comando = "SELECT COUNT(*) AS CANTIDAD FROM ENOC.BEC_PUESTO_ALUMNO WHERE ID_CCOSTO = ? AND TIPO = ? AND ID_EJERCICIO = ? ";
 			Object[] parametros = new Object[] {idCcosto, tipo, idEjercicio};
 			cantidad = enocJdbc.queryForObject(comando, Integer.class,parametros);		
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.bec.spring.BecPuestoAlumnoDao|getNumPuestosAlumno|:"+ex);
 		}
 		
 		return String.valueOf(cantidad);
 	}
	
	public String getTipoAcuerdo( String puestoId, String acuerdo){
 		String tipo = "0"; 		
 		try{
 			String comando = "SELECT COUNT(*) FROM ENOC.BEC_PUESTO_ALUMNO WHERE PUESTO_ID = ? AND TIPO = ?";
 			Object[] parametros = new Object[] {puestoId, acuerdo}; 			
 			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1){
 				comando = "SELECT TIPO FROM ENOC.BEC_PUESTO_ALUMNO WHERE PUESTO_ID = ? AND TIPO = ?";
 				tipo = enocJdbc.queryForObject(comando, String.class,parametros);
 			} 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.bec.spring.BecPuestoAlumnoDao|getNumPuestosAlumno|:"+ex);
 		} 		
 		return tipo;
 	}
	
	public String getNumPuestosAlumnosActivos( String idCcosto, String tipo, String idEjercicio){
 		int 		cantidad 	= 0;
 		
 		try{
 			String comando = "SELECT COUNT(*) AS CANTIDAD FROM ENOC.BEC_PUESTO_ALUMNO" +
 					" WHERE ID_CCOSTO = ?" +
 					" AND TIPO = ?" +
 					" AND ID_EJERCICIO = ?" +
 					" AND TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN"; 			
 			Object[] parametros = new Object[] {idCcosto, tipo, idEjercicio};
 			cantidad = enocJdbc.queryForObject(comando, Integer.class,parametros);		
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.bec.spring.BecPuestoAlumnoDao|getNumPuestosAlumno|:"+ex);
 		}
 		
 		return String.valueOf(cantidad);
 	}
	
	public String getNumPuestosAlumnosActivos( String idCcosto, String tipo, String idEjercicio, String periodoId){
 		int 		cantidad 	= 0; 		
 		try{
 			String comando = "SELECT COUNT(*) AS CANTIDAD FROM ENOC.BEC_PUESTO_ALUMNO"
 					+ " WHERE ID_CCOSTO = ?"
 					+ " AND TIPO = ?"
 					+ " AND ID_EJERCICIO = ?"
 					+ " AND PERIODO_ID = ?"
 					+ " AND TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN"; 			
 			Object[] parametros = new Object[] {idCcosto, tipo, idEjercicio, periodoId};
 			cantidad = enocJdbc.queryForObject(comando, Integer.class,parametros);		
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.bec.spring.BecPuestoAlumnoDao|getNumPuestosAlumno|:"+ex);
 		}
 		
 		return String.valueOf(cantidad);
 	}
	
	public String getPuestoAlumno( String codigoPersonal, String cargaId){
		
 		String puesto 			= "-";
 		
 		try{
 			String comando = "SELECT COALESCE(ALPUESTO_PUESTO_ID,'-') AS PUESTO FROM MATEO.FES_CC_AFE_ACUERDOS"
 					+ " WHERE MATRICULA = ?"
 					+ " AND CARGA_ID = ?";
 			Object[] parametros = new Object[] {codigoPersonal, cargaId};
 			puesto = enocJdbc.queryForObject(comando, String.class,parametros);		
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.bec.spring.BecPuestoAlumnoDao|getPuestoAlumno|:"+ex);
 		}
 		
 		return puesto;
 	}
	
	// Map de puestos vigentes en una fecha determinada
	public HashMap<String,BecPuestoAlumno> getPuestosAlumno( String idEjercicio, String fecha){
		
		HashMap<String, BecPuestoAlumno> mapa = new HashMap<String, BecPuestoAlumno>();
		List<BecPuestoAlumno> lista 			= new ArrayList<BecPuestoAlumno>();
		String comando			= "";
		try{
			comando = "SELECT PUESTO_ID, ID_EJERCICIO, ID_CCOSTO, CATEGORIA_ID, CODIGO_PERSONAL," +
					" TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN," +
					" TIPO, ESTADO, USUARIO, PERIODO_ID, NIVEL_ID, PLAN_ID, DESCRIPCION " +
					" FROM ENOC.BEC_PUESTO_ALUMNO" +
					" WHERE ID_EJERCICIO = ?" +
					" AND TO_DATE(?,'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN";
			lista = enocJdbc.query(comando, new BecPuestoAlumnoMapper(), idEjercicio, fecha);
			for (BecPuestoAlumno puesto : lista) {
				mapa.put(puesto.getCodigoPersonal(), puesto);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoAlumnoDao |getPuestoAlumno|:"+ex);
		}
		
		return mapa;
	}
	
	public List<BecPuestoAlumno> getListAll( String orden){
			
		List<BecPuestoAlumno> lista 			= new ArrayList<BecPuestoAlumno>();
		
		try{
			String comando = "SELECT * FROM ENOC.BEC_PUESTO_ALUMNO "+orden;
			lista = enocJdbc.query(comando, new BecPuestoAlumnoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoAlumnoDao|getListAll|:"+ex);
		}
		
		return (ArrayList<BecPuestoAlumno>) lista;
	}
	
	public List<BecPuestoAlumno> getListAllEjercicio( String idEjercicio, String orden){
		
		List<BecPuestoAlumno> lista 			= new ArrayList<BecPuestoAlumno>();		
		try{
			String comando = "SELECT COALESCE(PUESTO_ID,'-') AS PUESTO_ID, ID_EJERCICIO, ID_CCOSTO, CATEGORIA_ID, CODIGO_PERSONAL," +
					" TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN, "+
					" TIPO, ESTADO, USUARIO, PERIODO_ID, NIVEL_ID, PLAN_ID, DESCRIPCION "+
					" FROM ENOC.BEC_PUESTO_ALUMNO WHERE ID_EJERCICIO = ? "+orden;
			lista = enocJdbc.query(comando, new BecPuestoAlumnoMapper(), idEjercicio);		
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoAlumnoDao|getListAllEjercicio|:"+ex);
		}		
		return (ArrayList<BecPuestoAlumno>) lista;
	}
	
	public List<BecPuestoAlumno> getListDepartamento( String idEjercicio, String idCcosto, String orden){
		
		List<BecPuestoAlumno> lista 			= new ArrayList<BecPuestoAlumno>();
		
		try{
			String comando = "SELECT PUESTO_ID, ID_EJERCICIO, ID_CCOSTO, CATEGORIA_ID, CODIGO_PERSONAL," +
					" TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN, TIPO, ESTADO, USUARIO, PERIODO_ID, NIVEL_ID, PLAN_ID, DESCRIPCION " +
					" FROM ENOC.BEC_PUESTO_ALUMNO" +
					" WHERE ID_EJERCICIO = ? " +
					" AND ID_CCOSTO = ? "+orden;
			lista = enocJdbc.query(comando, new BecPuestoAlumnoMapper(), idEjercicio, idCcosto);	
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoAlumnoDao|getListDepartamento|:"+ex);
		}
		
		return (ArrayList<BecPuestoAlumno>) lista;
	}
	
	public List<BecPuestoAlumno> lisPorPeriodoYDepartamento( String periodoId, String idCcosto, String orden){
		
		List<BecPuestoAlumno> lista 			= new ArrayList<BecPuestoAlumno>();
		
		try{
			String comando = "SELECT PUESTO_ID, ID_EJERCICIO, ID_CCOSTO, CATEGORIA_ID, CODIGO_PERSONAL," +
					" TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN, TIPO, ESTADO, USUARIO, PERIODO_ID, NIVEL_ID, PLAN_ID, DESCRIPCION " +
					" FROM ENOC.BEC_PUESTO_ALUMNO" +
					" WHERE PERIODO_ID = ? " +
					" AND ID_CCOSTO = ? "+orden;
			lista = enocJdbc.query(comando, new BecPuestoAlumnoMapper(), periodoId, idCcosto);	
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoAlumnoDao|lisPorPeriodoYDepartamento|:"+ex);
		}
		
		return (ArrayList<BecPuestoAlumno>) lista;
	}
		
	public List<BecPuestoAlumno> getListPuestosActivos( String idEjercicio, String idCcosto, String categoriaId, String orden){
		
		List<BecPuestoAlumno> lista 			= new ArrayList<BecPuestoAlumno>();		
		try{
			String comando = "SELECT PUESTO_ID, ID_EJERCICIO, ID_CCOSTO, CATEGORIA_ID, CODIGO_PERSONAL," +
					" TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN, TIPO, ESTADO, USUARIO, PERIODO_ID, NIVEL_ID, PLAN_ID, DESCRIPCION " +
					" FROM ENOC.BEC_PUESTO_ALUMNO " +
					" WHERE ID_EJERCICIO = ? " +
					" AND now() BETWEEN FECHA_INI AND FECHA_FIN" +
					" AND ID_CCOSTO = ? AND CATEGORIA_ID = ? "+orden;
			lista = enocJdbc.query(comando, new BecPuestoAlumnoMapper(), idEjercicio, idCcosto, categoriaId);
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoAlumnoDao|getListPuestos|:"+ex);
		}
		
		return (ArrayList<BecPuestoAlumno>) lista;
	}
	
	public List<BecPuestoAlumno> getListPuestosFecha( String fechaPuesto, String idEjercicio, String idCcosto, String categoriaId, String orden){
		
		List<BecPuestoAlumno> lista 			= new ArrayList<BecPuestoAlumno>();
		
		try{
			String comando = "SELECT PUESTO_ID, ID_EJERCICIO, ID_CCOSTO, CATEGORIA_ID, CODIGO_PERSONAL," +
					" TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN, TIPO, ESTADO, USUARIO, PERIODO_ID, NIVEL_ID, PLAN_ID, DESCRIPCION " +
					" FROM ENOC.BEC_PUESTO_ALUMNO " +
					" WHERE ID_EJERCICIO = ? " +
					" AND TO_DATE(?, 'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN" +
					" AND ID_CCOSTO = ? AND CATEGORIA_ID = ? "+orden;
			lista = enocJdbc.query(comando, new BecPuestoAlumnoMapper(), idEjercicio, fechaPuesto, idCcosto, categoriaId);
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoAlumnoDao|getListPuestosFecha|:"+ex);
		}
		
		return (ArrayList<BecPuestoAlumno>) lista;
	}
	
	public List<BecPuestoAlumno> getListPuestos( String idEjercicio, String idCcosto, String categoriaId, String orden){
		
		List<BecPuestoAlumno> lista 			= new ArrayList<BecPuestoAlumno>();		
		try{
			String comando = "SELECT PUESTO_ID, ID_EJERCICIO, ID_CCOSTO, CATEGORIA_ID, CODIGO_PERSONAL," +
					" TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN, TIPO, ESTADO, USUARIO, PERIODO_ID, NIVEL_ID, PLAN_ID, DESCRIPCION " +
					" FROM ENOC.BEC_PUESTO_ALUMNO WHERE ID_EJERCICIO = ? " +
					" AND ID_CCOSTO = ? AND CATEGORIA_ID = ? "+orden;
			lista = enocJdbc.query(comando, new BecPuestoAlumnoMapper(), idEjercicio, idCcosto, categoriaId);			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoAlumnoDao|getListPuestos|:"+ex);
		}		
		return (ArrayList<BecPuestoAlumno>) lista;
	}
	
	public List<BecPuestoAlumno> listAlumnosEnPuestos( String idEjercicio, String orden){
		
		List<BecPuestoAlumno> lista 			= new ArrayList<BecPuestoAlumno>();		
		try{
			String comando = "SELECT PUESTO_ID, ID_EJERCICIO, ID_CCOSTO, CATEGORIA_ID, CODIGO_PERSONAL," +
					" TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN, TIPO, ESTADO, USUARIO, PERIODO_ID, NIVEL_ID, PLAN_ID, DESCRIPCION " +
					" FROM ENOC.BEC_PUESTO_ALUMNO WHERE ID_EJERCICIO = ? " +orden;
			Object[] parametros = new Object[] {idEjercicio};
			lista = enocJdbc.query(comando, new BecPuestoAlumnoMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoAlumnoDao|getListPuestos|:"+ex);
		}		
		return (ArrayList<BecPuestoAlumno>) lista;
	}
	
	public List<BecPuestoAlumno> getListPuestosNivel( String idEjercicio, String idCcosto, String categoriaId, String nivelId, String orden){		
		List<BecPuestoAlumno> lista 			= new ArrayList<BecPuestoAlumno>();		
		try{
			String comando = "SELECT PUESTO_ID, ID_EJERCICIO, ID_CCOSTO, CATEGORIA_ID, CODIGO_PERSONAL," +
					" TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN, TIPO, ESTADO, USUARIO, PERIODO_ID, NIVEL_ID, PLAN_ID, DESCRIPCION " +
					" FROM ENOC.BEC_PUESTO_ALUMNO WHERE ID_EJERCICIO = ? " +
					" AND ID_CCOSTO = ? AND CATEGORIA_ID = ? AND NIVEL_ID IN ( "+nivelId+" )"+orden;
			lista = enocJdbc.query(comando, new BecPuestoAlumnoMapper(), idEjercicio, idCcosto, categoriaId);
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoAlumnoDao|getListPuestosNivel|:"+ex);
		}		
		return (ArrayList<BecPuestoAlumno>) lista;
	}
	
	public List<BecPuestoAlumno> getListPuestosSC( String idEjercicio, String idCcosto, String orden){
		
		List<BecPuestoAlumno> lista 			= new ArrayList<BecPuestoAlumno>();
		
		try{
			String comando = "SELECT PUESTO_ID, ID_EJERCICIO, ID_CCOSTO, CATEGORIA_ID, CODIGO_PERSONAL," +
					" TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN, TIPO, ESTADO, USUARIO, PERIODO_ID, NIVEL_ID, PLAN_ID, DESCRIPCION " +
					" FROM ENOC.BEC_PUESTO_ALUMNO" +
					" WHERE ID_EJERCICIO = ? " +
					" AND ID_CCOSTO = ?" +
					" AND now() BETWEEN FECHA_INI AND FECHA_FIN "+orden;
			lista = enocJdbc.query(comando, new BecPuestoAlumnoMapper(), idEjercicio, idCcosto);
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoAlumnoDao|getListPuestosSC|:"+ex);
		}
		
		return (ArrayList<BecPuestoAlumno>) lista;
	}
	
	public List<BecPuestoAlumno> getListAlumnosCategoria( String idEjercicio, String categoriaId,String periodoId, String orden){
		
		List<BecPuestoAlumno> lista 			= new ArrayList<BecPuestoAlumno>();		
		try{
			String comando = "SELECT * FROM ENOC.BEC_PUESTO_ALUMNO WHERE ID_EJERCICIO = ? AND CATEGORIA_ID = ? "
					+ "AND PERIODO_ID = ? AND now() BETWEEN FECHA_INI AND FECHA_FIN "+orden;
			lista = enocJdbc.query(comando, new BecPuestoAlumnoMapper(), idEjercicio, categoriaId, periodoId);			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoAlumnoDao|getListAlumnosCategoria|:"+ex);
		}
		
		return (ArrayList<BecPuestoAlumno>) lista;
	}
	
	public List<BecPuestoAlumno> listAlumnosEnCategoria( String idEjercicio, String categoriaId, String fecha, String orden){
		
		List<BecPuestoAlumno> lista			= new ArrayList<BecPuestoAlumno>();
		
		try{
			String comando = "SELECT * FROM ENOC.BEC_PUESTO_ALUMNO WHERE ID_EJERCICIO = ? AND CATEGORIA_ID = ? "
					+ "AND TO_DATE(?,'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN "+orden;
			lista = enocJdbc.query(comando, new BecPuestoAlumnoMapper(), idEjercicio, categoriaId, fecha);
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoAlumnoDao|listAlumnosEnCategoria|:"+ex);
		}
		
		return (ArrayList<BecPuestoAlumno>) lista;
	}
	
	public HashMap<String,BecPuestoAlumno> getMapPuestos( String idEjercicio){
		
		HashMap<String, BecPuestoAlumno> mapa = new HashMap<String, BecPuestoAlumno>();
		List<BecPuestoAlumno> lista 			= new ArrayList<BecPuestoAlumno>();
		
		try{
			String comando = "SELECT PUESTO_ID, ID_EJERCICIO, ID_CCOSTO, CATEGORIA_ID, CODIGO_PERSONAL," +
					" TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN," +
					" TIPO, ESTADO, USUARIO, PERIODO_ID, NIVEL_ID, PLAN_ID, DESCRIPCION " +
					" FROM ENOC.BEC_PUESTO_ALUMNO" +
					" WHERE ID_EJERCICIO = ?";
			lista = enocJdbc.query(comando, new BecPuestoAlumnoMapper(), idEjercicio);
			for(BecPuestoAlumno puesto : lista ){
				mapa.put(puesto.getPuestoId(), puesto);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoAlumnoDao|getMapPuestos|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,BecPuestoAlumno> mapaPuestosEnInforme( String informeId){
		
		HashMap<String, BecPuestoAlumno> mapa = new HashMap<String, BecPuestoAlumno>();
		List<BecPuestoAlumno> lista 			= new ArrayList<BecPuestoAlumno>();
		
		try{
			String comando = "SELECT PUESTO_ID, ID_EJERCICIO, ID_CCOSTO, CATEGORIA_ID, CODIGO_PERSONAL, TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI,"
					+ " TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN, TIPO, ESTADO, USUARIO, PERIODO_ID, NIVEL_ID, PLAN_ID, DESCRIPCION"
					+ " FROM ENOC.BEC_PUESTO_ALUMNO"
					+ " WHERE PUESTO_ID IN (SELECT PUESTO_ID FROM ENOC.BEC_INFORME_ALUMNO WHERE INFORME_ID = ?)";
			Object[] parametros = new Object[] {informeId};
			lista = enocJdbc.query(comando, new BecPuestoAlumnoMapper(),parametros);			
			for(BecPuestoAlumno puesto : lista ){			
				mapa.put(puesto.getPuestoId(), puesto);				
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoAlumnoDao|mapaPuestosEnInforme|:"+ex);
		}
		
		return mapa;
	}
	
	public List<BecPuestoAlumno> getListAllEjerciciosAlum( String codigoPersonal, String orden){
			
			List<BecPuestoAlumno> lista 			= new ArrayList<BecPuestoAlumno>();			
			try{
				String comando = "SELECT PUESTO_ID, ID_EJERCICIO, ID_CCOSTO, CATEGORIA_ID, CODIGO_PERSONAL," +
						" TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN, TIPO, ESTADO, USUARIO, PERIODO_ID, NIVEL_ID, PLAN_ID, DESCRIPCION "+
						" FROM ENOC.BEC_PUESTO_ALUMNO WHERE CODIGO_PERSONAL = ? "+orden;
				lista = enocJdbc.query(comando, new BecPuestoAlumnoMapper(), codigoPersonal);				
			}catch(Exception ex){
				System.out.println("Error - aca.bec.spring.BecPuestoAlumnoDao|getListAllEjercicio|:"+ex);
			}
			
			return (ArrayList<BecPuestoAlumno>) lista;
	}
	
	public HashMap<String,String> mapAlumCarrera( String idEjercicio, String fecha){
		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, CARRERA_ID AS VALOR FROM ENOC.ALUM_ESTADO"
					+ " WHERE CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN"
					+ "		(SELECT MATRICULA||CARGA_ID||BLOQUE FROM MATEO.FES_CC_AFE_ACUERDOS"
					+ "		WHERE ACUERDO_EJERCICIO_ID = ?"
					+ "		AND TIPO_ID IN (1,3)"
					+ "		AND MATRICULA||ALPUESTO_PUESTO_ID IN"
					+ "			(SELECT CODIGO_PERSONAL||PUESTO_ID FROM ENOC.BEC_PUESTO_ALUMNO"
					+ "			WHERE ID_EJERCICIO = ?"
					+ " 		AND FECHA_INI<= TO_DATE(?, 'DD/MM/YYYY') AND FECHA_FIN >= TO_DATE(?, 'DD/MM/YYYY')))";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), idEjercicio, idEjercicio, fecha, fecha);		
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.becPuestoAlumno|mapAlumCarrera|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapAlumCarrera( String idEjercicio, String fecha, String tipos){
		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CODIGO_PERSONALAS LLAVE, CARRERA_ID AS VALOR FROM ENOC.ALUM_ESTADO"
					+ " WHERE SUBSTR(CARGA_ID,5,1) IN ("+tipos+")"
					+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN"
					+ "		(SELECT MATRICULA||CARGA_ID||BLOQUE FROM MATEO.FES_CC_AFE_ACUERDOS"
					+ "		WHERE ACUERDO_EJERCICIO_ID = ?"
					+ "		AND TIPO_ID IN (1,3)"
					+ "		AND MATRICULA||ALPUESTO_PUESTO_ID IN"
					+ "			(SELECT CODIGO_PERSONAL||PUESTO_ID FROM ENOC.BEC_PUESTO_ALUMNO"
					+ "			WHERE ID_EJERCICIO = ?"
					+ " 		AND FECHA_INI<= TO_DATE(?, 'DD/MM/YYYY') AND FECHA_FIN>=TO_DATE(?, 'DD/MM/YYYY')))";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), idEjercicio, idEjercicio, fecha, fecha );		
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.becPuestoAlumno|mapAlumCarrera|:"+ex);
		}
		
		return mapa;
	}
	
	/** CUENTA EL NUMERO DE PUESTOS OCUPADOS EN UNA CATEGORIA DURANTE UN EJERCICO Y PERIODO */
	public HashMap<String, String> mapTotPuestosEnCategoria( String ejercicioId, String periodoId){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();		
		try{			
			String comando = " SELECT CATEGORIA_IDAS LLAVE, COALESCE(COUNT(CATEGORIA_ID),0) AS VALOR FROM ENOC.BEC_PUESTO_ALUMNO"
					+ " WHERE ID_EJERCICIO = ? AND PERIODO_ID = ? GROUP BY CATEGORIA_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), ejercicioId, periodoId);		
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.bec.becPuestoAlumno|mapTotPuestosEnCategoria|:"+ex);
		}		
		return mapa;
	}
	
	/** CUENTA EL NUMERO DE PUESTOS OCUPADOS EN UNA CATEGORIA DURANTE UN EJERCICO Y FECHA */
	public HashMap<String, String> mapTotPuestosPorCategoria( String ejercicioId, String fecha){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();		
		try{			
			String comando = " SELECT CATEGORIA_ID AS LLAVE, COALESCE(COUNT(CATEGORIA_ID),0) AS VALOR FROM ENOC.BEC_PUESTO_ALUMNO"
					+ " WHERE ID_EJERCICIO = ? AND TO_DATE(?, 'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN"
					+ " GROUP BY CATEGORIA_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), ejercicioId, fecha);		
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.becPuestoAlumno|mapTotPuestosPorCategoria|:"+ex);
		}	
		return mapa;
	}	
	
	public HashMap<String, String> mapaTotAlumnosPorCategoria( ){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		
		try{			
			String comando = "SELECT CATEGORIA_ID AS LLAVE, COALESCE(COUNT(CATEGORIA_ID),0) AS VALOR FROM ENOC.BEC_PUESTO_ALUMNO GROUP BY CATEGORIA_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());		
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.becPuestoAlumno|mapTotPuestosPorCategoria|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, String> mapaPorPeriodoyDepto(String periodoId){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		
		try{			
			String comando = "SELECT ID_CCOSTO||CATEGORIA_ID AS LLAVE, COALESCE(COUNT(CATEGORIA_ID),0) AS VALOR "
					+ " FROM ENOC.BEC_PUESTO_ALUMNO"
					+ " WHERE PERIODO_ID = ?"					
					+ " GROUP BY ID_CCOSTO,CATEGORIA_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), periodoId);		
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.becPuestoAlumno|mapaPorPeriodoyDepto|:"+ex);
		}		
		return mapa;
	}
	
}