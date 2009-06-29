<%@ page import="gov.nih.nci.evs.browser.utils.*" %>
<script type="text/javascript" src="<%= request.getContextPath() %>/js/utils.js"></script>
<%
  // Session Attribute(s):
  String message = HTTPUtils.getSessionAttributeString(request, "message", true);
  
  // Member variable(s):
  String basePath = request.getContextPath();
%>
<div class="texttitle-blue">Change Request:</div><br/>
<%
  if (message != null && message.length() > 0) {
%>
    <div class="msgColor">
<%      
    String[] list = Utils.toStrings(message, "\n", false, false);
    for (int i=0; i<list.length; ++i) {
      String text = list[i];
      text = Utils.toHtml(text); // For leading spaces (indentation)
%>
      <%=text%><br/>
<%
    }
%>
   </div><br/>
<%
  }
%>
Do you want to suggest:
<br/>
<br/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="group1" onclick="go('<%=basePath%>/pages/new_concept.jsf')">A new concept, or
<br/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="group1" onclick="go('<%=basePath%>/pages/modify_concept.jsp')">A modification to a concept
<br/>
