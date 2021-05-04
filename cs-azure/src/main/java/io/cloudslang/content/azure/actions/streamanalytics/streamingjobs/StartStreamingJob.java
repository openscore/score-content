/*
 * (c) Copyright 2019 EntIT Software LLC, a Micro Focus company, L.P.
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
/*
 * (c) Copyright 2021 Micro Focus, L.P.
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
package io.cloudslang.content.azure.actions.streamanalytics.streamingjobs;

import com.hp.oo.sdk.content.annotations.Action;
import com.hp.oo.sdk.content.annotations.Output;
import com.hp.oo.sdk.content.annotations.Param;
import com.hp.oo.sdk.content.annotations.Response;
import io.cloudslang.content.azure.entities.AzureCommonInputs;
import io.cloudslang.content.azure.entities.StartStreamingJobInputs;
import io.cloudslang.content.azure.services.StreamingJobImpl;
import io.cloudslang.content.constants.ReturnCodes;

import java.util.Map;

import static com.hp.oo.sdk.content.plugin.ActionMetadata.MatchType.COMPARE_EQUAL;
import static com.hp.oo.sdk.content.plugin.ActionMetadata.ResponseType.ERROR;
import static com.hp.oo.sdk.content.plugin.ActionMetadata.ResponseType.RESOLVED;
import static io.cloudslang.content.azure.utils.AuthorizationInputNames.PROXY_HOST;
import static io.cloudslang.content.azure.utils.AuthorizationInputNames.PROXY_PASSWORD;
import static io.cloudslang.content.azure.utils.AuthorizationInputNames.PROXY_PORT;
import static io.cloudslang.content.azure.utils.AuthorizationInputNames.PROXY_USERNAME;
import static io.cloudslang.content.azure.utils.Constants.Common.API_VERSION;
import static io.cloudslang.content.azure.utils.Constants.Common.*;
import static io.cloudslang.content.azure.utils.Constants.DEFAULT_PROXY_PORT;
import static io.cloudslang.content.azure.utils.Constants.StartStreamingJobConstants.DEFAULT_OUTPUT_START_MODE;
import static io.cloudslang.content.azure.utils.Constants.StartStreamingJobConstants.START_STREAMING_JOB_OPERATION_NAME;
import static io.cloudslang.content.azure.utils.Descriptions.Common.SUBSCRIPTION_ID_DESC;
import static io.cloudslang.content.azure.utils.Descriptions.Common.*;
import static io.cloudslang.content.azure.utils.Descriptions.CreateStreamingJob.*;
import static io.cloudslang.content.azure.utils.Descriptions.StartStreamingJob.*;
import static io.cloudslang.content.azure.utils.Inputs.CreateStreamingJobInputs.*;
import static io.cloudslang.content.azure.utils.Inputs.StartStreamingJobInputs.OUTPUT_START_MODE;
import static io.cloudslang.content.azure.utils.Inputs.StartStreamingJobInputs.OUTPUT_START_TIME;
import static io.cloudslang.content.azure.utils.Outputs.CommonOutputs.AUTH_TOKEN;
import static io.cloudslang.content.constants.OutputNames.*;
import static io.cloudslang.content.constants.ResponseNames.FAILURE;
import static io.cloudslang.content.constants.ResponseNames.SUCCESS;
import static io.cloudslang.content.httpclient.entities.HttpClientInputs.*;
import static io.cloudslang.content.utils.OutputUtilities.getFailureResultsMap;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.defaultIfEmpty;
import static io.cloudslang.content.azure.utils.Inputs.CommonInputs.SUBSCRIPTION_ID;
import static io.cloudslang.content.azure.utils.Inputs.CommonInputs.RESOURCE_GROUP_NAME;

public class StartStreamingJob {

    @Action(name = START_STREAMING_JOB_OPERATION_NAME,
            description = START_STREAMING_JOB_OPERATION_DESC,
            outputs = {
                    @Output(value = RETURN_RESULT, description = RETURN_RESULT_DESC),
                    @Output(value = EXCEPTION, description = EXCEPTION_DESC),
                    @Output(value = STATUS_CODE, description = STATUS_CODE_DESC)
            },
            responses = {
                    @Response(text = SUCCESS, field = RETURN_CODE, value = ReturnCodes.SUCCESS, matchType = COMPARE_EQUAL, responseType = RESOLVED, description = SUCCESS_DESC),
                    @Response(text = FAILURE, field = RETURN_CODE, value = ReturnCodes.FAILURE, matchType = COMPARE_EQUAL, responseType = ERROR, description = FAILURE_DESC)
            })
    public Map<String, String> execute(@Param(value = AUTH_TOKEN, required = true, encrypted = true, description = AUTH_TOKEN_DESC) String authToken,
                                       @Param(value = SUBSCRIPTION_ID, required = true, description = SUBSCRIPTION_ID_DESC) String subscriptionId,
                                       @Param(value = RESOURCE_GROUP_NAME, required = true, description = RESOURCE_GROUP_NAME_DESC) String resourceGroupName,
                                       @Param(value = JOB_NAME, required = true, description = JOB_NAME_DESC) String jobName,
                                       @Param(value = API_VERSION, description = API_VERSION_DESC) String apiVersion,
                                       @Param(value = OUTPUT_START_MODE, description = OUTPUT_START_MODE_DESC) String outputStartMode,
                                       @Param(value = OUTPUT_START_TIME, description = OUTPUT_START_TIME_DESC) String outputStartTime,
                                       @Param(value = PROXY_HOST, description = PROXY_HOST_DESC) String proxyHost,
                                       @Param(value = PROXY_PORT, description = PROXY_PORT_DESC) String proxyPort,
                                       @Param(value = PROXY_USERNAME, description = PROXY_USERNAME_DESC) String proxyUsername,
                                       @Param(value = PROXY_PASSWORD, encrypted = true, description = PROXY_PASSWORD_DESC) String proxyPassword,
                                       @Param(value = TRUST_ALL_ROOTS, description = TRUST_ALL_ROOTS_DESC) String trustAllRoots,
                                       @Param(value = X509_HOSTNAME_VERIFIER, description = X509_DESC) String x509HostnameVerifier,
                                       @Param(value = TRUST_KEYSTORE, description = TRUST_KEYSTORE_DESC) String trustKeystore,
                                       @Param(value = TRUST_PASSWORD, encrypted = true, description = TRUST_PASSWORD_DESC) String trustPassword) {


        proxyHost = defaultIfEmpty(proxyHost, EMPTY);
        proxyPort = defaultIfEmpty(proxyPort, DEFAULT_PROXY_PORT);
        proxyUsername = defaultIfEmpty(proxyUsername, EMPTY);
        proxyPassword = defaultIfEmpty(proxyPassword, EMPTY);
        trustAllRoots = defaultIfEmpty(trustAllRoots, BOOLEAN_FALSE);
        x509HostnameVerifier = defaultIfEmpty(x509HostnameVerifier, STRICT);
        trustKeystore = defaultIfEmpty(trustKeystore, DEFAULT_JAVA_KEYSTORE);
        trustPassword = defaultIfEmpty(trustPassword, CHANGEIT);
        apiVersion = defaultIfEmpty(apiVersion, STREAM_API_VERSION);
        outputStartMode = defaultIfEmpty(outputStartMode, DEFAULT_OUTPUT_START_MODE);


        try {
            return StreamingJobImpl.startStreamingJob(StartStreamingJobInputs.builder().
                    azureCommonInputs(AzureCommonInputs.builder().apiVersion(apiVersion).authToken(authToken)
                            .resourceGroupName(resourceGroupName).subscriptionId(subscriptionId).jobName(jobName).proxyPort(proxyPort)
                            .proxyHost(proxyHost).proxyUsername(proxyUsername).proxyPassword(proxyPassword).trustAllRoots(trustAllRoots)
                            .x509HostnameVerifier(x509HostnameVerifier).trustKeystore(trustKeystore).trustPassword(trustPassword).build())
                    .outputStartMode(outputStartMode).outputStartTime(outputStartTime).build());
        } catch (Exception exception) {
            return getFailureResultsMap(exception);
        }
    }

}
