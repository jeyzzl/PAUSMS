<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<body>
<div class="container-fluid">
<h1>Clínicas</h1>
<div class="alert alert-info"></div>
<table style="width:30%" class="table table-condensed">
  <tr class="button" onMouseOut=this.style.backgroundColor='' style='cursor:pointer;' onClick="document.location.href='lista.jsp?Area=hospital'"><td>Hospital</td></tr>
  <tr class="button" onMouseOut=this.style.backgroundColor='' style='cursor:pointer;' onClick="document.location.href='lista.jsp?Area=vidasana'"><td>Vida Sana</td></tr>
  <tr class="button" onMouseOut=this.style.backgroundColor='' style='cursor:pointer;' onClick="document.location.href='lista.jsp?Area=dental'"><td>Dental</td></tr>
  <tr class="button" onMouseOut=this.style.backgroundColor='' style='cursor:pointer;' onClick="document.location.href='lista.jsp?Area=vision'"><td>Instituto de la Visión</td></tr>
</table>
</div>
</body>