<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.apFisica.spring.ApFisicaGrupo"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>


<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<html>
<body>
<%
  	String fecha      = request.getParameter("Fecha")==null?aca.util.Fecha.getHoy():request.getParameter("Fecha");
   
	List<ApFisicaGrupo> lisGrupos 			= (List<ApFisicaGrupo>) request.getAttribute("lisGrupos");    
	List<String> lisMaterias 				= (List<String>) request.getAttribute("lisMaterias");
	
    HashMap<String,String> mapaRegistrados 		= (HashMap<String,String>)request.getAttribute("mapaRegistrados");
    HashMap<String,String> mapaCupoMujeres 		= (HashMap<String,String>)request.getAttribute("mapaCupoMujeres");
    HashMap<String,String> mapaCupoHombres 		= (HashMap<String,String>)request.getAttribute("mapaCupoHombres");    
    HashMap<String,String> mapaMujeresEnCursos 	= (HashMap<String,String>)request.getAttribute("mapaMujeresEnCursos");
    HashMap<String,String> mapaHombresEnCursos 	= (HashMap<String,String>)request.getAttribute("mapaHombresEnCursos");   
%>
<div class="container-fluid">
    <h2>Grupos de Ejercicio</h2>
    <form name="forma" action="grupo">      
      <%
        int cont = 1;
      %>      
        <div class="alert alert-info d-flex align-items-center">
			<a href="agregar?Fecha=<%=fecha%>" class="btn btn-primary btn-sm"><i class="icon-white fas fa-plus"></i> Agregar</a>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input name="Fecha" type="text" id="Fecha" class="form-control" maxlength="10" data-date-format="dd/mm/yyyy" onfocus="focusFecha(this);" value="<%=fecha%>" style="width:120px">&nbsp;
			<a href="#" onclick="$(this).closest('form').submit()" class="btn btn-primary btn-sm"><i class="fas fa-filter icon-white"></i> Filtrar</a> 
        </div>     
        <table class="table table-bordered" id="table">
        <thead>
          <tr>
            <th width="1%">#</th>
            <th width="3%">Op.</th>           
            <th width="14%">Nombre Grupo</th>
            <th width="4%">Clave</th>
            <th width="4%">Cargas</th>
            <th width="10%">Lugar</th>
            <th width="10%">Instructor</th>
            <th width="2%">Cupo</th>
            <th width="2%">Al.</th>
            <th width="2%">Dis.</th>
            <th width="5%">F. Inicio</th>
            <th width="5%">F. Cierre</th>
            <th width="5%">F. Final</th>
            <th width="5%">Dia</th>
            <th width="5%">Hora</th>
            <th width="5%">Acceso</th>
            <th width="15%">Liga</th>
          </tr>
        </thead>
<%
	int  cupoTotal 		   = 0;
	int  registradosTotal  = 0;
	int  total 			   = 0;
	for( ApFisicaGrupo grupo : lisGrupos){		
    	String  status = "-";
        if (grupo.getAcceso().equals("T"))
        	status = "Todos";
        else if (grupo.getAcceso().equals("N"))
        	status = "Nuevos";     	
      	
      	String registrados = "0";
      	String colorRegistrados = "<span class='badge bg-secondary rounded-pill'>0</span>";
      	if (mapaRegistrados.containsKey(grupo.getGrupoId())){
      		registrados 		= mapaRegistrados.get(grupo.getGrupoId());
      		colorRegistrados 	= "<span class='badge bg-info rounded-pill'>"+registrados+"</span>";
      	}      	  
      	
      	int diferencia = Integer.parseInt(grupo.getCupo())-Integer.parseInt(registrados);
      	cupoTotal += Integer.parseInt(grupo.getCupo());
      	registradosTotal  += Integer.parseInt(registrados);
      	total = cupoTotal - registradosTotal;
      	
      	String dis = "0";
      	if(diferencia == 0){
      		dis = "<span class='badge bg-warning rounded-pill'>"+diferencia+"</span>";
      	}else{
      		dis = "<span class='badge bg-success rounded-pill'>"+diferencia+"</span>";
      	}
%>
          <tr>
            <td><%=cont++%></td>
            <td>
              <a href="agregar?GrupoId=<%= grupo.getGrupoId()%>&Nombre=<%=grupo.getNombreGrupo()%>&lugar=<%=grupo.getLugar()%>&CargaId=<%=grupo.getCargas()%>&Fecha=<%=fecha%>"><i class="fas fa-pencil-alt"></i></a>
		     <% if(registrados.equals("0")){%>         
              	<a href="javascript:Borrar('<%= grupo.getGrupoId()%>','<%=fecha%>');"><i class="fas fa-trash-alt"></i></a>
             <% } %>
            </td>
            <td><a href="lista?GrupoId=<%=grupo.getGrupoId()%>"><%= grupo.getNombreGrupo()%></a></td>
            <td><%= grupo.getClave()%></td>
            <td><%= grupo.getCargas()%></td>
            <td><%= grupo.getLugar()%></td>
            <td><%= grupo.getInstructor()%></td>
            <td><span class="badge bg-dark rounded-pill"><%=grupo.getCupo()%></span></td>
            <td><%=colorRegistrados%></td>
            <td><%=dis%></td>
            <td><%= grupo.getfInicio()%></td>
            <td><%= grupo.getfCierre()%></td>
            <td><%= grupo.getfFinal()%></td>
            <td><%= grupo.getDia1()%></td>
            <td><%= grupo.getHora()%></td>
            <td><%= status%></td>
            <td><%= grupo.getLiga()%></td>
          </tr>
        <%  
          }
        %>
         <tr class="table-secondary">
			<td colspan="7" class='text-center'><b>Totales</b></td>
			<td class='text-center'><b><%=cupoTotal%></b></td>
			<td class='text-center'><b><%=registradosTotal%></b></td>
			<td class='text-center'><b><%=total%></b></td>
			<td colspan="7">&nbsp;</td>
		</tr>
      </table>
      <table class="table table-bordered" id="table">
      <thead>
      <tr>
      	<th width="1%">#</th>  
      	<th width="10%">Materia</th>
      	<th width="10%" class="text-end">Cupo Mujeres</th>
      	<th width="10%" class="text-end">M.Insc.</th>
      	<th width="10%" class="text-end">M.Proceso</th>
      	<th width="10%" class="text-end">Cupo/Libre</th>
      	<th width="10%" class="text-end">Cupo Hombres</th>
      	<th width="10%" class="text-end">H.Insc.</th>
      	<th width="10%" class="text-end">H.Proceso</th>
      	<th width="10%" class="text-end">Cupo/Libre</th>
      </tr>
      </thead>
      <tbody>
<%
	int row=0;
	for (String materia : lisMaterias){
		row++;
		String cupoMujeres = "0";
		if (mapaCupoMujeres.containsKey(materia)){
			cupoMujeres = mapaCupoMujeres.get(materia);
		}
		
		String cupoHombres = "0";
		if (mapaCupoHombres.containsKey(materia)){
			cupoHombres = mapaCupoHombres.get(materia);
		}
		
		String mujeresInsc = "0";
		if (mapaMujeresEnCursos.containsKey(materia+"I")){
			mujeresInsc = mapaMujeresEnCursos.get(materia+"I");
		}
		
		String hombresInsc = "0";
		if (mapaHombresEnCursos.containsKey(materia+"I")){
			hombresInsc = mapaHombresEnCursos.get(materia+"I");
		}
		
		String mujeresProc = "0";
		if (mapaMujeresEnCursos.containsKey(materia+"M")){
			mujeresProc = mapaMujeresEnCursos.get(materia+"M");
		}
		
		String hombresProc = "0";
		if (mapaHombresEnCursos.containsKey(materia+"M")){
			hombresProc = mapaHombresEnCursos.get(materia+"M");
		}
		
		int resHombres = Integer.parseInt(cupoHombres) - Integer.parseInt(hombresInsc) - Integer.parseInt(hombresProc);
		int resMujeres = Integer.parseInt(cupoMujeres) - Integer.parseInt(mujeresInsc) - Integer.parseInt(mujeresProc);
%>  
	  <tr>
      	<td><%=row%></td>  
      	<td><%=materia%></td>
      	<td class="text-end"><span class="badge bg-dark rounded-pill"><%=cupoMujeres%></span></td>
      	<td class="text-end"><%=mujeresInsc%></td>
      	<td class="text-end"><%=mujeresProc%></td>
      	<td class="text-end"><%=resMujeres>=0?"<span class='badge bg-success rounded-pill'>"+resMujeres+"</span>":"<span class='badge bg-danger rounded-pill'>"+resMujeres+"</span>"%></td>
      	<td class="text-end"><span class="badge bg-dark rounded-pill"><%=cupoHombres%></span></td>
      	<td class="text-end"><%=hombresInsc%></td>
      	<td class="text-end"><%=hombresProc%></td>
      	<td class="text-end"><%=resHombres>=0?"<span class='badge bg-success rounded-pill'>"+resHombres+"</span>":"<span class='badge bg-danger rounded-pill'>"+resHombres+"</span>"%></td>
      </tr>  	
<% 	}%>
    </form>
</div>
</body>
<script>
    function Borrar(grupoId,fecha) {
      if (confirm("Si elimina este grupo eliminará toda la informacion que contenga el grupo") == true) {
        document.location.href = "borrar?GrupoId="+ grupoId+"&Fecha="+fecha;
      }
    }
    jQuery('#Fecha').datepicker();
</script>
</html>
