package aca.dherst.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class DherstAlumnoMapper implements RowMapper<DherstAlumno> {
    public DherstAlumno mapRow(ResultSet rs, int arg1) throws SQLException {
        DherstAlumno objeto = new DherstAlumno();

        objeto.setFolio(rs.getString("FOLIO"));
        objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
        objeto.setSlfNo(rs.getString("SLF_NO"));
        objeto.setNombre(rs.getString("NOMBRE"));
        objeto.setApellido(rs.getString("APELLIDO"));
        objeto.setDireccion(rs.getString("DIRECCION"));
        objeto.setEmail(rs.getString("EMAIL"));
        objeto.setTelefono(rs.getString("TELEFONO"));
        objeto.setCelular(rs.getString("CELULAR"));
        objeto.setGpa(rs.getString("GPA"));
        objeto.setSexo(rs.getString("SEXO"));
        objeto.setResidencia(rs.getString("RESIDENCIA"));
        objeto.setResEstadoId(rs.getString("RES_ESTADO_ID"));
        objeto.setEstadoId(rs.getString("ESTADO_ID"));
        objeto.setReligionId(rs.getString("RELIGION_ID"));
        objeto.setPrefAeropuerto(rs.getString("PREF_AEROPUERTO"));
        objeto.setEstadoCivil(rs.getString("ESTADO_CIVIL"));
        objeto.setResidenciaTipo(rs.getString("RESIDENCIA_TIPO"));
        objeto.setPlanId(rs.getString("PLAN_ID"));
        objeto.setStatus(rs.getString("STATUS"));

        return objeto;
    }
}
