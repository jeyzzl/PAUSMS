/**
 * 
 */
package aca.mentores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Jose Torres
 *
 */
public class MentPerfil {
	private String codigoPersonal;
	private String cargaId;
	private String folio;
	private String mentorId;
	private String fecha;
	private String relAsdb;
	private String relAsd;
	private String relNasd;
	private String resInt;
	private String resExt;
	private String resTipo;
	private String comComedor;
	private String comSnack;
	private String comAsistencia;
	private String comCasa;
	private String comOtro;
	private String com3;
	private String com2;
	private String com1;
	private String devDiaria;
	private String devSemana;
	private String devMenos;
	private String iglUm;
	private String iglOtra;
	private String iglNinguna;
	private String proFamiliar;
	private String proFinanciero;
	private String proMateria;
	private String proPendiente;
	private String lidEspiritual;
	private String lidPosicion;
	private String traNada;
	private String traMedio;
	private String traCompleto;
	private String estDesafios;
	private String estRelaciones;
	private String estProgreso;
	private String estRegresa;
	private String riesgoPersonal;
	private String riesgoIntegracion;
	private String riesgoRelaciones;
	private String riesgoAcademico;
	private String riesgoFinanciero;
	private String usuario;
	
	public MentPerfil(){
		codigoPersonal		= "";
		cargaId				= "";
		folio				= "";
		mentorId			= "";
		fecha				= "";
		relAsdb				= "";
		relAsd				= "";
		relNasd				= "";
		resInt				= "";
		resExt				= "";
		resTipo				= "";
		comComedor			= "";
		comSnack			= "";
		comAsistencia		= "";
		comCasa				= "";
		comOtro				= "";
		com3				= "";
		com2				= "";
		com1				= "";
		devDiaria			= "";
		devSemana			= "";
		devMenos			= "";
		iglUm				= "";
		iglOtra				= "";
		iglNinguna			= "";
		proFamiliar			= "";
		proFinanciero		= "";
		proMateria			= "";
		proPendiente		= "";
		lidEspiritual		= "";
		lidPosicion			= "";
		traNada				= "";
		traMedio			= "";
		traCompleto			= "";
		estDesafios			= "";
		estRelaciones		= "";
		estProgreso			= "";
		estRegresa			= "";
		riesgoPersonal		= "";
		riesgoIntegracion	= "";
		riesgoRelaciones	= "";
		riesgoAcademico		= "";
		riesgoFinanciero	= "";
		usuario				= "";
	}
	
	/**
	 * @return the cargaId
	 */
	public String getCargaId() {
		return cargaId;
	}

	/**
	 * @param cargaId the cargaId to set
	 */
	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
	}

	/**
	 * @return the codigoPersonal
	 */
	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	/**
	 * @param codigoPersonal the codigoPersonal to set
	 */
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	/**
	 * @return the com1
	 */
	public String getCom1() {
		return com1;
	}

	/**
	 * @param com1 the com1 to set
	 */
	public void setCom1(String com1) {
		this.com1 = com1;
	}

	/**
	 * @return the com2
	 */
	public String getCom2() {
		return com2;
	}

	/**
	 * @param com2 the com2 to set
	 */
	public void setCom2(String com2) {
		this.com2 = com2;
	}

	/**
	 * @return the com3
	 */
	public String getCom3() {
		return com3;
	}

	/**
	 * @param com3 the com3 to set
	 */
	public void setCom3(String com3) {
		this.com3 = com3;
	}

	/**
	 * @return the comAsistencia
	 */
	public String getComAsistencia() {
		return comAsistencia;
	}

	/**
	 * @param comAsistencia the comAsistencia to set
	 */
	public void setComAsistencia(String comAsistencia) {
		this.comAsistencia = comAsistencia;
	}

	/**
	 * @return the comCasa
	 */
	public String getComCasa() {
		return comCasa;
	}

	/**
	 * @param comCasa the comCasa to set
	 */
	public void setComCasa(String comCasa) {
		this.comCasa = comCasa;
	}

	/**
	 * @return the comComedor
	 */
	public String getComComedor() {
		return comComedor;
	}

	/**
	 * @param comComedor the comComedor to set
	 */
	public void setComComedor(String comComedor) {
		this.comComedor = comComedor;
	}

	/**
	 * @return the comOtro
	 */
	public String getComOtro() {
		return comOtro;
	}

	/**
	 * @param comOtro the comOtro to set
	 */
	public void setComOtro(String comOtro) {
		this.comOtro = comOtro;
	}

	/**
	 * @return the comSnack
	 */
	public String getComSnack() {
		return comSnack;
	}

	/**
	 * @param comSnack the comSnack to set
	 */
	public void setComSnack(String comSnack) {
		this.comSnack = comSnack;
	}

	/**
	 * @return the devDiaria
	 */
	public String getDevDiaria() {
		return devDiaria;
	}

	/**
	 * @param devDiaria the devDiaria to set
	 */
	public void setDevDiaria(String devDiaria) {
		this.devDiaria = devDiaria;
	}

	/**
	 * @return the devMenos
	 */
	public String getDevMenos() {
		return devMenos;
	}

	/**
	 * @param devMenos the devMenos to set
	 */
	public void setDevMenos(String devMenos) {
		this.devMenos = devMenos;
	}

	/**
	 * @return the devSemana
	 */
	public String getDevSemana() {
		return devSemana;
	}

	/**
	 * @param devSemana the devSemana to set
	 */
	public void setDevSemana(String devSemana) {
		this.devSemana = devSemana;
	}

	/**
	 * @return the estDesafios
	 */
	public String getEstDesafios() {
		return estDesafios;
	}

	/**
	 * @param estDesafios the estDesafios to set
	 */
	public void setEstDesafios(String estDesafios) {
		this.estDesafios = estDesafios;
	}

	/**
	 * @return the estProgreso
	 */
	public String getEstProgreso() {
		return estProgreso;
	}

	/**
	 * @param estProgreso the estProgreso to set
	 */
	public void setEstProgreso(String estProgreso) {
		this.estProgreso = estProgreso;
	}

	/**
	 * @return the estRegresa
	 */
	public String getEstRegresa() {
		return estRegresa;
	}

	/**
	 * @param estRegresa the estRegresa to set
	 */
	public void setEstRegresa(String estRegresa) {
		this.estRegresa = estRegresa;
	}

	/**
	 * @return the estRelaciones
	 */
	public String getEstRelaciones() {
		return estRelaciones;
	}

	/**
	 * @param estRelaciones the estRelaciones to set
	 */
	public void setEstRelaciones(String estRelaciones) {
		this.estRelaciones = estRelaciones;
	}

	/**
	 * @return the fecha
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the folio
	 */
	public String getFolio() {
		return folio;
	}

	/**
	 * @param folio the folio to set
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}

	/**
	 * @return the iglNinguna
	 */
	public String getIglNinguna() {
		return iglNinguna;
	}

	/**
	 * @param iglNinguna the iglNinguna to set
	 */
	public void setIglNinguna(String iglNinguna) {
		this.iglNinguna = iglNinguna;
	}

	/**
	 * @return the iglOtra
	 */
	public String getIglOtra() {
		return iglOtra;
	}

	/**
	 * @param iglOtra the iglOtra to set
	 */
	public void setIglOtra(String iglOtra) {
		this.iglOtra = iglOtra;
	}

	/**
	 * @return the iglUm
	 */
	public String getIglUm() {
		return iglUm;
	}

	/**
	 * @param iglUm the iglUm to set
	 */
	public void setIglUm(String iglUm) {
		this.iglUm = iglUm;
	}

	/**
	 * @return the lidEspiritual
	 */
	public String getLidEspiritual() {
		return lidEspiritual;
	}

	/**
	 * @param lidEspiritual the lidEspiritual to set
	 */
	public void setLidEspiritual(String lidEspiritual) {
		this.lidEspiritual = lidEspiritual;
	}

	/**
	 * @return the lidPosicion
	 */
	public String getLidPosicion() {
		return lidPosicion;
	}

	/**
	 * @param lidPosicion the lidPosicion to set
	 */
	public void setLidPosicion(String lidPosicion) {
		this.lidPosicion = lidPosicion;
	}

	/**
	 * @return the mentorId
	 */
	public String getMentorId() {
		return mentorId;
	}

	/**
	 * @param mentorId the mentorId to set
	 */
	public void setMentorId(String mentorId) {
		this.mentorId = mentorId;
	}

	/**
	 * @return the proFamiliar
	 */
	public String getProFamiliar() {
		return proFamiliar;
	}

	/**
	 * @param proFamiliar the proFamiliar to set
	 */
	public void setProFamiliar(String proFamiliar) {
		this.proFamiliar = proFamiliar;
	}

	/**
	 * @return the proFinanciero
	 */
	public String getProFinanciero() {
		return proFinanciero;
	}

	/**
	 * @param proFinanciero the proFinanciero to set
	 */
	public void setProFinanciero(String proFinanciero) {
		this.proFinanciero = proFinanciero;
	}

	/**
	 * @return the proMateria
	 */
	public String getProMateria() {
		return proMateria;
	}

	/**
	 * @param proMateria the proMateria to set
	 */
	public void setProMateria(String proMateria) {
		this.proMateria = proMateria;
	}

	/**
	 * @return the proPendiente
	 */
	public String getProPendiente() {
		return proPendiente;
	}

	/**
	 * @param proPendiente the proPendiente to set
	 */
	public void setProPendiente(String proPendiente) {
		this.proPendiente = proPendiente;
	}

	/**
	 * @return the relAsd
	 */
	public String getRelAsd() {
		return relAsd;
	}

	/**
	 * @param relAsd the relAsd to set
	 */
	public void setRelAsd(String relAsd) {
		this.relAsd = relAsd;
	}

	/**
	 * @return the relAsdb
	 */
	public String getRelAsdb() {
		return relAsdb;
	}

	/**
	 * @param relAsdb the relAsdb to set
	 */
	public void setRelAsdb(String relAsdb) {
		this.relAsdb = relAsdb;
	}

	/**
	 * @return the relNasd
	 */
	public String getRelNasd() {
		return relNasd;
	}

	/**
	 * @param relNasd the relNasd to set
	 */
	public void setRelNasd(String relNasd) {
		this.relNasd = relNasd;
	}

	/**
	 * @return the resExt
	 */
	public String getResExt() {
		return resExt;
	}

	/**
	 * @param resExt the resExt to set
	 */
	public void setResExt(String resExt) {
		this.resExt = resExt;
	}

	/**
	 * @return the resInt
	 */
	public String getResInt() {
		return resInt;
	}

	/**
	 * @param resInt the resInt to set
	 */
	public void setResInt(String resInt) {
		this.resInt = resInt;
	}

	/**
	 * @return the resTipo
	 */
	public String getResTipo() {
		return resTipo;
	}

	/**
	 * @param resTipo the resTipo to set
	 */
	public void setResTipo(String resTipo) {
		this.resTipo = resTipo;
	}

	/**
	 * @return the riesgoAcademico
	 */
	public String getRiesgoAcademico() {
		return riesgoAcademico;
	}

	/**
	 * @param riesgoAcademico the riesgoAcademico to set
	 */
	public void setRiesgoAcademico(String riesgoAcademico) {
		this.riesgoAcademico = riesgoAcademico;
	}

	/**
	 * @return the riesgoFinanciero
	 */
	public String getRiesgoFinanciero() {
		return riesgoFinanciero;
	}

	/**
	 * @param riesgoFinanciero the riesgoFinanciero to set
	 */
	public void setRiesgoFinanciero(String riesgoFinanciero) {
		this.riesgoFinanciero = riesgoFinanciero;
	}

	/**
	 * @return the riesgoIntegracion
	 */
	public String getRiesgoIntegracion() {
		return riesgoIntegracion;
	}

	/**
	 * @param riesgoIntegracion the riesgoIntegracion to set
	 */
	public void setRiesgoIntegracion(String riesgoIntegracion) {
		this.riesgoIntegracion = riesgoIntegracion;
	}

	/**
	 * @return the riesgoPersonal
	 */
	public String getRiesgoPersonal() {
		return riesgoPersonal;
	}

	/**
	 * @param riesgoPersonal the riesgoPersonal to set
	 */
	public void setRiesgoPersonal(String riesgoPersonal) {
		this.riesgoPersonal = riesgoPersonal;
	}

	/**
	 * @return the riesgoRelaciones
	 */
	public String getRiesgoRelaciones() {
		return riesgoRelaciones;
	}

	/**
	 * @param riesgoRelaciones the riesgoRelaciones to set
	 */
	public void setRiesgoRelaciones(String riesgoRelaciones) {
		this.riesgoRelaciones = riesgoRelaciones;
	}

	/**
	 * @return the traCompleto
	 */
	public String getTraCompleto() {
		return traCompleto;
	}

	/**
	 * @param traCompleto the traCompleto to set
	 */
	public void setTraCompleto(String traCompleto) {
		this.traCompleto = traCompleto;
	}

	/**
	 * @return the traMedio
	 */
	public String getTraMedio() {
		return traMedio;
	}

	/**
	 * @param traMedio the traMedio to set
	 */
	public void setTraMedio(String traMedio) {
		this.traMedio = traMedio;
	}

	/**
	 * @return the traNada
	 */
	public String getTraNada() {
		return traNada;
	}

	/**
	 * @param traNada the traNada to set
	 */
	public void setTraNada(String traNada) {
		this.traNada = traNada;
	}

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public boolean insertReg(Connection conn) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO"+
				" ENOC.MENT_PERFIL(CODIGO_PERSONAL, CARGA_ID, MENTOR_ID," +
				" FOLIO, FECHA, REL_ASDB, REL_ASD," +
				" REL_NASD, RES_INT, RES_EXT, RES_TIPO," +
				" COM_COMEDOR, COM_SNACK, COM_ASISTENCIA, COM_CASA," +
				" COM_OTRO, COM_3, COM_2, COM_1," +
				" DEV_DIARIA, DEV_SEMANA, DEV_MENOS, IGL_UM," +
				" IGL_OTRA, IGL_NINGUNA, PRO_FAMILIAR, PRO_FINANCIERO," +
				" PRO_MATERIA, PRO_PENDIENTE, LID_ESPIRITUAL, LID_POSICION," +
				" TRA_NADA, TRA_MEDIO, TRA_COMPLETO, EST_DESAFIOS," +
				" EST_RELACIONES, EST_PROGRESO, EST_REGRESA, RIESGO_PERSONAL," +
				" RIESGO_INTEGRACION, RIESGO_RELACIONES, RIESGO_ACADEMICO, RIESGO_FINANCIERO, USUARIO) "+
				"VALUES(?, ?, ?," +
				" TO_NUMBER(?, '99'), TO_DATE(?, 'DD/MM/YYYY'), ?, ?," +
				" ?, ?, ?, ?," +
				" ?, ?, ?, ?," +
				" ?, ?, ?, ?," +
				" ?, ?, ?, ?," +
				" ?, ?, ?, ?," +
				" ?, ?, ?, ?," +
				" ?, ?, ?, ?," +
				" ?, ?, ?, ?," +
				" ?, ?, ?, ?, ?)");	
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, cargaId);
			ps.setString(3, mentorId);
			ps.setString(4, folio);
			ps.setString(5, fecha);
			ps.setString(6, relAsdb);
			ps.setString(7, relAsd);
			ps.setString(8, relNasd);
			ps.setString(9, resInt);
			ps.setString(10, resExt);
			ps.setString(11, resTipo);
			ps.setString(12, comComedor);
			ps.setString(13, comSnack);
			ps.setString(14, comAsistencia);
			ps.setString(15, comCasa);
			ps.setString(16, comOtro);
			ps.setString(17, com3);
			ps.setString(18, com2);
			ps.setString(19, com1);
			ps.setString(20, devDiaria);
			ps.setString(21, devSemana);
			ps.setString(22, devMenos);
			ps.setString(23, iglUm);
			ps.setString(24, iglOtra);
			ps.setString(25, iglNinguna);
			ps.setString(26, proFamiliar);
			ps.setString(27, proFinanciero);
			ps.setString(28, proMateria);
			ps.setString(29, proPendiente);
			ps.setString(30, lidEspiritual);
			ps.setString(31, lidPosicion);
			ps.setString(32, traNada);
			ps.setString(33, traMedio);
			ps.setString(34, traCompleto);
			ps.setString(35, estDesafios);
			ps.setString(36, estRelaciones);
			ps.setString(37, estProgreso);
			ps.setString(38, estRegresa);
			ps.setString(39, riesgoPersonal);
			ps.setString(40, riesgoIntegracion);
			ps.setString(41, riesgoRelaciones);
			ps.setString(42, riesgoAcademico);
			ps.setString(43, riesgoFinanciero);
			ps.setString(44, usuario);
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentPerfil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.MENT_PERFIL"+ 
				" SET MENTOR_ID = ?," +
				" FECHA = TO_DATE(?, 'DD/MM/YYYY')," +
				" REL_ASDB = ?," +
				" REL_ASD = ?," +
				" REL_NASD = ?," +
				" RES_INT = ?," +
				" RES_EXT = ?," +
				" RES_TIPO = ?," +
				" COM_COMEDOR = ?," +
				" COM_SNACK = ?," +
				" COM_ASISTENCIA = ?," +
				" COM_CASA = ?," +
				" COM_OTRO = ?," +
				" COM_3 = ?," +
				" COM_2 = ?," +
				" COM_1 = ?," +
				" DEV_DIARIA = ?," +
				" DEV_SEMANA = ?," +
				" DEV_MENOS = ?," +
				" IGL_UM = ?," +
				" IGL_OTRA = ?," +
				" IGL_NINGUNA = ?," +
				" PRO_FAMILIAR = ?," +
				" PRO_FINANCIERO = ?," +
				" PRO_MATERIA = ?," +
				" PRO_PENDIENTE = ?," +
				" LID_ESPIRITUAL = ?," +
				" LID_POSICION = ?," +
				" TRA_NADA = ?," +
				" TRA_MEDIO = ?," +
				" TRA_COMPLETO = ?," +
				" EST_DESAFIOS = ?," +
				" EST_RELACIONES = ?," +
				" EST_PROGRESO = ?," +
				" EST_REGRESA = ?," +
				" RIESGO_PERSONAL = ?," +
				" RIESGO_INTEGRACION = ?," +
				" RIESGO_RELACIONES = ?," +
				" RIESGO_ACADEMICO = ?," +
				" RIESGO_FINANCIERO = ?," +
				" USUARIO = ?" +
				" WHERE CODIGO_PERSONAL = ?" +
				" AND FOLIO = TO_NUMBER(?, '99')" +
				" AND CARGA_ID = ?");
			
			ps.setString(1, mentorId);
			ps.setString(2, fecha);
			ps.setString(3, relAsdb);
			ps.setString(4, relAsd);
			ps.setString(5, relNasd);
			ps.setString(6, resInt);
			ps.setString(7, resExt);
			ps.setString(8, resTipo);
			ps.setString(9, comComedor);
			ps.setString(10, comSnack);
			ps.setString(11, comAsistencia);
			ps.setString(12, comCasa);
			ps.setString(13, comOtro);
			ps.setString(14, com3);
			ps.setString(15, com2);
			ps.setString(16, com1);
			ps.setString(17, devDiaria);
			ps.setString(18, devSemana);
			ps.setString(19, devMenos);
			ps.setString(20, iglUm);
			ps.setString(21, iglOtra);
			ps.setString(22, iglNinguna);
			ps.setString(23, proFamiliar);
			ps.setString(24, proFinanciero);
			ps.setString(25, proMateria);
			ps.setString(26, proPendiente);
			ps.setString(27, lidEspiritual);
			ps.setString(28, lidPosicion);
			ps.setString(29, traNada);
			ps.setString(30, traMedio);
			ps.setString(31, traCompleto);
			ps.setString(32, estDesafios);
			ps.setString(33, estRelaciones);
			ps.setString(34, estProgreso);
			ps.setString(35, estRegresa);
			ps.setString(36, riesgoPersonal);
			ps.setString(37, riesgoIntegracion);
			ps.setString(38, riesgoRelaciones);
			ps.setString(39, riesgoAcademico);
			ps.setString(40, riesgoFinanciero);
			ps.setString(41, usuario);
			ps.setString(42, codigoPersonal);
			ps.setString(43, folio);
			ps.setString(44, cargaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentPerfil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.MENT_PERFIL"+ 
				" WHERE CODIGO_PERSONAL = ?" +
				" AND FOLIO = TO_NUMBER(?, '99')" +
				" AND CARGA_ID = ?");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, folio);
			ps.setString(3, cargaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentPerfil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal		= rs.getString("CODIGO_PERSONAL");
		cargaId				= rs.getString("CARGA_ID");
		mentorId			= rs.getString("MENTOR_ID");
		folio				= rs.getString("FOLIO");
		fecha				= rs.getString("FECHA");
		relAsdb				= rs.getString("REL_ASDB");
		relAsd				= rs.getString("REL_ASD");
		relNasd				= rs.getString("REL_NASD");
		resInt				= rs.getString("RES_INT");
		resExt				= rs.getString("RES_EXT");
		resTipo				= rs.getString("RES_TIPO");
		comComedor			= rs.getString("COM_COMEDOR");
		comSnack			= rs.getString("COM_SNACK");
		comAsistencia		= rs.getString("COM_ASISTENCIA");
		comCasa				= rs.getString("COM_CASA");
		comOtro				= rs.getString("COM_OTRO");
		com3				= rs.getString("COM_3");
		com2				= rs.getString("COM_2");
		com1				= rs.getString("COM_1");
		devDiaria			= rs.getString("DEV_DIARIA");
		devSemana			= rs.getString("DEV_SEMANA");
		devMenos			= rs.getString("DEV_MENOS");
		iglUm				= rs.getString("IGL_UM");
		iglOtra				= rs.getString("IGL_OTRA");
		iglNinguna			= rs.getString("IGL_NINGUNA");
		proFamiliar			= rs.getString("PRO_FAMILIAR");
		proFinanciero		= rs.getString("PRO_FINANCIERO");
		proMateria			= rs.getString("PRO_MATERIA");
		proPendiente		= rs.getString("PRO_PENDIENTE");
		lidEspiritual		= rs.getString("LID_ESPIRITUAL");
		lidPosicion			= rs.getString("LID_POSICION");
		traNada				= rs.getString("TRA_NADA");
		traMedio			= rs.getString("TRA_MEDIO");
		traCompleto			= rs.getString("TRA_COMPLETO");
		estDesafios			= rs.getString("EST_DESAFIOS");
		estRelaciones		= rs.getString("EST_RELACIONES");
		estProgreso			= rs.getString("EST_PROGRESO");
		estRegresa			= rs.getString("EST_REGRESA");
		riesgoPersonal		= rs.getString("RIESGO_PERSONAL");
		riesgoIntegracion	= rs.getString("RIESGO_INTEGRACION");
		riesgoRelaciones	= rs.getString("RIESGO_RELACIONES");
		riesgoAcademico		= rs.getString("RIESGO_ACADEMICO");
		riesgoFinanciero	= rs.getString("RIESGO_FINANCIERO");
		usuario				= rs.getString("USUARIO");
	}
	
	public void mapeaRegId( Connection conn, String codigoPersonal, String cargaId, String folio) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{ 
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL, CARGA_ID, MENTOR_ID," +
					" FOLIO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, REL_ASDB, REL_ASD," +
					" REL_NASD, RES_INT, RES_EXT, RES_TIPO," +
					" COM_COMEDOR, COM_SNACK, COM_ASISTENCIA, COM_CASA," +
					" COM_OTRO, COM_3, COM_2, COM_1," +
					" DEV_DIARIA, DEV_SEMANA, DEV_MENOS, IGL_UM," +
					" IGL_OTRA, IGL_NINGUNA, PRO_FAMILIAR, PRO_FINANCIERO," +
					" PRO_MATERIA, PRO_PENDIENTE, LID_ESPIRITUAL, LID_POSICION," +
					" TRA_NADA, TRA_MEDIO, TRA_COMPLETO, EST_DESAFIOS," +
					" EST_RELACIONES, EST_PROGRESO, EST_REGRESA, RIESGO_PERSONAL," +
					" RIESGO_INTEGRACION, RIESGO_RELACIONES, RIESGO_ACADEMICO, RIESGO_FINANCIERO, USUARIO"+
					" FROM ENOC.MENT_PERFIL" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND CARGA_ID = ?" +
					" AND FOLIO = TO_NUMBER(?, '9')");
					
			ps.setString(1, codigoPersonal);
			ps.setString(2, cargaId);
			ps.setString(3, folio);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentPerfil|mapeaRegId|:"+ex);
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
			ps = conn.prepareStatement("SELECT * FROM ENOC.MENT_PERFIL" + 
				" WHERE CODIGO_PERSONAL = ?" +
				" AND FOLIO = TO_NUMBER(?, '9')" +
				" AND CARGA_ID = ?");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, folio);
			ps.setString(3, cargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentPerfil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static boolean esAlumnoMentoriable( Connection conn, String codigoPersonal ) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		boolean esMentoriable	= false;
		
		try{
			ps	= conn.prepareStatement("SELECT COALESCE(CODIGO_PERSONAL,'X') AS CODIGO_PERSONAL " +
					" FROM ENOC.INSCRITOS " +
					" WHERE CODIGO_PERSONAL = ?" +
					" AND MODALIDAD_ID IN ('1','4') " +
					" AND CODIGO_PERSONAL NOT IN " +
					"	(SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ACADEMICO WHERE TIPO_ALUMNO IN (5,6)" + 
					" AND SUBSTR(CARGA_ID,6,1) NOT IN ('F','G')"+
					" AND ENOC.FACULTAD(CARRERA_ID) != '110'");
			ps.setString(1, codigoPersonal);			
			rs = ps.executeQuery();
			if (rs.next()) 
				esMentoriable = true;	
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentorPerfil|esAlumnoMentoriable|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
		return esMentoriable;
	}
	
	public static int alumMenFac( Connection conn, String facultadId ) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		int numAlumnos			= 0;
		
		try{
			ps	= conn.prepareStatement("SELECT COALESCE(COUNT(CODIGO_PERSONAL),0) AS NUM_ALUMNOS " +
					" FROM ENOC.INSCRITOS " +
					" WHERE MODALIDAD_ID IN (1,4) " +
					" AND CODIGO_PERSONAL NOT IN " +
					"	(SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ACADEMICO WHERE TIPO_ALUMNO IN (5,6))" + 
					" AND ENOC.FACULTAD(CARRERA_ID) != '110'" +
					" AND SUBSTR(CARGA_ID,6,1) NOT IN ('F','G')"+
					" AND ENOC.FACULTAD(CARRERA_ID) = ?");
			ps.setString(1, facultadId);			
			rs = ps.executeQuery();
			if (rs.next()) 
				numAlumnos = rs.getInt("NUM_ALUMNOS");
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentPerfil|alumMenFac|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
		return numAlumnos;
	}
	
	public static int alumMenCarr( Connection conn, String carreraId ) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		int numAlumnos			= 0;
		
		try{
			ps	= conn.prepareStatement("SELECT COALESCE(COUNT(CODIGO_PERSONAL),0) AS NUM_ALUMNOS " +
					" FROM ENOC.INSCRITOS " +
					" WHERE MODALIDAD_ID IN ('1','4') " +
					" AND CODIGO_PERSONAL NOT IN " +
					"	(SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ACADEMICO WHERE TIPO_ALUMNO IN (5,6))" + 
					" AND ENOC.FACULTAD(CARRERA_ID) != '110'" +
					" AND SUBSTR(CARGA_ID,6,1) NOT IN ('F','G')"+
					" AND CARRERA_ID = ?");
			ps.setString(1, carreraId);
			rs = ps.executeQuery();
			if (rs.next()) 
				numAlumnos = rs.getInt("NUM_ALUMNOS");
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentPerfil|alumMenCarr|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
		return numAlumnos;
	}
	
	public static String getPerfilIngreso(Connection conn, String codigoPersonal, String cargaId ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String perfil		= "";
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(FOLIO) AS PERFIL_INGRESO FROM ENOC.MENT_PERFIL WHERE CODIGO_PERSONAL = ? " + 
					"AND CARGA_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cargaId);
			rs = ps.executeQuery();
			if (rs.next())
				perfil = rs.getString("PERFIL_INGRESO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentPerfil|getPerfilIngreso|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return perfil;
	}
	
	public static String getPerfilFolio(Connection conn, String codigoPersonal, String cargaId, String mentorId, String folio ) throws SQLException{
			
			PreparedStatement ps	= null;
			ResultSet 		rs		= null;
			String pFolio			= "0" ;
			
			try{
				ps = conn.prepareStatement("SELECT FOLIO FROM ENOC.MENT_PERFIL WHERE CODIGO_PERSONAL = ? " + 
						"AND CARGA_ID = ? AND MENTOR_ID = ? AND FOLIO = ? ");
				ps.setString(1, codigoPersonal);
				ps.setString(2, cargaId);
				ps.setString(3, mentorId);
				ps.setString(4, folio);
				rs = ps.executeQuery();
				if (rs.next())
					pFolio = rs.getString("FOLIO");
				
			}catch(Exception ex){
				System.out.println("Error - aca.mentores.MentPerfil|getPerfilIngreso|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { ps.close(); } catch (Exception ignore) { }
			}
			
			return pFolio;
		}
}