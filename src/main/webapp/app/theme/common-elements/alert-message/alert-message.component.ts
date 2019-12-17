import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'jhi-alert-message',
  templateUrl: './alert-message.component.html',
  styleUrls: ['./alert-message.component.css']
})
export class AlertMessageComponent implements OnInit {
  @Input() options: any = {};

  public showElement: boolean = true;

  constructor() {}

  ngOnInit() {}

  toggleElement() {
    this.showElement = !this.showElement;
  }
}
