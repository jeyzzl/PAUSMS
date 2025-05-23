<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.plan.spring.MapaCurso"%>
<%@ page import= "aca.plan.spring.MapaCursoPre"%>
<%@ page import= "aca.catalogo.spring.CatTipoCurso"%>

<jsp:useBean id="cursoUtil"  class="aca.plan.CursoUtil" scope="page"/>
<jsp:useBean id="tipoUtil"  class="aca.catalogo.TipoCursoUtil" scope="page"/>
<jsp:useBean id="PreUtil"  class="aca.plan.PrerrequisitoUtil" scope="page"/>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
	function Borrar(cursoId ){
		var planId = document.frmPlan.planId.value;
		if(confirm("Estas seguro de eliminar el registro: "+cursoId)==true){
	  		document.location.href="accion.jsp?Accion=4&cursoId="+cursoId+"&planId="+planId;
	  	}
	}
</script>
<%
	String planId			= request.getParameter("planId");
	String facultad			= request.getParameter("facultad");	
	    
	String creditos			= "";
	String ht				= "";
	String hp				= "";
	String fCreada			= "";
	String notaAprobatoria 	= "";
	String estado			= "";
	
	String version 			= "";
	String versionPlan		= (String) request.getAttribute("versionPlan");
	String nuevoCursoId 	= "";
	
	int  nCiclo				= 0;
	int cont				= 0;
	String sBgcolor	= "";
	String nombreCarga				= (String)request.getAttribute("nombreCarga");
		
	List<MapaCurso> lisCurso				= (List<MapaCurso>)request.getAttribute("lisCursos");	
	HashMap<String,CatTipoCurso> mapaTipos	= (HashMap<String,CatTipoCurso>)request.getAttribute("mapaTipos");
%>
<div class="container-fluid">
	<h3><spring:message code="aca.ListadoMateriasPlan"/>:&nbsp;<small class="text-muted fs-5">(<%=planId%> - <%=nombreCarga%>)</small>&nbsp;</h3>
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary" href="listado?facultad=<%=facultad%>"><spring:message code='aca.Regresar'/></a>
		&nbsp; &nbsp;
		<input type="text" class="form-control search-query" placeholder="Search..." id="buscar" style="width:200px;">
		&nbsp;&nbsp;
		<a href="../../mapa/nuevos/muestraPlanPdf.jsp?planId=<%=planId%>&versionId=<%=versionPlan%>"><% if( planId.contains("2010")){%>[ PDF ]<% }%></a>
	</div>	
  	<form name="frmPlan">
  		<input name="planId" type="hidden" value="<%=planId%>">
 	</form>					
	<table id="table" class="table table-bordered table-sm">	  	
	<thead class="table-info">
		<tr> 
	   		<th width="2%"><h5><spring:message code="aca.Numero"/></h5></th>
	    	<th width="4%"><h5><spring:message code="aca.Ciclo"/></h5></th>
	    	<th width="9%"><h5>Subject ID</h5></th>
	    	<th width="27%"><h5><spring:message code="aca.Nombre"/></h5></th>					    
	    	<th width="5%"><h5><spring:message code='aca.Creditos'/></h5></th>				    
	    	<th width="5%"><h5><spring:message code="aca.Nota"/></h5></th>					    
		    <th width="10%"><h5><spring:message code="aca.TipoCurso"/></h5></th>
		</tr>
	</thead>
	<tbody>
<%
	for (int i=0; i< lisCurso.size(); i++){	
		
		MapaCurso mcurso = (MapaCurso) lisCurso.get(i);
		
		nCiclo = Integer.parseInt(mcurso.getCiclo());
		
		cont++;
		
		if(mcurso.getCreditos()!=null){
			creditos = mcurso.getCreditos();
		}
		if(mcurso.getHt()!=null){
			ht = mcurso.getHt();
		}
		if(mcurso.getHp()!=null){
			hp = mcurso.getHp();
		}
		if(mcurso.getfCreada()!=null){
			fCreada	= mcurso.getfCreada();
		}
		if(mcurso.getNotaAprobatoria()!=null){
			notaAprobatoria = mcurso.getNotaAprobatoria();
		}
		if(mcurso.getEstado()!=null){
			estado = mcurso.getEstado();
		}
		String tipoNombre = "-";
		if (mapaTipos.containsKey(mcurso.getTipoCursoId())){
			tipoNombre = mapaTipos.get(mcurso.getTipoCursoId()).getNombreTipoCurso();
		}
%>
		<tr> 
	    	<td align="center"><%=cont%></td>
	    	<td align="center"><%=nCiclo%></td>
	    	<td><%=mcurso.getCursoId()%></td>
	    	<td><a href="lista?idCurso=<%=mcurso.getCursoId()%>&cursoClave=<%=mcurso.getCursoClave() %>&ciclo=<%=nCiclo%>&planId=<%=planId%>&facultad=<%=facultad%>"><%=mcurso.getNombreCurso()%></a></td>			    
	    	<td align="center"><%=creditos%></td>		    
		    <td align="center"><%=notaAprobatoria%></td>		    
		    <td align="center"><%=tipoNombre%></td>
	  	</tr>
<%	
		 creditos			= "";
		 ht					= "";
		 hp					= "";
		 fCreada			= "";
		 notaAprobatoria 	= "";
		 estado				= "";
	}	
%>
	</tbody>
	</table>		
</div>
<script src="../../js/search.js"></script>
<script>
	$('#buscar').search();
</script>