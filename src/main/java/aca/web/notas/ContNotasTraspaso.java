package aca.web.notas;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.AlumAcademico;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanDao;
import aca.vista.spring.AlumnoCurso;
import aca.vista.spring.AlumnoCursoDao;
import aca.catalogo.spring.CatCarreraDao;
import aca.kardex.spring.KrdxCursoImp;
import aca.kardex.spring.KrdxCursoImpDao;
import aca.traspaso.spring.Traspaso;
import aca.traspaso.spring.TraspasoDao;
import aca.plan.spring.MapaCurso;
import aca.plan.spring.MapaCursoDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;

@Controller
public class ContNotasTraspaso {

    @Autowired
    private TraspasoDao traspasoDao;

    @Autowired	
	private AlumPersonalDao alumPersonalDao;

    @Autowired	
	private AlumPlanDao alumPlanDao;

    @Autowired	
	private AlumnoCursoDao alumnoCursoDao;

    @Autowired	
	private CatCarreraDao catCarreraDao ;

    @Autowired	
	private MapaCursoDao mapaCursoDao;

    @Autowired	
	private MapaPlanDao mapaPlanDao;

    @Autowired	
	private KrdxCursoImpDao krdxCursoImpDao;

    @Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;

    @RequestMapping("/notas/traspaso/traspaso")
    public String notasTraspasoTraspaso(HttpServletRequest request, Model modelo){

        String codigoAlumno		= "0";          

        HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
        	codigoAlumno 		= sesion.getAttribute("codigoAlumno")==null?"0":(String)sesion.getAttribute("codigoAlumno");
		}

        String planId           = request.getParameter("PlanId")==null?alumPersonalDao.getPlanActivo(codigoAlumno):request.getParameter("PlanId");


        String nombreAlumno		= alumPersonalDao.getNombre(codigoAlumno, "NOMBRE");
	    String carreraId	 	= alumPersonalDao.getCarreraId(planId);
		String nombreCarrera 	= catCarreraDao.getNombreCarrera(carreraId);

        AlumPersonal alumno =  alumPersonalDao.mapeaRegId(codigoAlumno);

        List<AlumPlan> lisPlanes 				= alumPlanDao.getLista(codigoAlumno, "ORDER BY PLAN_ID");	
        List<Traspaso> lisTraspasos             = traspasoDao.getListAlumno(codigoAlumno, " ORDER BY SEMESTER_NAME");
        List<MapaCurso> lisCursosEnTraspaso     = mapaCursoDao.getCursosEnTraspaso(codigoAlumno, planId,  " ORDER BY CURSO_CLAVE");

        HashMap<String, AlumnoCurso> mapaCursosAlumno           = alumnoCursoDao.mapaCursosAlumno(codigoAlumno, planId);
        HashMap<String,String>	mapaCicloPorCurso               = traspasoDao.mapaCicloPorCurso(codigoAlumno);
        HashMap<String,String>	mapaGradePorCurso               = traspasoDao.mapaGradePorCurso(codigoAlumno);
        HashMap<String, MapaPlan> mapaPlan			            = mapaPlanDao.mapPlanes("'V','A','I'");

        
        modelo.addAttribute("alumno", alumno);
        modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("nombreCarrera", nombreCarrera);
		modelo.addAttribute("carreraId", carreraId);

        modelo.addAttribute("lisPlanes", lisPlanes);
        modelo.addAttribute("lisTraspasos", lisTraspasos);
        modelo.addAttribute("lisCursosEnTraspaso", lisCursosEnTraspaso);
        
        modelo.addAttribute("mapaCursosAlumno", mapaCursosAlumno);
        modelo.addAttribute("mapaCicloPorCurso", mapaCicloPorCurso);
        modelo.addAttribute("mapaGradePorCurso", mapaGradePorCurso);
        modelo.addAttribute("mapaPlan", mapaPlan);

        return "notas/traspaso/traspaso";
    }

    @RequestMapping("/notas/traspaso/grabar")
    public String notasTraspasoGrabar(HttpServletRequest request, Model modelo){

        String planId	= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String codigoAlumno		= "0"; 
		String codigoPersonal 	= "0"; 

		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
        	codigoAlumno 		= sesion.getAttribute("codigoAlumno")==null?"0":(String)sesion.getAttribute("codigoAlumno");
        	codigoPersonal 		= sesion.getAttribute("codigoPersonal")==null?"0":(String)sesion.getAttribute("codigoPersonal");
		}

        List<MapaCurso> lisCursosEnTraspaso                     = mapaCursoDao.getCursosEnTraspaso(codigoAlumno, planId,  " ORDER BY CURSO_CLAVE");

        KrdxCursoImp krdxCursoImp 		= new KrdxCursoImp(); 

        int row = 0;
        for(int i= 0; i < lisCursosEnTraspaso.size();  i++){
            row++;
			String cursoId			= request.getParameter("cursoId"+row);
            String tipoCalId		= request.getParameter("tipoCalId"+row);
            String ciclo		    = request.getParameter("ciclo"+row);
            String grade            = request.getParameter("grade"+row);
            String check            = request.getParameter("check"+row);
            String fecha            = aca.util.Fecha.getHoy();

            if(check != null){
                String folio = krdxCursoImpDao.maximoReg(codigoAlumno);

                System.out.println("cursoId = "+cursoId+", tipoCalId= "+tipoCalId+", ciclo="+ciclo+", grade="+grade+", folio="+folio+", fecha="+fecha);

                krdxCursoImp.setCodigoPersonal(codigoAlumno);
                krdxCursoImp.setFolio(folio);
                krdxCursoImp.setFCreada(fecha);
                krdxCursoImp.setCursoId(cursoId);
                krdxCursoImp.setCursoId2(cursoId);
                krdxCursoImp.setTipoCalId(tipoCalId);
                krdxCursoImp.setNota(grade);
                krdxCursoImp.setCiclo(ciclo);
                krdxCursoImp.setUsuario(codigoPersonal);
                krdxCursoImp.setFecha(fecha);

                if(!krdxCursoImpDao.existeReg(codigoAlumno, folio)){
                    krdxCursoImpDao.insertReg(krdxCursoImp);
                }	
            }
        }


        return "redirect:/notas/traspaso/traspaso?PlanId="+planId;
    }

    @RequestMapping("/notas/traspaso/lista")
    public String notasTraspasoLista(HttpServletRequest request, Model modelo){
        String codigoAlumno = (String)request.getParameter("CodigoAlumno");

        HttpSession sesion		= ((HttpServletRequest)request).getSession();
        sesion.setAttribute("codigoAlumno", codigoAlumno);

        String planId           = alumPersonalDao.getPlanActivo(codigoAlumno);
        String nombreAlumno		= alumPersonalDao.getNombre(codigoAlumno, "NOMBRE");
	    String carreraId	 	= alumPersonalDao.getCarreraId(planId);
		String nombreCarrera 	= catCarreraDao.getNombreCarrera(carreraId);

        List<AlumPersonal> lisAlumnos = alumPersonalDao.getListAlumnosEnTraspaso(" ORDER BY APELLIDO_PATERNO");

        HashMap<String,String> mapaAlumPlan                     = alumPlanDao.mapaPlanesActivosTodos();
        HashMap<String,String> mapNumMateriasTraspaso           = traspasoDao.mapaNumTraspasoPorAlumno();
        HashMap<String,String> mapNumMateriasFaltantesTraspaso  = traspasoDao.mapaNumTraspasoFaltantesPorAlumno();

        modelo.addAttribute("codigoAlumno", codigoAlumno);
        modelo.addAttribute("planId", planId);
        modelo.addAttribute("nombreAlumno", nombreAlumno);
        modelo.addAttribute("nombreCarrera", nombreCarrera);
        modelo.addAttribute("carreraId", carreraId);

        modelo.addAttribute("lisAlumnos", lisAlumnos);

        modelo.addAttribute("mapaAlumPlan", mapaAlumPlan);
        modelo.addAttribute("mapNumMateriasTraspaso", mapNumMateriasTraspaso);
        modelo.addAttribute("mapNumMateriasFaltantesTraspaso", mapNumMateriasFaltantesTraspaso);

        return "notas/traspaso/lista";
    }
}
