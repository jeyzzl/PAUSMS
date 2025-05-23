<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="GrupoPlan" scope="page" class="aca.carga.CargaGrupoPlan"/>
<jsp:useBean id="GrupoPlanU" scope="page" class="aca.carga.CargaGrupoPlanUtil"/>
<jsp:useBean id="GrupoEje" scope="page" class="aca.carga.CargaGrupoEje"/>
<jsp:useBean id="GrupoEjeU" scope="page" class="aca.carga.CargaGrupoEjeUtil"/>
<jsp:useBean id="GrupoCompetencia" scope="page" class="aca.carga.CargaGrupoCompetencia"/>
<jsp:useBean id="GrupoCompetenciaU" scope="page" class="aca.carga.CargaGrupoCompetenciaUtil"/>
<jsp:useBean id="GrupoBiblio" scope="page" class="aca.carga.CargaGrupoBiblio"/>
<jsp:useBean id="GrupoBiblioU" scope="page" class="aca.carga.CargaGrupoBiblioUtil"/>
<% 
	String cursoCargaId 	= "";
	String maestro 		    = "";
	String materia 		    = "";
    if (request.getParameter("CursoCargaId")!=null){
		session.setAttribute("CursoCargaId", request.getParameter("CursoCargaId"));
		session.setAttribute("Maestro", request.getParameter("Maestro"));
		session.setAttribute("Materia", request.getParameter("Materia"));
	}	
    cursoCargaId 			= (String) session.getAttribute("CursoCargaId");
    maestro 			    = (String) session.getAttribute("Maestro");
    materia 			    = (String) session.getAttribute("Materia");
     
    String cursoOrigen 		= aca.carga.CargaGrupoCursoUtil.cursoIdOrigen(conEnoc, cursoCargaId);
    String planId			= cursoOrigen.substring(0,8);
     
    int accion 		   		= request.getParameter("Accion")==null?0:Integer.parseInt(request.getParameter("Accion"));
    String Resultado      	= "";
    
    boolean datosGrales 	= false;
    boolean ejes 			= false;
    boolean planEval 		= false;
    boolean bibliografia	= false;
    boolean evaluacion 		= false;
     
    switch (accion){	        
		case 1: { // cierra prontuario
	    	GrupoPlan.setCursoCargaId(cursoCargaId);
	    	GrupoPlan.setEstado("C");
	    	if (GrupoPlanU.existeReg(conEnoc, cursoCargaId) == true){	
				if (GrupoPlanU.updateEstado(conEnoc, cursoCargaId, "C")){ 
					Resultado = "El estado del Prontuario es: Cerrado";					
				}else{
					Resultado = "No Cambió: "+GrupoPlan.getCursoCargaId();
				}
			}
			break;	
			
		}
	    
	    case 2: { // abre prontuario
	    	GrupoPlan.setCursoCargaId(cursoCargaId);
	    	GrupoPlan.setEstado("A");
	    	if (GrupoPlanU.existeReg(conEnoc, cursoCargaId) == true){	
				if (GrupoPlanU.updateEstado(conEnoc, cursoCargaId,"A")){ 
					Resultado = "El estado del Prontuario es: Abierto";					
				}else{
					Resultado = "No Cambió: "+GrupoPlan.getCursoCargaId();
				}
			}
			break;			
		}		
	}
    
%>

<body>
<table style="margin: 0 auto;  width:50%">
   <tr><td>&nbsp;</td></tr>
   <tr><td>&nbsp;</td></tr>
</table>
<table style="margin: 0 auto;  width:50%" style="border: dotted 2px gray;">
  <tr><td align="center" style="font-size:12pt; font-weight:bold;">Pasos para capturar el Prontuario</td></tr>
   <tr><td align="center" style="font-size:9pt; font-weight:bold;">Materia:&nbsp;<%= materia %></td></tr>
  <tr><td>&nbsp;</td></tr>
  <tr>
    <td><% if (!GrupoPlanU.existeDatos(conEnoc, cursoCargaId)== true){%>
          <img src="../../imagenes/vacio.jpg"  />
          <a href="datosPlan?Accion=1"><font size="2" color="#5FB404">Datos Generales del Curso</font></a>
        <% }else {%>
          <img src="../../imagenes/acierto.gif"  />
           <% if(!aca.carga.CargaGrupoPlanUtil.getEstado(conEnoc, cursoCargaId).equals("C")){ out.print("<a href='datosPlan?Accion=1'>"); } %>
             <font size="2" color="#5FB404">Datos Generales del Curso</font>
           <%  if(!aca.carga.CargaGrupoPlanUtil.getEstado(conEnoc, cursoCargaId).equals("C")){out.print("</a>");}%>
        <% }%>
    </td>
  </tr>
  <tr>
    <td><% if (GrupoEjeU.existenEjes(conEnoc, cursoCargaId)== false){%>
        <img src="../../imagenes/vacio.jpg"  />
        <a href="ejes"><font size="2" color="#5FB404">Ejes Transversales</font></a>
        <% }else{ ejes = true; %>
         <img src="../../imagenes/acierto.gif"  />
           <%  if(!aca.carga.CargaGrupoPlanUtil.getEstado(conEnoc, cursoCargaId).equals("C")){ out.print("<a href='ejes'>"); } %>
             <font size="2" color="#5FB404">Ejes Transversales</font>
           <%  if(!aca.carga.CargaGrupoPlanUtil.getEstado(conEnoc, cursoCargaId).equals("C")){out.print("</a>");}%> 
        <% }%>
    </td>
  </tr>  
  <tr>
    <td><%	if(aca.carga.CargaUnidadActividadUtil.getNumActividad(conEnoc, cursoCargaId)<=0){%>
        <img src="../../imagenes/vacio.jpg"  />
        <a href="planEvaluacion"><font size="2" color="#5FB404">Organización y Evaluación</font></a>
        <% 	}else{ planEval= true; %>
            <img src="../../imagenes/acierto.gif"  />
            <%  if(!aca.carga.CargaGrupoPlanUtil.getEstado(conEnoc, cursoCargaId).equals("C")){ out.print("<a href='planEvaluacion'>"); } %>
              <font size="2" color="#5FB404">Organización y Evaluación</font>
            <%  if(!aca.carga.CargaGrupoPlanUtil.getEstado(conEnoc, cursoCargaId).equals("C")){out.print("</a>");}%>
        <% 	} %>
    </td>
  </tr>
  <tr>
    <td><%if (GrupoBiblioU.existeBibliografia(conEnoc, cursoCargaId)== false){%>
      <img src="../../imagenes/vacio.jpg"  />
      <a href="bibliografia?Accion=1"><font size="2" color="#5FB404">Bibliografía</font></a>
      <%}else{ bibliografia=true; %>
      <img src="../../imagenes/acierto.gif"  />
         <%  if(!aca.carga.CargaGrupoPlanUtil.getEstado(conEnoc, cursoCargaId).equals("C")){ out.print("<a href='bibliografia?Accion=1'>"); } %>
           <font size="2" color="#5FB404">Bibliografía</font>
         <%  if(!aca.carga.CargaGrupoPlanUtil.getEstado(conEnoc, cursoCargaId).equals("C")){out.print("</a>");}%>
      <% }%>
    </td>
  </tr>
  <tr>
    <td><% if(aca.carga.CargaPlanEvalUtil.getSumEvaluaciones(conEnoc, cursoCargaId) != 100 ){%>
        <img src="../../imagenes/vacio.jpg"  />
        <a href="pautas?Accion=1"><font size="2" color="#5FB404">Pautas para la  Evaluación</font></a>
        <%  }else{ evaluacion=true; %>
        <img src="../../imagenes/acierto.gif"  />     
             <% if(!aca.carga.CargaGrupoPlanUtil.getEstado(conEnoc, cursoCargaId).equals("X")){ out.print("<a href='pautas?Accion=1'>"); } %>  
               <font size="2" color="#5FB404">Pautas para la  Evaluación</font>
             <% if(!aca.carga.CargaGrupoPlanUtil.getEstado(conEnoc, cursoCargaId).equals("X")){out.print("</a>");}%>
        <% } %>
    </td>
  </tr>
  <tr><td>&nbsp;</td></tr>
<%
	/**** Obtiene los datos del curso en la tabla Mapa_Nuevo_Curso*/
	String version 		= aca.plan.MapaNuevoCursoUtil.getMaxVersionCurso(conEnoc, cursoOrigen); 
	String nuevoCursoId = aca.plan.MapaNuevoCursoUtil.getNuevoCursoId(conEnoc, cursoOrigen, version);
%>  
  <tr>
    <td align="center">
      <input type="button" value="Materia PDF" onclick="location='../../mapa/nuevos/muestraMateriaPdf?planId=<%=planId%>&versionId=<%=version%>&cursoId=<%=nuevoCursoId%>'" />
<%   if(aca.carga.CargaGrupoPlanUtil.getEstado(conEnoc, cursoCargaId).equals("A") && datosGrales == true && ejes == true && planEval == true && bibliografia == true && evaluacion==true){ %>
	  <input type="button" value="Cerrar" onclick="location='plan?CursoCargaId=<%=cursoCargaId%>&Maestro=<%=maestro%>&Materia=<%=materia%>&Accion=1'" /> 
<%   }else{  %>
      <input type="button" value="Curso PDF" onclick="location='planPDF?CursoCargaId=<%=cursoCargaId%>&Maestro=<%=maestro%>&Materia=<%=materia%>'" />
     &nbsp;&nbsp;<input type="button" value="Abrir" onclick="location='plan?CursoCargaId=<%=cursoCargaId%>&Maestro=<%=maestro%>&Materia=<%=materia%>&Accion=2'" />     
<%   }%>     
	</td>     
  </tr>
</table>
</body>
<%@ include file= "../../cierra_enoc.jsp" %>