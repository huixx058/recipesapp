import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IIngredientAmount } from 'app/shared/model/ingredient-amount.model';

type EntityResponseType = HttpResponse<IIngredientAmount>;
type EntityArrayResponseType = HttpResponse<IIngredientAmount[]>;

@Injectable({ providedIn: 'root' })
export class IngredientAmountService {
    public resourceUrl = SERVER_API_URL + 'api/ingredient-amounts';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/ingredient-amounts';

    constructor(protected http: HttpClient) {}

    create(ingredientAmount: IIngredientAmount): Observable<EntityResponseType> {
        return this.http.post<IIngredientAmount>(this.resourceUrl, ingredientAmount, { observe: 'response' });
    }

    update(ingredientAmount: IIngredientAmount): Observable<EntityResponseType> {
        return this.http.put<IIngredientAmount>(this.resourceUrl, ingredientAmount, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IIngredientAmount>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IIngredientAmount[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IIngredientAmount[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
