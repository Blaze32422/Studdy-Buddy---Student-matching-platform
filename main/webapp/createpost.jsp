<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>StudyBuddy – Create Post</title>
  <link rel="stylesheet" href="css/styles.css"/>
</head>
<body>
<%@ include file="jspf/header.jspf" %>

<div class="section-card">
  <div class="section-header">
    <h2 class="section-title">New Study Request</h2>
    <span class="section-tag">Create post</span>
  </div>

  <form class="form" action="createpost" method="post">
    <div class="form-row">
      <label for="className">Class</label>
      <input id="className" name="className" placeholder="e.g. CS 201 – Data Structures" required />
    </div>

    <div class="form-row">
      <label for="description">What do you need help with?</label>
      <textarea id="description" name="description" required
                placeholder="Topics, upcoming exam, assignments, etc."></textarea>
    </div>


    <button type="submit" class="btn-primary">Post Request</button>
  </form>
</div>

<footer>StudyBuddy – Create Post</footer>
</div>
</body>
</html>
