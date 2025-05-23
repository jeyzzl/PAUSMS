package aca.alumno.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AlumReferenciaDao {
	
		@Autowired
		@Qualifier("jdbcEnoc")
		private JdbcTemplate enocJdbc;
		
	public boolean insertReg( AlumReferencia alumReferencia) {
		boolean ok = false;
		
		try{
			
			String comando = "INSERT INTO ENOC.ALUM_REFERENCIA"+ 
				"(CODIGO_PERSONAL, SCOTIABANK, BANAMEX, SANTANDER) "+
				"VALUES( ?, ?, ?, ?)";
			Object[] parametros = new Object[] {alumReferencia.getCodigoPersonal(), alumReferencia.getScotiabank(),
			alumReferencia.getBanamex(), alumReferencia.getSantander()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumReferenciaDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg( AlumReferencia alumReferencia) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.ALUM_REFERENCIA "+ 
				"SET "+
				"SCOTIABANK = ?, "+
				"BANAMEX = ?, "+
				"SANTANDER = ? "+
				"WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {alumReferencia.getScotiabank(), alumReferencia.getBanamex(),
			alumReferencia.getSantander(), alumReferencia.getCodigoPersonal()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumReferenciaDao|updateReg|:"+ex);		
		}
		return ok;
	}	
	
	public boolean deleteReg( String codigoPersonal) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.ALUM_REFERENCIA "+ 
				"WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumReferenciaDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public AlumReferencia mapeaRegId(  String codigoPersonal ) {
		AlumReferencia objeto = new AlumReferencia();
		
 		try{
	 		String comando = "SELECT * FROM ENOC.ALUM_REFERENCIA WHERE CODIGO_PERSONAL = ?"; 
	 		Object[] parametros = new Object[] {codigoPersonal};
			objeto = enocJdbc.queryForObject(comando, new AlumReferenciaMapper(), parametros);
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.spring.AlumReferenciaDao|mapeaRegId|:"+ex);
 		}
 		return objeto;
 	}
	
	public boolean existeReg( String codigoPersonal) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_REFERENCIA "+ 
				"WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumReferenciaDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public int generarReferenciaBanamex(String matricula){
		//String sucursal = "0870";
		//String cuenta = "0528570";
		int sumaSucursalCuenta = 1118;
		matricula = matricula.substring(1);
//----------------COMPLETAR CON CEROS---------------------
		/*if(sucursal.length()<4){
			String tmpSucursal = "";
			for(int i=4; i>sucursal.length(); i--) tmpSucursal+="0";
			sucursal = tmpSucursal;
		}
		if(cuenta.length()<7){
			String tmpCuenta = "";
			for(int i=7; i>cuenta.length(); i--) tmpCuenta+="0";
			cuenta = tmpCuenta;
		}*/
		sumaSucursalCuenta+=(Integer.parseInt(matricula.charAt(5)+"")*37);
		sumaSucursalCuenta+=(Integer.parseInt(matricula.charAt(4)+"")*31);
		sumaSucursalCuenta+=(Integer.parseInt(matricula.charAt(3)+"")*29);
		sumaSucursalCuenta+=(Integer.parseInt(matricula.charAt(2)+"")*23);
		sumaSucursalCuenta+=(Integer.parseInt(matricula.charAt(1)+"")*19);
		sumaSucursalCuenta+=(Integer.parseInt(matricula.charAt(0)+"")*17);
		return 99-(sumaSucursalCuenta%97);
	}
	
	public String invertirTexto(String matricula){
		StringBuilder textoInvertido = new StringBuilder();
		if (matricula.length()>0){
			for (int i=matricula.length()-1;i>=0;i--){
				textoInvertido.append(matricula.charAt(i));
			}
		}else{
			textoInvertido.append("");
		}
		return textoInvertido.toString();
	}
	
	public String generarReferenciaSantander(String matricula){
		
		String matInversa 		= invertirTexto(matricula);
		StringBuilder resultado = new StringBuilder();
		
		// Resultado de multiplicaciones
		for (int i=0; i < matInversa.length();i++){
			
			// Si es par multiplica por 2 e impar multiplica por 1  
			if ((i % 2)== 0) 
				resultado.append( String.valueOf(Integer.parseInt(matInversa.substring(i, i+1)) * 2));
			else
				resultado.append( String.valueOf(Integer.parseInt(matInversa.substring(i, i+1)) * 1));
		}		
		
		// Suma los números del resultado
		int suma = 0;
		for (int i=0; i < resultado.toString().length(); i++){
			suma += Integer.parseInt( resultado.toString().substring(i, i+1));
		}
		
		// Obtiene el digito verificador
		int digito = 10 - (suma % 10);
		if (digito == 10) digito = 0;
		
		return String.valueOf(digito);
	}	
	
	public List<AlumReferencia> getListAll( String orden) {
		
		List<AlumReferencia> lista= new ArrayList<AlumReferencia>();
		
		try{
			String comando = "SELECT * FROM ENOC.ALUM_REFERENCIA "+orden;
			lista = enocJdbc.query(comando, new AlumReferenciaMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumReferenciaDao|getListAll|:"+ex);
		}
		return lista;
	}
}