package aca.financiero.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SaldosAlumnosDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public HashMap<String, SaldosAlumnos> mapaInscritos(String orden) {
		HashMap<String, SaldosAlumnos> mapa = new HashMap<String, SaldosAlumnos>();
		List<SaldosAlumnos> lista 			= new ArrayList<SaldosAlumnos>();		
		try{
			String comando = "SELECT * FROM NOE.SALDOS_ALUMNOS WHERE MATRICULA IN(SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS) "+orden;			
			lista = enocJdbc.query(comando, new SaldosAlumnosMapper());				
			for(SaldosAlumnos saldo : lista){
				mapa.put(saldo.getMatricula(), saldo);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.SaldosAlumnosDao|getListInscritos|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, SaldosAlumnos> mapInscritosCarga(String cargaId) {
		HashMap<String, SaldosAlumnos> mapa = new HashMap<String, SaldosAlumnos>();
		List<SaldosAlumnos> lista 			= new ArrayList<SaldosAlumnos>();		
		try{
			String comando = "SELECT * FROM NOE.SALDOS_ALUMNOS WHERE MATRICULA IN(SELECT CODIGO_PERSONAL"
					+ " FROM ENOC.ALUM_ESTADO WHERE CARGA_ID = ? AND ESTADO = 'I')";			
			lista = enocJdbc.query(comando, new SaldosAlumnosMapper(), cargaId);			
			for(SaldosAlumnos saldo : lista){
				mapa.put(saldo.getMatricula(), saldo);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.SaldosAlumnosDao|mapInscritosCarga|:"+ex);
		}
	
		return mapa;
	}
	
	public HashMap<String, String> mapaPresupuestosPorMeses(String ejercicioId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT ID_CTAMAYOR||MES AS LLAVE, SUM(IMPORTE) AS VALOR FROM MATEO.CCP_PRESUPUESTO WHERE ID_EJERCICIO = ? GROUP BY ID_CTAMAYOR||MES";
			Object[] parametros = new Object[] {ejercicioId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(),parametros);			
			for(aca.Mapa map : lista){
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.SaldosAlumnosDao|mapaPresupuestosPorMeses|:"+ex);
		}	
		return mapa;
	}
}