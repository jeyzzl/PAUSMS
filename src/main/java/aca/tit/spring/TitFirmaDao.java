package aca.tit.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TitFirmaDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(TitFirma firma) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.TIT_FIRMA (CODIGO_PERSONAL, INSTITUCION, NOMBRE, PRIMERAPELLIDO, SEGUNDOAPELLIDO, CURP, "
					+ " IDCARGO, CARGO, ABRTITULO, SELLO, CERTIFICADO, NUMEROCERTIFICADO)"
					+ " VALUES(?, ?, ?, ?, ?, ?, TO_NUMBER(?,'99'), ?, ?, ?, ?, ?)";
			
			Object[] parametros = new Object[] {
					firma.getCodigoPersonal(), firma.getInstitucion(), firma.getNombre(), firma.getPrimerApellido(),
					firma.getSegundoApellido(),	firma.getCurp(), firma.getIdCargo(), firma.getCargo(),
					firma.getAbrTitulo(), firma.getSello(),	firma.getCertificado(), firma.getNumeroCertificado()
					};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitFirmaDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg(TitFirma firma) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.TIT_FIRMA "
					+ " SET NOMBRE = ?, "
					+ " PRIMERAPELLIDO = ?, "
					+ " SEGUNDOAPELLIDO = ?, "
					+ " CURP = ?, "
					+ " IDCARGO = TO_NUMBER(?,'99'), "
					+ " CARGO = ?, "
					+ " ABRTITULO = ?, "
					+ " SELLO = ?, "
					+ " CERTIFICADO = ?, "
					+ " NUMEROCERTIFICADO = ?,"
					+ " CER = ?"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND INSTITUCION = ?";
			
			Object[] parametros = new Object[] {
					firma.getNombre(), firma.getPrimerApellido(), firma.getSegundoApellido(),
					firma.getCurp(), firma.getIdCargo(), firma.getCargo(), firma.getAbrTitulo(),
					firma.getSello(), firma.getCertificado(), firma.getNumeroCertificado(), firma.getCer(), 
					firma.getCodigoPersonal(), firma.getInstitucion()
					};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitFirmaDao|updateReg|:"+ex);		
		}
		return ok;
	}	
	
	public boolean deleteReg(String codigoPersonal ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.TIT_FIRMA WHERE CODIGO_PERSONAL = ? ";
			
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitFirmaDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public TitFirma mapeaRegId(String codigoPersonal, String institucion) {
		TitFirma alumno = new TitFirma();
		 
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, PRIMERAPELLIDO, SEGUNDOAPELLIDO,"
					+ " CURP, IDCARGO, CARGO, ABRTITULO, SELLO, CERTIFICADO, NUMEROCERTIFICADO, INSTITUCION"
					+ " FROM ENOC.TIT_FIRMA "
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND INSTITUCION = ?";
			
			Object[] parametros = new Object[] {codigoPersonal, institucion};
			alumno = enocJdbc.queryForObject(comando, new TitFirmaMapperCorto(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitFirmaDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return alumno;
		
	}	
	
	public int numFirmas( String institucion ) {
		int total = 0;		 
		try{
			String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.TIT_FIRMA WHERE INSTITUCION = ?";	
			total = enocJdbc.queryForObject(comando, Integer.class, institucion);			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitFirmaDao|mapeaRegId|:"+ex);
		}
		return total;
		
	}
	
	public boolean existeReg(String codigoPersonal, String institucion) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.TIT_FIRMA WHERE CODIGO_PERSONAL = ? AND INSTITUCION = ?"; 
			
			Object[] parametros = new Object[] {codigoPersonal, institucion};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitFirmaDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public List<TitFirma> lisAll( String orden) {
		List<TitFirma> lista		= new ArrayList<TitFirma>();
		String comando		= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, INSTITUCION, NOMBRE, PRIMERAPELLIDO, SEGUNDOAPELLIDO,"
					+ " CURP, IDCARGO, CARGO, ABRTITULO, SELLO, CERTIFICADO, NUMEROCERTIFICADO"
					+ " FROM ENOC.TIT_FIRMA "+orden;
			
			lista = enocJdbc.query(comando, new TitFirmaMapperCorto());	
			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitFirmaDao|listAll|:"+ex);
		}
		return lista;
	}
	
	public List<TitFirma> lisInstitucion( String institucion, String orden) {
		List<TitFirma> lista		= new ArrayList<TitFirma>();
		String comando		= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, INSTITUCION, NOMBRE, PRIMERAPELLIDO, SEGUNDOAPELLIDO, CURP, IDCARGO, CARGO, ABRTITULO, SELLO, CERTIFICADO, NUMEROCERTIFICADO"
					+ " FROM ENOC.TIT_FIRMA"
					+ " WHERE INSTITUCION = ? "+orden;
			Object[] parametros = new Object[] {institucion};
			lista = enocJdbc.query(comando, new TitFirmaMapperCorto(), parametros);	
			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitFirmaDao|lisInstitucion|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String, String> mapaCertificados( ) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		String comando	= "";
	
		try{
			comando = " SELECT INSTITUCION||CODIGO_PERSONAL AS LLAVE, 'S' AS VALOR"
					+ " FROM ENOC.TIT_FIRMA"
					+ " WHERE CER IS NOT NULL";
		
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitFirmaDao|mapaCertificados|:"+ex);
		}
		return mapa;
	}
	
}