import { WebPlugin } from '@capacitor/core';

import type { CreateTableOptions, CreateTableResult, DeleteOptions, DeleteResult, DropTableOptions, DropTableResult, InsertOptions, InsertResult, jbsqlutilsjsPlugin, SelectOptions, UpdateOptions, UpdateResult } from './definitions';

export class jbsqlutilsjsWeb extends WebPlugin implements jbsqlutilsjsPlugin {

  dropTableIfExist(droptableOptions: DropTableOptions): Promise<DropTableResult> {
    throw new Error('Method not implemented.');
  }

  createTable(createtableOptions: CreateTableOptions): Promise<CreateTableResult> {
    throw new Error('Method not implemented.');
  }

  insertInto(insertOptions: InsertOptions): Promise<InsertResult> {
    throw new Error('Method not implemented.');
  }

  update(updateOptions: UpdateOptions): Promise<UpdateResult> {
    throw new Error('Method not implemented.');
  }

  delete(deleteOptions: DeleteOptions): Promise<DeleteResult> {
    throw new Error('Method not implemented.');
  }

  select<T>(selectOptions: SelectOptions): Promise<T[]> {
    throw new Error('Method not implemented.');
  }
  
}
