import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'jhi-appliance-status-widget',
  templateUrl: './appliance-status-widget.component.html',
  styleUrls: ['./appliance-status-widget.component.css']
})
export class ApplianceStatusWidgetComponent implements OnInit {
  @Input() options: any = {};

  constructor() {}

  ngOnInit() {}
}
