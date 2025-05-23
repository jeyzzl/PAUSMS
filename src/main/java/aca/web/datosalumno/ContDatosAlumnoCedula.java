package aca.web.datosalumno;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumAcademico;
import aca.alumno.spring.AlumAcademicoDao;
import aca.alumno.spring.AlumAsesorDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanDao;
import aca.alumno.spring.AlumUbicacion;
import aca.alumno.spring.AlumUbicacionDao;
import aca.archivo.spring.ArchDocAlum;
import aca.archivo.spring.ArchDocAlumDao;
import aca.archivo.spring.ArchDocStatus;
import aca.archivo.spring.ArchDocStatusDao;
import aca.archivo.spring.ArchDocumentosDao;
import aca.archivo.spring.ArchGrupo;
import aca.archivo.spring.ArchGrupoDao;
import aca.archivo.spring.ArchGrupoDocumento;
import aca.archivo.spring.ArchGrupoDocumentoDao;
import aca.archivo.spring.ArchGrupoPlanDao;
import aca.archivo.spring.ArchGrupos;
import aca.archivo.spring.ArchGruposCarreraDao;
import aca.archivo.spring.ArchGruposDao;
import aca.archivo.spring.ArchStatusDao;
import aca.archivo.spring.ArchUbicacionDao;
import aca.archivo.spring.ArchivoDao;
import aca.candado.spring.CandAlumno;
import aca.candado.spring.CandAlumnoDao;
import aca.candado.spring.CandTipo;
import aca.candado.spring.CandTipoDao;
import aca.candado.spring.Candado;
import aca.candado.spring.CandadoDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatEstadoDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatModalidadDao;
import aca.catalogo.spring.CatPaisDao;
import aca.catalogo.spring.CatReligion;
import aca.catalogo.spring.CatReligionDao;
import aca.catalogo.spring.CatTipoAlumnoDao;
import aca.catalogo.spring.CatTipoCurso;
import aca.catalogo.spring.CatTipoCursoDao;
import aca.emp.spring.EmpContacto;
import aca.emp.spring.EmpContactoDao;
import aca.financiero.spring.FinCostos;
import aca.financiero.spring.FinCostosDao;
import aca.financiero.spring.FinCreditos;
import aca.financiero.spring.FinCreditosDao;
import aca.financiero.spring.FinPermisoDao;
import aca.financiero.spring.FinSaldo;
import aca.leg.spring.LegDocumentoDao;
import aca.leg.spring.LegExtdoctos;
import aca.leg.spring.LegExtdoctosDao;
import aca.matricula.spring.MatAlumno;
import aca.matricula.spring.MatAlumnoDao;
import aca.matricula.spring.MatCostos;
import aca.matricula.spring.MatCostosDao;
import aca.matricula.spring.MatEvento;
import aca.matricula.spring.MatEventoDao;
import aca.matricula.spring.MatIngreso;
import aca.matricula.spring.MatIngresoDao;
import aca.mentores.spring.MentAlumnoDao;
import aca.plan.spring.MapaCurso;
import aca.plan.spring.MapaCursoDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.residencia.spring.ResDatosDao;
import aca.residencia.spring.ResRazonDao;
import aca.vista.spring.AlumnoCursoDao;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContDatosAlumnoCedula {
	
	@Autowired
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired
	private AlumAcademicoDao alumAcademicoDao;
	
	@Autowired
	AlumPlanDao alumPlanDao;

	@Autowired
	MapaPlanDao mapaPlanDao;
	
	@Autowired
	ResDatosDao resDatosDao;
	
	@Autowired
	ResRazonDao resRazonDao;
	
	@Autowired
	CatTipoAlumnoDao catTipoAlumnoDao;
	
	@Autowired
	MaestrosDao maestrosDao;
	
	@Autowired
	MentAlumnoDao mentAlumnoDao;
	
	@Autowired
	ArchivoDao archivoDao;
	
	@Autowired
	ArchGruposCarreraDao archGruposCarreraDao;
	
	@Autowired
	ArchDocumentosDao archDocumentosDao;
	
	@Autowired
	ArchStatusDao archStatusDao;
	
	@Autowired
	ArchGruposDao archGruposDao;
	
	@Autowired
	ArchDocAlumDao archDocAlumDao;
	
	@Autowired
	ArchUbicacionDao archUbicacionDao;
	
	@Autowired
	AlumAsesorDao alumAsesorDao;
	
	@Autowired
	EmpContactoDao empContactoDao;
	
	@Autowired
	CatPaisDao catPaisDao;
	
	@Autowired
	CatEstadoDao catEstadoDao;
	
	@Autowired
	LegExtdoctosDao legExtdoctosDao;
	
	@Autowired
	LegDocumentoDao legDocumentoDao;
	
	@Autowired
	CandTipoDao candTipoDao;
	
	@Autowired
	CandAlumnoDao candAlumnoDao;
	
	@Autowired
	CandadoDao candadoDao;
	
	@Autowired
	FinPermisoDao finPermisoDao;
	
	@Autowired
	CatReligionDao catReligionDao;
	
	@Autowired
	AlumnoCursoDao alumnoCursoDao;
	
	@Autowired
	MapaCursoDao mapaCursoDao;
	
	@Autowired
	AlumUbicacionDao alumUbicacionDao;
	
	@Autowired
	CatTipoCursoDao catTipoCursoDao;
	
	@Autowired
	FinCostosDao finCostosDao;
	
	@Autowired
	FinCreditosDao finCreditosDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	CargaDao cargaDao;
	
	@Autowired
	CatFacultadDao catFacultadDao;
	
	@Autowired
	MatAlumnoDao matAlumnoDao;
	
	@Autowired
	MatEventoDao matEventoDao;
	
	@Autowired
	MatIngresoDao matIngresoDao;
	
	@Autowired
	AccesoDao accesoDao;

	@Autowired
	private MatCostosDao matCostosDao;
	
	@Autowired
	private ArchGrupoPlanDao archGrupoPlanDao;
	
	@Autowired
	private ArchGrupoDao archGrupoDao;
	
	@Autowired
	private ArchGrupoDocumentoDao archGrupoDocumentoDao;
	
	@Autowired
	private ArchDocStatusDao archDocStatusDao;
	
	@Autowired
	CatModalidadDao catModalidadDao;
	
	
	@RequestMapping("/datos_alumno/cedula/alumno")
	public String datosAlumnoCedulaAlumno(HttpServletRequest request, Model modelo){
		
		String codigoAlumno 		= request.getParameter("Matricula")==null?"0":request.getParameter("Matricula");
		String eventoId	 			= request.getParameter("EventoId") == null ? "0" : request.getParameter("EventoId");
		String modo		 			= request.getParameter("Modo") == null ? "0" : request.getParameter("Modo");
		String accion		 			= request.getParameter("Accion") == null ? "0" : request.getParameter("Accion");
		
		String alumnoNombre 		= "-";
		HttpSession sesion			= ((HttpServletRequest)request).getSession();
        if (sesion!=null){       	
        	if (codigoAlumno.equals("0")){
        		codigoAlumno 			= (String) sesion.getAttribute("codigoAlumno");
        	}else {
        		sesion.setAttribute("codigoAlumno", codigoAlumno);
        	}
        	alumnoNombre 			= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
        }
        
        String grupoId 				= request.getParameter("GrupoId") == null ? "0" : request.getParameter("GrupoId");
        String planId 				= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
        String cargaId 				= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
        String estado 				= request.getParameter("Estado")==null?"-":request.getParameter("Estado");
        String tipoPlan				= "<span class='badge bg-dark'>Plan principal</span>";
        
        // Lista de planes (No mover de esta poscición) 
        List<AlumPlan> lisPlanes 					= alumPlanDao.lisPlanesAlumno(codigoAlumno," ORDER BY ENOC.ALUM_PLAN.F_INICIO DESC");
        boolean existePlan = false;
        for(AlumPlan plan : lisPlanes) {
        	if (plan.getPlanId().equals(planId)) existePlan = true; 
        }
        AlumPlan alumPlan			= alumPlanDao.mapeaRegId(codigoAlumno);
        if(planId.equals("0") || existePlan == false) {
        	planId = alumPlan.getPlanId();
        }else {
        	alumPlan				= alumPlanDao.mapeaRegId(codigoAlumno, planId);
        }
        
        MatCostos matCostos = new MatCostos();    	
    	if(matCostosDao.existeReg(eventoId, codigoAlumno)) {
    		matCostos = matCostosDao.mapeaRegId(eventoId, codigoAlumno);
    		if (cargaId.equals("0")) cargaId = matCostos.getCargaId();
    	}else {
    		matCostos.setMatricula(codigoAlumno);
    		matCostos.setEventoId(eventoId);
    	}
    	
        if (alumPlan.getEstado().equals("0")) tipoPlan = "<span class='badge bg-success'>Plan secundario</span>";
        
        AlumPersonal alumPersonal	= alumPersonalDao.mapeaRegId(codigoAlumno);
        AlumAcademico alumAcademico	= alumAcademicoDao.mapeaRegId(codigoAlumno);
        AlumUbicacion alumUbicacion = alumUbicacionDao.mapeaRegId(codigoAlumno);
        
        String paisNombre			= catPaisDao.getNombrePais(alumPersonal.getNacionalidad());
        String estadoNombre 		= catEstadoDao.getNombreEstado(alumPersonal.getPaisId(), alumPersonal.getEstadoId());
        
        String razonId 					= resDatosDao.getRazon(codigoAlumno);
        String razonNombre				= resRazonDao.nombreRazon(razonId);
        String tipoNombre				= catTipoAlumnoDao.getNombreTipo(alumAcademico.getTipoAlumno());
        
        String asesorId					= alumAsesorDao.getAsesor(codigoAlumno, planId);
		String asesorNombre				= "No asignado";
		if (!asesorId.equals("0"))	asesorNombre = maestrosDao.getNombreMaestro(asesorId, "Nombre");
		
		EmpContacto empContacto			= empContactoDao.mapeaRegId(asesorId); 
		
    	boolean esExtranjero 			= alumPersonalDao.esExtranjero(codigoAlumno);
    	String fechaVencimiento			= alumPersonalDao.getVencimientoFM3(alumPersonal.getCodigoPersonal());
		String autorizado 				= archivoDao.autorizaAlumno(codigoAlumno, planId);
		boolean finPermiso				= finPermisoDao.tienePermiso(codigoAlumno);
		
		// Consulta el saldo del estudiante
		FinSaldo finSaldo 	= new FinSaldo();
		try {		
			RestTemplate restTemplate = new RestTemplate();			
			finSaldo = restTemplate.getForObject("http://172.16.251.110:8080/infor/reportes/api/fe/saldo/"+codigoAlumno, FinSaldo.class);
		}catch(Exception ex) {
			System.out.println("Error en saldo:"+codigoAlumno);
		}
		
		List<CandTipo> lisTipos 					= candTipoDao.getListAll(" ORDER BY TIPO_ID");  
        List<CandAlumno> lisCandados 				= candAlumnoDao.getLista(codigoAlumno, "A", " ORDER BY FOLIO");
        
        HashMap<String,Candado> mapaCandados		= candadoDao.getMapaCandados();
				
		String carreraId 				= mapaPlanDao.getCarreraId(alumPlan.getPlanId());
		String gruposCarrera 			= archGruposCarreraDao.getGruposCarrera(carreraId);
		List<ArchGrupos> lisGrupos		= archGruposDao.getListGrupo(grupoId, "");
		List<ArchDocAlum> lisDocumentos = archDocAlumDao.getListOne(codigoAlumno," ORDER BY ORDEN_DOCUMENTO(IDDOCUMENTO)");	
		//System.out.println("Datos:"+alumPlan.getPlanId()+":"+alumPlan.getCiclo()+":"+alumPlan.getCicloSep());
		List<MapaCurso> lisCursosRezagados			= mapaCursoDao.getListaMateriasRezagadas(planId,codigoAlumno,"'1','I','5'",alumPlan.getCicloSep()," ORDER BY CICLO, NOMBRE_CURSO");
		
        List<LegExtdoctos> lisExtDocumentos			= legExtdoctosDao.getLista(codigoAlumno, " ORDER BY IDDOCUMENTO");
        
        List<FinCostos> listFinCostos 				=	finCostosDao.lisFinCostosActivos("ORDER BY CARGA_ID");
		
		HashMap<String, String> mapaDocumentos 		= archDocumentosDao.mapaTodos();
		HashMap<String, String> mapaStatus 			= archStatusDao.mapaStatus();
		HashMap<String, String> mapaUbicacion 		= archUbicacionDao.mapaUbicacion();
		HashMap<String, String> mapaTotGrupo 		= archGruposDao.mapaTotPorGrupo();
		HashMap<String, String> mapaTotAlumno 		= archGruposDao.mapaGruposAlumno(codigoAlumno);
        HashMap<String,MapaPlan> mapaPlanes 		= mapaPlanDao.mapPlanes("'V','I','A'");
        HashMap<String,String> mapaLegDocumentos	= legDocumentoDao.mapaDocumentos();
        HashMap<String,CatReligion> mapaReligiones 	= catReligionDao.getMapAll("");
        HashMap<String,CatTipoCurso> mapaTipoCurso	= catTipoCursoDao.getMapAll("");
        HashMap<String,FinCreditos> mapaFinCreditos	= finCreditosDao.mapaFinCreditos();
        HashMap<String, Carga> mapaCargas	 		= cargaDao.mapaCargas();
        
        FinCostos finCostos 	= new FinCostos();
        FinCreditos finCreditos = new FinCreditos();
        CatCarrera catCarrera	= new CatCarrera();
        
        catCarrera = catCarreraDao.mapeaRegId(carreraId);        
        if(cargaId.equals("0")) {
        	cargaId = listFinCostos.get(0).getCargaId();        	
        	if(finCostosDao.existeReg(cargaId)) {
        		finCostos = finCostosDao.mapeaRegId(cargaId);
        	}        	
        	if(finCreditosDao.existeReg(cargaId, planId)){
        		finCreditos = finCreditosDao.mapeaRegId(cargaId, planId);        		
        	}else if(finCreditosDao.existeReg(cargaId, catCarrera.getCcostoId())) {
        		finCreditos = finCreditosDao.mapeaRegId(cargaId, catCarrera.getCcostoId());
        	}
        }else {        	
        	if(finCostosDao.existeReg(cargaId)) {
        		finCostos = finCostosDao.mapeaRegId(cargaId);
        	}        	
        	if(finCreditosDao.existeReg(cargaId, planId)){        		
        		finCreditos = finCreditosDao.mapeaRegId(cargaId, planId);
        	}else if(finCreditosDao.existeReg(cargaId, catCarrera.getCcostoId())) {
        		finCreditos = finCreditosDao.mapeaRegId(cargaId, catCarrera.getCcostoId());
        	}
        }
        
    	MatIngreso matIngreso = new MatIngreso();
    	
    	if(matIngresoDao.existeReg(eventoId, codigoAlumno)) {
    		matIngreso = matIngresoDao.mapeaRegId(eventoId, codigoAlumno);
    	}else {
    		matIngreso.setMatricula(codigoAlumno);
    		matIngreso.setEventoId(eventoId);
    	}	
    	
    	
    	if(accion.equals("1")) {
    		matAlumnoDao.updateEstado(eventoId, codigoAlumno, estado, planId);
    		matAlumnoDao.updateModo(eventoId, codigoAlumno, modo, planId);
    	}

    	MatAlumno matAlumno = new MatAlumno();
    	
    	if(matAlumnoDao.existeReg(eventoId, codigoAlumno, planId)) {
    		matAlumno = matAlumnoDao.mapeaRegId(eventoId, codigoAlumno, planId);
    	}
    	
    	List<String> gruposDelPlan 						= archGrupoPlanDao.gruposDelPlan(planId);
    	
    	HashMap<String, ArchGrupo> mapaGrupos 			= archGrupoDao.mapaArchGrupo();
    	
    	HashMap<String,Boolean> mapaDocCompletos 	= new HashMap<String,Boolean>();
    	HashMap<String, ArchDocStatus> mapaValidos 	= archDocStatusDao.mapaEstadosValidos();	
		
		for(String grupo : gruposDelPlan) {
			
			List<ArchGrupoDocumento> lisDoc		= archGrupoDocumentoDao.lisPorGrupo(grupo, "");
			int cont = 0;
			for (ArchGrupoDocumento doc : lisDoc) {
				 if (archDocAlumDao.existeReg(codigoAlumno, doc.getDocumentoId())){
					 ArchDocAlum docAlumno = archDocAlumDao.mapeaRegId(codigoAlumno, doc.getDocumentoId());
					 if (mapaValidos.containsKey(docAlumno.getIdDocumento()+"-"+docAlumno.getIdStatus())){
						 cont++;
					 }
				 }
			}
			if (lisDoc.size()==cont) mapaDocCompletos.put(grupo, true); else mapaDocCompletos.put(grupo, false);

		}
        
        modelo.addAttribute("alumnoNombre", alumnoNombre);
        modelo.addAttribute("planId", planId);
        modelo.addAttribute("cargaId", cargaId);
        modelo.addAttribute("alumPersonal", alumPersonal);
        modelo.addAttribute("alumUbicacion", alumUbicacion);
        modelo.addAttribute("alumPlan", alumPlan);
        modelo.addAttribute("alumAcademico", alumAcademico);
        modelo.addAttribute("empContacto", empContacto);
        modelo.addAttribute("finSaldo", finSaldo);
        modelo.addAttribute("razonNombre", razonNombre);
        modelo.addAttribute("paisNombre", paisNombre);
        modelo.addAttribute("estadoNombre", estadoNombre);
        modelo.addAttribute("tipoNombre",tipoNombre);
        modelo.addAttribute("asesorNombre",asesorNombre);
        modelo.addAttribute("autorizado", autorizado);
        modelo.addAttribute("esExtranjero", esExtranjero);
        modelo.addAttribute("fechaVencimiento", fechaVencimiento);
        modelo.addAttribute("gruposCarrera", gruposCarrera);
        modelo.addAttribute("finPermiso", finPermiso);
        modelo.addAttribute("tipoPlan", tipoPlan);
        modelo.addAttribute("finCostos", finCostos);
        modelo.addAttribute("finCreditos", finCreditos);
        modelo.addAttribute("matIngreso", matIngreso);
        modelo.addAttribute("matCostos", matCostos);
        modelo.addAttribute("matAlumno", matAlumno);
        
        modelo.addAttribute("lisPlanes", lisPlanes);
        modelo.addAttribute("lisGrupos", lisGrupos);
        modelo.addAttribute("lisDocumentos", lisDocumentos);
        modelo.addAttribute("lisExtDocumentos", lisExtDocumentos);
        modelo.addAttribute("lisTipos", lisTipos);
        modelo.addAttribute("lisCandados", lisCandados);
        modelo.addAttribute("lisCursosRezagados", lisCursosRezagados);
        modelo.addAttribute("listFinCostos", listFinCostos);
        modelo.addAttribute("gruposDelPlan", gruposDelPlan);
        
        modelo.addAttribute("mapaPlanes", mapaPlanes);
        modelo.addAttribute("mapaTotGrupo", mapaTotGrupo);
		modelo.addAttribute("mapaTotAlumno", mapaTotAlumno);
		modelo.addAttribute("mapaDocumentos", mapaDocumentos);
		modelo.addAttribute("mapaUbicacion", mapaUbicacion);
		modelo.addAttribute("mapaStatus", mapaStatus);
		modelo.addAttribute("mapaLegDocumentos", mapaLegDocumentos);
		modelo.addAttribute("mapaCandados", mapaCandados);
		modelo.addAttribute("mapaReligiones", mapaReligiones);
		modelo.addAttribute("mapaTipoCurso", mapaTipoCurso);
		modelo.addAttribute("mapaFinCreditos", mapaFinCreditos);
		modelo.addAttribute("mapaCargas", mapaCargas);
		modelo.addAttribute("mapaGrupos", mapaGrupos);
		modelo.addAttribute("mapaDocCompletos", mapaDocCompletos);
		
		return "datos_alumno/cedula/alumno";
	}
	
	@RequestMapping("/datos_alumno/cedula/grabaCreditos")
	public String datosAlumnoCedulaGrabarMatCreditos(HttpServletRequest request, Model modelo){
		String matricula 		= request.getParameter("Matricula") == null ? "0" : request.getParameter("Matricula");
		String eventoId	 		= request.getParameter("EventoId") == null ? "0" : request.getParameter("EventoId");
		String creditos	 		= request.getParameter("Creditos") == null ? "0" : request.getParameter("Creditos");
		String planId	 		= request.getParameter("PlanId") == null ? "0" : request.getParameter("PlanId");
		String cargaId	 		= request.getParameter("CargaId") == null ? "0" : request.getParameter("CargaId");
		
		MatCostos objeto = new MatCostos();
		
		objeto.setMatricula(matricula);
		objeto.setEventoId(eventoId);
		objeto.setCreditos(creditos);
		objeto.setCargaId(cargaId);
		
		if(matCostosDao.existeReg(eventoId, matricula)) {
			matCostosDao.updateReg(objeto);
		}else {
			matCostosDao.insertReg(objeto);
		}
		
		return "redirect:/datos_alumno/cedula/alumno?Matricula="+matricula+"&EventoId="+eventoId+"&PlanId="+planId+"&CargaId="+cargaId;
	}
	
	@RequestMapping("/datos_alumno/cedula/grabarMatIngreso")
	public String datosAlumnoCedulaGrabarMatIngreso(HttpServletRequest request, Model modelo){
		String matricula 		= request.getParameter("Matricula") == null ? "0" : request.getParameter("Matricula");
		String eventoId	 		= request.getParameter("EventoId") == null ? "0" : request.getParameter("EventoId");
		String propios	 		= request.getParameter("Propios") == null ? "0" : request.getParameter("Propios");
		String becas	 		= request.getParameter("Becas") == null ? "0" : request.getParameter("Becas");
		String colportaje 		= request.getParameter("Colportaje") == null ? "0" : request.getParameter("Colportaje");
		String otro 	 		= request.getParameter("Otro") == null ? "0" : request.getParameter("Otro");
		
		MatIngreso objeto = new MatIngreso();
		
		objeto.setMatricula(matricula);
		objeto.setEventoId(eventoId);
		objeto.setPropios(propios);
		objeto.setBecario(becas);
		objeto.setColportaje(colportaje);
		objeto.setOtro(otro);
		
		if(matIngresoDao.existeReg(eventoId, matricula)) {
			matIngresoDao.updateReg(objeto);
		}else {
			matIngresoDao.insertReg(objeto);
		}
		
		return "redirect:/datos_alumno/cedula/alumno?Matricula="+matricula+"&EventoId="+eventoId;
	}

	@RequestMapping("/datos_alumno/cedula/listado")
	public String datosAlumnoCedulaListado(HttpServletRequest request, Model modelo){
		
		String codigoPersonal 	= "0";
		String codigoAlumno 	= "0";
		String nombreAlumno		= "-";
		String modalidadAlumno	= "0";
		String modalidadNombre 	= "-";
		String esEnLinea		= "N";
		String mensaje	 		= request.getParameter("Mensaje") == null ? "0" : request.getParameter("Mensaje");
		String eventoId	 		= request.getParameter("EventoId") == null ? "0" : request.getParameter("EventoId");
		Acceso acceso 			= new Acceso();
		
		boolean muestraAgregar	= false;
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoPersonal 	   	= (String) sesion.getAttribute("codigoPersonal");
        	codigoAlumno 	   	= (String) sesion.getAttribute("codigoAlumno") == null ? "0" : (String) sesion.getAttribute("codigoAlumno");
        	nombreAlumno 		= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
        	modalidadAlumno 	= alumAcademicoDao.getModalidadId(codigoAlumno);
			modalidadNombre 	= catModalidadDao.getNombreModalidad(modalidadAlumno);
			esEnLinea			= catModalidadDao.getEnLinea(modalidadAlumno);
        	acceso 				= accesoDao.mapeaRegId(codigoPersonal);
        }      
        
        List<MatEvento> lisMatEvento = matEventoDao.lisMatEvento("A"," ORDER BY EVENTO_ID DESC");

        MatEvento evento = new MatEvento();
        if(eventoId.equals("0")) {
        	eventoId = lisMatEvento.get(0).getEventoId();
        }
        
        if(matEventoDao.existeReg(eventoId)) {
        	evento = matEventoDao.mapeaRegId(eventoId);
        }
		
		HashMap<String, String> mapaAlumnos 		= alumPersonalDao.mapaAlumnosMatricula(eventoId);
		HashMap<String, CatFacultad> mapaFacultades = catFacultadDao.getMapFacultad("ORDER BY NOMBRE_FACULTAD");
		HashMap<String, CatCarrera> mapaCarreras 	= catCarreraDao.getMapAll("");		
		HashMap<String,MapaPlan> mapaPlanes 		= mapaPlanDao.mapPlanes("'V','I','A'");
		HashMap<String, MatAlumno> mapaMatAlumno	= matAlumnoDao.mapaMatAlumno(eventoId);
		
		List<MatAlumno> lisMatAlumno = matAlumnoDao.lisPorEvento(evento.getEventoId(),"ORDER BY FACULTAD(CARRERA(PLAN_ID))");
		List<AlumPlan> lisPlanes 	 = alumPlanDao.lisPlanesAlumno(codigoAlumno," ORDER BY ENOC.ALUM_PLAN.F_INICIO DESC");
		
		if(!codigoAlumno.equals("0") && !codigoAlumno.equals(codigoPersonal)) {
			muestraAgregar	= true;
		}
		
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("modalidadNombre", modalidadNombre);
		modelo.addAttribute("esEnLinea", esEnLinea);
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("lisMatAlumno", lisMatAlumno);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);		
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		modelo.addAttribute("mapaMatAlumno", mapaMatAlumno);
		modelo.addAttribute("muestraAgregar", muestraAgregar);
		modelo.addAttribute("codigoAlumno", codigoAlumno);
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("lisMatEvento", lisMatEvento);
		modelo.addAttribute("evento", evento);
		modelo.addAttribute("mensaje", mensaje);
		
		return "datos_alumno/cedula/listado";
	}

	@RequestMapping("/datos_alumno/cedula/agregar")
	public String datosAlumnoCedulaAgregar(HttpServletRequest request, Model modelo){
		
		String codigoPersonal 	= "0";
		String codigoAlumno 	= "0";
		String planId	 		= request.getParameter("PlanId") == null ? "0" : request.getParameter("PlanId");
		String eventoId	 		= request.getParameter("EventoId") == null ? "0" : request.getParameter("EventoId");
		String modo	 			= request.getParameter("Modo") == null ? "0" : request.getParameter("Modo");
		String estado 			= request.getParameter("Estado") == null ? "P" : request.getParameter("Estado");
		String mensaje		 	= "0";
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal 	   	= (String) sesion.getAttribute("codigoPersonal")== null ?"0":(String)sesion.getAttribute("codigoPersonal");
			codigoAlumno 	   	= (String) sesion.getAttribute("codigoAlumno") == null ?"0":(String)sesion.getAttribute("codigoAlumno");
		}
		
		MatAlumno alumno = new MatAlumno();
		
		if(!matAlumnoDao.existeReg(eventoId, codigoAlumno, planId)){
			alumno.setEventoId(eventoId);
			alumno.setCodigoPersonal(codigoAlumno);
			alumno.setPlanId(planId);
			alumno.setFecha(aca.util.Fecha.getHoy());
			alumno.setUsuario(codigoPersonal);
			alumno.setModo(modo);
			alumno.setEstado(estado);
			if(matAlumnoDao.insertReg(alumno)){
				mensaje = "1";
			}
		}else {
			alumno 	= matAlumnoDao.mapeaRegId(eventoId, codigoAlumno, planId);
			alumno.setFecha(aca.util.Fecha.getHoy());
			alumno.setUsuario(codigoPersonal);
			alumno.setModo(modo);
			if(matAlumnoDao.updateReg(alumno)){
				mensaje = "1";
			}			
		}
		
		return "redirect:/datos_alumno/cedula/listado?EventoId="+eventoId+"&Mensaje="+mensaje;
	}

	@RequestMapping("/datos_alumno/cedula/eliminar")
	public String datosAlumnoCedulaEliminar(HttpServletRequest request, Model modelo){
		
		String codigoAlumno 	= request.getParameter("Codigo") == null ? "0" : request.getParameter("Codigo");
		String planId	 		= request.getParameter("PlanId") == null ? "0" : request.getParameter("PlanId");
		String eventoId	 		= request.getParameter("EventoId") == null ? "0" : request.getParameter("EventoId");	
		
		if(matAlumnoDao.existeReg(eventoId, codigoAlumno, planId)) {
			matAlumnoDao.deleteReg(eventoId,codigoAlumno, planId);
		}
		
		return "redirect:/datos_alumno/cedula/listado?EventoId="+eventoId;
	}
	
}