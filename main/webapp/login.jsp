<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>StudyBuddy – Login</title>
  <link rel="stylesheet" href="css/styles.css"/>
</head>
<body>
<%@ include file="jspf/header.jspf" %>

<div class="section-card">
  <div class="section-header">
    <h2 class="section-title">Login</h2>
    <span class="section-tag">Access your account</span>
  </div>

  <c:if test="${not empty requestScope.error}">
    <p style="color:#f97373; font-size:0.85rem; margin-bottom:0.5rem;">
      ${requestScope.error}
    </p>
  </c:if>

  <form class="form" action="login" method="post">
    <div class="form-row">
      <label for="email">Email</label>
      <input type="text" id="email" name="email" required />
    </div>

    <div class="form-row">
      <label for="password">Password</label>
      <input type="password" id="password" name="password" required />
    </div>

    <button type="submit" class="btn-primary">Log In</button>
  </form>

  <p style="margin-top:0.8rem; font-size:0.84rem;">
    Don’t have an account?
    <a href="register.jsp" style="text-decoration:underline;">Register here</a>.
  </p>
</div>

<footer>StudyBuddy – Login</footer>
</body>
</html>
