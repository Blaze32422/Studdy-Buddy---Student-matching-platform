<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>StudyBuddy – Messages</title>
  <link rel="stylesheet" href="css/styles.css"/>
</head>
<body>
<%@ include file="jspf/header.jspf" %>

<div class="main-grid">
  <!-- LEFT: Conversation list -->
  <section>
    <div class="section-card">
      <div class="section-header">
        <h2 class="section-title">Conversations</h2>
        
      </div>

      <c:forEach var="receiver" items="${requestScope.receivers}">
        <form action="message" method="get" style="margin-bottom:0.5rem;">
          <input type="hidden" name="receiverName" value="${receiver}" />
          <button type="submit" class="btn-primary">
            ${receiver}
          </button>
        </form>
      </c:forEach>

    </div>
    <aside>
  <div class="section-card">
    <div class="section-header">
      <h3 class="section-title">Start a New Conversation</h3>
    </div>
    
    <form action="message" method="get">
      <div class="form-row">
        <label for="receiverName">Username:</label>
        <input type="text" id="receiverName" name="receiverName" required placeholder="Enter username"/>
      </div>
      <div style="margin-top: 0.5rem;">
        <button type="submit" class="btn-primary">Start Chat</button>
      </div>
      
    </form>

    <c:if test="${not empty requestScope.error}">
      <p style="color:red">${requestScope.error}</p>
    </c:if>
  </div>
</aside>
  </section>

 
</div>

<footer>StudyBuddy – Messages</footer>
</div>
</body>
</html>
