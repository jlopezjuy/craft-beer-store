import { Component } from '@angular/core';

/**
 * @title List with selection
 */
@Component({
    selector: 'jhi-list-selection-example',
    styleUrls: ['list-selection-example.css'],
    templateUrl: 'list-selection-example.html'
})
export class ListSelectionExample {
    typesOfShoes: string[] = ['Boots', 'Clogs', 'Loafers', 'Moccasins', 'Sneakers'];
}
