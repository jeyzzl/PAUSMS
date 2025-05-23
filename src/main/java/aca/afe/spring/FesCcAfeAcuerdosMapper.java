package aca.afe.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class FesCcAfeAcuerdosMapper implements RowMapper<FesCcAfeAcuerdos> {
	
	@Override
	public FesCcAfeAcuerdos mapRow(ResultSet rs, int arg1) throws SQLException {
		FesCcAfeAcuerdos objeto = new FesCcAfeAcuerdos();		
	    
		objeto.setMatricula(rs.getString("MATRICULA"));
		objeto.setCargaId(rs.getString("CARGA_ID"));
		objeto.setBloque(rs.getString("BLOQUE"));
		objeto.setTipoId(rs.getString("TIPO_ID"));
		objeto.setTipoNombre(rs.getString("TIPO_NOMBRE"));
		objeto.setTipoCuenta(rs.getString("TIPO_CUENTA"));
		objeto.setTipoImporte(rs.getString("TIPO_IMPORTE"));
		objeto.setTipoAcuerdo(rs.getString("TIPO_ACUERDO"));
		objeto.setAcuerdoFecha(rs.getString("ACUERDO_FECHA"));
		objeto.setAcuerdoImpMatricula(rs.getString("ACUERDO_IMP_MATRICULA"));
		objeto.setAcuerdoImpEnsenanza(rs.getString("ACUERDO_IMP_ENSENANZA"));
		objeto.setAcuerdoImpInternado(rs.getString("ACUERDO_IMP_INTERNADO"));
		objeto.setAcuerdoVigencia(rs.getString("ACUERDO_VIGENCIA"));
		objeto.setAcuerdoFolio(rs.getString("ACUERDO_FOLIO"));
		objeto.setAcuerdoPromesa(rs.getString("ACUERDO_PROMESA"));
		objeto.setAcuerdoHoras(rs.getString("ACUERDO_HORAS"));
		objeto.setAcuerdoEjercicioId(rs.getString("ACUERDO_EJERCICIO_ID"));		
		objeto.setAcuerdoCcostoId(rs.getString("ACUERDO_CCOSTO_ID"));
		objeto.setAlpuestoPuestoId(rs.getString("ALPUESTO_PUESTO_ID"));
		objeto.setCategoriaId(rs.getString("CATEGORIA_ID"));
		objeto.setCategoriaNombre(rs.getString("CATEGORIA_NOMBRE"));
		objeto.setAlpuestoFechaInical(rs.getString("ALPUESTO_FECHA_INICIAL"));
		objeto.setAlpuestoFechaFinal(rs.getString("ALPUESTO_FECHA_FINAL"));
		objeto.setAlpuestoTipo(rs.getString("ALPUESTO_TIPO"));
		objeto.setTotalBecaAdic(rs.getString("TOTAL_BECA_ADIC"));
		objeto.setValor(rs.getString("VALOR"));
		objeto.setId(rs.getString("ID"));
		
		return objeto;
	}

}
