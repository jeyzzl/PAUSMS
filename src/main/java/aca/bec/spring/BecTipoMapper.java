package aca.bec.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BecTipoMapper implements RowMapper<BecTipo> {
	
	@Override
	public BecTipo mapRow(ResultSet rs, int arg1) throws SQLException {
		
		BecTipo objeto = new BecTipo();
		
		objeto.setIdEjercicio(rs.getString("ID_EJERCICIO"));
		objeto.setTipo(rs.getString("TIPO"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setDepartamentos(rs.getString("DEPARTAMENTOS"));
		objeto.setCuenta(rs.getString("CUENTA"));
		objeto.setPorcentaje(rs.getString("PORCENTAJE"));
		objeto.setMeses(rs.getString("MESES"));	
		objeto.setHoras(rs.getString("HORAS"));
		objeto.setHorasPrepa(rs.getString("HORAS_PREPA"));	
		objeto.setAcuerdo(rs.getString("ACUERDO"));
		objeto.setImporte(rs.getString("IMPORTE"));
		objeto.setTipoAlumno(rs.getString("TIPO_ALUMNO"));	
		objeto.setDiezmo(rs.getString("DIEZMO"));
		objeto.setEstado(rs.getString("ESTADO"));	
		objeto.setAcumula(rs.getString("ACUMULA"));
		objeto.setColporta(rs.getString("COLPORTA"));
		objeto.setAplicaAdicional(rs.getString("APLICA_ADICIONAL"));	
		objeto.setMaximo(rs.getString("MAXIMO"));
		objeto.setLimite(rs.getString("LIMITE"));
		objeto.setCuentaSunplus(rs.getString("CUENTA_SUNPLUS"));
		objeto.setFlag(rs.getString("FLAG"));
		objeto.setMostrar(rs.getString("MOSTRAR"));
		objeto.setDescripcion(rs.getString("DESCRIPCION"));
		
		return objeto;
	}
}
