import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProva } from 'app/shared/model/prova.model';

type EntityResponseType = HttpResponse<IProva>;
type EntityArrayResponseType = HttpResponse<IProva[]>;

@Injectable({ providedIn: 'root' })
export class ProvaService {
  public resourceUrl = SERVER_API_URL + 'api/prova';

  constructor(protected http: HttpClient) {}

  create(prova: IProva): Observable<EntityResponseType> {
    return this.http.post<IProva>(this.resourceUrl, prova, { observe: 'response' });
  }

  update(prova: IProva): Observable<EntityResponseType> {
    return this.http.put<IProva>(this.resourceUrl, prova, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProva>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProva[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  download(format: string): Observable<HttpResponse<{}>> {
    return this.http.get(`${this.resourceUrl}/${format}/download`, { observe: 'response' });
  }
}
