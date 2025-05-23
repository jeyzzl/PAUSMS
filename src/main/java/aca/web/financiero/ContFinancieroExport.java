package aca.web.financiero;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.catalogo.spring.CatPeriodo;
import aca.catalogo.spring.CatPeriodoDao;
import aca.financiero.spring.FinConcept;
import aca.financiero.spring.FinConceptDao;
import aca.financiero.spring.FinQuote;
import aca.financiero.spring.FinQuoteConcept;
import aca.financiero.spring.FinQuoteConceptDao;
import aca.financiero.spring.FinQuoteDao;

@Controller
public class ContFinancieroExport {

    @Autowired
    private FinConceptDao finConceptDao;
    
    @Autowired
    private FinQuoteDao finQuoteDao;

    @Autowired
    private FinQuoteConceptDao finQuoteConceptDao;

    @Autowired
    private AlumPersonalDao alumPersonalDao;

    @Autowired
    private CatPeriodoDao catPeriodoDao;

    @RequestMapping("/financiero/export/export")
	public String financieroExportExport(HttpServletRequest request, Model modelo) {
        String periodoId = request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
        String semester = request.getParameter("Semester")==null?"1":request.getParameter("Semester");

        List<CatPeriodo> lisPeriodos = catPeriodoDao.getListAll(" ORDER BY PERIODO_ID");
        List<FinQuote> lisQuotes = finQuoteDao.getListPeriodoSemestre(periodoId, semester, " ORDER BY QUOTE_ID");

        HashMap<String,AlumPersonal> mapAlumPersonal    = alumPersonalDao.mapaAlumnosAlumnos( "");
        HashMap<String,String> mapSubjectsPerQuote      = finQuoteConceptDao.mapConceptsPerQuote(periodoId, semester, "S");
        HashMap<String,String> mapFeesPerQuote          = finQuoteConceptDao.mapConceptsPerQuote(periodoId, semester, "F");

        modelo.addAttribute("periodoId", periodoId);
        modelo.addAttribute("semester", semester);
        modelo.addAttribute("lisPeriodos", lisPeriodos);
        modelo.addAttribute("lisQuotes", lisQuotes);
        modelo.addAttribute("mapAlumPersonal", mapAlumPersonal);
        modelo.addAttribute("mapSubjectsPerQuote", mapSubjectsPerQuote);
        modelo.addAttribute("mapFeesPerQuote", mapFeesPerQuote);

        return "financiero/export/export";
    }

    // @RequestMapping("/financiero/export/generate")
    // public void financieroExportGenerar(HttpServletRequest request, HttpServletResponse response) throws IOException {
    //     String periodoId = request.getParameter("PeriodoId") == null ? "0" : request.getParameter("PeriodoId");
    //     String semester = request.getParameter("Semester") == null ? "1" : request.getParameter("Semester");

    //     // Get data
    //     List<FinQuote> lisQuotes = finQuoteDao.getListPeriodoSemestre(periodoId, semester, " ORDER BY QUOTE_ID");
    //     List<FinQuoteConcept> lisFeesSubjectsPerQuote = finQuoteConceptDao.getFeesSubjectsPerQuote(periodoId, semester, " ORDER BY QUOTE_ID, ROWID");
    //     Map<Integer, FinConcept> mapConcepts = finConceptDao.mapAll();
    //     Map<Integer, String> mapConceptLocation = finConceptDao.mapConceptLocation();

    //     // Group concepts by quote ID
    //     Map<Integer, List<FinQuoteConcept>> quoteConceptsMap = lisFeesSubjectsPerQuote.stream()
    //         .collect(Collectors.groupingBy(FinQuoteConcept::getQuoteId));

    //     // Set up XML response
    //     response.setContentType("application/xml");
    //     response.setHeader("Content-Disposition", "attachment; filename=\"student_orders.kfi\"");
        
    //     try (PrintWriter writer = response.getWriter()) {
    //         // XML header
    //         writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    //         writer.println("<xmlkfi object_id='107'>");
            
    //         // Process each quote
    //         for (FinQuote quote : lisQuotes) {
    //             writer.println("\t<record>");
                
    //             // Write header
    //             writer.println("\t\t<header>");
    //             writer.println("\t\t\t<customercode>" + quote.getCodigoPersonal() + "</customercode>");
    //             writer.println("\t\t\t<reference>" + quote.getDescription() + "</reference>");
    //             writer.println("\t\t</header>");
                
    //             // Get concepts for this quote
    //             List<FinQuoteConcept> concepts = quoteConceptsMap.getOrDefault(quote.getQuoteId(), new ArrayList<>());
                
    //             // Process all concepts
    //             for (FinQuoteConcept concept : concepts) {
    //                 FinConcept fc = mapConcepts.get(concept.getConcId());
    //                 if (fc != null) {
    //                     String location = mapConceptLocation.getOrDefault(concept.getConcId(), 
    //                         "F".equals(fc.getType()) ? "17" : "45").trim(); // Default to 17 for fees, 45 for subjects
                        
    //                     writer.println("\t\t<serviceline>");
    //                     writer.println("\t\t\t<location>" + location + "</location>");
                        
    //                     if ("S".equals(fc.getType())) {
    //                         writer.println("\t\t\t<servicecode>" + fc.getCursoClave() + "</servicecode>");
    //                     } else if ("F".equals(fc.getType())) {
    //                         writer.println("\t\t\t<servicecode>" + fc.getCode() + "</servicecode>");
    //                         writer.println("\t\t\t<qtyordered>" + concept.getNoUnits() + "</qtyordered>");
    //                     }
                        
    //                     writer.println("\t\t</serviceline>");
    //                 }
    //             }
                
    //             writer.println("\t</record>");
    //         }
            
    //         writer.println("</xmlkfi>");
    //     }
    // }

    @RequestMapping("/financiero/export/generate")
    public void financieroExportGenerar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Constants
        final String DEFAULT_SUBJECT_LOCATION = "45";
        final String DEFAULT_FEE_LOCATION = "17";
        final String DEFAULT_QTY = "1";
        
        String periodoId = request.getParameter("PeriodoId") == null ? "0" : request.getParameter("PeriodoId");
        String semester = request.getParameter("Semester") == null ? "1" : request.getParameter("Semester");

        try {
            // Get data
            List<FinQuote> lisQuotes = finQuoteDao.getListPeriodoSemestre(periodoId, semester, " ORDER BY QUOTE_ID");
            List<FinQuoteConcept> lisFeesSubjectsPerQuote = finQuoteConceptDao.getFeesSubjectsPerQuote(periodoId, semester, " ORDER BY QUOTE_ID, ROWID");
            Map<Integer, FinConcept> mapConcepts = finConceptDao.mapAll();
            Map<Integer, String> mapConceptLocation = finConceptDao.mapConceptLocation();

            // Group concepts by quote ID
            Map<Integer, List<FinQuoteConcept>> quoteConceptsMap = lisFeesSubjectsPerQuote.stream()
                .collect(Collectors.groupingBy(FinQuoteConcept::getQuoteId));

            // Set up response with exact encoding control
            response.setContentType("application/xml; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=\"student_orders.kfi\"");
            
            try (OutputStream out = response.getOutputStream()) {
                // Write XML declaration (no BOM)
                out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n".getBytes(StandardCharsets.UTF_8));
                
                // Write root element
                out.write("<xmlkfi object_id='107'>\n".getBytes(StandardCharsets.UTF_8));
                
                // Process each quote
                for (FinQuote quote : lisQuotes) {
                    if (quote.getCodigoPersonal() == null || quote.getDescription() == null) {
                        continue;
                    }
                    
                    if(quote.getCodigoPersonal().equals(request.getParameter(quote.getCodigoPersonal()))){
                        // Write record header
                        out.write("\t<record>\n".getBytes(StandardCharsets.UTF_8));
                        out.write("\t\t<header>\n".getBytes(StandardCharsets.UTF_8));
                        out.write(("\t\t\t<customercode>" + escapeXml(quote.getCodigoPersonal()) + "</customercode>\n").getBytes(StandardCharsets.UTF_8));
                        out.write(("\t\t\t<reference>" + escapeXml(quote.getDescription()) + "</reference>\n").getBytes(StandardCharsets.UTF_8));
                        out.write("\t\t</header>\n".getBytes(StandardCharsets.UTF_8));
                        
                        // Get concepts for this quote
                        List<FinQuoteConcept> concepts = quoteConceptsMap.getOrDefault(quote.getQuoteId(), Collections.emptyList());
                        
                        // Process all concepts
                        for (FinQuoteConcept concept : concepts) {
                            FinConcept fc = mapConcepts.get(concept.getConcId());
                            if (fc == null || fc.getType() == null) {
                                continue;
                            }
                            
                            String location = mapConceptLocation.getOrDefault(concept.getConcId(), 
                                        "F".equals(fc.getType()) ? DEFAULT_FEE_LOCATION : DEFAULT_SUBJECT_LOCATION)
                                        .trim();
                            
                            // Write service line
                            out.write("\t\t<serviceline>\n".getBytes(StandardCharsets.UTF_8));
                            out.write(("\t\t\t<location>" + location + "</location>\n").getBytes(StandardCharsets.UTF_8));
                            
                            if ("S".equals(fc.getType()) && fc.getCursoClave() != null) {
                                out.write(("\t\t\t<servicecode>" + escapeXml(fc.getCursoClave()) + "</servicecode>\n").getBytes(StandardCharsets.UTF_8));
                                out.write(("\t\t\t<discountamt>" + escapeXml(String.valueOf(concept.getDiscountAmt())) + "</discountamt>\n").getBytes(StandardCharsets.UTF_8));
                            } else if ("F".equals(fc.getType()) && fc.getCode() != null) {
                                out.write(("\t\t\t<servicecode>" + escapeXml(fc.getCode()) + "</servicecode>\n").getBytes(StandardCharsets.UTF_8));
                                String qty = concept.getNoUnits() != 0 ? String.valueOf(concept.getNoUnits()) : DEFAULT_QTY;
                                out.write(("\t\t\t<qtyordered>" + qty + "</qtyordered>\n").getBytes(StandardCharsets.UTF_8));
                            }
                            
                            out.write("\t\t</serviceline>\n".getBytes(StandardCharsets.UTF_8));
                        }
                        
                        out.write("\t</record>\n".getBytes(StandardCharsets.UTF_8));
                    }
                }
                
                // Close root element
                out.write("</xmlkfi>".getBytes(StandardCharsets.UTF_8));
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error generating export file");
            // Log error if logger is available
        }
    }

    private String escapeXml(String input) {
        if (input == null) return "";
        return input.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;")
                   .replace("'", "&apos;");
    }
}