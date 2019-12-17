import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'jhi-activity-post',
  templateUrl: './activity-post.component.html',
  styleUrls: ['./activity-post.component.css']
})
export class ActivityPostComponent implements OnInit {
  @Input() post: any = {};

  public showComment: boolean = false;

  constructor() {}

  ngOnInit() {}

  toggleComment() {
    this.showComment = !this.showComment;
  }
}
