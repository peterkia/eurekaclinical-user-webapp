<%--
  #%L
  Eureka WebApp
  %%
  Copyright (C) 2012 - 2013 Emory University
  %%
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
  
       http://www.apache.org/licenses/LICENSE-2.0
  
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
  #L%
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/tlds/template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/tlds/function.tld" prefix="myfn" %>


<template:insert template="/templates/eureka_main.jsp">
	<template:content name="content">
		<h3>Edit User ${currentUser.username}</h3>
		<form id="userform" action="admin" method="GET" id="editUserForm" role="form" class="">
			<div class="form-group">
				<label>
					Name
				</label>
				<div class="form-control-static">
						${currentUser.firstName} ${currentUser.lastName}
				</div>
			</div>
			<div class="form-group">
				<label>
					Organization
				</label>
				<div class="form-control-static">
						${currentUser.organization}
				</div>
			</div>
			<div class="form-group">
				<label>
					Title
				</label>
				<div class="form-control-static">
						${currentUser.title}
				</div>
			</div>
			<div class="form-group">
				<label>
					Department
				</label>
				<div class="form-control-static">
						${currentUser.department}
				</div>
			</div>
			<div class="form-group">
				<label>
					Email
				</label>
				<div class="form-control-static">
						${currentUser.email}
				</div>
			</div>
			<div class="form-group">
				<label>
					Role
				</label>
				<div class="form-control-static">
                                        <c:set var="userHasResearcherRole" value="0"></c:set>                                    
					<c:set var="userHasAdminRole" value="0"></c:set>

                                        <c:forEach var="userRole" items="${currentUser.roles}">

                                                <c:if test="${userRole == 1}">
                                                        <c:set var="userHasResearcherRole" value="1"></c:set>
                                                </c:if>                                                      
                                                <c:if test="${userRole == 2}">
                                                        <c:set var="userHasAdminRole" value="1"></c:set>
                                                </c:if>

                                        </c:forEach>
                                                
					<c:forEach var="role" items="${roles}">
						<c:if test="${role.name eq 'researcher'}">
                                                                <input type="checkbox" name="role" value="${role.id}"
                                                                        <c:choose>
                                                                                <c:when test="${ userHasResearcherRole == 1}">
                                                                                     checked="checked"
                                                                                </c:when>
                                                                        </c:choose>
                                                                 />${role.name}                                                    
                                                </c:if>
						<c:if test="${role.name eq 'admin'}">
                                                                <input type="checkbox" name="role" value="${role.id}"
                                                                        <c:choose>
                                                                                <c:when test="${ userHasAdminRole == 1}">
                                                                                     checked="checked"
                                                                                </c:when>
                                                                        </c:choose>
                                                                        <c:choose>
                                                                                <c:when test="${ (userHasAdminRole == 1) && (currentUser.username eq me.username )}">
                                                                                     disabled="disabled"
                                                                                </c:when>
                                                                        </c:choose>
                                                                 />${role.name}                                                    
                                                </c:if>                                            
					</c:forEach>
				</div>
			</div>
			<div class="form-group">
				<div class="checkbox checkbox-inline">
					<label>
						<c:choose>
							<c:when test="${currentUser.active == true}">
								<input type="checkbox" name="active" id="active"
									   checked ${((userHasAdminRole == 1) && (currentUser.username eq me.username )) ? "disabled" : ''}/>
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="active" id="active"/>
							</c:otherwise>
						</c:choose>
						Activated
					</label>
				</div>
			</div>
			<div class="form-group">
				<label>
					Verification Status:
				</label>
				<div class="form-control-static">
					<c:choose>
						<c:when test="${currentUser['class'].name == 'org.eurekaclinical.eureka.client.comm.LocalUser'}">
							<c:choose>
								<c:when test="${currentUser.verified == true}">
									Verified<span class="status"></span><br />
								</c:when>
								<c:otherwise>
									Un-verified<span class="status"></span><br />                                   
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							N/A
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<div class="form-group">
				<input type="hidden" name="id" value="${currentUser.id}"/>
				<input type="hidden" name="action" value="save"/>
				<button type="submit" class="btn btn-primary">Save</button>
			</div>
		</form>
		<script type="text/javascript">
			$(document).ready(function () {
				$('#userform').submit(function () {
					$('#userform :input').each(function () {
						$(this).removeAttr('disabled');
					});
					return true;
				});
			});
		</script>
	</template:content>

</template:insert>
