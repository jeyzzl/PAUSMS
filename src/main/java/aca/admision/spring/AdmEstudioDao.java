package aca.admision.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AdmEstudioDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AdmEstudio objeto){
		boolean ok = false;
		try{
			String comando = "INSERT INTO SALOMON.ADM_ESTUDIO (FOLIO, ID, TITULO, INSTITUCION, PAIS_ID, ESTADO_ID, CIUDAD_ID, COMPLETO, INICIO, FIN, DEPENDENCIA, CONVALIDA, ESTUDIOS, OTRA_MATERIA) "
					+ " VALUES(TO_NUMBER(?,'99999999'), TO_NUMBER(?,'99'), ?,?, TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), ?, ?, ?, ?, ?, TO_NUMBER(?, '9'), ?)";
			
			Object[] parametros = new Object[] {
				objeto.getFolio(), objeto.getId(),objeto.getTitulo(),objeto.getInstitucion(),objeto.getPaisId(),
				objeto.getEstadoId(),objeto.getCiudadId(),objeto.getCompleto(),objeto.getInicio(),objeto.getFin(),objeto.getDependencia(),objeto.getConvalida(),objeto.getEstudios(),objeto.getOtraMateria()
			};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - adm.admision.spring.AdmEstudioDao|insertReg|:"+ex);
		}

		return ok;
	}
	
	public boolean updateReg(AdmEstudio objeto){
		boolean ok = false;
		try{
			String comando = "UPDATE SALOMON.ADM_ESTUDIO "
					+ " SET TITULO = ? , "
					+ " INSTITUCION = ? , "
					+ " PAIS_ID = TO_NUMBER(?,'999'), "
					+ " ESTADO_ID = TO_NUMBER(?,'999'),"
					+ " CIUDAD_ID = TO_NUMBER(?,'999'), "
					+ " COMPLETO = ?, "
					+ " INICIO = ?, "
					+ " FIN = ?, "
					+ " DEPENDENCIA = ?, "
					+ " CONVALIDA = ?, "
					+ " ESTUDIOS = TO_NUMBER(?,'9'), "
					+ " OTRA_MATERIA = ? "
					+ " WHERE FOLIO = TO_NUMBER(?,'99999999')"
					+ " AND ID = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {
				objeto.getTitulo(),objeto.getInstitucion(),objeto.getPaisId(),objeto.getEstadoId(),objeto.getCiudadId(),
				objeto.getCompleto(),objeto.getInicio(),objeto.getFin(),objeto.getDependencia(),objeto.getConvalida(),
				objeto.getEstudios(),objeto.getOtraMateria(),objeto.getFolio(), objeto.getId()
			};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - adm.admision.spring.AdmEstudioDao|updateReg|:"+ex);
		}

		return ok;
	}
	
	public boolean deleteReg(String folio, String id){
		boolean ok = false;
		
		Object[] parametros = new Object[] {folio,id};

		try{
			String comando = "DELETE FROM SALOMON.ADM_ESTUDIO "+ 
					" WHERE FOLIO = TO_NUMBER(?,'99999999') AND ID = TO_NUMBER(?,'99') ";
			
			if (enocJdbc.update(comando,parametros)==1){			
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - adm.admision.spring.AdmEstudioDao|deleteReg|:"+ex);
			ok = false;
		}
		
		return ok;
	}
	
	public AdmEstudio mapeaRegId(String folio, String id){
		AdmEstudio objeto = new AdmEstudio();
		Object[] parametros = new Object[] {folio,id};
		
		try{
			String comando = "SELECT FOLIO, ID, TITULO, INSTITUCION, PAIS_ID, ESTADO_ID, CIUDAD_ID, COMPLETO, INICIO, FIN, DEPENDENCIA, CONVALIDA, ESTUDIOS, OTRA_MATERIA"
					+ " FROM SALOMON.ADM_ESTUDIO WHERE FOLIO = TO_NUMBER(?,'9999999') AND ID = TO_NUMBER(?,'99')";
			
			objeto = enocJdbc.queryForObject(comando, new AdmEstudioMapper(),parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmEvaluacionDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public AdmEstudio mapeaRegMaxEstudio(String folio){
		AdmEstudio objeto = new AdmEstudio();
		Object[] parametros = new Object[] {folio};
		
		try{
			String comando = "SELECT * " +
							"FROM ( " +
						    "SELECT * " +
						    "FROM SALOMON.ADM_ESTUDIO " +
						    "WHERE FOLIO = ? " +
						    "ORDER BY ESTUDIOS DESC " +
						    ")WHERE ROWNUM = 1";
			
			objeto = enocJdbc.queryForObject(comando, new AdmEstudioMapper(),parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmEvaluacionDao|mapeaRegMaxEstudio|:"+ex);
		}
		
		return objeto;
	}
	
	public boolean existeReg(String folio, String id){
		boolean ok = false;
		
		Object[] parametros = new Object[] {folio,id};	
		
		try{
			String comando = "SELECT * FROM SALOMON.ADM_ESTUDIO "
					+ " WHERE FOLIO = TO_NUMBER(?,'9999999') AND ID = TO_NUMBER(?,'99')";
			
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){	
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmEvaluacionDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg(String folio){
		String maximo = "1";
		
		try{
			String comando = "SELECT MAX(ID)+1 MAXIMO FROM SALOMON.ADM_ESTUDIO WHERE FOLIO = ?"; 
			
			maximo = enocJdbc.queryForObject(comando, String.class, new Object[] {folio});
			
		}catch(Exception ex){
			System.out.println("Error - adm.admision.spring.AdmEstudioDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}	
	
	public List<AdmEstudio> getAll(String orden){
		List<AdmEstudio> lista	= new ArrayList<AdmEstudio>();
		
		try{
			String comando = "SELECT FOLIO, ID, TITULO, INSTITUCION, PAIS_ID, ESTADO_ID, CIUDAD_ID, COMPLETO, INICIO, FIN, DEPENDENCIA, CONVALIDA, ESTUDIOS, OTRA_MATERIA "
					+ " FROM SALOMON.ADM_ESTUDIO "+ orden; 
			
			lista = enocJdbc.query(comando, new AdmEstudioMapper());
			
		}catch(Exception ex){
			System.out.println("Error - adm.admision.spring.AdmEstudioDao|getAll|:"+ex);
		}
		
		return lista;
	}
	
	public List<AdmEstudio> getListAFolio(String folio, String orden){
		List<AdmEstudio> lista	= new ArrayList<AdmEstudio>();		
		try{
			String comando = "SELECT FOLIO, ID, TITULO, INSTITUCION, PAIS_ID, ESTADO_ID, CIUDAD_ID, COMPLETO, INICIO, FIN, DEPENDENCIA, CONVALIDA, ESTUDIOS, OTRA_MATERIA "
					+ " FROM SALOMON.ADM_ESTUDIO WHERE FOLIO = TO_NUMBER(?,'9999999') "+ orden; 
			
			lista = enocJdbc.query(comando, new AdmEstudioMapper(), folio);			
		}catch(Exception ex){
			System.out.println("Error - adm.admision.spring.AdmEstudioDao|getListAFolio|:"+ex);
		}
		
		return lista;
	}
	
}
