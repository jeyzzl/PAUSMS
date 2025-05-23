package aca.web.patrocinador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.trabajo.spring.TrabDepartamento;
import aca.catalogo.spring.CatAcomodo;
import aca.catalogo.spring.CatAcomodoDao;
import aca.catalogo.spring.CatPatrocinador;
import aca.catalogo.spring.CatPatrocinadorDao;
import aca.plan.spring.MapaPlanDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumAcademico;
import aca.alumno.spring.AlumAcademicoDao;
import aca.alumno.spring.AlumPatrocinador;
import aca.alumno.spring.AlumPatrocinadorDao;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;

@Controller
public class ContPatrocinadorReportes {
    @Autowired
	AlumPersonalDao alumPersonalDao;

	@Autowired
	AlumAcademicoDao alumAcademicoDao;

	@Autowired
	AlumPlanDao alumPlanDao;
	
	@Autowired
	CatPatrocinadorDao catPatrocinadorDao;
	
	@Autowired
	AlumPatrocinadorDao alumPatrocinadorDao;
	
	@Autowired
	CargaDao cargaDao;

	@Autowired
	CatAcomodoDao catAcomodoDao;

    @Autowired
    MapaPlanDao mapaPlanDao;

    @RequestMapping("/patrocinador/reportes/menu")
	public String trabajoReportesMenu(HttpServletRequest request){		
		return "patrocinador/reportes/menu";
	}

    @RequestMapping("/patrocinador/reportes/patrocinador")
	public String patrocinadorReportesPatrocinador(HttpServletRequest request, Model model) {
		String cargaId		    = request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String patrocinadorId	= request.getParameter("PatrocinadorId")==null?"0":request.getParameter("PatrocinadorId");
        String grado            = request.getParameter("Grado")==null?"0":request.getParameter("Grado");
        String acomodo          =  request.getParameter("Acomodo")==null?"0":request.getParameter("Acomodo");
		
		List<Carga> lisCargas                           = cargaDao.getListAll("ORDER BY CARGA_ID DESC");
		List<CatPatrocinador> lisPatrocinadores		 	= catPatrocinadorDao.lisTodos("ORDER BY PATROCINADOR_ID");
        List<CatAcomodo> lisAcomodos                    = catAcomodoDao.getListAll(" ORDER BY TIPO, ACOMODO_ID");

        String filterQuery = "";
        List<AlumPatrocinador> lisAlumPatrocinadores 	= null;

        if(!grado.equals("0")){
            filterQuery = " AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ALUM_ACADEMICO WHERE GRADO = '"+grado+"')";
        }
        if(!acomodo.equals("0")){
            filterQuery += " AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ALUM_ACADEMICO WHERE ACOMODO_ID = "+acomodo+")";
        }

        if(patrocinadorId.equals("0")){
            lisAlumPatrocinadores 	= alumPatrocinadorDao.lisPatrocinadosPorPeriodo(cargaId, filterQuery+" ORDER BY CODIGO_PERSONAL");
        }else{
            lisAlumPatrocinadores = alumPatrocinadorDao.lisPatrocinadosPorPeriodo(cargaId, patrocinadorId, filterQuery+" ORDER BY CODIGO_PERSONAL");
        }
        
		HashMap <String, CatPatrocinador> mapPatrocinadores 	= catPatrocinadorDao.mapaCatPatrocinador();
        HashMap <String, AlumPersonal> mapaAlumPersonal         = alumPersonalDao.mapaAlumnosAlumnos(" ORDER BY CODIGO_PERSONAL");
        HashMap <String, AlumPlan> mapaAlumPlan                 = alumPlanDao.mapAlumPlanActivo();
        HashMap <String, AlumAcademico> mapaAlumAcademico       = alumAcademicoDao.getMapAcademico();
		HashMap <String, String> mapaPlan                       = mapaPlanDao.mapNombrePlan();
        
        model.addAttribute("cargaId", cargaId);
        model.addAttribute("patrocinadorId", patrocinadorId);
        model.addAttribute("grado", grado);
        model.addAttribute("acomodo", acomodo);
		model.addAttribute("lisCargas", lisCargas);
        model.addAttribute("lisPatrocinadores", lisPatrocinadores);
        model.addAttribute("lisAlumPatrocinadores", lisAlumPatrocinadores);
        model.addAttribute("lisAcomodos", lisAcomodos);
        model.addAttribute("mapPatrocinadores", mapPatrocinadores);
        model.addAttribute("mapaAlumPersonal", mapaAlumPersonal);
        model.addAttribute("mapaAlumPlan", mapaAlumPlan);
        model.addAttribute("mapaAlumAcademico", mapaAlumAcademico);
        model.addAttribute("mapaPlan", mapaPlan);

		return "patrocinador/reportes/patrocinador";
	}
}