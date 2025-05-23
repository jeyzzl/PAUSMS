<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.mentores.spring.MentAcceso"%>
<%@ page import="aca.catalogo.spring.CatFacultad"%>
<%@ page import="aca.catalogo.spring.CatCarrera"%>
<%@ page import="aca.plan.spring.MapaPlan"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
	function GrabaPrivilegios(){		
		document.frmPrivilegios.submit();
	}
</script>
<%	
	String strReferente		= request.getParameter("Codigo");
	MentAcceso mentAcceso 	= (MentAcceso) request.getAttribute("mentAcceso");
	String empleadoNombre	= (String) request.getAttribute("empleadoNombre");
	
	String strFac			= "X";	
	String strCheck			= "";		
	String strAcceso		= mentAcceso.getAcceso()==null?"X":mentAcceso.getAcceso();	
	int i=0, numAccion=0;
	
	List<MapaPlan> lisPlanes					= (List<MapaPlan>)request.getAttribute("lisPlanes");
	List<CatCarrera> lisCarreras				= (List<CatCarrera>) request.getAttribute("lisCarreras");
	HashMap<String,CatFacultad> mapaFacultades	= (HashMap<String,CatFacultad>) request.getAttribute("mapaFacultades");	
%>
<div class="container-fluid">
	<h2>Mentor Referee Privileges <small class="text-muted fs-4">( <%=empleadoNombre%> )</small></h2>
	<form name="frmPrivilegios" action="grabarPrivilegios" method="post" >
	<Input type="hidden" name="Codigo" value="<%=strReferente%>">	  	
  	<div class="alert alert-info">  		
		<a class="btn btn-primary" href="referente"><i class="fas fa-arrow-left"></i></a>
	</div>
	<table id="table" class="table table-sm table-bordered">
      	
<%	
	int row=0;
	for(CatCarrera carr : lisCarreras){			
		row++;
		if (strAcceso.indexOf(carr.getCarreraId())!=-1){
			strCheck = "Checked";			
		}else{ 
			strCheck = "";
		}	
		if (!carr.getFacultadId().equals(strFac)){ 
			strFac = carr.getFacultadId();
			String facultadNombre = "-";
			if (mapaFacultades.containsKey(strFac)){
				facultadNombre = mapaFacultades.get(strFac).getNombreFacultad();
			}
%>	
	<thead class="table-info">	 
	  	<tr><td colspan="6" align="center"><%=facultadNombre%></td></tr>
  	</thead>
<% 		} %>
  	<tr>
    	<td align="center"><input type="checkbox" name="Check<%=row%>" value="S" <%=strCheck%>></td>
    	<td align="center"><%=carr.getCarreraId()%></td>
    	 <td><%=carr.getNombreCarrera()%></td>
		<td>
		<%
				for (MapaPlan plan : lisPlanes){
					if ( plan.getCarreraId().equals(carr.getCarreraId())){
						String estilo = "<span class='badge bg-secondary rounded-pill'>"+plan.getPlanId()+"</span>";
						if (plan.getEstado().equals("A")) estilo = "<span class='badge bg-info rounded-pill'>"+plan.getPlanId()+"</span>";
						if (plan.getEstado().equals("V")) estilo = "<span class='badge bg-success rounded-pill'>"+plan.getPlanId()+"</span>";
						if (plan.getEstado().equals("I")) estilo = "<span class='badge bg-warning rounded-pill'>"+plan.getPlanId()+"</span>";
%>									
						<span style="text-decoration:none;"><%=estilo%></span>&nbsp;&nbsp;&nbsp;
		<%			}
				}
		%>  		 
  	</tr>    
<%	}	%>
  	<tr>
    	<td colspan="3" align="center">
	    	<a href="javascript:GrabaPrivilegios()" class="btn btn-primary">Save</a>
	    </td>
  	</tr>
	</table>
	</form>
</div>