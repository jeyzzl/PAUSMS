package aca.admision.spring;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AdmRecomiendaDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AdmRecomienda admRecomienda) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO SALOMON.ADM_RECOMIENDA"+ 
				" (FOLIO, RECOMENDACION_ID, NOMBRE, PUESTO, EMAIL, TELEFONO, ESTADO, DIRECCION ) "+
				" VALUES( TO_NUMBER(?,'99999999'), TO_NUMBER(?,'99'), ?, ?, ?, ?, ? )";
			
			Object[] parametros = new Object[] {
				admRecomienda.getFolio(),admRecomienda.getRecomendacionId(),admRecomienda.getNombre(),admRecomienda.getPuesto(),admRecomienda.getEmail(),admRecomienda.getTelefono(),
				admRecomienda.getEstado(), admRecomienda.getDireccion()
 		 	};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.spring.AdmRecomiendaDao|insertReg|:"+ex);
		}

		return ok;
	}
	
	public boolean updateReg(AdmRecomienda admRecomienda) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE SALOMON.ADM_RECOMIENDA " + 
					" SET NOMBRE = ?, " +
					" PUESTO = ?, " +
					" EMAIL = ?, " +
					" TELEFONO = ?, ESTADO = ?, DIRECCION = ? " +				
					" WHERE FOLIO = TO_NUMBER(?,'99999999') AND RECOMENDACION_ID = TO_NUMBER(?,'99') ";
			
			Object[] parametros = new Object[] {
				admRecomienda.getNombre(),admRecomienda.getPuesto(),admRecomienda.getEmail(),admRecomienda.getTelefono(),
				admRecomienda.getEstado(),admRecomienda.getDireccion(),admRecomienda.getFolio(),admRecomienda.getRecomendacionId()
 		 	};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.spring.AdmRecomiendaDao|updateReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean deleteReg(String folio, String recomendacionId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM SALOMON.ADM_RECOMIENDA "+ 
					" WHERE FOLIO = TO_NUMBER(?,'99999999') AND RECOMENDACION_ID = TO_NUMBER(?, '99') ";
			
			Object[] parametros = new Object[] {folio,recomendacionId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}

		}catch(Exception ex){
			System.out.println("Error - adm.alumno.spring.AdmRecomiendaDao|deleteReg|:"+ex);
		}
		
		return ok;
	}

	public boolean deleteReg(String folio) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM SALOMON.ADM_RECOMIENDA "+ 
					" WHERE FOLIO = TO_NUMBER(?,'99999999')";
			
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}

		}catch(Exception ex){
			System.out.println("Error - adm.alumno.spring.AdmRecomiendaDao|deleteReg|:"+ex);
		}
		
		return ok;
	}
	
	public AdmRecomienda mapeaRegId(String folio, String recomendacionId ) {
		AdmRecomienda objeto = new AdmRecomienda();
		
		try {
			String comando = "SELECT FOLIO, RECOMENDACION_ID, NOMBRE, PUESTO, EMAIL, TELEFONO, ESTADO, DIRECCION " +
					" FROM SALOMON.ADM_RECOMIENDA "+ 
					" WHERE FOLIO = TO_NUMBER(?,'9999999') AND RECOMENDACION_ID = TO_NUMBER(?,'99') ";
			
			Object[] parametros = new Object[] {folio,recomendacionId};
			objeto = enocJdbc.queryForObject(comando, new AdmRecomiendaMapper(), parametros);
		} catch (Exception ex) {
			System.out.println("Error - adm.alumno.spring.AdmRecomiendaDao|deleteReg|:"+ex);
		}
		
		return objeto;	
	}
	
	public boolean existeReg(String folio, String recomendacionId ) {
		boolean ok 		= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_RECOMIENDA "+ 
					" WHERE FOLIO = TO_NUMBER(?,'9999999') AND RECOMENDACION_ID = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {folio,recomendacionId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.spring.AdmRecomiendaDao|existeReg|:"+ex);
		}
		
		return ok;
	}

	public boolean existeRegFolio(String folio ) {
		boolean ok 		= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_RECOMIENDA "+ 
					" WHERE FOLIO = TO_NUMBER(?,'9999999')";
			
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.spring.AdmRecomiendaDao|existeRegFolio|:"+ex);
		}
		
		return ok;
	}
	
	public List<AdmRecomienda> getAll(Connection conn, String orden) {
		List<AdmRecomienda> lista	= new ArrayList<AdmRecomienda>();
		
		try{
			String comando = "SELECT FOLIO, RECOMENDACION_ID, NOMBRE, PUESTO, EMAIL, TELEFONO, ESTADO, DIRECCION"
					+ " FROM SALOMON.ADM_RECOMIENDA "+ orden; 
			
			lista = enocJdbc.query(comando, new AdmRecomiendaMapper());
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.spring.AdmRecomiendaDao|getAll|:"+ex);
		}
		
		return lista;
	}
	
	public List<AdmRecomienda> lisPorFolio(String folio, String orden) {
		List<AdmRecomienda> lista	= new ArrayList<AdmRecomienda>();		
		try{
			String comando = "SELECT FOLIO, RECOMENDACION_ID, NOMBRE, PUESTO, EMAIL, TELEFONO, ESTADO, DIRECCION"
					+ " FROM SALOMON.ADM_RECOMIENDA WHERE FOLIO = TO_NUMBER(?,'9999999') "+ orden;
			lista = enocJdbc.query(comando, new AdmRecomiendaMapper(), folio);			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.spring.AdmRecomiendaDao|lisPorFolio|:"+ex);
		}		
		return lista;
	}
	
	public HashMap<String,AdmRecomienda> mapaRecomendaciones(String folio) {		
		HashMap<String,AdmRecomienda> mapa = new HashMap<String,AdmRecomienda>();
		List<AdmRecomienda> lista		= new ArrayList<AdmRecomienda>();		
		try{
			String comando = "SELECT FOLIO, RECOMENDACION_ID, NOMBRE, PUESTO, EMAIL, TELEFONO, ESTADO, DIRECCION FROM SALOMON.ADM_RECOMIENDA"
					+ " WHERE FOLIO = TO_NUMBER(?,'99999999')";
			Object[] parametros = new Object[] {folio};
			lista = enocJdbc.query(comando, new AdmRecomiendaMapper(), parametros);
			for (AdmRecomienda doc: lista ) {
				mapa.put(doc.getFolio()+doc.getRecomendacionId(), doc);
			}			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.spring.AdmRecomiendaDao|mapaRecomendaciones|:"+ex);
		}
		
		return mapa;
	}

}
