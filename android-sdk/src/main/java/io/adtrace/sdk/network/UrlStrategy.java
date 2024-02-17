package io.adtrace.sdk.network;

import io.adtrace.sdk.ActivityKind;
import io.adtrace.sdk.Constants;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static io.adtrace.sdk.AdTraceConfig.DATA_RESIDENCY_TR;
import static io.adtrace.sdk.AdTraceConfig.DATA_RESIDENCY_US;
import static io.adtrace.sdk.AdTraceConfig.URL_STRATEGY_CHINA;
import static io.adtrace.sdk.AdTraceConfig.URL_STRATEGY_CN;
import static io.adtrace.sdk.AdTraceConfig.URL_STRATEGY_CN_ONLY;
import static io.adtrace.sdk.AdTraceConfig.URL_STRATEGY_INDIA;
import static io.adtrace.sdk.AdTraceConfig.DATA_RESIDENCY_EU;

/**
 * AdTrace android SDK (https://adtrace.io)
 * Created by Nasser Amini (github.com/namini40) on April 2022.
 * Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright © 2022.
 */

public class UrlStrategy {
    private static final String BASE_URL_INDIA = "https://app.adtrace.net.in";
    private static final String GDPR_URL_INDIA = "https://gdpr.adtrace.net.in";
    private static final String SUBSCRIPTION_URL_INDIA = "https://subscription.adtrace.net.in";
    private static final String PURCHASE_VERIFICATION_URL_INDIA = "https://ssrv.adtrace.net.in";

    private static final String BASE_URL_CHINA = "https://app.adtrace.world";
    private static final String GDPR_URL_CHINA = "https://gdpr.adtrace.world";
    private static final String SUBSCRIPTION_URL_CHINA = "https://subscription.adtrace.world";
    private static final String PURCHASE_VERIFICATION_URL_CHINA = "https://ssrv.adtrace.world";

    private static final String BASE_URL_CN = "https://app.adtrace.cn";
    private static final String GDPR_URL_CN = "https://gdpr.adtrace.com";
    private static final String SUBSCRIPTION_URL_CN = "https://subscription.adtrace.com";
    private static final String PURCHASE_VERIFICATION_URL_CN = "https://ssrv.adtrace.cn";

    private static final String BASE_URL_EU = "https://app.eu.adtrace.com";
    private static final String GDPR_URL_EU = "https://gdpr.eu.adtrace.com";
    private static final String SUBSCRIPTION_URL_EU = "https://subscription.eu.adtrace.com";
    private static final String PURCHASE_VERIFICATION_URL_EU = "https://ssrv.eu.adtrace.com";

    private static final String BASE_URL_TR = "https://app.tr.adtrace.com";
    private static final String GDPR_URL_TR = "https://gdpr.tr.adtrace.com";
    private static final String SUBSCRIPTION_URL_TR = "https://subscription.tr.adtrace.com";
    private static final String PURCHASE_VERIFICATION_URL_TR = "https://ssrv.tr.adtrace.com";

    private static final String BASE_URL_US = "https://app.us.adtrace.com";
    private static final String GDPR_URL_US = "https://gdpr.us.adtrace.com";
    private static final String SUBSCRIPTION_URL_US = "https://subscription.us.adtrace.com";
    private static final String PURCHASE_VERIFICATION_URL_US = "https://ssrv.us.adtrace.com";

    private final String baseUrlOverwrite;
    private final String gdprUrlOverwrite;
    private final String subscriptionUrlOverwrite;
    private final String purchaseVerificationUrlOverwrite;

    final List<String> baseUrlChoicesList;
    final List<String> gdprUrlChoicesList;
    final List<String> subscriptionUrlChoicesList;
    final List<String> purchaseVerificationUrlChoicesList;
    boolean wasLastAttemptSuccess;
    int choiceIndex;
    int startingChoiceIndex;
    boolean wasLastAttemptWithOverwrittenUrl;

    public UrlStrategy(final String baseUrlOverwrite,
                       final String gdprUrlOverwrite,
                       final String subscriptionUrlOverwrite,
                       final String purchaseVerificationUrlOverwrite,
                       final String adtraceUrlStrategy)
    {
        this.baseUrlOverwrite = baseUrlOverwrite;
        this.gdprUrlOverwrite = gdprUrlOverwrite;
        this.subscriptionUrlOverwrite = subscriptionUrlOverwrite;
        this.purchaseVerificationUrlOverwrite = purchaseVerificationUrlOverwrite;

        baseUrlChoicesList = baseUrlChoices(adtraceUrlStrategy);
        gdprUrlChoicesList = gdprUrlChoices(adtraceUrlStrategy);
        subscriptionUrlChoicesList = subscriptionUrlChoices(adtraceUrlStrategy);
        purchaseVerificationUrlChoicesList = purchaseVerificationUrlChoices(adtraceUrlStrategy);

        wasLastAttemptSuccess = false;
        choiceIndex = 0;
        startingChoiceIndex = 0;
        wasLastAttemptWithOverwrittenUrl = false;
    }

    public void resetAfterSuccess() {
        startingChoiceIndex = choiceIndex;
        wasLastAttemptSuccess = true;
    }

    @SuppressWarnings("UnnecessaryLocalVariable")
    public boolean shouldRetryAfterFailure(final ActivityKind activityKind) {
        wasLastAttemptSuccess = false;

        // does not need to "rotate" choice index
        //  since it will use the same overwritten url
        //  might as well stop retrying in the same sending "session"
        //  and let the backoff strategy pick it up
        if (wasLastAttemptWithOverwrittenUrl) {
            return false;
        }

        int choiceListSize;

        if (activityKind == ActivityKind.GDPR) {
            choiceListSize = gdprUrlChoicesList.size();
        } else if (activityKind == ActivityKind.SUBSCRIPTION) {
            choiceListSize = subscriptionUrlChoicesList.size();
        } else if (activityKind == ActivityKind.PURCHASE_VERIFICATION) {
            choiceListSize = purchaseVerificationUrlChoicesList.size();
        } else {
            choiceListSize = baseUrlChoicesList.size();
        }

        final int nextChoiceIndex = (choiceIndex + 1) % choiceListSize;
        choiceIndex = nextChoiceIndex;
        final boolean nextChoiceHasNotReturnedToStartingChoice = choiceIndex != startingChoiceIndex;

        return nextChoiceHasNotReturnedToStartingChoice;
    }

    public String targetUrlByActivityKind(final ActivityKind activityKind) {
        if (activityKind == ActivityKind.GDPR) {
            if (gdprUrlOverwrite != null) {
                wasLastAttemptWithOverwrittenUrl = true;
                return gdprUrlOverwrite;
            } else {
                wasLastAttemptWithOverwrittenUrl = false;
                return gdprUrlChoicesList.get(choiceIndex);
            }
        } else if (activityKind == ActivityKind.SUBSCRIPTION) {
            if (subscriptionUrlOverwrite != null) {
                wasLastAttemptWithOverwrittenUrl = true;
                return subscriptionUrlOverwrite;
            } else {
                wasLastAttemptWithOverwrittenUrl = false;
                return subscriptionUrlChoicesList.get(choiceIndex);
            }
        } else if (activityKind == ActivityKind.PURCHASE_VERIFICATION) {
            if (purchaseVerificationUrlOverwrite != null) {
                wasLastAttemptWithOverwrittenUrl = true;
                return purchaseVerificationUrlOverwrite;
            } else {
                wasLastAttemptWithOverwrittenUrl = false;
                return purchaseVerificationUrlChoicesList.get(choiceIndex);
            }
        } else {
            if (baseUrlOverwrite != null) {
                wasLastAttemptWithOverwrittenUrl = true;
                return baseUrlOverwrite;
            } else {
                wasLastAttemptWithOverwrittenUrl = false;
                return baseUrlChoicesList.get(choiceIndex);
            }
        }
    }

    private static List<String> baseUrlChoices(final String urlStrategy) {
        if (URL_STRATEGY_INDIA.equals(urlStrategy)) {
            return Arrays.asList(BASE_URL_INDIA, Constants.BASE_URL);
        } else if (URL_STRATEGY_CHINA.equals(urlStrategy)) {
            return Arrays.asList(BASE_URL_CHINA, Constants.BASE_URL);
        } else if (URL_STRATEGY_CN.equals(urlStrategy)) {
            return Arrays.asList(BASE_URL_CN, Constants.BASE_URL);
        } else if (URL_STRATEGY_CN_ONLY.equals(urlStrategy)) {
            return Arrays.asList(BASE_URL_CN);
        } else if (DATA_RESIDENCY_EU.equals(urlStrategy)) {
            return Collections.singletonList(BASE_URL_EU);
        } else if (DATA_RESIDENCY_TR.equals(urlStrategy)) {
            return Collections.singletonList(BASE_URL_TR);
        } else if (DATA_RESIDENCY_US.equals(urlStrategy)) {
            return Collections.singletonList(BASE_URL_US);
        } else {
            return Arrays.asList(Constants.BASE_URL, BASE_URL_INDIA, BASE_URL_CHINA);
        }
    }
    private static List<String> gdprUrlChoices(final String urlStrategy) {
        if (URL_STRATEGY_INDIA.equals(urlStrategy)) {
            return Arrays.asList(GDPR_URL_INDIA, Constants.GDPR_URL);
        } else if (URL_STRATEGY_CHINA.equals(urlStrategy)) {
            return Arrays.asList(GDPR_URL_CHINA, Constants.GDPR_URL);
        } else if (URL_STRATEGY_CN.equals(urlStrategy)) {
            return Arrays.asList(GDPR_URL_CN, Constants.GDPR_URL);
        } else if (URL_STRATEGY_CN_ONLY.equals(urlStrategy)) {
            return Arrays.asList(GDPR_URL_CN);
        } else if (DATA_RESIDENCY_EU.equals(urlStrategy)) {
            return Collections.singletonList(GDPR_URL_EU);
        } else if (DATA_RESIDENCY_TR.equals(urlStrategy)) {
            return Collections.singletonList(GDPR_URL_TR);
        } else if (DATA_RESIDENCY_US.equals(urlStrategy)) {
            return Collections.singletonList(GDPR_URL_US);
        } else {
            return Arrays.asList(Constants.GDPR_URL, GDPR_URL_INDIA, GDPR_URL_CHINA);
        }
    }
    private static List<String> subscriptionUrlChoices(final String urlStrategy) {
        if (URL_STRATEGY_INDIA.equals(urlStrategy)) {
            return Arrays.asList(SUBSCRIPTION_URL_INDIA, Constants.SUBSCRIPTION_URL);
        } else if (URL_STRATEGY_CHINA.equals(urlStrategy)) {
            return Arrays.asList(SUBSCRIPTION_URL_CHINA, Constants.SUBSCRIPTION_URL);
        } else if (URL_STRATEGY_CN.equals(urlStrategy)) {
            return Arrays.asList(SUBSCRIPTION_URL_CN, Constants.SUBSCRIPTION_URL);
        } else if (URL_STRATEGY_CN_ONLY.equals(urlStrategy)) {
            return Arrays.asList(SUBSCRIPTION_URL_CN);
        } else if (DATA_RESIDENCY_EU.equals(urlStrategy)) {
            return Collections.singletonList(SUBSCRIPTION_URL_EU);
        } else if (DATA_RESIDENCY_TR.equals(urlStrategy)) {
            return Collections.singletonList(SUBSCRIPTION_URL_TR);
        } else if (DATA_RESIDENCY_US.equals(urlStrategy)) {
            return Collections.singletonList(SUBSCRIPTION_URL_US);
        } else {
            return Arrays.asList(Constants.SUBSCRIPTION_URL,
                    SUBSCRIPTION_URL_INDIA,
                    SUBSCRIPTION_URL_CHINA);
        }
    }
    private static List<String> purchaseVerificationUrlChoices(final String urlStrategy) {
        if (URL_STRATEGY_INDIA.equals(urlStrategy)) {
            return Arrays.asList(PURCHASE_VERIFICATION_URL_INDIA, Constants.PURCHASE_VERIFICATION_URL);
        } else if (URL_STRATEGY_CHINA.equals(urlStrategy)) {
            return Arrays.asList(PURCHASE_VERIFICATION_URL_CHINA, Constants.PURCHASE_VERIFICATION_URL);
        } else if (URL_STRATEGY_CN.equals(urlStrategy)) {
            return Arrays.asList(PURCHASE_VERIFICATION_URL_CN, Constants.PURCHASE_VERIFICATION_URL);
        } else if (URL_STRATEGY_CN_ONLY.equals(urlStrategy)) {
            return Arrays.asList(PURCHASE_VERIFICATION_URL_CN);
        } else if (DATA_RESIDENCY_EU.equals(urlStrategy)) {
            return Collections.singletonList(PURCHASE_VERIFICATION_URL_EU);
        } else if (DATA_RESIDENCY_TR.equals(urlStrategy)) {
            return Collections.singletonList(PURCHASE_VERIFICATION_URL_TR);
        } else if (DATA_RESIDENCY_US.equals(urlStrategy)) {
            return Collections.singletonList(PURCHASE_VERIFICATION_URL_US);
        } else {
            return Arrays.asList(Constants.PURCHASE_VERIFICATION_URL,
                    PURCHASE_VERIFICATION_URL_INDIA,
                    PURCHASE_VERIFICATION_URL_CHINA);
        }
    }
}
