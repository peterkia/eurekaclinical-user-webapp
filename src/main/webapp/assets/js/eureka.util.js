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
window.eureka = (typeof window.eureka == "undefined" || !window.eureka) ? {} : window.eureka;

// add in the namespace
window.eureka.util = new function () {
    var messages = {
        500: "An internal error has occured. Please contact the technical team for further assistance."
    };
    this.insertMailToTag = function (container, userName, domainName) {
        var atSign = "&#64;"
        var email = userName + atSign + domainName;
        var content = "<a href=\"mail" + "to:" + email + "\">" + email + "</a>";
        $(container).html(content);
    };
    this.getUserMessage = function (status, content) {
        switch (status) {
            case 500:
                return messages[status];
            default:
                return content;
        }
    };
};
