package aca.afe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class FesCcAfeAcuerdosUtil {
	
	public FesCcAfeAcuerdos mapeaRegId(Connection con) throws SQLException{
		FesCcAfeAcuerdos afeAcuerdos = new FesCcAfeAcuerdos();
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement(" SELECT MATRICULA, CARGA_ID, BLOQUE, TIPO_ID,	TIPO_NOMBRE,TIPO_CUENTA,TIPO_IMPORTE,TIPO_ACUERDO,"
					+ " ACUERDO_FECHA, ACUERDO_IMP_MATRICULA,	ACUERDO_IMP_ENSENANZA,ACUERDO_IMP_INTERNADO, ACUERDO_VIGENCIA, ACUERDO_FOLIO,"
					+ " ACUERDO_PROMESA, ACUERDO_HORAS, ACUERDO_EJERCICIO_ID, ACUERDO_CCOSTO_ID, ALPUESTO_PUESTO_ID, CATEGORIA_ID, CATEGORIA_NOMBRE,"
					+ " ALPUESTO_FECHA_INICIAL, ALPUESTO_FECHA_FINAL, ALPUESTO_TIPO, COALESCE(TOTAL_BECA_ADIC,0) AS TOTAL_BECA_ADIC, VALOR, ID"
					+ " FROM MATEO.FES_CC_AFE_ACUERDOS"
					+ " WHERE MATRICULA = ?"
					+ " AND ACUERDO_FOLIO = TO_NUMBER(?,'999')"
					+ " AND ALPUESTO_PUESTO_ID = ?");
			ps.setString(1, afeAcuerdos.getMatricula());
			ps.setString(2, afeAcuerdos.getAcuerdoFolio());
			ps.setString(3, afeAcuerdos.getAlpuestoPuestoId());
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				afeAcuerdos.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.afe.FesCcAfeAcuerdosUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return afeAcuerdos;
	}
	
	public FesCcAfeAcuerdos mapeaRegPuestoId(Connection con, String matricula, String folio, String puesto) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;	
		FesCcAfeAcuerdos afeAcuerdos = new FesCcAfeAcuerdos();

		try{
			ps = con.prepareStatement("SELECT MATRICULA, CARGA_ID, BLOQUE, TIPO_ID,	TIPO_NOMBRE,TIPO_CUENTA,TIPO_IMPORTE,TIPO_ACUERDO,"
					+ " ACUERDO_FECHA, ACUERDO_IMP_MATRICULA,	ACUERDO_IMP_ENSENANZA,ACUERDO_IMP_INTERNADO, ACUERDO_VIGENCIA, ACUERDO_FOLIO,"
					+ " ACUERDO_PROMESA, ACUERDO_HORAS, ACUERDO_EJERCICIO_ID, ACUERDO_CCOSTO_ID, ALPUESTO_PUESTO_ID, CATEGORIA_ID, CATEGORIA_NOMBRE,"
					+ " ALPUESTO_FECHA_INICIAL, ALPUESTO_FECHA_FINAL, ALPUESTO_TIPO, COALESCE(TOTAL_BECA_ADIC,0) AS TOTAL_BECA_ADIC, VALOR, ID"
					+ " FROM MATEO.FES_CC_AFE_ACUERDOS"
					+ " WHERE MATRICULA = ?"
					+ " AND ACUERDO_FOLIO = TO_NUMBER(?,'999')"
					+ " AND ALPUESTO_PUESTO_ID = ?");
			ps.setString(1, matricula);
			ps.setString(2, folio);
			ps.setString(3, puesto);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				afeAcuerdos.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.afe.FesCcAfeAcuerdosUtil|mapeaRegPuestoId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return afeAcuerdos;
	}
	
	public boolean insertReg(Connection conn, FesCcAfeAcuerdos afeAcuerdos) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO MATEO.FES_CC_AFE_ACUERDOS "+ 
				" (MATRICULA, CARGA_ID, BLOQUE, TIPO_ID, TIPO_NOMBRE," +
					" TIPO_CUENTA, TIPO_IMPORTE, TIPO_ACUERDO, ACUERDO_FECHA, ACUERDO_IMP_MATRICULA," +
					" ACUERDO_IMP_ENSENANZA, ACUERDO_IMP_INTERNADO, ACUERDO_VIGENCIA, ACUERDO_FOLIO, ACUERDO_PROMESA, " +
					" ACUERDO_HORAS, ACUERDO_EJERCICIO_ID, ACUERDO_CCOSTO_ID, ALPUESTO_PUESTO_ID, CATEGORIA_ID, " +
					" CATEGORIA_NOMBRE, ALPUESTO_FECHA_INICIAL, ALPUESTO_FECHA_FINAL, ALPUESTO_TIPO, TOTAL_BECA_ADIC, VALOR, ID ) "+
				"VALUES( ?, ?, TO_NUMBER(?, '99'), TO_NUMBER(?, '99'), ?, " +
				" ?, TO_NUMBER(?, '9999999999.99'), ?, TO_DATE(?, 'DD/MM/YYYY'), TO_NUMBER(?, '9999999999.99')," +
				" TO_NUMBER(?, '9999999999.99'), TO_NUMBER(?, '9999999999.99'), TO_DATE(?, 'DD/MM/YYYY'), TO_NUMBER(?, '999'), TO_NUMBER(?, '9999999999.99')," +
				" TO_NUMBER(?, '9999'), ?, ?, ?, TO_NUMBER(?, '999')," +
				" ?, TO_DATE(?, 'DD/MM/YYYY'), TO_DATE(?, 'DD/MM/YYYY'), ?, TO_NUMBER(?, '99999999.99'), ?, TO_NUMBER( ?,'9999999') )");
					
				ps.setString(1, afeAcuerdos.getMatricula());
			    ps.setString(2, afeAcuerdos.getCargaId());
			    ps.setString(3, afeAcuerdos.getBloque());
			    ps.setString(4, afeAcuerdos.getTipoId());
			    ps.setString(5, afeAcuerdos.getTipoNombre());
			    ps.setString(6, afeAcuerdos.getTipoCuenta());
			    ps.setString(7, afeAcuerdos.getTipoImporte());
			    ps.setString(8, afeAcuerdos.getTipoAcuerdo());
			    ps.setString(9, afeAcuerdos.getAcuerdoFecha());
			    ps.setString(10, afeAcuerdos.getAcuerdoImpMatricula());
			    ps.setString(11, afeAcuerdos.getAcuerdoImpEnsenanza());
			    ps.setString(12, afeAcuerdos.getAcuerdoImpInternado());
			    ps.setString(13, afeAcuerdos.getAcuerdoVigencia());
			    ps.setString(14, afeAcuerdos.getAcuerdoFolio());
			    ps.setString(15, afeAcuerdos.getAcuerdoPromesa());
			    ps.setString(16, afeAcuerdos.getAcuerdoHoras());
			    ps.setString(17, afeAcuerdos.getAcuerdoEjercicioId());
			    ps.setString(18, afeAcuerdos.getAcuerdoCcostoId());
			    ps.setString(19, afeAcuerdos.getAlpuestoPuestoId());
			    ps.setString(20, afeAcuerdos.getCategoriaId());
			    ps.setString(21, afeAcuerdos.getCategoriaNombre());
			    ps.setString(22, afeAcuerdos.getAlpuestoFechaInical());
			    ps.setString(23, afeAcuerdos.getAlpuestoFechaFinal());
			    ps.setString(24, afeAcuerdos.getAlpuestoTipo());
			    ps.setString(25, afeAcuerdos.getTotalBecaAdic());
			    ps.setString(26, afeAcuerdos.getValor());
			    ps.setString(27, afeAcuerdos.getId());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.afe.FesCcAfeAcuerdosUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, FesCcAfeAcuerdos afeAcuerdos ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE MATEO.FES_CC_AFE_ACUERDOS"
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
					+ " AND ACUERDO_FOLIO = TO_NUMBER(?,'999')");
			
			ps.setString(1, afeAcuerdos.getCargaId());
		    ps.setString(2, afeAcuerdos.getBloque());
		    ps.setString(3, afeAcuerdos.getTipoId());
		    ps.setString(4, afeAcuerdos.getTipoNombre());
		    ps.setString(5, afeAcuerdos.getTipoCuenta());
		    ps.setString(6, afeAcuerdos.getTipoImporte());
		    ps.setString(7, afeAcuerdos.getTipoAcuerdo());
		    ps.setString(8, afeAcuerdos.getAcuerdoFecha());
		    ps.setString(9, afeAcuerdos.getAcuerdoImpMatricula());
		    ps.setString(10, afeAcuerdos.getAcuerdoImpEnsenanza());
		    ps.setString(11, afeAcuerdos.getAcuerdoImpInternado());
		    ps.setString(12, afeAcuerdos.getAcuerdoVigencia());
		    ps.setString(13, afeAcuerdos.getAcuerdoPromesa());
		    ps.setString(14, afeAcuerdos.getAcuerdoHoras());
		    ps.setString(15, afeAcuerdos.getAcuerdoEjercicioId());
		    ps.setString(16, afeAcuerdos.getAcuerdoCcostoId());
		    ps.setString(17, afeAcuerdos.getAlpuestoPuestoId());
		    ps.setString(18, afeAcuerdos.getCategoriaId());
		    ps.setString(19, afeAcuerdos.getCategoriaNombre());
		    ps.setString(20, afeAcuerdos.getAlpuestoFechaInical());
		    ps.setString(21, afeAcuerdos.getAlpuestoFechaFinal());
		    ps.setString(22, afeAcuerdos.getAlpuestoTipo());
		    ps.setString(23, afeAcuerdos.getTotalBecaAdic());
		    ps.setString(24, afeAcuerdos.getValor());
		    ps.setString(25, afeAcuerdos.getMatricula());
		    ps.setString(26, afeAcuerdos.getAcuerdoFolio());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.afe.FesCcAfeAcuerdosUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean updatePuestoReg(Connection conn, FesCcAfeAcuerdos afeAcuerdos) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE MATEO.FES_CC_AFE_ACUERDOS "+ 
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
				"WHERE MATRICULA = ? AND ACUERDO_FOLIO = TO_NUMBER(?,'999') AND ALPUESTO_PUESTO_ID = ?");
			
			ps.setString(1, afeAcuerdos.getCargaId());
			ps.setString(2, afeAcuerdos.getBloque());
			ps.setString(3, afeAcuerdos.getTipoId());
			ps.setString(4, afeAcuerdos.getTipoNombre());
			ps.setString(5, afeAcuerdos.getTipoCuenta());
			ps.setString(6, afeAcuerdos.getTipoImporte());
			ps.setString(7, afeAcuerdos.getTipoAcuerdo());
		    ps.setString(8, afeAcuerdos.getAcuerdoFecha());
			ps.setString(9, afeAcuerdos.getAcuerdoImpMatricula());
			ps.setString(10, afeAcuerdos.getAcuerdoImpEnsenanza());
			ps.setString(11, afeAcuerdos.getAcuerdoImpInternado());
			ps.setString(12, afeAcuerdos.getAcuerdoVigencia());
			ps.setString(13, afeAcuerdos.getAcuerdoPromesa());
			ps.setString(14, afeAcuerdos.getAcuerdoHoras());
			ps.setString(15, afeAcuerdos.getAcuerdoEjercicioId());
			ps.setString(16, afeAcuerdos.getAcuerdoCcostoId());
			ps.setString(17, afeAcuerdos.getCategoriaId());
			ps.setString(18, afeAcuerdos.getCategoriaNombre());
			ps.setString(19, afeAcuerdos.getAlpuestoFechaInical());
			ps.setString(20, afeAcuerdos.getAlpuestoFechaFinal());
			ps.setString(21, afeAcuerdos.getAlpuestoTipo());
			ps.setString(22, afeAcuerdos.getTotalBecaAdic());
			ps.setString(23, afeAcuerdos.getValor());
			ps.setString(24, afeAcuerdos.getMatricula());
			ps.setString(25, afeAcuerdos.getAcuerdoFolio());
			ps.setString(26, afeAcuerdos.getAlpuestoPuestoId());
			
			if (ps.executeUpdate() >= 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.afe.FesCcAfeAcuerdosUtil|updatePuestoReg|:"+ex);	
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean updateCcostoCategoria(Connection conn, FesCcAfeAcuerdos afeAcuerdos ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE MATEO.FES_CC_AFE_ACUERDOS"
					+ " SET ACUERDO_CCOSTO_ID = ?,"
					+ " CATEGORIA_ID = TO_NUMBER(?, '999'),"
					+ " CATEGORIA_NOMBRE = ?"
					+ " WHERE MATRICULA = ?"
					+ " AND ALPUESTO_PUESTO_ID = ?"
					+ " AND ACUERDO_FOLIO = TO_NUMBER(?,'999') ");
			
			//System.out.println("Datos:"+acuerdoCcostoId+":"+categoriaId+":"+categoriaNombre+":"+matricula+":"+acuerdoFolio);
		    ps.setString(1, afeAcuerdos.getAcuerdoCcostoId());
		    ps.setString(2, afeAcuerdos.getCategoriaId());
		    ps.setString(3, afeAcuerdos.getCategoriaNombre());
		    ps.setString(4, afeAcuerdos.getMatricula());
		    ps.setString(5, afeAcuerdos.getAlpuestoPuestoId());
		    ps.setString(6, afeAcuerdos.getAcuerdoFolio());
			
			if (ps.executeUpdate() == 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.afe.FesCcAfeAcuerdosUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean updateHoras(Connection conn,String horas, String codigoPersonal, String puestoId) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE MATEO.FES_CC_AFE_ACUERDOS "+ 
				"SET "+
				"ACUERDO_HORAS = '"+horas+"' "+
				"WHERE MATRICULA = '"+codigoPersonal+"' AND ALPUESTO_PUESTO_ID = '"+puestoId+"' ");
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.afe.FesCcAfeAcuerdosUtil|updateHoras|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean existeReg(Connection conn, String matricula, String acuerdoFolio, String alpuestoPuestoId ) throws SQLException{
		boolean ok 			= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM MATEO.FES_CC_AFE_ACUERDOS "
					+ " WHERE MATRICULA = ?"
					+ " AND ACUERDO_FOLIO = TO_NUMBER(?,'999')"
					+ " AND ALPUESTO_PUESTO_ID = ?"); 
			ps.setString(1,  matricula);
			ps.setString(2,  acuerdoFolio);
			ps.setString(3,  alpuestoPuestoId);
			
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.afe.FesCcAfeAcuerdosUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean existePuestoReg(Connection conn, String matricula, String acuerdoFolio, String alpuestoPuestoId ) throws SQLException{
		boolean ok 			= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM MATEO.FES_CC_AFE_ACUERDOS "
					+ " WHERE MATRICULA = ?"
					+ " AND ACUERDO_FOLIO = TO_NUMBER(?,'999')"
					+ " AND ALPUESTO_PUESTO_ID = ?"); 
			ps.setString(1,  matricula);
			ps.setString(2,  acuerdoFolio);
			ps.setString(3,  alpuestoPuestoId);
			
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.afe.FesCcAfeAcuerdosUtil|existePuestoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet rs 			= null;
		String maximo 			= "1";
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(ID)+1,1) AS MAXIMO FROM MATEO.FES_CC_AFE_ACUERDOS");
			
			rs= ps.executeQuery();		
			if(rs.next()){
				maximo = rs.getString("MAXIMO");
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.afe.FesCcAfeAcuerdosUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static String getBecaAdicional(Connection conn, String codigoPersonal, String puestoId, String ejercicioId, String tipos) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet rs 			= null;
		String beca 			= "0";
		
		try{
			// ACUERDO_IMP_ENSENANZA+ACUERDO_IMP_MATRICULA+ACUERDO_IMP_INTERNADO
			ps = conn.prepareStatement("SELECT MATRICULA, COALESCE(SUM(TOTAL_BECA_ADIC),0) AS TOTAL"
					+ " FROM MATEO.FES_CC_AFE_ACUERDOS"
					+ " WHERE ACUERDO_EJERCICIO_ID = '"+ejercicioId+"'"
					+ " AND MATRICULA = '"+codigoPersonal+"'"
					+ " AND ALPUESTO_PUESTO_ID = '"+puestoId+"'"
					+ " AND TIPO_ID NOT IN (SELECT TIPO FROM ENOC.BEC_TIPO WHERE ACUERDO IN ("+tipos+"))"
					+ " GROUP BY MATRICULA");
			
			rs= ps.executeQuery();		
			if(rs.next()){
				beca = rs.getString("TOTAL");
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.afe.FesCcAfeAcuerdosUtil|getBecaAdicional|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return beca;
	}
	
	public static String getTablaFin(Connection conn, String cargaId) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet rs 			= null;
		String tabla	 		= "0";		
		
		try{
			// Busca la tabla financiera de una carga 
			ps = conn.prepareStatement("SELECT TFINANCIERA_ID FROM NOE.FES_TFINANCIERA_ENC WHERE CARGA_ID = ?");
			ps.setString(1, cargaId);
			
			rs= ps.executeQuery();			
			if(rs.next()){
				tabla = rs.getString("TFINANCIERA_ID");
			}						
			
		}catch(Exception ex){
			System.out.println("Error - aca.afe.FesCcAfeAcuerdosUtil|getTablaFin|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return tabla;
	}
	
	
	public static float getCostoCredito(Connection conn, String tablaFin, String clasFin ) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet rs 			= null;		
		float costo				= 0;	 
		
		try{			
			
			// Buscar el costo del credito por clas fin
			ps = conn.prepareStatement("SELECT CCREDITO FROM NOE.FES_TFINANCIERA_CLAS WHERE TFINANCIERA_ID = TO_NUMBER(?,'9999') AND ACFE = ?");
			ps.setString(1, tablaFin);
			ps.setString(2, clasFin);
			
			rs= ps.executeQuery();			
			if(rs.next()){
				costo = rs.getFloat("CCREDITO");
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.afe.FesCcAfeAcuerdosUtil|getCostoCredito|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return costo;
	}
	
	public static float getPorcentaje(Connection conn, String tablaFin, String carreraId, String modalidadId) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet rs 			= null;				
		float porcentaje 		= 0;
		
		try{			
						
			// Buscar el porcentaje del costo aplicado a la carrera en la modalidad correspondiente
			ps = conn.prepareStatement("SELECT PCCREDITO FROM NOE.FES_TFINANCIERA_DET"
					+ " WHERE TFINANCIERA_ID = TO_NUMBER(?,'9999') AND CARRERA_ID = ? AND MODALIDAD_ID = TO_NUMBER(?,'99')");
			ps.setString(1, tablaFin);
			ps.setString(2, carreraId);
			ps.setString(3, modalidadId);			
			rs= ps.executeQuery();
			if(rs.next()){
				porcentaje = rs.getFloat("PCCREDITO");
			}
						
		}catch(Exception ex){
			System.out.println("Error - aca.afe.FesCcAfeAcuerdosUtil|getPorcentaje|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return porcentaje;
	}
	
	public static String getCargaDelPuesto(Connection conn, String codigoPersonal, String ejercicioId, String puestoId, String tipos) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet rs 			= null;				
		String carga 			= "0";		
		try{						
			// Buscar la carga del alumno
			ps = conn.prepareStatement("SELECT CARGA_ID FROM MATEO.FES_CC_AFE_ACUERDOS"
					+ " WHERE ACUERDO_EJERCICIO_ID = '"+ejercicioId+"'"
					+ " AND MATRICULA = '"+codigoPersonal+"'"
					+ " AND ALPUESTO_PUESTO_ID = '"+puestoId+"'"
					+ " AND TIPO_ACUERDO IN ("+tipos+")");			
			rs= ps.executeQuery();
			if(rs.next()){
				carga = rs.getString("CARGA_ID");				
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.afe.FesCcAfeAcuerdosUtil|getCargaDelPuesto|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return carga;
	}
	
	public ArrayList<FesCcAfeAcuerdos> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<FesCcAfeAcuerdos> list 	= new ArrayList<FesCcAfeAcuerdos>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	    = "";
		
		try{			
			comando = "SELECT * FROM MATEO.FES_CC_AFE_ACUERDOS " +orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				FesCcAfeAcuerdos obj = new FesCcAfeAcuerdos();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.afe.FesCcAfeAcuerdosUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
	
	public ArrayList<FesCcAfeAcuerdos> getListPuestoId(Connection conn, String idEjercicio, String puestoId, String orden ) throws SQLException{
		
		ArrayList<FesCcAfeAcuerdos> list 	= new ArrayList<FesCcAfeAcuerdos>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	    = "";
		
		try{			
			comando = "SELECT * " +
					" FROM MATEO.FES_CC_AFE_ACUERDOS " +
					" WHERE ALPUESTO_PUESTO_ID = '"+puestoId+"' " +
					" AND ACUERDO_EJERCICIO_ID = '"+idEjercicio+"' " +orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				FesCcAfeAcuerdos obj = new FesCcAfeAcuerdos();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.afe.FesCcAfeAcuerdosUtil|getListPuestoId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
	
	
	public ArrayList<FesCcAfeAcuerdos> getAcuerdosAlumnoPuestoMateo(Connection conn, String idEjercicio, String codigoPersonal, String puestoId, String orden) throws SQLException{
		
		ArrayList<FesCcAfeAcuerdos> lis 		= new ArrayList<FesCcAfeAcuerdos>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT * FROM MATEO.FES_CC_AFE_ACUERDOS " +
					" WHERE ACUERDO_EJERCICIO_ID = '"+idEjercicio+"' " +
					" AND MATRICULA = '"+codigoPersonal+"' " +
					" AND ALPUESTO_PUESTO_ID = '"+puestoId+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				FesCcAfeAcuerdos obj= new FesCcAfeAcuerdos();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.afe.FesCcAfeAcuerdosUtil|getAcuerdosAlumnoPuestoMateo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	// Map de consulta total de beca por alumno
	public static HashMap<String,String> mapBeca(Connection conn, String ejercicioId, String fecha) throws SQLException{
		
		HashMap<String,String> map				= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
				
		try{
			comando = "SELECT MATRICULA, ALPUESTO_PUESTO_ID, "+
					" SUM(CASE COALESCE(TOTAL_BECA_ADIC,0) WHEN 0 THEN ACUERDO_IMP_MATRICULA+ACUERDO_IMP_ENSENANZA+ACUERDO_IMP_INTERNADO ELSE TOTAL_BECA_ADIC END) AS TOTAL_BECA"+
					" FROM MATEO.FES_CC_AFE_ACUERDOS "+
					" WHERE ACUERDO_EJERCICIO_ID = '"+ejercicioId+"' "+
					" AND MATRICULA IN (SELECT CODIGO_PERSONAL FROM ENOC.BEC_PUESTO_ALUMNO WHERE TO_DATE('"+fecha+"','DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN)"+
					" GROUP BY MATRICULA, ALPUESTO_PUESTO_ID ";
			rs = st.executeQuery(comando);			
			while (rs.next()){				
				map.put(rs.getString("MATRICULA")+rs.getString("ALPUESTO_PUESTO_ID"), rs.getString("TOTAL_BECA"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.afe.FesCcAfeAcuerdosUtil|mapBeca|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	// Map de consulta total de beca por alumno
	public static HashMap<String,String> mapBecaSinFecha(Connection conn, String ejercicioId) throws SQLException{
			
		HashMap<String,String> map				= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
					
		try{
			comando = "SELECT MATRICULA, ALPUESTO_PUESTO_ID, "+
					" SUM(CASE COALESCE(TOTAL_BECA_ADIC,0) WHEN 0 THEN ACUERDO_IMP_MATRICULA+ACUERDO_IMP_ENSENANZA+ACUERDO_IMP_INTERNADO ELSE TOTAL_BECA_ADIC END) AS TOTAL_BECA"+
					" FROM MATEO.FES_CC_AFE_ACUERDOS "+
					" WHERE ACUERDO_EJERCICIO_ID = '"+ejercicioId+"' "+
					" GROUP BY MATRICULA, ALPUESTO_PUESTO_ID";
			rs = st.executeQuery(comando);			
			while (rs.next()){				
				map.put(rs.getString("MATRICULA")+rs.getString("ALPUESTO_PUESTO_ID"), rs.getString("TOTAL_BECA"));
			}
				
		}catch(Exception ex){
			System.out.println("Error - aca.afe.FesCcAfeAcuerdosUtil|mapBeca|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
			
		return map;
	}
	
	// Map de consulta total de beca por alumno
	public static HashMap<String,String> mapBecaBasicaEnPuesto(Connection conn, String ejercicioId) throws SQLException{
			
		HashMap<String,String> map				= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
					
		try{
			comando = " SELECT MATRICULA, ALPUESTO_PUESTO_ID,"
					+ " SUM(CASE COALESCE(TOTAL_BECA_ADIC,0) WHEN 0 THEN ACUERDO_IMP_MATRICULA+ACUERDO_IMP_ENSENANZA+ACUERDO_IMP_INTERNADO ELSE TOTAL_BECA_ADIC END) AS TOTAL_BECA"
					+ " FROM MATEO.FES_CC_AFE_ACUERDOS"
					+ " WHERE ACUERDO_EJERCICIO_ID = '"+ejercicioId+"'"
					+ " AND TIPO_ID IN (SELECT TIPO FROM ENOC.BEC_TIPO WHERE ID_EJERCICIO = '"+ejercicioId+"' AND ACUERDO IN ('B','I'))"
					+ " GROUP BY MATRICULA, ALPUESTO_PUESTO_ID";
			rs = st.executeQuery(comando);			
			while (rs.next()){				
				map.put(rs.getString("MATRICULA")+rs.getString("ALPUESTO_PUESTO_ID"), rs.getString("TOTAL_BECA"));
			}
				
		}catch(Exception ex){
			System.out.println("Error - aca.afe.FesCcAfeAcuerdosUtil|mapBecaBasicaEnPuesto|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
			
		return map;
	}
	
	// Map de consulta total de beca por alumno
	public static HashMap<String,String> mapBecaAdicionalEnPuesto(Connection conn, String ejercicioId) throws SQLException{
			
		HashMap<String,String> map				= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
					
		try{
			comando = " SELECT MATRICULA, ALPUESTO_PUESTO_ID,"
					+ " SUM(CASE COALESCE(TOTAL_BECA_ADIC,0) WHEN 0 THEN ACUERDO_IMP_MATRICULA+ACUERDO_IMP_ENSENANZA+ACUERDO_IMP_INTERNADO ELSE TOTAL_BECA_ADIC END) AS TOTAL_BECA"
					+ " FROM MATEO.FES_CC_AFE_ACUERDOS"
					+ " WHERE ACUERDO_EJERCICIO_ID = '"+ejercicioId+"'"
					+" AND TIPO_ID IN (SELECT TIPO FROM ENOC.BEC_TIPO WHERE ID_EJERCICIO = '"+ejercicioId+"' AND ACUERDO NOT IN ('B','I'))"
					+ " GROUP BY MATRICULA, ALPUESTO_PUESTO_ID";
			rs = st.executeQuery(comando);			
			while (rs.next()){				
				map.put(rs.getString("MATRICULA")+rs.getString("ALPUESTO_PUESTO_ID"), rs.getString("TOTAL_BECA"));
			}
				
		}catch(Exception ex){
			System.out.println("Error - aca.afe.FesCcAfeAcuerdosUtil|mapBecaAdicionalEnPuesto|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
			
		return map;
	}
		
	
	// Map de consulta del importe de beca por tipo de un alumno en el puesto
	public static HashMap<String,String> mapBecaAlumPorTipo(Connection conn, String ejercicioId, String fecha) throws SQLException{
		
		HashMap<String,String> map				= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
				
		try{
			comando = "SELECT MATRICULA, ALPUESTO_PUESTO_ID, TIPO_ID, "+
					" (CASE COALESCE(TOTAL_BECA_ADIC,0) WHEN 0 THEN ACUERDO_IMP_MATRICULA+ACUERDO_IMP_ENSENANZA+ACUERDO_IMP_INTERNADO ELSE TOTAL_BECA_ADIC END) AS TOTAL"+
					" FROM MATEO.FES_CC_AFE_ACUERDOS "+
					" WHERE ACUERDO_EJERCICIO_ID = '"+ejercicioId+"' "+
					" AND MATRICULA||ALPUESTO_PUESTO_ID||ACUERDO_FOLIO IN"+
					" (SELECT CODIGO_PERSONAL||PUESTO_ID||FOLIO FROM ENOC.BEC_ACUERDO WHERE CODIGO_PERSONAL||PUESTO_ID IN"+ 
					"	(SELECT CODIGO_PERSONAL||PUESTO_ID FROM ENOC.BEC_PUESTO_ALUMNO WHERE TO_DATE('"+fecha+"','DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN)"+
					" )";
			rs = st.executeQuery(comando);			
			while (rs.next()){				
				map.put(rs.getString("MATRICULA")+rs.getString("ALPUESTO_PUESTO_ID")+rs.getString("TIPO_ID"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.afe.FesCcAfeAcuerdosUtil|mapBecaAlumPorTipo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	// Map de consulta Beca de Acuerdo todos
	public static HashMap<String,String> mapBecaAcuerdoTodos(Connection conn, String ejercicioId, String fecha) throws SQLException{
			
			HashMap<String,String> map				= new HashMap<String,String>();
			Statement st 							= conn.createStatement();
			ResultSet rs		 					= null;
			String comando							= "";
					
			try{
				comando = "SELECT MATRICULA, TIPO_ID, SUM(ACUERDO_IMP_MATRICULA+ACUERDO_IMP_ENSENANZA+ACUERDO_IMP_INTERNADO) AS TOTAL_BECA"+
						" FROM MATEO.FES_CC_AFE_ACUERDOS "+
						" WHERE ACUERDO_EJERCICIO_ID = '"+ejercicioId+"' "+
						" AND MATRICULA IN (SELECT CODIGO_PERSONAL FROM ENOC.BEC_PUESTO_ALUMNO WHERE TO_DATE('"+fecha+"','DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN)"+
						" GROUP BY MATRICULA, TIPO_ID";
				rs = st.executeQuery(comando);	
				while (rs.next()){				
					map.put(rs.getString("MATRICULA")+rs.getString("TIPO_ID"), rs.getString("TOTAL_BECA"));				
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.afe.FesCcAfeAcuerdosUtil|mapBecaAcuerdo|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return map;
		}
	
	// Map de consulta Beca de Acuerdo 
	public static HashMap<String,String> mapBecaAcuerdo(Connection conn, String ejercicioId, String fecha, String tipo) throws SQLException{
		
		HashMap<String,String> map				= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
				
		try{
			comando = "SELECT MATRICULA, SUM(ACUERDO_IMP_MATRICULA+ACUERDO_IMP_ENSENANZA+ACUERDO_IMP_INTERNADO) AS TOTAL_BECA"+
					" FROM MATEO.FES_CC_AFE_ACUERDOS "+
					" WHERE ACUERDO_EJERCICIO_ID = '"+ejercicioId+"' "+
					" AND MATRICULA IN (SELECT CODIGO_PERSONAL FROM ENOC.BEC_PUESTO_ALUMNO WHERE TO_DATE('"+fecha+"','DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN)"+
					" AND TIPO_ID = '"+tipo+"'"+
					" GROUP BY MATRICULA";
			rs = st.executeQuery(comando);	
			while (rs.next()){				
				map.put(rs.getString("MATRICULA"), rs.getString("TOTAL_BECA"));				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.afe.FesCcAfeAcuerdosUtil|mapBecaAcuerdo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	// Map de consulta el diezmo del alumno
		public static HashMap<String,String> mapBecaDiezmo(Connection conn, String ejercicioId, String fecha) throws SQLException{
			
			HashMap<String,String> map				= new HashMap<String,String>();
			Statement st 							= conn.createStatement();
			ResultSet rs		 					= null;
			String comando							= "";
					
			try{
				comando = "SELECT" +
						" MATRICULA," +
						" ALPUESTO_PUESTO_ID," +
						" SUM(CASE COALESCE(TOTAL_BECA_ADIC,0) WHEN 0 THEN (ACUERDO_IMP_MATRICULA+ACUERDO_IMP_ENSENANZA+ACUERDO_IMP_INTERNADO)*.10 ELSE TOTAL_BECA_ADIC*0.10 END) AS DIEZMO" +
						" FROM MATEO.FES_CC_AFE_ACUERDOS " +
						" WHERE ACUERDO_EJERCICIO_ID = '"+ejercicioId+"'" +
						" AND MATRICULA IN (SELECT CODIGO_PERSONAL FROM ENOC.BEC_PUESTO_ALUMNO WHERE TO_DATE('"+fecha+"','DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN)" +
						" AND TIPO_ID IN (SELECT TIPO FROM ENOC.BEC_TIPO WHERE DIEZMO = 'S')" +
						" GROUP BY MATRICULA,ALPUESTO_PUESTO_ID ";
				rs = st.executeQuery(comando);			
				while (rs.next()){				
					map.put(rs.getString("MATRICULA")+rs.getString("ALPUESTO_PUESTO_ID"), rs.getString("DIEZMO"));				
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.afe.FesCcAfeAcuerdosUtil|mapBecaAcuerdo|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return map;
		}
		
		public static HashMap<String,String> mapBecaDiezmoSinFecha(Connection conn, String ejercicioId) throws SQLException{
			
			HashMap<String,String> map				= new HashMap<String,String>();
			Statement st 							= conn.createStatement();
			ResultSet rs		 					= null;
			String comando							= "";
					
			try{
				comando = "SELECT" +
						" MATRICULA," +
						" ALPUESTO_PUESTO_ID," +
						" SUM(CASE COALESCE(TOTAL_BECA_ADIC,0) WHEN 0 THEN (ACUERDO_IMP_MATRICULA+ACUERDO_IMP_ENSENANZA+ACUERDO_IMP_INTERNADO)*.10 ELSE TOTAL_BECA_ADIC*0.10 END) AS DIEZMO" +
						" FROM MATEO.FES_CC_AFE_ACUERDOS " +
						" WHERE ACUERDO_EJERCICIO_ID = '"+ejercicioId+"'" +
						" AND TIPO_ID IN (SELECT TIPO FROM ENOC.BEC_TIPO WHERE DIEZMO = 'S')" +
						" GROUP BY MATRICULA,ALPUESTO_PUESTO_ID ";
				rs = st.executeQuery(comando);			
				while (rs.next()){				
					map.put(rs.getString("MATRICULA")+rs.getString("ALPUESTO_PUESTO_ID"), rs.getString("DIEZMO"));				
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.afe.FesCcAfeAcuerdosUtil|mapBecaAcuerdo|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return map;
		}
		
		// Map de consulta la beca basica del alumno
		public static HashMap<String,String> mapBasicas(Connection conn, String ejercicioId, String fecha) throws SQLException{
			
			HashMap<String,String> map				= new HashMap<String,String>();
			Statement st 							= conn.createStatement();
			ResultSet rs		 					= null;
			String comando							= "";
					
			try{
				comando = "SELECT" +
						" MATRICULA," +
						" ALPUESTO_PUESTO_ID," +
						" SUM(ACUERDO_IMP_ENSENANZA) AS BASICA" +
						" FROM MATEO.FES_CC_AFE_ACUERDOS " +
						" WHERE ACUERDO_EJERCICIO_ID = '"+ejercicioId+"'" +
						" AND MATRICULA IN (SELECT CODIGO_PERSONAL FROM ENOC.BEC_PUESTO_ALUMNO WHERE TO_DATE('"+fecha+"','DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN)" +
						" AND TIPO_ID IN (SELECT TIPO FROM ENOC.BEC_TIPO WHERE ACUERDO IN ('B','I'))" +
						" GROUP BY MATRICULA,ALPUESTO_PUESTO_ID ";
				rs = st.executeQuery(comando);			
				while (rs.next()){				
					map.put(rs.getString("MATRICULA")+rs.getString("ALPUESTO_PUESTO_ID"), rs.getString("BASICA"));				
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.afe.FesCcAfeAcuerdosUtil|mapBasicas|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return map;
		}
		
		// Map de consulta la beca adicional
		public static HashMap<String,String> mapAdicional(Connection conn, String ejercicioId, String fecha) throws SQLException{
			
			HashMap<String,String> map				= new HashMap<String,String>();
			Statement st 							= conn.createStatement();
			ResultSet rs		 					= null;
			String comando							= "";
					
			try{
				comando = "SELECT" +
						" MATRICULA," +
						" ALPUESTO_PUESTO_ID," +
						" SUM(ACUERDO_IMP_ENSENANZA) AS ADICIONAL" +
						" FROM MATEO.FES_CC_AFE_ACUERDOS " +
						" WHERE ACUERDO_EJERCICIO_ID = '"+ejercicioId+"'" +
						" AND MATRICULA IN (SELECT CODIGO_PERSONAL FROM ENOC.BEC_PUESTO_ALUMNO WHERE TO_DATE('"+fecha+"','DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN)" +
						" AND TIPO_ID IN (SELECT TIPO FROM ENOC.BEC_TIPO WHERE ACUERDO IN ('A','E'))" +
						" GROUP BY MATRICULA,ALPUESTO_PUESTO_ID ";
				rs = st.executeQuery(comando);			
				while (rs.next()){				
					map.put(rs.getString("MATRICULA")+rs.getString("ALPUESTO_PUESTO_ID"), rs.getString("ADICIONAL"));			
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.afe.FesCcAfeAcuerdosUtil|mapAdicional|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return map;
		}		
		
		public String totalBecaAlumno(Connection conn, String codigoPersonal, String carga, String tipoBeca) throws SQLException{
			Statement st 			= conn.createStatement();
			ResultSet rs		 	= null;
			String total			= "0";
			String comando			= "";
					
			try{
				comando = " SELECT COALESCE(SUM(ACUERDO_IMP_MATRICULA+ACUERDO_IMP_ENSENANZA+ACUERDO_IMP_INTERNADO),0) AS TOTAL "
						+ " FROM MATEO.FES_CC_AFE_ACUERDOS "
						+ " WHERE MATRICULA = '"+codigoPersonal+"'"
						+ " AND CARGA_ID = '"+carga+"'"
						+ " AND TIPO_ACUERDO IN ("+tipoBeca+")"
						+ " AND ALPUESTO_PUESTO_ID IN (SELECT PUESTO_ID FROM BEC_PUESTO_ALUMNO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"')";
				rs = st.executeQuery(comando);			
				while (rs.next()){				
					total = rs.getString("TOTAL");		
				}				
			}catch(Exception ex){
				System.out.println("Error - aca.afe.FesCcAfeAcuerdosUtil|totalBecaAlumno|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			return total;
		}
		
		
}
