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

<jstl:if test="${pageContext.response.locale.language == 'es' }">
    <jstl:set value="{0,date,dd/MM/yyyy}" var="formatDate"/>
</jstl:if>

<jstl:if test="${pageContext.response.locale.language == 'en' }">
    <jstl:set value="{0,date,yyyy/MM/dd}" var="formatDate"/>
</jstl:if>


    <display:table id="newspaper" name="newsPapers" requestURI="${requestURI}"
                   pagesize="5">

        <acme:column code="newspaper.customer" value="${newspaper.customer}" />
        <acme:column code="newspaper.description" value="${newspaper.description}" />
        <acme:column code="newspaper.picture" value="${newspaper.picture}"/>
        <acme:column code="newspaper.published" value="${newspaper.published}"/>
        <acme:column code="newspaper.taboo" value="${newspaper.taboo}"/>

        <spring:message var="publicationDate" code="newspaper.publicationDate"/>
        <spring:message var="formatDate" code="event.format.date"/>
        <display:column property="publicationDate" title="${publicationDate}" format="${formatDate}" sortable="true" />

    </display:table>
</jstl:if>

<display:table id="newspaper" name="newsPapersPrivate" requestURI="${requestURI}"
               pagesize="5">

    <acme:column code="newspaper.customer" value="${newspaper.customer}" />
    <acme:column code="newspaper.description" value="${newspaper.description}" />
    <acme:column code="newspaper.picture" value="${newspaper.picture}"/>
    <acme:column code="newspaper.published" value="${newspaper.published}"/>
    <acme:column code="newspaper.taboo" value="${newspaper.taboo}"/>

    <spring:message var="publicationDate" code="newspaper.publicationDate"/>
    <spring:message var="formatDate" code="event.format.date"/>
    <display:column property="publicationDate" title="${publicationDate}" format="${formatDate}" sortable="true" />

</display:table>

<input type="button" value="<spring:message code="customer.cancel" /> " onclick="goBack()">
<script>
    function goBack() {
        window.history.back()
    }
</script>