 // Bean de datos personales del alumno 
 package  aca.well;

 import java.io.IOException;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


 /**
 * @author 
 *
 */
public class WellAlimentacion{
 	private String verduras;
 	private String frutas;
 	private String cereales;
 	private String azucares;
 	private String leguminosas;
 	private String productosOrigen;
 	private String carnesVegetarianas;
 	private String aceites;
 	private String nueces;
 	private String alimentos;
 	private String codigoPersonal;
 
 	
 	public WellAlimentacion(){		
 		
 		verduras				= "";
		frutas					= "";
		cereales				= "";
		azucares				= "";
		leguminosas				= "";
		productosOrigen			= "";
		carnesVegetarianas		= "";
		aceites					= "";
		nueces					= "";
		alimentos				= "";
		codigoPersonal			= "";

 	}
 	
	public String getVerduras() {
		return verduras;
	}

	public void setVerduras(String verduras) {
		this.verduras = verduras;
	}

	public String getFrutas() {
		return frutas;
	}

	public void setFrutas(String frutas) {
		this.frutas = frutas;
	}

	public String getCereales() {
		return cereales;
	}

	public void setCereales(String cereales) {
		this.cereales = cereales;
	}

	public String getAzucares() {
		return azucares;
	}

	public void setAzucares(String azucares) {
		this.azucares = azucares;
	}

	public String getLeguminosas() {
		return leguminosas;
	}

	public void setLeguminosas(String leguminosas) {
		this.leguminosas = leguminosas;
	}

	public String getProductosOrigen() {
		return productosOrigen;
	}

	public void setProductosOrigen(String productosOrigen) {
		this.productosOrigen = productosOrigen;
	}

	public String getCarnesVegetarianas() {
		return carnesVegetarianas;
	}

	public void setCarnesVegetarianas(String carnesVegetarianas) {
		this.carnesVegetarianas = carnesVegetarianas;
	}

	public String getAceites() {
		return aceites;
	}

	public void setAceites(String aceites) {
		this.aceites = aceites;
	}

	public String getNueces() {
		return nueces;
	}

	public void setNueces(String nueces) {
		this.nueces = nueces;
	}

	public String getAlimentos() {
		return alimentos;
	}

	public void setAlimentos(String alimentos) {
		this.alimentos = alimentos;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public boolean insertReg(Connection conn ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("INSERT INTO ENOC.WELLNESS_ALIMENTACION"+ 
 				"(VERDURAS, FRUTAS, CEREALES, AZUCARES, LEGUMINOSAS, PRODUCTOS_ORIGEN, CARNES_VEGETARIANAS, ACEITES, NUECES, ALIMENTOS, CODIGO_PERSONAL) "+
 				"VALUES( ?,?,?,?,?,?,?,?,?,?,?)");
 			ps.setString(1, verduras);
 			ps.setString(2, frutas);
 			ps.setString(3, cereales);
 			ps.setString(4, azucares);
 			ps.setString(5, leguminosas);
 			ps.setString(6, productosOrigen);
 			ps.setString(7, carnesVegetarianas);
 			ps.setString(8, aceites);
 			ps.setString(9, nueces);
 			ps.setString(10, alimentos);
 			ps.setString(11, codigoPersonal);
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;				
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.WellnessAlimentcion|insertReg|:"+ex);			
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}	
 	
 	public boolean updateReg(Connection conn ) throws Exception{ 		
 		Statement st 		= conn.createStatement(); 		
 		String comando = "";
 		boolean ok = false;
 		
 		try{
 			comando = "UPDATE ENOC.WELLNESS_ALIMENTACION"+			 
 				" SET VERDURAS = ?,"
 				+ "FRUTAS = ?, "
 				+ "CEREALES = ?,"
 				+ "AZUCARES = ?,"
 				+ "LEGUMINOSAS = ?,"
 				+ "PRODUCTOS_ORIGEN = ?,"
 				+ "CARNES_VEGETARIANAS = ?,"
 				+ "ACEITES = ?,"
 				+ "NUECES = ?, "
 				+ "ALIMENTOS = ?,"
 				+ "CODIGO_PERSONAL = ?";
			if (st.executeUpdate(comando)==1){
				ok=true;
			}
			
 		}catch(Exception ex){
 			System.out.println("Error - aca.wellness.WellnessAlimentcion|updateReg|:"+ex);		
 		}finally{
 			try { st.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	} 
 	
 	public boolean deleteReg(Connection conn ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("DELETE FROM ENOC.WELLNESS_ALIMENTACION "+ 
 				"WHERE CODIGO_PERSONAL = ? ");
 			ps.setString(1, codigoPersonal);
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.wellness.WellnessAlimentcion|deleteReg|:"+ex);			
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		return ok;
 	}
 	
 	public void mapeaReg(ResultSet rs ) throws SQLException, IOException{
 		verduras				= rs.getString("VERDURAS");
		frutas					= rs.getString("FRUTAS");
		cereales				= rs.getString("CEREALES");
		azucares				= rs.getString("AZUCARES");
		leguminosas				= rs.getString("LEGUMINOSAS");
		productosOrigen			= rs.getString("PRODUCTOS_ORIGEN");
		carnesVegetarianas		= rs.getString("CARNES_VEGETARIANAS");
		aceites					= rs.getString("ACEITES");
		nueces					= rs.getString("NUECES");
		alimentos				= rs.getString("ALIMENTOS");
		codigoPersonal			= rs.getString("CODIGO_PERSONAL");
 	}
  	
 	public void mapeaRegId( Connection conn, String codigoId ) throws SQLException, IOException{
 		ResultSet rs = null;
 		PreparedStatement ps = null; 
 		try{
	 		ps = conn.prepareStatement("SELECT "+
	 			" VERDURAS, FRUTAS, CEREALES, AZUCARES, LEGUMINOSAS, PRODUCTOS_ORIGEN, CARNES_VEGETARIANAS, ACEITES, NUECES, ALIMENTOS, CODIGO_PERSONAL "+
	 			" FROM ENOC.WELLNESS_ALIMENTACION WHERE CODIGO_PERSONAL = ? "); 
	 		ps.setString(1, codigoPersonal);	
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			mapeaReg(rs);
	 		}
 		}catch(Exception ex){
			System.out.println("Error - aca.wellness.WellnessAlimentcion|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
 	}
	


 	public boolean existeReg(Connection conn) throws SQLException{
 		boolean 		ok 	= false;
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		
 		try{
 			ps = conn.prepareStatement("SELECT * FROM ENOC.WELLNESS_ALIMENTACION "+ 
 				"WHERE CODIGO_PERSONAL = ?");
 			ps.setString(1, codigoPersonal);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				ok = true;
 			else
 				ok = false;
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.wellness.WellnessAlimentcion|existeReg|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	} 	
 }