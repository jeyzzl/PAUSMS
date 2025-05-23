<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.plan.spring.MapaCurso"%>
<%@ page import= "aca.carga.spring.Carga"%>
<%@ page import= "aca.catalogo.spring.CatTipoCurso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="cursoUtil"  class="aca.plan.CursoUtil" scope="page"/>
<jsp:useBean id="tipoUtil"  class="aca.catalogo.TipoCursoUtil" scope="page"/>
<jsp:useBean id="PreUtil"  class="aca.plan.PrerrequisitoUtil" scope="page"/>


<%
	String cargaId 			= (String)request.getAttribute("cargaId");
	String planId			= request.getParameter("planId");
	String facultad			= request.getParameter("facultad");	    
	
	String creditos			= "";
	String ht				= "";
	String hp				= "";
	String hi				= "";
	String version 			= "";	
	String nuevoCursoId 	= "";
	
	List<MapaCurso> lisCursos					= (List<MapaCurso>) request.getAttribute("lisCursos");
	List<Carga> lisCargas						= (List<Carga>) request.getAttribute("lisCargas");
	HashMap<String,MapaCurso> mapaCursos		= (HashMap<String,MapaCurso>) request.getAttribute("mapaCursos");
	HashMap<String,CatTipoCurso> mapaTipos		= (HashMap<String,CatTipoCurso>) request.getAttribute("mapaTipos");
	HashMap<String,String> mapaMaestros			= (HashMap<String,String>) request.getAttribute("mapaMaestros");
	
	// Lista maestros
	List<String> lisMaestros  			= (List<String>) request.getAttribute("lisMaestros");	
%>

<div class="container-fluid">

    <h2><spring:message code="aca.ListadoMateriasPlan"/> <%=planId%></h2>
    
    <div class="alert alert-info">
    	<a class="btn btn-primary" href="listado?facultad=<%=facultad %>"><spring:message code='aca.Regresar'/></a>
    </div>
    
    <form action="" name="forma">
    	<input type="hidden" name="planId" value="<%=planId %>" />
    	<input type="hidden" name="facultad" value="<%=facultad %>" />
    
		<table class="table table-bordered table-sm">
			 <thead>
				 <tr class="table-info"> 
				   <th>#</th>
				   <th><spring:message code="aca.Ciclo"/></th>
				   <th><spring:message code="aca.Clave"/></th>
				   <th><spring:message code="aca.Nombre"/></th>
				   <th  class="text-center"><spring:message code='aca.Creditos'/></th>
				   <th class="text-center"><spring:message code="aca.HT"/></th>
				   <th class="text-center"><spring:message code="aca.HP"/></th>
				   <th class="text-center"><spring:message code="aca.HI"/></th>
				   <th class="text-center"><spring:message code="aca.TH"/></th>
				   <th>
				   		<spring:message code="aca.Maestros"/>
				   		<select name="cargaId" id="cargaId" style="float:right;" onchange="document.forma.submit();">
				   			<%for(Carga carga : lisCargas){ %>
				   				<option value="<%=carga.getCargaId() %>" <%if(carga.getCargaId().equals(cargaId)){out.print("selected");} %>><%=carga.getCargaId() %> | <%=carga.getNombreCarga() %></option>
				   			<%} %>
				   		</select>
				   	</th>
				   <th>Subject type</th>
				 </tr>
			</thead>
			<%
				int cont = 0;
				for (MapaCurso mcurso : lisCursos){
					cont++;
					int  nCiclo = Integer.parseInt(mcurso.getCiclo());
						
					if(mcurso.getCreditos()!=null){
						creditos = mcurso.getCreditos();
					}
					if(mcurso.getHt()!=null){
						ht = mcurso.getHt();
					}
					if(mcurso.getHp()!=null){
						hp = mcurso.getHp();
					}
					
					if(mcurso.getHi()!=null){
						hi = mcurso.getHi();
					}
					String strMaestros = "&nbsp;";
					for(String str : lisMaestros){
						String cursoId 	= str.split("@@")[0];
						String maestro 	= str.split("@@")[1];
						String origen 	= str.split("@@")[2].equals("O") ? "<span class='label label-success'>Base</span>" : "<span class='label'>Compartido</span>";
						
						String maestroNombre = "-"; 
						if (mapaMaestros.containsKey(maestro)){
							maestroNombre = mapaMaestros.get(maestro);
						}
						if(cursoId.equals(mcurso.getCursoId())){
							strMaestros +=  maestro + " | " + maestroNombre + " <span style='float:right;'>" + origen + "</span> <br>";
						}
					}
					
					String tipoNombre = "-";
					if (mapaTipos.containsKey(mcurso.getTipoCursoId())){
						tipoNombre = mapaTipos.get(mcurso.getTipoCursoId()).getNombreTipoCurso();
					}
					
			%>
				 	<tr> 
				    	<td><%=cont%></td>
				    	<td><%=nCiclo%></td>
				    	<td title="<%=mcurso.getCursoId()%>"><%=mcurso.getCursoClave() %></td>
				    	<td><%=mcurso.getNombreCurso()%></td>
				    	<td align="center"><%=creditos%></td>
				    	<td align="center"><%=ht%></td>
				    	<td align="center"><%=hp%></td>
				    	<td align="center"><%=hi%></td>
				    	<td align="center"><%=Integer.parseInt(hi)+Integer.parseInt(ht)+Integer.parseInt(hp)%></td>
				    	<td><%=strMaestros %></td>
				    	<td><%=tipoNombre%></td>
				  	</tr>
			<%	
						 creditos			= "";
						 ht					= "";
						 hp					= "";
					}	
			%>
		</table>
	</form>
 </div> 