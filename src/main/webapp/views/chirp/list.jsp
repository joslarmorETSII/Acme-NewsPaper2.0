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

<display:table name="chirps" id="row" pagesize="5" class="displaytag" requestURI="${requestURI}">

	<security:authorize access="hasRole('USER')" >
		<display:column>
		<jstl:if test="${row.posted eq false}" >
			<acme:button url="chirp/user/edit.do?chirpId=${row.id}" code="chirp.edit" />
		</jstl:if>
		</display:column>
	</security:authorize>

	<security:authorize access="hasRole('USER')">
		<display:column >
			<jstl:if test="${ row.posted ne true  && row.taboo eq false}">
				<acme:button url="chirp/user/post.do?chirpId=${row.id}" code="chirp.post"/>
			</jstl:if>
			<jstl:if test="${row.posted eq true}">
				<spring:message code="chirp.publicado" var="publicado"/> <jstl:out value="${publicado}" />
			</jstl:if>
		</display:column>
	</security:authorize>

	<acme:column code="chirp.title" value="${row.title}" />

	<spring:message var="moment" code="chirp.moment"/>
	<spring:message var="formatDate" code="event.format.date"/>
	<display:column property="moment" title="${moment}" format="${formatDate}" sortable="true" />

	<acme:column code="chirp.description" value="${row.description}" sortable="true"/>


	<security:authorize access="hasRole('ADMINISTRATOR')" >
		<display:column>
			<acme:button url="chirp/administrator/edit.do?chirpId=${row.id}" code="chirp.delete" />
		</display:column>
	</security:authorize>

	<security:authorize access="isAuthenticated()" >
		<display:column>
			<acme:button url="chirp/user/display.do?chirpId=${row.id}" code="chirp.display" />
		</display:column>
	</security:authorize>

</display:table>

<security:authorize access="hasRole('USER')">
	<acme:button code="chirp.create" url="chirp/user/create.do"/>
</security:authorize>

<h1><b><spring:message code="chirp.following.user"/>
</b></h1>
<display:table name="chirpsFollowing" id="row" pagesize="5" class="displaytag" requestURI="${requestURI}">
	<acme:column code="chirp.user" value="${row.user.name}" />
	<acme:column code="chirp.title" value="${row.title}" />
	<display:column>
		<acme:button url="chirp/user/display.do?chirpId=${row.id}" code="chirp.display" />
	</display:column>
</display:table>





