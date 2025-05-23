<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>

<jsp:useBean id="cursoU" scope="page" class="aca.vista.AlumnoCursoUtil"/>
<jsp:useBean id="alumnoCurso" scope="page" class="aca.vista.AlumnoCurso"/>
<jsp:useBean id="AlumCursoU" class="aca.vista.AlumnoCursoUtil" scope="page"/>
<jsp:useBean id="tipoCal" scope="page" class="aca.catalogo.TipoCalUtil"/>
<jsp:useBean id="plan" scope="page" class="aca.alumno.AlumPlan"/>
<jsp:useBean id="AlumPlanU" scope="page" class="aca.alumno.PlanUtil"/>
<jsp:useBean id="Parametros" scope="page" class="aca.parametros.Parametros"/>

<%
	String institucion 			= (String)session.getAttribute("institucion");
	
	String s_codigo_personal 	= (String) session.getAttribute("codigoAlumno");
	String s_comentario			= request.getParameter("f_comentario");
	String s_destinatario		= request.getParameter("f_destinatario");
	String s_dia				= request.getParameter("f_dia");
	String s_mes				= request.getParameter("f_mes");
	String s_year				= request.getParameter("f_year");
	String cargaId				= request.getParameter("cargaId");
	String firma	 			= request.getParameter("firma");
	
	plan = AlumPlanU.mapeaRegId(conEnoc,s_codigo_personal);
	String escala 				= plan.getEscala();
	
	int row = 0;
	
	Parametros.mapeaRegId(conEnoc, "1");
%>
<html>
<head>
	<style TYPE="text/css">
		<!--
		BODY { background: #ffffff; }
		.para1 { margin-top: -770px; }
		.para2 { margin-top: 250px; }
		-->
	</style>	
</head>
<body bgcolor="#FFFFFF">
<div align="right"> <br>  </div>
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
            León, México, mediante<br>
            Resolución Oficial<br>
            publicada el 5 de mayo<br>
            de 1973.<br>
            <br>
            <b>Clave de la Institución</b><br>
            ante la SEP y Dirección<br>
            General de Estadística<br>
          19MSU1017U </div>
        </td>
        <td width="7" align="CENTER" valign="TOP" style="font-size: xx-small;"> 
          <img src="../../images/linea.jpg"  width=2 height=800 alt=""> 
        </td>
        <td colspan="2" align="JUSTIFY" width="642"> <br>
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
            <font face="Arial Narrow, Arial, Times New Roman">La que suscribe, 
            <%=Parametros.getConstancias()%>, Directora de Gestión Académica y Registro Escolar de la <%=institucion%>,
            por este medio <b>HACE CONSTAR QUE:</b></font><br>
            <br>
            <%=s_comentario.replaceAll("porcentaje","%") %><br>
            <br>
            
            <table style="width:98%"    >
<%				ArrayList lisCursos = cursoU.getListAlumnoCarga(conEnoc,s_codigo_personal, cargaId, "ORDER BY NOMBRE_CURSO");
               	String notaMateria = "";
           		aca.kardex.EvaluacionUtil krdxEvaluacionUtil = new aca.kardex.EvaluacionUtil();
				for(int i = 0; i < lisCursos.size(); i++){
					alumnoCurso = (aca.vista.AlumnoCurso) lisCursos.get(i);
					if (alumnoCurso.getTipoCalId().equals("I")||alumnoCurso.getTipoCalId().equals("1") ){
						row++;
						notaMateria = alumnoCurso.getNota();
						if (notaMateria.equals("0")){
							notaMateria = krdxEvaluacionUtil.getAlumnoPromedio(conEnoc, alumnoCurso.getCursoCargaId(), alumnoCurso.getCodigoPersonal());
						}
%>
				  <tr>
					<td width="5%" valign="top" height="0"><%=row%>.</td>
					<td width="60%"><%=alumnoCurso.getNombreCurso()%></td>
					<td width="28%">
					  Nota: [ 
					  <%if (AlumCursoU.obtenTipoCurso(conEnoc,alumnoCurso.getCursoId())==3||AlumCursoU.obtenTipoCurso(conEnoc,alumnoCurso.getCursoId())==4)
						  	out.print(tipoCal.getNombre(conEnoc,alumnoCurso.getTipoCalId(),""));
					  	else  {
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
					<td width="7%" align="center">[<%=tipoCal.getNombre(conEnoc,alumnoCurso.getTipoCalId(),"")%>]</td>
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
					<div align="left" style="WIDTH: 100px; HEIGHT: 80px">&nbsp;<img src="../../imagenes/firma.png" alt="Imprimir" width="172" height="90" >              	
              	</td>
              </tr>
            <%} %>
              <tr> 
                <td height="20"> 
                  <div align="left"><font size="3" face="Arial Narrow, Arial, Times New Roman"><%=Parametros.getConstancias()%></font></div>
                </td>
              </tr>
              <tr> 
                <td> 
                  <div align="left">
                    <font size="3" face="Arial Narrow, Arial, Times New Roman">
                      Directora de Gestión Académica y Registro Escolar
                    </font>
                  </div>
                </td>
              </tr>
            </table>
          </div>
          <br>
          <br>
          <br>
          <br>
          <br>
          <br>
          <br>          
		  <br>
		  <br>
        </td>
      </tr>
    </table>
  </form>
</div>
<!-- fin de estructura -->
<%@ include file= "../../cierra_enoc.jsp" %>