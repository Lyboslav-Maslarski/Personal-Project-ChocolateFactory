import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export function emailValidator(
  control: AbstractControl
): ValidationErrors | null {
  if (!control.value) {
    return null;
  }
  return /^[a-zA-Z0-9\.-]{4,}@(gmail|abv|yahoo|outlook|mail|email)\.(bg|com)$/.test(
    control.value
  )
    ? null
    : {
        invalidEmail: true,
      };
}

export function rePasswordValidator(
  getTargetControl: AbstractControl
): ValidatorFn {
  return function rePasswordValidator(
    control: AbstractControl
  ): ValidationErrors | null {
    const areTheSame = getTargetControl.value === control.value;
    return areTheSame ? null : { rePasswordValidator: true };
  };
}