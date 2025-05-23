package aca.rotaciones.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class RotHospitalEspecialidadDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(RotHospitalEspecialidad hospEsp ){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.ROT_HOSPITALESPECIALIDAD(HOSPITAL_ID, ESPECIALIDAD_ID, CONTACTO_PRINCIPAL, " + 
					" PUESTO_PRINCIPAL, CONTACTO_SECUNDARIO, PUESTO_SECUNDARIO, ESTADO) " +
					" VALUES(TO_NUMBER(?,'999'),TO_NUMBER(?,'999'),?,?,?,?,?)";
			
			Object[] parametros = new Object[] {
					hospEsp.getHospitalId(), hospEsp.getEspecialidadId(), hospEsp.getContactoPrincipal(), hospEsp.getPuestoPrincipal(),
					hospEsp.getContactoSecundario(), hospEsp.getPuestoSecundario(), hospEsp.getEstado()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.spring.RotHospitalEspecialidadDao|insertReg|:"+ex);
		}
		return ok;
	}
	
	public boolean updateReg(RotHospitalEspecialidad hospEsp ) {
		boolean ok = false;
		try{
			String comando = "UPDATE ENOC.ROT_HOSPITALESPECIALIDAD SET CONTACTO_PRINCIPAL = ? , " +
					" PUESTO_PRINCIPAL =  ?, CONTACTO_SECUNDARIO = ?, PUESTO_SECUNDARIO = ?, ESTADO = ? " +
					" WHERE HOSPITAL_ID = ? AND ESPECIALIDAD_ID = ? ";
			
			Object[] parametros = new Object[] {
					hospEsp.getContactoPrincipal(), hospEsp.getPuestoPrincipal(),hospEsp.getContactoSecundario(),
					hospEsp.getPuestoSecundario(), hospEsp.getEstado(), hospEsp.getHospitalId(), hospEsp.getEspecialidadId()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.spring.RotHospitalEspecialidadDao|updateReg|:"+ex);
		}
		return ok;
	}
	
	public boolean deleteReg(String hospitalId, String especialidadId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.ROT_HOSPITALESPECIALIDAD WHERE HOSPITAL_ID = ? AND ESPECIALIDAD_ID = ? "; 
			
			Object[] parametros = new Object[] {hospitalId, especialidadId};					
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.spring.RotHospitalEspecialidadDao|deletetReg|:"+ex);
		}
		return ok;
	}
	
	public RotHospitalEspecialidad mapeaRegId(String hospitalId, String especialidadId){
		RotHospitalEspecialidad hospEsp = new RotHospitalEspecialidad();
		try{
			String comando = "SELECT HOSPITAL_ID, ESPECIALIDAD_ID, CONTACTO_PRINCIPAL, PUESTO_PRINCIPAL," +
					" CONTACTO_SECUNDARIO, PUESTO_SECUNDARIO, ESTADO " +
					" FROM ENOC.ROT_HOSPITALESPECIALIDAD WHERE HOSPITAL_ID = ? AND ESPECIALIDAD_ID = ? ";
			
			Object[] parametros = new Object[] {hospitalId, especialidadId};
			hospEsp = enocJdbc.queryForObject(comando, new RotHospitalEspecialidadMapper(), parametros);
		
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.spring.RotHospitalEspecialidadDao|mapeaRegId|:"+ex);
		}
		return hospEsp;
	}
	
	public boolean existeReg(String hospitalId, String especialidadId) {
		boolean ok 			= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ROT_HOSPITALESPECIALIDAD WHERE HOSPITAL_ID = ? AND ESPECIALIDAD_ID = ? ";
			

			Object[] parametros = new Object[] {hospitalId, especialidadId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.spring.RotHospitalEspecialidadDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public boolean existeHospital( String hospitalId) {
		boolean ok 			= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ROT_HOSPITALESPECIALIDAD WHERE HOSPITAL_ID = ? ";
			
			Object[] parametros = new Object[] {hospitalId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.spring.RotHospitalEspecialidadDao|existeHospital|:"+ex);
		}
		return ok;
	}
	
	public List<RotHospitalEspecialidad> getListAll( String orden ) {
			
		List<RotHospitalEspecialidad> list	= new ArrayList<RotHospitalEspecialidad>();
		String comando				= "";
			
		try{
			comando = "SELECT * FROM ENOC.ROT_HOSPITALESPECIALIDAD "+orden;
			
			list = enocJdbc.query(comando, new RotHospitalEspecialidadMapper());
				
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.spring.RotHospitalEspecialidadDao|getListAll|:"+ex);
		}
		return list;
	}
	
	public List<RotHospitalEspecialidad> getListHosp( String hospitalId, String orden ) {
		
		List<RotHospitalEspecialidad> list	= new ArrayList<RotHospitalEspecialidad>();
		String comando				= "";
		
		try{
			comando = "SELECT * FROM ENOC.ROT_HOSPITALESPECIALIDAD WHERE HOSPITAL_ID = "+hospitalId+" "+orden; 
			
			list = enocJdbc.query(comando, new RotHospitalEspecialidadMapper());
	
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.spring.RotHospitalEspecialidadDao|getListHosp|:"+ex);
		}
		return list;
	}
	
	public List<RotHospitalEspecialidad> getListHospActivas( String hospitalId, String orden ) {
		
		List<RotHospitalEspecialidad> list	= new ArrayList<RotHospitalEspecialidad>();
		String comando				= "";
		
		try{
			comando = "SELECT * FROM ENOC.ROT_HOSPITALESPECIALIDAD WHERE HOSPITAL_ID = "+hospitalId+"" +
					"  AND ESTADO = 'A' "+orden; 
			
			list = enocJdbc.query(comando, new RotHospitalEspecialidadMapper());
		
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.spring.RotHospitalEspecialidadDao|getListHosp|:"+ex);
		}
		return list;
	}	
	
	public HashMap<String, String> mapTotEspecialidades(){		
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa>	lista 		 	= new ArrayList<aca.Mapa>();
		try{
			String comando = " SELECT HOSPITAL_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.ROT_HOSPITALESPECIALIDAD GROUP BY HOSPITAL_ID ";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.spring.RotHospitalEspecialidadDao|mapTotEspecialidades|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapTotHospitales(){		
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa>	lista 		 	= new ArrayList<aca.Mapa>();
		try{
			String comando = " SELECT ESPECIALIDAD_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.ROT_HOSPITALESPECIALIDAD GROUP BY ESPECIALIDAD_ID ";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.spring.RotHospitalEspecialidadDao|mapTotHospitales|:"+ex);
		}
		return mapa;
	}
}
