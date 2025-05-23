package aca.bec.spring;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BecTipoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( BecTipo tipo){
		boolean ok = false;
		try{
			String comando = "INSERT INTO ENOC.BEC_TIPO"
				+ "(ID_EJERCICIO, TIPO, NOMBRE, DEPARTAMENTOS, CUENTA, PORCENTAJE, MESES, HORAS, HORAS_PREPA,"
				+ " ACUERDO, IMPORTE, TIPO_ALUMNO, DIEZMO, ESTADO, ACUMULA, COLPORTA, APLICA_ADICIONAL, MAXIMO, LIMITE, CUENTA_SUNPLUS, FLAG, MOSTRAR,DESCRIPCION)"
				+ " VALUES( ?, TO_NUMBER(?,'99'), ?, ?, ?, ?, ?, TO_NUMBER(?,'9999'), TO_NUMBER(?,'9999'),"
				+ " ?, TO_NUMBER(?,'999999.99'), ?, ?, ?, ?, ?, ?, TO_NUMBER(?,'999.99'), TO_NUMBER(?,'99999999.99'), ?, ?, ?, ?)";
			Object[] parametros = new Object[] {tipo.getIdEjercicio(), tipo.getTipo(), tipo.getNombre(),tipo.getDepartamentos(),
					tipo.getCuenta(), tipo.getPorcentaje(), tipo.getMeses(), tipo.getHoras(), tipo.getHorasPrepa(),
					tipo.getAcuerdo(), tipo.getImporte(), tipo.getTipoAlumno(), tipo.getDiezmo(), tipo.getEstado(),
					tipo.getAcumula(), tipo.getColporta(), tipo.getAplicaAdicional(), tipo.getMaximo(), tipo.getLimite(), 
					tipo.getCuentaSunplus(), tipo.getFlag(), tipo.getMostrar(), tipo.getDescripcion()};			
			if (enocJdbc.update(comando,parametros)==1)
				ok = true;			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BeTipoDao|insertReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateReg( BecTipo tipo) {
		boolean ok = false;
		try{
			String comando = "UPDATE ENOC.BEC_TIPO"
				+ " SET NOMBRE = ?,"
				+ " DEPARTAMENTOS = ?, "
				+ " CUENTA = ?,"	
				+ " PORCENTAJE = ?,"
				+ " MESES = ?,"
				+ " HORAS = TO_NUMBER(?,'9999'),"
				+ " HORAS_PREPA = TO_NUMBER(?,'9999'),"
				+ " ACUERDO = ?,"
				+ " IMPORTE = TO_NUMBER(?,'999999.99'),"
				+ " TIPO_ALUMNO = ?,"
				+ " DIEZMO = ?,"
				+ " ESTADO = ?,"
				+ " ACUMULA = ?,"
				+ " COLPORTA = ?,"
				+ " APLICA_ADICIONAL = ?,"
				+ " MAXIMO = TO_NUMBER(?,'999.99'),"				
				+ " LIMITE = TO_NUMBER(?,'99999999.99'),"
				+ " CUENTA_SUNPLUS = ?,"
				+ " FLAG = ?,"
				+ " MOSTRAR = ?,"
				+ " DESCRIPCION = ?"				
				+ " WHERE ID_EJERCICIO = ? AND TIPO = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {
					tipo.getNombre(),tipo.getDepartamentos(), tipo.getCuenta(), tipo.getPorcentaje(), 
					tipo.getMeses(), tipo.getHoras(), tipo.getHorasPrepa(), tipo.getAcuerdo(),
					tipo.getImporte(), tipo.getTipoAlumno(), tipo.getDiezmo(), tipo.getEstado(), tipo.getAcumula(),
					tipo.getColporta(), tipo.getAplicaAdicional(), tipo.getMaximo(), tipo.getLimite(), tipo.getCuentaSunplus(), 
					tipo.getFlag(), tipo.getMostrar(),tipo.getDescripcion(),
					tipo.getIdEjercicio(), tipo.getTipo()
			};			
			if (enocJdbc.update(comando,parametros)==1)
				ok = true;			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecTipoDao|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean updateTipoAlumno( String tipoAlumno, String ejercicioId, String tipoId) {
		boolean ok = false;
		try{
			String comando = "UPDATE ENOC.BEC_TIPO SET TIPO_ALUMNO = ? WHERE ID_EJERCICIO = ? AND TIPO = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {tipoAlumno, ejercicioId, tipoId };			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecTipoDao|updateReg|:"+ex);		
		}		
		return ok;
	}
	
	public boolean updateDepartamentos( String ccostos, String ejercicioId, String tipoId) {
		boolean ok = false;
		try{
			String comando = "UPDATE ENOC.BEC_TIPO SET DEPARTAMENTOS = ? WHERE ID_EJERCICIO = ? AND TIPO = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {ccostos, ejercicioId, tipoId };			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecTipoDao|updateCcostos|:"+ex);		
		}		
		return ok;
	}
	
	public boolean updateMeses( String meses, String ejercicioId, String tipoId) {
		boolean ok = false;
		try{
			String comando = "UPDATE ENOC.BEC_TIPO SET MESES = ? WHERE ID_EJERCICIO = ? AND TIPO = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {meses, ejercicioId, tipoId };			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecTipoDao|updateMeses|:"+ex);		
		}		
		return ok;
	}
	
	
	public boolean deleteReg( String idEjercicio, String tipo) {
		boolean ok = false;
		try{
			String comando = "DELETE FROM ENOC.BEC_TIPO WHERE ID_EJERCICIO = ? AND TIPO = TO_NUMBER(?,'99') ";
			Object[] parametros = new Object[] {idEjercicio, tipo};
			
			if (enocJdbc.update(comando,parametros)==1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecTipoDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public boolean existeReg( String idEjercicio, String tipo) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BEC_TIPO WHERE ID_EJERCICIO = ? AND TIPO = TO_NUMBER(?,'99') ";
			Object[] parametros = new Object[] {idEjercicio, tipo};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecTipoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public BecTipo mapeaRegId( String idEjercicio, String tipo ) {
		
		BecTipo becTipo = new BecTipo();		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BEC_TIPO WHERE ID_EJERCICIO = ? AND TIPO = TO_NUMBER(?,'99') ";
			Object[] parametros = new Object[] {idEjercicio, tipo};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				comando = "SELECT ID_EJERCICIO, TIPO, NOMBRE, DEPARTAMENTOS, CUENTA, PORCENTAJE, MESES, HORAS,"
					+ " HORAS_PREPA, ACUERDO, IMPORTE, TIPO_ALUMNO, DIEZMO, ESTADO, ACUMULA, COLPORTA, APLICA_ADICIONAL, "
					+ " MAXIMO, LIMITE, CUENTA_SUNPLUS, FLAG, MOSTRAR, DESCRIPCION"
					+ " FROM ENOC.BEC_TIPO WHERE ID_EJERCICIO = ? AND TIPO = TO_NUMBER(?,'99') ";					
				becTipo = enocJdbc.queryForObject(comando, new BecTipoMapper(),parametros);
			}							
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecTipoDao|mapeaRegId|:"+ex);
		}
		
		return becTipo;
	}
	
	public String maximoReg( String idEjercicio ) {		
		int maximo = 1;		
		try{
			String comando = "SELECT COALESCE(MAX(TIPO)+1,1) FROM ENOC.BEC_TIPO WHERE ID_EJERCICIO = ?";
			Object[] parametros = new Object[] {idEjercicio};
			maximo = enocJdbc.queryForObject(comando, Integer.class, parametros);									
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecTipoDao|maximoReg|:"+ex);
		}		
		return String.valueOf(maximo);
	}
	
	public String getTipo(String idEjercicio, String acuerdo){
		String tipo 			= "0";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BEC_TIPO WHERE ID_EJERCICIO = ? AND ACUERDO = ?";
			Object[] parametros = new Object[] {idEjercicio, acuerdo};
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1) {
				comando = "SELECT TIPO FROM ENOC.BEC_TIPO WHERE ID_EJERCICIO = ? AND ACUERDO = ? AND ROWNUM = 1";
				tipo = enocJdbc.queryForObject(comando, String.class,parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecTipoDao|getTipo|:"+ex);			
		}		
		return tipo;		
	}
	
	public String getTipoAlumno(String idEjercicio, String tipo){				
		String tipoAlumno 		= "none";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BEC_TIPO WHERE ID_EJERCICIO = ? AND TIPO = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {idEjercicio, tipo};
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1) {
				comando 	= "SELECT TIPO_ALUMNO FROM ENOC.BEC_TIPO WHERE ID_EJERCICIO = ? AND TIPO = TO_NUMBER(?,'99')";
				tipoAlumno 	= enocJdbc.queryForObject(comando, String.class,parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecTipoDao|getTipoAlumno|:"+ex);
		}		
		return tipoAlumno;
	}
	
	public String getHoras(String idEjercicio, String acuerdo){
		String horas 			= "0";	
		try{			
			String comando = "SELECT COUNT(*) FROM ENOC.BEC_TIPO WHERE ID_EJERCICIO = ? AND ACUERDO = ?";
			Object[] parametros = new Object[] {idEjercicio, acuerdo};
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1) {
				comando = "SELECT HORAS FROM ENOC.BEC_TIPO WHERE ID_EJERCICIO = ? AND ACUERDO = ?";
				horas 	= enocJdbc.queryForObject(comando, String.class,parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecTipoDao|getHoras|:"+ex);
		}
		
		return horas;		
	}
	
	
	public String getHorasPrepa(String idEjercicio, String acuerdo){
		String horas 			= "0";	
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BEC_TIPO WHERE ID_EJERCICIO = ? AND ACUERDO = ?";
			Object[] parametros = new Object[] {idEjercicio, acuerdo};
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1) {
				comando = "SELECT HORAS_PREPA FROM ENOC.BEC_TIPO WHERE ID_EJERCICIO = ? AND ACUERDO = ?";
				horas = enocJdbc.queryForObject(comando, String.class,parametros);
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecTipoDao|getHorasPrepa|:"+ex);
		}		
		return horas;		
	}
	
	public String getBasicosyAdicionales( String ejercicioId) {		
		String tipos	= "0";
		List<String> lista 		= new ArrayList<String>();
		try{
			String comando = "SELECT TIPO FROM ENOC.BEC_TIPO WHERE ID_EJERCICIO = ? AND ACUERDO IN ('B','A','I','E')";		
			lista = enocJdbc.queryForList(comando, String.class, ejercicioId);
			for(String tipo : lista){
				if (tipos.equals("0")) tipos=tipo; else tipos = tipos+","+tipo; 
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecTipoDao|getBasicosyAdicionales|:"+ex);
		}		
		return tipos;
	}
	
	public String getBasicos( String ejercicioId) {		
		String tipos	= "0";
		List<String> lista 		= new ArrayList<String>();
		try{
			String comando = "SELECT TIPO FROM ENOC.BEC_TIPO WHERE ID_EJERCICIO = ? AND ACUERDO IN ('B','I')";	
			lista = enocJdbc.queryForList(comando, String.class, ejercicioId);
			for(String tipo : lista){
				if (tipos.equals("0")) tipos=tipo; else tipos = tipos+","+tipo; 
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecTipoDao|getBasicos|:"+ex);
		}		
		return tipos;
	}
	
	public String getImporte(String idEjercicio, String acuerdo){
		String importe 			= "0";		
		try{
			String comando = "SELECT COUNT(IMPORTE) FROM ENOC.BEC_TIPO WHERE ID_EJERCICIO = ? AND ACUERDO = ?";
			Object[] parametros = new Object[] {idEjercicio, acuerdo};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				comando = "SELECT IMPORTE FROM ENOC.BEC_TIPO WHERE ID_EJERCICIO = ? AND ACUERDO = ?";
				importe = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecTipoDao|getImporte|:"+ex);
		}		
		return importe;		
	}
	
	public String getAcuerdo(String idEjercicio, String tipo){
		String acuerdo 			= "";			
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BEC_TIPO WHERE ID_EJERCICIO = ? AND TIPO = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {idEjercicio, acuerdo};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				comando = "SELECT ACUERDO FROM ENOC.BEC_TIPO WHERE ID_EJERCICIO = ? AND TIPO = TO_NUMBER(?,'99')";
				acuerdo = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecTipoDao|getAcuerdo|:"+ex);
		}		
		return acuerdo;		
	}	
	
	public ArrayList<BecTipo> getListAll( String orden) {
		
		List<BecTipo> lista = new ArrayList<BecTipo>();
		
		try{
			String comando = "SELECT ID_EJERCICIO, TIPO, NOMBRE, DEPARTAMENTOS, CUENTA, PORCENTAJE, MESES, HORAS,"
				+ " HORAS_PREPA, ACUERDO, IMPORTE, TIPO_ALUMNO, DIEZMO, ESTADO, ACUMULA, COLPORTA, APLICA_ADICIONAL, "
				+ " MAXIMO, LIMITE, CUENTA_SUNPLUS, FLAG, MOSTRAR, DESCRIPCION"
				+ " FROM ENOC.BEC_TIPO " +orden;	
			lista = enocJdbc.query(comando, new BecTipoMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecTipoDao|getListAll|:"+ex);
		}
		
		return (ArrayList<BecTipo>)lista;
	}
	
	public List<BecTipo> getListAcuerdo(String idEjercicio, String tipos, String orden){
		List<BecTipo> lista			= new ArrayList<BecTipo>();
		try{
			String comando = "SELECT * FROM ENOC.BEC_TIPO WHERE ID_EJERCICIO = ? AND ACUERDO IN ("+tipos+") "+orden;		
			lista = enocJdbc.query(comando, new BecTipoMapper(), idEjercicio);
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecTipoDao|getListAcuerdo|:"+ex);
		}		
		return lista;
	}
	
	public ArrayList<BecTipo> listPorEjercicio( String ejercicioId, String orden) {
		
		List<BecTipo> lista = new ArrayList<BecTipo>();
		
		try{
			String comando = "SELECT ID_EJERCICIO, TIPO, NOMBRE, DEPARTAMENTOS, CUENTA, PORCENTAJE, MESES, HORAS,"
				+ " HORAS_PREPA, ACUERDO, IMPORTE, TIPO_ALUMNO, DIEZMO, ESTADO, ACUMULA, COLPORTA, APLICA_ADICIONAL, "
				+ " MAXIMO, LIMITE, CUENTA_SUNPLUS, FLAG, MOSTRAR, DESCRIPCION"
				+ " FROM ENOC.BEC_TIPO"
				+ " WHERE ID_EJERCICIO = ? " +orden;	
			Object[] parametros = new Object[] {ejercicioId};
			lista = enocJdbc.query(comando, new BecTipoMapper(),parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecTipoDao|getListAll|:"+ex);
		}
		
		return (ArrayList<BecTipo>)lista;
	}
	
	public ArrayList<BecTipo> lisPorTipoyCarrera( String ejercicioId, String tipo, String carreraId, String orden) {
		List<BecTipo> lista = new ArrayList<BecTipo>();		
		try{
			String comando = "SELECT ID_EJERCICIO, TIPO, NOMBRE, DEPARTAMENTOS, CUENTA, PORCENTAJE, MESES, HORAS,"
				+ " HORAS_PREPA, ACUERDO, IMPORTE, TIPO_ALUMNO, DIEZMO, ESTADO, ACUMULA, COLPORTA, APLICA_ADICIONAL, "
				+ " MAXIMO, LIMITE, CUENTA_SUNPLUS, FLAG, MOSTRAR, DESCRIPCION"
				+ " FROM ENOC.BEC_TIPO"
				+ " WHERE ID_EJERCICIO = ? "
				+ " AND INSTR(TIPO_ALUMNO,?) > 0"
				+ " AND MOSTRAR = 'S'"				
				+ " AND TIPO IN (SELECT TIPO FROM ENOC.BEC_TIPO_CARRERA WHERE ID_EJERCICIO = ? AND CARRERA_ID = ?) " +orden;
			Object[] parametros = new Object[] {ejercicioId, tipo, ejercicioId, carreraId};
			lista = enocJdbc.query(comando, new BecTipoMapper(),parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecTipoDao|getListAll|:"+ex);
		}
		
		return (ArrayList<BecTipo>)lista;
	}
	
	public HashMap<String, BecTipo> mapaBecTipos(String ejercicioId){
		
		HashMap<String,BecTipo> mapa		= new HashMap<String,BecTipo>();
		List<BecTipo> lista 			= new ArrayList<BecTipo>();
				
		try{
			String comando = "SELECT ID_EJERCICIO, TIPO, NOMBRE, DEPARTAMENTOS, CUENTA, PORCENTAJE, MESES, HORAS,"
					+ " HORAS_PREPA, ACUERDO, IMPORTE, TIPO_ALUMNO, DIEZMO, ESTADO, ACUMULA, COLPORTA, APLICA_ADICIONAL, "
					+ " MAXIMO, LIMITE, CUENTA_SUNPLUS, FLAG, MOSTRAR, DESCRIPCION"
					+ " FROM ENOC.BEC_TIPO"
					+ " WHERE ID_EJERCICIO = ?";
			Object[] parametros = new Object[] { ejercicioId };
			lista = enocJdbc.query(comando, new BecTipoMapper(),parametros);
			for (BecTipo tipo : lista) {
				mapa.put(tipo.getTipo(), tipo);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecTipoDao|mapaBecTipos|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaPrecios(String ejercicioId){
		
		HashMap<String,String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
				
		try{
			String comando = "SELECT DISTINCT(ACUERDO) AS LLAVE, IMPORTE AS VALOR FROM ENOC.BEC_TIPO WHERE ID_EJERCICIO = ?";
			Object[] parametros = new Object[] { ejercicioId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(),parametros);
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecTipoDao|mapaPrecios|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaTipos(String ejercicioId){
		
		HashMap<String,String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
				
		try{
			String comando = "SELECT TIPO AS LLAVE, NOMBRE AS VALOR FROM ENOC.BEC_TIPO WHERE ID_EJERCICIO = ?";
			Object[] parametros = new Object[] { ejercicioId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(),parametros);
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecTipoDao|mapaTipos|:"+ex);
		}
		
		return mapa;
	}
	
	public String getTipoNombre(String tipo, String ejercicioId) {
		String nombre = "";
		
		try{
			String comando = "SELECT NOMBRE FROM ENOC.BEC_TIPO WHERE TIPO = TO_NUMBER(?,'99') AND ID_EJERCICIO = ?"; 
			
			Object[] parametros = new Object[] {tipo,ejercicioId};
			nombre = enocJdbc.queryForObject(comando, String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error -  aca.bec.spring.BecTipoDao|getTipoNombre|:"+ex);
		}
		
		return nombre;		
	}
	
}
