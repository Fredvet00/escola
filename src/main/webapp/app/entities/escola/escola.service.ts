import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEscola } from 'app/shared/model/escola.model';

type EntityResponseType = HttpResponse<IEscola>;
type EntityArrayResponseType = HttpResponse<IEscola[]>;

@Injectable({ providedIn: 'root' })
export class EscolaService {
  public resourceUrl = SERVER_API_URL + 'api/escolas';

  constructor(protected http: HttpClient) {}

  create(escola: IEscola): Observable<EntityResponseType> {
    return this.http.post<IEscola>(this.resourceUrl, escola, { observe: 'response' });
  }

  update(escola: IEscola): Observable<EntityResponseType> {
    return this.http.put<IEscola>(this.resourceUrl, escola, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEscola>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEscola[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
