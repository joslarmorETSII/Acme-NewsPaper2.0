<%--
 * edit.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
		pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>




<h3><b><spring:message code="followUp.title"/>:&nbsp;</b><jstl:out value="${followUp.title}"/></h3>

<jstl:if test="${pageContext.response.locale.language == 'es'}">

		<b><spring:message code="followUp.moment"/>:&nbsp;</b><jstl:out value="${momentEs}" />
	<br/>
</jstl:if>

<jstl:if test="${pageContext.response.locale.language == 'en'}">
	<b><spring:message code="followUp.moment"/>:&nbsp;</b><jstl:out value="${momentEn}" />
	<br/>
</jstl:if>

<b><spring:message code="followUp.summary"/>:&nbsp;</b><jstl:out value="${followUp.summary}"/>
<br/>

<b><spring:message code="followUp.text"/>:&nbsp;</b><jstl:out value="${followUp.text}"/>
<br/>

<b><spring:message code="followUp.pictures"/>:&nbsp;</b><jstl:out value="${followUp.pictures}"/>
<br/>

<br>
<input type="button" name="cancel" value="<spring:message code="followUp.cancel" />"
	   onclick="javascript: relativeRedir('followUp/list.do?articleId=${followUp.article.id}');" />