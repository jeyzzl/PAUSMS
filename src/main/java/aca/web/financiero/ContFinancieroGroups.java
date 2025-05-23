package aca.web.financiero;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.financiero.spring.FinConcept;
import aca.financiero.spring.FinConceptDao;
import aca.financiero.spring.FinGroup;
import aca.financiero.spring.FinGroupConcept;
import aca.financiero.spring.FinGroupConceptDao;
import aca.financiero.spring.FinGroupDao;
import aca.mensaje.mensaje;

@Controller
public class ContFinancieroGroups {

    @Autowired
    private FinGroupDao finGroupDao;

    @Autowired
    private FinConceptDao finConceptDao;

    @Autowired
    private FinGroupConceptDao finGroupConceptDao;

    @RequestMapping("/financiero/groups/groups")
	public String financieroGroupsGroups(HttpServletRequest request, Model modelo) {

        List<FinGroup> lisGroups = finGroupDao.getListAll(" ORDER BY GROUP_ID");

        HashMap<String,String> mapNumConcepts = finGroupConceptDao.mapNumConcepts(); 

        modelo.addAttribute("lisGroups", lisGroups);
        modelo.addAttribute("mapNumConcepts", mapNumConcepts);

        return "financiero/groups/groups";
    }

    @RequestMapping("/financiero/groups/editar")
	public String financieroGroupsEditar(HttpServletRequest request, Model modelo) {
        String groupId = (String) request.getParameter("GroupId")==null?"0":request.getParameter("GroupId");

        FinGroup group = new FinGroup();
        if(finGroupDao.existeReg(groupId)){
            group = finGroupDao.mapeaRegId(groupId);
        }

        modelo.addAttribute("group", group);

        return "financiero/groups/editar";
    }

    @RequestMapping("/financiero/groups/grabar")
	public String financieroGroupsGrabar(HttpServletRequest request, Model modelo) {
        String groupId      = (String) request.getParameter("GroupId")==null?"0":request.getParameter("GroupId");
        String name         = (String) request.getParameter("Name")==null?"":request.getParameter("Name");
        String description  = (String) request.getParameter("Desc")==null?"":request.getParameter("Desc");
        String mensaje      = "";

        FinGroup group = new FinGroup();

        group.setName(name);
        group.setDescription(description);

        if(finGroupDao.existeReg(groupId)){
            group.setGroupId(Integer.parseInt(groupId));
            if(finGroupDao.updateReg(group)){
                mensaje = "Updated.";
            }else{
                mensaje = "Error updating.";
            }
        }else{
            group.setGroupId(finGroupDao.maximoReg());
            if(finGroupDao.insertReg(group)){
                mensaje = "Saved.";
            }else{
                mensaje = "Error saving";
            }
        }

        return "redirect:/financiero/groups/editar?GroupId="+group.getGroupId()+"&Mensaje="+mensaje;
    }

    @RequestMapping("/financiero/groups/eliminar")
	public String financieroGroupsEliminar(HttpServletRequest request, Model modelo) {
        String groupId      = (String) request.getParameter("GroupId")==null?"0":request.getParameter("GroupId");
        String mensaje      = "";

        if(finGroupDao.deleteReg(groupId)){
            mensaje = "Deleted.";
        }else{
            mensaje = "Error deleting.";
        }

        return "redirect:/financiero/groups/groups?Mensaje="+mensaje;
    }

    @RequestMapping("/financiero/groups/concepts")
	public String financieroGroupsConcepts(HttpServletRequest request, Model modelo) {
        String groupId = (String) request.getParameter("GroupId")==null?"0":request.getParameter("GroupId");

        FinGroup group = finGroupDao.mapeaRegId(groupId);

        List<FinGroupConcept> lisGroupConcepts = finGroupConceptDao.getListAll(groupId, "");
        List<FinConcept> lisConcepts = finConceptDao.lisAll(" ORDER BY CONC_ID");

        HashMap<Integer, FinConcept> mapConcepts = finConceptDao.mapAll();

        modelo.addAttribute("group", group);
        modelo.addAttribute("lisGroupConcepts", lisGroupConcepts);
        modelo.addAttribute("lisConcepts", lisConcepts);
        modelo.addAttribute("mapConcepts", mapConcepts);

        return "financiero/groups/concepts";
    }
    
    @RequestMapping("/financiero/groups/agregar")
	public String financieroGroupsAgregar(HttpServletRequest request, Model modelo) {
        String groupId  = (String) request.getParameter("GroupId")==null?"0":request.getParameter("GroupId");
        String concId   = (String) request.getParameter("ConcId")==null?"0":request.getParameter("ConcId");
        String noUnits  = (String) request.getParameter("NoUnits")==null?"0":request.getParameter("NoUnits");
        String mensaje = "";

        FinGroupConcept groupConcept = new FinGroupConcept();

        groupConcept.setGroupId(Integer.parseInt(groupId));
        groupConcept.setConcId(Integer.parseInt(concId));
        groupConcept.setNoUnits(Integer.parseInt(noUnits));

        if(finGroupConceptDao.existeReg(groupId, concId)){
            mensaje = "Error. Concept already in group";
        }else{
            if(finGroupConceptDao.insertReg(groupConcept)){
                mensaje = "Added concept: "+groupConcept.getConcId();
            }else{
                mensaje ="Error adding concept.";
            }
        }

        return "redirect:/financiero/groups/concepts?GroupId="+groupId+"&Mensaje="+mensaje;
    }

    @RequestMapping("/financiero/groups/remover")
	public String financieroGroupsRemover(HttpServletRequest request, Model modelo) {
        String groupId  = (String) request.getParameter("GroupId")==null?"0":request.getParameter("GroupId");
        String concId   = (String) request.getParameter("ConcId")==null?"0":request.getParameter("ConcId");
        String mensaje = "";

        if(finGroupConceptDao.deleteReg(groupId, concId)){
            mensaje = "Deleted concept: "+concId;
        }else{
            mensaje = "Error deleting concept.";
        }

        return "redirect:/financiero/groups/concepts?GroupId="+groupId+"&Mensaje="+mensaje;
    }
}
