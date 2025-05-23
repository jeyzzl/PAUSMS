package aca.dherst.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import aca.carga.spring.CargaAlumnoMapper;

@Component
public class DherstArchivoDao {

    @Autowired	
	@Qualifier("jdbcEnoc")	
	private JdbcTemplate enocJdbc;

    public boolean insertReg(DherstArchivo archivo){
		boolean ok = false;
		try{
			String comando = "INSERT INTO ENOC.DHERST_ARCHIVO(FOLIO, NOMBRE, FECHA, COMENTARIO, ARCHIVO, CODIGO_USUARIO) "
                           + "VALUES(?, ?, TO_DATE(?,'DD/MM/YYYY'), ?, ?, ?)";
			Object[] parametros = new Object[] {archivo.getFolio(), archivo.getNombre(), archivo.getFecha(), archivo.getComentario(), archivo.getArchivo(), archivo.getCodigoUsuario()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.dherst.spring.DherstArchivoDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg(DherstArchivo archivo) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.DHERST_ARCHIVO SET NOMBRE = ?, FECHA = TO_DATE(?,'DD/MM/YYYY'), COMENTARIO = ?, ARCHIVO = ?, CODIGO_USUARIO = ? WHERE FOLIO = ?";
			Object[] parametros = new Object[] {archivo.getNombre(), archivo.getFecha(), archivo.getComentario(), archivo.getArchivo(), archivo.getCodigoUsuario(), archivo.getFolio()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.dherst.spring.DherstArchivoDao|updateReg|:"+ex);		 
		}
		
		return ok;
	}	
	
	public boolean deleteReg(String id) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.DHERST_ARCHIVO WHERE FOLIO = ?";		
			Object[] parametros = new Object[] { id };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.dherst.spring.DherstArchivoDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public DherstArchivo mapeaRegId(String id){
		DherstArchivo dherst = new DherstArchivo();
		try{ 
			String comando = "SELECT COUNT(FOLIO) FROM ENOC.DHERST_ARCHIVO WHERE FOLIO = ?";
			Object[] parametros = new Object[] { id };
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>=1){				
				comando = "SELECT FOLIO, NOMBRE, FECHA, COMENTARIO, ARCHIVO, CODIGO_USUARIO FROM ENOC.DHERST_ARCHIVO WHERE FOLIO = ?";							
				dherst = enocJdbc.queryForObject(comando, new DherstArchivoMapper(), parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.dherst.spring.DherstArchivoDao|mapeaRegId|:"+ex);
		}
		
		return dherst;
	}

    public boolean existeReg(String id){
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.DHERST_ARCHIVO WHERE FOLIO = ?";
			Object[] parametros = new Object[] { id };
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>=1){											
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.dherst.spring.DherstArchivoDao|existeReg|:"+ex);
		}
		
		return ok;
	}

	public String maximoReg() {
		String maximo = "1";		
		try{
			String comando = "SELECT COALESCE(MAX(FOLIO)+1,1) FROM ENOC.DHERST_ARCHIVO";
			maximo = enocJdbc.queryForObject(comando, String.class);			
		}catch(Exception ex){
			System.out.println("Error - aca.dherst.spring.DherstArchivoDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}

	public List<DherstArchivo> getListAll( String orden ) {
		
		List<DherstArchivo> lista = new ArrayList<DherstArchivo>();		
		try{
			String comando = "SELECT FOLIO, NOMBRE, FECHA, COMENTARIO, ARCHIVO, CODIGO_USUARIO"
					+ " FROM ENOC.DHERST_ARCHIVO "+orden;
			lista = enocJdbc.query(comando, new DherstArchivoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.dherst.spring.DherstArchivoDao|getListAll|:"+ex);
		}
		return lista;
	}

	public HashMap<String, String> mapaAlumnosPorArchivo() {
		
		List<aca.Mapa> list 			= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String,String>();
		String comando = "";
		
		try{			
			comando = "SELECT FOLIO AS LLAVE, COUNT(DISTINCT(SLF_NO)) AS VALOR FROM DHERST_ALUMNO GROUP BY FOLIO";
			list = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa obj : list){
				mapa.put(obj.getLlave(), obj.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.dherst.spring.DherstArchivoDao|mapaAlumnosPorArchivo|:"+ex);
		}
		return mapa;
	}
}