package aca.pron.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PronMateriaDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(PronMateria pronMateria) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.PRON_MATERIA"
					+ " (CURSO_CARGA_ID, CURSO_ID, HORA_CLASE, HORA_TUTORIA, "
					+ " FORMACION, CORREO, DESCRIPCION, ENFOQUE, LUGAR, ESPECIAL, INTEGRIDAD)"
					+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			Object[] parametros = new Object[] {
				pronMateria.getCursoCargaId(),pronMateria.getCursoId(),pronMateria.getHoraClase(),
				pronMateria.getHoraTutoria(),pronMateria.getFormacion(),pronMateria.getCorreo(),
				pronMateria.getDescripcion(),pronMateria.getEnfoque(), pronMateria.getLugar(),
				pronMateria.getEspecial(),pronMateria.getIntegridad()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.pron.spring.PronMateriaDao|insertReg|:"+ex);
		}
		return ok;
	}	
	
	public boolean updateReg(PronMateria pronMateria) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.PRON_MATERIA"
					+ " SET CURSO_ID = ?,"
					+ " HORA_CLASE = ?,"
					+ " HORA_TUTORIA = ?,"
					+ " FORMACION = ?,"
					+ " LUGAR = ?,"
					+ " CORREO = ?,"
					+ " DESCRIPCION = ?,"
					+ " ENFOQUE = ?,"
					+ " ESPECIAL = ?,"
					+ " INTEGRIDAD = ?"
					+ " WHERE CURSO_CARGA_ID = ?";
			
			Object[] parametros = new Object[] {
				pronMateria.getCursoId(),pronMateria.getHoraClase(),
				pronMateria.getHoraTutoria(),pronMateria.getFormacion(),
				pronMateria.getLugar(),pronMateria.getCorreo(),
				pronMateria.getDescripcion(),pronMateria.getEnfoque(),
				pronMateria.getEspecial(),pronMateria.getIntegridad(),
				pronMateria.getCursoCargaId()
			};				
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.pron.spring.PronMateriaDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	public boolean deleteReg(String cursoCargaId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.PRON_MATERIA WHERE CURSO_CARGA_ID = ?";
			
			Object[] parametros = new Object[] {cursoCargaId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.pron.spring.PronMateriaDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public PronMateria mapeaRegId(String cursoCargaId) {
		PronMateria pronMateria = new PronMateria();
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.PRON_MATERIA WHERE CURSO_CARGA_ID = ?";			
			Object[] parametros = new Object[] {cursoCargaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				comando = " SELECT CURSO_CARGA_ID, CURSO_ID, HORA_CLASE, HORA_TUTORIA, FORMACION, CORREO, DESCRIPCION, ENFOQUE, LUGAR, ESPECIAL, INTEGRIDAD"
						+ " FROM ENOC.PRON_MATERIA"
						+ " WHERE CURSO_CARGA_ID = ?";				
				pronMateria = enocJdbc.queryForObject(comando, new PronMateriaMapper(), parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.pron.spring.PronMateriaDao|mapeaRegId|:"+ex);
		}		
		return pronMateria;		
	}	
	
	public boolean existeReg(String cursoCargaId) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.PRON_MATERIA WHERE CURSO_CARGA_ID = ?"; 
			
			Object[] parametros = new Object[] {cursoCargaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.pron.spring.PronMateriaDao|existeReg|:"+ex);
		}
		return ok;
	}

}
