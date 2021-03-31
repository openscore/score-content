package io.cloudslang.content.winrm.utils;

import io.cloudslang.content.utils.NumberUtilities;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.cloudslang.content.utils.BooleanUtilities.isValid;
import static io.cloudslang.content.utils.OtherUtilities.isValidIpPort;
import static io.cloudslang.content.winrm.utils.Constants.*;
import static io.cloudslang.content.winrm.utils.Inputs.WinRMInputs.*;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public class InputsValidation {

    @NotNull
    public static List<String> verifyWinRMInputs(@Nullable final String proxyPort,
                                                 @Nullable final String trust_all_roots,
                                                 @Nullable final String operationTimeout,
                                                 @Nullable final String useSSL,
                                                 @Nullable final String requestNewKerberosToken,
                                                 @Nullable final String authType,
                                                 @Nullable final String x509HostnameVerifier,
                                                 @NotNull final String trustKeystore,
                                                 @NotNull final String keystore,
                                                 @NotNull final String port) {

        final List<String> exceptionMessages = new ArrayList<>();
        addVerifyProxy(exceptionMessages, proxyPort, PROXY_PORT);
        addVerifyBoolean(exceptionMessages, trust_all_roots, TRUST_ALL_ROOTS);
        addVerifyBoolean(exceptionMessages, useSSL, USE_SSL);
        addVerifyBoolean(exceptionMessages, requestNewKerberosToken, REQUEST_NEW_KERBEROS_TOKEN);
        addVerifyNumber(exceptionMessages, operationTimeout, OPERATION_TIMEOUT);
        addVerifyAuthType(exceptionMessages, authType, AUTH_TYPE);
        addVerifyx509HostnameVerifier(exceptionMessages, x509HostnameVerifier, X509_HOSTNAME_VERIFIER);
        addVerifyFile(exceptionMessages, trustKeystore, TRUST_KEYSTORE);
        addVerifyFile(exceptionMessages, keystore, KEYSTORE);
        addVerifyPort(exceptionMessages, port, PORT);
        return exceptionMessages;
    }

    @NotNull
    private static List<String> addVerifyNotNullOrEmpty(@NotNull List<String> exceptions, @Nullable final String input, @NotNull final String inputName) {
        if (isEmpty(input)) {
            exceptions.add(String.format(EXCEPTION_NULL_EMPTY, inputName));
        }
        return exceptions;
    }

    @NotNull
    private static List<String> addVerifyFile(@NotNull List<String> exceptions, @NotNull final String filePath, @NotNull final String inputName) {
        if (!isValidFile(filePath)) {
            exceptions.add(String.format(EXCEPTION_INVALID_PATH, filePath, inputName));
        }
        return exceptions;
    }

    private static boolean isValidFile(@NotNull final String filePath) {
        return new File(filePath).exists();
    }

    @NotNull
    private static List<String> addVerifyProxy(@NotNull List<String> exceptions, @Nullable final String input, @NotNull final String inputName) {
        if (isEmpty(input)) {
            exceptions.add(String.format(EXCEPTION_NULL_EMPTY, inputName));
        } else if (!isValidIpPort(input)) {
            exceptions.add(String.format(EXCEPTION_INVALID_PROXY, input, PROXY_PORT));
        }
        return exceptions;
    }

    @NotNull
    private static List<String> addVerifyBoolean(@NotNull List<String> exceptions, @Nullable final String input, @NotNull final String inputName) {
        if (isEmpty(input)) {
            exceptions.add(String.format(EXCEPTION_NULL_EMPTY, inputName));
        } else if (!isValid(input)) {
            exceptions.add(String.format(EXCEPTION_INVALID_BOOLEAN, input, inputName));
        }
        return exceptions;
    }

    @NotNull
    private static List<String> addVerifyNumber(@NotNull List<String> exceptions, @Nullable final String input, @NotNull final String inputName) {
        if (isEmpty(input)) {
            exceptions.add(String.format(EXCEPTION_NULL_EMPTY, inputName));
        } else if (!NumberUtilities.isValidInt(input)) {
            exceptions.add(String.format(EXCEPTION_INVALID_NUMBER, input, inputName));
        }
        return exceptions;
    }

    @NotNull
    private static List<String> addVerifyAuthType(@NotNull List<String> exceptions, @Nullable final String input, @NotNull final String inputName) {
        List<String> authTypes = new ArrayList<>();
        authTypes.add("Basic");
        authTypes.add("NTLM");
        authTypes.add("Kerberos");
        if (!authTypes.contains(input))
            exceptions.add(String.format(EXCEPTION_INVALID_AUTH_TYPE, input, inputName));
        return exceptions;
    }

    @NotNull
    private static List<String> addVerifyx509HostnameVerifier(@NotNull List<String> exceptions, @Nullable final String input, @NotNull final String inputName) {
        List<String> x509HostnameVerifiers = new ArrayList<>();
        x509HostnameVerifiers.add("strict");
        x509HostnameVerifiers.add("allow_all");
        x509HostnameVerifiers.add("browser_compatible");
        if (!x509HostnameVerifiers.contains(input))
            exceptions.add(String.format(EXCEPTION_INVALID_HOSTNAME_VERIFIER, input, inputName));
        return exceptions;
    }

    @NotNull
    private static List<String> addVerifyPort(@NotNull List<String> exceptions, @NotNull final String input, @NotNull final String inputName) {

        if (!NumberUtilities.isValidInt(input)) {
            exceptions.add(String.format(EXCEPTION_INVALID_PORT, input, inputName));
        } else if (Integer.parseInt(input) != 5985 && Integer.parseInt(input) != 5986)
            exceptions.add(String.format(EXCEPTION_INVALID_PORT, input, inputName));
        return exceptions;
    }

}