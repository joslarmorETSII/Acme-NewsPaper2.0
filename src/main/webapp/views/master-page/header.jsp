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

<div>
	<img src="images/logo.png" height="200" width="450" alt="Acme NewsPaper Co., Inc." />
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMINISTRATOR')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="article/actor/listAll.do"><spring:message code="master.page.actor.article.listAll" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv" href="configuration/administrator/edit.do"><spring:message code="master.page.configuration.administrator.edit" /></a></li>
			<li><a class="fNiv" href="newsPaper/administrator/list.do"><spring:message code="master.page.newsPaper.administrator.list" /></a></li>
            <li><a class="fNiv" href="article/administrator/list.do"><spring:message code="master.page.article.administrator.list" /></a></li>
		</security:authorize>

		<security:authorize access="hasRole('USER')">
			<li><a class="fNiv"><spring:message	code="master.page.user" /></a>

			<ul>
					<li class="arrow"></li>
					<li><a href="article/user/list.do"><spring:message code="master.page.user.article.list" /></a></li>
					<li><a href="newsPaper/user/list.do"><spring:message code="master.page.user.newsPaper.list" /></a></li>

			</ul>
			</li>
			<li><a class="fNiv" href="user/list-followers.do"><spring:message code="master.page.user.followers" /></a></li>
			<li><a class="fNiv" href="user/list-following.do"><spring:message code="master.page.user.following" /></a></li>
			<li><a class="fNiv" href="user/list.do"><spring:message code="master.page.user.listAll" /></a></li>


		</security:authorize>

		<security:authorize access="hasRole('CUSTOMER')">
			<li><a class="fNiv"><spring:message	code="master.page.customer" /></a>
				<ul>
					<li class="arrow"></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a class="fNiv" href="newsPaper/listAll.do"><spring:message code="master.page.newsPaper.listAll" /></a></li>
			<li><a class="fNiv" href="article/listAll.do"><spring:message code="master.page.actor.article.listAll" /></a></li>
			<li><a class="fNiv" href="user/register.do"><spring:message code="master.page.user.register" /></a></li>

		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv" href="article/listAll.do"><spring:message code="master.page.actor.article.listAll" /></a></li>
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
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

