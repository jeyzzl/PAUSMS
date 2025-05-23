package aca.web.dherst;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.admision.spring.AdmCartaFulton;
import aca.admision.spring.AdmCartaFultonDao;
import aca.admision.spring.AdmCartaPau;
import aca.admision.spring.AdmCartaPauDao;
import aca.admision.spring.AdmCartaSonoma;
import aca.admision.spring.AdmCartaSonomaDao;
import aca.admision.spring.AdmCartaSubir;
import aca.alumno.spring.AlumAcademico;
import aca.alumno.spring.AlumAcademicoDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatCiudadDao;
import aca.catalogo.spring.CatEstadoDao;
import aca.catalogo.spring.CatPaisDao;
import aca.dherst.spring.DherstAlumno;
import aca.dherst.spring.DherstAlumnoDao;
import aca.dherst.spring.DherstArchivo;
import aca.dherst.spring.DherstArchivoDao;
import aca.mensaje.mensaje;
import aca.parametros.spring.Parametros;
import aca.parametros.spring.ParametrosDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ContDherstCarta {

    @Autowired
	private DherstArchivoDao dherstArchivoDao;

	@Autowired
	private DherstAlumnoDao dherstAlumnoDao;

	@Autowired
	private AlumPersonalDao alumPersonalDao;

    @Autowired
	private AlumAcademicoDao alumAcademicoDao;

    @Autowired
	private ParametrosDao parametrosDao;   
    
    @Autowired
	private MapaPlanDao mapaPlanDao;

    @Autowired
	private CatCarreraDao catCarreraDao;

    @Autowired
    private AdmCartaPauDao admCartaPauDao;

    @Autowired
    private AdmCartaSonomaDao admCartaSonomaDao;

    @Autowired
    private AdmCartaFultonDao admCartaFultonDao;

	@Autowired
	private CatCiudadDao catCiudadDao;

	@Autowired
	private CatEstadoDao catEstadoDao;

	@Autowired
	private CatPaisDao catPaisDao;

    @RequestMapping("/dherst/carta/alumnos")
	public String dherstCartaAlumos(HttpServletRequest request, Model modelo) {
        String folio = request.getParameter("folio")==null?"0":request.getParameter("folio");

        List<DherstArchivo> listArchivos = dherstArchivoDao.getListAll(" ORDER BY FOLIO");

        DherstArchivo archivo = new DherstArchivo();
        if(folio.equals("0")){
			if(!listArchivos.isEmpty()){
            	archivo = listArchivos.get(0);
			}
        }else{
            archivo = dherstArchivoDao.mapeaRegId(folio);
        }

        // Filtra la lista por alumnos en estado T = Transferido o C = Carta
        List<DherstAlumno> listAlumnos = dherstAlumnoDao.getListaArchivo(archivo.getFolio(), " AND (STATUS='T' OR STATUS = 'C') ORDER BY NOMBRE, APELLIDO");

        modelo.addAttribute("listArchivos",listArchivos);
        modelo.addAttribute("listAlumnos",listAlumnos);
        modelo.addAttribute("archivo", archivo);

		return "dherst/carta/alumnos";
	}

    @RequestMapping("/dherst/carta/carta")
	public String dherstCartaSubirCarta(HttpServletRequest request, Model modelo){
		String folio 			= request.getParameter("folio")==null?"0":request.getParameter("folio");
		String codigoPersonal 	= request.getParameter("codigoPersonal")==null?"0":request.getParameter("codigoPersonal");
		String slfNo 			= request.getParameter("slfNo")==null?"0":request.getParameter("slfNo");

		DherstAlumno alumno = new DherstAlumno();
		if(dherstAlumnoDao.existeReg(slfNo)){
			alumno = dherstAlumnoDao.mapeaRegId(slfNo);
		}

		modelo.addAttribute("folio", folio);
		modelo.addAttribute("codigoPersonal", codigoPersonal);
		modelo.addAttribute("slfNo", slfNo);
		modelo.addAttribute("alumno", alumno);
		
		return "dherst/carta/carta";
	}

    @RequestMapping("/dherst/carta/generarCarta")
	public String dherstCartaGenerarCarta(HttpServletRequest request, Model modelo) {
        String folio            = request.getParameter("folio")==null?"0":request.getParameter("folio");
        String codigoPersonal   = request.getParameter("codigoPersonal")==null?"0":request.getParameter("codigoPersonal");
        String slfNo            = request.getParameter("slfNo")==null?"0":request.getParameter("slfNo");
        String url              = "";
		String mensaje 			= "";

        Parametros parametros 	= parametrosDao.mapeaRegId("1");

        AdmCartaPau admCartaPau 		= admCartaPauDao.existeReg("1")?admCartaPauDao.mapeaRegId("1"): new AdmCartaPau();
		AdmCartaSonoma admCartaSonoma 	= admCartaSonomaDao.existeReg("1")?admCartaSonomaDao.mapeaRegId("1"): new AdmCartaSonoma();
		AdmCartaFulton admCartaFulton 	= admCartaFultonDao.existeReg("1")?admCartaFultonDao.mapeaRegId("1"): new AdmCartaFulton();

        DherstAlumno alumno             = dherstAlumnoDao.mapeaRegId(slfNo);
        AlumPersonal alumPersonal       = alumPersonalDao.mapeaRegId(codigoPersonal); 
        AlumAcademico alumAcademico     = alumAcademicoDao.mapeaRegId(codigoPersonal);

        MapaPlan plan			        = mapaPlanDao.mapeaRegId(alumno.getPlanId());

        String fechaHoy		            = aca.util.Fecha.getHoy();

        String nombreAplicante          = alumPersonal.getNombre()+" "+alumPersonal.getApellidoPaterno()+" "+(alumPersonal.getApellidoMaterno()==null || alumPersonal.getApellidoMaterno().equals("-")?"":alumPersonal.getApellidoMaterno());
        String matricula                = alumPersonal.getCodigoPersonal();
        String carreraNombre 	        = catCarreraDao.getNombreCarrera(plan.getCarreraId());
		String nombrePlan 				= mapaPlanDao.getNombrePlan(plan.getPlanId());
        String acomodoNombre            = "";
        if(alumno.getResidencia().equals("DST")) acomodoNombre = "Day Student";
        if(alumno.getResidencia().equals("LD")) acomodoNombre = "Boarding Ladie's";
        if(alumno.getResidencia().equals("MD")) acomodoNombre = "Boarding Men's";

        // Decide que carta generar
		String carta = "";
		if(parametros.getInstitucion().equals("Pacific Adventist University")) carta = "cartaPauPDF";
		if(parametros.getInstitucion().equals("Sonoma")) carta = "cartaSonomaPDF";
		if(parametros.getInstitucion().equals("Fulton")) carta = "cartaFultonPDF";

        url = carta+"?Folio="+folio
			+ "&Nombre="+nombreAplicante
			+ "&Matricula="+matricula
			+ "&Curso="+carreraNombre
			+ "&Fecha="+fechaHoy
			+ "&Plan="+nombrePlan
			+ "&Acomodo="+acomodoNombre; 

		if(parametros.getInstitucion().equals("Pacific Adventist University")){ 		// PARAMETROS DE PAU
			String fechaRegistro 	= admCartaPau.getFechaRegistro();
			String fechaOrientacion = admCartaPau.getFechaOrientacion();
			String fechaApertura 	= admCartaPau.getFechaApertura();
			String fechaInicio 		= admCartaPau.getFechaInicio();
			String fechaCierre 		= admCartaPau.getFechaCierre();
			String fechaEscrita 	= aca.util.Fecha.getFechaOficialIngles(fechaHoy);
			String fechaCierreEscrita = aca.util.Fecha.getFechaOficialIngles(fechaCierre);
			String direccionPostal 	= catCiudadDao.getNombreCiudad(alumPersonal.getResPaisId(), alumPersonal.getResEstadoId(), alumPersonal.getResCiudadId())+", "+catEstadoDao.getNombreEstado(alumPersonal.getResPaisId(), alumPersonal.getResEstadoId())+", "+catPaisDao.getNombrePais(alumPersonal.getResPaisId());
			String numeroCiclos 	= String.valueOf((Integer.parseInt(plan.getCiclos()) / 2));

			url += "&fRegistro="+fechaRegistro
				 + "&fOrientacion="+fechaOrientacion
				 + "&fApertura="+fechaApertura
				 + "&fInicio="+fechaInicio
				 + "&fCierre="+fechaCierre
				 + "&Direccion="+direccionPostal
				 + "&fEscrita="+fechaEscrita
				 + "&fCierreEscrita="+fechaCierreEscrita
				 + "&nCiclos="+numeroCiclos;
		}
		if(parametros.getInstitucion().equals("Sonoma")){								// PARAMETROS DE SONOMA
			String numeroOferta 		= "";
			String numeroCiclos 		= String.valueOf(Integer.parseInt(plan.getCiclos())/2);
			String fechaFinal 			= admCartaSonoma.getFechaFinal();
			String fechaFinalEscrita 	= aca.util.Fecha.getFechaOficial(fechaFinal);


			url +="&fFinalEscrita="+fechaFinalEscrita
				+"&fFinal="+fechaFinal
				+"&nOferta="+numeroOferta
				+"&nCiclos="+numeroCiclos;
		}
		if(parametros.getInstitucion().equals("Fulton")){								// PARAMETROS DE FULTON
			String fechaRegistro		= admCartaFulton.getFechaRegistro();
			String fechaOrientacion 	= admCartaFulton.getFechaOrientacion();
			String fechaInicio 			= admCartaFulton.getFechaInicio();
			String fechaCierre 			= admCartaFulton.getFechaCierre();
			String fechaArribo 			= admCartaFulton.getFechaArribo();
			String numeroCiclos 		= String.valueOf(Integer.parseInt(plan.getCiclos())/2);
			String direccionInterior 	= "";

			url += "&fRegistro="+fechaRegistro
				+"&fOrientacion="+fechaOrientacion
				+"&fInicio="+fechaInicio
				+"&fCierre="+fechaCierre
				+"&fArribo="+fechaArribo
				+"&Direccion="+direccionInterior
				+"&nCiclos="+numeroCiclos;
		}
		
		// Actualiza el estado del solicitante a carta generada
		if(alumPersonalDao.existeAlumno(alumno.getCodigoPersonal())){
			alumno.setStatus("C");
			if(dherstAlumnoDao.updateReg(alumno)){
				mensaje = "1";
			}
		}

        modelo.addAttribute("alumno", alumno);

		return "redirect:/dherst/carta/"+url;
	}

	@RequestMapping("/dherst/carta/cartaPauPDF")
	public String admlineaAdmisionCartaPauPDF(HttpServletRequest request, Model modelo){
		String folio 			= request.getParameter("Folio") == null ? "0" : request.getParameter("Folio");
		String nombre 			= request.getParameter("Nombre") == null ? "0" : request.getParameter("Nombre");
		String matricula 		= request.getParameter("Matricula") == null ? "0" : request.getParameter("Matricula");
		String curso 			= request.getParameter("Curso") == null ? "0" : request.getParameter("Curso");
		String acomodo 			= request.getParameter("Acomodo") == null ? "0" : request.getParameter("Acomodo");
		String plan 			= request.getParameter("Plan") == null ? "0" : request.getParameter("Plan");
		String fecha 			= request.getParameter("Fecha") == null ? "0" : request.getParameter("Fecha");
		String fechaRegistro 	= request.getParameter("fRegistro") == null ? "0" : request.getParameter("fRegistro");
		String fechaOrientacion = request.getParameter("fOrientacion") == null ? "0" : request.getParameter("fOrientacion");
		String fechaApertura 	= request.getParameter("fApertura") == null ? "0" : request.getParameter("fApertura");
		String fechaInicio 		= request.getParameter("fInicio") == null ? "0" : request.getParameter("fInicio");
		String fechaCierre 		= request.getParameter("fCierre") == null ? "0" : request.getParameter("fCierre");
		String direccionPostal 	= request.getParameter("Direccion") == null ? "0" : request.getParameter("Direccion");
		String fechaEscrita 	= request.getParameter("fEscrita") == null ? "0" : request.getParameter("fEscrita");
		String fechaCierreEscrita 	= request.getParameter("fCierreEscrita") == null ? "0" : request.getParameter("fCierreEscrita");
		String ciclos 			= request.getParameter("nCiclos") == null ? "0" : request.getParameter("nCiclos");

		System.out.println(plan);

		modelo.addAttribute("folio",folio);
		modelo.addAttribute("nombre",nombre);
		modelo.addAttribute("matricula",matricula);
		modelo.addAttribute("curso",curso);
		modelo.addAttribute("acomodo", acomodo);
		modelo.addAttribute("plan", plan);
		modelo.addAttribute("fecha",fecha);
		modelo.addAttribute("fechaRegistro",fechaRegistro);
		modelo.addAttribute("fechaOrientacion",fechaOrientacion);
		modelo.addAttribute("fechaApertura",fechaApertura);
		modelo.addAttribute("fechaInicio",fechaInicio);
		modelo.addAttribute("fechaCierre",fechaCierre);
		modelo.addAttribute("direccionPostal",direccionPostal);
		modelo.addAttribute("fechaEscrita", fechaEscrita);
		modelo.addAttribute("fechaCierreEscrita", fechaCierreEscrita);
		modelo.addAttribute("ciclos", ciclos);
		
		return "dherst/carta/cartaPauPDF";
	}

	@RequestMapping("/dherst/carta/cartaSonomaPDF")
	public String admlineaAdmisionCartaSonomaPDF(HttpServletRequest request, Model modelo){
		String folio 			= request.getParameter("Folio") == null ? "0" : request.getParameter("Folio");
		String nombre 			= request.getParameter("Nombre") == null ? "0" : request.getParameter("Nombre");
		String matricula 		= request.getParameter("Matricula") == null ? "0" : request.getParameter("Matricula");
		String curso 			= request.getParameter("Curso") == null ? "0" : request.getParameter("Curso");
		String ciclo 			= request.getParameter("Ciclo") == null ? "0" : request.getParameter("Ciclo");
		String fecha 			= request.getParameter("Fecha") == null ? "0" : request.getParameter("Fecha");
		String numeroOferta 	= request.getParameter("nOferta") == null ? "0" : request.getParameter("nOferta");
		String numeroCiclos 	= request.getParameter("nCiclos") == null ? "0" : request.getParameter("nCiclos");
		String fechaFinal 		= request.getParameter("fFinal") == null ? "0" : request.getParameter("fFinal");
		String fechaFinalEscrita = request.getParameter("fFinalEscrita") == null ? "0" : request.getParameter("fFinalEscrita");
		
		modelo.addAttribute("folio",folio);
		modelo.addAttribute("nombre",nombre);
		modelo.addAttribute("matricula",matricula);
		modelo.addAttribute("curso", curso);
		modelo.addAttribute("ciclo",ciclo);
		modelo.addAttribute("fecha",fecha);
		modelo.addAttribute("numeroOferta",numeroOferta);
		modelo.addAttribute("numeroCiclos",numeroCiclos);
		modelo.addAttribute("fechaFinal",fechaFinal);
		modelo.addAttribute("fechaFinalEscrita", fechaFinalEscrita);
		
		return "dherst/carta/cartaSonomaPDF";
	}

	@RequestMapping("/dherst/carta/cartaFultonPDF")
	public String admlineaAdmisionCartaFultonPDF(HttpServletRequest request, Model modelo){
		String folio 			= request.getParameter("Folio") == null ? "0" : request.getParameter("Folio");
		String nombre 			= request.getParameter("Nombre") == null ? "0" : request.getParameter("Nombre");
		String matricula 		= request.getParameter("Matricula") == null ? "0" : request.getParameter("Matricula");
		String curso 			= request.getParameter("Curso") == null ? "0" : request.getParameter("Curso");
		String ciclo 			= request.getParameter("Ciclo") == null ? "0" : request.getParameter("Ciclo");
		String fecha 			= request.getParameter("Fecha") == null ? "0" : request.getParameter("Fecha");
		String fechaRegistro 	= request.getParameter("fRegistro") == null ? "0" : request.getParameter("fRegistro");
		String fechaOrientacion = request.getParameter("fOrientacion") == null ? "0" : request.getParameter("fOrientacion");
		String fechaInicio 		= request.getParameter("fInicio") == null ? "0" : request.getParameter("fInicio");
		String fechaCierre 		= request.getParameter("fCierre") == null ? "0" : request.getParameter("fCierre");
		String fechaArribo 		= request.getParameter("fArribo") == null ? "0" : request.getParameter("fArribo");
		String direccion 		= request.getParameter("Direccion") == null ? "0" : request.getParameter("Direccion");
		String numeroCiclos 	= request.getParameter("nCiclos") == null ? "0" : request.getParameter("nCiclos");

		modelo.addAttribute("folio",folio);
		modelo.addAttribute("nombre",nombre);
		modelo.addAttribute("matricula",matricula);
		modelo.addAttribute("curso", curso);
		modelo.addAttribute("ciclo",ciclo);
		modelo.addAttribute("fecha",fecha);
		modelo.addAttribute("fechaRegistro",fechaRegistro);
		modelo.addAttribute("fechaOrientacion",fechaOrientacion);
		modelo.addAttribute("fechaInicio",fechaInicio);
		modelo.addAttribute("fechaCierre",fechaCierre);
		modelo.addAttribute("fechaArribo",fechaArribo);
		modelo.addAttribute("direccion",direccion);
		modelo.addAttribute("numeroCiclos", numeroCiclos);
		
		return "dherst/carta/cartaFultonPDF";
	}
}