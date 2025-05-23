<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.alumno.spring.AlumPlan"%>
<%@page import="aca.parametros.spring.Parametros"%>
<%@page import="aca.vista.spring.AlumnoCurso"%>
<%@page import="aca.plan.spring.MapaCurso"%>
<%@page import="aca.catalogo.spring.CatTipoCurso"%>
<%@page import="aca.catalogo.spring.CatTipoCal"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%	
	String institucion 			= (String)session.getAttribute("institucion");	
	String s_codigo_personal 	= (String)session.getAttribute("codigoAlumno");
	String s_comentario			= request.getParameter("f_comentario");
	String s_destinatario		= request.getParameter("f_destinatario");
	String s_dia				= request.getParameter("f_dia");
	String s_mes				= request.getParameter("f_mes");
	String s_year				= request.getParameter("f_year");
	String cargaId				= request.getParameter("CargaId");
	String firma	 			= request.getParameter("firma")==null?"sin":request.getParameter("firma");
	Parametros parametros		= (Parametros)request.getAttribute("parametros");
	AlumPlan plan 				= (AlumPlan)request.getAttribute("alumPlan");
	
	String escala 				= plan.getEscala();	
	int row = 0;
	
	
	List<AlumnoCurso> lisCursos 			= (List<AlumnoCurso>)request.getAttribute("lisCursos");
	HashMap<String,MapaCurso> mapaCursos	= (HashMap<String,MapaCurso>)request.getAttribute("mapaCursos");
	HashMap<String,CatTipoCurso> mapaTipos	= (HashMap<String,CatTipoCurso>)request.getAttribute("mapaTipos");
	HashMap<String,CatTipoCal> mapaTiposCal	= (HashMap<String,CatTipoCal>)request.getAttribute("mapaTiposCal");
%>
<html>
<head>
  	<link rel="stylesheet" href="print.css" type="text/css" media="print" />
	<style TYPE="text/css">
		<!--
		BODY { background: #ffffff; }
		.para1 { margin-top: -770px; }
		.para2 { margin-top: 250px; }
		-->
	</style>	
</head>
<body>
<table class="goback"><tr><td><a class="btn oculto" href="form">&lsaquo;&lsaquo; <spring:message code="aca.Regresar"/></a></td></tr></table>
<div align="right"> <br><br>  </div>
<div align="center" class="para2"> <img alt=""  src="../../imagenes/logo_fondo.jpg" width=539 height="505"></div>
<div CLASS="para1" style="position: relative; z-index: 0;"> 
  <form name="datos1">
    <table  cellpadding="2" align="LEFT" valign="TOP" style="font-size: 12pt; font-family: arial;" bordercolor="#7D3E9A">
      <tr> 
        <td width="101" align="CENTER" valign="TOP" style="font-size: 06pt;"> 
          <div align="left"><a href="javascript:window.print()"> <img src="../../imagenes/logo.jpg" alt="Imprimir" width="98" height="105" > 
            </a> <br>
            <br>
            <strong>Dirección de Certificación</strong><br>
            Apdo. 16-5 C.P. 67530<br>
            Montemorelos, NL, <br>
            M&eacute;xico<br>
            <br>
            <strong><spring:message code="aca.Tel"/>efonos:</strong><br>
            Directo(826) 263-0908<br>
            Conmutador 263-0900<br>
            Ext. 119,120,121 <br>
            Fax (826) 263-0979<br>
            <br>
            <br>
            <b><spring:message code="aca.Creada"/></b> por el Gobierno<br>
            del estado de Nuevo<br>
            Le&oacute;n, M&eacute;xico, mediante<br>
            Resoluci&oacute;n Oficial<br>
            publicada el 5 de mayo<br>
            de 1973.<br>
            <br>
            <b>Clave de la Institución</b><br>
            ante la SEP y Dirección<br>
            General de Estadística<br>
          19MSU1017U </div>
        </td>
        <td width="7" align="CENTER" valign="TOP" style="font-size: xx-small;"> 
          <img src="../../images/linea.jpg"  width=2 height=700 alt=""> 
        </td>
        <td colspan="2" align="JUSTIFY" width="642">
          <br>
          <br>                    
          <br>
          <br>
          <div align="RIGHT"> 
            <% //out.println(" <img src='../../fotodb/"+rset.getString("foto")+"' border='2' height='125'> "); %>
          </div>
          <br>
          <b><left><font size="3" face="Arial, Helvetica, sans-serif"><%=s_destinatario%></font></left></b><br>
          <br>
          <div align="JUSTIFY"> <font face="Arial Narrow, Arial, Times New Roman" size="3"> 
<!--             <font face="Arial Narrow, Arial, Times New Roman">La que suscribe,  -->
<%--             <%=parametros.getConstancias()%>, Vicerrectora Académica Asociada de la <%=institucion%>, --%>
            <font face="Arial Narrow, Arial, Times New Roman">El que suscribe, 
            <%=parametros.getCertificados()%>, Director de servicios escolares de la <%=institucion%>,
            por este medio <b>HACE CONSTAR QUE:</b></font><br>
            <br>
            <%=s_comentario.replaceAll("porcentaje","%") %><br>
            <br>
            
            <table style="width:98%"    ><%				
               	String notaMateria = "";
           		aca.kardex.EvaluacionUtil krdxEvaluacionUtil = new aca.kardex.EvaluacionUtil();
				for(AlumnoCurso  alumnoCurso : lisCursos){
					
					if (alumnoCurso.getTipoCalId().equals("I")||alumnoCurso.getTipoCalId().equals("1") ){
						row++;
						notaMateria = alumnoCurso.getNota();
						String tipoCurso 		= "0";
						String tipoCursoNombre	= "-";
						if (mapaCursos.containsKey(alumnoCurso.getCursoId())){
							tipoCurso = mapaCursos.get(alumnoCurso.getCursoId()).getCursoId();
							if (mapaTipos.containsKey(tipoCurso)){
								tipoCursoNombre = mapaTipos.get(tipoCurso).getNombreTipoCurso();
							}
						}
						
						String tipoCalNombre = "-";
						if (mapaTiposCal.containsKey(alumnoCurso.getTipoCalId())){
							tipoCalNombre = mapaTiposCal.get(alumnoCurso.getTipoCalId()).getNombreCorto();
						}
%>
				  <tr>
					<td width="5%" valign="top" height="0"><%=row%>.</td>
					<td width="60%"><%=alumnoCurso.getNombreCurso()%></td>
					<td width="28%">
					  Nota: [ 
					  <%if (tipoCurso.equals("3") ||tipoCurso.equals("4")){
						  	out.print(tipoCursoNombre);
					  }else{
					  		if(escala.equals("10")){
					  			out.print(Float.parseFloat(notaMateria)/10);
					  		}
					  		else{
					  			out.print(notaMateria);
					  		}
						}%>]
					  	
<%						if (!alumnoCurso.getNotaExtra().equals("0") && alumnoCurso.getNotaExtra()!=null){
					  		if(escala.equals("10")){
								out.println("Extra:["+Float.parseFloat(alumnoCurso.getNotaExtra())/10+"]");
					  		}
					  		else{
					  			out.println("Extra:["+alumnoCurso.getNotaExtra()+"]");
					  		}
					  	}%>
					</td>								  
					<td width="7%" align="center">[<%=tipoCalNombre%>]</td>
				  </tr>							  
<%					}
				} %>
            </table>
            <br>
            <font face="Arial Narrow, Arial, Times New Roman">A petición de quien 
            lo solicita y para los fines y usos que convengan, se le extiende 
            la presente <b>CONSTANCIA</b>, en la ciudad de Montemorelos, Nuevo 
            Le&oacute;n, M&eacute;xico, a los <%=s_dia%> d&iacute;as del mes de 
            <%=s_mes%> del a&ntilde;o <%=s_year%>.</font></font><br>
            <br>
            <font face="Arial Narrow, Arial, Times New Roman">Atentamente</font></div>
            <%if(firma.equals("sin")){ %>
            <br>
            <br>
            <br>
            <br>
            <%} %> 
            <table style="width:250" >
            <%if(firma.equals("con")){ %>
              <tr>
              	<td>
<!-- 					<div align="left" style="WIDTH: 100px; HEIGHT: 80px">&nbsp;<img src="../../imagenes/firma.png" alt="Imprimir" width="172" height="90" >              	 -->
					<div align="left" style="WIDTH: 100px; HEIGHT: 80px">&nbsp;<img src="../../imagenes/firmaJoseMendez.png" alt="Imprimir" width="172" height="90" >              	
              	</td>
              </tr>
            <%} %>
              <tr> 
                <td height="20"> 
                  <div align="left"><font size="3" face="Arial Narrow, Arial, Times New Roman"><%=parametros.getCertificados()%></font></div>
                </td>
              </tr>
              <tr> 
                <td> 
                  <div align="left">
                    <font size="3" face="Arial Narrow, Arial, Times New Roman">
<!--                       Vicerrectora Académica Asociada -->
                      Director de certificados escolares
                    </font>
                  </div>
                </td>
              </tr>
            </table>
          </div>          
        </td>
      </tr>
    </table>
  </form>
</div>