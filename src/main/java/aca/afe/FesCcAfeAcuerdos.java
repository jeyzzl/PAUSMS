package aca.afe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FesCcAfeAcuerdos {
	private String matricula;
	private String cargaId;
	private String bloque;
	private String tipoId;
	private String tipoNombre;
	private String tipoCuenta;
	private String tipoImporte;
	private String tipoAcuerdo;
	private String acuerdoFecha;
	private String acuerdoImpMatricula;
	private String acuerdoImpEnsenanza;
	private String acuerdoImpInternado;
	private String acuerdoVigencia;
	private String acuerdoFolio;
	private String acuerdoPromesa;
	private String acuerdoHoras;
	private String acuerdoEjercicioId;
	private String acuerdoCcostoId;
	private String alpuestoPuestoId;
	private String categoriaId;
	private String categoriaNombre;
	private String alpuestoFechaInical;
	private String alpuestoFechaFinal;
	private String alpuestoTipo;
	private String alpuestoTipoAdicional;
	private String totalBecaAdic;
	private String valor;
	private String id;
		
	// Constructor
	public FesCcAfeAcuerdos(){		
	    matricula 				= "";
	    cargaId					= "";
	    bloque					= "";
	    tipoId					= "";
	    tipoNombre				= "";
	    tipoCuenta				= "";
	    tipoImporte				= "";
	    tipoAcuerdo				= "";
	    acuerdoFecha			= "";
	    acuerdoImpMatricula		= "";
	    acuerdoImpEnsenanza	    = "";
	    acuerdoImpInternado		= "";
	    acuerdoVigencia			= "";
	    acuerdoFolio			= "";
	    acuerdoPromesa			= "";
	    acuerdoHoras			= "";
	    acuerdoEjercicioId		= "";
	    acuerdoCcostoId			= "";
	    alpuestoPuestoId		= "";
	    categoriaId				= "";
	    categoriaNombre			= "";
	    alpuestoFechaInical		= "";
	    alpuestoFechaFinal		= "";
	    alpuestoTipo			= "";
	    alpuestoTipoAdicional	= "";
	    totalBecaAdic			= "";
	    valor 					= "";
	    id 						= "0"; 
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getCargaId() {
		return cargaId;
	}

	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
	}

	public String getBloque() {
		return bloque;
	}

	public void setBloque(String bloque) {
		this.bloque = bloque;
	}

	public String getTipoId() {
		return tipoId;
	}

	public void setTipoId(String tipoId) {
		this.tipoId = tipoId;
	}

	public String getTipoNombre() {
		return tipoNombre;
	}

	public void setTipoNombre(String tipoNombre) {
		this.tipoNombre = tipoNombre;
	}

	public String getTipoCuenta() {
		return tipoCuenta;
	}

	public void setTipoCuenta(String tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	public String getTipoImporte() {
		return tipoImporte;
	}

	public void setTipoImporte(String tipoImporte) {
		this.tipoImporte = tipoImporte;
	}

	public String getTipoAcuerdo() {
		return tipoAcuerdo;
	}

	public void setTipoAcuerdo(String tipoAcuerdo) {
		this.tipoAcuerdo = tipoAcuerdo;
	}

	public String getAcuerdoFecha() {
		return acuerdoFecha;
	}

	public void setAcuerdoFecha(String acuerdoFecha) {
		this.acuerdoFecha = acuerdoFecha;
	}

	public String getAcuerdoImpMatricula() {
		return acuerdoImpMatricula;
	}

	public void setAcuerdoImpMatricula(String acuerdoImpMatricula) {
		this.acuerdoImpMatricula = acuerdoImpMatricula;
	}

	public String getAcuerdoImpEnsenanza() {
		return acuerdoImpEnsenanza;
	}

	public void setAcuerdoImpEnsenanza(String acuerdoImpEnsenanza) {
		this.acuerdoImpEnsenanza = acuerdoImpEnsenanza;
	}

	public String getAcuerdoImpInternado() {
		return acuerdoImpInternado;
	}

	public void setAcuerdoImpInternado(String acuerdoImpInternado) {
		this.acuerdoImpInternado = acuerdoImpInternado;
	}

	public String getAcuerdoVigencia() {
		return acuerdoVigencia;
	}

	public void setAcuerdoVigencia(String acuerdoVigencia) {
		this.acuerdoVigencia = acuerdoVigencia;
	}

	public String getAcuerdoFolio() {
		return acuerdoFolio;
	}

	public void setAcuerdoFolio(String acuerdoFolio) {
		this.acuerdoFolio = acuerdoFolio;
	}

	public String getAcuerdoPromesa() {
		return acuerdoPromesa;
	}

	public void setAcuerdoPromesa(String acuerdoPromesa) {
		this.acuerdoPromesa = acuerdoPromesa;
	}

	public String getAcuerdoHoras() {
		return acuerdoHoras;
	}

	public void setAcuerdoHoras(String acuerdoHoras) {
		this.acuerdoHoras = acuerdoHoras;
	}

	public String getAcuerdoEjercicioId() {
		return acuerdoEjercicioId;
	}

	public void setAcuerdoEjercicioId(String acuerdoEjercicioId) {
		this.acuerdoEjercicioId = acuerdoEjercicioId;
	}

	public String getAcuerdoCcostoId() {
		return acuerdoCcostoId;
	}

	public void setAcuerdoCcostoId(String acuerdoCcostoId) {
		this.acuerdoCcostoId = acuerdoCcostoId;
	}

	public String getAlpuestoPuestoId() {
		return alpuestoPuestoId;
	}

	public void setAlpuestoPuestoId(String alpuestoPuestoId) {
		this.alpuestoPuestoId = alpuestoPuestoId;
	}

	public String getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(String categoriaId) {
		this.categoriaId = categoriaId;
	}

	public String getCategoriaNombre() {
		return categoriaNombre;
	}

	public void setCategoriaNombre(String categoriaNombre) {
		this.categoriaNombre = categoriaNombre;
	}

	public String getAlpuestoFechaInical() {
		return alpuestoFechaInical;
	}

	public void setAlpuestoFechaInical(String alpuestoFechaInical) {
		this.alpuestoFechaInical = alpuestoFechaInical;
	}

	public String getAlpuestoFechaFinal() {
		return alpuestoFechaFinal;
	}

	public void setAlpuestoFechaFinal(String alpuestoFechaFinal) {
		this.alpuestoFechaFinal = alpuestoFechaFinal;
	}

	public String getAlpuestoTipo() {
		return alpuestoTipo;
	}

	public void setAlpuestoTipo(String alpuestoTipo) {
		this.alpuestoTipo = alpuestoTipo;
	}

	public String getTotalBecaAdic() {
		return totalBecaAdic;
	}

	public void setTotalBecaAdic(String totalBecaAdic) {
		this.totalBecaAdic = totalBecaAdic;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}	

	/**
	 * @return the alpuestoTipoAdicional
	 */
	public String getAlpuestoTipoAdicional() {
		return alpuestoTipoAdicional;
	}

	/**
	 * @param alpuestoTipoAdicional the alpuestoTipoAdicional to set
	 */
	public void setAlpuestoTipoAdicional(String alpuestoTipoAdicional) {
		this.alpuestoTipoAdicional = alpuestoTipoAdicional;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		matricula 				= rs.getString("MATRICULA");
		cargaId  			 	= rs.getString("CARGA_ID");
		bloque					= rs.getString("BLOQUE");
		tipoId					= rs.getString("TIPO_ID");
		tipoNombre				= rs.getString("TIPO_NOMBRE");
		tipoCuenta				= rs.getString("TIPO_CUENTA");
		tipoImporte    			= rs.getString("TIPO_IMPORTE");
		tipoAcuerdo				= rs.getString("TIPO_ACUERDO");
		acuerdoFecha   			= rs.getString("ACUERDO_FECHA");
		acuerdoImpMatricula		= rs.getString("ACUERDO_IMP_MATRICULA");
		acuerdoImpEnsenanza		= rs.getString("ACUERDO_IMP_ENSENANZA");
		acuerdoImpInternado 	= rs.getString("ACUERDO_IMP_INTERNADO");
		acuerdoVigencia			= rs.getString("ACUERDO_VIGENCIA");
	    acuerdoFolio			= rs.getString("ACUERDO_FOLIO");
	    acuerdoPromesa			= rs.getString("ACUERDO_PROMESA");
	    acuerdoHoras			= rs.getString("ACUERDO_HORAS");
	    acuerdoEjercicioId		= rs.getString("ACUERDO_EJERCICIO_ID");
	    acuerdoCcostoId			= rs.getString("ACUERDO_CCOSTO_ID");
	    alpuestoPuestoId		= rs.getString("ALPUESTO_PUESTO_ID");
	    categoriaId				= rs.getString("CATEGORIA_ID");
	    categoriaNombre			= rs.getString("CATEGORIA_NOMBRE");
	    alpuestoFechaInical		= rs.getString("ALPUESTO_FECHA_INICIAL");
	    alpuestoFechaFinal		= rs.getString("ALPUESTO_FECHA_FINAL");
	    alpuestoTipo			= rs.getString("ALPUESTO_TIPO");
	    totalBecaAdic			= rs.getString("TOTAL_BECA_ADIC");
	    valor 					= rs.getString("VALOR");
	    id 						= rs.getString("ID");
	}
	
	public void mapeaRegId(Connection con) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement(" SELECT MATRICULA, CARGA_ID, BLOQUE, TIPO_ID,	TIPO_NOMBRE,TIPO_CUENTA,TIPO_IMPORTE,TIPO_ACUERDO,"
					+ " ACUERDO_FECHA, ACUERDO_IMP_MATRICULA,	ACUERDO_IMP_ENSENANZA,ACUERDO_IMP_INTERNADO, ACUERDO_VIGENCIA, ACUERDO_FOLIO,"
					+ " ACUERDO_PROMESA, ACUERDO_HORAS, ACUERDO_EJERCICIO_ID, ACUERDO_CCOSTO_ID, ALPUESTO_PUESTO_ID, CATEGORIA_ID, CATEGORIA_NOMBRE,"
					+ " ALPUESTO_FECHA_INICIAL, ALPUESTO_FECHA_FINAL, ALPUESTO_TIPO, COALESCE(TOTAL_BECA_ADIC,0) AS TOTAL_BECA_ADIC, VALOR, ID"
					+ " FROM MATEO.FES_CC_AFE_ACUERDOS"
					+ " WHERE MATRICULA = ?"
					+ " AND ACUERDO_FOLIO = TO_NUMBER(?,'999')"
					+ " AND ALPUESTO_PUESTO_ID = ?");
			ps.setString(1, matricula);
			ps.setString(2, acuerdoFolio);
			ps.setString(3, alpuestoPuestoId);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.afe.FesCcAfeAcuerdosUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}


/*
 * ;
 * **/


}