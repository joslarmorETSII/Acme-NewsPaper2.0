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

<form:form action="/everyone/search.do" modelAttribute="search">
	<form:input path="keyword"/>
	<acme:submit name="search" code="master.page.search"/>
</form:form>
<br/>
<br/>
<fieldset>
<display:table name="articles" id="row" pagesize="5" class="displaytag" requestURI="${requestURI}">

	<acme:column code="article.title" value="${row.title}" />
	<spring:message var="moment" code="article.moment"/>
	<spring:message var="formatDate" code="event.format.date"/>
	<display:column property="moment" title="${moment}" format="${formatDate}" sortable="true" />

	<acme:column code="article.summary" value="${row.summary}" sortable="true"/>
	<acme:column code="article.finalMode" value="${row.finalMode}"/>

	<security:authorize access="hasRole('ADMINISTRATOR')" >
		<display:column>
			<acme:button url="article/administrator/edit.do?articleId=${row.id}" code="article.delete" />
		</display:column>
	</security:authorize>

	<security:authorize access="isAuthenticated()" >
		<display:column>
			<acme:button url="article/display.do?articleId=${row.id}" code="article.display" />
		</display:column>
	</security:authorize>
</display:table>
</fieldset>
<br/>
<fieldset>
<display:table name="newsPapers" id="newspaper" pagesize="10" class="displaytag" requestURI="${requestUri}">

	<acme:column code="newsPaper.publisher" value="${newspaper.publisher.name} " />
	<acme:column code="newsPaper.title" value="${newspaper.title}"/>
	<acme:column code="newsPaper.description" value="${newspaper.description}"/>
	<acme:column code="newsPaper.picture" value="${newspaper.picture}"/>
	<spring:message var="publicationDate" code="newsPaper.publicationDate"/>
	<spring:message var="formatDate2" code="event.format.date"/>
	<display:column property="publicationDate" title="${publicationDate}" format="${formatDate2}" sortable="true" />

	<display:column >
		<acme:button url="newsPaper/display.do?newsPaperId=${newspaper.id}" code="newsPaper.display"/>
	</display:column>

</display:table>
</fieldset>


