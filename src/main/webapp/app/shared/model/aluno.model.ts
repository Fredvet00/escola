export interface IAluno {
  id?: number;
  nome?: string;
  idade?: number;
}

export class Aluno implements IAluno {
  constructor(public id?: number, public nome?: string, public idade?: number) {}
}
