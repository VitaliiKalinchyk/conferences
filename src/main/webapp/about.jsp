<%@ page contentType="text/html;charset=UTF-8"%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Conference Smart App</title>
</head>

<body>
<menu>
    <strong>
        Conference Smart App
    </strong>
    <a href="controller?action=default">Main</a>
    <a href="controller?action=about">About us</a>
    <a href="controller?action=contacts">Contacts</a>
    <% if (session != null && session.getAttribute("user") != null) out.print("<a href=\"controller?action=profile\">Profile</a>"); %>
    <% if (session == null || session.getAttribute("user") == null) out.print("<a href=\"controller?action=sign-in-page\">Sign In</a>"); %>
    <% if (session == null || session.getAttribute("user") == null) out.print("<a href=\"controller?action=sign-up-page\">Signup</a>"); %>
    <% if (session != null && session.getAttribute("user") != null) out.print("<a href=\"controller?action=sign-out\">Sign Out</a>"); %>
    change language here
</menu>
<header>About us – Conference Smart App</header>
<p>
    Some info here
</p>
<footer>
    <p>
        2022 © Conference Smart App
    </p>
</footer>
</body>
</html>