<%--
  Created by IntelliJ IDEA.
  User: Félix
  Date: 06/04/2018
  Time: 16:55
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
        pageEncoding="ISO-8859-1" %>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="advertisement/agent/edit.do" modelAttribute="advertisement" >

    <form:hidden path="id"/>
    <form:hidden path="version"/>
    <form:hidden path="agent"/>


    <acme:textbox path="title" code="advertisement.title"/>
    <br />

    <acme:textbox path="banner" code="advertisement.banner"/>
    <br />

    <acme:textbox path="targetPage" code="advertisement.targetPage"/>
    <br />

    <acme:textbox path="taboo" code="advertisement.taboo"/>
    <br />

    <form:label path="newsPapers">
        <spring:message code="advertisement.newsPapers" />
    </form:label>
    <form:select path="newsPapers" items="${newsPapers}" itemLabel="title"/>
    <form:errors path="newsPapers" cssClass="error" />


    <input type="submit" name="save" value="<spring:message code="advertisement.save"/>" />

    <input type="button" name="cancel" value="<spring:message code="advertisement.cancel" />"
           onclick="javascript: relativeRedir('welcome/index.do');" />

</form:form>
