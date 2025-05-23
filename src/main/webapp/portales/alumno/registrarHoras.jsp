<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%> 
<%@page import="aca.trabajo.spring.TrabAlum"%>
<%@page import="aca.trabajo.spring.TrabInformeAlum"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%@ include file= "menu.jsp" %>
<%
	String matricula 			= (String) session.getAttribute("codigoAlumno");
	String nombreAlumno 		= (String) request.getAttribute("nombreAlumno");
    String fecha                = (String) request.getAttribute("fecha");

    TrabAlum trabAlum                           = (TrabAlum) request.getAttribute("trabAlum");
    TrabInformeAlum trabInformeAlum             = (TrabInformeAlum) request.getAttribute("trabInformeAlum");

    HashMap<String,String> mapaNombreDepartamentos = (HashMap<String,String>) request.getAttribute("mapaNombreDepartamentos");
    HashMap<String,String> mapaNombreCategorias = (HashMap<String,String>) request.getAttribute("mapaNombreCategorias");
    HashMap<String,String> mapaNombrePeriodos = (HashMap<String,String>) request.getAttribute("mapaNombrePeriodos");
%>
<script type = "text/javascript">	
	function Grabar(){
		if(document.frmConcluir.Descripcion.value!="" && document.frmConcluir.Descripcion.value.length >= 10){			
			document.frmConcluir.submit();
		}else{
			alert("You must type a description.");
		}
	}
</script>
<body>
<div class="container-fluid">
	<h3>
		Hours Report <small class="text-muted fs-6">( <%=matricula%> - <%=nombreAlumno%> )</small>
	</h3>
    <div class="alert alert-info">
        <a href="workline" class="btn btn-primary">Return</a>
    </div>
    <form id="frmHoras" name="frmHoras" action="grabarHoras" method="post">
    <div class="form-group">
        <input type="hidden" name="InformeId" id="InformeId" value="<%=trabInformeAlum.getInformeId()%>">
        <input type="hidden" name="DeptId" id="DeptId" value="<%=trabAlum.getDeptId()%>">
        <input type="hidden" name="CatId" id="CatId" value="<%=trabAlum.getCatId()%>">
        <input type="hidden" name="PeriodoId" id="PeriodoId" value="<%=trabAlum.getPeriodoId()%>">
        <input type="hidden" name="HoraInicio" id="HoraInicio" value="<%=trabInformeAlum.getHoraInicio()%>">

        <label for="Fecha" class="form-label">Date</label>
        <input type="text" name="Fecha" id="Fecha" value="<%=trabInformeAlum.getFecha().substring(0,10)%>" class="form-control mb-3" style="width: 15rem;" readonly>
<%  if(trabInformeAlum.getStatus().equals("A")){%>
        <label for="Horas" class="form-label">Start Time</label>
        <input type="text" name="Horas" id="Horas" value="<%=trabInformeAlum.getHoraInicio()%>" class="form-control mb-3" style="width: 15rem;" readonly>
<%  }%>
<%  if(trabInformeAlum.getStatus().equals("P")){%>
        <button type="submit" class="btn btn-primary">Start Hours</button>
<%  }%>
<%  if(trabInformeAlum.getStatus().equals("S")){%>
        Wait for Supervisor to authorize your report.
<%  }%>
    </div>
    </form>
    <form id="frmConcluir" name="frmConcluir" action="concluir" method="post">
        <input type="hidden" name="InformeId" id="InformeId" value="<%=trabInformeAlum.getInformeId()%>">
<%  if(trabInformeAlum.getStatus().equals("A")){%>
        <label for="Descripcion">Description:</label>
        <textarea class="form-control" id="Descripcion" name="Descripcion" rows="3" style="width: 25rem;" maxlength="280"><%=trabInformeAlum.getDescripcion()==null?"":trabInformeAlum.getDescripcion()%></textarea>
        <span class="mb-4" id="charCount">0</span>/280
        <br><br>
        <a href="javascript:Grabar()" class="btn btn-danger">Stop Hours</a>
<%  }%>
    </form>
</div>
<script>
    // Character counter for description textarea
    $(document).ready(function() {
        const textarea = $('#Descripcion');
        const charCount = $('#charCount');
        const maxLength = 280;
        
        // Update counter on page load (in case there's existing text)
        charCount.text(textarea.val().length);
        
        // Update counter as user types
        textarea.on('input', function() {
            const currentLength = $(this).val().length;
            charCount.text(currentLength);
            
            // Optional: change color when approaching limit
            if (currentLength > maxLength * 0.9) {
                charCount.css('color', 'red');
            } else {
                charCount.css('color', '');
            }
        });
    });
</script>
</body>