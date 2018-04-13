<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<div>
	<img src="images/logo.png" height="200" width="450" alt="Acme NewsPaper Co., Inc." />
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMINISTRATOR')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li><a href="administrator/dashboard.do"><spring:message code="master.page.administrator.dashboard" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv" href="chirp/administrator/list.do"><spring:message code="master.page.chirp.list-taboo" /></a></li>
			<li><a class="fNiv" href="configuration/administrator/edit.do"><spring:message code="master.page.configuration.administrator.edit" /></a></li>
			<li><a class="fNiv" href="newsPaper/administrator/list.do"><spring:message code="master.page.newsPaper.administrator" /></a></li>
            <li><a class="fNiv" href="article/administrator/list.do"><spring:message code="master.page.article.administrator" /></a></li>

		</security:authorize>

		<security:authorize access="hasRole('USER')">
			<li><a class="fNiv"><spring:message	code="master.page.user" /></a>

			<ul>
					<li class="arrow"></li>
					<li><a href="article/user/list.do"><spring:message code="master.page.user.article.list" /></a></li>
					<li><a href="newsPaper/user/list.do"><spring:message code="master.page.user.newsPaper.list" /></a></li>
					<li><a href="followUp/user/list.do"><spring:message code="master.page.user.followUp.list" /></a></li>
				<li><a href="chirp/user/list.do"><spring:message code="master.page.user.chirp.list" /></a></li>


			</ul>
			</li>
			<li><a class="fNiv" href="newsPaper/listAll.do"><spring:message code="master.page.actor.newsPaper.listAll" /></a></li>
			<li><a class="fNiv" href="article/listAll.do"><spring:message code="master.page.actor.article.listAll" /></a></li>
			<li><a class="fNiv" href="user/user/list-followers.do"><spring:message code="master.page.user.followers" /></a></li>
			<li><a class="fNiv" href="user/user/list-following.do"><spring:message code="master.page.user.following" /></a></li>
			<li><a class="fNiv" href="user/list.do"><spring:message code="master.page.user.listAll" /></a></li>



		</security:authorize>

		<security:authorize access="hasRole('CUSTOMER')">
			<li><a class="fNiv"><spring:message	code="master.page.customer" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="/newsPaper/customer/list.do"><spring:message code="master.page.newspaper.customer.list" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv" href="article/listAll.do"><spring:message code="master.page.actor.article.listAll" /></a></li>
			<li><a class="fNiv" href="/newsPaper/customer/listNewsPaperCustomer.do"><spring:message code="master.page.newspaper.customerNewsPaper" /></a></li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a class="fNiv" href="newsPaper/listAll.do"><spring:message code="master.page.newsPaper.listAll" /></a></li>
			<li><a class="fNiv" href="article/listAll.do"><spring:message code="master.page.actor.article.listAll" /></a></li>
			<li><a class="fNiv" href="user/list.do"><spring:message code="master.page.user.listAll" /></a></li>
			<li><a class="fNiv" href="user/register.do"><spring:message code="master.page.user.register" /></a></li>
			<li><a class="fNiv" href="customer/register.do"><spring:message code="master.page.customer.register" /></a></li>

		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
		<li><a class="fNiv" href="user/search.do"><spring:message code="master.page.search" /></a></li>

	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>


