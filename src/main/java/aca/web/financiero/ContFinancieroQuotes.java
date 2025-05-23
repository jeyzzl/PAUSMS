package aca.web.financiero;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import oracle.net.aso.m;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumAcademico;
import aca.alumno.spring.AlumAcademicoDao;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanDao;
import aca.alumno.spring.AlumUbicacion;
import aca.alumno.spring.AlumUbicacionDao;
import aca.catalogo.spring.CatPeriodo;
import aca.catalogo.spring.CatPeriodoDao;
import aca.catalogo.spring.CatRecogida;
import aca.catalogo.spring.CatRecogidaDao;
import aca.financiero.spring.FinConcept;
import aca.financiero.spring.FinConceptDao;
import aca.financiero.spring.FinGroup;
import aca.financiero.spring.FinGroupConcept;
import aca.financiero.spring.FinGroupConceptDao;
import aca.financiero.spring.FinGroupDao;
import aca.financiero.spring.FinQuote;
import aca.financiero.spring.FinQuoteConcept;
import aca.financiero.spring.FinQuoteConceptDao;
import aca.financiero.spring.FinQuoteDao;
import aca.plan.spring.MapaPlanDao;

@Controller
public class ContFinancieroQuotes {

    @Autowired
    private FinGroupDao finGroupDao;

    @Autowired
    private FinConceptDao finConceptDao;

    @Autowired
    private FinGroupConceptDao finGroupConceptDao;

    @Autowired
    private FinQuoteDao finQuoteDao;

    @Autowired
    private FinQuoteConceptDao finQuoteConceptDao;

    @Autowired
    private CatPeriodoDao catPeriodoDao;

    @Autowired
    private AlumPersonalDao alumPersonalDao;

   @Autowired
    private AlumAcademicoDao alumAcademicoDao;

    @Autowired
    private AlumPlanDao alumPlanDao;

    @Autowired
    private AlumUbicacionDao alumUbicacionDao;    

    @Autowired
    private CatRecogidaDao catRecogidaDao;

    @Autowired
    private MapaPlanDao mapaPlanDao;

    @RequestMapping("/financiero/quotes/quotes")
	public String financieroQuoteQuotes(HttpServletRequest request, Model modelo) {
        String periodoId    = (String)request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");

		String codigoAlumno = "0";
		String codigoPersonal = "0";
        
        HttpSession sesion  = ((HttpServletRequest)request).getSession();
		if(sesion  != null) {
			codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");
			codigoAlumno 		= (String) sesion.getAttribute("codigoAlumno");
		}

        AlumPersonal alumPersonal       = alumPersonalDao.mapeaRegId(codigoAlumno);
        AlumAcademico alumAcademico     = alumAcademicoDao.mapeaRegId(codigoAlumno);
        AlumPlan alumPlan               = alumPlanDao.mapeaRegId(codigoAlumno);
        AlumUbicacion alumUbicacion     = alumUbicacionDao.mapeaRegId(codigoAlumno);

        List<CatPeriodo> lisPeriodos    = catPeriodoDao.getListAll(" ORDER BY PERIODO_ID");
        if(lisPeriodos.size() >= 1 && periodoId.equals("0")) periodoId = lisPeriodos.get(0).getPeriodoId();

        List<FinQuote> lisQuotes = finQuoteDao.getListPeriodo(codigoAlumno, periodoId, " ORDER BY QUOTE_ID");

        String descuento  = mapaPlanDao.getDescuento(alumPlan.getPlanId());

        // HashMaps for Quotes
        HashMap<String,String> mapFeesPerQuote = finQuoteDao.mapFeesPerQuote();
        HashMap<String,String> mapSubjectsPerQuote = finQuoteDao.mapSubjectsPerQuote();
        HashMap<String,String> mapSubDescPerQuote = finQuoteDao.mapSubDescPerQuote();

        HashMap<String, CatRecogida> mapRecogida = catRecogidaDao.getMapAll("");
        
        HashMap<String, String> mapConceptsPerQuote = finQuoteConceptDao.mapConceptsPerQuote();
        
        modelo.addAttribute("codigoAlumno", codigoAlumno);
        modelo.addAttribute("alumPersonal", alumPersonal);
        modelo.addAttribute("alumAcademico", alumAcademico);
        modelo.addAttribute("alumPlan", alumPlan);
        modelo.addAttribute("alumUbicacion", alumUbicacion);
        modelo.addAttribute("lisPeriodos", lisPeriodos);
        modelo.addAttribute("lisQuotes", lisQuotes);
        modelo.addAttribute("periodoId", periodoId);
        modelo.addAttribute("mapFeesPerQuote", mapFeesPerQuote);
        modelo.addAttribute("mapSubjectsPerQuote", mapSubjectsPerQuote);
        modelo.addAttribute("mapSubDescPerQuote", mapSubDescPerQuote);
        modelo.addAttribute("mapRecogida", mapRecogida);
        modelo.addAttribute("mapConceptsPerQuote", mapConceptsPerQuote);
        modelo.addAttribute("descuento", descuento);

        return "financiero/quotes/quotes";
    }

    @RequestMapping("/financiero/quotes/editar")
	public String financieroQuoteEditar(HttpServletRequest request, Model modelo) {
        String periodoId    = (String)request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
        String quoteId      = (String)request.getParameter("QuoteId")==null?"0":request.getParameter("QuoteId");

        String codigoAlumno = "0";
		String codigoPersonal = "0";

        HttpSession sesion  = ((HttpServletRequest)request).getSession();
		if(sesion  != null) {
			codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");
			codigoAlumno 		= (String) sesion.getAttribute("codigoAlumno");
		}

        FinQuote quote = new FinQuote();
        if(finQuoteDao.existeReg(quoteId)){
            quote = finQuoteDao.mapeaRegId(quoteId);
        }

        modelo.addAttribute("periodoId", periodoId);
        modelo.addAttribute("quote", quote);

        return "financiero/quotes/editar";
    }

    @RequestMapping("/financiero/quotes/grabar")
	public String financieroQuoteGrabar(HttpServletRequest request, Model modelo) {
        String periodoId    = (String)request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
        String quoteId      = (String)request.getParameter("QuoteId")==null?"0":request.getParameter("QuoteId");
        String fecha        = (String)request.getParameter("Fecha")==null?"0":request.getParameter("Fecha");
        String description  = (String)request.getParameter("Desc")==null?"0":request.getParameter("Desc");
        String status       = (String)request.getParameter("Status")==null?"0":request.getParameter("Status");
        String semester     = (String)request.getParameter("Semester")==null?"0":request.getParameter("Semester");
        String mensaje      = "";

        String codigoAlumno = "0";
		String codigoPersonal = "0";

        HttpSession sesion  = ((HttpServletRequest)request).getSession();
		if(sesion  != null) {
			codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");
			codigoAlumno 		= (String) sesion.getAttribute("codigoAlumno");
		}

        FinQuote quote = new FinQuote();

        quote.setPeriodoId(periodoId);
        quote.setCodigoPersonal(codigoAlumno);
        quote.setFecha(fecha);
        quote.setDescription(description);
        quote.setStatus(status);
        quote.setSemester(Integer.parseInt(semester));

        if(finQuoteDao.existeReg(quoteId)){
            quote.setQuoteId(Integer.parseInt(quoteId));
            if(finQuoteDao.updateReg(quote)){
                mensaje = "Updated.";
            }else{
                mensaje = "Error updating";
            }   
        }else{
            quote.setQuoteId(finQuoteDao.maximoReg());
            if(finQuoteDao.insertReg(quote)){
                mensaje = "Saved.";
            }else{
                mensaje = "Error saving.";
            }
        }

        return "redirect:/financiero/quotes/quotes?PeriodoId="+periodoId+"&Mensaje="+mensaje;
    }

    @RequestMapping("/financiero/quotes/eliminar")
	public String financieroQuoteEliminar(HttpServletRequest request, Model modelo) {
        String quoteId      = (String)request.getParameter("QuoteId")==null?"0":request.getParameter("QuoteId");
        String periodoId    = (String)request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
        String mensaje = "";

        if(finQuoteDao.deleteReg(quoteId)){
            mensaje = "Deleted quote: "+quoteId;
        }else{
            mensaje = "Error deleting quote.";
        }

        return "redirect:/financiero/quotes/quotes?PeriodoId="+periodoId+"&Mensaje="+mensaje;
    }

    @RequestMapping("/financiero/quotes/quote")
	public String financieroQuoteQuote(HttpServletRequest request, Model modelo) {
        String periodoId    = (String)request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
        String quoteId      = (String)request.getParameter("QuoteId")==null?"0":request.getParameter("QuoteId");

        String codigoAlumno = "0";
		String codigoPersonal = "0";

        HttpSession sesion  = ((HttpServletRequest)request).getSession();
		if(sesion  != null) {
			codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");
			codigoAlumno 		= (String) sesion.getAttribute("codigoAlumno");
		}

        AlumPersonal alumPersonal       = alumPersonalDao.mapeaRegId(codigoAlumno);
        AlumAcademico alumAcademico     = alumAcademicoDao.mapeaRegId(codigoAlumno);
        AlumPlan alumPlan               = alumPlanDao.mapeaRegId(codigoAlumno);
        AlumUbicacion alumUbicacion     = alumUbicacionDao.mapeaRegId(codigoAlumno);

        String descuento = mapaPlanDao.getDescuento(alumPlan.getPlanId());

        List<FinConcept> lisConcepts = finConceptDao.lisAll(" ORDER BY CONC_ID");
        List<FinGroup> lisGroups = finGroupDao.getListAll(" ORDER BY GROUP_ID");

        List<FinQuoteConcept> lisQuoteConcepts = finQuoteConceptDao.getListAll(quoteId, " ORDER BY ROWID");

        HashMap<Integer, FinConcept> mapConcepts = finConceptDao.mapAll();
        HashMap<String, CatRecogida> mapRecogida = catRecogidaDao.getMapAll("");

        modelo.addAttribute("periodoId", periodoId);
        modelo.addAttribute("quoteId", quoteId);
        modelo.addAttribute("alumPersonal", alumPersonal);
        modelo.addAttribute("alumAcademico", alumAcademico);
        modelo.addAttribute("alumPlan", alumPlan);
        modelo.addAttribute("alumUbicacion", alumUbicacion);
        modelo.addAttribute("lisConcepts", lisConcepts);
        modelo.addAttribute("lisGroups", lisGroups);
        modelo.addAttribute("lisQuoteConcepts", lisQuoteConcepts);
        modelo.addAttribute("mapConcepts", mapConcepts);
        modelo.addAttribute("mapRecogida", mapRecogida);
        modelo.addAttribute("descuento", descuento);
        
        return "financiero/quotes/quote";
    }

    @RequestMapping("/financiero/quotes/agregar")
	public String financieroQuoteAgregar(HttpServletRequest request, Model modelo) {
        String periodoId    = (String)request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
        String quoteId      = (String)request.getParameter("QuoteId")==null?"0":request.getParameter("QuoteId");
        String concId       = (String)request.getParameter("ConcId")==null?"0":request.getParameter("ConcId");
        String noUnits      = (String)request.getParameter("NoUnits")==null?"0":request.getParameter("NoUnits");
        String mensaje      = "";
        String codigoAlumno = "";
        String planId       = "";
        
        HttpSession sesion  = ((HttpServletRequest)request).getSession();
		if(sesion  != null) {
			codigoAlumno 		= (String) sesion.getAttribute("codigoAlumno");
		}

        planId = alumPlanDao.getPlanActual(codigoAlumno);

        // Map Concept
        FinConcept concept = finConceptDao.mapeaRegId(concId);

        // Calculate amount
        Double amount = concept.getUnitCost() * Integer.parseInt(noUnits);

        // Map Quote Concept
        FinQuoteConcept finQuoteConcept = new FinQuoteConcept();

        finQuoteConcept.setQuoteId(Integer.parseInt(quoteId));
        finQuoteConcept.setConcId(Integer.parseInt(concId));
        finQuoteConcept.setNoUnits(Integer.parseInt(noUnits));
        finQuoteConcept.setAmount(amount);

        // Insert Quote Concept
        if(finQuoteConceptDao.existeReg(quoteId, concId)){
            mensaje = "Concept already in Quote";
        }else{
            if(finQuoteConceptDao.insertReg(finQuoteConcept)){
                mensaje = "Added.";
            }else{
                mensaje = "Error adding concept.";
            }
        }

        // Apply Tuition Discount
        String descuento = mapaPlanDao.getDescuento(planId);

        if(!descuento.equals("0")) {
            boolean applyDiscount = applyTuitionDiscount(quoteId, descuento, codigoAlumno);
            if(!applyDiscount) mensaje = "Error applying tuition discount";
        }

        return "redirect:/financiero/quotes/quote?QuoteId="+quoteId+"&PeriodoId="+periodoId+"&Mensaje="+mensaje;
    }

    @RequestMapping("/financiero/quotes/agregarG")
	public String financieroQuoteAgregarG(HttpServletRequest request, Model modelo) {
        String periodoId    = (String)request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
        String quoteId      = (String)request.getParameter("QuoteId")==null?"0":request.getParameter("QuoteId");
        String groupId      = (String)request.getParameter("GroupId")==null?"0":request.getParameter("GroupId");
        String codigoAlumno = "";
        String planId       = "";
        String mensaje      = "";

        HttpSession sesion  = ((HttpServletRequest)request).getSession();
		if(sesion  != null) {
			codigoAlumno 		= (String) sesion.getAttribute("codigoAlumno");
		}

        planId = alumPlanDao.getPlanActual(codigoAlumno);

        List<FinGroupConcept> lisGroupConcepts = finGroupConceptDao.getListAll(groupId, " ORDER BY CONC_ID");

        HashMap<Integer, FinConcept> mapConcepts = finConceptDao.mapAll();

        // Insert Group Concepts
        for(FinGroupConcept groupConcept : lisGroupConcepts){
            // Map Concept
            FinQuoteConcept finQuoteConcept = new FinQuoteConcept();
            finQuoteConcept.setQuoteId(Integer.parseInt(quoteId));
            finQuoteConcept.setConcId(groupConcept.getConcId());
            finQuoteConcept.setNoUnits(groupConcept.getNoUnits());
            
            // Calculate amount
            double unitCost = 0;
            if(mapConcepts.containsKey(groupConcept.getConcId())) unitCost = mapConcepts.get(groupConcept.getConcId()).getUnitCost();
            
            double amount = groupConcept.getNoUnits() * unitCost;
            finQuoteConcept.setAmount(amount);

            // Insert reg
            if(finQuoteConceptDao.existeReg(quoteId, String.valueOf(groupConcept.getConcId()))){
                mensaje = "Concept already in Quote";
                continue;
            }else{
                if(finQuoteConceptDao.insertReg(finQuoteConcept)){
                    mensaje = "Added.";
                }else{
                    mensaje = "Error adding concept.";
                }
            }
        }

        // Apply Tuition Discount
        String descuento = mapaPlanDao.getDescuento(planId);

        if(!descuento.equals("0")) {
            boolean applyDiscount = applyTuitionDiscount(quoteId, descuento, codigoAlumno);
            if(!applyDiscount) mensaje = "Error applying tuition discount";
        }

        return "redirect:/financiero/quotes/quote?QuoteId="+quoteId+"&PeriodoId="+periodoId+"&Mensaje="+mensaje;
    }

    @RequestMapping("/financiero/quotes/remover")
	public String financieroQuoteRemover(HttpServletRequest request, Model modelo) {
        String periodoId    = (String)request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
        String quoteId      = (String)request.getParameter("QuoteId")==null?"0":request.getParameter("QuoteId");
        String concId       = (String)request.getParameter("ConcId")==null?"0":request.getParameter("ConcId");
        String mensaje      = "";

        if(finQuoteConceptDao.deleteReg(quoteId, concId)){
            mensaje = "Removed.";
        }else{
            mensaje = "Error removing.";
        }

        return "redirect:/financiero/quotes/quote?QuoteId="+quoteId+"&PeriodoId="+periodoId+"&Mensaje="+mensaje;
    }
    

    public boolean applyTuitionDiscount(String quoteId, String discount, String codigoAlumno){
        boolean result = false;

        // Get list/num of subjects in quote
        List<FinQuoteConcept> lisQuoteSubjects = finQuoteConceptDao.getSubjectsPerQuote(quoteId, " ORDER BY CONC_ID");

        int numSubjects = lisQuoteSubjects.size();

        // Get tuiton total
        Double tuitionTotal = 0.0;
        for(FinQuoteConcept concept : lisQuoteSubjects){
            FinConcept subject = finConceptDao.mapeaRegId(String.valueOf(concept.getConcId()));

            tuitionTotal += subject.getUnitCost();
        }

        // Calculate discount total
        Double tuitionDiscount = (tuitionTotal * Double.parseDouble(discount)) / 100;

        // Calculate discount per subject
        Double subDiscountAmt = tuitionDiscount / numSubjects;
        subDiscountAmt = (double) Math.round(subDiscountAmt);

        // Update quote concepts with new amount
        int updatedSubjects = 0;
        int errors = 0;
        for(FinQuoteConcept concept : lisQuoteSubjects){
            concept.setDiscountAmt(subDiscountAmt);

            if(finQuoteConceptDao.existeReg(quoteId, String.valueOf(concept.getConcId()))){
                if(finQuoteConceptDao.updateReg(concept)){
                    updatedSubjects++;
                }else{
                    System.out.println("Error updating amount");
                    errors++;
                }
            }else{
                System.out.println("Concept not found in quote");
                errors++;
            }
        }

        if((updatedSubjects == numSubjects) && errors <= 0) result = true;

        return result;
    }
}
