package aca.web.hca;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;
import aca.carga.spring.CargaGrupoDao;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatPeriodoDao;
import aca.hca.spring.HcaActividad;
import aca.hca.spring.HcaActividadDao;
import aca.hca.spring.HcaMaestro;
import aca.hca.spring.HcaMaestroActividad;
import aca.hca.spring.HcaMaestroActividadDao;
import aca.hca.spring.HcaMaestroDao;
import aca.hca.spring.HcaMaestroEstadoDao;
import aca.hca.spring.HcaRangoDao;
import aca.plan.spring.MapaCursoDao;
import aca.vista.spring.CargaAcademica;
import aca.vista.spring.CargaAcademicaDao;
import aca.vista.spring.Maestros;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContHcaAnalisis {	
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	private CatPeriodoDao catPeriodoDao;
	
	@Autowired
	private CatFacultadDao catFacultadDao;
	
	@Autowired
	private CargaDao cargaDao;
	
	@Autowired
	private CargaGrupoDao cargaGrupoDao;
	
	@Autowired
	private HcaMaestroDao hcaMaestroDao;
	
	@Autowired
	private MaestrosDao maestrosDao;
	
	@Autowired
	private CargaAcademicaDao cargaAcademicaDao;
	
	@Autowired
	private HcaMaestroActividadDao hcaMaestroActividadDao;
	
	@Autowired
	private HcaMaestroEstadoDao hcaMaestroEstadoDao;
	
	@Autowired
	private HcaRangoDao hcaRangoDao;
	
	@Autowired
	private CatCarreraDao catCarreraDao;
	
	@Autowired
	private MapaCursoDao mapaCursoDao;
	
	@Autowired
	private HcaActividadDao hcaActividadDao;
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}	
	
	@RequestMapping("/hca/analisis/carga")
	public String hcaAnalisisCarga(HttpServletRequest request, Model modelo){
		
		String periodo			= catPeriodoDao.getPeriodo();	
		int numPeriodo 			= Integer.parseInt(periodo.substring(0,2))-1;
		String periodoAnt		= String.valueOf(numPeriodo);
		if (periodoAnt.length()==1){
			periodoAnt = "0"+periodoAnt+periodo.substring(0,2);
		}else{
			periodoAnt = periodoAnt+periodo.substring(0,2);
		}	
		
		String opcion			= request.getParameter("opcion");
		
		if(opcion == null) {
			opcion = "1";
		}
		
		String cargaId 			= request.getParameter("alumnos");
		
		List<CatFacultad> lisFacultades	= catFacultadDao.getListAll("ORDER BY 1");
		List<Carga>lisCarga 			= cargaDao.getListCargaPeriodo(periodo,"ORDER BY 1");
		List<Carga>lisCargas 			= cargaDao.getListMaestroPorCargas(cargaId);
		List<Carga>lisCargaPas 			= cargaDao.getListCargaPeriodo(periodoAnt,"ORDER BY 1");
		
		List<CargaAcademica> lisCursos		= new ArrayList<CargaAcademica>();
		List<HcaMaestroActividad> lisMA		= new ArrayList<HcaMaestroActividad>();
		
		HashMap<String,List<HcaMaestro>> mapaLisMaestros 	= new HashMap<String,List<HcaMaestro>>(); 
		HashMap<String,List<CargaAcademica>> mapaLisCursos 	= new HashMap<String,List<CargaAcademica>>(); 
		HashMap<String,List<HcaMaestroActividad>> mapaLisMA = new HashMap<String,List<HcaMaestroActividad>>(); 
		HashMap<String,Boolean> mapaTieneCarga 				= new HashMap<String,Boolean>(); 
		HashMap<String,Integer> mapaEstadoCarga				= new HashMap<String,Integer>(); 
		HashMap<String,Float> mapaValor						= new HashMap<String,Float>(); 
		HashMap<String,Integer> mapaFs						= new HashMap<String,Integer>(); 
		HashMap<String,String> mapaSemanas					= new HashMap<String,String>(); 
		HashMap<String,HcaActividad> mapaHcaActividad		= new HashMap<String,HcaActividad>(); 
		
		for(CatFacultad facu : lisFacultades){ //Ciclo que trae las facultades
			List<HcaMaestro> lisMaestros = hcaMaestroDao.lisPorFacultad(facu.getFacultadId(), "ORDER BY 1");
			mapaLisMaestros.put(facu.getFacultadId(), lisMaestros);
			
			for(HcaMaestro master : lisMaestros){ //Ciclo que trae los maestros por facultad

				for(Carga car : lisCargas){ //Ciclo que trae las cargas x maestro
					
					String sem 		= cargaDao.getSemanas(car.getCargaId());
					mapaSemanas.put(car.getCargaId(), sem);
					
					lisCursos = cargaAcademicaDao.getListaMaestro(car.getCargaId(), master.getCodigoPersonal(), "ORDER BY NOMBRE_CURSO");
					lisMA = hcaMaestroActividadDao.getListMaestroCarga(master.getCodigoPersonal(), car.getCargaId(), "ORDER BY ACTIVIDAD_ID"); //Llenar listor que trae las actividades x carga
				
					mapaLisCursos.put(car.getCargaId()+master.getCodigoPersonal(), lisCursos);
					mapaLisMA.put(car.getCargaId()+master.getCodigoPersonal(), lisMA);
				
					boolean tieneCarga = cargaGrupoDao.getTieneCargaDocente(master.getCodigoPersonal(), car.getCargaId() );
					mapaTieneCarga.put(master.getCodigoPersonal()+car.getCargaId(), tieneCarga);
					int estadoCarga = hcaMaestroEstadoDao.getEstadoCargaDocente(master.getCodigoPersonal(),car.getCargaId());
					mapaEstadoCarga.put(master.getCodigoPersonal()+car.getCargaId(), estadoCarga);
					
					for(CargaAcademica cargaAca : lisCursos){ //Ciclo que trae las cargas x maestro
						float valor = Float.parseFloat(hcaRangoDao.getValor(catCarreraDao.getCarreraNivel(cargaAca.getCarreraId()), cargaAca.getModalidadId(), cargaAca.getNumAlum()));
						mapaValor.put(cargaAca.getCarreraId()+cargaAca.getModalidadId()+cargaAca.getNumAlum(), valor);
						int fs 	= Integer.parseInt(mapaCursoDao.getFS(cargaAca.getCursoId()));
						mapaFs.put(cargaAca.getCursoId(), fs);
					}
					
					for(HcaMaestroActividad hcaMaAct : lisMA){ //Ciclo que trae las cargas x maestro
						HcaActividad hcaActividad = new HcaActividad();
						
						if(hcaActividadDao.existeReg(hcaMaAct.getActividadId())) {
							hcaActividad = hcaActividadDao.mapeaRegId(hcaMaAct.getActividadId());
							mapaHcaActividad.put(hcaMaAct.getActividadId(), hcaActividad);
						}
					}
				}
			}
		}
			
		HashMap<String, Maestros> mapaMaestros = maestrosDao.mapaMaestros();
		
		modelo.addAttribute("periodo", periodo);
		modelo.addAttribute("lisFacultades", lisFacultades);
		modelo.addAttribute("lisCarga", lisCarga);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisCargaPas", lisCargaPas);
		modelo.addAttribute("mapaLisMaestros", mapaLisMaestros);
		modelo.addAttribute("mapaMaestros", mapaMaestros);
		modelo.addAttribute("mapaLisCursos", mapaLisCursos);
		modelo.addAttribute("mapaLisMA", mapaLisMA);
		modelo.addAttribute("mapaTieneCarga", mapaTieneCarga);
		modelo.addAttribute("mapaEstadoCarga", mapaEstadoCarga);
		modelo.addAttribute("mapaValor", mapaValor);
		modelo.addAttribute("mapaFs", mapaFs);
		modelo.addAttribute("mapaSemanas", mapaSemanas);
		modelo.addAttribute("mapaHcaActividad", mapaHcaActividad);
		
		return "hca/analisis/carga";
	}
}