package io.adtrace.examples;

import android.app.Application;

/**
 * Application entry point.
 *
 * AdTrace is <b>not</b> started here on purpose, so this demo can show both:
 * <ul>
 *   <li>Case A — JS {@code AdTrace.onCreate} (WebBridge-only)</li>
 *   <li>Case B — native {@code AdTrace.onCreate} when the user picks hybrid mode</li>
 * </ul>
 *
 * See {@link AdTraceDemoSession} and TECHNICAL.md.
 */
public class GlobalApplication extends Application {
}
