/*
 * Copyright (C) 2026 the fukbd project.
 *
 * Part of a modified copy of FUTO Keyboard, distributed under the FUTO Source First License 1.1.
 */

package org.futo.inputmethod.event;

import androidx.annotation.Nullable;

/**
 * An optional, generic hook that lets a build supply its own hardware-key decoder per input
 * device. Nothing is installed in a stock build, and then every hardware-key path behaves exactly
 * as before. When a factory is installed, hardware keys are routed through FUTO's input logic even
 * though {@code ProductionFlags.IS_HARDWARE_KEYBOARD_SUPPORTED} is false.
 */
public final class HardwareKeyHooks {
    /** Creates the decoder for one input device, identified by its runtime device id. */
    public interface HardwareEventDecoderFactory {
        HardwareEventDecoder create(int deviceId);
    }

    @Nullable
    private static volatile HardwareEventDecoderFactory sFactory;

    private HardwareKeyHooks() {}

    /** Installs the factory; call once, before the input method starts (for example from a ContentProvider). */
    public static void install(final HardwareEventDecoderFactory factory) {
        sFactory = factory;
    }

    public static boolean isInstalled() {
        return sFactory != null;
    }

    @Nullable
    public static HardwareEventDecoderFactory factory() {
        return sFactory;
    }

    /** An event that swallows the key without passing anything to the input logic. */
    public static Event consumedNoOp() {
        return Event.createConsumedEvent(Event.createCursorMovedEvent(0));
    }
}
