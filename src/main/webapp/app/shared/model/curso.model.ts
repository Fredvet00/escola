export interface ICurso {
  id?: number;
  nome?: string;
}

export class Curso implements ICurso {
  constructor(public id?: number, public nome?: string) {}
}
