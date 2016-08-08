/*-
 * #%L
 * Eureka! Clinical User Webapp
 * %%
 * Copyright (C) 2016 Emory University
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
// get the namespace, or declare it here
window.eureka = (typeof window.eureka == "undefined" || !window.eureka ) ? {} : window.eureka;

// add in the namespace
window.eureka.password = new function () {
	var self = this;

	self.pwValidator = null;

	self.setup = function (formElem) {
		self.pwValidator = self.setupPasswordValidator(formElem);
		self.setupSubmitCallback(formElem);
		$.validator.addMethod("passwordCheck",
			function (value, element) {
				return value.length >= 8 && /[0-9]+/.test(value) && /[a-zA-Z]+/.test(value);
			},
			"Please enter at least 8 characters with at least 1 digit."
		);
	};

	self.setupPasswordValidator = function (formElem) {
		return $(formElem).validate({
			rules: {
				oldExpPassword: {
					required: true
				},
				newExpPassword: {
					required: true,
					minlength: 8,
					passwordCheck: true
				},
				verifyExpPassword: {
					required: true,
					minlength: 8,
					equalTo: "#newExpPassword"
				}
			},
			messages: {
				oldExpPassword: "Please enter your old password.",
				newExpPassword: {
					required: "Provide a password.",
					rangelength: $.validator.format("Please enter at least {0} characters.")
				},
				verifyExpPassword: {
					required: "Repeat your password.",
					minlength: $.validator.format("Please enter at least {0} characters."),
					equalTo: "Enter the same password as above."
				}
			},
			errorPlacement: function (error, element) {
				error.appendTo($(element).closest('.form-group').find('.help-inline'));
                                $('.help-inline').css("color", "#b94a48");
			},
			highlight: function (element) {
				$(element).closest('.form-group').addClass('has-error');
			},
			unhighlight: function (element) {
				$(element).closest('.form-group').removeClass('has-error');
			},
			// set this class to error-labels to indicate valid fields
			success: function (label) {
				// set &nbsp; as text for IE
				label.html("&nbsp;").addClass("checked");
			}
		});
	};

	self.setupSubmitCallback = function (formElem) {
		$(formElem).submit(function () {
			var oldPassword = $('#oldExpPassword').val();
			var newPassword = $('#newExpPassword').val();
			var verifyPassword = $('#verifyExpPassword').val();
			var userId = '';
			var targetURL = $('#targetURL').val();

			var dataString = 'action=savepassword' +
				'&id=' + userId +
				'&oldPassword=' + oldPassword +
				'&newPassword=' + newPassword +
				'&verifyPassword=' + verifyPassword;

			if (self.pwValidator.valid()) {
				$.ajax({
					type: 'POST',
					url: 'user_acct',
					data: dataString,
					success: function () {
						window.location.href = targetURL;
					},
					error: function (xhr /*, status, error*/) {
						$('#passwordChangeComplete').show();
						$('#passwordChangeComplete').text(xhr.responseText);
						$('#passwordChangeComplete').css({
							'font-weight': 'bold',
							'font-size': 16
						});

					}
				});
			}
			return false;
		});
	};

};
