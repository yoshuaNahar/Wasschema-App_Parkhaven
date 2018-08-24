import { Component, OnInit } from '@angular/core';
import { RoomInfo, SchemaService } from './schema.service';

@Component({
  selector: 'app-schema',
  templateUrl: './schema.component.html',
  styleUrls: ['./schema.component.css'],
})
export class SchemaComponent implements OnInit {

  rooms: RoomInfo[];

  constructor(private schemaService: SchemaService) {
  }

  ngOnInit(): void {
    this.rooms = this.schemaService.roomsInfo;
  }

}
