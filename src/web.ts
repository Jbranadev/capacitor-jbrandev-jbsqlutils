import { WebPlugin } from '@capacitor/core';

import type { CreateTableOptions, CreateTableResult, DeleteOptions, DeleteResult, DropTableOptions, DropTableResult, InsertOptions, InsertResult, jbsqlutilsjsPlugin, SelectOptions, UpdateOptions, UpdateResult } from './definitions';

export class jbsqlutilsjsWeb extends WebPlugin implements jbsqlutilsjsPlugin {

  dropTableIfExist(_droptableOptions: DropTableOptions): Promise<DropTableResult> {
    throw new Error('Method not implemented.');
  }

  createTable(_createtableOptions: CreateTableOptions): Promise<CreateTableResult> {
    throw new Error('Method not implemented.');
  }

  insertInto(_insertOptions: InsertOptions): Promise<InsertResult> {
    throw new Error('Method not implemented.');
  }

  update(_updateOptions: UpdateOptions): Promise<UpdateResult> {
    throw new Error('Method not implemented.');
  }

  delete(_deleteOptions: DeleteOptions): Promise<DeleteResult> {
    throw new Error('Method not implemented.');
  }

  select<T>(_selectOptions: SelectOptions): Promise<{'rows': T[]}> {
    throw new Error('Method not implemented.');
  }
  
}
