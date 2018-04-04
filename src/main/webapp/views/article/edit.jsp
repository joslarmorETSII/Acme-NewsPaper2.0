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

<form:form action="article/user/edit.do" modelAttribute="article" >

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="newsPaper" />
	<form:hidden path="followUps" />

	<acme:textbox path="title" code="article.title"/>
	<acme:textbox path="moment" code="article.moment" />
	<acme:textbox path="summary" code="article.summary" />
	<acme:textarea path="body" code="article.body" />
	<acme:textbox path="picture" code="article.picture"/>
	<acme:checkbox path="finalMode" code="article.finalMode"/>


	<acme:submit name="save" code="article.save"/>
	<acme:submit name="delete" code="article.delete"/>
	<acme:cancel code="article.cancel" url="welcome/index.do"/>

</form:form>





