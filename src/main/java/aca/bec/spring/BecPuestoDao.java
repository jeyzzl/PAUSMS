package aca.bec.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import aca.bec.spring.BecPuestoDao;

@Component
public class BecPuestoDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(BecPuesto becPuesto){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.BEC_PUESTO"+ 
				"(ID_EJERCICIO, ID_CCOSTO," +
				" CATEGORIA_ID, PERIODO_ID, JUSTIFICACION, FUNCION, COMPETENCIAS, CERTIFICACIONES, CANTIDAD, ESTADO, OTRAS_COMP)"+
				" VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
			
			Object[] parametros = new Object[] {becPuesto.getIdEjercicio(), becPuesto.getIdCcosto(), becPuesto.getCategoriaId(), becPuesto.getPeriodoId(), becPuesto.getJustificacion(), 
					becPuesto.getFuncion(), becPuesto.getCompetencias(), becPuesto.getCertificaciones(), becPuesto.getCantidad(), becPuesto.getEstado(), becPuesto.getOtrasComp()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg(BecPuesto becPuesto){
		boolean ok = false;

		try{
			String comando = "UPDATE ENOC.BEC_PUESTO"+ 
				" SET JUSTIFICACION = ?," +
				" FUNCION = ?, COMPETENCIAS = ?, CERTIFICACIONES = ?, CANTIDAD = ?, ESTADO = ?, OTRAS_COMP = ? "+				
				" WHERE ID_EJERCICIO = ? AND ID_CCOSTO = ? AND CATEGORIA_ID = ? AND PERIODO_ID = ? ";

			Object[] parametros = new Object[] {becPuesto.getJustificacion(), becPuesto.getFuncion(), becPuesto.getCompetencias(), becPuesto.getCertificaciones(), becPuesto.getCantidad(),
					becPuesto.getEstado(), becPuesto.getOtrasComp(), becPuesto.getIdEjercicio(), becPuesto.getIdCcosto(), becPuesto.getCategoriaId(), becPuesto.getPeriodoId() };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	
	public boolean deleteReg(String idEjercicio, String idCcosto, String categoriaId, String periodoId){
		boolean ok = false;

		try{
			String comando = "DELETE FROM ENOC.BEC_PUESTO"+ 
				" WHERE ID_EJERCICIO = ? AND ID_CCOSTO = ? AND CATEGORIA_ID = ? AND PERIODO_ID = ? ";
			
			Object[] parametros = new Object[] {idEjercicio, idCcosto, categoriaId, periodoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public BecPuesto mapeaRegId(String idEjercicio, String idCcosto, String categoriaId, String periodoId){
		BecPuesto becPuesto = new BecPuesto();		
		try{
			String comando = "SELECT  * " +
					" FROM ENOC.BEC_PUESTO WHERE ID_EJERCICIO = ? AND ID_CCOSTO = ? AND CATEGORIA_ID = ? AND PERIODO_ID = ? ";			
			Object[] parametros = new Object[] {idEjercicio, idCcosto, categoriaId, periodoId};
			becPuesto = enocJdbc.queryForObject(comando, new BecPuestoMapper(),parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoDao|mapeaRegId|:"+ex);
		}
		return becPuesto;
	}
	
	public boolean existeReg(String idEjercicio, String idCcosto, String categoriaId, String periodoId){
		boolean ok 			= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BEC_PUESTO WHERE ID_EJERCICIO = ? AND ID_CCOSTO = ? AND CATEGORIA_ID = ? AND PERIODO_ID = ? ";			
			Object[] parametros = new Object[] {idEjercicio, idCcosto, categoriaId, periodoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String getCantidadBecasUsadas(String idEjercicio, String ccosto, String tipo, String fechaPuesto, String periodoId){
		String cantidad 		= "0";		
		try{
			String comando = "SELECT COUNT(PUESTO_ID) AS CANTIDAD FROM ENOC.BEC_PUESTO_ALUMNO"
					+ " WHERE ID_EJERCICIO = ?"
					+ " AND ID_CCOSTO = ?"					
					+ " AND TIPO = ?"
					+ " AND TO_DATE(?, 'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN"
					+ " AND PERIODO_ID = ?";			
			Object[] parametros = new Object[] {idEjercicio, ccosto, tipo, fechaPuesto, periodoId};
			cantidad = enocJdbc.queryForObject(comando,String.class,parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoDao|getCantidadBecasUsadas|:"+ex);
		}
		return cantidad;
	}
	
	public String getCantidadActualTemporal(String fechaPuesto, String idEjercicio, String ccosto){
		String cantidad 		= "0";
		
		try{
			String comando = "SELECT COUNT(PUESTO_ID) AS CANTIDAD FROM ENOC.BEC_PUESTO_ALUMNO" +
					" WHERE ID_EJERCICIO = ?" +
					" AND ID_CCOSTO = ?" +
					" AND TIPO = 'T'" +
					" AND TO_DATE(?, 'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN";			
			Object[] parametros = new Object[] {idEjercicio, ccosto, fechaPuesto};
			cantidad = enocJdbc.queryForObject(comando,String.class,parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoDao|getCantidadActualTemporal|:"+ex);
		}
		return cantidad;
	}
	
	public String getCantidadActualBasica(String fechaPuesto, String idEjercicio, String ccosto){
		String cantidad 		= "0";
		
		try{
			String comando = "SELECT COUNT(PUESTO_ID) AS CANTIDAD FROM ENOC.BEC_PUESTO_ALUMNO " +
					" WHERE ID_EJERCICIO = ? " +
					" AND ID_CCOSTO = ?" +
					" AND TIPO = 'B'" +
					" AND TO_DATE(?, 'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN";
			
			Object[] parametros = new Object[] {idEjercicio, ccosto, fechaPuesto };
			cantidad = enocJdbc.queryForObject(comando,String.class,parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoDao|getCantidadActualBasica|:"+ex);
		}
		return cantidad;
	}
	
	public String getCantidadActualIndustrial(String fechaPuesto, String idEjercicio, String ccosto){
		String cantidad 		= "0";		
		try{
			String comando = "SELECT COUNT(PUESTO_ID) AS CANTIDAD FROM ENOC.BEC_PUESTO_ALUMNO " +
					" WHERE ID_EJERCICIO = ? " +
					" AND ID_CCOSTO = ?" +
					" AND TIPO = 'I'" +
					" AND TO_DATE(?, 'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN"; 
			
			Object[] parametros = new Object[] {idEjercicio, ccosto, fechaPuesto};
			cantidad = enocJdbc.queryForObject(comando,String.class,parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoDao|getCantidadActualIndustrial|:"+ex);
		}
		return cantidad;
	}
	
	public String getCantidadActualPreIndustrial(String fechaPuesto, String idEjercicio, String ccosto){
		String cantidad 		= "0";		
		try{
			String comando = "SELECT COUNT(PUESTO_ID) AS CANTIDAD FROM ENOC.BEC_PUESTO_ALUMNO " +
					" WHERE ID_EJERCICIO = ?" +
					" AND ID_CCOSTO = ?" +
					" AND TIPO = 'P'" +
					" AND TO_DATE(?, 'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN";			
			Object[] parametros = new Object[] {idEjercicio, ccosto, fechaPuesto};
			cantidad = enocJdbc.queryForObject(comando,String.class,parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoDao|getCantidadActualPreIndustrial|:"+ex);
		}
		return cantidad;
	}
	
	public String getCantidadActualPostgrado(String fechaPuesto, String idEjercicio, String ccosto){
		String cantidad 		= "0";		
		try{
			String comando = "SELECT COUNT(PUESTO_ID) AS CANTIDAD FROM ENOC.BEC_PUESTO_ALUMNO " +
					" WHERE ID_EJERCICIO = ? " +
					" AND ID_CCOSTO = ?" +
					" AND TIPO = 'M'" +
					" AND TO_DATE(?, 'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN";		
			Object[] parametros = new Object[] {idEjercicio, ccosto, fechaPuesto};
			cantidad = enocJdbc.queryForObject(comando,String.class,parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoDao|getCantidadActualPostgrado|:"+ex);
		}
		return cantidad;
	}
	
	public List<BecPuesto> getListAll(String orden){
		List<BecPuesto> lis 		= new ArrayList<BecPuesto>();
		try{
			String comando = "SELECT * FROM ENOC.BEC_PUESTO "+orden;			
			lis = enocJdbc.query(comando, new BecPuestoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoDao|getListAll|:"+ex);
		}
		return lis;
	}
	
	public List<BecPuesto> getListAllEjercicio(String idEjercicio, String orden){
		List<BecPuesto> lis 		= new ArrayList<BecPuesto>();
		try{
			String comando = "SELECT * FROM ENOC.BEC_PUESTO WHERE ID_EJERCICIO = ? "+orden;	
			lis = enocJdbc.query(comando, new BecPuestoMapper(), idEjercicio);
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoDao|getListAll|:"+ex);
		}
		return lis;
	}
	
	public List<BecPuesto> getListAllCcosto(String idCcosto, String idEjercicio, String periodoId, String orden){
		List<BecPuesto> lis 		= new ArrayList<BecPuesto>();
		try{
			String comando = "SELECT * FROM ENOC.BEC_PUESTO WHERE ID_CCOSTO = ? AND ID_EJERCICIO = ? AND PERIODO_ID = ? "+orden;			
			lis = enocJdbc.query(comando, new BecPuestoMapper(), idCcosto, idEjercicio, periodoId);			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoDao|getListAllCcosto|:"+ex);
		}
		return lis;
	}
	
	public List<BecPuesto> getListAllCcosto(String idCcosto, String idEjercicio, String orden){
		List<BecPuesto> lis 		= new ArrayList<BecPuesto>();
		try{
			String comando = "SELECT * FROM ENOC.BEC_PUESTO WHERE ID_EJERCICIO = ? AND  ID_CCOSTO = ? "+orden;
			lis = enocJdbc.query(comando, new BecPuestoMapper(), idEjercicio, idCcosto );			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoDao|getListAllCcosto|:"+ex);
		}
		return lis;
	}
	
	public List<BecPuesto> listPuestosDepto(String idCcosto, String idEjercicio, String fecha, String orden){
		List<BecPuesto> lis 		= new ArrayList<BecPuesto>();
		try{
			String comando = "SELECT * FROM ENOC.BEC_PUESTO"
					+ " WHERE ID_CCOSTO = ?"
					+ " AND ID_EJERCICIO = ?"
					+ " AND PERIODO_ID IN (SELECT PERIODO_ID FROM ENOC.BEC_PERIODO WHERE TO_DATE(?,'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN) "+orden;
			lis = enocJdbc.query(comando, new BecPuestoMapper(), idCcosto, idEjercicio, fecha);
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoDao|listPuestosDepto|:"+ex);
		}
		return lis;
	}
	
	public List<BecPuesto> lisPuestosPorDepto(String periodoId, String deptoId, String orden){
		List<BecPuesto> lis 		= new ArrayList<BecPuesto>();
		try{
			String comando = "SELECT * FROM ENOC.BEC_PUESTO WHERE PERIODO_ID = ? AND ID_CCOSTO = ? "+orden;
			lis = enocJdbc.query(comando, new BecPuestoMapper(), periodoId, deptoId );
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoDao|lisPuestosPorDepto|:"+ex);
		}
		return lis;
	}
	
	public List<BecPuesto> listPuestosEjercicio(String idEjercicio, String fecha, String orden){
		List<BecPuesto> lis 		= new ArrayList<BecPuesto>();
		try{
			String comando = "SELECT * FROM ENOC.BEC_PUESTO"
					+ " WHERE ID_EJERCICIO = ?"
					+ " AND PERIODO_ID IN (SELECT PERIODO_ID FROM ENOC.BEC_PERIODO WHERE TO_DATE(?,'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN) "+orden;
			Object[] parametros = new Object[] {idEjercicio, fecha};			
			lis = enocJdbc.query(comando, new BecPuestoMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoDao|listPuestosEjercicio|:"+ex);
		}
		return lis;
	}
	
	public List<BecPuesto> getListCategorias(String periodoId, String idCcosto, String orden){
		List<BecPuesto> lisCat 		= new ArrayList<BecPuesto>();
		try{
			String comando = "SELECT * FROM ENOC.BEC_PUESTO WHERE PERIODO_ID = ? AND ID_CCOSTO = ? "+ orden;			
			lisCat = enocJdbc.query(comando, new BecPuestoMapper(), periodoId, idCcosto);
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoDao|getListCategorias|:"+ex);
		}
		return lisCat;
	}
	
	public List<BecPuesto> getListCategorias(String idCcosto, String orden){
		List<BecPuesto> lisCat 		= new ArrayList<BecPuesto>();
		try{
			String comando = "SELECT * FROM ENOC.BEC_PUESTO WHERE ID_CCOSTO = ? "+ orden;
			lisCat = enocJdbc.query(comando, new BecPuestoMapper(), idCcosto);			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoDao|getListCategorias|:"+ex);
		}
		return lisCat;
	}
	
	public boolean tieneAlumnos(String idEjercicio, String idCcosto, String categoria, String periodoId){
		String comando					= "";
		boolean tiene					= false;		
		try{
			comando = "SELECT COUNT(*) FROM ENOC.BEC_PUESTO_ALUMNO WHERE ID_EJERCICIO = ? AND ID_CCOSTO = ? AND CATEGORIA_ID = ? AND PERIODO_ID = ?";			
			Object[] parametros = new Object[] {idEjercicio, idCcosto, categoria, periodoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				tiene = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoDao|tieneAlumnos|:"+ex);
		}
		return tiene;
	}
	
	public HashMap<String,BecPuesto> mapPuestos(String idEjercicio){
		HashMap<String, BecPuesto> mapa = new HashMap<String, BecPuesto>();
		List<BecPuesto> lis 			= new ArrayList<BecPuesto>();
		try{
			String comando = " SELECT * FROM ENOC.BEC_PUESTO WHERE ID_EJERCICIO = ?";			
			lis = enocJdbc.query(comando,new BecPuestoMapper(), idEjercicio);
			for(BecPuesto puesto : lis){				
				mapa.put(puesto.getIdEjercicio(), puesto);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoDao|MapPuestos|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapaCategorias(){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lis 			= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CATEGORIA_ID AS LLAVE, COUNT(*) AS VALOR FROM BEC_PUESTO GROUP BY CATEGORIA_ID";		
			lis = enocJdbc.query(comando,new aca.MapaMapper());
			for(aca.Mapa map : lis){				
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoDao|mapaCategorias|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapaPeriodos(){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lis 			= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT PERIODO_ID AS LLAVE, COUNT(*) AS VALOR FROM BEC_PUESTO GROUP BY PERIODO_ID";		
			lis = enocJdbc.query(comando,new aca.MapaMapper());
			for(aca.Mapa map : lis){				
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPuestoDao|mapaPeriodos|:"+ex);
		}
		return mapa;
	}
	
}
