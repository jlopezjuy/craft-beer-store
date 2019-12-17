import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'jhi-system-widget',
  templateUrl: './system-widget.component.html',
  styleUrls: ['./system-widget.component.css']
})
export class SystemWidgetComponent implements OnInit {
  @Input() options: any = {};
  @Output() toggleEvent = new EventEmitter();

  constructor() {}

  ngOnInit() {}

  onToggle() {
    this.toggleEvent.emit();
  }
}
