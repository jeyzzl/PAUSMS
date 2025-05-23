package aca.attache.spring;

import java.beans.Customizer;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AttacheProductDao {

    @Autowired	
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;

    public boolean insertReg(AttacheProduct product){
		boolean ok = false;
		try{
			String comando = "INSERT INTO ENOC.ATTACHE_PRODUCT(LOCATION, PRODUCTGROUP, CODE, DESCRIPTION, GLSET, COMMENT_, SALESPRICE1, UNITCOST) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
			Object[] parametros = new Object[] {product.getLocation(), product.getProductgroup(), product.getCode(), product.getDescription(), product.getGlset(), product.getComment(), product.getSalesprice1(), product.getUnitcost()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheProductDao|insertReg|:"+ex);			
		}
		
		return ok;
	}

	public boolean insertBatch(List<AttacheProduct> products){
		boolean ok = false;
		try{
			String comando = "INSERT INTO ENOC.ATTACHE_PRODUCT(LOCATION, PRODUCTGROUP, CODE, DESCRIPTION, GLSET, COMMENT_, SALESPRICE1, UNITCOST) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

			enocJdbc.batchUpdate(comando, products, products.size(),
				(ps, product) -> {
					ps.setString(1, product.getLocation());
					ps.setString(2, product.getProductgroup());
					ps.setString(3, product.getCode());
					ps.setString(4, product.getDescription());
					ps.setString(5, product.getGlset());
					ps.setString(6, product.getComment());
					ps.setDouble(7, product.getSalesprice1());
					ps.setDouble(8, product.getUnitcost());
				}	
			);

			ok = true;
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheProductDao|insertBatch|:"+ex);			
		}
		
		return ok;
	}

    public boolean updateReg(AttacheProduct product) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.ATTACHE_PRODUCT SET LOCATION = ?, PRODUCTGROUP = ?, DESCRIPTION = ?, GLSET = ?, COMMENT_ = ?, SALESPRICE1 = ?, UNITCOST = ? WHERE CODE = ?";
			Object[] parametros = new Object[] {product.getLocation(), product.getProductgroup(), product.getDescription(), product.getGlset(), product.getComment(), product.getSalesprice1(), product.getUnitcost(), product.getCode()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheProductDao|updateReg|:"+ex);		 
		}
		
		return ok;
	}

    public boolean deleteReg(String code) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.ATTACHE_PRODUCT WHERE CODE = ?";		
			Object[] parametros = new Object[] { code };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheProductDao|deleteReg|:"+ex);			
		}
		return ok;
	}

	public boolean emptyTempTable() {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.ATTACHE_PRODUCT";		
			if (enocJdbc.update(comando)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheProductDao|emptyTempTable|:"+ex);			
		}
		return ok;
	}

	public boolean emptyTable() {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.ATTACHE_PRODUCT";		
			if (enocJdbc.update(comando)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheProductDao|emptyTable|:"+ex);			
		}
		return ok;
	}

	public AttacheProduct mapeaRegId(String code){
		AttacheProduct opcion = new AttacheProduct();
		try{ 
			String comando = "SELECT COUNT(CODE) FROM ENOC.ATTACHE_PRODUCT WHERE CODE = ?";
			Object[] parametros = new Object[] { code };
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>=1){				
				comando = "SELECT LOCATION, PRODUCTGROUP, CODE, DESCRIPTION, GLSET, COMMENT_, SALESPRICE1, UNITCOST FROM ENOC.ATTACHE_PRODUCT WHERE CODE = ?";							
				opcion = enocJdbc.queryForObject(comando, new AttacheProductMapper(), parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheProductDao|mapeaRegId|:"+ex);
		}
		
		return opcion;
	}

    public boolean existeReg(String code){
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ATTACHE_PRODUCT WHERE CODE = ?";
			Object[] parametros = new Object[] { code };
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>=1){											
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheProductDao|existeReg|:"+ex);
		}
		
		return ok;
	}

	public boolean copiarTabla() {
		boolean ok = false;
		try{
			String comando = "INSERT INTO ATTACHE_PRODUCT SELECT * FROM ATTACHE_PRODUCT";
			if(enocJdbc.update(comando) >= 1){
				ok = true;
			}
		}catch (Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheProductDao|copiarTabla|:" + ex);
		}
		return ok;
	}

	public boolean syncConceptPrices() {
		boolean ok = false;
		try{
			String comando = 	"UPDATE FIN_CONCEPT FC SET FC.UNIT_COST = ( "+
								"SELECT AP.SALESPRICE1 "+
								"FROM ATTACHE_PRODUCT AP "+
								"WHERE FC.CURSO_CLAVE = TRIM(AP.CODE) AND ROWNUM = 1 "+
								")WHERE EXISTS ( "+
								"SELECT 1 "+
								"FROM ATTACHE_PRODUCT AP "+
								"WHERE FC.CURSO_CLAVE = TRIM(AP.CODE)) "+
								"AND FC.TYPE = 'S'";
			if(enocJdbc.update(comando) >= 1){
				ok = true;
			}
		}catch (Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheProductDao|syncConceptPrices|:" + ex);
		}
		return ok;
	}

	public Double getSalesPrice(String code) {
		float salesPrice 			= 0;
		Double price				= 0.0;
		
		try{
			String comando = "SELECT SALESPRICE1 FROM ATTACHE_PRODUCT WHERE TRIM(CODE) = ? AND ROWNUM = 1"; 
			
			if (enocJdbc.queryForObject(comando, Float.class, code) >= 1) {
 				salesPrice = enocJdbc.queryForObject(comando, Float.class, code);
			}

			price = Double.parseDouble(String.valueOf(salesPrice));
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchDocAlumDao|getSalesPrice|:"+ex);
		}
		return price;
	}

	public String getDescription(String code) {
		String description				= "";
		
		try{
			String comando = "SELECT TRIM(DESCRIPTION) FROM ATTACHE_PRODUCT WHERE TRIM(CODE) = ? AND ROWNUM = 1"; 
			
			description = enocJdbc.queryForObject(comando, String.class, code);
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchDocAlumDao|getDescription|:"+ex);
		}
		return description;
	}

	public List<AttacheProduct> lisProductConcept(){
		List<AttacheProduct> lista	= new ArrayList<AttacheProduct>();	

		try{
			String comando ="SELECT * FROM ATTACHE_PRODUCT WHERE TRIM(CODE) IN (SELECT CURSO_CLAVE FROM FIN_CONCEPT WHERE TYPE = 'S')";	
			lista = enocJdbc.query(comando, new AttacheProductMapper());	
		}
		catch(Exception e){
			System.out.println("Error - aca.opcion.spring.AttacheProductDao|lisProductConcept|:"+e);
		}

		return lista;
	}
    
}
