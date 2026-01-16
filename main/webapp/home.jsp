<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>StudyBuddy – Home</title>
  <link rel="stylesheet" href="css/styles.css"/>
</head>
<body>
<%@ include file="jspf/header.jspf" %>

<div class="main-grid">
  <section>
    <div class="section-card">
      <div class="section-header">
        <h2 class="section-title">Study Requests</h2>
        <span class="section-tag">Browse &amp; accept</span>
      </div>
      <p style="margin-bottom:.6rem;">
        Find classmates who need help or are looking to collaborate.
      </p>

      <c:forEach var="post" items="${requestScope.posts}">
        <div class="post-card">
          <div class="post-meta">
            <span><strong>${post.className}</strong></span>
            <span>by ${post.username}</span>
            <span>${post.datePosted}</span>
            <span class="badge">${post.status}</span>
          </div>
          <p>${post.description}</p>
          <div class="actions-row">
            <form action="acceptance" method="post">
              <input type="hidden" name="postId" value="${post.postId}" />
              <input type="hidden" name="acceptance" value="true" />
              <button type="submit" class="btn-small">Accept Request</button>
            </form>
            <form action="message" method="get" style="display:inline;">
              <input type="hidden" name="receiverName" value="${post.username}" />
              <button type="submit" class="btn-small btn-danger" >
              Message ${post.username}
              </button>
            </form>
          </div>
        </div>
      </c:forEach>

      <c:if test="${empty requestScope.posts}">
        <p>No study requests yet. Be the first to post!</p>
      </c:if>
    </div>
  </section>

    <div class="section-card">
      <div class="section-header">
        <h2 class="section-title">Create a Request</h2>
        <span class="section-tag">Quick action</span>
      </div>
      <p style="margin-bottom:.6rem;">
        Need help with a class? Create a post so others can find you.
      </p>
      <button class="btn-primary" onclick="window.location.href='createpost.jsp'">
        New Study Request
      </button>
    </div>
  </aside>
</div>

<footer>StudyBuddy – Home</footer>
</body>
</html>
