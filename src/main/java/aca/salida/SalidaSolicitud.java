
package aca.salida;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SalidaSolicitud {
	private String salidaId;
	private String proposito;
	private String otroProposito;
	private String grupoId;
	private String fechaSalida;
	private String fechaLlegada;
	private String lugar;
	private String alimento;
	private String desayuno;
	private String comida;
	private String cena;
	private String hospedaje;
	private String transporte;
	private String usuario;
	private String fecha;
	private String responsable;
	private String autorizo;
	private String folio;
	private String total;
	private String totalPersona;
	private String comentario;
	private String estado;
	private String lugarSalida;
	private String preautorizo;
	
	public SalidaSolicitud(){
		salidaId    	= "";
		fecha			= "";
		proposito		= "";
		otroProposito	= "";
		grupoId			= "";
		fechaSalida		= "";
		fechaLlegada	= "";
		lugar			= "";
		alimento		= "0";
		desayuno		= "0";
		comida			= "0";
		cena			= "0";
		hospedaje		= "0";
		transporte		= "0";
		usuario			= "";
		responsable		= "";
		autorizo		= "";
		folio			= "";
		total			= "0";
		totalPersona	= "0";
		comentario		= "";
		estado			= "S";
		lugarSalida		= "";
		preautorizo 	= ""; 
		
	}
	
	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public String getAutorizo() {
		return autorizo;
	}

	public void setAutorizo(String autorizo) {
		this.autorizo = autorizo;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}
	
	public String getSalidaId() {
		return salidaId;
	}

	public void setSalidaId(String salidaId) {
		this.salidaId = salidaId;
	}

	public String getProposito() {
		return proposito;
	}

	public void setProposito(String proposito) {
		this.proposito = proposito;
	}

	public String getOtroProposito() {
		return otroProposito;
	}

	public void setOtroProposito(String otroProposito) {
		this.otroProposito = otroProposito;
	}

	public String getGrupoId() {
		return grupoId;
	}

	public void setGrupoId(String grupoId) {
		this.grupoId = grupoId;
	}

	public String getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(String fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public String getFechaLlegada() {
		return fechaLlegada;
	}

	public void setFechaLlegada(String fechaLlegada) {
		this.fechaLlegada = fechaLlegada;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public String getAlimento() {
		return alimento;
	}

	public void setAlimento(String alimento) {
		this.alimento = alimento;
	}

	public String getDesayuno() {
		return desayuno;
	}

	public void setDesayuno(String desayuno) {
		this.desayuno = desayuno;
	}

	public String getComida() {
		return comida;
	}

	public void setComida(String comida) {
		this.comida = comida;
	}

	public String getCena() {
		return cena;
	}

	public void setCena(String cena) {
		this.cena = cena;
	}

	public String getHospedaje() {
		return hospedaje;
	}

	public void setHospedaje(String hospedaje) {
		this.hospedaje = hospedaje;
	}

	public String getTransporte() {
		return transporte;
	}

	public void setTransporte(String transporte) {
		this.transporte = transporte;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getTotalPersona() {
		return totalPersona;
	}

	public void setTotalPersona(String totalPersona) {
		this.totalPersona = totalPersona;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	} 

	public String getLugarSalida() {
		return lugarSalida;
	}

	public void setLugarSalida(String lugarSalida) {
		this.lugarSalida = lugarSalida;
	}
	
	public String getPreautorizo() {
		return preautorizo;
	}

	public void setPreautorizo(String preautorizo) {
		this.preautorizo = preautorizo;
	}

	public boolean insertReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{			
			ps = conn.prepareStatement("INSERT INTO ENOC.SAL_SOLICITUD " + 
					"(SALIDA_ID, FECHA, PROPOSITO, OTRO_PROPOSITO, GRUPO_ID, FECHA_SALIDA," +
					" FECHA_LLEGADA, LUGAR, ALIMENTO, DESAYUNO, COMIDA, CENA, HOSPEDAJE, TRANSPORTE, USUARIO, RESPONSABLE, AUTORIZO, FOLIO, TOTAL, TOTAL_PERSONA, COMENTARIO, ESTADO, LUGAR_SALIDA, PREAUTORIZO)" +
					" VALUES(TO_NUMBER(?,'99999'),TO_DATE(?, 'DD/MM/YYYY'), ?, ?, TO_NUMBER(?,'99'), TO_DATE(?, 'DD/MM/YYYY HH24:MI:SS'), TO_DATE(?, 'DD/MM/YYYY HH24:MI:SS'),"+
					" ?, ?, TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), TO_NUMBER(?,'99999.99'), TO_NUMBER(?,'99999.99'), ?, ?, ?, ?, TO_NUMBER(?,'99999999.99'), TO_NUMBER(?,'99999.99'), ?, ?, ?, ?)");
			ps.setString(1, salidaId);
			ps.setString(2, fecha);
			ps.setString(3, proposito);
			ps.setString(4, otroProposito);
			ps.setString(5, grupoId);			
			ps.setString(6, fechaSalida);
			ps.setString(7, fechaLlegada);
			ps.setString(8, lugar);
			ps.setString(9, alimento);
			ps.setString(10, desayuno);
			ps.setString(11, comida);
			ps.setString(12, cena);
			ps.setString(13, hospedaje);
			ps.setString(14, transporte);
			ps.setString(15, usuario);
			ps.setString(16, responsable);
			ps.setString(17, autorizo);
			ps.setString(18, folio);
			ps.setString(19, total);
			ps.setString(20, totalPersona);
			ps.setString(21, comentario);
			ps.setString(22, estado);
			ps.setString(23, lugarSalida);
			ps.setString(24, preautorizo);
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.SalidaSolicitud|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn) throws SQLException{
        boolean ok = false;
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement(
                    " UPDATE ENOC.SAL_SOLICITUD " + 
                    " SET FECHA = TO_DATE(?, 'DD/MM/YYYY HH24:MI:SS')," +
                    " PROPOSITO = ?," +
                    " OTRO_PROPOSITO = ?," +
                    " GRUPO_ID= TO_NUMBER(?, '99')," +
                    " FECHA_SALIDA = TO_DATE(?, 'DD/MM/YYYY HH24:MI:SS')," +
                    " FECHA_LLEGADA = TO_DATE(?, 'DD/MM/YYYY HH24:MI:SS')," +
                    " LUGAR = ?," +
                    " ALIMENTO = ?," +
                    " DESAYUNO = TO_NUMBER(?, '999')," +
                    " COMIDA = TO_NUMBER(?, '999')," +
                    " CENA = TO_NUMBER(?, '999')," +
                    " HOSPEDAJE = TO_NUMBER(?, '9999999')," +
                    " TRANSPORTE = TO_NUMBER(?, '9999999')," +
                    " USUARIO = ?," +
                    " RESPONSABLE = ?," +
                    " AUTORIZO = ?," +
                    " FOLIO =  ?," +
                    " TOTAL = TO_NUMBER(?, '9999999999')," +
                    " TOTAL_PERSONA = TO_NUMBER(?, '9999999')," +
                    " COMENTARIO = ?," +
                    " ESTADO = ?," +
                    " LUGAR_SALIDA = ?,"+ 
                    " PREAUTORIZO = ?" +
                    " WHERE SALIDA_ID = TO_NUMBER(?,'99999')");
                    
            
            ps.setString(1, fecha);
            ps.setString(2, proposito);
            ps.setString(3, otroProposito);
            ps.setString(4, grupoId);
            ps.setString(5, fechaSalida);
            ps.setString(6, fechaLlegada);
            ps.setString(7, lugar);
            ps.setString(8, alimento);
            ps.setString(9, desayuno);
            ps.setString(10, comida);
            ps.setString(11, cena);
            ps.setString(12, hospedaje);
            ps.setString(13, transporte);
            ps.setString(14, usuario);
            ps.setString(15, responsable);
            ps.setString(16, autorizo);
            ps.setString(17, folio);
            ps.setString(18, total);
			ps.setString(19, totalPersona);
			ps.setString(20, comentario);
			ps.setString(21, estado);
			ps.setString(22, lugarSalida);
			ps.setString(23, preautorizo);
            ps.setString(24, salidaId);

            if(ps.executeUpdate() == 1){
                ok = true;
            }
        }catch (Exception ex){
            System.out.println("Error - aca.salida.SalidaSolicitud|updateReg|:" + ex);
        }finally{
			try { ps.close(); } catch (Exception ignore) { }
		}

        return ok;
    }
	
	public boolean updateRegAutorizo(Connection conn) throws SQLException{
        boolean ok = false;
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement(
                    " UPDATE ENOC.SAL_SOLICITUD " + 
                    " SET AUTORIZO = ? ," +
                    " FOLIO =  ?  " +
                    " WHERE SALIDA_ID = TO_NUMBER(?,'99999')");
     
            ps.setString(1, autorizo);
            ps.setString(2, folio);
            ps.setString(3, salidaId);

            if(ps.executeUpdate() == 1){
                ok = true;
            }
        }catch (Exception ex){
            System.out.println("Error - aca.salida.SalidaSolicitud|updateRegAutorizo|:" + ex);
        }finally{
			try { ps.close(); } catch (Exception ignore) { }
		}

        return ok;
    }
	
	public boolean updateRegPreautorizo(Connection conn) throws SQLException{
        boolean ok = false;
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement(
                    " UPDATE ENOC.SAL_SOLICITUD " + 
                    " SET PREAUTORIZO = ?," +
                    " FOLIO =  ?  " +
                    " WHERE SALIDA_ID = TO_NUMBER(?,'99999')");
     
            ps.setString(1, preautorizo);
            ps.setString(2, folio);
            ps.setString(3, salidaId);

            if(ps.executeUpdate() == 1){
                ok = true;
            }
        }catch (Exception ex){
            System.out.println("Error - aca.salida.SalidaSolicitud|updateRegPreautorizo|:" + ex);
        }finally{
			try { ps.close(); } catch (Exception ignore) { }
		}

        return ok;
    }

    public boolean deleteReg(Connection conn) throws SQLException{
        PreparedStatement ps = null;
    	boolean ok = false;

        try {
            ps = conn.prepareStatement(
                    " DELETE FROM ENOC.SAL_SOLICITUD " + 
                    " WHERE SALIDA_ID = TO_NUMBER(?, '99999')");                   
            
            ps.setString(1, salidaId);
                      
            if(ps.executeUpdate() == 1){
                ok = true;
            }
        }catch (Exception ex){
            System.out.println("Error - aca.salida.SalidaSolicitud|deleteReg|:" + ex);
        }finally{
			try { ps.close(); } catch (Exception ignore) { }
		}

        return ok;
    }

    public void mapeaReg(ResultSet rs) throws SQLException {
    	
		salidaId	    = rs.getString("SALIDA_ID");
		fecha          	= rs.getString("FECHA");
		proposito      	= rs.getString("PROPOSITO");
		otroProposito  	= rs.getString("OTRO_PROPOSITO");
		grupoId        	= rs.getString("GRUPO_ID");
		fechaSalida    	= rs.getString("FECHA_SALIDA");
		fechaLlegada   	= rs.getString("FECHA_LLEGADA");
		lugar          	= rs.getString("LUGAR");
		alimento        = rs.getString("ALIMENTO");
		desayuno       	= rs.getString("DESAYUNO");
		comida         	= rs.getString("COMIDA");
		cena           	= rs.getString("CENA");
		hospedaje      	= rs.getString("HOSPEDAJE");
		transporte     	= rs.getString("TRANSPORTE");
		usuario        	= rs.getString("USUARIO");
		responsable    	= rs.getString("RESPONSABLE");
		autorizo	   	= rs.getString("AUTORIZO");
		folio          	= rs.getString("FOLIO");
		total	   		= rs.getString("TOTAL");
		totalPersona    = rs.getString("TOTAL_PERSONA");
		comentario	    = rs.getString("COMENTARIO");
		estado		    = rs.getString("ESTADO");
		lugarSalida		= rs.getString("LUGAR_SALIDA");
		preautorizo 	= rs.getString("PREAUTORIZO");

    }
    
	
	public void mapeaRegId(Connection con, String salidaId) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{ 
			ps = con.prepareStatement("SELECT SALIDA_ID, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, PROPOSITO, OTRO_PROPOSITO," +
					" GRUPO_ID, TO_CHAR(FECHA_SALIDA, 'DD/MM/YYYY/HH24:MI:SS') AS FECHA_SALIDA, "+
					" TO_CHAR(FECHA_LLEGADA, 'DD/MM/YYYY/HH24:MI:SS') AS FECHA_LLEGADA, LUGAR, ALIMENTO, "+
					" DESAYUNO, COMIDA, CENA, HOSPEDAJE, TRANSPORTE, USUARIO,RESPONSABLE, AUTORIZO, FOLIO, TOTAL, "+
					" TOTAL_PERSONA, COMENTARIO, ESTADO, LUGAR_SALIDA, PREAUTORIZO" +
					" FROM ENOC.SAL_SOLICITUD " + 
					" WHERE SALIDA_ID = TO_NUMBER(?,'99999')");
			
			ps.setString(1, salidaId);		
			rs = ps.executeQuery();
		
			if(rs.next()){		
				mapeaReg(rs);		
			}
		}catch(Exception ex){
			System.out.println("Error - aca.salida.SalidaSolicitud|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean ok 				= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.SAL_SOLICITUD" + 
					" WHERE SALIDA_ID = TO_NUMBER(?,'99999')");
					
			ps.setString(1, salidaId);
			
			rs= ps.executeQuery();
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.salida.SalidaSolicitud|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(SALIDA_ID)+1, 1) AS MAXIMO FROM ENOC.SAL_SOLICITUD" ); 
					
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.SalidaSolicitud|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
}