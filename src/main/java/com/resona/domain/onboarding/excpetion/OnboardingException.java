package com.resona.domain.onboarding.excpetion;

import com.resona.global.exception.GlobalException;
import com.resona.global.response.ErrorCode;

public class OnboardingException extends GlobalException {
  public OnboardingException(ErrorCode code) {
    super(code);
  }
}
