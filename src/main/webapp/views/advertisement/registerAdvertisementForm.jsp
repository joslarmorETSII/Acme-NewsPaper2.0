<%--
  Created by IntelliJ IDEA.
  User: Félix
  Date: 07/04/2018
  Time: 19:39
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<div class="input-group">
    <form:form  action="advertisement/agent/edit.do" modelAttribute="registerAdvertisementForm">

            <acme:textbox path="title" code="advertisement.title"/>
        <br />

            <acme:textbox path="banner" code="advertisement.banner"/>
        <br />

            <acme:textbox path="targetPage" code="advertisement.targetPage"/>
        <br />
    <div class="form-group">
        <acme:textbox code="advertisement.holder" path="holder" />
        <br/>

        <div class="form-group">
            <acme:textbox code="advertisement.brand" path="brand" />
        </div>

        <div class="form-group">
            <acme:textbox code="advertisement.number" path="number" />
        </div>
        <div class="form-group">
            <acme:textbox code="advertisement.expirationMonth" path="expirationMonth" />
        </div>
        <div class="form-group">
            <acme:textbox code="advertisement.expirationYear" path="expirationYear"/>
        </div>
        <div class="form-group">
            <acme:textbox code="advertisement.cvv" path="cvv"/>
        </div>

        <form:label path="newsPaper">
            <spring:message code="advertisement.newsPapers" />
        </form:label>
        <form:select path="newsPaper" items="${newsPapers}" itemLabel="title"/>
        <form:errors path="newsPaper" cssClass="error" />
        <br/>


        <input type="submit" name="save" value="<spring:message code="advertisement.save"/>" />

        <input type="button" name="cancel" value="<spring:message code="advertisement.cancel" />"
               onclick="javascript: relativeRedir('advertisement/list.do');" />

        </form:form>
    </div>

