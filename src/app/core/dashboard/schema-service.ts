import { Injectable } from '@angular/core';
import { AngularFireDatabase } from 'angularfire2/database';

@Injectable()
export class SchemaService {

  schemaUpdated;

  constructor(private afDb: AngularFireDatabase) {
    this.fetchSchema();
  }

  private fetchSchema() {
    // this.afDb.database.ref(`/schema/`).orderByKey().on('value', days_ => {
    //   days_.forEach(day_ => {
    //     const day = day_.val();
    //     console.log(day);
    //     this.schema.push(day);
    //     this.schemaUpdated.next(this.schema);
    //   });
    // });
    this.schemaUpdated = this.afDb.list('/schema/').valueChanges();
  }

}
