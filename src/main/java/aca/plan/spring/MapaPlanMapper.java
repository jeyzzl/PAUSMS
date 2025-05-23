package aca.plan.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MapaPlanMapper implements RowMapper<MapaPlan>{

	public MapaPlan mapRow(ResultSet rs, int arg1) throws SQLException {
		MapaPlan objeto = new MapaPlan();
		
		objeto.setPlanId(rs.getString("PLAN_ID"));
		objeto.setCarreraId(rs.getString("CARRERA_ID"));
		objeto.setNombrePlan(rs.getString("NOMBRE_PLAN"));
		objeto.setFInicio(rs.getString("F_INICIO"));
		objeto.setFFinal(rs.getString("F_FINAL"));
		objeto.setFActualiza(rs.getString("F_ACTUALIZA"));
		objeto.setNumCursos(rs.getString("NUM_CURSOS"));
		objeto.setMinimo(rs.getString("MINIMO"));
		objeto.setMaximo(rs.getString("MAXIMO"));
		objeto.setCarreraSe(rs.getString("CARRERA_SE"));
		objeto.setRvoe(rs.getString("RVOE"));
		objeto.setOficial(rs.getString("OFICIAL"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setEnLinea(rs.getString("ENLINEA"));
		objeto.setCiclos(rs.getString("CICLOS"));
		objeto.setNotaExtra(rs.getString("NOTA_EXTRA"));
		objeto.setGeneral(rs.getString("GENERAL"));
		objeto.setPlanSE(rs.getString("PLAN_SE"));
		objeto.setNombrePlanMujer(rs.getString("NOMBRE_PLAN_MUJER"));
		objeto.setClaveProfesiones(rs.getString("CLAVE_PROFESIONES"));
		objeto.setPrecio(rs.getString("PRECIO"));
		objeto.setRvoeInicial(rs.getString("RVOE_INICIAL"));
		objeto.setVersionId(rs.getString("VERSION_ID"));
		objeto.setSemsys(rs.getString("CLAVE_SEMSYS"));
		objeto.setExpediente(rs.getString("EXPEDIENTE"));
		objeto.setCreditos(rs.getString("CREDITOS"));
		objeto.setPlanOrigen(rs.getString("PLAN_ORIGEN"));
		objeto.setDescuento(rs.getString("DESCUENTO"));

		return objeto;
	}

}
