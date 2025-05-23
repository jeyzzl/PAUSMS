//Clase  para la tabla ARCH_REVALIDAS	
package aca.archivo.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ArchRevalidaDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(ArchRevalida objeto){
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.ARCH_REVALIDA(CODIGO_PERSONAL, REVALIDA, FECHA_REVALIDA) VALUES(?, ?, TO_DATE(?,'DD/MM/YYYY'))";
			Object[] parametros = new Object[] {
				objeto.getCodigoPersonal(),objeto.getRevalida(),objeto.getFechaRevalida()
			};
			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchRevalidaDao|insertReg|:"+ex);
		}
		return ok;
	}

	public boolean updateReg(ArchRevalida objeto) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.ARCH_REVALIDA SET REVALIDA = ?, FECHA_REVALIDA = TO_DATE(?,'DD/MM/YYYY') WHERE CODIGO_PERSONAL = ?";			
			Object[] parametros = new Object[] {
				objeto.getRevalida(),objeto.getFechaRevalida(),objeto.getCodigoPersonal()
			};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchRevalidaDao|updateReg|:"+ex);
		}
		return ok;
	}	

	public boolean deleteReg(String codigoPersonal) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.ARCH_REVALIDA WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {
				codigoPersonal
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchRevalidaDao|deleteReg|:"+ex);
		}
		return ok;
	}	
	
	public ArchRevalida mapeaRegId(String codigoPersonal) {
		ArchRevalida objeto = new ArchRevalida();				
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ARCH_REVALIDA WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {
				codigoPersonal
			};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				comando = "SELECT CODIGO_PERSONAL, REVALIDA, FECHA_REVALIDA FROM ENOC.ARCH_REVALIDA WHERE CODIGO_PERSONAL = ?";
				objeto = enocJdbc.queryForObject(comando, new ArchRevalidaMapper(), parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchRevalidaDao|mapeaRegId|:"+ex);
		}
		return objeto;
	}

	public boolean existeReg(String codigoPersonal) {
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ARCH_REVALIDA WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchRevalidaDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public List<ArchRevalida> getListAll(String orden){
		List<ArchRevalida> lista = new ArrayList<ArchRevalida>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, REVALIDA, FECHA_REVALIDA FROM ENOC.ARCH_REVALIDA "+orden; 
			lista = enocJdbc.query(comando, new ArchRevalidaMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchRevalidaDao|getListAll|:"+ex);
		}		
		return lista;
	}
}