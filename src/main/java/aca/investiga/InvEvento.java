package aca.investiga;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * @author Alvin
 *
 */
public class InvEvento {
	private String codigoPersonal;
	private String folio;
	private String proyectoId;
	private String fechaSolicitud;
	private String fechaInicio;
	private String lugar;
	private String tipoEvento;
	private String dias;
	private String nombreEvento;
	private String participa;
	private String tipoBeca;
	private String alumnos;
	private String hospedaje;
	private String transporte;
	private String viaticos;
	private String gastos;
	private String descripcion;
	private String estado;

	public InvEvento(){
		codigoPersonal		= "";
		folio				= "";
		proyectoId			= "";
		fechaSolicitud		= "";
		fechaInicio			= "";
		lugar				= "";
		tipoEvento			= "";
		dias				= "";
		nombreEvento		= "";
		participa			= "";
		tipoBeca			= "";
		alumnos				= "";
		hospedaje			= "";
		transporte			= "";
		viaticos			= "";
		gastos				= "";
		descripcion			= "";
		estado				= "";
	}
		
	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getProyectoId() {
		return proyectoId;
	}

	public void setProyectoId(String proyectoId) {
		this.proyectoId = proyectoId;
	}

	public String getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(String fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public String getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public String getDias() {
		return dias;
	}

	public void setDias(String dias) {
		this.dias = dias;
	}

	public String getNombreEvento() {
		return nombreEvento;
	}

	public void setNombreEvento(String nombreEvento) {
		this.nombreEvento = nombreEvento;
	}

	public String getParticipa() {
		return participa;
	}

	public void setParticipa(String participa) {
		this.participa = participa;
	}

	public String getTipoBeca() {
		return tipoBeca;
	}

	public void setTipoBeca(String tipoBeca) {
		this.tipoBeca = tipoBeca;
	}

	public String getAlumnos() {
		return alumnos;
	}

	public void setAlumnos(String alumnos) {
		this.alumnos = alumnos;
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

	public String getViaticos() {
		return viaticos;
	}

	public void setViaticos(String viaticos) {
		this.viaticos = viaticos;
	}

	public String getGastos() {
		return gastos;
	}

	public void setGastos(String gastos) {
		this.gastos = gastos;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		
		codigoPersonal		= rs.getString("CODIGO_PERSONAL");
		folio				= rs.getString("FOLIO");
		proyectoId			= rs.getString("PROYECTO_ID");
		fechaSolicitud		= rs.getString("FECHA_SOLICITUD");
		fechaInicio			= rs.getString("FECHA_INICIO");
		lugar				= rs.getString("LUGAR");
		tipoEvento			= rs.getString("TIPO_EVENTO");
		dias				= rs.getString("DIAS");
		nombreEvento		= rs.getString("NOMBRE_EVENTO");
		participa			= rs.getString("PARTICIPA");
		tipoBeca			= rs.getString("TIPO_BECA");
		alumnos				= rs.getString("ALUMNOS");
		hospedaje			= rs.getString("HOSPEDAJE");
		transporte			= rs.getString("TRANSPORTE");
		viaticos			= rs.getString("VIATICOS");
		gastos				= rs.getString("GASTOS");
		descripcion			= rs.getString("DESCRIPCION");
		estado				= rs.getString("ESTADO");

	}
	
	public InvEvento mapeaRegId(Connection con, String codigoPersonal, String folio) throws SQLException{
		
		InvEvento evento = new InvEvento();
		PreparedStatement ps = null;
		ResultSet rs = null;	
		try{ 
			ps = con.prepareStatement("SELECT CODIGO_PERSONAL, FOLIO, PROYECTO_ID, TO_CHAR(FECHA_SOLICITUD,'DD/MM/YYYY') AS FECHA_SOLICITUD,"
					+ " TO_CHAR(FECHA_INICIO,'DD/MM/YYYY') AS FECHA_INICIO, LUGAR, TIPO_EVENTO, DIAS, NOMBRE_EVENTO, PARTICIPA, TIPO_BECA, "
					+ " ALUMNOS, HOSPEDAJE, TRANSPORTE, VIATICOS, GASTOS, DESCRIPCION, ESTADO "
					+ " FROM ENOC.INV_EVENTO WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'999')"); 
			ps.setString(1, codigoPersonal);
			ps.setString(2, folio);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				evento.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvEvento|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return evento;
	}

}