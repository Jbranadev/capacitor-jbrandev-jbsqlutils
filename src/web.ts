import { WebPlugin } from '@capacitor/core';

import type { jbsqlutilsjsPlugin } from './definitions';

export class jbsqlutilsjsWeb extends WebPlugin implements jbsqlutilsjsPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
