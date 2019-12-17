import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'jhi-tab-example1',
  templateUrl: './tab-example1.component.html',
  styleUrls: ['./tab-example1.component.scss']
})
export class TabExample1Component implements OnInit {
  @Input() tabs: Array<any> = new Array<any>();
  public activeTab: string = '';

  constructor() {}

  ngOnInit() {}

  tabChange(title: string) {
    if (this.activeTab != title) {
      this.activeTab = title;
    }
  }
}
