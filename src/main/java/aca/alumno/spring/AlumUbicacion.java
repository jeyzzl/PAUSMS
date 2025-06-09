// Bean de datos personales del alumno
package  aca.alumno.spring;

import java.sql.*;

public class AlumUbicacion{
	private String codigoPersonal;
	private String pNombre;
	private String pReligion;
	private String pNacionalidad;
	private String mNombre;
	private String mReligion;
	private String mNacionalidad;
	private String tNombre;
	private String tDireccion;
	private String tColonia;
	private String tCodigo;
	private String tApartado;
	private String tTelefono;
	private String tEmail;
	private String tPais;
	private String tEstado;
	private String tCiudad;
	private String tCelular;
	private String tComunica;
	private String tEdoCd;
	private String codigoPadre;
	private String codigoMadre;
	private String fecha;
	private String vacuna;
	private String descripcionVacuna;
	private String recogidaId;
	private String pPais;
	private String pEstado;
	private String pCiudad;
	private String pOrigen;
	private String mPais;
	private String mEstado;
	private String mCiudad;
	private String mOrigen;
	
	public AlumUbicacion(){
		codigoPersonal		= "";
		pNombre				= "-";
		pReligion			= "1";
		pNacionalidad		= "";
		mNombre				= "-";
		mReligion			= "1";
		mNacionalidad		= "";
		tNombre 			= "";
		tDireccion			= "";
		tColonia			= "";
		tCodigo				= "";
		tApartado			= "";
		tTelefono			= "";
		tEmail				= "";
		tPais				= "";
		tEstado				= "";
		tCiudad				= "";
		tCelular			= "";
		tComunica			= "";
		tEdoCd				= "";
		codigoPadre			= "";
		codigoMadre			= "";
		fecha				= "";
		vacuna				= "";
		descripcionVacuna	= "";
		recogidaId			= "";	
		pPais				= "";
		pEstado				= "";
		pCiudad				= "";
		pOrigen				= "";
		mPais				= "";
		mEstado				= "";
		mCiudad				= "";
		mOrigen				= "";
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getpNombre() {
		return pNombre;
	}

	public void setpNombre(String pNombre) {
		this.pNombre = pNombre;
	}

	public String getpReligion() {
		return pReligion;
	}

	public void setpReligion(String pReligion) {
		this.pReligion = pReligion;
	}

	public String getpNacionalidad() {
		return pNacionalidad;
	}

	public void setpNacionalidad(String pNacionalidad) {
		this.pNacionalidad = pNacionalidad;
	}

	public String getmNombre() {
		return mNombre;
	}

	public void setmNombre(String mNombre) {
		this.mNombre = mNombre;
	}

	public String getmReligion() {
		return mReligion;
	}

	public void setmReligion(String mReligion) {
		this.mReligion = mReligion;
	}

	public String getmNacionalidad() {
		return mNacionalidad;
	}

	public void setmNacionalidad(String mNacionalidad) {
		this.mNacionalidad = mNacionalidad;
	}

	public String gettNombre() {
		return tNombre;
	}

	public void settNombre(String tNombre) {
		this.tNombre = tNombre;
	}

	public String gettDireccion() {
		return tDireccion;
	}

	public void settDireccion(String tDireccion) {
		this.tDireccion = tDireccion;
	}

	public String gettColonia() {
		return tColonia;
	}

	public void settColonia(String tColonia) {
		this.tColonia = tColonia;
	}

	public String gettCodigo() {
		return tCodigo;
	}

	public void settCodigo(String tCodigo) {
		this.tCodigo = tCodigo;
	}

	public String gettApartado() {
		return tApartado;
	}

	public void settApartado(String tApartado) {
		this.tApartado = tApartado;
	}

	public String gettTelefono() {
		return tTelefono;
	}

	public void settTelefono(String tTelefono) {
		this.tTelefono = tTelefono;
	}

	public String gettEmail() {
		return tEmail;
	}

	public void settEmail(String tEmail) {
		this.tEmail = tEmail;
	}

	public String gettPais() {
		return tPais;
	}

	public void settPais(String tPais) {
		this.tPais = tPais;
	}

	public String gettEstado() {
		return tEstado;
	}

	public void settEstado(String tEstado) {
		this.tEstado = tEstado;
	}

	public String gettCiudad() {
		return tCiudad;
	}

	public void settCiudad(String tCiudad) {
		this.tCiudad = tCiudad;
	}

	public String gettCelular() {
		return tCelular;
	}

	public void settCelular(String tCelular) {
		this.tCelular = tCelular;
	}

	public String gettComunica() {
		return tComunica;
	}

	public void settComunica(String tComunica) {
		this.tComunica = tComunica;
	}

	public String gettEdoCd() {
		return tEdoCd;
	}

	public void settEdoCd(String tEdoCd) {
		this.tEdoCd = tEdoCd;
	}

	public String getCodigoPadre() {
		return codigoPadre;
	}

	public void setCodigoPadre(String codigoPadre) {
		this.codigoPadre = codigoPadre;
	}

	public String getCodigoMadre() {
		return codigoMadre;
	}

	public void setCodigoMadre(String codigoMadre) {
		this.codigoMadre = codigoMadre;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getVacuna() {
		return vacuna;
	}

	public void setVacuna(String vacuna) {
		this.vacuna = vacuna;
	}

	public String getDescripcionVacuna() {
		return descripcionVacuna;
	}

	public void setDescripcionVacuna(String descripcionVacuna) {
		this.descripcionVacuna = descripcionVacuna;
	}

	public String getRecogidaId() {
		return recogidaId;
	}

	public void setRecogidaId(String recogidaId) {
		this.recogidaId = recogidaId;
	}

	public String getpPais() {
		return pPais;
	}
	public void setpPais(String pPais) {
		this.pPais = pPais;
	}

	public String getpEstado() {
		return pEstado;
	}
	public void setpEstado(String pEstado) {
		this.pEstado = pEstado;
	}

	public String getpCiudad() {
		return pCiudad;
	}
	public void setpCiudad(String pCiudad) {
		this.pCiudad = pCiudad;
	}

	public String getpOrigen() {
		return pOrigen;
	}
	public void setpOrigen(String pOrigen) {
		this.pOrigen = pOrigen;
	}

	public String getmPais() {
		return mPais;
	}
	public void setmPais(String mPais) {
		this.mPais = mPais;
	}

	public String getmEstado() {
		return mEstado;
	}
	public void setmEstado(String mEstado) {
		this.mEstado = mEstado;
	}

	public String getmCiudad() {
		return mCiudad;
	}
	public void setmCiudad(String mCiudad) {
		this.mCiudad = mCiudad;
	}

	public String getmOrigen() {
		return mOrigen;
	}
	public void setmOrigen(String mOrigen) {
		this.mOrigen = mOrigen;
	}

}