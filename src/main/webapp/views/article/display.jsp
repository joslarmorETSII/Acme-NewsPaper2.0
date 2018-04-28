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


<jstl:if test="${advertisement ne null}">
	<img src="${advertisement.banner}" width="500px" height="100%"  />
	<br/>
</jstl:if>


<h3><b><spring:message code="article.title"/>:&nbsp;</b><jstl:out value="${article.title}"/></h3>

<jstl:if test="${pageContext.response.locale.language == 'es'}">

	<b><spring:message code="article.moment"/>:&nbsp;</b><jstl:out value="${momentEs}" />
	<br/>
</jstl:if>

<jstl:if test="${pageContext.response.locale.language == 'en'}">
	<b><spring:message code="article.moment"/>:&nbsp;</b><jstl:out value="${momentEn}" />
	<br/>
</jstl:if>

<b><spring:message code="article.summary"/>:&nbsp;</b><jstl:out value="${article.summary}"/>
<br/>

<b><spring:message code="article.body"/>:&nbsp;</b><jstl:out value="${article.body}"/>
<br/>
<b><spring:message code="article.picture"/></b>

<jstl:forEach items="${article.pictures}" var="picture">
	<img src="${picture.url}" width="500px" height="100%" />
	<br/>
</jstl:forEach>

<b><spring:message code="article.finalMode"/>:&nbsp;</b><jstl:out value="${article.finalMode}"/>
<br/>

<b><spring:message code="article.taboo"/>:&nbsp;</b><jstl:out value="${article.taboo}"/>
<br/>

<a href="followUp/list.do?articleId=${article.id}"><spring:message code="article.listFollowUps"/></a>
<br/>

<input type="button" name="cancel" value="<spring:message code="newsPaper.cancel" />"
	   onclick="javascript: relativeRedir('article/listAll.do');" />