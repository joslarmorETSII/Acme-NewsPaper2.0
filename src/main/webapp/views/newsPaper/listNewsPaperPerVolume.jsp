<%--
  Created by IntelliJ IDEA.
  User: Félix
  Date: 3/04/2018
  Time: 18:51
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
<%@taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

    <display:table id="newspaper" name="newsPapers" requestURI="${requestURI}" pagesize="5">

        <acme:column code="newsPaper.publisher" value="${newspaper.publisher.name} " />
        <acme:column code="newsPaper.title" value="${newspaper.title}"/>

        <security:authorize access="hasRole('USER')" >
        <display:column>
            <jstl:if test="${volumeContieneNewspaper eq true or newspaper.publisher eq user}">
                <acme:button url="newsPaper/display.do?newsPaperId=${newspaper.id}" code="newsPaper.display"/>
            </jstl:if>
        </display:column>
        </security:authorize>

        <security:authorize access="hasRole('CUSTOMER')" >
        <display:column>
                <acme:button url="newsPaper/customer/display.do?newsPaperId=${newspaper.id}" code="newsPaper.display"/>
        </display:column>
        </security:authorize>
    </display:table>

<input type="button" value="<spring:message code="newsPaper.cancel" /> " onclick="goBack()">
<script>
    function goBack() {
        window.history.back()
    }
</script>