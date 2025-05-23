/**
 * 
 */
package aca.catalogo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Elifo
 *
 */
public class CatAptitud {
	private String fuerzam;
	private String fuerzaf;
	private String flexibilidadm;
	private String flexibilidadf;
	private String resistenciam;
	private String resistenciaf;
	private String cardiom;
	private String cardiof;
	private String imcMinBajom;
	private String imcMinBajof;
	private String imcMaxBajom;
	private String imcMaxBajof;
	private String imcMinNormalm;
	private String imcMinNormalf;
	private String imcMaxNormalm;
	private String imcMaxNormalf;
	private String imcMinSobrem;
	private String imcMinSobref;
	private String imcMaxSobrem;
	private String imcMaxSobref;
	private String imcMinObeso1m;
	private String imcMinObeso1f;
	private String imcMaxObeso1m;
	private String imcMaxObeso1f;
	private String imcMinObeso2m;
	private String imcMinObeso2f;
	private String imcMaxObeso2m;
	private String imcMaxObeso2f;
	private String imcMinObeso3m;
	private String imcMinObeso3f;
	private String imcMaxObeso3m;
	private String imcMaxObeso3f;
	private String grasaMinAceptablem;
	private String grasaMinAceptablef;
	private String grasaMaxAceptablem;
	private String grasaMaxAceptablef;
	private String grasaMinSobrem;
	private String grasaMinSobref;
	private String grasaMaxSobrem;
	private String grasaMaxSobref;
	private String grasaMinObesom;
	private String grasaMinObesof;
	private String grasaMaxObesom;
	private String grasaMaxObesof;
	private String abdomenRiesgom;
	private String abdomenRiesgof;
	
	public CatAptitud(){
		fuerzam				= "";
		fuerzaf				= "";
		flexibilidadm		= "";
		flexibilidadf		= "";
		resistenciam		= "";
		resistenciaf		= "";
		cardiom				= "";
		cardiof				= "";
		imcMinBajom			= "";
		imcMinBajof			= "";
		imcMaxBajom			= "";
		imcMaxBajof			= "";
		imcMinNormalm		= "";
		imcMinNormalf		= "";
		imcMaxNormalm		= "";
		imcMaxNormalf		= "";
		imcMinSobrem		= "";
		imcMinSobref		= "";
		imcMaxSobrem		= "";
		imcMaxSobref		= "";
		imcMinObeso1m		= "";
		imcMinObeso1f		= "";
		imcMaxObeso1m		= "";
		imcMaxObeso1f		= "";
		imcMinObeso2m			= "";
		imcMinObeso2f		= "";
		imcMaxObeso2m		= "";
		imcMaxObeso2f		= "";
		imcMinObeso3m		= "";
		imcMinObeso3f		= "";
		imcMaxObeso3m		= "";
		imcMaxObeso3f		= "";
		grasaMinAceptablem	= "";
		grasaMinAceptablef	= "";
		grasaMaxAceptablem	= "";
		grasaMaxAceptablef	= "";
		grasaMinSobrem		= "";
		grasaMinSobref		= "";
		grasaMaxSobrem		= "";
		grasaMaxSobref		= "";
		grasaMinObesom		= "";
		grasaMinObesof		= "";
		grasaMaxObesom		= "";
		grasaMaxObesof		= "";
		abdomenRiesgom		= "";
		abdomenRiesgof		= "";
	}

	/**
	 * @return the abdomenRiesgof
	 */
	public String getAbdomenRiesgof() {
		return abdomenRiesgof;
	}

	/**
	 * @param abdomenRiesgof the abdomenRiesgof to set
	 */
	public void setAbdomenRiesgof(String abdomenRiesgof) {
		this.abdomenRiesgof = abdomenRiesgof;
	}

	/**
	 * @return the abdomenRiesgom
	 */
	public String getAbdomenRiesgom() {
		return abdomenRiesgom;
	}

	/**
	 * @param abdomenRiesgom the abdomenRiesgom to set
	 */
	public void setAbdomenRiesgom(String abdomenRiesgom) {
		this.abdomenRiesgom = abdomenRiesgom;
	}

	/**
	 * @return the cardiof
	 */
	public String getCardiof() {
		return cardiof;
	}

	/**
	 * @param cardiof the cardiof to set
	 */
	public void setCardiof(String cardiof) {
		this.cardiof = cardiof;
	}

	/**
	 * @return the cardiom
	 */
	public String getCardiom() {
		return cardiom;
	}

	/**
	 * @param cardiom the cardiom to set
	 */
	public void setCardiom(String cardiom) {
		this.cardiom = cardiom;
	}

	/**
	 * @return the flexibilidadf
	 */
	public String getFlexibilidadf() {
		return flexibilidadf;
	}

	/**
	 * @param flexibilidadf the flexibilidadf to set
	 */
	public void setFlexibilidadf(String flexibilidadf) {
		this.flexibilidadf = flexibilidadf;
	}

	/**
	 * @return the flexibilidadm
	 */
	public String getFlexibilidadm() {
		return flexibilidadm;
	}

	/**
	 * @param flexibilidadm the flexibilidadm to set
	 */
	public void setFlexibilidadm(String flexibilidadm) {
		this.flexibilidadm = flexibilidadm;
	}

	/**
	 * @return the fuerzaf
	 */
	public String getFuerzaf() {
		return fuerzaf;
	}

	/**
	 * @param fuerzaf the fuerzaf to set
	 */
	public void setFuerzaf(String fuerzaf) {
		this.fuerzaf = fuerzaf;
	}

	/**
	 * @return the fuerzam
	 */
	public String getFuerzam() {
		return fuerzam;
	}

	/**
	 * @param fuerzam the fuerzam to set
	 */
	public void setFuerzam(String fuerzam) {
		this.fuerzam = fuerzam;
	}

	/**
	 * @return the grasaMaxAceptablef
	 */
	public String getGrasaMaxAceptablef() {
		return grasaMaxAceptablef;
	}

	/**
	 * @param grasaMaxAceptablef the grasaMaxAceptablef to set
	 */
	public void setGrasaMaxAceptablef(String grasaMaxAceptablef) {
		this.grasaMaxAceptablef = grasaMaxAceptablef;
	}

	/**
	 * @return the grasaMaxAceptablem
	 */
	public String getGrasaMaxAceptablem() {
		return grasaMaxAceptablem;
	}

	/**
	 * @param grasaMaxAceptablem the grasaMaxAceptablem to set
	 */
	public void setGrasaMaxAceptablem(String grasaMaxAceptablem) {
		this.grasaMaxAceptablem = grasaMaxAceptablem;
	}

	/**
	 * @return the grasaMaxObesof
	 */
	public String getGrasaMaxObesof() {
		return grasaMaxObesof;
	}

	/**
	 * @param grasaMaxObesof the grasaMaxObesof to set
	 */
	public void setGrasaMaxObesof(String grasaMaxObesof) {
		this.grasaMaxObesof = grasaMaxObesof;
	}

	/**
	 * @return the grasaMaxObesom
	 */
	public String getGrasaMaxObesom() {
		return grasaMaxObesom;
	}

	/**
	 * @param grasaMaxObesom the grasaMaxObesom to set
	 */
	public void setGrasaMaxObesom(String grasaMaxObesom) {
		this.grasaMaxObesom = grasaMaxObesom;
	}

	/**
	 * @return the grasaMaxSobref
	 */
	public String getGrasaMaxSobref() {
		return grasaMaxSobref;
	}

	/**
	 * @param grasaMaxSobref the grasaMaxSobref to set
	 */
	public void setGrasaMaxSobref(String grasaMaxSobref) {
		this.grasaMaxSobref = grasaMaxSobref;
	}

	/**
	 * @return the grasaMaxSobrem
	 */
	public String getGrasaMaxSobrem() {
		return grasaMaxSobrem;
	}

	/**
	 * @param grasaMaxSobrem the grasaMaxSobrem to set
	 */
	public void setGrasaMaxSobrem(String grasaMaxSobrem) {
		this.grasaMaxSobrem = grasaMaxSobrem;
	}

	/**
	 * @return the grasaMinAceptablef
	 */
	public String getGrasaMinAceptablef() {
		return grasaMinAceptablef;
	}

	/**
	 * @param grasaMinAceptablef the grasaMinAceptablef to set
	 */
	public void setGrasaMinAceptablef(String grasaMinAceptablef) {
		this.grasaMinAceptablef = grasaMinAceptablef;
	}

	/**
	 * @return the grasaMinAceptablem
	 */
	public String getGrasaMinAceptablem() {
		return grasaMinAceptablem;
	}

	/**
	 * @param grasaMinAceptablem the grasaMinAceptablem to set
	 */
	public void setGrasaMinAceptablem(String grasaMinAceptablem) {
		this.grasaMinAceptablem = grasaMinAceptablem;
	}

	/**
	 * @return the grasaMinObesof
	 */
	public String getGrasaMinObesof() {
		return grasaMinObesof;
	}

	/**
	 * @param grasaMinObesof the grasaMinObesof to set
	 */
	public void setGrasaMinObesof(String grasaMinObesof) {
		this.grasaMinObesof = grasaMinObesof;
	}

	/**
	 * @return the grasaMinObesom
	 */
	public String getGrasaMinObesom() {
		return grasaMinObesom;
	}

	/**
	 * @param grasaMinObesom the grasaMinObesom to set
	 */
	public void setGrasaMinObesom(String grasaMinObesom) {
		this.grasaMinObesom = grasaMinObesom;
	}

	/**
	 * @return the grasaMinSobref
	 */
	public String getGrasaMinSobref() {
		return grasaMinSobref;
	}

	/**
	 * @param grasaMinSobref the grasaMinSobref to set
	 */
	public void setGrasaMinSobref(String grasaMinSobref) {
		this.grasaMinSobref = grasaMinSobref;
	}

	/**
	 * @return the grasaMinSobrem
	 */
	public String getGrasaMinSobrem() {
		return grasaMinSobrem;
	}

	/**
	 * @param grasaMinSobrem the grasaMinSobrem to set
	 */
	public void setGrasaMinSobrem(String grasaMinSobrem) {
		this.grasaMinSobrem = grasaMinSobrem;
	}

	/**
	 * @return the imcMaxBajof
	 */
	public String getImcMaxBajof() {
		return imcMaxBajof;
	}

	/**
	 * @param imcMaxBajof the imcMaxBajof to set
	 */
	public void setImcMaxBajof(String imcMaxBajof) {
		this.imcMaxBajof = imcMaxBajof;
	}

	/**
	 * @return the imcMaxBajom
	 */
	public String getImcMaxBajom() {
		return imcMaxBajom;
	}

	/**
	 * @param imcMaxBajom the imcMaxBajom to set
	 */
	public void setImcMaxBajom(String imcMaxBajom) {
		this.imcMaxBajom = imcMaxBajom;
	}

	/**
	 * @return the imcMaxNormalf
	 */
	public String getImcMaxNormalf() {
		return imcMaxNormalf;
	}

	/**
	 * @param imcMaxNormalf the imcMaxNormalf to set
	 */
	public void setImcMaxNormalf(String imcMaxNormalf) {
		this.imcMaxNormalf = imcMaxNormalf;
	}

	/**
	 * @return the imcMaxNormalm
	 */
	public String getImcMaxNormalm() {
		return imcMaxNormalm;
	}

	/**
	 * @param imcMaxNormalm the imcMaxNormalm to set
	 */
	public void setImcMaxNormalm(String imcMaxNormalm) {
		this.imcMaxNormalm = imcMaxNormalm;
	}

	/**
	 * @return the imcMaxObeso1f
	 */
	public String getImcMaxObeso1f() {
		return imcMaxObeso1f;
	}

	/**
	 * @param imcMaxObeso1f the imcMaxObeso1f to set
	 */
	public void setImcMaxObeso1f(String imcMaxObeso1f) {
		this.imcMaxObeso1f = imcMaxObeso1f;
	}

	/**
	 * @return the imcMaxObeso1m
	 */
	public String getImcMaxObeso1m() {
		return imcMaxObeso1m;
	}

	/**
	 * @param imcMaxObeso1m the imcMaxObeso1m to set
	 */
	public void setImcMaxObeso1m(String imcMaxObeso1m) {
		this.imcMaxObeso1m = imcMaxObeso1m;
	}

	/**
	 * @return the imcMaxObeso2f
	 */
	public String getImcMaxObeso2f() {
		return imcMaxObeso2f;
	}

	/**
	 * @param imcMaxObeso2f the imcMaxObeso2f to set
	 */
	public void setImcMaxObeso2f(String imcMaxObeso2f) {
		this.imcMaxObeso2f = imcMaxObeso2f;
	}

	/**
	 * @return the imcMaxObeso2m
	 */
	public String getImcMaxObeso2m() {
		return imcMaxObeso2m;
	}

	/**
	 * @param imcMaxObeso2m the imcMaxObeso2m to set
	 */
	public void setImcMaxObeso2m(String imcMaxObeso2m) {
		this.imcMaxObeso2m = imcMaxObeso2m;
	}

	/**
	 * @return the imcMaxObeso3f
	 */
	public String getImcMaxObeso3f() {
		return imcMaxObeso3f;
	}

	/**
	 * @param imcMaxObeso3f the imcMaxObeso3f to set
	 */
	public void setImcMaxObeso3f(String imcMaxObeso3f) {
		this.imcMaxObeso3f = imcMaxObeso3f;
	}

	/**
	 * @return the imcMaxObeso3m
	 */
	public String getImcMaxObeso3m() {
		return imcMaxObeso3m;
	}

	/**
	 * @param imcMaxObeso3m the imcMaxObeso3m to set
	 */
	public void setImcMaxObeso3m(String imcMaxObeso3m) {
		this.imcMaxObeso3m = imcMaxObeso3m;
	}

	/**
	 * @return the imcMaxSobref
	 */
	public String getImcMaxSobref() {
		return imcMaxSobref;
	}

	/**
	 * @param imcMaxSobref the imcMaxSobref to set
	 */
	public void setImcMaxSobref(String imcMaxSobref) {
		this.imcMaxSobref = imcMaxSobref;
	}

	/**
	 * @return the imcMaxSobrem
	 */
	public String getImcMaxSobrem() {
		return imcMaxSobrem;
	}

	/**
	 * @param imcMaxSobrem the imcMaxSobrem to set
	 */
	public void setImcMaxSobrem(String imcMaxSobrem) {
		this.imcMaxSobrem = imcMaxSobrem;
	}

	/**
	 * @return the imcMinBajof
	 */
	public String getImcMinBajof() {
		return imcMinBajof;
	}

	/**
	 * @param imcMinBajof the imcMinBajof to set
	 */
	public void setImcMinBajof(String imcMinBajof) {
		this.imcMinBajof = imcMinBajof;
	}

	/**
	 * @return the imcMinBajom
	 */
	public String getImcMinBajom() {
		return imcMinBajom;
	}

	/**
	 * @param imcMinBajom the imcMinBajom to set
	 */
	public void setImcMinBajom(String imcMinBajom) {
		this.imcMinBajom = imcMinBajom;
	}

	/**
	 * @return the imcMinNormalf
	 */
	public String getImcMinNormalf() {
		return imcMinNormalf;
	}

	/**
	 * @param imcMinNormalf the imcMinNormalf to set
	 */
	public void setImcMinNormalf(String imcMinNormalf) {
		this.imcMinNormalf = imcMinNormalf;
	}

	/**
	 * @return the imcMinNormalm
	 */
	public String getImcMinNormalm() {
		return imcMinNormalm;
	}

	/**
	 * @param imcMinNormalm the imcMinNormalm to set
	 */
	public void setImcMinNormalm(String imcMinNormalm) {
		this.imcMinNormalm = imcMinNormalm;
	}

	/**
	 * @return the imcMinObeso1f
	 */
	public String getImcMinObeso1f() {
		return imcMinObeso1f;
	}

	/**
	 * @param imcMinObeso1f the imcMinObeso1f to set
	 */
	public void setImcMinObeso1f(String imcMinObeso1f) {
		this.imcMinObeso1f = imcMinObeso1f;
	}

	/**
	 * @return the imcMinObeso1m
	 */
	public String getImcMinObeso1m() {
		return imcMinObeso1m;
	}

	/**
	 * @param imcMinObeso1m the imcMinObeso1m to set
	 */
	public void setImcMinObeso1m(String imcMinObeso1m) {
		this.imcMinObeso1m = imcMinObeso1m;
	}

	/**
	 * @return the imcMinObeso2f
	 */
	public String getImcMinObeso2f() {
		return imcMinObeso2f;
	}

	/**
	 * @param imcMinObeso2f the imcMinObeso2f to set
	 */
	public void setImcMinObeso2f(String imcMinObeso2f) {
		this.imcMinObeso2f = imcMinObeso2f;
	}

	/**
	 * @return the imcMinObeso2m
	 */
	public String getImcMinObeso2m() {
		return imcMinObeso2m;
	}

	/**
	 * @param imcMinObeso2m the imcMinObeso2m to set
	 */
	public void setImcMinObeso2m(String imcMinObeso2m) {
		this.imcMinObeso2m = imcMinObeso2m;
	}

	/**
	 * @return the imcMinObeso3f
	 */
	public String getImcMinObeso3f() {
		return imcMinObeso3f;
	}

	/**
	 * @param imcMinObeso3f the imcMinObeso3f to set
	 */
	public void setImcMinObeso3f(String imcMinObeso3f) {
		this.imcMinObeso3f = imcMinObeso3f;
	}

	/**
	 * @return the imcMinObeso3m
	 */
	public String getImcMinObeso3m() {
		return imcMinObeso3m;
	}

	/**
	 * @param imcMinObeso3m the imcMinObeso3m to set
	 */
	public void setImcMinObeso3m(String imcMinObeso3m) {
		this.imcMinObeso3m = imcMinObeso3m;
	}

	/**
	 * @return the imcMinSobref
	 */
	public String getImcMinSobref() {
		return imcMinSobref;
	}

	/**
	 * @param imcMinSobref the imcMinSobref to set
	 */
	public void setImcMinSobref(String imcMinSobref) {
		this.imcMinSobref = imcMinSobref;
	}

	/**
	 * @return the imcMinSobrem
	 */
	public String getImcMinSobrem() {
		return imcMinSobrem;
	}

	/**
	 * @param imcMinSobrem the imcMinSobrem to set
	 */
	public void setImcMinSobrem(String imcMinSobrem) {
		this.imcMinSobrem = imcMinSobrem;
	}

	/**
	 * @return the resistenciaf
	 */
	public String getResistenciaf() {
		return resistenciaf;
	}

	/**
	 * @param resistenciaf the resistenciaf to set
	 */
	public void setResistenciaf(String resistenciaf) {
		this.resistenciaf = resistenciaf;
	}

	/**
	 * @return the resistenciam
	 */
	public String getResistenciam() {
		return resistenciam;
	}

	/**
	 * @param resistenciam the resistenciam to set
	 */
	public void setResistenciam(String resistenciam) {
		this.resistenciam = resistenciam;
	}
	
	public boolean insertReg(Connection conn) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CAT_APTITUD(FUERZAM, FUERZAF, FLEXIBILIDADM, FLEXIBILIDADF," +
				" RESISTENCIAM, RESISTENCIAF, CARDIOM, CARDIOF," +
				" IMC_MIN_BAJOM, IMC_MIN_BAJOF, IMC_MAX_BAJOM, IMC_MAX_BAJOF," +
				" IMC_MIN_NORMALM, IMC_MIN_NORMALF, IMC_MAX_NORMALM, IMC_MAX_NORMALF," +
				" IMC_MIN_SOBREM, IMC_MIN_SOBREF, IMC_MAX_SOBREM, IMC_MAX_SOBREF," +
				" IMC_MIN_OBESO1M, IMC_MIN_OBESO1F, IMC_MAX_OBESO1M, IMC_MAX_OBESO1F," +
				" IMC_MIN_OBESO2M, IMC_MIN_OBESO2F, IMC_MAX_OBESO2M, IMC_MAX_OBESO2F," +
				" IMC_MIN_OBESO3M, IMC_MIN_OBESO3F, IMC_MAX_OBESO3M, IMC_MAX_OBESO3F," +
				" GRASA_MIN_ACEPTABLEM, GRASA_MIN_ACEPTABLEF, GRASA_MAX_ACEPTABLEM, GRASA_MAX_ACEPTABLEF," +
				" GRASA_MIN_SOBREM, GRASA_MIN_SOBREF, GRASA_MAX_SOBREM, GRASA_MAX_SOBREF," +
				" GRASA_MIN_OBESOM, GRASA_MIN_OBESOF, GRASA_MAX_OBESOM, GRASA_MAX_OBESOF," +
				" ABDOMEN_RIESGOM, ABDOMEN_RIESGOF)"+
				" VALUES( ?, ?, ?, ?," +
				" ?, ?, ?, ?," +
				" ?, ?, ?, ?," +
				" ?, ?, ?, ?," +
				" ?, ?, ?, ?," +
				" ?, ?, ?, ?," +
				" ?, ?, ?, ?," +
				" ?, ?, ?, ?," +
				" ?, ?, ?, ?," +
				" ?, ?, ?, ?," +
				" ?, ?, ?, ?," +
				" ?, ?)");			

			ps.setString(1, fuerzam);
			ps.setString(2, fuerzaf);
			ps.setString(3, flexibilidadm);
			ps.setString(4, flexibilidadf);
			ps.setString(5, resistenciam);
			ps.setString(6, resistenciaf);
			ps.setString(7, cardiom);
			ps.setString(8, cardiof);
			ps.setString(9, imcMinBajom);
			ps.setString(10, imcMinBajof);
			ps.setString(11, imcMaxBajom);
			ps.setString(12, imcMaxBajof);
			ps.setString(13, imcMinNormalm);
			ps.setString(14, imcMinNormalf);
			ps.setString(15, imcMaxNormalm);
			ps.setString(16, imcMaxNormalf);
			ps.setString(17, imcMinSobrem);
			ps.setString(18, imcMinSobref);
			ps.setString(19, imcMaxSobrem);
			ps.setString(20, imcMaxSobref);
			ps.setString(21, imcMinObeso1m);
			ps.setString(22, imcMinObeso1f);
			ps.setString(23, imcMaxObeso1m);
			ps.setString(24, imcMaxObeso1f);
			ps.setString(25, imcMinObeso2m);
			ps.setString(26, imcMinObeso2f);
			ps.setString(27, imcMaxObeso2m);
			ps.setString(28, imcMaxObeso2f);
			ps.setString(29, imcMinObeso3m);
			ps.setString(30, imcMinObeso3f);
			ps.setString(31, imcMaxObeso3m);
			ps.setString(32, imcMaxObeso3f);
			ps.setString(33, grasaMinAceptablem);
			ps.setString(34, grasaMinAceptablef);
			ps.setString(35, grasaMaxAceptablem);
			ps.setString(36, grasaMaxAceptablef);
			ps.setString(37, grasaMinSobrem);
			ps.setString(38, grasaMinSobref);
			ps.setString(39, grasaMaxSobrem);
			ps.setString(40, grasaMaxSobref);
			ps.setString(41, grasaMinObesom);
			ps.setString(42, grasaMinObesof);
			ps.setString(43, grasaMaxObesom);
			ps.setString(44, grasaMaxObesof);
			ps.setString(45, abdomenRiesgom);
			ps.setString(46, abdomenRiesgof);
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatAptitud|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CAT_APTITUD"+ 
				" SET FUERZAM = TO_NUMBER(?, '99')," +
				" FUERZAF = TO_NUMBER(?, '99')," +
				" FLEXIBILIDADM = TO_NUMBER(?, '99')," +
				" FLEXIBILIDADF = TO_NUMBER(?, '99')," +
				" RESISTENCIAM = TO_NUMBER(?, '99')," +
				" RESISTENCIAF = TO_NUMBER(?, '99')," +
				" CARDIOM = TO_NUMBER(?, '99')," +
				" CARDIOF = TO_NUMBER(?, '99')," +
				" IMC_MIN_BAJOM = TO_NUMBER(?, '99.9')," +
				" IMC_MIN_BAJOF = TO_NUMBER(?, '99.9')," +
				" IMC_MAX_BAJOM = TO_NUMBER(?, '99.9')," +
				" IMC_MAX_BAJOF = TO_NUMBER(?, '99.9')," +
				" IMC_MIN_NORMALM = TO_NUMBER(?, '99.9')," +
				" IMC_MIN_NORMALF = TO_NUMBER(?, '99.9')," +
				" IMC_MAX_NORMALM = TO_NUMBER(?, '99.9')," +
				" IMC_MAX_NORMALF = TO_NUMBER(?, '99.9')," +
				" IMC_MIN_SOBREM = TO_NUMBER(?, '99.9')," +
				" IMC_MIN_SOBREF = TO_NUMBER(?, '99.9')," +
				" IMC_MAX_SOBREM = TO_NUMBER(?, '99.9')," +
				" IMC_MAX_SOBREF = TO_NUMBER(?, '99.9')," +
				" IMC_MIN_OBESO1M = TO_NUMBER(?, '99.9')," +
				" IMC_MIN_OBESO1F = TO_NUMBER(?, '99.9')," +
				" IMC_MAX_OBESO1M = TO_NUMBER(?, '99.9')," +
				" IMC_MAX_OBESO1F = TO_NUMBER(?, '99.9')," +
				" IMC_MIN_OBESO2M = TO_NUMBER(?, '99.9')," +
				" IMC_MIN_OBESO2F = TO_NUMBER(?, '99.9')," +
				" IMC_MAX_OBESO2M = TO_NUMBER(?, '99.9')," +
				" IMC_MAX_OBESO2F = TO_NUMBER(?, '99.9')," +
				" IMC_MIN_OBESO3M = TO_NUMBER(?, '99.9')," +
				" IMC_MIN_OBESO3F = TO_NUMBER(?, '99.9')," +
				" IMC_MAX_OBESO3M = TO_NUMBER(?, '99.9')," +
				" IMC_MAX_OBESO3F = TO_NUMBER(?, '99.9')," +
				" GRASA_MIN_ACEPTABLEM = TO_NUMBER(?, '99')," +
				" GRASA_MIN_ACEPTABLEF = TO_NUMBER(?, '99')," +
				" GRASA_MAX_ACEPTABLEM = TO_NUMBER(?, '99')," +
				" GRASA_MAX_ACEPTABLEF = TO_NUMBER(?, '99')," +
				" GRASA_MIN_SOBREM = TO_NUMBER(?, '99')," +
				" GRASA_MIN_SOBREF = TO_NUMBER(?, '99')," +
				" GRASA_MAX_SOBREM = TO_NUMBER(?, '99')," +
				" GRASA_MAX_SOBREF = TO_NUMBER(?, '99')," +
				" GRASA_MIN_OBESOM = TO_NUMBER(?, '99')," +
				" GRASA_MIN_OBESOF = TO_NUMBER(?, '99')," +
				" GRASA_MAX_OBESOM = TO_NUMBER(?, '99')," +
				" GRASA_MAX_OBESOF = TO_NUMBER(?, '99')," +
				" ABDOMEN_RIESGOM = TO_NUMBER(?, '999')," +
				" ABDOMEN_RIESGOF = TO_NUMBER(?, '999')");

			ps.setString(1, fuerzam);
			ps.setString(2, fuerzaf);
			ps.setString(3, flexibilidadm);
			ps.setString(4, flexibilidadf);
			ps.setString(5, resistenciam);
			ps.setString(6, resistenciaf);
			ps.setString(7, cardiom);
			ps.setString(8, cardiof);
			ps.setString(9, imcMinBajom);
			ps.setString(10, imcMinBajof);
			ps.setString(11, imcMaxBajom);
			ps.setString(12, imcMaxBajof);
			ps.setString(13, imcMinNormalm);
			ps.setString(14, imcMinNormalf);
			ps.setString(15, imcMaxNormalm);
			ps.setString(16, imcMaxNormalf);
			ps.setString(17, imcMinSobrem);
			ps.setString(18, imcMinSobref);
			ps.setString(19, imcMaxSobrem);
			ps.setString(20, imcMaxSobref);
			ps.setString(21, imcMinObeso1m);
			ps.setString(22, imcMinObeso1f);
			ps.setString(23, imcMaxObeso1m);
			ps.setString(24, imcMaxObeso1f);
			ps.setString(25, imcMinObeso2m);
			ps.setString(26, imcMinObeso2f);
			ps.setString(27, imcMaxObeso2m);
			ps.setString(28, imcMaxObeso2f);
			ps.setString(29, imcMinObeso3m);
			ps.setString(30, imcMinObeso3f);
			ps.setString(31, imcMaxObeso3m);
			ps.setString(32, imcMaxObeso3f);
			ps.setString(33, grasaMinAceptablem);
			ps.setString(34, grasaMinAceptablef);
			ps.setString(35, grasaMaxAceptablem);
			ps.setString(36, grasaMaxAceptablef);
			ps.setString(37, grasaMinSobrem);
			ps.setString(38, grasaMinSobref);
			ps.setString(39, grasaMaxSobrem);
			ps.setString(40, grasaMaxSobref);
			ps.setString(41, grasaMinObesom);
			ps.setString(42, grasaMinObesof);
			ps.setString(43, grasaMaxObesom);
			ps.setString(44, grasaMaxObesof);
			ps.setString(45, abdomenRiesgom);
			ps.setString(46, abdomenRiesgof);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatAptitud|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		fuerzam				= rs.getString("FUERZAM");
		fuerzaf				= rs.getString("FUERZAF");
		flexibilidadm		= rs.getString("FLEXIBILIDADM");
		flexibilidadf		= rs.getString("FLEXIBILIDADF");
		resistenciam		= rs.getString("RESISTENCIAM");
		resistenciaf		= rs.getString("RESISTENCIAF");
		cardiom				= rs.getString("CARDIOM");
		cardiof				= rs.getString("CARDIOF");
		imcMinBajom			= rs.getString("IMC_MIN_BAJOM");
		imcMinBajof			= rs.getString("IMC_MIN_BAJOF");
		imcMaxBajom			= rs.getString("IMC_MAX_BAJOM");
		imcMaxBajof			= rs.getString("IMC_MAX_BAJOF");
		imcMinNormalm		= rs.getString("IMC_MIN_NORMALM");
		imcMinNormalf		= rs.getString("IMC_MIN_NORMALF");
		imcMaxNormalm		= rs.getString("IMC_MAX_NORMALM");
		imcMaxNormalf		= rs.getString("IMC_MAX_NORMALF");
		imcMinSobrem		= rs.getString("IMC_MIN_SOBREM");
		imcMinSobref		= rs.getString("IMC_MIN_SOBREF");
		imcMaxSobrem		= rs.getString("IMC_MAX_SOBREM");
		imcMaxSobref		= rs.getString("IMC_MAX_SOBREF");
		imcMinObeso1m		= rs.getString("IMC_MIN_OBESO1M");
		imcMinObeso1f		= rs.getString("IMC_MIN_OBESO1F");
		imcMaxObeso1m		= rs.getString("IMC_MAX_OBESO1M");
		imcMaxObeso1f		= rs.getString("IMC_MAX_OBESO1F");
		imcMinObeso2m		= rs.getString("IMC_MIN_OBESO2M");
		imcMinObeso2f		= rs.getString("IMC_MIN_OBESO2F");
		imcMaxObeso2m		= rs.getString("IMC_MAX_OBESO2M");
		imcMaxObeso2f		= rs.getString("IMC_MAX_OBESO2F");
		imcMinObeso3m		= rs.getString("IMC_MIN_OBESO3M");
		imcMinObeso3f		= rs.getString("IMC_MIN_OBESO3F");
		imcMaxObeso3m		= rs.getString("IMC_MAX_OBESO3M");
		imcMaxObeso3f		= rs.getString("IMC_MAX_OBESO3F");
		grasaMinAceptablem	= rs.getString("GRASA_MIN_ACEPTABLEM");
		grasaMinAceptablef	= rs.getString("GRASA_MIN_ACEPTABLEF");
		grasaMaxAceptablem	= rs.getString("GRASA_MAX_ACEPTABLEM");
		grasaMaxAceptablef	= rs.getString("GRASA_MAX_ACEPTABLEF");
		grasaMinSobrem		= rs.getString("GRASA_MIN_SOBREM");
		grasaMinSobref		= rs.getString("GRASA_MIN_SOBREF");
		grasaMaxSobrem		= rs.getString("GRASA_MAX_SOBREM");
		grasaMaxSobref		= rs.getString("GRASA_MAX_SOBREF");
		grasaMinObesom		= rs.getString("GRASA_MIN_OBESOM");
		grasaMinObesof		= rs.getString("GRASA_MIN_OBESOF");
		grasaMaxObesom		= rs.getString("GRASA_MAX_OBESOM");
		grasaMaxObesof		= rs.getString("GRASA_MAX_OBESOF");
		abdomenRiesgom		= rs.getString("ABDOMEN_RIESGOM");
		abdomenRiesgof		= rs.getString("ABDOMEN_RIESGOF");
	}
	
	public void mapeaRegId( Connection conn) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT FUERZAM, FUERZAF, FLEXIBILIDADM, FLEXIBILIDADF," +
					" RESISTENCIAM, RESISTENCIAF, CARDIOM, CARDIOF," +
					" IMC_MIN_BAJOM, IMC_MIN_BAJOF, IMC_MAX_BAJOM, IMC_MAX_BAJOF," +
					" IMC_MIN_NORMALM, IMC_MIN_NORMALF, IMC_MAX_NORMALM, IMC_MAX_NORMALF," +
					" IMC_MIN_SOBREM, IMC_MIN_SOBREF, IMC_MAX_SOBREM, IMC_MAX_SOBREF," +
					" IMC_MIN_OBESO1M, IMC_MIN_OBESO1F, IMC_MAX_OBESO1M, IMC_MAX_OBESO1F," +
					" IMC_MIN_OBESO2M, IMC_MIN_OBESO2F, IMC_MAX_OBESO2M, IMC_MAX_OBESO2F," +
					" IMC_MIN_OBESO3M, IMC_MIN_OBESO3F, IMC_MAX_OBESO3M, IMC_MAX_OBESO3F," +
					" GRASA_MIN_ACEPTABLEM, GRASA_MIN_ACEPTABLEF, GRASA_MAX_ACEPTABLEM, GRASA_MAX_ACEPTABLEF," +
					" GRASA_MIN_SOBREM, GRASA_MIN_SOBREF, GRASA_MAX_SOBREM, GRASA_MAX_SOBREF," +
					" GRASA_MIN_OBESOM, GRASA_MIN_OBESOF, GRASA_MAX_OBESOM, GRASA_MAX_OBESOF," +
					" ABDOMEN_RIESGOM, ABDOMEN_RIESGOF"+
					" FROM ENOC.CAT_APTITUD"); 
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatAptitud|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
	}
}