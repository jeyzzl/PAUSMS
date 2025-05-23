/*
 * Created on 09-feb-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package aca.bsc;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Carlos
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Indicadores {
	private String idItem;
	private String nombre;
	private String nbInicio;
	private String nbFinal;
	private String nmInicio;
	private String nmFinal;
	private String nsInicio;
	private String nsFinal;	
	public String getIdItem() {
		return idItem;
	}
	public void setIdItem(String idItem) {
		this.idItem = idItem;
	}
	public String getNbFinal() {
		return nbFinal;
	}
	public void setNbFinal(String nbFinal) {
		this.nbFinal = nbFinal;
	}
	public String getNbInicio() {
		return nbInicio;
	}
	public void setNbInicio(String nbInicio) {
		this.nbInicio = nbInicio;
	}
	public String getNmFinal() {
		return nmFinal;
	}
	public void setNmFinal(String nmFinal) {
		this.nmFinal = nmFinal;
	}
	public String getNmInicio() {
		return nmInicio;
	}
	public void setNmInicio(String nmInicio) {
		this.nmInicio = nmInicio;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNsFinal() {
		return nsFinal;
	}
	public void setNsFinal(String nsFinal) {
		this.nsFinal = nsFinal;
	}
	public String getNsInicio() {
		return nsInicio;
	}
	public void setNsInicio(String nsInicio) {
		this.nsInicio = nsInicio;
	}
	
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		idItem	 		= rs.getString("IDITEM");
		nombre			= rs.getString("NOMBRE");
		nbInicio		= rs.getString("NB_INICIO");
		nbFinal			= rs.getString("NB_FINAL");
		nmInicio		= rs.getString("NM_INICIO");
		nmFinal			= rs.getString("NM_FINAL");
		nsInicio		= rs.getString("NS_INICIO");
		nsFinal			= rs.getString("NS_FINAL");
	}
	
	public void mapeaRegId(Connection con, String idItem) throws SQLException{		
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT * FROM ENOC.BSC_INDICADORES WHERE IDITEM = ? "); 
			ps.setString(1,idItem);
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bsc.IndicadoresUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
}