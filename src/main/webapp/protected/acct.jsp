<%--
  #%L
  Eureka! Clinical User Webapp
  %%
  Copyright (C) 2016 Emory University
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


<template:insert template="/templates/eureka_main.jsp">
    <template:content name="content">
        <h1>Account Settings ${user.username}</h1>
        <div id="passwordExpirationMsg" class="passwordExpirationMsg">
            <%=request.getAttribute("passwordExpiration")%>
            <br>
        </div>
        <form id="userInfoForm" method="POST" role="form">                 
            <input type="hidden" name="fullName" id="fullName" value="${user.fullName}" />
            <input type="hidden" name="username" id="username" value="${user.username}" />                        
            <div class="row">
                <div class="col-sm-6">
                    <div class="form-group">
                        <label for="firstName" class="control-label">First Name</label>
                        <input id="firstName" name="firstName" type="text"
                               class="form-control" value="${user.firstName}"/>
                        <span class="help-block default-hidden"></span>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6">
                    <div class="form-group">
                        <label for="lastName" class="control-label">Last Name</label>
                        <input id="lastName" name="lastName" type="text"
                               class="form-control" value="${user.lastName}"/>
                        <span class="help-block default-hidden"></span>
                    </div>
                </div>
            </div>                               
            <div class="row">
                <div class="col-sm-6">
                    <div class="form-group">
                        <label for="organization" class="control-label">Organization</label>
                        <input id="organization" name="organization" type="text"
                               class="form-control" value="${user.organization}"/>
                        <span class="help-block default-hidden"></span>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6">
                    <div class="form-group">
                        <label for="title" class="control-label">Title</label>
                        <input id="title" name="title" type="text"
                               class="form-control" value="${user.title}"/>
                        <span class="help-block default-hidden"></span>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6">
                    <div class="form-group">
                        <label for="department" class="control-label">Department</label>
                        <input id="department" name="department" type="text"
                               class="form-control" value="${user.department}"/>
                        <span class="help-block default-hidden"></span>
                    </div>
                </div>
            </div>                                    
            <div class="row">
                <div class="col-sm-6">
                    <div class="form-group">
                        <label for="email" class="control-label">Email Address</label>
                        <input id="email" name="email" type="text"
                               class="form-control" value="${user.email}"/>
                        <span class="help-block default-hidden"></span>
                    </div>
                </div>
            </div>        
            <div class="row">
                <div id="infoNotificationFailure" class="default-hidden help-block has-error">
                    <div id="infoNotificationFailureMsg"></div>                                    
                </div>
            </div>                                                    
            <div class="row">
                <div class="col-sm-6">
                    <div class="form-group">   
                        <input type="submit" value="Save Changes" id="saveEditBtn" class="btn btn-primary text-center"/>     
                        <button id="cancelBtn" class="btn btn-primary text-center">Cancel</button>                                                  
                        <c:if test="${user['class'].name == 'org.eurekaclinical.user.client.comm.LocalUser'}">                        
                            <button id="changePasswordBtn" class="btn btn-primary text-center">Change Password</button>
                        </c:if>      
                        <input type="hidden" name="id" id="id" value="${user.id}" /> 
                        <input type="hidden" name="action" value="saveinfo"/>                                                        

                    </div>
                </div>
            </div>                            
        </form>
        <div id="infoNotificationSuccess" class="default-hidden alert alert-success">
            <p><strong>Your changes has been saved successfully.</strong></p>
        </div>                                        
        <c:if test="${user['class'].name == 'org.eurekaclinical.user.client.comm.LocalUser'}">
            <div id="newPasswordModal" class="modal fade" role="dialog" aria-labelledby="newPasswordModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 id="newPasswordModalLabel" class="modal-title">
                                Change Password
                            </h4>
                        </div>
                        <form id="userCredentialForm" class="form-horizontal" action="#" method="post" role="form">
                            <div class="modal-body">
                                <div class="col-sm-9">
                                    <div class="form-group">
                                        <label for="oldPassword" class="control-label">Old Password</label>
                                        <input id="oldPassword" name="oldPassword" type="password" class="form-control" 
                                               />
                                        <span class="help-inline"></span>
                                    </div>
                                </div>

                                <div class="col-sm-9">
                                    <div class="form-group">
                                        <label for="newPassword" class="control-label" >New Password</label>
                                        <input id="newPassword" name="newPassword" type="password" class="form-control"
                                               />
                                        <span class="help-inline"></span>
                                    </div>
                                </div>

                                <div class="col-sm-9">
                                    <div class="form-group">
                                        <label for="verifyPassword" class="control-label">Re-enter New Password</label>
                                        <input id="verifyPassword" name="verifyPassword" type="password" class="form-control" 
                                               />
                                        <span class="help-inline"></span>
                                    </div>
                                </div>

                                <div class="row">
                                    <div id="passwordChangeNotificationMsg" class="col-sm-10 default-hidden">
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <input type="hidden" name="action" value="savepassword"/>
                                <input type="submit" value="Save Password" id="savePasswordBtn" class="btn btn-primary"/>
                                <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </c:if>
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.16.0/jquery.validate.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/eureka.account${initParam['ec-build-timestamp']}.js"></script>
        <script>var ctx = "${eurekaWebappUrl}"</script>
        <script type="text/javascript">
            $(document).ready(function () {

                eureka.account.setup('#changePasswordBtn', '#cancelBtn', '#userInfoForm', '#infoNotificationFailure', '#infoNotificationFailureMsg', '#infoNotificationSuccess', '#newPasswordModal', '#userCredentialForm', '#passwordChangeNotificationMsg', '#passwordExpirationMsg');
            });
        </script>
    </template:content>
</template:insert>

