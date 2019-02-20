import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRecipee } from 'app/shared/model/recipee.model';

type EntityResponseType = HttpResponse<IRecipee>;
type EntityArrayResponseType = HttpResponse<IRecipee[]>;

@Injectable({ providedIn: 'root' })
export class RecipeeService {
    public resourceUrl = SERVER_API_URL + 'api/recipees';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/recipees';

    constructor(protected http: HttpClient) {}

    create(recipee: IRecipee): Observable<EntityResponseType> {
        return this.http.post<IRecipee>(this.resourceUrl, recipee, { observe: 'response' });
    }

    update(recipee: IRecipee): Observable<EntityResponseType> {
        return this.http.put<IRecipee>(this.resourceUrl, recipee, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IRecipee>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRecipee[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRecipee[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
