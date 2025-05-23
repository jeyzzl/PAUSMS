<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="alumnoCursoUtil" scope="page" class="aca.vista.AlumnoCursoUtil"/>
<jsp:useBean id="alumnoUtil" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="plan" scope="page" class="aca.alumno.AlumPlan"/>
<jsp:useBean id="planU" scope="page" class="aca.alumno.PlanUtil"/>
<jsp:useBean id="planUtil" scope="page" class="aca.plan.PlanUtil"/>
<script type="text/javascript">	
	function recarga(pagina,plan){
		document.location.href='ponderado?plan='+plan+'&pag='+pagina;
	}
</script>
<%
	String matricula = (String) session.getAttribute("codigoAlumno");
	String planActual = request.getParameter("plan");
	String pagi = request.getParameter("pag");
	
	if(pagi == null||pagi.trim().equals("")||pagi.trim().equals("null")) pagi = "ponderadof";
	
	ArrayList<String> planes = planU.getPlanesAlumno(conEnoc,matricula);
	if(planActual==null){
		plan.mapeaRegId(conEnoc,matricula);
		planActual=plan.getPlanId();
	}
	else {
		plan.mapeaRegId(conEnoc,matricula,planActual);		
	}
	
%><head><link href="css/pa.css" rel="STYLESHEET" type="text/css">
</head>
<STYLE TYPE="text/css">
.tabbox
	{
		background: #eeeeee;
		border-left: 0pt gray solid;
		border-right: 0pt gray solid;
		border-bottom: 1pt gray solid;
	}
/* "javascript:recarga('promediof',this.options[this.selectedIndex].value)" */
</STYLE>
<%
	String pagina = "promediof";
%>
<body marginwidth="0" marginheight="0" leftmargin="0" topmargin="0" background="../../imagenes/back.gif">
<table style='margin:0 auto;'    >
  <tr><td align='center'>
  	<form name="forma" action="ponderado?pag=ponderadof" method='post'>
  		<select name="plan" onchange="document.forma.submit();">
  		<%
  		for(int i=0;i<planes.size();i++){%>
  			<option value='<%=(String)planes.get(i)%>' <%if(planActual.equals((String)planes.get(i)))out.print("Selected");%> ><%=aca.plan.PlanUtil.getNombrePlan(conEnoc,(String)planes.get(i))%></option>
  		<%}%>
  		</select>
  		
  	</form>
  	</td></tr>
  	<tr><td align='center'><input type=button value="Kardex para Imprimir" onClick="location='ponderado?pag=kardex&plan=<%=planActual %>';"></td></tr>
</table>
<table style='margin:0 auto;'     width="100%" height="88%">
<tr><td align="center">
<iframe width="98%" height="100%" name="datos" src="<%=pagi%>?plan=<%=planActual%>" frame scrolling="yes">
</iframe>
</td></tr>
<%@ include file= "../../cierra_enoc.jsp" %>