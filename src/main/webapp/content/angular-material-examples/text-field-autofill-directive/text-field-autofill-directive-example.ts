import { Component } from '@angular/core';

/** @title Monitoring autofill state with cdkAutofill */
@Component({
    selector: 'jhi-text-field-autofill-directive-example',
    templateUrl: './text-field-autofill-directive-example.html',
    styleUrls: ['./text-field-autofill-directive-example.css']
})
export class TextFieldAutofillDirectiveExample {
    firstNameAutofilled: boolean;
    lastNameAutofilled: boolean;
}
