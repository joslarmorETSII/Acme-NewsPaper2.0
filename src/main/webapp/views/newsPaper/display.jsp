<%--
  Created by IntelliJ IDEA.
  User: khawla
  Date: 04/04/2018
  Time: 18:14
  To change this template use File | Settings | File Templates.
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

<h3><b><spring:message code="newsPaper.title"/>:&nbsp;</b><jstl:out value="${newsPaper.title}"/></h3>

<jstl:if test="${pageContext.response.locale.language == 'es'}">

    <b><spring:message code="newsPaper.publicationDate"/>:&nbsp;</b><jstl:out value="${momentEs}" />
    <br/>
</jstl:if>

<jstl:if test="${pageContext.response.locale.language == 'en'}">
    <b><spring:message code="newsPaper.publicationDate"/>:&nbsp;</b><jstl:out value="${momentEn}" />
    <br/>
</jstl:if>

<b><spring:message code="newsPaper.description"/>:&nbsp;</b><jstl:out value="${newsPaper.description}"/>
<br/>

<b><spring:message code="newsPaper.modePrivate"/>:&nbsp;</b><jstl:out value="${newsPaper.modePrivate}"/>
<br/>

<b><spring:message code="newsPaper.picture"/>:&nbsp;</b><jstl:out value="${newsPaper.picture}"/>
<br/>

<b><spring:message code="newsPaper.published"/>:&nbsp;</b><jstl:out value="${newsPaper.published}"/>
<br/>

<b><spring:message code="newsPaper.taboo"/>:&nbsp;</b><jstl:out value="${newsPaper.taboo}"/>
<br/>

<br>
<input type="button" name="cancel" value="<spring:message code="newsPaper.cancel" />"
       onclick="javascript: relativeRedir('${cancelURI}');" />
