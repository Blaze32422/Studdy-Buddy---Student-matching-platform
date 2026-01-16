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
  
    <div class="section-card">
      <div class="section-header">
        <h2 class="section-title">
          Chat with ${requestScope.receiverName}
        </h2>
        <span class="section-tag">Direct messages</span>
      </div>

      <div style="max-height:280px; overflow-y:auto; margin-bottom:0.7rem;">
        <c:forEach var="msg" items="${requestScope.messages}">
          <div class="message-card">
            <div class="post-meta">
              <span>${msg.senderUsername}</span>
              <span>${msg.timeSent}</span>
            </div>
            <p>${msg.message}</p>
          </div>
        </c:forEach>

        <c:if test="${empty requestScope.messages}">
          <p>No messages yet. Say hi!</p>
        </c:if>
      </div>

      <form class="form" action="message" method="post">
        <input type="hidden" name="receiverId" value="${requestScope.receiverId}" />
        
        <div class="form-row">
          <label for="message">Your message</label>
          <textarea id="message" name="message" required></textarea>
        </div>
        <button type="submit" class="btn-primary">Send</button>
      </form>
    </div>
  
</div>

<footer>StudyBuddy – Messages</footer>
</div>
</body>
</html>
