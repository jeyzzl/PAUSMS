package aca.kardex.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class KrdxCursoImpMapper implements RowMapper<KrdxCursoImp> {
	public KrdxCursoImp mapRow(ResultSet rs, int arg1) throws SQLException {
		
		KrdxCursoImp objeto = new KrdxCursoImp();		
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setFolio(rs.getString("FOLIO"));		
		objeto.setFCreada(rs.getString("F_CREADA"));
		objeto.setCursoId(rs.getString("CURSO_ID"));
		objeto.setCursoId2(rs.getString("CURSO_ID2"));		
		objeto.setTitulo(rs.getString("TITULO"));		
		objeto.setOptativa(rs.getString("OPTATIVA"));
		objeto.setTipoCalId(rs.getString("TIPOCAL_ID"));		
		objeto.setNota(rs.getString("NOTA"));
		objeto.setNotaExtra(rs.getString("NOTA_EXTRA"));
		objeto.setFExtra(rs.getString("F_EXTRA"));
		objeto.setNotaConva(rs.getString("NOTA_CONVA"));		
		objeto.setObservaciones(rs.getString("OBSERVACIONES"));
		objeto.setOptativaNombre(rs.getString("OPTATIVA_NOMBRE"));
		objeto.setUsuario(rs.getString("USUARIO"));		
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setCiclo(rs.getString("CICLO"));
				
		return objeto;
	}
}