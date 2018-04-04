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

<form:form action="manage/editProfile.do" modelAttribute="customer" onsubmit="return validatePhone()">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="userAccount" />



	<acme:textbox code="customer.name" path="name" />
	<br />

	<acme:textbox code="customer.surname" path="surname"/>
	<br />

	<br />

	<form:label path="phone"><spring:message code="customer.phone" /></form:label>:&nbsp;
	<form:input id="phoneId" path="phone" placeholder="+34 611222333" />
	<form:errors cssClass="error" path="phone" />
	<br />

	<acme:textbox code="customer.emailAdress" path="emailAdress" />
	<br />

	<acme:textbox code="customer.postalAddresses" path="postalAddresses"/>
	<br/>





	<input type="submit" name="save" value="<spring:message code="button.save"/>" />

	<input type="button" name="cancel" value="<spring:message code="button.cancel" />"
		   onclick="javascript: relativeRedir('welcome/index.do');" />

</form:form>

<script>

    function validatePhone() {
        <spring:message code="customer.phone.ask" var="ask"/>
        var x = document.getElementById("phoneId").value;
        var patt = new RegExp("^\\+([3][4])( )(\\d{9})|()$");
        if(x != "" && !patt.test(x)){
            return confirm('<jstl:out value="${ask}"/>');
        }
    }

</script>