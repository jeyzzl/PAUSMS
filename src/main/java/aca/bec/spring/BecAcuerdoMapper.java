package aca.bec.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BecAcuerdoMapper implements RowMapper<BecAcuerdo> {
	
	@Override
	public BecAcuerdo mapRow(ResultSet rs, int arg1) throws SQLException {
		
		BecAcuerdo objeto = new BecAcuerdo();
		
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));	
		objeto.setIdEjercicio(rs.getString("ID_EJERCICIO"));
		objeto.setTipo(rs.getString("TIPO"));	
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setMatricula(rs.getString("MATRICULA"));
		objeto.setEnsenanza(rs.getString("ENSENANZA"));	
		objeto.setInternado(rs.getString("INTERNADO"));
		objeto.setValor(rs.getString("VALOR"));	
		objeto.setVigencia(rs.getString("VIGENCIA"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setPromesa(rs.getString("PROMESA"));	
		objeto.setIdCcosto(rs.getString("ID_CCOSTO"));
		objeto.setPuestoId(rs.getString("PUESTO_ID"));	
		objeto.setHoras(rs.getString("HORAS"));
		objeto.setTipoadicional(rs.getString("TIPOADICIONAL"));	
		objeto.setUsuario(rs.getString("USUARIO"));
		
		return objeto;
	}
}
