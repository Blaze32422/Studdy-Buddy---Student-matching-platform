<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>StudyBuddy – Register</title>
  <link rel="stylesheet" href="css/styles.css"/>
</head>
<body>
<%@ include file="jspf/header.jspf" %>

<div class="section-card">
  <div class="section-header">
    <h2 class="section-title">Create an Account</h2>
    <span class="section-tag">Join StudyBuddy</span>
  </div>

  <form class="form" action="register" method="post">
    <div class="form-row">
      <label for="username">Username</label>
      <input id="username" name="username" required />
    </div>

    <div class="form-row">
      <label for="password">Password</label>
      <input id="password" name="password" type="password" required />
    </div>

    <div class="form-row">
      <label for="firstName">First Name</label>
      <input id="firstName" name="firstName" required />
    </div>

    <div class="form-row">
      <label for="lastName">Last Name</label>
      <input id="lastName" name="lastName" required />
    </div>

    <div class="form-row">
      <label for="email">Email</label>
      <input id="email" name="email" type="email" required />
    </div>

    <div class="form-row">
      <label for="major">Major</label>
      <input id="major" name="major" />
    </div>

    <div class="form-row">
      <label for="year">Year</label>
      <select id="year" name="year">
        <option value="">Select year</option>
        <option value="1">Freshman</option>
        <option value="2">Sophomore</option>
        <option value="3">Junior</option>
        <option value="4">Senior</option>
        <option value="5">Graduate</option>
      </select>
    </div>

    <button type="submit" class="btn-primary">Create Account</button>
  </form>
</div>

<footer>StudyBuddy – Register</footer>
</div>
</body>
</html>
