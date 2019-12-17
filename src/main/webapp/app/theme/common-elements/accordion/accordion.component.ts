import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'jhi-accordion',
  templateUrl: './accordion.component.html',
  styleUrls: ['./accordion.component.css']
})
export class AccordionComponent implements OnInit {
  @Input() options: any;
  public target: any;

  constructor() {}

  ngOnInit() {}

  toggleTarget(target: string) {
    if (this.target && target == this.target) {
      this.target = undefined;
    } else {
      this.target = target;
    }
  }
}
