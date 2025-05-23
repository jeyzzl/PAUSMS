package aca.podium.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AspiranteDao {
	
	@Autowired
	@Qualifier("jdbcExa")
	private JdbcTemplate jdbcExamen;	
	
	@Autowired
	private ExamenDao examenDao;

	@Autowired
	private ExamenAreaDao examenAreaDao;
	
	public Aspirante buscaAspirantePorFolio(int folio) {
		Aspirante objeto = new Aspirante();		
		try {
			String query = "SELECT ID,FOLIO,GRADO FROM EXA.ASPIRANTE WHERE FOLIO = ? ORDER BY ID DESC LIMIT 1";
			objeto = jdbcExamen.queryForObject(query, new AspiranteMapper(), new Object[]{folio});
		} catch (Exception e) {
			System.out.println("Error - aca.podium.spring.AspiranteDao|buscaAspirantePorFolio| "+e);
		}
	
		return objeto;
	}
	
	public boolean existeAspirantePorFolio(int folio) {
		boolean ok = false;
		try {
			String query = "SELECT COUNT(*) FROM EXA.ASPIRANTE WHERE FOLIO = ?";
			if (jdbcExamen.queryForObject(query,Integer.class, new Object[]{folio}) >= 1){
				ok = true;
			}
		} catch (Exception e) {
			System.out.println("Error - aca.podium.spring.AspiranteDao|existeAspirantePorFolio| "+e);
		}
		
		return ok;
	}

	public HashMap<String,Notas> mapaNotasPorFechaFolio(String fechaIni, String fechaFin) {
		List<String> lista = new ArrayList<String>();
		HashMap<String,Notas> mapa = new HashMap<String,Notas>(); 
		
		boolean pregrado = true;
		
		float resLog 	= 0;		
		float resMat 	= 0;		
		float resEsp 	= 0;		
		float resIng 	= 0;
		float resBio 	= 0;
		float resFis 	= 0;
		float resQui 	= 0;
		float resEnsayo = 0;
		
		try {
		String comando = "SELECT DISTINCT(FOLIO) FROM EXA.EXAMEN WHERE FOLIO IN(SELECT FOLIO FROM EXA.ASPIRANTE"
				+ " WHERE TO_DATE(TO_CHAR(FECHA,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')"
				+ " ORDER BY FOLIO) ORDER BY FOLIO";
		
			lista = jdbcExamen.queryForList(comando, String.class, new Object[]{fechaIni,fechaFin});
			
			for(String folio : lista) {
				String examenId = "0";
				Aspirante aspirante = this.buscaAspirantePorFolio(Integer.parseInt(folio));
				Notas notas = new Notas();
				
				if(aspirante.getGrado().equals("P")) {
					pregrado = false;
				}
				
				List<Examen> lisExamenes = examenDao.lisExamenesPorFolio(Integer.parseInt(folio), "ORDER BY FECHA DESC");
				
				if (examenId.equals("0") && lisExamenes.size() >= 1) {
					
					examenId = String.valueOf(lisExamenes.get(0).getId());
					
					resLog 	= examenAreaDao.getPuntosPorArea(Integer.parseInt(examenId), "1,15");		
					resMat 	= examenAreaDao.getPuntosPorArea(Integer.parseInt(examenId), "2,17");		
					resEsp 	= examenAreaDao.getPuntosPorArea(Integer.parseInt(examenId), "3,4,14,19");		
					resIng 	= examenAreaDao.getPuntosPorArea(Integer.parseInt(examenId), "5,6");
					resBio 	= examenAreaDao.getPuntosPorArea(Integer.parseInt(examenId), "7");
					resFis 	= examenAreaDao.getPuntosPorArea(Integer.parseInt(examenId), "8");
					resQui 	= examenAreaDao.getPuntosPorArea(Integer.parseInt(examenId), "9");
					resEnsayo = examenAreaDao.getPuntosPorArea(Integer.parseInt(examenId), "12,20");
				}
				
				if (pregrado) {
					if (lisExamenes.size() >= 1) {
						for(Examen examen : lisExamenes) {
							if(resLog <= examenAreaDao.getPuntosPorArea(examen.getId(), "1,15")) {
								resLog = examenAreaDao.getPuntosPorArea(examen.getId(), "1,15");		
							}
							if(resMat <= examenAreaDao.getPuntosPorArea(examen.getId(), "2,17")) {
								resMat 	= examenAreaDao.getPuntosPorArea(examen.getId(), "2,17");		
							}
							if(resEsp <= examenAreaDao.getPuntosPorArea(examen.getId(), "3,4,14,19")) {
								resEsp 	= examenAreaDao.getPuntosPorArea(examen.getId(), "3,4,14,19");		
							}
							if(resIng <= examenAreaDao.getPuntosPorArea(examen.getId(), "5,6")) {
								resIng 	= examenAreaDao.getPuntosPorArea(examen.getId(), "5,6");
							}
							if(resBio <= examenAreaDao.getPuntosPorArea(examen.getId(), "7")) {
								resBio 	= examenAreaDao.getPuntosPorArea(examen.getId(), "7");
							}
							if(resFis <= examenAreaDao.getPuntosPorArea(examen.getId(), "8")) {
								resFis 	= examenAreaDao.getPuntosPorArea(examen.getId(), "8");
							}
							if(resQui <= examenAreaDao.getPuntosPorArea(examen.getId(), "9")) {
								resQui 	= examenAreaDao.getPuntosPorArea(examen.getId(), "9");
							}
							if(resEnsayo <= examenAreaDao.getPuntosPorArea(examen.getId(), "12,20")) {
								resEnsayo = examenAreaDao.getPuntosPorArea(examen.getId(), "12,20");
							}
						}
					}else if (lisExamenes.size() == 1) {
						examenId = String.valueOf(lisExamenes.get(0).getId());
						
						resLog 	= examenAreaDao.getPuntosPorArea(Integer.parseInt(examenId), "1,15");		
						resMat 	= examenAreaDao.getPuntosPorArea(Integer.parseInt(examenId), "2,17");		
						resEsp 	= examenAreaDao.getPuntosPorArea(Integer.parseInt(examenId), "3,4,14,19");		
						resIng 	= examenAreaDao.getPuntosPorArea(Integer.parseInt(examenId), "5,6");
						resBio 	= examenAreaDao.getPuntosPorArea(Integer.parseInt(examenId), "7");
						resFis 	= examenAreaDao.getPuntosPorArea(Integer.parseInt(examenId), "8");
						resQui 	= examenAreaDao.getPuntosPorArea(Integer.parseInt(examenId), "9");
						resEnsayo = examenAreaDao.getPuntosPorArea(Integer.parseInt(examenId), "12,20");
					}
				}

				if(resEnsayo > 0) {
					resEsp = resEsp + resEnsayo;
				}
				
				notas.setFolio(folio);
				notas.setResLog(resLog);
				notas.setResMat(resMat);
				notas.setResEsp(resEsp);
				notas.setResIng(resIng);
				notas.setResBio(resBio);
				notas.setResFis(resFis);
				notas.setResQui(resQui);
				
				mapa.put(folio, notas);
			}
			
		} catch (Exception e) {
			System.out.println("Error - aca.podium.spring.AspiranteDao|mapaNotasPorFechaFolio| "+e);
		}
		
		return mapa;
	}
	
	public HashMap<String,Date> mapaFechaPorFolio(String fechaIni, String fechaFin) {
		HashMap<String,Date> mapa = new HashMap<String,Date>(); 
		List<String> lista = new ArrayList<String>();
		
		try {
			String comando = "SELECT DISTINCT(FOLIO) FROM EXA.EXAMEN WHERE FOLIO IN(SELECT FOLIO FROM EXA.ASPIRANTE"
					+ " WHERE TO_DATE(TO_CHAR(FECHA,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')"
					+ " ORDER BY FOLIO) ORDER BY FOLIO";
			
			lista = jdbcExamen.queryForList(comando, String.class, new Object[]{fechaIni,fechaFin});
			
			for(String folio : lista) {
				
				List<Examen> lisExamenes = examenDao.lisExamenesPorFolio(Integer.parseInt(folio), "ORDER BY FECHA ASC");
				
				for(Examen examen : lisExamenes) {
					mapa.put(folio, examen.getFecha());
				}
			}
			
		} catch (Exception e) {
			System.out.println("Error - aca.podium.spring.AspiranteDao|mapaFechaPorFolio| "+e);
		}
		
		return mapa;
	}
	
}
