package aca.financiero.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class FesContratoFinancieroDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public List<FesContratoFinanciero> getListAlumContrato(String codigoPersonal, String orden ) {
		List<FesContratoFinanciero> lisAlumno	= new ArrayList<FesContratoFinanciero>();		
		try{
			String comando = "SELECT ID, MATRICULA," +
				" TO_CHAR(FECHA_VENCIMIENTO, 'DD/MM/YYYY') AS FECHA_VENCIMIENTO," +
				" IMPORTE, LIQUIDADO," +
				" TO_CHAR(FECHA_LIQUIDACION, 'DD/MM/YYYY') AS FECHA_LIQUIDACION," +
				" OBSERVACIONES" +
				" FROM NOE.FES_CONTRATO_FINANCIERO" +
				" WHERE MATRICULA = ? "+orden;
			lisAlumno = enocJdbc.query(comando, new FesContratoFinancieroMapper(), codigoPersonal);			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FesContratoFinancieroDao|getListAlumContrato|:"+ex);
		}
		
		return lisAlumno;
	}

	public List<FesContratoFinanciero> getListAll(String orden) {
		List<FesContratoFinanciero> lisAlumno	= new ArrayList<FesContratoFinanciero>();
		
		try{
			String comando = "SELECT ID, MATRICULA," +
				" TO_CHAR(FECHA_VENCIMIENTO, 'DD/MM/YYYY') AS FECHA_VENCIMIENTO," +
				" IMPORTE, LIQUIDADO," +
				" TO_CHAR(FECHA_LIQUIDACION, 'DD/MM/YYYY') AS FECHA_LIQUIDACION," +
				" OBSERVACIONES" +
				" FROM ENOC.FES_CONTRATO_FINANCIERO "+orden;
			
			lisAlumno = enocJdbc.query(comando, new FesContratoFinancieroMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FesContratoFinancieroDao|getListAll|:"+ex);
		}
		return lisAlumno;
	}
	
	public double getAlumDeudaContrato(String codigoPersonal) {
		double saldo = 0;		
		
		try{
			String comando = "SELECT COALESCE(SUM(IMPORTE),0) AS DEUDA FROM NOE.FES_CONTRATO_FINANCIERO "+
				" WHERE MATRICULA = ?" +
				" AND TO_DATE(now(),'DD-MM-YYYY') > TO_DATE(FECHA_VENCIMIENTO,'DD-MM-YYYY')" +
				" AND LIQUIDADO = 'N'";
			
			Object[] parametros = new Object[] {
				codigoPersonal
			};
			
			saldo = enocJdbc.queryForObject(comando, Double.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FesContratoFinancieroDao|getAlumDeudaContrato|:"+ex);
		}		
		
		return saldo;
	}
	
	// Obtiene el saldo global del alumno
	public double getAlumSaldoGlobal(String codigoPersonal){		
		double saldo = 0;		
		
		try{
			String comando = "SELECT COALESCE(SALDOGLOBAL,0) AS DEUDA FROM NOE.SALDOS_ALUMNOS "+
				" WHERE MATRICULA = ?";
			
			Object[] parametros = new Object[] {
				codigoPersonal
			};
				
			saldo = enocJdbc.queryForObject(comando, Double.class,parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FesContratoFinancieroDao|getAlumSaldoGlobal|:"+ex);
		}		
		return saldo;
	}
	
	// Obtiene el saldo vencido del contrato financiero (VISTA)
	public double getAlumSaldoVencido(String codigoPersonal) {		
		double saldo = 0;		
		
		try{
			String comando = "SELECT COALESCE(SVENCIDO,0) AS DEUDA FROM NOE.SALDOS_ALUMNOS "+
				" WHERE MATRICULA = ?";

			Object[] parametros = new Object[] {
				codigoPersonal
			};
					
			saldo = enocJdbc.queryForObject(comando, Double.class,parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FesContratoFinancieroDao|getAlumSaldoVencido|:"+ex);
		}		
		return saldo;
	}
	
	// Obtiene el saldo vencido del contrato financiero (VISTA)
	public HashMap<String,String> mapSaldoVencido(String cursoCargaId) {
		HashMap<String,String> mapDeuda = new HashMap<String,String>();
		List<aca.Mapa> lista =  new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT MATRICULA AS LLAVE, COALESCE(SVENCIDO,0) AS VALOR FROM NOE.SALDOS_ALUMNOS"+
				" WHERE MATRICULA IN (SELECT CODIGO_PERSONAL FROM ENOC.KRDX_CURSO_ACT WHERE CURSO_CARGA_ID = ? AND TIPOCAL_ID != 'M')";
			
			Object[] parametros = new Object[] {
				cursoCargaId
			};
			
			lista = enocJdbc.query(comando, new aca.MapaMapper(),parametros);
			
			for(aca.Mapa objeto : lista) {
				mapDeuda.put(objeto.getLlave(), objeto.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FesContratoFinancieroDao|mapSaldoVencido|:"+ex);
		}		
		return mapDeuda;
	}
	
	// Obtiene el campo de diferencia en la vista SALDOS_ALUMNOS
	public HashMap<String,String> mapSaldoDiferencia() {
		HashMap<String,String> mapDeuda = new HashMap<String,String>();
		List<aca.Mapa> lista =  new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT MATRICULA AS LLAVE, COALESCE(DIFERENCIA,0) AS VALOR FROM NOE.SALDOS_ALUMNOS"+
				" WHERE MATRICULA IN (SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS)";			
		
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			
			for(aca.Mapa objeto : lista) {
				mapDeuda.put(objeto.getLlave(), objeto.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FesContratoFinancieroDao|mapSaldoDiferencia|:"+ex);
		}		
		return mapDeuda;
	}
	
	// Obtiene la diferencia del contrato financiero considerando el margen de deuda permitida(VISTA)
	public double getAlumDiferencia(String codigoPersonal) {
		double saldo = 0;
		
		try{
			String comando = "SELECT COALESCE(DIFERENCIA,0) AS DEUDA FROM NOE.SALDOS_ALUMNOS "+
				" WHERE MATRICULA = ?";
			
			Object[] parametros = new Object[] {
				codigoPersonal
			};
						
			saldo = enocJdbc.queryForObject(comando, Double.class,parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FesContratoFinancieroDao|getAlumDiferencia|:"+ex);
		}
		return saldo;
	}
	
	// Obtiene la deuda del contrato financiero (VISTA)
	public double getAlumImporteContrato(String codigoPersonal) {		
		double saldo = 0;
		
		try{
			String comando = "SELECT COALESCE(IMPORTE_CONTRATOS,0) AS DEUDA FROM NOE.SALDOS_ALUMNOS "+
				" WHERE MATRICULA = ?";
				
			Object[] parametros = new Object[] {
				codigoPersonal
			};
								
			saldo = enocJdbc.queryForObject(comando, Double.class,parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FesContratoFinancieroDao|getAlumImporteContrato|:"+ex);
		}
		return saldo;
	}
	
	// Determina el saldo vencido del contrato financiero
	public double getCancelaPortal(String codigoPersonal) {
		double saldo = 0;		
		
		try{
			String comando = "SELECT DIFERENCIA FROM NOE.SALDOS_ALUMNOS WHERE MATRICULA = ?";
			
			Object[] parametros = new Object[] {
				codigoPersonal
			};
									
			saldo = enocJdbc.queryForObject(comando, Double.class,parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FesContratoFinancieroDao|getCancelaPortal|:"+ex);
		}		
		
		return saldo;
	}

	public HashMap<String, String> getMapTalleres() {
		HashMap<String, String> mapa= new HashMap<String, String>();
		List<aca.Mapa> lista =  new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CA.MATRICULA||(SELECT CC.NOMBRE FROM MATEO.CONT_CCOSTO CC WHERE ID_EJERCICIO='001-2012' AND CC.ID_CCOSTO = (SELECT CCOSTO_ID FROM NOE.AFE_PLAZA WHERE ID = CA.PLAZA_ID)) AS LLAVE,"
					+ " CA.NUMERO_HORAS AS VALOR" +
					" FROM NOE.AFE_CONTRATO_ALUMNO CA" +
					" WHERE CA.STATUS ='I'";
			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			
			for(aca.Mapa objeto : lista) {
				mapa.put(objeto.getLlave(), " ("+objeto.getValor()+"hrs.)");
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FesContratoFinancieroDao|getMapTalleres|:"+ex);
		}
		
		return mapa;
	}
}