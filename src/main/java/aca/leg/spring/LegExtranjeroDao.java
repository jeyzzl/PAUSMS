package aca.leg.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import aca.alumno.spring.AlumPersonalDao;

@Component
public class LegExtranjeroDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired	
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired
	aca.leg.spring.LegExtdoctosDao legExtDocDao;
	
	@Autowired
	aca.leg.spring.LegCondicionesDao legCondicionesDao;	
	
	public boolean insertReg( LegExtranjero leg) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.LEG_EXTRANJERO(CODIGO, RNE, CARRERA, COMENTARIO, TELEFONO)"
					+ " VALUES( ?, ?, ?, ?, ?)";
			Object[] parametros = new Object[] {leg.getCodigo(),leg.getRne(), leg.getCarrera(), leg.getComentario(), leg.getTelefono()};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.leg.spring.LegExtranjeroDao|insertReg|:"+ex);		
		}
		return ok;
	}	
	
	public boolean updateReg( LegExtranjero leg) {
		boolean ok = false;
		try{
			String comando = "UPDATE ENOC.LEG_EXTRANJERO SET RNE=?, CARRERA=?, COMENTARIO=?, TELEFONO=? WHERE CODIGO=?";
			Object[] parametros = new Object[] {leg.getRne(), leg.getCarrera(), leg.getComentario(), leg.getTelefono(), leg.getCodigo()};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.spring.LegExtranjeroDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	public boolean deleteReg( String codigo) {
		boolean ok = false;
		try{
			String comando = "DELETE FROM ENOC.LEG_EXTRANJERO WHERE CODIGO = ?"; 
			Object[] parametros = new Object[] {codigo};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.leg.spring.LegExtranjeroDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public LegExtranjero mapeaRegId( String codigo)	{
		LegExtranjero legExt = new LegExtranjero();
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.LEG_EXTRANJERO WHERE CODIGO = ?";
			Object[] parametros = new Object[] {codigo};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = " SELECT CODIGO, COALESCE(RNE,'-') AS RNE, COALESCE(CARRERA,'-') AS CARRERA, "
						+ " COALESCE(COMENTARIO,'-')AS COMENTARIO, COALESCE(TELEFONO,'-') AS TELEFONO "
						+ " FROM ENOC.LEG_EXTRANJERO"
						+ " WHERE CODIGO = ? ";
				legExt = enocJdbc.queryForObject(comando, new LegExtranjeroMapper(), parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.spring.LegExtranjeroDao|mapeaRegId|:"+ex);
		}
		return legExt;
	}
	
	public boolean existeReg( String codigo) {
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.LEG_EXTRANJERO WHERE CODIGO = ?";
			Object[] parametros = new Object[] {codigo};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.leg.spring.LegExtranjeroDao|existeReg|:"+ex);
		}	
		return ok;
	}
	
	
	public boolean autorizaExtranjero( String codigo) {		
		boolean autorizaExt = false;
		try{			
			if(!alumPersonalDao.getNacionalidad(codigo).equals("91")) {				
				List<String> lisGrupos 			= legCondicionesDao.lisGrupoId();
				List<LegExtdoctos> lisVigentes = legExtDocDao.listaDocVigentes(codigo);
				for(String grupo : lisGrupos) {
					List<String> lisDocumentos = legCondicionesDao.lisDocsGrupoId(grupo);
					int row=0;
					for (String doc : lisDocumentos){
						for (LegExtdoctos legDoc : lisVigentes) {
							if (legDoc.getIdDocumento().equals(doc)) {
								row++;
							}
						}
					}
					if (lisDocumentos.size() == row) {
						autorizaExt = true;
					}
				}
			}else {
				autorizaExt = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.spring.LegExtranjeroDao|autorizaExtranjero|:"+ex);
		}	
		return autorizaExt;
	}
	
	public List<LegExtranjero> lisInscritosModalidad(String modalidades, String orden){
		List<LegExtranjero> lista 			= new ArrayList<LegExtranjero>();		
		try{
			String comando="SELECT CODIGO, RNE, LIBRO, HOJA, CARRERA, COMENTARIO, TELEFONO" +
					" FROM ENOC.LEG_EXTRANJERO" + 
					" WHERE CODIGO IN (SELECT CODIGO_PERSONAL" +
									 " FROM ENOC.ALUM_ESTADO" + 
									 " WHERE MODALIDAD_ID IN ("+modalidades+") AND CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA" + 
									   					" WHERE TO_DATE(now(),'DD-MM-YY')"+
									   					" BETWEEN F_INICIO AND F_FINAL)) " +orden;
			lista = enocJdbc.query(comando, new LegExtranjeroMapper());
		}catch (Exception ex){
			System.out.println("Error - aca.leg.spring.LegExtranjeroDao|lisInscritosModalidad|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String, LegExtranjero> mapaExtranjeros(){
		HashMap<String, LegExtranjero> mapa = new HashMap<String, LegExtranjero>();
		List<LegExtranjero> lista	     = new ArrayList<LegExtranjero>();		
		try{
			String comando = "SELECT CODIGO, RNE, CARRERA, COMENTARIO, LIBRO, HOJA, TELEFONO FROM ENOC.LEG_EXTRANJERO";
			lista = enocJdbc.query(comando, new LegExtranjeroMapper());
			for (LegExtranjero ext : lista) {
				mapa.put(ext.getCodigo(), ext);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.spring.LegExtranjeroDao|mapaExtranjeros|:"+ex);
		}		
		return mapa;
	}
	
	
}