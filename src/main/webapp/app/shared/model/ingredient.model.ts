import { IRecipee } from 'app/shared/model/recipee.model';

export interface IIngredient {
    id?: number;
    name?: string;
    recipees?: IRecipee[];
}

export class Ingredient implements IIngredient {
    constructor(public id?: number, public name?: string, public recipees?: IRecipee[]) {}
}
