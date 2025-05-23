package aca.federacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FedVotoM {
	private String eventoId;
	private String codigoPersonal;
	private String amigable;
	private String influenciaproactiva;
	private String honesto;
	private String responsable;
	private String proaccionmisionera;
	private String consagrado;
	private String atletico;
	private String actitudfitness;
	private String generoso;
	private String altruista;
	private String amigableH;
	private String influenciaproactivaH;
	private String honestoH;
	private String responsableH;
	private String proaccionmisioneraH;
	private String consagradoH;
	private String atleticoH;
	private String actitudfitnessH;
	private String generosoH;
	private String altruistaH;
	private String fecha;
	
	public FedVotoM(){
		eventoId			= "";
		codigoPersonal		= "";
		amigable 			= "";
		influenciaproactiva = "";
		honesto 			= "";
		responsable 		= "";
		proaccionmisionera 	= "";
		consagrado 			= "";
		atletico 			= ""; 
		actitudfitness 		= "";
		generoso 			= "";
		altruista 			= "";
		amigableH 			= "";
		influenciaproactivaH = "";
		honestoH 			= "";
		responsableH 		= "";
		proaccionmisioneraH = "";
		consagradoH 		= "";
		atleticoH 			= ""; 
		actitudfitnessH 	= "";
		generosoH 			= "";
		altruistaH 			= "";
		fecha				= "";
	}

	public String getEventoId() {
		return eventoId;
	}

	public void setEventoId(String eventoId) {
		this.eventoId = eventoId;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}
	
	public String getAmigable() {
		return amigable;
	}

	public void setAmigable(String amigable) {
		this.amigable = amigable;
	}

	public String getInfluenciaproactiva() {
		return influenciaproactiva;
	}

	public void setInfluenciaproactiva(String influenciaproactiva) {
		this.influenciaproactiva = influenciaproactiva;
	}

	public String getHonesto() {
		return honesto;
	}

	public void setHonesto(String honesto) {
		this.honesto = honesto;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public String getProaccionmisionera() {
		return proaccionmisionera;
	}

	public void setProaccionmisionera(String proaccionmisionera) {
		this.proaccionmisionera = proaccionmisionera;
	}

	public String getConsagrado() {
		return consagrado;
	}

	public void setConsagrado(String consagrado) {
		this.consagrado = consagrado;
	}

	public String getAtletico() {
		return atletico;
	}

	public void setAtletico(String atletico) {
		this.atletico = atletico;
	}

	public String getActitudfitness() {
		return actitudfitness;
	}

	public void setActitudfitness(String actitudfitness) {
		this.actitudfitness = actitudfitness;
	}

	public String getGeneroso() {
		return generoso;
	}

	public void setGeneroso(String generoso) {
		this.generoso = generoso;
	}

	public String getAltruista() {
		return altruista;
	}

	public void setAltruista(String altruista) {
		this.altruista = altruista;
	}
	
	public String getAmigableH() {
		return amigableH;
	}

	public void setAmigableH(String amigableH) {
		this.amigableH = amigableH;
	}

	public String getInfluenciaproactivaH() {
		return influenciaproactivaH;
	}

	public void setInfluenciaproactivaH(String influenciaproactivaH) {
		this.influenciaproactivaH = influenciaproactivaH;
	}

	public String getHonestoH() {
		return honestoH;
	}

	public void setHonestoH(String honestoH) {
		this.honestoH = honestoH;
	}

	public String getResponsableH() {
		return responsableH;
	}

	public void setResponsableH(String responsableH) {
		this.responsableH = responsableH;
	}

	public String getProaccionmisioneraH() {
		return proaccionmisioneraH;
	}

	public void setProaccionmisioneraH(String proaccionmisioneraH) {
		this.proaccionmisioneraH = proaccionmisioneraH;
	}

	public String getConsagradoH() {
		return consagradoH;
	}

	public void setConsagradoH(String consagradoH) {
		this.consagradoH = consagradoH;
	}

	public String getAtleticoH() {
		return atleticoH;
	}

	public void setAtleticoH(String atleticoH) {
		this.atleticoH = atleticoH;
	}

	public String getActitudfitnessH() {
		return actitudfitnessH;
	}

	public void setActitudfitnessH(String actitudfitnessH) {
		this.actitudfitnessH = actitudfitnessH;
	}

	public String getGenerosoH() {
		return generosoH;
	}

	public void setGenerosoH(String generosoH) {
		this.generosoH = generosoH;
	}

	public String getAltruistaH() {
		return altruistaH;
	}

	public void setAltruistaH(String altruistaH) {
		this.altruistaH = altruistaH;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	
	public boolean insertReg( Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.FED_VOTOM " + 
				"(EVENTO_ID,CODIGO_PERSONAL, AMIGABLE, INFLUENCIAPROACTIVA, HONESTO, RESPONSABLE, PROACCIONMISIONERA, CONSAGRADO, ATLETICO, ACTITUDFITNESS, GENEROSO, ALTRUISTA,"
				+ "AMIGABLEH, INFLUENCIAPROACTIVAH, HONESTOH, RESPONSABLEH, PROACCIONMISIONERAH, CONSAGRADOH, ATLETICOH, ACTITUDFITNESSH, GENEROSOH, ALTRUISTAH, FECHA ) " +
				"VALUES( TO_NUMBER(?,'99'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, TO_DATE(?,'DD/MM/YYYY') )");			
			
			ps.setString(1, eventoId);
			ps.setString(2, codigoPersonal);
			ps.setString(3, amigable);
			ps.setString(4, influenciaproactiva);
			ps.setString(5, honesto);
			ps.setString(6, responsable);
			ps.setString(7, proaccionmisionera);
			ps.setString(8, consagrado);
			ps.setString(9, atletico);
			ps.setString(10, actitudfitness);
			ps.setString(11, generoso);
			ps.setString(12, altruista);
			ps.setString(13, amigableH);
			ps.setString(14, influenciaproactivaH);
			ps.setString(15, honestoH);
			ps.setString(16, responsableH);
			ps.setString(17, proaccionmisioneraH);
			ps.setString(18, consagradoH);
			ps.setString(19, atleticoH);
			ps.setString(20, actitudfitnessH);
			ps.setString(21, generosoH);
			ps.setString(22, altruistaH);
			ps.setString(23, fecha);
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.FedVotoM|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg( Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.FED_VOTOM SET " + 
					"AMIGABLE 				= ?, " +
					"INFLUENCIAPROACTIVA 	= ?, " +
					"HONESTO 				= ?, " +
					"RESPONSABLE 			= ?, " +
					"PROACCIONMISIONERA 	= ?, " +
					"CONSAGRADO 			= ?, " +
					"ATLETICO 				= ?, " +
					"ACTITUDFITNESS 		= ?, " +
					"GENEROSO 				= ?, " +
					"ALTRUISTA 				= ?, " +
					"AMIGABLEH 				= ?, " +
					"INFLUENCIAPROACTIVAH 	= ?, " +
					"HONESTOH 				= ?, " +
					"RESPONSABLEH 			= ?, " +
					"PROACCIONMISIONERAH 	= ?, " +
					"CONSAGRADOH 			= ?, " +
					"ATLETICOH 				= ?, " +
					"ACTITUDFITNESSH 		= ?, " +
					"GENEROSOH 				= ?, " +
					"ALTRUISTAH				= ?, " +
					"FECHA 					= ?  " +
					"WHERE EVENTO_ID = TO_NUMBER(?,'99') " +
					"AND CODIGO_PERSONAL = ?");
		
			ps.setString(1, amigable);
			ps.setString(2, influenciaproactiva);
			ps.setString(3, honesto);
			ps.setString(4, responsable);
			ps.setString(5, proaccionmisionera);
			ps.setString(6, consagrado);
			ps.setString(7, atletico);
			ps.setString(8, actitudfitness);
			ps.setString(9, generoso);
			ps.setString(10, altruista);
			ps.setString(11, amigableH);
			ps.setString(12, influenciaproactivaH);
			ps.setString(13, honestoH);
			ps.setString(14, responsableH);
			ps.setString(15, proaccionmisioneraH);
			ps.setString(16, consagradoH);
			ps.setString(17, atleticoH);
			ps.setString(18, actitudfitnessH);
			ps.setString(19, generosoH);
			ps.setString(20, altruistaH);
			ps.setString(21, fecha);
			ps.setString(22, eventoId);
			ps.setString(23, codigoPersonal);

			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.FedVotoM|updateReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.FED_VOTOM "+ 
					"WHERE EVENTO_ID = TO_NUMBER(?,'99') " +
					"AND CODIGO_PERSONAL = ?");
			
			ps.setString(1, eventoId);
			ps.setString(2, codigoPersonal);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error  - aca.alumno.FedVotoM|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		eventoId				= rs.getString("EVENTO_ID");
		codigoPersonal			= rs.getString("CODIGO_PERSONAL");
		amigable 				= rs.getString("AMIGABLE");
		influenciaproactiva 	= rs.getString("INFLUENCIAPROACTIVA");
		honesto 				= rs.getString("HONESTO");
		responsable 			= rs.getString("RESPONSABLE");
		proaccionmisionera		= rs.getString("PROACCIONMISIONERA");
		consagrado 				= rs.getString("CONSAGRADO");
		atletico 				= rs.getString("ATLETICO"); 
		actitudfitness 			= rs.getString("ACTITUDFITNESS");
		generoso 				= rs.getString("GENEROSO");
		altruista 				= rs.getString("ALTRUISTA");
		amigableH 				= rs.getString("AMIGABLEH");
		influenciaproactivaH 	= rs.getString("INFLUENCIAPROACTIVAH");
		honestoH 				= rs.getString("HONESTOH");
		responsableH 			= rs.getString("RESPONSABLEH");
		proaccionmisioneraH		= rs.getString("PROACCIONMISIONERAH");
		consagradoH 			= rs.getString("CONSAGRADOH");
		atleticoH 				= rs.getString("ATLETICOH"); 
		actitudfitnessH 		= rs.getString("ACTITUDFITNESSH");
		generosoH 				= rs.getString("GENEROSOH");
		altruistaH				= rs.getString("ALTRUISTAH");
		fecha			= rs.getString("FECHA");
	}
	
	public void mapeaRegId( Connection conn, String nivelId, String modalidadId ) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT * "+				
				"FROM ENOC.FED_VOTOM " + 
				"WHERE EVENTO_ID = TO_NUMBER(?,'99') " +
				"AND CODIGO_PERSONAL = ? ");
			
			ps.setString(1, eventoId);
			ps.setString(2, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.FedVotoM|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}

	public static boolean existeReg(Connection conn, String eventoId, String codigoPersonal) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.FED_VOTOM " + 
					"WHERE EVENTO_ID = TO_NUMBER(?,'99') " +
					"AND CODIGO_PERSONAL = ?");
			
			ps.setString(1, eventoId);
			ps.setString(2, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.FedVotoM|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	
	public static String participantes(Connection conn, String eventoId) throws SQLException{
		String 		cantidad 	= "0";
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(*) AS CANTIDAD FROM ENOC.FED_VOTOM " + 
					"WHERE EVENTO_ID = TO_NUMBER(?,'99')");
			
			ps.setString(1, eventoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				cantidad = rs.getString("CANTIDAD");
			
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.FedVotoM|participantes|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return cantidad;
	}	
}