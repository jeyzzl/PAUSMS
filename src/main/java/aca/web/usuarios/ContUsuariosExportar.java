package aca.web.usuarios;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

// import com.itextpdf.io.source.OutputStream;

import aca.FtpClientUtil;
import aca.SftpClientUtil;
import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatEscuela;
import aca.catalogo.spring.CatEscuelaDao;
import aca.catalogo.spring.CatEstadoDao;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatPaisDao;
import aca.dherst.spring.DherstAlumno;
import aca.dherst.spring.DherstAlumnoDao;
import aca.plan.spring.MapaMayorMenorDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.trabajo.spring.TrabAlum;
import aca.vista.spring.Maestros;

@Controller
public class ContUsuariosExportar {
	

    @Autowired	
	private AccesoDao accesoDao;

    @Autowired
    private AlumPersonalDao alumPersonalDao;

    @Autowired
    private AlumPlanDao alumPlanDao;

	@Autowired
	private CatFacultadDao catFacultadDao;

	@Autowired
	private MapaPlanDao mapaPlanDao;

	@Autowired
	private DherstAlumnoDao dherstAlumnoDao;

	@Autowired
	private CatPaisDao catPaisDao;

	@Autowired
	private CatEstadoDao catEstadoDao;

	@Autowired
	private CatCarreraDao catCarreraDao;

	@Autowired
	private MapaMayorMenorDao mapaMayorMenorDao;

	@RequestMapping("/usuarios/exportar/lista")
	public String usuariosExportarLista(HttpServletRequest request, Model modelo){
		String codigoPersonal 	= "0";
		String admin			= "N";
		String status 			= request.getParameter("Status")==null?"N":request.getParameter("Status");
		String plan 			= request.getParameter("Plan")==null?"T":request.getParameter("Plan");

		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
			admin				= accesoDao.mapeaRegId(codigoPersonal).getAdministrador();
		}
		
		List<AlumPersonal> lisAlumnos		= null;

		String planQuery = "";
		if(plan.equals("A")) planQuery = " AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ALUM_PLAN WHERE ESTADO = '1')";
		if(plan.equals("N")) planQuery = " AND CODIGO_PERSONAL  NOT IN (SELECT CODIGO_PERSONAL FROM ALUM_PLAN WHERE ESTADO = '1')";

		if(status.equals("N")){
			lisAlumnos = alumPersonalDao.lisExportar(planQuery+" ORDER BY F_CREADO");
		}
		if(status.equals("S")){
			lisAlumnos = alumPersonalDao.lisExportados(planQuery+" ORDER BY F_CREADO");
		}
		
		HashMap<String,Acceso> mapaAccesos 	= accesoDao.mapaTodos();
		HashMap<String,String> mapaAlumPlan = alumPlanDao.mapaPlanesActivosTodos();
		
		modelo.addAttribute("admin", admin);
		modelo.addAttribute("status", status);
		modelo.addAttribute("plan", plan);
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("mapaAccesos", mapaAccesos);
		modelo.addAttribute("mapaAlumPlan", mapaAlumPlan);

		return "usuarios/exportar/lista";
	}

	@RequestMapping("/usuarios/exportar/exportar")
	public String usuariosExportarExportar(HttpServletRequest request, Model modelo){
	// public void usuariosExportarExportar(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String codigoPersonal 	= "";
		String mensaje 			= "";

		HttpSession sesion  = ((HttpServletRequest)request).getSession();
		if(sesion  != null) {
			codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");
		}

		int assigned = 0;
		int errors = 0;
		List<String> errorStudents = new ArrayList<>();
        List<String> kfiFileContent = new ArrayList<>();

		// Add the header row
		kfiFileContent.add("<Object_ID=84>,,,,,,,,,,,,,,");
		kfiFileContent.add("<HEADROW>code,name,desc03,sortdtl,glsetno,costcentcd,dscntpct,desc01,desc02,comment,country,state,emailaddr,invform,taxcode,<ENDHEADROW>");

		List<AlumPersonal> lisAlumnos = alumPersonalDao.getListAll(" ORDER BY F_CREADO");
		for(AlumPersonal alumno : lisAlumnos){
			String codigoAlumno = request.getParameter(alumno.getCodigoPersonal());
			if (codigoAlumno != null && alumno.getCodigoPersonal().equals(codigoAlumno)) {
				if(alumPersonalDao.existeReg(alumno.getCodigoPersonal())){
					// retrieves students details and adds it to the KFI file.
					AlumPlan alumPlan = alumPlanDao.mapeaRegId(alumno.getCodigoPersonal());      
					String studentLine = formatStudentDetailsForKFI(alumno, alumPlan);
					// System.out.println("E: "+studentLine);
					kfiFileContent.add(studentLine);
					assigned++;
				}else{
					System.out.println("N.E: "+codigoAlumno);
					// error getting student details
					errors++;
					errorStudents.add(codigoAlumno);
				}
			}
		}
		
		// Download KFI file
		// String fileContent = String.join("\n", kfiFileContent);

		// // Set response headers for file download
		// response.setContentType("text/csv");
		// response.setHeader("Content-Disposition", "attachment; filename=\"2025customerkfi.kfi\"");
	
		// // Write the file content to the response output stream
		// try (ServletOutputStream outputStream = response.getOutputStream()) {
		// 	outputStream.write(fileContent.getBytes(StandardCharsets.UTF_8));
		// 	outputStream.flush();
		// } catch (IOException e) {
		// 	e.printStackTrace();
		// 	response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		// 	response.getWriter().write("Error generating the KFI file: " + e.getMessage());
		// }

    	// Generate KFI file
		if (assigned > 0) {
			try {
				// Create a temporary file
				File kfiFile = File.createTempFile("SMS_Export", ".kfi");
				kfiFile.deleteOnExit(); // Ensure the file is deleted when the JVM exits

				// Add UTF-8 BOM (optional)
				String bom = "\uFEFF";
				kfiFileContent.add(0, bom); // Add BOM at the beginning of the file

				// Use CRLF line endings for Windows compatibility
				String lineEnding = "\r\n";
				String fileContent = String.join(lineEnding, kfiFileContent);

				// Write the KFI file content
				Files.write(kfiFile.toPath(), fileContent.getBytes(StandardCharsets.UTF_8));

				// Upload the file to the SFTP server
				SftpClientUtil.uploadFile(
					"sftp.attache.theaccessgroup.com.au",
					22, 
					"PAC0241CAUsftp_User", 
					"Is!6?JD#A5c?Efauh4#29!R3", 
					kfiFile.getAbsolutePath(), 
					"/PAU_ACCOUNTS_CURRENT"
				);

				mensaje = "Exported " + assigned + " students to KFI file and uploaded to SFTP server.";
			} catch (Exception e) {
				e.printStackTrace();
				mensaje = "Error generating or uploading the KFI file: " + e.getMessage();
			}
		} else {
			mensaje = "No students were exported.";
		}

		if (errors >= 1) {
			mensaje += " Errors exporting students: " + String.join(", ", errorStudents);
		}

		modelo.addAttribute("mensaje", mensaje);

		return "redirect:/usuarios/exportar/lista";
	}

    private String formatStudentDetailsForKFI(AlumPersonal alumno, AlumPlan plan) {
		HashMap<String,DherstAlumno> mapaDherst = dherstAlumnoDao.mapaAlumnos();

		String pais = catPaisDao.getSepPais(alumno.getPaisId());
		String estado = catEstadoDao.getNombreCorto(alumno.getPaisId(), alumno.getEstadoId()); 
		String escuela = catCarreraDao.getNombreCorto(mapaPlanDao.getCarreraId(plan.getPlanId()));

		String nombrePlan = truncateUtf8(mapaPlanDao.getNombreSe(plan.getPlanId()), 30);
		String mayor = truncateUtf8(mapaMayorMenorDao.getMayor(alumno.getCodigoPersonal()), 30);

		CatCarrera carrera = catCarreraDao.mapeaRegId(mapaPlanDao.getCarreraId(plan.getPlanId()));

		boolean esDherst = mapaDherst.containsKey(alumno.getCodigoPersonal())?true:false;

        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,",
            alumno.getCodigoPersonal(), // code
            formatName(alumno.getNombre(), alumno.getApellidoMaterno(), alumno.getApellidoPaterno()), // name
            alumno.getSexo(), 			// desc03
            escuela, 					// sortdtl
            carrera.getGlsetno(), 		// glsetno
            carrera.getCostcentcd(), 	// costcentcd
            carrera.getDscntpct(), 		// dscntpct
            nombrePlan, 				// desc01
            mayor, 						// desc02
            esDherst?"DHERST":"SELF", 	// comment
            pais, 						// country
            estado, 					// state
            alumno.getEmail(), 			// emailaddr
            "STUDENTS", 				// invform
			"N"							// taxcode
        );
    }

	/**
	 * Formats the student's name by concatenating nombre, apellidoMaterno, and apellidoPaterno.
	 * Handles empty or null values.
	 */
	private String formatName(String nombre, String apellidoMaterno, String apellidoPaterno) {
		StringBuilder nameBuilder = new StringBuilder();
		nameBuilder.append(nombre != null ? nombre : "");

		if (apellidoMaterno != null && !apellidoMaterno.isEmpty() && !apellidoMaterno.equals(" ")) {
			nameBuilder.append(" ").append(apellidoMaterno);
		}

		if (apellidoPaterno != null && !apellidoPaterno.isEmpty() && !apellidoPaterno.equals(" ")) {
			nameBuilder.append(" ").append(apellidoPaterno);
		}

		return nameBuilder.toString();
	}
    
	/**
	 * Truncates a string to a maximum length, ensuring UTF-8 compatibility.
	 * Avoids cutting multi-byte characters in half.
	 */
	private String truncateUtf8(String input, int maxLength) {
		if (input == null || input.length() <= maxLength) {
			return input;
		}

		// Convert the string to a byte array using UTF-8 encoding
		byte[] utf8Bytes = input.getBytes(StandardCharsets.UTF_8);

		// If the byte array is already within the limit, return the original string
		if (utf8Bytes.length <= maxLength) {
			return input;
		}

		// Truncate the byte array to the maximum length
		byte[] truncatedBytes = Arrays.copyOf(utf8Bytes, maxLength);

		// Convert the truncated byte array back to a string
		return new String(truncatedBytes, StandardCharsets.UTF_8);
	}

	@RequestMapping("/usuarios/exportar/syncConfig")
	public String usuariosExportarSyncConfig(HttpServletRequest request, Model modelo){
		String codigoPersonal 	= "0";
		String admin			= "N";
		String sync 			= request.getParameter("Sync")==null?"N":request.getParameter("Sync");
		String option 			= request.getParameter("Option")==null?"S":request.getParameter("Option");

		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
			admin				= accesoDao.mapeaRegId(codigoPersonal).getAdministrador();
		}
		
		List<AlumPersonal> lisAlumnos		= alumPersonalDao.lisSync(sync, " ORDER BY F_CREADO");
		
		HashMap<String,Acceso> mapaAccesos 	= accesoDao.mapaTodos();
		HashMap<String,String> mapaAlumPlan = alumPlanDao.mapaPlanesActivosTodos();
		
		modelo.addAttribute("admin", admin);
		modelo.addAttribute("sync", sync);
		modelo.addAttribute("option", option);
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("mapaAccesos", mapaAccesos);
		modelo.addAttribute("mapaAlumPlan", mapaAlumPlan);

		return "usuarios/exportar/syncConfig";
	}

	@RequestMapping("/usuarios/exportar/removeSync")
	public String usuariosExportarRemoveSync(HttpServletRequest request, Model modelo){
		String mensaje 			= "";
		int assigned = 0;
		int errors = 0;
		List<String> errorStudents = new ArrayList<>();
		String action = request.getParameter("Action")==null?"N":request.getParameter("Action");

		List<AlumPersonal> lisAlumnos = alumPersonalDao.getListAll(" ORDER BY F_CREADO");
		for(AlumPersonal alumno : lisAlumnos){
			String codigoAlumno = request.getParameter(alumno.getCodigoPersonal());
			if (codigoAlumno != null && alumno.getCodigoPersonal().equals(codigoAlumno)) {
				if(alumPersonalDao.existeReg(alumno.getCodigoPersonal())){
					// retrieves students details and adds it to the KFI file.
					if(alumPersonalDao.updateSync(codigoAlumno, action)){
						assigned++;
					}else{
						System.out.println("N.E: "+codigoAlumno);
						// error updating student details
						errors++;
						errorStudents.add(codigoAlumno);
					}
				}else{
					System.out.println("N.E: "+codigoAlumno);
					// error getting student details
					errors++;
					errorStudents.add(codigoAlumno);
				}
			}
		}

		if (errors >= 1) {
			mensaje += " Errors removing sync from students: " + String.join(", ", errorStudents);
		}

		modelo.addAttribute("mensaje", mensaje);

		return "redirect:/usuarios/exportar/syncConfig";
	}
}
