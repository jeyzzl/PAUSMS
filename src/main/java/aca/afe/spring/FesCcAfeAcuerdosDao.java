package aca.afe.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class FesCcAfeAcuerdosDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public FesCcAfeAcuerdos mapeaRegId(String matricula, String folio, String puesto){
		FesCcAfeAcuerdos afeAcuerdos = new FesCcAfeAcuerdos();
				
		try{
			String comando = "SELECT MATRICULA, CARGA_ID, BLOQUE, TIPO_ID,	TIPO_NOMBRE,TIPO_CUENTA,TIPO_IMPORTE,TIPO_ACUERDO,"
					+ " ACUERDO_FECHA, ACUERDO_IMP_MATRICULA,  ACUERDO_IMP_ENSENANZA,ACUERDO_IMP_INTERNADO, ACUERDO_VIGENCIA, ACUERDO_FOLIO,"
					+ " ACUERDO_PROMESA, ACUERDO_HORAS, ACUERDO_EJERCICIO_ID, ACUERDO_CCOSTO_ID, ALPUESTO_PUESTO_ID, CATEGORIA_ID, CATEGORIA_NOMBRE,"
					+ " ALPUESTO_FECHA_INICIAL, ALPUESTO_FECHA_FINAL, ALPUESTO_TIPO, COALESCE(TOTAL_BECA_ADIC,0) AS TOTAL_BECA_ADIC, VALOR, ID"
					+ " FROM MATEO.FES_CC_AFE_ACUERDOS"
					+ " WHERE MATRICULA = ?"
					+ " AND ACUERDO_FOLIO = TO_NUMBER(?,'999')"
					+ " AND ALPUESTO_PUESTO_ID = ?";
			
			Object[] parametros = new Object[] {matricula, folio, puesto};
			afeAcuerdos = enocJdbc.queryForObject(comando, new FesCcAfeAcuerdosMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.spring.afe.FesCcAfeAcuerdosDao|mapeaRegId|:"+ex);
		}
		return afeAcuerdos;
	}
	
	public boolean insertReg(FesCcAfeAcuerdos afeAcuerdos){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO MATEO.FES_CC_AFE_ACUERDOS "+ 
				" (MATRICULA, CARGA_ID, BLOQUE, TIPO_ID, TIPO_NOMBRE," +
					" TIPO_CUENTA, TIPO_IMPORTE, TIPO_ACUERDO, ACUERDO_FECHA, ACUERDO_IMP_MATRICULA," +
					" ACUERDO_IMP_ENSENANZA, ACUERDO_IMP_INTERNADO, ACUERDO_VIGENCIA, ACUERDO_FOLIO, ACUERDO_PROMESA, " +
					" ACUERDO_HORAS, ACUERDO_EJERCICIO_ID, ACUERDO_CCOSTO_ID, ALPUESTO_PUESTO_ID, CATEGORIA_ID, " +
					" CATEGORIA_NOMBRE, ALPUESTO_FECHA_INICIAL, ALPUESTO_FECHA_FINAL, ALPUESTO_TIPO, TOTAL_BECA_ADIC, VALOR, ID ) "+
				"VALUES( ?, ?, TO_NUMBER(?, '99'), TO_NUMBER(?, '99'), ?, " +
				" ?, TO_NUMBER(?, '9999999999.99'), ?, TO_DATE(?, 'DD/MM/YYYY'), TO_NUMBER(?, '9999999999.99')," +
				" TO_NUMBER(?, '9999999999.99'), TO_NUMBER(?, '9999999999.99'), TO_DATE(?, 'DD/MM/YYYY'), TO_NUMBER(?, '999'), TO_NUMBER(?, '9999999999.99')," +
				" TO_NUMBER(?, '9999'), ?, ?, ?, TO_NUMBER(?, '999')," +
				" ?, TO_DATE(?, 'DD/MM/YYYY'), TO_DATE(?, 'DD/MM/YYYY'), ?, TO_NUMBER(?, '99999999.99'), ?, TO_NUMBER( ?,'9999999') )";
			
			Object[] parametros = new Object[] {
				afeAcuerdos.getMatricula(),afeAcuerdos.getCargaId(),afeAcuerdos.getBloque(),afeAcuerdos.getTipoId(),afeAcuerdos.getTipoNombre(),afeAcuerdos.getTipoCuenta(),
				afeAcuerdos.getTipoImporte(),afeAcuerdos.getTipoAcuerdo(),afeAcuerdos.getAcuerdoFecha(),afeAcuerdos.getAcuerdoImpMatricula(),afeAcuerdos.getAcuerdoImpEnsenanza(),
				afeAcuerdos.getAcuerdoImpInternado(),afeAcuerdos.getAcuerdoVigencia(),afeAcuerdos.getAcuerdoFolio(),afeAcuerdos.getAcuerdoPromesa(),afeAcuerdos.getAcuerdoHoras(),
				afeAcuerdos.getAcuerdoEjercicioId(),afeAcuerdos.getAcuerdoCcostoId(),afeAcuerdos.getAlpuestoPuestoId(),afeAcuerdos.getCategoriaId(),afeAcuerdos.getCategoriaNombre(),
				afeAcuerdos.getAlpuestoFechaInical(),afeAcuerdos.getAlpuestoFechaFinal(),afeAcuerdos.getAlpuestoTipo(),afeAcuerdos.getTotalBecaAdic(),afeAcuerdos.getValor(),
				afeAcuerdos.getId()
			};
			
			if (enocJdbc.update(comando,parametros) >=1) {
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.spring.afe.FesCcAfeAcuerdosDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg(FesCcAfeAcuerdos afeAcuerdos ){
		boolean ok = false;
		
		try{
			String comando = "UPDATE MATEO.FES_CC_AFE_ACUERDOS"
					+ " SET"
					+ " CARGA_ID = ?,"
					+ " BLOQUE = TO_NUMBER(?, '99'),"
					+ " TIPO_ID	= TO_NUMBER(?, '99'),"
					+ " TIPO_NOMBRE = ?,"
					+ " TIPO_CUENTA = ?,"
					+ " TIPO_IMPORTE = TO_NUMBER(?, '9999999999.99'),"
					+ " TIPO_ACUERDO = ?,"
					+ " ACUERDO_FECHA = TO_DATE(?, 'DD/MM/YYYY'),"
					+ " ACUERDO_IMP_MATRICULA = ?,"
					+ " ACUERDO_IMP_ENSENANZA = ?,"
					+ " ACUERDO_IMP_INTERNADO = ?,"
					+ " ACUERDO_VIGENCIA = TO_DATE(?, 'DD/MM/YYYY'),"
					+ " ACUERDO_PROMESA = TO_NUMBER(?, '9999999999.99'),"
					+ " ACUERDO_HORAS = TO_NUMBER(?, '9999'),"
					+ " ACUERDO_EJERCICIO_ID = ?,"
					+ " ACUERDO_CCOSTO_ID = ?,"
					+ " ALPUESTO_PUESTO_ID = ?,"
					+ " CATEGORIA_ID = TO_NUMBER(?, '999'),"
					+ " CATEGORIA_NOMBRE = ?,"
					+ " ALPUESTO_FECHA_INICIAL = TO_DATE(?, 'DD/MM/YYYY'),"
					+ " ALPUESTO_FECHA_FINAL = TO_DATE(?, 'DD/MM/YYYY'),"
					+ " ALPUESTO_TIPO = ?,"
					+ " TOTAL_BECA_ADIC = TO_NUMBER(?, '99999999.99'),"
					+ " VALOR = ?"
					+ " WHERE MATRICULA = ?"
					+ " AND ACUERDO_FOLIO = TO_NUMBER(?,'999')";
			
			Object[] parametros = new Object[] {
				afeAcuerdos.getCargaId(),afeAcuerdos.getBloque(),afeAcuerdos.getTipoId(),afeAcuerdos.getTipoNombre(),afeAcuerdos.getTipoCuenta(),
				afeAcuerdos.getTipoImporte(),afeAcuerdos.getTipoAcuerdo(),afeAcuerdos.getAcuerdoFecha(),afeAcuerdos.getAcuerdoImpMatricula(),afeAcuerdos.getAcuerdoImpEnsenanza(),
				afeAcuerdos.getAcuerdoImpInternado(),afeAcuerdos.getAcuerdoVigencia(),afeAcuerdos.getAcuerdoPromesa(),afeAcuerdos.getAcuerdoHoras(),
				afeAcuerdos.getAcuerdoEjercicioId(),afeAcuerdos.getAcuerdoCcostoId(),afeAcuerdos.getAlpuestoPuestoId(),afeAcuerdos.getCategoriaId(),afeAcuerdos.getCategoriaNombre(),
				afeAcuerdos.getAlpuestoFechaInical(),afeAcuerdos.getAlpuestoFechaFinal(),afeAcuerdos.getAlpuestoTipo(),afeAcuerdos.getTotalBecaAdic(),afeAcuerdos.getValor(),
				afeAcuerdos.getId(),afeAcuerdos.getMatricula(),afeAcuerdos.getAcuerdoFolio()
			};
			
			if (enocJdbc.update(comando,parametros) >= 1) {
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.spring.afe.FesCcAfeAcuerdosDao|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean updatePuestoReg(FesCcAfeAcuerdos afeAcuerdos){
		boolean ok = false;
		
		try{
			String comando = "UPDATE MATEO.FES_CC_AFE_ACUERDOS "+ 
				"SET "+
				"CARGA_ID 				= ?, "+
				"BLOQUE 				= TO_NUMBER(?, '99'), "+
				"TIPO_ID 				= TO_NUMBER(?, '99'), "+
				"TIPO_NOMBRE 			= ?, "+
				"TIPO_CUENTA 			= ?, "+
				"TIPO_IMPORTE 			= TO_NUMBER(?, '9999999999.99'), "+
				"TIPO_ACUERDO 			= ?, "+
				"ACUERDO_FECHA 			= TO_DATE(?, 'DD/MM/YYYY'), "+
				"ACUERDO_IMP_MATRICULA 	= ?, "+
				"ACUERDO_IMP_ENSENANZA 	= ?, "+
				"ACUERDO_IMP_INTERNADO 	= ?, "+
				"ACUERDO_VIGENCIA 		= TO_DATE(?, 'DD/MM/YYYY'), "+
				"ACUERDO_PROMESA 		= TO_NUMBER(?, '9999999999.99'), "+
				"ACUERDO_HORAS 			= TO_NUMBER(?, '9999'), "+
				"ACUERDO_EJERCICIO_ID 	= ?, "+
				"ACUERDO_CCOSTO_ID 		= ?, "+				
				"CATEGORIA_ID 			= TO_NUMBER(?, '999'), "+
				"CATEGORIA_NOMBRE 		= ?, "+
				"ALPUESTO_FECHA_INICIAL = TO_DATE(?, 'DD/MM/YYYY'), "+
				"ALPUESTO_FECHA_FINAL 	= TO_DATE(?, 'DD/MM/YYYY'), "+
				"ALPUESTO_TIPO 			= ?, "+
				"TOTAL_BECA_ADIC 		= TO_NUMBER(?, '99999999.99'), "+
				"VALOR 					= ? "+
				"WHERE MATRICULA = ? AND ACUERDO_FOLIO = TO_NUMBER(?,'999') AND ALPUESTO_PUESTO_ID = ?";
			
			Object[] parametros = new Object[] {
				afeAcuerdos.getCargaId(),afeAcuerdos.getBloque(),afeAcuerdos.getTipoId(),afeAcuerdos.getTipoNombre(),afeAcuerdos.getTipoCuenta(),
				afeAcuerdos.getTipoImporte(),afeAcuerdos.getTipoAcuerdo(),afeAcuerdos.getAcuerdoFecha(),afeAcuerdos.getAcuerdoImpMatricula(),afeAcuerdos.getAcuerdoImpEnsenanza(),
				afeAcuerdos.getAcuerdoImpInternado(),afeAcuerdos.getAcuerdoVigencia(),afeAcuerdos.getAcuerdoPromesa(),afeAcuerdos.getAcuerdoHoras(),
				afeAcuerdos.getAcuerdoEjercicioId(),afeAcuerdos.getAcuerdoCcostoId(),afeAcuerdos.getCategoriaId(),afeAcuerdos.getCategoriaNombre(),
				afeAcuerdos.getAlpuestoFechaInical(),afeAcuerdos.getAlpuestoFechaFinal(),afeAcuerdos.getAlpuestoTipo(),afeAcuerdos.getTotalBecaAdic(),afeAcuerdos.getValor(),
				afeAcuerdos.getMatricula(),afeAcuerdos.getAcuerdoFolio(),afeAcuerdos.getAlpuestoPuestoId()
			};
			
			if (enocJdbc.update(comando,parametros) >= 1) {
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.spring.afe.FesCcAfeAcuerdosDao|updatePuestoReg|:"+ex);	
		}
		
		return ok;
	}
	
	public boolean updateCcostoCategoria( FesCcAfeAcuerdos afeAcuerdos ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE MATEO.FES_CC_AFE_ACUERDOS"
					+ " SET ACUERDO_CCOSTO_ID = ?,"
					+ " CATEGORIA_ID = TO_NUMBER(?, '999'),"
					+ " CATEGORIA_NOMBRE = ?"
					+ " WHERE MATRICULA = ?"
					+ " AND ALPUESTO_PUESTO_ID = ?"
					+ " AND ACUERDO_FOLIO = TO_NUMBER(?,'999')";
					
			Object[] parametros = new Object[] {
				afeAcuerdos.getAcuerdoCcostoId(),afeAcuerdos.getCategoriaId(),afeAcuerdos.getCategoriaNombre(),afeAcuerdos.getMatricula(),
				afeAcuerdos.getAlpuestoPuestoId(),afeAcuerdos.getAcuerdoFolio()
			};
			
			if (enocJdbc.update(comando,parametros) >= 1) {
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.spring.afe.FesCcAfeAcuerdosDao|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean updateHoras(String horas, String codigoPersonal, String puestoId) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE MATEO.FES_CC_AFE_ACUERDOS" 
				+ " SET ACUERDO_HORAS = ? "
				+ " WHERE MATRICULA = ? AND ALPUESTO_PUESTO_ID = ? ";			
			if (enocJdbc.update(comando, horas, codigoPersonal, puestoId) >= 1) {
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.spring.afe.FesCcAfeAcuerdosDao|updateHoras|:"+ex);		
		}		
		return ok;
	}
	
	public boolean existeReg( String matricula, String acuerdoFolio, String alpuestoPuestoId ) {
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM MATEO.FES_CC_AFE_ACUERDOS "
					+ " WHERE MATRICULA = ?"
					+ " AND ACUERDO_FOLIO = TO_NUMBER(?,'999')"
					+ " AND ALPUESTO_PUESTO_ID = ?";			
			Object[] parametros = new Object[] {
				matricula,acuerdoFolio,alpuestoPuestoId
			};			
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.spring.afe.FesCcAfeAcuerdosDao|existeReg|:"+ex);
		}		
		return ok;
	}
	
	public boolean tieneBeca( String matricula, String cargaId, String bloqueId, String planId ){
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM MATEO.FES_CC_AFE_ACUERDOS "
					+ " WHERE MATRICULA = ? AND CARGA_ID = ? AND BLOQUE = TO_NUMBER(?,'99') "
					+ " AND ALPUESTO_PUESTO_ID IN (SELECT PUESTO_ID FROM BEC_PUESTO_ALUMNO WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?)";
			Object[] parametros = new Object[] {matricula, cargaId, bloqueId, matricula, planId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.spring.afe.FesCcAfeAcuerdosDao|existeReg|:"+ex);
		}		
		return ok;
	}
	
	public String getPuesto( String matricula, String cargaId, String bloqueId ){
		String puesto = "0";		
		try{
			String comando = "SELECT COUNT(*) FROM MATEO.FES_CC_AFE_ACUERDOS WHERE MATRICULA = ? AND CARGA_ID = ? AND BLOQUE = TO_NUMBER(?,'99') AND TIPO_ID IN (1,3) AND ROWNUM = 1";
			Object[] parametros = new Object[] {matricula, cargaId, bloqueId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				comando = "SELECT ALPUESTO_PUESTO_ID FROM MATEO.FES_CC_AFE_ACUERDOS WHERE MATRICULA = ? AND CARGA_ID = ? AND BLOQUE = TO_NUMBER(?,'99') AND TIPO_ID IN (1,3) AND ROWNUM = 1";
				puesto 	= enocJdbc.queryForObject(comando,String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.spring.afe.FesCcAfeAcuerdosDao|getPuesto|:"+ex);
		}		
		return puesto;
	}
	
	public boolean existePuestoReg( String matricula, String acuerdoFolio, String alpuestoPuestoId ) {
		boolean ok = false;
		
		try{
			String comando = "SELECT CONUT(*) FROM MATEO.FES_CC_AFE_ACUERDOS"
					+ " WHERE MATRICULA = ?"
					+ " AND ACUERDO_FOLIO = TO_NUMBER(?,'999')"
					+ " AND ALPUESTO_PUESTO_ID = ?";
			
			Object[] parametros = new Object[] {
				matricula,acuerdoFolio,alpuestoPuestoId
			};
			
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.spring.afe.FesCcAfeAcuerdosDao|existePuestoReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg() {
		String maximo = "1";		
		try{
			String comando = "SELECT COALESCE(MAX(ID)+1,1) AS MAXIMO FROM MATEO.FES_CC_AFE_ACUERDOS";			
			maximo = enocJdbc.queryForObject(comando, String.class);			
		}catch(Exception ex){
			System.out.println("Error - aca.spring.afe.FesCcAfeAcuerdosDao|existeReg|:"+ex);
		}		
		return maximo;
	}
	
	public String getBecaAdicional(String codigoPersonal, String puestoId, String ejercicioId, String tipos) {
		String beca = "0";		
		try{
			String comando = "SELECT COALESCE(SUM(TOTAL_BECA_ADIC),0) AS TOTAL"
					+ " FROM MATEO.FES_CC_AFE_ACUERDOS"
					+ " WHERE ACUERDO_EJERCICIO_ID = ?"
					+ " AND MATRICULA = ?"
					+ " AND ALPUESTO_PUESTO_ID = ?"
					+ " AND TIPO_ID NOT IN (SELECT TIPO FROM ENOC.BEC_TIPO WHERE ACUERDO IN ("+tipos+"))";
			
			beca = enocJdbc.queryForObject(comando, String.class, ejercicioId, codigoPersonal, puestoId );
			
		}catch(Exception ex){
			System.out.println("Error - aca.spring.afe.FesCcAfeAcuerdosDao|getBecaAdicional|:"+ex);
		}
		
		return beca;
	}
	
	public String getTablaFin(String cargaId) {
		String tabla = "0";		
		try{
			String comando = "SELECT COUNT(*) FROM NOE.FES_TFINANCIERA_ENC WHERE CARGA_ID = ?";			
			Object[] parametros = new Object[] {cargaId };
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				comando = "SELECT TFINANCIERA_ID FROM NOE.FES_TFINANCIERA_ENC WHERE CARGA_ID = ?";
				tabla = enocJdbc.queryForObject(comando, String.class, parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.spring.afe.FesCcAfeAcuerdosDao|getTablaFin|:"+ex);
		}
		
		return tabla;
	}
	
	public float getCostoCredito(String tablaFin, String clasFin) {
		float costo	= 0;		
		try{			
			String comando = "SELECT COUNT(*) FROM NOE.FES_TFINANCIERA_CLAS WHERE TFINANCIERA_ID = TO_NUMBER(?,'9999') AND ACFE = ?";			
			Object[] parametros = new Object[] { tablaFin,clasFin };
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				comando = "SELECT CCREDITO FROM NOE.FES_TFINANCIERA_CLAS WHERE TFINANCIERA_ID = TO_NUMBER(?,'9999') AND ACFE = ?";
				costo = enocJdbc.queryForObject(comando, Float.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.spring.afe.FesCcAfeAcuerdosDao|getCostoCredito|:"+ex);
		}		
		return costo;
	}
	
	public float getPorcentaje( String tablaFin, String carreraId, String modalidadId) {
		float porcentaje = 0;		
		try{			
			String comando = "SELECT COUNT(*) FROM NOE.FES_TFINANCIERA_DET WHERE TFINANCIERA_ID = TO_NUMBER(?,'9999') AND CARRERA_ID = ? AND MODALIDAD_ID = TO_NUMBER(?,'99')";			
			Object[] parametros = new Object[] { tablaFin,carreraId,modalidadId };	
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				comando = "SELECT PCCREDITO FROM NOE.FES_TFINANCIERA_DET WHERE TFINANCIERA_ID = TO_NUMBER(?,'9999') AND CARRERA_ID = ? AND MODALIDAD_ID = TO_NUMBER(?,'99')";
				porcentaje = enocJdbc.queryForObject(comando, Float.class, parametros);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.spring.afe.FesCcAfeAcuerdosDao|getPorcentaje|:"+ex);
		}	
		return porcentaje;
	}
	
	public String getCargaDelPuesto( String codigoPersonal, String ejercicioId, String puestoId, String tipos) {
		String carga = "0";		
		try{			
			String comando = "SELECT COUNT(*) FROM MATEO.FES_CC_AFE_ACUERDOS"
					+ " WHERE ACUERDO_EJERCICIO_ID = ?"
					+ " AND MATRICULA = ?"
					+ " AND ALPUESTO_PUESTO_ID = ?"
					+ " AND TIPO_ACUERDO IN ("+tipos+")";
			Object[] parametros = new Object[] { ejercicioId,codigoPersonal,puestoId };
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				comando = "SELECT CARGA_ID FROM MATEO.FES_CC_AFE_ACUERDOS"
						+ " WHERE ACUERDO_EJERCICIO_ID = ?"
						+ " AND MATRICULA = ?"
						+ " AND ALPUESTO_PUESTO_ID = ?"
						+ " AND TIPO_ACUERDO IN ("+tipos+")";
				carga = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.spring.afe.FesCcAfeAcuerdosDao|getCargaDelPuesto|:"+ex);
		}
		
		return carga;
	}
	
	public List<FesCcAfeAcuerdos> getListAll( String orden ) {
		List<FesCcAfeAcuerdos> list = new ArrayList<FesCcAfeAcuerdos>();		
		try{			
			String comando = "SELECT * FROM MATEO.FES_CC_AFE_ACUERDOS " +orden;			
			list = enocJdbc.query(comando, new FesCcAfeAcuerdosMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.spring.afe.FesCcAfeAcuerdosDao|getListAll|:"+ex);
		}		
		return list;
	}
	
	public List<FesCcAfeAcuerdos> getListPuestoId( String idEjercicio, String puestoId, String orden ) {
		List<FesCcAfeAcuerdos> list 	= new ArrayList<FesCcAfeAcuerdos>();		
		try{			
			String comando = "SELECT * FROM MATEO.FES_CC_AFE_ACUERDOS"
					+ " WHERE ALPUESTO_PUESTO_ID = ?"
					+ " AND ACUERDO_EJERCICIO_ID = ? " +orden;			
			list = enocJdbc.query(comando, new FesCcAfeAcuerdosMapper(), puestoId, idEjercicio);		
		}catch(Exception ex){
			System.out.println("Error - aca.spring.afe.FesCcAfeAcuerdosDao|getListPuestoId|:"+ex);
		}
		
		return list;
	}
	
	public List<FesCcAfeAcuerdos> getAcuerdosAlumnoPuestoMateo( String idEjercicio, String codigoPersonal, String puestoId, String orden) {
		List<FesCcAfeAcuerdos> lis = new ArrayList<FesCcAfeAcuerdos>();
		
		try{
			String comando = "SELECT * FROM MATEO.FES_CC_AFE_ACUERDOS " +
					" WHERE ACUERDO_EJERCICIO_ID = ?" +
					" AND MATRICULA = ? " +
					" AND ALPUESTO_PUESTO_ID = ? "+orden;
			
			lis = enocJdbc.query(comando, new FesCcAfeAcuerdosMapper(), idEjercicio, codigoPersonal, puestoId);		
			
		}catch(Exception ex){
			System.out.println("Error - aca.spring.afe.FesCcAfeAcuerdosDao|getAcuerdosAlumnoPuestoMateo|:"+ex);
		}
		
		return lis;
	}
	
	public HashMap<String,String> mapBeca( String ejercicioId, String fecha) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();
				
		try{
			String comando = "SELECT MATRICULA||ALPUESTO_PUESTO_ID AS LLAVE, "+
					" SUM(CASE COALESCE(TOTAL_BECA_ADIC,0) WHEN 0 THEN ACUERDO_IMP_MATRICULA+ACUERDO_IMP_ENSENANZA+ACUERDO_IMP_INTERNADO ELSE TOTAL_BECA_ADIC END) AS VALOR"+
					" FROM MATEO.FES_CC_AFE_ACUERDOS "+
					" WHERE ACUERDO_EJERCICIO_ID = ? "+
					" AND MATRICULA IN (SELECT CODIGO_PERSONAL FROM ENOC.BEC_PUESTO_ALUMNO WHERE TO_DATE(?,'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN)"+
					" GROUP BY MATRICULA, ALPUESTO_PUESTO_ID ";
			
			lista = enocJdbc.query(comando, new aca.MapaMapper(),ejercicioId, fecha);
			
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.spring.afe.FesCcAfeAcuerdosDao|mapBeca|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapBecaSinFecha( String ejercicioId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();
			
		try{
			String comando = "SELECT MATRICULA||ALPUESTO_PUESTO_ID AS LLAVE, "+
					" SUM(CASE COALESCE(TOTAL_BECA_ADIC,0) WHEN 0 THEN ACUERDO_IMP_MATRICULA+ACUERDO_IMP_ENSENANZA+ACUERDO_IMP_INTERNADO ELSE TOTAL_BECA_ADIC END) AS VALOR"+
					" FROM MATEO.FES_CC_AFE_ACUERDOS "+
					" WHERE ACUERDO_EJERCICIO_ID = ? "+
					" GROUP BY MATRICULA, ALPUESTO_PUESTO_ID";
			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), ejercicioId);
			
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.spring.afe.FesCcAfeAcuerdosDao|mapBecaSinFecha|:"+ex);
		}
			
		return mapa;
	}
	
	// Map de consulta total de beca por alumno
	public HashMap<String,String> mapBecaBasicaEnPuesto( String ejercicioId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();
					
		try{
			String comando = " SELECT MATRICULA||ALPUESTO_PUESTO_ID AS LLAVE,"
					+ " SUM(CASE COALESCE(TOTAL_BECA_ADIC,0) WHEN 0 THEN ACUERDO_IMP_MATRICULA+ACUERDO_IMP_ENSENANZA+ACUERDO_IMP_INTERNADO ELSE TOTAL_BECA_ADIC END) AS VALOR"
					+ " FROM MATEO.FES_CC_AFE_ACUERDOS"
					+ " WHERE ACUERDO_EJERCICIO_ID = ?"
					+ " AND TIPO_ID IN (SELECT TIPO FROM ENOC.BEC_TIPO WHERE ID_EJERCICIO = ?' AND ACUERDO IN ('B','I'))"
					+ " GROUP BY MATRICULA, ALPUESTO_PUESTO_ID";
			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), ejercicioId, ejercicioId);
			
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.spring.afe.FesCcAfeAcuerdosDao|mapBecaBasicaEnPuesto|:"+ex);
		}
			
		return mapa;
	}
	
	public HashMap<String,String> mapBecaAdicionalEnPuesto( String ejercicioId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();
					
		try{
			String comando = " SELECT MATRICULA||ALPUESTO_PUESTO_ID AS LLAVE,"
					+ " SUM(CASE COALESCE(TOTAL_BECA_ADIC,0) WHEN 0 THEN ACUERDO_IMP_MATRICULA+ACUERDO_IMP_ENSENANZA+ACUERDO_IMP_INTERNADO ELSE TOTAL_BECA_ADIC END) AS VALOR"
					+ " FROM MATEO.FES_CC_AFE_ACUERDOS"
					+ " WHERE ACUERDO_EJERCICIO_ID = ?"
					+ " AND TIPO_ID IN (SELECT TIPO FROM ENOC.BEC_TIPO WHERE ID_EJERCICIO = ? AND ACUERDO NOT IN ('B','I'))"
					+ " GROUP BY MATRICULA, ALPUESTO_PUESTO_ID";
			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), ejercicioId, ejercicioId);
			
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.spring.afe.FesCcAfeAcuerdosDao|mapBecaAdicionalEnPuesto|:"+ex);
		}
			
		return mapa;
	}
		
	
	public HashMap<String,String> mapBecaAlumPorTipo( String ejercicioId, String fecha) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();
				
		try{
			String comando = "SELECT MATRICULA||ALPUESTO_PUESTO_ID||TIPO_ID AS LLAVE, "+
					" (CASE COALESCE(TOTAL_BECA_ADIC,0) WHEN 0 THEN ACUERDO_IMP_MATRICULA+ACUERDO_IMP_ENSENANZA+ACUERDO_IMP_INTERNADO ELSE TOTAL_BECA_ADIC END) AS VALOR"+
					" FROM MATEO.FES_CC_AFE_ACUERDOS "+
					" WHERE ACUERDO_EJERCICIO_ID = ? "+
					" AND MATRICULA||ALPUESTO_PUESTO_ID||ACUERDO_FOLIO IN"+
					" (SELECT CODIGO_PERSONAL||PUESTO_ID||FOLIO FROM ENOC.BEC_ACUERDO WHERE CODIGO_PERSONAL||PUESTO_ID IN"+ 
					"	(SELECT CODIGO_PERSONAL||PUESTO_ID FROM ENOC.BEC_PUESTO_ALUMNO WHERE TO_DATE(?,'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN)"+
					" )";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), ejercicioId, fecha);			
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.spring.afe.FesCcAfeAcuerdosDao|mapBecaAlumPorTipo|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapBecaAcuerdoTodos( String ejercicioId, String fecha) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();
					
		try{
			String comando = "SELECT MATRICULA||TIPO_ID AS LLAVE, SUM(ACUERDO_IMP_MATRICULA+ACUERDO_IMP_ENSENANZA+ACUERDO_IMP_INTERNADO) AS VALOR"+
					" FROM MATEO.FES_CC_AFE_ACUERDOS "+
					" WHERE ACUERDO_EJERCICIO_ID = ?"+
					" AND MATRICULA IN (SELECT CODIGO_PERSONAL FROM ENOC.BEC_PUESTO_ALUMNO WHERE TO_DATE(?,'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN)"+
					" GROUP BY MATRICULA, TIPO_ID";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), ejercicioId, fecha);
			
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
				
		}catch(Exception ex){
			System.out.println("Error - aca.spring.afe.FesCcAfeAcuerdosDao|mapBecaAcuerdoTodos|:"+ex);
		}
		
		return mapa;
	}
	
	// Map de consulta Beca de Acuerdo 
	public HashMap<String,String> mapBecaAcuerdo( String ejercicioId, String fecha, String tipo) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();
				
		try{
			String comando = "SELECT MATRICULA AS LLAVE, SUM(ACUERDO_IMP_MATRICULA+ACUERDO_IMP_ENSENANZA+ACUERDO_IMP_INTERNADO) AS VALOR"+
					" FROM MATEO.FES_CC_AFE_ACUERDOS "+
					" WHERE ACUERDO_EJERCICIO_ID = ? "+
					" AND MATRICULA IN (SELECT CODIGO_PERSONAL FROM ENOC.BEC_PUESTO_ALUMNO WHERE TO_DATE(?,'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN)"+
					" AND TIPO_ID = ?"+
					" GROUP BY MATRICULA";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), ejercicioId, fecha, tipo);			
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.spring.afe.FesCcAfeAcuerdosDao|mapBecaAcuerdo|:"+ex);
		}
		
		return mapa;
	}
	
	// Map de consulta el diezmo del alumno
	public HashMap<String,String> mapBecaDiezmo( String ejercicioId, String fecha) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();
				
		try{
			String comando = "SELECT MATRICULA||ALPUESTO_PUESTO_ID AS LLAVE," +
					" SUM(CASE COALESCE(TOTAL_BECA_ADIC,0) WHEN 0 THEN (ACUERDO_IMP_MATRICULA+ACUERDO_IMP_ENSENANZA+ACUERDO_IMP_INTERNADO)*.10 ELSE TOTAL_BECA_ADIC*0.10 END) AS VALOR" +
					" FROM MATEO.FES_CC_AFE_ACUERDOS " +
					" WHERE ACUERDO_EJERCICIO_ID = ?" +
					" AND MATRICULA IN (SELECT CODIGO_PERSONAL FROM ENOC.BEC_PUESTO_ALUMNO WHERE TO_DATE(?,'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN)" +
					" AND TIPO_ID IN (SELECT TIPO FROM ENOC.BEC_TIPO WHERE DIEZMO = 'S')" +
					" GROUP BY MATRICULA,ALPUESTO_PUESTO_ID ";
			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), ejercicioId, fecha);
			
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.spring.afe.FesCcAfeAcuerdosDao|mapBecaDiezmo|:"+ex);
		}
		
		return mapa;
	}
		
	public HashMap<String,String> mapBecaDiezmoSinFecha( String ejercicioId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();
				
		try{
			String comando = "SELECT MATRICULA||ALPUESTO_PUESTO_ID AS LLAVE," +
					" SUM(CASE COALESCE(TOTAL_BECA_ADIC,0) WHEN 0 THEN (ACUERDO_IMP_MATRICULA+ACUERDO_IMP_ENSENANZA+ACUERDO_IMP_INTERNADO)*.10 ELSE TOTAL_BECA_ADIC*0.10 END) AS VALOR" +
					" FROM MATEO.FES_CC_AFE_ACUERDOS " +
					" WHERE ACUERDO_EJERCICIO_ID = ?" +
					" AND TIPO_ID IN (SELECT TIPO FROM ENOC.BEC_TIPO WHERE DIEZMO = 'S')" +
					" GROUP BY MATRICULA,ALPUESTO_PUESTO_ID ";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), ejercicioId);			
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.spring.afe.FesCcAfeAcuerdosDao|mapBecaDiezmoSinFecha|:"+ex);
		}
		
		return mapa;
	}
		
	public HashMap<String,String> mapBasicas( String ejercicioId, String fecha) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();
				
		try{
			String comando = "SELECT MATRICULA||ALPUESTO_PUESTO_ID AS LLAVE," +
					" SUM(ACUERDO_IMP_ENSENANZA) AS VALOR" +
					" FROM MATEO.FES_CC_AFE_ACUERDOS " +
					" WHERE ACUERDO_EJERCICIO_ID = ?" +
					" AND MATRICULA IN (SELECT CODIGO_PERSONAL FROM ENOC.BEC_PUESTO_ALUMNO WHERE TO_DATE(?,'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN)" +
					" AND TIPO_ID IN (SELECT TIPO FROM ENOC.BEC_TIPO WHERE ACUERDO IN ('B','I'))" +
					" GROUP BY MATRICULA,ALPUESTO_PUESTO_ID ";
			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), ejercicioId, fecha);
			
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.spring.afe.FesCcAfeAcuerdosDao|mapBasicas|:"+ex);
		}
		
		return mapa;
	}
		
	public HashMap<String,FesCcAfeAcuerdos> mapaAcuerdosEnEjercicio( String ejercicioId) {
		HashMap<String, FesCcAfeAcuerdos> mapa = new HashMap<String, FesCcAfeAcuerdos>();
		List<FesCcAfeAcuerdos> lista 		 = new ArrayList<FesCcAfeAcuerdos>();				
		try{
			String comando = "SELECT MATRICULA, CARGA_ID, BLOQUE, TIPO_ID,	TIPO_NOMBRE,TIPO_CUENTA,TIPO_IMPORTE,TIPO_ACUERDO,"
					+ " ACUERDO_FECHA, ACUERDO_IMP_MATRICULA,  ACUERDO_IMP_ENSENANZA,ACUERDO_IMP_INTERNADO, ACUERDO_VIGENCIA, ACUERDO_FOLIO,"
					+ " ACUERDO_PROMESA, ACUERDO_HORAS, ACUERDO_EJERCICIO_ID, ACUERDO_CCOSTO_ID, ALPUESTO_PUESTO_ID, CATEGORIA_ID, CATEGORIA_NOMBRE,"
					+ " ALPUESTO_FECHA_INICIAL, ALPUESTO_FECHA_FINAL, ALPUESTO_TIPO, COALESCE(TOTAL_BECA_ADIC,0) AS TOTAL_BECA_ADIC, VALOR, ID"
					+ " FROM MATEO.FES_CC_AFE_ACUERDOS"
					+ " WHERE ACUERDO_EJERCICIO_ID = ?";		
			lista = enocJdbc.query(comando, new FesCcAfeAcuerdosMapper(), ejercicioId);			
			for(FesCcAfeAcuerdos acuerdo : lista){
				mapa.put(acuerdo.getMatricula()+acuerdo.getAcuerdoFolio()+acuerdo.getAlpuestoPuestoId(), acuerdo);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.spring.afe.FesCcAfeAcuerdosDao|mapAdicional|:"+ex);
		}		
		return mapa;
	}		
	
	public HashMap<String,String> mapAdicional( String ejercicioId, String fecha) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();
				
		try{
			String comando = "SELECT MATRICULA||ALPUESTO_PUESTO_ID AS LLAVE," +
					" SUM(ACUERDO_IMP_ENSENANZA) AS VALOR" +
					" FROM MATEO.FES_CC_AFE_ACUERDOS " +
					" WHERE ACUERDO_EJERCICIO_ID = ?" +
					" AND MATRICULA IN (SELECT CODIGO_PERSONAL FROM ENOC.BEC_PUESTO_ALUMNO WHERE TO_DATE(?,'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN)" +
					" AND TIPO_ID IN (SELECT TIPO FROM ENOC.BEC_TIPO WHERE ACUERDO IN ('A','E'))" +
					" GROUP BY MATRICULA,ALPUESTO_PUESTO_ID ";
			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), ejercicioId, fecha);
			
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.spring.afe.FesCcAfeAcuerdosDao|mapAdicional|:"+ex);
		}
		
		return mapa;
	}
		
	public String totalBecaAlumno( String codigoPersonal, String carga, String tipoBeca) {
		String total = "0";				
		try{
			String comando = " SELECT COALESCE(SUM(ACUERDO_IMP_MATRICULA+ACUERDO_IMP_ENSENANZA+ACUERDO_IMP_INTERNADO),0) AS TOTAL "
					+ " FROM MATEO.FES_CC_AFE_ACUERDOS "
					+ " WHERE MATRICULA = ?"
					+ " AND CARGA_ID = ?"
					+ " AND TIPO_ACUERDO IN ("+tipoBeca+")"
					+ " AND ALPUESTO_PUESTO_ID IN (SELECT PUESTO_ID FROM BEC_PUESTO_ALUMNO WHERE CODIGO_PERSONAL = ?)";		
			total = enocJdbc.queryForObject(comando, String.class, codigoPersonal, carga, codigoPersonal);			
		}catch(Exception ex){
			System.out.println("Error - aca.spring.afe.FesCcAfeAcuerdosDao|totalBecaAlumno|:"+ex);
		}
		return total;
	}
		
}
