<%-- 
    Document   : testColapseTable
    Created on : 14/05/2014, 05:49:18 PM
    Author     : Daniel
--%>
<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<div class="container-fluid">  
    <div class="accordion" id="accordion2">  
        <div class="accordion-group">  
            <div class="accordion-heading">  
                <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapseOne">  
                    Click me to exapand. Click me again to collapse. Part I.  
                </a>  
            </div>  
            <div id="collapseOne" class="accordion-body collapse" style="height: 0px; ">  
                <div class="accordion-inner">  
                    Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.  
                </div>  
            </div>  
        </div>  
        <div class="accordion-group">  
            <div class="accordion-heading">  
                <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapseTwo">  
                    Click me to exapand. Click me again to collapse. Part II.  
                </a>  
            </div>  
            <div id="collapseTwo" class="accordion-body collapse">  
                <div class="accordion-inner">  
                    Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.  
                </div>  
            </div>  
        </div>  
        <div class="accordion-group">  
            <div class="accordion-heading">  
                <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapseThree">  
                    Click me to exapand. Click me again to collapse. Part III.  
                </a>  
            </div>  
            <div id="collapseThree" class="accordion-body collapse">  
                <div class="accordion-inner">  
                    Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.  
                </div>  
            </div>  
        </div>  
    </div>  
</div>  
<script type="text/javascript" src="../css-js//twitter-bootstrap/twitter-bootstrap-v2/docs/assets/js/jquery.js"></script>  
<script type="text/javascript" src="../css-js/twitter-bootstrap/twitter-bootstrap-v2/docs/assets/js/bootstrap-collapse.js"></script>  
        

<%@ include file= "../../cierra_enoc.jsp" %>