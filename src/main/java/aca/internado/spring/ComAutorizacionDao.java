package aca.internado.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ComAutorizacionDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	

	public boolean insertReg(ComAutorizacion comAutorizacion ){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO NOE.COM_AUTORIZACION VALUES(?,?,?,TO_DATE(?,'DD/MM/YYYY'),TO_DATE(?,'DD/MM/YYYY'),?,?,?,?,?)";
			
			Object[] parametros = new Object[] {
				comAutorizacion.getMatricula(),Integer.parseInt(comAutorizacion.getNumComidas()),comAutorizacion.getTipoComida(),
				comAutorizacion.getFechaInicial(),comAutorizacion.getFechaFinal(),comAutorizacion.getUsuario(),comAutorizacion.getCliente(),
				comAutorizacion.getPaquete(),Integer.parseInt(comAutorizacion.getCargaId()),comAutorizacion.getBloque()
			};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error aca.internado.spring.ComAutorizacionDao|insertReg|: "+ex);
		}

		return ok;
	}
	public boolean updateReg(ComAutorizacion comAutorizacion ){
		boolean ok = false;
		
		try{
			String comando = "UPDATE NOE.COM_AUTORIZACION SET NUM_COMIDAS = ?, TIPO_COMIDA = ?,FECHA_INICIAL = TO_DATE(?,'DD/MM/YYYY')," +
					" FECHA_FINAL = TO_DATE(?,'DD/MM/YYYY'), USUARIO = ?, CLIENTE = ?, PAQUETE = ?"+
					" WHERE MATRICULA = ? AND CARGA_ID = ? AND BLOQUE = ?";	
			
			Object[] parametros = new Object[] {
				Integer.parseInt(comAutorizacion.getNumComidas()),comAutorizacion.getTipoComida(),comAutorizacion.getFechaInicial(),
				comAutorizacion.getFechaFinal(),comAutorizacion.getUsuario(),comAutorizacion.getCliente(),comAutorizacion.getPaquete(),
				comAutorizacion.getMatricula(),Integer.parseInt(comAutorizacion.getCargaId()),comAutorizacion.getBloque()
			};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error aca.internado.spring.ComAutorizacionDao|updateReg|: "+ex);
		}
		
		return ok;
	}
	
	public boolean deleteReg(String matricula, String cargaId, String bloque ){
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM NOE.COM_AUTORIZACION" +
					" WHERE MATRICULA = ? AND CARGA_ID = ? AND BLOQUE = ?";
			
			Object[] parametros = new Object[] { matricula, cargaId, bloque };
			
		 	if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error aca.internado.spring.ComAutorizacionDao|deleteReg|: "+ex);
		}

		return ok;
	}
	
	public ComAutorizacion mapeaRegId(String matricula, String cargaId, String bloque){
		ComAutorizacion comAutorizacion = new ComAutorizacion();
		
		try{ 
			String comando = "SELECT COUNT(*) FROM NOE.COM_AUTORIZACION WHERE MATRICULA = ? AND CARGA_ID = ? AND BLOQUE = ?";
			Object[] parametros = new Object[] {matricula, cargaId, bloque };
			
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				comando = "SELECT MATRICULA, NUM_COMIDAS," +
					" TIPO_COMIDA, TO_CHAR(FECHA_INICIAL, 'DD/MM/YYYY') AS FECHA_INICIAL," +
					" TO_CHAR(FECHA_FINAL, 'DD/MM/YYYY') AS FECHA_FINAL, USUARIO," +
					" CLIENTE, PAQUETE, CARGA_ID, BLOQUE" +
					" FROM NOE.COM_AUTORIZACION" +
					" WHERE MATRICULA = ?" +
					" AND CARGA_ID = ?" +
					" AND BLOQUE = ?";
			
				comAutorizacion = enocJdbc.queryForObject(comando, new ComAutorizacionMapper(),parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.ComAutorizacionDao|mapeaRegId|:"+ex);
		}

		return comAutorizacion;
	}
	
	public boolean existeReg(String matricula, String cargaId, String bloque){
		boolean ok = false;
		
		try{ 
			String comando = "SELECT COUNT(*) FROM NOE.COM_AUTORIZACION WHERE MATRICULA = ? AND CARGA_ID = ? AND BLOQUE = ?";
			
			Object[] parametros = new Object[] { matricula, cargaId, bloque };
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok=true;
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.ComAutorizacionDao|existeReg|:"+ex);
		}

		return ok;
	}
		
	public String numComidas(String matricula, String cargaId, String bloque){
		String numComidas = "0";
		
		try{ 
			String comando = "SELECT COALESCE(NUM_COMIDAS,0) NUM_COMIDAS FROM NOE.COM_AUTORIZACION"
					+ " WHERE MATRICULA = ? AND CARGA_ID = ? AND BLOQUE = ?";
			
			Object[] parametros = new Object[] { matricula, cargaId, bloque };
			numComidas = String.valueOf(enocJdbc.queryForObject(comando,Integer.class,parametros));
			
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.ComAutorizacionDao|numComidas|:"+ex);
		}

		return numComidas;
	}
	
	public List<ComAutorizacion> getListAll(String orden) {
		List<ComAutorizacion> lista = new ArrayList<ComAutorizacion>();

		try{
			String comando = "SELECT MATRICULA, NUM_COMIDAS, TIPO_COMIDA," +
					" TO_CHAR(FECHA_INICIAL, 'DD/MM/YYYY) AS FECHA_INICIAL," +
					" TO_CHAR(FECHA_FINAL, 'DD/MM/YYYY) AS FECHA_FINAL," +
					" USUARIO, CLIENTE, PAQUETE, CARGA_ID, BLOQUE" +
					" FROM NOE.COM_AUTORIZACION" +
					" WHERE MATRICULA IN (SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS) "+orden;	
			
			lista = enocJdbc.query(comando, new ComAutorizacionMapper());
			
		}catch(Exception ex){
			System.out.println("Error  - aca.internado.spring.ComAutorizacionDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public List<ComAutorizacion> getListDormi(String dormitorio, String orden) {
		List<ComAutorizacion> lisComidas = new ArrayList<ComAutorizacion>();

		try{
			String comando = "SELECT MATRICULA, NUM_COMIDAS, TIPO_COMIDA,"
				+ " TO_CHAR(FECHA_INICIAL, 'DD/MM/YYYY') AS FECHA_INICIAL,"
				+ " TO_CHAR(FECHA_FINAL, 'DD/MM/YYYY') AS FECHA_FINAL,"
				+ " USUARIO, CLIENTE, PAQUETE, CARGA_ID, BLOQUE"
				+ " FROM NOE.COM_AUTORIZACION"
				+ " WHERE ALUM_DORMITORIO(MATRICULA) = ?"
				+ " AND MATRICULA IN "
				+ " (SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS WHERE MODALIDAD_ID IN (1,2,3,4) AND RESIDENCIA_ID = 'I')"
				+ " AND CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE now() BETWEEN F_INICIO AND F_FINAL ) "+orden;			
			lisComidas = enocJdbc.query(comando, new ComAutorizacionMapper(), dormitorio);
			
		}catch(Exception ex){
			System.out.println("Error  - aca.internado.spring.ComAutorizacionDao|getListDormi|:"+ex);
		}
		
		return lisComidas;
	}
	
	public HashMap<String,String> mapComidasAlumno(String cargas ){
		HashMap<String,String> map	= new HashMap<String,String>();
		List<aca.Mapa> lista					= new ArrayList<aca.Mapa>();
		
		try{
			String comando = " SELECT MATRICULA||CARGA_ID||BLOQUE AS LLAVE, NUM_COMIDAS AS VALOR"
					+ " FROM NOE.COM_AUTORIZACION WHERE CARGA_ID IN ('"+cargas+"')";
			
			lista = enocJdbc.query(comando, new aca.MapaMapper());			
			for(aca.Mapa mapa : lista){
				map.put(mapa.getLlave(), mapa.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.ComAutorizacionDao|mapComidasAlumno|:"+ex);
		}
		
		return map;
	}
	
	public HashMap<String,ComAutorizacion> mapComidasVigentes(){
		HashMap<String,ComAutorizacion> map	= new HashMap<String,ComAutorizacion>();
		List<ComAutorizacion> lista		= new ArrayList<ComAutorizacion>();		
		try{
			String comando = " SELECT MATRICULA, NUM_COMIDAS, TIPO_COMIDA, TO_CHAR(FECHA_INICIAL, 'DD/MM/YYYY') AS FECHA_INICIAL, TO_CHAR(FECHA_FINAL, 'DD/MM/YYYY') AS FECHA_FINAL,"
				+ " USUARIO, CLIENTE, PAQUETE, CARGA_ID, BLOQUE"
				+ " FROM NOE.COM_AUTORIZACION WHERE TO_DATE(TO_CHAR(SYSDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN NOE.COM_AUTORIZACION.FECHA_INICIAL AND NOE.COM_AUTORIZACION.FECHA_FINAL";			
			lista = enocJdbc.query(comando, new ComAutorizacionMapper());			
			for(ComAutorizacion comida : lista){
				map.put(comida.getMatricula(), comida);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.ComAutorizacionDao|mapComidasAlumno|:"+ex);
		}		
		return map;
	}
}
