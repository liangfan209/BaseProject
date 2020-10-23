package com.clkj.login.finger.biometricPrompt;

import android.os.CancellationSignal;

import com.clkj.login.finger.BiometricPromptManager;

import androidx.annotation.NonNull;

/**
 * Created by gaoyang on 2018/06/19.
 */
public interface IBiometricPromptImpl {

    void authenticate(@NonNull CancellationSignal cancel,
                      @NonNull BiometricPromptManager.OnBiometricIdentifyCallback callback);

}
