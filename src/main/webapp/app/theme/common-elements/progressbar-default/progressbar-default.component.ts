import { Component, OnInit } from '@angular/core';
import { NgbProgressbarConfig } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-progressbar-default',
  templateUrl: './progressbar-default.component.html',
  styleUrls: ['./progressbar-default.component.scss'],
  providers: [NgbProgressbarConfig]
})
export class ProgressbarDefaultComponent implements OnInit {
  constructor(config: NgbProgressbarConfig) {
    config.height = '20px';
  }

  ngOnInit() {}
}
