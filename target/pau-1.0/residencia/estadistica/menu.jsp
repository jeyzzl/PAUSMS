<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatModalidad"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>
<style>
	ol li{
		font-size: 14px;
		padding: 5px;
	}
</style>
<%
	String modalidades 		= (String) session.getAttribute("modalidadesReportes");
	if(modalidades == null){
		modalidades =  "'1'";
		session.setAttribute("modalidadesReportes", modalidades);
	}

	List<CatModalidad> lisModalidades	 = (List<CatModalidad>)request.getAttribute("lisModalidades");
	
	if(request.getParameter("CambiarModalidad") != null){
		modalidades = "";
		for(CatModalidad mod : lisModalidades){
			if( request.getParameter("mod-"+mod.getModalidadId()) != null ){
				modalidades += ""+mod.getModalidadId()+", ";
			}	
		}
		if(modalidades.length()>0){
			modalidades = modalidades.substring(0, modalidades.length()-2);
		}else{
			modalidades = "1";
		}
		
		session.setAttribute("modalidadesReportes", modalidades);
	}	
%>
<div class="container-fluid">	
	<h1>Reports</h1>
	<hr/>
	 <div class="row align-items-start">
 		<div class="col">
			<ol>
				<li><a href="estadisticas">Dormitory Students Statistics</a></li>
				<li><a href="dormiX">Dormitory students without assigned Dormitory</a></li>
				<li><a href="internos_tutor">Dormitory students by Mentor</a></li>
				<li><a href="internos_genero">Dormitory Students by Gender</a></li>
				<li><a href="internos">Dormitory students by School and Dormitory</a></li>
				<li><a href="externos">Off-campus Students by School and Reason</a></li>
				<li><a href="externos_tutor">Off-campus Students by Mentor</a></li>
				<li><a href="externos_genero">Off-campus Students by Gender</a></li>
				<li><a href="externos_sin_registrar">Unregistered Off-campus Students</a></li>
				<li><a href="tutor">Student's Tutor</a></li>
				<li><a href="direcciones">Student Visitation Report</a></li>
			</ol>   
		</div>				
		<div class="col alert alert-
		info">
	  	
	  		<form action="" method="post" name="forma">	
	  			<input type="hidden" name="CambiarModalidad" value="1" />	  		
		  		<h3><spring:message code="aca.Modalidades"/></h3>		  		
		  		<p>
			  		<a href="javascript:checaTodos();">All</a> | <a href="javascript:checaNinguno();"><spring:message code='aca.Ninguno'/></a>
			  	</p>		  		
		  		<p>
				  	<table>
						<%for(CatModalidad mod : lisModalidades){%>
							<tr>
								<td>
									<input <%if(modalidades.contains(""+mod.getModalidadId()+"")){out.print("checked");} %> type="checkbox" class="modalidades" id="mod-<%=mod.getModalidadId() %>" name="mod-<%=mod.getModalidadId() %>" value="<%=mod.getModalidadId() %>" />
									&nbsp;
									<%=mod.getNombreModalidad() %>
								</td>
							</tr>
						<%}%>
					</table>
				</p>
				<a href="javascript:document.forma.submit();" class="btn btn-primary"><i class="icon-ok icon-white"></i> Save</a>
			</form>			
		</div>		
	</div> 
</div>
<script>
	var mod = jQuery('.modalidades');

	function checaTodos(){
		mod.prop('checked', true);
	}
	
	function checaNinguno(){
		mod.prop('checked', false);
	}
</script>