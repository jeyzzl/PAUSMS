// Clase Util para la tabla de Carga
package aca.emp.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class EmpHorasDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( EmpHoras horas ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.EMP_HORAS"
					+ " (CODIGO_PERSONAL, FOLIO, CURSO_ID, CARGA_ID, FRECUENCIA, SEMANAS, PRECIO, MATERIA, CARRERA_ID, FECHA_INI, FECHA_FIN, ESTADO, FECHA, TIPO, CONTRATO_ID, TIPOPAGO_ID, BLOQUE_ID)"
					+ " VALUES( ?, TO_NUMBER(?,'999'), ?, ?, TO_NUMBER(?,'99.99'), TO_NUMBER(?,'99'),"
					+ " TO_NUMBER(?,'99999.99'), ?, ?, TO_DATE(?, 'DD/MM/YYYY'), TO_DATE(?, 'DD/MM/YYYY'), ?,"
					+ " TO_DATE(?, 'DD/MM/YYYY'), ?, ?, TO_NUMBER(?,'99'), TO_NUMBER(?,'99'))";
			Object[] parametros = new Object[] {
					horas.getCodigoPersonal(), horas.getFolio(), horas.getCursoId(), horas.getCargaId(), horas.getFrecuencia(), horas.getSemanas(),
					horas.getPrecio(), horas.getMateria(), horas.getCarreraId(), horas.getFechaIni(), horas.getFechaFin(), horas.getEstado(),
					horas.getFecha(), horas.getTipo(), horas.getContratoId(), horas.getTipoPagoId(), horas.getBloqueId()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpHorasDao|insertReg|:"+ex);			
		}
		
		return ok;
	}
	
	public boolean updateReg( EmpHoras horas ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.EMP_HORAS"
				+ " SET "
				+ " CURSO_ID = ?,"
				+ " CARGA_ID = ?,"
				+ " FRECUENCIA = TO_NUMBER(?,'99.99'),"
				+ " SEMANAS = TO_NUMBER(?,'99'),"
				+ " PRECIO = TO_NUMBER(?,'99999.99'),"
				+ " MATERIA = ?,"
				+ " CARRERA_ID = ?,"
				+ " FECHA_INI = TO_DATE(?,'DD/MM/YYYY'),"
				+ " FECHA_FIN = TO_DATE(?,'DD/MM/YYYY'),"
				+ " ESTADO = ?,"
				+ " FECHA = TO_DATE(?,'DD/MM/YYYY'),"
				+ " TIPO = ?,"
				+ " CONTRATO_ID = ?,"
				+ " TIPOPAGO_ID = TO_NUMBER(?,'99'),"
				+ " BLOQUE_ID = TO_NUMBER(?,'99')"
				+ " WHERE CODIGO_PERSONAL = ?"
				+ " AND FOLIO = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {
					horas.getCursoId(), horas.getCargaId(), horas.getFrecuencia(), horas.getSemanas(),
					horas.getPrecio(), horas.getMateria(), horas.getCarreraId(), horas.getFechaIni(), horas.getFechaFin(),
					horas.getEstado(), horas.getFecha(), horas.getTipo(), horas.getContratoId(), horas.getTipoPagoId(), horas.getBloqueId(),
					horas.getCodigoPersonal(), horas.getFolio()					
		 	};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpHorasDao|updateReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean deleteReg( String codigoPersonal, String folio ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.EMP_HORAS WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {codigoPersonal, folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpHorasDao|deleteReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateEstado( String codigoPersonal, String folio, String estado ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.EMP_HORAS SET ESTADO = ? WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {estado, codigoPersonal, folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpHorasDao|updateEstado|:"+ex);
		}
		
		return ok;
	}
	
	public boolean estadoCarrera( String cargaId, String carreraId, String estado ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.EMP_HORAS SET ESTADO = ? WHERE CARGA_ID = ? AND CARRERA_ID = ?";
			Object[] parametros = new Object[] {estado, cargaId, carreraId};
			if (enocJdbc.update(comando,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpHorasDao|estadoCarrera|:"+ex);
		}
		
		return ok;
	}
	
	public EmpHoras mapeaRegId(  String codigoPersonal, String folio ) {
		
		EmpHoras objeto = new EmpHoras();
		try{
			String comando = "SELECT CODIGO_PERSONAL, FOLIO, CURSO_ID, CARGA_ID, FRECUENCIA, SEMANAS, PRECIO, MATERIA, CARRERA_ID,"
					+ " TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, ESTADO,"
					+ " TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, TIPO, CONTRATO_ID, TIPOPAGO_ID, BLOQUE_ID"
					+ " FROM ENOC.EMP_HORAS"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND FOLIO = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {codigoPersonal, folio};
			objeto = enocJdbc.queryForObject(comando, new EmpHorasMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpHorasDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public boolean existeReg( String codigoPersonal, String folio) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EMP_HORAS WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {codigoPersonal, folio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpHorasDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public double totalContrato( String contratoId) {
		double total 	= 0;
		
		try{
			String comando = "SELECT COALESCE(SUM(FRECUENCIA*SEMANAS*PRECIO),0) FROM ENOC.EMP_HORAS WHERE CONTRATO_ID = ?";
			Object[] parametros = new Object[] {contratoId};
			total = enocJdbc.queryForObject(comando, Double.class, parametros);					
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpHorasDao|totalContrato|:"+ex);
		}
		
		return total;
	}
	
	public double gastoPorDepartamento( String cargaId, String departamento, String estado) {
		double total 	= 0;		
		try{
			String comando = "SELECT COUNT(*) FROM EMP_HORAS WHERE CARGA_ID = ? AND CCOSTO_CARRERA(CARRERA_ID) LIKE '"+departamento+"%' AND ESTADO = ?";
			Object[] parametros = new Object[] {cargaId, estado};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT SUM(FRECUENCIA*SEMANAS*PRECIO) FROM EMP_HORAS WHERE CARGA_ID = ? AND CCOSTO_CARRERA(CARRERA_ID) LIKE '"+departamento+"%' AND ESTADO = ?";
				total = enocJdbc.queryForObject(comando, Double.class, parametros);
			}
								
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpHorasDao|totalContrato|:"+ex);
		}
		
		return total;
	}
	
	public String maximoReg( String codigoPersonal) {
		
		int  maximo = 0;
		
		try{
			String comando = "SELECT COALESCE(MAX(FOLIO)+1,1) FROM ENOC.EMP_HORAS WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			maximo = enocJdbc.queryForObject(comando,Integer.class,parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpHorasDao|maximoCurso|:"+ex);
		}
		
		return String.valueOf(maximo);
	}
	
	public List<EmpHoras> lisTodos( String orden ) {
		
		List<EmpHoras> lista	= new ArrayList<EmpHoras>();	
		String comando	= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, FOLIO, CURSO_ID, CARGA_ID, FRECUENCIA, SEMANAS, PRECIO, MATERIA, CARRERA_ID, "
					+ " TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, ESTADO,"
					+ " TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, TIPO, CONTRATO_ID, TIPOPAGO_ID, BLOQUE_ID"
					+ " FROM ENOC.EMP_HORAS "+orden; 
			lista = enocJdbc.query(comando, new EmpHorasMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpHorasDao|listAll|:"+ex);
		}
		
		return lista;
	}
	
	
	public List<String> lisCarreraEnContrato( String contratoId) {		
		List<String> lista	= new ArrayList<String>();		
		try{
			String comando = " SELECT DISTINCT(CARRERA_ID) FROM ENOC.EMP_HORAS WHERE CONTRATO_ID = ? ORDER BY CARRERA_ID";
			Object[] parametros = new Object[] {contratoId};
			lista = enocJdbc.queryForList(comando, String.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpHorasDao|lisPorCarrera|:"+ex);
		}		
		return lista;
	}


	public List<EmpHoras> lisPorCarrera( String carrera, String orden ) {
		
		List<EmpHoras> lista	= new ArrayList<EmpHoras>();	
		String comando	= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, FOLIO, CURSO_ID, CARGA_ID, FRECUENCIA, SEMANAS, PRECIO, MATERIA, CARRERA_ID,"
					+ " TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, ESTADO,"
					+ " TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, TIPO, CONTRATO_ID, TIPOPAGO_ID, BLOQUE_ID"
					+ " FROM ENOC.EMP_HORAS"
					+ " WHERE CARRERA_ID = ? "+orden;
			Object[] parametros = new Object[] {carrera};
			lista = enocJdbc.query(comando, new EmpHorasMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpHorasDao|lisPorCarrera|:"+ex);
		}
		
		return lista;
	}
	
	public List<EmpHoras> lisPorMaestro( String codigoPersonal, String orden ) {
		
		List<EmpHoras> lista	= new ArrayList<EmpHoras>();	
		String comando	= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, FOLIO, CURSO_ID, CARGA_ID, FRECUENCIA, SEMANAS, PRECIO, MATERIA, CARRERA_ID,"
					+ " TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, ESTADO,"
					+ " TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, TIPO, CONTRATO_ID, TIPOPAGO_ID, BLOQUE_ID"
					+ " FROM ENOC.EMP_HORAS"
					+ " WHERE CODIGO_PERSONAL = ? "+orden;
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new EmpHorasMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpHorasDao|lisPorMaestro|:"+ex);
		}
		
		return lista;
	}
	
	public List<EmpHoras> lisPorMaestro( String codigoPersonal, String year, String orden ) {		
		List<EmpHoras> lista	= new ArrayList<EmpHoras>();	
		try{
			String comando = " SELECT CODIGO_PERSONAL, FOLIO, CURSO_ID, CARGA_ID, FRECUENCIA, SEMANAS, PRECIO, MATERIA, CARRERA_ID,"
					+ " TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, ESTADO,"
					+ " TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, TIPO, CONTRATO_ID, TIPOPAGO_ID, BLOQUE_ID"
					+ " FROM ENOC.EMP_HORAS"
					+ " WHERE CODIGO_PERSONAL = ? AND TO_CHAR(FECHA_INI,'YYYY') = ? "+orden;
			Object[] parametros = new Object[] {codigoPersonal, year};
			lista = enocJdbc.query(comando, new EmpHorasMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpHorasDao|lisPorMaestro|:"+ex);
		}		
		return lista;
	}
	
	public List<aca.Mapa> lisMaestrosPorCarga( String cargaId, String orden ) {
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		
		try{
			String comando = " SELECT DISTINCT(CODIGO_PERSONAL) AS LLAVE, ENOC.EMP_APELLIDO(CODIGO_PERSONAL) AS VALOR FROM ENOC.EMP_HORAS WHERE CARGA_ID = ? "+orden;
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando,  new aca.MapaMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpHorasDao|lisMaestrosPorCarga|:"+ex);
		}
		
		return lista;
	}
	
	public List<EmpHoras> lisPorContrato( String codigoPersonal, String contratoId, String year, String orden ) {
		
		List<EmpHoras> lista	= new ArrayList<EmpHoras>();	
		String comando	= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, FOLIO, CURSO_ID, CARGA_ID, FRECUENCIA, SEMANAS, PRECIO, MATERIA, CARRERA_ID,"
					+ " TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, ESTADO,"
					+ " TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, TIPO, CONTRATO_ID, TIPOPAGO_ID, BLOQUE_ID"
					+ " FROM ENOC.EMP_HORAS"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND TO_CHAR(FECHA,'YYYY') <= ?"
					+ " AND (CONTRATO_ID = ? OR CONTRATO_ID = '0') "+orden;
			Object[] parametros = new Object[] {codigoPersonal, year, contratoId};
			lista = enocJdbc.query(comando, new EmpHorasMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpHorasDao|lisPorContrato|:"+ex);
		}
		
		return lista;
	}
	
	public List<EmpHoras> lisPorContrato( String codigoPersonal, String contratoId, String orden ) {
		
		List<EmpHoras> lista	= new ArrayList<EmpHoras>();	
		String comando	= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, FOLIO, CURSO_ID, CARGA_ID, FRECUENCIA, SEMANAS, PRECIO, MATERIA, CARRERA_ID,"
					+ " TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, ESTADO,"
					+ " TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, TIPO, CONTRATO_ID, TIPOPAGO_ID, BLOQUE_ID"
					+ " FROM ENOC.EMP_HORAS"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CONTRATO_ID = ?  "+orden;
			Object[] parametros = new Object[] {codigoPersonal, contratoId};
			lista = enocJdbc.query(comando, new EmpHorasMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpHorasDao|lisPorContrato|:"+ex);
		}
		
		return lista;
	}
	
	public List<String> lisPorYear( String year, String orden ) {		
		List<String> lista	= new ArrayList<String>();
		try{
			String comando = " SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.EMP_HORAS WHERE TO_CHAR(FECHA_INI,'YYYY') = ? "+orden;
			Object[] parametros = new Object[] {year};
			lista = enocJdbc.queryForList(comando, String.class, parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpHorasDao|lisPorYear|:"+ex);
		}
		
		return lista;
	}
	
	public List<EmpHoras> lisPorCarreraYCarga( String carrera, String cargaId, String orden ) {
		
		List<EmpHoras> lista	= new ArrayList<EmpHoras>();	
		String comando	= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, FOLIO, CURSO_ID, CARGA_ID, FRECUENCIA, SEMANAS, PRECIO, MATERIA, CARRERA_ID,"
					+ " TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, ESTADO,"
					+ " TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, TIPO, CONTRATO_ID, TIPOPAGO_ID, BLOQUE_ID"
					+ " FROM ENOC.EMP_HORAS"
					+ " WHERE CARRERA_ID = ?"
					+ " AND CARGA_ID = ? "+orden;
			Object[] parametros = new Object[] {carrera, cargaId};
			lista = enocJdbc.query(comando, new EmpHorasMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpHorasDao|lisPorCarreraYCarga|:"+ex);
		}
		
		return lista;
	}
	
	public List<EmpHoras> lisFiltros( String estado, String fecha, String cargaId, String tipo, String orden ) {
		
		List<EmpHoras> lista	= new ArrayList<EmpHoras>();
		String comando			= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, FOLIO, CURSO_ID, CARGA_ID, FRECUENCIA, SEMANAS, PRECIO, MATERIA, CARRERA_ID,"
					+ " TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, ESTADO,"
					+ " TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, TIPO, CONTRATO_ID, TIPOPAGO_ID, BLOQUE_ID"
					+ " FROM ENOC.EMP_HORAS"
					+ " WHERE ESTADO = ?";
			if (!fecha.equals("0")) comando += " AND TO_DATE('"+fecha+"','DD/MM/YYYY') BETWEEN ENOC.EMP_HORAS.FECHA_INI AND ENOC.EMP_HORAS.FECHA_FIN";
			// Filtro de carga 
			if (!cargaId.equals("0")) comando += " AND CARGA_ID = '"+cargaId+"' ";
			// Filtro de tipo 
			if (!tipo.equals("T")) comando += " AND TIPO = '"+tipo+"' ";
			
			comando += orden;
			
			lista = enocJdbc.query(comando, new EmpHorasMapper(), estado);			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpHorasDao|lisFiltros|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,String> mapEstadoPorCarrera(String cargaId) {
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CARRERA_ID||ESTADO AS LLAVE, COUNT(*) AS VALOR FROM ENOC.EMP_HORAS WHERE CARGA_ID = ? GROUP BY CARRERA_ID||ESTADO";
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpHorasDao|mapEstadoPorCarrera|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapEnContrato(String cargaId) {
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CARRERA_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.EMP_HORAS WHERE CARGA_ID = ? AND CONTRATO_ID != '0' GROUP BY CARRERA_ID";
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpHorasDao|mapEstadoPorCarrera|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapSinContrato(String cargaId) {
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CARRERA_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.EMP_HORAS WHERE CARGA_ID = ? AND CONTRATO_ID = '0' GROUP BY CARRERA_ID";
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpHorasDao|mapEstadoPorCarrera|:"+ex);
		}
		
		return mapa;
	}

	public HashMap<String,String> mapContratoId(String contratoId) {
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL||FOLIO AS LLAVE, CODIGO_PERSONAL||FOLIO AS VALOR FROM ENOC.EMP_HORAS WHERE CONTRATO_ID = ?";
			Object[] parametros = new Object[] {contratoId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpHorasDao|mapContratoId|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapContratoMaterias() {
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CONTRATO_ID AS LLAVE, COUNT (*) AS VALOR FROM ENOC.EMP_HORAS GROUP BY CONTRATO_ID";		
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpHorasDao|mapContratoMaterias|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapPresupuestoCcosto(String cargaId, String estado){
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CCOSTO_CARRERA(CARRERA_ID) AS LLAVE, "
					+ " SUM(FRECUENCIA*SEMANAS*PRECIO) AS VALOR "
					+ " FROM EMP_HORAS "
					+ " WHERE CARGA_ID = ? "
					+ " AND ESTADO = ? "
					+ " GROUP BY CCOSTO_CARRERA(CARRERA_ID)";
			Object[] parametros = new Object[] {cargaId, estado};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpHorasDao|mapPresupuestoCcosto|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaMaestroGasto(String cargas, String estado, String tipo){
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL||FACULTAD(CARRERA_ID) AS LLAVE, SUM(FRECUENCIA*SEMANAS*PRECIO) AS VALOR"
					+ " FROM EMP_HORAS"
					+ " WHERE CARGA_ID IN ("+cargas+")"
					+ " AND ESTADO = ?"
					+ " AND TIPO = ? "
					+ " GROUP BY CODIGO_PERSONAL||FACULTAD(CARRERA_ID)";
			Object[] parametros = new Object[] {estado, tipo};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpHorasDao|mapaMaestroGasto|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaMaestroMaterias(String cargas, String estado, String tipo){
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL||FACULTAD(CARRERA_ID) AS LLAVE, COUNT(*) AS VALOR"
					+ " FROM EMP_HORAS"
					+ " WHERE CARGA_ID IN ("+cargas+")"
					+ " AND ESTADO = ?"
					+ " AND TIPO = ?"
					+ " GROUP BY CODIGO_PERSONAL||FACULTAD(CARRERA_ID)";
			Object[] parametros = new Object[] {estado, tipo};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpHorasDao|mapaMaestroMaterias|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaMaestroHoras(String cargas, String estado, String tipo){
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL||FACULTAD(CARRERA_ID) AS LLAVE, SUM(FRECUENCIA*SEMANAS) AS VALOR"
					+ " FROM ENOC.EMP_HORAS"
					+ " WHERE CARGA_ID IN ("+cargas+")"
					+ " AND ESTADO = ?"
					+ " AND TIPO = ?"
					+ " GROUP BY CODIGO_PERSONAL||FACULTAD(CARRERA_ID)";
			Object[] parametros = new Object[] {estado, tipo};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpHorasDao|mapaMaestroHoras|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaMaestroHorasSemana(String cargas, String estado, String tipo){
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL||FACULTAD(CARRERA_ID) AS LLAVE, SUM(FRECUENCIA) AS VALOR"
					+ " FROM ENOC.EMP_HORAS"
					+ " WHERE CARGA_ID IN ("+cargas+")"
					+ " AND ESTADO = ?"
					+ " AND TIPO = ?"
					+ " GROUP BY CODIGO_PERSONAL||FACULTAD(CARRERA_ID)";
			Object[] parametros = new Object[] {estado, tipo};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpHorasDao|mapaMaestroHorasSemana|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaMaestroGastoTotal(String cargas, String estado, String tipo){
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, SUM(FRECUENCIA*SEMANAS*PRECIO) AS VALOR"
					+ " FROM EMP_HORAS"
					+ " WHERE CARGA_ID IN ("+cargas+")"
					+ " AND ESTADO = ?"
					+ " AND TIPO = ? "
					+ " GROUP BY CODIGO_PERSONAL";
			Object[] parametros = new Object[] {estado, tipo};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpHorasDao|mapaMaestroGastoTotal|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaMaestroMateriasTotal(String cargas, String estado, String tipo){
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, COUNT(*) AS VALOR"
					+ " FROM EMP_HORAS"
					+ " WHERE CARGA_ID IN ("+cargas+")"
					+ " AND ESTADO = ?"
					+ " AND TIPO = ?"
					+ " GROUP BY CODIGO_PERSONAL";
			Object[] parametros = new Object[] {estado, tipo};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpHorasDao|mapaMaestroMateriasTotal|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaMaestroHorasTotal(String cargas, String estado, String tipo){
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, SUM(FRECUENCIA*SEMANAS) AS VALOR"
					+ " FROM ENOC.EMP_HORAS"
					+ " WHERE CARGA_ID IN ("+cargas+")"
					+ " AND ESTADO = ?"
					+ " AND TIPO = ?"
					+ " GROUP BY CODIGO_PERSONAL";
			Object[] parametros = new Object[] {estado, tipo};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpHorasDao|mapaMaestroHorasTotal|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapPresupuestoCarrera(String cargaId, String tipo ){
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CARRERA_ID||ESTADO AS LLAVE, "
					+ " SUM(FRECUENCIA*SEMANAS*PRECIO) AS VALOR "
					+ " FROM ENOC.EMP_HORAS"
					+ " WHERE CARGA_ID = ?"
					+ " AND TIPO = ?"		
					+ " GROUP BY CARRERA_ID,ESTADO";
			Object[] parametros = new Object[] {cargaId, tipo};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpHorasDao|mapPresupuestoCarrera|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaMaterias(String year){
		HashMap <String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, COUNT(FOLIO) AS VALOR "
					+ " FROM ENOC.EMP_HORAS "
					+ " WHERE TO_CHAR(FECHA_INI,'YYYY') = ?"
					+ " GROUP BY CODIGO_PERSONAL ";
			Object[] parametros = new Object[] {year};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpHorasDao|mapaMaterias|:"+ex);
		}		
		return mapa;
	}	
	
	public HashMap<String, String> mapaPendientes(String year){
		HashMap <String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, COUNT(FOLIO) AS VALOR"
					+ " FROM ENOC.EMP_HORAS "
					+ " WHERE TO_CHAR(FECHA_INI,'YYYY') = ?"
					+ " AND CONTRATO_ID = '0'"
					+ " AND ESTADO = 'N'"
					+ " GROUP BY CODIGO_PERSONAL ";
			Object[] parametros = new Object[] {year};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpHorasDao|mapaMaterias|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, String> mapaRegistradas(String year){
		HashMap <String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, COUNT(FOLIO) AS VALOR"
					+ " FROM ENOC.EMP_HORAS"
					+ " WHERE TO_CHAR(FECHA_INI,'YYYY') = ?"
					+ " AND CONTRATO_ID != '0'"
					+ " AND ESTADO = 'N'"
					+ " GROUP BY CODIGO_PERSONAL";
			Object[] parametros = new Object[] {year};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpHorasDao|mapaRegistradas|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, String> mapaImportePorCarrera(String contratoId){
		HashMap <String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CARRERA_ID AS LLAVE, SUM(PRECIO*FRECUENCIA*SEMANAS) AS VALOR"
					+ " FROM ENOC.EMP_HORAS"
					+ " WHERE CONTRATO_ID = ?"
					+ " GROUP BY CARRERA_ID";
			Object[] parametros = new Object[] {contratoId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpHorasDao|mapaImportePorCarrera|:"+ex);
		}		
		return mapa;
	}
}