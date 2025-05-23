<%@ page import= "java.util.List"%>
<%@ page import= "aca.plan.spring.MapaCurso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<head>
	<script>
		function seleccionarTodos(checkAll){
			var inputs = document.getElementsByTagName("INPUT");
			for(i=0; i<inputs.length; i++) if(inputs[i].type=="checkbox") inputs[i].checked=checkAll;
		}
	</script>
</head>
<!-- inicio de estructura -->
<%
	String facultad			= (String)request.getAttribute("facultad");
	String planId			= (String)request.getAttribute("planId");
  	String carreraNombre	= (String)request.getAttribute("carreraNombre");
  	
  	List<MapaCurso> lisCursos = (List<MapaCurso>) request.getAttribute("lisCursos");
%>
<body>
	<div class="container-fluid">
	<h2><b> <%=carreraNombre%></b> - <spring:message code="aca.Plan"/> <b><%=planId %></b></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="listado?facultad=<%=facultad%>"><i class="fas fa-arrow-left"></i></a>
	</div>		
	<form name="form1" method="post" action="update" id="noayuda">
	<table class="table table-bordered">
	<thead>
	  <tr> 
	    <td colspan='7' align='CENTER'>
	      <font size="3" face="Arial Narrow, Arial">
	      <strong><spring:message code="cargasGrupos.unid.Titulo2"/>
	      </strong>
	      </font>
	    </td>
	  </tr>
	</thead>
	<thead>
	  <tr>
	    <td><a onclick="seleccionarTodos(true);" class="btn btn-primary">All</a> <a class="btn btn-primary" onclick="seleccionarTodos(false);"><spring:message code="aca.Ninguno"/></a></td>
	  </tr>
	</thead>
	<thead class="table-info">
	  <tr align='CENTER'> 
	    <th width="117"><font size="2" face="Arial Narrow, Arial"><spring:message code="cargasGrupos.unid.EnLinea"/></font></th>
	    <th width="315"><font size="2" face="Arial Narrow, Arial">Subject Name</font></th>
	    <th width="29"><font size="2" face="Arial Narrow, Arial"><spring:message code="aca.Crd"/>.</font></th>
	    <th width="26"><font size="2" face="Arial Narrow, Arial"><spring:message code="aca.HT"/></font></th>
	    <th width="30"><font size="2" face="Arial Narrow, Arial"><spring:message code="aca.HP"/></font></th>
	    <th width="26"><font size="2" face="Arial Narrow, Arial"><spring:message code="aca.TH"/></font></th>
	    <th width="33"><font size="2" face="Arial Narrow, Arial"><spring:message code="aca.Nota"/></font></th>
	  </tr>
	</thead>
<%
		String ciclo 	= "0";
  		int row			= 0;
		for(MapaCurso curso : lisCursos){
			row++;
					
			if ( !ciclo.equals( curso.getCiclo() )){
				ciclo = curso.getCiclo();
%>
	  <tr> 
	    <td height="38" colspan='7' align="center"><b><spring:message code="aca.Semestre"/>: <%=ciclo%></b></td>
	  </tr>
<%			}  %>
	  <tr> 
	    <td align='center' width="117"> 
	      <input name="f_chekonline<%=row%>" type="checkbox" id="f_chekonline<%=row%>" value="S" <%=curso.getOnLine().equals("S")?"checked":""%> >
	      <input type="hidden" name="f_curso_id<%=row%>" value="<%=curso.getCursoId()%>"> 
		  <input type="hidden" name="f_online<%=row%>" value="<%=curso.getOnLine()%>">
		  <input type="hidden" name="f_nombreCurso" value="<%=curso.getNombreCurso()%>">
		  <input type="hidden" name="f_tipoCurso" value="<%=curso.getTipoCursoId()%>">
	    </td>
	    <td width="315"><font size="2">&nbsp;<%=curso.getNombreCurso()%></font></td>
	    <td align='Center' width="29"><b><font size="2"><%=curso.getCreditos()%></font></b></td>
	    <td align='Center' width="26"><font size="2"><%=curso.getHt()%></font></td>
	    <td align='Center' width="30"><font size="2"><%=curso.getHp()%></font></td>
	    <td align='Center' width="26"><b><font size="2"><%=Integer.parseInt(curso.getHt())+Integer.parseInt(curso.getHp())%></font></b></td>
	    <td align='Center' width="33"><font size="2"><%=curso.getNotaAprobatoria()%></font></td>
	  <tr> 
<%		} %>
	  <tr>
	    <td style="text-align:left;" colspan="7">
	      <input type="hidden" name="f_contador" value="<%=row%>">
		  <input type="hidden" name="planId" value="<%=planId%>">
		  <input type="hidden" name="facultad" value="<%=facultad%>">
	      <input class="btn btn-primary" name="Aceptar" type="submit" id="Aceptar" value="<spring:message code="aca.Actualizar"/>">      
	    </td>
	  </tr>
	</table>
	</form>
	</div>
</body>
<!-- fin de estructura -->