package aca.tit.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TituloElectronicoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	/*
	public TitDocumentos mapeaRegId(String nombreDocumento,  String digestion, String longitud) {
		TitDocumentos antecedente = new TitDocumentos();
		 
		try{
			String comando = "SELECT FOLIO, INSTITUCION, ESTUDIOID, ESTUDIO, ENTIDADID, ENTIDAD,"
					+ " TO_CHAR(FECHAINICIO, 'YYYY/MM/DD') AS FECHAINICIO, TO_CHAR(FECHATERMINACION, 'YYYY/MM/DD') AS FECHATERMINACION, CEDULA"
					+ " FROM ENOC.TIT_ANTECEDENTE WHERE FOLIO = TO_NUMBER(?,'9999999')";
			
			Object[] parametros = new Object[] {nombreDocumento,digestion,longitud};
			antecedente = enocJdbc.queryForObject(comando, parametros, new TitDocumentosMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitDocumentosDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return antecedente;
		
	}
*/	
}
