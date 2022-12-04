import { registerPlugin } from '@capacitor/core';

import type { jbsqlutilsjsPlugin } from './definitions';

const jbsqlutilsjs = registerPlugin<jbsqlutilsjsPlugin>('jbsqlutilsjs', {
  web: () => import('./web').then(m => new m.jbsqlutilsjsWeb()),
});

export * from './definitions';
export { jbsqlutilsjs };
