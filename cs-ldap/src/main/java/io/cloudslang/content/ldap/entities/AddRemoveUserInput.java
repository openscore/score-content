/*
 * (c) Copyright 2020 Micro Focus
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License v2.0 which accompany this distribution.
 *
 * The Apache License is available at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.cloudslang.content.ldap.entities;

import io.cloudslang.content.ldap.constants.Constants;
import io.cloudslang.content.ldap.utils.InputBuilderUtils;

import static io.cloudslang.content.ldap.utils.InputBuilderUtils.*;
import static org.apache.commons.lang3.StringUtils.defaultIfEmpty;

public class AddRemoveUserInput implements AddRemoveUsrInput {

    private String host;
    private String groupDN;
    private String userDN;
    private String username;
    private String password;
    private String protocol;
    private boolean trustAllRoots;
    private String trustKeystore;
    private String trustPassword;

    public AddRemoveUserInput() {
    }

    public String getHost() {
        return host;
    }

    public String getGroupDN() { return groupDN; }

    public String getUserDN() {
        return userDN;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() { return password; }

    public String getProtocol() {
        return protocol;
    }

    public Boolean getTrustAllRoots() {
        return trustAllRoots;
    }

    public String getTrustKeystore() {
        return trustKeystore;
    }

    public String getTrustPassword() {
        return trustPassword;
    }


    public static class Builder {

        private String host;
        private String groupDN;
        private String userDN;
        private String username;
        private String password;
        private String protocol;
        private String trustAllRoots;
        private String trustKeystore;
        private String trustPassword;

        public AddRemoveUserInput.Builder host(String host) {
            this.host = host;
            return this;
        }

        public AddRemoveUserInput.Builder groupDN(String groupDN) {
            this.groupDN = groupDN;
            return this;
        }

        public AddRemoveUserInput.Builder userDN(String userDN) {
            this.userDN = userDN;
            return this;
        }

        public AddRemoveUserInput.Builder username(String username) {
            this.username = username;
            return this;
        }


        public AddRemoveUserInput.Builder password(String password) {
            this.password = password;
            return this;
        }

        public AddRemoveUserInput.Builder protocol(String protocol) {
            this.protocol = protocol;
            return this;
        }

        public AddRemoveUserInput.Builder trustAllRoots(String trustAllRoots) {
            this.trustAllRoots = trustAllRoots;
            return this;
        }

        public AddRemoveUserInput.Builder trustKeystore(String trustKeystore) {
            this.trustKeystore = trustKeystore;
            return this;
        }


        public AddRemoveUserInput.Builder trustPassword(String trustPassword) {
            this.trustPassword = trustPassword;
            return this;
        }

        public AddRemoveUserInput build() throws Exception {
            AddRemoveUserInput input = new AddRemoveUserInput();

            input.host = buildHost(host, true);

            input.groupDN = InputBuilderUtils.buildComputerCommonName(groupDN, true);

            input.userDN = InputBuilderUtils.buildComputerCommonName(userDN, true);

            input.username = buildUsername(username);

            input.password = buildPassword(password);

            input.trustAllRoots = buildTrustAllRoots(trustAllRoots);

            input.protocol = buildProtocol(protocol);

            input.trustKeystore = defaultIfEmpty(trustKeystore, Constants.DEFAULT_JAVA_KEYSTORE);

            input.trustPassword = trustPassword;

            return input;
        }
    }
}
