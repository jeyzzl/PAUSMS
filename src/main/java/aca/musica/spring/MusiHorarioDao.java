package aca.musica.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MusiHorarioDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(MusiHorario musiHorario) {
 		boolean ok = false; 		
 		try{
 			String comando = "INSERT INTO ENOC.MUSI_HORARIO "
 				+ " (MAESTRO_ID,HORA_INICIO,HORA_FINAL,CUPO,VALOR,DIA,ESTADO,MIN_INICIO,MIN_FINAL,CARGA_ID,FOLIO) "+
 				"VALUES(?, ?, ?, TO_NUMBER(?,'99'),TO_NUMBER(?,'99.99'),TO_NUMBER(?,'9'), ?, ?, ?, ?, TO_NUMBER(?,'9999999'))"; 			
 			Object[] parametros = new Object[] {
				musiHorario.getMaestroId(),musiHorario.getHoraInicio(),musiHorario.getHoraFinal(),musiHorario.getCupo(),musiHorario.getValor(),musiHorario.getDia(),
				musiHorario.getEstado(),musiHorario.getMinInicio(),musiHorario.getMinFinal(),musiHorario.getCargaId(),musiHorario.getFolio()
			};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.spring.MusiHorarioDao|insertReg|:"+ex);			
 		}
 		return ok;
 	}	
 	
 	public boolean updateReg(MusiHorario musiHorario) { 		
 		boolean ok = false; 		
 		try{
 			String comando = "UPDATE ENOC.MUSI_HORARIO"		 
 				+ " SET HORA_INICIO = ?,"
 				+ " HORA_FINAL = ?,"
 				+ " CUPO = TO_NUMBER(?,'99'),"
 				+ " VALOR = TO_NUMBER(?,'99.99'),"
 				+ " DIA = TO_NUMBER(?,'99'),"
 				+ " ESTADO = ?,"
 				+ " MIN_INICIO = ?,"
 				+ " MIN_FINAL = ?,"
 				+ " CARGA_ID = ?,"
 				+ " MAESTRO_ID = ?"
 				+ " WHERE FOLIO = TO_NUMBER(?,'9999999')";
 			Object[] parametros = new Object[] {
				musiHorario.getHoraInicio(),musiHorario.getHoraFinal(),musiHorario.getCupo(),musiHorario.getValor(),musiHorario.getDia(),
				musiHorario.getEstado(),musiHorario.getMinInicio(),musiHorario.getMinFinal(),musiHorario.getCargaId(),
				musiHorario.getMaestroId(),musiHorario.getFolio()
			};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.spring.MusiHorarioDao|updateReg|:"+ex);		
 		} 		
 		return ok;
 	} 
 	
 	public boolean deleteReg(String folio) {
 		boolean ok = false; 		
 		try{
 			String comando = "DELETE FROM ENOC.MUSI_HORARIO WHERE FOLIO = TO_NUMBER(?,'9999999')"; 			
 			Object[] parametros = new Object[] {folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.spring.MusiHorarioDao|deleteReg|:"+ex);			
 		}
 		return ok;
 	}
 	
 	public MusiHorario mapeaRegId(String folio) {
 		MusiHorario objeto = new MusiHorario(); 		
 		try{
	 		String comando = "SELECT MAESTRO_ID,HORA_INICIO,HORA_FINAL,CUPO,VALOR,DIA,ESTADO,MIN_INICIO,MIN_FINAL,CARGA_ID,FOLIO FROM ENOC.MUSI_HORARIO"
	 				+ " WHERE FOLIO = TO_NUMBER(?,'9999999')"; 
	 		Object[] parametros = new Object[] {folio};
			objeto = enocJdbc.queryForObject(comando, new MusiHorarioMapper(), parametros);			
 		}catch(Exception ex){
			System.out.println("Error - aca.musica.spring.MusiHorarioDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		} 		
 		return objeto;
 	}

 	public boolean existeReg(String folio) { 
 		boolean ok = false;
 		try{
 			String comando = "SELECT COUNT(*) FROM ENOC.MUSI_HORARIO WHERE FOLIO = TO_NUMBER(?,'9999999')";
 			Object[] parametros = new Object[] {folio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			} 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.spring.MusiHorarioDao|existeReg|:"+ex);
 		} 		
 		return ok;
 	} 	
 	
 	public float getHorasReg(String codigoPersonal, String cursoCargaId) {
 		float horas = 0;
 		try{
 			String comando = "SELECT COALESCE(SUM(VALOR),0) FROM ENOC.MUSI_HORARIO WHERE FOLIO IN (SELECT FOLIO FROM MUSI_HORARIO_ALUMNO WHERE CODIGO_PERSONAL = ? AND CURSO_CARGA_ID = ?)";
 			Object[] parametros = new Object[] {codigoPersonal,cursoCargaId};
			horas = enocJdbc.queryForObject(comando,Float.class,parametros);			 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.spring.MusiHorarioDao|existeReg|:"+ex);
 		} 		
 		return horas;
 	}
 	
 	public float getHorasFolio(String folio) {
 		float horas = 0;
 		try{
 			String comando = "SELECT COALESCE(VALOR,0) FROM ENOC.MUSI_HORARIO WHERE FOLIO = ?";
 			Object[] parametros = new Object[] {folio};
			horas = enocJdbc.queryForObject(comando,Float.class,parametros);			 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.spring.MusiHorarioDao|getHorasFolio|:"+ex);
 		} 		
 		return horas;
 	}
 	
 	public int tieneHorarios(String maestroId, String cargaId) {
 		int horarios = 0;
 		try{
 			String comando = "SELECT COUNT(*) FROM ENOC.MUSI_HORARIO "
 					+ " WHERE MAESTRO_ID = ? AND CARGA_ID = ?";
 			Object[] parametros = new Object[] {maestroId, cargaId};
 			horarios = enocJdbc.queryForObject(comando,Integer.class,parametros);			 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.spring.MusiHorarioDao|tieneHorarios|:"+ex);
 		} 		
 		return horarios;
 	}
 	
 	public String maximoReg() {	
		String maximo	= "";
		
		try{
			String comando = "SELECT COALESCE(MAX(folio)+1,1) AS MAXIMO FROM ENOC.MUSI_HORARIO"; 
			
			maximo = enocJdbc.queryForObject(comando,String.class);
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.spring.MusiHorarioDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
 	
 	public List<MusiHorario> getListAll(String orden) {
		List<MusiHorario> lista	= new ArrayList<MusiHorario>();
		
		try{
			String comando = "SELECT MAESTRO_ID, HORA_INICIO, HORA_FINAL, CUPO, "
					+ " VALOR, DIA, ESTADO, MIN_INICIO, MIN_FINAL, CARGA_ID, FOLIO "
					+ " FROM ENOC.MUSI_HORARIO "+orden;
			
			lista = enocJdbc.query(comando, new MusiHorarioMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.spring.MusiHorarioDao|getListAll|:"+ex);
		}
		
		return lista;
	}
 	
 	public List<MusiHorario> getListHorarioMaestro(String maestroId, String cargaId, String orden) {
		List<MusiHorario> lista	= new ArrayList<MusiHorario>();		
		try{
			String comando = "SELECT MAESTRO_ID, HORA_INICIO, HORA_FINAL, CUPO, VALOR, DIA, ESTADO, MIN_INICIO, MIN_FINAL, CARGA_ID, FOLIO "
					+ " FROM ENOC.MUSI_HORARIO  "
					+ " WHERE MAESTRO_ID = ? "
					+ " AND CARGA_ID = ? "+orden;
			Object[] parametros = new Object[] {maestroId, cargaId};
			lista = enocJdbc.query(comando, new MusiHorarioMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.spring.MusiHorarioDao|getListHorarioMaestro|:"+ex);
		}
		
		return lista;
	}
 	
 	public List<MusiHorario> getListHorarioMaestro(String maestroId, String cargaId, String dia, String orden) {
		List<MusiHorario> lista	= new ArrayList<MusiHorario>();
		
		try{
			String comando = "SELECT MAESTRO_ID, HORA_INICIO, HORA_FINAL, CUPO, VALOR, DIA, ESTADO, MIN_INICIO, MIN_FINAL, CARGA_ID, FOLIO "
					+ " FROM ENOC.MUSI_HORARIO  "
					+ " WHERE MAESTRO_ID = ? "
					+ " AND CARGA_ID = ?"			
					+ " AND DIA = ? "+orden;
			Object[] parametros = new Object[] {maestroId, cargaId, dia};
			lista = enocJdbc.query(comando, new MusiHorarioMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.spring.MusiHorarioDao|getListHorarioMaestro|:"+ex);
		}
		
		return lista;
	}
 	
 	public HashMap<String, String> mapaCupoPorDia(String cargaId, String maestro){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();	
		try{			
			String comando = "SELECT DIA AS LLAVE, COUNT(*) AS VALOR FROM MUSI_HORARIO WHERE CARGA_ID = ? AND MAESTRO_ID = ? GROUP BY DIA";
			Object[] parametros = new Object[] {cargaId,maestro};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.musica.spring.MusiHorarioDao|mapaCupoPorDia|:"+ex);
		}
		
		return mapa;
	}	
 	
 	public HashMap<String, String> mapaDisponibles(String cargaId, String maestro){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();	
		try{			
			String comando = "SELECT DIA AS LLAVE, COUNT(*) AS VALOR FROM MUSI_HORARIO"
					+ " WHERE CARGA_ID = ? AND MAESTRO_ID = ?"
					+ " AND CUPO > (SELECT COUNT(*) FROM ENOC.MUSI_HORARIO_ALUMNO WHERE FOLIO = ENOC.MUSI_HORARIO.FOLIO)"
					+ " GROUP BY DIA";
			Object[] parametros = new Object[] {cargaId,maestro};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.musica.spring.MusiHorarioDao|mapaDisponibles|:"+ex);
		}
		
		return mapa;
	} 	
 	
}
