<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.acceso.spring.Acceso"%>
<%@page import="aca.vista.spring.Maestros"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<iframe name="downloadIframe" style="display:none;"></iframe>

<script type="text/javascript">
	function Refrescar() {
		document.frmUsuarios.submit();
	}

	function Grabar() {
		// Submit the form to the iframe for background download
		document.frmArchivo.target = "downloadIframe";
		document.frmArchivo.submit();

		// Delay the page refresh to ensure download starts
		setTimeout(function() {
			location.reload();
		}, 1500); // Adjust delay if necessary
	}

    function toggleButton() {
        const checkboxes = document.querySelectorAll('.form-check-input');
        const archivoButton = document.getElementById('archivoButton');
        const statusSelect = document.getElementById('Status');
        const anyChecked = Array.from(checkboxes).some(checkbox => checkbox.checked);
        const isNotSynced = statusSelect.value === 'N';
        archivoButton.style.display = anyChecked && isNotSynced ? 'inline-block' : 'none';
    }

    function selectAllCheckboxes(checked) {
        const checkboxes = document.querySelectorAll('.form-check-input');
        checkboxes.forEach(checkbox => {
            checkbox.checked = checked;
        });
        toggleButton();
    }

    window.onload = function() {
        const checkboxes = document.querySelectorAll('.form-check-input');
        checkboxes.forEach(checkbox => {
            checkbox.addEventListener('change', toggleButton);
        });

        const statusSelect = document.getElementById('Status');
        statusSelect.addEventListener('change', toggleButton);
        toggleButton();
    };
</script>
<%
	String codigoPersonal	= (String)session.getAttribute("codigoPersonal");
	String tipo 			= request.getParameter("Tipo")==null?"E":request.getParameter("Tipo");
    String status           = request.getParameter("Status")==null?"N":request.getParameter("Status");
	String mensaje 			= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	String admin 			= (String)request.getAttribute("admin");

    List<Maestros> lisMaestros 						= (List<Maestros>)request.getAttribute("lisMaestros");	
	List<AlumPersonal> lisAlumnos					= (List<AlumPersonal>)request.getAttribute("lisAlumnos");

    HashMap<String,Acceso> mapaAccesos 				= (HashMap<String,Acceso>)request.getAttribute("mapaAccesos");	
%>
<div class="container-fluid">
    <h2>Generate Sync File</h2>
    <form name="frmUsuarios" action="usuarios" method="post">
    <div class="alert alert-info d-flex align-items-center">
        <select id="Tipo" name="Tipo" class="form-select me-3" onchange="javascript:Refrescar();" style="width: 10rem;">
            <option value="E" <%=tipo.equals("E")?"selected":""%>>EMPLOYEES</option>
            <option value="A" <%=tipo.equals("A")?"selected":""%>>STUDENTS</option>
        </select>
        <label for="Status">Status:</label>
        <select id="Status" name="Status" class="form-select mx-1" onchange="javascript:Refrescar();" style="width: 10rem;">
            <option value="N" <%=status.equals("N")?"selected":""%>>NOT SYNCED</option>
            <option value="S" <%=status.equals("S")?"selected":""%>>SYNCED</option>
        </select>
<%      if(mensaje.equals("1")){%>
        Deleted
<%      }%>
    </div>
    </form>
    <form name="frmArchivo" action="generar" method="post">
    <button class="btn btn-primary mb-2" type="button" onclick="Grabar();" id="archivoButton" style="display: none;">Generate Sync File</button>
	<!-- Select All Button -->
<%  if(status.equals("N")){%>
    <button type="button" class="btn-sm btn-secondary mb-2" onclick="selectAllCheckboxes(true);" id="selectAllButton">Select All</button>
    <button type="button" class="btn-sm btn-secondary mb-2" onclick="selectAllCheckboxes(false);" id="deselectAllButton">Deselect All</button>
<%  }%>
    
    <table id="table" class="table table-bordered table-sm">
        <thead class="table-info">
            <tr>
                <th width="4%"></th>
                <th width="5%">#</th>
                <th width="7%">ID</th>
                <th width="20%">Name</th>
                <th>Email</th>
                <th>Sync</th>
            </tr>
        </thead>
        <tbody>
<%
	int row = 0;
	if (tipo.equals("E")){		
		for (Maestros maestro : lisMaestros){
            row++;
            Acceso acceso = new Acceso();
			if (mapaAccesos.containsKey(maestro.getCodigoPersonal())){
				acceso = mapaAccesos.get(maestro.getCodigoPersonal());
			}
%>
            <input type="hidden" name="TipoArchivo" value="E">
            <tr>
                <td>
<%          if(status.equals("N")){%>
                    <input class="form-check-input" type="checkbox" value="<%=maestro.getCodigoPersonal()%>" id="<%=maestro.getCodigoPersonal()%>" name="<%=maestro.getCodigoPersonal()%>">
<%          }else if(status.equals("S")){%>
                    <a href="eliminar?CodigoPersonal=<%=maestro.getCodigoPersonal()%>&Tipo=<%=tipo%>&Status=<%=status%>"><i class="fas fa-trash-alt text-danger"></i></a>
<%          }%> 
                </td>
                <td><%=row%></td>
                <td><%=maestro.getCodigoPersonal()%></td>
                <td><%=maestro.getNombre()%> <%=maestro.getApellidoMaterno()==null?"":maestro.getApellidoMaterno()%> <%=maestro.getApellidoPaterno()%></td>
                <td><%=maestro.getEmail()%></td>
                <td><%=acceso.getAlumnoMovil()%></td>
            </tr>
<%
		}			
	}else{
		for (AlumPersonal alumno : lisAlumnos){
			row++;
            Acceso acceso = new Acceso();
			if (mapaAccesos.containsKey(alumno.getCodigoPersonal())){
				acceso = mapaAccesos.get(alumno.getCodigoPersonal());
			}
%>
            <input type="hidden" name="TipoArchivo" value="A">
            <tr>
                <td>
<%          if(status.equals("N")){%>
                    <input class="form-check-input" type="checkbox" value="<%=alumno.getCodigoPersonal()%>" id="<%=alumno.getCodigoPersonal()%>" name="<%=alumno.getCodigoPersonal()%>">
<%          }else if(status.equals("S")){%>
                    <a href="eliminar?CodigoPersonal=<%=alumno.getCodigoPersonal()%>&Tipo=<%=tipo%>&Status=<%=status%>"><i class="fas fa-trash-alt text-danger"></i></a>
<%          }%> 
                </td>
                <td><%=row%></td>
                <td><%=alumno.getCodigoPersonal()%></td>
                <td><%=alumno.getNombre()%> <%=alumno.getApellidoMaterno()==null?"":alumno.getApellidoMaterno()%> <%=alumno.getApellidoPaterno()%></td>
                <td><%=alumno.getEmail()%></td>
                <td><%=acceso.getAlumnoMovil()%></td>
            </tr>
<%
		}
	}
%>
	    </tbody>
    </table>
    </form>
</div>