package aca.financiero.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ContMovimientoMapper implements RowMapper<ContMovimiento> {

	public ContMovimiento mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ContMovimiento objeto = new ContMovimiento();
		
		objeto.setIdEjercicio(rs.getString("ID_EJERCICIO"));
		objeto.setIdLibro(rs.getString("ID_LIBRO"));
		objeto.setIdCcosto(rs.getString("ID_CCOSTO"));
		objeto.setFolio(rs.getString("FOLIO"));		
		objeto.setNumMovto(rs.getString("NUMMOVTO"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setDescripcion(rs.getString("DESCRIPCION"));
		objeto.setImporte(rs.getString("IMPORTE"));		
		objeto.setNaturaleza(rs.getString("NATURALEZA"));
		objeto.setReferencia(rs.getString("REFERENCIA"));
		objeto.setReferencia2(rs.getString("REFERENCIA2"));
		objeto.setIdCtaMayorM(rs.getString("ID_CTAMAYORM"));
		objeto.setIdCcostoM(rs.getString("ID_CCOSTOM"));
		objeto.setIdAuxiliarM(rs.getString("ID_AUXILIARM"));
		objeto.setStatus(rs.getString("STATUS"));
		objeto.setTipoCuenta(rs.getString("TIPO_CUENTA"));
		objeto.setConceptoId(rs.getString("CONCEPTO_ID"));
		
		return objeto;
	}

}
