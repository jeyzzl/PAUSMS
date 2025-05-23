package aca.web.usuarios;

import java.io.PrintWriter;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanDao;
import aca.catalogo.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.emp.spring.EmpMaestroDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.Maestros;
import aca.vista.spring.MaestrosDao;
import aca.vista.spring.UsuariosDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class ContUsuariosSincronizar {
    @Autowired
	private UsuariosDao usuariosDao;
	
	@Autowired
	private AccesoDao accesoDao;

    @Autowired
	AlumPersonalDao alumPersonalDao;

    @Autowired
    EmpMaestroDao empMaestroDao;

    @Autowired
    MaestrosDao maestrosDao;

    @Autowired
    AlumPlanDao alumPlanDao;

    @Autowired
    MapaPlanDao mapaPlanDao;

    @Autowired
    CatCarreraDao catCarreraDao;

    @RequestMapping("/usuarios/sincronizar/usuarios")
    public String usuariosSincronizarUsuarios(HttpServletRequest request, Model modelo){
        String tipo 			= request.getParameter("Tipo")==null?"E":request.getParameter("Tipo");
        String status           = request.getParameter("Status")==null?"N":request.getParameter("Status");
        String codigoPersonal 	= "0";
		String admin			= "N";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
			admin				= accesoDao.mapeaRegId(codigoPersonal).getAdministrador();
		}

        // Lista de maestros filtrada por status de Acceso.AlumnoMovil
        List<Maestros> lisMaestros			= maestrosDao.getListAll(" WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ACCESO WHERE ALUMNO_MOVIL = '"+status+"') ORDER BY APELLIDO_PATERNO,APELLIDO_MATERNO,NOMBRE");
		List<AlumPersonal> lisAlumnos		= alumPersonalDao.getListAll(" WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ACCESO WHERE ALUMNO_MOVIL = '"+status+"') ORDER BY APELLIDO_PATERNO,APELLIDO_MATERNO,NOMBRE");
		
        HashMap<String,Acceso> mapaAccesos 	= accesoDao.mapaTodos();

        modelo.addAttribute("admin", admin);
		modelo.addAttribute("lisMaestros", lisMaestros);
		modelo.addAttribute("lisAlumnos", lisAlumnos);
        modelo.addAttribute("mapaAccesos", mapaAccesos);
        
        return "usuarios/sincronizar/usuarios";
    }

    @RequestMapping("/usuarios/sincronizar/generar")
    public ResponseEntity<Void> usuariosSincronizarGenerar(HttpServletRequest request, HttpServletResponse response){
        String tipo 			= request.getParameter("TipoArchivo")==null?"E":request.getParameter("TipoArchivo");
        String codigoPersonal 	= "0";
		String admin			= "N";

		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
			admin				= accesoDao.mapeaRegId(codigoPersonal).getAdministrador();  
		}

        List<Maestros> lisMaestros			= maestrosDao.getListAll(" WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ACCESO WHERE ALUMNO_MOVIL = 'N') ORDER BY APELLIDO_PATERNO,APELLIDO_MATERNO,NOMBRE");
		List<AlumPersonal> lisAlumnos		= alumPersonalDao.getListAll(" WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ACCESO WHERE ALUMNO_MOVIL = 'N') ORDER BY APELLIDO_PATERNO,APELLIDO_MATERNO,NOMBRE");

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"users_sync.csv\"");
        
        int synced = 0;

        try (PrintWriter writer = response.getWriter()) {
            // Write CSV header
            writer.println("Username,FirstName,LastName,Description,Email,UPN,Password,ScriptPath,HomeDrive,HomeDirectory,OU");

            // Process EMPLOYEES or STUDENTS based on "TipoArchivo"
            if ("E".equals(tipo)) {
                for (Maestros maestro : lisMaestros) {
                    String codigoMaestro = request.getParameter(maestro.getCodigoPersonal());
                    Acceso acceso = new Acceso();
                    if (codigoMaestro != null && maestro.getCodigoPersonal().equals(codigoMaestro)) {
                        // Generate random 7 character password 
                        String contrasena = generateRandomPassword(7);
                        acceso = accesoDao.mapeaRegId(codigoMaestro);
                        writer.printf("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,\"%s\"%n",
                                "s"+maestro.getCodigoPersonal(),
                                maestro.getNombre(),
                                maestro.getApellidoMaterno() == null ? "" : maestro.getApellidoMaterno() + " " + maestro.getApellidoPaterno(),
                                "Employee",
                                maestro.getEmail(),
                                maestro.getEmail(),
                                contrasena,
                                "logon_students.bat",
                                "H",
                                "\\\\pom-file2.pau.ac.pg\\studenthome$\\s"+maestro.getCodigoPersonal(),
                                "OU=PAU,OU=HET,OU=Koiari Park,OU=Students,OU=User Accounts,DC=pau,DC=ac,DC=pg"
                                );
                        acceso.setAlumnoMovil("S");
                        if(accesoDao.updateReg(acceso)){
                            synced++;
                        }
                    }
                }
            } else if ("A".equals(tipo)) {
                for (AlumPersonal alumno : lisAlumnos) {
                    String codigoAlumno = request.getParameter(alumno.getCodigoPersonal());
                    Acceso acceso = new Acceso();
                    if (codigoAlumno != null && alumno.getCodigoPersonal().equals(codigoAlumno)) {
                        // Generar contrasena de 7 digitos
                        String contrasena = generateRandomPassword(7);
                        // Obtener label de plan del alumno
                        String planId = alumPersonalDao.getPlanActivo(codigoAlumno);
                        String carreraId = mapaPlanDao.getCarreraId(planId);
                        String nombreCarrera = catCarreraDao.getNombreCarrera(carreraId);
                        String description = nombreCarrera.replace("Bachelor of ", "");
                        //
                        acceso = accesoDao.mapeaRegId(codigoAlumno);
                        writer.printf("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,\"OU=%s,OU=HET,OU=Koiari Park,OU=Students,OU=User Accounts,DC=pau,DC=ac,DC=pg\"%n",
                                "s"+alumno.getCodigoPersonal(),
                                alumno.getNombre(),
                                alumno.getApellidoMaterno()==null?"":alumno.getApellidoMaterno()+" "+alumno.getApellidoPaterno(),
                                "Student - "+description,
                                alumno.getEmail(),
                                alumno.getEmail(),
                                contrasena,
                                "logon_students.bat",
                                "H",
                                "\\\\pom-file2.pau.ac.pg\\studenthome$\\s"+alumno.getCodigoPersonal(),
                                description
                                );
                        acceso.setAlumnoMovil("S");
                        if(accesoDao.updateReg(acceso)){
                            synced++;
                        }
                    }
                }
            }
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().build();
    }

    @RequestMapping("/usuarios/sincronizar/eliminar")
    public String usuariosSincronizarEliminar(HttpServletRequest request, Model modelo){
        String codigoPersonal   = request.getParameter("CodigoPersonal")==null?"0":request.getParameter("CodigoPersonal");
        String tipo             = request.getParameter("Tipo")==null?"0":request.getParameter("Tipo");
        String status           = request.getParameter("Status")==null?"0":request.getParameter("Status");
        String mensaje = "";

        Acceso acceso = new Acceso();
        if(accesoDao.existeReg(codigoPersonal)){
            acceso = accesoDao.mapeaRegId(codigoPersonal);
            acceso.setAlumnoMovil("N");
            if(accesoDao.updateReg(acceso)){
                mensaje = "1";
            }else{
                mensaje = "0";
            }
        }

        return "redirect:/usuarios/sincronizar/usuarios?Tipo="+tipo+"&Status="+status+"&Mensaje="+mensaje;
    }

    private String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(length);
        
        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        
        return password.toString();
    }
}
