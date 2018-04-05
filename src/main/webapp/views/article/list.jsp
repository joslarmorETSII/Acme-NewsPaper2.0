<%--
 * action-1.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<jstl:if test="${pageContext.response.locale.language == 'es' }">
	<jstl:set value="{0,date,dd/MM/yyyy HH:mm}" var="formatDate"/>
</jstl:if>

<jstl:if test="${pageContext.response.locale.language == 'en' }">
	<jstl:set value="{0,date,yyyy/MM/dd HH:mm}" var="formatDate"/>
</jstl:if>

<display:table name="articles" id="row" pagesize="5" class="displaytag" requestURI="${requestURI}">

	<acme:column code="article.title" value="${row.title}" />

	<spring:message var="moment" code="article.moment"/>
	<spring:message var="formatDate" code="event.format.date"/>
	<display:column property="moment" title="${moment}" format="${formatDate}" sortable="true" />

	<acme:column code="article.summary" value="${row.summary}" sortable="true"/>
	<acme:column code="article.finalMode" value="${row.finalMode}"/>

	<security:authorize access="hasRole('USER')" >
		<acme:columnButton url="article/user/edit.do?articleId=${row.id}" codeButton="article.edit" />
	</security:authorize>

	<security:authorize access="hasRole('ADMINISTRATOR')" >
		<acme:columnButton url="article/administrator/edit.do?articleId=${row.id}" codeButton="article.delete" />
	</security:authorize>

	<security:authorize access="isAuthenticated()" >
		<acme:columnButton url="article/display.do?articleId=${row.id}" codeButton="article.display" />
	</security:authorize>

</display:table>






