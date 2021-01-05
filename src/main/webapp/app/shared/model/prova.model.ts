export interface IProva {
  id?: number;
  nome?: string;
  numquestoes?: number;
  enunciado?: string;
  texto?: string;
}

export class Prova implements IProva {
  constructor(public id?: number, public nome?: string, public numquestoes?: number, public enunciado?: string, public texto?: string) {}
}
