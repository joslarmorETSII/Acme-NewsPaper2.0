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

<h3><b><spring:message code="chirp.title"/>:&nbsp;</b><jstl:out value="${chirp.title}"/></h3>

<jstl:if test="${pageContext.response.locale.language == 'es'}">

    <b><spring:message code="chirp.moment"/>:&nbsp;</b><jstl:out value="${momentEs}" />
    <br/>
</jstl:if>

<jstl:if test="${pageContext.response.locale.language == 'en'}">
    <b><spring:message code="chirp.moment"/>:&nbsp;</b><jstl:out value="${momentEn}" />
    <br/>
</jstl:if>

<b><spring:message code="chirp.description"/>:&nbsp;</b><jstl:out value="${chirp.description}"/>
<br/>

<b><spring:message code="chirp.posted"/>:&nbsp;</b><jstl:out value="${chirp.posted}"/>
<br/>

<b><spring:message code="chirp.taboo"/>:&nbsp;</b><jstl:out value="${chirp.taboo}"/>
<br/>



<a href="chirp/listAll.do?chirpId=${chirp.id}"><spring:message code="chirp.listChirps"/></a>
<br/>

<input type="button" name="cancel" value="<spring:message code="chirp.cancel" />"
       onclick="javascript: relativeRedir('${cancelURI}');" />
