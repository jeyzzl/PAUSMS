package aca.internado.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class IntDatosAlumnoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	

	public boolean insertReg(IntDatosAlumno datosAlum ){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.INT_DATOS_ALUMNO VALUES(?,?,?,?,?,?,?,?,?)"; 
			Object[] parametros = new Object[] {
					datosAlum.getCodigoPersonal(),datosAlum.getComputadora(),datosAlum.getTratamiento(), datosAlum.getMotivo(), datosAlum.getTipoSangre(),
					datosAlum.getInstrumentos(),datosAlum.getCelular(),datosAlum.getCorreo(),datosAlum.getTelefono()
				};
				if (enocJdbc.update(comando,parametros)==1){
					ok = true;
				}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntDatosAlumnoDao|insertReg|:"+ex);
		}
		
		return ok;
	}
	public boolean updateReg(IntDatosAlumno datosAlum ){
		boolean ok = false;

		try{
			String comando = "UPDATE ENOC.INT_DATOS_ALUMNO "+ 
					" SET COMPUTADORA = ?, TRATAMIENTO = ?, MOTIVO = ?, TIPO_SANGRE = ?, "+
					" INSTRUMENTOS = ?, CELULAR = ?, CORREO = ?, TELEFONO = ? "+
					" WHERE CODIGO_PERSONAL = ?";			
			Object[] parametros = new Object[] {
					datosAlum.getComputadora(),datosAlum.getTratamiento(),datosAlum.getMotivo(), datosAlum.getTipoSangre(), datosAlum.getInstrumentos(),
					datosAlum.getCelular(),datosAlum.getCorreo(),datosAlum.getTelefono(),datosAlum.getCodigoPersonal()
				};
				if (enocJdbc.update(comando,parametros)==1){
					ok = true;
				}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntDatosAlumnoDao|updateReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean deleteReg(String codigoPersonal ){
		boolean ok = false;

		try{
			String comando = "DELETE FROM ENOC.INT_DATOS_ALUMNO WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] { codigoPersonal};
			
		 	if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntDatosAlumnoDao|deleteReg|:"+ex);
		}
		
		return ok;
	}
	
	public IntDatosAlumno mapeaRegId(String codigoPersonal){		
		IntDatosAlumno datosAlum = new IntDatosAlumno();		
		try{ 
			String comando = "SELECT COUNT(*) FROM ENOC.INT_DATOS_ALUMNO WHERE CODIGO_PERSONAL = ? "; 
			Object[] parametros = new Object[] { codigoPersonal };
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				comando = "SELECT * FROM ENOC.INT_DATOS_ALUMNO WHERE CODIGO_PERSONAL = ?";			
				datosAlum = enocJdbc.queryForObject(comando, new IntDatosAlumnoMapper(),parametros);
			}					
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntDatosAlumnoDao|mapeaRegId|:"+ex);
		}		
		return datosAlum;
	}
	
	public boolean existeReg(String codigoPersonal){		
		boolean ok = false;				
		try{ 
			String comando = "SELECT COUNT(*) FROM ENOC.INT_DATOS_ALUMNO WHERE CODIGO_PERSONAL = ? "; 
			Object[] parametros = new Object[] { codigoPersonal };
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntDatosAlumnoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
}
