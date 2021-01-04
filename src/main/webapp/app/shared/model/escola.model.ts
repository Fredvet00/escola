export interface IEscola {
  id?: number;
  nome?: string;
}

export class Escola implements IEscola {
  constructor(public id?: number, public nome?: string) {}
}
