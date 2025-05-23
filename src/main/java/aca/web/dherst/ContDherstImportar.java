package aca.web.dherst;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import com.opencsv.CSVReader;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.bouncycastle.jcajce.provider.asymmetric.dsa.DSASigner.detDSA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import aca.dherst.spring.DherstArchivo;
import aca.dherst.spring.DherstArchivoDao;
import aca.parametros.spring.Parametros;
import aca.parametros.spring.ParametrosDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumAcademico;
import aca.alumno.spring.AlumBanco;
import aca.alumno.spring.AlumEstudio;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumUbicacion;
import aca.catalogo.spring.CatCulturalDao;
import aca.catalogo.spring.CatEstado;
import aca.catalogo.spring.CatEstadoDao;
import aca.catalogo.spring.CatRegionDao;
import aca.catalogo.spring.CatReligion;
import aca.catalogo.spring.CatReligionDao;
import aca.dherst.spring.DherstAlumno;
import aca.dherst.spring.DherstAlumnoDao;

@Controller
public class ContDherstImportar {

	@Autowired
	private DherstArchivoDao dherstArchivoDao;

	@Autowired
	private DherstAlumnoDao dherstAlumnoDao;

	@Autowired
	private AlumPersonalDao alumPersonalDao;

	@Autowired
	private AccesoDao accesoDao;

	@Autowired
	private ParametrosDao parametrosDao;

	@Autowired
	private CatCulturalDao catCulturalDao;

	@Autowired
	private CatRegionDao catRegionDao;

	@Autowired
	private CatEstadoDao catEstadoDao;

	@Autowired
	private CatReligionDao catReligionDao;

	@Autowired
	private MapaPlanDao mapaPlanDao;

	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;

	@RequestMapping("/dherst/importar/alumnos")
	public String dherstImportarAlumos(HttpServletRequest request, Model modelo) {
		// logic for main view
		List<DherstArchivo> listArchivos = dherstArchivoDao.getListAll(" ORDER BY FOLIO");

		HashMap<String,String> mapaAlumnosPorArchivo = dherstArchivoDao.mapaAlumnosPorArchivo();

		modelo.addAttribute("listArchivos",listArchivos);
		modelo.addAttribute("mapaAlumnosPorArchivo", mapaAlumnosPorArchivo);

		return "dherst/importar/alumnos";
	}

	@RequestMapping("/dherst/importar/subirArchivo")
	public String dherstImportarSubir(HttpServletRequest request, Model modelo) {
		// logic for view to upload csv file
		DherstArchivo archivo 	= new DherstArchivo();
		String folio 			= request.getParameter("folio")==null?"0":request.getParameter("folio");
		String codigoPersonal 	= "";
		boolean existeArchivo 	= false;

		if(dherstArchivoDao.existeReg(folio)){
			archivo = dherstArchivoDao.mapeaRegId(folio);
			existeArchivo = true;
		}

		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			codigoPersonal 		= sesion.getAttribute("codigoPersonal").toString();		
		}

		List<DherstAlumno> listaAlumnos = dherstAlumnoDao.getListaArchivo(folio, " ORDER BY NOMBRE, APELLIDO");
	
		modelo.addAttribute("archivo", archivo);
		modelo.addAttribute("existeArchivo", existeArchivo);
		modelo.addAttribute("listaAlumnos", listaAlumnos);

		return "dherst/importar/subirArchivo";
	}

	@PostMapping("/dherst/importar/guardarArchivo")
	public String dherstImportarGuardar(@RequestParam("archivo") MultipartFile file, HttpServletRequest request){
		// logic to process the csv file and upload the records to the DB
		DherstArchivo archivo 	= new DherstArchivo();
		String folio 			= request.getParameter("folio")==null?"0":request.getParameter("folio");
		String codigoPersonal 	= "";
		String comentario 		= request.getParameter("comentario")==null?"":request.getParameter("comentario");
		String mensaje  		= "";
		boolean esCSV 			= false;
		boolean grabo 			= false;

		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			codigoPersonal 		= sesion.getAttribute("codigoPersonal").toString();		
		}

		if (file.isEmpty()) {
			mensaje = "4";
            return "redirect:/dherst/importar/subirArchivo?folio="+folio+"&mensaje="+mensaje;
        }

		try{
			// Set the file object
			archivo.setNombre(file.getOriginalFilename());
			archivo.setArchivo(file.getBytes());
			archivo.setFecha(aca.util.Fecha.getHoy());
			archivo.setCodigoUsuario(codigoPersonal);
			archivo.setComentario(comentario);

			// Checks if it is a csv file
			if (file.getOriginalFilename().toLowerCase().contains(".csv")) esCSV = true;
			
			// Store file in the DB
			if(esCSV){
				// Updates file if it already exist
				if(dherstArchivoDao.existeReg(folio)){
					archivo.setFolio(folio);
					if(dherstArchivoDao.updateReg(archivo)){
						mensaje = "1";
					}else{
						mensaje = "2";
					}
				}else{ // Saves the new file if it doesn't exist
					folio = dherstArchivoDao.maximoReg();
					archivo.setFolio(folio);
					if(dherstArchivoDao.insertReg(archivo)){
						mensaje = "1";
					}else{
						mensaje = "2";
					}
				}
			}else{
				mensaje = "2";
			}

			// CSV processing logic
			try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
				CSVReader csvReader = new CSVReader(reader);
				String[] line;
				int inserted = 0;
				int updated = 0;
				int errors = 0;
				// Creates student object
				DherstAlumno alumno = new DherstAlumno();

				// Read each line from the CSV file
				while ((line = csvReader.readNext()) != null) {
					// Extract data from each row (adjust indexes based on your CSV structure)
					alumno.setFolio(archivo.getFolio());
					alumno.setCodigoPersonal("0");
					alumno.setSlfNo(line[0].trim()); // Assuming first column is student ID
					alumno.setNombre(line[1]);
					alumno.setApellido(line[2]);
					alumno.setDireccion(line[3]);
					alumno.setEmail(line[4].trim());
					alumno.setTelefono(line[5]);
					alumno.setCelular(line[6]);
					alumno.setGpa(dherstAlumnoDao.validarGPA(line[7]));
					alumno.setSexo(line[8].trim().equals("Male")?"M":"F");
					alumno.setResidencia(line[9].trim());
					alumno.setResEstadoId(dherstAlumnoDao.validarEstado(line[10]));
					alumno.setEstadoId(dherstAlumnoDao.validarEstado(line[11]));
					alumno.setReligionId(dherstAlumnoDao.validarReligion(line[12].trim()));
					alumno.setPrefAeropuerto(line[13]);
					alumno.setEstadoCivil(dherstAlumnoDao.validarEstadoCivil(line[14]));
					alumno.setResidenciaTipo(dherstAlumnoDao.validarResidencia(line[15]));
					alumno.setPlanId(dherstAlumnoDao.validarPlanId(line[16]));
					alumno.setStatus("U");

					System.out.println("Student: "+alumno.getSlfNo()+" : "+alumno.getNombre()+" : "+alumno.getApellido()+" : "+alumno.getDireccion()+" : "+alumno.getEmail()+" : "+alumno.getTelefono()+" : "+alumno.getCelular()+" : "+alumno.getGpa()+" : "+alumno.getSexo()+" : "+alumno.getResidencia()
					+" : "+alumno.getResEstadoId()+" : "+alumno.getEstadoId()+" : "+alumno.getReligionId()+" : "+alumno.getPrefAeropuerto()+" : "+alumno.getEstadoCivil()+" : "+alumno.getResidenciaTipo());
					// Insert into Oracle DB using JdbcTemplate
					if(!dherstAlumnoDao.existeReg(alumno.getSlfNo())){
						if(dherstAlumnoDao.insertReg(alumno)){
							mensaje = "1";
							inserted++;
						}else{
							mensaje = "2";
							errors++;
						}
					}else{
						if(dherstAlumnoDao.updateReg(alumno)){
							mensaje = "1";
							updated++;
						}else{
							mensaje = "2";
							errors++;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				return "redirect:/dherst/importar/subirArchivo?error=processingError";
			}

		}catch(Exception ex){
			System.out.println(ex);
		}

		return "redirect:/dherst/importar/subirArchivo?folio="+folio;
	}

	@RequestMapping("/dherst/importar/borrarArchivo")
	public String dherstImportarBorrar(HttpServletRequest request, Model modelo){
		// logic to delete csv file from database
		String folio 	= request.getParameter("folio")==null?"0":request.getParameter("folio");
		String mensaje 	= "";

		if(dherstArchivoDao.deleteReg(folio)){
			mensaje = "1";
		}else{
			mensaje = "2";
		}

		return "redirect:/dherst/importar/subirArchivo?mensaje="+mensaje;
	}

	@RequestMapping("/dherst/importar/borrarEstudiantes")
	public String dherstImportarBorrarEstuadiantes(HttpServletRequest request, Model modelo){
		String folio 	= request.getParameter("folio")==null?"0":request.getParameter("folio");
		String mensaje 	= "";

		List<DherstAlumno> listaAlumnos = dherstAlumnoDao.getListaArchivo(folio, " ORDER BY NOMBRE, APELLIDO");

		boolean transferidos = false;
		for(DherstAlumno alumno : listaAlumnos){
			if(alumno.getStatus().equals("T")) transferidos = true;
		}

		if(!transferidos){
			if(dherstAlumnoDao.deleteStudents(folio)){
				mensaje = "1";
			}
		}else{
			mensaje = "3";
		}

		return "redirect:/dherst/importar/subirArchivo?folio="+folio+"&mensaje="+mensaje;
	}

	@RequestMapping("/dherst/importar/alumno")
	public String dherstImportarAlumno(HttpServletRequest request, Model modelo){
		String folio 			= request.getParameter("folio")==null?"0":request.getParameter("folio");
		String slfNo 			= request.getParameter("slfNo")==null?"0":request.getParameter("slfNo");
		String codigoPersonal 	= "";
		Parametros parametros	= parametrosDao.mapeaRegId("1");

		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			codigoPersonal 		= sesion.getAttribute("codigoPersonal").toString();		
		}

		List<CatEstado> lisEstados 		= catEstadoDao.getLista(parametros.getPaisId(),"");
		List<CatReligion> lisReligiones = catReligionDao.getListAll("");
		List<MapaPlan> lisPlanes = mapaPlanDao.getListAll(" ORDER BY CARRERA_ID, NOMBRE_PLAN");

		System.out.println(">"+slfNo+"<");

		DherstAlumno alumno = new DherstAlumno();
		if(dherstAlumnoDao.existeReg(slfNo)){
			alumno = dherstAlumnoDao.mapeaRegId(slfNo);
			System.out.println("EXISTE");
		}

		modelo.addAttribute("folio", folio);
		modelo.addAttribute("alumno", alumno);
		modelo.addAttribute("lisEstados", lisEstados);
		modelo.addAttribute("lisReligiones", lisReligiones);
		modelo.addAttribute("lisPlanes", lisPlanes);

		return "dherst/importar/alumno";		
	}

	@RequestMapping("/dherst/importar/guardarAlumno")
	public String dherstImportarGuardarAlumno(HttpServletRequest request, Model modelo){
		String folio 			= request.getParameter("folio")==null?"0":request.getParameter("folio");
		String codigoPersonal 	= request.getParameter("codigoPersonal")==null?"0":request.getParameter("codigoPersonal");
		String slfNo 			= request.getParameter("slfNo")==null?"0":request.getParameter("slfNo");
		String nombre 			= request.getParameter("nombre")==null?"0":request.getParameter("nombre");
		String apellido 		= request.getParameter("apellido")==null?"0":request.getParameter("apellido");
		String direccion 		= request.getParameter("direccion")==null?"0":request.getParameter("direccion");
		String email 			= request.getParameter("email")==null?"0":request.getParameter("email");
		String telefono 		= request.getParameter("telefono")==null?"0":request.getParameter("telefono");
		String celular 			= request.getParameter("celular")==null?"0":request.getParameter("celular");
		String gpa 				= request.getParameter("gpa")==null?"0":request.getParameter("gpa");
		String sexo 			= request.getParameter("sexo")==null?"0":request.getParameter("sexo");
		String residencia 		= request.getParameter("residencia")==null?"0":request.getParameter("residencia");
		String resEstadoId 		= request.getParameter("resEstadoId")==null?"0":request.getParameter("resEstadoId");
		String estadoId 		= request.getParameter("estadoId")==null?"0":request.getParameter("estadoId");
		String religionId 		= request.getParameter("religionId")==null?"0":request.getParameter("religionId");
		String prefAeropuerto 	= request.getParameter("prefAeropuerto")==null?"0":request.getParameter("prefAeropuerto");
		String estadoCivil 		= request.getParameter("estadoCivil")==null?"0":request.getParameter("estadoCivil");
		String residenciaTipo 	= request.getParameter("residenciaTipo")==null?"0":request.getParameter("residenciaTipo");
		String planId 			= request.getParameter("planId")==null?"0":request.getParameter("planId");
		String status 			= request.getParameter("status")==null?"0":request.getParameter("status");
		String mensaje 			= "";

		DherstAlumno alumno = new DherstAlumno();
		alumno.setFolio(folio);
		alumno.setSlfNo(slfNo);
		alumno.setCodigoPersonal(codigoPersonal);
		alumno.setNombre(nombre);
		alumno.setApellido(apellido);
		alumno.setDireccion(direccion);
		alumno.setEmail(email);
		alumno.setTelefono(telefono);
		alumno.setCelular(celular);
		alumno.setGpa(gpa);
		alumno.setSexo(sexo);
		alumno.setResidencia(residencia);
		alumno.setResEstadoId(resEstadoId);
		alumno.setEstadoId(estadoId);
		alumno.setReligionId(religionId);
		alumno.setPrefAeropuerto(prefAeropuerto);
		alumno.setEstadoCivil(estadoCivil);
		alumno.setResidenciaTipo(residenciaTipo);
		alumno.setPlanId(planId);
		alumno.setStatus(status);

		if(dherstAlumnoDao.existeReg(slfNo)){
			if(dherstAlumnoDao.updateReg(alumno)){
				mensaje = "1";
			}else{
				mensaje = "2";
			}
		}

		return "redirect:/dherst/importar/alumno?folio="+folio+"&slfNo="+slfNo+"&mensaje="+mensaje;
	}

	@RequestMapping("/dherst/importar/transferirEstudiantes")
	public String dherstImportarTransferirEstuadiantes(HttpServletRequest request, Model modelo){
		String folio 			= request.getParameter("folio")==null?"0":request.getParameter("folio");
		String mensaje 			= "";
		String codigoPersonal 	= "";
		Parametros parametros 	= parametrosDao.mapeaRegId("1");

		List<DherstAlumno> listaAlumnos = dherstAlumnoDao.getListaArchivo(folio, "");

		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			codigoPersonal 		= sesion.getAttribute("codigoPersonal").toString();		
		}

		int traspasos = 0;
		int errores = 0;

		String tipo = "PAU";
		if(parametros.getInstitucion().equals("Sonoma")){
			tipo = "SONOMA";
		}
		if(parametros.getInstitucion().equals("Fulton")){
			tipo = "FAC";
		}

		for(DherstAlumno alumno : listaAlumnos){
			AlumPersonal alumPersonal	 	= new AlumPersonal();
			Acceso acceso 					= new Acceso();

			//Obtiene siguiente matricula disponible para el alumno
			String siguienteMatricula = alumPersonalDao.maximoReg(tipo);

			alumPersonal.setCodigoPersonal(siguienteMatricula);			
			alumPersonal.setNombre(alumno.getNombre());
			alumPersonal.setApellidoPaterno(alumno.getApellido());
			alumPersonal.setApellidoMaterno("");
			alumPersonal.setNombreLegal(alumno.getNombre().trim().toUpperCase()+" "+alumno.getApellido().toUpperCase());
			// alumPersonal.setFNacimiento();
			alumPersonal.setSexo(alumno.getSexo());
			alumPersonal.setEstadoCivil(alumno.getEstadoCivil());
			alumPersonal.setReligionId(alumno.getReligionId());
			// if(alumPersonal.getReligionId().equals("1")){
			// 	alumPersonal.setBautizado();
			// }
			alumPersonal.setPaisId(parametros.getPaisId());
			alumPersonal.setEstadoId(alumno.getEstadoId());
			// alumPersonal.setCiudadId();
			alumPersonal.setNacionalidad(parametros.getPaisId());
			alumPersonal.setEmail(alumno.getEmail().trim());
			// if(catCulturalDao.existeReg()){
			// 	alumPersonal.setCulturalId();
			// 	if(catRegionDao.existeReg()){
			// 		alumPersonal.setRegionId();
			// 	}
			// }
			alumPersonal.setResPaisId(parametros.getPaisId());
			alumPersonal.setResEstadoId(alumno.getResEstadoId());
			// alumPersonal.setResCiudadId();
			alumPersonal.setCurp(alumno.getSlfNo());
			alumPersonal.setCotejado("N");
			alumPersonal.setEstado("A");
			alumPersonal.setFCreado(aca.util.Fecha.getHoy());
			alumPersonal.setUsAlta(codigoPersonal);

			String claveDigest		= aca.util.Encriptar.sha512ConBase64(request.getParameter("Matricula"));
			String claveHexa		= bCryptPasswordEncoder.encode(siguienteMatricula);

			acceso.setCodigoPersonal(siguienteMatricula);
			acceso.setAdministrador("N");
			acceso.setAccesos("X");
			acceso.setCotejador("N");
			acceso.setSupervisor("N");
			acceso.setPortalAlumno("N");
			acceso.setPortalMaestro("N");
			acceso.setModalidad("0");
			acceso.setUsuario(siguienteMatricula);
			acceso.setClave(claveDigest);
			acceso.setIdioma("en");
			acceso.setMenu("1");
			acceso.setClaveInicial(siguienteMatricula);
			acceso.setClaveHexa(claveHexa);
			acceso.setEnLinea("N");

			alumno.setCodigoPersonal(siguienteMatricula);
			alumno.setStatus("T");


			if(alumPersonalDao.existeReg(siguienteMatricula)==false && siguienteMatricula.length()==7){
				if(alumPersonalDao.insertReg(alumPersonal)){
					if(accesoDao.insertReg(acceso)){
						if(dherstAlumnoDao.updateReg(alumno)){
							traspasos++;
						}else{
							errores++;
						}
					}else{
						errores++;
					}
				}else{
					errores++;
				}
			}else{
				errores++;
			}
		}

		return "redirect:/dherst/importar/subirArchivo?folio="+folio+"&mensaje="+mensaje+"&errores="+errores+"&traspasos="+traspasos;
	}

}