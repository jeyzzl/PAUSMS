package aca.dherst.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class DherstArchivoMapper implements RowMapper<DherstArchivo> {
    public DherstArchivo mapRow(ResultSet rs, int arg1) throws SQLException {
        DherstArchivo objeto = new DherstArchivo();

        objeto.setFolio(rs.getString("FOLIO"));
        objeto.setNombre(rs.getString("NOMBRE"));
        objeto.setFecha(rs.getString("FECHA"));
        objeto.setComentario(rs.getString("COMENTARIO"));
        objeto.setArchivo(rs.getBytes("ARCHIVO"));
        objeto.setCodigoUsuario(rs.getString("CODIGO_USUARIO"));

        return objeto;
    }
}
