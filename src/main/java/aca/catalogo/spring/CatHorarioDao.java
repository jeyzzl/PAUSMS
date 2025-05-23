package aca.catalogo.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CatHorarioDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CatHorario horario) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.CAT_HORARIO (HORARIO_ID, FACULTAD_ID, DESCRIPCION, ESTADO, ACCESO)"
					+ " VALUES( TO_NUMBER(?,'9999999'), ?, ?, ?, ?)";
			Object[] parametros = new Object[] {horario.getHorarioId(), horario.getFacultadId(), horario.getDescripcion(), horario.getEstado(), horario.getAcceso()};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatHorarioDao|insertReg|:"+ex);			
		}
		
		return ok;
	}
	
	public boolean updateReg( CatHorario horario) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CAT_HORARIO SET FACULTAD_ID = ?, DESCRIPCION = ?, ESTADO = ?, ACCESO = ? WHERE HORARIO_ID = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {horario.getFacultadId(), horario.getDescripcion(), horario.getEstado(), horario.getAcceso(), horario.getHorarioId()};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatHorarioDao|updateReg|:"+ex);			
		}
		
		return ok;
	}
	
	public boolean deleteReg( String horarioId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CAT_HORARIO WHERE HORARIO_ID = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {horarioId};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatHorarioDao|deleteReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean existeReg( String horarioId) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_HORARIO WHERE HORARIO_ID = TO_NUMBER(?,'9999999')";			
			Object[] parametros = new Object[] {horarioId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatHorarioDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean existeFacu( String facultadId) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_HORARIO WHERE FACULTAD_ID = TO_NUMBER(?,'9999999')";			
			Object[] parametros = new Object[] {facultadId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatHorarioDao|existeFacu|:"+ex);
		}
		
		return ok;
	}
	
	public String getHorarioFacultad( String facultadId, String estado) {
		String horarioId	= "0";
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_HORARIO WHERE FACULTAD_ID = ? AND ESTADO = ? AND ROWNUM = 1";
			Object[] parametros = new Object[] {facultadId, estado};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				comando = "SELECT HORARIO_ID FROM ENOC.CAT_HORARIO WHERE FACULTAD_ID = ? AND ESTADO = ? AND ROWNUM = 1";
				horarioId = enocJdbc.queryForObject(comando, String.class, parametros);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatHorarioDao|getHorarioFacultad|:"+ex);
		}		
		return horarioId;
	}
	
	public String maximoReg() {
		int maximo = 1;
		
		try{
			String comando = "SELECT COALESCE(MAX(HORARIO_ID)+1,1) FROM ENOC.CAT_HORARIO";
			maximo = enocJdbc.queryForObject(comando, Integer.class);					
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatHorarioDao|existeReg|:"+ex);
		}
		
		return String.valueOf(maximo);
	}
	
	public CatHorario mapeaRegId(  String horarioId) {
		
		CatHorario horario 	= new CatHorario();
		try{
			String comando = "SELECT * FROM ENOC.CAT_HORARIO WHERE HORARIO_ID = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {horarioId};
			horario = enocJdbc.queryForObject(comando, new CatHorarioMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatHorarioDao|mapeaRegId|:"+ex);
		}
		
		return horario;
	}
	
	public List<CatHorario> getListAll( String orden ) {
		
		List<CatHorario> lista		= new ArrayList<CatHorario>();		
		try{
			String comando = "SELECT * FROM ENOC.CAT_HORARIO "+orden;
			lista = enocJdbc.query(comando, new CatHorarioMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatHorarioDao|getListAll|:"+ex);
		}		
		return lista;
	}
	
	public List<CatHorario> lisHorariosPorMaestro( String codigoPersonal, String bloqueId, String cargaId, String orden ){
		
		List<CatHorario> lista		= new ArrayList<CatHorario>();		
		try{
			String comando = "SELECT HORARIO_ID, FACULTAD_ID, DESCRIPCION, ESTADO, ACCESO FROM ENOC.CAT_HORARIO"
					+ " WHERE HORARIO_ID IN (SELECT DISTINCT(HORARIO_ID) FROM ENOC.CARGA_HORARIO WHERE CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL = ? AND BLOQUE_ID = ? AND CARGA_ID = ?)) "+orden;
			Object[] parametros = new Object[] {codigoPersonal, bloqueId, cargaId};
			lista = enocJdbc.query(comando, new CatHorarioMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatHorarioDao|lisHorariosPorMaestro|:"+ex);
		}
		
		return lista;
	}
	
	public List<CatHorario> filtroporAcceso( String codigoPersonal, String orden ) {
		
		List<CatHorario> lista		= new ArrayList<CatHorario>();			
		try{
			String comando = " SELECT * FROM ENOC.CAT_HORARIO"
					+ " WHERE FACULTAD_ID = '000' OR FACULTAD_ID IN"
					+ " (SELECT FACULTAD(CARRERA_ID) FROM ENOC.ACCESO_PLAN WHERE CODIGO_PERSONAL = ? "+ orden; 
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new CatHorarioMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatHorarioDao|filtroporAcceso|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String, String> mapHorario() {
		
		HashMap<String, String> mapa	= new HashMap<String, String>();
		List<CatHorario> lista		= new ArrayList<CatHorario>();		
		try{
			String comando = "SELECT * FROM ENOC.CAT_HORARIO";
			lista = enocJdbc.query(comando, new CatHorarioMapper());
			for (CatHorario horario : lista) {
				mapa.put(horario.getFacultadId(), horario.getFacultadId());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatHorarioDao|mapHorario|:"+ex);
		}
		
		return mapa;
	}
	
	public String getHorarioId( String cursoCargaId) {
		
		String  horarioId		= "0";		
		try{
			String comando = " SELECT HORARIO_ID FROM ENOC.CAT_HORARIO WHERE FACULTAD_ID =" +
										 " (SELECT ENOC.FACULTAD(CARRERA_ID) FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID = ?) AND ESTADO = 'A'";
			Object[] parametros = new Object[] {cursoCargaId};
			horarioId = enocJdbc.queryForObject(comando, String.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatHorarioDao|getHorarioId|:"+ex);
		}
		
		return horarioId;
	}
}
