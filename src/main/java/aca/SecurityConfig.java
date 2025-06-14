package aca;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired	
	@Qualifier("dsEnoc")
	private DataSource dataSource;
	
	@Value("${spring.queries.users-query}")
	private String usersQuery;
	
	@Value("${spring.queries.roles-query}")	
	private String rolesQuery;	
	
	@Bean
	public UserDetailsManager authenticateUsers() {
		
	  JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
	  users.setUsersByUsernameQuery(usersQuery);
	  users.setAuthoritiesByUsernameQuery(rolesQuery);
	  
	  return users;
	}	
	
	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception{	
		http.csrf((csrf) -> csrf.disable());
		http.cors((cors) -> cors.disable());
		http.authorizeHttpRequests(authorize -> authorize
			.requestMatchers("/*").permitAll()
			.requestMatchers("/login").permitAll()		
			.requestMatchers("/sesion").permitAll()
			.requestMatchers("/loginActivos*").permitAll()
			.requestMatchers("/recuperarPassword").permitAll()
			.requestMatchers("/nuevoPassword").permitAll()
			.requestMatchers("/grabarNuevoPassword*").permitAll()			
			.requestMatchers("/enviaLink*").permitAll()						
			.requestMatchers("/validaSecurity").permitAll()
			.requestMatchers("/validaDocumento*").permitAll()
			.requestMatchers("/cerrarPortal/**").permitAll()
			.requestMatchers("/sinAcceso/**").permitAll()
			.requestMatchers("/monitor/**").permitAll()
			.requestMatchers("/menuDelay/**").permitAll()
			.requestMatchers("/print.css").permitAll()			
			.requestMatchers("/loginStyle.css").permitAll()
			.requestMatchers("/fondoRecuperar.jpeg").permitAll()
			.requestMatchers("/diplomaPdf.jsp").permitAll()
			.requestMatchers("/foto").permitAll()			
			.requestMatchers("/css/**").permitAll()
			.requestMatchers("/js/**").permitAll()
			.requestMatchers("/*.jsp").permitAll()
			.requestMatchers("/*.ico").permitAll()
			.requestMatchers("/fontawesome5/**").permitAll()
			.requestMatchers("/fontawesome6/**").permitAll()
			.requestMatchers("/imagenes/**").permitAll()
			.requestMatchers("/bootstrap/**").permitAll()
			.requestMatchers("/bootstrap3/**").permitAll()
			.requestMatchers("/bootstrap4.4/**").permitAll()
			.requestMatchers("/bootstrap5.1/**").permitAll()
			.requestMatchers("/inicio*").authenticated()
			.requestMatchers("/empleado").authenticated()
			.requestMatchers("/cambiarPassword").authenticated()
			.requestMatchers("/esMovil").authenticated()
			.requestMatchers("/fotoMenu").authenticated()			
			.requestMatchers("/fotoArchivo").authenticated()
			.requestMatchers("/aca").authenticated()
			.requestMatchers("/paginaError").authenticated()
			.requestMatchers("/errores").authenticated()			
			.requestMatchers("/salir").authenticated()
			.requestMatchers("/notificacionList").authenticated()				
			.requestMatchers("/portales/portalAlumno/**").authenticated()
			.requestMatchers("/ws/**").authenticated()
			.requestMatchers("/catalogos/instituciones/**").hasAnyAuthority("A01","001")
			.requestMatchers("/catalogos/modalidad/**").hasAnyAuthority("A01","002")
			.requestMatchers("/catalogos/edificios/**").hasAnyAuthority("A01","003")
			.requestMatchers("/catalogos/pais/**").hasAnyAuthority("A01","004")
			.requestMatchers("/catalogos/division/**").hasAnyAuthority("A01","005")
			.requestMatchers("/catalogos/religion/**").hasAnyAuthority("A01","006")
			.requestMatchers("/catalogos/area/**").hasAnyAuthority("A01","007")
			.requestMatchers("/catalogos/escuela/**").hasAnyAuthority("A01","008")
			.requestMatchers("/catalogos/extension/**").hasAnyAuthority("A01","009")
			.requestMatchers("/catalogos/estrategia/**").hasAnyAuthority("A01","010")
			.requestMatchers("/catalogos/tipocal/**").hasAnyAuthority("A01","011")
			.requestMatchers("/catalogos/tipoalumno/**").hasAnyAuthority("A01","012")
			.requestMatchers("/catalogos/nivel/**").hasAnyAuthority("A01","013")
			.requestMatchers("/catalogos/razon/**").hasAnyAuthority("A01","015")
			.requestMatchers("/catalogos/gpa/**").hasAnyAuthority("A01","017")
			.requestMatchers("/catalogos/periodo/**").hasAnyAuthority("A01","018")
			.requestMatchers("/mapa/plan/**").hasAnyAuthority("A02","030")
			.requestMatchers("/mapa/materia/**").hasAnyAuthority("A02","031")
			.requestMatchers("/mapa/ciclo/**").hasAnyAuthority("A02","032")
			.requestMatchers("/mapa/consulta_mat/**").hasAnyAuthority("A02","033")
			.requestMatchers("/mapa/nuevos/**").hasAnyAuthority("A02","034")
			.requestMatchers("/mapa/inscritos/**").hasAnyAuthority("A02","035")
			.requestMatchers("/mapa/creditos/**").hasAnyAuthority("A02","036")
			.requestMatchers("/mapa/analisis/**").hasAnyAuthority("A02","037")
			.requestMatchers("/mapa/diamante/**").hasAnyAuthority("A02","038")
			.requestMatchers("/carga_grupo/carga/**").hasAnyAuthority("A03","050")
			.requestMatchers("/carga_grupo/bloque/**").hasAnyAuthority("A03","051")
			.requestMatchers("/carga_grupo/permiso/**").hasAnyAuthority("A03","052")
			.requestMatchers("/carga_grupo/grupo/**").hasAnyAuthority("A03","053")
			.requestMatchers("/carga_grupo/unidas/**").hasAnyAuthority("A03","054")
			.requestMatchers("/carga_grupo/unid/**").hasAnyAuthority("A03","055")
			.requestMatchers("/carga_grupo/materias/**").hasAnyAuthority("A03","056")
			.requestMatchers("/carga_grupo/lista_carga/**").hasAnyAuthority("A03","057")
			.requestMatchers("/carga_grupo/lista_bloque/**").hasAnyAuthority("A03","058")
			.requestMatchers("/carga_grupo/enlinea/**").hasAnyAuthority("A03","059")
			.requestMatchers("/carga_grupo/horario/**").hasAnyAuthority("A03","065")
			.requestMatchers("/carga_grupo/sinalumnos/**").hasAnyAuthority("A03","342")
			.requestMatchers("/admision/datos/**").hasAnyAuthority("A04","070")
			.requestMatchers("/admision/plan/**").hasAnyAuthority("A04","071")
			.requestMatchers("/admision/estado/**").hasAnyAuthority("A04","072")
			.requestMatchers("/admision/registro/**").hasAnyAuthority("A04","073")
			.requestMatchers("/admision/ingreso/**").hasAnyAuthority("A04","074")
			.requestMatchers("/admision/ingreso_carga/**").hasAnyAuthority("A04","075")
			.requestMatchers("/admision/reportes/**").hasAnyAuthority("A04","077")
			.requestMatchers("/admision/cimum/**").hasAnyAuthority("A04","079")
			.requestMatchers("/admision/fotos/**").hasAnyAuthority("A04","080")
			.requestMatchers("/admision/vue/**").hasAnyAuthority("A04","082")
			.requestMatchers("/datos_alumno/cumple/**").hasAnyAuthority("A05","081")
			.requestMatchers("/datos_alumno/cursos/**").hasAnyAuthority("A05","090")
			.requestMatchers("/datos_alumno/personal/**").hasAnyAuthority("A05","092")
			.requestMatchers("/datos_alumno/titulo/**").hasAnyAuthority("A05","099")
			.requestMatchers("/datos_alumno/../**").hasAnyAuthority("A05","100")
			.requestMatchers("/datos_alumno/fotocredencial/**").hasAnyAuthority("A05","102")
			.requestMatchers("/datos_alumno/solgraduacion/**").hasAnyAuthority("A05","106")
			.requestMatchers("/datos_alumno/fotosEnlinea/**").hasAnyAuthority("A05","108")
			.requestMatchers("/datos_alumno/cedula/**").hasAnyAuthority("A05","109")
			.requestMatchers("/datos_alumno/asesor/**").hasAnyAuthority("A05","112")
			.requestMatchers("/datos_profesor/cursos/**").hasAnyAuthority("A06","060")
			.requestMatchers("/datos_profesor/evalua/**").hasAnyAuthority("A06","061")
			.requestMatchers("/datos_profesor/curriculum/**").hasAnyAuthority("A06","062")
			.requestMatchers("/datos_profesor/alta/**").hasAnyAuthority("A06","063")
			.requestMatchers("/datos_profesor/listado/**").hasAnyAuthority("A06","064")
			.requestMatchers("/datos_profesor/acta/**").hasAnyAuthority("A06","066")
			.requestMatchers("/datos_profesor/portafolio/**").hasAnyAuthority("A06","067")
			.requestMatchers("/datos_profesor/plancurso/**").hasAnyAuthority("A06","110")
			.requestMatchers("/datos_profesor/vitae/**").hasAnyAuthority("A06","111")
			.requestMatchers("/datos_profesor/empleado/**").hasAnyAuthority("A06","113")
			.requestMatchers("/datos_profesor/ingreso/**").hasAnyAuthority("A06","114")
			.requestMatchers("/matricula/autoriza/**").hasAnyAuthority("A07","130")
			.requestMatchers("/matricula/candidatos/**").hasAnyAuthority("A07","131")
			.requestMatchers("/matricula/plan/**").hasAnyAuthority("A07","132")
			.requestMatchers("/matricula/altabaja/**").hasAnyAuthority("A07","133")
			.requestMatchers("/matricula/cambioCarrera/**").hasAnyAuthority("A07","134")
			.requestMatchers("/matricula/solicitud/**").hasAnyAuthority("A07","137")
			.requestMatchers("/matricula/calculo/**").hasAnyAuthority("A07","138")
			.requestMatchers("/matricula/carga/**").hasAnyAuthority("A07","139")
			.requestMatchers("/matricula/planes/**").hasAnyAuthority("A07","140")
			.requestMatchers("/matricula/cierre/**").hasAnyAuthority("A07","141")
			.requestMatchers("/matricula/contacto/**").hasAnyAuthority("A07","142")
			.requestMatchers("/archivo/reportes/**").hasAnyAuthority("A08","150")
			.requestMatchers("/archivo/cotejar_alumno/**").hasAnyAuthority("A08","152")
			.requestMatchers("/archivo/restaurar/**").hasAnyAuthority("A08","153")
			.requestMatchers("/archivo/grupo/**").hasAnyAuthority("A08","154")
			.requestMatchers("/archivo/consulta/**").hasAnyAuthority("A08","155")
			.requestMatchers("/archivo/documentos_alumno/**").hasAnyAuthority("A08","156")
			.requestMatchers("/archivo/documento_estado/**").hasAnyAuthority("A08","157")
			.requestMatchers("/archivo/estados/**").hasAnyAuthority("A08","158")
			.requestMatchers("/archivo/documentos/**").hasAnyAuthority("A08","159")
			.requestMatchers("/archivo/ubicacion/**").hasAnyAuthority("A08","160")
			.requestMatchers("/archivo/permisos/**").hasAnyAuthority("A08","161")
			.requestMatchers("/archivo/plan/**").hasAnyAuthority("A08","162")
			.requestMatchers("/archivo/respaldo/**").hasAnyAuthority("A08","163")
			.requestMatchers("/archivo/subir_documentos/**").hasAnyAuthority("A08","166")
			.requestMatchers("/archivo/traspaso/**").hasAnyAuthority("A08","168")
			.requestMatchers("/archivo/entregar/**").hasAnyAuthority("A08","169")
			.requestMatchers("/disciplina/unidades/**").hasAnyAuthority("A09","170")
			.requestMatchers("/disciplina/cat_juez/**").hasAnyAuthority("A09","171")
			.requestMatchers("/disciplina/cat_lugares/**").hasAnyAuthority("A09","172")
			.requestMatchers("/disciplina/cat_reportes/**").hasAnyAuthority("A09","173")
			.requestMatchers("/disciplina/total_unidad/**").hasAnyAuthority("A09","176")
			.requestMatchers("/disciplina/elogios_rep/**").hasAnyAuthority("A09","179")
			.requestMatchers("/disciplina/carga/**").hasAnyAuthority("A09","182")
			.requestMatchers("/disciplina/reportes/**").hasAnyAuthority("A09","183")
			.requestMatchers("/extranjeros/documentos/**").hasAnyAuthority("A10","190")
			.requestMatchers("/extranjeros/extranjero/**").hasAnyAuthority("A10","192")
			.requestMatchers("/extranjeros/reportes/**").hasAnyAuthority("A10","193")
			.requestMatchers("/extranjeros/permisos/**").hasAnyAuthority("A10","194")
			.requestMatchers("/extranjeros/docextranjero/**").hasAnyAuthority("A10","195")
			.requestMatchers("/extranjeros/condiciones/**").hasAnyAuthority("A10","196")
			.requestMatchers("/extranjeros/reportes/**").hasAnyAuthority("A10","199")
			.requestMatchers("/extranjeros/ext_inscritos/**").hasAnyAuthority("A10","200")
			.requestMatchers("/extranjeros/estadistica/**").hasAnyAuthority("A10","201")
			.requestMatchers("/extranjeros/tramite/**").hasAnyAuthority("A10","204")
			.requestMatchers("/extranjeros/requisito/**").hasAnyAuthority("A10","205")
			.requestMatchers("/extranjeros/tramreq/**").hasAnyAuthority("A10","206")
			.requestMatchers("/extranjeros/nacionalidad/**").hasAnyAuthority("A10","207")
			.requestMatchers("/mentores/entrevistas/**").hasAnyAuthority("A11","212")
			.requestMatchers("/mentores/reportes/**").hasAnyAuthority("A11","214")
			.requestMatchers("/mentores/motivos/**").hasAnyAuthority("A11","220")
			.requestMatchers("/mentores/referente/**").hasAnyAuthority("A11","221")
			.requestMatchers("/mentores/ment_alum/**").hasAnyAuthority("A11","222")
			.requestMatchers("/mentores/bitacora/**").hasAnyAuthority("A11","224")
			.requestMatchers("/mentores/aconsejados/**").hasAnyAuthority("A11","226")
			.requestMatchers("/mentores/datos/**").hasAnyAuthority("A11","227")
			.requestMatchers("/mentores/anuario/**").hasAnyAuthority("A11","228")
			.requestMatchers("/mentores/agenda/**").hasAnyAuthority("A11","229")
			.requestMatchers("/residencia/residencia/**").hasAnyAuthority("A12","230")
			.requestMatchers("/residencia/expediente/**").hasAnyAuthority("A12","231")
			.requestMatchers("/residencia/colonia/**").hasAnyAuthority("A12","233")
			.requestMatchers("/residencia/estadistica/**").hasAnyAuthority("A12","237")
			.requestMatchers("/residencia/candado/**").hasAnyAuthority("A12","240")
			.requestMatchers("/residencia/solicitud/**").hasAnyAuthority("A12","241")
			.requestMatchers("/residencia/hospedaje/**").hasAnyAuthority("A12","242")
			.requestMatchers("/residencia/periodo/**").hasAnyAuthority("A12","243")
			.requestMatchers("/residencia/alumno/**").hasAnyAuthority("A12","244")
			.requestMatchers("/usuarios/privilegios/**").hasAnyAuthority("A13","250")
			.requestMatchers("/usuarios/aplicaciones/**").hasAnyAuthority("A13","251")
			.requestMatchers("/usuarios/clave/**").hasAnyAuthority("A13","252")
			.requestMatchers("/usuarios/roles/**").hasAnyAuthority("A13","253")
			.requestMatchers("/usuarios/migrar/**").hasAnyAuthority("A13","254")
			.requestMatchers("/usuarios/unav/**").hasAnyAuthority("A13","255")
			.requestMatchers("/usuarios/fotos/**").hasAnyAuthority("A13","256")
			.requestMatchers("/usuarios/subir/**").hasAnyAuthority("A13","257")
			.requestMatchers("/vota/votaciones/**").hasAnyAuthority("A14","270")
			.requestMatchers("/parametros/alumno/**").hasAnyAuthority("A15","290")
			.requestMatchers("/parametros/carga/**").hasAnyAuthority("A15","291")
			.requestMatchers("/parametros/param/**").hasAnyAuthority("A15","292")
			.requestMatchers("/parametros/metrics/**").hasAnyAuthority("A15","293")
			.requestMatchers("/informes/calificacion/**").hasAnyAuthority("A16","310")
			.requestMatchers("/informes/911/**").hasAnyAuthority("A16","312")
			.requestMatchers("/informes/alta_baja/**").hasAnyAuthority("A16","313")
			.requestMatchers("/informes/procedencia/**").hasAnyAuthority("A16","314")
			.requestMatchers("/informes/sep/**").hasAnyAuthority("A16","315")
			.requestMatchers("/informes/datos_inscritos/**").hasAnyAuthority("A16","316")
			.requestMatchers("/informes/tipoAlumno/**").hasAnyAuthority("A16","317")
			.requestMatchers("/informes/ingreso/**").hasAnyAuthority("A16","318")
			.requestMatchers("/informes/datos/**").hasAnyAuthority("A16","319")
			.requestMatchers("/informes/extra/**").hasAnyAuthority("A16","321")
			.requestMatchers("/informes/baja_total/**").hasAnyAuthority("A16","325")
			.requestMatchers("/informes/tabla/**").hasAnyAuthority("A16","326")		
			.requestMatchers("/informes/vacunados/**").hasAnyAuthority("A16","327")
			.requestMatchers("/convalida/solicitud/**").hasAnyAuthority("A17","550")
			.requestMatchers("/convalida/proceso/**").hasAnyAuthority("A17","551")
			.requestMatchers("/convalida/listado/**").hasAnyAuthority("A17","552")
			.requestMatchers("/convalida/registro/**").hasAnyAuthority("A17","553")
			.requestMatchers("/convalida/sol_interna/**").hasAnyAuthority("A17","554")
			.requestMatchers("/convalida/periodo/**").hasAnyAuthority("A17","555")
			.requestMatchers("/administrar/planillas/**").hasAnyAuthority("A18","331")
			.requestMatchers("/administrar/titulo/**").hasAnyAuthority("A18","332")
			.requestMatchers("/administrar/analisis/**").hasAnyAuthority("A18","333")
			.requestMatchers("/administrar/cancela/**").hasAnyAuthority("A18","334")
			.requestMatchers("/administrar/cohorte/**").hasAnyAuthority("A18","337")
			.requestMatchers("/administrar/estudios/**").hasAnyAuthority("A18","338")
			.requestMatchers("/administrar/diferida/**").hasAnyAuthority("A18","339")
			.requestMatchers("/administrar/se/**").hasAnyAuthority("A18","340")
			.requestMatchers("/administrar/idp/**").hasAnyAuthority("A18","341")
			.requestMatchers("/utilerias/claves/**").hasAnyAuthority("A19","350")			
			.requestMatchers("/utilerias/apellidos/**").hasAnyAuthority("A19","353")
			.requestMatchers("/utilerias/img_doc/**").hasAnyAuthority("A19","354")
			.requestMatchers("/utilerias/img_doc/**").hasAnyAuthority("A19","355")
			.requestMatchers("/utilerias/img_doc/**").hasAnyAuthority("A19","356")
			.requestMatchers("/utilerias/img_doc/**").hasAnyAuthority("A19","357")
			.requestMatchers("/utilerias/hospital/**").hasAnyAuthority("A19","358")
			.requestMatchers("/utilerias/ingreso/**").hasAnyAuthority("A19","359")
			.requestMatchers("/utilerias/grado/**").hasAnyAuthority("A19","360")
			.requestMatchers("/utilerias/egreso/**").hasAnyAuthority("A19","361")
			.requestMatchers("/utilerias/semestre/**").hasAnyAuthority("A19","362")
			.requestMatchers("/utilerias/trayectoria/**").hasAnyAuthority("A19","364")
			.requestMatchers("/utilerias/browser/**").hasAnyAuthority("A19","365")
			.requestMatchers("/utilerias/idioma/**").hasAnyAuthority("A19","366")
			.requestMatchers("/credencial_alum/fotos/**").hasAnyAuthority("A20","370")
			.requestMatchers("/credencial_alum/fotos/**").hasAnyAuthority("A20","371")
			.requestMatchers("/credencial_alum/listas/**").hasAnyAuthority("A20","372")
			.requestMatchers("/credencial_alum/listas/**").hasAnyAuthority("A20","373")
			.requestMatchers("/credencial_alum/listas/**").hasAnyAuthority("A20","374")
			.requestMatchers("/credencial_alum/listas/**").hasAnyAuthority("A20","376")
			.requestMatchers("/credencial_alum/credencial/**").hasAnyAuthority("A20","377")
			.requestMatchers("/credencial_emp/fotos/**").hasAnyAuthority("A21","390")
			.requestMatchers("/credencial_emp/fotos/**").hasAnyAuthority("A21","391")
			.requestMatchers("/credencial_emp/datos_emp/**").hasAnyAuthority("A21","392")
			.requestMatchers("/credencial_emp/cumple/**").hasAnyAuthority("A21","393")
			.requestMatchers("/credencial_emp/consultar/**").hasAnyAuthority("A21","394")
			.requestMatchers("/credencial_emp/hsm/**").hasAnyAuthority("A21","395")
			.requestMatchers("/credencial_emp/visitantes/**").hasAnyAuthority("A21","396")
			.requestMatchers("/credencial_emp/redimensionar/**").hasAnyAuthority("A21","397")
			.requestMatchers("/candados/alumno/**").hasAnyAuthority("A22","410")
			.requestMatchers("/candados/catalogo/**").hasAnyAuthority("A22","412")
			.requestMatchers("/candados/reporte/**").hasAnyAuthority("A22","413")
			.requestMatchers("/candados/listado/**").hasAnyAuthority("A22","415")
			.requestMatchers("/candados/reptipo/**").hasAnyAuthority("A22","416")
			.requestMatchers("/candados/aplicar/**").hasAnyAuthority("A22","417")
			.requestMatchers("/candados/deudas/**").hasAnyAuthority("A22","418")
			.requestMatchers("/bsc/indicadores/**").hasAnyAuthority("A23","430")
			.requestMatchers("/bsc/registronotas/**").hasAnyAuthority("A23","431")
			.requestMatchers("/bsc/aprovechamiento/**").hasAnyAuthority("A23","432")
			.requestMatchers("/bsc/indice/**").hasAnyAuthority("A23","434")
			.requestMatchers("/bsc/eficiencia/**").hasAnyAuthority("A23","435")
			.requestMatchers("/bsc/deserta/**").hasAnyAuthority("A23","436")
			.requestMatchers("/bsc/eficienciaTerminal/**").hasAnyAuthority("A23","437")
			.requestMatchers("/bsc/reportes/**").hasAnyAuthority("A23","438")
			.requestMatchers("/bsc/inscritos/**").hasAnyAuthority("A23","439")
			.requestMatchers("/bsc/saii/**").hasAnyAuthority("A23","440")
			.requestMatchers("/bsc/periodo/**").hasAnyAuthority("A23","441")
			.requestMatchers("/exalumnos/evento/**").hasAnyAuthority("A24","450")
			.requestMatchers("/exalumnos/exalumno/**").hasAnyAuthority("A24","451")
			.requestMatchers("/exalumnos/asistencia/**").hasAnyAuthority("A24","452")
			.requestMatchers("/exalumnos/reportes/**").hasAnyAuthority("A24","453")
			.requestMatchers("/servicio/catalogos/**").hasAnyAuthority("A25","471")
			.requestMatchers("/servicio/servicio/**").hasAnyAuthority("A25","472")
			.requestMatchers("/servicio/reporte/**").hasAnyAuthority("A25","473")
			.requestMatchers("/servicio/sector/**").hasAnyAuthority("A25","474")
			.requestMatchers("/servicio/documentos/**").hasAnyAuthority("A25","475")
			.requestMatchers("/internado/dormitorios/**").hasAnyAuthority("A26","490")
			.requestMatchers("/internado/calificado/**").hasAnyAuthority("A26","491")
			.requestMatchers("/internado/comidas/**").hasAnyAuthority("A26","492")
			.requestMatchers("/titulacion/catalogos/**").hasAnyAuthority("A27","510")
			.requestMatchers("/titulacion/titulacion/**").hasAnyAuthority("A27","511")
			.requestMatchers("/titulacion/tramites/**").hasAnyAuthority("A27","512")
			.requestMatchers("/titulacion/firmar/**").hasAnyAuthority("A27","513")
			.requestMatchers("/titulacion/reportes/**").hasAnyAuthority("A27","514")
			.requestMatchers("/finanzas/permisos/**").hasAnyAuthority("A28","531")
			.requestMatchers("/finanzas/alum_permisos/**").hasAnyAuthority("A28","532")
			.requestMatchers("/finanzas/reporte/**").hasAnyAuthority("A28","533")
			.requestMatchers("/finanzas/mensaje/**").hasAnyAuthority("A28","534")
			.requestMatchers("/finanzas/hermanos/**").hasAnyAuthority("A28","535")
			.requestMatchers("/finanzas/cuentas/**").hasAnyAuthority("A28","536")
			.requestMatchers("/finanzas/descuentos/**").hasAnyAuthority("A28","537")
			.requestMatchers("/finanzas/tipo/**").hasAnyAuthority("A28","538")
			.requestMatchers("/finanzas/repdescuento/**").hasAnyAuthority("A28","539")
			.requestMatchers("/finanzas/periodo/**").hasAnyAuthority("A28","540")
			.requestMatchers("/finanzas/formatos/**").hasAnyAuthority("A28","541")
			.requestMatchers("/finanzas/maratum/**").hasAnyAuthority("A28","542")
			.requestMatchers("/finanzas/alumno/**").hasAnyAuthority("A28","543")
			.requestMatchers("/inscritos/inscritos_periodo/**").hasAnyAuthority("A29","571")
			.requestMatchers("/inscritos/inscritos_fac/**").hasAnyAuthority("A29","572")
			.requestMatchers("/inscritos/mentor/**").hasAnyAuthority("A29","573")
			.requestMatchers("/inscritos/inscritos/**").hasAnyAuthority("A29","574")
			.requestMatchers("/inscritos/estadistica/**").hasAnyAuthority("A29","576")
			.requestMatchers("/inscritos/tabla/**").hasAnyAuthority("A29","577")
			.requestMatchers("/inscritos/alum_insc/**").hasAnyAuthority("A29","578")
			.requestMatchers("/inscritos/primeros/**").hasAnyAuthority("A29","579")
			.requestMatchers("/inscritos/ciclos/**").hasAnyAuthority("A29","580")
			.requestMatchers("/inscritos/inscritos_se/**").hasAnyAuthority("A29","581")
			.requestMatchers("/inscritos/ventas/**").hasAnyAuthority("A29","582")
			.requestMatchers("/inscritos/localiza/**").hasAnyAuthority("A29","583")
			.requestMatchers("/inscritos/menu/**").hasAnyAuthority("A29","584")
			.requestMatchers("/aptitud_fisica/referencia/**").hasAnyAuthority("A30","590")
			.requestMatchers("/aptitud_fisica/alumno/**").hasAnyAuthority("A30","591")
			.requestMatchers("/aptitud_fisica/horario/**").hasAnyAuthority("A30","593")
			.requestMatchers("/aptitud_fisica/reportes/**").hasAnyAuthority("A30","594")
			.requestMatchers("/aptitud_fisica/periodo/**").hasAnyAuthority("A30","595")
			.requestMatchers("/aptitud_fisica/antro/**").hasAnyAuthority("A30","596")
			.requestMatchers("/baja/solicitud/**").hasAnyAuthority("A31","610")
			.requestMatchers("/baja/autorizacion/**").hasAnyAuthority("A31","611")
			.requestMatchers("/baja/baja/**").hasAnyAuthority("A31","612")
			.requestMatchers("/baja/reportes/**").hasAnyAuthority("A31","613")
			.requestMatchers("/hca/actividad/**").hasAnyAuthority("A32","630")
			.requestMatchers("/hca/contrato/**").hasAnyAuthority("A32","631")
			.requestMatchers("/hca/maestro/**").hasAnyAuthority("A32","632")
			.requestMatchers("/hca/reporte/**").hasAnyAuthority("A32","633")
			.requestMatchers("/hca/analisis/**").hasAnyAuthority("A32","635")
			.requestMatchers("/hca/informe/**").hasAnyAuthority("A32","636")
			.requestMatchers("/hca/rango/**").hasAnyAuthority("A32","637")
			.requestMatchers("/hca/materias/**").hasAnyAuthority("A32","638")
			.requestMatchers("/certificacion/planes/**").hasAnyAuthority("A33","650")
			.requestMatchers("/certificacion/traspaso/**").hasAnyAuthority("A33","651")
			.requestMatchers("/certificacion/certificado/**").hasAnyAuthority("A33","652")
			.requestMatchers("/certificacion/envio/**").hasAnyAuthority("A33","654")
			.requestMatchers("/evaluacion/estudiantil/**").hasAnyAuthority("A34","670")
			.requestMatchers("/evaluacion/docente/**").hasAnyAuthority("A34","671")
			.requestMatchers("/evaluacion/pares/**").hasAnyAuthority("A34","674")
			.requestMatchers("/evaluacion/periodo/**").hasAnyAuthority("A34","675")
			.requestMatchers("/evaluacion/participacion/**").hasAnyAuthority("A34","676")
			.requestMatchers("/evaluacion/resultados/**").hasAnyAuthority("A34","677")
			.requestMatchers("/evaluacion/resestudiantil/**").hasAnyAuthority("A34","678")
			.requestMatchers("/graduacion/eventos/**").hasAnyAuthority("A35","690")
			.requestMatchers("/graduacion/graduacion/**").hasAnyAuthority("A35","691")
			.requestMatchers("/graduacion/paises/**").hasAnyAuthority("A35","692")
			.requestMatchers("/graduacion/gradua/**").hasAnyAuthority("A35","693")
			.requestMatchers("/graduacion/consulta/**").hasAnyAuthority("A35","694")
			.requestMatchers("/graduacion/atuendos/**").hasAnyAuthority("A35","695")
			.requestMatchers("/graduacion/total/**").hasAnyAuthority("A35","696")
			.requestMatchers("/graduacion/reconocimientos/**").hasAnyAuthority("A35","697")
			.requestMatchers("/admlinea/admision/**").hasAnyAuthority("A36","710")
			.requestMatchers("/admlinea/requisito/**").hasAnyAuthority("A36","711")
			.requestMatchers("/admlinea/documentos/**").hasAnyAuthority("A36","712")
			.requestMatchers("/admlinea/estadistica/**").hasAnyAuthority("A36","713")
			.requestMatchers("/admlinea/carrera/**").hasAnyAuthority("A36","714")
			.requestMatchers("/admlinea/sedes/**").hasAnyAuthority("A36","715")
			.requestMatchers("/admlinea/visto/**").hasAnyAuthority("A36","716")
			.requestMatchers("/admlinea/accesoVobo/**").hasAnyAuthority("A36","717")
			.requestMatchers("/admlinea/consulta/**").hasAnyAuthority("A36","718")
			.requestMatchers("/admlinea/proceso/**").hasAnyAuthority("A36","720")
			.requestMatchers("/admlinea/opciones/**").hasAnyAuthority("A36","721")
			.requestMatchers("/admlinea/plan/**").hasAnyAuthority("A36","724")
			.requestMatchers("/musica/alumno/**").hasAnyAuthority("A37","730")
			.requestMatchers("/musica/movtos/**").hasAnyAuthority("A37","731")
			.requestMatchers("/musica/inscripcion/**").hasAnyAuthority("A37","732")
			.requestMatchers("/musica/tabla/**").hasAnyAuthority("A37","733")
			.requestMatchers("/musica/cuenta/**").hasAnyAuthority("A37","734")
			.requestMatchers("/musica/periodo/**").hasAnyAuthority("A37","735")
			.requestMatchers("/musica/institucion/**").hasAnyAuthority("A37","736")
			.requestMatchers("/musica/tipocta/**").hasAnyAuthority("A37","737")
			.requestMatchers("/musica/estadistica/**").hasAnyAuthority("A37","738")
			.requestMatchers("/musica/pagare/**").hasAnyAuthority("A37","739")
			.requestMatchers("/musica/conmovtos/**").hasAnyAuthority("A37","740")
			.requestMatchers("/musica/catalogo/**").hasAnyAuthority("A37","741")
			.requestMatchers("/musica/maestro/**").hasAnyAuthority("A37","742")
			.requestMatchers("/musica/horario/**").hasAnyAuthority("A37","743")
			.requestMatchers("/musica/salon/**").hasAnyAuthority("A37","744")
			.requestMatchers("/musica/cursos/**").hasAnyAuthority("A37","745")
			.requestMatchers("/salida/grupos/**").hasAnyAuthority("A38","750")
			.requestMatchers("/salida/solicitud/**").hasAnyAuthority("A38","751")
			.requestMatchers("/salida/salidas/**").hasAnyAuthority("A38","752")
			.requestMatchers("/salida/permiso/**").hasAnyAuthority("A38","753")
			.requestMatchers("/salida/comidas/**").hasAnyAuthority("A38","754")
			.requestMatchers("/salida/informe/**").hasAnyAuthority("A38","755")
			.requestMatchers("/salida/proposito/**").hasAnyAuthority("A38","756")
			.requestMatchers("/vigilancia/auto/**").hasAnyAuthority("A39","770")
			.requestMatchers("/rendimiento/notas/**").hasAnyAuthority("A40","790")
			.requestMatchers("/rendimiento/extra/**").hasAnyAuthority("A40","791")
			.requestMatchers("/rendimiento/cargas/**").hasAnyAuthority("A40","792")
			.requestMatchers("/rendimiento/aprovechamiento/**").hasAnyAuthority("A40","793")
			.requestMatchers("/rendimiento/boletas/**").hasAnyAuthority("A40","794")
			.requestMatchers("/rendimiento/prom_carga/**").hasAnyAuthority("A40","795")
			.requestMatchers("/rendimiento/promedio/**").hasAnyAuthority("A40","796")
			.requestMatchers("/rendimiento/reprobado/**").hasAnyAuthority("A40","797")
			.requestMatchers("/rendimiento/materias/**").hasAnyAuthority("A40","799")
			.requestMatchers("/cartas/vacaciones/**").hasAnyAuthority("A41","080")
			.requestMatchers("/cartas/constancia/**").hasAnyAuthority("A41","095")
			.requestMatchers("/cartas/carta/**").hasAnyAuthority("A41","107")
			.requestMatchers("/cartas/constancia_mat/**").hasAnyAuthority("A41","810")
			.requestMatchers("/cartas/generador/**").hasAnyAuthority("A41","811")
			.requestMatchers("/cartas/imprimir/**").hasAnyAuthority("A41","812")
			.requestMatchers("/cartas/firma/**").hasAnyAuthority("A41","813")
			.requestMatchers("/notas/importado/**").hasAnyAuthority("A42","091")
			.requestMatchers("/notas/../**").hasAnyAuthority("A42","093")
			.requestMatchers("/notas/certificado/**").hasAnyAuthority("A42","094")
			.requestMatchers("/notas/convalidar/**").hasAnyAuthority("A42","096")
			.requestMatchers("/notas/../**").hasAnyAuthority("A42","097")
			.requestMatchers("/notas/avance/**").hasAnyAuthority("A42","098")
			.requestMatchers("/notas/corregir/**").hasAnyAuthority("A42","101")
			.requestMatchers("/notas/../**").hasAnyAuthority("A42","103")
			.requestMatchers("/notas/radiografia/**").hasAnyAuthority("A42","830")
			.requestMatchers("/notas/solicitud/**").hasAnyAuthority("A42","831")
			.requestMatchers("/analisis/avance/**").hasAnyAuthority("A43","852")
			.requestMatchers("/analisis/sabana/**").hasAnyAuthority("A43","853")
			.requestMatchers("/analisis/creditosMaestro/**").hasAnyAuthority("A43","855")
			.requestMatchers("/analisis/comparativo/**").hasAnyAuthority("A43","856")
			.requestMatchers("/analisis/est_estado/**").hasAnyAuthority("A43","858")
			.requestMatchers("/analisis/maestros/**").hasAnyAuthority("A43","862")
			.requestMatchers("/analisis/deptos/**").hasAnyAuthority("A43","863")
			.requestMatchers("/analisis/sueldos/**").hasAnyAuthority("A43","864")
			.requestMatchers("/analisis/distribucion/**").hasAnyAuthority("A43","865")
			.requestMatchers("/analisis/reportes/**").hasAnyAuthority("A43","866")
			.requestMatchers("/clinicos/hospital/**").hasAnyAuthority("A44","870")
			.requestMatchers("/clinicos/especialidad/**").hasAnyAuthority("A44","871")
			.requestMatchers("/clinicos/esphospital/**").hasAnyAuthority("A44","872")
			.requestMatchers("/clinicos/institucion/**").hasAnyAuthority("A44","873")
			.requestMatchers("/clinicos/programacion/**").hasAnyAuthority("A44","874")
			.requestMatchers("/clinicos/alumno/**").hasAnyAuthority("A44","875")
			.requestMatchers("/clinicos/pago/**").hasAnyAuthority("A44","876")
			.requestMatchers("/clinicos/reportes/**").hasAnyAuthority("A44","877")
			.requestMatchers("/taller/acceso/**").hasAnyAuthority("A45","890")
			.requestMatchers("/taller/puesto/**").hasAnyAuthority("A45","891")
			.requestMatchers("/taller/depto/**").hasAnyAuthority("A45","892")
			.requestMatchers("/taller/acuerdo/**").hasAnyAuthority("A45","893")
			.requestMatchers("/taller/tipo/**").hasAnyAuthority("A45","894")
			.requestMatchers("/taller/categoria/**").hasAnyAuthority("A45","895")
			.requestMatchers("/taller/reportes/**").hasAnyAuthority("A45","896")
			.requestMatchers("/taller/permiso/**").hasAnyAuthority("A45","897")
			.requestMatchers("/taller/parametro/**").hasAnyAuthority("A45","898")
			.requestMatchers("/taller/alta/**").hasAnyAuthority("A45","899")
			.requestMatchers("/taller/informe/**").hasAnyAuthority("A45","900")
			.requestMatchers("/taller/catinforme/**").hasAnyAuthority("A45","901")
			.requestMatchers("/taller/solicitud/**").hasAnyAuthority("A45","902")
			.requestMatchers("/taller/periodos/**").hasAnyAuthority("A45","903")
			.requestMatchers("/cultural/evento/**").hasAnyAuthority("A47","930")
			.requestMatchers("/cultural/asistencia/**").hasAnyAuthority("A47","931")
			.requestMatchers("/cultural/reporte/**").hasAnyAuthority("A47","932")
			.requestMatchers("/evalua_emp/planeacion/**").hasAnyAuthority("A48","950")
			.requestMatchers("/evalua_emp/jefes/**").hasAnyAuthority("A48","951")
			.requestMatchers("/evalua_emp/recursos/**").hasAnyAuthority("A48","953")
			.requestMatchers("/evalua_emp/centro/**").hasAnyAuthority("A48","954")
			.requestMatchers("/alerta/registro/**").hasAnyAuthority("A49","970")
			.requestMatchers("/alerta/periodos/**").hasAnyAuthority("A49","971")
			.requestMatchers("/alerta/ingreso/**").hasAnyAuthority("A49","972")
			.requestMatchers("/alerta/reportes/**").hasAnyAuthority("A49","973")
			.requestMatchers("/alerta/covid/**").hasAnyAuthority("A49","974")
			.requestMatchers("/investigacion/proyecto/**").hasAnyAuthority("A51","1000")
			.requestMatchers("/investigacion/admin/**").hasAnyAuthority("A51","1001")
			.requestMatchers("/investigacion/solicitud/**").hasAnyAuthority("A51","1002")
			.requestMatchers("/investigacion/adminsol/**").hasAnyAuthority("A51","1003")
			.requestMatchers("/investigacion/consulta/**").hasAnyAuthority("A51","1004")
			.requestMatchers("/porDocente/portafolio/**").hasAnyAuthority("A52","1020")
			.requestMatchers("/porDocente/porempleado/**").hasAnyAuthority("A52","1021")
			.requestMatchers("/porDocente/requisitos/**").hasAnyAuthority("A52","1022")
			.requestMatchers("/bitacora/mesa/**").hasAnyAuthority("A53","1040")
			.requestMatchers("/bitacora/tramite/**").hasAnyAuthority("A53","1041")
			.requestMatchers("/bitacora/seguimiento/**").hasAnyAuthority("A53","1042")
			.requestMatchers("/bitacora/status/**").hasAnyAuthority("A53","1043")
			.requestMatchers("/bitacora/area/**").hasAnyAuthority("A53","1044")
			.requestMatchers("/bitacora/consulta/**").hasAnyAuthority("A53","1045")
			.requestMatchers("/bitacora/estadistica/**").hasAnyAuthority("A53","1046")
			.requestMatchers("/bitacora/requisito/**").hasAnyAuthority("A53","1047")
			.requestMatchers("/bitacora/tramiteRequi/**").hasAnyAuthority("A53","1048")
			.requestMatchers("/bitacora/solicitud/**").hasAnyAuthority("A53","1049")
			.requestMatchers("/horas/periodo/**").hasAnyAuthority("A54","1060")
			.requestMatchers("/horas/horas/**").hasAnyAuthority("A54","1061")
			.requestMatchers("/horas/reporte/**").hasAnyAuthority("A54","1062")
			.requestMatchers("/horas/rango/**").hasAnyAuthority("A54","1063")
			.requestMatchers("/horas/docemp/**").hasAnyAuthority("A54","1064")
			.requestMatchers("/horas/contrato/**").hasAnyAuthority("A54","1066")
			.requestMatchers("/horas/presupuesto/**").hasAnyAuthority("A54","1067")
			.requestMatchers("/comedor/receta/**").hasAnyAuthority("A55","1080")
			.requestMatchers("/comedor/prima/**").hasAnyAuthority("A55","1081")
			.requestMatchers("/comedor/lista/**").hasAnyAuthority("A55","1082")
			.requestMatchers("/comedor/suma/**").hasAnyAuthority("A55","1083")
			.requestMatchers("/convenio/convenio/**").hasAnyAuthority("A56","1100")
			.requestMatchers("/convenio/tipos/**").hasAnyAuthority("A56","1101")
			.requestMatchers("/convenio/consulta/**").hasAnyAuthority("A56","1102")
			.requestMatchers("/expediente/documentos/**").hasAnyAuthority("A57","1120")
			.requestMatchers("/expediente/empleado/**").hasAnyAuthority("A57","1121")
			.requestMatchers("/expediente/consulta/**").hasAnyAuthority("A57","1122")
			.requestMatchers("/costo/concepto/**").hasAnyAuthority("A58","1041")
			.requestMatchers("/costo/costo/**").hasAnyAuthority("A58","1042")
			.requestMatchers("/costo/pagare/**").hasAnyAuthority("A58","1043")
			.requestMatchers("/portales/alumno/**").hasAnyAuthority("A59","1160","100")
			.requestMatchers("/portales/maestro/**").hasAnyAuthority("A59","1161")
			.requestMatchers("/portales/mentor/**").hasAnyAuthority("A59","1162")
			.requestMatchers("/portales/portafolio/**").hasAnyAuthority("A59","1163")
			.requestMatchers("/portales/preceptor/**").hasAnyAuthority("A59","1164")
			.requestMatchers("/diploma/diploma/**").hasAnyAuthority("A60","1180")
			.requestMatchers("/encuesta/periodo/**").hasAnyAuthority("A61","1200")
			.anyRequest().authenticated()
			)
			.formLogin(formLogin -> formLogin
					.loginPage("/login")
					.loginProcessingUrl("/login")
					.failureUrl("/login?error=1")
					.defaultSuccessUrl("/validaSecurity")
					.usernameParameter("usuario")
					.passwordParameter("password")
					.permitAll()
			).logout(
					logout -> logout
					.logoutRequestMatcher(new AntPathRequestMatcher("/salir"))
					.logoutSuccessUrl("/login") // Explicitly redirect to /login
					.invalidateHttpSession(true) // Ensure the session is invalidated
					.deleteCookies("JSESSIONID") // Delete the session cookie
					// .addLogoutHandler((request, response, authentication) -> {
					// 	System.out.println("User logged out. Invalidating session.");
					// })
					.permitAll()
			);
			http.headers(
					headers -> headers
					.frameOptions(
							frameOptions -> frameOptions
							.sameOrigin()
					)
			);
		 return http.build();
	}
}