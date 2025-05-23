package aca.valida.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ValDocumentoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( ValDocumento documento ) {
		boolean ok = false;
		try{
			String comando = "INSERT INTO ENOC.VAL_DOCUMENTO(CLAVE, TIPO, FOLIO, CODIGO_PERSONAL)"
				+ " VALUES(?, ?, ?, ?)";			
			Object[] parametros = new Object[] {documento.getClave(), documento.getTipo(), documento.getFolio(), documento.getCodigoPersonal() };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.valida.ValDocumentoDao|insertReg|:"+documento.getClave()+":"+documento.getTipo()+":"+documento.getFolio()+":"+documento.getCodigoPersonal()+":"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg( ValDocumento documento ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.VAL_DOCUMENTO"
				+ " SET  TIPO = ?, FOLIO = ?, CODIGO_PERSONAL = ?"
				+ " WHERE CLAVE = ?";			
			Object[] parametros = new Object[] {documento.getTipo(), documento.getFolio(),  documento.getCodigoPersonal(), documento.getClave() };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.voto.spring.ValDocumentoDao|updateReg|:"+ex);		
		}
		return ok;
	}	
	
	public boolean deleteReg( String clave ) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.VAL_DOCUMENTO WHERE CLAVE = ?";			
			Object[] parametros = new Object[] {clave};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.voto.spring.ValDocumentoDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public ValDocumento mapeaRegId( String clave){		
		ValDocumento votoAlumno = new ValDocumento();		
		try{
			String comando = "SELECT CLAVE, TIPO, FOLIO, CODIGO_PERSONAL FROM ENOC.VAL_DOCUMENTO WHERE CLAVE = ?";
			Object[] parametros = new Object[] {clave};
			votoAlumno = enocJdbc.queryForObject(comando, new ValDocumentoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.voto.spring.ValDocumentoDao|mapeaRegId|:"+ex);
			ex.printStackTrace();		
		}		
		return votoAlumno;
	}
	
	public boolean existeReg( String clave){		
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.VAL_DOCUMENTO WHERE CLAVE = ?";
			Object[] parametros = new Object[] {clave};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.voto.spring.ValDocumentoDao|existeReg|:"+ex);		
		}		
		return ok;
	}	
	
	public List<ValDocumento> lisTodos( String orden ){		
		List<ValDocumento> lista = new ArrayList<ValDocumento>();		
		try{
			String comando = "SELECT CLAVE, TIPO, FOLIO, CODIGO_PERSONAL FROM ENOC.VAL_DOCUMENTO "+orden;
			lista = enocJdbc.query(comando, new ValDocumentoMapper());		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.ValDocumentoDao|lisTodos|:"+ex);
		}		
		return lista;
	}
	
	public HashMap<String,String> mapaPorTipo(String tipo){
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa = new HashMap<String,String>();		
		try {
			String comando = "SELECT FOLIO||CODIGO_PERSONAL AS LLAVE, CLAVE AS VALOR FROM ENOC.VAL_DOCUMENTO WHERE TIPO = ?"; 
			lista = enocJdbc.query(comando, new aca.MapaMapper(), tipo);			
			for(aca.Mapa opcion : lista) {				
				mapa.put(opcion.getLlave(), opcion.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.diploma.spring.DipAlumnoDao|mapaPorTipo|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaPorFolio(){
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa = new HashMap<String,String>();		
		try {
			String comando = "SELECT FOLIO AS LLAVE, COUNT(CLAVE) AS VALOR FROM ENOC.VAL_DOCUMENTO GROUP BY FOLIO"; 
			lista = enocJdbc.query(comando, new aca.MapaMapper());			
			for(aca.Mapa opcion : lista) {				
				mapa.put(opcion.getLlave(), opcion.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.diploma.spring.DipAlumnoDao|mapaPorFolio|:"+ex);
		}		
		return mapa;
	}
}