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
        <h2 class="section-title">My Unaccepted Posts</h2>
      </div>

      <c:forEach var="post" items="${unacceptedposts}">
        <div class="post-card">
          <div class="post-meta">
            <span><strong>${post.className}</strong></span>
            <span>by ${post.username}</span>
            <span>${post.datePosted}</span>
            <span class="badge">${post.status}</span>
          </div>
          <p>${post.description}</p>
          <div class="actions-row">
            <form action="editpost" method="get" style="display:inline;">
              <input type="hidden" name="postId" value="${post.postId}" />
              <button type="submit" class="btn-small btn-danger" >
              Edit
              </button>
            </form>
              
            <form action="deletepost" method="post" style="display:inline;">
              <input type="hidden" name="postId" value="${post.postId}" />
              <button type="submit" class="btn-small btn-danger" 
                    onclick="return confirm('Are you sure you want to delete this post?');">
              Delete
              </button>
            </form>
          </div>
        </div>
      </c:forEach>
    </div>
  </section>
  <section>
    <div class="section-card">
      <div class="section-header">
        <h2 class="section-title">My Info</h2>
        <span class="section-tag">Account</span>
      </div>

      <form class="form" action="edituser" method="post">
        <div class="form-row">
          <label>Username</label>
          <input name="username" value="${sessionScope.user.username}"  />
        </div>
        <div class="form-row">
          <label>Password</label>
          <input name="password" value="${sessionScope.user.password}"  />
        </div>
        <div class="form-row">
          <label>First Name</label>
          <input name="firstName" value="${sessionScope.user.firstName}" />
        </div>
        <div class="form-row">
          <label>Last Name</label>
          <input name="lastName" value="${sessionScope.user.lastName}" />
        </div>
        <div class="form-row">
          <label>Email</label>
          <input name="email" value="${sessionScope.user.email}" />
        </div>
        <div class="form-row">
          <label>Major</label>
          <input name="major" value="${sessionScope.user.major}" />
        </div>
        <div class="form-row">
      <label for="year">Year</label>
      <select id="year" name="year">
        <option value="${sessionScope.user.schoolYear}">Select year</option>
        <option value="1">Freshman</option>
        <option value="2">Sophomore</option>
        <option value="3">Junior</option>
        <option value="4">Senior</option>
        <option value="5">Graduate</option>
      </select>
    </div>
        <button type="submit" class="btn-primary">Save Changes</button>
        <button type="submit" class="btn-primary" formaction = "deleteuser"
                    onclick="return confirm('Are you sure you want to delete your account?');">
              Delete Account
            </button>
      </form>
      
    </div>
  </section>
  <section>
    <div class="section-card">
      <div class="section-header">
        <h2 class="section-title">My Accepted Posts</h2>
      </div>

      <c:forEach var="post" items="${acceptedposts}">
        <div class="post-card">
          <div class="post-meta">
            <span><strong>${post.className}</strong></span>
            <span>by ${post.username}</span>
            <span>${post.datePosted}</span>
            <span class="badge">${post.status}</span>
          </div>
          <p>${post.description}</p>
          <div class="actions-row">
            
            <form action="message" method="get" style="display:inline;">
              <input type="hidden" name="receiverName" value="${post.accepterName}" />
              <button type="submit" class="btn-small btn-danger" >
              Message ${post.accepterName}
              </button>
            </form>
            <form action="editpost" method="get" style="display:inline;">
              <input type="hidden" name="postId" value="${post.postId}" />
              <button type="submit" class="btn-small btn-danger" >
              Edit
              </button>
            </form>

            <form action="deletepost" method="post" style="display:inline;">
              <input type="hidden" name="postId" value="${post.postId}" />
              <button type="submit" class="btn-small btn-danger" 
                    onclick="return confirm('Are you sure you want to delete this post?');">
              Delete
              </button>
            </form>

          </div>
        </div>
      </c:forEach>


    </div>
  </section>
<section>
    <div class="section-card">
      <div class="section-header">
        <h2 class="section-title">Posts I Accepted</h2>
      </div>

      <c:forEach var="post" items="${postsacceptedbyuser}">
        <div class="post-card">
          <div class="post-meta">
            <span><strong>${post.className}</strong></span>
            <span>by ${post.username}</span>
            <span>${post.datePosted}</span>
            <span class="badge">${post.status}</span>
          </div>
          <p>${post.description}</p>
          <div class="actions-row">
            <form action="unaccept" method="post" style="display:inline;">
              <input type="hidden" name="postId" value="${post.postId}" />
              <button type="submit" class="btn-small btn-danger" 
                    onclick="return confirm('Are you sure you want to unaccept?');">
              Unaccept
              </button>
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
    </div>
  </section>
  
</div>

<footer>StudyBuddy – Profile</footer>
</body>
</html>
