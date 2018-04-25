<%--
  Created by IntelliJ IDEA.
  User: Félix
  Date: 25/04/2018
  Time: 12:05
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
        pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<display:table id="row" name="volumes" requestURI="${requestURI}"
               pagesize="5">

    <acme:column code="volume.title" value="${row.title}"/>
    <acme:column code="volume.description" value="${row.description}"/>
    <acme:column code="volume.year" value="${row.year}"/>

    <display:column >
        <spring:message code="newsPapers.public" var="publics"/> <jstl:out value="${newsPapers}" />
    </display:column>

    <display:column >
            <acme:button url="newsPaper/display.do?newsPaperId=${row.id}" code="newsPaper.display"/>
    </display:column>

</display:table>

<input type="button" value="<spring:message code="customer.cancel" /> " onclick="goBack()">
<script>
    function goBack() {
        window.history.back()
    }
</script>
