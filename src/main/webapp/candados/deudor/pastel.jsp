<html>
<head>
<title>Generador de gráficas</title>
<!--
This file retrieved from the JS-Examples archives
http://www.js-examples.com
100s of free ready to use scripts, tutorials, forums.
Author: Grigory - http://mpp.by.ru/ 
-->
<p style="margin: 0 auto;"><b>Generador de gráficas
<script type="text/javascript"></script>
/**
 *
 * class Point
 *
 */

function Point(pos) {

 this.setColor       = setColor;
 this.getColor       = getColor;
 this.setHeight      = setHeight;
 this.getHeight      = getHeight;
 this.setWidth       = setWidth;
 this.getWidth       = getWidth;
 this.setTop         = setTop;
 this.getTop         = getTop;
 this.setLeft        = setLeft;
 this.getLeft        = getLeft;
 this.setCoordinates = setCoordinates;
 this.getCoordinates = getCoordinates;
 this.getHTML        = getHTML;
 this.setCenter      = setCenter;
 this.getCenter      = getCenter;

 function setLeft(newLeft) {
  this.left = newLeft;
 }

 function getLeft() {
  return this.left;
 }

 function setTop(newTop) {
  this.top = newTop;
 }

 function getTop() {
  return this.top;
 }

 function setColor(newColor) {
  this.color = newColor;
 }

 function getColor() {
  return this.color;
 }

 function setHeight(newHeight) {
  this.height = newHeight;
 }

 function getHeight() {
  return this.height;
 }


 function setWidth(newWidth) {
  this.width = newWidth;
 }

 function getWidth() {
  return this.width;
 }

 function setCenter(isCenter) {
  this.center = isCenter==true;
 }

 function getCenter() {
  return this.center;
 }

 function setCoordinates(newCoordinates) {
  this.coordinates = newCoordinates;
  with(this) {
   setLeft ( Math.floor (getCoordinates()[0]) );
   setTop  ( Math.floor (getCoordinates()[1]) );
  }
 }

 function getCoordinates() {
  return this.coordinates;
 }


 function getHTML() {

  with (this)
   return "<div style='position:absolute;background-Color:" + getColor() +
    ";left:" +  (getCenter() ? getLeft() - Math.floor(getWidth() /2) : getLeft()) + 
    "px;top:" + (getCenter() ? getTop()  - Math.floor(getHeight()/2) : getTop()) + 
    "px;width:" + getWidth() + "px;height:" + getHeight() + "px;font-size:0px'></div>";
 }

 with(this) {
  if(arguments.length>0) {
   setLeft ( typeof(pos)=="object"? pos[0] : arguments[0] );
   setTop  ( typeof(pos)=="object"? pos[1] : arguments[1] );
  }
  setColor ("black");
  setHeight(1);
  setWidth (1);
  setCenter (false);
 }

}
   </script>
<script type="text/javascript" style="language:jScript;">
/**
 *
 * class Line
 *
 */

function Line() {


 this.getStyleHTML   = getStyleHTML;
 this.setColor       = setColor;
 this.getColor       = getColor;
 this.getHTML        = getHTML;
 this.setCoordinates = setCoordinates;
 this.getCoordinates = getCoordinates;
 this.setBounds      = setBounds;
 this.getBounds      = getBounds;
 this.setLeft        = setLeft;
 this.getLeft        = getLeft;
 this.setTop         = setTop;
 this.getTop         = getTop;
 this.setHeight      = setHeight;
 this.getHeight      = getHeight;
 this.setWidth       = setWidth ;
 this.getWidth       = getWidth ;
 this.setLineWidth   = setLineWidth;
 this.getLineWidth   = getLineWidth;
 this.getDistance    = getDistance;

 this.pt = new Point();


 function setLeft(newLeft) {
  this.left = newLeft;
 }

 function getLeft() {
  return this.left;
 }

 function setTop(newTop) {
  this.top = newTop;
 }

 function getTop() {
  return this.top;
 }

 function setHeight(newHeight) {
  this.height = newHeight;
 }

 function getHeight() {
  return this.height;
 }

 function setWidth(newWidth) {
  this.width = newWidth;
 }

 function getWidth() {
  return this.width;
 }

 function getStyleHTML() {
  with(this)
   return 'style=\"position:absolute' +
   ';left:' + getLeft() +
   'px;top:' + getTop() +
   'px;width:' + getWidth() +
   'px;height:' + getHeight() +
   'px;background-color:' + getColor() +
   ';font-size:0px\"';
 }


 function setColor(newColor) {
  this.color = newColor;
 }

 function getColor() {
  return this.color;
 }


 function getDistance(begin,end) {
  var dx = end[0] - begin[0];
  var dy = end[1] - begin[1];
  with(Math)
   return sqrt(pow(dx,2) + pow(dy,2));
 }


 function setLineWidth(newWidth) {
  this.lineWidth = newWidth;
  this.pt.setWidth  (this.getLineWidth());
  this.pt.setHeight (this.getLineWidth());
 }

 function getLineWidth() {
  return this.lineWidth;
 }


 function setBounds (beginX, beginY, endX, endY) {
  setCoordinates ([[beginX,beginY],[endX,endY]]);
 }

 function getBounds () {
  return this.getCoorditanes();
 }


 function setCoordinates(newCoordinates) {
  this.coordinates = newCoordinates;
 }

 function getCoordinates() {
  return this.coordinates;
 }


 function getHTML() {

 // it use a function y = kx + b; [x0,y0]-[x1,y1]
  with(this) {
   x0 = getCoordinates()[0][0];
   y0 = getCoordinates()[0][1];
   x1 = getCoordinates()[1][0];
   y1 = getCoordinates()[1][1];
  }
  var k = (y1 - y0) / (x1 - x0);
  var b = y1 - k * x1;

  with(this)
   var sb = ["<div " + getStyleHTML() + " >"];

  this.pt.setColor(this.getColor());


  if(x0==x1) 
   with(this.pt) {
    setHeight (Math.abs(y1 - y0));
    setTop    (Math.min (y0, y1));
    setLeft   (Math.min (x0, x1));
    sb.push (getHTML());
   }
  else if(y0==y1)
   with(this.pt) {
    setWidth (Math.abs (x1 - x0));
    setTop   (Math.min (y0,y1));
    setLeft  (Math.min (x0, x1));
    sb.push (getHTML());
   }
  else {
   step = Math.abs(k)>1 ? Math.abs(1/k) : 1;
   for(var x=Math.min(x0,x1); x<Math.max(x0,x1); x=x+step) {
    y = k*x + b;
    this.pt.setCoordinates([x,y]);
    sb.push(this.pt.getHTML())
   }
  }

  sb.push ("</div");

  return sb.join("");
 }

 with (this) {
  setLineWidth(1);
  setColor ("black");
 }

}
   </script>
<script type="text/javascript" style="language:jScript;">
/**
 *
 *  class Chart
 *
 */

function Chart() {

 this.getWidth       = getWidth;
 this.setWidth       = setWidth;
 this.getHeight      = getHeight;
 this.setHeight      = setHeight;
 this.visualize      = visualize;
 this.isVisualized   = isVisualized;
 this.getHTML        = getHTML;
 this.applyTo        = applyTo;
 this.setLineWidth   = setLineWidth;
 this.getLineWidth   = getLineWidth;
 this.setLineColor   = setLineColor;
 this.getLineColor   = getLineColor;
 this.setPointSize   = setPointSize;
 this.getPointSize   = getPointSize;
 this.redraw         = redraw;
 this.setChartType   = setChartType;
 this.getChartType   = getChartType;
 this.setValues      = setValues;
 this.getValues      = getValues;
 this.setGridEnabled = setGridEnabled;
 this.getGridEnabled = getGridEnabled;
 this.setGridLayout  = setGridLayout;
 this.getGridLayout  = getGridLayout;
 this.getGridHTML    = getGridHTML;
 this.setGridColor   = setGridColor;
 this.getGridColor   = getGridColor;
 this.setRange       = setRange;
 this.getRange       = getRange;

 this.self = null;

 function setLineColor(newColor) {
  this.lineColor = newColor;
  this.redraw();
 }

 function getLineColor() {
  return this.lineColor;
 }

 function applyTo(parentHTMLObject) {
  this.id = parentHTMLObject.id + "_chart";
  parentHTMLObject.innerHTML += this.getHTML();
  this.visualize();
 }

 function isVisualized() {
  return this.self != null;
 }

 function getWidth() {
  return this.width;
 }

 function setWidth(newWidth) {
  this.width = newWidth;
  if(this.isVisualized()) {
   this.self.firstChild.style.width  = this.getWidth();
  }
  this.redraw();
 }

 function getHeight() {
  return this.height;
 }

 function setHeight(newHeight) {
  this.height = newHeight;
  if(this.isVisualized()) {
   this.self.firstChild.style.height = this.getHeight();
  }
  this.redraw();
 }


 function setPointSize(newSize) {
  this.pointSize = newSize;
  this.redraw();
 }

 function getPointSize() {
  return this.pointSize;
 }


 function setRange(newRange) {
  this.range = newRange;
  this.redraw();
 }

 function getRange() {
  return this.range;
 }

 function setGridColor(newColor) {
  this.gridColor = newColor;
  this.redraw();
 }

 function getGridColor() {
  return this.gridColor;
 }


 function getGridHTML() {

  var str = "";
  var lineX = new Line();
  var lineY = new Line();

  lineX.setColor (this.getGridColor());
  lineY.setColor (this.getGridColor());

  with(this) {
   dx = getWidth () / getGridLayout()[0];
   dy = getHeight() / getGridLayout()[1];

   for(var i=0; i<getGridLayout()[0]; i++) {
    x = dx*i;
    lineX.setCoordinates([[x,0],[x,getHeight()]]); 
    lineX.getHTML();
    str += lineX.getHTML();
   }

   for(var i=0; i<getGridLayout()[1]; i++) {
    y = dy*i;
    lineY.setCoordinates([[0,y],[getWidth(),y]]); 
    str += lineY.getHTML();
   }
  }

  return str;
 }


 function setGridLayout(newGridLayout) {
  this.gridLayout = newGridLayout;
  this.redraw();
 }

 function getGridLayout() {
  return this.gridLayout;
 }

 function setGridEnabled(isEnabled) {
  this.gridEnabled = isEnabled==true;
  this.redraw();
 }

 function getGridEnabled() {
  return this.gridEnabled;
 }

 function visualize() {
  this.self  = document.getElementById(this.id);
  self.owner = this;
  this.contentWindow = this.self.firstChild.contentWindow;
  this.contentWindow.owner = this;
  this.contentWindow.onload = new Function ("this.owner.map = this.document.documentElement.lastChild; this.owner.redraw()");
 }

 function setChartType(newType) {
  this.chartType = newType;
  this.redraw();
 }

 function getChartType() {
  return this.chartType;
 }

 function setValues(newValues) {
  this.values = newValues;
  this.redraw();
 }

 function getValues() {
  return this.values;
 }

 function setLineWidth(newLineWidth) {
  this.lineWidth = newLineWidth;
  this.redraw();
 }

 function getLineWidth() {
  return this.lineWidth;
 }

 function redraw() {
  with(this)

   if(isVisualized()) {

    map.topMargin  = 0;
    map.leftMargin = 0;

    var sb = [this.getGridHTML()];

    var ln = new Line();
    var pt = new Point();
    var bx = - getRange()[0][0];
    var by = - getRange()[1][0];

    var kx = getWidth()  / ( getRange()[0][1] - getRange()[0][0] );
    var ky = getHeight() / ( getRange()[1][1] - getRange()[1][0] );

    var pset = [];

    var x;
    var y;
    for(var i=0; i<getValues().length; i++) {
     x = Math.floor ( (getValues()[i][0] + bx ) * kx );
     y = Math.floor ( getHeight() - ( getValues()[i][1] + by ) * ky );
     pset.push([x,y]);
    }

    switch(getChartType()) {

     case Chart.Scatter ://      this.window.alert("this is Scatter");
      pt.setCenter(true);
      pt.setColor(getLineColor());
      for(var i=0; i<pset.length; i++) {
       x0 = pset[i][0];
       y0 = pset[i][1];
       pt.setCoordinates([x0, y0]);
       pt.setWidth  (getLineWidth());
       pt.setHeight (getLineWidth());
       sb.push(pt.getHTML());
      }
      break;

     case Chart.Polyline  ://      this.window.alert("this is Polyline");
      ln.setColor(getLineColor());
      ln.setLineWidth(getLineWidth());
      for(var i=0; i<pset.length; i++) {
       x0 = pset[i][0];
       y0 = pset[i][1];
       x1 = i==pset.length-1 ? x0 : pset[i+1][0];
       y1 = i==pset.length-1 ? y0 : pset[i+1][1];

       ln.setCoordinates([[x0,y0],[x1,y1]]);
       sb.push (ln.getHTML());
      }
      break;

     case Chart.Polygone ://      this.window.alert("this is Polygone");
      pt.setWidth  (getPointSize());
      pt.setHeight (getPointSize());
      pt.setCenter (true);

      for(var i=0; i<pset.length; i++) {
       x0 = pset[i][0];
       y0 = pset[i][1];
       x1 = x0;
       y1 = getHeight();

       ln.setCoordinates([[x0,y0],[x1,y1]]);
      //pt.setCoordinates([x0,y0]);
       sb.push (ln.getHTML());
      }
      break;


     case Chart.Gisto  ://      this.window.alert("Gistogram");
      var dx = (getWidth()-getLineWidth()) / pset.length;

      ln.setLineWidth (getLineWidth());

      for(var i=0; i<pset.length; i++) {
       x0 = i*dx;
       y0 = getHeight();
       x1 = x0;
       y1 = pset[i][1];

       ln.setColor (getLineColor());
       ln.setCoordinates([[x0,y0],[x1,y1]]);
       sb.push (ln.getHTML());
      }

      break;


     default : alert("unknown " + getChartType());

    }

    map.innerHTML = sb.join("");

   }
 }

 function getHTML() {
  with(this)
   return "<div id=" + id + " ><iframe disabled frameborder=yes scrolling=no style='width:" + getWidth() + "px;height:" + getHeight() + "px;'></iframe></div>";
 }

// presettings
 with(this) {
  setChartType(Chart.Polygone);
  setValues([]);
  setGridLayout([10,4]);
  setGridColor("silver");
  setLineWidth (3);
  setWidth(400);
  setHeight(400);
  setRange([[0,200],[-100,100]]);
  setPointSize(10);
  setLineColor("black");
  this.window = window;
 }

}

Chart.Scatter  = 0;
Chart.Gisto    = 1;
Chart.Polyline = 2;
Chart.Polygone = 3;
   </script>
</head>

</b>
<body>

</p>
<table style="border:1; cellSpacing:-1; cellPdding:-1;">
  <tr>
    <td>How to use it:</td>
    <td style="text-align:center">Preview Chart component</td>
  </tr>
  <tr>
    <td style="vertical-align:top">
    <pre>&lt; HTML &gt;

 &lt; HEAD &gt;
  &lt; script type=jScript src=&quot;Point.js&quot;&gt; &lt; /SCRIPT &gt;
  &lt; script type=jScript src=&quot;Line.js&quot; &gt; &lt; /SCRIPT &gt;

  &lt; script type=jScript src=&quot;Chart.js&quot;&gt; &lt; /SCRIPT &gt;
 &lt; /HEAD &gt;
 &lt; BODY &gt;
  &lt; ELEMENTTAGNAME id=myPlace&gt; &lt;/ ELEMENTTAGNAME&gt;

  &lt; script type=jScript&gt;
var ch = new Chart();
with(ch) {
 setChartType(<select onchange="if(this.options[this.selectedIndex].text=='Chart.Gisto') alert('use setLineWidth()'); ch.setChartType(eval(this.options[this.selectedIndex].text));"><option selected>Chart.Scatter</option><option>Chart.Polyline</option><option>Chart.Polygone</option><option>Chart.Gisto</option></select>);
 setValues(<select onchange="ch.setValues(ValuesSet[this.selectedIndex])"><option selected>set 0</option><option>set 1</option><option>Set 2</option></select>);
 setGridLayout([<input type="text" id="gr_ly0" size="1" value="10" onchange="ch.setGridLayout([parseInt(this.value),parseInt(gr_ly1.value)])">,<input id="gr_ly1" type="text" size="1" value="4" onchange="ch.setGridLayout([parseInt(gr_ly0.value),parseInt(this.value)])">]);
 setGridColor(<select onchange="ch.setGridColor(this.options[this.selectedIndex].text);"><option selected>silver</option><option>black</option><option>navy</option><option>green</option></select>);
 setLineWidth (<input type="text" value="3" size="1" onchange="ch.setLineWidth(parseInt(this.value));">);
 setLineColor(<select onchange="ch.setLineColor(this.options[this.selectedIndex].text);"><option selected>black</option><option>silver</option><option>red</option><option>navy</option><option>green</option></select>);
 setWidth(<input type="text" size="2" value="400" onchange="ch.setWidth(parseInt(this.value));">);
 setHeight(<input type="text" size="2" onchange="ch.setHeight(parseInt(this.value));" value="400">);
 setRange([[<input type="text" size="2" value="0" onchange="ch.range[0][0]=parseInt(this.value);ch.redraw();">,<input type="text" size="2" value="200" onchange="ch.range[0][1]=parseInt(this.value);ch.redraw();">],[<input type="text" size="2" value="-100" onchange="ch.range[1][0]=parseInt(this.value);ch.redraw();">,<input type="text" size="2" value="100" onchange="ch.range[1][1]=parseInt(this.value);ch.redraw();">]]);

 applyTo(document.getElementById(&quot;myPlace&quot;));
}
  &lt; SCRIPT &gt;

 &lt; /BODY &gt;
&lt; /HTML &gt;</pre>
    </td>
    <td style="vertical-align:top" id="ChartPlace"></td>
  </tr>
  <tr>
    <td></td>

  </tr>
</table>
<script type="text/javascript" style="language:jScript;">

   ValuesSet = [];

   ValuesSet[0] = [];
   for(var i=0; i<200; i++) {
    ValuesSet[0].push ([i,100 * Math.sin (i/40)]);
    i= i + 20;
   }

   ValuesSet[1] = [];
   for(var i=0; i<200; i++) {
    ValuesSet[1].push ([i, 100-i]);
    i= i + 20;
   }

   ValuesSet[2] = [];
   for(var i=0; i<200; i++) {
    ValuesSet[2].push ([i, 0.5*i]);
    i= i + 20;
   }


   var ch = new Chart();
   ch.setValues (ValuesSet[0]);
   ch.setChartType (Chart.Scatter);
   ch.applyTo(document.getElementById("ChartPlace"));
  </script>
  
</body>

</html>