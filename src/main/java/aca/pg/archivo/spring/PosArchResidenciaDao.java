package aca.pg.archivo.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PosArchResidenciaDao {
	
	@Autowired
	@Qualifier("jdbcArchivo")
	private JdbcTemplate archivoJdbc;	
	
	// Insert con JDBC Template
	public boolean insertRegByte( PosArchResidencia imagen){
		
		boolean ok = false;
		try{
			String query = "INSERT INTO ARCH_RESIDENCIA (CODIGO_PERSONAL, FOLIO, IMAGEN)"
					+" VALUES(?, ?, ?)";
			Object[] parametros = new Object[] {imagen.getCodigoPersonal(),imagen.getFolio(),imagen.getImagen()};
			if (archivoJdbc.update(query,parametros)==1){
				ok = true;
			}
		}catch( Exception ex){
			System.out.println("Error-aca.pg.archivo.spring.ArchResidenciaDao||insertRegByte:"+ex);
		}
		
		return ok;		
	}
	
	public boolean deleteReg(String codigoPersonal, String folio ){
		boolean ok 				= false;
		try{
			String query = "DELETE FROM ARCH_RESIDENCIA WHERE CODIGO_PERSONAL = ? AND FOLIO = ?";	
			Object[] parametros = new Object[] {codigoPersonal, folio};
			if (archivoJdbc.update(query,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.spring.ArchResidenciaDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public PosArchResidencia mapeaRegId(String codigoPersonal, String folio){
		PosArchResidencia imagen = new PosArchResidencia();
		
		try{			
			String query = "SELECT CODIGO_PERSONAL, FOLIO, IMAGEN"
					+ " FROM ARCH_RESIDENCIA"
					+ " WHERE CODIGO_PERSONAL = ? AND FOLIO = ?";
			Object[] parametros = new Object[]{codigoPersonal,folio};
			imagen = archivoJdbc.queryForObject(query, new PosArchResidenciaMapper(), parametros);
		}catch( Exception ex){
			System.out.println("Error: aca.pg.archivo.spring.ArchResidenciaUtil|mapeaRegId|:"+ex);
		}
		
		return imagen;
	}
	
	public String maximoReg(String codigoPersonal){	
		
		String folio 	= "000";
		int numero		= 0;
		try{
			String query = "SELECT COALESCE(MAX(TO_NUMBER(FOLIO,'999'))+1,0) FROM ARCH_RESIDENCIA WHERE CODIGO_PERSONAL = ?";				
			Object[] parametros = new Object[]{codigoPersonal};
			numero = archivoJdbc.queryForObject(query, Integer.class, parametros);
			if (numero<10) {
				folio = "00"+String.valueOf(numero);
			}else if (numero < 100) {
				folio = "0"+String.valueOf(numero);
			}else {
				folio = String.valueOf(numero);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.spring.ArchResidenciaDao|maximoReg|:"+ex);
		}
		
		return folio;
	}
	
	public List<PosArchResidencia> getLista(String codigoPersonal, String orden ){		
		List<PosArchResidencia> lista 		= new ArrayList<PosArchResidencia>();		
		try{
			String comando = " SELECT * FROM ARCH_RESIDENCIA WHERE CODIGO_PERSONAL = ? "+orden;	
			Object[] parametros = new Object[]{codigoPersonal};
			lista = archivoJdbc.query(comando, new PosArchResidenciaMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.spring.PosArchResidenciaDao|getLista|:"+ex);
		}
		
		return lista;
	}
}
