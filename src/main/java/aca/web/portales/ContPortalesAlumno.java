package aca.web.portales;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import aca.NotificationService;
import aca.TareasProgramadas;
import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoConfirmar;
import aca.acceso.spring.AccesoConfirmarDao;
import aca.acceso.spring.AccesoValida;
import aca.acceso.spring.AccesoValidaDao;
import aca.afe.spring.FesCcAfeAcuerdosDao;
import aca.alerta.spring.AlertaCovid;
import aca.alerta.spring.AlertaCovidDao;
import aca.alerta.spring.AlertaDocAlum;
import aca.alerta.spring.AlertaDocAlumDao;
import aca.alerta.spring.AlertaPeriodoDao;
import aca.alumno.spring.AlumAcademico;
import aca.alumno.spring.AlumAsesorDao;
import aca.alumno.spring.AlumBeca;
import aca.alumno.spring.AlumBecaDao;
import aca.alumno.spring.AlumDocumento;
import aca.alumno.spring.AlumDocumentoDao;
import aca.alumno.spring.AlumEstado;
import aca.alumno.spring.AlumEstadoDao;
import aca.alumno.spring.AlumEstudio;
import aca.alumno.spring.AlumEstudioDao;
import aca.alumno.spring.AlumFamilia;
import aca.alumno.spring.AlumHermanos;
import aca.alumno.spring.AlumImagen;
import aca.alumno.spring.AlumImagenDao;
import aca.alumno.spring.AlumPatrocinador;
import aca.alumno.spring.AlumPatrocinadorDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumBanco;
import aca.alumno.spring.AlumBancoDao;
import aca.alumno.spring.AlumReferencia;
import aca.alumno.spring.AlumReferenciaDao;
import aca.alumno.spring.AlumUbicacion;
import aca.alumno.spring.AlumUbicacionDao;
import aca.alumno.spring.CancelaEstudio;
import aca.alumno.spring.CancelaEstudioDao;
import aca.apFisica.spring.ApFisicaAlumno;
import aca.apFisica.spring.ApFisicaGrupo;
import aca.archivo.spring.ArchDocAlum;
import aca.archivo.spring.ArchDocStatus;
import aca.archivo.spring.ArchDocStatusDao;
import aca.archivo.spring.ArchEntrega;
import aca.archivo.spring.ArchEntregaDao;
import aca.archivo.spring.ArchGrupo;
import aca.archivo.spring.ArchGrupoDao;
import aca.archivo.spring.ArchGrupoDocumento;
import aca.archivo.spring.ArchGrupoDocumentoDao;
import aca.archivo.spring.ArchGrupoPlanDao;
import aca.archivo.spring.ArchGruposCarreraDao;
import aca.archivo.spring.ArchGruposDao;
import aca.archivo.spring.ArchPermisos;
import aca.archivo.spring.ArchPermisosDao;
import aca.archivo.spring.ArchivoDao;
import aca.archivos.spring.ArchivosAlumno;
import aca.archivos.spring.ArchivosAlumnoDao;
import aca.archivos.spring.ArchivosProfesor;
import aca.archivos.spring.ArchivosProfesorDao;
import aca.attache.spring.AttacheCustomer;
import aca.attache.spring.AttacheCustomerAdjustmentTransaction;
import aca.attache.spring.AttacheCustomerAdjustmentTransactionDao;
import aca.attache.spring.AttacheCustomerDao;
import aca.attache.spring.AttacheCustomerInvoiceTransaction;
import aca.attache.spring.AttacheCustomerInvoiceTransactionDao;
import aca.attache.spring.AttacheCustomerPaymentTransaction;
import aca.attache.spring.AttacheCustomerPaymentTransactionDao;
import aca.bec.spring.BecAcuerdoDao;
import aca.bec.spring.BecCategoria;
import aca.bec.spring.BecCategoriaDao;
import aca.bec.spring.BecContrato;
import aca.bec.spring.BecContratoDao;
import aca.bec.spring.BecInforme;
import aca.bec.spring.BecInformeAlumno;
import aca.bec.spring.BecInformeAlumnoDao;
import aca.bec.spring.BecInformeDao;
import aca.bec.spring.BecPeriodo;
import aca.bec.spring.BecPeriodoDao;
import aca.bec.spring.BecPlazas;
import aca.bec.spring.BecPlazasDao;
import aca.bec.spring.BecPuesto;
import aca.bec.spring.BecPuestoAlumno;
import aca.bec.spring.BecPuestoAlumnoDao;
import aca.bec.spring.BecPuestoDao;
import aca.bec.spring.BecSolPeriodo;
import aca.bec.spring.BecSolPeriodoDao;
import aca.bec.spring.BecSolicitud;
import aca.bec.spring.BecSolicitudDao;
import aca.bec.spring.BecTipo;
import aca.bec.spring.BecTipoDao;
import aca.bitacora.spring.BitAreaDao;
import aca.bitacora.spring.BitEtiqueta;
import aca.bitacora.spring.BitRequisito;
import aca.bitacora.spring.BitRequisitoAlumno;
import aca.bitacora.spring.BitRequisitoAlumnoDao;
import aca.bitacora.spring.BitRequisitoDao;
import aca.bitacora.spring.BitSolicitud;
import aca.bitacora.spring.BitSolicitudDao;
import aca.bitacora.spring.BitTramite;
import aca.bitacora.spring.BitTramiteAlumno;
import aca.bitacora.spring.BitTramiteRequisito;
import aca.bitacora.spring.BitTramiteRequisitoDao;
import aca.calcula.spring.CalAlumno;
import aca.calcula.spring.CalAlumnoDao;
import aca.calcula.spring.CalConcepto;
import aca.calcula.spring.CalConceptoDao;
import aca.calcula.spring.CalCosto;
import aca.calcula.spring.CalCostoDao;
import aca.calcula.spring.CalMovimiento;
import aca.calcula.spring.CalMovimientoDao;
import aca.calcula.spring.CalPagare;
import aca.calcula.spring.CalPagareAlumno;
import aca.calcula.spring.CalPagareAlumnoDao;
import aca.calcula.spring.CalPagareDao;
import aca.candado.spring.CandAlumno;
import aca.candado.spring.CandTipo;
import aca.candado.spring.CandTipoDao;
import aca.candado.spring.Candado;
import aca.candado.spring.CandadoDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaAlumno;
import aca.carga.spring.CargaBloque;
import aca.carga.spring.CargaBloqueDao;
import aca.carga.spring.CargaEnLinea;
import aca.carga.spring.CargaEnLineaDao;
import aca.carga.spring.CargaFinanciero;
import aca.carga.spring.CargaFinancieroDao;
import aca.carga.spring.CargaGrupo;
import aca.carga.spring.CargaGrupoActividad;
import aca.carga.spring.CargaGrupoActividadDao;
import aca.carga.spring.CargaGrupoAsistenciaDao;
import aca.carga.spring.CargaGrupoCursoDao;
import aca.carga.spring.CargaGrupoEvaluacion;
import aca.carga.spring.CargaGrupoEvaluacionDao;
import aca.carga.spring.CargaGrupoHoraDao;
import aca.carga.spring.CargaGrupoProgramacion;
import aca.carga.spring.CargaPracticaAlumno;
import aca.carga.spring.CargaPracticaAlumnoDao;
import aca.catalogo.spring.CatCiudad;
import aca.catalogo.spring.CatAreaDao;
import aca.catalogo.spring.CatAvanceDao;
import aca.catalogo.spring.CatBanco;
import aca.catalogo.spring.CatBancoDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCiudadDao;
import aca.catalogo.spring.CatCultural;
import aca.catalogo.spring.CatCulturalDao;
import aca.catalogo.spring.CatEstado;
import aca.catalogo.spring.CatEstadoDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatGradePointDao;
import aca.catalogo.spring.CatHorarioFacultad;
import aca.catalogo.spring.CatHorarioFacultadDao;
import aca.catalogo.spring.CatInstitucionDao;
import aca.catalogo.spring.CatModalidad;
import aca.catalogo.spring.CatModalidadDao;
import aca.catalogo.spring.CatNivel;
import aca.catalogo.spring.CatNivelDao;
import aca.catalogo.spring.CatNivelInicio;
import aca.catalogo.spring.CatNivelInicioDao;
import aca.catalogo.spring.CatPais;
import aca.catalogo.spring.CatPaisDao;
import aca.catalogo.spring.CatPatrocinador;
import aca.catalogo.spring.CatPatrocinadorDao;
import aca.catalogo.spring.CatPeriodoDao;
import aca.catalogo.spring.CatRegion;
import aca.catalogo.spring.CatRegionDao;
import aca.catalogo.spring.CatReligion;
import aca.catalogo.spring.CatReligionDao;
import aca.catalogo.spring.CatSalon;
import aca.catalogo.spring.CatSalonDao;
import aca.catalogo.spring.CatTipoAlumnoDao;
import aca.catalogo.spring.CatTipoCal;
import aca.catalogo.spring.CatTipoCurso;
import aca.conva.spring.ConvEvento;
import aca.conva.spring.ConvHistorial;
import aca.conva.spring.ConvMateria;
import aca.conva.spring.ConvMateriaDao;
import aca.covid.spring.CovidDao;
import aca.covid.spring.CovidPeriodoDao;
import aca.disciplina.spring.CondAlumno;
import aca.disciplina.spring.CondReporte;
import aca.edo.spring.Edo;
import aca.edo.spring.EdoAlumnoPreg;
import aca.edo.spring.EdoAlumnoPregDao;
import aca.edo.spring.EdoAlumnoResp;
import aca.edo.spring.EdoAlumnoRespDao;
import aca.emp.spring.EmpContacto;
import aca.emp.spring.EmpContactoDao;
import aca.emp.spring.EmpMaestroDao;
import aca.encuesta.spring.EncPeriodo;
import aca.encuesta.spring.EncPeriodoDao;
import aca.encuesta.spring.EncPeriodoRes;
import aca.encuesta.spring.EncPeriodoResDao;
import aca.financiero.spring.A3lAsalfldg;
import aca.financiero.spring.AyudaEstudios;
import aca.financiero.spring.AyudaEstudiosDao;
import aca.financiero.spring.ContCcosto;
import aca.financiero.spring.ContCcostoDao;
import aca.financiero.spring.ContConcepto;
import aca.financiero.spring.ContEjercicio;
import aca.financiero.spring.ContEjercicioDao;
import aca.financiero.spring.ContMovimiento;
import aca.financiero.spring.ContSaldosRip;
import aca.financiero.spring.FesCcPagareDetDao;
import aca.financiero.spring.FesCcobro;
import aca.financiero.spring.FinCalculo;
import aca.financiero.spring.FinCalculoDao;
import aca.financiero.spring.FinComentario;
import aca.financiero.spring.FinPeriodo;
import aca.financiero.spring.FinPeriodoDao;
import aca.financiero.spring.FinPermisoDao;
import aca.financiero.spring.FinRolesDao;
import aca.financiero.spring.FinSaldo;
import aca.fulton.spring.Student;
import aca.fulton.spring.StudentDao;
import aca.fulton.spring.StudentTransactions;
import aca.fulton.spring.StudentTransactionsDao;
import aca.graduacion.spring.AlumEgreso;
import aca.graduacion.spring.AlumEgresoDao;
import aca.graduacion.spring.AlumEvento;
import aca.graduacion.spring.AlumEventoDao;
import aca.internado.spring.ComAutorizacion;
import aca.internado.spring.ComAutorizacionDao;
import aca.internado.spring.IntAlumno;
import aca.internado.spring.IntAlumnoDao;
import aca.internado.spring.IntDormitorio;
import aca.internado.spring.IntDormitorioDao;
import aca.kardex.spring.KrdxAlumnoActiv;
import aca.kardex.spring.KrdxAlumnoActivDao;
import aca.kardex.spring.KrdxAlumnoEvalDao;
import aca.kardex.spring.KrdxCursoAct;
import aca.kardex.spring.KrdxCursoActDao;
import aca.leg.spring.LegDocumentoDao;
import aca.leg.spring.LegExtdoctos;
import aca.leg.spring.LegExtdoctosDao;
import aca.log.spring.LogOperacion;
import aca.log.spring.LogOperacionDao;
import aca.matricula.spring.MatAlumno;
import aca.matricula.spring.MatAlumnoDao;
import aca.matricula.spring.MatEvento;
import aca.matricula.spring.MatEventoDao;
import aca.mentores.spring.MentAlumnoDao;
import aca.mentores.spring.MentEncuesta;
import aca.mentores.spring.MentEncuestaDao;
import aca.mentores.spring.MentPregunta;
import aca.mentores.spring.MentPreguntaDao;
import aca.mentores.spring.MentRespuesta;
import aca.mentores.spring.MentRespuestaDao;
import aca.mov.Claims;
import aca.mov.User;
import aca.mov.UserPas;
import aca.musica.spring.MusiAutorizaDao;
import aca.musica.spring.MusiHorario;
import aca.musica.spring.MusiHorarioAlumno;
import aca.musica.spring.MusiHorarioAlumnoDao;
import aca.musica.spring.MusiHorarioDao;
import aca.notifica.spring.NotiCovid;
import aca.notifica.spring.NotiMensajes;
import aca.notifica.spring.NotiMensajesDao;
import aca.nse.spring.NseEncuesta;
import aca.nse.spring.NseEncuestaDao;
import aca.nse.spring.NsePregunta;
import aca.nse.spring.NsePreguntaDao;
import aca.nse.spring.NseRespuesta;
import aca.nse.spring.NseRespuestaDao;
import aca.nse.spring.NseRespuestaPregunta;
import aca.nse.spring.NseRespuestaPreguntaDao;
import aca.objeto.spring.Hora;
import aca.objeto.spring.HoraDao;
import aca.padre.spring.PadreAlumno;
import aca.padre.spring.PadreAlumnoDao;
import aca.padre.spring.PadrePersonal;
import aca.padre.spring.PadrePersonalDao;
import aca.parametros.spring.Parametros;
import aca.parametros.spring.ParametrosDao;
import aca.pg.archivo.spring.PosArchDocAlum;
import aca.pg.archivo.spring.PosFotoAlum;
import aca.pg.archivo.spring.PosFotoAlumDao;
import aca.plan.spring.MapaArchivo;
import aca.plan.spring.MapaAvance;
import aca.plan.spring.MapaCredito;
import aca.plan.spring.MapaCreditoDao;
import aca.plan.spring.MapaCurso;
import aca.plan.spring.MapaMayorMenor;
import aca.plan.spring.MapaMayorMenorDao;
import aca.plan.spring.MapaPlan;
import aca.residencia.spring.ResDatos;
import aca.residencia.spring.ResDatosDao;
import aca.residencia.spring.ResDocumento;
import aca.residencia.spring.ResDocumentoDao;
import aca.residencia.spring.ResExpediente;
import aca.residencia.spring.ResExpedienteDao;
import aca.residencia.spring.ResRazonDao;
import aca.sep.spring.SepAlumno;
import aca.ssoc.spring.SsocAsignacion;
import aca.ssoc.spring.SsocAsignacionDao;
import aca.ssoc.spring.SsocDocAlum;
import aca.ssoc.spring.SsocDocAlumDao;
import aca.ssoc.spring.SsocDocumentosDao;
import aca.ssoc.spring.SsocInicioDao;
import aca.ssoc.spring.SsocRequisito;
import aca.ssoc.spring.SsocRequisitoDao;
import aca.tit.spring.TitAlumno;
import aca.tit.spring.TitAlumnoDao;
import aca.trabajo.spring.TrabAlum;
import aca.trabajo.spring.TrabAlumDao;
import aca.trabajo.spring.TrabAsesor;
import aca.trabajo.spring.TrabAsesorDao;
import aca.trabajo.spring.TrabCategoria;
import aca.trabajo.spring.TrabCategoriaDao;
import aca.trabajo.spring.TrabDepartamentoDao;
import aca.trabajo.spring.TrabInformeAlum;
import aca.trabajo.spring.TrabInformeAlumDao;
import aca.trabajo.spring.TrabPeriodo;
import aca.trabajo.spring.TrabPeriodoDao;
import aca.util.Fecha;
import aca.vigilancia.spring.VigAuto;
import aca.vigilancia.spring.VigAutoDao;
import aca.vigilancia.spring.VigDoc;
import aca.vigilancia.spring.VigDocAutoDao;
import aca.vigilancia.spring.VigDocDao;
import aca.vigilancia.spring.VigDocumentoDao;
import aca.vista.spring.AlumnoCurso;
import aca.vista.spring.AlumnoEficiencia;
import aca.vista.spring.AlumnoEficienciaDao;
import aca.vista.spring.AlumnoEvaluacionDao;
import aca.vista.spring.CargaAcademica;
import aca.vista.spring.CargaAcademicaDao;
import aca.vista.spring.Estadistica;
import aca.vista.spring.EstadisticaDao;
import aca.vista.spring.FinTabla;
import aca.vista.spring.FinTablaDao;
import aca.vista.spring.Inscritos;
import aca.vista.spring.Maestros;
import aca.voto.spring.VotoAlumno;
import aca.voto.spring.VotoCandidato;
import aca.voto.spring.VotoCandidatoAlumno;
import aca.voto.spring.VotoCandidatoAlumnoDao;
import aca.voto.spring.VotoCandidatoDao;
import aca.voto.spring.VotoEvento;
import net.glxn.qrgen.javase.QRCode;

@Controller
public class ContPortalesAlumno {

	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;

	@Autowired
	aca.cita.spring.CitaEventoDao citaEventoDao;

	@Autowired
	aca.cita.spring.CitaPeriodoDao citaPeriodoDao;

	@Autowired
	aca.cita.spring.CitaReservadaDao citaReservadaDao;

	@Autowired
	aca.bitacora.spring.BitEtiquetaDao bitEtiquetaDao;

	@Autowired
	aca.bitacora.spring.BitTramiteDao bitTramiteDao;

	@Autowired
	aca.bitacora.spring.BitEstadoDao bitEstadoDao;

	@Autowired
	aca.bitacora.spring.BitTramiteAlumnoDao bitTramiteAlumnoDao;

	@Autowired
	aca.alumno.spring.AlumPersonalDao alumPersonalDao;

	@Autowired
	private LegExtdoctosDao legExtdoctosDao;

	@Autowired
	aca.voto.spring.VotoEventoDao votoEventoDao;

	@Autowired
	VotoCandidatoDao votoCandidatoDao;

	@Autowired
	aca.voto.spring.VotoAlumnoDao votoAlumnoDao;

	@Autowired
	aca.conva.spring.ConvEventoDao convEventoDao;

	@Autowired
	aca.conva.spring.ConvHistorialDao convHistorialDao;

	@Autowired
	aca.por.spring.PorHoraDao porHoraDao;

	@Autowired
	aca.por.spring.PorRegistroDao porRegistroDao;

	@Autowired
	aca.plan.spring.MapaArchivoDao mapaArchivoDao;

	@Autowired
	aca.alumno.spring.AlumHermanosDao alumHermanosDao;

	@Autowired
	aca.alumno.spring.AlumFamiliaDao alumFamiliaDao;

	@Autowired
	aca.financiero.spring.FinComentarioDao finComentarioDao;

	@Autowired
	aca.financiero.spring.FesCcobroDao fesCcobroDao;

	@Autowired
	aca.financiero.spring.ContConceptoDao contConceptoDao;

	@Autowired
	aca.financiero.spring.ContMovimientoDao contMovimientoDao;

	@Autowired
	aca.financiero.spring.A3lAsalfldgDao a3lAsalfldgDao;

	@Autowired
	aca.vista.spring.MaestrosDao maestrosDao;

	@Autowired
	aca.financiero.spring.ContSaldosRipDao contSaldosRipDao;

	@Autowired
	aca.financiero.spring.A3lAnlCodeDao a3lAnlCodeDao;

	@Autowired
	aca.vista.spring.UsuariosDao usuariosDao;

	@Autowired
	aca.carga.spring.CargaAlumnoDao cargaAlumnoDao;

	@Autowired
	aca.archivo.spring.ArchDocAlumDao archDocAlumDao;

	@Autowired
	aca.archivo.spring.ArchDocumentosDao archDocumentosDao;

	@Autowired
	aca.archivo.spring.ArchStatusDao archStatusDao;

	@Autowired
	aca.archivo.spring.ArchUbicacionDao archUbicacionDao;

	@Autowired
	aca.alumno.spring.AlumPlanDao alumPlanDao;

	@Autowired
	aca.plan.spring.MapaPlanDao mapaPlanDao;

	@Autowired
	aca.catalogo.spring.CatFacultadDao catFacultadDao;

	@Autowired
	aca.catalogo.spring.CatCarreraDao catCarreraDao;

	@Autowired
	aca.disciplina.spring.CondAlumnoDao condAlumnoDao;

	@Autowired
	aca.disciplina.spring.CondReporteDao condReporteDao;

	@Autowired
	aca.disciplina.spring.CondLugarDao condLugarDao;

	@Autowired
	aca.disciplina.spring.CondJuezDao condJuezDao;

	@Autowired
	aca.acceso.spring.AccesoDao accesoDao;

	@Autowired
	aca.vista.spring.InscritosDao inscritosDao;

	@Autowired
	aca.alumno.spring.AlumAcademicoDao alumAcademicoDao;

	@Autowired
	aca.plan.spring.MapaAvanceDao mapaAvanceDao;

	@Autowired
	aca.catalogo.spring.CatTipoCursoDao catTipoCursoDao;

	@Autowired
	BecPuestoAlumnoDao becPuestoAlumnoDao;

	@Autowired
	MentAlumnoDao mentAlumnoDao;

	@Autowired
	CatPeriodoDao catPeriodoDao;

	@Autowired
	AlumUbicacionDao alumUbicacionDao;

	@Autowired
	CatReligionDao catReligionDao;

	@Autowired
	CatBancoDao catBancoDao;

	@Autowired
	CatInstitucionDao catInstitucionDao;

	@Autowired
	CatTipoAlumnoDao catTipoAlumnoDao;

	@Autowired
	CatPaisDao catPaisDao;

	@Autowired
	CatEstadoDao catEstadoDao;

	@Autowired
	CatCiudadDao catCiudadDao;

	@Autowired
	ContCcostoDao contCcostoDao;

	@Autowired
	BecInformeDao becInformeDao;

	@Autowired
	BecInformeAlumnoDao becInformeAlumnoDao;

	@Autowired
	BecAcuerdoDao becAcuerdoDao;

	@Autowired
	BecCategoriaDao becCategoriaDao;

	@Autowired
	ResDatosDao resDatosDao;

	@Autowired
	ResRazonDao resRazonDao;

	@Autowired
	aca.carga.spring.CargaDao cargaDao;

	@Autowired
	aca.carga.spring.CargaGrupoProgramacionDao cargaGrupoProgramacionDao;

	@Autowired
	aca.vista.spring.AlumnoCursoDao alumnoCursoDao;

	@Autowired
	aca.plan.spring.MapaCursoElectivaDao mapaCursoElectivaDao;

	@Autowired
	CargaGrupoAsistenciaDao cargaGrupoAsistenciaDao;

	@Autowired
	CargaBloqueDao cargaBloqueDao;

	@Autowired
	CargaGrupoCursoDao cargaGrupoCursoDao;

	@Autowired
	CancelaEstudioDao cancelaEstudioDao;

	@Autowired
	aca.kardex.spring.KrdxCursoActDao krdxCursoActDao;

	@Autowired
	aca.catalogo.spring.CatTipoCalDao catTipoCalDao;

	@Autowired
	aca.plan.spring.MapaCursoDao mapaCursoDao;

	@Autowired
	aca.carga.spring.CargaGrupoDao cargaGrupoDao;

	@Autowired
	aca.edo.spring.EdoDao edoDao;

	@Autowired
	aca.apFisica.spring.ApFisicaAlumnoDao apFisicaAlumnoDao;

	@Autowired
	aca.apFisica.spring.ApFisicaGrupoDao apFisicaGrupoDao;

	@Autowired
	SsocInicioDao ssocInicioDao;

	@Autowired
	SsocDocAlumDao ssocDocAlumDao;

	@Autowired
	SsocDocumentosDao ssocDocumentosDao;

	@Autowired
	SsocAsignacionDao ssocAsignacionDao;

	@Autowired
	BitAreaDao bitAreaDao;

	@Autowired
	ParametrosDao parametrosDao;

	@Autowired
	ArchivoDao archivoDao;

	@Autowired
	FesCcPagareDetDao fesCcPagareDetDao;

	@Autowired
	CatAvanceDao catAvanceDao;

	@Autowired
	ConvMateriaDao convMateriaDao;

	@Autowired
	ArchGruposDao archGruposDao;

	@Autowired
	ArchGruposCarreraDao archGruposCarreraDao;

	@Autowired
	CargaGrupoEvaluacionDao cargaGrupoEvaluacionDao;

	@Autowired
	CargaGrupoActividadDao cargaGrupoActividadDao;

	@Autowired
	KrdxAlumnoEvalDao krdxAlumnoEvalDao;

	@Autowired
	BecPlazasDao becPlazasDao;

	@Autowired
	KrdxAlumnoActivDao krdxAlumnoActivDao;

	@Autowired
	AlumnoEficienciaDao alumnoEficienciaDao;

	@Autowired
	FinPeriodoDao finPeriodoDao;

	@Autowired
	aca.candado.spring.CandAlumnoDao candAlumnoDao;

	@Autowired
	EdoAlumnoRespDao edoAlumnoRespDao;

	@Autowired
	AlumReferenciaDao alumReferenciaDao;

	@Autowired
	FinPermisoDao finPermisoDao;

	@Autowired
	CandTipoDao candTipoDao;

	@Autowired
	aca.sep.spring.SepAlumnoDao sepAlumnoDao;

	@Autowired
	private aca.pg.archivo.spring.PosArchDocAlumDao posArchDocAlumDao;

	@Autowired
	private EdoAlumnoPregDao edoAlumnoPregDao;

	@Autowired
	private CatAreaDao catAreaDao;

	@Autowired
	private CancelaEstudioDao cancelaEstudiodoDao;

	@Autowired
	private MapaCreditoDao mapaCreditoDao;

	@Autowired
	private AlumnoEvaluacionDao alumnoEvaluacionDao;

	@Autowired
	private EstadisticaDao estadisticaDao;

	@Autowired
	private ComAutorizacionDao comAutorizacionDao;

	@Autowired
	private LegDocumentoDao legDocumentoDao;

	@Autowired
	private MusiHorarioDao musiHorarioDao;

	@Autowired
	private MusiHorarioAlumnoDao musiHorarioAlumnoDao;

	@Autowired
	private MusiAutorizaDao musiAutorizaDao;

	@Autowired
	private ArchivosProfesorDao archivosProfesorDao;

	@Autowired
	private ArchivosAlumnoDao archivosAlumnoDao;

	@Autowired
	private TitAlumnoDao titAlumnoDao;

	@Autowired
	private BecPeriodoDao becPeriodoDao;

	@Autowired
	CovidDao covidDao;

	@Autowired
	CovidPeriodoDao covidPeriodoDao;

	@Autowired
	AyudaEstudiosDao ayudaEstudiosDao;

	@Autowired
	CandadoDao candadoDao;

	@Autowired
	private ArchPermisosDao archPermisosDao;

	@Autowired
	private CatNivelDao catNivelDao;

	@Autowired
	private CargaEnLineaDao cargaEnLineaDao;

	@Autowired
	CatModalidadDao catModalidadDao;

	@Autowired
	CargaAcademicaDao cargaAcademicaDao;

	@Autowired
	AlumAsesorDao alumAsesorDao;
	
	@Autowired
	AlumBancoDao alumBancoDao;
	
	@Autowired
	AlumPatrocinadorDao alumPatrocinadorDao;
	
	@Autowired
	AlumEstadoDao alumEstadoDao;

	@Autowired
	EmpContactoDao empContactoDao;

	@Autowired
	MatAlumnoDao matAlumnoDao;

	@Autowired
	private ContEjercicioDao contEjercicioDao;

	@Autowired
	private LogOperacionDao logOperacionDao;

	@Autowired
	private AccesoConfirmarDao accesoConfirmarDao;

	@Autowired
	private MatEventoDao matEventoDao;

	@Autowired
	private AlumBecaDao alumBecaDao;

	@Autowired
	private AlumDocumentoDao alumDocumentoDao;

	@Autowired
	private ArchGrupoPlanDao archGrupoPlanDao;

	@Autowired
	private ArchGrupoDocumentoDao archGrupoDocumentoDao;

	@Autowired
	private ArchDocStatusDao archDocStatusDao;

	@Autowired
	private ArchGrupoDao archGrupoDao;

	@Autowired
	BecSolicitudDao becSolicitudDao;

	@Autowired
	private NotiMensajesDao notiMensajesDao;

	@Autowired
	private CargaGrupoHoraDao cargaGrupoHoraDao;

	@Autowired
	private CatHorarioFacultadDao catHorarioFacultadDao;

	@Autowired
	private HoraDao horaDao;

	@Autowired
	private AccesoValidaDao accesoValidaDao;

	@Autowired
	private VotoCandidatoAlumnoDao votoCandidatoAlumnoDao;

	@Autowired
	private PosFotoAlumDao posFotoAlumDao;

	@Autowired
	ArchEntregaDao archEntregaDao;

	@Autowired
	CargaPracticaAlumnoDao cargaPracticaAlumnoDao;

	@Autowired
	private AlertaCovidDao alertaCovidDao;

	@Autowired
	private FinRolesDao finRolesDao;

	@Autowired
	private CatSalonDao catSalonDao;

	@Autowired
	PadreAlumnoDao padreAlumnoDao;

	@Autowired
	PadrePersonalDao padrePersonalDao;

	@Autowired
	BecSolPeriodoDao becSolPeriodoDao;

	@Autowired
	ResExpedienteDao resExpedienteDao;

	@Autowired
	ResDocumentoDao resDocumentoDao;

	@Autowired
	AlumImagenDao alumImagenDao;

	@Autowired
	BecContratoDao becContratoDao;

	@Autowired
	FesCcAfeAcuerdosDao fesCcAfeAcuerdosDao;

	@Autowired
	SsocRequisitoDao sscoRequisitoDao;

	@Autowired
	AlertaPeriodoDao alertaPeriodoDao;

	@Autowired
	AlertaDocAlumDao alertaDocAlumDao;

	@Autowired
	BitSolicitudDao bitSolicitudDao;

	@Autowired
	BitTramiteRequisitoDao bitTramiteRequisitoDao;

	@Autowired
	BitRequisitoAlumnoDao bitRequisitoAlumnoDao;

	@Autowired
	BitRequisitoDao bitRequisitoDao;

	@Autowired
	FinTablaDao finTablaDao;

	@Autowired
	CalAlumnoDao calAlumnoDao;

	@Autowired
	CalCostoDao calCostoDao;

	@Autowired
	CalConceptoDao calConceptoDao;

	@Autowired
	CalPagareDao calPagareDao;

	@Autowired
	CalMovimientoDao calMovimientoDao;

	@Autowired
	CalPagareAlumnoDao calPagareAlumnoDao;

	@Autowired
	AlumEventoDao alumEventoDao;

	@Autowired
	AlumEgresoDao alumEgresoDao;

	@Autowired
	EncPeriodoResDao encPeriodoResDao;

	@Autowired
	EncPeriodoDao encPeriodoDao;

	@Autowired
	MentEncuestaDao mentEncuestaDao;

	@Autowired
	MentPreguntaDao mentPreguntaDao;

	@Autowired
	MentRespuestaDao mentRespuestaDao;
	
	@Autowired
	BecPuestoDao becPuestoDao;
	
	@Autowired
	private NsePreguntaDao nsePreguntaDao;
	
	@Autowired
	private NseEncuestaDao nseEncuestaDao;
	
	@Autowired
	private NseRespuestaDao nseRespuestaDao;
	
	@Autowired
	private NseRespuestaPreguntaDao nseRespuestaPreguntaDao;
	
	@Autowired
	BecTipoDao becTipoDao;
	
	@Autowired
	VigAutoDao vigAutoDao;
	
	@Autowired
	VigDocDao vigDocDao;
	
	@Autowired
	VigDocumentoDao vigDocumentoDao;
	
	@Autowired
	VigDocAutoDao vigDocAutoDao;
	
	@Autowired
	KrdxCursoActDao krdxKursoActDao;
	
	@Autowired
	CatPatrocinadorDao catPatrocinadorDao;
	
	@Autowired
	AlumEstudioDao alumEstudioDao;
	
	@Autowired
	IntDormitorioDao intDormitorioDao;

	@Autowired
	IntAlumnoDao intAlumnoDao;
	
	@Autowired
	EmpMaestroDao empMaestroDao;
	
	@Autowired
	FinCalculoDao finCalculoDao;
	
	@Autowired
	CargaFinancieroDao cargaFinancieroDao;

	@Autowired
	private CatGradePointDao catGradePointDao;

	@Autowired
	private MapaMayorMenorDao mapaMayorMenorDao;

	@Autowired
	private CatNivelInicioDao catNivelInicioDao;

	@Autowired
	private StudentDao studentDao;

	@Autowired
	private StudentTransactionsDao studentTransactionsDao;

	@Autowired
	private AttacheCustomerDao attacheCustomerDao;
	
	@Autowired
	private AttacheCustomerInvoiceTransactionDao attacheCustomerInvoiceTransactionDao;

	@Autowired
	private AttacheCustomerPaymentTransactionDao attacheCustomerPaymentTransactionDao;

	@Autowired
	private AttacheCustomerAdjustmentTransactionDao attacheCustomerAdjustmentTransactionDao;

	@Autowired
	private TrabAlumDao trabAlumDao;

	@Autowired
	private TrabInformeAlumDao trabInformeAlumDao;

	@Autowired
	private TrabDepartamentoDao trabDepartamentoDao;

	@Autowired
	private TrabCategoriaDao trabCategoriaDao;

	@Autowired
	private TrabPeriodoDao trabPeriodoDao;

	@Autowired
	private TrabAsesorDao trabAsesorDao;

	@Autowired
	private CatCulturalDao catCulturalDao;

	@Autowired
	private CatRegionDao catRegionDao;

	@Autowired
    private NotificationService notificationService;
	
	private static final Logger log = LoggerFactory.getLogger(TareasProgramadas.class);

	public void enviarConEnoc(HttpServletRequest request, String url) {
		// Enviar de la conexión
		try {
			request.setAttribute("conEnoc", enoc.getConnection());
			// System.out.println("Abrir conEnoc"+url);
		} catch (Exception ex) {
			System.out.println(url + " " + ex);
		}
	}

	@RequestMapping("/portales/alumno/hermanos")
	public String portalesAlumnoHermanos(HttpServletRequest request, Model modelo) {

		HttpSession sesion = request.getSession();

		AlumPersonal alumno = new AlumPersonal();
		AlumFamilia familia = new AlumFamilia();
		String familiaId = "0";
		String apellidos = "-";
		String nombreAlumno = "-";

		if (sesion != null) {
			String codigoAlumno = (String) sesion.getAttribute("codigoAlumno");
			alumno = alumPersonalDao.mapeaRegId(codigoAlumno);
			nombreAlumno = alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
			apellidos = alumno.getApellidoPaterno().trim() + " " + alumno.getApellidoMaterno().trim();
			if (alumHermanosDao.existeReg(codigoAlumno)) {
				familiaId = alumHermanosDao.getFamiliaId(codigoAlumno);
				familia = alumFamiliaDao.mapeaRegId(familiaId);
			}
		}

		List<aca.alumno.spring.AlumHermanos> lista = alumHermanosDao.getListFamilia(familiaId,
				" ORDER BY CODIGO_PERSONAL");
		HashMap<String, AlumPersonal> mapaFamilia = alumPersonalDao.mapaAlumnosFamilia(apellidos);

		modelo.addAttribute("lista", lista);
		modelo.addAttribute("familia", familia);
		modelo.addAttribute("apellidos", apellidos);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("mapaFamilia", mapaFamilia);

		return "portales/alumno/hermanos";
	}

	@RequestMapping("/portales/alumno/grabarHermano")
	public String portalesAlumnoGrabarHermanos(HttpServletRequest request, Model modelo) {

		String codigoAlumno = request.getParameter("CodigoAlumno") == null ? "0" : request.getParameter("CodigoAlumno");
		String codigoNuevo = request.getParameter("CodigoNuevo") == null ? "0" : request.getParameter("CodigoNuevo");
		String apellidos = request.getParameter("Apellidos") == null ? "0" : request.getParameter("Apellidos");
		String familiaId = "0";
		String mensaje = "-";
		boolean ok = true;

		AlumFamilia familia = new AlumFamilia();
		AlumHermanos hermano = new AlumHermanos();

		if (alumPersonalDao.getEsHermano(codigoNuevo, apellidos) && alumPersonalDao.existeAlumno(codigoNuevo)
				&& !alumHermanosDao.existeReg(codigoNuevo)) {
			familiaId = alumHermanosDao.getFamiliaId(codigoAlumno);
			if (familiaId.equals("0")) {
				// Crear familia
				familia.setFamiliaId(alumFamiliaDao.maximoReg());
				familia.setFecha(aca.util.Fecha.getHoy());
				familia.setEstado("A");
				if (alumFamiliaDao.insertReg(familia)) {
					// Grabar alumno principal
					familiaId = familia.getFamiliaId();
					hermano.setFamiliaId(familiaId);
					hermano.setCodigoPersonal(codigoAlumno);
					hermano.setFecha(aca.util.Fecha.getHoy());
					hermano.setEstado("A");
					if (alumHermanosDao.insertReg(hermano)) {
						ok = true;
					} else {
						ok = false;
						mensaje = "Error while saving the applicant sibling";
					}
					hermano = new AlumHermanos();
				} else {
					ok = false;
					mensaje = "Error while saving family";
				}
			}
			// Grabar alumno adicional
			hermano.setFamiliaId(familiaId);
			hermano.setCodigoPersonal(codigoNuevo);
			hermano.setFecha(aca.util.Fecha.getHoy());
			hermano.setEstado("E");
			if (ok && alumHermanosDao.insertReg(hermano)) {
				mensaje = "Saved";
			} else {
				mensaje = "Error while saving additional sibling";
			}
		} else if (!alumPersonalDao.existeAlumno(codigoNuevo)) {
			mensaje = "No ID found: " + codigoNuevo + " !";
		} else if (alumHermanosDao.existeReg(codigoNuevo)) {
			mensaje = "Sibling already registered";
		} else {
			mensaje = "Surnames for (" + codigoNuevo + ") do not match";
		}

		return "redirect:/portales/alumno/hermanos?Mensaje=" + mensaje;
	}

	@RequestMapping("/portales/alumno/autorizarHermano")
	public String portalesAlumnoAutorizarHermano(HttpServletRequest request, Model modelo) {

		String familiaId = request.getParameter("FamiliaId") == null ? "0" : request.getParameter("FamiliaId");
		String codigoAlumno = request.getParameter("CodigoAlumno") == null ? "0" : request.getParameter("CodigoAlumno");

		String mensaje = "-";

		AlumFamilia familia = new AlumFamilia();
		AlumHermanos hermano = new AlumHermanos();

		if (alumFamiliaDao.existeReg(familiaId) && alumHermanosDao.existeReg(familiaId, codigoAlumno)) {
			hermano.setFamiliaId(familiaId);
			hermano.setCodigoPersonal(codigoAlumno);
			hermano = alumHermanosDao.mapeaRegId(familiaId, codigoAlumno);
			hermano.setEstado("A");
			if (alumHermanosDao.updateReg(hermano)) {
				familia.setFamiliaId(familiaId);
				familia = alumFamiliaDao.mapeaRegId(familiaId);
				familia.setEstado("A");
				if (alumFamiliaDao.updateReg(familia)) {
					mensaje = "Accepted in the family";
				}
			} else {
				mensaje = "Error updating family status";
			}
		} else {
			mensaje = "Family or student not found";
		}

		return "redirect:/portales/alumno/hermanos?Mensaje=" + mensaje;
	}

	@RequestMapping("/portales/alumno/borrarHermano")
	public String portalesAlumnoBorrarHermano(HttpServletRequest request, Model modelo) {

		String familiaId = request.getParameter("FamiliaId") == null ? "0" : request.getParameter("FamiliaId");
		String codigoAlumno = request.getParameter("CodigoAlumno") == null ? "0" : request.getParameter("CodigoAlumno");

		String mensaje = "-";
		int numHermanos = 0;

		if (alumFamiliaDao.existeReg(familiaId) && alumHermanosDao.existeReg(familiaId, codigoAlumno)) {
			if (alumHermanosDao.deleteReg(familiaId, codigoAlumno)) {
				numHermanos = alumHermanosDao.getTotalHermanos(familiaId);
				if (numHermanos == 0 && alumFamiliaDao.deleteReg(familiaId)) {
					mensaje = "Family record deleted";
				}
			} else {
				mensaje = "Error deleting";
			}
		} else {
			mensaje = "Family or student not found";
		}

		return "redirect:/portales/alumno/hermanos?Mensaje=" + mensaje;
	}

	@RequestMapping("/portales/alumno/hermanosAccion")
	public String portalesAlumnoHermanosAccion(HttpServletRequest request) {
		enviarConEnoc(request, "Error-aca.ControllerPortales|portalesAlumnoHermanosAccion");
		return "portales/alumno/hermanosAccion";
	}

	@RequestMapping("/portales/alumno/faltantes")
	public String portalesAlumnoFaltantes(HttpServletRequest request) {
		enviarConEnoc(request, "Error-aca.ControllerPortales|portalesAlumnoFaltantes");
		return "portales/alumno/faltantes";
	}

	@RequestMapping("/portales/alumno/horario")
	public String portalesAlumnoHorario(HttpServletRequest request, Model modelo) {

		String matricula = "";
		String cargaId = request.getParameter("CargaId");

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
		}

		String horarioId = cargaGrupoHoraDao.getHorarioPrincipal(matricula, cargaId);

		List<CargaBloque> listaCargaBloque = cargaBloqueDao.getListBloqueCarga(cargaId,
				"AND BLOQUE_ID IN (SELECT BLOQUE_ID FROM ENOC.CARGA_GRUPO WHERE CARGA_ID='" + cargaId
						+ "' AND CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.KRDX_CURSO_ACT WHERE CARGA_ID='"
						+ cargaId + "' AND CODIGO_PERSONAL='" + matricula
						+ "') AND CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO_HORA WHERE CARGA_ID='"
						+ cargaId + "'))");
		List<String> lisHorarios = cargaGrupoHoraDao.horariosAlumno(matricula, cargaId);
		List<String> turno = catHorarioFacultadDao.getTurno(horarioId, "");
		List<String> turnoDif = catHorarioFacultadDao.getTurno(horarioId, "");

		String bloqueId = (request.getParameter("BloqueId") == null || request.getParameter("BloqueId").equals("null"))
				? (listaCargaBloque.isEmpty() ? "1" : listaCargaBloque.get(0).getBloqueId())
				: request.getParameter("BloqueId");
		AlumPlan alumPlan = alumPlanDao.mapeaRegId(matricula);
		AlumPersonal alumPersonal = alumPersonalDao.mapeaRegId(matricula);
		String semTetra = alumPersonalDao.getCarreraId(alumPlan.getPlanId()).startsWith("107")
				? "Tetramestre"
				: "Semestre";

		HashMap<String, Hora> mapHoras = horaDao.mapaHorasDelAlumno(matricula, cargaId, "'M','I'", bloqueId, horarioId);
		HashMap<String, String> mapCursos = horaDao.mapaCursosDelAlumnos(matricula, cargaId, "'M','I'");
		HashMap<String, Hora> mapHorasDif = new HashMap<String, Hora>();
		HashMap<String, CatSalon> mapaSalones = catSalonDao.getMapAll("ORDER BY SALON_ID");

		HashMap<String, List<CatHorarioFacultad>> mapaListaHorario = new HashMap<String, List<CatHorarioFacultad>>();
		HashMap<String, List<CatHorarioFacultad>> mapaListaHorarioDif = new HashMap<String, List<CatHorarioFacultad>>();

		for (String horario : lisHorarios) {
			mapHorasDif = horaDao.mapaHorasAdicionalesDelAlumno(matricula, cargaId, bloqueId, horarioId);
			for (String tur : turno) {
				List<CatHorarioFacultad> getListaTurno = catHorarioFacultadDao.getListaTurno(horarioId, tur,
						" ORDER BY TURNO, PERIODO");
				mapaListaHorario.put(horario + tur, getListaTurno);
			}
		}

		for (String horario : lisHorarios) {
			for (String tur : turnoDif) {
				List<CatHorarioFacultad> getListaTurno = catHorarioFacultadDao.getListaTurno(horarioId, tur,
						" ORDER BY TURNO, PERIODO");
				mapaListaHorarioDif.put(horario + tur, getListaTurno);
			}
		}

		modelo.addAttribute("SubMenu", "3");
		modelo.addAttribute("matricula", matricula);
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("horarioId", horarioId);
		modelo.addAttribute("bloqueId", bloqueId);
		modelo.addAttribute("semTetra", semTetra);
		modelo.addAttribute("alumPlan", alumPlan);
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("listaCargaBloque", listaCargaBloque);
		modelo.addAttribute("lisHorarios", lisHorarios);
		modelo.addAttribute("turno", turno);
		modelo.addAttribute("turnoDif", turnoDif);
		modelo.addAttribute("mapHoras", mapHoras);
		modelo.addAttribute("mapHorasDif", mapHorasDif);
		modelo.addAttribute("mapCursos", mapCursos);
		modelo.addAttribute("mapaListaHorario", mapaListaHorario);
		modelo.addAttribute("mapaListaHorarioDif", mapaListaHorarioDif);
		modelo.addAttribute("mapaSalones", mapaSalones);

		return "portales/alumno/horario";
	}

	@RequestMapping("/portales/alumno/muestraCalculoAccion")
	public String portalesAlumnoMuestraCalculoAccion(HttpServletRequest request) {
		// Verificar si se necesita enviar la conexion
		// enviarConEnoc(request,"Error-aca.ControllerPortales|portalesAlumnoMuestraCalculoAccion");
		return "portales/alumno/muestraCalculoAccion";
	}

	@RequestMapping("/portales/alumno/verificarArchivo")
	public String portalesAlumnoVerificarArchivo(HttpServletRequest request) {
		// enviarConEnoc(request,
		// "Error-aca.ControllerPortales|portalesAlumnoVerificarArchivo");
		return "portales/alumno/verificarArchivo";
	}

	@RequestMapping("/portales/alumno/accionMaterias")
	public String portalesAlumnoAccionMaterias(HttpServletRequest request) {
		enviarConEnoc(request, "Error-aca.ControllerPortales|portalesAlumnoAccionMaterias");
		return "portales/alumno/accionMaterias";
	}

	@RequestMapping("/portales/alumno/transferir")
	public String portalesAlumnoTransferir(HttpServletRequest request) {
		enviarConEnoc(request, "Error-aca.ControllerPortales|portalesAlumnoTransferir");
		return "portales/alumno/transferir";
	}

	@RequestMapping("/portales/alumno/financiero")
	public String portalesAlumnoFinanciero(HttpServletRequest request, Model modelo) {

		String matricula = "0";
		boolean existeAlumno = false;
		String year = request.getParameter("year") == null ? aca.util.Fecha.getHoy().substring(6, 10)
				: request.getParameter("year");
		String cuentas = request.getParameter("cuenta") == null ? "TODO" : request.getParameter("cuenta");
		int yearOld = Integer.parseInt(year) - 1;
		double saldoAntOracle = 0;
		double saldoAntSunPlus = 0;
		double saldoFormal = 0;
		double saldoCimum = 0;
		double saldoCimumAllende = 0;
		double saldoRemedial = 0;
		double saldoIdiomas = 0;
		double saldoFormalTodo = 0;
		double saldoCimumTodo = 0;
		double saldoCimumAllendeTodo = 0;
		double saldoIdiomasTodo = 0;
		double saldoRemedialTodo = 0;

		String fechaHoy = aca.util.Fecha.getHoy();
		String nombreAlumno = "";
		String codigoPersonal = "0";

		String ejercicioId = "x";
		String ejercicioOld = "X";
		String fechaInicio = "X";
		String fechaFinal = "X";

		if (cuentas.equals("TODO"))
			cuentas = "'SFORMA01','SIDIOM01','SCIMUN01','SCIMUN02','SREMED01'";
		else
			cuentas = "'" + cuentas + "'";

		// Define la fecha de inicio de la consulta y el ejercicio contable
		fechaInicio = "01/01/" + fechaHoy.substring(6, 10);
		ejercicioId = "001-" + fechaHoy.substring(6, 10);
		if (!year.equals("")) {
			ejercicioId = "001-" + year;
			ejercicioOld = "001-" + yearOld;
			fechaInicio = "01/01/" + year;
		}
		// fechaFinal
		fechaFinal = year.equals("") ? fechaHoy : "31/12/" + year;

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
			codigoPersonal 		= sesion.getAttribute("codigoPersonal") == null ?"-":(String) sesion.getAttribute("codigoPersonal");
			existeAlumno = alumPersonalDao.existeAlumno(matricula);
			// saldoAntOracle = contMovimientoDao.getSaldoAnterior(matricula, ejercicioOld,
			// 		"'1.1.04.01','1.1.04.29','1.1.04.30'");
			// saldoAntSunPlus = a3lAsalfldgDao.getSaldoAnteriorAlumno("S" + matricula, "01/01/" + year,
			// 		"'134ALUMN01','134ALUMI01','134ALUMP01'");
			// saldoFormal = a3lAsalfldgDao.getSaldoAnteriorPorTipo("S" + matricula, "01/01/" + year,
			// 		"'134ALUMN01','134ALUMI01','134ALUMP01'", "SFORMA01");
			// saldoIdiomas = a3lAsalfldgDao.getSaldoAnteriorPorTipo("S" + matricula, "01/01/" + year,
			// 		"'134ALUMN01','134ALUMI01','134ALUMP01'", "SIDIOM01");
			// saldoCimum = a3lAsalfldgDao.getSaldoAnteriorPorTipo("S" + matricula, "01/01/" + year,
			// 		"'134ALUMN01','134ALUMI01','134ALUMP01'", "SCIMUN01");
			// saldoCimumAllende = a3lAsalfldgDao.getSaldoAnteriorPorTipo("S" + matricula, "01/01/" + year,
			// 		"'134ALUMN01','134ALUMI01','134ALUMP01'", "SCIMUN02");
			// saldoRemedial = a3lAsalfldgDao.getSaldoAnteriorPorTipo("S" + matricula, "01/01/" + year,
			// 		"'134ALUMN01','134ALUMI01','134ALUMP01'", "SREMED01");

			// saldoFormalTodo = a3lAsalfldgDao.getSaldoAnteriorPorTipoSinFecha("S" + matricula,
			// 		"'134ALUMN01','134ALUMI01','134ALUMP01'", "SFORMA01");
			// saldoIdiomasTodo = a3lAsalfldgDao.getSaldoAnteriorPorTipoSinFecha("S" + matricula,
			// 		"'134ALUMN01','134ALUMI01','134ALUMP01'", "SIDIOM01");
			// saldoCimumTodo = a3lAsalfldgDao.getSaldoAnteriorPorTipoSinFecha("S" + matricula,
			// 		"'134ALUMN01','134ALUMI01','134ALUMP01'", "SCIMUN01");
			// saldoCimumAllendeTodo = a3lAsalfldgDao.getSaldoAnteriorPorTipoSinFecha("S" + matricula,
			// 		"'134ALUMN01','134ALUMI01','134ALUMP01'", "SCIMUN02");
			// saldoRemedialTodo = a3lAsalfldgDao.getSaldoAnteriorPorTipoSinFecha("S" + matricula,
			// 		"'134ALUMN01','134ALUMI01','134ALUMP01'", "SREMED01");

			// nombreAlumno = usuariosDao.getNombreUsuario(matricula, "NOMBRE");
		}
		
		boolean tieneSaldoRip = contSaldosRipDao.tieneSaldoRip(matricula, "A");
		ContSaldosRip rip = new ContSaldosRip();
		if (tieneSaldoRip) {
			rip = contSaldosRipDao.mapeaRegId(matricula, "A");
		}

		boolean tieneComentarios = finComentarioDao.tieneComentario(matricula, fechaInicio, fechaFinal);

		// Lista de comentarios
		List<FinComentario> lisComentarios = finComentarioDao.lisAlumnoComentarios(matricula, year, " ORDER BY FECHA");

		// Lista de calculos
		// List<FesCcobro> lisCalculos = fesCcobroDao.lisCalculosAlumno(matricula, " ORDER BY FECHA");

		// Lista de movimientos en oracle
		List<ContMovimiento> lisMovOracle = contMovimientoDao.lisMovtosAlumno(matricula, "'40'",
				"'1.1.04.01','1.1.04.29','1.1.04.30'", year, "'C','D'");

		// Lista de movimientos en SunPlus
		// List<A3lAsalfldg> lisMovSunPlus = a3lAsalfldgDao.lisMovtosAlumnoPorCuentas(
		// 		"'134ALUMN01','134ALUMI01','134ALUMP01'", "S" + matricula, year, cuentas, " ORDER BY TRANS_DATETIME"); // '334ANTIS01',

		// Lista de cuentas del alumno (Formal, CIMUM, Idiomas)
		// List<String> lisAlumnoCuentas = a3lAsalfldgDao.lisAlumnoCuentas("'134ALUMN01','134ALUMI01','134ALUMP01'",
		// 		"S" + matricula, year, ""); // '334ANTIS01',

		// List<String> lisAlumnoCuentasSinFecha = a3lAsalfldgDao
		// 		.lisAlumnoCuentasSinFecha("'134ALUMN01','134ALUMI01','134ALUMP01'", "S" + matricula, ""); // '334ANTIS01',

		// Lista de planes activos del alumno
		List<CargaAlumno> lisPlanes = cargaAlumnoDao.lisCargasActivas(matricula);

		// map de conceptos de movimientos
		HashMap<String, ContConcepto> mapaConceptos = contConceptoDao.mapaConceptos();

		// map de maestros con comentarios
		HashMap<String, String> mapaMaestros = maestrosDao.mapaMaestroComentaorio("NOMBRE");

		// map de los codigos de la dimensión FLAG (ANAL_T7,08)
		// HashMap<String, String> mapaCodigos = a3lAnlCodeDao.mapaCodeDim("08");

		HashMap<String, MapaPlan> mapaPlanes = mapaPlanDao.mapPlanes("'V','I','A'");
		// HashMap<String, String> mapaCuentas = a3lAsalfldgDao
		// 		.mapaMovtosPorCuenta("'134ALUMN01','134ALUMI01','134ALUMP01'", "S" + matricula, year); // '334ANTIS01',

		modelo.addAttribute("SubMenu", "5");
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		modelo.addAttribute("existeAlumno", existeAlumno);
		modelo.addAttribute("saldoAntOracle", saldoAntOracle);
		modelo.addAttribute("saldoAntSunPlus", saldoAntSunPlus);
		modelo.addAttribute("saldoFormal", saldoFormal);
		modelo.addAttribute("saldoIdiomas", saldoIdiomas);
		modelo.addAttribute("saldoCimum", saldoCimum);
		modelo.addAttribute("saldoCimumAllende", saldoCimumAllende);
		modelo.addAttribute("saldoRemedial", saldoRemedial);
		modelo.addAttribute("saldoFormalTodo", saldoFormalTodo);
		modelo.addAttribute("saldoIdiomasTodo", saldoIdiomasTodo);
		modelo.addAttribute("saldoCimumTodo", saldoCimumTodo);
		modelo.addAttribute("saldoCimumAllendeTodo", saldoCimumAllendeTodo);
		modelo.addAttribute("saldoRemedialTodo", saldoRemedialTodo);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("tieneComentarios", tieneComentarios);
		modelo.addAttribute("tieneSaldoRip", tieneSaldoRip);
		modelo.addAttribute("rip", rip);
		modelo.addAttribute("lisComentarios", lisComentarios);
		// modelo.addAttribute("lisCalculos", lisCalculos);
		modelo.addAttribute("lisMovOracle", lisMovOracle);
		// modelo.addAttribute("lisMovSunPlus", lisMovSunPlus);
		// modelo.addAttribute("lisAlumnoCuentas", lisAlumnoCuentas);
		// modelo.addAttribute("lisAlumnoCuentasSinFecha", lisAlumnoCuentasSinFecha);
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("mapaConceptos", mapaConceptos);
		modelo.addAttribute("mapaMaestros", mapaMaestros);
		// modelo.addAttribute("mapaCodigos", mapaCodigos);
		// modelo.addAttribute("mapaCuentas", mapaCuentas);
		
		return "portales/alumno/financiero";
	}

	@RequestMapping("/portales/alumno/financieroFulton")
	public String portalesAlumnoFinancieroFulton(HttpServletRequest request, Model modelo){
		String matricula 		= "0";
		String periodo 			= request.getParameter("Periodo")==null?"0":request.getParameter("Periodo").equals("")?"0":request.getParameter("Periodo");
		String fechaInicio 		= request.getParameter("FInicio")==null?"0":request.getParameter("FInicio").equals("")?"0":request.getParameter("FInicio");;
		String fechaFin 		= request.getParameter("FFin")==null?"0":request.getParameter("FFin").equals("")?"0":request.getParameter("FFin");;
		boolean existeAlumno 	= false;
		String codigoPersonal 	= "0";
		String fechaHoy 		= aca.util.Fecha.getHoy();
		String nombreAlumno 	= "";

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula 			= (String) sesion.getAttribute("codigoAlumno");
			codigoPersonal 		= sesion.getAttribute("codigoPersonal") == null ?"-":(String) sesion.getAttribute("codigoPersonal");
			existeAlumno 		= alumPersonalDao.existeAlumno(matricula);
			nombreAlumno 		= usuariosDao.getNombreUsuario(matricula, "NOMBRE");
		}

		Student student = new Student();
		if(studentDao.existeRegMatricula(matricula)){
			student = studentDao.mapeaRegIdMatricula(matricula);
		}

		List<StudentTransactions> lisTransactions;
		String balancePrevio = null;
		
		// Fills out list depending on filter parameters
		if((fechaInicio.equals("0") || fechaFin.equals("0")) && periodo.equals("0")){
			lisTransactions = studentTransactionsDao.getListAll(matricula, " ORDER BY TRAN_DATE");
		}else if(!periodo.equals("0") && (fechaInicio.equals("0") || fechaFin.equals("0"))){
			lisTransactions = studentTransactionsDao.getListPorPeriodo(matricula, periodo, " ORDER BY TRAN_DATE");
			balancePrevio = studentTransactionsDao.getPreviousBalanceByPeriod(matricula, periodo);
		}else {
			lisTransactions = studentTransactionsDao.getListPorFecha(matricula, fechaInicio, fechaFin, " ORDER BY TRAN_DATE");
			balancePrevio = studentTransactionsDao.getPreviousBalanceByDate(matricula, fechaInicio);
		}

		List<String> lisPeriodosAlumno = studentTransactionsDao.getListPeriodosAlumno(matricula, " ORDER BY PERIOD");

		modelo.addAttribute("SubMenu", "5");
		modelo.addAttribute("matricula", matricula);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("student", student);
		modelo.addAttribute("periodo", periodo);
		modelo.addAttribute("fechaInicio", fechaInicio);
		modelo.addAttribute("fechaFin", fechaFin);
		modelo.addAttribute("balancePrevio", balancePrevio);
		modelo.addAttribute("lisTransactions", lisTransactions);
		modelo.addAttribute("lisPeriodosAlumno", lisPeriodosAlumno);
		
		return "portales/alumno/financieroFulton";
	}

	@RequestMapping("/portales/alumno/financieroPau")
	public String portalesAlumnoFinancieroPau(HttpServletRequest request, Model modelo){
		String matricula 		= "0";
		boolean existeAlumno 	= false;
		String codigoPersonal 	= "0";
		String fechaHoy 		= aca.util.Fecha.getHoy();
		String nombreAlumno 	= "";
		String invnum 			= request.getParameter("Invnum")==null?"0":request.getParameter("Invnum");

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula 			= (String) sesion.getAttribute("codigoAlumno");
			codigoPersonal 		= sesion.getAttribute("codigoPersonal") == null ?"-":(String) sesion.getAttribute("codigoPersonal");
			existeAlumno 		= alumPersonalDao.existeAlumno(matricula);
			nombreAlumno 		= usuariosDao.getNombreUsuario(matricula, "NOMBRE");
		}

		AttacheCustomer student = new AttacheCustomer();
		if(attacheCustomerDao.existeReg(matricula)){
			student = attacheCustomerDao.mapeaRegId(matricula);
		}

		List<AttacheCustomerInvoiceTransaction> lisInvoices 				= attacheCustomerInvoiceTransactionDao.lisInvoices(matricula, " ORDER BY TRANDATE");
		List<AttacheCustomerInvoiceTransaction> lisInvoicesAlum 				= null;
		if(invnum.equals("0")){
			lisInvoicesAlum = attacheCustomerInvoiceTransactionDao.lisInvoices(matricula, " ORDER BY TRANDATE");
		}else{
			lisInvoicesAlum = attacheCustomerInvoiceTransactionDao.lisInvoices(matricula, invnum, " ORDER BY TRANDATE");
		}
		HashMap<String, String> mapaPagoPorInvoice		= attacheCustomerInvoiceTransactionDao.mapaPagoPorInvoice(matricula, " ORDER BY ACIT.INVNUM");
		
		List<AttacheCustomerPaymentTransaction> lisPagosPorInvoice = null;
		List<AttacheCustomerAdjustmentTransaction> lisAjustesPorInvoice = null;
		if(invnum.equals("0") ){
			lisPagosPorInvoice = attacheCustomerPaymentTransactionDao.lisPagos(matricula, " ORDER BY ACPT.TRANDATE");
			lisAjustesPorInvoice = attacheCustomerAdjustmentTransactionDao.lisAjuste(matricula, " ORDER BY ACAT.TRANDATE");
		}else{
			lisPagosPorInvoice = attacheCustomerPaymentTransactionDao.lisPagosPorInvoice(matricula, invnum, " ORDER BY ACPT.TRANDATE");
			lisAjustesPorInvoice = attacheCustomerAdjustmentTransactionDao.lisAjustePorInvoice(matricula, invnum, " ORDER BY ACAT.TRANDATE");
		}

		AttacheCustomerInvoiceTransaction invoice = attacheCustomerInvoiceTransactionDao.mapeaRegId(matricula, invnum);

		modelo.addAttribute("SubMenu", "5");
		modelo.addAttribute("matricula", matricula);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("student", student);
		modelo.addAttribute("invnum", invnum);
		modelo.addAttribute("inovice", invoice);
		modelo.addAttribute("lisInvoices", lisInvoices);
		modelo.addAttribute("lisInvoicesAlum", lisInvoicesAlum);
		modelo.addAttribute("mapaPagoPorInvoice", mapaPagoPorInvoice);
		modelo.addAttribute("lisPagosPorInvoice", lisPagosPorInvoice);
		modelo.addAttribute("lisAjustesPorInvoice", lisAjustesPorInvoice);

		return "portales/alumno/financieroPau";
	}

	@RequestMapping("/portales/alumno/financieromov")
	public String portalesAlumnoFinancieroMov(HttpServletRequest request, Model modelo) {

		String matricula = "0";
		boolean existeAlumno = false;
		String year = request.getParameter("year") == null ? aca.util.Fecha.getHoy().substring(6, 10)
				: request.getParameter("year");
		String cuentas = request.getParameter("cuenta") == null ? "TODO" : request.getParameter("cuenta");
		int yearOld = Integer.parseInt(year) - 1;
		double saldoAntOracle = 0;
		double saldoAntSunPlus = 0;
		double saldoFormal = 0;
		double saldoCimum = 0;
		double saldoIdiomas = 0;
		double saldoCimumAllende = 0;
		double saldoRemedial = 0;

		String fechaHoy = aca.util.Fecha.getHoy();
		String nombreAlumno = "";

		String ejercicioId = "x";
		String ejercicioOld = "X";
		String fechaInicio = "X";
		String fechaFinal = "X";

		if (cuentas.equals("TODO"))
			cuentas = "'SFORMA01','SIDIOM01','SCIMUN01','SCIMUN02','SREMED01'";
		else
			cuentas = "'" + cuentas + "'";

		// Define la fecha de inicio de la consulta y el ejercicio contable
		fechaInicio = "01/01/" + fechaHoy.substring(6, 10);
		ejercicioId = "001-" + fechaHoy.substring(6, 10);
		if (!year.equals("")) {
			ejercicioId = "001-" + year;
			ejercicioOld = "001-" + yearOld;
			fechaInicio = "01/01/" + year;
		}
		// fechaFinal
		fechaFinal = year.equals("") ? fechaHoy : "31/12/" + year;

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
			existeAlumno = alumPersonalDao.existeAlumno(matricula);
			// saldoAntOracle = contMovimientoDao.getSaldoAnterior(matricula, ejercicioOld,
			// 		"'1.1.04.01','1.1.04.29','1.1.04.30'");
			// saldoAntSunPlus = a3lAsalfldgDao.getSaldoAnteriorAlumno("S" + matricula, "01/01/" + year,
			// 		"'134ALUMN01','134ALUMI01','134ALUMP01'");
			// saldoFormal = a3lAsalfldgDao.getSaldoAnteriorPorTipo("S" + matricula, "01/01/" + year,
			// 		"'134ALUMN01','134ALUMI01','134ALUMP01'", "SFORMA01");
			// saldoIdiomas = a3lAsalfldgDao.getSaldoAnteriorPorTipo("S" + matricula, "01/01/" + year,
			// 		"'134ALUMN01','134ALUMI01','134ALUMP01'", "SIDIOM01");
			// saldoCimum = a3lAsalfldgDao.getSaldoAnteriorPorTipo("S" + matricula, "01/01/" + year,
			// 		"'134ALUMN01','134ALUMI01','134ALUMP01'", "SCIMUN01");
			// saldoCimumAllende = a3lAsalfldgDao.getSaldoAnteriorPorTipo("S" + matricula, "01/01/" + year,
			// 		"'134ALUMN01','134ALUMI01','134ALUMP01'", "SCIMUN02");
			// saldoRemedial = a3lAsalfldgDao.getSaldoAnteriorPorTipo("S" + matricula, "01/01/" + year,
			// 		"'134ALUMN01','134ALUMI01','134ALUMP01'", "SREMED01");

			nombreAlumno = usuariosDao.getNombreUsuario(matricula, "NOMBRE");
		}

		boolean tieneSaldoRip = contSaldosRipDao.tieneSaldoRip(matricula, "A");
		ContSaldosRip rip = new ContSaldosRip();
		if (tieneSaldoRip) {
			rip = contSaldosRipDao.mapeaRegId(matricula, "A");
		}

		boolean tieneComentarios = finComentarioDao.tieneComentario(matricula, fechaInicio, fechaFinal);

		// Lista de comentarios
		List<FinComentario> lisComentarios = finComentarioDao.lisAlumnoComentarios(matricula, year, " ORDER BY FECHA");

		// Lista de calculos
		// List<FesCcobro> lisCalculos = fesCcobroDao.lisCalculosAlumno(matricula, " ORDER BY FECHA");

		// Lista de movimientos en oracle
		List<ContMovimiento> lisMovOracle = contMovimientoDao.lisMovtosAlumno(matricula, "'40'",
				"'1.1.04.01','1.1.04.29','1.1.04.30'", year, "'C','D'");

		// Lista de movimientos en SunPlus
		// List<A3lAsalfldg> lisMovSunPlus = a3lAsalfldgDao.lisMovtosAlumnoPorCuentas(
		// 		"'134ALUMN01','134ALUMI01','134ALUMP01'", "S" + matricula, year, cuentas, " ORDER BY TRANS_DATETIME"); // '334ANTIS01',

		// Lista de cuentas del alumno (Formal, CIMUM, Idiomas)
		// List<String> lisAlumnoCuentas = a3lAsalfldgDao.lisAlumnoCuentas("'134ALUMN01','134ALUMI01','134ALUMP01'",
		// 		"S" + matricula, year, ""); // '334ANTIS01',

		// Lista de planes activos del alumno
		List<CargaAlumno> lisPlanes = cargaAlumnoDao.lisCargasActivas(matricula);

		// map de conceptos de movimientos
		HashMap<String, ContConcepto> mapaConceptos = contConceptoDao.mapaConceptos();

		// map de maestros con comentarios
		HashMap<String, String> mapaMaestros = maestrosDao.mapaMaestroComentaorio("NOMBRE");

		// map de los codigos de la dimensión FLAG (ANAL_T7,08)
		// HashMap<String, String> mapaCodigos = a3lAnlCodeDao.mapaCodeDim("08");

		HashMap<String, MapaPlan> mapaPlanes = mapaPlanDao.mapPlanes("'V','I','A'");
		// HashMap<String, String> mapaCuentas = a3lAsalfldgDao
		// 		.mapaMovtosPorCuenta("'134ALUMN01','134ALUMI01','134ALUMP01'", "S" + matricula, year); // '334ANTIS01',

		modelo.addAttribute("SubMenu", "5");
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		modelo.addAttribute("existeAlumno", existeAlumno);
		modelo.addAttribute("saldoAntOracle", saldoAntOracle);
		modelo.addAttribute("saldoAntSunPlus", saldoAntSunPlus);
		modelo.addAttribute("saldoFormal", saldoFormal);
		modelo.addAttribute("saldoIdiomas", saldoIdiomas);
		modelo.addAttribute("saldoCimum", saldoCimum);
		modelo.addAttribute("saldoCimumAllende", saldoCimumAllende);
		modelo.addAttribute("saldoRemedial", saldoRemedial);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("tieneComentarios", tieneComentarios);
		modelo.addAttribute("tieneSaldoRip", tieneSaldoRip);
		modelo.addAttribute("rip", rip);
		modelo.addAttribute("lisComentarios", lisComentarios);
		// modelo.addAttribute("lisCalculos", lisCalculos);
		modelo.addAttribute("lisMovOracle", lisMovOracle);
		// modelo.addAttribute("lisMovSunPlus", lisMovSunPlus);
		// modelo.addAttribute("lisAlumnoCuentas", lisAlumnoCuentas);
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("mapaConceptos", mapaConceptos);
		modelo.addAttribute("mapaMaestros", mapaMaestros);
		// modelo.addAttribute("mapaCodigos", mapaCodigos);
		// modelo.addAttribute("mapaCuentas", mapaCuentas);

		return "portales/alumno/financieromov";
	}

	@RequestMapping("/portales/alumno/citaMedica")
	public String portalesAlumnoCitaMedica(HttpServletRequest request, Model modelo) {

		String nombreAlumno = "";
		HttpSession sesion = request.getSession();
		if (sesion != null) {
			String codigoAlumno = (String) sesion.getAttribute("codigoAlumno");
			nombreAlumno = alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
		}

		List<aca.cita.spring.CitaEvento> lista = citaEventoDao.getListAll();
		modelo.addAttribute("lista", lista);
		modelo.addAttribute("nombreAlumno", nombreAlumno);

		return "portales/alumno/citaMedica";
	}

	@RequestMapping("/portales/alumno/citaHora")
	public String portalesAlumnoCitaHora(HttpServletRequest request, Model modelo) {

		HttpSession sesion = null;
		String eventoId = request.getParameter("EventoId") == null ? "0" : request.getParameter("EventoId");

		String codigoAlumno = "";
		String nombreAlumno = "";
		String nombreEvento = "";

		sesion = request.getSession();
		if (sesion != null) {
			codigoAlumno = (String) sesion.getAttribute("codigoAlumno");
			nombreAlumno = alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
		}
		nombreEvento = citaEventoDao.getEventoNombre(eventoId);
		int maximo = citaPeriodoDao.getMaxPeriodo(eventoId);

		HashMap<String, String> mapaCupo = citaPeriodoDao.mapaCupo();
		HashMap<String, String> mapaOcupados = citaReservadaDao.mapaOcupados("A");
		HashMap<String, String> mapaAlumno = citaReservadaDao.mapaAlumnoReservado();
		boolean existeReservacion = citaReservadaDao.existeReservacion(eventoId, codigoAlumno, "'A','C'");

		modelo.addAttribute("mapaCupo", mapaCupo);
		modelo.addAttribute("mapaOcupados", mapaOcupados);
		modelo.addAttribute("mapaAlumno", mapaAlumno);
		modelo.addAttribute("maximo", maximo);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("nombreEvento", nombreEvento);
		modelo.addAttribute("existeReservacion", existeReservacion);

		return "portales/alumno/citaHora";
	}

	@RequestMapping("/portales/alumno/reservar")
	public String portalesAlumnoReservar(HttpServletRequest request) {

		HttpSession sesion = null;
		String codigoAlumno = "";
		String eventoId = request.getParameter("EventoId") == null ? "0" : request.getParameter("EventoId");
		String periodoId = request.getParameter("PeriodoId") == null ? "0" : request.getParameter("PeriodoId");
		String dia = request.getParameter("Dia") == null ? "0" : request.getParameter("Dia");
		String resultado = "Booking error";
		aca.cita.spring.CitaReservada reservada = new aca.cita.spring.CitaReservada();

		sesion = request.getSession();
		if (sesion != null) {
			codigoAlumno = (String) sesion.getAttribute("codigoAlumno");
			reservada.setCodigoPersonal(codigoAlumno);
			reservada.setEventoId(eventoId);
			reservada.setPeriodoId(periodoId);
			reservada.setDia(dia);
			reservada.setFecha(aca.util.Fecha.getHoy());
			reservada.setEstado("A");
			boolean existe = citaReservadaDao.existeReg(eventoId, dia, periodoId, codigoAlumno);
			int cupo = citaPeriodoDao.getCupo(eventoId, dia, periodoId);
			int ocupados = citaReservadaDao.getOcupados(eventoId, dia, periodoId, "A");
			if ((cupo - ocupados > 0)) {
				if (existe) {
					if (citaReservadaDao.updateReg(reservada)) {
						resultado = "Appointment booked";
					}
				} else {
					if (citaReservadaDao.insertReg(reservada)) {
						resultado = "Appointment booked";
					}
				}
			}
		}

		return "redirect:/portales/alumno/citaHora?EventoId=" + eventoId + "&resultado=" + resultado;
	}

	@RequestMapping("/portales/alumno/cancelar")
	public String portalesAlumnoCancelar(HttpServletRequest request) {

		HttpSession sesion = null;
		String codigoAlumno = "";
		String eventoId = request.getParameter("EventoId") == null ? "0" : request.getParameter("EventoId");
		String periodoId = request.getParameter("PeriodoId") == null ? "0" : request.getParameter("PeriodoId");
		String dia = request.getParameter("Dia") == null ? "0" : request.getParameter("Dia");
		String resultado = "Error canceling";
		aca.cita.spring.CitaReservada reservada = new aca.cita.spring.CitaReservada();

		sesion = request.getSession();
		if (sesion != null) {
			codigoAlumno = (String) sesion.getAttribute("codigoAlumno");
			reservada.setCodigoPersonal(codigoAlumno);
			reservada.setEventoId(eventoId);
			reservada.setPeriodoId(periodoId);
			reservada.setDia(dia);
			reservada.setFecha(aca.util.Fecha.getHoy());
			reservada.setEstado("C");

			if (citaReservadaDao.updateReg(reservada)) {
				resultado = "Appointment cancelled";
			}
		}

		return "redirect:/portales/alumno/citaHora?EventoId=" + eventoId + "&resultado=" + resultado;
	}

	@RequestMapping(value = { "/portales/alumno/docFoto", "/academico/portales/alumno/docFoto" })
	public void docFoto(HttpServletRequest request, HttpServletResponse response) throws SQLException {

		HttpSession sesion = null;
		java.sql.Connection conn = null;

		try {
			Class.forName("org.postgresql.Driver");
			conn = java.sql.DriverManager.getConnection(aca.conecta.Conectar.coneccion(),
					aca.conecta.Conectar.usuario(), aca.conecta.Conectar.password());

			java.sql.Statement stmt = conn.createStatement();
			java.sql.ResultSet rset = null;

			String codigoPersonal = "0";
			String matricula = request.getParameter("matricula");
			String s_iddocumento = request.getParameter("documento");
			String s_nhoja = request.getParameter("hoja");

			byte[] buf = null;

			sesion = request.getSession();
			if (sesion != null) {
				codigoPersonal = sesion.getAttribute("codigoPersonal") == null ? "-"
						: (String) sesion.getAttribute("codigoPersonal");
			}

			// Valida los permisos del usuario
			boolean permiso = false;
			// Si tiene una sesión activa
			if (codigoPersonal.charAt(0) == '9') {
				permiso = true;
			} else if (codigoPersonal.charAt(0) == '0' || codigoPersonal.charAt(0) == '1'
					|| codigoPersonal.charAt(0) == '2') {
				if (accesoDao.esAdministrador(codigoPersonal) || accesoDao.getPortalAlumno(codigoPersonal).equals("S")
						|| matricula.equals(codigoPersonal)) {
					permiso = true;
				}
			}

			// Si tiene permiso regresa la foto
			if (permiso) {
				conn.setAutoCommit(false);
				String COMANDO = "select imagen,fuente,origen from arch_docalum Where matricula = '" + matricula
						+ "' and " + "iddocumento = '" + s_iddocumento + "' and hoja = " + s_nhoja;
				rset = stmt.executeQuery(COMANDO);
				if (rset.next()) {

					org.postgresql.largeobject.LargeObject obj;
					org.postgresql.largeobject.LargeObjectManager lom = ((org.postgresql.PGConnection) conn)
							.getLargeObjectAPI();
					long oid = rset.getInt("imagen");
					obj = lom.open(oid, org.postgresql.largeobject.LargeObjectManager.READ);
					buf = new byte[obj.size()];
					obj.read(buf, 0, obj.size());
					OutputStream out = response.getOutputStream();
					out.write(buf);
					out.close();
				}
				conn.setAutoCommit(true);
			}
			if (conn != null)
				conn.close();
			if (stmt != null)
				stmt.close();
			if (rset != null)
				rset.close();

		} catch (Exception ex) {
			System.out.println("Error /foto:" + ex);
		} finally {
			if (conn != null)
				conn.close();
		}

	}

	@RequestMapping("/portales/alumno/modificarMovil")
	public String portalesAlumnoModificarMovil(HttpServletRequest request, Model modelo) {

		String codigoPersonal = "0";
		String movil = request.getParameter("Movil") == null ? "0" : request.getParameter("Movil");
		HttpSession sesion = request.getSession();
		if (sesion != null) {
			codigoPersonal = (String) sesion.getAttribute("codigoPersonal");
		}
		if (accesoDao.existeReg(codigoPersonal)) {
			accesoDao.updateAlumnoMovil(codigoPersonal, movil);
		}

		return "redirect:/portales/portalAlumno/portal";
	}

	@RequestMapping("/portales/alumno/resumenmov")
	public String portalesAlumnoResumenMov(HttpServletRequest request, Model modelo) {
		AlumPersonal alumPersonal = new AlumPersonal();
		String matricula = "0";
		AlumAcademico alumAcademico = new AlumAcademico();
		AlumPlan alumPlan = new AlumPlan();

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
			alumAcademico = alumAcademicoDao.mapeaRegId(matricula);
			alumPersonal = alumPersonalDao.mapeaRegId(matricula);
			alumPlan = alumPlanDao.mapeaRegId(matricula);
		}
		List<CargaAlumno> lisPlanes = cargaAlumnoDao.lisCargasActivas(matricula);
		HashMap<String, MapaPlan> mapaPlanes = mapaPlanDao.mapPlanes("'V','I','A'");

		modelo.addAttribute("SubMenu", "1");
		modelo.addAttribute("esInscrito", inscritosDao.inscrito(matricula));
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("alumAcademico", alumAcademico);
		modelo.addAttribute("alumPlan", alumPlan);

		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("mapaPlanes", mapaPlanes);

		return "portales/alumno/resumenmov";
	}

	@RequestMapping("/portales/alumno/datosmov")
	public String portalesAlumnoDatosMov(HttpServletRequest request, Model modelo) {
		String matricula 		= "0";
		String codigoPersonal 	= "0";
		String ejercicioId 		= "0";		
		HttpSession sesion 		= request.getSession();
		if (sesion != null) {
			matricula 			= (String) sesion.getAttribute("codigoAlumno");
			codigoPersonal 		= sesion.getAttribute("codigoPersonal") == null?"-":(String) sesion.getAttribute("codigoPersonal");
			ejercicioId 		= (String) sesion.getAttribute("ejercicioId");
		}
		String fechaHoy 		= aca.util.Fecha.getHoy();
		String periodoId 		= catPeriodoDao.getPeriodoActivo();

		AlumPersonal alumPersonal 	= alumPersonalDao.mapeaRegId(matricula);
		AlumAcademico alumAcademico = alumAcademicoDao.mapeaRegId(matricula);
		AlumUbicacion alumUbicacion = alumUbicacionDao.mapeaRegId(matricula);
		AlumPlan alumPlan 			= alumPlanDao.mapeaRegId(matricula);
		ResDatos resDatos 			= resDatosDao.mapeaRegId(matricula);

		String puestoReciente 		= becPuestoAlumnoDao.maximoPuesto(matricula);
		String puestoId	 			= request.getParameter("puestoId") == null ? puestoReciente : request.getParameter("puestoId");
		String mentorId 			= mentAlumnoDao.getMentorId(matricula, periodoId, fechaHoy);
		String mentorNombre 		= maestrosDao.getNombreMaestro(codigoPersonal, "NOMBRE");
		String mentorCorto 			= maestrosDao.getNombreCorto(mentorId, "NOMBRE");
		String nombreReligion 		= catReligionDao.getNombreReligion(alumPersonal.getReligionId());
		String nombreInstitucion 	= catInstitucionDao.getNombreInstitucionFinanzas(alumAcademico.getObreroInstitucion());
		String nombreTipo 			= catTipoAlumnoDao.getNombreTipo(alumAcademico.getTipoAlumno());
		String nombreNacionalidad 	= catPaisDao.getNacionalidad(alumPersonal.getNacionalidad());
		String fechaVencimiento 	= alumPersonalDao.getVencimientoFM3(alumPersonal.getCodigoPersonal());
		boolean alumnoInscrito 		= alumPersonalDao.esInscrito(matricula);
		BecPuestoAlumno puestoAlumno = becPuestoAlumnoDao.mapeaRegId(puestoId);
		String horasTotal 			= becAcuerdoDao.getHorasConv(matricula, puestoId);
		String paisNombre 			= catPaisDao.getNombrePais(alumPersonal.getPaisId());
		String estadoNombre 		= catEstadoDao.getNombreEstado(alumPersonal.getPaisId(), alumPersonal.getEstadoId());
		String ciudadNombre 		= catCiudadDao.getNombreCiudad(alumPersonal.getPaisId(), alumPersonal.getEstadoId(), alumPersonal.getCiudadId());
		String razonNombre 			= resRazonDao.nombreRazon(resDatos.getRazon());
		boolean tieneFoto 			= false;
		boolean tieneFotoEnviada 	= false;

		if (posFotoAlumDao.existeReg(matricula, "O")) {
			tieneFoto = true;
		}

		if (posFotoAlumDao.existeReg(matricula, "E")) {
			tieneFotoEnviada = true;
		}

		List<CargaAlumno> lisPlanes 			= cargaAlumnoDao.lisCargasActivas(matricula);
		List<LegExtdoctos> lisExtDoc 			= legExtdoctosDao.lisAlumno(matricula);
		List<BecPuestoAlumno> lisPuestos	 	= becPuestoAlumnoDao.getListAllEjerciciosAlum(matricula," ORDER BY PUESTO_ID DESC");
		List<BecInforme> lisInformes 			= becInformeDao.getListPuestoAlumno(matricula, puestoId," ORDER BY ID_EJERCICIO,ORDEN");

		HashMap<String, MapaPlan> mapaPlanes 	= mapaPlanDao.mapPlanes("'V','I','A'");
		HashMap<String, String> mapaDocumentos 	= legDocumentoDao.mapaDocumentos();
		HashMap<String, ContCcosto> mapaDeptos 	= contCcostoDao.mapaDeptos("S");
		HashMap<String, String> mapaHorasAlumno = becInformeAlumnoDao.mapHorasPuestoAlumno(matricula, puestoId);
		HashMap<String, String> mapaEstadoAlumno = becInformeAlumnoDao.mapEstado(matricula, puestoId);
		HashMap<String, String> mapaEvalAlumno 	= becInformeAlumnoDao.mapEvaluacionAlumno(matricula);
		HashMap<String, String> mapaCategorias 	= becCategoriaDao.getMapCategorias();

		modelo.addAttribute("SubMenu", "2");
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("alumAcademico", alumAcademico);
		modelo.addAttribute("alumUbicacion", alumUbicacion);
		modelo.addAttribute("alumPlan", alumPlan);
		modelo.addAttribute("resDatos", resDatos);
		modelo.addAttribute("puestoReciente", puestoReciente);
		modelo.addAttribute("puestoId", puestoId);
		modelo.addAttribute("mentorId", mentorId);
		modelo.addAttribute("mentorNombre", mentorNombre);
		modelo.addAttribute("mentorCorto", mentorCorto);
		modelo.addAttribute("nombreReligion", nombreReligion);
		modelo.addAttribute("nombreInstitucion", nombreInstitucion);
		modelo.addAttribute("nombreTipo", nombreTipo);
		modelo.addAttribute("nombreNacionalidad", nombreNacionalidad);
		modelo.addAttribute("fechaVencimiento", fechaVencimiento);
		modelo.addAttribute("alumnoInscrito", alumnoInscrito);
		modelo.addAttribute("puestoAlumno", puestoAlumno);
		modelo.addAttribute("horasTotal", horasTotal);
		modelo.addAttribute("paisNombre", paisNombre);
		modelo.addAttribute("estadoNombre", estadoNombre);
		modelo.addAttribute("ciudadNombre", ciudadNombre);
		modelo.addAttribute("razonNombre", razonNombre);

		modelo.addAttribute("lisPuestos", lisPuestos);
		modelo.addAttribute("lisInformes", lisInformes);
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("lisExtDoc", lisExtDoc);

		modelo.addAttribute("mapaPlanes", mapaPlanes);
		modelo.addAttribute("mapaDocumentos", mapaDocumentos);
		modelo.addAttribute("mapaDeptos", mapaDeptos);
		modelo.addAttribute("mapaHorasAlumno", mapaHorasAlumno);
		modelo.addAttribute("mapaEstadoAlumno", mapaEstadoAlumno);
		modelo.addAttribute("mapaEvalAlumno", mapaEvalAlumno);
		modelo.addAttribute("mapaCategorias", mapaCategorias);
		modelo.addAttribute("tieneFoto", tieneFoto);
		modelo.addAttribute("tieneFotoEnviada", tieneFotoEnviada);

		return "portales/alumno/datosmov";
	}

	@RequestMapping("/portales/alumno/resumen")
	public String portalesAlumnoResumen(HttpServletRequest request, Model modelo) {
		String mensajeFoto 			= request.getParameter("Mensaje")==null ?"0":request.getParameter("Mensaje");
		String mensajeEnc 			= request.getParameter("MensajeEnc")==null?"0":request.getParameter("MensajeEnc");
		String eventoVoto 			= votoEventoDao.eventoActivo(aca.util.Fecha.getHoy());
		String matricula 			= "0";
		String codigoPersonal		= "0"; 
		String modalidadAlumno 		= "0";
		String modalidadNombre		= "-";
		String esEnLinea			= "N";
		String periodoActivo 		= encPeriodoDao.getPeriodoActivo();

		List<String> lisCargas 		= new ArrayList<String>();
		AlumAcademico alumAcademico = new AlumAcademico();
		AlumPersonal alumPersonal 	= new AlumPersonal();
		AlumPlan alumPlan 			= new AlumPlan();
		EncPeriodo encPeriodo 		= new EncPeriodo();
		Edo edo = new Edo();
		boolean evaluaDocente 		= false;
		boolean tienePermiso 		= false;
		boolean existeCovid 		= false;
		boolean contestoEncuesta 	= false;
		boolean contestoMentoria 	= false;
		String planActual 			= "0";
		String periodoCovid 		= String.valueOf(covidPeriodoDao.getActual());
		String encuestaActiva 		= mentEncuestaDao.getEncuestaActiva();
		String colportorId			= "N";		 
		
		FinPeriodo finPeriodo 		= new FinPeriodo();
		HttpSession sesion 			= request.getSession();
		if (sesion != null) {
			matricula 				= (String) sesion.getAttribute("codigoAlumno");
			codigoPersonal 			= (String) sesion.getAttribute("codigoPersonal");
			modalidadAlumno 		= alumAcademicoDao.getModalidadId(matricula);
			modalidadNombre 		= catModalidadDao.getNombreModalidad(modalidadAlumno);
			esEnLinea				= catModalidadDao.getEnLinea(modalidadAlumno);
			lisCargas 				= inscritosDao.getCargasInscritas(matricula);
			alumAcademico 			= alumAcademicoDao.mapeaRegId(matricula);
			alumPersonal 			= alumPersonalDao.mapeaRegId(matricula);
			alumPlan 				= alumPlanDao.mapeaRegId(matricula);
			tienePermiso 			= finPermisoDao.tienePermiso(matricula);
			planActual 				= alumPlanDao.getPlanActual(matricula);
		}
			
		if (alumPersonalDao.existeAlumno(matricula)) {
			colportorId 		= alumPersonalDao.esColportor(matricula);
		}

		if (!periodoActivo.equals("0")) {
			encPeriodo = encPeriodoDao.mapeaRegId(periodoActivo);
		}

		if (encPeriodoDao.contestoEncuesta(matricula, periodoActivo)) {
			contestoEncuesta = true;
		}
		
		if(mentRespuestaDao.contestoEncuesta(matricula, encuestaActiva)) {
			contestoMentoria = true;
		}

		String mentorId = mentAlumnoDao.getMentorPorFecha(matricula, aca.util.Fecha.getHoy());
		String mentorNombre = "-";
		if (!mentorId.equals("0"))
			mentorNombre = maestrosDao.getNombreMaestro(mentorId, "Nombre");
		String autorizado = archivoDao.autorizaAlumno(matricula, planActual);

		// Verifica si existe una evaluacion activa
		String evalDocente = edoDao.getEdoActual("E", alumAcademico.getModalidadId());

		if (!evalDocente.equals("0")) {
			edo = edoDao.mapeaRegId(evalDocente);
			evaluaDocente = true;
		}

		// // Consulta el saldo del estudiante
		// FinSaldo finSaldo = new FinSaldo();
		// try {
		// 	RestTemplate restTemplate = new RestTemplate();
		// 	finSaldo = restTemplate.getForObject("http://172.16.251.110:8080/infor/reportes/api/fe/saldo/" + matricula,
		// 			FinSaldo.class);
		// } catch (Exception ex) {
		// 	System.out.println("Balance error:" + matricula);
		// }

		// Verifica si hay periodos de bloqueo
		String periodoId = finPeriodoDao.getPeriodoActivo(aca.util.Fecha.getHoy());
		String mensaje = "";
		// if (!periodoId.equals("0")) {
		// 	finPeriodo = finPeriodoDao.mapeaRegId(periodoId);
		// 	if (finPeriodo.getModalidades().contains(modalidadAlumno) && Double.valueOf(finSaldo.getSaldoVencido()) > Double.valueOf(finPeriodo.getCantidad())) {
		// 		// Valida las cargas en donde se aplica el bloqueo
		// 		boolean si = false;
		// 		for (String carga : lisCargas) {
		// 			if (finPeriodo.getCargas().contains(carga)) {
		// 				if (sesion != null && !tienePermiso) {
		// 					sesion.setAttribute("cerrarPortal", "S");
		// 					mensaje = finPeriodo.getMensaje();
		// 					si = true;
		// 				}
		// 			}
		// 		}
		// 		if (!si) {
		// 			if (sesion != null) {
		// 				sesion.setAttribute("cerrarPortal", "N");
		// 			}
		// 		}
		// 	} else {
		// 		if (sesion != null) {
		// 			sesion.setAttribute("cerrarPortal", "N");
		// 		}
		// 	}
		// }

		if (alertaCovidDao.existeReg(matricula)) {
			existeCovid = true;
		}

		boolean fotoAprobada = true;
		boolean fotoEnviada = false;
		boolean permisoEvento = false;
		boolean noPermisoEvento = false;
		boolean graduacionPendiente = false;

		if (alumEgresoDao.existePermisoAlum(matricula, "S", "A")) {
			permisoEvento = true;
		}

		if (alumEgresoDao.existePermisoAlum(matricula, "N", "A")) {
			noPermisoEvento = true;
		}

		if (alumEgresoDao.existeReg("43", matricula) || alumEgresoDao.existeReg("51", matricula)) {
			graduacionPendiente = true;
		}

		PosFotoAlum foto = new PosFotoAlum();

		if (posFotoAlumDao.existeReg(matricula, "A"))
			foto = posFotoAlumDao.mapeaRegId(matricula, "A");
		if (!posFotoAlumDao.existeReg(matricula, "O"))
			fotoAprobada = false;
		if (posFotoAlumDao.existeReg(matricula, "A"))
			fotoEnviada = true;

		String cargaId 			= cargaDao.getMejorCarga(matricula);
		int invitacionId 		= alumEgresoDao.invitacionId(matricula);
		String planGraduacion	= alumEgresoDao.mapeaRegId(String.valueOf(invitacionId), matricula).getPlanId();

		List<AlumnoCurso> lisCursos 				= alumnoCursoDao.getListAlumnoCarga(matricula, cargaId, "");
		List<CandAlumno> lisCandados 				= candAlumnoDao.getLista(matricula, "A", " ORDER BY FOLIO");
		List<AlumPlan> lisPlanes 					= alumPlanDao.lisPlanesAlumno(matricula, " ORDER BY ESTADO DESC, PLAN_ID");
		List<MatEvento> lisMatEventos 				= matEventoDao.lisMatEvento("A", " ORDER BY EVENTO_NOMBRE DESC");
		HashMap<String, MapaPlan> mapaPlanes 		= mapaPlanDao.mapPlanes("'V','I','A'");
		HashMap<String, String> mapaTipoCal 		= catTipoCalDao.mapTipoCalCorto();
		HashMap<String, String> mapaContestadas 	= edoAlumnoRespDao.mapaContestadas(matricula);
		HashMap<String, CandTipo> mapaCandados 		= candTipoDao.mapTipo();

		modelo.addAttribute("SubMenu", "1");
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("mensajeEnc", mensajeEnc);
		modelo.addAttribute("autorizado", autorizado);
		modelo.addAttribute("modalidadNombre", modalidadNombre);
		modelo.addAttribute("esEnLinea", esEnLinea);		
		// modelo.addAttribute("finSaldo", finSaldo);
		modelo.addAttribute("eventoVoto", eventoVoto);
		modelo.addAttribute("extranjero", alumPersonalDao.esExtranjero(matricula));
		modelo.addAttribute("venceFM3", alumPersonalDao.getVencimientoFM3(matricula));
		modelo.addAttribute("conCandados", candAlumnoDao.conCandados(matricula));
		modelo.addAttribute("tienePlan", alumPlanDao.tienePlan(matricula));
		modelo.addAttribute("esInscrito", inscritosDao.inscrito(matricula));
		modelo.addAttribute("evaluaDocente", evaluaDocente);
		modelo.addAttribute("carreraAlumno", alumPlanDao.getCarreraId(matricula));
		modelo.addAttribute("facultad", catCarreraDao.getFacultadId(alumPlanDao.getCarreraId(matricula)));
		modelo.addAttribute("mentorNombre", mentorNombre);
		modelo.addAttribute("invitacion", invitacionId);
		modelo.addAttribute("alumEgreso", alumEgresoDao.mapeaPorCodigoyPlan(matricula, planGraduacion));

		modelo.addAttribute("alumno", alumPersonal);
		modelo.addAttribute("academico", alumAcademico);
		modelo.addAttribute("plan", alumPlan);
		modelo.addAttribute("edo", edo);

		modelo.addAttribute("lisEventos", votoEventoDao.getListEventosActuales(""));
		modelo.addAttribute("lisMatEventos", lisMatEventos);
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("lisCursos", lisCursos);
		modelo.addAttribute("lisCandados", lisCandados);

		modelo.addAttribute("mapaPlanes", mapaPlanes);
		modelo.addAttribute("mapaTipoCal", mapaTipoCal);
		modelo.addAttribute("mapaContestadas", mapaContestadas);
		modelo.addAttribute("mapaCandados", mapaCandados);
		modelo.addAttribute("graduacionPendiente", graduacionPendiente);

		modelo.addAttribute("foto", foto);
		modelo.addAttribute("permisoEvento", permisoEvento);
		modelo.addAttribute("noPermisoEvento", noPermisoEvento);
		modelo.addAttribute("fotoAprobada", fotoAprobada);
		modelo.addAttribute("fotoEnviada", fotoEnviada);
		modelo.addAttribute("mensajeFoto", mensajeFoto);
		modelo.addAttribute("existeCovid", existeCovid);
		modelo.addAttribute("encPeriodo", encPeriodo);
		modelo.addAttribute("contestoEncuesta", contestoEncuesta);
		modelo.addAttribute("contestoMentoria", contestoMentoria);
		modelo.addAttribute("colportorId", colportorId);		

		return "portales/alumno/resumen";
	}

	@RequestMapping("/portales/alumno/covid")
	public String portalesAlumnoCovid(HttpServletRequest request, Model modelo) {
		String codigoPersonal = "0";
		AlumPersonal alumno = new AlumPersonal();
		MapaPlan mapaPlan = new MapaPlan();
		CatCarrera catCarrera = new CatCarrera();
		String nombreFacultad = "";

		String planId = request.getParameter("PlanId") == null ? "0" : request.getParameter("PlanId");
		String mensaje = request.getParameter("Mensaje") == null ? "0" : request.getParameter("Mensaje");

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			codigoPersonal = (String) sesion.getAttribute("codigoAlumno");
			alumno = alumPersonalDao.mapeaRegId(codigoPersonal);
		}

		if (mapaPlanDao.existeReg(planId)) {
			mapaPlan = mapaPlanDao.mapeaRegId(planId);

			if (catCarreraDao.existeReg(mapaPlan.getCarreraId())) {
				catCarrera = catCarreraDao.mapeaRegId(mapaPlan.getCarreraId());
				nombreFacultad = catFacultadDao.getNombreFacultad(catCarrera.getFacultadId());
			}
		}

		AlertaCovid alertaCovid = new AlertaCovid();
		alertaCovid.setPaisUno("91");
		alertaCovid.setPaisDos("91");

		String folio = alertaCovidDao.ultimoFolio(codigoPersonal);
		if (alertaCovidDao.existeReg(codigoPersonal)) {
			alertaCovid = alertaCovidDao.mapeaRegIdFolio(codigoPersonal, folio);
			if (!alertaCovid.getContactoFecha().equals("01/01/2000")) {
				alertaCovid.setContactoFecha("");
			}
		}

		List<CatPais> listaPais = catPaisDao.getListAll("NOMBRE_PAIS");

		modelo.addAttribute("alertaCovid", alertaCovid);
		modelo.addAttribute("alumno", alumno);
		modelo.addAttribute("nombreFacultad", nombreFacultad);
		modelo.addAttribute("nombreCarrera", catCarrera.getNombreCarrera());
		modelo.addAttribute("listaPais", listaPais);
		modelo.addAttribute("mensaje", mensaje);

		return "portales/alumno/covid";
	}

	@RequestMapping("/portales/alumno/resCovid")
	public String portalesAlumnoResCovid(HttpServletRequest request, Model modelo) {
		String codigoPersonal = "0";
		String folio = "1";
		AlumPersonal alumno = new AlumPersonal();

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			codigoPersonal = (String) sesion.getAttribute("codigoAlumno");
			alumno = alumPersonalDao.mapeaRegId(codigoPersonal);
			folio = alertaCovidDao.ultimoFolio(codigoPersonal);
		}

		NotiCovid notiCovid = new NotiCovid();
		notiCovid = alertaCovidDao.evaluaRespuesta(codigoPersonal, folio);

		modelo.addAttribute("alumno", alumno);
		modelo.addAttribute("notiCovid", notiCovid);

		return "portales/alumno/resCovid";
	}

	@RequestMapping("/portales/alumno/grabarCovid")
	public String portalesAlumnoGrabarCovid(HttpServletRequest request, Model modelo) {

		String codigoPersonal = request.getParameter("CodigoPersonal") == null ? "-"
				: request.getParameter("CodigoPersonal");
		String paisUno = request.getParameter("PaisUno") == null ? "-" : request.getParameter("PaisUno");
		String paisDos = request.getParameter("PaisDos") == null ? "-" : request.getParameter("PaisDos");
		String ciudadUno = request.getParameter("CiudadUno") == null ? "-" : request.getParameter("CiudadUno");
		String ciudadDos = request.getParameter("CiudadDos") == null ? "-" : request.getParameter("CiudadDos");
		String fechaUno = request.getParameter("FechaUno") == null ? "-" : request.getParameter("FechaUno");
		String fechaDos = request.getParameter("FechaDos") == null ? "-" : request.getParameter("FechaDos");
		String contacto = request.getParameter("Contacto") == null ? "-" : request.getParameter("Contacto");
		String fechaContacto = request.getParameter("FechaContacto") == null ? ""
				: request.getParameter("FechaContacto");
		String fiebre = request.getParameter("Fiebre") == null ? "-" : request.getParameter("Fiebre");
		String tos = request.getParameter("Tos") == null ? "-" : request.getParameter("Tos");
		String cabeza = request.getParameter("Cabeza") == null ? "-" : request.getParameter("Cabeza");
		String respirar = request.getParameter("Respirar") == null ? "-" : request.getParameter("Respirar");
		String garganta = request.getParameter("Garganta") == null ? "-" : request.getParameter("Garganta");
		String escurrimiento = request.getParameter("Escurrimiento") == null ? "-"
				: request.getParameter("Escurrimiento");
		String olfato = request.getParameter("Olfato") == null ? "-" : request.getParameter("Olfato");
		String gusto = request.getParameter("Gusto") == null ? "-" : request.getParameter("Gusto");
		String cuerpo = request.getParameter("Cuerpo") == null ? "-" : request.getParameter("Cuerpo");
		String folio = "1";
		String mensaje = "0";

		AlertaCovid alertaCovid = new AlertaCovid();

		if (fechaContacto.equals("")) {
			fechaContacto = "01/01/2000";
		}

		alertaCovid.setCodigoPersonal(codigoPersonal);
		alertaCovid.setPaisUno(paisUno);
		alertaCovid.setPaisDos(paisDos);
		alertaCovid.setCiudadUno(ciudadUno);
		alertaCovid.setCiudadDos(ciudadDos);
		alertaCovid.setFechaUno(fechaUno);
		alertaCovid.setFechaDos(fechaDos);
		alertaCovid.setContacto(contacto);
		alertaCovid.setContactoFecha(fechaContacto);
		alertaCovid.setFiebre(fiebre);
		alertaCovid.setTos(tos);
		alertaCovid.setCabeza(cabeza);
		alertaCovid.setRespirar(respirar);
		alertaCovid.setGarganta(garganta);
		alertaCovid.setEscurrimiento(escurrimiento);
		alertaCovid.setOlfato(olfato);
		alertaCovid.setGusto(gusto);
		alertaCovid.setCuerpo(cuerpo);
		if (alertaCovidDao.existeReg(codigoPersonal)) {
			folio = alertaCovidDao.maximoFolio(codigoPersonal);
			alertaCovid.setFolio(folio);
			alertaCovidDao.insertReg(alertaCovid);
			mensaje = "1";
		} else {
			alertaCovid.setFolio(folio);
			if (alertaCovidDao.insertReg(alertaCovid)) {
				mensaje = "1";
			}
		}

		return "redirect:/portales/alumno/resCovid?Mensaje=" + mensaje;
	}

	@ResponseBody
	@RequestMapping(value = "/portales/alumno/verificaSolicitud")
	public String verificaSolicitud(HttpServletRequest request) {
		String codigoPersonal = "0";
		String eventoId = request.getParameter("EventoId") == null ? "-" : request.getParameter("EventoId");
		String planId = request.getParameter("PlanId") == null ? "-" : request.getParameter("PlanId");

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			codigoPersonal = (String) sesion.getAttribute("codigoAlumno");
		}

		String mensaje = "OK";
		if (matAlumnoDao.existeReg(eventoId, codigoPersonal, planId)) {
			mensaje = "You have already made a request!";
		}

		return mensaje;
	}

	@RequestMapping("/portales/alumno/datos")
	public String portalesAlumnoDatos(HttpServletRequest request, Model modelo) {

		String matricula = "0";
		String codigoPersonal = "0";
		String ejercicioId = "0";

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula 			= (String) sesion.getAttribute("codigoAlumno");
			codigoPersonal 		= sesion.getAttribute("codigoPersonal") == null ?"-":(String) sesion.getAttribute("codigoPersonal");
			ejercicioId 		= (String) sesion.getAttribute("ejercicioId");
		}
		
		String fechaHoy 		= aca.util.Fecha.getHoy();
		String periodoId 		= catPeriodoDao.getPeriodoActivo();	
		
		AlumPersonal alumPersonal 		= alumPersonalDao.mapeaRegId(matricula);
		AlumAcademico alumAcademico 	= alumAcademicoDao.mapeaRegId(matricula);
		AlumUbicacion alumUbicacion		= alumUbicacionDao.mapeaRegId(matricula);
		AlumPlan alumPlan 				= alumPlanDao.mapeaRegId(matricula);
		ResDatos resDatos 				= resDatosDao.mapeaRegId(matricula);
		AlumImagen alumImagen 			= alumImagenDao.mapeaRegId(matricula);
		AlumBanco alumBanco				= alumBancoDao.mapeaRegId(matricula);

		String puestoReciente 			= becPuestoAlumnoDao.maximoPuesto(matricula);
		String puestoId 				= request.getParameter("puestoId") == null ? puestoReciente : request.getParameter("puestoId");
		String mentorId 				= mentAlumnoDao.getMentorId(matricula, periodoId, fechaHoy);
		String mentorNombre	 			= maestrosDao.getNombreMaestro(codigoPersonal, "NOMBRE");
		String mentorCorto	 			= maestrosDao.getNombreCorto(mentorId, "NOMBRE");
		String nombreReligion 			= catReligionDao.getNombreReligion(alumPersonal.getReligionId());
		String nombreInstitucion 		= catInstitucionDao.getNombreInstitucionFinanzas(alumAcademico.getObreroInstitucion());
		String nombreTipo 				= catTipoAlumnoDao.getNombreTipo(alumAcademico.getTipoAlumno());
		String nombreNacionalidad	 	= catPaisDao.getNacionalidad(alumPersonal.getNacionalidad());
		String fechaVencimiento	 		= alumPersonalDao.getVencimientoFM3(alumPersonal.getCodigoPersonal());
		boolean alumnoInscrito 			= alumPersonalDao.esInscrito(matricula);
		boolean tieneFoto 				= false;		
		BecPuestoAlumno puestoAlumno 	= becPuestoAlumnoDao.mapeaRegId(puestoId);
		
		String horasTotal 				= becAcuerdoDao.getHorasConv(matricula, puestoId);
		String paisNombre 				= catPaisDao.getNombrePais(alumPersonal.getPaisId());
		String estadoNombre			 	= catEstadoDao.getNombreEstado(alumPersonal.getPaisId(), alumPersonal.getEstadoId());
		String ciudadNombre 			= catCiudadDao.getNombreCiudad(alumPersonal.getPaisId(), alumPersonal.getEstadoId(),alumPersonal.getCiudadId());
		String razonNombre 				= resRazonDao.nombreRazon(resDatos.getRazon());
		int numAutos					= vigAutoDao.numAutos(matricula, "'A'");
		
		boolean tieneFotoEnviada = posFotoAlumDao.existeReg(matricula, "E");

		if (posFotoAlumDao.existeReg(matricula, "O")) {
			tieneFoto = true;
		}

		List<CargaAlumno> lisPlanes 				= cargaAlumnoDao.lisCargasActivas(matricula);
		List<LegExtdoctos> lisExtDoc 				= legExtdoctosDao.lisAlumno(matricula);
		List<BecPuestoAlumno> lisPuestos 			= becPuestoAlumnoDao.getListAllEjerciciosAlum(matricula," ORDER BY PUESTO_ID DESC");
		List<BecInforme> lisInformes 				= becInformeDao.getListPuestoAlumno(matricula, puestoId," ORDER BY ID_EJERCICIO,ORDEN");
		List<VigAuto> lisAutos 						= vigAutoDao.lisPorUsuario(matricula, "ORDER BY FECHA DESC");
		List<VigDoc> lisDocAutos				 	= vigDocDao.lisTodos(" ORDER BY DOCUMENTO_ID");

		HashMap<String, MapaPlan> mapaPlanes 		= mapaPlanDao.mapPlanes("'V','I','A'");
		HashMap<String, String> mapaDocumentos 		= legDocumentoDao.mapaDocumentos();
		// HashMap<String, ContCcosto> mapaDeptos 		= contCcostoDao.mapaDeptos("S");
		HashMap<String, String> mapaHorasAlumno 	= becInformeAlumnoDao.mapHorasPuestoAlumno(matricula, puestoId);
		HashMap<String, String> mapaEstadoAlumno 	= becInformeAlumnoDao.mapEstado(matricula, puestoId);
		HashMap<String, String> mapaEvalAlumno 		= becInformeAlumnoDao.mapEvaluacionAlumno(matricula);
		HashMap<String, String> mapaCategorias 		= becCategoriaDao.getMapCategorias();
		HashMap<String, BecContrato> mapaContratos 	= becContratoDao.mapaContratosAlumno(matricula);
		HashMap<String,String> mapaImagenes	 		= vigDocumentoDao.mapaConImagen();
		HashMap<String,String> mapaDocAutos			= vigDocAutoDao.mapaTodos();
		HashMap<String,CatBanco> mapaBanco 			= catBancoDao.getMapAll(" ORDER BY BANCO_ID");


		modelo.addAttribute("SubMenu", "2");
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("alumAcademico", alumAcademico);
		modelo.addAttribute("alumUbicacion", alumUbicacion);
		modelo.addAttribute("alumPlan", alumPlan);
		modelo.addAttribute("resDatos", resDatos);
		modelo.addAttribute("puestoReciente", puestoReciente);
		modelo.addAttribute("puestoId", puestoId);
		modelo.addAttribute("mentorId", mentorId);
		modelo.addAttribute("mentorNombre", mentorNombre);
		modelo.addAttribute("mentorCorto", mentorCorto);
		modelo.addAttribute("nombreReligion", nombreReligion);
		modelo.addAttribute("nombreInstitucion", nombreInstitucion);
		modelo.addAttribute("nombreTipo", nombreTipo);
		modelo.addAttribute("nombreNacionalidad", nombreNacionalidad);
		modelo.addAttribute("fechaVencimiento", fechaVencimiento);
		modelo.addAttribute("alumnoInscrito", alumnoInscrito);
		modelo.addAttribute("puestoAlumno", puestoAlumno);
		modelo.addAttribute("horasTotal", horasTotal);
		modelo.addAttribute("paisNombre", paisNombre);
		modelo.addAttribute("estadoNombre", estadoNombre);
		modelo.addAttribute("ciudadNombre", ciudadNombre);
		modelo.addAttribute("razonNombre", razonNombre);
		modelo.addAttribute("tieneFoto", tieneFoto);
		modelo.addAttribute("tieneFotoEnviada", tieneFotoEnviada);
		modelo.addAttribute("alumImagen", alumImagen);
		modelo.addAttribute("numAutos", numAutos);
		modelo.addAttribute("alumBanco", alumBanco);
		
		modelo.addAttribute("lisPuestos", lisPuestos);
		modelo.addAttribute("lisInformes", lisInformes);
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("lisExtDoc", lisExtDoc);
		modelo.addAttribute("lisAutos", lisAutos);
		modelo.addAttribute("lisDocAutos", lisDocAutos);

		modelo.addAttribute("mapaPlanes", mapaPlanes);
		modelo.addAttribute("mapaDocumentos", mapaDocumentos);
		// modelo.addAttribute("mapaDeptos", mapaDeptos);
		modelo.addAttribute("mapaHorasAlumno", mapaHorasAlumno);
		modelo.addAttribute("mapaEstadoAlumno", mapaEstadoAlumno);
		modelo.addAttribute("mapaEvalAlumno", mapaEvalAlumno);
		modelo.addAttribute("mapaCategorias", mapaCategorias);
		modelo.addAttribute("mapaContratos", mapaContratos);
		modelo.addAttribute("mapaImagenes", mapaImagenes);
		modelo.addAttribute("mapaDocAutos", mapaDocAutos);	
		modelo.addAttribute("mapaBanco", mapaBanco);

		return "portales/alumno/datos";
	}

	@RequestMapping("/portales/alumno/votoMoodie")
	public String portalesAlumnoVotoMoodie(HttpServletRequest request, Model modelo) {

		String codigoPersonal = "0";

		HttpSession sesion = null;
		sesion = request.getSession();
		if (sesion != null) {
			codigoPersonal = (String) sesion.getAttribute("codigoAlumno");
		}

		VotoCandidato candidato = new VotoCandidato();
		String eventoVoto = request.getParameter("eventoVoto") == null ? "0" : request.getParameter("eventoVoto");
		String eventoNombre = votoEventoDao.eventoNombre(eventoVoto);
		String candidatoId = request.getParameter("candidatoId") == null ? "1" : request.getParameter("candidatoId");
		String candidatoNombre = votoCandidatoDao.getCandidatoNombre(eventoVoto, candidatoId);
		boolean existe = false;
		boolean yaVoto = false;
		String candidatos = "";
		String candidatas = "";
		String codigos = votoCandidatoDao.getCodigos(eventoVoto, candidatoId);

		HashMap<String, String> mapa = alumPersonalDao.mapCandidatos(codigos);
		// HashMap<String,String> mapaVoto = votoAlumnoDao.mapaVotos(eventoVoto);
		HashMap<String, String> mapaYaVoto = votoAlumnoDao.mapaYaVoto(eventoVoto, codigoPersonal);

		List<String> lisElegidos = votoAlumnoDao.lisElegidos(eventoVoto, codigoPersonal);
		List<VotoCandidato> lisCandidatos = votoCandidatoDao.getListaEvento(eventoVoto, " ORDER BY CANDIDATO_ID");
		if (votoCandidatoDao.existeReg(eventoVoto, candidatoId)) {
			candidato = votoCandidatoDao.mapeaRegId(eventoVoto, candidatoId);
			existe = true;
			candidatos = candidato.getCandidatos() == null ? "-" : candidato.getCandidatos();
			candidatas = candidato.getCandidatas() == null ? "-" : candidato.getCandidatas();
		}

		// Valida si ya voto el alumno
		if (votoAlumnoDao.existeReg(codigoPersonal, eventoVoto, candidatoId)) {
			yaVoto = true;
		}

		modelo.addAttribute("eventoVoto", eventoVoto);
		modelo.addAttribute("eventoNombre", eventoNombre);
		modelo.addAttribute("lisCandidatos", lisCandidatos);
		modelo.addAttribute("lisElegidos", lisElegidos);
		modelo.addAttribute("candidatoId", candidatoId);
		modelo.addAttribute("candidatoNombre", candidatoNombre);
		modelo.addAttribute("candidatos", candidatos);
		modelo.addAttribute("candidatas", candidatas);
		modelo.addAttribute("existe", existe);
		modelo.addAttribute("mapa", mapa);
		modelo.addAttribute("mapaYaVoto", mapaYaVoto);
		modelo.addAttribute("yaVoto", yaVoto);

		return "portales/alumno/votoMoodie";
	}

	@RequestMapping("/portales/alumno/votoFed")
	public String portalesAlumnoVoto(HttpServletRequest request, Model modelo) {

		String codigoPersonal = "0";
		String codigoSesion = "";

		HttpSession sesion = null;
		sesion = request.getSession();
		if (sesion != null) {
			codigoPersonal = (String) sesion.getAttribute("codigoPersonal");
			codigoSesion = (String) sesion.getAttribute("codigoLogeado");
		}

		String eventoVoto = request.getParameter("eventoVoto") == null ? "0" : request.getParameter("eventoVoto");

		VotoCandidato candidato = new VotoCandidato();
		VotoEvento votoEvento = votoEventoDao.mapeaRegId(eventoVoto);
		String eventoNombre = votoEventoDao.eventoNombre(eventoVoto);
		String poblacion = votoEventoDao.getPoblacion(eventoVoto);
		String candidatoId = request.getParameter("candidatoId") == null ? "1" : request.getParameter("candidatoId");
		String candidatoNombre = votoCandidatoDao.getCandidatoNombre(eventoVoto, candidatoId);
		String voto = votoAlumnoDao.getVoto(codigoPersonal, eventoVoto, candidatoId);
		boolean existe = false;
		boolean yaVoto = false;
		boolean esVotante = false;
		boolean esInscrito = inscritosDao.existeReg(codigoPersonal);
		if ((poblacion.equals("-") && esInscrito && codigoPersonal.equals(codigoSesion))
				|| (poblacion.contains(codigoPersonal) && codigoPersonal.equals(codigoSesion)))
			esVotante = true;

		List<String> lisFacultades = votoCandidatoDao.lisFacultadesGanadoras(eventoVoto);
		List<VotoCandidato> lisCandidatos = votoCandidatoDao.getListaEvento(eventoVoto, " ORDER BY CANDIDATO_ID");
		List<VotoCandidatoAlumno> lisAlumnos = votoCandidatoAlumnoDao.lisEventos(eventoVoto, candidatoId," ORDER BY ORDEN");

		HashMap<String, String> mapa = alumPersonalDao.mapCandidatos(eventoVoto, candidatoId);
		HashMap<String, String> mapaVoto = votoAlumnoDao.mapaVotos(eventoVoto);
		HashMap<String, String> mapaYaVoto = votoAlumnoDao.mapaYaVoto(eventoVoto, codigoPersonal);
		HashMap<String, CatFacultad> mapaFacultades = catFacultadDao.getMapFacultad("");

		if (votoCandidatoDao.existeReg(eventoVoto, candidatoId)) {
			candidato = votoCandidatoDao.mapeaRegId(eventoVoto, candidatoId);
			existe = true;
		}
		// Valida si ya voto el alumno
		if (votoAlumnoDao.existeReg(codigoPersonal, eventoVoto, candidatoId)) {
			yaVoto = true;
		}

		modelo.addAttribute("eventoVoto", eventoVoto);
		modelo.addAttribute("votoEvento", votoEvento);
		modelo.addAttribute("eventoNombre", eventoNombre);
		modelo.addAttribute("candidatoId", candidatoId);
		modelo.addAttribute("candidatoNombre", candidatoNombre);
		modelo.addAttribute("existe", existe);
		modelo.addAttribute("yaVoto", yaVoto);
		modelo.addAttribute("esVotante", esVotante);
		modelo.addAttribute("estado", candidato.getEstado());
		modelo.addAttribute("ganador", candidato.getGanador());
		modelo.addAttribute("voto", voto);

		modelo.addAttribute("lisCandidatos", lisCandidatos);
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("lisFacultades", lisFacultades);
		modelo.addAttribute("mapa", mapa);
		modelo.addAttribute("mapaVoto", mapaVoto);
		modelo.addAttribute("mapaYaVoto", mapaYaVoto);
		modelo.addAttribute("mapaFacultades", mapaFacultades);

		return "portales/alumno/votoFed";
	}

	@RequestMapping("/portales/alumno/votar")
	public String portalesAlumnoVotar(HttpServletRequest request, Model modelo) {
		String codigoPersonal = "";

		HttpSession sesion = null;
		sesion = request.getSession();
		if (sesion != null) {
			codigoPersonal = (String) sesion.getAttribute("codigoPersonal");
		}

		VotoAlumno votoAlumno = new VotoAlumno();
		String eventoVoto = request.getParameter("eventoVoto") == null ? "0" : request.getParameter("eventoVoto");
		String candidatoId = request.getParameter("candidatoId") == null ? "1" : request.getParameter("candidatoId");
		String votoM = request.getParameter("votoM") == null ? "-" : request.getParameter("votoM");
		String votoH = request.getParameter("votoH") == null ? "-" : request.getParameter("votoH");

		votoAlumno.setCodigoPersonal(codigoPersonal);
		votoAlumno.setCandidatoId(candidatoId);
		votoAlumno.setVoto(votoM);
		if (!votoAlumnoDao.existeReg(codigoPersonal, candidatoId, votoM)) {
			votoAlumnoDao.insertReg(votoAlumno);
		}

		votoAlumno = new VotoAlumno();
		votoAlumno.setCodigoPersonal(codigoPersonal);
		votoAlumno.setEventoId(eventoVoto);
		votoAlumno.setCandidatoId(candidatoId);
		votoAlumno.setVoto(votoH);
		if (!votoAlumnoDao.existeReg(codigoPersonal, eventoVoto, candidatoId, votoH)) {
			votoAlumnoDao.insertReg(votoAlumno);
		}

		return "redirect:/portales/alumno/votoMoodie?eventoVoto=" + eventoVoto + "&candidatoId=" + candidatoId;
	}

	@RequestMapping("/portales/alumno/votarFed")
	public String portalesAlumnoVotarFed(HttpServletRequest request, Model modelo) {
		String codigoPersonal = "";

		HttpSession sesion = null;
		sesion = request.getSession();
		if (sesion != null) {
			codigoPersonal = (String) sesion.getAttribute("codigoPersonal");
		}

		VotoAlumno votoAlumno = new VotoAlumno();
		String eventoVoto = request.getParameter("eventoVoto") == null ? "0" : request.getParameter("eventoVoto");
		String candidatoId = request.getParameter("candidatoId") == null ? "1" : request.getParameter("candidatoId");
		String voto = request.getParameter("voto") == null ? "-" : request.getParameter("voto");
		String poblacion = votoEventoDao.getPoblacion(eventoVoto);

		boolean votoValido = false;
		if (poblacion.equals("-")) {
			votoValido = inscritosDao.existeReg(codigoPersonal);
		} else {
			if (poblacion.contains(codigoPersonal))
				votoValido = true;
		}
		votoAlumno.setCodigoPersonal(codigoPersonal);
		votoAlumno.setEventoId(eventoVoto);
		votoAlumno.setCandidatoId(candidatoId);
		votoAlumno.setVoto(voto);
		if (!votoAlumnoDao.existeReg(codigoPersonal, eventoVoto, candidatoId, voto) && votoValido) {
			votoAlumnoDao.insertReg(votoAlumno);
		}

		return "redirect:/portales/alumno/votoFed?eventoVoto=" + eventoVoto + "&candidatoId=" + candidatoId;
	}

	@RequestMapping("/portales/alumno/votoManana")
	public String portalesAlumnoVotoManana(HttpServletRequest request, Model modelo) {

		String codigoPersonal = "0";
		boolean esInscrito = false;
		HttpSession sesion = null;
		sesion = request.getSession();
		if (sesion != null) {
			codigoPersonal = (String) sesion.getAttribute("codigoAlumno");
			esInscrito = inscritosDao.inscrito(codigoPersonal);
		}

		VotoCandidato candidato = new VotoCandidato();
		String eventoVoto 		= request.getParameter("eventoVoto") == null ? "0" : request.getParameter("eventoVoto");
		String eventoNombre 	= votoEventoDao.eventoNombre(eventoVoto);
		String candidatoId 		= request.getParameter("candidatoId") == null ? "1" : request.getParameter("candidatoId");
		String candidatoNombre 	= votoCandidatoDao.getCandidatoNombre(eventoVoto, candidatoId);
		String fechaFin 		= votoEventoDao.getFechaFin(eventoVoto);
		boolean existe = false;
		boolean yaVoto = false;
		String candidatos = "";
		String codigos = votoCandidatoDao.getCodigos(eventoVoto, candidatoId);

		HashMap<String, String> mapa = usuariosDao.mapCandidatos(codigos);
		HashMap<String, String> mapaVoto = votoAlumnoDao.mapaVotos(eventoVoto);
		HashMap<String, String> mapaYaVoto = votoAlumnoDao.mapaYaVoto(eventoVoto, codigoPersonal);

		List<String> lisElegidos = votoAlumnoDao.lisElegidos(eventoVoto, codigoPersonal);
		List<VotoCandidato> lisCandidatos = votoCandidatoDao.getListaEvento(eventoVoto, " ORDER BY CANDIDATO_ID");
		if (votoCandidatoDao.existeReg(eventoVoto, candidatoId)) {
			candidato = votoCandidatoDao.mapeaRegId(eventoVoto, candidatoId);
			existe = true;
			candidatos = candidato.getCandidatos() == null ? "-" : candidato.getCandidatos();
		}
		// Valida si ya voto el alumno
		if (votoAlumnoDao.existeReg(codigoPersonal, eventoVoto, candidatoId)) {
			yaVoto = true;
		}

		modelo.addAttribute("eventoVoto", eventoVoto);
		modelo.addAttribute("eventoNombre", eventoNombre);
		modelo.addAttribute("lisCandidatos", lisCandidatos);
		modelo.addAttribute("lisElegidos", lisElegidos);
		modelo.addAttribute("candidatoId", candidatoId);
		modelo.addAttribute("candidatoNombre", candidatoNombre);
		modelo.addAttribute("candidatos", candidatos);
		modelo.addAttribute("existe", existe);
		modelo.addAttribute("mapa", mapa);
		modelo.addAttribute("mapaVoto", mapaVoto);
		modelo.addAttribute("mapaYaVoto", mapaYaVoto);
		modelo.addAttribute("yaVoto", yaVoto);
		modelo.addAttribute("esInscrito", esInscrito);
		modelo.addAttribute("estado",candidato.getEstado());
		modelo.addAttribute("fechaFin",fechaFin);

		return "portales/alumno/votoManana";
	}

	@RequestMapping("/portales/alumno/votarMan")
	public String portalesAlumnoVotarMan(HttpServletRequest request, Model modelo) {
		String codigoPersonal = "";

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			codigoPersonal = (String) sesion.getAttribute("codigoAlumno");
		}

		VotoAlumno votoAlumno 	= new VotoAlumno();
		String eventoVoto 		= request.getParameter("eventoVoto") == null ? "0" : request.getParameter("eventoVoto");
		String candidatoId 		= request.getParameter("candidatoId") == null ? "1" : request.getParameter("candidatoId");
		String voto 			= request.getParameter("voto") == null ? "-" : request.getParameter("voto");
		String fechaFin 		= votoEventoDao.getFechaFin(eventoVoto);
		String mensaje 			= "-";
		SimpleDateFormat sdf 	= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date dateEvento 		= new Date();
		try {
			dateEvento 			= sdf.parse(fechaFin); 
		}catch(Exception ex){
			System.out.println("Error in the formatting of the event closing date!");
		}
		Date dateHoy 			= new Date();

		votoAlumno.setCodigoPersonal(codigoPersonal);
		votoAlumno.setEventoId(eventoVoto);
		votoAlumno.setCandidatoId(candidatoId);
		votoAlumno.setVoto(voto);
		if (!votoAlumnoDao.existeReg(codigoPersonal, eventoVoto, candidatoId, voto)) {
			if (dateHoy.before(dateEvento)){
				if (votoAlumnoDao.insertReg(votoAlumno)){
					mensaje = "Vote registered";
				}else {
					mensaje = "Error saving vote";
				}
			}else {
				mensaje = "Your vote was not registered because the period has ended!";
			}
		}
		return "redirect:/portales/alumno/votoManana?eventoVoto="+eventoVoto+"&candidatoId="+candidatoId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/portales/alumno/votoRopa")
	public String portalesAlumnoVotoRopa(HttpServletRequest request, Model modelo) {

		String codigoPersonal = "0";
		boolean esInscrito = false;
		HttpSession sesion = null;
		sesion = request.getSession();
		if (sesion != null) {
			codigoPersonal = (String) sesion.getAttribute("codigoAlumno");
			esInscrito = inscritosDao.inscrito(codigoPersonal);
		}

		VotoCandidato candidato = new VotoCandidato();
		String eventoVoto 		= request.getParameter("eventoVoto") == null ? "0" : request.getParameter("eventoVoto");
		String eventoNombre 	= votoEventoDao.eventoNombre(eventoVoto);
		String candidatoId 		= request.getParameter("candidatoId") == null ? "1" : request.getParameter("candidatoId");
		String candidatoNombre 	= votoCandidatoDao.getCandidatoNombre(eventoVoto, candidatoId);
		String fechaFin 		= votoEventoDao.getFechaFin(eventoVoto);
		boolean existe = false;
		boolean yaVoto = false;
		String candidatos = "";
		String codigos 			= votoCandidatoDao.getCodigos(eventoVoto, candidatoId);
		
		HashMap<String, String> mapaVoto 	= votoAlumnoDao.mapaVotos(eventoVoto);
		HashMap<String, String> mapaYaVoto 	= votoAlumnoDao.mapaYaVoto(eventoVoto, codigoPersonal);

		List<String> lisElegidos 			= votoAlumnoDao.lisElegidos(eventoVoto, codigoPersonal);
		List<VotoCandidato> lisCandidatos 	= votoCandidatoDao.getListaEvento(eventoVoto, " ORDER BY CANDIDATO_ID");
		if (votoCandidatoDao.existeReg(eventoVoto, candidatoId)) {
			candidato 	= votoCandidatoDao.mapeaRegId(eventoVoto, candidatoId);
			existe 		= true;
			candidatos 	= candidato.getCandidatos() == null ? "-" : candidato.getCandidatos();
		}
		// Valida si ya voto el alumno
		if (votoAlumnoDao.existeReg(codigoPersonal, eventoVoto, candidatoId)) {
			yaVoto = true;
		}

		modelo.addAttribute("eventoVoto", eventoVoto);
		modelo.addAttribute("eventoNombre", eventoNombre);
		modelo.addAttribute("candidatoId", candidatoId);
		modelo.addAttribute("candidatoNombre", candidatoNombre);
		modelo.addAttribute("candidatos", candidatos);				
		modelo.addAttribute("existe", existe);
		modelo.addAttribute("yaVoto", yaVoto);
		modelo.addAttribute("esInscrito", esInscrito);		
		modelo.addAttribute("fechaFin",fechaFin);
		modelo.addAttribute("estado",candidato.getEstado());		
		modelo.addAttribute("codigos", codigos);
		
		
		modelo.addAttribute("lisCandidatos", lisCandidatos);
		modelo.addAttribute("lisElegidos", lisElegidos);	
		modelo.addAttribute("mapaVoto", mapaVoto);
		modelo.addAttribute("mapaYaVoto", mapaYaVoto);
		
		
		return "portales/alumno/votoRopa";
	}
	
	@RequestMapping("/portales/alumno/votarRopa")
	public String portalesAlumnoVotarRopa(HttpServletRequest request, Model modelo) {
		String codigoPersonal = "";

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			codigoPersonal = (String) sesion.getAttribute("codigoAlumno");
		}

		VotoAlumno votoAlumno 	= new VotoAlumno();
		String eventoVoto 		= request.getParameter("eventoVoto") == null ? "0" : request.getParameter("eventoVoto");
		String candidatoId 		= request.getParameter("candidatoId") == null ? "1" : request.getParameter("candidatoId");
		String voto 			= request.getParameter("voto") == null ? "-" : request.getParameter("voto");
		String fechaFin 		= votoEventoDao.getFechaFin(eventoVoto);
		String mensaje 			= "-";
		SimpleDateFormat sdf 	= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date dateEvento 		= new Date();
		try {
			dateEvento 			= sdf.parse(fechaFin); 
		}catch(Exception ex){
			System.out.println("Error in the event closing date format!");
		}
		Date dateHoy 			= new Date();

		votoAlumno.setCodigoPersonal(codigoPersonal);
		votoAlumno.setEventoId(eventoVoto);
		votoAlumno.setCandidatoId(candidatoId);
		votoAlumno.setVoto(voto);
		if (!votoAlumnoDao.existeReg(codigoPersonal, eventoVoto, candidatoId, voto)) {
			if (dateHoy.before(dateEvento)){
				if (votoAlumnoDao.insertReg(votoAlumno)){
					mensaje = "Vote registered";
				}else {
					mensaje = "Error saving vote";
				}
			}else {
				mensaje = "Your vote was not registered because the period has ended!";
			}
		}
		return "redirect:/portales/alumno/votoRopa?eventoVoto="+eventoVoto+"&candidatoId="+candidatoId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/portales/alumno/bajarPlan")
	public void portalesAlumnoBajarPlan(HttpServletRequest request, HttpServletResponse response ) {
		
		MapaArchivo archivo 	= new aca.plan.spring.MapaArchivo();
		String planId 			= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId"); 
		String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");		
		try {						
			if(mapaArchivoDao.existeReg(planId, folio)){
				archivo = mapaArchivoDao.mapeaRegId(planId, folio);			
				
				OutputStream out = response.getOutputStream();
				response.setHeader("Content-Disposition","attachment; filename=\""+ archivo.getNombre()+ "\"");
				out.write(archivo.getArchivo());				
			}
		}catch(Exception ex){
			System.out.println("Error:mapa/plan/bajarPlan:"+ex);
		}		
	}

	@RequestMapping("/portales/alumno/bajarArchivo")
	public void portalesAlumnoBajarArchivo(HttpServletRequest request, HttpServletResponse response) {

		String cursoCargaId = request.getParameter("CursoCargaId") == null ? "0" : request.getParameter("CursoCargaId");
		String folio 		= request.getParameter("Folio") == null ? "0" : request.getParameter("Folio");
		String profesor 	= request.getParameter("Profesor") == null ? "0" : request.getParameter("Profesor");
		
		ArchivosProfesor archivo = archivosProfesorDao.mapeaRegId(cursoCargaId, Integer.parseInt(folio), profesor);		
		try{
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition","attachment; filename=\""+archivo.getNombre()+ "\"");
			response.getOutputStream().write(archivo.getArchivoNuevo());						
			response.flushBuffer();
			
		}catch(Exception ex){
			System.out.println("Error /bajarArchivo:"+ex);
		}
	}

	@RequestMapping("/portales/alumno/bajarArchivoAlumno")
	public void portalesAlumnoBajarArchivoAlumno(HttpServletRequest request, HttpServletResponse response) {

		String archivoId 		= request.getParameter("CursoCargaId") == null ? "0" : request.getParameter("CursoCargaId");
		String codigoAlumno 	= request.getParameter("CodigoAlumno") == null ? "0" : request.getParameter("CodigoAlumno");
		String folio 			= request.getParameter("Folio") == null ? "0" : request.getParameter("Folio");
		
		try {
			if (archivosAlumnoDao.existeReg(codigoAlumno, archivoId, folio)) {				
				ArchivosAlumno archivo = archivosAlumnoDao.mapeaRegId(codigoAlumno, archivoId, folio);

				response.setContentType("application/octet-stream");
				response.setHeader("Content-Disposition", "attachment; filename=\"" + archivo.getNombre() + "\"");
				response.getOutputStream().write(archivo.getArchivoNuevo());
				response.flushBuffer();
			}
		} catch (Exception ex) {
			System.out.println("Error:mapa/plan/bajarArchivo:" + ex);
		}
	}

	@RequestMapping("/portales/alumno/disciplina")
	public String portalesAlumnoDisciplina(HttpServletRequest request, Model modelo) {

		String codigoAlumno 		= "0";
		String codigoPersonal	= "0";
		String nombreAlumno 	= "-";
		String planId 			= "0";
		String carreraId 		= "0";
		String carreraNombre 	= "0";
		String periodoId 		= "0";
		String periodo 			= request.getParameter("periodo") == null ? catPeriodoDao.getPeriodo(): request.getParameter("periodo");

		HttpSession sesion 	= request.getSession();
		if (sesion != null) {
			codigoAlumno 	= (String) sesion.getAttribute("codigoAlumno");
			codigoPersonal 	= (String) sesion.getAttribute("codigoPersonal");
			planId 			= alumPlanDao.getPlanActual(codigoAlumno);
			carreraId 		= mapaPlanDao.getCarreraId(planId);
			carreraNombre 	= catCarreraDao.getNombreCarrera(carreraId);
			periodoId 		= catPeriodoDao.getPeriodoActivo();
			nombreAlumno 	= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
		}
		
		// Lista de planes activos del alumno
		List<CargaAlumno> lisPlanes = cargaAlumnoDao.lisCargasActivas(codigoAlumno);
		List<CondAlumno> lisAlumno = condAlumnoDao.getLista(periodo, codigoAlumno, "ORDER BY ENOC.COND_ALUMNO.FECHA");

		HashMap<String, CondReporte> mapaReportes = condReporteDao.mapaReportes();
		HashMap<String, String> mapaLugar = condLugarDao.mapaLugar();
		HashMap<String, String> mapaJuez = condJuezDao.mapaJuez();
		HashMap<String, MapaPlan> mapaPlanes = mapaPlanDao.mapPlanes("'V','I','A'");

		modelo.addAttribute("SubMenu", "9");
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("carreraNombre", carreraNombre);		
		
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("mapaReportes", mapaReportes);
		modelo.addAttribute("mapaLugar", mapaLugar);
		modelo.addAttribute("mapaJuez", mapaJuez);
		modelo.addAttribute("lisAlumno", lisAlumno);

		return "portales/alumno/disciplina";
	}

	@RequestMapping("/portales/alumno/documentos")
	public String portalesAlumnoDocumentos(HttpServletRequest request, Model modelo) {

		String planId = request.getParameter("PlanId") == null ? "0" : request.getParameter("PlanId").trim();
		String grupoId = request.getParameter("GrupoId") == null ? "0" : request.getParameter("GrupoId");

		String codigoAlumno 	= "0";
		String codigoPersonal 	= "0";
		String nombreAlumno 	= "-";
		String autorizaAlumno 	= "No Autorizado";

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			codigoAlumno 		= (String) sesion.getAttribute("codigoAlumno");
			codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");
			nombreAlumno 		= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
			if (planId.equals("0")) {
				planId 			= alumPlanDao.getPlanActual(codigoAlumno);
			}
			autorizaAlumno		= archivoDao.autorizaAlumno(codigoAlumno, planId);
		}
		
		List<TitAlumno> lisAlumno = titAlumnoDao.lisAlumno(codigoAlumno, "");
		List<String> lisPlanesAlumno = alumPlanDao.getPlanesAlumno(codigoAlumno);
		if (planId.equals("0") && lisPlanesAlumno.size() > 1) {
			planId = lisPlanesAlumno.get(0);
		}

		List<String> lisPlanes 						= alumPlanDao.getPlanesAlumno(codigoAlumno);
		List<String> gruposDelPlan 					= archGrupoPlanDao.gruposDelPlan(planId);
		List<ArchDocStatus> lisValidos 				= archDocStatusDao.lisStatusValidos(planId, " ORDER BY IDDOCUMENTO, IDSTATUS");

		List<CargaAlumno> lisPlanesActivos			= cargaAlumnoDao.lisCargasActivas(codigoAlumno);
		List<ArchDocAlum> lisDocumentos 			= archDocAlumDao.getListOne(codigoAlumno," ORDER BY ORDEN_DOCUMENTO(IDDOCUMENTO)");
		List<ArchGrupoDocumento> lisPorGrupo 		= archGrupoDocumentoDao.lisPorGrupo(grupoId, "");
		List<ArchPermisos> listaPermisos 			= archPermisosDao.getListThis(codigoAlumno," ORDER BY ENOC.ARCH_PERMISOS.FECHA_LIM DESC");

		HashMap<String, ArchGrupo> mapaGrupos 		= archGrupoDao.mapaArchGrupo();
		HashMap<String, String> mapaDocumentos 		= archDocumentosDao.mapaTodos();
		HashMap<String, String> mapaStatus 			= archStatusDao.mapaStatus();
		HashMap<String, String> mapaUbicacion 		= archUbicacionDao.mapaUbicacion();
		HashMap<String, MapaPlan> mapaPlanes 		= mapaPlanDao.mapPlanes("'V','I','A'");
		HashMap<String, String> mapaNumDocumentos 	= posArchDocAlumDao.mapaNumDocumentos(codigoAlumno);
		HashMap<String, ArchDocStatus> mapaValidos 	= archDocStatusDao.mapaEstadosValidos();
		HashMap<String, ArchDocAlum> mapaDocAlumno 	= archDocAlumDao.mapArchDocAlumno(codigoAlumno);
		HashMap<String, ArchDocAlum> mapaBuscaDocumento = archDocAlumDao.mapaBuscaPorDocumento(codigoAlumno);

		HashMap<String, Boolean> mapaDocCompletos = new HashMap<String, Boolean>();

		for (String grupo : gruposDelPlan) {
			List<ArchGrupoDocumento> lisDoc = archGrupoDocumentoDao.lisPorGrupo(grupo, "");
			int cont = 0;
			for (ArchGrupoDocumento doc : lisDoc) {
				if (archDocAlumDao.existeReg(codigoAlumno, doc.getDocumentoId())) {
					ArchDocAlum docAlumno = archDocAlumDao.mapeaRegId(codigoAlumno, doc.getDocumentoId());
					if (mapaValidos.containsKey(docAlumno.getIdDocumento() + "-" + docAlumno.getIdStatus())) {
						cont++;
					}
				}
			}
			if (lisDoc.size() == cont)
				mapaDocCompletos.put(grupo, true);
			else
				mapaDocCompletos.put(grupo, false);
		}

		modelo.addAttribute("SubMenu", "6");
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("autorizaAlumno", autorizaAlumno);		
		modelo.addAttribute("gruposDelPlan", gruposDelPlan);
		
		modelo.addAttribute("lisPlanesActivos", lisPlanesActivos);
		modelo.addAttribute("lisPlanesAlumno", lisPlanesAlumno);
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("lisDocumentos", lisDocumentos);
		modelo.addAttribute("lisAlumno", lisAlumno);
		modelo.addAttribute("listaPermisos", listaPermisos);
		modelo.addAttribute("lisPorGrupo", lisPorGrupo);
		modelo.addAttribute("lisValidos", lisValidos);
		
		modelo.addAttribute("mapaGrupos", mapaGrupos);
		modelo.addAttribute("mapaDocumentos", mapaDocumentos);
		modelo.addAttribute("mapaStatus", mapaStatus);
		modelo.addAttribute("mapaUbicacion", mapaUbicacion);
		modelo.addAttribute("mapaNumDocumentos", mapaNumDocumentos);		
		modelo.addAttribute("mapaDocCompletos", mapaDocCompletos);		
		modelo.addAttribute("mapaDocAlumno", mapaDocAlumno);
		modelo.addAttribute("mapaBuscaDocumento", mapaBuscaDocumento);

		return "portales/alumno/documentos";
	}

	@RequestMapping("/portales/alumno/listaPermisosAlumno")
	public String portalesAlumnoListaPermisosAlumno(HttpServletRequest request, Model modelo) {
		String matricula = "0";
		String nombreAlumno = "-";

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
			nombreAlumno = alumPersonalDao.getNombreAlumno(matricula, "NOMBRE");
		}

		List<ArchPermisos> listaPermisos = archPermisosDao.getListThis(matricula," ORDER BY ENOC.ARCH_PERMISOS.FECHA_LIM DESC");

		modelo.addAttribute("SubMenu", "6");
		modelo.addAttribute("listaPermisos", listaPermisos);
		modelo.addAttribute("nombreAlumno", nombreAlumno);

		return "portales/alumno/listaPermisosAlumno";
	}

	@RequestMapping("/portales/alumno/cumple")
	public String portalesAlumnoCumple(HttpServletRequest request, Model modelo) {

		String matricula 		= "0";
		String codigoPersonal 	= "0";
		HttpSession sesion 	= request.getSession();
		if (sesion != null) {
			matricula 		= (String) sesion.getAttribute("codigoAlumno");
			codigoPersonal 	= (String) sesion.getAttribute("codigoPersonal");
		}
		
		// Lista de inscritos
		List<Inscritos> lisInscritos 					= inscritosDao.getListCumpleSem("ORDER BY F_NACIMIENTO, CARRERA_ID, APELLIDO_PATERNO");
		HashMap<String, CatFacultad> mapaFacultades 	= catFacultadDao.getMapFacultad("");
		HashMap<String, CatCarrera> mapaCarreras 		= catCarreraDao.getMapAll("");

		modelo.addAttribute("SubMenu", "11");
		
		modelo.addAttribute("lisInscritos", lisInscritos);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);

		return "portales/alumno/cumple";
	}

	@RequestMapping("/portales/alumno/cumplemov")
	public String portalesAlumnoCumpleMov(HttpServletRequest request, Model modelo) {

		String matricula = "0";
		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
		}

		// Lista de inscritos
		List<Inscritos> lisInscritos = inscritosDao
				.getListCumpleSem("ORDER BY F_NACIMIENTO, CARRERA_ID, APELLIDO_PATERNO");
		HashMap<String, CatFacultad> mapaFacultades = catFacultadDao.getMapFacultad("");
		HashMap<String, CatCarrera> mapaCarreras = catCarreraDao.getMapAll("");

		modelo.addAttribute("SubMenu", "11");
		modelo.addAttribute("lisInscritos", lisInscritos);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);

		return "portales/alumno/cumplemov";
	}

	@RequestMapping("/portales/alumno/tramites")
	public String portalesAlumnoTramites(HttpServletRequest request, Model modelo) {

		String folio = request.getParameter("Folio") == null ? "0" : request.getParameter("Folio");
		String codigoAlumno 	= "0";
		String codigoPersonal 	= "0";

		HttpSession sesion 	= request.getSession();
		if (sesion != null) {
			codigoPersonal 	= (String) sesion.getAttribute("codigoPersonal");
			codigoAlumno 	= (String) sesion.getAttribute("codigoAlumno");
		}
		
		String nombreAlumno = alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
		String hoy = aca.util.Fecha.getHoy();

		// Lisata de tramites del alumno
		List<BitTramiteAlumno> lisTramites 		= bitTramiteAlumnoDao.lisTramiteOrigen(codigoAlumno," ORDER BY ENOC.BIT_TRAMITE_ALUMNO.FECHA_INICIO");
		List<BitSolicitud> lisSolicitudes 		= bitSolicitudDao.lisSolicitudes(codigoAlumno, " ORDER BY FECHA");

		// Mapa del catalogo de tramites
		HashMap<String, BitTramite> mapaTramite = bitTramiteDao.mapTramites();
		// Mapa del catalogo de estados
		HashMap<String, String> mapaEstado 						= bitEstadoDao.mapEstados();		
		HashMap<String, String> mapaHijos 						= bitTramiteAlumnoDao.mapCuentaHijos("1,2,3");
		HashMap<String, String> mapaEtiquetas 					= bitEtiquetaDao.mapaCuentaEtiquetas(codigoAlumno);
		HashMap<String, String> mapaAreas 						= bitAreaDao.mapaAreas();
		HashMap<String, String> mapaRequisitosEnTramite 		= bitTramiteRequisitoDao.mapaRequisitosEnTramite();
		HashMap<String, String> mapaBitRequisitosCumpleAlumno 	= bitRequisitoAlumnoDao.mapaBitRequisitosCumpleAlumno(codigoAlumno, "A");
		HashMap<String, String> mapaBitSolicitudPorAlumno 		= bitSolicitudDao.mapaBitSolicitudPorAlumno(codigoAlumno);

		modelo.addAttribute("SubMenu", "7");
		modelo.addAttribute("matricula", codigoAlumno);
		modelo.addAttribute("nombreAlumno", nombreAlumno);		
		modelo.addAttribute("hoy", hoy);		
		
		modelo.addAttribute("lisTramites", lisTramites);
		modelo.addAttribute("lisSolicitudes", lisSolicitudes);
		modelo.addAttribute("mapaTramite", mapaTramite);
		modelo.addAttribute("mapaEstado", mapaEstado);
		modelo.addAttribute("mapaHijos", mapaHijos);
		modelo.addAttribute("mapaEtiquetas", mapaEtiquetas);
		modelo.addAttribute("mapaAreas", mapaAreas);
		modelo.addAttribute("mapaRequisitosEnTramite", mapaRequisitosEnTramite);
		modelo.addAttribute("mapaBitRequisitosCumpleAlumno", mapaBitRequisitosCumpleAlumno);
		modelo.addAttribute("mapaBitSolicitudPorAlumno", mapaBitSolicitudPorAlumno);

		return "portales/alumno/tramites";
	}

	@RequestMapping("/portales/alumno/verRequisitos")
	public String portalesAlumnoVerRequisitos(HttpServletRequest request, Model modelo) {
		String tramiteId = request.getParameter("TramiteId") == null ? "0" : request.getParameter("TramiteId");
		String folio = request.getParameter("Folio") == null ? "0" : request.getParameter("Folio");
		String nombreTramite = "";
		String matricula = "0";

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
		}

		BitSolicitud bitSolicitud = bitSolicitudDao.mapeaRegId(matricula, folio);

		String nombreAlumno = alumPersonalDao.getNombreAlumno(matricula, "NOMBRE");

		List<BitTramiteRequisito> lisRequisitos = bitTramiteRequisitoDao.lisTramites(tramiteId,
				" ORDER BY REQUISITO_ID");

		HashMap<String, BitRequisito> mapRequisitos = bitRequisitoDao.mapRequisitos();
		HashMap<String, BitRequisitoAlumno> mapaBitRequisitoAlumno = new HashMap<String, BitRequisitoAlumno>();

		if (bitRequisitoAlumnoDao.existeReg(matricula, tramiteId, folio)) {
			mapaBitRequisitoAlumno = bitRequisitoAlumnoDao.mapaBitRequisitoAlumno(matricula, tramiteId);
		}

		if (bitTramiteDao.existeReg(tramiteId)) {
			nombreTramite = bitTramiteDao.mapeaRegId(tramiteId).getNombre();
		}
		modelo.addAttribute("matricula", matricula);
		modelo.addAttribute("lisRequisitos", lisRequisitos);
		modelo.addAttribute("mapaBitRequisitoAlumno", mapaBitRequisitoAlumno);
		modelo.addAttribute("mapRequisitos", mapRequisitos);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("nombreTramite", nombreTramite);
		modelo.addAttribute("folio", folio);
		modelo.addAttribute("bitSolicitud", bitSolicitud);

		return "portales/alumno/verRequisitos";
	}

	@RequestMapping("/portales/alumno/borrarSolicitud")
	public String portalesAlumnoTramitesBorrarSolicitud(HttpServletRequest request) {

		String folio = request.getParameter("Folio") == null ? "0" : request.getParameter("Folio");
		String matricula = "0";

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
		}

		if (bitSolicitudDao.existeReg(matricula, folio)) {
			bitSolicitudDao.deleteReg(matricula, folio);
		}

		return "redirect:/portales/alumno/tramites";
	}

	@RequestMapping("/portales/alumno/editarSolicitud")
	public String portalesAlumnoTramitesEditarSolicitud(HttpServletRequest request, Model modelo) {

		String folio = request.getParameter("Folio") == null ? "0" : request.getParameter("Folio");
		String mensaje = request.getParameter("Mensaje") == null ? "0" : request.getParameter("Mensaje");
		String tramiteId = request.getParameter("TramiteId") == null ? "0" : request.getParameter("TramiteId");
		String matricula = "0";
		String nombreTra = "";

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
		}

		BitSolicitud solicitud = new BitSolicitud();
		BitTramite tramite = new BitTramite();

		if (bitSolicitudDao.existeReg(matricula, folio)) {
			solicitud = bitSolicitudDao.mapeaRegId(matricula, folio);
		}

		if (bitTramiteDao.existeReg(tramiteId)) {
			tramite = bitTramiteDao.mapeaRegId(tramiteId);
		}

		List<BitTramiteRequisito> lisRequisitosTramite = new ArrayList<BitTramiteRequisito>();

		HashMap<String, BitRequisito> mapRequisitos = bitRequisitoDao.mapRequisitos();

		if (!tramiteId.equals("0")) {
			lisRequisitosTramite = bitTramiteRequisitoDao.lisTramites(tramiteId, " ORDER BY TRAMITE_ID");
		}

		List<BitTramite> lisTramites = bitTramiteDao.lisTramites("WHERE SOL_ALUMNO = 'S' ORDER BY NOMBRE");
		if (!solicitud.getTramiteId().equals("0")) {
			tramiteId = solicitud.getTramiteId();
		}

		if (bitTramiteDao.existeReg(tramiteId)) {
			nombreTra = bitTramiteDao.getNombre(tramiteId);
		}

		modelo.addAttribute("matricula", matricula);
		modelo.addAttribute("lisTramites", lisTramites);
		modelo.addAttribute("solicitud", solicitud);
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("tramiteId", tramiteId);
		modelo.addAttribute("lisRequisitosTramite", lisRequisitosTramite);
		modelo.addAttribute("mapRequisitos", mapRequisitos);
		modelo.addAttribute("nombreTra", nombreTra);
		modelo.addAttribute("tramite", tramite);

		return "portales/alumno/editarSolicitud";
	}

	@RequestMapping("/portales/alumno/grabarSolicitud")
	public String portalesAlumnoTramitesGrabarSolicitud(HttpServletRequest request) {

		String folio = request.getParameter("Folio") == null ? "0" : request.getParameter("Folio");
		String tramiteId = request.getParameter("TramiteId") == null ? "0" : request.getParameter("TramiteId");
		String textoAlumno = request.getParameter("TextoAlumno") == null ? "0" : request.getParameter("TextoAlumno");
		String mensaje = "0";
		String matricula = "0";
		String fechaHoy = aca.util.Fecha.getHoy();

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
		}

		BitSolicitud solicitud = new BitSolicitud();

		if (bitSolicitudDao.existeReg(matricula, folio)) {
			solicitud = bitSolicitudDao.mapeaRegId(matricula, folio);

			solicitud.setTramiteId(tramiteId);
			solicitud.setTextoAlumno(textoAlumno);
			solicitud.setFecha(fechaHoy);

			if (bitSolicitudDao.updateReg(solicitud)) {
				mensaje = "1";
			} else {
				mensaje = "2";
			}
		} else {
			folio = bitSolicitudDao.maximoReg(matricula);

			solicitud.setCodigoPersonal(matricula);
			solicitud.setFolio(folio);
			solicitud.setTramiteId(tramiteId);
			solicitud.setStatus("1");
			solicitud.setTextoAlumno(textoAlumno);
			solicitud.setFecha(fechaHoy);

			if (bitSolicitudDao.insertReg(solicitud)) {
				mensaje = "1";
			} else {
				mensaje = "2";
			}
		}

		return "redirect:/portales/alumno/editarSolicitud?Folio=" + folio + "&Mensaje=" + mensaje + "&TramiteId="
				+ tramiteId;
	}

	@RequestMapping("/portales/alumno/servicio")
	public String portalesAlumnoServicio(HttpServletRequest request, Model modelo) {

		String planId = request.getParameter("PlanId") == null ? "0" : request.getParameter("PlanId");

		String codigoAlumno 	= "0";
		String codigoPersonal	= "0";
		HttpSession sesion 		= request.getSession();
		if (sesion != null) {
			codigoAlumno 		= (String) sesion.getAttribute("codigoAlumno");
			codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");
		}
		
		AlumPersonal alumPersonal 	= alumPersonalDao.mapeaRegId(codigoAlumno);
		AlumPlan alumPlan 			= alumPlanDao.mapeaRegId(codigoAlumno);
		if (planId.equals("0")) planId = alumPlan.getPlanId();
		String promCreditos 		= alumPlanDao.getAlumPromCreditos(codigoAlumno, planId);
		String fechaInicio 			= ssocInicioDao.getFecha(codigoAlumno, planId);

		// Lista de planes del alumno
		List<String> lisPlanes 								= alumPlanDao.getPlanesAlumno(codigoAlumno);
		List<SsocDocAlum> lisDocumentos 					= ssocDocAlumDao.getDocPlan(codigoAlumno, planId);
		List<String> lisFaltantes 							= ssocDocAlumDao.getDocumentosFaltantes(codigoAlumno, planId);
		HashMap<String, MapaPlan> mapaPlanes 				= mapaPlanDao.mapPlanes("'A','V','I'");
		HashMap<String, String> mapaDocumentos 				= ssocDocumentosDao.mapaDocumentos();
		HashMap<String, SsocAsignacion> mapaAsignaciones 	= ssocAsignacionDao.mapaAsignaciones(codigoAlumno);

		modelo.addAttribute("SubMenu", "8");
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("alumPlan", alumPlan);
		modelo.addAttribute("promCreditos", promCreditos);
		modelo.addAttribute("fechaInicio", fechaInicio);
		
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("lisDocumentos", lisDocumentos);
		modelo.addAttribute("lisFaltantes", lisFaltantes);
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		modelo.addAttribute("mapaDocumentos", mapaDocumentos);
		modelo.addAttribute("mapaAsignaciones", mapaAsignaciones);

		return "portales/alumno/servicio";
	}

	@RequestMapping("/portales/alumno/avance")
	public String portalesAlumnoAvance(HttpServletRequest request, Model modelo) {

		String planId 			= request.getParameter("PlanId") == null ? "0" : request.getParameter("PlanId").trim();
		String codigoAlumno 	= "0";
		String institucion 		= "-";
		String codigoPersonal	= "0";

		HttpSession sesion 		= request.getSession();
		if (sesion != null) {
			codigoAlumno 		= (String) sesion.getAttribute("codigoAlumno");
			institucion 		= (String) sesion.getAttribute("institucion");
			codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");
		}
		
		Parametros parametros 	= parametrosDao.mapeaRegId("1");
		String nombreAlumno 	= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
		String carrera 			= catCarreraDao.getNombreCarrera(mapaPlanDao.getCarreraId(planId));
		String modalidad 		= alumAcademicoDao.getModalidad(codigoAlumno);
		String residencia 		= alumAcademicoDao.getResidencia(codigoAlumno);

		List<String> lisPlanes = alumPlanDao.getPlanesAlumno(codigoAlumno);
		if (planId.equals("0") && lisPlanes.size() >= 1) {
			planId = lisPlanes.get(0);
		}
		String documentos 		= archivoDao.autorizaAlumno(codigoAlumno, planId);
		String nombrePlan 		= mapaPlanDao.getNombrePlan(planId);

		List<CatTipoCurso> lisCursos = catTipoCursoDao.lisCursosEnPlan(planId, " ORDER BY TIPOCURSO_ID");

		HashMap<String, MapaPlan> mapaPlanes = mapaPlanDao.mapPlanes("'A','V','I'");
		HashMap<String, String> mapaCredReq = mapaAvanceDao.mapaCreditosPorTipo(planId);
		HashMap<String, String> mapaCredAc = alumnoCursoDao.mapaCredPorTipo(codigoAlumno, planId, "'1'");
		HashMap<String, String> mapaCredInsc = alumnoCursoDao.mapaCredPorTipo(codigoAlumno, planId, "'I'");

		modelo.addAttribute("SubMenu", "10");
		modelo.addAttribute("codigoAlumno", codigoAlumno);
		modelo.addAttribute("institucion", institucion);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("nombrePlan", nombrePlan);
		modelo.addAttribute("carrera", carrera);
		modelo.addAttribute("modalidad", modalidad);
		modelo.addAttribute("residencia", residencia);
		modelo.addAttribute("documentos", documentos);
		modelo.addAttribute("parametros", parametros);
		
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("lisCursos", lisCursos);
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		modelo.addAttribute("mapaCredReq", mapaCredReq);
		modelo.addAttribute("mapaCredAc", mapaCredAc);
		modelo.addAttribute("mapaCredInsc", mapaCredInsc);
		modelo.addAttribute("pagare", fesCcPagareDetDao.getPagareNoVencido(codigoAlumno));
		modelo.addAttribute("saldo", contMovimientoDao.getSaldo(codigoAlumno));
		modelo.addAttribute("compPlan", mapaCursoDao.cuentaMaterias(planId, "3"));
		modelo.addAttribute("compAlumno", alumnoCursoDao.materiasAlumno(codigoAlumno, planId, "3"));
		modelo.addAttribute("avance", alumPlanDao.getAvanceId(codigoAlumno, planId));
		modelo.addAttribute("avan", catAvanceDao.mapeaRegId(alumPlanDao.getAvanceId(codigoAlumno, planId)));

		return "portales/alumno/avance";
	}

	@RequestMapping("/portales/alumno/materias")
	public String portalesAlumnoMaterias(HttpServletRequest request, Model modelo) {

		String cursoCargaId = request.getParameter("CursoCargaId") == null ? "0" : request.getParameter("CursoCargaId");
		String cargaId = request.getParameter("CargaId") == null ? "0" : request.getParameter("CargaId");
		String bloqueId = request.getParameter("BloqueId") == null ? "0" : request.getParameter("BloqueId");
		String cargaSesion = "0";
		String bloqueSesion = "0";
		String maestrosAlumno = "'0'";

		String matricula = "0";
		String planId = "";
		String carreraNombre = "";
		String mod = "";
		String fInicio = "";
		String codigoPersonal = "0";

		boolean estudiosCancelados = false;

		AlumPersonal alumno = new AlumPersonal();
		AlumAcademico academico = new AlumAcademico();
		AlumPlan plan = new AlumPlan();
		CancelaEstudio cancelaEstudio = new CancelaEstudio();

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
			codigoPersonal 		= sesion.getAttribute("codigoPersonal") == null ?"-":(String) sesion.getAttribute("codigoPersonal");
			cargaSesion = (String) sesion.getAttribute("cargaId");
			bloqueSesion = (String) sesion.getAttribute("bloqueId");
			alumno = alumPersonalDao.mapeaRegId(matricula);
			academico = alumAcademicoDao.mapeaRegId(matricula);
			mod = alumAcademicoDao.getModalidadId(matricula);
		}
		
		// Actualizar los puntos ganados para calcular la eficiencia
		krdxAlumnoEvalDao.updatePuntosActividades(matricula, cargaId);
		krdxAlumnoEvalDao.updatePuntosSinActividades(matricula, cargaId);

		List<Carga> lisCarga = cargaDao.getListAlumno(matricula);

		boolean existeCarga = false;
		for (Carga car : lisCarga) {
			if (car.getCargaId().equals(cargaSesion)) {
				existeCarga = true;
				break;
			}
		}

		if (cargaId.equals("0") && existeCarga) {
			cargaId = cargaSesion;
		} else if (cargaId.equals("0") && lisCarga.size() >= 1) {
			cargaId = lisCarga.get(0).getCargaId();
			sesion.setAttribute("cargaId", cargaId);
		} else if (!cargaId.equals("0") && sesion != null) {
			sesion.setAttribute("cargaId", cargaId);
		}

		fInicio = cargaDao.getFInicio(cargaId);

		List<CargaBloque> lisBloques = cargaBloqueDao.getListaAlumno(cargaId, matricula, " ORDER BY BLOQUE_ID");
		boolean existeBloque = false;
		for (CargaBloque bloq : lisBloques) {
			if (bloq.getBloqueId().equals(bloqueSesion)) {
				existeBloque = true;
				break;
			}
		}

		if (bloqueId.equals("0") && existeBloque) {
			bloqueId = bloqueSesion;
		} else if (bloqueId.equals("0") && lisBloques.size() >= 1) {
			bloqueId = lisBloques.get(0).getBloqueId();
			sesion.setAttribute("bloqueId", bloqueId);
		} else {
			sesion.setAttribute("bloqueId", bloqueId);
		}

		Estadistica estadistica = new Estadistica();
		estadistica = estadisticaDao.mapeaReg(matricula, cargaId, bloqueId);

		AlumAcademico alum = new AlumAcademico();
		alum = alumAcademicoDao.mapeaRegId(matricula);

		// codigos de los maestros del alumno
		maestrosAlumno = alumnoCursoDao.getMaestrosAlumno(matricula, cargaId);

		List<CargaGrupoProgramacion> lisProgra = cargaGrupoProgramacionDao.getListMateria(cursoCargaId,
				" ORDER BY ORDEN, TO_CHAR(ENOC.CARGA_GRUPO_PROGRAMACION.FECHA,'YYYY/MM/DD')");
		List<AlumnoCurso> lisAlumnos = alumnoCursoDao.getListCurso(cursoCargaId,
				" ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");
		List<String> materiasElegibles = mapaCursoElectivaDao.getMaterias(" ORDER BY 1");
		List<Hora> lisHoras = horaDao.listaHorasDelAlumno(matricula, cargaId, bloqueId);

		List<AlumnoCurso> lisCursos = alumnoCursoDao.getListAlumnoCargaBloque(matricula, cargaId, bloqueId,
				" ORDER BY CICLO, NOMBRE_CURSO");
		// System.out.println("Datos:"+matricula+":"+cargaId+":"+bloqueId+":"+lisCursos.size());
		List<Edo> lisEdoActual = edoDao.lisEdoActual("E", "ORDER BY EDO_ID");

		planId = cargaAlumnoDao.getPlanId(matricula, cargaId, bloqueId);
		carreraNombre = catCarreraDao.getNombreCarrera(mapaPlanDao.getCarreraId(planId));
		plan = alumPlanDao.mapeaRegId(matricula, planId);

		if (lisCursos.size() > 0) {
			plan = alumPlanDao.mapeaRegId(matricula, lisCursos.get(0).getPlanId());
		}

		// Revisa si debe bloquear acceso a esa carga
		Carga carga = cargaDao.mapeaRegId(cargaId);
		// System.out.println(carga.getCargaId());
		String fechaHoy = aca.util.Fecha.getHoy(); System.out.println(fechaHoy);
		String fechaExtra = carga.getFExtra(); System.out.println(fechaExtra);
		String fechaFinal = carga.getFFinal(); System.out.println(fechaFinal);
		boolean bloquear = false;
		if(aca.util.Fecha.getFechaBetweenFecha(fechaExtra, fechaFinal, fechaHoy)){
			bloquear = true;
		}else if(carga.getBloqueo().equals("1")){
			bloquear = true;
		}

		HashMap<String, String> mapAsistencia = cargaGrupoAsistenciaDao.mapAsistenciaClase(cursoCargaId);
		HashMap<String, String> mapAsistenciaTotal = cargaGrupoAsistenciaDao.mapAsistenciaTotal(cursoCargaId);
		HashMap<String, String> mapMateriasOrigen = cargaGrupoCursoDao.mapMateriasOrigenAlumno(matricula);
		HashMap<String, String> mapMaestroNombre = maestrosDao.mapMaestrosDeCodigo(maestrosAlumno, "NOMBRE");
		HashMap<String, String> mapTipoCurso = alumnoCursoDao.mapTipoCursoAlumno(matricula);
		HashMap<String, String> mapTipoCal = catTipoCalDao.mapTipoCal();
		HashMap<String, MapaCurso> mapCursos = mapaCursoDao.mapaCursosDelAlumno(matricula);
		HashMap<String, CargaGrupo> mapCargaGrupo = cargaGrupoDao.mapCargaGrupo(cargaId);
		HashMap<String, Edo> mapAllEdo = edoDao.mapAllEdo(" ORDER BY EDO_ID");
		HashMap<String, String> mapCarreraNombre = catCarreraDao.getMapNombre();
		HashMap<String, String> mapTieneGrupo = apFisicaGrupoDao.mapTieneGrupo();
		HashMap<String, String> mapaGruposDelAlumno = apFisicaAlumnoDao.mapaGruposDelAlumno(matricula);
		HashMap<String, String> mapGrupoActivoTodos = apFisicaGrupoDao.mapGrupoActivo("T");
		HashMap<String, String> mapGrupoActivoNuevos = apFisicaGrupoDao.mapGrupoActivo("N");
		HashMap<String, String> mapPuntosEvaluados = alumnoEficienciaDao.mapaPuntosEvaluados(matricula);
		HashMap<String, String> mapAvanceEvaluaciones = alumnoEficienciaDao.mapaAvanceEvaluacion(matricula);
		HashMap<String, String> mapPuntosGanados = alumnoEvaluacionDao.mapaPuntosAlumno(matricula, cargaId,
				"'%','P','E'");
		HashMap<String, String> mapContestadas = edoAlumnoRespDao.mapaContestadas(matricula);
		HashMap<String, String> mapCargasAlumno = musiAutorizaDao.mapCargasAlumno(matricula);
		ComAutorizacion comAutorizacion = comAutorizacionDao.mapeaRegId(matricula, cargaId, bloqueId);
		HashMap<String, String> mapaLetrasDeNotas		= catGradePointDao.mapaLetrasDeNotas(" ORDER BY INICIO");

		estudiosCancelados = cancelaEstudioDao.existeRegId(matricula, planId);
		if (estudiosCancelados) {
			cancelaEstudio = cancelaEstudioDao.mapeaRegId(matricula, planId);
		}

		modelo.addAttribute("SubMenu", "3");
		modelo.addAttribute("alumno", alumno);
		modelo.addAttribute("academico", academico);
		modelo.addAttribute("estadistica", estadistica);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("bloquear", bloquear);
		modelo.addAttribute("carreraNombre", carreraNombre);
		modelo.addAttribute("mod", mod);
		modelo.addAttribute("fInicio", fInicio);
		modelo.addAttribute("estudiosCancelados", estudiosCancelados);
		modelo.addAttribute("plan", plan);
		modelo.addAttribute("cancelaEstudio", cancelaEstudio);
		modelo.addAttribute("lisProgra", lisProgra);
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("lisEdoActual", lisEdoActual);
		modelo.addAttribute("materiasElegibles", materiasElegibles);
		modelo.addAttribute("lisCarga", lisCarga);
		modelo.addAttribute("lisBloques", lisBloques);
		modelo.addAttribute("lisCursos", lisCursos);
		modelo.addAttribute("lisHoras", lisHoras);
		modelo.addAttribute("mapAsistencia", mapAsistencia);
		modelo.addAttribute("mapAsistenciaTotal", mapAsistenciaTotal);
		modelo.addAttribute("mapMateriasOrigen", mapMateriasOrigen);
		modelo.addAttribute("mapMaestroNombre", mapMaestroNombre);
		modelo.addAttribute("mapTipoCurso", mapTipoCurso);
		modelo.addAttribute("mapTipoCal", mapTipoCal);
		modelo.addAttribute("mapCursos", mapCursos);
		modelo.addAttribute("mapCargaGrupo", mapCargaGrupo);
		modelo.addAttribute("mapAllEdo", mapAllEdo);
		modelo.addAttribute("mapCarreraNombre", mapCarreraNombre);
		modelo.addAttribute("mapTieneGrupo", mapTieneGrupo);
		modelo.addAttribute("mapaGruposDelAlumno", mapaGruposDelAlumno);
		modelo.addAttribute("mapGrupoActivoTodos", mapGrupoActivoTodos);
		modelo.addAttribute("mapGrupoActivoNuevos", mapGrupoActivoNuevos);
		modelo.addAttribute("mapPuntosEvaluados", mapPuntosEvaluados);
		modelo.addAttribute("mapPuntosGanados", mapPuntosGanados);
		modelo.addAttribute("mapContestadas", mapContestadas);
		modelo.addAttribute("mapCargasAlumno", mapCargasAlumno);
		modelo.addAttribute("mapaLetrasDeNotas", mapaLetrasDeNotas);
		modelo.addAttribute("comAutorizacion", comAutorizacion);
		modelo.addAttribute("mapAvanceEvaluaciones", mapAvanceEvaluaciones);
		modelo.addAttribute("alum", alum);
		
		return "portales/alumno/materias";
	}

	@RequestMapping("/portales/alumno/materiasmov")
	public String portalesAlumnoMateriasMov

	(HttpServletRequest request, Model modelo) {

		// long inicio = System.currentTimeMillis();
		// System.out.println("Paso 5:"+(System.currentTimeMillis()-inicio));
		String cursoCargaId = request.getParameter("CursoCargaId") == null ? "0" : request.getParameter("CursoCargaId");
		String cargaId = request.getParameter("CargaId") == null ? "0" : request.getParameter("CargaId");
		String bloqueId = request.getParameter("BloqueId") == null ? "0" : request.getParameter("BloqueId");
		String cargaSesion = "0";
		String bloqueSesion = "0";
		String maestrosAlumno = "'0'";

		String matricula = "0";
		String planId = "";
		String carreraNombre = "";
		String mod = "";
		String fInicio = "";

		boolean estudiosCancelados = false;

		AlumPersonal alumno = new AlumPersonal();
		AlumAcademico academico = new AlumAcademico();
		AlumPlan plan = new AlumPlan();
		CancelaEstudio cancelaEstudio = new CancelaEstudio();

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
			cargaSesion = (String) sesion.getAttribute("cargaId");
			bloqueSesion = (String) sesion.getAttribute("bloqueId");
			alumno = alumPersonalDao.mapeaRegId(matricula);
			academico = alumAcademicoDao.mapeaRegId(matricula);
			mod = alumAcademicoDao.getModalidadId(matricula);
		}

		// Actualizar los puntos ganados para calcular la eficiencia
		krdxAlumnoEvalDao.updatePuntosActividades(matricula, cargaId);
		krdxAlumnoEvalDao.updatePuntosSinActividades(matricula, cargaId);

		List<Carga> lisCarga = cargaDao.getListAlumno(matricula);

		boolean existeCarga = false;
		for (Carga car : lisCarga) {
			if (car.getCargaId().equals(cargaSesion)) {
				existeCarga = true;
				break;
			}
		}

		if (cargaId.equals("0") && existeCarga) {
			cargaId = cargaSesion;
		} else if (cargaId.equals("0") && lisCarga.size() >= 1) {
			cargaId = lisCarga.get(0).getCargaId();
			sesion.setAttribute("cargaId", cargaId);
		} else if (!cargaId.equals("0") && sesion != null) {
			sesion.setAttribute("cargaId", cargaId);
		}

		fInicio = cargaDao.getFInicio(cargaId);

		List<CargaBloque> lisBloques = cargaBloqueDao.getListaAlumno(cargaId, matricula, " ORDER BY BLOQUE_ID");
		boolean existeBloque = false;
		for (CargaBloque bloq : lisBloques) {
			if (bloq.getBloqueId().equals(bloqueSesion)) {
				existeBloque = true;
				break;
			}
		}

		if (bloqueId.equals("0") && existeBloque) {
			bloqueId = bloqueSesion;
		} else if (bloqueId.equals("0") && lisBloques.size() >= 1) {
			bloqueId = lisBloques.get(0).getBloqueId();
			sesion.setAttribute("bloqueId", bloqueId);
		} else {
			sesion.setAttribute("bloqueId", bloqueId);
		}

		Estadistica estadistica = new Estadistica();
		estadistica = estadisticaDao.mapeaReg(matricula, cargaId, bloqueId);

		// codigos de los maestros del alumno
		maestrosAlumno = alumnoCursoDao.getMaestrosAlumno(matricula, cargaId);

		List<CargaGrupoProgramacion> lisProgra = cargaGrupoProgramacionDao.getListMateria(cursoCargaId,
				" ORDER BY ORDEN, TO_CHAR(ENOC.CARGA_GRUPO_PROGRAMACION.FECHA,'YYYY/MM/DD')");
		List<AlumnoCurso> lisAlumnos = alumnoCursoDao.getListCurso(cursoCargaId,
				" ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");
		List<String> materiasElegibles = mapaCursoElectivaDao.getMaterias(" ORDER BY 1");

		List<AlumnoCurso> lisCursos = alumnoCursoDao.getListAlumnoCargaBloque(matricula, cargaId, bloqueId,
				" ORDER BY CICLO, NOMBRE_CURSO");
		List<Edo> lisEdoActual = edoDao.lisEdoActual("E", "ORDER BY EDO_ID");

		planId = cargaAlumnoDao.getPlanId(matricula, cargaId, bloqueId);
		carreraNombre = catCarreraDao.getNombreCarrera(mapaPlanDao.getCarreraId(planId));
		plan = alumPlanDao.mapeaRegId(matricula, planId);

		if (lisCursos.size() > 0) {
			plan = alumPlanDao.mapeaRegId(matricula, lisCursos.get(0).getPlanId());
		}

		HashMap<String, String> mapAsistencia = cargaGrupoAsistenciaDao.mapAsistenciaClase(cursoCargaId);
		HashMap<String, String> mapAsistenciaTotal = cargaGrupoAsistenciaDao.mapAsistenciaTotal(cursoCargaId);
		HashMap<String, String> mapMateriasOrigen = cargaGrupoCursoDao.mapMateriasOrigenAlumno(matricula);
		HashMap<String, String> mapMaestroNombre = maestrosDao.mapMaestrosDeCodigo(maestrosAlumno, "NOMBRE");
		HashMap<String, String> mapTipoCurso = alumnoCursoDao.mapTipoCursoAlumno(matricula);
		HashMap<String, String> mapTipoCal = catTipoCalDao.mapTipoCalCorto();
		HashMap<String, MapaCurso> mapCursos = mapaCursoDao.mapaCursosDelAlumno(matricula);
		HashMap<String, CargaGrupo> mapCargaGrupo = cargaGrupoDao.mapCargaGrupo(cargaId);
		HashMap<String, Edo> mapAllEdo = edoDao.mapAllEdo(" ORDER BY EDO_ID");
		HashMap<String, String> mapCarreraNombre = catCarreraDao.getMapNombre();
		HashMap<String, String> mapTieneGrupo = apFisicaGrupoDao.mapTieneGrupo();
		HashMap<String, String> mapaGruposDelAlumno = apFisicaAlumnoDao.mapaGruposDelAlumno(matricula);
		HashMap<String, String> mapGrupoActivoTodos = apFisicaGrupoDao.mapGrupoActivo("T");
		HashMap<String, String> mapGrupoActivoNuevos = apFisicaGrupoDao.mapGrupoActivo("N");
		HashMap<String, String> mapPuntosEvaluados = alumnoEficienciaDao.mapaPuntosEvaluados(matricula);
		HashMap<String, String> mapAvanceEvaluaciones = alumnoEficienciaDao.mapaAvanceEvaluacion(matricula);
		HashMap<String, String> mapPuntosGanados = alumnoEvaluacionDao.mapaPuntosAlumno(matricula, cargaId,
				"'%','P','E'");
		HashMap<String, String> mapContestadas = edoAlumnoRespDao.mapaContestadas(matricula);
		HashMap<String, String> mapCargasAlumno = musiAutorizaDao.mapCargasAlumno(matricula);
		ComAutorizacion comAutorizacion = comAutorizacionDao.mapeaRegId(matricula, cargaId, bloqueId);

		estudiosCancelados = cancelaEstudioDao.existeRegId(matricula, planId);
		if (estudiosCancelados) {
			cancelaEstudio = cancelaEstudioDao.mapeaRegId(matricula, planId);
		}

		modelo.addAttribute("SubMenu", "3");
		modelo.addAttribute("alumno", alumno);
		modelo.addAttribute("academico", academico);
		modelo.addAttribute("estadistica", estadistica);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("carreraNombre", carreraNombre);
		modelo.addAttribute("mod", mod);
		modelo.addAttribute("fInicio", fInicio);
		modelo.addAttribute("estudiosCancelados", estudiosCancelados);
		modelo.addAttribute("plan", plan);
		modelo.addAttribute("cancelaEstudio", cancelaEstudio);
		modelo.addAttribute("lisProgra", lisProgra);
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("lisEdoActual", lisEdoActual);
		modelo.addAttribute("materiasElegibles", materiasElegibles);
		modelo.addAttribute("lisCarga", lisCarga);
		modelo.addAttribute("lisBloques", lisBloques);
		modelo.addAttribute("lisCursos", lisCursos);
		modelo.addAttribute("mapAsistencia", mapAsistencia);
		modelo.addAttribute("mapAsistenciaTotal", mapAsistenciaTotal);
		modelo.addAttribute("mapMateriasOrigen", mapMateriasOrigen);
		modelo.addAttribute("mapMaestroNombre", mapMaestroNombre);
		modelo.addAttribute("mapTipoCurso", mapTipoCurso);
		modelo.addAttribute("mapTipoCal", mapTipoCal);
		modelo.addAttribute("mapCursos", mapCursos);
		modelo.addAttribute("mapCargaGrupo", mapCargaGrupo);
		modelo.addAttribute("mapAllEdo", mapAllEdo);
		modelo.addAttribute("mapCarreraNombre", mapCarreraNombre);
		modelo.addAttribute("mapTieneGrupo", mapTieneGrupo);
		modelo.addAttribute("mapaGruposDelAlumno", mapaGruposDelAlumno);
		modelo.addAttribute("mapGrupoActivoTodos", mapGrupoActivoTodos);
		modelo.addAttribute("mapGrupoActivoNuevos", mapGrupoActivoNuevos);
		modelo.addAttribute("mapPuntosEvaluados", mapPuntosEvaluados);
		modelo.addAttribute("mapPuntosGanados", mapPuntosGanados);
		modelo.addAttribute("mapContestadas", mapContestadas);
		modelo.addAttribute("mapCargasAlumno", mapCargasAlumno);
		modelo.addAttribute("comAutorizacion", comAutorizacion);
		modelo.addAttribute("mapAvanceEvaluaciones", mapAvanceEvaluaciones);

		return "portales/alumno/materiasmov";
	}

	@RequestMapping("/portales/alumno/subirArchivo")
	public String portalesAlumnoSubirArchivo(HttpServletRequest request, Model modelo) {
		String cursoCargaId = request.getParameter("CursoCargaId") == null ? "0" : request.getParameter("CursoCargaId");
		String evaluacion = request.getParameter("Evaluacion") == null ? "0" : request.getParameter("Evaluacion");
		String actividad = request.getParameter("Actividad") == null ? "0" : request.getParameter("Actividad");
		String enviar = request.getParameter("Enviar") == null ? "N" : request.getParameter("Enviar");
		String mensaje = request.getParameter("Mensaje") == null ? "" : request.getParameter("Mensaje");

		modelo.addAttribute("cursoCargaId", cursoCargaId);
		modelo.addAttribute("evaluacion", evaluacion);
		modelo.addAttribute("actividad", actividad);
		modelo.addAttribute("enviar", enviar);
		modelo.addAttribute("mensaje", mensaje);

		return "portales/alumno/subirArchivo";
	}

	@RequestMapping("/portales/alumno/grabarArchivo")
	public String portalesAlumnoGrabarArchivo(HttpServletRequest request, Model modelo,
			@RequestParam("archivo") MultipartFile archivo) {

		String cursoCargaId = request.getParameter("CursoCargaId") == null ? "0" : request.getParameter("CursoCargaId");
		String codigoPersonal = "0";
		String evaluacion = request.getParameter("Evaluacion") == null ? "0" : request.getParameter("Evaluacion");
		String actividad = request.getParameter("Actividad") == null ? "0" : request.getParameter("Actividad");
		String comentario = request.getParameter("comentario") == null ? "-" : request.getParameter("comentario");
		String enviar = request.getParameter("Enviar") == null ? "N" : request.getParameter("Enviar");
		String mensaje = "-";
		
		HttpSession sesion = request.getSession();
		if (sesion != null) {
			codigoPersonal = (String) sesion.getAttribute("codigoAlumno");
		}

		try {
			byte[] file = archivo.getBytes();

			ArchivosAlumno grabaArchivo = new ArchivosAlumno();

			String nombreArchivo = archivo.getOriginalFilename();

			if (!evaluacion.equals("0") & actividad.equals("0")) {
				grabaArchivo.setArchivoId(cursoCargaId + "-" + evaluacion);
				grabaArchivo.setActividadId(actividad);
			} else if (evaluacion.equals("0") & !actividad.equals("0")) {
				grabaArchivo.setArchivoId(cursoCargaId);
				grabaArchivo.setActividadId(actividad);
			}else {
				grabaArchivo.setArchivoId(cursoCargaId);
				grabaArchivo.setActividadId(actividad);
			}

			grabaArchivo.setEvaluacionId(evaluacion);
			grabaArchivo.setFolio(archivosAlumnoDao.folio(codigoPersonal, cursoCargaId));
			grabaArchivo.setCodigoPersonal(codigoPersonal);
			grabaArchivo.setFecha(aca.util.Fecha.getHoy());
			grabaArchivo.setNombre(nombreArchivo);
			grabaArchivo.setComentario(comentario);
			grabaArchivo.setArchivoNuevo(file);
			grabaArchivo.setTamano(null);
			grabaArchivo.setStatus("E");
			grabaArchivo.setArchivo(0);


			if (archivosAlumnoDao.insertReg(grabaArchivo)) {
				mensaje = "File sent";
			} else {
				mensaje = "Error sending file";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/portales/alumno/subirArchivo?CursoCargaId=" + cursoCargaId + "&Mensaje=" + mensaje
				+ "&Enviar=" + enviar;
	}

	@RequestMapping("/portales/alumno/grabarComentario")
	public String portalesAlumnoGrabarComentario(HttpServletRequest request, Model modelo) {

		String cursoCargaId = request.getParameter("CursoCargaId") == null ? "0" : request.getParameter("CursoCargaId");
		String codigoPersonal = "0";
		String evaluacion = request.getParameter("Evaluacion") == null ? "0" : request.getParameter("Evaluacion");
		String actividad = request.getParameter("Actividad") == null ? "0" : request.getParameter("Actividad");
		String comentario = request.getParameter("comentario") == null ? "-" : request.getParameter("comentario");
		String enviar = request.getParameter("Enviar") == null ? "N" : request.getParameter("Enviar");
		String mensaje = "-";

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			codigoPersonal = (String) sesion.getAttribute("codigoAlumno");
		}

		try {

			ArchivosAlumno grabaArchivo = new ArchivosAlumno();
			
			if(evaluacion.equals("0")) {
				grabaArchivo.setArchivoId(cursoCargaId + "-" + actividad);
			}
			
			grabaArchivo.setArchivoId(cursoCargaId + "-" + evaluacion);
			grabaArchivo.setActividadId(actividad);
			grabaArchivo.setEvaluacionId(evaluacion);
			grabaArchivo.setFolio(archivosAlumnoDao.folio(codigoPersonal, cursoCargaId));
			grabaArchivo.setCodigoPersonal(codigoPersonal);
			grabaArchivo.setFecha(aca.util.Fecha.getHoy());
			grabaArchivo.setNombre("comment");
			grabaArchivo.setComentario(comentario);
			grabaArchivo.setArchivoNuevo(null);
			grabaArchivo.setTamano(null);
			grabaArchivo.setStatus("E");
			grabaArchivo.setArchivo(0);

			if (archivosAlumnoDao.insertReg(grabaArchivo)) {
				mensaje = "Comment saved";
			} else {
				mensaje = "Error saving comment";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/portales/alumno/subirArchivo?CursoCargaId=" + cursoCargaId + "&Mensaje=" + mensaje
				+ "&Enviar=" + enviar;
	}

	@RequestMapping("/portales/alumno/detallecal")
	public String portalesAlumnodetalleCal(HttpServletRequest request, Model modelo) {

		String cursoCargaId = request.getParameter("CursoCargaId") == null ? "0" : request.getParameter("CursoCargaId");
		String cursoId = cargaGrupoCursoDao.cursoIdOrigen(cursoCargaId);
		String materiaNombre = mapaCursoDao.getNombreCurso(cursoId);
		String matricula = "0";
		String alumnoNombre = "-";
		String codigoMaestro = cargaGrupoDao.getCodigoPersonal(cursoCargaId);
		String maestroNombre = maestrosDao.getNombreMaestro(codigoMaestro, "NOMBRE");

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
			alumnoNombre = alumPersonalDao.getNombreAlumno(matricula, "NOMBRE");

		}

		List<CargaGrupoEvaluacion> lisEvaluaciones = cargaGrupoEvaluacionDao.getLista(cursoCargaId,
				" ORDER BY ENOC.CARGA_GRUPO_EVALUACION.FECHA");
		List<CargaGrupoActividad> lisActividades = cargaGrupoActividadDao.getListCurso(cursoCargaId,
				" ORDER BY EVALUACION_ID, ENOC.CARGA_GRUPO_ACTIVIDAD.FECHA");

		HashMap<String, AlumnoEficiencia> mapaEvaluaciones = alumnoEficienciaDao.mapaMateria(cursoCargaId);
		HashMap<String, KrdxAlumnoActiv> mapaActividades = krdxAlumnoActivDao.mapActividadesMateria(cursoCargaId);
		HashMap<String, String> mapPuntosEvaluados = alumnoEficienciaDao.mapaPuntosEvaluados(matricula);
		HashMap<String, String> mapPuntosEvaluaciones = alumnoEvaluacionDao.mapaPuntosEvaluaciones(cursoCargaId,
				"'%','P','E'");
		HashMap<String, String> mapaActividadEnviada = archivosAlumnoDao.mapaArchivosDelAlumno(matricula);
		HashMap<String, String> mapaLetrasDeNotas		= catGradePointDao.mapaLetrasDeNotas(" ORDER BY INICIO");

		modelo.addAttribute("SubMenu", "3");
		modelo.addAttribute("materiaNombre", materiaNombre);
		modelo.addAttribute("maestroNombre", maestroNombre);
		modelo.addAttribute("alumnoNombre", alumnoNombre);
		modelo.addAttribute("lisEvaluaciones", lisEvaluaciones);
		modelo.addAttribute("lisActividades", lisActividades);
		modelo.addAttribute("mapaEvaluaciones", mapaEvaluaciones);
		modelo.addAttribute("mapaActividades", mapaActividades);
		modelo.addAttribute("mapPuntosEvaluados", mapPuntosEvaluados);
		modelo.addAttribute("mapPuntosEvaluaciones", mapPuntosEvaluaciones);
		modelo.addAttribute("mapaActividadEnviada", mapaActividadEnviada);
		modelo.addAttribute("mapaLetrasDeNotas", mapaLetrasDeNotas);

		return "portales/alumno/detallecal";
	}

	@RequestMapping("/portales/alumno/horariocimum")
	public String portalesAlumnoHorarioMusiAlumno(HttpServletRequest request, Model modelo) {

		String cargaId = request.getParameter("CargaId") == null ? "0" : request.getParameter("CargaId");
		String cursoCargaId = request.getParameter("CursoCargaId") == null ? "0" : request.getParameter("CursoCargaId");
		String maestro = request.getParameter("CodigoMaestro") == null ? "0" : request.getParameter("CodigoMaestro");
		String dia = request.getParameter("Dia") == null ? "0" : request.getParameter("Dia");

		String matricula = "0";
		String matriculaAlumno = "0";
		String nombreAlumno = "-";
		String nombreMaestro = "-";
		String materia = "";
		float horasReg = 0;
		float horasMat = 0;

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoPersonal");
			matriculaAlumno = (String) sesion.getAttribute("codigoAlumno");
			nombreAlumno = alumPersonalDao.getNombreAlumno(matriculaAlumno, "NOMBRE");
			nombreMaestro = maestrosDao.getNombreMaestro(maestro, "NOMBRE");
			materia = "X";
			horasMat = krdxCursoActDao.numFrecuenciasMateria(matricula, cursoCargaId);
			horasReg = musiHorarioDao.getHorasReg(matricula, cursoCargaId);
		}

		List<MusiHorario> lisHorarioMaestro = musiHorarioDao.getListHorarioMaestro(maestro, cargaId, dia,
				"ORDER BY 6,2");

		HashMap<String, String> mapaCupos = musiHorarioDao.mapaCupoPorDia(cargaId, maestro);
		HashMap<String, String> mapaDisponibles = musiHorarioDao.mapaDisponibles(cargaId, maestro);
		HashMap<String, String> mapaOcupados = musiHorarioAlumnoDao.mapaOcupados(cargaId);
		HashMap<String, String> mapaOcupadosAlumno = musiHorarioAlumnoDao.mapaOcupadosAlumno(matricula);

		modelo.addAttribute("materia", materia);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("nombreMaestro", nombreMaestro);
		modelo.addAttribute("horasMat", horasMat);
		modelo.addAttribute("horasReg", horasReg);
		modelo.addAttribute("lisHorarioMaestro", lisHorarioMaestro);
		modelo.addAttribute("mapaCupos", mapaCupos);
		modelo.addAttribute("mapaDisponibles", mapaDisponibles);
		modelo.addAttribute("mapaOcupados", mapaOcupados);
		modelo.addAttribute("mapaOcupadosAlumno", mapaOcupadosAlumno);

		return "portales/alumno/horariocimum";
	}

	@RequestMapping("/portales/alumno/grabarHorario")
	public String portalesAlumnoGrabarHorario(HttpServletRequest request, Model modelo) {

		String cargaId = request.getParameter("CargaId") == null ? "0" : request.getParameter("CargaId");
		String cursoCargaId = request.getParameter("CursoCargaId") == null ? "0" : request.getParameter("CursoCargaId");
		String maestro = request.getParameter("CodigoMaestro") == null ? "0" : request.getParameter("CodigoMaestro");
		String diaHora = request.getParameter("Dia") == null ? "0" : request.getParameter("Dia");
		String folio = request.getParameter("Folio") == null ? "0" : request.getParameter("Folio");
		String matricula = "0";
		float horasReg = 0;
		float horasMat = 0;
		float horasFolio = 0;
		String mensaje = "-";
		MusiHorarioAlumno horario = new MusiHorarioAlumno();

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoPersonal");
			horasMat = krdxCursoActDao.numFrecuenciasMateria(matricula, cursoCargaId);
			horasReg = musiHorarioDao.getHorasReg(matricula, cursoCargaId);
			horasFolio = musiHorarioDao.getHorasFolio(folio);
			// Se permite grabar si no supera las frecuencias permitidas(La cantidad de
			// frecuencias de la materia es mayor o igual a la suma de frecuencias
			// registradas + frecuencias seleccionadas)
			if (horasMat >= (horasReg + horasFolio)) {
				horario.setCodigoPersonal(matricula);
				horario.setCursoCargaId(cursoCargaId);
				horario.setFolio(folio);
				if (musiHorarioAlumnoDao.insertReg(horario)) {
					mensaje = "Saved";
				} else {
					mensaje = "Error saving";
				}
			} else {
				mensaje = "Exceeds the number of allowed frequencies";
			}
		}
		return "redirect:/portales/alumno/horariocimum?CargaId=" + cargaId + "&CursoCargaId=" + cursoCargaId
				+ "&CodigoMaestro=" + maestro + "&Dia=" + diaHora + "&Mensaje=" + mensaje;
	}

	@RequestMapping("/portales/alumno/borrarHorario")
	public String portalesAlumnoBorrarHorario(HttpServletRequest request, Model modelo) {

		String cargaId = request.getParameter("CargaId") == null ? "0" : request.getParameter("CargaId");
		String cursoCargaId = request.getParameter("CursoCargaId") == null ? "0" : request.getParameter("CursoCargaId");
		String maestro = request.getParameter("CodigoMaestro") == null ? "0" : request.getParameter("CodigoMaestro");
		String diaHora = request.getParameter("Dia") == null ? "0" : request.getParameter("Dia");
		String folio = request.getParameter("Folio") == null ? "0" : request.getParameter("Folio");
		String matricula = "0";
		String mensaje = "-";

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoPersonal");
			// Se permite grabar si no supera las frecuencias permitidas(La cantidad de
			// frecuencias de la materia es mayor o igual a la suma de frecuencias
			// registradas + frecuencias seleccionadas)
			if (musiHorarioAlumnoDao.deleteReg(matricula, folio)) {
				mensaje = "Deleted";
			} else {
				mensaje = "Error deleting";
			}
		}

		return "redirect:/portales/alumno/horariocimum?CargaId=" + cargaId + "&CursoCargaId=" + cursoCargaId
				+ "&CodigoMaestro=" + maestro + "&Dia=" + diaHora + "&Mensaje=" + mensaje;
	}

	@RequestMapping("/portales/alumno/convalidacion")
	public String portalesAlumnoConvalidacion(HttpServletRequest request, Model modelo) {
		// enviarConEnoc(request,"Error-aca.ControllerPortales|portalesAlumnoFinanciero");

		String matricula 		= "0";	
		String codigoPersonal 	= "";

		HttpSession sesion 		= request.getSession();
		if (sesion != null) {
			matricula 			= (String) sesion.getAttribute("codigoAlumno");
			codigoPersonal 		=	(String) sesion.getAttribute("codigoPersonal");
		}
		
		List<ConvEvento> lisConvalidaciones 	= convEventoDao.getListPersonal(matricula, "ORDER BY CONVALIDACION_ID");
		List<ConvMateria> lisMaterias 			= convMateriaDao.getListAlumno(matricula, " ORDER BY CONVALIDACION_ID");
		HashMap<String, String> mapCursos 		= mapaCursoDao.mapCursosConvalida(matricula);

		modelo.addAttribute("SubMenu", "12");
		modelo.addAttribute("matricula", matricula);
		
		modelo.addAttribute("lisConvalidaciones", lisConvalidaciones);
		modelo.addAttribute("lisMaterias", lisMaterias);
		modelo.addAttribute("mapCursos", mapCursos);
		
		return "portales/alumno/convalidacion";
	}

	@RequestMapping("/portales/alumno/historial")
	public String portalesAlumnoHistorial(HttpServletRequest request, Model modelo) {

		String matricula = "0";

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
		}

		List<ConvEvento> lisConv = convEventoDao.getListPersonal(matricula, "ORDER BY CONVALIDACION_ID");

		List<ConvHistorial> lisHistorial = convHistorialDao.lisPorAlumno(matricula,
				"ORDER BY TO_DATE(FECHA, 'DD/MM/YY') ASC");

		modelo.addAttribute("SubMenu", "12");
		modelo.addAttribute("matricula", matricula);
		modelo.addAttribute("lisConv", lisConv);
		modelo.addAttribute("lisHistorial", lisHistorial);

		return "portales/alumno/historial";
	}

	@RequestMapping("/portales/alumno/detallesPromedio")
	public String portalesAlumnoDetallesPromedio(HttpServletRequest request, Model modelo) {

		String planId = request.getParameter("PlanId") == null ? "0" : request.getParameter("PlanId");
		String matricula = "0";
		String nombreAlumno = "-";

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
			nombreAlumno = alumPersonalDao.getNombreAlumno(matricula, "NOMBRE");
		}

		AlumPlan alumPlan = alumPlanDao.mapeaRegId(matricula, planId);
		// String semTetra = alumPersonalDao.getCarreraId(planId).startsWith("107") ? "Tetra." : "Sem.";
		String escala = alumPlan.getEscala();

		List<AlumnoCurso> listaCursos = alumnoCursoDao.getListAll("WHERE CODIGO_PERSONAL='" + matricula + "'"
				+ " AND PLAN_ID='" + planId + "' AND TIPOCAL_ID IN ('1')"
				+ " AND (SELECT TIPOCURSO_ID FROM ENOC.MAPA_CURSO WHERE CURSO_ID=ALUMNO_CURSO.CURSO_ID) IN ('1', '2', '7','9')"
				+ " AND CONVALIDACION IN ('N', 'I') AND ((NOTA!=0 OR CONVALIDACION='S' OR CREDITOS>0) OR NOTA_EXTRA!=0)"
				+ " ORDER BY CICLO");

		HashMap<String,String> mapaGradePoint			 	= alumnoCursoDao.mapaGradePoint(matricula, planId);

		modelo.addAttribute("SubMenu", "4");
		modelo.addAttribute("matricula", matricula);
		modelo.addAttribute("alumPlan", alumPlan);
		// modelo.addAttribute("semTetra", semTetra);
		modelo.addAttribute("escala", escala);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("listaCursos", listaCursos);
		modelo.addAttribute("mapaGradePoint", mapaGradePoint);

		return "portales/alumno/detallesPromedio";
	}

	@RequestMapping("/portales/alumno/etiquetas")
	public String portalesAlumnoEtiquetas(HttpServletRequest request, Model modelo) {

		String folio = request.getParameter("Folio") == null ? "0" : request.getParameter("Folio");
		String matricula = "0";

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
		}

		BitTramiteAlumno bitTramiteAlumno = bitTramiteAlumnoDao.mapeaRegId(folio);
		BitTramite bitTramite = bitTramiteDao.mapeaRegId(bitTramiteAlumno.getTramiteId());

		List<BitEtiqueta> lisEtiquetasFolio = bitEtiquetaDao.listEtiquetas(folio,
				" ORDER BY ENOC.BIT_ETIQUETA.FECHA");
		List<BitEtiqueta> lisEtiquetasAlumno = bitEtiquetaDao.listEtiquetasAlumno(matricula,
				" ORDER BY ENOC.BIT_ETIQUETA.FECHA");
		HashMap<String, BitTramiteAlumno> mapaTurnados = bitTramiteAlumnoDao.mapTramitesAlumno(matricula);
		HashMap<String, BitTramite> mapaTramites = bitTramiteDao.mapTramites();
		HashMap<String, String> mapaAreas = bitAreaDao.mapaAreas();
		HashMap<String, String> mapaEstados = bitEstadoDao.mapEstados();
		HashMap<String, String> mapaMaestros = maestrosDao.mapaMaestroCorto("NOMBRE");

		String nombreAlumno = alumPersonalDao.getNombreAlumno(matricula, "NOMBRE");
		String nombreTramite = bitTramiteDao.getNombre(bitTramiteAlumno.getTramiteId());
		String estado = bitEstadoDao.getEstadoNombre(bitTramiteAlumno.getEstado());

		modelo.addAttribute("matricula", matricula);
		modelo.addAttribute("bitTramiteAlumno", bitTramiteAlumno);
		modelo.addAttribute("bitTramite", bitTramite);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("nombreTramite", nombreTramite);
		modelo.addAttribute("estado", estado);

		modelo.addAttribute("SubMenu", "7");
		modelo.addAttribute("lisEtiquetasFolio", lisEtiquetasFolio);
		modelo.addAttribute("lisEtiquetasAlumno", lisEtiquetasAlumno);
		modelo.addAttribute("mapaTurnados", mapaTurnados);
		modelo.addAttribute("mapaTramites", mapaTramites);
		modelo.addAttribute("mapaAreas", mapaAreas);
		modelo.addAttribute("mapaEstados", mapaEstados);
		modelo.addAttribute("mapaMaestros", mapaMaestros);

		return "portales/alumno/etiquetas";
	}

	@RequestMapping("/portales/alumno/portal_alumno")
	public String portalesAlumnoPortalAlumno(HttpServletRequest request, Model modelo) {
		return "portales/alumno/portal_alumno";
	}

	@RequestMapping("/portales/alumno/acreditados")
	public String portalesAlumnoAcreditados(HttpServletRequest request, Model modelo) {
		return "portales/alumno/acreditados";
	}

	@RequestMapping("/portales/alumno/amigos")
	public String portalesAlumnoAmigos(HttpServletRequest request, Model modelo) {

		String grupoId 		= request.getParameter("grupoId") == null ? "0" : request.getParameter("grupoId");
		String grupoNombre	= apFisicaGrupoDao.nombreGrupo(grupoId);

		List<aca.Mapa> lisAmigos = apFisicaAlumnoDao.lisAmigosDelGrupo(grupoId, "ORDER BY NOMBRE");
		
		modelo.addAttribute("SubMenu", "3");
		modelo.addAttribute("grupoNombre", grupoNombre);
		modelo.addAttribute("lisAmigos", lisAmigos);

		return "portales/alumno/amigos";
	}

	@RequestMapping("/portales/alumno/aptitud")
	public String portalesAlumnoAptitud(HttpServletRequest request, Model modelo) {
		enviarConEnoc(request, "Error-aca.ContPortalesAlumno|portalesAlumnoAptitud");
		return "portales/alumno/aptitud";
	}

	@RequestMapping("/portales/alumno/autorizaPadre")
	public String portalesAlumnoAutorizaPadre(HttpServletRequest request, Model modelo) {

		String matricula = "0";
		String alumnoNombre = "-";
		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
			alumnoNombre = alumPersonalDao.getNombreAlumno(matricula, "NOMBRE");
		}

		List<PadreAlumno> lisPadres = padreAlumnoDao.getListaPadres(matricula, "'S','A'", "ORDER BY 2");
		HashMap<String, PadrePersonal> mapaPadres = padrePersonalDao.mapaTodos();

		modelo.addAttribute("alumnoNombre", alumnoNombre);
		modelo.addAttribute("lisPadres", lisPadres);
		modelo.addAttribute("mapaPadres", mapaPadres);

		return "portales/alumno/autorizaPadre";
	}

	@RequestMapping("/portales/alumno/grabarPadre")
	public String portalesAlumnoGrabarPadre(HttpServletRequest request, Model modelo) {

		String padreId = request.getParameter("PadreId") == null ? "0" : request.getParameter("PadreId");
		String fechaHoy = aca.util.Fecha.getHoy();
		String matricula = "0";
		String mensaje = "-";
		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
		}

		PadreAlumno padre = new PadreAlumno();
		padre.setCodigoPersonal(matricula);
		padre.setPadreId(padreId);
		if (padreAlumnoDao.existeReg(padreId, matricula)) {
			if (padreAlumnoDao.updateAutoriza(padreId, matricula, fechaHoy, "A")) {
				mensaje = "Approved";

			} else {
				mensaje = "Error updating";
			}
		}

		return "redirect:/portales/alumno/autorizaPadre?Mensaje=" + mensaje;
	}

	@RequestMapping("/portales/alumno/bajar")
	public String portalesAlumnoBajar(HttpServletRequest request, Model modelo) {
		return "portales/alumno/bajar";
	}

	@RequestMapping("/portales/alumno/becas")
	public String portalesAlumnoBecas(HttpServletRequest request, Model modelo) {
		enviarConEnoc(request, "Error-aca.ContPortalesAlumno|portalesAlumnoBecas");
		return "portales/alumno/becas";
	}

	@RequestMapping("/portales/alumno/boleta")
	public String portalesAlumnoBoleta(HttpServletRequest request, Model modelo) {

		String cargaId = request.getParameter("CargaId") == null ? "0" : request.getParameter("CargaId");
		String bloqueId = request.getParameter("BloqueId") == null ? "0" : request.getParameter("BloqueId");
		String matricula = "0";
		String planId = "0";
		String carreraId = "0";
		String carreraNombre = "-";
		AlumPersonal alumno = new AlumPersonal();
		Carga carga = cargaDao.mapeaRegId(cargaId);

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
			if (cargaId.equals("0"))
				cargaId = (String) sesion.getAttribute("cargaId");
			if (alumPersonalDao.existeReg(matricula)) {
				alumno = alumPersonalDao.mapeaRegId(matricula);
			}

			if (cargaAlumnoDao.existeReg(matricula, cargaId, bloqueId)) {
				planId = cargaAlumnoDao.mapeaRegId(matricula, cargaId, bloqueId).getPlanId();
				carreraId = mapaPlanDao.getCarreraId(planId);
				carreraNombre = catCarreraDao.getNombreCarrera(carreraId);
			}
		}

		List<AlumnoCurso> lisCursos = alumnoCursoDao.getListAlumnoCarga(matricula, cargaId,
				"ORDER BY CICLO, NOMBRE_CURSO");
		HashMap<String, CatTipoCal> mapaTipos = catTipoCalDao.getMapAll("");

		modelo.addAttribute("alumno", alumno);
		modelo.addAttribute("carga", carga);
		modelo.addAttribute("carreraNombre", carreraNombre);
		modelo.addAttribute("lisCursos", lisCursos);
		modelo.addAttribute("mapaTipos", mapaTipos);

		return "portales/alumno/boleta";
	}

	@RequestMapping("/portales/alumno/boletaParcial")
	public String portalesAlumnoBoletaParcial(HttpServletRequest request, Model modelo) {

		String cargaId = request.getParameter("CargaId") == null ? "0" : request.getParameter("CargaId");
		String bloqueId = request.getParameter("BloqueId") == null ? "0" : request.getParameter("BloqueId");
		String matricula = "0";
		String planId = "0";
		String carreraId = "0";
		String carreraNombre = "-";
		AlumPersonal alumno = new AlumPersonal();
		AlumPlan plan = new AlumPlan();
		Carga carga = new Carga();

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
			if (cargaId.equals("0"))
				cargaId = (String) sesion.getAttribute("cargaId");
			if (alumPersonalDao.existeReg(matricula)) {
				alumno = alumPersonalDao.mapeaRegId(matricula);
			}
			if (cargaDao.existeReg(cargaId)) {
				carga = cargaDao.mapeaRegId(cargaId);
			}
			if (cargaAlumnoDao.existeReg(matricula, cargaId, bloqueId)) {
				planId = cargaAlumnoDao.mapeaRegId(matricula, cargaId, bloqueId).getPlanId();
				carreraId = mapaPlanDao.getCarreraId(planId);
				carreraNombre = catCarreraDao.getNombreCarrera(carreraId);
			}
		}

		List<AlumnoCurso> lisCursos = alumnoCursoDao.getListAlumnoCarga(matricula, cargaId,
				"ORDER BY CICLO, NOMBRE_CURSO");
		HashMap<String, CatTipoCal> mapaTipos = catTipoCalDao.getMapAll("");
		HashMap<String, String> mapAvanceEvaluaciones = alumnoEficienciaDao.mapaAvanceEvaluacion(matricula);
		HashMap<String, String> mapPuntosEvaluados = alumnoEficienciaDao.mapaPuntosEvaluados(matricula);
		HashMap<String, String> mapPuntosGanados = alumnoEvaluacionDao.mapaPuntosAlumno(matricula, cargaId,
				"'%','P','E'");

		plan = alumPlanDao.mapeaRegId(matricula, planId);

		if (lisCursos.size() > 0) {
			plan = alumPlanDao.mapeaRegId(matricula, lisCursos.get(0).getPlanId());
		}

		modelo.addAttribute("alumno", alumno);
		modelo.addAttribute("carga", carga);
		modelo.addAttribute("plan", plan);
		modelo.addAttribute("carreraNombre", carreraNombre);
		modelo.addAttribute("lisCursos", lisCursos);
		modelo.addAttribute("mapaTipos", mapaTipos);
		modelo.addAttribute("mapAvanceEvaluaciones", mapAvanceEvaluaciones);
		modelo.addAttribute("mapPuntosEvaluados", mapPuntosEvaluados);
		modelo.addAttribute("mapPuntosGanados", mapPuntosGanados);

		return "portales/alumno/boletaParcial";
	}

	@RequestMapping("/portales/alumno/borrar")
	public String portalesAlumnoBorrar(HttpServletRequest request, Model modelo) {
		enviarConEnoc(request, "Error-aca.ContPortalesAlumno|portalesAlumnoBorrar");
		return "portales/alumno/borrar";
	}

	@RequestMapping("/portales/alumno/calificaciones")
	public String portalesAlumnoCalificaciones(HttpServletRequest request, Model modelo) {

		String ver 				= request.getParameter("ver") == null ? "T" : request.getParameter("ver");
		String planId 			= request.getParameter("plan") == null ? "0" : request.getParameter("plan");
		boolean tienePlanActivo = false;
		Parametros parametros 	= parametrosDao.mapeaRegId("1"); 

		String codigoPersonal = "0";
		String codigoAlumno = "0";
		String matricula = "0";
		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula 			= (String) sesion.getAttribute("codigoAlumno");
			codigoAlumno 		= (String) sesion.getAttribute("codigoAlumno");
			codigoPersonal 		= sesion.getAttribute("codigoPersonal") == null ?"-":(String) sesion.getAttribute("codigoPersonal");
		}
		
		AlumPlan alumPlan = new AlumPlan();
		if (planId.equals("0")) {
			if (alumPlanDao.tienePlanActivo(codigoAlumno)) {
				tienePlanActivo = true;
				alumPlan = alumPlanDao.mapeaRegId(codigoAlumno);
				planId = alumPlan.getPlanId();
			}
		} else {
			alumPlan = alumPlanDao.mapeaRegId(codigoAlumno, planId);
			tienePlanActivo = true;
		}
		
		boolean esEmpleado = empMaestroDao.esMaestro(codigoPersonal);

		String planNombre = mapaPlanDao.getNombrePlan(planId);
		String ultimaInscripcion = estadisticaDao.getFechaMaximaPorPlan(codigoAlumno, planId);
		if (ultimaInscripcion.equals("01/01/2000")) {
			ultimaInscripcion = alumPlan.getFInicio();
		}

		AlumPersonal alumPersonal = alumPersonalDao.mapeaRegId(codigoAlumno);
		String modalidadUsuario = accesoDao.getModalidad(codigoPersonal);
		String modalidadAlumno = alumAcademicoDao.getModalidadId(codigoAlumno);

		String facultadId = catCarreraDao.getFacultadId(alumPlanDao.getCarreraId(codigoAlumno));
		String areaId = catFacultadDao.getAreaId(facultadId);
		String tipoProm = catAreaDao.getTipoPromedio(areaId);

		boolean mostrar = false;
		if (modalidadUsuario.equals("0")) {
			mostrar = false;
		} else if (modalidadUsuario.equals(modalidadAlumno)) {
			mostrar = false;
		}

		boolean tienePlan = false;
		boolean tieneRvoe = mapaArchivoDao.existeReg(planId, "1");
		if (mapaArchivoDao.existeReg(planId, "2")) {
			tienePlan = true;
		}

		CancelaEstudio cancelaEstudio = new CancelaEstudio();
		boolean cancelado = false;
		if (cancelaEstudiodoDao.existeReg(codigoAlumno, alumPlan.getPlanId())) {
			cancelaEstudio = cancelaEstudiodoDao.mapeaRegId(codigoAlumno, alumPlan.getPlanId());
			cancelado = true;
		}

		String condicion = "";
		if (ver.equals("A"))
			condicion = " AND TIPOCAL_ID IN ('1','I')";
		else if (ver.equals("R"))
			condicion = " AND TIPOCAL_ID IN ('2','4')";
		else if (ver.equals("D"))
			condicion = " AND TIPOCAL_ID IN ('5','6')";

		System.out.println(condicion);

		List<AlumnoCurso> lisCursosAlumno = alumnoCursoDao.getListAlumno(codigoAlumno, " AND PLAN_ID='" + planId + "' "	+ condicion + " ORDER BY CICLO, NOMBRE_CURSO, TO_CHAR(ENOC.ALUMNO_CURSO.F_EVALUACION,'YYYY-MM-DD')");
		List<MapaCurso> lisCursosCarrera = mapaCursoDao.getListPlan(planId, " ORDER BY CICLO, NOMBRE_CURSO");
		List<MapaAvance> lisAvances = mapaAvanceDao.getListPlan(planId, " ORDER BY PLAN_ID");
		List<AlumPlan> lisPlanes = alumPlanDao.getLista(codigoAlumno,"ORDER BY TO_CHAR(ENOC.ALUM_PLAN.F_INICIO,'YYYY/MM/DD')");
		List<CatTipoCurso> lisTiposCurso = catTipoCursoDao.getListAll("");
		List<aca.Mapa> lisCreditosRequeridos = mapaAvanceDao.lisCreditosRequeridos(planId, " ORDER BY 1");

		HashMap<String, MapaPlan> mapaPlanes 				= mapaPlanDao.mapPlanes("'A','V','I'");
		HashMap<String, MapaCurso> mapaCursos 				= mapaCursoDao.getMapaCursos(planId, "");
		HashMap<String, MapaCredito> mapaCreditos 			= mapaCreditoDao.mapaCredito(planId);
		HashMap<String, CatTipoCurso> mapaTipoCurso 		= catTipoCursoDao.getMapAll("");
		HashMap<String, CatTipoCal> mapaTipoCal 			= catTipoCalDao.getMapAll("");
		HashMap<String, String> mapaTipoPorCiclo 			= mapaCursoDao.mapaTipoPorCiclo(planId);
		HashMap<String, MapaAvance> mapaAvances 			= mapaAvanceDao.getMapAll(" WHERE PLAN_ID='" + planId + "'");
		HashMap<String, String> mapaCreditosCicloyTipo 		= mapaAvanceDao.mapaCreditosPorCicloyTipo(planId);
		HashMap<String, String> mapaCreditosPorCiclo 		= alumnoCursoDao.getMapCreditos(planId, codigoAlumno);
		HashMap<String, String> mapaCreditosPorCicloAvance 	= mapaAvanceDao.getMapCreditos(planId);
		HashMap<String, String> mapaCreditoObligatorio 		= mapaCursoDao.getCursoTerminadoObligatorio(codigoAlumno,planId);
		HashMap<String, String> mapaMateriasConv 			= convMateriaDao.mapaMatConv(codigoAlumno, "'P'");
		HashMap<String, String> mapaCreditosAlumno 			= alumnoCursoDao.mapaCreditosAlumno(planId, codigoAlumno);
		HashMap<String, Maestros> mapaMaestros 				= maestrosDao.mapaMaestrosEnNotas(codigoAlumno);
		HashMap<String, AlumnoCurso> mapaCursosDelAlumno 	= alumnoCursoDao.mapaCursosDelAlumno(codigoAlumno,planId);
		HashMap<String,String> mapaGradePoint			 	= alumnoCursoDao.mapaGradePoint(codigoAlumno, planId);
		HashMap<String,MapaMayorMenor> mapaMayores 			= mapaMayorMenorDao.mapMayoresPorPlan( planId," ORDER BY NOMBRE");
		HashMap<String,MapaMayorMenor> mapaMenores 			= mapaMayorMenorDao.mapMenoresPorPlan( planId," ORDER BY NOMBRE");

		boolean completo = true;
		for (aca.Mapa map : lisCreditosRequeridos) {
			if (mapaCreditosAlumno.containsKey(map.getLlave())) {
				if (Integer.parseInt(mapaCreditosAlumno.get(map.getLlave())) < Integer.parseInt(map.getValor())) {
					completo = false;
				}
			}
		}
		if (completo)
			alumPlanDao.updateFinalizado("S", codigoAlumno, planId);

		String cargaId = alumEstadoDao.getCarga(codigoAlumno);
		Carga carga = cargaDao.mapeaRegId(cargaId);

		modelo.addAttribute("SubMenu", "4");
		modelo.addAttribute("esEmpleado", esEmpleado);
		modelo.addAttribute("alumPersonal", alumPersonal);		
		modelo.addAttribute("alumPlan", alumPlan);
		modelo.addAttribute("modalidadUsuario", modalidadUsuario);
		modelo.addAttribute("modalidadAlumno", modalidadAlumno);
		modelo.addAttribute("mostrar", mostrar);
		modelo.addAttribute("tipoProm", tipoProm);
		modelo.addAttribute("tienePlan", tienePlan);
		modelo.addAttribute("tienePlanActivo", tienePlanActivo);
		modelo.addAttribute("tieneRvoe", tieneRvoe);
		modelo.addAttribute("tieneRvoe", tieneRvoe);
		modelo.addAttribute("cancelaEstudio", cancelaEstudio);
		modelo.addAttribute("cancelado", cancelado);
		modelo.addAttribute("planNombre", planNombre);
		modelo.addAttribute("ultimaInscripcion", ultimaInscripcion);
		modelo.addAttribute("parametros", parametros);
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("carga", carga);

		modelo.addAttribute("lisCursosAlumno", lisCursosAlumno);
		modelo.addAttribute("lisCursosCarrera", lisCursosCarrera);
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("lisAvances", lisAvances);
		modelo.addAttribute("lisTiposCurso", lisTiposCurso);

		modelo.addAttribute("mapaPlanes", mapaPlanes);
		modelo.addAttribute("mapaCursos", mapaCursos);
		modelo.addAttribute("mapaCreditos", mapaCreditos);
		modelo.addAttribute("mapaTipoCurso", mapaTipoCurso);
		modelo.addAttribute("mapaTipoPorCiclo", mapaTipoPorCiclo);
		modelo.addAttribute("mapaTipoCal", mapaTipoCal);
		modelo.addAttribute("mapaAvances", mapaAvances);
		modelo.addAttribute("mapaCreditosPorCiclo", mapaCreditosPorCiclo);
		modelo.addAttribute("mapaCreditosCicloyTipo", mapaCreditosCicloyTipo);
		modelo.addAttribute("mapaCreditosPorCicloAvance", mapaCreditosPorCicloAvance);
		modelo.addAttribute("mapaCreditoObligatorio", mapaCreditoObligatorio);
		modelo.addAttribute("mapaMateriasConv", mapaMateriasConv);
		modelo.addAttribute("mapaCreditosAlumno", mapaCreditosAlumno);
		modelo.addAttribute("mapaMaestros", mapaMaestros);
		modelo.addAttribute("mapaCursosDelAlumno", mapaCursosDelAlumno);
		modelo.addAttribute("mapaValNotas", mapaGradePoint);
		modelo.addAttribute("mapaGradePoint", mapaGradePoint);
		modelo.addAttribute("mapaMayores", mapaMayores);
		modelo.addAttribute("mapaMenores", mapaMenores);
		
		return "portales/alumno/calificaciones";
	}

	@RequestMapping("/portales/alumno/cambiaPortal")
	public String portalesAlumnoCambiaPortal(HttpServletRequest request, Model modelo) {
		return "portales/alumno/cambiaPortal";
	}

	@RequestMapping("/portales/alumno/claseGraduandos")
	public String portalesAlumnoClaseGraduandos(HttpServletRequest request, Model modelo) {
		enviarConEnoc(request, "Error-aca.ContPortalesAlumno|portalesAlumnoClaseGraduandos");
		return "portales/alumno/claseGraduandos";
	}

	@RequestMapping("/portales/alumno/codigoBarras")
	public String portalesAlumnoCodigoBarras(HttpServletRequest request, Model modelo) {
		return "portales/alumno/codigoBarras";
	}

	@RequestMapping("/portales/alumno/digital")
	public String portalesAlumnoDigital(HttpServletRequest request, Model modelo) {

		String codigoAlumno = "0";
		String documentoId = request.getParameter("DocumentoId") == null ? "0" : request.getParameter("DocumentoId");
		String hoja = request.getParameter("Hoja") == null ? "0" : request.getParameter("Hoja");
		String usuario = request.getParameter("usuario");
		String alumnoNombre = "-";
		String documentoNombre = "-";

		AlumPersonal alumPersonal = new AlumPersonal();
		AlumPlan alumPlan = new AlumPlan();
		PosArchDocAlum imagenDoc = new PosArchDocAlum();
		HttpSession sesion = request.getSession();
		if (sesion != null) {
			codigoAlumno = (String) sesion.getAttribute("codigoAlumno");
			if (alumPersonalDao.existeAlumno(codigoAlumno)) {
				alumPersonal = alumPersonalDao.mapeaRegId(codigoAlumno);
				alumnoNombre = alumPersonal.getNombre() + " " + alumPersonal.getApellidoPaterno() + " "
						+ alumPersonal.getApellidoMaterno();
				alumPlan = alumPlanDao.mapeaRegId(codigoAlumno);
			}
		}

		if (!documentoId.equals("0")) {
			documentoNombre = archDocumentosDao.getDescripcion(documentoId);
			if (!hoja.equals("0")) {
				imagenDoc = posArchDocAlumDao.mapeaRegId(codigoAlumno, documentoId, hoja);
			}
		}

		List<ArchDocAlum> lisDocumentos = archDocAlumDao.getListDocFiltrados(codigoAlumno, "S", "ORDER BY IDDOCUMENTO");
		List<PosArchDocAlum> lisImagenes = posArchDocAlumDao.lisImagenesAlumno(codigoAlumno,
				" ORDER BY IDDOCUMENTO, HOJA");
		HashMap<String, String> mapaDocumentos = archDocumentosDao.mapaTodos();

		modelo.addAttribute("SubMenu", "6");
		modelo.addAttribute("alumnoNombre", alumnoNombre);
		modelo.addAttribute("documentoNombre", documentoNombre);
		modelo.addAttribute("imagenDoc", imagenDoc);
		modelo.addAttribute("alumPlan", alumPlan);
		modelo.addAttribute("lisDocumentos", lisDocumentos);
		modelo.addAttribute("lisImagenes", lisImagenes);
		modelo.addAttribute("mapaDocumentos", mapaDocumentos);

		return "portales/alumno/digital";
	}

	@RequestMapping("/portales/alumno/digitalIncorrecto")
	public String portalesAlumnoDigitalIncorrecto(HttpServletRequest request) {

		String documentoId = request.getParameter("DocumentoId") == null ? "0" : request.getParameter("DocumentoId");
		String codigoAlumno = "0";

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			codigoAlumno = (String) sesion.getAttribute("codigoAlumno");
		}

		if (archDocAlumDao.existeReg(codigoAlumno, documentoId)) {
			ArchDocAlum documento = archDocAlumDao.mapeaRegId(codigoAlumno, documentoId);

			if (documento.getIncorrecto().equals("S")) {
				archDocAlumDao.marcarIncorrecto(codigoAlumno, documentoId, "N");
			} else {
				archDocAlumDao.marcarIncorrecto(codigoAlumno, documentoId, "S");
			}
		}

		return "redirect:/portales/alumno/digital";
	}

	@RequestMapping("/portales/alumno/digitalmax")
	public String portalesAlumnoDigitalMax(HttpServletRequest request, Model modelo) {
		return "portales/alumno/digitalmax";
	}

	@RequestMapping("/portales/alumno/docEntregados")
	public String portalesAlumnoDocEntregados(HttpServletRequest request, Model modelo) {

		String codigoAlumno = "0";
		HttpSession sesion = request.getSession();
		if (sesion != null) {
			codigoAlumno = (String) sesion.getAttribute("codigoAlumno");
		}

		AlumPersonal alumPersonal = alumPersonalDao.mapeaRegId(codigoAlumno);
		List<ArchEntrega> lisEntregas = archEntregaDao.lisSinArchivos(codigoAlumno);
		HashMap<String, String> mapaDocumentos = archDocumentosDao.mapaTodos();
		HashMap<String, String> mapaIdentificacion = archEntregaDao.mapaIdentificacion(codigoAlumno);
		HashMap<String, String> mapaPoder = archEntregaDao.mapaPoder(codigoAlumno);
		HashMap<String, String> mapaEnvio = archEntregaDao.mapaEnvio(codigoAlumno);
		HashMap<String, String> mapaFirma = archEntregaDao.mapaFirma(codigoAlumno);
		HashMap<String, String> mapaExtra = archEntregaDao.mapaExtra(codigoAlumno);

		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("lisEntregas", lisEntregas);
		modelo.addAttribute("mapaDocumentos", mapaDocumentos);
		modelo.addAttribute("mapaIdentificacion", mapaIdentificacion);
		modelo.addAttribute("mapaPoder", mapaPoder);
		modelo.addAttribute("mapaEnvio", mapaEnvio);
		modelo.addAttribute("mapaFirma", mapaFirma);
		modelo.addAttribute("mapaExtra", mapaExtra);

		return "portales/alumno/docEntregados";
	}

	@RequestMapping("/portales/alumno/docFotos")
	public String portalesAlumnoDocFotos(HttpServletRequest request, Model modelo) {
		enviarConEnoc(request, "Error-aca.ContPortalesAlumno|portalesAlumnoDocFotos");
		return "portales/alumno/docFotos";
	}

	@RequestMapping("/portales/alumno/documentosExternos")
	public String portalesAlumnoDocumentosExternos(HttpServletRequest request, Model modelo) {

		return "portales/alumno/documentosExternos";
	}

	@RequestMapping("/portales/alumno/elegirMateria")
	public String portalesAlumnoElegirMateria(HttpServletRequest request, Model modelo) {
		enviarConEnoc(request, "Error-aca.ContPortalesAlumno|portalesAlumnoElegirMateria");
		return "portales/alumno/elegirMateria";
	}

	@RequestMapping("/portales/alumno/evaluaMaestro")
	public String portalesAlumnoEvaluaMaestro(HttpServletRequest request, Model modelo) {

		String edoId 				= request.getParameter("edo") == null ? "0" : request.getParameter("edo");
		String cursoCargaId 		= request.getParameter("cursoCarga") == null ? "0" : request.getParameter("cursoCarga");
		String cursoId 				= cargaGrupoDao.getCursoId(cursoCargaId);
		String materiaNombre 		= mapaCursoDao.getNombreCurso(cursoId);
		String maestroNombre 		= "-";
		Edo edo = new Edo();
		if (edoDao.existeReg(edoId)) {
			edo = edoDao.mapeaRegId(edoId);
		}

		CargaGrupo grupo = new CargaGrupo();
		if (cargaGrupoDao.existeReg(cursoCargaId)) {
			grupo = cargaGrupoDao.mapeaRegId(cursoCargaId);
			maestroNombre = usuariosDao.getNombreUsuario(grupo.getCodigoPersonal(), "NOMBRE");
		}

		List<EdoAlumnoPreg> lisPreguntas = edoAlumnoPregDao.getListEdo(edoId, " ORDER BY ORDEN");

		modelo.addAttribute("edo", edo);
		modelo.addAttribute("grupo", grupo);
		modelo.addAttribute("materiaNombre", materiaNombre);
		modelo.addAttribute("maestroNombre", maestroNombre);
		modelo.addAttribute("lisPreguntas", lisPreguntas);

		return "portales/alumno/evaluaMaestro";
	}

	@RequestMapping("/portales/alumno/grabarEvaluacion")
	public String portalesAlumnoGrabarEvaluacion(HttpServletRequest request, Model modelo) {

		String edoId = request.getParameter("edo") == null ? "0" : request.getParameter("edo");
		String cursoCargaId = request.getParameter("cursoCarga") == null ? "0" : request.getParameter("cursoCarga");
		String codigoAlumno = "0";
		HttpSession sesion = request.getSession();
		if (sesion != null) {
			codigoAlumno = (String) sesion.getAttribute("codigoPersonal");
		}

		CargaGrupo grupo = new CargaGrupo();
		if (cargaGrupoDao.existeReg(cursoCargaId)) {
			grupo = cargaGrupoDao.mapeaRegId(cursoCargaId);
		}

		List<EdoAlumnoPreg> lisPreguntas = edoAlumnoPregDao.getListEdo(edoId, " ORDER BY ORDEN");
		boolean guardo = true;
		int row = 0;
		for (EdoAlumnoPreg edoAlumnoPreg : lisPreguntas) {
			EdoAlumnoResp edoAlumnoResp = new EdoAlumnoResp();
			edoAlumnoResp.setEdoId(edoId);
			edoAlumnoResp.setPreguntaId(edoAlumnoPreg.getPreguntaId());
			edoAlumnoResp.setCodigoPersonal(codigoAlumno);
			edoAlumnoResp.setCursoCargaId(cursoCargaId);
			edoAlumnoResp.setCodigoMaestro(grupo.getCodigoPersonal());
			edoAlumnoResp.setRespuesta(request.getParameter("pregunta" + row));
			if (!edoAlumnoRespDao.existeReg(edoId, edoAlumnoPreg.getPreguntaId(), codigoAlumno, cursoCargaId, grupo.getCodigoPersonal())){
				if (!edoAlumnoRespDao.insertReg(edoAlumnoResp)) {
					guardo = false;
				}
			}			
			row++;
		}

		return "redirect:/portales/alumno/materias";
	}

	@RequestMapping("/portales/alumno/finComentario")
	public String portalesAlumnoFinComentario(HttpServletRequest request, Model modelo) {
		String matricula = "";
		String usuario = "";

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
			usuario = (String) sesion.getAttribute("codigoEmpleado");
		}

		String tipoEmpleado = finRolesDao.traeRol(usuario);
		String mensaje = "X";
		String alert = "";

		String accion = request.getParameter("Accion") == null ? "0" : request.getParameter("Accion");

		String folio = finComentarioDao.maximoReg(matricula);
		String comentario = "";
		String fecha = "";

		FinComentario finComentario = new FinComentario();

		if (accion.equals("2")) {
			finComentario.setCodigoPersonal(matricula);
			finComentario.setFolio(folio);
			finComentario.setComentario(request.getParameter("Comentario"));
			finComentario.setFecha(request.getParameter("FechaCom"));
			finComentario.setUsuario(usuario);
			finComentario.setTipo(tipoEmpleado);

			comentario = request.getParameter("Comentario");
			fecha = request.getParameter("FechaCom");

			if (finComentarioDao.insertReg(finComentario)) {
				mensaje = "Saved";
				alert = "success";
			} else {
				mensaje = "Error saving";
				alert = "danger";
			}
		}

		modelo.addAttribute("SubMenu", "5");
		modelo.addAttribute("matricula", matricula);
		modelo.addAttribute("usuario", usuario);
		modelo.addAttribute("tipoEmpleado", tipoEmpleado);
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("alert", alert);
		modelo.addAttribute("accion", accion);
		modelo.addAttribute("folio", folio);
		modelo.addAttribute("comentario", comentario);
		modelo.addAttribute("fecha", fecha);

		return "portales/alumno/finComentario";
	}

	@RequestMapping("/portales/alumno/finComentarioAccion")
	public String portalesAlumnoFinComentarioAccion(HttpServletRequest request, Model modelo) {

		String matricula = "";
		String usuario = "";

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
			usuario = (String) sesion.getAttribute("codigoEmpleado");
		}
		String tipoEmpleado = finRolesDao.traeRol(usuario);
		String folio = request.getParameter("Folio") == null ? "0" : request.getParameter("Folio");
		String mensaje = "X";
		String alert = "";
		String accion = request.getParameter("Accion") == null ? "0" : request.getParameter("Accion");

		FinComentario finComentario = new FinComentario();
		finComentario.setCodigoPersonal(matricula);
		finComentario.setFolio(folio);
		if (finComentarioDao.existeReg(matricula, folio)) {
			finComentario = finComentarioDao.mapeaRegId(matricula, folio);
		}

		String comentario = finComentario.getComentario();
		String fecha = finComentario.getFecha();

		if (accion.equals("2")) {
			finComentario.setCodigoPersonal(matricula);
			finComentario.setFolio(folio);
			finComentario.setComentario(request.getParameter("Comentario"));
			finComentario.setFecha(request.getParameter("FechaCom"));
			finComentario.setUsuario(usuario);
			finComentario.setTipo(tipoEmpleado);

			comentario = request.getParameter("Comentario");
			fecha = request.getParameter("FechaCom");

			if (finComentarioDao.updateReg(finComentario)) {
				mensaje = "Updated";
				alert = "success";
			} else {
				mensaje = "Error updating";
				alert = "danger";
			}
		}

		if (accion.equals("3")) {
			if (finComentarioDao.deleteReg(matricula, folio)) {
				comentario = "";
				fecha = "";
				folio = finComentarioDao.maximoReg(matricula);
				mensaje = "Deleted";
				alert = "success";
			} else {
				mensaje = "Error deleting";
				alert = "danger";
			}
		}

		modelo.addAttribute("SubMenu", "5");
		modelo.addAttribute("matricula", matricula);
		modelo.addAttribute("usuario", usuario);
		modelo.addAttribute("comentario", comentario);
		modelo.addAttribute("fecha", fecha);
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("alert", alert);

		return "portales/alumno/finComentarioAccion";
	}

	@RequestMapping("/portales/alumno/foto")
	public String portalesAlumnoFoto(HttpServletRequest request, Model modelo) {
		return "portales/alumno/foto";
	}

	@RequestMapping("/portales/alumno/fotoAlumno")
	public String portalesAlumnoFotoAlumno(HttpServletRequest request, Model modelo) {
		return "portales/alumno/fotoAlumno";
	}

	@RequestMapping("/portales/alumno/fotoBajar")
	public String portalesAlumnoFotoBajar(HttpServletRequest request, Model modelo) {
		return "portales/alumno/fotoBajar";
	}

	@RequestMapping("/portales/alumno/fotoEnviada")
	public String portalesAlumnoFotoEnviada(HttpServletRequest request, Model modelo) {
		return "portales/alumno/fotoEnviada";
	}

	@RequestMapping("/portales/alumno/fotoGeneral")
	public String portalesAlumnoFotoGeneral(HttpServletRequest request, Model modelo) {
		return "portales/alumno/fotoGeneral";
	}

	@RequestMapping("/portales/alumno/general")
	public String portalesAlumnoGeneral(HttpServletRequest request, Model modelo) {
		enviarConEnoc(request, "Error-aca.ContPortalesAlumno|portalesAlumnoGeneral");
		return "portales/alumno/general";
	}

	@RequestMapping("/portales/alumno/amigosGrupo")
	public String portalesAlumnoGrupoInscrito(HttpServletRequest request, Model modelo) {
		String grupoId 				= request.getParameter("GrupoId") == null ? "0" : request.getParameter("GrupoId");
		String nombreGrupo 			= apFisicaGrupoDao.nombreGrupo(grupoId);
		String nombreInstructor 	= apFisicaGrupoDao.nombreInstructor(grupoId);
		String liga 				= apFisicaGrupoDao.getLiga(grupoId);

		List<aca.Mapa> lisAmigos = apFisicaAlumnoDao.lisAmigosDelGrupo(grupoId, "ORDER BY APELLIDO_PATERNO,APELLIDO_MATERNO,NOMBRE");

		modelo.addAttribute("SubMenu", "3");
		modelo.addAttribute("lisAmigos", lisAmigos);
		modelo.addAttribute("nombreGrupo", nombreGrupo);
		modelo.addAttribute("nombreInstructor", nombreInstructor);
		modelo.addAttribute("liga", liga);
		return "portales/alumno/amigosGrupo";
	}

	@RequestMapping("/portales/alumno/guardar")
	public String portalesAlumnoGuardar(HttpServletRequest request, Model modelo) {
		return "portales/alumno/guardar";
	}

	@RequestMapping("/portales/alumno/imagen")
	public String portalesAlumnoImagen(HttpServletRequest request, Model modelo) {
		enviarConEnoc(request, "Error-aca.ContPortalesAlumno|portalesAlumnoImagen");
		return "portales/alumno/imagen";
	}

	@RequestMapping("/portales/alumno/imprimir")
	public String portalesAlumnoImprimir(HttpServletRequest request, Model modelo) {
		enviarConEnoc(request, "Error-aca.ContPortalesAlumno|portalesAlumnoImprimir");
		return "portales/alumno/imprimir";
	}

	@RequestMapping("/portales/alumno/inscripcion")
	public String portalesAlumnoInscripcion(HttpServletRequest request, Model modelo) {
		return "portales/alumno/inscripcion";
	}

	@RequestMapping("/portales/alumno/kardex")
	public String portalesAlumnoKardex(HttpServletRequest request, Model modelo) {
		enviarConEnoc(request, "Error-aca.ContPortalesAlumno|portalesAlumnoKardex");
		return "portales/alumno/kardex";
	}

	@RequestMapping("/portales/alumno/matricula")
	public String portalesAlumnoMatricula(HttpServletRequest request, Model modelo) {
		enviarConEnoc(request, "Error-aca.ContPortalesAlumno|portalesAlumnoMatricula");
		return "portales/alumno/matricula";
	}

	@RequestMapping("/portales/alumno/mensajeAumento")
	public String portalesAlumnoMensajeAumento(HttpServletRequest request, Model modelo) {
		// enviarConEnoc(request,
		// "Error-aca.ContPortalesAlumno|portalesAlumnoMensajeAumento");
		return "portales/alumno/mensajeAumento";
	}

	@RequestMapping("/portales/alumno/menuPortal")
	public String portalesAlumnoMenuPortal(HttpServletRequest request, Model modelo){
		enviarConEnoc(request, "Error-aca.ContPortalesAlumno|portalesAlumnoMenuPortal");
		return "portales/alumno/menuPortal";
	}

	@RequestMapping("/portales/alumno/modificar")
	public String portalesAlumnoModificar(HttpServletRequest request, Model modelo){
		enviarConEnoc(request, "Error-aca.ContPortalesAlumno|portalesAlumnoModificar");
		return "portales/alumno/modificar";
	}

	@RequestMapping("/portales/alumno/muestraCalculo")
	public String portalesAlumnoMuestraCalculo(HttpServletRequest request, Model modelo){
		enviarConEnoc(request, "Error-aca.ContPortalesAlumno|portalesAlumnoMuestraCalculo");
		return "portales/alumno/muestraCalculo";
	}

	@RequestMapping("/portales/alumno/opciones")
	public String portalesAlumnoOpciones(HttpServletRequest request, Model modelo){
		enviarConEnoc(request, "Error-aca.ContPortalesAlumno|portalesAlumnoOpciones");
		return "portales/alumno/opciones";
	}

	@RequestMapping("/portales/alumno/plazas")
	public String portalesAlumnoPlazas(HttpServletRequest request, Model modelo){

		String ejercicioId = request.getParameter("ejercicioId") == null ? aca.util.Fecha.getEjercicioHoy()	: request.getParameter("ejercicioId");
		String periodoId = request.getParameter("PeriodoId") == null ? becPeriodoDao.getPeriodoActual()	: request.getParameter("PeriodoId");

		String matricula = "0";
		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
		}
		
		// Ejercicios a partir del 2013
		List<ContEjercicio> listEjercicios = contEjercicioDao.getListProximos("ORDER BY 1 ASC");
		
		// Lista de centros de costo
		List<ContCcosto> listCostos 	= contCcostoDao.getListCentrosCostoVacantes(ejercicioId, periodoId, "'S'","ORDER BY NOMBRE");
		List<BecPeriodo> periodos 		= becPeriodoDao.lisVigentes(" ORDER BY PERIODO_ID DESC");
		List<BecPlazas> plazas 			= becPlazasDao.getListAll("WHERE PERIODO_ID = '" + periodoId + "'");

		// Plazas básicas total en los centros de costo
		HashMap<String, String> totalPlazas = becPlazasDao.getBecPlazasDepto(ejercicioId, periodoId);
		HashMap<String, String> totalPlazasB = becPlazasDao.getBecPlazasBasicas(ejercicioId, periodoId);
		HashMap<String, String> totalPlazasT = becPlazasDao.getBecPlazasTemporales(ejercicioId, periodoId);
		HashMap<String, String> totalPlazasI = becPlazasDao.getBecPlazasIndustriales(ejercicioId, periodoId);
		HashMap<String, String> totalPlazasP = becPlazasDao.getBecPlazasPreindustriales(ejercicioId, periodoId);
		HashMap<String, String> totalPlazasM = becPlazasDao.getBecPlazasPosgrado(ejercicioId, periodoId);
		HashMap<String, String> mapJefes = contCcostoDao.mapaJefes(ejercicioId);
		HashMap<String, BecPlazas> mapaPlazas = becPlazasDao.getBecPlazas(ejercicioId, periodoId);

		String cCostoId = "-";
		for (ContCcosto depto : listCostos) {
			cCostoId += depto.getIdCcosto();
		}
		HashMap<String, String> plazasAsignadas = becPlazasDao.mapaPlazasAsignadas(ejercicioId, periodoId);		

		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("listEjercicios", listEjercicios);
		modelo.addAttribute("listCostos", listCostos);
		modelo.addAttribute("totalPlazas", totalPlazas);
		modelo.addAttribute("totalPlazasB", totalPlazasB);
		modelo.addAttribute("totalPlazasT", totalPlazasT);
		modelo.addAttribute("totalPlazasI", totalPlazasI);
		modelo.addAttribute("totalPlazasP", totalPlazasP);
		modelo.addAttribute("totalPlazasM", totalPlazasM);
		modelo.addAttribute("mapJefes", mapJefes);
		modelo.addAttribute("plazasAsignadas", plazasAsignadas);		
		modelo.addAttribute("periodos", periodos);
		modelo.addAttribute("mapaPlazas", mapaPlazas);
		modelo.addAttribute("SubMenu", "13");

		return "portales/alumno/plazas";
	}
	
	@RequestMapping("/portales/alumno/plazasDepto")
	public String portalesAlumnoPlazasDepto(HttpServletRequest request, Model modelo){		
		
		String ejercicioId 		= request.getParameter("EjercicioId") == null?"0":request.getParameter("EjercicioId");
		String periodoId 		= request.getParameter("PeriodoId") == null?"0":request.getParameter("PeriodoId");
		String deptoId			= request.getParameter("DeptoId")==null?"0":request.getParameter("DeptoId");
		String deptoNombre 		= contCcostoDao.getNombre(ejercicioId, deptoId);
		
		List<BecPuesto> lisPuestos 						= becPuestoDao.lisPuestosPorDepto(periodoId, deptoId, " ORDER BY CATEGORIA_ID");
		List<BecPuestoAlumno> lisAlumnos 				= becPuestoAlumnoDao.lisPorPeriodoYDepartamento(periodoId, deptoId, "ORDER BY CATEGORIA_ID");
		HashMap<String, BecCategoria> mapaCategorias 	= becCategoriaDao.mapaCategorias();
		HashMap<String, String> mapaAlumnos 			= alumPersonalDao.mapaAlumnosConBeca(periodoId,"MUYCORTO");
		HashMap<String, String> mapaPuestos 			= becPuestoAlumnoDao.mapaPorPeriodoyDepto(periodoId);
		
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("deptoId", deptoId);
		modelo.addAttribute("deptoNombre", deptoNombre);
		modelo.addAttribute("lisPuestos", lisPuestos);
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("mapaCategorias", mapaCategorias);
		modelo.addAttribute("mapaCategorias", mapaCategorias);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("mapaPuestos", mapaPuestos);
		modelo.addAttribute("SubMenu", "13");
		
		return "portales/alumno/plazasDepto";
	}
	
	@RequestMapping("/portales/alumno/experiencias")
	public String portalesAlumnoExperiencias(HttpServletRequest request, Model modelo){
		
		String ejercicioId 		= contEjercicioDao.getEjercicioActual("001");		
		String planId			= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String codigoAlumno		= "0";		 
		String tipo				= "0";
		String tipoNombre 		= "-";	
		String carreraId		= "0";
		String carreraNombre	= "-";
		String nombreAlumno		= "-";
		
		HttpSession sesion = request.getSession();
		if (sesion != null) {
			codigoAlumno 	= (String) sesion.getAttribute("codigoAlumno");
			nombreAlumno = alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");			
			if (planId.equals("0")) {
				planId 		= alumPlanDao.getPlanActual(codigoAlumno);
			}
			if (alumAcademicoDao.existeReg(codigoAlumno)) {
				tipo 		= alumAcademicoDao.getTipoAlumnoId(codigoAlumno);
				tipoNombre 	= catTipoAlumnoDao.getNombreTipo(tipo);
				carreraId	= mapaPlanDao.getCarreraId(planId);
			}						
			carreraNombre 	= mapaPlanDao.getCarreraSe(planId);
		}
		
		List<BecTipo> lisTipos 					= becTipoDao.lisPorTipoyCarrera(ejercicioId, tipo, carreraId, " ORDER BY TIPO");
		List<AlumPlan> lisPlanes 				= alumPlanDao.lisPlanesAlumno(codigoAlumno, "ORDER BY PLAN_ID");
		HashMap<String,String> mapaPlanes		= mapaPlanDao.mapaCarreraOficial();
		
		modelo.addAttribute("SubMenu", "13");
		modelo.addAttribute("planId", planId);		
		modelo.addAttribute("carreraNombre", carreraNombre);
		modelo.addAttribute("tipo", tipo);		
		modelo.addAttribute("tipoNombre", tipoNombre);
		modelo.addAttribute("lisTipos", lisTipos);
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("codigoAlumno", codigoAlumno);

		
		return "portales/alumno/experiencias";
	}
	

	@RequestMapping("/portales/alumno/seguroAccidente")
	public String portalesAlumnoSeguroVida(HttpServletRequest request, Model modelo) {
		String matricula = "0";
		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
		}

		AlumPersonal alumno = alumPersonalDao.mapeaRegId(matricula);

		modelo.addAttribute("alumno", alumno);
		modelo.addAttribute("fecha", Fecha.getFechaHoy());

		return "portales/alumno/seguroAccidente";
	}

	@RequestMapping("/portales/alumno/educacionGarantizada")
	public String portalesAlumnoEducacionGarantizada(HttpServletRequest request, Model modelo) {
		String matricula = "0";
		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
		}

		AlumPersonal alumPersonal = alumPersonalDao.mapeaRegId(matricula);
		ResDatos resDatos = resDatosDao.mapeaRegId(matricula);
		AlumPlan alumPlan = alumPlanDao.mapeaRegId(matricula);
		AlumAcademico alumAcademico = alumAcademicoDao.mapeaRegId(matricula);

		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("resDatos", resDatos);
		modelo.addAttribute("alumPlan", alumPlan);
		modelo.addAttribute("alumAcademico", alumAcademico);

		return "portales/alumno/educacionGarantizada";
	}

	@RequestMapping("/portales/alumno/ponderado")
	public String portalesAlumnoPonderado(HttpServletRequest request, Model modelo) {
		enviarConEnoc(request, "Error-aca.ContPortalesAlumno|portalesAlumnoPonderado");
		return "portales/alumno/ponderado";
	}

	@RequestMapping("/portales/alumno/ponderadof")
	public String portalesAlumnoPonderadof(HttpServletRequest request, Model modelo) {
		enviarConEnoc(request, "Error-aca.ContPortalesAlumno|portalesAlumnoPonderadof");
		return "portales/alumno/ponderadof";
	}

	@RequestMapping("/portales/alumno/referencias")
	public String portalesAlumnoReferencias(HttpServletRequest request, Model modelo) {

		String planId = request.getParameter("PlanId") == null ? "0" : request.getParameter("PlanId");
		String matricula = "0";
		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
		}

		// Lista de planes (No mover de ésta poscición)
		List<AlumPlan> lisPlanes = alumPlanDao.lisPlanesAlumno(matricula, " ORDER BY ENOC.ALUM_PLAN.F_INICIO DESC");

		AlumPlan alumPlan = alumPlanDao.mapeaRegId(matricula);
		boolean existePlan = false;
		for (AlumPlan plan : lisPlanes) {
			if (plan.getPlanId().equals(planId)) {
				existePlan = true;
				break;
			}
		}
		if (planId.equals("0") || !existePlan) {
			planId = alumPlan.getPlanId();
		}

		String carrera = mapaPlanDao.getCarreraId(planId);
		MapaPlan mapaPlan = mapaPlanDao.mapeaRegId(planId);
		// String cuenta = "";
		// if (!carrera.equals("10209") && !carrera.equals("10210")) {
		// 	if (mapaPlan.getOficial().equals("N") || mapaPlan.getOficial().equals("S"))
		// 		cuenta = "1";
		// 	if (mapaPlan.getOficial().equals("I"))
		// 		cuenta = "2";
		// 	if (mapaPlan.getOficial().equals("C"))
		// 		cuenta = "3";
		// 	if (mapaPlan.getOficial().equals("R"))
		// 		cuenta = "4";
		// 	if (mapaPlan.getOficial().equals("A"))
		// 		cuenta = "5";
		// }

		// String digito = alumReferenciaDao.generarReferenciaSantander(cuenta + matricula);
		String nombre = usuariosDao.getNombreUsuario(matricula, "NOMBRE");
		String numCuenta = "";
		// boolean tieneScotiabank = false;
		// boolean tieneSantander = false;
		// boolean tieneBanorte = false;
		// boolean esCovoprom = false;

		String institucion = parametrosDao.mapeaRegId("1").getInstitucion();

		// AlumReferencia alumno = new AlumReferencia();
		// alumno.setCodigoPersonal(cuenta + matricula);
		// if (alumReferenciaDao.existeReg(cuenta + matricula)) {

		// 	alumno = alumReferenciaDao.mapeaRegId(cuenta + matricula);
		// 	tieneScotiabank = alumno.getScotiabank() != null && !alumno.getScotiabank().equals("-");
		// 	tieneSantander = alumno.getSantander() != null && !alumno.getSantander().equals("-");
		// 	// Es la misma referencia de Scotiabank
		// 	tieneBanorte = alumno.getScotiabank() != null && !alumno.getScotiabank().equals("-");

		// 	if (!tieneScotiabank || !tieneSantander) {
		// 		// Se actualiza el que no exista
		// 		if (!tieneScotiabank)
		// 			alumno.setScotiabank(digito);
		// 		if (!tieneSantander)
		// 			alumno.setSantander(digito);
		// 		if (alumReferenciaDao.updateReg(alumno)) {
		// 			tieneScotiabank = true;
		// 			tieneSantander = true;
		// 			tieneBanorte = true;
		// 		}
		// 	}
		// } else {
		// 	alumno.setBanamex("-");
		// 	alumno.setScotiabank(digito);
		// 	alumno.setSantander(digito);
		// 	if (alumReferenciaDao.insertReg(alumno)) {
		// 		// System.out.println("Inserte el digito verificador"+matricula+":"+digito);
		// 		tieneScotiabank = true;
		// 		tieneSantander = true;
		// 		tieneBanorte = true;
		// 	}
		// }

		// if (carrera.equals("10209") || carrera.equals("10210")) {
		// 	tieneSantander = false;
		// 	tieneBanorte = false;
		// 	esCovoprom = true;
		// 	numCuenta = "25300065609";
		// } else {
		// 	tieneSantander = true;
		// 	numCuenta = "25300000361";
		// }

		HashMap<String, MapaPlan> mapaPlanes = mapaPlanDao.mapPlanes("'V','I','A'");

		modelo.addAttribute("SubMenu", "5");
		// modelo.addAttribute("cuenta", cuenta);
		// modelo.addAttribute("digito", digito);
		modelo.addAttribute("nombre", nombre);
		modelo.addAttribute("numCuenta", numCuenta);
		// modelo.addAttribute("tieneScotiabank", tieneScotiabank);
		// modelo.addAttribute("tieneSantander", tieneSantander);
		// modelo.addAttribute("tieneBanorte", tieneBanorte);
		// modelo.addAttribute("esCovoprom", esCovoprom);
		modelo.addAttribute("institucion", institucion);
		// modelo.addAttribute("alumno", alumno);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("mapaPlanes", mapaPlanes);

		return "portales/alumno/referencias";
	}

	@RequestMapping("/portales/alumno/grupos")
	public String portalesAlumnoGrupos(HttpServletRequest request, Model modelo){
		String matricula 	= "0";
		String cargaId 		= request.getParameter("CargaId") == null ? "-" : request.getParameter("CargaId");
		String cursoCargaId = request.getParameter("CursoCargaId") == null ? "0" : request.getParameter("CursoCargaId");

		HttpSession sesion 	= request.getSession();
		if (sesion != null) {
			matricula 		= (String) sesion.getAttribute("codigoAlumno");
			if (cursoCargaId.equals("0")) {
				cursoCargaId = (String)sesion.getAttribute("cursoCargaId");
			}else {
				sesion.setAttribute("cursoCargaId", cursoCargaId);
			}		
		}	

		// Clave de la materia (7 digitos) que inscribio el alumno
		String cursoId 		= request.getParameter("CursoId") == null ? "-" : request.getParameter("CursoId");
		String cursoOrigen 	= cargaGrupoCursoDao.cursoIdOrigen(cursoCargaId); 
		String materia		= mapaCursoDao.getNombreCurso(cursoOrigen);
		String accion 		= request.getParameter("accion") == null ? "0" : request.getParameter("accion");
		String grupoId 		= request.getParameter("grupo") == null ? "0" : request.getParameter("grupo");
		String fechaHoy 	= aca.util.Fecha.getHoy();

		boolean tieneGrupo = apFisicaAlumnoDao.tieneGrupo(matricula, cursoCargaId);

		ApFisicaGrupo grupoAp = new ApFisicaGrupo();
		ApFisicaAlumno apFisicaAlumno = new ApFisicaAlumno();
		
		String sexoAlumno = alumPersonalDao.getGenero(matricula).equals("M")?"'M','T'":"'F','T'";

		List<ApFisicaGrupo> lisGrupos = null;
		if (tieneGrupo) {
			lisGrupos = apFisicaGrupoDao.getGruposAlumnos(cargaId, cursoId, "'T'", sexoAlumno, " ORDER BY GRUPO_ID");
		} else {
			lisGrupos = apFisicaGrupoDao.getGruposAlumnos(cargaId, cursoId, "'N','T'", sexoAlumno," ORDER BY GRUPO_ID");
		}

		HashMap<String, String> mapaActivos = apFisicaGrupoDao.mapaActivos(cargaId);

		int accionFmt = 0;

		if (accion.equals("0")) {
			// Busca el grupo que el alumno registró en el sistema
			grupoId = apFisicaAlumnoDao.grupoDelAlumno(matricula, cursoCargaId);
			if (apFisicaGrupoDao.existeReg(grupoId)) {
				grupoAp = apFisicaGrupoDao.mapeaRegId(grupoId);
			}
			if (apFisicaAlumnoDao.existeReg(matricula, grupoId)) {
				apFisicaAlumno = apFisicaAlumnoDao.mapeaRegId(matricula, grupoId);
			}
		} else if (accion.equals("2")) {

			grupoAp = apFisicaGrupoDao.mapeaRegId(grupoId);

			int registrados = apFisicaAlumnoDao.registrados(grupoId);
			int faltantes = Integer.parseInt(grupoAp.getCupo()) - registrados;

			if (faltantes > 0) {
				// Inactiva el estado de los grupos registrados por el alumno con esta clave de
				// materia
				apFisicaAlumnoDao.updateQuitarGrupo(matricula, cursoId);

				apFisicaAlumno.setCodigoPersonal(matricula);
				apFisicaAlumno.setGrupoId(grupoId);
				apFisicaAlumno.setCursoCargaId(cursoCargaId);

				if (apFisicaAlumnoDao.existeReg(matricula, grupoId)) {
					apFisicaAlumno.setEstado("A");
					apFisicaAlumno.setFecha(fechaHoy);
					if (apFisicaAlumnoDao.updateReg(apFisicaAlumno)) {
						accionFmt = 3;
					} else {
						accionFmt = 4;
					}

				} else {
					apFisicaAlumno.setEstado("A");
					apFisicaAlumno.setFecha(fechaHoy);
					apFisicaAlumno.setCursoCargaId(cursoCargaId);
					if (apFisicaAlumnoDao.insertReg(apFisicaAlumno)) {
						accionFmt = 1;
					} else {
						accionFmt = 2;
					}
				}
			} else {
				accionFmt = 7;
			}
		}

		HashMap<String, Integer> mapaGrupoRegistrados = new HashMap<String, Integer>();

		for (ApFisicaGrupo grupo : lisGrupos) {
			int registrados = apFisicaAlumnoDao.registrados(grupo.getGrupoId());
			mapaGrupoRegistrados.put(grupo.getGrupoId(), registrados);
		}
		
		modelo.addAttribute("SubMenu", "3");
		modelo.addAttribute("materia", materia);
		modelo.addAttribute("apFisicaAlumno", apFisicaAlumno);
		modelo.addAttribute("grupoAp", grupoAp);
		modelo.addAttribute("tieneGrupo", tieneGrupo);
		modelo.addAttribute("accionFmt", accionFmt);
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("cursoId", cursoId);
		modelo.addAttribute("grupoId", grupoId);
		modelo.addAttribute("accion", accion);
		modelo.addAttribute("lisGrupos", lisGrupos);
		modelo.addAttribute("mapaActivos", mapaActivos);
		modelo.addAttribute("mapaGrupoRegistrados", mapaGrupoRegistrados);

		return "portales/alumno/grupos";
	}

	@RequestMapping("/portales/alumno/reglamento")
	public String portalesAlumnoReglamento(HttpServletRequest request, Model modelo) {
		return "portales/alumno/reglamento";
	}

	@RequestMapping("/portales/alumno/requisitos")
	public String portalesAlumnoRequisitos(HttpServletRequest request, Model modelo) {

		List<SsocRequisito> lisRequisitos = sscoRequisitoDao.getListAll(" ORDER BY ORDEN");

		modelo.addAttribute("lisRequisitos", lisRequisitos);

		return "portales/alumno/requisitos";
	}

	@RequestMapping("/portales/alumno/reservados")
	public String portalesAlumnoReservados(HttpServletRequest request, Model modelo) {
		return "portales/alumno/reservados";
	}

	@RequestMapping("/portales/alumno/sepAlumno")
	public String portalesAlumnoSepAlumno(HttpServletRequest request, Model modelo) {
		String mensaje = request.getParameter("Mensaje") == null ? "0" : request.getParameter("Mensaje");

		String codigoPersonal = "0";

		String matricula = "0";
		HttpSession sesion = request.getSession();
		if (sesion != null) {
			codigoPersonal = (String) sesion.getAttribute("codigoPersonal");
			matricula = (String) sesion.getAttribute("codigoAlumno");
		}

		Acceso acceso = accesoDao.mapeaRegId(codigoPersonal);

		AlumPersonal alumPersonal = alumPersonalDao.mapeaRegId(matricula);

		List<SepAlumno> listSep = sepAlumnoDao.listAlumno(matricula);

		List<String> listaFechas = sepAlumnoDao.ultimasDosFechasSepAlumno();

		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("listSep", listSep);
		modelo.addAttribute("listaFechas", listaFechas);

		return "portales/alumno/sepAlumno";
	}

	@RequestMapping("/portales/alumno/modificarCiclo")
	public String portalesAlumnoModificarCiclo(HttpServletRequest request) {
		String fecha = request.getParameter("Fecha") == null ? "0" : request.getParameter("Fecha");
		String mensaje = "0";
		if (alumPlanDao.editarCiclo(fecha)) {
			mensaje = "1";
		}

		return "redirect:/portales/alumno/sepAlumno?Mensaje=" + mensaje;
	}

	@RequestMapping("/portales/alumno/sepAlumnoEditar")
	public String portalesAlumnoSepAlumnoEditar(HttpServletRequest request, Model modelo) {
		String folio = request.getParameter("folio") == null ? "0" : request.getParameter("folio");
		String fecha = "";

		SepAlumno alumno = sepAlumnoDao.mapeaRegId(folio);

		fecha = alumno.getFecha();
		fecha = fecha.substring(0, 10);

		String[] array = fecha.split("-");

		fecha = array[2] + "/" + array[1] + "/" + array[0];

		alumno.setFecha(fecha);

		modelo.addAttribute("listaPais", catPaisDao.getListAll(""));
		modelo.addAttribute("listaEstado", catEstadoDao.getLista(alumno.getPaisId(), ""));
		modelo.addAttribute("alumno", alumno);

		return "portales/alumno/sepAlumnoEditar";
	}

	@RequestMapping("/portales/alumno/getEstados")
	@ResponseBody
	public String portalesAlumnoGetestados(HttpServletRequest request) {
		String paisId = request.getParameter("paisId") == null ? "" : request.getParameter("paisId");
		String mensaje = "";
		List<CatEstado> lisEstados = catEstadoDao.getLista(paisId, "ORDER BY 1,3");
		for (CatEstado edo : lisEstados) {
			mensaje += "<option value='" + edo.getEstadoId() + "'>" + edo.getNombreEstado() + "</option>";
		}
		return mensaje;
	}

	@RequestMapping("/portales/alumno/sepAlumnoGrabar")
	public String portalesAlumnoSepAlumnoGrabar(HttpServletRequest request) {

		String folio = request.getParameter("folio") == null ? "0" : request.getParameter("folio");
		String plantel = request.getParameter("plantel") == null ? "0" : request.getParameter("plantel");
		String planSep = request.getParameter("planSep") == null ? "0" : request.getParameter("planSep");
		String ciclo = request.getParameter("ciclo") == null ? "0" : request.getParameter("ciclo");
		String curp = request.getParameter("curp") == null ? "0" : request.getParameter("curp");
		String nombre = request.getParameter("nombre") == null ? "0" : request.getParameter("nombre");
		String paterno = request.getParameter("paterno") == null ? "0" : request.getParameter("paterno");
		String materno = request.getParameter("materno") == null ? "0" : request.getParameter("materno");
		String fecha = request.getParameter("fecha") == null ? "0" : request.getParameter("fecha");
		String codigoPersonal = request.getParameter("codigoPersonal") == null ? "0"
				: request.getParameter("codigoPersonal");
		String planUm = request.getParameter("planUm") == null ? "0" : request.getParameter("planUm");
		String genero = request.getParameter("genero") == null ? "0" : request.getParameter("genero");
		String edad = request.getParameter("edad") == null ? "0" : request.getParameter("edad");
		String grado = request.getParameter("grado") == null ? "0" : request.getParameter("grado");
		String paisId = request.getParameter("paisId") == null ? "0" : request.getParameter("paisId");
		String estadoId = request.getParameter("estadoId") == null ? "0" : request.getParameter("estadoId");
		String prepaLugar = request.getParameter("prepaLugar") == null ? "0" : request.getParameter("prepaLugar");
		String usado = request.getParameter("usado") == null ? "0" : request.getParameter("usado");

		SepAlumno alumno = new SepAlumno();

		alumno.setFolio(folio);
		alumno.setPlantel(plantel);
		alumno.setPlanSep(planSep);
		alumno.setCiclo(ciclo);
		alumno.setCurp(curp);
		alumno.setNombre(nombre);
		alumno.setPaterno(paterno);
		alumno.setMaterno(materno);
		alumno.setFecha(fecha);
		alumno.setCodigoPersonal(codigoPersonal);	
		alumno.setPlanUm(planUm);
		alumno.setGenero(genero);
		alumno.setEdad(edad);
		alumno.setGrado(grado);
		alumno.setPaisId(paisId);
		alumno.setEstadoId(estadoId);
		alumno.setPrepaLugar(prepaLugar);
		alumno.setUsado(usado);

		sepAlumnoDao.updateReg(alumno);

		return "redirect:/portales/alumno/sepAlumno";
	}

	@RequestMapping("/portales/alumno/subir")
	public String portalesAlumnoSubir(HttpServletRequest request, Model modelo) {
		enviarConEnoc(request, "Error-aca.ContPortalesAlumno|portalesAlumnoSubir");
		return "portales/alumno/subir";
	}

	@RequestMapping("/portales/alumno/tareas")
	public String portalesAlumnoTareas(HttpServletRequest request, Model modelo) {
		enviarConEnoc(request, "Error-aca.ContPortalesAlumno|portalesAlumnoTareas");
		return "portales/alumno/tareas";
	}

	@RequestMapping("/portales/alumno/tit_requisitos")
	public String portalesAlumnoTitRequisitos(HttpServletRequest request, Model modelo) {
		enviarConEnoc(request, "Error-aca.ContPortalesAlumno|portalesAlumnoTitRequisitos");
		return "portales/alumno/tit_requisitos";
	}

	@RequestMapping("/portales/alumno/titulacion")
	public String portalesAlumnoTitulacion(HttpServletRequest request, Model modelo) {
		enviarConEnoc(request, "Error-aca.ContPortalesAlumno|portalesAlumnoTitulacion");
		return "portales/alumno/titulacion";
	}

	@RequestMapping("/portales/alumno/upload")
	public String portalesAlumnoUpload(HttpServletRequest request, Model modelo) {
		return "portales/alumno/upload";
	}

	@RequestMapping("/portales/alumno/validareferencia")
	public String portalesAlumnoValidaReferencia(HttpServletRequest request, Model modelo) {
		enviarConEnoc(request, "Error-aca.ContPortalesAlumno|portalesAlumnoValidaReferencia");
		return "portales/alumno/validareferencia";
	}

	@RequestMapping("/portales/alumno/verificaArchivo")
	public String portalesAlumnoVerificaArchivo(HttpServletRequest request, Model modelo) {
		return "portales/alumno/verificaArchivo";
	}

	@RequestMapping("/portales/alumno/votoGradua")
	public String portalesAlumnoVotoGradua(HttpServletRequest request, Model modelo) {
		enviarConEnoc(request, "Error-aca.ContPortalesAlumno|portalesAlumnoVotoGradua");
		return "portales/alumno/votoGradua";
	}

	@RequestMapping("/portales/alumno/promedio")
	public String portalesAlumnoPromedio(HttpServletRequest request, Model modelo) {
		return "portales/alumno/promedio";
	}

	@RequestMapping("/portales/alumno/promedios")
	public String portalesAlumnoPromedios(HttpServletRequest request, Model modelo) {

		String codigoAlumno = "0";
		String alumnoNombre = "-";
		HttpSession sesion = request.getSession();
		if (sesion != null) {
			codigoAlumno = (String) sesion.getAttribute("codigoAlumno");
			alumnoNombre = alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
		}

		List<BecInformeAlumno> lisInformes = becInformeAlumnoDao.alumnosPromedio(codigoAlumno,
				" ORDER BY ENOC.BEC_INFORME_ALUMNO.FECHA DESC");
		HashMap<String, BecInforme> mapaInformes = becInformeDao.mapaInformes();

		modelo.addAttribute("alumnoNombre", alumnoNombre);
		modelo.addAttribute("lisInformes", lisInformes);
		modelo.addAttribute("mapaInformes", mapaInformes);

		return "portales/alumno/promedios";
	}

	@RequestMapping("/portales/alumno/promediof")
	public String portalesAlumnoPromediof(HttpServletRequest request, Model modelo) {
		// enviarConEnoc(request,
		// "Error-aca.ContPortalesAlumno|portalesAlumnoPromediof");
		String codigoAlumno = "0";
		String planId = request.getParameter("PlanId") == null ? "0" : request.getParameter("PlanId");
		String alumnoNombre = "-";

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			codigoAlumno = (String) sesion.getAttribute("codigoAlumno");
			alumnoNombre = alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
		}

		AlumPlan alumPlan = new AlumPlan();
		if (planId.equals("0")) {
			alumPlan = alumPlanDao.mapeaRegId(codigoAlumno);
			planId = alumPlan.getPlanId();
		} else {
			alumPlan = alumPlanDao.mapeaRegId(codigoAlumno, planId);
			planId = alumPlan.getPlanId();
		}
		Parametros parametros = parametrosDao.mapeaRegId("1");
		String carreraNombre = mapaPlanDao.getCarreraSe(planId);
		boolean cancelaEstudio = cancelaEstudioDao.existeReg(codigoAlumno, planId);
		String cancelaComentario = "-";
		if (cancelaEstudio) {
			cancelaComentario = cancelaEstudioDao.mapeaRegId(codigoAlumno, planId).getComentario();
		}

		int totalMaterias = mapaCursoDao.totalMateriasObligatoriasDos(planId, "S");
		int totalObligatorios = mapaCursoDao.getNumCredPlanObligatorio(planId, "S");
		int totalCreditos = Integer.parseInt(mapaAvanceDao.getCreditosPlan(planId).trim());

		List<String> lisPlanes = alumPlanDao.getPlanesAlumno(codigoAlumno);
		List<AlumnoCurso> lisCursosAlumno = alumnoCursoDao.getListAlumno(codigoAlumno,
				" AND PLAN_ID='" + planId + "' AND TIPOCAL_ID IN ('1') AND CREDITOS !=0 ORDER BY CICLO, NOMBRE_CURSO");
		List<MapaCurso> lisCursosCarrera = mapaCursoDao.getListPlan(planId, " ORDER BY CICLO, NOMBRE_CURSO");

		HashMap<String, MapaPlan> mapaPlanes = mapaPlanDao.mapPlanes("'A','V','I'");
		HashMap<String, MapaCurso> mapaCursos = mapaCursoDao.mapaCursosPlan(planId);
		HashMap<String, MapaCredito> mapaCreditos = mapaCreditoDao.mapaCredito(planId);

		modelo.addAttribute("planId", planId);
		modelo.addAttribute("alumPlan", alumPlan);
		modelo.addAttribute("parametros", parametros);
		modelo.addAttribute("alumnoNombre", alumnoNombre);
		modelo.addAttribute("carreraNombre", carreraNombre);
		modelo.addAttribute("cancelaEstudio", cancelaEstudio);
		modelo.addAttribute("cancelaComentario", cancelaComentario);
		modelo.addAttribute("totalMaterias", totalMaterias);
		modelo.addAttribute("totalObligatorios", totalObligatorios);
		modelo.addAttribute("totalCreditos", totalCreditos);

		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("lisCursosCarrera", lisCursosCarrera);
		modelo.addAttribute("lisCursosAlumno", lisCursosAlumno);
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		modelo.addAttribute("mapaCursos", mapaCursos);
		modelo.addAttribute("mapaCreditos", mapaCreditos);

		return "portales/alumno/promediof";
	}

	@RequestMapping("/portales/alumno/filesMaestro")
	public String portalesMaestroFilesMaestro(HttpServletRequest request, Model modelo) {

		String cursoCargaId = request.getParameter("CursoCargaId") == null ? "0" : request.getParameter("CursoCargaId");
		String cursoId = cargaGrupoCursoDao.cursoIdOrigen(cursoCargaId);
		String materiaNombre = mapaCursoDao.getNombreCurso(cursoId);

		List<ArchivosProfesor> lisArchivos = archivosProfesorDao.listaArchivosMateria(cursoCargaId);

		modelo.addAttribute("materiaNombre", materiaNombre);
		modelo.addAttribute("cursoCargaId", cursoCargaId);
		modelo.addAttribute("lisArchivos", lisArchivos);

		return "portales/alumno/filesMaestro";
	}

	@RequestMapping("/portales/alumno/validacion")
	public String portalesInscEnLineaValidacion(HttpServletRequest request, Model modelo) {

		String matricula = "0";
		String year = aca.util.Fecha.getHoy().substring(6, 10);
		AyudaEstudios ayuda = new AyudaEstudios();
		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
		}

		AlumPersonal alumPersonal = alumPersonalDao.mapeaRegId(matricula);
		AlumAcademico alumAcademico = alumAcademicoDao.mapeaRegId(matricula);
		AlumUbicacion alumUbicacion = alumUbicacionDao.mapeaRegId(matricula);
		
		AlumPlan alumPlan = alumPlanDao.mapeaRegId(matricula);
		String planNombre = mapaPlanDao.getNombrePlan(alumPlan.getPlanId());
		
//		String razonId = resDatosDao.getRazon(matricula);
//		String razon = resRazonDao.mapeaRegId(razonId).getDescripcion();
		
		String nombreNacionalidad = catPaisDao.getNacionalidad(alumPersonal.getNacionalidad());
		String fechaVencimiento = alumPersonalDao.getVencimientoFM3(alumPersonal.getCodigoPersonal());
		String nombreInstitucion = catInstitucionDao.getNombreInstitucion(alumAcademico.getObreroInstitucion());
		String nombreTipo = catTipoAlumnoDao.getNombreTipo(alumAcademico.getTipoAlumno());
		String clasFin = alumAcademicoDao.getClasFinAlumno(matricula);
		String religionNombre = catReligionDao.getNombreReligion(alumPersonal.getReligionId());
		boolean extranjero = alumPersonalDao.esExtranjero(matricula);

//		int becaHijoUM = 0;
//		if (alumAcademico.getTipoAlumno().equals("9")) {
//			becaHijoUM = ayudaEstudiosDao.becasRegistradas(matricula, year, "A");
//			if (becaHijoUM == 1)
//				ayuda = ayudaEstudiosDao.mapeaRegId(matricula, "A");
//		}

		HashMap<String, CatCarrera> mapaCarreras = catCarreraDao.getMapAll("");
		HashMap<String, CatNivel> mapaNiveles = catNivelDao.getMapAll("");
		HashMap<String, CatModalidad> mapaModalidades = catModalidadDao.getMapAll("");
		HashMap<String, IntDormitorio> mapaDormitorios = intDormitorioDao.getMapAll("");
		HashMap<String, CatReligion> mapaReligiones = catReligionDao.getMapAll("");
		HashMap<String, CatCultural> mapaCultural = catCulturalDao.getMapAll("");
		HashMap<String, CatRegion> mapaRegion = catRegionDao.getMapAll("");
		HashMap<String, CatPais> mapaPaises = catPaisDao.getMapAll();
		HashMap<String, CatEstado> mapaEstados = catEstadoDao.getMapAll();
		HashMap<String, CatCiudad> mapaCiudades = catCiudadDao.getMapAll("");
		

		modelo.addAttribute("SubMenu", "13");
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("alumAcademico", alumAcademico);
		modelo.addAttribute("alumUbicacion", alumUbicacion);
		modelo.addAttribute("alumPlan", alumPlan);
		modelo.addAttribute("ayuda", ayuda);
//		modelo.addAttribute("becaHijoUM", becaHijoUM);
		modelo.addAttribute("nombreNacionalidad", nombreNacionalidad);
		modelo.addAttribute("fechaVencimiento", fechaVencimiento);
		modelo.addAttribute("nombreInstitucion", nombreInstitucion);
		modelo.addAttribute("nombreTipo", nombreTipo);
		modelo.addAttribute("clasFin", clasFin);
		modelo.addAttribute("extranjero", extranjero);
//		modelo.addAttribute("razon", razon);
		modelo.addAttribute("religionNombre", religionNombre);
		modelo.addAttribute("planNombre", planNombre);

		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaNiveles", mapaNiveles);
		modelo.addAttribute("mapaModalidades", mapaModalidades);
		modelo.addAttribute("mapaDormitorios", mapaDormitorios);
		modelo.addAttribute("mapaReligiones", mapaReligiones);
		modelo.addAttribute("mapaCultural", mapaCultural);
		modelo.addAttribute("mapaRegion", mapaRegion);
		modelo.addAttribute("mapaPaises", mapaPaises);
		modelo.addAttribute("mapaEstados", mapaEstados);
		modelo.addAttribute("mapaCiudades", mapaCiudades);

		return "portales/alumno/validacion";
	}

	@RequestMapping("/portales/alumno/pasos")
	public String portalesAlumnoPasos(HttpServletRequest request, Model modelo) {

		String matricula 		= "0";
		String codigoPersonal 	= "0";
		String esEnLinea 		= "N";
		String modalidadAlumno 	= "0";
		String planId 			= request.getParameter("PlanId") == null ? "0" : request.getParameter("PlanId");
		String bloqueSesion 	= "0";
		
		HttpSession sesion 	= request.getSession();
		if (sesion != null) {
			matricula 			= (String) sesion.getAttribute("codigoAlumno");
			codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");
			modalidadAlumno 	= alumAcademicoDao.getModalidadId(matricula);		
			esEnLinea			= catModalidadDao.getEnLinea(modalidadAlumno);
			bloqueSesion 		= (String) sesion.getAttribute("bloqueId");
		}
		
		AlumPersonal alumPersonal 			= alumPersonalDao.mapeaRegId(matricula);
		AlumPlan alumPlan 					= alumPlanDao.mapeaRegId(matricula);
		// Lista de planes (No mover de esta poscición)
		List<AlumPlan> lisPlanes 			= alumPlanDao.lisPlanesAlumno(matricula, " ORDER BY ENOC.ALUM_PLAN.F_INICIO DESC");
		
		// NOT USED
		// List<aca.Mapa> lisPagosPendientes 	= fesCcobroDao.lisPagosPendientes(matricula);
		// int pagosPendientes = 0;
		// for (aca.Mapa pagos : lisPagosPendientes) {
		// 	float pagoInicial = Float.valueOf(pagos.getValor());
		// 	if (pagoInicial < 0)
		// 		pagosPendientes++;
		// }

		boolean existePlan = false;
		for (AlumPlan plan : lisPlanes) {
			if (plan.getPlanId().equals(planId)) {
				existePlan = true;
				break;
			}
		}
		if (planId.equals("0") || !existePlan) {
			planId = alumPlan.getPlanId();
		} else {
			alumPlan = alumPlanDao.mapeaRegId(matricula, planId);
		}
		String planNombre 	= mapaPlanDao.getNombrePlan(planId);
		String planOficial		= mapaPlanDao.getOficial(planId);
		
		int pendientesConfirmar		= cargaAlumnoDao.pendientesDeConfirmar(matricula);
		
		// NOT USED
		boolean tieneNse		= nseRespuestaDao.encuestaPendiente(matricula);
		boolean tieneVacuna		= alumUbicacionDao.tieneVacuna(matricula);
		// Si no es alumno presencial no se pide las vacunas ni la encuesta
		if (!modalidadAlumno.equals("1") && !modalidadAlumno.equals("4") && !planOficial.equals("S")){
			tieneNse 		= true;
			tieneVacuna 	= true;
		} 

		MapaPlan mapaPlan = mapaPlanDao.mapeaRegId(planId);
		CatCarrera carrera = catCarreraDao.mapeaRegId(mapaPlan.getCarreraId());
		String coordinadorNombre = maestrosDao.getNombreCorto(carrera.getCodigoPersonal(), "NOMBRE");
		CatFacultad facultad = catFacultadDao.mapeaRegId(carrera.getFacultadId());
		String directorNombre = maestrosDao.getNombreCorto(facultad.getCodigoPersonal(), "NOMBRE");
		String asesorId = "0";
		String asesorNombre = "-";
		boolean existeAsesor = false;
		if (alumAsesorDao.existeReg(matricula, planId)) {
			existeAsesor = true;
			asesorId = alumAsesorDao.mapeaRegId(matricula, planId).getAsesorId();
			asesorNombre = maestrosDao.getNombreCorto(asesorId, "NOMBRE");
		}

		String cargaId = (String) sesion.getAttribute("cargaId");
		String bloqueId = "0";

		List<CargaBloque> lisBloques = cargaBloqueDao.getListaAlumno(cargaId, matricula, " ORDER BY BLOQUE_ID");
		boolean existeBloque = false;
		for (CargaBloque bloq : lisBloques) {
			if (bloq.getBloqueId().equals(bloqueSesion)) {
				existeBloque = true;
				break;
			}
		}


		if (bloqueId.equals("0") && existeBloque) {
			bloqueId = bloqueSesion;
		} else if (bloqueId.equals("0") && lisBloques.size() >= 1) {
			bloqueId = lisBloques.get(0).getBloqueId();
			sesion.setAttribute("bloqueId", bloqueId);
		} else {
			bloqueId = cargaAlumnoDao.getMejorBloque(matricula, cargaId);
			sesion.setAttribute("bloqueId", bloqueId);
		}

		HashMap<String, MapaPlan> mapaPlanes = mapaPlanDao.mapPlanes("'V','I','A'");
		HashMap<String, EmpContacto> mapaContactos = empContactoDao.mapaContactos();

		CargaAlumno cargaAlumno = new CargaAlumno();
		boolean existeCarga = false;
		// System.out.println(codigoPersonal+" : "+cargaId+" : "+bloqueId);
		if(cargaAlumnoDao.existeReg(matricula, cargaId, bloqueId)){
			cargaAlumno = cargaAlumnoDao.mapeaRegId(matricula, cargaId, bloqueId);
			existeCarga = true;
		}

		CargaFinanciero cargaFin = new CargaFinanciero();
		// System.out.println(cargaId);
		if(cargaFinancieroDao.existeReg(matricula, cargaId, bloqueId)){
			cargaFin = cargaFinancieroDao.mapeaRegId(matricula, cargaId, bloqueId);
		}

		modelo.addAttribute("SubMenu", "13");
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("esEnLinea", esEnLinea);

		modelo.addAttribute("pendientesConfirmar", pendientesConfirmar);
//		modelo.addAttribute("pagosPendientes", pagosPendientes);
		modelo.addAttribute("alumPlan", alumPlan);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("existeAsesor", existeAsesor);
		modelo.addAttribute("facultad", facultad);
		modelo.addAttribute("carrera", carrera);
		modelo.addAttribute("coordinadorNombre", coordinadorNombre);
		modelo.addAttribute("directorNombre", directorNombre);
		modelo.addAttribute("asesorId", asesorId);
		modelo.addAttribute("asesorNombre", asesorNombre);
		modelo.addAttribute("planNombre", planNombre);
		modelo.addAttribute("cargaAlumno", cargaAlumno);
		modelo.addAttribute("existeCarga", existeCarga);
		modelo.addAttribute("cargaFin", cargaFin);
//		modelo.addAttribute("tieneNse", tieneNse);
//		modelo.addAttribute("tieneVacuna", tieneVacuna);				

		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		modelo.addAttribute("mapaContactos", mapaContactos);

		return "portales/alumno/pasos";
	}

	@RequestMapping("/portales/alumno/previos")
	public String portalesAlumnoPrevios(HttpServletRequest request, Model modelo){

		String matricula = "0";
		String modalidadAlumno = "0";
		String esEnLinea = "N";
		
		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
			modalidadAlumno 		= alumAcademicoDao.getModalidadId(matricula);		
			esEnLinea				= catModalidadDao.getEnLinea(modalidadAlumno);

		}

		AlumPersonal alumPersonal 	= alumPersonalDao.mapeaRegId(matricula);	
		String planId				= alumPlanDao.getPlanActual(matricula);
		String planOficial			= mapaPlanDao.getOficial(planId);
		boolean tieneNse			= nseRespuestaDao.encuestaPendiente(matricula);
		boolean tieneVacuna			= alumUbicacionDao.tieneVacuna(matricula);
		// Si es alumno virtual o de planes no formales no se piden las vacunas ni la encuesta
		if (!modalidadAlumno.equals("1") && !modalidadAlumno.equals("4") && !planOficial.equals("S")){
			tieneNse 		= true;
			tieneVacuna 	= true;
		}
		
		int encuestaNse				= nseEncuestaDao.getEncuestaActiva();
//		NseEncuesta encuestaActiva 	= nseEncuestaDao.encuestaActiva();
		boolean contestada			= nseRespuestaDao.tieneRespuestas(matricula, String.valueOf(encuestaNse));

		modelo.addAttribute("SubMenu", "13");
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("esEnLinea", esEnLinea);
		modelo.addAttribute("tieneNse", tieneNse);
		modelo.addAttribute("tieneVacuna", tieneVacuna);
		modelo.addAttribute("encuestaNse", encuestaNse);
		modelo.addAttribute("contestada", contestada);
//		modelo.addAttribute("encuestaActiva", encuestaActiva);

		return "portales/alumno/previos";
	}
	
	@RequestMapping("/portales/alumno/patrocinadores")
	public String portalesAlumnoInscripcionWorkline (HttpServletRequest request, Model modelo) {
		String cargaId 		= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String periodoId 	= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		
		String matricula 	= "0";
		HttpSession sesion  = request.getSession();
		if(sesion != null) {
			matricula = (String)sesion.getAttribute("codigoAlumno");
		}
		
		AlumPersonal alumPersonal = alumPersonalDao.mapeaRegId(matricula);

		TrabAlum trabAlum = trabAlumDao.mapeaRegId(matricula, "A");
		HashMap<String, String> mapaCategorias = trabCategoriaDao.mapaCategoriaNombre();
		HashMap<String, String> mapaDepartamentos = trabDepartamentoDao.mapaDeptNombre(" ORDER BY DEPT_ID");
		List<TrabPeriodo> lisPeriodos = trabPeriodoDao.lisTodosPorAlumno(matricula, " ORDER BY PERIODO_ID");
		if(periodoId.equals("0") || periodoId == null) {
			periodoId = trabAlum.getPeriodoId();
		}
		List<TrabAlum> lisTrabajos = trabAlumDao.lisPorAlumno(matricula, periodoId, " ORDER BY PERIODO_ID");
		
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("trabAlum", trabAlum);
		modelo.addAttribute("mapaCategorias", mapaCategorias);
		modelo.addAttribute("mapaDepartamentos", mapaDepartamentos);
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("lisTrabajos", lisTrabajos);
		modelo.addAttribute("periodoId", periodoId);

		return "portales/alumno/patrocinadores";
	}

	@RequestMapping("/portales/alumno/planes")
	public String portalesInscEnLineaPlanes(HttpServletRequest request, Model modelo) {

		String matricula = "0";
		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
		}

		AlumPersonal alumPersonal = alumPersonalDao.mapeaRegId(matricula);

		// Lista de planes activos del alumno
		List<AlumPlan> lisPlanes = alumPlanDao.lisPlanesAlumno(matricula, " ORDER BY ENOC.ALUM_PLAN.F_INICIO DESC");

		HashMap<String, MapaPlan> mapaPlanes = mapaPlanDao.mapPlanes("'V','I','A'");
		HashMap<String, String> mapaCreditosPlanes = mapaAvanceDao.mapaCreditosPorPlan();
		HashMap<String, String> mapaCreditosAlumno = alumnoCursoDao.mapaCreditosPorAlumno(matricula, "'1','I','5','6'");

		modelo.addAttribute("SubMenu", "13");
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		modelo.addAttribute("mapaCreditosPlanes", mapaCreditosPlanes);
		modelo.addAttribute("mapaCreditosAlumno", mapaCreditosAlumno);

		return "portales/alumno/planes";
	}

	@RequestMapping("/portales/alumno/seguro")
	public String portalesAlumnoSeguro(HttpServletRequest request, Model modelo) {

		String mensaje = request.getParameter("Mensaje") == null ? "0" : request.getParameter("Mensaje");

		String matricula = "0";
		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
		}

		AlumPersonal alumPersonal = alumPersonalDao.mapeaRegId(matricula);

		List<AlumDocumento> lisAlumDocumentoFormato = alumDocumentoDao.lisAlumDocumentoFormato(matricula);

		modelo.addAttribute("SubMenu", "13");
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("matricula", matricula);
		modelo.addAttribute("lisAlumDocumentoFormato", lisAlumDocumentoFormato);

		return "portales/alumno/seguro";
	}

	@RequestMapping("/portales/alumno/candados")
	public String portalesAlumnoCandados(HttpServletRequest request, Model modelo) {

		String matricula = "0";
		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
		}
		
		int paisId = 0;
		
		if(parametrosDao.existeReg("1")) {
			paisId = Integer.parseInt(parametrosDao.getPaisId("1"));
		}

		AlumPersonal alumPersonal = alumPersonalDao.mapeaRegId(matricula);
		AlumAcademico alumAcademico = alumAcademicoDao.mapeaRegId(matricula);
		AlumPlan alumPlan = alumPlanDao.mapeaRegId(matricula);
		String planAlumno = mapaPlanDao.getNombrePlan(alumPlan.getPlanId());
		String nombreNacionalidad = catPaisDao.getNacionalidad(alumPersonal.getNacionalidad());
//		String fechaVencimiento = alumPersonalDao.getVencimientoFM3(alumPersonal.getCodigoPersonal());
		boolean autorizado = archivoDao.autorizaDocumentosAlumno(matricula, alumPlan.getPlanId());
		boolean extranjero = alumPersonalDao.esExtranjero(matricula, paisId);
		boolean inscrito = inscritosDao.inscrito(matricula);
		boolean finPermiso = finPermisoDao.tienePermiso(matricula);

//		LegExtdoctos legExtDoctos = new LegExtdoctos();
//
//		if (!alumPersonal.getNacionalidad().equals("91")) {
//			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
//			Date hoy = new Date();
//			Date fechav = null;
//			try {
//				fechav = df.parse(fechaVencimiento);
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//			int dias = Long.valueOf((fechav.getTime() - hoy.getTime()) / 1000 / 60 / 60 / 24).intValue();
//			if (dias < 30) {
//				if (dias <= 0) {
//					if (legExtdoctosDao.existeReg(matricula, "4")) {
//						legExtDoctos = legExtdoctosDao.mapeaRegId(matricula, "4");
//						legExtDoctos.setFechaVence("31/12/20");
//						legExtdoctosDao.updateReg(legExtDoctos);
//					} else {
//						legExtDoctos.setCodigo(matricula);
//						legExtDoctos.setEstado("1");
//						legExtDoctos.setFecha(Fecha.getHoy());
//						legExtDoctos.setFechaTramite(Fecha.getHoy());
//						legExtDoctos.setFechaVence("31/12/20");
//						legExtDoctos.setIdDocumento("4");
//						legExtDoctos.setNumDocto("-");
//					}
//				}
//			}
//		}

		List<CandTipo> lisTipos = candTipoDao.getListAll(" ORDER BY TIPO_ID");
		List<CandAlumno> lisCandados = candAlumnoDao.getLista(matricula, "A", " ORDER BY FOLIO");
		List<CargaBloque> lisBloquesActivos = cargaBloqueDao.lisBloquesEnInscripcion(matricula, " ORDER BY BLOQUE_ID");

		MapaPlan mapaPlan = mapaPlanDao.mapeaRegId(alumPlan.getPlanId());
		CatCarrera carrera = catCarreraDao.mapeaRegId(mapaPlan.getCarreraId());
		HashMap<String, EmpContacto> mapaContactos = empContactoDao.mapaContactos();

		HashMap<String, Candado> mapaCandados = candadoDao.getMapaCandados();
		// String coordinadorNombre = maestrosDao.getNombreCorto(carrera.getCodigoPersonal(), "NOMBRE");


		modelo.addAttribute("SubMenu", "13");
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("alumAcademico", alumAcademico);
		modelo.addAttribute("planAlumno", planAlumno);
		modelo.addAttribute("nombreNacionalidad", nombreNacionalidad);
//		modelo.addAttribute("fechaVencimiento", fechaVencimiento);
		modelo.addAttribute("autorizado", autorizado);
		modelo.addAttribute("extranjero", extranjero);
		modelo.addAttribute("inscrito", inscrito);
		modelo.addAttribute("finPermiso", finPermiso);
		modelo.addAttribute("carrera", carrera);

		modelo.addAttribute("lisTipos", lisTipos);
		modelo.addAttribute("lisCandados", lisCandados);
		modelo.addAttribute("lisBloquesActivos", lisBloquesActivos);

		modelo.addAttribute("mapaCandados", mapaCandados);
		modelo.addAttribute("mapaContactos", mapaContactos);

		modelo.addAttribute("SubMenu", "13");

		return "portales/alumno/candados";
	}

	@RequestMapping("/portales/alumno/cargas")
	public String portalesAlumnoCargas(HttpServletRequest request, Model modelo) {
		String year = aca.util.Fecha.getHoy().substring(6, 10);
		String matricula = "0";

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
		}

		AlumPersonal alumPersonal = alumPersonalDao.mapeaRegId(matricula);

		List<CargaAlumno> lisCargas = cargaAlumnoDao.lisCargasAlumno(matricula, year, " ORDER BY FECHA DESC");
		HashMap<String, String> mapaMaterias = alumnoCursoDao.mapaMateriasPorCargayBloque(matricula);
		HashMap<String, MapaPlan> mapaPlanes = mapaPlanDao.mapPlanes("'V','I','A'");
		HashMap<String, Carga> mapaCargas = cargaDao.mapaCargas();
		HashMap<String, CargaBloque> mapaBloques = cargaBloqueDao.mapaBloques();
		
		HashMap<String, AlumEstado> mapaEstados = alumEstadoDao.mapaAlumEstado(matricula);

//		HashMap<String, FesCcobro> mapaFesCobro = fesCcobroDao.mapaFesCobroPorMatricula(matricula);

		modelo.addAttribute("SubMenu", "13");
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("mapaMaterias", mapaMaterias);
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		modelo.addAttribute("mapaEstados", mapaEstados);
//		modelo.addAttribute("mapaFesCobro", mapaFesCobro);
		modelo.addAttribute("mapaCargas", mapaCargas);
		modelo.addAttribute("mapaBloques", mapaBloques);

		return "portales/alumno/cargas";
	}

	@Transactional
	@RequestMapping("/portales/alumno/verMaterias")
	public String portalesAlumnoVerMaterias(HttpServletRequest request, Model modelo) {
		String cargaId 				= request.getParameter("CargaId") == null ? "0" : request.getParameter("CargaId");
		String bloqueId 			= request.getParameter("BloqueId") == null ? "0" : request.getParameter("BloqueId");
		String confirmar 			= request.getParameter("Confirmar") == null ? "0" : request.getParameter("Confirmar");
		String confirmarBeca 		= request.getParameter("ConfirmarBeca") == null ? "N": request.getParameter("ConfirmarBeca");
		String formaPago 			= request.getParameter("FormaPago") == null ? "0" : request.getParameter("FormaPago");
		String revizarCosto 		= request.getParameter("RevizarCosto") == null ? "N" : request.getParameter("RevizarCosto");
		String confirmarConvenio 	= request.getParameter("ConfirmarConvenio") == null ? "N":request.getParameter("ConfirmarConvenio");
		String codigoPersonal 		= "0";
		String matricula 			= "0";
		String mensaje 				= "0";
		boolean tieneBeca 			= false;
		String puestoAlumno 		= "";
		LogOperacion log 			= new LogOperacion();

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
			codigoPersonal = (String) sesion.getAttribute("codigoPersonal");
			if (cargaId.equals("0")) {
				cargaId = (String) sesion.getAttribute("cargaId");
			} else {
				sesion.setAttribute("cargaId", cargaId);
			}
			if (bloqueId.equals("0")) {
				bloqueId = (String) sesion.getAttribute("bloqueId");
			} else {
				sesion.setAttribute("bloqueId", bloqueId);
			}
		}

		// System.out.println(bloqueId);

		AlumPersonal alumPersonal = alumPersonalDao.mapeaRegId(matricula);
		AlumAcademico alumAcademico = alumAcademicoDao.mapeaRegId(matricula);
		String modalidadNombre = catModalidadDao.getNombreModalidad(alumAcademico.getModalidadId());
		// FesCcobro cobro = fesCcobroDao.mapeaRegId(matricula, cargaId, bloqueId);
		Acceso acceso = accesoDao.mapeaRegId(codigoPersonal);

		if (!formaPago.equals("0")) {
			// cobro.setFormaPago(formaPago);
		}

		Carga carga = cargaDao.mapeaRegId(cargaId);
		List<AlumnoCurso> lista = alumnoCursoDao.listaMateriasEnCargayBloque(matricula, cargaId, bloqueId,"ORDER BY SUBSTR(GRUPO, 0, 1), CICLO");
		List<AccesoConfirmar> lisConfirmar = accesoConfirmarDao.lisPorEmpleado(codigoPersonal, "ORDER BY MODALIDAD_ID");
		List<CargaPracticaAlumno> lisPracticas = cargaPracticaAlumnoDao.lisPorAlumno(matricula, " ORDER BY FOLIO");
		
		CargaFinanciero financiero 		= new CargaFinanciero();
		if(cargaFinancieroDao.existeReg(matricula, cargaId, bloqueId)) {
			financiero = cargaFinancieroDao.mapeaRegId(matricula, cargaId, bloqueId);
		}

		boolean confirmarModalidad = false;
		for (AccesoConfirmar mod : lisConfirmar) {
			if (mod.getCodigoPersonal().equals(codigoPersonal)
					&& mod.getModalidadId().equals(alumAcademico.getModalidadId())) {
				confirmarModalidad = true;
				break;
			}
		}

		HashMap<String, CargaGrupo> mapaCargaGrupo 			= cargaGrupoDao.mapCargaGrupoPorCarga(cargaId);
		HashMap<String, KrdxCursoAct> mapaMateriasAlumno 	= krdxCursoActDao.mapaMateriasDelAlumno(matricula);
		HashMap<String, String> mapaMaestroCorto 			= maestrosDao.mapaMaestroCorto("NOMBRE");
		HashMap<String, CargaBloque> mapaBloques 			= cargaBloqueDao.mapaBloques();
		HashMap<String, CargaBloque> mapaBloquesActivos 	= cargaBloqueDao.mapaBloquesActivos();
		HashMap<String, String> mapaTipoCalCorto 			= catTipoCalDao.mapTipoCalCorto();
		HashMap<String, CatModalidad> mapaModalidades 		= catModalidadDao.getMapAll("");
		HashMap<String, String> mapaLibres 					= cargaPracticaAlumnoDao.mapaPorAlumnoyEstado(matricula, "L");
		HashMap<String, String> mapaPracticas 				= cargaPracticaAlumnoDao.mapaPorAlumnoyEstado(matricula, "A");
		HashMap<String, String> mapaCursosPracticos 		= mapaCursoDao.mapaCursosEnPracticas();
		HashMap<String, FinTabla> mapaCostos 				= finTablaDao.mapTablaCargas("'" + cargaId + "'");

		CargaAlumno cargaAlumno = new CargaAlumno();
		BecPuestoAlumno becPuestoAlumno = new BecPuestoAlumno();
		boolean existeConvenio = false;
		boolean autorizaConvenio = false;
		if (cargaAlumnoDao.existeReg(matricula, cargaId, bloqueId)) {
			cargaAlumno = cargaAlumnoDao.mapeaRegId(matricula, cargaId, bloqueId);
			// if (fesCcAfeAcuerdosDao.tieneBeca(matricula, cargaId, bloqueId, cargaAlumno.getPlanId())) {
			// 	tieneBeca = true;
			// 	puestoAlumno = fesCcAfeAcuerdosDao.getPuesto(matricula, cargaId, bloqueId);
			// 	becPuestoAlumno = becPuestoAlumnoDao.mapeaRegId(puestoAlumno);

			// 	if (confirmarConvenio.equals("S")) {
			// 		becContratoDao.updateAutorizado(matricula, puestoAlumno);
			// 	}
			// 	existeConvenio = becContratoDao.existeReg(matricula, puestoAlumno);
			// 	autorizaConvenio = becContratoDao.existeAutorizacion(matricula, puestoAlumno);
			// }
			if (confirmarBeca.equals("S")) {
				cargaAlumno.setBeca(confirmarBeca);
				cargaAlumnoDao.updateReg(cargaAlumno);
			}
			if (revizarCosto.equals("S")) {
				cargaAlumno.setCalculo(revizarCosto);
				cargaAlumnoDao.updateReg(cargaAlumno);
			}
			if (confirmar.equals("S")) {
				cargaAlumno.setConfirmar("S");
				if (cargaAlumnoDao.updateReg(cargaAlumno)) {
					String datos = "CodigoAlumno:" + matricula + ", Confirmar Materias Hora:" + aca.util.Fecha.getHora()
							+ ":" + aca.util.Fecha.getMinutos() + ":" + aca.util.Fecha.getSegundos() + ":"
							+ aca.util.Fecha.getAMPM();
					log.setDatos(datos);
					log.setIp(request.getRemoteAddr());
					log.setUsuario(matricula);
					log.setTabla("CARGA_ALUMNO");
					log.setOperacion("update");
					logOperacionDao.insertReg(log);
				}
			} else if (confirmar.equals("N")) {
				cargaAlumno.setConfirmar("N");
				cargaAlumnoDao.updateReg(cargaAlumno);
			}
			if (cargaAlumno.getConfirmar().equals("S")) {
				mensaje = "1";
			}
		}

		AlumImagen alumImagen = new AlumImagen();

		if (alumImagenDao.existeReg(matricula)) {
			alumImagen = alumImagenDao.mapeaRegId(matricula);
		}

		String planCarga = "0";

		MapaPlan mapaPlan = new MapaPlan();
		AlumPlan plan = new AlumPlan();
		AlumEstado alumEstado = new AlumEstado();

		if (matricula.subSequence(0, 1).equals("0") || matricula.subSequence(0, 1).equals("1")
				|| matricula.subSequence(0, 1).equals("2")) {
			planCarga = cargaAlumnoDao.getPlanId(matricula, cargaId, bloqueId);
			plan = alumPlanDao.mapeaRegId(matricula, planCarga);
			mapaPlan = mapaPlanDao.mapeaRegId(plan.getPlanId());
			alumEstado = alumEstadoDao.mapeaRegId(codigoPersonal, cargaId);
		}

		HashMap<String, String> mapaConfirmadas = krdxCursoActDao.mapaConfirmadas(matricula);

		modelo.addAttribute("SubMenu", "13");
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("carga", carga);
		// modelo.addAttribute("cobro", cobro);
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("alumAcademico", alumAcademico);
		modelo.addAttribute("cargaAlumno", cargaAlumno);
		modelo.addAttribute("confirmarModalidad", confirmarModalidad);
		modelo.addAttribute("modalidadNombre", modalidadNombre);
		modelo.addAttribute("alumImagen", alumImagen);
		modelo.addAttribute("alumEstado", alumEstado);
		modelo.addAttribute("tieneBeca", tieneBeca);
		modelo.addAttribute("becPuestoAlumno", becPuestoAlumno);
		modelo.addAttribute("existeConvenio", existeConvenio);
		modelo.addAttribute("autorizaConvenio", autorizaConvenio);
		modelo.addAttribute("financiero", financiero);

		modelo.addAttribute("lista", lista);
		modelo.addAttribute("lisConfirmar", lisConfirmar);
		modelo.addAttribute("lisPracticas", lisPracticas);
		modelo.addAttribute("mapaCargaGrupo", mapaCargaGrupo);
		modelo.addAttribute("mapaMaestroCorto", mapaMaestroCorto);
		modelo.addAttribute("mapaBloques", mapaBloques);
		modelo.addAttribute("mapaBloquesActivos", mapaBloquesActivos);
		modelo.addAttribute("mapaTipoCalCorto", mapaTipoCalCorto);
		modelo.addAttribute("mapaModalidades", mapaModalidades);
		modelo.addAttribute("mapaLibres", mapaLibres);
		modelo.addAttribute("mapaPracticas", mapaPracticas);
		modelo.addAttribute("mapaCursosPracticos", mapaCursosPracticos);
		modelo.addAttribute("mapaCostos", mapaCostos);
		modelo.addAttribute("mapaPlan", mapaPlan);
		modelo.addAttribute("mapaConfirmadas", mapaConfirmadas);
		modelo.addAttribute("mapaMateriasAlumno", mapaMateriasAlumno);

		modelo.addAttribute("mensaje", mensaje);

		return "portales/alumno/verMaterias";
	}

	@RequestMapping("/portales/alumno/verCostos")
	public ResponseEntity<byte[]> portalesAlumnoVerCosto(HttpServletRequest request, Model modelo) throws IOException {
		String matricula = "0";
		String codigoPersonal = "0";
		String codigoLogeado = "0";
		String cargaId 				= request.getParameter("CargaId") == null ? "0" : request.getParameter("CargaId");
		String bloqueId 			= request.getParameter("BloqueId") == null ? "0" : request.getParameter("BloqueId");
		
		CargaAlumno cargaAlumno = new CargaAlumno();
		
		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
			codigoPersonal = (String) sesion.getAttribute("codigoPersonal");
			codigoLogeado = (String) sesion.getAttribute("codigoLogeado");
			if (cargaId.equals("0")) {
				cargaId = (String) sesion.getAttribute("cargaId");
			} else {
				sesion.setAttribute("cargaId", cargaId);
			}
			if (bloqueId.equals("0")) {
				bloqueId = (String) sesion.getAttribute("bloqueId");
			} else {
				sesion.setAttribute("bloqueId", bloqueId);
			}
		}
		
		if(cargaAlumnoDao.existeReg(matricula, cargaId, bloqueId)){
			cargaAlumno = cargaAlumnoDao.mapeaRegId(matricula, cargaId, bloqueId);
		}
		
		if(codigoLogeado.equals(matricula)) {
			cargaAlumno.setCalculo("S");
		}
		cargaAlumnoDao.updateReg(cargaAlumno);
		
		FinCalculo finCalculo = finCalculoDao.mapeaRegId(matricula, cargaId, bloqueId);
		byte[] pdf = finCalculo.getArchivo();
		
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", matricula + ".pdf");
        
        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
		
	}

	@RequestMapping("/portales/alumno/confirmarMaterias")
	public String portalesAlumnoConfirmarMaterias(HttpServletRequest request, Model modelo) {
		String cargaId = request.getParameter("CargaId") == null ? "0" : request.getParameter("CargaId");
		String bloqueId = request.getParameter("BloqueId") == null ? "0" : request.getParameter("BloqueId");
		String matricula = "0";

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
		}

		List<AlumnoCurso> lista = alumnoCursoDao.listaMateriasEnCargayBloque(matricula, cargaId, bloqueId,
				" ORDER BY GRUPO,CICLO");

		for (AlumnoCurso materia : lista) {
			if (request.getParameter(matricula + materia.getCursoCargaId()) != null) {
				if (krdxCursoActDao.updateConfirmar(matricula, materia.getCursoCargaId(), "S")
						&& cargaAlumnoDao.existeCarga(matricula, cargaId)) {
					cargaAlumnoDao.confirmarMaterias(matricula, cargaId, bloqueId, "S");
				}
			} else {
				krdxCursoActDao.updateConfirmar(matricula, materia.getCursoCargaId(), "N");
			}
		}

		return "redirect:/portales/alumno/verMaterias?CargaId=" + cargaId + "&BloqueId=" + bloqueId;
	}

	@RequestMapping("/portales/alumno/aceptoConcentimiento")
	public String portalesAlumnoAceptoConcentimiento(HttpServletRequest request, Model modelo) {
		String acepto = request.getParameter("Ac") == null ? "0" : request.getParameter("Ac");
		String cargaId = request.getParameter("CargaId") == null ? "0" : request.getParameter("CargaId");
		String bloqueId = request.getParameter("BloqueId") == null ? "0" : request.getParameter("BloqueId");
		String matricula = "0";

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
		}

		if (acepto.equals("1")) {
			acepto = "S";
		} else if (acepto.equals("2")) {
			acepto = "N";
		}

		String tipo = "";
		alumPersonalDao.getEdad(matricula);
		if (alumPersonalDao.getEdad(matricula) >= 18) {
			tipo = "A";
		} else {
			tipo = "P";
		}

		AlumImagen alumImagen = new AlumImagen();
		alumImagen.setCodigoPersonal(matricula);
		alumImagen.setConsentimiento(acepto);
		alumImagen.setTipo(tipo);

		if (alumImagenDao.existeReg(matricula)) {
			alumImagenDao.updateReg(alumImagen);
		} else {
			alumImagenDao.insertReg(alumImagen);
		}

		return "redirect:/portales/alumno/verMaterias?CargaId=" + cargaId + "&BloqueId=" + bloqueId;
	}

	@RequestMapping("/portales/alumno/carga")
	public String portalesAlumnoCarga(HttpServletRequest request, Model modelo) {
		String matricula = "0";
		String cargaId = request.getParameter("CargaId") == null ? "0" : request.getParameter("CargaId");
		String bloqueId = request.getParameter("BloqueId") == null ? "0" : request.getParameter("BloqueId");

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
		}

		List<CargaEnLinea> lisCargas = cargaEnLineaDao.getListActivas(" ORDER BY CARGA_ID");
		if (cargaId.equals("0") && lisCargas.size() >= 1) {
			cargaId = lisCargas.get(0).getCargaId();
		}

		CargaAlumno cargaAlumno = new CargaAlumno();

		if (cargaAlumnoDao.existeReg(matricula, cargaId, bloqueId)) {
			cargaAlumno = cargaAlumnoDao.mapeaRegId(matricula, cargaId, bloqueId);
		}

		AlumPersonal alumPersonal = alumPersonalDao.mapeaRegId(matricula);
		AlumAcademico alumAcademico = alumAcademicoDao.mapeaRegId(matricula);
		String modalidadNombre = catModalidadDao.getNombreModalidad(alumAcademico.getModalidadId());
		List<CargaBloque> lisBloques = cargaBloqueDao.getLista(cargaId, " ORDER BY BLOQUE_ID");
		List<AlumPlan> lisPlanes = alumPlanDao.getLista(matricula, " ORDER BY ENOC.ALUM_PLAN.F_INICIO DESC");

		if (bloqueId.equals("0") && lisBloques.size() >= 1) {
			bloqueId = lisBloques.get(0).getBloqueId();
		}

		String planRegistrado = "0";
		if (cargaAlumnoDao.existeReg(matricula, cargaId, bloqueId)) {
			planRegistrado = cargaAlumnoDao.mapeaRegId(matricula, cargaId, bloqueId).getPlanId();
		}

		HashMap<String, MapaPlan> mapaPlanes = mapaPlanDao.mapPlanes("'V','I','A'");
		HashMap<String, CatCarrera> mapaCarreras = catCarreraDao.getMapAll("");
		HashMap<String, CatNivel> mapaNiveles = catNivelDao.getMapAll("");
		HashMap<String, String> mapaMateriasOfrecidas = cargaAcademicaDao.mapaMateriasOfrecidas(cargaId,
				alumAcademico.getModalidadId());
		HashMap<String, String> mapaMateriasAlumno = alumnoCursoDao.mapaMateriasPorAlumno(matricula);

		modelo.addAttribute("SubMenu", "13");
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("planRegistrado", planRegistrado);
		modelo.addAttribute("modalidadNombre", modalidadNombre);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisBloques", lisBloques);
		modelo.addAttribute("lisPlanes", lisPlanes);

		modelo.addAttribute("mapaPlanes", mapaPlanes);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaNiveles", mapaNiveles);
		modelo.addAttribute("mapaMateriasOfrecidas", mapaMateriasOfrecidas);
		modelo.addAttribute("mapaMateriasAlumno", mapaMateriasAlumno);
		modelo.addAttribute("cargaAlumno", cargaAlumno);
		modelo.addAttribute("alumPersonal", alumPersonal);

		return "portales/alumno/carga";
	}

	@RequestMapping("/portales/alumno/grabarCarga")
	public String portalesalumnoGrabarCarga(HttpServletRequest request, Model modelo) {
		String matricula = "-";
		String cargaId = request.getParameter("CargaId");
		String bloqueId = request.getParameter("BloqueId");
		String planId = request.getParameter("PlanId");

		CargaAlumno cargaAlumno = new CargaAlumno();

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
		}

		cargaAlumno.setCodigoPersonal(matricula);
		cargaAlumno.setCargaId(cargaId);
		cargaAlumno.setBloqueId(bloqueId);
		cargaAlumno.setEstado("1");
		cargaAlumno.setFecha(aca.util.Fecha.getHoy());
		cargaAlumno.setGrupo("XX");
		cargaAlumno.setPlanId(planId);
		cargaAlumno.setConfirmar("N");

		if (!cargaAlumnoDao.existeReg(matricula, cargaId, bloqueId)) {
			cargaAlumnoDao.insertReg(cargaAlumno);
		}

		return "redirect:/portales/alumno/carga?CargaId=" + cargaId + "&BloqueId=" + bloqueId;
	}

	@RequestMapping("/portales/alumno/borrarCarga")
	public String portalesAlumnoBorrarCarga(HttpServletRequest request, Model modelo) {
		String matricula = "-";
		String cargaId = request.getParameter("CargaId");
		String bloqueId = request.getParameter("BloqueId");
		String planId = request.getParameter("PlanId");

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
		}

		if (cargaAlumnoDao.existeReg(matricula, cargaId, bloqueId)) {
			cargaAlumnoDao.deleteReg(matricula, cargaId, bloqueId, planId);
		}

		return "redirect:/portales/alumno/carga?CargaId=" + cargaId + "&BloqueId=" + bloqueId;
	}

	@RequestMapping("/portales/alumno/pagos")
	public String portalesAlumnoPagos(HttpServletRequest request, Model modelo) {

		String year = aca.util.Fecha.getHoy().substring(6, 10);
		String matricula = "0";
		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
		}

		AlumPersonal alumPersonal = alumPersonalDao.mapeaRegId(matricula);
		AlumAcademico alumAcademico = alumAcademicoDao.mapeaRegId(matricula);

		List<CargaAlumno> lisCargas = cargaAlumnoDao.lisCargasAlumno(matricula, year,
				" ORDER BY CARGA_ID DESC, BLOQUE_ID");
		HashMap<String, String> mapaMaterias = alumnoCursoDao.mapaMateriasPorCargayBloque(matricula);
		HashMap<String, MapaPlan> mapaPlanes = mapaPlanDao.mapPlanes("'V','I','A'");
		// HashMap<String, FesCcobro> mapaFesCobro = fesCcobroDao.mapaFesCobroPorMatricula(matricula);
		HashMap<String, AlumEstado> mapaAlumeEstado = alumEstadoDao.mapaAlumEstado(matricula);
		HashMap<String, Carga> mapaCargas = cargaDao.mapaCargas();
		HashMap<String, CargaBloque> mapaBloques = cargaBloqueDao.mapaBloques();
		// HashMap<String, String> mapaCreditos = fesCcobroDao.mapaCreditosAlumno(matricula);
		// HashMap<String, String> mapaPagos = fesCcobroDao.mapaPagoInicial(matricula);
		HashMap<String, CargaFinanciero> mapaCargaFinanciero = cargaFinancieroDao.mapaFinancieroAlumnoCarga(" ORDER BY CARGA_ID");

		// Cambiar el estado de los alumnos a Pagado "P"
		for (CargaAlumno carga : lisCargas) {
			double pagoInicial = 1;
			// if (mapaPagos.containsKey(carga.getCargaId() + carga.getBloqueId())) {
			// 	pagoInicial = Double.valueOf(mapaPagos.get(carga.getCargaId() + carga.getBloqueId()));
			// 	if (pagoInicial <= 0) {
			// 		pagoInicial = 0;
			// 		cargaAlumnoDao.updatePago(carga.getCodigoPersonal(), carga.getCargaId(), carga.getBloqueId(), "S");
			// 	}
			// }
		}

		modelo.addAttribute("SubMenu", "13");
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("alumAcademico", alumAcademico);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("mapaMaterias", mapaMaterias);
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		// modelo.addAttribute("mapaFesCobro", mapaFesCobro);
		modelo.addAttribute("mapaAlumeEstado", mapaAlumeEstado);
		modelo.addAttribute("mapaCargas", mapaCargas);
		modelo.addAttribute("mapaBloques", mapaBloques);
		// modelo.addAttribute("mapaCreditos", mapaCreditos);
		// modelo.addAttribute("mapaPagos", mapaPagos);
		modelo.addAttribute("mapaCargaFinanciero", mapaCargaFinanciero);

		return "portales/alumno/pagos";
	}

	@RequestMapping("/portales/alumno/formasPago")
	public String portalesAlumnoFormasPago(HttpServletRequest request, Model modelo) {

		String planId = request.getParameter("PlanId") == null ? "0" : request.getParameter("PlanId");
		String matricula = "0";
		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
		}

		// Lista de planes (No mover de ésta poscición)
		List<AlumPlan> lisPlanes = alumPlanDao.lisPlanesAlumno(matricula, " ORDER BY ENOC.ALUM_PLAN.F_INICIO DESC");

		AlumPlan alumPlan = alumPlanDao.mapeaRegId(matricula);
		boolean existePlan = false;
		for (AlumPlan plan : lisPlanes) {
			if (plan.getPlanId().equals(planId)) {
				existePlan = true;
				break;
			}
		}
		if (planId.equals("0") || !existePlan) {
			planId = alumPlan.getPlanId();
		}

		String carrera = mapaPlanDao.getCarreraId(planId);
		MapaPlan mapaPlan = mapaPlanDao.mapeaRegId(planId);
		String cuenta = "";
		if (!carrera.equals("10209") && !carrera.equals("10210")) {
			if (mapaPlan.getOficial().equals("N") || mapaPlan.getOficial().equals("S"))
				cuenta = "1";
			if (mapaPlan.getOficial().equals("I"))
				cuenta = "2";
			if (mapaPlan.getOficial().equals("C"))
				cuenta = "3";
			if (mapaPlan.getOficial().equals("D"))
				cuenta = "4";
			if (mapaPlan.getOficial().equals("A"))
				cuenta = "5";
		}

		String digito = alumReferenciaDao.generarReferenciaSantander(cuenta + matricula);
		String nombre = usuariosDao.getNombreUsuario(matricula, "NOMBRE");
		String numCuenta = "";
		boolean tieneScotiabank = false;
		boolean tieneSantander = false;
		boolean tieneBanorte = false;
		boolean esCovoprom = false;

		String institucion = parametrosDao.mapeaRegId("1").getInstitucion();

		AlumReferencia alumno = new AlumReferencia();
		alumno.setCodigoPersonal(cuenta + matricula);
		if (alumReferenciaDao.existeReg(cuenta + matricula)) {

			alumno = alumReferenciaDao.mapeaRegId(cuenta + matricula);
			tieneScotiabank = alumno.getScotiabank() != null && !alumno.getScotiabank().equals("-");
			tieneSantander = alumno.getSantander() != null && !alumno.getSantander().equals("-");
			// Es la misma referencia de Scotiabank
			tieneBanorte = alumno.getScotiabank() != null && !alumno.getScotiabank().equals("-");

			if (!tieneScotiabank || !tieneSantander) {
				// Se actualiza el que no exista
				if (!tieneScotiabank)
					alumno.setScotiabank(digito);
				if (!tieneSantander)
					alumno.setSantander(digito);
				if (alumReferenciaDao.updateReg(alumno)) {
					tieneScotiabank = true;
					tieneSantander = true;
					tieneBanorte = true;
				}
			}
		} else {
			alumno.setBanamex("-");
			alumno.setScotiabank(digito);
			alumno.setSantander(digito);
			if (alumReferenciaDao.insertReg(alumno)) {
				// System.out.println("Inserte el digito verificador"+matricula+":"+digito);
				tieneScotiabank = true;
				tieneSantander = true;
				tieneBanorte = true;
			}
		}

		if (carrera.equals("10209") || carrera.equals("10210")) {
			tieneSantander = false;
			esCovoprom = true;
			numCuenta = "25300065609";
		} else {
			tieneSantander = true;
			numCuenta = "25300000361";
		}

		HashMap<String, MapaPlan> mapaPlanes = mapaPlanDao.mapPlanes("'V','I','A'");

		modelo.addAttribute("SubMenu", "13");
		modelo.addAttribute("cuenta", cuenta);
		modelo.addAttribute("digito", digito);
		modelo.addAttribute("nombre", nombre);
		modelo.addAttribute("numCuenta", numCuenta);
		modelo.addAttribute("tieneScotiabank", tieneScotiabank);
		modelo.addAttribute("tieneSantander", tieneSantander);
		modelo.addAttribute("tieneBanorte", tieneBanorte);
		modelo.addAttribute("esCovoprom", esCovoprom);
		modelo.addAttribute("institucion", institucion);
		modelo.addAttribute("alumno", alumno);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("mapaPlanes", mapaPlanes);

		return "portales/alumno/formasPago";
	}

	@RequestMapping("/portales/alumno/solicitudInscripcion")
	public String portalesAlumnoSolicitudInscripcion(HttpServletRequest request, Model modelo) {

		String eventoId = request.getParameter("EventoId") == null ? "0" : request.getParameter("EventoId");
		String planId = request.getParameter("PlanId") == null ? "0" : request.getParameter("PlanId");
		String modo = request.getParameter("Modo") == null ? "P" : request.getParameter("Modo");
		String fecha = aca.util.Fecha.getHoy();
		String codigoPersonal = "0";
		String matricula = "0";

		HttpSession sesion = request.getSession();
		if (sesion != null) {			
			codigoPersonal 	= sesion.getAttribute("codigoPersonal") ==null?"0":(String)sesion.getAttribute("codigoPersonal");
			matricula 		= sesion.getAttribute("codigoAlumno") ==null?"0":(String)sesion.getAttribute("codigoAlumno");
		}

		MatAlumno matAlumno = new MatAlumno();

		if (!matAlumnoDao.existeReg(eventoId, matricula, planId)) {
			matAlumno.setEventoId(eventoId);
			matAlumno.setCodigoPersonal(matricula);
			matAlumno.setPlanId(planId);
			matAlumno.setFecha(fecha);
			matAlumno.setUsuario(codigoPersonal);
			matAlumno.setModo(modo);
			matAlumno.setEstado("P");
			matAlumnoDao.insertReg(matAlumno);
		} else {
			matAlumno = matAlumnoDao.mapeaRegId(eventoId, matricula, planId);
			matAlumno.setModo(modo);
			matAlumno.setFecha(fecha);
			matAlumnoDao.updateReg(matAlumno);
		}

		// log.info("notiMensaesDao.existeRegURL");
		String carreraId = mapaPlanDao.getCarreraId(planId);
		CatCarrera catCarrera = catCarreraDao.mapeaRegId(carreraId);
		String codigoCoordinador = catCarrera.getCodigoPersonal();

		NotiMensajes notiMensajes = new NotiMensajes();
		notiMensajes.setCodigoPersonal(codigoCoordinador);
		notiMensajes.setTipo("2");// Solicitud de inscripción
		notiMensajes.setUrl(matricula + "/academico/matricula/plan/carga?moduloId=A07&carpeta=matricula");
		if (notiMensajesDao.existeRegURL(notiMensajes.getCodigoPersonal(), notiMensajes.getTipo(),
				notiMensajes.getUrl())) {// si existe actualizamos la fecha
			notiMensajes = notiMensajesDao.mapeaRegURL(notiMensajes.getCodigoPersonal(), notiMensajes.getTipo(),
					notiMensajes.getUrl());
			notiMensajesDao.updateRegNow(notiMensajes);
			// log.info("Se hizo notiMensajesDao.updateRegNow");
		} else {
			notiMensajes.setMensaje(matricula + " request to enroll  in " + planId);
			notiMensajes.setSilenciado("N");
			notiMensajes.setVisto("N");
			notiMensajes.setSms("S");
			notiMensajesDao.insertRegNow(notiMensajes);
			// log.info("Se hizo notiMensajesDao.insertRegNow");
		}

		return "redirect:/portales/alumno/pasos?PlanId=" + planId;
	}

	@RequestMapping("/portales/alumno/cierre")
	public String portalesAlumnoCierre(HttpServletRequest request, Model modelo) {

		String year = aca.util.Fecha.getHoy().substring(6, 10);
		String esEnLinea	= "N";
		String matricula 	= "0";
		String modalidadAlumno = "0";
		String documentoId	 = "1";

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
			modalidadAlumno 		= alumAcademicoDao.getModalidadId(matricula);
			esEnLinea				= catModalidadDao.getEnLinea(modalidadAlumno);
		}

		AlumPersonal alumPersonal = alumPersonalDao.mapeaRegId(matricula);
		List<CargaAlumno> lisCargas = cargaAlumnoDao.lisCargasAlumno(matricula, year," ORDER BY FECHA DESC, BLOQUE_ID");
		HashMap<String, MapaPlan> mapaPlanes = mapaPlanDao.mapPlanes("'V','I','A'");
//		HashMap<String, FesCcobro> mapaFesCobro = fesCcobroDao.mapaFesCobroPorMatricula(matricula);
		HashMap<String, AlumEstado> mapaAlumEstado = alumEstadoDao.mapaAlumEstado(matricula);
		HashMap<String, Carga> mapaCargas = cargaDao.mapaCargas();
		HashMap<String, CargaBloque> mapaBloques = cargaBloqueDao.mapaBloques();
		// HashMap<String, String> mapaPagos = fesCcobroDao.mapaPagoInicial(matricula);
		HashMap<String, String> mapaCodigo = accesoValidaDao.mapaCredencialCodigo();
		HashMap<String, String> mapaLibres = cargaPracticaAlumnoDao.mapaPorAlumnoyEstado(matricula, "L");
		HashMap<String, String> mapaPracticas = cargaPracticaAlumnoDao.mapaPorAlumnoyEstado(matricula, "A");
		HashMap<String, CargaFinanciero> mapaCargaFinanciero = cargaFinancieroDao.mapaFinancieroAlumnoCarga(" ORDER BY CARGA_ID");
		List<CargaPracticaAlumno> lisPracticas = cargaPracticaAlumnoDao.lisPorAlumno(matricula, " ORDER BY FOLIO");

		String cargasConCartas = cargaEnLineaDao.cargasConCartas("S");

		// Cambiar el estado de los alumnos a Pagado "P"
		for (CargaAlumno carga : lisCargas) {
			double pagoInicial = 1;
			// if (mapaPagos.containsKey(carga.getCargaId() + carga.getBloqueId())) {
			// 	pagoInicial = Double.valueOf(mapaPagos.get(carga.getCargaId() + carga.getBloqueId()));
			// 	if (pagoInicial <= 0) {
			// 		pagoInicial = 0;
			// 		cargaAlumnoDao.updatePago(carga.getCodigoPersonal(), carga.getCargaId(), carga.getBloqueId(), "S");
			// 	}
			// }

//			FesCcobro cobro = new FesCcobro();
//			if (mapaFesCobro.containsKey(carga.getCargaId() + carga.getBloqueId())) {
//				cobro = mapaFesCobro.get(carga.getCargaId() + carga.getBloqueId());
//			}
			
			AlumEstado estado = new AlumEstado();
			if(mapaAlumEstado.containsKey(carga.getCargaId() + carga.getBloqueId())) {
				estado = mapaAlumEstado.get(carga.getCargaId() + carga.getBloqueId());
			}

			if (estado.getEstado().equals("I") && cargasConCartas.contains(carga.getCargaId())) {
				if (!accesoValidaDao.existeCredencialReg(matricula, carga.getCargaId() + carga.getBloqueId())) {
					AccesoValida valida = new AccesoValida();

					documentoId = accesoValidaDao.maxDocId(matricula);

					String codigo = aca.util.Encriptar.sha512ConBase64(documentoId);

					// CARACTERES ESPECIALES NO PERMITIDOS ! * ' ( ) ; : @ & = + $ , / ? # [ ]
					codigo = codigo.replace("+", "X");
					codigo = codigo.replace("#", "X");
					codigo = codigo.replace("'", "X");
					codigo = codigo.replace("(", "X");
					codigo = codigo.replace(")", "X");
					codigo = codigo.replace("&", "X");
					codigo = codigo.replace("@", "X");
					codigo = codigo.replace(";", "X");
					codigo = codigo.replace(":", "X");
					codigo = codigo.replace("=", "X");
					codigo = codigo.replace("$", "X");
					codigo = codigo.replace("/", "X");
					codigo = codigo.replace("?", "X");
					codigo = codigo.replace("[", "X");
					codigo = codigo.replace("]", "X");
					codigo = codigo.replace("!", "X");
					codigo = codigo.replace(",", "X");
					codigo = codigo.replace("*", "X");

					valida.setDocumentoId(documentoId);
					valida.setCodigoPersonal(matricula);
					valida.setFecha(estado.getFecha());
					valida.setLlave(carga.getCargaId() + carga.getBloqueId());
					valida.setCodigo(codigo);
					valida.setTipo("1");
					accesoValidaDao.insertReg(valida);
				}
			}

		}

		modelo.addAttribute("SubMenu", "13");
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("esEnLinea", esEnLinea);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisPracticas", lisPracticas);
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		modelo.addAttribute("mapaAlumEstado", mapaAlumEstado);
//		modelo.addAttribute("mapaFesCobro", mapaFesCobro);
		modelo.addAttribute("mapaCargas", mapaCargas);
		modelo.addAttribute("mapaBloques", mapaBloques);
		// modelo.addAttribute("mapaPagos", mapaPagos);
		modelo.addAttribute("mapaCodigo", mapaCodigo);
		modelo.addAttribute("mapaLibres", mapaLibres);
		modelo.addAttribute("mapaPracticas", mapaPracticas);
		modelo.addAttribute("mapaCargaFinanciero", mapaCargaFinanciero);
		modelo.addAttribute("cargasConCartas", cargasConCartas);

		return "portales/alumno/cierre";
	}

	@RequestMapping("/portales/alumno/credeAlumnoQr")
	public ResponseEntity<byte[]> portalesAlumnoCredeAlumnoQr(HttpServletRequest request) {

		String codigo = request.getParameter("Codigo") == null ? "0" : request.getParameter("Codigo");

		String matricula = "0";
		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
		}

		AlumPersonal alumPersonal = alumPersonalDao.mapeaRegId(matricula);

		String textoQR = "https://academico.um.edu.mx/academico/certifica?Matricula=" + matricula + "&Codigo=" + codigo;
		byte[] bytes = QRCode.from(textoQR).withSize(270, 270).stream().toByteArray();

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);
		headers.setContentLength(bytes.length);
		return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.CREATED);
	}

	@RequestMapping("/portales/alumno/constanciaPdf")
	public String portalesAlumnoConstanciaPdf(HttpServletRequest request, Model modelo) {

		String cargaId 		= request.getParameter("CargaId") == null ? "0" : request.getParameter("CargaId");
		String bloqueId 	= request.getParameter("BloqueId") == null ? "0" : request.getParameter("BloqueId");

		String matricula 	= "0";
		HttpSession sesion 	= request.getSession();
		if (sesion != null) {
			matricula 		= (String) sesion.getAttribute("codigoAlumno");
		}

		AlumPersonal alumPersonal 	= alumPersonalDao.mapeaRegId(matricula);
		// FesCcobro cobro 			= fesCcobroDao.mapeaRegId(matricula, cargaId, bloqueId);
		String planNombre 			= mapaPlanDao.getCarreraSe(alumPersonalDao.getPlanActivo(matricula));
		Carga carga 				= cargaDao.mapeaRegId(cargaId);

		HashMap<String, String> mapaLibres = cargaPracticaAlumnoDao.mapaPorAlumnoyEstado(matricula, "L");
		HashMap<String, String> mapaPracticas = cargaPracticaAlumnoDao.mapaPorAlumnoyEstado(matricula, "A");
		List<CargaPracticaAlumno> lisPracticas = cargaPracticaAlumnoDao.lisPorAlumno(matricula, " ORDER BY FOLIO");

		modelo.addAttribute("SubMenu", "13");
		modelo.addAttribute("planNombre", planNombre);
		modelo.addAttribute("alumPersonal", alumPersonal);
		// modelo.addAttribute("cobro", cobro);
		modelo.addAttribute("carga", carga);
		modelo.addAttribute("mapaLibres", mapaLibres);
		modelo.addAttribute("mapaPracticas", mapaPracticas);
		modelo.addAttribute("lisPracticas", lisPracticas);

		return "portales/alumno/constanciaPdf";
	}

	@RequestMapping("/portales/alumno/residencia")
	public String portalesAlumnoResidencia(HttpServletRequest request, Model modelo) {
		String year = aca.util.Fecha.getHoy().substring(6, 10);
		String matricula = "0";

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
		}

		AlumPersonal alumPersonal = alumPersonalDao.mapeaRegId(matricula);

		modelo.addAttribute("SubMenu", "13");
		modelo.addAttribute("alumPersonal", alumPersonal);

		return "portales/alumno/residencia";
	}

	@RequestMapping("/portales/alumno/solicitudBeca")
	public String portalesAlumnoSolicitudBecas(HttpServletRequest request, Model modelo) {

		String mensaje = request.getParameter("Mensaje") == null ? "0" : request.getParameter("Mensaje");
		String matricula = "0";
		AlumPersonal alumPersonal = new AlumPersonal();

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
			alumPersonal = alumPersonalDao.mapeaRegId(matricula);
		}

		boolean acepto = alumBecaDao.existeReg(matricula);

		List<AlumDocumento> lisAlumDocumento = alumDocumentoDao.lisAlumDocumento(matricula);
		List<String> lisPlanes = alumPlanDao.getPlanesAlumno(matricula);
		List<BecSolPeriodo> lisPeriodos = becSolPeriodoDao.getAll(" ORDER BY 1 DESC");
		HashMap<String, MapaPlan> mapaPlanes = mapaPlanDao.mapPlanes("'V','I','A'");
		HashMap<String, BecSolicitud> mapaSolicitudes = becSolicitudDao.mapaSolicitudes();

		modelo.addAttribute("SubMenu", "13");

		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("matricula", matricula);
		modelo.addAttribute("acepto", acepto);
		modelo.addAttribute("alumPersonal", alumPersonal);

		modelo.addAttribute("lisAlumDocumento", lisAlumDocumento);
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		modelo.addAttribute("mapaSolicitudes", mapaSolicitudes);

		return "portales/alumno/solicitudBeca";
	}

	@RequestMapping("/portales/alumno/solicitudExternado")
	public String portalesAlumnoSolicitudExternado(HttpServletRequest request, Model modelo) {

		String mensaje = request.getParameter("Mensaje") == null ? "0" : request.getParameter("Mensaje");
		String matricula = "0";
		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
		}

		AlumPersonal alumPersonal = alumPersonalDao.mapeaRegId(matricula);

		List<AlumDocumento> lisAlumDocumentoFormato = alumDocumentoDao.lisAlumDocumentoFormato(matricula);
		List<ResExpediente> listaExpedientes = resExpedienteDao.listaPorCodigoPersonal(matricula);

		HashMap<String, String> mapaCantidadDcoumentos = resDocumentoDao.mapaCantidadDcoumentos(matricula);
		HashMap<String, String> mapNombrePlan = mapaPlanDao.mapNombrePlan();

		modelo.addAttribute("SubMenu", "13");
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("matricula", matricula);
		modelo.addAttribute("lisAlumDocumentoFormato", lisAlumDocumentoFormato);
		modelo.addAttribute("listaExpedientes", listaExpedientes);
		modelo.addAttribute("mapaCantidadDcoumentos", mapaCantidadDcoumentos);
		modelo.addAttribute("mapNombrePlan", mapNombrePlan);

		return "portales/alumno/solicitudExternado";
	}

	@RequestMapping("/portales/alumno/externado")
	public String portalesAlumnoExternado(HttpServletRequest request, Model modelo){
		String mensaje = request.getParameter("Mensaje") == null ? "0" : request.getParameter("Mensaje");
		String matricula = "0";
		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
		}

		AlumPersonal alumPersonal = alumPersonalDao.mapeaRegId(matricula);
		
		IntAlumno internado = new IntAlumno();

		if(intAlumnoDao.existeReg(matricula)){
			internado = intAlumnoDao.mapeaRegId(matricula);
		}

		IntDormitorio dormitorio = intDormitorioDao.mapeaRegId(internado.getDormitorioId());

		List<IntAlumno> lisRoomates = intDormitorioDao.getAlumnosEnCuarto(internado.getDormitorioId(), internado.getCuartoId(), " ORDER BY ORDEN");

		HashMap<String, AlumPersonal> mapaAlumnos = alumPersonalDao.mapAlumnosInternos(internado.getDormitorioId());

		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("matricula", matricula);
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("internado", internado);
		modelo.addAttribute("dormitorio", dormitorio);
		modelo.addAttribute("lisRoomates", lisRoomates);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);

		return "portales/alumno/externado";
	}

	@RequestMapping("/portales/alumno/nuevoExpediente")
	public String portalesAlumnoNuevoExpediente(HttpServletRequest request, Model modelo) {
		String matricula = "0";
		String folio = request.getParameter("Folio") == null ? "0" : request.getParameter("Folio");

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
		}

		List<String> lisPlanes = alumPlanDao.getPlanesAlumno(matricula);
		HashMap<String, String> mapNombrePlan = mapaPlanDao.mapNombrePlan();

		ResExpediente expediente = new ResExpediente();
		if (resExpedienteDao.existeReg(folio)) {
			expediente = resExpedienteDao.mapeaRegId(folio);
		}

		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("expediente", expediente);
		modelo.addAttribute("mapNombrePlan", mapNombrePlan);

		return "portales/alumno/nuevoExpediente";
	}

	@RequestMapping("/portales/alumno/grabarExpediente")
	public String portalesAlumnoGrabarExpediente(HttpServletRequest request) {
		String matricula = "0";
		String folio = request.getParameter("Folio") == null ? "0" : request.getParameter("Folio");
		String planId = request.getParameter("PlanId") == null ? "0" : request.getParameter("PlanId");
		String descripcion = request.getParameter("Descripcion") == null ? "0" : request.getParameter("Descripcion");
		String mensaje = "";
		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoPersonal");
		}

		ResExpediente expediente = new ResExpediente();
		if (folio.equals("0"))
			folio = resExpedienteDao.maximoReg();
		expediente.setFolio(folio);
		expediente.setDescripcion(descripcion);
		expediente.setEstado("S");
		expediente.setPlanId(planId);
		expediente.setCodigoPersonal(matricula);

		if (!resExpedienteDao.existeReg(folio)) {
			resExpedienteDao.insertReg(expediente);
			mensaje = "3";
		} else {
			resExpedienteDao.updateReg(expediente);
			mensaje = "3";
		}

		return "redirect:/portales/alumno/solicitudExternado?Mensaje=" + mensaje;
	}

	@RequestMapping("/portales/alumno/borrarExpediente")
	public String portalesAlumnoBorrarExpediente(HttpServletRequest request) {
		String matricula = "0";
		String folio = request.getParameter("Folio") == null ? "0" : request.getParameter("Folio");
		String mensaje = "";
		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoPersonal");
		}

		if (resExpedienteDao.existeReg(folio)) {
			if (resDocumentoDao.existeDocExpedienteReg(folio)) {
				resDocumentoDao.deleteDocExpedienteReg(folio);
			}
			resExpedienteDao.deleteReg(folio);
			mensaje = "4";
		}

		return "redirect:/portales/alumno/solicitudExternado?Mensaje=" + mensaje;
	}

	@RequestMapping("/portales/alumno/nuevoDocumento")
	public String portalesAlumnoNuevoDocumento(HttpServletRequest request, Model modelo) {
		String matricula = "0";
		String expedienteId = request.getParameter("ExpedienteId") == null ? "0" : request.getParameter("ExpedienteId");
		String mensaje = request.getParameter("Mensaje") == null ? "0" : request.getParameter("Mensaje");

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
		}

		ResDocumento documento = new ResDocumento();
		ResExpediente expediente = new ResExpediente();

		if (resExpedienteDao.existeReg(expedienteId)) {
			expediente = resExpedienteDao.mapeaRegId(expedienteId);
		}

		documento.setFolioExpediente(expedienteId);

		List<ResDocumento> listaDocumentos = resDocumentoDao.listaPorExpediente(expedienteId);

		modelo.addAttribute("listaDocumentos", listaDocumentos);
		modelo.addAttribute("documento", documento);
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("expediente", expediente);

		return "portales/alumno/nuevoDocumento";
	}

	@RequestMapping("/portales/alumno/grabarDocumento")
	public String portalesAlumnoGrabarDocumento(@RequestParam("archivo") MultipartFile file,
			HttpServletRequest request) {
		String folio = request.getParameter("Folio") == null ? "0" : request.getParameter("Folio");
		String expedienteId = request.getParameter("ExpedienteId") == null ? "0" : request.getParameter("ExpedienteId");
		String descripcion = request.getParameter("Descripcion") == null ? "0" : request.getParameter("Descripcion");
		String mensaje = "";

		ResExpediente expediente = new ResExpediente();
		ResDocumento documento = new ResDocumento();

		if (resExpedienteDao.existeReg(expedienteId)) {
			expediente = resExpedienteDao.mapeaRegId(expedienteId);
		}

		try {
			if (folio.equals("0"))
				folio = resDocumentoDao.maximoReg();
			documento.setFolio(folio);
			documento.setDescripcion(descripcion);
			documento.setFolioExpediente(expedienteId);
			documento.setDocumento(file.getBytes());
			documento.setNombre(file.getOriginalFilename());
			if (!resDocumentoDao.existeReg(folio)) {
				if (resDocumentoDao.insertReg(documento)) {
					expediente.setFecha(aca.util.Fecha.getHoy());
					resExpedienteDao.updateReg(expediente);					
					mensaje = "1";
				}				
			} else {				
				if (resDocumentoDao.updateReg(documento)) {
					expediente.setFecha(aca.util.Fecha.getHoy());
					resExpedienteDao.updateReg(expediente);
					mensaje = "1";
				}				
			}
		} catch (Exception ex) {
			System.out.println("Error saving documents for external residence status");	
		}
		
		return "redirect:/portales/alumno/nuevoDocumento?Mensaje=" + mensaje + "&ExpedienteId=" + expedienteId;
	}

	@RequestMapping("/portales/alumno/borrarDocumentoExpediente")
	public String portalesAlumnoBorrarDocumentoExpediente(HttpServletRequest request) {
		String folio = request.getParameter("Folio") == null ? "0" : request.getParameter("Folio");
		String expedienteId = request.getParameter("ExpedienteId") == null ? "0" : request.getParameter("ExpedienteId");
		String mensaje = "";

		if (resDocumentoDao.existeReg(folio)) {
			resDocumentoDao.deleteReg(folio);
			mensaje = "2";
		}

		return "redirect:/portales/alumno/nuevoDocumento?Mensaje=" + mensaje + "&ExpedienteId=" + expedienteId;
	}

	@RequestMapping("/portales/alumno/aceptoRequisitos")
	public String portalesAlumnoAceptoRequisitos(HttpServletRequest request) {
		String matricula = "0";

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
		}

		AlumBeca alumBeca = new AlumBeca();

		alumBeca.setCodigoPersonal(matricula);
		alumBeca.setBeca("S");

		alumBecaDao.insertReg(alumBeca);

		return "redirect:/portales/alumno/solicitudBeca";
	}

	@PostMapping("/portales/alumno/subirArchivoBeca")
	public String portalesAlumnoSubirArchivoBeca(@RequestParam("archivo") MultipartFile file,
			HttpServletRequest request) {
		// System.out.println("Pase por subirArchivo @@PostMapping...");
		String matricula = request.getParameter("Matricula") == null ? "0" : request.getParameter("Matricula");
		String planId = request.getParameter("PlanId") == null ? "0" : request.getParameter("PlanId");
		String periodoId = request.getParameter("PeriodoId") == null ? "0" : request.getParameter("PeriodoId");
		String mensaje = "";

		AlumDocumento alumno = new AlumDocumento();

		try {
			String folio = alumDocumentoDao.maximoReg(matricula);
			alumno.setCodigoPersonal(matricula);
			alumno.setFolio(folio);
			alumno.setPlanId(planId);
			alumno.setArchivo(file.getBytes());
			alumno.setEstado("S");
			alumno.setNombre(file.getOriginalFilename());
			alumno.setTipo("1");

			BecSolicitud becSolicitud = new BecSolicitud();

			if (becSolicitudDao.existeReg(matricula, folio)) {
				becSolicitud = becSolicitudDao.mapeaRegId(matricula, folio);
			}

			if (!alumDocumentoDao.existeReg(matricula, folio) && !planId.equals("0")) {
				if (alumDocumentoDao.insertReg(alumno)) {
					becSolicitud.setPeriodoId(periodoId);
					becSolicitudDao.updateReg(becSolicitud);
					mensaje = "1";
				}
			}
		} catch (Exception ex) {

		}

		return "redirect:/portales/alumno/solicitudBeca?Mensaje=" + mensaje;
	}

	@PostMapping("/portales/alumno/subirFormato")
	public String portalesAlumnoSubirFormato(@RequestParam("archivo") MultipartFile file, HttpServletRequest request) {

		String matricula = request.getParameter("Matricula") == null ? "0" : request.getParameter("Matricula");
		String planId = request.getParameter("PlanId") == null ? "0" : request.getParameter("PlanId");
		String tipo = request.getParameter("Tipo") == null ? "1" : request.getParameter("Tipo");
		String mensaje = "";

		AlumDocumento alumno = new AlumDocumento();

		try {
			String folio = alumDocumentoDao.maximoReg(matricula);
			alumno.setCodigoPersonal(matricula);
			alumno.setFolio(folio);
			alumno.setPlanId(planId);
			alumno.setArchivo(file.getBytes());
			alumno.setEstado("S");
			alumno.setNombre(file.getOriginalFilename());
			alumno.setTipo(tipo);

			if (!alumDocumentoDao.existeReg(matricula, folio)) {
				if (alumDocumentoDao.insertReg(alumno)) {
					mensaje = "1";
				}
			}
		} catch (Exception ex) {

		}

		return "redirect:/portales/alumno/seguro?Mensaje=" + mensaje;
	}

	@PostMapping("/portales/alumno/subirFoto")
	public String portalesAlumnoSubirFoto(@RequestParam("archivo") MultipartFile file, HttpServletRequest request) {

		String codigoPersonal = request.getParameter("CodigoPersonal") == null ? "0"
				: request.getParameter("CodigoPersonal");
		String usuario = "";
		String mensaje = "";

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			usuario = (String) sesion.getAttribute("codigoEmpleado");
		}

		PosFotoAlum foto = new PosFotoAlum();
		try {
			foto.setFoto(file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}

		foto.setCodigoPersonal(codigoPersonal);
		foto.setTipo("A");
		foto.setFecha(aca.util.Fecha.getHoy());
		foto.setUsuario(usuario);

		if (!posFotoAlumDao.existeReg(codigoPersonal, "A")) {
			if (posFotoAlumDao.insertReg(foto)) {
				mensaje = "1";
			}
		}

		return "redirect:/portales/alumno/resumen?Mensaje=" + mensaje;
	}

	@RequestMapping("/portales/alumno/borrarFoto")
	public String portalesAlumnoBorrarFoto(HttpServletRequest request) {

		String codigoPersonal = request.getParameter("CodigoPersonal") == null ? "0"
				: request.getParameter("CodigoPersonal");
		String tipo = request.getParameter("Tipo") == null ? "A" : request.getParameter("Tipo");
		String usuario = "0";
		HttpSession sesion = request.getSession();
		if (sesion != null) {
			usuario = (String) sesion.getAttribute("codigoPersonal");
		}

		try {
			if (posFotoAlumDao.existeReg(codigoPersonal, tipo) && codigoPersonal.equals(usuario)) {
				posFotoAlumDao.deleteReg(codigoPersonal, tipo);
			}
		} catch (Exception ex) {
			System.out.println("Error al borrar la foto" + ex);
		}
		return "redirect:/portales/alumno/resumen";
	}

	@RequestMapping("/portales/alumno/borrarDocumento")
	public String portalesAlumnoBorrarDocumento(HttpServletRequest request) {
		String matricula = request.getParameter("Matricula") == null ? "0" : request.getParameter("Matricula");
		String folio = request.getParameter("Folio") == null ? "0" : request.getParameter("Folio");
		String mensaje = "";

		if (alumDocumentoDao.existeReg(matricula, folio)) {
			if (alumDocumentoDao.deleteReg(matricula, folio)) {
				mensaje = "2";
			}
		}

		return "redirect:/portales/alumno/solicitudBeca?Mensaje=" + mensaje;
	}

	@RequestMapping("/portales/alumno/borrarFormato")
	public String portalesAlumnoBorrarFormato(HttpServletRequest request) {
		String matricula = request.getParameter("Matricula") == null ? "0" : request.getParameter("Matricula");
		String folio = request.getParameter("Folio") == null ? "0" : request.getParameter("Folio");
		String mensaje = "";

		if (alumDocumentoDao.existeReg(matricula, folio)) {
			if (alumDocumentoDao.deleteReg(matricula, folio)) {
				mensaje = "2";
			}
		}

		return "redirect:/portales/alumno/seguro?Mensaje=" + mensaje;
	}

	@RequestMapping("/portales/alumno/validaUsuarioMovil")
	public String portalesAlumnoValidaUsuarioMovil(HttpServletRequest request, Model modelo) {
		AlumPersonal alumPersonal = new AlumPersonal();

		String mensaje = request.getParameter("Mensaje") == null ? "0" : request.getParameter("Mensaje");

		String codigoAlumno = "0";
		HttpSession sesion = request.getSession();
		if (sesion != null) {
			codigoAlumno = (String) sesion.getAttribute("codigoAlumno");
		}
		boolean existeCodigo = false;

		RestTemplate restTemplate = new RestTemplate();

		String url = "https://wso2is.um.edu.mx/t/um.movil/api/identity/user/v1.0/validate-username";

		// Headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "Basic bGFmdWVudGVAdW0ubW92aWw6Sm9rZXIyNDA0");
		// Body
		Map<String, Object> map = new HashMap<>();
		map.put("username", codigoAlumno + "@um.movil");

		// Data attached to the request.
		HttpEntity<Map<String, Object>> requestBody = new HttpEntity<>(map, headers);

		// Send request with POST method.
		ResponseEntity<String> result = restTemplate.postForEntity(url, requestBody, String.class);

		if (result.getStatusCodeValue() == 200) {
			if (result.getBody().contains("60002")) {
				existeCodigo = true;
			}
		}

		if (alumPersonalDao.existeAlumno(codigoAlumno)) {
			alumPersonal = alumPersonalDao.mapeaRegId(codigoAlumno);
		}

		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("codigoAlumno", codigoAlumno);
		modelo.addAttribute("existeCodigo", existeCodigo);
		modelo.addAttribute("mensaje", mensaje);

		return "portales/alumno/validaUsuarioMovil";
	}

	@RequestMapping("/portales/alumno/registrarUsuarioMovil")
	public String portalesAlumnoRegistrarUsuarioMovil(HttpServletRequest request, Model modelo) {
		AlumPersonal alumPersonal = new AlumPersonal();

		String password = request.getParameter("Pass") == null ? "0" : request.getParameter("Pass");
		String mensaje = "0";
		String codigoAlumno = "0";
		HttpSession sesion = request.getSession();
		if (sesion != null) {
			codigoAlumno = (String) sesion.getAttribute("codigoAlumno");
		}

		if (alumPersonalDao.existeAlumno(codigoAlumno)) {
			alumPersonal = alumPersonalDao.mapeaRegId(codigoAlumno);
		}

		RestTemplate restTemplate = new RestTemplate();

		String url = "https://wso2is.um.edu.mx/t/um.movil/api/identity/user/v1.0/me";

		User usuario = new User();
		List<Claims> claims = new ArrayList<Claims>();

		claims.add(new Claims("http://wso2.org/claims/givenname", alumPersonal.getNombre()));
		claims.add(new Claims("http://wso2.org/claims/emailaddress", alumPersonal.getEmail()));
		claims.add(new Claims("http://wso2.org/claims/lastname",
				alumPersonal.getApellidoPaterno() + " " + alumPersonal.getApellidoMaterno()));
		claims.add(new Claims("http://wso2.org/claims/userid", codigoAlumno));

		usuario.setUsername(codigoAlumno);
		usuario.setRealm("PRIMARY");
		usuario.setPassword(password);
		usuario.setClaims(claims);

		// Headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "Basic bGFmdWVudGVAdW0ubW92aWw6Sm9rZXIyNDA0");
		// Body
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user", usuario);

		// Data attached to the request.
		HttpEntity<Map<String, Object>> requestBody = new HttpEntity<>(map, headers);

		// Send request with POST method.
		ResponseEntity<String> result = restTemplate.postForEntity(url, requestBody, String.class);

		if (result.getStatusCodeValue() != 201) {
			mensaje = "1";
		} else if (result.getStatusCodeValue() == 201) {
			mensaje = "3";
		}

		return "redirect:/portales/alumno/validaUsuarioMovil?Mensaje=" + mensaje;
	}

	@RequestMapping("/portales/alumno/cambiarPasswordMovil")
	public String portalesAlumnoCambiarPasswordMovil(HttpServletRequest request, Model modelo) {
		String password = request.getParameter("Pass") == null ? "0" : request.getParameter("Pass");
		String mensaje = request.getParameter("Mensaje") == null ? "0" : request.getParameter("Mensaje");
		String llave = "";

		String codigoAlumno = "0";
		HttpSession sesion = request.getSession();
		if (sesion != null) {
			codigoAlumno = (String) sesion.getAttribute("codigoAlumno");
		}

		UserPas usuario = new UserPas();
		usuario.setUsername(codigoAlumno);

		RestTemplate restTemplate = new RestTemplate();

		String url = "https://wso2is.um.edu.mx/t/um.movil/api/identity/recovery/v0.9/recover-password?type=email&notify=false";

		// Headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "Basic bGFmdWVudGVAdW0ubW92aWw6Sm9rZXIyNDA0");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user", usuario);

		// Data attached to the request.
		HttpEntity<Map<String, Object>> requestBody = new HttpEntity<>(map, headers);

		// Send request with POST method.
		ResponseEntity<String> result = restTemplate.postForEntity(url, requestBody, String.class);

		if (result.getStatusCodeValue() == 202) {
			llave = result.getBody();
		}

		url = "https://wso2is.um.edu.mx/t/um.movil/api/identity/recovery/v0.9/set-password";

		// Headers
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "Basic bGFmdWVudGVAdW0ubW92aWw6Sm9rZXIyNDA0");
		// Body
		map = new HashMap<>();
		map.put("key", llave);
		map.put("password", password);

		// Data attached to the request.
		requestBody = new HttpEntity<>(map, headers);

		// Send request with POST method.
		result = restTemplate.postForEntity(url, requestBody, String.class);

		if (result.getStatusCodeValue() != 200) {
			mensaje = "2";
		} else if (result.getStatusCodeValue() == 200) {
			mensaje = "3";
		}

		return "redirect:/portales/alumno/validaUsuarioMovil?Mensaje=" + mensaje;
	}

	@RequestMapping("/portales/alumno/oferta")
	public String matriculaCargaSimilares(HttpServletRequest request, Model modelo) {

		String planId = request.getParameter("PlanId") == null ? "0" : request.getParameter("PlanId");
		String planNombre = mapaPlanDao.getCarreraSe(planId);
		String codigoAlumno = "0";
		String alumnoNombre = "-";
		

		AlumAcademico academico = new AlumAcademico();
		HttpSession sesion = request.getSession();
		if (sesion != null) {
			codigoAlumno = (String) sesion.getAttribute("codigoAlumno");
			academico = alumAcademicoDao.mapeaRegId(codigoAlumno);
			alumnoNombre = alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
		}

		List<String> lisCiclos = cargaAcademicaDao.lisCiclosProximos(planId);
		List<CargaAcademica> lisMaterias = cargaAcademicaDao.lisMateriasProximas(planId, "ORDER BY CICLO, NOMBRE");
		HashMap<String, CatModalidad> mapaModalidades = catModalidadDao.getMapAll("");
		HashMap<String, CargaBloque> mapaBloques = cargaBloqueDao.mapaBloques();
		Map<String, String> mapaAlumnoCurso = alumnoCursoDao.mapMateriaAc(codigoAlumno);

		modelo.addAttribute("planNombre", planNombre);
		modelo.addAttribute("alumnoNombre", alumnoNombre);

		modelo.addAttribute("lisCiclos", lisCiclos);
		modelo.addAttribute("lisMaterias", lisMaterias);
		modelo.addAttribute("mapaModalidades", mapaModalidades);
		modelo.addAttribute("mapaBloques", mapaBloques);
		modelo.addAttribute("mapaAlumnoCurso", mapaAlumnoCurso);
		modelo.addAttribute("academico", academico);

		return "portales/alumno/oferta";
	}

	@RequestMapping("/portales/alumno/descargarConvenio")
	public void tallerPuestoDescargarContrato(HttpServletResponse response, HttpServletRequest request) {
		String codigoAlumno = request.getParameter("CodigoAlumno") == null ? "0" : request.getParameter("CodigoAlumno");
		String puesto = request.getParameter("Puesto") == null ? "0" : request.getParameter("Puesto");

		BecContrato becContrato = new BecContrato();
		if (becContratoDao.existeReg(codigoAlumno, puesto)) {
			becContrato = becContratoDao.mapeaRegId(codigoAlumno, puesto);
		}
		try {
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + becContrato.getNombre() + "\"");
			response.getOutputStream().write(becContrato.getArchivo());
			response.flushBuffer();
		} catch (IOException e) {
			System.out.println("ERROR");
			e.printStackTrace();
		}
	}

	@RequestMapping("/portales/alumno/retorno")
	public String portalesAlumnoRetorno(HttpServletRequest request, Model modelo) {

		String mensaje = request.getParameter("Mensaje") == null ? "0" : request.getParameter("Mensaje");
		String matricula = "0";
		String periodoId = alertaPeriodoDao.getPeriodoActivo();
		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
		}

		AlumPersonal alumPersonal 	= alumPersonalDao.mapeaRegId(matricula);
		AlumUbicacion alumUbicacion = new AlumUbicacion();
		
		if(alumUbicacionDao.existeReg(matricula)) {
			alumUbicacion = alumUbicacionDao.mapeaRegId(matricula);
		}
		
		HashMap<String, String> mapaDocumentos = alertaDocAlumDao.mapaAlumDocumento(matricula, periodoId);

		modelo.addAttribute("SubMenu", "13");
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("matricula", matricula);
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("alumUbicacion", alumUbicacion);
		modelo.addAttribute("mapaDocumentos", mapaDocumentos);

		return "portales/alumno/retorno";
	}
	
	@RequestMapping("/portales/alumno/grabarVacuna")
	public String portalesAlumnoGrabarVacuna(HttpServletRequest request) {
		
		String mensaje = "0";
		String matricula = "0";
		String vacunado = request.getParameter("Vacunado") == null ? "N" : request.getParameter("Vacunado");
		String descVacuna = request.getParameter("DescVacuna") == null ? "-" : request.getParameter("DescVacuna");
		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
		}
		
		AlumUbicacion alumUbicacion = new AlumUbicacion();
		
		if(alumUbicacionDao.existeReg(matricula)) {
			alumUbicacion = alumUbicacionDao.mapeaRegId(matricula);
			alumUbicacion.setVacuna(vacunado);
			alumUbicacion.setDescripcionVacuna(descVacuna);
			
			if(alumUbicacionDao.updateReg(alumUbicacion)) {
				mensaje = "1";
			}
		}

		return "redirect:/portales/alumno/retorno?Mensaje="+mensaje;
	}

	@RequestMapping("/portales/alumno/subirRetorno")
	public String portalesAlumnoSubirRetorno(HttpServletRequest request, Model modelo) {

		String periodoId = alertaPeriodoDao.getPeriodoActivo();
		String matricula = "0";
		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
		}

		AlumPersonal alumPersonal = alumPersonalDao.mapeaRegId(matricula);

		modelo.addAttribute("SubMenu", "13");
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("matricula", matricula);
		modelo.addAttribute("periodoId", periodoId);

		return "portales/alumno/subirRetorno";
	}

	@RequestMapping("/portales/alumno/grabarRetorno")
	public String portalesAlumnoGrabarRetorno(HttpServletRequest request, Model modelo,
			@RequestParam("archivo") MultipartFile archivo) {

		String codigoAlumno = "0";
		String periodoId = request.getParameter("PeriodoId") == null ? "0" : request.getParameter("PeriodoId");
		String documentoId = request.getParameter("DocumentoId") == null ? "0" : request.getParameter("DocumentoId");
		String mensaje = "-";

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			codigoAlumno = (String) sesion.getAttribute("codigoAlumno");
		}
		try {
			String nombreArchivo = archivo.getOriginalFilename();
			byte[] file = archivo.getBytes();

			AlertaDocAlum documento = new AlertaDocAlum();
			documento.setCodigoPersonal(codigoAlumno);
			documento.setPeriodoId(periodoId);
			documento.setDocumentoId(documentoId);
			documento.setArchivo(file);
			documento.setNombre(nombreArchivo);
			if (!alertaDocAlumDao.existeReg(codigoAlumno, periodoId, documentoId)) {
				if (alertaDocAlumDao.insertReg(documento)) {
					mensaje = "File sent";
				} else {
					mensaje = "Error sending file";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/portales/alumno/subirRetorno?Mensaje=" + mensaje;
	}

	@RequestMapping("/portales/alumno/descargarRetorno")
	public void portalesAlumnoDescargarRetorno(HttpServletResponse response, HttpServletRequest request) {

		String codigoAlumno = "0";
		String periodoId = alertaPeriodoDao.getPeriodoActivo();
		String documentoId = request.getParameter("DocumentoId") == null ? "0" : request.getParameter("DocumentoId");

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			codigoAlumno = (String) sesion.getAttribute("codigoAlumno");
		}

		AlertaDocAlum documento = new AlertaDocAlum();
		if (alertaDocAlumDao.existeReg(codigoAlumno, periodoId, documentoId)) {
			documento = alertaDocAlumDao.mapeaRegId(codigoAlumno, periodoId, documentoId);
		}
		try {
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + documento.getNombre() + "\"");
			response.getOutputStream().write(documento.getArchivo());
			response.flushBuffer();
		} catch (IOException e) {
			System.out.println("ERROR");
			e.printStackTrace();
		}
	}

	@RequestMapping("/portales/alumno/borrarRetorno")
	public String portalesAlumnoBorrarRetorno(HttpServletRequest request) {
		String codigoAlumno = "0";
		String periodoId = alertaPeriodoDao.getPeriodoActivo();
		String documentoId = request.getParameter("DocumentoId") == null ? "0" : request.getParameter("DocumentoId");
		String mensaje = "-";
		HttpSession sesion = request.getSession();
		if (sesion != null) {
			codigoAlumno = (String) sesion.getAttribute("codigoAlumno");
		}

		if (alertaDocAlumDao.existeReg(codigoAlumno, periodoId, documentoId)) {
			if (alertaDocAlumDao.deleteReg(codigoAlumno, periodoId, documentoId)) {
				mensaje = "File deleted";
			} else {
				mensaje = "Error deleting file";
			}
		}

		return "redirect:/portales/alumno/retorno?Mensaje=" + mensaje;
	}

	@RequestMapping("/portales/alumno/bajarExterno")
	public void portalesAlumnoBajarExterno(HttpServletRequest request, HttpServletResponse response) {

		String folio = request.getParameter("Folio") == null ? "0" : request.getParameter("Folio");
		ResDocumento resDocumento = new ResDocumento();
		try {
			if (resDocumentoDao.existeReg(folio)) {
				resDocumento = resDocumentoDao.mapeaRegId(folio);
				OutputStream out = response.getOutputStream();
				response.setHeader("Content-Disposition", "attachment; filename=\"" + resDocumento.getNombre() + "\"");
				out.write(resDocumento.getDocumento());
			}
		} catch (Exception ex) {
			System.out.println("Error:portales/alumno/bajarArchivo:" + ex);
		}
	}

	@RequestMapping("/portales/alumno/calculo")
	public String portalesAlumnoCalculo(HttpServletRequest request, Model modelo) {

		String cargaId = "0";
		String bloqueId = "0";
		String mensaje = request.getParameter("Mensaje") == null ? "0" : request.getParameter("Mensaje");

		HttpSession sesion = request.getSession();
		String codigoAlumno = "0";
		String nombreAlumno = "-";
		String tipoAlumno = "-";
		String planCarga = "0";
		float saldoAnterior = 0;
		String ingreso = "N";
		boolean existePagare = false;

		AlumPlan plan = new AlumPlan();
		AlumPersonal personal = new AlumPersonal();
		AlumAcademico academico = new AlumAcademico();
		MapaPlan mapaPlan = new MapaPlan();
		CalAlumno calAlumno = new CalAlumno();
		List<MapaCurso> lisMaterias = new ArrayList<MapaCurso>();
		List<CargaAcademica> lisOferta = new ArrayList<CargaAcademica>();
		List<KrdxCursoAct> lisCurso = new ArrayList<KrdxCursoAct>();

		if (sesion != null) {
			codigoAlumno = (String) sesion.getAttribute("codigoAlumno");
			nombreAlumno = usuariosDao.getNombreUsuario(codigoAlumno, "NOMBRE");
			cargaId = (String) sesion.getAttribute("cargaId");
			bloqueId = (String) sesion.getAttribute("bloqueId");
			ingreso = cargaAlumnoDao.getIngreso(codigoAlumno, cargaId, bloqueId);

			if (codigoAlumno.subSequence(0, 1).equals("0") || codigoAlumno.subSequence(0, 1).equals("1")
					|| codigoAlumno.subSequence(0, 1).equals("2")) {
				planCarga = cargaAlumnoDao.getPlanId(codigoAlumno, cargaId, bloqueId);
				plan = alumPlanDao.mapeaRegId(codigoAlumno, planCarga);
				mapaPlan = mapaPlanDao.mapeaRegId(plan.getPlanId());
				personal = alumPersonalDao.mapeaRegId(codigoAlumno);
				academico = alumAcademicoDao.mapeaRegId(codigoAlumno);
				tipoAlumno = catTipoAlumnoDao.getNombreTipo(academico.getTipoAlumno());
			}

			lisMaterias = mapaCursoDao.getLista(plan.getPlanId(), " ORDER BY CICLO, NOMBRE_CURSO");
			lisOferta = cargaAcademicaDao.getListaCarga(cargaId, plan.getPlanId(), academico.getModalidadId(),
					" ORDER BY CICLO, NOMBRE_CURSO");
			lisCurso = krdxCursoActDao.lisMateriasConfirmadasEnCarga(codigoAlumno, cargaId, " ORDER BY CURSO_ID");
		}

		if (calAlumnoDao.existeReg(codigoAlumno, cargaId, bloqueId)) {
			calAlumno = calAlumnoDao.mapeaRegId(codigoAlumno, cargaId, bloqueId);
			existePagare = true;
		}

		// Consulta el saldo del estudiante
		FinSaldo finSaldo = new FinSaldo();
		try {
			RestTemplate restTemplate = new RestTemplate();
			finSaldo = restTemplate.getForObject(
					"http://172.16.251.110:8080/infor/reportes/api/fe/saldo/" + codigoAlumno, FinSaldo.class);
		} catch (Exception ex) {
			System.out.println("Error en saldo:" + codigoAlumno);
		}

		List<CalMovimiento> listaMovimientos = calMovimientoDao.lisTodos("WHERE CODIGO_PERSONAL = '" + codigoAlumno
				+ "' AND CARGA_ID = '" + cargaId + "' AND BLOQUE_ID = " + bloqueId);
		List<CalPagareAlumno> listaParageAlumno = calPagareAlumnoDao.lisPorCarga(cargaId, bloqueId,
				"AND CODIGO_PERSONAL = " + codigoAlumno + " ORDER BY ENOC.CAL_PAGARE_ALUMNO.FECHA");

		List<CalCosto> lisCostos = calCostoDao.lisPorCarga(cargaId, bloqueId, "ORDER BY CONCEPTO_ID");
		List<CalPagare> lisPagares = calPagareDao.lisPorCarga(cargaId, bloqueId, "");
		List<CalConcepto> lisDescuentos = calConceptoDao.lisTodos("WHERE TIPO = 'C'");
		HashMap<String, FinTabla> mapaCostos = finTablaDao.mapTablaCargas("'" + cargaId + "'");
		HashMap<String, KrdxCursoAct> mapaKardexCurso = krdxCursoActDao.getMapAlumCurso(codigoAlumno);
		HashMap<String, String> mapaConfirmadas = krdxCursoActDao.mapaConfirmadas(codigoAlumno);
		HashMap<String, String> mapaMateriaCosto = cargaGrupoDao.mapaMateriaCosto(cargaId);
		HashMap<String, CalConcepto> mapaConceptos = calConceptoDao.mapaTodos();
		HashMap<String, String> mapaConceptoMovimientos = calMovimientoDao
				.mapaConceptoMovimientos("WHERE CODIGO_PERSONAL = '" + codigoAlumno + "' AND CARGA_ID = '" + cargaId
						+ "' AND BLOQUE_ID = " + bloqueId);

		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("mapaConceptoMovimientos", mapaConceptoMovimientos);
		modelo.addAttribute("existePagare", existePagare);
		modelo.addAttribute("bloqueId", bloqueId);
		modelo.addAttribute("cargaNombre", cargaDao.getNombreCarga(cargaId));
		modelo.addAttribute("bloqueNombre", cargaBloqueDao.getNombre(cargaId, bloqueId));
		modelo.addAttribute("personal", personal);
		modelo.addAttribute("academico", academico);
		modelo.addAttribute("tipoAlumno", tipoAlumno);
		modelo.addAttribute("calAlumno", calAlumno);
		modelo.addAttribute("ingreso", ingreso);
		modelo.addAttribute("finSaldo", finSaldo);

		modelo.addAttribute("lisMaterias", lisMaterias);
		modelo.addAttribute("lisCostos", lisCostos);
		modelo.addAttribute("lisOferta", lisOferta);
		modelo.addAttribute("lisCurso", lisCurso);
		modelo.addAttribute("lisPagares", lisPagares);
		modelo.addAttribute("listaMovimientos", listaMovimientos);
		modelo.addAttribute("lisDescuentos", lisDescuentos);
		modelo.addAttribute("listaParageAlumno", listaParageAlumno);

		modelo.addAttribute("mapaKardexCurso", mapaKardexCurso);
		modelo.addAttribute("mapaConfirmadas", mapaConfirmadas);
		modelo.addAttribute("mapaCostos", mapaCostos);
		modelo.addAttribute("mapaMateriaCosto", mapaMateriaCosto);
		modelo.addAttribute("mapaPlan", mapaPlan);
		modelo.addAttribute("mapaConceptos", mapaConceptos);

		return "portales/alumno/calculo";
	}

	@RequestMapping("/portales/alumno/cambiarS")
	public String portalesAlumnoCambiarS(HttpServletRequest request, Model modelo) {

		String cargaId = request.getParameter("CargaId") == null ? "0" : request.getParameter("CargaId");
		String bloqueId = request.getParameter("BloqueId") == null ? "0" : request.getParameter("BloqueId");
		String matricula = "0";
		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
		}

		if (calAlumnoDao.updateConfirmar(matricula, cargaId, bloqueId, "S")) {

		}

		return "redirect:/portales/alumno/calculo?CargaId=" + cargaId + "&BloqueId=" + bloqueId;
	}

	@RequestMapping("/portales/alumno/grabarPagare")
	public String portalesAlumnoGrabarPagare(HttpServletRequest request) {
		String cargaId = request.getParameter("CargaId") == null ? "0" : request.getParameter("CargaId");
		String bloqueId = request.getParameter("BloqueId") == null ? "0" : request.getParameter("BloqueId");
		String numPagares = request.getParameter("NumPagare") == null ? "0" : request.getParameter("NumPagare");
		String cobroMatricula = request.getParameter("CobroMatricula") == null ? "0"
				: request.getParameter("CobroMatricula");
		String saldo = request.getParameter("Saldo") == null ? "0" : request.getParameter("Saldo");
		String porcentaje = request.getParameter("Porcentaje") == null ? "35" : request.getParameter("Porcentaje");
		String totMat = request.getParameter("TotMat") == null ? "0" : request.getParameter("TotMat");
		String totEns = request.getParameter("TotEns") == null ? "0" : request.getParameter("TotEns");
		String totDes = request.getParameter("TotDes") == null ? "0" : request.getParameter("TotDes");
		String mensaje = "0";
		String ingreso = "N";
		String planId = "00000000";
		String facultadId = "000";

		if (cobroMatricula.equals("N"))
			totMat = "0";

		float netoSaldo = Float.valueOf(saldo);
		float netoMat = Float.valueOf(totMat);
		float netoEns = Float.valueOf(totEns);
		float netoDes = Float.valueOf(totDes);
		float netoTotal = 0;
		float netoInicial = 0;
		float sumaPagares = 0;

		java.text.DecimalFormat formato = new java.text.DecimalFormat("###,##0.00;-###,##0.00");

		HttpSession sesion = request.getSession();
		String codigoAlumno = "0";
		if (sesion != null) {
			codigoAlumno = (String) sesion.getAttribute("codigoAlumno");
			ingreso = cargaAlumnoDao.getIngreso(codigoAlumno, cargaId, bloqueId);
			planId = cargaAlumnoDao.getPlanId(codigoAlumno, cargaId, bloqueId);
			facultadId = catCarreraDao.getFacultadId(mapaPlanDao.getCarreraId(planId));
			if (Float.valueOf(saldo) < 0) {
				netoMat = netoMat + Math.abs(Float.valueOf(saldo));
				netoSaldo = 0;
			} else {
				if (netoSaldo > netoMat) {
					netoSaldo = netoSaldo - netoMat;
					netoMat = 0;
				} else {
					netoMat = netoMat - netoSaldo;
					netoSaldo = 0;
				}
			}
			// System.out.println("NetoMat:"+netoMat+" NetoSaldo:"+netoSaldo);
			if (netoDes > netoEns) {
				netoEns = 0;
				netoDes = netoDes - netoEns;
			} else {
				netoEns = netoEns - netoDes;
			}

			netoTotal = netoMat + netoEns;
			// La facultad de UM Virtual es la 113
			if (ingreso.equals("R") && facultadId.equals("113")) {
				if (netoSaldo >= netoTotal) {
					netoTotal = 0;
					netoSaldo = netoSaldo - netoTotal;
					netoInicial = 0;
					sumaPagares = 0;
				} else {
					netoTotal = netoTotal - netoSaldo;
					netoSaldo = 0;
					netoInicial = netoTotal / Integer.parseInt(numPagares);
					sumaPagares = netoTotal - netoInicial;
				}
			} else {
				netoInicial = netoMat + (netoEns * Float.valueOf(porcentaje) / 100);
				if (netoSaldo >= netoTotal) {
					netoInicial = 0;
					netoTotal = 0;
					netoSaldo = netoSaldo - netoTotal;
					sumaPagares = 0;
				} else if (netoSaldo > netoInicial) {
					netoTotal = netoTotal - netoSaldo;
					netoInicial = 0;
					netoSaldo = netoSaldo - netoInicial;
					sumaPagares = netoTotal;
				} else {
					netoInicial = netoInicial - netoSaldo;
					netoTotal = netoTotal - netoInicial - netoSaldo;
					netoSaldo = 0;
					sumaPagares = netoTotal;
				}
				// sumaPagares = netoTotal - netoInicial;
			}
		}

		CalAlumno alumno = new CalAlumno();

		alumno.setCodigoPersonal(codigoAlumno);
		alumno.setCargaId(cargaId);
		alumno.setBloqueId(bloqueId);
		alumno.setNumPagare(numPagares);
		alumno.setCobroMatricula(cobroMatricula);
		alumno.setSaldo(saldo);
		alumno.setPorcentaje(porcentaje);

		if (calAlumnoDao.existeReg(codigoAlumno, cargaId, bloqueId)) {
			if (calAlumnoDao.updateReg(alumno)) {
				mensaje = "1";
			} else {
				mensaje = "2";
			}
		} else {
			if (calAlumnoDao.insertReg(alumno)) {
				mensaje = "1";
			} else {
				mensaje = "2";
			}
		}

		CalPagareAlumno pagareAlumno = new CalPagareAlumno();

		List<CalPagare> lisPagares = calPagareDao.lisPorCarga(cargaId, bloqueId, "");

		pagareAlumno.setCodigoPersonal(codigoAlumno);
		pagareAlumno.setBloqueId(bloqueId);
		pagareAlumno.setCargaId(cargaId);

		if (mensaje.equals("1")) {
			if (calPagareAlumnoDao.existeReg(codigoAlumno, cargaId, bloqueId)) {
				calPagareAlumnoDao.deleteReg(codigoAlumno, cargaId, bloqueId);
			}

			int row = 1;
			for (CalPagare pagare : lisPagares) {
				if (numPagares.equals("1")) {
					pagareAlumno.setPagareNombre("Pago único");
					pagareAlumno.setFecha(pagare.getFecha());
					pagareAlumno.setImporte(String.valueOf(formato.format(netoTotal)));
					pagareAlumno.setPagareId(pagare.getPagareId());
					calPagareAlumnoDao.insertReg(pagareAlumno);
					break;
				} else if (row <= Integer.parseInt(numPagares)) {

					float pago = sumaPagares / (Integer.parseInt(numPagares) - 1);

					if (row == 1) {
						pagareAlumno.setPagareNombre("Pago inicial");
						pagareAlumno.setImporte(String.valueOf(formato.format(netoInicial)));
					} else {
						pagareAlumno.setPagareNombre(pagare.getPagareNombre());
						pagareAlumno.setImporte(String.valueOf(formato.format(pago)));
					}
					pagareAlumno.setPagareId(pagare.getPagareId());
					pagareAlumno.setFecha(pagare.getFecha());

					calPagareAlumnoDao.insertReg(pagareAlumno);
				}
				row++;
			}
		}

		return "redirect:/portales/alumno/calculo?Mensaje=" + mensaje;
	}

	@RequestMapping("/portales/alumno/listaEventos")
	public String portalesAlumnoListaEventos(HttpServletRequest request, Model modelo) {

		String codigoAlumno = "0";
		String nombreAlumno = "-";
		HttpSession sesion = request.getSession();
		if (sesion != null) {
			codigoAlumno = (String) sesion.getAttribute("codigoAlumno");
			nombreAlumno = alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
		}

		List<AlumEgreso> lisEventos = alumEgresoDao.lisEventosActivos(codigoAlumno, " ORDER BY EVENTO_ID");
		HashMap<String, AlumEvento> mapEventos = alumEventoDao.mapEventos();

		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("lisEventos", lisEventos);
		modelo.addAttribute("mapEventos", mapEventos);

		return "portales/alumno/listaEventos";
	}

	@RequestMapping("/portales/alumno/confirmarAsistencia")
	public String portalesAlumnoConfirmarAsistencia(HttpServletRequest request, Model modelo) {

		String codigoAlumno = "0";
		String nombreAlumno = "-";
		HttpSession sesion = request.getSession();
		if (sesion != null) {
			codigoAlumno = (String) sesion.getAttribute("codigoAlumno");
			nombreAlumno = alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
		}
		String eventoId = request.getParameter("EventoId") == null ? "N" : request.getParameter("EventoId");
		String confirmar = request.getParameter("Confirmar") == null ? "N" : request.getParameter("Confirmar");
		String mensaje = "-";

		if (alumEgresoDao.updateConfirmar(eventoId, codigoAlumno, confirmar)) {
			if (confirmar.equals("S"))
				mensaje = "Your attendance has been registered!";
			if (confirmar.equals("N"))
				mensaje = "Thank you for informing that you will not be at the event";
		}

		return "redirect:/portales/alumno/listaEventos?Mensaje=" + mensaje;
	}

	@RequestMapping("/portales/alumno/intencionRegreso")
	public String portalesAlumnoIntencionRegreso(HttpServletRequest request, Model modelo) {

		String codigoAlumno = "0";
		HttpSession sesion = request.getSession();
		if (sesion != null) {
			codigoAlumno = (String) sesion.getAttribute("codigoAlumno");
		}
		String periodoId = request.getParameter("PeriodoId") == null ? "0" : request.getParameter("PeriodoId");

		EncPeriodoRes encPeriodoRes = new EncPeriodoRes();

		if (encPeriodoResDao.existeReg(codigoAlumno, periodoId)) {
			encPeriodoRes = encPeriodoResDao.mapeaRegId(codigoAlumno, periodoId);
		} else {
			encPeriodoRes.setCodigoPersonal(codigoAlumno);
			encPeriodoRes.setPeriodoId(periodoId);
		}

		modelo.addAttribute("encPeriodoRes", encPeriodoRes);

		return "portales/alumno/intencionRegreso";
	}

	@RequestMapping("/portales/alumno/grabarIntencion")
	public String portalesAlumnoGrabarIntencion(HttpServletRequest request, Model modelo) {

		String mensajeEnc = "0";
		String codigoAlumno = "0";
		HttpSession sesion = request.getSession();
		if (sesion != null) {
			codigoAlumno = (String) sesion.getAttribute("codigoAlumno");
		}

		String periodoId = request.getParameter("PeriodoId") == null ? "0" : request.getParameter("PeriodoId");
		String finAlumno = request.getParameter("FinAlumno") == null ? "N" : request.getParameter("FinAlumno");
		String finPor = request.getParameter("FinPor") == null ? "0" : request.getParameter("FinPor");
		String finColpor = request.getParameter("FinColpor") == null ? "N" : request.getParameter("FinColpor");
		String finTrabajo = request.getParameter("FinTrabajo") == null ? "N" : request.getParameter("FinTrabajo");
		String finOtro = request.getParameter("FinOtro") == null ? "" : request.getParameter("FinOtro");
		String regresar = request.getParameter("Regresar") == null ? "N" : request.getParameter("Regresar");
		String practicas = request.getParameter("Practicas") == null ? "N" : request.getParameter("Practicas");
		String interno = request.getParameter("Interno") == null ? "0" : request.getParameter("Interno");
		String externo = request.getParameter("Externo") == null ? "0" : request.getParameter("Externo");
		String obsSaldo = request.getParameter("ObsSaldo") == null ? "N" : request.getParameter("ObsSaldo");
		String obsFin = request.getParameter("ObsFin") == null ? "0" : request.getParameter("ObsFin");
		String obsMat = request.getParameter("ObsMat") == null ? "N" : request.getParameter("ObsMat");
		String obsDoc = request.getParameter("ObsDoc") == null ? "N" : request.getParameter("ObsDoc");
		String obsSalud = request.getParameter("ObsSalud") == null ? "N" : request.getParameter("ObsSalud");
		String obsAdaptacion = request.getParameter("ObsAdaptacion") == null ? "N"
				: request.getParameter("ObsAdaptacion");
		String obsFamiliar = request.getParameter("ObsFamiliar") == null ? "N" : request.getParameter("ObsFamiliar");
		String planEstudiar = request.getParameter("PlanEstudiar") == null ? "N" : request.getParameter("PlanEstudiar");
		String planOtroEst = request.getParameter("PlanOtroEst") == null ? "" : request.getParameter("PlanOtroEst");
		String planTrabajo = request.getParameter("PlanTrabajo") == null ? "N" : request.getParameter("PlanTrabajo");
		String planColportar = request.getParameter("PlanColportar") == null ? "N"
				: request.getParameter("PlanColportar");
		String planOtroTrabajo = request.getParameter("PlanOtroTrabajo") == null ? "N"
				: request.getParameter("PlanOtroTrabajo");
		String planDescansar = request.getParameter("PlanDescansar") == null ? "N"
				: request.getParameter("PlanDescansar");
		String planNinguno = request.getParameter("PlanNinguno") == null ? "N" : request.getParameter("PlanNinguno");
		String orientacion = request.getParameter("Orientacion") == null ? "N" : request.getParameter("Orientacion");
		String planId = planId = alumPlanDao.getPlanActual(codigoAlumno);

		EncPeriodoRes encPeriodoRes = new EncPeriodoRes();

		encPeriodoRes.setCodigoPersonal(codigoAlumno);
		encPeriodoRes.setPeriodoId(periodoId);
		encPeriodoRes.setFinAlumno(finAlumno);
		encPeriodoRes.setFinPor(finPor);
		encPeriodoRes.setFinColpor(finColpor);
		encPeriodoRes.setFinTrabajo(finTrabajo);
		encPeriodoRes.setFinOtro(finOtro);
		encPeriodoRes.setRegresar(regresar);
		encPeriodoRes.setPracticas(practicas);
		encPeriodoRes.setInterno(interno);
		encPeriodoRes.setExterno(externo);
		encPeriodoRes.setObsSaldo(obsSaldo);
		encPeriodoRes.setObsFin(obsFin);
		encPeriodoRes.setObsMat(obsMat);
		encPeriodoRes.setObsDoc(obsDoc);
		encPeriodoRes.setObsSalud(obsSalud);
		encPeriodoRes.setObsAdaptacion(obsAdaptacion);
		encPeriodoRes.setObsFamiliar(obsFamiliar);
		encPeriodoRes.setPlanEstudiar(planEstudiar);
		encPeriodoRes.setPlanOtroEst(planOtroEst);
		encPeriodoRes.setPlanTrabajo(planTrabajo);
		encPeriodoRes.setPlanColportar(planColportar);
		encPeriodoRes.setPlanOtroTrabajo(planOtroTrabajo);
		encPeriodoRes.setPlanDescansar(planDescansar);
		encPeriodoRes.setPlanNinguno(planNinguno);
		encPeriodoRes.setOrientacion(orientacion);
		encPeriodoRes.setPlanId(planId);

		if (!encPeriodoResDao.existeReg(codigoAlumno, periodoId)) {
			if (encPeriodoResDao.insertReg(encPeriodoRes)) {
				mensajeEnc = "1";
			}
		}

		return "redirect:/portales/alumno/resumen?MensajeEnc=" + mensajeEnc;
	}

	@RequestMapping("/portales/alumno/encuestaMentor")
	public String portalesAlumnoEncuestaMentor(HttpServletRequest request, Model modelo) {
		String encuestaActiva = mentEncuestaDao.getEncuestaActiva();

		List<MentPregunta> preguntasEncuesta = new ArrayList<MentPregunta>();

		if (!encuestaActiva.equals("0")) {
			preguntasEncuesta = mentPreguntaDao.getListAll(" WHERE ENCUESTA_ID = " + encuestaActiva);
		}

		modelo.addAttribute("encuestaActiva", encuestaActiva);
		modelo.addAttribute("preguntasEncuesta", preguntasEncuesta);

		return "portales/alumno/encuestaMentor";
	}

	@RequestMapping("/portales/alumno/grabarEncuestaMent")
	public String portalesAlumnoGrabarEncuestaMent(HttpServletRequest request, Model modelo) {

		String mensajeEnc = "0";
		String codigoAlumno = "0";
		HttpSession sesion = request.getSession();
		if (sesion != null) {
			codigoAlumno = (String) sesion.getAttribute("codigoAlumno");
		}

		String encuestaId = request.getParameter("EncuestaId") == null ? "0" : request.getParameter("EncuestaId");

		List<MentPregunta> preguntasEncuesta = new ArrayList<MentPregunta>();

		if (!encuestaId.equals("0")) {
			preguntasEncuesta = mentPreguntaDao.getListAll(" WHERE ENCUESTA_ID = " + encuestaId);
		}

		for (MentPregunta pregunta : preguntasEncuesta) {
			MentRespuesta respuesta = new MentRespuesta();

			respuesta.setCodigoPersonal(codigoAlumno);
			respuesta.setPreguntaId(pregunta.getPreguntaId());
			respuesta.setEncuestaId(encuestaId);

			String importancia = request.getParameter("Imp" + pregunta.getPreguntaId()) == null ? "0" : request.getParameter("Imp" + pregunta.getPreguntaId());
			String satisfaccion = request.getParameter("Sat" + pregunta.getPreguntaId()) == null ? "0" : request.getParameter("Sat" + pregunta.getPreguntaId());

			if (!importancia.equals("0")) {
				respuesta.setImportancia(importancia);
			}
			if (!satisfaccion.equals("0")) {
				respuesta.setSatisfaccion(satisfaccion);
			}

			if (mentRespuestaDao.insertReg(respuesta)) {
				mensajeEnc = "2";
			}
		}

		return "redirect:/portales/alumno/resumen?MensajeEnc=" + mensajeEnc;
	}
	
	@RequestMapping("/portales/alumno/nseEncuesta")
	public String portalesAlumnoNseEncuesta(HttpServletRequest request, Model modelo){	
		
		String encuestaId			= request.getParameter("EncuestaId")==null?"0":request.getParameter("EncuestaId");
		String mensaje				= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		String codigoPersonal		= "0";
		
		HttpSession sesion 			= request.getSession();
		if (sesion != null) {
			codigoPersonal 			= (String) sesion.getAttribute("codigoAlumno");
		}
		
		NseEncuesta nseEncuesta 	= nseEncuestaDao.mapeaRegId(encuestaId); 
		int preguntasContestadas	= nseRespuestaDao.getNumRespuestas(codigoPersonal, encuestaId);
		List<NsePregunta> lisPreguntas			= nsePreguntaDao.lista(encuestaId, " ORDER BY PREGUNTA");
		List<NseRespuestaPregunta> lisRespuestas	= nseRespuestaPreguntaDao.lista(encuestaId, "ORDER BY PREGUNTA_ID, RESPUESTA");
		HashMap<String,String> mapaRespuestas		= nseRespuestaDao.mapaRespuestas(codigoPersonal, encuestaId);
		
		modelo.addAttribute("encuestaId", encuestaId);
		modelo.addAttribute("nseEncuesta", nseEncuesta);
		modelo.addAttribute("preguntasContestadas", preguntasContestadas);
		modelo.addAttribute("lisPreguntas", lisPreguntas);
		modelo.addAttribute("lisRespuestas", lisRespuestas);
		modelo.addAttribute("mapaRespuestas", mapaRespuestas);		
		modelo.addAttribute("mensaje", mensaje);
		
		return "portales/alumno/nseEncuesta";
	}	
	
	@RequestMapping("/portales/alumno/grabarEncuesta")
	public String portalesAlumnoGrabarEncuesta(HttpServletRequest request){	
		
		String encuestaId			= request.getParameter("EncuestaId")==null?"1":request.getParameter("EncuestaId");
		String mensaje				= "-";
		String codigoPersonal		= "0";
		
		HttpSession sesion 			= request.getSession();
		if (sesion != null) {
			codigoPersonal 			= (String) sesion.getAttribute("codigoAlumno");
		}	
		
		List<NsePregunta> lisPreguntas	= nsePreguntaDao.lista(encuestaId," ORDER BY PREGUNTA");	
		
		for(NsePregunta pregunta : lisPreguntas){
			NseRespuesta respuesta = new NseRespuesta();
			if(request.getParameter("preguntaId"+pregunta.getPreguntaId()) != null) {
				
				String[] respuestaAlumno = request.getParameter("preguntaId"+pregunta.getPreguntaId()).split("-");
				
				String respuestaId  = respuestaAlumno[0];
				String puntos 		= respuestaAlumno[1];
				
				
				respuesta.setEncuestaId(encuestaId);
				respuesta.setRespuesta(respuestaId);
				respuesta.setCodigoPersonal(codigoPersonal);
				respuesta.setPreguntaId(pregunta.getPreguntaId());
				respuesta.setPuntos(puntos);
				if (nseRespuestaDao.existeRegId(encuestaId, pregunta.getPreguntaId(), codigoPersonal)){
					if(nseRespuestaDao.updateReg(respuesta)) {
						mensaje = "1";
					}
				}else {
					if(nseRespuestaDao.insertReg(respuesta)) {
						mensaje = "1";
					}
				}
				
			}
		}			
		return "redirect:/portales/alumno/nseEncuesta?EncuestaId="+encuestaId+"&Mensaje="+mensaje;
	}	
	
	@RequestMapping("/portales/alumno/sonomaPdf")
	public String portalesAlumnoSonomaPdf(HttpServletRequest request, Model modelo){	
		
		String planId				= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");		
		String codigoPersonal		= "0";
		
		HttpSession sesion 			= request.getSession();
		if (sesion != null) {
			codigoPersonal 			= (String) sesion.getAttribute("codigoAlumno");
		}		
		
		AlumPersonal alumPersonal = alumPersonalDao.mapeaRegId(codigoPersonal);
		AlumEstudio alumEstudio = alumEstudioDao.mapeaReg(codigoPersonal);
		String nombreAlum = alumPersonal.getNombreLegal();
		String dob =  alumPersonal.getFNacimiento();
		
		MapaPlan mapaPlan = mapaPlanDao.mapeaRegId(planId);
		String planNombre = mapaPlan.getNombrePlan();
		String institucion = alumEstudio.getInstitucion();
		
		List<MapaCurso> lisCursos 				= mapaCursoDao.getLista(planId, " ORDER BY CICLO, NOMBRE_CURSO");
		List<AlumnoCurso> lisNotas 				= alumnoCursoDao.lisNotasPorAlumnoAndPlan(codigoPersonal, planId, "ORDER BY CICLO, CURSO_ID, ALUMNO_CURSO.F_EVALUACION");
		HashMap<String,String> mapaGradePoint 	= alumnoCursoDao.mapaGradePoint(codigoPersonal, planId);

		String fechaGraduacion = "N/A";
		AlumEgreso alumEgreso = alumEgresoDao.existeRegAlum(codigoPersonal, planId)?alumEgresoDao.mapeaPorCodigoyPlan(codigoPersonal, planId):new AlumEgreso();
		AlumEvento alumEvento = alumEventoDao.mapeaRegId(alumEgreso.getEventoId());
		if(alumEvento.getFecha()!=null && !alumEvento.getFecha().equals("")) fechaGraduacion = alumEvento.getFecha();
		
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("nombreAlum", nombreAlum);
		modelo.addAttribute("institucion", institucion);
		modelo.addAttribute("dob", dob);
		modelo.addAttribute("planNombre", planNombre);
		modelo.addAttribute("lisCursos", lisCursos);
		modelo.addAttribute("lisNotas", lisNotas);
		modelo.addAttribute("mapaGradePoint", mapaGradePoint);
		modelo.addAttribute("fechaGraduacion", fechaGraduacion);
		
		return "portales/alumno/sonomaPdf";
	}
	
	@RequestMapping("/portales/alumno/pauPdf")
	public String portalesAlumnoPauPdf(HttpServletRequest request, Model modelo){
		
		String planId				= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");		
		String codigoPersonal		= "0";
		String fecha				= aca.util.Fecha.getHoy();

		Parametros parametros		= parametrosDao.mapeaRegId("1");

		HttpSession sesion 			= request.getSession();
		if (sesion != null) {
			codigoPersonal 			= (String) sesion.getAttribute("codigoAlumno");
		}		
		
		AlumPersonal alumPersonal	= alumPersonalDao.mapeaRegId(codigoPersonal);
		AlumPlan alumPlan 			= alumPlanDao.mapeaRegId(codigoPersonal);
		AlumAcademico alumAcademico = alumAcademicoDao.mapeaRegId(codigoPersonal);
		String nombreAlum = alumPersonal.getNombre()+" "+(alumPersonal.getApellidoMaterno()==null?"":alumPersonal.getApellidoMaterno().equals("-")?"":alumPersonal.getApellidoMaterno())+" "+alumPersonal.getApellidoPaterno();
		String dob =  alumPersonal.getFNacimiento();
		String entry = alumPlan.getPrimerMatricula().substring(6,10);
		
		MapaPlan mapaPlan = mapaPlanDao.mapeaRegId(planId);
		String planNombre = mapaPlan.getNombrePlan();
		String institucion = parametros.getInstitucion();
		String nivelInicio = catNivelInicioDao.getNombreNivel(alumAcademico.getNivelInicioId());
		
		String fechaGraduacion = "N/A";
		AlumEgreso alumEgreso = alumEgresoDao.existeRegAlum(codigoPersonal, planId)?alumEgresoDao.mapeaPorCodigoyPlan(codigoPersonal, planId):new AlumEgreso();
		AlumEvento alumEvento = alumEventoDao.mapeaRegId(alumEgreso.getEventoId());
		if(alumEvento.getFecha()!=null && !alumEvento.getFecha().equals("")) fechaGraduacion = alumEvento.getFecha();
		
		List<MapaCurso> lisCursos 							= mapaCursoDao.getLista(planId, " ORDER BY CICLO, NOMBRE_CURSO");
		List<AlumnoCurso> lisNotas 							= alumnoCursoDao.lisNotasPorAlumnoAndPlan(codigoPersonal, planId, "ORDER BY CICLO, CURSO_ID, ALUMNO_CURSO.F_EVALUACION");
		HashMap<String,String> mapaGradePoint 				= alumnoCursoDao.mapaGradePoint(codigoPersonal, planId);
		HashMap<String,AlumnoCurso> mapaCursos 				= alumnoCursoDao.mapaCursosAlumno(codigoPersonal, planId);
		HashMap<String, CatTipoCal> mapaTipoCal				= catTipoCalDao.getMapAll("");
		HashMap<String,MapaMayorMenor> mapaMayores 			= mapaMayorMenorDao.mapMayoresPorPlan(planId, " ORDER BY NOMBRE");
		HashMap<String,MapaMayorMenor> mapaMenores 			= mapaMayorMenorDao.mapMenoresPorPlan(planId," ORDER BY NOMBRE");
		HashMap<String,String> mapaEscuelaPlan 				= mapaPlanDao.mapaFacultadDelPlan();

		String mayor = "n/a";
		String menor = "n/a";
		if(mapaMayores.containsKey(alumPlan.getMayor())) mayor = mapaMayores.get(alumPlan.getMayor()).getNombre();
		if(mapaMenores.containsKey(alumPlan.getMenor())) menor = mapaMenores.get(alumPlan.getMenor()).getNombre();
		
		modelo.addAttribute("fecha", fecha);
		modelo.addAttribute("nombreAlum", nombreAlum);
		modelo.addAttribute("institucion", institucion);
		modelo.addAttribute("dob", dob);
		modelo.addAttribute("entry", entry);
		modelo.addAttribute("planNombre", planNombre);
		modelo.addAttribute("lisCursos", lisCursos);
		modelo.addAttribute("lisNotas", lisNotas);
		modelo.addAttribute("mapaGradePoint", mapaGradePoint);
		modelo.addAttribute("mapaCursos", mapaCursos);
		modelo.addAttribute("mapaTipoCal", mapaTipoCal);
		modelo.addAttribute("mapaEscuelaPlan", mapaEscuelaPlan);
		modelo.addAttribute("mayor", mayor);
		modelo.addAttribute("menor", menor);
		modelo.addAttribute("nivelInicio", nivelInicio);
		modelo.addAttribute("fechaGraduacion", fechaGraduacion);
		
		return "portales/alumno/pauPdf";
	}
	
	@RequestMapping("/portales/alumno/fultonPdf")
	public String portalesAlumnoFultonPdf(HttpServletRequest request, Model modelo){	
		
		String planId			= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");		
		String codigoAlumno			= "-";
		
		HttpSession sesion  			= request.getSession();
		if (sesion != null) {
			codigoAlumno 		= (String) sesion.getAttribute("codigoAlumno");			
		}	
		AlumPersonal alumPersonal = alumPersonalDao.mapeaRegId(codigoAlumno);
		String planNombre = mapaPlanDao.getNombrePlan(planId);
		
		//MAPEA EL PLAN
		AlumPlan alumPlan = alumPlanDao.mapeaRegId(codigoAlumno, planId);
		
		AlumEstudio alumEstudio = new AlumEstudio();
		
		List<AlumEstudio> lisEstudio = alumEstudioDao.lisAlumEstudio(codigoAlumno);
		if(lisEstudio.size() >= 1) {
			alumEstudio = lisEstudio.get(0);
		}
		
		//LISTA DE CURSOS EN EL PLAN
		List<MapaCurso> lisCursosCarrera = mapaCursoDao.getListPlan(planId, " ORDER BY CICLO, CURSO_ID");
		//MAPA DE CURSOS DEL ALUMNO
		HashMap<String,AlumnoCurso> mapaCursosAlumno = alumnoCursoDao.mapaCursosAlumno(codigoAlumno, planId);
		//MAPA DE CREDITOS DEL ALUMNO
		HashMap<String,String> mapaGradePoint 	= alumnoCursoDao.mapaGradePoint(codigoAlumno, planId);

		String fechaGraduacion = "N/A";
		AlumEgreso alumEgreso = alumEgresoDao.existeRegAlum(codigoAlumno, planId)?alumEgresoDao.mapeaPorCodigoyPlan(codigoAlumno, planId):new AlumEgreso();
		AlumEvento alumEvento = alumEventoDao.mapeaRegId(alumEgreso.getEventoId());
		if(alumEvento.getFecha()!=null && !alumEvento.getFecha().equals("")) fechaGraduacion = alumEvento.getFecha();
		
		modelo.addAttribute("planNombre",planNombre);
		modelo.addAttribute("alumPersonal",alumPersonal);
		modelo.addAttribute("alumPlan",alumPlan);
		modelo.addAttribute("alumEstudio", alumEstudio);
		
		modelo.addAttribute("lisCursosCarrera",lisCursosCarrera);
		modelo.addAttribute("mapaCursosAlumno", mapaCursosAlumno);
		modelo.addAttribute("mapaGradePoint", mapaGradePoint);
		modelo.addAttribute("fechaGraduacion", fechaGraduacion);
		
		return "portales/alumno/fultonPdf";
	}

	@RequestMapping("/portales/alumno/workline")
	public String portalesAlumnoWorkline(HttpServletRequest request, Model modelo){	
		String planId = request.getParameter("PlanId") == null ? "0" : request.getParameter("PlanId").trim();

		String codigoAlumno 	= "0";
		String codigoPersonal 	= "0";
		String nombreAlumno 	= "-";
		String cargaId 			= "-";

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			codigoAlumno 		= (String) sesion.getAttribute("codigoAlumno");
			codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");
			nombreAlumno 		= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
			if (planId.equals("0")) {
				planId 			= alumPlanDao.getPlanActual(codigoAlumno);
			}
			cargaId = (String) sesion.getAttribute("cargaId");
		}

		AlumPersonal alumPersonal = alumPersonalDao.mapeaRegId(codigoAlumno);
		String planNombre = mapaPlanDao.getNombrePlan(planId);

		TrabAlum trabAlum = trabAlumDao.mapeaRegId(codigoAlumno, "A");

		Carga carga = cargaDao.mapeaRegId(cargaId);

		List<TrabInformeAlum> lisTrabInformeAlum = trabInformeAlumDao.lisInformesAlumno(codigoAlumno, " ORDER BY FECHA");

		TrabPeriodo trabPeriodo = trabPeriodoDao.mapeaRegId(trabAlum.getPeriodoId());

		HashMap<String,String> mapaHorasPorSemana = trabInformeAlumDao.mapHorasAlumPorSemana(trabAlum.getPeriodoId(), codigoAlumno, " ORDER BY MATRICULA, LLAVE");
		HashMap<String,String> mapaHorasTotPorSemana = trabInformeAlumDao.mapHorasTotAlumPorSemana(trabAlum.getPeriodoId(), codigoAlumno, " ORDER BY MATRICULA, LLAVE");

		HashMap<String, String> mapaNombreDepartamentos = trabDepartamentoDao.mapaDeptNombre("ORDER BY DEPT_ID");
		HashMap<String, String> mapaNombreCategorias 	= trabCategoriaDao.mapaCategoriaNombre();
		HashMap<String, String> mapaNombrePeriodos 		= trabPeriodoDao.mapaPeriodoNombre();
		HashMap<String, String> mapaPeriodoHoras 		= trabInformeAlumDao.mapPeriodoyHoras(codigoAlumno);
		HashMap<String, String> mapaPeriodoHorasTotales = trabInformeAlumDao.mapPeriodoyHorasTotales(codigoAlumno);

		// for(TrabInformeAlum trabInf : lisTrabInformeAlum){
		// 	System.out.println(trabInf.getFecha());  
		// }	

		modelo.addAttribute("SubMenu", "14");

		modelo.addAttribute("alumPersonal",alumPersonal);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("carga", carga);
		modelo.addAttribute("trabAlum", trabAlum);
		modelo.addAttribute("trabPeriodo", trabPeriodo);
		modelo.addAttribute("lisTrabInformeAlum", lisTrabInformeAlum);
		modelo.addAttribute("mapaNombreDepartamentos", mapaNombreDepartamentos);
		modelo.addAttribute("mapaNombreCategorias", mapaNombreCategorias);
		modelo.addAttribute("mapaNombrePeriodos", mapaNombrePeriodos);
		modelo.addAttribute("mapaPeriodoHoras", mapaPeriodoHoras);
		modelo.addAttribute("mapaPeriodoHorasTotales", mapaPeriodoHorasTotales);
		modelo.addAttribute("mapaHorasPorSemana", mapaHorasPorSemana);
		modelo.addAttribute("mapaHorasTotPorSemana", mapaHorasTotPorSemana);
		
		return "portales/alumno/workline";
	}

	@RequestMapping("/portales/alumno/registrarHoras")
	public String portalesAlumnoWorklineRegistrarHoras(HttpServletRequest request, Model modelo){
		String codigoAlumno 	= "0";
		String codigoPersonal 	= "0";
		String nombreAlumno 	= "-";
		String informeId 		= request.getParameter("InformeId")==null?"0":request.getParameter("InformeId");
		String fecha 			= aca.util.Fecha.getHoy();
		String horaInicio 		= aca.util.Fecha.getFechayHora();

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			codigoAlumno 		= (String) sesion.getAttribute("codigoAlumno");
			codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");
			nombreAlumno 		= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
		}

		TrabAlum trabAlum = trabAlumDao.mapeaRegId(codigoAlumno, "A");
		TrabInformeAlum trabInformeAlum = new TrabInformeAlum();
		if(trabInformeAlumDao.existeReg(codigoAlumno, informeId)){
			trabInformeAlum = trabInformeAlumDao.mapeaRegId(codigoAlumno, informeId);
		}else{
			trabInformeAlum.setInformeId(trabInformeAlumDao.maximoReg());
			trabInformeAlum.setMatricula(codigoAlumno);
			trabInformeAlum.setDeptId(trabAlum.getDeptId());
			trabInformeAlum.setCatId(trabAlum.getCatId());
			trabInformeAlum.setPeriodoId(trabAlum.getPeriodoId());
			trabInformeAlum.setFecha(fecha);
			trabInformeAlum.setHoraInicio(horaInicio);
		}

		HashMap<String, String> mapaNombreDepartamentos = trabDepartamentoDao.mapaDeptNombre("ORDER BY DEPT_ID");
		HashMap<String, String> mapaNombreCategorias = trabCategoriaDao.mapaCategoriaNombre();
		HashMap<String, String> mapaNombrePeriodos = trabPeriodoDao.mapaPeriodoNombre();
		
		modelo.addAttribute("SubMenu", "14");
		modelo.addAttribute("trabAlum", trabAlum);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("fecha", fecha);
		modelo.addAttribute("trabInformeAlum", trabInformeAlum);
		modelo.addAttribute("mapaNombreDepartamentos", mapaNombreDepartamentos);
		modelo.addAttribute("mapaNombreCategorias", mapaNombreCategorias);
		modelo.addAttribute("mapaNombrePeriodos", mapaNombrePeriodos);
		
		return "portales/alumno/registrarHoras";
	}

	@RequestMapping("/portales/alumno/grabarHoras")
	public String portalesAlumnoWorklineGrabarHoras(HttpServletRequest request, Model modelo){
		String codigoAlumno 	= "0";
		String codigoPersonal 	= "0";
		String nombreAlumno 	= "-";
		String informeId 		= request.getParameter("InformeId")==null?"0":request.getParameter("InformeId");
		String deptId 			= request.getParameter("DeptId")==null?"0":request.getParameter("DeptId");
		String catId 			= request.getParameter("CatId")==null?"0":request.getParameter("CatId");
		String periodoId 		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String fecha 			= request.getParameter("Fecha")==null?aca.util.Fecha.getHoy():request.getParameter("Fecha");
		String horaInicio 		= request.getParameter("HoraInicio")==null?aca.util.Fecha.getFechayHora():request.getParameter("HoraInicio");
		String descripcion 		= request.getParameter("Descripcion")==null?"":request.getParameter("Descripcion");
		String mensaje 			= "";



		HttpSession sesion = request.getSession();
		if (sesion != null) {
			codigoAlumno 		= (String) sesion.getAttribute("codigoAlumno");
			codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");
			nombreAlumno 		= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
		}

		// System.out.println(deptId + " : " + catId + " : "+periodoId);

		if(trabAlumDao.existeActivo(codigoAlumno)){
			TrabAlum trabAlum = trabAlumDao.mapeaRegId(codigoAlumno, "A");
			TrabInformeAlum trabInformeAlum = new TrabInformeAlum();

			List<TrabAsesor> lisAsesores = trabAsesorDao.lisPorDepartamento(deptId, " ORDER BY CODIGO_PERSONAL");
			trabInformeAlum.setInformeId(trabInformeAlumDao.maximoReg());
			trabInformeAlum.setMatricula(codigoAlumno);
			trabInformeAlum.setDeptId(deptId);
			trabInformeAlum.setCatId(catId);
			trabInformeAlum.setPeriodoId(periodoId);
			trabInformeAlum.setFecha(fecha);
			trabInformeAlum.setStatus("S");
			trabInformeAlum.setHoraInicio(horaInicio);
			trabInformeAlum.setDescripcion(descripcion);
			if(trabInformeAlumDao.insertReg(trabInformeAlum)){
				mensaje = "Report started";
				for(TrabAsesor asesor : lisAsesores){
					String notificationMessage = "Student " + nombreAlumno + " ( " + codigoAlumno + " ) has CREATED a new report. Dept: "+deptId+" Cat: "+catId;
					notificationService.sendNotificationToAdmin(notificationMessage, accesoDao.getUsuario(asesor.getCodigoPersonal()));	
				}
			}else{
				mensaje = "Error sending report";
			}	
		}else{
			mensaje = "No active workline";
		}

		return "redirect:/portales/alumno/registrarHoras?InformeId="+informeId+"&Mensaje="+mensaje;
	}

	@RequestMapping("/portales/alumno/concluir")
	public String portalesAlumnoWorklineTerminarReporte(HttpServletRequest request, Model modelo){
		String codigoAlumno 	= "0";
		String codigoPersonal 	= "0";
		String nombreAlumno 	= "-";
		String informeId 		= request.getParameter("InformeId")==null?"0":request.getParameter("InformeId");
		String descripcion 		= request.getParameter("Descripcion")==null?"":request.getParameter("Descripcion");
		String horaFin 			= aca.util.Fecha.getFechayHora();
		String mensaje 			= "";

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			codigoAlumno 		= (String) sesion.getAttribute("codigoAlumno");
			codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");
			nombreAlumno 		= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
		}

		// System.out.println(descripcion);

		
		TrabInformeAlum trabInformeAlum = new TrabInformeAlum();
		if(trabInformeAlumDao.existeReg(codigoAlumno, informeId)){
			TrabAlum trabAlum = trabAlumDao.mapeaRegId(codigoAlumno, "A");
			if(trabInformeAlumDao.updateHoraFin(codigoAlumno, informeId, horaFin, descripcion)){
				mensaje = "Stoped";

				String notificationMessage = "Student " + nombreAlumno + " ( " + codigoAlumno + " ) has CLOSED report "+informeId+". Dept: "+trabAlum.getDeptId()+" Cat:"+trabAlum.getCatId();
				notificationService.sendNotificationToAdmin(notificationMessage, accesoDao.getUsuario(trabInformeAlumDao.getUsuario(informeId)));	

			}else{
				mensaje = "Error stoping hours";
			}	
		}else{
			mensaje = "No report found";
		}

		return "redirect:/portales/alumno/workline?Mensaje="+mensaje;
	}


	@RequestMapping("/portales/alumno/eliminarHoras")
	public String portalesAlumnoWorklineEliminarHoras(HttpServletRequest request, Model modelo){
		String codigoAlumno 	= "0";
		String codigoPersonal 	= "0";
		String nombreAlumno 	= "-";
		String informeId 		= request.getParameter("InformeId")==null?"0":request.getParameter("InformeId");
		String mensaje 			= "";

		HttpSession sesion = request.getSession();
		if (sesion != null) {
			codigoAlumno 		= (String) sesion.getAttribute("codigoAlumno");
			codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");
			nombreAlumno 		= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
		}

		if(trabAlumDao.existeActivo(codigoAlumno)){
			TrabAlum trabAlum = trabAlumDao.mapeaRegId(codigoAlumno, "A");
			TrabInformeAlum trabInformeAlum = new TrabInformeAlum();

			if(trabInformeAlumDao.existeReg(codigoAlumno, informeId)){
				if(trabInformeAlumDao.deleteReg(codigoAlumno, informeId)){
					mensaje = "Report updated succesfully";
				}else{
					mensaje = "Error updating report";
				}
			}else{
				mensaje = "Erro, no report found";
			}
		}else{
			mensaje = "No active workline";
		}

		return "redirect:/portales/alumno/workline?Mensaje="+mensaje;
	}

	@RequestMapping("/portales/alumno/credencial")
	public String portalesAlumnoCredencial(HttpServletRequest request, Model modelo){
		String matricula = "0";
		String modalidadAlumno = "0";
		String esEnLinea = "N";
		String cargaId = "";
		
		HttpSession sesion = request.getSession();
		if (sesion != null) {
			matricula = (String) sesion.getAttribute("codigoAlumno");
			modalidadAlumno 		= alumAcademicoDao.getModalidadId(matricula);		
			esEnLinea				= catModalidadDao.getEnLinea(modalidadAlumno);
			cargaId = 	(String) sesion.getAttribute("cargaId");
		}

		AlumPersonal alumPersonal 	= alumPersonalDao.mapeaRegId(matricula);	
		String planId				= alumPlanDao.getPlanActual(matricula);
		String planOficial			= mapaPlanDao.getOficial(planId);

		MatAlumno matAlumno 		= new MatAlumno();
		MatEvento matEvento 		= new MatEvento();
		
		if(matEventoDao.existeRegCarga(cargaId)){
			// System.out.println(cargaId);
			matEvento = matEventoDao.mapeaRegIdCarga(cargaId);
		}

		// System.out.println(matEvento.getEventoId()+" : "+matricula+" : "+planId);
		if(matAlumnoDao.existeReg(matEvento.getEventoId(), matricula, planId)){
			matAlumno = matAlumnoDao.mapeaRegId(matEvento.getEventoId(), matricula, planId);
		}

		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("matAlumno", matAlumno);
		modelo.addAttribute("matEvento", matEvento);
		 
		return "portales/alumno/credencial";
	}

	@RequestMapping("/portales/alumno/notificacion")
	public String portalesAlumnoNotificacion(HttpServletRequest request, Model modelo){
		String notificationMessage = "Test notification";
		notificationService.sendNotificationToAdmin(notificationMessage, "admin");	

		return "redirect:/portales/alumno/resumen";
	}
	
}
