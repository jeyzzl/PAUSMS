package aca.web.financiero;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import oracle.net.aso.c;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.attache.spring.AttacheProduct;
import aca.attache.spring.AttacheProductDao;
import aca.financiero.spring.FinConcept;
import aca.financiero.spring.FinConceptDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaCurso;
import aca.plan.spring.MapaCursoDao;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContFinancieroConcepts {

    @Autowired
    private FinConceptDao finConceptDao;

    @Autowired
    private MapaCursoDao mapaCursoDao;

    @Autowired
    private MapaPlanDao mapaPlanDao;

    @Autowired
	private MaestrosDao maestrosDao;

    @Autowired
    private AttacheProductDao attacheProductDao;

    @RequestMapping("/financiero/concepts/concepts")
	public String financieroConceptsConcepts(HttpServletRequest request, Model modelo) {
        String tipo = request.getParameter("Tipo")==null?"A":request.getParameter("Tipo");
        String status = request.getParameter("Status")==null?"B":request.getParameter("Status");
        String planId = request.getParameter("PlanId")==null?"-":request.getParameter("PlanId");

        String filterQuery = "";

        if(!tipo.equals("A")){
            filterQuery = " WHERE TYPE = '"+tipo+"'";
            if(tipo.equals("S") && !planId.equals("-")){
                filterQuery += " AND CURSO_CLAVE IN (SELECT CURSO_CLAVE FROM MAPA_CURSO WHERE PLAN_ID = '"+planId+"')";
            }
        }

        if(!status.equals("B")){
            filterQuery += " AND STATUS = '"+status+"'";
        }

        List<FinConcept> lisConcepts = finConceptDao.lisAll(filterQuery+" ORDER BY CONC_ID");

        List<MapaPlan> lisPlanes = mapaPlanDao.getListPlanActivo("");

        HashMap<String,String> mapConceptsUsed = finConceptDao.mapConceptsUsed();        

        modelo.addAttribute("tipo", tipo);
        modelo.addAttribute("status", status);
        modelo.addAttribute("planId", planId);
        modelo.addAttribute("lisConcepts", lisConcepts);
        modelo.addAttribute("lisPlanes", lisPlanes);
        modelo.addAttribute("mapConceptsUsed", mapConceptsUsed);
        
        return "financiero/concepts/concepts";
    }

    @RequestMapping("/financiero/concepts/editar")
	public String financieroConceptsEditar(HttpServletRequest request, Model modelo) {
        String concId = request.getParameter("ConcId")==null?"0":request.getParameter("ConcId");

        FinConcept concept = new FinConcept();
        if(finConceptDao.existeReg(concId)){
            concept = finConceptDao.mapeaRegId(concId);
        }

        List<String> lisLlaves = mapaCursoDao.lisCursoClaves(" ORDER BY CURSO_CLAVE");
        List<MapaCurso> lisCursos = mapaCursoDao.getListaCursos(" ORDER BY CURSO_CLAVE");

        modelo.addAttribute("concept", concept);
        modelo.addAttribute("lisLlaves", lisLlaves);
        modelo.addAttribute("lisCursos", lisCursos);

        return "financiero/concepts/editar";
    }

    @RequestMapping("/financiero/concepts/grabar")
	public String financieroConceptsGrabar(HttpServletRequest request, Model modelo) {
        String concId       = request.getParameter("ConcId")==null?"0":request.getParameter("ConcId");
        String name         = request.getParameter("Name")==null?"0":request.getParameter("Name");
        String unitCost     = request.getParameter("UnitCost")==null?"0":request.getParameter("UnitCost");
        String type         = request.getParameter("Type")==null?"0":request.getParameter("Type");
        String status       = request.getParameter("Status")==null?"0":request.getParameter("Status");
        String cursoClave   = request.getParameter("CursoClave")==null?"0":request.getParameter("CursoClave");
        String code         = request.getParameter("Code")==null?"0":request.getParameter("Code");
        String mensaje      = "";

        FinConcept concept = new FinConcept();
        
        concept.setUnitCost(Double.parseDouble(unitCost));
        concept.setType(type);
        concept.setStatus(status);
        concept.setCursoClave(type.equals("S")?cursoClave:"0");
        concept.setCode(code);

        if(finConceptDao.existeReg(concId)){
            concept.setConcId(Integer.parseInt(concId));
            concept.setName(name);
            if(finConceptDao.updateReg(concept)){
                mensaje = "Updated.";
            }else{
                mensaje = "Error updating concept.";
            }
        }else{
            concept.setConcId(finConceptDao.maximoReg());

            if(concept.getType().equals("S")){
                concept.setName(attacheProductDao.getDescription(concept.getCursoClave()));
                concept.setUnitCost(attacheProductDao.getSalesPrice(concept.getCursoClave()));
            }
            if(concept.getType().equals("F")){
                concept.setName(attacheProductDao.getDescription(concept.getCode()));
                concept.setUnitCost(attacheProductDao.getSalesPrice(concept.getCode()));
            }

            if(concept.getType().equals("D")){
                concept.setName(name);
                concept.setUnitCost(Double.parseDouble(unitCost));
            }

            if(finConceptDao.insertReg(concept)){
                mensaje = "Saved.";
            }else{
                mensaje = "Error saving concept.";
            }
        }

        return "redirect:/financiero/concepts/concepts?Mensaje="+mensaje;
    }

    @RequestMapping("/financiero/concepts/eliminar")
	public String financieroConceptsEliminar(HttpServletRequest request, Model modelo) {
        String concId   = (String)request.getParameter("ConcId")==null?"0":request.getParameter("ConcId");
        String mensaje  = "";

        if(finConceptDao.deleteReg(concId)){
            mensaje = "Deleted.";
        }else{
            mensaje = "Error deleting.";
        }
        

        return "redirect:/financiero/concepts/concepts?Mensaje="+mensaje;
    }

    @RequestMapping("/financiero/concepts/sincronizarMaterias")
	public String financieroConceptsSincronizarMaterias(HttpServletRequest request, Model modelo) {
        String mensaje  = "";

        if(attacheProductDao.syncConceptPrices()){
            mensaje = "Synced correctly.";
        }else{
            mensaje = "Error in sync. Ensure Subject Keys are correct.";
        }

        return "redirect:/financiero/concepts/concepts?Mensaje="+mensaje;
    }
    
}
